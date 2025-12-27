package com.oracle.truffle.api.impl;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.BlockNode;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.UnexpectedResultException;

final class DefaultBlockNode<T extends Node> extends BlockNode<T> {
   final BlockNode.ElementExecutor<T> executor;

   DefaultBlockNode(T[] elements, BlockNode.ElementExecutor<T> executor) {
      super(elements);
      this.executor = executor;
   }

   @Override
   public Object executeGeneric(VirtualFrame frame, int arg) {
      BlockNode.ElementExecutor<T> ex = this.executor;
      T[] e = this.getElements();
      int last = e.length - 1;

      for (int i = 0; i < last; i++) {
         ex.executeVoid(frame, e[i], i, arg);
      }

      return ex.executeGeneric(frame, e[last], last, arg);
   }

   @Override
   public void executeVoid(VirtualFrame frame, int arg) {
      BlockNode.ElementExecutor<T> ex = this.executor;
      T[] e = this.getElements();

      for (int i = 0; i < e.length; i++) {
         ex.executeVoid(frame, e[i], i, arg);
      }
   }

   @Override
   public byte executeByte(VirtualFrame frame, int arg) throws UnexpectedResultException {
      BlockNode.ElementExecutor<T> ex = this.executor;
      T[] e = this.getElements();
      int last = e.length - 1;

      for (int i = 0; i < last; i++) {
         ex.executeVoid(frame, e[i], i, arg);
      }

      return ex.executeByte(frame, e[last], last, arg);
   }

   @Override
   public short executeShort(VirtualFrame frame, int arg) throws UnexpectedResultException {
      BlockNode.ElementExecutor<T> ex = this.executor;
      T[] e = this.getElements();
      int last = e.length - 1;

      for (int i = 0; i < last; i++) {
         ex.executeVoid(frame, e[i], i, arg);
      }

      return ex.executeShort(frame, e[last], last, arg);
   }

   @Override
   public char executeChar(VirtualFrame frame, int arg) throws UnexpectedResultException {
      BlockNode.ElementExecutor<T> ex = this.executor;
      T[] e = this.getElements();
      int last = e.length - 1;

      for (int i = 0; i < last; i++) {
         ex.executeVoid(frame, e[i], i, arg);
      }

      return ex.executeChar(frame, e[last], last, arg);
   }

   @Override
   public int executeInt(VirtualFrame frame, int arg) throws UnexpectedResultException {
      BlockNode.ElementExecutor<T> ex = this.executor;
      T[] e = this.getElements();
      int last = e.length - 1;

      for (int i = 0; i < last; i++) {
         ex.executeVoid(frame, e[i], i, arg);
      }

      return ex.executeInt(frame, e[last], last, arg);
   }

   @Override
   public long executeLong(VirtualFrame frame, int arg) throws UnexpectedResultException {
      BlockNode.ElementExecutor<T> ex = this.executor;
      T[] e = this.getElements();
      int last = e.length - 1;

      for (int i = 0; i < last; i++) {
         ex.executeVoid(frame, e[i], i, arg);
      }

      return ex.executeLong(frame, e[last], last, arg);
   }

   @Override
   public float executeFloat(VirtualFrame frame, int arg) throws UnexpectedResultException {
      BlockNode.ElementExecutor<T> ex = this.executor;
      T[] e = this.getElements();
      int last = e.length - 1;

      for (int i = 0; i < last; i++) {
         ex.executeVoid(frame, e[i], i, arg);
      }

      return ex.executeFloat(frame, e[last], last, arg);
   }

   @Override
   public double executeDouble(VirtualFrame frame, int arg) throws UnexpectedResultException {
      BlockNode.ElementExecutor<T> ex = this.executor;
      T[] e = this.getElements();
      int last = e.length - 1;

      for (int i = 0; i < last; i++) {
         ex.executeVoid(frame, e[i], i, arg);
      }

      return ex.executeDouble(frame, e[last], last, arg);
   }

   @Override
   public boolean executeBoolean(VirtualFrame frame, int arg) throws UnexpectedResultException {
      BlockNode.ElementExecutor<T> ex = this.executor;
      T[] e = this.getElements();
      int last = e.length - 1;

      for (int i = 0; i < last; i++) {
         ex.executeVoid(frame, e[i], i, arg);
      }

      return ex.executeBoolean(frame, e[last], last, arg);
   }
}
