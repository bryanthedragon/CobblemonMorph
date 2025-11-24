
package com.oracle.truffle.object;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.object.DynamicObject;
import com.oracle.truffle.api.object.HiddenKey;
import com.oracle.truffle.api.object.IncompatibleLocationException;
import com.oracle.truffle.api.object.Location;
import com.oracle.truffle.api.object.Property;
import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.object.LocationImpl;
import com.oracle.truffle.object.ShapeImpl;
import java.util.Objects;

public final class PropertyImpl
extends Property {
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
        if (!this.getLocation().equals(newLocation)) {
            return new PropertyImpl(this.key, newLocation, this.flags);
        }
        return this;
    }

    @Override
    public Object get(DynamicObject store, Shape shape) {
        return this.getLocation().get(store, shape);
    }

    @Override
    public Object get(DynamicObject store, boolean condition2) {
        return this.getLocation().get(store, condition2);
    }

    private static boolean verifyShapeParameter(DynamicObject store, Shape shape) {
        assert (shape == null || store.getShape() == shape) : "wrong shape";
        return true;
    }

    @Override
    public void set(DynamicObject store, Object value2, Shape shape) throws IncompatibleLocationException {
        assert (PropertyImpl.verifyShapeParameter(store, shape));
        ((LocationImpl)this.getLocation()).set(store, value2, shape);
    }

    @Override
    public void setSafe(DynamicObject store, Object value2, Shape shape) {
        assert (PropertyImpl.verifyShapeParameter(store, shape));
        try {
            ((LocationImpl)this.getLocation()).set(store, value2, shape);
        }
        catch (IncompatibleLocationException ex) {
            throw new IllegalStateException();
        }
    }

    @Override
    public void setGeneric(DynamicObject store, Object value2, Shape shape) {
        assert (PropertyImpl.verifyShapeParameter(store, shape));
        try {
            this.set(store, value2, shape);
        }
        catch (IncompatibleLocationException ex) {
            this.setSlowCase(store, value2);
        }
    }

    @Override
    public void setSafe(DynamicObject store, Object value2, Shape oldShape, Shape newShape) {
        assert (PropertyImpl.verifyShapeParameter(store, oldShape));
        try {
            ((LocationImpl)this.getLocation()).set(store, value2, oldShape, newShape);
        }
        catch (IncompatibleLocationException ex) {
            throw new IllegalStateException();
        }
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        PropertyImpl other = (PropertyImpl)obj;
        return !(this.key != other.key && !this.key.equals(other.key) || this.flags != other.flags || this.location != other.location && !this.location.equals(other.location));
    }

    public boolean isSame(Property obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        PropertyImpl other = (PropertyImpl)obj;
        return this.key.equals(other.key) && this.flags == other.flags;
    }

    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = 31 * result + this.key.hashCode();
        result = 31 * result + this.location.hashCode();
        result = 31 * result + this.flags;
        return result;
    }

    public String toString() {
        return "\"" + this.key + "\":" + this.location + (String)(this.flags == 0 ? "" : "%" + this.flags);
    }

    @Override
    public Location getLocation() {
        return this.location;
    }

    private void setSlowCase(DynamicObject store, Object value2) {
        ShapeImpl oldShape = (ShapeImpl)store.getShape();
        oldShape.getLayoutStrategy().propertySetFallback(this, store, value2, oldShape);
    }

    @Override
    public boolean isHidden() {
        return this.key instanceof HiddenKey;
    }

    public Property copyWithFlags(int newFlags) {
        return new PropertyImpl(this.key, this.location, newFlags);
    }
}

