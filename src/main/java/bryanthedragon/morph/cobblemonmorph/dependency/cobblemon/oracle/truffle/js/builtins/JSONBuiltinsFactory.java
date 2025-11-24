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
import com.oracle.truffle.api.strings.TruffleStringBuilder;
import com.oracle.truffle.js.builtins.JSONBuiltins;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.nodes.unary.IsCallableNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.util.StringBuilderProfile;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=JSONBuiltins.class)
public final class JSONBuiltinsFactory {

    @GeneratedBy(value=JSONBuiltins.JSONStringifyNode.class)
    public static final class JSONStringifyNodeGen
    extends JSONBuiltins.JSONStringifyNode
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
        private StringifyAStringNoReplacerData stringifyAStringNoReplacer_cache;

        private JSONStringifyNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
                if ((state_0 & 3) != 0) {
                    JSDynamicObject arguments1Value__;
                    if ((state_0 & 1) != 0 && this.isCallable(arguments1Value_)) {
                        return this.stringify(arguments0Value_, arguments1Value_, arguments2Value_);
                    }
                    if ((state_0 & 2) != 0 && arguments1Value_ instanceof JSDynamicObject && this.isArray(arguments1Value__ = (JSDynamicObject)arguments1Value_)) {
                        return this.stringifyReplacerArray(arguments0Value_, arguments1Value__, arguments2Value_);
                    }
                }
                if ((state_0 & 0xC) != 0) {
                    if ((state_0 & 4) != 0 && arguments0Value_ instanceof TruffleString) {
                        TruffleString arguments0Value__ = (TruffleString)arguments0Value_;
                        StringifyAStringNoReplacerData s2_ = this.stringifyAStringNoReplacer_cache;
                        if (s2_ != null && !this.isCallable(arguments1Value_) && !this.isArray(arguments1Value_)) {
                            return this.stringifyAStringNoReplacer(arguments0Value__, arguments1Value_, arguments2Value_, s2_.stringBuilderProfile_, s2_.appendRawValueNode_, s2_.appendStringNode_, s2_.builderToStringNode_);
                        }
                    }
                    if (!((state_0 & 8) == 0 || JSGuards.isString(arguments0Value_) || this.isCallable(arguments1Value_) || this.isArray(arguments1Value_))) {
                        return this.stringifyNoReplacer(arguments0Value_, arguments1Value_, arguments2Value_);
                    }
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_, arguments2Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.execute(frameValue);
        }

        private Object executeAndSpecialize(Object arguments0Value, Object arguments1Value, Object arguments2Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                JSDynamicObject arguments1Value_;
                int state_0 = this.state_0_;
                if (this.isCallable(arguments1Value)) {
                    this.state_0_ = state_0 |= 1;
                    lock.unlock();
                    hasLock = false;
                    Object object = this.stringify(arguments0Value, arguments1Value, arguments2Value);
                    return object;
                }
                if (arguments1Value instanceof JSDynamicObject && this.isArray(arguments1Value_ = (JSDynamicObject)arguments1Value)) {
                    this.state_0_ = state_0 |= 2;
                    lock.unlock();
                    hasLock = false;
                    Object object = this.stringifyReplacerArray(arguments0Value, arguments1Value_, arguments2Value);
                    return object;
                }
                if (arguments0Value instanceof TruffleString) {
                    TruffleString arguments0Value_ = (TruffleString)arguments0Value;
                    if (!this.isCallable(arguments1Value) && !this.isArray(arguments1Value)) {
                        StringifyAStringNoReplacerData s2_ = super.insert(new StringifyAStringNoReplacerData());
                        s2_.stringBuilderProfile_ = this.createStringBuilderProfile();
                        s2_.appendRawValueNode_ = s2_.insertAccessor(TruffleStringBuilder.AppendCharUTF16Node.create());
                        s2_.appendStringNode_ = s2_.insertAccessor(TruffleStringBuilder.AppendStringNode.create());
                        s2_.builderToStringNode_ = s2_.insertAccessor(TruffleStringBuilder.ToStringNode.create());
                        VarHandle.storeStoreFence();
                        this.stringifyAStringNoReplacer_cache = s2_;
                        this.state_0_ = state_0 |= 4;
                        lock.unlock();
                        hasLock = false;
                        Object object = this.stringifyAStringNoReplacer(arguments0Value_, arguments1Value, arguments2Value, s2_.stringBuilderProfile_, s2_.appendRawValueNode_, s2_.appendStringNode_, s2_.builderToStringNode_);
                        return object;
                    }
                }
                if (!(JSGuards.isString(arguments0Value) || this.isCallable(arguments1Value) || this.isArray(arguments1Value))) {
                    this.state_0_ = state_0 |= 8;
                    lock.unlock();
                    hasLock = false;
                    Object object = this.stringifyNoReplacer(arguments0Value, arguments1Value, arguments2Value);
                    return object;
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
            Object[] data = new Object[5];
            data[0] = 0;
            int state_0 = this.state_0_;
            Object[] s = new Object[3];
            s[0] = "stringify";
            s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[1] = s;
            s = new Object[3];
            s[0] = "stringifyReplacerArray";
            s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[2] = s;
            s = new Object[3];
            s[0] = "stringifyAStringNoReplacer";
            if ((state_0 & 4) != 0) {
                s[1] = (byte)1;
                ArrayList<List<Cloneable>> cached = new ArrayList<List<Cloneable>>();
                StringifyAStringNoReplacerData s2_ = this.stringifyAStringNoReplacer_cache;
                if (s2_ != null) {
                    cached.add(Arrays.asList(s2_.stringBuilderProfile_, s2_.appendRawValueNode_, s2_.appendStringNode_, s2_.builderToStringNode_));
                }
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[3] = s;
            s = new Object[3];
            s[0] = "stringifyNoReplacer";
            s[1] = (state_0 & 8) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[4] = s;
            return Introspection.Provider.create(data);
        }

        public static JSONBuiltins.JSONStringifyNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSONStringifyNodeGen(context, builtin, arguments);
        }

        @GeneratedBy(value=JSONBuiltins.JSONStringifyNode.class)
        private static final class StringifyAStringNoReplacerData
        extends Node {
            @CompilerDirectives.CompilationFinal
            StringBuilderProfile stringBuilderProfile_;
            @Node.Child
            TruffleStringBuilder.AppendCharUTF16Node appendRawValueNode_;
            @Node.Child
            TruffleStringBuilder.AppendStringNode appendStringNode_;
            @Node.Child
            TruffleStringBuilder.ToStringNode builderToStringNode_;

            StringifyAStringNoReplacerData() {
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

    @GeneratedBy(value=JSONBuiltins.JSONParseNode.class)
    public static final class JSONParseNodeGen
    extends JSONBuiltins.JSONParseNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private IsCallableNode isCallable;

        private JSONParseNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
                if ((state_0 & 1) != 0 && this.isCallable.executeBoolean(arguments1Value_)) {
                    return this.parse(arguments0Value_, arguments1Value_, this.isCallable);
                }
                if ((state_0 & 2) != 0 && !this.isCallable.executeBoolean(arguments1Value_)) {
                    return this.parseUnfiltered(arguments0Value_, arguments1Value_, this.isCallable);
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
                IsCallableNode parseUnfiltered_isCallable__;
                Object parse_isCallable__;
                int state_0 = this.state_0_;
                boolean Parse_duplicateFound_ = false;
                if ((state_0 & 1) != 0 && this.isCallable.executeBoolean(arguments1Value)) {
                    Parse_duplicateFound_ = true;
                }
                if (!Parse_duplicateFound_ && ((IsCallableNode)(parse_isCallable__ = super.insert(this.isCallable == null ? IsCallableNode.create() : this.isCallable))).executeBoolean(arguments1Value) && (state_0 & 1) == 0) {
                    if (this.isCallable == null) {
                        IsCallableNode parse_isCallable___check = (IsCallableNode)super.insert(parse_isCallable__);
                        if (parse_isCallable___check == null) {
                            throw new AssertionError((Object)"Specialization 'parse(Object, Object, IsCallableNode)' contains a shared cache with name 'isCallable' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state.");
                        }
                        this.isCallable = parse_isCallable___check;
                    }
                    this.state_0_ = state_0 |= 1;
                    Parse_duplicateFound_ = true;
                }
                if (Parse_duplicateFound_) {
                    lock.unlock();
                    hasLock = false;
                    parse_isCallable__ = this.parse(arguments0Value, arguments1Value, this.isCallable);
                    return parse_isCallable__;
                }
                boolean ParseUnfiltered_duplicateFound_ = false;
                if ((state_0 & 2) != 0 && !this.isCallable.executeBoolean(arguments1Value)) {
                    ParseUnfiltered_duplicateFound_ = true;
                }
                if (!ParseUnfiltered_duplicateFound_ && !(parseUnfiltered_isCallable__ = super.insert(this.isCallable == null ? IsCallableNode.create() : this.isCallable)).executeBoolean(arguments1Value) && (state_0 & 2) == 0) {
                    if (this.isCallable == null) {
                        IsCallableNode parseUnfiltered_isCallable___check = super.insert(parseUnfiltered_isCallable__);
                        if (parseUnfiltered_isCallable___check == null) {
                            throw new AssertionError((Object)"Specialization 'parseUnfiltered(Object, Object, IsCallableNode)' contains a shared cache with name 'isCallable' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state.");
                        }
                        this.isCallable = parseUnfiltered_isCallable___check;
                    }
                    this.state_0_ = state_0 |= 2;
                    ParseUnfiltered_duplicateFound_ = true;
                }
                if (ParseUnfiltered_duplicateFound_) {
                    lock.unlock();
                    hasLock = false;
                    Object object = this.parseUnfiltered(arguments0Value, arguments1Value, this.isCallable);
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
            ArrayList<List<IsCallableNode>> cached;
            Object[] data = new Object[3];
            data[0] = 0;
            int state_0 = this.state_0_;
            Object[] s = new Object[3];
            s[0] = "parse";
            if ((state_0 & 1) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList<List<IsCallableNode>>();
                cached.add(Arrays.asList(this.isCallable));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[1] = s;
            s = new Object[3];
            s[0] = "parseUnfiltered";
            if ((state_0 & 2) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList();
                cached.add(Arrays.asList(this.isCallable));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[2] = s;
            return Introspection.Provider.create(data);
        }

        public static JSONBuiltins.JSONParseNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSONParseNodeGen(context, builtin, arguments);
        }
    }
}

