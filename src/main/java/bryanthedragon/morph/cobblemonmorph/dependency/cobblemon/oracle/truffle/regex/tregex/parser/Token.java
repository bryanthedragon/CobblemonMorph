
package com.oracle.truffle.regex.tregex.parser;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.source.SourceSection;
import com.oracle.truffle.regex.charset.CodePointSet;
import com.oracle.truffle.regex.tregex.util.json.Json;
import com.oracle.truffle.regex.tregex.util.json.JsonConvertible;
import com.oracle.truffle.regex.tregex.util.json.JsonObject;
import java.util.Objects;

public class Token
implements JsonConvertible {
    private static final Token CARET = new Token(Kind.caret);
    private static final Token DOLLAR = new Token(Kind.dollar);
    private static final Token WORD_BOUNDARY = new Token(Kind.wordBoundary);
    private static final Token NON_WORD_BOUNDARY = new Token(Kind.nonWordBoundary);
    private static final Token ALTERNATION = new Token(Kind.alternation);
    private static final Token CAPTURE_GROUP_BEGIN = new Token(Kind.captureGroupBegin);
    private static final Token NON_CAPTURE_GROUP_BEGIN = new Token(Kind.nonCaptureGroupBegin);
    private static final Token LOOK_AHEAD_ASSERTION_BEGIN = new LookAheadAssertionBegin(false);
    private static final Token NEGATIVE_LOOK_AHEAD_ASSERTION_BEGIN = new LookAheadAssertionBegin(true);
    private static final Token LOOK_BEHIND_ASSERTION_BEGIN = new LookBehindAssertionBegin(false);
    private static final Token NEGATIVE_LOOK_BEHIND_ASSERTION_BEGIN = new LookBehindAssertionBegin(true);
    private static final Token GROUP_END = new Token(Kind.groupEnd);
    public final Kind kind;
    private SourceSection sourceSection;

    public static Token createCaret() {
        return CARET;
    }

    public static Token createDollar() {
        return DOLLAR;
    }

    public static Token createWordBoundary() {
        return WORD_BOUNDARY;
    }

    public static Token createNonWordBoundary() {
        return NON_WORD_BOUNDARY;
    }

    public static Token createAlternation() {
        return ALTERNATION;
    }

    public static Token createCaptureGroupBegin() {
        return CAPTURE_GROUP_BEGIN;
    }

    public static Token createNonCaptureGroupBegin() {
        return NON_CAPTURE_GROUP_BEGIN;
    }

    public static Token createLookAheadAssertionBegin() {
        return LOOK_AHEAD_ASSERTION_BEGIN;
    }

    public static Token createLookBehindAssertionBegin() {
        return LOOK_BEHIND_ASSERTION_BEGIN;
    }

    public static Token createGroupEnd() {
        return GROUP_END;
    }

    public static BackReference createBackReference(int groupNr) {
        return new BackReference(groupNr);
    }

    public static Quantifier createQuantifier(int min2, int max2, boolean greedy) {
        return new Quantifier(min2, max2, greedy);
    }

    public static CharacterClass createCharClass(CodePointSet codePointSet) {
        return new CharacterClass(codePointSet, false);
    }

    public static CharacterClass createCharClass(CodePointSet codePointSet, boolean wasSingleChar) {
        return new CharacterClass(codePointSet, wasSingleChar);
    }

    public static Token createLookAheadAssertionBegin(boolean negated) {
        return negated ? NEGATIVE_LOOK_AHEAD_ASSERTION_BEGIN : LOOK_AHEAD_ASSERTION_BEGIN;
    }

    public static Token createLookBehindAssertionBegin(boolean negated) {
        return negated ? NEGATIVE_LOOK_BEHIND_ASSERTION_BEGIN : LOOK_BEHIND_ASSERTION_BEGIN;
    }

    public Token(Kind kind) {
        this.kind = kind;
    }

    public SourceSection getSourceSection() {
        return this.sourceSection;
    }

    public void setSourceSection(SourceSection sourceSection) {
        this.sourceSection = sourceSection;
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public JsonObject toJson() {
        return Json.obj(Json.prop("kind", this.kind.name()));
    }

    public static final class LookBehindAssertionBegin
    extends LookAroundAssertionBegin {
        public LookBehindAssertionBegin(boolean negated) {
            super(Kind.lookBehindAssertionBegin, negated);
        }
    }

    public static final class LookAheadAssertionBegin
    extends LookAroundAssertionBegin {
        public LookAheadAssertionBegin(boolean negated) {
            super(Kind.lookAheadAssertionBegin, negated);
        }
    }

    public static class LookAroundAssertionBegin
    extends Token {
        private final boolean negated;

        protected LookAroundAssertionBegin(Kind kind, boolean negated) {
            super(kind);
            this.negated = negated;
        }

        public boolean isNegated() {
            return this.negated;
        }
    }

    public static final class BackReference
    extends Token {
        private final int groupNr;

        public BackReference(int groupNr) {
            super(Kind.backReference);
            this.groupNr = groupNr;
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public JsonObject toJson() {
            return super.toJson().append(Json.prop("groupNr", this.groupNr));
        }

        public int getGroupNr() {
            return this.groupNr;
        }
    }

    public static final class CharacterClass
    extends Token {
        private final CodePointSet codePointSet;
        private final boolean wasSingleChar;

        public CharacterClass(CodePointSet codePointSet, boolean wasSingleChar) {
            super(Kind.charClass);
            this.codePointSet = codePointSet;
            this.wasSingleChar = wasSingleChar;
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public JsonObject toJson() {
            return super.toJson().append(Json.prop("codePointSet", this.codePointSet));
        }

        public CodePointSet getCodePointSet() {
            return this.codePointSet;
        }

        public boolean wasSingleChar() {
            return this.wasSingleChar;
        }
    }

    public static final class Quantifier
    extends Token {
        private final int min;
        private final int max;
        private final boolean greedy;
        @CompilerDirectives.CompilationFinal
        private int index = -1;
        @CompilerDirectives.CompilationFinal
        private int zeroWidthIndex = -1;

        public Quantifier(int min2, int max2, boolean greedy) {
            super(Kind.quantifier);
            this.min = min2;
            this.max = max2;
            this.greedy = greedy;
        }

        public boolean isInfiniteLoop() {
            return this.getMax() == -1;
        }

        public int getMin() {
            return this.min;
        }

        public int getMax() {
            return this.max;
        }

        public boolean isGreedy() {
            return this.greedy;
        }

        public boolean hasIndex() {
            return this.index >= 0;
        }

        public int getIndex() {
            return this.index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public boolean hasZeroWidthIndex() {
            return this.zeroWidthIndex >= 0;
        }

        public int getZeroWidthIndex() {
            return this.zeroWidthIndex;
        }

        public void setZeroWidthIndex(int zeroWidthIndex) {
            this.zeroWidthIndex = zeroWidthIndex;
        }

        public boolean isWithinThreshold(int threshold) {
            return this.min <= threshold && this.max <= threshold;
        }

        public boolean isUnrollTrivial() {
            return this.min == 0 && this.max <= 1;
        }

        public int hashCode() {
            return Objects.hash(this.min, this.max, this.greedy, this.index, this.zeroWidthIndex);
        }

        public boolean equalsSemantic(Quantifier o) {
            return this.min == o.min && this.max == o.max && this.greedy == o.greedy;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Quantifier)) {
                return false;
            }
            Quantifier o = (Quantifier)obj;
            return this.min == o.min && this.max == o.max && this.greedy == o.greedy && this.index == o.index && this.zeroWidthIndex == o.zeroWidthIndex;
        }

        @CompilerDirectives.TruffleBoundary
        public String toString() {
            String ret = this.minMaxToString();
            return this.isGreedy() ? ret : ret + "?";
        }

        private String minMaxToString() {
            if (this.min == 0 && this.max == 1) {
                return "?";
            }
            if (this.min == 0 && this.isInfiniteLoop()) {
                return "*";
            }
            if (this.min == 1 && this.isInfiniteLoop()) {
                return "+";
            }
            return String.format("{%d,%s}", this.min, this.isInfiniteLoop() ? "" : String.valueOf(this.max));
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public JsonObject toJson() {
            return super.toJson().append(Json.prop("min", this.getMin()), Json.prop("max", this.getMax()), Json.prop("greedy", this.isGreedy()));
        }
    }

    public static enum Kind {
        caret,
        dollar,
        wordBoundary,
        nonWordBoundary,
        backReference,
        quantifier,
        alternation,
        captureGroupBegin,
        nonCaptureGroupBegin,
        lookAheadAssertionBegin,
        lookBehindAssertionBegin,
        groupEnd,
        charClass;

    }
}

