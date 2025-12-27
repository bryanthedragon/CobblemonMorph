package com.oracle.truffle.api.impl;

import com.oracle.truffle.api.Assumption;

public abstract class AbstractAssumption implements Assumption {
   protected final Object name;
   protected volatile boolean isValid;

   protected AbstractAssumption(Object name) {
      this.name = name;
      this.isValid = true;
   }

   @Override
   public final String getName() {
      return this.name.toString();
   }

   @Override
   public final String toString() {
      return "Assumption(" + (this.isValid ? "valid" : "invalid") + ", name=" + this.name + ")";
   }
}
