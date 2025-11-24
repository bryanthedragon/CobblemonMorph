
package com.oracle.truffle.api.debug;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(value=RetentionPolicy.CLASS)
@interface SuppressFBWarnings {
    public String[] value();

    public String justification() default "";
}

