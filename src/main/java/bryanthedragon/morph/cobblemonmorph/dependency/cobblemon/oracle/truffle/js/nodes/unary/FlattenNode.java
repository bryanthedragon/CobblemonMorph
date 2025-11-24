
package com.oracle.truffle.js.nodes.unary;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Fallback;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.unary.FlattenNodeGen;
import com.oracle.truffle.js.runtime.SafeInteger;
import com.oracle.truffle.js.runtime.Strings;

public abstract class FlattenNode
extends JavaScriptBaseNode {
    FlattenNode() {
    }

    public abstract Object execute(Object var1);

    @Specialization
    protected static TruffleString doLazyString(TruffleString value2, @Cached TruffleString.MaterializeNode materializeNode) {
        return Strings.flatten(materializeNode, value2);
    }

    @Specialization
    protected static double doSafeInteger(SafeInteger value2) {
        return value2.doubleValue();
    }

    @Fallback
    protected static Object doOther(Object value2) {
        return value2;
    }

    public static FlattenNode create() {
        return FlattenNodeGen.create();
    }
}

