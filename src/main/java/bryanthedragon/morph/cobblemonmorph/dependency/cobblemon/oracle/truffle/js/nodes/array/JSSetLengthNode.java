
package com.oracle.truffle.js.nodes.array;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.access.WritePropertyNode;
import com.oracle.truffle.js.nodes.array.ArrayLengthNode;
import com.oracle.truffle.js.nodes.array.JSSetLengthNodeGen;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.builtins.JSArray;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;

public abstract class JSSetLengthNode
extends JavaScriptBaseNode {
    private final JSContext context;
    protected final boolean isStrict;

    protected JSSetLengthNode(JSContext context, boolean isStrict) {
        this.context = context;
        this.isStrict = isStrict;
    }

    public static JSSetLengthNode create(JSContext context, boolean strict) {
        return JSSetLengthNodeGen.create(context, strict);
    }

    public abstract Object execute(Object var1, Object var2);

    protected final WritePropertyNode createWritePropertyNode() {
        return WritePropertyNode.create(null, JSArray.LENGTH, null, this.context, this.isStrict);
    }

    protected static boolean isArray(Object object) {
        return JSArray.isJSFastArray(object);
    }

    @Specialization(guards={"isArray(object)"})
    protected static int setArrayLength(JSDynamicObject object, int length, @Cached(value="create(isStrict)") ArrayLengthNode.ArrayLengthWriteNode arrayLengthWriteNode) {
        arrayLengthWriteNode.executeVoid(object, length);
        return length;
    }

    @Specialization
    protected static int setIntLength(JSDynamicObject object, int length, @Cached(value="createWritePropertyNode()") WritePropertyNode setLengthProperty) {
        setLengthProperty.executeIntWithValue(object, length);
        return length;
    }

    @Specialization(replaces={"setIntLength"})
    protected static Object setLength(JSDynamicObject object, Object length, @Cached(value="createWritePropertyNode()") WritePropertyNode setLengthProperty) {
        setLengthProperty.executeWithValue(object, length);
        return length;
    }

    @Specialization(guards={"!isJSDynamicObject(object)"})
    protected static Object setLengthForeign(Object object, Object length) {
        return length;
    }
}

