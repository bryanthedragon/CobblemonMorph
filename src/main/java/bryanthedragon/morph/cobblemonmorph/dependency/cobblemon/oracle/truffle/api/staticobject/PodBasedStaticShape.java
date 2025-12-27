package com.oracle.truffle.api.staticobject;

final class PodBasedStaticShape<T> extends StaticShape<T> {
   private final Object pod;

   private PodBasedStaticShape(Class<?> storageClass, boolean safetyChecks, Object pod) {
      super(storageClass, safetyChecks);
      this.pod = pod;
   }

   static <T> PodBasedStaticShape<T> create(Class<?> generatedStorageClass, T factory, boolean safetyChecks, Object pod) {
      PodBasedStaticShape<T> shape = new PodBasedStaticShape<>(generatedStorageClass, safetyChecks, pod);
      shape.setFactory(factory);
      return shape;
   }

   @Override
   Object getStorage(Object obj, boolean primitive) {
      return this.cast(obj, this.storageClass, true);
   }
}
