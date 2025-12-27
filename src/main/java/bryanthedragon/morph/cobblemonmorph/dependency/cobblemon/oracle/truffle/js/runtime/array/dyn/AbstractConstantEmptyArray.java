package com.oracle.truffle.js.runtime.array.dyn;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.js.runtime.JSConfig;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.array.ArrayAllocationSite;
import com.oracle.truffle.js.runtime.array.DynamicArray;
import com.oracle.truffle.js.runtime.array.ScriptArray;
import com.oracle.truffle.js.runtime.builtins.JSAbstractArray;
import com.oracle.truffle.js.runtime.builtins.JSArray;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.Undefined;
import java.util.List;

public abstract class AbstractConstantEmptyArray extends AbstractConstantArray {
   protected AbstractConstantEmptyArray(int integrityLevel, DynamicArray.DynamicArrayCache cache) {
      super(integrityLevel, cache);
   }

   protected static void setCapacity(JSDynamicObject object, long length) {
      JSArray.arraySetLength(object, length);
   }

   protected static long getCapacity(JSDynamicObject object) {
      return JSArray.arrayGetLength(object);
   }

   @Override
   public Object getElementInBounds(JSDynamicObject object, int index) {
      return Undefined.instance;
   }

   @Override
   public int lengthInt(JSDynamicObject object) {
      return (int)getCapacity(object);
   }

   @Override
   public Object cloneArray(JSDynamicObject object) {
      return ScriptArray.EMPTY_OBJECT_ARRAY;
   }

   @Override
   public boolean hasElement(JSDynamicObject object, long index) {
      return false;
   }

   @Override
   public ScriptArray deleteElementImpl(JSDynamicObject object, long index, boolean strict) {
      return this;
   }

   @Override
   public long firstElementIndex(JSDynamicObject object) {
      return 0L;
   }

   @Override
   public long lastElementIndex(JSDynamicObject object) {
      return -1L;
   }

   @Override
   public long nextElementIndex(JSDynamicObject object, long index) {
      return JSRuntime.MAX_SAFE_INTEGER_LONG;
   }

   @Override
   public long previousElementIndex(JSDynamicObject object, long index) {
      return -1L;
   }

   public AbstractIntArray createWriteableInt(JSDynamicObject object, long index, int value, ScriptArray.ProfileHolder profile) {
      assert index >= 0L;

      int capacity = this.lengthInt(object);
      int[] initialArray = new int[calcNewArraySize(capacity, profile)];
      AbstractIntArray newArray;
      if (CREATE_WRITABLE_PROFILE.indexZero(profile, index == 0L)) {
         newArray = ZeroBasedIntArray.makeZeroBasedIntArray(object, capacity, 0, initialArray, this.integrityLevel);
      } else {
         newArray = this.createWritableIntContiguous(object, capacity, index, initialArray, profile);
      }

      if (JSConfig.TraceArrayTransitions) {
         traceArrayTransition(this, newArray, index, value);
      }

      this.notifyAllocationSite(object, newArray);
      return newArray;
   }

   private AbstractIntArray createWritableIntContiguous(JSDynamicObject object, int capacity, long index, int[] initialArray, ScriptArray.ProfileHolder profile) {
      long length = Math.max(index + 1L, (long)capacity);
      int arrayOffset = 0;
      long indexOffset = index;
      if (CREATE_WRITABLE_PROFILE.indexLessThanLength(profile, index < initialArray.length)) {
         arrayOffset = (int)index;
         indexOffset = 0L;
      }

      return ContiguousIntArray.makeContiguousIntArray(object, length, initialArray, indexOffset, arrayOffset, 0, this.integrityLevel);
   }

   private static int calcNewArraySize(int capacity, ScriptArray.ProfileHolder profile) {
      if (CREATE_WRITABLE_PROFILE.lengthZero(profile, capacity == 0)) {
         return 8;
      } else {
         return CREATE_WRITABLE_PROFILE.lengthBelowLimit(profile, capacity < 1000000) ? capacity : 8;
      }
   }

   public AbstractDoubleArray createWriteableDouble(JSDynamicObject object, long index, double value, ScriptArray.ProfileHolder profile) {
      int capacity = this.lengthInt(object);
      double[] initialArray = new double[calcNewArraySize(capacity, profile)];
      AbstractDoubleArray newArray;
      if (CREATE_WRITABLE_PROFILE.indexZero(profile, index == 0L)) {
         newArray = ZeroBasedDoubleArray.makeZeroBasedDoubleArray(object, capacity, 0, initialArray, this.integrityLevel);
      } else {
         newArray = this.createWritableDoubleContiguous(object, capacity, index, initialArray, profile);
      }

      if (JSConfig.TraceArrayTransitions) {
         traceArrayTransition(this, newArray, index, value);
      }

      this.notifyAllocationSite(object, newArray);
      return newArray;
   }

   private AbstractDoubleArray createWritableDoubleContiguous(
      JSDynamicObject object, int capacity, long index, double[] initialArray, ScriptArray.ProfileHolder profile
   ) {
      long length = Math.max(index + 1L, (long)capacity);
      int arrayOffset = 0;
      long indexOffset = index;
      if (CREATE_WRITABLE_PROFILE.indexLessThanLength(profile, index < initialArray.length)) {
         arrayOffset = (int)index;
         indexOffset = 0L;
      }

      return ContiguousDoubleArray.makeContiguousDoubleArray(object, length, initialArray, indexOffset, arrayOffset, 0, this.integrityLevel);
   }

   public AbstractJSObjectArray createWriteableJSObject(JSDynamicObject object, long index, JSDynamicObject value, ScriptArray.ProfileHolder profile) {
      int capacity = this.lengthInt(object);
      JSDynamicObject[] initialArray = new JSDynamicObject[calcNewArraySize(capacity, profile)];
      AbstractJSObjectArray newArray;
      if (CREATE_WRITABLE_PROFILE.indexZero(profile, index == 0L)) {
         newArray = ZeroBasedJSObjectArray.makeZeroBasedJSObjectArray(object, capacity, 0, initialArray, this.integrityLevel);
      } else {
         newArray = this.createWritableJSObjectContiguous(object, capacity, index, initialArray, profile);
      }

      if (JSConfig.TraceArrayTransitions) {
         traceArrayTransition(this, newArray, index, value);
      }

      this.notifyAllocationSite(object, newArray);
      return newArray;
   }

   private AbstractJSObjectArray createWritableJSObjectContiguous(
      JSDynamicObject object, int capacity, long index, JSDynamicObject[] initialArray, ScriptArray.ProfileHolder profile
   ) {
      long length = Math.max(index + 1L, (long)capacity);
      int arrayOffset = 0;
      long indexOffset = index;
      if (CREATE_WRITABLE_PROFILE.indexLessThanLength(profile, index < initialArray.length)) {
         arrayOffset = (int)index;
         indexOffset = 0L;
      }

      return ContiguousJSObjectArray.makeContiguousJSObjectArray(object, length, initialArray, indexOffset, arrayOffset, 0, this.integrityLevel);
   }

   public AbstractObjectArray createWriteableObject(JSDynamicObject object, long index, Object value, ScriptArray.ProfileHolder profile) {
      int capacity = this.lengthInt(object);
      Object[] initialArray = new Object[calcNewArraySize(capacity, profile)];
      AbstractObjectArray newArray;
      if (CREATE_WRITABLE_PROFILE.indexZero(profile, index == 0L)) {
         newArray = ZeroBasedObjectArray.makeZeroBasedObjectArray(object, capacity, 0, initialArray, this.integrityLevel);
      } else {
         newArray = this.createWritableObjectContiguous(object, capacity, index, initialArray, profile);
      }

      if (JSConfig.TraceArrayTransitions) {
         traceArrayTransition(this, newArray, index, value);
      }

      this.notifyAllocationSite(object, newArray);
      return newArray;
   }

   private AbstractObjectArray createWritableObjectContiguous(
      JSDynamicObject object, int capacity, long index, Object[] initialArray, ScriptArray.ProfileHolder profile
   ) {
      long length = Math.max(index + 1L, (long)capacity);
      int arrayOffset = 0;
      long indexOffset = index;
      if (CREATE_WRITABLE_PROFILE.indexLessThanLength(profile, index < initialArray.length)) {
         arrayOffset = (int)index;
         indexOffset = 0L;
      }

      return ContiguousObjectArray.makeContiguousObjectArray(object, length, initialArray, indexOffset, arrayOffset, 0, this.integrityLevel);
   }

   @Override
   public boolean isHolesType() {
      return true;
   }

   @Override
   public boolean hasHoles(JSDynamicObject object) {
      return getCapacity(object) != 0L;
   }

   @Override
   public List<Object> ownPropertyKeys(JSDynamicObject object) {
      return this.ownPropertyKeysContiguous(object);
   }

   private void notifyAllocationSite(JSDynamicObject object, ScriptArray newArray) {
      if (JSConfig.TrackArrayAllocationSites && CompilerDirectives.inInterpreter()) {
         ArrayAllocationSite site = JSAbstractArray.arrayGetAllocationSite(object);
         if (site != null) {
            site.notifyArrayTransition(newArray, this.lengthInt(object));
         }
      }
   }
}
