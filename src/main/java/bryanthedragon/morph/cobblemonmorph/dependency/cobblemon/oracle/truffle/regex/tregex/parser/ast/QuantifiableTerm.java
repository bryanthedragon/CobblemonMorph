package com.oracle.truffle.regex.tregex.parser.ast;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.regex.tregex.parser.Token;

public abstract class QuantifiableTerm extends Term {
   private Token.Quantifier quantifier;

   QuantifiableTerm() {
   }

   QuantifiableTerm(QuantifiableTerm copy) {
      super(copy);
      this.quantifier = copy.quantifier;
   }

   public abstract QuantifiableTerm copy(RegexAST ast);

   public boolean hasQuantifier() {
      return this.quantifier != null;
   }

   public boolean hasNotUnrolledQuantifier() {
      return this.hasQuantifier() && !this.isExpandedQuantifier();
   }

   public abstract boolean isUnrollingCandidate();

   public Token.Quantifier getQuantifier() {
      return this.quantifier;
   }

   public void setQuantifier(Token.Quantifier quantifier) {
      this.quantifier = quantifier;
   }

   boolean quantifierEquals(QuantifiableTerm o) {
      if (this.quantifier == null) {
         return o.quantifier == null;
      } else {
         return o.quantifier == null ? this.quantifier == null : this.quantifier.equalsSemantic(o.quantifier);
      }
   }

   @Override
   public boolean equalsSemantic(RegexASTNode obj) {
      return this.equalsSemantic(obj, false);
   }

   public abstract boolean equalsSemantic(RegexASTNode obj, boolean ignoreQuantifier);

   @CompilerDirectives.TruffleBoundary
   protected String quantifierToString() {
      return this.hasNotUnrolledQuantifier() ? this.quantifier.toString() : "";
   }

   @Override
   public RegexASTSubtreeRootNode getSubTreeParent() {
      for (RegexASTNode current = this; current.getParent() != null; current = current.getParent().getParent()) {
         assert current instanceof QuantifiableTerm;

         if (current.getParent() instanceof RegexASTSubtreeRootNode) {
            return (RegexASTSubtreeRootNode)current.getParent();
         }
      }

      return null;
   }
}
