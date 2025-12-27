package com.oracle.truffle.js.runtime.array.dyn;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.js.runtime.array.DynamicArray;
import com.oracle.truffle.js.runtime.array.ScriptArray;
import com.oracle.truffle.js.runtime.builtins.JSAbstractArray;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.util.Objects;

public abstract class AbstractJSObjectArray extends AbstractWritableArray {
   protected AbstractJSObjectArray(int integrityLevel, DynamicArray.DynamicArrayCache cache) {
      super(integrityLevel, cache);
   }

   @Override
   AbstractWritableArray sameTypeHolesArray(JSDynamicObject object, int length, Object array, long indexOffset, int arrayOffset, int usedLength, int holeCount) {
      return HolesJSObjectArray.makeHolesJSObjectArray(
         object, length, (JSDynamicObject[])array, indexOffset, arrayOffset, usedLength, holeCount, this.integrityLevel
      );
   }

   public abstract void setInBoundsFast(JSDynamicObject object, int index, JSDynamicObject value);

   @Override
   public final ScriptArray setElementImpl(JSDynamicObject object, long index, Object value, boolean strict) {
      assert index >= 0L;

      if (CompilerDirectives.injectBranchProbability(0.9999, JSDynamicObject.isJSDynamicObject(value) && this.isSupported(object, index))) {
         this.setSupported(object, (int)index, (JSDynamicObject)value, ScriptArray.ProfileHolder.empty());
         return this;
      } else {
         return this.rewrite(object, index, value).setElementImpl(object, index, value, strict);
      }
   }

   private ScriptArray rewrite(JSDynamicObject object, long index, Object value) {
      if (this.isSupportedContiguous(object, index)) {
         return this.toContiguous(object, index, value);
      } else {
         return (ScriptArray)(this.isSupportedHoles(object, index) ? this.toHoles(object, index, value) : this.toObject(object, index, value));
      }
   }

   @Override
   public Object getInBoundsFast(JSDynamicObject object, int index) {
      return this.getInBoundsFastJSObject(object, index);
   }

   @Override
   int getArrayLength(Object array) {
      return ((JSDynamicObject[])array).length;
   }

   protected static JSDynamicObject[] getArray(JSDynamicObject object) {
      Object array = JSAbstractArray.arrayGetArray(object);
      if (array.getClass() == JSDynamicObject[].class) {
         return CompilerDirectives.castExact(array, JSDynamicObject[].class);
      } else {
         throw CompilerDirectives.shouldNotReachHere();
      }
   }

   public abstract JSDynamicObject getInBoundsFastJSObject(JSDynamicObject object, int index);

   public final void setInBounds(JSDynamicObject object, int index, JSDynamicObject value, ScriptArray.ProfileHolder profile) {
      getArray(object)[this.prepareInBounds(object, index, profile)] = checkNonNull(value);
   }

   public final void setSupported(JSDynamicObject object, int index, JSDynamicObject value, ScriptArray.ProfileHolder profile) {
      int preparedIndex = this.prepareSupported(object, index, profile);
      getArray(object)[preparedIndex] = checkNonNull(value);
   }

   @Override
   void fillWithHoles(Object array, int fromIndex, int toIndex) {
      JSDynamicObject[] objectArray = (JSDynamicObject[])array;

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
      JSDynamicObject[] newArray = new JSDynamicObject[newCapacity];
      System.arraycopy(getArray(object), 0, newArray, offset, oldCapacity);
      JSAbstractArray.arraySetArray(object, newArray);
   }

   public abstract AbstractJSObjectArray toHoles(JSDynamicObject object, long index, Object value);

   @Override
   public abstract AbstractWritableArray toObject(JSDynamicObject object, long index, Object value);

   @Override
   public final AbstractWritableArray toDouble(JSDynamicObject object, long index, double value) {
      return this;
   }

   @Override
   public ScriptArray deleteElementImpl(JSDynamicObject object, long index, boolean strict) {
      return this.toHoles(object, index, null).deleteElementImpl(object, index, strict);
   }

   @Override
   protected final void moveRangePrepared(JSDynamicObject object, int src, int dst, int len) {
      JSDynamicObject[] array = getArray(object);
      System.arraycopy(array, src, array, dst, len);
   }

   @Override
   public final Object allocateArray(int length) {
      return new JSDynamicObject[length];
   }

   @Override
   public Object cloneArray(JSDynamicObject object) {
      return getArray(object).clone();
   }

   protected abstract AbstractJSObjectArray withIntegrityLevel(int newIntegrityLevel);

   protected static JSDynamicObject checkNonNull(JSDynamicObject value) {
      assert value != null;

      return value;
   }

   protected JSDynamicObject castNonNull(JSDynamicObject value) {
      return Objects.requireNonNull(value);
   }
}
