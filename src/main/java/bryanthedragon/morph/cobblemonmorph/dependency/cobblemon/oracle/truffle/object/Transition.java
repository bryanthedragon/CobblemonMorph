package com.oracle.truffle.object;

import com.oracle.truffle.api.object.Property;
import java.util.Objects;

public abstract class Transition {
   protected Transition() {
   }

   @Override
   public int hashCode() {
      return 1;
   }

   @Override
   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else {
         return obj == null ? false : this.getClass() == obj.getClass();
      }
   }

   public abstract boolean isDirect();

   protected boolean hasConstantLocation() {
      return false;
   }

   public abstract static class AbstractReplacePropertyTransition extends Transition.PropertyTransition {
      private final Property after;

      public AbstractReplacePropertyTransition(Property before, Property after) {
         super(before);
         this.after = after;
      }

      public Property getPropertyBefore() {
         return this.getProperty();
      }

      public Property getPropertyAfter() {
         return this.after;
      }

      @Override
      public boolean equals(Object obj) {
         if (!super.equals(obj)) {
            return false;
         } else {
            Transition.AbstractReplacePropertyTransition other = (Transition.AbstractReplacePropertyTransition)obj;
            return !Objects.equals(this.property, other.property) ? false : Objects.equals(this.after, other.after);
         }
      }

      @Override
      public int hashCode() {
         int prime = 31;
         int result = super.hashCode();
         result = 31 * result + this.property.hashCode();
         return 31 * result + this.after.hashCode();
      }

      @Override
      public String toString() {
         return String.format("replace(%s,%s)", this.getPropertyBefore(), this.getPropertyAfter());
      }

      @Override
      protected boolean hasConstantLocation() {
         return this.getPropertyBefore().getLocation().isConstant() || this.getPropertyAfter().getLocation().isConstant();
      }
   }

   public static final class AddPropertyTransition extends Transition.TypedPropertyTransition {
      public AddPropertyTransition(Property property, Object locationOrType) {
         super(property, locationOrType);
      }

      public AddPropertyTransition(Object key, int flags, Object locationType) {
         super(key, flags, locationType);
      }

      @Override
      public boolean isDirect() {
         return true;
      }

      @Override
      public String toString() {
         return String.format("add(%s)", this.propertyToString());
      }
   }

   public static final class DirectReplacePropertyTransition extends Transition.AbstractReplacePropertyTransition {
      public DirectReplacePropertyTransition(Property before, Property after) {
         super(before, after);
      }

      @Override
      public boolean isDirect() {
         return true;
      }
   }

   public static final class IndirectReplacePropertyTransition extends Transition.AbstractReplacePropertyTransition {
      public IndirectReplacePropertyTransition(Property before, Property after) {
         super(before, after);
      }

      @Override
      public boolean isDirect() {
         return false;
      }
   }

   static final class ObjectFlagsTransition extends Transition {
      private final int objectFlags;

      ObjectFlagsTransition(int newFlags) {
         this.objectFlags = newFlags;
      }

      public int getObjectFlags() {
         return this.objectFlags;
      }

      @Override
      public boolean equals(Object other) {
         return super.equals(other) && this.objectFlags == ((Transition.ObjectFlagsTransition)other).objectFlags;
      }

      @Override
      public int hashCode() {
         int prime = 31;
         int result = super.hashCode();
         return 31 * result + this.objectFlags;
      }

      @Override
      public boolean isDirect() {
         return true;
      }

      @Override
      public String toString() {
         return String.format("objectFlags(%s)", this.getObjectFlags());
      }
   }

   public static final class ObjectTypeTransition extends Transition {
      private final Object objectType;

      public ObjectTypeTransition(Object objectType) {
         this.objectType = objectType;
      }

      public Object getObjectType() {
         return this.objectType;
      }

      @Override
      public boolean equals(Object other) {
         return super.equals(other) && Objects.equals(this.objectType, ((Transition.ObjectTypeTransition)other).objectType);
      }

      @Override
      public int hashCode() {
         int prime = 31;
         int result = super.hashCode();
         return 31 * result + (this.objectType == null ? 0 : this.objectType.hashCode());
      }

      @Override
      public boolean isDirect() {
         return true;
      }

      @Override
      public String toString() {
         return String.format("objectType(%s)", this.getObjectType());
      }
   }

   public abstract static class PropertyTransition extends Transition {
      protected final Property property;
      protected final Object key;
      protected final int flags;

      protected PropertyTransition(Property property) {
         this.property = property;
         this.key = property.getKey();
         this.flags = property.getFlags();
      }

      protected PropertyTransition(Object key, int flags) {
         this.property = null;
         this.key = key;
         this.flags = flags;
      }

      @Override
      public int hashCode() {
         int prime = 31;
         int result = super.hashCode();
         result = 31 * result + (this.key == null ? 0 : this.key.hashCode());
         return 31 * result + this.flags;
      }

      @Override
      public boolean equals(Object obj) {
         if (!super.equals(obj)) {
            return false;
         } else {
            Transition.PropertyTransition other = (Transition.PropertyTransition)obj;
            return !Objects.equals(this.key, other.key) ? false : this.flags == other.flags;
         }
      }

      public Property getProperty() {
         return Objects.requireNonNull(this.property);
      }

      public Object getPropertyKey() {
         return this.key;
      }

      public int getPropertyFlags() {
         return this.flags;
      }

      @Override
      protected boolean hasConstantLocation() {
         return this.getProperty().getLocation().isConstant();
      }
   }

   public static final class RemovePropertyTransition extends Transition.TypedPropertyTransition {
      private final boolean direct;

      public RemovePropertyTransition(Property property, Object locationOrType, boolean direct) {
         super(property, locationOrType);
         this.direct = direct;
      }

      @Override
      public boolean isDirect() {
         return this.direct;
      }

      @Override
      public String toString() {
         return String.format("remove(%s)", this.propertyToString());
      }
   }

   public static final class ShareShapeTransition extends Transition {
      @Override
      public boolean isDirect() {
         return true;
      }
   }

   protected abstract static class TypedPropertyTransition extends Transition.PropertyTransition {
      private final Object locationOrType;

      protected TypedPropertyTransition(Property property, Object locationOrType) {
         super(property);
         this.locationOrType = locationOrType;
      }

      protected TypedPropertyTransition(Object key, int flags, Object locationType) {
         super(key, flags);
         this.locationOrType = locationType;
      }

      @Override
      public int hashCode() {
         int prime = 31;
         int result = super.hashCode();
         return 31 * result + (this.locationOrType == null ? 0 : this.locationOrType.hashCode());
      }

      @Override
      public boolean equals(Object obj) {
         if (!super.equals(obj)) {
            return false;
         } else {
            Transition.TypedPropertyTransition other = (Transition.TypedPropertyTransition)obj;
            return Objects.equals(this.locationOrType, other.locationOrType);
         }
      }

      public Object getLocationOrType() {
         return this.locationOrType;
      }

      protected final String propertyToString() {
         return "\"" + this.key + "\":" + this.locationOrType + (this.flags == 0 ? "" : "%" + this.flags);
      }
   }
}
