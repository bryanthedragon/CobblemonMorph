
package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.access.HasPropertyCacheNode;
import com.oracle.truffle.js.nodes.access.PropertyGetNode;
import com.oracle.truffle.js.nodes.cast.JSToBooleanNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Symbol;
import com.oracle.truffle.js.runtime.builtins.JSFunction;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.Undefined;
import java.util.Set;

public final class WithTargetNode
extends JavaScriptNode {
    @Node.Child
    private JavaScriptNode withVariable;
    @Node.Child
    private HasPropertyCacheNode withObjectHasProperty;
    @Node.Child
    private HasPropertyCacheNode globalObjectHasProperty;
    @Node.Child
    private PropertyGetNode withObjectGetUnscopables;
    @Node.Child
    private PropertyGetNode unscopablesGetProperty;
    @Node.Child
    private JSToBooleanNode toBoolean;
    private final JSContext context;

    private WithTargetNode(JSContext context, TruffleString propertyName, JavaScriptNode withVariable) {
        this.withVariable = withVariable;
        this.context = context;
        this.withObjectHasProperty = HasPropertyCacheNode.create(propertyName, context);
        this.globalObjectHasProperty = HasPropertyCacheNode.create(propertyName, context);
        this.withObjectGetUnscopables = PropertyGetNode.create(Symbol.SYMBOL_UNSCOPABLES, false, context);
        this.unscopablesGetProperty = PropertyGetNode.create(propertyName, false, context);
        this.toBoolean = JSToBooleanNode.create();
    }

    public static JavaScriptNode create(JSContext context, TruffleString propertyName, JavaScriptNode withVariable) {
        return new WithTargetNode(context, propertyName, withVariable);
    }

    @Override
    public Object execute(VirtualFrame frame) {
        Object target = this.withVariable.execute(frame);
        if (this.withObjectHasProperty.hasProperty(target) && this.isPropertyScopable(target)) {
            return target;
        }
        if (this.context.isOptionNashornCompatibilityMode() && this.hasNoSuchProperty(target, false) && !this.globalObjectHasProperty.hasProperty(this.getRealm().getGlobalObject())) {
            return target;
        }
        return Undefined.instance;
    }

    private boolean isPropertyScopable(Object target) {
        if (this.context.getEcmaScriptVersion() >= 6) {
            if (JSDynamicObject.isJSDynamicObject(target)) {
                boolean blocked;
                Object unscopables = this.withObjectGetUnscopables.getValue(target);
                if (JSRuntime.isObject(unscopables) && (blocked = this.toBoolean.executeBoolean(this.unscopablesGetProperty.getValue(unscopables)))) {
                    return false;
                }
            } else {
                return true;
            }
        }
        return true;
    }

    private boolean hasNoSuchProperty(Object thisTruffleObj, boolean isMethod) {
        if (JSRuntime.isObject(thisTruffleObj)) {
            JSDynamicObject thisObj = (JSDynamicObject)thisTruffleObj;
            if (!isMethod && !this.context.getNoSuchPropertyUnusedAssumption().isValid() && JSObject.hasOwnProperty(thisObj, JSObject.NO_SUCH_PROPERTY_NAME) || isMethod && !this.context.getNoSuchMethodUnusedAssumption().isValid() && JSObject.hasOwnProperty(thisObj, JSObject.NO_SUCH_METHOD_NAME)) {
                return WithTargetNode.hasNoSuchPropertyImpl(thisObj, isMethod);
            }
        }
        return false;
    }

    private static boolean hasNoSuchPropertyImpl(JSDynamicObject thisObj, boolean isMethod) {
        assert (JSRuntime.isObject(thisObj));
        Object function = JSObject.get(thisObj, isMethod ? JSObject.NO_SUCH_METHOD_NAME : JSObject.NO_SUCH_PROPERTY_NAME);
        return JSFunction.isJSFunction(function);
    }

    @Override
    protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
        return new WithTargetNode(this.context, (TruffleString)this.withObjectHasProperty.getKey(), WithTargetNode.cloneUninitialized(this.withVariable, materializedTags));
    }
}

