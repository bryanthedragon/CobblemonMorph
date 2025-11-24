
package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Executed;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.access.IteratorGetNextValueNode;
import com.oracle.truffle.js.nodes.access.IteratorToArrayNodeGen;
import com.oracle.truffle.js.nodes.access.JSConstantNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.builtins.JSArray;
import com.oracle.truffle.js.runtime.objects.IteratorRecord;
import com.oracle.truffle.js.runtime.util.SimpleArrayList;
import java.util.Set;

public abstract class IteratorToArrayNode
extends JavaScriptNode {
    private final JSContext context;
    @Node.Child
    @Executed
    JavaScriptNode iteratorNode;
    @Node.Child
    private IteratorGetNextValueNode iteratorStepNode;

    protected IteratorToArrayNode(JSContext context, JavaScriptNode iteratorNode, IteratorGetNextValueNode iteratorStepNode) {
        this.context = context;
        this.iteratorNode = iteratorNode;
        this.iteratorStepNode = iteratorStepNode;
    }

    public static IteratorToArrayNode create(JSContext context, JavaScriptNode iterator) {
        IteratorGetNextValueNode iteratorStep = IteratorGetNextValueNode.create(context, null, JSConstantNode.create(null), true);
        return IteratorToArrayNodeGen.create(context, iterator, iteratorStep);
    }

    @Specialization(guards={"!iteratorRecord.isDone()"})
    protected Object doIterator(VirtualFrame frame, IteratorRecord iteratorRecord, @Cached BranchProfile growProfile) {
        Object value2;
        SimpleArrayList<Object> elements2 = new SimpleArrayList<Object>();
        while ((value2 = this.iteratorStepNode.execute(frame, iteratorRecord)) != null) {
            elements2.add(value2, growProfile);
        }
        return JSArray.createZeroBasedObjectArray(this.context, this.getRealm(), elements2.toArray());
    }

    @Specialization(guards={"iteratorRecord.isDone()"})
    protected Object doDoneIterator(IteratorRecord iteratorRecord) {
        return JSArray.createEmptyZeroLength(this.context, this.getRealm());
    }

    public abstract Object execute(VirtualFrame var1, IteratorRecord var2);

    @Override
    protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
        return IteratorToArrayNodeGen.create(this.context, IteratorToArrayNode.cloneUninitialized(this.iteratorNode, materializedTags), IteratorToArrayNode.cloneUninitialized(this.iteratorStepNode, materializedTags));
    }
}

