package com.oracle.truffle.regex.tregex.parser.ast;

import com.oracle.truffle.regex.tregex.automaton.SimpleStateIndex;

public final class GlobalSubTreeIndex extends SimpleStateIndex<RegexASTSubtreeRootNode> {
   protected int getStateId(RegexASTSubtreeRootNode state) {
      return state.getGlobalSubTreeId();
   }

   protected void setStateId(RegexASTSubtreeRootNode state, int id) {
      state.setGlobalSubTreeId(id);
   }
}
