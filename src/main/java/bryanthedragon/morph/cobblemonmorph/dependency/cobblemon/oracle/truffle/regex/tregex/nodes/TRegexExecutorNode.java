
package com.oracle.truffle.regex.tregex.nodes;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.regex.RegexSource;
import com.oracle.truffle.regex.tregex.nodes.TRegexExecutorBaseNode;
import com.oracle.truffle.regex.tregex.nodes.TRegexExecutorLocals;
import com.oracle.truffle.regex.tregex.nodes.input.InputLengthNode;
import com.oracle.truffle.regex.tregex.nodes.input.InputReadNode;
import com.oracle.truffle.regex.tregex.parser.ast.RegexAST;
import com.oracle.truffle.regex.tregex.string.Encodings;

public abstract class TRegexExecutorNode
extends TRegexExecutorBaseNode {
    private final RegexSource source;
    private final int numberOfCaptureGroups;
    private final int numberOfTransitions;
    @Node.Child
    private InputLengthNode lengthNode;
    @Node.Child
    private InputReadNode charAtNode;
    private final BranchProfile bmpProfile = BranchProfile.create();
    private final BranchProfile astralProfile = BranchProfile.create();

    protected TRegexExecutorNode(RegexAST ast, int numberOfTransitions) {
        this(ast.getSource(), ast.getNumberOfCaptureGroups(), numberOfTransitions);
    }

    protected TRegexExecutorNode(TRegexExecutorNode copy) {
        this(copy.source, copy.numberOfCaptureGroups, copy.numberOfTransitions);
    }

    protected TRegexExecutorNode(RegexSource source, int numberOfCaptureGroups, int numberOfTransitions) {
        this.source = source;
        this.numberOfCaptureGroups = numberOfCaptureGroups;
        this.numberOfTransitions = numberOfTransitions;
    }

    public RegexSource getSource() {
        return this.source;
    }

    public final int getNumberOfCaptureGroups() {
        return this.numberOfCaptureGroups;
    }

    public final int getNumberOfTransitions() {
        return this.numberOfTransitions;
    }

    public Encodings.Encoding getEncoding() {
        return this.source.getEncoding();
    }

    public boolean isUTF8() {
        return this.getEncoding() == Encodings.UTF_8;
    }

    public boolean isUTF16() {
        return this.getEncoding() == Encodings.UTF_16;
    }

    public boolean isUTF32() {
        return this.getEncoding() == Encodings.UTF_32;
    }

    public BranchProfile getBMPProfile() {
        return this.bmpProfile;
    }

    public BranchProfile getAstralProfile() {
        return this.astralProfile;
    }

    public int getInputLength(TRegexExecutorLocals locals) {
        if (this.lengthNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.lengthNode = this.insert(InputLengthNode.create());
        }
        return this.lengthNode.execute(locals.getInput(), this.getEncoding());
    }

    public boolean inputAtBegin(TRegexExecutorLocals locals) {
        return locals.getIndex() == (this.isForward() ? 0 : this.getInputLength(locals));
    }

    public boolean inputAtEnd(TRegexExecutorLocals locals) {
        return locals.getIndex() == (this.isForward() ? this.getInputLength(locals) : 0);
    }

    public int getMinIndex(TRegexExecutorLocals locals) {
        return 0;
    }

    public int getMaxIndex(TRegexExecutorLocals locals) {
        return locals.getMaxIndex();
    }

    public boolean inputHasNext(TRegexExecutorLocals locals) {
        return this.inputHasNext(locals, locals.getIndex());
    }

    public boolean inputHasNext(TRegexExecutorLocals locals, int index) {
        return this.inputHasNext(locals, index, this.isForward());
    }

    public boolean inputHasNext(TRegexExecutorLocals locals, boolean forward) {
        return this.inputHasNext(locals, locals.getIndex(), forward);
    }

    public boolean inputHasNext(TRegexExecutorLocals locals, int index, boolean forward) {
        return forward ? index < this.getMaxIndex(locals) : index > this.getMinIndex(locals);
    }

    public int inputReadAndDecode(TRegexExecutorLocals locals) {
        return this.inputReadAndDecode(locals, locals.getIndex());
    }

    @ExplodeLoop
    public int inputReadAndDecode(TRegexExecutorLocals locals, int index) {
        if (this.getEncoding() == Encodings.UTF_16) {
            int c2;
            locals.setNextIndex(this.inputIncRaw(index));
            int c = this.inputReadRaw(locals);
            if (this.inputUTF16IsHighSurrogate(c) && this.inputHasNext(locals, locals.getNextIndex()) && this.inputUTF16IsLowSurrogate(c2 = this.inputReadRaw(locals, locals.getNextIndex()))) {
                locals.setNextIndex(this.inputIncRaw(locals.getNextIndex()));
                return this.inputUTF16ToCodePoint(c, c2);
            }
            return c;
        }
        if (this.getEncoding() == Encodings.UTF_8) {
            int c = this.inputReadRaw(locals);
            if (c < 128) {
                locals.setNextIndex(this.inputIncRaw(index));
                return c;
            }
            int codepoint = c & 0x3F;
            if (!this.isForward()) {
                assert (c >> 6 == 2);
                for (int i = 1; i < 4; ++i) {
                    c = this.inputReadRaw(locals, locals.getIndex() - i);
                    if (i >= 3 || c >> 6 != 2) break;
                    codepoint |= (c & 0x3F) << 6 * i;
                }
            }
            int nBytes = TRegexExecutorNode.inputUTF8NumberOfLeadingOnes(c);
            assert (1 < nBytes && nBytes < 5) : nBytes;
            if (this.isForward()) {
                locals.setNextIndex(this.inputIncRaw(index));
                codepoint = c & 255 >>> nBytes;
                switch (nBytes) {
                    case 4: {
                        codepoint = codepoint << 6 | this.inputReadRaw(locals, locals.getNextIndex()) & 0x3F;
                        locals.setNextIndex(this.inputIncRaw(locals.getNextIndex()));
                    }
                    case 3: {
                        codepoint = codepoint << 6 | this.inputReadRaw(locals, locals.getNextIndex()) & 0x3F;
                        locals.setNextIndex(this.inputIncRaw(locals.getNextIndex()));
                    }
                }
                codepoint = codepoint << 6 | this.inputReadRaw(locals, locals.getNextIndex()) & 0x3F;
                locals.setNextIndex(this.inputIncRaw(locals.getNextIndex()));
                return codepoint;
            }
            locals.setNextIndex(this.inputIncRaw(index, nBytes));
            return codepoint | (c & 255 >>> nBytes) << 6 * (nBytes - 1);
        }
        assert (this.getEncoding() == Encodings.UTF_16_RAW || this.getEncoding() == Encodings.UTF_32 || this.getEncoding() == Encodings.LATIN_1 || this.getEncoding() == Encodings.BYTES || this.getEncoding() == Encodings.ASCII);
        locals.setNextIndex(this.inputIncRaw(index));
        return this.inputReadRaw(locals);
    }

    public boolean inputUTF16IsHighSurrogate(int c) {
        return Encodings.Encoding.UTF16.isHighSurrogate(c, this.isForward());
    }

    public boolean inputUTF16IsLowSurrogate(int c) {
        return Encodings.Encoding.UTF16.isLowSurrogate(c, this.isForward());
    }

    public int inputUTF16ToCodePoint(int highSurrogate, int lowSurrogate) {
        return this.isForward() ? Character.toCodePoint((char)highSurrogate, (char)lowSurrogate) : Character.toCodePoint((char)lowSurrogate, (char)highSurrogate);
    }

    private static boolean inputUTF8IsTrailingByte(int c) {
        return c >> 6 == 2;
    }

    private static int inputUTF8NumberOfLeadingOnes(int c) {
        return Integer.numberOfLeadingZeros(~(c << 24));
    }

    public int inputReadRaw(TRegexExecutorLocals locals) {
        return this.inputReadRaw(locals, locals.getIndex());
    }

    public int inputReadRaw(TRegexExecutorLocals locals, int index) {
        return this.inputReadRaw(locals, index, this.isForward());
    }

    public int inputReadRaw(TRegexExecutorLocals locals, boolean forward) {
        return this.inputReadRaw(locals, locals.getIndex(), forward);
    }

    public int inputReadRaw(TRegexExecutorLocals locals, int index, boolean forward) {
        if (this.charAtNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.charAtNode = this.insert(InputReadNode.create());
        }
        return this.charAtNode.execute(locals.getInput(), forward ? index : index - 1, this.getEncoding());
    }

    public void inputAdvance(TRegexExecutorLocals locals) {
        locals.setIndex(locals.getNextIndex());
    }

    public void inputSkip(TRegexExecutorLocals locals) {
        this.inputSkipIntl(locals, this.isForward());
    }

    public void inputSkipReverse(TRegexExecutorLocals locals) {
        this.inputSkipIntl(locals, !this.isForward());
    }

    protected void inputSkipIntl(TRegexExecutorLocals locals, boolean forward) {
        if (this.isUTF16()) {
            int c = this.inputReadRaw(locals, forward);
            this.inputIncRaw(locals, forward);
            if (Encodings.Encoding.UTF16.isHighSurrogate(c, forward) && this.inputHasNext(locals, forward) && Encodings.Encoding.UTF16.isLowSurrogate(this.inputReadRaw(locals, forward), forward)) {
                this.inputIncRaw(locals, forward);
            }
        } else if (this.isUTF8()) {
            if (forward) {
                int c = this.inputReadRaw(locals, true);
                if (c < 128) {
                    this.inputIncRaw(locals, true);
                } else {
                    this.getBMPProfile().enter();
                    this.inputIncRaw(locals, TRegexExecutorNode.inputUTF8NumberOfLeadingOnes(c), true);
                }
            } else {
                int c;
                do {
                    c = this.inputReadRaw(locals, false);
                    this.inputIncRaw(locals, false);
                } while (this.inputHasNext(locals, false) && TRegexExecutorNode.inputUTF8IsTrailingByte(c));
            }
        } else {
            assert (this.getEncoding() == Encodings.UTF_16_RAW || this.getEncoding() == Encodings.UTF_32 || this.getEncoding() == Encodings.LATIN_1 || this.getEncoding() == Encodings.BYTES || this.getEncoding() == Encodings.ASCII);
            this.inputIncRaw(locals, forward);
        }
    }

    public void inputIncRaw(TRegexExecutorLocals locals) {
        this.inputIncRaw(locals, 1);
    }

    public void inputIncRaw(TRegexExecutorLocals locals, int offset) {
        this.inputIncRaw(locals, offset, this.isForward());
    }

    public void inputIncRaw(TRegexExecutorLocals locals, boolean forward) {
        this.inputIncRaw(locals, 1, forward);
    }

    public void inputIncRaw(TRegexExecutorLocals locals, int offset, boolean forward) {
        locals.setIndex(TRegexExecutorNode.inputIncRaw(locals.getIndex(), offset, forward));
    }

    public int inputIncRaw(int index) {
        return TRegexExecutorNode.inputIncRaw(index, 1, this.isForward());
    }

    public int inputIncRaw(int index, int offset) {
        return TRegexExecutorNode.inputIncRaw(index, offset, this.isForward());
    }

    public static int inputIncRaw(int index, boolean forward) {
        return TRegexExecutorNode.inputIncRaw(index, 1, forward);
    }

    public static int inputIncRaw(int index, int offset, boolean forward) {
        assert (offset > 0);
        return forward ? index + offset : index - offset;
    }

    public void inputIncNextIndexRaw(TRegexExecutorLocals locals) {
        this.inputIncNextIndexRaw(locals, 1);
    }

    public void inputIncNextIndexRaw(TRegexExecutorLocals locals, int offset) {
        locals.setNextIndex(TRegexExecutorNode.inputIncRaw(locals.getIndex(), offset, this.isForward()));
    }

    public int countUpTo(TRegexExecutorLocals locals, int max2, int nCodePoints) {
        CompilerAsserts.partialEvaluationConstant(nCodePoints);
        if (nCodePoints > 0) {
            int i;
            assert (this.isForward());
            int index = locals.getIndex();
            for (i = 0; locals.getIndex() < max2 && i < nCodePoints; ++i) {
                this.inputSkipIntl(locals, true);
            }
            locals.setIndex(index);
            return i;
        }
        return 0;
    }

    public int rewindUpTo(TRegexExecutorLocals locals, int min2, int nCodePoints) {
        CompilerAsserts.partialEvaluationConstant(nCodePoints);
        if (nCodePoints > 0) {
            int i;
            assert (this.isForward());
            for (i = 0; locals.getIndex() > min2 && i < nCodePoints; ++i) {
                this.inputSkipIntl(locals, false);
            }
            return i;
        }
        return 0;
    }

    public boolean isBooleanMatch() {
        boolean booleanMatch = this.source.getOptions().isBooleanMatch();
        CompilerAsserts.partialEvaluationConstant(booleanMatch);
        return booleanMatch;
    }

    public abstract TRegexExecutorNode shallowCopy();

    public abstract String getName();

    public abstract boolean isForward();

    public abstract boolean writesCaptureGroups();

    public abstract TRegexExecutorLocals createLocals(Object var1, int var2, int var3, int var4);
}

