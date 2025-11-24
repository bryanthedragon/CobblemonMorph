
package com.oracle.truffle.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.graalvm.options.OptionCategory;
import org.graalvm.options.OptionStability;

@Retention(value=RetentionPolicy.CLASS)
@Target(value={ElementType.FIELD})
public @interface Option {
    public String name() default "";

    public String help();

    public boolean deprecated() default false;

    public String deprecationMessage() default "";

    public OptionCategory category();

    public OptionStability stability() default OptionStability.EXPERIMENTAL;

    public String usageSyntax() default "";

    @Retention(value=RetentionPolicy.CLASS)
    @Target(value={ElementType.TYPE})
    public static @interface Group {
        public String[] value();
    }
}

