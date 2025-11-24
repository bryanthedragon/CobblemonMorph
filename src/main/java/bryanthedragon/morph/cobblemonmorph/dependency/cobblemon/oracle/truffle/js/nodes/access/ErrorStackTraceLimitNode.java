
package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.object.DynamicObjectLibrary;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.access.ErrorStackTraceLimitNodeGen;
import com.oracle.truffle.js.nodes.cast.IsNumberNode;
import com.oracle.truffle.js.nodes.cast.JSToIntegerAsLongNode;
import com.oracle.truffle.js.runtime.JSErrorType;
import com.oracle.truffle.js.runtime.builtins.JSError;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObjectUtil;
import com.oracle.truffle.js.runtime.objects.JSProperty;
import com.oracle.truffle.js.runtime.objects.Undefined;

public abstract class ErrorStackTraceLimitNode
extends JavaScriptBaseNode {
    @Node.Child
    private DynamicObjectLibrary getStackTraceLimit = JSObjectUtil.createDispatched(JSError.STACK_TRACE_LIMIT_PROPERTY_NAME);
    @Node.Child
    private IsNumberNode isNumber = IsNumberNode.create();
    @Node.Child
    private JSToIntegerAsLongNode toInteger = JSToIntegerAsLongNode.create();

    protected ErrorStackTraceLimitNode() {
    }

    public static ErrorStackTraceLimitNode create() {
        return ErrorStackTraceLimitNodeGen.create();
    }

    @Specialization
    public int doInt() {
        Object value2;
        JSDynamicObject errorConstructor = this.getRealm().getErrorConstructor(JSErrorType.Error);
        if (JSProperty.isData(this.getStackTraceLimit.getPropertyFlagsOrDefault(errorConstructor, JSError.STACK_TRACE_LIMIT_PROPERTY_NAME, 8)) && this.isNumber.execute(value2 = this.getStackTraceLimit.getOrDefault(errorConstructor, JSError.STACK_TRACE_LIMIT_PROPERTY_NAME, Undefined.instance))) {
            long limit = this.toInteger.executeLong(value2);
            return (int)Math.max(0L, Math.min(limit, Integer.MAX_VALUE));
        }
        return 0;
    }

    public abstract int executeInt();
}

