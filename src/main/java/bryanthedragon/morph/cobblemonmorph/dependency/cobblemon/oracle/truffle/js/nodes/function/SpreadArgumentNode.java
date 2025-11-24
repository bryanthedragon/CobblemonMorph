
package com.oracle.truffle.js.nodes.function;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.access.GetIteratorNode;
import com.oracle.truffle.js.nodes.access.IteratorGetNextValueNode;
import com.oracle.truffle.js.nodes.access.JSConstantNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.objects.IteratorRecord;
import com.oracle.truffle.js.runtime.util.SimpleArrayList;
import java.util.Set;

public final class SpreadArgumentNode
extends JavaScriptNode {
    @Node.Child
    private GetIteratorNode getIteratorNode;
    @Node.Child
    private IteratorGetNextValueNode iteratorStepNode;
    private final BranchProfile errorBranch = BranchProfile.create();
    private final BranchProfile listGrowProfile = BranchProfile.create();
    private final JSContext context;

    private SpreadArgumentNode(JSContext context, GetIteratorNode getIteratorNode) {
        this.context = context;
        this.getIteratorNode = getIteratorNode;
        this.iteratorStepNode = IteratorGetNextValueNode.create(context, null, JSConstantNode.create(null), false);
    }

    @Override
    public boolean isInstrumentable() {
        return false;
    }

    public static SpreadArgumentNode create(JSContext context, GetIteratorNode getIteratorNode) {
        return new SpreadArgumentNode(context, getIteratorNode);
    }

    public Object[] execute(VirtualFrame frame) {
        SimpleArrayList<Object> argList = new SimpleArrayList<Object>();
        this.executeToList(frame, argList, this.listGrowProfile);
        return argList.toArray();
    }

    public void executeToList(VirtualFrame frame, SimpleArrayList<Object> argList, BranchProfile growProfile) {
        Object nextArg;
        IteratorRecord iteratorRecord = this.getIteratorNode.execute(frame);
        while ((nextArg = this.iteratorStepNode.execute(frame, iteratorRecord)) != null) {
            if ((long)argList.size() >= this.context.getFunctionArgumentsLimit()) {
                this.errorBranch.enter();
                throw Errors.createRangeError("spreaded function argument count exceeds limit");
            }
            argList.add(nextArg, growProfile);
        }
    }

    @Override
    protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
        SpreadArgumentNode copy = (SpreadArgumentNode)this.copy();
        copy.getIteratorNode = SpreadArgumentNode.cloneUninitialized(this.getIteratorNode, materializedTags);
        copy.iteratorStepNode = SpreadArgumentNode.cloneUninitialized(this.iteratorStepNode, materializedTags);
        return copy;
    }
}

