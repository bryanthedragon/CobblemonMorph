package com.oracle.truffle.js.runtime.array.dyn;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.array.DynamicArray;
import com.oracle.truffle.js.runtime.array.ScriptArray;
import com.oracle.truffle.js.runtime.builtins.JSAbstractArray;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;

public abstract class AbstractDoubleArray extends AbstractWritableArray {
   protected AbstractDoubleArray(int integrityLevel, DynamicArray.DynamicArrayCache cache) {
      super(integrityLevel, cache);
   }

   @Override
   AbstractWritableArray sameTypeHolesArray(JSDynamicObject object, int length, Object array, long indexOffset, int arrayOffset, int usedLength, int holeCount) {
      return HolesDoubleArray.makeHolesDoubleArray(object, length, (double[])array, indexOffset, arrayOffset, usedLength, holeCount, this.integrityLevel);
   }

   public abstract void setInBoundsFast(JSDynamicObject object, int index, double value);

   @Override
   public final ScriptArray setElementImpl(JSDynamicObject object, long index, Object value, boolean strict) {
      assert index >= 0L;

      if (CompilerDirectives.injectBranchProbability(0.9999, (value instanceof Integer || value instanceof Double) && this.isSupported(object, (int)index))) {
         double doubleValue = JSRuntime.doubleValue((Number)value);

         assert !HolesDoubleArray.isHoleValue(doubleValue);

         this.setSupported(object, (int)index, doubleValue, ScriptArray.ProfileHolder.empty());
         return this;
      } else {
         return this.rewrite(object, index, value).setElementImpl(object, index, value, strict);
      }
   }

   private ScriptArray rewrite(JSDynamicObject object, long index, Object value) {
      if (!(value instanceof Integer) && !(value instanceof Double)) {
         return this.toObject(object, index, value);
      } else if (this.isSupportedContiguous(object, index)) {
         return this.toContiguous(object, index, value);
      } else {
         return (ScriptArray)(this.isSupportedHoles(object, index) ? this.toHoles(object, index, value) : this.toSparse(object, index, value));
      }
   }

   @Override
   public Object getInBoundsFast(JSDynamicObject object, int index) {
      return this.getInBoundsFastDouble(object, index);
   }

   @Override
   public abstract double getInBoundsFastDouble(JSDynamicObject object, int index);

   @Override
   int getArrayLength(Object array) {
      return ((double[])array).length;
   }

   protected static double[] getArray(JSDynamicObject object) {
      Object array = JSAbstractArray.arrayGetArray(object);
      if (array.getClass() == double[].class) {
         return (double[])array;
      } else {
         throw CompilerDirectives.shouldNotReachHere();
      }
   }

   public final void setInBounds(JSDynamicObject object, int index, double value, ScriptArray.ProfileHolder profile) {
      getArray(object)[this.prepareInBounds(object, index, profile)] = value;
   }

   public final void setSupported(JSDynamicObject object, int index, double value, ScriptArray.ProfileHolder profile) {
      int preparedIndex = this.prepareSupported(object, index, profile);
      getArray(object)[preparedIndex] = value;
   }

   @Override
   void fillWithHoles(Object array, int fromIndex, int toIndex) {
      double[] doubleArray = (double[])array;

      for (int i = fromIndex; i < toIndex; i++) {
         doubleArray[i] = HolesDoubleArray.HOLE_VALUE_DOUBLE;
      }
   }

   @Override
   protected final void setHoleValue(JSDynamicObject object, int preparedIndex) {
      getArray(object)[preparedIndex] = HolesDoubleArray.HOLE_VALUE_DOUBLE;
   }

   @Override
   protected final boolean isHolePrepared(JSDynamicObject object, int preparedIndex) {
      return HolesDoubleArray.isHoleValue(getArray(object)[preparedIndex]);
   }

   @Override
   protected final int getArrayCapacity(JSDynamicObject object) {
      return getArray(object).length;
   }

   @Override
   protected final void resizeArray(JSDynamicObject object, int newCapacity, int oldCapacity, int offset) {
      double[] newArray = new double[newCapacity];
      System.arraycopy(getArray(object), 0, newArray, offset, oldCapacity);
      JSAbstractArray.arraySetArray(object, newArray);
   }

   public abstract AbstractDoubleArray toHoles(JSDynamicObject object, long index, Object value);

   @Override
   public final AbstractWritableArray toDouble(JSDynamicObject object, long index, double value) {
      return this;
   }

   @Override
   public ScriptArray deleteElementImpl(JSDynamicObject object, long index, boolean strict) {
      return this.toHoles(object, index, 9221120237041090561L).deleteElementImpl(object, index, strict);
   }

   @Override
   protected final void moveRangePrepared(JSDynamicObject object, int src, int dst, int len) {
      double[] array = getArray(object);
      System.arraycopy(array, src, array, dst, len);
   }

   @Override
   public final Object allocateArray(int length) {
      return new double[length];
   }

   @Override
   public Object cloneArray(JSDynamicObject object) {
      return getArray(object).clone();
   }

   protected abstract AbstractDoubleArray withIntegrityLevel(int newIntegrityLevel);
}
