package com.oracle.truffle.js.runtime.array.dyn;

import com.oracle.truffle.js.runtime.JSConfig;
import com.oracle.truffle.js.runtime.array.DynamicArray;
import com.oracle.truffle.js.runtime.array.ScriptArray;
import com.oracle.truffle.js.runtime.builtins.JSAbstractArray;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.Undefined;
import java.util.List;

public final class ConstantObjectArray extends AbstractConstantArray {
   private static final ConstantObjectArray CONSTANT_OBJECT_ARRAY = new ConstantObjectArray(false, 0, createCache()).maybePreinitializeCache();
   private static final ConstantObjectArray CONSTANT_HOLES_OBJECT_ARRAY = new ConstantObjectArray(true, 0, createCache()).maybePreinitializeCache();
   private final boolean holes;

   public static ConstantObjectArray createConstantObjectArray() {
      return CONSTANT_OBJECT_ARRAY;
   }

   public static AbstractConstantArray createConstantHolesObjectArray() {
      return CONSTANT_HOLES_OBJECT_ARRAY;
   }

   private ConstantObjectArray(boolean holes, int integrityLevel, DynamicArray.DynamicArrayCache cache) {
      super(integrityLevel, cache);
      this.holes = holes;
   }

   private static Object[] getArray(JSDynamicObject object) {
      return (Object[])JSAbstractArray.arrayGetArray(object);
   }

   @Override
   public boolean hasElement(JSDynamicObject object, long index) {
      return index >= 0L && index < getArray(object).length ? !this.holes || getArray(object)[(int)index] != null : false;
   }

   @Override
   public Object getElementInBounds(JSDynamicObject object, int index) {
      Object value = getElementInBoundsDirect(object, index);
      return this.holes && value == null ? Undefined.instance : value;
   }

   private static boolean isEmpty(JSDynamicObject object, int index) {
      return getArray(object)[index] == null;
   }

   public static Object getElementInBoundsDirect(JSDynamicObject object, int index) {
      return getArray(object)[index];
   }

   @Override
   public boolean hasHoles(JSDynamicObject object) {
      return this.holes;
   }

   @Override
   public int lengthInt(JSDynamicObject object) {
      return getArray(object).length;
   }

   @Override
   public Object cloneArray(JSDynamicObject object) {
      return getArray(object);
   }

   @Override
   public long nextElementIndex(JSDynamicObject object, long index0) {
      if (!this.holes) {
         return super.nextElementIndex(object, index0);
      } else {
         int index = (int)index0;

         do {
            index++;
         } while (index < super.lastElementIndex(object) && isEmpty(object, index));

         return index;
      }
   }

   @Override
   public long previousElementIndex(JSDynamicObject object, long index0) {
      if (!this.holes) {
         return super.previousElementIndex(object, index0);
      } else {
         int index = (int)index0;

         do {
            index--;
         } while (index >= super.firstElementIndex(object) && isEmpty(object, index));

         return index;
      }
   }

   @Override
   public long firstElementIndex(JSDynamicObject object) {
      if (!this.holes) {
         return super.firstElementIndex(object);
      } else {
         int index = 0;
         int length = this.lengthInt(object);

         while (index < length && isEmpty(object, index)) {
            index++;
         }

         return index;
      }
   }

   @Override
   public long lastElementIndex(JSDynamicObject object) {
      if (!this.holes) {
         return super.lastElementIndex(object);
      } else {
         int index = this.lengthInt(object);

         do {
            index--;
         } while (index >= 0 && isEmpty(object, index));

         return index;
      }
   }

   @Override
   public ScriptArray deleteElementImpl(JSDynamicObject object, long index, boolean strict) {
      return this.createWriteableObject(object, index, null, ScriptArray.ProfileHolder.empty()).deleteElementImpl(object, index, strict);
   }

   @Override
   public ScriptArray setLengthImpl(JSDynamicObject object, long length, ScriptArray.ProfileHolder profile) {
      return this.createWriteableObject(object, length - 1L, null, ScriptArray.ProfileHolder.empty()).setLengthImpl(object, length, profile);
   }

   public AbstractObjectArray createWriteableInt(JSDynamicObject object, long index, int value, ScriptArray.ProfileHolder profile) {
      return this.createWriteableObject(object, index, value, profile);
   }

   public AbstractObjectArray createWriteableDouble(JSDynamicObject object, long index, double value, ScriptArray.ProfileHolder profile) {
      return this.createWriteableObject(object, index, value, profile);
   }

   public AbstractObjectArray createWriteableJSObject(JSDynamicObject object, long index, JSDynamicObject value, ScriptArray.ProfileHolder profile) {
      return this.createWriteableObject(object, index, value, profile);
   }

   public AbstractObjectArray createWriteableObject(JSDynamicObject object, long index, Object value, ScriptArray.ProfileHolder profile) {
      Object[] array = getArray(object);
      AbstractObjectArray newArray;
      if (this.holes) {
         int arrayOffset = (int)this.firstElementIndex(object);
         int usedLength = (int)this.lastElementIndex(object) + 1 - arrayOffset;
         int holeCount = this.countHoles(object);
         newArray = HolesObjectArray.makeHolesObjectArray(
            object, array.length, ArrayCopy.objectToObject(array), 0L, arrayOffset, usedLength, holeCount, this.integrityLevel
         );
      } else {
         newArray = ZeroBasedObjectArray.makeZeroBasedObjectArray(object, array.length, array.length, ArrayCopy.objectToObject(array), this.integrityLevel);
      }

      if (JSConfig.TraceArrayTransitions) {
         traceArrayTransition(this, newArray, index, value);
      }

      return newArray;
   }

   private int countHoles(JSDynamicObject object) {
      int index = (int)this.firstElementIndex(object);
      int lastIndex = (int)(this.lastElementIndex(object) + 1L);
      Object[] objArray = getArray(object);

      int holeCount;
      for (holeCount = 0; index < lastIndex; index++) {
         if (HolesObjectArray.isHoleValue(objArray[index])) {
            holeCount++;
         }
      }

      return holeCount;
   }

   @Override
   public boolean isHolesType() {
      return this.holes;
   }

   @Override
   public ScriptArray removeRangeImpl(JSDynamicObject object, long start, long end) {
      Object[] array = getArray(object);
      if (array.length - (end - start) == 0L) {
         AbstractConstantEmptyArray.setCapacity(object, 0L);
      } else {
         Object[] newArray = new Object[array.length - (int)(end - start)];
         System.arraycopy(array, 0, newArray, 0, (int)start);
         System.arraycopy(array, (int)end, newArray, (int)start, (int)(array.length - end));
         JSAbstractArray.arraySetArray(object, newArray);
      }

      return this;
   }

   @Override
   public ScriptArray addRangeImpl(JSDynamicObject object, long offset, int size) {
      Object[] array = getArray(object);
      if (array.length == 0) {
         AbstractConstantEmptyArray.setCapacity(object, size);
         return this;
      } else {
         Object[] newArray = new Object[array.length + size];
         System.arraycopy(array, 0, newArray, 0, (int)offset);
         System.arraycopy(array, (int)offset, newArray, (int)offset + size, (int)(array.length - offset));
         JSAbstractArray.arraySetArray(object, newArray);
         return this;
      }
   }

   @Override
   protected DynamicArray withIntegrityLevel(int newIntegrityLevel) {
      return new ConstantObjectArray(this.holes, newIntegrityLevel, this.cache);
   }

   @Override
   public List<Object> ownPropertyKeys(JSDynamicObject object) {
      return this.holes ? this.ownPropertyKeysHoles(object) : this.ownPropertyKeysContiguous(object);
   }
}
