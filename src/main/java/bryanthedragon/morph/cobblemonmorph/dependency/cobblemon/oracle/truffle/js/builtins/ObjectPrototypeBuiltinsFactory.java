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
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.builtins.ObjectPrototypeBuiltins;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.SafeInteger;
import com.oracle.truffle.js.runtime.Symbol;
import com.oracle.truffle.js.runtime.builtins.JSClass;
import com.oracle.truffle.js.runtime.builtins.JSProxyObject;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=ObjectPrototypeBuiltins.class)
public final class ObjectPrototypeBuiltinsFactory {
    private static final LibraryFactory<InteropLibrary> INTEROP_LIBRARY_ = LibraryFactory.resolve(InteropLibrary.class);

    @GeneratedBy(value=ObjectPrototypeBuiltins.ObjectPrototypeLookupGetterOrSetterNode.class)
    public static final class ObjectPrototypeLookupGetterOrSetterNodeGen
    extends ObjectPrototypeBuiltins.ObjectPrototypeLookupGetterOrSetterNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;

        private ObjectPrototypeLookupGetterOrSetterNodeGen(JSContext context, JSBuiltin builtin, boolean getter, JavaScriptNode[] arguments) {
            super(context, builtin, getter);
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
            return this.lookup(arguments0Value_, arguments1Value_);
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
            s[0] = "lookup";
            s[1] = (byte)1;
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static ObjectPrototypeBuiltins.ObjectPrototypeLookupGetterOrSetterNode create(JSContext context, JSBuiltin builtin, boolean getter, JavaScriptNode[] arguments) {
            return new ObjectPrototypeLookupGetterOrSetterNodeGen(context, builtin, getter, arguments);
        }
    }

    @GeneratedBy(value=ObjectPrototypeBuiltins.ObjectPrototypeDefineGetterOrSetterNode.class)
    public static final class ObjectPrototypeDefineGetterOrSetterNodeGen
    extends ObjectPrototypeBuiltins.ObjectPrototypeDefineGetterOrSetterNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @Node.Child
        private JavaScriptNode arguments2_;

        private ObjectPrototypeDefineGetterOrSetterNodeGen(JSContext context, JSBuiltin builtin, boolean getter, JavaScriptNode[] arguments) {
            super(context, builtin, getter);
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
            return this.define(arguments0Value_, arguments1Value_, arguments2Value_);
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
            s[0] = "define";
            s[1] = (byte)1;
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static ObjectPrototypeBuiltins.ObjectPrototypeDefineGetterOrSetterNode create(JSContext context, JSBuiltin builtin, boolean getter, JavaScriptNode[] arguments) {
            return new ObjectPrototypeDefineGetterOrSetterNodeGen(context, builtin, getter, arguments);
        }
    }

    @GeneratedBy(value=ObjectPrototypeBuiltins.ObjectPrototypeIsPrototypeOfNode.class)
    public static final class ObjectPrototypeIsPrototypeOfNodeGen
    extends ObjectPrototypeBuiltins.ObjectPrototypeIsPrototypeOfNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @CompilerDirectives.CompilationFinal
        private int state_0_;

        private ObjectPrototypeIsPrototypeOfNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
                JSDynamicObject arguments1Value__;
                if ((state_0 & 1) != 0 && arguments1Value_ instanceof JSDynamicObject && JSGuards.isJSObject(arguments1Value__ = (JSDynamicObject)arguments1Value_)) {
                    return this.isPrototypeOf(arguments0Value_, arguments1Value__);
                }
                if ((state_0 & 2) != 0 && !JSGuards.isJSObject(arguments1Value_)) {
                    return this.isPrototypeOfNoObject(arguments0Value_, arguments1Value_);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
        }

        @Override
        public boolean executeBoolean(VirtualFrame frameValue) {
            int state_0 = this.state_0_;
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            if (state_0 != 0) {
                JSDynamicObject arguments1Value__;
                if ((state_0 & 1) != 0 && arguments1Value_ instanceof JSDynamicObject && JSGuards.isJSObject(arguments1Value__ = (JSDynamicObject)arguments1Value_)) {
                    return this.isPrototypeOf(arguments0Value_, arguments1Value__);
                }
                if ((state_0 & 2) != 0 && !JSGuards.isJSObject(arguments1Value_)) {
                    return this.isPrototypeOfNoObject(arguments0Value_, arguments1Value_);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.executeBoolean(frameValue);
        }

        private boolean executeAndSpecialize(Object arguments0Value, Object arguments1Value) {
            JSDynamicObject arguments1Value_;
            int state_0 = this.state_0_;
            if (arguments1Value instanceof JSDynamicObject && JSGuards.isJSObject(arguments1Value_ = (JSDynamicObject)arguments1Value)) {
                this.state_0_ = state_0 |= 1;
                return this.isPrototypeOf(arguments0Value, arguments1Value_);
            }
            if (!JSGuards.isJSObject(arguments1Value)) {
                this.state_0_ = state_0 |= 2;
                return this.isPrototypeOfNoObject(arguments0Value, arguments1Value);
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
            s[0] = "isPrototypeOf";
            s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[1] = s;
            s = new Object[3];
            s[0] = "isPrototypeOfNoObject";
            s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[2] = s;
            return Introspection.Provider.create(data);
        }

        public static ObjectPrototypeBuiltins.ObjectPrototypeIsPrototypeOfNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new ObjectPrototypeIsPrototypeOfNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=ObjectPrototypeBuiltins.ObjectPrototypeHasOwnPropertyNode.class)
    public static final class ObjectPrototypeHasOwnPropertyNodeGen
    extends ObjectPrototypeBuiltins.ObjectPrototypeHasOwnPropertyNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @CompilerDirectives.CompilationFinal
        private int state_0_;
        @CompilerDirectives.CompilationFinal
        private int exclude_;

        private ObjectPrototypeHasOwnPropertyNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            if ((state_0 & 0x3FD) == 0 && state_0 != 0) {
                return this.execute_int0(state_0, frameValue);
            }
            return this.execute_generic1(state_0, frameValue);
        }

        private Object execute_int0(int state_0, VirtualFrame frameValue) {
            JSDynamicObject arguments0Value__;
            int arguments1Value_;
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            try {
                arguments1Value_ = this.arguments1_.executeInt(frameValue);
            }
            catch (UnexpectedResultException ex) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.executeAndSpecialize(arguments0Value_, ex.getResult());
            }
            assert ((state_0 & 2) != 0);
            if (arguments0Value_ instanceof JSDynamicObject && JSGuards.isJSObject(arguments0Value__ = (JSDynamicObject)arguments0Value_)) {
                return this.doJSObjectIntKey(arguments0Value__, arguments1Value_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
        }

        private Object execute_generic1(int state_0, VirtualFrame frameValue) {
            Object arguments0Value__;
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            if ((state_0 & 0xF) != 0 && arguments0Value_ instanceof JSDynamicObject) {
                arguments0Value__ = (JSDynamicObject)arguments0Value_;
                if ((state_0 & 1) != 0 && arguments1Value_ instanceof TruffleString) {
                    TruffleString arguments1Value__ = (TruffleString)arguments1Value_;
                    if (JSGuards.isJSObject(arguments0Value__)) {
                        return this.doJSObjectTStringKey((JSDynamicObject)arguments0Value__, arguments1Value__);
                    }
                }
                if ((state_0 & 2) != 0 && arguments1Value_ instanceof Integer) {
                    int arguments1Value__ = (Integer)arguments1Value_;
                    if (JSGuards.isJSObject(arguments0Value__)) {
                        return this.doJSObjectIntKey((JSDynamicObject)arguments0Value__, arguments1Value__);
                    }
                }
                if ((state_0 & 0xC) != 0) {
                    if ((state_0 & 4) != 0 && JSGuards.isJSObject(arguments0Value__)) {
                        return this.doJSObjectAnyKey((JSDynamicObject)arguments0Value__, arguments1Value_);
                    }
                    if ((state_0 & 8) != 0 && JSGuards.isNullOrUndefined(arguments0Value__)) {
                        return this.hasOwnPropertyNullOrUndefined((JSDynamicObject)arguments0Value__, arguments1Value_);
                    }
                }
            }
            if ((state_0 & 0x3F0) != 0) {
                if ((state_0 & 0x10) != 0 && arguments0Value_ instanceof TruffleString) {
                    arguments0Value__ = (TruffleString)arguments0Value_;
                    return this.hasOwnPropertyTString((TruffleString)arguments0Value__, arguments1Value_);
                }
                if ((state_0 & 0x20) != 0 && !JSGuards.isTruffleObject(arguments0Value_)) {
                    return this.hasOwnPropertyPrimitive(arguments0Value_, arguments1Value_);
                }
                if ((state_0 & 0x40) != 0 && arguments0Value_ instanceof Symbol) {
                    arguments0Value__ = (Symbol)arguments0Value_;
                    return this.hasOwnPropertySymbol((Symbol)arguments0Value__, arguments1Value_);
                }
                if ((state_0 & 0x80) != 0 && arguments0Value_ instanceof SafeInteger) {
                    arguments0Value__ = (SafeInteger)arguments0Value_;
                    return this.hasOwnPropertySafeInteger((SafeInteger)arguments0Value__, arguments1Value_);
                }
                if ((state_0 & 0x100) != 0 && arguments0Value_ instanceof BigInt) {
                    arguments0Value__ = (BigInt)arguments0Value_;
                    return this.hasOwnPropertyBigInt((BigInt)arguments0Value__, arguments1Value_);
                }
                if ((state_0 & 0x200) != 0 && JSGuards.isForeignObject(arguments0Value_)) {
                    return this.hasOwnPropertyForeign(arguments0Value_, arguments1Value_);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
        }

        @Override
        public boolean executeBoolean(VirtualFrame frameValue) {
            int state_0 = this.state_0_;
            if ((state_0 & 0x3FD) == 0 && state_0 != 0) {
                return this.executeBoolean_int2(state_0, frameValue);
            }
            return this.executeBoolean_generic3(state_0, frameValue);
        }

        private boolean executeBoolean_int2(int state_0, VirtualFrame frameValue) {
            JSDynamicObject arguments0Value__;
            int arguments1Value_;
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            try {
                arguments1Value_ = this.arguments1_.executeInt(frameValue);
            }
            catch (UnexpectedResultException ex) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.executeAndSpecialize(arguments0Value_, ex.getResult());
            }
            assert ((state_0 & 2) != 0);
            if (arguments0Value_ instanceof JSDynamicObject && JSGuards.isJSObject(arguments0Value__ = (JSDynamicObject)arguments0Value_)) {
                return this.doJSObjectIntKey(arguments0Value__, arguments1Value_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
        }

        private boolean executeBoolean_generic3(int state_0, VirtualFrame frameValue) {
            Object arguments0Value__;
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            if ((state_0 & 0xF) != 0 && arguments0Value_ instanceof JSDynamicObject) {
                arguments0Value__ = (JSDynamicObject)arguments0Value_;
                if ((state_0 & 1) != 0 && arguments1Value_ instanceof TruffleString) {
                    TruffleString arguments1Value__ = (TruffleString)arguments1Value_;
                    if (JSGuards.isJSObject(arguments0Value__)) {
                        return this.doJSObjectTStringKey((JSDynamicObject)arguments0Value__, arguments1Value__);
                    }
                }
                if ((state_0 & 2) != 0 && arguments1Value_ instanceof Integer) {
                    int arguments1Value__ = (Integer)arguments1Value_;
                    if (JSGuards.isJSObject(arguments0Value__)) {
                        return this.doJSObjectIntKey((JSDynamicObject)arguments0Value__, arguments1Value__);
                    }
                }
                if ((state_0 & 0xC) != 0) {
                    if ((state_0 & 4) != 0 && JSGuards.isJSObject(arguments0Value__)) {
                        return this.doJSObjectAnyKey((JSDynamicObject)arguments0Value__, arguments1Value_);
                    }
                    if ((state_0 & 8) != 0 && JSGuards.isNullOrUndefined(arguments0Value__)) {
                        return this.hasOwnPropertyNullOrUndefined((JSDynamicObject)arguments0Value__, arguments1Value_);
                    }
                }
            }
            if ((state_0 & 0x3F0) != 0) {
                if ((state_0 & 0x10) != 0 && arguments0Value_ instanceof TruffleString) {
                    arguments0Value__ = (TruffleString)arguments0Value_;
                    return this.hasOwnPropertyTString((TruffleString)arguments0Value__, arguments1Value_);
                }
                if ((state_0 & 0x20) != 0 && !JSGuards.isTruffleObject(arguments0Value_)) {
                    return this.hasOwnPropertyPrimitive(arguments0Value_, arguments1Value_);
                }
                if ((state_0 & 0x40) != 0 && arguments0Value_ instanceof Symbol) {
                    arguments0Value__ = (Symbol)arguments0Value_;
                    return this.hasOwnPropertySymbol((Symbol)arguments0Value__, arguments1Value_);
                }
                if ((state_0 & 0x80) != 0 && arguments0Value_ instanceof SafeInteger) {
                    arguments0Value__ = (SafeInteger)arguments0Value_;
                    return this.hasOwnPropertySafeInteger((SafeInteger)arguments0Value__, arguments1Value_);
                }
                if ((state_0 & 0x100) != 0 && arguments0Value_ instanceof BigInt) {
                    arguments0Value__ = (BigInt)arguments0Value_;
                    return this.hasOwnPropertyBigInt((BigInt)arguments0Value__, arguments1Value_);
                }
                if ((state_0 & 0x200) != 0 && JSGuards.isForeignObject(arguments0Value_)) {
                    return this.hasOwnPropertyForeign(arguments0Value_, arguments1Value_);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.executeBoolean(frameValue);
        }

        private boolean executeAndSpecialize(Object arguments0Value, Object arguments1Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                Object arguments0Value_;
                int state_0 = this.state_0_;
                int exclude = this.exclude_;
                if (arguments0Value instanceof JSDynamicObject) {
                    arguments0Value_ = (JSDynamicObject)arguments0Value;
                    if ((exclude & 1) == 0 && arguments1Value instanceof TruffleString) {
                        TruffleString arguments1Value_ = (TruffleString)arguments1Value;
                        if (JSGuards.isJSObject(arguments0Value_)) {
                            this.state_0_ = state_0 |= 1;
                            lock.unlock();
                            hasLock = false;
                            boolean bl = this.doJSObjectTStringKey((JSDynamicObject)arguments0Value_, arguments1Value_);
                            return bl;
                        }
                    }
                    if ((exclude & 2) == 0 && arguments1Value instanceof Integer) {
                        int arguments1Value_ = (Integer)arguments1Value;
                        if (JSGuards.isJSObject(arguments0Value_)) {
                            this.state_0_ = state_0 |= 2;
                            lock.unlock();
                            hasLock = false;
                            boolean bl = this.doJSObjectIntKey((JSDynamicObject)arguments0Value_, arguments1Value_);
                            return bl;
                        }
                    }
                    if (JSGuards.isJSObject(arguments0Value_)) {
                        this.exclude_ = exclude |= 3;
                        state_0 &= 0xFFFFFFFC;
                        this.state_0_ = state_0 |= 4;
                        lock.unlock();
                        hasLock = false;
                        boolean bl = this.doJSObjectAnyKey((JSDynamicObject)arguments0Value_, arguments1Value);
                        return bl;
                    }
                    if (JSGuards.isNullOrUndefined(arguments0Value_)) {
                        this.state_0_ = state_0 |= 8;
                        lock.unlock();
                        hasLock = false;
                        boolean bl = this.hasOwnPropertyNullOrUndefined((JSDynamicObject)arguments0Value_, arguments1Value);
                        return bl;
                    }
                }
                if (arguments0Value instanceof TruffleString) {
                    arguments0Value_ = (TruffleString)arguments0Value;
                    this.state_0_ = state_0 |= 0x10;
                    lock.unlock();
                    hasLock = false;
                    boolean bl = this.hasOwnPropertyTString((TruffleString)arguments0Value_, arguments1Value);
                    return bl;
                }
                if (!JSGuards.isTruffleObject(arguments0Value)) {
                    this.state_0_ = state_0 |= 0x20;
                    lock.unlock();
                    hasLock = false;
                    boolean arguments0Value_2 = this.hasOwnPropertyPrimitive(arguments0Value, arguments1Value);
                    return arguments0Value_2;
                }
                if (arguments0Value instanceof Symbol) {
                    arguments0Value_ = (Symbol)arguments0Value;
                    this.state_0_ = state_0 |= 0x40;
                    lock.unlock();
                    hasLock = false;
                    boolean bl = this.hasOwnPropertySymbol((Symbol)arguments0Value_, arguments1Value);
                    return bl;
                }
                if (arguments0Value instanceof SafeInteger) {
                    arguments0Value_ = (SafeInteger)arguments0Value;
                    this.state_0_ = state_0 |= 0x80;
                    lock.unlock();
                    hasLock = false;
                    boolean bl = this.hasOwnPropertySafeInteger((SafeInteger)arguments0Value_, arguments1Value);
                    return bl;
                }
                if (arguments0Value instanceof BigInt) {
                    arguments0Value_ = (BigInt)arguments0Value;
                    this.state_0_ = state_0 |= 0x100;
                    lock.unlock();
                    hasLock = false;
                    boolean bl = this.hasOwnPropertyBigInt((BigInt)arguments0Value_, arguments1Value);
                    return bl;
                }
                if (JSGuards.isForeignObject(arguments0Value)) {
                    this.state_0_ = state_0 |= 0x200;
                    lock.unlock();
                    hasLock = false;
                    boolean bl = this.hasOwnPropertyForeign(arguments0Value, arguments1Value);
                    return bl;
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
            Object[] data = new Object[11];
            data[0] = 0;
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            Object[] s = new Object[3];
            s[0] = "doJSObjectTStringKey";
            s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : ((exclude & 1) != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0));
            data[1] = s;
            s = new Object[3];
            s[0] = "doJSObjectIntKey";
            s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : ((exclude & 2) != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0));
            data[2] = s;
            s = new Object[3];
            s[0] = "doJSObjectAnyKey";
            s[1] = (state_0 & 4) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[3] = s;
            s = new Object[3];
            s[0] = "hasOwnPropertyNullOrUndefined";
            s[1] = (state_0 & 8) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[4] = s;
            s = new Object[3];
            s[0] = "hasOwnPropertyTString";
            s[1] = (state_0 & 0x10) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[5] = s;
            s = new Object[3];
            s[0] = "hasOwnPropertyPrimitive";
            s[1] = (state_0 & 0x20) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[6] = s;
            s = new Object[3];
            s[0] = "hasOwnPropertySymbol";
            s[1] = (state_0 & 0x40) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[7] = s;
            s = new Object[3];
            s[0] = "hasOwnPropertySafeInteger";
            s[1] = (state_0 & 0x80) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[8] = s;
            s = new Object[3];
            s[0] = "hasOwnPropertyBigInt";
            s[1] = (state_0 & 0x100) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[9] = s;
            s = new Object[3];
            s[0] = "hasOwnPropertyForeign";
            s[1] = (state_0 & 0x200) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[10] = s;
            return Introspection.Provider.create(data);
        }

        public static ObjectPrototypeBuiltins.ObjectPrototypeHasOwnPropertyNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new ObjectPrototypeHasOwnPropertyNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=ObjectPrototypeBuiltins.ObjectPrototypePropertyIsEnumerableNode.class)
    public static final class ObjectPrototypePropertyIsEnumerableNodeGen
    extends ObjectPrototypeBuiltins.ObjectPrototypePropertyIsEnumerableNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;

        private ObjectPrototypePropertyIsEnumerableNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            return this.propertyIsEnumerable(arguments0Value_, arguments1Value_);
        }

        @Override
        public boolean executeBoolean(VirtualFrame frameValue) {
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            return this.propertyIsEnumerable(arguments0Value_, arguments1Value_);
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
            s[0] = "propertyIsEnumerable";
            s[1] = (byte)1;
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static ObjectPrototypeBuiltins.ObjectPrototypePropertyIsEnumerableNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new ObjectPrototypePropertyIsEnumerableNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=ObjectPrototypeBuiltins.ObjectPrototypeToLocaleStringNode.class)
    public static final class ObjectPrototypeToLocaleStringNodeGen
    extends ObjectPrototypeBuiltins.ObjectPrototypeToLocaleStringNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;

        private ObjectPrototypeToLocaleStringNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            return this.toLocaleString(arguments0Value_);
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
            s[0] = "toLocaleString";
            s[1] = (byte)1;
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static ObjectPrototypeBuiltins.ObjectPrototypeToLocaleStringNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new ObjectPrototypeToLocaleStringNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=ObjectPrototypeBuiltins.FormatCacheNode.class)
    public static final class FormatCacheNodeGen
    extends ObjectPrototypeBuiltins.FormatCacheNode
    implements Introspection.Provider {
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private ExecuteCachedData executeCached_cache;

        private FormatCacheNodeGen() {
        }

        @Override
        @ExplodeLoop
        public TruffleString execute(TruffleString arg0Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
                if ((state_0 & 1) != 0) {
                    ExecuteCachedData s0_ = this.executeCached_cache;
                    while (s0_ != null) {
                        if (JSGuards.stringEquals(s0_.equalsNode_, s0_.cachedName_, arg0Value)) {
                            return this.executeCached(arg0Value, s0_.cachedName_, s0_.cachedResult_, s0_.equalsNode_);
                        }
                        s0_ = s0_.next_;
                    }
                }
                if ((state_0 & 2) != 0) {
                    return this.executeUncached(arg0Value);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private TruffleString executeAndSpecialize(TruffleString arg0Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                TruffleString truffleString;
                int state_0 = this.state_0_;
                int count0_ = 0;
                ExecuteCachedData s0_ = this.executeCached_cache;
                if ((state_0 & 1) != 0) {
                    while (s0_ != null && !JSGuards.stringEquals(s0_.equalsNode_, s0_.cachedName_, arg0Value)) {
                        s0_ = s0_.next_;
                        ++count0_;
                    }
                }
                if (s0_ == null) {
                    TruffleString cachedName__ = arg0Value;
                    TruffleString.EqualNode equalsNode__ = super.insert(TruffleString.EqualNode.create());
                    if (JSGuards.stringEquals(equalsNode__, cachedName__, arg0Value) && count0_ < 10) {
                        s0_ = super.insert(new ExecuteCachedData(this.executeCached_cache));
                        s0_.cachedName_ = cachedName__;
                        s0_.cachedResult_ = this.executeUncached(arg0Value);
                        s0_.equalsNode_ = s0_.insertAccessor(equalsNode__);
                        VarHandle.storeStoreFence();
                        this.executeCached_cache = s0_;
                        this.state_0_ = state_0 |= 1;
                    }
                }
                if (s0_ != null) {
                    lock.unlock();
                    hasLock = false;
                    truffleString = this.executeCached(arg0Value, s0_.cachedName_, s0_.cachedResult_, s0_.equalsNode_);
                    return truffleString;
                }
                this.state_0_ = state_0 |= 2;
                lock.unlock();
                hasLock = false;
                truffleString = this.executeUncached(arg0Value);
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
            ExecuteCachedData s0_;
            int state_0 = this.state_0_;
            if (state_0 == 0) {
                return NodeCost.UNINITIALIZED;
            }
            if ((state_0 & state_0 - 1) == 0 && ((s0_ = this.executeCached_cache) == null || s0_.next_ == null)) {
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
            s[0] = "executeCached";
            if ((state_0 & 1) != 0) {
                s[1] = (byte)1;
                ArrayList<List<Object>> cached = new ArrayList<List<Object>>();
                ExecuteCachedData s0_ = this.executeCached_cache;
                while (s0_ != null) {
                    cached.add(Arrays.asList(s0_.cachedName_, s0_.cachedResult_, s0_.equalsNode_));
                    s0_ = s0_.next_;
                }
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[1] = s;
            s = new Object[3];
            s[0] = "executeUncached";
            s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[2] = s;
            return Introspection.Provider.create(data);
        }

        public static ObjectPrototypeBuiltins.FormatCacheNode create() {
            return new FormatCacheNodeGen();
        }

        @GeneratedBy(value=ObjectPrototypeBuiltins.FormatCacheNode.class)
        private static final class ExecuteCachedData
        extends Node {
            @Node.Child
            ExecuteCachedData next_;
            @CompilerDirectives.CompilationFinal
            TruffleString cachedName_;
            @CompilerDirectives.CompilationFinal
            TruffleString cachedResult_;
            @Node.Child
            TruffleString.EqualNode equalsNode_;

            ExecuteCachedData(ExecuteCachedData next_) {
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

    @GeneratedBy(value=ObjectPrototypeBuiltins.GetBuiltinToStringTagNode.class)
    public static final class GetBuiltinToStringTagNodeGen
    extends ObjectPrototypeBuiltins.GetBuiltinToStringTagNode
    implements Introspection.Provider {
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @CompilerDirectives.CompilationFinal
        private volatile int exclude_;
        @CompilerDirectives.CompilationFinal
        private CachedData cached_cache;

        private GetBuiltinToStringTagNodeGen() {
        }

        @Override
        @ExplodeLoop
        public TruffleString execute(JSObject arg0Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
                if ((state_0 & 1) != 0) {
                    CachedData s0_ = this.cached_cache;
                    while (s0_ != null) {
                        assert (s0_.cachedClass_ != null);
                        if (s0_.cachedClass_.isInstance(arg0Value)) {
                            return ObjectPrototypeBuiltins.GetBuiltinToStringTagNode.cached(arg0Value, s0_.cachedClass_);
                        }
                        s0_ = s0_.next_;
                    }
                }
                if ((state_0 & 2) != 0) {
                    return ObjectPrototypeBuiltins.GetBuiltinToStringTagNode.uncached(arg0Value);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private TruffleString executeAndSpecialize(JSObject arg0Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                int exclude = this.exclude_;
                if (exclude == 0) {
                    JSClass cachedClass__;
                    int count0_ = 0;
                    CachedData s0_ = this.cached_cache;
                    if ((state_0 & 1) != 0) {
                        while (s0_ != null) {
                            assert (s0_.cachedClass_ != null);
                            if (s0_.cachedClass_.isInstance(arg0Value)) break;
                            s0_ = s0_.next_;
                            ++count0_;
                        }
                    }
                    if (s0_ == null && (cachedClass__ = JSObject.getJSClass(arg0Value)) != null && cachedClass__.isInstance(arg0Value) && count0_ < 5) {
                        s0_ = new CachedData(this.cached_cache);
                        s0_.cachedClass_ = cachedClass__;
                        VarHandle.storeStoreFence();
                        this.cached_cache = s0_;
                        this.state_0_ = state_0 |= 1;
                    }
                    if (s0_ != null) {
                        lock.unlock();
                        hasLock = false;
                        TruffleString truffleString = ObjectPrototypeBuiltins.GetBuiltinToStringTagNode.cached(arg0Value, s0_.cachedClass_);
                        return truffleString;
                    }
                }
                this.exclude_ = exclude |= 1;
                this.cached_cache = null;
                state_0 &= 0xFFFFFFFE;
                this.state_0_ = state_0 |= 2;
                lock.unlock();
                hasLock = false;
                TruffleString truffleString = ObjectPrototypeBuiltins.GetBuiltinToStringTagNode.uncached(arg0Value);
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
            Object[] data = new Object[3];
            data[0] = 0;
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            Object[] s = new Object[3];
            s[0] = "cached";
            if ((state_0 & 1) != 0) {
                s[1] = (byte)1;
                ArrayList<List<JSClass>> cached = new ArrayList<List<JSClass>>();
                CachedData s0_ = this.cached_cache;
                while (s0_ != null) {
                    cached.add(Arrays.asList(s0_.cachedClass_));
                    s0_ = s0_.next_;
                }
                s[2] = cached;
            } else {
                s[1] = exclude != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0);
            }
            data[1] = s;
            s = new Object[3];
            s[0] = "uncached";
            s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[2] = s;
            return Introspection.Provider.create(data);
        }

        public static ObjectPrototypeBuiltins.GetBuiltinToStringTagNode create() {
            return new GetBuiltinToStringTagNodeGen();
        }

        @GeneratedBy(value=ObjectPrototypeBuiltins.GetBuiltinToStringTagNode.class)
        private static final class CachedData {
            @CompilerDirectives.CompilationFinal
            CachedData next_;
            @CompilerDirectives.CompilationFinal
            JSClass cachedClass_;

            CachedData(CachedData next_) {
                this.next_ = next_;
            }
        }
    }

    @GeneratedBy(value=ObjectPrototypeBuiltins.ObjectPrototypeToStringNode.class)
    public static final class ObjectPrototypeToStringNodeGen
    extends ObjectPrototypeBuiltins.ObjectPrototypeToStringNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @CompilerDirectives.CompilationFinal
        private volatile int exclude_;
        @Node.Child
        private ObjectPrototypeBuiltins.GetBuiltinToStringTagNode builtinTag;
        @Node.Child
        private InteropLibrary foreignObject0_interop_;

        private ObjectPrototypeToStringNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            super(context, builtin);
            this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
        }

        @Override
        public JavaScriptNode[] getArguments() {
            return new JavaScriptNode[]{this.arguments0_};
        }

        @Override
        public Object execute(VirtualFrame frameValue) {
            Object arguments0Value__;
            int state_0 = this.state_0_;
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSObject && !JSGuards.isJSProxy(arguments0Value__ = (JSObject)arguments0Value_)) {
                return this.doJSObject((JSObject)arguments0Value__, this.builtinTag);
            }
            if ((state_0 & 2) != 0 && arguments0Value_ instanceof JSProxyObject) {
                arguments0Value__ = (JSProxyObject)arguments0Value_;
                return this.doJSProxy((JSProxyObject)arguments0Value__, this.builtinTag);
            }
            if ((state_0 & 0x3C) != 0) {
                if ((state_0 & 4) != 0 && JSGuards.isJSNull(arguments0Value_)) {
                    return this.doNull(arguments0Value_);
                }
                if ((state_0 & 8) != 0 && JSGuards.isUndefined(arguments0Value_)) {
                    return this.doUndefined(arguments0Value_);
                }
                if ((state_0 & 0x10) != 0 && this.foreignObject0_interop_.accepts(arguments0Value_) && JSGuards.isForeignObject(arguments0Value_)) {
                    return this.doForeignObject(arguments0Value_, this.foreignObject0_interop_);
                }
                if ((state_0 & 0x20) != 0 && JSGuards.isForeignObject(arguments0Value_)) {
                    return this.foreignObject1Boundary(state_0, arguments0Value_);
                }
            }
            if ((state_0 & 0x40) != 0 && arguments0Value_ instanceof Symbol) {
                arguments0Value__ = (Symbol)arguments0Value_;
                return this.doSymbol((Symbol)arguments0Value__);
            }
            if ((state_0 & 0x80) != 0 && arguments0Value_ instanceof TruffleString) {
                arguments0Value__ = (TruffleString)arguments0Value_;
                return this.doString((TruffleString)arguments0Value__);
            }
            if ((state_0 & 0x100) != 0 && arguments0Value_ instanceof SafeInteger) {
                arguments0Value__ = (SafeInteger)arguments0Value_;
                return this.doSafeInteger((SafeInteger)arguments0Value__);
            }
            if ((state_0 & 0x200) != 0 && arguments0Value_ instanceof BigInt) {
                arguments0Value__ = (BigInt)arguments0Value_;
                return this.doBigInt((BigInt)arguments0Value__);
            }
            if ((state_0 & 0x400) != 0 && !JSGuards.isTruffleObject(arguments0Value_)) {
                return this.doObject(arguments0Value_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @CompilerDirectives.TruffleBoundary
        private Object foreignObject1Boundary(int state_0, Object arguments0Value_) {
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this);
            try {
                InteropLibrary foreignObject1_interop__ = INTEROP_LIBRARY_.getUncached(arguments0Value_);
                TruffleString truffleString = this.doForeignObject(arguments0Value_, foreignObject1_interop__);
                return truffleString;
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
        private TruffleString executeAndSpecialize(Object arguments0Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                TruffleString truffleString;
                Object arguments0Value_;
                int state_0 = this.state_0_;
                int exclude = this.exclude_;
                if (arguments0Value instanceof JSObject && !JSGuards.isJSProxy(arguments0Value_ = (JSObject)arguments0Value)) {
                    this.builtinTag = super.insert(this.builtinTag == null ? ObjectPrototypeBuiltins.GetBuiltinToStringTagNode.create() : this.builtinTag);
                    this.state_0_ = state_0 |= 1;
                    lock.unlock();
                    hasLock = false;
                    TruffleString truffleString2 = this.doJSObject((JSObject)arguments0Value_, this.builtinTag);
                    return truffleString2;
                }
                if (arguments0Value instanceof JSProxyObject) {
                    arguments0Value_ = (JSProxyObject)arguments0Value;
                    this.builtinTag = super.insert(this.builtinTag == null ? ObjectPrototypeBuiltins.GetBuiltinToStringTagNode.create() : this.builtinTag);
                    this.state_0_ = state_0 |= 2;
                    lock.unlock();
                    hasLock = false;
                    TruffleString truffleString3 = this.doJSProxy((JSProxyObject)arguments0Value_, this.builtinTag);
                    return truffleString3;
                }
                if (JSGuards.isJSNull(arguments0Value)) {
                    this.state_0_ = state_0 |= 4;
                    lock.unlock();
                    hasLock = false;
                    arguments0Value_ = this.doNull(arguments0Value);
                    return arguments0Value_;
                }
                if (JSGuards.isUndefined(arguments0Value)) {
                    this.state_0_ = state_0 |= 8;
                    lock.unlock();
                    hasLock = false;
                    arguments0Value_ = this.doUndefined(arguments0Value);
                    return arguments0Value_;
                }
                if (exclude == 0) {
                    boolean ForeignObject0_duplicateFound_ = false;
                    if ((state_0 & 0x10) != 0 && this.foreignObject0_interop_.accepts(arguments0Value) && JSGuards.isForeignObject(arguments0Value)) {
                        ForeignObject0_duplicateFound_ = true;
                    }
                    if (!ForeignObject0_duplicateFound_ && JSGuards.isForeignObject(arguments0Value) && (state_0 & 0x10) == 0) {
                        this.foreignObject0_interop_ = super.insert(INTEROP_LIBRARY_.create(arguments0Value));
                        this.state_0_ = state_0 |= 0x10;
                        ForeignObject0_duplicateFound_ = true;
                    }
                    if (ForeignObject0_duplicateFound_) {
                        lock.unlock();
                        hasLock = false;
                        TruffleString truffleString4 = this.doForeignObject(arguments0Value, this.foreignObject0_interop_);
                        return truffleString4;
                    }
                }
                InteropLibrary foreignObject1_interop__ = null;
                EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                Node prev_ = encapsulating_.set(this);
                try {
                    if (JSGuards.isForeignObject(arguments0Value)) {
                        foreignObject1_interop__ = INTEROP_LIBRARY_.getUncached(arguments0Value);
                        this.exclude_ = exclude |= 1;
                        state_0 &= 0xFFFFFFEF;
                        this.state_0_ = state_0 |= 0x20;
                        lock.unlock();
                        hasLock = false;
                        TruffleString truffleString5 = this.doForeignObject(arguments0Value, foreignObject1_interop__);
                        return truffleString5;
                    }
                }
                finally {
                    encapsulating_.set(prev_);
                }
                if (arguments0Value instanceof Symbol) {
                    arguments0Value_ = (Symbol)arguments0Value;
                    this.state_0_ = state_0 |= 0x40;
                    lock.unlock();
                    hasLock = false;
                    truffleString = this.doSymbol((Symbol)arguments0Value_);
                    return truffleString;
                }
                if (arguments0Value instanceof TruffleString) {
                    arguments0Value_ = (TruffleString)arguments0Value;
                    this.state_0_ = state_0 |= 0x80;
                    lock.unlock();
                    hasLock = false;
                    truffleString = this.doString((TruffleString)arguments0Value_);
                    return truffleString;
                }
                if (arguments0Value instanceof SafeInteger) {
                    arguments0Value_ = (SafeInteger)arguments0Value;
                    this.state_0_ = state_0 |= 0x100;
                    lock.unlock();
                    hasLock = false;
                    truffleString = this.doSafeInteger((SafeInteger)arguments0Value_);
                    return truffleString;
                }
                if (arguments0Value instanceof BigInt) {
                    arguments0Value_ = (BigInt)arguments0Value;
                    this.state_0_ = state_0 |= 0x200;
                    lock.unlock();
                    hasLock = false;
                    truffleString = this.doBigInt((BigInt)arguments0Value_);
                    return truffleString;
                }
                if (!JSGuards.isTruffleObject(arguments0Value)) {
                    this.state_0_ = state_0 |= 0x400;
                    lock.unlock();
                    hasLock = false;
                    TruffleString truffleString6 = this.doObject(arguments0Value);
                    return truffleString6;
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
            ArrayList<List<Object>> cached;
            Object[] data = new Object[12];
            data[0] = 0;
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            Object[] s = new Object[3];
            s[0] = "doJSObject";
            if ((state_0 & 1) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList<List<Object>>();
                cached.add(Arrays.asList(this.builtinTag));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[1] = s;
            s = new Object[3];
            s[0] = "doJSProxy";
            if ((state_0 & 2) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList();
                cached.add(Arrays.asList(this.builtinTag));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[2] = s;
            s = new Object[3];
            s[0] = "doNull";
            s[1] = (state_0 & 4) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[3] = s;
            s = new Object[3];
            s[0] = "doUndefined";
            s[1] = (state_0 & 8) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[4] = s;
            s = new Object[3];
            s[0] = "doForeignObject";
            if ((state_0 & 0x10) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList();
                cached.add(Arrays.asList(this.foreignObject0_interop_));
                s[2] = cached;
            } else {
                s[1] = exclude != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0);
            }
            data[5] = s;
            s = new Object[3];
            s[0] = "doForeignObject";
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
            s[0] = "doSymbol";
            s[1] = (state_0 & 0x40) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[7] = s;
            s = new Object[3];
            s[0] = "doString";
            s[1] = (state_0 & 0x80) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[8] = s;
            s = new Object[3];
            s[0] = "doSafeInteger";
            s[1] = (state_0 & 0x100) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[9] = s;
            s = new Object[3];
            s[0] = "doBigInt";
            s[1] = (state_0 & 0x200) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[10] = s;
            s = new Object[3];
            s[0] = "doObject";
            s[1] = (state_0 & 0x400) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[11] = s;
            return Introspection.Provider.create(data);
        }

        public static ObjectPrototypeBuiltins.ObjectPrototypeToStringNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new ObjectPrototypeToStringNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=ObjectPrototypeBuiltins.ObjectPrototypeValueOfNode.class)
    public static final class ObjectPrototypeValueOfNodeGen
    extends ObjectPrototypeBuiltins.ObjectPrototypeValueOfNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private InteropLibrary valueOfForeign_interop_;

        private ObjectPrototypeValueOfNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            super(context, builtin);
            this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
        }

        @Override
        public JavaScriptNode[] getArguments() {
            return new JavaScriptNode[]{this.arguments0_};
        }

        @Override
        public Object execute(VirtualFrame frameValue) {
            Object arguments0Value__;
            int state_0 = this.state_0_;
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSDynamicObject && JSGuards.isJSDynamicObject(arguments0Value__ = (JSDynamicObject)arguments0Value_)) {
                return this.valueOfJSObject((JSDynamicObject)arguments0Value__);
            }
            if ((state_0 & 2) != 0 && arguments0Value_ instanceof Symbol) {
                arguments0Value__ = (Symbol)arguments0Value_;
                return this.valueOfSymbol((Symbol)arguments0Value__);
            }
            if ((state_0 & 4) != 0 && arguments0Value_ instanceof TruffleString) {
                arguments0Value__ = (TruffleString)arguments0Value_;
                return this.valueOfLazyString((TruffleString)arguments0Value__);
            }
            if ((state_0 & 8) != 0 && arguments0Value_ instanceof SafeInteger) {
                arguments0Value__ = (SafeInteger)arguments0Value_;
                return this.valueOfSafeInteger((SafeInteger)arguments0Value__);
            }
            if ((state_0 & 0x10) != 0 && arguments0Value_ instanceof BigInt) {
                arguments0Value__ = (BigInt)arguments0Value_;
                return this.valueOfBigInt((BigInt)arguments0Value__);
            }
            if ((state_0 & 0x60) != 0) {
                if ((state_0 & 0x20) != 0 && !JSGuards.isTruffleObject(arguments0Value_)) {
                    return this.valueOfOther(arguments0Value_);
                }
                if ((state_0 & 0x40) != 0 && JSGuards.isForeignObject(arguments0Value_)) {
                    return this.valueOfForeign(arguments0Value_, this.valueOfForeign_interop_);
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
                Object arguments0Value_;
                int state_0 = this.state_0_;
                if (arguments0Value instanceof JSDynamicObject && JSGuards.isJSDynamicObject(arguments0Value_ = (JSDynamicObject)arguments0Value)) {
                    this.state_0_ = state_0 |= 1;
                    lock.unlock();
                    hasLock = false;
                    JSDynamicObject jSDynamicObject = this.valueOfJSObject((JSDynamicObject)arguments0Value_);
                    return jSDynamicObject;
                }
                if (arguments0Value instanceof Symbol) {
                    arguments0Value_ = (Symbol)arguments0Value;
                    this.state_0_ = state_0 |= 2;
                    lock.unlock();
                    hasLock = false;
                    JSDynamicObject jSDynamicObject = this.valueOfSymbol((Symbol)arguments0Value_);
                    return jSDynamicObject;
                }
                if (arguments0Value instanceof TruffleString) {
                    arguments0Value_ = (TruffleString)arguments0Value;
                    this.state_0_ = state_0 |= 4;
                    lock.unlock();
                    hasLock = false;
                    JSDynamicObject jSDynamicObject = this.valueOfLazyString((TruffleString)arguments0Value_);
                    return jSDynamicObject;
                }
                if (arguments0Value instanceof SafeInteger) {
                    arguments0Value_ = (SafeInteger)arguments0Value;
                    this.state_0_ = state_0 |= 8;
                    lock.unlock();
                    hasLock = false;
                    JSDynamicObject jSDynamicObject = this.valueOfSafeInteger((SafeInteger)arguments0Value_);
                    return jSDynamicObject;
                }
                if (arguments0Value instanceof BigInt) {
                    arguments0Value_ = (BigInt)arguments0Value;
                    this.state_0_ = state_0 |= 0x10;
                    lock.unlock();
                    hasLock = false;
                    JSDynamicObject jSDynamicObject = this.valueOfBigInt((BigInt)arguments0Value_);
                    return jSDynamicObject;
                }
                if (!JSGuards.isTruffleObject(arguments0Value)) {
                    this.state_0_ = state_0 |= 0x20;
                    lock.unlock();
                    hasLock = false;
                    object = this.valueOfOther(arguments0Value);
                    return object;
                }
                if (JSGuards.isForeignObject(arguments0Value)) {
                    this.valueOfForeign_interop_ = super.insert(INTEROP_LIBRARY_.createDispatched(5));
                    this.state_0_ = state_0 |= 0x40;
                    lock.unlock();
                    hasLock = false;
                    object = this.valueOfForeign(arguments0Value, this.valueOfForeign_interop_);
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
            Object[] data = new Object[8];
            data[0] = 0;
            int state_0 = this.state_0_;
            Object[] s = new Object[3];
            s[0] = "valueOfJSObject";
            s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[1] = s;
            s = new Object[3];
            s[0] = "valueOfSymbol";
            s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[2] = s;
            s = new Object[3];
            s[0] = "valueOfLazyString";
            s[1] = (state_0 & 4) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[3] = s;
            s = new Object[3];
            s[0] = "valueOfSafeInteger";
            s[1] = (state_0 & 8) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[4] = s;
            s = new Object[3];
            s[0] = "valueOfBigInt";
            s[1] = (state_0 & 0x10) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[5] = s;
            s = new Object[3];
            s[0] = "valueOfOther";
            s[1] = (state_0 & 0x20) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[6] = s;
            s = new Object[3];
            s[0] = "valueOfForeign";
            if ((state_0 & 0x40) != 0) {
                s[1] = (byte)1;
                ArrayList<List<InteropLibrary>> cached = new ArrayList<List<InteropLibrary>>();
                cached.add(Arrays.asList(this.valueOfForeign_interop_));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[7] = s;
            return Introspection.Provider.create(data);
        }

        public static ObjectPrototypeBuiltins.ObjectPrototypeValueOfNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new ObjectPrototypeValueOfNodeGen(context, builtin, arguments);
        }
    }
}

