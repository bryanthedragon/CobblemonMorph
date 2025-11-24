
package com.oracle.truffle.js.nodes.intl;

import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.access.PropertyGetNode;
import com.oracle.truffle.js.nodes.cast.JSToBooleanNode;
import com.oracle.truffle.js.nodes.intl.GetBooleanOptionNodeGen;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.objects.Undefined;

public abstract class GetBooleanOptionNode
extends JavaScriptBaseNode {
    private final Boolean fallback;
    @Node.Child
    PropertyGetNode propertyGetNode;
    @Node.Child
    JSToBooleanNode toBooleanNode = JSToBooleanNode.create();

    protected GetBooleanOptionNode(JSContext context, TruffleString property, Boolean fallback) {
        this.fallback = fallback;
        this.propertyGetNode = PropertyGetNode.create(property, false, context);
    }

    public abstract Boolean executeValue(Object var1);

    public static GetBooleanOptionNode create(JSContext context, TruffleString property, Boolean fallback) {
        return GetBooleanOptionNodeGen.create(context, property, fallback);
    }

    @Specialization
    public Boolean getOption(Object options) {
        Object propertyValue = this.propertyGetNode.getValue(options);
        if (propertyValue == Undefined.instance) {
            return this.fallback;
        }
        return this.toOptionType(propertyValue);
    }

    protected Boolean toOptionType(Object propertyValue) {
        return this.toBooleanNode.executeBoolean(propertyValue);
    }
}

