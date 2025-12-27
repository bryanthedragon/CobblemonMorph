package com.oracle.truffle.object;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.object.DynamicObject;
import com.oracle.truffle.api.object.DynamicObjectLibrary;
import com.oracle.truffle.api.object.Property;
import com.oracle.truffle.api.object.Shape;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

final class DynamicObjectSupport {
   private DynamicObjectSupport() {
   }

   static void ensureCapacity(DynamicObject object, Shape otherShape) {
      grow(object, object.getShape(), otherShape);
   }

   static void grow(DynamicObject object, Shape thisShape, Shape otherShape) {
      ShapeImpl thisShapeImpl = (ShapeImpl)thisShape;
      ShapeImpl otherShapeImpl = (ShapeImpl)otherShape;
      int sourceObjectCapacity = thisShapeImpl.getObjectArrayCapacity();
      int targetObjectCapacity = otherShapeImpl.getObjectArrayCapacity();
      if (sourceObjectCapacity < targetObjectCapacity) {
         growObjectStore(object, thisShapeImpl, sourceObjectCapacity, targetObjectCapacity);
      }

      int sourcePrimitiveCapacity = thisShapeImpl.getPrimitiveArrayCapacity();
      int targetPrimitiveCapacity = otherShapeImpl.getPrimitiveArrayCapacity();
      if (sourcePrimitiveCapacity < targetPrimitiveCapacity) {
         growPrimitiveStore(object, thisShapeImpl, sourcePrimitiveCapacity, targetPrimitiveCapacity);
      }
   }

   private static void growObjectStore(DynamicObject object, ShapeImpl thisShape, int sourceCapacity, int targetCapacity) {
      Object[] newObjectStore = new Object[targetCapacity];
      if (sourceCapacity != 0) {
         int sourceSize = thisShape.getObjectArraySize();
         Object[] oldObjectStore = LayoutImpl.ACCESS.getObjectArray(object);
         LayoutImpl.ACCESS.arrayCopy(oldObjectStore, newObjectStore, sourceSize);
      }

      LayoutImpl.ACCESS.setObjectArray(object, newObjectStore);
   }

   private static void growPrimitiveStore(DynamicObject object, ShapeImpl thisShape, int sourceCapacity, int targetCapacity) {
      int[] newPrimitiveArray = new int[targetCapacity];
      if (sourceCapacity != 0) {
         int sourceSize = thisShape.getPrimitiveArraySize();
         int[] oldPrimitiveArray = LayoutImpl.ACCESS.getPrimitiveArray(object);
         LayoutImpl.ACCESS.arrayCopy(oldPrimitiveArray, newPrimitiveArray, sourceSize);
      }

      LayoutImpl.ACCESS.setPrimitiveArray(object, newPrimitiveArray);
   }

   static void resize(DynamicObject object, Shape thisShape, Shape otherShape) {
      resizeObjectStore(object, thisShape, otherShape);
      resizePrimitiveStore(object, thisShape, otherShape);
   }

   static void trimToSize(DynamicObject object, Shape thisShape, Shape otherShape) {
      trimObjectStore(object, thisShape, otherShape);
      trimPrimitiveStore(object, thisShape, otherShape);
   }

   private static void resizeObjectStore(DynamicObject object, Shape oldShape, Shape newShape) {
      ShapeImpl oldShapeImpl = (ShapeImpl)oldShape;
      ShapeImpl newShapeImpl = (ShapeImpl)newShape;
      int destinationCapacity = newShapeImpl.getObjectArrayCapacity();
      if (destinationCapacity == 0) {
         LayoutImpl.ACCESS.setObjectArray(object, null);
      } else {
         int sourceCapacity = oldShapeImpl.getObjectArrayCapacity();
         if (sourceCapacity != destinationCapacity) {
            int sourceSize = oldShapeImpl.getObjectArraySize();
            Object[] newObjectStore = new Object[destinationCapacity];
            if (sourceSize != 0) {
               Object[] oldObjectStore = LayoutImpl.ACCESS.getObjectArray(object);
               int destinationSize = newShapeImpl.getObjectArraySize();
               int length = Math.min(sourceSize, destinationSize);
               LayoutImpl.ACCESS.arrayCopy(oldObjectStore, newObjectStore, length);
            }

            LayoutImpl.ACCESS.setObjectArray(object, newObjectStore);
         }
      }
   }

   private static void resizePrimitiveStore(DynamicObject object, Shape oldShape, Shape newShape) {
      ShapeImpl oldShapeImpl = (ShapeImpl)oldShape;
      ShapeImpl newShapeImpl = (ShapeImpl)newShape;

      assert newShapeImpl.hasPrimitiveArray();

      int destinationCapacity = newShapeImpl.getPrimitiveArrayCapacity();
      if (destinationCapacity == 0) {
         LayoutImpl.ACCESS.setPrimitiveArray(object, null);
      } else {
         int sourceCapacity = oldShapeImpl.getPrimitiveArrayCapacity();
         if (sourceCapacity != destinationCapacity) {
            int sourceSize = oldShapeImpl.getPrimitiveArraySize();
            int[] newPrimitiveArray = new int[destinationCapacity];
            if (sourceSize != 0) {
               int[] oldPrimitiveArray = LayoutImpl.ACCESS.getPrimitiveArray(object);
               int destinationSize = newShapeImpl.getPrimitiveArraySize();
               int length = Math.min(sourceSize, destinationSize);
               LayoutImpl.ACCESS.arrayCopy(oldPrimitiveArray, newPrimitiveArray, length);
            }

            LayoutImpl.ACCESS.setPrimitiveArray(object, newPrimitiveArray);
         }
      }
   }

   private static void trimObjectStore(DynamicObject object, Shape thisShape, Shape newShape) {
      ShapeImpl thisShapeImpl = (ShapeImpl)thisShape;
      ShapeImpl newShapeImpl = (ShapeImpl)newShape;
      Object[] oldObjectStore = LayoutImpl.ACCESS.getObjectArray(object);
      int destinationCapacity = newShapeImpl.getObjectArrayCapacity();
      if (destinationCapacity == 0) {
         if (oldObjectStore != null) {
            LayoutImpl.ACCESS.setObjectArray(object, null);
         }
      } else {
         int sourceCapacity = thisShapeImpl.getObjectArrayCapacity();
         if (sourceCapacity > destinationCapacity) {
            Object[] newObjectStore = new Object[destinationCapacity];
            int destinationSize = newShapeImpl.getObjectArraySize();
            LayoutImpl.ACCESS.arrayCopy(oldObjectStore, newObjectStore, destinationSize);
            LayoutImpl.ACCESS.setObjectArray(object, newObjectStore);
         }
      }
   }

   private static void trimPrimitiveStore(DynamicObject object, Shape thisShape, Shape newShape) {
      ShapeImpl thisShapeImpl = (ShapeImpl)thisShape;
      ShapeImpl newShapeImpl = (ShapeImpl)newShape;
      int[] oldPrimitiveStore = LayoutImpl.ACCESS.getPrimitiveArray(object);
      int destinationCapacity = newShapeImpl.getPrimitiveArrayCapacity();
      if (destinationCapacity == 0) {
         if (oldPrimitiveStore != null) {
            LayoutImpl.ACCESS.setPrimitiveArray(object, null);
         }
      } else {
         int sourceCapacity = thisShapeImpl.getPrimitiveArrayCapacity();
         if (sourceCapacity > destinationCapacity) {
            int[] newPrimitiveStore = new int[destinationCapacity];
            int destinationSize = newShapeImpl.getPrimitiveArraySize();
            LayoutImpl.ACCESS.arrayCopy(oldPrimitiveStore, newPrimitiveStore, destinationSize);
            LayoutImpl.ACCESS.setPrimitiveArray(object, newPrimitiveStore);
         }
      }
   }

   static Map<Object, Object> archive(DynamicObject object) {
      Map<Object, Object> archive = new HashMap<>();
      Property[] properties = ((ShapeImpl)object.getShape()).getPropertyArray();

      for (Property property : properties) {
         archive.put(property.getKey(), DynamicObjectLibrary.getUncached().getOrDefault(object, property.getKey(), null));
      }

      return archive;
   }

   static boolean verifyValues(DynamicObject object, Map<Object, Object> archive) {
      Property[] properties = ((ShapeImpl)object.getShape()).getPropertyArray();

      for (Property property : properties) {
         Object key = property.getKey();
         Object before = archive.get(key);
         Object after = DynamicObjectLibrary.getUncached().getOrDefault(object, key, null);

         assert Objects.equals(after, before) : "before != after for key: " + key;
      }

      return true;
   }

   @CompilerDirectives.TruffleBoundary
   static void invalidateAllPropertyAssumptions(Shape shape) {
      ShapeImpl shapeImpl = (ShapeImpl)shape;
      if (shapeImpl.isLeaf()) {
         shapeImpl.invalidateLeafAssumption();
      }

      if (shapeImpl.allowPropertyAssumptions()) {
         shapeImpl.invalidateAllPropertyAssumptions();
      }
   }
}
