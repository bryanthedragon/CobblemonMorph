
package com.oracle.truffle.js.builtins.intl;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.js.builtins.intl.SegmenterPrototypeBuiltins;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.cast.JSToStringNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.builtins.intl.JSSegmenterObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=SegmenterPrototypeBuiltins.class)
public final class SegmenterPrototypeBuiltinsFactory {

    @GeneratedBy(value=SegmenterPrototypeBuiltins.JSSegmenterSegmentNode.class)
    public static final class JSSegmenterSegmentNodeGen
    extends SegmenterPrototypeBuiltins.JSSegmenterSegmentNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private JSToStringNode segmenter_toStringNode_;

        private JSSegmenterSegmentNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
                if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSSegmenterObject) {
                    JSSegmenterObject arguments0Value__ = (JSSegmenterObject)arguments0Value_;
                    return this.doSegmenter(arguments0Value__, arguments1Value_, this.segmenter_toStringNode_);
                }
                if ((state_0 & 2) != 0 && !JSGuards.isJSSegmenter(arguments0Value_)) {
                    return this.doOther(arguments0Value_, arguments1Value_);
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
                if (arguments0Value instanceof JSSegmenterObject) {
                    JSSegmenterObject arguments0Value_ = (JSSegmenterObject)arguments0Value;
                    this.segmenter_toStringNode_ = super.insert(JSToStringNode.create());
                    this.state_0_ = state_0 |= 1;
                    lock.unlock();
                    hasLock = false;
                    Object object = this.doSegmenter(arguments0Value_, arguments1Value, this.segmenter_toStringNode_);
                    return object;
                }
                if (!JSGuards.isJSSegmenter(arguments0Value)) {
                    this.state_0_ = state_0 |= 2;
                    lock.unlock();
                    hasLock = false;
                    Object object = this.doOther(arguments0Value, arguments1Value);
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
            Object[] data = new Object[3];
            data[0] = 0;
            int state_0 = this.state_0_;
            Object[] s = new Object[3];
            s[0] = "doSegmenter";
            if ((state_0 & 1) != 0) {
                s[1] = (byte)1;
                ArrayList<List<JSToStringNode>> cached = new ArrayList<List<JSToStringNode>>();
                cached.add(Arrays.asList(this.segmenter_toStringNode_));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[1] = s;
            s = new Object[3];
            s[0] = "doOther";
            s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[2] = s;
            return Introspection.Provider.create(data);
        }

        public static SegmenterPrototypeBuiltins.JSSegmenterSegmentNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSSegmenterSegmentNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=SegmenterPrototypeBuiltins.JSSegmenterResolvedOptionsNode.class)
    public static final class JSSegmenterResolvedOptionsNodeGen
    extends SegmenterPrototypeBuiltins.JSSegmenterResolvedOptionsNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @CompilerDirectives.CompilationFinal
        private int state_0_;

        private JSSegmenterResolvedOptionsNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSSegmenterObject) {
                JSSegmenterObject arguments0Value__ = (JSSegmenterObject)arguments0Value_;
                return this.doResolvedOptions(arguments0Value__);
            }
            if ((state_0 & 2) != 0 && !JSGuards.isJSSegmenter(arguments0Value_)) {
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
            if (arguments0Value instanceof JSSegmenterObject) {
                JSSegmenterObject arguments0Value_ = (JSSegmenterObject)arguments0Value;
                this.state_0_ = state_0 |= 1;
                return this.doResolvedOptions(arguments0Value_);
            }
            if (!JSGuards.isJSSegmenter(arguments0Value)) {
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

        public static SegmenterPrototypeBuiltins.JSSegmenterResolvedOptionsNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSSegmenterResolvedOptionsNodeGen(context, builtin, arguments);
        }
    }
}

