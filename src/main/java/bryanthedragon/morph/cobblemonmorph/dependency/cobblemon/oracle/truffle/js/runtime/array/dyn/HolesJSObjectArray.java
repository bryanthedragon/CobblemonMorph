package com.oracle.truffle.js.runtime.array.dyn;

import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSConfig;
import com.oracle.truffle.js.runtime.array.DynamicArray;
import com.oracle.truffle.js.runtime.array.ScriptArray;
import com.oracle.truffle.js.runtime.builtins.JSAbstractArray;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.Undefined;
import java.util.List;

public final class HolesJSObjectArray extends AbstractContiguousJSObjectArray {
   private static final HolesJSObjectArray HOLES_JSOBJECT_ARRAY = new HolesJSObjectArray(0, createCache()).maybePreinitializeCache();

   private HolesJSObjectArray(int integrityLevel, DynamicArray.DynamicArrayCache cache) {
      super(integrityLevel, cache);
   }

   public static HolesJSObjectArray makeHolesJSObjectArray(
      JSDynamicObject object, int length, JSDynamicObject[] array, long indexOffset, int arrayOffset, int usedLength, int holeCount, int integrityLevel
   ) {
      HolesJSObjectArray arrayType = createHolesJSObjectArray().setIntegrityLevel(integrityLevel);
      setArrayProperties(object, array, length, usedLength, indexOffset, arrayOffset);
      JSAbstractArray.arraySetHoleCount(object, holeCount);

      assert holeCount == arrayType.countHoles(object);

      return arrayType;
   }

   private static HolesJSObjectArray createHolesJSObjectArray() {
      return HOLES_JSOBJECT_ARRAY;
   }

   @Override
   AbstractWritableArray sameTypeHolesArray(JSDynamicObject object, int length, Object array, long indexOffset, int arrayOffset, int usedLength, int holeCount) {
      setArrayProperties(object, array, length, usedLength, indexOffset, arrayOffset);
      JSAbstractArray.arraySetHoleCount(object, holeCount);
      return this;
   }

   @Override
   public void setInBoundsFast(JSDynamicObject object, int index, JSDynamicObject value) {
      throw Errors.shouldNotReachHere("should not call this method, use setInBounds(Non)Hole");
   }

   public boolean isHoleFast(JSDynamicObject object, int index) {
      int internalIndex = (int)(index - this.getIndexOffset(object));
      return this.isHolePrepared(object, internalIndex);
   }

   public void setInBoundsFastHole(JSDynamicObject object, int index, JSDynamicObject value) {
      int internalIndex = (int)(index - this.getIndexOffset(object));

      assert this.isHolePrepared(object, internalIndex);

      this.incrementHolesCount(object, -1);
      this.setInBoundsFastIntl(object, index, internalIndex, value);
   }

   public void setInBoundsFastNonHole(JSDynamicObject object, int index, JSDynamicObject value) {
      int internalIndex = (int)(index - this.getIndexOffset(object));

      assert !this.isHolePrepared(object, internalIndex);

      this.setInBoundsFastIntl(object, index, internalIndex, value);
   }

   private void setInBoundsFastIntl(JSDynamicObject object, int index, int internalIndex, JSDynamicObject value) {
      getArray(object)[internalIndex] = checkNonNull(value);
   }

   @Override
   public boolean containsHoles(JSDynamicObject object, long index) {
      return JSAbstractArray.arrayGetHoleCount(object) > 0 || !this.isInBoundsFast(object, index);
   }

   public AbstractJSObjectArray toNonHoles(JSDynamicObject object, long index, Object value) {
      assert !this.containsHoles(object, index);

      JSDynamicObject[] array = getArray(object);
      int length = this.lengthInt(object);
      int usedLength = getUsedLength(object);
      int arrayOffset = this.getArrayOffset(object);
      long indexOffset = this.getIndexOffset(object);
      this.setInBoundsFastNonHole(object, (int)index, (JSDynamicObject)value);
      AbstractJSObjectArray newArray;
      if (indexOffset == 0L && arrayOffset == 0) {
         newArray = ZeroBasedJSObjectArray.makeZeroBasedJSObjectArray(object, length, usedLength, array, this.integrityLevel);
      } else {
         newArray = ContiguousJSObjectArray.makeContiguousJSObjectArray(object, length, array, indexOffset, arrayOffset, usedLength, this.integrityLevel);
      }

      if (JSConfig.TraceArrayTransitions) {
         traceArrayTransition(this, newArray, index, value);
      }

      return newArray;
   }

   @Override
   public AbstractWritableArray toObject(JSDynamicObject object, long index, Object value) {
      JSDynamicObject[] array = getArray(object);
      int length = this.lengthInt(object);
      int usedLength = getUsedLength(object);
      int arrayOffset = this.getArrayOffset(object);
      long indexOffset = this.getIndexOffset(object);
      int holeCount = JSAbstractArray.arrayGetHoleCount(object);
      Object[] objectCopy = ArrayCopy.jsobjectToObjectHoles(array, arrayOffset, usedLength);
      HolesObjectArray newArray = HolesObjectArray.makeHolesObjectArray(
         object, length, objectCopy, indexOffset, arrayOffset, usedLength, holeCount, this.integrityLevel
      );
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
      JSDynamicObject value = this.getInBoundsFastJSObject(object, index);
      return isHoleValue(value) ? Undefined.instance : value;
   }

   public HolesJSObjectArray toHoles(JSDynamicObject object, long index, Object value) {
      return this;
   }

   public static boolean isHoleValue(JSDynamicObject value) {
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
   protected JSDynamicObject castNonNull(JSDynamicObject value) {
      return value;
   }

   protected HolesJSObjectArray withIntegrityLevel(int newIntegrityLevel) {
      return new HolesJSObjectArray(newIntegrityLevel, this.cache);
   }

   @Override
   public List<Object> ownPropertyKeys(JSDynamicObject object) {
      return this.ownPropertyKeysHoles(object);
   }
}
