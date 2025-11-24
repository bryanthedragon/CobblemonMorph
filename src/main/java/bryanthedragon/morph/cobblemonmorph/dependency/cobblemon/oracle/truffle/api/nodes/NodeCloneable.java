
package com.oracle.truffle.api.nodes;

public abstract class NodeCloneable
implements Cloneable {
    protected NodeCloneable() {
    }

    protected Object clone() {
        try {
            return super.clone();
        }
        catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}

