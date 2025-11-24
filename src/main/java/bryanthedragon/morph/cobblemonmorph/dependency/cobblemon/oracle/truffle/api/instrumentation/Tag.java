
package com.oracle.truffle.api.instrumentation;

import com.oracle.truffle.api.impl.Accessor;
import com.oracle.truffle.api.instrumentation.InstrumentAccessor;
import com.oracle.truffle.api.nodes.LanguageInfo;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Objects;

public abstract class Tag {
    protected Tag() {
        throw new AssertionError((Object)"No tag instances allowed.");
    }

    public static Class<? extends Tag> findProvidedTag(LanguageInfo language, String tagId) {
        Objects.requireNonNull(language);
        Objects.requireNonNull(tagId);
        Accessor.EngineSupport engine = InstrumentAccessor.engineAccess();
        if (engine == null) {
            return null;
        }
        for (Class<?> tag : engine.getProvidedTags(language)) {
            String alias = Tag.getIdentifier(tag);
            if (alias == null || !alias.equals(tagId)) continue;
            return tag;
        }
        return null;
    }

    public static String getIdentifier(Class<? extends Tag> tag) {
        Objects.requireNonNull(tag);
        Identifier alias = tag.getAnnotation(Identifier.class);
        if (alias != null) {
            return alias.value();
        }
        return null;
    }

    @Retention(value=RetentionPolicy.RUNTIME)
    @Target(value={ElementType.TYPE})
    public static @interface Identifier {
        public String value();
    }
}

