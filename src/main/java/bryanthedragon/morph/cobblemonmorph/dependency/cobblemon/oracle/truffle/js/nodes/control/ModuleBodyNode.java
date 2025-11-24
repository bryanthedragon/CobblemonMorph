
package com.oracle.truffle.js.nodes.control;

import com.oracle.truffle.api.frame.MaterializedFrame;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.control.YieldException;
import com.oracle.truffle.js.runtime.JSArguments;
import com.oracle.truffle.js.runtime.JSFrameUtil;
import com.oracle.truffle.js.runtime.objects.JSModuleRecord;
import com.oracle.truffle.js.runtime.objects.Undefined;
import java.util.Set;

public final class ModuleBodyNode
extends JavaScriptNode {
    @Node.Child
    private JavaScriptNode moduleBodyNode;

    private ModuleBodyNode(JavaScriptNode body) {
        this.moduleBodyNode = body;
    }

    public static JavaScriptNode create(JavaScriptNode body) {
        return new ModuleBodyNode(body);
    }

    @Override
    public Object execute(VirtualFrame frame) {
        JSModuleRecord moduleRecord = (JSModuleRecord)JSArguments.getUserArgument(frame.getArguments(), 0);
        MaterializedFrame moduleFrame = moduleRecord.getEnvironment() != null ? JSFrameUtil.castMaterializedFrame(moduleRecord.getEnvironment()) : frame.materialize();
        try {
            return this.moduleBodyNode.execute(moduleFrame);
        }
        catch (YieldException e) {
            assert (e.isYield());
            moduleRecord.setEnvironment(moduleFrame);
            return Undefined.instance;
        }
    }

    @Override
    protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
        return ModuleBodyNode.create(ModuleBodyNode.cloneUninitialized(this.moduleBodyNode, materializedTags));
    }
}

