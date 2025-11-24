
package org.graalvm.shadowed.org.jcodings;

public final class Ptr {
    public int p;
    public static final Ptr NULL = new Ptr(0);

    public Ptr() {
        this(0);
    }

    public Ptr(int p) {
        this.p = p;
    }
}

