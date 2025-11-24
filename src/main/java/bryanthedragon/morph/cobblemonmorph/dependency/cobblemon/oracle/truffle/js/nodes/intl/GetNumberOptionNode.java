
package com.oracle.truffle.js.nodes.intl;

import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.access.PropertyGetNode;
import com.oracle.truffle.js.nodes.intl.DefaultNumberOptionNode;
import com.oracle.truffle.js.nodes.intl.GetNumberOptionNodeGen;
import com.oracle.truffle.js.runtime.JSContext;

public abstract class GetNumberOptionNode
extends JavaScriptBaseNode {
    @Node.Child
    PropertyGetNode propertyGetNode;
    @Node.Child
    DefaultNumberOptionNode defaultNumberOptionNode;

    protected GetNumberOptionNode(JSContext context, TruffleString property) {
        this.propertyGetNode = PropertyGetNode.create(property, context);
        this.defaultNumberOptionNode = DefaultNumberOptionNode.create();
    }

    public abstract int executeInt(Object var1, int var2, int var3, int var4);

    public static GetNumberOptionNode create(JSContext context, TruffleString property) {
        return GetNumberOptionNodeGen.create(context, property);
    }

    @Specialization
    public int getOption(Object options, int minimum, int maximum, int fallback) {
        Object value2 = this.propertyGetNode.getValue(options);
        return this.defaultNumberOptionNode.executeInt(value2, minimum, maximum, fallback);
    }
}

