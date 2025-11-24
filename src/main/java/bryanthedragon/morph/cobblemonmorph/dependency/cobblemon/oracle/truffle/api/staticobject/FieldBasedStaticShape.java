
package com.oracle.truffle.api.staticobject;

import com.oracle.truffle.api.staticobject.StaticShape;

final class FieldBasedStaticShape<T>
extends StaticShape<T> {
    private FieldBasedStaticShape(Class<?> storageClass, boolean safetyChecks) {
        super(storageClass, safetyChecks);
    }

    static <T> FieldBasedStaticShape<T> create(Class<?> generatedStorageClass, Class<? extends T> generatedFactoryClass, boolean safetyChecks) {
        try {
            FieldBasedStaticShape<T> shape = new FieldBasedStaticShape<T>(generatedStorageClass, safetyChecks);
            T factory = generatedFactoryClass.cast(UNSAFE.allocateInstance(generatedFactoryClass));
            shape.setFactory(factory);
            return shape;
        }
        catch (InstantiationException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    Object getStorage(Object obj, boolean primitive) {
        return this.cast(obj, this.storageClass, true);
    }
}

