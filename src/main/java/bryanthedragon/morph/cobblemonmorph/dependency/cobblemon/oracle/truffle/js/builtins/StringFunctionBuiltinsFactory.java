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
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.builtins.StringFunctionBuiltins;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.cast.JSToNumberNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.runtime.JSContext;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=StringFunctionBuiltins.class)
public final class StringFunctionBuiltinsFactory {

    @GeneratedBy(value=StringFunctionBuiltins.StringRawNode.class)
    public static final class StringRawNodeGen
    extends StringFunctionBuiltins.StringRawNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @CompilerDirectives.CompilationFinal
        private int state_0_;

        private StringRawNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
                return this.raw(arguments0Value_, arguments1Value__);
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
                return this.raw(arguments0Value, arguments1Value_);
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
            s[0] = "raw";
            s[1] = state_0 != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static StringFunctionBuiltins.StringRawNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new StringRawNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=StringFunctionBuiltins.JSFromCodePointNode.class)
    public static final class JSFromCodePointNodeGen
    extends StringFunctionBuiltins.JSFromCodePointNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private FromCodePointData fromCodePoint_cache;

        private JSFromCodePointNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
                FromCodePointData s0_ = this.fromCodePoint_cache;
                if (s0_ != null) {
                    return this.fromCodePoint(arguments0Value__, s0_.toNumberNode_, s0_.fromCodePointNode_, s0_.concatNode_);
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
                    FromCodePointData s0_ = super.insert(new FromCodePointData());
                    s0_.toNumberNode_ = s0_.insertAccessor(JSToNumberNode.create());
                    s0_.fromCodePointNode_ = s0_.insertAccessor(TruffleString.FromCodePointNode.create());
                    s0_.concatNode_ = s0_.insertAccessor(TruffleString.ConcatNode.create());
                    VarHandle.storeStoreFence();
                    this.fromCodePoint_cache = s0_;
                    this.state_0_ = state_0 |= 1;
                    lock.unlock();
                    hasLock = false;
                    Object object = this.fromCodePoint(arguments0Value_, s0_.toNumberNode_, s0_.fromCodePointNode_, s0_.concatNode_);
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
            return NodeCost.MONOMORPHIC;
        }

        @Override
        public Introspection getIntrospectionData() {
            Object[] data = new Object[2];
            data[0] = 0;
            int state_0 = this.state_0_;
            Object[] s = new Object[3];
            s[0] = "fromCodePoint";
            if (state_0 != 0) {
                s[1] = (byte)1;
                ArrayList<List<Node>> cached = new ArrayList<List<Node>>();
                FromCodePointData s0_ = this.fromCodePoint_cache;
                if (s0_ != null) {
                    cached.add(Arrays.asList(s0_.toNumberNode_, s0_.fromCodePointNode_, s0_.concatNode_));
                }
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static StringFunctionBuiltins.JSFromCodePointNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSFromCodePointNodeGen(context, builtin, arguments);
        }

        @GeneratedBy(value=StringFunctionBuiltins.JSFromCodePointNode.class)
        private static final class FromCodePointData
        extends Node {
            @Node.Child
            JSToNumberNode toNumberNode_;
            @Node.Child
            TruffleString.FromCodePointNode fromCodePointNode_;
            @Node.Child
            TruffleString.ConcatNode concatNode_;

            FromCodePointData() {
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

    @GeneratedBy(value=StringFunctionBuiltins.JSFromCharCodeNode.class)
    public static final class JSFromCharCodeNodeGen
    extends StringFunctionBuiltins.JSFromCharCodeNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private TruffleString.FromCodePointNode fromCharCodeOneArg_fromCodePointNode_;
        @Node.Child
        private TruffleString.FromCharArrayUTF16Node fromCharCodeTwoOrMore_fromCharArrayNode_;

        private JSFromCharCodeNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
                    return this.fromCharCode(arguments0Value__);
                }
                if ((state_0 & 2) != 0 && arguments0Value__.length == 1) {
                    return this.fromCharCodeOneArg(arguments0Value__, this.fromCharCodeOneArg_fromCodePointNode_);
                }
                if ((state_0 & 4) != 0 && arguments0Value__.length >= 2) {
                    return this.fromCharCodeTwoOrMore(arguments0Value__, this.fromCharCodeTwoOrMore_fromCharArrayNode_);
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
                        Object object = this.fromCharCode(arguments0Value_);
                        return object;
                    }
                    if (arguments0Value_.length == 1) {
                        this.fromCharCodeOneArg_fromCodePointNode_ = super.insert(TruffleString.FromCodePointNode.create());
                        this.state_0_ = state_0 |= 2;
                        lock.unlock();
                        hasLock = false;
                        Object object = this.fromCharCodeOneArg(arguments0Value_, this.fromCharCodeOneArg_fromCodePointNode_);
                        return object;
                    }
                    if (arguments0Value_.length >= 2) {
                        this.fromCharCodeTwoOrMore_fromCharArrayNode_ = super.insert(TruffleString.FromCharArrayUTF16Node.create());
                        this.state_0_ = state_0 |= 4;
                        lock.unlock();
                        hasLock = false;
                        Object object = this.fromCharCodeTwoOrMore(arguments0Value_, this.fromCharCodeTwoOrMore_fromCharArrayNode_);
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
            ArrayList<List<Node>> cached;
            Object[] data = new Object[4];
            data[0] = 0;
            int state_0 = this.state_0_;
            Object[] s = new Object[3];
            s[0] = "fromCharCode";
            s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[1] = s;
            s = new Object[3];
            s[0] = "fromCharCodeOneArg";
            if ((state_0 & 2) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList<List<Node>>();
                cached.add(Arrays.asList(this.fromCharCodeOneArg_fromCodePointNode_));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[2] = s;
            s = new Object[3];
            s[0] = "fromCharCodeTwoOrMore";
            if ((state_0 & 4) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList();
                cached.add(Arrays.asList(this.fromCharCodeTwoOrMore_fromCharArrayNode_));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[3] = s;
            return Introspection.Provider.create(data);
        }

        public static StringFunctionBuiltins.JSFromCharCodeNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSFromCharCodeNodeGen(context, builtin, arguments);
        }
    }
}

