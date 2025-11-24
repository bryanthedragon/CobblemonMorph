
package com.oracle.truffle.regex.tregex.parser.ast;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.regex.tregex.buffer.CompilationBuffer;
import com.oracle.truffle.regex.tregex.parser.ast.Group;
import com.oracle.truffle.regex.tregex.parser.ast.MatchFound;
import com.oracle.truffle.regex.tregex.parser.ast.PositionAssertion;
import com.oracle.truffle.regex.tregex.parser.ast.RegexAST;
import com.oracle.truffle.regex.tregex.parser.ast.RegexASTNode;
import com.oracle.truffle.regex.tregex.parser.ast.SubTreeIndex;
import com.oracle.truffle.regex.tregex.parser.ast.Term;
import com.oracle.truffle.regex.tregex.parser.ast.visitors.RegexASTVisitorIterable;
import com.oracle.truffle.regex.tregex.util.json.Json;
import com.oracle.truffle.regex.tregex.util.json.JsonObject;

public abstract class RegexASTSubtreeRootNode
extends Term
implements RegexASTVisitorIterable {
    private int globalSubTreeId = -1;
    private int subTreeId = -1;
    private Group group;
    private PositionAssertion anchoredInitialState;
    private MatchFound unAnchoredInitialState;
    private PositionAssertion anchoredFinalState;
    private MatchFound matchFound;
    private boolean visitorGroupVisited = false;
    private final SubTreeIndex subtrees = new SubTreeIndex();

    RegexASTSubtreeRootNode() {
    }

    RegexASTSubtreeRootNode(RegexASTSubtreeRootNode copy, RegexAST ast) {
        super(copy);
        ast.createNFAHelperNodes(this);
    }

    RegexASTSubtreeRootNode(RegexASTSubtreeRootNode copy, RegexAST ast, CompilationBuffer compilationBuffer) {
        this(copy, ast);
        this.setGroup(copy.group.copyRecursive(ast, compilationBuffer));
    }

    public boolean globalSubTreeIdInitialized() {
        return this.globalSubTreeId >= 0;
    }

    public int getGlobalSubTreeId() {
        return this.globalSubTreeId;
    }

    public void setGlobalSubTreeId(int globalSubTreeId) {
        this.globalSubTreeId = globalSubTreeId;
    }

    public boolean subTreeIdInitialized() {
        return this.subTreeId >= 0;
    }

    public int getSubTreeId() {
        return this.subTreeId;
    }

    public void setSubTreeId(int subTreeId) {
        this.subTreeId = subTreeId;
    }

    public SubTreeIndex getSubtrees() {
        return this.subtrees;
    }

    @Override
    public abstract RegexASTSubtreeRootNode copy(RegexAST var1);

    public Group getGroup() {
        return this.group;
    }

    public void setGroup(Group group) {
        this.group = group;
        group.setParent(this);
        if (this.anchoredInitialState != null) {
            this.anchoredInitialState.setNext(group);
        }
        if (this.unAnchoredInitialState != null) {
            this.unAnchoredInitialState.setNext(group);
        }
    }

    public MatchFound getMatchFound() {
        return this.matchFound;
    }

    public void setMatchFound(MatchFound matchFound) {
        this.matchFound = matchFound;
        matchFound.setParent(this);
    }

    public Term getAnchoredInitialState() {
        return this.anchoredInitialState;
    }

    public void setAnchoredInitialState(PositionAssertion anchoredInitialState) {
        this.anchoredInitialState = anchoredInitialState;
        anchoredInitialState.setParent(this);
        anchoredInitialState.setNext(this.group);
    }

    public Term getUnAnchoredInitialState() {
        return this.unAnchoredInitialState;
    }

    public void setUnAnchoredInitialState(MatchFound unAnchoredInitialState) {
        this.unAnchoredInitialState = unAnchoredInitialState;
        unAnchoredInitialState.setParent(this);
        unAnchoredInitialState.setNext(this.group);
    }

    public Term getAnchoredFinalState() {
        return this.anchoredFinalState;
    }

    public void setAnchoredFinalState(PositionAssertion anchoredFinalState) {
        this.anchoredFinalState = anchoredFinalState;
        anchoredFinalState.setParent(this);
    }

    @Override
    public boolean visitorHasNext() {
        return !this.visitorGroupVisited;
    }

    @Override
    public RegexASTNode visitorGetNext(boolean reverse) {
        this.visitorGroupVisited = true;
        return this.group;
    }

    @Override
    public void resetVisitorIterator() {
        this.visitorGroupVisited = false;
    }

    public abstract String getPrefix();

    @CompilerDirectives.TruffleBoundary
    public String toString() {
        return "(" + this.getPrefix() + this.group.alternativesToString() + ")";
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    protected JsonObject toJson(String typeName) {
        return super.toJson(typeName).append(Json.prop("group", RegexASTSubtreeRootNode.astNodeId(this.group)));
    }
}

