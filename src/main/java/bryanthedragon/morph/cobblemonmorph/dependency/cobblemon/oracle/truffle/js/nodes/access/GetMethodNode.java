
package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.access.PropertyGetNode;
import com.oracle.truffle.js.nodes.unary.IsCallableNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.objects.Null;
import com.oracle.truffle.js.runtime.objects.Undefined;

public class GetMethodNode
extends JavaScriptBaseNode {
    @Node.Child
    private PropertyGetNode cacheNode;
    @Node.Child
    private IsCallableNode isCallableNode;
    private final ConditionProfile undefinedOrNull = ConditionProfile.createBinaryProfile();
    private final BranchProfile notCallableBranch = BranchProfile.create();

    protected GetMethodNode(JSContext context, Object propertyKey) {
        this.cacheNode = PropertyGetNode.create(propertyKey, false, context);
        this.isCallableNode = IsCallableNode.create();
    }

    public static GetMethodNode create(JSContext ctx, Object key) {
        return new GetMethodNode(ctx, key);
    }

    public Object executeWithTarget(Object target) {
        Object method = this.cacheNode.getValue(target);
        if (this.isCallableNode.executeBoolean(method)) {
            return method;
        }
        if (this.undefinedOrNull.profile(method == Undefined.instance || method == Null.instance)) {
            return Undefined.instance;
        }
        this.notCallableBranch.enter();
        throw Errors.createTypeErrorNotAFunction(method, this);
    }
}

