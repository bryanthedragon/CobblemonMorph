
package com.oracle.truffle.js.nodes.cast;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.access.GetIteratorBaseNode;
import com.oracle.truffle.js.nodes.access.IteratorCloseNode;
import com.oracle.truffle.js.nodes.access.IteratorStepNode;
import com.oracle.truffle.js.nodes.access.IteratorValueNode;
import com.oracle.truffle.js.nodes.cast.JSStringListFromIterableNodeGen;
import com.oracle.truffle.js.runtime.Boundaries;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.objects.IteratorRecord;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class JSStringListFromIterableNode
extends JavaScriptBaseNode {
    protected final JSContext context;

    protected JSStringListFromIterableNode(JSContext context) {
        this.context = context;
    }

    public abstract List<String> executeIterable(Object var1);

    public static JSStringListFromIterableNode create(JSContext context) {
        return JSStringListFromIterableNodeGen.create(context);
    }

    @Specialization
    @CompilerDirectives.TruffleBoundary
    protected static List<String> stringToList(TruffleString s) {
        int[] codePoints = Strings.toJavaString(s).codePoints().toArray();
        int length = codePoints.length;
        ArrayList<String> result = new ArrayList<String>(length);
        for (int i = 0; i < length; ++i) {
            result.add(new String(codePoints, i, 1));
        }
        return result;
    }

    @Specialization(guards={"!isUndefined(iterable)", "!isString(iterable)"})
    protected static List<String> toArray(Object iterable, @Cached GetIteratorBaseNode getIteratorNode, @Cached IteratorStepNode iteratorStepNode, @Cached IteratorValueNode iteratorValueNode, @Cached(value="create(context)") IteratorCloseNode iteratorCloseNode) {
        IteratorRecord iteratorRecord = getIteratorNode.execute(iterable);
        ArrayList<String> list = new ArrayList<String>();
        Object next = true;
        while (!JSStringListFromIterableNode.isFalse(next)) {
            next = iteratorStepNode.execute(iteratorRecord);
            if (JSStringListFromIterableNode.isFalse(next)) continue;
            Object nextValue = iteratorValueNode.execute(next);
            if (!Strings.isTString(nextValue)) {
                iteratorCloseNode.executeAbrupt(iteratorRecord.getIterator());
                throw Errors.createTypeError("nonString value encountered!");
            }
            Boundaries.listAdd(list, Strings.toJavaString(JSRuntime.toStringIsString(nextValue)));
        }
        return list;
    }

    private static boolean isFalse(Object o) {
        return o == Boolean.FALSE;
    }

    @Specialization(guards={"isUndefined(value)"})
    protected List<String> doUndefined(Object value2) {
        return Collections.emptyList();
    }
}

