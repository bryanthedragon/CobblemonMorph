package com.oracle.truffle.api.object;

@Deprecated(since = "22.2")
public interface ObjectLocation {
   @Deprecated(since = "22.2")
   Class<? extends Object> getType();

   @Deprecated(since = "22.2")
   boolean isNonNull();
}
