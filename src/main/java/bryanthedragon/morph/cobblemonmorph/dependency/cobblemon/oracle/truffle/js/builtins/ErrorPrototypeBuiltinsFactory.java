
package com.oracle.truffle.js.builtins;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.builtins.ErrorPrototypeBuiltins;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=ErrorPrototypeBuiltins.class)
public final class ErrorPrototypeBuiltinsFactory {

    @GeneratedBy(value=ErrorPrototypeBuiltins.ErrorPrototypeGetStackTraceNode.class)
    public static final class ErrorPrototypeGetStackTraceNodeGen
    extends ErrorPrototypeBuiltins.ErrorPrototypeGetStackTraceNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @CompilerDirectives.CompilationFinal
        private int state_0_;

        private ErrorPrototypeGetStackTraceNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            super(context, builtin);
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
            if ((state_0 & 1) != 0 && !JSGuards.isJSObject(arguments0Value_)) {
                return this.getStackTrace(arguments0Value_);
            }
            if ((state_0 & 2) != 0 && arguments0Value_ instanceof JSDynamicObject && JSGuards.isJSObject(arguments0Value__ = (JSDynamicObject)arguments0Value_)) {
                return this.getStackTrace(arguments0Value__);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.execute(frameValue);
        }

        private JSDynamicObject executeAndSpecialize(Object arguments0Value) {
            JSDynamicObject arguments0Value_;
            int state_0 = this.state_0_;
            if (!JSGuards.isJSObject(arguments0Value)) {
                this.state_0_ = state_0 |= 1;
                return this.getStackTrace(arguments0Value);
            }
            if (arguments0Value instanceof JSDynamicObject && JSGuards.isJSObject(arguments0Value_ = (JSDynamicObject)arguments0Value)) {
                this.state_0_ = state_0 |= 2;
                return this.getStackTrace(arguments0Value_);
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
            s[0] = "getStackTrace";
            s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[1] = s;
            s = new Object[3];
            s[0] = "getStackTrace";
            s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[2] = s;
            return Introspection.Provider.create(data);
        }

        public static ErrorPrototypeBuiltins.ErrorPrototypeGetStackTraceNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new ErrorPrototypeGetStackTraceNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=ErrorPrototypeBuiltins.ErrorPrototypeToStringNode.class)
    public static final class ErrorPrototypeToStringNodeGen
    extends ErrorPrototypeBuiltins.ErrorPrototypeToStringNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private TruffleString.ConcatNode toStringNonObject_concatNode_;

        private ErrorPrototypeToStringNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            super(context, builtin);
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
            if ((state_0 & 1) != 0 && !JSGuards.isJSObject(arguments0Value_)) {
                return this.toStringNonObject(arguments0Value_, this.toStringNonObject_concatNode_);
            }
            if ((state_0 & 2) != 0 && arguments0Value_ instanceof JSDynamicObject && JSGuards.isJSObject(arguments0Value__ = (JSDynamicObject)arguments0Value_)) {
                return this.toStringObject(arguments0Value__);
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
                JSDynamicObject arguments0Value_;
                int state_0 = this.state_0_;
                if (!JSGuards.isJSObject(arguments0Value)) {
                    this.toStringNonObject_concatNode_ = super.insert(TruffleString.ConcatNode.create());
                    this.state_0_ = state_0 |= 1;
                    lock.unlock();
                    hasLock = false;
                    Object object = this.toStringNonObject(arguments0Value, this.toStringNonObject_concatNode_);
                    return object;
                }
                if (arguments0Value instanceof JSDynamicObject && JSGuards.isJSObject(arguments0Value_ = (JSDynamicObject)arguments0Value)) {
                    this.state_0_ = state_0 |= 2;
                    lock.unlock();
                    hasLock = false;
                    Object object = this.toStringObject(arguments0Value_);
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
            Object[] data = new Object[3];
            data[0] = 0;
            int state_0 = this.state_0_;
            Object[] s = new Object[3];
            s[0] = "toStringNonObject";
            if ((state_0 & 1) != 0) {
                s[1] = (byte)1;
                ArrayList<List<TruffleString.ConcatNode>> cached = new ArrayList<List<TruffleString.ConcatNode>>();
                cached.add(Arrays.asList(this.toStringNonObject_concatNode_));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[1] = s;
            s = new Object[3];
            s[0] = "toStringObject";
            s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[2] = s;
            return Introspection.Provider.create(data);
        }

        public static ErrorPrototypeBuiltins.ErrorPrototypeToStringNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new ErrorPrototypeToStringNodeGen(context, builtin, arguments);
        }
    }
}

