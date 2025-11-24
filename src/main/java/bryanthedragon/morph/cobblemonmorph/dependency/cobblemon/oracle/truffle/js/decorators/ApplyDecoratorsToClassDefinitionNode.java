
package com.oracle.truffle.js.decorators;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.js.decorators.CreateDecoratorContextObjectNode;
import com.oracle.truffle.js.nodes.function.JSFunctionCallNode;
import com.oracle.truffle.js.nodes.unary.IsCallableNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSArguments;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.Undefined;
import java.util.List;

public class ApplyDecoratorsToClassDefinitionNode
extends Node {
    @Node.Child
    private JSFunctionCallNode callNode;
    @Node.Child
    private IsCallableNode isCallableNode;
    @Node.Child
    private CreateDecoratorContextObjectNode createDecoratorContextObject;
    private final BranchProfile errorProfile = BranchProfile.create();

    public static ApplyDecoratorsToClassDefinitionNode create(JSContext context) {
        return new ApplyDecoratorsToClassDefinitionNode(context);
    }

    ApplyDecoratorsToClassDefinitionNode(JSContext context) {
        this.createDecoratorContextObject = CreateDecoratorContextObjectNode.create(context, false);
        this.callNode = JSFunctionCallNode.createCall();
        this.isCallableNode = IsCallableNode.create();
    }

    @ExplodeLoop
    public Object executeDecorators(VirtualFrame frame, Object className, JSObject constructor, Object[] decorators, List<Object> extraInitializers) {
        Object classDef = constructor;
        for (Object decorator : decorators) {
            CreateDecoratorContextObjectNode.Record state = new CreateDecoratorContextObjectNode.Record();
            JSDynamicObject context = this.createDecoratorContextObject.evaluateClass(frame, className, extraInitializers, state);
            Object newDef = this.callNode.executeCall(JSArguments.create(Undefined.instance, decorator, classDef, context));
            state.finished = true;
            if (this.isCallableNode.executeBoolean(newDef)) {
                classDef = newDef;
                continue;
            }
            if (newDef == Undefined.instance) continue;
            this.errorProfile.enter();
            throw Errors.createTypeErrorWrongDecoratorReturn(this);
        }
        return classDef;
    }
}

