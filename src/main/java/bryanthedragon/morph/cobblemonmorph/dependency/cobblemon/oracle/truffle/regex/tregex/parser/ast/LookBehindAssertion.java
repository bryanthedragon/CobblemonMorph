
package com.oracle.truffle.regex.tregex.parser.ast;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.regex.tregex.buffer.CompilationBuffer;
import com.oracle.truffle.regex.tregex.parser.ast.LookAroundAssertion;
import com.oracle.truffle.regex.tregex.parser.ast.RegexAST;
import com.oracle.truffle.regex.tregex.parser.ast.RegexASTNode;
import com.oracle.truffle.regex.tregex.util.json.JsonValue;

public class LookBehindAssertion
extends LookAroundAssertion {
    LookBehindAssertion(boolean negated) {
        super(negated);
    }

    private LookBehindAssertion(LookBehindAssertion copy, RegexAST ast) {
        super(copy, ast);
    }

    private LookBehindAssertion(LookBehindAssertion copy, RegexAST ast, CompilationBuffer compilationBuffer) {
        super(copy, ast, compilationBuffer);
    }

    @Override
    public LookBehindAssertion copy(RegexAST ast) {
        return ast.register(new LookBehindAssertion(this, ast));
    }

    @Override
    public LookBehindAssertion copyRecursive(RegexAST ast, CompilationBuffer compilationBuffer) {
        return ast.register(new LookBehindAssertion(this, ast, compilationBuffer));
    }

    @Override
    public String getPrefix() {
        return this.isNegated() ? "?<!" : "?<=";
    }

    @Override
    public boolean equalsSemantic(RegexASTNode obj) {
        return this == obj || obj.isLookBehindAssertion() && this.groupEqualsSemantic(obj.asLookBehindAssertion());
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public JsonValue toJson() {
        return this.toJson(this.isNegated() ? "NegativeLookBehindAssertion" : "LookBehindAssertion");
    }
}

