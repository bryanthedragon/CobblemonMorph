package com.oracle.truffle.js.nodes.interop;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;

public abstract class ExportArgumentsNode extends JavaScriptBaseNode {
   private static final int MAX_FIXED = 250;

   public abstract Object[] export(Object[] extractedUserArguments);

   public static ExportArgumentsNode create(int expectedLength) {
      final class FixedLength extends ExportArgumentsNode {
         @Node.Children
         private final ExportValueNode[] exportNodes;

         FixedLength(int userArgumentCount) {
            ExportValueNode[] exportNodeArray = new ExportValueNode[userArgumentCount];

            for (int i = 0; i < exportNodeArray.length; i++) {
               exportNodeArray[i] = ExportValueNode.create();
            }

            this.exportNodes = exportNodeArray;
         }

         @ExplodeLoop
         @Override
         public Object[] export(Object[] extractedUserArguments) {
            if (extractedUserArguments.length != this.exportNodes.length) {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return this.<VariableLength>replace(new VariableLength()).export(extractedUserArguments);
            } else {
               for (int i = 0; i < this.exportNodes.length; i++) {
                  extractedUserArguments[i] = this.exportNodes[i].execute(extractedUserArguments[i]);
               }

               return extractedUserArguments;
            }
         }
      }


      final class VariableLength extends ExportArgumentsNode {
         @Node.Child
         private ExportValueNode exportNode = ExportValueNode.create();

         @Override
         public Object[] export(Object[] extractedUserArguments) {
            for (int i = 0; i < extractedUserArguments.length; i++) {
               extractedUserArguments[i] = this.exportNode.execute(extractedUserArguments[i]);
            }

            return extractedUserArguments;
         }
      }

      return (ExportArgumentsNode)(expectedLength >= 0 && expectedLength <= 250 ? new FixedLength(expectedLength) : new VariableLength());
   }
}
