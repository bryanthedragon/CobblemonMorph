package com.oracle.truffle.api.instrumentation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.CLASS)
@Target(ElementType.TYPE)
public @interface GenerateWrapper {
   @Retention(RetentionPolicy.CLASS)
   @Target(ElementType.METHOD)
   public @interface Ignore {
   }

   @Retention(RetentionPolicy.CLASS)
   @Target(ElementType.METHOD)
   public @interface IncomingConverter {
   }

   @Retention(RetentionPolicy.CLASS)
   @Target(ElementType.METHOD)
   public @interface OutgoingConverter {
   }
}
