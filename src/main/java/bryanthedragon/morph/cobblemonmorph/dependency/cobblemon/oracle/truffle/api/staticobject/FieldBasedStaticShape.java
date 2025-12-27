package com.oracle.truffle.api.staticobject;

final class FieldBasedStaticShape<T> extends StaticShape<T> {
   private FieldBasedStaticShape(Class<?> storageClass, boolean safetyChecks) {
      super(storageClass, safetyChecks);
   }

   static <T> FieldBasedStaticShape<T> create(Class<?> generatedStorageClass, Class<? extends T> generatedFactoryClass, boolean safetyChecks) {
      try {
         FieldBasedStaticShape<T> shape = new FieldBasedStaticShape<>(generatedStorageClass, safetyChecks);
         T factory = (T)generatedFactoryClass.cast(UNSAFE.allocateInstance(generatedFactoryClass));
         shape.setFactory(factory);
         return shape;
      } catch (InstantiationException var5) {
         throw new RuntimeException(var5);
      }
   }

   @Override
   Object getStorage(Object obj, boolean primitive) {
      return this.cast(obj, this.storageClass, true);
   }
}
