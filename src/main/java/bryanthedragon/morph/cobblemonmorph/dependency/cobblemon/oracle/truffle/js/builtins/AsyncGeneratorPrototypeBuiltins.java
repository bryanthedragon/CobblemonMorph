
package com.oracle.truffle.js.builtins;

import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.js.builtins.AsyncGeneratorPrototypeBuiltinsFactory;
import com.oracle.truffle.js.builtins.JSBuiltinsContainer;
import com.oracle.truffle.js.nodes.control.AsyncGeneratorEnqueueNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.nodes.function.JSBuiltinNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.builtins.BuiltinEnum;
import com.oracle.truffle.js.runtime.builtins.JSFunction;
import com.oracle.truffle.js.runtime.objects.Completion;

public final class AsyncGeneratorPrototypeBuiltins
extends JSBuiltinsContainer.SwitchEnum<AsyncGeneratorPrototype> {
    public static final JSBuiltinsContainer BUILTINS = new AsyncGeneratorPrototypeBuiltins();

    protected AsyncGeneratorPrototypeBuiltins() {
        super(JSFunction.ASYNC_GENERATOR_PROTOTYPE_NAME, AsyncGeneratorPrototype.class);
    }

    @Override
    protected Object createNode(JSContext context, JSBuiltin builtin, boolean construct, boolean newTarget, AsyncGeneratorPrototype builtinEnum) {
        Completion.Type resumeMethod;
        assert (context.getEcmaScriptVersion() >= 8);
        switch (builtinEnum) {
            case next: {
                resumeMethod = Completion.Type.Normal;
                break;
            }
            case return_: {
                resumeMethod = Completion.Type.Return;
                break;
            }
            case throw_: {
                resumeMethod = Completion.Type.Throw;
                break;
            }
            default: {
                return null;
            }
        }
        return AsyncGeneratorPrototypeBuiltinsFactory.AsyncGeneratorResumeNodeGen.create(context, builtin, resumeMethod, AsyncGeneratorPrototypeBuiltins.args().withThis().fixedArgs(1).createArgumentNodes(context));
    }

    public static abstract class AsyncGeneratorResumeNode
    extends JSBuiltinNode {
        private final Completion.Type resumeType;
        @Node.Child
        private AsyncGeneratorEnqueueNode enqueueNode;

        public AsyncGeneratorResumeNode(JSContext context, JSBuiltin builtin, Completion.Type resumeType) {
            super(context, builtin);
            this.resumeType = resumeType;
            this.enqueueNode = AsyncGeneratorEnqueueNode.create(context);
        }

        @Specialization
        protected Object resume(VirtualFrame frame, Object thisObj, Object value2) {
            Completion completion = Completion.create(this.resumeType, value2);
            return this.enqueueNode.execute(frame, thisObj, completion);
        }
    }

    public static enum AsyncGeneratorPrototype implements BuiltinEnum<AsyncGeneratorPrototype>
    {
        next(1),
        return_(1),
        throw_(1);

        private final int length;

        private AsyncGeneratorPrototype(int length) {
            this.length = length;
        }

        @Override
        public int getLength() {
            return this.length;
        }
    }
}

