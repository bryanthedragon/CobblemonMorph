
package com.oracle.truffle.js.runtime.array;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.Truffle;
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
    protected static final SetLengthProfileAccess SET_LENGTH_PROFILE = new SetLengthProfileAccess(){};

    public abstract Object getElement(JSDynamicObject var1, long var2);

    public abstract Object getElementInBounds(JSDynamicObject var1, long var2);

    public abstract ScriptArray setElementImpl(JSDynamicObject var1, long var2, Object var4, boolean var5);

    public final ScriptArray setElement(JSDynamicObject object, long index, Object value2, boolean strict) {
        if (this.isFrozen()) {
            if (strict) {
                ScriptArray.setElementFrozenStrict(index);
            }
            return this;
        }
        if (this.isLengthNotWritable() && index >= this.length(object)) {
            if (strict) {
                throw Errors.createTypeErrorLengthNotWritable();
            }
            return this;
        }
        return this.setElementImpl(object, index, value2, strict);
    }

    @CompilerDirectives.TruffleBoundary
    private static void setElementFrozenStrict(long index) {
        JSContext context = JavaScriptLanguage.getCurrentLanguage().getJSContext();
        if (context.isOptionNashornCompatibilityMode()) {
            throw Errors.createTypeErrorFormat("Cannot set property \"%d\" of frozen array", index);
        }
        throw Errors.createTypeErrorCannotRedefineProperty(Strings.fromLong(index));
    }

    public abstract ScriptArray deleteElementImpl(JSDynamicObject var1, long var2, boolean var4);

    public final ScriptArray deleteElement(JSDynamicObject object, long index, boolean strict) {
        assert (this.canDeleteElement(object, index, strict));
        return this.deleteElementImpl(object, index, strict);
    }

    public final boolean canDeleteElement(JSDynamicObject object, long index, boolean strict) {
        if (this.isSealed() && this.hasElement(object, index)) {
            if (strict) {
                throw Errors.createTypeErrorCannotDeletePropertyOfSealedArray(index);
            }
            return false;
        }
        return true;
    }

    public abstract boolean hasElement(JSDynamicObject var1, long var2);

    public abstract long length(JSDynamicObject var1);

    public abstract int lengthInt(JSDynamicObject var1);

    public static ProfileHolder createSetLengthProfile() {
        return ProfileHolder.create(8, SetLengthProfileAccess.class);
    }

    public abstract ScriptArray setLengthImpl(JSDynamicObject var1, long var2, ProfileHolder var4);

    public final ScriptArray setLength(JSDynamicObject object, long len, boolean strict, ProfileHolder profile) {
        if (this.isLengthNotWritable()) {
            if (strict) {
                throw Errors.createTypeErrorLengthNotWritable();
            }
            return this;
        }
        if (this.isSealed()) assert (len >= this.lastElementIndex(object) + 1L);
        return this.setLengthImpl(object, len, profile);
    }

    public final ScriptArray setLength(JSDynamicObject object, long len, boolean strict) {
        return this.setLength(object, len, strict, ProfileHolder.empty());
    }

    public abstract long firstElementIndex(JSDynamicObject var1);

    public abstract long lastElementIndex(JSDynamicObject var1);

    public abstract long nextElementIndex(JSDynamicObject var1, long var2);

    public abstract long previousElementIndex(JSDynamicObject var1, long var2);

    public boolean isInBoundsFast(JSDynamicObject object, long index) {
        return this.firstElementIndex(object) <= index && index <= this.lastElementIndex(object);
    }

    public Iterable<Object> asIterable(final JSDynamicObject object) {
        return new Iterable<Object>(){

            @Override
            public Iterator<Object> iterator() {
                return new DefaultIterator(object);
            }
        };
    }

    @CompilerDirectives.TruffleBoundary
    public final Object[] toArray(JSDynamicObject thisObj) {
        int len = this.lengthInt(thisObj);
        Object[] newArray = new Object[len];
        Arrays.fill(newArray, Undefined.instance);
        long i = this.firstElementIndex(thisObj);
        while (i <= this.lastElementIndex(thisObj)) {
            if (i >= 0L) {
                newArray[(int)i] = this.getElement(thisObj, i);
            }
            i = this.nextElementIndex(thisObj, i);
        }
        return newArray;
    }

    public static AbstractConstantArray createConstantEmptyArray() {
        return ConstantEmptyArray.createConstantEmptyArray();
    }

    public static AbstractConstantArray createConstantArray(Object[] elements2) {
        if (elements2 == null || elements2.length == 0) {
            return ScriptArray.createConstantEmptyArray();
        }
        return ConstantObjectArray.createConstantObjectArray();
    }

    public static boolean valueIsByte(int value2) {
        return -128 <= value2 && value2 <= 127;
    }

    @CompilerDirectives.TruffleBoundary
    public String toString(JSDynamicObject object) {
        StringBuilder sb = new StringBuilder();
        int i = 0;
        while ((long)i < this.length(object)) {
            Object element;
            if (i != 0) {
                sb.append(",");
            }
            if ((element = this.getElement(object, i)) != Null.instance && element != Undefined.instance) {
                sb.append(element);
            }
            ++i;
        }
        if ((long)i < this.length(object)) {
            sb.append(",... [" + (this.length(object) - (long)i + 1L) + " more]");
        }
        return sb.toString();
    }

    @CompilerDirectives.TruffleBoundary
    protected static final void traceArrayTransition(ScriptArray oldArray, ScriptArray newArray, long index, Object value2) {
        String access = oldArray.getClass().getSimpleName() + " -> " + newArray.getClass().getSimpleName();
        Stream<Node> nodeStream = null;
        ArrayList stackTrace = new ArrayList();
        Truffle.getRuntime().iterateFrames(frameInstance -> {
            stackTrace.add(frameInstance);
            return null;
        });
        nodeStream = StreamSupport.stream(stackTrace.spliterator(), false).filter(fi -> fi.getCallNode() != null).map(fi -> fi.getCallNode());
        int stackTraceLimit = JavaScriptLanguage.getCurrentLanguage().getJSContext().getContextOptions().getStackTraceLimit();
        StackTraceElement[] array = (StackTraceElement[])nodeStream.filter(n -> n.getEncapsulatingSourceSection() != null).map(node -> {
            SourceSection callNodeSourceSection = node.getEncapsulatingSourceSection();
            String declaringClass = "js";
            String methodName = node.getRootNode().getName();
            String fileName = callNodeSourceSection.isAvailable() ? callNodeSourceSection.getSource().getName() : "<unknown>";
            int startLine = callNodeSourceSection.getStartLine();
            return new StackTraceElement(declaringClass, methodName != null ? methodName : "<unknown>", fileName, startLine);
        }).limit(stackTraceLimit).toArray(StackTraceElement[]::new);
        System.out.printf("[js]      array transition %-48s |index %5s |value %-20s |caller %5s\n", access, index, value2, array[0]);
    }

    @CompilerDirectives.TruffleBoundary
    protected static final void traceWrite(String access, long index, Object value2) {
        System.out.printf("[js]      array set        %-48s |index %5s |value %-20s\n", access, index, value2);
    }

    public boolean isHolesType() {
        return false;
    }

    public abstract boolean hasHoles(JSDynamicObject var1);

    public abstract ScriptArray removeRangeImpl(JSDynamicObject var1, long var2, long var4);

    public final ScriptArray removeRange(JSDynamicObject object, long start2, long end2) {
        assert (start2 >= 0L && start2 <= end2);
        if (this.isSealed()) {
            throw Errors.createTypeErrorCannotDeletePropertyOfSealedArray(start2);
        }
        return this.removeRangeImpl(object, start2, end2);
    }

    public final ScriptArray removeRange(JSDynamicObject object, long start2, long end2, BranchProfile errorBranch) {
        assert (start2 >= 0L && start2 <= end2);
        if (this.isSealed()) {
            errorBranch.enter();
            throw Errors.createTypeErrorCannotDeletePropertyOfSealedArray(start2);
        }
        return this.removeRangeImpl(object, start2, end2);
    }

    public ScriptArray shiftRangeImpl(JSDynamicObject object, long limit) {
        return this.removeRangeImpl(object, 0L, limit);
    }

    public final ScriptArray shiftRange(JSDynamicObject object, long from) {
        assert (from >= 0L);
        assert (!this.isSealed());
        return this.shiftRangeImpl(object, from);
    }

    public abstract ScriptArray addRangeImpl(JSDynamicObject var1, long var2, int var4);

    public final ScriptArray addRange(JSDynamicObject object, long offset, int size) {
        if (!this.isExtensible()) {
            throw this.addRangeNotExtensible();
        }
        return this.addRangeImpl(object, offset, size);
    }

    @CompilerDirectives.TruffleBoundary
    private JSException addRangeNotExtensible() {
        if (this.isFrozen()) {
            throw Errors.createTypeError("Cannot add property of frozen array");
        }
        if (this.isSealed()) {
            throw Errors.createTypeError("Cannot add property to sealed array");
        }
        throw Errors.createTypeError("Cannot add property to non-extensible array");
    }

    public List<Object> ownPropertyKeys(JSDynamicObject object) {
        assert (!this.isHolesType() || !this.hasHoles(object));
        return this.ownPropertyKeysContiguous(object);
    }

    protected final List<Object> ownPropertyKeysContiguous(JSDynamicObject object) {
        return ScriptArray.makeRangeList(this.firstElementIndex(object), this.lastElementIndex(object) + 1L);
    }

    @CompilerDirectives.TruffleBoundary
    protected final List<Object> ownPropertyKeysHoles(JSDynamicObject object) {
        long currentIndex;
        long start2 = currentIndex = this.firstElementIndex(object);
        long end2 = currentIndex;
        int total = 0;
        ArrayList<Long> rangeList = new ArrayList<Long>();
        while (currentIndex <= this.lastElementIndex(object)) {
            if (currentIndex == end2) {
                end2 = currentIndex + 1L;
            } else {
                assert (end2 < currentIndex);
                assert (start2 < end2);
                total = (int)((long)total + (end2 - start2));
                rangeList.add(start2);
                rangeList.add(end2);
                start2 = currentIndex;
                end2 = currentIndex + 1L;
            }
            currentIndex = this.nextElementIndex(object, currentIndex);
        }
        if (start2 < end2) {
            total = (int)((long)total + (end2 - start2));
            if (rangeList.isEmpty()) {
                return ScriptArray.makeRangeList(start2, end2);
            }
            rangeList.add(start2);
            rangeList.add(end2);
        }
        return ScriptArray.makeMultiRangeList(total, ScriptArray.toLongArray(rangeList));
    }

    private static long[] toLongArray(List<Long> longList) {
        long[] longArray = new long[longList.size()];
        for (int i = 0; i < longArray.length; ++i) {
            longArray[i] = longList.get(i);
        }
        return longArray;
    }

    public static List<Object> makeRangeList(final long rangeStart, final long rangeEnd) {
        assert (rangeEnd - rangeStart >= 0L && rangeEnd - rangeStart <= Integer.MAX_VALUE);
        return new AbstractList<Object>(){

            @Override
            public Object get(int index) {
                if (index >= 0 && rangeStart + (long)index < rangeEnd) {
                    return Strings.fromLong(rangeStart + (long)index);
                }
                throw new IndexOutOfBoundsException();
            }

            @Override
            public int size() {
                return (int)(rangeEnd - rangeStart);
            }
        };
    }

    protected static List<Object> makeMultiRangeList(final int total, final long[] ranges) {
        return new AbstractList<Object>(){

            @Override
            public Object get(int index) {
                if (index >= 0) {
                    long relativeIndex = index;
                    for (int rangeIndex = 0; rangeIndex < ranges.length; rangeIndex += 2) {
                        long rangeEnd = ranges[rangeIndex + 1];
                        long rangeStart = ranges[rangeIndex];
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
        if (length < 8) {
            return 8;
        }
        return ScriptArray.nextPow2(length);
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
        assert (this == other);
        return this;
    }

    private static final class ProfileHolderImpl
    extends NodeCloneable
    implements ProfileHolder {
        @CompilerDirectives.CompilationFinal(dimensions=1)
        private ConditionProfile[] conditionProfiles;
        private Class<?> profileAccessClass;
        private static final ProfileHolderImpl EMPTY = new ProfileHolderImpl();

        private ProfileHolderImpl(int profileCount, Class<?> profileAccessClass) {
            this.conditionProfiles = new ConditionProfile[profileCount];
            this.profileAccessClass = profileAccessClass;
        }

        private ProfileHolderImpl() {
        }

        @Override
        public boolean profile(ProfileAccess profileAccess, int index, boolean condition2) {
            assert (this.profileAccessClass == null || this.profileAccessClass.isInstance(profileAccess));
            ConditionProfile[] profiles = this.conditionProfiles;
            if (profiles == null) {
                return condition2;
            }
            ConditionProfile profile = profiles[index];
            if (profile == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                profile = profiles[index] = ConditionProfile.createBinaryProfile();
            }
            return profile.profile(condition2);
        }
    }

    public static interface ProfileHolder {
        public boolean profile(ProfileAccess var1, int var2, boolean var3);

        public static ProfileHolder create(int profileCount, Class<?> profileAccessClass) {
            return new ProfileHolderImpl(profileCount, profileAccessClass);
        }

        public static ProfileHolder empty() {
            return ProfileHolderImpl.EMPTY;
        }
    }

    protected final class DefaultIterator
    implements Iterator<Object> {
        private long currentIndex;
        private final JSDynamicObject arrayObject;

        public DefaultIterator(JSDynamicObject arrayObject) {
            this.arrayObject = arrayObject;
            this.currentIndex = ScriptArray.this.firstElementIndex(arrayObject);
        }

        @Override
        public void remove() {
            --this.currentIndex;
        }

        @Override
        public Object next() {
            assert (this.currentIndex >= ScriptArray.this.firstElementIndex(this.arrayObject));
            Object element = ScriptArray.this.getElement(this.arrayObject, this.currentIndex);
            this.currentIndex = ScriptArray.this.nextElementIndex(this.arrayObject, this.currentIndex);
            return element;
        }

        @Override
        public boolean hasNext() {
            assert (this.currentIndex >= ScriptArray.this.firstElementIndex(this.arrayObject));
            return this.currentIndex <= ScriptArray.this.lastElementIndex(this.arrayObject);
        }
    }

    protected static interface SetLengthProfileAccess
    extends ProfileAccess {
        default public boolean lengthZero(ProfileHolder profile, boolean condition2) {
            return profile.profile(this, 0, condition2);
        }

        default public boolean lengthLess(ProfileHolder profile, boolean condition2) {
            return profile.profile(this, 1, condition2);
        }

        default public boolean zeroBasedSetUsedLength(ProfileHolder profile, boolean condition2) {
            return profile.profile(this, 2, condition2);
        }

        default public boolean zeroBasedClearUnusedArea(ProfileHolder profile, boolean condition2) {
            return profile.profile(this, 3, condition2);
        }

        default public boolean contiguousZeroUsed(ProfileHolder profile, boolean condition2) {
            return profile.profile(this, 4, condition2);
        }

        default public boolean contiguousNegativeUsed(ProfileHolder profile, boolean condition2) {
            return profile.profile(this, 5, condition2);
        }

        default public boolean contiguousShrinkUsed(ProfileHolder profile, boolean condition2) {
            return profile.profile(this, 6, condition2);
        }

        default public boolean clearUnusedArea(ProfileHolder profile, boolean condition2) {
            return profile.profile(this, 7, condition2);
        }
    }

    protected static interface ProfileAccess {
    }
}

