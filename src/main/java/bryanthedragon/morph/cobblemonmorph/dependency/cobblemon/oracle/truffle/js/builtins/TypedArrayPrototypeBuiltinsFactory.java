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
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.api.profiles.Profile;
import com.oracle.truffle.api.profiles.ValueProfile;
import com.oracle.truffle.js.builtins.TypedArrayPrototypeBuiltins;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.control.DeletePropertyNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.builtins.JSTypedArrayObject;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=TypedArrayPrototypeBuiltins.class)
public final class TypedArrayPrototypeBuiltinsFactory {

    @GeneratedBy(value=TypedArrayPrototypeBuiltins.JSArrayBufferViewIteratorNode.class)
    public static final class JSArrayBufferViewIteratorNodeGen
    extends TypedArrayPrototypeBuiltins.JSArrayBufferViewIteratorNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @CompilerDirectives.CompilationFinal
        private int state_0_;

        private JSArrayBufferViewIteratorNodeGen(JSContext context, JSBuiltin builtin, int iterationKind, JavaScriptNode[] arguments) {
            super(context, builtin, iterationKind);
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
            if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSDynamicObject && JSGuards.isJSArrayBufferView(arguments0Value__ = (JSDynamicObject)arguments0Value_)) {
                return this.doObject(arguments0Value__);
            }
            if ((state_0 & 2) != 0 && !JSGuards.isJSArrayBufferView(arguments0Value_)) {
                return this.doNotObject(arguments0Value_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.execute(frameValue);
        }

        private JSDynamicObject executeAndSpecialize(Object arguments0Value) {
            JSDynamicObject arguments0Value_;
            int state_0 = this.state_0_;
            if (arguments0Value instanceof JSDynamicObject && JSGuards.isJSArrayBufferView(arguments0Value_ = (JSDynamicObject)arguments0Value)) {
                this.state_0_ = state_0 |= 1;
                return this.doObject(arguments0Value_);
            }
            if (!JSGuards.isJSArrayBufferView(arguments0Value)) {
                this.state_0_ = state_0 |= 2;
                return this.doNotObject(arguments0Value);
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
            s[0] = "doObject";
            s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[1] = s;
            s = new Object[3];
            s[0] = "doNotObject";
            s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[2] = s;
            return Introspection.Provider.create(data);
        }

        public static TypedArrayPrototypeBuiltins.JSArrayBufferViewIteratorNode create(JSContext context, JSBuiltin builtin, int iterationKind, JavaScriptNode[] arguments) {
            return new JSArrayBufferViewIteratorNodeGen(context, builtin, iterationKind, arguments);
        }
    }

    @GeneratedBy(value=TypedArrayPrototypeBuiltins.JSArrayBufferViewFillNode.class)
    public static final class JSArrayBufferViewFillNodeGen
    extends TypedArrayPrototypeBuiltins.JSArrayBufferViewFillNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @Node.Child
        private JavaScriptNode arguments2_;
        @Node.Child
        private JavaScriptNode arguments3_;

        private JSArrayBufferViewFillNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            Object arguments2Value_ = this.arguments2_.execute(frameValue);
            Object arguments3Value_ = this.arguments3_.execute(frameValue);
            return this.fill(arguments0Value_, arguments1Value_, arguments2Value_, arguments3Value_);
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
            s[0] = "fill";
            s[1] = (byte)1;
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static TypedArrayPrototypeBuiltins.JSArrayBufferViewFillNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSArrayBufferViewFillNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=TypedArrayPrototypeBuiltins.JSArrayBufferViewReverseNode.class)
    public static final class JSArrayBufferViewReverseNodeGen
    extends TypedArrayPrototypeBuiltins.JSArrayBufferViewReverseNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private DeletePropertyNode reverse0_deletePropertyNode_;

        private JSArrayBufferViewReverseNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSDynamicObject && JSGuards.isJSArrayBufferView(arguments0Value__ = (JSDynamicObject)arguments0Value_)) {
                return this.reverse(arguments0Value__, this.reverse0_deletePropertyNode_);
            }
            if ((state_0 & 2) != 0 && !JSGuards.isJSArrayBufferView(arguments0Value_)) {
                return this.reverse(arguments0Value_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.execute(frameValue);
        }

        private Object executeAndSpecialize(Object arguments0Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                JSDynamicObject arguments0Value_;
                int state_0 = this.state_0_;
                if (arguments0Value instanceof JSDynamicObject && JSGuards.isJSArrayBufferView(arguments0Value_ = (JSDynamicObject)arguments0Value)) {
                    this.reverse0_deletePropertyNode_ = super.insert(DeletePropertyNode.create(true, this.getContext()));
                    this.state_0_ = state_0 |= 1;
                    lock.unlock();
                    hasLock = false;
                    JSDynamicObject jSDynamicObject = this.reverse(arguments0Value_, this.reverse0_deletePropertyNode_);
                    return jSDynamicObject;
                }
                if (!JSGuards.isJSArrayBufferView(arguments0Value)) {
                    this.state_0_ = state_0 |= 2;
                    lock.unlock();
                    hasLock = false;
                    Object object = this.reverse(arguments0Value);
                    return object;
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
            s[0] = "reverse";
            if ((state_0 & 1) != 0) {
                s[1] = (byte)1;
                ArrayList<List<DeletePropertyNode>> cached = new ArrayList<List<DeletePropertyNode>>();
                cached.add(Arrays.asList(this.reverse0_deletePropertyNode_));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[1] = s;
            s = new Object[3];
            s[0] = "reverse";
            s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[2] = s;
            return Introspection.Provider.create(data);
        }

        public static TypedArrayPrototypeBuiltins.JSArrayBufferViewReverseNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSArrayBufferViewReverseNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=TypedArrayPrototypeBuiltins.JSArrayBufferViewForEachNode.class)
    public static final class JSArrayBufferViewForEachNodeGen
    extends TypedArrayPrototypeBuiltins.JSArrayBufferViewForEachNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @Node.Child
        private JavaScriptNode arguments2_;
        @CompilerDirectives.CompilationFinal
        private int state_0_;

        private JSArrayBufferViewForEachNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
                if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSDynamicObject && JSGuards.isJSArrayBufferView(arguments0Value__ = (JSDynamicObject)arguments0Value_)) {
                    return this.forEach(arguments0Value__, arguments1Value_, arguments2Value_);
                }
                if ((state_0 & 2) != 0 && !JSGuards.isJSArrayBufferView(arguments0Value_)) {
                    return this.forEachNonTypedArray(arguments0Value_, arguments1Value_, arguments2Value_);
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
            JSDynamicObject arguments0Value_;
            int state_0 = this.state_0_;
            if (arguments0Value instanceof JSDynamicObject && JSGuards.isJSArrayBufferView(arguments0Value_ = (JSDynamicObject)arguments0Value)) {
                this.state_0_ = state_0 |= 1;
                return this.forEach(arguments0Value_, arguments1Value, arguments2Value);
            }
            if (!JSGuards.isJSArrayBufferView(arguments0Value)) {
                this.state_0_ = state_0 |= 2;
                return this.forEachNonTypedArray(arguments0Value, arguments1Value, arguments2Value);
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
            Object[] data = new Object[3];
            data[0] = 0;
            int state_0 = this.state_0_;
            Object[] s = new Object[3];
            s[0] = "forEach";
            s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[1] = s;
            s = new Object[3];
            s[0] = "forEachNonTypedArray";
            s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[2] = s;
            return Introspection.Provider.create(data);
        }

        public static TypedArrayPrototypeBuiltins.JSArrayBufferViewForEachNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSArrayBufferViewForEachNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=TypedArrayPrototypeBuiltins.JSArrayBufferViewSetNode.class)
    public static final class JSArrayBufferViewSetNodeGen
    extends TypedArrayPrototypeBuiltins.JSArrayBufferViewSetNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @Node.Child
        private JavaScriptNode arguments2_;
        @CompilerDirectives.CompilationFinal
        private int state_0_;

        private JSArrayBufferViewSetNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
                if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSDynamicObject && JSGuards.isJSArrayBufferView(arguments0Value__ = (JSDynamicObject)arguments0Value_)) {
                    return this.set(arguments0Value__, arguments1Value_, arguments2Value_);
                }
                if ((state_0 & 2) != 0 && !JSGuards.isJSArrayBufferView(arguments0Value_)) {
                    return this.set(arguments0Value_, arguments1Value_, arguments2Value_);
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
            JSDynamicObject arguments0Value_;
            int state_0 = this.state_0_;
            if (arguments0Value instanceof JSDynamicObject && JSGuards.isJSArrayBufferView(arguments0Value_ = (JSDynamicObject)arguments0Value)) {
                this.state_0_ = state_0 |= 1;
                return this.set(arguments0Value_, arguments1Value, arguments2Value);
            }
            if (!JSGuards.isJSArrayBufferView(arguments0Value)) {
                this.state_0_ = state_0 |= 2;
                return this.set(arguments0Value, arguments1Value, arguments2Value);
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
            Object[] data = new Object[3];
            data[0] = 0;
            int state_0 = this.state_0_;
            Object[] s = new Object[3];
            s[0] = "set";
            s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[1] = s;
            s = new Object[3];
            s[0] = "set";
            s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[2] = s;
            return Introspection.Provider.create(data);
        }

        public static TypedArrayPrototypeBuiltins.JSArrayBufferViewSetNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSArrayBufferViewSetNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=TypedArrayPrototypeBuiltins.JSArrayBufferViewSubarrayNode.class)
    public static final class JSArrayBufferViewSubarrayNodeGen
    extends TypedArrayPrototypeBuiltins.JSArrayBufferViewSubarrayNode
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
        private ValueProfile subarray0_arrayTypeProfile_;
        @CompilerDirectives.CompilationFinal
        private Subarray1Data subarray1_cache;

        private JSArrayBufferViewSubarrayNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            if ((state_0 & 6) == 0 && state_0 != 0) {
                return this.execute_int_int0(state_0, frameValue);
            }
            return this.execute_generic1(state_0, frameValue);
        }

        private Object execute_int_int0(int state_0, VirtualFrame frameValue) {
            JSDynamicObject arguments0Value__;
            int arguments2Value_;
            int arguments1Value_;
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            try {
                arguments1Value_ = this.arguments1_.executeInt(frameValue);
            }
            catch (UnexpectedResultException ex) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                Object arguments2Value = this.arguments2_.execute(frameValue);
                return this.executeAndSpecialize(arguments0Value_, ex.getResult(), arguments2Value);
            }
            try {
                arguments2Value_ = this.arguments2_.executeInt(frameValue);
            }
            catch (UnexpectedResultException ex) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.executeAndSpecialize(arguments0Value_, arguments1Value_, ex.getResult());
            }
            assert ((state_0 & 1) != 0);
            if (arguments0Value_ instanceof JSDynamicObject && JSGuards.isJSArrayBufferView(arguments0Value__ = (JSDynamicObject)arguments0Value_)) {
                return this.subarray(arguments0Value__, arguments1Value_, arguments2Value_, this.subarray0_arrayTypeProfile_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_, arguments2Value_);
        }

        private Object execute_generic1(int state_0, VirtualFrame frameValue) {
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            Object arguments2Value_ = this.arguments2_.execute(frameValue);
            if ((state_0 & 3) != 0 && arguments0Value_ instanceof JSDynamicObject) {
                Subarray1Data s1_;
                JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
                if ((state_0 & 1) != 0 && arguments1Value_ instanceof Integer) {
                    int arguments1Value__ = (Integer)arguments1Value_;
                    if (arguments2Value_ instanceof Integer) {
                        int arguments2Value__ = (Integer)arguments2Value_;
                        if (JSGuards.isJSArrayBufferView(arguments0Value__)) {
                            return this.subarray(arguments0Value__, arguments1Value__, arguments2Value__, this.subarray0_arrayTypeProfile_);
                        }
                    }
                }
                if ((state_0 & 2) != 0 && (s1_ = this.subarray1_cache) != null && JSGuards.isJSArrayBufferView(arguments0Value__)) {
                    return this.subarray(arguments0Value__, arguments1Value_, arguments2Value_, s1_.arrayTypeProfile_, s1_.negativeBegin_, s1_.negativeEnd_, s1_.smallerEnd_);
                }
            }
            if ((state_0 & 4) != 0 && !JSGuards.isJSArrayBufferView(arguments0Value_)) {
                return this.subarrayGeneric(arguments0Value_, arguments1Value_, arguments2Value_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_, arguments2Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.execute(frameValue);
        }

        private JSTypedArrayObject executeAndSpecialize(Object arguments0Value, Object arguments1Value, Object arguments2Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                if (arguments0Value instanceof JSDynamicObject) {
                    JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
                    if (arguments1Value instanceof Integer) {
                        int arguments1Value_ = (Integer)arguments1Value;
                        if (arguments2Value instanceof Integer) {
                            int arguments2Value_ = (Integer)arguments2Value;
                            if (JSGuards.isJSArrayBufferView(arguments0Value_)) {
                                this.subarray0_arrayTypeProfile_ = ValueProfile.createIdentityProfile();
                                this.state_0_ = state_0 |= 1;
                                lock.unlock();
                                hasLock = false;
                                JSTypedArrayObject jSTypedArrayObject = this.subarray(arguments0Value_, arguments1Value_, arguments2Value_, this.subarray0_arrayTypeProfile_);
                                return jSTypedArrayObject;
                            }
                        }
                    }
                    if (JSGuards.isJSArrayBufferView(arguments0Value_)) {
                        Subarray1Data s1_ = new Subarray1Data();
                        s1_.arrayTypeProfile_ = ValueProfile.createIdentityProfile();
                        s1_.negativeBegin_ = ConditionProfile.createBinaryProfile();
                        s1_.negativeEnd_ = ConditionProfile.createBinaryProfile();
                        s1_.smallerEnd_ = ConditionProfile.createBinaryProfile();
                        VarHandle.storeStoreFence();
                        this.subarray1_cache = s1_;
                        this.state_0_ = state_0 |= 2;
                        lock.unlock();
                        hasLock = false;
                        JSTypedArrayObject jSTypedArrayObject = this.subarray(arguments0Value_, arguments1Value, arguments2Value, s1_.arrayTypeProfile_, s1_.negativeBegin_, s1_.negativeEnd_, s1_.smallerEnd_);
                        return jSTypedArrayObject;
                    }
                }
                if (!JSGuards.isJSArrayBufferView(arguments0Value)) {
                    this.state_0_ = state_0 |= 4;
                    lock.unlock();
                    hasLock = false;
                    JSTypedArrayObject jSTypedArrayObject = this.subarrayGeneric(arguments0Value, arguments1Value, arguments2Value);
                    return jSTypedArrayObject;
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
            ArrayList<List<Profile>> cached;
            Object[] data = new Object[4];
            data[0] = 0;
            int state_0 = this.state_0_;
            Object[] s = new Object[3];
            s[0] = "subarray";
            if ((state_0 & 1) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList<List<Profile>>();
                cached.add(Arrays.asList(this.subarray0_arrayTypeProfile_));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[1] = s;
            s = new Object[3];
            s[0] = "subarray";
            if ((state_0 & 2) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList();
                Subarray1Data s1_ = this.subarray1_cache;
                if (s1_ != null) {
                    cached.add(Arrays.asList(s1_.arrayTypeProfile_, s1_.negativeBegin_, s1_.negativeEnd_, s1_.smallerEnd_));
                }
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[2] = s;
            s = new Object[3];
            s[0] = "subarrayGeneric";
            s[1] = (state_0 & 4) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[3] = s;
            return Introspection.Provider.create(data);
        }

        public static TypedArrayPrototypeBuiltins.JSArrayBufferViewSubarrayNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSArrayBufferViewSubarrayNodeGen(context, builtin, arguments);
        }

        @GeneratedBy(value=TypedArrayPrototypeBuiltins.JSArrayBufferViewSubarrayNode.class)
        private static final class Subarray1Data {
            @CompilerDirectives.CompilationFinal
            ValueProfile arrayTypeProfile_;
            @CompilerDirectives.CompilationFinal
            ConditionProfile negativeBegin_;
            @CompilerDirectives.CompilationFinal
            ConditionProfile negativeEnd_;
            @CompilerDirectives.CompilationFinal
            ConditionProfile smallerEnd_;

            Subarray1Data() {
            }
        }
    }
}

