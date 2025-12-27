package com.oracle.truffle.js.runtime.array.dyn;

import com.oracle.truffle.js.runtime.JSConfig;
import com.oracle.truffle.js.runtime.array.DynamicArray;
import com.oracle.truffle.js.runtime.array.ScriptArray;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;

public final class ContiguousDoubleArray extends AbstractContiguousDoubleArray {
   private static final ContiguousDoubleArray CONTIGUOUS_DOUBLE_ARRAY = new ContiguousDoubleArray(0, createCache()).maybePreinitializeCache();

   public static ContiguousDoubleArray makeContiguousDoubleArray(
      JSDynamicObject object, long length, double[] array, long indexOffset, int arrayOffset, int usedLength, int integrityLevel
   ) {
      ContiguousDoubleArray arrayType = createContiguousDoubleArray().setIntegrityLevel(integrityLevel);
      setArrayProperties(object, array, length, usedLength, indexOffset, arrayOffset);
      return arrayType;
   }

   private static ContiguousDoubleArray createContiguousDoubleArray() {
      return CONTIGUOUS_DOUBLE_ARRAY;
   }

   private ContiguousDoubleArray(int integrityLevel, DynamicArray.DynamicArrayCache cache) {
      super(integrityLevel, cache);
   }

   @Override
   protected int prepareInBounds(JSDynamicObject object, int index, ScriptArray.ProfileHolder profile) {
      return this.prepareInBoundsContiguous(object, index, profile);
   }

   @Override
   protected int prepareSupported(JSDynamicObject object, int index, ScriptArray.ProfileHolder profile) {
      return this.prepareSupportedContiguous(object, index, profile);
   }

   @Override
   public boolean isSupported(JSDynamicObject object, long index) {
      return this.isSupportedContiguous(object, index);
   }

   public ContiguousObjectArray toObject(JSDynamicObject object, long index, Object value) {
      double[] array = getArray(object);
      int length = this.lengthInt(object);
      int usedLength = getUsedLength(object);
      int arrayOffset = this.getArrayOffset(object);
      long indexOffset = this.getIndexOffset(object);
      Object[] doubleCopy = ArrayCopy.doubleToObject(array, arrayOffset, usedLength);
      ContiguousObjectArray newArray = ContiguousObjectArray.makeContiguousObjectArray(
         object, length, doubleCopy, indexOffset, arrayOffset, usedLength, this.integrityLevel
      );
      if (JSConfig.TraceArrayTransitions) {
         traceArrayTransition(this, newArray, index, value);
      }

      return newArray;
   }

   public ZeroBasedDoubleArray toNonContiguous(JSDynamicObject object, int index, Object value, ScriptArray.ProfileHolder profile) {
      this.setSupported(object, index, (Double)value, profile);
      double[] array = getArray(object);
      int length = this.lengthInt(object);
      int usedLength = getUsedLength(object);
      ZeroBasedDoubleArray newArray = ZeroBasedDoubleArray.makeZeroBasedDoubleArray(object, length, usedLength, array, this.integrityLevel);
      if (JSConfig.TraceArrayTransitions) {
         traceArrayTransition(this, newArray, index, value);
      }

      return newArray;
   }

   public HolesDoubleArray toHoles(JSDynamicObject object, long index, Object value) {
      double[] array = getArray(object);
      int length = this.lengthInt(object);
      int usedLength = getUsedLength(object);
      int arrayOffset = this.getArrayOffset(object);
      long indexOffset = this.getIndexOffset(object);
      HolesDoubleArray newArray = HolesDoubleArray.makeHolesDoubleArray(object, length, array, indexOffset, arrayOffset, usedLength, 0, this.integrityLevel);
      if (JSConfig.TraceArrayTransitions) {
         traceArrayTransition(this, newArray, index, value);
      }

      return newArray;
   }

   @Override
   public ScriptArray removeRangeImpl(JSDynamicObject object, long start, long end) {
      return this.removeRangeContiguous(object, start, end);
   }

   protected ContiguousDoubleArray withIntegrityLevel(int newIntegrityLevel) {
      return new ContiguousDoubleArray(newIntegrityLevel, this.cache);
   }
}
