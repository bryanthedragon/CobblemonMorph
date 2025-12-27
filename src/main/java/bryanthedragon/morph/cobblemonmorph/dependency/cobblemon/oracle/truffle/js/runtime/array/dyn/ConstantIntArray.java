package com.oracle.truffle.js.runtime.array.dyn;

import com.oracle.truffle.js.runtime.JSConfig;
import com.oracle.truffle.js.runtime.array.DynamicArray;
import com.oracle.truffle.js.runtime.array.ScriptArray;
import com.oracle.truffle.js.runtime.builtins.JSAbstractArray;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;

public final class ConstantIntArray extends AbstractConstantArray {
   private static final ConstantIntArray CONSTANT_INT_ARRAY = new ConstantIntArray(0, createCache()).maybePreinitializeCache();

   public static ConstantIntArray createConstantIntArray() {
      return CONSTANT_INT_ARRAY;
   }

   private ConstantIntArray(int integrityLevel, DynamicArray.DynamicArrayCache cache) {
      super(integrityLevel, cache);
   }

   @Override
   public Object getElementInBounds(JSDynamicObject object, int index) {
      return getElementInt(object, index);
   }

   public static int getElementInt(JSDynamicObject object, int index) {
      return getArray(object)[index];
   }

   private static int[] getArray(JSDynamicObject object) {
      return (int[])JSAbstractArray.arrayGetArray(object);
   }

   @Override
   public int lengthInt(JSDynamicObject object) {
      return getArray(object).length;
   }

   @Override
   public boolean hasElement(JSDynamicObject object, long index) {
      return index >= 0L && index < getArray(object).length;
   }

   @Override
   public Object cloneArray(JSDynamicObject object) {
      return getArray(object);
   }

   @Override
   public ScriptArray deleteElementImpl(JSDynamicObject object, long index, boolean strict) {
      return this.createWriteableInt(object, index, Integer.MIN_VALUE, ScriptArray.ProfileHolder.empty()).deleteElementImpl(object, index, strict);
   }

   @Override
   public ScriptArray setLengthImpl(JSDynamicObject object, long length, ScriptArray.ProfileHolder profile) {
      return this.createWriteableInt(object, length - 1L, Integer.MIN_VALUE, ScriptArray.ProfileHolder.empty()).setLengthImpl(object, length, profile);
   }

   public AbstractIntArray createWriteableInt(JSDynamicObject object, long index, int value, ScriptArray.ProfileHolder profile) {
      int[] copyArray = ArrayCopy.intToInt(getArray(object));
      ZeroBasedIntArray newArray = ZeroBasedIntArray.makeZeroBasedIntArray(object, copyArray.length, copyArray.length, copyArray, this.integrityLevel);
      if (JSConfig.TraceArrayTransitions) {
         traceArrayTransition(this, newArray, index, value);
      }

      return newArray;
   }

   @Override
   public AbstractWritableArray createWriteableDouble(JSDynamicObject object, long index, double value, ScriptArray.ProfileHolder profile) {
      double[] copyArray = ArrayCopy.intToDouble(getArray(object));
      ZeroBasedDoubleArray newArray = ZeroBasedDoubleArray.makeZeroBasedDoubleArray(object, copyArray.length, copyArray.length, copyArray, this.integrityLevel);
      if (JSConfig.TraceArrayTransitions) {
         traceArrayTransition(this, newArray, index, value);
      }

      return newArray;
   }

   @Override
   public AbstractWritableArray createWriteableJSObject(JSDynamicObject object, long index, JSDynamicObject value, ScriptArray.ProfileHolder profile) {
      return this.createWriteableObject(object, index, value, ScriptArray.ProfileHolder.empty());
   }

   @Override
   public AbstractWritableArray createWriteableObject(JSDynamicObject object, long index, Object value, ScriptArray.ProfileHolder profile) {
      Object[] copyArray = ArrayCopy.intToObject(getArray(object));
      ZeroBasedObjectArray newArray = ZeroBasedObjectArray.makeZeroBasedObjectArray(object, copyArray.length, copyArray.length, copyArray, this.integrityLevel);
      if (JSConfig.TraceArrayTransitions) {
         traceArrayTransition(this, newArray, index, value);
      }

      return newArray;
   }

   @Override
   public ScriptArray removeRangeImpl(JSDynamicObject object, long start, long end) {
      int[] array = getArray(object);
      if (array.length - (end - start) == 0L) {
         AbstractConstantEmptyArray.setCapacity(object, 0L);
      } else {
         int[] newArray = new int[array.length - (int)(end - start)];
         System.arraycopy(array, 0, newArray, 0, (int)start);
         System.arraycopy(array, (int)end, newArray, (int)start, (int)(array.length - end));
         JSAbstractArray.arraySetArray(object, newArray);
      }

      return this;
   }

   @Override
   public ScriptArray addRangeImpl(JSDynamicObject object, long offset, int size) {
      int[] array = getArray(object);
      if (array.length == 0) {
         AbstractConstantEmptyArray.setCapacity(object, size);
         return this;
      } else {
         int[] newArray = new int[array.length + size];
         System.arraycopy(array, 0, newArray, 0, (int)offset);
         System.arraycopy(array, (int)offset, newArray, (int)offset + size, (int)(array.length - offset));
         JSAbstractArray.arraySetArray(object, newArray);
         return this;
      }
   }

   @Override
   protected DynamicArray withIntegrityLevel(int newIntegrityLevel) {
      return new ConstantIntArray(newIntegrityLevel, this.cache);
   }
}
