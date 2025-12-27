package com.oracle.truffle.js.runtime.array.dyn;

import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.array.DynamicArray;
import com.oracle.truffle.js.runtime.array.ScriptArray;
import com.oracle.truffle.js.runtime.array.SparseArray;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.Undefined;

public abstract class AbstractConstantArray extends DynamicArray {
   protected static final AbstractConstantArray.CreateWritableProfileAccess CREATE_WRITABLE_PROFILE = new AbstractConstantArray.CreateWritableProfileAccess() {};

   protected AbstractConstantArray(int integrityLevel, DynamicArray.DynamicArrayCache cache) {
      super(integrityLevel, cache);
   }

   @Override
   public final ScriptArray setElementImpl(JSDynamicObject object, long index, Object value, boolean strict) {
      if (index <= 2147483647L) {
         if (value instanceof Integer) {
            return this.createWriteableInt(object, index, (Integer)value, ScriptArray.ProfileHolder.empty()).setElementImpl(object, index, value, strict);
         } else {
            return value instanceof Double
               ? this.createWriteableDouble(object, index, (Double)value, ScriptArray.ProfileHolder.empty()).setElementImpl(object, index, value, strict)
               : this.createWriteableObject(object, index, value, ScriptArray.ProfileHolder.empty()).setElementImpl(object, index, value, strict);
         }
      } else {
         return SparseArray.makeSparseArray(object, this).setElementImpl(object, index, value, strict);
      }
   }

   @Override
   public final Object getElement(JSDynamicObject object, long index) {
      return this.isInBoundsFast(object, index) ? this.getElementInBounds(object, (int)index) : Undefined.instance;
   }

   @Override
   public final Object getElementInBounds(JSDynamicObject object, long index) {
      assert this.isInBoundsFast(object, index);

      return this.getElementInBounds(object, (int)index);
   }

   public abstract Object getElementInBounds(JSDynamicObject object, int index);

   @Override
   public final long length(JSDynamicObject object) {
      return this.lengthInt(object);
   }

   @Override
   public long firstElementIndex(JSDynamicObject object) {
      return 0L;
   }

   @Override
   public long lastElementIndex(JSDynamicObject object) {
      return this.length(object) - 1L;
   }

   @Override
   public long nextElementIndex(JSDynamicObject object, long index) {
      return index >= this.lastElementIndex(object) ? JSRuntime.MAX_SAFE_INTEGER_LONG : index + 1L;
   }

   @Override
   public long previousElementIndex(JSDynamicObject object, long index) {
      return index - 1L;
   }

   @Override
   public final boolean isInBoundsFast(JSDynamicObject object, long index) {
      return this.firstElementIndex(object) <= index && index <= this.lastElementIndex(object);
   }

   public abstract AbstractWritableArray createWriteableDouble(JSDynamicObject object, long index, double value, ScriptArray.ProfileHolder profile);

   public abstract AbstractWritableArray createWriteableInt(JSDynamicObject object, long index, int value, ScriptArray.ProfileHolder profile);

   public abstract AbstractWritableArray createWriteableObject(JSDynamicObject object, long index, Object value, ScriptArray.ProfileHolder profile);

   public abstract AbstractWritableArray createWriteableJSObject(JSDynamicObject object, long index, JSDynamicObject value, ScriptArray.ProfileHolder profile);

   public static ScriptArray.ProfileHolder createCreateWritableProfile() {
      return ScriptArray.ProfileHolder.create(4, AbstractConstantArray.CreateWritableProfileAccess.class);
   }

   @Override
   public boolean hasHoles(JSDynamicObject object) {
      return false;
   }

   protected interface CreateWritableProfileAccess extends ScriptArray.ProfileAccess {
      default boolean lengthZero(ScriptArray.ProfileHolder profile, boolean condition) {
         return profile.profile(this, 0, condition);
      }

      default boolean lengthBelowLimit(ScriptArray.ProfileHolder profile, boolean condition) {
         return profile.profile(this, 1, condition);
      }

      default boolean indexZero(ScriptArray.ProfileHolder profile, boolean condition) {
         return profile.profile(this, 2, condition);
      }

      default boolean indexLessThanLength(ScriptArray.ProfileHolder profile, boolean condition) {
         return profile.profile(this, 3, condition);
      }
   }
}
