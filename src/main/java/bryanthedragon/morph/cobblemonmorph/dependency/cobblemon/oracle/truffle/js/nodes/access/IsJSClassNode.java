
package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.access.IsJSClassNodeGen;
import com.oracle.truffle.js.nodes.unary.JSUnaryNode;
import com.oracle.truffle.js.runtime.builtins.JSClass;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.util.Set;

public abstract class IsJSClassNode
extends JSUnaryNode {
    protected static final int MAX_SHAPE_COUNT = 1;
    private final JSClass jsclass;

    protected IsJSClassNode(JSClass jsclass, JavaScriptNode operand) {
        super(operand);
        this.jsclass = jsclass;
    }

    public abstract boolean executeBoolean(Object var1);

    @Specialization(guards={"cachedShape.check(object)"}, limit="MAX_SHAPE_COUNT")
    protected static boolean doIsInstanceShape(JSDynamicObject object, @Cached(value="object.getShape()") Shape cachedShape, @Cached(value="doIsInstance(object)") boolean cachedResult) {
        return cachedResult && cachedShape.check(object);
    }

    @Specialization(replaces={"doIsInstanceShape"})
    protected boolean doIsInstanceObject(JSDynamicObject object) {
        return this.jsclass.isInstance(object);
    }

    @Specialization(replaces={"doIsInstanceObject"})
    protected boolean doIsInstance(Object object) {
        return this.jsclass.isInstance(object);
    }

    public static IsJSClassNode create(JSClass clazz) {
        return IsJSClassNode.create(clazz, null);
    }

    public static IsJSClassNode create(JSClass clazz, JavaScriptNode operand) {
        return IsJSClassNodeGen.create(clazz, operand);
    }

    @Override
    protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
        return IsJSClassNode.create(this.jsclass, IsJSClassNode.cloneUninitialized(this.getOperand(), materializedTags));
    }

    @Override
    public boolean isResultAlwaysOfType(Class<?> clazz) {
        return clazz == Boolean.TYPE;
    }
}

