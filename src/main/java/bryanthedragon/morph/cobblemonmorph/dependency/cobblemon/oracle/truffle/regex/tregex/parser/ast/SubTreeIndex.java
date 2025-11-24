
package com.oracle.truffle.regex.tregex.parser.ast;

import com.oracle.truffle.regex.tregex.automaton.SimpleStateIndex;
import com.oracle.truffle.regex.tregex.parser.ast.RegexASTSubtreeRootNode;

public final class SubTreeIndex
extends SimpleStateIndex<RegexASTSubtreeRootNode> {
    @Override
    protected int getStateId(RegexASTSubtreeRootNode state) {
        return state.getSubTreeId();
    }

    @Override
    protected void setStateId(RegexASTSubtreeRootNode state, int id) {
        state.setSubTreeId(id);
    }
}

