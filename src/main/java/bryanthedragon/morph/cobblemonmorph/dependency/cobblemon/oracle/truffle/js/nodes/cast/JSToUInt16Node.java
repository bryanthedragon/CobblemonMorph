
package com.oracle.truffle.js.nodes.cast;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.cast.JSToNumberNode;
import com.oracle.truffle.js.nodes.cast.JSToUInt16NodeGen;
import com.oracle.truffle.js.runtime.JSRuntime;

public abstract class JSToUInt16Node
extends JavaScriptBaseNode {
    public static JSToUInt16Node create() {
        return JSToUInt16NodeGen.create();
    }

    public abstract int executeInt(Object var1);

    @Specialization
    protected int doInt(int value2) {
        return JSRuntime.toUInt16(value2);
    }

    @Specialization
    protected int doDouble(double value2, @Cached(value="create()") BranchProfile needPositiveInfinityBranch) {
        if (JSRuntime.isPositiveInfinity(value2)) {
            needPositiveInfinityBranch.enter();
            return 0;
        }
        return JSRuntime.toUInt16((long)value2);
    }

    @Specialization
    protected int doGeneric(Object value2, @Cached(value="create()") JSToNumberNode toNumberNode) {
        return JSRuntime.toUInt16(toNumberNode.executeNumber(value2));
    }
}

