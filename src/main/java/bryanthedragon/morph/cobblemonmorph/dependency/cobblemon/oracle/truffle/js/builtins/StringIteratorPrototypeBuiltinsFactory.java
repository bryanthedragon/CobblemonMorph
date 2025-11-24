
package com.oracle.truffle.js.builtins;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.builtins.StringIteratorPrototypeBuiltins;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=StringIteratorPrototypeBuiltins.class)
public final class StringIteratorPrototypeBuiltinsFactory {

    @GeneratedBy(value=StringIteratorPrototypeBuiltins.StringIteratorNextNode.class)
    public static final class StringIteratorNextNodeGen
    extends StringIteratorPrototypeBuiltins.StringIteratorNextNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private TruffleString.FromCodePointNode stringIterator_fromCodePointNode_;
        @Node.Child
        private TruffleString.SubstringByteIndexNode stringIterator_substringNode_;

        private StringIteratorNextNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            super(context, builtin);
            this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
        }

        @Override
        public JavaScriptNode[] getArguments() {
            return new JavaScriptNode[]{this.arguments0_};
        }

        private boolean fallbackGuard_(Object arguments0Value) {
            JSDynamicObject arguments0Value_;
            return !(arguments0Value instanceof JSDynamicObject) || !this.isStringIterator(arguments0Value_ = (JSDynamicObject)arguments0Value);
        }

        @Override
        public Object execute(VirtualFrame frameValue) {
            JSDynamicObject arguments0Value__;
            int state_0 = this.state_0_;
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSDynamicObject && this.isStringIterator(arguments0Value__ = (JSDynamicObject)arguments0Value_)) {
                return this.doStringIterator(frameValue, arguments0Value__, this.stringIterator_fromCodePointNode_, this.stringIterator_substringNode_);
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

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private JSDynamicObject executeAndSpecialize(VirtualFrame frameValue, Object arguments0Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                JSDynamicObject arguments0Value_;
                int state_0 = this.state_0_;
                if (arguments0Value instanceof JSDynamicObject && this.isStringIterator(arguments0Value_ = (JSDynamicObject)arguments0Value)) {
                    this.stringIterator_fromCodePointNode_ = super.insert(TruffleString.FromCodePointNode.create());
                    this.stringIterator_substringNode_ = super.insert(TruffleString.SubstringByteIndexNode.create());
                    this.state_0_ = state_0 |= 1;
                    lock.unlock();
                    hasLock = false;
                    JSDynamicObject jSDynamicObject = this.doStringIterator(frameValue, arguments0Value_, this.stringIterator_fromCodePointNode_, this.stringIterator_substringNode_);
                    return jSDynamicObject;
                }
                this.state_0_ = state_0 |= 2;
                lock.unlock();
                hasLock = false;
                JSDynamicObject jSDynamicObject = this.doIncompatibleReceiver(arguments0Value);
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
            s[0] = "doStringIterator";
            if ((state_0 & 1) != 0) {
                s[1] = (byte)1;
                ArrayList<List<Node>> cached = new ArrayList<List<Node>>();
                cached.add(Arrays.asList(this.stringIterator_fromCodePointNode_, this.stringIterator_substringNode_));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[1] = s;
            s = new Object[3];
            s[0] = "doIncompatibleReceiver";
            s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[2] = s;
            return Introspection.Provider.create(data);
        }

        public static StringIteratorPrototypeBuiltins.StringIteratorNextNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new StringIteratorNextNodeGen(context, builtin, arguments);
        }
    }
}

