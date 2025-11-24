
package com.oracle.truffle.js.nodes.temporal;

import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.js.nodes.temporal.TemporalCalendarDateFromFieldsNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalPlainDateObject;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;

@GeneratedBy(value=TemporalCalendarDateFromFieldsNode.class)
public final class TemporalCalendarDateFromFieldsNodeGen
extends TemporalCalendarDateFromFieldsNode
implements Introspection.Provider {
    private TemporalCalendarDateFromFieldsNodeGen(JSContext ctx) {
        super(ctx);
    }

    @Override
    public JSTemporalPlainDateObject execute(JSDynamicObject arg0Value, JSDynamicObject arg1Value, Object arg2Value) {
        return this.toTemporalDate(arg0Value, arg1Value, arg2Value);
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
        s[0] = "toTemporalDate";
        s[1] = (byte)1;
        data[1] = s;
        return Introspection.Provider.create(data);
    }

    public static TemporalCalendarDateFromFieldsNode create(JSContext ctx) {
        return new TemporalCalendarDateFromFieldsNodeGen(ctx);
    }
}

