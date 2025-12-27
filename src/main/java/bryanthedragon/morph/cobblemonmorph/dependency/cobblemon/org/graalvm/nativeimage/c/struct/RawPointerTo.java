package org.graalvm.nativeimage.c.struct;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.graalvm.word.WordBase;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface RawPointerTo {
   Class<? extends WordBase> value() default WordBase.class;
}
