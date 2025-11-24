
package com.oracle.truffle.js.nodes.interop;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.interop.ExportValueNode;

public abstract class ExportArgumentsNode
extends JavaScriptBaseNode {
    private static final int MAX_FIXED = 250;

    public abstract Object[] export(Object[] var1);

    public static ExportArgumentsNode create(int expectedLength) {
        if (expectedLength >= 0 && expectedLength <= 250) {
            final class FixedLength
            extends ExportArgumentsNode {
                @Node.Children
                private final ExportValueNode[] exportNodes;

                FixedLength(int userArgumentCount) {
                    ExportValueNode[] exportNodeArray = new ExportValueNode[userArgumentCount];
                    for (int i = 0; i < exportNodeArray.length; ++i) {
                        exportNodeArray[i] = ExportValueNode.create();
                    }
                    this.exportNodes = exportNodeArray;
                }

                @Override
                @ExplodeLoop
                public Object[] export(Object[] extractedUserArguments) {
                    if (extractedUserArguments.length == this.exportNodes.length) {
                        for (int i = 0; i < this.exportNodes.length; ++i) {
                            extractedUserArguments[i] = this.exportNodes[i].execute(extractedUserArguments[i]);
                        }
                        return extractedUserArguments;
                    }
                    CompilerDirectives.transferToInterpreterAndInvalidate();
                    final class VariableLength
                    extends ExportArgumentsNode {
                        @Node.Child
                        private ExportValueNode exportNode = ExportValueNode.create();

                        VariableLength() {
                        }

                        @Override
                        public Object[] export(Object[] extractedUserArguments) {
                            for (int i = 0; i < extractedUserArguments.length; ++i) {
                                extractedUserArguments[i] = this.exportNode.execute(extractedUserArguments[i]);
                            }
                            return extractedUserArguments;
                        }
                    }
                    return this.replace(new VariableLength()).export(extractedUserArguments);
                }
            }
            return new FixedLength(expectedLength);
        }
        return new VariableLength();
    }
}

