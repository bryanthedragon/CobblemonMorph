package com.oracle.truffle.js.runtime.array.dyn;

import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSConfig;
import com.oracle.truffle.js.runtime.array.DynamicArray;
import com.oracle.truffle.js.runtime.array.ScriptArray;
import com.oracle.truffle.js.runtime.builtins.JSAbstractArray;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.Undefined;
import java.util.List;

public final class HolesIntArray extends AbstractContiguousIntArray {
   private static final HolesIntArray HOLES_INT_ARRAY = new HolesIntArray(0, createCache()).maybePreinitializeCache();
   public static final int HOLE_VALUE = Integer.MIN_VALUE;

   private HolesIntArray(int integrityLevel, DynamicArray.DynamicArrayCache cache) {
      super(integrityLevel, cache);
   }

   public static HolesIntArray makeHolesIntArray(
      JSDynamicObject object, int length, int[] array, long indexOffset, int arrayOffset, int usedLength, int holeCount, int integrityLevel
   ) {
      HolesIntArray arrayType = createHolesIntArray().setIntegrityLevel(integrityLevel);
      setArrayProperties(object, array, length, usedLength, indexOffset, arrayOffset);
      JSAbstractArray.arraySetHoleCount(object, holeCount);

      assert holeCount == arrayType.countHoles(object) : String.format(
         "holeCount, %d, differs from the actual count, %d", holeCount, arrayType.countHoles(object)
      );

      return arrayType;
   }

   private static HolesIntArray createHolesIntArray() {
      return HOLES_INT_ARRAY;
   }

   @Override
   AbstractWritableArray sameTypeHolesArray(JSDynamicObject object, int length, Object array, long indexOffset, int arrayOffset, int usedLength, int holeCount) {
      setArrayProperties(object, array, length, usedLength, indexOffset, arrayOffset);
      JSAbstractArray.arraySetHoleCount(object, holeCount);
      return this;
   }

   @Override
   public void setInBoundsFast(JSDynamicObject object, int index, int value) {
      throw Errors.shouldNotReachHere("should not call this method, use setInBounds(Non)Hole");
   }

   public boolean isHoleFast(JSDynamicObject object, int index) {
      int internalIndex = (int)(index - this.getIndexOffset(object));
      return this.isHolePrepared(object, internalIndex);
   }

   public void setInBoundsFastHole(JSDynamicObject object, int index, int value) {
      int internalIndex = (int)(index - this.getIndexOffset(object));

      assert this.isHolePrepared(object, internalIndex);

      this.incrementHolesCount(object, -1);
      this.setInBoundyFastIntl(object, index, internalIndex, value);
   }

   public void setInBoundsFastNonHole(JSDynamicObject object, int index, int value) {
      int internalIndex = (int)(index - this.getIndexOffset(object));

      assert !this.isHolePrepared(object, internalIndex);

      this.setInBoundyFastIntl(object, index, internalIndex, value);
   }

   private void setInBoundyFastIntl(JSDynamicObject object, int index, int internalIndex, int value) {
      getArray(object)[internalIndex] = value;
   }

   @Override
   public boolean containsHoles(JSDynamicObject object, long index) {
      return JSAbstractArray.arrayGetHoleCount(object) > 0 || !this.isInBoundsFast(object, index);
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

   public AbstractIntArray toNonHoles(JSDynamicObject object, long index, Object value) {
      assert !this.containsHoles(object, index);

      int[] array = getArray(object);
      int length = this.lengthInt(object);
      int usedLength = getUsedLength(object);
      int arrayOffset = this.getArrayOffset(object);
      long indexOffset = this.getIndexOffset(object);
      this.setInBoundsFastNonHole(object, (int)index, (Integer)value);
      AbstractIntArray newArray;
      if (indexOffset == 0L && arrayOffset == 0) {
         newArray = ZeroBasedIntArray.makeZeroBasedIntArray(object, length, usedLength, array, this.integrityLevel);
      } else {
         newArray = ContiguousIntArray.makeContiguousIntArray(object, length, array, indexOffset, arrayOffset, usedLength, this.integrityLevel);
      }

      if (JSConfig.TraceArrayTransitions) {
         traceArrayTransition(this, newArray, index, value);
      }

      return newArray;
   }

   @Override
   public Object getInBoundsFast(JSDynamicObject object, int index) {
      int value = this.getInBoundsFastInt(object, index);
      return isHoleValue(value) ? Undefined.instance : value;
   }

   @Override
   protected void incrementHolesCount(JSDynamicObject object, int offset) {
      JSAbstractArray.arraySetHoleCount(object, JSAbstractArray.arrayGetHoleCount(object) + offset);
   }

   public HolesIntArray toHoles(JSDynamicObject object, long index, Object value) {
      return this;
   }

   @Override
   public AbstractWritableArray toDouble(JSDynamicObject object, long index, double value) {
      int[] array = getArray(object);
      int length = this.lengthInt(object);
      int usedLength = getUsedLength(object);
      int arrayOffset = this.getArrayOffset(object);
      long indexOffset = this.getIndexOffset(object);
      int holeCount = JSAbstractArray.arrayGetHoleCount(object);
      double[] doubleCopy = ArrayCopy.intToDoubleHoles(array, arrayOffset, usedLength);
      HolesDoubleArray newArray = HolesDoubleArray.makeHolesDoubleArray(
         object, length, doubleCopy, indexOffset, arrayOffset, usedLength, holeCount, this.integrityLevel
      );
      if (JSConfig.TraceArrayTransitions) {
         traceArrayTransition(this, newArray, index, value);
      }

      return newArray;
   }

   @Override
   public AbstractWritableArray toObject(JSDynamicObject object, long index, Object value) {
      int[] array = getArray(object);
      int length = this.lengthInt(object);
      int usedLength = getUsedLength(object);
      int arrayOffset = this.getArrayOffset(object);
      long indexOffset = this.getIndexOffset(object);
      int holeCount = JSAbstractArray.arrayGetHoleCount(object);
      Object[] objectCopy = ArrayCopy.intToObjectHoles(array, arrayOffset, usedLength);
      HolesObjectArray newArray = HolesObjectArray.makeHolesObjectArray(
         object, length, objectCopy, indexOffset, arrayOffset, usedLength, holeCount, this.integrityLevel
      );
      if (JSConfig.TraceArrayTransitions) {
         traceArrayTransition(this, newArray, index, value);
      }

      return newArray;
   }

   public static boolean isHoleValue(int value) {
      return value == Integer.MIN_VALUE;
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
   protected HolesObjectArray toObjectHoles(JSDynamicObject object) {
      throw new UnsupportedOperationException("already a holes array");
   }

   @Override
   public boolean isHolesType() {
      return true;
   }

   @Override
   public ScriptArray removeRangeImpl(JSDynamicObject object, long start, long end) {
      return this.removeRangeHoles(object, start, end);
   }

   protected HolesIntArray withIntegrityLevel(int newIntegrityLevel) {
      return new HolesIntArray(newIntegrityLevel, this.cache);
   }

   @Override
   public List<Object> ownPropertyKeys(JSDynamicObject object) {
      return this.ownPropertyKeysHoles(object);
   }
}
