
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
import com.oracle.truffle.regex.literal.LiteralRegexExecNodeGen;
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

@ImportStatic(value={TRegexGuards.class})
public abstract class LiteralRegexExecNode
extends RegexExecNode
implements JsonConvertible {
    @Node.Child
    LiteralRegexExecImplNode implNode;

    public LiteralRegexExecNode(RegexLanguage language, RegexAST ast, LiteralRegexExecImplNode implNode) {
        super(language, ast.getSource(), ast.getFlags().isUnicode());
        this.implNode = this.insert(implNode);
    }

    @Override
    protected final String getEngineLabel() {
        return "literal:" + this.implNode.getImplName() + "(" + this.implNode.getLiteral() + ")";
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public JsonValue toJson() {
        return Json.obj(Json.prop("method", this.implNode.getImplName()), Json.prop("literal", DebugUtil.escapeString(this.implNode.getLiteral())), Json.prop("factory", this.implNode.resultFactory));
    }

    @Override
    public abstract RegexResult execute(VirtualFrame var1, Object var2, int var3);

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

    @Specialization(guards={"neitherByteArrayNorString(input)"})
    RegexResult doTruffleObject(Object input, int fromIndex, @Cached(value="createClassProfile()") ValueProfile inputClassProfile) {
        return this.implNode.execute(inputClassProfile.profile(input), fromIndex, this.getEncoding(), false);
    }

    public static LiteralRegexExecNode create(RegexLanguage language, RegexAST ast, LiteralRegexExecImplNode implNode) {
        return LiteralRegexExecNodeGen.create(language, ast, implNode);
    }

    public static final class RegionMatches
    extends NonEmptyLiteralRegexExecNode {
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
            if (this.regionMatchesNode.execute(input, fromIndex, this.literal.getLiteralContent(tString), 0, this.literalLength, this.literal.getMaskContent(tString), encoding)) {
                return this.createFromStart(fromIndex);
            }
            return RegexResult.getNoMatchInstance();
        }
    }

    public static final class Equals
    extends NonEmptyLiteralRegexExecNode {
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
            if (fromIndex == 0 && this.equalsNode.execute(input, this.literal.getLiteralContent(tString), this.literal.getMaskContent(tString), encoding)) {
                return this.createFromStart(0);
            }
            return RegexResult.getNoMatchInstance();
        }
    }

    public static final class EndsWith
    extends NonEmptyLiteralRegexExecNode {
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
            if ((this.sticky ? fromIndex == matchStart : fromIndex <= matchStart) && this.endsWithNode.execute(input, this.literal.getLiteralContent(tString), this.literal.getMaskContent(tString), encoding)) {
                return this.createFromEnd(this.inputLength(input));
            }
            return RegexResult.getNoMatchInstance();
        }
    }

    public static final class StartsWith
    extends NonEmptyLiteralRegexExecNode {
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
            if (fromIndex == 0 && this.startsWithNode.execute(input, this.literal.getLiteralContent(tString), this.literal.getMaskContent(tString), encoding)) {
                return this.createFromStart(0);
            }
            return RegexResult.getNoMatchInstance();
        }
    }

    public static final class IndexOfString
    extends NonEmptyLiteralRegexExecNode {
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
            int start2 = this.indexOfStringNode.execute(input, fromIndex, this.inputLength(input), this.literal.getLiteralContent(tString), this.literal.getMaskContent(tString), encoding);
            if (start2 < 0) {
                return RegexResult.getNoMatchInstance();
            }
            return this.createFromStart(start2);
        }
    }

    static abstract class NonEmptyLiteralRegexExecNode
    extends LiteralRegexExecImplNode {
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

    public static final class EmptyEquals
    extends EmptyLiteralRegexExecNode {
        public EmptyEquals(PreCalcResultVisitor preCalcResultVisitor, boolean mustAdvance) {
            super(preCalcResultVisitor, mustAdvance);
        }

        @Override
        protected String getImplName() {
            return "emptyEquals";
        }

        @Override
        protected RegexResult execute(Object input, int fromIndex, Encodings.Encoding encoding, boolean tString) {
            assert (fromIndex <= this.inputLength(input));
            return this.inputLength(input) == 0 && !this.mustAdvance ? this.createFromStart(0) : RegexResult.getNoMatchInstance();
        }
    }

    public static final class EmptyEndsWith
    extends EmptyLiteralRegexExecNode {
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
            assert (fromIndex <= this.inputLength(input));
            if (this.sticky && fromIndex < this.inputLength(input) || this.mustAdvance && fromIndex == this.inputLength(input)) {
                return RegexResult.getNoMatchInstance();
            }
            return this.createFromEnd(this.inputLength(input));
        }
    }

    public static final class EmptyStartsWith
    extends EmptyLiteralRegexExecNode {
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

    public static final class EmptyIndexOf
    extends EmptyLiteralRegexExecNode {
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
                if (fromIndex < this.inputLength(input)) {
                    return this.createFromStart(fromIndex + 1);
                }
                return RegexResult.getNoMatchInstance();
            }
            return this.createFromStart(fromIndex);
        }
    }

    static abstract class EmptyLiteralRegexExecNode
    extends LiteralRegexExecImplNode {
        protected final boolean mustAdvance;

        EmptyLiteralRegexExecNode(PreCalcResultVisitor preCalcResultVisitor, boolean mustAdvance) {
            super(preCalcResultVisitor);
            this.mustAdvance = mustAdvance;
        }
    }

    static abstract class LiteralRegexExecImplNode
    extends Node {
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

        final RegexResult createFromStart(int start2) {
            return this.resultFactory == null ? RegexResult.getBooleanMatchInstance() : this.resultFactory.createFromStart(start2);
        }

        final RegexResult createFromEnd(int end2) {
            return this.resultFactory == null ? RegexResult.getBooleanMatchInstance() : this.resultFactory.createFromEnd(end2);
        }

        abstract RegexResult execute(Object var1, int var2, Encodings.Encoding var3, boolean var4);
    }
}

