
package com.oracle.truffle.js.builtins;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.library.LibraryFactory;
import com.oracle.truffle.api.nodes.EncapsulatingNodeReference;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.builtins.FunctionPrototypeBuiltins;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.nodes.unary.IsCallableNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.builtins.JSFunctionObject;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=FunctionPrototypeBuiltins.class)
public final class FunctionPrototypeBuiltinsFactory {
    private static final LibraryFactory<InteropLibrary> INTEROP_LIBRARY_ = LibraryFactory.resolve(InteropLibrary.class);

    @GeneratedBy(value=FunctionPrototypeBuiltins.HasInstanceNode.class)
    public static final class HasInstanceNodeGen
    extends FunctionPrototypeBuiltins.HasInstanceNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;

        private HasInstanceNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            return this.hasInstance(arguments0Value_, arguments1Value_);
        }

        @Override
        public boolean executeBoolean(VirtualFrame frameValue) {
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            return this.hasInstance(arguments0Value_, arguments1Value_);
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
            s[0] = "hasInstance";
            s[1] = (byte)1;
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static FunctionPrototypeBuiltins.HasInstanceNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new HasInstanceNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=FunctionPrototypeBuiltins.JSCallNode.class)
    public static final class JSCallNodeGen
    extends FunctionPrototypeBuiltins.JSCallNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @Node.Child
        private JavaScriptNode arguments2_;
        @CompilerDirectives.CompilationFinal
        private int state_0_;

        private JSCallNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            if (state_0 != 0 && arguments2Value_ instanceof Object[]) {
                Object[] arguments2Value__ = (Object[])arguments2Value_;
                return this.call(arguments0Value_, arguments1Value_, arguments2Value__);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_, arguments2Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.execute(frameValue);
        }

        private Object executeAndSpecialize(Object arguments0Value, Object arguments1Value, Object arguments2Value) {
            int state_0 = this.state_0_;
            if (arguments2Value instanceof Object[]) {
                Object[] arguments2Value_ = (Object[])arguments2Value;
                this.state_0_ = state_0 |= 1;
                return this.call(arguments0Value, arguments1Value, arguments2Value_);
            }
            throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_, this.arguments1_, this.arguments2_}, arguments0Value, arguments1Value, arguments2Value);
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
            s[0] = "call";
            s[1] = state_0 != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static FunctionPrototypeBuiltins.JSCallNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSCallNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=FunctionPrototypeBuiltins.JSApplyNode.class)
    public static final class JSApplyNodeGen
    extends FunctionPrototypeBuiltins.JSApplyNode
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
        private IsCallableNode isCallable;

        private JSApplyNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
                JSDynamicObject arguments0Value__;
                if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSDynamicObject && JSGuards.isJSFunction(arguments0Value__ = (JSDynamicObject)arguments0Value_)) {
                    return this.applyFunction(arguments0Value__, arguments1Value_, arguments2Value_);
                }
                if ((state_0 & 6) != 0) {
                    if ((state_0 & 2) != 0 && this.isCallable.executeBoolean(arguments0Value_)) {
                        return this.applyCallable(arguments0Value_, arguments1Value_, arguments2Value_, this.isCallable);
                    }
                    if ((state_0 & 4) != 0 && !this.isCallable.executeBoolean(arguments0Value_)) {
                        return this.error(arguments0Value_, arguments1Value_, arguments2Value_, this.isCallable);
                    }
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
                IsCallableNode error_isCallable__;
                Object applyCallable_isCallable__;
                JSDynamicObject arguments0Value_;
                int state_0 = this.state_0_;
                int exclude = this.exclude_;
                if (exclude == 0 && arguments0Value instanceof JSDynamicObject && JSGuards.isJSFunction(arguments0Value_ = (JSDynamicObject)arguments0Value)) {
                    this.state_0_ = state_0 |= 1;
                    lock.unlock();
                    hasLock = false;
                    Object object = this.applyFunction(arguments0Value_, arguments1Value, arguments2Value);
                    return object;
                }
                boolean ApplyCallable_duplicateFound_ = false;
                if ((state_0 & 2) != 0 && this.isCallable.executeBoolean(arguments0Value)) {
                    ApplyCallable_duplicateFound_ = true;
                }
                if (!ApplyCallable_duplicateFound_ && ((IsCallableNode)(applyCallable_isCallable__ = super.insert(this.isCallable == null ? IsCallableNode.create() : this.isCallable))).executeBoolean(arguments0Value) && (state_0 & 2) == 0) {
                    if (this.isCallable == null) {
                        IsCallableNode applyCallable_isCallable___check = (IsCallableNode)super.insert(applyCallable_isCallable__);
                        if (applyCallable_isCallable___check == null) {
                            throw new AssertionError((Object)"Specialization 'applyCallable(Object, Object, Object, IsCallableNode)' contains a shared cache with name 'isCallable' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state.");
                        }
                        this.isCallable = applyCallable_isCallable___check;
                    }
                    this.exclude_ = exclude |= 1;
                    state_0 &= 0xFFFFFFFE;
                    this.state_0_ = state_0 |= 2;
                    ApplyCallable_duplicateFound_ = true;
                }
                if (ApplyCallable_duplicateFound_) {
                    lock.unlock();
                    hasLock = false;
                    applyCallable_isCallable__ = this.applyCallable(arguments0Value, arguments1Value, arguments2Value, this.isCallable);
                    return applyCallable_isCallable__;
                }
                boolean Error_duplicateFound_ = false;
                if ((state_0 & 4) != 0 && !this.isCallable.executeBoolean(arguments0Value)) {
                    Error_duplicateFound_ = true;
                }
                if (!Error_duplicateFound_ && !(error_isCallable__ = super.insert(this.isCallable == null ? IsCallableNode.create() : this.isCallable)).executeBoolean(arguments0Value) && (state_0 & 4) == 0) {
                    if (this.isCallable == null) {
                        IsCallableNode error_isCallable___check = super.insert(error_isCallable__);
                        if (error_isCallable___check == null) {
                            throw new AssertionError((Object)"Specialization 'error(Object, Object, Object, IsCallableNode)' contains a shared cache with name 'isCallable' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state.");
                        }
                        this.isCallable = error_isCallable___check;
                    }
                    this.state_0_ = state_0 |= 4;
                    Error_duplicateFound_ = true;
                }
                if (Error_duplicateFound_) {
                    lock.unlock();
                    hasLock = false;
                    Object object = this.error(arguments0Value, arguments1Value, arguments2Value, this.isCallable);
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
            ArrayList<List<IsCallableNode>> cached;
            Object[] data = new Object[4];
            data[0] = 0;
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            Object[] s = new Object[3];
            s[0] = "applyFunction";
            s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : (exclude != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0));
            data[1] = s;
            s = new Object[3];
            s[0] = "applyCallable";
            if ((state_0 & 2) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList<List<IsCallableNode>>();
                cached.add(Arrays.asList(this.isCallable));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[2] = s;
            s = new Object[3];
            s[0] = "error";
            if ((state_0 & 4) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList();
                cached.add(Arrays.asList(this.isCallable));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[3] = s;
            return Introspection.Provider.create(data);
        }

        public static FunctionPrototypeBuiltins.JSApplyNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSApplyNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=FunctionPrototypeBuiltins.JSFunctionToStringNode.class)
    public static final class JSFunctionToStringNodeGen
    extends FunctionPrototypeBuiltins.JSFunctionToStringNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @CompilerDirectives.CompilationFinal
        private volatile int exclude_;
        @Node.Child
        private IsCallableNode isCallable;
        @Node.Child
        private InteropLibrary toStringCallable0_interop_;

        private JSFunctionToStringNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            if ((state_0 & 3) != 0 && arguments0Value_ instanceof JSDynamicObject) {
                JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
                if ((state_0 & 1) != 0 && JSGuards.isJSFunction(arguments0Value__) && !this.isBoundTarget(arguments0Value__)) {
                    return this.toStringDefault(arguments0Value__);
                }
                if ((state_0 & 2) != 0 && JSGuards.isJSFunction(arguments0Value__) && this.isBoundTarget(arguments0Value__)) {
                    return this.toStringBound(arguments0Value__);
                }
            }
            if ((state_0 & 0x3C) != 0) {
                if ((state_0 & 4) != 0 && this.toStringCallable0_interop_.accepts(arguments0Value_)) {
                    assert (this.isES2019OrLater());
                    if (!JSGuards.isJSFunction(arguments0Value_) && this.isCallable.executeBoolean(arguments0Value_)) {
                        return this.toStringCallable(arguments0Value_, this.isCallable, this.toStringCallable0_interop_);
                    }
                }
                if ((state_0 & 8) != 0) {
                    assert (this.isES2019OrLater());
                    if (!JSGuards.isJSFunction(arguments0Value_) && this.isCallable.executeBoolean(arguments0Value_)) {
                        return this.toStringCallable1Boundary(state_0, arguments0Value_);
                    }
                }
                if ((state_0 & 0x10) != 0) {
                    assert (this.isES2019OrLater());
                    if (!this.isCallable.executeBoolean(arguments0Value_)) {
                        return this.toStringNotCallable(arguments0Value_, this.isCallable);
                    }
                }
                if ((state_0 & 0x20) != 0) {
                    assert (!this.isES2019OrLater());
                    if (!JSGuards.isJSFunction(arguments0Value_)) {
                        return this.toStringNotFunction(arguments0Value_);
                    }
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @CompilerDirectives.TruffleBoundary
        private Object toStringCallable1Boundary(int state_0, Object arguments0Value_) {
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this);
            try {
                InteropLibrary toStringCallable1_interop__ = INTEROP_LIBRARY_.getUncached(arguments0Value_);
                TruffleString truffleString = this.toStringCallable(arguments0Value_, this.isCallable, toStringCallable1_interop__);
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
        private TruffleString executeAndSpecialize(Object arguments0Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                TruffleString truffleString;
                IsCallableNode toStringNotCallable_isCallable__;
                int state_0 = this.state_0_;
                int exclude = this.exclude_;
                if (arguments0Value instanceof JSDynamicObject) {
                    JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
                    if (JSGuards.isJSFunction(arguments0Value_) && !this.isBoundTarget(arguments0Value_)) {
                        this.state_0_ = state_0 |= 1;
                        lock.unlock();
                        hasLock = false;
                        TruffleString truffleString2 = this.toStringDefault(arguments0Value_);
                        return truffleString2;
                    }
                    if (JSGuards.isJSFunction(arguments0Value_) && this.isBoundTarget(arguments0Value_)) {
                        this.state_0_ = state_0 |= 2;
                        lock.unlock();
                        hasLock = false;
                        TruffleString truffleString3 = this.toStringBound(arguments0Value_);
                        return truffleString3;
                    }
                }
                if (exclude == 0) {
                    Object toStringCallable0_isCallable__;
                    boolean ToStringCallable0_duplicateFound_ = false;
                    if ((state_0 & 4) != 0 && this.toStringCallable0_interop_.accepts(arguments0Value)) {
                        assert (this.isES2019OrLater());
                        if (!JSGuards.isJSFunction(arguments0Value) && this.isCallable.executeBoolean(arguments0Value)) {
                            ToStringCallable0_duplicateFound_ = true;
                        }
                    }
                    if (!ToStringCallable0_duplicateFound_ && this.isES2019OrLater() && !JSGuards.isJSFunction(arguments0Value) && ((IsCallableNode)(toStringCallable0_isCallable__ = super.insert(this.isCallable == null ? IsCallableNode.create() : this.isCallable))).executeBoolean(arguments0Value) && (state_0 & 4) == 0) {
                        if (this.isCallable == null) {
                            IsCallableNode toStringCallable0_isCallable___check = (IsCallableNode)super.insert(toStringCallable0_isCallable__);
                            if (toStringCallable0_isCallable___check == null) {
                                throw new AssertionError((Object)"Specialization 'toStringCallable(Object, IsCallableNode, InteropLibrary)' contains a shared cache with name 'isCallable' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state.");
                            }
                            this.isCallable = toStringCallable0_isCallable___check;
                        }
                        this.toStringCallable0_interop_ = super.insert(INTEROP_LIBRARY_.create(arguments0Value));
                        this.state_0_ = state_0 |= 4;
                        ToStringCallable0_duplicateFound_ = true;
                    }
                    if (ToStringCallable0_duplicateFound_) {
                        lock.unlock();
                        hasLock = false;
                        toStringCallable0_isCallable__ = this.toStringCallable(arguments0Value, this.isCallable, this.toStringCallable0_interop_);
                        return toStringCallable0_isCallable__;
                    }
                }
                InteropLibrary toStringCallable1_interop__ = null;
                EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                Node prev_ = encapsulating_.set(this);
                try {
                    IsCallableNode toStringCallable1_isCallable__;
                    boolean ToStringCallable1_duplicateFound_ = false;
                    if ((state_0 & 8) != 0) {
                        assert (this.isES2019OrLater());
                        if (!JSGuards.isJSFunction(arguments0Value) && this.isCallable.executeBoolean(arguments0Value)) {
                            ToStringCallable1_duplicateFound_ = true;
                            toStringCallable1_interop__ = INTEROP_LIBRARY_.getUncached(arguments0Value);
                        }
                    }
                    if (!ToStringCallable1_duplicateFound_ && this.isES2019OrLater() && !JSGuards.isJSFunction(arguments0Value) && (toStringCallable1_isCallable__ = super.insert(this.isCallable == null ? IsCallableNode.create() : this.isCallable)).executeBoolean(arguments0Value) && (state_0 & 8) == 0) {
                        if (this.isCallable == null) {
                            IsCallableNode toStringCallable1_isCallable___check = super.insert(toStringCallable1_isCallable__);
                            if (toStringCallable1_isCallable___check == null) {
                                throw new AssertionError((Object)"Specialization 'toStringCallable(Object, IsCallableNode, InteropLibrary)' contains a shared cache with name 'isCallable' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state.");
                            }
                            this.isCallable = toStringCallable1_isCallable___check;
                        }
                        toStringCallable1_interop__ = INTEROP_LIBRARY_.getUncached(arguments0Value);
                        this.exclude_ = exclude |= 1;
                        state_0 &= 0xFFFFFFFB;
                        this.state_0_ = state_0 |= 8;
                        ToStringCallable1_duplicateFound_ = true;
                    }
                    if (ToStringCallable1_duplicateFound_) {
                        lock.unlock();
                        hasLock = false;
                        TruffleString truffleString4 = this.toStringCallable(arguments0Value, this.isCallable, toStringCallable1_interop__);
                        return truffleString4;
                    }
                }
                finally {
                    encapsulating_.set(prev_);
                }
                boolean ToStringNotCallable_duplicateFound_ = false;
                if ((state_0 & 0x10) != 0) {
                    assert (this.isES2019OrLater());
                    if (!this.isCallable.executeBoolean(arguments0Value)) {
                        ToStringNotCallable_duplicateFound_ = true;
                    }
                }
                if (!ToStringNotCallable_duplicateFound_ && this.isES2019OrLater() && !(toStringNotCallable_isCallable__ = super.insert(this.isCallable == null ? IsCallableNode.create() : this.isCallable)).executeBoolean(arguments0Value) && (state_0 & 0x10) == 0) {
                    if (this.isCallable == null) {
                        IsCallableNode toStringNotCallable_isCallable___check = super.insert(toStringNotCallable_isCallable__);
                        if (toStringNotCallable_isCallable___check == null) {
                            throw new AssertionError((Object)"Specialization 'toStringNotCallable(Object, IsCallableNode)' contains a shared cache with name 'isCallable' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state.");
                        }
                        this.isCallable = toStringNotCallable_isCallable___check;
                    }
                    this.state_0_ = state_0 |= 0x10;
                    ToStringNotCallable_duplicateFound_ = true;
                }
                if (ToStringNotCallable_duplicateFound_) {
                    lock.unlock();
                    hasLock = false;
                    truffleString = this.toStringNotCallable(arguments0Value, this.isCallable);
                    return truffleString;
                }
                if (!this.isES2019OrLater() && !JSGuards.isJSFunction(arguments0Value)) {
                    this.state_0_ = state_0 |= 0x20;
                    lock.unlock();
                    hasLock = false;
                    truffleString = this.toStringNotFunction(arguments0Value);
                    return truffleString;
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
            ArrayList<List<Node>> cached;
            Object[] data = new Object[7];
            data[0] = 0;
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            Object[] s = new Object[3];
            s[0] = "toStringDefault";
            s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[1] = s;
            s = new Object[3];
            s[0] = "toStringBound";
            s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[2] = s;
            s = new Object[3];
            s[0] = "toStringCallable";
            if ((state_0 & 4) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList<List<Node>>();
                cached.add(Arrays.asList(this.isCallable, this.toStringCallable0_interop_));
                s[2] = cached;
            } else {
                s[1] = exclude != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0);
            }
            data[3] = s;
            s = new Object[3];
            s[0] = "toStringCallable";
            if ((state_0 & 8) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList();
                cached.add(Arrays.asList(this.isCallable));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[4] = s;
            s = new Object[3];
            s[0] = "toStringNotCallable";
            if ((state_0 & 0x10) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList();
                cached.add(Arrays.asList(this.isCallable));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[5] = s;
            s = new Object[3];
            s[0] = "toStringNotFunction";
            s[1] = (state_0 & 0x20) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[6] = s;
            return Introspection.Provider.create(data);
        }

        public static FunctionPrototypeBuiltins.JSFunctionToStringNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSFunctionToStringNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=FunctionPrototypeBuiltins.JSBindNode.class)
    public static final class JSBindNodeGen
    extends FunctionPrototypeBuiltins.JSBindNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @Node.Child
        private JavaScriptNode arguments2_;
        @CompilerDirectives.CompilationFinal
        private int state_0_;

        private JSBindNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            if (state_0 != 0 && arguments2Value_ instanceof Object[]) {
                JSDynamicObject arguments0Value__;
                Object[] arguments2Value__ = (Object[])arguments2Value_;
                if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSFunctionObject) {
                    JSFunctionObject arguments0Value__2 = (JSFunctionObject)arguments0Value_;
                    return this.bindFunction(arguments0Value__2, arguments1Value_, arguments2Value__);
                }
                if ((state_0 & 2) != 0 && arguments0Value_ instanceof JSDynamicObject && JSGuards.isJSProxy(arguments0Value__ = (JSDynamicObject)arguments0Value_)) {
                    return this.bindProxy(arguments0Value__, arguments1Value_, arguments2Value__);
                }
                if ((state_0 & 4) != 0 && !JSGuards.isJSFunction(arguments0Value_) && !JSGuards.isJSProxy(arguments0Value_)) {
                    return this.bindError(arguments0Value_, arguments1Value_, arguments2Value__);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_, arguments2Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.execute(frameValue);
        }

        private JSDynamicObject executeAndSpecialize(Object arguments0Value, Object arguments1Value, Object arguments2Value) {
            int state_0 = this.state_0_;
            if (arguments2Value instanceof Object[]) {
                JSDynamicObject arguments0Value_;
                Object[] arguments2Value_ = (Object[])arguments2Value;
                if (arguments0Value instanceof JSFunctionObject) {
                    JSFunctionObject arguments0Value_2 = (JSFunctionObject)arguments0Value;
                    this.state_0_ = state_0 |= 1;
                    return this.bindFunction(arguments0Value_2, arguments1Value, arguments2Value_);
                }
                if (arguments0Value instanceof JSDynamicObject && JSGuards.isJSProxy(arguments0Value_ = (JSDynamicObject)arguments0Value)) {
                    this.state_0_ = state_0 |= 2;
                    return this.bindProxy(arguments0Value_, arguments1Value, arguments2Value_);
                }
                if (!JSGuards.isJSFunction(arguments0Value) && !JSGuards.isJSProxy(arguments0Value)) {
                    this.state_0_ = state_0 |= 4;
                    return this.bindError(arguments0Value, arguments1Value, arguments2Value_);
                }
            }
            throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_, this.arguments1_, this.arguments2_}, arguments0Value, arguments1Value, arguments2Value);
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
            s[0] = "bindFunction";
            s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[1] = s;
            s = new Object[3];
            s[0] = "bindProxy";
            s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[2] = s;
            s = new Object[3];
            s[0] = "bindError";
            s[1] = (state_0 & 4) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[3] = s;
            return Introspection.Provider.create(data);
        }

        public static FunctionPrototypeBuiltins.JSBindNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSBindNodeGen(context, builtin, arguments);
        }
    }
}

