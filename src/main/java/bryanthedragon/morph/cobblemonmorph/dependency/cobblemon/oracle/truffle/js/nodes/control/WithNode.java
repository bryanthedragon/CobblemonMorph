package com.oracle.truffle.js.nodes.control;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import java.util.Set;

public final class WithNode extends StatementNode {
   @Node.Child
   private JavaScriptNode statement;
   @Node.Child
   private JavaScriptNode writeActiveObject;

   private WithNode(JavaScriptNode expression, JavaScriptNode statement) {
      this.writeActiveObject = expression;
      this.statement = statement;
   }

   public static WithNode create(JavaScriptNode expression, JavaScriptNode statement) {
      return new WithNode(expression, statement);
   }

   @Override
   public Object execute(VirtualFrame frame) {
      this.writeActiveObject.executeVoid(frame);
      return this.statement.execute(frame);
   }

   @Override
   protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
      return create(cloneUninitialized(this.writeActiveObject, materializedTags), cloneUninitialized(this.statement, materializedTags));
   }
}
