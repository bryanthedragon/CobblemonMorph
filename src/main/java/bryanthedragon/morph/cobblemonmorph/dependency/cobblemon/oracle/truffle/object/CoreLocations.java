
package com.oracle.truffle.object;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.object.DynamicObject;
import com.oracle.truffle.api.object.IncompatibleLocationException;
import com.oracle.truffle.api.object.Location;
import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.object.CoreLocation;
import com.oracle.truffle.object.LayoutImpl;
import com.oracle.truffle.object.LocationImpl;
import com.oracle.truffle.object.UnsafeAccess;
import java.lang.reflect.Field;
import java.util.Objects;

abstract class CoreLocations {
    static final int LONG_FIELD_SLOT_SIZE = 1;
    static final int LONG_ARRAY_SLOT_SIZE = 2;
    static final int OBJECT_SLOT_SIZE = 1;
    static final int MAX_DYNAMIC_FIELDS = 1000;

    CoreLocations() {
    }

    public static LongLocation createLongLocation(LongLocation longLocation, boolean allowInt) {
        if (!allowInt && longLocation instanceof LongLocationDecorator || longLocation instanceof LongLocationDecorator && ((LongLocationDecorator)longLocation).allowInt == allowInt) {
            return longLocation;
        }
        return new LongLocationDecorator(longLocation, allowInt);
    }

    static long decodeLong(int lower, int upper) {
        return (long)lower & 0xFFFFFFFFL | (long)upper << 32;
    }

    static int lowerInt(long value2) {
        return (int)value2;
    }

    static int upperInt(long value2) {
        return (int)(value2 >>> 32);
    }

    static int getLocationOrdinal(CoreLocation loc) {
        LocationImpl internal = loc.getInternalLocation();
        boolean isPrimitive = internal instanceof LongLocation;
        if (internal instanceof FieldLocation) {
            return (isPrimitive ? -2147483647 : 0) + ((FieldLocation)internal).getIndex();
        }
        if (internal instanceof ArrayLocation) {
            return (isPrimitive ? -2147483647 : 0) + 1000 + ((ArrayLocation)internal).getIndex();
        }
        throw new IllegalArgumentException(internal.getClass().getName());
    }

    static final class DynamicLongFieldLocation
    extends SimpleLongFieldLocation {
        private final long offset;
        private final Class<? extends DynamicObject> tclass;

        DynamicLongFieldLocation(int index, long offset, Class<? extends DynamicObject> declaringClass) {
            super(index);
            this.offset = offset;
            this.tclass = declaringClass;
            assert (offset % 8L == 0L);
        }

        @Override
        public long getLong(DynamicObject store, boolean guard) {
            return UnsafeAccess.unsafeGetLong(DynamicLongFieldLocation.receiverCast(store, this.tclass), this.offset);
        }

        @Override
        public void setLong(DynamicObject store, long value2, boolean guard, boolean init2) {
            UnsafeAccess.unsafePutLong(DynamicLongFieldLocation.receiverCast(store, this.tclass), this.offset, value2);
        }

        @Override
        public Class<? extends DynamicObject> getDeclaringClass() {
            return this.tclass;
        }
    }

    static final class DynamicObjectFieldLocation
    extends SimpleObjectFieldLocation {
        private final long offset;
        private final Class<? extends DynamicObject> tclass;

        private DynamicObjectFieldLocation(int index, long offset, Class<? extends DynamicObject> declaringClass) {
            super(index);
            this.offset = offset;
            this.tclass = declaringClass;
        }

        DynamicObjectFieldLocation(int index, Field objectField) {
            this(index, UnsafeAccess.objectFieldOffset(objectField), objectField.getDeclaringClass().asSubclass(DynamicObject.class));
            if (objectField.getType() != Object.class) {
                throw new IllegalArgumentException();
            }
        }

        @Override
        public Object get(DynamicObject store, boolean guard) {
            return UnsafeAccess.unsafeGetObject(DynamicObjectFieldLocation.receiverCast(store, this.tclass), this.offset);
        }

        @Override
        public void set(DynamicObject store, Object value2, boolean guard, boolean init2) {
            UnsafeAccess.unsafePutObject(DynamicObjectFieldLocation.receiverCast(store, this.tclass), this.offset, value2);
        }

        @Override
        public Class<? extends DynamicObject> getDeclaringClass() {
            return this.tclass;
        }
    }

    static class BooleanLocationDecorator
    extends PrimitiveLocationDecorator
    implements BooleanLocation {
        protected BooleanLocationDecorator(LongLocation longLocation) {
            super(longLocation);
        }

        @Override
        public final Object get(DynamicObject store, boolean guard) {
            return this.getBoolean(store, guard);
        }

        @Override
        public boolean getBoolean(DynamicObject store, boolean guard) {
            return this.getLongInternal(store, guard) != 0L;
        }

        @Override
        public void setBoolean(DynamicObject store, boolean value2, boolean guard, boolean init2) {
            this.setLongInternal(store, value2 ? 1L : 0L, guard);
        }

        @Override
        public final void set(DynamicObject store, Object value2, boolean guard, boolean init2) throws IncompatibleLocationException {
            if (!this.canStore(value2)) {
                throw BooleanLocationDecorator.incompatibleLocation();
            }
            this.setBoolean(store, (boolean)((Boolean)value2), guard, init2);
        }

        @Override
        public final boolean getBoolean(DynamicObject store, Shape shape) {
            return this.getBoolean(store, BooleanLocationDecorator.checkShape(store, shape));
        }

        @Override
        public final boolean canStore(Object value2) {
            return value2 instanceof Boolean;
        }

        @Override
        public Class<Boolean> getType() {
            return Boolean.TYPE;
        }
    }

    static class DoubleLocationDecorator
    extends PrimitiveLocationDecorator
    implements DoubleLocation {
        private final boolean allowInt;

        protected DoubleLocationDecorator(LongLocation longLocation, boolean allowInt) {
            super(longLocation);
            this.allowInt = allowInt;
        }

        @Override
        public final Object get(DynamicObject store, boolean guard) {
            return this.getDouble(store, guard);
        }

        @Override
        public double getDouble(DynamicObject store, boolean guard) {
            return Double.longBitsToDouble(this.getLongInternal(store, guard));
        }

        @Override
        public void setDouble(DynamicObject store, double value2, boolean guard, boolean init2) {
            this.setLongInternal(store, Double.doubleToRawLongBits(value2), guard);
        }

        @Override
        public final void set(DynamicObject store, Object value2, boolean guard, boolean init2) throws IncompatibleLocationException {
            if (!this.canStore(value2)) {
                throw DoubleLocationDecorator.incompatibleLocation();
            }
            this.setDouble(store, this.doubleValue(value2), guard, init2);
        }

        private double doubleValue(Object value2) {
            if (!this.allowInt || value2 instanceof Double) {
                return (Double)value2;
            }
            return ((Integer)value2).doubleValue();
        }

        @Override
        public final double getDouble(DynamicObject store, Shape shape) {
            return this.getDouble(store, DoubleLocationDecorator.checkShape(store, shape));
        }

        @Override
        public final boolean canStore(Object value2) {
            return value2 instanceof Double || this.allowInt && value2 instanceof Integer;
        }

        @Override
        public Class<Double> getType() {
            return Double.TYPE;
        }

        @Override
        public boolean equals(Object obj) {
            return super.equals(obj) && this.allowInt == ((DoubleLocationDecorator)obj).allowInt;
        }

        @Override
        public boolean isImplicitCastIntToDouble() {
            return this.allowInt;
        }
    }

    static class IntLocationDecorator
    extends PrimitiveLocationDecorator
    implements IntLocation {
        protected IntLocationDecorator(LongLocation longLocation) {
            super(longLocation);
        }

        @Override
        public final Object get(DynamicObject store, boolean guard) {
            return this.getInt(store, guard);
        }

        @Override
        public int getInt(DynamicObject store, boolean guard) {
            return (int)this.getLongInternal(store, guard);
        }

        @Override
        public void setInt(DynamicObject store, int value2, boolean guard, boolean init2) {
            this.setLongInternal(store, value2, guard);
        }

        @Override
        public final void set(DynamicObject store, Object value2, boolean guard, boolean init2) throws IncompatibleLocationException {
            if (!this.canStore(value2)) {
                throw IntLocationDecorator.incompatibleLocation();
            }
            this.setLongInternal(store, ((Integer)value2).intValue(), guard);
        }

        @Override
        public final int getInt(DynamicObject store, Shape shape) {
            return this.getInt(store, IntLocationDecorator.checkShape(store, shape));
        }

        @Override
        public final boolean canStore(Object value2) {
            return value2 instanceof Integer;
        }

        @Override
        public Class<Integer> getType() {
            return Integer.TYPE;
        }
    }

    public static abstract class PrimitiveLocationDecorator
    extends CoreLocation {
        private final LongLocation longLocation;

        protected PrimitiveLocationDecorator(LongLocation longLocation) {
            this.longLocation = longLocation;
        }

        public final long getLongInternal(DynamicObject store, boolean guard) {
            return this.longLocation.getLong(store, guard);
        }

        public final void setLongInternal(DynamicObject store, long value2, boolean guard) {
            this.longLocation.setLong(store, value2, guard, true);
        }

        public final LongLocation getInternalLongLocation() {
            return this.longLocation;
        }

        @Override
        protected final LocationImpl getInternalLocation() {
            return (LocationImpl)((Object)this.longLocation);
        }

        @Override
        public final int primitiveFieldCount() {
            return ((LocationImpl)((Object)this.longLocation)).primitiveFieldCount();
        }

        @Override
        public final int primitiveArrayCount() {
            return ((LocationImpl)((Object)this.longLocation)).primitiveArrayCount();
        }

        @Override
        public final void accept(LocationImpl.LocationVisitor locationVisitor) {
            ((LocationImpl)((Object)this.longLocation)).accept(locationVisitor);
        }

        @Override
        public String getWhereString() {
            return ((LocationImpl)((Object)this.longLocation)).getWhereString();
        }

        @Override
        public boolean equals(Object obj) {
            return super.equals(obj) && this.longLocation.equals(((PrimitiveLocationDecorator)obj).longLocation);
        }

        @Override
        public int hashCode() {
            return this.longLocation.hashCode();
        }
    }

    public static abstract class SimpleLongFieldLocation
    extends FieldLocation
    implements LongLocation {
        protected SimpleLongFieldLocation(int index) {
            super(index);
        }

        @Override
        public final Object get(DynamicObject store, boolean guard) {
            return this.getLong(store, guard);
        }

        @Override
        public final void set(DynamicObject store, Object value2, boolean guard, boolean init2) throws IncompatibleLocationException {
            if (!this.canStore(value2)) {
                throw SimpleLongFieldLocation.incompatibleLocation();
            }
            this.setLong(store, (long)((Long)value2), guard, init2);
        }

        @Override
        public final boolean canStore(Object value2) {
            return value2 instanceof Long;
        }

        @Override
        public abstract long getLong(DynamicObject var1, boolean var2);

        @Override
        public final long getLong(DynamicObject store, Shape shape) {
            return this.getLong(store, SimpleLongFieldLocation.checkShape(store, shape));
        }

        @Override
        public abstract void setLong(DynamicObject var1, long var2, boolean var4, boolean var5);

        @Override
        public int primitiveFieldCount() {
            return 1;
        }

        @Override
        public final Class<Long> getType() {
            return Long.TYPE;
        }

        @Override
        public void accept(LocationImpl.LocationVisitor locationVisitor) {
            locationVisitor.visitPrimitiveField(this.getIndex(), 1);
        }

        @Override
        public boolean isImplicitCastIntToLong() {
            return false;
        }
    }

    static class LongLocationDecorator
    extends PrimitiveLocationDecorator
    implements LongLocation {
        protected final boolean allowInt;

        protected LongLocationDecorator(LongLocation longLocation, boolean allowInt) {
            super(longLocation);
            this.allowInt = allowInt;
        }

        @Override
        public final Object get(DynamicObject store, boolean guard) {
            return this.getLong(store, guard);
        }

        @Override
        public long getLong(DynamicObject store, boolean guard) {
            return super.getLongInternal(store, guard);
        }

        @Override
        public final void set(DynamicObject store, Object value2, boolean guard, boolean init2) throws IncompatibleLocationException {
            if (!this.canStore(value2)) {
                throw LongLocationDecorator.incompatibleLocation();
            }
            this.setLong(store, this.longValue(value2), guard, init2);
        }

        @Override
        public void setLong(DynamicObject store, long value2, boolean guard, boolean init2) {
            super.setLongInternal(store, value2, guard);
        }

        private long longValue(Object value2) {
            if (!this.allowInt || value2 instanceof Long) {
                return (Long)value2;
            }
            return ((Integer)value2).longValue();
        }

        @Override
        public final boolean canStore(Object value2) {
            return value2 instanceof Long || this.allowInt && value2 instanceof Integer;
        }

        @Override
        public Class<Long> getType() {
            return Long.TYPE;
        }

        @Override
        public boolean equals(Object obj) {
            return super.equals(obj) && this.allowInt == ((LongLocationDecorator)obj).allowInt;
        }

        @Override
        public boolean isImplicitCastIntToLong() {
            return this.allowInt;
        }
    }

    static class LongArrayLocation
    extends ArrayLocation
    implements LongLocation {
        private static final int ALIGN = 1;
        protected final boolean allowInt;

        protected LongArrayLocation(int index, boolean allowInt) {
            super(index);
            this.allowInt = allowInt;
        }

        protected LongArrayLocation(int index) {
            this(index, false);
        }

        @Override
        public final Object get(DynamicObject store, boolean guard) {
            return this.getLong(store, guard);
        }

        @Override
        public final void set(DynamicObject store, Object value2, boolean guard, boolean init2) throws IncompatibleLocationException {
            if (!this.canStore(value2)) {
                throw LongArrayLocation.incompatibleLocation();
            }
            this.setLong(store, this.longValue(value2), guard, init2);
        }

        private long longValue(Object value2) {
            if (!this.allowInt || value2 instanceof Long) {
                return (Long)value2;
            }
            return ((Integer)value2).longValue();
        }

        protected static final int[] getArray(DynamicObject store) {
            return LayoutImpl.ACCESS.getPrimitiveArray(store);
        }

        @Override
        public long getLong(DynamicObject store, boolean guard) {
            boolean boundsCheck;
            int[] array = LongArrayLocation.getArray(store);
            int idx = this.index;
            boolean bl = boundsCheck = idx >= 0 && idx < array.length - 1;
            if (boundsCheck) {
                long offset = UnsafeAccess.ARRAY_INT_BASE_OFFSET + UnsafeAccess.ARRAY_INT_INDEX_SCALE * (long)idx;
                return UnsafeAccess.unsafeGetLong(array, offset, boundsCheck, null);
            }
            throw LongArrayLocation.arrayIndexOutOfBounds(idx);
        }

        public final void setLongInternal(DynamicObject store, long value2) {
            int[] array = LongArrayLocation.getArray(store);
            int idx = this.index;
            if (idx < 0 || idx >= array.length - 1) {
                throw LongArrayLocation.arrayIndexOutOfBounds(idx);
            }
            long offset = UnsafeAccess.ARRAY_INT_BASE_OFFSET + UnsafeAccess.ARRAY_INT_INDEX_SCALE * (long)idx;
            UnsafeAccess.unsafePutLong(array, offset, value2, null);
        }

        private static ArrayIndexOutOfBoundsException arrayIndexOutOfBounds(int idx) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            throw new ArrayIndexOutOfBoundsException(idx);
        }

        @Override
        public void setLong(DynamicObject store, long value2, boolean guard, boolean init2) {
            this.setLongInternal(store, value2);
        }

        @Override
        public final boolean canStore(Object value2) {
            return value2 instanceof Long || this.allowInt && value2 instanceof Integer;
        }

        @Override
        public final Class<Long> getType() {
            return Long.TYPE;
        }

        @Override
        public int primitiveArrayCount() {
            return 2;
        }

        @Override
        public final void accept(LocationImpl.LocationVisitor locationVisitor) {
            locationVisitor.visitPrimitiveArray(this.getIndex(), 2);
        }

        @Override
        public boolean equals(Object obj) {
            return super.equals(obj) && this.allowInt == ((LongArrayLocation)obj).allowInt;
        }

        @Override
        public boolean isImplicitCastIntToLong() {
            return this.allowInt;
        }
    }

    public static abstract class SimpleObjectFieldLocation
    extends FieldLocation
    implements ObjectLocation {
        protected SimpleObjectFieldLocation(int index) {
            super(index);
        }

        @Override
        public abstract Object get(DynamicObject var1, boolean var2);

        @Override
        public abstract void set(DynamicObject var1, Object var2, boolean var3, boolean var4);

        @Override
        public boolean canStore(Object value2) {
            return true;
        }

        @Override
        public Class<? extends Object> getType() {
            return Object.class;
        }

        @Override
        public boolean isNonNull() {
            return false;
        }

        @Override
        protected void clear(DynamicObject store) {
            this.set(store, null, false, true);
        }

        @Override
        public int objectFieldCount() {
            return 1;
        }

        @Override
        public final void accept(LocationImpl.LocationVisitor locationVisitor) {
            locationVisitor.visitObjectField(this.getIndex(), 1);
        }
    }

    static class ObjectArrayLocation
    extends ArrayLocation
    implements ObjectLocation {
        protected ObjectArrayLocation(int index) {
            super(index);
        }

        protected static final Object[] getArray(DynamicObject store) {
            return LayoutImpl.ACCESS.getObjectArray(store);
        }

        @Override
        public Object get(DynamicObject store, boolean guard) {
            return ObjectArrayLocation.getArray(store)[this.index];
        }

        @Override
        public final void set(DynamicObject store, Object value2, boolean guard, boolean init2) {
            ObjectArrayLocation.getArray((DynamicObject)store)[this.index] = value2;
        }

        @Override
        public boolean canStore(Object value2) {
            return true;
        }

        @Override
        public Class<? extends Object> getType() {
            return Object.class;
        }

        @Override
        public final boolean isNonNull() {
            return false;
        }

        @Override
        protected void clear(DynamicObject store) {
            this.set(store, null, false, true);
        }

        @Override
        public int objectArrayCount() {
            return 1;
        }

        @Override
        public final void accept(LocationImpl.LocationVisitor locationVisitor) {
            locationVisitor.visitObjectArray(this.index, 1);
        }
    }

    public static abstract class FieldLocation
    extends InstanceLocation {
        protected FieldLocation(int index) {
            super(index);
        }

        @Override
        public int hashCode() {
            int prime = 31;
            int result = super.hashCode();
            result = 31 * result + this.index;
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (!super.equals(obj)) {
                return false;
            }
            FieldLocation other = (FieldLocation)obj;
            return this.index == other.index;
        }

        @Override
        public String getWhereString() {
            return "@" + this.index;
        }

        public abstract Class<? extends DynamicObject> getDeclaringClass();

        protected static DynamicObject receiverCast(DynamicObject store, Class<? extends DynamicObject> tclass) {
            try {
                return tclass.cast(Objects.requireNonNull(store));
            }
            catch (ClassCastException | NullPointerException ex) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                throw FieldLocation.illegalReceiver(store, tclass);
            }
        }

        protected static IllegalArgumentException illegalReceiver(DynamicObject store, Class<? extends DynamicObject> declaringClass) {
            CompilerAsserts.neverPartOfCompilation();
            return new IllegalArgumentException("Invalid receiver type (expected " + declaringClass + ", was " + (store == null ? null : store.getClass()) + ")");
        }
    }

    public static abstract class ArrayLocation
    extends InstanceLocation {
        protected ArrayLocation(int index) {
            super(index);
        }

        @Override
        public int hashCode() {
            int prime = 31;
            int result = super.hashCode();
            result = 31 * result + this.index;
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (!super.equals(obj)) {
                return false;
            }
            ArrayLocation other = (ArrayLocation)obj;
            return this.index == other.index;
        }

        @Override
        public String getWhereString() {
            return "[" + this.index + "]";
        }
    }

    static abstract class InstanceLocation
    extends CoreLocation {
        protected final int index;

        protected InstanceLocation(int index) {
            this.index = index;
        }

        public final int getIndex() {
            return this.index;
        }
    }

    public static final class DeclaredLocation
    extends ValueLocation {
        DeclaredLocation(Object value2) {
            super(value2);
        }

        @Override
        public boolean isDeclared() {
            return true;
        }
    }

    public static final class ConstantLocation
    extends ValueLocation {
        ConstantLocation(Object value2) {
            super(value2);
        }

        @Override
        public boolean isConstant() {
            return true;
        }
    }

    public static abstract class ValueLocation
    extends CoreLocation {
        private final Object value;

        ValueLocation(Object value2) {
            assert (!(value2 instanceof Location));
            this.value = value2;
        }

        @Override
        public int hashCode() {
            int prime = 31;
            int result = super.hashCode();
            result = 31 * result + (this.value == null ? 0 : this.value.hashCode());
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            return super.equals(obj) && Objects.equals(this.value, ((ValueLocation)obj).value);
        }

        @Override
        public final Object get(DynamicObject store, boolean guard) {
            return this.value;
        }

        @Override
        public boolean canStore(Object val) {
            return ValueLocation.valueEquals(this.value, val);
        }

        @Override
        public final void set(DynamicObject store, Object value2, boolean guard, boolean init2) throws IncompatibleLocationException {
            if (!this.canStore(value2)) {
                if (init2) {
                    CompilerDirectives.transferToInterpreterAndInvalidate();
                    throw new UnsupportedOperationException();
                }
                throw ValueLocation.incompatibleLocation();
            }
        }

        @Override
        public String toString() {
            return "=" + String.valueOf(this.value);
        }

        @Override
        public final void accept(LocationImpl.LocationVisitor locationVisitor) {
        }

        @Override
        public final boolean isValue() {
            return true;
        }
    }

    public static interface BooleanLocation
    extends TypedLocation,
    com.oracle.truffle.api.object.BooleanLocation {
        @Override
        public boolean getBoolean(DynamicObject var1, boolean var2);

        public void setBoolean(DynamicObject var1, boolean var2, boolean var3, boolean var4);

        @Override
        default public Class<Boolean> getType() {
            return Boolean.TYPE;
        }

        @Override
        default public boolean getBoolean(DynamicObject store, Shape shape) {
            return this.getBoolean(store, store.getShape() == shape);
        }

        @Override
        default public void setBoolean(DynamicObject store, boolean value2, Shape shape) {
            this.setBoolean(store, value2, store.getShape() == shape, false);
        }

        @Override
        default public void setBoolean(DynamicObject store, boolean value2) {
            this.setBoolean(store, value2, false, false);
        }

        @Override
        default public void setBoolean(DynamicObject store, boolean value2, Shape oldShape, Shape newShape) {
            LayoutImpl.ACCESS.grow(store, oldShape, newShape);
            this.setBoolean(store, value2, false, false);
            LayoutImpl.ACCESS.setShapeWithStoreFence(store, newShape);
        }
    }

    public static interface DoubleLocation
    extends TypedLocation,
    com.oracle.truffle.api.object.DoubleLocation {
        @Override
        public double getDouble(DynamicObject var1, boolean var2);

        public void setDouble(DynamicObject var1, double var2, boolean var4, boolean var5);

        @Override
        default public Class<Double> getType() {
            return Double.TYPE;
        }

        public boolean isImplicitCastIntToDouble();

        @Override
        default public double getDouble(DynamicObject store, Shape shape) {
            return this.getDouble(store, store.getShape() == shape);
        }

        @Override
        default public void setDouble(DynamicObject store, double value2, Shape shape) {
            this.setDouble(store, value2, store.getShape() == shape, false);
        }

        @Override
        default public void setDouble(DynamicObject store, double value2) {
            this.setDouble(store, value2, false, false);
        }

        @Override
        default public void setDouble(DynamicObject store, double value2, Shape oldShape, Shape newShape) {
            LayoutImpl.ACCESS.grow(store, oldShape, newShape);
            this.setDouble(store, value2, false, false);
            LayoutImpl.ACCESS.setShapeWithStoreFence(store, newShape);
        }
    }

    public static interface LongLocation
    extends TypedLocation,
    com.oracle.truffle.api.object.LongLocation {
        @Override
        public long getLong(DynamicObject var1, boolean var2);

        public void setLong(DynamicObject var1, long var2, boolean var4, boolean var5);

        @Override
        default public Class<Long> getType() {
            return Long.TYPE;
        }

        public boolean isImplicitCastIntToLong();

        @Override
        default public long getLong(DynamicObject store, Shape shape) {
            return this.getLong(store, store.getShape() == shape);
        }

        @Override
        default public void setLong(DynamicObject store, long value2, Shape shape) {
            this.setLong(store, value2, store.getShape() == shape, false);
        }

        @Override
        default public void setLong(DynamicObject store, long value2) {
            this.setLong(store, value2, false, false);
        }

        @Override
        default public void setLong(DynamicObject store, long value2, Shape oldShape, Shape newShape) {
            LayoutImpl.ACCESS.grow(store, oldShape, newShape);
            this.setLong(store, value2, false, false);
            LayoutImpl.ACCESS.setShapeWithStoreFence(store, newShape);
        }
    }

    public static interface IntLocation
    extends TypedLocation,
    com.oracle.truffle.api.object.IntLocation {
        @Override
        public int getInt(DynamicObject var1, boolean var2);

        public void setInt(DynamicObject var1, int var2, boolean var3, boolean var4);

        @Override
        default public Class<Integer> getType() {
            return Integer.TYPE;
        }

        @Override
        default public int getInt(DynamicObject store, Shape shape) {
            return this.getInt(store, store.getShape() == shape);
        }

        @Override
        default public void setInt(DynamicObject store, int value2, Shape shape) {
            this.setInt(store, value2, store.getShape() == shape, false);
        }

        @Override
        default public void setInt(DynamicObject store, int value2) {
            this.setInt(store, value2, false, false);
        }

        @Override
        default public void setInt(DynamicObject store, int value2, Shape oldShape, Shape newShape) {
            LayoutImpl.ACCESS.grow(store, oldShape, newShape);
            this.setInt(store, value2, false, false);
            LayoutImpl.ACCESS.setShapeWithStoreFence(store, newShape);
        }
    }

    public static interface ObjectLocation
    extends TypedLocation,
    com.oracle.truffle.api.object.ObjectLocation {
        @Override
        public Class<? extends Object> getType();

        @Override
        public boolean isNonNull();
    }

    public static interface TypedLocation {
        public Class<?> getType();
    }
}

