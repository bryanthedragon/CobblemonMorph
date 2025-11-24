
package com.oracle.truffle.js.nodes.intl;

import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.intl.GetBooleanOptionNode;
import com.oracle.truffle.js.runtime.JSContext;

@GeneratedBy(value=GetBooleanOptionNode.class)
public final class GetBooleanOptionNodeGen
extends GetBooleanOptionNode
implements Introspection.Provider {
    private GetBooleanOptionNodeGen(JSContext context, TruffleString property, Boolean fallback) {
        super(context, property, fallback);
    }

    @Override
    public Boolean executeValue(Object arg0Value) {
        return this.getOption(arg0Value);
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
        s[0] = "getOption";
        s[1] = (byte)1;
        data[1] = s;
        return Introspection.Provider.create(data);
    }

    public static GetBooleanOptionNode create(JSContext context, TruffleString property, Boolean fallback) {
        return new GetBooleanOptionNodeGen(context, property, fallback);
    }
}

