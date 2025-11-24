
package com.oracle.truffle.object;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.object.CoreLocations;
import com.oracle.truffle.object.LocationImpl;

abstract class CoreLocation
extends LocationImpl {
    protected CoreLocation() {
    }

    @Override
    public String toString() {
        String typeString = this instanceof CoreLocations.TypedLocation ? ((CoreLocations.TypedLocation)((Object)this)).getType().getSimpleName() : "Object";
        return typeString + this.getWhereString();
    }

    @Override
    protected final boolean isIntLocation() {
        return this instanceof CoreLocations.IntLocation;
    }

    @Override
    protected final boolean isDoubleLocation() {
        return this instanceof CoreLocations.DoubleLocation;
    }

    @Override
    protected final boolean isLongLocation() {
        return this instanceof CoreLocations.LongLocation;
    }

    @Override
    protected boolean isObjectLocation() {
        return this instanceof CoreLocations.ObjectLocation;
    }

    static boolean valueEquals(Object val1, Object val2) {
        return val1 == val2 || val1 != null && CoreLocation.equalsBoundary(val1, val2);
    }

    @CompilerDirectives.TruffleBoundary
    private static boolean equalsBoundary(Object val1, Object val2) {
        return val1.equals(val2);
    }
}

