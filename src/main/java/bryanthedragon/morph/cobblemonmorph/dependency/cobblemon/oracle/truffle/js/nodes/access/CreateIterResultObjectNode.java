
package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.access.CreateDataPropertyNode;
import com.oracle.truffle.js.nodes.access.CreateIterResultObjectNodeGen;
import com.oracle.truffle.js.nodes.access.CreateObjectNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;

public abstract class CreateIterResultObjectNode
extends JavaScriptBaseNode {
    @Node.Child
    private CreateObjectNode createObjectNode;
    @Node.Child
    private CreateDataPropertyNode createValuePropertyNode;
    @Node.Child
    private CreateDataPropertyNode createDonePropertyNode;

    protected CreateIterResultObjectNode(JSContext context) {
        this.createObjectNode = CreateObjectNode.create(context);
        this.createValuePropertyNode = CreateDataPropertyNode.create(context, Strings.VALUE);
        this.createDonePropertyNode = CreateDataPropertyNode.create(context, Strings.DONE);
    }

    public static CreateIterResultObjectNode create(JSContext context) {
        return CreateIterResultObjectNodeGen.create(context);
    }

    @Specialization
    protected JSDynamicObject doCreateIterResultObject(VirtualFrame frame, Object value2, boolean done) {
        JSDynamicObject iterResult = this.createObjectNode.execute(frame);
        this.createValuePropertyNode.executeVoid(iterResult, value2);
        this.createDonePropertyNode.executeVoid(iterResult, done);
        return iterResult;
    }

    public abstract JSDynamicObject execute(VirtualFrame var1, Object var2, boolean var3);
}

