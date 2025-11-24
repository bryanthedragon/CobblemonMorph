
package com.oracle.truffle.object;

import com.oracle.truffle.api.object.Property;
import java.util.Objects;

public abstract class Transition {
    protected Transition() {
    }

    public int hashCode() {
        int result = 1;
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        return this.getClass() == obj.getClass();
    }

    public abstract boolean isDirect();

    protected boolean hasConstantLocation() {
        return false;
    }

    static final class ObjectFlagsTransition
    extends Transition {
        private final int objectFlags;

        ObjectFlagsTransition(int newFlags) {
            this.objectFlags = newFlags;
        }

        public int getObjectFlags() {
            return this.objectFlags;
        }

        @Override
        public boolean equals(Object other) {
            return super.equals(other) && this.objectFlags == ((ObjectFlagsTransition)other).objectFlags;
        }

        @Override
        public int hashCode() {
            int prime = 31;
            int result = super.hashCode();
            result = 31 * result + this.objectFlags;
            return result;
        }

        @Override
        public boolean isDirect() {
            return true;
        }

        public String toString() {
            return String.format("objectFlags(%s)", this.getObjectFlags());
        }
    }

    public static final class ShareShapeTransition
    extends Transition {
        @Override
        public boolean isDirect() {
            return true;
        }
    }

    public static final class DirectReplacePropertyTransition
    extends AbstractReplacePropertyTransition {
        public DirectReplacePropertyTransition(Property before, Property after2) {
            super(before, after2);
        }

        @Override
        public boolean isDirect() {
            return true;
        }
    }

    public static final class IndirectReplacePropertyTransition
    extends AbstractReplacePropertyTransition {
        public IndirectReplacePropertyTransition(Property before, Property after2) {
            super(before, after2);
        }

        @Override
        public boolean isDirect() {
            return false;
        }
    }

    public static abstract class AbstractReplacePropertyTransition
    extends PropertyTransition {
        private final Property after;

        public AbstractReplacePropertyTransition(Property before, Property after2) {
            super(before);
            this.after = after2;
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
            }
            AbstractReplacePropertyTransition other = (AbstractReplacePropertyTransition)obj;
            if (!Objects.equals(this.property, other.property)) {
                return false;
            }
            return Objects.equals(this.after, other.after);
        }

        @Override
        public int hashCode() {
            int prime = 31;
            int result = super.hashCode();
            result = 31 * result + this.property.hashCode();
            result = 31 * result + this.after.hashCode();
            return result;
        }

        public String toString() {
            return String.format("replace(%s,%s)", this.getPropertyBefore(), this.getPropertyAfter());
        }

        @Override
        protected boolean hasConstantLocation() {
            return this.getPropertyBefore().getLocation().isConstant() || this.getPropertyAfter().getLocation().isConstant();
        }
    }

    public static final class ObjectTypeTransition
    extends Transition {
        private final Object objectType;

        public ObjectTypeTransition(Object objectType) {
            this.objectType = objectType;
        }

        public Object getObjectType() {
            return this.objectType;
        }

        @Override
        public boolean equals(Object other) {
            return super.equals(other) && Objects.equals(this.objectType, ((ObjectTypeTransition)other).objectType);
        }

        @Override
        public int hashCode() {
            int prime = 31;
            int result = super.hashCode();
            result = 31 * result + (this.objectType == null ? 0 : this.objectType.hashCode());
            return result;
        }

        @Override
        public boolean isDirect() {
            return true;
        }

        public String toString() {
            return String.format("objectType(%s)", this.getObjectType());
        }
    }

    public static final class RemovePropertyTransition
    extends TypedPropertyTransition {
        private final boolean direct;

        public RemovePropertyTransition(Property property, Object locationOrType, boolean direct) {
            super(property, locationOrType);
            this.direct = direct;
        }

        @Override
        public boolean isDirect() {
            return this.direct;
        }

        public String toString() {
            return String.format("remove(%s)", this.propertyToString());
        }
    }

    public static final class AddPropertyTransition
    extends TypedPropertyTransition {
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

        public String toString() {
            return String.format("add(%s)", this.propertyToString());
        }
    }

    protected static abstract class TypedPropertyTransition
    extends PropertyTransition {
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
            result = 31 * result + (this.locationOrType == null ? 0 : this.locationOrType.hashCode());
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (!super.equals(obj)) {
                return false;
            }
            TypedPropertyTransition other = (TypedPropertyTransition)obj;
            return Objects.equals(this.locationOrType, other.locationOrType);
        }

        public Object getLocationOrType() {
            return this.locationOrType;
        }

        protected final String propertyToString() {
            return "\"" + this.key + "\":" + this.locationOrType + (String)(this.flags == 0 ? "" : "%" + this.flags);
        }
    }

    public static abstract class PropertyTransition
    extends Transition {
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
            result = 31 * result + this.flags;
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (!super.equals(obj)) {
                return false;
            }
            PropertyTransition other = (PropertyTransition)obj;
            if (!Objects.equals(this.key, other.key)) {
                return false;
            }
            return this.flags == other.flags;
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
}

