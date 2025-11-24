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
import com.oracle.truffle.api.utilities.AssumedValue;
import com.oracle.truffle.js.builtins.ConstructorBuiltins;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.JSTypesGen;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.ScriptNode;
import com.oracle.truffle.js.nodes.access.GetMethodNode;
import com.oracle.truffle.js.nodes.access.IsJSObjectNode;
import com.oracle.truffle.js.nodes.access.IsObjectNode;
import com.oracle.truffle.js.nodes.access.IsRegExpNode;
import com.oracle.truffle.js.nodes.access.IterableToListNode;
import com.oracle.truffle.js.nodes.access.PropertyGetNode;
import com.oracle.truffle.js.nodes.access.ReadElementNode;
import com.oracle.truffle.js.nodes.array.ArrayCreateNode;
import com.oracle.truffle.js.nodes.cast.JSNumberToBigIntNode;
import com.oracle.truffle.js.nodes.cast.JSNumericToNumberNode;
import com.oracle.truffle.js.nodes.cast.JSToBigIntNode;
import com.oracle.truffle.js.nodes.cast.JSToBooleanNode;
import com.oracle.truffle.js.nodes.cast.JSToIndexNode;
import com.oracle.truffle.js.nodes.cast.JSToIntegerThrowOnInfinityNode;
import com.oracle.truffle.js.nodes.cast.JSToIntegerWithoutRoundingNode;
import com.oracle.truffle.js.nodes.cast.JSToNumericNode;
import com.oracle.truffle.js.nodes.cast.JSToObjectNode;
import com.oracle.truffle.js.nodes.cast.JSToStringNode;
import com.oracle.truffle.js.nodes.cast.ToArrayLengthNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.nodes.function.JSFunctionCallNode;
import com.oracle.truffle.js.nodes.temporal.ToTemporalCalendarWithISODefaultNode;
import com.oracle.truffle.js.nodes.temporal.ToTemporalTimeZoneNode;
import com.oracle.truffle.js.nodes.unary.IsCallableNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.Symbol;
import com.oracle.truffle.js.runtime.builtins.JSAbstractBuffer;
import com.oracle.truffle.js.runtime.builtins.JSArrayBuffer;
import com.oracle.truffle.js.runtime.builtins.wasm.JSWebAssemblyModuleObject;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.util.LRUCache;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=ConstructorBuiltins.class)
public final class ConstructorBuiltinsFactory {
    private static final LibraryFactory<InteropLibrary> INTEROP_LIBRARY_ = LibraryFactory.resolve(InteropLibrary.class);

    @GeneratedBy(value=ConstructorBuiltins.ConstructWebAssemblyGlobalNode.class)
    public static final class ConstructWebAssemblyGlobalNodeGen
    extends ConstructorBuiltins.ConstructWebAssemblyGlobalNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @Node.Child
        private JavaScriptNode arguments2_;
        @CompilerDirectives.CompilationFinal
        private int state_0_;

        private ConstructWebAssemblyGlobalNodeGen(JSContext context, JSBuiltin builtin, boolean newTargetCase, JavaScriptNode[] arguments) {
            super(context, builtin, newTargetCase);
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
            if (state_0 != 0 && arguments0Value_ instanceof JSDynamicObject) {
                JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
                return this.constructGlobal(arguments0Value__, arguments1Value_, arguments2Value_);
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
            if (arguments0Value instanceof JSDynamicObject) {
                JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
                this.state_0_ = state_0 |= 1;
                return this.constructGlobal(arguments0Value_, arguments1Value, arguments2Value);
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
            s[0] = "constructGlobal";
            s[1] = state_0 != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static ConstructorBuiltins.ConstructWebAssemblyGlobalNode create(JSContext context, JSBuiltin builtin, boolean newTargetCase, JavaScriptNode[] arguments) {
            return new ConstructWebAssemblyGlobalNodeGen(context, builtin, newTargetCase, arguments);
        }
    }

    @GeneratedBy(value=ConstructorBuiltins.ConstructWebAssemblyTableNode.class)
    public static final class ConstructWebAssemblyTableNodeGen
    extends ConstructorBuiltins.ConstructWebAssemblyTableNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private TruffleString.EqualNode stringEqualsNode_;

        private ConstructWebAssemblyTableNodeGen(JSContext context, JSBuiltin builtin, boolean newTargetCase, JavaScriptNode[] arguments) {
            super(context, builtin, newTargetCase);
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
                return this.constructTable(arguments0Value__, arguments1Value_, this.stringEqualsNode_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.execute(frameValue);
        }

        private JSDynamicObject executeAndSpecialize(Object arguments0Value, Object arguments1Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                if (arguments0Value instanceof JSDynamicObject) {
                    JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
                    this.stringEqualsNode_ = super.insert(TruffleString.EqualNode.create());
                    this.state_0_ = state_0 |= 1;
                    lock.unlock();
                    hasLock = false;
                    JSDynamicObject jSDynamicObject = this.constructTable(arguments0Value_, arguments1Value, this.stringEqualsNode_);
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
            s[0] = "constructTable";
            if (state_0 != 0) {
                s[1] = (byte)1;
                ArrayList<List<TruffleString.EqualNode>> cached = new ArrayList<List<TruffleString.EqualNode>>();
                cached.add(Arrays.asList(this.stringEqualsNode_));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static ConstructorBuiltins.ConstructWebAssemblyTableNode create(JSContext context, JSBuiltin builtin, boolean newTargetCase, JavaScriptNode[] arguments) {
            return new ConstructWebAssemblyTableNodeGen(context, builtin, newTargetCase, arguments);
        }
    }

    @GeneratedBy(value=ConstructorBuiltins.ConstructWebAssemblyMemoryNode.class)
    public static final class ConstructWebAssemblyMemoryNodeGen
    extends ConstructorBuiltins.ConstructWebAssemblyMemoryNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @CompilerDirectives.CompilationFinal
        private int state_0_;

        private ConstructWebAssemblyMemoryNodeGen(JSContext context, JSBuiltin builtin, boolean newTargetCase, JavaScriptNode[] arguments) {
            super(context, builtin, newTargetCase);
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
                return this.constructMemory(arguments0Value__, arguments1Value_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.execute(frameValue);
        }

        private JSDynamicObject executeAndSpecialize(Object arguments0Value, Object arguments1Value) {
            int state_0 = this.state_0_;
            if (arguments0Value instanceof JSDynamicObject) {
                JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
                this.state_0_ = state_0 |= 1;
                return this.constructMemory(arguments0Value_, arguments1Value);
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
            s[0] = "constructMemory";
            s[1] = state_0 != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static ConstructorBuiltins.ConstructWebAssemblyMemoryNode create(JSContext context, JSBuiltin builtin, boolean newTargetCase, JavaScriptNode[] arguments) {
            return new ConstructWebAssemblyMemoryNodeGen(context, builtin, newTargetCase, arguments);
        }
    }

    @GeneratedBy(value=ConstructorBuiltins.ConstructWebAssemblyInstanceNode.class)
    public static final class ConstructWebAssemblyInstanceNodeGen
    extends ConstructorBuiltins.ConstructWebAssemblyInstanceNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @Node.Child
        private JavaScriptNode arguments2_;
        @CompilerDirectives.CompilationFinal
        private int state_0_;

        private ConstructWebAssemblyInstanceNodeGen(JSContext context, JSBuiltin builtin, boolean newTargetCase, JavaScriptNode[] arguments) {
            super(context, builtin, newTargetCase);
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
            if (state_0 != 0 && arguments0Value_ instanceof JSDynamicObject) {
                JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
                if ((state_0 & 1) != 0 && arguments1Value_ instanceof JSWebAssemblyModuleObject) {
                    JSWebAssemblyModuleObject arguments1Value__ = (JSWebAssemblyModuleObject)arguments1Value_;
                    return this.constructInstanceFromModule(arguments0Value__, arguments1Value__, arguments2Value_);
                }
                if ((state_0 & 2) != 0 && !JSGuards.isJSWebAssemblyModule(arguments1Value_)) {
                    return this.constructInstanceFromOther(arguments0Value__, arguments1Value_, arguments2Value_);
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
            if (arguments0Value instanceof JSDynamicObject) {
                JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
                if (arguments1Value instanceof JSWebAssemblyModuleObject) {
                    JSWebAssemblyModuleObject arguments1Value_ = (JSWebAssemblyModuleObject)arguments1Value;
                    this.state_0_ = state_0 |= 1;
                    return this.constructInstanceFromModule(arguments0Value_, arguments1Value_, arguments2Value);
                }
                if (!JSGuards.isJSWebAssemblyModule(arguments1Value)) {
                    this.state_0_ = state_0 |= 2;
                    return this.constructInstanceFromOther(arguments0Value_, arguments1Value, arguments2Value);
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
            Object[] data = new Object[3];
            data[0] = 0;
            int state_0 = this.state_0_;
            Object[] s = new Object[3];
            s[0] = "constructInstanceFromModule";
            s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[1] = s;
            s = new Object[3];
            s[0] = "constructInstanceFromOther";
            s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[2] = s;
            return Introspection.Provider.create(data);
        }

        public static ConstructorBuiltins.ConstructWebAssemblyInstanceNode create(JSContext context, JSBuiltin builtin, boolean newTargetCase, JavaScriptNode[] arguments) {
            return new ConstructWebAssemblyInstanceNodeGen(context, builtin, newTargetCase, arguments);
        }
    }

    @GeneratedBy(value=ConstructorBuiltins.ConstructWebAssemblyModuleNode.class)
    public static final class ConstructWebAssemblyModuleNodeGen
    extends ConstructorBuiltins.ConstructWebAssemblyModuleNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @CompilerDirectives.CompilationFinal
        private int state_0_;

        private ConstructWebAssemblyModuleNodeGen(JSContext context, JSBuiltin builtin, boolean newTargetCase, JavaScriptNode[] arguments) {
            super(context, builtin, newTargetCase);
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
                return this.constructModule(arguments0Value__, arguments1Value_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.execute(frameValue);
        }

        private JSDynamicObject executeAndSpecialize(Object arguments0Value, Object arguments1Value) {
            int state_0 = this.state_0_;
            if (arguments0Value instanceof JSDynamicObject) {
                JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
                this.state_0_ = state_0 |= 1;
                return this.constructModule(arguments0Value_, arguments1Value);
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
            s[0] = "constructModule";
            s[1] = state_0 != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static ConstructorBuiltins.ConstructWebAssemblyModuleNode create(JSContext context, JSBuiltin builtin, boolean newTargetCase, JavaScriptNode[] arguments) {
            return new ConstructWebAssemblyModuleNodeGen(context, builtin, newTargetCase, arguments);
        }
    }

    @GeneratedBy(value=ConstructorBuiltins.PromiseConstructorNode.class)
    public static final class PromiseConstructorNodeGen
    extends ConstructorBuiltins.PromiseConstructorNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @CompilerDirectives.CompilationFinal
        private int state_0_;

        private PromiseConstructorNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
                if ((state_0 & 1) != 0 && this.isCallable.executeBoolean(arguments1Value_)) {
                    return this.construct(arguments0Value__, arguments1Value_);
                }
                if ((state_0 & 2) != 0 && !this.isCallable.executeBoolean(arguments1Value_)) {
                    return this.notCallable(arguments0Value__, arguments1Value_);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.execute(frameValue);
        }

        private JSDynamicObject executeAndSpecialize(Object arguments0Value, Object arguments1Value) {
            int state_0 = this.state_0_;
            if (arguments0Value instanceof JSDynamicObject) {
                JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
                if (this.isCallable.executeBoolean(arguments1Value)) {
                    this.state_0_ = state_0 |= 1;
                    return this.construct(arguments0Value_, arguments1Value);
                }
                if (!this.isCallable.executeBoolean(arguments1Value)) {
                    this.state_0_ = state_0 |= 2;
                    return this.notCallable(arguments0Value_, arguments1Value);
                }
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
            Object[] data = new Object[3];
            data[0] = 0;
            int state_0 = this.state_0_;
            Object[] s = new Object[3];
            s[0] = "construct";
            s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[1] = s;
            s = new Object[3];
            s[0] = "notCallable";
            s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[2] = s;
            return Introspection.Provider.create(data);
        }

        public static ConstructorBuiltins.PromiseConstructorNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new PromiseConstructorNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=ConstructorBuiltins.ConstructSymbolNode.class)
    public static final class ConstructSymbolNodeGen
    extends ConstructorBuiltins.ConstructSymbolNode
    implements Introspection.Provider {
        private ConstructSymbolNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            super(context, builtin);
        }

        @Override
        public JavaScriptNode[] getArguments() {
            return new JavaScriptNode[0];
        }

        @Override
        public Object execute(VirtualFrame frameValue) {
            return ConstructorBuiltins.ConstructSymbolNode.construct();
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
            s[0] = "construct";
            s[1] = (byte)1;
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static ConstructorBuiltins.ConstructSymbolNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new ConstructSymbolNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=ConstructorBuiltins.CallSymbolNode.class)
    public static final class CallSymbolNodeGen
    extends ConstructorBuiltins.CallSymbolNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private JSToStringNode callSymbolGeneric_toStringNode_;

        private CallSymbolNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            if ((state_0 & 1) != 0 && arguments0Value_ instanceof TruffleString) {
                TruffleString arguments0Value__ = (TruffleString)arguments0Value_;
                return this.callSymbolString(arguments0Value__);
            }
            if ((state_0 & 2) != 0 && !JSGuards.isString(arguments0Value_)) {
                return this.callSymbolGeneric(arguments0Value_, this.callSymbolGeneric_toStringNode_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.execute(frameValue);
        }

        private Symbol executeAndSpecialize(Object arguments0Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                if (arguments0Value instanceof TruffleString) {
                    TruffleString arguments0Value_ = (TruffleString)arguments0Value;
                    this.state_0_ = state_0 |= 1;
                    lock.unlock();
                    hasLock = false;
                    Symbol symbol = this.callSymbolString(arguments0Value_);
                    return symbol;
                }
                if (!JSGuards.isString(arguments0Value)) {
                    this.callSymbolGeneric_toStringNode_ = super.insert(JSToStringNode.create());
                    this.state_0_ = state_0 |= 2;
                    lock.unlock();
                    hasLock = false;
                    Symbol symbol = this.callSymbolGeneric(arguments0Value, this.callSymbolGeneric_toStringNode_);
                    return symbol;
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
            s[0] = "callSymbolString";
            s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[1] = s;
            s = new Object[3];
            s[0] = "callSymbolGeneric";
            if ((state_0 & 2) != 0) {
                s[1] = (byte)1;
                ArrayList<List<JSToStringNode>> cached = new ArrayList<List<JSToStringNode>>();
                cached.add(Arrays.asList(this.callSymbolGeneric_toStringNode_));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[2] = s;
            return Introspection.Provider.create(data);
        }

        public static ConstructorBuiltins.CallSymbolNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new CallSymbolNodeGen(context, builtin, arguments);
        }

        @GeneratedBy(value=ConstructorBuiltins.CallSymbolNode.Inlined.class)
        public static final class InlinedNodeGen
        extends ConstructorBuiltins.CallSymbolNode.Inlined
        implements Introspection.Provider {
            @Node.Child
            private JavaScriptNode arguments0_;
            @CompilerDirectives.CompilationFinal
            private volatile int state_0_;
            @Node.Child
            private JSToStringNode callSymbolGeneric_toStringNode_;
            @Node.Child
            private CallSymbolSingletonData callSymbolSingleton_cache;

            private InlinedNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
                super(context, builtin);
                this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
            }

            @Override
            public JavaScriptNode[] getArguments() {
                return new JavaScriptNode[]{this.arguments0_};
            }

            @Override
            @ExplodeLoop
            protected Object executeWithArguments(Object arguments0Value) {
                int state_0 = this.state_0_;
                if ((state_0 & 1) != 0 && !JSGuards.isString(arguments0Value)) {
                    return this.callSymbolGeneric(arguments0Value, this.callSymbolGeneric_toStringNode_);
                }
                if ((state_0 & 6) != 0 && arguments0Value instanceof TruffleString) {
                    TruffleString arguments0Value_ = (TruffleString)arguments0Value;
                    if ((state_0 & 2) != 0) {
                        CallSymbolSingletonData s1_ = this.callSymbolSingleton_cache;
                        while (s1_ != null) {
                            if (this.acceptCache(s1_.equalNode_, arguments0Value_, s1_.cachedValue_, s1_.symbolUsageMarker_)) {
                                return this.callSymbolSingleton(arguments0Value_, s1_.cachedValue_, s1_.equalNode_, s1_.symbolUsageMarker_, s1_.cachedSymbol_);
                            }
                            s1_ = s1_.next_;
                        }
                    }
                    if ((state_0 & 4) != 0) {
                        return this.callSymbolString(arguments0Value_);
                    }
                }
                if ((state_0 & 8) != 0) {
                    return this.callInlinedSymbolGeneric(arguments0Value);
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.executeAndSpecialize(arguments0Value);
            }

            @Override
            @ExplodeLoop
            public Object execute(VirtualFrame frameValue) {
                int state_0 = this.state_0_;
                Object arguments0Value_ = this.arguments0_.execute(frameValue);
                if ((state_0 & 1) != 0 && !JSGuards.isString(arguments0Value_)) {
                    return this.callSymbolGeneric(arguments0Value_, this.callSymbolGeneric_toStringNode_);
                }
                if ((state_0 & 6) != 0 && arguments0Value_ instanceof TruffleString) {
                    TruffleString arguments0Value__ = (TruffleString)arguments0Value_;
                    if ((state_0 & 2) != 0) {
                        CallSymbolSingletonData s1_ = this.callSymbolSingleton_cache;
                        while (s1_ != null) {
                            if (this.acceptCache(s1_.equalNode_, arguments0Value__, s1_.cachedValue_, s1_.symbolUsageMarker_)) {
                                return this.callSymbolSingleton(arguments0Value__, s1_.cachedValue_, s1_.equalNode_, s1_.symbolUsageMarker_, s1_.cachedSymbol_);
                            }
                            s1_ = s1_.next_;
                        }
                    }
                    if ((state_0 & 4) != 0) {
                        return this.callSymbolString(arguments0Value__);
                    }
                }
                if ((state_0 & 8) != 0) {
                    return this.callInlinedSymbolGeneric(arguments0Value_);
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.executeAndSpecialize(arguments0Value_);
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
                    int state_0 = this.state_0_;
                    if (!JSGuards.isString(arguments0Value)) {
                        this.callSymbolGeneric_toStringNode_ = super.insert(JSToStringNode.create());
                        this.state_0_ = state_0 |= 1;
                        lock.unlock();
                        hasLock = false;
                        Symbol symbol = this.callSymbolGeneric(arguments0Value, this.callSymbolGeneric_toStringNode_);
                        return symbol;
                    }
                    if (arguments0Value instanceof TruffleString) {
                        Symbol symbol;
                        TruffleString arguments0Value_ = (TruffleString)arguments0Value;
                        int count1_ = 0;
                        CallSymbolSingletonData s1_ = this.callSymbolSingleton_cache;
                        if ((state_0 & 2) != 0) {
                            while (s1_ != null && !this.acceptCache(s1_.equalNode_, arguments0Value_, s1_.cachedValue_, s1_.symbolUsageMarker_)) {
                                s1_ = s1_.next_;
                                ++count1_;
                            }
                        }
                        if (s1_ == null) {
                            AtomicReference<Object> symbolUsageMarker__;
                            TruffleString cachedValue__ = arguments0Value_;
                            TruffleString.EqualNode equalNode__ = super.insert(TruffleString.EqualNode.create());
                            if (this.acceptCache(equalNode__, arguments0Value_, cachedValue__, symbolUsageMarker__ = this.createSymbolUsageMarker()) && count1_ < 3) {
                                s1_ = super.insert(new CallSymbolSingletonData(this.callSymbolSingleton_cache));
                                s1_.cachedValue_ = cachedValue__;
                                s1_.equalNode_ = s1_.insertAccessor(equalNode__);
                                s1_.symbolUsageMarker_ = symbolUsageMarker__;
                                s1_.cachedSymbol_ = this.createCachedSingletonSymbol(arguments0Value_);
                                VarHandle.storeStoreFence();
                                this.callSymbolSingleton_cache = s1_;
                                this.state_0_ = state_0 |= 2;
                            }
                        }
                        if (s1_ != null) {
                            lock.unlock();
                            hasLock = false;
                            symbol = this.callSymbolSingleton(arguments0Value_, s1_.cachedValue_, s1_.equalNode_, s1_.symbolUsageMarker_, s1_.cachedSymbol_);
                            return symbol;
                        }
                        this.state_0_ = state_0 |= 4;
                        lock.unlock();
                        hasLock = false;
                        symbol = this.callSymbolString(arguments0Value_);
                        return symbol;
                    }
                    this.state_0_ = state_0 |= 8;
                    lock.unlock();
                    hasLock = false;
                    TruffleString truffleString = this.callInlinedSymbolGeneric(arguments0Value);
                    return truffleString;
                }
                finally {
                    if (hasLock) {
                        lock.unlock();
                    }
                }
            }

            @Override
            public NodeCost getCost() {
                CallSymbolSingletonData s1_;
                int state_0 = this.state_0_;
                if (state_0 == 0) {
                    return NodeCost.UNINITIALIZED;
                }
                if ((state_0 & state_0 - 1) == 0 && ((s1_ = this.callSymbolSingleton_cache) == null || s1_.next_ == null)) {
                    return NodeCost.MONOMORPHIC;
                }
                return NodeCost.POLYMORPHIC;
            }

            @Override
            public Introspection getIntrospectionData() {
                ArrayList<List<Object>> cached;
                Object[] data = new Object[5];
                data[0] = 0;
                int state_0 = this.state_0_;
                Object[] s = new Object[3];
                s[0] = "callSymbolGeneric";
                if ((state_0 & 1) != 0) {
                    s[1] = (byte)1;
                    cached = new ArrayList<List<Object>>();
                    cached.add(Arrays.asList(this.callSymbolGeneric_toStringNode_));
                    s[2] = cached;
                } else {
                    s[1] = (byte)0;
                }
                data[1] = s;
                s = new Object[3];
                s[0] = "callSymbolSingleton";
                if ((state_0 & 2) != 0) {
                    s[1] = (byte)1;
                    cached = new ArrayList();
                    CallSymbolSingletonData s1_ = this.callSymbolSingleton_cache;
                    while (s1_ != null) {
                        cached.add(Arrays.asList(s1_.cachedValue_, s1_.equalNode_, s1_.symbolUsageMarker_, s1_.cachedSymbol_));
                        s1_ = s1_.next_;
                    }
                    s[2] = cached;
                } else {
                    s[1] = (byte)0;
                }
                data[2] = s;
                s = new Object[3];
                s[0] = "callSymbolString";
                s[1] = (state_0 & 4) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
                data[3] = s;
                s = new Object[3];
                s[0] = "callInlinedSymbolGeneric";
                s[1] = (state_0 & 8) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
                data[4] = s;
                return Introspection.Provider.create(data);
            }

            public static ConstructorBuiltins.CallSymbolNode.Inlined create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
                return new InlinedNodeGen(context, builtin, arguments);
            }

            @GeneratedBy(value=ConstructorBuiltins.CallSymbolNode.Inlined.class)
            private static final class CallSymbolSingletonData
            extends Node {
                @Node.Child
                CallSymbolSingletonData next_;
                @CompilerDirectives.CompilationFinal
                TruffleString cachedValue_;
                @Node.Child
                TruffleString.EqualNode equalNode_;
                @CompilerDirectives.CompilationFinal
                AtomicReference<Object> symbolUsageMarker_;
                @CompilerDirectives.CompilationFinal
                Symbol cachedSymbol_;

                CallSymbolSingletonData(CallSymbolSingletonData next_) {
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

    @GeneratedBy(value=ConstructorBuiltins.ConstructWeakMapNode.class)
    public static final class ConstructWeakMapNodeGen
    extends ConstructorBuiltins.ConstructWeakMapNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private ConstructMapFromIterableData constructMapFromIterable_cache;

        private ConstructWeakMapNodeGen(JSContext context, JSBuiltin builtin, boolean isNewTargetCase, JavaScriptNode[] arguments) {
            super(context, builtin, isNewTargetCase);
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
                ConstructMapFromIterableData s1_;
                JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
                if ((state_0 & 1) != 0 && JSGuards.isNullOrUndefined(arguments1Value_)) {
                    return this.constructEmptyMap(arguments0Value__, arguments1Value_);
                }
                if ((state_0 & 2) != 0 && (s1_ = this.constructMapFromIterable_cache) != null && !JSGuards.isNullOrUndefined(arguments1Value_)) {
                    return this.constructMapFromIterable(arguments0Value__, arguments1Value_, s1_.readElementNode_, s1_.isObjectNode_, s1_.isCallableNode_);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.execute(frameValue);
        }

        private JSDynamicObject executeAndSpecialize(Object arguments0Value, Object arguments1Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                if (arguments0Value instanceof JSDynamicObject) {
                    JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
                    if (JSGuards.isNullOrUndefined(arguments1Value)) {
                        this.state_0_ = state_0 |= 1;
                        lock.unlock();
                        hasLock = false;
                        JSDynamicObject jSDynamicObject = this.constructEmptyMap(arguments0Value_, arguments1Value);
                        return jSDynamicObject;
                    }
                    if (!JSGuards.isNullOrUndefined(arguments1Value)) {
                        ConstructMapFromIterableData s1_ = super.insert(new ConstructMapFromIterableData());
                        s1_.readElementNode_ = s1_.insertAccessor(ReadElementNode.create(this.getContext()));
                        s1_.isObjectNode_ = s1_.insertAccessor(IsObjectNode.create());
                        s1_.isCallableNode_ = s1_.insertAccessor(IsCallableNode.create());
                        VarHandle.storeStoreFence();
                        this.constructMapFromIterable_cache = s1_;
                        this.state_0_ = state_0 |= 2;
                        lock.unlock();
                        hasLock = false;
                        JSDynamicObject jSDynamicObject = this.constructMapFromIterable(arguments0Value_, arguments1Value, s1_.readElementNode_, s1_.isObjectNode_, s1_.isCallableNode_);
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
            s[0] = "constructEmptyMap";
            s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[1] = s;
            s = new Object[3];
            s[0] = "constructMapFromIterable";
            if ((state_0 & 2) != 0) {
                s[1] = (byte)1;
                ArrayList<List<JavaScriptBaseNode>> cached = new ArrayList<List<JavaScriptBaseNode>>();
                ConstructMapFromIterableData s1_ = this.constructMapFromIterable_cache;
                if (s1_ != null) {
                    cached.add(Arrays.asList(s1_.readElementNode_, s1_.isObjectNode_, s1_.isCallableNode_));
                }
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[2] = s;
            return Introspection.Provider.create(data);
        }

        public static ConstructorBuiltins.ConstructWeakMapNode create(JSContext context, JSBuiltin builtin, boolean isNewTargetCase, JavaScriptNode[] arguments) {
            return new ConstructWeakMapNodeGen(context, builtin, isNewTargetCase, arguments);
        }

        @GeneratedBy(value=ConstructorBuiltins.ConstructWeakMapNode.class)
        private static final class ConstructMapFromIterableData
        extends Node {
            @Node.Child
            ReadElementNode readElementNode_;
            @Node.Child
            IsObjectNode isObjectNode_;
            @Node.Child
            IsCallableNode isCallableNode_;

            ConstructMapFromIterableData() {
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

    @GeneratedBy(value=ConstructorBuiltins.ConstructWeakSetNode.class)
    public static final class ConstructWeakSetNodeGen
    extends ConstructorBuiltins.ConstructWeakSetNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private IsCallableNode constructSetFromIterable_isCallableNode_;

        private ConstructWeakSetNodeGen(JSContext context, JSBuiltin builtin, boolean isNewTargetCase, JavaScriptNode[] arguments) {
            super(context, builtin, isNewTargetCase);
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
                if ((state_0 & 1) != 0 && JSGuards.isNullOrUndefined(arguments1Value_)) {
                    return this.constructEmptySet(arguments0Value__, arguments1Value_);
                }
                if ((state_0 & 2) != 0 && !JSGuards.isNullOrUndefined(arguments1Value_)) {
                    return this.constructSetFromIterable(arguments0Value__, arguments1Value_, this.constructSetFromIterable_isCallableNode_);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.execute(frameValue);
        }

        private JSDynamicObject executeAndSpecialize(Object arguments0Value, Object arguments1Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                if (arguments0Value instanceof JSDynamicObject) {
                    JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
                    if (JSGuards.isNullOrUndefined(arguments1Value)) {
                        this.state_0_ = state_0 |= 1;
                        lock.unlock();
                        hasLock = false;
                        JSDynamicObject jSDynamicObject = this.constructEmptySet(arguments0Value_, arguments1Value);
                        return jSDynamicObject;
                    }
                    if (!JSGuards.isNullOrUndefined(arguments1Value)) {
                        this.constructSetFromIterable_isCallableNode_ = super.insert(IsCallableNode.create());
                        this.state_0_ = state_0 |= 2;
                        lock.unlock();
                        hasLock = false;
                        JSDynamicObject jSDynamicObject = this.constructSetFromIterable(arguments0Value_, arguments1Value, this.constructSetFromIterable_isCallableNode_);
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
            s[0] = "constructEmptySet";
            s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[1] = s;
            s = new Object[3];
            s[0] = "constructSetFromIterable";
            if ((state_0 & 2) != 0) {
                s[1] = (byte)1;
                ArrayList<List<IsCallableNode>> cached = new ArrayList<List<IsCallableNode>>();
                cached.add(Arrays.asList(this.constructSetFromIterable_isCallableNode_));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[2] = s;
            return Introspection.Provider.create(data);
        }

        public static ConstructorBuiltins.ConstructWeakSetNode create(JSContext context, JSBuiltin builtin, boolean isNewTargetCase, JavaScriptNode[] arguments) {
            return new ConstructWeakSetNodeGen(context, builtin, isNewTargetCase, arguments);
        }
    }

    @GeneratedBy(value=ConstructorBuiltins.ConstructSetNode.class)
    public static final class ConstructSetNodeGen
    extends ConstructorBuiltins.ConstructSetNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private IsCallableNode constructSetFromIterable_isCallableNode_;

        private ConstructSetNodeGen(JSContext context, JSBuiltin builtin, boolean isNewTargetCase, JavaScriptNode[] arguments) {
            super(context, builtin, isNewTargetCase);
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
                if ((state_0 & 1) != 0 && JSGuards.isNullOrUndefined(arguments1Value_)) {
                    return this.constructEmptySet(arguments0Value__, arguments1Value_);
                }
                if ((state_0 & 2) != 0 && !JSGuards.isNullOrUndefined(arguments1Value_)) {
                    return this.constructSetFromIterable(arguments0Value__, arguments1Value_, this.constructSetFromIterable_isCallableNode_);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.execute(frameValue);
        }

        private JSDynamicObject executeAndSpecialize(Object arguments0Value, Object arguments1Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                if (arguments0Value instanceof JSDynamicObject) {
                    JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
                    if (JSGuards.isNullOrUndefined(arguments1Value)) {
                        this.state_0_ = state_0 |= 1;
                        lock.unlock();
                        hasLock = false;
                        JSDynamicObject jSDynamicObject = this.constructEmptySet(arguments0Value_, arguments1Value);
                        return jSDynamicObject;
                    }
                    if (!JSGuards.isNullOrUndefined(arguments1Value)) {
                        this.constructSetFromIterable_isCallableNode_ = super.insert(IsCallableNode.create());
                        this.state_0_ = state_0 |= 2;
                        lock.unlock();
                        hasLock = false;
                        JSDynamicObject jSDynamicObject = this.constructSetFromIterable(arguments0Value_, arguments1Value, this.constructSetFromIterable_isCallableNode_);
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
            s[0] = "constructEmptySet";
            s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[1] = s;
            s = new Object[3];
            s[0] = "constructSetFromIterable";
            if ((state_0 & 2) != 0) {
                s[1] = (byte)1;
                ArrayList<List<IsCallableNode>> cached = new ArrayList<List<IsCallableNode>>();
                cached.add(Arrays.asList(this.constructSetFromIterable_isCallableNode_));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[2] = s;
            return Introspection.Provider.create(data);
        }

        public static ConstructorBuiltins.ConstructSetNode create(JSContext context, JSBuiltin builtin, boolean isNewTargetCase, JavaScriptNode[] arguments) {
            return new ConstructSetNodeGen(context, builtin, isNewTargetCase, arguments);
        }
    }

    @GeneratedBy(value=ConstructorBuiltins.ConstructMapNode.class)
    public static final class ConstructMapNodeGen
    extends ConstructorBuiltins.ConstructMapNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private ConstructMapFromIterableData constructMapFromIterable_cache;

        private ConstructMapNodeGen(JSContext context, JSBuiltin builtin, boolean isNewTargetCase, JavaScriptNode[] arguments) {
            super(context, builtin, isNewTargetCase);
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
                ConstructMapFromIterableData s1_;
                JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
                if ((state_0 & 1) != 0 && JSGuards.isNullOrUndefined(arguments1Value_)) {
                    return this.constructEmptyMap(arguments0Value__, arguments1Value_);
                }
                if ((state_0 & 2) != 0 && (s1_ = this.constructMapFromIterable_cache) != null && !JSGuards.isNullOrUndefined(arguments1Value_)) {
                    return this.constructMapFromIterable(arguments0Value__, arguments1Value_, s1_.readElementNode_, s1_.isObjectNode_, s1_.isCallableNode_);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.execute(frameValue);
        }

        private JSDynamicObject executeAndSpecialize(Object arguments0Value, Object arguments1Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                if (arguments0Value instanceof JSDynamicObject) {
                    JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
                    if (JSGuards.isNullOrUndefined(arguments1Value)) {
                        this.state_0_ = state_0 |= 1;
                        lock.unlock();
                        hasLock = false;
                        JSDynamicObject jSDynamicObject = this.constructEmptyMap(arguments0Value_, arguments1Value);
                        return jSDynamicObject;
                    }
                    if (!JSGuards.isNullOrUndefined(arguments1Value)) {
                        ConstructMapFromIterableData s1_ = super.insert(new ConstructMapFromIterableData());
                        s1_.readElementNode_ = s1_.insertAccessor(ReadElementNode.create(this.getContext()));
                        s1_.isObjectNode_ = s1_.insertAccessor(IsObjectNode.create());
                        s1_.isCallableNode_ = s1_.insertAccessor(IsCallableNode.create());
                        VarHandle.storeStoreFence();
                        this.constructMapFromIterable_cache = s1_;
                        this.state_0_ = state_0 |= 2;
                        lock.unlock();
                        hasLock = false;
                        JSDynamicObject jSDynamicObject = this.constructMapFromIterable(arguments0Value_, arguments1Value, s1_.readElementNode_, s1_.isObjectNode_, s1_.isCallableNode_);
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
            s[0] = "constructEmptyMap";
            s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[1] = s;
            s = new Object[3];
            s[0] = "constructMapFromIterable";
            if ((state_0 & 2) != 0) {
                s[1] = (byte)1;
                ArrayList<List<JavaScriptBaseNode>> cached = new ArrayList<List<JavaScriptBaseNode>>();
                ConstructMapFromIterableData s1_ = this.constructMapFromIterable_cache;
                if (s1_ != null) {
                    cached.add(Arrays.asList(s1_.readElementNode_, s1_.isObjectNode_, s1_.isCallableNode_));
                }
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[2] = s;
            return Introspection.Provider.create(data);
        }

        public static ConstructorBuiltins.ConstructMapNode create(JSContext context, JSBuiltin builtin, boolean isNewTargetCase, JavaScriptNode[] arguments) {
            return new ConstructMapNodeGen(context, builtin, isNewTargetCase, arguments);
        }

        @GeneratedBy(value=ConstructorBuiltins.ConstructMapNode.class)
        private static final class ConstructMapFromIterableData
        extends Node {
            @Node.Child
            ReadElementNode readElementNode_;
            @Node.Child
            IsObjectNode isObjectNode_;
            @Node.Child
            IsCallableNode isCallableNode_;

            ConstructMapFromIterableData() {
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

    @GeneratedBy(value=ConstructorBuiltins.ConstructJavaImporterNode.class)
    public static final class ConstructJavaImporterNodeGen
    extends ConstructorBuiltins.ConstructJavaImporterNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @CompilerDirectives.CompilationFinal
        private int state_0_;

        private ConstructJavaImporterNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            if (state_0 != 0 && arguments0Value_ instanceof Object[]) {
                Object[] arguments0Value__ = (Object[])arguments0Value_;
                return this.constructJavaImporter(arguments0Value__);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.execute(frameValue);
        }

        private JSDynamicObject executeAndSpecialize(Object arguments0Value) {
            int state_0 = this.state_0_;
            if (arguments0Value instanceof Object[]) {
                Object[] arguments0Value_ = (Object[])arguments0Value;
                this.state_0_ = state_0 |= 1;
                return this.constructJavaImporter(arguments0Value_);
            }
            throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_}, arguments0Value);
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
            s[0] = "constructJavaImporter";
            s[1] = state_0 != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static ConstructorBuiltins.ConstructJavaImporterNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new ConstructJavaImporterNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=ConstructorBuiltins.ConstructJSProxyNode.class)
    public static final class ConstructJSProxyNodeGen
    extends ConstructorBuiltins.ConstructJSProxyNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @Node.Child
        private JavaScriptNode arguments2_;
        @CompilerDirectives.CompilationFinal
        private int state_0_;

        private ConstructJSProxyNodeGen(JSContext context, JSBuiltin builtin, boolean isNewTargetCase, JavaScriptNode[] arguments) {
            super(context, builtin, isNewTargetCase);
            this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
            this.arguments1_ = arguments != null && 1 < arguments.length ? arguments[1] : null;
            this.arguments2_ = arguments != null && 2 < arguments.length ? arguments[2] : null;
        }

        @Override
        public JavaScriptNode[] getArguments() {
            return new JavaScriptNode[]{this.arguments0_, this.arguments1_, this.arguments2_};
        }

        @Override
        public JSDynamicObject execute(JSDynamicObject arguments0Value, Object arguments1Value, Object arguments2Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
                return this.constructJSProxy(arguments0Value, arguments1Value, arguments2Value);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value, arguments1Value, arguments2Value);
        }

        @Override
        public Object execute(VirtualFrame frameValue) {
            int state_0 = this.state_0_;
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            Object arguments2Value_ = this.arguments2_.execute(frameValue);
            if (state_0 != 0 && arguments0Value_ instanceof JSDynamicObject) {
                JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
                return this.constructJSProxy(arguments0Value__, arguments1Value_, arguments2Value_);
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
            if (arguments0Value instanceof JSDynamicObject) {
                JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
                this.state_0_ = state_0 |= 1;
                return this.constructJSProxy(arguments0Value_, arguments1Value, arguments2Value);
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
            s[0] = "constructJSProxy";
            s[1] = state_0 != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static ConstructorBuiltins.ConstructJSProxyNode create(JSContext context, JSBuiltin builtin, boolean isNewTargetCase, JavaScriptNode[] arguments) {
            return new ConstructJSProxyNodeGen(context, builtin, isNewTargetCase, arguments);
        }
    }

    @GeneratedBy(value=ConstructorBuiltins.ConstructJSAdapterNode.class)
    public static final class ConstructJSAdapterNodeGen
    extends ConstructorBuiltins.ConstructJSAdapterNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @Node.Child
        private JavaScriptNode arguments2_;
        @CompilerDirectives.CompilationFinal
        private int state_0_;

        private ConstructJSAdapterNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            if ((state_0 & 7) != 0 && arguments0Value_ instanceof JSDynamicObject) {
                JSDynamicObject arguments1Value__;
                JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
                if ((state_0 & 3) != 0) {
                    if ((state_0 & 1) != 0 && JSGuards.isJSObject(arguments0Value__) && JSGuards.isUndefined(arguments1Value_) && JSGuards.isUndefined(arguments2Value_)) {
                        return this.constructJSAdapter(arguments0Value__, arguments1Value_, arguments2Value_);
                    }
                    if ((state_0 & 2) != 0 && arguments1Value_ instanceof JSDynamicObject) {
                        arguments1Value__ = (JSDynamicObject)arguments1Value_;
                        if (JSGuards.isJSObject(arguments0Value__) && JSGuards.isJSObject(arguments1Value__) && JSGuards.isUndefined(arguments2Value_)) {
                            return this.constructJSAdapter(arguments0Value__, arguments1Value__, arguments2Value_);
                        }
                    }
                }
                if ((state_0 & 4) != 0 && arguments1Value_ instanceof JSDynamicObject) {
                    arguments1Value__ = (JSDynamicObject)arguments1Value_;
                    if (arguments2Value_ instanceof JSDynamicObject) {
                        JSDynamicObject arguments2Value__ = (JSDynamicObject)arguments2Value_;
                        if (JSGuards.isJSObject(arguments0Value__) && JSGuards.isJSObject(arguments1Value__) && JSGuards.isJSObject(arguments2Value__)) {
                            return this.constructJSAdapter(arguments0Value__, arguments1Value__, arguments2Value__);
                        }
                    }
                }
            }
            if ((state_0 & 8) != 0 && ConstructJSAdapterNodeGen.fallbackGuard_(arguments0Value_, arguments1Value_, arguments2Value_)) {
                return this.constructJSAdapter(arguments0Value_, arguments1Value_, arguments2Value_);
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
            if (arguments0Value instanceof JSDynamicObject) {
                JSDynamicObject arguments1Value_;
                JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
                if (JSGuards.isJSObject(arguments0Value_) && JSGuards.isUndefined(arguments1Value) && JSGuards.isUndefined(arguments2Value)) {
                    this.state_0_ = state_0 |= 1;
                    return this.constructJSAdapter(arguments0Value_, arguments1Value, arguments2Value);
                }
                if (arguments1Value instanceof JSDynamicObject) {
                    arguments1Value_ = (JSDynamicObject)arguments1Value;
                    if (JSGuards.isJSObject(arguments0Value_) && JSGuards.isJSObject(arguments1Value_) && JSGuards.isUndefined(arguments2Value)) {
                        this.state_0_ = state_0 |= 2;
                        return this.constructJSAdapter(arguments0Value_, arguments1Value_, arguments2Value);
                    }
                }
                if (arguments1Value instanceof JSDynamicObject) {
                    arguments1Value_ = (JSDynamicObject)arguments1Value;
                    if (arguments2Value instanceof JSDynamicObject) {
                        JSDynamicObject arguments2Value_ = (JSDynamicObject)arguments2Value;
                        if (JSGuards.isJSObject(arguments0Value_) && JSGuards.isJSObject(arguments1Value_) && JSGuards.isJSObject(arguments2Value_)) {
                            this.state_0_ = state_0 |= 4;
                            return this.constructJSAdapter(arguments0Value_, arguments1Value_, arguments2Value_);
                        }
                    }
                }
            }
            this.state_0_ = state_0 |= 8;
            return this.constructJSAdapter(arguments0Value, arguments1Value, arguments2Value);
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
            Object[] data = new Object[5];
            data[0] = 0;
            int state_0 = this.state_0_;
            Object[] s = new Object[3];
            s[0] = "constructJSAdapter";
            s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[1] = s;
            s = new Object[3];
            s[0] = "constructJSAdapter";
            s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[2] = s;
            s = new Object[3];
            s[0] = "constructJSAdapter";
            s[1] = (state_0 & 4) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[3] = s;
            s = new Object[3];
            s[0] = "constructJSAdapter";
            s[1] = (state_0 & 8) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[4] = s;
            return Introspection.Provider.create(data);
        }

        private static boolean fallbackGuard_(Object arguments0Value, Object arguments1Value, Object arguments2Value) {
            if (arguments0Value instanceof JSDynamicObject) {
                JSDynamicObject arguments2Value_;
                JSDynamicObject arguments1Value_;
                JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
                if (JSGuards.isJSObject(arguments0Value_) && JSGuards.isUndefined(arguments1Value) && JSGuards.isUndefined(arguments2Value)) {
                    return false;
                }
                if (arguments1Value instanceof JSDynamicObject && JSGuards.isJSObject(arguments0Value_ = (JSDynamicObject)arguments0Value) && JSGuards.isJSObject(arguments1Value_ = (JSDynamicObject)arguments1Value) && JSGuards.isUndefined(arguments2Value)) {
                    return false;
                }
                if (arguments1Value instanceof JSDynamicObject && arguments2Value instanceof JSDynamicObject && JSGuards.isJSObject(arguments0Value_ = (JSDynamicObject)arguments0Value) && JSGuards.isJSObject(arguments1Value_ = (JSDynamicObject)arguments1Value) && JSGuards.isJSObject(arguments2Value_ = (JSDynamicObject)arguments2Value)) {
                    return false;
                }
            }
            return true;
        }

        public static ConstructorBuiltins.ConstructJSAdapterNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new ConstructJSAdapterNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=ConstructorBuiltins.CallRequiresNewNode.class)
    public static final class CallRequiresNewNodeGen
    extends ConstructorBuiltins.CallRequiresNewNode
    implements Introspection.Provider {
        private CallRequiresNewNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            super(context, builtin);
        }

        @Override
        public JavaScriptNode[] getArguments() {
            return new JavaScriptNode[0];
        }

        @Override
        public Object execute(VirtualFrame frameValue) {
            return this.call();
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
            s[0] = "call";
            s[1] = (byte)1;
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static ConstructorBuiltins.CallRequiresNewNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new CallRequiresNewNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=ConstructorBuiltins.ConstructDataViewNode.class)
    public static final class ConstructDataViewNodeGen
    extends ConstructorBuiltins.ConstructDataViewNode
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
        @CompilerDirectives.CompilationFinal
        private BranchProfile errorBranch;
        @CompilerDirectives.CompilationFinal
        private ConditionProfile byteLengthCondition;
        @Node.Child
        private JSToIndexNode offsetToIndexNode;
        @Node.Child
        private JSToIndexNode lengthToIndexNode;
        @Node.Child
        private InteropLibrary bufferInterop;

        private ConstructDataViewNodeGen(JSContext context, JSBuiltin builtin, boolean isNewTargetCase, JavaScriptNode[] arguments) {
            super(context, builtin, isNewTargetCase);
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
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            Object arguments2Value_ = this.arguments2_.execute(frameValue);
            Object arguments3Value_ = this.arguments3_.execute(frameValue);
            if (state_0 != 0 && arguments0Value_ instanceof JSDynamicObject) {
                JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
                if ((state_0 & 7) != 0 && arguments1Value_ instanceof JSDynamicObject) {
                    JSDynamicObject arguments1Value__ = (JSDynamicObject)arguments1Value_;
                    if ((state_0 & 1) != 0 && JSArrayBuffer.isJSHeapArrayBuffer(arguments1Value__)) {
                        return this.ofHeapArrayBuffer(arguments0Value__, arguments1Value__, arguments2Value_, arguments3Value_, this.errorBranch, this.byteLengthCondition, this.offsetToIndexNode, this.lengthToIndexNode);
                    }
                    if ((state_0 & 2) != 0 && JSArrayBuffer.isJSDirectOrSharedArrayBuffer(arguments1Value__)) {
                        return this.ofDirectArrayBuffer(arguments0Value__, arguments1Value__, arguments2Value_, arguments3Value_, this.errorBranch, this.byteLengthCondition, this.offsetToIndexNode, this.lengthToIndexNode);
                    }
                    if ((state_0 & 4) != 0 && JSArrayBuffer.isJSInteropArrayBuffer(arguments1Value__)) {
                        return this.ofInteropArrayBuffer(arguments0Value__, arguments1Value__, arguments2Value_, arguments3Value_, this.errorBranch, this.byteLengthCondition, this.offsetToIndexNode, this.lengthToIndexNode, this.bufferInterop);
                    }
                }
                if ((state_0 & 0x18) != 0) {
                    if ((state_0 & 8) != 0 && !JSAbstractBuffer.isJSAbstractBuffer(arguments1Value_) && this.bufferInterop.hasBufferElements(arguments1Value_)) {
                        return this.ofInteropBuffer(arguments0Value__, arguments1Value_, arguments2Value_, arguments3Value_, this.errorBranch, this.byteLengthCondition, this.offsetToIndexNode, this.lengthToIndexNode, this.bufferInterop);
                    }
                    if ((state_0 & 0x10) != 0 && !JSAbstractBuffer.isJSAbstractBuffer(arguments1Value_) && !this.bufferInterop.hasBufferElements(arguments1Value_)) {
                        return ConstructorBuiltins.ConstructDataViewNode.error(arguments0Value__, arguments1Value_, arguments2Value_, arguments3Value_, this.bufferInterop);
                    }
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_, arguments2Value_, arguments3Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.execute(frameValue);
        }

        private JSDynamicObject executeAndSpecialize(Object arguments0Value, Object arguments1Value, Object arguments2Value, Object arguments3Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                if (arguments0Value instanceof JSDynamicObject) {
                    InteropLibrary error_bufferInterop__;
                    InteropLibrary ofInteropBuffer_bufferInterop__;
                    JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
                    if (arguments1Value instanceof JSDynamicObject) {
                        JSDynamicObject arguments1Value_ = (JSDynamicObject)arguments1Value;
                        if (JSArrayBuffer.isJSHeapArrayBuffer(arguments1Value_)) {
                            this.errorBranch = this.errorBranch == null ? BranchProfile.create() : this.errorBranch;
                            this.byteLengthCondition = this.byteLengthCondition == null ? ConditionProfile.createBinaryProfile() : this.byteLengthCondition;
                            this.offsetToIndexNode = super.insert(this.offsetToIndexNode == null ? JSToIndexNode.create() : this.offsetToIndexNode);
                            this.lengthToIndexNode = super.insert(this.lengthToIndexNode == null ? JSToIndexNode.create() : this.lengthToIndexNode);
                            this.state_0_ = state_0 |= 1;
                            lock.unlock();
                            hasLock = false;
                            JSDynamicObject jSDynamicObject = this.ofHeapArrayBuffer(arguments0Value_, arguments1Value_, arguments2Value, arguments3Value, this.errorBranch, this.byteLengthCondition, this.offsetToIndexNode, this.lengthToIndexNode);
                            return jSDynamicObject;
                        }
                        if (JSArrayBuffer.isJSDirectOrSharedArrayBuffer(arguments1Value_)) {
                            this.errorBranch = this.errorBranch == null ? BranchProfile.create() : this.errorBranch;
                            this.byteLengthCondition = this.byteLengthCondition == null ? ConditionProfile.createBinaryProfile() : this.byteLengthCondition;
                            this.offsetToIndexNode = super.insert(this.offsetToIndexNode == null ? JSToIndexNode.create() : this.offsetToIndexNode);
                            this.lengthToIndexNode = super.insert(this.lengthToIndexNode == null ? JSToIndexNode.create() : this.lengthToIndexNode);
                            this.state_0_ = state_0 |= 2;
                            lock.unlock();
                            hasLock = false;
                            JSDynamicObject jSDynamicObject = this.ofDirectArrayBuffer(arguments0Value_, arguments1Value_, arguments2Value, arguments3Value, this.errorBranch, this.byteLengthCondition, this.offsetToIndexNode, this.lengthToIndexNode);
                            return jSDynamicObject;
                        }
                        if (JSArrayBuffer.isJSInteropArrayBuffer(arguments1Value_)) {
                            this.errorBranch = this.errorBranch == null ? BranchProfile.create() : this.errorBranch;
                            this.byteLengthCondition = this.byteLengthCondition == null ? ConditionProfile.createBinaryProfile() : this.byteLengthCondition;
                            this.offsetToIndexNode = super.insert(this.offsetToIndexNode == null ? JSToIndexNode.create() : this.offsetToIndexNode);
                            this.lengthToIndexNode = super.insert(this.lengthToIndexNode == null ? JSToIndexNode.create() : this.lengthToIndexNode);
                            this.bufferInterop = super.insert(this.bufferInterop == null ? INTEROP_LIBRARY_.createDispatched(5) : this.bufferInterop);
                            this.state_0_ = state_0 |= 4;
                            lock.unlock();
                            hasLock = false;
                            JSDynamicObject jSDynamicObject = this.ofInteropArrayBuffer(arguments0Value_, arguments1Value_, arguments2Value, arguments3Value, this.errorBranch, this.byteLengthCondition, this.offsetToIndexNode, this.lengthToIndexNode, this.bufferInterop);
                            return jSDynamicObject;
                        }
                    }
                    if (!JSAbstractBuffer.isJSAbstractBuffer(arguments1Value) && (ofInteropBuffer_bufferInterop__ = super.insert(this.bufferInterop == null ? INTEROP_LIBRARY_.createDispatched(5) : this.bufferInterop)).hasBufferElements(arguments1Value)) {
                        Object ofInteropBuffer_bufferInterop___check;
                        this.errorBranch = this.errorBranch == null ? BranchProfile.create() : this.errorBranch;
                        this.byteLengthCondition = this.byteLengthCondition == null ? ConditionProfile.createBinaryProfile() : this.byteLengthCondition;
                        this.offsetToIndexNode = super.insert(this.offsetToIndexNode == null ? JSToIndexNode.create() : this.offsetToIndexNode);
                        this.lengthToIndexNode = super.insert(this.lengthToIndexNode == null ? JSToIndexNode.create() : this.lengthToIndexNode);
                        if (this.bufferInterop == null) {
                            ofInteropBuffer_bufferInterop___check = super.insert(ofInteropBuffer_bufferInterop__);
                            if (ofInteropBuffer_bufferInterop___check == null) {
                                throw new AssertionError((Object)"Specialization 'ofInteropBuffer(JSDynamicObject, Object, Object, Object, BranchProfile, ConditionProfile, JSToIndexNode, JSToIndexNode, InteropLibrary)' contains a shared cache with name 'bufferInterop' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state.");
                            }
                            this.bufferInterop = ofInteropBuffer_bufferInterop___check;
                        }
                        this.state_0_ = state_0 |= 8;
                        lock.unlock();
                        hasLock = false;
                        ofInteropBuffer_bufferInterop___check = this.ofInteropBuffer(arguments0Value_, arguments1Value, arguments2Value, arguments3Value, this.errorBranch, this.byteLengthCondition, this.offsetToIndexNode, this.lengthToIndexNode, ofInteropBuffer_bufferInterop__);
                        return ofInteropBuffer_bufferInterop___check;
                    }
                    if (!JSAbstractBuffer.isJSAbstractBuffer(arguments1Value) && !(error_bufferInterop__ = super.insert(this.bufferInterop == null ? INTEROP_LIBRARY_.createDispatched(5) : this.bufferInterop)).hasBufferElements(arguments1Value)) {
                        if (this.bufferInterop == null) {
                            InteropLibrary error_bufferInterop___check = super.insert(error_bufferInterop__);
                            if (error_bufferInterop___check == null) {
                                throw new AssertionError((Object)"Specialization 'error(JSDynamicObject, Object, Object, Object, InteropLibrary)' contains a shared cache with name 'bufferInterop' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state.");
                            }
                            this.bufferInterop = error_bufferInterop___check;
                        }
                        this.state_0_ = state_0 |= 0x10;
                        lock.unlock();
                        hasLock = false;
                        JSDynamicObject jSDynamicObject = ConstructorBuiltins.ConstructDataViewNode.error(arguments0Value_, arguments1Value, arguments2Value, arguments3Value, error_bufferInterop__);
                        return jSDynamicObject;
                    }
                }
                throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_, this.arguments1_, this.arguments2_, this.arguments3_}, arguments0Value, arguments1Value, arguments2Value, arguments3Value);
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
            Object[] data = new Object[6];
            data[0] = 0;
            int state_0 = this.state_0_;
            Object[] s = new Object[3];
            s[0] = "ofHeapArrayBuffer";
            if ((state_0 & 1) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList<List<Cloneable>>();
                cached.add(Arrays.asList(this.errorBranch, this.byteLengthCondition, this.offsetToIndexNode, this.lengthToIndexNode));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[1] = s;
            s = new Object[3];
            s[0] = "ofDirectArrayBuffer";
            if ((state_0 & 2) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList();
                cached.add(Arrays.asList(this.errorBranch, this.byteLengthCondition, this.offsetToIndexNode, this.lengthToIndexNode));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[2] = s;
            s = new Object[3];
            s[0] = "ofInteropArrayBuffer";
            if ((state_0 & 4) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList();
                cached.add(Arrays.asList(this.errorBranch, this.byteLengthCondition, this.offsetToIndexNode, this.lengthToIndexNode, this.bufferInterop));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[3] = s;
            s = new Object[3];
            s[0] = "ofInteropBuffer";
            if ((state_0 & 8) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList();
                cached.add(Arrays.asList(this.errorBranch, this.byteLengthCondition, this.offsetToIndexNode, this.lengthToIndexNode, this.bufferInterop));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[4] = s;
            s = new Object[3];
            s[0] = "error";
            if ((state_0 & 0x10) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList();
                cached.add(Arrays.asList(this.bufferInterop));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[5] = s;
            return Introspection.Provider.create(data);
        }

        public static ConstructorBuiltins.ConstructDataViewNode create(JSContext context, JSBuiltin builtin, boolean isNewTargetCase, JavaScriptNode[] arguments) {
            return new ConstructDataViewNodeGen(context, builtin, isNewTargetCase, arguments);
        }
    }

    @GeneratedBy(value=ConstructorBuiltins.ConstructAggregateErrorNode.class)
    public static final class ConstructAggregateErrorNodeGen
    extends ConstructorBuiltins.ConstructAggregateErrorNode
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
        private ConstructErrorData constructError_cache;

        private ConstructAggregateErrorNodeGen(JSContext context, JSBuiltin builtin, boolean isNewTargetCase, JavaScriptNode[] arguments) {
            super(context, builtin, isNewTargetCase);
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
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            Object arguments2Value_ = this.arguments2_.execute(frameValue);
            Object arguments3Value_ = this.arguments3_.execute(frameValue);
            if (state_0 != 0 && arguments0Value_ instanceof JSDynamicObject) {
                JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
                ConstructErrorData s0_ = this.constructError_cache;
                if (s0_ != null) {
                    return this.constructError(arguments0Value__, arguments1Value_, arguments2Value_, arguments3Value_, s0_.toStringNode_, s0_.getIteratorMethodNode_, s0_.iteratorCallNode_, s0_.isObjectNode_, s0_.iterableToListNode_, s0_.getNextMethodNode_);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_, arguments2Value_, arguments3Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.execute(frameValue);
        }

        private JSDynamicObject executeAndSpecialize(Object arguments0Value, Object arguments1Value, Object arguments2Value, Object arguments3Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                if (arguments0Value instanceof JSDynamicObject) {
                    JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
                    ConstructErrorData s0_ = super.insert(new ConstructErrorData());
                    s0_.toStringNode_ = s0_.insertAccessor(JSToStringNode.create());
                    s0_.getIteratorMethodNode_ = s0_.insertAccessor(this.createGetIteratorMethod());
                    s0_.iteratorCallNode_ = s0_.insertAccessor(JSFunctionCallNode.createCall());
                    s0_.isObjectNode_ = s0_.insertAccessor(IsJSObjectNode.create());
                    s0_.iterableToListNode_ = s0_.insertAccessor(IterableToListNode.create());
                    s0_.getNextMethodNode_ = s0_.insertAccessor(PropertyGetNode.create(Strings.NEXT, this.getContext()));
                    VarHandle.storeStoreFence();
                    this.constructError_cache = s0_;
                    this.state_0_ = state_0 |= 1;
                    lock.unlock();
                    hasLock = false;
                    JSDynamicObject jSDynamicObject = this.constructError(arguments0Value_, arguments1Value, arguments2Value, arguments3Value, s0_.toStringNode_, s0_.getIteratorMethodNode_, s0_.iteratorCallNode_, s0_.isObjectNode_, s0_.iterableToListNode_, s0_.getNextMethodNode_);
                    return jSDynamicObject;
                }
                throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_, this.arguments1_, this.arguments2_, this.arguments3_}, arguments0Value, arguments1Value, arguments2Value, arguments3Value);
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
            s[0] = "constructError";
            if (state_0 != 0) {
                s[1] = (byte)1;
                ArrayList<List<JavaScriptBaseNode>> cached = new ArrayList<List<JavaScriptBaseNode>>();
                ConstructErrorData s0_ = this.constructError_cache;
                if (s0_ != null) {
                    cached.add(Arrays.asList(s0_.toStringNode_, s0_.getIteratorMethodNode_, s0_.iteratorCallNode_, s0_.isObjectNode_, s0_.iterableToListNode_, s0_.getNextMethodNode_));
                }
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static ConstructorBuiltins.ConstructAggregateErrorNode create(JSContext context, JSBuiltin builtin, boolean isNewTargetCase, JavaScriptNode[] arguments) {
            return new ConstructAggregateErrorNodeGen(context, builtin, isNewTargetCase, arguments);
        }

        @GeneratedBy(value=ConstructorBuiltins.ConstructAggregateErrorNode.class)
        private static final class ConstructErrorData
        extends Node {
            @Node.Child
            JSToStringNode toStringNode_;
            @Node.Child
            GetMethodNode getIteratorMethodNode_;
            @Node.Child
            JSFunctionCallNode iteratorCallNode_;
            @Node.Child
            IsJSObjectNode isObjectNode_;
            @Node.Child
            IterableToListNode iterableToListNode_;
            @Node.Child
            PropertyGetNode getNextMethodNode_;

            ConstructErrorData() {
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

    @GeneratedBy(value=ConstructorBuiltins.ConstructErrorNode.class)
    public static final class ConstructErrorNodeGen
    extends ConstructorBuiltins.ConstructErrorNode
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
        private JSToStringNode constructError1_toStringNode_;

        private ConstructErrorNodeGen(JSContext context, JSBuiltin builtin, boolean isNewTargetCase, JavaScriptNode[] arguments) {
            super(context, builtin, isNewTargetCase);
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
            if (state_0 != 0 && arguments0Value_ instanceof JSDynamicObject) {
                JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
                if ((state_0 & 1) != 0 && arguments1Value_ instanceof TruffleString) {
                    TruffleString arguments1Value__ = (TruffleString)arguments1Value_;
                    return this.constructError(arguments0Value__, arguments1Value__, arguments2Value_);
                }
                if ((state_0 & 2) != 0 && !JSGuards.isString(arguments1Value_)) {
                    return this.constructError(arguments0Value__, arguments1Value_, arguments2Value_, this.constructError1_toStringNode_);
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
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                if (arguments0Value instanceof JSDynamicObject) {
                    JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
                    if (arguments1Value instanceof TruffleString) {
                        TruffleString arguments1Value_ = (TruffleString)arguments1Value;
                        this.state_0_ = state_0 |= 1;
                        lock.unlock();
                        hasLock = false;
                        JSDynamicObject jSDynamicObject = this.constructError(arguments0Value_, arguments1Value_, arguments2Value);
                        return jSDynamicObject;
                    }
                    if (!JSGuards.isString(arguments1Value)) {
                        this.constructError1_toStringNode_ = super.insert(JSToStringNode.create());
                        this.state_0_ = state_0 |= 2;
                        lock.unlock();
                        hasLock = false;
                        JSDynamicObject jSDynamicObject = this.constructError(arguments0Value_, arguments1Value, arguments2Value, this.constructError1_toStringNode_);
                        return jSDynamicObject;
                    }
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
            Object[] data = new Object[3];
            data[0] = 0;
            int state_0 = this.state_0_;
            Object[] s = new Object[3];
            s[0] = "constructError";
            s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[1] = s;
            s = new Object[3];
            s[0] = "constructError";
            if ((state_0 & 2) != 0) {
                s[1] = (byte)1;
                ArrayList<List<JSToStringNode>> cached = new ArrayList<List<JSToStringNode>>();
                cached.add(Arrays.asList(this.constructError1_toStringNode_));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[2] = s;
            return Introspection.Provider.create(data);
        }

        public static ConstructorBuiltins.ConstructErrorNode create(JSContext context, JSBuiltin builtin, boolean isNewTargetCase, JavaScriptNode[] arguments) {
            return new ConstructErrorNodeGen(context, builtin, isNewTargetCase, arguments);
        }
    }

    @GeneratedBy(value=ConstructorBuiltins.ConstructArrayBufferNode.class)
    public static final class ConstructArrayBufferNodeGen
    extends ConstructorBuiltins.ConstructArrayBufferNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @CompilerDirectives.CompilationFinal
        private BranchProfile errorBranch;
        @Node.Child
        private InteropLibrary bufferInterop;
        @Node.Child
        private JSToIndexNode constructFromLength_toIndexNode_;

        private ConstructArrayBufferNodeGen(JSContext context, JSBuiltin builtin, boolean useShared, boolean isNewTargetCase, JavaScriptNode[] arguments) {
            super(context, builtin, useShared, isNewTargetCase);
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
                if ((state_0 & 1) != 0 && !this.bufferInterop.hasBufferElements(arguments1Value_)) {
                    return this.constructFromLength(arguments0Value__, arguments1Value_, this.constructFromLength_toIndexNode_, this.errorBranch, this.bufferInterop);
                }
                if ((state_0 & 2) != 0 && this.bufferInterop.hasBufferElements(arguments1Value_)) {
                    return this.constructFromInteropBuffer(arguments0Value__, arguments1Value_, this.errorBranch, this.bufferInterop);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.execute(frameValue);
        }

        private JSDynamicObject executeAndSpecialize(Object arguments0Value, Object arguments1Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                if (arguments0Value instanceof JSDynamicObject) {
                    JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
                    InteropLibrary constructFromLength_bufferInterop__ = super.insert(this.bufferInterop == null ? INTEROP_LIBRARY_.createDispatched(5) : this.bufferInterop);
                    if (!constructFromLength_bufferInterop__.hasBufferElements(arguments1Value)) {
                        Object constructFromLength_bufferInterop___check;
                        this.constructFromLength_toIndexNode_ = super.insert(JSToIndexNode.create());
                        BranchProfile branchProfile = this.errorBranch = this.errorBranch == null ? BranchProfile.create() : this.errorBranch;
                        if (this.bufferInterop == null) {
                            constructFromLength_bufferInterop___check = super.insert(constructFromLength_bufferInterop__);
                            if (constructFromLength_bufferInterop___check == null) {
                                throw new AssertionError((Object)"Specialization 'constructFromLength(JSDynamicObject, Object, JSToIndexNode, BranchProfile, InteropLibrary)' contains a shared cache with name 'bufferInterop' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state.");
                            }
                            this.bufferInterop = constructFromLength_bufferInterop___check;
                        }
                        this.state_0_ = state_0 |= 1;
                        lock.unlock();
                        hasLock = false;
                        constructFromLength_bufferInterop___check = this.constructFromLength(arguments0Value_, arguments1Value, this.constructFromLength_toIndexNode_, this.errorBranch, constructFromLength_bufferInterop__);
                        return constructFromLength_bufferInterop___check;
                    }
                    InteropLibrary constructFromInteropBuffer_bufferInterop__ = super.insert(this.bufferInterop == null ? INTEROP_LIBRARY_.createDispatched(5) : this.bufferInterop);
                    if (constructFromInteropBuffer_bufferInterop__.hasBufferElements(arguments1Value)) {
                        BranchProfile branchProfile = this.errorBranch = this.errorBranch == null ? BranchProfile.create() : this.errorBranch;
                        if (this.bufferInterop == null) {
                            InteropLibrary constructFromInteropBuffer_bufferInterop___check = super.insert(constructFromInteropBuffer_bufferInterop__);
                            if (constructFromInteropBuffer_bufferInterop___check == null) {
                                throw new AssertionError((Object)"Specialization 'constructFromInteropBuffer(JSDynamicObject, Object, BranchProfile, InteropLibrary)' contains a shared cache with name 'bufferInterop' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state.");
                            }
                            this.bufferInterop = constructFromInteropBuffer_bufferInterop___check;
                        }
                        this.state_0_ = state_0 |= 2;
                        lock.unlock();
                        hasLock = false;
                        JSDynamicObject jSDynamicObject = this.constructFromInteropBuffer(arguments0Value_, arguments1Value, this.errorBranch, constructFromInteropBuffer_bufferInterop__);
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
            s[0] = "constructFromLength";
            if ((state_0 & 1) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList<List<Cloneable>>();
                cached.add(Arrays.asList(this.constructFromLength_toIndexNode_, this.errorBranch, this.bufferInterop));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[1] = s;
            s = new Object[3];
            s[0] = "constructFromInteropBuffer";
            if ((state_0 & 2) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList();
                cached.add(Arrays.asList(this.errorBranch, this.bufferInterop));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[2] = s;
            return Introspection.Provider.create(data);
        }

        public static ConstructorBuiltins.ConstructArrayBufferNode create(JSContext context, JSBuiltin builtin, boolean useShared, boolean isNewTargetCase, JavaScriptNode[] arguments) {
            return new ConstructArrayBufferNodeGen(context, builtin, useShared, isNewTargetCase, arguments);
        }
    }

    @GeneratedBy(value=ConstructorBuiltins.CallTypedArrayNode.class)
    public static final class CallTypedArrayNodeGen
    extends ConstructorBuiltins.CallTypedArrayNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @CompilerDirectives.CompilationFinal
        private int state_0_;

        private CallTypedArrayNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            if (state_0 != 0 && arguments0Value_ instanceof Object[]) {
                Object[] arguments0Value__ = (Object[])arguments0Value_;
                return this.callTypedArray(arguments0Value__);
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
            if (arguments0Value instanceof Object[]) {
                Object[] arguments0Value_ = (Object[])arguments0Value;
                this.state_0_ = state_0 |= 1;
                return this.callTypedArray(arguments0Value_);
            }
            throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_}, arguments0Value);
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
            s[0] = "callTypedArray";
            s[1] = state_0 != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static ConstructorBuiltins.CallTypedArrayNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new CallTypedArrayNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=ConstructorBuiltins.CreateDynamicFunctionNode.class)
    static final class CreateDynamicFunctionNodeGen
    extends ConstructorBuiltins.CreateDynamicFunctionNode
    implements Introspection.Provider {
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @CompilerDirectives.CompilationFinal
        private volatile int exclude_;
        @CompilerDirectives.CompilationFinal
        private CachedData cached_cache;
        @CompilerDirectives.CompilationFinal
        private LRUCache<ConstructorBuiltins.CreateDynamicFunctionNode.CachedSourceKey, ScriptNode> uncached_cache_;
        @CompilerDirectives.CompilationFinal
        private ConditionProfile uncached_cacheHit_;

        private CreateDynamicFunctionNodeGen(JSContext context, boolean generatorFunction, boolean asyncFunction) {
            super(context, generatorFunction, asyncFunction);
        }

        @Override
        protected JSDynamicObject executeFunction(String arg0Value, String arg1Value, String arg2Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
                CachedData s0_;
                if ((state_0 & 1) != 0 && (s0_ = this.cached_cache) != null && ConstructorBuiltins.CreateDynamicFunctionNode.equals(s0_.cachedParamList_, arg0Value) && ConstructorBuiltins.CreateDynamicFunctionNode.equals(s0_.cachedBody_, arg1Value) && ConstructorBuiltins.CreateDynamicFunctionNode.equals(s0_.cachedSourceName_, arg2Value)) {
                    return this.doCached(arg0Value, arg1Value, arg2Value, s0_.cachedParamList_, s0_.cachedBody_, s0_.cachedSourceName_, s0_.cachedParsedFunction_);
                }
                if ((state_0 & 2) != 0) {
                    return this.doUncached(arg0Value, arg1Value, arg2Value, this.uncached_cache_, this.uncached_cacheHit_);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private JSDynamicObject executeAndSpecialize(String arg0Value, String arg1Value, String arg2Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                int exclude = this.exclude_;
                if (exclude == 0) {
                    String cachedSourceName__;
                    String cachedBody__;
                    String cachedParamList__;
                    CachedData s0_ = this.cached_cache;
                    boolean Cached_duplicateFound_ = false;
                    if ((state_0 & 1) != 0 && ConstructorBuiltins.CreateDynamicFunctionNode.equals(s0_.cachedParamList_, arg0Value) && ConstructorBuiltins.CreateDynamicFunctionNode.equals(s0_.cachedBody_, arg1Value) && ConstructorBuiltins.CreateDynamicFunctionNode.equals(s0_.cachedSourceName_, arg2Value)) {
                        Cached_duplicateFound_ = true;
                    }
                    if (!Cached_duplicateFound_ && ConstructorBuiltins.CreateDynamicFunctionNode.equals(cachedParamList__ = arg0Value, arg0Value) && ConstructorBuiltins.CreateDynamicFunctionNode.equals(cachedBody__ = arg1Value, arg1Value) && ConstructorBuiltins.CreateDynamicFunctionNode.equals(cachedSourceName__ = arg2Value, arg2Value) && (state_0 & 1) == 0) {
                        s0_ = new CachedData();
                        s0_.cachedParamList_ = cachedParamList__;
                        s0_.cachedBody_ = cachedBody__;
                        s0_.cachedSourceName_ = cachedSourceName__;
                        s0_.cachedParsedFunction_ = this.createAssumedValue();
                        VarHandle.storeStoreFence();
                        this.cached_cache = s0_;
                        this.state_0_ = state_0 |= 1;
                        Cached_duplicateFound_ = true;
                    }
                    if (Cached_duplicateFound_) {
                        lock.unlock();
                        hasLock = false;
                        JSDynamicObject jSDynamicObject = this.doCached(arg0Value, arg1Value, arg2Value, s0_.cachedParamList_, s0_.cachedBody_, s0_.cachedSourceName_, s0_.cachedParsedFunction_);
                        return jSDynamicObject;
                    }
                }
                this.uncached_cache_ = this.createCache();
                this.uncached_cacheHit_ = ConditionProfile.createCountingProfile();
                this.exclude_ = exclude |= 1;
                this.cached_cache = null;
                state_0 &= 0xFFFFFFFE;
                this.state_0_ = state_0 |= 2;
                lock.unlock();
                hasLock = false;
                JSDynamicObject jSDynamicObject = this.doUncached(arg0Value, arg1Value, arg2Value, this.uncached_cache_, this.uncached_cacheHit_);
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
            ArrayList<List<Object>> cached;
            Object[] data = new Object[3];
            data[0] = 0;
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            Object[] s = new Object[3];
            s[0] = "doCached";
            if ((state_0 & 1) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList<List<Object>>();
                CachedData s0_ = this.cached_cache;
                if (s0_ != null) {
                    cached.add(Arrays.asList(s0_.cachedParamList_, s0_.cachedBody_, s0_.cachedSourceName_, s0_.cachedParsedFunction_));
                }
                s[2] = cached;
            } else {
                s[1] = exclude != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0);
            }
            data[1] = s;
            s = new Object[3];
            s[0] = "doUncached";
            if ((state_0 & 2) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList();
                cached.add(Arrays.asList(this.uncached_cache_, this.uncached_cacheHit_));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[2] = s;
            return Introspection.Provider.create(data);
        }

        public static ConstructorBuiltins.CreateDynamicFunctionNode create(JSContext context, boolean generatorFunction, boolean asyncFunction) {
            return new CreateDynamicFunctionNodeGen(context, generatorFunction, asyncFunction);
        }

        @GeneratedBy(value=ConstructorBuiltins.CreateDynamicFunctionNode.class)
        private static final class CachedData {
            @CompilerDirectives.CompilationFinal
            String cachedParamList_;
            @CompilerDirectives.CompilationFinal
            String cachedBody_;
            @CompilerDirectives.CompilationFinal
            String cachedSourceName_;
            @CompilerDirectives.CompilationFinal
            AssumedValue<ScriptNode> cachedParsedFunction_;

            CachedData() {
            }
        }
    }

    @GeneratedBy(value=ConstructorBuiltins.ConstructFunctionNode.class)
    public static final class ConstructFunctionNodeGen
    extends ConstructorBuiltins.ConstructFunctionNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @CompilerDirectives.CompilationFinal
        private ConditionProfile hasArgsProfile_;
        @CompilerDirectives.CompilationFinal
        private ConditionProfile hasParamsProfile_;

        private ConstructFunctionNodeGen(JSContext context, JSBuiltin builtin, boolean generatorFunction, boolean asyncFunction, boolean isNewTargetCase, JavaScriptNode[] arguments) {
            super(context, builtin, generatorFunction, asyncFunction, isNewTargetCase);
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
                if (arguments1Value_ instanceof Object[]) {
                    Object[] arguments1Value__ = (Object[])arguments1Value_;
                    return this.constructFunction(arguments0Value__, arguments1Value__, this.hasArgsProfile_, this.hasParamsProfile_);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.execute(frameValue);
        }

        private JSDynamicObject executeAndSpecialize(Object arguments0Value, Object arguments1Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                if (arguments0Value instanceof JSDynamicObject) {
                    JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
                    if (arguments1Value instanceof Object[]) {
                        Object[] arguments1Value_ = (Object[])arguments1Value;
                        this.hasArgsProfile_ = ConditionProfile.createBinaryProfile();
                        this.hasParamsProfile_ = ConditionProfile.createBinaryProfile();
                        this.state_0_ = state_0 |= 1;
                        lock.unlock();
                        hasLock = false;
                        JSDynamicObject jSDynamicObject = this.constructFunction(arguments0Value_, arguments1Value_, this.hasArgsProfile_, this.hasParamsProfile_);
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
            s[0] = "constructFunction";
            if (state_0 != 0) {
                s[1] = (byte)1;
                ArrayList<List<ConditionProfile>> cached = new ArrayList<List<ConditionProfile>>();
                cached.add(Arrays.asList(this.hasArgsProfile_, this.hasParamsProfile_));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static ConstructorBuiltins.ConstructFunctionNode create(JSContext context, JSBuiltin builtin, boolean generatorFunction, boolean asyncFunction, boolean isNewTargetCase, JavaScriptNode[] arguments) {
            return new ConstructFunctionNodeGen(context, builtin, generatorFunction, asyncFunction, isNewTargetCase, arguments);
        }
    }

    @GeneratedBy(value=ConstructorBuiltins.ConstructBigIntNode.class)
    public static final class ConstructBigIntNodeGen
    extends ConstructorBuiltins.ConstructBigIntNode
    implements Introspection.Provider {
        private ConstructBigIntNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            super(context, builtin);
        }

        @Override
        public JavaScriptNode[] getArguments() {
            return new JavaScriptNode[0];
        }

        @Override
        public Object execute(VirtualFrame frameValue) {
            return ConstructorBuiltins.ConstructBigIntNode.construct();
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
            s[0] = "construct";
            s[1] = (byte)1;
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static ConstructorBuiltins.ConstructBigIntNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new ConstructBigIntNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=ConstructorBuiltins.CallBigIntNode.class)
    public static final class CallBigIntNodeGen
    extends ConstructorBuiltins.CallBigIntNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private JSNumberToBigIntNode callBigInt_numberToBigIntNode_;
        @Node.Child
        private JSToBigIntNode callBigInt_toBigIntNode_;

        private CallBigIntNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            super(context, builtin);
            this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
        }

        @Override
        public JavaScriptNode[] getArguments() {
            return new JavaScriptNode[]{this.arguments0_};
        }

        @Override
        public Object execute(VirtualFrame frameValue) {
            Object[] arguments0Value__;
            int state_0 = this.state_0_;
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            if ((state_0 & 2) != 0 && arguments0Value_ instanceof Object[] && (arguments0Value__ = (Object[])arguments0Value_).length > 0) {
                return this.callBigInt(arguments0Value__, this.callBigInt_numberToBigIntNode_, this.callBigInt_toBigIntNode_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            Object[] arguments0Value__;
            int state_0 = this.state_0_;
            if ((state_0 & 2) != 0) {
                this.execute(frameValue);
                return;
            }
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            if ((state_0 & 1) != 0 && arguments0Value_ instanceof Object[] && (arguments0Value__ = (Object[])arguments0Value_).length == 0) {
                this.callBigIntZero(arguments0Value__);
                return;
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.executeAndSpecialize(arguments0Value_);
        }

        private Object executeAndSpecialize(Object arguments0Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                if (arguments0Value instanceof Object[]) {
                    Object[] arguments0Value_ = (Object[])arguments0Value;
                    if (arguments0Value_.length == 0) {
                        this.state_0_ = state_0 |= 1;
                        lock.unlock();
                        hasLock = false;
                        this.callBigIntZero(arguments0Value_);
                        Object var6_6 = null;
                        return var6_6;
                    }
                    if (arguments0Value_.length > 0) {
                        this.callBigInt_numberToBigIntNode_ = super.insert(JSNumberToBigIntNode.create());
                        this.callBigInt_toBigIntNode_ = super.insert(JSToBigIntNode.create());
                        this.state_0_ = state_0 |= 2;
                        lock.unlock();
                        hasLock = false;
                        Object object = this.callBigInt(arguments0Value_, this.callBigInt_numberToBigIntNode_, this.callBigInt_toBigIntNode_);
                        return object;
                    }
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
            s[0] = "callBigIntZero";
            s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[1] = s;
            s = new Object[3];
            s[0] = "callBigInt";
            if ((state_0 & 2) != 0) {
                s[1] = (byte)1;
                ArrayList<List<JavaScriptBaseNode>> cached = new ArrayList<List<JavaScriptBaseNode>>();
                cached.add(Arrays.asList(this.callBigInt_numberToBigIntNode_, this.callBigInt_toBigIntNode_));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[2] = s;
            return Introspection.Provider.create(data);
        }

        public static ConstructorBuiltins.CallBigIntNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new CallBigIntNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=ConstructorBuiltins.ConstructNumberNode.class)
    public static final class ConstructNumberNodeGen
    extends ConstructorBuiltins.ConstructNumberNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private JSToNumericNode constructNumber_toNumericNode_;
        @Node.Child
        private JSNumericToNumberNode constructNumber_toNumberFromNumericNode_;

        private ConstructNumberNodeGen(JSContext context, JSBuiltin builtin, boolean isNewTargetCase, JavaScriptNode[] arguments) {
            super(context, builtin, isNewTargetCase);
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
                if (arguments1Value_ instanceof Object[]) {
                    Object[] arguments1Value__ = (Object[])arguments1Value_;
                    if ((state_0 & 1) != 0 && arguments1Value__.length == 0) {
                        return this.constructNumberZero(arguments0Value__, arguments1Value__);
                    }
                    if ((state_0 & 2) != 0 && arguments1Value__.length > 0) {
                        return this.constructNumber(arguments0Value__, arguments1Value__, this.constructNumber_toNumericNode_, this.constructNumber_toNumberFromNumericNode_);
                    }
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.execute(frameValue);
        }

        private JSDynamicObject executeAndSpecialize(Object arguments0Value, Object arguments1Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                if (arguments0Value instanceof JSDynamicObject) {
                    JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
                    if (arguments1Value instanceof Object[]) {
                        Object[] arguments1Value_ = (Object[])arguments1Value;
                        if (arguments1Value_.length == 0) {
                            this.state_0_ = state_0 |= 1;
                            lock.unlock();
                            hasLock = false;
                            JSDynamicObject jSDynamicObject = this.constructNumberZero(arguments0Value_, arguments1Value_);
                            return jSDynamicObject;
                        }
                        if (arguments1Value_.length > 0) {
                            this.constructNumber_toNumericNode_ = super.insert(JSToNumericNode.create());
                            this.constructNumber_toNumberFromNumericNode_ = super.insert(JSNumericToNumberNode.create());
                            this.state_0_ = state_0 |= 2;
                            lock.unlock();
                            hasLock = false;
                            JSDynamicObject jSDynamicObject = this.constructNumber(arguments0Value_, arguments1Value_, this.constructNumber_toNumericNode_, this.constructNumber_toNumberFromNumericNode_);
                            return jSDynamicObject;
                        }
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
            s[0] = "constructNumberZero";
            s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[1] = s;
            s = new Object[3];
            s[0] = "constructNumber";
            if ((state_0 & 2) != 0) {
                s[1] = (byte)1;
                ArrayList<List<JavaScriptBaseNode>> cached = new ArrayList<List<JavaScriptBaseNode>>();
                cached.add(Arrays.asList(this.constructNumber_toNumericNode_, this.constructNumber_toNumberFromNumericNode_));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[2] = s;
            return Introspection.Provider.create(data);
        }

        public static ConstructorBuiltins.ConstructNumberNode create(JSContext context, JSBuiltin builtin, boolean isNewTargetCase, JavaScriptNode[] arguments) {
            return new ConstructNumberNodeGen(context, builtin, isNewTargetCase, arguments);
        }
    }

    @GeneratedBy(value=ConstructorBuiltins.CallNumberNode.class)
    public static final class CallNumberNodeGen
    extends ConstructorBuiltins.CallNumberNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private JSToNumericNode callNumber_toNumericNode_;
        @Node.Child
        private JSNumericToNumberNode callNumber_toNumberFromNumericNode_;

        private CallNumberNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            if (state_0 != 0 && arguments0Value_ instanceof Object[]) {
                Object[] arguments0Value__ = (Object[])arguments0Value_;
                if ((state_0 & 1) != 0 && arguments0Value__.length == 0) {
                    return this.callNumberZero(arguments0Value__);
                }
                if ((state_0 & 2) != 0 && arguments0Value__.length > 0) {
                    return this.callNumber(arguments0Value__, this.callNumber_toNumericNode_, this.callNumber_toNumberFromNumericNode_);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_);
        }

        @Override
        public int executeInt(VirtualFrame frameValue) throws UnexpectedResultException {
            Object[] arguments0Value__;
            int state_0 = this.state_0_;
            if ((state_0 & 2) != 0) {
                return JSTypesGen.expectInteger(this.execute(frameValue));
            }
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            if ((state_0 & 1) != 0 && arguments0Value_ instanceof Object[] && (arguments0Value__ = (Object[])arguments0Value_).length == 0) {
                return this.callNumberZero(arguments0Value__);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return JSTypesGen.expectInteger(this.executeAndSpecialize(arguments0Value_));
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            int state_0 = this.state_0_;
            try {
                if ((state_0 & 2) == 0 && state_0 != 0) {
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

        private Object executeAndSpecialize(Object arguments0Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                if (arguments0Value instanceof Object[]) {
                    Object[] arguments0Value_ = (Object[])arguments0Value;
                    if (arguments0Value_.length == 0) {
                        this.state_0_ = state_0 |= 1;
                        lock.unlock();
                        hasLock = false;
                        Integer n = this.callNumberZero(arguments0Value_);
                        return n;
                    }
                    if (arguments0Value_.length > 0) {
                        this.callNumber_toNumericNode_ = super.insert(JSToNumericNode.create());
                        this.callNumber_toNumberFromNumericNode_ = super.insert(JSNumericToNumberNode.create());
                        this.state_0_ = state_0 |= 2;
                        lock.unlock();
                        hasLock = false;
                        Number number = this.callNumber(arguments0Value_, this.callNumber_toNumericNode_, this.callNumber_toNumberFromNumericNode_);
                        return number;
                    }
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
            s[0] = "callNumberZero";
            s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[1] = s;
            s = new Object[3];
            s[0] = "callNumber";
            if ((state_0 & 2) != 0) {
                s[1] = (byte)1;
                ArrayList<List<JavaScriptBaseNode>> cached = new ArrayList<List<JavaScriptBaseNode>>();
                cached.add(Arrays.asList(this.callNumber_toNumericNode_, this.callNumber_toNumberFromNumericNode_));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[2] = s;
            return Introspection.Provider.create(data);
        }

        public static ConstructorBuiltins.CallNumberNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new CallNumberNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=ConstructorBuiltins.ConstructObjectNode.class)
    public static final class ConstructObjectNodeGen
    extends ConstructorBuiltins.ConstructObjectNode
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
        private ConstructObjectJSObject0Data constructObjectJSObject0_cache;
        @Node.Child
        private JSToObjectNode constructObjectJSObject1_toObjectNode_;
        @CompilerDirectives.CompilationFinal
        private ConditionProfile constructObjectJSObject1_isNull_;

        private ConstructObjectNodeGen(JSContext context, JSBuiltin builtin, boolean isNewTargetCase, JavaScriptNode[] arguments) {
            super(context, builtin, isNewTargetCase);
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
            if (state_0 != 0 && arguments0Value_ instanceof JSDynamicObject) {
                JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
                if (arguments1Value_ instanceof Object[]) {
                    Object[] arguments1Value__ = (Object[])arguments1Value_;
                    if ((state_0 & 1) != 0) {
                        assert (this.isNewTargetCase);
                        return this.constructObjectNewTarget(arguments0Value__, arguments1Value__);
                    }
                    if ((state_0 & 2) != 0 && arguments1Value__.length == 0) {
                        return this.constructObject0(arguments0Value__, arguments1Value__);
                    }
                    if ((state_0 & 4) != 0) {
                        ConstructObjectJSObject0Data s2_ = this.constructObjectJSObject0_cache;
                        while (s2_ != null) {
                            if (s2_.interop_.accepts(ConstructorBuiltins.ConstructObjectNode.firstArgument(arguments1Value__))) {
                                assert (!this.isNewTargetCase);
                                if (arguments1Value__.length > 0 && !ConstructorBuiltins.ConstructObjectNode.arg0NullOrUndefined(arguments1Value__)) {
                                    return this.constructObjectJSObject(arguments0Value__, arguments1Value__, s2_.toObjectNode_, s2_.interop_, s2_.isNull_);
                                }
                            }
                            s2_ = s2_.next_;
                        }
                    }
                    if ((state_0 & 8) != 0) {
                        assert (!this.isNewTargetCase);
                        if (arguments1Value__.length > 0 && !ConstructorBuiltins.ConstructObjectNode.arg0NullOrUndefined(arguments1Value__)) {
                            return this.constructObjectJSObject1Boundary(state_0, arguments0Value__, arguments1Value__);
                        }
                    }
                    if ((state_0 & 0x10) != 0 && arguments1Value__.length > 0 && ConstructorBuiltins.ConstructObjectNode.arg0NullOrUndefined(arguments1Value__)) {
                        return this.constructObjectNullOrUndefined(arguments0Value__, arguments1Value__);
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
        private Object constructObjectJSObject1Boundary(int state_0, JSDynamicObject arguments0Value__, Object[] arguments1Value__) {
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this);
            try {
                InteropLibrary constructObjectJSObject1_interop__ = INTEROP_LIBRARY_.getUncached(ConstructorBuiltins.ConstructObjectNode.firstArgument(arguments1Value__));
                Object object = this.constructObjectJSObject(arguments0Value__, arguments1Value__, this.constructObjectJSObject1_toObjectNode_, constructObjectJSObject1_interop__, this.constructObjectJSObject1_isNull_);
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
                int state_0 = this.state_0_;
                int exclude = this.exclude_;
                if (arguments0Value instanceof JSDynamicObject) {
                    JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
                    if (arguments1Value instanceof Object[]) {
                        Object[] arguments1Value_ = (Object[])arguments1Value;
                        if (this.isNewTargetCase) {
                            this.state_0_ = state_0 |= 1;
                            lock.unlock();
                            hasLock = false;
                            JSDynamicObject jSDynamicObject = this.constructObjectNewTarget(arguments0Value_, arguments1Value_);
                            return jSDynamicObject;
                        }
                        if (arguments1Value_.length == 0) {
                            this.state_0_ = state_0 |= 2;
                            lock.unlock();
                            hasLock = false;
                            JSDynamicObject jSDynamicObject = this.constructObject0(arguments0Value_, arguments1Value_);
                            return jSDynamicObject;
                        }
                        if (exclude == 0) {
                            int count2_ = 0;
                            ConstructObjectJSObject0Data s2_ = this.constructObjectJSObject0_cache;
                            if ((state_0 & 4) != 0) {
                                while (s2_ != null) {
                                    if (s2_.interop_.accepts(ConstructorBuiltins.ConstructObjectNode.firstArgument(arguments1Value_))) {
                                        assert (!this.isNewTargetCase);
                                        if (arguments1Value_.length > 0 && !ConstructorBuiltins.ConstructObjectNode.arg0NullOrUndefined(arguments1Value_)) break;
                                    }
                                    s2_ = s2_.next_;
                                    ++count2_;
                                }
                            }
                            if (s2_ == null && !this.isNewTargetCase && arguments1Value_.length > 0 && !ConstructorBuiltins.ConstructObjectNode.arg0NullOrUndefined(arguments1Value_) && count2_ < 5) {
                                s2_ = super.insert(new ConstructObjectJSObject0Data(this.constructObjectJSObject0_cache));
                                s2_.toObjectNode_ = s2_.insertAccessor(JSToObjectNode.createToObject(this.getContext()));
                                s2_.interop_ = s2_.insertAccessor(INTEROP_LIBRARY_.create(ConstructorBuiltins.ConstructObjectNode.firstArgument(arguments1Value_)));
                                s2_.isNull_ = ConditionProfile.createBinaryProfile();
                                VarHandle.storeStoreFence();
                                this.constructObjectJSObject0_cache = s2_;
                                this.state_0_ = state_0 |= 4;
                            }
                            if (s2_ != null) {
                                lock.unlock();
                                hasLock = false;
                                Object object = this.constructObjectJSObject(arguments0Value_, arguments1Value_, s2_.toObjectNode_, s2_.interop_, s2_.isNull_);
                                return object;
                            }
                        }
                        InteropLibrary constructObjectJSObject1_interop__ = null;
                        EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                        Node prev_ = encapsulating_.set(this);
                        try {
                            if (!this.isNewTargetCase && arguments1Value_.length > 0 && !ConstructorBuiltins.ConstructObjectNode.arg0NullOrUndefined(arguments1Value_)) {
                                this.constructObjectJSObject1_toObjectNode_ = super.insert(JSToObjectNode.createToObject(this.getContext()));
                                constructObjectJSObject1_interop__ = INTEROP_LIBRARY_.getUncached(ConstructorBuiltins.ConstructObjectNode.firstArgument(arguments1Value_));
                                this.constructObjectJSObject1_isNull_ = ConditionProfile.createBinaryProfile();
                                this.exclude_ = exclude |= 1;
                                this.constructObjectJSObject0_cache = null;
                                state_0 &= 0xFFFFFFFB;
                                this.state_0_ = state_0 |= 8;
                                lock.unlock();
                                hasLock = false;
                                Object object = this.constructObjectJSObject(arguments0Value_, arguments1Value_, this.constructObjectJSObject1_toObjectNode_, constructObjectJSObject1_interop__, this.constructObjectJSObject1_isNull_);
                                return object;
                            }
                        }
                        finally {
                            encapsulating_.set(prev_);
                        }
                        if (arguments1Value_.length > 0 && ConstructorBuiltins.ConstructObjectNode.arg0NullOrUndefined(arguments1Value_)) {
                            this.state_0_ = state_0 |= 0x10;
                            lock.unlock();
                            hasLock = false;
                            JSDynamicObject jSDynamicObject = this.constructObjectNullOrUndefined(arguments0Value_, arguments1Value_);
                            return jSDynamicObject;
                        }
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
            ConstructObjectJSObject0Data s2_;
            int state_0 = this.state_0_;
            if (state_0 == 0) {
                return NodeCost.UNINITIALIZED;
            }
            if ((state_0 & state_0 - 1) == 0 && ((s2_ = this.constructObjectJSObject0_cache) == null || s2_.next_ == null)) {
                return NodeCost.MONOMORPHIC;
            }
            return NodeCost.POLYMORPHIC;
        }

        @Override
        public Introspection getIntrospectionData() {
            ArrayList<List<Cloneable>> cached;
            Object[] data = new Object[6];
            data[0] = 0;
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            Object[] s = new Object[3];
            s[0] = "constructObjectNewTarget";
            s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[1] = s;
            s = new Object[3];
            s[0] = "constructObject0";
            s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[2] = s;
            s = new Object[3];
            s[0] = "constructObjectJSObject";
            if ((state_0 & 4) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList<List<Cloneable>>();
                ConstructObjectJSObject0Data s2_ = this.constructObjectJSObject0_cache;
                while (s2_ != null) {
                    cached.add(Arrays.asList(s2_.toObjectNode_, s2_.interop_, s2_.isNull_));
                    s2_ = s2_.next_;
                }
                s[2] = cached;
            } else {
                s[1] = exclude != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0);
            }
            data[3] = s;
            s = new Object[3];
            s[0] = "constructObjectJSObject";
            if ((state_0 & 8) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList();
                cached.add(Arrays.asList(this.constructObjectJSObject1_toObjectNode_, this.constructObjectJSObject1_isNull_));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[4] = s;
            s = new Object[3];
            s[0] = "constructObjectNullOrUndefined";
            s[1] = (state_0 & 0x10) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[5] = s;
            return Introspection.Provider.create(data);
        }

        public static ConstructorBuiltins.ConstructObjectNode create(JSContext context, JSBuiltin builtin, boolean isNewTargetCase, JavaScriptNode[] arguments) {
            return new ConstructObjectNodeGen(context, builtin, isNewTargetCase, arguments);
        }

        @GeneratedBy(value=ConstructorBuiltins.ConstructObjectNode.class)
        private static final class ConstructObjectJSObject0Data
        extends Node {
            @Node.Child
            ConstructObjectJSObject0Data next_;
            @Node.Child
            JSToObjectNode toObjectNode_;
            @Node.Child
            InteropLibrary interop_;
            @CompilerDirectives.CompilationFinal
            ConditionProfile isNull_;

            ConstructObjectJSObject0Data(ConstructObjectJSObject0Data next_) {
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

    @GeneratedBy(value=ConstructorBuiltins.ConstructDateTimeFormatNode.class)
    public static final class ConstructDateTimeFormatNodeGen
    extends ConstructorBuiltins.ConstructDateTimeFormatNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @Node.Child
        private JavaScriptNode arguments2_;
        @CompilerDirectives.CompilationFinal
        private int state_0_;

        private ConstructDateTimeFormatNodeGen(JSContext context, JSBuiltin builtin, boolean newTargetCase, JavaScriptNode[] arguments) {
            super(context, builtin, newTargetCase);
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
            if (state_0 != 0 && arguments0Value_ instanceof JSDynamicObject) {
                JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
                return this.constructDateTimeFormat(arguments0Value__, arguments1Value_, arguments2Value_);
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
            if (arguments0Value instanceof JSDynamicObject) {
                JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
                this.state_0_ = state_0 |= 1;
                return this.constructDateTimeFormat(arguments0Value_, arguments1Value, arguments2Value);
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
            s[0] = "constructDateTimeFormat";
            s[1] = state_0 != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static ConstructorBuiltins.ConstructDateTimeFormatNode create(JSContext context, JSBuiltin builtin, boolean newTargetCase, JavaScriptNode[] arguments) {
            return new ConstructDateTimeFormatNodeGen(context, builtin, newTargetCase, arguments);
        }
    }

    @GeneratedBy(value=ConstructorBuiltins.CallDateTimeFormatNode.class)
    public static final class CallDateTimeFormatNodeGen
    extends ConstructorBuiltins.CallDateTimeFormatNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;

        private CallDateTimeFormatNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            return this.callDateTimeFormat(arguments0Value_, arguments1Value_);
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
            s[0] = "callDateTimeFormat";
            s[1] = (byte)1;
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static ConstructorBuiltins.CallDateTimeFormatNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new CallDateTimeFormatNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=ConstructorBuiltins.ConstructPluralRulesNode.class)
    public static final class ConstructPluralRulesNodeGen
    extends ConstructorBuiltins.ConstructPluralRulesNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @Node.Child
        private JavaScriptNode arguments2_;
        @CompilerDirectives.CompilationFinal
        private int state_0_;

        private ConstructPluralRulesNodeGen(JSContext context, JSBuiltin builtin, boolean newTargetCase, JavaScriptNode[] arguments) {
            super(context, builtin, newTargetCase);
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
            if (state_0 != 0 && arguments0Value_ instanceof JSDynamicObject) {
                JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
                return this.constructPluralRules(arguments0Value__, arguments1Value_, arguments2Value_);
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
            if (arguments0Value instanceof JSDynamicObject) {
                JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
                this.state_0_ = state_0 |= 1;
                return this.constructPluralRules(arguments0Value_, arguments1Value, arguments2Value);
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
            s[0] = "constructPluralRules";
            s[1] = state_0 != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static ConstructorBuiltins.ConstructPluralRulesNode create(JSContext context, JSBuiltin builtin, boolean newTargetCase, JavaScriptNode[] arguments) {
            return new ConstructPluralRulesNodeGen(context, builtin, newTargetCase, arguments);
        }
    }

    @GeneratedBy(value=ConstructorBuiltins.ConstructNumberFormatNode.class)
    public static final class ConstructNumberFormatNodeGen
    extends ConstructorBuiltins.ConstructNumberFormatNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @Node.Child
        private JavaScriptNode arguments2_;
        @CompilerDirectives.CompilationFinal
        private int state_0_;

        private ConstructNumberFormatNodeGen(JSContext context, JSBuiltin builtin, boolean newTargetCase, JavaScriptNode[] arguments) {
            super(context, builtin, newTargetCase);
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
            if (state_0 != 0 && arguments0Value_ instanceof JSDynamicObject) {
                JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
                return this.constructNumberFormat(arguments0Value__, arguments1Value_, arguments2Value_);
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
            if (arguments0Value instanceof JSDynamicObject) {
                JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
                this.state_0_ = state_0 |= 1;
                return this.constructNumberFormat(arguments0Value_, arguments1Value, arguments2Value);
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
            s[0] = "constructNumberFormat";
            s[1] = state_0 != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static ConstructorBuiltins.ConstructNumberFormatNode create(JSContext context, JSBuiltin builtin, boolean newTargetCase, JavaScriptNode[] arguments) {
            return new ConstructNumberFormatNodeGen(context, builtin, newTargetCase, arguments);
        }
    }

    @GeneratedBy(value=ConstructorBuiltins.CallNumberFormatNode.class)
    public static final class CallNumberFormatNodeGen
    extends ConstructorBuiltins.CallNumberFormatNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;

        private CallNumberFormatNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            return this.callNumberFormat(arguments0Value_, arguments1Value_);
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
            s[0] = "callNumberFormat";
            s[1] = (byte)1;
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static ConstructorBuiltins.CallNumberFormatNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new CallNumberFormatNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=ConstructorBuiltins.ConstructLocaleNode.class)
    public static final class ConstructLocaleNodeGen
    extends ConstructorBuiltins.ConstructLocaleNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @Node.Child
        private JavaScriptNode arguments2_;
        @CompilerDirectives.CompilationFinal
        private int state_0_;

        private ConstructLocaleNodeGen(JSContext context, JSBuiltin builtin, boolean newTargetCase, JavaScriptNode[] arguments) {
            super(context, builtin, newTargetCase);
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
            if (state_0 != 0 && arguments0Value_ instanceof JSDynamicObject) {
                JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
                return this.constructLocale(arguments0Value__, arguments1Value_, arguments2Value_);
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
            if (arguments0Value instanceof JSDynamicObject) {
                JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
                this.state_0_ = state_0 |= 1;
                return this.constructLocale(arguments0Value_, arguments1Value, arguments2Value);
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
            s[0] = "constructLocale";
            s[1] = state_0 != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static ConstructorBuiltins.ConstructLocaleNode create(JSContext context, JSBuiltin builtin, boolean newTargetCase, JavaScriptNode[] arguments) {
            return new ConstructLocaleNodeGen(context, builtin, newTargetCase, arguments);
        }
    }

    @GeneratedBy(value=ConstructorBuiltins.ConstructDisplayNamesNode.class)
    public static final class ConstructDisplayNamesNodeGen
    extends ConstructorBuiltins.ConstructDisplayNamesNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @Node.Child
        private JavaScriptNode arguments2_;
        @CompilerDirectives.CompilationFinal
        private int state_0_;

        private ConstructDisplayNamesNodeGen(JSContext context, JSBuiltin builtin, boolean newTargetCase, JavaScriptNode[] arguments) {
            super(context, builtin, newTargetCase);
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
            if (state_0 != 0 && arguments0Value_ instanceof JSDynamicObject) {
                JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
                return this.constructDisplayNames(arguments0Value__, arguments1Value_, arguments2Value_);
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
            if (arguments0Value instanceof JSDynamicObject) {
                JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
                this.state_0_ = state_0 |= 1;
                return this.constructDisplayNames(arguments0Value_, arguments1Value, arguments2Value);
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
            s[0] = "constructDisplayNames";
            s[1] = state_0 != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static ConstructorBuiltins.ConstructDisplayNamesNode create(JSContext context, JSBuiltin builtin, boolean newTargetCase, JavaScriptNode[] arguments) {
            return new ConstructDisplayNamesNodeGen(context, builtin, newTargetCase, arguments);
        }
    }

    @GeneratedBy(value=ConstructorBuiltins.ConstructSegmenterNode.class)
    public static final class ConstructSegmenterNodeGen
    extends ConstructorBuiltins.ConstructSegmenterNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @Node.Child
        private JavaScriptNode arguments2_;
        @CompilerDirectives.CompilationFinal
        private int state_0_;

        private ConstructSegmenterNodeGen(JSContext context, JSBuiltin builtin, boolean newTargetCase, JavaScriptNode[] arguments) {
            super(context, builtin, newTargetCase);
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
            if (state_0 != 0 && arguments0Value_ instanceof JSDynamicObject) {
                JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
                return this.constructSegmenter(arguments0Value__, arguments1Value_, arguments2Value_);
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
            if (arguments0Value instanceof JSDynamicObject) {
                JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
                this.state_0_ = state_0 |= 1;
                return this.constructSegmenter(arguments0Value_, arguments1Value, arguments2Value);
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
            s[0] = "constructSegmenter";
            s[1] = state_0 != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static ConstructorBuiltins.ConstructSegmenterNode create(JSContext context, JSBuiltin builtin, boolean newTargetCase, JavaScriptNode[] arguments) {
            return new ConstructSegmenterNodeGen(context, builtin, newTargetCase, arguments);
        }
    }

    @GeneratedBy(value=ConstructorBuiltins.ConstructRelativeTimeFormatNode.class)
    public static final class ConstructRelativeTimeFormatNodeGen
    extends ConstructorBuiltins.ConstructRelativeTimeFormatNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @Node.Child
        private JavaScriptNode arguments2_;
        @CompilerDirectives.CompilationFinal
        private int state_0_;

        private ConstructRelativeTimeFormatNodeGen(JSContext context, JSBuiltin builtin, boolean newTargetCase, JavaScriptNode[] arguments) {
            super(context, builtin, newTargetCase);
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
            if (state_0 != 0 && arguments0Value_ instanceof JSDynamicObject) {
                JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
                return this.constructRelativeTimeFormat(arguments0Value__, arguments1Value_, arguments2Value_);
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
            if (arguments0Value instanceof JSDynamicObject) {
                JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
                this.state_0_ = state_0 |= 1;
                return this.constructRelativeTimeFormat(arguments0Value_, arguments1Value, arguments2Value);
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
            s[0] = "constructRelativeTimeFormat";
            s[1] = state_0 != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static ConstructorBuiltins.ConstructRelativeTimeFormatNode create(JSContext context, JSBuiltin builtin, boolean newTargetCase, JavaScriptNode[] arguments) {
            return new ConstructRelativeTimeFormatNodeGen(context, builtin, newTargetCase, arguments);
        }
    }

    @GeneratedBy(value=ConstructorBuiltins.ConstructListFormatNode.class)
    public static final class ConstructListFormatNodeGen
    extends ConstructorBuiltins.ConstructListFormatNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @Node.Child
        private JavaScriptNode arguments2_;
        @CompilerDirectives.CompilationFinal
        private int state_0_;

        private ConstructListFormatNodeGen(JSContext context, JSBuiltin builtin, boolean newTargetCase, JavaScriptNode[] arguments) {
            super(context, builtin, newTargetCase);
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
            if (state_0 != 0 && arguments0Value_ instanceof JSDynamicObject) {
                JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
                return this.constructListFormat(arguments0Value__, arguments1Value_, arguments2Value_);
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
            if (arguments0Value instanceof JSDynamicObject) {
                JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
                this.state_0_ = state_0 |= 1;
                return this.constructListFormat(arguments0Value_, arguments1Value, arguments2Value);
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
            s[0] = "constructListFormat";
            s[1] = state_0 != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static ConstructorBuiltins.ConstructListFormatNode create(JSContext context, JSBuiltin builtin, boolean newTargetCase, JavaScriptNode[] arguments) {
            return new ConstructListFormatNodeGen(context, builtin, newTargetCase, arguments);
        }
    }

    @GeneratedBy(value=ConstructorBuiltins.ConstructCollatorNode.class)
    public static final class ConstructCollatorNodeGen
    extends ConstructorBuiltins.ConstructCollatorNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @Node.Child
        private JavaScriptNode arguments2_;
        @CompilerDirectives.CompilationFinal
        private int state_0_;

        private ConstructCollatorNodeGen(JSContext context, JSBuiltin builtin, boolean newTargetCase, JavaScriptNode[] arguments) {
            super(context, builtin, newTargetCase);
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
            if (state_0 != 0 && arguments0Value_ instanceof JSDynamicObject) {
                JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
                return this.constructCollator(arguments0Value__, arguments1Value_, arguments2Value_);
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
            if (arguments0Value instanceof JSDynamicObject) {
                JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
                this.state_0_ = state_0 |= 1;
                return this.constructCollator(arguments0Value_, arguments1Value, arguments2Value);
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
            s[0] = "constructCollator";
            s[1] = state_0 != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static ConstructorBuiltins.ConstructCollatorNode create(JSContext context, JSBuiltin builtin, boolean newTargetCase, JavaScriptNode[] arguments) {
            return new ConstructCollatorNodeGen(context, builtin, newTargetCase, arguments);
        }
    }

    @GeneratedBy(value=ConstructorBuiltins.CallCollatorNode.class)
    public static final class CallCollatorNodeGen
    extends ConstructorBuiltins.CallCollatorNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;

        private CallCollatorNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            return this.callCollator(arguments0Value_, arguments1Value_);
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
            s[0] = "callCollator";
            s[1] = (byte)1;
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static ConstructorBuiltins.CallCollatorNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new CallCollatorNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=ConstructorBuiltins.ConstructFinalizationRegistryNode.class)
    public static final class ConstructFinalizationRegistryNodeGen
    extends ConstructorBuiltins.ConstructFinalizationRegistryNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @CompilerDirectives.CompilationFinal
        private int state_0_;

        private ConstructFinalizationRegistryNodeGen(JSContext context, JSBuiltin builtin, boolean newTargetCase, JavaScriptNode[] arguments) {
            super(context, builtin, newTargetCase);
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
                if ((state_0 & 1) != 0 && this.isCallableNode.executeBoolean(arguments1Value_)) {
                    return this.constructFinalizationRegistry(arguments0Value__, arguments1Value_);
                }
                if ((state_0 & 2) != 0 && !this.isCallableNode.executeBoolean(arguments1Value_)) {
                    return this.constructFinalizationRegistryNonObject(arguments0Value__, arguments1Value_);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.execute(frameValue);
        }

        private JSDynamicObject executeAndSpecialize(Object arguments0Value, Object arguments1Value) {
            int state_0 = this.state_0_;
            if (arguments0Value instanceof JSDynamicObject) {
                JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
                if (this.isCallableNode.executeBoolean(arguments1Value)) {
                    this.state_0_ = state_0 |= 1;
                    return this.constructFinalizationRegistry(arguments0Value_, arguments1Value);
                }
                if (!this.isCallableNode.executeBoolean(arguments1Value)) {
                    this.state_0_ = state_0 |= 2;
                    return this.constructFinalizationRegistryNonObject(arguments0Value_, arguments1Value);
                }
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
            Object[] data = new Object[3];
            data[0] = 0;
            int state_0 = this.state_0_;
            Object[] s = new Object[3];
            s[0] = "constructFinalizationRegistry";
            s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[1] = s;
            s = new Object[3];
            s[0] = "constructFinalizationRegistryNonObject";
            s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[2] = s;
            return Introspection.Provider.create(data);
        }

        public static ConstructorBuiltins.ConstructFinalizationRegistryNode create(JSContext context, JSBuiltin builtin, boolean newTargetCase, JavaScriptNode[] arguments) {
            return new ConstructFinalizationRegistryNodeGen(context, builtin, newTargetCase, arguments);
        }
    }

    @GeneratedBy(value=ConstructorBuiltins.ConstructWeakRefNode.class)
    public static final class ConstructWeakRefNodeGen
    extends ConstructorBuiltins.ConstructWeakRefNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @CompilerDirectives.CompilationFinal
        private int state_0_;

        private ConstructWeakRefNodeGen(JSContext context, JSBuiltin builtin, boolean newTargetCase, JavaScriptNode[] arguments) {
            super(context, builtin, newTargetCase);
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
                if ((state_0 & 1) != 0 && JSGuards.isJSObject(arguments1Value_)) {
                    return this.constructWeakRef(arguments0Value__, arguments1Value_);
                }
                if ((state_0 & 2) != 0 && !JSGuards.isJSObject(arguments1Value_)) {
                    return this.constructWeakRefNonObject(arguments0Value__, arguments1Value_);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.execute(frameValue);
        }

        private JSDynamicObject executeAndSpecialize(Object arguments0Value, Object arguments1Value) {
            int state_0 = this.state_0_;
            if (arguments0Value instanceof JSDynamicObject) {
                JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
                if (JSGuards.isJSObject(arguments1Value)) {
                    this.state_0_ = state_0 |= 1;
                    return this.constructWeakRef(arguments0Value_, arguments1Value);
                }
                if (!JSGuards.isJSObject(arguments1Value)) {
                    this.state_0_ = state_0 |= 2;
                    return this.constructWeakRefNonObject(arguments0Value_, arguments1Value);
                }
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
            Object[] data = new Object[3];
            data[0] = 0;
            int state_0 = this.state_0_;
            Object[] s = new Object[3];
            s[0] = "constructWeakRef";
            s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[1] = s;
            s = new Object[3];
            s[0] = "constructWeakRefNonObject";
            s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[2] = s;
            return Introspection.Provider.create(data);
        }

        public static ConstructorBuiltins.ConstructWeakRefNode create(JSContext context, JSBuiltin builtin, boolean newTargetCase, JavaScriptNode[] arguments) {
            return new ConstructWeakRefNodeGen(context, builtin, newTargetCase, arguments);
        }
    }

    @GeneratedBy(value=ConstructorBuiltins.ConstructStringNode.class)
    public static final class ConstructStringNodeGen
    extends ConstructorBuiltins.ConstructStringNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private JSToStringNode constructString_toStringNode_;

        private ConstructStringNodeGen(JSContext context, JSBuiltin builtin, boolean newTargetCase, JavaScriptNode[] arguments) {
            super(context, builtin, newTargetCase);
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
                if (arguments1Value_ instanceof Object[]) {
                    Object[] arguments1Value__ = (Object[])arguments1Value_;
                    if ((state_0 & 1) != 0 && arguments1Value__.length == 0) {
                        return this.constructStringInt0(arguments0Value__, arguments1Value__);
                    }
                    if ((state_0 & 2) != 0 && arguments1Value__.length != 0) {
                        return this.constructString(arguments0Value__, arguments1Value__, this.constructString_toStringNode_);
                    }
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.execute(frameValue);
        }

        private JSDynamicObject executeAndSpecialize(Object arguments0Value, Object arguments1Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                if (arguments0Value instanceof JSDynamicObject) {
                    JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
                    if (arguments1Value instanceof Object[]) {
                        Object[] arguments1Value_ = (Object[])arguments1Value;
                        if (arguments1Value_.length == 0) {
                            this.state_0_ = state_0 |= 1;
                            lock.unlock();
                            hasLock = false;
                            JSDynamicObject jSDynamicObject = this.constructStringInt0(arguments0Value_, arguments1Value_);
                            return jSDynamicObject;
                        }
                        if (arguments1Value_.length != 0) {
                            this.constructString_toStringNode_ = super.insert(JSToStringNode.create());
                            this.state_0_ = state_0 |= 2;
                            lock.unlock();
                            hasLock = false;
                            JSDynamicObject jSDynamicObject = this.constructString(arguments0Value_, arguments1Value_, this.constructString_toStringNode_);
                            return jSDynamicObject;
                        }
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
            s[0] = "constructStringInt0";
            s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[1] = s;
            s = new Object[3];
            s[0] = "constructString";
            if ((state_0 & 2) != 0) {
                s[1] = (byte)1;
                ArrayList<List<JSToStringNode>> cached = new ArrayList<List<JSToStringNode>>();
                cached.add(Arrays.asList(this.constructString_toStringNode_));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[2] = s;
            return Introspection.Provider.create(data);
        }

        public static ConstructorBuiltins.ConstructStringNode create(JSContext context, JSBuiltin builtin, boolean newTargetCase, JavaScriptNode[] arguments) {
            return new ConstructStringNodeGen(context, builtin, newTargetCase, arguments);
        }
    }

    @GeneratedBy(value=ConstructorBuiltins.CallStringNode.class)
    public static final class CallStringNodeGen
    extends ConstructorBuiltins.CallStringNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private JSToStringNode callStringGeneric_toStringNode_;

        private CallStringNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            if (state_0 != 0 && arguments0Value_ instanceof Object[]) {
                Object[] arguments0Value__ = (Object[])arguments0Value_;
                if ((state_0 & 1) != 0 && arguments0Value__.length == 0) {
                    return this.callStringInt0(arguments0Value__);
                }
                if ((state_0 & 2) != 0 && arguments0Value__.length != 0) {
                    return this.callStringGeneric(arguments0Value__, this.callStringGeneric_toStringNode_);
                }
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
                int state_0 = this.state_0_;
                if (arguments0Value instanceof Object[]) {
                    Object[] arguments0Value_ = (Object[])arguments0Value;
                    if (arguments0Value_.length == 0) {
                        this.state_0_ = state_0 |= 1;
                        lock.unlock();
                        hasLock = false;
                        Object object = this.callStringInt0(arguments0Value_);
                        return object;
                    }
                    if (arguments0Value_.length != 0) {
                        this.callStringGeneric_toStringNode_ = super.insert(JSToStringNode.createSymbolToString());
                        this.state_0_ = state_0 |= 2;
                        lock.unlock();
                        hasLock = false;
                        Object object = this.callStringGeneric(arguments0Value_, this.callStringGeneric_toStringNode_);
                        return object;
                    }
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
            s[0] = "callStringInt0";
            s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[1] = s;
            s = new Object[3];
            s[0] = "callStringGeneric";
            if ((state_0 & 2) != 0) {
                s[1] = (byte)1;
                ArrayList<List<JSToStringNode>> cached = new ArrayList<List<JSToStringNode>>();
                cached.add(Arrays.asList(this.callStringGeneric_toStringNode_));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[2] = s;
            return Introspection.Provider.create(data);
        }

        public static ConstructorBuiltins.CallStringNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new CallStringNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=ConstructorBuiltins.ConstructRegExpNode.class)
    public static final class ConstructRegExpNodeGen
    extends ConstructorBuiltins.ConstructRegExpNode
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
        private IsRegExpNode isRegExpNode_;

        private ConstructRegExpNodeGen(JSContext context, JSBuiltin builtin, boolean isCall, boolean isNewTargetCase, JavaScriptNode[] arguments) {
            super(context, builtin, isCall, isNewTargetCase);
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
            if (state_0 != 0 && arguments0Value_ instanceof JSDynamicObject) {
                JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
                return this.constructRegExp(arguments0Value__, arguments1Value_, arguments2Value_, this.isRegExpNode_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_, arguments2Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.execute(frameValue);
        }

        private JSDynamicObject executeAndSpecialize(Object arguments0Value, Object arguments1Value, Object arguments2Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                if (arguments0Value instanceof JSDynamicObject) {
                    JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
                    this.isRegExpNode_ = super.insert(IsRegExpNode.create(this.getContext()));
                    this.state_0_ = state_0 |= 1;
                    lock.unlock();
                    hasLock = false;
                    JSDynamicObject jSDynamicObject = this.constructRegExp(arguments0Value_, arguments1Value, arguments2Value, this.isRegExpNode_);
                    return jSDynamicObject;
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
            return NodeCost.MONOMORPHIC;
        }

        @Override
        public Introspection getIntrospectionData() {
            Object[] data = new Object[2];
            data[0] = 0;
            int state_0 = this.state_0_;
            Object[] s = new Object[3];
            s[0] = "constructRegExp";
            if (state_0 != 0) {
                s[1] = (byte)1;
                ArrayList<List<IsRegExpNode>> cached = new ArrayList<List<IsRegExpNode>>();
                cached.add(Arrays.asList(this.isRegExpNode_));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static ConstructorBuiltins.ConstructRegExpNode create(JSContext context, JSBuiltin builtin, boolean isCall, boolean isNewTargetCase, JavaScriptNode[] arguments) {
            return new ConstructRegExpNodeGen(context, builtin, isCall, isNewTargetCase, arguments);
        }
    }

    @GeneratedBy(value=ConstructorBuiltins.ConstructTemporalZonedDateTime.class)
    public static final class ConstructTemporalZonedDateTimeNodeGen
    extends ConstructorBuiltins.ConstructTemporalZonedDateTime
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
        private ConstructTemporalZonedDateTimeData constructTemporalZonedDateTime_cache;

        private ConstructTemporalZonedDateTimeNodeGen(JSContext context, JSBuiltin builtin, boolean isNewTargetCase, JavaScriptNode[] arguments) {
            super(context, builtin, isNewTargetCase);
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
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            Object arguments2Value_ = this.arguments2_.execute(frameValue);
            Object arguments3Value_ = this.arguments3_.execute(frameValue);
            if (state_0 != 0 && arguments0Value_ instanceof JSDynamicObject) {
                JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
                ConstructTemporalZonedDateTimeData s0_ = this.constructTemporalZonedDateTime_cache;
                if (s0_ != null) {
                    return this.constructTemporalZonedDateTime(arguments0Value__, arguments1Value_, arguments2Value_, arguments3Value_, s0_.toTemporalTimeZone_, s0_.toTemporalCalendarWithISODefaultNode_, s0_.toBigIntNode_, s0_.errorBranch_);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_, arguments2Value_, arguments3Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.execute(frameValue);
        }

        private JSDynamicObject executeAndSpecialize(Object arguments0Value, Object arguments1Value, Object arguments2Value, Object arguments3Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                if (arguments0Value instanceof JSDynamicObject) {
                    JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
                    ConstructTemporalZonedDateTimeData s0_ = super.insert(new ConstructTemporalZonedDateTimeData());
                    s0_.toTemporalTimeZone_ = s0_.insertAccessor(ToTemporalTimeZoneNode.create(this.getContext()));
                    s0_.toTemporalCalendarWithISODefaultNode_ = s0_.insertAccessor(ToTemporalCalendarWithISODefaultNode.create(this.getContext()));
                    s0_.toBigIntNode_ = s0_.insertAccessor(JSToBigIntNode.create());
                    s0_.errorBranch_ = BranchProfile.create();
                    VarHandle.storeStoreFence();
                    this.constructTemporalZonedDateTime_cache = s0_;
                    this.state_0_ = state_0 |= 1;
                    lock.unlock();
                    hasLock = false;
                    JSDynamicObject jSDynamicObject = this.constructTemporalZonedDateTime(arguments0Value_, arguments1Value, arguments2Value, arguments3Value, s0_.toTemporalTimeZone_, s0_.toTemporalCalendarWithISODefaultNode_, s0_.toBigIntNode_, s0_.errorBranch_);
                    return jSDynamicObject;
                }
                throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_, this.arguments1_, this.arguments2_, this.arguments3_}, arguments0Value, arguments1Value, arguments2Value, arguments3Value);
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
            s[0] = "constructTemporalZonedDateTime";
            if (state_0 != 0) {
                s[1] = (byte)1;
                ArrayList<List<Cloneable>> cached = new ArrayList<List<Cloneable>>();
                ConstructTemporalZonedDateTimeData s0_ = this.constructTemporalZonedDateTime_cache;
                if (s0_ != null) {
                    cached.add(Arrays.asList(s0_.toTemporalTimeZone_, s0_.toTemporalCalendarWithISODefaultNode_, s0_.toBigIntNode_, s0_.errorBranch_));
                }
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static ConstructorBuiltins.ConstructTemporalZonedDateTime create(JSContext context, JSBuiltin builtin, boolean isNewTargetCase, JavaScriptNode[] arguments) {
            return new ConstructTemporalZonedDateTimeNodeGen(context, builtin, isNewTargetCase, arguments);
        }

        @GeneratedBy(value=ConstructorBuiltins.ConstructTemporalZonedDateTime.class)
        private static final class ConstructTemporalZonedDateTimeData
        extends Node {
            @Node.Child
            ToTemporalTimeZoneNode toTemporalTimeZone_;
            @Node.Child
            ToTemporalCalendarWithISODefaultNode toTemporalCalendarWithISODefaultNode_;
            @Node.Child
            JSToBigIntNode toBigIntNode_;
            @CompilerDirectives.CompilationFinal
            BranchProfile errorBranch_;

            ConstructTemporalZonedDateTimeData() {
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

    @GeneratedBy(value=ConstructorBuiltins.ConstructTemporalTimeZone.class)
    public static final class ConstructTemporalTimeZoneNodeGen
    extends ConstructorBuiltins.ConstructTemporalTimeZone
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private JSToStringNode toStringNode_;

        private ConstructTemporalTimeZoneNodeGen(JSContext context, JSBuiltin builtin, boolean isNewTargetCase, JavaScriptNode[] arguments) {
            super(context, builtin, isNewTargetCase);
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
                return this.constructTemporalTimeZone(arguments0Value__, arguments1Value_, this.toStringNode_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.execute(frameValue);
        }

        private JSDynamicObject executeAndSpecialize(Object arguments0Value, Object arguments1Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                if (arguments0Value instanceof JSDynamicObject) {
                    JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
                    this.toStringNode_ = super.insert(JSToStringNode.create());
                    this.state_0_ = state_0 |= 1;
                    lock.unlock();
                    hasLock = false;
                    JSDynamicObject jSDynamicObject = this.constructTemporalTimeZone(arguments0Value_, arguments1Value, this.toStringNode_);
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
            s[0] = "constructTemporalTimeZone";
            if (state_0 != 0) {
                s[1] = (byte)1;
                ArrayList<List<JSToStringNode>> cached = new ArrayList<List<JSToStringNode>>();
                cached.add(Arrays.asList(this.toStringNode_));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static ConstructorBuiltins.ConstructTemporalTimeZone create(JSContext context, JSBuiltin builtin, boolean isNewTargetCase, JavaScriptNode[] arguments) {
            return new ConstructTemporalTimeZoneNodeGen(context, builtin, isNewTargetCase, arguments);
        }
    }

    @GeneratedBy(value=ConstructorBuiltins.ConstructTemporalInstant.class)
    public static final class ConstructTemporalInstantNodeGen
    extends ConstructorBuiltins.ConstructTemporalInstant
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @CompilerDirectives.CompilationFinal
        private BranchProfile errorBranch_;

        private ConstructTemporalInstantNodeGen(JSContext context, JSBuiltin builtin, boolean isNewTargetCase, JavaScriptNode[] arguments) {
            super(context, builtin, isNewTargetCase);
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
                return this.constructTemporalInstant(arguments0Value__, arguments1Value_, this.errorBranch_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.execute(frameValue);
        }

        private JSDynamicObject executeAndSpecialize(Object arguments0Value, Object arguments1Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                if (arguments0Value instanceof JSDynamicObject) {
                    JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
                    this.errorBranch_ = BranchProfile.create();
                    this.state_0_ = state_0 |= 1;
                    lock.unlock();
                    hasLock = false;
                    JSDynamicObject jSDynamicObject = this.constructTemporalInstant(arguments0Value_, arguments1Value, this.errorBranch_);
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
            s[0] = "constructTemporalInstant";
            if (state_0 != 0) {
                s[1] = (byte)1;
                ArrayList<List<BranchProfile>> cached = new ArrayList<List<BranchProfile>>();
                cached.add(Arrays.asList(this.errorBranch_));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static ConstructorBuiltins.ConstructTemporalInstant create(JSContext context, JSBuiltin builtin, boolean isNewTargetCase, JavaScriptNode[] arguments) {
            return new ConstructTemporalInstantNodeGen(context, builtin, isNewTargetCase, arguments);
        }
    }

    @GeneratedBy(value=ConstructorBuiltins.ConstructTemporalPlainMonthDay.class)
    public static final class ConstructTemporalPlainMonthDayNodeGen
    extends ConstructorBuiltins.ConstructTemporalPlainMonthDay
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @Node.Child
        private JavaScriptNode arguments2_;
        @Node.Child
        private JavaScriptNode arguments3_;
        @Node.Child
        private JavaScriptNode arguments4_;
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private ConstructTemporalPlainMonthDayData constructTemporalPlainMonthDay_cache;

        private ConstructTemporalPlainMonthDayNodeGen(JSContext context, JSBuiltin builtin, boolean isNewTargetCase, JavaScriptNode[] arguments) {
            super(context, builtin, isNewTargetCase);
            this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
            this.arguments1_ = arguments != null && 1 < arguments.length ? arguments[1] : null;
            this.arguments2_ = arguments != null && 2 < arguments.length ? arguments[2] : null;
            this.arguments3_ = arguments != null && 3 < arguments.length ? arguments[3] : null;
            this.arguments4_ = arguments != null && 4 < arguments.length ? arguments[4] : null;
        }

        @Override
        public JavaScriptNode[] getArguments() {
            return new JavaScriptNode[]{this.arguments0_, this.arguments1_, this.arguments2_, this.arguments3_, this.arguments4_};
        }

        @Override
        public Object execute(VirtualFrame frameValue) {
            int state_0 = this.state_0_;
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            Object arguments2Value_ = this.arguments2_.execute(frameValue);
            Object arguments3Value_ = this.arguments3_.execute(frameValue);
            Object arguments4Value_ = this.arguments4_.execute(frameValue);
            if (state_0 != 0 && arguments0Value_ instanceof JSDynamicObject) {
                JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
                ConstructTemporalPlainMonthDayData s0_ = this.constructTemporalPlainMonthDay_cache;
                if (s0_ != null) {
                    return this.constructTemporalPlainMonthDay(arguments0Value__, arguments1Value_, arguments2Value_, arguments3Value_, arguments4Value_, s0_.errorBranch_, s0_.toInt_, s0_.toTemporalCalendarWithISODefaultNode_);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_, arguments2Value_, arguments3Value_, arguments4Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.execute(frameValue);
        }

        private JSDynamicObject executeAndSpecialize(Object arguments0Value, Object arguments1Value, Object arguments2Value, Object arguments3Value, Object arguments4Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                if (arguments0Value instanceof JSDynamicObject) {
                    JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
                    ConstructTemporalPlainMonthDayData s0_ = super.insert(new ConstructTemporalPlainMonthDayData());
                    s0_.errorBranch_ = BranchProfile.create();
                    s0_.toInt_ = s0_.insertAccessor(JSToIntegerThrowOnInfinityNode.create());
                    s0_.toTemporalCalendarWithISODefaultNode_ = s0_.insertAccessor(ToTemporalCalendarWithISODefaultNode.create(this.getContext()));
                    VarHandle.storeStoreFence();
                    this.constructTemporalPlainMonthDay_cache = s0_;
                    this.state_0_ = state_0 |= 1;
                    lock.unlock();
                    hasLock = false;
                    JSDynamicObject jSDynamicObject = this.constructTemporalPlainMonthDay(arguments0Value_, arguments1Value, arguments2Value, arguments3Value, arguments4Value, s0_.errorBranch_, s0_.toInt_, s0_.toTemporalCalendarWithISODefaultNode_);
                    return jSDynamicObject;
                }
                throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_, this.arguments1_, this.arguments2_, this.arguments3_, this.arguments4_}, arguments0Value, arguments1Value, arguments2Value, arguments3Value, arguments4Value);
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
            s[0] = "constructTemporalPlainMonthDay";
            if (state_0 != 0) {
                s[1] = (byte)1;
                ArrayList<List<Cloneable>> cached = new ArrayList<List<Cloneable>>();
                ConstructTemporalPlainMonthDayData s0_ = this.constructTemporalPlainMonthDay_cache;
                if (s0_ != null) {
                    cached.add(Arrays.asList(s0_.errorBranch_, s0_.toInt_, s0_.toTemporalCalendarWithISODefaultNode_));
                }
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static ConstructorBuiltins.ConstructTemporalPlainMonthDay create(JSContext context, JSBuiltin builtin, boolean isNewTargetCase, JavaScriptNode[] arguments) {
            return new ConstructTemporalPlainMonthDayNodeGen(context, builtin, isNewTargetCase, arguments);
        }

        @GeneratedBy(value=ConstructorBuiltins.ConstructTemporalPlainMonthDay.class)
        private static final class ConstructTemporalPlainMonthDayData
        extends Node {
            @CompilerDirectives.CompilationFinal
            BranchProfile errorBranch_;
            @Node.Child
            JSToIntegerThrowOnInfinityNode toInt_;
            @Node.Child
            ToTemporalCalendarWithISODefaultNode toTemporalCalendarWithISODefaultNode_;

            ConstructTemporalPlainMonthDayData() {
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

    @GeneratedBy(value=ConstructorBuiltins.ConstructTemporalPlainYearMonth.class)
    public static final class ConstructTemporalPlainYearMonthNodeGen
    extends ConstructorBuiltins.ConstructTemporalPlainYearMonth
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @Node.Child
        private JavaScriptNode arguments2_;
        @Node.Child
        private JavaScriptNode arguments3_;
        @Node.Child
        private JavaScriptNode arguments4_;
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private ConstructTemporalPlainYearMonthData constructTemporalPlainYearMonth_cache;

        private ConstructTemporalPlainYearMonthNodeGen(JSContext context, JSBuiltin builtin, boolean isNewTargetCase, JavaScriptNode[] arguments) {
            super(context, builtin, isNewTargetCase);
            this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
            this.arguments1_ = arguments != null && 1 < arguments.length ? arguments[1] : null;
            this.arguments2_ = arguments != null && 2 < arguments.length ? arguments[2] : null;
            this.arguments3_ = arguments != null && 3 < arguments.length ? arguments[3] : null;
            this.arguments4_ = arguments != null && 4 < arguments.length ? arguments[4] : null;
        }

        @Override
        public JavaScriptNode[] getArguments() {
            return new JavaScriptNode[]{this.arguments0_, this.arguments1_, this.arguments2_, this.arguments3_, this.arguments4_};
        }

        @Override
        public Object execute(VirtualFrame frameValue) {
            int state_0 = this.state_0_;
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            Object arguments2Value_ = this.arguments2_.execute(frameValue);
            Object arguments3Value_ = this.arguments3_.execute(frameValue);
            Object arguments4Value_ = this.arguments4_.execute(frameValue);
            if (state_0 != 0 && arguments0Value_ instanceof JSDynamicObject) {
                JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
                ConstructTemporalPlainYearMonthData s0_ = this.constructTemporalPlainYearMonth_cache;
                if (s0_ != null) {
                    return this.constructTemporalPlainYearMonth(arguments0Value__, arguments1Value_, arguments2Value_, arguments3Value_, arguments4Value_, s0_.errorBranch_, s0_.toInteger_, s0_.toTemporalCalendarWithISODefaultNode_);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_, arguments2Value_, arguments3Value_, arguments4Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.execute(frameValue);
        }

        private JSDynamicObject executeAndSpecialize(Object arguments0Value, Object arguments1Value, Object arguments2Value, Object arguments3Value, Object arguments4Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                if (arguments0Value instanceof JSDynamicObject) {
                    JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
                    ConstructTemporalPlainYearMonthData s0_ = super.insert(new ConstructTemporalPlainYearMonthData());
                    s0_.errorBranch_ = BranchProfile.create();
                    s0_.toInteger_ = s0_.insertAccessor(JSToIntegerThrowOnInfinityNode.create());
                    s0_.toTemporalCalendarWithISODefaultNode_ = s0_.insertAccessor(ToTemporalCalendarWithISODefaultNode.create(this.getContext()));
                    VarHandle.storeStoreFence();
                    this.constructTemporalPlainYearMonth_cache = s0_;
                    this.state_0_ = state_0 |= 1;
                    lock.unlock();
                    hasLock = false;
                    JSDynamicObject jSDynamicObject = this.constructTemporalPlainYearMonth(arguments0Value_, arguments1Value, arguments2Value, arguments3Value, arguments4Value, s0_.errorBranch_, s0_.toInteger_, s0_.toTemporalCalendarWithISODefaultNode_);
                    return jSDynamicObject;
                }
                throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_, this.arguments1_, this.arguments2_, this.arguments3_, this.arguments4_}, arguments0Value, arguments1Value, arguments2Value, arguments3Value, arguments4Value);
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
            s[0] = "constructTemporalPlainYearMonth";
            if (state_0 != 0) {
                s[1] = (byte)1;
                ArrayList<List<Cloneable>> cached = new ArrayList<List<Cloneable>>();
                ConstructTemporalPlainYearMonthData s0_ = this.constructTemporalPlainYearMonth_cache;
                if (s0_ != null) {
                    cached.add(Arrays.asList(s0_.errorBranch_, s0_.toInteger_, s0_.toTemporalCalendarWithISODefaultNode_));
                }
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static ConstructorBuiltins.ConstructTemporalPlainYearMonth create(JSContext context, JSBuiltin builtin, boolean isNewTargetCase, JavaScriptNode[] arguments) {
            return new ConstructTemporalPlainYearMonthNodeGen(context, builtin, isNewTargetCase, arguments);
        }

        @GeneratedBy(value=ConstructorBuiltins.ConstructTemporalPlainYearMonth.class)
        private static final class ConstructTemporalPlainYearMonthData
        extends Node {
            @CompilerDirectives.CompilationFinal
            BranchProfile errorBranch_;
            @Node.Child
            JSToIntegerThrowOnInfinityNode toInteger_;
            @Node.Child
            ToTemporalCalendarWithISODefaultNode toTemporalCalendarWithISODefaultNode_;

            ConstructTemporalPlainYearMonthData() {
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

    @GeneratedBy(value=ConstructorBuiltins.ConstructTemporalCalendar.class)
    public static final class ConstructTemporalCalendarNodeGen
    extends ConstructorBuiltins.ConstructTemporalCalendar
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @CompilerDirectives.CompilationFinal
        private BranchProfile errorBranch_;
        @Node.Child
        private JSToStringNode toString_;

        private ConstructTemporalCalendarNodeGen(JSContext context, JSBuiltin builtin, boolean isNewTargetCase, JavaScriptNode[] arguments) {
            super(context, builtin, isNewTargetCase);
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
                return this.constructTemporalCalendar(arguments0Value__, arguments1Value_, this.errorBranch_, this.toString_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.execute(frameValue);
        }

        private JSDynamicObject executeAndSpecialize(Object arguments0Value, Object arguments1Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                if (arguments0Value instanceof JSDynamicObject) {
                    JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
                    this.errorBranch_ = BranchProfile.create();
                    this.toString_ = super.insert(JSToStringNode.create());
                    this.state_0_ = state_0 |= 1;
                    lock.unlock();
                    hasLock = false;
                    JSDynamicObject jSDynamicObject = this.constructTemporalCalendar(arguments0Value_, arguments1Value, this.errorBranch_, this.toString_);
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
            s[0] = "constructTemporalCalendar";
            if (state_0 != 0) {
                s[1] = (byte)1;
                ArrayList<List<Cloneable>> cached = new ArrayList<List<Cloneable>>();
                cached.add(Arrays.asList(this.errorBranch_, this.toString_));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static ConstructorBuiltins.ConstructTemporalCalendar create(JSContext context, JSBuiltin builtin, boolean isNewTargetCase, JavaScriptNode[] arguments) {
            return new ConstructTemporalCalendarNodeGen(context, builtin, isNewTargetCase, arguments);
        }
    }

    @GeneratedBy(value=ConstructorBuiltins.ConstructTemporalDurationNode.class)
    public static final class ConstructTemporalDurationNodeGen
    extends ConstructorBuiltins.ConstructTemporalDurationNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @Node.Child
        private JavaScriptNode arguments2_;
        @Node.Child
        private JavaScriptNode arguments3_;
        @Node.Child
        private JavaScriptNode arguments4_;
        @Node.Child
        private JavaScriptNode arguments5_;
        @Node.Child
        private JavaScriptNode arguments6_;
        @Node.Child
        private JavaScriptNode arguments7_;
        @Node.Child
        private JavaScriptNode arguments8_;
        @Node.Child
        private JavaScriptNode arguments9_;
        @Node.Child
        private JavaScriptNode arguments10_;
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private JSToIntegerWithoutRoundingNode toIntegerNode_;
        @CompilerDirectives.CompilationFinal
        private BranchProfile errorBranch_;

        private ConstructTemporalDurationNodeGen(JSContext context, JSBuiltin builtin, boolean isNewTargetCase, JavaScriptNode[] arguments) {
            super(context, builtin, isNewTargetCase);
            this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
            this.arguments1_ = arguments != null && 1 < arguments.length ? arguments[1] : null;
            this.arguments2_ = arguments != null && 2 < arguments.length ? arguments[2] : null;
            this.arguments3_ = arguments != null && 3 < arguments.length ? arguments[3] : null;
            this.arguments4_ = arguments != null && 4 < arguments.length ? arguments[4] : null;
            this.arguments5_ = arguments != null && 5 < arguments.length ? arguments[5] : null;
            this.arguments6_ = arguments != null && 6 < arguments.length ? arguments[6] : null;
            this.arguments7_ = arguments != null && 7 < arguments.length ? arguments[7] : null;
            this.arguments8_ = arguments != null && 8 < arguments.length ? arguments[8] : null;
            this.arguments9_ = arguments != null && 9 < arguments.length ? arguments[9] : null;
            this.arguments10_ = arguments != null && 10 < arguments.length ? arguments[10] : null;
        }

        @Override
        public JavaScriptNode[] getArguments() {
            return new JavaScriptNode[]{this.arguments0_, this.arguments1_, this.arguments2_, this.arguments3_, this.arguments4_, this.arguments5_, this.arguments6_, this.arguments7_, this.arguments8_, this.arguments9_, this.arguments10_};
        }

        @Override
        public Object execute(VirtualFrame frameValue) {
            int state_0 = this.state_0_;
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            Object arguments2Value_ = this.arguments2_.execute(frameValue);
            Object arguments3Value_ = this.arguments3_.execute(frameValue);
            Object arguments4Value_ = this.arguments4_.execute(frameValue);
            Object arguments5Value_ = this.arguments5_.execute(frameValue);
            Object arguments6Value_ = this.arguments6_.execute(frameValue);
            Object arguments7Value_ = this.arguments7_.execute(frameValue);
            Object arguments8Value_ = this.arguments8_.execute(frameValue);
            Object arguments9Value_ = this.arguments9_.execute(frameValue);
            Object arguments10Value_ = this.arguments10_.execute(frameValue);
            if (state_0 != 0 && arguments0Value_ instanceof JSDynamicObject) {
                JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
                return this.constructTemporalDuration(arguments0Value__, arguments1Value_, arguments2Value_, arguments3Value_, arguments4Value_, arguments5Value_, arguments6Value_, arguments7Value_, arguments8Value_, arguments9Value_, arguments10Value_, this.toIntegerNode_, this.errorBranch_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_, arguments2Value_, arguments3Value_, arguments4Value_, arguments5Value_, arguments6Value_, arguments7Value_, arguments8Value_, arguments9Value_, arguments10Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.execute(frameValue);
        }

        private JSDynamicObject executeAndSpecialize(Object arguments0Value, Object arguments1Value, Object arguments2Value, Object arguments3Value, Object arguments4Value, Object arguments5Value, Object arguments6Value, Object arguments7Value, Object arguments8Value, Object arguments9Value, Object arguments10Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                if (arguments0Value instanceof JSDynamicObject) {
                    JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
                    this.toIntegerNode_ = super.insert(JSToIntegerWithoutRoundingNode.create());
                    this.errorBranch_ = BranchProfile.create();
                    this.state_0_ = state_0 |= 1;
                    lock.unlock();
                    hasLock = false;
                    JSDynamicObject jSDynamicObject = this.constructTemporalDuration(arguments0Value_, arguments1Value, arguments2Value, arguments3Value, arguments4Value, arguments5Value, arguments6Value, arguments7Value, arguments8Value, arguments9Value, arguments10Value, this.toIntegerNode_, this.errorBranch_);
                    return jSDynamicObject;
                }
                throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_, this.arguments1_, this.arguments2_, this.arguments3_, this.arguments4_, this.arguments5_, this.arguments6_, this.arguments7_, this.arguments8_, this.arguments9_, this.arguments10_}, arguments0Value, arguments1Value, arguments2Value, arguments3Value, arguments4Value, arguments5Value, arguments6Value, arguments7Value, arguments8Value, arguments9Value, arguments10Value);
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
            s[0] = "constructTemporalDuration";
            if (state_0 != 0) {
                s[1] = (byte)1;
                ArrayList<List<Cloneable>> cached = new ArrayList<List<Cloneable>>();
                cached.add(Arrays.asList(this.toIntegerNode_, this.errorBranch_));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static ConstructorBuiltins.ConstructTemporalDurationNode create(JSContext context, JSBuiltin builtin, boolean isNewTargetCase, JavaScriptNode[] arguments) {
            return new ConstructTemporalDurationNodeGen(context, builtin, isNewTargetCase, arguments);
        }
    }

    @GeneratedBy(value=ConstructorBuiltins.ConstructTemporalPlainDateTimeNode.class)
    public static final class ConstructTemporalPlainDateTimeNodeGen
    extends ConstructorBuiltins.ConstructTemporalPlainDateTimeNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @Node.Child
        private JavaScriptNode arguments2_;
        @Node.Child
        private JavaScriptNode arguments3_;
        @Node.Child
        private JavaScriptNode arguments4_;
        @Node.Child
        private JavaScriptNode arguments5_;
        @Node.Child
        private JavaScriptNode arguments6_;
        @Node.Child
        private JavaScriptNode arguments7_;
        @Node.Child
        private JavaScriptNode arguments8_;
        @Node.Child
        private JavaScriptNode arguments9_;
        @Node.Child
        private JavaScriptNode arguments10_;
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private ConstructTemporalPlainDateTimeData constructTemporalPlainDateTime_cache;

        private ConstructTemporalPlainDateTimeNodeGen(JSContext context, JSBuiltin builtin, boolean isNewTargetCase, JavaScriptNode[] arguments) {
            super(context, builtin, isNewTargetCase);
            this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
            this.arguments1_ = arguments != null && 1 < arguments.length ? arguments[1] : null;
            this.arguments2_ = arguments != null && 2 < arguments.length ? arguments[2] : null;
            this.arguments3_ = arguments != null && 3 < arguments.length ? arguments[3] : null;
            this.arguments4_ = arguments != null && 4 < arguments.length ? arguments[4] : null;
            this.arguments5_ = arguments != null && 5 < arguments.length ? arguments[5] : null;
            this.arguments6_ = arguments != null && 6 < arguments.length ? arguments[6] : null;
            this.arguments7_ = arguments != null && 7 < arguments.length ? arguments[7] : null;
            this.arguments8_ = arguments != null && 8 < arguments.length ? arguments[8] : null;
            this.arguments9_ = arguments != null && 9 < arguments.length ? arguments[9] : null;
            this.arguments10_ = arguments != null && 10 < arguments.length ? arguments[10] : null;
        }

        @Override
        public JavaScriptNode[] getArguments() {
            return new JavaScriptNode[]{this.arguments0_, this.arguments1_, this.arguments2_, this.arguments3_, this.arguments4_, this.arguments5_, this.arguments6_, this.arguments7_, this.arguments8_, this.arguments9_, this.arguments10_};
        }

        @Override
        public Object execute(VirtualFrame frameValue) {
            int state_0 = this.state_0_;
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            Object arguments2Value_ = this.arguments2_.execute(frameValue);
            Object arguments3Value_ = this.arguments3_.execute(frameValue);
            Object arguments4Value_ = this.arguments4_.execute(frameValue);
            Object arguments5Value_ = this.arguments5_.execute(frameValue);
            Object arguments6Value_ = this.arguments6_.execute(frameValue);
            Object arguments7Value_ = this.arguments7_.execute(frameValue);
            Object arguments8Value_ = this.arguments8_.execute(frameValue);
            Object arguments9Value_ = this.arguments9_.execute(frameValue);
            Object arguments10Value_ = this.arguments10_.execute(frameValue);
            if (state_0 != 0 && arguments0Value_ instanceof JSDynamicObject) {
                JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
                ConstructTemporalPlainDateTimeData s0_ = this.constructTemporalPlainDateTime_cache;
                if (s0_ != null) {
                    return this.constructTemporalPlainDateTime(arguments0Value__, arguments1Value_, arguments2Value_, arguments3Value_, arguments4Value_, arguments5Value_, arguments6Value_, arguments7Value_, arguments8Value_, arguments9Value_, arguments10Value_, s0_.toIntegerNode_, s0_.toTemporalCalendarWithISODefaultNode_, s0_.errorBranch_);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_, arguments2Value_, arguments3Value_, arguments4Value_, arguments5Value_, arguments6Value_, arguments7Value_, arguments8Value_, arguments9Value_, arguments10Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.execute(frameValue);
        }

        private JSDynamicObject executeAndSpecialize(Object arguments0Value, Object arguments1Value, Object arguments2Value, Object arguments3Value, Object arguments4Value, Object arguments5Value, Object arguments6Value, Object arguments7Value, Object arguments8Value, Object arguments9Value, Object arguments10Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                if (arguments0Value instanceof JSDynamicObject) {
                    JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
                    ConstructTemporalPlainDateTimeData s0_ = super.insert(new ConstructTemporalPlainDateTimeData());
                    s0_.toIntegerNode_ = s0_.insertAccessor(JSToIntegerThrowOnInfinityNode.create());
                    s0_.toTemporalCalendarWithISODefaultNode_ = s0_.insertAccessor(ToTemporalCalendarWithISODefaultNode.create(this.getContext()));
                    s0_.errorBranch_ = BranchProfile.create();
                    VarHandle.storeStoreFence();
                    this.constructTemporalPlainDateTime_cache = s0_;
                    this.state_0_ = state_0 |= 1;
                    lock.unlock();
                    hasLock = false;
                    JSDynamicObject jSDynamicObject = this.constructTemporalPlainDateTime(arguments0Value_, arguments1Value, arguments2Value, arguments3Value, arguments4Value, arguments5Value, arguments6Value, arguments7Value, arguments8Value, arguments9Value, arguments10Value, s0_.toIntegerNode_, s0_.toTemporalCalendarWithISODefaultNode_, s0_.errorBranch_);
                    return jSDynamicObject;
                }
                throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_, this.arguments1_, this.arguments2_, this.arguments3_, this.arguments4_, this.arguments5_, this.arguments6_, this.arguments7_, this.arguments8_, this.arguments9_, this.arguments10_}, arguments0Value, arguments1Value, arguments2Value, arguments3Value, arguments4Value, arguments5Value, arguments6Value, arguments7Value, arguments8Value, arguments9Value, arguments10Value);
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
            s[0] = "constructTemporalPlainDateTime";
            if (state_0 != 0) {
                s[1] = (byte)1;
                ArrayList<List<Cloneable>> cached = new ArrayList<List<Cloneable>>();
                ConstructTemporalPlainDateTimeData s0_ = this.constructTemporalPlainDateTime_cache;
                if (s0_ != null) {
                    cached.add(Arrays.asList(s0_.toIntegerNode_, s0_.toTemporalCalendarWithISODefaultNode_, s0_.errorBranch_));
                }
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static ConstructorBuiltins.ConstructTemporalPlainDateTimeNode create(JSContext context, JSBuiltin builtin, boolean isNewTargetCase, JavaScriptNode[] arguments) {
            return new ConstructTemporalPlainDateTimeNodeGen(context, builtin, isNewTargetCase, arguments);
        }

        @GeneratedBy(value=ConstructorBuiltins.ConstructTemporalPlainDateTimeNode.class)
        private static final class ConstructTemporalPlainDateTimeData
        extends Node {
            @Node.Child
            JSToIntegerThrowOnInfinityNode toIntegerNode_;
            @Node.Child
            ToTemporalCalendarWithISODefaultNode toTemporalCalendarWithISODefaultNode_;
            @CompilerDirectives.CompilationFinal
            BranchProfile errorBranch_;

            ConstructTemporalPlainDateTimeData() {
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

    @GeneratedBy(value=ConstructorBuiltins.ConstructTemporalPlainTimeNode.class)
    public static final class ConstructTemporalPlainTimeNodeGen
    extends ConstructorBuiltins.ConstructTemporalPlainTimeNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @Node.Child
        private JavaScriptNode arguments2_;
        @Node.Child
        private JavaScriptNode arguments3_;
        @Node.Child
        private JavaScriptNode arguments4_;
        @Node.Child
        private JavaScriptNode arguments5_;
        @Node.Child
        private JavaScriptNode arguments6_;
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @CompilerDirectives.CompilationFinal
        private BranchProfile errorBranch_;
        @Node.Child
        private JSToIntegerThrowOnInfinityNode toIntegerNode_;

        private ConstructTemporalPlainTimeNodeGen(JSContext context, JSBuiltin builtin, boolean isNewTargetCase, JavaScriptNode[] arguments) {
            super(context, builtin, isNewTargetCase);
            this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
            this.arguments1_ = arguments != null && 1 < arguments.length ? arguments[1] : null;
            this.arguments2_ = arguments != null && 2 < arguments.length ? arguments[2] : null;
            this.arguments3_ = arguments != null && 3 < arguments.length ? arguments[3] : null;
            this.arguments4_ = arguments != null && 4 < arguments.length ? arguments[4] : null;
            this.arguments5_ = arguments != null && 5 < arguments.length ? arguments[5] : null;
            this.arguments6_ = arguments != null && 6 < arguments.length ? arguments[6] : null;
        }

        @Override
        public JavaScriptNode[] getArguments() {
            return new JavaScriptNode[]{this.arguments0_, this.arguments1_, this.arguments2_, this.arguments3_, this.arguments4_, this.arguments5_, this.arguments6_};
        }

        @Override
        public Object execute(VirtualFrame frameValue) {
            int state_0 = this.state_0_;
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            Object arguments2Value_ = this.arguments2_.execute(frameValue);
            Object arguments3Value_ = this.arguments3_.execute(frameValue);
            Object arguments4Value_ = this.arguments4_.execute(frameValue);
            Object arguments5Value_ = this.arguments5_.execute(frameValue);
            Object arguments6Value_ = this.arguments6_.execute(frameValue);
            if (state_0 != 0 && arguments0Value_ instanceof JSDynamicObject) {
                JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
                return this.constructTemporalPlainTime(arguments0Value__, arguments1Value_, arguments2Value_, arguments3Value_, arguments4Value_, arguments5Value_, arguments6Value_, this.errorBranch_, this.toIntegerNode_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_, arguments2Value_, arguments3Value_, arguments4Value_, arguments5Value_, arguments6Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.execute(frameValue);
        }

        private JSDynamicObject executeAndSpecialize(Object arguments0Value, Object arguments1Value, Object arguments2Value, Object arguments3Value, Object arguments4Value, Object arguments5Value, Object arguments6Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                if (arguments0Value instanceof JSDynamicObject) {
                    JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
                    this.errorBranch_ = BranchProfile.create();
                    this.toIntegerNode_ = super.insert(JSToIntegerThrowOnInfinityNode.create());
                    this.state_0_ = state_0 |= 1;
                    lock.unlock();
                    hasLock = false;
                    JSDynamicObject jSDynamicObject = this.constructTemporalPlainTime(arguments0Value_, arguments1Value, arguments2Value, arguments3Value, arguments4Value, arguments5Value, arguments6Value, this.errorBranch_, this.toIntegerNode_);
                    return jSDynamicObject;
                }
                throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_, this.arguments1_, this.arguments2_, this.arguments3_, this.arguments4_, this.arguments5_, this.arguments6_}, arguments0Value, arguments1Value, arguments2Value, arguments3Value, arguments4Value, arguments5Value, arguments6Value);
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
            s[0] = "constructTemporalPlainTime";
            if (state_0 != 0) {
                s[1] = (byte)1;
                ArrayList<List<Cloneable>> cached = new ArrayList<List<Cloneable>>();
                cached.add(Arrays.asList(this.errorBranch_, this.toIntegerNode_));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static ConstructorBuiltins.ConstructTemporalPlainTimeNode create(JSContext context, JSBuiltin builtin, boolean isNewTargetCase, JavaScriptNode[] arguments) {
            return new ConstructTemporalPlainTimeNodeGen(context, builtin, isNewTargetCase, arguments);
        }
    }

    @GeneratedBy(value=ConstructorBuiltins.ConstructTemporalPlainDateNode.class)
    public static final class ConstructTemporalPlainDateNodeGen
    extends ConstructorBuiltins.ConstructTemporalPlainDateNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @Node.Child
        private JavaScriptNode arguments2_;
        @Node.Child
        private JavaScriptNode arguments3_;
        @Node.Child
        private JavaScriptNode arguments4_;
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private ConstructTemporalPlainDateData constructTemporalPlainDate_cache;

        private ConstructTemporalPlainDateNodeGen(JSContext context, JSBuiltin builtin, boolean isNewTargetCase, JavaScriptNode[] arguments) {
            super(context, builtin, isNewTargetCase);
            this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
            this.arguments1_ = arguments != null && 1 < arguments.length ? arguments[1] : null;
            this.arguments2_ = arguments != null && 2 < arguments.length ? arguments[2] : null;
            this.arguments3_ = arguments != null && 3 < arguments.length ? arguments[3] : null;
            this.arguments4_ = arguments != null && 4 < arguments.length ? arguments[4] : null;
        }

        @Override
        public JavaScriptNode[] getArguments() {
            return new JavaScriptNode[]{this.arguments0_, this.arguments1_, this.arguments2_, this.arguments3_, this.arguments4_};
        }

        @Override
        public Object execute(VirtualFrame frameValue) {
            int state_0 = this.state_0_;
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            Object arguments2Value_ = this.arguments2_.execute(frameValue);
            Object arguments3Value_ = this.arguments3_.execute(frameValue);
            Object arguments4Value_ = this.arguments4_.execute(frameValue);
            if (state_0 != 0 && arguments0Value_ instanceof JSDynamicObject) {
                JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
                ConstructTemporalPlainDateData s0_ = this.constructTemporalPlainDate_cache;
                if (s0_ != null) {
                    return this.constructTemporalPlainDate(arguments0Value__, arguments1Value_, arguments2Value_, arguments3Value_, arguments4Value_, s0_.toIntegerNode_, s0_.toTemporalCalendarWithISODefaultNode_, s0_.errorBranch_);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_, arguments2Value_, arguments3Value_, arguments4Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.execute(frameValue);
        }

        private JSDynamicObject executeAndSpecialize(Object arguments0Value, Object arguments1Value, Object arguments2Value, Object arguments3Value, Object arguments4Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                if (arguments0Value instanceof JSDynamicObject) {
                    JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
                    ConstructTemporalPlainDateData s0_ = super.insert(new ConstructTemporalPlainDateData());
                    s0_.toIntegerNode_ = s0_.insertAccessor(JSToIntegerThrowOnInfinityNode.create());
                    s0_.toTemporalCalendarWithISODefaultNode_ = s0_.insertAccessor(ToTemporalCalendarWithISODefaultNode.create(this.getContext()));
                    s0_.errorBranch_ = BranchProfile.create();
                    VarHandle.storeStoreFence();
                    this.constructTemporalPlainDate_cache = s0_;
                    this.state_0_ = state_0 |= 1;
                    lock.unlock();
                    hasLock = false;
                    JSDynamicObject jSDynamicObject = this.constructTemporalPlainDate(arguments0Value_, arguments1Value, arguments2Value, arguments3Value, arguments4Value, s0_.toIntegerNode_, s0_.toTemporalCalendarWithISODefaultNode_, s0_.errorBranch_);
                    return jSDynamicObject;
                }
                throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_, this.arguments1_, this.arguments2_, this.arguments3_, this.arguments4_}, arguments0Value, arguments1Value, arguments2Value, arguments3Value, arguments4Value);
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
            s[0] = "constructTemporalPlainDate";
            if (state_0 != 0) {
                s[1] = (byte)1;
                ArrayList<List<Cloneable>> cached = new ArrayList<List<Cloneable>>();
                ConstructTemporalPlainDateData s0_ = this.constructTemporalPlainDate_cache;
                if (s0_ != null) {
                    cached.add(Arrays.asList(s0_.toIntegerNode_, s0_.toTemporalCalendarWithISODefaultNode_, s0_.errorBranch_));
                }
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static ConstructorBuiltins.ConstructTemporalPlainDateNode create(JSContext context, JSBuiltin builtin, boolean isNewTargetCase, JavaScriptNode[] arguments) {
            return new ConstructTemporalPlainDateNodeGen(context, builtin, isNewTargetCase, arguments);
        }

        @GeneratedBy(value=ConstructorBuiltins.ConstructTemporalPlainDateNode.class)
        private static final class ConstructTemporalPlainDateData
        extends Node {
            @Node.Child
            JSToIntegerThrowOnInfinityNode toIntegerNode_;
            @Node.Child
            ToTemporalCalendarWithISODefaultNode toTemporalCalendarWithISODefaultNode_;
            @CompilerDirectives.CompilationFinal
            BranchProfile errorBranch_;

            ConstructTemporalPlainDateData() {
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

    @GeneratedBy(value=ConstructorBuiltins.ConstructDateNode.class)
    public static final class ConstructDateNodeGen
    extends ConstructorBuiltins.ConstructDateNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @CompilerDirectives.CompilationFinal
        private ConditionProfile constructDateOne_isSpecialCase_;
        @Node.Child
        private InteropLibrary constructDateOne_interop_;

        private ConstructDateNodeGen(JSContext context, JSBuiltin builtin, boolean isNewTargetCase, JavaScriptNode[] arguments) {
            super(context, builtin, isNewTargetCase);
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
                if (arguments1Value_ instanceof Object[]) {
                    Object[] arguments1Value__ = (Object[])arguments1Value_;
                    if ((state_0 & 1) != 0 && arguments1Value__.length == 0) {
                        return this.constructDateZero(arguments0Value__, arguments1Value__);
                    }
                    if ((state_0 & 2) != 0 && arguments1Value__.length == 1) {
                        return this.constructDateOne(arguments0Value__, arguments1Value__, this.constructDateOne_isSpecialCase_, this.constructDateOne_interop_);
                    }
                    if ((state_0 & 4) != 0 && arguments1Value__.length >= 2) {
                        return this.constructDateMult(arguments0Value__, arguments1Value__);
                    }
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.execute(frameValue);
        }

        private JSDynamicObject executeAndSpecialize(Object arguments0Value, Object arguments1Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                if (arguments0Value instanceof JSDynamicObject) {
                    JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
                    if (arguments1Value instanceof Object[]) {
                        Object[] arguments1Value_ = (Object[])arguments1Value;
                        if (arguments1Value_.length == 0) {
                            this.state_0_ = state_0 |= 1;
                            lock.unlock();
                            hasLock = false;
                            JSDynamicObject jSDynamicObject = this.constructDateZero(arguments0Value_, arguments1Value_);
                            return jSDynamicObject;
                        }
                        if (arguments1Value_.length == 1) {
                            this.constructDateOne_isSpecialCase_ = ConditionProfile.createBinaryProfile();
                            this.constructDateOne_interop_ = super.insert(INTEROP_LIBRARY_.createDispatched(5));
                            this.state_0_ = state_0 |= 2;
                            lock.unlock();
                            hasLock = false;
                            JSDynamicObject jSDynamicObject = this.constructDateOne(arguments0Value_, arguments1Value_, this.constructDateOne_isSpecialCase_, this.constructDateOne_interop_);
                            return jSDynamicObject;
                        }
                        if (arguments1Value_.length >= 2) {
                            this.state_0_ = state_0 |= 4;
                            lock.unlock();
                            hasLock = false;
                            JSDynamicObject jSDynamicObject = this.constructDateMult(arguments0Value_, arguments1Value_);
                            return jSDynamicObject;
                        }
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
            s[0] = "constructDateZero";
            s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[1] = s;
            s = new Object[3];
            s[0] = "constructDateOne";
            if ((state_0 & 2) != 0) {
                s[1] = (byte)1;
                ArrayList<List<Cloneable>> cached = new ArrayList<List<Cloneable>>();
                cached.add(Arrays.asList(this.constructDateOne_isSpecialCase_, this.constructDateOne_interop_));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[2] = s;
            s = new Object[3];
            s[0] = "constructDateMult";
            s[1] = (state_0 & 4) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[3] = s;
            return Introspection.Provider.create(data);
        }

        public static ConstructorBuiltins.ConstructDateNode create(JSContext context, JSBuiltin builtin, boolean isNewTargetCase, JavaScriptNode[] arguments) {
            return new ConstructDateNodeGen(context, builtin, isNewTargetCase, arguments);
        }
    }

    @GeneratedBy(value=ConstructorBuiltins.CallDateNode.class)
    public static final class CallDateNodeGen
    extends ConstructorBuiltins.CallDateNode
    implements Introspection.Provider {
        private CallDateNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            super(context, builtin);
        }

        @Override
        public JavaScriptNode[] getArguments() {
            return new JavaScriptNode[0];
        }

        @Override
        public Object execute(VirtualFrame frameValue) {
            return this.callDate();
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
            s[0] = "callDate";
            s[1] = (byte)1;
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static ConstructorBuiltins.CallDateNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new CallDateNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=ConstructorBuiltins.ConstructBooleanNode.class)
    public static final class ConstructBooleanNodeGen
    extends ConstructorBuiltins.ConstructBooleanNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private JSToBooleanNode toBoolean_;

        private ConstructBooleanNodeGen(JSContext context, JSBuiltin builtin, boolean isNewTargetCase, JavaScriptNode[] arguments) {
            super(context, builtin, isNewTargetCase);
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
                return this.constructBoolean(arguments0Value__, arguments1Value_, this.toBoolean_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.execute(frameValue);
        }

        private JSDynamicObject executeAndSpecialize(Object arguments0Value, Object arguments1Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                if (arguments0Value instanceof JSDynamicObject) {
                    JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
                    this.toBoolean_ = super.insert(JSToBooleanNode.create());
                    this.state_0_ = state_0 |= 1;
                    lock.unlock();
                    hasLock = false;
                    JSDynamicObject jSDynamicObject = this.constructBoolean(arguments0Value_, arguments1Value, this.toBoolean_);
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
            s[0] = "constructBoolean";
            if (state_0 != 0) {
                s[1] = (byte)1;
                ArrayList<List<JSToBooleanNode>> cached = new ArrayList<List<JSToBooleanNode>>();
                cached.add(Arrays.asList(this.toBoolean_));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static ConstructorBuiltins.ConstructBooleanNode create(JSContext context, JSBuiltin builtin, boolean isNewTargetCase, JavaScriptNode[] arguments) {
            return new ConstructBooleanNodeGen(context, builtin, isNewTargetCase, arguments);
        }
    }

    @GeneratedBy(value=ConstructorBuiltins.CallBooleanNode.class)
    public static final class CallBooleanNodeGen
    extends ConstructorBuiltins.CallBooleanNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private JSToBooleanNode toBoolean_;

        private CallBooleanNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            if (state_0 != 0) {
                return this.callBoolean(arguments0Value_, this.toBoolean_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_);
        }

        @Override
        public boolean executeBoolean(VirtualFrame frameValue) {
            int state_0 = this.state_0_;
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            if (state_0 != 0) {
                return this.callBoolean(arguments0Value_, this.toBoolean_);
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
                this.toBoolean_ = super.insert(JSToBooleanNode.create());
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                boolean bl = this.callBoolean(arguments0Value, this.toBoolean_);
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
            return NodeCost.MONOMORPHIC;
        }

        @Override
        public Introspection getIntrospectionData() {
            Object[] data = new Object[2];
            data[0] = 0;
            int state_0 = this.state_0_;
            Object[] s = new Object[3];
            s[0] = "callBoolean";
            if (state_0 != 0) {
                s[1] = (byte)1;
                ArrayList<List<JSToBooleanNode>> cached = new ArrayList<List<JSToBooleanNode>>();
                cached.add(Arrays.asList(this.toBoolean_));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static ConstructorBuiltins.CallBooleanNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new CallBooleanNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=ConstructorBuiltins.ConstructArrayNode.class)
    public static final class ConstructArrayNodeGen
    extends ConstructorBuiltins.ConstructArrayNode
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
        private ConstructWithLengthData constructWithLength_cache;
        @Node.Child
        private ConstructWithForeignArg0Data constructWithForeignArg0_cache;
        @Node.Child
        private ConstructWithForeignArg1Data constructWithForeignArg1_cache;
        @CompilerDirectives.CompilationFinal
        private ConstructArrayVarargsData constructArrayVarargs_cache;

        private ConstructArrayNodeGen(JSContext context, JSBuiltin builtin, boolean isNewTargetCase, JavaScriptNode[] arguments) {
            super(context, builtin, isNewTargetCase);
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
            if (state_0 != 0 && arguments0Value_ instanceof JSDynamicObject) {
                JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
                if (arguments1Value_ instanceof Object[]) {
                    ConstructArrayVarargsData s5_;
                    ConstructWithForeignArg1Data s4_;
                    Object[] arguments1Value__ = (Object[])arguments1Value_;
                    if ((state_0 & 1) != 0 && arguments1Value__.length == 0) {
                        return this.constructArray0(arguments0Value__, arguments1Value__);
                    }
                    if ((state_0 & 2) != 0 && ConstructorBuiltins.ConstructArrayNode.isOneIntegerArg(arguments1Value__)) {
                        return this.constructArrayWithIntLength(arguments0Value__, arguments1Value__);
                    }
                    if ((state_0 & 4) != 0 && arguments1Value__.length == 1) {
                        ConstructWithLengthData s2_ = this.constructWithLength_cache;
                        while (s2_ != null) {
                            long len__ = s2_.toArrayLengthNode_.executeLong(ConstructorBuiltins.ConstructArrayNode.firstArg(arguments1Value__));
                            if (s2_.toArrayLengthNode_.isTypeNumber(len__)) {
                                return this.constructWithLength(arguments0Value__, arguments1Value__, s2_.toArrayLengthNode_, s2_.arrayCreateNode_, len__);
                            }
                            s2_ = s2_.next_;
                        }
                    }
                    if ((state_0 & 8) != 0) {
                        ConstructWithForeignArg0Data s3_ = this.constructWithForeignArg0_cache;
                        while (s3_ != null) {
                            if (s3_.interop_.accepts(ConstructorBuiltins.ConstructArrayNode.firstArg(arguments1Value__)) && ConstructorBuiltins.ConstructArrayNode.isOneForeignArg(arguments1Value__)) {
                                return this.constructWithForeignArg(arguments0Value__, arguments1Value__, s3_.interop_, s3_.arrayCreateNode_, s3_.isNumber_, s3_.rangeErrorProfile_);
                            }
                            s3_ = s3_.next_;
                        }
                    }
                    if ((state_0 & 0x10) != 0 && (s4_ = this.constructWithForeignArg1_cache) != null && ConstructorBuiltins.ConstructArrayNode.isOneForeignArg(arguments1Value__)) {
                        return this.constructWithForeignArg1Boundary(state_0, s4_, arguments0Value__, arguments1Value__);
                    }
                    if ((state_0 & 0x20) != 0 && (s5_ = this.constructArrayVarargs_cache) != null && !ConstructorBuiltins.ConstructArrayNode.isOneNumberArg(arguments1Value__) && !ConstructorBuiltins.ConstructArrayNode.isOneForeignArg(arguments1Value__)) {
                        return this.constructArrayVarargs(arguments0Value__, arguments1Value__, s5_.isIntegerCase_, s5_.isDoubleCase_, s5_.isObjectCase_, s5_.isLengthZero_);
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
        private Object constructWithForeignArg1Boundary(int state_0, ConstructWithForeignArg1Data s4_, JSDynamicObject arguments0Value__, Object[] arguments1Value__) {
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this);
            try {
                InteropLibrary interop__ = INTEROP_LIBRARY_.getUncached(ConstructorBuiltins.ConstructArrayNode.firstArg(arguments1Value__));
                JSDynamicObject jSDynamicObject = this.constructWithForeignArg(arguments0Value__, arguments1Value__, interop__, s4_.arrayCreateNode_, s4_.isNumber_, s4_.rangeErrorProfile_);
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
                if (arguments0Value instanceof JSDynamicObject) {
                    JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
                    if (arguments1Value instanceof Object[]) {
                        JSDynamicObject jSDynamicObject;
                        Object[] arguments1Value_ = (Object[])arguments1Value;
                        if (arguments1Value_.length == 0) {
                            this.state_0_ = state_0 |= 1;
                            lock.unlock();
                            hasLock = false;
                            JSDynamicObject jSDynamicObject2 = this.constructArray0(arguments0Value_, arguments1Value_);
                            return jSDynamicObject2;
                        }
                        if ((exclude & 1) == 0 && ConstructorBuiltins.ConstructArrayNode.isOneIntegerArg(arguments1Value_)) {
                            this.state_0_ = state_0 |= 2;
                            lock.unlock();
                            hasLock = false;
                            JSDynamicObject jSDynamicObject3 = this.constructArrayWithIntLength(arguments0Value_, arguments1Value_);
                            return jSDynamicObject3;
                        }
                        long len__ = 0L;
                        if (arguments1Value_.length == 1) {
                            ToArrayLengthNode toArrayLengthNode__;
                            int count2_ = 0;
                            ConstructWithLengthData s2_ = this.constructWithLength_cache;
                            if ((state_0 & 4) != 0) {
                                while (s2_ != null && !s2_.toArrayLengthNode_.isTypeNumber(len__ = s2_.toArrayLengthNode_.executeLong(ConstructorBuiltins.ConstructArrayNode.firstArg(arguments1Value_)))) {
                                    s2_ = s2_.next_;
                                    ++count2_;
                                }
                            }
                            if (s2_ == null && (toArrayLengthNode__ = super.insert(ToArrayLengthNode.create())).isTypeNumber(len__ = toArrayLengthNode__.executeLong(ConstructorBuiltins.ConstructArrayNode.firstArg(arguments1Value_))) && count2_ < 3) {
                                s2_ = super.insert(new ConstructWithLengthData(this.constructWithLength_cache));
                                s2_.toArrayLengthNode_ = s2_.insertAccessor(toArrayLengthNode__);
                                s2_.arrayCreateNode_ = s2_.insertAccessor(ArrayCreateNode.create(this.getContext()));
                                VarHandle.storeStoreFence();
                                this.constructWithLength_cache = s2_;
                                this.exclude_ = exclude |= 1;
                                state_0 &= 0xFFFFFFFD;
                                this.state_0_ = state_0 |= 4;
                            }
                            if (s2_ != null) {
                                lock.unlock();
                                hasLock = false;
                                jSDynamicObject = this.constructWithLength(arguments0Value_, arguments1Value_, s2_.toArrayLengthNode_, s2_.arrayCreateNode_, len__);
                                return jSDynamicObject;
                            }
                        }
                        if ((exclude & 2) == 0) {
                            int count3_ = 0;
                            ConstructWithForeignArg0Data s3_ = this.constructWithForeignArg0_cache;
                            if ((state_0 & 8) != 0) {
                                while (!(s3_ == null || s3_.interop_.accepts(ConstructorBuiltins.ConstructArrayNode.firstArg(arguments1Value_)) && ConstructorBuiltins.ConstructArrayNode.isOneForeignArg(arguments1Value_))) {
                                    s3_ = s3_.next_;
                                    ++count3_;
                                }
                            }
                            if (s3_ == null && ConstructorBuiltins.ConstructArrayNode.isOneForeignArg(arguments1Value_) && count3_ < 5) {
                                s3_ = super.insert(new ConstructWithForeignArg0Data(this.constructWithForeignArg0_cache));
                                s3_.interop_ = s3_.insertAccessor(INTEROP_LIBRARY_.create(ConstructorBuiltins.ConstructArrayNode.firstArg(arguments1Value_)));
                                s3_.arrayCreateNode_ = s3_.insertAccessor(ArrayCreateNode.create(this.getContext()));
                                s3_.isNumber_ = ConditionProfile.createBinaryProfile();
                                s3_.rangeErrorProfile_ = BranchProfile.create();
                                VarHandle.storeStoreFence();
                                this.constructWithForeignArg0_cache = s3_;
                                this.state_0_ = state_0 |= 8;
                            }
                            if (s3_ != null) {
                                lock.unlock();
                                hasLock = false;
                                JSDynamicObject count2_ = this.constructWithForeignArg(arguments0Value_, arguments1Value_, s3_.interop_, s3_.arrayCreateNode_, s3_.isNumber_, s3_.rangeErrorProfile_);
                                return count2_;
                            }
                        }
                        InteropLibrary interop__ = null;
                        EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                        Node prev_ = encapsulating_.set(this);
                        try {
                            if (ConstructorBuiltins.ConstructArrayNode.isOneForeignArg(arguments1Value_)) {
                                ConstructWithForeignArg1Data s4_ = super.insert(new ConstructWithForeignArg1Data());
                                interop__ = INTEROP_LIBRARY_.getUncached(ConstructorBuiltins.ConstructArrayNode.firstArg(arguments1Value_));
                                s4_.arrayCreateNode_ = s4_.insertAccessor(ArrayCreateNode.create(this.getContext()));
                                s4_.isNumber_ = ConditionProfile.createBinaryProfile();
                                s4_.rangeErrorProfile_ = BranchProfile.create();
                                VarHandle.storeStoreFence();
                                this.constructWithForeignArg1_cache = s4_;
                                this.exclude_ = exclude |= 2;
                                this.constructWithForeignArg0_cache = null;
                                state_0 &= 0xFFFFFFF7;
                                this.state_0_ = state_0 |= 0x10;
                                lock.unlock();
                                hasLock = false;
                                jSDynamicObject = this.constructWithForeignArg(arguments0Value_, arguments1Value_, interop__, s4_.arrayCreateNode_, s4_.isNumber_, s4_.rangeErrorProfile_);
                                return jSDynamicObject;
                            }
                        }
                        finally {
                            encapsulating_.set(prev_);
                        }
                        if (!ConstructorBuiltins.ConstructArrayNode.isOneNumberArg(arguments1Value_) && !ConstructorBuiltins.ConstructArrayNode.isOneForeignArg(arguments1Value_)) {
                            ConstructArrayVarargsData s5_ = new ConstructArrayVarargsData();
                            s5_.isIntegerCase_ = BranchProfile.create();
                            s5_.isDoubleCase_ = BranchProfile.create();
                            s5_.isObjectCase_ = BranchProfile.create();
                            s5_.isLengthZero_ = ConditionProfile.createBinaryProfile();
                            VarHandle.storeStoreFence();
                            this.constructArrayVarargs_cache = s5_;
                            this.state_0_ = state_0 |= 0x20;
                            lock.unlock();
                            hasLock = false;
                            JSDynamicObject jSDynamicObject4 = this.constructArrayVarargs(arguments0Value_, arguments1Value_, s5_.isIntegerCase_, s5_.isDoubleCase_, s5_.isObjectCase_, s5_.isLengthZero_);
                            return jSDynamicObject4;
                        }
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
            int state_0 = this.state_0_;
            if (state_0 == 0) {
                return NodeCost.UNINITIALIZED;
            }
            if ((state_0 & state_0 - 1) == 0) {
                ConstructWithLengthData s2_ = this.constructWithLength_cache;
                ConstructWithForeignArg0Data s3_ = this.constructWithForeignArg0_cache;
                if (!(s2_ != null && s2_.next_ != null || s3_ != null && s3_.next_ != null)) {
                    return NodeCost.MONOMORPHIC;
                }
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
            s[0] = "constructArray0";
            s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[1] = s;
            s = new Object[3];
            s[0] = "constructArrayWithIntLength";
            s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : ((exclude & 1) != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0));
            data[2] = s;
            s = new Object[3];
            s[0] = "constructWithLength";
            if ((state_0 & 4) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList<List<Cloneable>>();
                ConstructWithLengthData s2_ = this.constructWithLength_cache;
                while (s2_ != null) {
                    cached.add(Arrays.asList(s2_.toArrayLengthNode_, s2_.arrayCreateNode_));
                    s2_ = s2_.next_;
                }
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[3] = s;
            s = new Object[3];
            s[0] = "constructWithForeignArg";
            if ((state_0 & 8) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList();
                ConstructWithForeignArg0Data s3_ = this.constructWithForeignArg0_cache;
                while (s3_ != null) {
                    cached.add(Arrays.asList(s3_.interop_, s3_.arrayCreateNode_, s3_.isNumber_, s3_.rangeErrorProfile_));
                    s3_ = s3_.next_;
                }
                s[2] = cached;
            } else {
                s[1] = (exclude & 2) != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0);
            }
            data[4] = s;
            s = new Object[3];
            s[0] = "constructWithForeignArg";
            if ((state_0 & 0x10) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList();
                ConstructWithForeignArg1Data s4_ = this.constructWithForeignArg1_cache;
                if (s4_ != null) {
                    cached.add(Arrays.asList(s4_.arrayCreateNode_, s4_.isNumber_, s4_.rangeErrorProfile_));
                }
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[5] = s;
            s = new Object[3];
            s[0] = "constructArrayVarargs";
            if ((state_0 & 0x20) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList();
                ConstructArrayVarargsData s5_ = this.constructArrayVarargs_cache;
                if (s5_ != null) {
                    cached.add(Arrays.asList(s5_.isIntegerCase_, s5_.isDoubleCase_, s5_.isObjectCase_, s5_.isLengthZero_));
                }
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[6] = s;
            return Introspection.Provider.create(data);
        }

        public static ConstructorBuiltins.ConstructArrayNode create(JSContext context, JSBuiltin builtin, boolean isNewTargetCase, JavaScriptNode[] arguments) {
            return new ConstructArrayNodeGen(context, builtin, isNewTargetCase, arguments);
        }

        @GeneratedBy(value=ConstructorBuiltins.ConstructArrayNode.class)
        private static final class ConstructArrayVarargsData {
            @CompilerDirectives.CompilationFinal
            BranchProfile isIntegerCase_;
            @CompilerDirectives.CompilationFinal
            BranchProfile isDoubleCase_;
            @CompilerDirectives.CompilationFinal
            BranchProfile isObjectCase_;
            @CompilerDirectives.CompilationFinal
            ConditionProfile isLengthZero_;

            ConstructArrayVarargsData() {
            }
        }

        @GeneratedBy(value=ConstructorBuiltins.ConstructArrayNode.class)
        private static final class ConstructWithForeignArg1Data
        extends Node {
            @Node.Child
            ArrayCreateNode arrayCreateNode_;
            @CompilerDirectives.CompilationFinal
            ConditionProfile isNumber_;
            @CompilerDirectives.CompilationFinal
            BranchProfile rangeErrorProfile_;

            ConstructWithForeignArg1Data() {
            }

            @Override
            public NodeCost getCost() {
                return NodeCost.NONE;
            }

            <T extends Node> T insertAccessor(T node) {
                return super.insert(node);
            }
        }

        @GeneratedBy(value=ConstructorBuiltins.ConstructArrayNode.class)
        private static final class ConstructWithForeignArg0Data
        extends Node {
            @Node.Child
            ConstructWithForeignArg0Data next_;
            @Node.Child
            InteropLibrary interop_;
            @Node.Child
            ArrayCreateNode arrayCreateNode_;
            @CompilerDirectives.CompilationFinal
            ConditionProfile isNumber_;
            @CompilerDirectives.CompilationFinal
            BranchProfile rangeErrorProfile_;

            ConstructWithForeignArg0Data(ConstructWithForeignArg0Data next_) {
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

        @GeneratedBy(value=ConstructorBuiltins.ConstructArrayNode.class)
        private static final class ConstructWithLengthData
        extends Node {
            @Node.Child
            ConstructWithLengthData next_;
            @Node.Child
            ToArrayLengthNode toArrayLengthNode_;
            @Node.Child
            ArrayCreateNode arrayCreateNode_;

            ConstructWithLengthData(ConstructWithLengthData next_) {
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

