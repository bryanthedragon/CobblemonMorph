package com.oracle.truffle.js.runtime.array.dyn;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.js.runtime.array.DynamicArray;
import com.oracle.truffle.js.runtime.array.ScriptArray;
import com.oracle.truffle.js.runtime.builtins.JSAbstractArray;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.util.Objects;

public abstract class AbstractObjectArray extends AbstractWritableArray {
   protected AbstractObjectArray(int integrityLevel, DynamicArray.DynamicArrayCache cache) {
      super(integrityLevel, cache);
   }

   @Override
   AbstractWritableArray sameTypeHolesArray(JSDynamicObject object, int length, Object array, long indexOffset, int arrayOffset, int usedLength, int holeCount) {
      return HolesObjectArray.makeHolesObjectArray(object, length, (Object[])array, indexOffset, arrayOffset, usedLength, holeCount, this.integrityLevel);
   }

   public abstract void setInBoundsFast(JSDynamicObject object, int index, Object value);

   @Override
   public final ScriptArray setElementImpl(JSDynamicObject object, long index, Object value, boolean strict) {
      assert index >= 0L;

      if (CompilerDirectives.injectBranchProbability(0.9999, this.isSupported(object, index))) {
         assert value != null;

         this.setSupported(object, (int)index, value);
         return this;
      } else {
         return this.rewrite(object, index, value).setElementImpl(object, index, value, strict);
      }
   }

   private ScriptArray rewrite(JSDynamicObject object, long index, Object value) {
      if (this.isSupportedContiguous(object, index)) {
         return this.toContiguous(object, index, value);
      } else {
         return (ScriptArray)(this.isSupportedHoles(object, index) ? this.toHoles(object, index, value) : this.toSparse(object, index, value));
      }
   }

   @Override
   public Object getInBoundsFast(JSDynamicObject object, int index) {
      return this.getInBoundsFastObject(object, index);
   }

   @Override
   int getArrayLength(Object array) {
      return ((Object[])array).length;
   }

   protected static Object[] getArray(JSDynamicObject object) {
      Object array = JSAbstractArray.arrayGetArray(object);
      if (array.getClass() == Object[].class) {
         return CompilerDirectives.castExact(array, Object[].class);
      } else {
         throw CompilerDirectives.shouldNotReachHere();
      }
   }

   public abstract Object getInBoundsFastObject(JSDynamicObject object, int index);

   public final void setInBounds(JSDynamicObject object, int index, Object value, ScriptArray.ProfileHolder profile) {
      getArray(object)[this.prepareInBounds(object, index, profile)] = checkNonNull(value);
   }

   public final void setSupported(JSDynamicObject object, int index, Object value) {
      int preparedIndex = this.prepareSupported(object, index, ScriptArray.ProfileHolder.empty());
      getArray(object)[preparedIndex] = checkNonNull(value);
   }

   @Override
   void fillWithHoles(Object array, int fromIndex, int toIndex) {
      Object[] objectArray = (Object[])array;

      for (int i = fromIndex; i < toIndex; i++) {
         objectArray[i] = null;
      }
   }

   @Override
   protected final void setHoleValue(JSDynamicObject object, int preparedIndex) {
      getArray(object)[preparedIndex] = null;
   }

   @Override
   protected final void fillHoles(JSDynamicObject object, int internalIndex, int grown, ScriptArray.ProfileHolder profile) {
      if (grown != 0) {
         this.incrementHolesCount(object, Math.abs(grown) - 1);
      }
   }

   @Override
   protected final boolean isHolePrepared(JSDynamicObject object, int preparedIndex) {
      return HolesObjectArray.isHoleValue(getArray(object)[preparedIndex]);
   }

   @Override
   protected final int getArrayCapacity(JSDynamicObject object) {
      return getArray(object).length;
   }

   @Override
   protected final void resizeArray(JSDynamicObject object, int newCapacity, int oldCapacity, int offset) {
      Object[] newArray = new Object[newCapacity];
      System.arraycopy(getArray(object), 0, newArray, offset, oldCapacity);
      JSAbstractArray.arraySetArray(object, newArray);
   }

   public abstract AbstractObjectArray toHoles(JSDynamicObject object, long index, Object value);

   @Override
   public final AbstractWritableArray toDouble(JSDynamicObject object, long index, double value) {
      return this;
   }

   @Override
   public final AbstractWritableArray toObject(JSDynamicObject object, long index, Object value) {
      return this;
   }

   @Override
   public ScriptArray deleteElementImpl(JSDynamicObject object, long index, boolean strict) {
      return this.toHoles(object, index, null).deleteElementImpl(object, index, strict);
   }

   @Override
   protected final void moveRangePrepared(JSDynamicObject object, int src, int dst, int len) {
      Object[] array = getArray(object);
      System.arraycopy(array, src, array, dst, len);
   }

   @Override
   public final Object allocateArray(int length) {
      return new Object[length];
   }

   @Override
   public Object cloneArray(JSDynamicObject object) {
      return getArray(object).clone();
   }

   protected abstract AbstractObjectArray withIntegrityLevel(int newIntegrityLevel);

   protected static Object checkNonNull(Object value) {
      assert value != null;

      return value;
   }

   protected Object castNonNull(Object value) {
      return Objects.requireNonNull(value);
   }
}
