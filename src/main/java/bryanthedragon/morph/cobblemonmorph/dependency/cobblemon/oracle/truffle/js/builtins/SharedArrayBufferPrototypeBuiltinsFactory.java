
package com.oracle.truffle.js.builtins;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.js.builtins.SharedArrayBufferPrototypeBuiltins;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=SharedArrayBufferPrototypeBuiltins.class)
public final class SharedArrayBufferPrototypeBuiltinsFactory {

    @GeneratedBy(value=SharedArrayBufferPrototypeBuiltins.ByteLengthGetterNode.class)
    public static final class ByteLengthGetterNodeGen
    extends SharedArrayBufferPrototypeBuiltins.ByteLengthGetterNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @CompilerDirectives.CompilationFinal
        private int state_0_;

        private ByteLengthGetterNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
                if ((state_0 & 1) != 0 && JSGuards.isJSSharedArrayBuffer(arguments0Value_)) {
                    return SharedArrayBufferPrototypeBuiltins.ByteLengthGetterNode.sharedArrayBuffer(arguments0Value_);
                }
                if ((state_0 & 2) != 0 && !JSGuards.isJSSharedArrayBuffer(arguments0Value_)) {
                    return SharedArrayBufferPrototypeBuiltins.ByteLengthGetterNode.error(arguments0Value_);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_);
        }

        @Override
        public int executeInt(VirtualFrame frameValue) {
            int state_0 = this.state_0_;
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            if (state_0 != 0) {
                if ((state_0 & 1) != 0 && JSGuards.isJSSharedArrayBuffer(arguments0Value_)) {
                    return SharedArrayBufferPrototypeBuiltins.ByteLengthGetterNode.sharedArrayBuffer(arguments0Value_);
                }
                if ((state_0 & 2) != 0 && !JSGuards.isJSSharedArrayBuffer(arguments0Value_)) {
                    return SharedArrayBufferPrototypeBuiltins.ByteLengthGetterNode.error(arguments0Value_);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.executeInt(frameValue);
        }

        private int executeAndSpecialize(Object arguments0Value) {
            int state_0 = this.state_0_;
            if (JSGuards.isJSSharedArrayBuffer(arguments0Value)) {
                this.state_0_ = state_0 |= 1;
                return SharedArrayBufferPrototypeBuiltins.ByteLengthGetterNode.sharedArrayBuffer(arguments0Value);
            }
            if (!JSGuards.isJSSharedArrayBuffer(arguments0Value)) {
                this.state_0_ = state_0 |= 2;
                return SharedArrayBufferPrototypeBuiltins.ByteLengthGetterNode.error(arguments0Value);
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
            s[0] = "sharedArrayBuffer";
            s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[1] = s;
            s = new Object[3];
            s[0] = "error";
            s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[2] = s;
            return Introspection.Provider.create(data);
        }

        public static SharedArrayBufferPrototypeBuiltins.ByteLengthGetterNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new ByteLengthGetterNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=SharedArrayBufferPrototypeBuiltins.JSSharedArrayBufferSliceNode.class)
    public static final class JSSharedArrayBufferSliceNodeGen
    extends SharedArrayBufferPrototypeBuiltins.JSSharedArrayBufferSliceNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @Node.Child
        private JavaScriptNode arguments2_;
        @CompilerDirectives.CompilationFinal
        private int state_0_;
        @CompilerDirectives.CompilationFinal
        private int exclude_;

        private JSSharedArrayBufferSliceNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            if ((state_0 & 6) == 0 && state_0 != 0) {
                return this.execute_int_int0(state_0, frameValue);
            }
            return this.execute_generic1(state_0, frameValue);
        }

        private Object execute_int_int0(int state_0, VirtualFrame frameValue) {
            JSDynamicObject arguments0Value__;
            int arguments2Value_;
            int arguments1Value_;
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            try {
                arguments1Value_ = this.arguments1_.executeInt(frameValue);
            }
            catch (UnexpectedResultException ex) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                Object arguments2Value = this.arguments2_.execute(frameValue);
                return this.executeAndSpecialize(arguments0Value_, ex.getResult(), arguments2Value);
            }
            try {
                arguments2Value_ = this.arguments2_.executeInt(frameValue);
            }
            catch (UnexpectedResultException ex) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.executeAndSpecialize(arguments0Value_, arguments1Value_, ex.getResult());
            }
            assert ((state_0 & 1) != 0);
            if (arguments0Value_ instanceof JSDynamicObject && JSGuards.isJSSharedArrayBuffer(arguments0Value__ = (JSDynamicObject)arguments0Value_)) {
                return this.sliceSharedIntInt(arguments0Value__, arguments1Value_, arguments2Value_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_, arguments2Value_);
        }

        private Object execute_generic1(int state_0, VirtualFrame frameValue) {
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            Object arguments2Value_ = this.arguments2_.execute(frameValue);
            if ((state_0 & 3) != 0 && arguments0Value_ instanceof JSDynamicObject) {
                JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
                if ((state_0 & 1) != 0 && arguments1Value_ instanceof Integer) {
                    int arguments1Value__ = (Integer)arguments1Value_;
                    if (arguments2Value_ instanceof Integer) {
                        int arguments2Value__ = (Integer)arguments2Value_;
                        if (JSGuards.isJSSharedArrayBuffer(arguments0Value__)) {
                            return this.sliceSharedIntInt(arguments0Value__, arguments1Value__, arguments2Value__);
                        }
                    }
                }
                if ((state_0 & 2) != 0 && JSGuards.isJSSharedArrayBuffer(arguments0Value__)) {
                    return this.sliceShared(arguments0Value__, arguments1Value_, arguments2Value_);
                }
            }
            if ((state_0 & 4) != 0 && !JSGuards.isJSSharedArrayBuffer(arguments0Value_)) {
                return SharedArrayBufferPrototypeBuiltins.JSSharedArrayBufferSliceNode.error(arguments0Value_, arguments1Value_, arguments2Value_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_, arguments2Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.execute(frameValue);
        }

        private JSDynamicObject executeAndSpecialize(Object arguments0Value, Object arguments1Value, Object arguments2Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                int exclude = this.exclude_;
                if (arguments0Value instanceof JSDynamicObject) {
                    JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
                    if (exclude == 0 && arguments1Value instanceof Integer) {
                        int arguments1Value_ = (Integer)arguments1Value;
                        if (arguments2Value instanceof Integer) {
                            int arguments2Value_ = (Integer)arguments2Value;
                            if (JSGuards.isJSSharedArrayBuffer(arguments0Value_)) {
                                this.state_0_ = state_0 |= 1;
                                lock.unlock();
                                hasLock = false;
                                JSDynamicObject jSDynamicObject = this.sliceSharedIntInt(arguments0Value_, arguments1Value_, arguments2Value_);
                                return jSDynamicObject;
                            }
                        }
                    }
                    if (JSGuards.isJSSharedArrayBuffer(arguments0Value_)) {
                        this.exclude_ = exclude |= 1;
                        state_0 &= 0xFFFFFFFE;
                        this.state_0_ = state_0 |= 2;
                        lock.unlock();
                        hasLock = false;
                        JSDynamicObject jSDynamicObject = this.sliceShared(arguments0Value_, arguments1Value, arguments2Value);
                        return jSDynamicObject;
                    }
                }
                if (!JSGuards.isJSSharedArrayBuffer(arguments0Value)) {
                    this.state_0_ = state_0 |= 4;
                    lock.unlock();
                    hasLock = false;
                    JSDynamicObject jSDynamicObject = SharedArrayBufferPrototypeBuiltins.JSSharedArrayBufferSliceNode.error(arguments0Value, arguments1Value, arguments2Value);
                    return jSDynamicObject;
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
            Object[] data = new Object[4];
            data[0] = 0;
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            Object[] s = new Object[3];
            s[0] = "sliceSharedIntInt";
            s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : (exclude != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0));
            data[1] = s;
            s = new Object[3];
            s[0] = "sliceShared";
            s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[2] = s;
            s = new Object[3];
            s[0] = "error";
            s[1] = (state_0 & 4) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[3] = s;
            return Introspection.Provider.create(data);
        }

        public static SharedArrayBufferPrototypeBuiltins.JSSharedArrayBufferSliceNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSSharedArrayBufferSliceNodeGen(context, builtin, arguments);
        }
    }
}

