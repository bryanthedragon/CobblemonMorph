
package com.oracle.truffle.regex.tregex.parser.ast;

import com.oracle.truffle.regex.tregex.buffer.CompilationBuffer;
import com.oracle.truffle.regex.tregex.parser.ast.AtomicGroup;
import com.oracle.truffle.regex.tregex.parser.ast.BackReference;
import com.oracle.truffle.regex.tregex.parser.ast.CharacterClass;
import com.oracle.truffle.regex.tregex.parser.ast.Group;
import com.oracle.truffle.regex.tregex.parser.ast.LookAheadAssertion;
import com.oracle.truffle.regex.tregex.parser.ast.LookAroundAssertion;
import com.oracle.truffle.regex.tregex.parser.ast.LookBehindAssertion;
import com.oracle.truffle.regex.tregex.parser.ast.MatchFound;
import com.oracle.truffle.regex.tregex.parser.ast.PositionAssertion;
import com.oracle.truffle.regex.tregex.parser.ast.QuantifiableTerm;
import com.oracle.truffle.regex.tregex.parser.ast.RegexAST;
import com.oracle.truffle.regex.tregex.parser.ast.RegexASTRootNode;
import com.oracle.truffle.regex.tregex.parser.ast.RegexASTSubtreeRootNode;
import com.oracle.truffle.regex.tregex.parser.ast.Sequence;
import com.oracle.truffle.regex.tregex.util.json.Json;
import com.oracle.truffle.regex.tregex.util.json.JsonConvertible;
import com.oracle.truffle.regex.tregex.util.json.JsonObject;
import com.oracle.truffle.regex.tregex.util.json.JsonValue;

public abstract class RegexASTNode
implements JsonConvertible {
    static final int FLAG_PREFIX = 1;
    static final int FLAG_DEAD = 2;
    static final int FLAG_HAS_CARET = 4;
    static final int FLAG_HAS_DOLLAR = 8;
    static final int FLAG_STARTS_WITH_CARET = 16;
    static final int FLAG_ENDS_WITH_DOLLAR = 32;
    static final int FLAG_BACK_REFERENCE_IS_NESTED = 64;
    static final int FLAG_BACK_REFERENCE_IS_FORWARD = 128;
    static final int FLAG_GROUP_LOOP = 256;
    static final int FLAG_GROUP_EXPANDED_QUANTIFIER = 512;
    static final int FLAG_EMPTY_GUARD = 1024;
    static final int FLAG_LOOK_AROUND_NEGATED = 2048;
    static final int FLAG_HAS_LOOPS = 4096;
    static final int FLAG_HAS_CAPTURE_GROUPS = 8192;
    static final int FLAG_HAS_QUANTIFIERS = 16384;
    static final int FLAG_HAS_LOOK_BEHINDS = 32768;
    static final int FLAG_HAS_LOOK_AHEADS = 65536;
    static final int FLAG_HAS_BACK_REFERENCES = 131072;
    static final int FLAG_CHARACTER_CLASS_WAS_SINGLE_CHAR = 262144;
    private int id = -1;
    private RegexASTNode parent;
    private int flags;
    private int minPath = 0;
    private int maxPath = 0;

    protected RegexASTNode() {
    }

    protected RegexASTNode(RegexASTNode copy) {
        this.flags = copy.flags;
        this.minPath = copy.minPath;
        this.maxPath = copy.maxPath;
    }

    public abstract RegexASTNode copy(RegexAST var1);

    public abstract RegexASTNode copyRecursive(RegexAST var1, CompilationBuffer var2);

    public abstract boolean equalsSemantic(RegexASTNode var1);

    public boolean idInitialized() {
        return this.id >= 0;
    }

    public final int getId() {
        assert (this.idInitialized());
        return this.id;
    }

    public final void setId(int id) {
        assert (!this.idInitialized());
        assert (id <= Integer.MAX_VALUE);
        this.id = id;
    }

    public RegexASTNode getParent() {
        return this.parent;
    }

    public void setParent(RegexASTNode parent) {
        this.parent = parent;
    }

    protected boolean isFlagSet(int flag) {
        return (this.flags & flag) != 0;
    }

    protected void setFlag(int flag) {
        this.setFlag(flag, true);
    }

    protected int getFlags(int mask) {
        return this.flags & mask;
    }

    protected void setFlags(int newFlags, int mask) {
        assert ((newFlags & ~mask) == 0);
        this.flags = this.flags & ~mask | newFlags;
    }

    protected void setFlag(int flag, boolean value2) {
        this.flags = value2 ? (this.flags |= flag) : (this.flags &= ~flag);
    }

    public void markAsDead() {
        this.setDead(true);
    }

    public void setDead(boolean dead) {
        this.setFlag(2, dead);
    }

    public boolean isDead() {
        return this.isFlagSet(2);
    }

    public boolean isPrefix() {
        return this.isFlagSet(1);
    }

    public void setPrefix() {
        this.setFlag(1);
    }

    public boolean hasEmptyGuard() {
        return this.isFlagSet(1024);
    }

    public void setEmptyGuard(boolean emptyGuard) {
        this.setFlag(1024, emptyGuard);
    }

    public boolean hasCaret() {
        return this.isFlagSet(4);
    }

    public void setHasCaret() {
        this.setHasCaret(true);
    }

    public void setHasCaret(boolean hasCaret) {
        this.setFlag(4, hasCaret);
    }

    public boolean hasDollar() {
        return this.isFlagSet(8);
    }

    public void setHasDollar() {
        this.setHasDollar(true);
    }

    public void setHasDollar(boolean hasDollar) {
        this.setFlag(8, hasDollar);
    }

    public boolean startsWithCaret() {
        return this.isFlagSet(16);
    }

    public void setStartsWithCaret() {
        this.setStartsWithCaret(true);
    }

    public void setStartsWithCaret(boolean startsWithCaret) {
        this.setFlag(16, startsWithCaret);
    }

    public boolean endsWithDollar() {
        return this.isFlagSet(32);
    }

    public void setEndsWithDollar() {
        this.setEndsWithDollar(true);
    }

    public void setEndsWithDollar(boolean endsWithDollar) {
        this.setFlag(32, endsWithDollar);
    }

    public boolean hasLoops() {
        return this.isFlagSet(4096);
    }

    public void setHasLoops() {
        this.setHasLoops(true);
    }

    public void setHasLoops(boolean hasLoops) {
        this.setFlag(4096, hasLoops);
    }

    public boolean hasQuantifiers() {
        return this.isFlagSet(16384);
    }

    public void setHasQuantifiers() {
        this.setFlag(16384, true);
    }

    public boolean hasCaptureGroups() {
        return this.isFlagSet(8192);
    }

    public void setHasCaptureGroups() {
        this.setFlag(8192, true);
    }

    public boolean hasLookAheads() {
        return this.isFlagSet(65536);
    }

    public void setHasLookAheads() {
        this.setFlag(65536, true);
    }

    public boolean hasLookBehinds() {
        return this.isFlagSet(32768);
    }

    public void setHasLookBehinds() {
        this.setFlag(32768, true);
    }

    public boolean hasBackReferences() {
        return this.isFlagSet(131072);
    }

    public void setHasBackReferences() {
        this.setFlag(131072, true);
    }

    public boolean isExpandedQuantifier() {
        return this.isFlagSet(512);
    }

    public void setExpandedQuantifier(boolean expandedQuantifier) {
        this.setFlag(512, expandedQuantifier);
    }

    public int getMinPath() {
        return this.minPath;
    }

    public void setMinPath(int n) {
        this.minPath = n;
    }

    public void incMinPath() {
        this.incMinPath(1);
    }

    public void incMinPath(int n) {
        this.minPath += n;
    }

    public int getMaxPath() {
        return this.maxPath;
    }

    public void setMaxPath(int n) {
        this.maxPath = n;
    }

    public void incMaxPath() {
        this.incMaxPath(1);
    }

    public void incMaxPath(int n) {
        this.maxPath += n;
    }

    public abstract RegexASTSubtreeRootNode getSubTreeParent();

    public boolean isInLookBehindAssertion() {
        return this.getSubTreeParent() instanceof LookBehindAssertion;
    }

    public boolean isInLookAheadAssertion() {
        return this.getSubTreeParent() instanceof LookAheadAssertion;
    }

    public String toStringWithID() {
        return String.format("%d (%s)", this.id, this.toString());
    }

    protected static JsonValue astNodeId(RegexASTNode astNode) {
        return astNode == null ? Json.nullValue() : Json.val(astNode.id);
    }

    public boolean isBackReference() {
        return this instanceof BackReference;
    }

    public boolean isCharacterClass() {
        return this instanceof CharacterClass;
    }

    public boolean isGroup() {
        return this instanceof Group;
    }

    public boolean isLookAroundAssertion() {
        return this instanceof LookAroundAssertion;
    }

    public boolean isLookAheadAssertion() {
        return this instanceof LookAheadAssertion;
    }

    public boolean isLookBehindAssertion() {
        return this instanceof LookBehindAssertion;
    }

    public boolean isAtomicGroup() {
        return this instanceof AtomicGroup;
    }

    public boolean isMatchFound() {
        return this instanceof MatchFound;
    }

    public boolean isPositionAssertion() {
        return this instanceof PositionAssertion;
    }

    public boolean isQuantifiableTerm() {
        return this instanceof QuantifiableTerm;
    }

    public boolean isRoot() {
        return this instanceof RegexASTRootNode;
    }

    public boolean isSubtreeRoot() {
        return this instanceof RegexASTSubtreeRootNode;
    }

    public boolean isSequence() {
        return this instanceof Sequence;
    }

    public boolean isCaret() {
        return this.isPositionAssertion() && this.asPositionAssertion().isCaret();
    }

    public boolean isDollar() {
        return this.isPositionAssertion() && this.asPositionAssertion().isDollar();
    }

    public BackReference asBackReference() {
        return (BackReference)this;
    }

    public CharacterClass asCharacterClass() {
        return (CharacterClass)this;
    }

    public Group asGroup() {
        return (Group)this;
    }

    public LookAroundAssertion asLookAroundAssertion() {
        return (LookAroundAssertion)this;
    }

    public LookAheadAssertion asLookAheadAssertion() {
        return (LookAheadAssertion)this;
    }

    public LookBehindAssertion asLookBehindAssertion() {
        return (LookBehindAssertion)this;
    }

    public AtomicGroup asAtomicGroup() {
        return (AtomicGroup)this;
    }

    public RegexASTSubtreeRootNode asSubtreeRootNode() {
        return (RegexASTSubtreeRootNode)this;
    }

    public MatchFound asMatchFound() {
        return (MatchFound)this;
    }

    public PositionAssertion asPositionAssertion() {
        return (PositionAssertion)this;
    }

    public QuantifiableTerm asQuantifiableTerm() {
        return (QuantifiableTerm)this;
    }

    public Sequence asSequence() {
        return (Sequence)this;
    }

    protected JsonObject toJson(String typeName) {
        return Json.obj(Json.prop("id", this.id), Json.prop("type", typeName), Json.prop("parent", RegexASTNode.astNodeId(this.parent)), Json.prop("minPath", this.minPath), Json.prop("isPrefix", this.isPrefix()), Json.prop("isDead", this.isDead()));
    }
}

