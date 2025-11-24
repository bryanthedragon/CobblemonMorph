
package com.oracle.truffle.js.builtins;

import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.js.builtins.AsyncGeneratorPrototypeBuiltins;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.objects.Completion;

@GeneratedBy(value=AsyncGeneratorPrototypeBuiltins.class)
public final class AsyncGeneratorPrototypeBuiltinsFactory {

    @GeneratedBy(value=AsyncGeneratorPrototypeBuiltins.AsyncGeneratorResumeNode.class)
    public static final class AsyncGeneratorResumeNodeGen
    extends AsyncGeneratorPrototypeBuiltins.AsyncGeneratorResumeNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;

        private AsyncGeneratorResumeNodeGen(JSContext context, JSBuiltin builtin, Completion.Type resumeType, JavaScriptNode[] arguments) {
            super(context, builtin, resumeType);
            this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
            this.arguments1_ = arguments != null && 1 < arguments.length ? arguments[1] : null;
        }

        @Override
        public JavaScriptNode[] getArguments() {
            return new JavaScriptNode[]{this.arguments0_, this.arguments1_};
        }

        @Override
        public Object execute(VirtualFrame frameValue) {
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            return this.resume(frameValue, arguments0Value_, arguments1Value_);
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
            s[0] = "resume";
            s[1] = (byte)1;
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static AsyncGeneratorPrototypeBuiltins.AsyncGeneratorResumeNode create(JSContext context, JSBuiltin builtin, Completion.Type resumeType, JavaScriptNode[] arguments) {
            return new AsyncGeneratorResumeNodeGen(context, builtin, resumeType, arguments);
        }
    }
}

