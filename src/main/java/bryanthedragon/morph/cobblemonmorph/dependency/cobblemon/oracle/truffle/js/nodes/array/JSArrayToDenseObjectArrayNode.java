
package com.oracle.truffle.js.nodes.array;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.access.ReadElementNode;
import com.oracle.truffle.js.nodes.array.JSArrayNextElementIndexNode;
import com.oracle.truffle.js.nodes.array.JSArrayToDenseObjectArrayNodeGen;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.array.ScriptArray;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.util.SimpleArrayList;
import java.util.Objects;

public abstract class JSArrayToDenseObjectArrayNode
extends JavaScriptBaseNode {
    protected final JSContext context;

    protected JSArrayToDenseObjectArrayNode(JSContext context) {
        this.context = Objects.requireNonNull(context);
    }

    public static JSArrayToDenseObjectArrayNode create(JSContext context) {
        return JSArrayToDenseObjectArrayNodeGen.create(context);
    }

    public abstract Object[] executeObjectArray(JSDynamicObject var1, ScriptArray var2, long var3);

    @Specialization(guards={"cachedArrayType.isInstance(arrayType)", "!cachedArrayType.isHolesType()", "!cachedArrayType.hasHoles(array)", "cachedArrayType.firstElementIndex(array)==0"}, limit="5")
    protected Object[] fromDenseArray(JSDynamicObject array, ScriptArray arrayType, long length, @Cached(value="arrayType") ScriptArray cachedArrayType, @Cached(value="create(context)") ReadElementNode readNode) {
        assert (JSRuntime.longIsRepresentableAsInt(length));
        int iLen = (int)length;
        Object[] arr = new Object[iLen];
        for (int index = 0; index < iLen; ++index) {
            Object value2;
            arr[index] = value2 = readNode.executeWithTargetAndIndex((Object)array, index);
        }
        return arr;
    }

    @Specialization(guards={"cachedArrayType.isInstance(arrayType)", "cachedArrayType.isHolesType() || cachedArrayType.hasHoles(array)"}, limit="5")
    protected Object[] fromSparseArray(JSDynamicObject array, ScriptArray arrayType, long length, @Cached(value="arrayType") ScriptArray cachedArrayType, @Cached(value="create(context)") JSArrayNextElementIndexNode nextElementIndexNode, @Cached BranchProfile growProfile) {
        long pos = cachedArrayType.firstElementIndex(array);
        SimpleArrayList<Object> list = new SimpleArrayList<Object>();
        while (pos <= cachedArrayType.lastElementIndex(array)) {
            assert (cachedArrayType.hasElement(array, pos));
            list.add(cachedArrayType.getElement(array, pos), growProfile);
            pos = nextElementIndexNode.executeLong(array, pos, length);
        }
        return list.toArray(new Object[list.size()]);
    }

    @Specialization(replaces={"fromDenseArray", "fromSparseArray"})
    protected Object[] doUncached(JSDynamicObject array, ScriptArray arrayType, long length, @Cached(value="create(context)") JSArrayNextElementIndexNode nextElementIndexNode, @Cached(value="create(context)") ReadElementNode readNode, @Cached BranchProfile growProfile) {
        if (arrayType.isHolesType() || arrayType.hasHoles(array) || arrayType.firstElementIndex(array) != 0L) {
            return this.fromSparseArray(array, arrayType, length, arrayType, nextElementIndexNode, growProfile);
        }
        return this.fromDenseArray(array, arrayType, length, arrayType, readNode);
    }
}

