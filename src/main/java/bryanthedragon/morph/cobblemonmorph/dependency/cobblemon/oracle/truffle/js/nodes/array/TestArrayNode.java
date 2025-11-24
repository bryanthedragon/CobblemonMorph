
package com.oracle.truffle.js.nodes.array;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.array.TestArrayNodeGen;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.array.ScriptArray;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;

public abstract class TestArrayNode
extends JavaScriptBaseNode {
    protected static final int MAX_TYPE_COUNT = 4;
    protected final Test test;

    protected TestArrayNode(Test test) {
        this.test = test;
    }

    protected static ScriptArray getArrayType(JSDynamicObject target) {
        return JSObject.getArray(target);
    }

    protected static TestArrayNode create(Test test) {
        return TestArrayNodeGen.create(test);
    }

    public static TestArrayNode createHasHoles() {
        return TestArrayNode.create(Test.HasHoles);
    }

    public static TestArrayNode createIsSealed() {
        return TestArrayNode.create(Test.IsSealed);
    }

    public abstract boolean executeBoolean(JSDynamicObject var1);

    @Specialization(guards={"arrayType.isInstance(getArrayType(target))"}, limit="MAX_TYPE_COUNT")
    protected final boolean doCached(JSDynamicObject target, @Cached(value="getArrayType(target)") ScriptArray arrayType) {
        if (this.test == Test.HasHoles) {
            return arrayType.hasHoles(target);
        }
        if (this.test == Test.IsSealed) {
            return arrayType.isSealed();
        }
        throw Errors.shouldNotReachHere();
    }

    @Specialization(replaces={"doCached"})
    protected final boolean doUncached(JSDynamicObject target) {
        ScriptArray arrayType = TestArrayNode.getArrayType(target);
        return this.doCached(target, arrayType);
    }

    protected static enum Test {
        HasHoles,
        IsSealed;

    }
}

