package com.oracle.truffle.api.staticobject;

import java.util.Map;

final class PodBasedShapeGenerator<T> extends ShapeGenerator<T> {
   final Class<?> storageSuperClass;
   final Class<T> storageFactoryInterface;

   private PodBasedShapeGenerator(Class<?> storageSuperClass, Class<T> storageFactoryInterface) {
      this.storageSuperClass = storageSuperClass;
      this.storageFactoryInterface = storageFactoryInterface;
   }

   static <T> PodBasedShapeGenerator<T> getShapeGenerator(Class<?> storageSuperClass, Class<T> storageFactoryInterface) {
      return new PodBasedShapeGenerator<>(storageSuperClass, storageFactoryInterface);
   }

   @Override
   StaticShape<T> generateShape(StaticShape<T> parentShape, Map<String, StaticProperty> staticProperties, boolean safetyChecks, String storageClassName) {
      if (parentShape != null && !(parentShape instanceof PodBasedStaticShape)) {
         throw new IllegalArgumentException("Expected parent shape of type '" + PodBasedStaticShape.class.getName() + "'; got: " + parentShape);
      } else {
         return this.generateShape((PodBasedStaticShape<T>)parentShape, staticProperties, safetyChecks);
      }
   }

   private StaticShape<T> generateShape(PodBasedStaticShape<T> parentShape, Map<String, StaticProperty> staticProperties, boolean safetyChecks) {
      throw new UnsupportedOperationException("This method must be susbtituted by a class in TruffleBaseFeature");
   }
}
