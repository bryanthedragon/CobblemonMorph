
package com.oracle.truffle.js.builtins;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.builtins.DebugBuiltins;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.SafeInteger;
import com.oracle.truffle.js.runtime.builtins.JSFunctionObject;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=DebugBuiltins.class)
public final class DebugBuiltinsFactory {

    @GeneratedBy(value=DebugBuiltins.DebugNeverPartOfCompilationNode.class)
    public static final class DebugNeverPartOfCompilationNodeGen
    extends DebugBuiltins.DebugNeverPartOfCompilationNode
    implements Introspection.Provider {
        private DebugNeverPartOfCompilationNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            super(context, builtin);
        }

        @Override
        public JavaScriptNode[] getArguments() {
            return new JavaScriptNode[0];
        }

        @Override
        public Object execute(VirtualFrame frameValue) {
            return DebugBuiltins.DebugNeverPartOfCompilationNode.neverPartOfCompilation();
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
            s[0] = "neverPartOfCompilation";
            s[1] = (byte)1;
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static DebugBuiltins.DebugNeverPartOfCompilationNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new DebugNeverPartOfCompilationNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=DebugBuiltins.DebugCreateSafeInteger.class)
    public static final class DebugCreateSafeIntegerNodeGen
    extends DebugBuiltins.DebugCreateSafeInteger
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @CompilerDirectives.CompilationFinal
        private int state_0_;

        private DebugCreateSafeIntegerNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            if ((state_0 & 6) == 0 && state_0 != 0) {
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
            return DebugBuiltins.DebugCreateSafeInteger.createSafeInteger(arguments0Value_);
        }

        private Object execute_generic1(int state_0, VirtualFrame frameValue) {
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            if ((state_0 & 1) != 0 && arguments0Value_ instanceof Integer) {
                int arguments0Value__ = (Integer)arguments0Value_;
                return DebugBuiltins.DebugCreateSafeInteger.createSafeInteger(arguments0Value__);
            }
            if ((state_0 & 2) != 0 && arguments0Value_ instanceof SafeInteger) {
                SafeInteger arguments0Value__ = (SafeInteger)arguments0Value_;
                return DebugBuiltins.DebugCreateSafeInteger.createSafeInteger(arguments0Value__);
            }
            if ((state_0 & 4) != 0) {
                return DebugBuiltins.DebugCreateSafeInteger.createSafeInteger(arguments0Value_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.execute(frameValue);
        }

        private SafeInteger executeAndSpecialize(Object arguments0Value) {
            int state_0 = this.state_0_;
            if (arguments0Value instanceof Integer) {
                int arguments0Value_ = (Integer)arguments0Value;
                this.state_0_ = state_0 |= 1;
                return DebugBuiltins.DebugCreateSafeInteger.createSafeInteger(arguments0Value_);
            }
            if (arguments0Value instanceof SafeInteger) {
                SafeInteger arguments0Value_ = (SafeInteger)arguments0Value;
                this.state_0_ = state_0 |= 2;
                return DebugBuiltins.DebugCreateSafeInteger.createSafeInteger(arguments0Value_);
            }
            this.state_0_ = state_0 |= 4;
            return DebugBuiltins.DebugCreateSafeInteger.createSafeInteger(arguments0Value);
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
            s[0] = "createSafeInteger";
            s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[1] = s;
            s = new Object[3];
            s[0] = "createSafeInteger";
            s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[2] = s;
            s = new Object[3];
            s[0] = "createSafeInteger";
            s[1] = (state_0 & 4) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[3] = s;
            return Introspection.Provider.create(data);
        }

        public static DebugBuiltins.DebugCreateSafeInteger create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new DebugCreateSafeIntegerNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=DebugBuiltins.DebugTypedArrayDetachBufferNode.class)
    public static final class DebugTypedArrayDetachBufferNodeGen
    extends DebugBuiltins.DebugTypedArrayDetachBufferNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;

        private DebugTypedArrayDetachBufferNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            return DebugBuiltins.DebugTypedArrayDetachBufferNode.detachBuffer(arguments0Value_);
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
            s[0] = "detachBuffer";
            s[1] = (byte)1;
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static DebugBuiltins.DebugTypedArrayDetachBufferNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new DebugTypedArrayDetachBufferNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=DebugBuiltins.DebugSystemProperty.class)
    public static final class DebugSystemPropertyNodeGen
    extends DebugBuiltins.DebugSystemProperty
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;

        private DebugSystemPropertyNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            return DebugBuiltins.DebugSystemProperty.systemProperty(arguments0Value_);
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
            s[0] = "systemProperty";
            s[1] = (byte)1;
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static DebugBuiltins.DebugSystemProperty create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new DebugSystemPropertyNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=DebugBuiltins.DebugSystemProperties.class)
    public static final class DebugSystemPropertiesNodeGen
    extends DebugBuiltins.DebugSystemProperties
    implements Introspection.Provider {
        private DebugSystemPropertiesNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            super(context, builtin);
        }

        @Override
        public JavaScriptNode[] getArguments() {
            return new JavaScriptNode[0];
        }

        @Override
        public Object execute(VirtualFrame frameValue) {
            return this.systemProperties();
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
            s[0] = "systemProperties";
            s[1] = (byte)1;
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static DebugBuiltins.DebugSystemProperties create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new DebugSystemPropertiesNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=DebugBuiltins.DebugLoadModuleNode.class)
    public static final class DebugLoadModuleNodeGen
    extends DebugBuiltins.DebugLoadModuleNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;

        private DebugLoadModuleNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            return this.loadModule(arguments0Value_, arguments1Value_);
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
            s[0] = "loadModule";
            s[1] = (byte)1;
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static DebugBuiltins.DebugLoadModuleNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new DebugLoadModuleNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=DebugBuiltins.DebugJSStackNode.class)
    public static final class DebugJSStackNodeGen
    extends DebugBuiltins.DebugJSStackNode
    implements Introspection.Provider {
        private DebugJSStackNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            super(context, builtin);
        }

        @Override
        public JavaScriptNode[] getArguments() {
            return new JavaScriptNode[0];
        }

        @Override
        public Object execute(VirtualFrame frameValue) {
            return this.printJSStack();
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
            s[0] = "printJSStack";
            s[1] = (byte)1;
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static DebugBuiltins.DebugJSStackNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new DebugJSStackNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=DebugBuiltins.DebugIsHolesArrayNode.class)
    public static final class DebugIsHolesArrayNodeGen
    extends DebugBuiltins.DebugIsHolesArrayNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;

        private DebugIsHolesArrayNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            super(context, builtin);
            this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
        }

        @Override
        public JavaScriptNode[] getArguments() {
            return new JavaScriptNode[]{this.arguments0_};
        }

        @Override
        public boolean executeBoolean(Object arguments0Value) {
            return this.isHolesArray(arguments0Value);
        }

        @Override
        public Object execute(VirtualFrame frameValue) {
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            return this.isHolesArray(arguments0Value_);
        }

        @Override
        public boolean executeBoolean(VirtualFrame frameValue) {
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            return this.isHolesArray(arguments0Value_);
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
            s[0] = "isHolesArray";
            s[1] = (byte)1;
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static DebugBuiltins.DebugIsHolesArrayNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new DebugIsHolesArrayNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=DebugBuiltins.DebugStringCompareNode.class)
    public static final class DebugStringCompareNodeGen
    extends DebugBuiltins.DebugStringCompareNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private TruffleString.CompareCharsUTF16Node compareNode_;

        private DebugStringCompareNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
                return this.stringCompare(arguments0Value_, arguments1Value_, this.compareNode_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
        }

        @Override
        public int executeInt(VirtualFrame frameValue) {
            int state_0 = this.state_0_;
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            if (state_0 != 0) {
                return this.stringCompare(arguments0Value_, arguments1Value_, this.compareNode_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.executeInt(frameValue);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private int executeAndSpecialize(Object arguments0Value, Object arguments1Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                this.compareNode_ = super.insert(TruffleString.CompareCharsUTF16Node.create());
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                int n = this.stringCompare(arguments0Value, arguments1Value, this.compareNode_);
                return n;
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
            s[0] = "stringCompare";
            if (state_0 != 0) {
                s[1] = (byte)1;
                ArrayList<List<TruffleString.CompareCharsUTF16Node>> cached = new ArrayList<List<TruffleString.CompareCharsUTF16Node>>();
                cached.add(Arrays.asList(this.compareNode_));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static DebugBuiltins.DebugStringCompareNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new DebugStringCompareNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=DebugBuiltins.DebugHeapDumpNode.class)
    public static final class DebugHeapDumpNodeGen
    extends DebugBuiltins.DebugHeapDumpNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;

        private DebugHeapDumpNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            return this.heapDump(arguments0Value_, arguments1Value_);
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
            s[0] = "heapDump";
            s[1] = (byte)1;
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static DebugBuiltins.DebugHeapDumpNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new DebugHeapDumpNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=DebugBuiltins.DebugAssertIntNode.class)
    public static final class DebugAssertIntNodeGen
    extends DebugBuiltins.DebugAssertIntNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;

        private DebugAssertIntNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            return this.assertInt(arguments0Value_, arguments1Value_);
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
            s[0] = "assertInt";
            s[1] = (byte)1;
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static DebugBuiltins.DebugAssertIntNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new DebugAssertIntNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=DebugBuiltins.DebugArrayTypeNode.class)
    public static final class DebugArrayTypeNodeGen
    extends DebugBuiltins.DebugArrayTypeNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;

        private DebugArrayTypeNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            return this.arraytype(arguments0Value_);
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
            s[0] = "arraytype";
            s[1] = (byte)1;
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static DebugBuiltins.DebugArrayTypeNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new DebugArrayTypeNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=DebugBuiltins.DebugPrintSourceAttribution.class)
    public static final class DebugPrintSourceAttributionNodeGen
    extends DebugBuiltins.DebugPrintSourceAttribution
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @CompilerDirectives.CompilationFinal
        private int state_0_;

        private DebugPrintSourceAttributionNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSFunctionObject) {
                JSFunctionObject arguments0Value__ = (JSFunctionObject)arguments0Value_;
                return this.printSourceAttribution(arguments0Value__);
            }
            if ((state_0 & 2) != 0 && arguments0Value_ instanceof TruffleString) {
                TruffleString arguments0Value__ = (TruffleString)arguments0Value_;
                return this.printSourceAttribution(arguments0Value__);
            }
            if ((state_0 & 4) != 0 && DebugPrintSourceAttributionNodeGen.fallbackGuard_(state_0, arguments0Value_)) {
                return this.illegalArgument(arguments0Value_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.execute(frameValue);
        }

        private Object executeAndSpecialize(Object arguments0Value) {
            int state_0 = this.state_0_;
            if (arguments0Value instanceof JSFunctionObject) {
                JSFunctionObject arguments0Value_ = (JSFunctionObject)arguments0Value;
                this.state_0_ = state_0 |= 1;
                return this.printSourceAttribution(arguments0Value_);
            }
            if (arguments0Value instanceof TruffleString) {
                TruffleString arguments0Value_ = (TruffleString)arguments0Value;
                this.state_0_ = state_0 |= 2;
                return this.printSourceAttribution(arguments0Value_);
            }
            this.state_0_ = state_0 |= 4;
            return this.illegalArgument(arguments0Value);
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
            s[0] = "printSourceAttribution";
            s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[1] = s;
            s = new Object[3];
            s[0] = "printSourceAttribution";
            s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[2] = s;
            s = new Object[3];
            s[0] = "illegalArgument";
            s[1] = (state_0 & 4) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[3] = s;
            return Introspection.Provider.create(data);
        }

        private static boolean fallbackGuard_(int state_0, Object arguments0Value) {
            if ((state_0 & 1) == 0 && arguments0Value instanceof JSFunctionObject) {
                return false;
            }
            return (state_0 & 2) != 0 || !(arguments0Value instanceof TruffleString);
        }

        public static DebugBuiltins.DebugPrintSourceAttribution create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new DebugPrintSourceAttributionNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=DebugBuiltins.DebugToJavaStringNode.class)
    public static final class DebugToJavaStringNodeGen
    extends DebugBuiltins.DebugToJavaStringNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;

        private DebugToJavaStringNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            return DebugBuiltins.DebugToJavaStringNode.toJavaString(arguments0Value_);
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
            s[0] = "toJavaString";
            s[1] = (byte)1;
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static DebugBuiltins.DebugToJavaStringNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new DebugToJavaStringNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=DebugBuiltins.DebugPrintObjectNode.class)
    public static final class DebugPrintObjectNodeGen
    extends DebugBuiltins.DebugPrintObjectNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @CompilerDirectives.CompilationFinal
        private int state_0_;

        private DebugPrintObjectNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            if (state_0 != 0 && arguments0Value_ instanceof JSDynamicObject) {
                JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
                return this.printObject(arguments0Value__, arguments1Value_);
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
            if (arguments0Value instanceof JSDynamicObject) {
                JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
                this.state_0_ = state_0 |= 1;
                return this.printObject(arguments0Value_, arguments1Value);
            }
            throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_, this.arguments1_}, arguments0Value, arguments1Value);
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
            s[0] = "printObject";
            s[1] = state_0 != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static DebugBuiltins.DebugPrintObjectNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new DebugPrintObjectNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=DebugBuiltins.DebugDumpFunctionTreeNode.class)
    public static final class DebugDumpFunctionTreeNodeGen
    extends DebugBuiltins.DebugDumpFunctionTreeNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;

        private DebugDumpFunctionTreeNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            return this.dumpFunctionTree(arguments0Value_);
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
            s[0] = "dumpFunctionTree";
            s[1] = (byte)1;
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static DebugBuiltins.DebugDumpFunctionTreeNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new DebugDumpFunctionTreeNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=DebugBuiltins.DebugDumpCountersNode.class)
    public static final class DebugDumpCountersNodeGen
    extends DebugBuiltins.DebugDumpCountersNode
    implements Introspection.Provider {
        private DebugDumpCountersNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            super(context, builtin);
        }

        @Override
        public JavaScriptNode[] getArguments() {
            return new JavaScriptNode[0];
        }

        @Override
        public Object execute(VirtualFrame frameValue) {
            return DebugBuiltins.DebugDumpCountersNode.dumpCounters();
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
            s[0] = "dumpCounters";
            s[1] = (byte)1;
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static DebugBuiltins.DebugDumpCountersNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new DebugDumpCountersNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=DebugBuiltins.DebugShapeNode.class)
    public static final class DebugShapeNodeGen
    extends DebugBuiltins.DebugShapeNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;

        private DebugShapeNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            return DebugBuiltins.DebugShapeNode.shape(arguments0Value_);
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
            s[0] = "shape";
            s[1] = (byte)1;
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static DebugBuiltins.DebugShapeNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new DebugShapeNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=DebugBuiltins.DebugClassNameNode.class)
    public static final class DebugClassNameNodeGen
    extends DebugBuiltins.DebugClassNameNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;

        private DebugClassNameNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            return DebugBuiltins.DebugClassNameNode.clazz(arguments0Value_);
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
            s[0] = "clazz";
            s[1] = (byte)1;
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static DebugBuiltins.DebugClassNameNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new DebugClassNameNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=DebugBuiltins.DebugClassNode.class)
    public static final class DebugClassNodeGen
    extends DebugBuiltins.DebugClassNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;

        private DebugClassNodeGen(JSContext context, JSBuiltin builtin, boolean getName, JavaScriptNode[] arguments) {
            super(context, builtin, getName);
            this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
        }

        @Override
        public JavaScriptNode[] getArguments() {
            return new JavaScriptNode[]{this.arguments0_};
        }

        @Override
        public Object execute(VirtualFrame frameValue) {
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            return this.clazz(arguments0Value_);
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
            s[0] = "clazz";
            s[1] = (byte)1;
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static DebugBuiltins.DebugClassNode create(JSContext context, JSBuiltin builtin, boolean getName, JavaScriptNode[] arguments) {
            return new DebugClassNodeGen(context, builtin, getName, arguments);
        }
    }

    @GeneratedBy(value=DebugBuiltins.DebugContinueInInterpreter.class)
    public static final class DebugContinueInInterpreterNodeGen
    extends DebugBuiltins.DebugContinueInInterpreter
    implements Introspection.Provider {
        private DebugContinueInInterpreterNodeGen(JSContext context, JSBuiltin builtin, boolean invalidate, JavaScriptNode[] arguments) {
            super(context, builtin, invalidate);
        }

        @Override
        public JavaScriptNode[] getArguments() {
            return new JavaScriptNode[0];
        }

        @Override
        public Object execute(VirtualFrame frameValue) {
            return this.continueInInterpreter();
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
            s[0] = "continueInInterpreter";
            s[1] = (byte)1;
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static DebugBuiltins.DebugContinueInInterpreter create(JSContext context, JSBuiltin builtin, boolean invalidate, JavaScriptNode[] arguments) {
            return new DebugContinueInInterpreterNodeGen(context, builtin, invalidate, arguments);
        }
    }
}

