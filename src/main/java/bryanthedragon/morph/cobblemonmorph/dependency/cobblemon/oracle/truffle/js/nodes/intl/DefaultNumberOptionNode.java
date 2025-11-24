
package com.oracle.truffle.js.nodes.intl;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.cast.JSToNumberNode;
import com.oracle.truffle.js.nodes.intl.DefaultNumberOptionNodeGen;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSException;
import com.oracle.truffle.js.runtime.JSRuntime;

public abstract class DefaultNumberOptionNode
extends JavaScriptBaseNode {
    protected DefaultNumberOptionNode() {
    }

    public abstract int executeInt(Object var1, int var2, int var3, int var4);

    public static DefaultNumberOptionNode create() {
        return DefaultNumberOptionNodeGen.create();
    }

    @Specialization(guards={"!isUndefined(value)"})
    public int getOption(Object value2, int minimum, int maximum, int fallback, @Cached(value="create()") JSToNumberNode toNumberNode, @Cached(value="create()") BranchProfile errorBranch) {
        Number numValue = toNumberNode.executeNumber(value2);
        double doubleValue = JSRuntime.doubleValue(numValue);
        if (Double.isNaN(doubleValue) || doubleValue < (double)minimum || (double)maximum < doubleValue) {
            errorBranch.enter();
            throw this.createRangeError(doubleValue, minimum, maximum);
        }
        return (int)doubleValue;
    }

    @CompilerDirectives.TruffleBoundary
    private JSException createRangeError(double value2, int minimum, int maximum) throws JSException {
        return Errors.createRangeErrorFormat("invalid value %f found where only values between %d and %d are allowed", this, value2, minimum, maximum);
    }

    @Specialization(guards={"isUndefined(value)"})
    public int getOptionFromUndefined(Object value2, int minimum, int maximum, int fallback) {
        return fallback;
    }
}

