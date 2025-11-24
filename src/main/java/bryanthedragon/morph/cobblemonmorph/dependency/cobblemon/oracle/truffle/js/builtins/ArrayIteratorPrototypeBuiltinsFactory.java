
package com.oracle.truffle.js.builtins;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.js.builtins.ArrayIteratorPrototypeBuiltins;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;

@GeneratedBy(value=ArrayIteratorPrototypeBuiltins.class)
public final class ArrayIteratorPrototypeBuiltinsFactory {

    @GeneratedBy(value=ArrayIteratorPrototypeBuiltins.ArrayIteratorNextNode.class)
    public static final class ArrayIteratorNextNodeGen
    extends ArrayIteratorPrototypeBuiltins.ArrayIteratorNextNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @CompilerDirectives.CompilationFinal
        private int state_0_;

        private ArrayIteratorNextNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            super(context, builtin);
            this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
        }

        @Override
        public JavaScriptNode[] getArguments() {
            return new JavaScriptNode[]{this.arguments0_};
        }

        private boolean fallbackGuard_(Object arguments0Value) {
            JSDynamicObject arguments0Value_;
            return !(arguments0Value instanceof JSDynamicObject) || !this.isArrayIterator(arguments0Value_ = (JSDynamicObject)arguments0Value);
        }

        @Override
        public Object execute(VirtualFrame frameValue) {
            JSDynamicObject arguments0Value__;
            int state_0 = this.state_0_;
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSDynamicObject && this.isArrayIterator(arguments0Value__ = (JSDynamicObject)arguments0Value_)) {
                return this.doArrayIterator(frameValue, arguments0Value__);
            }
            if ((state_0 & 2) != 0 && this.fallbackGuard_(arguments0Value_)) {
                return this.doIncompatibleReceiver(arguments0Value_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(frameValue, arguments0Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.execute(frameValue);
        }

        private JSDynamicObject executeAndSpecialize(VirtualFrame frameValue, Object arguments0Value) {
            JSDynamicObject arguments0Value_;
            int state_0 = this.state_0_;
            if (arguments0Value instanceof JSDynamicObject && this.isArrayIterator(arguments0Value_ = (JSDynamicObject)arguments0Value)) {
                this.state_0_ = state_0 |= 1;
                return this.doArrayIterator(frameValue, arguments0Value_);
            }
            this.state_0_ = state_0 |= 2;
            return this.doIncompatibleReceiver(arguments0Value);
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
            s[0] = "doArrayIterator";
            s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[1] = s;
            s = new Object[3];
            s[0] = "doIncompatibleReceiver";
            s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[2] = s;
            return Introspection.Provider.create(data);
        }

        public static ArrayIteratorPrototypeBuiltins.ArrayIteratorNextNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new ArrayIteratorNextNodeGen(context, builtin, arguments);
        }
    }
}

