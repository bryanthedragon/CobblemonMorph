package com.oracle.truffle.js.builtins;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.builtins.helper.GCNodeGen;
import com.oracle.truffle.js.builtins.helper.SharedMemorySync;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.access.PropertyGetNode;
import com.oracle.truffle.js.nodes.access.PropertySetNode;
import com.oracle.truffle.js.nodes.cast.JSToBooleanNode;
import com.oracle.truffle.js.nodes.cast.JSToIndexNode;
import com.oracle.truffle.js.nodes.cast.JSToLengthNode;
import com.oracle.truffle.js.nodes.cast.JSToNumberNode;
import com.oracle.truffle.js.nodes.cast.JSToPrimitiveNode;
import com.oracle.truffle.js.nodes.cast.JSToStringNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.nodes.function.JSBuiltinNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSAgent;
import com.oracle.truffle.js.runtime.JSAgentWaiterList;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSException;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.Symbol;
import com.oracle.truffle.js.runtime.builtins.BuiltinEnum;
import com.oracle.truffle.js.runtime.builtins.JSArrayBufferView;
import com.oracle.truffle.js.runtime.builtins.JSFunction;
import com.oracle.truffle.js.runtime.builtins.JSFunctionObject;
import com.oracle.truffle.js.runtime.builtins.JSOrdinary;
import com.oracle.truffle.js.runtime.builtins.JSSharedArrayBuffer;
import com.oracle.truffle.js.runtime.builtins.JSTestV8;
import com.oracle.truffle.js.runtime.objects.IteratorRecord;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.Undefined;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public final class TestV8Builtins extends JSBuiltinsContainer.SwitchEnum<TestV8Builtins.TestV8> {
   public static final JSBuiltinsContainer BUILTINS = new TestV8Builtins();

   protected TestV8Builtins() {
      super(JSTestV8.CLASS_NAME, TestV8Builtins.TestV8.class);
   }

   protected Object createNode(JSContext context, JSBuiltin builtin, boolean construct, boolean newTarget, TestV8Builtins.TestV8 builtinEnum) {
      switch (builtinEnum) {
         case class_:
            return DebugBuiltinsFactory.DebugClassNodeGen.create(context, builtin, true, args().fixedArgs(1).createArgumentNodes(context));
         case className:
            return DebugBuiltinsFactory.DebugClassNameNodeGen.create(context, builtin, args().fixedArgs(1).createArgumentNodes(context));
         case createAsyncFromSyncIterator:
            return TestV8BuiltinsFactory.TestV8CreateAsyncFromSyncIteratorNodeGen.create(context, builtin, args().fixedArgs(1).createArgumentNodes(context));
         case runMicrotasks:
            return TestV8BuiltinsFactory.TestV8RunMicrotasksNodeGen.create(context, builtin, args().createArgumentNodes(context));
         case enqueueJob:
            return TestV8BuiltinsFactory.TestV8EnqueueJobNodeGen.create(context, builtin, args().fixedArgs(1).createArgumentNodes(context));
         case setTimeout:
            return TestV8BuiltinsFactory.TestV8SetTimeoutNodeGen.create(context, builtin, args().fixedArgs(1).createArgumentNodes(context));
         case stringCompare:
            return DebugBuiltinsFactory.DebugStringCompareNodeGen.create(context, builtin, args().fixedArgs(2).createArgumentNodes(context));
         case typedArrayDetachBuffer:
            return DebugBuiltinsFactory.DebugTypedArrayDetachBufferNodeGen.create(context, builtin, args().fixedArgs(1).createArgumentNodes(context));
         case constructDouble:
            return TestV8BuiltinsFactory.TestV8ConstructDoubleNodeGen.create(context, builtin, args().fixedArgs(2).createArgumentNodes(context));
         case doubleHi:
            return TestV8BuiltinsFactory.TestV8DoublePartNodeGen.create(context, builtin, true, args().fixedArgs(1).createArgumentNodes(context));
         case doubleLo:
            return TestV8BuiltinsFactory.TestV8DoublePartNodeGen.create(context, builtin, false, args().fixedArgs(1).createArgumentNodes(context));
         case deoptimize:
            return DebugBuiltinsFactory.DebugContinueInInterpreterNodeGen.create(context, builtin, true, args().createArgumentNodes(context));
         case gc:
            return GCNodeGen.create(context, builtin, args().createArgumentNodes(context));
         case referenceEqual:
            return TestV8BuiltinsFactory.TestV8ReferenceEqualNodeGen.create(context, builtin, args().fixedArgs(2).createArgumentNodes(context));
         case toStringConv:
            return TestV8BuiltinsFactory.TestV8ToStringNodeGen.create(context, builtin, args().fixedArgs(1).createArgumentNodes(context));
         case toName:
            return TestV8BuiltinsFactory.TestV8ToNameNodeGen.create(context, builtin, args().fixedArgs(1).createArgumentNodes(context));
         case toPrimitive:
            return TestV8BuiltinsFactory.TestV8ToPrimitiveNodeGen.create(
               context, builtin, JSToPrimitiveNode.Hint.Default, args().fixedArgs(1).createArgumentNodes(context)
            );
         case toPrimitiveString:
            return TestV8BuiltinsFactory.TestV8ToPrimitiveNodeGen.create(
               context, builtin, JSToPrimitiveNode.Hint.String, args().fixedArgs(1).createArgumentNodes(context)
            );
         case toPrimitiveNumber:
            return TestV8BuiltinsFactory.TestV8ToPrimitiveNodeGen.create(
               context, builtin, JSToPrimitiveNode.Hint.Number, args().fixedArgs(1).createArgumentNodes(context)
            );
         case toNumber:
            return TestV8BuiltinsFactory.TestV8ToNumberNodeGen.create(context, builtin, args().fixedArgs(1).createArgumentNodes(context));
         case toLength:
            return TestV8BuiltinsFactory.TestV8ToLengthNodeGen.create(context, builtin, args().fixedArgs(1).createArgumentNodes(context));
         case atomicsNumWaitersForTesting:
            return TestV8BuiltinsFactory.TestV8AtomicsNumWaitersForTestingNodeGen.create(context, builtin, args().fixedArgs(2).createArgumentNodes(context));
         case atomicsNumUnresolvedAsyncPromisesForTesting:
            return TestV8BuiltinsFactory.TestV8AtomicsNumUnresolvedAsyncPromisesForTestingNodeGen.create(
               context, builtin, args().fixedArgs(2).createArgumentNodes(context)
            );
         case setAllowAtomicsWait:
            return TestV8BuiltinsFactory.TestV8SetAllowAtomicsWaitNodeGen.create(context, builtin, args().fixedArgs(1).createArgumentNodes(context));
         default:
            return null;
      }
   }

   public static enum TestV8 implements BuiltinEnum<TestV8Builtins.TestV8> {
      class_(1),
      className(1),
      createAsyncFromSyncIterator(1),
      runMicrotasks(0),
      enqueueJob(1),
      setTimeout(1),
      stringCompare(2),
      typedArrayDetachBuffer(1),
      constructDouble(2),
      doubleHi(1),
      doubleLo(1),
      deoptimize(0),
      gc(0),
      referenceEqual(2),
      toLength(1),
      toStringConv(1),
      toName(1),
      toNumber(1),
      toPrimitive(1),
      toPrimitiveString(1),
      toPrimitiveNumber(1),
      atomicsNumWaitersForTesting(2),
      atomicsNumUnresolvedAsyncPromisesForTesting(2),
      setAllowAtomicsWait(1);

      private final int length;

      private TestV8(int length) {
         this.length = length;
      }

      @Override
      public int getLength() {
         return this.length;
      }
   }

   public abstract static class TestV8AtomicsBaseNode extends JSBuiltinNode {
      public TestV8AtomicsBaseNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      protected JSDynamicObject ensureSharedArray(Object maybeTarget) {
         if (JSArrayBufferView.isJSArrayBufferView(maybeTarget)) {
            JSDynamicObject buffer = JSArrayBufferView.getArrayBuffer((JSDynamicObject)maybeTarget);
            if (JSSharedArrayBuffer.isJSSharedArrayBuffer(buffer)) {
               return (JSDynamicObject)maybeTarget;
            }
         }

         throw this.createTypeErrorNotSharedArray();
      }

      @CompilerDirectives.TruffleBoundary
      protected final JSException createTypeErrorNotSharedArray() {
         return Errors.createTypeError("Cannot execute on non-shared array.", this);
      }

      @CompilerDirectives.TruffleBoundary
      protected static final JSException createRangeErrorSharedArray(Object idx) {
         return Errors.createRangeError("Range error with index : " + idx);
      }

      protected static int validateAtomicAccess(JSDynamicObject target, long convertedIndex, Object originalIndex) {
         int length = JSArrayBufferView.typedArrayGetLength(target);

         assert convertedIndex >= 0L;

         if (convertedIndex >= length) {
            throw createRangeErrorSharedArray(originalIndex);
         } else {
            return (int)convertedIndex;
         }
      }
   }

   public abstract static class TestV8AtomicsNumUnresolvedAsyncPromisesForTestingNode extends TestV8Builtins.TestV8AtomicsBaseNode {
      public TestV8AtomicsNumUnresolvedAsyncPromisesForTestingNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization
      protected Object numUnresolvedAsyncPromises(Object maybeTarget, Object index, @Cached("create()") JSToIndexNode toIndexNode) {
         JSDynamicObject target = this.ensureSharedArray(maybeTarget);
         int i = validateAtomicAccess(target, toIndexNode.executeLong(index), index);
         JSAgent agent = this.getRealm().getAgent();
         JSAgentWaiterList.JSAgentWaiterListEntry wl = SharedMemorySync.getWaiterList(this.getContext(), target, i);
         return agent.getAsyncWaitersToBeResolved(wl);
      }
   }

   public abstract static class TestV8AtomicsNumWaitersForTestingNode extends TestV8Builtins.TestV8AtomicsBaseNode {
      public TestV8AtomicsNumWaitersForTestingNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization
      protected Object numWaiters(Object maybeTarget, Object index, @Cached("create()") JSToIndexNode toIndexNode) {
         JSDynamicObject target = this.ensureSharedArray(maybeTarget);
         int i = validateAtomicAccess(target, toIndexNode.executeLong(index), index);
         JSAgentWaiterList.JSAgentWaiterListEntry wl = SharedMemorySync.getWaiterList(this.getContext(), target, i);
         return wl.size();
      }
   }

   public abstract static class TestV8ConstructDoubleNode extends JSBuiltinNode {
      public TestV8ConstructDoubleNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @CompilerDirectives.TruffleBoundary
      @Specialization
      protected double constructDouble(Object hiObj, Object loObj) {
         long hi = JSRuntime.toUInt32(hiObj);
         long lo = JSRuntime.toUInt32(loObj);
         return Double.longBitsToDouble(hi << 32 | lo);
      }
   }

   public abstract static class TestV8CreateAsyncFromSyncIterator extends JSBuiltinNode {
      @Node.Child
      private PropertySetNode setState;
      @Node.Child
      private PropertyGetNode getNextMethodNode;

      public TestV8CreateAsyncFromSyncIterator(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
         this.setState = PropertySetNode.createSetHidden(JSFunction.ASYNC_FROM_SYNC_ITERATOR_KEY, context);
         this.getNextMethodNode = PropertyGetNode.create(Strings.NEXT, context);
      }

      @Specialization(guards = "isJSObject(syncIterator)")
      protected Object createAsyncFromSyncIterator(JSDynamicObject syncIterator) {
         JSContext context = this.getContext();
         JSObject obj = JSOrdinary.create(context, context.getAsyncFromSyncIteratorFactory(), this.getRealm());
         IteratorRecord syncIteratorRecord = IteratorRecord.create(syncIterator, this.getNextMethodNode.getValue(syncIterator), false);
         this.setState.setValue(obj, syncIteratorRecord);
         return obj;
      }

      @Specialization(guards = "!isJSObject(syncIterator)")
      protected Object notObject(Object syncIterator) {
         throw Errors.createTypeErrorNotAnObject(syncIterator, this);
      }
   }

   public abstract static class TestV8DoublePartNode extends JSBuiltinNode {
      private final boolean upper;

      public TestV8DoublePartNode(JSContext context, JSBuiltin builtin, boolean upper) {
         super(context, builtin);
         this.upper = upper;
      }

      @Specialization
      protected int doublePart(Object value) {
         long bits = Double.doubleToRawLongBits((Double)value);
         return this.upper ? (int)(bits >>> 32) : (int)(bits & 4294967295L);
      }
   }

   public abstract static class TestV8EnqueueJobNode extends JSBuiltinNode {
      public TestV8EnqueueJobNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization
      protected Object enqueueJob(Object function) {
         if (JSFunction.isJSFunction(function)) {
            this.getContext().promiseEnqueueJob(this.getRealm(), (JSFunctionObject)function);
         }

         return 0;
      }
   }

   public abstract static class TestV8ReferenceEqualNode extends JSBuiltinNode {
      public TestV8ReferenceEqualNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization
      protected boolean referenceEqual(Object arg1, Object arg2) {
         return arg1 == arg2;
      }
   }

   public abstract static class TestV8RunMicrotasksNode extends JSBuiltinNode {
      public TestV8RunMicrotasksNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization
      protected Object runMicrotasks() {
         this.getContext().processAllPendingPromiseJobs(this.getRealm());
         return Undefined.instance;
      }
   }

   public abstract static class TestV8SetAllowAtomicsWait extends JSBuiltinNode {
      @Node.Child
      JSToBooleanNode toBooleanNode = JSToBooleanNode.create();

      public TestV8SetAllowAtomicsWait(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization
      protected Object setAllowAtomicsWait(Object allow) {
         this.getRealm().getAgent().setCanBlock(this.toBooleanNode.executeBoolean(allow));
         return Undefined.instance;
      }
   }

   public abstract static class TestV8SetTimeoutNode extends JSBuiltinNode {
      public TestV8SetTimeoutNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization
      @CompilerDirectives.TruffleBoundary
      protected Object setTimeout(Object callback) {
         assert JSRuntime.isCallable(callback);

         JSRealm realm = this.getRealm();
         List<Object> embedderData = (List<Object>)realm.getEmbedderData();
         if (embedderData == null) {
            embedderData = new ArrayList<>();
            realm.setEmbedderData(embedderData);
         }

         embedderData.add(callback);
         return Undefined.instance;
      }
   }

   public abstract static class TestV8ToLengthNode extends JSBuiltinNode {
      @Node.Child
      private JSToLengthNode toLengthNode = JSToLengthNode.create();

      public TestV8ToLengthNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization
      protected Object toLengthOp(Object obj) {
         long value = this.toLengthNode.executeLong(obj);
         double d = value;
         return JSRuntime.doubleIsRepresentableAsInt(d) ? (int)d : d;
      }

      @Override
      protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
         return TestV8BuiltinsFactory.TestV8ToLengthNodeGen.create(
            this.getContext(), this.getBuiltin(), cloneUninitialized(this.getArguments(), materializedTags)
         );
      }
   }

   public abstract static class TestV8ToNameNode extends JSBuiltinNode {
      @Node.Child
      private JSToStringNode toStringNode = JSToStringNode.create();

      public TestV8ToNameNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization
      protected Object toName(Object obj) {
         return obj instanceof Symbol ? obj : this.toStringNode.executeString(obj);
      }
   }

   public abstract static class TestV8ToNumberNode extends JSBuiltinNode {
      @Node.Child
      private JSToNumberNode toNumberNode = JSToNumberNode.create();

      public TestV8ToNumberNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization
      protected Number toNumberOp(Object obj) {
         return this.toNumberNode.executeNumber(obj);
      }
   }

   public abstract static class TestV8ToPrimitiveNode extends JSBuiltinNode {
      @Node.Child
      private JSToPrimitiveNode toPrimitiveNode;

      public TestV8ToPrimitiveNode(JSContext context, JSBuiltin builtin, JSToPrimitiveNode.Hint hint) {
         super(context, builtin);
         this.toPrimitiveNode = JSToPrimitiveNode.create(hint);
      }

      @Specialization
      protected Object toPrimitive(Object obj) {
         return this.toPrimitiveNode.execute(obj);
      }
   }

   public abstract static class TestV8ToStringNode extends JSBuiltinNode {
      @Node.Child
      private JSToStringNode toStringNode = JSToStringNode.create();

      public TestV8ToStringNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization
      protected TruffleString toStringConv(Object obj) {
         return this.toStringNode.executeString(obj);
      }
   }
}
