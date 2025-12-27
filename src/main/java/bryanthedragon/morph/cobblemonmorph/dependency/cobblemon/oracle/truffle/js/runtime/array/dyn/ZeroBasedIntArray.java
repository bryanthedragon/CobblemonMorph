package com.oracle.truffle.js.runtime.array.dyn;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.js.runtime.JSConfig;
import com.oracle.truffle.js.runtime.array.DynamicArray;
import com.oracle.truffle.js.runtime.array.ScriptArray;
import com.oracle.truffle.js.runtime.builtins.JSAbstractArray;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;

public final class ZeroBasedIntArray extends AbstractIntArray {
   private static final ZeroBasedIntArray ZERO_BASED_INT_ARRAY = new ZeroBasedIntArray(0, createCache()).maybePreinitializeCache();

   public static ZeroBasedIntArray makeZeroBasedIntArray(JSDynamicObject object, int length, int usedLength, int[] array, int integrityLevel) {
      ZeroBasedIntArray arrayType = createZeroBasedIntArray().setIntegrityLevel(integrityLevel);
      JSAbstractArray.arraySetLength(object, length);
      JSAbstractArray.arraySetUsedLength(object, usedLength);
      JSAbstractArray.arraySetArray(object, array);
      return arrayType;
   }

   public static ZeroBasedIntArray createZeroBasedIntArray() {
      return ZERO_BASED_INT_ARRAY;
   }

   private ZeroBasedIntArray(int integrityLevel, DynamicArray.DynamicArrayCache cache) {
      super(integrityLevel, cache);
   }

   @Override
   public boolean isSupported(JSDynamicObject object, long index) {
      return isSupportedZeroBased(object, (int)index);
   }

   @Override
   public int getInBoundsFastInt(JSDynamicObject object, int index) {
      return getArray(object)[index];
   }

   @Override
   public void setInBoundsFast(JSDynamicObject object, int index, int value) {
      getArray(object)[index] = value;
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

   public ZeroBasedDoubleArray toDouble(JSDynamicObject object, long index, double value) {
      int[] array = getArray(object);
      int length = this.lengthInt(object);
      int usedLength = getUsedLength(object);
      double[] doubleCopy = ArrayCopy.intToDouble(array, 0, usedLength);
      ZeroBasedDoubleArray newArray = ZeroBasedDoubleArray.makeZeroBasedDoubleArray(object, length, usedLength, doubleCopy, this.integrityLevel);
      if (JSConfig.TraceArrayTransitions) {
         traceArrayTransition(this, newArray, index, value);
      }

      return newArray;
   }

   public ZeroBasedObjectArray toObject(JSDynamicObject object, long index, Object value) {
      int[] array = getArray(object);
      int length = this.lengthInt(object);
      int usedLength = getUsedLength(object);
      Object[] doubleCopy = ArrayCopy.intToObject(array, 0, usedLength);
      ZeroBasedObjectArray newArray = ZeroBasedObjectArray.makeZeroBasedObjectArray(object, length, usedLength, doubleCopy, this.integrityLevel);
      if (JSConfig.TraceArrayTransitions) {
         traceArrayTransition(this, newArray, index, value);
      }

      return newArray;
   }

   public ContiguousIntArray toContiguous(JSDynamicObject object, long index, Object value) {
      int[] array = getArray(object);
      int length = this.lengthInt(object);
      int usedLength = getUsedLength(object);
      ContiguousIntArray newArray = ContiguousIntArray.makeContiguousIntArray(object, length, array, 0L, 0, usedLength, this.integrityLevel);
      if (JSConfig.TraceArrayTransitions) {
         traceArrayTransition(this, newArray, index, value);
      }

      return newArray;
   }

   @Override
   public AbstractWritableArray toHoles(JSDynamicObject object, long index, Object value) {
      int[] array = getArray(object);
      int length = this.lengthInt(object);
      int usedLength = getUsedLength(object);
      AbstractWritableArray newArray;
      if (CompilerDirectives.injectBranchProbability(1.0E-4, containsHoleValue(object))) {
         newArray = this.toObjectHoles(object);
      } else {
         newArray = HolesIntArray.makeHolesIntArray(object, length, array, 0L, 0, usedLength, 0, this.integrityLevel);
      }

      if (JSConfig.TraceArrayTransitions) {
         traceArrayTransition(this, newArray, index, value);
      }

      return newArray;
   }

   @Override
   protected HolesObjectArray toObjectHoles(JSDynamicObject object) {
      int length = this.lengthInt(object);
      int usedLength = getUsedLength(object);
      return HolesObjectArray.makeHolesObjectArray(object, length, convertToObject(object), 0L, 0, usedLength, 0, this.integrityLevel);
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
      int[] array = getArray(object);
      int usedLength = getUsedLength(object);
      long moveLength = usedLength - end;
      if (moveLength > 0L) {
         System.arraycopy(array, (int)end, array, (int)start, (int)moveLength);
      }

      if (start < usedLength) {
         int newUsedLength = (int)(moveLength > 0L ? usedLength - (end - start) : start);
         JSAbstractArray.arraySetUsedLength(object, newUsedLength);
      }

      return this;
   }

   @Override
   public ScriptArray shiftRangeImpl(JSDynamicObject object, long from) {
      int usedLength = getUsedLength(object);
      return (ScriptArray)(from < usedLength
         ? ContiguousIntArray.makeContiguousIntArray(
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

   protected ZeroBasedIntArray withIntegrityLevel(int newIntegrityLevel) {
      return new ZeroBasedIntArray(newIntegrityLevel, this.cache);
   }

   @Override
   public long nextElementIndex(JSDynamicObject object, long index) {
      return this.nextElementIndexZeroBased(object, index);
   }
}
