
package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Executed;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.access.CopyDataPropertiesNode;
import com.oracle.truffle.js.nodes.access.RestObjectWithExcludedNodeGen;
import com.oracle.truffle.js.nodes.cast.JSToObjectArrayNode;
import com.oracle.truffle.js.nodes.cast.JSToObjectNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.util.Set;

abstract class RestObjectWithExcludedNode
extends JavaScriptNode {
    @Node.Child
    @Executed
    protected JavaScriptNode targetNode;
    @Node.Child
    @Executed
    protected JavaScriptNode sourceNode;
    @Node.Child
    @Executed
    protected JavaScriptNode excludedNode;
    @Node.Child
    private CopyDataPropertiesNode copyDataPropertiesNode;
    protected final JSContext context;

    protected RestObjectWithExcludedNode(JSContext context, JavaScriptNode targetNode, JavaScriptNode sourceNode, JavaScriptNode excludedNode) {
        this.context = context;
        this.targetNode = targetNode;
        this.sourceNode = sourceNode;
        this.excludedNode = JSToObjectArrayNode.create(context, excludedNode);
        this.copyDataPropertiesNode = CopyDataPropertiesNode.create(context);
    }

    @Specialization(guards={"isNullOrUndefined(source)"})
    protected static JSDynamicObject doNullOrUndefined(JSDynamicObject restObj, Object source, Object[] excludedItems) {
        return restObj;
    }

    @Specialization(guards={"isJSObject(source)"})
    protected final JSDynamicObject copyDataProperties(JSDynamicObject restObj, JSDynamicObject source, Object[] excludedItems) {
        this.copyDataPropertiesNode.execute(restObj, source, excludedItems);
        return restObj;
    }

    @Specialization(guards={"!isJSDynamicObject(source)"})
    protected final Object doOther(JSDynamicObject restObj, Object source, Object[] excludedItems, @Cached(value="createToObjectNoCheck(context)") JSToObjectNode toObjectNode) {
        Object from = toObjectNode.execute(source);
        this.copyDataPropertiesNode.execute(restObj, from, excludedItems);
        return restObj;
    }

    @Override
    protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
        return RestObjectWithExcludedNodeGen.create(this.context, RestObjectWithExcludedNode.cloneUninitialized(this.targetNode, materializedTags), RestObjectWithExcludedNode.cloneUninitialized(this.sourceNode, materializedTags), RestObjectWithExcludedNode.cloneUninitialized(this.excludedNode, materializedTags));
    }
}

