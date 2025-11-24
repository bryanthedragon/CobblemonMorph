
package com.oracle.truffle.regex.tregex.parser.ast;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.regex.tregex.buffer.CompilationBuffer;
import com.oracle.truffle.regex.tregex.parser.ast.RegexAST;
import com.oracle.truffle.regex.tregex.parser.ast.RegexASTNode;
import com.oracle.truffle.regex.tregex.parser.ast.Term;
import com.oracle.truffle.regex.tregex.util.json.JsonValue;

public class MatchFound
extends Term {
    private RegexASTNode next;

    @Override
    public MatchFound copy(RegexAST ast) {
        throw CompilerDirectives.shouldNotReachHere();
    }

    @Override
    public MatchFound copyRecursive(RegexAST ast, CompilationBuffer compilationBuffer) {
        throw CompilerDirectives.shouldNotReachHere();
    }

    public RegexASTNode getNext() {
        return this.next;
    }

    public void setNext(RegexASTNode next) {
        this.next = next;
    }

    @Override
    public boolean equalsSemantic(RegexASTNode obj) {
        return obj instanceof MatchFound;
    }

    public String toString() {
        return "::";
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public JsonValue toJson() {
        return this.toJson("MatchFound");
    }
}

