package com.oracle.truffle.regex.literal;

import com.oracle.truffle.regex.RegexLanguage;
import com.oracle.truffle.regex.tregex.parser.RegexProperties;
import com.oracle.truffle.regex.tregex.parser.ast.RegexAST;
import com.oracle.truffle.regex.tregex.parser.ast.visitors.PreCalcResultVisitor;

public final class LiteralRegexEngine {
   public static LiteralRegexExecNode createNode(RegexLanguage language, RegexAST ast) {
      RegexProperties props = ast.getProperties();
      return !ast.isLiteralString()
            || !props.isFixedCodePointWidth()
            || props.hasLoneSurrogates()
            || props.hasQuantifiers() && ast.getRoot().getMinPath() > 32767
         ? null
         : createLiteralNode(language, ast);
   }

   private static LiteralRegexExecNode createLiteralNode(RegexLanguage language, RegexAST ast) {
      PreCalcResultVisitor preCalcResultVisitor = PreCalcResultVisitor.run(ast, true);
      boolean caret = ast.getRoot().startsWithCaret();
      boolean dollar = ast.getRoot().endsWithDollar();
      if (ast.getRoot().getMinPath() == 0) {
         if (caret) {
            return dollar
               ? LiteralRegexExecNode.create(language, ast, new LiteralRegexExecNode.EmptyEquals(preCalcResultVisitor, ast.getOptions().isMustAdvance()))
               : LiteralRegexExecNode.create(language, ast, new LiteralRegexExecNode.EmptyStartsWith(preCalcResultVisitor, ast.getOptions().isMustAdvance()));
         } else {
            return dollar
               ? LiteralRegexExecNode.create(
                  language, ast, new LiteralRegexExecNode.EmptyEndsWith(preCalcResultVisitor, ast.getFlags().isSticky(), ast.getOptions().isMustAdvance())
               )
               : LiteralRegexExecNode.create(language, ast, new LiteralRegexExecNode.EmptyIndexOf(preCalcResultVisitor, ast.getOptions().isMustAdvance()));
         }
      } else if (caret) {
         return dollar
            ? LiteralRegexExecNode.create(language, ast, new LiteralRegexExecNode.Equals(preCalcResultVisitor))
            : LiteralRegexExecNode.create(language, ast, new LiteralRegexExecNode.StartsWith(preCalcResultVisitor));
      } else if (dollar) {
         return LiteralRegexExecNode.create(language, ast, new LiteralRegexExecNode.EndsWith(preCalcResultVisitor, ast.getFlags().isSticky()));
      } else if (ast.getFlags().isSticky()) {
         return LiteralRegexExecNode.create(language, ast, new LiteralRegexExecNode.RegionMatches(preCalcResultVisitor));
      } else {
         return preCalcResultVisitor.getLiteral().encodedLength() <= 64
            ? LiteralRegexExecNode.create(language, ast, new LiteralRegexExecNode.IndexOfString(preCalcResultVisitor))
            : null;
      }
   }
}
