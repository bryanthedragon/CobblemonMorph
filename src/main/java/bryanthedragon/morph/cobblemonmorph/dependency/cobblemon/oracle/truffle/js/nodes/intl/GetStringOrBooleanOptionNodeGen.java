
package com.oracle.truffle.js.nodes.intl;

import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.intl.GetStringOrBooleanOptionNode;
import com.oracle.truffle.js.runtime.JSContext;

@GeneratedBy(value=GetStringOrBooleanOptionNode.class)
public final class GetStringOrBooleanOptionNodeGen
extends GetStringOrBooleanOptionNode
implements Introspection.Provider {
    private GetStringOrBooleanOptionNodeGen(JSContext context, TruffleString property, String[] values, Object trueValue, Object falsyValue, Object fallback) {
        super(context, property, values, trueValue, falsyValue, fallback);
    }

    @Override
    public Object executeValue(Object arg0Value) {
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

    public static GetStringOrBooleanOptionNode create(JSContext context, TruffleString property, String[] values, Object trueValue, Object falsyValue, Object fallback) {
        return new GetStringOrBooleanOptionNodeGen(context, property, values, trueValue, falsyValue, fallback);
    }
}

