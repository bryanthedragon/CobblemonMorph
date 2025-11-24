
package com.oracle.truffle.regex.tregex.parser.ast;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.regex.UnsupportedRegexException;
import com.oracle.truffle.regex.tregex.buffer.CompilationBuffer;
import com.oracle.truffle.regex.tregex.parser.ast.QuantifiableTerm;
import com.oracle.truffle.regex.tregex.parser.ast.RegexAST;
import com.oracle.truffle.regex.tregex.parser.ast.RegexASTNode;
import com.oracle.truffle.regex.tregex.parser.ast.Sequence;
import com.oracle.truffle.regex.tregex.parser.ast.Term;
import com.oracle.truffle.regex.tregex.parser.ast.visitors.RegexASTVisitorIterable;
import com.oracle.truffle.regex.tregex.util.json.Json;
import com.oracle.truffle.regex.tregex.util.json.JsonValue;
import java.util.ArrayList;
import java.util.stream.Collectors;

public final class Group
extends QuantifiableTerm
implements RegexASTVisitorIterable {
    private ArrayList<Sequence> alternatives = new ArrayList();
    private short visitorIterationIndex = 0;
    private short groupNumber = (short)-1;
    private short enclosedCaptureGroupsLow;
    private short enclosedCaptureGroupsHigh;

    Group() {
    }

    Group(int groupNumber) {
        this.setGroupNumber(groupNumber);
    }

    private Group(Group copy) {
        super(copy);
        this.groupNumber = copy.groupNumber;
        this.enclosedCaptureGroupsLow = copy.enclosedCaptureGroupsLow;
        this.enclosedCaptureGroupsHigh = copy.enclosedCaptureGroupsHigh;
    }

    @Override
    public Group copy(RegexAST ast) {
        return ast.register(new Group(this));
    }

    @Override
    public Group copyRecursive(RegexAST ast, CompilationBuffer compilationBuffer) {
        Group copy = this.copy(ast);
        for (Sequence s : this.alternatives) {
            copy.add(s.copyRecursive(ast, compilationBuffer));
        }
        return copy;
    }

    public boolean isLoop() {
        return this.isFlagSet(256);
    }

    public void setLoop(boolean loop) {
        this.setFlag(256, loop);
    }

    public int getGroupNumber() {
        return this.groupNumber;
    }

    public int getBoundaryIndexStart() {
        assert (this.isCapturing());
        return Group.groupNumberToBoundaryIndexStart(this.groupNumber);
    }

    public int getBoundaryIndexEnd() {
        assert (this.isCapturing());
        return Group.groupNumberToBoundaryIndexEnd(this.groupNumber);
    }

    public static int groupNumberToBoundaryIndexStart(int groupNumber) {
        return groupNumber * 2;
    }

    public static int groupNumberToBoundaryIndexEnd(int groupNumber) {
        return groupNumber * 2 + 1;
    }

    public boolean isCapturing() {
        return this.groupNumber >= 0;
    }

    public void setGroupNumber(int groupNumber) {
        assert (groupNumber <= Short.MAX_VALUE);
        this.groupNumber = (short)groupNumber;
    }

    public void clearGroupNumber() {
        this.groupNumber = (short)-1;
    }

    public int getEnclosedCaptureGroupsLow() {
        return this.enclosedCaptureGroupsLow;
    }

    public void setEnclosedCaptureGroupsLow(int enclosedCaptureGroupsLow) {
        assert (enclosedCaptureGroupsLow <= Short.MAX_VALUE);
        this.enclosedCaptureGroupsLow = (short)enclosedCaptureGroupsLow;
    }

    public int getEnclosedCaptureGroupsHigh() {
        return this.enclosedCaptureGroupsHigh;
    }

    public void setEnclosedCaptureGroupsHigh(int enclosedCaptureGroupsHigh) {
        assert (enclosedCaptureGroupsHigh <= Short.MAX_VALUE);
        this.enclosedCaptureGroupsHigh = (short)enclosedCaptureGroupsHigh;
    }

    public boolean hasEnclosedCaptureGroups() {
        return this.enclosedCaptureGroupsHigh > this.enclosedCaptureGroupsLow;
    }

    public boolean isAlwaysZeroWidth() {
        for (Sequence s : this.alternatives) {
            for (Term t : s.getTerms()) {
                if (t.isPositionAssertion() || t.isLookAroundAssertion() || t.isGroup() && t.asGroup().isAlwaysZeroWidth() || t.isAtomicGroup() && t.asAtomicGroup().getGroup().isAlwaysZeroWidth()) continue;
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean isUnrollingCandidate() {
        return this.hasQuantifier() && this.getQuantifier().isWithinThreshold(6);
    }

    public ArrayList<Sequence> getAlternatives() {
        return this.alternatives;
    }

    public void setAlternatives(ArrayList<Sequence> alternatives) {
        for (Sequence s : alternatives) {
            s.setParent(this);
        }
        this.alternatives = alternatives;
    }

    public Sequence getFirstAlternative() {
        return this.alternatives.get(0);
    }

    public int size() {
        return this.alternatives.size();
    }

    public boolean isEmpty() {
        return this.size() == 0;
    }

    public void add(Sequence sequence) {
        sequence.setParent(this);
        this.alternatives.add(sequence);
        this.checkMaxSize();
    }

    public void insertFirst(Sequence sequence) {
        sequence.setParent(this);
        this.alternatives.add(0, sequence);
        this.checkMaxSize();
    }

    private void checkMaxSize() {
        if (this.alternatives.size() > Short.MAX_VALUE) {
            throw new UnsupportedRegexException("too many sequences in a single group");
        }
    }

    public Sequence addSequence(RegexAST ast) {
        Sequence sequence = ast.createSequence();
        this.add(sequence);
        return sequence;
    }

    public Sequence getLastAlternative() {
        return this.alternatives.get(this.size() - 1);
    }

    public void removeLastSequence() {
        this.alternatives.remove(this.alternatives.size() - 1);
    }

    public boolean isLiteral() {
        return this.alternatives.size() == 1 && this.alternatives.get(0).isLiteral();
    }

    @Override
    public boolean visitorHasNext() {
        return this.visitorIterationIndex < this.alternatives.size();
    }

    @Override
    public RegexASTNode visitorGetNext(boolean reverse) {
        short s = this.visitorIterationIndex;
        this.visitorIterationIndex = (short)(s + 1);
        return this.alternatives.get(s);
    }

    @Override
    public void resetVisitorIterator() {
        this.visitorIterationIndex = 0;
    }

    @CompilerDirectives.TruffleBoundary
    public String alternativesToString() {
        return this.alternatives.stream().map(Sequence::toString).collect(Collectors.joining("|"));
    }

    public String loopToString() {
        return this.isLoop() ? "*" : this.quantifierToString();
    }

    @Override
    public boolean equalsSemantic(RegexASTNode obj, boolean ignoreQuantifier) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Group)) {
            return false;
        }
        Group o = (Group)obj;
        if (this.size() != o.size() || this.groupNumber != o.groupNumber || this.isLoop() != o.isLoop() || !ignoreQuantifier && !this.quantifierEquals(o)) {
            return false;
        }
        for (int i = 0; i < this.size(); ++i) {
            if (this.alternatives.get(i).equalsSemantic(o.alternatives.get(i))) continue;
            return false;
        }
        return true;
    }

    @CompilerDirectives.TruffleBoundary
    public String toString() {
        return "(" + (this.isCapturing() ? "" : "?:") + this.alternativesToString() + ")" + this.loopToString();
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public JsonValue toJson() {
        return this.toJson("Group").append(Json.prop("groupNumber", this.groupNumber), Json.prop("isCapturing", this.isCapturing()), Json.prop("isLoop", this.isLoop()), Json.prop("isExpandedLoop", this.isExpandedQuantifier()), Json.prop("alternatives", this.alternatives));
    }
}

