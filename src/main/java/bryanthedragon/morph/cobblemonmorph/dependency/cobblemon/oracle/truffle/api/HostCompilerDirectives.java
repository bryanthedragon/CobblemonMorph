package com.oracle.truffle.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public final class HostCompilerDirectives {
   private HostCompilerDirectives() {
   }

   @Retention(RetentionPolicy.RUNTIME)
   @Target({ElementType.METHOD, ElementType.CONSTRUCTOR})
   public @interface BytecodeInterpreterSwitch {
   }

   @Retention(RetentionPolicy.RUNTIME)
   @Target({ElementType.METHOD, ElementType.CONSTRUCTOR})
   @Deprecated(since = "22.2")
   public @interface BytecodeInterpreterSwitchBoundary {
   }

   @Retention(RetentionPolicy.RUNTIME)
   @Target({ElementType.METHOD, ElementType.CONSTRUCTOR})
   public @interface InliningCutoff {
   }
}
