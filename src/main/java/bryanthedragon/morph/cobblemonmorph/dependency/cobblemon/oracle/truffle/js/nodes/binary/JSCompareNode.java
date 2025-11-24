
package com.oracle.truffle.js.nodes.binary;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.binary.JSBinaryNode;
import com.oracle.truffle.js.nodes.cast.JSStringToNumberNode;

public abstract class JSCompareNode
extends JSBinaryNode {
    @Node.Child
    private JSStringToNumberNode stringToNumberNode;

    protected JSCompareNode(JavaScriptNode left, JavaScriptNode right) {
        super(left, right);
    }

    @Override
    public final Object execute(VirtualFrame frame) {
        return this.executeBoolean(frame);
    }

    @Override
    public abstract boolean executeBoolean(VirtualFrame var1);

    protected double stringToDouble(TruffleString value2) {
        if (this.stringToNumberNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.stringToNumberNode = this.insert(JSStringToNumberNode.create());
        }
        return this.stringToNumberNode.executeString(value2);
    }

    @Override
    public final boolean isResultAlwaysOfType(Class<?> clazz) {
        return clazz == Boolean.TYPE;
    }
}

