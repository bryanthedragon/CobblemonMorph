package com.oracle.truffle.api.object;

@Deprecated(since = "22.2")
public interface IntLocation {
   @Deprecated(since = "22.2")
   int getInt(DynamicObject store, Shape shape);

   @Deprecated(since = "22.2")
   int getInt(DynamicObject store, boolean condition);

   @Deprecated(since = "22.2")
   void setInt(DynamicObject store, int value) throws FinalLocationException;

   @Deprecated(since = "22.2")
   void setInt(DynamicObject store, int value, Shape shape) throws FinalLocationException;

   @Deprecated(since = "22.2")
   void setInt(DynamicObject store, int value, Shape oldShape, Shape newShape);

   @Deprecated(since = "22.2")
   Class<Integer> getType();
}
