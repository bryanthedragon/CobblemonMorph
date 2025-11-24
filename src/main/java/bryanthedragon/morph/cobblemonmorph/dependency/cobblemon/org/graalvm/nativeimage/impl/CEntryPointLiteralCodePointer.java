
package org.graalvm.nativeimage.impl;

import org.graalvm.nativeimage.c.function.CFunctionPointer;
import org.graalvm.word.ComparableWord;

public class CEntryPointLiteralCodePointer
implements CFunctionPointer {
    public final Class<?> definingClass;
    public final String methodName;
    public final Class<?>[] parameterTypes;

    public CEntryPointLiteralCodePointer(Class<?> definingClass, String methodName, Class<?> ... parameterTypes) {
        this.definingClass = definingClass;
        this.methodName = methodName;
        this.parameterTypes = parameterTypes;
    }

    @Override
    public boolean isNull() {
        throw new IllegalStateException("Cannot invoke method during native image generation");
    }

    @Override
    public boolean isNonNull() {
        throw new IllegalStateException("Cannot invoke method during native image generation");
    }

    @Override
    public boolean equal(ComparableWord val) {
        throw new IllegalStateException("Cannot invoke method during native image generation");
    }

    @Override
    public boolean notEqual(ComparableWord val) {
        throw new IllegalStateException("Cannot invoke method during native image generation");
    }

    @Override
    public long rawValue() {
        throw new IllegalStateException("Cannot invoke method during native image generation");
    }
}

