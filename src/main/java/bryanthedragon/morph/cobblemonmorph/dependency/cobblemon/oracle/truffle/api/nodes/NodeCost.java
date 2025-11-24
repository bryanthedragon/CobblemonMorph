
package com.oracle.truffle.api.nodes;

public enum NodeCost {
    NONE,
    UNINITIALIZED,
    MONOMORPHIC,
    POLYMORPHIC,
    MEGAMORPHIC;


    public boolean isTrivial() {
        return this == NONE || this == UNINITIALIZED;
    }

    public static NodeCost fromCount(int nodeCount) {
        switch (nodeCount) {
            case 0: {
                return UNINITIALIZED;
            }
            case 1: {
                return MONOMORPHIC;
            }
        }
        return POLYMORPHIC;
    }
}

