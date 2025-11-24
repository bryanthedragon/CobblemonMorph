
package com.oracle.truffle.js.nodes.intl;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.js.nodes.intl.CreateRegExpNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.builtins.JSRegExpObject;

@GeneratedBy(value=CreateRegExpNode.class)
public final class CreateRegExpNodeGen
extends CreateRegExpNode
implements Introspection.Provider {
    @CompilerDirectives.CompilationFinal
    private int state_0_;

    private CreateRegExpNodeGen(JSContext context) {
        super(context);
    }

    @Override
    protected JSRegExpObject execute(Object arg0Value, boolean arg1Value, Object arg2Value, boolean arg3Value) {
        int state_0 = this.state_0_;
        if (state_0 != 0) {
            if ((state_0 & 1) != 0 && !CreateRegExpNode.b(arg3Value)) {
                return this.createWithoutNamedCG(arg0Value, arg1Value, arg2Value, arg3Value);
            }
            if ((state_0 & 2) != 0 && CreateRegExpNode.b(arg3Value)) {
                return this.createWithNamedCG(arg0Value, arg1Value, arg2Value, arg3Value);
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value);
    }

    private JSRegExpObject executeAndSpecialize(Object arg0Value, boolean arg1Value, Object arg2Value, boolean arg3Value) {
        int state_0 = this.state_0_;
        if (!CreateRegExpNode.b(arg3Value)) {
            this.state_0_ = state_0 |= 1;
            return this.createWithoutNamedCG(arg0Value, arg1Value, arg2Value, arg3Value);
        }
        if (CreateRegExpNode.b(arg3Value)) {
            this.state_0_ = state_0 |= 2;
            return this.createWithNamedCG(arg0Value, arg1Value, arg2Value, arg3Value);
        }
        throw new UnsupportedSpecializationException(this, new Node[]{null, null, null, null}, arg0Value, arg1Value, arg2Value, arg3Value);
    }

    @Override
    public NodeCost getCost() {
        int state_0 = this.state_0_;
        if (state_0 == 0) {
            return NodeCost.UNINITIALIZED;
        }
        if ((state_0 & state_0 - 1) == 0) {
            return NodeCost.MONOMORPHIC;
        }
        return NodeCost.POLYMORPHIC;
    }

    @Override
    public Introspection getIntrospectionData() {
        Object[] data = new Object[3];
        data[0] = 0;
        int state_0 = this.state_0_;
        Object[] s = new Object[3];
        s[0] = "createWithoutNamedCG";
        s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[1] = s;
        s = new Object[3];
        s[0] = "createWithNamedCG";
        s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[2] = s;
        return Introspection.Provider.create(data);
    }

    public static CreateRegExpNode create(JSContext context) {
        return new CreateRegExpNodeGen(context);
    }
}

