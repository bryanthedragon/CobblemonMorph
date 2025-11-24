
package com.oracle.truffle.api.staticobject;

import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.impl.asm.ClassVisitor;
import com.oracle.truffle.api.impl.asm.FieldVisitor;
import com.oracle.truffle.api.impl.asm.Type;
import com.oracle.truffle.api.staticobject.ArrayBasedShapeGenerator;
import com.oracle.truffle.api.staticobject.FieldBasedShapeGenerator;
import com.oracle.truffle.api.staticobject.GeneratorClassLoader;
import com.oracle.truffle.api.staticobject.PodBasedShapeGenerator;
import com.oracle.truffle.api.staticobject.StaticProperty;
import com.oracle.truffle.api.staticobject.StaticShape;
import java.lang.reflect.Field;
import java.util.Map;
import sun.misc.Unsafe;

abstract class ShapeGenerator<T> {
    protected static final Unsafe UNSAFE = ShapeGenerator.getUnsafe();
    private static final String DELIMITER = "$$";

    ShapeGenerator() {
    }

    abstract StaticShape<T> generateShape(StaticShape<T> var1, Map<String, StaticProperty> var2, boolean var3, String var4);

    static <T> ShapeGenerator<T> getShapeGenerator(TruffleLanguage<?> language, GeneratorClassLoader gcl, StaticShape<T> parentShape, StaticShape.StorageStrategy strategy, String storageClassName) {
        Class<?> parentStorageClass = parentShape.getStorageClass();
        Class<?> storageSuperclass = strategy == StaticShape.StorageStrategy.ARRAY_BASED ? parentStorageClass.getSuperclass() : parentStorageClass;
        return ShapeGenerator.getShapeGenerator(language, gcl, storageSuperclass, parentShape.getFactoryInterface(), strategy, storageClassName);
    }

    static <T> ShapeGenerator<T> getShapeGenerator(TruffleLanguage<?> language, GeneratorClassLoader gcl, Class<?> storageSuperClass, Class<T> storageFactoryInterface, StaticShape.StorageStrategy strategy, String storageClassName) {
        switch (strategy) {
            case ARRAY_BASED: {
                return ArrayBasedShapeGenerator.getShapeGenerator(language, gcl, storageSuperClass, storageFactoryInterface, storageClassName);
            }
            case FIELD_BASED: {
                return FieldBasedShapeGenerator.getShapeGenerator(gcl, storageSuperClass, storageFactoryInterface);
            }
            case POD_BASED: {
                return PodBasedShapeGenerator.getShapeGenerator(storageSuperClass, storageFactoryInterface);
            }
        }
        throw new IllegalArgumentException("Unexpected strategy: " + strategy);
    }

    static String generateFactoryName(Class<?> generatedStorageClass) {
        return Type.getInternalName(generatedStorageClass) + "$$Factory";
    }

    static void addStorageFields(ClassVisitor cv, Map<String, StaticProperty> staticProperties) {
        for (Map.Entry<String, StaticProperty> entry : staticProperties.entrySet()) {
            StaticProperty property = entry.getValue();
            String descriptor = Type.getDescriptor(property.getPropertyType());
            ShapeGenerator.addStorageField(cv, entry.getKey(), descriptor, property.storeAsFinal());
        }
    }

    static void addStorageField(ClassVisitor cv, String propertyName, String descriptor, boolean storeAsFinal) {
        int access = storeAsFinal ? 17 : 1;
        FieldVisitor fv = cv.visitField(access, propertyName, descriptor, null, null);
        fv.visitEnd();
    }

    static <T> Class<? extends T> load(GeneratorClassLoader gcl, String internalName, byte[] bytes) {
        try {
            return gcl.defineGeneratedClass(internalName.replace('/', '.'), bytes, 0, bytes.length);
        }
        catch (ClassFormatError e) {
            throw new RuntimeException(e);
        }
    }

    private static Unsafe getUnsafe() {
        try {
            return Unsafe.getUnsafe();
        }
        catch (SecurityException securityException) {
            try {
                Field theUnsafeInstance = Unsafe.class.getDeclaredField("theUnsafe");
                theUnsafeInstance.setAccessible(true);
                return (Unsafe)theUnsafeInstance.get(Unsafe.class);
            }
            catch (Exception e) {
                throw new RuntimeException("exception while trying to get Unsafe.theUnsafe via reflection:", e);
            }
        }
    }
}

