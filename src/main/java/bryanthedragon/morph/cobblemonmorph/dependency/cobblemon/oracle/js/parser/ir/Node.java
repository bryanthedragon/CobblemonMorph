package com.oracle.js.parser.ir;

import com.oracle.js.parser.Token;
import com.oracle.js.parser.TokenType;
import com.oracle.js.parser.ir.visitor.NodeVisitor;
import com.oracle.js.parser.ir.visitor.TranslatorNodeVisitor;
import java.util.ArrayList;
import java.util.List;

public abstract class Node implements Cloneable {
   protected final int start;
   protected final int finish;
   private final long token;

   public Node(final long token, final int finish) {
      this.token = token;
      this.start = Token.descPosition(token);
      this.finish = finish;
   }

   protected Node(final long token, final int start, final int finish) {
      this.start = start;
      this.finish = finish;
      this.token = token;
   }

   protected Node(final Node node) {
      this.token = node.token;
      this.start = node.start;
      this.finish = node.finish;
   }

   protected Node(final Node node, final int finish) {
      this.token = node.token;
      this.start = node.start;
      this.finish = finish;
   }

   public boolean isLoop() {
      return false;
   }

   public boolean isAssignment() {
      return false;
   }

   public abstract Node accept(NodeVisitor<? extends LexicalContext> visitor);

   public abstract <R> R accept(TranslatorNodeVisitor<? extends LexicalContext, R> visitor);

   @Override
   public final String toString() {
      return this.toString(true);
   }

   public final String toString(final boolean includeTypeInfo) {
      StringBuilder sb = new StringBuilder();
      this.toString(sb, includeTypeInfo);
      return sb.toString();
   }

   public abstract void toString(final StringBuilder sb, final boolean printType);

   public int getFinish() {
      return this.finish;
   }

   public int getStart() {
      return this.start;
   }

   public int getSourceOrder() {
      return this.getStart();
   }

   @Override
   protected Object clone() {
      try {
         return super.clone();
      } catch (CloneNotSupportedException var2) {
         throw new AssertionError(var2);
      }
   }

   @Override
   public final boolean equals(final Object other) {
      return this == other;
   }

   @Override
   public final int hashCode() {
      return Long.hashCode(this.token);
   }

   public TokenType tokenType() {
      return Token.descType(this.token);
   }

   public boolean isTokenType(final TokenType type) {
      return this.tokenType() == type;
   }

   public long getToken() {
      return this.token;
   }

   static <T extends Node> List<T> accept(final NodeVisitor<? extends LexicalContext> visitor, final List<T> list) {
      int size = list.size();
      if (size == 0) {
         return list;
      } else {
         List<T> newList = null;

         for (int i = 0; i < size; i++) {
            T node = (T)list.get(i);
            T newNode = (T)(node == null ? null : node.accept(visitor));
            if (newNode == node) {
               if (newList != null) {
                  newList.add(node);
               }
            } else {
               if (newList == null) {
                  newList = new ArrayList<>(size);

                  for (int j = 0; j < i; j++) {
                     newList.add(list.get(j));
                  }
               }

               newList.add(newNode);
            }
         }

         return newList == null ? list : newList;
      }
   }

   static <T extends LexicalContextNode> T replaceInLexicalContext(final LexicalContext lc, final T oldNode, final T newNode) {
      if (lc != null) {
         lc.replace(oldNode, newNode);
      }

      return newNode;
   }
}
