
package com.oracle.truffle.api.object;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.object.DynamicObject;

@Deprecated(since="22.2")
public class ObjectType {
    static final ObjectType DEFAULT = new ObjectType();

    @Deprecated(since="22.2")
    public ObjectType() {
    }

    @Deprecated(since="22.2")
    public boolean equals(DynamicObject object, Object other) {
        return object == other;
    }

    @Deprecated(since="22.2")
    public int hashCode(DynamicObject object) {
        return System.identityHashCode(object);
    }

    @Deprecated(since="22.2")
    @CompilerDirectives.TruffleBoundary
    public String toString(DynamicObject object) {
        return "DynamicObject<" + this.toString() + ">@" + Integer.toHexString(this.hashCode(object));
    }

    @Deprecated(since="22.2")
    public Class<?> dispatch() {
        return null;
    }
}

