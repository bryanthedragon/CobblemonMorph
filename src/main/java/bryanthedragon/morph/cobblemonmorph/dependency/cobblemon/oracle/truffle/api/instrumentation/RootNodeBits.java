
package com.oracle.truffle.api.instrumentation;

import com.oracle.truffle.api.instrumentation.InstrumentAccessor;
import com.oracle.truffle.api.nodes.RootNode;

final class RootNodeBits {
    private static final int INITIALIZED = 1;
    private static final int SAME_SOURCE = 2;
    private static final int NO_SOURCE_SECTION = 4;
    private static final int SOURCE_SECTION_HIERARCHICAL = 8;
    private static final int NOT_EXECUTED = 16;
    private static final int ALL = 31;

    RootNodeBits() {
    }

    static boolean wasExecuted(int bits) {
        return bits > 0 && (bits & 0x10) == 0;
    }

    static boolean wasNotExecuted(int bits) {
        return (bits & 0x10) > 0;
    }

    static boolean isSourceSectionsHierachical(int bits) {
        return (bits & 8) > 0;
    }

    static boolean isSameSource(int bits) {
        return (bits & 2) > 0;
    }

    static boolean isNoSourceSection(int bits) {
        return (bits & 4) > 0;
    }

    static int setSourceSectionsUnstructured(int bits) {
        return bits & 0xFFFFFFF7;
    }

    static int setHasDifferentSource(int bits) {
        return bits & 0xFFFFFFFD;
    }

    static int setHasSourceSection(int bits) {
        return bits & 0xFFFFFFFB;
    }

    static int setExecuted(int bits) {
        return bits & 0xFFFFFFEF;
    }

    static int get(RootNode root) {
        return InstrumentAccessor.nodesAccess().getRootNodeBits(root);
    }

    static void set(RootNode root, int bits) {
        InstrumentAccessor.nodesAccess().setRootNodeBits(root, bits);
    }

    static boolean isUninitialized(int bits) {
        return bits == 0;
    }

    static int getAll() {
        return 31;
    }
}

