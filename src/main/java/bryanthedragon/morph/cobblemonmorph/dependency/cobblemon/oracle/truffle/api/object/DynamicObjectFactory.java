package com.oracle.truffle.api.object;

@Deprecated(since = "22.2")
public interface DynamicObjectFactory {
   DynamicObject newInstance(Object... initialValues);

   Shape getShape();
}
