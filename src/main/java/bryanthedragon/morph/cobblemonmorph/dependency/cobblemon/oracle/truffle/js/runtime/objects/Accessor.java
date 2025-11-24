
package com.oracle.truffle.js.runtime.objects;

import com.oracle.truffle.js.runtime.objects.Undefined;
import java.util.Objects;

public final class Accessor {
    private final Object getter;
    private final Object setter;

    public Accessor(Object getter, Object setter) {
        this.getter = getter == null ? Undefined.instance : getter;
        this.setter = setter == null ? Undefined.instance : setter;
    }

    public Object getGetter() {
        return this.getter;
    }

    public Object getSetter() {
        return this.setter;
    }

    public boolean hasGetter() {
        return this.getter != Undefined.instance;
    }

    public boolean hasSetter() {
        return this.setter != Undefined.instance;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Accessor)) {
            return false;
        }
        Accessor other = (Accessor)obj;
        return Objects.equals(this.getter, other.getter) && Objects.equals(this.setter, other.setter);
    }

    public int hashCode() {
        return Objects.hash(this.getter, this.setter);
    }
}

