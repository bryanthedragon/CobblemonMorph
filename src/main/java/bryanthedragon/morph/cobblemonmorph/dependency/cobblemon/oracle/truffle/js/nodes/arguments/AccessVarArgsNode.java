
package com.oracle.truffle.js.nodes.arguments;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.arguments.AccessIndexedArgumentNode;
import com.oracle.truffle.js.runtime.JSArguments;
import java.util.Set;

public class AccessVarArgsNode
extends AccessIndexedArgumentNode {
    private static final int MAX_UNROLL = 250;
    private static final int UNINITIALIZED = -2;
    private static final int UNSTABLE = -1;
    @CompilerDirectives.CompilationFinal
    private int userArgumentCount = -2;

    AccessVarArgsNode(int paramIndex) {
        super(paramIndex);
    }

    public static AccessVarArgsNode create(int paramIndex) {
        return new AccessVarArgsNode(paramIndex);
    }

    public final Object[] execute(VirtualFrame frame) {
        Object[] arguments = frame.getArguments();
        int currentUserArgumentCount = JSArguments.getUserArgumentCount(arguments);
        if (this.profile(this.index >= currentUserArgumentCount)) {
            return JSArguments.EMPTY_ARGUMENTS_ARRAY;
        }
        int constantUserArgumentCount = this.userArgumentCount;
        if (constantUserArgumentCount == -2) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            constantUserArgumentCount = currentUserArgumentCount <= 250 ? currentUserArgumentCount : -1;
            this.userArgumentCount = constantUserArgumentCount;
        }
        if (constantUserArgumentCount == -1) {
            return this.getArgumentsArrayWithoutExplosion(arguments, currentUserArgumentCount);
        }
        if (constantUserArgumentCount != currentUserArgumentCount) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            constantUserArgumentCount = currentUserArgumentCount;
            this.userArgumentCount = -1;
        }
        return this.getArgumentsArray(arguments, constantUserArgumentCount);
    }

    @ExplodeLoop
    private Object[] getArgumentsArray(Object[] arguments, int constantUserArgumentCount) {
        int length = constantUserArgumentCount - this.index;
        Object[] varArgs = new Object[length];
        for (int i = 0; i < length; ++i) {
            varArgs[i] = JSArguments.getUserArgument(arguments, i + this.index);
        }
        return varArgs;
    }

    private Object[] getArgumentsArrayWithoutExplosion(Object[] arguments, int currentUserArgumentCount) {
        int length = currentUserArgumentCount - this.index;
        Object[] varArgs = new Object[length];
        for (int i = 0; i < length; ++i) {
            varArgs[i] = JSArguments.getUserArgument(arguments, i + this.index);
        }
        return varArgs;
    }

    @Override
    protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
        return new AccessVarArgsNode(this.index);
    }
}

