package com.oracle.truffle.api.staticobject;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.TruffleLanguage;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import org.graalvm.nativeimage.ImageInfo;
import sun.misc.Unsafe;

public abstract class StaticShape<T> {
   static final Unsafe UNSAFE = getUnsafe();
   final Class<?> storageClass;
   final boolean safetyChecks;
   @CompilerDirectives.CompilationFinal
   T factory;

   StaticShape(Class<?> storageClass, boolean safetyChecks) {
      this.storageClass = storageClass;
      this.safetyChecks = safetyChecks;
   }

   public static StaticShape.Builder newBuilder(TruffleLanguage<?> language) {
      return new StaticShape.Builder(language);
   }

   final void setFactory(T factory) {
      assert this.factory == null;

      this.factory = factory;
   }

   public final T getFactory() {
      return this.factory;
   }

   final Class<?> getStorageClass() {
      return this.storageClass;
   }

   abstract Object getStorage(Object obj, boolean primitive);

   final <U> U cast(Object obj, Class<U> type, boolean checkCondition) {
      if (this.safetyChecks) {
         return checkedCast(obj, type);
      } else {
         assert checkedCast(obj, type) != null;

         return SomAccessor.RUNTIME.unsafeCast(obj, type, !checkCondition || type.isInstance(obj), false, false);
      }
   }

   final Class<T> getFactoryInterface() {
      assert this.factory.getClass().getInterfaces().length == 1;

      return (Class<T>)this.factory.getClass().getInterfaces()[0];
   }

   private static <U> U checkedCast(Object obj, Class<U> type) {
      try {
         return type.cast(obj);
      } catch (ClassCastException var3) {
         throw new IllegalArgumentException("Object '" + obj + "' of class '" + obj.getClass().getName() + "' does not have the expected shape", var3);
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

   public static final class Builder {
      private static final int MAX_NUMBER_OF_PROPERTIES = 65535;
      private static final int MAX_PROPERTY_ID_BYTE_LENGTH = 65535;
      private static final String DELIMITER = "$$";
      private static final AtomicInteger counter = new AtomicInteger();
      private final String storageClassName;
      private final HashMap<String, StaticProperty> staticProperties = new LinkedHashMap<>();
      private final TruffleLanguage<?> language;
      boolean hasLongPropertyId = false;
      boolean isActive = true;

      Builder(TruffleLanguage<?> language) {
         this.language = language;
         this.storageClassName = storageClassName();
      }

      static String storageClassName() {
         return ShapeGenerator.class.getPackage().getName().replace('.', '/') + "/GeneratedStaticObject$$" + counter.incrementAndGet();
      }

      public StaticShape.Builder property(StaticProperty property, Class<?> type, boolean storeAsFinal) {
         CompilerAsserts.neverPartOfCompilation();
         StaticPropertyValidator.validate(type);
         this.checkStatus();
         property.init(type, storeAsFinal);
         this.staticProperties.put(this.validateAndGetId(property), property);
         return this;
      }

      public StaticShape<DefaultStaticObjectFactory> build() {
         return this.build(Object.class, DefaultStaticObjectFactory.class);
      }

      public <T> StaticShape<T> build(StaticShape<T> parentShape) {
         Objects.requireNonNull(parentShape);
         GeneratorClassLoader gcl = this.getOrCreateClassLoader(parentShape.getFactoryInterface());
         ShapeGenerator<T> sg = ShapeGenerator.getShapeGenerator(this.language, gcl, parentShape, this.getStorageStrategy(), this.storageClassName);
         return this.build(sg, parentShape);
      }

      public <T> StaticShape<T> build(Class<?> superClass, Class<T> factoryInterface) {
         validateClasses(superClass, factoryInterface);
         GeneratorClassLoader gcl = this.getOrCreateClassLoader(factoryInterface);
         ShapeGenerator<T> sg = ShapeGenerator.getShapeGenerator(
            this.language, gcl, superClass, factoryInterface, this.getStorageStrategy(), this.storageClassName
         );
         return this.build(sg, null);
      }

      private <T> StaticShape<T> build(ShapeGenerator<T> sg, StaticShape<T> parentShape) {
         CompilerAsserts.neverPartOfCompilation();
         this.checkStatus();
         Map<String, StaticProperty> properties = (Map<String, StaticProperty>)(this.hasLongPropertyId
            ? defaultPropertyIds(this.staticProperties)
            : this.staticProperties);
         boolean safetyChecks = !SomAccessor.ENGINE.areStaticObjectSafetyChecksRelaxed(SomAccessor.LANGUAGE.getPolyglotLanguageInstance(this.language));
         StaticShape<T> shape = sg.generateShape(parentShape, properties, safetyChecks, this.storageClassName);

         for (StaticProperty staticProperty : properties.values()) {
            staticProperty.initShape(shape);
         }

         this.setInactive();
         return shape;
      }

      private void checkStatus() {
         if (!this.isActive) {
            throw new IllegalStateException(
               "This Builder instance has already built a StaticShape. It is not possible to add static properties or build other shapes"
            );
         }
      }

      private void setInactive() {
         this.isActive = false;
      }

      private GeneratorClassLoader getOrCreateClassLoader(Class<?> referenceClass) {
         ClassLoader cl = SomAccessor.ENGINE.getStaticObjectClassLoader(SomAccessor.LANGUAGE.getPolyglotLanguageInstance(this.language), referenceClass);
         if (cl == null) {
            cl = new GeneratorClassLoader(referenceClass);
            SomAccessor.ENGINE.setStaticObjectClassLoader(SomAccessor.LANGUAGE.getPolyglotLanguageInstance(this.language), referenceClass, cl);
         }

         if (!GeneratorClassLoader.class.isInstance(cl)) {
            throw new RuntimeException("The Truffle language instance associated to this Builder returned an unexpected class loader");
         } else {
            return (GeneratorClassLoader)cl;
         }
      }

      private String validateAndGetId(StaticProperty property) {
         String id = property.getId();
         Objects.requireNonNull(id);
         if (this.staticProperties.size() == 65535) {
            throw new IllegalArgumentException("This builder already contains the maximum number of properties: 65535");
         } else if (id.length() == 0) {
            throw new IllegalArgumentException("The property id cannot be an empty string");
         } else {
            id = id.replace("_", "__");
            id = id.replace(".", "_,");
            id = id.replace(";", "_:");
            id = id.replace("[", "_]");
            id = id.replace("/", "_\\");
            if (this.staticProperties.containsKey(id)) {
               throw new IllegalArgumentException("This builder already contains a property with id '" + id + "'");
            } else {
               if (modifiedUtfLength(id) > 65535) {
                  this.hasLongPropertyId = true;
               }

               return id;
            }
         }
      }

      private static void validateClasses(Class<?> storageSuperClass, Class<?> storageFactoryInterface) {
         CompilerAsserts.neverPartOfCompilation();
         if (!storageFactoryInterface.isInterface()) {
            throw new IllegalArgumentException(storageFactoryInterface.getName() + " must be an interface.");
         } else {
            for (Method m : storageFactoryInterface.getMethods()) {
               if (!m.getReturnType().isAssignableFrom(storageSuperClass)) {
                  throw new IllegalArgumentException("The return type of '" + m + "' is not assignable from '" + storageSuperClass.getName() + "'");
               }

               try {
                  storageSuperClass.getDeclaredConstructor(m.getParameterTypes());
               } catch (NoSuchMethodException var7) {
                  throw new IllegalArgumentException("Method '" + m + "' does not match any constructor in '" + storageSuperClass.getName() + "'", var7);
               }
            }

            if (!isClassVisible(storageFactoryInterface.getClassLoader(), StaticShape.class)) {
               throw new IllegalArgumentException(
                  "The class loader of factory interface '"
                     + storageFactoryInterface.getName()
                     + "' (cl: '"
                     + storageFactoryInterface.getClassLoader()
                     + "') must have visibility of '"
                     + StaticShape.class.getName()
                     + "' (cl: '"
                     + StaticShape.class.getClassLoader()
                     + "')"
               );
            } else {
               for (Class<?> c = storageSuperClass; c != null; c = c.getSuperclass()) {
                  for (Method m : c.getDeclaredMethods()) {
                     if (Modifier.isAbstract(m.getModifiers())) {
                        throw new IllegalArgumentException("'" + storageSuperClass.getName() + "' has abstract methods");
                     }
                  }
               }

               if (Cloneable.class.isAssignableFrom(storageSuperClass)) {
                  Method clone = getCloneMethod(storageSuperClass);
                  if (clone != null && Modifier.isFinal(clone.getModifiers())) {
                     throw new IllegalArgumentException("'" + storageSuperClass.getName() + "' implements Cloneable and declares a final 'clone()' method");
                  }
               }
            }
         }
      }

      private static boolean isClassVisible(ClassLoader cl, Class<?> clazz) {
         if (cl == null) {
            return clazz.getClassLoader() == null;
         } else {
            try {
               cl.loadClass(clazz.getName());
               return true;
            } catch (ClassNotFoundException var3) {
               return false;
            }
         }
      }

      private static Map<String, StaticProperty> defaultPropertyIds(Map<String, StaticProperty> staticProperties) {
         Map<String, StaticProperty> newStaticProperties = new LinkedHashMap<>();
         int idx = 0;

         for (StaticProperty property : staticProperties.values()) {
            newStaticProperties.put("field" + idx++, property);
         }

         return newStaticProperties;
      }

      private static int modifiedUtfLength(String str) {
         int strlen = str.length();
         int utflen = 0;

         for (int i = 0; i < strlen; i++) {
            int c = str.charAt(i);
            if (c >= 1 && c <= 127) {
               utflen++;
            } else if (c > 2047) {
               utflen += 3;
            } else {
               utflen += 2;
            }
         }

         return utflen;
      }

      private static Method getCloneMethod(Class<?> c) {
         for (Class<?> clazz = c; clazz != null; clazz = clazz.getSuperclass()) {
            try {
               return clazz.getDeclaredMethod("clone");
            } catch (NoSuchMethodException var3) {
            }
         }

         return null;
      }

      private StaticShape.StorageStrategy getStorageStrategy() {
         String strategy = SomAccessor.ENGINE.getStaticObjectStorageStrategy(SomAccessor.LANGUAGE.getPolyglotLanguageInstance(this.language));
         switch (strategy) {
            case "DEFAULT":
               if (ImageInfo.inImageCode()) {
                  return StaticShape.StorageStrategy.ARRAY_BASED;
               }

               return StaticShape.StorageStrategy.FIELD_BASED;
            case "FIELD_BASED":
               if (ImageInfo.inImageCode()) {
                  return StaticShape.StorageStrategy.POD_BASED;
               }

               return StaticShape.StorageStrategy.FIELD_BASED;
            case "ARRAY_BASED":
               return StaticShape.StorageStrategy.ARRAY_BASED;
            default:
               throw new IllegalArgumentException("Should not reach here. Unexpected storage strategy: " + strategy);
         }
      }
   }

   static enum StorageStrategy {
      ARRAY_BASED,
      FIELD_BASED,
      POD_BASED;
   }
}
