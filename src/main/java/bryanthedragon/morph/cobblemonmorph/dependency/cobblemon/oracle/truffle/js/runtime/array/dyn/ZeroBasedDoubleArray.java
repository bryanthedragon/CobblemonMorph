package com.oracle.truffle.js.runtime.array.dyn;

import com.oracle.truffle.js.runtime.JSConfig;
import com.oracle.truffle.js.runtime.array.DynamicArray;
import com.oracle.truffle.js.runtime.array.ScriptArray;
import com.oracle.truffle.js.runtime.builtins.JSAbstractArray;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;

public final class ZeroBasedDoubleArray extends AbstractDoubleArray {
   private static final ZeroBasedDoubleArray ZERO_BASED_DOUBLE_ARRAY = new ZeroBasedDoubleArray(0, createCache()).maybePreinitializeCache();

   public static ZeroBasedDoubleArray makeZeroBasedDoubleArray(JSDynamicObject object, int length, int usedLength, double[] array, int integrityLevel) {
      ZeroBasedDoubleArray arrayType = createZeroBasedDoubleArray().setIntegrityLevel(integrityLevel);
      JSAbstractArray.arraySetLength(object, length);
      JSAbstractArray.arraySetUsedLength(object, usedLength);
      JSAbstractArray.arraySetArray(object, array);
      return arrayType;
   }

   public static ZeroBasedDoubleArray createZeroBasedDoubleArray() {
      return ZERO_BASED_DOUBLE_ARRAY;
   }

   private ZeroBasedDoubleArray(int integrityLevel, DynamicArray.DynamicArrayCache cache) {
      super(integrityLevel, cache);
   }

   @Override
   public double getInBoundsFastDouble(JSDynamicObject object, int index) {
      return getArray(object)[index];
   }

   @Override
   public void setInBoundsFast(JSDynamicObject object, int index, double value) {
      getArray(object)[index] = value;
   }

   @Override
   public boolean isSupported(JSDynamicObject object, long index) {
      return isSupportedZeroBased(object, (int)index);
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

   public ZeroBasedObjectArray toObject(JSDynamicObject object, long index, Object value) {
      double[] array = getArray(object);
      int length = this.lengthInt(object);
      int usedLength = getUsedLength(object);
      Object[] doubleCopy = ArrayCopy.doubleToObject(array, 0, usedLength);
      ZeroBasedObjectArray newArray = ZeroBasedObjectArray.makeZeroBasedObjectArray(object, length, usedLength, doubleCopy, this.integrityLevel);
      if (JSConfig.TraceArrayTransitions) {
         traceArrayTransition(this, newArray, index, value);
      }

      return newArray;
   }

   public ContiguousDoubleArray toContiguous(JSDynamicObject object, long index, Object value) {
      double[] array = getArray(object);
      int length = this.lengthInt(object);
      int usedLength = getUsedLength(object);
      ContiguousDoubleArray newArray = ContiguousDoubleArray.makeContiguousDoubleArray(object, length, array, 0L, 0, usedLength, this.integrityLevel);
      if (JSConfig.TraceArrayTransitions) {
         traceArrayTransition(this, newArray, index, value);
      }

      return newArray;
   }

   public HolesDoubleArray toHoles(JSDynamicObject object, long index, Object value) {
      double[] array = getArray(object);
      int length = this.lengthInt(object);
      int usedLength = getUsedLength(object);
      HolesDoubleArray newArray = HolesDoubleArray.makeHolesDoubleArray(object, length, array, 0L, 0, usedLength, 0, this.integrityLevel);
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
      double[] array = getArray(object);
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
         ? ContiguousDoubleArray.makeContiguousDoubleArray(
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

   protected ZeroBasedDoubleArray withIntegrityLevel(int newIntegrityLevel) {
      return new ZeroBasedDoubleArray(newIntegrityLevel, this.cache);
   }

   @Override
   public long nextElementIndex(JSDynamicObject object, long index) {
      return this.nextElementIndexZeroBased(object, index);
   }
}
