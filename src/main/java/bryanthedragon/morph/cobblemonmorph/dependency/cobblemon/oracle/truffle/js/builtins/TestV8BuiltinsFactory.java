
package com.oracle.truffle.js.builtins;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.js.builtins.TestV8Builtins;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.cast.JSToIndexNode;
import com.oracle.truffle.js.nodes.cast.JSToPrimitiveNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=TestV8Builtins.class)
public final class TestV8BuiltinsFactory {

    @GeneratedBy(value=TestV8Builtins.TestV8SetAllowAtomicsWait.class)
    public static final class TestV8SetAllowAtomicsWaitNodeGen
    extends TestV8Builtins.TestV8SetAllowAtomicsWait
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;

        private TestV8SetAllowAtomicsWaitNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            return this.setAllowAtomicsWait(arguments0Value_);
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
            s[0] = "setAllowAtomicsWait";
            s[1] = (byte)1;
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static TestV8Builtins.TestV8SetAllowAtomicsWait create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new TestV8SetAllowAtomicsWaitNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=TestV8Builtins.TestV8AtomicsNumUnresolvedAsyncPromisesForTestingNode.class)
    public static final class TestV8AtomicsNumUnresolvedAsyncPromisesForTestingNodeGen
    extends TestV8Builtins.TestV8AtomicsNumUnresolvedAsyncPromisesForTestingNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private JSToIndexNode toIndexNode_;

        private TestV8AtomicsNumUnresolvedAsyncPromisesForTestingNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            if (state_0 != 0) {
                return this.numUnresolvedAsyncPromises(arguments0Value_, arguments1Value_, this.toIndexNode_);
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
                int state_0 = this.state_0_;
                this.toIndexNode_ = super.insert(JSToIndexNode.create());
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                Object object = this.numUnresolvedAsyncPromises(arguments0Value, arguments1Value, this.toIndexNode_);
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
            s[0] = "numUnresolvedAsyncPromises";
            if (state_0 != 0) {
                s[1] = (byte)1;
                ArrayList<List<JSToIndexNode>> cached = new ArrayList<List<JSToIndexNode>>();
                cached.add(Arrays.asList(this.toIndexNode_));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static TestV8Builtins.TestV8AtomicsNumUnresolvedAsyncPromisesForTestingNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new TestV8AtomicsNumUnresolvedAsyncPromisesForTestingNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=TestV8Builtins.TestV8AtomicsNumWaitersForTestingNode.class)
    public static final class TestV8AtomicsNumWaitersForTestingNodeGen
    extends TestV8Builtins.TestV8AtomicsNumWaitersForTestingNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private JSToIndexNode toIndexNode_;

        private TestV8AtomicsNumWaitersForTestingNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            if (state_0 != 0) {
                return this.numWaiters(arguments0Value_, arguments1Value_, this.toIndexNode_);
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
                int state_0 = this.state_0_;
                this.toIndexNode_ = super.insert(JSToIndexNode.create());
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                Object object = this.numWaiters(arguments0Value, arguments1Value, this.toIndexNode_);
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
            s[0] = "numWaiters";
            if (state_0 != 0) {
                s[1] = (byte)1;
                ArrayList<List<JSToIndexNode>> cached = new ArrayList<List<JSToIndexNode>>();
                cached.add(Arrays.asList(this.toIndexNode_));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static TestV8Builtins.TestV8AtomicsNumWaitersForTestingNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new TestV8AtomicsNumWaitersForTestingNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=TestV8Builtins.TestV8SetTimeoutNode.class)
    public static final class TestV8SetTimeoutNodeGen
    extends TestV8Builtins.TestV8SetTimeoutNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;

        private TestV8SetTimeoutNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            return this.setTimeout(arguments0Value_);
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
            s[0] = "setTimeout";
            s[1] = (byte)1;
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static TestV8Builtins.TestV8SetTimeoutNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new TestV8SetTimeoutNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=TestV8Builtins.TestV8ReferenceEqualNode.class)
    public static final class TestV8ReferenceEqualNodeGen
    extends TestV8Builtins.TestV8ReferenceEqualNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;

        private TestV8ReferenceEqualNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            return this.referenceEqual(arguments0Value_, arguments1Value_);
        }

        @Override
        public boolean executeBoolean(VirtualFrame frameValue) {
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            return this.referenceEqual(arguments0Value_, arguments1Value_);
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
            s[0] = "referenceEqual";
            s[1] = (byte)1;
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static TestV8Builtins.TestV8ReferenceEqualNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new TestV8ReferenceEqualNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=TestV8Builtins.TestV8ToLengthNode.class)
    public static final class TestV8ToLengthNodeGen
    extends TestV8Builtins.TestV8ToLengthNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;

        private TestV8ToLengthNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            return this.toLengthOp(arguments0Value_);
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
            s[0] = "toLengthOp";
            s[1] = (byte)1;
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static TestV8Builtins.TestV8ToLengthNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new TestV8ToLengthNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=TestV8Builtins.TestV8CreateAsyncFromSyncIterator.class)
    public static final class TestV8CreateAsyncFromSyncIteratorNodeGen
    extends TestV8Builtins.TestV8CreateAsyncFromSyncIterator
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @CompilerDirectives.CompilationFinal
        private int state_0_;

        private TestV8CreateAsyncFromSyncIteratorNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
                return this.createAsyncFromSyncIterator(arguments0Value__);
            }
            if ((state_0 & 2) != 0 && !JSGuards.isJSObject(arguments0Value_)) {
                return this.notObject(arguments0Value_);
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
                return this.createAsyncFromSyncIterator(arguments0Value_);
            }
            if (!JSGuards.isJSObject(arguments0Value)) {
                this.state_0_ = state_0 |= 2;
                return this.notObject(arguments0Value);
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
            s[0] = "createAsyncFromSyncIterator";
            s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[1] = s;
            s = new Object[3];
            s[0] = "notObject";
            s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[2] = s;
            return Introspection.Provider.create(data);
        }

        public static TestV8Builtins.TestV8CreateAsyncFromSyncIterator create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new TestV8CreateAsyncFromSyncIteratorNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=TestV8Builtins.TestV8EnqueueJobNode.class)
    public static final class TestV8EnqueueJobNodeGen
    extends TestV8Builtins.TestV8EnqueueJobNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;

        private TestV8EnqueueJobNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            return this.enqueueJob(arguments0Value_);
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
            s[0] = "enqueueJob";
            s[1] = (byte)1;
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static TestV8Builtins.TestV8EnqueueJobNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new TestV8EnqueueJobNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=TestV8Builtins.TestV8RunMicrotasksNode.class)
    public static final class TestV8RunMicrotasksNodeGen
    extends TestV8Builtins.TestV8RunMicrotasksNode
    implements Introspection.Provider {
        private TestV8RunMicrotasksNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            super(context, builtin);
        }

        @Override
        public JavaScriptNode[] getArguments() {
            return new JavaScriptNode[0];
        }

        @Override
        public Object execute(VirtualFrame frameValue) {
            return this.runMicrotasks();
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
            s[0] = "runMicrotasks";
            s[1] = (byte)1;
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static TestV8Builtins.TestV8RunMicrotasksNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new TestV8RunMicrotasksNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=TestV8Builtins.TestV8ToNameNode.class)
    public static final class TestV8ToNameNodeGen
    extends TestV8Builtins.TestV8ToNameNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;

        private TestV8ToNameNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            return this.toName(arguments0Value_);
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
            s[0] = "toName";
            s[1] = (byte)1;
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static TestV8Builtins.TestV8ToNameNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new TestV8ToNameNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=TestV8Builtins.TestV8ToPrimitiveNode.class)
    public static final class TestV8ToPrimitiveNodeGen
    extends TestV8Builtins.TestV8ToPrimitiveNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;

        private TestV8ToPrimitiveNodeGen(JSContext context, JSBuiltin builtin, JSToPrimitiveNode.Hint hint, JavaScriptNode[] arguments) {
            super(context, builtin, hint);
            this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
        }

        @Override
        public JavaScriptNode[] getArguments() {
            return new JavaScriptNode[]{this.arguments0_};
        }

        @Override
        public Object execute(VirtualFrame frameValue) {
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            return this.toPrimitive(arguments0Value_);
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
            s[0] = "toPrimitive";
            s[1] = (byte)1;
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static TestV8Builtins.TestV8ToPrimitiveNode create(JSContext context, JSBuiltin builtin, JSToPrimitiveNode.Hint hint, JavaScriptNode[] arguments) {
            return new TestV8ToPrimitiveNodeGen(context, builtin, hint, arguments);
        }
    }

    @GeneratedBy(value=TestV8Builtins.TestV8ToNumberNode.class)
    public static final class TestV8ToNumberNodeGen
    extends TestV8Builtins.TestV8ToNumberNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;

        private TestV8ToNumberNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            return this.toNumberOp(arguments0Value_);
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
            s[0] = "toNumberOp";
            s[1] = (byte)1;
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static TestV8Builtins.TestV8ToNumberNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new TestV8ToNumberNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=TestV8Builtins.TestV8ToStringNode.class)
    public static final class TestV8ToStringNodeGen
    extends TestV8Builtins.TestV8ToStringNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;

        private TestV8ToStringNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            return this.toStringConv(arguments0Value_);
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
            s[0] = "toStringConv";
            s[1] = (byte)1;
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static TestV8Builtins.TestV8ToStringNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new TestV8ToStringNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=TestV8Builtins.TestV8DoublePartNode.class)
    public static final class TestV8DoublePartNodeGen
    extends TestV8Builtins.TestV8DoublePartNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;

        private TestV8DoublePartNodeGen(JSContext context, JSBuiltin builtin, boolean upper, JavaScriptNode[] arguments) {
            super(context, builtin, upper);
            this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
        }

        @Override
        public JavaScriptNode[] getArguments() {
            return new JavaScriptNode[]{this.arguments0_};
        }

        @Override
        public Object execute(VirtualFrame frameValue) {
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            return this.doublePart(arguments0Value_);
        }

        @Override
        public int executeInt(VirtualFrame frameValue) {
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            return this.doublePart(arguments0Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.executeInt(frameValue);
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
            s[0] = "doublePart";
            s[1] = (byte)1;
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static TestV8Builtins.TestV8DoublePartNode create(JSContext context, JSBuiltin builtin, boolean upper, JavaScriptNode[] arguments) {
            return new TestV8DoublePartNodeGen(context, builtin, upper, arguments);
        }
    }

    @GeneratedBy(value=TestV8Builtins.TestV8ConstructDoubleNode.class)
    public static final class TestV8ConstructDoubleNodeGen
    extends TestV8Builtins.TestV8ConstructDoubleNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;

        private TestV8ConstructDoubleNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            return this.constructDouble(arguments0Value_, arguments1Value_);
        }

        @Override
        public double executeDouble(VirtualFrame frameValue) {
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            return this.constructDouble(arguments0Value_, arguments1Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.executeDouble(frameValue);
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
            s[0] = "constructDouble";
            s[1] = (byte)1;
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static TestV8Builtins.TestV8ConstructDoubleNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new TestV8ConstructDoubleNodeGen(context, builtin, arguments);
        }
    }
}

