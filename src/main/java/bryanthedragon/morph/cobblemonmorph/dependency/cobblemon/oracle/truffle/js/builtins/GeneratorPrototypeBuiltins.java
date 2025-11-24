
package com.oracle.truffle.js.builtins;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.js.builtins.GeneratorPrototypeBuiltinsFactory;
import com.oracle.truffle.js.builtins.JSBuiltinsContainer;
import com.oracle.truffle.js.nodes.access.PropertyGetNode;
import com.oracle.truffle.js.nodes.function.InternalCallNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.nodes.function.JSBuiltinNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSArguments;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.builtins.BuiltinEnum;
import com.oracle.truffle.js.runtime.builtins.JSFunction;
import com.oracle.truffle.js.runtime.objects.Completion;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.Undefined;

public final class GeneratorPrototypeBuiltins
extends JSBuiltinsContainer.SwitchEnum<GeneratorPrototype> {
    public static final JSBuiltinsContainer BUILTINS = new GeneratorPrototypeBuiltins();

    protected GeneratorPrototypeBuiltins() {
        super(JSFunction.GENERATOR_PROTOTYPE_NAME, GeneratorPrototype.class);
    }

    @Override
    protected Object createNode(JSContext context, JSBuiltin builtin, boolean construct, boolean newTarget, GeneratorPrototype builtinEnum) {
        Completion.Type resumeMethod;
        assert (context.getEcmaScriptVersion() >= 6);
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
        return GeneratorPrototypeBuiltinsFactory.GeneratorResumeNodeGen.create(context, builtin, resumeMethod, GeneratorPrototypeBuiltins.args().withThis().fixedArgs(1).createArgumentNodes(context));
    }

    public static abstract class GeneratorResumeNode
    extends JSBuiltinNode {
        private final Completion.Type resumeType;
        @Node.Child
        private PropertyGetNode getGeneratorTarget;
        @Node.Child
        private PropertyGetNode getGeneratorContext;
        @Node.Child
        private InternalCallNode callNode;
        private final BranchProfile errorBranch = BranchProfile.create();

        public GeneratorResumeNode(JSContext context, JSBuiltin builtin, Completion.Type resumeType) {
            super(context, builtin);
            this.resumeType = resumeType;
            this.getGeneratorTarget = PropertyGetNode.createGetHidden(JSFunction.GENERATOR_TARGET_ID, context);
            this.getGeneratorContext = PropertyGetNode.createGetHidden(JSFunction.GENERATOR_CONTEXT_ID, context);
            this.callNode = InternalCallNode.create();
        }

        @Specialization
        protected Object resume(JSObject generator, Object value2) {
            Object generatorTarget = this.getGeneratorTarget.getValue(generator);
            if (generatorTarget != Undefined.instance) {
                Object generatorContext = this.getGeneratorContext.getValue(generator);
                return this.callNode.execute((CallTarget)generatorTarget, JSArguments.createResumeArguments(generatorContext, generator, this.resumeType, value2));
            }
            this.errorBranch.enter();
            throw Errors.createTypeErrorGeneratorObjectExpected();
        }

        @Specialization(guards={"!isJSObject(thisObj)"})
        protected Object resume(Object thisObj, Object value2) {
            throw Errors.createTypeErrorGeneratorObjectExpected();
        }
    }

    public static enum GeneratorPrototype implements BuiltinEnum<GeneratorPrototype>
    {
        next(1),
        return_(1),
        throw_(1);

        private final int length;

        private GeneratorPrototype(int length) {
            this.length = length;
        }

        @Override
        public int getLength() {
            return this.length;
        }
    }
}

