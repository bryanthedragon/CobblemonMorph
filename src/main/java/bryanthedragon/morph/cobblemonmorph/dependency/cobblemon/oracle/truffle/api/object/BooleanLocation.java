package com.oracle.truffle.api.object;

@Deprecated(since = "22.2")
public interface BooleanLocation {
   @Deprecated(since = "22.2")
   boolean getBoolean(DynamicObject store, Shape shape);

   @Deprecated(since = "22.2")
   boolean getBoolean(DynamicObject store, boolean condition);

   @Deprecated(since = "22.2")
   void setBoolean(DynamicObject store, boolean value) throws FinalLocationException;

   @Deprecated(since = "22.2")
   void setBoolean(DynamicObject store, boolean value, Shape shape) throws FinalLocationException;

   @Deprecated(since = "22.2")
   void setBoolean(DynamicObject store, boolean value, Shape oldShape, Shape newShape);

   @Deprecated(since = "22.2")
   Class<Boolean> getType();
}
