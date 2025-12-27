package com.oracle.truffle.js.runtime.array.dyn;

import com.oracle.truffle.js.runtime.JSConfig;
import com.oracle.truffle.js.runtime.array.DynamicArray;
import com.oracle.truffle.js.runtime.array.ScriptArray;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;

public final class ContiguousObjectArray extends AbstractContiguousObjectArray {
   private static final ContiguousObjectArray CONTIGUOUS_OBJECT_ARRAY = new ContiguousObjectArray(0, createCache()).maybePreinitializeCache();

   public static ContiguousObjectArray makeContiguousObjectArray(
      JSDynamicObject object, long length, Object[] array, long indexOffset, int arrayOffset, int usedLength, int integrityLevel
   ) {
      ContiguousObjectArray arrayType = createContiguousObjectArray().setIntegrityLevel(integrityLevel);
      setArrayProperties(object, array, length, usedLength, indexOffset, arrayOffset);
      return arrayType;
   }

   private static ContiguousObjectArray createContiguousObjectArray() {
      return CONTIGUOUS_OBJECT_ARRAY;
   }

   private ContiguousObjectArray(int integrityLevel, DynamicArray.DynamicArrayCache cache) {
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

   public HolesObjectArray toHoles(JSDynamicObject object, long index, Object value) {
      Object[] array = getArray(object);
      int length = this.lengthInt(object);
      int usedLength = getUsedLength(object);
      int arrayOffset = this.getArrayOffset(object);
      long indexOffset = this.getIndexOffset(object);
      HolesObjectArray newArray = HolesObjectArray.makeHolesObjectArray(object, length, array, indexOffset, arrayOffset, usedLength, 0, this.integrityLevel);
      if (JSConfig.TraceArrayTransitions) {
         traceArrayTransition(this, newArray, index, value);
      }

      return newArray;
   }

   public ZeroBasedObjectArray toNonContiguous(JSDynamicObject object, int index, Object value, ScriptArray.ProfileHolder profile) {
      this.setSupported(object, index, value);
      Object[] array = getArray(object);
      int length = this.lengthInt(object);
      int usedLength = getUsedLength(object);
      ZeroBasedObjectArray newArray = ZeroBasedObjectArray.makeZeroBasedObjectArray(object, length, usedLength, array, this.integrityLevel);
      if (JSConfig.TraceArrayTransitions) {
         traceArrayTransition(this, newArray, index, value);
      }

      return newArray;
   }

   @Override
   public ScriptArray removeRangeImpl(JSDynamicObject object, long start, long end) {
      return this.removeRangeContiguous(object, start, end);
   }

   protected ContiguousObjectArray withIntegrityLevel(int newIntegrityLevel) {
      return new ContiguousObjectArray(newIntegrityLevel, this.cache);
   }
}
