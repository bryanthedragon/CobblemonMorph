
package com.oracle.truffle.js.builtins.helper;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Fallback;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.js.builtins.helper.ListGetNodeGen;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.runtime.Boundaries;
import com.oracle.truffle.js.runtime.util.UnmodifiableArrayList;
import com.oracle.truffle.js.runtime.util.UnmodifiablePropertyKeyList;
import java.util.ArrayList;
import java.util.List;

public abstract class ListGetNode
extends JavaScriptBaseNode {
    protected ListGetNode() {
    }

    public static ListGetNode create() {
        return ListGetNodeGen.create();
    }

    public abstract Object execute(Object var1, int var2);

    @Specialization
    static Object unmodifiableArrayList(UnmodifiableArrayList<?> list, int index) {
        return list.get(index);
    }

    @Specialization
    static Object unmodifiablePropertyKeyList(UnmodifiablePropertyKeyList<?> list, int index) {
        return list.get(index);
    }

    @CompilerDirectives.TruffleBoundary(allowInlining=true)
    @Specialization
    static Object arrayList(ArrayList<?> list, int index) {
        return list.get(index);
    }

    @Fallback
    static Object list(Object list, int index) {
        return Boundaries.listGet((List)list, index);
    }
}

