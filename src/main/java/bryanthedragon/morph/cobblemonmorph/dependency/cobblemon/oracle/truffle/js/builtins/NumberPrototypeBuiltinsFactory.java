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
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.builtins.NumberPrototypeBuiltins;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.JSTypesGen;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.cast.JSToIntegerAsIntNode;
import com.oracle.truffle.js.nodes.cast.JSToNumberNode;
import com.oracle.truffle.js.nodes.cast.JSToStringNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.builtins.JSNumberObject;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=NumberPrototypeBuiltins.class)
public final class NumberPrototypeBuiltinsFactory {
    private static final LibraryFactory<InteropLibrary> INTEROP_LIBRARY_ = LibraryFactory.resolve(InteropLibrary.class);

    @GeneratedBy(value=NumberPrototypeBuiltins.JSNumberToPrecisionNode.class)
    public static final class JSNumberToPrecisionNodeGen
    extends NumberPrototypeBuiltins.JSNumberToPrecisionNode
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
        private JSToStringNode toString;
        @Node.Child
        private JSToNumberNode toNumber;
        @Node.Child
        private ToPrecisionForeignObjectUndefined0Data toPrecisionForeignObjectUndefined0_cache;
        @Node.Child
        private JSToStringNode toPrecisionForeignObjectUndefined1_toStringNode_;
        @Node.Child
        private ToPrecisionForeignObject0Data toPrecisionForeignObject0_cache;
        @Node.Child
        private JSToNumberNode toPrecisionForeignObject1_toNumberNode_;

        private JSNumberToPrecisionNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
                if ((state_0 & 3) != 0 && arguments0Value_ instanceof JSDynamicObject) {
                    JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
                    if ((state_0 & 1) != 0 && JSGuards.isJSNumber(arguments0Value__) && JSGuards.isUndefined(arguments1Value_)) {
                        return this.toPrecisionUndefined(arguments0Value__, arguments1Value_, this.toString);
                    }
                    if ((state_0 & 2) != 0 && JSGuards.isJSNumber(arguments0Value__) && !JSGuards.isUndefined(arguments1Value_)) {
                        return this.toPrecision(arguments0Value__, arguments1Value_, this.toNumber);
                    }
                }
                if ((state_0 & 0x1FC) != 0) {
                    if ((state_0 & 4) != 0 && JSGuards.isJavaNumber(arguments0Value_) && JSGuards.isUndefined(arguments1Value_)) {
                        return this.toPrecisionPrimitiveUndefined(arguments0Value_, arguments1Value_, this.toString);
                    }
                    if ((state_0 & 8) != 0 && JSGuards.isJavaNumber(arguments0Value_) && !JSGuards.isUndefined(arguments1Value_)) {
                        return this.toPrecisionPrimitive(arguments0Value_, arguments1Value_, this.toNumber);
                    }
                    if ((state_0 & 0x10) != 0) {
                        ToPrecisionForeignObjectUndefined0Data s4_ = this.toPrecisionForeignObjectUndefined0_cache;
                        while (s4_ != null) {
                            if (s4_.interop_.accepts(arguments0Value_) && JSGuards.isForeignObject(arguments0Value_) && JSGuards.isUndefined(arguments1Value_)) {
                                return this.toPrecisionForeignObjectUndefined(arguments0Value_, arguments1Value_, s4_.toStringNode_, s4_.interop_);
                            }
                            s4_ = s4_.next_;
                        }
                    }
                    if ((state_0 & 0x20) != 0 && JSGuards.isForeignObject(arguments0Value_) && JSGuards.isUndefined(arguments1Value_)) {
                        return this.toPrecisionForeignObjectUndefined1Boundary(state_0, arguments0Value_, arguments1Value_);
                    }
                    if ((state_0 & 0x40) != 0) {
                        ToPrecisionForeignObject0Data s6_ = this.toPrecisionForeignObject0_cache;
                        while (s6_ != null) {
                            if (s6_.interop_.accepts(arguments0Value_) && JSGuards.isForeignObject(arguments0Value_) && !JSGuards.isUndefined(arguments1Value_)) {
                                return this.toPrecisionForeignObject(arguments0Value_, arguments1Value_, s6_.toNumberNode_, s6_.interop_);
                            }
                            s6_ = s6_.next_;
                        }
                    }
                    if ((state_0 & 0x80) != 0 && JSGuards.isForeignObject(arguments0Value_) && !JSGuards.isUndefined(arguments1Value_)) {
                        return this.toPrecisionForeignObject1Boundary(state_0, arguments0Value_, arguments1Value_);
                    }
                    if (!((state_0 & 0x100) == 0 || JSGuards.isJSNumber(arguments0Value_) || JSGuards.isJavaNumber(arguments0Value_) || JSGuards.isForeignObject(arguments0Value_))) {
                        return this.toPrecisionOther(arguments0Value_, arguments1Value_);
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
        private Object toPrecisionForeignObjectUndefined1Boundary(int state_0, Object arguments0Value_, Object arguments1Value_) {
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this);
            try {
                InteropLibrary toPrecisionForeignObjectUndefined1_interop__ = INTEROP_LIBRARY_.getUncached(arguments0Value_);
                Object object = this.toPrecisionForeignObjectUndefined(arguments0Value_, arguments1Value_, this.toPrecisionForeignObjectUndefined1_toStringNode_, toPrecisionForeignObjectUndefined1_interop__);
                return object;
            }
            finally {
                encapsulating_.set(prev_);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @CompilerDirectives.TruffleBoundary
        private Object toPrecisionForeignObject1Boundary(int state_0, Object arguments0Value_, Object arguments1Value_) {
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this);
            try {
                InteropLibrary toPrecisionForeignObject1_interop__ = INTEROP_LIBRARY_.getUncached(arguments0Value_);
                Object object = this.toPrecisionForeignObject(arguments0Value_, arguments1Value_, this.toPrecisionForeignObject1_toNumberNode_, toPrecisionForeignObject1_interop__);
                return object;
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
        private Object executeAndSpecialize(Object arguments0Value, Object arguments1Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                Object arguments0Value_;
                int state_0 = this.state_0_;
                int exclude = this.exclude_;
                if (arguments0Value instanceof JSDynamicObject) {
                    arguments0Value_ = (JSDynamicObject)arguments0Value;
                    if (JSGuards.isJSNumber(arguments0Value_) && JSGuards.isUndefined(arguments1Value)) {
                        this.toString = super.insert(this.toString == null ? JSToStringNode.create() : this.toString);
                        this.state_0_ = state_0 |= 1;
                        lock.unlock();
                        hasLock = false;
                        Object object = this.toPrecisionUndefined((JSDynamicObject)arguments0Value_, arguments1Value, this.toString);
                        return object;
                    }
                    if (JSGuards.isJSNumber(arguments0Value_) && !JSGuards.isUndefined(arguments1Value)) {
                        this.toNumber = super.insert(this.toNumber == null ? JSToNumberNode.create() : this.toNumber);
                        this.state_0_ = state_0 |= 2;
                        lock.unlock();
                        hasLock = false;
                        Object object = this.toPrecision((JSDynamicObject)arguments0Value_, arguments1Value, this.toNumber);
                        return object;
                    }
                }
                if (JSGuards.isJavaNumber(arguments0Value) && JSGuards.isUndefined(arguments1Value)) {
                    this.toString = super.insert(this.toString == null ? JSToStringNode.create() : this.toString);
                    this.state_0_ = state_0 |= 4;
                    lock.unlock();
                    hasLock = false;
                    arguments0Value_ = this.toPrecisionPrimitiveUndefined(arguments0Value, arguments1Value, this.toString);
                    return arguments0Value_;
                }
                if (JSGuards.isJavaNumber(arguments0Value) && !JSGuards.isUndefined(arguments1Value)) {
                    this.toNumber = super.insert(this.toNumber == null ? JSToNumberNode.create() : this.toNumber);
                    this.state_0_ = state_0 |= 8;
                    lock.unlock();
                    hasLock = false;
                    arguments0Value_ = this.toPrecisionPrimitive(arguments0Value, arguments1Value, this.toNumber);
                    return arguments0Value_;
                }
                if ((exclude & 1) == 0) {
                    int count4_ = 0;
                    ToPrecisionForeignObjectUndefined0Data s4_ = this.toPrecisionForeignObjectUndefined0_cache;
                    if ((state_0 & 0x10) != 0) {
                        while (!(s4_ == null || s4_.interop_.accepts(arguments0Value) && JSGuards.isForeignObject(arguments0Value) && JSGuards.isUndefined(arguments1Value))) {
                            s4_ = s4_.next_;
                            ++count4_;
                        }
                    }
                    if (s4_ == null && JSGuards.isForeignObject(arguments0Value) && JSGuards.isUndefined(arguments1Value) && count4_ < 5) {
                        s4_ = super.insert(new ToPrecisionForeignObjectUndefined0Data(this.toPrecisionForeignObjectUndefined0_cache));
                        s4_.toStringNode_ = s4_.insertAccessor(JSToStringNode.create());
                        s4_.interop_ = s4_.insertAccessor(INTEROP_LIBRARY_.create(arguments0Value));
                        VarHandle.storeStoreFence();
                        this.toPrecisionForeignObjectUndefined0_cache = s4_;
                        this.state_0_ = state_0 |= 0x10;
                    }
                    if (s4_ != null) {
                        lock.unlock();
                        hasLock = false;
                        Object object = this.toPrecisionForeignObjectUndefined(arguments0Value, arguments1Value, s4_.toStringNode_, s4_.interop_);
                        return object;
                    }
                }
                InteropLibrary toPrecisionForeignObjectUndefined1_interop__ = null;
                EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                Object prev_ = encapsulating_.set(this);
                try {
                    if (JSGuards.isForeignObject(arguments0Value) && JSGuards.isUndefined(arguments1Value)) {
                        this.toPrecisionForeignObjectUndefined1_toStringNode_ = super.insert(JSToStringNode.create());
                        toPrecisionForeignObjectUndefined1_interop__ = INTEROP_LIBRARY_.getUncached(arguments0Value);
                        this.exclude_ = exclude |= 1;
                        this.toPrecisionForeignObjectUndefined0_cache = null;
                        state_0 &= 0xFFFFFFEF;
                        this.state_0_ = state_0 |= 0x20;
                        lock.unlock();
                        hasLock = false;
                        Object object = this.toPrecisionForeignObjectUndefined(arguments0Value, arguments1Value, this.toPrecisionForeignObjectUndefined1_toStringNode_, toPrecisionForeignObjectUndefined1_interop__);
                        return object;
                    }
                }
                finally {
                    encapsulating_.set((Node)prev_);
                }
                if ((exclude & 2) == 0) {
                    int count6_ = 0;
                    ToPrecisionForeignObject0Data s6_ = this.toPrecisionForeignObject0_cache;
                    if ((state_0 & 0x40) != 0) {
                        while (!(s6_ == null || s6_.interop_.accepts(arguments0Value) && JSGuards.isForeignObject(arguments0Value) && !JSGuards.isUndefined(arguments1Value))) {
                            s6_ = s6_.next_;
                            ++count6_;
                        }
                    }
                    if (s6_ == null && JSGuards.isForeignObject(arguments0Value) && !JSGuards.isUndefined(arguments1Value) && count6_ < 5) {
                        s6_ = super.insert(new ToPrecisionForeignObject0Data(this.toPrecisionForeignObject0_cache));
                        s6_.toNumberNode_ = s6_.insertAccessor(JSToNumberNode.create());
                        s6_.interop_ = s6_.insertAccessor(INTEROP_LIBRARY_.create(arguments0Value));
                        VarHandle.storeStoreFence();
                        this.toPrecisionForeignObject0_cache = s6_;
                        this.state_0_ = state_0 |= 0x40;
                    }
                    if (s6_ != null) {
                        lock.unlock();
                        hasLock = false;
                        prev_ = this.toPrecisionForeignObject(arguments0Value, arguments1Value, s6_.toNumberNode_, s6_.interop_);
                        return prev_;
                    }
                }
                InteropLibrary toPrecisionForeignObject1_interop__ = null;
                encapsulating_ = EncapsulatingNodeReference.getCurrent();
                prev_ = encapsulating_.set(this);
                try {
                    if (JSGuards.isForeignObject(arguments0Value) && !JSGuards.isUndefined(arguments1Value)) {
                        this.toPrecisionForeignObject1_toNumberNode_ = super.insert(JSToNumberNode.create());
                        toPrecisionForeignObject1_interop__ = INTEROP_LIBRARY_.getUncached(arguments0Value);
                        this.exclude_ = exclude |= 2;
                        this.toPrecisionForeignObject0_cache = null;
                        state_0 &= 0xFFFFFFBF;
                        this.state_0_ = state_0 |= 0x80;
                        lock.unlock();
                        hasLock = false;
                        Object object = this.toPrecisionForeignObject(arguments0Value, arguments1Value, this.toPrecisionForeignObject1_toNumberNode_, toPrecisionForeignObject1_interop__);
                        return object;
                    }
                }
                finally {
                    encapsulating_.set((Node)prev_);
                }
                if (!(JSGuards.isJSNumber(arguments0Value) || JSGuards.isJavaNumber(arguments0Value) || JSGuards.isForeignObject(arguments0Value))) {
                    this.state_0_ = state_0 |= 0x100;
                    lock.unlock();
                    hasLock = false;
                    Object object = this.toPrecisionOther(arguments0Value, arguments1Value);
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
                ToPrecisionForeignObjectUndefined0Data s4_ = this.toPrecisionForeignObjectUndefined0_cache;
                ToPrecisionForeignObject0Data s6_ = this.toPrecisionForeignObject0_cache;
                if (!(s4_ != null && s4_.next_ != null || s6_ != null && s6_.next_ != null)) {
                    return NodeCost.MONOMORPHIC;
                }
            }
            return NodeCost.POLYMORPHIC;
        }

        @Override
        public Introspection getIntrospectionData() {
            ArrayList<List<Node>> cached;
            Object[] data = new Object[10];
            data[0] = 0;
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            Object[] s = new Object[3];
            s[0] = "toPrecisionUndefined";
            if ((state_0 & 1) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList<List<Node>>();
                cached.add(Arrays.asList(this.toString));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[1] = s;
            s = new Object[3];
            s[0] = "toPrecision";
            if ((state_0 & 2) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList();
                cached.add(Arrays.asList(this.toNumber));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[2] = s;
            s = new Object[3];
            s[0] = "toPrecisionPrimitiveUndefined";
            if ((state_0 & 4) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList();
                cached.add(Arrays.asList(this.toString));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[3] = s;
            s = new Object[3];
            s[0] = "toPrecisionPrimitive";
            if ((state_0 & 8) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList();
                cached.add(Arrays.asList(this.toNumber));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[4] = s;
            s = new Object[3];
            s[0] = "toPrecisionForeignObjectUndefined";
            if ((state_0 & 0x10) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList();
                ToPrecisionForeignObjectUndefined0Data s4_ = this.toPrecisionForeignObjectUndefined0_cache;
                while (s4_ != null) {
                    cached.add(Arrays.asList(s4_.toStringNode_, s4_.interop_));
                    s4_ = s4_.next_;
                }
                s[2] = cached;
            } else {
                s[1] = (exclude & 1) != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0);
            }
            data[5] = s;
            s = new Object[3];
            s[0] = "toPrecisionForeignObjectUndefined";
            if ((state_0 & 0x20) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList();
                cached.add(Arrays.asList(this.toPrecisionForeignObjectUndefined1_toStringNode_));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[6] = s;
            s = new Object[3];
            s[0] = "toPrecisionForeignObject";
            if ((state_0 & 0x40) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList();
                ToPrecisionForeignObject0Data s6_ = this.toPrecisionForeignObject0_cache;
                while (s6_ != null) {
                    cached.add(Arrays.asList(s6_.toNumberNode_, s6_.interop_));
                    s6_ = s6_.next_;
                }
                s[2] = cached;
            } else {
                s[1] = (exclude & 2) != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0);
            }
            data[7] = s;
            s = new Object[3];
            s[0] = "toPrecisionForeignObject";
            if ((state_0 & 0x80) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList();
                cached.add(Arrays.asList(this.toPrecisionForeignObject1_toNumberNode_));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[8] = s;
            s = new Object[3];
            s[0] = "toPrecisionOther";
            s[1] = (state_0 & 0x100) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[9] = s;
            return Introspection.Provider.create(data);
        }

        public static NumberPrototypeBuiltins.JSNumberToPrecisionNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSNumberToPrecisionNodeGen(context, builtin, arguments);
        }

        @GeneratedBy(value=NumberPrototypeBuiltins.JSNumberToPrecisionNode.class)
        private static final class ToPrecisionForeignObject0Data
        extends Node {
            @Node.Child
            ToPrecisionForeignObject0Data next_;
            @Node.Child
            JSToNumberNode toNumberNode_;
            @Node.Child
            InteropLibrary interop_;

            ToPrecisionForeignObject0Data(ToPrecisionForeignObject0Data next_) {
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

        @GeneratedBy(value=NumberPrototypeBuiltins.JSNumberToPrecisionNode.class)
        private static final class ToPrecisionForeignObjectUndefined0Data
        extends Node {
            @Node.Child
            ToPrecisionForeignObjectUndefined0Data next_;
            @Node.Child
            JSToStringNode toStringNode_;
            @Node.Child
            InteropLibrary interop_;

            ToPrecisionForeignObjectUndefined0Data(ToPrecisionForeignObjectUndefined0Data next_) {
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

    @GeneratedBy(value=NumberPrototypeBuiltins.JSNumberToExponentialNode.class)
    public static final class JSNumberToExponentialNodeGen
    extends NumberPrototypeBuiltins.JSNumberToExponentialNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @CompilerDirectives.CompilationFinal
        private volatile int exclude_;
        @CompilerDirectives.CompilationFinal
        private BranchProfile digitsError;
        @Node.Child
        private JSToIntegerAsIntNode toInt;
        @Node.Child
        private ToExponentialForeignObjectUndefined0Data toExponentialForeignObjectUndefined0_cache;
        @Node.Child
        private ToExponentialForeignObject0Data toExponentialForeignObject0_cache;
        @CompilerDirectives.CompilationFinal
        private BranchProfile toExponentialForeignObject1_digitsErrorBranch_;
        @Node.Child
        private JSToIntegerAsIntNode toExponentialForeignObject1_toIntegerNode_;

        private JSNumberToExponentialNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
                if ((state_0 & 3) != 0 && arguments0Value_ instanceof JSDynamicObject) {
                    JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
                    if ((state_0 & 1) != 0 && JSGuards.isJSNumber(arguments0Value__) && JSGuards.isUndefined(arguments1Value_)) {
                        return this.toExponentialUndefined(arguments0Value__, arguments1Value_);
                    }
                    if ((state_0 & 2) != 0 && JSGuards.isJSNumber(arguments0Value__) && !JSGuards.isUndefined(arguments1Value_)) {
                        return this.toExponential(arguments0Value__, arguments1Value_, this.digitsError, this.toInt);
                    }
                }
                if ((state_0 & 0x1FC) != 0) {
                    if ((state_0 & 4) != 0 && JSGuards.isJavaNumber(arguments0Value_) && JSGuards.isUndefined(arguments1Value_)) {
                        return this.toExponentialPrimitiveUndefined(arguments0Value_, arguments1Value_);
                    }
                    if ((state_0 & 8) != 0 && JSGuards.isJavaNumber(arguments0Value_) && !JSGuards.isUndefined(arguments1Value_)) {
                        return this.toExponentialPrimitive(arguments0Value_, arguments1Value_, this.digitsError, this.toInt);
                    }
                    if ((state_0 & 0x10) != 0) {
                        ToExponentialForeignObjectUndefined0Data s4_ = this.toExponentialForeignObjectUndefined0_cache;
                        while (s4_ != null) {
                            if (s4_.interop_.accepts(arguments0Value_) && JSGuards.isForeignObject(arguments0Value_) && JSGuards.isUndefined(arguments1Value_)) {
                                return this.toExponentialForeignObjectUndefined(arguments0Value_, arguments1Value_, s4_.interop_);
                            }
                            s4_ = s4_.next_;
                        }
                    }
                    if ((state_0 & 0x20) != 0 && JSGuards.isForeignObject(arguments0Value_) && JSGuards.isUndefined(arguments1Value_)) {
                        return this.toExponentialForeignObjectUndefined1Boundary(state_0, arguments0Value_, arguments1Value_);
                    }
                    if ((state_0 & 0x40) != 0) {
                        ToExponentialForeignObject0Data s6_ = this.toExponentialForeignObject0_cache;
                        while (s6_ != null) {
                            if (s6_.interop_.accepts(arguments0Value_) && JSGuards.isForeignObject(arguments0Value_) && !JSGuards.isUndefined(arguments1Value_)) {
                                return this.toExponentialForeignObject(arguments0Value_, arguments1Value_, s6_.digitsErrorBranch_, s6_.toIntegerNode_, s6_.interop_);
                            }
                            s6_ = s6_.next_;
                        }
                    }
                    if ((state_0 & 0x80) != 0 && JSGuards.isForeignObject(arguments0Value_) && !JSGuards.isUndefined(arguments1Value_)) {
                        return this.toExponentialForeignObject1Boundary(state_0, arguments0Value_, arguments1Value_);
                    }
                    if (!((state_0 & 0x100) == 0 || JSGuards.isJSNumber(arguments0Value_) || JSGuards.isJavaNumber(arguments0Value_) || JSGuards.isForeignObject(arguments0Value_))) {
                        return this.toExponentialOther(arguments0Value_, arguments1Value_);
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
        private Object toExponentialForeignObjectUndefined1Boundary(int state_0, Object arguments0Value_, Object arguments1Value_) {
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this);
            try {
                InteropLibrary toExponentialForeignObjectUndefined1_interop__ = INTEROP_LIBRARY_.getUncached(arguments0Value_);
                Object object = this.toExponentialForeignObjectUndefined(arguments0Value_, arguments1Value_, toExponentialForeignObjectUndefined1_interop__);
                return object;
            }
            finally {
                encapsulating_.set(prev_);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @CompilerDirectives.TruffleBoundary
        private Object toExponentialForeignObject1Boundary(int state_0, Object arguments0Value_, Object arguments1Value_) {
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this);
            try {
                InteropLibrary toExponentialForeignObject1_interop__ = INTEROP_LIBRARY_.getUncached(arguments0Value_);
                Object object = this.toExponentialForeignObject(arguments0Value_, arguments1Value_, this.toExponentialForeignObject1_digitsErrorBranch_, this.toExponentialForeignObject1_toIntegerNode_, toExponentialForeignObject1_interop__);
                return object;
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
        private Object executeAndSpecialize(Object arguments0Value, Object arguments1Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                Object arguments0Value_;
                int state_0 = this.state_0_;
                int exclude = this.exclude_;
                if (arguments0Value instanceof JSDynamicObject) {
                    arguments0Value_ = (JSDynamicObject)arguments0Value;
                    if (JSGuards.isJSNumber(arguments0Value_) && JSGuards.isUndefined(arguments1Value)) {
                        this.state_0_ = state_0 |= 1;
                        lock.unlock();
                        hasLock = false;
                        Object object = this.toExponentialUndefined((JSDynamicObject)arguments0Value_, arguments1Value);
                        return object;
                    }
                    if (JSGuards.isJSNumber(arguments0Value_) && !JSGuards.isUndefined(arguments1Value)) {
                        this.digitsError = this.digitsError == null ? BranchProfile.create() : this.digitsError;
                        this.toInt = super.insert(this.toInt == null ? JSToIntegerAsIntNode.create() : this.toInt);
                        this.state_0_ = state_0 |= 2;
                        lock.unlock();
                        hasLock = false;
                        Object object = this.toExponential((JSDynamicObject)arguments0Value_, arguments1Value, this.digitsError, this.toInt);
                        return object;
                    }
                }
                if (JSGuards.isJavaNumber(arguments0Value) && JSGuards.isUndefined(arguments1Value)) {
                    this.state_0_ = state_0 |= 4;
                    lock.unlock();
                    hasLock = false;
                    arguments0Value_ = this.toExponentialPrimitiveUndefined(arguments0Value, arguments1Value);
                    return arguments0Value_;
                }
                if (JSGuards.isJavaNumber(arguments0Value) && !JSGuards.isUndefined(arguments1Value)) {
                    this.digitsError = this.digitsError == null ? BranchProfile.create() : this.digitsError;
                    this.toInt = super.insert(this.toInt == null ? JSToIntegerAsIntNode.create() : this.toInt);
                    this.state_0_ = state_0 |= 8;
                    lock.unlock();
                    hasLock = false;
                    arguments0Value_ = this.toExponentialPrimitive(arguments0Value, arguments1Value, this.digitsError, this.toInt);
                    return arguments0Value_;
                }
                if ((exclude & 1) == 0) {
                    int count4_ = 0;
                    ToExponentialForeignObjectUndefined0Data s4_ = this.toExponentialForeignObjectUndefined0_cache;
                    if ((state_0 & 0x10) != 0) {
                        while (!(s4_ == null || s4_.interop_.accepts(arguments0Value) && JSGuards.isForeignObject(arguments0Value) && JSGuards.isUndefined(arguments1Value))) {
                            s4_ = s4_.next_;
                            ++count4_;
                        }
                    }
                    if (s4_ == null && JSGuards.isForeignObject(arguments0Value) && JSGuards.isUndefined(arguments1Value) && count4_ < 5) {
                        s4_ = super.insert(new ToExponentialForeignObjectUndefined0Data(this.toExponentialForeignObjectUndefined0_cache));
                        s4_.interop_ = s4_.insertAccessor(INTEROP_LIBRARY_.create(arguments0Value));
                        VarHandle.storeStoreFence();
                        this.toExponentialForeignObjectUndefined0_cache = s4_;
                        this.state_0_ = state_0 |= 0x10;
                    }
                    if (s4_ != null) {
                        lock.unlock();
                        hasLock = false;
                        Object object = this.toExponentialForeignObjectUndefined(arguments0Value, arguments1Value, s4_.interop_);
                        return object;
                    }
                }
                InteropLibrary toExponentialForeignObjectUndefined1_interop__ = null;
                EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                Object prev_ = encapsulating_.set(this);
                try {
                    if (JSGuards.isForeignObject(arguments0Value) && JSGuards.isUndefined(arguments1Value)) {
                        toExponentialForeignObjectUndefined1_interop__ = INTEROP_LIBRARY_.getUncached(arguments0Value);
                        this.exclude_ = exclude |= 1;
                        this.toExponentialForeignObjectUndefined0_cache = null;
                        state_0 &= 0xFFFFFFEF;
                        this.state_0_ = state_0 |= 0x20;
                        lock.unlock();
                        hasLock = false;
                        Object object = this.toExponentialForeignObjectUndefined(arguments0Value, arguments1Value, toExponentialForeignObjectUndefined1_interop__);
                        return object;
                    }
                }
                finally {
                    encapsulating_.set((Node)prev_);
                }
                if ((exclude & 2) == 0) {
                    int count6_ = 0;
                    ToExponentialForeignObject0Data s6_ = this.toExponentialForeignObject0_cache;
                    if ((state_0 & 0x40) != 0) {
                        while (!(s6_ == null || s6_.interop_.accepts(arguments0Value) && JSGuards.isForeignObject(arguments0Value) && !JSGuards.isUndefined(arguments1Value))) {
                            s6_ = s6_.next_;
                            ++count6_;
                        }
                    }
                    if (s6_ == null && JSGuards.isForeignObject(arguments0Value) && !JSGuards.isUndefined(arguments1Value) && count6_ < 5) {
                        s6_ = super.insert(new ToExponentialForeignObject0Data(this.toExponentialForeignObject0_cache));
                        s6_.digitsErrorBranch_ = BranchProfile.create();
                        s6_.toIntegerNode_ = s6_.insertAccessor(JSToIntegerAsIntNode.create());
                        s6_.interop_ = s6_.insertAccessor(INTEROP_LIBRARY_.create(arguments0Value));
                        VarHandle.storeStoreFence();
                        this.toExponentialForeignObject0_cache = s6_;
                        this.state_0_ = state_0 |= 0x40;
                    }
                    if (s6_ != null) {
                        lock.unlock();
                        hasLock = false;
                        prev_ = this.toExponentialForeignObject(arguments0Value, arguments1Value, s6_.digitsErrorBranch_, s6_.toIntegerNode_, s6_.interop_);
                        return prev_;
                    }
                }
                InteropLibrary toExponentialForeignObject1_interop__ = null;
                encapsulating_ = EncapsulatingNodeReference.getCurrent();
                prev_ = encapsulating_.set(this);
                try {
                    if (JSGuards.isForeignObject(arguments0Value) && !JSGuards.isUndefined(arguments1Value)) {
                        this.toExponentialForeignObject1_digitsErrorBranch_ = BranchProfile.create();
                        this.toExponentialForeignObject1_toIntegerNode_ = super.insert(JSToIntegerAsIntNode.create());
                        toExponentialForeignObject1_interop__ = INTEROP_LIBRARY_.getUncached(arguments0Value);
                        this.exclude_ = exclude |= 2;
                        this.toExponentialForeignObject0_cache = null;
                        state_0 &= 0xFFFFFFBF;
                        this.state_0_ = state_0 |= 0x80;
                        lock.unlock();
                        hasLock = false;
                        Object object = this.toExponentialForeignObject(arguments0Value, arguments1Value, this.toExponentialForeignObject1_digitsErrorBranch_, this.toExponentialForeignObject1_toIntegerNode_, toExponentialForeignObject1_interop__);
                        return object;
                    }
                }
                finally {
                    encapsulating_.set((Node)prev_);
                }
                if (!(JSGuards.isJSNumber(arguments0Value) || JSGuards.isJavaNumber(arguments0Value) || JSGuards.isForeignObject(arguments0Value))) {
                    this.state_0_ = state_0 |= 0x100;
                    lock.unlock();
                    hasLock = false;
                    Object object = this.toExponentialOther(arguments0Value, arguments1Value);
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
                ToExponentialForeignObjectUndefined0Data s4_ = this.toExponentialForeignObjectUndefined0_cache;
                ToExponentialForeignObject0Data s6_ = this.toExponentialForeignObject0_cache;
                if (!(s4_ != null && s4_.next_ != null || s6_ != null && s6_.next_ != null)) {
                    return NodeCost.MONOMORPHIC;
                }
            }
            return NodeCost.POLYMORPHIC;
        }

        @Override
        public Introspection getIntrospectionData() {
            ArrayList<List<Object>> cached;
            Object[] data = new Object[10];
            data[0] = 0;
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            Object[] s = new Object[3];
            s[0] = "toExponentialUndefined";
            s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[1] = s;
            s = new Object[3];
            s[0] = "toExponential";
            if ((state_0 & 2) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList<List<Object>>();
                cached.add(Arrays.asList(this.digitsError, this.toInt));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[2] = s;
            s = new Object[3];
            s[0] = "toExponentialPrimitiveUndefined";
            s[1] = (state_0 & 4) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[3] = s;
            s = new Object[3];
            s[0] = "toExponentialPrimitive";
            if ((state_0 & 8) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList();
                cached.add(Arrays.asList(this.digitsError, this.toInt));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[4] = s;
            s = new Object[3];
            s[0] = "toExponentialForeignObjectUndefined";
            if ((state_0 & 0x10) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList();
                ToExponentialForeignObjectUndefined0Data s4_ = this.toExponentialForeignObjectUndefined0_cache;
                while (s4_ != null) {
                    cached.add(Arrays.asList(s4_.interop_));
                    s4_ = s4_.next_;
                }
                s[2] = cached;
            } else {
                s[1] = (exclude & 1) != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0);
            }
            data[5] = s;
            s = new Object[3];
            s[0] = "toExponentialForeignObjectUndefined";
            if ((state_0 & 0x20) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList();
                cached.add(Arrays.asList(new Object[0]));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[6] = s;
            s = new Object[3];
            s[0] = "toExponentialForeignObject";
            if ((state_0 & 0x40) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList();
                ToExponentialForeignObject0Data s6_ = this.toExponentialForeignObject0_cache;
                while (s6_ != null) {
                    cached.add(Arrays.asList(s6_.digitsErrorBranch_, s6_.toIntegerNode_, s6_.interop_));
                    s6_ = s6_.next_;
                }
                s[2] = cached;
            } else {
                s[1] = (exclude & 2) != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0);
            }
            data[7] = s;
            s = new Object[3];
            s[0] = "toExponentialForeignObject";
            if ((state_0 & 0x80) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList();
                cached.add(Arrays.asList(this.toExponentialForeignObject1_digitsErrorBranch_, this.toExponentialForeignObject1_toIntegerNode_));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[8] = s;
            s = new Object[3];
            s[0] = "toExponentialOther";
            s[1] = (state_0 & 0x100) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[9] = s;
            return Introspection.Provider.create(data);
        }

        public static NumberPrototypeBuiltins.JSNumberToExponentialNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSNumberToExponentialNodeGen(context, builtin, arguments);
        }

        @GeneratedBy(value=NumberPrototypeBuiltins.JSNumberToExponentialNode.class)
        private static final class ToExponentialForeignObject0Data
        extends Node {
            @Node.Child
            ToExponentialForeignObject0Data next_;
            @CompilerDirectives.CompilationFinal
            BranchProfile digitsErrorBranch_;
            @Node.Child
            JSToIntegerAsIntNode toIntegerNode_;
            @Node.Child
            InteropLibrary interop_;

            ToExponentialForeignObject0Data(ToExponentialForeignObject0Data next_) {
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

        @GeneratedBy(value=NumberPrototypeBuiltins.JSNumberToExponentialNode.class)
        private static final class ToExponentialForeignObjectUndefined0Data
        extends Node {
            @Node.Child
            ToExponentialForeignObjectUndefined0Data next_;
            @Node.Child
            InteropLibrary interop_;

            ToExponentialForeignObjectUndefined0Data(ToExponentialForeignObjectUndefined0Data next_) {
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

    @GeneratedBy(value=NumberPrototypeBuiltins.JSNumberToFixedNode.class)
    public static final class JSNumberToFixedNodeGen
    extends NumberPrototypeBuiltins.JSNumberToFixedNode
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
        private JSToIntegerAsIntNode toInt;
        @Node.Child
        private ToFixedForeignObject0Data toFixedForeignObject0_cache;
        @Node.Child
        private JSToIntegerAsIntNode toFixedForeignObject1_toIntegerNode_;

        private JSNumberToFixedNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
                if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSDynamicObject && JSGuards.isJSNumber(arguments0Value__ = (JSDynamicObject)arguments0Value_)) {
                    return this.toFixed(arguments0Value__, arguments1Value_, this.toInt);
                }
                if ((state_0 & 0x1E) != 0) {
                    if ((state_0 & 2) != 0 && JSGuards.isJavaNumber(arguments0Value_)) {
                        return this.toFixedJava(arguments0Value_, arguments1Value_, this.toInt);
                    }
                    if ((state_0 & 4) != 0) {
                        ToFixedForeignObject0Data s2_ = this.toFixedForeignObject0_cache;
                        while (s2_ != null) {
                            if (s2_.interop_.accepts(arguments0Value_) && JSGuards.isForeignObject(arguments0Value_)) {
                                return this.toFixedForeignObject(arguments0Value_, arguments1Value_, s2_.toIntegerNode_, s2_.interop_);
                            }
                            s2_ = s2_.next_;
                        }
                    }
                    if ((state_0 & 8) != 0 && JSGuards.isForeignObject(arguments0Value_)) {
                        return this.toFixedForeignObject1Boundary(state_0, arguments0Value_, arguments1Value_);
                    }
                    if ((state_0 & 0x10) != 0 && JSNumberToFixedNodeGen.fallbackGuard_(state_0, arguments0Value_, arguments1Value_)) {
                        return this.toFixedGeneric(arguments0Value_, arguments1Value_);
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
        private Object toFixedForeignObject1Boundary(int state_0, Object arguments0Value_, Object arguments1Value_) {
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this);
            try {
                InteropLibrary toFixedForeignObject1_interop__ = INTEROP_LIBRARY_.getUncached(arguments0Value_);
                Object object = this.toFixedForeignObject(arguments0Value_, arguments1Value_, this.toFixedForeignObject1_toIntegerNode_, toFixedForeignObject1_interop__);
                return object;
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
        private Object executeAndSpecialize(Object arguments0Value, Object arguments1Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                Object arguments0Value_;
                int state_0 = this.state_0_;
                int exclude = this.exclude_;
                if (arguments0Value instanceof JSDynamicObject && JSGuards.isJSNumber(arguments0Value_ = (JSDynamicObject)arguments0Value)) {
                    this.toInt = super.insert(this.toInt == null ? JSToIntegerAsIntNode.create() : this.toInt);
                    this.state_0_ = state_0 |= 1;
                    lock.unlock();
                    hasLock = false;
                    Object object = this.toFixed((JSDynamicObject)arguments0Value_, arguments1Value, this.toInt);
                    return object;
                }
                if (JSGuards.isJavaNumber(arguments0Value)) {
                    this.toInt = super.insert(this.toInt == null ? JSToIntegerAsIntNode.create() : this.toInt);
                    this.state_0_ = state_0 |= 2;
                    lock.unlock();
                    hasLock = false;
                    arguments0Value_ = this.toFixedJava(arguments0Value, arguments1Value, this.toInt);
                    return arguments0Value_;
                }
                if (exclude == 0) {
                    int count2_ = 0;
                    ToFixedForeignObject0Data s2_ = this.toFixedForeignObject0_cache;
                    if ((state_0 & 4) != 0) {
                        while (!(s2_ == null || s2_.interop_.accepts(arguments0Value) && JSGuards.isForeignObject(arguments0Value))) {
                            s2_ = s2_.next_;
                            ++count2_;
                        }
                    }
                    if (s2_ == null && JSGuards.isForeignObject(arguments0Value) && count2_ < 5) {
                        s2_ = super.insert(new ToFixedForeignObject0Data(this.toFixedForeignObject0_cache));
                        s2_.toIntegerNode_ = s2_.insertAccessor(JSToIntegerAsIntNode.create());
                        s2_.interop_ = s2_.insertAccessor(INTEROP_LIBRARY_.create(arguments0Value));
                        VarHandle.storeStoreFence();
                        this.toFixedForeignObject0_cache = s2_;
                        this.state_0_ = state_0 |= 4;
                    }
                    if (s2_ != null) {
                        lock.unlock();
                        hasLock = false;
                        Object object = this.toFixedForeignObject(arguments0Value, arguments1Value, s2_.toIntegerNode_, s2_.interop_);
                        return object;
                    }
                }
                InteropLibrary toFixedForeignObject1_interop__ = null;
                EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                Node prev_ = encapsulating_.set(this);
                try {
                    if (JSGuards.isForeignObject(arguments0Value)) {
                        this.toFixedForeignObject1_toIntegerNode_ = super.insert(JSToIntegerAsIntNode.create());
                        toFixedForeignObject1_interop__ = INTEROP_LIBRARY_.getUncached(arguments0Value);
                        this.exclude_ = exclude |= 1;
                        this.toFixedForeignObject0_cache = null;
                        state_0 &= 0xFFFFFFFB;
                        this.state_0_ = state_0 |= 8;
                        lock.unlock();
                        hasLock = false;
                        Object object = this.toFixedForeignObject(arguments0Value, arguments1Value, this.toFixedForeignObject1_toIntegerNode_, toFixedForeignObject1_interop__);
                        return object;
                    }
                }
                finally {
                    encapsulating_.set(prev_);
                }
                this.state_0_ = state_0 |= 0x10;
                lock.unlock();
                hasLock = false;
                Object object = this.toFixedGeneric(arguments0Value, arguments1Value);
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
            ToFixedForeignObject0Data s2_;
            int state_0 = this.state_0_;
            if (state_0 == 0) {
                return NodeCost.UNINITIALIZED;
            }
            if ((state_0 & state_0 - 1) == 0 && ((s2_ = this.toFixedForeignObject0_cache) == null || s2_.next_ == null)) {
                return NodeCost.MONOMORPHIC;
            }
            return NodeCost.POLYMORPHIC;
        }

        @Override
        public Introspection getIntrospectionData() {
            ArrayList<List<Node>> cached;
            Object[] data = new Object[6];
            data[0] = 0;
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            Object[] s = new Object[3];
            s[0] = "toFixed";
            if ((state_0 & 1) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList<List<Node>>();
                cached.add(Arrays.asList(this.toInt));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[1] = s;
            s = new Object[3];
            s[0] = "toFixedJava";
            if ((state_0 & 2) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList();
                cached.add(Arrays.asList(this.toInt));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[2] = s;
            s = new Object[3];
            s[0] = "toFixedForeignObject";
            if ((state_0 & 4) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList();
                ToFixedForeignObject0Data s2_ = this.toFixedForeignObject0_cache;
                while (s2_ != null) {
                    cached.add(Arrays.asList(s2_.toIntegerNode_, s2_.interop_));
                    s2_ = s2_.next_;
                }
                s[2] = cached;
            } else {
                s[1] = exclude != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0);
            }
            data[3] = s;
            s = new Object[3];
            s[0] = "toFixedForeignObject";
            if ((state_0 & 8) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList();
                cached.add(Arrays.asList(this.toFixedForeignObject1_toIntegerNode_));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[4] = s;
            s = new Object[3];
            s[0] = "toFixedGeneric";
            s[1] = (state_0 & 0x10) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[5] = s;
            return Introspection.Provider.create(data);
        }

        private static boolean fallbackGuard_(int state_0, Object arguments0Value, Object arguments1Value) {
            JSDynamicObject arguments0Value_;
            if (arguments0Value instanceof JSDynamicObject && JSGuards.isJSNumber(arguments0Value_ = (JSDynamicObject)arguments0Value)) {
                return false;
            }
            if ((state_0 & 2) == 0 && JSGuards.isJavaNumber(arguments0Value)) {
                return false;
            }
            return (state_0 & 8) != 0 || !JSGuards.isForeignObject(arguments0Value);
        }

        public static NumberPrototypeBuiltins.JSNumberToFixedNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSNumberToFixedNodeGen(context, builtin, arguments);
        }

        @GeneratedBy(value=NumberPrototypeBuiltins.JSNumberToFixedNode.class)
        private static final class ToFixedForeignObject0Data
        extends Node {
            @Node.Child
            ToFixedForeignObject0Data next_;
            @Node.Child
            JSToIntegerAsIntNode toIntegerNode_;
            @Node.Child
            InteropLibrary interop_;

            ToFixedForeignObject0Data(ToFixedForeignObject0Data next_) {
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

    @GeneratedBy(value=NumberPrototypeBuiltins.JSNumberValueOfNode.class)
    public static final class JSNumberValueOfNodeGen
    extends NumberPrototypeBuiltins.JSNumberValueOfNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @CompilerDirectives.CompilationFinal
        private volatile int exclude_;
        @Node.Child
        private ValueOfForeignObject0Data valueOfForeignObject0_cache;

        private JSNumberValueOfNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            super(context, builtin);
            this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
        }

        @Override
        public JavaScriptNode[] getArguments() {
            return new JavaScriptNode[]{this.arguments0_};
        }

        @Override
        @ExplodeLoop
        public Object execute(VirtualFrame frameValue) {
            JSDynamicObject arguments0Value__;
            int state_0 = this.state_0_;
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSDynamicObject && JSGuards.isJSNumber(arguments0Value__ = (JSDynamicObject)arguments0Value_)) {
                return this.valueOf(arguments0Value__);
            }
            if ((state_0 & 0x1E) != 0) {
                if ((state_0 & 2) != 0 && JSGuards.isJavaNumber(arguments0Value_)) {
                    return this.valueOfPrimitive(arguments0Value_);
                }
                if ((state_0 & 4) != 0) {
                    ValueOfForeignObject0Data s2_ = this.valueOfForeignObject0_cache;
                    while (s2_ != null) {
                        if (s2_.interop_.accepts(arguments0Value_) && JSGuards.isForeignObject(arguments0Value_)) {
                            return this.valueOfForeignObject(arguments0Value_, s2_.interop_);
                        }
                        s2_ = s2_.next_;
                    }
                }
                if ((state_0 & 8) != 0 && JSGuards.isForeignObject(arguments0Value_)) {
                    return this.valueOfForeignObject1Boundary(state_0, arguments0Value_);
                }
                if ((state_0 & 0x10) != 0 && JSNumberValueOfNodeGen.fallbackGuard_(state_0, arguments0Value_)) {
                    return this.valueOfOther(arguments0Value_);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @CompilerDirectives.TruffleBoundary
        private Object valueOfForeignObject1Boundary(int state_0, Object arguments0Value_) {
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this);
            try {
                InteropLibrary valueOfForeignObject1_interop__ = INTEROP_LIBRARY_.getUncached(arguments0Value_);
                Double d = this.valueOfForeignObject(arguments0Value_, valueOfForeignObject1_interop__);
                return d;
            }
            finally {
                encapsulating_.set(prev_);
            }
        }

        @Override
        @ExplodeLoop
        public double executeDouble(VirtualFrame frameValue) throws UnexpectedResultException {
            int state_0 = this.state_0_;
            if ((state_0 & 0x11) != 0) {
                return JSTypesGen.expectDouble(this.execute(frameValue));
            }
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            if ((state_0 & 0xE) != 0) {
                if ((state_0 & 2) != 0 && JSGuards.isJavaNumber(arguments0Value_)) {
                    return this.valueOfPrimitive(arguments0Value_);
                }
                if ((state_0 & 4) != 0) {
                    ValueOfForeignObject0Data s2_ = this.valueOfForeignObject0_cache;
                    while (s2_ != null) {
                        if (s2_.interop_.accepts(arguments0Value_) && JSGuards.isForeignObject(arguments0Value_)) {
                            return this.valueOfForeignObject(arguments0Value_, s2_.interop_);
                        }
                        s2_ = s2_.next_;
                    }
                }
                if ((state_0 & 8) != 0 && JSGuards.isForeignObject(arguments0Value_)) {
                    return this.valueOfForeignObject1Boundary0(state_0, arguments0Value_);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return JSTypesGen.expectDouble(this.executeAndSpecialize(arguments0Value_));
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @CompilerDirectives.TruffleBoundary
        private double valueOfForeignObject1Boundary0(int state_0, Object arguments0Value_) throws UnexpectedResultException {
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this);
            try {
                InteropLibrary valueOfForeignObject1_interop__ = INTEROP_LIBRARY_.getUncached(arguments0Value_);
                double d = this.valueOfForeignObject(arguments0Value_, valueOfForeignObject1_interop__);
                return d;
            }
            finally {
                encapsulating_.set(prev_);
            }
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            int state_0 = this.state_0_;
            try {
                if ((state_0 & 0x11) == 0 && state_0 != 0) {
                    this.executeDouble(frameValue);
                    return;
                }
                this.execute(frameValue);
                return;
            }
            catch (UnexpectedResultException ex) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return;
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private Object executeAndSpecialize(Object arguments0Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                Object arguments0Value_;
                int state_0 = this.state_0_;
                int exclude = this.exclude_;
                if (arguments0Value instanceof JSDynamicObject && JSGuards.isJSNumber(arguments0Value_ = (JSDynamicObject)arguments0Value)) {
                    this.state_0_ = state_0 |= 1;
                    lock.unlock();
                    hasLock = false;
                    Number number = this.valueOf((JSDynamicObject)arguments0Value_);
                    return number;
                }
                if (JSGuards.isJavaNumber(arguments0Value)) {
                    this.state_0_ = state_0 |= 2;
                    lock.unlock();
                    hasLock = false;
                    arguments0Value_ = this.valueOfPrimitive(arguments0Value);
                    return arguments0Value_;
                }
                if (exclude == 0) {
                    int count2_ = 0;
                    ValueOfForeignObject0Data s2_ = this.valueOfForeignObject0_cache;
                    if ((state_0 & 4) != 0) {
                        while (!(s2_ == null || s2_.interop_.accepts(arguments0Value) && JSGuards.isForeignObject(arguments0Value))) {
                            s2_ = s2_.next_;
                            ++count2_;
                        }
                    }
                    if (s2_ == null && JSGuards.isForeignObject(arguments0Value) && count2_ < 5) {
                        s2_ = super.insert(new ValueOfForeignObject0Data(this.valueOfForeignObject0_cache));
                        s2_.interop_ = s2_.insertAccessor(INTEROP_LIBRARY_.create(arguments0Value));
                        VarHandle.storeStoreFence();
                        this.valueOfForeignObject0_cache = s2_;
                        this.state_0_ = state_0 |= 4;
                    }
                    if (s2_ != null) {
                        lock.unlock();
                        hasLock = false;
                        Double d = this.valueOfForeignObject(arguments0Value, s2_.interop_);
                        return d;
                    }
                }
                InteropLibrary valueOfForeignObject1_interop__ = null;
                EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                Node prev_ = encapsulating_.set(this);
                try {
                    if (JSGuards.isForeignObject(arguments0Value)) {
                        valueOfForeignObject1_interop__ = INTEROP_LIBRARY_.getUncached(arguments0Value);
                        this.exclude_ = exclude |= 1;
                        this.valueOfForeignObject0_cache = null;
                        state_0 &= 0xFFFFFFFB;
                        this.state_0_ = state_0 |= 8;
                        lock.unlock();
                        hasLock = false;
                        Double d = this.valueOfForeignObject(arguments0Value, valueOfForeignObject1_interop__);
                        return d;
                    }
                }
                finally {
                    encapsulating_.set(prev_);
                }
                this.state_0_ = state_0 |= 0x10;
                lock.unlock();
                hasLock = false;
                Object object = this.valueOfOther(arguments0Value);
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
            ValueOfForeignObject0Data s2_;
            int state_0 = this.state_0_;
            if (state_0 == 0) {
                return NodeCost.UNINITIALIZED;
            }
            if ((state_0 & state_0 - 1) == 0 && ((s2_ = this.valueOfForeignObject0_cache) == null || s2_.next_ == null)) {
                return NodeCost.MONOMORPHIC;
            }
            return NodeCost.POLYMORPHIC;
        }

        @Override
        public Introspection getIntrospectionData() {
            ArrayList<List<Object>> cached;
            Object[] data = new Object[6];
            data[0] = 0;
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            Object[] s = new Object[3];
            s[0] = "valueOf";
            s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[1] = s;
            s = new Object[3];
            s[0] = "valueOfPrimitive";
            s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[2] = s;
            s = new Object[3];
            s[0] = "valueOfForeignObject";
            if ((state_0 & 4) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList<List<Object>>();
                ValueOfForeignObject0Data s2_ = this.valueOfForeignObject0_cache;
                while (s2_ != null) {
                    cached.add(Arrays.asList(s2_.interop_));
                    s2_ = s2_.next_;
                }
                s[2] = cached;
            } else {
                s[1] = exclude != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0);
            }
            data[3] = s;
            s = new Object[3];
            s[0] = "valueOfForeignObject";
            if ((state_0 & 8) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList();
                cached.add(Arrays.asList(new Object[0]));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[4] = s;
            s = new Object[3];
            s[0] = "valueOfOther";
            s[1] = (state_0 & 0x10) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[5] = s;
            return Introspection.Provider.create(data);
        }

        private static boolean fallbackGuard_(int state_0, Object arguments0Value) {
            JSDynamicObject arguments0Value_;
            if (arguments0Value instanceof JSDynamicObject && JSGuards.isJSNumber(arguments0Value_ = (JSDynamicObject)arguments0Value)) {
                return false;
            }
            if ((state_0 & 2) == 0 && JSGuards.isJavaNumber(arguments0Value)) {
                return false;
            }
            return (state_0 & 8) != 0 || !JSGuards.isForeignObject(arguments0Value);
        }

        public static NumberPrototypeBuiltins.JSNumberValueOfNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSNumberValueOfNodeGen(context, builtin, arguments);
        }

        @GeneratedBy(value=NumberPrototypeBuiltins.JSNumberValueOfNode.class)
        private static final class ValueOfForeignObject0Data
        extends Node {
            @Node.Child
            ValueOfForeignObject0Data next_;
            @Node.Child
            InteropLibrary interop_;

            ValueOfForeignObject0Data(ValueOfForeignObject0Data next_) {
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

    @GeneratedBy(value=NumberPrototypeBuiltins.JSNumberToLocaleStringIntlNode.class)
    public static final class JSNumberToLocaleStringIntlNodeGen
    extends NumberPrototypeBuiltins.JSNumberToLocaleStringIntlNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @Node.Child
        private JavaScriptNode arguments2_;
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @CompilerDirectives.CompilationFinal
        private volatile int exclude_;
        @Node.Child
        private ToLocaleStringForeignObject0Data toLocaleStringForeignObject0_cache;

        private JSNumberToLocaleStringIntlNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
        @ExplodeLoop
        public Object execute(VirtualFrame frameValue) {
            int state_0 = this.state_0_;
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            Object arguments2Value_ = this.arguments2_.execute(frameValue);
            if (state_0 != 0) {
                JSDynamicObject arguments0Value__;
                if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSDynamicObject && JSGuards.isJSNumber(arguments0Value__ = (JSDynamicObject)arguments0Value_)) {
                    return this.jsNumberToLocaleString(arguments0Value__, arguments1Value_, arguments2Value_);
                }
                if ((state_0 & 0x1E) != 0) {
                    if ((state_0 & 2) != 0 && JSGuards.isJavaNumber(arguments0Value_)) {
                        return this.javaNumberToLocaleString(arguments0Value_, arguments1Value_, arguments2Value_);
                    }
                    if ((state_0 & 4) != 0) {
                        ToLocaleStringForeignObject0Data s2_ = this.toLocaleStringForeignObject0_cache;
                        while (s2_ != null) {
                            if (s2_.interop_.accepts(arguments0Value_) && JSGuards.isForeignObject(arguments0Value_)) {
                                return this.toLocaleStringForeignObject(arguments0Value_, arguments1Value_, arguments2Value_, s2_.interop_);
                            }
                            s2_ = s2_.next_;
                        }
                    }
                    if ((state_0 & 8) != 0 && JSGuards.isForeignObject(arguments0Value_)) {
                        return this.toLocaleStringForeignObject1Boundary(state_0, arguments0Value_, arguments1Value_, arguments2Value_);
                    }
                    if ((state_0 & 0x10) != 0 && JSNumberToLocaleStringIntlNodeGen.fallbackGuard_(state_0, arguments0Value_, arguments1Value_, arguments2Value_)) {
                        return this.failForNonNumbers(arguments0Value_, arguments1Value_, arguments2Value_);
                    }
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_, arguments2Value_);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @CompilerDirectives.TruffleBoundary
        private Object toLocaleStringForeignObject1Boundary(int state_0, Object arguments0Value_, Object arguments1Value_, Object arguments2Value_) {
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this);
            try {
                InteropLibrary toLocaleStringForeignObject1_interop__ = INTEROP_LIBRARY_.getUncached(arguments0Value_);
                TruffleString truffleString = this.toLocaleStringForeignObject(arguments0Value_, arguments1Value_, arguments2Value_, toLocaleStringForeignObject1_interop__);
                return truffleString;
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
        private Object executeAndSpecialize(Object arguments0Value, Object arguments1Value, Object arguments2Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                Object arguments0Value_;
                int state_0 = this.state_0_;
                int exclude = this.exclude_;
                if (arguments0Value instanceof JSDynamicObject && JSGuards.isJSNumber(arguments0Value_ = (JSDynamicObject)arguments0Value)) {
                    this.state_0_ = state_0 |= 1;
                    lock.unlock();
                    hasLock = false;
                    TruffleString truffleString = this.jsNumberToLocaleString((JSDynamicObject)arguments0Value_, arguments1Value, arguments2Value);
                    return truffleString;
                }
                if (JSGuards.isJavaNumber(arguments0Value)) {
                    this.state_0_ = state_0 |= 2;
                    lock.unlock();
                    hasLock = false;
                    arguments0Value_ = this.javaNumberToLocaleString(arguments0Value, arguments1Value, arguments2Value);
                    return arguments0Value_;
                }
                if (exclude == 0) {
                    int count2_ = 0;
                    ToLocaleStringForeignObject0Data s2_ = this.toLocaleStringForeignObject0_cache;
                    if ((state_0 & 4) != 0) {
                        while (!(s2_ == null || s2_.interop_.accepts(arguments0Value) && JSGuards.isForeignObject(arguments0Value))) {
                            s2_ = s2_.next_;
                            ++count2_;
                        }
                    }
                    if (s2_ == null && JSGuards.isForeignObject(arguments0Value) && count2_ < 5) {
                        s2_ = super.insert(new ToLocaleStringForeignObject0Data(this.toLocaleStringForeignObject0_cache));
                        s2_.interop_ = s2_.insertAccessor(INTEROP_LIBRARY_.create(arguments0Value));
                        VarHandle.storeStoreFence();
                        this.toLocaleStringForeignObject0_cache = s2_;
                        this.state_0_ = state_0 |= 4;
                    }
                    if (s2_ != null) {
                        lock.unlock();
                        hasLock = false;
                        TruffleString truffleString = this.toLocaleStringForeignObject(arguments0Value, arguments1Value, arguments2Value, s2_.interop_);
                        return truffleString;
                    }
                }
                InteropLibrary toLocaleStringForeignObject1_interop__ = null;
                EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                Node prev_ = encapsulating_.set(this);
                try {
                    if (JSGuards.isForeignObject(arguments0Value)) {
                        toLocaleStringForeignObject1_interop__ = INTEROP_LIBRARY_.getUncached(arguments0Value);
                        this.exclude_ = exclude |= 1;
                        this.toLocaleStringForeignObject0_cache = null;
                        state_0 &= 0xFFFFFFFB;
                        this.state_0_ = state_0 |= 8;
                        lock.unlock();
                        hasLock = false;
                        TruffleString truffleString = this.toLocaleStringForeignObject(arguments0Value, arguments1Value, arguments2Value, toLocaleStringForeignObject1_interop__);
                        return truffleString;
                    }
                }
                finally {
                    encapsulating_.set(prev_);
                }
                this.state_0_ = state_0 |= 0x10;
                lock.unlock();
                hasLock = false;
                Object object = this.failForNonNumbers(arguments0Value, arguments1Value, arguments2Value);
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
            ToLocaleStringForeignObject0Data s2_;
            int state_0 = this.state_0_;
            if (state_0 == 0) {
                return NodeCost.UNINITIALIZED;
            }
            if ((state_0 & state_0 - 1) == 0 && ((s2_ = this.toLocaleStringForeignObject0_cache) == null || s2_.next_ == null)) {
                return NodeCost.MONOMORPHIC;
            }
            return NodeCost.POLYMORPHIC;
        }

        @Override
        public Introspection getIntrospectionData() {
            ArrayList<List<Object>> cached;
            Object[] data = new Object[6];
            data[0] = 0;
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            Object[] s = new Object[3];
            s[0] = "jsNumberToLocaleString";
            s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[1] = s;
            s = new Object[3];
            s[0] = "javaNumberToLocaleString";
            s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[2] = s;
            s = new Object[3];
            s[0] = "toLocaleStringForeignObject";
            if ((state_0 & 4) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList<List<Object>>();
                ToLocaleStringForeignObject0Data s2_ = this.toLocaleStringForeignObject0_cache;
                while (s2_ != null) {
                    cached.add(Arrays.asList(s2_.interop_));
                    s2_ = s2_.next_;
                }
                s[2] = cached;
            } else {
                s[1] = exclude != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0);
            }
            data[3] = s;
            s = new Object[3];
            s[0] = "toLocaleStringForeignObject";
            if ((state_0 & 8) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList();
                cached.add(Arrays.asList(new Object[0]));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[4] = s;
            s = new Object[3];
            s[0] = "failForNonNumbers";
            s[1] = (state_0 & 0x10) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[5] = s;
            return Introspection.Provider.create(data);
        }

        private static boolean fallbackGuard_(int state_0, Object arguments0Value, Object arguments1Value, Object arguments2Value) {
            JSDynamicObject arguments0Value_;
            if (arguments0Value instanceof JSDynamicObject && JSGuards.isJSNumber(arguments0Value_ = (JSDynamicObject)arguments0Value)) {
                return false;
            }
            if ((state_0 & 2) == 0 && JSGuards.isJavaNumber(arguments0Value)) {
                return false;
            }
            return (state_0 & 8) != 0 || !JSGuards.isForeignObject(arguments0Value);
        }

        public static NumberPrototypeBuiltins.JSNumberToLocaleStringIntlNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSNumberToLocaleStringIntlNodeGen(context, builtin, arguments);
        }

        @GeneratedBy(value=NumberPrototypeBuiltins.JSNumberToLocaleStringIntlNode.class)
        private static final class ToLocaleStringForeignObject0Data
        extends Node {
            @Node.Child
            ToLocaleStringForeignObject0Data next_;
            @Node.Child
            InteropLibrary interop_;

            ToLocaleStringForeignObject0Data(ToLocaleStringForeignObject0Data next_) {
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

    @GeneratedBy(value=NumberPrototypeBuiltins.JSNumberToLocaleStringNode.class)
    public static final class JSNumberToLocaleStringNodeGen
    extends NumberPrototypeBuiltins.JSNumberToLocaleStringNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @CompilerDirectives.CompilationFinal
        private volatile int exclude_;
        @Node.Child
        private ToLocaleStringForeignObject0Data toLocaleStringForeignObject0_cache;

        private JSNumberToLocaleStringNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            super(context, builtin);
            this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
        }

        @Override
        public JavaScriptNode[] getArguments() {
            return new JavaScriptNode[]{this.arguments0_};
        }

        @Override
        @ExplodeLoop
        public Object execute(VirtualFrame frameValue) {
            JSDynamicObject arguments0Value__;
            int state_0 = this.state_0_;
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSDynamicObject && JSGuards.isJSNumber(arguments0Value__ = (JSDynamicObject)arguments0Value_)) {
                return this.toLocaleString(arguments0Value__);
            }
            if ((state_0 & 0x1E) != 0) {
                if ((state_0 & 2) != 0 && JSGuards.isJavaNumber(arguments0Value_)) {
                    return this.toLocaleStringPrimitive(arguments0Value_);
                }
                if ((state_0 & 4) != 0) {
                    ToLocaleStringForeignObject0Data s2_ = this.toLocaleStringForeignObject0_cache;
                    while (s2_ != null) {
                        if (s2_.interop_.accepts(arguments0Value_) && JSGuards.isForeignObject(arguments0Value_)) {
                            return this.toLocaleStringForeignObject(arguments0Value_, s2_.interop_);
                        }
                        s2_ = s2_.next_;
                    }
                }
                if ((state_0 & 8) != 0 && JSGuards.isForeignObject(arguments0Value_)) {
                    return this.toLocaleStringForeignObject1Boundary(state_0, arguments0Value_);
                }
                if ((state_0 & 0x10) != 0 && JSNumberToLocaleStringNodeGen.fallbackGuard_(state_0, arguments0Value_)) {
                    return this.toLocaleStringOther(arguments0Value_);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @CompilerDirectives.TruffleBoundary
        private Object toLocaleStringForeignObject1Boundary(int state_0, Object arguments0Value_) {
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this);
            try {
                InteropLibrary toLocaleStringForeignObject1_interop__ = INTEROP_LIBRARY_.getUncached(arguments0Value_);
                Object object = this.toLocaleStringForeignObject(arguments0Value_, toLocaleStringForeignObject1_interop__);
                return object;
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
        private Object executeAndSpecialize(Object arguments0Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                Object arguments0Value_;
                int state_0 = this.state_0_;
                int exclude = this.exclude_;
                if (arguments0Value instanceof JSDynamicObject && JSGuards.isJSNumber(arguments0Value_ = (JSDynamicObject)arguments0Value)) {
                    this.state_0_ = state_0 |= 1;
                    lock.unlock();
                    hasLock = false;
                    Object object = this.toLocaleString((JSDynamicObject)arguments0Value_);
                    return object;
                }
                if (JSGuards.isJavaNumber(arguments0Value)) {
                    this.state_0_ = state_0 |= 2;
                    lock.unlock();
                    hasLock = false;
                    arguments0Value_ = this.toLocaleStringPrimitive(arguments0Value);
                    return arguments0Value_;
                }
                if (exclude == 0) {
                    int count2_ = 0;
                    ToLocaleStringForeignObject0Data s2_ = this.toLocaleStringForeignObject0_cache;
                    if ((state_0 & 4) != 0) {
                        while (!(s2_ == null || s2_.interop_.accepts(arguments0Value) && JSGuards.isForeignObject(arguments0Value))) {
                            s2_ = s2_.next_;
                            ++count2_;
                        }
                    }
                    if (s2_ == null && JSGuards.isForeignObject(arguments0Value) && count2_ < 5) {
                        s2_ = super.insert(new ToLocaleStringForeignObject0Data(this.toLocaleStringForeignObject0_cache));
                        s2_.interop_ = s2_.insertAccessor(INTEROP_LIBRARY_.create(arguments0Value));
                        VarHandle.storeStoreFence();
                        this.toLocaleStringForeignObject0_cache = s2_;
                        this.state_0_ = state_0 |= 4;
                    }
                    if (s2_ != null) {
                        lock.unlock();
                        hasLock = false;
                        Object object = this.toLocaleStringForeignObject(arguments0Value, s2_.interop_);
                        return object;
                    }
                }
                InteropLibrary toLocaleStringForeignObject1_interop__ = null;
                EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                Node prev_ = encapsulating_.set(this);
                try {
                    if (JSGuards.isForeignObject(arguments0Value)) {
                        toLocaleStringForeignObject1_interop__ = INTEROP_LIBRARY_.getUncached(arguments0Value);
                        this.exclude_ = exclude |= 1;
                        this.toLocaleStringForeignObject0_cache = null;
                        state_0 &= 0xFFFFFFFB;
                        this.state_0_ = state_0 |= 8;
                        lock.unlock();
                        hasLock = false;
                        Object object = this.toLocaleStringForeignObject(arguments0Value, toLocaleStringForeignObject1_interop__);
                        return object;
                    }
                }
                finally {
                    encapsulating_.set(prev_);
                }
                this.state_0_ = state_0 |= 0x10;
                lock.unlock();
                hasLock = false;
                String string = this.toLocaleStringOther(arguments0Value);
                return string;
            }
            finally {
                if (hasLock) {
                    lock.unlock();
                }
            }
        }

        @Override
        public NodeCost getCost() {
            ToLocaleStringForeignObject0Data s2_;
            int state_0 = this.state_0_;
            if (state_0 == 0) {
                return NodeCost.UNINITIALIZED;
            }
            if ((state_0 & state_0 - 1) == 0 && ((s2_ = this.toLocaleStringForeignObject0_cache) == null || s2_.next_ == null)) {
                return NodeCost.MONOMORPHIC;
            }
            return NodeCost.POLYMORPHIC;
        }

        @Override
        public Introspection getIntrospectionData() {
            ArrayList<List<Object>> cached;
            Object[] data = new Object[6];
            data[0] = 0;
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            Object[] s = new Object[3];
            s[0] = "toLocaleString";
            s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[1] = s;
            s = new Object[3];
            s[0] = "toLocaleStringPrimitive";
            s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[2] = s;
            s = new Object[3];
            s[0] = "toLocaleStringForeignObject";
            if ((state_0 & 4) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList<List<Object>>();
                ToLocaleStringForeignObject0Data s2_ = this.toLocaleStringForeignObject0_cache;
                while (s2_ != null) {
                    cached.add(Arrays.asList(s2_.interop_));
                    s2_ = s2_.next_;
                }
                s[2] = cached;
            } else {
                s[1] = exclude != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0);
            }
            data[3] = s;
            s = new Object[3];
            s[0] = "toLocaleStringForeignObject";
            if ((state_0 & 8) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList();
                cached.add(Arrays.asList(new Object[0]));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[4] = s;
            s = new Object[3];
            s[0] = "toLocaleStringOther";
            s[1] = (state_0 & 0x10) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[5] = s;
            return Introspection.Provider.create(data);
        }

        private static boolean fallbackGuard_(int state_0, Object arguments0Value) {
            JSDynamicObject arguments0Value_;
            if (arguments0Value instanceof JSDynamicObject && JSGuards.isJSNumber(arguments0Value_ = (JSDynamicObject)arguments0Value)) {
                return false;
            }
            if ((state_0 & 2) == 0 && JSGuards.isJavaNumber(arguments0Value)) {
                return false;
            }
            return (state_0 & 8) != 0 || !JSGuards.isForeignObject(arguments0Value);
        }

        public static NumberPrototypeBuiltins.JSNumberToLocaleStringNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSNumberToLocaleStringNodeGen(context, builtin, arguments);
        }

        @GeneratedBy(value=NumberPrototypeBuiltins.JSNumberToLocaleStringNode.class)
        private static final class ToLocaleStringForeignObject0Data
        extends Node {
            @Node.Child
            ToLocaleStringForeignObject0Data next_;
            @Node.Child
            InteropLibrary interop_;

            ToLocaleStringForeignObject0Data(ToLocaleStringForeignObject0Data next_) {
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

    @GeneratedBy(value=NumberPrototypeBuiltins.JSNumberToStringNode.class)
    public static final class JSNumberToStringNodeGen
    extends NumberPrototypeBuiltins.JSNumberToStringNode
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
        private JSToIntegerAsIntNode toInt;
        @Node.Child
        private ToStringForeignObject0Data toStringForeignObject0_cache;
        @Node.Child
        private JSToIntegerAsIntNode toStringForeignObject1_toIntegerNode_;

        private JSNumberToStringNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            if ((state_0 & 0x3DF) == 0 && state_0 != 0) {
                return this.execute_int0(state_0, frameValue);
            }
            return this.execute_generic1(state_0, frameValue);
        }

        private Object execute_int0(int state_0, VirtualFrame frameValue) {
            int arguments1Value_;
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            try {
                arguments1Value_ = this.arguments1_.executeInt(frameValue);
            }
            catch (UnexpectedResultException ex) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.executeAndSpecialize(arguments0Value_, ex.getResult());
            }
            assert ((state_0 & 0x20) != 0);
            if (arguments0Value_ instanceof Number) {
                Number arguments0Value__ = (Number)arguments0Value_;
                return this.toStringPrimitiveRadixInt(arguments0Value__, arguments1Value_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @CompilerDirectives.TruffleBoundary
        private Object toStringForeignObject1Boundary(int state_0, Object arguments0Value_, Object arguments1Value_) {
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this);
            try {
                InteropLibrary toStringForeignObject1_interop__ = INTEROP_LIBRARY_.getUncached(arguments0Value_);
                Object object = this.toStringForeignObject(arguments0Value_, arguments1Value_, this.toStringForeignObject1_toIntegerNode_, toStringForeignObject1_interop__);
                return object;
            }
            finally {
                encapsulating_.set(prev_);
            }
        }

        @ExplodeLoop
        private Object execute_generic1(int state_0, VirtualFrame frameValue) {
            Object arguments0Value__;
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            if ((state_0 & 0x1F) != 0) {
                if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSNumberObject && NumberPrototypeBuiltins.JSNumberToStringNode.isJSNumberInteger((JSNumberObject)(arguments0Value__ = (JSNumberObject)arguments0Value_)) && this.isRadix10(arguments1Value_)) {
                    return this.toStringIntRadix10((JSNumberObject)arguments0Value__, arguments1Value_);
                }
                if ((state_0 & 6) != 0 && arguments0Value_ instanceof JSDynamicObject) {
                    arguments0Value__ = (JSDynamicObject)arguments0Value_;
                    if ((state_0 & 2) != 0 && JSGuards.isJSNumber(arguments0Value__) && this.isRadix10(arguments1Value_)) {
                        return this.toStringRadix10((JSDynamicObject)arguments0Value__, arguments1Value_);
                    }
                    if ((state_0 & 4) != 0 && JSGuards.isJSNumber(arguments0Value__) && !JSGuards.isUndefined(arguments1Value_)) {
                        return this.toString((JSDynamicObject)arguments0Value__, arguments1Value_, this.toInt);
                    }
                }
                if ((state_0 & 0x18) != 0) {
                    if ((state_0 & 8) != 0 && JSGuards.isJavaNumber(arguments0Value_) && JSGuards.isNumberInteger(arguments0Value_) && this.isRadix10(arguments1Value_)) {
                        return this.toStringPrimitiveIntRadix10(arguments0Value_, arguments1Value_);
                    }
                    if ((state_0 & 0x10) != 0 && JSGuards.isJavaNumber(arguments0Value_) && this.isRadix10(arguments1Value_)) {
                        return this.toStringPrimitiveRadix10(arguments0Value_, arguments1Value_);
                    }
                }
            }
            if ((state_0 & 0x60) != 0 && arguments0Value_ instanceof Number) {
                arguments0Value__ = (Number)arguments0Value_;
                if ((state_0 & 0x20) != 0 && arguments1Value_ instanceof Integer) {
                    int arguments1Value__ = (Integer)arguments1Value_;
                    return this.toStringPrimitiveRadixInt((Number)arguments0Value__, arguments1Value__);
                }
                if ((state_0 & 0x40) != 0 && !JSGuards.isUndefined(arguments1Value_)) {
                    return this.toStringPrimitive((Number)arguments0Value__, arguments1Value_, this.toInt);
                }
            }
            if ((state_0 & 0x380) != 0) {
                if ((state_0 & 0x80) != 0) {
                    ToStringForeignObject0Data s7_ = this.toStringForeignObject0_cache;
                    while (s7_ != null) {
                        if (s7_.interop_.accepts(arguments0Value_) && JSGuards.isForeignObject(arguments0Value_)) {
                            return this.toStringForeignObject(arguments0Value_, arguments1Value_, s7_.toIntegerNode_, s7_.interop_);
                        }
                        s7_ = s7_.next_;
                    }
                }
                if ((state_0 & 0x100) != 0 && JSGuards.isForeignObject(arguments0Value_)) {
                    return this.toStringForeignObject1Boundary(state_0, arguments0Value_, arguments1Value_);
                }
                if (!((state_0 & 0x200) == 0 || JSGuards.isJSNumber(arguments0Value_) || JSGuards.isJavaNumber(arguments0Value_) || JSGuards.isForeignObject(arguments0Value_))) {
                    return this.toStringNoNumber(arguments0Value_, arguments1Value_);
                }
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
        private Object executeAndSpecialize(Object arguments0Value, Object arguments1Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                Object arguments0Value_;
                int state_0 = this.state_0_;
                int exclude = this.exclude_;
                if (arguments0Value instanceof JSNumberObject && NumberPrototypeBuiltins.JSNumberToStringNode.isJSNumberInteger((JSNumberObject)(arguments0Value_ = (JSNumberObject)arguments0Value)) && this.isRadix10(arguments1Value)) {
                    this.state_0_ = state_0 |= 1;
                    lock.unlock();
                    hasLock = false;
                    Object object = this.toStringIntRadix10((JSNumberObject)arguments0Value_, arguments1Value);
                    return object;
                }
                if (arguments0Value instanceof JSDynamicObject) {
                    arguments0Value_ = (JSDynamicObject)arguments0Value;
                    if (JSGuards.isJSNumber(arguments0Value_) && this.isRadix10(arguments1Value)) {
                        this.state_0_ = state_0 |= 2;
                        lock.unlock();
                        hasLock = false;
                        Object object = this.toStringRadix10((JSDynamicObject)arguments0Value_, arguments1Value);
                        return object;
                    }
                    if (JSGuards.isJSNumber(arguments0Value_) && !JSGuards.isUndefined(arguments1Value)) {
                        this.toInt = super.insert(this.toInt == null ? JSToIntegerAsIntNode.create() : this.toInt);
                        this.state_0_ = state_0 |= 4;
                        lock.unlock();
                        hasLock = false;
                        Object object = this.toString((JSDynamicObject)arguments0Value_, arguments1Value, this.toInt);
                        return object;
                    }
                }
                if (JSGuards.isJavaNumber(arguments0Value) && JSGuards.isNumberInteger(arguments0Value) && this.isRadix10(arguments1Value)) {
                    this.state_0_ = state_0 |= 8;
                    lock.unlock();
                    hasLock = false;
                    arguments0Value_ = this.toStringPrimitiveIntRadix10(arguments0Value, arguments1Value);
                    return arguments0Value_;
                }
                if (JSGuards.isJavaNumber(arguments0Value) && this.isRadix10(arguments1Value)) {
                    this.state_0_ = state_0 |= 0x10;
                    lock.unlock();
                    hasLock = false;
                    arguments0Value_ = this.toStringPrimitiveRadix10(arguments0Value, arguments1Value);
                    return arguments0Value_;
                }
                if (arguments0Value instanceof Number) {
                    arguments0Value_ = (Number)arguments0Value;
                    if ((exclude & 1) == 0 && arguments1Value instanceof Integer) {
                        int arguments1Value_ = (Integer)arguments1Value;
                        this.state_0_ = state_0 |= 0x20;
                        lock.unlock();
                        hasLock = false;
                        Object object = this.toStringPrimitiveRadixInt((Number)arguments0Value_, arguments1Value_);
                        return object;
                    }
                    if (!JSGuards.isUndefined(arguments1Value)) {
                        this.toInt = super.insert(this.toInt == null ? JSToIntegerAsIntNode.create() : this.toInt);
                        this.exclude_ = exclude |= 1;
                        state_0 &= 0xFFFFFFDF;
                        this.state_0_ = state_0 |= 0x40;
                        lock.unlock();
                        hasLock = false;
                        Object arguments1Value_ = this.toStringPrimitive((Number)arguments0Value_, arguments1Value, this.toInt);
                        return arguments1Value_;
                    }
                }
                if ((exclude & 2) == 0) {
                    int count7_ = 0;
                    ToStringForeignObject0Data s7_ = this.toStringForeignObject0_cache;
                    if ((state_0 & 0x80) != 0) {
                        while (!(s7_ == null || s7_.interop_.accepts(arguments0Value) && JSGuards.isForeignObject(arguments0Value))) {
                            s7_ = s7_.next_;
                            ++count7_;
                        }
                    }
                    if (s7_ == null && JSGuards.isForeignObject(arguments0Value) && count7_ < 5) {
                        s7_ = super.insert(new ToStringForeignObject0Data(this.toStringForeignObject0_cache));
                        s7_.toIntegerNode_ = s7_.insertAccessor(JSToIntegerAsIntNode.create());
                        s7_.interop_ = s7_.insertAccessor(INTEROP_LIBRARY_.create(arguments0Value));
                        VarHandle.storeStoreFence();
                        this.toStringForeignObject0_cache = s7_;
                        this.state_0_ = state_0 |= 0x80;
                    }
                    if (s7_ != null) {
                        lock.unlock();
                        hasLock = false;
                        Object object = this.toStringForeignObject(arguments0Value, arguments1Value, s7_.toIntegerNode_, s7_.interop_);
                        return object;
                    }
                }
                InteropLibrary toStringForeignObject1_interop__ = null;
                EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                Node prev_ = encapsulating_.set(this);
                try {
                    if (JSGuards.isForeignObject(arguments0Value)) {
                        this.toStringForeignObject1_toIntegerNode_ = super.insert(JSToIntegerAsIntNode.create());
                        toStringForeignObject1_interop__ = INTEROP_LIBRARY_.getUncached(arguments0Value);
                        this.exclude_ = exclude |= 2;
                        this.toStringForeignObject0_cache = null;
                        state_0 &= 0xFFFFFF7F;
                        this.state_0_ = state_0 |= 0x100;
                        lock.unlock();
                        hasLock = false;
                        Object object = this.toStringForeignObject(arguments0Value, arguments1Value, this.toStringForeignObject1_toIntegerNode_, toStringForeignObject1_interop__);
                        return object;
                    }
                }
                finally {
                    encapsulating_.set(prev_);
                }
                if (!(JSGuards.isJSNumber(arguments0Value) || JSGuards.isJavaNumber(arguments0Value) || JSGuards.isForeignObject(arguments0Value))) {
                    this.state_0_ = state_0 |= 0x200;
                    lock.unlock();
                    hasLock = false;
                    String string = this.toStringNoNumber(arguments0Value, arguments1Value);
                    return string;
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
            ToStringForeignObject0Data s7_;
            int state_0 = this.state_0_;
            if (state_0 == 0) {
                return NodeCost.UNINITIALIZED;
            }
            if ((state_0 & state_0 - 1) == 0 && ((s7_ = this.toStringForeignObject0_cache) == null || s7_.next_ == null)) {
                return NodeCost.MONOMORPHIC;
            }
            return NodeCost.POLYMORPHIC;
        }

        @Override
        public Introspection getIntrospectionData() {
            ArrayList<List<Node>> cached;
            Object[] data = new Object[11];
            data[0] = 0;
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            Object[] s = new Object[3];
            s[0] = "toStringIntRadix10";
            s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[1] = s;
            s = new Object[3];
            s[0] = "toStringRadix10";
            s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[2] = s;
            s = new Object[3];
            s[0] = "toString";
            if ((state_0 & 4) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList<List<Node>>();
                cached.add(Arrays.asList(this.toInt));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[3] = s;
            s = new Object[3];
            s[0] = "toStringPrimitiveIntRadix10";
            s[1] = (state_0 & 8) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[4] = s;
            s = new Object[3];
            s[0] = "toStringPrimitiveRadix10";
            s[1] = (state_0 & 0x10) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[5] = s;
            s = new Object[3];
            s[0] = "toStringPrimitiveRadixInt";
            s[1] = (state_0 & 0x20) != 0 ? Byte.valueOf((byte)1) : ((exclude & 1) != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0));
            data[6] = s;
            s = new Object[3];
            s[0] = "toStringPrimitive";
            if ((state_0 & 0x40) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList();
                cached.add(Arrays.asList(this.toInt));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[7] = s;
            s = new Object[3];
            s[0] = "toStringForeignObject";
            if ((state_0 & 0x80) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList();
                ToStringForeignObject0Data s7_ = this.toStringForeignObject0_cache;
                while (s7_ != null) {
                    cached.add(Arrays.asList(s7_.toIntegerNode_, s7_.interop_));
                    s7_ = s7_.next_;
                }
                s[2] = cached;
            } else {
                s[1] = (exclude & 2) != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0);
            }
            data[8] = s;
            s = new Object[3];
            s[0] = "toStringForeignObject";
            if ((state_0 & 0x100) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList();
                cached.add(Arrays.asList(this.toStringForeignObject1_toIntegerNode_));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[9] = s;
            s = new Object[3];
            s[0] = "toStringNoNumber";
            s[1] = (state_0 & 0x200) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[10] = s;
            return Introspection.Provider.create(data);
        }

        public static NumberPrototypeBuiltins.JSNumberToStringNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSNumberToStringNodeGen(context, builtin, arguments);
        }

        @GeneratedBy(value=NumberPrototypeBuiltins.JSNumberToStringNode.class)
        private static final class ToStringForeignObject0Data
        extends Node {
            @Node.Child
            ToStringForeignObject0Data next_;
            @Node.Child
            JSToIntegerAsIntNode toIntegerNode_;
            @Node.Child
            InteropLibrary interop_;

            ToStringForeignObject0Data(ToStringForeignObject0Data next_) {
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
}

