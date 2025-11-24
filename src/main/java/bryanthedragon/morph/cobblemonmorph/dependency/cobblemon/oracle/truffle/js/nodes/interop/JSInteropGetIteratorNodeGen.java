/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.invoke.VarHandle
 */
package com.oracle.truffle.js.nodes.interop;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.nodes.DenyReplace;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.js.lang.JavaScriptLanguage;
import com.oracle.truffle.js.nodes.access.PropertyGetNode;
import com.oracle.truffle.js.nodes.function.JSFunctionCallNode;
import com.oracle.truffle.js.nodes.interop.JSInteropCallNode;
import com.oracle.truffle.js.nodes.interop.JSInteropGetIteratorNode;
import com.oracle.truffle.js.nodes.unary.IsCallableNode;
import com.oracle.truffle.js.nodes.unary.IsCallableNodeGen;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Symbol;
import com.oracle.truffle.js.runtime.objects.JSObject;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=JSInteropGetIteratorNode.class)
public final class JSInteropGetIteratorNodeGen
extends JSInteropGetIteratorNode
implements Introspection.Provider {
    private static final Uncached UNCACHED = new Uncached();
    @CompilerDirectives.CompilationFinal
    private volatile int state_0_;
    @Node.Child
    private DefaultData default_cache;

    private JSInteropGetIteratorNodeGen() {
    }

    @Override
    protected Object execute(JSObject arg0Value, JavaScriptLanguage arg1Value, boolean arg2Value) throws UnsupportedMessageException {
        DefaultData s0_;
        int state_0 = this.state_0_;
        if (state_0 != 0 && (s0_ = this.default_cache) != null) {
            return this.doDefault(arg0Value, arg1Value, arg2Value, s0_.iteratorPropertyGetNode_, s0_.isCallableNode_, s0_.callNode_, s0_.nextPropertyGetNode_, s0_.exceptionBranch_);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private Object executeAndSpecialize(JSObject arg0Value, JavaScriptLanguage arg1Value, boolean arg2Value) throws UnsupportedMessageException {
        Lock lock = this.getLock();
        boolean hasLock = true;
        lock.lock();
        try {
            int state_0 = this.state_0_;
            DefaultData s0_ = super.insert(new DefaultData());
            s0_.iteratorPropertyGetNode_ = s0_.insertAccessor(PropertyGetNode.create(Symbol.SYMBOL_ITERATOR, arg1Value.getJSContext()));
            s0_.isCallableNode_ = s0_.insertAccessor(IsCallableNode.create());
            s0_.callNode_ = s0_.insertAccessor(JSFunctionCallNode.createCall());
            s0_.nextPropertyGetNode_ = s0_.insertAccessor(PropertyGetNode.create(JSRuntime.NEXT, arg1Value.getJSContext()));
            s0_.exceptionBranch_ = BranchProfile.create();
            VarHandle.storeStoreFence();
            this.default_cache = s0_;
            this.state_0_ = state_0 |= 1;
            lock.unlock();
            hasLock = false;
            Object object = this.doDefault(arg0Value, arg1Value, arg2Value, s0_.iteratorPropertyGetNode_, s0_.isCallableNode_, s0_.callNode_, s0_.nextPropertyGetNode_, s0_.exceptionBranch_);
            return object;
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
        return NodeCost.MONOMORPHIC;
    }

    @Override
    public Introspection getIntrospectionData() {
        Object[] data = new Object[2];
        data[0] = 0;
        int state_0 = this.state_0_;
        Object[] s = new Object[3];
        s[0] = "doDefault";
        if (state_0 != 0) {
            s[1] = (byte)1;
            ArrayList<List<Cloneable>> cached = new ArrayList<List<Cloneable>>();
            DefaultData s0_ = this.default_cache;
            if (s0_ != null) {
                cached.add(Arrays.asList(s0_.iteratorPropertyGetNode_, s0_.isCallableNode_, s0_.callNode_, s0_.nextPropertyGetNode_, s0_.exceptionBranch_));
            }
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[1] = s;
        return Introspection.Provider.create(data);
    }

    public static JSInteropGetIteratorNode create() {
        return new JSInteropGetIteratorNodeGen();
    }

    public static JSInteropGetIteratorNode getUncached() {
        return UNCACHED;
    }

    @GeneratedBy(value=JSInteropGetIteratorNode.class)
    @DenyReplace
    private static final class Uncached
    extends JSInteropGetIteratorNode {
        private Uncached() {
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        protected Object execute(JSObject arg0Value, JavaScriptLanguage arg1Value, boolean arg2Value) throws UnsupportedMessageException {
            return this.doDefault(arg0Value, arg1Value, arg2Value, JSInteropCallNode.getUncachedProperty(), IsCallableNodeGen.getUncached(), JSFunctionCallNode.getUncachedCall(), JSInteropCallNode.getUncachedProperty(), BranchProfile.getUncached());
        }

        @Override
        public NodeCost getCost() {
            return NodeCost.MEGAMORPHIC;
        }

        @Override
        public boolean isAdoptable() {
            return false;
        }
    }

    @GeneratedBy(value=JSInteropGetIteratorNode.class)
    private static final class DefaultData
    extends Node {
        @Node.Child
        PropertyGetNode iteratorPropertyGetNode_;
        @Node.Child
        IsCallableNode isCallableNode_;
        @Node.Child
        JSFunctionCallNode callNode_;
        @Node.Child
        PropertyGetNode nextPropertyGetNode_;
        @CompilerDirectives.CompilationFinal
        BranchProfile exceptionBranch_;

        DefaultData() {
        }

        @Override
        public NodeCost getCost() {
            return NodeCost.NONE;
        }

        <T extends Node> T insertAccessor(T node) {
            return super.insert(node);
        }
    }
}

