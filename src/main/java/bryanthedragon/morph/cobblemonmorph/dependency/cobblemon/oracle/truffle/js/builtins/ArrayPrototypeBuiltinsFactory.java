/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.invoke.VarHandle
 */
package com.oracle.truffle.js.builtins;

import com.oracle.truffle.api.Assumption;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.library.LibraryFactory;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.nodes.SlowPathException;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.api.profiles.ValueProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.builtins.ArrayPrototypeBuiltins;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.JSTypesGen;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.access.GetPrototypeNode;
import com.oracle.truffle.js.nodes.access.IsArrayNode;
import com.oracle.truffle.js.nodes.access.WritePropertyNode;
import com.oracle.truffle.js.nodes.array.ArrayLengthNode;
import com.oracle.truffle.js.nodes.array.JSArrayDeleteRangeNode;
import com.oracle.truffle.js.nodes.array.JSArrayFirstElementIndexNode;
import com.oracle.truffle.js.nodes.array.JSArrayLastElementIndexNode;
import com.oracle.truffle.js.nodes.array.JSArrayToDenseObjectArrayNode;
import com.oracle.truffle.js.nodes.array.TestArrayNode;
import com.oracle.truffle.js.nodes.binary.JSIdenticalNode;
import com.oracle.truffle.js.nodes.cast.JSToIntegerAsLongNode;
import com.oracle.truffle.js.nodes.cast.JSToObjectNode;
import com.oracle.truffle.js.nodes.cast.JSToStringNode;
import com.oracle.truffle.js.nodes.control.DeletePropertyNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.array.ScriptArray;
import com.oracle.truffle.js.runtime.builtins.JSArrayObject;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=ArrayPrototypeBuiltins.class)
public final class ArrayPrototypeBuiltinsFactory {
    private static final LibraryFactory<InteropLibrary> INTEROP_LIBRARY_ = LibraryFactory.resolve(InteropLibrary.class);

    @GeneratedBy(value=ArrayPrototypeBuiltins.JSArrayGroupToMapNode.class)
    public static final class JSArrayGroupToMapNodeGen
    extends ArrayPrototypeBuiltins.JSArrayGroupToMapNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @Node.Child
        private JavaScriptNode arguments2_;

        private JSArrayGroupToMapNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            Object arguments2Value_ = this.arguments2_.execute(frameValue);
            return this.groupToMap(arguments0Value_, arguments1Value_, arguments2Value_);
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
            s[0] = "groupToMap";
            s[1] = (byte)1;
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static ArrayPrototypeBuiltins.JSArrayGroupToMapNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSArrayGroupToMapNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=ArrayPrototypeBuiltins.JSArrayGroupNode.class)
    public static final class JSArrayGroupNodeGen
    extends ArrayPrototypeBuiltins.JSArrayGroupNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @Node.Child
        private JavaScriptNode arguments2_;

        private JSArrayGroupNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            Object arguments2Value_ = this.arguments2_.execute(frameValue);
            return this.group(arguments0Value_, arguments1Value_, arguments2Value_);
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
            s[0] = "group";
            s[1] = (byte)1;
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static ArrayPrototypeBuiltins.JSArrayGroupNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSArrayGroupNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=ArrayPrototypeBuiltins.JSArrayAtNode.class)
    public static final class JSArrayAtNodeGen
    extends ArrayPrototypeBuiltins.JSArrayAtNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;

        private JSArrayAtNodeGen(JSContext context, JSBuiltin builtin, boolean isTypedArrayImplementation, JavaScriptNode[] arguments) {
            super(context, builtin, isTypedArrayImplementation);
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
            return this.at(arguments0Value_, arguments1Value_);
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
            s[0] = "at";
            s[1] = (byte)1;
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static ArrayPrototypeBuiltins.JSArrayAtNode create(JSContext context, JSBuiltin builtin, boolean isTypedArrayImplementation, JavaScriptNode[] arguments) {
            return new JSArrayAtNodeGen(context, builtin, isTypedArrayImplementation, arguments);
        }
    }

    @GeneratedBy(value=ArrayPrototypeBuiltins.JSArrayIteratorNode.class)
    public static final class JSArrayIteratorNodeGen
    extends ArrayPrototypeBuiltins.JSArrayIteratorNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private JSToObjectNode notJSObject_toObjectNode_;

        private JSArrayIteratorNodeGen(JSContext context, JSBuiltin builtin, int iterationKind, JavaScriptNode[] arguments) {
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
            if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSDynamicObject && JSGuards.isJSObject(arguments0Value__ = (JSDynamicObject)arguments0Value_)) {
                return this.doJSObject(arguments0Value__);
            }
            if ((state_0 & 2) != 0 && !JSGuards.isJSObject(arguments0Value_)) {
                return this.doNotJSObject(arguments0Value_, this.notJSObject_toObjectNode_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.execute(frameValue);
        }

        private JSDynamicObject executeAndSpecialize(Object arguments0Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                JSDynamicObject arguments0Value_;
                int state_0 = this.state_0_;
                if (arguments0Value instanceof JSDynamicObject && JSGuards.isJSObject(arguments0Value_ = (JSDynamicObject)arguments0Value)) {
                    this.state_0_ = state_0 |= 1;
                    lock.unlock();
                    hasLock = false;
                    JSDynamicObject jSDynamicObject = this.doJSObject(arguments0Value_);
                    return jSDynamicObject;
                }
                if (!JSGuards.isJSObject(arguments0Value)) {
                    this.notJSObject_toObjectNode_ = super.insert(JSToObjectNode.createToObject(this.getContext()));
                    this.state_0_ = state_0 |= 2;
                    lock.unlock();
                    hasLock = false;
                    JSDynamicObject jSDynamicObject = this.doNotJSObject(arguments0Value, this.notJSObject_toObjectNode_);
                    return jSDynamicObject;
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
            s[0] = "doJSObject";
            s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[1] = s;
            s = new Object[3];
            s[0] = "doNotJSObject";
            if ((state_0 & 2) != 0) {
                s[1] = (byte)1;
                ArrayList<List<JSToObjectNode>> cached = new ArrayList<List<JSToObjectNode>>();
                cached.add(Arrays.asList(this.notJSObject_toObjectNode_));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[2] = s;
            return Introspection.Provider.create(data);
        }

        public static ArrayPrototypeBuiltins.JSArrayIteratorNode create(JSContext context, JSBuiltin builtin, int iterationKind, JavaScriptNode[] arguments) {
            return new JSArrayIteratorNodeGen(context, builtin, iterationKind, arguments);
        }
    }

    @GeneratedBy(value=ArrayPrototypeBuiltins.JSArrayReverseNode.class)
    public static final class JSArrayReverseNodeGen
    extends ArrayPrototypeBuiltins.JSArrayReverseNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @CompilerDirectives.CompilationFinal
        private int state_0_;
        @CompilerDirectives.CompilationFinal
        private int exclude_;

        private JSArrayReverseNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSArrayObject) {
                JSArrayObject arguments0Value__ = (JSArrayObject)arguments0Value_;
                return this.reverseJSArray(arguments0Value__);
            }
            if ((state_0 & 2) != 0) {
                return this.reverseGeneric(arguments0Value_);
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
                int exclude = this.exclude_;
                if (exclude == 0 && arguments0Value instanceof JSArrayObject) {
                    JSArrayObject arguments0Value_ = (JSArrayObject)arguments0Value;
                    this.state_0_ = state_0 |= 1;
                    lock.unlock();
                    hasLock = false;
                    Object object = this.reverseJSArray(arguments0Value_);
                    return object;
                }
                this.exclude_ = exclude |= 1;
                state_0 &= 0xFFFFFFFE;
                this.state_0_ = state_0 |= 2;
                lock.unlock();
                hasLock = false;
                Object object = this.reverseGeneric(arguments0Value);
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
            Object[] data = new Object[3];
            data[0] = 0;
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            Object[] s = new Object[3];
            s[0] = "reverseJSArray";
            s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : (exclude != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0));
            data[1] = s;
            s = new Object[3];
            s[0] = "reverseGeneric";
            s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[2] = s;
            return Introspection.Provider.create(data);
        }

        public static ArrayPrototypeBuiltins.JSArrayReverseNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSArrayReverseNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=ArrayPrototypeBuiltins.JSArrayIncludesNode.class)
    public static final class JSArrayIncludesNodeGen
    extends ArrayPrototypeBuiltins.JSArrayIncludesNode
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
        private JSIdenticalNode identicalNode_;

        private JSArrayIncludesNodeGen(JSContext context, JSBuiltin builtin, boolean isTypedArrayImplementation, JavaScriptNode[] arguments) {
            super(context, builtin, isTypedArrayImplementation);
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
                return this.includes(arguments0Value_, arguments1Value_, arguments2Value_, this.identicalNode_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_, arguments2Value_);
        }

        @Override
        public boolean executeBoolean(VirtualFrame frameValue) {
            int state_0 = this.state_0_;
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            Object arguments2Value_ = this.arguments2_.execute(frameValue);
            if (state_0 != 0) {
                return this.includes(arguments0Value_, arguments1Value_, arguments2Value_, this.identicalNode_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_, arguments2Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.executeBoolean(frameValue);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private boolean executeAndSpecialize(Object arguments0Value, Object arguments1Value, Object arguments2Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                this.identicalNode_ = super.insert(JSIdenticalNode.createSameValueZero());
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                boolean bl = this.includes(arguments0Value, arguments1Value, arguments2Value, this.identicalNode_);
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
            s[0] = "includes";
            if (state_0 != 0) {
                s[1] = (byte)1;
                ArrayList<List<JSIdenticalNode>> cached = new ArrayList<List<JSIdenticalNode>>();
                cached.add(Arrays.asList(this.identicalNode_));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static ArrayPrototypeBuiltins.JSArrayIncludesNode create(JSContext context, JSBuiltin builtin, boolean isTypedArrayImplementation, JavaScriptNode[] arguments) {
            return new JSArrayIncludesNodeGen(context, builtin, isTypedArrayImplementation, arguments);
        }
    }

    @GeneratedBy(value=ArrayPrototypeBuiltins.JSArrayCopyWithinNode.class)
    public static final class JSArrayCopyWithinNodeGen
    extends ArrayPrototypeBuiltins.JSArrayCopyWithinNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @Node.Child
        private JavaScriptNode arguments2_;
        @Node.Child
        private JavaScriptNode arguments3_;

        private JSArrayCopyWithinNodeGen(JSContext context, JSBuiltin builtin, boolean isTypedArrayImplementation, JavaScriptNode[] arguments) {
            super(context, builtin, isTypedArrayImplementation);
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
            return this.copyWithin(arguments0Value_, arguments1Value_, arguments2Value_, arguments3Value_);
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
            s[0] = "copyWithin";
            s[1] = (byte)1;
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static ArrayPrototypeBuiltins.JSArrayCopyWithinNode create(JSContext context, JSBuiltin builtin, boolean isTypedArrayImplementation, JavaScriptNode[] arguments) {
            return new JSArrayCopyWithinNodeGen(context, builtin, isTypedArrayImplementation, arguments);
        }
    }

    @GeneratedBy(value=ArrayPrototypeBuiltins.JSArrayFillNode.class)
    public static final class JSArrayFillNodeGen
    extends ArrayPrototypeBuiltins.JSArrayFillNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @Node.Child
        private JavaScriptNode arguments2_;
        @Node.Child
        private JavaScriptNode arguments3_;

        private JSArrayFillNodeGen(JSContext context, JSBuiltin builtin, boolean isTypedArrayImplementation, JavaScriptNode[] arguments) {
            super(context, builtin, isTypedArrayImplementation);
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

        public static ArrayPrototypeBuiltins.JSArrayFillNode create(JSContext context, JSBuiltin builtin, boolean isTypedArrayImplementation, JavaScriptNode[] arguments) {
            return new JSArrayFillNodeGen(context, builtin, isTypedArrayImplementation, arguments);
        }
    }

    @GeneratedBy(value=ArrayPrototypeBuiltins.JSArrayReduceNode.class)
    public static final class JSArrayReduceNodeGen
    extends ArrayPrototypeBuiltins.JSArrayReduceNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @Node.Child
        private JavaScriptNode arguments2_;
        @CompilerDirectives.CompilationFinal
        private int state_0_;

        private JSArrayReduceNodeGen(JSContext context, JSBuiltin builtin, boolean isTypedArrayImplementation, boolean isForward, JavaScriptNode[] arguments) {
            super(context, builtin, isTypedArrayImplementation, isForward);
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
                return this.reduce(arguments0Value_, arguments1Value_, arguments2Value__);
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
                return this.reduce(arguments0Value, arguments1Value, arguments2Value_);
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
            s[0] = "reduce";
            s[1] = state_0 != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static ArrayPrototypeBuiltins.JSArrayReduceNode create(JSContext context, JSBuiltin builtin, boolean isTypedArrayImplementation, boolean isForward, JavaScriptNode[] arguments) {
            return new JSArrayReduceNodeGen(context, builtin, isTypedArrayImplementation, isForward, arguments);
        }
    }

    @GeneratedBy(value=ArrayPrototypeBuiltins.JSArraySortNode.class)
    public static final class JSArraySortNodeGen
    extends ArrayPrototypeBuiltins.JSArraySortNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private JSArrayToDenseObjectArrayNode sortArray_arrayToObjectArrayNode_;
        @Node.Child
        private JSArrayDeleteRangeNode sortArray_arrayDeleteRangeNode_;
        @CompilerDirectives.CompilationFinal
        private Assumption sortArray_assumption0_;
        @CompilerDirectives.CompilationFinal
        private ConditionProfile sort_isJSObject_;

        private JSArraySortNodeGen(JSContext context, JSBuiltin builtin, boolean isTypedArrayImplementation, JavaScriptNode[] arguments) {
            super(context, builtin, isTypedArrayImplementation);
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
                if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSDynamicObject) {
                    JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
                    if (!Assumption.isValidAssumption(this.sortArray_assumption0_)) {
                        CompilerDirectives.transferToInterpreterAndInvalidate();
                        this.removeSortArray_();
                        return this.executeAndSpecialize(arguments0Value__, arguments1Value_);
                    }
                    assert (!this.isTypedArrayImplementation);
                    if (JSGuards.isJSFastArray(arguments0Value__)) {
                        return this.sortArray(arguments0Value__, arguments1Value_, this.sortArray_arrayToObjectArrayNode_, this.sortArray_arrayDeleteRangeNode_);
                    }
                }
                if ((state_0 & 2) != 0) {
                    return this.sort(arguments0Value_, arguments1Value_, this.sort_isJSObject_);
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
                int state_0 = this.state_0_;
                if (arguments0Value instanceof JSDynamicObject) {
                    Assumption sortArray_assumption0;
                    JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
                    if (!this.isTypedArrayImplementation && JSGuards.isJSFastArray(arguments0Value_) && Assumption.isValidAssumption(sortArray_assumption0 = this.getContext().getArrayPrototypeNoElementsAssumption())) {
                        this.sortArray_arrayToObjectArrayNode_ = super.insert(JSArrayToDenseObjectArrayNode.create(this.getContext()));
                        this.sortArray_arrayDeleteRangeNode_ = super.insert(JSArrayDeleteRangeNode.create(this.getContext(), true));
                        this.sortArray_assumption0_ = sortArray_assumption0;
                        this.state_0_ = state_0 |= 1;
                        lock.unlock();
                        hasLock = false;
                        JSDynamicObject jSDynamicObject = this.sortArray(arguments0Value_, arguments1Value, this.sortArray_arrayToObjectArrayNode_, this.sortArray_arrayDeleteRangeNode_);
                        return jSDynamicObject;
                    }
                }
                this.sort_isJSObject_ = ConditionProfile.createBinaryProfile();
                this.state_0_ = state_0 |= 2;
                lock.unlock();
                hasLock = false;
                Object object = this.sort(arguments0Value, arguments1Value, this.sort_isJSObject_);
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

        void removeSortArray_() {
            Lock lock = this.getLock();
            lock.lock();
            try {
                this.state_0_ &= 0xFFFFFFFE;
            }
            finally {
                lock.unlock();
            }
        }

        @Override
        public Introspection getIntrospectionData() {
            ArrayList<List<Cloneable>> cached;
            Object[] data = new Object[3];
            data[0] = 0;
            int state_0 = this.state_0_;
            Object[] s = new Object[3];
            s[0] = "sortArray";
            if ((state_0 & 1) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList<List<Cloneable>>();
                cached.add(Arrays.asList(this.sortArray_arrayToObjectArrayNode_, this.sortArray_arrayDeleteRangeNode_));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[1] = s;
            s = new Object[3];
            s[0] = "sort";
            if ((state_0 & 2) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList();
                cached.add(Arrays.asList(this.sort_isJSObject_));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[2] = s;
            return Introspection.Provider.create(data);
        }

        public static ArrayPrototypeBuiltins.JSArraySortNode create(JSContext context, JSBuiltin builtin, boolean isTypedArrayImplementation, JavaScriptNode[] arguments) {
            return new JSArraySortNodeGen(context, builtin, isTypedArrayImplementation, arguments);
        }
    }

    @GeneratedBy(value=ArrayPrototypeBuiltins.JSArrayFindIndexNode.class)
    public static final class JSArrayFindIndexNodeGen
    extends ArrayPrototypeBuiltins.JSArrayFindIndexNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @Node.Child
        private JavaScriptNode arguments2_;

        private JSArrayFindIndexNodeGen(JSContext context, JSBuiltin builtin, boolean isTypedArrayImplementation, boolean isLast, JavaScriptNode[] arguments) {
            super(context, builtin, isTypedArrayImplementation, isLast);
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
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            Object arguments2Value_ = this.arguments2_.execute(frameValue);
            return this.findIndex(arguments0Value_, arguments1Value_, arguments2Value_);
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
            s[0] = "findIndex";
            s[1] = (byte)1;
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static ArrayPrototypeBuiltins.JSArrayFindIndexNode create(JSContext context, JSBuiltin builtin, boolean isTypedArrayImplementation, boolean isLast, JavaScriptNode[] arguments) {
            return new JSArrayFindIndexNodeGen(context, builtin, isTypedArrayImplementation, isLast, arguments);
        }
    }

    @GeneratedBy(value=ArrayPrototypeBuiltins.JSArrayFindNode.class)
    public static final class JSArrayFindNodeGen
    extends ArrayPrototypeBuiltins.JSArrayFindNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @Node.Child
        private JavaScriptNode arguments2_;

        private JSArrayFindNodeGen(JSContext context, JSBuiltin builtin, boolean isTypedArrayImplementation, boolean isLast, JavaScriptNode[] arguments) {
            super(context, builtin, isTypedArrayImplementation, isLast);
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
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            Object arguments2Value_ = this.arguments2_.execute(frameValue);
            return this.find(arguments0Value_, arguments1Value_, arguments2Value_);
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
            s[0] = "find";
            s[1] = (byte)1;
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static ArrayPrototypeBuiltins.JSArrayFindNode create(JSContext context, JSBuiltin builtin, boolean isTypedArrayImplementation, boolean isLast, JavaScriptNode[] arguments) {
            return new JSArrayFindNodeGen(context, builtin, isTypedArrayImplementation, isLast, arguments);
        }
    }

    @GeneratedBy(value=ArrayPrototypeBuiltins.JSArrayFlatNode.class)
    public static final class JSArrayFlatNodeGen
    extends ArrayPrototypeBuiltins.JSArrayFlatNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private ArrayPrototypeBuiltins.FlattenIntoArrayNode flattenIntoArrayNode_;

        private JSArrayFlatNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
                return this.flat(arguments0Value_, arguments1Value_, this.flattenIntoArrayNode_);
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
                this.flattenIntoArrayNode_ = super.insert(ArrayPrototypeBuiltins.JSArrayFlatNode.createFlattenIntoArrayNode(this.getContext()));
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                Object object = this.flat(arguments0Value, arguments1Value, this.flattenIntoArrayNode_);
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
            s[0] = "flat";
            if (state_0 != 0) {
                s[1] = (byte)1;
                ArrayList<List<ArrayPrototypeBuiltins.FlattenIntoArrayNode>> cached = new ArrayList<List<ArrayPrototypeBuiltins.FlattenIntoArrayNode>>();
                cached.add(Arrays.asList(this.flattenIntoArrayNode_));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static ArrayPrototypeBuiltins.JSArrayFlatNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSArrayFlatNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=ArrayPrototypeBuiltins.JSArrayFlatMapNode.class)
    public static final class JSArrayFlatMapNodeGen
    extends ArrayPrototypeBuiltins.JSArrayFlatMapNode
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
        private ArrayPrototypeBuiltins.FlattenIntoArrayNode flattenIntoArrayNode_;

        private JSArrayFlatMapNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
                return this.flatMap(arguments0Value_, arguments1Value_, arguments2Value_, this.flattenIntoArrayNode_);
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
                this.flattenIntoArrayNode_ = super.insert(ArrayPrototypeBuiltins.JSArrayFlatMapNode.createFlattenIntoArrayNode(this.getContext()));
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                Object object = this.flatMap(arguments0Value, arguments1Value, arguments2Value, this.flattenIntoArrayNode_);
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
            s[0] = "flatMap";
            if (state_0 != 0) {
                s[1] = (byte)1;
                ArrayList<List<ArrayPrototypeBuiltins.FlattenIntoArrayNode>> cached = new ArrayList<List<ArrayPrototypeBuiltins.FlattenIntoArrayNode>>();
                cached.add(Arrays.asList(this.flattenIntoArrayNode_));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static ArrayPrototypeBuiltins.JSArrayFlatMapNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSArrayFlatMapNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=ArrayPrototypeBuiltins.FlattenIntoArrayNode.class)
    public static final class FlattenIntoArrayNodeGen
    extends ArrayPrototypeBuiltins.FlattenIntoArrayNode
    implements Introspection.Provider {
        private FlattenIntoArrayNodeGen(JSContext context, boolean withMapCallback) {
            super(context, withMapCallback);
        }

        @Override
        protected long executeLong(JSDynamicObject arg0Value, Object arg1Value, long arg2Value, long arg3Value, long arg4Value, Object arg5Value, Object arg6Value) {
            return this.flatten(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value);
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
            s[0] = "flatten";
            s[1] = (byte)1;
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static ArrayPrototypeBuiltins.FlattenIntoArrayNode create(JSContext context, boolean withMapCallback) {
            return new FlattenIntoArrayNodeGen(context, withMapCallback);
        }
    }

    @GeneratedBy(value=ArrayPrototypeBuiltins.JSArrayMapNode.class)
    public static final class JSArrayMapNodeGen
    extends ArrayPrototypeBuiltins.JSArrayMapNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @Node.Child
        private JavaScriptNode arguments2_;

        private JSArrayMapNodeGen(JSContext context, JSBuiltin builtin, boolean isTypedArrayImplementation, JavaScriptNode[] arguments) {
            super(context, builtin, isTypedArrayImplementation);
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
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            Object arguments2Value_ = this.arguments2_.execute(frameValue);
            return this.map(arguments0Value_, arguments1Value_, arguments2Value_);
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
            s[0] = "map";
            s[1] = (byte)1;
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static ArrayPrototypeBuiltins.JSArrayMapNode create(JSContext context, JSBuiltin builtin, boolean isTypedArrayImplementation, JavaScriptNode[] arguments) {
            return new JSArrayMapNodeGen(context, builtin, isTypedArrayImplementation, arguments);
        }
    }

    @GeneratedBy(value=ArrayPrototypeBuiltins.JSArraySomeNode.class)
    public static final class JSArraySomeNodeGen
    extends ArrayPrototypeBuiltins.JSArraySomeNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @Node.Child
        private JavaScriptNode arguments2_;

        private JSArraySomeNodeGen(JSContext context, JSBuiltin builtin, boolean isTypedArrayImplementation, JavaScriptNode[] arguments) {
            super(context, builtin, isTypedArrayImplementation);
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
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            Object arguments2Value_ = this.arguments2_.execute(frameValue);
            return this.some(arguments0Value_, arguments1Value_, arguments2Value_);
        }

        @Override
        public boolean executeBoolean(VirtualFrame frameValue) {
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            Object arguments2Value_ = this.arguments2_.execute(frameValue);
            return this.some(arguments0Value_, arguments1Value_, arguments2Value_);
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
            s[0] = "some";
            s[1] = (byte)1;
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static ArrayPrototypeBuiltins.JSArraySomeNode create(JSContext context, JSBuiltin builtin, boolean isTypedArrayImplementation, JavaScriptNode[] arguments) {
            return new JSArraySomeNodeGen(context, builtin, isTypedArrayImplementation, arguments);
        }
    }

    @GeneratedBy(value=ArrayPrototypeBuiltins.JSArrayForEachNode.class)
    public static final class JSArrayForEachNodeGen
    extends ArrayPrototypeBuiltins.JSArrayForEachNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @Node.Child
        private JavaScriptNode arguments2_;

        private JSArrayForEachNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            Object arguments2Value_ = this.arguments2_.execute(frameValue);
            return this.forEach(arguments0Value_, arguments1Value_, arguments2Value_);
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
            s[0] = "forEach";
            s[1] = (byte)1;
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static ArrayPrototypeBuiltins.JSArrayForEachNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSArrayForEachNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=ArrayPrototypeBuiltins.JSArrayFilterNode.class)
    public static final class JSArrayFilterNodeGen
    extends ArrayPrototypeBuiltins.JSArrayFilterNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @Node.Child
        private JavaScriptNode arguments2_;

        private JSArrayFilterNodeGen(JSContext context, JSBuiltin builtin, boolean isTypedArrayImplementation, JavaScriptNode[] arguments) {
            super(context, builtin, isTypedArrayImplementation);
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
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            Object arguments2Value_ = this.arguments2_.execute(frameValue);
            return this.filter(arguments0Value_, arguments1Value_, arguments2Value_);
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
            s[0] = "filter";
            s[1] = (byte)1;
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static ArrayPrototypeBuiltins.JSArrayFilterNode create(JSContext context, JSBuiltin builtin, boolean isTypedArrayImplementation, JavaScriptNode[] arguments) {
            return new JSArrayFilterNodeGen(context, builtin, isTypedArrayImplementation, arguments);
        }
    }

    @GeneratedBy(value=ArrayPrototypeBuiltins.JSArrayEveryNode.class)
    public static final class JSArrayEveryNodeGen
    extends ArrayPrototypeBuiltins.JSArrayEveryNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @Node.Child
        private JavaScriptNode arguments2_;

        private JSArrayEveryNodeGen(JSContext context, JSBuiltin builtin, boolean isTypedArrayImplementation, JavaScriptNode[] arguments) {
            super(context, builtin, isTypedArrayImplementation);
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
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            Object arguments2Value_ = this.arguments2_.execute(frameValue);
            return this.every(arguments0Value_, arguments1Value_, arguments2Value_);
        }

        @Override
        public boolean executeBoolean(VirtualFrame frameValue) {
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            Object arguments2Value_ = this.arguments2_.execute(frameValue);
            return this.every(arguments0Value_, arguments1Value_, arguments2Value_);
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
            s[0] = "every";
            s[1] = (byte)1;
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static ArrayPrototypeBuiltins.JSArrayEveryNode create(JSContext context, JSBuiltin builtin, boolean isTypedArrayImplementation, JavaScriptNode[] arguments) {
            return new JSArrayEveryNodeGen(context, builtin, isTypedArrayImplementation, arguments);
        }
    }

    @GeneratedBy(value=ArrayPrototypeBuiltins.JSArraySpliceNode.class)
    public static final class JSArraySpliceNodeGen
    extends ArrayPrototypeBuiltins.JSArraySpliceNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private ArrayPrototypeBuiltins.JSArraySpliceNode.SpliceJSArrayNode spliceJSArray_;

        private JSArraySpliceNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            if (state_0 != 0 && arguments1Value_ instanceof Object[]) {
                Object[] arguments1Value__ = (Object[])arguments1Value_;
                return this.splice(arguments0Value_, arguments1Value__, this.spliceJSArray_);
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
                if (arguments1Value instanceof Object[]) {
                    Object[] arguments1Value_ = (Object[])arguments1Value;
                    this.spliceJSArray_ = super.insert(SpliceJSArrayNodeGen.create(this.getContext()));
                    this.state_0_ = state_0 |= 1;
                    lock.unlock();
                    hasLock = false;
                    JSDynamicObject jSDynamicObject = this.splice(arguments0Value, arguments1Value_, this.spliceJSArray_);
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
            s[0] = "splice";
            if (state_0 != 0) {
                s[1] = (byte)1;
                ArrayList<List<ArrayPrototypeBuiltins.JSArraySpliceNode.SpliceJSArrayNode>> cached = new ArrayList<List<ArrayPrototypeBuiltins.JSArraySpliceNode.SpliceJSArrayNode>>();
                cached.add(Arrays.asList(this.spliceJSArray_));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static ArrayPrototypeBuiltins.JSArraySpliceNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSArraySpliceNodeGen(context, builtin, arguments);
        }

        @GeneratedBy(value=ArrayPrototypeBuiltins.JSArraySpliceNode.SpliceJSArrayNode.class)
        static final class SpliceJSArrayNodeGen
        extends ArrayPrototypeBuiltins.JSArraySpliceNode.SpliceJSArrayNode
        implements Introspection.Provider {
            @CompilerDirectives.CompilationFinal
            private volatile int state_0_;
            @CompilerDirectives.CompilationFinal
            private volatile int exclude_;
            @Node.Child
            private CachedData cached_cache;
            @Node.Child
            private GetPrototypeNode uncached_getPrototypeNode_;
            @CompilerDirectives.CompilationFinal
            private ConditionProfile uncached_arrayElementwise_;

            private SpliceJSArrayNodeGen(JSContext context) {
                super(context);
            }

            @Override
            @ExplodeLoop
            void execute(JSDynamicObject arg0Value, long arg1Value, long arg2Value, long arg3Value, long arg4Value, ScriptArray arg5Value, ArrayPrototypeBuiltins.JSArraySpliceNode arg6Value) {
                int state_0 = this.state_0_;
                if (state_0 != 0) {
                    if ((state_0 & 1) != 0) {
                        CachedData s0_ = this.cached_cache;
                        while (s0_ != null) {
                            if (s0_.cachedArrayType_.isInstance(arg5Value)) {
                                ArrayPrototypeBuiltins.JSArraySpliceNode.SpliceJSArrayNode.doCached(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, s0_.cachedArrayType_, s0_.getPrototypeNode_, s0_.arrayElementwise_);
                                return;
                            }
                            s0_ = s0_.next_;
                        }
                    }
                    if ((state_0 & 2) != 0) {
                        ArrayPrototypeBuiltins.JSArraySpliceNode.SpliceJSArrayNode.doUncached(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, this.uncached_getPrototypeNode_, this.uncached_arrayElementwise_);
                        return;
                    }
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private void executeAndSpecialize(JSDynamicObject arg0Value, long arg1Value, long arg2Value, long arg3Value, long arg4Value, ScriptArray arg5Value, ArrayPrototypeBuiltins.JSArraySpliceNode arg6Value) {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_0 = this.state_0_;
                    int exclude = this.exclude_;
                    if (exclude == 0) {
                        ScriptArray cachedArrayType__;
                        int count0_ = 0;
                        CachedData s0_ = this.cached_cache;
                        if ((state_0 & 1) != 0) {
                            while (s0_ != null && !s0_.cachedArrayType_.isInstance(arg5Value)) {
                                s0_ = s0_.next_;
                                ++count0_;
                            }
                        }
                        if (s0_ == null && (cachedArrayType__ = arg5Value).isInstance(arg5Value) && count0_ < 5) {
                            s0_ = super.insert(new CachedData(this.cached_cache));
                            s0_.cachedArrayType_ = cachedArrayType__;
                            s0_.getPrototypeNode_ = s0_.insertAccessor(GetPrototypeNode.create());
                            s0_.arrayElementwise_ = ConditionProfile.create();
                            VarHandle.storeStoreFence();
                            this.cached_cache = s0_;
                            this.state_0_ = state_0 |= 1;
                        }
                        if (s0_ != null) {
                            lock.unlock();
                            hasLock = false;
                            ArrayPrototypeBuiltins.JSArraySpliceNode.SpliceJSArrayNode.doCached(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, s0_.cachedArrayType_, s0_.getPrototypeNode_, s0_.arrayElementwise_);
                            return;
                        }
                    }
                    this.uncached_getPrototypeNode_ = super.insert(GetPrototypeNode.create());
                    this.uncached_arrayElementwise_ = ConditionProfile.create();
                    this.exclude_ = exclude |= 1;
                    this.cached_cache = null;
                    state_0 &= 0xFFFFFFFE;
                    this.state_0_ = state_0 |= 2;
                    lock.unlock();
                    hasLock = false;
                    ArrayPrototypeBuiltins.JSArraySpliceNode.SpliceJSArrayNode.doUncached(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, this.uncached_getPrototypeNode_, this.uncached_arrayElementwise_);
                    return;
                }
                finally {
                    if (hasLock) {
                        lock.unlock();
                    }
                }
            }

            @Override
            public NodeCost getCost() {
                CachedData s0_;
                int state_0 = this.state_0_;
                if (state_0 == 0) {
                    return NodeCost.UNINITIALIZED;
                }
                if ((state_0 & state_0 - 1) == 0 && ((s0_ = this.cached_cache) == null || s0_.next_ == null)) {
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
                    while (s0_ != null) {
                        cached.add(Arrays.asList(s0_.cachedArrayType_, s0_.getPrototypeNode_, s0_.arrayElementwise_));
                        s0_ = s0_.next_;
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
                    cached.add(Arrays.asList(this.uncached_getPrototypeNode_, this.uncached_arrayElementwise_));
                    s[2] = cached;
                } else {
                    s[1] = (byte)0;
                }
                data[2] = s;
                return Introspection.Provider.create(data);
            }

            public static ArrayPrototypeBuiltins.JSArraySpliceNode.SpliceJSArrayNode create(JSContext context) {
                return new SpliceJSArrayNodeGen(context);
            }

            @GeneratedBy(value=ArrayPrototypeBuiltins.JSArraySpliceNode.SpliceJSArrayNode.class)
            private static final class CachedData
            extends Node {
                @Node.Child
                CachedData next_;
                @CompilerDirectives.CompilationFinal
                ScriptArray cachedArrayType_;
                @Node.Child
                GetPrototypeNode getPrototypeNode_;
                @CompilerDirectives.CompilationFinal
                ConditionProfile arrayElementwise_;

                CachedData(CachedData next_) {
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

    @GeneratedBy(value=ArrayPrototypeBuiltins.JSArrayToLocaleStringNode.class)
    public static final class JSArrayToLocaleStringNodeGen
    extends ArrayPrototypeBuiltins.JSArrayToLocaleStringNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private JSToStringNode toStringNode_;

        private JSArrayToLocaleStringNodeGen(JSContext context, JSBuiltin builtin, boolean isTypedArrayImplementation, JavaScriptNode[] arguments) {
            super(context, builtin, isTypedArrayImplementation);
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
                return this.toLocaleString(frameValue, arguments0Value_, this.toStringNode_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(frameValue, arguments0Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.execute(frameValue);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private TruffleString executeAndSpecialize(VirtualFrame frameValue, Object arguments0Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                this.toStringNode_ = super.insert(JSToStringNode.create());
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                TruffleString truffleString = this.toLocaleString(frameValue, arguments0Value, this.toStringNode_);
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
            s[0] = "toLocaleString";
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

        public static ArrayPrototypeBuiltins.JSArrayToLocaleStringNode create(JSContext context, JSBuiltin builtin, boolean isTypedArrayImplementation, JavaScriptNode[] arguments) {
            return new JSArrayToLocaleStringNodeGen(context, builtin, isTypedArrayImplementation, arguments);
        }
    }

    @GeneratedBy(value=ArrayPrototypeBuiltins.JSArrayJoinNode.class)
    public static final class JSArrayJoinNodeGen
    extends ArrayPrototypeBuiltins.JSArrayJoinNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;

        private JSArrayJoinNodeGen(JSContext context, JSBuiltin builtin, boolean isTypedArrayImplementation, JavaScriptNode[] arguments) {
            super(context, builtin, isTypedArrayImplementation);
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
            return this.join(arguments0Value_, arguments1Value_);
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
            s[0] = "join";
            s[1] = (byte)1;
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static ArrayPrototypeBuiltins.JSArrayJoinNode create(JSContext context, JSBuiltin builtin, boolean isTypedArrayImplementation, JavaScriptNode[] arguments) {
            return new JSArrayJoinNodeGen(context, builtin, isTypedArrayImplementation, arguments);
        }
    }

    @GeneratedBy(value=ArrayPrototypeBuiltins.JSArrayIndexOfNode.class)
    public static final class JSArrayIndexOfNodeGen
    extends ArrayPrototypeBuiltins.JSArrayIndexOfNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @CompilerDirectives.CompilationFinal
        private int state_0_;

        private JSArrayIndexOfNodeGen(JSContext context, JSBuiltin builtin, boolean isTypedArrayImplementation, boolean isForward, JavaScriptNode[] arguments) {
            super(context, builtin, isTypedArrayImplementation, isForward);
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
            if (state_0 != 0 && arguments1Value_ instanceof Object[]) {
                Object[] arguments1Value__ = (Object[])arguments1Value_;
                return this.indexOf(arguments0Value_, arguments1Value__);
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
            if (arguments1Value instanceof Object[]) {
                Object[] arguments1Value_ = (Object[])arguments1Value;
                this.state_0_ = state_0 |= 1;
                return this.indexOf(arguments0Value, arguments1Value_);
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
            s[0] = "indexOf";
            s[1] = state_0 != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static ArrayPrototypeBuiltins.JSArrayIndexOfNode create(JSContext context, JSBuiltin builtin, boolean isTypedArrayImplementation, boolean isForward, JavaScriptNode[] arguments) {
            return new JSArrayIndexOfNodeGen(context, builtin, isTypedArrayImplementation, isForward, arguments);
        }
    }

    @GeneratedBy(value=ArrayPrototypeBuiltins.JSArrayConcatNode.class)
    public static final class JSArrayConcatNodeGen
    extends ArrayPrototypeBuiltins.JSArrayConcatNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @CompilerDirectives.CompilationFinal
        private int state_0_;

        private JSArrayConcatNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            if (state_0 != 0 && arguments1Value_ instanceof Object[]) {
                Object[] arguments1Value__ = (Object[])arguments1Value_;
                return this.concat(arguments0Value_, arguments1Value__);
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
            if (arguments1Value instanceof Object[]) {
                Object[] arguments1Value_ = (Object[])arguments1Value;
                this.state_0_ = state_0 |= 1;
                return this.concat(arguments0Value, arguments1Value_);
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
            s[0] = "concat";
            s[1] = state_0 != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static ArrayPrototypeBuiltins.JSArrayConcatNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSArrayConcatNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=ArrayPrototypeBuiltins.JSArrayToStringNode.class)
    public static final class JSArrayToStringNodeGen
    extends ArrayPrototypeBuiltins.JSArrayToStringNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;

        private JSArrayToStringNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            return this.toString(arguments0Value_);
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
            s[0] = "toString";
            s[1] = (byte)1;
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static ArrayPrototypeBuiltins.JSArrayToStringNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSArrayToStringNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=ArrayPrototypeBuiltins.JSArrayUnshiftNode.class)
    public static final class JSArrayUnshiftNodeGen
    extends ArrayPrototypeBuiltins.JSArrayUnshiftNode
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
        private UnshiftHolesData unshiftHoles_cache;

        private JSArrayUnshiftNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            super(context, builtin);
            this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
            this.arguments1_ = arguments != null && 1 < arguments.length ? arguments[1] : null;
        }

        @Override
        public JavaScriptNode[] getArguments() {
            return new JavaScriptNode[]{this.arguments0_, this.arguments1_};
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public Object execute(VirtualFrame frameValue) {
            int state_0 = this.state_0_;
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            if (state_0 != 0 && arguments1Value_ instanceof Object[]) {
                UnshiftHolesData s2_;
                Object[] arguments1Value__ = (Object[])arguments1Value_;
                if ((state_0 & 3) != 0 && arguments0Value_ instanceof JSDynamicObject) {
                    JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
                    if ((state_0 & 1) != 0 && this.isFastPath(arguments0Value__)) {
                        try {
                            return this.unshiftInt(arguments0Value__, arguments1Value__);
                        }
                        catch (UnexpectedResultException ex) {
                            CompilerDirectives.transferToInterpreterAndInvalidate();
                            Lock lock = this.getLock();
                            lock.lock();
                            try {
                                this.exclude_ |= 1;
                                this.state_0_ &= 0xFFFFFFFE;
                            }
                            finally {
                                lock.unlock();
                            }
                            return ex.getResult();
                        }
                    }
                    if ((state_0 & 2) != 0 && this.isFastPath(arguments0Value__)) {
                        return this.unshiftDouble(arguments0Value__, arguments1Value__);
                    }
                }
                if ((state_0 & 4) != 0 && (s2_ = this.unshiftHoles_cache) != null && !this.isFastPath(arguments0Value_)) {
                    return this.unshiftHoles(arguments0Value_, arguments1Value__, s2_.deletePropertyNode_, s2_.lastElementIndexNode_, s2_.firstElementIndexNode_);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
        }

        @Override
        public double executeDouble(VirtualFrame frameValue) throws UnexpectedResultException {
            int state_0 = this.state_0_;
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            if ((state_0 & 6) != 0 && arguments1Value_ instanceof Object[]) {
                UnshiftHolesData s2_;
                JSDynamicObject arguments0Value__;
                Object[] arguments1Value__ = (Object[])arguments1Value_;
                if ((state_0 & 2) != 0 && arguments0Value_ instanceof JSDynamicObject && this.isFastPath(arguments0Value__ = (JSDynamicObject)arguments0Value_)) {
                    return this.unshiftDouble(arguments0Value__, arguments1Value__);
                }
                if ((state_0 & 4) != 0 && (s2_ = this.unshiftHoles_cache) != null && !this.isFastPath(arguments0Value_)) {
                    return this.unshiftHoles(arguments0Value_, arguments1Value__, s2_.deletePropertyNode_, s2_.lastElementIndexNode_, s2_.firstElementIndexNode_);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return JSTypesGen.expectDouble(this.executeAndSpecialize(arguments0Value_, arguments1Value_));
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public int executeInt(VirtualFrame frameValue) throws UnexpectedResultException {
            int state_0 = this.state_0_;
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSDynamicObject) {
                JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
                if (arguments1Value_ instanceof Object[]) {
                    Object[] arguments1Value__ = (Object[])arguments1Value_;
                    if (this.isFastPath(arguments0Value__)) {
                        try {
                            return this.unshiftInt(arguments0Value__, arguments1Value__);
                        }
                        catch (UnexpectedResultException ex) {
                            CompilerDirectives.transferToInterpreterAndInvalidate();
                            Lock lock = this.getLock();
                            lock.lock();
                            try {
                                this.exclude_ |= 1;
                                this.state_0_ &= 0xFFFFFFFE;
                            }
                            finally {
                                lock.unlock();
                            }
                            return JSTypesGen.expectInteger(ex.getResult());
                        }
                    }
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return JSTypesGen.expectInteger(this.executeAndSpecialize(arguments0Value_, arguments1Value_));
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            int state_0 = this.state_0_;
            try {
                if ((state_0 & 6) == 0 && state_0 != 0) {
                    this.executeInt(frameValue);
                    return;
                }
                if ((state_0 & 1) == 0 && state_0 != 0) {
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
         * Loose catch block
         */
        private Object executeAndSpecialize(Object arguments0Value, Object arguments1Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                int exclude = this.exclude_;
                if (arguments1Value instanceof Object[]) {
                    Object[] arguments1Value_ = (Object[])arguments1Value;
                    if (arguments0Value instanceof JSDynamicObject) {
                        JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
                        if (exclude == 0 && this.isFastPath(arguments0Value_)) {
                            this.state_0_ = state_0 |= 1;
                            try {
                                lock.unlock();
                                hasLock = false;
                                Integer n = this.unshiftInt(arguments0Value_, arguments1Value_);
                                return n;
                            }
                            catch (UnexpectedResultException ex) {
                                CompilerDirectives.transferToInterpreterAndInvalidate();
                                lock.lock();
                                try {
                                    this.exclude_ |= 1;
                                    this.state_0_ &= 0xFFFFFFFE;
                                }
                                finally {
                                    lock.unlock();
                                }
                                Object object = ex.getResult();
                                if (hasLock) {
                                    lock.unlock();
                                }
                                return object;
                            }
                        }
                        if (this.isFastPath(arguments0Value_)) {
                            this.exclude_ = exclude |= 1;
                            state_0 &= 0xFFFFFFFE;
                            this.state_0_ = state_0 |= 2;
                            lock.unlock();
                            hasLock = false;
                            Double d = this.unshiftDouble(arguments0Value_, arguments1Value_);
                            return d;
                        }
                    }
                    if (!this.isFastPath(arguments0Value)) {
                        UnshiftHolesData s2_ = super.insert(new UnshiftHolesData());
                        s2_.deletePropertyNode_ = s2_.insertAccessor(DeletePropertyNode.create(true, this.getContext()));
                        s2_.lastElementIndexNode_ = s2_.insertAccessor(JSArrayLastElementIndexNode.create(this.getContext()));
                        s2_.firstElementIndexNode_ = s2_.insertAccessor(JSArrayFirstElementIndexNode.create(this.getContext()));
                        VarHandle.storeStoreFence();
                        this.unshiftHoles_cache = s2_;
                        this.state_0_ = state_0 |= 4;
                        lock.unlock();
                        hasLock = false;
                        Double d = this.unshiftHoles(arguments0Value, arguments1Value_, s2_.deletePropertyNode_, s2_.lastElementIndexNode_, s2_.firstElementIndexNode_);
                        return d;
                    }
                }
                throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_, this.arguments1_}, arguments0Value, arguments1Value);
                {
                    catch (Throwable throwable) {
                        throw throwable;
                    }
                }
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
            int exclude = this.exclude_;
            Object[] s = new Object[3];
            s[0] = "unshiftInt";
            s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : (exclude != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0));
            data[1] = s;
            s = new Object[3];
            s[0] = "unshiftDouble";
            s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[2] = s;
            s = new Object[3];
            s[0] = "unshiftHoles";
            if ((state_0 & 4) != 0) {
                s[1] = (byte)1;
                ArrayList<List<JavaScriptBaseNode>> cached = new ArrayList<List<JavaScriptBaseNode>>();
                UnshiftHolesData s2_ = this.unshiftHoles_cache;
                if (s2_ != null) {
                    cached.add(Arrays.asList(s2_.deletePropertyNode_, s2_.lastElementIndexNode_, s2_.firstElementIndexNode_));
                }
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[3] = s;
            return Introspection.Provider.create(data);
        }

        public static ArrayPrototypeBuiltins.JSArrayUnshiftNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSArrayUnshiftNodeGen(context, builtin, arguments);
        }

        @GeneratedBy(value=ArrayPrototypeBuiltins.JSArrayUnshiftNode.class)
        private static final class UnshiftHolesData
        extends Node {
            @Node.Child
            DeletePropertyNode deletePropertyNode_;
            @Node.Child
            JSArrayLastElementIndexNode lastElementIndexNode_;
            @Node.Child
            JSArrayFirstElementIndexNode firstElementIndexNode_;

            UnshiftHolesData() {
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

    @GeneratedBy(value=ArrayPrototypeBuiltins.JSArrayShiftNode.class)
    public static final class JSArrayShiftNodeGen
    extends ArrayPrototypeBuiltins.JSArrayShiftNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private IsArrayNode isArray;
        @Node.Child
        private TestArrayNode hasHoles;
        @Node.Child
        private TestArrayNode isSealed;
        @CompilerDirectives.CompilationFinal
        private ConditionProfile lengthIsZero;
        @Node.Child
        private DeletePropertyNode deleteProperty;
        @CompilerDirectives.CompilationFinal
        private ValueProfile shiftWithoutHoles_arrayTypeProfile_;
        @CompilerDirectives.CompilationFinal
        private ConditionProfile shiftWithoutHoles_lengthLargerOne_;
        @Node.Child
        private JSArrayFirstElementIndexNode shiftSparse_firstElementIndexNode_;
        @Node.Child
        private JSArrayLastElementIndexNode shiftSparse_lastElementIndexNode_;
        @Node.Child
        private InteropLibrary shiftForeign_arrays_;

        private JSArrayShiftNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            if ((state_0 & 7) != 0 && arguments0Value_ instanceof JSDynamicObject) {
                JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
                if ((state_0 & 1) != 0 && ArrayPrototypeBuiltins.JSArrayShiftNode.isArrayWithoutHolesAndNotSealed(arguments0Value__, this.isArray, this.hasHoles, this.isSealed)) {
                    return this.shiftWithoutHoles(arguments0Value__, this.isArray, this.hasHoles, this.isSealed, this.shiftWithoutHoles_arrayTypeProfile_, this.lengthIsZero, this.shiftWithoutHoles_lengthLargerOne_);
                }
                if ((state_0 & 2) != 0 && ArrayPrototypeBuiltins.JSArrayShiftNode.isArrayWithHolesOrSealed(arguments0Value__, this.isArray, this.hasHoles, this.isSealed)) {
                    return this.shiftWithHoles(arguments0Value__, this.isArray, this.hasHoles, this.isSealed, this.deleteProperty, this.lengthIsZero);
                }
                if ((state_0 & 4) != 0 && this.isArray.execute(arguments0Value__) && ArrayPrototypeBuiltins.JSArrayShiftNode.isSparseArray(arguments0Value__)) {
                    return this.shiftSparse(arguments0Value__, this.isArray, this.deleteProperty, this.lengthIsZero, this.shiftSparse_firstElementIndexNode_, this.shiftSparse_lastElementIndexNode_);
                }
            }
            if ((state_0 & 0x18) != 0) {
                if ((state_0 & 8) != 0 && !JSGuards.isJSArray(arguments0Value_) && !JSGuards.isForeignObject(arguments0Value_)) {
                    return this.shiftGeneric(arguments0Value_, this.deleteProperty, this.lengthIsZero);
                }
                if ((state_0 & 0x10) != 0 && JSGuards.isForeignObject(arguments0Value_)) {
                    return this.shiftForeign(arguments0Value_, this.shiftForeign_arrays_, this.lengthIsZero);
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
                Object object;
                int state_0 = this.state_0_;
                if (arguments0Value instanceof JSDynamicObject) {
                    IsArrayNode shiftSparse_isArrayNode__;
                    TestArrayNode shiftWithHoles_isSealedNode__;
                    TestArrayNode shiftWithHoles_hasHolesNode__;
                    Object shiftWithHoles_isArrayNode__;
                    TestArrayNode shiftWithoutHoles_isSealedNode__;
                    TestArrayNode shiftWithoutHoles_hasHolesNode__;
                    Object shiftWithoutHoles_isArrayNode__;
                    JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
                    boolean ShiftWithoutHoles_duplicateFound_ = false;
                    if ((state_0 & 1) != 0 && ArrayPrototypeBuiltins.JSArrayShiftNode.isArrayWithoutHolesAndNotSealed(arguments0Value_, this.isArray, this.hasHoles, this.isSealed)) {
                        ShiftWithoutHoles_duplicateFound_ = true;
                    }
                    if (!ShiftWithoutHoles_duplicateFound_ && ArrayPrototypeBuiltins.JSArrayShiftNode.isArrayWithoutHolesAndNotSealed(arguments0Value_, (IsArrayNode)(shiftWithoutHoles_isArrayNode__ = super.insert(this.isArray == null ? IsArrayNode.createIsArray() : this.isArray)), shiftWithoutHoles_hasHolesNode__ = super.insert(this.hasHoles == null ? TestArrayNode.createHasHoles() : this.hasHoles), shiftWithoutHoles_isSealedNode__ = super.insert(this.isSealed == null ? TestArrayNode.createIsSealed() : this.isSealed)) && (state_0 & 1) == 0) {
                        if (this.isArray == null) {
                            IsArrayNode shiftWithoutHoles_isArrayNode___check = (IsArrayNode)super.insert(shiftWithoutHoles_isArrayNode__);
                            if (shiftWithoutHoles_isArrayNode___check == null) {
                                throw new AssertionError((Object)"Specialization 'shiftWithoutHoles(JSDynamicObject, IsArrayNode, TestArrayNode, TestArrayNode, ValueProfile, ConditionProfile, ConditionProfile)' contains a shared cache with name 'isArrayNode' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state.");
                            }
                            this.isArray = shiftWithoutHoles_isArrayNode___check;
                        }
                        if (this.hasHoles == null) {
                            TestArrayNode shiftWithoutHoles_hasHolesNode___check = super.insert(shiftWithoutHoles_hasHolesNode__);
                            if (shiftWithoutHoles_hasHolesNode___check == null) {
                                throw new AssertionError((Object)"Specialization 'shiftWithoutHoles(JSDynamicObject, IsArrayNode, TestArrayNode, TestArrayNode, ValueProfile, ConditionProfile, ConditionProfile)' contains a shared cache with name 'hasHolesNode' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state.");
                            }
                            this.hasHoles = shiftWithoutHoles_hasHolesNode___check;
                        }
                        if (this.isSealed == null) {
                            TestArrayNode shiftWithoutHoles_isSealedNode___check = super.insert(shiftWithoutHoles_isSealedNode__);
                            if (shiftWithoutHoles_isSealedNode___check == null) {
                                throw new AssertionError((Object)"Specialization 'shiftWithoutHoles(JSDynamicObject, IsArrayNode, TestArrayNode, TestArrayNode, ValueProfile, ConditionProfile, ConditionProfile)' contains a shared cache with name 'isSealedNode' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state.");
                            }
                            this.isSealed = shiftWithoutHoles_isSealedNode___check;
                        }
                        this.shiftWithoutHoles_arrayTypeProfile_ = ValueProfile.createClassProfile();
                        this.lengthIsZero = this.lengthIsZero == null ? ConditionProfile.createBinaryProfile() : this.lengthIsZero;
                        this.shiftWithoutHoles_lengthLargerOne_ = ConditionProfile.createBinaryProfile();
                        this.state_0_ = state_0 |= 1;
                        ShiftWithoutHoles_duplicateFound_ = true;
                    }
                    if (ShiftWithoutHoles_duplicateFound_) {
                        lock.unlock();
                        hasLock = false;
                        shiftWithoutHoles_isArrayNode__ = this.shiftWithoutHoles(arguments0Value_, this.isArray, this.hasHoles, this.isSealed, this.shiftWithoutHoles_arrayTypeProfile_, this.lengthIsZero, this.shiftWithoutHoles_lengthLargerOne_);
                        return shiftWithoutHoles_isArrayNode__;
                    }
                    boolean ShiftWithHoles_duplicateFound_ = false;
                    if ((state_0 & 2) != 0 && ArrayPrototypeBuiltins.JSArrayShiftNode.isArrayWithHolesOrSealed(arguments0Value_, this.isArray, this.hasHoles, this.isSealed)) {
                        ShiftWithHoles_duplicateFound_ = true;
                    }
                    if (!ShiftWithHoles_duplicateFound_ && ArrayPrototypeBuiltins.JSArrayShiftNode.isArrayWithHolesOrSealed(arguments0Value_, (IsArrayNode)(shiftWithHoles_isArrayNode__ = super.insert(this.isArray == null ? IsArrayNode.createIsArray() : this.isArray)), shiftWithHoles_hasHolesNode__ = super.insert(this.hasHoles == null ? TestArrayNode.createHasHoles() : this.hasHoles), shiftWithHoles_isSealedNode__ = super.insert(this.isSealed == null ? TestArrayNode.createIsSealed() : this.isSealed)) && (state_0 & 2) == 0) {
                        if (this.isArray == null) {
                            IsArrayNode shiftWithHoles_isArrayNode___check = (IsArrayNode)super.insert(shiftWithHoles_isArrayNode__);
                            if (shiftWithHoles_isArrayNode___check == null) {
                                throw new AssertionError((Object)"Specialization 'shiftWithHoles(JSDynamicObject, IsArrayNode, TestArrayNode, TestArrayNode, DeletePropertyNode, ConditionProfile)' contains a shared cache with name 'isArrayNode' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state.");
                            }
                            this.isArray = shiftWithHoles_isArrayNode___check;
                        }
                        if (this.hasHoles == null) {
                            TestArrayNode shiftWithHoles_hasHolesNode___check = super.insert(shiftWithHoles_hasHolesNode__);
                            if (shiftWithHoles_hasHolesNode___check == null) {
                                throw new AssertionError((Object)"Specialization 'shiftWithHoles(JSDynamicObject, IsArrayNode, TestArrayNode, TestArrayNode, DeletePropertyNode, ConditionProfile)' contains a shared cache with name 'hasHolesNode' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state.");
                            }
                            this.hasHoles = shiftWithHoles_hasHolesNode___check;
                        }
                        if (this.isSealed == null) {
                            TestArrayNode shiftWithHoles_isSealedNode___check = super.insert(shiftWithHoles_isSealedNode__);
                            if (shiftWithHoles_isSealedNode___check == null) {
                                throw new AssertionError((Object)"Specialization 'shiftWithHoles(JSDynamicObject, IsArrayNode, TestArrayNode, TestArrayNode, DeletePropertyNode, ConditionProfile)' contains a shared cache with name 'isSealedNode' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state.");
                            }
                            this.isSealed = shiftWithHoles_isSealedNode___check;
                        }
                        this.deleteProperty = super.insert(this.deleteProperty == null ? DeletePropertyNode.create(true, this.getContext()) : this.deleteProperty);
                        this.lengthIsZero = this.lengthIsZero == null ? ConditionProfile.createBinaryProfile() : this.lengthIsZero;
                        this.state_0_ = state_0 |= 2;
                        ShiftWithHoles_duplicateFound_ = true;
                    }
                    if (ShiftWithHoles_duplicateFound_) {
                        lock.unlock();
                        hasLock = false;
                        shiftWithHoles_isArrayNode__ = this.shiftWithHoles(arguments0Value_, this.isArray, this.hasHoles, this.isSealed, this.deleteProperty, this.lengthIsZero);
                        return shiftWithHoles_isArrayNode__;
                    }
                    boolean ShiftSparse_duplicateFound_ = false;
                    if ((state_0 & 4) != 0 && this.isArray.execute(arguments0Value_) && ArrayPrototypeBuiltins.JSArrayShiftNode.isSparseArray(arguments0Value_)) {
                        ShiftSparse_duplicateFound_ = true;
                    }
                    if (!ShiftSparse_duplicateFound_ && (shiftSparse_isArrayNode__ = super.insert(this.isArray == null ? IsArrayNode.createIsArray() : this.isArray)).execute(arguments0Value_) && ArrayPrototypeBuiltins.JSArrayShiftNode.isSparseArray(arguments0Value_) && (state_0 & 4) == 0) {
                        if (this.isArray == null) {
                            IsArrayNode shiftSparse_isArrayNode___check = super.insert(shiftSparse_isArrayNode__);
                            if (shiftSparse_isArrayNode___check == null) {
                                throw new AssertionError((Object)"Specialization 'shiftSparse(JSDynamicObject, IsArrayNode, DeletePropertyNode, ConditionProfile, JSArrayFirstElementIndexNode, JSArrayLastElementIndexNode)' contains a shared cache with name 'isArrayNode' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state.");
                            }
                            this.isArray = shiftSparse_isArrayNode___check;
                        }
                        this.deleteProperty = super.insert(this.deleteProperty == null ? DeletePropertyNode.create(true, this.getContext()) : this.deleteProperty);
                        this.lengthIsZero = this.lengthIsZero == null ? ConditionProfile.createBinaryProfile() : this.lengthIsZero;
                        this.shiftSparse_firstElementIndexNode_ = super.insert(JSArrayFirstElementIndexNode.create(this.getContext()));
                        this.shiftSparse_lastElementIndexNode_ = super.insert(JSArrayLastElementIndexNode.create(this.getContext()));
                        this.state_0_ = state_0 |= 4;
                        ShiftSparse_duplicateFound_ = true;
                    }
                    if (ShiftSparse_duplicateFound_) {
                        lock.unlock();
                        hasLock = false;
                        Object object2 = this.shiftSparse(arguments0Value_, this.isArray, this.deleteProperty, this.lengthIsZero, this.shiftSparse_firstElementIndexNode_, this.shiftSparse_lastElementIndexNode_);
                        return object2;
                    }
                }
                if (!JSGuards.isJSArray(arguments0Value) && !JSGuards.isForeignObject(arguments0Value)) {
                    this.deleteProperty = super.insert(this.deleteProperty == null ? DeletePropertyNode.create(true, this.getContext()) : this.deleteProperty);
                    this.lengthIsZero = this.lengthIsZero == null ? ConditionProfile.createBinaryProfile() : this.lengthIsZero;
                    this.state_0_ = state_0 |= 8;
                    lock.unlock();
                    hasLock = false;
                    object = this.shiftGeneric(arguments0Value, this.deleteProperty, this.lengthIsZero);
                    return object;
                }
                if (JSGuards.isForeignObject(arguments0Value)) {
                    this.shiftForeign_arrays_ = super.insert(INTEROP_LIBRARY_.createDispatched(5));
                    this.lengthIsZero = this.lengthIsZero == null ? ConditionProfile.createBinaryProfile() : this.lengthIsZero;
                    this.state_0_ = state_0 |= 0x10;
                    lock.unlock();
                    hasLock = false;
                    object = this.shiftForeign(arguments0Value, this.shiftForeign_arrays_, this.lengthIsZero);
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
            ArrayList<List<Cloneable>> cached;
            Object[] data = new Object[6];
            data[0] = 0;
            int state_0 = this.state_0_;
            Object[] s = new Object[3];
            s[0] = "shiftWithoutHoles";
            if ((state_0 & 1) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList<List<Cloneable>>();
                cached.add(Arrays.asList(this.isArray, this.hasHoles, this.isSealed, this.shiftWithoutHoles_arrayTypeProfile_, this.lengthIsZero, this.shiftWithoutHoles_lengthLargerOne_));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[1] = s;
            s = new Object[3];
            s[0] = "shiftWithHoles";
            if ((state_0 & 2) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList();
                cached.add(Arrays.asList(this.isArray, this.hasHoles, this.isSealed, this.deleteProperty, this.lengthIsZero));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[2] = s;
            s = new Object[3];
            s[0] = "shiftSparse";
            if ((state_0 & 4) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList();
                cached.add(Arrays.asList(this.isArray, this.deleteProperty, this.lengthIsZero, this.shiftSparse_firstElementIndexNode_, this.shiftSparse_lastElementIndexNode_));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[3] = s;
            s = new Object[3];
            s[0] = "shiftGeneric";
            if ((state_0 & 8) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList();
                cached.add(Arrays.asList(this.deleteProperty, this.lengthIsZero));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[4] = s;
            s = new Object[3];
            s[0] = "shiftForeign";
            if ((state_0 & 0x10) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList();
                cached.add(Arrays.asList(this.shiftForeign_arrays_, this.lengthIsZero));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[5] = s;
            return Introspection.Provider.create(data);
        }

        public static ArrayPrototypeBuiltins.JSArrayShiftNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSArrayShiftNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=ArrayPrototypeBuiltins.JSArraySliceNode.class)
    public static final class JSArraySliceNodeGen
    extends ArrayPrototypeBuiltins.JSArraySliceNode
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
        private JSToIntegerAsLongNode toIntegerAsLong_;

        private JSArraySliceNodeGen(JSContext context, JSBuiltin builtin, boolean isTypedArrayImplementation, JavaScriptNode[] arguments) {
            super(context, builtin, isTypedArrayImplementation);
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
                return this.sliceGeneric(arguments0Value_, arguments1Value_, arguments2Value_, this.toIntegerAsLong_);
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
                this.toIntegerAsLong_ = super.insert(JSToIntegerAsLongNode.create());
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                Object object = this.sliceGeneric(arguments0Value, arguments1Value, arguments2Value, this.toIntegerAsLong_);
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
            s[0] = "sliceGeneric";
            if (state_0 != 0) {
                s[1] = (byte)1;
                ArrayList<List<JSToIntegerAsLongNode>> cached = new ArrayList<List<JSToIntegerAsLongNode>>();
                cached.add(Arrays.asList(this.toIntegerAsLong_));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static ArrayPrototypeBuiltins.JSArraySliceNode create(JSContext context, JSBuiltin builtin, boolean isTypedArrayImplementation, JavaScriptNode[] arguments) {
            return new JSArraySliceNodeGen(context, builtin, isTypedArrayImplementation, arguments);
        }
    }

    @GeneratedBy(value=ArrayPrototypeBuiltins.DeleteAndSetLengthNode.class)
    protected static final class DeleteAndSetLengthNodeGen
    extends ArrayPrototypeBuiltins.DeleteAndSetLengthNode
    implements Introspection.Provider {
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @CompilerDirectives.CompilationFinal
        private volatile int exclude_;
        @Node.Child
        private ArrayLengthNode.ArrayLengthWriteNode setArrayLength_arrayLengthWriteNode_;
        @Node.Child
        private DeletePropertyNode setIntLength_deletePropertyNode_;
        @Node.Child
        private WritePropertyNode setIntLength_setLengthProperty_;
        @Node.Child
        private SetLengthData setLength_cache;
        @Node.Child
        private InteropLibrary foreignArray_arrays_;

        private DeleteAndSetLengthNodeGen(JSContext context) {
            super(context);
        }

        @Override
        public void executeVoid(Object arg0Value, long arg1Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
                if ((state_0 & 7) != 0 && arg0Value instanceof JSDynamicObject) {
                    SetLengthData s2_;
                    JSDynamicObject arg0Value_ = (JSDynamicObject)arg0Value;
                    if ((state_0 & 1) != 0 && ArrayPrototypeBuiltins.DeleteAndSetLengthNode.isArray(arg0Value_) && JSRuntime.longIsRepresentableAsInt(arg1Value)) {
                        ArrayPrototypeBuiltins.DeleteAndSetLengthNode.setArrayLength(arg0Value_, arg1Value, this.setArrayLength_arrayLengthWriteNode_);
                        return;
                    }
                    if ((state_0 & 2) != 0 && JSGuards.isJSObject(arg0Value_) && JSRuntime.longIsRepresentableAsInt(arg1Value)) {
                        ArrayPrototypeBuiltins.DeleteAndSetLengthNode.setIntLength(arg0Value_, arg1Value, this.setIntLength_deletePropertyNode_, this.setIntLength_setLengthProperty_);
                        return;
                    }
                    if ((state_0 & 4) != 0 && (s2_ = this.setLength_cache) != null && JSGuards.isJSObject(arg0Value_)) {
                        ArrayPrototypeBuiltins.DeleteAndSetLengthNode.setLength(arg0Value_, arg1Value, s2_.deletePropertyNode_, s2_.setLengthProperty_, s2_.indexInIntRangeCondition_);
                        return;
                    }
                }
                if ((state_0 & 8) != 0 && !JSGuards.isJSObject(arg0Value)) {
                    ArrayPrototypeBuiltins.DeleteAndSetLengthNode.foreignArray(arg0Value, arg1Value, this.foreignArray_arrays_);
                    return;
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.executeAndSpecialize(arg0Value, arg1Value);
        }

        private void executeAndSpecialize(Object arg0Value, long arg1Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                int exclude = this.exclude_;
                if (arg0Value instanceof JSDynamicObject) {
                    JSDynamicObject arg0Value_ = (JSDynamicObject)arg0Value;
                    if (ArrayPrototypeBuiltins.DeleteAndSetLengthNode.isArray(arg0Value_) && JSRuntime.longIsRepresentableAsInt(arg1Value)) {
                        this.setArrayLength_arrayLengthWriteNode_ = super.insert(ArrayPrototypeBuiltins.DeleteAndSetLengthNode.createArrayLengthWriteNode());
                        this.state_0_ = state_0 |= 1;
                        lock.unlock();
                        hasLock = false;
                        ArrayPrototypeBuiltins.DeleteAndSetLengthNode.setArrayLength(arg0Value_, arg1Value, this.setArrayLength_arrayLengthWriteNode_);
                        return;
                    }
                    if (exclude == 0 && JSGuards.isJSObject(arg0Value_) && JSRuntime.longIsRepresentableAsInt(arg1Value)) {
                        this.setIntLength_deletePropertyNode_ = super.insert(DeletePropertyNode.create(true, this.context));
                        this.setIntLength_setLengthProperty_ = super.insert(this.createWritePropertyNode());
                        this.state_0_ = state_0 |= 2;
                        lock.unlock();
                        hasLock = false;
                        ArrayPrototypeBuiltins.DeleteAndSetLengthNode.setIntLength(arg0Value_, arg1Value, this.setIntLength_deletePropertyNode_, this.setIntLength_setLengthProperty_);
                        return;
                    }
                    if (JSGuards.isJSObject(arg0Value_)) {
                        SetLengthData s2_ = super.insert(new SetLengthData());
                        s2_.deletePropertyNode_ = s2_.insertAccessor(DeletePropertyNode.create(true, this.context));
                        s2_.setLengthProperty_ = s2_.insertAccessor(this.createWritePropertyNode());
                        s2_.indexInIntRangeCondition_ = ConditionProfile.createBinaryProfile();
                        VarHandle.storeStoreFence();
                        this.setLength_cache = s2_;
                        this.exclude_ = exclude |= 1;
                        state_0 &= 0xFFFFFFFD;
                        this.state_0_ = state_0 |= 4;
                        lock.unlock();
                        hasLock = false;
                        ArrayPrototypeBuiltins.DeleteAndSetLengthNode.setLength(arg0Value_, arg1Value, s2_.deletePropertyNode_, s2_.setLengthProperty_, s2_.indexInIntRangeCondition_);
                        return;
                    }
                }
                if (!JSGuards.isJSObject(arg0Value)) {
                    this.foreignArray_arrays_ = super.insert(INTEROP_LIBRARY_.createDispatched(5));
                    this.state_0_ = state_0 |= 8;
                    lock.unlock();
                    hasLock = false;
                    ArrayPrototypeBuiltins.DeleteAndSetLengthNode.foreignArray(arg0Value, arg1Value, this.foreignArray_arrays_);
                    return;
                }
                throw new UnsupportedSpecializationException(this, new Node[]{null, null}, arg0Value, arg1Value);
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
            Object[] data = new Object[5];
            data[0] = 0;
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            Object[] s = new Object[3];
            s[0] = "setArrayLength";
            if ((state_0 & 1) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList<List<Cloneable>>();
                cached.add(Arrays.asList(this.setArrayLength_arrayLengthWriteNode_));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[1] = s;
            s = new Object[3];
            s[0] = "setIntLength";
            if ((state_0 & 2) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList();
                cached.add(Arrays.asList(this.setIntLength_deletePropertyNode_, this.setIntLength_setLengthProperty_));
                s[2] = cached;
            } else {
                s[1] = exclude != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0);
            }
            data[2] = s;
            s = new Object[3];
            s[0] = "setLength";
            if ((state_0 & 4) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList();
                SetLengthData s2_ = this.setLength_cache;
                if (s2_ != null) {
                    cached.add(Arrays.asList(s2_.deletePropertyNode_, s2_.setLengthProperty_, s2_.indexInIntRangeCondition_));
                }
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[3] = s;
            s = new Object[3];
            s[0] = "foreignArray";
            if ((state_0 & 8) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList();
                cached.add(Arrays.asList(this.foreignArray_arrays_));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[4] = s;
            return Introspection.Provider.create(data);
        }

        public static ArrayPrototypeBuiltins.DeleteAndSetLengthNode create(JSContext context) {
            return new DeleteAndSetLengthNodeGen(context);
        }

        @GeneratedBy(value=ArrayPrototypeBuiltins.DeleteAndSetLengthNode.class)
        private static final class SetLengthData
        extends Node {
            @Node.Child
            DeletePropertyNode deletePropertyNode_;
            @Node.Child
            WritePropertyNode setLengthProperty_;
            @CompilerDirectives.CompilationFinal
            ConditionProfile indexInIntRangeCondition_;

            SetLengthData() {
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

    @GeneratedBy(value=ArrayPrototypeBuiltins.JSArrayPopNode.class)
    public static final class JSArrayPopNodeGen
    extends ArrayPrototypeBuiltins.JSArrayPopNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private ArrayPrototypeBuiltins.DeleteAndSetLengthNode deleteAndSetLength_;
        @CompilerDirectives.CompilationFinal
        private ConditionProfile lengthIsZero_;

        private JSArrayPopNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
                return this.popGeneric(arguments0Value_, this.deleteAndSetLength_, this.lengthIsZero_);
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
                this.deleteAndSetLength_ = super.insert(ArrayPrototypeBuiltins.DeleteAndSetLengthNode.create(this.getContext()));
                this.lengthIsZero_ = ConditionProfile.createBinaryProfile();
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                Object object = this.popGeneric(arguments0Value, this.deleteAndSetLength_, this.lengthIsZero_);
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
            s[0] = "popGeneric";
            if (state_0 != 0) {
                s[1] = (byte)1;
                ArrayList<List<Cloneable>> cached = new ArrayList<List<Cloneable>>();
                cached.add(Arrays.asList(this.deleteAndSetLength_, this.lengthIsZero_));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static ArrayPrototypeBuiltins.JSArrayPopNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSArrayPopNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=ArrayPrototypeBuiltins.JSArrayPushNode.class)
    public static final class JSArrayPushNodeGen
    extends ArrayPrototypeBuiltins.JSArrayPushNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @CompilerDirectives.CompilationFinal
        private int state_0_;
        @CompilerDirectives.CompilationFinal
        private int exclude_;

        private JSArrayPushNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            super(context, builtin);
            this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
            this.arguments1_ = arguments != null && 1 < arguments.length ? arguments[1] : null;
        }

        @Override
        public JavaScriptNode[] getArguments() {
            return new JavaScriptNode[]{this.arguments0_, this.arguments1_};
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public Object execute(VirtualFrame frameValue) {
            int state_0 = this.state_0_;
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            if (state_0 != 0 && arguments1Value_ instanceof Object[]) {
                Object[] arguments1Value__ = (Object[])arguments1Value_;
                if ((state_0 & 0x1F) != 0 && arguments0Value_ instanceof JSDynamicObject) {
                    JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
                    if ((state_0 & 1) != 0 && JSGuards.isJSArray(arguments0Value__) && arguments1Value__.length == 0) {
                        return this.pushArrayNone(arguments0Value__, arguments1Value__);
                    }
                    if ((state_0 & 2) != 0 && JSGuards.isJSArray(arguments0Value__) && arguments1Value__.length == 1) {
                        try {
                            return this.pushArraySingle(arguments0Value__, arguments1Value__);
                        }
                        catch (SlowPathException ex) {
                            CompilerDirectives.transferToInterpreterAndInvalidate();
                            Lock lock = this.getLock();
                            lock.lock();
                            try {
                                this.exclude_ |= 1;
                                this.state_0_ &= 0xFFFFFFFD;
                            }
                            finally {
                                lock.unlock();
                            }
                            return this.executeAndSpecialize(arguments0Value__, arguments1Value__);
                        }
                    }
                    if ((state_0 & 4) != 0 && JSGuards.isJSArray(arguments0Value__) && arguments1Value__.length == 1) {
                        return this.pushArraySingleLong(arguments0Value__, arguments1Value__);
                    }
                    if ((state_0 & 8) != 0 && JSGuards.isJSArray(arguments0Value__) && arguments1Value__.length >= 2) {
                        try {
                            return this.pushArrayAll(arguments0Value__, arguments1Value__);
                        }
                        catch (SlowPathException ex) {
                            CompilerDirectives.transferToInterpreterAndInvalidate();
                            Lock lock = this.getLock();
                            lock.lock();
                            try {
                                this.exclude_ |= 2;
                                this.state_0_ &= 0xFFFFFFF7;
                            }
                            finally {
                                lock.unlock();
                            }
                            return this.executeAndSpecialize(arguments0Value__, arguments1Value__);
                        }
                    }
                    if ((state_0 & 0x10) != 0 && JSGuards.isJSArray(arguments0Value__) && arguments1Value__.length >= 2) {
                        return this.pushArrayAllLong(arguments0Value__, arguments1Value__);
                    }
                }
                if ((state_0 & 0x20) != 0 && !JSGuards.isJSArray(arguments0Value_)) {
                    return this.pushProperty(arguments0Value_, arguments1Value__);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
        }

        @Override
        public double executeDouble(VirtualFrame frameValue) throws UnexpectedResultException {
            int state_0 = this.state_0_;
            if ((state_0 & 1) != 0) {
                return JSTypesGen.expectDouble(this.execute(frameValue));
            }
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            if ((state_0 & 0x34) != 0 && arguments1Value_ instanceof Object[]) {
                Object[] arguments1Value__ = (Object[])arguments1Value_;
                if ((state_0 & 0x14) != 0 && arguments0Value_ instanceof JSDynamicObject) {
                    JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
                    if ((state_0 & 4) != 0 && JSGuards.isJSArray(arguments0Value__) && arguments1Value__.length == 1) {
                        return this.pushArraySingleLong(arguments0Value__, arguments1Value__);
                    }
                    if ((state_0 & 0x10) != 0 && JSGuards.isJSArray(arguments0Value__) && arguments1Value__.length >= 2) {
                        return this.pushArrayAllLong(arguments0Value__, arguments1Value__);
                    }
                }
                if ((state_0 & 0x20) != 0 && !JSGuards.isJSArray(arguments0Value_)) {
                    return this.pushProperty(arguments0Value_, arguments1Value__);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return JSTypesGen.expectDouble(this.executeAndSpecialize(arguments0Value_, arguments1Value_));
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public int executeInt(VirtualFrame frameValue) throws UnexpectedResultException {
            int state_0 = this.state_0_;
            if ((state_0 & 1) != 0) {
                return JSTypesGen.expectInteger(this.execute(frameValue));
            }
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            if ((state_0 & 0xA) != 0 && arguments0Value_ instanceof JSDynamicObject) {
                JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
                if (arguments1Value_ instanceof Object[]) {
                    Object[] arguments1Value__ = (Object[])arguments1Value_;
                    if ((state_0 & 2) != 0 && JSGuards.isJSArray(arguments0Value__) && arguments1Value__.length == 1) {
                        try {
                            return this.pushArraySingle(arguments0Value__, arguments1Value__);
                        }
                        catch (SlowPathException ex) {
                            CompilerDirectives.transferToInterpreterAndInvalidate();
                            Lock lock = this.getLock();
                            lock.lock();
                            try {
                                this.exclude_ |= 1;
                                this.state_0_ &= 0xFFFFFFFD;
                            }
                            finally {
                                lock.unlock();
                            }
                            return JSTypesGen.expectInteger(this.executeAndSpecialize(arguments0Value__, arguments1Value__));
                        }
                    }
                    if ((state_0 & 8) != 0 && JSGuards.isJSArray(arguments0Value__) && arguments1Value__.length >= 2) {
                        try {
                            return this.pushArrayAll(arguments0Value__, arguments1Value__);
                        }
                        catch (SlowPathException ex) {
                            CompilerDirectives.transferToInterpreterAndInvalidate();
                            Lock lock = this.getLock();
                            lock.lock();
                            try {
                                this.exclude_ |= 2;
                                this.state_0_ &= 0xFFFFFFF7;
                            }
                            finally {
                                lock.unlock();
                            }
                            return JSTypesGen.expectInteger(this.executeAndSpecialize(arguments0Value__, arguments1Value__));
                        }
                    }
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return JSTypesGen.expectInteger(this.executeAndSpecialize(arguments0Value_, arguments1Value_));
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            int state_0 = this.state_0_;
            try {
                if ((state_0 & 0x35) == 0 && state_0 != 0) {
                    this.executeInt(frameValue);
                    return;
                }
                if ((state_0 & 0xB) == 0 && state_0 != 0) {
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
         * Exception decompiling
         */
        private Object executeAndSpecialize(Object arguments0Value, Object arguments1Value) {
            /*
             * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
             * 
             * org.benf.cfr.reader.util.ConfusedCFRException: Started 2 blocks at once
             *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:412)
             *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:487)
             *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:736)
             *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:850)
             *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
             *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
             *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
             *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
             *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
             *     at org.benf.cfr.reader.entities.ClassFile.analyseInnerClassesPass1(ClassFile.java:923)
             *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1035)
             *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
             *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
             *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
             *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
             *     at org.benf.cfr.reader.Main.main(Main.java:54)
             */
            throw new IllegalStateException("Decompilation failed");
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
            Object[] data = new Object[7];
            data[0] = 0;
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            Object[] s = new Object[3];
            s[0] = "pushArrayNone";
            s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[1] = s;
            s = new Object[3];
            s[0] = "pushArraySingle";
            s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : ((exclude & 1) != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0));
            data[2] = s;
            s = new Object[3];
            s[0] = "pushArraySingleLong";
            s[1] = (state_0 & 4) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[3] = s;
            s = new Object[3];
            s[0] = "pushArrayAll";
            s[1] = (state_0 & 8) != 0 ? Byte.valueOf((byte)1) : ((exclude & 2) != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0));
            data[4] = s;
            s = new Object[3];
            s[0] = "pushArrayAllLong";
            s[1] = (state_0 & 0x10) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[5] = s;
            s = new Object[3];
            s[0] = "pushProperty";
            s[1] = (state_0 & 0x20) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[6] = s;
            return Introspection.Provider.create(data);
        }

        public static ArrayPrototypeBuiltins.JSArrayPushNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSArrayPushNodeGen(context, builtin, arguments);
        }
    }
}

