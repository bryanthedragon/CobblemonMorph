
package com.oracle.truffle.js.builtins.helper;

import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.js.builtins.helper.GCNode;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.runtime.JSContext;

@GeneratedBy(value=GCNode.class)
public final class GCNodeGen
extends GCNode
implements Introspection.Provider {
    private GCNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
        super(context, builtin);
    }

    @Override
    public JavaScriptNode[] getArguments() {
        return new JavaScriptNode[0];
    }

    @Override
    public Object execute(VirtualFrame frameValue) {
        return this.gc();
    }

    @Override
    public void executeVoid(VirtualFrame frameValue) {
        this.execute(frameValue);
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
        s[0] = "gc";
        s[1] = (byte)1;
        data[1] = s;
        return Introspection.Provider.create(data);
    }

    public static GCNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
        return new GCNodeGen(context, builtin, arguments);
    }
}

