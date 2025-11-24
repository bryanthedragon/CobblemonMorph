
package com.oracle.truffle.regex.literal;

import com.oracle.truffle.regex.RegexLanguage;
import com.oracle.truffle.regex.literal.LiteralRegexExecNode;
import com.oracle.truffle.regex.tregex.parser.RegexProperties;
import com.oracle.truffle.regex.tregex.parser.ast.RegexAST;
import com.oracle.truffle.regex.tregex.parser.ast.visitors.PreCalcResultVisitor;

public final class LiteralRegexEngine {
    public static LiteralRegexExecNode createNode(RegexLanguage language, RegexAST ast) {
        RegexProperties props = ast.getProperties();
        if (ast.isLiteralString() && props.isFixedCodePointWidth() && !props.hasLoneSurrogates() && (!props.hasQuantifiers() || ast.getRoot().getMinPath() <= Short.MAX_VALUE)) {
            return LiteralRegexEngine.createLiteralNode(language, ast);
        }
        return null;
    }

    private static LiteralRegexExecNode createLiteralNode(RegexLanguage language, RegexAST ast) {
        PreCalcResultVisitor preCalcResultVisitor = PreCalcResultVisitor.run(ast, true);
        boolean caret = ast.getRoot().startsWithCaret();
        boolean dollar = ast.getRoot().endsWithDollar();
        if (ast.getRoot().getMinPath() == 0) {
            if (caret) {
                if (dollar) {
                    return LiteralRegexExecNode.create(language, ast, new LiteralRegexExecNode.EmptyEquals(preCalcResultVisitor, ast.getOptions().isMustAdvance()));
                }
                return LiteralRegexExecNode.create(language, ast, new LiteralRegexExecNode.EmptyStartsWith(preCalcResultVisitor, ast.getOptions().isMustAdvance()));
            }
            if (dollar) {
                return LiteralRegexExecNode.create(language, ast, new LiteralRegexExecNode.EmptyEndsWith(preCalcResultVisitor, ast.getFlags().isSticky(), ast.getOptions().isMustAdvance()));
            }
            return LiteralRegexExecNode.create(language, ast, new LiteralRegexExecNode.EmptyIndexOf(preCalcResultVisitor, ast.getOptions().isMustAdvance()));
        }
        if (caret) {
            if (dollar) {
                return LiteralRegexExecNode.create(language, ast, new LiteralRegexExecNode.Equals(preCalcResultVisitor));
            }
            return LiteralRegexExecNode.create(language, ast, new LiteralRegexExecNode.StartsWith(preCalcResultVisitor));
        }
        if (dollar) {
            return LiteralRegexExecNode.create(language, ast, new LiteralRegexExecNode.EndsWith(preCalcResultVisitor, ast.getFlags().isSticky()));
        }
        if (ast.getFlags().isSticky()) {
            return LiteralRegexExecNode.create(language, ast, new LiteralRegexExecNode.RegionMatches(preCalcResultVisitor));
        }
        if (preCalcResultVisitor.getLiteral().encodedLength() <= 64) {
            return LiteralRegexExecNode.create(language, ast, new LiteralRegexExecNode.IndexOfString(preCalcResultVisitor));
        }
        return null;
    }
}

