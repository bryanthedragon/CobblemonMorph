
package org.graalvm.nativeimage.c.function;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.function.BooleanSupplier;

@Retention(value=RetentionPolicy.RUNTIME)
@Target(value={ElementType.METHOD})
public @interface CEntryPoint {
    public String name() default "";

    public String[] documentation() default {""};

    public Class<? extends ExceptionHandler> exceptionHandler() default FatalExceptionHandler.class;

    public Builtin builtin() default Builtin.NO_BUILTIN;

    public Class<? extends BooleanSupplier> include() default AlwaysIncluded.class;

    public Publish publishAs() default Publish.SymbolAndHeader;

    public static interface ExceptionHandler {
    }

    @Retention(value=RetentionPolicy.RUNTIME)
    @Target(value={ElementType.PARAMETER})
    public static @interface IsolateContext {
    }

    @Retention(value=RetentionPolicy.RUNTIME)
    @Target(value={ElementType.PARAMETER})
    public static @interface IsolateThreadContext {
    }

    public static enum Builtin {
        NO_BUILTIN,
        CREATE_ISOLATE,
        ATTACH_THREAD,
        GET_CURRENT_THREAD,
        GET_ISOLATE,
        DETACH_THREAD,
        TEAR_DOWN_ISOLATE;

    }

    public static enum Publish {
        NotPublished,
        SymbolOnly,
        SymbolAndHeader;

    }

    public static final class NotIncludedAutomatically
    implements BooleanSupplier {
        private NotIncludedAutomatically() {
        }

        @Override
        public boolean getAsBoolean() {
            return false;
        }
    }

    public static final class AlwaysIncluded
    implements BooleanSupplier {
        private AlwaysIncluded() {
        }

        @Override
        public boolean getAsBoolean() {
            return true;
        }
    }

    public static final class FatalExceptionHandler
    implements ExceptionHandler {
        private FatalExceptionHandler() {
        }
    }
}

