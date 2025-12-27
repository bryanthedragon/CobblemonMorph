package com.oracle.truffle.api.object;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.nodes.UnexpectedResultException;

public final class PropertyGetter {
   private final Shape expectedShape;
   private final Property property;
   private final Location location;

   PropertyGetter(Shape expectedShape, Property property) {
      this.expectedShape = expectedShape;
      this.property = property;
      this.location = property.getLocation();
   }

   public boolean accepts(DynamicObject receiver) {
      return receiver.getShape() == this.expectedShape;
   }

   public Object get(DynamicObject receiver) {
      boolean guard = this.accepts(receiver);
      if (guard) {
         return this.location.get(receiver, guard);
      } else {
         throw illegalArgumentException();
      }
   }

   public int getInt(DynamicObject receiver) throws UnexpectedResultException {
      boolean guard = this.accepts(receiver);
      if (guard) {
         return this.location.getInt(receiver, guard);
      } else {
         throw illegalArgumentException();
      }
   }

   public long getLong(DynamicObject receiver) throws UnexpectedResultException {
      boolean guard = this.accepts(receiver);
      if (guard) {
         return this.location.getLong(receiver, guard);
      } else {
         throw illegalArgumentException();
      }
   }

   public double getDouble(DynamicObject receiver) throws UnexpectedResultException {
      boolean guard = this.accepts(receiver);
      if (guard) {
         return this.location.getDouble(receiver, guard);
      } else {
         throw illegalArgumentException();
      }
   }

   public Object getKey() {
      return this.property.getKey();
   }

   public int getFlags() {
      return this.property.getFlags();
   }

   private static IllegalArgumentException illegalArgumentException() {
      CompilerDirectives.transferToInterpreterAndInvalidate();
      throw new IllegalArgumentException("Receiver not supported.");
   }
}
