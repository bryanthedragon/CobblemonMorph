package com.oracle.truffle.api.staticobject;

import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.impl.asm.ClassVisitor;
import com.oracle.truffle.api.impl.asm.FieldVisitor;
import com.oracle.truffle.api.impl.asm.Type;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.Map.Entry;
import sun.misc.Unsafe;

abstract class ShapeGenerator<T> {
   protected static final Unsafe UNSAFE = getUnsafe();
   private static final String DELIMITER = "$$";

   abstract StaticShape<T> generateShape(
      StaticShape<T> parentShape, Map<String, StaticProperty> staticProperties, boolean safetyChecks, String storageClassName
   );

   static <T> ShapeGenerator<T> getShapeGenerator(
      TruffleLanguage<?> language, GeneratorClassLoader gcl, StaticShape<T> parentShape, StaticShape.StorageStrategy strategy, String storageClassName
   ) {
      Class<?> parentStorageClass = parentShape.getStorageClass();
      Class<?> storageSuperclass = strategy == StaticShape.StorageStrategy.ARRAY_BASED ? parentStorageClass.getSuperclass() : parentStorageClass;
      return getShapeGenerator(language, gcl, storageSuperclass, parentShape.getFactoryInterface(), strategy, storageClassName);
   }

   static <T> ShapeGenerator<T> getShapeGenerator(
      TruffleLanguage<?> language,
      GeneratorClassLoader gcl,
      Class<?> storageSuperClass,
      Class<T> storageFactoryInterface,
      StaticShape.StorageStrategy strategy,
      String storageClassName
   ) {
      switch (strategy) {
         case ARRAY_BASED:
            return ArrayBasedShapeGenerator.getShapeGenerator(language, gcl, storageSuperClass, storageFactoryInterface, storageClassName);
         case FIELD_BASED:
            return FieldBasedShapeGenerator.getShapeGenerator(gcl, storageSuperClass, storageFactoryInterface);
         case POD_BASED:
            return PodBasedShapeGenerator.getShapeGenerator(storageSuperClass, storageFactoryInterface);
         default:
            throw new IllegalArgumentException("Unexpected strategy: " + strategy);
      }
   }

   static String generateFactoryName(Class<?> generatedStorageClass) {
      return Type.getInternalName(generatedStorageClass) + "$$Factory";
   }

   static void addStorageFields(ClassVisitor cv, Map<String, StaticProperty> staticProperties) {
      for (Entry<String, StaticProperty> entry : staticProperties.entrySet()) {
         StaticProperty property = entry.getValue();
         String descriptor = Type.getDescriptor(property.getPropertyType());
         addStorageField(cv, entry.getKey(), descriptor, property.storeAsFinal());
      }
   }

   static void addStorageField(ClassVisitor cv, String propertyName, String descriptor, boolean storeAsFinal) {
      int access = storeAsFinal ? 17 : 1;
      FieldVisitor fv = cv.visitField(access, propertyName, descriptor, null, null);
      fv.visitEnd();
   }

   static <T> Class<? extends T> load(GeneratorClassLoader gcl, String internalName, byte[] bytes) {
      try {
         return (Class<? extends T>)gcl.defineGeneratedClass(internalName.replace('/', '.'), bytes, 0, bytes.length);
      } catch (ClassFormatError var4) {
         throw new RuntimeException(var4);
      }
   }

   private static Unsafe getUnsafe() {
      try {
         return Unsafe.getUnsafe();
      } catch (SecurityException var2) {
         try {
            Field theUnsafeInstance = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafeInstance.setAccessible(true);
            return (Unsafe)theUnsafeInstance.get(Unsafe.class);
         } catch (Exception var1) {
            throw new RuntimeException("exception while trying to get Unsafe.theUnsafe via reflection:", var1);
         }
      }
   }
}
