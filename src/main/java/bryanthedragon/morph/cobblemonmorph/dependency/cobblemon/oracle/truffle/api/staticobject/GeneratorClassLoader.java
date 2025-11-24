
package com.oracle.truffle.api.staticobject;

final class GeneratorClassLoader
extends ClassLoader {
    private final Class<?> referenceClass;

    GeneratorClassLoader(Class<?> referenceClass) {
        super(referenceClass.getClassLoader());
        this.referenceClass = referenceClass;
    }

    Class<?> defineGeneratedClass(String name, byte[] b, int off, int len) throws ClassFormatError {
        return this.defineClass(name, b, off, len, this.referenceClass.getProtectionDomain());
    }
}

