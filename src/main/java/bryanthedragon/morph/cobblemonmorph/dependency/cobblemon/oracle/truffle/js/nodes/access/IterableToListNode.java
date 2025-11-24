
package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.GenerateUncached;
import com.oracle.truffle.api.dsl.ImportStatic;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.access.IterableToListNodeGen;
import com.oracle.truffle.js.nodes.access.IteratorStepNode;
import com.oracle.truffle.js.nodes.access.IteratorValueNode;
import com.oracle.truffle.js.runtime.interop.JSInteropUtil;
import com.oracle.truffle.js.runtime.objects.IteratorRecord;
import com.oracle.truffle.js.runtime.util.SimpleArrayList;

@GenerateUncached
@ImportStatic(value={JSInteropUtil.class})
public abstract class IterableToListNode
extends JavaScriptBaseNode {
    protected IterableToListNode() {
    }

    public abstract SimpleArrayList<Object> execute(IteratorRecord var1);

    public static IterableToListNode create() {
        return IterableToListNodeGen.create();
    }

    public static IterableToListNode getUncached() {
        return IterableToListNodeGen.getUncached();
    }

    @Specialization
    protected static SimpleArrayList<Object> iterableToList(IteratorRecord iterator, @Cached IteratorStepNode iteratorStepNode, @Cached IteratorValueNode getIteratorValueNode, @Cached BranchProfile growProfile) {
        Object next;
        SimpleArrayList<Object> values = new SimpleArrayList<Object>();
        while ((next = iteratorStepNode.execute(iterator)) != Boolean.FALSE) {
            Object nextValue = getIteratorValueNode.execute(next);
            values.add(nextValue, growProfile);
        }
        return values;
    }
}

