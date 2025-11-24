/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.invoke.VarHandle
 */
package com.oracle.truffle.js.builtins.intl;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.builtins.intl.DateTimeFormatPrototypeBuiltins;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.cast.JSToNumberNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.builtins.intl.JSDateTimeFormatObject;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=DateTimeFormatPrototypeBuiltins.class)
public final class DateTimeFormatPrototypeBuiltinsFactory {

    @GeneratedBy(value=DateTimeFormatPrototypeBuiltins.JSDateTimeFormatFormatRangeToPartsNode.class)
    public static final class JSDateTimeFormatFormatRangeToPartsNodeGen
    extends DateTimeFormatPrototypeBuiltins.JSDateTimeFormatFormatRangeToPartsNode
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
        private FormatRangeToPartsData formatRangeToParts_cache;

        private JSDateTimeFormatFormatRangeToPartsNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
                if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSDateTimeFormatObject) {
                    JSDateTimeFormatObject arguments0Value__ = (JSDateTimeFormatObject)arguments0Value_;
                    FormatRangeToPartsData s0_ = this.formatRangeToParts_cache;
                    if (s0_ != null) {
                        return this.doFormatRangeToParts(arguments0Value__, arguments1Value_, arguments2Value_, s0_.startDateToNumberNode_, s0_.endDateToNumberNode_, s0_.errorBranch_);
                    }
                }
                if ((state_0 & 2) != 0 && JSDateTimeFormatFormatRangeToPartsNodeGen.fallbackGuard_(state_0, arguments0Value_, arguments1Value_, arguments2Value_)) {
                    return this.throwTypeError(arguments0Value_, arguments1Value_, arguments2Value_);
                }
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
                if (arguments0Value instanceof JSDateTimeFormatObject) {
                    JSDateTimeFormatObject arguments0Value_ = (JSDateTimeFormatObject)arguments0Value;
                    FormatRangeToPartsData s0_ = super.insert(new FormatRangeToPartsData());
                    s0_.startDateToNumberNode_ = s0_.insertAccessor(JSToNumberNode.create());
                    s0_.endDateToNumberNode_ = s0_.insertAccessor(JSToNumberNode.create());
                    s0_.errorBranch_ = BranchProfile.create();
                    VarHandle.storeStoreFence();
                    this.formatRangeToParts_cache = s0_;
                    this.state_0_ = state_0 |= 1;
                    lock.unlock();
                    hasLock = false;
                    Object object = this.doFormatRangeToParts(arguments0Value_, arguments1Value, arguments2Value, s0_.startDateToNumberNode_, s0_.endDateToNumberNode_, s0_.errorBranch_);
                    return object;
                }
                this.state_0_ = state_0 |= 2;
                lock.unlock();
                hasLock = false;
                Object object = this.throwTypeError(arguments0Value, arguments1Value, arguments2Value);
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
            Object[] s = new Object[3];
            s[0] = "doFormatRangeToParts";
            if ((state_0 & 1) != 0) {
                s[1] = (byte)1;
                ArrayList<List<Cloneable>> cached = new ArrayList<List<Cloneable>>();
                FormatRangeToPartsData s0_ = this.formatRangeToParts_cache;
                if (s0_ != null) {
                    cached.add(Arrays.asList(s0_.startDateToNumberNode_, s0_.endDateToNumberNode_, s0_.errorBranch_));
                }
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[1] = s;
            s = new Object[3];
            s[0] = "throwTypeError";
            s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[2] = s;
            return Introspection.Provider.create(data);
        }

        private static boolean fallbackGuard_(int state_0, Object arguments0Value, Object arguments1Value, Object arguments2Value) {
            return (state_0 & 1) != 0 || !(arguments0Value instanceof JSDateTimeFormatObject);
        }

        public static DateTimeFormatPrototypeBuiltins.JSDateTimeFormatFormatRangeToPartsNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSDateTimeFormatFormatRangeToPartsNodeGen(context, builtin, arguments);
        }

        @GeneratedBy(value=DateTimeFormatPrototypeBuiltins.JSDateTimeFormatFormatRangeToPartsNode.class)
        private static final class FormatRangeToPartsData
        extends Node {
            @Node.Child
            JSToNumberNode startDateToNumberNode_;
            @Node.Child
            JSToNumberNode endDateToNumberNode_;
            @CompilerDirectives.CompilationFinal
            BranchProfile errorBranch_;

            FormatRangeToPartsData() {
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

    @GeneratedBy(value=DateTimeFormatPrototypeBuiltins.JSDateTimeFormatFormatRangeNode.class)
    public static final class JSDateTimeFormatFormatRangeNodeGen
    extends DateTimeFormatPrototypeBuiltins.JSDateTimeFormatFormatRangeNode
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
        private FormatRangeData formatRange_cache;

        private JSDateTimeFormatFormatRangeNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
                if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSDateTimeFormatObject) {
                    JSDateTimeFormatObject arguments0Value__ = (JSDateTimeFormatObject)arguments0Value_;
                    FormatRangeData s0_ = this.formatRange_cache;
                    if (s0_ != null) {
                        return this.doFormatRange(arguments0Value__, arguments1Value_, arguments2Value_, s0_.startDateToNumberNode_, s0_.endDateToNumberNode_, s0_.errorBranch_);
                    }
                }
                if ((state_0 & 2) != 0 && JSDateTimeFormatFormatRangeNodeGen.fallbackGuard_(state_0, arguments0Value_, arguments1Value_, arguments2Value_)) {
                    return this.throwTypeError(arguments0Value_, arguments1Value_, arguments2Value_);
                }
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
                if (arguments0Value instanceof JSDateTimeFormatObject) {
                    JSDateTimeFormatObject arguments0Value_ = (JSDateTimeFormatObject)arguments0Value;
                    FormatRangeData s0_ = super.insert(new FormatRangeData());
                    s0_.startDateToNumberNode_ = s0_.insertAccessor(JSToNumberNode.create());
                    s0_.endDateToNumberNode_ = s0_.insertAccessor(JSToNumberNode.create());
                    s0_.errorBranch_ = BranchProfile.create();
                    VarHandle.storeStoreFence();
                    this.formatRange_cache = s0_;
                    this.state_0_ = state_0 |= 1;
                    lock.unlock();
                    hasLock = false;
                    TruffleString truffleString = this.doFormatRange(arguments0Value_, arguments1Value, arguments2Value, s0_.startDateToNumberNode_, s0_.endDateToNumberNode_, s0_.errorBranch_);
                    return truffleString;
                }
                this.state_0_ = state_0 |= 2;
                lock.unlock();
                hasLock = false;
                Object object = this.throwTypeError(arguments0Value, arguments1Value, arguments2Value);
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
            Object[] s = new Object[3];
            s[0] = "doFormatRange";
            if ((state_0 & 1) != 0) {
                s[1] = (byte)1;
                ArrayList<List<Cloneable>> cached = new ArrayList<List<Cloneable>>();
                FormatRangeData s0_ = this.formatRange_cache;
                if (s0_ != null) {
                    cached.add(Arrays.asList(s0_.startDateToNumberNode_, s0_.endDateToNumberNode_, s0_.errorBranch_));
                }
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[1] = s;
            s = new Object[3];
            s[0] = "throwTypeError";
            s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[2] = s;
            return Introspection.Provider.create(data);
        }

        private static boolean fallbackGuard_(int state_0, Object arguments0Value, Object arguments1Value, Object arguments2Value) {
            return (state_0 & 1) != 0 || !(arguments0Value instanceof JSDateTimeFormatObject);
        }

        public static DateTimeFormatPrototypeBuiltins.JSDateTimeFormatFormatRangeNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSDateTimeFormatFormatRangeNodeGen(context, builtin, arguments);
        }

        @GeneratedBy(value=DateTimeFormatPrototypeBuiltins.JSDateTimeFormatFormatRangeNode.class)
        private static final class FormatRangeData
        extends Node {
            @Node.Child
            JSToNumberNode startDateToNumberNode_;
            @Node.Child
            JSToNumberNode endDateToNumberNode_;
            @CompilerDirectives.CompilationFinal
            BranchProfile errorBranch_;

            FormatRangeData() {
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

    @GeneratedBy(value=DateTimeFormatPrototypeBuiltins.JSDateTimeFormatFormatToPartsNode.class)
    public static final class JSDateTimeFormatFormatToPartsNodeGen
    extends DateTimeFormatPrototypeBuiltins.JSDateTimeFormatFormatToPartsNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @CompilerDirectives.CompilationFinal
        private int state_0_;

        private JSDateTimeFormatFormatToPartsNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
                if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSDateTimeFormatObject) {
                    JSDateTimeFormatObject arguments0Value__ = (JSDateTimeFormatObject)arguments0Value_;
                    return this.doFormatToParts(arguments0Value__, arguments1Value_);
                }
                if ((state_0 & 2) != 0 && JSDateTimeFormatFormatToPartsNodeGen.fallbackGuard_(state_0, arguments0Value_, arguments1Value_)) {
                    return this.throwTypeError(arguments0Value_, arguments1Value_);
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
            int state_0 = this.state_0_;
            if (arguments0Value instanceof JSDateTimeFormatObject) {
                JSDateTimeFormatObject arguments0Value_ = (JSDateTimeFormatObject)arguments0Value;
                this.state_0_ = state_0 |= 1;
                return this.doFormatToParts(arguments0Value_, arguments1Value);
            }
            this.state_0_ = state_0 |= 2;
            return this.throwTypeError(arguments0Value, arguments1Value);
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
            s[0] = "doFormatToParts";
            s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[1] = s;
            s = new Object[3];
            s[0] = "throwTypeError";
            s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[2] = s;
            return Introspection.Provider.create(data);
        }

        private static boolean fallbackGuard_(int state_0, Object arguments0Value, Object arguments1Value) {
            return (state_0 & 1) != 0 || !(arguments0Value instanceof JSDateTimeFormatObject);
        }

        public static DateTimeFormatPrototypeBuiltins.JSDateTimeFormatFormatToPartsNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSDateTimeFormatFormatToPartsNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=DateTimeFormatPrototypeBuiltins.JSDateTimeFormatResolvedOptionsNode.class)
    public static final class JSDateTimeFormatResolvedOptionsNodeGen
    extends DateTimeFormatPrototypeBuiltins.JSDateTimeFormatResolvedOptionsNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @CompilerDirectives.CompilationFinal
        private int state_0_;

        private JSDateTimeFormatResolvedOptionsNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSDateTimeFormatObject) {
                JSDateTimeFormatObject arguments0Value__ = (JSDateTimeFormatObject)arguments0Value_;
                return this.doResolvedOptions(arguments0Value__);
            }
            if ((state_0 & 2) != 0 && JSDateTimeFormatResolvedOptionsNodeGen.fallbackGuard_(state_0, arguments0Value_)) {
                return this.throwTypeError(arguments0Value_);
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
            if (arguments0Value instanceof JSDateTimeFormatObject) {
                JSDateTimeFormatObject arguments0Value_ = (JSDateTimeFormatObject)arguments0Value;
                this.state_0_ = state_0 |= 1;
                return this.doResolvedOptions(arguments0Value_);
            }
            this.state_0_ = state_0 |= 2;
            return this.throwTypeError(arguments0Value);
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
            s[0] = "doResolvedOptions";
            s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[1] = s;
            s = new Object[3];
            s[0] = "throwTypeError";
            s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[2] = s;
            return Introspection.Provider.create(data);
        }

        private static boolean fallbackGuard_(int state_0, Object arguments0Value) {
            return (state_0 & 1) != 0 || !(arguments0Value instanceof JSDateTimeFormatObject);
        }

        public static DateTimeFormatPrototypeBuiltins.JSDateTimeFormatResolvedOptionsNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSDateTimeFormatResolvedOptionsNodeGen(context, builtin, arguments);
        }
    }
}

