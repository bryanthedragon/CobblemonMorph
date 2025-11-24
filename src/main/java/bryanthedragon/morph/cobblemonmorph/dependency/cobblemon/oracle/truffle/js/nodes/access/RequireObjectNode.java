
package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Executed;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.access.RequireObjectNodeGen;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.util.Set;

public abstract class RequireObjectNode
extends JavaScriptNode {
    protected static final int MAX_SHAPE_COUNT = 1;
    @Node.Child
    @Executed
    protected JavaScriptNode operandNode;

    protected RequireObjectNode(JavaScriptNode operand) {
        this.operandNode = operand;
    }

    public abstract Object execute(Object var1);

    @Specialization(guards={"cachedShape.check(object)"}, limit="MAX_SHAPE_COUNT")
    protected static Object doObjectShape(JSDynamicObject object, @Cached(value="object.getShape()") Shape cachedShape, @Cached(value="isJSObject(object)") boolean cachedResult) {
        return RequireObjectNode.requireObject(object, cachedResult);
    }

    @Specialization(replaces={"doObjectShape"})
    protected static Object doObject(Object object) {
        return RequireObjectNode.requireObject(object, JSGuards.isJSObject(object));
    }

    private static Object requireObject(Object object, boolean isObject) {
        if (isObject) {
            return object;
        }
        throw Errors.createTypeErrorIncompatibleReceiver(object);
    }

    public static RequireObjectNode create() {
        return RequireObjectNodeGen.create(null);
    }

    public static JavaScriptNode create(JavaScriptNode operand) {
        return RequireObjectNodeGen.create(operand);
    }

    @Override
    protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
        return RequireObjectNodeGen.create(RequireObjectNode.cloneUninitialized(this.operandNode, materializedTags));
    }
}

