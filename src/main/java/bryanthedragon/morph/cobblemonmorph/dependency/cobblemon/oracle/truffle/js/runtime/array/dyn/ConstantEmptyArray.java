package com.oracle.truffle.js.runtime.array.dyn;

import com.oracle.truffle.js.runtime.array.DynamicArray;
import com.oracle.truffle.js.runtime.array.ScriptArray;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;

public final class ConstantEmptyArray extends AbstractConstantEmptyArray {
   private static final ConstantEmptyArray EMPTY_ARRAY = new ConstantEmptyArray(0, createCache()).maybePreinitializeCache();

   public static ConstantEmptyArray createConstantEmptyArray() {
      return EMPTY_ARRAY;
   }

   private ConstantEmptyArray(int integrityLevel, DynamicArray.DynamicArrayCache cache) {
      super(integrityLevel, cache);
   }

   @Override
   public ScriptArray setLengthImpl(JSDynamicObject object, long length, ScriptArray.ProfileHolder profile) {
      setCapacity(object, length);
      return this;
   }

   @Override
   public ScriptArray removeRangeImpl(JSDynamicObject object, long start, long end) {
      setCapacity(object, getCapacity(object) - (end - start));
      return this;
   }

   @Override
   public ScriptArray addRangeImpl(JSDynamicObject object, long offset, int size) {
      setCapacity(object, getCapacity(object) + size);
      return this;
   }

   @Override
   protected DynamicArray withIntegrityLevel(int newIntegrityLevel) {
      return new ConstantEmptyArray(newIntegrityLevel, this.cache);
   }
}
