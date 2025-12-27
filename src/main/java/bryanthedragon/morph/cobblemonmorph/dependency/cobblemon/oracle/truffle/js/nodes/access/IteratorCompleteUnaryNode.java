package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.cast.JSToBooleanNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.Strings;
import java.util.Set;

public class IteratorCompleteUnaryNode extends JavaScriptNode {
   @Node.Child
   private PropertyGetNode getDoneNode;
   @Node.Child
   private JavaScriptNode iterResultNode;
   @Node.Child
   private JSToBooleanNode toBooleanNode;

   protected IteratorCompleteUnaryNode(JSContext context, JavaScriptNode iterResultNode) {
      this.iterResultNode = iterResultNode;
      this.getDoneNode = PropertyGetNode.create(Strings.DONE, false, context);
   }

   public static JavaScriptNode create(JSContext context, JavaScriptNode iterResultNode) {
      return new IteratorCompleteUnaryNode(context, iterResultNode);
   }

   @Override
   public Object execute(VirtualFrame frame) {
      return this.executeBoolean(frame);
   }

   @Override
   public boolean executeBoolean(VirtualFrame frame) {
      Object iterResult = this.iterResultNode.execute(frame);
      Object done = this.getDoneNode.getValue(iterResult);
      if (done instanceof Boolean) {
         return (Boolean)done;
      } else {
         if (this.toBooleanNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.toBooleanNode = this.insert(JSToBooleanNode.create());
         }

         return this.toBooleanNode.executeBoolean(done);
      }
   }

   @Override
   public final boolean isResultAlwaysOfType(Class<?> clazz) {
      return clazz == boolean.class;
   }

   @Override
   protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
      return create(this.getDoneNode.getContext(), cloneUninitialized(this.iterResultNode, materializedTags));
   }
}
