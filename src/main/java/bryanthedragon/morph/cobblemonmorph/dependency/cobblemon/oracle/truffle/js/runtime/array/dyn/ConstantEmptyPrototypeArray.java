package com.oracle.truffle.js.runtime.array.dyn;

import com.oracle.truffle.api.Assumption;
import com.oracle.truffle.js.runtime.array.DynamicArray;
import com.oracle.truffle.js.runtime.array.ScriptArray;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;

public final class ConstantEmptyPrototypeArray extends AbstractConstantEmptyArray {
   private static final ConstantEmptyPrototypeArray CONSTANT_EMPTY_PROTOTYPE_ARRAY = new ConstantEmptyPrototypeArray(0, createCache())
      .maybePreinitializeCache();

   public static ScriptArray createConstantEmptyPrototypeArray() {
      return CONSTANT_EMPTY_PROTOTYPE_ARRAY;
   }

   private ConstantEmptyPrototypeArray(int integrityLevel, DynamicArray.DynamicArrayCache cache) {
      super(integrityLevel, cache);
   }

   private static Assumption getArrayPrototypeNoElementsAssumption(JSDynamicObject object) {
      return JSObject.getJSContext(object).getArrayPrototypeNoElementsAssumption();
   }

   @Override
   public ScriptArray setLengthImpl(JSDynamicObject object, long length, ScriptArray.ProfileHolder profile) {
      setCapacity(object, length);
      return this;
   }

   @Override
   public AbstractIntArray createWriteableInt(JSDynamicObject object, long index, int value, ScriptArray.ProfileHolder profile) {
      getArrayPrototypeNoElementsAssumption(object).invalidate("Array.prototype no element assumption");
      return super.createWriteableInt(object, index, value, profile);
   }

   @Override
   public AbstractDoubleArray createWriteableDouble(JSDynamicObject object, long index, double value, ScriptArray.ProfileHolder profile) {
      getArrayPrototypeNoElementsAssumption(object).invalidate("Array.prototype no element assumption");
      return super.createWriteableDouble(object, index, value, profile);
   }

   @Override
   public AbstractJSObjectArray createWriteableJSObject(JSDynamicObject object, long index, JSDynamicObject value, ScriptArray.ProfileHolder profile) {
      getArrayPrototypeNoElementsAssumption(object).invalidate("Array.prototype no element assumption");
      return super.createWriteableJSObject(object, index, value, profile);
   }

   @Override
   public AbstractObjectArray createWriteableObject(JSDynamicObject object, long index, Object value, ScriptArray.ProfileHolder profile) {
      getArrayPrototypeNoElementsAssumption(object).invalidate("Array.prototype no element assumption");
      return super.createWriteableObject(object, index, value, profile);
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
      return new ConstantEmptyPrototypeArray(newIntegrityLevel, this.cache);
   }
}
