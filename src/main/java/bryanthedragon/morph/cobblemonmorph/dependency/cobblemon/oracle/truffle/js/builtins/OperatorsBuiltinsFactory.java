/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.invoke.VarHandle
 */
package com.oracle.truffle.js.builtins;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.library.LibraryFactory;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.object.DynamicObjectLibrary;
import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.js.builtins.OperatorsBuiltins;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.builtins.JSOverloadedOperatorsObject;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.OperatorSet;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=OperatorsBuiltins.class)
public final class OperatorsBuiltinsFactory {
    private static final LibraryFactory<DynamicObjectLibrary> DYNAMIC_OBJECT_LIBRARY_ = LibraryFactory.resolve(DynamicObjectLibrary.class);

    @GeneratedBy(value=OperatorsBuiltins.ConstructOperatorSetNode.class)
    public static final class ConstructOperatorSetNodeGen
    extends OperatorsBuiltins.ConstructOperatorSetNode
    implements Introspection.Provider {
        private ConstructOperatorSetNodeGen(JSContext context) {
            super(context);
        }

        @Override
        public OperatorSet execute(Object arg0Value, Object[] arg1Value) {
            return this.construct(arg0Value, arg1Value);
        }

        @Override
        public NodeCost getCost() {
            return NodeCost.MONOMORPHIC;
        }

        @Override
        public Introspection getIntrospectionData() {
            Object[] data = new Object[2];
            data[0] = 0;
            Object[] s = new Object[3];
            s[0] = "construct";
            s[1] = (byte)1;
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static OperatorsBuiltins.ConstructOperatorSetNode create(JSContext context) {
            return new ConstructOperatorSetNodeGen(context);
        }
    }

    @GeneratedBy(value=OperatorsBuiltins.CreateOverloadedOperatorsObjectNode.class)
    public static final class CreateOverloadedOperatorsObjectNodeGen
    extends OperatorsBuiltins.CreateOverloadedOperatorsObjectNode
    implements Introspection.Provider {
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @CompilerDirectives.CompilationFinal
        private CachedProtoData cachedProto_cache;
        @Node.Child
        private DynamicObjectLibrary createWithProto_setProtoNode_;
        @CompilerDirectives.CompilationFinal
        private Shape createWithProto_cachedShape_;
        @CompilerDirectives.CompilationFinal
        private Shape createDefaultProto_cachedShape_;

        private CreateOverloadedOperatorsObjectNodeGen(JSContext context, OperatorSet operatorSet) {
            super(context, operatorSet);
        }

        @Override
        @ExplodeLoop
        protected JSOverloadedOperatorsObject execute(Object arg0Value) {
            int state_0 = this.state_0_;
            if ((state_0 & 1) != 0) {
                assert (!this.getContext().isMultiContext());
                CachedProtoData s0_ = this.cachedProto_cache;
                while (s0_ != null) {
                    if (arg0Value == s0_.cachedPrototype_) {
                        assert (JSGuards.isJSObject(s0_.cachedPrototype_));
                        return this.doCachedProto(arg0Value, s0_.cachedPrototype_, s0_.cachedShape_);
                    }
                    s0_ = s0_.next_;
                }
            }
            if ((state_0 & 2) != 0 && arg0Value instanceof JSObject) {
                JSObject arg0Value_ = (JSObject)arg0Value;
                return this.createWithProto(arg0Value_, this.createWithProto_setProtoNode_, this.createWithProto_cachedShape_);
            }
            if ((state_0 & 4) != 0 && !JSGuards.isJSObject(arg0Value)) {
                return this.createDefaultProto(arg0Value, this.createDefaultProto_cachedShape_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value);
        }

        private JSOverloadedOperatorsObject executeAndSpecialize(Object arg0Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                if (!this.getContext().isMultiContext()) {
                    Object cachedPrototype__;
                    int count0_ = 0;
                    CachedProtoData s0_ = this.cachedProto_cache;
                    if ((state_0 & 1) != 0) {
                        while (s0_ != null) {
                            if (arg0Value == s0_.cachedPrototype_) {
                                assert (JSGuards.isJSObject(s0_.cachedPrototype_));
                                break;
                            }
                            s0_ = s0_.next_;
                            ++count0_;
                        }
                    }
                    if (s0_ == null && JSGuards.isJSObject(cachedPrototype__ = arg0Value) && count0_ < this.getContext().getPropertyCacheLimit()) {
                        s0_ = new CachedProtoData(this.cachedProto_cache);
                        s0_.cachedPrototype_ = cachedPrototype__;
                        s0_.cachedShape_ = this.getProtoChildShape(arg0Value);
                        VarHandle.storeStoreFence();
                        this.cachedProto_cache = s0_;
                        this.state_0_ = state_0 |= 1;
                    }
                    if (s0_ != null) {
                        lock.unlock();
                        hasLock = false;
                        JSOverloadedOperatorsObject jSOverloadedOperatorsObject = this.doCachedProto(arg0Value, s0_.cachedPrototype_, s0_.cachedShape_);
                        return jSOverloadedOperatorsObject;
                    }
                }
                if (arg0Value instanceof JSObject) {
                    JSObject arg0Value_ = (JSObject)arg0Value;
                    this.createWithProto_setProtoNode_ = super.insert(DYNAMIC_OBJECT_LIBRARY_.createDispatched(3));
                    this.createWithProto_cachedShape_ = this.getShapeWithoutProto();
                    this.state_0_ = state_0 |= 2;
                    lock.unlock();
                    hasLock = false;
                    JSOverloadedOperatorsObject jSOverloadedOperatorsObject = this.createWithProto(arg0Value_, this.createWithProto_setProtoNode_, this.createWithProto_cachedShape_);
                    return jSOverloadedOperatorsObject;
                }
                if (!JSGuards.isJSObject(arg0Value)) {
                    this.createDefaultProto_cachedShape_ = this.getShapeWithDefaultProto(this.getRealm());
                    this.state_0_ = state_0 |= 4;
                    lock.unlock();
                    hasLock = false;
                    JSOverloadedOperatorsObject jSOverloadedOperatorsObject = this.createDefaultProto(arg0Value, this.createDefaultProto_cachedShape_);
                    return jSOverloadedOperatorsObject;
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
            CachedProtoData s0_;
            int state_0 = this.state_0_;
            if (state_0 == 0) {
                return NodeCost.UNINITIALIZED;
            }
            if ((state_0 & state_0 - 1) == 0 && ((s0_ = this.cachedProto_cache) == null || s0_.next_ == null)) {
                return NodeCost.MONOMORPHIC;
            }
            return NodeCost.POLYMORPHIC;
        }

        @Override
        public Introspection getIntrospectionData() {
            ArrayList<List<Object>> cached;
            Object[] data = new Object[4];
            data[0] = 0;
            int state_0 = this.state_0_;
            Object[] s = new Object[3];
            s[0] = "doCachedProto";
            if ((state_0 & 1) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList<List<Object>>();
                CachedProtoData s0_ = this.cachedProto_cache;
                while (s0_ != null) {
                    cached.add(Arrays.asList(s0_.cachedPrototype_, s0_.cachedShape_));
                    s0_ = s0_.next_;
                }
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[1] = s;
            s = new Object[3];
            s[0] = "createWithProto";
            if ((state_0 & 2) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList();
                cached.add(Arrays.asList(this.createWithProto_setProtoNode_, this.createWithProto_cachedShape_));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[2] = s;
            s = new Object[3];
            s[0] = "createDefaultProto";
            if ((state_0 & 4) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList();
                cached.add(Arrays.asList(this.createDefaultProto_cachedShape_));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[3] = s;
            return Introspection.Provider.create(data);
        }

        public static OperatorsBuiltins.CreateOverloadedOperatorsObjectNode create(JSContext context, OperatorSet operatorSet) {
            return new CreateOverloadedOperatorsObjectNodeGen(context, operatorSet);
        }

        @GeneratedBy(value=OperatorsBuiltins.CreateOverloadedOperatorsObjectNode.class)
        private static final class CachedProtoData {
            @CompilerDirectives.CompilationFinal
            CachedProtoData next_;
            @CompilerDirectives.CompilationFinal
            Object cachedPrototype_;
            @CompilerDirectives.CompilationFinal
            Shape cachedShape_;

            CachedProtoData(CachedProtoData next_) {
                this.next_ = next_;
            }
        }
    }

    @GeneratedBy(value=OperatorsBuiltins.OperatorsNode.class)
    public static final class OperatorsNodeGen
    extends OperatorsBuiltins.OperatorsNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @CompilerDirectives.CompilationFinal
        private int state_0_;

        private OperatorsNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            super(context, builtin);
            this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
            this.arguments1_ = arguments != null && 1 < arguments.length ? arguments[1] : null;
        }

        @Override
        public JavaScriptNode[] getArguments() {
            return new JavaScriptNode[]{this.arguments0_, this.arguments1_};
        }

        @Override
        public Object execute(VirtualFrame frameValue) {
            int state_0 = this.state_0_;
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            if (state_0 != 0 && arguments1Value_ instanceof Object[]) {
                Object[] arguments1Value__ = (Object[])arguments1Value_;
                return this.doOperators(frameValue, arguments0Value_, arguments1Value__);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(frameValue, arguments0Value_, arguments1Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.execute(frameValue);
        }

        private JSDynamicObject executeAndSpecialize(VirtualFrame frameValue, Object arguments0Value, Object arguments1Value) {
            int state_0 = this.state_0_;
            if (arguments1Value instanceof Object[]) {
                Object[] arguments1Value_ = (Object[])arguments1Value;
                this.state_0_ = state_0 |= 1;
                return this.doOperators(frameValue, arguments0Value, arguments1Value_);
            }
            throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_, this.arguments1_}, arguments0Value, arguments1Value);
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
            s[0] = "doOperators";
            s[1] = state_0 != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static OperatorsBuiltins.OperatorsNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new OperatorsNodeGen(context, builtin, arguments);
        }
    }
}

