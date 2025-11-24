
package com.oracle.truffle.regex.tregex.parser.ast;

import com.oracle.truffle.regex.tregex.automaton.SimpleStateIndex;
import com.oracle.truffle.regex.tregex.parser.ast.RegexASTSubtreeRootNode;

public final class GlobalSubTreeIndex
extends SimpleStateIndex<RegexASTSubtreeRootNode> {
    @Override
    protected int getStateId(RegexASTSubtreeRootNode state) {
        return state.getGlobalSubTreeId();
    }

    @Override
    protected void setStateId(RegexASTSubtreeRootNode state, int id) {
        state.setGlobalSubTreeId(id);
    }
}

