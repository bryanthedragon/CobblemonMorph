package com.oracle.truffle.regex.tregex.parser.ast;

import com.oracle.truffle.regex.tregex.automaton.SimpleStateIndex;

public final class SubTreeIndex extends SimpleStateIndex<RegexASTSubtreeRootNode> {
   protected int getStateId(RegexASTSubtreeRootNode state) {
      return state.getSubTreeId();
   }

   protected void setStateId(RegexASTSubtreeRootNode state, int id) {
      state.setSubTreeId(id);
   }
}
