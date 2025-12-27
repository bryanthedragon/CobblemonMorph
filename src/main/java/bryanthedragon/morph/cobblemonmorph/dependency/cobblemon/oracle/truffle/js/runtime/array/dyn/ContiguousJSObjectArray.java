package com.oracle.truffle.js.runtime.array.dyn;

import com.oracle.truffle.js.runtime.JSConfig;
import com.oracle.truffle.js.runtime.array.DynamicArray;
import com.oracle.truffle.js.runtime.array.ScriptArray;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;

public final class ContiguousJSObjectArray extends AbstractContiguousJSObjectArray {
   private static final ContiguousJSObjectArray CONTIGUOUS_JSOBJECT_ARRAY = new ContiguousJSObjectArray(0, createCache()).maybePreinitializeCache();

   public static ContiguousJSObjectArray makeContiguousJSObjectArray(
      JSDynamicObject object, long length, JSDynamicObject[] array, long indexOffset, int arrayOffset, int usedLength, int integrityLevel
   ) {
      ContiguousJSObjectArray arrayType = createContiguousJSObjectArray().setIntegrityLevel(integrityLevel);
      setArrayProperties(object, array, length, usedLength, indexOffset, arrayOffset);
      return arrayType;
   }

   private static ContiguousJSObjectArray createContiguousJSObjectArray() {
      return CONTIGUOUS_JSOBJECT_ARRAY;
   }

   private ContiguousJSObjectArray(int integrityLevel, DynamicArray.DynamicArrayCache cache) {
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

   public HolesJSObjectArray toHoles(JSDynamicObject object, long index, Object value) {
      JSDynamicObject[] array = getArray(object);
      int length = this.lengthInt(object);
      int usedLength = getUsedLength(object);
      int arrayOffset = this.getArrayOffset(object);
      long indexOffset = this.getIndexOffset(object);
      HolesJSObjectArray newArray = HolesJSObjectArray.makeHolesJSObjectArray(
         object, length, array, indexOffset, arrayOffset, usedLength, 0, this.integrityLevel
      );
      if (JSConfig.TraceArrayTransitions) {
         traceArrayTransition(this, newArray, index, value);
      }

      return newArray;
   }

   public ZeroBasedJSObjectArray toNonContiguous(JSDynamicObject object, int index, Object value, ScriptArray.ProfileHolder profile) {
      this.setSupported(object, index, (JSDynamicObject)value, profile);
      JSDynamicObject[] array = getArray(object);
      int length = this.lengthInt(object);
      int usedLength = getUsedLength(object);
      ZeroBasedJSObjectArray newArray = ZeroBasedJSObjectArray.makeZeroBasedJSObjectArray(object, length, usedLength, array, this.integrityLevel);
      if (JSConfig.TraceArrayTransitions) {
         traceArrayTransition(this, newArray, index, value);
      }

      return newArray;
   }

   public ContiguousObjectArray toObject(JSDynamicObject object, long index, Object value) {
      JSDynamicObject[] array = getArray(object);
      int length = this.lengthInt(object);
      int usedLength = getUsedLength(object);
      int arrayOffset = this.getArrayOffset(object);
      long indexOffset = this.getIndexOffset(object);
      Object[] doubleCopy = ArrayCopy.jsobjectToObject(array, arrayOffset, usedLength);
      ContiguousObjectArray newArray = ContiguousObjectArray.makeContiguousObjectArray(
         object, length, doubleCopy, indexOffset, arrayOffset, usedLength, this.integrityLevel
      );
      if (JSConfig.TraceArrayTransitions) {
         traceArrayTransition(this, newArray, index, value);
      }

      return newArray;
   }

   @Override
   public ScriptArray removeRangeImpl(JSDynamicObject object, long start, long end) {
      return this.removeRangeContiguous(object, start, end);
   }

   protected ContiguousJSObjectArray withIntegrityLevel(int newIntegrityLevel) {
      return new ContiguousJSObjectArray(newIntegrityLevel, this.cache);
   }
}
