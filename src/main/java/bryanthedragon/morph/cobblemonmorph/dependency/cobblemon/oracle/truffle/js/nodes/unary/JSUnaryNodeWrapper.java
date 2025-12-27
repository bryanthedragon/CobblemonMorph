package com.oracle.truffle.js.nodes.unary;

import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.InstrumentableNode;
import com.oracle.truffle.api.instrumentation.ProbeNode;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.js.runtime.SafeInteger;

@GeneratedBy(JSUnaryNode.class)
final class JSUnaryNodeWrapper extends JSUnaryNode implements InstrumentableNode.WrapperNode {
   @Node.Child
   private JSUnaryNode delegateNode;
   @Node.Child
   private ProbeNode probeNode;

   JSUnaryNodeWrapper(JSUnaryNode delegateNode, ProbeNode probeNode) {
      this.delegateNode = delegateNode;
      this.probeNode = probeNode;
   }

   public JSUnaryNode getDelegateNode() {
      return this.delegateNode;
   }

   @Override
   public ProbeNode getProbeNode() {
      return this.probeNode;
   }

   @Override
   public NodeCost getCost() {
      return NodeCost.NONE;
   }

   @Override
   public Object execute(VirtualFrame frame) {
      while (true) {
         boolean wasOnReturnExecuted = false;

         Object returnValue;
         try {
            this.probeNode.onEnter(frame);
            returnValue = this.delegateNode.execute(frame);
            wasOnReturnExecuted = true;
            this.probeNode.onReturnValue(frame, returnValue);
         } catch (Throwable var6) {
            Object result = this.probeNode.onReturnExceptionalOrUnwind(frame, var6, wasOnReturnExecuted);
            if (result == ProbeNode.UNWIND_ACTION_REENTER) {
               continue;
            }

            if (result == null) {
               throw var6;
            }

            returnValue = result;
         }

         return returnValue;
      }
   }

   @Override
   public boolean executeBoolean(VirtualFrame frame) throws UnexpectedResultException {
      while (true) {
         boolean wasOnReturnExecuted = false;

         boolean returnValue;
         try {
            try {
               this.probeNode.onEnter(frame);
               returnValue = this.delegateNode.executeBoolean(frame);
               wasOnReturnExecuted = true;
               this.probeNode.onReturnValue(frame, returnValue);
            } catch (UnexpectedResultException var6) {
               wasOnReturnExecuted = true;
               this.probeNode.onReturnValue(frame, var6.getResult());
               throw var6;
            }
         } catch (Throwable var7) {
            Object result = this.probeNode.onReturnExceptionalOrUnwind(frame, var7, wasOnReturnExecuted);
            if (result == ProbeNode.UNWIND_ACTION_REENTER) {
               continue;
            }

            if (!(result instanceof Boolean)) {
               if (result != null) {
                  throw new UnexpectedResultException(result);
               }

               throw var7;
            }

            returnValue = (Boolean)result;
         }

         return returnValue;
      }
   }

   @Override
   public double executeDouble(VirtualFrame frame) throws UnexpectedResultException {
      while (true) {
         boolean wasOnReturnExecuted = false;

         double returnValue;
         try {
            try {
               this.probeNode.onEnter(frame);
               returnValue = this.delegateNode.executeDouble(frame);
               wasOnReturnExecuted = true;
               this.probeNode.onReturnValue(frame, returnValue);
            } catch (UnexpectedResultException var7) {
               wasOnReturnExecuted = true;
               this.probeNode.onReturnValue(frame, var7.getResult());
               throw var7;
            }
         } catch (Throwable var8) {
            Object result = this.probeNode.onReturnExceptionalOrUnwind(frame, var8, wasOnReturnExecuted);
            if (result == ProbeNode.UNWIND_ACTION_REENTER) {
               continue;
            }

            if (!(result instanceof Double)) {
               if (result != null) {
                  throw new UnexpectedResultException(result);
               }

               throw var8;
            }

            returnValue = (Double)result;
         }

         return returnValue;
      }
   }

   @Override
   public int executeInt(VirtualFrame frame) throws UnexpectedResultException {
      while (true) {
         boolean wasOnReturnExecuted = false;

         int returnValue;
         try {
            try {
               this.probeNode.onEnter(frame);
               returnValue = this.delegateNode.executeInt(frame);
               wasOnReturnExecuted = true;
               this.probeNode.onReturnValue(frame, returnValue);
            } catch (UnexpectedResultException var6) {
               wasOnReturnExecuted = true;
               this.probeNode.onReturnValue(frame, var6.getResult());
               throw var6;
            }
         } catch (Throwable var7) {
            Object result = this.probeNode.onReturnExceptionalOrUnwind(frame, var7, wasOnReturnExecuted);
            if (result == ProbeNode.UNWIND_ACTION_REENTER) {
               continue;
            }

            if (!(result instanceof Integer)) {
               if (result != null) {
                  throw new UnexpectedResultException(result);
               }

               throw var7;
            }

            returnValue = (Integer)result;
         }

         return returnValue;
      }
   }

   @Override
   public long executeLong(VirtualFrame frame) throws UnexpectedResultException {
      while (true) {
         boolean wasOnReturnExecuted = false;

         long returnValue;
         try {
            try {
               this.probeNode.onEnter(frame);
               returnValue = this.delegateNode.executeLong(frame);
               wasOnReturnExecuted = true;
               this.probeNode.onReturnValue(frame, returnValue);
            } catch (UnexpectedResultException var7) {
               wasOnReturnExecuted = true;
               this.probeNode.onReturnValue(frame, var7.getResult());
               throw var7;
            }
         } catch (Throwable var8) {
            Object result = this.probeNode.onReturnExceptionalOrUnwind(frame, var8, wasOnReturnExecuted);
            if (result == ProbeNode.UNWIND_ACTION_REENTER) {
               continue;
            }

            if (!(result instanceof Long)) {
               if (result != null) {
                  throw new UnexpectedResultException(result);
               }

               throw var8;
            }

            returnValue = (Long)result;
         }

         return returnValue;
      }
   }

   @Override
   public SafeInteger executeSafeInteger(VirtualFrame frame) throws UnexpectedResultException {
      while (true) {
         boolean wasOnReturnExecuted = false;

         SafeInteger returnValue;
         try {
            try {
               this.probeNode.onEnter(frame);
               returnValue = this.delegateNode.executeSafeInteger(frame);
               wasOnReturnExecuted = true;
               this.probeNode.onReturnValue(frame, returnValue);
            } catch (UnexpectedResultException var6) {
               wasOnReturnExecuted = true;
               this.probeNode.onReturnValue(frame, var6.getResult());
               throw var6;
            }
         } catch (Throwable var7) {
            Object result = this.probeNode.onReturnExceptionalOrUnwind(frame, var7, wasOnReturnExecuted);
            if (result == ProbeNode.UNWIND_ACTION_REENTER) {
               continue;
            }

            if (!(result instanceof SafeInteger)) {
               if (result != null) {
                  throw new UnexpectedResultException(result);
               }

               throw var7;
            }

            returnValue = (SafeInteger)result;
         }

         return returnValue;
      }
   }

   @Override
   public void executeVoid(VirtualFrame frame) {
      while (true) {
         boolean wasOnReturnExecuted = false;

         try {
            this.probeNode.onEnter(frame);
            Object returnValue = this.delegateNode.execute(frame);
            wasOnReturnExecuted = true;
            this.probeNode.onReturnValue(frame, returnValue);
         } catch (Throwable var6) {
            Object result = this.probeNode.onReturnExceptionalOrUnwind(frame, var6, wasOnReturnExecuted);
            if (result == ProbeNode.UNWIND_ACTION_REENTER) {
               continue;
            }

            if (result == null) {
               throw var6;
            }
         }

         return;
      }
   }

   @Override
   public Object execute(VirtualFrame frame, Object operandValue) {
      while (true) {
         boolean wasOnReturnExecuted = false;

         Object returnValue;
         try {
            this.probeNode.onEnter(frame);
            returnValue = this.delegateNode.execute(frame, operandValue);
            wasOnReturnExecuted = true;
            this.probeNode.onReturnValue(frame, returnValue);
         } catch (Throwable var7) {
            Object result = this.probeNode.onReturnExceptionalOrUnwind(frame, var7, wasOnReturnExecuted);
            if (result == ProbeNode.UNWIND_ACTION_REENTER) {
               continue;
            }

            if (result == null) {
               throw var7;
            }

            returnValue = result;
         }

         return returnValue;
      }
   }
}
