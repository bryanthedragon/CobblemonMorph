package com.oracle.truffle.js.runtime.array.dyn;

import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSConfig;
import com.oracle.truffle.js.runtime.array.DynamicArray;
import com.oracle.truffle.js.runtime.array.ScriptArray;
import com.oracle.truffle.js.runtime.builtins.JSAbstractArray;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.Undefined;
import java.util.List;

public final class HolesObjectArray extends AbstractContiguousObjectArray {
   private static final HolesObjectArray HOLES_OBJECT_ARRAY = new HolesObjectArray(0, createCache()).maybePreinitializeCache();

   private HolesObjectArray(int integrityLevel, DynamicArray.DynamicArrayCache cache) {
      super(integrityLevel, cache);
   }

   public static HolesObjectArray makeHolesObjectArray(
      JSDynamicObject object, int length, Object[] array, long indexOffset, int arrayOffset, int usedLength, int holeCount, int integrityLevel
   ) {
      HolesObjectArray arrayType = createHolesObjectArray().setIntegrityLevel(integrityLevel);
      setArrayProperties(object, array, length, usedLength, indexOffset, arrayOffset);
      JSAbstractArray.arraySetHoleCount(object, holeCount);

      assert holeCount == arrayType.countHoles(object) : String.format(
         "holeCount, %d, differs from the actual count, %d", holeCount, arrayType.countHoles(object)
      );

      return arrayType;
   }

   public static HolesObjectArray createHolesObjectArray() {
      return HOLES_OBJECT_ARRAY;
   }

   @Override
   AbstractWritableArray sameTypeHolesArray(JSDynamicObject object, int length, Object array, long indexOffset, int arrayOffset, int usedLength, int holeCount) {
      setArrayProperties(object, array, length, usedLength, indexOffset, arrayOffset);
      JSAbstractArray.arraySetHoleCount(object, holeCount);
      return this;
   }

   @Override
   public void setInBoundsFast(JSDynamicObject object, int index, Object value) {
      throw Errors.shouldNotReachHere("should not call this method, use setInBounds(Non)Hole");
   }

   public boolean isHoleFast(JSDynamicObject object, int index) {
      int internalIndex = (int)(index - this.getIndexOffset(object));
      return this.isHolePrepared(object, internalIndex);
   }

   public void setInBoundsFastHole(JSDynamicObject object, int index, Object value) {
      int internalIndex = (int)(index - this.getIndexOffset(object));

      assert this.isHolePrepared(object, internalIndex);

      this.incrementHolesCount(object, -1);
      this.setInBoundsFastIntl(object, index, internalIndex, value);
   }

   public void setInBoundsFastNonHole(JSDynamicObject object, int index, Object value) {
      int internalIndex = (int)(index - this.getIndexOffset(object));

      assert !this.isHolePrepared(object, internalIndex);

      this.setInBoundsFastIntl(object, index, internalIndex, checkNonNull(value));
   }

   private void setInBoundsFastIntl(JSDynamicObject object, int index, int internalIndex, Object value) {
      getArray(object)[internalIndex] = value;
   }

   @Override
   public boolean containsHoles(JSDynamicObject object, long index) {
      return JSAbstractArray.arrayGetHoleCount(object) > 0 || !this.isInBoundsFast(object, index);
   }

   public AbstractObjectArray toNonHoles(JSDynamicObject object, long index, Object value) {
      assert !this.containsHoles(object, index);

      Object[] array = getArray(object);
      int length = this.lengthInt(object);
      int usedLength = getUsedLength(object);
      int arrayOffset = this.getArrayOffset(object);
      long indexOffset = this.getIndexOffset(object);
      this.setInBoundsFastNonHole(object, (int)index, value);
      AbstractObjectArray newArray;
      if (indexOffset == 0L && arrayOffset == 0) {
         newArray = ZeroBasedObjectArray.makeZeroBasedObjectArray(object, length, usedLength, array, this.integrityLevel);
      } else {
         newArray = ContiguousObjectArray.makeContiguousObjectArray(object, length, array, indexOffset, arrayOffset, usedLength, this.integrityLevel);
      }

      if (JSConfig.TraceArrayTransitions) {
         traceArrayTransition(this, newArray, index, value);
      }

      return newArray;
   }

   @Override
   protected void incrementHolesCount(JSDynamicObject object, int offset) {
      JSAbstractArray.arraySetHoleCount(object, JSAbstractArray.arrayGetHoleCount(object) + offset);
   }

   @Override
   public int prepareInBounds(JSDynamicObject object, int index, ScriptArray.ProfileHolder profile) {
      return this.prepareInBoundsHoles(object, index, profile);
   }

   @Override
   public boolean isSupported(JSDynamicObject object, long index) {
      return this.isSupportedHoles(object, index);
   }

   @Override
   public int prepareSupported(JSDynamicObject object, int index, ScriptArray.ProfileHolder profile) {
      return this.prepareSupportedHoles(object, index, profile);
   }

   @Override
   public Object getInBoundsFast(JSDynamicObject object, int index) {
      Object value = this.getInBoundsFastObject(object, index);
      return isHoleValue(value) ? Undefined.instance : value;
   }

   public HolesObjectArray toHoles(JSDynamicObject object, long index, Object value) {
      return this;
   }

   public static boolean isHoleValue(Object value) {
      return value == null;
   }

   @Override
   public long nextElementIndex(JSDynamicObject object, long index0) {
      return this.nextElementIndexHoles(object, index0);
   }

   @Override
   public long previousElementIndex(JSDynamicObject object, long index0) {
      return this.previousElementIndexHoles(object, index0);
   }

   @Override
   public boolean hasElement(JSDynamicObject object, long index) {
      return super.hasElement(object, index) && !this.isHolePrepared(object, this.prepareInBoundsFast(object, (int)index));
   }

   @Override
   public ScriptArray deleteElementImpl(JSDynamicObject object, long index, boolean strict) {
      return this.deleteElementHoles(object, index);
   }

   @Override
   public boolean isHolesType() {
      return true;
   }

   @Override
   public ScriptArray removeRangeImpl(JSDynamicObject object, long start, long end) {
      return this.removeRangeHoles(object, start, end);
   }

   @Override
   protected Object castNonNull(Object value) {
      return value;
   }

   protected HolesObjectArray withIntegrityLevel(int newIntegrityLevel) {
      return new HolesObjectArray(newIntegrityLevel, this.cache);
   }

   @Override
   public List<Object> ownPropertyKeys(JSDynamicObject object) {
      return this.ownPropertyKeysHoles(object);
   }
}
