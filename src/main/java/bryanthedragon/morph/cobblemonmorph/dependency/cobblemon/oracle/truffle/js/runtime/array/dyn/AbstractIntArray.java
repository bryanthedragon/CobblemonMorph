package com.oracle.truffle.js.runtime.array.dyn;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.js.runtime.array.DynamicArray;
import com.oracle.truffle.js.runtime.array.ScriptArray;
import com.oracle.truffle.js.runtime.builtins.JSAbstractArray;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;

public abstract class AbstractIntArray extends AbstractWritableArray {
   protected AbstractIntArray(int integrityLevel, DynamicArray.DynamicArrayCache cache) {
      super(integrityLevel, cache);
   }

   @Override
   AbstractWritableArray sameTypeHolesArray(JSDynamicObject object, int length, Object array, long indexOffset, int arrayOffset, int usedLength, int holeCount) {
      return HolesIntArray.makeHolesIntArray(object, length, (int[])array, indexOffset, arrayOffset, usedLength, holeCount, this.integrityLevel);
   }

   @Override
   public final ScriptArray setElementImpl(JSDynamicObject object, long index, Object value, boolean strict) {
      assert index >= 0L;

      if (CompilerDirectives.injectBranchProbability(0.9999, value instanceof Integer && this.isSupported(object, index))) {
         int intValue = (Integer)value;
         if (CompilerDirectives.injectBranchProbability(1.0E-4, intValue == Integer.MIN_VALUE)) {
            return this.toObject(object, index, value).setElementImpl(object, index, value, strict);
         } else {
            this.setSupported(object, (int)index, intValue, ScriptArray.ProfileHolder.empty());
            return this;
         }
      } else {
         return this.rewrite(object, index, value).setElementImpl(object, index, value, strict);
      }
   }

   private ScriptArray rewrite(JSDynamicObject object, long index, Object value) {
      if (value instanceof Integer) {
         if (this.isSupportedContiguous(object, index)) {
            return this.toContiguous(object, index, value);
         } else {
            return (ScriptArray)(this.isSupportedHoles(object, index) ? this.toHoles(object, index, value) : this.toSparse(object, index, value));
         }
      } else {
         return value instanceof Double ? this.toDouble(object, index, (Double)value) : this.toObject(object, index, value);
      }
   }

   @Override
   public Object getInBoundsFast(JSDynamicObject object, int index) {
      return this.getInBoundsFastInt(object, index);
   }

   @Override
   int getArrayLength(Object array) {
      return ((int[])array).length;
   }

   protected static int[] getArray(JSDynamicObject object) {
      Object array = JSAbstractArray.arrayGetArray(object);
      if (array.getClass() == int[].class) {
         return (int[])array;
      } else {
         throw CompilerDirectives.shouldNotReachHere();
      }
   }

   @Override
   public abstract int getInBoundsFastInt(JSDynamicObject object, int index);

   public abstract void setInBoundsFast(JSDynamicObject object, int index, int value);

   public final void setInBounds(JSDynamicObject object, int index, int value, ScriptArray.ProfileHolder profile) {
      getArray(object)[this.prepareInBounds(object, index, profile)] = value;
   }

   public final void setSupported(JSDynamicObject object, int index, int value, ScriptArray.ProfileHolder profile) {
      int preparedIndex = this.prepareSupported(object, index, profile);
      getArray(object)[preparedIndex] = value;
   }

   @Override
   void fillWithHoles(Object array, int fromIndex, int toIndex) {
      int[] intArray = (int[])array;

      for (int i = fromIndex; i < toIndex; i++) {
         intArray[i] = Integer.MIN_VALUE;
      }
   }

   @Override
   protected final void setHoleValue(JSDynamicObject object, int preparedIndex) {
      getArray(object)[preparedIndex] = Integer.MIN_VALUE;
   }

   @Override
   protected final boolean isHolePrepared(JSDynamicObject object, int preparedIndex) {
      return HolesIntArray.isHoleValue(getArray(object)[preparedIndex]);
   }

   @Override
   protected final int getArrayCapacity(JSDynamicObject object) {
      return getArray(object).length;
   }

   @Override
   protected final void resizeArray(JSDynamicObject object, int newCapacity, int oldCapacity, int offset) {
      int[] newArray = new int[newCapacity];
      System.arraycopy(getArray(object), 0, newArray, offset, oldCapacity);
      JSAbstractArray.arraySetArray(object, newArray);
   }

   @Override
   public abstract AbstractWritableArray toHoles(JSDynamicObject object, long index, Object value);

   @Override
   public ScriptArray deleteElementImpl(JSDynamicObject object, long index, boolean strict) {
      return this.toHoles(object, index, Integer.MIN_VALUE).deleteElementImpl(object, index, strict);
   }

   protected abstract HolesObjectArray toObjectHoles(JSDynamicObject object);

   protected static Object[] convertToObject(JSDynamicObject object) {
      int[] array = getArray(object);
      int usedLength = getUsedLength(object);
      int arrayOffset = JSAbstractArray.arrayGetArrayOffset(object);
      Object[] obj = new Object[array.length];

      for (int i = arrayOffset; i < arrayOffset + usedLength; i++) {
         obj[i] = array[i];
      }

      return obj;
   }

   protected static boolean containsHoleValue(JSDynamicObject object) {
      int[] array = getArray(object);
      int usedLength = getUsedLength(object);

      for (int i = 0; i < usedLength; i++) {
         if (array[i] == Integer.MIN_VALUE) {
            return true;
         }
      }

      return false;
   }

   @Override
   protected final void moveRangePrepared(JSDynamicObject object, int src, int dst, int len) {
      int[] array = getArray(object);
      System.arraycopy(array, src, array, dst, len);
   }

   @Override
   public final Object allocateArray(int length) {
      return new int[length];
   }

   @Override
   public Object cloneArray(JSDynamicObject object) {
      return getArray(object).clone();
   }

   protected abstract AbstractIntArray withIntegrityLevel(int newIntegrityLevel);
}
