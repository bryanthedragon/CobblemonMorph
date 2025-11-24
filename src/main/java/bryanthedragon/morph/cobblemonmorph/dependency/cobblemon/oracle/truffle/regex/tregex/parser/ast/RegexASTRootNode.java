
package com.oracle.truffle.regex.tregex.parser.ast;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.regex.tregex.buffer.CompilationBuffer;
import com.oracle.truffle.regex.tregex.parser.ast.RegexAST;
import com.oracle.truffle.regex.tregex.parser.ast.RegexASTNode;
import com.oracle.truffle.regex.tregex.parser.ast.RegexASTSubtreeRootNode;
import com.oracle.truffle.regex.tregex.util.json.JsonValue;

public class RegexASTRootNode
extends RegexASTSubtreeRootNode {
    RegexASTRootNode() {
        this.setId(0);
    }

    private RegexASTRootNode(RegexASTRootNode copy, RegexAST ast) {
        super(copy, ast);
    }

    private RegexASTRootNode(RegexASTRootNode copy, RegexAST ast, CompilationBuffer compilationBuffer) {
        super(copy, ast, compilationBuffer);
    }

    @Override
    public RegexASTSubtreeRootNode copy(RegexAST ast) {
        return new RegexASTRootNode(this, ast);
    }

    @Override
    public RegexASTSubtreeRootNode copyRecursive(RegexAST ast, CompilationBuffer compilationBuffer) {
        return new RegexASTRootNode(this, ast, compilationBuffer);
    }

    @Override
    public String getPrefix() {
        return "ROOT";
    }

    @Override
    public boolean equalsSemantic(RegexASTNode obj) {
        return this == obj || obj instanceof RegexASTRootNode && ((RegexASTRootNode)obj).getGroup().equalsSemantic(this.getGroup());
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public String toString() {
        return this.getGroup().toString();
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public JsonValue toJson() {
        return this.getGroup().toJson();
    }
}

