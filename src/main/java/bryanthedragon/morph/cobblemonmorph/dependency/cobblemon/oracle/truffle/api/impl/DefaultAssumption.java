package com.oracle.truffle.api.impl;

import com.oracle.truffle.api.nodes.InvalidAssumptionException;

final class DefaultAssumption extends AbstractAssumption {
   DefaultAssumption(String name) {
      super(name);
   }

   private DefaultAssumption(Object name) {
      super(name);
   }

   @Override
   public void check() throws InvalidAssumptionException {
      if (!this.isValid) {
         throw new InvalidAssumptionException();
      }
   }

   @Override
   public void invalidate() {
      this.invalidate("");
   }

   @Override
   public void invalidate(String message) {
      if (this.name != DefaultAssumption.Lazy.ALWAYS_VALID_NAME) {
         this.isValid = false;
      } else {
         throw new UnsupportedOperationException("Cannot invalidate this assumption - it is always valid");
      }
   }

   @Override
   public boolean isValid() {
      return this.isValid;
   }

   static DefaultAssumption createAlwaysValid() {
      return new DefaultAssumption(DefaultAssumption.Lazy.ALWAYS_VALID_NAME);
   }

   static class Lazy {
      static final Object ALWAYS_VALID_NAME = new Object() {
         @Override
         public String toString() {
            return "<always valid>";
         }
      };
   }
}
