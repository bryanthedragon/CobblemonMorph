package com.oracle.truffle.object;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.object.DynamicObject;
import com.oracle.truffle.api.object.IncompatibleLocationException;
import com.oracle.truffle.api.object.Location;
import com.oracle.truffle.api.object.Shape;
import java.lang.reflect.Field;
import java.util.Objects;

abstract class CoreLocations {
   static final int LONG_FIELD_SLOT_SIZE = 1;
   static final int LONG_ARRAY_SLOT_SIZE = 2;
   static final int OBJECT_SLOT_SIZE = 1;
   static final int MAX_DYNAMIC_FIELDS = 1000;

   public static CoreLocations.LongLocation createLongLocation(CoreLocations.LongLocation longLocation, boolean allowInt) {
      return (CoreLocations.LongLocation)((allowInt || !(longLocation instanceof CoreLocations.LongLocationDecorator))
            && (!(longLocation instanceof CoreLocations.LongLocationDecorator) || ((CoreLocations.LongLocationDecorator)longLocation).allowInt != allowInt)
         ? new CoreLocations.LongLocationDecorator(longLocation, allowInt)
         : longLocation);
   }

   static long decodeLong(int lower, int upper) {
      return lower & 4294967295L | (long)upper << 32;
   }

   static int lowerInt(long value) {
      return (int)value;
   }

   static int upperInt(long value) {
      return (int)(value >>> 32);
   }

   static int getLocationOrdinal(CoreLocation loc) {
      LocationImpl internal = loc.getInternalLocation();
      boolean isPrimitive = internal instanceof CoreLocations.LongLocation;
      if (internal instanceof CoreLocations.FieldLocation) {
         return (isPrimitive ? -2147483647 : 0) + ((CoreLocations.FieldLocation)internal).getIndex();
      } else if (internal instanceof CoreLocations.ArrayLocation) {
         return (isPrimitive ? -2147483647 : 0) + 1000 + ((CoreLocations.ArrayLocation)internal).getIndex();
      } else {
         throw new IllegalArgumentException(internal.getClass().getName());
      }
   }

   public abstract static class ArrayLocation extends CoreLocations.InstanceLocation {
      protected ArrayLocation(int index) {
         super(index);
      }

      @Override
      public int hashCode() {
         int prime = 31;
         int result = super.hashCode();
         return 31 * result + this.index;
      }

      @Override
      public boolean equals(Object obj) {
         if (!super.equals(obj)) {
            return false;
         } else {
            CoreLocations.ArrayLocation other = (CoreLocations.ArrayLocation)obj;
            return this.index == other.index;
         }
      }

      @Override
      public String getWhereString() {
         return "[" + this.index + "]";
      }
   }

   public interface BooleanLocation extends CoreLocations.TypedLocation, com.oracle.truffle.api.object.BooleanLocation {
      @Override
      boolean getBoolean(DynamicObject store, boolean guard);

      void setBoolean(DynamicObject store, boolean value, boolean guard, boolean init);

      @Override
      default Class<Boolean> getType() {
         return boolean.class;
      }

      @Override
      default boolean getBoolean(DynamicObject store, Shape shape) {
         return this.getBoolean(store, store.getShape() == shape);
      }

      @Override
      default void setBoolean(DynamicObject store, boolean value, Shape shape) {
         this.setBoolean(store, value, store.getShape() == shape, false);
      }

      @Override
      default void setBoolean(DynamicObject store, boolean value) {
         this.setBoolean(store, value, false, false);
      }

      @Override
      default void setBoolean(DynamicObject store, boolean value, Shape oldShape, Shape newShape) {
         LayoutImpl.ACCESS.grow(store, oldShape, newShape);
         this.setBoolean(store, value, false, false);
         LayoutImpl.ACCESS.setShapeWithStoreFence(store, newShape);
      }
   }

   static class BooleanLocationDecorator extends CoreLocations.PrimitiveLocationDecorator implements CoreLocations.BooleanLocation {
      protected BooleanLocationDecorator(CoreLocations.LongLocation longLocation) {
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
      public void setBoolean(DynamicObject store, boolean value, boolean guard, boolean init) {
         this.setLongInternal(store, value ? 1L : 0L, guard);
      }

      @Override
      public final void set(DynamicObject store, Object value, boolean guard, boolean init) throws IncompatibleLocationException {
         if (this.canStore(value)) {
            this.setBoolean(store, (Boolean)value, guard, init);
         } else {
            throw incompatibleLocation();
         }
      }

      @Override
      public final boolean getBoolean(DynamicObject store, Shape shape) {
         return this.getBoolean(store, checkShape(store, shape));
      }

      @Override
      public final boolean canStore(Object value) {
         return value instanceof Boolean;
      }

      @Override
      public Class<Boolean> getType() {
         return boolean.class;
      }
   }

   public static final class ConstantLocation extends CoreLocations.ValueLocation {
      ConstantLocation(Object value) {
         super(value);
      }

      @Override
      public boolean isConstant() {
         return true;
      }
   }

   public static final class DeclaredLocation extends CoreLocations.ValueLocation {
      DeclaredLocation(Object value) {
         super(value);
      }

      @Override
      public boolean isDeclared() {
         return true;
      }
   }

   public interface DoubleLocation extends CoreLocations.TypedLocation, com.oracle.truffle.api.object.DoubleLocation {
      @Override
      double getDouble(DynamicObject store, boolean guard);

      void setDouble(DynamicObject store, double value, boolean guard, boolean init);

      @Override
      default Class<Double> getType() {
         return double.class;
      }

      boolean isImplicitCastIntToDouble();

      @Override
      default double getDouble(DynamicObject store, Shape shape) {
         return this.getDouble(store, store.getShape() == shape);
      }

      @Override
      default void setDouble(DynamicObject store, double value, Shape shape) {
         this.setDouble(store, value, store.getShape() == shape, false);
      }

      @Override
      default void setDouble(DynamicObject store, double value) {
         this.setDouble(store, value, false, false);
      }

      @Override
      default void setDouble(DynamicObject store, double value, Shape oldShape, Shape newShape) {
         LayoutImpl.ACCESS.grow(store, oldShape, newShape);
         this.setDouble(store, value, false, false);
         LayoutImpl.ACCESS.setShapeWithStoreFence(store, newShape);
      }
   }

   static class DoubleLocationDecorator extends CoreLocations.PrimitiveLocationDecorator implements CoreLocations.DoubleLocation {
      private final boolean allowInt;

      protected DoubleLocationDecorator(CoreLocations.LongLocation longLocation, boolean allowInt) {
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
      public void setDouble(DynamicObject store, double value, boolean guard, boolean init) {
         this.setLongInternal(store, Double.doubleToRawLongBits(value), guard);
      }

      @Override
      public final void set(DynamicObject store, Object value, boolean guard, boolean init) throws IncompatibleLocationException {
         if (this.canStore(value)) {
            this.setDouble(store, this.doubleValue(value), guard, init);
         } else {
            throw incompatibleLocation();
         }
      }

      private double doubleValue(Object value) {
         return this.allowInt && !(value instanceof Double) ? ((Integer)value).doubleValue() : (Double)value;
      }

      @Override
      public final double getDouble(DynamicObject store, Shape shape) {
         return this.getDouble(store, checkShape(store, shape));
      }

      @Override
      public final boolean canStore(Object value) {
         return value instanceof Double || this.allowInt && value instanceof Integer;
      }

      @Override
      public Class<Double> getType() {
         return double.class;
      }

      @Override
      public boolean equals(Object obj) {
         return super.equals(obj) && this.allowInt == ((CoreLocations.DoubleLocationDecorator)obj).allowInt;
      }

      @Override
      public boolean isImplicitCastIntToDouble() {
         return this.allowInt;
      }
   }

   static final class DynamicLongFieldLocation extends CoreLocations.SimpleLongFieldLocation {
      private final long offset;
      private final Class<? extends DynamicObject> tclass;

      DynamicLongFieldLocation(int index, long offset, Class<? extends DynamicObject> declaringClass) {
         super(index);
         this.offset = offset;
         this.tclass = declaringClass;

         assert offset % 8L == 0L;
      }

      @Override
      public long getLong(DynamicObject store, boolean guard) {
         return UnsafeAccess.unsafeGetLong(receiverCast(store, this.tclass), this.offset);
      }

      @Override
      public void setLong(DynamicObject store, long value, boolean guard, boolean init) {
         UnsafeAccess.unsafePutLong(receiverCast(store, this.tclass), this.offset, value);
      }

      @Override
      public Class<? extends DynamicObject> getDeclaringClass() {
         return this.tclass;
      }
   }

   static final class DynamicObjectFieldLocation extends CoreLocations.SimpleObjectFieldLocation {
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
         return UnsafeAccess.unsafeGetObject(receiverCast(store, this.tclass), this.offset);
      }

      @Override
      public void set(DynamicObject store, Object value, boolean guard, boolean init) {
         UnsafeAccess.unsafePutObject(receiverCast(store, this.tclass), this.offset, value);
      }

      @Override
      public Class<? extends DynamicObject> getDeclaringClass() {
         return this.tclass;
      }
   }

   public abstract static class FieldLocation extends CoreLocations.InstanceLocation {
      protected FieldLocation(int index) {
         super(index);
      }

      @Override
      public int hashCode() {
         int prime = 31;
         int result = super.hashCode();
         return 31 * result + this.index;
      }

      @Override
      public boolean equals(Object obj) {
         if (!super.equals(obj)) {
            return false;
         } else {
            CoreLocations.FieldLocation other = (CoreLocations.FieldLocation)obj;
            return this.index == other.index;
         }
      }

      @Override
      public String getWhereString() {
         return "@" + this.index;
      }

      public abstract Class<? extends DynamicObject> getDeclaringClass();

      protected static DynamicObject receiverCast(DynamicObject store, Class<? extends DynamicObject> tclass) {
         try {
            return tclass.cast(Objects.requireNonNull(store));
         } catch (NullPointerException | ClassCastException var3) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            throw illegalReceiver(store, tclass);
         }
      }

      protected static IllegalArgumentException illegalReceiver(DynamicObject store, Class<? extends DynamicObject> declaringClass) {
         CompilerAsserts.neverPartOfCompilation();
         return new IllegalArgumentException("Invalid receiver type (expected " + declaringClass + ", was " + (store == null ? null : store.getClass()) + ")");
      }
   }

   abstract static class InstanceLocation extends CoreLocation {
      protected final int index;

      protected InstanceLocation(int index) {
         this.index = index;
      }

      public final int getIndex() {
         return this.index;
      }
   }

   public interface IntLocation extends CoreLocations.TypedLocation, com.oracle.truffle.api.object.IntLocation {
      @Override
      int getInt(DynamicObject store, boolean guard);

      void setInt(DynamicObject store, int value, boolean guard, boolean init);

      @Override
      default Class<Integer> getType() {
         return int.class;
      }

      @Override
      default int getInt(DynamicObject store, Shape shape) {
         return this.getInt(store, store.getShape() == shape);
      }

      @Override
      default void setInt(DynamicObject store, int value, Shape shape) {
         this.setInt(store, value, store.getShape() == shape, false);
      }

      @Override
      default void setInt(DynamicObject store, int value) {
         this.setInt(store, value, false, false);
      }

      @Override
      default void setInt(DynamicObject store, int value, Shape oldShape, Shape newShape) {
         LayoutImpl.ACCESS.grow(store, oldShape, newShape);
         this.setInt(store, value, false, false);
         LayoutImpl.ACCESS.setShapeWithStoreFence(store, newShape);
      }
   }

   static class IntLocationDecorator extends CoreLocations.PrimitiveLocationDecorator implements CoreLocations.IntLocation {
      protected IntLocationDecorator(CoreLocations.LongLocation longLocation) {
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
      public void setInt(DynamicObject store, int value, boolean guard, boolean init) {
         this.setLongInternal(store, value, guard);
      }

      @Override
      public final void set(DynamicObject store, Object value, boolean guard, boolean init) throws IncompatibleLocationException {
         if (this.canStore(value)) {
            this.setLongInternal(store, ((Integer)value).intValue(), guard);
         } else {
            throw incompatibleLocation();
         }
      }

      @Override
      public final int getInt(DynamicObject store, Shape shape) {
         return this.getInt(store, checkShape(store, shape));
      }

      @Override
      public final boolean canStore(Object value) {
         return value instanceof Integer;
      }

      @Override
      public Class<Integer> getType() {
         return int.class;
      }
   }

   static class LongArrayLocation extends CoreLocations.ArrayLocation implements CoreLocations.LongLocation {
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
      public final void set(DynamicObject store, Object value, boolean guard, boolean init) throws IncompatibleLocationException {
         if (this.canStore(value)) {
            this.setLong(store, this.longValue(value), guard, init);
         } else {
            throw incompatibleLocation();
         }
      }

      private long longValue(Object value) {
         return this.allowInt && !(value instanceof Long) ? ((Integer)value).longValue() : (Long)value;
      }

      protected static final int[] getArray(DynamicObject store) {
         return LayoutImpl.ACCESS.getPrimitiveArray(store);
      }

      @Override
      public long getLong(DynamicObject store, boolean guard) {
         int[] array = getArray(store);
         int idx = this.index;
         boolean boundsCheck = idx >= 0 && idx < array.length - 1;
         if (boundsCheck) {
            long offset = UnsafeAccess.ARRAY_INT_BASE_OFFSET + UnsafeAccess.ARRAY_INT_INDEX_SCALE * idx;
            return UnsafeAccess.unsafeGetLong(array, offset, boundsCheck, null);
         } else {
            throw arrayIndexOutOfBounds(idx);
         }
      }

      public final void setLongInternal(DynamicObject store, long value) {
         int[] array = getArray(store);
         int idx = this.index;
         if (idx >= 0 && idx < array.length - 1) {
            long offset = UnsafeAccess.ARRAY_INT_BASE_OFFSET + UnsafeAccess.ARRAY_INT_INDEX_SCALE * idx;
            UnsafeAccess.unsafePutLong(array, offset, value, null);
         } else {
            throw arrayIndexOutOfBounds(idx);
         }
      }

      private static ArrayIndexOutOfBoundsException arrayIndexOutOfBounds(int idx) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         throw new ArrayIndexOutOfBoundsException(idx);
      }

      @Override
      public void setLong(DynamicObject store, long value, boolean guard, boolean init) {
         this.setLongInternal(store, value);
      }

      @Override
      public final boolean canStore(Object value) {
         return value instanceof Long || this.allowInt && value instanceof Integer;
      }

      @Override
      public final Class<Long> getType() {
         return long.class;
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
         return super.equals(obj) && this.allowInt == ((CoreLocations.LongArrayLocation)obj).allowInt;
      }

      @Override
      public boolean isImplicitCastIntToLong() {
         return this.allowInt;
      }
   }

   public interface LongLocation extends CoreLocations.TypedLocation, com.oracle.truffle.api.object.LongLocation {
      @Override
      long getLong(DynamicObject store, boolean guard);

      void setLong(DynamicObject store, long value, boolean guard, boolean init);

      @Override
      default Class<Long> getType() {
         return long.class;
      }

      boolean isImplicitCastIntToLong();

      @Override
      default long getLong(DynamicObject store, Shape shape) {
         return this.getLong(store, store.getShape() == shape);
      }

      @Override
      default void setLong(DynamicObject store, long value, Shape shape) {
         this.setLong(store, value, store.getShape() == shape, false);
      }

      @Override
      default void setLong(DynamicObject store, long value) {
         this.setLong(store, value, false, false);
      }

      @Override
      default void setLong(DynamicObject store, long value, Shape oldShape, Shape newShape) {
         LayoutImpl.ACCESS.grow(store, oldShape, newShape);
         this.setLong(store, value, false, false);
         LayoutImpl.ACCESS.setShapeWithStoreFence(store, newShape);
      }
   }

   static class LongLocationDecorator extends CoreLocations.PrimitiveLocationDecorator implements CoreLocations.LongLocation {
      protected final boolean allowInt;

      protected LongLocationDecorator(CoreLocations.LongLocation longLocation, boolean allowInt) {
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
      public final void set(DynamicObject store, Object value, boolean guard, boolean init) throws IncompatibleLocationException {
         if (this.canStore(value)) {
            this.setLong(store, this.longValue(value), guard, init);
         } else {
            throw incompatibleLocation();
         }
      }

      @Override
      public void setLong(DynamicObject store, long value, boolean guard, boolean init) {
         super.setLongInternal(store, value, guard);
      }

      private long longValue(Object value) {
         return this.allowInt && !(value instanceof Long) ? ((Integer)value).longValue() : (Long)value;
      }

      @Override
      public final boolean canStore(Object value) {
         return value instanceof Long || this.allowInt && value instanceof Integer;
      }

      @Override
      public Class<Long> getType() {
         return long.class;
      }

      @Override
      public boolean equals(Object obj) {
         return super.equals(obj) && this.allowInt == ((CoreLocations.LongLocationDecorator)obj).allowInt;
      }

      @Override
      public boolean isImplicitCastIntToLong() {
         return this.allowInt;
      }
   }

   static class ObjectArrayLocation extends CoreLocations.ArrayLocation implements CoreLocations.ObjectLocation {
      protected ObjectArrayLocation(int index) {
         super(index);
      }

      protected static final Object[] getArray(DynamicObject store) {
         return LayoutImpl.ACCESS.getObjectArray(store);
      }

      @Override
      public Object get(DynamicObject store, boolean guard) {
         return getArray(store)[this.index];
      }

      @Override
      public final void set(DynamicObject store, Object value, boolean guard, boolean init) {
         getArray(store)[this.index] = value;
      }

      @Override
      public boolean canStore(Object value) {
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

   public interface ObjectLocation extends CoreLocations.TypedLocation, com.oracle.truffle.api.object.ObjectLocation {
      @Override
      Class<? extends Object> getType();

      @Override
      boolean isNonNull();
   }

   public abstract static class PrimitiveLocationDecorator extends CoreLocation {
      private final CoreLocations.LongLocation longLocation;

      protected PrimitiveLocationDecorator(CoreLocations.LongLocation longLocation) {
         this.longLocation = longLocation;
      }

      public final long getLongInternal(DynamicObject store, boolean guard) {
         return this.longLocation.getLong(store, guard);
      }

      public final void setLongInternal(DynamicObject store, long value, boolean guard) {
         this.longLocation.setLong(store, value, guard, true);
      }

      public final CoreLocations.LongLocation getInternalLongLocation() {
         return this.longLocation;
      }

      @Override
      protected final LocationImpl getInternalLocation() {
         return (LocationImpl)this.longLocation;
      }

      @Override
      public final int primitiveFieldCount() {
         return ((LocationImpl)this.longLocation).primitiveFieldCount();
      }

      @Override
      public final int primitiveArrayCount() {
         return ((LocationImpl)this.longLocation).primitiveArrayCount();
      }

      @Override
      public final void accept(LocationImpl.LocationVisitor locationVisitor) {
         ((LocationImpl)this.longLocation).accept(locationVisitor);
      }

      @Override
      public String getWhereString() {
         return ((LocationImpl)this.longLocation).getWhereString();
      }

      @Override
      public boolean equals(Object obj) {
         return super.equals(obj) && this.longLocation.equals(((CoreLocations.PrimitiveLocationDecorator)obj).longLocation);
      }

      @Override
      public int hashCode() {
         return this.longLocation.hashCode();
      }
   }

   public abstract static class SimpleLongFieldLocation extends CoreLocations.FieldLocation implements CoreLocations.LongLocation {
      protected SimpleLongFieldLocation(int index) {
         super(index);
      }

      @Override
      public final Object get(DynamicObject store, boolean guard) {
         return this.getLong(store, guard);
      }

      @Override
      public final void set(DynamicObject store, Object value, boolean guard, boolean init) throws IncompatibleLocationException {
         if (this.canStore(value)) {
            this.setLong(store, (Long)value, guard, init);
         } else {
            throw incompatibleLocation();
         }
      }

      @Override
      public final boolean canStore(Object value) {
         return value instanceof Long;
      }

      @Override
      public abstract long getLong(DynamicObject store, boolean guard);

      @Override
      public final long getLong(DynamicObject store, Shape shape) {
         return this.getLong(store, checkShape(store, shape));
      }

      @Override
      public abstract void setLong(DynamicObject store, long value, boolean guard, boolean init);

      @Override
      public int primitiveFieldCount() {
         return 1;
      }

      @Override
      public final Class<Long> getType() {
         return long.class;
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

   public abstract static class SimpleObjectFieldLocation extends CoreLocations.FieldLocation implements CoreLocations.ObjectLocation {
      protected SimpleObjectFieldLocation(int index) {
         super(index);
      }

      @Override
      public abstract Object get(DynamicObject store, boolean guard);

      @Override
      public abstract void set(DynamicObject store, Object value, boolean guard, boolean init);

      @Override
      public boolean canStore(Object value) {
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

   public interface TypedLocation {
      Class<?> getType();
   }

   public abstract static class ValueLocation extends CoreLocation {
      private final Object value;

      ValueLocation(Object value) {
         assert !(value instanceof Location);

         this.value = value;
      }

      @Override
      public int hashCode() {
         int prime = 31;
         int result = super.hashCode();
         return 31 * result + (this.value == null ? 0 : this.value.hashCode());
      }

      @Override
      public boolean equals(Object obj) {
         return super.equals(obj) && Objects.equals(this.value, ((CoreLocations.ValueLocation)obj).value);
      }

      @Override
      public final Object get(DynamicObject store, boolean guard) {
         return this.value;
      }

      @Override
      public boolean canStore(Object val) {
         return valueEquals(this.value, val);
      }

      @Override
      public final void set(DynamicObject store, Object value, boolean guard, boolean init) throws IncompatibleLocationException {
         if (!this.canStore(value)) {
            if (init) {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               throw new UnsupportedOperationException();
            } else {
               throw incompatibleLocation();
            }
         }
      }

      @Override
      public String toString() {
         return "=" + this.value;
      }

      @Override
      public final void accept(LocationImpl.LocationVisitor locationVisitor) {
      }

      @Override
      public final boolean isValue() {
         return true;
      }
   }
}
