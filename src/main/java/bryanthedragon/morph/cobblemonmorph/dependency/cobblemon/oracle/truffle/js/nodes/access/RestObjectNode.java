
package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Executed;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.access.CopyDataPropertiesNode;
import com.oracle.truffle.js.nodes.access.RestObjectNodeGen;
import com.oracle.truffle.js.nodes.access.RestObjectWithExcludedNodeGen;
import com.oracle.truffle.js.nodes.cast.JSToObjectNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.util.Set;

public abstract class RestObjectNode
extends JavaScriptNode {
    @Node.Child
    @Executed
    protected JavaScriptNode targetNode;
    @Node.Child
    @Executed
    protected JavaScriptNode sourceNode;
    @Node.Child
    private CopyDataPropertiesNode copyDataPropertiesNode;
    protected final JSContext context;

    protected RestObjectNode(JSContext context, JavaScriptNode targetNode, JavaScriptNode sourceNode) {
        this.context = context;
        this.targetNode = targetNode;
        this.sourceNode = sourceNode;
        this.copyDataPropertiesNode = CopyDataPropertiesNode.create(context);
    }

    public static JavaScriptNode create(JSContext context, JavaScriptNode targetNode, JavaScriptNode sourceNode, JavaScriptNode excludedNode) {
        if (excludedNode == null) {
            return RestObjectNodeGen.create(context, targetNode, sourceNode);
        }
        return RestObjectWithExcludedNodeGen.create(context, targetNode, sourceNode, excludedNode);
    }

    @Specialization(guards={"isNullOrUndefined(source)"})
    protected static JSDynamicObject doNullOrUndefined(JSDynamicObject restObj, Object source) {
        return restObj;
    }

    @Specialization(guards={"isJSObject(source)"})
    protected final JSDynamicObject copyDataProperties(JSDynamicObject restObj, JSDynamicObject source) {
        this.copyDataPropertiesNode.execute(restObj, source);
        return restObj;
    }

    @Specialization(guards={"!isJSDynamicObject(source)"})
    protected final Object doOther(JSDynamicObject restObj, Object source, @Cached(value="createToObjectNoCheck(context)") JSToObjectNode toObjectNode) {
        Object from = toObjectNode.execute(source);
        this.copyDataPropertiesNode.execute(restObj, from);
        return restObj;
    }

    @Override
    protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
        return RestObjectNodeGen.create(this.context, RestObjectNode.cloneUninitialized(this.targetNode, materializedTags), RestObjectNode.cloneUninitialized(this.sourceNode, materializedTags));
    }
}

