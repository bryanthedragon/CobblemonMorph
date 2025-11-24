
package com.oracle.truffle.regex.tregex.nfa;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.regex.UnsupportedRegexException;
import com.oracle.truffle.regex.tregex.nfa.ASTSuccessor;
import com.oracle.truffle.regex.tregex.parser.ast.RegexASTNode;
import com.oracle.truffle.regex.tregex.util.json.Json;
import com.oracle.truffle.regex.tregex.util.json.JsonConvertible;
import com.oracle.truffle.regex.tregex.util.json.JsonValue;
import java.util.ArrayList;

public final class ASTStep
implements JsonConvertible {
    private final RegexASTNode root;
    private final ArrayList<ASTSuccessor> successors = new ArrayList();

    public ASTStep(RegexASTNode root) {
        this.root = root;
    }

    public RegexASTNode getRoot() {
        return this.root;
    }

    public ArrayList<ASTSuccessor> getSuccessors() {
        return this.successors;
    }

    public void addSuccessor(ASTSuccessor successor) {
        this.successors.add(successor);
        if (this.successors.size() > Short.MAX_VALUE) {
            throw new UnsupportedRegexException("ASTSuccessor explosion");
        }
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public JsonValue toJson() {
        return Json.obj(Json.prop("root", this.root.getId()), Json.prop("successors", this.successors));
    }
}

