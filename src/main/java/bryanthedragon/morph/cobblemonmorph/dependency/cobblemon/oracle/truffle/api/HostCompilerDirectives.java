
package com.oracle.truffle.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public final class HostCompilerDirectives {
    private HostCompilerDirectives() {
    }

    @Retention(value=RetentionPolicy.RUNTIME)
    @Target(value={ElementType.METHOD, ElementType.CONSTRUCTOR})
    public static @interface InliningCutoff {
    }

    @Retention(value=RetentionPolicy.RUNTIME)
    @Target(value={ElementType.METHOD, ElementType.CONSTRUCTOR})
    @Deprecated(since="22.2")
    public static @interface BytecodeInterpreterSwitchBoundary {
    }

    @Retention(value=RetentionPolicy.RUNTIME)
    @Target(value={ElementType.METHOD, ElementType.CONSTRUCTOR})
    public static @interface BytecodeInterpreterSwitch {
    }
}

