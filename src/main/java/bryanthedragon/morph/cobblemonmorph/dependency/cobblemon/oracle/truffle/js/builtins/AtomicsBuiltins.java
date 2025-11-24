
package com.oracle.truffle.js.builtins;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Bind;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.ImportStatic;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.builtins.AtomicsBuiltinsFactory;
import com.oracle.truffle.js.builtins.JSBuiltinsContainer;
import com.oracle.truffle.js.builtins.helper.SharedMemorySync;
import com.oracle.truffle.js.nodes.access.CreateDataPropertyNode;
import com.oracle.truffle.js.nodes.access.CreateObjectNode;
import com.oracle.truffle.js.nodes.cast.JSToBigIntNode;
import com.oracle.truffle.js.nodes.cast.JSToDoubleNode;
import com.oracle.truffle.js.nodes.cast.JSToIndexNode;
import com.oracle.truffle.js.nodes.cast.JSToInt32Node;
import com.oracle.truffle.js.nodes.cast.JSToIntegerOrInfinityNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.nodes.function.JSBuiltinNode;
import com.oracle.truffle.js.nodes.promise.NewPromiseCapabilityNode;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSAgent;
import com.oracle.truffle.js.runtime.JSAgentWaiterList;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSException;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.SafeInteger;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.array.TypedArray;
import com.oracle.truffle.js.runtime.builtins.BuiltinEnum;
import com.oracle.truffle.js.runtime.builtins.JSArrayBuffer;
import com.oracle.truffle.js.runtime.builtins.JSArrayBufferView;
import com.oracle.truffle.js.runtime.builtins.JSSharedArrayBuffer;
import com.oracle.truffle.js.runtime.builtins.JSTypedArrayObject;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.PromiseCapabilityRecord;
import com.oracle.truffle.js.runtime.objects.Undefined;

public final class AtomicsBuiltins
extends JSBuiltinsContainer.SwitchEnum<Atomics> {
    public static final JSBuiltinsContainer BUILTINS = new AtomicsBuiltins();

    protected AtomicsBuiltins() {
        super(JSRealm.ATOMICS_CLASS_NAME, Atomics.class);
    }

    @Override
    protected Object createNode(JSContext context, JSBuiltin builtin, boolean construct, boolean newTarget, Atomics builtinEnum) {
        assert (context.getEcmaScriptVersion() >= 8);
        switch (builtinEnum) {
            case compareExchange: {
                return AtomicsBuiltinsFactory.AtomicsCompareExchangeNodeGen.create(context, builtin, AtomicsBuiltins.args().fixedArgs(4).createArgumentNodes(context));
            }
            case load: {
                return AtomicsBuiltinsFactory.AtomicsLoadNodeGen.create(context, builtin, AtomicsBuiltins.args().fixedArgs(4).createArgumentNodes(context));
            }
            case store: {
                return AtomicsBuiltinsFactory.AtomicsStoreNodeGen.create(context, builtin, AtomicsBuiltins.args().fixedArgs(3).createArgumentNodes(context));
            }
            case add: {
                return AtomicsBuiltinsFactory.AtomicsComputeNodeGen.create(context, builtin, (a, b) -> a + b, (a, b) -> a.add((BigInt)b), AtomicsBuiltins.args().fixedArgs(3).createArgumentNodes(context));
            }
            case sub: {
                return AtomicsBuiltinsFactory.AtomicsComputeNodeGen.create(context, builtin, (a, b) -> a - b, (a, b) -> a.subtract((BigInt)b), AtomicsBuiltins.args().fixedArgs(3).createArgumentNodes(context));
            }
            case and: {
                return AtomicsBuiltinsFactory.AtomicsComputeNodeGen.create(context, builtin, (a, b) -> a & b, (a, b) -> a.and((BigInt)b), AtomicsBuiltins.args().fixedArgs(3).createArgumentNodes(context));
            }
            case or: {
                return AtomicsBuiltinsFactory.AtomicsComputeNodeGen.create(context, builtin, (a, b) -> a | b, (a, b) -> a.or((BigInt)b), AtomicsBuiltins.args().fixedArgs(3).createArgumentNodes(context));
            }
            case xor: {
                return AtomicsBuiltinsFactory.AtomicsComputeNodeGen.create(context, builtin, (a, b) -> a ^ b, (a, b) -> a.xor((BigInt)b), AtomicsBuiltins.args().fixedArgs(3).createArgumentNodes(context));
            }
            case exchange: {
                return AtomicsBuiltinsFactory.AtomicsComputeNodeGen.create(context, builtin, (a, b) -> b, (a, b) -> b, AtomicsBuiltins.args().fixedArgs(3).createArgumentNodes(context));
            }
            case notify: {
                return AtomicsBuiltinsFactory.AtomicsNotifyNodeGen.create(context, builtin, AtomicsBuiltins.args().fixedArgs(3).createArgumentNodes(context));
            }
            case wait: {
                return AtomicsBuiltinsFactory.AtomicsWaitNodeGen.create(context, builtin, AtomicsBuiltins.args().fixedArgs(4).createArgumentNodes(context));
            }
            case isLockFree: {
                return AtomicsBuiltinsFactory.AtomicsIsLockFreeNodeGen.create(context, builtin, AtomicsBuiltins.args().fixedArgs(1).createArgumentNodes(context));
            }
            case waitAsync: {
                return AtomicsBuiltinsFactory.AtomicsWaitAsyncNodeGen.create(context, builtin, AtomicsBuiltins.args().fixedArgs(4).createArgumentNodes(context));
            }
        }
        return null;
    }

    @FunctionalInterface
    public static interface AtomicBinaryOperator<T> {
        public T apply(T var1, T var2);
    }

    @FunctionalInterface
    public static interface AtomicIntBinaryOperator {
        public int applyAsInt(int var1, int var2);
    }

    public static abstract class AtomicsIsLockFreeNode
    extends AtomicsOperationNode {
        private static final boolean AR_IsLockFree1 = true;
        private static final boolean AR_IsLockFree2 = true;
        private static final boolean AR_IsLockFree8 = true;

        public AtomicsIsLockFreeNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        protected static boolean doInt(int size) {
            if (size == 1) {
                return true;
            }
            if (size == 2) {
                return true;
            }
            if (size == 4) {
                return true;
            }
            return size == 8;
        }

        @Specialization
        protected static boolean doGeneric(Object size, @Cached JSToInt32Node toInt32Node) {
            return AtomicsIsLockFreeNode.doInt(toInt32Node.executeInt(size));
        }
    }

    public static abstract class AtomicsWaitAsyncNode
    extends AtomicsWaitBaseNode {
        public AtomicsWaitAsyncNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        protected Object doGeneric(VirtualFrame frame, Object maybeTarget, Object index, Object value2, Object timeout) {
            return this.doWait(frame, maybeTarget, index, value2, timeout, true);
        }
    }

    public static abstract class AtomicsWaitNode
    extends AtomicsWaitBaseNode {
        public AtomicsWaitNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        protected Object doGeneric(VirtualFrame frame, Object maybeTarget, Object index, Object value2, Object timeout) {
            return this.doWait(frame, maybeTarget, index, value2, timeout, false);
        }
    }

    public static abstract class AtomicsWaitBaseNode
    extends AtomicsOperationNode {
        private final ConditionProfile isAsyncProfile = ConditionProfile.createBinaryProfile();
        private final ConditionProfile timeoutNaNProfile = ConditionProfile.createBinaryProfile();
        private final BranchProfile valuesNotEqualBranch = BranchProfile.create();
        private final BranchProfile asyncImmediateTimeoutBranch = BranchProfile.create();
        private final ConditionProfile awokenProfile = ConditionProfile.createBinaryProfile();
        private final BranchProfile errorBranch = BranchProfile.create();
        private final BranchProfile notSharedArrayBuffer = BranchProfile.create();
        @Node.Child
        private JSToIndexNode toIndexNode = JSToIndexNode.create();
        @Node.Child
        private JSToDoubleNode toDoubleNode = JSToDoubleNode.create();
        @Node.Child
        private AtomicsLoadNode loadNode = this.createHelperNode();
        @Node.Child
        private JSToBigIntNode toBigIntNode;
        @Node.Child
        private JSToInt32Node toInt32Node;
        @Node.Child
        private NewPromiseCapabilityNode newPromiseCapabilityNode;
        @Node.Child
        private CreateObjectNode objectCreateNode;
        @Node.Child
        private CreateDataPropertyNode createAsyncPropertyNode;
        @Node.Child
        private CreateDataPropertyNode createValuePropertyNode;

        public AtomicsWaitBaseNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
            this.createAsyncPropertyNode = CreateDataPropertyNode.create(context, Strings.ASYNC);
            this.createValuePropertyNode = CreateDataPropertyNode.create(context, Strings.VALUE);
        }

        protected AtomicsLoadNode createHelperNode() {
            return AtomicsBuiltinsFactory.AtomicsLoadNodeGen.create(this.getContext(), this.getBuiltin(), JSBuiltinsContainer.args().fixedArgs(4).createArgumentNodes(this.getContext()));
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        protected Object doWait(VirtualFrame frame, Object maybeTarget, Object index, Object value2, Object timeout, boolean isAsync) {
            JSTypedArrayObject target = this.validateTypedArray(maybeTarget);
            this.validateIntegerTypedArray(target, true);
            if (!AtomicsWaitBaseNode.isSharedBufferView(target)) {
                this.notSharedArrayBuffer.enter();
                throw this.createTypeErrorNotSharedArray();
            }
            int i = AtomicsWaitBaseNode.validateAtomicAccess(target, this.toIndexNode.executeLong(index), index);
            boolean isInt32 = AtomicsWaitBaseNode.isInt32SharedBufferView(target);
            long v = isInt32 ? (long)this.toInt32(value2) : this.toBigInt(value2).longValue();
            double q = this.toDoubleNode.executeDouble(timeout);
            double t = this.timeoutNaNProfile.profile(JSRuntime.isNaN(q)) ? Double.POSITIVE_INFINITY : Math.max(q, 0.0);
            JSAgent agent = this.getRealm().getAgent();
            if (!isAsync && !agent.canBlock()) {
                this.errorBranch.enter();
                throw this.createTypeErrorUnsupported();
            }
            JSAgentWaiterList.JSAgentWaiterListEntry wl = SharedMemorySync.getWaiterList(this.getContext(), target, i);
            PromiseCapabilityRecord promiseCapability = null;
            JSDynamicObject resultObject = null;
            if (this.isAsyncProfile.profile(isAsync)) {
                this.getContext().signalAsyncWaiterRecordUsage();
                promiseCapability = this.newPromiseCapability();
                resultObject = this.ordinaryObjectCreate(frame);
            }
            wl.enterCriticalSection();
            try {
                boolean isNotEqual;
                Object w = this.loadNode.executeWithBufferAndIndex(frame, maybeTarget, i);
                boolean bl = isInt32 ? !(w instanceof Integer) || (Integer)w != (int)v : (isNotEqual = !(w instanceof BigInt) || ((BigInt)w).longValue() != v);
                if (isNotEqual) {
                    this.valuesNotEqualBranch.enter();
                    if (!this.isAsyncProfile.profile(isAsync)) {
                        TruffleString truffleString = Strings.NOT_EQUAL;
                        return truffleString;
                    }
                    this.createAsyncPropertyNode.executeVoid(resultObject, false);
                    this.createValuePropertyNode.executeVoid(resultObject, Strings.NOT_EQUAL);
                    JSDynamicObject jSDynamicObject = resultObject;
                    return jSDynamicObject;
                }
                if (isAsync && t == 0.0) {
                    this.asyncImmediateTimeoutBranch.enter();
                    this.createAsyncPropertyNode.executeVoid(resultObject, false);
                    this.createValuePropertyNode.executeVoid(resultObject, Strings.TIMED_OUT);
                    JSDynamicObject jSDynamicObject = resultObject;
                    return jSDynamicObject;
                }
                int id = agent.getSignifier();
                JSAgentWaiterList.WaiterRecord waiterRecord = JSAgentWaiterList.WaiterRecord.create(id, promiseCapability, t, Strings.OK, wl, agent);
                SharedMemorySync.addWaiter(agent, wl, waiterRecord, isAsync);
                if (!this.isAsyncProfile.profile(isAsync)) {
                    boolean awoken = SharedMemorySync.suspendAgent(agent, wl, waiterRecord);
                    if (this.awokenProfile.profile(awoken)) {
                        assert (!wl.contains(waiterRecord));
                        TruffleString truffleString = Strings.OK;
                        return truffleString;
                    }
                    SharedMemorySync.removeWaiter(wl, waiterRecord);
                    TruffleString truffleString = Strings.TIMED_OUT;
                    return truffleString;
                }
                this.createAsyncPropertyNode.executeVoid(resultObject, true);
                this.createValuePropertyNode.executeVoid(resultObject, waiterRecord.getPromiseCapability().getPromise());
                JSDynamicObject jSDynamicObject = resultObject;
                return jSDynamicObject;
            }
            finally {
                wl.leaveCriticalSection();
            }
        }

        private int toInt32(Object v) {
            if (this.toInt32Node == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.toInt32Node = this.insert(JSToInt32Node.create());
            }
            return this.toInt32Node.executeInt(v);
        }

        private BigInt toBigInt(Object v) {
            if (this.toBigIntNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.toBigIntNode = this.insert(JSToBigIntNode.create());
            }
            return this.toBigIntNode.executeBigInteger(v);
        }

        private PromiseCapabilityRecord newPromiseCapability() {
            if (this.newPromiseCapabilityNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.newPromiseCapabilityNode = this.insert(NewPromiseCapabilityNode.create(this.getContext()));
            }
            return this.newPromiseCapabilityNode.executeDefault();
        }

        private JSDynamicObject ordinaryObjectCreate(VirtualFrame frame) {
            if (this.objectCreateNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.objectCreateNode = this.insert(CreateObjectNode.create(this.getContext()));
            }
            return this.objectCreateNode.execute(frame);
        }
    }

    public static abstract class AtomicsNotifyNode
    extends AtomicsOperationNode {
        public AtomicsNotifyNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        protected Object doNotify(Object maybeTarget, Object index, Object count, @Cached JSToIndexNode toIndexNode, @Cached(value="create()") JSToInt32Node toInt32Node, @Cached(value="create()") BranchProfile notSharedArrayBuffer) {
            JSTypedArrayObject target = this.validateTypedArray(maybeTarget);
            this.validateIntegerTypedArray(target, true);
            int i = AtomicsNotifyNode.validateAtomicAccess(target, toIndexNode.executeLong(index), index);
            int c = Integer.MAX_VALUE;
            if (count != Undefined.instance) {
                int tmp = toInt32Node.executeInt(count);
                c = Integer.max(tmp, 0);
            }
            if (!AtomicsNotifyNode.isSharedBufferView(target)) {
                notSharedArrayBuffer.enter();
                return 0;
            }
            JSAgentWaiterList.JSAgentWaiterListEntry wl = SharedMemorySync.getWaiterList(this.getContext(), target, i);
            return AtomicsNotifyNode.notifyWaiters(wl, c);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @CompilerDirectives.TruffleBoundary
        private static Object notifyWaiters(JSAgentWaiterList.JSAgentWaiterListEntry wl, int c) {
            wl.enterCriticalSection();
            try {
                int n;
                boolean wake = false;
                JSAgentWaiterList.WaiterRecord[] waiters = SharedMemorySync.removeWaiters(wl, c);
                for (n = 0; n < waiters.length; ++n) {
                    JSAgentWaiterList.WaiterRecord waiterRecord = waiters[n];
                    waiterRecord.setNotified();
                    if (waiterRecord.getPromiseCapability() == null) {
                        wake = true;
                        continue;
                    }
                    if (!Double.isInfinite(waiterRecord.getTimeout())) continue;
                    waiterRecord.enqueueInAgent();
                }
                if (wake) {
                    SharedMemorySync.wakeWaiters(wl);
                }
                Integer n2 = n;
                return n2;
            }
            finally {
                wl.leaveCriticalSection();
            }
        }
    }

    public static abstract class AtomicsComputeNode
    extends AtomicsOperationNode {
        private final AtomicIntBinaryOperator intOperator;
        private final AtomicBinaryOperator<BigInt> bigIntOperator;
        @Node.Child
        private JSToBigIntNode toBigIntNode;
        @Node.Child
        private JSToInt32Node toIntNode;

        public AtomicsComputeNode(JSContext context, JSBuiltin builtin, AtomicIntBinaryOperator intOperator, AtomicBinaryOperator<BigInt> bigIntOperator) {
            super(context, builtin);
            this.intOperator = intOperator;
            this.bigIntOperator = bigIntOperator;
        }

        private int atomicDoInt(JSTypedArrayObject target, int index, int value2, TypedArray.TypedIntArray typedArray) {
            int result;
            int initial;
            while (!SharedMemorySync.compareAndSetInt(target, index, initial = SharedMemorySync.doVolatileGet(target, index, typedArray), result = this.intOperator.applyAsInt(initial, value2), typedArray)) {
            }
            return initial;
        }

        private BigInt atomicDoBigInt(JSTypedArrayObject target, int index, BigInt value2, TypedArray.TypedBigIntArray typedArray) {
            BigInt result;
            BigInt initial;
            while (!SharedMemorySync.compareAndSetBigInt(target, index, initial = SharedMemorySync.doVolatileGetBigInt(target, index, typedArray), result = this.bigIntOperator.apply(initial, value2), typedArray)) {
            }
            return initial;
        }

        @CompilerDirectives.TruffleBoundary
        private int nonAtomicDoInt(JSTypedArrayObject target, int index, int value2, TypedArray.TypedIntArray typedArray) {
            int initial = typedArray.getInt(target, index, InteropLibrary.getUncached());
            int result = this.intOperator.applyAsInt(initial, value2);
            typedArray.setInt(target, index, result, InteropLibrary.getUncached());
            return initial;
        }

        @CompilerDirectives.TruffleBoundary
        private BigInt nonAtomicDoBigInt(JSTypedArrayObject target, int index, BigInt value2, TypedArray.TypedBigIntArray typedArray) {
            BigInt initial = typedArray.getBigInt(target, index, InteropLibrary.getUncached());
            BigInt result = this.bigIntOperator.apply(initial, value2);
            typedArray.setBigInt(target, index, result, InteropLibrary.getUncached());
            return initial;
        }

        @Specialization(guards={"isSharedBufferView(target)", "isDirectInt8Array(ta)", "ta.isInBoundsFast(target, index)"})
        protected int doSharedInt8Array(JSTypedArrayObject target, int index, int value2, @Bind(value="typedArrayGetArrayType(target)") TypedArray ta) {
            return (byte)this.atomicDoInt(target, index, value2, (TypedArray.DirectInt8Array)ta);
        }

        @Specialization(guards={"isSharedBufferView(target)", "isDirectUint8Array(ta)", "ta.isInBoundsFast(target, index)"})
        protected int doSharedUint8Array(JSTypedArrayObject target, int index, int value2, @Bind(value="typedArrayGetArrayType(target)") TypedArray ta) {
            return this.atomicDoInt(target, index, value2, (TypedArray.DirectUint8Array)ta) & 0xFF;
        }

        @Specialization(guards={"isSharedBufferView(target)", "isDirectInt16Array(ta)", "ta.isInBoundsFast(target, index)"})
        protected int doSharedInt16Array(JSTypedArrayObject target, int index, int value2, @Bind(value="typedArrayGetArrayType(target)") TypedArray ta) {
            return (short)this.atomicDoInt(target, index, value2, (TypedArray.DirectInt16Array)ta);
        }

        @Specialization(guards={"isSharedBufferView(target)", "isDirectUint16Array(ta)", "ta.isInBoundsFast(target, index)"})
        protected int doSharedUint16Array(JSTypedArrayObject target, int index, int value2, @Bind(value="typedArrayGetArrayType(target)") TypedArray ta) {
            return this.atomicDoInt(target, index, value2, (TypedArray.DirectUint16Array)ta) & 0xFFFF;
        }

        @Specialization(guards={"isSharedBufferView(target)", "isDirectInt32Array(ta)", "ta.isInBoundsFast(target, index)"})
        protected int doSharedInt32Array(JSTypedArrayObject target, int index, int value2, @Bind(value="typedArrayGetArrayType(target)") TypedArray ta) {
            return this.atomicDoInt(target, index, value2, (TypedArray.DirectInt32Array)ta);
        }

        @Specialization(guards={"isSharedBufferView(target)", "isDirectUint32Array(ta)", "ta.isInBoundsFast(target, index)"})
        protected SafeInteger doSharedUint32Array(JSTypedArrayObject target, int index, int value2, @Bind(value="typedArrayGetArrayType(target)") TypedArray ta) {
            return SafeInteger.valueOf((long)this.atomicDoInt(target, index, value2, (TypedArray.DirectUint32Array)ta) & 0xFFFFFFFFL);
        }

        @Specialization(guards={"isSharedBufferView(target)", "isDirectInt32Array(ta)"})
        protected int doSharedInt32ArrayObjIdx(JSTypedArrayObject target, Object index, int value2, @Bind(value="typedArrayGetArrayType(target)") TypedArray ta, @Cached @Cached.Shared(value="toIndex") JSToIndexNode toIndexNode) {
            int intIndex = AtomicsComputeNode.validateAtomicAccess(target, toIndexNode.executeLong(index), index);
            return this.atomicDoInt(target, intIndex, value2, (TypedArray.DirectInt32Array)ta);
        }

        @Specialization(guards={"isSharedBufferView(target)", "isDirectBigInt64Array(ta)"})
        protected BigInt doSharedBigInt64Array(JSTypedArrayObject target, Object index, Object value2, @Bind(value="typedArrayGetArrayType(target)") TypedArray ta, @Cached @Cached.Shared(value="toIndex") JSToIndexNode toIndexNode) {
            int intIndex = AtomicsComputeNode.validateAtomicAccess(target, toIndexNode.executeLong(index), index);
            return this.atomicDoBigInt(target, intIndex, this.toBigInt(value2), (TypedArray.DirectBigInt64Array)ta);
        }

        @Specialization(guards={"isSharedBufferView(target)", "isDirectBigUint64Array(ta)"})
        protected BigInt doSharedBigUint64Array(JSTypedArrayObject target, Object index, Object value2, @Bind(value="typedArrayGetArrayType(target)") TypedArray ta, @Cached @Cached.Shared(value="toIndex") JSToIndexNode toIndexNode) {
            int intIndex = AtomicsComputeNode.validateAtomicAccess(target, toIndexNode.executeLong(index), index);
            return this.atomicDoBigInt(target, intIndex, this.toBigInt(value2), (TypedArray.DirectBigUint64Array)ta);
        }

        @Specialization
        protected Object doGeneric(Object maybeTarget, Object index, Object value2, @Cached @Cached.Shared(value="toIndex") JSToIndexNode toIndexNode, @Cached BranchProfile notSharedArrayBuffer) {
            JSTypedArrayObject target = this.validateTypedArray(maybeTarget);
            TypedArray ta = this.validateIntegerTypedArray(target, false);
            int intIndex = AtomicsComputeNode.validateAtomicAccess(target, toIndexNode.executeLong(index), index);
            if (!AtomicsComputeNode.isSharedBufferView(target)) {
                notSharedArrayBuffer.enter();
                if (ta instanceof TypedArray.DirectInt8Array || ta instanceof TypedArray.Int8Array || ta instanceof TypedArray.InteropInt8Array) {
                    return (int)((byte)this.nonAtomicDoInt(target, intIndex, this.toIntChecked(value2, target), (TypedArray.TypedIntArray)ta));
                }
                if (ta instanceof TypedArray.DirectUint8Array || ta instanceof TypedArray.Uint8Array || ta instanceof TypedArray.InteropUint8Array) {
                    return this.nonAtomicDoInt(target, intIndex, this.toIntChecked(value2, target), (TypedArray.TypedIntArray)ta) & 0xFF;
                }
                if (ta instanceof TypedArray.DirectInt16Array || ta instanceof TypedArray.Int16Array || ta instanceof TypedArray.InteropInt16Array) {
                    return this.nonAtomicDoInt(target, intIndex, this.toIntChecked(value2, target), (TypedArray.TypedIntArray)ta);
                }
                if (ta instanceof TypedArray.DirectUint16Array || ta instanceof TypedArray.Uint16Array || ta instanceof TypedArray.InteropUint16Array) {
                    return this.nonAtomicDoInt(target, intIndex, this.toIntChecked(value2, target), (TypedArray.TypedIntArray)ta) & 0xFFFF;
                }
                if (ta instanceof TypedArray.DirectInt32Array || ta instanceof TypedArray.Int32Array || ta instanceof TypedArray.InteropInt32Array) {
                    return this.nonAtomicDoInt(target, intIndex, this.toIntChecked(value2, target), (TypedArray.TypedIntArray)ta);
                }
                if (ta instanceof TypedArray.DirectUint32Array || ta instanceof TypedArray.Uint32Array || ta instanceof TypedArray.InteropUint32Array) {
                    return SafeInteger.valueOf((long)this.nonAtomicDoInt(target, intIndex, this.toIntChecked(value2, target), (TypedArray.TypedIntArray)ta) & 0xFFFFFFFFL);
                }
                if (ta instanceof TypedArray.DirectBigInt64Array || ta instanceof TypedArray.DirectBigUint64Array || ta instanceof TypedArray.BigInt64Array || ta instanceof TypedArray.BigUint64Array || ta instanceof TypedArray.InteropBigInt64Array || ta instanceof TypedArray.InteropBigUint64Array) {
                    return this.nonAtomicDoBigInt(target, intIndex, this.toBigIntChecked(value2, target), (TypedArray.TypedBigIntArray)ta);
                }
                throw Errors.shouldNotReachHere();
            }
            if (ta instanceof TypedArray.DirectInt8Array || ta instanceof TypedArray.Int8Array) {
                return (int)((byte)this.atomicDoInt(target, intIndex, this.toInt(value2), (TypedArray.TypedIntArray)ta));
            }
            if (ta instanceof TypedArray.DirectUint8Array || ta instanceof TypedArray.Uint8Array) {
                return this.atomicDoInt(target, intIndex, this.toInt(value2), (TypedArray.TypedIntArray)ta) & 0xFF;
            }
            if (ta instanceof TypedArray.DirectInt16Array || ta instanceof TypedArray.Int16Array) {
                return (int)((short)this.atomicDoInt(target, intIndex, this.toInt(value2), (TypedArray.TypedIntArray)ta));
            }
            if (ta instanceof TypedArray.DirectUint16Array || ta instanceof TypedArray.Uint16Array) {
                return this.atomicDoInt(target, intIndex, this.toInt(value2), (TypedArray.TypedIntArray)ta) & 0xFFFF;
            }
            if (ta instanceof TypedArray.DirectInt32Array || ta instanceof TypedArray.Int32Array) {
                return this.atomicDoInt(target, intIndex, this.toInt(value2), (TypedArray.TypedIntArray)ta);
            }
            if (ta instanceof TypedArray.DirectUint32Array || ta instanceof TypedArray.Uint32Array) {
                return SafeInteger.valueOf((long)this.atomicDoInt(target, intIndex, this.toInt(value2), (TypedArray.TypedIntArray)ta) & 0xFFFFFFFFL);
            }
            if (ta instanceof TypedArray.DirectBigInt64Array || ta instanceof TypedArray.DirectBigUint64Array || ta instanceof TypedArray.BigInt64Array || ta instanceof TypedArray.BigUint64Array) {
                return this.atomicDoBigInt(target, intIndex, this.toBigInt(value2), (TypedArray.TypedBigIntArray)ta);
            }
            throw Errors.shouldNotReachHere();
        }

        private int toIntChecked(Object v, JSTypedArrayObject target) {
            int value2 = this.toInt(v);
            this.checkDetached(target);
            return value2;
        }

        private int toInt(Object v) {
            if (this.toIntNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.toIntNode = this.insert(JSToInt32Node.create());
            }
            return this.toIntNode.executeInt(v);
        }

        private BigInt toBigIntChecked(Object v, JSTypedArrayObject target) {
            BigInt result = this.toBigInt(v);
            this.checkDetached(target);
            return result;
        }

        private BigInt toBigInt(Object v) {
            if (this.toBigIntNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.toBigIntNode = this.insert(JSToBigIntNode.create());
            }
            return this.toBigIntNode.executeBigInteger(v);
        }
    }

    public static abstract class AtomicsStoreNode
    extends AtomicsOperationNode {
        @Node.Child
        private JSToInt32Node toIntNode;
        @Node.Child
        private JSToBigIntNode toBigIntNode;
        @Node.Child
        private JSToIntegerOrInfinityNode toIntOrInfNode;

        public AtomicsStoreNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization(guards={"isSharedBufferView(target)", "isDirectInt8Array(ta)", "ta.isInBoundsFast(target, index)"})
        protected int doSharedInt8Array(JSTypedArrayObject target, int index, int value2, @Bind(value="typedArrayGetArrayType(target)") TypedArray ta) {
            SharedMemorySync.doVolatilePut(target, index, value2, (TypedArray.DirectInt8Array)ta);
            return value2;
        }

        @Specialization(guards={"isSharedBufferView(target)", "isDirectUint8Array(ta)", "ta.isInBoundsFast(target, index)"})
        protected int doSharedUint8Array(JSTypedArrayObject target, int index, int value2, @Bind(value="typedArrayGetArrayType(target)") TypedArray ta) {
            SharedMemorySync.doVolatilePut(target, index, value2, (TypedArray.DirectUint8Array)ta);
            return value2;
        }

        @Specialization(guards={"isSharedBufferView(target)", "isDirectInt8Array(ta)", "ta.isInBoundsFast(target, index)"})
        protected Number doSharedInt8Array(JSTypedArrayObject target, int index, Object value2, @Bind(value="typedArrayGetArrayType(target)") TypedArray ta) {
            Number v = this.toIntegerOrInfinity(value2);
            SharedMemorySync.doVolatilePut(target, index, this.toRaw(v), (TypedArray.DirectInt8Array)ta);
            return v;
        }

        @Specialization(guards={"isSharedBufferView(target)", "isDirectUint8Array(ta)", "ta.isInBoundsFast(target, index)"})
        protected Number doSharedUint8Array(JSTypedArrayObject target, int index, Object value2, @Bind(value="typedArrayGetArrayType(target)") TypedArray ta) {
            Number v = this.toIntegerOrInfinity(value2);
            SharedMemorySync.doVolatilePut(target, index, this.toRaw(v), (TypedArray.DirectUint8Array)ta);
            return v;
        }

        @Specialization(guards={"isSharedBufferView(target)", "isDirectInt16Array(ta)", "ta.isInBoundsFast(target, index)"})
        protected Object doSharedInt16Array(JSTypedArrayObject target, int index, int value2, @Bind(value="typedArrayGetArrayType(target)") TypedArray ta) {
            SharedMemorySync.doVolatilePut(target, index, (short)value2, (TypedArray.DirectInt16Array)ta);
            return value2;
        }

        @Specialization(guards={"isSharedBufferView(target)", "isDirectUint16Array(ta)", "ta.isInBoundsFast(target, index)"})
        protected Object doSharedUint16Array(JSTypedArrayObject target, int index, int value2, @Bind(value="typedArrayGetArrayType(target)") TypedArray ta) {
            SharedMemorySync.doVolatilePut(target, index, (short)value2, (TypedArray.DirectUint16Array)ta);
            return value2;
        }

        @Specialization(guards={"isSharedBufferView(target)", "isDirectInt16Array(ta)", "ta.isInBoundsFast(target, index)"})
        protected Number doSharedInt16Array(JSTypedArrayObject target, int index, Object value2, @Bind(value="typedArrayGetArrayType(target)") TypedArray ta) {
            Number v = this.toIntegerOrInfinity(value2);
            SharedMemorySync.doVolatilePut(target, index, this.toRaw(v), (TypedArray.DirectInt16Array)ta);
            return v;
        }

        @Specialization(guards={"isSharedBufferView(target)", "isDirectUint16Array(ta)", "ta.isInBoundsFast(target, index)"})
        protected Number doSharedUint16Array(JSTypedArrayObject target, int index, Object value2, @Bind(value="typedArrayGetArrayType(target)") TypedArray ta) {
            Number v = this.toIntegerOrInfinity(value2);
            SharedMemorySync.doVolatilePut(target, index, this.toRaw(v), (TypedArray.DirectUint16Array)ta);
            return v;
        }

        @Specialization(guards={"isSharedBufferView(target)", "isDirectInt32Array(ta)", "ta.isInBoundsFast(target, index)"})
        protected int doSharedInt32Array(JSTypedArrayObject target, int index, int value2, @Bind(value="typedArrayGetArrayType(target)") TypedArray ta) {
            SharedMemorySync.doVolatilePut(target, index, value2, (TypedArray.DirectInt32Array)ta);
            return value2;
        }

        @Specialization(guards={"isSharedBufferView(target)", "isDirectUint32Array(ta)", "ta.isInBoundsFast(target, index)"})
        protected int doSharedUint32Array(JSTypedArrayObject target, int index, int value2, @Bind(value="typedArrayGetArrayType(target)") TypedArray ta) {
            SharedMemorySync.doVolatilePut(target, index, value2, (TypedArray.DirectUint32Array)ta);
            return value2;
        }

        @Specialization(guards={"isSharedBufferView(target)", "isDirectInt32Array(ta)", "ta.isInBoundsFast(target, index)"})
        protected Object doSharedInt32Array(JSTypedArrayObject target, int index, Object value2, @Bind(value="typedArrayGetArrayType(target)") TypedArray ta) {
            Number v = this.toIntegerOrInfinity(value2);
            SharedMemorySync.doVolatilePut(target, index, this.toRaw(v), (TypedArray.DirectInt32Array)ta);
            return v;
        }

        @Specialization(guards={"isSharedBufferView(target)", "isDirectUint32Array(ta)", "ta.isInBoundsFast(target, index)"})
        protected Object doSharedUint32Array(JSTypedArrayObject target, int index, Object value2, @Bind(value="typedArrayGetArrayType(target)") TypedArray ta) {
            Number v = this.toIntegerOrInfinity(value2);
            SharedMemorySync.doVolatilePut(target, index, this.toRaw(v), (TypedArray.DirectUint32Array)ta);
            return v;
        }

        @Specialization(guards={"isSharedBufferView(target)", "isDirectInt32Array(ta)"})
        protected Object doSharedInt32ArrayObjIdx(JSTypedArrayObject target, Object index, int value2, @Bind(value="typedArrayGetArrayType(target)") TypedArray ta, @Cached @Cached.Shared(value="toIndex") JSToIndexNode toIndexNode) {
            int intIndex = AtomicsStoreNode.validateAtomicAccess(target, toIndexNode.executeLong(index), index);
            SharedMemorySync.doVolatilePut(target, intIndex, value2, (TypedArray.DirectInt32Array)ta);
            return value2;
        }

        @Specialization(guards={"isSharedBufferView(target)", "isDirectBigInt64Array(ta)"})
        protected Object doSharedBigInt64Array(JSTypedArrayObject target, Object index, Object value2, @Bind(value="typedArrayGetArrayType(target)") TypedArray ta, @Cached @Cached.Shared(value="toIndex") JSToIndexNode toIndexNode) {
            int intIndex = AtomicsStoreNode.validateAtomicAccess(target, toIndexNode.executeLong(index), index);
            BigInt biValue = this.toBigInt(value2, target);
            SharedMemorySync.doVolatilePutBigInt(target, intIndex, biValue, (TypedArray.DirectBigInt64Array)ta);
            return biValue;
        }

        @Specialization(guards={"isSharedBufferView(target)", "isDirectBigUint64Array(ta)"})
        protected Object doSharedBigUint64Array(JSTypedArrayObject target, Object index, Object value2, @Bind(value="typedArrayGetArrayType(target)") TypedArray ta, @Cached @Cached.Shared(value="toIndex") JSToIndexNode toIndexNode) {
            int intIndex = AtomicsStoreNode.validateAtomicAccess(target, toIndexNode.executeLong(index), index);
            BigInt biValue = this.toBigInt(value2, target);
            SharedMemorySync.doVolatilePutBigInt(target, intIndex, biValue, (TypedArray.DirectBigUint64Array)ta);
            return biValue;
        }

        @Specialization
        protected Object doGeneric(Object maybeTarget, Object index, Object value2, @Cached @Cached.Shared(value="toIndex") JSToIndexNode toIndexNode) {
            JSTypedArrayObject target = this.validateTypedArray(maybeTarget);
            TypedArray ta = this.validateIntegerTypedArray(target, false);
            int intIndex = AtomicsStoreNode.validateAtomicAccess(target, toIndexNode.executeLong(index), index);
            if (ta instanceof TypedArray.DirectInt8Array || ta instanceof TypedArray.DirectUint8Array || ta instanceof TypedArray.Int8Array || ta instanceof TypedArray.Uint8Array || ta instanceof TypedArray.InteropInt8Array || ta instanceof TypedArray.InteropUint8Array) {
                Number v = this.toIntegerOrInfinityChecked(value2, target);
                SharedMemorySync.doVolatilePut(target, intIndex, this.toRaw(v), (TypedArray.TypedIntArray)ta);
                return v;
            }
            if (ta instanceof TypedArray.DirectInt16Array || ta instanceof TypedArray.DirectUint16Array || ta instanceof TypedArray.Int16Array || ta instanceof TypedArray.Uint16Array || ta instanceof TypedArray.InteropInt16Array || ta instanceof TypedArray.InteropUint16Array) {
                Number v = this.toIntegerOrInfinityChecked(value2, target);
                SharedMemorySync.doVolatilePut(target, intIndex, (short)this.toRaw(v), (TypedArray.TypedIntArray)ta);
                return v;
            }
            if (ta instanceof TypedArray.DirectInt32Array || ta instanceof TypedArray.DirectUint32Array || ta instanceof TypedArray.Int32Array || ta instanceof TypedArray.Uint32Array || ta instanceof TypedArray.InteropInt32Array || ta instanceof TypedArray.InteropUint32Array) {
                Number v = this.toIntegerOrInfinityChecked(value2, target);
                SharedMemorySync.doVolatilePut(target, intIndex, this.toRaw(v), (TypedArray.TypedIntArray)ta);
                return v;
            }
            if (ta instanceof TypedArray.DirectBigInt64Array || ta instanceof TypedArray.DirectBigUint64Array || ta instanceof TypedArray.BigInt64Array || ta instanceof TypedArray.BigUint64Array || ta instanceof TypedArray.InteropBigInt64Array || ta instanceof TypedArray.InteropBigUint64Array) {
                BigInt v = this.toBigInt(value2, target);
                SharedMemorySync.doVolatilePutBigInt(target, intIndex, v, (TypedArray.TypedBigIntArray)ta);
                return v;
            }
            throw Errors.shouldNotReachHere();
        }

        private Number toIntegerOrInfinity(Object value2) {
            if (this.toIntOrInfNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.toIntOrInfNode = this.insert(JSToIntegerOrInfinityNode.create());
            }
            return this.toIntOrInfNode.executeNumber(value2);
        }

        private Number toIntegerOrInfinityChecked(Object value2, JSTypedArrayObject target) {
            Number result = this.toIntegerOrInfinity(value2);
            this.checkDetached(target);
            return result;
        }

        private int toRaw(Object v) {
            if (this.toIntNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.toIntNode = this.insert(JSToInt32Node.create());
            }
            return this.toIntNode.executeInt(v);
        }

        private BigInt toBigInt(Object v, JSTypedArrayObject target) {
            if (this.toBigIntNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.toBigIntNode = this.insert(JSToBigIntNode.create());
            }
            BigInt result = this.toBigIntNode.executeBigInteger(v);
            this.checkDetached(target);
            return result;
        }
    }

    public static abstract class AtomicsLoadNode
    extends AtomicsOperationNode {
        public AtomicsLoadNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        public abstract Object executeWithBufferAndIndex(VirtualFrame var1, Object var2, Object var3);

        @Specialization(guards={"isSharedBufferView(target)", "isDirectInt8Array(ta)", "ta.isInBoundsFast(target, index)"})
        protected int doInt8ArrayObj(JSTypedArrayObject target, int index, @Bind(value="typedArrayGetArrayType(target)") TypedArray ta) {
            return SharedMemorySync.doVolatileGet(target, index, (TypedArray.DirectInt8Array)ta);
        }

        @Specialization(guards={"isSharedBufferView(target)", "isDirectUint8Array(ta)", "ta.isInBoundsFast(target, index)"})
        protected int doUint8ArrayObj(JSTypedArrayObject target, int index, @Bind(value="typedArrayGetArrayType(target)") TypedArray ta) {
            return SharedMemorySync.doVolatileGet(target, index, (TypedArray.DirectUint8Array)ta) & 0xFF;
        }

        @Specialization(guards={"isSharedBufferView(target)", "isDirectInt16Array(ta)", "ta.isInBoundsFast(target, index)"})
        protected int doInt16ArrayObj(JSTypedArrayObject target, int index, @Bind(value="typedArrayGetArrayType(target)") TypedArray ta) {
            return SharedMemorySync.doVolatileGet(target, index, (TypedArray.DirectInt16Array)ta);
        }

        @Specialization(guards={"isSharedBufferView(target)", "isDirectUint16Array(ta)", "ta.isInBoundsFast(target, index)"})
        protected int doUint16ArrayObj(JSTypedArrayObject target, int index, @Bind(value="typedArrayGetArrayType(target)") TypedArray ta) {
            return SharedMemorySync.doVolatileGet(target, index, (TypedArray.DirectUint16Array)ta) & 0xFFFF;
        }

        @Specialization(guards={"isSharedBufferView(target)", "isDirectInt32Array(ta)", "ta.isInBoundsFast(target, index)"})
        protected int doInt32ArrayObj(JSTypedArrayObject target, int index, @Bind(value="typedArrayGetArrayType(target)") TypedArray ta) {
            return SharedMemorySync.doVolatileGet(target, index, (TypedArray.DirectInt32Array)ta);
        }

        @Specialization(guards={"isSharedBufferView(target)", "isDirectUint32Array(ta)", "ta.isInBoundsFast(target, index)"})
        protected SafeInteger doUint32ArrayObj(JSTypedArrayObject target, int index, @Bind(value="typedArrayGetArrayType(target)") TypedArray ta) {
            return SafeInteger.valueOf((long)SharedMemorySync.doVolatileGet(target, index, (TypedArray.DirectUint32Array)ta) & 0xFFFFFFFFL);
        }

        @Specialization(guards={"isSharedBufferView(target)", "isDirectBigInt64Array(ta)", "ta.isInBoundsFast(target, index)"})
        protected BigInt doBigInt64ArrayObj(JSTypedArrayObject target, int index, @Bind(value="typedArrayGetArrayType(target)") TypedArray ta) {
            return SharedMemorySync.doVolatileGetBigInt(target, index, (TypedArray.DirectBigInt64Array)ta);
        }

        @Specialization(guards={"isSharedBufferView(target)", "isDirectBigUint64Array(ta)", "ta.isInBoundsFast(target, index)"})
        protected BigInt doBigUint64ArrayObj(JSTypedArrayObject target, int index, @Bind(value="typedArrayGetArrayType(target)") TypedArray ta) {
            return SharedMemorySync.doVolatileGetBigInt(target, index, (TypedArray.DirectBigUint64Array)ta);
        }

        @Specialization(guards={"isSharedBufferView(target)", "isDirectInt32Array(ta)"})
        protected int doInt32ArrayObjObjIdx(JSTypedArrayObject target, Object index, @Bind(value="typedArrayGetArrayType(target)") TypedArray ta, @Cached @Cached.Shared(value="toIndex") JSToIndexNode toIndexNode) {
            int intIndex = AtomicsLoadNode.validateAtomicAccess(target, toIndexNode.executeLong(index), index);
            return SharedMemorySync.doVolatileGet(target, intIndex, (TypedArray.DirectInt32Array)ta);
        }

        @Specialization
        protected Object doGeneric(Object maybeTarget, Object index, @Cached @Cached.Shared(value="toIndex") JSToIndexNode toIndexNode) {
            JSTypedArrayObject target = this.validateTypedArray(maybeTarget);
            TypedArray ta = this.validateIntegerTypedArray(target, false);
            int intIndex = AtomicsLoadNode.validateAtomicAccess(target, toIndexNode.executeLong(index), index);
            this.checkDetached(target);
            if (ta instanceof TypedArray.DirectInt8Array || ta instanceof TypedArray.Int8Array || ta instanceof TypedArray.InteropInt8Array) {
                return SharedMemorySync.doVolatileGet(target, intIndex, (TypedArray.TypedIntArray)ta);
            }
            if (ta instanceof TypedArray.DirectUint8Array || ta instanceof TypedArray.Uint8Array || ta instanceof TypedArray.InteropUint8Array) {
                return SharedMemorySync.doVolatileGet(target, intIndex, (TypedArray.TypedIntArray)ta) & 0xFF;
            }
            if (ta instanceof TypedArray.DirectInt16Array || ta instanceof TypedArray.Int16Array || ta instanceof TypedArray.InteropInt16Array) {
                return SharedMemorySync.doVolatileGet(target, intIndex, (TypedArray.TypedIntArray)ta);
            }
            if (ta instanceof TypedArray.DirectUint16Array || ta instanceof TypedArray.Uint16Array || ta instanceof TypedArray.InteropUint16Array) {
                return SharedMemorySync.doVolatileGet(target, intIndex, (TypedArray.TypedIntArray)ta) & 0xFFFF;
            }
            if (ta instanceof TypedArray.DirectInt32Array || ta instanceof TypedArray.Int32Array || ta instanceof TypedArray.InteropInt32Array) {
                return SharedMemorySync.doVolatileGet(target, intIndex, (TypedArray.TypedIntArray)ta);
            }
            if (ta instanceof TypedArray.DirectUint32Array || ta instanceof TypedArray.Uint32Array || ta instanceof TypedArray.InteropUint32Array) {
                return SafeInteger.valueOf((long)SharedMemorySync.doVolatileGet(target, intIndex, (TypedArray.TypedIntArray)ta) & 0xFFFFFFFFL);
            }
            if (ta instanceof TypedArray.DirectBigInt64Array || ta instanceof TypedArray.BigInt64Array || ta instanceof TypedArray.InteropBigInt64Array) {
                return SharedMemorySync.doVolatileGetBigInt(target, intIndex, (TypedArray.TypedBigIntArray)ta);
            }
            if (ta instanceof TypedArray.DirectBigUint64Array || ta instanceof TypedArray.BigUint64Array || ta instanceof TypedArray.InteropBigUint64Array) {
                return SharedMemorySync.doVolatileGetBigInt(target, intIndex, (TypedArray.TypedBigIntArray)ta);
            }
            throw Errors.shouldNotReachHere();
        }
    }

    public static abstract class AtomicsCompareExchangeNode
    extends AtomicsOperationNode {
        @Node.Child
        private JSToBigIntNode toBigIntNode;
        @Node.Child
        private JSToInt32Node toIntNode;

        public AtomicsCompareExchangeNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        protected Object doCASUint32(JSTypedArrayObject target, int index, Object expected, Object replacement, TypedArray.TypedIntArray typedArray) {
            return SafeInteger.valueOf(JSRuntime.toUInt32(typedArray.compareExchangeInt(target, index, (int)JSRuntime.toUInt32(expected), (int)JSRuntime.toUInt32(replacement))));
        }

        protected int doCASInt(JSTypedArrayObject target, int index, int expected, int replacement, TypedArray.TypedIntArray typedArray) {
            return typedArray.compareExchangeInt(target, index, expected, replacement);
        }

        protected BigInt doCASBigInt(JSTypedArrayObject target, int index, BigInt expected, BigInt replacement, TypedArray.TypedBigIntArray typedArray) {
            return typedArray.compareExchangeBigInt(target, index, expected, replacement);
        }

        @CompilerDirectives.TruffleBoundary
        protected static int doInt8(JSTypedArrayObject target, int index, int expected, int replacement, boolean signed, TypedArray.TypedIntArray typedArray) {
            byte expectedChopped;
            int read2 = typedArray.getInt(target, index, InteropLibrary.getUncached());
            read2 = signed ? (byte)read2 : (byte)(read2 & 0xFF);
            byte by = expectedChopped = signed ? (byte)expected : (byte)(expected & 0xFF);
            if (read2 == expectedChopped) {
                byte signedValue = signed ? (byte)replacement : (byte)(replacement & 0xFF);
                typedArray.setInt(target, index, signedValue, InteropLibrary.getUncached());
            }
            return read2;
        }

        @CompilerDirectives.TruffleBoundary
        protected static int doInt16(JSTypedArrayObject target, int index, int expected, int replacement, boolean signed, TypedArray.TypedIntArray typedArray) {
            short expectedChopped;
            int read2 = typedArray.getInt(target, index, InteropLibrary.getUncached());
            read2 = signed ? (short)read2 : (short)(read2 & 0xFFFF);
            short s = expectedChopped = signed ? (short)expected : (short)(expected & 0xFFFF);
            if (read2 == expectedChopped) {
                short signedValue = signed ? (short)replacement : (short)(replacement & 0xFFFF);
                typedArray.setInt(target, index, signedValue, InteropLibrary.getUncached());
            }
            return read2;
        }

        @CompilerDirectives.TruffleBoundary
        protected static Object doUint32(JSTypedArrayObject target, int index, Object expected, Object replacement, TypedArray.TypedIntArray typedArray) {
            long read2 = JSRuntime.toUInt32(typedArray.getInt(target, index, InteropLibrary.getUncached()));
            if (read2 == JSRuntime.toUInt32(expected)) {
                typedArray.setInt(target, index, (int)JSRuntime.toUInt32(replacement), InteropLibrary.getUncached());
            }
            return SafeInteger.valueOf(read2);
        }

        @CompilerDirectives.TruffleBoundary
        protected static int doInt(JSTypedArrayObject target, int index, int expected, int replacement, TypedArray.TypedIntArray typedArray) {
            int read2 = typedArray.getInt(target, index, InteropLibrary.getUncached());
            if (read2 == expected) {
                typedArray.setInt(target, index, replacement, InteropLibrary.getUncached());
            }
            return read2;
        }

        @CompilerDirectives.TruffleBoundary
        protected static BigInt doBigInt(JSTypedArrayObject target, int index, BigInt expected, BigInt replacement, TypedArray.TypedBigIntArray typedArray) {
            BigInt read2 = typedArray.getBigInt(target, index, InteropLibrary.getUncached());
            if (read2.compareTo(expected) == 0) {
                typedArray.setBigInt(target, index, replacement, InteropLibrary.getUncached());
            }
            return read2;
        }

        @Specialization(guards={"isSharedBufferView(target)", "isDirectInt8Array(ta)", "ta.isInBoundsFast(target, index)"})
        protected int doInt8Array(JSTypedArrayObject target, int index, int expected, int replacement, @Bind(value="typedArrayGetArrayType(target)") TypedArray ta) {
            return (byte)this.doCASInt(target, index, expected, replacement, (TypedArray.DirectInt8Array)ta);
        }

        @Specialization(guards={"isSharedBufferView(target)", "isDirectUint8Array(ta)", "ta.isInBoundsFast(target, index)"})
        protected int doUint8Array(JSTypedArrayObject target, int index, int expected, int replacement, @Bind(value="typedArrayGetArrayType(target)") TypedArray ta) {
            return this.doCASInt(target, index, expected, replacement, (TypedArray.DirectUint8Array)ta) & 0xFF;
        }

        @Specialization(guards={"isSharedBufferView(target)", "isDirectInt16Array(ta)", "ta.isInBoundsFast(target, index)"})
        protected int doInt16Array(JSTypedArrayObject target, int index, int expected, int replacement, @Bind(value="typedArrayGetArrayType(target)") TypedArray ta) {
            return (short)this.doCASInt(target, index, expected, replacement, (TypedArray.DirectInt16Array)ta);
        }

        @Specialization(guards={"isSharedBufferView(target)", "isDirectUint16Array(ta)", "ta.isInBoundsFast(target, index)"})
        protected int doUint16Array(JSTypedArrayObject target, int index, int expected, int replacement, @Bind(value="typedArrayGetArrayType(target)") TypedArray ta) {
            return this.doCASInt(target, index, expected, replacement, (TypedArray.DirectUint16Array)ta) & 0xFFFF;
        }

        @Specialization(guards={"isSharedBufferView(target)", "isDirectUint32Array(ta)", "ta.isInBoundsFast(target, index)"})
        protected Object doUint32Array(JSTypedArrayObject target, int index, Object expected, Object replacement, @Bind(value="typedArrayGetArrayType(target)") TypedArray ta) {
            return this.doCASUint32(target, index, expected, replacement, (TypedArray.DirectUint32Array)ta);
        }

        @Specialization(guards={"isSharedBufferView(target)", "isDirectInt32Array(ta)", "ta.isInBoundsFast(target, index)"})
        protected int doInt32ArrayInt(JSTypedArrayObject target, int index, int expected, int replacement, @Bind(value="typedArrayGetArrayType(target)") TypedArray ta) {
            return this.doCASInt(target, index, expected, replacement, (TypedArray.DirectInt32Array)ta);
        }

        @Specialization(guards={"isSharedBufferView(target)", "isDirectInt32Array(ta)", "ta.isInBoundsFast(target, index)"})
        protected int doInt32ArrayObj(JSTypedArrayObject target, int index, Object expected, Object replacement, @Bind(value="typedArrayGetArrayType(target)") TypedArray ta) {
            return this.doCASInt(target, index, this.toInt(expected), this.toInt(replacement), (TypedArray.DirectInt32Array)ta);
        }

        @Specialization(guards={"isSharedBufferView(target)", "isDirectInt32Array(ta)"})
        protected int doInt32ArrayIntObjIdx(JSTypedArrayObject target, Object index, int expected, int replacement, @Bind(value="typedArrayGetArrayType(target)") TypedArray ta, @Cached @Cached.Shared(value="toIndex") JSToIndexNode toIndexNode) {
            int intIndex = AtomicsCompareExchangeNode.validateAtomicAccess(target, toIndexNode.executeLong(index), index);
            return this.doCASInt(target, intIndex, expected, replacement, (TypedArray.DirectInt32Array)ta);
        }

        @Specialization(guards={"isSharedBufferView(target)", "isDirectInt32Array(ta)"})
        protected int doInt32ArrayObjObjIdx(JSTypedArrayObject target, Object index, Object expected, Object replacement, @Bind(value="typedArrayGetArrayType(target)") TypedArray ta, @Cached @Cached.Shared(value="toIndex") JSToIndexNode toIndexNode) {
            int intIndex = AtomicsCompareExchangeNode.validateAtomicAccess(target, toIndexNode.executeLong(index), index);
            return this.doCASInt(target, intIndex, this.toInt(expected), this.toInt(replacement), (TypedArray.DirectInt32Array)ta);
        }

        @Specialization(guards={"isSharedBufferView(target)", "isDirectBigInt64Array(ta)"})
        protected BigInt doBigInt64ArrayObjObjIdx(JSTypedArrayObject target, Object index, Object expected, Object replacement, @Bind(value="typedArrayGetArrayType(target)") TypedArray ta, @Cached @Cached.Shared(value="toIndex") JSToIndexNode toIndexNode) {
            int intIndex = AtomicsCompareExchangeNode.validateAtomicAccess(target, toIndexNode.executeLong(index), index);
            return this.doCASBigInt(target, intIndex, this.toBigInt(expected).toBigInt64(), this.toBigInt(replacement), (TypedArray.DirectBigInt64Array)ta);
        }

        @Specialization(guards={"isSharedBufferView(target)", "isDirectBigUint64Array(ta)"})
        protected BigInt doBigUint64ArrayObjObjIdx(JSTypedArrayObject target, Object index, Object expected, Object replacement, @Bind(value="typedArrayGetArrayType(target)") TypedArray ta, @Cached @Cached.Shared(value="toIndex") JSToIndexNode toIndexNode) {
            int intIndex = AtomicsCompareExchangeNode.validateAtomicAccess(target, toIndexNode.executeLong(index), index);
            return this.doCASBigInt(target, intIndex, this.toBigInt(expected).toBigUint64(), this.toBigInt(replacement), (TypedArray.DirectBigUint64Array)ta);
        }

        @Specialization
        protected Object doGeneric(Object maybeTarget, Object index, Object expected, Object replacement, @Cached @Cached.Shared(value="toIndex") JSToIndexNode toIndexNode, @Cached BranchProfile notSharedArrayBuffer) {
            JSTypedArrayObject target = this.validateTypedArray(maybeTarget);
            TypedArray ta = this.validateIntegerTypedArray(target, false);
            int intIndex = AtomicsCompareExchangeNode.validateAtomicAccess(target, toIndexNode.executeLong(index), index);
            if (!AtomicsCompareExchangeNode.isSharedBufferView(target)) {
                notSharedArrayBuffer.enter();
                if (ta instanceof TypedArray.Int8Array || ta instanceof TypedArray.DirectInt8Array || ta instanceof TypedArray.InteropInt8Array) {
                    return (int)((byte)AtomicsCompareExchangeNode.doInt8(target, intIndex, this.toInt(expected), this.toIntChecked(replacement, target), true, (TypedArray.TypedIntArray)ta));
                }
                if (ta instanceof TypedArray.Uint8Array || ta instanceof TypedArray.DirectUint8Array || ta instanceof TypedArray.InteropUint8Array) {
                    return AtomicsCompareExchangeNode.doInt8(target, intIndex, this.toInt(expected), this.toIntChecked(replacement, target), false, (TypedArray.TypedIntArray)ta) & 0xFF;
                }
                if (ta instanceof TypedArray.Int16Array || ta instanceof TypedArray.DirectInt16Array || ta instanceof TypedArray.InteropInt16Array) {
                    return (int)((short)AtomicsCompareExchangeNode.doInt16(target, intIndex, this.toInt(expected), this.toIntChecked(replacement, target), true, (TypedArray.TypedIntArray)ta));
                }
                if (ta instanceof TypedArray.Uint16Array || ta instanceof TypedArray.DirectUint16Array || ta instanceof TypedArray.InteropUint16Array) {
                    return AtomicsCompareExchangeNode.doInt16(target, intIndex, this.toInt(expected), this.toIntChecked(replacement, target), false, (TypedArray.TypedIntArray)ta) & 0xFFFF;
                }
                if (ta instanceof TypedArray.Int32Array || ta instanceof TypedArray.DirectInt32Array || ta instanceof TypedArray.InteropInt32Array) {
                    return AtomicsCompareExchangeNode.doInt(target, intIndex, this.toInt(expected), this.toIntChecked(replacement, target), (TypedArray.TypedIntArray)ta);
                }
                if (ta instanceof TypedArray.Uint32Array || ta instanceof TypedArray.DirectUint32Array || ta instanceof TypedArray.InteropUint32Array) {
                    return AtomicsCompareExchangeNode.doUint32(target, intIndex, this.toInt(expected), this.toIntChecked(replacement, target), (TypedArray.TypedIntArray)ta);
                }
                if (ta instanceof TypedArray.BigInt64Array || ta instanceof TypedArray.DirectBigInt64Array || ta instanceof TypedArray.InteropBigInt64Array) {
                    return AtomicsCompareExchangeNode.doBigInt(target, intIndex, this.toBigInt(expected).toBigInt64(), this.toBigIntChecked(replacement, target), (TypedArray.TypedBigIntArray)ta);
                }
                if (ta instanceof TypedArray.BigUint64Array || ta instanceof TypedArray.DirectBigUint64Array || ta instanceof TypedArray.InteropBigUint64Array) {
                    return AtomicsCompareExchangeNode.doBigInt(target, intIndex, this.toBigInt(expected).toBigUint64(), this.toBigIntChecked(replacement, target), (TypedArray.TypedBigIntArray)ta);
                }
                throw Errors.shouldNotReachHere();
            }
            if (ta instanceof TypedArray.Int8Array || ta instanceof TypedArray.DirectInt8Array) {
                return (int)((byte)this.doCASInt(target, intIndex, this.toInt(expected), this.toInt(replacement), (TypedArray.TypedIntArray)ta));
            }
            if (ta instanceof TypedArray.Uint8Array || ta instanceof TypedArray.DirectUint8Array) {
                return this.doCASInt(target, intIndex, this.toInt(expected), this.toInt(replacement), (TypedArray.TypedIntArray)ta) & 0xFFFF;
            }
            if (ta instanceof TypedArray.Int16Array || ta instanceof TypedArray.DirectInt16Array) {
                return (int)((short)this.doCASInt(target, intIndex, this.toInt(expected), this.toInt(replacement), (TypedArray.TypedIntArray)ta));
            }
            if (ta instanceof TypedArray.Uint16Array || ta instanceof TypedArray.DirectUint16Array) {
                return this.doCASInt(target, intIndex, this.toInt(expected), this.toInt(replacement), (TypedArray.TypedIntArray)ta) & 0xFFFF;
            }
            if (ta instanceof TypedArray.Int32Array || ta instanceof TypedArray.DirectInt32Array) {
                return this.doCASInt(target, intIndex, this.toInt(expected), this.toInt(replacement), (TypedArray.TypedIntArray)ta);
            }
            if (ta instanceof TypedArray.Uint32Array || ta instanceof TypedArray.DirectUint32Array) {
                return this.doCASUint32(target, intIndex, this.toInt(expected), this.toInt(replacement), (TypedArray.TypedIntArray)ta);
            }
            if (ta instanceof TypedArray.BigInt64Array || ta instanceof TypedArray.DirectBigInt64Array) {
                return this.doCASBigInt(target, intIndex, this.toBigInt(expected).toBigInt64(), this.toBigInt(replacement), (TypedArray.TypedBigIntArray)ta);
            }
            if (ta instanceof TypedArray.BigUint64Array || ta instanceof TypedArray.DirectBigUint64Array) {
                return this.doCASBigInt(target, intIndex, this.toBigInt(expected).toBigUint64(), this.toBigInt(replacement), (TypedArray.TypedBigIntArray)ta);
            }
            throw Errors.shouldNotReachHere();
        }

        private int toIntChecked(Object v, JSTypedArrayObject target) {
            int value2 = this.toInt(v);
            this.checkDetached(target);
            return value2;
        }

        private int toInt(Object v) {
            if (this.toIntNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.toIntNode = this.insert(JSToInt32Node.create());
            }
            return this.toIntNode.executeInt(v);
        }

        private BigInt toBigIntChecked(Object v, JSTypedArrayObject target) {
            BigInt result = this.toBigInt(v);
            this.checkDetached(target);
            return result;
        }

        private BigInt toBigInt(Object v) {
            if (this.toBigIntNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.toBigIntNode = this.insert(JSToBigIntNode.create());
            }
            return this.toBigIntNode.executeBigInteger(v);
        }
    }

    @ImportStatic(value={JSArrayBufferView.class})
    public static abstract class AtomicsOperationNode
    extends JSBuiltinNode {
        private final BranchProfile detachedBuffer = BranchProfile.create();

        public AtomicsOperationNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        public static boolean isSharedBufferView(JSTypedArrayObject object) {
            return JSArrayBufferView.isJSArrayBufferView(object) && JSSharedArrayBuffer.isJSSharedArrayBuffer(JSArrayBufferView.getArrayBuffer(object));
        }

        public static boolean isInt32SharedBufferView(JSTypedArrayObject object) {
            return AtomicsOperationNode.isSharedBufferView(object) && JSArrayBufferView.typedArrayGetArrayType(object) instanceof TypedArray.DirectInt32Array;
        }

        public static boolean isDirectInt8Array(TypedArray ta) {
            return ta instanceof TypedArray.DirectInt8Array;
        }

        public static boolean isDirectUint8Array(TypedArray ta) {
            return ta instanceof TypedArray.DirectUint8Array;
        }

        public static boolean isDirectInt16Array(TypedArray ta) {
            return ta instanceof TypedArray.DirectInt16Array;
        }

        public static boolean isDirectUint16Array(TypedArray ta) {
            return ta instanceof TypedArray.DirectUint16Array;
        }

        public static boolean isDirectInt32Array(TypedArray ta) {
            return ta instanceof TypedArray.DirectInt32Array;
        }

        public static boolean isDirectUint32Array(TypedArray ta) {
            return ta instanceof TypedArray.DirectUint32Array;
        }

        public static boolean isDirectBigInt64Array(TypedArray ta) {
            return ta instanceof TypedArray.DirectBigInt64Array;
        }

        public static boolean isDirectBigUint64Array(TypedArray ta) {
            return ta instanceof TypedArray.DirectBigUint64Array;
        }

        protected void checkDetached(JSTypedArrayObject object) {
            if (this.getContext().getTypedArrayNotDetachedAssumption().isValid()) {
                return;
            }
            if (JSArrayBuffer.isDetachedBuffer(JSArrayBufferView.getArrayBuffer(object))) {
                this.detachedBuffer.enter();
                throw this.createTypeErrorNotDetachedArray();
            }
        }

        protected static int validateAtomicAccess(JSTypedArrayObject target, long convertedIndex, Object originalIndex) {
            int length = JSArrayBufferView.typedArrayGetLength(target);
            assert (convertedIndex >= 0L);
            if (convertedIndex >= (long)length) {
                throw AtomicsOperationNode.createRangeErrorSharedArray(originalIndex);
            }
            return (int)convertedIndex;
        }

        protected JSTypedArrayObject validateTypedArray(Object object) {
            if (!JSArrayBufferView.isJSArrayBufferView(object)) {
                throw this.createTypeErrorNotTypedArray();
            }
            JSTypedArrayObject typedArrayObject = (JSTypedArrayObject)object;
            this.checkDetached(typedArrayObject);
            return typedArrayObject;
        }

        protected TypedArray validateIntegerTypedArray(JSTypedArrayObject typedArrayObject, boolean waitable) {
            TypedArray ta = JSArrayBufferView.typedArrayGetArrayType(typedArrayObject);
            if (waitable) {
                if (!(ta instanceof TypedArray.DirectInt32Array || ta instanceof TypedArray.DirectBigInt64Array || ta instanceof TypedArray.Int32Array || ta instanceof TypedArray.BigInt64Array || ta instanceof TypedArray.InteropInt32Array || ta instanceof TypedArray.InteropBigInt64Array)) {
                    throw this.createTypeErrorNotWaitableIntArray();
                }
            } else if (!(ta instanceof TypedArray.DirectInt8Array || ta instanceof TypedArray.DirectUint8Array || ta instanceof TypedArray.DirectInt16Array || ta instanceof TypedArray.DirectUint16Array || ta instanceof TypedArray.DirectInt32Array || ta instanceof TypedArray.DirectUint32Array || ta instanceof TypedArray.DirectBigInt64Array || ta instanceof TypedArray.DirectBigUint64Array || ta instanceof TypedArray.Int8Array || ta instanceof TypedArray.Uint8Array || ta instanceof TypedArray.Int16Array || ta instanceof TypedArray.Uint16Array || ta instanceof TypedArray.Int32Array || ta instanceof TypedArray.Uint32Array || ta instanceof TypedArray.BigInt64Array || ta instanceof TypedArray.BigUint64Array || ta instanceof TypedArray.InteropInt8Array || ta instanceof TypedArray.InteropUint8Array || ta instanceof TypedArray.InteropInt16Array || ta instanceof TypedArray.InteropUint16Array || ta instanceof TypedArray.InteropInt32Array || ta instanceof TypedArray.InteropUint32Array || ta instanceof TypedArray.InteropBigInt64Array || ta instanceof TypedArray.InteropBigUint64Array)) {
                throw this.createTypeErrorNotIntArray();
            }
            return ta;
        }

        @CompilerDirectives.TruffleBoundary
        protected final JSException createTypeErrorNotDetachedArray() {
            return Errors.createTypeError("Cannot execute on detached array.", (Node)this);
        }

        @CompilerDirectives.TruffleBoundary
        protected final JSException createTypeErrorNotTypedArray() {
            return Errors.createTypeError("Cannot execute on non-typed array.", (Node)this);
        }

        @CompilerDirectives.TruffleBoundary
        protected final JSException createTypeErrorNotSharedArray() {
            return Errors.createTypeError("Cannot execute on non-shared array.", (Node)this);
        }

        @CompilerDirectives.TruffleBoundary
        protected final JSException createTypeErrorNotIntArray() {
            return Errors.createTypeError("Can only execute on selected types of int typed arrays (\"Int8Array\", \"Uint8Array\", \"Int16Array\", \"Uint16Array\",  \"Int32Array\", \"Uint32Array\", \"BigUint64Array\", or \"BigInt64Array\").", (Node)this);
        }

        @CompilerDirectives.TruffleBoundary
        protected final JSException createTypeErrorNotWaitableIntArray() {
            return Errors.createTypeError("Can only execute on Int32Array or BigInt64Array typed arrays.", (Node)this);
        }

        @CompilerDirectives.TruffleBoundary
        protected static final JSException createRangeErrorSharedArray(Object idx) {
            return Errors.createRangeError("Range error with index : " + idx);
        }

        @CompilerDirectives.TruffleBoundary
        protected final JSException createTypeErrorUnsupported() {
            return Errors.createTypeError("Unsupported operation", (Node)this);
        }
    }

    public static enum Atomics implements BuiltinEnum<Atomics>
    {
        compareExchange(4),
        load(2),
        store(3),
        add(3),
        sub(3),
        and(3),
        or(3),
        xor(3),
        exchange(3),
        wait(4),
        isLockFree(1),
        notify(3),
        waitAsync(4);

        private final int length;

        private Atomics(int length) {
            this.length = length;
        }

        @Override
        public int getLength() {
            return this.length;
        }

        @Override
        public int getECMAScriptVersion() {
            if (this.equals(notify)) {
                return 10;
            }
            if (this.equals(waitAsync)) {
                return 14;
            }
            return 8;
        }
    }
}

