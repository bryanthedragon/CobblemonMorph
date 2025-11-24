
package com.oracle.truffle.api.dsl;

import com.oracle.truffle.api.dsl.NodeFields;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(value=RetentionPolicy.CLASS)
@Target(value={ElementType.TYPE})
@Repeatable(value=NodeFields.class)
public @interface NodeField {
    public String name();

    public Class<?> type();
}

