
package com.oracle.truffle.polyglot;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(value=RetentionPolicy.CLASS)
@interface SuppressFBWarnings {
    public String[] value();

    public String justification() default "";
}

