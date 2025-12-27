package com.oracle.truffle.js.runtime.array.dyn;

import com.oracle.truffle.js.runtime.JSConfig;
import com.oracle.truffle.js.runtime.array.DynamicArray;
import com.oracle.truffle.js.runtime.array.ScriptArray;
import com.oracle.truffle.js.runtime.builtins.JSAbstractArray;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.util.Arrays;

public final class ZeroBasedJSObjectArray extends AbstractJSObjectArray {
   private static final ZeroBasedJSObjectArray ZERO_BASED_JSOBJECT_ARRAY = new ZeroBasedJSObjectArray(0, createCache()).maybePreinitializeCache();

   public static <T> ZeroBasedJSObjectArray makeZeroBasedJSObjectArray(JSDynamicObject object, int length, int usedLength, T[] array, int integrityLevel) {
      ZeroBasedJSObjectArray arrayType = createZeroBasedJSObjectArray().setIntegrityLevel(integrityLevel);
      JSAbstractArray.arraySetLength(object, length);
      JSAbstractArray.arraySetUsedLength(object, usedLength);
      JSAbstractArray.arraySetArray(object, array);
      return arrayType;
   }

   public static ZeroBasedJSObjectArray createZeroBasedJSObjectArray() {
      return ZERO_BASED_JSOBJECT_ARRAY;
   }

   private ZeroBasedJSObjectArray(int integrityLevel, DynamicArray.DynamicArrayCache cache) {
      super(integrityLevel, cache);
   }

   @Override
   public boolean isSupported(JSDynamicObject object, long index) {
      return isSupportedZeroBased(object, (int)index);
   }

   @Override
   public JSDynamicObject getInBoundsFastJSObject(JSDynamicObject object, int index) {
      return this.castNonNull(getArray(object)[index]);
   }

   @Override
   public void setInBoundsFast(JSDynamicObject object, int index, JSDynamicObject value) {
      getArray(object)[index] = checkNonNull(value);
   }

   @Override
   protected int prepareInBoundsFast(JSDynamicObject object, long index) {
      return (int)index;
   }

   @Override
   protected int prepareInBounds(JSDynamicObject object, int index, ScriptArray.ProfileHolder profile) {
      prepareInBoundsZeroBased(object, index, profile);
      return index;
   }

   @Override
   protected int prepareSupported(JSDynamicObject object, int index, ScriptArray.ProfileHolder profile) {
      this.prepareSupportedZeroBased(object, index, profile);
      return index;
   }

   @Override
   protected void setLengthLess(JSDynamicObject object, long length, ScriptArray.ProfileHolder profile) {
      this.setLengthLessZeroBased(object, length, profile);
   }

   public ContiguousJSObjectArray toContiguous(JSDynamicObject object, long index, Object value) {
      JSDynamicObject[] array = getArray(object);
      int length = this.lengthInt(object);
      int usedLength = getUsedLength(object);
      ContiguousJSObjectArray newArray = ContiguousJSObjectArray.makeContiguousJSObjectArray(object, length, array, 0L, 0, usedLength, this.integrityLevel);
      if (JSConfig.TraceArrayTransitions) {
         traceArrayTransition(this, newArray, index, value);
      }

      return newArray;
   }

   public HolesJSObjectArray toHoles(JSDynamicObject object, long index, Object value) {
      JSDynamicObject[] array = getArray(object);
      int length = this.lengthInt(object);
      int usedLength = getUsedLength(object);
      HolesJSObjectArray newArray = HolesJSObjectArray.makeHolesJSObjectArray(object, length, array, 0L, 0, usedLength, 0, this.integrityLevel);
      if (JSConfig.TraceArrayTransitions) {
         traceArrayTransition(this, newArray, index, value);
      }

      return newArray;
   }

   public ZeroBasedObjectArray toObject(JSDynamicObject object, long index, Object value) {
      JSDynamicObject[] array = getArray(object);
      int length = this.lengthInt(object);
      int usedLength = getUsedLength(object);
      Object[] doubleCopy = ArrayCopy.jsobjectToObject(array, 0, usedLength);
      ZeroBasedObjectArray newArray = ZeroBasedObjectArray.makeZeroBasedObjectArray(object, length, usedLength, doubleCopy, this.integrityLevel);
      if (JSConfig.TraceArrayTransitions) {
         traceArrayTransition(this, newArray, index, value);
      }

      return newArray;
   }

   @Override
   public long firstElementIndex(JSDynamicObject object) {
      return 0L;
   }

   @Override
   public long lastElementIndex(JSDynamicObject object) {
      return getUsedLength(object) - 1;
   }

   @Override
   public ScriptArray removeRangeImpl(JSDynamicObject object, long start, long end) {
      JSDynamicObject[] array = getArray(object);
      int usedLength = getUsedLength(object);
      long moveLength = usedLength - end;
      if (moveLength > 0L) {
         System.arraycopy(array, (int)end, array, (int)start, (int)moveLength);
      }

      if (start < usedLength) {
         Arrays.fill(array, (int)(start + Math.max(0L, moveLength)), usedLength, null);
         int newUsedLength = (int)(moveLength > 0L ? usedLength - (end - start) : start);
         JSAbstractArray.arraySetUsedLength(object, newUsedLength);
      }

      return this;
   }

   @Override
   public ScriptArray shiftRangeImpl(JSDynamicObject object, long from) {
      int usedLength = getUsedLength(object);
      return (ScriptArray)(from < usedLength
         ? ContiguousJSObjectArray.makeContiguousJSObjectArray(
            object, this.lengthInt(object) - from, getArray(object), -from, (int)from, (int)(usedLength - from), this.integrityLevel
         )
         : this.removeRangeImpl(object, 0L, from));
   }

   @Override
   public ScriptArray addRangeImpl(JSDynamicObject object, long offset, int size) {
      return this.addRangeImplZeroBased(object, offset, size);
   }

   @Override
   public boolean hasHoles(JSDynamicObject object) {
      int length = this.lengthInt(object);
      int usedLength = getUsedLength(object);
      return usedLength < length;
   }

   protected ZeroBasedJSObjectArray withIntegrityLevel(int newIntegrityLevel) {
      return new ZeroBasedJSObjectArray(newIntegrityLevel, this.cache);
   }

   @Override
   public long nextElementIndex(JSDynamicObject object, long index) {
      return this.nextElementIndexZeroBased(object, index);
   }
}
