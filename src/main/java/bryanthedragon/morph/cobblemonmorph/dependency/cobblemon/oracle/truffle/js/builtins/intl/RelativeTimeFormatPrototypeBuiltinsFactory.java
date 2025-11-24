
package com.oracle.truffle.js.builtins.intl;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.builtins.intl.RelativeTimeFormatPrototypeBuiltins;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.cast.JSToNumberNode;
import com.oracle.truffle.js.nodes.cast.JSToStringNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.builtins.intl.JSRelativeTimeFormatObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=RelativeTimeFormatPrototypeBuiltins.class)
public final class RelativeTimeFormatPrototypeBuiltinsFactory {

    @GeneratedBy(value=RelativeTimeFormatPrototypeBuiltins.JSRelativeTimeFormatFormatToPartsNode.class)
    public static final class JSRelativeTimeFormatFormatToPartsNodeGen
    extends RelativeTimeFormatPrototypeBuiltins.JSRelativeTimeFormatFormatToPartsNode
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
        private JSToStringNode formatToParts_toStringNode_;
        @Node.Child
        private JSToNumberNode formatToParts_toNumberNode_;

        private JSRelativeTimeFormatFormatToPartsNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
                if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSRelativeTimeFormatObject) {
                    JSRelativeTimeFormatObject arguments0Value__ = (JSRelativeTimeFormatObject)arguments0Value_;
                    return this.doFormatToParts(arguments0Value__, arguments1Value_, arguments2Value_, this.formatToParts_toStringNode_, this.formatToParts_toNumberNode_);
                }
                if ((state_0 & 2) != 0 && !JSGuards.isJSRelativeTimeFormat(arguments0Value_)) {
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

        private Object executeAndSpecialize(Object arguments0Value, Object arguments1Value, Object arguments2Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                if (arguments0Value instanceof JSRelativeTimeFormatObject) {
                    JSRelativeTimeFormatObject arguments0Value_ = (JSRelativeTimeFormatObject)arguments0Value;
                    this.formatToParts_toStringNode_ = super.insert(JSToStringNode.create());
                    this.formatToParts_toNumberNode_ = super.insert(JSToNumberNode.create());
                    this.state_0_ = state_0 |= 1;
                    lock.unlock();
                    hasLock = false;
                    Object object = this.doFormatToParts(arguments0Value_, arguments1Value, arguments2Value, this.formatToParts_toStringNode_, this.formatToParts_toNumberNode_);
                    return object;
                }
                if (!JSGuards.isJSRelativeTimeFormat(arguments0Value)) {
                    this.state_0_ = state_0 |= 2;
                    lock.unlock();
                    hasLock = false;
                    Object object = this.throwTypeError(arguments0Value, arguments1Value, arguments2Value);
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
            Object[] data = new Object[3];
            data[0] = 0;
            int state_0 = this.state_0_;
            Object[] s = new Object[3];
            s[0] = "doFormatToParts";
            if ((state_0 & 1) != 0) {
                s[1] = (byte)1;
                ArrayList<List<JavaScriptBaseNode>> cached = new ArrayList<List<JavaScriptBaseNode>>();
                cached.add(Arrays.asList(this.formatToParts_toStringNode_, this.formatToParts_toNumberNode_));
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

        public static RelativeTimeFormatPrototypeBuiltins.JSRelativeTimeFormatFormatToPartsNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSRelativeTimeFormatFormatToPartsNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=RelativeTimeFormatPrototypeBuiltins.JSRelativeTimeFormatFormatNode.class)
    public static final class JSRelativeTimeFormatFormatNodeGen
    extends RelativeTimeFormatPrototypeBuiltins.JSRelativeTimeFormatFormatNode
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
        private JSToStringNode format_toStringNode_;
        @Node.Child
        private JSToNumberNode format_toNumberNode_;

        private JSRelativeTimeFormatFormatNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
                if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSRelativeTimeFormatObject) {
                    JSRelativeTimeFormatObject arguments0Value__ = (JSRelativeTimeFormatObject)arguments0Value_;
                    return this.doFormat(arguments0Value__, arguments1Value_, arguments2Value_, this.format_toStringNode_, this.format_toNumberNode_);
                }
                if ((state_0 & 2) != 0 && !JSGuards.isJSRelativeTimeFormat(arguments0Value_)) {
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

        private Object executeAndSpecialize(Object arguments0Value, Object arguments1Value, Object arguments2Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                if (arguments0Value instanceof JSRelativeTimeFormatObject) {
                    JSRelativeTimeFormatObject arguments0Value_ = (JSRelativeTimeFormatObject)arguments0Value;
                    this.format_toStringNode_ = super.insert(JSToStringNode.create());
                    this.format_toNumberNode_ = super.insert(JSToNumberNode.create());
                    this.state_0_ = state_0 |= 1;
                    lock.unlock();
                    hasLock = false;
                    TruffleString truffleString = this.doFormat(arguments0Value_, arguments1Value, arguments2Value, this.format_toStringNode_, this.format_toNumberNode_);
                    return truffleString;
                }
                if (!JSGuards.isJSRelativeTimeFormat(arguments0Value)) {
                    this.state_0_ = state_0 |= 2;
                    lock.unlock();
                    hasLock = false;
                    Object object = this.throwTypeError(arguments0Value, arguments1Value, arguments2Value);
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
            Object[] data = new Object[3];
            data[0] = 0;
            int state_0 = this.state_0_;
            Object[] s = new Object[3];
            s[0] = "doFormat";
            if ((state_0 & 1) != 0) {
                s[1] = (byte)1;
                ArrayList<List<JavaScriptBaseNode>> cached = new ArrayList<List<JavaScriptBaseNode>>();
                cached.add(Arrays.asList(this.format_toStringNode_, this.format_toNumberNode_));
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

        public static RelativeTimeFormatPrototypeBuiltins.JSRelativeTimeFormatFormatNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSRelativeTimeFormatFormatNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=RelativeTimeFormatPrototypeBuiltins.JSRelativeTimeFormatResolvedOptionsNode.class)
    public static final class JSRelativeTimeFormatResolvedOptionsNodeGen
    extends RelativeTimeFormatPrototypeBuiltins.JSRelativeTimeFormatResolvedOptionsNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @CompilerDirectives.CompilationFinal
        private int state_0_;

        private JSRelativeTimeFormatResolvedOptionsNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSRelativeTimeFormatObject) {
                JSRelativeTimeFormatObject arguments0Value__ = (JSRelativeTimeFormatObject)arguments0Value_;
                return this.doResolvedOptions(arguments0Value__);
            }
            if ((state_0 & 2) != 0 && !JSGuards.isJSRelativeTimeFormat(arguments0Value_)) {
                return this.doResolvedOptions(arguments0Value_);
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
            if (arguments0Value instanceof JSRelativeTimeFormatObject) {
                JSRelativeTimeFormatObject arguments0Value_ = (JSRelativeTimeFormatObject)arguments0Value;
                this.state_0_ = state_0 |= 1;
                return this.doResolvedOptions(arguments0Value_);
            }
            if (!JSGuards.isJSRelativeTimeFormat(arguments0Value)) {
                this.state_0_ = state_0 |= 2;
                return this.doResolvedOptions(arguments0Value);
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
            s[0] = "doResolvedOptions";
            s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[1] = s;
            s = new Object[3];
            s[0] = "doResolvedOptions";
            s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[2] = s;
            return Introspection.Provider.create(data);
        }

        public static RelativeTimeFormatPrototypeBuiltins.JSRelativeTimeFormatResolvedOptionsNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSRelativeTimeFormatResolvedOptionsNodeGen(context, builtin, arguments);
        }
    }
}

