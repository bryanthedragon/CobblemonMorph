package com.oracle.truffle.object;

import com.oracle.truffle.api.Assumption;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.api.object.DynamicObject;
import com.oracle.truffle.api.object.IncompatibleLocationException;
import com.oracle.truffle.api.object.Location;
import com.oracle.truffle.api.object.LongLocation;
import com.oracle.truffle.api.object.Shape;
import java.util.Objects;

public abstract class LocationImpl extends Location {
   protected LocationImpl() {
   }

   @Override
   public void set(DynamicObject store, Object value, Shape shape) throws IncompatibleLocationException {
      this.set(store, value, checkShape(store, shape), false);
   }

   @Override
   public void set(DynamicObject store, Object value, Shape oldShape, Shape newShape) throws IncompatibleLocationException {
      if (this.canStore(value)) {
         LayoutImpl.ACCESS.grow(store, oldShape, newShape);

         try {
            this.setInternal(store, value);
         } catch (IncompatibleLocationException var6) {
            throw shouldNotHappen(var6);
         }

         LayoutImpl.ACCESS.setShapeWithStoreFence(store, newShape);
      } else {
         throw incompatibleLocation();
      }
   }

   @Override
   protected final Object getInternal(DynamicObject store) {
      throw new UnsupportedOperationException();
   }

   @Override
   public abstract Object get(DynamicObject store, boolean guard);

   @Override
   protected long getLong(DynamicObject store, boolean guard) throws UnexpectedResultException {
      return expectLong(this.get(store, guard));
   }

   @Override
   protected int getInt(DynamicObject store, boolean guard) throws UnexpectedResultException {
      return expectInteger(this.get(store, guard));
   }

   @Override
   protected double getDouble(DynamicObject store, boolean guard) throws UnexpectedResultException {
      return expectDouble(this.get(store, guard));
   }

   protected boolean getBoolean(DynamicObject store, boolean guard) throws UnexpectedResultException {
      return expectBoolean(this.get(store, guard));
   }

   protected abstract void set(DynamicObject store, Object value, boolean guard, boolean init) throws IncompatibleLocationException;

   protected void setInt(DynamicObject store, int value, boolean guard, boolean init) throws IncompatibleLocationException {
      this.set(store, value, guard, init);
   }

   protected void setLong(DynamicObject store, long value, boolean guard, boolean init) throws IncompatibleLocationException {
      this.set(store, value, guard, init);
   }

   protected void setDouble(DynamicObject store, double value, boolean guard, boolean init) throws IncompatibleLocationException {
      this.set(store, value, guard, init);
   }

   protected static final boolean checkShape(DynamicObject store, Shape shape) {
      return store.getShape() == shape;
   }

   @Override
   protected final void setInternal(DynamicObject store, Object value) throws IncompatibleLocationException {
      this.set(store, value, false, true);
   }

   @Override
   public boolean canSet(DynamicObject store, Object value) {
      return this.canStore(value) && this.canStoreFinal(store, value);
   }

   @Override
   public boolean canSet(Object value) {
      return this.canSet(null, value);
   }

   @Override
   public boolean canStore(Object value) {
      return true;
   }

   protected boolean canStoreFinal(DynamicObject store, Object value) {
      return true;
   }

   @Override
   public boolean isFinal() {
      return false;
   }

   @Override
   public boolean isConstant() {
      return false;
   }

   @Override
   public int hashCode() {
      int prime = 31;
      int result = 1;
      return 31 * result + (this.isFinal() ? 1231 : 1237);
   }

   @Override
   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else {
         return obj == null ? false : this.getClass() == obj.getClass();
      }
   }

   @Override
   public String toString() {
      String finalString = this.isFinal() ? "f" : "";
      String typeString = Objects.requireNonNullElse(this.getType(), Object.class).getSimpleName();
      return finalString + typeString + this.getWhereString();
   }

   protected String getWhereString() {
      return "";
   }

   public int objectArrayCount() {
      return 0;
   }

   public int objectFieldCount() {
      return 0;
   }

   public int primitiveFieldCount() {
      return 0;
   }

   public int primitiveArrayCount() {
      return 0;
   }

   public abstract void accept(LocationImpl.LocationVisitor locationVisitor);

   protected LocationImpl getInternalLocation() {
      return this;
   }

   static boolean isSameLocation(LocationImpl loc1, LocationImpl loc2) {
      return loc1 == loc2 || loc1.getInternalLocation().equals(loc2.getInternalLocation());
   }

   protected final void setSafe(DynamicObject store, Object value, boolean guard, boolean init) {
      try {
         this.set(store, value, guard, init);
      } catch (IncompatibleLocationException var6) {
         throw shouldNotHappen(var6);
      }
   }

   protected final void setIntSafe(DynamicObject store, int value, boolean guard, boolean init) {
      try {
         this.setInt(store, value, guard, init);
      } catch (IncompatibleLocationException var6) {
         throw shouldNotHappen(var6);
      }
   }

   protected final void setLongSafe(DynamicObject store, long value, boolean guard, boolean init) {
      try {
         this.setLong(store, value, guard, init);
      } catch (IncompatibleLocationException var7) {
         throw shouldNotHappen(var7);
      }
   }

   protected final void setDoubleSafe(DynamicObject store, double value, boolean guard, boolean init) {
      try {
         this.setDouble(store, value, guard, init);
      } catch (IncompatibleLocationException var7) {
         throw shouldNotHappen(var7);
      }
   }

   protected boolean isIntLocation() {
      return false;
   }

   protected boolean isLongLocation() {
      return false;
   }

   protected boolean isDoubleLocation() {
      return false;
   }

   protected boolean isImplicitCastIntToLong() {
      return false;
   }

   protected boolean isImplicitCastIntToDouble() {
      return false;
   }

   protected boolean isObjectLocation() {
      return false;
   }

   static boolean expectBoolean(Object value) throws UnexpectedResultException {
      if (value instanceof Boolean) {
         return (Boolean)value;
      } else {
         throw new UnexpectedResultException(value);
      }
   }

   static int expectInteger(Object value) throws UnexpectedResultException {
      if (value instanceof Integer) {
         return (Integer)value;
      } else {
         throw new UnexpectedResultException(value);
      }
   }

   static double expectDouble(Object value) throws UnexpectedResultException {
      if (value instanceof Double) {
         return (Double)value;
      } else {
         throw new UnexpectedResultException(value);
      }
   }

   static long expectLong(Object value) throws UnexpectedResultException {
      if (value instanceof Long) {
         return (Long)value;
      } else {
         throw new UnexpectedResultException(value);
      }
   }

   public Class<?> getType() {
      return null;
   }

   protected void clear(DynamicObject store) {
   }

   @Override
   public Assumption getFinalAssumption() {
      return Assumption.NEVER_VALID;
   }

   protected static RuntimeException shouldNotHappen(Exception e) {
      CompilerDirectives.transferToInterpreterAndInvalidate();
      throw new IllegalStateException(e);
   }

   public interface InternalLongLocation extends LongLocation {
      void setLongInternal(DynamicObject store, long value);

      String getWhereString();
   }

   public interface LocationVisitor {
      void visitObjectField(int index, int count);

      void visitObjectArray(int index, int count);

      void visitPrimitiveField(int index, int count);

      void visitPrimitiveArray(int index, int count);
   }
}
