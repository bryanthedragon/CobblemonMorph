package com.oracle.truffle.js.nodes.control;

import com.oracle.truffle.api.exception.AbstractTruffleException;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.ControlFlowException;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.objects.Undefined;
import java.util.Set;

@NodeInfo(shortName = "try-finally")
public class TryFinallyNode extends StatementNode implements ResumableNode.WithObjectState {
   @Node.Child
   private JavaScriptNode tryBlock;
   @Node.Child
   private JavaScriptNode finallyBlock;

   TryFinallyNode(JavaScriptNode tryBlock, JavaScriptNode finallyBlock) {
      this.tryBlock = tryBlock;
      this.finallyBlock = finallyBlock;
   }

   public static JavaScriptNode create(JavaScriptNode tryBlock, JavaScriptNode finallyBlock) {
      return new TryFinallyNode(tryBlock, finallyBlock);
   }

   @Override
   protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
      return create(cloneUninitialized(this.tryBlock, materializedTags), cloneUninitialized(this.finallyBlock, materializedTags));
   }

   @Override
   public Object execute(VirtualFrame frame) {
      Object result = null;

      Throwable throwable;
      try {
         result = this.tryBlock.execute(frame);
         throwable = null;
      } catch (ControlFlowException var5) {
         throwable = var5;
      } catch (AbstractTruffleException var6) {
         throwable = var6;
      } catch (StackOverflowError var7) {
         throwable = var7;
      }

      this.finallyBlock.executeVoid(frame);
      if (throwable != null) {
         throw JSRuntime.rethrow(throwable);
      } else {
         assert result != null;

         return result;
      }
   }

   @Override
   public void executeVoid(VirtualFrame frame) {
      Throwable throwable;
      try {
         this.tryBlock.executeVoid(frame);
         throwable = null;
      } catch (ControlFlowException var4) {
         throwable = var4;
      } catch (AbstractTruffleException var5) {
         throwable = var5;
      } catch (StackOverflowError var6) {
         throwable = var6;
      }

      this.finallyBlock.executeVoid(frame);
      if (throwable != null) {
         throw JSRuntime.rethrow(throwable);
      }
   }

   @Override
   public Object resume(VirtualFrame frame, int stateSlot) {
      Object result = EMPTY;
      Throwable throwable = null;
      Object state = this.getStateAndReset(frame, stateSlot);
      if (state == Undefined.instance) {
         try {
            result = this.tryBlock.execute(frame);
         } catch (YieldException var8) {
            throw var8;
         } catch (ControlFlowException var9) {
            throwable = var9;
         } catch (AbstractTruffleException var10) {
            throwable = var10;
         } catch (StackOverflowError var11) {
            throwable = var11;
         }
      } else if (state instanceof Throwable) {
         throwable = (Throwable)state;
      }

      try {
         this.finallyBlock.execute(frame);
      } catch (YieldException var7) {
         this.setState(frame, stateSlot, throwable);
         throw var7;
      }

      if (throwable != null) {
         throw JSRuntime.rethrow(throwable);
      } else {
         return result;
      }
   }
}
