
package com.oracle.truffle.js.nodes.intl;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.access.PropertyGetNode;
import com.oracle.truffle.js.nodes.cast.JSToStringNode;
import com.oracle.truffle.js.nodes.intl.GetStringOptionNodeGen;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.objects.Undefined;
import java.util.Arrays;
import java.util.List;

public abstract class GetStringOptionNode
extends JavaScriptBaseNode {
    private final List<String> validValues;
    private final String fallback;
    @Node.Child
    PropertyGetNode propertyGetNode;
    @Node.Child
    JSToStringNode toStringNode = JSToStringNode.create();

    protected GetStringOptionNode(JSContext context, TruffleString property, String[] values, String fallback) {
        this.validValues = values != null ? Arrays.asList(values) : null;
        this.fallback = fallback;
        this.propertyGetNode = PropertyGetNode.create(property, false, context);
    }

    public abstract String executeValue(Object var1);

    public static GetStringOptionNode create(JSContext context, TruffleString property, String[] values, String fallback) {
        return GetStringOptionNodeGen.create(context, property, values, fallback);
    }

    protected String makeFinalSelection(String value2) {
        if (this.validValues == null) {
            return value2;
        }
        this.ensureSelectedValueIsValid(value2);
        return value2;
    }

    @CompilerDirectives.TruffleBoundary
    private void ensureSelectedValueIsValid(String value2) {
        if (!this.validValues.contains(value2)) {
            throw Errors.createRangeError(String.format("invalid option %s found where only %s is allowed", value2, this.validValues.toString()));
        }
    }

    @Specialization
    public String getOption(Object options) {
        Object propertyValue = this.propertyGetNode.getValue(options);
        if (propertyValue == Undefined.instance) {
            return this.fallback;
        }
        return this.makeFinalSelection(Strings.toJavaString(this.toOptionType(propertyValue)));
    }

    protected TruffleString toOptionType(Object propertyValue) {
        return this.toStringNode.executeString(propertyValue);
    }
}

