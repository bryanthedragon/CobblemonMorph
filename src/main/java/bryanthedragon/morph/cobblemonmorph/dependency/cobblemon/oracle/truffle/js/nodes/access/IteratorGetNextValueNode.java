
package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.dsl.Executed;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.access.IsJSObjectNode;
import com.oracle.truffle.js.nodes.access.IteratorGetNextValueNodeGen;
import com.oracle.truffle.js.nodes.access.PropertyGetNode;
import com.oracle.truffle.js.nodes.cast.JSToBooleanNode;
import com.oracle.truffle.js.nodes.function.JSFunctionCallNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSArguments;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.objects.IteratorRecord;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.Undefined;
import java.util.Set;

public abstract class IteratorGetNextValueNode
extends JavaScriptNode {
    @Node.Child
    @Executed
    JavaScriptNode iteratorNode;
    @Node.Child
    private PropertyGetNode getValueNode;
    @Node.Child
    private PropertyGetNode getDoneNode;
    @Node.Child
    private JSFunctionCallNode methodCallNode;
    @Node.Child
    private IsJSObjectNode isObjectNode;
    @Node.Child
    private JavaScriptNode doneResultNode;
    @Node.Child
    private JSToBooleanNode toBooleanNode;
    private final boolean setDone;
    private final boolean readValue;

    protected IteratorGetNextValueNode(JSContext context, JavaScriptNode iteratorNode, JavaScriptNode doneNode, boolean setDone, boolean readValue) {
        this.iteratorNode = iteratorNode;
        this.getValueNode = PropertyGetNode.create(Strings.VALUE, false, context);
        this.getDoneNode = PropertyGetNode.create(Strings.DONE, false, context);
        this.methodCallNode = JSFunctionCallNode.createCall();
        this.isObjectNode = IsJSObjectNode.create();
        this.toBooleanNode = JSToBooleanNode.create();
        this.doneResultNode = doneNode;
        this.setDone = setDone;
        this.readValue = readValue;
    }

    public static IteratorGetNextValueNode create(JSContext context, JavaScriptNode iterator, JavaScriptNode doneNode, boolean setDone) {
        return IteratorGetNextValueNode.create(context, iterator, doneNode, setDone, true);
    }

    public static IteratorGetNextValueNode create(JSContext context, JavaScriptNode iterator, JavaScriptNode doneNode, boolean setDone, boolean readValue) {
        return IteratorGetNextValueNodeGen.create(context, iterator, doneNode, setDone, readValue);
    }

    private Object iteratorNext(IteratorRecord iteratorRecord) {
        Object next = iteratorRecord.getNextMethod();
        JSDynamicObject iterator = iteratorRecord.getIterator();
        Object result = this.methodCallNode.executeCall(JSArguments.createZeroArg(iterator, next));
        if (!this.isObjectNode.executeBoolean(result)) {
            throw Errors.createTypeErrorIterResultNotAnObject(result, this);
        }
        return result;
    }

    @Specialization
    protected Object iteratorStepAndGetValue(VirtualFrame frame, IteratorRecord iteratorRecord) {
        try {
            Object result = this.iteratorNext(iteratorRecord);
            boolean done = this.toBooleanNode.executeBoolean(this.getDoneNode.getValue(result));
            if (!done) {
                return this.readValue ? this.getValueNode.getValue(result) : Undefined.instance;
            }
            if (this.setDone) {
                iteratorRecord.setDone(true);
            }
            return this.doneResultNode.execute(frame);
        }
        catch (Exception ex) {
            if (this.setDone) {
                iteratorRecord.setDone(true);
            }
            throw ex;
        }
    }

    public abstract Object execute(VirtualFrame var1, IteratorRecord var2);

    @Override
    protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
        return IteratorGetNextValueNode.create(this.getValueNode.getContext(), IteratorGetNextValueNode.cloneUninitialized(this.iteratorNode, materializedTags), IteratorGetNextValueNode.cloneUninitialized(this.doneResultNode, materializedTags), this.setDone, this.readValue);
    }
}

