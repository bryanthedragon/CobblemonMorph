
package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.access.CreateDataPropertyNodeGen;
import com.oracle.truffle.js.nodes.access.IsJSObjectNode;
import com.oracle.truffle.js.nodes.access.PropertySetNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.objects.JSAttributes;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;

public abstract class CreateDataPropertyNode
extends JavaScriptBaseNode {
    protected final JSContext context;
    protected final Object key;
    protected final boolean enumerable;
    @Node.Child
    protected IsJSObjectNode isObject;

    protected CreateDataPropertyNode(JSContext context, Object key, boolean enumerable) {
        assert (JSRuntime.isPropertyKey(key));
        this.context = context;
        this.key = key;
        this.isObject = IsJSObjectNode.create();
        this.enumerable = enumerable;
    }

    public static CreateDataPropertyNode create(JSContext context, Object key) {
        return CreateDataPropertyNodeGen.create(context, key, true);
    }

    public static CreateDataPropertyNode createNonEnumerable(JSContext context, Object key) {
        return CreateDataPropertyNodeGen.create(context, key, false);
    }

    public abstract void executeVoid(Object var1, Object var2);

    @Specialization(guards={"context.getPropertyCacheLimit() > 0", "isObject.executeBoolean(object)"})
    protected static void doCached(Object object, Object value2, @Cached(value="makeDefinePropertyCache()") PropertySetNode propertyCache) {
        propertyCache.setValue(object, value2);
    }

    @Specialization(guards={"context.getPropertyCacheLimit() == 0", "isJSObject(object)"})
    protected final void doUncached(JSDynamicObject object, Object value2) {
        if (this.enumerable) {
            JSRuntime.createDataPropertyOrThrow(object, this.key, value2);
        } else {
            JSRuntime.createNonEnumerableDataPropertyOrThrow(object, this.key, value2);
        }
    }

    @Specialization(guards={"!isJSObject(object)"})
    protected final void doNonObject(Object object, Object value2) {
        throw Errors.createTypeErrorNotAnObject(object, this);
    }

    protected final PropertySetNode makeDefinePropertyCache() {
        if (this.enumerable) {
            return PropertySetNode.createImpl(this.key, false, this.context, true, true, JSAttributes.getDefault());
        }
        return PropertySetNode.createImpl(this.key, false, this.context, true, true, JSAttributes.getDefaultNotEnumerable());
    }
}

