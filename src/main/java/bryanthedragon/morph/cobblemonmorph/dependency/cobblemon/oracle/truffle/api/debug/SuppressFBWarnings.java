package com.oracle.truffle.api.debug;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.CLASS)
@interface SuppressFBWarnings {
   String[] value();

   String justification() default "";
}
