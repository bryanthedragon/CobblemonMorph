
package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.access.JSProxyCallNode;
import com.oracle.truffle.js.nodes.unary.IsCallableNode;
import com.oracle.truffle.js.nodes.unary.IsConstructorNode;
import com.oracle.truffle.js.runtime.JSContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=JSProxyCallNode.class)
public final class JSProxyCallNodeGen
extends JSProxyCallNode
implements Introspection.Provider {
    @CompilerDirectives.CompilationFinal
    private volatile int state_0_;
    @Node.Child
    private IsCallableNode call_isCallable_;
    @Node.Child
    private IsConstructorNode construct_isConstructor_;

    private JSProxyCallNodeGen(JSContext context, boolean isNew, boolean isNewTarget) {
        super(context, isNew, isNewTarget);
    }

    @Override
    public Object execute(Object[] arg0Value) {
        int state_0 = this.state_0_;
        if (state_0 != 0) {
            if ((state_0 & 1) != 0) {
                assert (!this.isNew);
                assert (!this.isNewTarget);
                return this.doCall(arg0Value, this.call_isCallable_);
            }
            if ((state_0 & 2) != 0) {
                assert (this.isNew || this.isNewTarget);
                return this.doConstruct(arg0Value, this.construct_isConstructor_);
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(arg0Value);
    }

    private Object executeAndSpecialize(Object[] arg0Value) {
        Lock lock = this.getLock();
        boolean hasLock = true;
        lock.lock();
        try {
            int state_0 = this.state_0_;
            if (!this.isNew && !this.isNewTarget) {
                this.call_isCallable_ = super.insert(IsCallableNode.create());
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                Object object = this.doCall(arg0Value, this.call_isCallable_);
                return object;
            }
            if (this.isNew || this.isNewTarget) {
                this.construct_isConstructor_ = super.insert(IsConstructorNode.create());
                this.state_0_ = state_0 |= 2;
                lock.unlock();
                hasLock = false;
                Object object = this.doConstruct(arg0Value, this.construct_isConstructor_);
                return object;
            }
            throw new UnsupportedSpecializationException(this, new Node[]{null}, arg0Value);
        }
        finally {
            if (hasLock) {
                lock.unlock();
            }
        }
    }

    @Override
    public Introspection getIntrospectionData() {
        ArrayList<List<JavaScriptBaseNode>> cached;
        Object[] data = new Object[3];
        data[0] = 0;
        int state_0 = this.state_0_;
        Object[] s = new Object[3];
        s[0] = "doCall";
        if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList<List<JavaScriptBaseNode>>();
            cached.add(Arrays.asList(this.call_isCallable_));
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[1] = s;
        s = new Object[3];
        s[0] = "doConstruct";
        if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList();
            cached.add(Arrays.asList(this.construct_isConstructor_));
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[2] = s;
        return Introspection.Provider.create(data);
    }

    public static JSProxyCallNode create(JSContext context, boolean isNew, boolean isNewTarget) {
        return new JSProxyCallNodeGen(context, isNew, isNewTarget);
    }
}

