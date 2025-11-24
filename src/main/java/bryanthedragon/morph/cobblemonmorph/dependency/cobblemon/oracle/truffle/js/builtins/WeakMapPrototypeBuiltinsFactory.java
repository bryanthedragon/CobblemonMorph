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
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.object.DynamicObjectLibrary;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.js.builtins.WeakMapPrototypeBuiltins;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.builtins.JSWeakMapObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=WeakMapPrototypeBuiltins.class)
public final class WeakMapPrototypeBuiltinsFactory {
    private static final LibraryFactory<DynamicObjectLibrary> DYNAMIC_OBJECT_LIBRARY_ = LibraryFactory.resolve(DynamicObjectLibrary.class);

    @GeneratedBy(value=WeakMapPrototypeBuiltins.JSWeakMapHasNode.class)
    public static final class JSWeakMapHasNodeGen
    extends WeakMapPrototypeBuiltins.JSWeakMapHasNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private DynamicObjectLibrary has_invertedGetter_;
        @CompilerDirectives.CompilationFinal
        private ConditionProfile has_hasInvertedProfile_;

        private JSWeakMapHasNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            if ((state_0 & 3) != 0 && arguments0Value_ instanceof JSWeakMapObject) {
                JSWeakMapObject arguments0Value__ = (JSWeakMapObject)arguments0Value_;
                if ((state_0 & 1) != 0 && arguments1Value_ instanceof JSObject) {
                    JSObject arguments1Value__ = (JSObject)arguments1Value_;
                    return this.has(arguments0Value__, arguments1Value__, this.has_invertedGetter_, this.has_hasInvertedProfile_);
                }
                if ((state_0 & 2) != 0 && !JSGuards.isJSObject(arguments1Value_)) {
                    return WeakMapPrototypeBuiltins.JSWeakMapHasNode.hasNonObjectKey(arguments0Value__, arguments1Value_);
                }
            }
            if ((state_0 & 4) != 0 && !JSGuards.isJSWeakMap(arguments0Value_)) {
                return WeakMapPrototypeBuiltins.JSWeakMapHasNode.notWeakMap(arguments0Value_, arguments1Value_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
        }

        @Override
        public boolean executeBoolean(VirtualFrame frameValue) {
            int state_0 = this.state_0_;
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            if ((state_0 & 3) != 0 && arguments0Value_ instanceof JSWeakMapObject) {
                JSWeakMapObject arguments0Value__ = (JSWeakMapObject)arguments0Value_;
                if ((state_0 & 1) != 0 && arguments1Value_ instanceof JSObject) {
                    JSObject arguments1Value__ = (JSObject)arguments1Value_;
                    return this.has(arguments0Value__, arguments1Value__, this.has_invertedGetter_, this.has_hasInvertedProfile_);
                }
                if ((state_0 & 2) != 0 && !JSGuards.isJSObject(arguments1Value_)) {
                    return WeakMapPrototypeBuiltins.JSWeakMapHasNode.hasNonObjectKey(arguments0Value__, arguments1Value_);
                }
            }
            if ((state_0 & 4) != 0 && !JSGuards.isJSWeakMap(arguments0Value_)) {
                return WeakMapPrototypeBuiltins.JSWeakMapHasNode.notWeakMap(arguments0Value_, arguments1Value_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.executeBoolean(frameValue);
        }

        private boolean executeAndSpecialize(Object arguments0Value, Object arguments1Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                if (arguments0Value instanceof JSWeakMapObject) {
                    JSWeakMapObject arguments0Value_ = (JSWeakMapObject)arguments0Value;
                    if (arguments1Value instanceof JSObject) {
                        JSObject arguments1Value_ = (JSObject)arguments1Value;
                        this.has_invertedGetter_ = super.insert(DYNAMIC_OBJECT_LIBRARY_.createDispatched(5));
                        this.has_hasInvertedProfile_ = ConditionProfile.createBinaryProfile();
                        this.state_0_ = state_0 |= 1;
                        lock.unlock();
                        hasLock = false;
                        boolean bl = this.has(arguments0Value_, arguments1Value_, this.has_invertedGetter_, this.has_hasInvertedProfile_);
                        return bl;
                    }
                    if (!JSGuards.isJSObject(arguments1Value)) {
                        this.state_0_ = state_0 |= 2;
                        lock.unlock();
                        hasLock = false;
                        boolean bl = WeakMapPrototypeBuiltins.JSWeakMapHasNode.hasNonObjectKey(arguments0Value_, arguments1Value);
                        return bl;
                    }
                }
                if (!JSGuards.isJSWeakMap(arguments0Value)) {
                    this.state_0_ = state_0 |= 4;
                    lock.unlock();
                    hasLock = false;
                    boolean bl = WeakMapPrototypeBuiltins.JSWeakMapHasNode.notWeakMap(arguments0Value, arguments1Value);
                    return bl;
                }
                throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_, this.arguments1_}, arguments0Value, arguments1Value);
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
            Object[] data = new Object[4];
            data[0] = 0;
            int state_0 = this.state_0_;
            Object[] s = new Object[3];
            s[0] = "has";
            if ((state_0 & 1) != 0) {
                s[1] = (byte)1;
                ArrayList<List<Cloneable>> cached = new ArrayList<List<Cloneable>>();
                cached.add(Arrays.asList(this.has_invertedGetter_, this.has_hasInvertedProfile_));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[1] = s;
            s = new Object[3];
            s[0] = "hasNonObjectKey";
            s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[2] = s;
            s = new Object[3];
            s[0] = "notWeakMap";
            s[1] = (state_0 & 4) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[3] = s;
            return Introspection.Provider.create(data);
        }

        public static WeakMapPrototypeBuiltins.JSWeakMapHasNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSWeakMapHasNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=WeakMapPrototypeBuiltins.JSWeakMapSetNode.class)
    public static final class JSWeakMapSetNodeGen
    extends WeakMapPrototypeBuiltins.JSWeakMapSetNode
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
        private SetData set_cache;

        private JSWeakMapSetNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
                if ((state_0 & 3) != 0 && arguments0Value_ instanceof JSWeakMapObject) {
                    JSWeakMapObject arguments0Value__ = (JSWeakMapObject)arguments0Value_;
                    if ((state_0 & 1) != 0 && arguments1Value_ instanceof JSObject) {
                        JSObject arguments1Value__ = (JSObject)arguments1Value_;
                        SetData s0_ = this.set_cache;
                        if (s0_ != null) {
                            return this.set(arguments0Value__, arguments1Value__, arguments2Value_, s0_.invertedGetter_, s0_.invertedSetter_, s0_.hasInvertedProfile_);
                        }
                    }
                    if ((state_0 & 2) != 0 && !JSGuards.isJSObject(arguments1Value_)) {
                        return WeakMapPrototypeBuiltins.JSWeakMapSetNode.setNonObjectKey(arguments0Value__, arguments1Value_, arguments2Value_);
                    }
                }
                if ((state_0 & 4) != 0 && !JSGuards.isJSWeakMap(arguments0Value_)) {
                    return WeakMapPrototypeBuiltins.JSWeakMapSetNode.notWeakMap(arguments0Value_, arguments1Value_, arguments2Value_);
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
                if (arguments0Value instanceof JSWeakMapObject) {
                    JSWeakMapObject arguments0Value_ = (JSWeakMapObject)arguments0Value;
                    if (arguments1Value instanceof JSObject) {
                        JSObject arguments1Value_ = (JSObject)arguments1Value;
                        SetData s0_ = super.insert(new SetData());
                        s0_.invertedGetter_ = s0_.insertAccessor(DYNAMIC_OBJECT_LIBRARY_.createDispatched(5));
                        s0_.invertedSetter_ = s0_.insertAccessor(DYNAMIC_OBJECT_LIBRARY_.createDispatched(5));
                        s0_.hasInvertedProfile_ = ConditionProfile.createBinaryProfile();
                        VarHandle.storeStoreFence();
                        this.set_cache = s0_;
                        this.state_0_ = state_0 |= 1;
                        lock.unlock();
                        hasLock = false;
                        Object object = this.set(arguments0Value_, arguments1Value_, arguments2Value, s0_.invertedGetter_, s0_.invertedSetter_, s0_.hasInvertedProfile_);
                        return object;
                    }
                    if (!JSGuards.isJSObject(arguments1Value)) {
                        this.state_0_ = state_0 |= 2;
                        lock.unlock();
                        hasLock = false;
                        Object object = WeakMapPrototypeBuiltins.JSWeakMapSetNode.setNonObjectKey(arguments0Value_, arguments1Value, arguments2Value);
                        return object;
                    }
                }
                if (!JSGuards.isJSWeakMap(arguments0Value)) {
                    this.state_0_ = state_0 |= 4;
                    lock.unlock();
                    hasLock = false;
                    Object object = WeakMapPrototypeBuiltins.JSWeakMapSetNode.notWeakMap(arguments0Value, arguments1Value, arguments2Value);
                    return object;
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
            Object[] data = new Object[4];
            data[0] = 0;
            int state_0 = this.state_0_;
            Object[] s = new Object[3];
            s[0] = "set";
            if ((state_0 & 1) != 0) {
                s[1] = (byte)1;
                ArrayList<List<Cloneable>> cached = new ArrayList<List<Cloneable>>();
                SetData s0_ = this.set_cache;
                if (s0_ != null) {
                    cached.add(Arrays.asList(s0_.invertedGetter_, s0_.invertedSetter_, s0_.hasInvertedProfile_));
                }
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[1] = s;
            s = new Object[3];
            s[0] = "setNonObjectKey";
            s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[2] = s;
            s = new Object[3];
            s[0] = "notWeakMap";
            s[1] = (state_0 & 4) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[3] = s;
            return Introspection.Provider.create(data);
        }

        public static WeakMapPrototypeBuiltins.JSWeakMapSetNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSWeakMapSetNodeGen(context, builtin, arguments);
        }

        @GeneratedBy(value=WeakMapPrototypeBuiltins.JSWeakMapSetNode.class)
        private static final class SetData
        extends Node {
            @Node.Child
            DynamicObjectLibrary invertedGetter_;
            @Node.Child
            DynamicObjectLibrary invertedSetter_;
            @CompilerDirectives.CompilationFinal
            ConditionProfile hasInvertedProfile_;

            SetData() {
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

    @GeneratedBy(value=WeakMapPrototypeBuiltins.JSWeakMapGetNode.class)
    public static final class JSWeakMapGetNodeGen
    extends WeakMapPrototypeBuiltins.JSWeakMapGetNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private DynamicObjectLibrary get_invertedGetter_;
        @CompilerDirectives.CompilationFinal
        private ConditionProfile get_hasInvertedProfile_;

        private JSWeakMapGetNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            if ((state_0 & 3) != 0 && arguments0Value_ instanceof JSWeakMapObject) {
                JSWeakMapObject arguments0Value__ = (JSWeakMapObject)arguments0Value_;
                if ((state_0 & 1) != 0 && arguments1Value_ instanceof JSObject) {
                    JSObject arguments1Value__ = (JSObject)arguments1Value_;
                    return this.get(arguments0Value__, arguments1Value__, this.get_invertedGetter_, this.get_hasInvertedProfile_);
                }
                if ((state_0 & 2) != 0 && !JSGuards.isJSObject(arguments1Value_)) {
                    return WeakMapPrototypeBuiltins.JSWeakMapGetNode.getNonObjectKey(arguments0Value__, arguments1Value_);
                }
            }
            if ((state_0 & 4) != 0 && !JSGuards.isJSWeakMap(arguments0Value_)) {
                return WeakMapPrototypeBuiltins.JSWeakMapGetNode.notWeakMap(arguments0Value_, arguments1Value_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.execute(frameValue);
        }

        private Object executeAndSpecialize(Object arguments0Value, Object arguments1Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                if (arguments0Value instanceof JSWeakMapObject) {
                    JSWeakMapObject arguments0Value_ = (JSWeakMapObject)arguments0Value;
                    if (arguments1Value instanceof JSObject) {
                        JSObject arguments1Value_ = (JSObject)arguments1Value;
                        this.get_invertedGetter_ = super.insert(DYNAMIC_OBJECT_LIBRARY_.createDispatched(5));
                        this.get_hasInvertedProfile_ = ConditionProfile.createBinaryProfile();
                        this.state_0_ = state_0 |= 1;
                        lock.unlock();
                        hasLock = false;
                        Object object = this.get(arguments0Value_, arguments1Value_, this.get_invertedGetter_, this.get_hasInvertedProfile_);
                        return object;
                    }
                    if (!JSGuards.isJSObject(arguments1Value)) {
                        this.state_0_ = state_0 |= 2;
                        lock.unlock();
                        hasLock = false;
                        Object object = WeakMapPrototypeBuiltins.JSWeakMapGetNode.getNonObjectKey(arguments0Value_, arguments1Value);
                        return object;
                    }
                }
                if (!JSGuards.isJSWeakMap(arguments0Value)) {
                    this.state_0_ = state_0 |= 4;
                    lock.unlock();
                    hasLock = false;
                    Object object = WeakMapPrototypeBuiltins.JSWeakMapGetNode.notWeakMap(arguments0Value, arguments1Value);
                    return object;
                }
                throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_, this.arguments1_}, arguments0Value, arguments1Value);
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
            Object[] data = new Object[4];
            data[0] = 0;
            int state_0 = this.state_0_;
            Object[] s = new Object[3];
            s[0] = "get";
            if ((state_0 & 1) != 0) {
                s[1] = (byte)1;
                ArrayList<List<Cloneable>> cached = new ArrayList<List<Cloneable>>();
                cached.add(Arrays.asList(this.get_invertedGetter_, this.get_hasInvertedProfile_));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[1] = s;
            s = new Object[3];
            s[0] = "getNonObjectKey";
            s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[2] = s;
            s = new Object[3];
            s[0] = "notWeakMap";
            s[1] = (state_0 & 4) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[3] = s;
            return Introspection.Provider.create(data);
        }

        public static WeakMapPrototypeBuiltins.JSWeakMapGetNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSWeakMapGetNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=WeakMapPrototypeBuiltins.JSWeakMapDeleteNode.class)
    public static final class JSWeakMapDeleteNodeGen
    extends WeakMapPrototypeBuiltins.JSWeakMapDeleteNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private DynamicObjectLibrary delete_invertedGetter_;
        @CompilerDirectives.CompilationFinal
        private ConditionProfile delete_hasInvertedProfile_;

        private JSWeakMapDeleteNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            if ((state_0 & 3) != 0 && arguments0Value_ instanceof JSWeakMapObject) {
                JSWeakMapObject arguments0Value__ = (JSWeakMapObject)arguments0Value_;
                if ((state_0 & 1) != 0 && arguments1Value_ instanceof JSObject) {
                    JSObject arguments1Value__ = (JSObject)arguments1Value_;
                    return WeakMapPrototypeBuiltins.JSWeakMapDeleteNode.delete(arguments0Value__, arguments1Value__, this.delete_invertedGetter_, this.delete_hasInvertedProfile_);
                }
                if ((state_0 & 2) != 0 && !JSGuards.isJSObject(arguments1Value_)) {
                    return WeakMapPrototypeBuiltins.JSWeakMapDeleteNode.deleteNonObjectKey(arguments0Value__, arguments1Value_);
                }
            }
            if ((state_0 & 4) != 0 && !JSGuards.isJSWeakMap(arguments0Value_)) {
                return WeakMapPrototypeBuiltins.JSWeakMapDeleteNode.notWeakMap(arguments0Value_, arguments1Value_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
        }

        @Override
        public boolean executeBoolean(VirtualFrame frameValue) {
            int state_0 = this.state_0_;
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            if ((state_0 & 3) != 0 && arguments0Value_ instanceof JSWeakMapObject) {
                JSWeakMapObject arguments0Value__ = (JSWeakMapObject)arguments0Value_;
                if ((state_0 & 1) != 0 && arguments1Value_ instanceof JSObject) {
                    JSObject arguments1Value__ = (JSObject)arguments1Value_;
                    return WeakMapPrototypeBuiltins.JSWeakMapDeleteNode.delete(arguments0Value__, arguments1Value__, this.delete_invertedGetter_, this.delete_hasInvertedProfile_);
                }
                if ((state_0 & 2) != 0 && !JSGuards.isJSObject(arguments1Value_)) {
                    return WeakMapPrototypeBuiltins.JSWeakMapDeleteNode.deleteNonObjectKey(arguments0Value__, arguments1Value_);
                }
            }
            if ((state_0 & 4) != 0 && !JSGuards.isJSWeakMap(arguments0Value_)) {
                return WeakMapPrototypeBuiltins.JSWeakMapDeleteNode.notWeakMap(arguments0Value_, arguments1Value_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.executeBoolean(frameValue);
        }

        private boolean executeAndSpecialize(Object arguments0Value, Object arguments1Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                if (arguments0Value instanceof JSWeakMapObject) {
                    JSWeakMapObject arguments0Value_ = (JSWeakMapObject)arguments0Value;
                    if (arguments1Value instanceof JSObject) {
                        JSObject arguments1Value_ = (JSObject)arguments1Value;
                        this.delete_invertedGetter_ = super.insert(DYNAMIC_OBJECT_LIBRARY_.createDispatched(5));
                        this.delete_hasInvertedProfile_ = ConditionProfile.createBinaryProfile();
                        this.state_0_ = state_0 |= 1;
                        lock.unlock();
                        hasLock = false;
                        boolean bl = WeakMapPrototypeBuiltins.JSWeakMapDeleteNode.delete(arguments0Value_, arguments1Value_, this.delete_invertedGetter_, this.delete_hasInvertedProfile_);
                        return bl;
                    }
                    if (!JSGuards.isJSObject(arguments1Value)) {
                        this.state_0_ = state_0 |= 2;
                        lock.unlock();
                        hasLock = false;
                        boolean bl = WeakMapPrototypeBuiltins.JSWeakMapDeleteNode.deleteNonObjectKey(arguments0Value_, arguments1Value);
                        return bl;
                    }
                }
                if (!JSGuards.isJSWeakMap(arguments0Value)) {
                    this.state_0_ = state_0 |= 4;
                    lock.unlock();
                    hasLock = false;
                    boolean bl = WeakMapPrototypeBuiltins.JSWeakMapDeleteNode.notWeakMap(arguments0Value, arguments1Value);
                    return bl;
                }
                throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_, this.arguments1_}, arguments0Value, arguments1Value);
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
            Object[] data = new Object[4];
            data[0] = 0;
            int state_0 = this.state_0_;
            Object[] s = new Object[3];
            s[0] = "delete";
            if ((state_0 & 1) != 0) {
                s[1] = (byte)1;
                ArrayList<List<Cloneable>> cached = new ArrayList<List<Cloneable>>();
                cached.add(Arrays.asList(this.delete_invertedGetter_, this.delete_hasInvertedProfile_));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[1] = s;
            s = new Object[3];
            s[0] = "deleteNonObjectKey";
            s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[2] = s;
            s = new Object[3];
            s[0] = "notWeakMap";
            s[1] = (state_0 & 4) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[3] = s;
            return Introspection.Provider.create(data);
        }

        public static WeakMapPrototypeBuiltins.JSWeakMapDeleteNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSWeakMapDeleteNodeGen(context, builtin, arguments);
        }
    }
}

