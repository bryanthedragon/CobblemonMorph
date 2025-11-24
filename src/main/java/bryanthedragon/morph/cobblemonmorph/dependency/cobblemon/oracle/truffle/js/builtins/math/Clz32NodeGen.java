
package com.oracle.truffle.js.builtins.math;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.js.builtins.math.Clz32Node;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.cast.JSToUInt32Node;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.runtime.JSContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=Clz32Node.class)
public final class Clz32NodeGen
extends Clz32Node
implements Introspection.Provider {
    @Node.Child
    private JavaScriptNode arguments0_;
    @CompilerDirectives.CompilationFinal
    private volatile int state_0_;
    @Node.Child
    private JSToUInt32Node clz321_toUInt32Node_;

    private Clz32NodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
        if ((state_0 & 2) == 0 && state_0 != 0) {
            return this.execute_int0(state_0, frameValue);
        }
        return this.execute_generic1(state_0, frameValue);
    }

    private Object execute_int0(int state_0, VirtualFrame frameValue) {
        int arguments0Value_;
        try {
            arguments0Value_ = this.arguments0_.executeInt(frameValue);
        }
        catch (UnexpectedResultException ex) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(ex.getResult());
        }
        assert ((state_0 & 1) != 0);
        return Clz32Node.clz32(arguments0Value_);
    }

    private Object execute_generic1(int state_0, VirtualFrame frameValue) {
        Object arguments0Value_ = this.arguments0_.execute(frameValue);
        if ((state_0 & 1) != 0 && arguments0Value_ instanceof Integer) {
            int arguments0Value__ = (Integer)arguments0Value_;
            return Clz32Node.clz32(arguments0Value__);
        }
        if ((state_0 & 2) != 0) {
            return this.clz32(arguments0Value_, this.clz321_toUInt32Node_);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(arguments0Value_);
    }

    @Override
    public int executeInt(VirtualFrame frameValue) {
        int state_0 = this.state_0_;
        if ((state_0 & 2) == 0 && state_0 != 0) {
            return this.executeInt_int2(state_0, frameValue);
        }
        return this.executeInt_generic3(state_0, frameValue);
    }

    private int executeInt_int2(int state_0, VirtualFrame frameValue) {
        int arguments0Value_;
        try {
            arguments0Value_ = this.arguments0_.executeInt(frameValue);
        }
        catch (UnexpectedResultException ex) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(ex.getResult());
        }
        assert ((state_0 & 1) != 0);
        return Clz32Node.clz32(arguments0Value_);
    }

    private int executeInt_generic3(int state_0, VirtualFrame frameValue) {
        Object arguments0Value_ = this.arguments0_.execute(frameValue);
        if ((state_0 & 1) != 0 && arguments0Value_ instanceof Integer) {
            int arguments0Value__ = (Integer)arguments0Value_;
            return Clz32Node.clz32(arguments0Value__);
        }
        if ((state_0 & 2) != 0) {
            return this.clz32(arguments0Value_, this.clz321_toUInt32Node_);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(arguments0Value_);
    }

    @Override
    public void executeVoid(VirtualFrame frameValue) {
        this.executeInt(frameValue);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private int executeAndSpecialize(Object arguments0Value) {
        Lock lock = this.getLock();
        boolean hasLock = true;
        lock.lock();
        try {
            int state_0 = this.state_0_;
            if (arguments0Value instanceof Integer) {
                int arguments0Value_ = (Integer)arguments0Value;
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                int n = Clz32Node.clz32(arguments0Value_);
                return n;
            }
            this.clz321_toUInt32Node_ = super.insert(JSToUInt32Node.create());
            this.state_0_ = state_0 |= 2;
            lock.unlock();
            hasLock = false;
            int n = this.clz32(arguments0Value, this.clz321_toUInt32Node_);
            return n;
        }
        finally {
            if (hasLock) {
                lock.unlock();
            }
        }
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
        s[0] = "clz32";
        s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[1] = s;
        s = new Object[3];
        s[0] = "clz32";
        if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
            ArrayList<List<JSToUInt32Node>> cached = new ArrayList<List<JSToUInt32Node>>();
            cached.add(Arrays.asList(this.clz321_toUInt32Node_));
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[2] = s;
        return Introspection.Provider.create(data);
    }

    public static Clz32Node create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
        return new Clz32NodeGen(context, builtin, arguments);
    }
}

