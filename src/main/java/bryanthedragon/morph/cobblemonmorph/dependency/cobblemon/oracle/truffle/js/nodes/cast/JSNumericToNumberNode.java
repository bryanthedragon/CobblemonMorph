
package com.oracle.truffle.js.nodes.cast;

import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.cast.JSNumericToNumberNodeGen;
import com.oracle.truffle.js.runtime.BigInt;

public abstract class JSNumericToNumberNode
extends JavaScriptBaseNode {
    public abstract Number executeNumeric(Object var1);

    public static JSNumericToNumberNode create() {
        return JSNumericToNumberNodeGen.create();
    }

    @Specialization
    protected static Number doBigInt(BigInt value2) {
        return value2.doubleValue();
    }

    @Specialization(guards={"!isBigInt(value)"})
    protected static Number doOther(Number value2) {
        return value2;
    }
}

