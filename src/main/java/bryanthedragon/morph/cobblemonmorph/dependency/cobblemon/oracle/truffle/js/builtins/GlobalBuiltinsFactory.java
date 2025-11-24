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
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.library.LibraryFactory;
import com.oracle.truffle.api.nodes.EncapsulatingNodeReference;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.builtins.GlobalBuiltins;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.JSTypes;
import com.oracle.truffle.js.nodes.JSTypesGen;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.cast.JSToDoubleNode;
import com.oracle.truffle.js.nodes.cast.JSToNumberNode;
import com.oracle.truffle.js.nodes.cast.JSToStringNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.SafeInteger;
import com.oracle.truffle.js.runtime.Symbol;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=GlobalBuiltins.class)
public final class GlobalBuiltinsFactory {
    private static final LibraryFactory<InteropLibrary> INTEROP_LIBRARY_ = LibraryFactory.resolve(InteropLibrary.class);

    @GeneratedBy(value=GlobalBuiltins.JSGlobalImportScriptEngineGlobalBindingsNode.class)
    static final class JSGlobalImportScriptEngineGlobalBindingsNodeGen
    extends GlobalBuiltins.JSGlobalImportScriptEngineGlobalBindingsNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;

        private JSGlobalImportScriptEngineGlobalBindingsNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            return this.importGlobalContext(arguments0Value_);
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
            s[0] = "importGlobalContext";
            s[1] = (byte)1;
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static GlobalBuiltins.JSGlobalImportScriptEngineGlobalBindingsNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSGlobalImportScriptEngineGlobalBindingsNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=GlobalBuiltins.JSGlobalReadBufferNode.class)
    public static final class JSGlobalReadBufferNodeGen
    extends GlobalBuiltins.JSGlobalReadBufferNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;

        private JSGlobalReadBufferNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            return this.readbuffer(arguments0Value_);
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
            s[0] = "readbuffer";
            s[1] = (byte)1;
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static GlobalBuiltins.JSGlobalReadBufferNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSGlobalReadBufferNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=GlobalBuiltins.JSGlobalReadFullyNode.class)
    public static final class JSGlobalReadFullyNodeGen
    extends GlobalBuiltins.JSGlobalReadFullyNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;

        private JSGlobalReadFullyNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            return this.read(arguments0Value_);
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
            s[0] = "read";
            s[1] = (byte)1;
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static GlobalBuiltins.JSGlobalReadFullyNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSGlobalReadFullyNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=GlobalBuiltins.JSGlobalReadLineNode.class)
    public static final class JSGlobalReadLineNodeGen
    extends GlobalBuiltins.JSGlobalReadLineNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;

        private JSGlobalReadLineNodeGen(JSContext context, JSBuiltin builtin, boolean returnNullWhenEmpty, JavaScriptNode[] arguments) {
            super(context, builtin, returnNullWhenEmpty);
            this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
        }

        @Override
        public JavaScriptNode[] getArguments() {
            return new JavaScriptNode[]{this.arguments0_};
        }

        @Override
        public Object execute(VirtualFrame frameValue) {
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            return this.readLine(arguments0Value_);
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
            s[0] = "readLine";
            s[1] = (byte)1;
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static GlobalBuiltins.JSGlobalReadLineNode create(JSContext context, JSBuiltin builtin, boolean returnNullWhenEmpty, JavaScriptNode[] arguments) {
            return new JSGlobalReadLineNodeGen(context, builtin, returnNullWhenEmpty, arguments);
        }
    }

    @GeneratedBy(value=GlobalBuiltins.JSGlobalExitNode.class)
    public static final class JSGlobalExitNodeGen
    extends GlobalBuiltins.JSGlobalExitNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private JSToNumberNode exit2_toNumberNode_;

        private JSGlobalExitNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            if ((state_0 & 5) == 0 && state_0 != 0) {
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
            assert ((state_0 & 2) != 0);
            return this.exit(arguments0Value_);
        }

        private Object execute_generic1(int state_0, VirtualFrame frameValue) {
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            if ((state_0 & 1) != 0 && JSGuards.isUndefined(arguments0Value_)) {
                return this.exit(arguments0Value_);
            }
            if ((state_0 & 2) != 0 && arguments0Value_ instanceof Integer) {
                int arguments0Value__ = (Integer)arguments0Value_;
                return this.exit(arguments0Value__);
            }
            if ((state_0 & 4) != 0) {
                return this.exit(arguments0Value_, this.exit2_toNumberNode_);
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
                if (JSGuards.isUndefined(arguments0Value)) {
                    this.state_0_ = state_0 |= 1;
                    lock.unlock();
                    hasLock = false;
                    Object object = this.exit(arguments0Value);
                    return object;
                }
                if (arguments0Value instanceof Integer) {
                    int arguments0Value_ = (Integer)arguments0Value;
                    this.state_0_ = state_0 |= 2;
                    lock.unlock();
                    hasLock = false;
                    Object object = this.exit(arguments0Value_);
                    return object;
                }
                this.exit2_toNumberNode_ = super.insert(JSToNumberNode.create());
                this.state_0_ = state_0 |= 4;
                lock.unlock();
                hasLock = false;
                Object object = this.exit(arguments0Value, this.exit2_toNumberNode_);
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
            Object[] data = new Object[4];
            data[0] = 0;
            int state_0 = this.state_0_;
            Object[] s = new Object[3];
            s[0] = "exit";
            s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[1] = s;
            s = new Object[3];
            s[0] = "exit";
            s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[2] = s;
            s = new Object[3];
            s[0] = "exit";
            if ((state_0 & 4) != 0) {
                s[1] = (byte)1;
                ArrayList<List<JSToNumberNode>> cached = new ArrayList<List<JSToNumberNode>>();
                cached.add(Arrays.asList(this.exit2_toNumberNode_));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[3] = s;
            return Introspection.Provider.create(data);
        }

        public static GlobalBuiltins.JSGlobalExitNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSGlobalExitNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=GlobalBuiltins.JSGlobalLoadWithNewGlobalNode.class)
    public static final class JSGlobalLoadWithNewGlobalNodeGen
    extends GlobalBuiltins.JSGlobalLoadWithNewGlobalNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private InteropLibrary loadTruffleObject_interop_;

        private JSGlobalLoadWithNewGlobalNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
                JSDynamicObject arguments0Value__;
                Object[] arguments1Value__ = (Object[])arguments1Value_;
                if ((state_0 & 1) != 0 && arguments0Value_ instanceof TruffleString) {
                    TruffleString arguments0Value__2 = (TruffleString)arguments0Value_;
                    return this.loadString(arguments0Value__2, arguments1Value__);
                }
                if ((state_0 & 2) != 0 && JSGuards.isForeignObject(arguments0Value_)) {
                    return this.loadTruffleObject(arguments0Value_, arguments1Value__, this.loadTruffleObject_interop_);
                }
                if ((state_0 & 4) != 0 && arguments0Value_ instanceof JSDynamicObject && JSGuards.isJSObject(arguments0Value__ = (JSDynamicObject)arguments0Value_)) {
                    return this.loadScriptObj(arguments0Value__, arguments1Value__);
                }
                if (!((state_0 & 8) == 0 || JSGuards.isString(arguments0Value_) || JSGuards.isForeignObject(arguments0Value_) || JSGuards.isJSObject(arguments0Value_))) {
                    return this.loadConvertToString(arguments0Value_, arguments1Value__);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.execute(frameValue);
        }

        private Object executeAndSpecialize(Object arguments0Value, Object arguments1Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                if (arguments1Value instanceof Object[]) {
                    JSDynamicObject arguments0Value_;
                    Object[] arguments1Value_ = (Object[])arguments1Value;
                    if (arguments0Value instanceof TruffleString) {
                        TruffleString arguments0Value_2 = (TruffleString)arguments0Value;
                        this.state_0_ = state_0 |= 1;
                        lock.unlock();
                        hasLock = false;
                        Object object = this.loadString(arguments0Value_2, arguments1Value_);
                        return object;
                    }
                    if (JSGuards.isForeignObject(arguments0Value)) {
                        this.loadTruffleObject_interop_ = super.insert(INTEROP_LIBRARY_.createDispatched(5));
                        this.state_0_ = state_0 |= 2;
                        lock.unlock();
                        hasLock = false;
                        Object arguments0Value_2 = this.loadTruffleObject(arguments0Value, arguments1Value_, this.loadTruffleObject_interop_);
                        return arguments0Value_2;
                    }
                    if (arguments0Value instanceof JSDynamicObject && JSGuards.isJSObject(arguments0Value_ = (JSDynamicObject)arguments0Value)) {
                        this.state_0_ = state_0 |= 4;
                        lock.unlock();
                        hasLock = false;
                        Object object = this.loadScriptObj(arguments0Value_, arguments1Value_);
                        return object;
                    }
                    if (!(JSGuards.isString(arguments0Value) || JSGuards.isForeignObject(arguments0Value) || JSGuards.isJSObject(arguments0Value))) {
                        this.state_0_ = state_0 |= 8;
                        lock.unlock();
                        hasLock = false;
                        Object object = this.loadConvertToString(arguments0Value, arguments1Value_);
                        return object;
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
            Object[] data = new Object[5];
            data[0] = 0;
            int state_0 = this.state_0_;
            Object[] s = new Object[3];
            s[0] = "loadString";
            s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[1] = s;
            s = new Object[3];
            s[0] = "loadTruffleObject";
            if ((state_0 & 2) != 0) {
                s[1] = (byte)1;
                ArrayList<List<InteropLibrary>> cached = new ArrayList<List<InteropLibrary>>();
                cached.add(Arrays.asList(this.loadTruffleObject_interop_));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[2] = s;
            s = new Object[3];
            s[0] = "loadScriptObj";
            s[1] = (state_0 & 4) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[3] = s;
            s = new Object[3];
            s[0] = "loadConvertToString";
            s[1] = (state_0 & 8) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[4] = s;
            return Introspection.Provider.create(data);
        }

        public static GlobalBuiltins.JSGlobalLoadWithNewGlobalNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSGlobalLoadWithNewGlobalNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=GlobalBuiltins.JSGlobalLoadNode.class)
    public static final class JSGlobalLoadNodeGen
    extends GlobalBuiltins.JSGlobalLoadNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private InteropLibrary loadTruffleObject_interop_;

        private JSGlobalLoadNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
                JSDynamicObject arguments0Value__;
                Object[] arguments1Value__ = (Object[])arguments1Value_;
                if ((state_0 & 1) != 0 && arguments0Value_ instanceof TruffleString) {
                    TruffleString arguments0Value__2 = (TruffleString)arguments0Value_;
                    return this.loadString(arguments0Value__2, arguments1Value__);
                }
                if ((state_0 & 2) != 0 && JSGuards.isForeignObject(arguments0Value_)) {
                    return this.loadTruffleObject(arguments0Value_, arguments1Value__, this.loadTruffleObject_interop_);
                }
                if ((state_0 & 4) != 0 && arguments0Value_ instanceof JSDynamicObject && JSGuards.isJSObject(arguments0Value__ = (JSDynamicObject)arguments0Value_)) {
                    return this.loadScriptObj(arguments0Value__, arguments1Value__);
                }
                if (!((state_0 & 8) == 0 || JSGuards.isString(arguments0Value_) || JSGuards.isForeignObject(arguments0Value_) || JSGuards.isJSObject(arguments0Value_))) {
                    return this.loadConvertToString(arguments0Value_, arguments1Value__);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.execute(frameValue);
        }

        private Object executeAndSpecialize(Object arguments0Value, Object arguments1Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                if (arguments1Value instanceof Object[]) {
                    JSDynamicObject arguments0Value_;
                    Object[] arguments1Value_ = (Object[])arguments1Value;
                    if (arguments0Value instanceof TruffleString) {
                        TruffleString arguments0Value_2 = (TruffleString)arguments0Value;
                        this.state_0_ = state_0 |= 1;
                        lock.unlock();
                        hasLock = false;
                        Object object = this.loadString(arguments0Value_2, arguments1Value_);
                        return object;
                    }
                    if (JSGuards.isForeignObject(arguments0Value)) {
                        this.loadTruffleObject_interop_ = super.insert(INTEROP_LIBRARY_.createDispatched(5));
                        this.state_0_ = state_0 |= 2;
                        lock.unlock();
                        hasLock = false;
                        Object arguments0Value_2 = this.loadTruffleObject(arguments0Value, arguments1Value_, this.loadTruffleObject_interop_);
                        return arguments0Value_2;
                    }
                    if (arguments0Value instanceof JSDynamicObject && JSGuards.isJSObject(arguments0Value_ = (JSDynamicObject)arguments0Value)) {
                        this.state_0_ = state_0 |= 4;
                        lock.unlock();
                        hasLock = false;
                        Object object = this.loadScriptObj(arguments0Value_, arguments1Value_);
                        return object;
                    }
                    if (!(JSGuards.isString(arguments0Value) || JSGuards.isForeignObject(arguments0Value) || JSGuards.isJSObject(arguments0Value))) {
                        this.state_0_ = state_0 |= 8;
                        lock.unlock();
                        hasLock = false;
                        Object object = this.loadConvertToString(arguments0Value, arguments1Value_);
                        return object;
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
            Object[] data = new Object[5];
            data[0] = 0;
            int state_0 = this.state_0_;
            Object[] s = new Object[3];
            s[0] = "loadString";
            s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[1] = s;
            s = new Object[3];
            s[0] = "loadTruffleObject";
            if ((state_0 & 2) != 0) {
                s[1] = (byte)1;
                ArrayList<List<InteropLibrary>> cached = new ArrayList<List<InteropLibrary>>();
                cached.add(Arrays.asList(this.loadTruffleObject_interop_));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[2] = s;
            s = new Object[3];
            s[0] = "loadScriptObj";
            s[1] = (state_0 & 4) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[3] = s;
            s = new Object[3];
            s[0] = "loadConvertToString";
            s[1] = (state_0 & 8) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[4] = s;
            return Introspection.Provider.create(data);
        }

        public static GlobalBuiltins.JSGlobalLoadNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSGlobalLoadNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=GlobalBuiltins.JSGlobalPrintNode.class)
    public static final class JSGlobalPrintNodeGen
    extends GlobalBuiltins.JSGlobalPrintNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @CompilerDirectives.CompilationFinal
        private int state_0_;

        private JSGlobalPrintNodeGen(JSContext context, JSBuiltin builtin, boolean useErr, boolean noNewline, JavaScriptNode[] arguments) {
            super(context, builtin, useErr, noNewline);
            this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
        }

        @Override
        public JavaScriptNode[] getArguments() {
            return new JavaScriptNode[]{this.arguments0_};
        }

        @Override
        public Object executeObjectArray(Object[] arguments0Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
                return this.print(arguments0Value);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value);
        }

        @Override
        public Object execute(VirtualFrame frameValue) {
            int state_0 = this.state_0_;
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            if (state_0 != 0 && arguments0Value_ instanceof Object[]) {
                Object[] arguments0Value__ = (Object[])arguments0Value_;
                return this.print(arguments0Value__);
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
                return this.print(arguments0Value_);
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
            s[0] = "print";
            s[1] = state_0 != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static GlobalBuiltins.JSGlobalPrintNode create(JSContext context, JSBuiltin builtin, boolean useErr, boolean noNewline, JavaScriptNode[] arguments) {
            return new JSGlobalPrintNodeGen(context, builtin, useErr, noNewline, arguments);
        }
    }

    @GeneratedBy(value=GlobalBuiltins.JSGlobalUnEscapeNode.class)
    public static final class JSGlobalUnEscapeNodeGen
    extends GlobalBuiltins.JSGlobalUnEscapeNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;

        private JSGlobalUnEscapeNodeGen(JSContext context, JSBuiltin builtin, boolean unescape, JavaScriptNode[] arguments) {
            super(context, builtin, unescape);
            this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
        }

        @Override
        public JavaScriptNode[] getArguments() {
            return new JavaScriptNode[]{this.arguments0_};
        }

        @Override
        public Object execute(VirtualFrame frameValue) {
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            return this.escape(arguments0Value_);
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
            s[0] = "escape";
            s[1] = (byte)1;
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static GlobalBuiltins.JSGlobalUnEscapeNode create(JSContext context, JSBuiltin builtin, boolean unescape, JavaScriptNode[] arguments) {
            return new JSGlobalUnEscapeNodeGen(context, builtin, unescape, arguments);
        }
    }

    @GeneratedBy(value=GlobalBuiltins.JSGlobalIndirectEvalNode.class)
    public static final class JSGlobalIndirectEvalNodeGen
    extends GlobalBuiltins.JSGlobalIndirectEvalNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @CompilerDirectives.CompilationFinal
        private volatile int exclude_;
        @Node.Child
        private IndirectEvalForeignObject0Data indirectEvalForeignObject0_cache;

        private JSGlobalIndirectEvalNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            if ((state_0 & 0x7F7) == 0 && (state_0 & 0x7FF) != 0) {
                return this.execute_int0(state_0, frameValue);
            }
            if ((state_0 & 0x7DF) == 0 && (state_0 & 0x7FF) != 0) {
                return this.execute_long1(state_0, frameValue);
            }
            if ((state_0 & 0x7BF) == 0 && (state_0 & 0x7FF) != 0) {
                return this.execute_double2(state_0, frameValue);
            }
            if ((state_0 & 0x77F) == 0 && (state_0 & 0x7FF) != 0) {
                return this.execute_boolean3(state_0, frameValue);
            }
            return this.execute_generic4(state_0, frameValue);
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
            assert ((state_0 & 8) != 0);
            return this.indirectEvalInt(arguments0Value_);
        }

        private Object execute_long1(int state_0, VirtualFrame frameValue) {
            long arguments0Value_;
            try {
                arguments0Value_ = this.arguments0_.executeLong(frameValue);
            }
            catch (UnexpectedResultException ex) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.executeAndSpecialize(ex.getResult());
            }
            assert ((state_0 & 0x20) != 0);
            return this.indirectEvalLong(arguments0Value_);
        }

        private Object execute_double2(int state_0, VirtualFrame frameValue) {
            double arguments0Value_;
            long arguments0Value_long = 0L;
            int arguments0Value_int = 0;
            try {
                if ((state_0 & 0x7000) == 0 && (state_0 & 0x7FF) != 0) {
                    arguments0Value_ = this.arguments0_.executeDouble(frameValue);
                } else if ((state_0 & 0x6800) == 0 && (state_0 & 0x7FF) != 0) {
                    arguments0Value_int = this.arguments0_.executeInt(frameValue);
                    arguments0Value_ = JSTypes.intToDouble(arguments0Value_int);
                } else if ((state_0 & 0x3800) == 0 && (state_0 & 0x7FF) != 0) {
                    arguments0Value_long = this.arguments0_.executeLong(frameValue);
                    arguments0Value_ = JSTypes.longToDouble(arguments0Value_long);
                } else {
                    Object arguments0Value__ = this.arguments0_.execute(frameValue);
                    arguments0Value_ = JSTypesGen.expectImplicitDouble((state_0 & 0x7800) >>> 11, arguments0Value__);
                }
            }
            catch (UnexpectedResultException ex) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.executeAndSpecialize(ex.getResult());
            }
            assert ((state_0 & 0x40) != 0);
            return this.indirectEvalDouble(arguments0Value_);
        }

        private Object execute_boolean3(int state_0, VirtualFrame frameValue) {
            boolean arguments0Value_;
            try {
                arguments0Value_ = this.arguments0_.executeBoolean(frameValue);
            }
            catch (UnexpectedResultException ex) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.executeAndSpecialize(ex.getResult());
            }
            assert ((state_0 & 0x80) != 0);
            return this.indirectEvalBoolean(arguments0Value_);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @CompilerDirectives.TruffleBoundary
        private Object indirectEvalForeignObject1Boundary(int state_0, Object arguments0Value_) {
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this);
            try {
                InteropLibrary indirectEvalForeignObject1_interop__ = INTEROP_LIBRARY_.getUncached(arguments0Value_);
                Object object = this.indirectEvalForeignObject(arguments0Value_, indirectEvalForeignObject1_interop__);
                return object;
            }
            finally {
                encapsulating_.set(prev_);
            }
        }

        @ExplodeLoop
        private Object execute_generic4(int state_0, VirtualFrame frameValue) {
            TruffleObject arguments0Value__;
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            if ((state_0 & 1) != 0 && arguments0Value_ instanceof TruffleString) {
                TruffleString arguments0Value__2 = (TruffleString)arguments0Value_;
                return this.indirectEvalString(arguments0Value__2);
            }
            if ((state_0 & 6) != 0) {
                if ((state_0 & 2) != 0) {
                    IndirectEvalForeignObject0Data s1_ = this.indirectEvalForeignObject0_cache;
                    while (s1_ != null) {
                        if (s1_.interop_.accepts(arguments0Value_) && JSGuards.isForeignObject(arguments0Value_)) {
                            return this.indirectEvalForeignObject(arguments0Value_, s1_.interop_);
                        }
                        s1_ = s1_.next_;
                    }
                }
                if ((state_0 & 4) != 0 && JSGuards.isForeignObject(arguments0Value_)) {
                    return this.indirectEvalForeignObject1Boundary(state_0, arguments0Value_);
                }
            }
            if ((state_0 & 8) != 0 && arguments0Value_ instanceof Integer) {
                int arguments0Value__3 = (Integer)arguments0Value_;
                return this.indirectEvalInt(arguments0Value__3);
            }
            if ((state_0 & 0x10) != 0 && arguments0Value_ instanceof SafeInteger) {
                arguments0Value__ = (SafeInteger)arguments0Value_;
                return this.indirectEvalSafeInteger((SafeInteger)arguments0Value__);
            }
            if ((state_0 & 0x20) != 0 && arguments0Value_ instanceof Long) {
                long arguments0Value__4 = (Long)arguments0Value_;
                return this.indirectEvalLong(arguments0Value__4);
            }
            if ((state_0 & 0x40) != 0 && JSTypesGen.isImplicitDouble((state_0 & 0x7800) >>> 11, arguments0Value_)) {
                double arguments0Value__5 = JSTypesGen.asImplicitDouble((state_0 & 0x7800) >>> 11, arguments0Value_);
                return this.indirectEvalDouble(arguments0Value__5);
            }
            if ((state_0 & 0x80) != 0 && arguments0Value_ instanceof Boolean) {
                boolean arguments0Value__6 = (Boolean)arguments0Value_;
                return this.indirectEvalBoolean(arguments0Value__6);
            }
            if ((state_0 & 0x100) != 0 && arguments0Value_ instanceof Symbol) {
                arguments0Value__ = (Symbol)arguments0Value_;
                return this.indirectEvalSymbol((Symbol)arguments0Value__);
            }
            if ((state_0 & 0x200) != 0 && arguments0Value_ instanceof BigInt) {
                arguments0Value__ = (BigInt)arguments0Value_;
                return this.indirectEvalBigInt((BigInt)arguments0Value__);
            }
            if ((state_0 & 0x400) != 0 && arguments0Value_ instanceof JSDynamicObject && JSGuards.isJSDynamicObject(arguments0Value__ = (JSDynamicObject)arguments0Value_)) {
                return this.indirectEvalJSType((JSDynamicObject)arguments0Value__);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_);
        }

        @Override
        public boolean executeBoolean(VirtualFrame frameValue) throws UnexpectedResultException {
            boolean arguments0Value_;
            int state_0 = this.state_0_;
            if ((state_0 & 7) != 0) {
                return JSTypesGen.expectBoolean(this.execute(frameValue));
            }
            try {
                arguments0Value_ = this.arguments0_.executeBoolean(frameValue);
            }
            catch (UnexpectedResultException ex) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return JSTypesGen.expectBoolean(this.executeAndSpecialize(ex.getResult()));
            }
            if ((state_0 & 0x80) != 0) {
                return this.indirectEvalBoolean(arguments0Value_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return JSTypesGen.expectBoolean(this.executeAndSpecialize(arguments0Value_));
        }

        @Override
        public double executeDouble(VirtualFrame frameValue) throws UnexpectedResultException {
            double arguments0Value_;
            int state_0 = this.state_0_;
            if ((state_0 & 7) != 0) {
                return JSTypesGen.expectDouble(this.execute(frameValue));
            }
            long arguments0Value_long = 0L;
            int arguments0Value_int = 0;
            try {
                if ((state_0 & 0x7000) == 0 && (state_0 & 0x7FF) != 0) {
                    arguments0Value_ = this.arguments0_.executeDouble(frameValue);
                } else if ((state_0 & 0x6800) == 0 && (state_0 & 0x7FF) != 0) {
                    arguments0Value_int = this.arguments0_.executeInt(frameValue);
                    arguments0Value_ = JSTypes.intToDouble(arguments0Value_int);
                } else if ((state_0 & 0x3800) == 0 && (state_0 & 0x7FF) != 0) {
                    arguments0Value_long = this.arguments0_.executeLong(frameValue);
                    arguments0Value_ = JSTypes.longToDouble(arguments0Value_long);
                } else {
                    Object arguments0Value__ = this.arguments0_.execute(frameValue);
                    arguments0Value_ = JSTypesGen.expectImplicitDouble((state_0 & 0x7800) >>> 11, arguments0Value__);
                }
            }
            catch (UnexpectedResultException ex) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return JSTypesGen.expectDouble(this.executeAndSpecialize(ex.getResult()));
            }
            if ((state_0 & 0x40) != 0) {
                return this.indirectEvalDouble(arguments0Value_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return JSTypesGen.expectDouble(this.executeAndSpecialize((state_0 & 0x6800) == 0 && (state_0 & 0x7FF) != 0 ? (Number)arguments0Value_int : (Number)((state_0 & 0x3800) == 0 && (state_0 & 0x7FF) != 0 ? (Number)arguments0Value_long : (Number)arguments0Value_)));
        }

        @Override
        public int executeInt(VirtualFrame frameValue) throws UnexpectedResultException {
            int arguments0Value_;
            int state_0 = this.state_0_;
            if ((state_0 & 7) != 0) {
                return JSTypesGen.expectInteger(this.execute(frameValue));
            }
            try {
                arguments0Value_ = this.arguments0_.executeInt(frameValue);
            }
            catch (UnexpectedResultException ex) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return JSTypesGen.expectInteger(this.executeAndSpecialize(ex.getResult()));
            }
            if ((state_0 & 8) != 0) {
                return this.indirectEvalInt(arguments0Value_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return JSTypesGen.expectInteger(this.executeAndSpecialize(arguments0Value_));
        }

        @Override
        public long executeLong(VirtualFrame frameValue) throws UnexpectedResultException {
            long arguments0Value_;
            int state_0 = this.state_0_;
            if ((state_0 & 7) != 0) {
                return JSTypesGen.expectLong(this.execute(frameValue));
            }
            try {
                arguments0Value_ = this.arguments0_.executeLong(frameValue);
            }
            catch (UnexpectedResultException ex) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return JSTypesGen.expectLong(this.executeAndSpecialize(ex.getResult()));
            }
            if ((state_0 & 0x20) != 0) {
                return this.indirectEvalLong(arguments0Value_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return JSTypesGen.expectLong(this.executeAndSpecialize(arguments0Value_));
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            int state_0 = this.state_0_;
            try {
                if ((state_0 & 0x7DF) == 0 && (state_0 & 0x7FF) != 0) {
                    this.executeLong(frameValue);
                    return;
                }
                if ((state_0 & 0x7F7) == 0 && (state_0 & 0x7FF) != 0) {
                    this.executeInt(frameValue);
                    return;
                }
                if ((state_0 & 0x7BF) == 0 && (state_0 & 0x7FF) != 0) {
                    this.executeDouble(frameValue);
                    return;
                }
                if ((state_0 & 0x77F) == 0 && (state_0 & 0x7FF) != 0) {
                    this.executeBoolean(frameValue);
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
        private Object executeAndSpecialize(Object arguments0Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                JSDynamicObject arguments0Value_;
                Object object;
                int state_0 = this.state_0_;
                int exclude = this.exclude_;
                if (arguments0Value instanceof TruffleString) {
                    TruffleString arguments0Value_2 = (TruffleString)arguments0Value;
                    this.state_0_ = state_0 |= 1;
                    lock.unlock();
                    hasLock = false;
                    Object object2 = this.indirectEvalString(arguments0Value_2);
                    return object2;
                }
                if (exclude == 0) {
                    int count1_ = 0;
                    IndirectEvalForeignObject0Data s1_ = this.indirectEvalForeignObject0_cache;
                    if ((state_0 & 2) != 0) {
                        while (!(s1_ == null || s1_.interop_.accepts(arguments0Value) && JSGuards.isForeignObject(arguments0Value))) {
                            s1_ = s1_.next_;
                            ++count1_;
                        }
                    }
                    if (s1_ == null && JSGuards.isForeignObject(arguments0Value) && count1_ < 3) {
                        s1_ = super.insert(new IndirectEvalForeignObject0Data(this.indirectEvalForeignObject0_cache));
                        s1_.interop_ = s1_.insertAccessor(INTEROP_LIBRARY_.create(arguments0Value));
                        VarHandle.storeStoreFence();
                        this.indirectEvalForeignObject0_cache = s1_;
                        this.state_0_ = state_0 |= 2;
                    }
                    if (s1_ != null) {
                        lock.unlock();
                        hasLock = false;
                        Object object3 = this.indirectEvalForeignObject(arguments0Value, s1_.interop_);
                        return object3;
                    }
                }
                InteropLibrary indirectEvalForeignObject1_interop__ = null;
                Object encapsulating_ = EncapsulatingNodeReference.getCurrent();
                Node prev_ = ((EncapsulatingNodeReference)encapsulating_).set(this);
                try {
                    if (JSGuards.isForeignObject(arguments0Value)) {
                        indirectEvalForeignObject1_interop__ = INTEROP_LIBRARY_.getUncached(arguments0Value);
                        this.exclude_ = exclude |= 1;
                        this.indirectEvalForeignObject0_cache = null;
                        state_0 &= 0xFFFFFFFD;
                        this.state_0_ = state_0 |= 4;
                        lock.unlock();
                        hasLock = false;
                        Object object4 = this.indirectEvalForeignObject(arguments0Value, indirectEvalForeignObject1_interop__);
                        return object4;
                    }
                }
                finally {
                    ((EncapsulatingNodeReference)encapsulating_).set(prev_);
                }
                if (arguments0Value instanceof Integer) {
                    int arguments0Value_3 = (Integer)arguments0Value;
                    this.state_0_ = state_0 |= 8;
                    lock.unlock();
                    hasLock = false;
                    encapsulating_ = this.indirectEvalInt(arguments0Value_3);
                    return encapsulating_;
                }
                if (arguments0Value instanceof SafeInteger) {
                    SafeInteger arguments0Value_4 = (SafeInteger)arguments0Value;
                    this.state_0_ = state_0 |= 0x10;
                    lock.unlock();
                    hasLock = false;
                    encapsulating_ = this.indirectEvalSafeInteger(arguments0Value_4);
                    return encapsulating_;
                }
                if (arguments0Value instanceof Long) {
                    long arguments0Value_5 = (Long)arguments0Value;
                    this.state_0_ = state_0 |= 0x20;
                    lock.unlock();
                    hasLock = false;
                    Long l = this.indirectEvalLong(arguments0Value_5);
                    return l;
                }
                int doubleCast0 = JSTypesGen.specializeImplicitDouble(arguments0Value);
                if (doubleCast0 != 0) {
                    double arguments0Value_6 = JSTypesGen.asImplicitDouble(doubleCast0, arguments0Value);
                    state_0 |= doubleCast0 << 11;
                    this.state_0_ = state_0 |= 0x40;
                    lock.unlock();
                    hasLock = false;
                    Double d = this.indirectEvalDouble(arguments0Value_6);
                    return d;
                }
                if (arguments0Value instanceof Boolean) {
                    boolean arguments0Value_7 = (Boolean)arguments0Value;
                    this.state_0_ = state_0 |= 0x80;
                    lock.unlock();
                    hasLock = false;
                    object = this.indirectEvalBoolean(arguments0Value_7);
                    return object;
                }
                if (arguments0Value instanceof Symbol) {
                    Symbol arguments0Value_8 = (Symbol)arguments0Value;
                    this.state_0_ = state_0 |= 0x100;
                    lock.unlock();
                    hasLock = false;
                    object = this.indirectEvalSymbol(arguments0Value_8);
                    return object;
                }
                if (arguments0Value instanceof BigInt) {
                    BigInt arguments0Value_9 = (BigInt)arguments0Value;
                    this.state_0_ = state_0 |= 0x200;
                    lock.unlock();
                    hasLock = false;
                    object = this.indirectEvalBigInt(arguments0Value_9);
                    return object;
                }
                if (arguments0Value instanceof JSDynamicObject && JSGuards.isJSDynamicObject(arguments0Value_ = (JSDynamicObject)arguments0Value)) {
                    this.state_0_ = state_0 |= 0x400;
                    lock.unlock();
                    hasLock = false;
                    object = this.indirectEvalJSType(arguments0Value_);
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
            IndirectEvalForeignObject0Data s1_;
            int state_0 = this.state_0_;
            if ((state_0 & 0x7FF) == 0) {
                return NodeCost.UNINITIALIZED;
            }
            if ((state_0 & 0x7FF & (state_0 & 0x7FF) - 1) == 0 && ((s1_ = this.indirectEvalForeignObject0_cache) == null || s1_.next_ == null)) {
                return NodeCost.MONOMORPHIC;
            }
            return NodeCost.POLYMORPHIC;
        }

        @Override
        public Introspection getIntrospectionData() {
            ArrayList<List<Object>> cached;
            Object[] data = new Object[12];
            data[0] = 0;
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            Object[] s = new Object[3];
            s[0] = "indirectEvalString";
            s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[1] = s;
            s = new Object[3];
            s[0] = "indirectEvalForeignObject";
            if ((state_0 & 2) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList<List<Object>>();
                IndirectEvalForeignObject0Data s1_ = this.indirectEvalForeignObject0_cache;
                while (s1_ != null) {
                    cached.add(Arrays.asList(s1_.interop_));
                    s1_ = s1_.next_;
                }
                s[2] = cached;
            } else {
                s[1] = exclude != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0);
            }
            data[2] = s;
            s = new Object[3];
            s[0] = "indirectEvalForeignObject";
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
            s[0] = "indirectEvalInt";
            s[1] = (state_0 & 8) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[4] = s;
            s = new Object[3];
            s[0] = "indirectEvalSafeInteger";
            s[1] = (state_0 & 0x10) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[5] = s;
            s = new Object[3];
            s[0] = "indirectEvalLong";
            s[1] = (state_0 & 0x20) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[6] = s;
            s = new Object[3];
            s[0] = "indirectEvalDouble";
            s[1] = (state_0 & 0x40) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[7] = s;
            s = new Object[3];
            s[0] = "indirectEvalBoolean";
            s[1] = (state_0 & 0x80) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[8] = s;
            s = new Object[3];
            s[0] = "indirectEvalSymbol";
            s[1] = (state_0 & 0x100) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[9] = s;
            s = new Object[3];
            s[0] = "indirectEvalBigInt";
            s[1] = (state_0 & 0x200) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[10] = s;
            s = new Object[3];
            s[0] = "indirectEvalJSType";
            s[1] = (state_0 & 0x400) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[11] = s;
            return Introspection.Provider.create(data);
        }

        public static GlobalBuiltins.JSGlobalIndirectEvalNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSGlobalIndirectEvalNodeGen(context, builtin, arguments);
        }

        @GeneratedBy(value=GlobalBuiltins.JSGlobalIndirectEvalNode.class)
        private static final class IndirectEvalForeignObject0Data
        extends Node {
            @Node.Child
            IndirectEvalForeignObject0Data next_;
            @Node.Child
            InteropLibrary interop_;

            IndirectEvalForeignObject0Data(IndirectEvalForeignObject0Data next_) {
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

    @GeneratedBy(value=GlobalBuiltins.JSGlobalDecodeURINode.class)
    public static final class JSGlobalDecodeURINodeGen
    extends GlobalBuiltins.JSGlobalDecodeURINode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;

        private JSGlobalDecodeURINodeGen(JSContext context, JSBuiltin builtin, boolean isSpecial, JavaScriptNode[] arguments) {
            super(context, builtin, isSpecial);
            this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
        }

        @Override
        public JavaScriptNode[] getArguments() {
            return new JavaScriptNode[]{this.arguments0_};
        }

        @Override
        public Object execute(VirtualFrame frameValue) {
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            return this.decodeURI(arguments0Value_);
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
            s[0] = "decodeURI";
            s[1] = (byte)1;
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static GlobalBuiltins.JSGlobalDecodeURINode create(JSContext context, JSBuiltin builtin, boolean isSpecial, JavaScriptNode[] arguments) {
            return new JSGlobalDecodeURINodeGen(context, builtin, isSpecial, arguments);
        }
    }

    @GeneratedBy(value=GlobalBuiltins.JSGlobalEncodeURINode.class)
    public static final class JSGlobalEncodeURINodeGen
    extends GlobalBuiltins.JSGlobalEncodeURINode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;

        private JSGlobalEncodeURINodeGen(JSContext context, JSBuiltin builtin, boolean isSpecial, JavaScriptNode[] arguments) {
            super(context, builtin, isSpecial);
            this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
        }

        @Override
        public JavaScriptNode[] getArguments() {
            return new JavaScriptNode[]{this.arguments0_};
        }

        @Override
        public Object execute(VirtualFrame frameValue) {
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            return this.encodeURI(arguments0Value_);
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
            s[0] = "encodeURI";
            s[1] = (byte)1;
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static GlobalBuiltins.JSGlobalEncodeURINode create(JSContext context, JSBuiltin builtin, boolean isSpecial, JavaScriptNode[] arguments) {
            return new JSGlobalEncodeURINodeGen(context, builtin, isSpecial, arguments);
        }
    }

    @GeneratedBy(value=GlobalBuiltins.JSGlobalParseIntNode.class)
    public static final class JSGlobalParseIntNodeGen
    extends GlobalBuiltins.JSGlobalParseIntNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @CompilerDirectives.CompilationFinal
        private BranchProfile parseIntInt_needsRadixConversion_;
        @CompilerDirectives.CompilationFinal
        private BranchProfile parseIntDouble_needsRadixConversion_;
        @Node.Child
        private TruffleString.ReadCharUTF16Node parseIntStringInt10_readRawNode_;
        @Node.Child
        private ParseIntGenericData parseIntGeneric_cache;

        private JSGlobalParseIntNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            if ((state_0 & 0x7C) == 0 && (state_0 & 0x7F) != 0) {
                return this.execute_int0(state_0, frameValue);
            }
            if ((state_0 & 0x63) == 0 && (state_0 & 0x7F) != 0) {
                return this.execute_double1(state_0, frameValue);
            }
            if ((state_0 & 0x5F) == 0 && (state_0 & 0x7F) != 0) {
                return this.execute_int2(state_0, frameValue);
            }
            return this.execute_generic3(state_0, frameValue);
        }

        private Object execute_int0(int state_0, VirtualFrame frameValue) {
            int arguments0Value_;
            try {
                arguments0Value_ = this.arguments0_.executeInt(frameValue);
            }
            catch (UnexpectedResultException ex) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                Object arguments1Value = this.arguments1_.execute(frameValue);
                return this.executeAndSpecialize(ex.getResult(), arguments1Value);
            }
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            if ((state_0 & 3) != 0) {
                if ((state_0 & 1) != 0 && JSGuards.isUndefined(arguments1Value_)) {
                    return this.parseIntNoRadix(arguments0Value_, arguments1Value_);
                }
                if ((state_0 & 2) != 0 && !JSGuards.isUndefined(arguments1Value_)) {
                    return this.parseIntInt(arguments0Value_, arguments1Value_, this.parseIntInt_needsRadixConversion_);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
        }

        private Object execute_double1(int state_0, VirtualFrame frameValue) {
            double arguments0Value_;
            long arguments0Value_long = 0L;
            int arguments0Value_int = 0;
            try {
                if ((state_0 & 0x700) == 0 && (state_0 & 0x7F) != 0) {
                    arguments0Value_ = this.arguments0_.executeDouble(frameValue);
                } else if ((state_0 & 0x680) == 0 && (state_0 & 0x7F) != 0) {
                    arguments0Value_int = this.arguments0_.executeInt(frameValue);
                    arguments0Value_ = JSTypes.intToDouble(arguments0Value_int);
                } else if ((state_0 & 0x380) == 0 && (state_0 & 0x7F) != 0) {
                    arguments0Value_long = this.arguments0_.executeLong(frameValue);
                    arguments0Value_ = JSTypes.longToDouble(arguments0Value_long);
                } else {
                    Object arguments0Value__ = this.arguments0_.execute(frameValue);
                    arguments0Value_ = JSTypesGen.expectImplicitDouble((state_0 & 0x780) >>> 7, arguments0Value__);
                }
            }
            catch (UnexpectedResultException ex) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                Object arguments1Value = this.arguments1_.execute(frameValue);
                return this.executeAndSpecialize(ex.getResult(), arguments1Value);
            }
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            if ((state_0 & 0x1C) != 0) {
                if ((state_0 & 4) != 0 && GlobalBuiltins.JSGlobalParseIntNode.hasRegularToStringInInt32Range(arguments0Value_) && JSGuards.isUndefined(arguments1Value_)) {
                    return this.parseIntDoubleToInt(arguments0Value_, arguments1Value_);
                }
                if ((state_0 & 8) != 0 && GlobalBuiltins.JSGlobalParseIntNode.hasRegularToString(arguments0Value_) && JSGuards.isUndefined(arguments1Value_)) {
                    return this.parseIntDoubleNoRadix(arguments0Value_, arguments1Value_);
                }
                if ((state_0 & 0x10) != 0 && GlobalBuiltins.JSGlobalParseIntNode.hasRegularToString(arguments0Value_)) {
                    return this.parseIntDouble(arguments0Value_, arguments1Value_, this.parseIntDouble_needsRadixConversion_);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize((state_0 & 0x680) == 0 && (state_0 & 0x7F) != 0 ? (Number)arguments0Value_int : (Number)((state_0 & 0x380) == 0 && (state_0 & 0x7F) != 0 ? (Number)arguments0Value_long : (Number)arguments0Value_), arguments1Value_);
        }

        private Object execute_int2(int state_0, VirtualFrame frameValue) {
            int arguments1Value_;
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            try {
                arguments1Value_ = this.arguments1_.executeInt(frameValue);
            }
            catch (UnexpectedResultException ex) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.executeAndSpecialize(arguments0Value_, ex.getResult());
            }
            assert ((state_0 & 0x20) != 0);
            if (arguments0Value_ instanceof TruffleString) {
                TruffleString arguments0Value__ = (TruffleString)arguments0Value_;
                if (arguments1Value_ == 10 && JSGuards.stringLength(arguments0Value__) < 15) {
                    return this.parseIntStringInt10(arguments0Value__, arguments1Value_, this.parseIntStringInt10_readRawNode_);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
        }

        private Object execute_generic3(int state_0, VirtualFrame frameValue) {
            ParseIntGenericData s6_;
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            if ((state_0 & 0x1F) != 0) {
                if ((state_0 & 3) != 0 && arguments0Value_ instanceof Integer) {
                    int arguments0Value__ = (Integer)arguments0Value_;
                    if ((state_0 & 1) != 0 && JSGuards.isUndefined(arguments1Value_)) {
                        return this.parseIntNoRadix(arguments0Value__, arguments1Value_);
                    }
                    if ((state_0 & 2) != 0 && !JSGuards.isUndefined(arguments1Value_)) {
                        return this.parseIntInt(arguments0Value__, arguments1Value_, this.parseIntInt_needsRadixConversion_);
                    }
                }
                if ((state_0 & 0x1C) != 0 && JSTypesGen.isImplicitDouble((state_0 & 0x780) >>> 7, arguments0Value_)) {
                    double arguments0Value__ = JSTypesGen.asImplicitDouble((state_0 & 0x780) >>> 7, arguments0Value_);
                    if ((state_0 & 4) != 0 && GlobalBuiltins.JSGlobalParseIntNode.hasRegularToStringInInt32Range(arguments0Value__) && JSGuards.isUndefined(arguments1Value_)) {
                        return this.parseIntDoubleToInt(arguments0Value__, arguments1Value_);
                    }
                    if ((state_0 & 8) != 0 && GlobalBuiltins.JSGlobalParseIntNode.hasRegularToString(arguments0Value__) && JSGuards.isUndefined(arguments1Value_)) {
                        return this.parseIntDoubleNoRadix(arguments0Value__, arguments1Value_);
                    }
                    if ((state_0 & 0x10) != 0 && GlobalBuiltins.JSGlobalParseIntNode.hasRegularToString(arguments0Value__)) {
                        return this.parseIntDouble(arguments0Value__, arguments1Value_, this.parseIntDouble_needsRadixConversion_);
                    }
                }
            }
            if ((state_0 & 0x20) != 0 && arguments0Value_ instanceof TruffleString) {
                int arguments1Value__;
                TruffleString arguments0Value__ = (TruffleString)arguments0Value_;
                if (arguments1Value_ instanceof Integer && (arguments1Value__ = ((Integer)arguments1Value_).intValue()) == 10 && JSGuards.stringLength(arguments0Value__) < 15) {
                    return this.parseIntStringInt10(arguments0Value__, arguments1Value__, this.parseIntStringInt10_readRawNode_);
                }
            }
            if ((state_0 & 0x40) != 0 && (s6_ = this.parseIntGeneric_cache) != null && !GlobalBuiltins.JSGlobalParseIntNode.isShortStringInt10(arguments0Value_, arguments1Value_)) {
                return this.parseIntGeneric(arguments0Value_, arguments1Value_, s6_.toStringNode_, s6_.needsRadix16_, s6_.needsDontFitLong_, s6_.readRawNode_, s6_.substringNode_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
        }

        @Override
        public double executeDouble(VirtualFrame frameValue) throws UnexpectedResultException {
            double arguments0Value_;
            int state_0 = this.state_0_;
            if ((state_0 & 0x62) != 0) {
                return JSTypesGen.expectDouble(this.execute(frameValue));
            }
            long arguments0Value_long = 0L;
            int arguments0Value_int = 0;
            try {
                if ((state_0 & 0x700) == 0 && (state_0 & 0x7F) != 0) {
                    arguments0Value_ = this.arguments0_.executeDouble(frameValue);
                } else if ((state_0 & 0x680) == 0 && (state_0 & 0x7F) != 0) {
                    arguments0Value_int = this.arguments0_.executeInt(frameValue);
                    arguments0Value_ = JSTypes.intToDouble(arguments0Value_int);
                } else if ((state_0 & 0x380) == 0 && (state_0 & 0x7F) != 0) {
                    arguments0Value_long = this.arguments0_.executeLong(frameValue);
                    arguments0Value_ = JSTypes.longToDouble(arguments0Value_long);
                } else {
                    Object arguments0Value__ = this.arguments0_.execute(frameValue);
                    arguments0Value_ = JSTypesGen.expectImplicitDouble((state_0 & 0x780) >>> 7, arguments0Value__);
                }
            }
            catch (UnexpectedResultException ex) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                Object arguments1Value = this.arguments1_.execute(frameValue);
                return JSTypesGen.expectDouble(this.executeAndSpecialize(ex.getResult(), arguments1Value));
            }
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            if ((state_0 & 0x18) != 0) {
                if ((state_0 & 8) != 0 && GlobalBuiltins.JSGlobalParseIntNode.hasRegularToString(arguments0Value_) && JSGuards.isUndefined(arguments1Value_)) {
                    return this.parseIntDoubleNoRadix(arguments0Value_, arguments1Value_);
                }
                if ((state_0 & 0x10) != 0 && GlobalBuiltins.JSGlobalParseIntNode.hasRegularToString(arguments0Value_)) {
                    return this.parseIntDouble(arguments0Value_, arguments1Value_, this.parseIntDouble_needsRadixConversion_);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return JSTypesGen.expectDouble(this.executeAndSpecialize((state_0 & 0x680) == 0 && (state_0 & 0x7F) != 0 ? (Number)arguments0Value_int : (Number)((state_0 & 0x380) == 0 && (state_0 & 0x7F) != 0 ? (Number)arguments0Value_long : (Number)arguments0Value_), arguments1Value_));
        }

        @Override
        public int executeInt(VirtualFrame frameValue) throws UnexpectedResultException {
            int state_0 = this.state_0_;
            if ((state_0 & 0x62) != 0) {
                return JSTypesGen.expectInteger(this.execute(frameValue));
            }
            if ((state_0 & 4) == 0 && (state_0 & 5) != 0) {
                return this.executeInt_int4(state_0, frameValue);
            }
            if ((state_0 & 1) == 0 && (state_0 & 5) != 0) {
                return this.executeInt_double5(state_0, frameValue);
            }
            return this.executeInt_generic6(state_0, frameValue);
        }

        private int executeInt_int4(int state_0, VirtualFrame frameValue) throws UnexpectedResultException {
            int arguments0Value_;
            try {
                arguments0Value_ = this.arguments0_.executeInt(frameValue);
            }
            catch (UnexpectedResultException ex) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                Object arguments1Value = this.arguments1_.execute(frameValue);
                return JSTypesGen.expectInteger(this.executeAndSpecialize(ex.getResult(), arguments1Value));
            }
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            assert ((state_0 & 1) != 0);
            if (JSGuards.isUndefined(arguments1Value_)) {
                return this.parseIntNoRadix(arguments0Value_, arguments1Value_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return JSTypesGen.expectInteger(this.executeAndSpecialize(arguments0Value_, arguments1Value_));
        }

        private int executeInt_double5(int state_0, VirtualFrame frameValue) throws UnexpectedResultException {
            double arguments0Value_;
            long arguments0Value_long = 0L;
            int arguments0Value_int = 0;
            try {
                if ((state_0 & 0x700) == 0 && (state_0 & 0x7F) != 0) {
                    arguments0Value_ = this.arguments0_.executeDouble(frameValue);
                } else if ((state_0 & 0x680) == 0 && (state_0 & 0x7F) != 0) {
                    arguments0Value_int = this.arguments0_.executeInt(frameValue);
                    arguments0Value_ = JSTypes.intToDouble(arguments0Value_int);
                } else if ((state_0 & 0x380) == 0 && (state_0 & 0x7F) != 0) {
                    arguments0Value_long = this.arguments0_.executeLong(frameValue);
                    arguments0Value_ = JSTypes.longToDouble(arguments0Value_long);
                } else {
                    Object arguments0Value__ = this.arguments0_.execute(frameValue);
                    arguments0Value_ = JSTypesGen.expectImplicitDouble((state_0 & 0x780) >>> 7, arguments0Value__);
                }
            }
            catch (UnexpectedResultException ex) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                Object arguments1Value = this.arguments1_.execute(frameValue);
                return JSTypesGen.expectInteger(this.executeAndSpecialize(ex.getResult(), arguments1Value));
            }
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            assert ((state_0 & 4) != 0);
            if (GlobalBuiltins.JSGlobalParseIntNode.hasRegularToStringInInt32Range(arguments0Value_) && JSGuards.isUndefined(arguments1Value_)) {
                return this.parseIntDoubleToInt(arguments0Value_, arguments1Value_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return JSTypesGen.expectInteger(this.executeAndSpecialize((state_0 & 0x680) == 0 && (state_0 & 0x7F) != 0 ? (Number)arguments0Value_int : (Number)((state_0 & 0x380) == 0 && (state_0 & 0x7F) != 0 ? (Number)arguments0Value_long : (Number)arguments0Value_), arguments1Value_));
        }

        private int executeInt_generic6(int state_0, VirtualFrame frameValue) throws UnexpectedResultException {
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            if ((state_0 & 5) != 0) {
                double arguments0Value__;
                if ((state_0 & 1) != 0 && arguments0Value_ instanceof Integer) {
                    int arguments0Value__2 = (Integer)arguments0Value_;
                    if (JSGuards.isUndefined(arguments1Value_)) {
                        return this.parseIntNoRadix(arguments0Value__2, arguments1Value_);
                    }
                }
                if ((state_0 & 4) != 0 && JSTypesGen.isImplicitDouble((state_0 & 0x780) >>> 7, arguments0Value_) && GlobalBuiltins.JSGlobalParseIntNode.hasRegularToStringInInt32Range(arguments0Value__ = JSTypesGen.asImplicitDouble((state_0 & 0x780) >>> 7, arguments0Value_)) && JSGuards.isUndefined(arguments1Value_)) {
                    return this.parseIntDoubleToInt(arguments0Value__, arguments1Value_);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return JSTypesGen.expectInteger(this.executeAndSpecialize(arguments0Value_, arguments1Value_));
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            int state_0 = this.state_0_;
            try {
                if ((state_0 & 0x7A) == 0 && (state_0 & 0x7F) != 0) {
                    this.executeInt(frameValue);
                    return;
                }
                if ((state_0 & 0x67) == 0 && (state_0 & 0x7F) != 0) {
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

        private Object executeAndSpecialize(Object arguments0Value, Object arguments1Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int doubleCast0;
                int state_0 = this.state_0_;
                if (arguments0Value instanceof Integer) {
                    int arguments0Value_ = (Integer)arguments0Value;
                    if (JSGuards.isUndefined(arguments1Value)) {
                        this.state_0_ = state_0 |= 1;
                        lock.unlock();
                        hasLock = false;
                        Integer n = this.parseIntNoRadix(arguments0Value_, arguments1Value);
                        return n;
                    }
                    if (!JSGuards.isUndefined(arguments1Value)) {
                        this.parseIntInt_needsRadixConversion_ = BranchProfile.create();
                        this.state_0_ = state_0 |= 2;
                        lock.unlock();
                        hasLock = false;
                        Object object = this.parseIntInt(arguments0Value_, arguments1Value, this.parseIntInt_needsRadixConversion_);
                        return object;
                    }
                }
                if ((doubleCast0 = JSTypesGen.specializeImplicitDouble(arguments0Value)) != 0) {
                    double arguments0Value_ = JSTypesGen.asImplicitDouble(doubleCast0, arguments0Value);
                    if (GlobalBuiltins.JSGlobalParseIntNode.hasRegularToStringInInt32Range(arguments0Value_) && JSGuards.isUndefined(arguments1Value)) {
                        state_0 |= doubleCast0 << 7;
                        this.state_0_ = state_0 |= 4;
                        lock.unlock();
                        hasLock = false;
                        Integer n = this.parseIntDoubleToInt(arguments0Value_, arguments1Value);
                        return n;
                    }
                    if (GlobalBuiltins.JSGlobalParseIntNode.hasRegularToString(arguments0Value_) && JSGuards.isUndefined(arguments1Value)) {
                        state_0 |= doubleCast0 << 7;
                        this.state_0_ = state_0 |= 8;
                        lock.unlock();
                        hasLock = false;
                        Double d = this.parseIntDoubleNoRadix(arguments0Value_, arguments1Value);
                        return d;
                    }
                    if (GlobalBuiltins.JSGlobalParseIntNode.hasRegularToString(arguments0Value_)) {
                        this.parseIntDouble_needsRadixConversion_ = BranchProfile.create();
                        state_0 |= doubleCast0 << 7;
                        this.state_0_ = state_0 |= 0x10;
                        lock.unlock();
                        hasLock = false;
                        Double d = this.parseIntDouble(arguments0Value_, arguments1Value, this.parseIntDouble_needsRadixConversion_);
                        return d;
                    }
                }
                if (arguments0Value instanceof TruffleString) {
                    int arguments1Value_;
                    TruffleString arguments0Value_ = (TruffleString)arguments0Value;
                    if (arguments1Value instanceof Integer && (arguments1Value_ = ((Integer)arguments1Value).intValue()) == 10 && JSGuards.stringLength(arguments0Value_) < 15) {
                        this.parseIntStringInt10_readRawNode_ = super.insert(TruffleString.ReadCharUTF16Node.create());
                        this.state_0_ = state_0 |= 0x20;
                        lock.unlock();
                        hasLock = false;
                        Object object = this.parseIntStringInt10(arguments0Value_, arguments1Value_, this.parseIntStringInt10_readRawNode_);
                        return object;
                    }
                }
                if (!GlobalBuiltins.JSGlobalParseIntNode.isShortStringInt10(arguments0Value, arguments1Value)) {
                    ParseIntGenericData s6_ = super.insert(new ParseIntGenericData());
                    s6_.toStringNode_ = s6_.insertAccessor(JSToStringNode.create());
                    s6_.needsRadix16_ = BranchProfile.create();
                    s6_.needsDontFitLong_ = BranchProfile.create();
                    s6_.readRawNode_ = s6_.insertAccessor(TruffleString.ReadCharUTF16Node.create());
                    s6_.substringNode_ = s6_.insertAccessor(TruffleString.SubstringByteIndexNode.create());
                    VarHandle.storeStoreFence();
                    this.parseIntGeneric_cache = s6_;
                    this.state_0_ = state_0 |= 0x40;
                    lock.unlock();
                    hasLock = false;
                    Object object = this.parseIntGeneric(arguments0Value, arguments1Value, s6_.toStringNode_, s6_.needsRadix16_, s6_.needsDontFitLong_, s6_.readRawNode_, s6_.substringNode_);
                    return object;
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
            if ((state_0 & 0x7F) == 0) {
                return NodeCost.UNINITIALIZED;
            }
            if ((state_0 & 0x7F & (state_0 & 0x7F) - 1) == 0) {
                return NodeCost.MONOMORPHIC;
            }
            return NodeCost.POLYMORPHIC;
        }

        @Override
        public Introspection getIntrospectionData() {
            ArrayList<List<Cloneable>> cached;
            Object[] data = new Object[8];
            data[0] = 0;
            int state_0 = this.state_0_;
            Object[] s = new Object[3];
            s[0] = "parseIntNoRadix";
            s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[1] = s;
            s = new Object[3];
            s[0] = "parseIntInt";
            if ((state_0 & 2) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList<List<Cloneable>>();
                cached.add(Arrays.asList(this.parseIntInt_needsRadixConversion_));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[2] = s;
            s = new Object[3];
            s[0] = "parseIntDoubleToInt";
            s[1] = (state_0 & 4) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[3] = s;
            s = new Object[3];
            s[0] = "parseIntDoubleNoRadix";
            s[1] = (state_0 & 8) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[4] = s;
            s = new Object[3];
            s[0] = "parseIntDouble";
            if ((state_0 & 0x10) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList();
                cached.add(Arrays.asList(this.parseIntDouble_needsRadixConversion_));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[5] = s;
            s = new Object[3];
            s[0] = "parseIntStringInt10";
            if ((state_0 & 0x20) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList();
                cached.add(Arrays.asList(this.parseIntStringInt10_readRawNode_));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[6] = s;
            s = new Object[3];
            s[0] = "parseIntGeneric";
            if ((state_0 & 0x40) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList();
                ParseIntGenericData s6_ = this.parseIntGeneric_cache;
                if (s6_ != null) {
                    cached.add(Arrays.asList(s6_.toStringNode_, s6_.needsRadix16_, s6_.needsDontFitLong_, s6_.readRawNode_, s6_.substringNode_));
                }
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[7] = s;
            return Introspection.Provider.create(data);
        }

        public static GlobalBuiltins.JSGlobalParseIntNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSGlobalParseIntNodeGen(context, builtin, arguments);
        }

        @GeneratedBy(value=GlobalBuiltins.JSGlobalParseIntNode.class)
        private static final class ParseIntGenericData
        extends Node {
            @Node.Child
            JSToStringNode toStringNode_;
            @CompilerDirectives.CompilationFinal
            BranchProfile needsRadix16_;
            @CompilerDirectives.CompilationFinal
            BranchProfile needsDontFitLong_;
            @Node.Child
            TruffleString.ReadCharUTF16Node readRawNode_;
            @Node.Child
            TruffleString.SubstringByteIndexNode substringNode_;

            ParseIntGenericData() {
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

    @GeneratedBy(value=GlobalBuiltins.JSGlobalParseFloatNode.class)
    public static final class JSGlobalParseFloatNodeGen
    extends GlobalBuiltins.JSGlobalParseFloatNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @CompilerDirectives.CompilationFinal
        private ConditionProfile parseFloatDouble_negativeZero_;

        private JSGlobalParseFloatNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            if ((state_0 & 0x7E) == 0 && (state_0 & 0x7F) != 0) {
                return this.execute_int0(state_0, frameValue);
            }
            if ((state_0 & 0x7D) == 0 && (state_0 & 0x7F) != 0) {
                return this.execute_double1(state_0, frameValue);
            }
            if ((state_0 & 0x7B) == 0 && (state_0 & 0x7F) != 0) {
                return this.execute_boolean2(state_0, frameValue);
            }
            return this.execute_generic3(state_0, frameValue);
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
            return this.parseFloatInt(arguments0Value_);
        }

        private Object execute_double1(int state_0, VirtualFrame frameValue) {
            double arguments0Value_;
            long arguments0Value_long = 0L;
            int arguments0Value_int = 0;
            try {
                if ((state_0 & 0x700) == 0 && (state_0 & 0x7F) != 0) {
                    arguments0Value_ = this.arguments0_.executeDouble(frameValue);
                } else if ((state_0 & 0x680) == 0 && (state_0 & 0x7F) != 0) {
                    arguments0Value_int = this.arguments0_.executeInt(frameValue);
                    arguments0Value_ = JSTypes.intToDouble(arguments0Value_int);
                } else if ((state_0 & 0x380) == 0 && (state_0 & 0x7F) != 0) {
                    arguments0Value_long = this.arguments0_.executeLong(frameValue);
                    arguments0Value_ = JSTypes.longToDouble(arguments0Value_long);
                } else {
                    Object arguments0Value__ = this.arguments0_.execute(frameValue);
                    arguments0Value_ = JSTypesGen.expectImplicitDouble((state_0 & 0x780) >>> 7, arguments0Value__);
                }
            }
            catch (UnexpectedResultException ex) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.executeAndSpecialize(ex.getResult());
            }
            assert ((state_0 & 2) != 0);
            return this.parseFloatDouble(arguments0Value_, this.parseFloatDouble_negativeZero_);
        }

        private Object execute_boolean2(int state_0, VirtualFrame frameValue) {
            boolean arguments0Value_;
            try {
                arguments0Value_ = this.arguments0_.executeBoolean(frameValue);
            }
            catch (UnexpectedResultException ex) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.executeAndSpecialize(ex.getResult());
            }
            assert ((state_0 & 4) != 0);
            return this.parseFloatBoolean(arguments0Value_);
        }

        private Object execute_generic3(int state_0, VirtualFrame frameValue) {
            TruffleObject arguments0Value__;
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            if ((state_0 & 1) != 0 && arguments0Value_ instanceof Integer) {
                int arguments0Value__2 = (Integer)arguments0Value_;
                return this.parseFloatInt(arguments0Value__2);
            }
            if ((state_0 & 2) != 0 && JSTypesGen.isImplicitDouble((state_0 & 0x780) >>> 7, arguments0Value_)) {
                double arguments0Value__3 = JSTypesGen.asImplicitDouble((state_0 & 0x780) >>> 7, arguments0Value_);
                return this.parseFloatDouble(arguments0Value__3, this.parseFloatDouble_negativeZero_);
            }
            if ((state_0 & 4) != 0 && arguments0Value_ instanceof Boolean) {
                boolean arguments0Value__4 = (Boolean)arguments0Value_;
                return this.parseFloatBoolean(arguments0Value__4);
            }
            if ((state_0 & 0x18) != 0) {
                if ((state_0 & 8) != 0 && JSGuards.isUndefined(arguments0Value_)) {
                    return this.parseFloatUndefined(arguments0Value_);
                }
                if ((state_0 & 0x10) != 0 && JSGuards.isJSNull(arguments0Value_)) {
                    return this.parseFloatNull(arguments0Value_);
                }
            }
            if ((state_0 & 0x20) != 0 && arguments0Value_ instanceof TruffleString) {
                TruffleString arguments0Value__5 = (TruffleString)arguments0Value_;
                return this.parseFloat(arguments0Value__5);
            }
            if ((state_0 & 0x40) != 0 && arguments0Value_ instanceof TruffleObject && !JSGuards.isJSNull(arguments0Value__ = (TruffleObject)arguments0Value_) && !JSGuards.isUndefined(arguments0Value__) && !JSGuards.isString(arguments0Value__)) {
                return this.parseFloat(arguments0Value__);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_);
        }

        @Override
        public double executeDouble(VirtualFrame frameValue) throws UnexpectedResultException {
            int state_0 = this.state_0_;
            if ((state_0 & 0x7C) == 0 && (state_0 & 0x7E) != 0) {
                return this.executeDouble_double4(state_0, frameValue);
            }
            if ((state_0 & 0x7A) == 0 && (state_0 & 0x7E) != 0) {
                return this.executeDouble_boolean5(state_0, frameValue);
            }
            return this.executeDouble_generic6(state_0, frameValue);
        }

        private double executeDouble_double4(int state_0, VirtualFrame frameValue) throws UnexpectedResultException {
            double arguments0Value_;
            long arguments0Value_long = 0L;
            int arguments0Value_int = 0;
            try {
                if ((state_0 & 0x700) == 0 && (state_0 & 0x7F) != 0) {
                    arguments0Value_ = this.arguments0_.executeDouble(frameValue);
                } else if ((state_0 & 0x680) == 0 && (state_0 & 0x7F) != 0) {
                    arguments0Value_int = this.arguments0_.executeInt(frameValue);
                    arguments0Value_ = JSTypes.intToDouble(arguments0Value_int);
                } else if ((state_0 & 0x380) == 0 && (state_0 & 0x7F) != 0) {
                    arguments0Value_long = this.arguments0_.executeLong(frameValue);
                    arguments0Value_ = JSTypes.longToDouble(arguments0Value_long);
                } else {
                    Object arguments0Value__ = this.arguments0_.execute(frameValue);
                    arguments0Value_ = JSTypesGen.expectImplicitDouble((state_0 & 0x780) >>> 7, arguments0Value__);
                }
            }
            catch (UnexpectedResultException ex) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return JSTypesGen.expectDouble(this.executeAndSpecialize(ex.getResult()));
            }
            assert ((state_0 & 2) != 0);
            return this.parseFloatDouble(arguments0Value_, this.parseFloatDouble_negativeZero_);
        }

        private double executeDouble_boolean5(int state_0, VirtualFrame frameValue) throws UnexpectedResultException {
            boolean arguments0Value_;
            try {
                arguments0Value_ = this.arguments0_.executeBoolean(frameValue);
            }
            catch (UnexpectedResultException ex) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return JSTypesGen.expectDouble(this.executeAndSpecialize(ex.getResult()));
            }
            assert ((state_0 & 4) != 0);
            return this.parseFloatBoolean(arguments0Value_);
        }

        private double executeDouble_generic6(int state_0, VirtualFrame frameValue) throws UnexpectedResultException {
            TruffleObject arguments0Value__;
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            if ((state_0 & 2) != 0 && JSTypesGen.isImplicitDouble((state_0 & 0x780) >>> 7, arguments0Value_)) {
                double arguments0Value__2 = JSTypesGen.asImplicitDouble((state_0 & 0x780) >>> 7, arguments0Value_);
                return this.parseFloatDouble(arguments0Value__2, this.parseFloatDouble_negativeZero_);
            }
            if ((state_0 & 4) != 0 && arguments0Value_ instanceof Boolean) {
                boolean arguments0Value__3 = (Boolean)arguments0Value_;
                return this.parseFloatBoolean(arguments0Value__3);
            }
            if ((state_0 & 0x18) != 0) {
                if ((state_0 & 8) != 0 && JSGuards.isUndefined(arguments0Value_)) {
                    return this.parseFloatUndefined(arguments0Value_);
                }
                if ((state_0 & 0x10) != 0 && JSGuards.isJSNull(arguments0Value_)) {
                    return this.parseFloatNull(arguments0Value_);
                }
            }
            if ((state_0 & 0x20) != 0 && arguments0Value_ instanceof TruffleString) {
                TruffleString arguments0Value__4 = (TruffleString)arguments0Value_;
                return this.parseFloat(arguments0Value__4);
            }
            if ((state_0 & 0x40) != 0 && arguments0Value_ instanceof TruffleObject && !JSGuards.isJSNull(arguments0Value__ = (TruffleObject)arguments0Value_) && !JSGuards.isUndefined(arguments0Value__) && !JSGuards.isString(arguments0Value__)) {
                return this.parseFloat(arguments0Value__);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return JSTypesGen.expectDouble(this.executeAndSpecialize(arguments0Value_));
        }

        @Override
        public int executeInt(VirtualFrame frameValue) throws UnexpectedResultException {
            int arguments0Value_;
            int state_0 = this.state_0_;
            try {
                arguments0Value_ = this.arguments0_.executeInt(frameValue);
            }
            catch (UnexpectedResultException ex) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return JSTypesGen.expectInteger(this.executeAndSpecialize(ex.getResult()));
            }
            if ((state_0 & 1) != 0) {
                return this.parseFloatInt(arguments0Value_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return JSTypesGen.expectInteger(this.executeAndSpecialize(arguments0Value_));
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            int state_0 = this.state_0_;
            try {
                if ((state_0 & 0x7E) == 0 && (state_0 & 0x7F) != 0) {
                    this.executeInt(frameValue);
                    return;
                }
                if ((state_0 & 1) == 0 && (state_0 & 0x7F) != 0) {
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

        private Object executeAndSpecialize(Object arguments0Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                TruffleObject arguments0Value_;
                int state_0 = this.state_0_;
                if (arguments0Value instanceof Integer) {
                    int arguments0Value_2 = (Integer)arguments0Value;
                    this.state_0_ = state_0 |= 1;
                    lock.unlock();
                    hasLock = false;
                    Integer n = this.parseFloatInt(arguments0Value_2);
                    return n;
                }
                int doubleCast0 = JSTypesGen.specializeImplicitDouble(arguments0Value);
                if (doubleCast0 != 0) {
                    double arguments0Value_3 = JSTypesGen.asImplicitDouble(doubleCast0, arguments0Value);
                    this.parseFloatDouble_negativeZero_ = ConditionProfile.createBinaryProfile();
                    state_0 |= doubleCast0 << 7;
                    this.state_0_ = state_0 |= 2;
                    lock.unlock();
                    hasLock = false;
                    Double d = this.parseFloatDouble(arguments0Value_3, this.parseFloatDouble_negativeZero_);
                    return d;
                }
                if (arguments0Value instanceof Boolean) {
                    boolean arguments0Value_4 = (Boolean)arguments0Value;
                    this.state_0_ = state_0 |= 4;
                    lock.unlock();
                    hasLock = false;
                    Double d = this.parseFloatBoolean(arguments0Value_4);
                    return d;
                }
                if (JSGuards.isUndefined(arguments0Value)) {
                    this.state_0_ = state_0 |= 8;
                    lock.unlock();
                    hasLock = false;
                    Double arguments0Value_4 = this.parseFloatUndefined(arguments0Value);
                    return arguments0Value_4;
                }
                if (JSGuards.isJSNull(arguments0Value)) {
                    this.state_0_ = state_0 |= 0x10;
                    lock.unlock();
                    hasLock = false;
                    Double arguments0Value_4 = this.parseFloatNull(arguments0Value);
                    return arguments0Value_4;
                }
                if (arguments0Value instanceof TruffleString) {
                    TruffleString arguments0Value_5 = (TruffleString)arguments0Value;
                    this.state_0_ = state_0 |= 0x20;
                    lock.unlock();
                    hasLock = false;
                    Double d = this.parseFloat(arguments0Value_5);
                    return d;
                }
                if (arguments0Value instanceof TruffleObject && !JSGuards.isJSNull(arguments0Value_ = (TruffleObject)arguments0Value) && !JSGuards.isUndefined(arguments0Value_) && !JSGuards.isString(arguments0Value_)) {
                    this.state_0_ = state_0 |= 0x40;
                    lock.unlock();
                    hasLock = false;
                    Double d = this.parseFloat(arguments0Value_);
                    return d;
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
            if ((state_0 & 0x7F) == 0) {
                return NodeCost.UNINITIALIZED;
            }
            if ((state_0 & 0x7F & (state_0 & 0x7F) - 1) == 0) {
                return NodeCost.MONOMORPHIC;
            }
            return NodeCost.POLYMORPHIC;
        }

        @Override
        public Introspection getIntrospectionData() {
            Object[] data = new Object[8];
            data[0] = 0;
            int state_0 = this.state_0_;
            Object[] s = new Object[3];
            s[0] = "parseFloatInt";
            s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[1] = s;
            s = new Object[3];
            s[0] = "parseFloatDouble";
            if ((state_0 & 2) != 0) {
                s[1] = (byte)1;
                ArrayList<List<ConditionProfile>> cached = new ArrayList<List<ConditionProfile>>();
                cached.add(Arrays.asList(this.parseFloatDouble_negativeZero_));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[2] = s;
            s = new Object[3];
            s[0] = "parseFloatBoolean";
            s[1] = (state_0 & 4) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[3] = s;
            s = new Object[3];
            s[0] = "parseFloatUndefined";
            s[1] = (state_0 & 8) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[4] = s;
            s = new Object[3];
            s[0] = "parseFloatNull";
            s[1] = (state_0 & 0x10) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[5] = s;
            s = new Object[3];
            s[0] = "parseFloat";
            s[1] = (state_0 & 0x20) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[6] = s;
            s = new Object[3];
            s[0] = "parseFloat";
            s[1] = (state_0 & 0x40) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[7] = s;
            return Introspection.Provider.create(data);
        }

        public static GlobalBuiltins.JSGlobalParseFloatNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSGlobalParseFloatNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=GlobalBuiltins.JSGlobalIsFiniteNode.class)
    public static final class JSGlobalIsFiniteNodeGen
    extends GlobalBuiltins.JSGlobalIsFiniteNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private JSToDoubleNode isFiniteGeneric_toDoubleNode_;

        private JSGlobalIsFiniteNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            if ((state_0 & 0xE) == 0 && (state_0 & 0xF) != 0) {
                return this.execute_int0(state_0, frameValue);
            }
            if ((state_0 & 0xD) == 0 && (state_0 & 0xF) != 0) {
                return this.execute_double1(state_0, frameValue);
            }
            return this.execute_generic2(state_0, frameValue);
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
            return GlobalBuiltins.JSGlobalIsFiniteNode.isFiniteInt(arguments0Value_);
        }

        private Object execute_double1(int state_0, VirtualFrame frameValue) {
            double arguments0Value_;
            long arguments0Value_long = 0L;
            int arguments0Value_int = 0;
            try {
                if ((state_0 & 0xE0) == 0 && (state_0 & 0xF) != 0) {
                    arguments0Value_ = this.arguments0_.executeDouble(frameValue);
                } else if ((state_0 & 0xD0) == 0 && (state_0 & 0xF) != 0) {
                    arguments0Value_int = this.arguments0_.executeInt(frameValue);
                    arguments0Value_ = JSTypes.intToDouble(arguments0Value_int);
                } else if ((state_0 & 0x70) == 0 && (state_0 & 0xF) != 0) {
                    arguments0Value_long = this.arguments0_.executeLong(frameValue);
                    arguments0Value_ = JSTypes.longToDouble(arguments0Value_long);
                } else {
                    Object arguments0Value__ = this.arguments0_.execute(frameValue);
                    arguments0Value_ = JSTypesGen.expectImplicitDouble((state_0 & 0xF0) >>> 4, arguments0Value__);
                }
            }
            catch (UnexpectedResultException ex) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.executeAndSpecialize(ex.getResult());
            }
            assert ((state_0 & 2) != 0);
            return GlobalBuiltins.JSGlobalIsFiniteNode.isFiniteDouble(arguments0Value_);
        }

        private Object execute_generic2(int state_0, VirtualFrame frameValue) {
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            if ((state_0 & 1) != 0 && arguments0Value_ instanceof Integer) {
                int arguments0Value__ = (Integer)arguments0Value_;
                return GlobalBuiltins.JSGlobalIsFiniteNode.isFiniteInt(arguments0Value__);
            }
            if ((state_0 & 2) != 0 && JSTypesGen.isImplicitDouble((state_0 & 0xF0) >>> 4, arguments0Value_)) {
                double arguments0Value__ = JSTypesGen.asImplicitDouble((state_0 & 0xF0) >>> 4, arguments0Value_);
                return GlobalBuiltins.JSGlobalIsFiniteNode.isFiniteDouble(arguments0Value__);
            }
            if ((state_0 & 0xC) != 0) {
                if ((state_0 & 4) != 0 && !JSGuards.isUndefined(arguments0Value_)) {
                    return GlobalBuiltins.JSGlobalIsFiniteNode.isFiniteGeneric(arguments0Value_, this.isFiniteGeneric_toDoubleNode_);
                }
                if ((state_0 & 8) != 0 && JSGuards.isUndefined(arguments0Value_)) {
                    return GlobalBuiltins.JSGlobalIsFiniteNode.isFiniteUndefined(arguments0Value_);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_);
        }

        @Override
        public boolean executeBoolean(VirtualFrame frameValue) {
            int state_0 = this.state_0_;
            if ((state_0 & 0xE) == 0 && (state_0 & 0xF) != 0) {
                return this.executeBoolean_int3(state_0, frameValue);
            }
            if ((state_0 & 0xD) == 0 && (state_0 & 0xF) != 0) {
                return this.executeBoolean_double4(state_0, frameValue);
            }
            return this.executeBoolean_generic5(state_0, frameValue);
        }

        private boolean executeBoolean_int3(int state_0, VirtualFrame frameValue) {
            int arguments0Value_;
            try {
                arguments0Value_ = this.arguments0_.executeInt(frameValue);
            }
            catch (UnexpectedResultException ex) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.executeAndSpecialize(ex.getResult());
            }
            assert ((state_0 & 1) != 0);
            return GlobalBuiltins.JSGlobalIsFiniteNode.isFiniteInt(arguments0Value_);
        }

        private boolean executeBoolean_double4(int state_0, VirtualFrame frameValue) {
            double arguments0Value_;
            long arguments0Value_long = 0L;
            int arguments0Value_int = 0;
            try {
                if ((state_0 & 0xE0) == 0 && (state_0 & 0xF) != 0) {
                    arguments0Value_ = this.arguments0_.executeDouble(frameValue);
                } else if ((state_0 & 0xD0) == 0 && (state_0 & 0xF) != 0) {
                    arguments0Value_int = this.arguments0_.executeInt(frameValue);
                    arguments0Value_ = JSTypes.intToDouble(arguments0Value_int);
                } else if ((state_0 & 0x70) == 0 && (state_0 & 0xF) != 0) {
                    arguments0Value_long = this.arguments0_.executeLong(frameValue);
                    arguments0Value_ = JSTypes.longToDouble(arguments0Value_long);
                } else {
                    Object arguments0Value__ = this.arguments0_.execute(frameValue);
                    arguments0Value_ = JSTypesGen.expectImplicitDouble((state_0 & 0xF0) >>> 4, arguments0Value__);
                }
            }
            catch (UnexpectedResultException ex) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.executeAndSpecialize(ex.getResult());
            }
            assert ((state_0 & 2) != 0);
            return GlobalBuiltins.JSGlobalIsFiniteNode.isFiniteDouble(arguments0Value_);
        }

        private boolean executeBoolean_generic5(int state_0, VirtualFrame frameValue) {
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            if ((state_0 & 1) != 0 && arguments0Value_ instanceof Integer) {
                int arguments0Value__ = (Integer)arguments0Value_;
                return GlobalBuiltins.JSGlobalIsFiniteNode.isFiniteInt(arguments0Value__);
            }
            if ((state_0 & 2) != 0 && JSTypesGen.isImplicitDouble((state_0 & 0xF0) >>> 4, arguments0Value_)) {
                double arguments0Value__ = JSTypesGen.asImplicitDouble((state_0 & 0xF0) >>> 4, arguments0Value_);
                return GlobalBuiltins.JSGlobalIsFiniteNode.isFiniteDouble(arguments0Value__);
            }
            if ((state_0 & 0xC) != 0) {
                if ((state_0 & 4) != 0 && !JSGuards.isUndefined(arguments0Value_)) {
                    return GlobalBuiltins.JSGlobalIsFiniteNode.isFiniteGeneric(arguments0Value_, this.isFiniteGeneric_toDoubleNode_);
                }
                if ((state_0 & 8) != 0 && JSGuards.isUndefined(arguments0Value_)) {
                    return GlobalBuiltins.JSGlobalIsFiniteNode.isFiniteUndefined(arguments0Value_);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.executeBoolean(frameValue);
        }

        private boolean executeAndSpecialize(Object arguments0Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                boolean bl;
                int state_0 = this.state_0_;
                if (arguments0Value instanceof Integer) {
                    int arguments0Value_ = (Integer)arguments0Value;
                    this.state_0_ = state_0 |= 1;
                    lock.unlock();
                    hasLock = false;
                    boolean bl2 = GlobalBuiltins.JSGlobalIsFiniteNode.isFiniteInt(arguments0Value_);
                    return bl2;
                }
                int doubleCast0 = JSTypesGen.specializeImplicitDouble(arguments0Value);
                if (doubleCast0 != 0) {
                    double arguments0Value_ = JSTypesGen.asImplicitDouble(doubleCast0, arguments0Value);
                    state_0 |= doubleCast0 << 4;
                    this.state_0_ = state_0 |= 2;
                    lock.unlock();
                    hasLock = false;
                    boolean bl3 = GlobalBuiltins.JSGlobalIsFiniteNode.isFiniteDouble(arguments0Value_);
                    return bl3;
                }
                if (!JSGuards.isUndefined(arguments0Value)) {
                    this.isFiniteGeneric_toDoubleNode_ = super.insert(JSToDoubleNode.create());
                    this.state_0_ = state_0 |= 4;
                    lock.unlock();
                    hasLock = false;
                    bl = GlobalBuiltins.JSGlobalIsFiniteNode.isFiniteGeneric(arguments0Value, this.isFiniteGeneric_toDoubleNode_);
                    return bl;
                }
                if (JSGuards.isUndefined(arguments0Value)) {
                    this.state_0_ = state_0 |= 8;
                    lock.unlock();
                    hasLock = false;
                    bl = GlobalBuiltins.JSGlobalIsFiniteNode.isFiniteUndefined(arguments0Value);
                    return bl;
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
            if ((state_0 & 0xF) == 0) {
                return NodeCost.UNINITIALIZED;
            }
            if ((state_0 & 0xF & (state_0 & 0xF) - 1) == 0) {
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
            s[0] = "isFiniteInt";
            s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[1] = s;
            s = new Object[3];
            s[0] = "isFiniteDouble";
            s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[2] = s;
            s = new Object[3];
            s[0] = "isFiniteGeneric";
            if ((state_0 & 4) != 0) {
                s[1] = (byte)1;
                ArrayList<List<JSToDoubleNode>> cached = new ArrayList<List<JSToDoubleNode>>();
                cached.add(Arrays.asList(this.isFiniteGeneric_toDoubleNode_));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[3] = s;
            s = new Object[3];
            s[0] = "isFiniteUndefined";
            s[1] = (state_0 & 8) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[4] = s;
            return Introspection.Provider.create(data);
        }

        public static GlobalBuiltins.JSGlobalIsFiniteNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSGlobalIsFiniteNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=GlobalBuiltins.JSGlobalIsNaNNode.class)
    public static final class JSGlobalIsNaNNodeGen
    extends GlobalBuiltins.JSGlobalIsNaNNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private JSToDoubleNode isNaNGeneric_toDoubleNode_;

        private JSGlobalIsNaNNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            if ((state_0 & 0xE) == 0 && (state_0 & 0xF) != 0) {
                return this.execute_int0(state_0, frameValue);
            }
            if ((state_0 & 0xD) == 0 && (state_0 & 0xF) != 0) {
                return this.execute_double1(state_0, frameValue);
            }
            return this.execute_generic2(state_0, frameValue);
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
            return GlobalBuiltins.JSGlobalIsNaNNode.isNaNInt(arguments0Value_);
        }

        private Object execute_double1(int state_0, VirtualFrame frameValue) {
            double arguments0Value_;
            long arguments0Value_long = 0L;
            int arguments0Value_int = 0;
            try {
                if ((state_0 & 0xE0) == 0 && (state_0 & 0xF) != 0) {
                    arguments0Value_ = this.arguments0_.executeDouble(frameValue);
                } else if ((state_0 & 0xD0) == 0 && (state_0 & 0xF) != 0) {
                    arguments0Value_int = this.arguments0_.executeInt(frameValue);
                    arguments0Value_ = JSTypes.intToDouble(arguments0Value_int);
                } else if ((state_0 & 0x70) == 0 && (state_0 & 0xF) != 0) {
                    arguments0Value_long = this.arguments0_.executeLong(frameValue);
                    arguments0Value_ = JSTypes.longToDouble(arguments0Value_long);
                } else {
                    Object arguments0Value__ = this.arguments0_.execute(frameValue);
                    arguments0Value_ = JSTypesGen.expectImplicitDouble((state_0 & 0xF0) >>> 4, arguments0Value__);
                }
            }
            catch (UnexpectedResultException ex) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.executeAndSpecialize(ex.getResult());
            }
            assert ((state_0 & 2) != 0);
            return GlobalBuiltins.JSGlobalIsNaNNode.isNaNDouble(arguments0Value_);
        }

        private Object execute_generic2(int state_0, VirtualFrame frameValue) {
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            if ((state_0 & 1) != 0 && arguments0Value_ instanceof Integer) {
                int arguments0Value__ = (Integer)arguments0Value_;
                return GlobalBuiltins.JSGlobalIsNaNNode.isNaNInt(arguments0Value__);
            }
            if ((state_0 & 2) != 0 && JSTypesGen.isImplicitDouble((state_0 & 0xF0) >>> 4, arguments0Value_)) {
                double arguments0Value__ = JSTypesGen.asImplicitDouble((state_0 & 0xF0) >>> 4, arguments0Value_);
                return GlobalBuiltins.JSGlobalIsNaNNode.isNaNDouble(arguments0Value__);
            }
            if ((state_0 & 0xC) != 0) {
                if ((state_0 & 4) != 0 && !JSGuards.isUndefined(arguments0Value_)) {
                    return GlobalBuiltins.JSGlobalIsNaNNode.isNaNGeneric(arguments0Value_, this.isNaNGeneric_toDoubleNode_);
                }
                if ((state_0 & 8) != 0 && JSGuards.isUndefined(arguments0Value_)) {
                    return GlobalBuiltins.JSGlobalIsNaNNode.isNaNUndefined(arguments0Value_);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_);
        }

        @Override
        public boolean executeBoolean(VirtualFrame frameValue) {
            int state_0 = this.state_0_;
            if ((state_0 & 0xE) == 0 && (state_0 & 0xF) != 0) {
                return this.executeBoolean_int3(state_0, frameValue);
            }
            if ((state_0 & 0xD) == 0 && (state_0 & 0xF) != 0) {
                return this.executeBoolean_double4(state_0, frameValue);
            }
            return this.executeBoolean_generic5(state_0, frameValue);
        }

        private boolean executeBoolean_int3(int state_0, VirtualFrame frameValue) {
            int arguments0Value_;
            try {
                arguments0Value_ = this.arguments0_.executeInt(frameValue);
            }
            catch (UnexpectedResultException ex) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.executeAndSpecialize(ex.getResult());
            }
            assert ((state_0 & 1) != 0);
            return GlobalBuiltins.JSGlobalIsNaNNode.isNaNInt(arguments0Value_);
        }

        private boolean executeBoolean_double4(int state_0, VirtualFrame frameValue) {
            double arguments0Value_;
            long arguments0Value_long = 0L;
            int arguments0Value_int = 0;
            try {
                if ((state_0 & 0xE0) == 0 && (state_0 & 0xF) != 0) {
                    arguments0Value_ = this.arguments0_.executeDouble(frameValue);
                } else if ((state_0 & 0xD0) == 0 && (state_0 & 0xF) != 0) {
                    arguments0Value_int = this.arguments0_.executeInt(frameValue);
                    arguments0Value_ = JSTypes.intToDouble(arguments0Value_int);
                } else if ((state_0 & 0x70) == 0 && (state_0 & 0xF) != 0) {
                    arguments0Value_long = this.arguments0_.executeLong(frameValue);
                    arguments0Value_ = JSTypes.longToDouble(arguments0Value_long);
                } else {
                    Object arguments0Value__ = this.arguments0_.execute(frameValue);
                    arguments0Value_ = JSTypesGen.expectImplicitDouble((state_0 & 0xF0) >>> 4, arguments0Value__);
                }
            }
            catch (UnexpectedResultException ex) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.executeAndSpecialize(ex.getResult());
            }
            assert ((state_0 & 2) != 0);
            return GlobalBuiltins.JSGlobalIsNaNNode.isNaNDouble(arguments0Value_);
        }

        private boolean executeBoolean_generic5(int state_0, VirtualFrame frameValue) {
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            if ((state_0 & 1) != 0 && arguments0Value_ instanceof Integer) {
                int arguments0Value__ = (Integer)arguments0Value_;
                return GlobalBuiltins.JSGlobalIsNaNNode.isNaNInt(arguments0Value__);
            }
            if ((state_0 & 2) != 0 && JSTypesGen.isImplicitDouble((state_0 & 0xF0) >>> 4, arguments0Value_)) {
                double arguments0Value__ = JSTypesGen.asImplicitDouble((state_0 & 0xF0) >>> 4, arguments0Value_);
                return GlobalBuiltins.JSGlobalIsNaNNode.isNaNDouble(arguments0Value__);
            }
            if ((state_0 & 0xC) != 0) {
                if ((state_0 & 4) != 0 && !JSGuards.isUndefined(arguments0Value_)) {
                    return GlobalBuiltins.JSGlobalIsNaNNode.isNaNGeneric(arguments0Value_, this.isNaNGeneric_toDoubleNode_);
                }
                if ((state_0 & 8) != 0 && JSGuards.isUndefined(arguments0Value_)) {
                    return GlobalBuiltins.JSGlobalIsNaNNode.isNaNUndefined(arguments0Value_);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.executeBoolean(frameValue);
        }

        private boolean executeAndSpecialize(Object arguments0Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                boolean bl;
                int state_0 = this.state_0_;
                if (arguments0Value instanceof Integer) {
                    int arguments0Value_ = (Integer)arguments0Value;
                    this.state_0_ = state_0 |= 1;
                    lock.unlock();
                    hasLock = false;
                    boolean bl2 = GlobalBuiltins.JSGlobalIsNaNNode.isNaNInt(arguments0Value_);
                    return bl2;
                }
                int doubleCast0 = JSTypesGen.specializeImplicitDouble(arguments0Value);
                if (doubleCast0 != 0) {
                    double arguments0Value_ = JSTypesGen.asImplicitDouble(doubleCast0, arguments0Value);
                    state_0 |= doubleCast0 << 4;
                    this.state_0_ = state_0 |= 2;
                    lock.unlock();
                    hasLock = false;
                    boolean bl3 = GlobalBuiltins.JSGlobalIsNaNNode.isNaNDouble(arguments0Value_);
                    return bl3;
                }
                if (!JSGuards.isUndefined(arguments0Value)) {
                    this.isNaNGeneric_toDoubleNode_ = super.insert(JSToDoubleNode.create());
                    this.state_0_ = state_0 |= 4;
                    lock.unlock();
                    hasLock = false;
                    bl = GlobalBuiltins.JSGlobalIsNaNNode.isNaNGeneric(arguments0Value, this.isNaNGeneric_toDoubleNode_);
                    return bl;
                }
                if (JSGuards.isUndefined(arguments0Value)) {
                    this.state_0_ = state_0 |= 8;
                    lock.unlock();
                    hasLock = false;
                    bl = GlobalBuiltins.JSGlobalIsNaNNode.isNaNUndefined(arguments0Value);
                    return bl;
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
            if ((state_0 & 0xF) == 0) {
                return NodeCost.UNINITIALIZED;
            }
            if ((state_0 & 0xF & (state_0 & 0xF) - 1) == 0) {
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
            s[0] = "isNaNInt";
            s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[1] = s;
            s = new Object[3];
            s[0] = "isNaNDouble";
            s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[2] = s;
            s = new Object[3];
            s[0] = "isNaNGeneric";
            if ((state_0 & 4) != 0) {
                s[1] = (byte)1;
                ArrayList<List<JSToDoubleNode>> cached = new ArrayList<List<JSToDoubleNode>>();
                cached.add(Arrays.asList(this.isNaNGeneric_toDoubleNode_));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[3] = s;
            s = new Object[3];
            s[0] = "isNaNUndefined";
            s[1] = (state_0 & 8) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[4] = s;
            return Introspection.Provider.create(data);
        }

        public static GlobalBuiltins.JSGlobalIsNaNNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSGlobalIsNaNNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=GlobalBuiltins.GlobalScriptingEXECNode.class)
    public static final class GlobalScriptingEXECNodeGen
    extends GlobalBuiltins.GlobalScriptingEXECNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;

        private GlobalScriptingEXECNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            return this.exec(arguments0Value_, arguments1Value_);
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
            s[0] = "exec";
            s[1] = (byte)1;
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static GlobalBuiltins.GlobalScriptingEXECNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new GlobalScriptingEXECNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=GlobalBuiltins.GlobalNashornExtensionParseToJSONNode.class)
    public static final class GlobalNashornExtensionParseToJSONNodeGen
    extends GlobalBuiltins.GlobalNashornExtensionParseToJSONNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @Node.Child
        private JavaScriptNode arguments2_;

        private GlobalNashornExtensionParseToJSONNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            return this.parseToJSON(arguments0Value_, arguments1Value_, arguments2Value_);
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
            s[0] = "parseToJSON";
            s[1] = (byte)1;
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static GlobalBuiltins.GlobalNashornExtensionParseToJSONNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new GlobalNashornExtensionParseToJSONNodeGen(context, builtin, arguments);
        }
    }
}

