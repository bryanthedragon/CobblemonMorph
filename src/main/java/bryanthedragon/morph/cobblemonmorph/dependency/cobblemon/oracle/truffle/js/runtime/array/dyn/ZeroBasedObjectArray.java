package com.oracle.truffle.js.runtime.array.dyn;

import com.oracle.truffle.js.runtime.JSConfig;
import com.oracle.truffle.js.runtime.array.DynamicArray;
import com.oracle.truffle.js.runtime.array.ScriptArray;
import com.oracle.truffle.js.runtime.builtins.JSAbstractArray;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.util.Arrays;

public final class ZeroBasedObjectArray extends AbstractObjectArray {
   private static final ZeroBasedObjectArray ZERO_BASED_OBJECT_ARRAY = new ZeroBasedObjectArray(0, createCache()).maybePreinitializeCache();

   public static ZeroBasedObjectArray makeZeroBasedObjectArray(JSDynamicObject object, int length, int usedLength, Object[] array, int integrityLevel) {
      ZeroBasedObjectArray arrayType = createZeroBasedObjectArray().setIntegrityLevel(integrityLevel);
      JSAbstractArray.arraySetLength(object, length);
      JSAbstractArray.arraySetUsedLength(object, usedLength);
      JSAbstractArray.arraySetArray(object, array);
      return arrayType;
   }

   public static ZeroBasedObjectArray createZeroBasedObjectArray() {
      return ZERO_BASED_OBJECT_ARRAY;
   }

   private ZeroBasedObjectArray(int integrityLevel, DynamicArray.DynamicArrayCache cache) {
      super(integrityLevel, cache);
   }

   @Override
   public boolean isSupported(JSDynamicObject object, long index) {
      return isSupportedZeroBased(object, (int)index);
   }

   @Override
   public Object getInBoundsFastObject(JSDynamicObject object, int index) {
      return this.castNonNull(getArray(object)[index]);
   }

   @Override
   public void setInBoundsFast(JSDynamicObject object, int index, Object value) {
      getArray(object)[index] = checkNonNull(value);
   }

   @Override
   protected int prepareInBoundsFast(JSDynamicObject object, long index) {
      return (int)index;
   }

   @Override
   protected int prepareInBounds(JSDynamicObject object, int index, ScriptArray.ProfileHolder profile) {
      prepareInBoundsZeroBased(object, index, profile);
      return index;
   }

   @Override
   protected int prepareSupported(JSDynamicObject object, int index, ScriptArray.ProfileHolder profile) {
      this.prepareSupportedZeroBased(object, index, profile);
      return index;
   }

   @Override
   protected void setLengthLess(JSDynamicObject object, long length, ScriptArray.ProfileHolder profile) {
      this.setLengthLessZeroBased(object, length, profile);
   }

   public ContiguousObjectArray toContiguous(JSDynamicObject object, long index, Object value) {
      Object[] array = getArray(object);
      int length = this.lengthInt(object);
      int usedLength = getUsedLength(object);
      ContiguousObjectArray newArray = ContiguousObjectArray.makeContiguousObjectArray(object, length, array, 0L, 0, usedLength, this.integrityLevel);
      if (JSConfig.TraceArrayTransitions) {
         traceArrayTransition(this, newArray, index, value);
      }

      return newArray;
   }

   public HolesObjectArray toHoles(JSDynamicObject object, long index, Object value) {
      Object[] array = getArray(object);
      int length = this.lengthInt(object);
      int usedLength = getUsedLength(object);
      HolesObjectArray newArray = HolesObjectArray.makeHolesObjectArray(object, length, array, 0L, 0, usedLength, 0, this.integrityLevel);
      if (JSConfig.TraceArrayTransitions) {
         traceArrayTransition(this, newArray, index, value);
      }

      return newArray;
   }

   @Override
   public long firstElementIndex(JSDynamicObject object) {
      return 0L;
   }

   @Override
   public long lastElementIndex(JSDynamicObject object) {
      return getUsedLength(object) - 1;
   }

   @Override
   public ScriptArray removeRangeImpl(JSDynamicObject object, long start, long end) {
      Object[] array = getArray(object);
      int usedLength = getUsedLength(object);
      long moveLength = usedLength - end;
      if (moveLength > 0L) {
         System.arraycopy(array, (int)end, array, (int)start, (int)moveLength);
      }

      if (start < usedLength) {
         Arrays.fill(array, (int)(start + Math.max(0L, moveLength)), usedLength, null);
         int newUsedLength = (int)(moveLength > 0L ? usedLength - (end - start) : start);
         JSAbstractArray.arraySetUsedLength(object, newUsedLength);
      }

      return this;
   }

   @Override
   public ScriptArray shiftRangeImpl(JSDynamicObject object, long from) {
      int usedLength = getUsedLength(object);
      return (ScriptArray)(from < usedLength
         ? ContiguousObjectArray.makeContiguousObjectArray(
            object, this.lengthInt(object) - from, getArray(object), -from, (int)from, (int)(usedLength - from), this.integrityLevel
         )
         : this.removeRangeImpl(object, 0L, from));
   }

   @Override
   public ScriptArray addRangeImpl(JSDynamicObject object, long offset, int size) {
      return this.addRangeImplZeroBased(object, offset, size);
   }

   @Override
   public boolean hasHoles(JSDynamicObject object) {
      int length = this.lengthInt(object);
      int usedLength = getUsedLength(object);
      return usedLength < length;
   }

   protected ZeroBasedObjectArray withIntegrityLevel(int newIntegrityLevel) {
      return new ZeroBasedObjectArray(newIntegrityLevel, this.cache);
   }

   @Override
   public long nextElementIndex(JSDynamicObject object, long index) {
      return this.nextElementIndexZeroBased(object, index);
   }
}
