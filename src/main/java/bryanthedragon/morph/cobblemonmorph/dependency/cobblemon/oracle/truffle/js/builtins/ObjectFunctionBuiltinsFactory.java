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
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.library.LibraryFactory;
import com.oracle.truffle.api.nodes.EncapsulatingNodeReference;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.builtins.ObjectFunctionBuiltins;
import com.oracle.truffle.js.builtins.helper.ListGetNode;
import com.oracle.truffle.js.builtins.helper.ListSizeNode;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.JSTypes;
import com.oracle.truffle.js.nodes.JSTypesGen;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.access.EnumerableOwnPropertyNamesNode;
import com.oracle.truffle.js.nodes.access.GetPrototypeNode;
import com.oracle.truffle.js.nodes.access.IsExtensibleNode;
import com.oracle.truffle.js.nodes.access.JSGetOwnPropertyNode;
import com.oracle.truffle.js.nodes.access.ReadElementNode;
import com.oracle.truffle.js.nodes.access.WriteElementNode;
import com.oracle.truffle.js.nodes.binary.JSIdenticalNode;
import com.oracle.truffle.js.nodes.cast.JSToObjectNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.nodes.interop.ImportValueNode;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.SafeInteger;
import com.oracle.truffle.js.runtime.Symbol;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.util.JSClassProfile;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=ObjectFunctionBuiltins.class)
public final class ObjectFunctionBuiltinsFactory {
    private static final LibraryFactory<InteropLibrary> INTEROP_LIBRARY_ = LibraryFactory.resolve(InteropLibrary.class);

    @GeneratedBy(value=ObjectFunctionBuiltins.ObjectHasOwnNode.class)
    public static final class ObjectHasOwnNodeGen
    extends ObjectFunctionBuiltins.ObjectHasOwnNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;

        private ObjectHasOwnNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            return this.hasOwn(arguments0Value_, arguments1Value_);
        }

        @Override
        public boolean executeBoolean(VirtualFrame frameValue) {
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            return this.hasOwn(arguments0Value_, arguments1Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.executeBoolean(frameValue);
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
            s[0] = "hasOwn";
            s[1] = (byte)1;
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static ObjectFunctionBuiltins.ObjectHasOwnNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new ObjectHasOwnNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=ObjectFunctionBuiltins.ObjectBindPropertiesNode.class)
    public static final class ObjectBindPropertiesNodeGen
    extends ObjectFunctionBuiltins.ObjectBindPropertiesNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @CompilerDirectives.CompilationFinal
        private volatile int exclude_;
        @Node.Child
        private BindProperties5Data bindProperties5_cache;
        @Node.Child
        private InteropLibrary bindProperties6_members_;

        private ObjectBindPropertiesNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            super(context, builtin);
            this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
            this.arguments1_ = arguments != null && 1 < arguments.length ? arguments[1] : null;
        }

        @Override
        public JavaScriptNode[] getArguments() {
            return new JavaScriptNode[]{this.arguments0_, this.arguments1_};
        }

        @Override
        @ExplodeLoop
        public Object execute(VirtualFrame frameValue) {
            int state_0 = this.state_0_;
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            if ((state_0 & 1) != 0 && !JSGuards.isJSObject(arguments0Value_)) {
                return this.bindPropertiesInvalidTarget(arguments0Value_, arguments1Value_);
            }
            if ((state_0 & 0x1FE) != 0 && arguments0Value_ instanceof JSDynamicObject) {
                Object arguments1Value__;
                JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
                if ((state_0 & 2) != 0 && arguments1Value_ instanceof JSDynamicObject) {
                    arguments1Value__ = (JSDynamicObject)arguments1Value_;
                    if (JSGuards.isJSObject(arguments0Value__) && JSGuards.isJSDynamicObject(arguments1Value__)) {
                        return this.bindPropertiesDynamicObject(arguments0Value__, (JSDynamicObject)arguments1Value__);
                    }
                }
                if ((state_0 & 4) != 0 && arguments1Value_ instanceof Symbol) {
                    arguments1Value__ = (Symbol)arguments1Value_;
                    if (JSGuards.isJSObject(arguments0Value__)) {
                        return this.bindProperties(arguments0Value__, (Symbol)arguments1Value__);
                    }
                }
                if ((state_0 & 8) != 0 && arguments1Value_ instanceof TruffleString) {
                    arguments1Value__ = (TruffleString)arguments1Value_;
                    if (JSGuards.isJSObject(arguments0Value__)) {
                        return this.bindProperties(arguments0Value__, (TruffleString)arguments1Value__);
                    }
                }
                if ((state_0 & 0x10) != 0 && arguments1Value_ instanceof SafeInteger) {
                    arguments1Value__ = (SafeInteger)arguments1Value_;
                    if (JSGuards.isJSObject(arguments0Value__)) {
                        return this.bindProperties(arguments0Value__, (SafeInteger)arguments1Value__);
                    }
                }
                if ((state_0 & 0x20) != 0 && arguments1Value_ instanceof BigInt) {
                    arguments1Value__ = (BigInt)arguments1Value_;
                    if (JSGuards.isJSObject(arguments0Value__)) {
                        return this.bindProperties(arguments0Value__, (BigInt)arguments1Value__);
                    }
                }
                if ((state_0 & 0x1C0) != 0) {
                    if ((state_0 & 0x40) != 0 && JSGuards.isJSObject(arguments0Value__) && !JSGuards.isTruffleObject(arguments1Value_)) {
                        return this.bindProperties(arguments0Value__, arguments1Value_);
                    }
                    if ((state_0 & 0x80) != 0) {
                        BindProperties5Data s7_ = this.bindProperties5_cache;
                        while (s7_ != null) {
                            if (s7_.interop_.accepts(arguments1Value_) && JSGuards.isJSObject(arguments0Value__) && JSGuards.isForeignObject(arguments1Value_)) {
                                return this.bindProperties(arguments0Value__, arguments1Value_, s7_.interop_, s7_.members_);
                            }
                            s7_ = s7_.next_;
                        }
                    }
                    if ((state_0 & 0x100) != 0 && JSGuards.isJSObject(arguments0Value__) && JSGuards.isForeignObject(arguments1Value_)) {
                        return this.bindProperties6Boundary(state_0, arguments0Value__, arguments1Value_);
                    }
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @CompilerDirectives.TruffleBoundary
        private Object bindProperties6Boundary(int state_0, JSDynamicObject arguments0Value__, Object arguments1Value_) {
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this);
            try {
                InteropLibrary bindProperties6_interop__ = INTEROP_LIBRARY_.getUncached(arguments1Value_);
                JSDynamicObject jSDynamicObject = this.bindProperties(arguments0Value__, arguments1Value_, bindProperties6_interop__, this.bindProperties6_members_);
                return jSDynamicObject;
            }
            finally {
                encapsulating_.set(prev_);
            }
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.execute(frameValue);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private JSDynamicObject executeAndSpecialize(Object arguments0Value, Object arguments1Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                int exclude = this.exclude_;
                if (!JSGuards.isJSObject(arguments0Value)) {
                    this.state_0_ = state_0 |= 1;
                    lock.unlock();
                    hasLock = false;
                    JSDynamicObject jSDynamicObject = this.bindPropertiesInvalidTarget(arguments0Value, arguments1Value);
                    return jSDynamicObject;
                }
                if (arguments0Value instanceof JSDynamicObject) {
                    Object arguments1Value_;
                    JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
                    if (arguments1Value instanceof JSDynamicObject) {
                        arguments1Value_ = (JSDynamicObject)arguments1Value;
                        if (JSGuards.isJSObject(arguments0Value_) && JSGuards.isJSDynamicObject(arguments1Value_)) {
                            this.state_0_ = state_0 |= 2;
                            lock.unlock();
                            hasLock = false;
                            JSDynamicObject jSDynamicObject = this.bindPropertiesDynamicObject(arguments0Value_, (JSDynamicObject)arguments1Value_);
                            return jSDynamicObject;
                        }
                    }
                    if (arguments1Value instanceof Symbol) {
                        arguments1Value_ = (Symbol)arguments1Value;
                        if (JSGuards.isJSObject(arguments0Value_)) {
                            this.state_0_ = state_0 |= 4;
                            lock.unlock();
                            hasLock = false;
                            JSDynamicObject jSDynamicObject = this.bindProperties(arguments0Value_, (Symbol)arguments1Value_);
                            return jSDynamicObject;
                        }
                    }
                    if (arguments1Value instanceof TruffleString) {
                        arguments1Value_ = (TruffleString)arguments1Value;
                        if (JSGuards.isJSObject(arguments0Value_)) {
                            this.state_0_ = state_0 |= 8;
                            lock.unlock();
                            hasLock = false;
                            JSDynamicObject jSDynamicObject = this.bindProperties(arguments0Value_, (TruffleString)arguments1Value_);
                            return jSDynamicObject;
                        }
                    }
                    if (arguments1Value instanceof SafeInteger) {
                        arguments1Value_ = (SafeInteger)arguments1Value;
                        if (JSGuards.isJSObject(arguments0Value_)) {
                            this.state_0_ = state_0 |= 0x10;
                            lock.unlock();
                            hasLock = false;
                            JSDynamicObject jSDynamicObject = this.bindProperties(arguments0Value_, (SafeInteger)arguments1Value_);
                            return jSDynamicObject;
                        }
                    }
                    if (arguments1Value instanceof BigInt) {
                        arguments1Value_ = (BigInt)arguments1Value;
                        if (JSGuards.isJSObject(arguments0Value_)) {
                            this.state_0_ = state_0 |= 0x20;
                            lock.unlock();
                            hasLock = false;
                            JSDynamicObject jSDynamicObject = this.bindProperties(arguments0Value_, (BigInt)arguments1Value_);
                            return jSDynamicObject;
                        }
                    }
                    if (JSGuards.isJSObject(arguments0Value_) && !JSGuards.isTruffleObject(arguments1Value)) {
                        this.state_0_ = state_0 |= 0x40;
                        lock.unlock();
                        hasLock = false;
                        arguments1Value_ = this.bindProperties(arguments0Value_, arguments1Value);
                        return arguments1Value_;
                    }
                    if (exclude == 0) {
                        int count7_ = 0;
                        BindProperties5Data s7_ = this.bindProperties5_cache;
                        if ((state_0 & 0x80) != 0) {
                            while (!(s7_ == null || s7_.interop_.accepts(arguments1Value) && JSGuards.isJSObject(arguments0Value_) && JSGuards.isForeignObject(arguments1Value))) {
                                s7_ = s7_.next_;
                                ++count7_;
                            }
                        }
                        if (s7_ == null && JSGuards.isJSObject(arguments0Value_) && JSGuards.isForeignObject(arguments1Value) && count7_ < 5) {
                            s7_ = super.insert(new BindProperties5Data(this.bindProperties5_cache));
                            s7_.interop_ = s7_.insertAccessor(INTEROP_LIBRARY_.create(arguments1Value));
                            s7_.members_ = s7_.insertAccessor(INTEROP_LIBRARY_.createDispatched(5));
                            VarHandle.storeStoreFence();
                            this.bindProperties5_cache = s7_;
                            this.state_0_ = state_0 |= 0x80;
                        }
                        if (s7_ != null) {
                            lock.unlock();
                            hasLock = false;
                            JSDynamicObject jSDynamicObject = this.bindProperties(arguments0Value_, arguments1Value, s7_.interop_, s7_.members_);
                            return jSDynamicObject;
                        }
                    }
                    InteropLibrary bindProperties6_interop__ = null;
                    EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                    Node prev_ = encapsulating_.set(this);
                    try {
                        if (JSGuards.isJSObject(arguments0Value_) && JSGuards.isForeignObject(arguments1Value)) {
                            bindProperties6_interop__ = INTEROP_LIBRARY_.getUncached(arguments1Value);
                            this.bindProperties6_members_ = super.insert(INTEROP_LIBRARY_.createDispatched(5));
                            this.exclude_ = exclude |= 1;
                            this.bindProperties5_cache = null;
                            state_0 &= 0xFFFFFF7F;
                            this.state_0_ = state_0 |= 0x100;
                            lock.unlock();
                            hasLock = false;
                            JSDynamicObject jSDynamicObject = this.bindProperties(arguments0Value_, arguments1Value, bindProperties6_interop__, this.bindProperties6_members_);
                            return jSDynamicObject;
                        }
                    }
                    finally {
                        encapsulating_.set(prev_);
                    }
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
            BindProperties5Data s7_;
            int state_0 = this.state_0_;
            if (state_0 == 0) {
                return NodeCost.UNINITIALIZED;
            }
            if ((state_0 & state_0 - 1) == 0 && ((s7_ = this.bindProperties5_cache) == null || s7_.next_ == null)) {
                return NodeCost.MONOMORPHIC;
            }
            return NodeCost.POLYMORPHIC;
        }

        @Override
        public Introspection getIntrospectionData() {
            ArrayList<List<InteropLibrary>> cached;
            Object[] data = new Object[10];
            data[0] = 0;
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            Object[] s = new Object[3];
            s[0] = "bindPropertiesInvalidTarget";
            s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[1] = s;
            s = new Object[3];
            s[0] = "bindPropertiesDynamicObject";
            s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[2] = s;
            s = new Object[3];
            s[0] = "bindProperties";
            s[1] = (state_0 & 4) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[3] = s;
            s = new Object[3];
            s[0] = "bindProperties";
            s[1] = (state_0 & 8) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[4] = s;
            s = new Object[3];
            s[0] = "bindProperties";
            s[1] = (state_0 & 0x10) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[5] = s;
            s = new Object[3];
            s[0] = "bindProperties";
            s[1] = (state_0 & 0x20) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[6] = s;
            s = new Object[3];
            s[0] = "bindProperties";
            s[1] = (state_0 & 0x40) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[7] = s;
            s = new Object[3];
            s[0] = "bindProperties";
            if ((state_0 & 0x80) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList<List<InteropLibrary>>();
                BindProperties5Data s7_ = this.bindProperties5_cache;
                while (s7_ != null) {
                    cached.add(Arrays.asList(s7_.interop_, s7_.members_));
                    s7_ = s7_.next_;
                }
                s[2] = cached;
            } else {
                s[1] = exclude != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0);
            }
            data[8] = s;
            s = new Object[3];
            s[0] = "bindProperties";
            if ((state_0 & 0x100) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList();
                cached.add(Arrays.asList(this.bindProperties6_members_));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[9] = s;
            return Introspection.Provider.create(data);
        }

        public static ObjectFunctionBuiltins.ObjectBindPropertiesNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new ObjectBindPropertiesNodeGen(context, builtin, arguments);
        }

        @GeneratedBy(value=ObjectFunctionBuiltins.ObjectBindPropertiesNode.class)
        private static final class BindProperties5Data
        extends Node {
            @Node.Child
            BindProperties5Data next_;
            @Node.Child
            InteropLibrary interop_;
            @Node.Child
            InteropLibrary members_;

            BindProperties5Data(BindProperties5Data next_) {
                this.next_ = next_;
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

    @GeneratedBy(value=ObjectFunctionBuiltins.ObjectFromEntriesNode.class)
    public static final class ObjectFromEntriesNodeGen
    extends ObjectFunctionBuiltins.ObjectFromEntriesNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;

        private ObjectFromEntriesNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            super(context, builtin);
            this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
        }

        @Override
        public JavaScriptNode[] getArguments() {
            return new JavaScriptNode[]{this.arguments0_};
        }

        @Override
        public Object execute(VirtualFrame frameValue) {
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            return this.entries(arguments0Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.execute(frameValue);
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
            s[0] = "entries";
            s[1] = (byte)1;
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static ObjectFunctionBuiltins.ObjectFromEntriesNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new ObjectFromEntriesNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=ObjectFunctionBuiltins.ObjectValuesOrEntriesNode.class)
    public static final class ObjectValuesOrEntriesNodeGen
    extends ObjectFunctionBuiltins.ObjectValuesOrEntriesNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private ObjectFunctionBuiltins.ObjectValuesOrEntriesNode valuesOrEntriesGeneric_recursive_;

        private ObjectValuesOrEntriesNodeGen(JSContext context, JSBuiltin builtin, boolean entries, JavaScriptNode[] arguments) {
            super(context, builtin, entries);
            this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
        }

        @Override
        public JavaScriptNode[] getArguments() {
            return new JavaScriptNode[]{this.arguments0_};
        }

        @Override
        protected JSDynamicObject executeEvaluated(Object arguments0Value) {
            JSDynamicObject arguments0Value_;
            int state_0 = this.state_0_;
            if ((state_0 & 1) != 0 && arguments0Value instanceof JSDynamicObject && JSGuards.isJSObject(arguments0Value_ = (JSDynamicObject)arguments0Value)) {
                return this.valuesOrEntriesJSObject(arguments0Value_);
            }
            if ((state_0 & 6) != 0) {
                if ((state_0 & 2) != 0 && JSGuards.isForeignObject(arguments0Value)) {
                    return this.valuesOrEntriesForeign(arguments0Value);
                }
                if ((state_0 & 4) != 0 && !JSGuards.isJSObject(arguments0Value) && !JSGuards.isForeignObject(arguments0Value)) {
                    return this.valuesOrEntriesGeneric(arguments0Value, this.valuesOrEntriesGeneric_recursive_);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value);
        }

        @Override
        public Object execute(VirtualFrame frameValue) {
            JSDynamicObject arguments0Value__;
            int state_0 = this.state_0_;
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSDynamicObject && JSGuards.isJSObject(arguments0Value__ = (JSDynamicObject)arguments0Value_)) {
                return this.valuesOrEntriesJSObject(arguments0Value__);
            }
            if ((state_0 & 6) != 0) {
                if ((state_0 & 2) != 0 && JSGuards.isForeignObject(arguments0Value_)) {
                    return this.valuesOrEntriesForeign(arguments0Value_);
                }
                if ((state_0 & 4) != 0 && !JSGuards.isJSObject(arguments0Value_) && !JSGuards.isForeignObject(arguments0Value_)) {
                    return this.valuesOrEntriesGeneric(arguments0Value_, this.valuesOrEntriesGeneric_recursive_);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.execute(frameValue);
        }

        private JSDynamicObject executeAndSpecialize(Object arguments0Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                JSDynamicObject jSDynamicObject;
                JSDynamicObject arguments0Value_;
                int state_0 = this.state_0_;
                if (arguments0Value instanceof JSDynamicObject && JSGuards.isJSObject(arguments0Value_ = (JSDynamicObject)arguments0Value)) {
                    this.state_0_ = state_0 |= 1;
                    lock.unlock();
                    hasLock = false;
                    JSDynamicObject jSDynamicObject2 = this.valuesOrEntriesJSObject(arguments0Value_);
                    return jSDynamicObject2;
                }
                if (JSGuards.isForeignObject(arguments0Value)) {
                    this.state_0_ = state_0 |= 2;
                    lock.unlock();
                    hasLock = false;
                    jSDynamicObject = this.valuesOrEntriesForeign(arguments0Value);
                    return jSDynamicObject;
                }
                if (!JSGuards.isJSObject(arguments0Value) && !JSGuards.isForeignObject(arguments0Value)) {
                    this.valuesOrEntriesGeneric_recursive_ = super.insert(this.createRecursive());
                    this.state_0_ = state_0 |= 4;
                    lock.unlock();
                    hasLock = false;
                    jSDynamicObject = this.valuesOrEntriesGeneric(arguments0Value, this.valuesOrEntriesGeneric_recursive_);
                    return jSDynamicObject;
                }
                throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_}, arguments0Value);
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
            s[0] = "valuesOrEntriesJSObject";
            s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[1] = s;
            s = new Object[3];
            s[0] = "valuesOrEntriesForeign";
            s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[2] = s;
            s = new Object[3];
            s[0] = "valuesOrEntriesGeneric";
            if ((state_0 & 4) != 0) {
                s[1] = (byte)1;
                ArrayList<List<ObjectFunctionBuiltins.ObjectValuesOrEntriesNode>> cached = new ArrayList<List<ObjectFunctionBuiltins.ObjectValuesOrEntriesNode>>();
                cached.add(Arrays.asList(this.valuesOrEntriesGeneric_recursive_));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[3] = s;
            return Introspection.Provider.create(data);
        }

        public static ObjectFunctionBuiltins.ObjectValuesOrEntriesNode create(JSContext context, JSBuiltin builtin, boolean entries, JavaScriptNode[] arguments) {
            return new ObjectValuesOrEntriesNodeGen(context, builtin, entries, arguments);
        }
    }

    @GeneratedBy(value=ObjectFunctionBuiltins.AssignPropertiesNode.class)
    static final class AssignPropertiesNodeGen
    extends ObjectFunctionBuiltins.AssignPropertiesNode
    implements Introspection.Provider {
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @CompilerDirectives.CompilationFinal
        private volatile int exclude_;
        @Node.Child
        private CopyPropertiesFromJSObjectData copyPropertiesFromJSObject_cache;
        @Node.Child
        private Object0Data object0_cache;
        @Node.Child
        private InteropLibrary object1_keysInterop_;
        @Node.Child
        private InteropLibrary object1_stringInterop_;

        private AssignPropertiesNodeGen(JSContext context) {
            super(context);
        }

        @Override
        @ExplodeLoop
        void executeVoid(Object arg0Value, Object arg1Value, WriteElementNode arg2Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
                if ((state_0 & 1) != 0 && arg1Value instanceof JSDynamicObject) {
                    JSDynamicObject arg1Value_ = (JSDynamicObject)arg1Value;
                    CopyPropertiesFromJSObjectData s0_ = this.copyPropertiesFromJSObject_cache;
                    if (s0_ != null && JSGuards.isJSObject(arg1Value_)) {
                        ObjectFunctionBuiltins.AssignPropertiesNode.copyPropertiesFromJSObject(arg0Value, arg1Value_, arg2Value, s0_.read_, s0_.getOwnProperty_, s0_.listSize_, s0_.listGet_, s0_.classProfile_);
                        return;
                    }
                }
                if ((state_0 & 6) != 0) {
                    if ((state_0 & 2) != 0) {
                        Object0Data s1_ = this.object0_cache;
                        while (s1_ != null) {
                            if (s1_.fromInterop_.accepts(arg1Value) && !JSGuards.isJSObject(arg1Value)) {
                                this.doObject(arg0Value, arg1Value, arg2Value, s1_.fromInterop_, s1_.keysInterop_, s1_.stringInterop_);
                                return;
                            }
                            s1_ = s1_.next_;
                        }
                    }
                    if ((state_0 & 4) != 0 && !JSGuards.isJSObject(arg1Value)) {
                        this.object1Boundary(state_0, arg0Value, arg1Value, arg2Value);
                        return;
                    }
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.executeAndSpecialize(arg0Value, arg1Value, arg2Value);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @CompilerDirectives.TruffleBoundary
        private void object1Boundary(int state_0, Object arg0Value, Object arg1Value, WriteElementNode arg2Value) {
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this);
            try {
                InteropLibrary object1_fromInterop__ = INTEROP_LIBRARY_.getUncached(arg1Value);
                this.doObject(arg0Value, arg1Value, arg2Value, object1_fromInterop__, this.object1_keysInterop_, this.object1_stringInterop_);
                return;
            }
            finally {
                encapsulating_.set(prev_);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private void executeAndSpecialize(Object arg0Value, Object arg1Value, WriteElementNode arg2Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                JSDynamicObject arg1Value_;
                int state_0 = this.state_0_;
                int exclude = this.exclude_;
                if (arg1Value instanceof JSDynamicObject && JSGuards.isJSObject(arg1Value_ = (JSDynamicObject)arg1Value)) {
                    CopyPropertiesFromJSObjectData s0_ = super.insert(new CopyPropertiesFromJSObjectData());
                    s0_.read_ = s0_.insertAccessor(ReadElementNode.create(this.context));
                    s0_.getOwnProperty_ = s0_.insertAccessor(JSGetOwnPropertyNode.create(false));
                    s0_.listSize_ = s0_.insertAccessor(ListSizeNode.create());
                    s0_.listGet_ = s0_.insertAccessor(ListGetNode.create());
                    s0_.classProfile_ = JSClassProfile.create();
                    VarHandle.storeStoreFence();
                    this.copyPropertiesFromJSObject_cache = s0_;
                    this.state_0_ = state_0 |= 1;
                    lock.unlock();
                    hasLock = false;
                    ObjectFunctionBuiltins.AssignPropertiesNode.copyPropertiesFromJSObject(arg0Value, arg1Value_, arg2Value, s0_.read_, s0_.getOwnProperty_, s0_.listSize_, s0_.listGet_, s0_.classProfile_);
                    return;
                }
                if (exclude == 0) {
                    int count1_ = 0;
                    Object0Data s1_ = this.object0_cache;
                    if ((state_0 & 2) != 0) {
                        while (s1_ != null && (!s1_.fromInterop_.accepts(arg1Value) || JSGuards.isJSObject(arg1Value))) {
                            s1_ = s1_.next_;
                            ++count1_;
                        }
                    }
                    if (s1_ == null && !JSGuards.isJSObject(arg1Value) && count1_ < 5) {
                        s1_ = super.insert(new Object0Data(this.object0_cache));
                        s1_.fromInterop_ = s1_.insertAccessor(INTEROP_LIBRARY_.create(arg1Value));
                        s1_.keysInterop_ = s1_.insertAccessor(INTEROP_LIBRARY_.createDispatched(5));
                        s1_.stringInterop_ = s1_.insertAccessor(INTEROP_LIBRARY_.createDispatched(5));
                        VarHandle.storeStoreFence();
                        this.object0_cache = s1_;
                        this.state_0_ = state_0 |= 2;
                    }
                    if (s1_ != null) {
                        lock.unlock();
                        hasLock = false;
                        this.doObject(arg0Value, arg1Value, arg2Value, s1_.fromInterop_, s1_.keysInterop_, s1_.stringInterop_);
                        return;
                    }
                }
                InteropLibrary object1_fromInterop__ = null;
                EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                Node prev_ = encapsulating_.set(this);
                try {
                    if (!JSGuards.isJSObject(arg1Value)) {
                        object1_fromInterop__ = INTEROP_LIBRARY_.getUncached(arg1Value);
                        this.object1_keysInterop_ = super.insert(INTEROP_LIBRARY_.createDispatched(5));
                        this.object1_stringInterop_ = super.insert(INTEROP_LIBRARY_.createDispatched(5));
                        this.exclude_ = exclude |= 1;
                        this.object0_cache = null;
                        state_0 &= 0xFFFFFFFD;
                        this.state_0_ = state_0 |= 4;
                        lock.unlock();
                        hasLock = false;
                        this.doObject(arg0Value, arg1Value, arg2Value, object1_fromInterop__, this.object1_keysInterop_, this.object1_stringInterop_);
                        return;
                    }
                }
                finally {
                    encapsulating_.set(prev_);
                }
                throw new UnsupportedSpecializationException(this, new Node[]{null, null, null}, arg0Value, arg1Value, arg2Value);
            }
            finally {
                if (hasLock) {
                    lock.unlock();
                }
            }
        }

        @Override
        public NodeCost getCost() {
            Object0Data s1_;
            int state_0 = this.state_0_;
            if (state_0 == 0) {
                return NodeCost.UNINITIALIZED;
            }
            if ((state_0 & state_0 - 1) == 0 && ((s1_ = this.object0_cache) == null || s1_.next_ == null)) {
                return NodeCost.MONOMORPHIC;
            }
            return NodeCost.POLYMORPHIC;
        }

        @Override
        public Introspection getIntrospectionData() {
            ArrayList<List<Cloneable>> cached;
            Object[] data = new Object[4];
            data[0] = 0;
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            Object[] s = new Object[3];
            s[0] = "copyPropertiesFromJSObject";
            if ((state_0 & 1) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList<List<Cloneable>>();
                CopyPropertiesFromJSObjectData s0_ = this.copyPropertiesFromJSObject_cache;
                if (s0_ != null) {
                    cached.add(Arrays.asList(s0_.read_, s0_.getOwnProperty_, s0_.listSize_, s0_.listGet_, s0_.classProfile_));
                }
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[1] = s;
            s = new Object[3];
            s[0] = "doObject";
            if ((state_0 & 2) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList();
                Object0Data s1_ = this.object0_cache;
                while (s1_ != null) {
                    cached.add(Arrays.asList(s1_.fromInterop_, s1_.keysInterop_, s1_.stringInterop_));
                    s1_ = s1_.next_;
                }
                s[2] = cached;
            } else {
                s[1] = exclude != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0);
            }
            data[2] = s;
            s = new Object[3];
            s[0] = "doObject";
            if ((state_0 & 4) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList();
                cached.add(Arrays.asList(this.object1_keysInterop_, this.object1_stringInterop_));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[3] = s;
            return Introspection.Provider.create(data);
        }

        public static ObjectFunctionBuiltins.AssignPropertiesNode create(JSContext context) {
            return new AssignPropertiesNodeGen(context);
        }

        @GeneratedBy(value=ObjectFunctionBuiltins.AssignPropertiesNode.class)
        private static final class Object0Data
        extends Node {
            @Node.Child
            Object0Data next_;
            @Node.Child
            InteropLibrary fromInterop_;
            @Node.Child
            InteropLibrary keysInterop_;
            @Node.Child
            InteropLibrary stringInterop_;

            Object0Data(Object0Data next_) {
                this.next_ = next_;
            }

            @Override
            public NodeCost getCost() {
                return NodeCost.NONE;
            }

            <T extends Node> T insertAccessor(T node) {
                return super.insert(node);
            }
        }

        @GeneratedBy(value=ObjectFunctionBuiltins.AssignPropertiesNode.class)
        private static final class CopyPropertiesFromJSObjectData
        extends Node {
            @Node.Child
            ReadElementNode read_;
            @Node.Child
            JSGetOwnPropertyNode getOwnProperty_;
            @Node.Child
            ListSizeNode listSize_;
            @Node.Child
            ListGetNode listGet_;
            @CompilerDirectives.CompilationFinal
            JSClassProfile classProfile_;

            CopyPropertiesFromJSObjectData() {
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

    @GeneratedBy(value=ObjectFunctionBuiltins.ObjectAssignNode.class)
    public static final class ObjectAssignNodeGen
    extends ObjectFunctionBuiltins.ObjectAssignNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private AssignData assign_cache;

        private ObjectAssignNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
                AssignData s0_ = this.assign_cache;
                if (s0_ != null) {
                    return this.assign(arguments0Value_, arguments1Value__, s0_.toObjectNode_, s0_.write_, s0_.assignProperties_);
                }
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
                if (arguments1Value instanceof Object[]) {
                    Object[] arguments1Value_ = (Object[])arguments1Value;
                    AssignData s0_ = super.insert(new AssignData());
                    s0_.toObjectNode_ = s0_.insertAccessor(JSToObjectNode.createToObject(this.getContext()));
                    s0_.write_ = s0_.insertAccessor(WriteElementNode.create(this.getContext(), true));
                    s0_.assignProperties_ = s0_.insertAccessor(AssignPropertiesNodeGen.create(this.getContext()));
                    VarHandle.storeStoreFence();
                    this.assign_cache = s0_;
                    this.state_0_ = state_0 |= 1;
                    lock.unlock();
                    hasLock = false;
                    Object object = this.assign(arguments0Value, arguments1Value_, s0_.toObjectNode_, s0_.write_, s0_.assignProperties_);
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
            return NodeCost.MONOMORPHIC;
        }

        @Override
        public Introspection getIntrospectionData() {
            Object[] data = new Object[2];
            data[0] = 0;
            int state_0 = this.state_0_;
            Object[] s = new Object[3];
            s[0] = "assign";
            if (state_0 != 0) {
                s[1] = (byte)1;
                ArrayList<List<JavaScriptBaseNode>> cached = new ArrayList<List<JavaScriptBaseNode>>();
                AssignData s0_ = this.assign_cache;
                if (s0_ != null) {
                    cached.add(Arrays.asList(s0_.toObjectNode_, s0_.write_, s0_.assignProperties_));
                }
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static ObjectFunctionBuiltins.ObjectAssignNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new ObjectAssignNodeGen(context, builtin, arguments);
        }

        @GeneratedBy(value=ObjectFunctionBuiltins.ObjectAssignNode.class)
        private static final class AssignData
        extends Node {
            @Node.Child
            JSToObjectNode toObjectNode_;
            @Node.Child
            WriteElementNode write_;
            @Node.Child
            ObjectFunctionBuiltins.AssignPropertiesNode assignProperties_;

            AssignData() {
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

    @GeneratedBy(value=ObjectFunctionBuiltins.ObjectIsNode.class)
    public static final class ObjectIsNodeGen
    extends ObjectFunctionBuiltins.ObjectIsNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private JSIdenticalNode isNumberNumber_doIdenticalNode_;
        @Node.Child
        private JSIdenticalNode isObject_doIdenticalNode_;

        private ObjectIsNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            if ((state_0 & 0xE) == 0 && (state_0 & 0xF) != 0) {
                return this.execute_int_int0(state_0, frameValue);
            }
            if ((state_0 & 0xD) == 0 && (state_0 & 0xF) != 0) {
                return this.execute_double_double1(state_0, frameValue);
            }
            return this.execute_generic2(state_0, frameValue);
        }

        private Object execute_int_int0(int state_0, VirtualFrame frameValue) {
            int arguments1Value_;
            int arguments0Value_;
            try {
                arguments0Value_ = this.arguments0_.executeInt(frameValue);
            }
            catch (UnexpectedResultException ex) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                Object arguments1Value = this.arguments1_.execute(frameValue);
                return this.executeAndSpecialize(ex.getResult(), arguments1Value);
            }
            try {
                arguments1Value_ = this.arguments1_.executeInt(frameValue);
            }
            catch (UnexpectedResultException ex) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.executeAndSpecialize(arguments0Value_, ex.getResult());
            }
            assert ((state_0 & 1) != 0);
            return this.isInt(arguments0Value_, arguments1Value_);
        }

        private Object execute_double_double1(int state_0, VirtualFrame frameValue) {
            double arguments1Value_;
            double arguments0Value_;
            long arguments0Value_long = 0L;
            int arguments0Value_int = 0;
            try {
                if ((state_0 & 0xE0) == 0 && (state_0 & 0xF) != 0) {
                    arguments0Value_ = this.arguments0_.executeDouble(frameValue);
                } else if ((state_0 & 0xD0) == 0 && (state_0 & 0xF) != 0) {
                    arguments0Value_int = this.arguments0_.executeInt(frameValue);
                    arguments0Value_ = JSTypes.intToDouble(arguments0Value_int);
                } else if ((state_0 & 0x70) == 0 && (state_0 & 0xF) != 0) {
                    arguments0Value_long = this.arguments0_.executeLong(frameValue);
                    arguments0Value_ = JSTypes.longToDouble(arguments0Value_long);
                } else {
                    Object arguments0Value__ = this.arguments0_.execute(frameValue);
                    arguments0Value_ = JSTypesGen.expectImplicitDouble((state_0 & 0xF0) >>> 4, arguments0Value__);
                }
            }
            catch (UnexpectedResultException ex) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                Object arguments1Value = this.arguments1_.execute(frameValue);
                return this.executeAndSpecialize(ex.getResult(), arguments1Value);
            }
            long arguments1Value_long = 0L;
            int arguments1Value_int = 0;
            try {
                if ((state_0 & 0xE00) == 0 && (state_0 & 0xF) != 0) {
                    arguments1Value_ = this.arguments1_.executeDouble(frameValue);
                } else if ((state_0 & 0xD00) == 0 && (state_0 & 0xF) != 0) {
                    arguments1Value_int = this.arguments1_.executeInt(frameValue);
                    arguments1Value_ = JSTypes.intToDouble(arguments1Value_int);
                } else if ((state_0 & 0x700) == 0 && (state_0 & 0xF) != 0) {
                    arguments1Value_long = this.arguments1_.executeLong(frameValue);
                    arguments1Value_ = JSTypes.longToDouble(arguments1Value_long);
                } else {
                    Object arguments1Value__ = this.arguments1_.execute(frameValue);
                    arguments1Value_ = JSTypesGen.expectImplicitDouble((state_0 & 0xF00) >>> 8, arguments1Value__);
                }
            }
            catch (UnexpectedResultException ex) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.executeAndSpecialize((state_0 & 0xD0) == 0 && (state_0 & 0xF) != 0 ? (Number)arguments0Value_int : (Number)((state_0 & 0x70) == 0 && (state_0 & 0xF) != 0 ? (Number)arguments0Value_long : (Number)arguments0Value_), ex.getResult());
            }
            assert ((state_0 & 2) != 0);
            return this.isDouble(arguments0Value_, arguments1Value_);
        }

        private Object execute_generic2(int state_0, VirtualFrame frameValue) {
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            if ((state_0 & 1) != 0 && arguments0Value_ instanceof Integer) {
                int arguments0Value__ = (Integer)arguments0Value_;
                if (arguments1Value_ instanceof Integer) {
                    int arguments1Value__ = (Integer)arguments1Value_;
                    return this.isInt(arguments0Value__, arguments1Value__);
                }
            }
            if ((state_0 & 2) != 0 && JSTypesGen.isImplicitDouble((state_0 & 0xF0) >>> 4, arguments0Value_)) {
                double arguments0Value__ = JSTypesGen.asImplicitDouble((state_0 & 0xF0) >>> 4, arguments0Value_);
                if (JSTypesGen.isImplicitDouble((state_0 & 0xF00) >>> 8, arguments1Value_)) {
                    double arguments1Value__ = JSTypesGen.asImplicitDouble((state_0 & 0xF00) >>> 8, arguments1Value_);
                    return this.isDouble(arguments0Value__, arguments1Value__);
                }
            }
            if ((state_0 & 4) != 0 && arguments0Value_ instanceof Number) {
                Number arguments1Value__;
                Number arguments0Value__ = (Number)arguments0Value_;
                if (arguments1Value_ instanceof Number && this.isNumberNumber(arguments0Value__, arguments1Value__ = (Number)arguments1Value_)) {
                    return this.isNumberNumber(arguments0Value__, arguments1Value__, this.isNumberNumber_doIdenticalNode_);
                }
            }
            if ((state_0 & 8) != 0 && !this.isNumberNumber(arguments0Value_, arguments1Value_)) {
                return this.isObject(arguments0Value_, arguments1Value_, this.isObject_doIdenticalNode_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
        }

        @Override
        public boolean executeBoolean(VirtualFrame frameValue) {
            int state_0 = this.state_0_;
            if ((state_0 & 0xE) == 0 && (state_0 & 0xF) != 0) {
                return this.executeBoolean_int_int3(state_0, frameValue);
            }
            if ((state_0 & 0xD) == 0 && (state_0 & 0xF) != 0) {
                return this.executeBoolean_double_double4(state_0, frameValue);
            }
            return this.executeBoolean_generic5(state_0, frameValue);
        }

        private boolean executeBoolean_int_int3(int state_0, VirtualFrame frameValue) {
            int arguments1Value_;
            int arguments0Value_;
            try {
                arguments0Value_ = this.arguments0_.executeInt(frameValue);
            }
            catch (UnexpectedResultException ex) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                Object arguments1Value = this.arguments1_.execute(frameValue);
                return this.executeAndSpecialize(ex.getResult(), arguments1Value);
            }
            try {
                arguments1Value_ = this.arguments1_.executeInt(frameValue);
            }
            catch (UnexpectedResultException ex) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.executeAndSpecialize(arguments0Value_, ex.getResult());
            }
            assert ((state_0 & 1) != 0);
            return this.isInt(arguments0Value_, arguments1Value_);
        }

        private boolean executeBoolean_double_double4(int state_0, VirtualFrame frameValue) {
            double arguments1Value_;
            double arguments0Value_;
            long arguments0Value_long = 0L;
            int arguments0Value_int = 0;
            try {
                if ((state_0 & 0xE0) == 0 && (state_0 & 0xF) != 0) {
                    arguments0Value_ = this.arguments0_.executeDouble(frameValue);
                } else if ((state_0 & 0xD0) == 0 && (state_0 & 0xF) != 0) {
                    arguments0Value_int = this.arguments0_.executeInt(frameValue);
                    arguments0Value_ = JSTypes.intToDouble(arguments0Value_int);
                } else if ((state_0 & 0x70) == 0 && (state_0 & 0xF) != 0) {
                    arguments0Value_long = this.arguments0_.executeLong(frameValue);
                    arguments0Value_ = JSTypes.longToDouble(arguments0Value_long);
                } else {
                    Object arguments0Value__ = this.arguments0_.execute(frameValue);
                    arguments0Value_ = JSTypesGen.expectImplicitDouble((state_0 & 0xF0) >>> 4, arguments0Value__);
                }
            }
            catch (UnexpectedResultException ex) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                Object arguments1Value = this.arguments1_.execute(frameValue);
                return this.executeAndSpecialize(ex.getResult(), arguments1Value);
            }
            long arguments1Value_long = 0L;
            int arguments1Value_int = 0;
            try {
                if ((state_0 & 0xE00) == 0 && (state_0 & 0xF) != 0) {
                    arguments1Value_ = this.arguments1_.executeDouble(frameValue);
                } else if ((state_0 & 0xD00) == 0 && (state_0 & 0xF) != 0) {
                    arguments1Value_int = this.arguments1_.executeInt(frameValue);
                    arguments1Value_ = JSTypes.intToDouble(arguments1Value_int);
                } else if ((state_0 & 0x700) == 0 && (state_0 & 0xF) != 0) {
                    arguments1Value_long = this.arguments1_.executeLong(frameValue);
                    arguments1Value_ = JSTypes.longToDouble(arguments1Value_long);
                } else {
                    Object arguments1Value__ = this.arguments1_.execute(frameValue);
                    arguments1Value_ = JSTypesGen.expectImplicitDouble((state_0 & 0xF00) >>> 8, arguments1Value__);
                }
            }
            catch (UnexpectedResultException ex) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.executeAndSpecialize((state_0 & 0xD0) == 0 && (state_0 & 0xF) != 0 ? (Number)arguments0Value_int : (Number)((state_0 & 0x70) == 0 && (state_0 & 0xF) != 0 ? (Number)arguments0Value_long : (Number)arguments0Value_), ex.getResult());
            }
            assert ((state_0 & 2) != 0);
            return this.isDouble(arguments0Value_, arguments1Value_);
        }

        private boolean executeBoolean_generic5(int state_0, VirtualFrame frameValue) {
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            if ((state_0 & 1) != 0 && arguments0Value_ instanceof Integer) {
                int arguments0Value__ = (Integer)arguments0Value_;
                if (arguments1Value_ instanceof Integer) {
                    int arguments1Value__ = (Integer)arguments1Value_;
                    return this.isInt(arguments0Value__, arguments1Value__);
                }
            }
            if ((state_0 & 2) != 0 && JSTypesGen.isImplicitDouble((state_0 & 0xF0) >>> 4, arguments0Value_)) {
                double arguments0Value__ = JSTypesGen.asImplicitDouble((state_0 & 0xF0) >>> 4, arguments0Value_);
                if (JSTypesGen.isImplicitDouble((state_0 & 0xF00) >>> 8, arguments1Value_)) {
                    double arguments1Value__ = JSTypesGen.asImplicitDouble((state_0 & 0xF00) >>> 8, arguments1Value_);
                    return this.isDouble(arguments0Value__, arguments1Value__);
                }
            }
            if ((state_0 & 4) != 0 && arguments0Value_ instanceof Number) {
                Number arguments1Value__;
                Number arguments0Value__ = (Number)arguments0Value_;
                if (arguments1Value_ instanceof Number && this.isNumberNumber(arguments0Value__, arguments1Value__ = (Number)arguments1Value_)) {
                    return this.isNumberNumber(arguments0Value__, arguments1Value__, this.isNumberNumber_doIdenticalNode_);
                }
            }
            if ((state_0 & 8) != 0 && !this.isNumberNumber(arguments0Value_, arguments1Value_)) {
                return this.isObject(arguments0Value_, arguments1Value_, this.isObject_doIdenticalNode_);
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
                int doubleCast0;
                int state_0 = this.state_0_;
                if (arguments0Value instanceof Integer) {
                    int arguments0Value_ = (Integer)arguments0Value;
                    if (arguments1Value instanceof Integer) {
                        int arguments1Value_ = (Integer)arguments1Value;
                        this.state_0_ = state_0 |= 1;
                        lock.unlock();
                        hasLock = false;
                        boolean bl = this.isInt(arguments0Value_, arguments1Value_);
                        return bl;
                    }
                }
                if ((doubleCast0 = JSTypesGen.specializeImplicitDouble(arguments0Value)) != 0) {
                    double arguments0Value_ = JSTypesGen.asImplicitDouble(doubleCast0, arguments0Value);
                    int doubleCast1 = JSTypesGen.specializeImplicitDouble(arguments1Value);
                    if (doubleCast1 != 0) {
                        double arguments1Value_ = JSTypesGen.asImplicitDouble(doubleCast1, arguments1Value);
                        state_0 |= doubleCast0 << 4;
                        state_0 |= doubleCast1 << 8;
                        this.state_0_ = state_0 |= 2;
                        lock.unlock();
                        hasLock = false;
                        boolean bl = this.isDouble(arguments0Value_, arguments1Value_);
                        return bl;
                    }
                }
                if (arguments0Value instanceof Number) {
                    Number arguments1Value_;
                    Number arguments0Value_ = (Number)arguments0Value;
                    if (arguments1Value instanceof Number && this.isNumberNumber(arguments0Value_, arguments1Value_ = (Number)arguments1Value)) {
                        this.isNumberNumber_doIdenticalNode_ = super.insert(JSIdenticalNode.createSameValue());
                        this.state_0_ = state_0 |= 4;
                        lock.unlock();
                        hasLock = false;
                        boolean bl = this.isNumberNumber(arguments0Value_, arguments1Value_, this.isNumberNumber_doIdenticalNode_);
                        return bl;
                    }
                }
                if (!this.isNumberNumber(arguments0Value, arguments1Value)) {
                    this.isObject_doIdenticalNode_ = super.insert(JSIdenticalNode.createSameValue());
                    this.state_0_ = state_0 |= 8;
                    lock.unlock();
                    hasLock = false;
                    boolean bl = this.isObject(arguments0Value, arguments1Value, this.isObject_doIdenticalNode_);
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
            if ((state_0 & 0xF) == 0) {
                return NodeCost.UNINITIALIZED;
            }
            if ((state_0 & 0xF & (state_0 & 0xF) - 1) == 0) {
                return NodeCost.MONOMORPHIC;
            }
            return NodeCost.POLYMORPHIC;
        }

        @Override
        public Introspection getIntrospectionData() {
            ArrayList<List<JSIdenticalNode>> cached;
            Object[] data = new Object[5];
            data[0] = 0;
            int state_0 = this.state_0_;
            Object[] s = new Object[3];
            s[0] = "isInt";
            s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[1] = s;
            s = new Object[3];
            s[0] = "isDouble";
            s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[2] = s;
            s = new Object[3];
            s[0] = "isNumberNumber";
            if ((state_0 & 4) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList<List<JSIdenticalNode>>();
                cached.add(Arrays.asList(this.isNumberNumber_doIdenticalNode_));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[3] = s;
            s = new Object[3];
            s[0] = "isObject";
            if ((state_0 & 8) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList();
                cached.add(Arrays.asList(this.isObject_doIdenticalNode_));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[4] = s;
            return Introspection.Provider.create(data);
        }

        public static ObjectFunctionBuiltins.ObjectIsNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new ObjectIsNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=ObjectFunctionBuiltins.ObjectSetPrototypeOfNode.class)
    public static final class ObjectSetPrototypeOfNodeGen
    extends ObjectFunctionBuiltins.ObjectSetPrototypeOfNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @CompilerDirectives.CompilationFinal
        private int state_0_;

        private ObjectSetPrototypeOfNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            if ((state_0 & 3) != 0 && arguments0Value_ instanceof JSObject) {
                JSDynamicObject arguments1Value__;
                JSObject arguments0Value__ = (JSObject)arguments0Value_;
                if ((state_0 & 1) != 0 && arguments1Value_ instanceof JSDynamicObject && JSGuards.isValidPrototype(arguments1Value__ = (JSDynamicObject)arguments1Value_)) {
                    return this.setPrototypeOfJSObject(arguments0Value__, arguments1Value__);
                }
                if ((state_0 & 2) != 0 && !JSGuards.isValidPrototype(arguments1Value_)) {
                    return ObjectFunctionBuiltins.ObjectSetPrototypeOfNode.setPrototypeOfJSObjectToInvalidNewProto(arguments0Value__, arguments1Value_);
                }
            }
            if ((state_0 & 0x1C) != 0) {
                if ((state_0 & 4) != 0 && JSGuards.isNullOrUndefined(arguments0Value_)) {
                    return this.setPrototypeOfNonObjectCoercible(arguments0Value_, arguments1Value_);
                }
                if (!((state_0 & 8) == 0 || JSGuards.isJSObject(arguments0Value_) || JSGuards.isNullOrUndefined(arguments0Value_) || JSGuards.isForeignObject(arguments0Value_))) {
                    return ObjectFunctionBuiltins.ObjectSetPrototypeOfNode.setPrototypeOfValue(arguments0Value_, arguments1Value_);
                }
                if ((state_0 & 0x10) != 0 && JSGuards.isForeignObject(arguments0Value_)) {
                    return this.setPrototypeOfForeignObject(arguments0Value_, arguments1Value_);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.execute(frameValue);
        }

        private Object executeAndSpecialize(Object arguments0Value, Object arguments1Value) {
            int state_0 = this.state_0_;
            if (arguments0Value instanceof JSObject) {
                JSDynamicObject arguments1Value_;
                JSObject arguments0Value_ = (JSObject)arguments0Value;
                if (arguments1Value instanceof JSDynamicObject && JSGuards.isValidPrototype(arguments1Value_ = (JSDynamicObject)arguments1Value)) {
                    this.state_0_ = state_0 |= 1;
                    return this.setPrototypeOfJSObject(arguments0Value_, arguments1Value_);
                }
                if (!JSGuards.isValidPrototype(arguments1Value)) {
                    this.state_0_ = state_0 |= 2;
                    return ObjectFunctionBuiltins.ObjectSetPrototypeOfNode.setPrototypeOfJSObjectToInvalidNewProto(arguments0Value_, arguments1Value);
                }
            }
            if (JSGuards.isNullOrUndefined(arguments0Value)) {
                this.state_0_ = state_0 |= 4;
                return this.setPrototypeOfNonObjectCoercible(arguments0Value, arguments1Value);
            }
            if (!(JSGuards.isJSObject(arguments0Value) || JSGuards.isNullOrUndefined(arguments0Value) || JSGuards.isForeignObject(arguments0Value))) {
                this.state_0_ = state_0 |= 8;
                return ObjectFunctionBuiltins.ObjectSetPrototypeOfNode.setPrototypeOfValue(arguments0Value, arguments1Value);
            }
            if (JSGuards.isForeignObject(arguments0Value)) {
                this.state_0_ = state_0 |= 0x10;
                return this.setPrototypeOfForeignObject(arguments0Value, arguments1Value);
            }
            throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_, this.arguments1_}, arguments0Value, arguments1Value);
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
            Object[] data = new Object[6];
            data[0] = 0;
            int state_0 = this.state_0_;
            Object[] s = new Object[3];
            s[0] = "setPrototypeOfJSObject";
            s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[1] = s;
            s = new Object[3];
            s[0] = "setPrototypeOfJSObjectToInvalidNewProto";
            s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[2] = s;
            s = new Object[3];
            s[0] = "setPrototypeOfNonObjectCoercible";
            s[1] = (state_0 & 4) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[3] = s;
            s = new Object[3];
            s[0] = "setPrototypeOfValue";
            s[1] = (state_0 & 8) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[4] = s;
            s = new Object[3];
            s[0] = "setPrototypeOfForeignObject";
            s[1] = (state_0 & 0x10) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[5] = s;
            return Introspection.Provider.create(data);
        }

        public static ObjectFunctionBuiltins.ObjectSetPrototypeOfNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new ObjectSetPrototypeOfNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=ObjectFunctionBuiltins.ObjectKeysNode.class)
    public static final class ObjectKeysNodeGen
    extends ObjectFunctionBuiltins.ObjectKeysNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @CompilerDirectives.CompilationFinal
        private int state_0_;

        private ObjectKeysNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            super(context, builtin);
            this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
        }

        @Override
        public JavaScriptNode[] getArguments() {
            return new JavaScriptNode[]{this.arguments0_};
        }

        @Override
        public Object execute(VirtualFrame frameValue) {
            Object arguments0Value__;
            int state_0 = this.state_0_;
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSDynamicObject && JSGuards.isJSDynamicObject(arguments0Value__ = (JSDynamicObject)arguments0Value_)) {
                return this.keysDynamicObject((JSDynamicObject)arguments0Value__);
            }
            if ((state_0 & 2) != 0 && arguments0Value_ instanceof Symbol) {
                arguments0Value__ = (Symbol)arguments0Value_;
                return this.keysSymbol((Symbol)arguments0Value__);
            }
            if ((state_0 & 4) != 0 && arguments0Value_ instanceof TruffleString) {
                arguments0Value__ = (TruffleString)arguments0Value_;
                return this.keysString((TruffleString)arguments0Value__);
            }
            if ((state_0 & 8) != 0 && arguments0Value_ instanceof SafeInteger) {
                arguments0Value__ = (SafeInteger)arguments0Value_;
                return this.keysSafeInt((SafeInteger)arguments0Value__);
            }
            if ((state_0 & 0x10) != 0 && arguments0Value_ instanceof BigInt) {
                arguments0Value__ = (BigInt)arguments0Value_;
                return this.keysBigInt((BigInt)arguments0Value__);
            }
            if ((state_0 & 0x60) != 0) {
                if ((state_0 & 0x20) != 0 && !JSGuards.isTruffleObject(arguments0Value_)) {
                    return this.keysOther(arguments0Value_);
                }
                if ((state_0 & 0x40) != 0 && JSGuards.isForeignObject(arguments0Value_)) {
                    return this.keysForeign(arguments0Value_);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.execute(frameValue);
        }

        private JSDynamicObject executeAndSpecialize(Object arguments0Value) {
            Object arguments0Value_;
            int state_0 = this.state_0_;
            if (arguments0Value instanceof JSDynamicObject && JSGuards.isJSDynamicObject(arguments0Value_ = (JSDynamicObject)arguments0Value)) {
                this.state_0_ = state_0 |= 1;
                return this.keysDynamicObject((JSDynamicObject)arguments0Value_);
            }
            if (arguments0Value instanceof Symbol) {
                arguments0Value_ = (Symbol)arguments0Value;
                this.state_0_ = state_0 |= 2;
                return this.keysSymbol((Symbol)arguments0Value_);
            }
            if (arguments0Value instanceof TruffleString) {
                arguments0Value_ = (TruffleString)arguments0Value;
                this.state_0_ = state_0 |= 4;
                return this.keysString((TruffleString)arguments0Value_);
            }
            if (arguments0Value instanceof SafeInteger) {
                arguments0Value_ = (SafeInteger)arguments0Value;
                this.state_0_ = state_0 |= 8;
                return this.keysSafeInt((SafeInteger)arguments0Value_);
            }
            if (arguments0Value instanceof BigInt) {
                arguments0Value_ = (BigInt)arguments0Value;
                this.state_0_ = state_0 |= 0x10;
                return this.keysBigInt((BigInt)arguments0Value_);
            }
            if (!JSGuards.isTruffleObject(arguments0Value)) {
                this.state_0_ = state_0 |= 0x20;
                return this.keysOther(arguments0Value);
            }
            if (JSGuards.isForeignObject(arguments0Value)) {
                this.state_0_ = state_0 |= 0x40;
                return this.keysForeign(arguments0Value);
            }
            throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_}, arguments0Value);
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
            Object[] data = new Object[8];
            data[0] = 0;
            int state_0 = this.state_0_;
            Object[] s = new Object[3];
            s[0] = "keysDynamicObject";
            s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[1] = s;
            s = new Object[3];
            s[0] = "keysSymbol";
            s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[2] = s;
            s = new Object[3];
            s[0] = "keysString";
            s[1] = (state_0 & 4) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[3] = s;
            s = new Object[3];
            s[0] = "keysSafeInt";
            s[1] = (state_0 & 8) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[4] = s;
            s = new Object[3];
            s[0] = "keysBigInt";
            s[1] = (state_0 & 0x10) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[5] = s;
            s = new Object[3];
            s[0] = "keysOther";
            s[1] = (state_0 & 0x20) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[6] = s;
            s = new Object[3];
            s[0] = "keysForeign";
            s[1] = (state_0 & 0x40) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[7] = s;
            return Introspection.Provider.create(data);
        }

        public static ObjectFunctionBuiltins.ObjectKeysNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new ObjectKeysNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=ObjectFunctionBuiltins.ObjectSetIntegrityLevelNode.class)
    public static final class ObjectSetIntegrityLevelNodeGen
    extends ObjectFunctionBuiltins.ObjectSetIntegrityLevelNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;

        private ObjectSetIntegrityLevelNodeGen(JSContext context, JSBuiltin builtin, boolean freeze, JavaScriptNode[] arguments) {
            super(context, builtin, freeze);
            this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
        }

        @Override
        public JavaScriptNode[] getArguments() {
            return new JavaScriptNode[]{this.arguments0_};
        }

        @Override
        public Object execute(VirtualFrame frameValue) {
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            return this.setIntegrityLevel(arguments0Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.execute(frameValue);
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
            s[0] = "setIntegrityLevel";
            s[1] = (byte)1;
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static ObjectFunctionBuiltins.ObjectSetIntegrityLevelNode create(JSContext context, JSBuiltin builtin, boolean freeze, JavaScriptNode[] arguments) {
            return new ObjectSetIntegrityLevelNodeGen(context, builtin, freeze, arguments);
        }
    }

    @GeneratedBy(value=ObjectFunctionBuiltins.ObjectTestIntegrityLevelNode.class)
    public static final class ObjectTestIntegrityLevelNodeGen
    extends ObjectFunctionBuiltins.ObjectTestIntegrityLevelNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;

        private ObjectTestIntegrityLevelNodeGen(JSContext context, JSBuiltin builtin, boolean frozen, JavaScriptNode[] arguments) {
            super(context, builtin, frozen);
            this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
        }

        @Override
        public JavaScriptNode[] getArguments() {
            return new JavaScriptNode[]{this.arguments0_};
        }

        @Override
        public Object execute(VirtualFrame frameValue) {
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            return this.testIntegrityLevel(arguments0Value_);
        }

        @Override
        public boolean executeBoolean(VirtualFrame frameValue) {
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            return this.testIntegrityLevel(arguments0Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.executeBoolean(frameValue);
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
            s[0] = "testIntegrityLevel";
            s[1] = (byte)1;
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static ObjectFunctionBuiltins.ObjectTestIntegrityLevelNode create(JSContext context, JSBuiltin builtin, boolean frozen, JavaScriptNode[] arguments) {
            return new ObjectTestIntegrityLevelNodeGen(context, builtin, frozen, arguments);
        }
    }

    @GeneratedBy(value=ObjectFunctionBuiltins.ObjectPreventExtensionsNode.class)
    public static final class ObjectPreventExtensionsNodeGen
    extends ObjectFunctionBuiltins.ObjectPreventExtensionsNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @CompilerDirectives.CompilationFinal
        private int state_0_;

        private ObjectPreventExtensionsNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            super(context, builtin);
            this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
        }

        @Override
        public JavaScriptNode[] getArguments() {
            return new JavaScriptNode[]{this.arguments0_};
        }

        @Override
        public Object execute(VirtualFrame frameValue) {
            JSDynamicObject arguments0Value__;
            int state_0 = this.state_0_;
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSDynamicObject && JSGuards.isJSObject(arguments0Value__ = (JSDynamicObject)arguments0Value_)) {
                return this.preventExtensionsObject(arguments0Value__);
            }
            if ((state_0 & 2) != 0 && !JSGuards.isJSObject(arguments0Value_)) {
                return this.preventExtensionsNonObject(arguments0Value_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.execute(frameValue);
        }

        private Object executeAndSpecialize(Object arguments0Value) {
            JSDynamicObject arguments0Value_;
            int state_0 = this.state_0_;
            if (arguments0Value instanceof JSDynamicObject && JSGuards.isJSObject(arguments0Value_ = (JSDynamicObject)arguments0Value)) {
                this.state_0_ = state_0 |= 1;
                return this.preventExtensionsObject(arguments0Value_);
            }
            if (!JSGuards.isJSObject(arguments0Value)) {
                this.state_0_ = state_0 |= 2;
                return this.preventExtensionsNonObject(arguments0Value);
            }
            throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_}, arguments0Value);
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
            s[0] = "preventExtensionsObject";
            s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[1] = s;
            s = new Object[3];
            s[0] = "preventExtensionsNonObject";
            s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[2] = s;
            return Introspection.Provider.create(data);
        }

        public static ObjectFunctionBuiltins.ObjectPreventExtensionsNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new ObjectPreventExtensionsNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=ObjectFunctionBuiltins.ObjectIsExtensibleNode.class)
    public static final class ObjectIsExtensibleNodeGen
    extends ObjectFunctionBuiltins.ObjectIsExtensibleNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private IsExtensibleNode isExtensibleObject_isExtensibleNode_;

        private ObjectIsExtensibleNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            super(context, builtin);
            this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
        }

        @Override
        public JavaScriptNode[] getArguments() {
            return new JavaScriptNode[]{this.arguments0_};
        }

        @Override
        public Object execute(VirtualFrame frameValue) {
            JSDynamicObject arguments0Value__;
            int state_0 = this.state_0_;
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSDynamicObject && JSGuards.isJSObject(arguments0Value__ = (JSDynamicObject)arguments0Value_)) {
                return this.isExtensibleObject(arguments0Value__, this.isExtensibleObject_isExtensibleNode_);
            }
            if ((state_0 & 2) != 0 && !JSGuards.isJSObject(arguments0Value_)) {
                return this.isExtensibleNonObject(arguments0Value_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_);
        }

        @Override
        public boolean executeBoolean(VirtualFrame frameValue) {
            JSDynamicObject arguments0Value__;
            int state_0 = this.state_0_;
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSDynamicObject && JSGuards.isJSObject(arguments0Value__ = (JSDynamicObject)arguments0Value_)) {
                return this.isExtensibleObject(arguments0Value__, this.isExtensibleObject_isExtensibleNode_);
            }
            if ((state_0 & 2) != 0 && !JSGuards.isJSObject(arguments0Value_)) {
                return this.isExtensibleNonObject(arguments0Value_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.executeBoolean(frameValue);
        }

        private boolean executeAndSpecialize(Object arguments0Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                JSDynamicObject arguments0Value_;
                int state_0 = this.state_0_;
                if (arguments0Value instanceof JSDynamicObject && JSGuards.isJSObject(arguments0Value_ = (JSDynamicObject)arguments0Value)) {
                    this.isExtensibleObject_isExtensibleNode_ = super.insert(IsExtensibleNode.create());
                    this.state_0_ = state_0 |= 1;
                    lock.unlock();
                    hasLock = false;
                    boolean bl = this.isExtensibleObject(arguments0Value_, this.isExtensibleObject_isExtensibleNode_);
                    return bl;
                }
                if (!JSGuards.isJSObject(arguments0Value)) {
                    this.state_0_ = state_0 |= 2;
                    lock.unlock();
                    hasLock = false;
                    boolean bl = this.isExtensibleNonObject(arguments0Value);
                    return bl;
                }
                throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_}, arguments0Value);
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
            s[0] = "isExtensibleObject";
            if ((state_0 & 1) != 0) {
                s[1] = (byte)1;
                ArrayList<List<IsExtensibleNode>> cached = new ArrayList<List<IsExtensibleNode>>();
                cached.add(Arrays.asList(this.isExtensibleObject_isExtensibleNode_));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[1] = s;
            s = new Object[3];
            s[0] = "isExtensibleNonObject";
            s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[2] = s;
            return Introspection.Provider.create(data);
        }

        public static ObjectFunctionBuiltins.ObjectIsExtensibleNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new ObjectIsExtensibleNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=ObjectFunctionBuiltins.ObjectDefinePropertiesNode.class)
    public static final class ObjectDefinePropertiesNodeGen
    extends ObjectFunctionBuiltins.ObjectDefinePropertiesNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @CompilerDirectives.CompilationFinal
        private int state_0_;
        @CompilerDirectives.CompilationFinal
        private int exclude_;

        private ObjectDefinePropertiesNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSDynamicObject) {
                JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
                if (arguments1Value_ instanceof JSDynamicObject) {
                    JSDynamicObject arguments1Value__ = (JSDynamicObject)arguments1Value_;
                    if (JSGuards.isJSObject(arguments0Value__) && JSGuards.isJSObject(arguments1Value__)) {
                        return this.definePropertiesObjectObject(arguments0Value__, arguments1Value__);
                    }
                }
            }
            if ((state_0 & 2) != 0) {
                return this.definePropertiesGeneric(arguments0Value_, arguments1Value_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.execute(frameValue);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private JSDynamicObject executeAndSpecialize(Object arguments0Value, Object arguments1Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                int exclude = this.exclude_;
                if (exclude == 0 && arguments0Value instanceof JSDynamicObject) {
                    JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
                    if (arguments1Value instanceof JSDynamicObject) {
                        JSDynamicObject arguments1Value_ = (JSDynamicObject)arguments1Value;
                        if (JSGuards.isJSObject(arguments0Value_) && JSGuards.isJSObject(arguments1Value_)) {
                            this.state_0_ = state_0 |= 1;
                            lock.unlock();
                            hasLock = false;
                            JSDynamicObject jSDynamicObject = this.definePropertiesObjectObject(arguments0Value_, arguments1Value_);
                            return jSDynamicObject;
                        }
                    }
                }
                this.exclude_ = exclude |= 1;
                state_0 &= 0xFFFFFFFE;
                this.state_0_ = state_0 |= 2;
                lock.unlock();
                hasLock = false;
                JSDynamicObject jSDynamicObject = this.definePropertiesGeneric(arguments0Value, arguments1Value);
                return jSDynamicObject;
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
            int exclude = this.exclude_;
            Object[] s = new Object[3];
            s[0] = "definePropertiesObjectObject";
            s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : (exclude != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0));
            data[1] = s;
            s = new Object[3];
            s[0] = "definePropertiesGeneric";
            s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[2] = s;
            return Introspection.Provider.create(data);
        }

        public static ObjectFunctionBuiltins.ObjectDefinePropertiesNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new ObjectDefinePropertiesNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=ObjectFunctionBuiltins.ObjectDefinePropertyNode.class)
    public static final class ObjectDefinePropertyNodeGen
    extends ObjectFunctionBuiltins.ObjectDefinePropertyNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @Node.Child
        private JavaScriptNode arguments2_;
        @CompilerDirectives.CompilationFinal
        private int state_0_;
        @CompilerDirectives.CompilationFinal
        private int exclude_;

        private ObjectDefinePropertyNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
                if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSDynamicObject) {
                    JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
                    if (arguments1Value_ instanceof TruffleString) {
                        TruffleString arguments1Value__ = (TruffleString)arguments1Value_;
                        if (JSGuards.isJSObject(arguments0Value__)) {
                            return this.definePropertyJSObjectTString(arguments0Value__, arguments1Value__, arguments2Value_);
                        }
                    }
                }
                if ((state_0 & 2) != 0) {
                    return this.definePropertyGeneric(arguments0Value_, arguments1Value_, arguments2Value_);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_, arguments2Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.execute(frameValue);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private JSDynamicObject executeAndSpecialize(Object arguments0Value, Object arguments1Value, Object arguments2Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                int exclude = this.exclude_;
                if (exclude == 0 && arguments0Value instanceof JSDynamicObject) {
                    JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
                    if (arguments1Value instanceof TruffleString) {
                        TruffleString arguments1Value_ = (TruffleString)arguments1Value;
                        if (JSGuards.isJSObject(arguments0Value_)) {
                            this.state_0_ = state_0 |= 1;
                            lock.unlock();
                            hasLock = false;
                            JSDynamicObject jSDynamicObject = this.definePropertyJSObjectTString(arguments0Value_, arguments1Value_, arguments2Value);
                            return jSDynamicObject;
                        }
                    }
                }
                this.exclude_ = exclude |= 1;
                state_0 &= 0xFFFFFFFE;
                this.state_0_ = state_0 |= 2;
                lock.unlock();
                hasLock = false;
                JSDynamicObject jSDynamicObject = this.definePropertyGeneric(arguments0Value, arguments1Value, arguments2Value);
                return jSDynamicObject;
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
            int exclude = this.exclude_;
            Object[] s = new Object[3];
            s[0] = "definePropertyJSObjectTString";
            s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : (exclude != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0));
            data[1] = s;
            s = new Object[3];
            s[0] = "definePropertyGeneric";
            s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[2] = s;
            return Introspection.Provider.create(data);
        }

        public static ObjectFunctionBuiltins.ObjectDefinePropertyNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new ObjectDefinePropertyNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=ObjectFunctionBuiltins.ObjectCreateNode.class)
    public static final class ObjectCreateNodeGen
    extends ObjectFunctionBuiltins.ObjectCreateNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @CompilerDirectives.CompilationFinal
        private volatile int exclude_;
        @Node.Child
        private CreateForeignNullOrInvalidPrototype0Data createForeignNullOrInvalidPrototype0_cache;
        @CompilerDirectives.CompilationFinal
        private ConditionProfile createForeignNullOrInvalidPrototype1_isNull_;

        private ObjectCreateNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            super(context, builtin);
            this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
            this.arguments1_ = arguments != null && 1 < arguments.length ? arguments[1] : null;
        }

        @Override
        public JavaScriptNode[] getArguments() {
            return new JavaScriptNode[]{this.arguments0_, this.arguments1_};
        }

        @Override
        @ExplodeLoop
        public Object execute(VirtualFrame frameValue) {
            int state_0 = this.state_0_;
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            if ((state_0 & 7) != 0) {
                if ((state_0 & 1) != 0 && JSGuards.isJSNull(arguments0Value_)) {
                    return this.createPrototypeNull(arguments0Value_, arguments1Value_);
                }
                if ((state_0 & 2) != 0) {
                    CreateForeignNullOrInvalidPrototype0Data s1_ = this.createForeignNullOrInvalidPrototype0_cache;
                    while (s1_ != null) {
                        if (s1_.interop_.accepts(arguments0Value_) && !JSGuards.isJSNull(arguments0Value_) && !JSGuards.isJSObject(arguments0Value_)) {
                            return this.createForeignNullOrInvalidPrototype(arguments0Value_, arguments1Value_, s1_.interop_, s1_.isNull_);
                        }
                        s1_ = s1_.next_;
                    }
                }
                if ((state_0 & 4) != 0 && !JSGuards.isJSNull(arguments0Value_) && !JSGuards.isJSObject(arguments0Value_)) {
                    return this.createForeignNullOrInvalidPrototype1Boundary(state_0, arguments0Value_, arguments1Value_);
                }
            }
            if ((state_0 & 0x38) != 0 && arguments0Value_ instanceof JSDynamicObject) {
                JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
                if ((state_0 & 8) != 0 && arguments1Value_ instanceof JSDynamicObject) {
                    JSDynamicObject arguments1Value__ = (JSDynamicObject)arguments1Value_;
                    if (JSGuards.isJSObject(arguments0Value__) && JSGuards.isJSObject(arguments1Value__)) {
                        return this.createObjectObject(arguments0Value__, arguments1Value__);
                    }
                }
                if ((state_0 & 0x30) != 0) {
                    if ((state_0 & 0x10) != 0 && JSGuards.isJSObject(arguments0Value__) && !JSGuards.isJSNull(arguments1Value_)) {
                        return this.createObjectNotNull(arguments0Value__, arguments1Value_);
                    }
                    if ((state_0 & 0x20) != 0 && JSGuards.isJSObject(arguments0Value__) && JSGuards.isJSNull(arguments1Value_)) {
                        return this.createObjectNull(arguments0Value__, arguments1Value_);
                    }
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @CompilerDirectives.TruffleBoundary
        private Object createForeignNullOrInvalidPrototype1Boundary(int state_0, Object arguments0Value_, Object arguments1Value_) {
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this);
            try {
                InteropLibrary createForeignNullOrInvalidPrototype1_interop__ = INTEROP_LIBRARY_.getUncached(arguments0Value_);
                JSDynamicObject jSDynamicObject = this.createForeignNullOrInvalidPrototype(arguments0Value_, arguments1Value_, createForeignNullOrInvalidPrototype1_interop__, this.createForeignNullOrInvalidPrototype1_isNull_);
                return jSDynamicObject;
            }
            finally {
                encapsulating_.set(prev_);
            }
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.execute(frameValue);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private JSDynamicObject executeAndSpecialize(Object arguments0Value, Object arguments1Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                int exclude = this.exclude_;
                if (JSGuards.isJSNull(arguments0Value)) {
                    this.state_0_ = state_0 |= 1;
                    lock.unlock();
                    hasLock = false;
                    JSDynamicObject jSDynamicObject = this.createPrototypeNull(arguments0Value, arguments1Value);
                    return jSDynamicObject;
                }
                if (exclude == 0) {
                    int count1_ = 0;
                    CreateForeignNullOrInvalidPrototype0Data s1_ = this.createForeignNullOrInvalidPrototype0_cache;
                    if ((state_0 & 2) != 0) {
                        while (s1_ != null && (!s1_.interop_.accepts(arguments0Value) || JSGuards.isJSNull(arguments0Value) || JSGuards.isJSObject(arguments0Value))) {
                            s1_ = s1_.next_;
                            ++count1_;
                        }
                    }
                    if (s1_ == null && !JSGuards.isJSNull(arguments0Value) && !JSGuards.isJSObject(arguments0Value) && count1_ < 5) {
                        s1_ = super.insert(new CreateForeignNullOrInvalidPrototype0Data(this.createForeignNullOrInvalidPrototype0_cache));
                        s1_.interop_ = s1_.insertAccessor(INTEROP_LIBRARY_.create(arguments0Value));
                        s1_.isNull_ = ConditionProfile.createBinaryProfile();
                        VarHandle.storeStoreFence();
                        this.createForeignNullOrInvalidPrototype0_cache = s1_;
                        this.state_0_ = state_0 |= 2;
                    }
                    if (s1_ != null) {
                        lock.unlock();
                        hasLock = false;
                        JSDynamicObject jSDynamicObject = this.createForeignNullOrInvalidPrototype(arguments0Value, arguments1Value, s1_.interop_, s1_.isNull_);
                        return jSDynamicObject;
                    }
                }
                InteropLibrary createForeignNullOrInvalidPrototype1_interop__ = null;
                EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                Node prev_ = encapsulating_.set(this);
                try {
                    if (!JSGuards.isJSNull(arguments0Value) && !JSGuards.isJSObject(arguments0Value)) {
                        createForeignNullOrInvalidPrototype1_interop__ = INTEROP_LIBRARY_.getUncached(arguments0Value);
                        this.createForeignNullOrInvalidPrototype1_isNull_ = ConditionProfile.createBinaryProfile();
                        this.exclude_ = exclude |= 1;
                        this.createForeignNullOrInvalidPrototype0_cache = null;
                        state_0 &= 0xFFFFFFFD;
                        this.state_0_ = state_0 |= 4;
                        lock.unlock();
                        hasLock = false;
                        JSDynamicObject jSDynamicObject = this.createForeignNullOrInvalidPrototype(arguments0Value, arguments1Value, createForeignNullOrInvalidPrototype1_interop__, this.createForeignNullOrInvalidPrototype1_isNull_);
                        return jSDynamicObject;
                    }
                }
                finally {
                    encapsulating_.set(prev_);
                }
                if (arguments0Value instanceof JSDynamicObject) {
                    JSDynamicObject jSDynamicObject;
                    JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
                    if (arguments1Value instanceof JSDynamicObject) {
                        JSDynamicObject arguments1Value_ = (JSDynamicObject)arguments1Value;
                        if (JSGuards.isJSObject(arguments0Value_) && JSGuards.isJSObject(arguments1Value_)) {
                            this.state_0_ = state_0 |= 8;
                            lock.unlock();
                            hasLock = false;
                            JSDynamicObject jSDynamicObject2 = this.createObjectObject(arguments0Value_, arguments1Value_);
                            return jSDynamicObject2;
                        }
                    }
                    if (JSGuards.isJSObject(arguments0Value_) && !JSGuards.isJSNull(arguments1Value)) {
                        this.state_0_ = state_0 |= 0x10;
                        lock.unlock();
                        hasLock = false;
                        jSDynamicObject = this.createObjectNotNull(arguments0Value_, arguments1Value);
                        return jSDynamicObject;
                    }
                    if (JSGuards.isJSObject(arguments0Value_) && JSGuards.isJSNull(arguments1Value)) {
                        this.state_0_ = state_0 |= 0x20;
                        lock.unlock();
                        hasLock = false;
                        jSDynamicObject = this.createObjectNull(arguments0Value_, arguments1Value);
                        return jSDynamicObject;
                    }
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
            CreateForeignNullOrInvalidPrototype0Data s1_;
            int state_0 = this.state_0_;
            if (state_0 == 0) {
                return NodeCost.UNINITIALIZED;
            }
            if ((state_0 & state_0 - 1) == 0 && ((s1_ = this.createForeignNullOrInvalidPrototype0_cache) == null || s1_.next_ == null)) {
                return NodeCost.MONOMORPHIC;
            }
            return NodeCost.POLYMORPHIC;
        }

        @Override
        public Introspection getIntrospectionData() {
            ArrayList<List<Cloneable>> cached;
            Object[] data = new Object[7];
            data[0] = 0;
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            Object[] s = new Object[3];
            s[0] = "createPrototypeNull";
            s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[1] = s;
            s = new Object[3];
            s[0] = "createForeignNullOrInvalidPrototype";
            if ((state_0 & 2) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList<List<Cloneable>>();
                CreateForeignNullOrInvalidPrototype0Data s1_ = this.createForeignNullOrInvalidPrototype0_cache;
                while (s1_ != null) {
                    cached.add(Arrays.asList(s1_.interop_, s1_.isNull_));
                    s1_ = s1_.next_;
                }
                s[2] = cached;
            } else {
                s[1] = exclude != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0);
            }
            data[2] = s;
            s = new Object[3];
            s[0] = "createForeignNullOrInvalidPrototype";
            if ((state_0 & 4) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList();
                cached.add(Arrays.asList(this.createForeignNullOrInvalidPrototype1_isNull_));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[3] = s;
            s = new Object[3];
            s[0] = "createObjectObject";
            s[1] = (state_0 & 8) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[4] = s;
            s = new Object[3];
            s[0] = "createObjectNotNull";
            s[1] = (state_0 & 0x10) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[5] = s;
            s = new Object[3];
            s[0] = "createObjectNull";
            s[1] = (state_0 & 0x20) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[6] = s;
            return Introspection.Provider.create(data);
        }

        public static ObjectFunctionBuiltins.ObjectCreateNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new ObjectCreateNodeGen(context, builtin, arguments);
        }

        @GeneratedBy(value=ObjectFunctionBuiltins.ObjectCreateNode.class)
        private static final class CreateForeignNullOrInvalidPrototype0Data
        extends Node {
            @Node.Child
            CreateForeignNullOrInvalidPrototype0Data next_;
            @Node.Child
            InteropLibrary interop_;
            @CompilerDirectives.CompilationFinal
            ConditionProfile isNull_;

            CreateForeignNullOrInvalidPrototype0Data(CreateForeignNullOrInvalidPrototype0Data next_) {
                this.next_ = next_;
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

    @GeneratedBy(value=ObjectFunctionBuiltins.ObjectGetOwnPropertyNamesOrSymbolsNode.class)
    public static final class ObjectGetOwnPropertyNamesOrSymbolsNodeGen
    extends ObjectFunctionBuiltins.ObjectGetOwnPropertyNamesOrSymbolsNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @CompilerDirectives.CompilationFinal
        private JSClassProfile jsclassProfile;
        @Node.Child
        private ListSizeNode listSize;
        @Node.Child
        private EnumerableOwnPropertyNamesNode getForeignObjectNames_enumerableOwnPropertyNamesNode_;
        @CompilerDirectives.CompilationFinal
        private ConditionProfile getForeignObjectNames_hasElements_;

        private ObjectGetOwnPropertyNamesOrSymbolsNodeGen(JSContext context, JSBuiltin builtin, boolean symbols, JavaScriptNode[] arguments) {
            super(context, builtin, symbols);
            this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
        }

        @Override
        public JavaScriptNode[] getArguments() {
            return new JavaScriptNode[]{this.arguments0_};
        }

        @Override
        public Object execute(VirtualFrame frameValue) {
            JSDynamicObject arguments0Value__;
            int state_0 = this.state_0_;
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSDynamicObject && JSGuards.isJSObject(arguments0Value__ = (JSDynamicObject)arguments0Value_)) {
                return this.getJSObject(arguments0Value__, this.jsclassProfile, this.listSize);
            }
            if ((state_0 & 0xE) != 0) {
                if ((state_0 & 2) != 0 && !JSGuards.isJSObject(arguments0Value_) && !JSGuards.isForeignObject(arguments0Value_)) {
                    return this.getDefault(arguments0Value_, this.jsclassProfile, this.listSize);
                }
                if ((state_0 & 4) != 0 && JSGuards.isForeignObject(arguments0Value_)) {
                    assert (this.symbols);
                    return this.getForeignObjectSymbols(arguments0Value_);
                }
                if ((state_0 & 8) != 0 && JSGuards.isForeignObject(arguments0Value_)) {
                    assert (!this.symbols);
                    return this.getForeignObjectNames(arguments0Value_, this.getForeignObjectNames_enumerableOwnPropertyNamesNode_, this.getForeignObjectNames_hasElements_);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.execute(frameValue);
        }

        private JSDynamicObject executeAndSpecialize(Object arguments0Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                JSDynamicObject jSDynamicObject;
                JSDynamicObject arguments0Value_;
                int state_0 = this.state_0_;
                if (arguments0Value instanceof JSDynamicObject && JSGuards.isJSObject(arguments0Value_ = (JSDynamicObject)arguments0Value)) {
                    this.jsclassProfile = this.jsclassProfile == null ? JSClassProfile.create() : this.jsclassProfile;
                    this.listSize = super.insert(this.listSize == null ? ListSizeNode.create() : this.listSize);
                    this.state_0_ = state_0 |= 1;
                    lock.unlock();
                    hasLock = false;
                    JSDynamicObject jSDynamicObject2 = this.getJSObject(arguments0Value_, this.jsclassProfile, this.listSize);
                    return jSDynamicObject2;
                }
                if (!JSGuards.isJSObject(arguments0Value) && !JSGuards.isForeignObject(arguments0Value)) {
                    this.jsclassProfile = this.jsclassProfile == null ? JSClassProfile.create() : this.jsclassProfile;
                    this.listSize = super.insert(this.listSize == null ? ListSizeNode.create() : this.listSize);
                    this.state_0_ = state_0 |= 2;
                    lock.unlock();
                    hasLock = false;
                    jSDynamicObject = this.getDefault(arguments0Value, this.jsclassProfile, this.listSize);
                    return jSDynamicObject;
                }
                if (JSGuards.isForeignObject(arguments0Value) && this.symbols) {
                    this.state_0_ = state_0 |= 4;
                    lock.unlock();
                    hasLock = false;
                    jSDynamicObject = this.getForeignObjectSymbols(arguments0Value);
                    return jSDynamicObject;
                }
                if (JSGuards.isForeignObject(arguments0Value) && !this.symbols) {
                    this.getForeignObjectNames_enumerableOwnPropertyNamesNode_ = super.insert(EnumerableOwnPropertyNamesNode.createKeys(this.getContext()));
                    this.getForeignObjectNames_hasElements_ = ConditionProfile.create();
                    this.state_0_ = state_0 |= 8;
                    lock.unlock();
                    hasLock = false;
                    jSDynamicObject = this.getForeignObjectNames(arguments0Value, this.getForeignObjectNames_enumerableOwnPropertyNamesNode_, this.getForeignObjectNames_hasElements_);
                    return jSDynamicObject;
                }
                throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_}, arguments0Value);
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
            ArrayList<List<Cloneable>> cached;
            Object[] data = new Object[5];
            data[0] = 0;
            int state_0 = this.state_0_;
            Object[] s = new Object[3];
            s[0] = "getJSObject";
            if ((state_0 & 1) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList<List<Cloneable>>();
                cached.add(Arrays.asList(this.jsclassProfile, this.listSize));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[1] = s;
            s = new Object[3];
            s[0] = "getDefault";
            if ((state_0 & 2) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList();
                cached.add(Arrays.asList(this.jsclassProfile, this.listSize));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[2] = s;
            s = new Object[3];
            s[0] = "getForeignObjectSymbols";
            s[1] = (state_0 & 4) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[3] = s;
            s = new Object[3];
            s[0] = "getForeignObjectNames";
            if ((state_0 & 8) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList();
                cached.add(Arrays.asList(this.getForeignObjectNames_enumerableOwnPropertyNamesNode_, this.getForeignObjectNames_hasElements_));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[4] = s;
            return Introspection.Provider.create(data);
        }

        public static ObjectFunctionBuiltins.ObjectGetOwnPropertyNamesOrSymbolsNode create(JSContext context, JSBuiltin builtin, boolean symbols, JavaScriptNode[] arguments) {
            return new ObjectGetOwnPropertyNamesOrSymbolsNodeGen(context, builtin, symbols, arguments);
        }
    }

    @GeneratedBy(value=ObjectFunctionBuiltins.ObjectGetOwnPropertyDescriptorsNode.class)
    public static final class ObjectGetOwnPropertyDescriptorsNodeGen
    extends ObjectFunctionBuiltins.ObjectGetOwnPropertyDescriptorsNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @CompilerDirectives.CompilationFinal
        private volatile int exclude_;
        @Node.Child
        private GetJSObjectData getJSObject_cache;
        @Node.Child
        private GetForeignObject0Data getForeignObject0_cache;
        @Node.Child
        private GetForeignObject1Data getForeignObject1_cache;
        @Node.Child
        private ObjectFunctionBuiltins.ObjectGetOwnPropertyDescriptorsNode getDefault_recursive_;

        private ObjectGetOwnPropertyDescriptorsNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            super(context, builtin);
            this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
        }

        @Override
        public JavaScriptNode[] getArguments() {
            return new JavaScriptNode[]{this.arguments0_};
        }

        @Override
        @ExplodeLoop
        protected JSDynamicObject executeEvaluated(Object arguments0Value) {
            int state_0 = this.state_0_;
            if ((state_0 & 1) != 0 && arguments0Value instanceof JSDynamicObject) {
                JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
                GetJSObjectData s0_ = this.getJSObject_cache;
                if (s0_ != null && JSGuards.isJSObject(arguments0Value_)) {
                    return this.getJSObject(arguments0Value_, s0_.getOwnPropertyNode_, s0_.listSize_, s0_.listGet_, s0_.classProfile_);
                }
            }
            if ((state_0 & 0xE) != 0) {
                GetForeignObject1Data s2_;
                if ((state_0 & 2) != 0) {
                    GetForeignObject0Data s1_ = this.getForeignObject0_cache;
                    while (s1_ != null) {
                        if (s1_.interop_.accepts(arguments0Value) && JSGuards.isForeignObject(arguments0Value)) {
                            return this.getForeignObject(arguments0Value, s1_.interop_, s1_.members_, s1_.toJSType_, s1_.errorBranch_);
                        }
                        s1_ = s1_.next_;
                    }
                }
                if ((state_0 & 4) != 0 && (s2_ = this.getForeignObject1_cache) != null && JSGuards.isForeignObject(arguments0Value)) {
                    return this.getForeignObject1Boundary(state_0, s2_, arguments0Value);
                }
                if ((state_0 & 8) != 0 && !JSGuards.isJSObject(arguments0Value) && !JSGuards.isForeignObject(arguments0Value)) {
                    return this.getDefault(arguments0Value, this.getDefault_recursive_);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @CompilerDirectives.TruffleBoundary
        private JSDynamicObject getForeignObject1Boundary(int state_0, GetForeignObject1Data s2_, Object arguments0Value) {
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this);
            try {
                InteropLibrary interop__ = INTEROP_LIBRARY_.getUncached(arguments0Value);
                JSDynamicObject jSDynamicObject = this.getForeignObject(arguments0Value, interop__, s2_.members_, s2_.toJSType_, s2_.errorBranch_);
                return jSDynamicObject;
            }
            finally {
                encapsulating_.set(prev_);
            }
        }

        @Override
        @ExplodeLoop
        public Object execute(VirtualFrame frameValue) {
            int state_0 = this.state_0_;
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSDynamicObject) {
                JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
                GetJSObjectData s0_ = this.getJSObject_cache;
                if (s0_ != null && JSGuards.isJSObject(arguments0Value__)) {
                    return this.getJSObject(arguments0Value__, s0_.getOwnPropertyNode_, s0_.listSize_, s0_.listGet_, s0_.classProfile_);
                }
            }
            if ((state_0 & 0xE) != 0) {
                GetForeignObject1Data s2_;
                if ((state_0 & 2) != 0) {
                    GetForeignObject0Data s1_ = this.getForeignObject0_cache;
                    while (s1_ != null) {
                        if (s1_.interop_.accepts(arguments0Value_) && JSGuards.isForeignObject(arguments0Value_)) {
                            return this.getForeignObject(arguments0Value_, s1_.interop_, s1_.members_, s1_.toJSType_, s1_.errorBranch_);
                        }
                        s1_ = s1_.next_;
                    }
                }
                if ((state_0 & 4) != 0 && (s2_ = this.getForeignObject1_cache) != null && JSGuards.isForeignObject(arguments0Value_)) {
                    return this.getForeignObject1Boundary0(state_0, s2_, arguments0Value_);
                }
                if ((state_0 & 8) != 0 && !JSGuards.isJSObject(arguments0Value_) && !JSGuards.isForeignObject(arguments0Value_)) {
                    return this.getDefault(arguments0Value_, this.getDefault_recursive_);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @CompilerDirectives.TruffleBoundary
        private Object getForeignObject1Boundary0(int state_0, GetForeignObject1Data s2_, Object arguments0Value_) {
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this);
            try {
                InteropLibrary interop__ = INTEROP_LIBRARY_.getUncached(arguments0Value_);
                JSDynamicObject jSDynamicObject = this.getForeignObject(arguments0Value_, interop__, s2_.members_, s2_.toJSType_, s2_.errorBranch_);
                return jSDynamicObject;
            }
            finally {
                encapsulating_.set(prev_);
            }
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.execute(frameValue);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private JSDynamicObject executeAndSpecialize(Object arguments0Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                JSDynamicObject arguments0Value_;
                int state_0 = this.state_0_;
                int exclude = this.exclude_;
                if (arguments0Value instanceof JSDynamicObject && JSGuards.isJSObject(arguments0Value_ = (JSDynamicObject)arguments0Value)) {
                    GetJSObjectData s0_ = super.insert(new GetJSObjectData());
                    s0_.getOwnPropertyNode_ = s0_.insertAccessor(JSGetOwnPropertyNode.create());
                    s0_.listSize_ = s0_.insertAccessor(ListSizeNode.create());
                    s0_.listGet_ = s0_.insertAccessor(ListGetNode.create());
                    s0_.classProfile_ = JSClassProfile.create();
                    VarHandle.storeStoreFence();
                    this.getJSObject_cache = s0_;
                    this.state_0_ = state_0 |= 1;
                    lock.unlock();
                    hasLock = false;
                    JSDynamicObject jSDynamicObject = this.getJSObject(arguments0Value_, s0_.getOwnPropertyNode_, s0_.listSize_, s0_.listGet_, s0_.classProfile_);
                    return jSDynamicObject;
                }
                if (exclude == 0) {
                    int count1_ = 0;
                    GetForeignObject0Data s1_ = this.getForeignObject0_cache;
                    if ((state_0 & 2) != 0) {
                        while (!(s1_ == null || s1_.interop_.accepts(arguments0Value) && JSGuards.isForeignObject(arguments0Value))) {
                            s1_ = s1_.next_;
                            ++count1_;
                        }
                    }
                    if (s1_ == null && JSGuards.isForeignObject(arguments0Value) && count1_ < 5) {
                        s1_ = super.insert(new GetForeignObject0Data(this.getForeignObject0_cache));
                        s1_.interop_ = s1_.insertAccessor(INTEROP_LIBRARY_.create(arguments0Value));
                        s1_.members_ = s1_.insertAccessor(INTEROP_LIBRARY_.createDispatched(5));
                        s1_.toJSType_ = s1_.insertAccessor(ImportValueNode.create());
                        s1_.errorBranch_ = BranchProfile.create();
                        VarHandle.storeStoreFence();
                        this.getForeignObject0_cache = s1_;
                        this.state_0_ = state_0 |= 2;
                    }
                    if (s1_ != null) {
                        lock.unlock();
                        hasLock = false;
                        JSDynamicObject jSDynamicObject = this.getForeignObject(arguments0Value, s1_.interop_, s1_.members_, s1_.toJSType_, s1_.errorBranch_);
                        return jSDynamicObject;
                    }
                }
                InteropLibrary interop__ = null;
                EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                Node prev_ = encapsulating_.set(this);
                try {
                    if (JSGuards.isForeignObject(arguments0Value)) {
                        GetForeignObject1Data s2_ = super.insert(new GetForeignObject1Data());
                        interop__ = INTEROP_LIBRARY_.getUncached(arguments0Value);
                        s2_.members_ = s2_.insertAccessor(INTEROP_LIBRARY_.createDispatched(5));
                        s2_.toJSType_ = s2_.insertAccessor(ImportValueNode.create());
                        s2_.errorBranch_ = BranchProfile.create();
                        VarHandle.storeStoreFence();
                        this.getForeignObject1_cache = s2_;
                        this.exclude_ = exclude |= 1;
                        this.getForeignObject0_cache = null;
                        state_0 &= 0xFFFFFFFD;
                        this.state_0_ = state_0 |= 4;
                        lock.unlock();
                        hasLock = false;
                        JSDynamicObject jSDynamicObject = this.getForeignObject(arguments0Value, interop__, s2_.members_, s2_.toJSType_, s2_.errorBranch_);
                        return jSDynamicObject;
                    }
                }
                finally {
                    encapsulating_.set(prev_);
                }
                if (!JSGuards.isJSObject(arguments0Value) && !JSGuards.isForeignObject(arguments0Value)) {
                    this.getDefault_recursive_ = super.insert(this.createRecursive());
                    this.state_0_ = state_0 |= 8;
                    lock.unlock();
                    hasLock = false;
                    JSDynamicObject jSDynamicObject = this.getDefault(arguments0Value, this.getDefault_recursive_);
                    return jSDynamicObject;
                }
                throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_}, arguments0Value);
            }
            finally {
                if (hasLock) {
                    lock.unlock();
                }
            }
        }

        @Override
        public NodeCost getCost() {
            GetForeignObject0Data s1_;
            int state_0 = this.state_0_;
            if (state_0 == 0) {
                return NodeCost.UNINITIALIZED;
            }
            if ((state_0 & state_0 - 1) == 0 && ((s1_ = this.getForeignObject0_cache) == null || s1_.next_ == null)) {
                return NodeCost.MONOMORPHIC;
            }
            return NodeCost.POLYMORPHIC;
        }

        @Override
        public Introspection getIntrospectionData() {
            ArrayList<List<Cloneable>> cached;
            Object[] data = new Object[5];
            data[0] = 0;
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            Object[] s = new Object[3];
            s[0] = "getJSObject";
            if ((state_0 & 1) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList<List<Cloneable>>();
                GetJSObjectData s0_ = this.getJSObject_cache;
                if (s0_ != null) {
                    cached.add(Arrays.asList(s0_.getOwnPropertyNode_, s0_.listSize_, s0_.listGet_, s0_.classProfile_));
                }
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[1] = s;
            s = new Object[3];
            s[0] = "getForeignObject";
            if ((state_0 & 2) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList();
                GetForeignObject0Data s1_ = this.getForeignObject0_cache;
                while (s1_ != null) {
                    cached.add(Arrays.asList(s1_.interop_, s1_.members_, s1_.toJSType_, s1_.errorBranch_));
                    s1_ = s1_.next_;
                }
                s[2] = cached;
            } else {
                s[1] = exclude != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0);
            }
            data[2] = s;
            s = new Object[3];
            s[0] = "getForeignObject";
            if ((state_0 & 4) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList();
                GetForeignObject1Data s2_ = this.getForeignObject1_cache;
                if (s2_ != null) {
                    cached.add(Arrays.asList(s2_.members_, s2_.toJSType_, s2_.errorBranch_));
                }
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[3] = s;
            s = new Object[3];
            s[0] = "getDefault";
            if ((state_0 & 8) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList();
                cached.add(Arrays.asList(this.getDefault_recursive_));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[4] = s;
            return Introspection.Provider.create(data);
        }

        public static ObjectFunctionBuiltins.ObjectGetOwnPropertyDescriptorsNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new ObjectGetOwnPropertyDescriptorsNodeGen(context, builtin, arguments);
        }

        @GeneratedBy(value=ObjectFunctionBuiltins.ObjectGetOwnPropertyDescriptorsNode.class)
        private static final class GetForeignObject1Data
        extends Node {
            @Node.Child
            InteropLibrary members_;
            @Node.Child
            ImportValueNode toJSType_;
            @CompilerDirectives.CompilationFinal
            BranchProfile errorBranch_;

            GetForeignObject1Data() {
            }

            @Override
            public NodeCost getCost() {
                return NodeCost.NONE;
            }

            <T extends Node> T insertAccessor(T node) {
                return super.insert(node);
            }
        }

        @GeneratedBy(value=ObjectFunctionBuiltins.ObjectGetOwnPropertyDescriptorsNode.class)
        private static final class GetForeignObject0Data
        extends Node {
            @Node.Child
            GetForeignObject0Data next_;
            @Node.Child
            InteropLibrary interop_;
            @Node.Child
            InteropLibrary members_;
            @Node.Child
            ImportValueNode toJSType_;
            @CompilerDirectives.CompilationFinal
            BranchProfile errorBranch_;

            GetForeignObject0Data(GetForeignObject0Data next_) {
                this.next_ = next_;
            }

            @Override
            public NodeCost getCost() {
                return NodeCost.NONE;
            }

            <T extends Node> T insertAccessor(T node) {
                return super.insert(node);
            }
        }

        @GeneratedBy(value=ObjectFunctionBuiltins.ObjectGetOwnPropertyDescriptorsNode.class)
        private static final class GetJSObjectData
        extends Node {
            @Node.Child
            JSGetOwnPropertyNode getOwnPropertyNode_;
            @Node.Child
            ListSizeNode listSize_;
            @Node.Child
            ListGetNode listGet_;
            @CompilerDirectives.CompilationFinal
            JSClassProfile classProfile_;

            GetJSObjectData() {
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

    @GeneratedBy(value=ObjectFunctionBuiltins.ObjectGetOwnPropertyDescriptorNode.class)
    public static final class ObjectGetOwnPropertyDescriptorNodeGen
    extends ObjectFunctionBuiltins.ObjectGetOwnPropertyDescriptorNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @CompilerDirectives.CompilationFinal
        private volatile int exclude_;
        @Node.Child
        private GetForeignObject0Data getForeignObject0_cache;
        @Node.Child
        private ImportValueNode getForeignObject1_toJSType_;
        @Node.Child
        private TruffleString.ReadCharUTF16Node getForeignObject1_charAtNode_;

        private ObjectGetOwnPropertyDescriptorNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            super(context, builtin);
            this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
            this.arguments1_ = arguments != null && 1 < arguments.length ? arguments[1] : null;
        }

        @Override
        public JavaScriptNode[] getArguments() {
            return new JavaScriptNode[]{this.arguments0_, this.arguments1_};
        }

        @Override
        @ExplodeLoop
        public Object execute(VirtualFrame frameValue) {
            int state_0 = this.state_0_;
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            if (state_0 != 0) {
                JSDynamicObject arguments0Value__;
                if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSDynamicObject && JSGuards.isJSObject(arguments0Value__ = (JSDynamicObject)arguments0Value_)) {
                    return this.getJSObject(arguments0Value__, arguments1Value_);
                }
                if ((state_0 & 0xE) != 0) {
                    if ((state_0 & 2) != 0) {
                        GetForeignObject0Data s1_ = this.getForeignObject0_cache;
                        while (s1_ != null) {
                            if (s1_.interop_.accepts(arguments0Value_) && JSGuards.isForeignObject(arguments0Value_)) {
                                return this.getForeignObject(arguments0Value_, arguments1Value_, s1_.interop_, s1_.toJSType_, s1_.charAtNode_);
                            }
                            s1_ = s1_.next_;
                        }
                    }
                    if ((state_0 & 4) != 0 && JSGuards.isForeignObject(arguments0Value_)) {
                        return this.getForeignObject1Boundary(state_0, arguments0Value_, arguments1Value_);
                    }
                    if ((state_0 & 8) != 0 && !JSGuards.isJSObject(arguments0Value_) && !JSGuards.isForeignObject(arguments0Value_)) {
                        return this.getDefault(arguments0Value_, arguments1Value_);
                    }
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @CompilerDirectives.TruffleBoundary
        private Object getForeignObject1Boundary(int state_0, Object arguments0Value_, Object arguments1Value_) {
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this);
            try {
                InteropLibrary getForeignObject1_interop__ = INTEROP_LIBRARY_.getUncached(arguments0Value_);
                JSDynamicObject jSDynamicObject = this.getForeignObject(arguments0Value_, arguments1Value_, getForeignObject1_interop__, this.getForeignObject1_toJSType_, this.getForeignObject1_charAtNode_);
                return jSDynamicObject;
            }
            finally {
                encapsulating_.set(prev_);
            }
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.execute(frameValue);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private JSDynamicObject executeAndSpecialize(Object arguments0Value, Object arguments1Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                JSDynamicObject arguments0Value_;
                int state_0 = this.state_0_;
                int exclude = this.exclude_;
                if (arguments0Value instanceof JSDynamicObject && JSGuards.isJSObject(arguments0Value_ = (JSDynamicObject)arguments0Value)) {
                    this.state_0_ = state_0 |= 1;
                    lock.unlock();
                    hasLock = false;
                    JSDynamicObject jSDynamicObject = this.getJSObject(arguments0Value_, arguments1Value);
                    return jSDynamicObject;
                }
                if (exclude == 0) {
                    int count1_ = 0;
                    GetForeignObject0Data s1_ = this.getForeignObject0_cache;
                    if ((state_0 & 2) != 0) {
                        while (!(s1_ == null || s1_.interop_.accepts(arguments0Value) && JSGuards.isForeignObject(arguments0Value))) {
                            s1_ = s1_.next_;
                            ++count1_;
                        }
                    }
                    if (s1_ == null && JSGuards.isForeignObject(arguments0Value) && count1_ < 5) {
                        s1_ = super.insert(new GetForeignObject0Data(this.getForeignObject0_cache));
                        s1_.interop_ = s1_.insertAccessor(INTEROP_LIBRARY_.create(arguments0Value));
                        s1_.toJSType_ = s1_.insertAccessor(ImportValueNode.create());
                        s1_.charAtNode_ = s1_.insertAccessor(TruffleString.ReadCharUTF16Node.create());
                        VarHandle.storeStoreFence();
                        this.getForeignObject0_cache = s1_;
                        this.state_0_ = state_0 |= 2;
                    }
                    if (s1_ != null) {
                        lock.unlock();
                        hasLock = false;
                        JSDynamicObject jSDynamicObject = this.getForeignObject(arguments0Value, arguments1Value, s1_.interop_, s1_.toJSType_, s1_.charAtNode_);
                        return jSDynamicObject;
                    }
                }
                InteropLibrary getForeignObject1_interop__ = null;
                EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                Node prev_ = encapsulating_.set(this);
                try {
                    if (JSGuards.isForeignObject(arguments0Value)) {
                        getForeignObject1_interop__ = INTEROP_LIBRARY_.getUncached(arguments0Value);
                        this.getForeignObject1_toJSType_ = super.insert(ImportValueNode.create());
                        this.getForeignObject1_charAtNode_ = super.insert(TruffleString.ReadCharUTF16Node.create());
                        this.exclude_ = exclude |= 1;
                        this.getForeignObject0_cache = null;
                        state_0 &= 0xFFFFFFFD;
                        this.state_0_ = state_0 |= 4;
                        lock.unlock();
                        hasLock = false;
                        JSDynamicObject jSDynamicObject = this.getForeignObject(arguments0Value, arguments1Value, getForeignObject1_interop__, this.getForeignObject1_toJSType_, this.getForeignObject1_charAtNode_);
                        return jSDynamicObject;
                    }
                }
                finally {
                    encapsulating_.set(prev_);
                }
                if (!JSGuards.isJSObject(arguments0Value) && !JSGuards.isForeignObject(arguments0Value)) {
                    this.state_0_ = state_0 |= 8;
                    lock.unlock();
                    hasLock = false;
                    JSDynamicObject jSDynamicObject = this.getDefault(arguments0Value, arguments1Value);
                    return jSDynamicObject;
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
            GetForeignObject0Data s1_;
            int state_0 = this.state_0_;
            if (state_0 == 0) {
                return NodeCost.UNINITIALIZED;
            }
            if ((state_0 & state_0 - 1) == 0 && ((s1_ = this.getForeignObject0_cache) == null || s1_.next_ == null)) {
                return NodeCost.MONOMORPHIC;
            }
            return NodeCost.POLYMORPHIC;
        }

        @Override
        public Introspection getIntrospectionData() {
            ArrayList<List<Node>> cached;
            Object[] data = new Object[5];
            data[0] = 0;
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            Object[] s = new Object[3];
            s[0] = "getJSObject";
            s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[1] = s;
            s = new Object[3];
            s[0] = "getForeignObject";
            if ((state_0 & 2) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList<List<Node>>();
                GetForeignObject0Data s1_ = this.getForeignObject0_cache;
                while (s1_ != null) {
                    cached.add(Arrays.asList(s1_.interop_, s1_.toJSType_, s1_.charAtNode_));
                    s1_ = s1_.next_;
                }
                s[2] = cached;
            } else {
                s[1] = exclude != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0);
            }
            data[2] = s;
            s = new Object[3];
            s[0] = "getForeignObject";
            if ((state_0 & 4) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList();
                cached.add(Arrays.asList(this.getForeignObject1_toJSType_, this.getForeignObject1_charAtNode_));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[3] = s;
            s = new Object[3];
            s[0] = "getDefault";
            s[1] = (state_0 & 8) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[4] = s;
            return Introspection.Provider.create(data);
        }

        public static ObjectFunctionBuiltins.ObjectGetOwnPropertyDescriptorNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new ObjectGetOwnPropertyDescriptorNodeGen(context, builtin, arguments);
        }

        @GeneratedBy(value=ObjectFunctionBuiltins.ObjectGetOwnPropertyDescriptorNode.class)
        private static final class GetForeignObject0Data
        extends Node {
            @Node.Child
            GetForeignObject0Data next_;
            @Node.Child
            InteropLibrary interop_;
            @Node.Child
            ImportValueNode toJSType_;
            @Node.Child
            TruffleString.ReadCharUTF16Node charAtNode_;

            GetForeignObject0Data(GetForeignObject0Data next_) {
                this.next_ = next_;
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

    @GeneratedBy(value=ObjectFunctionBuiltins.ObjectGetPrototypeOfNode.class)
    public static final class ObjectGetPrototypeOfNodeGen
    extends ObjectFunctionBuiltins.ObjectGetPrototypeOfNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @CompilerDirectives.CompilationFinal
        private ConditionProfile getPrototypeOfNonObject_isForeignProfile_;
        @Node.Child
        private GetPrototypeNode getPrototypeOfJSObject_getPrototypeNode_;

        private ObjectGetPrototypeOfNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            super(context, builtin);
            this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
        }

        @Override
        public JavaScriptNode[] getArguments() {
            return new JavaScriptNode[]{this.arguments0_};
        }

        @Override
        public Object execute(VirtualFrame frameValue) {
            JSDynamicObject arguments0Value__;
            int state_0 = this.state_0_;
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            if ((state_0 & 1) != 0 && !JSGuards.isJSObject(arguments0Value_)) {
                return this.getPrototypeOfNonObject(arguments0Value_, this.getPrototypeOfNonObject_isForeignProfile_);
            }
            if ((state_0 & 2) != 0 && arguments0Value_ instanceof JSDynamicObject && JSGuards.isJSObject(arguments0Value__ = (JSDynamicObject)arguments0Value_)) {
                return this.getPrototypeOfJSObject(arguments0Value__, this.getPrototypeOfJSObject_getPrototypeNode_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.execute(frameValue);
        }

        private JSDynamicObject executeAndSpecialize(Object arguments0Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                JSDynamicObject arguments0Value_;
                int state_0 = this.state_0_;
                if (!JSGuards.isJSObject(arguments0Value)) {
                    this.getPrototypeOfNonObject_isForeignProfile_ = ConditionProfile.createBinaryProfile();
                    this.state_0_ = state_0 |= 1;
                    lock.unlock();
                    hasLock = false;
                    JSDynamicObject jSDynamicObject = this.getPrototypeOfNonObject(arguments0Value, this.getPrototypeOfNonObject_isForeignProfile_);
                    return jSDynamicObject;
                }
                if (arguments0Value instanceof JSDynamicObject && JSGuards.isJSObject(arguments0Value_ = (JSDynamicObject)arguments0Value)) {
                    this.getPrototypeOfJSObject_getPrototypeNode_ = super.insert(GetPrototypeNode.create());
                    this.state_0_ = state_0 |= 2;
                    lock.unlock();
                    hasLock = false;
                    JSDynamicObject jSDynamicObject = this.getPrototypeOfJSObject(arguments0Value_, this.getPrototypeOfJSObject_getPrototypeNode_);
                    return jSDynamicObject;
                }
                throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_}, arguments0Value);
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
            ArrayList<List<Cloneable>> cached;
            Object[] data = new Object[3];
            data[0] = 0;
            int state_0 = this.state_0_;
            Object[] s = new Object[3];
            s[0] = "getPrototypeOfNonObject";
            if ((state_0 & 1) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList<List<Cloneable>>();
                cached.add(Arrays.asList(this.getPrototypeOfNonObject_isForeignProfile_));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[1] = s;
            s = new Object[3];
            s[0] = "getPrototypeOfJSObject";
            if ((state_0 & 2) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList();
                cached.add(Arrays.asList(this.getPrototypeOfJSObject_getPrototypeNode_));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[2] = s;
            return Introspection.Provider.create(data);
        }

        public static ObjectFunctionBuiltins.ObjectGetPrototypeOfNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new ObjectGetPrototypeOfNodeGen(context, builtin, arguments);
        }
    }
}

