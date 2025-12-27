package com.oracle.truffle.api.impl;

final class ImplAccessor extends Accessor {
   private static final ImplAccessor ACCESSOR = new ImplAccessor();

   private ImplAccessor() {
   }

   static Accessor.FrameSupport frameSupportAccessor() {
      return ACCESSOR.framesSupport();
   }
}
