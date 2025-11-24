
package org.graalvm.shadowed.org.jcodings;

public final class ObjPtr<T> {
    public T p;
    static final ObjPtr<Void> NULL = new ObjPtr();

    public ObjPtr() {
        this(null);
    }

    public ObjPtr(T p) {
        this.p = p;
    }
}

