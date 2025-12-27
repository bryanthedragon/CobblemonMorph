package com.oracle.truffle.api.object;

public abstract class Property {
   @Deprecated(since = "22.2")
   protected Property() {
   }

   @Deprecated(since = "22.2")
   public static Property create(Object key, Location location, int flags) {
      return Layout.getFactory().createProperty(key, location, flags);
   }

   public abstract Object getKey();

   public abstract int getFlags();

   @Deprecated(since = "22.2")
   public abstract Object get(DynamicObject store, Shape shape);

   @Deprecated(since = "22.2")
   public abstract Object get(DynamicObject store, boolean condition);

   @Deprecated(since = "22.2")
   public abstract void set(DynamicObject store, Object value, Shape shape) throws IncompatibleLocationException, FinalLocationException;

   @Deprecated(since = "22.2")
   public abstract void setGeneric(DynamicObject store, Object value, Shape shape);

   @Deprecated(since = "22.2")
   public abstract void setSafe(DynamicObject store, Object value, Shape shape);

   @Deprecated(since = "22.2")
   public abstract void setSafe(DynamicObject store, Object value, Shape oldShape, Shape newShape);

   public abstract Location getLocation();

   public abstract boolean isHidden();
}
