
package com.oracle.truffle.regex.tregex.parser.ast;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.regex.tregex.buffer.CompilationBuffer;
import com.oracle.truffle.regex.tregex.parser.ast.RegexAST;
import com.oracle.truffle.regex.tregex.parser.ast.RegexASTNode;
import com.oracle.truffle.regex.tregex.parser.ast.RegexASTSubtreeRootNode;
import com.oracle.truffle.regex.tregex.parser.ast.Term;
import com.oracle.truffle.regex.tregex.util.json.JsonValue;

public final class AtomicGroup
extends RegexASTSubtreeRootNode {
    AtomicGroup() {
    }

    private AtomicGroup(AtomicGroup copy, RegexAST ast) {
        super(copy, ast);
    }

    private AtomicGroup(AtomicGroup copy, RegexAST ast, CompilationBuffer compilationBuffer) {
        super(copy, ast, compilationBuffer);
    }

    @Override
    public RegexASTSubtreeRootNode copy(RegexAST ast) {
        return ast.register(new AtomicGroup(this, ast));
    }

    @Override
    public Term copyRecursive(RegexAST ast, CompilationBuffer compilationBuffer) {
        return ast.register(new AtomicGroup(this, ast, compilationBuffer));
    }

    public int getEnclosedCaptureGroupsLow() {
        return this.getGroup().getEnclosedCaptureGroupsLow();
    }

    public int getEnclosedCaptureGroupsHigh() {
        return this.getGroup().getEnclosedCaptureGroupsHigh();
    }

    @Override
    public String getPrefix() {
        return "?>";
    }

    @Override
    public boolean equalsSemantic(RegexASTNode obj) {
        return this == obj || obj.isAtomicGroup() && this.getGroup().equalsSemantic(obj.asAtomicGroup().getGroup());
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public JsonValue toJson() {
        return this.toJson("AtomicGroup");
    }
}

