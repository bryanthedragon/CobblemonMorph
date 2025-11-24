
package com.oracle.truffle.js.nodes.unary;

import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.unary.VoidNode;

@GeneratedBy(value=VoidNode.class)
public final class VoidNodeGen
extends VoidNode
implements Introspection.Provider {
    private VoidNodeGen(JavaScriptNode operand) {
        super(operand);
    }

    @Override
    public Object execute(VirtualFrame frameValue, Object operandNodeValue) {
        return this.doGeneric(operandNodeValue);
    }

    @Override
    public Object execute(VirtualFrame frameValue) {
        Object operandNodeValue_ = this.operandNode.execute(frameValue);
        return this.doGeneric(operandNodeValue_);
    }

    @Override
    public void executeVoid(VirtualFrame frameValue) {
        this.execute(frameValue);
    }

    @Override
    public Introspection getIntrospectionData() {
        Object[] data = new Object[2];
        data[0] = 0;
        Object[] s = new Object[3];
        s[0] = "doGeneric";
        s[1] = (byte)1;
        data[1] = s;
        return Introspection.Provider.create(data);
    }

    public static VoidNode create(JavaScriptNode operand) {
        return new VoidNodeGen(operand);
    }
}

