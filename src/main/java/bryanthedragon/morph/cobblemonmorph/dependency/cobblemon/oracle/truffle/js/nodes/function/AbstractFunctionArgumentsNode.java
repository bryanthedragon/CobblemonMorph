
package com.oracle.truffle.js.nodes.function;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import java.util.Set;

public abstract class AbstractFunctionArgumentsNode
extends JavaScriptBaseNode {
    public abstract int getCount(VirtualFrame var1);

    public abstract Object[] executeFillObjectArray(VirtualFrame var1, Object[] var2, int var3);

    public abstract void materializeInstrumentableArguments();

    protected abstract AbstractFunctionArgumentsNode copyUninitialized(Set<Class<? extends Tag>> var1);

    public static <T extends AbstractFunctionArgumentsNode> T cloneUninitialized(T node, Set<Class<? extends Tag>> materializedTags) {
        return (T)(node == null ? null : node.copyUninitialized(materializedTags));
    }
}

