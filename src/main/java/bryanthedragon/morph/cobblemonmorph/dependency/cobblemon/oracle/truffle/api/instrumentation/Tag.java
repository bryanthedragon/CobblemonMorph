package com.oracle.truffle.api.instrumentation;

import com.oracle.truffle.api.impl.Accessor;
import com.oracle.truffle.api.nodes.LanguageInfo;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Objects;

public abstract class Tag {
   protected Tag() {
      throw new AssertionError("No tag instances allowed.");
   }

   public static Class<? extends Tag> findProvidedTag(LanguageInfo language, String tagId) {
      Objects.requireNonNull(language);
      Objects.requireNonNull(tagId);
      Accessor.EngineSupport engine = InstrumentAccessor.engineAccess();
      if (engine == null) {
         return null;
      } else {
         for (Class<? extends Tag> tag : engine.getProvidedTags(language)) {
            String alias = getIdentifier(tag);
            if (alias != null && alias.equals(tagId)) {
               return tag;
            }
         }

         return null;
      }
   }

   public static String getIdentifier(Class<? extends Tag> tag) {
      Objects.requireNonNull(tag);
      Tag.Identifier alias = tag.getAnnotation(Tag.Identifier.class);
      return alias != null ? alias.value() : null;
   }

   @Retention(RetentionPolicy.RUNTIME)
   @Target(ElementType.TYPE)
   public @interface Identifier {
      String value();
   }
}
