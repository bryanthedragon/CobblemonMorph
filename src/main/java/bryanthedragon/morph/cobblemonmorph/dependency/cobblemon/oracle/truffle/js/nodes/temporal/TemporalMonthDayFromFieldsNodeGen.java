
package com.oracle.truffle.js.nodes.temporal;

import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.js.nodes.temporal.TemporalMonthDayFromFieldsNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalPlainMonthDayObject;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;

@GeneratedBy(value=TemporalMonthDayFromFieldsNode.class)
public final class TemporalMonthDayFromFieldsNodeGen
extends TemporalMonthDayFromFieldsNode
implements Introspection.Provider {
    private TemporalMonthDayFromFieldsNodeGen(JSContext ctx) {
        super(ctx);
    }

    @Override
    public JSTemporalPlainMonthDayObject execute(JSDynamicObject arg0Value, JSDynamicObject arg1Value, JSDynamicObject arg2Value) {
        return this.monthDayFromFields(arg0Value, arg1Value, arg2Value);
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
        s[0] = "monthDayFromFields";
        s[1] = (byte)1;
        data[1] = s;
        return Introspection.Provider.create(data);
    }

    public static TemporalMonthDayFromFieldsNode create(JSContext ctx) {
        return new TemporalMonthDayFromFieldsNodeGen(ctx);
    }
}

