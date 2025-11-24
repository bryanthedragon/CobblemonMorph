
package com.oracle.truffle.js.builtins.intl;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.builtins.intl.ListFormatPrototypeBuiltins;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.cast.JSStringListFromIterableNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.builtins.intl.JSListFormatObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=ListFormatPrototypeBuiltins.class)
public final class ListFormatPrototypeBuiltinsFactory {

    @GeneratedBy(value=ListFormatPrototypeBuiltins.JSListFormatFormatToPartsNode.class)
    public static final class JSListFormatFormatToPartsNodeGen
    extends ListFormatPrototypeBuiltins.JSListFormatFormatToPartsNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private JSStringListFromIterableNode formatToParts_strListFromIterableNode_;

        private JSListFormatFormatToPartsNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
                if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSListFormatObject) {
                    JSListFormatObject arguments0Value__ = (JSListFormatObject)arguments0Value_;
                    return this.doFormatToParts(arguments0Value__, arguments1Value_, this.formatToParts_strListFromIterableNode_);
                }
                if ((state_0 & 2) != 0 && JSListFormatFormatToPartsNodeGen.fallbackGuard_(state_0, arguments0Value_, arguments1Value_)) {
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

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private Object executeAndSpecialize(Object arguments0Value, Object arguments1Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                if (arguments0Value instanceof JSListFormatObject) {
                    JSListFormatObject arguments0Value_ = (JSListFormatObject)arguments0Value;
                    this.formatToParts_strListFromIterableNode_ = super.insert(JSStringListFromIterableNode.create(this.getContext()));
                    this.state_0_ = state_0 |= 1;
                    lock.unlock();
                    hasLock = false;
                    Object object = this.doFormatToParts(arguments0Value_, arguments1Value, this.formatToParts_strListFromIterableNode_);
                    return object;
                }
                this.state_0_ = state_0 |= 2;
                lock.unlock();
                hasLock = false;
                Object object = this.throwTypeError(arguments0Value, arguments1Value);
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
            s[0] = "doFormatToParts";
            if ((state_0 & 1) != 0) {
                s[1] = (byte)1;
                ArrayList<List<JSStringListFromIterableNode>> cached = new ArrayList<List<JSStringListFromIterableNode>>();
                cached.add(Arrays.asList(this.formatToParts_strListFromIterableNode_));
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

        private static boolean fallbackGuard_(int state_0, Object arguments0Value, Object arguments1Value) {
            return (state_0 & 1) != 0 || !(arguments0Value instanceof JSListFormatObject);
        }

        public static ListFormatPrototypeBuiltins.JSListFormatFormatToPartsNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSListFormatFormatToPartsNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=ListFormatPrototypeBuiltins.JSListFormatFormatNode.class)
    public static final class JSListFormatFormatNodeGen
    extends ListFormatPrototypeBuiltins.JSListFormatFormatNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private JSStringListFromIterableNode format_strListFromIterableNode_;

        private JSListFormatFormatNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
                if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSListFormatObject) {
                    JSListFormatObject arguments0Value__ = (JSListFormatObject)arguments0Value_;
                    return this.doFormat(arguments0Value__, arguments1Value_, this.format_strListFromIterableNode_);
                }
                if ((state_0 & 2) != 0 && JSListFormatFormatNodeGen.fallbackGuard_(state_0, arguments0Value_, arguments1Value_)) {
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

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private Object executeAndSpecialize(Object arguments0Value, Object arguments1Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                if (arguments0Value instanceof JSListFormatObject) {
                    JSListFormatObject arguments0Value_ = (JSListFormatObject)arguments0Value;
                    this.format_strListFromIterableNode_ = super.insert(JSStringListFromIterableNode.create(this.getContext()));
                    this.state_0_ = state_0 |= 1;
                    lock.unlock();
                    hasLock = false;
                    TruffleString truffleString = this.doFormat(arguments0Value_, arguments1Value, this.format_strListFromIterableNode_);
                    return truffleString;
                }
                this.state_0_ = state_0 |= 2;
                lock.unlock();
                hasLock = false;
                Object object = this.throwTypeError(arguments0Value, arguments1Value);
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
            s[0] = "doFormat";
            if ((state_0 & 1) != 0) {
                s[1] = (byte)1;
                ArrayList<List<JSStringListFromIterableNode>> cached = new ArrayList<List<JSStringListFromIterableNode>>();
                cached.add(Arrays.asList(this.format_strListFromIterableNode_));
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

        private static boolean fallbackGuard_(int state_0, Object arguments0Value, Object arguments1Value) {
            return (state_0 & 1) != 0 || !(arguments0Value instanceof JSListFormatObject);
        }

        public static ListFormatPrototypeBuiltins.JSListFormatFormatNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSListFormatFormatNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=ListFormatPrototypeBuiltins.JSListFormatResolvedOptionsNode.class)
    public static final class JSListFormatResolvedOptionsNodeGen
    extends ListFormatPrototypeBuiltins.JSListFormatResolvedOptionsNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @CompilerDirectives.CompilationFinal
        private int state_0_;

        private JSListFormatResolvedOptionsNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSListFormatObject) {
                JSListFormatObject arguments0Value__ = (JSListFormatObject)arguments0Value_;
                return this.doResolvedOptions(arguments0Value__);
            }
            if ((state_0 & 2) != 0 && JSListFormatResolvedOptionsNodeGen.fallbackGuard_(state_0, arguments0Value_)) {
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
            if (arguments0Value instanceof JSListFormatObject) {
                JSListFormatObject arguments0Value_ = (JSListFormatObject)arguments0Value;
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
            return (state_0 & 1) != 0 || !(arguments0Value instanceof JSListFormatObject);
        }

        public static ListFormatPrototypeBuiltins.JSListFormatResolvedOptionsNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSListFormatResolvedOptionsNodeGen(context, builtin, arguments);
        }
    }
}

