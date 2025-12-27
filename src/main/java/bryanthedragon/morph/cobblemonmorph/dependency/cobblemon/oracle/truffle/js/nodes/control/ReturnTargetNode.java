package com.oracle.truffle.js.nodes.control;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.js.nodes.JSTypesGen;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import java.util.Set;

public class ReturnTargetNode extends JavaScriptNode {
   @Node.Child
   protected JavaScriptNode body;

   protected ReturnTargetNode(JavaScriptNode child) {
      this.body = child;
   }

   public static ReturnTargetNode create(JavaScriptNode child) {
      return new ReturnTargetNode(child);
   }

   public static ReturnTargetNode.FrameReturnTargetNode createFrameReturnTarget(JavaScriptNode body, JavaScriptNode returnValue) {
      return new ReturnTargetNode.FrameReturnTargetNode(body, returnValue);
   }

   @Override
   public Object execute(VirtualFrame frame) {
      try {
         return this.body.execute(frame);
      } catch (ReturnException var3) {
         return var3.getResult();
      }
   }

   @Override
   public int executeInt(VirtualFrame frame) throws UnexpectedResultException {
      try {
         return this.body.executeInt(frame);
      } catch (ReturnException var3) {
         return JSTypesGen.expectInteger(var3.getResult());
      }
   }

   @Override
   public double executeDouble(VirtualFrame frame) throws UnexpectedResultException {
      try {
         return this.body.executeDouble(frame);
      } catch (ReturnException var3) {
         return JSTypesGen.expectDouble(var3.getResult());
      }
   }

   @Override
   public boolean executeBoolean(VirtualFrame frame) throws UnexpectedResultException {
      try {
         return this.body.executeBoolean(frame);
      } catch (ReturnException var3) {
         return JSTypesGen.expectBoolean(var3.getResult());
      }
   }

   public final JavaScriptNode getBody() {
      return this.body;
   }

   @Override
   protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
      return new ReturnTargetNode(cloneUninitialized(this.body, materializedTags));
   }

   public static class FrameReturnTargetNode extends ReturnTargetNode {
      @Node.Child
      private JavaScriptNode returnValue;

      protected FrameReturnTargetNode(JavaScriptNode body, JavaScriptNode returnValue) {
         super(body);
         this.returnValue = returnValue;
      }

      @Override
      public Object execute(VirtualFrame frame) {
         try {
            return this.body.execute(frame);
         } catch (ReturnException var3) {
            return this.returnValue.execute(frame);
         }
      }

      @Override
      public int executeInt(VirtualFrame frame) throws UnexpectedResultException {
         try {
            return this.body.executeInt(frame);
         } catch (ReturnException var3) {
            return this.returnValue.executeInt(frame);
         }
      }

      @Override
      public double executeDouble(VirtualFrame frame) throws UnexpectedResultException {
         try {
            return this.body.executeDouble(frame);
         } catch (ReturnException var3) {
            return this.returnValue.executeDouble(frame);
         }
      }

      @Override
      public boolean executeBoolean(VirtualFrame frame) throws UnexpectedResultException {
         try {
            return this.body.executeBoolean(frame);
         } catch (ReturnException var3) {
            return this.returnValue.executeBoolean(frame);
         }
      }

      @Override
      protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
         return new ReturnTargetNode.FrameReturnTargetNode(
            cloneUninitialized(this.body, materializedTags), cloneUninitialized(this.returnValue, materializedTags)
         );
      }
   }
}
