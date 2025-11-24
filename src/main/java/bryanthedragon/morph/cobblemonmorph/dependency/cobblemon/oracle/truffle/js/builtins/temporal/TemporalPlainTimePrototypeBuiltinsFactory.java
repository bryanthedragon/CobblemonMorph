/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.invoke.VarHandle
 */
package com.oracle.truffle.js.builtins.temporal;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.builtins.temporal.TemporalPlainTimePrototypeBuiltins;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.JSTypesGen;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.access.EnumerableOwnPropertyNamesNode;
import com.oracle.truffle.js.nodes.cast.JSToIntegerAsIntNode;
import com.oracle.truffle.js.nodes.cast.JSToIntegerThrowOnInfinityNode;
import com.oracle.truffle.js.nodes.cast.JSToNumberNode;
import com.oracle.truffle.js.nodes.cast.JSToStringNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.nodes.temporal.TemporalRoundDurationNode;
import com.oracle.truffle.js.nodes.temporal.ToLimitedTemporalDurationNode;
import com.oracle.truffle.js.nodes.temporal.ToTemporalDateNode;
import com.oracle.truffle.js.nodes.temporal.ToTemporalTimeNode;
import com.oracle.truffle.js.nodes.temporal.ToTemporalTimeZoneNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=TemporalPlainTimePrototypeBuiltins.class)
public final class TemporalPlainTimePrototypeBuiltinsFactory {

    @GeneratedBy(value=TemporalPlainTimePrototypeBuiltins.JSTemporalPlainTimeValueOf.class)
    public static final class JSTemporalPlainTimeValueOfNodeGen
    extends TemporalPlainTimePrototypeBuiltins.JSTemporalPlainTimeValueOf
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;

        private JSTemporalPlainTimeValueOfNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            return this.valueOf(arguments0Value_);
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
            s[0] = "valueOf";
            s[1] = (byte)1;
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static TemporalPlainTimePrototypeBuiltins.JSTemporalPlainTimeValueOf create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSTemporalPlainTimeValueOfNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=TemporalPlainTimePrototypeBuiltins.JSTemporalPlainTimeToLocaleString.class)
    public static final class JSTemporalPlainTimeToLocaleStringNodeGen
    extends TemporalPlainTimePrototypeBuiltins.JSTemporalPlainTimeToLocaleString
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;

        private JSTemporalPlainTimeToLocaleStringNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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

        public static TemporalPlainTimePrototypeBuiltins.JSTemporalPlainTimeToLocaleString create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSTemporalPlainTimeToLocaleStringNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=TemporalPlainTimePrototypeBuiltins.JSTemporalPlainTimeToString.class)
    public static final class JSTemporalPlainTimeToStringNodeGen
    extends TemporalPlainTimePrototypeBuiltins.JSTemporalPlainTimeToString
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private JSToStringNode toStringNode_;
        @Node.Child
        private TruffleString.EqualNode equalNode_;

        private JSTemporalPlainTimeToStringNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
                return this.toString(arguments0Value_, arguments1Value_, this.toStringNode_, this.equalNode_);
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
        private TruffleString executeAndSpecialize(Object arguments0Value, Object arguments1Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                this.toStringNode_ = super.insert(JSToStringNode.create());
                this.equalNode_ = super.insert(TruffleString.EqualNode.create());
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                TruffleString truffleString = this.toString(arguments0Value, arguments1Value, this.toStringNode_, this.equalNode_);
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
            s[0] = "toString";
            if (state_0 != 0) {
                s[1] = (byte)1;
                ArrayList<List<Node>> cached = new ArrayList<List<Node>>();
                cached.add(Arrays.asList(this.toStringNode_, this.equalNode_));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static TemporalPlainTimePrototypeBuiltins.JSTemporalPlainTimeToString create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSTemporalPlainTimeToStringNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=TemporalPlainTimePrototypeBuiltins.JSTemporalPlainTimeGetISOFields.class)
    public static final class JSTemporalPlainTimeGetISOFieldsNodeGen
    extends TemporalPlainTimePrototypeBuiltins.JSTemporalPlainTimeGetISOFields
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;

        private JSTemporalPlainTimeGetISOFieldsNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            return this.getISOFields(arguments0Value_);
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
            s[0] = "getISOFields";
            s[1] = (byte)1;
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static TemporalPlainTimePrototypeBuiltins.JSTemporalPlainTimeGetISOFields create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSTemporalPlainTimeGetISOFieldsNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=TemporalPlainTimePrototypeBuiltins.JSTemporalPlainTimeToZonedDateTime.class)
    public static final class JSTemporalPlainTimeToZonedDateTimeNodeGen
    extends TemporalPlainTimePrototypeBuiltins.JSTemporalPlainTimeToZonedDateTime
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private ToTemporalDateNode toTemporalDate_;
        @Node.Child
        private ToTemporalTimeZoneNode toTemporalTimeZone_;

        private JSTemporalPlainTimeToZonedDateTimeNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
                return this.toZonedDateTime(arguments0Value_, arguments1Value_, this.toTemporalDate_, this.toTemporalTimeZone_);
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
        private JSDynamicObject executeAndSpecialize(Object arguments0Value, Object arguments1Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                this.toTemporalDate_ = super.insert(ToTemporalDateNode.create(this.getContext()));
                this.toTemporalTimeZone_ = super.insert(ToTemporalTimeZoneNode.create(this.getContext()));
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                JSDynamicObject jSDynamicObject = this.toZonedDateTime(arguments0Value, arguments1Value, this.toTemporalDate_, this.toTemporalTimeZone_);
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
            return NodeCost.MONOMORPHIC;
        }

        @Override
        public Introspection getIntrospectionData() {
            Object[] data = new Object[2];
            data[0] = 0;
            int state_0 = this.state_0_;
            Object[] s = new Object[3];
            s[0] = "toZonedDateTime";
            if (state_0 != 0) {
                s[1] = (byte)1;
                ArrayList<List<JavaScriptBaseNode>> cached = new ArrayList<List<JavaScriptBaseNode>>();
                cached.add(Arrays.asList(this.toTemporalDate_, this.toTemporalTimeZone_));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static TemporalPlainTimePrototypeBuiltins.JSTemporalPlainTimeToZonedDateTime create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSTemporalPlainTimeToZonedDateTimeNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=TemporalPlainTimePrototypeBuiltins.JSTemporalPlainTimeToPlainDateTime.class)
    public static final class JSTemporalPlainTimeToPlainDateTimeNodeGen
    extends TemporalPlainTimePrototypeBuiltins.JSTemporalPlainTimeToPlainDateTime
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private ToTemporalDateNode toTemporalDate_;

        private JSTemporalPlainTimeToPlainDateTimeNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
                return this.toPlainDateTime(arguments0Value_, arguments1Value_, this.toTemporalDate_);
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
        private JSDynamicObject executeAndSpecialize(Object arguments0Value, Object arguments1Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                this.toTemporalDate_ = super.insert(ToTemporalDateNode.create(this.getContext()));
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                JSDynamicObject jSDynamicObject = this.toPlainDateTime(arguments0Value, arguments1Value, this.toTemporalDate_);
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
            return NodeCost.MONOMORPHIC;
        }

        @Override
        public Introspection getIntrospectionData() {
            Object[] data = new Object[2];
            data[0] = 0;
            int state_0 = this.state_0_;
            Object[] s = new Object[3];
            s[0] = "toPlainDateTime";
            if (state_0 != 0) {
                s[1] = (byte)1;
                ArrayList<List<ToTemporalDateNode>> cached = new ArrayList<List<ToTemporalDateNode>>();
                cached.add(Arrays.asList(this.toTemporalDate_));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static TemporalPlainTimePrototypeBuiltins.JSTemporalPlainTimeToPlainDateTime create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSTemporalPlainTimeToPlainDateTimeNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=TemporalPlainTimePrototypeBuiltins.JSTemporalPlainTimeEquals.class)
    public static final class JSTemporalPlainTimeEqualsNodeGen
    extends TemporalPlainTimePrototypeBuiltins.JSTemporalPlainTimeEquals
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private ToTemporalTimeNode equalsGeneric_toTemporalTime_;

        private JSTemporalPlainTimeEqualsNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
                if ((state_0 & 1) != 0 && arguments1Value_ instanceof JSDynamicObject && JSGuards.isJSTemporalTime(arguments1Value__ = (JSDynamicObject)arguments1Value_)) {
                    return this.equalsOtherObj(arguments0Value_, arguments1Value__);
                }
                if ((state_0 & 2) != 0 && !JSGuards.isJSTemporalTime(arguments1Value_)) {
                    return this.equalsGeneric(arguments0Value_, arguments1Value_, this.equalsGeneric_toTemporalTime_);
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
                if ((state_0 & 1) != 0 && arguments1Value_ instanceof JSDynamicObject && JSGuards.isJSTemporalTime(arguments1Value__ = (JSDynamicObject)arguments1Value_)) {
                    return this.equalsOtherObj(arguments0Value_, arguments1Value__);
                }
                if ((state_0 & 2) != 0 && !JSGuards.isJSTemporalTime(arguments1Value_)) {
                    return this.equalsGeneric(arguments0Value_, arguments1Value_, this.equalsGeneric_toTemporalTime_);
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
                JSDynamicObject arguments1Value_;
                int state_0 = this.state_0_;
                if (arguments1Value instanceof JSDynamicObject && JSGuards.isJSTemporalTime(arguments1Value_ = (JSDynamicObject)arguments1Value)) {
                    this.state_0_ = state_0 |= 1;
                    lock.unlock();
                    hasLock = false;
                    boolean bl = this.equalsOtherObj(arguments0Value, arguments1Value_);
                    return bl;
                }
                if (!JSGuards.isJSTemporalTime(arguments1Value)) {
                    this.equalsGeneric_toTemporalTime_ = super.insert(ToTemporalTimeNode.create(this.getContext()));
                    this.state_0_ = state_0 |= 2;
                    lock.unlock();
                    hasLock = false;
                    boolean bl = this.equalsGeneric(arguments0Value, arguments1Value, this.equalsGeneric_toTemporalTime_);
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
            Object[] data = new Object[3];
            data[0] = 0;
            int state_0 = this.state_0_;
            Object[] s = new Object[3];
            s[0] = "equalsOtherObj";
            s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[1] = s;
            s = new Object[3];
            s[0] = "equalsGeneric";
            if ((state_0 & 2) != 0) {
                s[1] = (byte)1;
                ArrayList<List<ToTemporalTimeNode>> cached = new ArrayList<List<ToTemporalTimeNode>>();
                cached.add(Arrays.asList(this.equalsGeneric_toTemporalTime_));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[2] = s;
            return Introspection.Provider.create(data);
        }

        public static TemporalPlainTimePrototypeBuiltins.JSTemporalPlainTimeEquals create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSTemporalPlainTimeEqualsNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=TemporalPlainTimePrototypeBuiltins.JSTemporalPlainTimeRound.class)
    public static final class JSTemporalPlainTimeRoundNodeGen
    extends TemporalPlainTimePrototypeBuiltins.JSTemporalPlainTimeRound
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private JSToNumberNode toNumber_;
        @Node.Child
        private TruffleString.EqualNode equalNode_;

        private JSTemporalPlainTimeRoundNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
                return this.round(arguments0Value_, arguments1Value_, this.toNumber_, this.equalNode_);
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
        private JSDynamicObject executeAndSpecialize(Object arguments0Value, Object arguments1Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                this.toNumber_ = super.insert(JSToNumberNode.create());
                this.equalNode_ = super.insert(TruffleString.EqualNode.create());
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                JSDynamicObject jSDynamicObject = this.round(arguments0Value, arguments1Value, this.toNumber_, this.equalNode_);
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
            return NodeCost.MONOMORPHIC;
        }

        @Override
        public Introspection getIntrospectionData() {
            Object[] data = new Object[2];
            data[0] = 0;
            int state_0 = this.state_0_;
            Object[] s = new Object[3];
            s[0] = "round";
            if (state_0 != 0) {
                s[1] = (byte)1;
                ArrayList<List<Node>> cached = new ArrayList<List<Node>>();
                cached.add(Arrays.asList(this.toNumber_, this.equalNode_));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static TemporalPlainTimePrototypeBuiltins.JSTemporalPlainTimeRound create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSTemporalPlainTimeRoundNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=TemporalPlainTimePrototypeBuiltins.JSTemporalPlainTimeSince.class)
    public static final class JSTemporalPlainTimeSinceNodeGen
    extends TemporalPlainTimePrototypeBuiltins.JSTemporalPlainTimeSince
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
        private SinceData since_cache;

        private JSTemporalPlainTimeSinceNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            SinceData s0_;
            int state_0 = this.state_0_;
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            Object arguments2Value_ = this.arguments2_.execute(frameValue);
            if (state_0 != 0 && (s0_ = this.since_cache) != null) {
                return this.since(arguments0Value_, arguments1Value_, arguments2Value_, s0_.toNumber_, s0_.namesNode_, s0_.toTemporalTime_, s0_.equalNode_, s0_.roundDurationNode_);
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
        private JSDynamicObject executeAndSpecialize(Object arguments0Value, Object arguments1Value, Object arguments2Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                SinceData s0_ = super.insert(new SinceData());
                s0_.toNumber_ = s0_.insertAccessor(JSToNumberNode.create());
                s0_.namesNode_ = s0_.insertAccessor(EnumerableOwnPropertyNamesNode.createKeys(this.getContext()));
                s0_.toTemporalTime_ = s0_.insertAccessor(ToTemporalTimeNode.create(this.getContext()));
                s0_.equalNode_ = s0_.insertAccessor(TruffleString.EqualNode.create());
                s0_.roundDurationNode_ = s0_.insertAccessor(TemporalRoundDurationNode.create(this.getContext()));
                VarHandle.storeStoreFence();
                this.since_cache = s0_;
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                JSDynamicObject jSDynamicObject = this.since(arguments0Value, arguments1Value, arguments2Value, s0_.toNumber_, s0_.namesNode_, s0_.toTemporalTime_, s0_.equalNode_, s0_.roundDurationNode_);
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
            return NodeCost.MONOMORPHIC;
        }

        @Override
        public Introspection getIntrospectionData() {
            Object[] data = new Object[2];
            data[0] = 0;
            int state_0 = this.state_0_;
            Object[] s = new Object[3];
            s[0] = "since";
            if (state_0 != 0) {
                s[1] = (byte)1;
                ArrayList<List<Node>> cached = new ArrayList<List<Node>>();
                SinceData s0_ = this.since_cache;
                if (s0_ != null) {
                    cached.add(Arrays.asList(s0_.toNumber_, s0_.namesNode_, s0_.toTemporalTime_, s0_.equalNode_, s0_.roundDurationNode_));
                }
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static TemporalPlainTimePrototypeBuiltins.JSTemporalPlainTimeSince create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSTemporalPlainTimeSinceNodeGen(context, builtin, arguments);
        }

        @GeneratedBy(value=TemporalPlainTimePrototypeBuiltins.JSTemporalPlainTimeSince.class)
        private static final class SinceData
        extends Node {
            @Node.Child
            JSToNumberNode toNumber_;
            @Node.Child
            EnumerableOwnPropertyNamesNode namesNode_;
            @Node.Child
            ToTemporalTimeNode toTemporalTime_;
            @Node.Child
            TruffleString.EqualNode equalNode_;
            @Node.Child
            TemporalRoundDurationNode roundDurationNode_;

            SinceData() {
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

    @GeneratedBy(value=TemporalPlainTimePrototypeBuiltins.JSTemporalPlainTimeUntil.class)
    public static final class JSTemporalPlainTimeUntilNodeGen
    extends TemporalPlainTimePrototypeBuiltins.JSTemporalPlainTimeUntil
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
        private UntilData until_cache;

        private JSTemporalPlainTimeUntilNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            UntilData s0_;
            int state_0 = this.state_0_;
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            Object arguments2Value_ = this.arguments2_.execute(frameValue);
            if (state_0 != 0 && (s0_ = this.until_cache) != null) {
                return this.until(arguments0Value_, arguments1Value_, arguments2Value_, s0_.toNumber_, s0_.namesNode_, s0_.toTemporalTime_, s0_.equalNode_, s0_.roundDurationNode_);
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
        private JSDynamicObject executeAndSpecialize(Object arguments0Value, Object arguments1Value, Object arguments2Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                UntilData s0_ = super.insert(new UntilData());
                s0_.toNumber_ = s0_.insertAccessor(JSToNumberNode.create());
                s0_.namesNode_ = s0_.insertAccessor(EnumerableOwnPropertyNamesNode.createKeys(this.getContext()));
                s0_.toTemporalTime_ = s0_.insertAccessor(ToTemporalTimeNode.create(this.getContext()));
                s0_.equalNode_ = s0_.insertAccessor(TruffleString.EqualNode.create());
                s0_.roundDurationNode_ = s0_.insertAccessor(TemporalRoundDurationNode.create(this.getContext()));
                VarHandle.storeStoreFence();
                this.until_cache = s0_;
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                JSDynamicObject jSDynamicObject = this.until(arguments0Value, arguments1Value, arguments2Value, s0_.toNumber_, s0_.namesNode_, s0_.toTemporalTime_, s0_.equalNode_, s0_.roundDurationNode_);
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
            return NodeCost.MONOMORPHIC;
        }

        @Override
        public Introspection getIntrospectionData() {
            Object[] data = new Object[2];
            data[0] = 0;
            int state_0 = this.state_0_;
            Object[] s = new Object[3];
            s[0] = "until";
            if (state_0 != 0) {
                s[1] = (byte)1;
                ArrayList<List<Node>> cached = new ArrayList<List<Node>>();
                UntilData s0_ = this.until_cache;
                if (s0_ != null) {
                    cached.add(Arrays.asList(s0_.toNumber_, s0_.namesNode_, s0_.toTemporalTime_, s0_.equalNode_, s0_.roundDurationNode_));
                }
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static TemporalPlainTimePrototypeBuiltins.JSTemporalPlainTimeUntil create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSTemporalPlainTimeUntilNodeGen(context, builtin, arguments);
        }

        @GeneratedBy(value=TemporalPlainTimePrototypeBuiltins.JSTemporalPlainTimeUntil.class)
        private static final class UntilData
        extends Node {
            @Node.Child
            JSToNumberNode toNumber_;
            @Node.Child
            EnumerableOwnPropertyNamesNode namesNode_;
            @Node.Child
            ToTemporalTimeNode toTemporalTime_;
            @Node.Child
            TruffleString.EqualNode equalNode_;
            @Node.Child
            TemporalRoundDurationNode roundDurationNode_;

            UntilData() {
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

    @GeneratedBy(value=TemporalPlainTimePrototypeBuiltins.JSTemporalPlainTimeWith.class)
    public static final class JSTemporalPlainTimeWithNodeGen
    extends TemporalPlainTimePrototypeBuiltins.JSTemporalPlainTimeWith
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
        private JSToIntegerThrowOnInfinityNode toIntThrows_;
        @Node.Child
        private JSToIntegerAsIntNode toInt_;

        private JSTemporalPlainTimeWithNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
                return this.with(arguments0Value_, arguments1Value_, arguments2Value_, this.toIntThrows_, this.toInt_);
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
        private JSDynamicObject executeAndSpecialize(Object arguments0Value, Object arguments1Value, Object arguments2Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                this.toIntThrows_ = super.insert(JSToIntegerThrowOnInfinityNode.create());
                this.toInt_ = super.insert(JSToIntegerAsIntNode.create());
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                JSDynamicObject jSDynamicObject = this.with(arguments0Value, arguments1Value, arguments2Value, this.toIntThrows_, this.toInt_);
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
            return NodeCost.MONOMORPHIC;
        }

        @Override
        public Introspection getIntrospectionData() {
            Object[] data = new Object[2];
            data[0] = 0;
            int state_0 = this.state_0_;
            Object[] s = new Object[3];
            s[0] = "with";
            if (state_0 != 0) {
                s[1] = (byte)1;
                ArrayList<List<JavaScriptBaseNode>> cached = new ArrayList<List<JavaScriptBaseNode>>();
                cached.add(Arrays.asList(this.toIntThrows_, this.toInt_));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static TemporalPlainTimePrototypeBuiltins.JSTemporalPlainTimeWith create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSTemporalPlainTimeWithNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=TemporalPlainTimePrototypeBuiltins.JSTemporalPlainTimeSubtract.class)
    public static final class JSTemporalPlainTimeSubtractNodeGen
    extends TemporalPlainTimePrototypeBuiltins.JSTemporalPlainTimeSubtract
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private ToLimitedTemporalDurationNode toLimitedTemporalDurationNode_;

        private JSTemporalPlainTimeSubtractNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
                return this.subtract(arguments0Value_, arguments1Value_, this.toLimitedTemporalDurationNode_);
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
        private JSDynamicObject executeAndSpecialize(Object arguments0Value, Object arguments1Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                this.toLimitedTemporalDurationNode_ = super.insert(ToLimitedTemporalDurationNode.create());
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                JSDynamicObject jSDynamicObject = this.subtract(arguments0Value, arguments1Value, this.toLimitedTemporalDurationNode_);
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
            return NodeCost.MONOMORPHIC;
        }

        @Override
        public Introspection getIntrospectionData() {
            Object[] data = new Object[2];
            data[0] = 0;
            int state_0 = this.state_0_;
            Object[] s = new Object[3];
            s[0] = "subtract";
            if (state_0 != 0) {
                s[1] = (byte)1;
                ArrayList<List<ToLimitedTemporalDurationNode>> cached = new ArrayList<List<ToLimitedTemporalDurationNode>>();
                cached.add(Arrays.asList(this.toLimitedTemporalDurationNode_));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static TemporalPlainTimePrototypeBuiltins.JSTemporalPlainTimeSubtract create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSTemporalPlainTimeSubtractNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=TemporalPlainTimePrototypeBuiltins.JSTemporalPlainTimeAdd.class)
    public static final class JSTemporalPlainTimeAddNodeGen
    extends TemporalPlainTimePrototypeBuiltins.JSTemporalPlainTimeAdd
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private ToLimitedTemporalDurationNode toLimitedTemporalDurationNode_;

        private JSTemporalPlainTimeAddNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
                return this.add(arguments0Value_, arguments1Value_, this.toLimitedTemporalDurationNode_);
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
        private JSDynamicObject executeAndSpecialize(Object arguments0Value, Object arguments1Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                this.toLimitedTemporalDurationNode_ = super.insert(ToLimitedTemporalDurationNode.create());
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                JSDynamicObject jSDynamicObject = this.add(arguments0Value, arguments1Value, this.toLimitedTemporalDurationNode_);
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
            return NodeCost.MONOMORPHIC;
        }

        @Override
        public Introspection getIntrospectionData() {
            Object[] data = new Object[2];
            data[0] = 0;
            int state_0 = this.state_0_;
            Object[] s = new Object[3];
            s[0] = "add";
            if (state_0 != 0) {
                s[1] = (byte)1;
                ArrayList<List<ToLimitedTemporalDurationNode>> cached = new ArrayList<List<ToLimitedTemporalDurationNode>>();
                cached.add(Arrays.asList(this.toLimitedTemporalDurationNode_));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static TemporalPlainTimePrototypeBuiltins.JSTemporalPlainTimeAdd create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSTemporalPlainTimeAddNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=TemporalPlainTimePrototypeBuiltins.JSTemporalPlainTimeGetterNode.class)
    public static final class JSTemporalPlainTimeGetterNodeGen
    extends TemporalPlainTimePrototypeBuiltins.JSTemporalPlainTimeGetterNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @CompilerDirectives.CompilationFinal
        private int state_0_;

        private JSTemporalPlainTimeGetterNodeGen(JSContext context, JSBuiltin builtin, TemporalPlainTimePrototypeBuiltins.TemporalPlainTimePrototype property, JavaScriptNode[] arguments) {
            super(context, builtin, property);
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
                if ((state_0 & 1) != 0 && JSGuards.isJSTemporalTime(arguments0Value_)) {
                    return this.timeGetter(arguments0Value_);
                }
                if ((state_0 & 2) != 0 && !JSGuards.isJSTemporalTime(arguments0Value_)) {
                    return TemporalPlainTimePrototypeBuiltins.JSTemporalPlainTimeGetterNode.error(arguments0Value_);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_);
        }

        @Override
        public int executeInt(VirtualFrame frameValue) throws UnexpectedResultException {
            int state_0 = this.state_0_;
            if ((state_0 & 1) != 0) {
                return JSTypesGen.expectInteger(this.execute(frameValue));
            }
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            if ((state_0 & 2) != 0 && !JSGuards.isJSTemporalTime(arguments0Value_)) {
                return TemporalPlainTimePrototypeBuiltins.JSTemporalPlainTimeGetterNode.error(arguments0Value_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return JSTypesGen.expectInteger(this.executeAndSpecialize(arguments0Value_));
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            int state_0 = this.state_0_;
            try {
                if ((state_0 & 1) == 0 && state_0 != 0) {
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
            int state_0 = this.state_0_;
            if (JSGuards.isJSTemporalTime(arguments0Value)) {
                this.state_0_ = state_0 |= 1;
                return this.timeGetter(arguments0Value);
            }
            if (!JSGuards.isJSTemporalTime(arguments0Value)) {
                this.state_0_ = state_0 |= 2;
                return TemporalPlainTimePrototypeBuiltins.JSTemporalPlainTimeGetterNode.error(arguments0Value);
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
            s[0] = "timeGetter";
            s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[1] = s;
            s = new Object[3];
            s[0] = "error";
            s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[2] = s;
            return Introspection.Provider.create(data);
        }

        public static TemporalPlainTimePrototypeBuiltins.JSTemporalPlainTimeGetterNode create(JSContext context, JSBuiltin builtin, TemporalPlainTimePrototypeBuiltins.TemporalPlainTimePrototype property, JavaScriptNode[] arguments) {
            return new JSTemporalPlainTimeGetterNodeGen(context, builtin, property, arguments);
        }
    }
}

