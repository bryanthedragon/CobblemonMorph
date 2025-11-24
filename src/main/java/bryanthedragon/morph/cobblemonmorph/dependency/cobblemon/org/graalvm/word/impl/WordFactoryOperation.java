
package org.graalvm.word.impl;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.graalvm.word.impl.WordFactoryOpcode;

@Retention(value=RetentionPolicy.RUNTIME)
@Target(value={ElementType.METHOD})
public @interface WordFactoryOperation {
    public WordFactoryOpcode opcode();
}

