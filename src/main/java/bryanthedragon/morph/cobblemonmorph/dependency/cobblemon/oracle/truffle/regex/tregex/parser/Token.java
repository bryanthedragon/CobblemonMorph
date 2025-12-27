package com.oracle.truffle.regex.tregex.parser;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.source.SourceSection;
import com.oracle.truffle.regex.charset.CodePointSet;
import com.oracle.truffle.regex.tregex.util.json.Json;
import com.oracle.truffle.regex.tregex.util.json.JsonConvertible;
import com.oracle.truffle.regex.tregex.util.json.JsonObject;
import java.util.Objects;

public class Token implements JsonConvertible {
   private static final Token CARET = new Token(Token.Kind.caret);
   private static final Token DOLLAR = new Token(Token.Kind.dollar);
   private static final Token WORD_BOUNDARY = new Token(Token.Kind.wordBoundary);
   private static final Token NON_WORD_BOUNDARY = new Token(Token.Kind.nonWordBoundary);
   private static final Token ALTERNATION = new Token(Token.Kind.alternation);
   private static final Token CAPTURE_GROUP_BEGIN = new Token(Token.Kind.captureGroupBegin);
   private static final Token NON_CAPTURE_GROUP_BEGIN = new Token(Token.Kind.nonCaptureGroupBegin);
   private static final Token LOOK_AHEAD_ASSERTION_BEGIN = new Token.LookAheadAssertionBegin(false);
   private static final Token NEGATIVE_LOOK_AHEAD_ASSERTION_BEGIN = new Token.LookAheadAssertionBegin(true);
   private static final Token LOOK_BEHIND_ASSERTION_BEGIN = new Token.LookBehindAssertionBegin(false);
   private static final Token NEGATIVE_LOOK_BEHIND_ASSERTION_BEGIN = new Token.LookBehindAssertionBegin(true);
   private static final Token GROUP_END = new Token(Token.Kind.groupEnd);
   public final Token.Kind kind;
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

   public static Token.BackReference createBackReference(int groupNr) {
      return new Token.BackReference(groupNr);
   }

   public static Token.Quantifier createQuantifier(int min, int max, boolean greedy) {
      return new Token.Quantifier(min, max, greedy);
   }

   public static Token.CharacterClass createCharClass(CodePointSet codePointSet) {
      return new Token.CharacterClass(codePointSet, false);
   }

   public static Token.CharacterClass createCharClass(CodePointSet codePointSet, boolean wasSingleChar) {
      return new Token.CharacterClass(codePointSet, wasSingleChar);
   }

   public static Token createLookAheadAssertionBegin(boolean negated) {
      return negated ? NEGATIVE_LOOK_AHEAD_ASSERTION_BEGIN : LOOK_AHEAD_ASSERTION_BEGIN;
   }

   public static Token createLookBehindAssertionBegin(boolean negated) {
      return negated ? NEGATIVE_LOOK_BEHIND_ASSERTION_BEGIN : LOOK_BEHIND_ASSERTION_BEGIN;
   }

   public Token(Token.Kind kind) {
      this.kind = kind;
   }

   public SourceSection getSourceSection() {
      return this.sourceSection;
   }

   public void setSourceSection(SourceSection sourceSection) {
      this.sourceSection = sourceSection;
   }

   @CompilerDirectives.TruffleBoundary
   public JsonObject toJson() {
      return Json.obj(Json.prop("kind", this.kind.name()));
   }

   public static final class BackReference extends Token {
      private final int groupNr;

      public BackReference(int groupNr) {
         super(Token.Kind.backReference);
         this.groupNr = groupNr;
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public JsonObject toJson() {
         return super.toJson().append(Json.prop("groupNr", this.groupNr));
      }

      public int getGroupNr() {
         return this.groupNr;
      }
   }

   public static final class CharacterClass extends Token {
      private final CodePointSet codePointSet;
      private final boolean wasSingleChar;

      public CharacterClass(CodePointSet codePointSet, boolean wasSingleChar) {
         super(Token.Kind.charClass);
         this.codePointSet = codePointSet;
         this.wasSingleChar = wasSingleChar;
      }

      @CompilerDirectives.TruffleBoundary
      @Override
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

   public static final class LookAheadAssertionBegin extends Token.LookAroundAssertionBegin {
      public LookAheadAssertionBegin(boolean negated) {
         super(Token.Kind.lookAheadAssertionBegin, negated);
      }
   }

   public static class LookAroundAssertionBegin extends Token {
      private final boolean negated;

      protected LookAroundAssertionBegin(Token.Kind kind, boolean negated) {
         super(kind);
         this.negated = negated;
      }

      public boolean isNegated() {
         return this.negated;
      }
   }

   public static final class LookBehindAssertionBegin extends Token.LookAroundAssertionBegin {
      public LookBehindAssertionBegin(boolean negated) {
         super(Token.Kind.lookBehindAssertionBegin, negated);
      }
   }

   public static final class Quantifier extends Token {
      private final int min;
      private final int max;
      private final boolean greedy;
      @CompilerDirectives.CompilationFinal
      private int index = -1;
      @CompilerDirectives.CompilationFinal
      private int zeroWidthIndex = -1;

      public Quantifier(int min, int max, boolean greedy) {
         super(Token.Kind.quantifier);
         this.min = min;
         this.max = max;
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

      @Override
      public int hashCode() {
         return Objects.hash(this.min, this.max, this.greedy, this.index, this.zeroWidthIndex);
      }

      public boolean equalsSemantic(Token.Quantifier o) {
         return this.min == o.min && this.max == o.max && this.greedy == o.greedy;
      }

      @Override
      public boolean equals(Object obj) {
         if (this == obj) {
            return true;
         } else if (!(obj instanceof Token.Quantifier)) {
            return false;
         } else {
            Token.Quantifier o = (Token.Quantifier)obj;
            return this.min == o.min && this.max == o.max && this.greedy == o.greedy && this.index == o.index && this.zeroWidthIndex == o.zeroWidthIndex;
         }
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public String toString() {
         String ret = this.minMaxToString();
         return this.isGreedy() ? ret : ret + "?";
      }

      private String minMaxToString() {
         if (this.min == 0 && this.max == 1) {
            return "?";
         } else if (this.min == 0 && this.isInfiniteLoop()) {
            return "*";
         } else {
            return this.min == 1 && this.isInfiniteLoop() ? "+" : String.format("{%d,%s}", this.min, this.isInfiniteLoop() ? "" : String.valueOf(this.max));
         }
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public JsonObject toJson() {
         return super.toJson().append(Json.prop("min", this.getMin()), Json.prop("max", this.getMax()), Json.prop("greedy", this.isGreedy()));
      }
   }
}
