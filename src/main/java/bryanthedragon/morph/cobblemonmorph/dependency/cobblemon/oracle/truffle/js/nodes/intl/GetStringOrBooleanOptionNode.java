
package com.oracle.truffle.js.nodes.intl;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.access.PropertyGetNode;
import com.oracle.truffle.js.nodes.cast.JSToBooleanNode;
import com.oracle.truffle.js.nodes.cast.JSToStringNode;
import com.oracle.truffle.js.nodes.intl.GetStringOrBooleanOptionNodeGen;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.objects.Undefined;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public abstract class GetStringOrBooleanOptionNode
extends JavaScriptBaseNode {
    private final Set<String> values;
    private final Object trueValue;
    private final Object falsyValue;
    private final Object fallback;
    @Node.Child
    PropertyGetNode propertyGetNode;
    @Node.Child
    JSToStringNode toStringNode;
    @Node.Child
    JSToBooleanNode toBooleanNode;

    protected GetStringOrBooleanOptionNode(JSContext context, TruffleString property, String[] values, Object trueValue, Object falsyValue, Object fallback) {
        this.values = new HashSet<String>(Arrays.asList(values));
        this.trueValue = trueValue;
        this.falsyValue = falsyValue;
        this.fallback = fallback;
        this.propertyGetNode = PropertyGetNode.create(property, false, context);
        this.toStringNode = JSToStringNode.create();
        this.toBooleanNode = JSToBooleanNode.create();
    }

    public abstract Object executeValue(Object var1);

    public static GetStringOrBooleanOptionNode create(JSContext context, TruffleString property, String[] values, Object trueValue, Object falsyValue, Object fallback) {
        return GetStringOrBooleanOptionNodeGen.create(context, property, values, trueValue, falsyValue, fallback);
    }

    @Specialization
    public Object getOption(Object options) {
        Object value2 = this.propertyGetNode.getValue(options);
        if (value2 == Undefined.instance) {
            return this.fallback;
        }
        if (value2 == Boolean.TRUE) {
            return this.trueValue;
        }
        boolean valueBoolean = this.toBooleanNode.executeBoolean(value2);
        if (this.propertyGetNode.getContext().getEcmaScriptVersion() < 14) {
            return valueBoolean ? this.trueValue : this.falsyValue;
        }
        if (!valueBoolean) {
            return this.falsyValue;
        }
        String stringValue = Strings.toJavaString(this.toStringNode.executeString(value2));
        return this.isValid(stringValue) ? stringValue : this.fallback;
    }

    @CompilerDirectives.TruffleBoundary
    private boolean isValid(String value2) {
        return this.values.contains(value2);
    }
}

