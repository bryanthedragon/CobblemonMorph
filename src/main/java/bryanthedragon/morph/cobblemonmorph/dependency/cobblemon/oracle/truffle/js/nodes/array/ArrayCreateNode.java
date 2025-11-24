
package com.oracle.truffle.js.nodes.array;

import com.oracle.truffle.api.dsl.ImportStatic;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.array.ArrayCreateNodeGen;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.builtins.JSArray;
import com.oracle.truffle.js.runtime.builtins.JSArrayObject;

@ImportStatic(value={JSRuntime.class, Integer.class})
public abstract class ArrayCreateNode
extends JavaScriptBaseNode {
    private final JSContext context;

    protected ArrayCreateNode(JSContext context) {
        this.context = context;
    }

    public static ArrayCreateNode create(JSContext context) {
        return ArrayCreateNodeGen.create(context);
    }

    @Specialization(guards={"isValidArrayLength(length)", "length <= MAX_VALUE"})
    protected JSArrayObject doDefault(long length) {
        return JSArray.createEmptyChecked(this.context, this.getRealm(), length);
    }

    @Specialization(guards={"isValidArrayLength(length)", "length > MAX_VALUE"})
    protected JSArrayObject doLargeLength(long length) {
        return JSArray.createSparseArray(this.context, this.getRealm(), length);
    }

    @Specialization(guards={"!isValidArrayLength(length)"})
    protected JSArrayObject doInvalidLength(long length) {
        throw Errors.createRangeErrorInvalidArrayLength();
    }

    public abstract JSArrayObject execute(long var1);
}

