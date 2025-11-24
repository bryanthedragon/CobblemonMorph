
package com.oracle.truffle.js.nodes.intl;

import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.js.nodes.intl.InitializeNumberFormatNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;

@GeneratedBy(value=InitializeNumberFormatNode.class)
public final class InitializeNumberFormatNodeGen
extends InitializeNumberFormatNode
implements Introspection.Provider {
    private InitializeNumberFormatNodeGen(JSContext context) {
        super(context);
    }

    @Override
    public JSDynamicObject executeInit(JSDynamicObject arg0Value, Object arg1Value, Object arg2Value) {
        return this.initializeNumberFormat(arg0Value, arg1Value, arg2Value);
    }

    @Override
    public NodeCost getCost() {
        return NodeCost.MONOMORPHIC;
    }

    @Override
    public Introspection getIntrospectionData() {
        Object[] data = new Object[2];
        data[0] = 0;
        Object[] s = new Object[3];
        s[0] = "initializeNumberFormat";
        s[1] = (byte)1;
        data[1] = s;
        return Introspection.Provider.create(data);
    }

    public static InitializeNumberFormatNode create(JSContext context) {
        return new InitializeNumberFormatNodeGen(context);
    }
}

