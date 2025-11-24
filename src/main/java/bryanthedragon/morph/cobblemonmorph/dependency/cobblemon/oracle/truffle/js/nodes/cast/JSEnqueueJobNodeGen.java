
package com.oracle.truffle.js.nodes.cast;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.cast.JSEnqueueJobNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.builtins.JSFunctionObject;

@GeneratedBy(value=JSEnqueueJobNode.class)
public final class JSEnqueueJobNodeGen
extends JSEnqueueJobNode
implements Introspection.Provider {
    @Node.Child
    private JavaScriptNode function_;
    @CompilerDirectives.CompilationFinal
    private int state_0_;

    private JSEnqueueJobNodeGen(JSContext context, JavaScriptNode function) {
        super(context);
        this.function_ = function;
    }

    @Override
    JavaScriptNode getFunction() {
        return this.function_;
    }

    @Override
    public Object execute(VirtualFrame frameValue) {
        int state_0 = this.state_0_;
        Object functionValue_ = this.function_.execute(frameValue);
        if (state_0 != 0 && functionValue_ instanceof JSFunctionObject) {
            JSFunctionObject functionValue__ = (JSFunctionObject)functionValue_;
            return this.doOther(functionValue__);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(functionValue_);
    }

    @Override
    public void executeVoid(VirtualFrame frameValue) {
        this.execute(frameValue);
    }

    private Object executeAndSpecialize(Object functionValue) {
        int state_0 = this.state_0_;
        if (functionValue instanceof JSFunctionObject) {
            JSFunctionObject functionValue_ = (JSFunctionObject)functionValue;
            this.state_0_ = state_0 |= 1;
            return this.doOther(functionValue_);
        }
        throw new UnsupportedSpecializationException(this, new Node[]{this.function_}, functionValue);
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
        s[0] = "doOther";
        s[1] = state_0 != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[1] = s;
        return Introspection.Provider.create(data);
    }

    public static JSEnqueueJobNode create(JSContext context, JavaScriptNode function) {
        return new JSEnqueueJobNodeGen(context, function);
    }
}

