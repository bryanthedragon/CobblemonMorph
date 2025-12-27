package com.oracle.truffle.js.runtime.array.dyn;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.js.runtime.JSConfig;
import com.oracle.truffle.js.runtime.array.DynamicArray;
import com.oracle.truffle.js.runtime.array.ScriptArray;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;

public final class ContiguousIntArray extends AbstractContiguousIntArray {
   private static final ContiguousIntArray CONTIGUOUS_INT_ARRAY = new ContiguousIntArray(0, createCache()).maybePreinitializeCache();

   public static ContiguousIntArray makeContiguousIntArray(
      JSDynamicObject object, long length, int[] array, long indexOffset, int arrayOffset, int usedLength, int integrityLevel
   ) {
      ContiguousIntArray arrayType = createContiguousIntArray().setIntegrityLevel(integrityLevel);
      setArrayProperties(object, array, length, usedLength, indexOffset, arrayOffset);
      return arrayType;
   }

   private static ContiguousIntArray createContiguousIntArray() {
      return CONTIGUOUS_INT_ARRAY;
   }

   private ContiguousIntArray(int integrityLevel, DynamicArray.DynamicArrayCache cache) {
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

   public ContiguousDoubleArray toDouble(JSDynamicObject object, long index, double value) {
      int[] array = getArray(object);
      int length = this.lengthInt(object);
      int usedLength = getUsedLength(object);
      int arrayOffset = this.getArrayOffset(object);
      long indexOffset = this.getIndexOffset(object);
      double[] doubleCopy = ArrayCopy.intToDouble(array, arrayOffset, usedLength);
      ContiguousDoubleArray newArray = ContiguousDoubleArray.makeContiguousDoubleArray(
         object, length, doubleCopy, indexOffset, arrayOffset, usedLength, this.integrityLevel
      );
      if (JSConfig.TraceArrayTransitions) {
         traceArrayTransition(this, newArray, index, value);
      }

      return newArray;
   }

   public ContiguousObjectArray toObject(JSDynamicObject object, long index, Object value) {
      int[] array = getArray(object);
      int length = this.lengthInt(object);
      int usedLength = getUsedLength(object);
      int arrayOffset = this.getArrayOffset(object);
      long indexOffset = this.getIndexOffset(object);
      Object[] doubleCopy = ArrayCopy.intToObject(array, arrayOffset, usedLength);
      ContiguousObjectArray newArray = ContiguousObjectArray.makeContiguousObjectArray(
         object, length, doubleCopy, indexOffset, arrayOffset, usedLength, this.integrityLevel
      );
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
      int arrayOffset = this.getArrayOffset(object);
      long indexOffset = this.getIndexOffset(object);
      AbstractWritableArray newArray;
      if (CompilerDirectives.injectBranchProbability(1.0E-4, containsHoleValue(object))) {
         newArray = this.toObjectHoles(object);
      } else {
         newArray = HolesIntArray.makeHolesIntArray(object, length, array, indexOffset, arrayOffset, usedLength, 0, this.integrityLevel);
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
      int arrayOffset = this.getArrayOffset(object);
      long indexOffset = this.getIndexOffset(object);
      return HolesObjectArray.makeHolesObjectArray(object, length, convertToObject(object), indexOffset, arrayOffset, usedLength, 0, this.integrityLevel);
   }

   public ZeroBasedIntArray toNonContiguous(JSDynamicObject object, int index, Object value, ScriptArray.ProfileHolder profile) {
      this.setSupported(object, index, (Integer)value, profile);
      int[] array = getArray(object);
      int length = this.lengthInt(object);
      int usedLength = getUsedLength(object);
      ZeroBasedIntArray newArray = ZeroBasedIntArray.makeZeroBasedIntArray(object, length, usedLength, array, this.integrityLevel);
      if (JSConfig.TraceArrayTransitions) {
         traceArrayTransition(this, newArray, index, value);
      }

      return newArray;
   }

   @Override
   public ScriptArray removeRangeImpl(JSDynamicObject object, long start, long end) {
      return this.removeRangeContiguous(object, start, end);
   }

   protected ContiguousIntArray withIntegrityLevel(int newIntegrityLevel) {
      return new ContiguousIntArray(newIntegrityLevel, this.cache);
   }
}
