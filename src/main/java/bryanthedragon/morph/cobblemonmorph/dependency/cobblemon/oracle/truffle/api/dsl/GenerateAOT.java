package com.oracle.truffle.api.dsl;

import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.nodes.RootNode;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.CLASS)
@Target(ElementType.TYPE)
public @interface GenerateAOT {
   @Retention(RetentionPolicy.CLASS)
   @Target(ElementType.METHOD)
   public @interface Exclude {
   }

   public interface Provider {
      void prepareForAOT(TruffleLanguage<?> language, RootNode root);
   }
}
