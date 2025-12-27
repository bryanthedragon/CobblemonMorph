package com.oracle.truffle.api.object;

@Deprecated(since = "22.2")
public interface DoubleLocation {
   @Deprecated(since = "22.2")
   double getDouble(DynamicObject store, Shape shape);

   @Deprecated(since = "22.2")
   double getDouble(DynamicObject store, boolean condition);

   @Deprecated(since = "22.2")
   void setDouble(DynamicObject store, double value) throws FinalLocationException;

   @Deprecated(since = "22.2")
   void setDouble(DynamicObject store, double value, Shape shape) throws FinalLocationException;

   @Deprecated(since = "22.2")
   void setDouble(DynamicObject store, double value, Shape oldShape, Shape newShape);

   @Deprecated(since = "22.2")
   Class<Double> getType();
}
