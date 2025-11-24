
package com.oracle.truffle.api.object;

import com.oracle.truffle.api.object.DynamicObject;
import com.oracle.truffle.api.object.FinalLocationException;
import com.oracle.truffle.api.object.IncompatibleLocationException;
import com.oracle.truffle.api.object.Layout;
import com.oracle.truffle.api.object.Location;
import com.oracle.truffle.api.object.Shape;

public abstract class Property {
    @Deprecated(since="22.2")
    protected Property() {
    }

    @Deprecated(since="22.2")
    public static Property create(Object key, Location location, int flags) {
        return Layout.getFactory().createProperty(key, location, flags);
    }

    public abstract Object getKey();

    public abstract int getFlags();

    @Deprecated(since="22.2")
    public abstract Object get(DynamicObject var1, Shape var2);

    @Deprecated(since="22.2")
    public abstract Object get(DynamicObject var1, boolean var2);

    @Deprecated(since="22.2")
    public abstract void set(DynamicObject var1, Object var2, Shape var3) throws IncompatibleLocationException, FinalLocationException;

    @Deprecated(since="22.2")
    public abstract void setGeneric(DynamicObject var1, Object var2, Shape var3);

    @Deprecated(since="22.2")
    public abstract void setSafe(DynamicObject var1, Object var2, Shape var3);

    @Deprecated(since="22.2")
    public abstract void setSafe(DynamicObject var1, Object var2, Shape var3, Shape var4);

    public abstract Location getLocation();

    public abstract boolean isHidden();
}

