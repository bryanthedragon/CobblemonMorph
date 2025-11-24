
package com.oracle.truffle.js.nodes.intl;

import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.js.nodes.intl.InitializeSegmenterNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;

@GeneratedBy(value=InitializeSegmenterNode.class)
public final class InitializeSegmenterNodeGen
extends InitializeSegmenterNode
implements Introspection.Provider {
    private InitializeSegmenterNodeGen(JSContext context) {
        super(context);
    }

    @Override
    public JSDynamicObject executeInit(JSDynamicObject arg0Value, Object arg1Value, Object arg2Value) {
        return this.initializeSegmenter(arg0Value, arg1Value, arg2Value);
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
        s[0] = "initializeSegmenter";
        s[1] = (byte)1;
        data[1] = s;
        return Introspection.Provider.create(data);
    }

    public static InitializeSegmenterNode create(JSContext context) {
        return new InitializeSegmenterNodeGen(context);
    }
}

