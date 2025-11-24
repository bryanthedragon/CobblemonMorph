
package com.oracle.truffle.js.nodes.interop;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.interop.UnknownIdentifierException;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.nodes.DenyReplace;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.access.PropertyGetNode;
import com.oracle.truffle.js.nodes.access.ReadElementNode;
import com.oracle.truffle.js.nodes.function.JSFunctionCallNode;
import com.oracle.truffle.js.nodes.interop.ImportValueNode;
import com.oracle.truffle.js.nodes.interop.JSInteropInvokeNode;
import com.oracle.truffle.js.nodes.unary.IsCallableNode;
import com.oracle.truffle.js.nodes.unary.IsCallableNodeGen;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=JSInteropInvokeNode.class)
public final class JSInteropInvokeNodeGen
extends JSInteropInvokeNode
implements Introspection.Provider {
    private static final Uncached UNCACHED = new Uncached();
    @CompilerDirectives.CompilationFinal
    private volatile int state_0_;
    @CompilerDirectives.CompilationFinal
    private volatile int exclude_;
    @Node.Child
    private IsCallableNode isCallable;
    @Node.Child
    private JSFunctionCallNode call;
    @Node.Child
    private ImportValueNode importValue;
    @CompilerDirectives.CompilationFinal
    private TruffleString cached_cachedName_;
    @Node.Child
    private TruffleString.EqualNode cached_equalNode_;
    @Node.Child
    private PropertyGetNode cached_functionPropertyGetNode_;
    @Node.Child
    private ReadElementNode uncached_readNode_;

    private JSInteropInvokeNodeGen() {
    }

    @Override
    public Object execute(JSDynamicObject arg0Value, TruffleString arg1Value, Object[] arg2Value) throws UnknownIdentifierException, UnsupportedMessageException {
        int state_0 = this.state_0_;
        if (state_0 != 0) {
            if ((state_0 & 1) != 0 && JSGuards.stringEquals(this.cached_equalNode_, this.cached_cachedName_, arg1Value)) {
                return this.doCached(arg0Value, arg1Value, arg2Value, this.cached_cachedName_, this.cached_equalNode_, this.cached_functionPropertyGetNode_, this.isCallable, this.call, this.importValue);
            }
            if ((state_0 & 2) != 0) {
                return this.doUncached(arg0Value, arg1Value, arg2Value, this.uncached_readNode_, this.isCallable, this.call, this.importValue);
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private Object executeAndSpecialize(JSDynamicObject arg0Value, TruffleString arg1Value, Object[] arg2Value) throws UnknownIdentifierException, UnsupportedMessageException {
        Lock lock = this.getLock();
        boolean hasLock = true;
        lock.lock();
        try {
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            if (exclude == 0) {
                boolean Cached_duplicateFound_ = false;
                if ((state_0 & 1) != 0 && JSGuards.stringEquals(this.cached_equalNode_, this.cached_cachedName_, arg1Value)) {
                    Cached_duplicateFound_ = true;
                }
                if (!Cached_duplicateFound_) {
                    TruffleString cached_cachedName__ = arg1Value;
                    TruffleString.EqualNode cached_equalNode__ = super.insert(TruffleString.EqualNode.create());
                    if (JSGuards.stringEquals(cached_equalNode__, cached_cachedName__, arg1Value) && (state_0 & 1) == 0) {
                        this.cached_cachedName_ = cached_cachedName__;
                        this.cached_equalNode_ = super.insert(cached_equalNode__);
                        this.cached_functionPropertyGetNode_ = super.insert(this.createGetProperty(cached_cachedName__));
                        this.isCallable = super.insert(this.isCallable == null ? IsCallableNode.create() : this.isCallable);
                        this.call = super.insert(this.call == null ? JSFunctionCallNode.createCall() : this.call);
                        this.importValue = super.insert(this.importValue == null ? ImportValueNode.create() : this.importValue);
                        this.state_0_ = state_0 |= 1;
                        Cached_duplicateFound_ = true;
                    }
                }
                if (Cached_duplicateFound_) {
                    lock.unlock();
                    hasLock = false;
                    Object object = this.doCached(arg0Value, arg1Value, arg2Value, this.cached_cachedName_, this.cached_equalNode_, this.cached_functionPropertyGetNode_, this.isCallable, this.call, this.importValue);
                    return object;
                }
            }
            this.uncached_readNode_ = super.insert(ReadElementNode.create(this.getLanguage().getJSContext()));
            this.isCallable = super.insert(this.isCallable == null ? IsCallableNode.create() : this.isCallable);
            this.call = super.insert(this.call == null ? JSFunctionCallNode.createCall() : this.call);
            this.importValue = super.insert(this.importValue == null ? ImportValueNode.create() : this.importValue);
            this.exclude_ = exclude |= 1;
            state_0 &= 0xFFFFFFFE;
            this.state_0_ = state_0 |= 2;
            lock.unlock();
            hasLock = false;
            Object object = this.doUncached(arg0Value, arg1Value, arg2Value, this.uncached_readNode_, this.isCallable, this.call, this.importValue);
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
        if ((state_0 & state_0 - 1) == 0) {
            return NodeCost.MONOMORPHIC;
        }
        return NodeCost.POLYMORPHIC;
    }

    @Override
    public Introspection getIntrospectionData() {
        ArrayList<List<Object>> cached;
        Object[] data = new Object[3];
        data[0] = 0;
        int state_0 = this.state_0_;
        int exclude = this.exclude_;
        Object[] s = new Object[3];
        s[0] = "doCached";
        if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList<List<Object>>();
            cached.add(Arrays.asList(this.cached_cachedName_, this.cached_equalNode_, this.cached_functionPropertyGetNode_, this.isCallable, this.call, this.importValue));
            s[2] = cached;
        } else {
            s[1] = exclude != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0);
        }
        data[1] = s;
        s = new Object[3];
        s[0] = "doUncached";
        if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList();
            cached.add(Arrays.asList(this.uncached_readNode_, this.isCallable, this.call, this.importValue));
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[2] = s;
        return Introspection.Provider.create(data);
    }

    public static JSInteropInvokeNode create() {
        return new JSInteropInvokeNodeGen();
    }

    public static JSInteropInvokeNode getUncached() {
        return UNCACHED;
    }

    @GeneratedBy(value=JSInteropInvokeNode.class)
    @DenyReplace
    private static final class Uncached
    extends JSInteropInvokeNode {
        private Uncached() {
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public Object execute(JSDynamicObject arg0Value, TruffleString arg1Value, Object[] arg2Value) throws UnknownIdentifierException, UnsupportedMessageException {
            return this.doUncached(arg0Value, arg1Value, arg2Value, JSInteropInvokeNode.getUncachedRead(), IsCallableNodeGen.getUncached(), JSFunctionCallNode.getUncachedCall(), ImportValueNode.getUncached());
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
}

