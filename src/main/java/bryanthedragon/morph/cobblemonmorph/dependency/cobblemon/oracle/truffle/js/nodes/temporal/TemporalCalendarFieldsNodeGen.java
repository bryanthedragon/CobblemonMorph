
package com.oracle.truffle.js.nodes.temporal;

import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.temporal.TemporalCalendarFieldsNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.util.List;

@GeneratedBy(value=TemporalCalendarFieldsNode.class)
public final class TemporalCalendarFieldsNodeGen
extends TemporalCalendarFieldsNode
implements Introspection.Provider {
    private TemporalCalendarFieldsNodeGen(JSContext ctx) {
        super(ctx);
    }

    @Override
    public List<TruffleString> execute(JSDynamicObject arg0Value, List<TruffleString> arg1Value) {
        return this.calendarFields(arg0Value, arg1Value);
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
        s[0] = "calendarFields";
        s[1] = (byte)1;
        data[1] = s;
        return Introspection.Provider.create(data);
    }

    public static TemporalCalendarFieldsNode create(JSContext ctx) {
        return new TemporalCalendarFieldsNodeGen(ctx);
    }
}

