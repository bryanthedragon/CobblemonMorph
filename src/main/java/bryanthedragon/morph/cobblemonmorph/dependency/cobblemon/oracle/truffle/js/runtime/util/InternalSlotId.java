package com.oracle.truffle.js.runtime.util;

import com.oracle.truffle.api.strings.TruffleString;

public final class InternalSlotId {
   private final TruffleString description;
   private final int ordinal;

   public InternalSlotId(TruffleString description, int ordinal) {
      this.description = description;
      this.ordinal = ordinal;
   }

   @Override
   public String toString() {
      return ":" + this.description + ":" + this.ordinal;
   }
}
