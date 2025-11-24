
package com.oracle.truffle.js.builtins.commonjs;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.builtins.commonjs.CommonJSResolveBuiltin;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.runtime.JSContext;

@GeneratedBy(value=CommonJSResolveBuiltin.class)
public final class CommonJSResolveBuiltinNodeGen
extends CommonJSResolveBuiltin
implements Introspection.Provider {
    @Node.Child
    private JavaScriptNode arguments0_;
    @CompilerDirectives.CompilationFinal
    private int state_0_;

    private CommonJSResolveBuiltinNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
        super(context, builtin);
        this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
    }

    @Override
    public JavaScriptNode[] getArguments() {
        return new JavaScriptNode[]{this.arguments0_};
    }

    @Override
    public Object execute(VirtualFrame frameValue) {
        int state_0 = this.state_0_;
        Object arguments0Value_ = this.arguments0_.execute(frameValue);
        if (state_0 != 0 && arguments0Value_ instanceof TruffleString) {
            TruffleString arguments0Value__ = (TruffleString)arguments0Value_;
            return this.resolve(arguments0Value__);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(arguments0Value_);
    }

    @Override
    public void executeVoid(VirtualFrame frameValue) {
        this.execute(frameValue);
    }

    private TruffleString executeAndSpecialize(Object arguments0Value) {
        int state_0 = this.state_0_;
        if (arguments0Value instanceof TruffleString) {
            TruffleString arguments0Value_ = (TruffleString)arguments0Value;
            this.state_0_ = state_0 |= 1;
            return this.resolve(arguments0Value_);
        }
        throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_}, arguments0Value);
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
        s[0] = "resolve";
        s[1] = state_0 != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[1] = s;
        return Introspection.Provider.create(data);
    }

    public static CommonJSResolveBuiltin create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
        return new CommonJSResolveBuiltinNodeGen(context, builtin, arguments);
    }
}

