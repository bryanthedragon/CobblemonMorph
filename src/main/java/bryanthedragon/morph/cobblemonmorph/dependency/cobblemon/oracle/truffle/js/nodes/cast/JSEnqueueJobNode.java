
package com.oracle.truffle.js.nodes.cast;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.cast.JSEnqueueJobNodeGen;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.builtins.JSFunctionObject;
import com.oracle.truffle.js.runtime.objects.Undefined;
import java.util.Set;

@NodeChild(value="function")
public abstract class JSEnqueueJobNode
extends JavaScriptNode {
    private final JSContext context;

    public JSEnqueueJobNode(JSContext context) {
        this.context = context;
    }

    public static JSEnqueueJobNode create(JSContext context, JavaScriptNode argument) {
        return JSEnqueueJobNodeGen.create(context, argument);
    }

    @CompilerDirectives.TruffleBoundary
    @Specialization
    protected Object doOther(JSFunctionObject function) {
        this.context.promiseEnqueueJob(this.getRealm(), function);
        return Undefined.instance;
    }

    abstract JavaScriptNode getFunction();

    @Override
    protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
        return JSEnqueueJobNodeGen.create(this.context, JSEnqueueJobNode.cloneUninitialized(this.getFunction(), materializedTags));
    }
}

