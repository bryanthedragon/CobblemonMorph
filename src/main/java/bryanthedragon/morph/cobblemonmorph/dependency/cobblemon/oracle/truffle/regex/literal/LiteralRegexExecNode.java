package com.oracle.truffle.regex.literal;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.ImportStatic;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.profiles.ValueProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.regex.RegexExecNode;
import com.oracle.truffle.regex.RegexLanguage;
import com.oracle.truffle.regex.result.PreCalculatedResultFactory;
import com.oracle.truffle.regex.result.RegexResult;
import com.oracle.truffle.regex.tregex.nodes.input.InputEndsWithNode;
import com.oracle.truffle.regex.tregex.nodes.input.InputEqualsNode;
import com.oracle.truffle.regex.tregex.nodes.input.InputIndexOfStringNode;
import com.oracle.truffle.regex.tregex.nodes.input.InputRegionMatchesNode;
import com.oracle.truffle.regex.tregex.nodes.input.InputStartsWithNode;
import com.oracle.truffle.regex.tregex.parser.ast.InnerLiteral;
import com.oracle.truffle.regex.tregex.parser.ast.RegexAST;
import com.oracle.truffle.regex.tregex.parser.ast.visitors.PreCalcResultVisitor;
import com.oracle.truffle.regex.tregex.string.Encodings;
import com.oracle.truffle.regex.tregex.util.DebugUtil;
import com.oracle.truffle.regex.tregex.util.json.Json;
import com.oracle.truffle.regex.tregex.util.json.JsonConvertible;
import com.oracle.truffle.regex.tregex.util.json.JsonValue;
import com.oracle.truffle.regex.util.TRegexGuards;

@ImportStatic(TRegexGuards.class)
public abstract class LiteralRegexExecNode extends RegexExecNode implements JsonConvertible {
   @Node.Child
   LiteralRegexExecNode.LiteralRegexExecImplNode implNode;

   public LiteralRegexExecNode(RegexLanguage language, RegexAST ast, LiteralRegexExecNode.LiteralRegexExecImplNode implNode) {
      super(language, ast.getSource(), ast.getFlags().isUnicode());
      this.implNode = this.insert(implNode);
   }

   @Override
   protected final String getEngineLabel() {
      return "literal:" + this.implNode.getImplName() + "(" + this.implNode.getLiteral() + ")";
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public JsonValue toJson() {
      return Json.obj(
         Json.prop("method", this.implNode.getImplName()),
         Json.prop("literal", DebugUtil.escapeString(this.implNode.getLiteral())),
         Json.prop("factory", this.implNode.resultFactory)
      );
   }

   @Override
   public abstract RegexResult execute(VirtualFrame frame, Object input, int fromIndex);

   @Specialization
   RegexResult doByteArray(byte[] input, int fromIndex) {
      return this.implNode.execute(input, fromIndex, this.getEncoding(), false);
   }

   @Specialization
   RegexResult doString(String input, int fromIndex) {
      return this.implNode.execute(input, fromIndex, this.getEncoding(), false);
   }

   @Specialization
   RegexResult doTString(TruffleString input, int fromIndex, @Cached TruffleString.MaterializeNode materializeNode) {
      materializeNode.execute(input, this.getEncoding().getTStringEncoding());
      return this.implNode.execute(input, fromIndex, this.getEncoding(), true);
   }

   @Specialization(guards = "neitherByteArrayNorString(input)")
   RegexResult doTruffleObject(Object input, int fromIndex, @Cached("createClassProfile()") ValueProfile inputClassProfile) {
      return this.implNode.execute(inputClassProfile.profile(input), fromIndex, this.getEncoding(), false);
   }

   public static LiteralRegexExecNode create(RegexLanguage language, RegexAST ast, LiteralRegexExecNode.LiteralRegexExecImplNode implNode) {
      return LiteralRegexExecNodeGen.create(language, ast, implNode);
   }

   public static final class EmptyEndsWith extends LiteralRegexExecNode.EmptyLiteralRegexExecNode {
      private final boolean sticky;

      public EmptyEndsWith(PreCalcResultVisitor preCalcResultVisitor, boolean sticky, boolean mustAdvance) {
         super(preCalcResultVisitor, mustAdvance);
         this.sticky = sticky;
      }

      @Override
      protected String getImplName() {
         return "emptyEndsWith";
      }

      @Override
      protected RegexResult execute(Object input, int fromIndex, Encodings.Encoding encoding, boolean tString) {
         assert fromIndex <= this.inputLength(input);

         return (!this.sticky || fromIndex >= this.inputLength(input)) && (!this.mustAdvance || fromIndex != this.inputLength(input))
            ? this.createFromEnd(this.inputLength(input))
            : RegexResult.getNoMatchInstance();
      }
   }

   public static final class EmptyEquals extends LiteralRegexExecNode.EmptyLiteralRegexExecNode {
      public EmptyEquals(PreCalcResultVisitor preCalcResultVisitor, boolean mustAdvance) {
         super(preCalcResultVisitor, mustAdvance);
      }

      @Override
      protected String getImplName() {
         return "emptyEquals";
      }

      @Override
      protected RegexResult execute(Object input, int fromIndex, Encodings.Encoding encoding, boolean tString) {
         assert fromIndex <= this.inputLength(input);

         return this.inputLength(input) == 0 && !this.mustAdvance ? this.createFromStart(0) : RegexResult.getNoMatchInstance();
      }
   }

   public static final class EmptyIndexOf extends LiteralRegexExecNode.EmptyLiteralRegexExecNode {
      public EmptyIndexOf(PreCalcResultVisitor preCalcResultVisitor, boolean mustAdvance) {
         super(preCalcResultVisitor, mustAdvance);
      }

      @Override
      protected String getImplName() {
         return "emptyIndexOf";
      }

      @Override
      protected RegexResult execute(Object input, int fromIndex, Encodings.Encoding encoding, boolean tString) {
         if (this.mustAdvance) {
            return fromIndex < this.inputLength(input) ? this.createFromStart(fromIndex + 1) : RegexResult.getNoMatchInstance();
         } else {
            return this.createFromStart(fromIndex);
         }
      }
   }

   abstract static class EmptyLiteralRegexExecNode extends LiteralRegexExecNode.LiteralRegexExecImplNode {
      protected final boolean mustAdvance;

      EmptyLiteralRegexExecNode(PreCalcResultVisitor preCalcResultVisitor, boolean mustAdvance) {
         super(preCalcResultVisitor);
         this.mustAdvance = mustAdvance;
      }
   }

   public static final class EmptyStartsWith extends LiteralRegexExecNode.EmptyLiteralRegexExecNode {
      public EmptyStartsWith(PreCalcResultVisitor preCalcResultVisitor, boolean mustAdvance) {
         super(preCalcResultVisitor, mustAdvance);
      }

      @Override
      protected String getImplName() {
         return "emptyStartsWith";
      }

      @Override
      protected RegexResult execute(Object input, int fromIndex, Encodings.Encoding encoding, boolean tString) {
         return fromIndex == 0 && !this.mustAdvance ? this.createFromStart(0) : RegexResult.getNoMatchInstance();
      }
   }

   public static final class EndsWith extends LiteralRegexExecNode.NonEmptyLiteralRegexExecNode {
      private final boolean sticky;
      @Node.Child
      InputEndsWithNode endsWithNode = InputEndsWithNode.create();

      public EndsWith(PreCalcResultVisitor preCalcResultVisitor, boolean sticky) {
         super(preCalcResultVisitor);
         this.sticky = sticky;
      }

      @Override
      protected String getImplName() {
         return "endsWith";
      }

      @Override
      protected RegexResult execute(Object input, int fromIndex, Encodings.Encoding encoding, boolean tString) {
         int matchStart = this.inputLength(input) - this.literalLength;
         return (this.sticky ? fromIndex == matchStart : fromIndex <= matchStart)
               && this.endsWithNode.execute(input, this.literal.getLiteralContent(tString), this.literal.getMaskContent(tString), encoding)
            ? this.createFromEnd(this.inputLength(input))
            : RegexResult.getNoMatchInstance();
      }
   }

   public static final class Equals extends LiteralRegexExecNode.NonEmptyLiteralRegexExecNode {
      @Node.Child
      InputEqualsNode equalsNode = InputEqualsNode.create();

      public Equals(PreCalcResultVisitor preCalcResultVisitor) {
         super(preCalcResultVisitor);
      }

      @Override
      protected String getImplName() {
         return "equals";
      }

      @Override
      protected RegexResult execute(Object input, int fromIndex, Encodings.Encoding encoding, boolean tString) {
         return fromIndex == 0 && this.equalsNode.execute(input, this.literal.getLiteralContent(tString), this.literal.getMaskContent(tString), encoding)
            ? this.createFromStart(0)
            : RegexResult.getNoMatchInstance();
      }
   }

   public static final class IndexOfString extends LiteralRegexExecNode.NonEmptyLiteralRegexExecNode {
      @Node.Child
      InputIndexOfStringNode indexOfStringNode = InputIndexOfStringNode.create();

      public IndexOfString(PreCalcResultVisitor preCalcResultVisitor) {
         super(preCalcResultVisitor);
      }

      @Override
      protected String getImplName() {
         return "indexOfString";
      }

      @Override
      protected RegexResult execute(Object input, int fromIndex, Encodings.Encoding encoding, boolean tString) {
         int start = this.indexOfStringNode
            .execute(input, fromIndex, this.inputLength(input), this.literal.getLiteralContent(tString), this.literal.getMaskContent(tString), encoding);
         return start < 0 ? RegexResult.getNoMatchInstance() : this.createFromStart(start);
      }
   }

   abstract static class LiteralRegexExecImplNode extends Node {
      private final PreCalculatedResultFactory resultFactory;

      protected LiteralRegexExecImplNode(PreCalcResultVisitor preCalcResultVisitor) {
         this.resultFactory = preCalcResultVisitor.isBooleanMatch() ? null : preCalcResultVisitor.getResultFactory();
      }

      abstract String getImplName();

      String getLiteral() {
         return "";
      }

      final int inputLength(Object input) {
         return ((RegexExecNode)this.getParent()).inputLength(input);
      }

      final RegexResult createFromStart(int start) {
         return this.resultFactory == null ? RegexResult.getBooleanMatchInstance() : this.resultFactory.createFromStart(start);
      }

      final RegexResult createFromEnd(int end) {
         return this.resultFactory == null ? RegexResult.getBooleanMatchInstance() : this.resultFactory.createFromEnd(end);
      }

      abstract RegexResult execute(Object input, int fromIndex, Encodings.Encoding encoding, boolean tString);
   }

   abstract static class NonEmptyLiteralRegexExecNode extends LiteralRegexExecNode.LiteralRegexExecImplNode {
      protected final int literalLength;
      protected final InnerLiteral literal;

      NonEmptyLiteralRegexExecNode(PreCalcResultVisitor preCalcResultVisitor) {
         super(preCalcResultVisitor);
         this.literalLength = preCalcResultVisitor.getLiteral().encodedLength();
         this.literal = new InnerLiteral(preCalcResultVisitor.getLiteral(), preCalcResultVisitor.getMask(), 0);
      }

      @Override
      protected String getLiteral() {
         return this.literal.getLiteral().toString();
      }
   }

   public static final class RegionMatches extends LiteralRegexExecNode.NonEmptyLiteralRegexExecNode {
      @Node.Child
      InputRegionMatchesNode regionMatchesNode = InputRegionMatchesNode.create();

      public RegionMatches(PreCalcResultVisitor preCalcResultVisitor) {
         super(preCalcResultVisitor);
      }

      @Override
      protected String getImplName() {
         return "regionMatches";
      }

      @Override
      protected RegexResult execute(Object input, int fromIndex, Encodings.Encoding encoding, boolean tString) {
         return this.regionMatchesNode
               .execute(input, fromIndex, this.literal.getLiteralContent(tString), 0, this.literalLength, this.literal.getMaskContent(tString), encoding)
            ? this.createFromStart(fromIndex)
            : RegexResult.getNoMatchInstance();
      }
   }

   public static final class StartsWith extends LiteralRegexExecNode.NonEmptyLiteralRegexExecNode {
      @Node.Child
      InputStartsWithNode startsWithNode = InputStartsWithNode.create();

      public StartsWith(PreCalcResultVisitor preCalcResultVisitor) {
         super(preCalcResultVisitor);
      }

      @Override
      protected String getImplName() {
         return "startsWith";
      }

      @Override
      protected RegexResult execute(Object input, int fromIndex, Encodings.Encoding encoding, boolean tString) {
         return fromIndex == 0 && this.startsWithNode.execute(input, this.literal.getLiteralContent(tString), this.literal.getMaskContent(tString), encoding)
            ? this.createFromStart(0)
            : RegexResult.getNoMatchInstance();
      }
   }
}
