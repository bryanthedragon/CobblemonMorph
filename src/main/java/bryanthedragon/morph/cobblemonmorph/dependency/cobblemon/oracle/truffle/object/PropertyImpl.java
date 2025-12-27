package com.oracle.truffle.object;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.object.DynamicObject;
import com.oracle.truffle.api.object.HiddenKey;
import com.oracle.truffle.api.object.IncompatibleLocationException;
import com.oracle.truffle.api.object.Location;
import com.oracle.truffle.api.object.Property;
import com.oracle.truffle.api.object.Shape;
import java.util.Objects;

public final class PropertyImpl extends Property {
   private final Object key;
   private final Location location;
   private final int flags;

   PropertyImpl(Object key, Location location, int flags) {
      CompilerAsserts.neverPartOfCompilation();
      this.key = Objects.requireNonNull(key);
      this.location = Objects.requireNonNull(location);
      this.flags = flags;
   }

   @Override
   public Object getKey() {
      return this.key;
   }

   @Override
   public int getFlags() {
      return this.flags;
   }

   public Property relocate(Location newLocation) {
      return !this.getLocation().equals(newLocation) ? new PropertyImpl(this.key, newLocation, this.flags) : this;
   }

   @Override
   public Object get(DynamicObject store, Shape shape) {
      return this.getLocation().get(store, shape);
   }

   @Override
   public Object get(DynamicObject store, boolean condition) {
      return this.getLocation().get(store, condition);
   }

   private static boolean verifyShapeParameter(DynamicObject store, Shape shape) {
      assert shape == null || store.getShape() == shape : "wrong shape";

      return true;
   }

   @Override
   public void set(DynamicObject store, Object value, Shape shape) throws IncompatibleLocationException {
      assert verifyShapeParameter(store, shape);

      ((LocationImpl)this.getLocation()).set(store, value, shape);
   }

   @Override
   public void setSafe(DynamicObject store, Object value, Shape shape) {
      assert verifyShapeParameter(store, shape);

      try {
         ((LocationImpl)this.getLocation()).set(store, value, shape);
      } catch (IncompatibleLocationException var5) {
         throw new IllegalStateException();
      }
   }

   @Override
   public void setGeneric(DynamicObject store, Object value, Shape shape) {
      assert verifyShapeParameter(store, shape);

      try {
         this.set(store, value, shape);
      } catch (IncompatibleLocationException var5) {
         this.setSlowCase(store, value);
      }
   }

   @Override
   public void setSafe(DynamicObject store, Object value, Shape oldShape, Shape newShape) {
      assert verifyShapeParameter(store, oldShape);

      try {
         ((LocationImpl)this.getLocation()).set(store, value, oldShape, newShape);
      } catch (IncompatibleLocationException var6) {
         throw new IllegalStateException();
      }
   }

   @Override
   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (obj == null) {
         return false;
      } else if (this.getClass() != obj.getClass()) {
         return false;
      } else {
         PropertyImpl other = (PropertyImpl)obj;
         return (this.key == other.key || this.key.equals(other.key))
            && this.flags == other.flags
            && (this.location == other.location || this.location.equals(other.location));
      }
   }

   public boolean isSame(Property obj) {
      if (this == obj) {
         return true;
      } else if (obj == null) {
         return false;
      } else if (this.getClass() != obj.getClass()) {
         return false;
      } else {
         PropertyImpl other = (PropertyImpl)obj;
         return this.key.equals(other.key) && this.flags == other.flags;
      }
   }

   @Override
   public int hashCode() {
      int prime = 31;
      int result = 1;
      result = 31 * result + this.key.hashCode();
      result = 31 * result + this.location.hashCode();
      return 31 * result + this.flags;
   }

   @Override
   public String toString() {
      return "\"" + this.key + "\":" + this.location + (this.flags == 0 ? "" : "%" + this.flags);
   }

   @Override
   public Location getLocation() {
      return this.location;
   }

   private void setSlowCase(DynamicObject store, Object value) {
      ShapeImpl oldShape = (ShapeImpl)store.getShape();
      oldShape.getLayoutStrategy().propertySetFallback(this, store, value, oldShape);
   }

   @Override
   public boolean isHidden() {
      return this.key instanceof HiddenKey;
   }

   public Property copyWithFlags(int newFlags) {
      return new PropertyImpl(this.key, this.location, newFlags);
   }
}
