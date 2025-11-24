
package com.oracle.truffle.api.staticobject;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.staticobject.ArrayBasedShapeGenerator;
import com.oracle.truffle.api.staticobject.SomAccessor;
import com.oracle.truffle.api.staticobject.StaticProperty;
import com.oracle.truffle.api.staticobject.StaticShape;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import sun.misc.Unsafe;

final class ArrayBasedStaticShape<T>
extends StaticShape<T> {
    private static final Class[] PRIMITIVE_TYPES = new Class[]{Long.TYPE, Double.TYPE, Integer.TYPE, Float.TYPE, Short.TYPE, Character.TYPE, Byte.TYPE, Boolean.TYPE};
    private static final int N_PRIMITIVES = PRIMITIVE_TYPES.length;
    @CompilerDirectives.CompilationFinal(dimensions=1)
    private final StaticShape<T>[] superShapes;
    private final ArrayBasedPropertyLayout propertyLayout;

    private ArrayBasedStaticShape(ArrayBasedStaticShape<T> parentShape, Class<?> storageClass, ArrayBasedPropertyLayout propertyLayout, boolean safetyChecks) {
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

    static <T> ArrayBasedStaticShape<T> create(ArrayBasedShapeGenerator<?> generator, Class<?> generatedStorageClass, Class<? extends T> generatedFactoryClass, ArrayBasedStaticShape<T> parentShape, Collection<StaticProperty> staticProperties, boolean checkShapes) {
        try {
            ArrayBasedPropertyLayout parentPropertyLayout = parentShape == null ? null : parentShape.getPropertyLayout();
            ArrayBasedPropertyLayout propertyLayout = new ArrayBasedPropertyLayout(generator, parentPropertyLayout, staticProperties);
            ArrayBasedStaticShape<T> shape = new ArrayBasedStaticShape<T>(parentShape, generatedStorageClass, propertyLayout, checkShapes);
            T factory = generatedFactoryClass.cast(generatedFactoryClass.getConstructor(ArrayBasedStaticShape.class, Integer.TYPE, Integer.TYPE).newInstance(shape, propertyLayout.getPrimitiveArraySize(), propertyLayout.getObjectArraySize()));
            shape.setFactory(factory);
            return shape;
        }
        catch (IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    Object getStorage(Object obj, boolean primitive) {
        Object receiverObject = this.cast(obj, this.storageClass, false);
        if (this.safetyChecks) {
            this.checkShape(receiverObject);
        } else assert (this.checkShape(receiverObject));
        if (primitive) {
            Object storage = UNSAFE.getObject(receiverObject, (long)this.propertyLayout.generator.getByteArrayOffset());
            assert (storage != null);
            assert (storage.getClass() == byte[].class);
            return SomAccessor.RUNTIME.unsafeCast(storage, byte[].class, true, true, true);
        }
        Object storage = UNSAFE.getObject(receiverObject, (long)this.propertyLayout.generator.getObjectArrayOffset());
        assert (storage != null);
        assert (storage.getClass() == Object[].class);
        return SomAccessor.RUNTIME.unsafeCast(storage, Object[].class, true, true, true);
    }

    private boolean checkShape(Object receiverObject) {
        ArrayBasedStaticShape receiverShape = this.cast(UNSAFE.getObject(receiverObject, (long)this.propertyLayout.generator.getShapeOffset()), ArrayBasedStaticShape.class, false);
        if (this != receiverShape && (receiverShape.superShapes.length < this.superShapes.length || receiverShape.superShapes[this.superShapes.length - 1] != this)) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            throw new IllegalArgumentException("Incompatible shape on property access. Expected '" + this + "' got '" + receiverShape + "'.");
        }
        return true;
    }

    private ArrayBasedPropertyLayout getPropertyLayout() {
        return this.propertyLayout;
    }

    private static int typeToInt(Class<?> type) {
        if (!type.isPrimitive()) {
            return PRIMITIVE_TYPES.length;
        }
        for (int i = 0; i < PRIMITIVE_TYPES.length; ++i) {
            if (type != PRIMITIVE_TYPES[i]) continue;
            return i;
        }
        throw new IllegalArgumentException("Invalid StaticProperty type: " + type.getName());
    }

    private static Class<?> intToType(int i) {
        return i == PRIMITIVE_TYPES.length ? Object.class : PRIMITIVE_TYPES[i];
    }

    static class ArrayBasedPropertyLayout {
        private final int primitiveArraySize;
        private final int objectArraySize;
        @CompilerDirectives.CompilationFinal(dimensions=2)
        private final int[][] leftoverHoles;
        private final int lastOffset;
        private final ArrayBasedShapeGenerator<?> generator;

        ArrayBasedPropertyLayout(ArrayBasedShapeGenerator<?> generator, ArrayBasedPropertyLayout parentLayout, Collection<StaticProperty> staticProperties) {
            int objArraySize;
            Object parentLeftoverHoles;
            int superTotalByteCount;
            this.generator = generator;
            if (parentLayout == null) {
                superTotalByteCount = ArrayBasedPropertyLayout.base() + ArrayBasedPropertyLayout.alignmentCorrection();
                parentLeftoverHoles = ArrayBasedPropertyLayout.alignmentCorrection() > 0 ? (Object)new int[][]{{ArrayBasedPropertyLayout.base(), ArrayBasedPropertyLayout.base() + ArrayBasedPropertyLayout.alignmentCorrection()}} : (Object)new int[0][];
                objArraySize = 0;
            } else {
                superTotalByteCount = parentLayout.lastOffset;
                parentLeftoverHoles = parentLayout.leftoverHoles;
                objArraySize = parentLayout.objectArraySize;
            }
            int[] primitiveFields = new int[N_PRIMITIVES];
            for (StaticProperty staticProperty : staticProperties) {
                int propertyIndex = ArrayBasedStaticShape.typeToInt(staticProperty.getPropertyType());
                if (!staticProperty.getPropertyType().isPrimitive()) continue;
                int n = propertyIndex;
                primitiveFields[n] = primitiveFields[n] + 1;
            }
            PrimitiveFieldIndexes primitiveFieldIndexes = new PrimitiveFieldIndexes(primitiveFields, superTotalByteCount, (int[][])parentLeftoverHoles);
            for (StaticProperty staticProperty : staticProperties) {
                int offset;
                if (staticProperty.getPropertyType().isPrimitive()) {
                    int propertyIndex = ArrayBasedStaticShape.typeToInt(staticProperty.getPropertyType());
                    offset = primitiveFieldIndexes.getIndex(propertyIndex);
                } else {
                    offset = Unsafe.ARRAY_OBJECT_BASE_OFFSET + Unsafe.ARRAY_OBJECT_INDEX_SCALE * objArraySize++;
                }
                staticProperty.initOffset(offset);
            }
            this.lastOffset = primitiveFieldIndexes.offsets[N_PRIMITIVES - 1];
            this.primitiveArraySize = ArrayBasedPropertyLayout.getSizeToAlloc(parentLayout == null ? 0 : parentLayout.primitiveArraySize, primitiveFieldIndexes);
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

        private static int getSizeToAlloc(int superToAlloc, PrimitiveFieldIndexes fieldIndexes) {
            int toAlloc = fieldIndexes.offsets[N_PRIMITIVES - 1] - ArrayBasedPropertyLayout.base();
            assert (toAlloc >= 0);
            if (toAlloc == ArrayBasedPropertyLayout.alignmentCorrection() && fieldIndexes.schedule.isEmpty()) {
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
            if (type == Boolean.TYPE) {
                return 1;
            }
            return ArrayBasedPropertyLayout.getBitCount(type) >> 3;
        }

        private static int getBitCount(Class<?> type) {
            if (type == Boolean.TYPE) {
                return 1;
            }
            if (type == Byte.TYPE) {
                return 8;
            }
            if (type == Character.TYPE || type == Short.TYPE) {
                return 16;
            }
            if (type == Float.TYPE || type == Integer.TYPE) {
                return 32;
            }
            if (type == Double.TYPE || type == Long.TYPE) {
                return 64;
            }
            throw new IllegalArgumentException("Invalid StaticProperty type: " + type.getName());
        }

        private static class ScheduleEntry {
            final int propertyIndex;
            final int offset;

            ScheduleEntry(int propertyIndex, int offset) {
                this.propertyIndex = propertyIndex;
                this.offset = offset;
            }
        }

        private static final class FillingSchedule {
            static final int[][] EMPTY_INT_ARRAY_ARRAY = new int[0][];
            final List<ScheduleEntry> schedule;
            int[][] nextLeftoverHoles;
            final boolean isEmpty;

            boolean isEmpty() {
                return this.isEmpty;
            }

            static FillingSchedule create(int holeStart, int holeEnd, int[] counts, int[][] leftoverHoles) {
                ArrayList<ScheduleEntry> schedule = new ArrayList<ScheduleEntry>();
                if (leftoverHoles == EMPTY_INT_ARRAY_ARRAY) {
                    FillingSchedule.scheduleHole(holeStart, holeEnd, counts, schedule);
                    return new FillingSchedule(schedule);
                }
                ArrayList<int[]> nextHoles = new ArrayList<int[]>();
                FillingSchedule.scheduleHole(holeStart, holeEnd, counts, schedule, nextHoles);
                if (leftoverHoles != null) {
                    for (int[] hole : leftoverHoles) {
                        FillingSchedule.scheduleHole(hole[0], hole[1], counts, schedule, nextHoles);
                    }
                }
                return new FillingSchedule(schedule, nextHoles);
            }

            private static void scheduleHole(int holeStart, int holeEnd, int[] counts, List<ScheduleEntry> schedule, List<int[]> nextHoles) {
                int end2 = holeEnd;
                int holeSize = holeEnd - holeStart;
                int i = 0;
                block0: while (holeSize > 0 && i < N_PRIMITIVES) {
                    int byteCount = ArrayBasedPropertyLayout.getByteCount(i);
                    while (counts[i] > 0 && byteCount <= holeSize) {
                        int newEnd = end2 - byteCount;
                        if (newEnd % byteCount != 0) {
                            int misalignment = newEnd % byteCount;
                            int aligned = newEnd - misalignment;
                            if (aligned < holeStart) {
                                i = (byte)(i + 1);
                                continue block0;
                            }
                            schedule.add(new ScheduleEntry(i, aligned));
                            int n = i;
                            counts[n] = counts[n] - 1;
                            FillingSchedule.scheduleHole(end2 - misalignment, end2, counts, schedule, nextHoles);
                            newEnd = aligned;
                        } else {
                            int n = i;
                            counts[n] = counts[n] - 1;
                            schedule.add(new ScheduleEntry(i, newEnd));
                        }
                        end2 = newEnd;
                        holeSize = end2 - holeStart;
                    }
                    i = (byte)(i + 1);
                }
                if (holeSize > 0) {
                    nextHoles.add(new int[]{holeStart, end2});
                }
            }

            private static void scheduleHole(int holeStart, int holeEnd, int[] counts, List<ScheduleEntry> schedule) {
                int end2 = holeEnd;
                int holeSize = holeEnd - holeStart;
                for (int i = 0; holeSize > 0 && i < N_PRIMITIVES; i = (int)((byte)(i + 1))) {
                    int primitiveByteCount = ArrayBasedPropertyLayout.getByteCount(i);
                    while (counts[i] > 0 && primitiveByteCount <= holeSize) {
                        int n = i;
                        counts[n] = counts[n] - 1;
                        holeSize -= primitiveByteCount;
                        schedule.add(new ScheduleEntry(i, end2 -= primitiveByteCount));
                    }
                }
                assert (holeSize >= 0);
            }

            private FillingSchedule(List<ScheduleEntry> schedule) {
                this.schedule = schedule;
                this.isEmpty = schedule == null || schedule.isEmpty();
            }

            private FillingSchedule(List<ScheduleEntry> schedule, List<int[]> nextHoles) {
                this.schedule = schedule;
                this.nextLeftoverHoles = nextHoles.isEmpty() ? null : (int[][])nextHoles.toArray((T[])EMPTY_INT_ARRAY_ARRAY);
                this.isEmpty = schedule != null && schedule.isEmpty();
            }

            ScheduleEntry query(int propertyIndex) {
                for (ScheduleEntry e : this.schedule) {
                    if (e.propertyIndex != propertyIndex) continue;
                    this.schedule.remove(e);
                    return e;
                }
                return null;
            }
        }

        private static final class PrimitiveFieldIndexes {
            final int[] offsets = new int[N_PRIMITIVES];
            final FillingSchedule schedule;

            PrimitiveFieldIndexes(int[] primitiveFields, int superTotalByteCount, int[][] leftoverHoles) {
                this.offsets[0] = PrimitiveFieldIndexes.startOffset(superTotalByteCount, primitiveFields);
                this.schedule = FillingSchedule.create(superTotalByteCount, this.offsets[0], primitiveFields, leftoverHoles);
                for (int i = 1; i < N_PRIMITIVES; ++i) {
                    this.offsets[i] = this.offsets[i - 1] + primitiveFields[i - 1] * ArrayBasedPropertyLayout.getByteCount(i - 1);
                }
            }

            int getIndex(int propertyIndex) {
                ScheduleEntry entry = this.schedule.query(propertyIndex);
                if (entry != null) {
                    return entry.offset;
                }
                int prevOffset = this.offsets[propertyIndex];
                int n = propertyIndex;
                this.offsets[n] = this.offsets[n] + ArrayBasedPropertyLayout.getByteCount(propertyIndex);
                return prevOffset;
            }

            private static int startOffset(int superTotalByteCount, int[] primitiveCounts) {
                int i;
                for (i = 0; i < N_PRIMITIVES && primitiveCounts[i] == 0; ++i) {
                }
                if (i == N_PRIMITIVES) {
                    return superTotalByteCount;
                }
                int r = superTotalByteCount % ArrayBasedPropertyLayout.getByteCount(i);
                if (r == 0) {
                    return superTotalByteCount;
                }
                return superTotalByteCount + ArrayBasedPropertyLayout.getByteCount(i) - r;
            }
        }
    }
}

