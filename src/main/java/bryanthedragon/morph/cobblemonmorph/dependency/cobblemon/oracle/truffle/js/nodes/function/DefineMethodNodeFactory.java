/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.invoke.VarHandle
 */
package com.oracle.truffle.js.nodes.function;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.function.DefineMethodNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.builtins.JSFunctionData;
import com.oracle.truffle.js.runtime.builtins.JSFunctionFactory;
import com.oracle.truffle.js.runtime.builtins.JSFunctionObject;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=DefineMethodNode.class)
public final class DefineMethodNodeFactory {

    @GeneratedBy(value=DefineMethodNode.FunctionCreateNode.class)
    protected static final class FunctionCreateNodeGen
    extends DefineMethodNode.FunctionCreateNode
    implements Introspection.Provider {
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @CompilerDirectives.CompilationFinal
        private volatile int exclude_;
        @CompilerDirectives.CompilationFinal
        private CachedData cached_cache;
        @CompilerDirectives.CompilationFinal
        private JSFunctionFactory multiContext_factory_;

        private FunctionCreateNodeGen(JSContext context, JSFunctionData functionData, int blockScopeSlot) {
            super(context, functionData, blockScopeSlot);
        }

        @Override
        @ExplodeLoop
        public JSFunctionObject executeWithPrototype(VirtualFrame frameValue, Object arg0Value) {
            int state_0 = this.state_0_;
            if ((state_0 & 7) != 0 && arg0Value instanceof JSDynamicObject) {
                JSDynamicObject arg0Value_ = (JSDynamicObject)arg0Value;
                if ((state_0 & 1) != 0) {
                    assert (!this.getContext().isMultiContext());
                    CachedData s0_ = this.cached_cache;
                    while (s0_ != null) {
                        if (arg0Value_ == s0_.cachedPrototype_) {
                            assert (JSGuards.isJSObject(s0_.cachedPrototype_));
                            return this.doCached(frameValue, arg0Value_, s0_.cachedPrototype_, s0_.factory_);
                        }
                        s0_ = s0_.next_;
                    }
                }
                if ((state_0 & 2) != 0) {
                    assert (!this.getContext().isMultiContext());
                    if (JSGuards.isJSObject(arg0Value_)) {
                        return this.doUncached(frameValue, arg0Value_);
                    }
                }
                if ((state_0 & 4) != 0) {
                    assert (this.getContext().isMultiContext());
                    if (JSGuards.isJSObject(arg0Value_)) {
                        return this.doMultiContext(frameValue, arg0Value_, this.multiContext_factory_);
                    }
                }
            }
            if ((state_0 & 8) != 0 && !JSGuards.isJSObject(arg0Value)) {
                return this.doNonObject(arg0Value);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(frameValue, arg0Value);
        }

        private JSFunctionObject executeAndSpecialize(VirtualFrame frameValue, Object arg0Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                int exclude = this.exclude_;
                if (arg0Value instanceof JSDynamicObject) {
                    JSDynamicObject arg0Value_ = (JSDynamicObject)arg0Value;
                    if (exclude == 0 && !this.getContext().isMultiContext()) {
                        JSDynamicObject cachedPrototype__;
                        int count0_ = 0;
                        CachedData s0_ = this.cached_cache;
                        if ((state_0 & 1) != 0) {
                            while (s0_ != null) {
                                if (arg0Value_ == s0_.cachedPrototype_) {
                                    assert (JSGuards.isJSObject(s0_.cachedPrototype_));
                                    break;
                                }
                                s0_ = s0_.next_;
                                ++count0_;
                            }
                        }
                        if (s0_ == null && JSGuards.isJSObject(cachedPrototype__ = arg0Value_) && count0_ < this.getContext().getPropertyCacheLimit()) {
                            s0_ = new CachedData(this.cached_cache);
                            s0_.cachedPrototype_ = cachedPrototype__;
                            s0_.factory_ = this.makeFactory(arg0Value_);
                            VarHandle.storeStoreFence();
                            this.cached_cache = s0_;
                            this.state_0_ = state_0 |= 1;
                        }
                        if (s0_ != null) {
                            lock.unlock();
                            hasLock = false;
                            JSFunctionObject jSFunctionObject = this.doCached(frameValue, arg0Value_, s0_.cachedPrototype_, s0_.factory_);
                            return jSFunctionObject;
                        }
                    }
                    if (!this.getContext().isMultiContext() && JSGuards.isJSObject(arg0Value_)) {
                        this.exclude_ = exclude |= 1;
                        this.cached_cache = null;
                        state_0 &= 0xFFFFFFFE;
                        this.state_0_ = state_0 |= 2;
                        lock.unlock();
                        hasLock = false;
                        JSFunctionObject jSFunctionObject = this.doUncached(frameValue, arg0Value_);
                        return jSFunctionObject;
                    }
                    if (this.getContext().isMultiContext() && JSGuards.isJSObject(arg0Value_)) {
                        this.multiContext_factory_ = this.makeFactoryMultiContext();
                        this.state_0_ = state_0 |= 4;
                        lock.unlock();
                        hasLock = false;
                        JSFunctionObject jSFunctionObject = this.doMultiContext(frameValue, arg0Value_, this.multiContext_factory_);
                        return jSFunctionObject;
                    }
                }
                if (!JSGuards.isJSObject(arg0Value)) {
                    this.state_0_ = state_0 |= 8;
                    lock.unlock();
                    hasLock = false;
                    JSFunctionObject jSFunctionObject = this.doNonObject(arg0Value);
                    return jSFunctionObject;
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
        public NodeCost getCost() {
            CachedData s0_;
            int state_0 = this.state_0_;
            if (state_0 == 0) {
                return NodeCost.UNINITIALIZED;
            }
            if ((state_0 & state_0 - 1) == 0 && ((s0_ = this.cached_cache) == null || s0_.next_ == null)) {
                return NodeCost.MONOMORPHIC;
            }
            return NodeCost.POLYMORPHIC;
        }

        @Override
        public Introspection getIntrospectionData() {
            ArrayList<List<Object>> cached;
            Object[] data = new Object[5];
            data[0] = 0;
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            Object[] s = new Object[3];
            s[0] = "doCached";
            if ((state_0 & 1) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList<List<Object>>();
                CachedData s0_ = this.cached_cache;
                while (s0_ != null) {
                    cached.add(Arrays.asList(s0_.cachedPrototype_, s0_.factory_));
                    s0_ = s0_.next_;
                }
                s[2] = cached;
            } else {
                s[1] = exclude != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0);
            }
            data[1] = s;
            s = new Object[3];
            s[0] = "doUncached";
            s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[2] = s;
            s = new Object[3];
            s[0] = "doMultiContext";
            if ((state_0 & 4) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList();
                cached.add(Arrays.asList(this.multiContext_factory_));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[3] = s;
            s = new Object[3];
            s[0] = "doNonObject";
            s[1] = (state_0 & 8) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[4] = s;
            return Introspection.Provider.create(data);
        }

        public static DefineMethodNode.FunctionCreateNode create(JSContext context, JSFunctionData functionData, int blockScopeSlot) {
            return new FunctionCreateNodeGen(context, functionData, blockScopeSlot);
        }

        @GeneratedBy(value=DefineMethodNode.FunctionCreateNode.class)
        private static final class CachedData {
            @CompilerDirectives.CompilationFinal
            CachedData next_;
            @CompilerDirectives.CompilationFinal
            JSDynamicObject cachedPrototype_;
            @CompilerDirectives.CompilationFinal
            JSFunctionFactory factory_;

            CachedData(CachedData next_) {
                this.next_ = next_;
            }
        }
    }
}

