
package org.graalvm.collections;

public abstract class Equivalence {
    public static final Equivalence DEFAULT = new Equivalence(){

        @Override
        public boolean equals(Object a, Object b) {
            return a.equals(b);
        }

        @Override
        public int hashCode(Object o) {
            return o.hashCode();
        }
    };
    public static final Equivalence IDENTITY = new Equivalence(){

        @Override
        public boolean equals(Object a, Object b) {
            return a == b;
        }

        @Override
        public int hashCode(Object o) {
            return o.hashCode();
        }
    };
    public static final Equivalence IDENTITY_WITH_SYSTEM_HASHCODE = new Equivalence(){

        @Override
        public boolean equals(Object a, Object b) {
            return a == b;
        }

        @Override
        public int hashCode(Object o) {
            return System.identityHashCode(o);
        }
    };

    protected Equivalence() {
    }

    public abstract boolean equals(Object var1, Object var2);

    public abstract int hashCode(Object var1);
}

