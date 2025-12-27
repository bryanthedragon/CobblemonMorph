package com.oracle.truffle.api.dsl;

import com.oracle.truffle.api.impl.Accessor;

final class DSLAccessor extends Accessor {
   private static final DSLAccessor ACCESSOR = new DSLAccessor();

   private DSLAccessor() {
   }

   static Accessor.NodeSupport nodeAccessor() {
      return ACCESSOR.nodeSupport();
   }
}
