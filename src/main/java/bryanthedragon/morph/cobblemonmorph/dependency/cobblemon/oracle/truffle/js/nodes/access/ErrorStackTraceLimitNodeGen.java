
package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.js.nodes.access.ErrorStackTraceLimitNode;

@GeneratedBy(value=ErrorStackTraceLimitNode.class)
public final class ErrorStackTraceLimitNodeGen
extends ErrorStackTraceLimitNode
implements Introspection.Provider {
    private ErrorStackTraceLimitNodeGen() {
    }

    @Override
    public int executeInt() {
        return this.doInt();
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
        s[0] = "doInt";
        s[1] = (byte)1;
        data[1] = s;
        return Introspection.Provider.create(data);
    }

    public static ErrorStackTraceLimitNode create() {
        return new ErrorStackTraceLimitNodeGen();
    }
}

