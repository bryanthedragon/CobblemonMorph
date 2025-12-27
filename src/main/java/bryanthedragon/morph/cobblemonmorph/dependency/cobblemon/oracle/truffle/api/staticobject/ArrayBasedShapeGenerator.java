package com.oracle.truffle.api.staticobject;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.TruffleOptions;
import com.oracle.truffle.api.impl.asm.ClassVisitor;
import com.oracle.truffle.api.impl.asm.ClassWriter;
import com.oracle.truffle.api.impl.asm.Label;
import com.oracle.truffle.api.impl.asm.MethodVisitor;
import com.oracle.truffle.api.impl.asm.Opcodes;
import com.oracle.truffle.api.impl.asm.Type;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.graalvm.collections.Pair;
import org.graalvm.nativeimage.ImageInfo;

final class ArrayBasedShapeGenerator<T> extends ShapeGenerator<T> {
   private static final ConcurrentHashMap<Pair<Class<?>, Class<?>>, Object> generatorCache = TruffleOptions.AOT ? new ConcurrentHashMap<>() : null;
   private static final String[] ARRAY_SIZE_FIELDS = new String[]{"primitiveArraySize", "objectArraySize"};
   private static final String STATIC_SHAPE_DESCRIPTOR = Type.getDescriptor(ArrayBasedStaticShape.class);
   private final Class<?> generatedStorageClass;
   private final Class<? extends T> generatedFactoryClass;
   @CompilerDirectives.CompilationFinal
   private int byteArrayOffset;
   @CompilerDirectives.CompilationFinal
   private int objectArrayOffset;
   @CompilerDirectives.CompilationFinal
   private int shapeOffset;

   private ArrayBasedShapeGenerator(Class<?> generatedStorageClass, Class<? extends T> generatedFactoryClass) {
      this(
         generatedStorageClass,
         generatedFactoryClass,
         getObjectFieldOffset(generatedStorageClass, "primitive"),
         getObjectFieldOffset(generatedStorageClass, "object"),
         getObjectFieldOffset(generatedStorageClass, "shape")
      );
   }

   private ArrayBasedShapeGenerator(
      Class<?> generatedStorageClass, Class<? extends T> generatedFactoryClass, int byteArrayOffset, int objectArrayOffset, int shapeOffset
   ) {
      this.generatedStorageClass = generatedStorageClass;
      this.generatedFactoryClass = generatedFactoryClass;
      this.byteArrayOffset = byteArrayOffset;
      this.objectArrayOffset = objectArrayOffset;
      this.shapeOffset = shapeOffset;
   }

   int getByteArrayOffset() {
      return this.byteArrayOffset;
   }

   int getObjectArrayOffset() {
      return this.objectArrayOffset;
   }

   int getShapeOffset() {
      return this.shapeOffset;
   }

   static <T> ArrayBasedShapeGenerator<T> getShapeGenerator(
      TruffleLanguage<?> language, GeneratorClassLoader gcl, Class<?> storageSuperClass, Class<T> storageFactoryInterface, String storageClassName
   ) {
      ConcurrentHashMap<Pair<Class<?>, Class<?>>, Object> cache;
      if (TruffleOptions.AOT) {
         cache = generatorCache;
      } else {
         cache = SomAccessor.ENGINE.getGeneratorCache(SomAccessor.LANGUAGE.getPolyglotLanguageInstance(language));
      }

      Pair<Class<?>, Class<?>> pair = Pair.create(storageSuperClass, storageFactoryInterface);
      ArrayBasedShapeGenerator<T> sg = (ArrayBasedShapeGenerator<T>)cache.get(pair);
      if (sg == null) {
         if (ImageInfo.inImageRuntimeCode()) {
            throw new IllegalStateException("This code should not be executed at Native Image run time. Please report this issue");
         }

         Class<?> generatedStorageClass = generateStorage(gcl, storageSuperClass, storageClassName);
         Class<? extends T> generatedFactoryClass = generateFactory(gcl, generatedStorageClass, storageFactoryInterface);
         sg = new ArrayBasedShapeGenerator<>(generatedStorageClass, generatedFactoryClass);
         ArrayBasedShapeGenerator<T> prevSg = (ArrayBasedShapeGenerator<T>)cache.putIfAbsent(pair, sg);
         if (prevSg != null) {
            sg = prevSg;
         }
      }

      return sg;
   }

   private static int getObjectFieldOffset(Class<?> c, String fieldName) {
      try {
         return Math.toIntExact(UNSAFE.objectFieldOffset(c.getField(fieldName)));
      } catch (NoSuchFieldException var3) {
         throw new RuntimeException(var3);
      }
   }

   @Override
   StaticShape<T> generateShape(StaticShape<T> parentShape, Map<String, StaticProperty> staticProperties, boolean safetyChecks, String storageClassName) {
      return ArrayBasedStaticShape.create(
         this, this.generatedStorageClass, this.generatedFactoryClass, (ArrayBasedStaticShape<T>)parentShape, staticProperties.values(), safetyChecks
      );
   }

   void patchOffsets(int nativeByteArrayOffset, int nativeObjectArrayOffset, int nativeShapeOffset) {
      assert TruffleOptions.AOT;

      CompilerAsserts.neverPartOfCompilation();
      this.byteArrayOffset = nativeByteArrayOffset;
      this.objectArrayOffset = nativeObjectArrayOffset;
      this.shapeOffset = nativeShapeOffset;
   }

   private static String getStorageConstructorDescriptor(Constructor<?> superConstructor) {
      StringBuilder sb = new StringBuilder();
      sb.append('(');

      for (Class<?> parameter : superConstructor.getParameterTypes()) {
         sb.append(Type.getDescriptor(parameter));
      }

      sb.append(STATIC_SHAPE_DESCRIPTOR);
      return sb.append("II)V").toString();
   }

   private static Object getFrameLocal(Class<?> clazz) {
      if (clazz.isPrimitive()) {
         if (clazz == boolean.class || clazz == byte.class || clazz == char.class || clazz == int.class || clazz == short.class) {
            return Opcodes.INTEGER;
         } else if (clazz == double.class) {
            return Opcodes.DOUBLE;
         } else if (clazz == float.class) {
            return Opcodes.FLOAT;
         } else if (clazz == long.class) {
            return Opcodes.LONG;
         } else {
            throw new AssertionError();
         }
      } else {
         return Type.getInternalName(clazz);
      }
   }

   private static Object[] getFrameLocals(String storageName, Class<?>[] constructorParameters) {
      Object[] frameLocals = new Object[constructorParameters.length + 4];
      int idx = 0;

      for (frameLocals[idx++] = storageName; idx <= constructorParameters.length; idx++) {
         frameLocals[idx] = getFrameLocal(constructorParameters[idx - 1]);
      }

      frameLocals[idx++] = Type.getInternalName(ArrayBasedStaticShape.class);
      frameLocals[idx++] = Opcodes.INTEGER;
      frameLocals[idx++] = Opcodes.INTEGER;
      return frameLocals;
   }

   private static void addStorageConstructors(ClassVisitor cv, String storageName, Class<?> storageSuperClass, String storageSuperName) {
      for (Constructor<?> superConstructor : storageSuperClass.getDeclaredConstructors()) {
         String storageConstructorDescriptor = getStorageConstructorDescriptor(superConstructor);
         String superConstructorDescriptor = Type.getConstructorDescriptor(superConstructor);
         MethodVisitor mv = cv.visitMethod(1, "<init>", storageConstructorDescriptor, null, null);
         mv.visitCode();
         mv.visitVarInsn(25, 0);
         int maxStack = 1;
         int maxLocals = 1;
         Class<?>[] constructorParameters = superConstructor.getParameterTypes();

         for (Class<?> constructorParameter : constructorParameters) {
            Type parameterType = Type.getType(constructorParameter);
            int loadOpcode = parameterType.getOpcode(21);
            mv.visitVarInsn(loadOpcode, maxLocals);
            int parameterSize = parameterType.getSize();
            maxStack += parameterSize;
            maxLocals += parameterSize;
         }

         mv.visitMethodInsn(183, storageSuperName, "<init>", superConstructorDescriptor, false);
         Object[] frameLocals = getFrameLocals(storageName, constructorParameters);
         mv.visitVarInsn(25, 0);
         mv.visitVarInsn(25, maxLocals++);
         mv.visitFieldInsn(181, storageName, "shape", STATIC_SHAPE_DESCRIPTOR);
         mv.visitVarInsn(25, 0);
         mv.visitVarInsn(21, maxLocals);
         Label lNoPrimitives = new Label();
         mv.visitJumpInsn(158, lNoPrimitives);
         mv.visitVarInsn(21, maxLocals++);
         mv.visitIntInsn(188, 8);
         Label lSetPrimitive = new Label();
         mv.visitJumpInsn(167, lSetPrimitive);
         mv.visitLabel(lNoPrimitives);
         mv.visitFrame(0, frameLocals.length, frameLocals, 1, new Object[]{storageName});
         mv.visitInsn(1);
         mv.visitLabel(lSetPrimitive);
         mv.visitFrame(0, frameLocals.length, frameLocals, 2, new Object[]{storageName, "[B"});
         mv.visitFieldInsn(181, storageName, "primitive", "[B");
         mv.visitVarInsn(25, 0);
         mv.visitVarInsn(21, maxLocals);
         Label lNoObjects = new Label();
         mv.visitJumpInsn(158, lNoObjects);
         mv.visitVarInsn(21, maxLocals++);
         mv.visitTypeInsn(189, "java/lang/Object");
         Label lSetObject = new Label();
         mv.visitJumpInsn(167, lSetObject);
         mv.visitLabel(lNoObjects);
         mv.visitFrame(0, frameLocals.length, frameLocals, 1, new Object[]{storageName});
         mv.visitInsn(1);
         mv.visitLabel(lSetObject);
         mv.visitFrame(0, frameLocals.length, frameLocals, 2, new Object[]{storageName, "[Ljava/lang/Object;"});
         mv.visitFieldInsn(181, storageName, "object", "[Ljava/lang/Object;");
         mv.visitInsn(177);
         mv.visitMaxs(Math.max(maxStack, 3), maxLocals);
         mv.visitEnd();
      }
   }

   private static Method getCloneMethod(Class<?> storageSuperClass) {
      for (Class<?> clazz = storageSuperClass; clazz != null; clazz = clazz.getSuperclass()) {
         try {
            return clazz.getDeclaredMethod("clone");
         } catch (NoSuchMethodException var3) {
         }
      }

      throw new RuntimeException("Should not reach here");
   }

   private static String[] getCloneMethodExceptions(Method cloneMethod) {
      return Arrays.stream(cloneMethod.getExceptionTypes()).map(c -> Type.getInternalName((Class<?>)c)).toArray(String[]::new);
   }

   private static void addCloneMethod(Class<?> storageSuperClass, ClassVisitor cv, String className) {
      Object[] frameLocals = new Object[]{className, className};
      Method superCloneMethod = getCloneMethod(storageSuperClass);
      String superCloneMethodDescriptor = Type.getMethodDescriptor(superCloneMethod);
      MethodVisitor mv = cv.visitMethod(1, "clone", superCloneMethodDescriptor, null, getCloneMethodExceptions(superCloneMethod));
      mv.visitVarInsn(25, 0);
      mv.visitMethodInsn(183, Type.getInternalName(storageSuperClass), "clone", superCloneMethodDescriptor, false);
      mv.visitTypeInsn(192, className);
      mv.visitVarInsn(58, 1);
      mv.visitVarInsn(25, 1);
      mv.visitVarInsn(25, 0);
      mv.visitFieldInsn(180, className, "primitive", "[B");
      Label lHasPrimitives = new Label();
      mv.visitJumpInsn(199, lHasPrimitives);
      mv.visitInsn(1);
      Label lSetPrimitive = new Label();
      mv.visitJumpInsn(167, lSetPrimitive);
      mv.visitLabel(lHasPrimitives);
      mv.visitFrame(0, 2, frameLocals, 1, new Object[]{className});
      mv.visitVarInsn(25, 0);
      mv.visitFieldInsn(180, className, "primitive", "[B");
      mv.visitMethodInsn(182, "[B", "clone", "()Ljava/lang/Object;", false);
      mv.visitTypeInsn(192, "[B");
      mv.visitTypeInsn(192, "[B");
      mv.visitLabel(lSetPrimitive);
      mv.visitFrame(0, 2, frameLocals, 2, new Object[]{className, "[B"});
      mv.visitFieldInsn(181, className, "primitive", "[B");
      mv.visitVarInsn(25, 1);
      mv.visitVarInsn(25, 0);
      mv.visitFieldInsn(180, className, "object", "[Ljava/lang/Object;");
      Label lHasObjects = new Label();
      mv.visitJumpInsn(199, lHasObjects);
      mv.visitInsn(1);
      Label lSetObject = new Label();
      mv.visitJumpInsn(167, lSetObject);
      mv.visitLabel(lHasObjects);
      mv.visitFrame(0, 2, frameLocals, 1, new Object[]{className});
      mv.visitVarInsn(25, 0);
      mv.visitFieldInsn(180, className, "object", "[Ljava/lang/Object;");
      mv.visitMethodInsn(182, "[Ljava/lang/Object;", "clone", "()Ljava/lang/Object;", false);
      mv.visitTypeInsn(192, "[Ljava/lang/Object;");
      mv.visitTypeInsn(192, "[Ljava/lang/Object;");
      mv.visitLabel(lSetObject);
      mv.visitFrame(0, 2, frameLocals, 2, new Object[]{className, "[Ljava/lang/Object;"});
      mv.visitFieldInsn(181, className, "object", "[Ljava/lang/Object;");
      mv.visitVarInsn(25, 1);
      mv.visitInsn(176);
      mv.visitMaxs(3, 3);
      mv.visitEnd();
   }

   private static void addFactoryFields(ClassVisitor cv) {
      cv.visitField(17, "shape", STATIC_SHAPE_DESCRIPTOR, null, null).visitEnd();
      cv.visitField(17, "primitiveArraySize", "I", null, null).visitEnd();
      cv.visitField(17, "objectArraySize", "I", null, null).visitEnd();
   }

   private static void addFactoryConstructor(ClassVisitor cv, String className) {
      MethodVisitor mv = cv.visitMethod(1, "<init>", "(" + STATIC_SHAPE_DESCRIPTOR + "II)V", null, null);
      mv.visitCode();
      mv.visitVarInsn(25, 0);
      mv.visitMethodInsn(183, Type.getInternalName(Object.class), "<init>", "()V", false);
      mv.visitVarInsn(25, 0);
      mv.visitVarInsn(25, 1);
      mv.visitFieldInsn(181, className, "shape", STATIC_SHAPE_DESCRIPTOR);
      mv.visitVarInsn(25, 0);
      mv.visitVarInsn(21, 2);
      mv.visitFieldInsn(181, className, "primitiveArraySize", "I");
      mv.visitVarInsn(25, 0);
      mv.visitVarInsn(21, 3);
      mv.visitFieldInsn(181, className, "objectArraySize", "I");
      mv.visitInsn(177);
      mv.visitMaxs(2, 4);
      mv.visitEnd();
   }

   private static void addFactoryMethods(ClassVisitor cv, Class<?> storageClass, Class<?> storageFactoryInterface, String factoryName) {
      for (Method m : storageFactoryInterface.getMethods()) {
         MethodVisitor mv = cv.visitMethod(17, m.getName(), Type.getMethodDescriptor(m), null, null);
         mv.visitCode();
         mv.visitTypeInsn(187, Type.getInternalName(storageClass));
         mv.visitInsn(89);
         StringBuilder constructorDescriptor = new StringBuilder();
         constructorDescriptor.append('(');
         Class<?>[] params = m.getParameterTypes();
         int maxStack = 2;
         int maxLocals = 1;

         for (Class<?> param : params) {
            Type parameterType = Type.getType(param);
            int loadOpcode = parameterType.getOpcode(21);
            mv.visitVarInsn(loadOpcode, maxLocals);
            int parameterSize = parameterType.getSize();
            maxStack += parameterSize;
            maxLocals += parameterSize;
            constructorDescriptor.append(Type.getDescriptor(param));
         }

         constructorDescriptor.append(STATIC_SHAPE_DESCRIPTOR);
         mv.visitVarInsn(25, 0);
         mv.visitFieldInsn(180, factoryName, "shape", STATIC_SHAPE_DESCRIPTOR);
         maxStack++;

         for (String fieldName : ARRAY_SIZE_FIELDS) {
            constructorDescriptor.append("I");
            mv.visitVarInsn(25, 0);
            mv.visitFieldInsn(180, factoryName, fieldName, "I");
            maxStack++;
         }

         constructorDescriptor.append(")V");
         String storageName = Type.getInternalName(storageClass);
         mv.visitMethodInsn(183, storageName, "<init>", constructorDescriptor.toString(), false);
         mv.visitInsn(176);
         mv.visitMaxs(maxStack, maxLocals);
         mv.visitEnd();
      }
   }

   private static Class<?> generateStorage(GeneratorClassLoader gcl, Class<?> storageSuperClass, String storageClassName) {
      String storageSuperName = Type.getInternalName(storageSuperClass);
      ClassWriter storageWriter = new ClassWriter(0);
      int storageAccess = 4129;
      storageWriter.visit(52, storageAccess, storageClassName, null, storageSuperName, null);
      addStorageConstructors(storageWriter, storageClassName, storageSuperClass, storageSuperName);
      addStorageField(storageWriter, "primitive", "[B", true);
      addStorageField(storageWriter, "object", "[Ljava/lang/Object;", true);
      addStorageField(storageWriter, "shape", STATIC_SHAPE_DESCRIPTOR, true);
      if (Cloneable.class.isAssignableFrom(storageSuperClass)) {
         addCloneMethod(storageSuperClass, storageWriter, storageClassName);
      }

      storageWriter.visitEnd();
      return load(gcl, storageClassName, storageWriter.toByteArray());
   }

   private static <T> Class<? extends T> generateFactory(GeneratorClassLoader gcl, Class<?> storageClass, Class<T> storageFactoryInterface) {
      ClassWriter factoryWriter = new ClassWriter(0);
      int factoryAccess = 4145;
      String factoryName = generateFactoryName(storageClass);
      factoryWriter.visit(52, factoryAccess, factoryName, null, Type.getInternalName(Object.class), new String[]{Type.getInternalName(storageFactoryInterface)});
      addFactoryFields(factoryWriter);
      addFactoryConstructor(factoryWriter, factoryName);
      addFactoryMethods(factoryWriter, storageClass, storageFactoryInterface, factoryName);
      factoryWriter.visitEnd();
      return load(gcl, factoryName, factoryWriter.toByteArray());
   }
}
