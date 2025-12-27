package com.oracle.truffle.regex.tregex.parser.ast;

import com.oracle.truffle.regex.UnsupportedRegexException;
import com.oracle.truffle.regex.tregex.automaton.AbstractState;
import com.oracle.truffle.regex.tregex.buffer.CompilationBuffer;
import com.oracle.truffle.regex.tregex.nfa.ASTTransition;

public abstract class Term extends RegexASTNode implements AbstractState<Term, ASTTransition> {
   private int seqIndex = 0;

   Term() {
   }

   Term(Term copy) {
      super(copy);
   }

   public abstract Term copy(RegexAST ast);

   public abstract Term copyRecursive(RegexAST ast, CompilationBuffer compilationBuffer);

   public int getSeqIndex() {
      return this.seqIndex;
   }

   public void setSeqIndex(int seqIndex) {
      this.seqIndex = seqIndex;
      if (seqIndex > 32767) {
         throw new UnsupportedRegexException("too many terms in a single sequence");
      }
   }

   @Override
   public RegexASTSubtreeRootNode getSubTreeParent() {
      for (RegexASTNode current = this; current.getParent() != null; current = current.getParent().getParent()) {
         assert current instanceof Term;

         if (current.getParent() instanceof RegexASTSubtreeRootNode) {
            return (RegexASTSubtreeRootNode)current.getParent();
         }
      }

      return null;
   }
}
