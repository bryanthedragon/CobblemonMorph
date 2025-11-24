
package com.oracle.truffle.regex.tregex.parser.ast;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.regex.tregex.buffer.CompilationBuffer;
import com.oracle.truffle.regex.tregex.buffer.ObjectArrayBuffer;
import com.oracle.truffle.regex.tregex.parser.ast.Group;
import com.oracle.truffle.regex.tregex.parser.ast.RegexAST;
import com.oracle.truffle.regex.tregex.parser.ast.RegexASTNode;
import com.oracle.truffle.regex.tregex.parser.ast.RegexASTSubtreeRootNode;
import com.oracle.truffle.regex.tregex.parser.ast.Term;
import com.oracle.truffle.regex.tregex.parser.ast.visitors.RegexASTVisitorIterable;
import com.oracle.truffle.regex.tregex.util.json.Json;
import com.oracle.truffle.regex.tregex.util.json.JsonValue;
import java.util.ArrayList;
import java.util.stream.Collectors;

public final class Sequence
extends RegexASTNode
implements RegexASTVisitorIterable {
    private final ArrayList<Term> terms = new ArrayList();
    private short visitorIterationIndex = 0;

    Sequence() {
    }

    private Sequence(Sequence copy) {
        super(copy);
    }

    @Override
    public Sequence copy(RegexAST ast) {
        return ast.register(new Sequence(this));
    }

    @Override
    public Sequence copyRecursive(RegexAST ast, CompilationBuffer compilationBuffer) {
        Sequence copy = this.copy(ast);
        for (Term t : this.terms) {
            copy.add(t.copyRecursive(ast, compilationBuffer));
        }
        return copy;
    }

    @Override
    public Group getParent() {
        return (Group)super.getParent();
    }

    @Override
    public void setParent(RegexASTNode parent) {
        assert (parent instanceof Group);
        super.setParent(parent);
    }

    public ArrayList<Term> getTerms() {
        return this.terms;
    }

    public boolean isEmpty() {
        return this.terms.isEmpty();
    }

    public int size() {
        return this.terms.size();
    }

    public Term getFirstTerm() {
        return this.terms.get(0);
    }

    public Term get(int i) {
        return this.terms.get(i);
    }

    public Term getLastTerm() {
        return this.terms.get(this.terms.size() - 1);
    }

    public void add(Term term) {
        term.setParent(this);
        term.setSeqIndex(this.terms.size());
        this.terms.add(term);
    }

    public void replace(int index, Term term) {
        term.setParent(this);
        term.setSeqIndex(index);
        this.terms.set(index, term);
    }

    public void removeTerm(int i, CompilationBuffer compilationBuffer) {
        int j;
        ObjectArrayBuffer<Term> buf = compilationBuffer.getObjectBuffer1();
        int size = this.size();
        for (j = i + 1; j < size; ++j) {
            buf.add(this.getLastTerm());
            this.removeLastTerm();
        }
        this.removeLastTerm();
        for (j = buf.length() - 1; j >= 0; --j) {
            this.add((Term)buf.get(j));
        }
    }

    public void removeLastTerm() {
        this.terms.remove(this.terms.size() - 1);
    }

    public boolean isFirstInGroup() {
        return this.getParent().getFirstAlternative() == this;
    }

    public boolean isLastInGroup() {
        return this.getParent().getAlternatives().get(this.getParent().getAlternatives().size() - 1) == this;
    }

    public boolean isPenultimateInGroup() {
        ArrayList<Sequence> alt = this.getParent().getAlternatives();
        return alt.size() > 1 && alt.get(alt.size() - 2) == this;
    }

    public boolean isLiteral() {
        if (this.isEmpty()) {
            return false;
        }
        for (Term t : this.terms) {
            if (t.isCharacterClass() && !t.asCharacterClass().hasNotUnrolledQuantifier()) continue;
            return false;
        }
        return true;
    }

    public boolean isSingleCharClass() {
        return this.size() == 1 && this.isLiteral();
    }

    public int getEnclosedCaptureGroupsLow() {
        int lo = Integer.MAX_VALUE;
        for (Term t : this.terms) {
            if (!(t instanceof Group)) continue;
            Group g = (Group)t;
            if (g.getEnclosedCaptureGroupsLow() != g.getEnclosedCaptureGroupsHigh()) {
                lo = Math.min(lo, g.getEnclosedCaptureGroupsLow());
            }
            if (!g.isCapturing()) continue;
            lo = Math.min(lo, g.getGroupNumber());
        }
        return lo == Integer.MAX_VALUE ? -1 : lo;
    }

    public int getEnclosedCaptureGroupsHigh() {
        int hi = Integer.MIN_VALUE;
        for (Term t : this.terms) {
            if (!(t instanceof Group)) continue;
            Group g = (Group)t;
            if (g.getEnclosedCaptureGroupsLow() != g.getEnclosedCaptureGroupsHigh()) {
                hi = Math.max(hi, g.getEnclosedCaptureGroupsHigh());
            }
            if (!g.isCapturing()) continue;
            hi = Math.max(hi, g.getGroupNumber() + 1);
        }
        return hi == Integer.MIN_VALUE ? -1 : hi;
    }

    @Override
    public RegexASTSubtreeRootNode getSubTreeParent() {
        return this.getParent().getSubTreeParent();
    }

    @Override
    public boolean visitorHasNext() {
        return this.visitorIterationIndex < this.terms.size();
    }

    @Override
    public void resetVisitorIterator() {
        this.visitorIterationIndex = 0;
    }

    @Override
    public RegexASTNode visitorGetNext(boolean reverse) {
        if (reverse) {
            this.visitorIterationIndex = (short)(this.visitorIterationIndex + 1);
            return this.terms.get(this.terms.size() - this.visitorIterationIndex);
        }
        short s = this.visitorIterationIndex;
        this.visitorIterationIndex = (short)(s + 1);
        return this.terms.get(s);
    }

    @Override
    public boolean equalsSemantic(RegexASTNode obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Sequence)) {
            return false;
        }
        Sequence o = (Sequence)obj;
        if (this.size() != o.size()) {
            return false;
        }
        for (int i = 0; i < this.size(); ++i) {
            if (this.terms.get(i).equalsSemantic(o.terms.get(i))) continue;
            return false;
        }
        return true;
    }

    @CompilerDirectives.TruffleBoundary
    public String toString() {
        return this.terms.stream().map(Object::toString).collect(Collectors.joining(""));
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public JsonValue toJson() {
        return this.toJson("Sequence").append(Json.prop("terms", this.terms));
    }
}

