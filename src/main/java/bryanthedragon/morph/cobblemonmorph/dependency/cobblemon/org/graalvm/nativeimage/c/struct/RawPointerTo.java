
package org.graalvm.nativeimage.c.struct;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.graalvm.word.WordBase;

@Retention(value=RetentionPolicy.RUNTIME)
@Target(value={ElementType.TYPE})
public @interface RawPointerTo {
    public Class<? extends WordBase> value() default WordBase.class;
}

