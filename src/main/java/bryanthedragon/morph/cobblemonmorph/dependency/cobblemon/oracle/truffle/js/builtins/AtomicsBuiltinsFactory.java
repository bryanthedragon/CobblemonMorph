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
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.js.builtins.AtomicsBuiltins;
import com.oracle.truffle.js.nodes.JSTypesGen;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.cast.JSToIndexNode;
import com.oracle.truffle.js.nodes.cast.JSToInt32Node;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.SafeInteger;
import com.oracle.truffle.js.runtime.array.ScriptArray;
import com.oracle.truffle.js.runtime.array.TypedArray;
import com.oracle.truffle.js.runtime.builtins.JSArrayBufferView;
import com.oracle.truffle.js.runtime.builtins.JSTypedArrayObject;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=AtomicsBuiltins.class)
public final class AtomicsBuiltinsFactory {

    @GeneratedBy(value=AtomicsBuiltins.AtomicsIsLockFreeNode.class)
    public static final class AtomicsIsLockFreeNodeGen
    extends AtomicsBuiltins.AtomicsIsLockFreeNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private JSToInt32Node generic_toInt32Node_;

        private AtomicsIsLockFreeNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            if ((state_0 & 2) == 0 && state_0 != 0) {
                return this.execute_int0(state_0, frameValue);
            }
            return this.execute_generic1(state_0, frameValue);
        }

        private Object execute_int0(int state_0, VirtualFrame frameValue) {
            int arguments0Value_;
            try {
                arguments0Value_ = this.arguments0_.executeInt(frameValue);
            }
            catch (UnexpectedResultException ex) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.executeAndSpecialize(ex.getResult());
            }
            assert ((state_0 & 1) != 0);
            return AtomicsBuiltins.AtomicsIsLockFreeNode.doInt(arguments0Value_);
        }

        private Object execute_generic1(int state_0, VirtualFrame frameValue) {
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            if ((state_0 & 1) != 0 && arguments0Value_ instanceof Integer) {
                int arguments0Value__ = (Integer)arguments0Value_;
                return AtomicsBuiltins.AtomicsIsLockFreeNode.doInt(arguments0Value__);
            }
            if ((state_0 & 2) != 0) {
                return AtomicsBuiltins.AtomicsIsLockFreeNode.doGeneric(arguments0Value_, this.generic_toInt32Node_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_);
        }

        @Override
        public boolean executeBoolean(VirtualFrame frameValue) {
            int state_0 = this.state_0_;
            if ((state_0 & 2) == 0 && state_0 != 0) {
                return this.executeBoolean_int2(state_0, frameValue);
            }
            return this.executeBoolean_generic3(state_0, frameValue);
        }

        private boolean executeBoolean_int2(int state_0, VirtualFrame frameValue) {
            int arguments0Value_;
            try {
                arguments0Value_ = this.arguments0_.executeInt(frameValue);
            }
            catch (UnexpectedResultException ex) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.executeAndSpecialize(ex.getResult());
            }
            assert ((state_0 & 1) != 0);
            return AtomicsBuiltins.AtomicsIsLockFreeNode.doInt(arguments0Value_);
        }

        private boolean executeBoolean_generic3(int state_0, VirtualFrame frameValue) {
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            if ((state_0 & 1) != 0 && arguments0Value_ instanceof Integer) {
                int arguments0Value__ = (Integer)arguments0Value_;
                return AtomicsBuiltins.AtomicsIsLockFreeNode.doInt(arguments0Value__);
            }
            if ((state_0 & 2) != 0) {
                return AtomicsBuiltins.AtomicsIsLockFreeNode.doGeneric(arguments0Value_, this.generic_toInt32Node_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.executeBoolean(frameValue);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private boolean executeAndSpecialize(Object arguments0Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                if (arguments0Value instanceof Integer) {
                    int arguments0Value_ = (Integer)arguments0Value;
                    this.state_0_ = state_0 |= 1;
                    lock.unlock();
                    hasLock = false;
                    boolean bl = AtomicsBuiltins.AtomicsIsLockFreeNode.doInt(arguments0Value_);
                    return bl;
                }
                this.generic_toInt32Node_ = super.insert(JSToInt32Node.create());
                this.state_0_ = state_0 |= 2;
                lock.unlock();
                hasLock = false;
                boolean bl = AtomicsBuiltins.AtomicsIsLockFreeNode.doGeneric(arguments0Value, this.generic_toInt32Node_);
                return bl;
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
            s[0] = "doInt";
            s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[1] = s;
            s = new Object[3];
            s[0] = "doGeneric";
            if ((state_0 & 2) != 0) {
                s[1] = (byte)1;
                ArrayList<List<JSToInt32Node>> cached = new ArrayList<List<JSToInt32Node>>();
                cached.add(Arrays.asList(this.generic_toInt32Node_));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[2] = s;
            return Introspection.Provider.create(data);
        }

        public static AtomicsBuiltins.AtomicsIsLockFreeNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new AtomicsIsLockFreeNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=AtomicsBuiltins.AtomicsWaitAsyncNode.class)
    public static final class AtomicsWaitAsyncNodeGen
    extends AtomicsBuiltins.AtomicsWaitAsyncNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @Node.Child
        private JavaScriptNode arguments2_;
        @Node.Child
        private JavaScriptNode arguments3_;

        private AtomicsWaitAsyncNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            return this.doGeneric(frameValue, arguments0Value_, arguments1Value_, arguments2Value_, arguments3Value_);
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
            s[0] = "doGeneric";
            s[1] = (byte)1;
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static AtomicsBuiltins.AtomicsWaitAsyncNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new AtomicsWaitAsyncNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=AtomicsBuiltins.AtomicsWaitNode.class)
    public static final class AtomicsWaitNodeGen
    extends AtomicsBuiltins.AtomicsWaitNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @Node.Child
        private JavaScriptNode arguments2_;
        @Node.Child
        private JavaScriptNode arguments3_;

        private AtomicsWaitNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            return this.doGeneric(frameValue, arguments0Value_, arguments1Value_, arguments2Value_, arguments3Value_);
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
            s[0] = "doGeneric";
            s[1] = (byte)1;
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static AtomicsBuiltins.AtomicsWaitNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new AtomicsWaitNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=AtomicsBuiltins.AtomicsNotifyNode.class)
    public static final class AtomicsNotifyNodeGen
    extends AtomicsBuiltins.AtomicsNotifyNode
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
        private NotifyData notify_cache;

        private AtomicsNotifyNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            NotifyData s0_;
            int state_0 = this.state_0_;
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            Object arguments2Value_ = this.arguments2_.execute(frameValue);
            if (state_0 != 0 && (s0_ = this.notify_cache) != null) {
                return this.doNotify(arguments0Value_, arguments1Value_, arguments2Value_, s0_.toIndexNode_, s0_.toInt32Node_, s0_.notSharedArrayBuffer_);
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
        private Object executeAndSpecialize(Object arguments0Value, Object arguments1Value, Object arguments2Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                NotifyData s0_ = super.insert(new NotifyData());
                s0_.toIndexNode_ = s0_.insertAccessor(JSToIndexNode.create());
                s0_.toInt32Node_ = s0_.insertAccessor(JSToInt32Node.create());
                s0_.notSharedArrayBuffer_ = BranchProfile.create();
                VarHandle.storeStoreFence();
                this.notify_cache = s0_;
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                Object object = this.doNotify(arguments0Value, arguments1Value, arguments2Value, s0_.toIndexNode_, s0_.toInt32Node_, s0_.notSharedArrayBuffer_);
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
            s[0] = "doNotify";
            if (state_0 != 0) {
                s[1] = (byte)1;
                ArrayList<List<Cloneable>> cached = new ArrayList<List<Cloneable>>();
                NotifyData s0_ = this.notify_cache;
                if (s0_ != null) {
                    cached.add(Arrays.asList(s0_.toIndexNode_, s0_.toInt32Node_, s0_.notSharedArrayBuffer_));
                }
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static AtomicsBuiltins.AtomicsNotifyNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new AtomicsNotifyNodeGen(context, builtin, arguments);
        }

        @GeneratedBy(value=AtomicsBuiltins.AtomicsNotifyNode.class)
        private static final class NotifyData
        extends Node {
            @Node.Child
            JSToIndexNode toIndexNode_;
            @Node.Child
            JSToInt32Node toInt32Node_;
            @CompilerDirectives.CompilationFinal
            BranchProfile notSharedArrayBuffer_;

            NotifyData() {
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

    @GeneratedBy(value=AtomicsBuiltins.AtomicsComputeNode.class)
    public static final class AtomicsComputeNodeGen
    extends AtomicsBuiltins.AtomicsComputeNode
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
        private JSToIndexNode toIndex;
        @CompilerDirectives.CompilationFinal
        private BranchProfile generic_notSharedArrayBuffer_;

        private AtomicsComputeNodeGen(JSContext context, JSBuiltin builtin, AtomicsBuiltins.AtomicIntBinaryOperator intOperator, AtomicsBuiltins.AtomicBinaryOperator<BigInt> bigIntOperator, JavaScriptNode[] arguments) {
            super(context, builtin, intOperator, bigIntOperator);
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
            if ((state_0 & 0x3C0) == 0 && state_0 != 0) {
                return this.execute_int_int0(state_0, frameValue);
            }
            if ((state_0 & 0x3BF) == 0 && state_0 != 0) {
                return this.execute_int1(state_0, frameValue);
            }
            return this.execute_generic2(state_0, frameValue);
        }

        private Object execute_int_int0(int state_0, VirtualFrame frameValue) {
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
            if ((state_0 & 0x3F) != 0 && arguments0Value_ instanceof JSTypedArrayObject) {
                TypedArray sharedUint32Array_ta__;
                TypedArray sharedInt32Array_ta__;
                TypedArray sharedUint16Array_ta__;
                TypedArray sharedInt16Array_ta__;
                TypedArray sharedUint8Array_ta__;
                TypedArray sharedInt8Array_ta__;
                JSTypedArrayObject arguments0Value__ = (JSTypedArrayObject)arguments0Value_;
                if ((state_0 & 1) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__) && AtomicsBuiltins.AtomicsOperationNode.isDirectInt8Array(sharedInt8Array_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__)) && sharedInt8Array_ta__.isInBoundsFast(arguments0Value__, arguments1Value_)) {
                    return this.doSharedInt8Array(arguments0Value__, arguments1Value_, arguments2Value_, sharedInt8Array_ta__);
                }
                if ((state_0 & 2) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__) && AtomicsBuiltins.AtomicsOperationNode.isDirectUint8Array(sharedUint8Array_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__)) && sharedUint8Array_ta__.isInBoundsFast(arguments0Value__, arguments1Value_)) {
                    return this.doSharedUint8Array(arguments0Value__, arguments1Value_, arguments2Value_, sharedUint8Array_ta__);
                }
                if ((state_0 & 4) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__) && AtomicsBuiltins.AtomicsOperationNode.isDirectInt16Array(sharedInt16Array_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__)) && sharedInt16Array_ta__.isInBoundsFast(arguments0Value__, arguments1Value_)) {
                    return this.doSharedInt16Array(arguments0Value__, arguments1Value_, arguments2Value_, sharedInt16Array_ta__);
                }
                if ((state_0 & 8) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__) && AtomicsBuiltins.AtomicsOperationNode.isDirectUint16Array(sharedUint16Array_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__)) && sharedUint16Array_ta__.isInBoundsFast(arguments0Value__, arguments1Value_)) {
                    return this.doSharedUint16Array(arguments0Value__, arguments1Value_, arguments2Value_, sharedUint16Array_ta__);
                }
                if ((state_0 & 0x10) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__) && AtomicsBuiltins.AtomicsOperationNode.isDirectInt32Array(sharedInt32Array_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__)) && sharedInt32Array_ta__.isInBoundsFast(arguments0Value__, arguments1Value_)) {
                    return this.doSharedInt32Array(arguments0Value__, arguments1Value_, arguments2Value_, sharedInt32Array_ta__);
                }
                if ((state_0 & 0x20) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__) && AtomicsBuiltins.AtomicsOperationNode.isDirectUint32Array(sharedUint32Array_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__)) && sharedUint32Array_ta__.isInBoundsFast(arguments0Value__, arguments1Value_)) {
                    return this.doSharedUint32Array(arguments0Value__, arguments1Value_, arguments2Value_, sharedUint32Array_ta__);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_, arguments2Value_);
        }

        private Object execute_int1(int state_0, VirtualFrame frameValue) {
            TypedArray sharedInt32ArrayObjIdx_ta__;
            JSTypedArrayObject arguments0Value__;
            int arguments2Value_;
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            try {
                arguments2Value_ = this.arguments2_.executeInt(frameValue);
            }
            catch (UnexpectedResultException ex) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.executeAndSpecialize(arguments0Value_, arguments1Value_, ex.getResult());
            }
            assert ((state_0 & 0x40) != 0);
            if (arguments0Value_ instanceof JSTypedArrayObject && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__ = (JSTypedArrayObject)arguments0Value_) && AtomicsBuiltins.AtomicsOperationNode.isDirectInt32Array(sharedInt32ArrayObjIdx_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__))) {
                return this.doSharedInt32ArrayObjIdx(arguments0Value__, arguments1Value_, arguments2Value_, sharedInt32ArrayObjIdx_ta__, this.toIndex);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_, arguments2Value_);
        }

        private Object execute_generic2(int state_0, VirtualFrame frameValue) {
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            Object arguments2Value_ = this.arguments2_.execute(frameValue);
            if ((state_0 & 0x1FF) != 0 && arguments0Value_ instanceof JSTypedArrayObject) {
                JSTypedArrayObject arguments0Value__ = (JSTypedArrayObject)arguments0Value_;
                if ((state_0 & 0x7F) != 0 && arguments2Value_ instanceof Integer) {
                    TypedArray sharedInt32ArrayObjIdx_ta__;
                    int arguments2Value__ = (Integer)arguments2Value_;
                    if ((state_0 & 0x3F) != 0 && arguments1Value_ instanceof Integer) {
                        TypedArray sharedUint32Array_ta__;
                        TypedArray sharedInt32Array_ta__;
                        TypedArray sharedUint16Array_ta__;
                        TypedArray sharedInt16Array_ta__;
                        TypedArray sharedUint8Array_ta__;
                        TypedArray sharedInt8Array_ta__;
                        int arguments1Value__ = (Integer)arguments1Value_;
                        if ((state_0 & 1) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__) && AtomicsBuiltins.AtomicsOperationNode.isDirectInt8Array(sharedInt8Array_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__)) && sharedInt8Array_ta__.isInBoundsFast(arguments0Value__, arguments1Value__)) {
                            return this.doSharedInt8Array(arguments0Value__, arguments1Value__, arguments2Value__, sharedInt8Array_ta__);
                        }
                        if ((state_0 & 2) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__) && AtomicsBuiltins.AtomicsOperationNode.isDirectUint8Array(sharedUint8Array_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__)) && sharedUint8Array_ta__.isInBoundsFast(arguments0Value__, arguments1Value__)) {
                            return this.doSharedUint8Array(arguments0Value__, arguments1Value__, arguments2Value__, sharedUint8Array_ta__);
                        }
                        if ((state_0 & 4) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__) && AtomicsBuiltins.AtomicsOperationNode.isDirectInt16Array(sharedInt16Array_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__)) && sharedInt16Array_ta__.isInBoundsFast(arguments0Value__, arguments1Value__)) {
                            return this.doSharedInt16Array(arguments0Value__, arguments1Value__, arguments2Value__, sharedInt16Array_ta__);
                        }
                        if ((state_0 & 8) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__) && AtomicsBuiltins.AtomicsOperationNode.isDirectUint16Array(sharedUint16Array_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__)) && sharedUint16Array_ta__.isInBoundsFast(arguments0Value__, arguments1Value__)) {
                            return this.doSharedUint16Array(arguments0Value__, arguments1Value__, arguments2Value__, sharedUint16Array_ta__);
                        }
                        if ((state_0 & 0x10) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__) && AtomicsBuiltins.AtomicsOperationNode.isDirectInt32Array(sharedInt32Array_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__)) && sharedInt32Array_ta__.isInBoundsFast(arguments0Value__, arguments1Value__)) {
                            return this.doSharedInt32Array(arguments0Value__, arguments1Value__, arguments2Value__, sharedInt32Array_ta__);
                        }
                        if ((state_0 & 0x20) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__) && AtomicsBuiltins.AtomicsOperationNode.isDirectUint32Array(sharedUint32Array_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__)) && sharedUint32Array_ta__.isInBoundsFast(arguments0Value__, arguments1Value__)) {
                            return this.doSharedUint32Array(arguments0Value__, arguments1Value__, arguments2Value__, sharedUint32Array_ta__);
                        }
                    }
                    if ((state_0 & 0x40) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__) && AtomicsBuiltins.AtomicsOperationNode.isDirectInt32Array(sharedInt32ArrayObjIdx_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__))) {
                        return this.doSharedInt32ArrayObjIdx(arguments0Value__, arguments1Value_, arguments2Value__, sharedInt32ArrayObjIdx_ta__, this.toIndex);
                    }
                }
                if ((state_0 & 0x180) != 0) {
                    TypedArray sharedBigUint64Array_ta__;
                    TypedArray sharedBigInt64Array_ta__;
                    if ((state_0 & 0x80) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__) && AtomicsBuiltins.AtomicsOperationNode.isDirectBigInt64Array(sharedBigInt64Array_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__))) {
                        return this.doSharedBigInt64Array(arguments0Value__, arguments1Value_, arguments2Value_, sharedBigInt64Array_ta__, this.toIndex);
                    }
                    if ((state_0 & 0x100) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__) && AtomicsBuiltins.AtomicsOperationNode.isDirectBigUint64Array(sharedBigUint64Array_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__))) {
                        return this.doSharedBigUint64Array(arguments0Value__, arguments1Value_, arguments2Value_, sharedBigUint64Array_ta__, this.toIndex);
                    }
                }
            }
            if ((state_0 & 0x200) != 0) {
                return this.doGeneric(arguments0Value_, arguments1Value_, arguments2Value_, this.toIndex, this.generic_notSharedArrayBuffer_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_, arguments2Value_);
        }

        @Override
        public int executeInt(VirtualFrame frameValue) throws UnexpectedResultException {
            int state_0 = this.state_0_;
            if ((state_0 & 0x200) != 0) {
                return JSTypesGen.expectInteger(this.execute(frameValue));
            }
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            if ((state_0 & 0x40) == 0 && (state_0 & 0x5F) != 0) {
                return this.executeInt_int_int3(state_0, frameValue, arguments0Value_);
            }
            if ((state_0 & 0x1F) == 0 && (state_0 & 0x5F) != 0) {
                return this.executeInt_int4(state_0, frameValue, arguments0Value_);
            }
            return this.executeInt_generic5(state_0, frameValue, arguments0Value_);
        }

        private int executeInt_int_int3(int state_0, VirtualFrame frameValue, Object arguments0Value_) throws UnexpectedResultException {
            int arguments2Value_;
            int arguments1Value_;
            try {
                arguments1Value_ = this.arguments1_.executeInt(frameValue);
            }
            catch (UnexpectedResultException ex) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                Object arguments2Value = this.arguments2_.execute(frameValue);
                return JSTypesGen.expectInteger(this.executeAndSpecialize(arguments0Value_, ex.getResult(), arguments2Value));
            }
            try {
                arguments2Value_ = this.arguments2_.executeInt(frameValue);
            }
            catch (UnexpectedResultException ex) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return JSTypesGen.expectInteger(this.executeAndSpecialize(arguments0Value_, arguments1Value_, ex.getResult()));
            }
            if ((state_0 & 0x1F) != 0 && arguments0Value_ instanceof JSTypedArrayObject) {
                TypedArray sharedInt32Array_ta__;
                TypedArray sharedUint16Array_ta__;
                TypedArray sharedInt16Array_ta__;
                TypedArray sharedUint8Array_ta__;
                TypedArray sharedInt8Array_ta__;
                JSTypedArrayObject arguments0Value__ = (JSTypedArrayObject)arguments0Value_;
                if ((state_0 & 1) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__) && AtomicsBuiltins.AtomicsOperationNode.isDirectInt8Array(sharedInt8Array_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__)) && sharedInt8Array_ta__.isInBoundsFast(arguments0Value__, arguments1Value_)) {
                    return this.doSharedInt8Array(arguments0Value__, arguments1Value_, arguments2Value_, sharedInt8Array_ta__);
                }
                if ((state_0 & 2) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__) && AtomicsBuiltins.AtomicsOperationNode.isDirectUint8Array(sharedUint8Array_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__)) && sharedUint8Array_ta__.isInBoundsFast(arguments0Value__, arguments1Value_)) {
                    return this.doSharedUint8Array(arguments0Value__, arguments1Value_, arguments2Value_, sharedUint8Array_ta__);
                }
                if ((state_0 & 4) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__) && AtomicsBuiltins.AtomicsOperationNode.isDirectInt16Array(sharedInt16Array_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__)) && sharedInt16Array_ta__.isInBoundsFast(arguments0Value__, arguments1Value_)) {
                    return this.doSharedInt16Array(arguments0Value__, arguments1Value_, arguments2Value_, sharedInt16Array_ta__);
                }
                if ((state_0 & 8) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__) && AtomicsBuiltins.AtomicsOperationNode.isDirectUint16Array(sharedUint16Array_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__)) && sharedUint16Array_ta__.isInBoundsFast(arguments0Value__, arguments1Value_)) {
                    return this.doSharedUint16Array(arguments0Value__, arguments1Value_, arguments2Value_, sharedUint16Array_ta__);
                }
                if ((state_0 & 0x10) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__) && AtomicsBuiltins.AtomicsOperationNode.isDirectInt32Array(sharedInt32Array_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__)) && sharedInt32Array_ta__.isInBoundsFast(arguments0Value__, arguments1Value_)) {
                    return this.doSharedInt32Array(arguments0Value__, arguments1Value_, arguments2Value_, sharedInt32Array_ta__);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return JSTypesGen.expectInteger(this.executeAndSpecialize(arguments0Value_, arguments1Value_, arguments2Value_));
        }

        private int executeInt_int4(int state_0, VirtualFrame frameValue, Object arguments0Value_) throws UnexpectedResultException {
            TypedArray sharedInt32ArrayObjIdx_ta__;
            JSTypedArrayObject arguments0Value__;
            int arguments2Value_;
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            try {
                arguments2Value_ = this.arguments2_.executeInt(frameValue);
            }
            catch (UnexpectedResultException ex) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return JSTypesGen.expectInteger(this.executeAndSpecialize(arguments0Value_, arguments1Value_, ex.getResult()));
            }
            assert ((state_0 & 0x40) != 0);
            if (arguments0Value_ instanceof JSTypedArrayObject && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__ = (JSTypedArrayObject)arguments0Value_) && AtomicsBuiltins.AtomicsOperationNode.isDirectInt32Array(sharedInt32ArrayObjIdx_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__))) {
                return this.doSharedInt32ArrayObjIdx(arguments0Value__, arguments1Value_, arguments2Value_, sharedInt32ArrayObjIdx_ta__, this.toIndex);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return JSTypesGen.expectInteger(this.executeAndSpecialize(arguments0Value_, arguments1Value_, arguments2Value_));
        }

        private int executeInt_generic5(int state_0, VirtualFrame frameValue, Object arguments0Value_) throws UnexpectedResultException {
            int arguments2Value_;
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            try {
                arguments2Value_ = this.arguments2_.executeInt(frameValue);
            }
            catch (UnexpectedResultException ex) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return JSTypesGen.expectInteger(this.executeAndSpecialize(arguments0Value_, arguments1Value_, ex.getResult()));
            }
            if ((state_0 & 0x5F) != 0 && arguments0Value_ instanceof JSTypedArrayObject) {
                TypedArray sharedInt32ArrayObjIdx_ta__;
                JSTypedArrayObject arguments0Value__ = (JSTypedArrayObject)arguments0Value_;
                if ((state_0 & 0x1F) != 0 && arguments1Value_ instanceof Integer) {
                    TypedArray sharedInt32Array_ta__;
                    TypedArray sharedUint16Array_ta__;
                    TypedArray sharedInt16Array_ta__;
                    TypedArray sharedUint8Array_ta__;
                    TypedArray sharedInt8Array_ta__;
                    int arguments1Value__ = (Integer)arguments1Value_;
                    if ((state_0 & 1) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__) && AtomicsBuiltins.AtomicsOperationNode.isDirectInt8Array(sharedInt8Array_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__)) && sharedInt8Array_ta__.isInBoundsFast(arguments0Value__, arguments1Value__)) {
                        return this.doSharedInt8Array(arguments0Value__, arguments1Value__, arguments2Value_, sharedInt8Array_ta__);
                    }
                    if ((state_0 & 2) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__) && AtomicsBuiltins.AtomicsOperationNode.isDirectUint8Array(sharedUint8Array_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__)) && sharedUint8Array_ta__.isInBoundsFast(arguments0Value__, arguments1Value__)) {
                        return this.doSharedUint8Array(arguments0Value__, arguments1Value__, arguments2Value_, sharedUint8Array_ta__);
                    }
                    if ((state_0 & 4) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__) && AtomicsBuiltins.AtomicsOperationNode.isDirectInt16Array(sharedInt16Array_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__)) && sharedInt16Array_ta__.isInBoundsFast(arguments0Value__, arguments1Value__)) {
                        return this.doSharedInt16Array(arguments0Value__, arguments1Value__, arguments2Value_, sharedInt16Array_ta__);
                    }
                    if ((state_0 & 8) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__) && AtomicsBuiltins.AtomicsOperationNode.isDirectUint16Array(sharedUint16Array_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__)) && sharedUint16Array_ta__.isInBoundsFast(arguments0Value__, arguments1Value__)) {
                        return this.doSharedUint16Array(arguments0Value__, arguments1Value__, arguments2Value_, sharedUint16Array_ta__);
                    }
                    if ((state_0 & 0x10) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__) && AtomicsBuiltins.AtomicsOperationNode.isDirectInt32Array(sharedInt32Array_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__)) && sharedInt32Array_ta__.isInBoundsFast(arguments0Value__, arguments1Value__)) {
                        return this.doSharedInt32Array(arguments0Value__, arguments1Value__, arguments2Value_, sharedInt32Array_ta__);
                    }
                }
                if ((state_0 & 0x40) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__) && AtomicsBuiltins.AtomicsOperationNode.isDirectInt32Array(sharedInt32ArrayObjIdx_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__))) {
                    return this.doSharedInt32ArrayObjIdx(arguments0Value__, arguments1Value_, arguments2Value_, sharedInt32ArrayObjIdx_ta__, this.toIndex);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return JSTypesGen.expectInteger(this.executeAndSpecialize(arguments0Value_, arguments1Value_, arguments2Value_));
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            int state_0 = this.state_0_;
            try {
                if ((state_0 & 0x3A0) == 0 && state_0 != 0) {
                    this.executeInt(frameValue);
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
        private Object executeAndSpecialize(Object arguments0Value, Object arguments1Value, Object arguments2Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                if (arguments0Value instanceof JSTypedArrayObject) {
                    BigInt bigInt;
                    JSTypedArrayObject arguments0Value_ = (JSTypedArrayObject)arguments0Value;
                    if (arguments2Value instanceof Integer) {
                        int arguments2Value_ = (Integer)arguments2Value;
                        if (arguments1Value instanceof Integer) {
                            int arguments1Value_ = (Integer)arguments1Value;
                            TypedArray sharedInt8Array_ta__ = null;
                            if (AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value_) && AtomicsBuiltins.AtomicsOperationNode.isDirectInt8Array(sharedInt8Array_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value_)) && sharedInt8Array_ta__.isInBoundsFast(arguments0Value_, arguments1Value_)) {
                                this.state_0_ = state_0 |= 1;
                                lock.unlock();
                                hasLock = false;
                                Integer n = this.doSharedInt8Array(arguments0Value_, arguments1Value_, arguments2Value_, sharedInt8Array_ta__);
                                return n;
                            }
                            TypedArray sharedUint8Array_ta__ = null;
                            if (AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value_) && AtomicsBuiltins.AtomicsOperationNode.isDirectUint8Array(sharedUint8Array_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value_)) && sharedUint8Array_ta__.isInBoundsFast(arguments0Value_, arguments1Value_)) {
                                this.state_0_ = state_0 |= 2;
                                lock.unlock();
                                hasLock = false;
                                Integer n = this.doSharedUint8Array(arguments0Value_, arguments1Value_, arguments2Value_, sharedUint8Array_ta__);
                                return n;
                            }
                            TypedArray sharedInt16Array_ta__ = null;
                            if (AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value_) && AtomicsBuiltins.AtomicsOperationNode.isDirectInt16Array(sharedInt16Array_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value_)) && sharedInt16Array_ta__.isInBoundsFast(arguments0Value_, arguments1Value_)) {
                                this.state_0_ = state_0 |= 4;
                                lock.unlock();
                                hasLock = false;
                                Integer n = this.doSharedInt16Array(arguments0Value_, arguments1Value_, arguments2Value_, sharedInt16Array_ta__);
                                return n;
                            }
                            TypedArray sharedUint16Array_ta__ = null;
                            if (AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value_) && AtomicsBuiltins.AtomicsOperationNode.isDirectUint16Array(sharedUint16Array_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value_)) && sharedUint16Array_ta__.isInBoundsFast(arguments0Value_, arguments1Value_)) {
                                this.state_0_ = state_0 |= 8;
                                lock.unlock();
                                hasLock = false;
                                Integer n = this.doSharedUint16Array(arguments0Value_, arguments1Value_, arguments2Value_, sharedUint16Array_ta__);
                                return n;
                            }
                            TypedArray sharedInt32Array_ta__ = null;
                            if (AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value_) && AtomicsBuiltins.AtomicsOperationNode.isDirectInt32Array(sharedInt32Array_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value_)) && sharedInt32Array_ta__.isInBoundsFast(arguments0Value_, arguments1Value_)) {
                                this.state_0_ = state_0 |= 0x10;
                                lock.unlock();
                                hasLock = false;
                                Integer n = this.doSharedInt32Array(arguments0Value_, arguments1Value_, arguments2Value_, sharedInt32Array_ta__);
                                return n;
                            }
                            TypedArray sharedUint32Array_ta__ = null;
                            if (AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value_) && AtomicsBuiltins.AtomicsOperationNode.isDirectUint32Array(sharedUint32Array_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value_)) && sharedUint32Array_ta__.isInBoundsFast(arguments0Value_, arguments1Value_)) {
                                this.state_0_ = state_0 |= 0x20;
                                lock.unlock();
                                hasLock = false;
                                SafeInteger safeInteger = this.doSharedUint32Array(arguments0Value_, arguments1Value_, arguments2Value_, sharedUint32Array_ta__);
                                return safeInteger;
                            }
                        }
                        TypedArray sharedInt32ArrayObjIdx_ta__ = null;
                        if (AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value_) && AtomicsBuiltins.AtomicsOperationNode.isDirectInt32Array(sharedInt32ArrayObjIdx_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value_))) {
                            this.toIndex = super.insert(this.toIndex == null ? JSToIndexNode.create() : this.toIndex);
                            this.state_0_ = state_0 |= 0x40;
                            lock.unlock();
                            hasLock = false;
                            Integer n = this.doSharedInt32ArrayObjIdx(arguments0Value_, arguments1Value, arguments2Value_, sharedInt32ArrayObjIdx_ta__, this.toIndex);
                            return n;
                        }
                    }
                    TypedArray sharedBigInt64Array_ta__ = null;
                    if (AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value_) && AtomicsBuiltins.AtomicsOperationNode.isDirectBigInt64Array(sharedBigInt64Array_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value_))) {
                        this.toIndex = super.insert(this.toIndex == null ? JSToIndexNode.create() : this.toIndex);
                        this.state_0_ = state_0 |= 0x80;
                        lock.unlock();
                        hasLock = false;
                        bigInt = this.doSharedBigInt64Array(arguments0Value_, arguments1Value, arguments2Value, sharedBigInt64Array_ta__, this.toIndex);
                        return bigInt;
                    }
                    TypedArray sharedBigUint64Array_ta__ = null;
                    if (AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value_) && AtomicsBuiltins.AtomicsOperationNode.isDirectBigUint64Array(sharedBigUint64Array_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value_))) {
                        this.toIndex = super.insert(this.toIndex == null ? JSToIndexNode.create() : this.toIndex);
                        this.state_0_ = state_0 |= 0x100;
                        lock.unlock();
                        hasLock = false;
                        bigInt = this.doSharedBigUint64Array(arguments0Value_, arguments1Value, arguments2Value, sharedBigUint64Array_ta__, this.toIndex);
                        return bigInt;
                    }
                }
                this.toIndex = super.insert(this.toIndex == null ? JSToIndexNode.create() : this.toIndex);
                this.generic_notSharedArrayBuffer_ = BranchProfile.create();
                this.state_0_ = state_0 |= 0x200;
                lock.unlock();
                hasLock = false;
                Object object = this.doGeneric(arguments0Value, arguments1Value, arguments2Value, this.toIndex, this.generic_notSharedArrayBuffer_);
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
            Object[] data = new Object[11];
            data[0] = 0;
            int state_0 = this.state_0_;
            Object[] s = new Object[3];
            s[0] = "doSharedInt8Array";
            if ((state_0 & 1) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList<List<Object>>();
                cached.add(Arrays.asList(new Object[0]));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[1] = s;
            s = new Object[3];
            s[0] = "doSharedUint8Array";
            if ((state_0 & 2) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList();
                cached.add(Arrays.asList(new Object[0]));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[2] = s;
            s = new Object[3];
            s[0] = "doSharedInt16Array";
            if ((state_0 & 4) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList();
                cached.add(Arrays.asList(new Object[0]));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[3] = s;
            s = new Object[3];
            s[0] = "doSharedUint16Array";
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
            s[0] = "doSharedInt32Array";
            if ((state_0 & 0x10) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList();
                cached.add(Arrays.asList(new Object[0]));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[5] = s;
            s = new Object[3];
            s[0] = "doSharedUint32Array";
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
            s[0] = "doSharedInt32ArrayObjIdx";
            if ((state_0 & 0x40) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList();
                cached.add(Arrays.asList(this.toIndex));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[7] = s;
            s = new Object[3];
            s[0] = "doSharedBigInt64Array";
            if ((state_0 & 0x80) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList();
                cached.add(Arrays.asList(this.toIndex));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[8] = s;
            s = new Object[3];
            s[0] = "doSharedBigUint64Array";
            if ((state_0 & 0x100) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList();
                cached.add(Arrays.asList(this.toIndex));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[9] = s;
            s = new Object[3];
            s[0] = "doGeneric";
            if ((state_0 & 0x200) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList();
                cached.add(Arrays.asList(this.toIndex, this.generic_notSharedArrayBuffer_));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[10] = s;
            return Introspection.Provider.create(data);
        }

        public static AtomicsBuiltins.AtomicsComputeNode create(JSContext context, JSBuiltin builtin, AtomicsBuiltins.AtomicIntBinaryOperator intOperator, AtomicsBuiltins.AtomicBinaryOperator<BigInt> bigIntOperator, JavaScriptNode[] arguments) {
            return new AtomicsComputeNodeGen(context, builtin, intOperator, bigIntOperator, arguments);
        }
    }

    @GeneratedBy(value=AtomicsBuiltins.AtomicsStoreNode.class)
    public static final class AtomicsStoreNodeGen
    extends AtomicsBuiltins.AtomicsStoreNode
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
        private JSToIndexNode toIndex;

        private AtomicsStoreNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            if ((state_0 & 0xFCCC) == 0 && state_0 != 0) {
                return this.execute_int_int0(state_0, frameValue);
            }
            if ((state_0 & 0xF003) == 0 && state_0 != 0) {
                return this.execute_int1(state_0, frameValue);
            }
            if ((state_0 & 0xEFFF) == 0 && state_0 != 0) {
                return this.execute_int2(state_0, frameValue);
            }
            return this.execute_generic3(state_0, frameValue);
        }

        private Object execute_int_int0(int state_0, VirtualFrame frameValue) {
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
            if ((state_0 & 0x333) != 0 && arguments0Value_ instanceof JSTypedArrayObject) {
                TypedArray sharedUint32Array0_ta__;
                TypedArray sharedInt32Array0_ta__;
                TypedArray sharedUint16Array0_ta__;
                TypedArray sharedInt16Array0_ta__;
                TypedArray sharedUint8Array0_ta__;
                TypedArray sharedInt8Array0_ta__;
                JSTypedArrayObject arguments0Value__ = (JSTypedArrayObject)arguments0Value_;
                if ((state_0 & 1) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__) && AtomicsBuiltins.AtomicsOperationNode.isDirectInt8Array(sharedInt8Array0_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__)) && sharedInt8Array0_ta__.isInBoundsFast(arguments0Value__, arguments1Value_)) {
                    return this.doSharedInt8Array(arguments0Value__, arguments1Value_, arguments2Value_, sharedInt8Array0_ta__);
                }
                if ((state_0 & 2) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__) && AtomicsBuiltins.AtomicsOperationNode.isDirectUint8Array(sharedUint8Array0_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__)) && sharedUint8Array0_ta__.isInBoundsFast(arguments0Value__, arguments1Value_)) {
                    return this.doSharedUint8Array(arguments0Value__, arguments1Value_, arguments2Value_, sharedUint8Array0_ta__);
                }
                if ((state_0 & 0x10) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__) && AtomicsBuiltins.AtomicsOperationNode.isDirectInt16Array(sharedInt16Array0_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__)) && sharedInt16Array0_ta__.isInBoundsFast(arguments0Value__, arguments1Value_)) {
                    return this.doSharedInt16Array(arguments0Value__, arguments1Value_, arguments2Value_, sharedInt16Array0_ta__);
                }
                if ((state_0 & 0x20) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__) && AtomicsBuiltins.AtomicsOperationNode.isDirectUint16Array(sharedUint16Array0_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__)) && sharedUint16Array0_ta__.isInBoundsFast(arguments0Value__, arguments1Value_)) {
                    return this.doSharedUint16Array(arguments0Value__, arguments1Value_, arguments2Value_, sharedUint16Array0_ta__);
                }
                if ((state_0 & 0x100) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__) && AtomicsBuiltins.AtomicsOperationNode.isDirectInt32Array(sharedInt32Array0_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__)) && sharedInt32Array0_ta__.isInBoundsFast(arguments0Value__, arguments1Value_)) {
                    return this.doSharedInt32Array(arguments0Value__, arguments1Value_, arguments2Value_, sharedInt32Array0_ta__);
                }
                if ((state_0 & 0x200) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__) && AtomicsBuiltins.AtomicsOperationNode.isDirectUint32Array(sharedUint32Array0_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__)) && sharedUint32Array0_ta__.isInBoundsFast(arguments0Value__, arguments1Value_)) {
                    return this.doSharedUint32Array(arguments0Value__, arguments1Value_, arguments2Value_, sharedUint32Array0_ta__);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_, arguments2Value_);
        }

        private Object execute_int1(int state_0, VirtualFrame frameValue) {
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
            Object arguments2Value_ = this.arguments2_.execute(frameValue);
            if ((state_0 & 0xFFC) != 0 && arguments0Value_ instanceof JSTypedArrayObject) {
                JSTypedArrayObject arguments0Value__ = (JSTypedArrayObject)arguments0Value_;
                if ((state_0 & 0xC) != 0) {
                    TypedArray sharedUint8Array1_ta__;
                    TypedArray sharedInt8Array1_ta__;
                    if ((state_0 & 4) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__) && AtomicsBuiltins.AtomicsOperationNode.isDirectInt8Array(sharedInt8Array1_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__)) && sharedInt8Array1_ta__.isInBoundsFast(arguments0Value__, arguments1Value_)) {
                        return this.doSharedInt8Array(arguments0Value__, arguments1Value_, arguments2Value_, sharedInt8Array1_ta__);
                    }
                    if ((state_0 & 8) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__) && AtomicsBuiltins.AtomicsOperationNode.isDirectUint8Array(sharedUint8Array1_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__)) && sharedUint8Array1_ta__.isInBoundsFast(arguments0Value__, arguments1Value_)) {
                        return this.doSharedUint8Array(arguments0Value__, arguments1Value_, arguments2Value_, sharedUint8Array1_ta__);
                    }
                }
                if ((state_0 & 0x30) != 0 && arguments2Value_ instanceof Integer) {
                    TypedArray sharedUint16Array0_ta__;
                    TypedArray sharedInt16Array0_ta__;
                    int arguments2Value__ = (Integer)arguments2Value_;
                    if ((state_0 & 0x10) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__) && AtomicsBuiltins.AtomicsOperationNode.isDirectInt16Array(sharedInt16Array0_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__)) && sharedInt16Array0_ta__.isInBoundsFast(arguments0Value__, arguments1Value_)) {
                        return this.doSharedInt16Array(arguments0Value__, arguments1Value_, arguments2Value__, sharedInt16Array0_ta__);
                    }
                    if ((state_0 & 0x20) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__) && AtomicsBuiltins.AtomicsOperationNode.isDirectUint16Array(sharedUint16Array0_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__)) && sharedUint16Array0_ta__.isInBoundsFast(arguments0Value__, arguments1Value_)) {
                        return this.doSharedUint16Array(arguments0Value__, arguments1Value_, arguments2Value__, sharedUint16Array0_ta__);
                    }
                }
                if ((state_0 & 0xC0) != 0) {
                    TypedArray sharedUint16Array1_ta__;
                    TypedArray sharedInt16Array1_ta__;
                    if ((state_0 & 0x40) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__) && AtomicsBuiltins.AtomicsOperationNode.isDirectInt16Array(sharedInt16Array1_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__)) && sharedInt16Array1_ta__.isInBoundsFast(arguments0Value__, arguments1Value_)) {
                        return this.doSharedInt16Array(arguments0Value__, arguments1Value_, arguments2Value_, sharedInt16Array1_ta__);
                    }
                    if ((state_0 & 0x80) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__) && AtomicsBuiltins.AtomicsOperationNode.isDirectUint16Array(sharedUint16Array1_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__)) && sharedUint16Array1_ta__.isInBoundsFast(arguments0Value__, arguments1Value_)) {
                        return this.doSharedUint16Array(arguments0Value__, arguments1Value_, arguments2Value_, sharedUint16Array1_ta__);
                    }
                }
                if ((state_0 & 0x300) != 0 && arguments2Value_ instanceof Integer) {
                    TypedArray sharedUint32Array0_ta__;
                    TypedArray sharedInt32Array0_ta__;
                    int arguments2Value__ = (Integer)arguments2Value_;
                    if ((state_0 & 0x100) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__) && AtomicsBuiltins.AtomicsOperationNode.isDirectInt32Array(sharedInt32Array0_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__)) && sharedInt32Array0_ta__.isInBoundsFast(arguments0Value__, arguments1Value_)) {
                        return this.doSharedInt32Array(arguments0Value__, arguments1Value_, arguments2Value__, sharedInt32Array0_ta__);
                    }
                    if ((state_0 & 0x200) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__) && AtomicsBuiltins.AtomicsOperationNode.isDirectUint32Array(sharedUint32Array0_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__)) && sharedUint32Array0_ta__.isInBoundsFast(arguments0Value__, arguments1Value_)) {
                        return this.doSharedUint32Array(arguments0Value__, arguments1Value_, arguments2Value__, sharedUint32Array0_ta__);
                    }
                }
                if ((state_0 & 0xC00) != 0) {
                    TypedArray sharedUint32Array1_ta__;
                    TypedArray sharedInt32Array1_ta__;
                    if ((state_0 & 0x400) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__) && AtomicsBuiltins.AtomicsOperationNode.isDirectInt32Array(sharedInt32Array1_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__)) && sharedInt32Array1_ta__.isInBoundsFast(arguments0Value__, arguments1Value_)) {
                        return this.doSharedInt32Array(arguments0Value__, arguments1Value_, arguments2Value_, sharedInt32Array1_ta__);
                    }
                    if ((state_0 & 0x800) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__) && AtomicsBuiltins.AtomicsOperationNode.isDirectUint32Array(sharedUint32Array1_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__)) && sharedUint32Array1_ta__.isInBoundsFast(arguments0Value__, arguments1Value_)) {
                        return this.doSharedUint32Array(arguments0Value__, arguments1Value_, arguments2Value_, sharedUint32Array1_ta__);
                    }
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_, arguments2Value_);
        }

        private Object execute_int2(int state_0, VirtualFrame frameValue) {
            TypedArray sharedInt32ArrayObjIdx_ta__;
            JSTypedArrayObject arguments0Value__;
            int arguments2Value_;
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            try {
                arguments2Value_ = this.arguments2_.executeInt(frameValue);
            }
            catch (UnexpectedResultException ex) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.executeAndSpecialize(arguments0Value_, arguments1Value_, ex.getResult());
            }
            assert ((state_0 & 0x1000) != 0);
            if (arguments0Value_ instanceof JSTypedArrayObject && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__ = (JSTypedArrayObject)arguments0Value_) && AtomicsBuiltins.AtomicsOperationNode.isDirectInt32Array(sharedInt32ArrayObjIdx_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__))) {
                return this.doSharedInt32ArrayObjIdx(arguments0Value__, arguments1Value_, arguments2Value_, sharedInt32ArrayObjIdx_ta__, this.toIndex);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_, arguments2Value_);
        }

        private Object execute_generic3(int state_0, VirtualFrame frameValue) {
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            Object arguments2Value_ = this.arguments2_.execute(frameValue);
            if ((state_0 & Short.MAX_VALUE) != 0 && arguments0Value_ instanceof JSTypedArrayObject) {
                JSTypedArrayObject arguments0Value__ = (JSTypedArrayObject)arguments0Value_;
                if ((state_0 & 0xFFF) != 0 && arguments1Value_ instanceof Integer) {
                    int arguments2Value__;
                    int arguments1Value__ = (Integer)arguments1Value_;
                    if ((state_0 & 3) != 0 && arguments2Value_ instanceof Integer) {
                        TypedArray sharedUint8Array0_ta__;
                        TypedArray sharedInt8Array0_ta__;
                        arguments2Value__ = (Integer)arguments2Value_;
                        if ((state_0 & 1) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__) && AtomicsBuiltins.AtomicsOperationNode.isDirectInt8Array(sharedInt8Array0_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__)) && sharedInt8Array0_ta__.isInBoundsFast(arguments0Value__, arguments1Value__)) {
                            return this.doSharedInt8Array(arguments0Value__, arguments1Value__, arguments2Value__, sharedInt8Array0_ta__);
                        }
                        if ((state_0 & 2) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__) && AtomicsBuiltins.AtomicsOperationNode.isDirectUint8Array(sharedUint8Array0_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__)) && sharedUint8Array0_ta__.isInBoundsFast(arguments0Value__, arguments1Value__)) {
                            return this.doSharedUint8Array(arguments0Value__, arguments1Value__, arguments2Value__, sharedUint8Array0_ta__);
                        }
                    }
                    if ((state_0 & 0xC) != 0) {
                        TypedArray sharedUint8Array1_ta__;
                        TypedArray sharedInt8Array1_ta__;
                        if ((state_0 & 4) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__) && AtomicsBuiltins.AtomicsOperationNode.isDirectInt8Array(sharedInt8Array1_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__)) && sharedInt8Array1_ta__.isInBoundsFast(arguments0Value__, arguments1Value__)) {
                            return this.doSharedInt8Array(arguments0Value__, arguments1Value__, arguments2Value_, sharedInt8Array1_ta__);
                        }
                        if ((state_0 & 8) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__) && AtomicsBuiltins.AtomicsOperationNode.isDirectUint8Array(sharedUint8Array1_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__)) && sharedUint8Array1_ta__.isInBoundsFast(arguments0Value__, arguments1Value__)) {
                            return this.doSharedUint8Array(arguments0Value__, arguments1Value__, arguments2Value_, sharedUint8Array1_ta__);
                        }
                    }
                    if ((state_0 & 0x30) != 0 && arguments2Value_ instanceof Integer) {
                        TypedArray sharedUint16Array0_ta__;
                        TypedArray sharedInt16Array0_ta__;
                        arguments2Value__ = (Integer)arguments2Value_;
                        if ((state_0 & 0x10) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__) && AtomicsBuiltins.AtomicsOperationNode.isDirectInt16Array(sharedInt16Array0_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__)) && sharedInt16Array0_ta__.isInBoundsFast(arguments0Value__, arguments1Value__)) {
                            return this.doSharedInt16Array(arguments0Value__, arguments1Value__, arguments2Value__, sharedInt16Array0_ta__);
                        }
                        if ((state_0 & 0x20) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__) && AtomicsBuiltins.AtomicsOperationNode.isDirectUint16Array(sharedUint16Array0_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__)) && sharedUint16Array0_ta__.isInBoundsFast(arguments0Value__, arguments1Value__)) {
                            return this.doSharedUint16Array(arguments0Value__, arguments1Value__, arguments2Value__, sharedUint16Array0_ta__);
                        }
                    }
                    if ((state_0 & 0xC0) != 0) {
                        TypedArray sharedUint16Array1_ta__;
                        TypedArray sharedInt16Array1_ta__;
                        if ((state_0 & 0x40) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__) && AtomicsBuiltins.AtomicsOperationNode.isDirectInt16Array(sharedInt16Array1_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__)) && sharedInt16Array1_ta__.isInBoundsFast(arguments0Value__, arguments1Value__)) {
                            return this.doSharedInt16Array(arguments0Value__, arguments1Value__, arguments2Value_, sharedInt16Array1_ta__);
                        }
                        if ((state_0 & 0x80) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__) && AtomicsBuiltins.AtomicsOperationNode.isDirectUint16Array(sharedUint16Array1_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__)) && sharedUint16Array1_ta__.isInBoundsFast(arguments0Value__, arguments1Value__)) {
                            return this.doSharedUint16Array(arguments0Value__, arguments1Value__, arguments2Value_, sharedUint16Array1_ta__);
                        }
                    }
                    if ((state_0 & 0x300) != 0 && arguments2Value_ instanceof Integer) {
                        TypedArray sharedUint32Array0_ta__;
                        TypedArray sharedInt32Array0_ta__;
                        int arguments2Value__2 = (Integer)arguments2Value_;
                        if ((state_0 & 0x100) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__) && AtomicsBuiltins.AtomicsOperationNode.isDirectInt32Array(sharedInt32Array0_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__)) && sharedInt32Array0_ta__.isInBoundsFast(arguments0Value__, arguments1Value__)) {
                            return this.doSharedInt32Array(arguments0Value__, arguments1Value__, arguments2Value__2, sharedInt32Array0_ta__);
                        }
                        if ((state_0 & 0x200) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__) && AtomicsBuiltins.AtomicsOperationNode.isDirectUint32Array(sharedUint32Array0_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__)) && sharedUint32Array0_ta__.isInBoundsFast(arguments0Value__, arguments1Value__)) {
                            return this.doSharedUint32Array(arguments0Value__, arguments1Value__, arguments2Value__2, sharedUint32Array0_ta__);
                        }
                    }
                    if ((state_0 & 0xC00) != 0) {
                        TypedArray sharedUint32Array1_ta__;
                        TypedArray sharedInt32Array1_ta__;
                        if ((state_0 & 0x400) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__) && AtomicsBuiltins.AtomicsOperationNode.isDirectInt32Array(sharedInt32Array1_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__)) && sharedInt32Array1_ta__.isInBoundsFast(arguments0Value__, arguments1Value__)) {
                            return this.doSharedInt32Array(arguments0Value__, arguments1Value__, arguments2Value_, sharedInt32Array1_ta__);
                        }
                        if ((state_0 & 0x800) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__) && AtomicsBuiltins.AtomicsOperationNode.isDirectUint32Array(sharedUint32Array1_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__)) && sharedUint32Array1_ta__.isInBoundsFast(arguments0Value__, arguments1Value__)) {
                            return this.doSharedUint32Array(arguments0Value__, arguments1Value__, arguments2Value_, sharedUint32Array1_ta__);
                        }
                    }
                }
                if ((state_0 & 0x7000) != 0) {
                    if ((state_0 & 0x1000) != 0 && arguments2Value_ instanceof Integer) {
                        TypedArray sharedInt32ArrayObjIdx_ta__;
                        int arguments2Value__ = (Integer)arguments2Value_;
                        if (AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__) && AtomicsBuiltins.AtomicsOperationNode.isDirectInt32Array(sharedInt32ArrayObjIdx_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__))) {
                            return this.doSharedInt32ArrayObjIdx(arguments0Value__, arguments1Value_, arguments2Value__, sharedInt32ArrayObjIdx_ta__, this.toIndex);
                        }
                    }
                    if ((state_0 & 0x6000) != 0) {
                        TypedArray sharedBigUint64Array_ta__;
                        TypedArray sharedBigInt64Array_ta__;
                        if ((state_0 & 0x2000) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__) && AtomicsBuiltins.AtomicsOperationNode.isDirectBigInt64Array(sharedBigInt64Array_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__))) {
                            return this.doSharedBigInt64Array(arguments0Value__, arguments1Value_, arguments2Value_, sharedBigInt64Array_ta__, this.toIndex);
                        }
                        if ((state_0 & 0x4000) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__) && AtomicsBuiltins.AtomicsOperationNode.isDirectBigUint64Array(sharedBigUint64Array_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__))) {
                            return this.doSharedBigUint64Array(arguments0Value__, arguments1Value_, arguments2Value_, sharedBigUint64Array_ta__, this.toIndex);
                        }
                    }
                }
            }
            if ((state_0 & 0x8000) != 0) {
                return this.doGeneric(arguments0Value_, arguments1Value_, arguments2Value_, this.toIndex);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_, arguments2Value_);
        }

        @Override
        public int executeInt(VirtualFrame frameValue) throws UnexpectedResultException {
            int arguments2Value_;
            int arguments1Value_;
            int state_0 = this.state_0_;
            if ((state_0 & 0xFCFC) != 0) {
                return JSTypesGen.expectInteger(this.execute(frameValue));
            }
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            try {
                arguments1Value_ = this.arguments1_.executeInt(frameValue);
            }
            catch (UnexpectedResultException ex) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                Object arguments2Value = this.arguments2_.execute(frameValue);
                return JSTypesGen.expectInteger(this.executeAndSpecialize(arguments0Value_, ex.getResult(), arguments2Value));
            }
            try {
                arguments2Value_ = this.arguments2_.executeInt(frameValue);
            }
            catch (UnexpectedResultException ex) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return JSTypesGen.expectInteger(this.executeAndSpecialize(arguments0Value_, arguments1Value_, ex.getResult()));
            }
            if ((state_0 & 0x303) != 0 && arguments0Value_ instanceof JSTypedArrayObject) {
                TypedArray sharedUint32Array0_ta__;
                TypedArray sharedInt32Array0_ta__;
                TypedArray sharedUint8Array0_ta__;
                TypedArray sharedInt8Array0_ta__;
                JSTypedArrayObject arguments0Value__ = (JSTypedArrayObject)arguments0Value_;
                if ((state_0 & 1) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__) && AtomicsBuiltins.AtomicsOperationNode.isDirectInt8Array(sharedInt8Array0_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__)) && sharedInt8Array0_ta__.isInBoundsFast(arguments0Value__, arguments1Value_)) {
                    return this.doSharedInt8Array(arguments0Value__, arguments1Value_, arguments2Value_, sharedInt8Array0_ta__);
                }
                if ((state_0 & 2) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__) && AtomicsBuiltins.AtomicsOperationNode.isDirectUint8Array(sharedUint8Array0_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__)) && sharedUint8Array0_ta__.isInBoundsFast(arguments0Value__, arguments1Value_)) {
                    return this.doSharedUint8Array(arguments0Value__, arguments1Value_, arguments2Value_, sharedUint8Array0_ta__);
                }
                if ((state_0 & 0x100) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__) && AtomicsBuiltins.AtomicsOperationNode.isDirectInt32Array(sharedInt32Array0_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__)) && sharedInt32Array0_ta__.isInBoundsFast(arguments0Value__, arguments1Value_)) {
                    return this.doSharedInt32Array(arguments0Value__, arguments1Value_, arguments2Value_, sharedInt32Array0_ta__);
                }
                if ((state_0 & 0x200) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__) && AtomicsBuiltins.AtomicsOperationNode.isDirectUint32Array(sharedUint32Array0_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__)) && sharedUint32Array0_ta__.isInBoundsFast(arguments0Value__, arguments1Value_)) {
                    return this.doSharedUint32Array(arguments0Value__, arguments1Value_, arguments2Value_, sharedUint32Array0_ta__);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return JSTypesGen.expectInteger(this.executeAndSpecialize(arguments0Value_, arguments1Value_, arguments2Value_));
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            int state_0 = this.state_0_;
            try {
                if ((state_0 & 0xFCFC) == 0 && state_0 != 0) {
                    this.executeInt(frameValue);
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
        private Object executeAndSpecialize(Object arguments0Value, Object arguments1Value, Object arguments2Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                if (arguments0Value instanceof JSTypedArrayObject) {
                    Object object;
                    JSTypedArrayObject arguments0Value_ = (JSTypedArrayObject)arguments0Value;
                    if (arguments1Value instanceof Integer) {
                        Object sharedUint16Array0_ta__;
                        Object sharedUint8Array0_ta__;
                        int arguments1Value_ = (Integer)arguments1Value;
                        if (arguments2Value instanceof Integer) {
                            int arguments2Value_ = (Integer)arguments2Value;
                            TypedArray sharedInt8Array0_ta__ = null;
                            if (AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value_) && AtomicsBuiltins.AtomicsOperationNode.isDirectInt8Array(sharedInt8Array0_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value_)) && sharedInt8Array0_ta__.isInBoundsFast(arguments0Value_, arguments1Value_)) {
                                this.state_0_ = state_0 |= 1;
                                lock.unlock();
                                hasLock = false;
                                Integer n = this.doSharedInt8Array(arguments0Value_, arguments1Value_, arguments2Value_, sharedInt8Array0_ta__);
                                return n;
                            }
                            sharedUint8Array0_ta__ = null;
                            if (AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value_) && AtomicsBuiltins.AtomicsOperationNode.isDirectUint8Array((TypedArray)(sharedUint8Array0_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value_))) && ((ScriptArray)sharedUint8Array0_ta__).isInBoundsFast(arguments0Value_, arguments1Value_)) {
                                this.state_0_ = state_0 |= 2;
                                lock.unlock();
                                hasLock = false;
                                Integer n = this.doSharedUint8Array(arguments0Value_, arguments1Value_, arguments2Value_, (TypedArray)sharedUint8Array0_ta__);
                                return n;
                            }
                        }
                        TypedArray sharedInt8Array1_ta__ = null;
                        if (AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value_) && AtomicsBuiltins.AtomicsOperationNode.isDirectInt8Array(sharedInt8Array1_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value_)) && sharedInt8Array1_ta__.isInBoundsFast(arguments0Value_, arguments1Value_)) {
                            this.state_0_ = state_0 |= 4;
                            lock.unlock();
                            hasLock = false;
                            sharedUint8Array0_ta__ = this.doSharedInt8Array(arguments0Value_, arguments1Value_, arguments2Value, sharedInt8Array1_ta__);
                            return sharedUint8Array0_ta__;
                        }
                        TypedArray sharedUint8Array1_ta__ = null;
                        if (AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value_) && AtomicsBuiltins.AtomicsOperationNode.isDirectUint8Array(sharedUint8Array1_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value_)) && sharedUint8Array1_ta__.isInBoundsFast(arguments0Value_, arguments1Value_)) {
                            this.state_0_ = state_0 |= 8;
                            lock.unlock();
                            hasLock = false;
                            sharedUint8Array0_ta__ = this.doSharedUint8Array(arguments0Value_, arguments1Value_, arguments2Value, sharedUint8Array1_ta__);
                            return sharedUint8Array0_ta__;
                        }
                        if (arguments2Value instanceof Integer) {
                            int arguments2Value_ = (Integer)arguments2Value;
                            TypedArray sharedInt16Array0_ta__ = null;
                            if (AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value_) && AtomicsBuiltins.AtomicsOperationNode.isDirectInt16Array(sharedInt16Array0_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value_)) && sharedInt16Array0_ta__.isInBoundsFast(arguments0Value_, arguments1Value_)) {
                                this.state_0_ = state_0 |= 0x10;
                                lock.unlock();
                                hasLock = false;
                                Object object2 = this.doSharedInt16Array(arguments0Value_, arguments1Value_, arguments2Value_, sharedInt16Array0_ta__);
                                return object2;
                            }
                            sharedUint16Array0_ta__ = null;
                            if (AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value_) && AtomicsBuiltins.AtomicsOperationNode.isDirectUint16Array((TypedArray)(sharedUint16Array0_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value_))) && ((ScriptArray)sharedUint16Array0_ta__).isInBoundsFast(arguments0Value_, arguments1Value_)) {
                                this.state_0_ = state_0 |= 0x20;
                                lock.unlock();
                                hasLock = false;
                                Object object3 = this.doSharedUint16Array(arguments0Value_, arguments1Value_, arguments2Value_, (TypedArray)sharedUint16Array0_ta__);
                                return object3;
                            }
                        }
                        TypedArray sharedInt16Array1_ta__ = null;
                        if (AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value_) && AtomicsBuiltins.AtomicsOperationNode.isDirectInt16Array(sharedInt16Array1_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value_)) && sharedInt16Array1_ta__.isInBoundsFast(arguments0Value_, arguments1Value_)) {
                            this.state_0_ = state_0 |= 0x40;
                            lock.unlock();
                            hasLock = false;
                            sharedUint16Array0_ta__ = this.doSharedInt16Array(arguments0Value_, arguments1Value_, arguments2Value, sharedInt16Array1_ta__);
                            return sharedUint16Array0_ta__;
                        }
                        TypedArray sharedUint16Array1_ta__ = null;
                        if (AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value_) && AtomicsBuiltins.AtomicsOperationNode.isDirectUint16Array(sharedUint16Array1_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value_)) && sharedUint16Array1_ta__.isInBoundsFast(arguments0Value_, arguments1Value_)) {
                            this.state_0_ = state_0 |= 0x80;
                            lock.unlock();
                            hasLock = false;
                            sharedUint16Array0_ta__ = this.doSharedUint16Array(arguments0Value_, arguments1Value_, arguments2Value, sharedUint16Array1_ta__);
                            return sharedUint16Array0_ta__;
                        }
                        if (arguments2Value instanceof Integer) {
                            int arguments2Value_ = (Integer)arguments2Value;
                            TypedArray sharedInt32Array0_ta__ = null;
                            if (AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value_) && AtomicsBuiltins.AtomicsOperationNode.isDirectInt32Array(sharedInt32Array0_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value_)) && sharedInt32Array0_ta__.isInBoundsFast(arguments0Value_, arguments1Value_)) {
                                this.state_0_ = state_0 |= 0x100;
                                lock.unlock();
                                hasLock = false;
                                Integer n = this.doSharedInt32Array(arguments0Value_, arguments1Value_, arguments2Value_, sharedInt32Array0_ta__);
                                return n;
                            }
                            TypedArray sharedUint32Array0_ta__ = null;
                            if (AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value_) && AtomicsBuiltins.AtomicsOperationNode.isDirectUint32Array(sharedUint32Array0_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value_)) && sharedUint32Array0_ta__.isInBoundsFast(arguments0Value_, arguments1Value_)) {
                                this.state_0_ = state_0 |= 0x200;
                                lock.unlock();
                                hasLock = false;
                                Integer n = this.doSharedUint32Array(arguments0Value_, arguments1Value_, arguments2Value_, sharedUint32Array0_ta__);
                                return n;
                            }
                        }
                        TypedArray sharedInt32Array1_ta__ = null;
                        if (AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value_) && AtomicsBuiltins.AtomicsOperationNode.isDirectInt32Array(sharedInt32Array1_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value_)) && sharedInt32Array1_ta__.isInBoundsFast(arguments0Value_, arguments1Value_)) {
                            this.state_0_ = state_0 |= 0x400;
                            lock.unlock();
                            hasLock = false;
                            object = this.doSharedInt32Array(arguments0Value_, arguments1Value_, arguments2Value, sharedInt32Array1_ta__);
                            return object;
                        }
                        TypedArray sharedUint32Array1_ta__ = null;
                        if (AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value_) && AtomicsBuiltins.AtomicsOperationNode.isDirectUint32Array(sharedUint32Array1_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value_)) && sharedUint32Array1_ta__.isInBoundsFast(arguments0Value_, arguments1Value_)) {
                            this.state_0_ = state_0 |= 0x800;
                            lock.unlock();
                            hasLock = false;
                            object = this.doSharedUint32Array(arguments0Value_, arguments1Value_, arguments2Value, sharedUint32Array1_ta__);
                            return object;
                        }
                    }
                    TypedArray sharedInt32ArrayObjIdx_ta__ = null;
                    if (arguments2Value instanceof Integer) {
                        int arguments2Value_ = (Integer)arguments2Value;
                        if (AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value_) && AtomicsBuiltins.AtomicsOperationNode.isDirectInt32Array(sharedInt32ArrayObjIdx_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value_))) {
                            this.toIndex = super.insert(this.toIndex == null ? JSToIndexNode.create() : this.toIndex);
                            this.state_0_ = state_0 |= 0x1000;
                            lock.unlock();
                            hasLock = false;
                            object = this.doSharedInt32ArrayObjIdx(arguments0Value_, arguments1Value, arguments2Value_, sharedInt32ArrayObjIdx_ta__, this.toIndex);
                            return object;
                        }
                    }
                    TypedArray sharedBigInt64Array_ta__ = null;
                    if (AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value_) && AtomicsBuiltins.AtomicsOperationNode.isDirectBigInt64Array(sharedBigInt64Array_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value_))) {
                        this.toIndex = super.insert(this.toIndex == null ? JSToIndexNode.create() : this.toIndex);
                        this.state_0_ = state_0 |= 0x2000;
                        lock.unlock();
                        hasLock = false;
                        Object object4 = this.doSharedBigInt64Array(arguments0Value_, arguments1Value, arguments2Value, sharedBigInt64Array_ta__, this.toIndex);
                        return object4;
                    }
                    TypedArray sharedBigUint64Array_ta__ = null;
                    if (AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value_) && AtomicsBuiltins.AtomicsOperationNode.isDirectBigUint64Array(sharedBigUint64Array_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value_))) {
                        this.toIndex = super.insert(this.toIndex == null ? JSToIndexNode.create() : this.toIndex);
                        this.state_0_ = state_0 |= 0x4000;
                        lock.unlock();
                        hasLock = false;
                        Object object5 = this.doSharedBigUint64Array(arguments0Value_, arguments1Value, arguments2Value, sharedBigUint64Array_ta__, this.toIndex);
                        return object5;
                    }
                }
                this.toIndex = super.insert(this.toIndex == null ? JSToIndexNode.create() : this.toIndex);
                this.state_0_ = state_0 |= 0x8000;
                lock.unlock();
                hasLock = false;
                Object object = this.doGeneric(arguments0Value, arguments1Value, arguments2Value, this.toIndex);
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
            Object[] data = new Object[17];
            data[0] = 0;
            int state_0 = this.state_0_;
            Object[] s = new Object[3];
            s[0] = "doSharedInt8Array";
            if ((state_0 & 1) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList<List<Object>>();
                cached.add(Arrays.asList(new Object[0]));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[1] = s;
            s = new Object[3];
            s[0] = "doSharedUint8Array";
            if ((state_0 & 2) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList();
                cached.add(Arrays.asList(new Object[0]));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[2] = s;
            s = new Object[3];
            s[0] = "doSharedInt8Array";
            if ((state_0 & 4) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList();
                cached.add(Arrays.asList(new Object[0]));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[3] = s;
            s = new Object[3];
            s[0] = "doSharedUint8Array";
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
            s[0] = "doSharedInt16Array";
            if ((state_0 & 0x10) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList();
                cached.add(Arrays.asList(new Object[0]));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[5] = s;
            s = new Object[3];
            s[0] = "doSharedUint16Array";
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
            s[0] = "doSharedInt16Array";
            if ((state_0 & 0x40) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList();
                cached.add(Arrays.asList(new Object[0]));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[7] = s;
            s = new Object[3];
            s[0] = "doSharedUint16Array";
            if ((state_0 & 0x80) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList();
                cached.add(Arrays.asList(new Object[0]));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[8] = s;
            s = new Object[3];
            s[0] = "doSharedInt32Array";
            if ((state_0 & 0x100) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList();
                cached.add(Arrays.asList(new Object[0]));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[9] = s;
            s = new Object[3];
            s[0] = "doSharedUint32Array";
            if ((state_0 & 0x200) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList();
                cached.add(Arrays.asList(new Object[0]));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[10] = s;
            s = new Object[3];
            s[0] = "doSharedInt32Array";
            if ((state_0 & 0x400) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList();
                cached.add(Arrays.asList(new Object[0]));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[11] = s;
            s = new Object[3];
            s[0] = "doSharedUint32Array";
            if ((state_0 & 0x800) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList();
                cached.add(Arrays.asList(new Object[0]));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[12] = s;
            s = new Object[3];
            s[0] = "doSharedInt32ArrayObjIdx";
            if ((state_0 & 0x1000) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList();
                cached.add(Arrays.asList(this.toIndex));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[13] = s;
            s = new Object[3];
            s[0] = "doSharedBigInt64Array";
            if ((state_0 & 0x2000) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList();
                cached.add(Arrays.asList(this.toIndex));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[14] = s;
            s = new Object[3];
            s[0] = "doSharedBigUint64Array";
            if ((state_0 & 0x4000) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList();
                cached.add(Arrays.asList(this.toIndex));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[15] = s;
            s = new Object[3];
            s[0] = "doGeneric";
            if ((state_0 & 0x8000) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList();
                cached.add(Arrays.asList(this.toIndex));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[16] = s;
            return Introspection.Provider.create(data);
        }

        public static AtomicsBuiltins.AtomicsStoreNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new AtomicsStoreNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=AtomicsBuiltins.AtomicsLoadNode.class)
    public static final class AtomicsLoadNodeGen
    extends AtomicsBuiltins.AtomicsLoadNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private JSToIndexNode toIndex;

        private AtomicsLoadNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            super(context, builtin);
            this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
            this.arguments1_ = arguments != null && 1 < arguments.length ? arguments[1] : null;
        }

        @Override
        public JavaScriptNode[] getArguments() {
            return new JavaScriptNode[]{this.arguments0_, this.arguments1_};
        }

        @Override
        public Object executeWithBufferAndIndex(VirtualFrame frameValue, Object arguments0Value, Object arguments1Value) {
            int state_0 = this.state_0_;
            if ((state_0 & 0x1FF) != 0 && arguments0Value instanceof JSTypedArrayObject) {
                TypedArray int32ArrayObjObjIdx_ta__;
                JSTypedArrayObject arguments0Value_ = (JSTypedArrayObject)arguments0Value;
                if ((state_0 & 0xFF) != 0 && arguments1Value instanceof Integer) {
                    TypedArray bigUint64ArrayObj_ta__;
                    TypedArray bigInt64ArrayObj_ta__;
                    TypedArray uint32ArrayObj_ta__;
                    TypedArray int32ArrayObj_ta__;
                    TypedArray uint16ArrayObj_ta__;
                    TypedArray int16ArrayObj_ta__;
                    TypedArray uint8ArrayObj_ta__;
                    TypedArray int8ArrayObj_ta__;
                    int arguments1Value_ = (Integer)arguments1Value;
                    if ((state_0 & 1) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value_) && AtomicsBuiltins.AtomicsOperationNode.isDirectInt8Array(int8ArrayObj_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value_)) && int8ArrayObj_ta__.isInBoundsFast(arguments0Value_, arguments1Value_)) {
                        return this.doInt8ArrayObj(arguments0Value_, arguments1Value_, int8ArrayObj_ta__);
                    }
                    if ((state_0 & 2) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value_) && AtomicsBuiltins.AtomicsOperationNode.isDirectUint8Array(uint8ArrayObj_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value_)) && uint8ArrayObj_ta__.isInBoundsFast(arguments0Value_, arguments1Value_)) {
                        return this.doUint8ArrayObj(arguments0Value_, arguments1Value_, uint8ArrayObj_ta__);
                    }
                    if ((state_0 & 4) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value_) && AtomicsBuiltins.AtomicsOperationNode.isDirectInt16Array(int16ArrayObj_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value_)) && int16ArrayObj_ta__.isInBoundsFast(arguments0Value_, arguments1Value_)) {
                        return this.doInt16ArrayObj(arguments0Value_, arguments1Value_, int16ArrayObj_ta__);
                    }
                    if ((state_0 & 8) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value_) && AtomicsBuiltins.AtomicsOperationNode.isDirectUint16Array(uint16ArrayObj_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value_)) && uint16ArrayObj_ta__.isInBoundsFast(arguments0Value_, arguments1Value_)) {
                        return this.doUint16ArrayObj(arguments0Value_, arguments1Value_, uint16ArrayObj_ta__);
                    }
                    if ((state_0 & 0x10) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value_) && AtomicsBuiltins.AtomicsOperationNode.isDirectInt32Array(int32ArrayObj_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value_)) && int32ArrayObj_ta__.isInBoundsFast(arguments0Value_, arguments1Value_)) {
                        return this.doInt32ArrayObj(arguments0Value_, arguments1Value_, int32ArrayObj_ta__);
                    }
                    if ((state_0 & 0x20) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value_) && AtomicsBuiltins.AtomicsOperationNode.isDirectUint32Array(uint32ArrayObj_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value_)) && uint32ArrayObj_ta__.isInBoundsFast(arguments0Value_, arguments1Value_)) {
                        return this.doUint32ArrayObj(arguments0Value_, arguments1Value_, uint32ArrayObj_ta__);
                    }
                    if ((state_0 & 0x40) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value_) && AtomicsBuiltins.AtomicsOperationNode.isDirectBigInt64Array(bigInt64ArrayObj_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value_)) && bigInt64ArrayObj_ta__.isInBoundsFast(arguments0Value_, arguments1Value_)) {
                        return this.doBigInt64ArrayObj(arguments0Value_, arguments1Value_, bigInt64ArrayObj_ta__);
                    }
                    if ((state_0 & 0x80) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value_) && AtomicsBuiltins.AtomicsOperationNode.isDirectBigUint64Array(bigUint64ArrayObj_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value_)) && bigUint64ArrayObj_ta__.isInBoundsFast(arguments0Value_, arguments1Value_)) {
                        return this.doBigUint64ArrayObj(arguments0Value_, arguments1Value_, bigUint64ArrayObj_ta__);
                    }
                }
                if ((state_0 & 0x100) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value_) && AtomicsBuiltins.AtomicsOperationNode.isDirectInt32Array(int32ArrayObjObjIdx_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value_))) {
                    return this.doInt32ArrayObjObjIdx(arguments0Value_, arguments1Value, int32ArrayObjObjIdx_ta__, this.toIndex);
                }
            }
            if ((state_0 & 0x200) != 0) {
                return this.doGeneric(arguments0Value, arguments1Value, this.toIndex);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value, arguments1Value);
        }

        @Override
        public Object execute(VirtualFrame frameValue) {
            int state_0 = this.state_0_;
            if ((state_0 & 0x300) == 0 && state_0 != 0) {
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
            if ((state_0 & 0xFF) != 0 && arguments0Value_ instanceof JSTypedArrayObject) {
                TypedArray bigUint64ArrayObj_ta__;
                TypedArray bigInt64ArrayObj_ta__;
                TypedArray uint32ArrayObj_ta__;
                TypedArray int32ArrayObj_ta__;
                TypedArray uint16ArrayObj_ta__;
                TypedArray int16ArrayObj_ta__;
                TypedArray uint8ArrayObj_ta__;
                TypedArray int8ArrayObj_ta__;
                JSTypedArrayObject arguments0Value__ = (JSTypedArrayObject)arguments0Value_;
                if ((state_0 & 1) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__) && AtomicsBuiltins.AtomicsOperationNode.isDirectInt8Array(int8ArrayObj_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__)) && int8ArrayObj_ta__.isInBoundsFast(arguments0Value__, arguments1Value_)) {
                    return this.doInt8ArrayObj(arguments0Value__, arguments1Value_, int8ArrayObj_ta__);
                }
                if ((state_0 & 2) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__) && AtomicsBuiltins.AtomicsOperationNode.isDirectUint8Array(uint8ArrayObj_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__)) && uint8ArrayObj_ta__.isInBoundsFast(arguments0Value__, arguments1Value_)) {
                    return this.doUint8ArrayObj(arguments0Value__, arguments1Value_, uint8ArrayObj_ta__);
                }
                if ((state_0 & 4) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__) && AtomicsBuiltins.AtomicsOperationNode.isDirectInt16Array(int16ArrayObj_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__)) && int16ArrayObj_ta__.isInBoundsFast(arguments0Value__, arguments1Value_)) {
                    return this.doInt16ArrayObj(arguments0Value__, arguments1Value_, int16ArrayObj_ta__);
                }
                if ((state_0 & 8) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__) && AtomicsBuiltins.AtomicsOperationNode.isDirectUint16Array(uint16ArrayObj_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__)) && uint16ArrayObj_ta__.isInBoundsFast(arguments0Value__, arguments1Value_)) {
                    return this.doUint16ArrayObj(arguments0Value__, arguments1Value_, uint16ArrayObj_ta__);
                }
                if ((state_0 & 0x10) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__) && AtomicsBuiltins.AtomicsOperationNode.isDirectInt32Array(int32ArrayObj_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__)) && int32ArrayObj_ta__.isInBoundsFast(arguments0Value__, arguments1Value_)) {
                    return this.doInt32ArrayObj(arguments0Value__, arguments1Value_, int32ArrayObj_ta__);
                }
                if ((state_0 & 0x20) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__) && AtomicsBuiltins.AtomicsOperationNode.isDirectUint32Array(uint32ArrayObj_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__)) && uint32ArrayObj_ta__.isInBoundsFast(arguments0Value__, arguments1Value_)) {
                    return this.doUint32ArrayObj(arguments0Value__, arguments1Value_, uint32ArrayObj_ta__);
                }
                if ((state_0 & 0x40) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__) && AtomicsBuiltins.AtomicsOperationNode.isDirectBigInt64Array(bigInt64ArrayObj_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__)) && bigInt64ArrayObj_ta__.isInBoundsFast(arguments0Value__, arguments1Value_)) {
                    return this.doBigInt64ArrayObj(arguments0Value__, arguments1Value_, bigInt64ArrayObj_ta__);
                }
                if ((state_0 & 0x80) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__) && AtomicsBuiltins.AtomicsOperationNode.isDirectBigUint64Array(bigUint64ArrayObj_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__)) && bigUint64ArrayObj_ta__.isInBoundsFast(arguments0Value__, arguments1Value_)) {
                    return this.doBigUint64ArrayObj(arguments0Value__, arguments1Value_, bigUint64ArrayObj_ta__);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
        }

        private Object execute_generic1(int state_0, VirtualFrame frameValue) {
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            if ((state_0 & 0x1FF) != 0 && arguments0Value_ instanceof JSTypedArrayObject) {
                TypedArray int32ArrayObjObjIdx_ta__;
                JSTypedArrayObject arguments0Value__ = (JSTypedArrayObject)arguments0Value_;
                if ((state_0 & 0xFF) != 0 && arguments1Value_ instanceof Integer) {
                    TypedArray bigUint64ArrayObj_ta__;
                    TypedArray bigInt64ArrayObj_ta__;
                    TypedArray uint32ArrayObj_ta__;
                    TypedArray int32ArrayObj_ta__;
                    TypedArray uint16ArrayObj_ta__;
                    TypedArray int16ArrayObj_ta__;
                    TypedArray uint8ArrayObj_ta__;
                    TypedArray int8ArrayObj_ta__;
                    int arguments1Value__ = (Integer)arguments1Value_;
                    if ((state_0 & 1) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__) && AtomicsBuiltins.AtomicsOperationNode.isDirectInt8Array(int8ArrayObj_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__)) && int8ArrayObj_ta__.isInBoundsFast(arguments0Value__, arguments1Value__)) {
                        return this.doInt8ArrayObj(arguments0Value__, arguments1Value__, int8ArrayObj_ta__);
                    }
                    if ((state_0 & 2) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__) && AtomicsBuiltins.AtomicsOperationNode.isDirectUint8Array(uint8ArrayObj_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__)) && uint8ArrayObj_ta__.isInBoundsFast(arguments0Value__, arguments1Value__)) {
                        return this.doUint8ArrayObj(arguments0Value__, arguments1Value__, uint8ArrayObj_ta__);
                    }
                    if ((state_0 & 4) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__) && AtomicsBuiltins.AtomicsOperationNode.isDirectInt16Array(int16ArrayObj_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__)) && int16ArrayObj_ta__.isInBoundsFast(arguments0Value__, arguments1Value__)) {
                        return this.doInt16ArrayObj(arguments0Value__, arguments1Value__, int16ArrayObj_ta__);
                    }
                    if ((state_0 & 8) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__) && AtomicsBuiltins.AtomicsOperationNode.isDirectUint16Array(uint16ArrayObj_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__)) && uint16ArrayObj_ta__.isInBoundsFast(arguments0Value__, arguments1Value__)) {
                        return this.doUint16ArrayObj(arguments0Value__, arguments1Value__, uint16ArrayObj_ta__);
                    }
                    if ((state_0 & 0x10) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__) && AtomicsBuiltins.AtomicsOperationNode.isDirectInt32Array(int32ArrayObj_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__)) && int32ArrayObj_ta__.isInBoundsFast(arguments0Value__, arguments1Value__)) {
                        return this.doInt32ArrayObj(arguments0Value__, arguments1Value__, int32ArrayObj_ta__);
                    }
                    if ((state_0 & 0x20) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__) && AtomicsBuiltins.AtomicsOperationNode.isDirectUint32Array(uint32ArrayObj_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__)) && uint32ArrayObj_ta__.isInBoundsFast(arguments0Value__, arguments1Value__)) {
                        return this.doUint32ArrayObj(arguments0Value__, arguments1Value__, uint32ArrayObj_ta__);
                    }
                    if ((state_0 & 0x40) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__) && AtomicsBuiltins.AtomicsOperationNode.isDirectBigInt64Array(bigInt64ArrayObj_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__)) && bigInt64ArrayObj_ta__.isInBoundsFast(arguments0Value__, arguments1Value__)) {
                        return this.doBigInt64ArrayObj(arguments0Value__, arguments1Value__, bigInt64ArrayObj_ta__);
                    }
                    if ((state_0 & 0x80) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__) && AtomicsBuiltins.AtomicsOperationNode.isDirectBigUint64Array(bigUint64ArrayObj_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__)) && bigUint64ArrayObj_ta__.isInBoundsFast(arguments0Value__, arguments1Value__)) {
                        return this.doBigUint64ArrayObj(arguments0Value__, arguments1Value__, bigUint64ArrayObj_ta__);
                    }
                }
                if ((state_0 & 0x100) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__) && AtomicsBuiltins.AtomicsOperationNode.isDirectInt32Array(int32ArrayObjObjIdx_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__))) {
                    return this.doInt32ArrayObjObjIdx(arguments0Value__, arguments1Value_, int32ArrayObjObjIdx_ta__, this.toIndex);
                }
            }
            if ((state_0 & 0x200) != 0) {
                return this.doGeneric(arguments0Value_, arguments1Value_, this.toIndex);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
        }

        @Override
        public int executeInt(VirtualFrame frameValue) throws UnexpectedResultException {
            int state_0 = this.state_0_;
            if ((state_0 & 0x200) != 0) {
                return JSTypesGen.expectInteger(this.execute(frameValue));
            }
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            if ((state_0 & 0x100) == 0 && (state_0 & 0x11F) != 0) {
                return this.executeInt_int2(state_0, frameValue, arguments0Value_);
            }
            return this.executeInt_generic3(state_0, frameValue, arguments0Value_);
        }

        private int executeInt_int2(int state_0, VirtualFrame frameValue, Object arguments0Value_) throws UnexpectedResultException {
            int arguments1Value_;
            try {
                arguments1Value_ = this.arguments1_.executeInt(frameValue);
            }
            catch (UnexpectedResultException ex) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return JSTypesGen.expectInteger(this.executeAndSpecialize(arguments0Value_, ex.getResult()));
            }
            if ((state_0 & 0x1F) != 0 && arguments0Value_ instanceof JSTypedArrayObject) {
                TypedArray int32ArrayObj_ta__;
                TypedArray uint16ArrayObj_ta__;
                TypedArray int16ArrayObj_ta__;
                TypedArray uint8ArrayObj_ta__;
                TypedArray int8ArrayObj_ta__;
                JSTypedArrayObject arguments0Value__ = (JSTypedArrayObject)arguments0Value_;
                if ((state_0 & 1) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__) && AtomicsBuiltins.AtomicsOperationNode.isDirectInt8Array(int8ArrayObj_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__)) && int8ArrayObj_ta__.isInBoundsFast(arguments0Value__, arguments1Value_)) {
                    return this.doInt8ArrayObj(arguments0Value__, arguments1Value_, int8ArrayObj_ta__);
                }
                if ((state_0 & 2) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__) && AtomicsBuiltins.AtomicsOperationNode.isDirectUint8Array(uint8ArrayObj_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__)) && uint8ArrayObj_ta__.isInBoundsFast(arguments0Value__, arguments1Value_)) {
                    return this.doUint8ArrayObj(arguments0Value__, arguments1Value_, uint8ArrayObj_ta__);
                }
                if ((state_0 & 4) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__) && AtomicsBuiltins.AtomicsOperationNode.isDirectInt16Array(int16ArrayObj_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__)) && int16ArrayObj_ta__.isInBoundsFast(arguments0Value__, arguments1Value_)) {
                    return this.doInt16ArrayObj(arguments0Value__, arguments1Value_, int16ArrayObj_ta__);
                }
                if ((state_0 & 8) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__) && AtomicsBuiltins.AtomicsOperationNode.isDirectUint16Array(uint16ArrayObj_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__)) && uint16ArrayObj_ta__.isInBoundsFast(arguments0Value__, arguments1Value_)) {
                    return this.doUint16ArrayObj(arguments0Value__, arguments1Value_, uint16ArrayObj_ta__);
                }
                if ((state_0 & 0x10) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__) && AtomicsBuiltins.AtomicsOperationNode.isDirectInt32Array(int32ArrayObj_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__)) && int32ArrayObj_ta__.isInBoundsFast(arguments0Value__, arguments1Value_)) {
                    return this.doInt32ArrayObj(arguments0Value__, arguments1Value_, int32ArrayObj_ta__);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return JSTypesGen.expectInteger(this.executeAndSpecialize(arguments0Value_, arguments1Value_));
        }

        private int executeInt_generic3(int state_0, VirtualFrame frameValue, Object arguments0Value_) throws UnexpectedResultException {
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            if ((state_0 & 0x11F) != 0 && arguments0Value_ instanceof JSTypedArrayObject) {
                TypedArray int32ArrayObjObjIdx_ta__;
                JSTypedArrayObject arguments0Value__ = (JSTypedArrayObject)arguments0Value_;
                if ((state_0 & 0x1F) != 0 && arguments1Value_ instanceof Integer) {
                    TypedArray int32ArrayObj_ta__;
                    TypedArray uint16ArrayObj_ta__;
                    TypedArray int16ArrayObj_ta__;
                    TypedArray uint8ArrayObj_ta__;
                    TypedArray int8ArrayObj_ta__;
                    int arguments1Value__ = (Integer)arguments1Value_;
                    if ((state_0 & 1) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__) && AtomicsBuiltins.AtomicsOperationNode.isDirectInt8Array(int8ArrayObj_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__)) && int8ArrayObj_ta__.isInBoundsFast(arguments0Value__, arguments1Value__)) {
                        return this.doInt8ArrayObj(arguments0Value__, arguments1Value__, int8ArrayObj_ta__);
                    }
                    if ((state_0 & 2) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__) && AtomicsBuiltins.AtomicsOperationNode.isDirectUint8Array(uint8ArrayObj_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__)) && uint8ArrayObj_ta__.isInBoundsFast(arguments0Value__, arguments1Value__)) {
                        return this.doUint8ArrayObj(arguments0Value__, arguments1Value__, uint8ArrayObj_ta__);
                    }
                    if ((state_0 & 4) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__) && AtomicsBuiltins.AtomicsOperationNode.isDirectInt16Array(int16ArrayObj_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__)) && int16ArrayObj_ta__.isInBoundsFast(arguments0Value__, arguments1Value__)) {
                        return this.doInt16ArrayObj(arguments0Value__, arguments1Value__, int16ArrayObj_ta__);
                    }
                    if ((state_0 & 8) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__) && AtomicsBuiltins.AtomicsOperationNode.isDirectUint16Array(uint16ArrayObj_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__)) && uint16ArrayObj_ta__.isInBoundsFast(arguments0Value__, arguments1Value__)) {
                        return this.doUint16ArrayObj(arguments0Value__, arguments1Value__, uint16ArrayObj_ta__);
                    }
                    if ((state_0 & 0x10) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__) && AtomicsBuiltins.AtomicsOperationNode.isDirectInt32Array(int32ArrayObj_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__)) && int32ArrayObj_ta__.isInBoundsFast(arguments0Value__, arguments1Value__)) {
                        return this.doInt32ArrayObj(arguments0Value__, arguments1Value__, int32ArrayObj_ta__);
                    }
                }
                if ((state_0 & 0x100) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__) && AtomicsBuiltins.AtomicsOperationNode.isDirectInt32Array(int32ArrayObjObjIdx_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__))) {
                    return this.doInt32ArrayObjObjIdx(arguments0Value__, arguments1Value_, int32ArrayObjObjIdx_ta__, this.toIndex);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return JSTypesGen.expectInteger(this.executeAndSpecialize(arguments0Value_, arguments1Value_));
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            int state_0 = this.state_0_;
            try {
                if ((state_0 & 0x2E0) == 0 && state_0 != 0) {
                    this.executeInt(frameValue);
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
        private Object executeAndSpecialize(Object arguments0Value, Object arguments1Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                if (arguments0Value instanceof JSTypedArrayObject) {
                    JSTypedArrayObject arguments0Value_ = (JSTypedArrayObject)arguments0Value;
                    if (arguments1Value instanceof Integer) {
                        int arguments1Value_ = (Integer)arguments1Value;
                        TypedArray int8ArrayObj_ta__ = null;
                        if (AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value_) && AtomicsBuiltins.AtomicsOperationNode.isDirectInt8Array(int8ArrayObj_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value_)) && int8ArrayObj_ta__.isInBoundsFast(arguments0Value_, arguments1Value_)) {
                            this.state_0_ = state_0 |= 1;
                            lock.unlock();
                            hasLock = false;
                            Integer n = this.doInt8ArrayObj(arguments0Value_, arguments1Value_, int8ArrayObj_ta__);
                            return n;
                        }
                        TypedArray uint8ArrayObj_ta__ = null;
                        if (AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value_) && AtomicsBuiltins.AtomicsOperationNode.isDirectUint8Array(uint8ArrayObj_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value_)) && uint8ArrayObj_ta__.isInBoundsFast(arguments0Value_, arguments1Value_)) {
                            this.state_0_ = state_0 |= 2;
                            lock.unlock();
                            hasLock = false;
                            Integer n = this.doUint8ArrayObj(arguments0Value_, arguments1Value_, uint8ArrayObj_ta__);
                            return n;
                        }
                        TypedArray int16ArrayObj_ta__ = null;
                        if (AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value_) && AtomicsBuiltins.AtomicsOperationNode.isDirectInt16Array(int16ArrayObj_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value_)) && int16ArrayObj_ta__.isInBoundsFast(arguments0Value_, arguments1Value_)) {
                            this.state_0_ = state_0 |= 4;
                            lock.unlock();
                            hasLock = false;
                            Integer n = this.doInt16ArrayObj(arguments0Value_, arguments1Value_, int16ArrayObj_ta__);
                            return n;
                        }
                        TypedArray uint16ArrayObj_ta__ = null;
                        if (AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value_) && AtomicsBuiltins.AtomicsOperationNode.isDirectUint16Array(uint16ArrayObj_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value_)) && uint16ArrayObj_ta__.isInBoundsFast(arguments0Value_, arguments1Value_)) {
                            this.state_0_ = state_0 |= 8;
                            lock.unlock();
                            hasLock = false;
                            Integer n = this.doUint16ArrayObj(arguments0Value_, arguments1Value_, uint16ArrayObj_ta__);
                            return n;
                        }
                        TypedArray int32ArrayObj_ta__ = null;
                        if (AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value_) && AtomicsBuiltins.AtomicsOperationNode.isDirectInt32Array(int32ArrayObj_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value_)) && int32ArrayObj_ta__.isInBoundsFast(arguments0Value_, arguments1Value_)) {
                            this.state_0_ = state_0 |= 0x10;
                            lock.unlock();
                            hasLock = false;
                            Integer n = this.doInt32ArrayObj(arguments0Value_, arguments1Value_, int32ArrayObj_ta__);
                            return n;
                        }
                        TypedArray uint32ArrayObj_ta__ = null;
                        if (AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value_) && AtomicsBuiltins.AtomicsOperationNode.isDirectUint32Array(uint32ArrayObj_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value_)) && uint32ArrayObj_ta__.isInBoundsFast(arguments0Value_, arguments1Value_)) {
                            this.state_0_ = state_0 |= 0x20;
                            lock.unlock();
                            hasLock = false;
                            SafeInteger safeInteger = this.doUint32ArrayObj(arguments0Value_, arguments1Value_, uint32ArrayObj_ta__);
                            return safeInteger;
                        }
                        TypedArray bigInt64ArrayObj_ta__ = null;
                        if (AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value_) && AtomicsBuiltins.AtomicsOperationNode.isDirectBigInt64Array(bigInt64ArrayObj_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value_)) && bigInt64ArrayObj_ta__.isInBoundsFast(arguments0Value_, arguments1Value_)) {
                            this.state_0_ = state_0 |= 0x40;
                            lock.unlock();
                            hasLock = false;
                            BigInt bigInt = this.doBigInt64ArrayObj(arguments0Value_, arguments1Value_, bigInt64ArrayObj_ta__);
                            return bigInt;
                        }
                        TypedArray bigUint64ArrayObj_ta__ = null;
                        if (AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value_) && AtomicsBuiltins.AtomicsOperationNode.isDirectBigUint64Array(bigUint64ArrayObj_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value_)) && bigUint64ArrayObj_ta__.isInBoundsFast(arguments0Value_, arguments1Value_)) {
                            this.state_0_ = state_0 |= 0x80;
                            lock.unlock();
                            hasLock = false;
                            BigInt bigInt = this.doBigUint64ArrayObj(arguments0Value_, arguments1Value_, bigUint64ArrayObj_ta__);
                            return bigInt;
                        }
                    }
                    TypedArray int32ArrayObjObjIdx_ta__ = null;
                    if (AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value_) && AtomicsBuiltins.AtomicsOperationNode.isDirectInt32Array(int32ArrayObjObjIdx_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value_))) {
                        this.toIndex = super.insert(this.toIndex == null ? JSToIndexNode.create() : this.toIndex);
                        this.state_0_ = state_0 |= 0x100;
                        lock.unlock();
                        hasLock = false;
                        Integer n = this.doInt32ArrayObjObjIdx(arguments0Value_, arguments1Value, int32ArrayObjObjIdx_ta__, this.toIndex);
                        return n;
                    }
                }
                this.toIndex = super.insert(this.toIndex == null ? JSToIndexNode.create() : this.toIndex);
                this.state_0_ = state_0 |= 0x200;
                lock.unlock();
                hasLock = false;
                Object object = this.doGeneric(arguments0Value, arguments1Value, this.toIndex);
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
            Object[] data = new Object[11];
            data[0] = 0;
            int state_0 = this.state_0_;
            Object[] s = new Object[3];
            s[0] = "doInt8ArrayObj";
            if ((state_0 & 1) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList<List<Object>>();
                cached.add(Arrays.asList(new Object[0]));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[1] = s;
            s = new Object[3];
            s[0] = "doUint8ArrayObj";
            if ((state_0 & 2) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList();
                cached.add(Arrays.asList(new Object[0]));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[2] = s;
            s = new Object[3];
            s[0] = "doInt16ArrayObj";
            if ((state_0 & 4) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList();
                cached.add(Arrays.asList(new Object[0]));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[3] = s;
            s = new Object[3];
            s[0] = "doUint16ArrayObj";
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
            s[0] = "doInt32ArrayObj";
            if ((state_0 & 0x10) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList();
                cached.add(Arrays.asList(new Object[0]));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[5] = s;
            s = new Object[3];
            s[0] = "doUint32ArrayObj";
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
            s[0] = "doBigInt64ArrayObj";
            if ((state_0 & 0x40) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList();
                cached.add(Arrays.asList(new Object[0]));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[7] = s;
            s = new Object[3];
            s[0] = "doBigUint64ArrayObj";
            if ((state_0 & 0x80) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList();
                cached.add(Arrays.asList(new Object[0]));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[8] = s;
            s = new Object[3];
            s[0] = "doInt32ArrayObjObjIdx";
            if ((state_0 & 0x100) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList();
                cached.add(Arrays.asList(this.toIndex));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[9] = s;
            s = new Object[3];
            s[0] = "doGeneric";
            if ((state_0 & 0x200) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList();
                cached.add(Arrays.asList(this.toIndex));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[10] = s;
            return Introspection.Provider.create(data);
        }

        public static AtomicsBuiltins.AtomicsLoadNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new AtomicsLoadNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=AtomicsBuiltins.AtomicsCompareExchangeNode.class)
    public static final class AtomicsCompareExchangeNodeGen
    extends AtomicsBuiltins.AtomicsCompareExchangeNode
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
        private JSToIndexNode toIndex;
        @CompilerDirectives.CompilationFinal
        private BranchProfile generic_notSharedArrayBuffer_;

        private AtomicsCompareExchangeNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            if ((state_0 & 0xFD0) == 0 && state_0 != 0) {
                return this.execute_int_int_int0(state_0, frameValue);
            }
            if ((state_0 & 0xF7F) == 0 && state_0 != 0) {
                return this.execute_int_int1(state_0, frameValue);
            }
            if ((state_0 & 0xF8F) == 0 && state_0 != 0) {
                return this.execute_int2(state_0, frameValue);
            }
            return this.execute_generic3(state_0, frameValue);
        }

        private Object execute_int_int_int0(int state_0, VirtualFrame frameValue) {
            int arguments3Value_;
            int arguments2Value_;
            int arguments1Value_;
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            try {
                arguments1Value_ = this.arguments1_.executeInt(frameValue);
            }
            catch (UnexpectedResultException ex) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                Object arguments2Value = this.arguments2_.execute(frameValue);
                Object arguments3Value = this.arguments3_.execute(frameValue);
                return this.executeAndSpecialize(arguments0Value_, ex.getResult(), arguments2Value, arguments3Value);
            }
            try {
                arguments2Value_ = this.arguments2_.executeInt(frameValue);
            }
            catch (UnexpectedResultException ex) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                Object arguments3Value = this.arguments3_.execute(frameValue);
                return this.executeAndSpecialize(arguments0Value_, arguments1Value_, ex.getResult(), arguments3Value);
            }
            try {
                arguments3Value_ = this.arguments3_.executeInt(frameValue);
            }
            catch (UnexpectedResultException ex) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.executeAndSpecialize(arguments0Value_, arguments1Value_, arguments2Value_, ex.getResult());
            }
            if ((state_0 & 0x2F) != 0 && arguments0Value_ instanceof JSTypedArrayObject) {
                TypedArray int32ArrayInt_ta__;
                TypedArray uint16Array_ta__;
                TypedArray int16Array_ta__;
                TypedArray uint8Array_ta__;
                TypedArray int8Array_ta__;
                JSTypedArrayObject arguments0Value__ = (JSTypedArrayObject)arguments0Value_;
                if ((state_0 & 1) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__) && AtomicsBuiltins.AtomicsOperationNode.isDirectInt8Array(int8Array_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__)) && int8Array_ta__.isInBoundsFast(arguments0Value__, arguments1Value_)) {
                    return this.doInt8Array(arguments0Value__, arguments1Value_, arguments2Value_, arguments3Value_, int8Array_ta__);
                }
                if ((state_0 & 2) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__) && AtomicsBuiltins.AtomicsOperationNode.isDirectUint8Array(uint8Array_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__)) && uint8Array_ta__.isInBoundsFast(arguments0Value__, arguments1Value_)) {
                    return this.doUint8Array(arguments0Value__, arguments1Value_, arguments2Value_, arguments3Value_, uint8Array_ta__);
                }
                if ((state_0 & 4) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__) && AtomicsBuiltins.AtomicsOperationNode.isDirectInt16Array(int16Array_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__)) && int16Array_ta__.isInBoundsFast(arguments0Value__, arguments1Value_)) {
                    return this.doInt16Array(arguments0Value__, arguments1Value_, arguments2Value_, arguments3Value_, int16Array_ta__);
                }
                if ((state_0 & 8) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__) && AtomicsBuiltins.AtomicsOperationNode.isDirectUint16Array(uint16Array_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__)) && uint16Array_ta__.isInBoundsFast(arguments0Value__, arguments1Value_)) {
                    return this.doUint16Array(arguments0Value__, arguments1Value_, arguments2Value_, arguments3Value_, uint16Array_ta__);
                }
                if ((state_0 & 0x20) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__) && AtomicsBuiltins.AtomicsOperationNode.isDirectInt32Array(int32ArrayInt_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__)) && int32ArrayInt_ta__.isInBoundsFast(arguments0Value__, arguments1Value_)) {
                    return this.doInt32ArrayInt(arguments0Value__, arguments1Value_, arguments2Value_, arguments3Value_, int32ArrayInt_ta__);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_, arguments2Value_, arguments3Value_);
        }

        private Object execute_int_int1(int state_0, VirtualFrame frameValue) {
            TypedArray int32ArrayIntObjIdx_ta__;
            JSTypedArrayObject arguments0Value__;
            int arguments3Value_;
            int arguments2Value_;
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            try {
                arguments2Value_ = this.arguments2_.executeInt(frameValue);
            }
            catch (UnexpectedResultException ex) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                Object arguments3Value = this.arguments3_.execute(frameValue);
                return this.executeAndSpecialize(arguments0Value_, arguments1Value_, ex.getResult(), arguments3Value);
            }
            try {
                arguments3Value_ = this.arguments3_.executeInt(frameValue);
            }
            catch (UnexpectedResultException ex) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.executeAndSpecialize(arguments0Value_, arguments1Value_, arguments2Value_, ex.getResult());
            }
            assert ((state_0 & 0x80) != 0);
            if (arguments0Value_ instanceof JSTypedArrayObject && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__ = (JSTypedArrayObject)arguments0Value_) && AtomicsBuiltins.AtomicsOperationNode.isDirectInt32Array(int32ArrayIntObjIdx_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__))) {
                return this.doInt32ArrayIntObjIdx(arguments0Value__, arguments1Value_, arguments2Value_, arguments3Value_, int32ArrayIntObjIdx_ta__, this.toIndex);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_, arguments2Value_, arguments3Value_);
        }

        private Object execute_int2(int state_0, VirtualFrame frameValue) {
            int arguments1Value_;
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            try {
                arguments1Value_ = this.arguments1_.executeInt(frameValue);
            }
            catch (UnexpectedResultException ex) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                Object arguments2Value = this.arguments2_.execute(frameValue);
                Object arguments3Value = this.arguments3_.execute(frameValue);
                return this.executeAndSpecialize(arguments0Value_, ex.getResult(), arguments2Value, arguments3Value);
            }
            Object arguments2Value_ = this.arguments2_.execute(frameValue);
            Object arguments3Value_ = this.arguments3_.execute(frameValue);
            if ((state_0 & 0x70) != 0 && arguments0Value_ instanceof JSTypedArrayObject) {
                TypedArray int32ArrayObj_ta__;
                TypedArray uint32Array_ta__;
                JSTypedArrayObject arguments0Value__ = (JSTypedArrayObject)arguments0Value_;
                if ((state_0 & 0x10) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__) && AtomicsBuiltins.AtomicsOperationNode.isDirectUint32Array(uint32Array_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__)) && uint32Array_ta__.isInBoundsFast(arguments0Value__, arguments1Value_)) {
                    return this.doUint32Array(arguments0Value__, arguments1Value_, arguments2Value_, arguments3Value_, uint32Array_ta__);
                }
                if ((state_0 & 0x20) != 0 && arguments2Value_ instanceof Integer) {
                    int arguments2Value__ = (Integer)arguments2Value_;
                    if (arguments3Value_ instanceof Integer) {
                        TypedArray int32ArrayInt_ta__;
                        int arguments3Value__ = (Integer)arguments3Value_;
                        if (AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__) && AtomicsBuiltins.AtomicsOperationNode.isDirectInt32Array(int32ArrayInt_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__)) && int32ArrayInt_ta__.isInBoundsFast(arguments0Value__, arguments1Value_)) {
                            return this.doInt32ArrayInt(arguments0Value__, arguments1Value_, arguments2Value__, arguments3Value__, int32ArrayInt_ta__);
                        }
                    }
                }
                if ((state_0 & 0x40) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__) && AtomicsBuiltins.AtomicsOperationNode.isDirectInt32Array(int32ArrayObj_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__)) && int32ArrayObj_ta__.isInBoundsFast(arguments0Value__, arguments1Value_)) {
                    return this.doInt32ArrayObj(arguments0Value__, arguments1Value_, arguments2Value_, arguments3Value_, int32ArrayObj_ta__);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_, arguments2Value_, arguments3Value_);
        }

        private Object execute_generic3(int state_0, VirtualFrame frameValue) {
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            Object arguments2Value_ = this.arguments2_.execute(frameValue);
            Object arguments3Value_ = this.arguments3_.execute(frameValue);
            if ((state_0 & 0x7FF) != 0 && arguments0Value_ instanceof JSTypedArrayObject) {
                JSTypedArrayObject arguments0Value__ = (JSTypedArrayObject)arguments0Value_;
                if ((state_0 & 0x7F) != 0 && arguments1Value_ instanceof Integer) {
                    TypedArray int32ArrayObj_ta__;
                    TypedArray uint32Array_ta__;
                    int arguments3Value__;
                    int arguments2Value__;
                    int arguments1Value__ = (Integer)arguments1Value_;
                    if ((state_0 & 0xF) != 0 && arguments2Value_ instanceof Integer) {
                        arguments2Value__ = (Integer)arguments2Value_;
                        if (arguments3Value_ instanceof Integer) {
                            TypedArray uint16Array_ta__;
                            TypedArray int16Array_ta__;
                            TypedArray uint8Array_ta__;
                            TypedArray int8Array_ta__;
                            arguments3Value__ = (Integer)arguments3Value_;
                            if ((state_0 & 1) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__) && AtomicsBuiltins.AtomicsOperationNode.isDirectInt8Array(int8Array_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__)) && int8Array_ta__.isInBoundsFast(arguments0Value__, arguments1Value__)) {
                                return this.doInt8Array(arguments0Value__, arguments1Value__, arguments2Value__, arguments3Value__, int8Array_ta__);
                            }
                            if ((state_0 & 2) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__) && AtomicsBuiltins.AtomicsOperationNode.isDirectUint8Array(uint8Array_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__)) && uint8Array_ta__.isInBoundsFast(arguments0Value__, arguments1Value__)) {
                                return this.doUint8Array(arguments0Value__, arguments1Value__, arguments2Value__, arguments3Value__, uint8Array_ta__);
                            }
                            if ((state_0 & 4) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__) && AtomicsBuiltins.AtomicsOperationNode.isDirectInt16Array(int16Array_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__)) && int16Array_ta__.isInBoundsFast(arguments0Value__, arguments1Value__)) {
                                return this.doInt16Array(arguments0Value__, arguments1Value__, arguments2Value__, arguments3Value__, int16Array_ta__);
                            }
                            if ((state_0 & 8) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__) && AtomicsBuiltins.AtomicsOperationNode.isDirectUint16Array(uint16Array_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__)) && uint16Array_ta__.isInBoundsFast(arguments0Value__, arguments1Value__)) {
                                return this.doUint16Array(arguments0Value__, arguments1Value__, arguments2Value__, arguments3Value__, uint16Array_ta__);
                            }
                        }
                    }
                    if ((state_0 & 0x10) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__) && AtomicsBuiltins.AtomicsOperationNode.isDirectUint32Array(uint32Array_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__)) && uint32Array_ta__.isInBoundsFast(arguments0Value__, arguments1Value__)) {
                        return this.doUint32Array(arguments0Value__, arguments1Value__, arguments2Value_, arguments3Value_, uint32Array_ta__);
                    }
                    if ((state_0 & 0x20) != 0 && arguments2Value_ instanceof Integer) {
                        arguments2Value__ = (Integer)arguments2Value_;
                        if (arguments3Value_ instanceof Integer) {
                            TypedArray int32ArrayInt_ta__;
                            arguments3Value__ = (Integer)arguments3Value_;
                            if (AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__) && AtomicsBuiltins.AtomicsOperationNode.isDirectInt32Array(int32ArrayInt_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__)) && int32ArrayInt_ta__.isInBoundsFast(arguments0Value__, arguments1Value__)) {
                                return this.doInt32ArrayInt(arguments0Value__, arguments1Value__, arguments2Value__, arguments3Value__, int32ArrayInt_ta__);
                            }
                        }
                    }
                    if ((state_0 & 0x40) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__) && AtomicsBuiltins.AtomicsOperationNode.isDirectInt32Array(int32ArrayObj_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__)) && int32ArrayObj_ta__.isInBoundsFast(arguments0Value__, arguments1Value__)) {
                        return this.doInt32ArrayObj(arguments0Value__, arguments1Value__, arguments2Value_, arguments3Value_, int32ArrayObj_ta__);
                    }
                }
                if ((state_0 & 0x780) != 0) {
                    if ((state_0 & 0x80) != 0 && arguments2Value_ instanceof Integer) {
                        int arguments2Value__ = (Integer)arguments2Value_;
                        if (arguments3Value_ instanceof Integer) {
                            TypedArray int32ArrayIntObjIdx_ta__;
                            int arguments3Value__ = (Integer)arguments3Value_;
                            if (AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__) && AtomicsBuiltins.AtomicsOperationNode.isDirectInt32Array(int32ArrayIntObjIdx_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__))) {
                                return this.doInt32ArrayIntObjIdx(arguments0Value__, arguments1Value_, arguments2Value__, arguments3Value__, int32ArrayIntObjIdx_ta__, this.toIndex);
                            }
                        }
                    }
                    if ((state_0 & 0x700) != 0) {
                        TypedArray bigUint64ArrayObjObjIdx_ta__;
                        TypedArray bigInt64ArrayObjObjIdx_ta__;
                        TypedArray int32ArrayObjObjIdx_ta__;
                        if ((state_0 & 0x100) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__) && AtomicsBuiltins.AtomicsOperationNode.isDirectInt32Array(int32ArrayObjObjIdx_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__))) {
                            return this.doInt32ArrayObjObjIdx(arguments0Value__, arguments1Value_, arguments2Value_, arguments3Value_, int32ArrayObjObjIdx_ta__, this.toIndex);
                        }
                        if ((state_0 & 0x200) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__) && AtomicsBuiltins.AtomicsOperationNode.isDirectBigInt64Array(bigInt64ArrayObjObjIdx_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__))) {
                            return this.doBigInt64ArrayObjObjIdx(arguments0Value__, arguments1Value_, arguments2Value_, arguments3Value_, bigInt64ArrayObjObjIdx_ta__, this.toIndex);
                        }
                        if ((state_0 & 0x400) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__) && AtomicsBuiltins.AtomicsOperationNode.isDirectBigUint64Array(bigUint64ArrayObjObjIdx_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__))) {
                            return this.doBigUint64ArrayObjObjIdx(arguments0Value__, arguments1Value_, arguments2Value_, arguments3Value_, bigUint64ArrayObjObjIdx_ta__, this.toIndex);
                        }
                    }
                }
            }
            if ((state_0 & 0x800) != 0) {
                return this.doGeneric(arguments0Value_, arguments1Value_, arguments2Value_, arguments3Value_, this.toIndex, this.generic_notSharedArrayBuffer_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_, arguments2Value_, arguments3Value_);
        }

        @Override
        public int executeInt(VirtualFrame frameValue) throws UnexpectedResultException {
            int state_0 = this.state_0_;
            if ((state_0 & 0x810) != 0) {
                return JSTypesGen.expectInteger(this.execute(frameValue));
            }
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            if ((state_0 & 0x1C0) == 0 && (state_0 & 0x1EF) != 0) {
                return this.executeInt_int_int_int4(state_0, frameValue, arguments0Value_);
            }
            if ((state_0 & 0x16F) == 0 && (state_0 & 0x1EF) != 0) {
                return this.executeInt_int_int5(state_0, frameValue, arguments0Value_);
            }
            if ((state_0 & 0x1AF) == 0 && (state_0 & 0x1EF) != 0) {
                return this.executeInt_int6(state_0, frameValue, arguments0Value_);
            }
            return this.executeInt_generic7(state_0, frameValue, arguments0Value_);
        }

        private int executeInt_int_int_int4(int state_0, VirtualFrame frameValue, Object arguments0Value_) throws UnexpectedResultException {
            int arguments3Value_;
            int arguments2Value_;
            int arguments1Value_;
            try {
                arguments1Value_ = this.arguments1_.executeInt(frameValue);
            }
            catch (UnexpectedResultException ex) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                Object arguments2Value = this.arguments2_.execute(frameValue);
                Object arguments3Value = this.arguments3_.execute(frameValue);
                return JSTypesGen.expectInteger(this.executeAndSpecialize(arguments0Value_, ex.getResult(), arguments2Value, arguments3Value));
            }
            try {
                arguments2Value_ = this.arguments2_.executeInt(frameValue);
            }
            catch (UnexpectedResultException ex) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                Object arguments3Value = this.arguments3_.execute(frameValue);
                return JSTypesGen.expectInteger(this.executeAndSpecialize(arguments0Value_, arguments1Value_, ex.getResult(), arguments3Value));
            }
            try {
                arguments3Value_ = this.arguments3_.executeInt(frameValue);
            }
            catch (UnexpectedResultException ex) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return JSTypesGen.expectInteger(this.executeAndSpecialize(arguments0Value_, arguments1Value_, arguments2Value_, ex.getResult()));
            }
            if ((state_0 & 0x2F) != 0 && arguments0Value_ instanceof JSTypedArrayObject) {
                TypedArray int32ArrayInt_ta__;
                TypedArray uint16Array_ta__;
                TypedArray int16Array_ta__;
                TypedArray uint8Array_ta__;
                TypedArray int8Array_ta__;
                JSTypedArrayObject arguments0Value__ = (JSTypedArrayObject)arguments0Value_;
                if ((state_0 & 1) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__) && AtomicsBuiltins.AtomicsOperationNode.isDirectInt8Array(int8Array_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__)) && int8Array_ta__.isInBoundsFast(arguments0Value__, arguments1Value_)) {
                    return this.doInt8Array(arguments0Value__, arguments1Value_, arguments2Value_, arguments3Value_, int8Array_ta__);
                }
                if ((state_0 & 2) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__) && AtomicsBuiltins.AtomicsOperationNode.isDirectUint8Array(uint8Array_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__)) && uint8Array_ta__.isInBoundsFast(arguments0Value__, arguments1Value_)) {
                    return this.doUint8Array(arguments0Value__, arguments1Value_, arguments2Value_, arguments3Value_, uint8Array_ta__);
                }
                if ((state_0 & 4) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__) && AtomicsBuiltins.AtomicsOperationNode.isDirectInt16Array(int16Array_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__)) && int16Array_ta__.isInBoundsFast(arguments0Value__, arguments1Value_)) {
                    return this.doInt16Array(arguments0Value__, arguments1Value_, arguments2Value_, arguments3Value_, int16Array_ta__);
                }
                if ((state_0 & 8) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__) && AtomicsBuiltins.AtomicsOperationNode.isDirectUint16Array(uint16Array_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__)) && uint16Array_ta__.isInBoundsFast(arguments0Value__, arguments1Value_)) {
                    return this.doUint16Array(arguments0Value__, arguments1Value_, arguments2Value_, arguments3Value_, uint16Array_ta__);
                }
                if ((state_0 & 0x20) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__) && AtomicsBuiltins.AtomicsOperationNode.isDirectInt32Array(int32ArrayInt_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__)) && int32ArrayInt_ta__.isInBoundsFast(arguments0Value__, arguments1Value_)) {
                    return this.doInt32ArrayInt(arguments0Value__, arguments1Value_, arguments2Value_, arguments3Value_, int32ArrayInt_ta__);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return JSTypesGen.expectInteger(this.executeAndSpecialize(arguments0Value_, arguments1Value_, arguments2Value_, arguments3Value_));
        }

        private int executeInt_int_int5(int state_0, VirtualFrame frameValue, Object arguments0Value_) throws UnexpectedResultException {
            TypedArray int32ArrayIntObjIdx_ta__;
            JSTypedArrayObject arguments0Value__;
            int arguments3Value_;
            int arguments2Value_;
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            try {
                arguments2Value_ = this.arguments2_.executeInt(frameValue);
            }
            catch (UnexpectedResultException ex) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                Object arguments3Value = this.arguments3_.execute(frameValue);
                return JSTypesGen.expectInteger(this.executeAndSpecialize(arguments0Value_, arguments1Value_, ex.getResult(), arguments3Value));
            }
            try {
                arguments3Value_ = this.arguments3_.executeInt(frameValue);
            }
            catch (UnexpectedResultException ex) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return JSTypesGen.expectInteger(this.executeAndSpecialize(arguments0Value_, arguments1Value_, arguments2Value_, ex.getResult()));
            }
            assert ((state_0 & 0x80) != 0);
            if (arguments0Value_ instanceof JSTypedArrayObject && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__ = (JSTypedArrayObject)arguments0Value_) && AtomicsBuiltins.AtomicsOperationNode.isDirectInt32Array(int32ArrayIntObjIdx_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__))) {
                return this.doInt32ArrayIntObjIdx(arguments0Value__, arguments1Value_, arguments2Value_, arguments3Value_, int32ArrayIntObjIdx_ta__, this.toIndex);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return JSTypesGen.expectInteger(this.executeAndSpecialize(arguments0Value_, arguments1Value_, arguments2Value_, arguments3Value_));
        }

        private int executeInt_int6(int state_0, VirtualFrame frameValue, Object arguments0Value_) throws UnexpectedResultException {
            TypedArray int32ArrayObj_ta__;
            JSTypedArrayObject arguments0Value__;
            int arguments1Value_;
            try {
                arguments1Value_ = this.arguments1_.executeInt(frameValue);
            }
            catch (UnexpectedResultException ex) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                Object arguments2Value = this.arguments2_.execute(frameValue);
                Object arguments3Value = this.arguments3_.execute(frameValue);
                return JSTypesGen.expectInteger(this.executeAndSpecialize(arguments0Value_, ex.getResult(), arguments2Value, arguments3Value));
            }
            Object arguments2Value_ = this.arguments2_.execute(frameValue);
            Object arguments3Value_ = this.arguments3_.execute(frameValue);
            assert ((state_0 & 0x40) != 0);
            if (arguments0Value_ instanceof JSTypedArrayObject && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__ = (JSTypedArrayObject)arguments0Value_) && AtomicsBuiltins.AtomicsOperationNode.isDirectInt32Array(int32ArrayObj_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__)) && int32ArrayObj_ta__.isInBoundsFast(arguments0Value__, arguments1Value_)) {
                return this.doInt32ArrayObj(arguments0Value__, arguments1Value_, arguments2Value_, arguments3Value_, int32ArrayObj_ta__);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return JSTypesGen.expectInteger(this.executeAndSpecialize(arguments0Value_, arguments1Value_, arguments2Value_, arguments3Value_));
        }

        private int executeInt_generic7(int state_0, VirtualFrame frameValue, Object arguments0Value_) throws UnexpectedResultException {
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            Object arguments2Value_ = this.arguments2_.execute(frameValue);
            Object arguments3Value_ = this.arguments3_.execute(frameValue);
            if ((state_0 & 0x1EF) != 0 && arguments0Value_ instanceof JSTypedArrayObject) {
                JSTypedArrayObject arguments0Value__ = (JSTypedArrayObject)arguments0Value_;
                if ((state_0 & 0x6F) != 0 && arguments1Value_ instanceof Integer) {
                    TypedArray int32ArrayObj_ta__;
                    int arguments1Value__ = (Integer)arguments1Value_;
                    if ((state_0 & 0x2F) != 0 && arguments2Value_ instanceof Integer) {
                        int arguments2Value__ = (Integer)arguments2Value_;
                        if (arguments3Value_ instanceof Integer) {
                            TypedArray int32ArrayInt_ta__;
                            TypedArray uint16Array_ta__;
                            TypedArray int16Array_ta__;
                            TypedArray uint8Array_ta__;
                            TypedArray int8Array_ta__;
                            int arguments3Value__ = (Integer)arguments3Value_;
                            if ((state_0 & 1) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__) && AtomicsBuiltins.AtomicsOperationNode.isDirectInt8Array(int8Array_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__)) && int8Array_ta__.isInBoundsFast(arguments0Value__, arguments1Value__)) {
                                return this.doInt8Array(arguments0Value__, arguments1Value__, arguments2Value__, arguments3Value__, int8Array_ta__);
                            }
                            if ((state_0 & 2) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__) && AtomicsBuiltins.AtomicsOperationNode.isDirectUint8Array(uint8Array_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__)) && uint8Array_ta__.isInBoundsFast(arguments0Value__, arguments1Value__)) {
                                return this.doUint8Array(arguments0Value__, arguments1Value__, arguments2Value__, arguments3Value__, uint8Array_ta__);
                            }
                            if ((state_0 & 4) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__) && AtomicsBuiltins.AtomicsOperationNode.isDirectInt16Array(int16Array_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__)) && int16Array_ta__.isInBoundsFast(arguments0Value__, arguments1Value__)) {
                                return this.doInt16Array(arguments0Value__, arguments1Value__, arguments2Value__, arguments3Value__, int16Array_ta__);
                            }
                            if ((state_0 & 8) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__) && AtomicsBuiltins.AtomicsOperationNode.isDirectUint16Array(uint16Array_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__)) && uint16Array_ta__.isInBoundsFast(arguments0Value__, arguments1Value__)) {
                                return this.doUint16Array(arguments0Value__, arguments1Value__, arguments2Value__, arguments3Value__, uint16Array_ta__);
                            }
                            if ((state_0 & 0x20) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__) && AtomicsBuiltins.AtomicsOperationNode.isDirectInt32Array(int32ArrayInt_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__)) && int32ArrayInt_ta__.isInBoundsFast(arguments0Value__, arguments1Value__)) {
                                return this.doInt32ArrayInt(arguments0Value__, arguments1Value__, arguments2Value__, arguments3Value__, int32ArrayInt_ta__);
                            }
                        }
                    }
                    if ((state_0 & 0x40) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__) && AtomicsBuiltins.AtomicsOperationNode.isDirectInt32Array(int32ArrayObj_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__)) && int32ArrayObj_ta__.isInBoundsFast(arguments0Value__, arguments1Value__)) {
                        return this.doInt32ArrayObj(arguments0Value__, arguments1Value__, arguments2Value_, arguments3Value_, int32ArrayObj_ta__);
                    }
                }
                if ((state_0 & 0x180) != 0) {
                    TypedArray int32ArrayObjObjIdx_ta__;
                    if ((state_0 & 0x80) != 0 && arguments2Value_ instanceof Integer) {
                        int arguments2Value__ = (Integer)arguments2Value_;
                        if (arguments3Value_ instanceof Integer) {
                            TypedArray int32ArrayIntObjIdx_ta__;
                            int arguments3Value__ = (Integer)arguments3Value_;
                            if (AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__) && AtomicsBuiltins.AtomicsOperationNode.isDirectInt32Array(int32ArrayIntObjIdx_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__))) {
                                return this.doInt32ArrayIntObjIdx(arguments0Value__, arguments1Value_, arguments2Value__, arguments3Value__, int32ArrayIntObjIdx_ta__, this.toIndex);
                            }
                        }
                    }
                    if ((state_0 & 0x100) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__) && AtomicsBuiltins.AtomicsOperationNode.isDirectInt32Array(int32ArrayObjObjIdx_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__))) {
                        return this.doInt32ArrayObjObjIdx(arguments0Value__, arguments1Value_, arguments2Value_, arguments3Value_, int32ArrayObjObjIdx_ta__, this.toIndex);
                    }
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return JSTypesGen.expectInteger(this.executeAndSpecialize(arguments0Value_, arguments1Value_, arguments2Value_, arguments3Value_));
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            int state_0 = this.state_0_;
            try {
                if ((state_0 & 0xE10) == 0 && state_0 != 0) {
                    this.executeInt(frameValue);
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
        private Object executeAndSpecialize(Object arguments0Value, Object arguments1Value, Object arguments2Value, Object arguments3Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                if (arguments0Value instanceof JSTypedArrayObject) {
                    int arguments3Value_;
                    JSTypedArrayObject arguments0Value_ = (JSTypedArrayObject)arguments0Value;
                    if (arguments1Value instanceof Integer) {
                        int arguments1Value_ = (Integer)arguments1Value;
                        if (arguments2Value instanceof Integer) {
                            int arguments2Value_ = (Integer)arguments2Value;
                            if (arguments3Value instanceof Integer) {
                                arguments3Value_ = (Integer)arguments3Value;
                                TypedArray int8Array_ta__ = null;
                                if (AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value_) && AtomicsBuiltins.AtomicsOperationNode.isDirectInt8Array(int8Array_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value_)) && int8Array_ta__.isInBoundsFast(arguments0Value_, arguments1Value_)) {
                                    this.state_0_ = state_0 |= 1;
                                    lock.unlock();
                                    hasLock = false;
                                    Integer n = this.doInt8Array(arguments0Value_, arguments1Value_, arguments2Value_, arguments3Value_, int8Array_ta__);
                                    return n;
                                }
                                TypedArray uint8Array_ta__ = null;
                                if (AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value_) && AtomicsBuiltins.AtomicsOperationNode.isDirectUint8Array(uint8Array_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value_)) && uint8Array_ta__.isInBoundsFast(arguments0Value_, arguments1Value_)) {
                                    this.state_0_ = state_0 |= 2;
                                    lock.unlock();
                                    hasLock = false;
                                    Integer n = this.doUint8Array(arguments0Value_, arguments1Value_, arguments2Value_, arguments3Value_, uint8Array_ta__);
                                    return n;
                                }
                                TypedArray int16Array_ta__ = null;
                                if (AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value_) && AtomicsBuiltins.AtomicsOperationNode.isDirectInt16Array(int16Array_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value_)) && int16Array_ta__.isInBoundsFast(arguments0Value_, arguments1Value_)) {
                                    this.state_0_ = state_0 |= 4;
                                    lock.unlock();
                                    hasLock = false;
                                    Integer n = this.doInt16Array(arguments0Value_, arguments1Value_, arguments2Value_, arguments3Value_, int16Array_ta__);
                                    return n;
                                }
                                TypedArray uint16Array_ta__ = null;
                                if (AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value_) && AtomicsBuiltins.AtomicsOperationNode.isDirectUint16Array(uint16Array_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value_)) && uint16Array_ta__.isInBoundsFast(arguments0Value_, arguments1Value_)) {
                                    this.state_0_ = state_0 |= 8;
                                    lock.unlock();
                                    hasLock = false;
                                    Integer n = this.doUint16Array(arguments0Value_, arguments1Value_, arguments2Value_, arguments3Value_, uint16Array_ta__);
                                    return n;
                                }
                            }
                        }
                        TypedArray uint32Array_ta__ = null;
                        if (AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value_) && AtomicsBuiltins.AtomicsOperationNode.isDirectUint32Array(uint32Array_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value_)) && uint32Array_ta__.isInBoundsFast(arguments0Value_, arguments1Value_)) {
                            this.state_0_ = state_0 |= 0x10;
                            lock.unlock();
                            hasLock = false;
                            Object arguments3Value_2 = this.doUint32Array(arguments0Value_, arguments1Value_, arguments2Value, arguments3Value, uint32Array_ta__);
                            return arguments3Value_2;
                        }
                        TypedArray int32ArrayInt_ta__ = null;
                        if (arguments2Value instanceof Integer) {
                            int arguments2Value_ = (Integer)arguments2Value;
                            if (arguments3Value instanceof Integer) {
                                int arguments3Value_3 = (Integer)arguments3Value;
                                if (AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value_) && AtomicsBuiltins.AtomicsOperationNode.isDirectInt32Array(int32ArrayInt_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value_)) && int32ArrayInt_ta__.isInBoundsFast(arguments0Value_, arguments1Value_)) {
                                    this.state_0_ = state_0 |= 0x20;
                                    lock.unlock();
                                    hasLock = false;
                                    Integer n = this.doInt32ArrayInt(arguments0Value_, arguments1Value_, arguments2Value_, arguments3Value_3, int32ArrayInt_ta__);
                                    return n;
                                }
                            }
                        }
                        TypedArray int32ArrayObj_ta__ = null;
                        if (AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value_) && AtomicsBuiltins.AtomicsOperationNode.isDirectInt32Array(int32ArrayObj_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value_)) && int32ArrayObj_ta__.isInBoundsFast(arguments0Value_, arguments1Value_)) {
                            this.state_0_ = state_0 |= 0x40;
                            lock.unlock();
                            hasLock = false;
                            Integer arguments2Value_ = this.doInt32ArrayObj(arguments0Value_, arguments1Value_, arguments2Value, arguments3Value, int32ArrayObj_ta__);
                            return arguments2Value_;
                        }
                    }
                    TypedArray int32ArrayIntObjIdx_ta__ = null;
                    if (arguments2Value instanceof Integer) {
                        int arguments2Value_ = (Integer)arguments2Value;
                        if (arguments3Value instanceof Integer) {
                            arguments3Value_ = (Integer)arguments3Value;
                            if (AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value_) && AtomicsBuiltins.AtomicsOperationNode.isDirectInt32Array(int32ArrayIntObjIdx_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value_))) {
                                this.toIndex = super.insert(this.toIndex == null ? JSToIndexNode.create() : this.toIndex);
                                this.state_0_ = state_0 |= 0x80;
                                lock.unlock();
                                hasLock = false;
                                Integer n = this.doInt32ArrayIntObjIdx(arguments0Value_, arguments1Value, arguments2Value_, arguments3Value_, int32ArrayIntObjIdx_ta__, this.toIndex);
                                return n;
                            }
                        }
                    }
                    TypedArray int32ArrayObjObjIdx_ta__ = null;
                    if (AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value_) && AtomicsBuiltins.AtomicsOperationNode.isDirectInt32Array(int32ArrayObjObjIdx_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value_))) {
                        this.toIndex = super.insert(this.toIndex == null ? JSToIndexNode.create() : this.toIndex);
                        this.state_0_ = state_0 |= 0x100;
                        lock.unlock();
                        hasLock = false;
                        Integer n = this.doInt32ArrayObjObjIdx(arguments0Value_, arguments1Value, arguments2Value, arguments3Value, int32ArrayObjObjIdx_ta__, this.toIndex);
                        return n;
                    }
                    TypedArray bigInt64ArrayObjObjIdx_ta__ = null;
                    if (AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value_) && AtomicsBuiltins.AtomicsOperationNode.isDirectBigInt64Array(bigInt64ArrayObjObjIdx_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value_))) {
                        this.toIndex = super.insert(this.toIndex == null ? JSToIndexNode.create() : this.toIndex);
                        this.state_0_ = state_0 |= 0x200;
                        lock.unlock();
                        hasLock = false;
                        BigInt bigInt = this.doBigInt64ArrayObjObjIdx(arguments0Value_, arguments1Value, arguments2Value, arguments3Value, bigInt64ArrayObjObjIdx_ta__, this.toIndex);
                        return bigInt;
                    }
                    TypedArray bigUint64ArrayObjObjIdx_ta__ = null;
                    if (AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value_) && AtomicsBuiltins.AtomicsOperationNode.isDirectBigUint64Array(bigUint64ArrayObjObjIdx_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value_))) {
                        this.toIndex = super.insert(this.toIndex == null ? JSToIndexNode.create() : this.toIndex);
                        this.state_0_ = state_0 |= 0x400;
                        lock.unlock();
                        hasLock = false;
                        BigInt bigInt = this.doBigUint64ArrayObjObjIdx(arguments0Value_, arguments1Value, arguments2Value, arguments3Value, bigUint64ArrayObjObjIdx_ta__, this.toIndex);
                        return bigInt;
                    }
                }
                this.toIndex = super.insert(this.toIndex == null ? JSToIndexNode.create() : this.toIndex);
                this.generic_notSharedArrayBuffer_ = BranchProfile.create();
                this.state_0_ = state_0 |= 0x800;
                lock.unlock();
                hasLock = false;
                Object object = this.doGeneric(arguments0Value, arguments1Value, arguments2Value, arguments3Value, this.toIndex, this.generic_notSharedArrayBuffer_);
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
            Object[] data = new Object[13];
            data[0] = 0;
            int state_0 = this.state_0_;
            Object[] s = new Object[3];
            s[0] = "doInt8Array";
            if ((state_0 & 1) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList<List<Object>>();
                cached.add(Arrays.asList(new Object[0]));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[1] = s;
            s = new Object[3];
            s[0] = "doUint8Array";
            if ((state_0 & 2) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList();
                cached.add(Arrays.asList(new Object[0]));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[2] = s;
            s = new Object[3];
            s[0] = "doInt16Array";
            if ((state_0 & 4) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList();
                cached.add(Arrays.asList(new Object[0]));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[3] = s;
            s = new Object[3];
            s[0] = "doUint16Array";
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
            s[0] = "doUint32Array";
            if ((state_0 & 0x10) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList();
                cached.add(Arrays.asList(new Object[0]));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[5] = s;
            s = new Object[3];
            s[0] = "doInt32ArrayInt";
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
            s[0] = "doInt32ArrayObj";
            if ((state_0 & 0x40) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList();
                cached.add(Arrays.asList(new Object[0]));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[7] = s;
            s = new Object[3];
            s[0] = "doInt32ArrayIntObjIdx";
            if ((state_0 & 0x80) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList();
                cached.add(Arrays.asList(this.toIndex));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[8] = s;
            s = new Object[3];
            s[0] = "doInt32ArrayObjObjIdx";
            if ((state_0 & 0x100) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList();
                cached.add(Arrays.asList(this.toIndex));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[9] = s;
            s = new Object[3];
            s[0] = "doBigInt64ArrayObjObjIdx";
            if ((state_0 & 0x200) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList();
                cached.add(Arrays.asList(this.toIndex));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[10] = s;
            s = new Object[3];
            s[0] = "doBigUint64ArrayObjObjIdx";
            if ((state_0 & 0x400) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList();
                cached.add(Arrays.asList(this.toIndex));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[11] = s;
            s = new Object[3];
            s[0] = "doGeneric";
            if ((state_0 & 0x800) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList();
                cached.add(Arrays.asList(this.toIndex, this.generic_notSharedArrayBuffer_));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[12] = s;
            return Introspection.Provider.create(data);
        }

        public static AtomicsBuiltins.AtomicsCompareExchangeNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new AtomicsCompareExchangeNodeGen(context, builtin, arguments);
        }
    }
}

