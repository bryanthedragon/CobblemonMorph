package com.oracle.truffle.api.staticobject;

import com.oracle.truffle.api.impl.asm.ClassVisitor;
import com.oracle.truffle.api.impl.asm.ClassWriter;
import com.oracle.truffle.api.impl.asm.MethodVisitor;
import com.oracle.truffle.api.impl.asm.Type;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Map.Entry;

final class FieldBasedShapeGenerator<T> extends ShapeGenerator<T> {
   private final GeneratorClassLoader gcl;
   private final Class<?> storageSuperClass;
   private final Class<T> storageFactoryInterface;

   private FieldBasedShapeGenerator(GeneratorClassLoader gcl, Class<?> storageSuperClass, Class<T> storageFactoryInterface) {
      this.gcl = gcl;
      this.storageSuperClass = storageSuperClass;
      this.storageFactoryInterface = storageFactoryInterface;
   }

   static <T> FieldBasedShapeGenerator<T> getShapeGenerator(GeneratorClassLoader gcl, Class<?> storageSuperClass, Class<T> storageFactoryInterface) {
      return new FieldBasedShapeGenerator<>(gcl, storageSuperClass, storageFactoryInterface);
   }

   @Override
   StaticShape<T> generateShape(StaticShape<T> parentShape, Map<String, StaticProperty> staticProperties, boolean safetyChecks, String storageClassName) {
      Class<?> generatedStorageClass = generateStorage(this.gcl, this.storageSuperClass, staticProperties, storageClassName);
      Class<? extends T> generatedFactoryClass = generateFactory(this.gcl, generatedStorageClass, this.storageFactoryInterface);

      for (Entry<String, StaticProperty> entry : staticProperties.entrySet()) {
         int offset = getObjectFieldOffset(generatedStorageClass, entry.getKey());
         entry.getValue().initOffset(offset);
      }

      return FieldBasedStaticShape.create(generatedStorageClass, generatedFactoryClass, safetyChecks);
   }

   private static int getObjectFieldOffset(Class<?> c, String fieldName) {
      try {
         return Math.toIntExact(UNSAFE.objectFieldOffset(c.getField(fieldName)));
      } catch (NoSuchFieldException var3) {
         throw new RuntimeException(var3);
      }
   }

   private static String getStorageConstructorDescriptor(Constructor<?> superConstructor) {
      return Type.getConstructorDescriptor(superConstructor);
   }

   private static void addStorageConstructors(ClassVisitor cv, Class<?> storageSuperClass, String storageSuperName) {
      for (Constructor<?> superConstructor : storageSuperClass.getDeclaredConstructors()) {
         String storageConstructorDescriptor = getStorageConstructorDescriptor(superConstructor);
         MethodVisitor mv = cv.visitMethod(1, "<init>", storageConstructorDescriptor, null, null);
         mv.visitCode();
         mv.visitVarInsn(25, 0);
         int var = 1;

         for (Class<?> constructorParameter : superConstructor.getParameterTypes()) {
            Type parameterType = Type.getType(constructorParameter);
            int loadOpcode = parameterType.getOpcode(21);
            mv.visitVarInsn(loadOpcode, var);
            var += parameterType.getSize();
         }

         mv.visitMethodInsn(183, storageSuperName, "<init>", storageConstructorDescriptor, false);
         mv.visitInsn(177);
         mv.visitMaxs(var + 1, var);
         mv.visitEnd();
      }
   }

   private static void addFactoryConstructor(ClassVisitor cv) {
      MethodVisitor mv = cv.visitMethod(1, "<init>", "()V", null, null);
      mv.visitCode();
      mv.visitVarInsn(25, 0);
      mv.visitMethodInsn(183, Type.getInternalName(Object.class), "<init>", "()V", false);
      mv.visitInsn(177);
      mv.visitMaxs(1, 1);
      mv.visitEnd();
   }

   private static void addFactoryMethods(ClassVisitor cv, Class<?> storageClass, Class<?> storageFactoryInterface) {
      for (Method m : storageFactoryInterface.getMethods()) {
         MethodVisitor mv = cv.visitMethod(17, m.getName(), Type.getMethodDescriptor(m), null, null);
         mv.visitCode();
         mv.visitTypeInsn(187, Type.getInternalName(storageClass));
         mv.visitInsn(89);
         int maxStack = 2;
         int maxLocals = 1;
         StringBuilder constructorDescriptor = new StringBuilder();
         constructorDescriptor.append('(');
         Class<?>[] params = m.getParameterTypes();

         for (Class<?> param : params) {
            Type paramType = Type.getType(param);
            int loadOpcode = paramType.getOpcode(21);
            mv.visitVarInsn(loadOpcode, maxLocals);
            constructorDescriptor.append(Type.getDescriptor(param));
            maxStack += paramType.getSize();
            maxLocals += paramType.getSize();
         }

         constructorDescriptor.append(")V");
         String storageName = Type.getInternalName(storageClass);
         mv.visitMethodInsn(183, storageName, "<init>", constructorDescriptor.toString(), false);
         mv.visitInsn(176);
         mv.visitMaxs(maxStack, maxLocals);
         mv.visitEnd();
      }
   }

   private static Class<?> generateStorage(
      GeneratorClassLoader gcl, Class<?> storageSuperClass, Map<String, StaticProperty> staticProperties, String storageClassName
   ) {
      String storageSuperName = Type.getInternalName(storageSuperClass);
      ClassWriter storageWriter = new ClassWriter(0);
      int storageAccess = 4129;
      storageWriter.visit(52, storageAccess, storageClassName, null, storageSuperName, null);
      addStorageConstructors(storageWriter, storageSuperClass, storageSuperName);
      addStorageFields(storageWriter, staticProperties);
      storageWriter.visitEnd();
      return load(gcl, storageClassName, storageWriter.toByteArray());
   }

   private static <T> Class<? extends T> generateFactory(GeneratorClassLoader gcl, Class<?> storageClass, Class<T> storageFactoryInterface) {
      ClassWriter factoryWriter = new ClassWriter(0);
      int factoryAccess = 4145;
      String factoryName = generateFactoryName(storageClass);
      factoryWriter.visit(52, factoryAccess, factoryName, null, Type.getInternalName(Object.class), new String[]{Type.getInternalName(storageFactoryInterface)});
      addFactoryConstructor(factoryWriter);
      addFactoryMethods(factoryWriter, storageClass, storageFactoryInterface);
      factoryWriter.visitEnd();
      return load(gcl, factoryName, factoryWriter.toByteArray());
   }
}
