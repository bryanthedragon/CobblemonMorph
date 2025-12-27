package com.oracle.truffle.api.object;

@Deprecated(since = "22.2")
public interface LongLocation {
   @Deprecated(since = "22.2")
   long getLong(DynamicObject store, Shape shape);

   @Deprecated(since = "22.2")
   long getLong(DynamicObject store, boolean condition);

   @Deprecated(since = "22.2")
   void setLong(DynamicObject store, long value) throws FinalLocationException;

   @Deprecated(since = "22.2")
   void setLong(DynamicObject store, long value, Shape shape) throws FinalLocationException;

   @Deprecated(since = "22.2")
   void setLong(DynamicObject store, long value, Shape oldShape, Shape newShape);

   @Deprecated(since = "22.2")
   Class<Long> getType();
}
