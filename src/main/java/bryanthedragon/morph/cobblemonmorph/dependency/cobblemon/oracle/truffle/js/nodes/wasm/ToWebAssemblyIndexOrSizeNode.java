
package com.oracle.truffle.js.nodes.wasm;

import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.cast.JSToNumberNode;
import com.oracle.truffle.js.nodes.wasm.ToWebAssemblyIndexOrSizeNodeGen;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSRuntime;

public abstract class ToWebAssemblyIndexOrSizeNode
extends JavaScriptBaseNode {
    private final String errorMessagePrefix;
    private final BranchProfile errorBranch;
    @Node.Child
    JSToNumberNode toNumberNode;

    protected ToWebAssemblyIndexOrSizeNode(String errorMessagePrefix) {
        this.errorMessagePrefix = errorMessagePrefix;
        this.errorBranch = BranchProfile.create();
        this.toNumberNode = JSToNumberNode.create();
    }

    public static ToWebAssemblyIndexOrSizeNode create(String errorMessagePrefix) {
        return ToWebAssemblyIndexOrSizeNodeGen.create(errorMessagePrefix);
    }

    public abstract double executeDouble(Object var1);

    public int executeInt(Object value2) {
        double valueDouble = this.executeDouble(value2);
        if (valueDouble > 2.147483647E9) {
            this.errorBranch.enter();
            throw Errors.createTypeErrorFormat("%s must be in the int range", this.errorMessagePrefix);
        }
        return (int)valueDouble;
    }

    @Specialization
    protected double convert(Object value2) {
        Number valueNumber = this.toNumberNode.executeNumber(value2);
        double valueDouble = JSRuntime.doubleValue(valueNumber);
        if (Double.isNaN(valueDouble)) {
            this.errorBranch.enter();
            throw Errors.createTypeErrorFormat("%s must be convertible to a valid number", this.errorMessagePrefix);
        }
        if (valueDouble < 0.0) {
            this.errorBranch.enter();
            throw Errors.createTypeErrorFormat("%s must be non-negative", this.errorMessagePrefix);
        }
        return valueDouble;
    }
}

