package com.oracle.truffle.api.nodes;

import com.oracle.truffle.api.frame.VirtualFrame;
import java.util.Objects;

public abstract class BlockNode<T extends Node> extends Node {
   public static final int NO_ARGUMENT = 0;
   @Node.Children
   private final T[] elements;

   protected BlockNode(T[] elements) {
      this.elements = elements;

      assert this.getClass().getName().equals("com.oracle.truffle.api.impl.DefaultBlockNode")
         || this.getClass().getName().equals("org.graalvm.compiler.truffle.runtime.OptimizedBlockNode") : "Custom block implementations are not allowed.";
   }

   public abstract void executeVoid(VirtualFrame frame, int argument);

   public abstract Object executeGeneric(VirtualFrame frame, int argument);

   public abstract byte executeByte(VirtualFrame frame, int argument) throws UnexpectedResultException;

   public abstract short executeShort(VirtualFrame frame, int argument) throws UnexpectedResultException;

   public abstract int executeInt(VirtualFrame frame, int argument) throws UnexpectedResultException;

   public abstract char executeChar(VirtualFrame frame, int argument) throws UnexpectedResultException;

   public abstract float executeFloat(VirtualFrame frame, int argument) throws UnexpectedResultException;

   public abstract double executeDouble(VirtualFrame frame, int argument) throws UnexpectedResultException;

   public abstract long executeLong(VirtualFrame frame, int argument) throws UnexpectedResultException;

   public abstract boolean executeBoolean(VirtualFrame frame, int argument) throws UnexpectedResultException;

   public final T[] getElements() {
      return this.elements;
   }

   @Override
   public final NodeCost getCost() {
      return NodeCost.NONE;
   }

   public static <T extends Node> BlockNode<T> create(T[] elements, BlockNode.ElementExecutor<T> executor) {
      Objects.requireNonNull((T)elements);
      Objects.requireNonNull(executor);
      if (elements.length == 0) {
         throw new IllegalArgumentException("Empty blocks are not allowed.");
      } else {
         return NodeAccessor.RUNTIME.createBlockNode(elements, executor);
      }
   }

   public interface ElementExecutor<T extends Node> {
      void executeVoid(VirtualFrame frame, T node, int index, int argument);

      default Object executeGeneric(VirtualFrame frame, T node, int index, int argument) {
         this.executeVoid(frame, node, index, argument);
         return null;
      }

      default boolean executeBoolean(VirtualFrame frame, T node, int index, int argument) throws UnexpectedResultException {
         Object result = this.executeGeneric(frame, node, index, argument);
         if (result instanceof Boolean) {
            return (Boolean)result;
         } else {
            throw new UnexpectedResultException(result);
         }
      }

      default byte executeByte(VirtualFrame frame, T node, int index, int argument) throws UnexpectedResultException {
         Object result = this.executeGeneric(frame, node, index, argument);
         if (result instanceof Byte) {
            return (Byte)result;
         } else {
            throw new UnexpectedResultException(result);
         }
      }

      default short executeShort(VirtualFrame frame, T node, int index, int argument) throws UnexpectedResultException {
         Object result = this.executeGeneric(frame, node, index, argument);
         if (result instanceof Short) {
            return (Short)result;
         } else {
            throw new UnexpectedResultException(result);
         }
      }

      default char executeChar(VirtualFrame frame, T node, int index, int argument) throws UnexpectedResultException {
         Object result = this.executeGeneric(frame, node, index, argument);
         if (result instanceof Character) {
            return (Character)result;
         } else {
            throw new UnexpectedResultException(result);
         }
      }

      default int executeInt(VirtualFrame frame, T node, int index, int argument) throws UnexpectedResultException {
         Object result = this.executeGeneric(frame, node, index, argument);
         if (result instanceof Integer) {
            return (Integer)result;
         } else {
            throw new UnexpectedResultException(result);
         }
      }

      default long executeLong(VirtualFrame frame, T node, int index, int argument) throws UnexpectedResultException {
         Object result = this.executeGeneric(frame, node, index, argument);
         if (result instanceof Long) {
            return (Long)result;
         } else {
            throw new UnexpectedResultException(result);
         }
      }

      default float executeFloat(VirtualFrame frame, T node, int index, int argument) throws UnexpectedResultException {
         Object result = this.executeGeneric(frame, node, index, argument);
         if (result instanceof Float) {
            return (Float)result;
         } else {
            throw new UnexpectedResultException(result);
         }
      }

      default double executeDouble(VirtualFrame frame, T node, int index, int argument) throws UnexpectedResultException {
         Object result = this.executeGeneric(frame, node, index, argument);
         if (result instanceof Double) {
            return (Double)result;
         } else {
            throw new UnexpectedResultException(result);
         }
      }
   }
}
