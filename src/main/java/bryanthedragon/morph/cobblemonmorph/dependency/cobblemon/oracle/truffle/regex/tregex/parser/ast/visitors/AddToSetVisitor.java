package com.oracle.truffle.regex.tregex.parser.ast.visitors;

import com.oracle.truffle.regex.tregex.automaton.StateSet;
import com.oracle.truffle.regex.tregex.parser.ast.CharacterClass;
import com.oracle.truffle.regex.tregex.parser.ast.RegexAST;
import com.oracle.truffle.regex.tregex.parser.ast.RegexASTNode;

public final class AddToSetVisitor extends DepthFirstTraversalRegexASTVisitor {
   private final StateSet<RegexAST, RegexASTNode> set;

   private AddToSetVisitor(StateSet<RegexAST, RegexASTNode> set) {
      this.set = set;
   }

   public static void addCharacterClasses(StateSet<RegexAST, RegexASTNode> set, RegexASTNode runRoot) {
      new AddToSetVisitor(set).run(runRoot);
   }

   @Override
   protected void visit(CharacterClass characterClass) {
      this.set.add(characterClass);
   }
}
