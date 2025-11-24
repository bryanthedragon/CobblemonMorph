
package com.oracle.truffle.js.nodes.control;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.access.CreateIterResultObjectNode;
import com.oracle.truffle.js.nodes.access.JSWriteFrameSlotNode;
import com.oracle.truffle.js.nodes.control.AbstractYieldNode;
import com.oracle.truffle.js.nodes.control.ResumableNode;
import com.oracle.truffle.js.nodes.control.ReturnNode;
import com.oracle.truffle.js.nodes.control.YieldResultNode;
import com.oracle.truffle.js.nodes.control.YieldStarNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.objects.Completion;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.util.Set;

public class YieldNode
extends AbstractYieldNode
implements ResumableNode.WithIntState {
    @Node.Child
    private CreateIterResultObjectNode createIterResultObjectNode;

    protected YieldNode(JSContext context, int stateSlot, JavaScriptNode expression, JavaScriptNode yieldValue, ReturnNode returnNode, YieldResultNode yieldResultNode) {
        super(context, stateSlot, expression, yieldValue, returnNode, yieldResultNode);
        this.createIterResultObjectNode = CreateIterResultObjectNode.create(context);
    }

    public static JavaScriptNode createYield(JSContext context, int stateSlot, JavaScriptNode expression, JavaScriptNode yieldValue, ReturnNode returnNode, JSWriteFrameSlotNode writeYieldResultNode) {
        return new YieldNode(context, stateSlot, expression, yieldValue, returnNode, writeYieldResultNode == null ? new YieldResultNode.ExceptionYieldResultNode() : new YieldResultNode.FrameYieldResultNode(writeYieldResultNode));
    }

    public static JavaScriptNode createYieldStar(JSContext context, int stateSlot, JavaScriptNode expression, JavaScriptNode yieldValue, ReturnNode returnNode, JSWriteFrameSlotNode writeYieldResultNode) {
        return new YieldStarNode(context, stateSlot, expression, yieldValue, returnNode, writeYieldResultNode == null ? new YieldResultNode.ExceptionYieldResultNode() : new YieldResultNode.FrameYieldResultNode(writeYieldResultNode));
    }

    @Override
    public Object execute(VirtualFrame frame) {
        int index = this.getStateAsInt(frame, this.stateSlot);
        if (index == 0) {
            Object value2 = this.expression.execute(frame);
            JSDynamicObject iterNextObj = this.createIterResultObjectNode.execute(frame, value2, false);
            this.setStateAsInt(frame, this.stateSlot, 1);
            return this.generatorYield(frame, iterNextObj);
        }
        assert (index == 1);
        this.setStateAsInt(frame, this.stateSlot, 0);
        Object value3 = this.yieldValue.execute(frame);
        if (value3 instanceof Completion) {
            Completion completion = (Completion)value3;
            value3 = completion.getValue();
            if (this.returnOrExceptionProfile.profile(completion.isThrow())) {
                return this.throwValue(value3);
            }
            assert (completion.isReturn());
            return this.returnValue(frame, value3);
        }
        return value3;
    }

    @Override
    protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
        return new YieldNode(this.context, this.stateSlot, YieldNode.cloneUninitialized(this.expression, materializedTags), YieldNode.cloneUninitialized(this.yieldValue, materializedTags), YieldNode.cloneUninitialized(this.returnNode, materializedTags), this.generatorYieldNode.cloneUninitialized());
    }
}

