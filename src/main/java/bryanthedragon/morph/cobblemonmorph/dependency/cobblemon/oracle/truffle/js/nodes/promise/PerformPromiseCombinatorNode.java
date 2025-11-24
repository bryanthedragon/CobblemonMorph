
package com.oracle.truffle.js.nodes.promise;

import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.access.IteratorStepNode;
import com.oracle.truffle.js.nodes.access.IteratorValueNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.objects.IteratorRecord;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.PromiseCapabilityRecord;

public abstract class PerformPromiseCombinatorNode
extends JavaScriptBaseNode {
    protected final JSContext context;
    @Node.Child
    private IteratorStepNode iteratorStep;
    @Node.Child
    private IteratorValueNode iteratorValue;

    protected PerformPromiseCombinatorNode(JSContext context) {
        this.context = context;
        this.iteratorStep = IteratorStepNode.create();
        this.iteratorValue = IteratorValueNode.create();
    }

    public abstract JSDynamicObject execute(IteratorRecord var1, JSDynamicObject var2, PromiseCapabilityRecord var3, Object var4);

    protected final Object iteratorStepOrSetDone(IteratorRecord iteratorRecord) {
        Object next;
        try {
            next = this.iteratorStep.execute(iteratorRecord);
        }
        catch (Throwable error) {
            iteratorRecord.setDone(true);
            throw error;
        }
        return next;
    }

    protected final Object iteratorValueOrSetDone(IteratorRecord iteratorRecord, Object next) {
        Object nextValue;
        try {
            nextValue = this.iteratorValue.execute(next);
        }
        catch (Throwable error) {
            iteratorRecord.setDone(true);
            throw error;
        }
        return nextValue;
    }

    protected static final class BoxedInt {
        int value;

        BoxedInt(int value2) {
            this.value = value2;
        }
    }
}

