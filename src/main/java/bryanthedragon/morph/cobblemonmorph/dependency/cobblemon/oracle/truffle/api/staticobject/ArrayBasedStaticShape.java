package com.oracle.truffle.api.staticobject;

import com.oracle.truffle.api.CompilerDirectives;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import sun.misc.Unsafe;

final class ArrayBasedStaticShape<T> extends StaticShape<T> {
   private static final Class[] PRIMITIVE_TYPES = new Class[]{
      long.class, double.class, int.class, float.class, short.class, char.class, byte.class, boolean.class
   };
   private static final int N_PRIMITIVES = PRIMITIVE_TYPES.length;
   @CompilerDirectives.CompilationFinal(dimensions = 1)
   private final StaticShape<T>[] superShapes;
   private final ArrayBasedStaticShape.ArrayBasedPropertyLayout propertyLayout;

   private ArrayBasedStaticShape(
      ArrayBasedStaticShape<T> parentShape, Class<?> storageClass, ArrayBasedStaticShape.ArrayBasedPropertyLayout propertyLayout, boolean safetyChecks
   ) {
      super(storageClass, safetyChecks);
      if (parentShape == null) {
         this.superShapes = new StaticShape[]{this};
      } else {
         int depth = parentShape.superShapes.length;
         this.superShapes = new StaticShape[depth + 1];
         System.arraycopy(parentShape.superShapes, 0, this.superShapes, 0, depth);
         this.superShapes[depth] = this;
      }

      this.propertyLayout = propertyLayout;
   }

   static <T> ArrayBasedStaticShape<T> create(
      ArrayBasedShapeGenerator<?> generator,
      Class<?> generatedStorageClass,
      Class<? extends T> generatedFactoryClass,
      ArrayBasedStaticShape<T> parentShape,
      Collection<StaticProperty> staticProperties,
      boolean checkShapes
   ) {
      try {
         ArrayBasedStaticShape.ArrayBasedPropertyLayout parentPropertyLayout = parentShape == null ? null : parentShape.getPropertyLayout();
         ArrayBasedStaticShape.ArrayBasedPropertyLayout propertyLayout = new ArrayBasedStaticShape.ArrayBasedPropertyLayout(
            generator, parentPropertyLayout, staticProperties
         );
         ArrayBasedStaticShape<T> shape = new ArrayBasedStaticShape<>(parentShape, generatedStorageClass, propertyLayout, checkShapes);
         T factory = (T)generatedFactoryClass.cast(
            generatedFactoryClass.getConstructor(ArrayBasedStaticShape.class, int.class, int.class)
               .newInstance(shape, propertyLayout.getPrimitiveArraySize(), propertyLayout.getObjectArraySize())
         );
         shape.setFactory(factory);
         return shape;
      } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException var10) {
         throw new RuntimeException(var10);
      }
   }

   @Override
   Object getStorage(Object obj, boolean primitive) {
      Object receiverObject = this.cast(obj, this.storageClass, false);
      if (this.safetyChecks) {
         this.checkShape(receiverObject);
      } else {
         assert this.checkShape(receiverObject);
      }

      if (primitive) {
         Object storage = UNSAFE.getObject(receiverObject, this.propertyLayout.generator.getByteArrayOffset());

         assert storage != null;

         assert storage.getClass() == byte[].class;

         return SomAccessor.RUNTIME.unsafeCast(storage, byte[].class, true, true, true);
      } else {
         Object storage = UNSAFE.getObject(receiverObject, this.propertyLayout.generator.getObjectArrayOffset());

         assert storage != null;

         assert storage.getClass() == Object[].class;

         return SomAccessor.RUNTIME.unsafeCast(storage, Object[].class, true, true, true);
      }
   }

   private boolean checkShape(Object receiverObject) {
      ArrayBasedStaticShape<?> receiverShape = this.cast(
         UNSAFE.getObject(receiverObject, this.propertyLayout.generator.getShapeOffset()), ArrayBasedStaticShape.class, false
      );
      if (this == receiverShape
         || receiverShape.superShapes.length >= this.superShapes.length && receiverShape.superShapes[this.superShapes.length - 1] == this) {
         return true;
      } else {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         throw new IllegalArgumentException("Incompatible shape on property access. Expected '" + this + "' got '" + receiverShape + "'.");
      }
   }

   private ArrayBasedStaticShape.ArrayBasedPropertyLayout getPropertyLayout() {
      return this.propertyLayout;
   }

   private static int typeToInt(Class<?> type) {
      if (!type.isPrimitive()) {
         return PRIMITIVE_TYPES.length;
      } else {
         for (int i = 0; i < PRIMITIVE_TYPES.length; i++) {
            if (type == PRIMITIVE_TYPES[i]) {
               return i;
            }
         }

         throw new IllegalArgumentException("Invalid StaticProperty type: " + type.getName());
      }
   }

   private static Class<?> intToType(int i) {
      return i == PRIMITIVE_TYPES.length ? Object.class : PRIMITIVE_TYPES[i];
   }

   static class ArrayBasedPropertyLayout {
      private final int primitiveArraySize;
      private final int objectArraySize;
      @CompilerDirectives.CompilationFinal(dimensions = 2)
      private final int[][] leftoverHoles;
      private final int lastOffset;
      private final ArrayBasedShapeGenerator<?> generator;

      ArrayBasedPropertyLayout(
         ArrayBasedShapeGenerator<?> generator, ArrayBasedStaticShape.ArrayBasedPropertyLayout parentLayout, Collection<StaticProperty> staticProperties
      ) {
         this.generator = generator;
         int superTotalByteCount;
         int[][] parentLeftoverHoles;
         int objArraySize;
         if (parentLayout == null) {
            superTotalByteCount = base() + alignmentCorrection();
            if (alignmentCorrection() > 0) {
               parentLeftoverHoles = new int[][]{{base(), base() + alignmentCorrection()}};
            } else {
               parentLeftoverHoles = new int[0][];
            }

            objArraySize = 0;
         } else {
            superTotalByteCount = parentLayout.lastOffset;
            parentLeftoverHoles = parentLayout.leftoverHoles;
            objArraySize = parentLayout.objectArraySize;
         }

         int[] primitiveFields = new int[ArrayBasedStaticShape.N_PRIMITIVES];

         for (StaticProperty staticProperty : staticProperties) {
            int propertyIndex = ArrayBasedStaticShape.typeToInt(staticProperty.getPropertyType());
            if (staticProperty.getPropertyType().isPrimitive()) {
               primitiveFields[propertyIndex]++;
            }
         }

         ArrayBasedStaticShape.ArrayBasedPropertyLayout.PrimitiveFieldIndexes primitiveFieldIndexes = new ArrayBasedStaticShape.ArrayBasedPropertyLayout.PrimitiveFieldIndexes(
            primitiveFields, superTotalByteCount, parentLeftoverHoles
         );

         for (StaticProperty staticPropertyx : staticProperties) {
            int offset;
            if (staticPropertyx.getPropertyType().isPrimitive()) {
               int propertyIndex = ArrayBasedStaticShape.typeToInt(staticPropertyx.getPropertyType());
               offset = primitiveFieldIndexes.getIndex(propertyIndex);
            } else {
               offset = Unsafe.ARRAY_OBJECT_BASE_OFFSET + Unsafe.ARRAY_OBJECT_INDEX_SCALE * objArraySize++;
            }

            staticPropertyx.initOffset(offset);
         }

         this.lastOffset = primitiveFieldIndexes.offsets[ArrayBasedStaticShape.N_PRIMITIVES - 1];
         this.primitiveArraySize = getSizeToAlloc(parentLayout == null ? 0 : parentLayout.primitiveArraySize, primitiveFieldIndexes);
         this.objectArraySize = objArraySize;
         this.leftoverHoles = primitiveFieldIndexes.schedule.nextLeftoverHoles;
      }

      private static int base() {
         return Unsafe.ARRAY_BYTE_BASE_OFFSET;
      }

      private static int alignmentCorrection() {
         int misalignment = Unsafe.ARRAY_BYTE_BASE_OFFSET % Unsafe.ARRAY_LONG_INDEX_SCALE;
         return misalignment == 0 ? 0 : Unsafe.ARRAY_LONG_INDEX_SCALE - misalignment;
      }

      private static int getSizeToAlloc(int superToAlloc, ArrayBasedStaticShape.ArrayBasedPropertyLayout.PrimitiveFieldIndexes fieldIndexes) {
         int toAlloc = fieldIndexes.offsets[ArrayBasedStaticShape.N_PRIMITIVES - 1] - base();

         assert toAlloc >= 0;

         if (toAlloc == alignmentCorrection() && fieldIndexes.schedule.isEmpty()) {
            toAlloc = superToAlloc;
         }

         return toAlloc;
      }

      int getPrimitiveArraySize() {
         return this.primitiveArraySize;
      }

      int getObjectArraySize() {
         return this.objectArraySize;
      }

      static int getByteCount(int b) {
         Class<?> type = ArrayBasedStaticShape.intToType(b);
         return type == boolean.class ? 1 : getBitCount(type) >> 3;
      }

      private static int getBitCount(Class<?> type) {
         if (type == boolean.class) {
            return 1;
         } else if (type == byte.class) {
            return 8;
         } else if (type == char.class || type == short.class) {
            return 16;
         } else if (type == float.class || type == int.class) {
            return 32;
         } else if (type != double.class && type != long.class) {
            throw new IllegalArgumentException("Invalid StaticProperty type: " + type.getName());
         } else {
            return 64;
         }
      }

      private static final class FillingSchedule {
         static final int[][] EMPTY_INT_ARRAY_ARRAY = new int[0][];
         final List<ArrayBasedStaticShape.ArrayBasedPropertyLayout.ScheduleEntry> schedule;
         int[][] nextLeftoverHoles;
         final boolean isEmpty;

         boolean isEmpty() {
            return this.isEmpty;
         }

         static ArrayBasedStaticShape.ArrayBasedPropertyLayout.FillingSchedule create(int holeStart, int holeEnd, int[] counts, int[][] leftoverHoles) {
            List<ArrayBasedStaticShape.ArrayBasedPropertyLayout.ScheduleEntry> schedule = new ArrayList<>();
            if (leftoverHoles == EMPTY_INT_ARRAY_ARRAY) {
               scheduleHole(holeStart, holeEnd, counts, schedule);
               return new ArrayBasedStaticShape.ArrayBasedPropertyLayout.FillingSchedule(schedule);
            } else {
               List<int[]> nextHoles = new ArrayList<>();
               scheduleHole(holeStart, holeEnd, counts, schedule, nextHoles);
               if (leftoverHoles != null) {
                  for (int[] hole : leftoverHoles) {
                     scheduleHole(hole[0], hole[1], counts, schedule, nextHoles);
                  }
               }

               return new ArrayBasedStaticShape.ArrayBasedPropertyLayout.FillingSchedule(schedule, nextHoles);
            }
         }

         private static void scheduleHole(
            int holeStart, int holeEnd, int[] counts, List<ArrayBasedStaticShape.ArrayBasedPropertyLayout.ScheduleEntry> schedule, List<int[]> nextHoles
         ) {
            int end = holeEnd;
            int holeSize = holeEnd - holeStart;
            byte i = 0;

            label37:
            while (holeSize > 0 && i < ArrayBasedStaticShape.N_PRIMITIVES) {
               int byteCount = ArrayBasedStaticShape.ArrayBasedPropertyLayout.getByteCount(i);

               while (counts[i] > 0 && byteCount <= holeSize) {
                  int newEnd = end - byteCount;
                  if (newEnd % byteCount != 0) {
                     int misalignment = newEnd % byteCount;
                     int aligned = newEnd - misalignment;
                     if (aligned < holeStart) {
                        i++;
                        continue label37;
                     }

                     schedule.add(new ArrayBasedStaticShape.ArrayBasedPropertyLayout.ScheduleEntry(i, aligned));
                     counts[i]--;
                     scheduleHole(end - misalignment, end, counts, schedule, nextHoles);
                     newEnd = aligned;
                  } else {
                     counts[i]--;
                     schedule.add(new ArrayBasedStaticShape.ArrayBasedPropertyLayout.ScheduleEntry(i, newEnd));
                  }

                  end = newEnd;
                  holeSize = newEnd - holeStart;
               }

               i++;
            }

            if (holeSize > 0) {
               nextHoles.add(new int[]{holeStart, end});
            }
         }

         private static void scheduleHole(int holeStart, int holeEnd, int[] counts, List<ArrayBasedStaticShape.ArrayBasedPropertyLayout.ScheduleEntry> schedule) {
            int end = holeEnd;
            int holeSize = holeEnd - holeStart;

            for (byte i = 0; holeSize > 0 && i < ArrayBasedStaticShape.N_PRIMITIVES; i++) {
               int primitiveByteCount = ArrayBasedStaticShape.ArrayBasedPropertyLayout.getByteCount(i);

               while (counts[i] > 0 && primitiveByteCount <= holeSize) {
                  counts[i]--;
                  end -= primitiveByteCount;
                  holeSize -= primitiveByteCount;
                  schedule.add(new ArrayBasedStaticShape.ArrayBasedPropertyLayout.ScheduleEntry(i, end));
               }
            }

            assert holeSize >= 0;
         }

         private FillingSchedule(List<ArrayBasedStaticShape.ArrayBasedPropertyLayout.ScheduleEntry> schedule) {
            this.schedule = schedule;
            this.isEmpty = schedule == null || schedule.isEmpty();
         }

         private FillingSchedule(List<ArrayBasedStaticShape.ArrayBasedPropertyLayout.ScheduleEntry> schedule, List<int[]> nextHoles) {
            this.schedule = schedule;
            this.nextLeftoverHoles = nextHoles.isEmpty() ? null : nextHoles.toArray(EMPTY_INT_ARRAY_ARRAY);
            this.isEmpty = schedule != null && schedule.isEmpty();
         }

         ArrayBasedStaticShape.ArrayBasedPropertyLayout.ScheduleEntry query(int propertyIndex) {
            for (ArrayBasedStaticShape.ArrayBasedPropertyLayout.ScheduleEntry e : this.schedule) {
               if (e.propertyIndex == propertyIndex) {
                  this.schedule.remove(e);
                  return e;
               }
            }

            return null;
         }
      }

      private static final class PrimitiveFieldIndexes {
         final int[] offsets = new int[ArrayBasedStaticShape.N_PRIMITIVES];
         final ArrayBasedStaticShape.ArrayBasedPropertyLayout.FillingSchedule schedule;

         PrimitiveFieldIndexes(int[] primitiveFields, int superTotalByteCount, int[][] leftoverHoles) {
            this.offsets[0] = startOffset(superTotalByteCount, primitiveFields);
            this.schedule = ArrayBasedStaticShape.ArrayBasedPropertyLayout.FillingSchedule.create(
               superTotalByteCount, this.offsets[0], primitiveFields, leftoverHoles
            );

            for (int i = 1; i < ArrayBasedStaticShape.N_PRIMITIVES; i++) {
               this.offsets[i] = this.offsets[i - 1] + primitiveFields[i - 1] * ArrayBasedStaticShape.ArrayBasedPropertyLayout.getByteCount(i - 1);
            }
         }

         int getIndex(int propertyIndex) {
            ArrayBasedStaticShape.ArrayBasedPropertyLayout.ScheduleEntry entry = this.schedule.query(propertyIndex);
            if (entry != null) {
               return entry.offset;
            } else {
               int prevOffset = this.offsets[propertyIndex];
               this.offsets[propertyIndex] = this.offsets[propertyIndex] + ArrayBasedStaticShape.ArrayBasedPropertyLayout.getByteCount(propertyIndex);
               return prevOffset;
            }
         }

         private static int startOffset(int superTotalByteCount, int[] primitiveCounts) {
            int i = 0;

            while (i < ArrayBasedStaticShape.N_PRIMITIVES && primitiveCounts[i] == 0) {
               i++;
            }

            if (i == ArrayBasedStaticShape.N_PRIMITIVES) {
               return superTotalByteCount;
            } else {
               int r = superTotalByteCount % ArrayBasedStaticShape.ArrayBasedPropertyLayout.getByteCount(i);
               return r == 0 ? superTotalByteCount : superTotalByteCount + ArrayBasedStaticShape.ArrayBasedPropertyLayout.getByteCount(i) - r;
            }
         }
      }

      private static class ScheduleEntry {
         final int propertyIndex;
         final int offset;

         ScheduleEntry(int propertyIndex, int offset) {
            this.propertyIndex = propertyIndex;
            this.offset = offset;
         }
      }
   }
}
