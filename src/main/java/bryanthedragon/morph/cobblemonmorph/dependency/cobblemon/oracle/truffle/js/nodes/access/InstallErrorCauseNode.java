
package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.access.CreateDataPropertyNode;
import com.oracle.truffle.js.nodes.access.HasPropertyCacheNode;
import com.oracle.truffle.js.nodes.access.IsObjectNode;
import com.oracle.truffle.js.nodes.access.PropertyGetNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;

public class InstallErrorCauseNode
extends JavaScriptBaseNode {
    @Node.Child
    private CreateDataPropertyNode createNonEnumerableDataPropertyNode;
    @Node.Child
    private HasPropertyCacheNode hasPropertyNode;
    @Node.Child
    private PropertyGetNode getPropertyNode;
    @Node.Child
    private IsObjectNode isObjectNode;

    public InstallErrorCauseNode(JSContext context) {
        this.createNonEnumerableDataPropertyNode = CreateDataPropertyNode.createNonEnumerable(context, Strings.CAUSE);
        this.hasPropertyNode = HasPropertyCacheNode.create(Strings.CAUSE, context);
        this.getPropertyNode = PropertyGetNode.create(Strings.CAUSE, context);
        this.isObjectNode = IsObjectNode.create();
    }

    public void executeVoid(JSDynamicObject error, Object options) {
        assert (JSObject.isJSObject(error));
        if (this.isObjectNode.executeBoolean(options) && this.hasPropertyNode.hasProperty(options)) {
            Object cause = this.getPropertyNode.getValue(options);
            this.createNonEnumerableDataPropertyNode.executeVoid(error, cause);
        }
    }
}

