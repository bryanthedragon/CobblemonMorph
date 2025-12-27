package com.oracle.truffle.js.nodes;

import com.oracle.truffle.api.dsl.ImplicitCast;
import com.oracle.truffle.api.dsl.TypeSystem;

@TypeSystem
public class IntToLongTypeSystem {
   @ImplicitCast
   public static long intToLong(int value) {
      return value;
   }
}
