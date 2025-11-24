
package com.oracle.truffle.regex.tregex.parser.ast.visitors;

import com.oracle.truffle.regex.tregex.automaton.StateSet;
import com.oracle.truffle.regex.tregex.parser.ast.CharacterClass;
import com.oracle.truffle.regex.tregex.parser.ast.RegexAST;
import com.oracle.truffle.regex.tregex.parser.ast.RegexASTNode;
import com.oracle.truffle.regex.tregex.parser.ast.visitors.DepthFirstTraversalRegexASTVisitor;

public final class AddToSetVisitor
extends DepthFirstTraversalRegexASTVisitor {
    private final StateSet<RegexAST, RegexASTNode> set;

    private AddToSetVisitor(StateSet<RegexAST, RegexASTNode> set2) {
        this.set = set2;
    }

    public static void addCharacterClasses(StateSet<RegexAST, RegexASTNode> set2, RegexASTNode runRoot) {
        new AddToSetVisitor(set2).run(runRoot);
    }

    @Override
    protected void visit(CharacterClass characterClass) {
        this.set.add(characterClass);
    }
}

