package com.oracle.truffle.js.runtime.array;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.frame.FrameInstance;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCloneable;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.api.source.SourceSection;
import com.oracle.truffle.js.lang.JavaScriptLanguage;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSException;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.array.dyn.AbstractConstantArray;
import com.oracle.truffle.js.runtime.array.dyn.ConstantEmptyArray;
import com.oracle.truffle.js.runtime.array.dyn.ConstantObjectArray;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.Null;
import com.oracle.truffle.js.runtime.objects.Undefined;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public abstract class ScriptArray {
   public static final Object[] EMPTY_OBJECT_ARRAY = new Object[0];
   protected static final ScriptArray.SetLengthProfileAccess SET_LENGTH_PROFILE = new ScriptArray.SetLengthProfileAccess() {};

   public abstract Object getElement(JSDynamicObject object, long index);

   public abstract Object getElementInBounds(JSDynamicObject object, long index);

   public abstract ScriptArray setElementImpl(JSDynamicObject object, long index, Object value, boolean strict);

   public final ScriptArray setElement(JSDynamicObject object, long index, Object value, boolean strict) {
      if (this.isFrozen()) {
         if (strict) {
            setElementFrozenStrict(index);
         }

         return this;
      } else if (!this.isLengthNotWritable() || index < this.length(object)) {
         return this.setElementImpl(object, index, value, strict);
      } else if (strict) {
         throw Errors.createTypeErrorLengthNotWritable();
      } else {
         return this;
      }
   }

   @CompilerDirectives.TruffleBoundary
   private static void setElementFrozenStrict(long index) {
      JSContext context = JavaScriptLanguage.getCurrentLanguage().getJSContext();
      if (context.isOptionNashornCompatibilityMode()) {
         throw Errors.createTypeErrorFormat("Cannot set property \"%d\" of frozen array", index);
      } else {
         throw Errors.createTypeErrorCannotRedefineProperty(Strings.fromLong(index));
      }
   }

   public abstract ScriptArray deleteElementImpl(JSDynamicObject object, long index, boolean strict);

   public final ScriptArray deleteElement(JSDynamicObject object, long index, boolean strict) {
      assert this.canDeleteElement(object, index, strict);

      return this.deleteElementImpl(object, index, strict);
   }

   public final boolean canDeleteElement(JSDynamicObject object, long index, boolean strict) {
      if (!this.isSealed() || !this.hasElement(object, index)) {
         return true;
      } else if (strict) {
         throw Errors.createTypeErrorCannotDeletePropertyOfSealedArray(index);
      } else {
         return false;
      }
   }

   public abstract boolean hasElement(JSDynamicObject object, long index);

   public abstract long length(JSDynamicObject object);

   public abstract int lengthInt(JSDynamicObject object);

   public static ScriptArray.ProfileHolder createSetLengthProfile() {
      return ScriptArray.ProfileHolder.create(8, ScriptArray.SetLengthProfileAccess.class);
   }

   public abstract ScriptArray setLengthImpl(JSDynamicObject object, long len, ScriptArray.ProfileHolder profile);

   public final ScriptArray setLength(JSDynamicObject object, long len, boolean strict, ScriptArray.ProfileHolder profile) {
      if (this.isLengthNotWritable()) {
         if (strict) {
            throw Errors.createTypeErrorLengthNotWritable();
         } else {
            return this;
         }
      } else {
         assert !this.isSealed() || len >= this.lastElementIndex(object) + 1L;

         return this.setLengthImpl(object, len, profile);
      }
   }

   public final ScriptArray setLength(JSDynamicObject object, long len, boolean strict) {
      return this.setLength(object, len, strict, ScriptArray.ProfileHolder.empty());
   }

   public abstract long firstElementIndex(JSDynamicObject object);

   public abstract long lastElementIndex(JSDynamicObject object);

   public abstract long nextElementIndex(JSDynamicObject object, long index);

   public abstract long previousElementIndex(JSDynamicObject object, long index);

   public boolean isInBoundsFast(JSDynamicObject object, long index) {
      return this.firstElementIndex(object) <= index && index <= this.lastElementIndex(object);
   }

   public Iterable<Object> asIterable(JSDynamicObject object) {
      return new Iterable<Object>() {
         @Override
         public Iterator<Object> iterator() {
            return ScriptArray.this.new DefaultIterator(object);
         }
      };
   }

   @CompilerDirectives.TruffleBoundary
   public final Object[] toArray(JSDynamicObject thisObj) {
      int len = this.lengthInt(thisObj);
      Object[] newArray = new Object[len];
      Arrays.fill(newArray, Undefined.instance);

      for (long i = this.firstElementIndex(thisObj); i <= this.lastElementIndex(thisObj); i = this.nextElementIndex(thisObj, i)) {
         if (i >= 0L) {
            newArray[(int)i] = this.getElement(thisObj, i);
         }
      }

      return newArray;
   }

   public static AbstractConstantArray createConstantEmptyArray() {
      return ConstantEmptyArray.createConstantEmptyArray();
   }

   public static AbstractConstantArray createConstantArray(Object[] elements) {
      return (AbstractConstantArray)(elements != null && elements.length != 0 ? ConstantObjectArray.createConstantObjectArray() : createConstantEmptyArray());
   }

   public static boolean valueIsByte(int value) {
      return -128 <= value && value <= 127;
   }

   @CompilerDirectives.TruffleBoundary
   public String toString(JSDynamicObject object) {
      StringBuilder sb = new StringBuilder();

      int i;
      for (i = 0; i < this.length(object); i++) {
         if (i != 0) {
            sb.append(",");
         }

         Object element = this.getElement(object, i);
         if (element != Null.instance && element != Undefined.instance) {
            sb.append(element);
         }
      }

      if (i < this.length(object)) {
         sb.append(",... [" + (this.length(object) - i + 1L) + " more]");
      }

      return sb.toString();
   }

   @CompilerDirectives.TruffleBoundary
   protected static final void traceArrayTransition(ScriptArray oldArray, ScriptArray newArray, long index, Object value) {
      String access = oldArray.getClass().getSimpleName() + " -> " + newArray.getClass().getSimpleName();
      Stream<Node> nodeStream = null;
      List<FrameInstance> stackTrace = new ArrayList<>();
      Truffle.getRuntime().iterateFrames(frameInstance -> {
         stackTrace.add(frameInstance);
         return null;
      });
      nodeStream = StreamSupport.stream(stackTrace.spliterator(), false).filter(fi -> fi.getCallNode() != null).map(fi -> fi.getCallNode());
      int stackTraceLimit = JavaScriptLanguage.getCurrentLanguage().getJSContext().getContextOptions().getStackTraceLimit();
      StackTraceElement[] array = nodeStream.filter(n -> n.getEncapsulatingSourceSection() != null).map(node -> {
         SourceSection callNodeSourceSection = node.getEncapsulatingSourceSection();
         String declaringClass = "js";
         String methodName = node.getRootNode().getName();
         String fileName = callNodeSourceSection.isAvailable() ? callNodeSourceSection.getSource().getName() : "<unknown>";
         int startLine = callNodeSourceSection.getStartLine();
         return new StackTraceElement(declaringClass, methodName != null ? methodName : "<unknown>", fileName, startLine);
      }).limit(stackTraceLimit).toArray(StackTraceElement[]::new);
      System.out.printf("[js]      array transition %-48s |index %5s |value %-20s |caller %5s\n", access, index, value, array[0]);
   }

   @CompilerDirectives.TruffleBoundary
   protected static final void traceWrite(String access, long index, Object value) {
      System.out.printf("[js]      array set        %-48s |index %5s |value %-20s\n", access, index, value);
   }

   public boolean isHolesType() {
      return false;
   }

   public abstract boolean hasHoles(JSDynamicObject object);

   public abstract ScriptArray removeRangeImpl(JSDynamicObject object, long start, long end);

   public final ScriptArray removeRange(JSDynamicObject object, long start, long end) {
      assert start >= 0L && start <= end;

      if (this.isSealed()) {
         throw Errors.createTypeErrorCannotDeletePropertyOfSealedArray(start);
      } else {
         return this.removeRangeImpl(object, start, end);
      }
   }

   public final ScriptArray removeRange(JSDynamicObject object, long start, long end, BranchProfile errorBranch) {
      assert start >= 0L && start <= end;

      if (this.isSealed()) {
         errorBranch.enter();
         throw Errors.createTypeErrorCannotDeletePropertyOfSealedArray(start);
      } else {
         return this.removeRangeImpl(object, start, end);
      }
   }

   public ScriptArray shiftRangeImpl(JSDynamicObject object, long limit) {
      return this.removeRangeImpl(object, 0L, limit);
   }

   public final ScriptArray shiftRange(JSDynamicObject object, long from) {
      assert from >= 0L;

      assert !this.isSealed();

      return this.shiftRangeImpl(object, from);
   }

   public abstract ScriptArray addRangeImpl(JSDynamicObject object, long offset, int size);

   public final ScriptArray addRange(JSDynamicObject object, long offset, int size) {
      if (!this.isExtensible()) {
         throw this.addRangeNotExtensible();
      } else {
         return this.addRangeImpl(object, offset, size);
      }
   }

   @CompilerDirectives.TruffleBoundary
   private JSException addRangeNotExtensible() {
      if (this.isFrozen()) {
         throw Errors.createTypeError("Cannot add property of frozen array");
      } else if (this.isSealed()) {
         throw Errors.createTypeError("Cannot add property to sealed array");
      } else {
         throw Errors.createTypeError("Cannot add property to non-extensible array");
      }
   }

   public List<Object> ownPropertyKeys(JSDynamicObject object) {
      assert !this.isHolesType() || !this.hasHoles(object);

      return this.ownPropertyKeysContiguous(object);
   }

   protected final List<Object> ownPropertyKeysContiguous(JSDynamicObject object) {
      return makeRangeList(this.firstElementIndex(object), this.lastElementIndex(object) + 1L);
   }

   @CompilerDirectives.TruffleBoundary
   protected final List<Object> ownPropertyKeysHoles(JSDynamicObject object) {
      long currentIndex = this.firstElementIndex(object);
      long start = currentIndex;
      long end = currentIndex;
      int total = 0;

      List<Long> rangeList;
      for (rangeList = new ArrayList<>(); currentIndex <= this.lastElementIndex(object); currentIndex = this.nextElementIndex(object, currentIndex)) {
         if (currentIndex == end) {
            end = currentIndex + 1L;
         } else {
            assert end < currentIndex;

            assert start < end;

            total = (int)(total + (end - start));
            rangeList.add(start);
            rangeList.add(end);
            start = currentIndex;
            end = currentIndex + 1L;
         }
      }

      if (start < end) {
         total = (int)(total + (end - start));
         if (rangeList.isEmpty()) {
            return makeRangeList(start, end);
         }

         rangeList.add(start);
         rangeList.add(end);
      }

      return makeMultiRangeList(total, toLongArray(rangeList));
   }

   private static long[] toLongArray(List<Long> longList) {
      long[] longArray = new long[longList.size()];

      for (int i = 0; i < longArray.length; i++) {
         longArray[i] = longList.get(i);
      }

      return longArray;
   }

   public static List<Object> makeRangeList(final long rangeStart, final long rangeEnd) {
      assert rangeEnd - rangeStart >= 0L && rangeEnd - rangeStart <= 2147483647L;

      return new AbstractList<Object>() {
         @Override
         public Object get(int index) {
            if (index >= 0 && rangeStart + index < rangeEnd) {
               return Strings.fromLong(rangeStart + index);
            } else {
               throw new IndexOutOfBoundsException();
            }
         }

         @Override
         public int size() {
            return (int)(rangeEnd - rangeStart);
         }
      };
   }

   protected static List<Object> makeMultiRangeList(final int total, final long[] ranges) {
      return new AbstractList<Object>() {
         @Override
         public Object get(int index) {
            if (index >= 0) {
               long relativeIndex = index;

               for (int rangeIndex = 0; rangeIndex < ranges.length; rangeIndex += 2) {
                  long rangeStart = ranges[rangeIndex];
                  long rangeEnd = ranges[rangeIndex + 1];
                  long rangeLen = rangeEnd - rangeStart;
                  if (relativeIndex < rangeLen) {
                     return Strings.fromLong(rangeStart + relativeIndex);
                  }

                  relativeIndex -= rangeLen;
               }
            }

            throw new IndexOutOfBoundsException();
         }

         @Override
         public int size() {
            return total;
         }
      };
   }

   protected static int nextPower(int length) {
      return length < 8 ? 8 : nextPow2(length);
   }

   private static int nextPow2(int val) {
      int x = val - 1;
      x |= x >> 1;
      x |= x >> 2;
      x |= x >> 4;
      x |= x >> 8;
      x |= x >> 16;
      return x + 1;
   }

   public boolean isSealed() {
      return false;
   }

   public boolean isFrozen() {
      return false;
   }

   public boolean isLengthNotWritable() {
      return false;
   }

   public boolean isExtensible() {
      return true;
   }

   public abstract ScriptArray seal();

   public abstract ScriptArray freeze();

   public abstract ScriptArray setLengthNotWritable();

   public abstract ScriptArray preventExtensions();

   public final boolean isInstance(ScriptArray other) {
      CompilerAsserts.partialEvaluationConstant(this);
      return this == other;
   }

   public final ScriptArray cast(ScriptArray other) {
      CompilerAsserts.partialEvaluationConstant(this);

      assert this == other;

      return this;
   }

   protected final class DefaultIterator implements Iterator<Object> {
      private long currentIndex;
      private final JSDynamicObject arrayObject;

      public DefaultIterator(JSDynamicObject arrayObject) {
         this.arrayObject = arrayObject;
         this.currentIndex = ScriptArray.this.firstElementIndex(arrayObject);
      }

      @Override
      public void remove() {
         this.currentIndex--;
      }

      @Override
      public Object next() {
         assert this.currentIndex >= ScriptArray.this.firstElementIndex(this.arrayObject);

         Object element = ScriptArray.this.getElement(this.arrayObject, this.currentIndex);
         this.currentIndex = ScriptArray.this.nextElementIndex(this.arrayObject, this.currentIndex);
         return element;
      }

      @Override
      public boolean hasNext() {
         assert this.currentIndex >= ScriptArray.this.firstElementIndex(this.arrayObject);

         return this.currentIndex <= ScriptArray.this.lastElementIndex(this.arrayObject);
      }
   }

   protected interface ProfileAccess {
   }

   public interface ProfileHolder {
      boolean profile(ScriptArray.ProfileAccess profileAccess, int index, boolean condition);

      static ScriptArray.ProfileHolder create(int profileCount, Class<?> profileAccessClass) {
         return new ScriptArray.ProfileHolderImpl(profileCount, profileAccessClass);
      }

      static ScriptArray.ProfileHolder empty() {
         return ScriptArray.ProfileHolderImpl.EMPTY;
      }
   }

   private static final class ProfileHolderImpl extends NodeCloneable implements ScriptArray.ProfileHolder {
      @CompilerDirectives.CompilationFinal(dimensions = 1)
      private ConditionProfile[] conditionProfiles;
      private Class<?> profileAccessClass;
      private static final ScriptArray.ProfileHolderImpl EMPTY = new ScriptArray.ProfileHolderImpl();

      private ProfileHolderImpl(int profileCount, Class<?> profileAccessClass) {
         this.conditionProfiles = new ConditionProfile[profileCount];
         this.profileAccessClass = profileAccessClass;
      }

      private ProfileHolderImpl() {
      }

      @Override
      public boolean profile(ScriptArray.ProfileAccess profileAccess, int index, boolean condition) {
         assert this.profileAccessClass == null || this.profileAccessClass.isInstance(profileAccess);

         ConditionProfile[] profiles = this.conditionProfiles;
         if (profiles == null) {
            return condition;
         } else {
            ConditionProfile profile = profiles[index];
            if (profile == null) {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               profile = profiles[index] = ConditionProfile.createBinaryProfile();
            }

            return profile.profile(condition);
         }
      }
   }

   protected interface SetLengthProfileAccess extends ScriptArray.ProfileAccess {
      default boolean lengthZero(ScriptArray.ProfileHolder profile, boolean condition) {
         return profile.profile(this, 0, condition);
      }

      default boolean lengthLess(ScriptArray.ProfileHolder profile, boolean condition) {
         return profile.profile(this, 1, condition);
      }

      default boolean zeroBasedSetUsedLength(ScriptArray.ProfileHolder profile, boolean condition) {
         return profile.profile(this, 2, condition);
      }

      default boolean zeroBasedClearUnusedArea(ScriptArray.ProfileHolder profile, boolean condition) {
         return profile.profile(this, 3, condition);
      }

      default boolean contiguousZeroUsed(ScriptArray.ProfileHolder profile, boolean condition) {
         return profile.profile(this, 4, condition);
      }

      default boolean contiguousNegativeUsed(ScriptArray.ProfileHolder profile, boolean condition) {
         return profile.profile(this, 5, condition);
      }

      default boolean contiguousShrinkUsed(ScriptArray.ProfileHolder profile, boolean condition) {
         return profile.profile(this, 6, condition);
      }

      default boolean clearUnusedArea(ScriptArray.ProfileHolder profile, boolean condition) {
         return profile.profile(this, 7, condition);
      }
   }
}
