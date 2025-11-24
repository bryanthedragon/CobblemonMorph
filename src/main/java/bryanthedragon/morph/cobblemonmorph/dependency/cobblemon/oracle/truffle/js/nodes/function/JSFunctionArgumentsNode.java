
package com.oracle.truffle.js.nodes.function;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.function.AbstractFunctionArgumentsNode;
import com.oracle.truffle.js.nodes.function.JSFunctionOneArgumentNode;
import com.oracle.truffle.js.nodes.function.JSFunctionZeroArgumentsNode;
import com.oracle.truffle.js.nodes.function.SpreadArgumentNode;
import com.oracle.truffle.js.nodes.function.SpreadFunctionArgumentsNode;
import com.oracle.truffle.js.nodes.instrumentation.JSInputGeneratingNodeWrapper;
import com.oracle.truffle.js.runtime.JSContext;
import java.util.Set;

public class JSFunctionArgumentsNode
extends AbstractFunctionArgumentsNode {
    @Node.Children
    protected final JavaScriptNode[] args;

    public static AbstractFunctionArgumentsNode create(JSContext context, JavaScriptNode[] args) {
        assert ((long)args.length <= context.getFunctionArgumentsLimit());
        for (JavaScriptNode arg : args) {
            if (!(arg instanceof SpreadArgumentNode)) continue;
            return new SpreadFunctionArgumentsNode(args);
        }
        if (args.length == 0) {
            return new JSFunctionZeroArgumentsNode();
        }
        if (args.length == 1) {
            return JSFunctionOneArgumentNode.create(args[0]);
        }
        return new JSFunctionArgumentsNode(args);
    }

    protected JSFunctionArgumentsNode(JavaScriptNode[] args) {
        this.args = args;
    }

    @Override
    public int getCount(VirtualFrame frame) {
        return this.args.length;
    }

    @Override
    @ExplodeLoop
    public Object[] executeFillObjectArray(VirtualFrame frame, Object[] arguments, int delta) {
        for (int i = 0; i < this.args.length; ++i) {
            arguments[i + delta] = this.args[i].execute(frame);
        }
        return arguments;
    }

    @Override
    protected AbstractFunctionArgumentsNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
        return new JSFunctionArgumentsNode(JavaScriptNode.cloneUninitialized(this.args, materializedTags));
    }

    @Override
    public void materializeInstrumentableArguments() {
        for (int i = 0; i < this.args.length; ++i) {
            if (this.args[i] instanceof SpreadArgumentNode || this.args[i].isInstrumentable()) continue;
            this.args[i] = this.insert(JSInputGeneratingNodeWrapper.create(this.args[i]));
        }
    }
}

