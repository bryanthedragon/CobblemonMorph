
package com.oracle.truffle.js.nodes.arguments;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.arguments.AccessIndexedArgumentNode;
import com.oracle.truffle.js.runtime.JSArguments;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.array.dyn.ConstantObjectArray;
import com.oracle.truffle.js.runtime.builtins.JSArray;
import java.util.Set;

public class AccessRestArgumentsNode
extends AccessIndexedArgumentNode {
    private final JSContext context;

    AccessRestArgumentsNode(JSContext context, int paramIndex) {
        super(paramIndex);
        this.context = context;
    }

    public static AccessRestArgumentsNode create(JSContext context, int paramIndex) {
        return new AccessRestArgumentsNode(context, paramIndex);
    }

    @Override
    public Object execute(VirtualFrame frame) {
        Object[] jsArguments = frame.getArguments();
        int restLength = JSArguments.getUserArgumentCount(jsArguments) - this.index;
        JSRealm realm = this.getRealm();
        if (this.profile(restLength > 0)) {
            return JSArray.create(this.context, realm, ConstantObjectArray.createConstantObjectArray(), JSArguments.extractUserArguments(jsArguments, this.index), restLength);
        }
        return JSArray.createEmptyZeroLength(this.context, realm);
    }

    @Override
    protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
        return new AccessRestArgumentsNode(this.context, this.index);
    }
}

