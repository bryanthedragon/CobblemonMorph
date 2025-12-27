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

public final class AtomicsBuiltins extends JSBuiltinsContainer.SwitchEnum<AtomicsBuiltins.Atomics> {
   public static final JSBuiltinsContainer BUILTINS = new AtomicsBuiltins();

   protected AtomicsBuiltins() {
      super(JSRealm.ATOMICS_CLASS_NAME, AtomicsBuiltins.Atomics.class);
   }

   protected Object createNode(JSContext context, JSBuiltin builtin, boolean construct, boolean newTarget, AtomicsBuiltins.Atomics builtinEnum) {
      assert context.getEcmaScriptVersion() >= 8;

      switch (builtinEnum) {
         case compareExchange:
            return AtomicsBuiltinsFactory.AtomicsCompareExchangeNodeGen.create(context, builtin, args().fixedArgs(4).createArgumentNodes(context));
         case load:
            return AtomicsBuiltinsFactory.AtomicsLoadNodeGen.create(context, builtin, args().fixedArgs(4).createArgumentNodes(context));
         case store:
            return AtomicsBuiltinsFactory.AtomicsStoreNodeGen.create(context, builtin, args().fixedArgs(3).createArgumentNodes(context));
         case add:
            return AtomicsBuiltinsFactory.AtomicsComputeNodeGen.create(
               context, builtin, (a, b) -> a + b, (a, b) -> a.add(b), args().fixedArgs(3).createArgumentNodes(context)
            );
         case sub:
            return AtomicsBuiltinsFactory.AtomicsComputeNodeGen.create(
               context, builtin, (a, b) -> a - b, (a, b) -> a.subtract(b), args().fixedArgs(3).createArgumentNodes(context)
            );
         case and:
            return AtomicsBuiltinsFactory.AtomicsComputeNodeGen.create(
               context, builtin, (a, b) -> a & b, (a, b) -> a.and(b), args().fixedArgs(3).createArgumentNodes(context)
            );
         case or:
            return AtomicsBuiltinsFactory.AtomicsComputeNodeGen.create(
               context, builtin, (a, b) -> a | b, (a, b) -> a.or(b), args().fixedArgs(3).createArgumentNodes(context)
            );
         case xor:
            return AtomicsBuiltinsFactory.AtomicsComputeNodeGen.create(
               context, builtin, (a, b) -> a ^ b, (a, b) -> a.xor(b), args().fixedArgs(3).createArgumentNodes(context)
            );
         case exchange:
            return AtomicsBuiltinsFactory.AtomicsComputeNodeGen.create(
               context, builtin, (a, b) -> b, (a, b) -> b, args().fixedArgs(3).createArgumentNodes(context)
            );
         case notify:
            return AtomicsBuiltinsFactory.AtomicsNotifyNodeGen.create(context, builtin, args().fixedArgs(3).createArgumentNodes(context));
         case wait:
            return AtomicsBuiltinsFactory.AtomicsWaitNodeGen.create(context, builtin, args().fixedArgs(4).createArgumentNodes(context));
         case isLockFree:
            return AtomicsBuiltinsFactory.AtomicsIsLockFreeNodeGen.create(context, builtin, args().fixedArgs(1).createArgumentNodes(context));
         case waitAsync:
            return AtomicsBuiltinsFactory.AtomicsWaitAsyncNodeGen.create(context, builtin, args().fixedArgs(4).createArgumentNodes(context));
         default:
            return null;
      }
   }

   @FunctionalInterface
   public interface AtomicBinaryOperator<T> {
      T apply(T t, T u);
   }

   @FunctionalInterface
   public interface AtomicIntBinaryOperator {
      int applyAsInt(int left, int right);
   }

   public static enum Atomics implements BuiltinEnum<AtomicsBuiltins.Atomics> {
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
         } else {
            return this.equals(waitAsync) ? 14 : 8;
         }
      }
   }

   public abstract static class AtomicsCompareExchangeNode extends AtomicsBuiltins.AtomicsOperationNode {
      @Node.Child
      private JSToBigIntNode toBigIntNode;
      @Node.Child
      private JSToInt32Node toIntNode;

      public AtomicsCompareExchangeNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      protected Object doCASUint32(JSTypedArrayObject target, int index, Object expected, Object replacement, TypedArray.TypedIntArray typedArray) {
         return SafeInteger.valueOf(
            JSRuntime.toUInt32((long)typedArray.compareExchangeInt(target, index, (int)JSRuntime.toUInt32(expected), (int)JSRuntime.toUInt32(replacement)))
         );
      }

      protected int doCASInt(JSTypedArrayObject target, int index, int expected, int replacement, TypedArray.TypedIntArray typedArray) {
         return typedArray.compareExchangeInt(target, index, expected, replacement);
      }

      protected BigInt doCASBigInt(JSTypedArrayObject target, int index, BigInt expected, BigInt replacement, TypedArray.TypedBigIntArray typedArray) {
         return typedArray.compareExchangeBigInt(target, index, expected, replacement);
      }

      @CompilerDirectives.TruffleBoundary
      protected static int doInt8(JSTypedArrayObject target, int index, int expected, int replacement, boolean signed, TypedArray.TypedIntArray typedArray) {
         int read = typedArray.getInt(target, index, InteropLibrary.getUncached());
         read = signed ? (byte)read : read & 0xFF;
         int expectedChopped = signed ? (byte)expected : expected & 0xFF;
         if (read == expectedChopped) {
            int signedValue = signed ? (byte)replacement : replacement & 0xFF;
            typedArray.setInt(target, index, signedValue, InteropLibrary.getUncached());
         }

         return read;
      }

      @CompilerDirectives.TruffleBoundary
      protected static int doInt16(JSTypedArrayObject target, int index, int expected, int replacement, boolean signed, TypedArray.TypedIntArray typedArray) {
         int read = typedArray.getInt(target, index, InteropLibrary.getUncached());
         read = signed ? (short)read : read & 65535;
         int expectedChopped = signed ? (short)expected : expected & 65535;
         if (read == expectedChopped) {
            int signedValue = signed ? (short)replacement : replacement & 65535;
            typedArray.setInt(target, index, signedValue, InteropLibrary.getUncached());
         }

         return read;
      }

      @CompilerDirectives.TruffleBoundary
      protected static Object doUint32(JSTypedArrayObject target, int index, Object expected, Object replacement, TypedArray.TypedIntArray typedArray) {
         long read = JSRuntime.toUInt32((long)typedArray.getInt(target, index, InteropLibrary.getUncached()));
         if (read == JSRuntime.toUInt32(expected)) {
            typedArray.setInt(target, index, (int)JSRuntime.toUInt32(replacement), InteropLibrary.getUncached());
         }

         return SafeInteger.valueOf(read);
      }

      @CompilerDirectives.TruffleBoundary
      protected static int doInt(JSTypedArrayObject target, int index, int expected, int replacement, TypedArray.TypedIntArray typedArray) {
         int read = typedArray.getInt(target, index, InteropLibrary.getUncached());
         if (read == expected) {
            typedArray.setInt(target, index, replacement, InteropLibrary.getUncached());
         }

         return read;
      }

      @CompilerDirectives.TruffleBoundary
      protected static BigInt doBigInt(JSTypedArrayObject target, int index, BigInt expected, BigInt replacement, TypedArray.TypedBigIntArray typedArray) {
         BigInt read = typedArray.getBigInt(target, index, InteropLibrary.getUncached());
         if (read.compareTo(expected) == 0) {
            typedArray.setBigInt(target, index, replacement, InteropLibrary.getUncached());
         }

         return read;
      }

      @Specialization(guards = {"isSharedBufferView(target)", "isDirectInt8Array(ta)", "ta.isInBoundsFast(target, index)"})
      protected int doInt8Array(JSTypedArrayObject target, int index, int expected, int replacement, @Bind("typedArrayGetArrayType(target)") TypedArray ta) {
         return (byte)this.doCASInt(target, index, expected, replacement, (TypedArray.DirectInt8Array)ta);
      }

      @Specialization(guards = {"isSharedBufferView(target)", "isDirectUint8Array(ta)", "ta.isInBoundsFast(target, index)"})
      protected int doUint8Array(JSTypedArrayObject target, int index, int expected, int replacement, @Bind("typedArrayGetArrayType(target)") TypedArray ta) {
         return this.doCASInt(target, index, expected, replacement, (TypedArray.DirectUint8Array)ta) & 0xFF;
      }

      @Specialization(guards = {"isSharedBufferView(target)", "isDirectInt16Array(ta)", "ta.isInBoundsFast(target, index)"})
      protected int doInt16Array(JSTypedArrayObject target, int index, int expected, int replacement, @Bind("typedArrayGetArrayType(target)") TypedArray ta) {
         return (short)this.doCASInt(target, index, expected, replacement, (TypedArray.DirectInt16Array)ta);
      }

      @Specialization(guards = {"isSharedBufferView(target)", "isDirectUint16Array(ta)", "ta.isInBoundsFast(target, index)"})
      protected int doUint16Array(JSTypedArrayObject target, int index, int expected, int replacement, @Bind("typedArrayGetArrayType(target)") TypedArray ta) {
         return this.doCASInt(target, index, expected, replacement, (TypedArray.DirectUint16Array)ta) & 65535;
      }

      @Specialization(guards = {"isSharedBufferView(target)", "isDirectUint32Array(ta)", "ta.isInBoundsFast(target, index)"})
      protected Object doUint32Array(
         JSTypedArrayObject target, int index, Object expected, Object replacement, @Bind("typedArrayGetArrayType(target)") TypedArray ta
      ) {
         return this.doCASUint32(target, index, expected, replacement, (TypedArray.DirectUint32Array)ta);
      }

      @Specialization(guards = {"isSharedBufferView(target)", "isDirectInt32Array(ta)", "ta.isInBoundsFast(target, index)"})
      protected int doInt32ArrayInt(JSTypedArrayObject target, int index, int expected, int replacement, @Bind("typedArrayGetArrayType(target)") TypedArray ta) {
         return this.doCASInt(target, index, expected, replacement, (TypedArray.DirectInt32Array)ta);
      }

      @Specialization(guards = {"isSharedBufferView(target)", "isDirectInt32Array(ta)", "ta.isInBoundsFast(target, index)"})
      protected int doInt32ArrayObj(
         JSTypedArrayObject target, int index, Object expected, Object replacement, @Bind("typedArrayGetArrayType(target)") TypedArray ta
      ) {
         return this.doCASInt(target, index, this.toInt(expected), this.toInt(replacement), (TypedArray.DirectInt32Array)ta);
      }

      @Specialization(guards = {"isSharedBufferView(target)", "isDirectInt32Array(ta)"})
      protected int doInt32ArrayIntObjIdx(
         JSTypedArrayObject target,
         Object index,
         int expected,
         int replacement,
         @Bind("typedArrayGetArrayType(target)") TypedArray ta,
         @Cached @Cached.Shared("toIndex") JSToIndexNode toIndexNode
      ) {
         int intIndex = validateAtomicAccess(target, toIndexNode.executeLong(index), index);
         return this.doCASInt(target, intIndex, expected, replacement, (TypedArray.DirectInt32Array)ta);
      }

      @Specialization(guards = {"isSharedBufferView(target)", "isDirectInt32Array(ta)"})
      protected int doInt32ArrayObjObjIdx(
         JSTypedArrayObject target,
         Object index,
         Object expected,
         Object replacement,
         @Bind("typedArrayGetArrayType(target)") TypedArray ta,
         @Cached @Cached.Shared("toIndex") JSToIndexNode toIndexNode
      ) {
         int intIndex = validateAtomicAccess(target, toIndexNode.executeLong(index), index);
         return this.doCASInt(target, intIndex, this.toInt(expected), this.toInt(replacement), (TypedArray.DirectInt32Array)ta);
      }

      @Specialization(guards = {"isSharedBufferView(target)", "isDirectBigInt64Array(ta)"})
      protected BigInt doBigInt64ArrayObjObjIdx(
         JSTypedArrayObject target,
         Object index,
         Object expected,
         Object replacement,
         @Bind("typedArrayGetArrayType(target)") TypedArray ta,
         @Cached @Cached.Shared("toIndex") JSToIndexNode toIndexNode
      ) {
         int intIndex = validateAtomicAccess(target, toIndexNode.executeLong(index), index);
         return this.doCASBigInt(target, intIndex, this.toBigInt(expected).toBigInt64(), this.toBigInt(replacement), (TypedArray.DirectBigInt64Array)ta);
      }

      @Specialization(guards = {"isSharedBufferView(target)", "isDirectBigUint64Array(ta)"})
      protected BigInt doBigUint64ArrayObjObjIdx(
         JSTypedArrayObject target,
         Object index,
         Object expected,
         Object replacement,
         @Bind("typedArrayGetArrayType(target)") TypedArray ta,
         @Cached @Cached.Shared("toIndex") JSToIndexNode toIndexNode
      ) {
         int intIndex = validateAtomicAccess(target, toIndexNode.executeLong(index), index);
         return this.doCASBigInt(target, intIndex, this.toBigInt(expected).toBigUint64(), this.toBigInt(replacement), (TypedArray.DirectBigUint64Array)ta);
      }

      @Specialization
      protected Object doGeneric(
         Object maybeTarget,
         Object index,
         Object expected,
         Object replacement,
         @Cached @Cached.Shared("toIndex") JSToIndexNode toIndexNode,
         @Cached BranchProfile notSharedArrayBuffer
      ) {
         JSTypedArrayObject target = this.validateTypedArray(maybeTarget);
         TypedArray ta = this.validateIntegerTypedArray(target, false);
         int intIndex = validateAtomicAccess(target, toIndexNode.executeLong(index), index);
         if (!isSharedBufferView(target)) {
            notSharedArrayBuffer.enter();
            if (ta instanceof TypedArray.Int8Array || ta instanceof TypedArray.DirectInt8Array || ta instanceof TypedArray.InteropInt8Array) {
               return Integer.valueOf(
                  (byte)doInt8(target, intIndex, this.toInt(expected), this.toIntChecked(replacement, target), true, (TypedArray.TypedIntArray)ta)
               );
            } else if (ta instanceof TypedArray.Uint8Array || ta instanceof TypedArray.DirectUint8Array || ta instanceof TypedArray.InteropUint8Array) {
               return doInt8(target, intIndex, this.toInt(expected), this.toIntChecked(replacement, target), false, (TypedArray.TypedIntArray)ta) & 0xFF;
            } else if (ta instanceof TypedArray.Int16Array || ta instanceof TypedArray.DirectInt16Array || ta instanceof TypedArray.InteropInt16Array) {
               return Integer.valueOf(
                  (short)doInt16(target, intIndex, this.toInt(expected), this.toIntChecked(replacement, target), true, (TypedArray.TypedIntArray)ta)
               );
            } else if (ta instanceof TypedArray.Uint16Array || ta instanceof TypedArray.DirectUint16Array || ta instanceof TypedArray.InteropUint16Array) {
               return doInt16(target, intIndex, this.toInt(expected), this.toIntChecked(replacement, target), false, (TypedArray.TypedIntArray)ta) & 65535;
            } else if (ta instanceof TypedArray.Int32Array || ta instanceof TypedArray.DirectInt32Array || ta instanceof TypedArray.InteropInt32Array) {
               return doInt(target, intIndex, this.toInt(expected), this.toIntChecked(replacement, target), (TypedArray.TypedIntArray)ta);
            } else if (ta instanceof TypedArray.Uint32Array || ta instanceof TypedArray.DirectUint32Array || ta instanceof TypedArray.InteropUint32Array) {
               return doUint32(target, intIndex, this.toInt(expected), this.toIntChecked(replacement, target), (TypedArray.TypedIntArray)ta);
            } else if (ta instanceof TypedArray.BigInt64Array || ta instanceof TypedArray.DirectBigInt64Array || ta instanceof TypedArray.InteropBigInt64Array) {
               return doBigInt(
                  target, intIndex, this.toBigInt(expected).toBigInt64(), this.toBigIntChecked(replacement, target), (TypedArray.TypedBigIntArray)ta
               );
            } else if (!(ta instanceof TypedArray.BigUint64Array)
               && !(ta instanceof TypedArray.DirectBigUint64Array)
               && !(ta instanceof TypedArray.InteropBigUint64Array)) {
               throw Errors.shouldNotReachHere();
            } else {
               return doBigInt(
                  target, intIndex, this.toBigInt(expected).toBigUint64(), this.toBigIntChecked(replacement, target), (TypedArray.TypedBigIntArray)ta
               );
            }
         } else if (ta instanceof TypedArray.Int8Array || ta instanceof TypedArray.DirectInt8Array) {
            return Integer.valueOf((byte)this.doCASInt(target, intIndex, this.toInt(expected), this.toInt(replacement), (TypedArray.TypedIntArray)ta));
         } else if (ta instanceof TypedArray.Uint8Array || ta instanceof TypedArray.DirectUint8Array) {
            return this.doCASInt(target, intIndex, this.toInt(expected), this.toInt(replacement), (TypedArray.TypedIntArray)ta) & 65535;
         } else if (ta instanceof TypedArray.Int16Array || ta instanceof TypedArray.DirectInt16Array) {
            return Integer.valueOf((short)this.doCASInt(target, intIndex, this.toInt(expected), this.toInt(replacement), (TypedArray.TypedIntArray)ta));
         } else if (ta instanceof TypedArray.Uint16Array || ta instanceof TypedArray.DirectUint16Array) {
            return this.doCASInt(target, intIndex, this.toInt(expected), this.toInt(replacement), (TypedArray.TypedIntArray)ta) & 65535;
         } else if (ta instanceof TypedArray.Int32Array || ta instanceof TypedArray.DirectInt32Array) {
            return this.doCASInt(target, intIndex, this.toInt(expected), this.toInt(replacement), (TypedArray.TypedIntArray)ta);
         } else if (ta instanceof TypedArray.Uint32Array || ta instanceof TypedArray.DirectUint32Array) {
            return this.doCASUint32(target, intIndex, this.toInt(expected), this.toInt(replacement), (TypedArray.TypedIntArray)ta);
         } else if (ta instanceof TypedArray.BigInt64Array || ta instanceof TypedArray.DirectBigInt64Array) {
            return this.doCASBigInt(target, intIndex, this.toBigInt(expected).toBigInt64(), this.toBigInt(replacement), (TypedArray.TypedBigIntArray)ta);
         } else if (!(ta instanceof TypedArray.BigUint64Array) && !(ta instanceof TypedArray.DirectBigUint64Array)) {
            throw Errors.shouldNotReachHere();
         } else {
            return this.doCASBigInt(target, intIndex, this.toBigInt(expected).toBigUint64(), this.toBigInt(replacement), (TypedArray.TypedBigIntArray)ta);
         }
      }

      private int toIntChecked(Object v, JSTypedArrayObject target) {
         int value = this.toInt(v);
         this.checkDetached(target);
         return value;
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

   public abstract static class AtomicsComputeNode extends AtomicsBuiltins.AtomicsOperationNode {
      private final AtomicsBuiltins.AtomicIntBinaryOperator intOperator;
      private final AtomicsBuiltins.AtomicBinaryOperator<BigInt> bigIntOperator;
      @Node.Child
      private JSToBigIntNode toBigIntNode;
      @Node.Child
      private JSToInt32Node toIntNode;

      public AtomicsComputeNode(
         JSContext context, JSBuiltin builtin, AtomicsBuiltins.AtomicIntBinaryOperator intOperator, AtomicsBuiltins.AtomicBinaryOperator<BigInt> bigIntOperator
      ) {
         super(context, builtin);
         this.intOperator = intOperator;
         this.bigIntOperator = bigIntOperator;
      }

      private int atomicDoInt(JSTypedArrayObject target, int index, int value, TypedArray.TypedIntArray typedArray) {
         int initial;
         int result;
         do {
            initial = SharedMemorySync.doVolatileGet(target, index, typedArray);
            result = this.intOperator.applyAsInt(initial, value);
         } while (!SharedMemorySync.compareAndSetInt(target, index, initial, result, typedArray));

         return initial;
      }

      private BigInt atomicDoBigInt(JSTypedArrayObject target, int index, BigInt value, TypedArray.TypedBigIntArray typedArray) {
         BigInt initial;
         BigInt result;
         do {
            initial = SharedMemorySync.doVolatileGetBigInt(target, index, typedArray);
            result = this.bigIntOperator.apply(initial, value);
         } while (!SharedMemorySync.compareAndSetBigInt(target, index, initial, result, typedArray));

         return initial;
      }

      @CompilerDirectives.TruffleBoundary
      private int nonAtomicDoInt(JSTypedArrayObject target, int index, int value, TypedArray.TypedIntArray typedArray) {
         int initial = typedArray.getInt(target, index, InteropLibrary.getUncached());
         int result = this.intOperator.applyAsInt(initial, value);
         typedArray.setInt(target, index, result, InteropLibrary.getUncached());
         return initial;
      }

      @CompilerDirectives.TruffleBoundary
      private BigInt nonAtomicDoBigInt(JSTypedArrayObject target, int index, BigInt value, TypedArray.TypedBigIntArray typedArray) {
         BigInt initial = typedArray.getBigInt(target, index, InteropLibrary.getUncached());
         BigInt result = this.bigIntOperator.apply(initial, value);
         typedArray.setBigInt(target, index, result, InteropLibrary.getUncached());
         return initial;
      }

      @Specialization(guards = {"isSharedBufferView(target)", "isDirectInt8Array(ta)", "ta.isInBoundsFast(target, index)"})
      protected int doSharedInt8Array(JSTypedArrayObject target, int index, int value, @Bind("typedArrayGetArrayType(target)") TypedArray ta) {
         return (byte)this.atomicDoInt(target, index, value, (TypedArray.DirectInt8Array)ta);
      }

      @Specialization(guards = {"isSharedBufferView(target)", "isDirectUint8Array(ta)", "ta.isInBoundsFast(target, index)"})
      protected int doSharedUint8Array(JSTypedArrayObject target, int index, int value, @Bind("typedArrayGetArrayType(target)") TypedArray ta) {
         return this.atomicDoInt(target, index, value, (TypedArray.DirectUint8Array)ta) & 0xFF;
      }

      @Specialization(guards = {"isSharedBufferView(target)", "isDirectInt16Array(ta)", "ta.isInBoundsFast(target, index)"})
      protected int doSharedInt16Array(JSTypedArrayObject target, int index, int value, @Bind("typedArrayGetArrayType(target)") TypedArray ta) {
         return (short)this.atomicDoInt(target, index, value, (TypedArray.DirectInt16Array)ta);
      }

      @Specialization(guards = {"isSharedBufferView(target)", "isDirectUint16Array(ta)", "ta.isInBoundsFast(target, index)"})
      protected int doSharedUint16Array(JSTypedArrayObject target, int index, int value, @Bind("typedArrayGetArrayType(target)") TypedArray ta) {
         return this.atomicDoInt(target, index, value, (TypedArray.DirectUint16Array)ta) & 65535;
      }

      @Specialization(guards = {"isSharedBufferView(target)", "isDirectInt32Array(ta)", "ta.isInBoundsFast(target, index)"})
      protected int doSharedInt32Array(JSTypedArrayObject target, int index, int value, @Bind("typedArrayGetArrayType(target)") TypedArray ta) {
         return this.atomicDoInt(target, index, value, (TypedArray.DirectInt32Array)ta);
      }

      @Specialization(guards = {"isSharedBufferView(target)", "isDirectUint32Array(ta)", "ta.isInBoundsFast(target, index)"})
      protected SafeInteger doSharedUint32Array(JSTypedArrayObject target, int index, int value, @Bind("typedArrayGetArrayType(target)") TypedArray ta) {
         return SafeInteger.valueOf(this.atomicDoInt(target, index, value, (TypedArray.DirectUint32Array)ta) & 4294967295L);
      }

      @Specialization(guards = {"isSharedBufferView(target)", "isDirectInt32Array(ta)"})
      protected int doSharedInt32ArrayObjIdx(
         JSTypedArrayObject target,
         Object index,
         int value,
         @Bind("typedArrayGetArrayType(target)") TypedArray ta,
         @Cached @Cached.Shared("toIndex") JSToIndexNode toIndexNode
      ) {
         int intIndex = validateAtomicAccess(target, toIndexNode.executeLong(index), index);
         return this.atomicDoInt(target, intIndex, value, (TypedArray.DirectInt32Array)ta);
      }

      @Specialization(guards = {"isSharedBufferView(target)", "isDirectBigInt64Array(ta)"})
      protected BigInt doSharedBigInt64Array(
         JSTypedArrayObject target,
         Object index,
         Object value,
         @Bind("typedArrayGetArrayType(target)") TypedArray ta,
         @Cached @Cached.Shared("toIndex") JSToIndexNode toIndexNode
      ) {
         int intIndex = validateAtomicAccess(target, toIndexNode.executeLong(index), index);
         return this.atomicDoBigInt(target, intIndex, this.toBigInt(value), (TypedArray.DirectBigInt64Array)ta);
      }

      @Specialization(guards = {"isSharedBufferView(target)", "isDirectBigUint64Array(ta)"})
      protected BigInt doSharedBigUint64Array(
         JSTypedArrayObject target,
         Object index,
         Object value,
         @Bind("typedArrayGetArrayType(target)") TypedArray ta,
         @Cached @Cached.Shared("toIndex") JSToIndexNode toIndexNode
      ) {
         int intIndex = validateAtomicAccess(target, toIndexNode.executeLong(index), index);
         return this.atomicDoBigInt(target, intIndex, this.toBigInt(value), (TypedArray.DirectBigUint64Array)ta);
      }

      @Specialization
      protected Object doGeneric(
         Object maybeTarget,
         Object index,
         Object value,
         @Cached @Cached.Shared("toIndex") JSToIndexNode toIndexNode,
         @Cached BranchProfile notSharedArrayBuffer
      ) {
         JSTypedArrayObject target = this.validateTypedArray(maybeTarget);
         TypedArray ta = this.validateIntegerTypedArray(target, false);
         int intIndex = validateAtomicAccess(target, toIndexNode.executeLong(index), index);
         if (!isSharedBufferView(target)) {
            notSharedArrayBuffer.enter();
            if (ta instanceof TypedArray.DirectInt8Array || ta instanceof TypedArray.Int8Array || ta instanceof TypedArray.InteropInt8Array) {
               return Integer.valueOf((byte)this.nonAtomicDoInt(target, intIndex, this.toIntChecked(value, target), (TypedArray.TypedIntArray)ta));
            } else if (ta instanceof TypedArray.DirectUint8Array || ta instanceof TypedArray.Uint8Array || ta instanceof TypedArray.InteropUint8Array) {
               return this.nonAtomicDoInt(target, intIndex, this.toIntChecked(value, target), (TypedArray.TypedIntArray)ta) & 0xFF;
            } else if (ta instanceof TypedArray.DirectInt16Array || ta instanceof TypedArray.Int16Array || ta instanceof TypedArray.InteropInt16Array) {
               return this.nonAtomicDoInt(target, intIndex, this.toIntChecked(value, target), (TypedArray.TypedIntArray)ta);
            } else if (ta instanceof TypedArray.DirectUint16Array || ta instanceof TypedArray.Uint16Array || ta instanceof TypedArray.InteropUint16Array) {
               return this.nonAtomicDoInt(target, intIndex, this.toIntChecked(value, target), (TypedArray.TypedIntArray)ta) & 65535;
            } else if (ta instanceof TypedArray.DirectInt32Array || ta instanceof TypedArray.Int32Array || ta instanceof TypedArray.InteropInt32Array) {
               return this.nonAtomicDoInt(target, intIndex, this.toIntChecked(value, target), (TypedArray.TypedIntArray)ta);
            } else if (ta instanceof TypedArray.DirectUint32Array || ta instanceof TypedArray.Uint32Array || ta instanceof TypedArray.InteropUint32Array) {
               return SafeInteger.valueOf(this.nonAtomicDoInt(target, intIndex, this.toIntChecked(value, target), (TypedArray.TypedIntArray)ta) & 4294967295L);
            } else if (!(ta instanceof TypedArray.DirectBigInt64Array)
               && !(ta instanceof TypedArray.DirectBigUint64Array)
               && !(ta instanceof TypedArray.BigInt64Array)
               && !(ta instanceof TypedArray.BigUint64Array)
               && !(ta instanceof TypedArray.InteropBigInt64Array)
               && !(ta instanceof TypedArray.InteropBigUint64Array)) {
               throw Errors.shouldNotReachHere();
            } else {
               return this.nonAtomicDoBigInt(target, intIndex, this.toBigIntChecked(value, target), (TypedArray.TypedBigIntArray)ta);
            }
         } else if (ta instanceof TypedArray.DirectInt8Array || ta instanceof TypedArray.Int8Array) {
            return Integer.valueOf((byte)this.atomicDoInt(target, intIndex, this.toInt(value), (TypedArray.TypedIntArray)ta));
         } else if (ta instanceof TypedArray.DirectUint8Array || ta instanceof TypedArray.Uint8Array) {
            return this.atomicDoInt(target, intIndex, this.toInt(value), (TypedArray.TypedIntArray)ta) & 0xFF;
         } else if (ta instanceof TypedArray.DirectInt16Array || ta instanceof TypedArray.Int16Array) {
            return Integer.valueOf((short)this.atomicDoInt(target, intIndex, this.toInt(value), (TypedArray.TypedIntArray)ta));
         } else if (ta instanceof TypedArray.DirectUint16Array || ta instanceof TypedArray.Uint16Array) {
            return this.atomicDoInt(target, intIndex, this.toInt(value), (TypedArray.TypedIntArray)ta) & 65535;
         } else if (ta instanceof TypedArray.DirectInt32Array || ta instanceof TypedArray.Int32Array) {
            return this.atomicDoInt(target, intIndex, this.toInt(value), (TypedArray.TypedIntArray)ta);
         } else if (ta instanceof TypedArray.DirectUint32Array || ta instanceof TypedArray.Uint32Array) {
            return SafeInteger.valueOf(this.atomicDoInt(target, intIndex, this.toInt(value), (TypedArray.TypedIntArray)ta) & 4294967295L);
         } else if (!(ta instanceof TypedArray.DirectBigInt64Array)
            && !(ta instanceof TypedArray.DirectBigUint64Array)
            && !(ta instanceof TypedArray.BigInt64Array)
            && !(ta instanceof TypedArray.BigUint64Array)) {
            throw Errors.shouldNotReachHere();
         } else {
            return this.atomicDoBigInt(target, intIndex, this.toBigInt(value), (TypedArray.TypedBigIntArray)ta);
         }
      }

      private int toIntChecked(Object v, JSTypedArrayObject target) {
         int value = this.toInt(v);
         this.checkDetached(target);
         return value;
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

   public abstract static class AtomicsIsLockFreeNode extends AtomicsBuiltins.AtomicsOperationNode {
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
         } else if (size == 2) {
            return true;
         } else {
            return size == 4 ? true : size == 8;
         }
      }

      @Specialization
      protected static boolean doGeneric(Object size, @Cached JSToInt32Node toInt32Node) {
         return doInt(toInt32Node.executeInt(size));
      }
   }

   public abstract static class AtomicsLoadNode extends AtomicsBuiltins.AtomicsOperationNode {
      public AtomicsLoadNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      public abstract Object executeWithBufferAndIndex(VirtualFrame frame, Object target, Object index);

      @Specialization(guards = {"isSharedBufferView(target)", "isDirectInt8Array(ta)", "ta.isInBoundsFast(target, index)"})
      protected int doInt8ArrayObj(JSTypedArrayObject target, int index, @Bind("typedArrayGetArrayType(target)") TypedArray ta) {
         return SharedMemorySync.doVolatileGet(target, index, (TypedArray.DirectInt8Array)ta);
      }

      @Specialization(guards = {"isSharedBufferView(target)", "isDirectUint8Array(ta)", "ta.isInBoundsFast(target, index)"})
      protected int doUint8ArrayObj(JSTypedArrayObject target, int index, @Bind("typedArrayGetArrayType(target)") TypedArray ta) {
         return SharedMemorySync.doVolatileGet(target, index, (TypedArray.DirectUint8Array)ta) & 0xFF;
      }

      @Specialization(guards = {"isSharedBufferView(target)", "isDirectInt16Array(ta)", "ta.isInBoundsFast(target, index)"})
      protected int doInt16ArrayObj(JSTypedArrayObject target, int index, @Bind("typedArrayGetArrayType(target)") TypedArray ta) {
         return SharedMemorySync.doVolatileGet(target, index, (TypedArray.DirectInt16Array)ta);
      }

      @Specialization(guards = {"isSharedBufferView(target)", "isDirectUint16Array(ta)", "ta.isInBoundsFast(target, index)"})
      protected int doUint16ArrayObj(JSTypedArrayObject target, int index, @Bind("typedArrayGetArrayType(target)") TypedArray ta) {
         return SharedMemorySync.doVolatileGet(target, index, (TypedArray.DirectUint16Array)ta) & 65535;
      }

      @Specialization(guards = {"isSharedBufferView(target)", "isDirectInt32Array(ta)", "ta.isInBoundsFast(target, index)"})
      protected int doInt32ArrayObj(JSTypedArrayObject target, int index, @Bind("typedArrayGetArrayType(target)") TypedArray ta) {
         return SharedMemorySync.doVolatileGet(target, index, (TypedArray.DirectInt32Array)ta);
      }

      @Specialization(guards = {"isSharedBufferView(target)", "isDirectUint32Array(ta)", "ta.isInBoundsFast(target, index)"})
      protected SafeInteger doUint32ArrayObj(JSTypedArrayObject target, int index, @Bind("typedArrayGetArrayType(target)") TypedArray ta) {
         return SafeInteger.valueOf(SharedMemorySync.doVolatileGet(target, index, (TypedArray.DirectUint32Array)ta) & 4294967295L);
      }

      @Specialization(guards = {"isSharedBufferView(target)", "isDirectBigInt64Array(ta)", "ta.isInBoundsFast(target, index)"})
      protected BigInt doBigInt64ArrayObj(JSTypedArrayObject target, int index, @Bind("typedArrayGetArrayType(target)") TypedArray ta) {
         return SharedMemorySync.doVolatileGetBigInt(target, index, (TypedArray.DirectBigInt64Array)ta);
      }

      @Specialization(guards = {"isSharedBufferView(target)", "isDirectBigUint64Array(ta)", "ta.isInBoundsFast(target, index)"})
      protected BigInt doBigUint64ArrayObj(JSTypedArrayObject target, int index, @Bind("typedArrayGetArrayType(target)") TypedArray ta) {
         return SharedMemorySync.doVolatileGetBigInt(target, index, (TypedArray.DirectBigUint64Array)ta);
      }

      @Specialization(guards = {"isSharedBufferView(target)", "isDirectInt32Array(ta)"})
      protected int doInt32ArrayObjObjIdx(
         JSTypedArrayObject target,
         Object index,
         @Bind("typedArrayGetArrayType(target)") TypedArray ta,
         @Cached @Cached.Shared("toIndex") JSToIndexNode toIndexNode
      ) {
         int intIndex = validateAtomicAccess(target, toIndexNode.executeLong(index), index);
         return SharedMemorySync.doVolatileGet(target, intIndex, (TypedArray.DirectInt32Array)ta);
      }

      @Specialization
      protected Object doGeneric(Object maybeTarget, Object index, @Cached @Cached.Shared("toIndex") JSToIndexNode toIndexNode) {
         JSTypedArrayObject target = this.validateTypedArray(maybeTarget);
         TypedArray ta = this.validateIntegerTypedArray(target, false);
         int intIndex = validateAtomicAccess(target, toIndexNode.executeLong(index), index);
         this.checkDetached(target);
         if (ta instanceof TypedArray.DirectInt8Array || ta instanceof TypedArray.Int8Array || ta instanceof TypedArray.InteropInt8Array) {
            return SharedMemorySync.doVolatileGet(target, intIndex, (TypedArray.TypedIntArray)ta);
         } else if (ta instanceof TypedArray.DirectUint8Array || ta instanceof TypedArray.Uint8Array || ta instanceof TypedArray.InteropUint8Array) {
            return SharedMemorySync.doVolatileGet(target, intIndex, (TypedArray.TypedIntArray)ta) & 0xFF;
         } else if (ta instanceof TypedArray.DirectInt16Array || ta instanceof TypedArray.Int16Array || ta instanceof TypedArray.InteropInt16Array) {
            return SharedMemorySync.doVolatileGet(target, intIndex, (TypedArray.TypedIntArray)ta);
         } else if (ta instanceof TypedArray.DirectUint16Array || ta instanceof TypedArray.Uint16Array || ta instanceof TypedArray.InteropUint16Array) {
            return SharedMemorySync.doVolatileGet(target, intIndex, (TypedArray.TypedIntArray)ta) & 65535;
         } else if (ta instanceof TypedArray.DirectInt32Array || ta instanceof TypedArray.Int32Array || ta instanceof TypedArray.InteropInt32Array) {
            return SharedMemorySync.doVolatileGet(target, intIndex, (TypedArray.TypedIntArray)ta);
         } else if (ta instanceof TypedArray.DirectUint32Array || ta instanceof TypedArray.Uint32Array || ta instanceof TypedArray.InteropUint32Array) {
            return SafeInteger.valueOf(SharedMemorySync.doVolatileGet(target, intIndex, (TypedArray.TypedIntArray)ta) & 4294967295L);
         } else if (ta instanceof TypedArray.DirectBigInt64Array || ta instanceof TypedArray.BigInt64Array || ta instanceof TypedArray.InteropBigInt64Array) {
            return SharedMemorySync.doVolatileGetBigInt(target, intIndex, (TypedArray.TypedBigIntArray)ta);
         } else if (!(ta instanceof TypedArray.DirectBigUint64Array)
            && !(ta instanceof TypedArray.BigUint64Array)
            && !(ta instanceof TypedArray.InteropBigUint64Array)) {
            throw Errors.shouldNotReachHere();
         } else {
            return SharedMemorySync.doVolatileGetBigInt(target, intIndex, (TypedArray.TypedBigIntArray)ta);
         }
      }
   }

   public abstract static class AtomicsNotifyNode extends AtomicsBuiltins.AtomicsOperationNode {
      public AtomicsNotifyNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization
      protected Object doNotify(
         Object maybeTarget,
         Object index,
         Object count,
         @Cached JSToIndexNode toIndexNode,
         @Cached("create()") JSToInt32Node toInt32Node,
         @Cached("create()") BranchProfile notSharedArrayBuffer
      ) {
         JSTypedArrayObject target = this.validateTypedArray(maybeTarget);
         this.validateIntegerTypedArray(target, true);
         int i = validateAtomicAccess(target, toIndexNode.executeLong(index), index);
         int c = Integer.MAX_VALUE;
         if (count != Undefined.instance) {
            int tmp = toInt32Node.executeInt(count);
            c = Integer.max(tmp, 0);
         }

         if (!isSharedBufferView(target)) {
            notSharedArrayBuffer.enter();
            return 0;
         } else {
            JSAgentWaiterList.JSAgentWaiterListEntry wl = SharedMemorySync.getWaiterList(this.getContext(), target, i);
            return notifyWaiters(wl, c);
         }
      }

      @CompilerDirectives.TruffleBoundary
      private static Object notifyWaiters(JSAgentWaiterList.JSAgentWaiterListEntry wl, int c) {
         wl.enterCriticalSection();

         Integer var9;
         try {
            boolean wake = false;
            JSAgentWaiterList.WaiterRecord[] waiters = SharedMemorySync.removeWaiters(wl, c);

            int n;
            for (n = 0; n < waiters.length; n++) {
               JSAgentWaiterList.WaiterRecord waiterRecord = waiters[n];
               waiterRecord.setNotified();
               if (waiterRecord.getPromiseCapability() == null) {
                  wake = true;
               } else if (Double.isInfinite(waiterRecord.getTimeout())) {
                  waiterRecord.enqueueInAgent();
               }
            }

            if (wake) {
               SharedMemorySync.wakeWaiters(wl);
            }

            var9 = n;
         } finally {
            wl.leaveCriticalSection();
         }

         return var9;
      }
   }

   @ImportStatic(JSArrayBufferView.class)
   public abstract static class AtomicsOperationNode extends JSBuiltinNode {
      private final BranchProfile detachedBuffer = BranchProfile.create();

      public AtomicsOperationNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      public static boolean isSharedBufferView(JSTypedArrayObject object) {
         return JSArrayBufferView.isJSArrayBufferView(object) && JSSharedArrayBuffer.isJSSharedArrayBuffer(JSArrayBufferView.getArrayBuffer(object));
      }

      public static boolean isInt32SharedBufferView(JSTypedArrayObject object) {
         return isSharedBufferView(object) && JSArrayBufferView.typedArrayGetArrayType(object) instanceof TypedArray.DirectInt32Array;
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
         if (!this.getContext().getTypedArrayNotDetachedAssumption().isValid()) {
            if (JSArrayBuffer.isDetachedBuffer(JSArrayBufferView.getArrayBuffer(object))) {
               this.detachedBuffer.enter();
               throw this.createTypeErrorNotDetachedArray();
            }
         }
      }

      protected static int validateAtomicAccess(JSTypedArrayObject target, long convertedIndex, Object originalIndex) {
         int length = JSArrayBufferView.typedArrayGetLength(target);

         assert convertedIndex >= 0L;

         if (convertedIndex >= length) {
            throw createRangeErrorSharedArray(originalIndex);
         } else {
            return (int)convertedIndex;
         }
      }

      protected JSTypedArrayObject validateTypedArray(Object object) {
         if (!JSArrayBufferView.isJSArrayBufferView(object)) {
            throw this.createTypeErrorNotTypedArray();
         } else {
            JSTypedArrayObject typedArrayObject = (JSTypedArrayObject)object;
            this.checkDetached(typedArrayObject);
            return typedArrayObject;
         }
      }

      protected TypedArray validateIntegerTypedArray(JSTypedArrayObject typedArrayObject, boolean waitable) {
         TypedArray ta = JSArrayBufferView.typedArrayGetArrayType(typedArrayObject);
         if (waitable) {
            if (!(ta instanceof TypedArray.DirectInt32Array)
               && !(ta instanceof TypedArray.DirectBigInt64Array)
               && !(ta instanceof TypedArray.Int32Array)
               && !(ta instanceof TypedArray.BigInt64Array)
               && !(ta instanceof TypedArray.InteropInt32Array)
               && !(ta instanceof TypedArray.InteropBigInt64Array)) {
               throw this.createTypeErrorNotWaitableIntArray();
            }
         } else if (!(ta instanceof TypedArray.DirectInt8Array)
            && !(ta instanceof TypedArray.DirectUint8Array)
            && !(ta instanceof TypedArray.DirectInt16Array)
            && !(ta instanceof TypedArray.DirectUint16Array)
            && !(ta instanceof TypedArray.DirectInt32Array)
            && !(ta instanceof TypedArray.DirectUint32Array)
            && !(ta instanceof TypedArray.DirectBigInt64Array)
            && !(ta instanceof TypedArray.DirectBigUint64Array)
            && !(ta instanceof TypedArray.Int8Array)
            && !(ta instanceof TypedArray.Uint8Array)
            && !(ta instanceof TypedArray.Int16Array)
            && !(ta instanceof TypedArray.Uint16Array)
            && !(ta instanceof TypedArray.Int32Array)
            && !(ta instanceof TypedArray.Uint32Array)
            && !(ta instanceof TypedArray.BigInt64Array)
            && !(ta instanceof TypedArray.BigUint64Array)
            && !(ta instanceof TypedArray.InteropInt8Array)
            && !(ta instanceof TypedArray.InteropUint8Array)
            && !(ta instanceof TypedArray.InteropInt16Array)
            && !(ta instanceof TypedArray.InteropUint16Array)
            && !(ta instanceof TypedArray.InteropInt32Array)
            && !(ta instanceof TypedArray.InteropUint32Array)
            && !(ta instanceof TypedArray.InteropBigInt64Array)
            && !(ta instanceof TypedArray.InteropBigUint64Array)) {
            throw this.createTypeErrorNotIntArray();
         }

         return ta;
      }

      @CompilerDirectives.TruffleBoundary
      protected final JSException createTypeErrorNotDetachedArray() {
         return Errors.createTypeError("Cannot execute on detached array.", this);
      }

      @CompilerDirectives.TruffleBoundary
      protected final JSException createTypeErrorNotTypedArray() {
         return Errors.createTypeError("Cannot execute on non-typed array.", this);
      }

      @CompilerDirectives.TruffleBoundary
      protected final JSException createTypeErrorNotSharedArray() {
         return Errors.createTypeError("Cannot execute on non-shared array.", this);
      }

      @CompilerDirectives.TruffleBoundary
      protected final JSException createTypeErrorNotIntArray() {
         return Errors.createTypeError(
            "Can only execute on selected types of int typed arrays (\"Int8Array\", \"Uint8Array\", \"Int16Array\", \"Uint16Array\",  \"Int32Array\", \"Uint32Array\", \"BigUint64Array\", or \"BigInt64Array\").",
            this
         );
      }

      @CompilerDirectives.TruffleBoundary
      protected final JSException createTypeErrorNotWaitableIntArray() {
         return Errors.createTypeError("Can only execute on Int32Array or BigInt64Array typed arrays.", this);
      }

      @CompilerDirectives.TruffleBoundary
      protected static final JSException createRangeErrorSharedArray(Object idx) {
         return Errors.createRangeError("Range error with index : " + idx);
      }

      @CompilerDirectives.TruffleBoundary
      protected final JSException createTypeErrorUnsupported() {
         return Errors.createTypeError("Unsupported operation", this);
      }
   }

   public abstract static class AtomicsStoreNode extends AtomicsBuiltins.AtomicsOperationNode {
      @Node.Child
      private JSToInt32Node toIntNode;
      @Node.Child
      private JSToBigIntNode toBigIntNode;
      @Node.Child
      private JSToIntegerOrInfinityNode toIntOrInfNode;

      public AtomicsStoreNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization(guards = {"isSharedBufferView(target)", "isDirectInt8Array(ta)", "ta.isInBoundsFast(target, index)"})
      protected int doSharedInt8Array(JSTypedArrayObject target, int index, int value, @Bind("typedArrayGetArrayType(target)") TypedArray ta) {
         SharedMemorySync.doVolatilePut(target, index, value, (TypedArray.DirectInt8Array)ta);
         return value;
      }

      @Specialization(guards = {"isSharedBufferView(target)", "isDirectUint8Array(ta)", "ta.isInBoundsFast(target, index)"})
      protected int doSharedUint8Array(JSTypedArrayObject target, int index, int value, @Bind("typedArrayGetArrayType(target)") TypedArray ta) {
         SharedMemorySync.doVolatilePut(target, index, value, (TypedArray.DirectUint8Array)ta);
         return value;
      }

      @Specialization(guards = {"isSharedBufferView(target)", "isDirectInt8Array(ta)", "ta.isInBoundsFast(target, index)"})
      protected Number doSharedInt8Array(JSTypedArrayObject target, int index, Object value, @Bind("typedArrayGetArrayType(target)") TypedArray ta) {
         Number v = this.toIntegerOrInfinity(value);
         SharedMemorySync.doVolatilePut(target, index, this.toRaw(v), (TypedArray.DirectInt8Array)ta);
         return v;
      }

      @Specialization(guards = {"isSharedBufferView(target)", "isDirectUint8Array(ta)", "ta.isInBoundsFast(target, index)"})
      protected Number doSharedUint8Array(JSTypedArrayObject target, int index, Object value, @Bind("typedArrayGetArrayType(target)") TypedArray ta) {
         Number v = this.toIntegerOrInfinity(value);
         SharedMemorySync.doVolatilePut(target, index, this.toRaw(v), (TypedArray.DirectUint8Array)ta);
         return v;
      }

      @Specialization(guards = {"isSharedBufferView(target)", "isDirectInt16Array(ta)", "ta.isInBoundsFast(target, index)"})
      protected Object doSharedInt16Array(JSTypedArrayObject target, int index, int value, @Bind("typedArrayGetArrayType(target)") TypedArray ta) {
         SharedMemorySync.doVolatilePut(target, index, (short)value, (TypedArray.DirectInt16Array)ta);
         return value;
      }

      @Specialization(guards = {"isSharedBufferView(target)", "isDirectUint16Array(ta)", "ta.isInBoundsFast(target, index)"})
      protected Object doSharedUint16Array(JSTypedArrayObject target, int index, int value, @Bind("typedArrayGetArrayType(target)") TypedArray ta) {
         SharedMemorySync.doVolatilePut(target, index, (short)value, (TypedArray.DirectUint16Array)ta);
         return value;
      }

      @Specialization(guards = {"isSharedBufferView(target)", "isDirectInt16Array(ta)", "ta.isInBoundsFast(target, index)"})
      protected Number doSharedInt16Array(JSTypedArrayObject target, int index, Object value, @Bind("typedArrayGetArrayType(target)") TypedArray ta) {
         Number v = this.toIntegerOrInfinity(value);
         SharedMemorySync.doVolatilePut(target, index, this.toRaw(v), (TypedArray.DirectInt16Array)ta);
         return v;
      }

      @Specialization(guards = {"isSharedBufferView(target)", "isDirectUint16Array(ta)", "ta.isInBoundsFast(target, index)"})
      protected Number doSharedUint16Array(JSTypedArrayObject target, int index, Object value, @Bind("typedArrayGetArrayType(target)") TypedArray ta) {
         Number v = this.toIntegerOrInfinity(value);
         SharedMemorySync.doVolatilePut(target, index, this.toRaw(v), (TypedArray.DirectUint16Array)ta);
         return v;
      }

      @Specialization(guards = {"isSharedBufferView(target)", "isDirectInt32Array(ta)", "ta.isInBoundsFast(target, index)"})
      protected int doSharedInt32Array(JSTypedArrayObject target, int index, int value, @Bind("typedArrayGetArrayType(target)") TypedArray ta) {
         SharedMemorySync.doVolatilePut(target, index, value, (TypedArray.DirectInt32Array)ta);
         return value;
      }

      @Specialization(guards = {"isSharedBufferView(target)", "isDirectUint32Array(ta)", "ta.isInBoundsFast(target, index)"})
      protected int doSharedUint32Array(JSTypedArrayObject target, int index, int value, @Bind("typedArrayGetArrayType(target)") TypedArray ta) {
         SharedMemorySync.doVolatilePut(target, index, value, (TypedArray.DirectUint32Array)ta);
         return value;
      }

      @Specialization(guards = {"isSharedBufferView(target)", "isDirectInt32Array(ta)", "ta.isInBoundsFast(target, index)"})
      protected Object doSharedInt32Array(JSTypedArrayObject target, int index, Object value, @Bind("typedArrayGetArrayType(target)") TypedArray ta) {
         Number v = this.toIntegerOrInfinity(value);
         SharedMemorySync.doVolatilePut(target, index, this.toRaw(v), (TypedArray.DirectInt32Array)ta);
         return v;
      }

      @Specialization(guards = {"isSharedBufferView(target)", "isDirectUint32Array(ta)", "ta.isInBoundsFast(target, index)"})
      protected Object doSharedUint32Array(JSTypedArrayObject target, int index, Object value, @Bind("typedArrayGetArrayType(target)") TypedArray ta) {
         Number v = this.toIntegerOrInfinity(value);
         SharedMemorySync.doVolatilePut(target, index, this.toRaw(v), (TypedArray.DirectUint32Array)ta);
         return v;
      }

      @Specialization(guards = {"isSharedBufferView(target)", "isDirectInt32Array(ta)"})
      protected Object doSharedInt32ArrayObjIdx(
         JSTypedArrayObject target,
         Object index,
         int value,
         @Bind("typedArrayGetArrayType(target)") TypedArray ta,
         @Cached @Cached.Shared("toIndex") JSToIndexNode toIndexNode
      ) {
         int intIndex = validateAtomicAccess(target, toIndexNode.executeLong(index), index);
         SharedMemorySync.doVolatilePut(target, intIndex, value, (TypedArray.DirectInt32Array)ta);
         return value;
      }

      @Specialization(guards = {"isSharedBufferView(target)", "isDirectBigInt64Array(ta)"})
      protected Object doSharedBigInt64Array(
         JSTypedArrayObject target,
         Object index,
         Object value,
         @Bind("typedArrayGetArrayType(target)") TypedArray ta,
         @Cached @Cached.Shared("toIndex") JSToIndexNode toIndexNode
      ) {
         int intIndex = validateAtomicAccess(target, toIndexNode.executeLong(index), index);
         BigInt biValue = this.toBigInt(value, target);
         SharedMemorySync.doVolatilePutBigInt(target, intIndex, biValue, (TypedArray.DirectBigInt64Array)ta);
         return biValue;
      }

      @Specialization(guards = {"isSharedBufferView(target)", "isDirectBigUint64Array(ta)"})
      protected Object doSharedBigUint64Array(
         JSTypedArrayObject target,
         Object index,
         Object value,
         @Bind("typedArrayGetArrayType(target)") TypedArray ta,
         @Cached @Cached.Shared("toIndex") JSToIndexNode toIndexNode
      ) {
         int intIndex = validateAtomicAccess(target, toIndexNode.executeLong(index), index);
         BigInt biValue = this.toBigInt(value, target);
         SharedMemorySync.doVolatilePutBigInt(target, intIndex, biValue, (TypedArray.DirectBigUint64Array)ta);
         return biValue;
      }

      @Specialization
      protected Object doGeneric(Object maybeTarget, Object index, Object value, @Cached @Cached.Shared("toIndex") JSToIndexNode toIndexNode) {
         JSTypedArrayObject target = this.validateTypedArray(maybeTarget);
         TypedArray ta = this.validateIntegerTypedArray(target, false);
         int intIndex = validateAtomicAccess(target, toIndexNode.executeLong(index), index);
         if (ta instanceof TypedArray.DirectInt8Array
            || ta instanceof TypedArray.DirectUint8Array
            || ta instanceof TypedArray.Int8Array
            || ta instanceof TypedArray.Uint8Array
            || ta instanceof TypedArray.InteropInt8Array
            || ta instanceof TypedArray.InteropUint8Array) {
            Number v = this.toIntegerOrInfinityChecked(value, target);
            SharedMemorySync.doVolatilePut(target, intIndex, this.toRaw(v), (TypedArray.TypedIntArray)ta);
            return v;
         } else if (ta instanceof TypedArray.DirectInt16Array
            || ta instanceof TypedArray.DirectUint16Array
            || ta instanceof TypedArray.Int16Array
            || ta instanceof TypedArray.Uint16Array
            || ta instanceof TypedArray.InteropInt16Array
            || ta instanceof TypedArray.InteropUint16Array) {
            Number v = this.toIntegerOrInfinityChecked(value, target);
            SharedMemorySync.doVolatilePut(target, intIndex, (short)this.toRaw(v), (TypedArray.TypedIntArray)ta);
            return v;
         } else if (ta instanceof TypedArray.DirectInt32Array
            || ta instanceof TypedArray.DirectUint32Array
            || ta instanceof TypedArray.Int32Array
            || ta instanceof TypedArray.Uint32Array
            || ta instanceof TypedArray.InteropInt32Array
            || ta instanceof TypedArray.InteropUint32Array) {
            Number v = this.toIntegerOrInfinityChecked(value, target);
            SharedMemorySync.doVolatilePut(target, intIndex, this.toRaw(v), (TypedArray.TypedIntArray)ta);
            return v;
         } else if (!(ta instanceof TypedArray.DirectBigInt64Array)
            && !(ta instanceof TypedArray.DirectBigUint64Array)
            && !(ta instanceof TypedArray.BigInt64Array)
            && !(ta instanceof TypedArray.BigUint64Array)
            && !(ta instanceof TypedArray.InteropBigInt64Array)
            && !(ta instanceof TypedArray.InteropBigUint64Array)) {
            throw Errors.shouldNotReachHere();
         } else {
            BigInt v = this.toBigInt(value, target);
            SharedMemorySync.doVolatilePutBigInt(target, intIndex, v, (TypedArray.TypedBigIntArray)ta);
            return v;
         }
      }

      private Number toIntegerOrInfinity(Object value) {
         if (this.toIntOrInfNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.toIntOrInfNode = this.insert(JSToIntegerOrInfinityNode.create());
         }

         return this.toIntOrInfNode.executeNumber(value);
      }

      private Number toIntegerOrInfinityChecked(Object value, JSTypedArrayObject target) {
         Number result = this.toIntegerOrInfinity(value);
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

   public abstract static class AtomicsWaitAsyncNode extends AtomicsBuiltins.AtomicsWaitBaseNode {
      public AtomicsWaitAsyncNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization
      protected Object doGeneric(VirtualFrame frame, Object maybeTarget, Object index, Object value, Object timeout) {
         return this.doWait(frame, maybeTarget, index, value, timeout, true);
      }
   }

   public abstract static class AtomicsWaitBaseNode extends AtomicsBuiltins.AtomicsOperationNode {
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
      private AtomicsBuiltins.AtomicsLoadNode loadNode = this.createHelperNode();
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

      protected AtomicsBuiltins.AtomicsLoadNode createHelperNode() {
         return AtomicsBuiltinsFactory.AtomicsLoadNodeGen.create(
            this.getContext(), this.getBuiltin(), JSBuiltinsContainer.args().fixedArgs(4).createArgumentNodes(this.getContext())
         );
      }

      protected Object doWait(VirtualFrame frame, Object maybeTarget, Object index, Object value, Object timeout, boolean isAsync) {
         JSTypedArrayObject target = this.validateTypedArray(maybeTarget);
         this.validateIntegerTypedArray(target, true);
         if (!isSharedBufferView(target)) {
            this.notSharedArrayBuffer.enter();
            throw this.createTypeErrorNotSharedArray();
         } else {
            int i = validateAtomicAccess(target, this.toIndexNode.executeLong(index), index);
            boolean isInt32 = isInt32SharedBufferView(target);
            long v = isInt32 ? this.toInt32(value) : this.toBigInt(value).longValue();
            double q = this.toDoubleNode.executeDouble(timeout);
            double t;
            if (this.timeoutNaNProfile.profile(JSRuntime.isNaN(q))) {
               t = Double.POSITIVE_INFINITY;
            } else {
               t = Math.max(q, 0.0);
            }

            JSAgent agent = this.getRealm().getAgent();
            if (!isAsync && !agent.canBlock()) {
               this.errorBranch.enter();
               throw this.createTypeErrorUnsupported();
            } else {
               JSAgentWaiterList.JSAgentWaiterListEntry wl = SharedMemorySync.getWaiterList(this.getContext(), target, i);
               PromiseCapabilityRecord promiseCapability = null;
               JSDynamicObject resultObject = null;
               if (this.isAsyncProfile.profile(isAsync)) {
                  this.getContext().signalAsyncWaiterRecordUsage();
                  promiseCapability = this.newPromiseCapability();
                  resultObject = this.ordinaryObjectCreate(frame);
               }

               wl.enterCriticalSection();

               TruffleString id;
               try {
                  Object w = this.loadNode.executeWithBufferAndIndex(frame, maybeTarget, i);
                  boolean isNotEqual = isInt32 ? !(w instanceof Integer) || (Integer)w != (int)v : !(w instanceof BigInt) || ((BigInt)w).longValue() != v;
                  if (!isNotEqual) {
                     if (isAsync && t == 0.0) {
                        this.asyncImmediateTimeoutBranch.enter();
                        this.createAsyncPropertyNode.executeVoid(resultObject, false);
                        this.createValuePropertyNode.executeVoid(resultObject, Strings.TIMED_OUT);
                        return resultObject;
                     }

                     int idx = agent.getSignifier();
                     JSAgentWaiterList.WaiterRecord waiterRecord = JSAgentWaiterList.WaiterRecord.create(idx, promiseCapability, t, Strings.OK, wl, agent);
                     SharedMemorySync.addWaiter(agent, wl, waiterRecord, isAsync);
                     if (this.isAsyncProfile.profile(isAsync)) {
                        this.createAsyncPropertyNode.executeVoid(resultObject, true);
                        this.createValuePropertyNode.executeVoid(resultObject, waiterRecord.getPromiseCapability().getPromise());
                        return resultObject;
                     }

                     boolean awoken = SharedMemorySync.suspendAgent(agent, wl, waiterRecord);
                     if (this.awokenProfile.profile(awoken)) {
                        assert !wl.contains(waiterRecord);

                        return Strings.OK;
                     }

                     SharedMemorySync.removeWaiter(wl, waiterRecord);
                     return Strings.TIMED_OUT;
                  }

                  this.valuesNotEqualBranch.enter();
                  if (this.isAsyncProfile.profile(isAsync)) {
                     this.createAsyncPropertyNode.executeVoid(resultObject, false);
                     this.createValuePropertyNode.executeVoid(resultObject, Strings.NOT_EQUAL);
                     return resultObject;
                  }

                  id = Strings.NOT_EQUAL;
               } finally {
                  wl.leaveCriticalSection();
               }

               return id;
            }
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

   public abstract static class AtomicsWaitNode extends AtomicsBuiltins.AtomicsWaitBaseNode {
      public AtomicsWaitNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization
      protected Object doGeneric(VirtualFrame frame, Object maybeTarget, Object index, Object value, Object timeout) {
         return this.doWait(frame, maybeTarget, index, value, timeout, false);
      }
   }
}
