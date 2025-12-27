package com.oracle.truffle.api.frame;

import com.oracle.truffle.api.CompilerDirectives;

public enum FrameSlotKind {
   Object,
   Long,
   Int,
   Double,
   Float,
   Boolean,
   Byte,
   Illegal,
   Static;

   public final byte tag = (byte)this.ordinal();
   @CompilerDirectives.CompilationFinal(dimensions = 1)
   private static final FrameSlotKind[] VALUES = values();

   public static FrameSlotKind fromTag(byte tag) {
      return VALUES[tag];
   }
}
