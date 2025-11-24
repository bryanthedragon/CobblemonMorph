
package com.oracle.truffle.regex.tregex.parser.ast;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.regex.tregex.buffer.CompilationBuffer;
import com.oracle.truffle.regex.tregex.parser.ast.BackReference;
import com.oracle.truffle.regex.tregex.parser.ast.QuantifiableTerm;
import com.oracle.truffle.regex.tregex.parser.ast.RegexAST;
import com.oracle.truffle.regex.tregex.parser.ast.RegexASTNode;
import com.oracle.truffle.regex.tregex.parser.ast.Sequence;
import com.oracle.truffle.regex.tregex.util.json.Json;
import com.oracle.truffle.regex.tregex.util.json.JsonValue;

public class SubexpressionCall
extends QuantifiableTerm {
    private final int groupNr;

    SubexpressionCall(int groupNr) {
        this.groupNr = groupNr;
    }

    private SubexpressionCall(SubexpressionCall copy) {
        super(copy);
        this.groupNr = copy.groupNr;
    }

    @Override
    public SubexpressionCall copy(RegexAST ast) {
        return ast.register(new SubexpressionCall(this));
    }

    @Override
    public SubexpressionCall copyRecursive(RegexAST ast, CompilationBuffer compilationBuffer) {
        return this.copy(ast);
    }

    @Override
    public Sequence getParent() {
        return (Sequence)super.getParent();
    }

    public int getGroupNr() {
        return this.groupNr;
    }

    @Override
    public boolean isUnrollingCandidate() {
        return this.hasQuantifier() && this.getQuantifier().isWithinThreshold(6);
    }

    @Override
    public boolean equalsSemantic(RegexASTNode obj, boolean ignoreQuantifier) {
        return obj instanceof SubexpressionCall && ((SubexpressionCall)obj).groupNr == this.groupNr && (ignoreQuantifier || this.quantifierEquals((BackReference)obj));
    }

    @CompilerDirectives.TruffleBoundary
    public String toString() {
        return "\\g<" + this.groupNr + ">" + this.quantifierToString();
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public JsonValue toJson() {
        return this.toJson("SubexpressionCall").append(Json.prop("groupNr", this.groupNr));
    }
}

