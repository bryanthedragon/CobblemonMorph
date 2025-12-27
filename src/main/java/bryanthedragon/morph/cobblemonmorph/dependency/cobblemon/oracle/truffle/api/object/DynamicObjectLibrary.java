package com.oracle.truffle.api.object;

import com.oracle.truffle.api.library.GenerateLibrary;
import com.oracle.truffle.api.library.Library;
import com.oracle.truffle.api.library.LibraryFactory;
import com.oracle.truffle.api.nodes.UnexpectedResultException;

@GenerateLibrary(defaultExportLookupEnabled = true, dynamicDispatchEnabled = false, pushEncapsulatingNode = false)
public abstract class DynamicObjectLibrary extends Library {
   private static final LibraryFactory<DynamicObjectLibrary> FACTORY = LibraryFactory.resolve(DynamicObjectLibrary.class);
   private static final DynamicObjectLibrary UNCACHED = FACTORY.getUncached();

   protected DynamicObjectLibrary() {
   }

   public static LibraryFactory<DynamicObjectLibrary> getFactory() {
      return FACTORY;
   }

   public static DynamicObjectLibrary getUncached() {
      return UNCACHED;
   }

   public abstract Shape getShape(DynamicObject object);

   public abstract Object getOrDefault(DynamicObject object, Object key, Object defaultValue);

   public int getIntOrDefault(DynamicObject object, Object key, Object defaultValue) throws UnexpectedResultException {
      Object value = this.getOrDefault(object, key, defaultValue);
      if (value instanceof Integer) {
         return (Integer)value;
      } else {
         throw new UnexpectedResultException(value);
      }
   }

   public double getDoubleOrDefault(DynamicObject object, Object key, Object defaultValue) throws UnexpectedResultException {
      Object value = this.getOrDefault(object, key, defaultValue);
      if (value instanceof Double) {
         return (Double)value;
      } else {
         throw new UnexpectedResultException(value);
      }
   }

   public long getLongOrDefault(DynamicObject object, Object key, Object defaultValue) throws UnexpectedResultException {
      Object value = this.getOrDefault(object, key, defaultValue);
      if (value instanceof Long) {
         return (Long)value;
      } else {
         throw new UnexpectedResultException(value);
      }
   }

   public abstract void put(DynamicObject object, Object key, Object value);

   public void putInt(DynamicObject object, Object key, int value) {
      this.put(object, key, value);
   }

   public void putDouble(DynamicObject object, Object key, double value) {
      this.put(object, key, value);
   }

   public void putLong(DynamicObject object, Object key, long value) {
      this.put(object, key, value);
   }

   public abstract boolean putIfPresent(DynamicObject object, Object key, Object value);

   public abstract void putWithFlags(DynamicObject object, Object key, Object value, int flags);

   public abstract void putConstant(DynamicObject object, Object key, Object value, int flags);

   public abstract boolean removeKey(DynamicObject object, Object key);

   public abstract boolean setDynamicType(DynamicObject object, Object type);

   public abstract Object getDynamicType(DynamicObject object);

   public abstract boolean containsKey(DynamicObject object, Object key);

   public abstract int getShapeFlags(DynamicObject object);

   public abstract boolean setShapeFlags(DynamicObject object, int flags);

   public abstract Property getProperty(DynamicObject object, Object key);

   public final int getPropertyFlagsOrDefault(DynamicObject object, Object key, int defaultValue) {
      Property property = this.getProperty(object, key);
      return property != null ? property.getFlags() : defaultValue;
   }

   public abstract boolean setPropertyFlags(DynamicObject object, Object key, int propertyFlags);

   public abstract void markShared(DynamicObject object);

   public abstract boolean isShared(DynamicObject object);

   public abstract boolean updateShape(DynamicObject object);

   public abstract boolean resetShape(DynamicObject object, Shape otherShape);

   public abstract Object[] getKeyArray(DynamicObject object);

   public abstract Property[] getPropertyArray(DynamicObject object);
}
