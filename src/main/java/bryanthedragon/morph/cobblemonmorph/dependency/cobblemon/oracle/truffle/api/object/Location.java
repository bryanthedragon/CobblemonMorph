package com.oracle.truffle.api.object;

import com.oracle.truffle.api.Assumption;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.nodes.UnexpectedResultException;

public abstract class Location {
   protected Location() {
   }

   @Deprecated(since = "22.2")
   protected static IncompatibleLocationException incompatibleLocation() throws IncompatibleLocationException {
      CompilerDirectives.transferToInterpreterAndInvalidate();
      throw IncompatibleLocationException.instance();
   }

   @Deprecated(since = "22.2")
   protected static FinalLocationException finalLocation() throws FinalLocationException {
      CompilerDirectives.transferToInterpreterAndInvalidate();
      throw FinalLocationException.instance();
   }

   @Deprecated(since = "22.2")
   public final Object get(DynamicObject store, Shape shape) {
      return this.get(store, store.getShape() == shape);
   }

   @Deprecated(since = "22.2")
   public Object get(DynamicObject store, boolean condition) {
      return this.getInternal(store);
   }

   @Deprecated(since = "22.2")
   public final Object get(DynamicObject store) {
      return this.get(store, false);
   }

   protected int getInt(DynamicObject store, boolean guard) throws UnexpectedResultException {
      throw CompilerDirectives.shouldNotReachHere();
   }

   protected long getLong(DynamicObject store, boolean guard) throws UnexpectedResultException {
      throw CompilerDirectives.shouldNotReachHere();
   }

   protected double getDouble(DynamicObject store, boolean guard) throws UnexpectedResultException {
      throw CompilerDirectives.shouldNotReachHere();
   }

   @Deprecated(since = "22.2")
   public void set(DynamicObject store, Object value, Shape shape) throws IncompatibleLocationException, FinalLocationException {
      this.setInternal(store, value);
   }

   @Deprecated(since = "22.2")
   public void set(DynamicObject store, Object value, Shape oldShape, Shape newShape) throws IncompatibleLocationException {
      throw incompatibleLocation();
   }

   @Deprecated(since = "22.2")
   public final void set(DynamicObject store, Object value) throws IncompatibleLocationException, FinalLocationException {
      this.set(store, value, null);
   }

   @Deprecated(since = "22.2")
   protected abstract Object getInternal(DynamicObject store);

   @Deprecated(since = "22.2")
   protected abstract void setInternal(DynamicObject store, Object value) throws IncompatibleLocationException;

   @Deprecated(since = "22.2")
   public boolean canSet(DynamicObject store, Object value) {
      return this.canStore(value);
   }

   @Deprecated(since = "22.2")
   public boolean canSet(Object value) {
      return this.canStore(value);
   }

   public boolean canStore(Object value) {
      return true;
   }

   public boolean isFinal() {
      return false;
   }

   public boolean isConstant() {
      return false;
   }

   @Override
   public abstract int hashCode();

   @Override
   public abstract boolean equals(Object obj);

   @Deprecated(since = "22.2")
   public boolean isDeclared() {
      return false;
   }

   public boolean isValue() {
      return false;
   }

   public boolean isAssumedFinal() {
      return false;
   }

   public Assumption getFinalAssumption() {
      return Assumption.NEVER_VALID;
   }

   @Deprecated(since = "22.2")
   protected static boolean checkShape(DynamicObject store, Shape shape) {
      return store.getShape() == shape;
   }

   public boolean isPrimitive() {
      return this instanceof DoubleLocation || this instanceof IntLocation || this instanceof LongLocation || this instanceof BooleanLocation;
   }

   public Object getConstantValue() {
      return this.isConstant() ? this.get(null) : null;
   }
}
