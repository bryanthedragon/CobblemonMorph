
package com.oracle.truffle.js.builtins.helper;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.builtins.helper.JSONStringifyStringNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;

@GeneratedBy(value=JSONStringifyStringNode.class)
public final class JSONStringifyStringNodeGen
extends JSONStringifyStringNode
implements Introspection.Provider {
    @CompilerDirectives.CompilationFinal
    private int state_0_;

    private JSONStringifyStringNodeGen(JSContext context) {
        super(context);
    }

    @Override
    public Object execute(Object arg0Value, Object arg1Value, JSDynamicObject arg2Value) {
        int state_0 = this.state_0_;
        if (state_0 != 0 && arg1Value instanceof TruffleString) {
            TruffleString arg1Value_ = (TruffleString)arg1Value;
            return this.jsonStrMain(arg0Value, arg1Value_, arg2Value);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value);
    }

    private Object executeAndSpecialize(Object arg0Value, Object arg1Value, JSDynamicObject arg2Value) {
        int state_0 = this.state_0_;
        if (arg1Value instanceof TruffleString) {
            TruffleString arg1Value_ = (TruffleString)arg1Value;
            this.state_0_ = state_0 |= 1;
            return this.jsonStrMain(arg0Value, arg1Value_, arg2Value);
        }
        throw new UnsupportedSpecializationException(this, new Node[]{null, null, null}, arg0Value, arg1Value, arg2Value);
    }

    @Override
    public NodeCost getCost() {
        int state_0 = this.state_0_;
        if (state_0 == 0) {
            return NodeCost.UNINITIALIZED;
        }
        return NodeCost.MONOMORPHIC;
    }

    @Override
    public Introspection getIntrospectionData() {
        Object[] data = new Object[2];
        data[0] = 0;
        int state_0 = this.state_0_;
        Object[] s = new Object[3];
        s[0] = "jsonStrMain";
        s[1] = state_0 != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[1] = s;
        return Introspection.Provider.create(data);
    }

    public static JSONStringifyStringNode create(JSContext context) {
        return new JSONStringifyStringNodeGen(context);
    }
}

