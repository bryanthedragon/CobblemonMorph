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
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.profiles.ValueProfile;
import com.oracle.truffle.js.builtins.DataViewPrototypeBuiltins;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.cast.JSToIndexNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.builtins.JSDataView;
import com.oracle.truffle.js.runtime.builtins.JSDataViewObject;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=DataViewPrototypeBuiltins.class)
public final class DataViewPrototypeBuiltinsFactory {

    @GeneratedBy(value=DataViewPrototypeBuiltins.DataViewSetNode.class)
    public static final class DataViewSetNodeGen
    extends DataViewPrototypeBuiltins.DataViewSetNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @Node.Child
        private JavaScriptNode arguments2_;
        @Node.Child
        private JavaScriptNode arguments3_;
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private DataViewData dataView_cache;

        private DataViewSetNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            super(context, builtin);
            this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
            this.arguments1_ = arguments != null && 1 < arguments.length ? arguments[1] : null;
            this.arguments2_ = arguments != null && 2 < arguments.length ? arguments[2] : null;
            this.arguments3_ = arguments != null && 3 < arguments.length ? arguments[3] : null;
        }

        @Override
        public JavaScriptNode[] getArguments() {
            return new JavaScriptNode[]{this.arguments0_, this.arguments1_, this.arguments2_, this.arguments3_};
        }

        @Override
        public Object execute(VirtualFrame frameValue) {
            int state_0 = this.state_0_;
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            Object arguments2Value_ = this.arguments2_.execute(frameValue);
            Object arguments3Value_ = this.arguments3_.execute(frameValue);
            if (state_0 != 0) {
                if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSDataViewObject) {
                    JSDataViewObject arguments0Value__ = (JSDataViewObject)arguments0Value_;
                    DataViewData s0_ = this.dataView_cache;
                    if (s0_ != null) {
                        return this.doDataView(arguments0Value__, arguments1Value_, arguments2Value_, arguments3Value_, s0_.toIndexNode_, s0_.bufferTypeProfile_, s0_.arrayTypeProfile_);
                    }
                }
                if ((state_0 & 2) != 0 && !JSDataView.isJSDataView(arguments0Value_)) {
                    return this.doIncompatibleReceiver(arguments0Value_, arguments1Value_, arguments2Value_, arguments3Value_);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_, arguments2Value_, arguments3Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.execute(frameValue);
        }

        private Object executeAndSpecialize(Object arguments0Value, Object arguments1Value, Object arguments2Value, Object arguments3Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                if (arguments0Value instanceof JSDataViewObject) {
                    JSDataViewObject arguments0Value_ = (JSDataViewObject)arguments0Value;
                    DataViewData s0_ = super.insert(new DataViewData());
                    s0_.toIndexNode_ = s0_.insertAccessor(JSToIndexNode.create());
                    s0_.bufferTypeProfile_ = ValueProfile.createClassProfile();
                    s0_.arrayTypeProfile_ = ValueProfile.createClassProfile();
                    VarHandle.storeStoreFence();
                    this.dataView_cache = s0_;
                    this.state_0_ = state_0 |= 1;
                    lock.unlock();
                    hasLock = false;
                    Object object = this.doDataView(arguments0Value_, arguments1Value, arguments2Value, arguments3Value, s0_.toIndexNode_, s0_.bufferTypeProfile_, s0_.arrayTypeProfile_);
                    return object;
                }
                if (!JSDataView.isJSDataView(arguments0Value)) {
                    this.state_0_ = state_0 |= 2;
                    lock.unlock();
                    hasLock = false;
                    JSDynamicObject jSDynamicObject = this.doIncompatibleReceiver(arguments0Value, arguments1Value, arguments2Value, arguments3Value);
                    return jSDynamicObject;
                }
                throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_, this.arguments1_, this.arguments2_, this.arguments3_}, arguments0Value, arguments1Value, arguments2Value, arguments3Value);
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
            s[0] = "doDataView";
            if ((state_0 & 1) != 0) {
                s[1] = (byte)1;
                ArrayList<List<Cloneable>> cached = new ArrayList<List<Cloneable>>();
                DataViewData s0_ = this.dataView_cache;
                if (s0_ != null) {
                    cached.add(Arrays.asList(s0_.toIndexNode_, s0_.bufferTypeProfile_, s0_.arrayTypeProfile_));
                }
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[1] = s;
            s = new Object[3];
            s[0] = "doIncompatibleReceiver";
            s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[2] = s;
            return Introspection.Provider.create(data);
        }

        public static DataViewPrototypeBuiltins.DataViewSetNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new DataViewSetNodeGen(context, builtin, arguments);
        }

        @GeneratedBy(value=DataViewPrototypeBuiltins.DataViewSetNode.class)
        private static final class DataViewData
        extends Node {
            @Node.Child
            JSToIndexNode toIndexNode_;
            @CompilerDirectives.CompilationFinal
            ValueProfile bufferTypeProfile_;
            @CompilerDirectives.CompilationFinal
            ValueProfile arrayTypeProfile_;

            DataViewData() {
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

    @GeneratedBy(value=DataViewPrototypeBuiltins.DataViewGetNode.class)
    public static final class DataViewGetNodeGen
    extends DataViewPrototypeBuiltins.DataViewGetNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @Node.Child
        private JavaScriptNode arguments2_;
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private DataViewData dataView_cache;

        private DataViewGetNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            super(context, builtin);
            this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
            this.arguments1_ = arguments != null && 1 < arguments.length ? arguments[1] : null;
            this.arguments2_ = arguments != null && 2 < arguments.length ? arguments[2] : null;
        }

        @Override
        public JavaScriptNode[] getArguments() {
            return new JavaScriptNode[]{this.arguments0_, this.arguments1_, this.arguments2_};
        }

        @Override
        public Object execute(VirtualFrame frameValue) {
            int state_0 = this.state_0_;
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            Object arguments2Value_ = this.arguments2_.execute(frameValue);
            if (state_0 != 0) {
                if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSDataViewObject) {
                    JSDataViewObject arguments0Value__ = (JSDataViewObject)arguments0Value_;
                    DataViewData s0_ = this.dataView_cache;
                    if (s0_ != null) {
                        return this.doDataView(arguments0Value__, arguments1Value_, arguments2Value_, s0_.toIndexNode_, s0_.bufferTypeProfile_, s0_.arrayTypeProfile_);
                    }
                }
                if ((state_0 & 2) != 0 && !JSDataView.isJSDataView(arguments0Value_)) {
                    return this.doIncompatibleReceiver(arguments0Value_, arguments1Value_, arguments2Value_);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_, arguments2Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.execute(frameValue);
        }

        private Object executeAndSpecialize(Object arguments0Value, Object arguments1Value, Object arguments2Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                if (arguments0Value instanceof JSDataViewObject) {
                    JSDataViewObject arguments0Value_ = (JSDataViewObject)arguments0Value;
                    DataViewData s0_ = super.insert(new DataViewData());
                    s0_.toIndexNode_ = s0_.insertAccessor(JSToIndexNode.create());
                    s0_.bufferTypeProfile_ = ValueProfile.createClassProfile();
                    s0_.arrayTypeProfile_ = ValueProfile.createClassProfile();
                    VarHandle.storeStoreFence();
                    this.dataView_cache = s0_;
                    this.state_0_ = state_0 |= 1;
                    lock.unlock();
                    hasLock = false;
                    Object object = this.doDataView(arguments0Value_, arguments1Value, arguments2Value, s0_.toIndexNode_, s0_.bufferTypeProfile_, s0_.arrayTypeProfile_);
                    return object;
                }
                if (!JSDataView.isJSDataView(arguments0Value)) {
                    this.state_0_ = state_0 |= 2;
                    lock.unlock();
                    hasLock = false;
                    JSDynamicObject jSDynamicObject = this.doIncompatibleReceiver(arguments0Value, arguments1Value, arguments2Value);
                    return jSDynamicObject;
                }
                throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_, this.arguments1_, this.arguments2_}, arguments0Value, arguments1Value, arguments2Value);
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
            s[0] = "doDataView";
            if ((state_0 & 1) != 0) {
                s[1] = (byte)1;
                ArrayList<List<Cloneable>> cached = new ArrayList<List<Cloneable>>();
                DataViewData s0_ = this.dataView_cache;
                if (s0_ != null) {
                    cached.add(Arrays.asList(s0_.toIndexNode_, s0_.bufferTypeProfile_, s0_.arrayTypeProfile_));
                }
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[1] = s;
            s = new Object[3];
            s[0] = "doIncompatibleReceiver";
            s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[2] = s;
            return Introspection.Provider.create(data);
        }

        public static DataViewPrototypeBuiltins.DataViewGetNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new DataViewGetNodeGen(context, builtin, arguments);
        }

        @GeneratedBy(value=DataViewPrototypeBuiltins.DataViewGetNode.class)
        private static final class DataViewData
        extends Node {
            @Node.Child
            JSToIndexNode toIndexNode_;
            @CompilerDirectives.CompilationFinal
            ValueProfile bufferTypeProfile_;
            @CompilerDirectives.CompilationFinal
            ValueProfile arrayTypeProfile_;

            DataViewData() {
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
}

