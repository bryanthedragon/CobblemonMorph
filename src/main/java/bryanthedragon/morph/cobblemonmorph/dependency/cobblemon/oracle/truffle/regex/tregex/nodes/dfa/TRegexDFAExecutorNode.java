
package com.oracle.truffle.regex.tregex.nodes.dfa;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.regex.RegexRootNode;
import com.oracle.truffle.regex.RegexSource;
import com.oracle.truffle.regex.tregex.matchers.CharMatcher;
import com.oracle.truffle.regex.tregex.nodes.TRegexExecutorLocals;
import com.oracle.truffle.regex.tregex.nodes.TRegexExecutorNode;
import com.oracle.truffle.regex.tregex.nodes.dfa.BackwardDFAStateNode;
import com.oracle.truffle.regex.tregex.nodes.dfa.DFAAbstractStateNode;
import com.oracle.truffle.regex.tregex.nodes.dfa.DFACaptureGroupTrackingData;
import com.oracle.truffle.regex.tregex.nodes.dfa.DFAFindInnerLiteralStateNode;
import com.oracle.truffle.regex.tregex.nodes.dfa.DFAInitialStateNode;
import com.oracle.truffle.regex.tregex.nodes.dfa.DFAStateNode;
import com.oracle.truffle.regex.tregex.nodes.dfa.SequentialMatchers;
import com.oracle.truffle.regex.tregex.nodes.dfa.TRegexDFAExecutorDebugRecorder;
import com.oracle.truffle.regex.tregex.nodes.dfa.TRegexDFAExecutorLocals;
import com.oracle.truffle.regex.tregex.nodes.dfa.TRegexDFAExecutorProperties;
import com.oracle.truffle.regex.tregex.nodes.input.InputIndexOfNode;
import com.oracle.truffle.regex.tregex.nodes.input.InputIndexOfStringNode;
import java.util.Arrays;

public final class TRegexDFAExecutorNode
extends TRegexExecutorNode {
    private static final int IP_TRANSITION_MARKER = 32768;
    public static final int NO_MATCH = -2;
    private final TRegexDFAExecutorProperties props;
    private final int maxNumberOfNFAStates;
    @CompilerDirectives.CompilationFinal(dimensions=1)
    private final DFAAbstractStateNode[] states;
    @CompilerDirectives.CompilationFinal(dimensions=1)
    private final int[] cgResultOrder;
    private final TRegexDFAExecutorDebugRecorder debugRecorder;
    @Node.Child
    private InputIndexOfNode indexOfNode;
    @Node.Child
    private InputIndexOfStringNode indexOfStringNode;
    @Node.Child
    private TRegexDFAExecutorNode innerLiteralPrefixMatcher;

    public TRegexDFAExecutorNode(RegexSource source, TRegexDFAExecutorProperties props, int numberOfCaptureGroups, int maxNumberOfNFAStates, DFAAbstractStateNode[] states, TRegexDFAExecutorDebugRecorder debugRecorder, TRegexDFAExecutorNode innerLiteralPrefixMatcher) {
        this(source, props, numberOfCaptureGroups, TRegexDFAExecutorNode.calcNumberOfTransitions(states), maxNumberOfNFAStates, states, props.isGenericCG() && maxNumberOfNFAStates > 1 ? TRegexDFAExecutorNode.initResultOrder(maxNumberOfNFAStates, numberOfCaptureGroups, props) : null, debugRecorder, innerLiteralPrefixMatcher);
    }

    public TRegexDFAExecutorNode(RegexSource source, TRegexDFAExecutorProperties props, int numberOfCaptureGroups, int numberOfTransitions, int maxNumberOfNFAStates, DFAAbstractStateNode[] states, int[] cgResultOrder, TRegexDFAExecutorDebugRecorder debugRecorder, TRegexDFAExecutorNode innerLiteralPrefixMatcher) {
        super(source, numberOfCaptureGroups, numberOfTransitions);
        this.props = props;
        this.maxNumberOfNFAStates = maxNumberOfNFAStates;
        this.states = states;
        this.cgResultOrder = cgResultOrder;
        this.debugRecorder = debugRecorder;
        this.innerLiteralPrefixMatcher = innerLiteralPrefixMatcher;
    }

    private TRegexDFAExecutorNode(TRegexDFAExecutorNode copy, TRegexDFAExecutorNode innerLiteralPrefixMatcher) {
        this(copy.getSource(), copy.props, copy.getNumberOfCaptureGroups(), copy.getNumberOfTransitions(), copy.maxNumberOfNFAStates, copy.states, copy.cgResultOrder, copy.debugRecorder, innerLiteralPrefixMatcher);
    }

    @Override
    public TRegexDFAExecutorNode shallowCopy() {
        return new TRegexDFAExecutorNode(this, this.innerLiteralPrefixMatcher == null ? null : this.innerLiteralPrefixMatcher.shallowCopy());
    }

    private DFAInitialStateNode getInitialState() {
        return (DFAInitialStateNode)this.states[0];
    }

    public int getPrefixLength() {
        return this.getInitialState().getPrefixLength();
    }

    public boolean isAnchored() {
        return !this.getInitialState().hasUnAnchoredEntry();
    }

    @Override
    public String getName() {
        return "dfa";
    }

    @Override
    public boolean isForward() {
        return this.props.isForward();
    }

    public boolean isBackward() {
        return !this.props.isForward();
    }

    public boolean isSearching() {
        return this.props.isSearching();
    }

    public boolean isSimpleCG() {
        return this.props.isSimpleCG();
    }

    public boolean isGenericCG() {
        return this.props.isGenericCG();
    }

    public boolean isRegressionTestMode() {
        return this.props.isRegressionTestMode();
    }

    public int getNumberOfStates() {
        return this.states.length;
    }

    private static int calcNumberOfTransitions(DFAAbstractStateNode[] states) {
        int sum = 0;
        for (DFAAbstractStateNode state : states) {
            sum += state.getSuccessors().length;
            if (!(state instanceof DFAStateNode) || ((DFAStateNode)state).treeTransitionMatching() || ((DFAStateNode)state).getSequentialMatchers().getNoMatchSuccessor() < 0) continue;
            ++sum;
        }
        return sum;
    }

    public boolean recordExecution() {
        return this.debugRecorder != null;
    }

    public TRegexDFAExecutorDebugRecorder getDebugRecorder() {
        return this.debugRecorder;
    }

    InputIndexOfNode getIndexOfNode() {
        if (this.indexOfNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.indexOfNode = this.insert(InputIndexOfNode.create());
        }
        return this.indexOfNode;
    }

    InputIndexOfStringNode getIndexOfStringNode() {
        if (this.indexOfStringNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.indexOfStringNode = this.insert(InputIndexOfStringNode.create());
        }
        return this.indexOfStringNode;
    }

    @Override
    public TRegexExecutorLocals createLocals(Object input, int fromIndex, int index, int maxIndex) {
        return new TRegexDFAExecutorLocals(input, fromIndex, index, maxIndex, this.createCGData());
    }

    @Override
    public boolean writesCaptureGroups() {
        return this.isSimpleCG();
    }

    private DFACaptureGroupTrackingData createCGData() {
        if (this.isSimpleCG()) {
            return new DFACaptureGroupTrackingData(null, TRegexDFAExecutorNode.createResultsArray(this.resultLength()), this.props.isSimpleCGMustCopy() ? new int[this.resultLength()] : null);
        }
        if (this.isGenericCG()) {
            return new DFACaptureGroupTrackingData(this.maxNumberOfNFAStates == 1 ? null : Arrays.copyOf(this.cgResultOrder, this.cgResultOrder.length), TRegexDFAExecutorNode.createResultsArray(this.maxNumberOfNFAStates * this.resultLength()), new int[this.resultLength()]);
        }
        return null;
    }

    private int resultLength() {
        return this.getNumberOfCaptureGroups() * 2 + (this.props.tracksLastGroup() ? 1 : 0);
    }

    private static int[] createResultsArray(int length) {
        int[] results2 = new int[length];
        Arrays.fill(results2, -1);
        return results2;
    }

    /*
     * Unable to fully structure code
     */
    @Override
    @ExplodeLoop(kind=ExplodeLoop.LoopExplosionKind.MERGE_EXPLODE)
    public Object execute(VirtualFrame frame, TRegexExecutorLocals abstractLocals, TruffleString.CodeRange codeRange, boolean tString) {
        block87: {
            locals = (TRegexDFAExecutorLocals)abstractLocals;
            CompilerDirectives.ensureVirtualized(locals);
            CompilerAsserts.partialEvaluationConstant(this.states);
            CompilerAsserts.partialEvaluationConstant(this.states.length);
            CompilerAsserts.partialEvaluationConstant((Object)codeRange);
            if (!this.validArgs(locals)) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                throw new IllegalArgumentException(String.format("Got illegal args! (fromIndex %d, initialIndex %d, maxIndex %d)", new Object[]{locals.getFromIndex(), locals.getIndex(), locals.getMaxIndex()}));
            }
            if (this.isGenericCG() || this.isSimpleCG()) {
                CompilerDirectives.ensureVirtualized(locals.getCGData());
            }
            if (this.props.getMinResultLength() > 0 && (this.isForward() != false ? locals.getMaxIndex() - locals.getIndex() : locals.getIndex() - Math.max(0, locals.getFromIndex() - this.getPrefixLength())) < this.props.getMinResultLength()) {
                return this.isGenericCG() != false || this.isSimpleCG() != false ? null : Integer.valueOf(-2);
            }
            if (this.recordExecution()) {
                this.debugRecorder.startRecording(locals);
            }
            if (this.isBackward()) {
                locals.setCurMinIndex(locals.getFromIndex());
            }
            ip = 0;
            block5: while (true) {
                block88: {
                    block90: {
                        block91: {
                            block92: {
                                block89: {
                                    if (CompilerDirectives.inInterpreter()) {
                                        RegexRootNode.checkThreadInterrupted();
                                    }
                                    CompilerAsserts.partialEvaluationConstant(ip);
                                    if (ip < 0) break block87;
                                    curState = this.states[ip & 32767];
                                    CompilerAsserts.partialEvaluationConstant(curState);
                                    successors = curState.getSuccessors();
                                    CompilerAsserts.partialEvaluationConstant(successors);
                                    CompilerAsserts.partialEvaluationConstant(successors.length);
                                    if (curState instanceof DFAInitialStateNode) {
                                        if (this.isSearching()) {
                                            if (!TRegexDFAExecutorNode.$assertionsDisabled && !this.isForward()) {
                                                throw new AssertionError();
                                            }
                                            for (i = 0; i < this.getPrefixLength(); ++i) {
                                                if (locals.getIndex() <= 0) {
                                                    this.initNextIndex(locals);
                                                    ip = this.initialStateSuccessor(locals, curState, successors, i);
                                                    continue block5;
                                                }
                                                this.inputSkipIntl(locals, false);
                                            }
                                            this.initNextIndex(locals);
                                            atBegin = this.inputAtBegin(locals);
                                        } else {
                                            this.initNextIndex(locals);
                                            atBegin = this.inputAtBegin(locals);
                                            for (i = 0; i < this.getPrefixLength(); ++i) {
                                                if (!TRegexDFAExecutorNode.$assertionsDisabled && !this.isForward()) {
                                                    throw new AssertionError();
                                                }
                                                if (locals.getIndex() >= locals.getFromIndex()) {
                                                    if (atBegin) {
                                                        ip = this.initialStateSuccessor(locals, curState, successors, i);
                                                        continue block5;
                                                    }
                                                    ip = this.initialStateSuccessor(locals, curState, successors, i + successors.length / 2);
                                                    continue block5;
                                                }
                                                this.inputSkipIntl(locals, true);
                                            }
                                        }
                                        if (atBegin) {
                                            ip = this.initialStateSuccessor(locals, curState, successors, this.getPrefixLength());
                                            continue;
                                        }
                                        ip = this.initialStateSuccessor(locals, curState, successors, this.getPrefixLength() + successors.length / 2);
                                        continue;
                                    }
                                    if (!(curState instanceof DFAStateNode)) break block88;
                                    state = (DFAStateNode)curState;
                                    if (ip > 32768) {
                                        i = ip >> 16;
                                        ip = this.execTransition(locals, state, i);
                                        continue;
                                    }
                                    if (CompilerDirectives.hasNextTier()) {
                                        locals.incLoopCount(this);
                                    }
                                    this.inputAdvance(locals);
                                    state.beforeFindSuccessor(locals, this);
                                    if (this.isForward() && state.canDoIndexOf() && this.inputHasNext(locals)) {
                                        indexOfResult = state.indexOfCall.execute(this, locals.getInput(), locals.getIndex(), this.getMaxIndex(locals), this.getEncoding(), tString);
                                        postLoopIndex = indexOfResult < 0 ? this.getMaxIndex(locals) : indexOfResult;
                                        state.afterIndexOf(locals, this, locals.getIndex(), postLoopIndex);
                                        if (!TRegexDFAExecutorNode.$assertionsDisabled && locals.getIndex() != postLoopIndex) {
                                            throw new AssertionError();
                                        }
                                        if (successors.length == 2 && indexOfResult >= 0) {
                                            successor = state.getLoopToSelf() + 1 & 1;
                                            CompilerAsserts.partialEvaluationConstant(successor);
                                            this.inputIncNextIndexRaw(locals, state.indexOfCall.encodedLength());
                                            ip = this.execTransition(locals, state, successor);
                                            continue;
                                        }
                                    }
                                    if (!this.inputHasNext(locals)) {
                                        state.atEnd(locals, this);
                                        if (this.isBackward() && state.hasBackwardPrefixState() && locals.getIndex() > 0) {
                                            if (!TRegexDFAExecutorNode.$assertionsDisabled && locals.getIndex() != locals.getFromIndex()) {
                                                throw new AssertionError();
                                            }
                                            locals.setCurMinIndex(0);
                                            ip = TRegexDFAExecutorNode.transitionMatch(state, ((BackwardDFAStateNode)state).getBackwardPrefixStateIndex());
                                            continue;
                                        }
                                        break block87;
                                    }
                                    if (state.treeTransitionMatching()) {
                                        c = this.inputReadAndDecode(locals);
                                        treeSuccessor = state.getTreeMatcher().checkMatchTree(c);
                                        for (i = 0; i < successors.length; ++i) {
                                            if (i != treeSuccessor) continue;
                                            ip = TRegexDFAExecutorNode.transitionMatch(state, i);
                                            continue block5;
                                        }
                                        break block87;
                                    }
                                    matchers = state.getSequentialMatchers();
                                    CompilerAsserts.partialEvaluationConstant(matchers);
                                    if (!(matchers instanceof SequentialMatchers.SimpleSequentialMatchers)) break block89;
                                    c = this.inputReadAndDecode(locals);
                                    cMatchers = ((SequentialMatchers.SimpleSequentialMatchers)matchers).getMatchers();
                                    if (cMatchers != null) {
                                        for (i = 0; i < cMatchers.length; ++i) {
                                            if (!TRegexDFAExecutorNode.match(cMatchers, i, c)) continue;
                                            ip = TRegexDFAExecutorNode.transitionMatch(state, i);
                                            continue block5;
                                        }
                                    }
                                    break block90;
                                }
                                if (!(matchers instanceof SequentialMatchers.UTF8SequentialMatchers)) break block91;
                                utf8Matchers = (SequentialMatchers.UTF8SequentialMatchers)matchers;
                                ascii = utf8Matchers.getAscii();
                                enc2 = utf8Matchers.getEnc2();
                                enc3 = utf8Matchers.getEnc3();
                                enc4 = utf8Matchers.getEnc4();
                                maxBytes = utf8Matchers.getMaxBytes();
                                CompilerAsserts.partialEvaluationConstant(maxBytes);
                                c = this.inputReadRaw(locals);
                                if (codeRange != TruffleString.CodeRange.ASCII && c >= 128) break block92;
                                this.inputIncNextIndexRaw(locals);
                                if (ascii != null) {
                                    for (i = 0; i < ascii.length; ++i) {
                                        if (!TRegexDFAExecutorNode.match(ascii, i, c)) continue;
                                        ip = TRegexDFAExecutorNode.transitionMatch(state, i);
                                        continue block5;
                                    }
                                }
                                break block90;
                            }
                            this.getBMPProfile().enter();
                            codepoint = 0;
                            if (this.isBackward()) {
                                codepoint = c & 63;
                                if (!TRegexDFAExecutorNode.$assertionsDisabled && c >> 6 != 2) {
                                    throw new AssertionError();
                                }
                                for (i = 1; i < 4; ++i) {
                                    c = this.inputReadRaw((TRegexExecutorLocals)locals, locals.getIndex() - i);
                                    if (i >= 3 || c >> 6 != 2) break;
                                    codepoint |= (c & 63) << 6 * i;
                                }
                            }
                            nBytes = Integer.numberOfLeadingZeros(~(c << 24));
                            if (!(TRegexDFAExecutorNode.$assertionsDisabled || 1 < nBytes && nBytes < 5)) {
                                throw new AssertionError(nBytes);
                            }
                            this.inputIncNextIndexRaw(locals, nBytes);
                            if (maxBytes <= 1 || nBytes > maxBytes) break block90;
                            if (this.isBackward()) {
                                codepoint |= (c & 255 >>> nBytes) << 6 * (nBytes - 1);
                            }
                            if (this.isForward()) {
                                index = locals.getIndex();
                                codepoint = (c & 255 >>> nBytes) << 6 | this.inputReadRaw((TRegexExecutorLocals)locals, ++index) & 63;
                                if (maxBytes > 2 && nBytes > 2) {
                                    codepoint = codepoint << 6 | this.inputReadRaw((TRegexExecutorLocals)locals, ++index) & 63;
                                }
                                if (maxBytes > 3 && nBytes > 3) {
                                    this.getAstralProfile().enter();
                                    codepoint = codepoint << 6 | this.inputReadRaw((TRegexExecutorLocals)locals, ++index) & 63;
                                }
                            }
                            switch (nBytes - 2) {
                                case 0: {
                                    if (enc2 == null) ** break;
                                    for (i = 0; i < enc2.length; ++i) {
                                        if (!TRegexDFAExecutorNode.match(enc2, i, codepoint)) continue;
                                        ip = TRegexDFAExecutorNode.transitionMatch(state, i);
                                        continue block5;
                                    }
                                    break block90;
                                }
                                case 1: {
                                    if (enc3 == null) ** break;
                                    for (i = 0; i < enc3.length; ++i) {
                                        if (!TRegexDFAExecutorNode.match(enc3, i, codepoint)) continue;
                                        ip = TRegexDFAExecutorNode.transitionMatch(state, i);
                                        continue block5;
                                    }
                                    break block90;
                                }
                                case 2: {
                                    if (enc4 == null) ** break;
                                    this.getAstralProfile().enter();
                                    for (i = 0; i < enc4.length; ++i) {
                                        if (!TRegexDFAExecutorNode.match(enc4, i, codepoint)) continue;
                                        ip = TRegexDFAExecutorNode.transitionMatch(state, i);
                                        continue block5;
                                    }
                                    ** break;
                                }
                            }
lbl181:
                            // 5 sources

                            break block90;
                        }
                        if (matchers instanceof SequentialMatchers.UTF16RawSequentialMatchers) {
                            c = this.inputReadAndDecode(locals);
                            ascii = ((SequentialMatchers.UTF16RawSequentialMatchers)matchers).getAscii();
                            latin1 = ((SequentialMatchers.UTF16RawSequentialMatchers)matchers).getLatin1();
                            bmp = ((SequentialMatchers.UTF16RawSequentialMatchers)matchers).getBmp();
                            if (latin1 != null && (bmp == null || codeRange.isSubsetOf(TruffleString.CodeRange.LATIN_1) || c < 256)) {
                                byteMatchers = TRegexDFAExecutorNode.asciiOrLatin1Matchers(codeRange, ascii, latin1);
                                for (i = 0; i < byteMatchers.length; ++i) {
                                    if (!TRegexDFAExecutorNode.match(byteMatchers, i, c)) continue;
                                    ip = TRegexDFAExecutorNode.transitionMatch(state, i);
                                    continue block5;
                                }
                            } else if (bmp != null) {
                                this.getBMPProfile().enter();
                                for (i = 0; i < bmp.length; ++i) {
                                    if (!TRegexDFAExecutorNode.match(bmp, i, c)) continue;
                                    ip = TRegexDFAExecutorNode.transitionMatch(state, i);
                                    continue block5;
                                }
                            }
                        } else {
                            if (!TRegexDFAExecutorNode.$assertionsDisabled && !(matchers instanceof SequentialMatchers.UTF16Or32SequentialMatchers)) {
                                throw new AssertionError();
                            }
                            utf16Or32Matchers = (SequentialMatchers.UTF16Or32SequentialMatchers)matchers;
                            ascii = utf16Or32Matchers.getAscii();
                            latin1 = utf16Or32Matchers.getLatin1();
                            bmp = utf16Or32Matchers.getBmp();
                            astral = utf16Or32Matchers.getAstral();
                            c = this.inputReadRaw(locals);
                            this.inputIncNextIndexRaw(locals);
                            if (this.isUTF16()) {
                                if (codeRange.isSupersetOf(TruffleString.CodeRange.VALID) && state.utf16MustDecode() && this.inputUTF16IsHighSurrogate(c) && (codeRange == TruffleString.CodeRange.VALID || this.inputHasNext((TRegexExecutorLocals)locals, locals.getNextIndex()))) {
                                    this.getAstralProfile().enter();
                                    c2 = this.inputReadRaw((TRegexExecutorLocals)locals, locals.getNextIndex());
                                    if (codeRange == TruffleString.CodeRange.VALID || this.inputUTF16IsLowSurrogate(c2)) {
                                        if (!TRegexDFAExecutorNode.$assertionsDisabled && !this.inputUTF16IsLowSurrogate(c2)) {
                                            throw new AssertionError();
                                        }
                                        locals.setNextIndex(this.inputIncRaw(locals.getNextIndex()));
                                        if (astral != null) {
                                            c = this.inputUTF16ToCodePoint(c, c2);
                                        }
                                    }
                                    if (astral != null) {
                                        for (i = 0; i < astral.length; ++i) {
                                            if (!TRegexDFAExecutorNode.match(astral, i, c)) continue;
                                            ip = TRegexDFAExecutorNode.transitionMatch(state, i);
                                            continue block5;
                                        }
                                    }
                                } else if (latin1 != null && (bmp == null || codeRange.isSubsetOf(TruffleString.CodeRange.LATIN_1) || c < 256)) {
                                    byteMatchers = TRegexDFAExecutorNode.asciiOrLatin1Matchers(codeRange, ascii, latin1);
                                    for (i = 0; i < byteMatchers.length; ++i) {
                                        if (!TRegexDFAExecutorNode.match(byteMatchers, i, c)) continue;
                                        ip = TRegexDFAExecutorNode.transitionMatch(state, i);
                                        continue block5;
                                    }
                                } else if (bmp != null && codeRange.isSupersetOf(TruffleString.CodeRange.BMP)) {
                                    this.getBMPProfile().enter();
                                    for (i = 0; i < bmp.length; ++i) {
                                        if (!TRegexDFAExecutorNode.match(bmp, i, c)) continue;
                                        ip = TRegexDFAExecutorNode.transitionMatch(state, i);
                                        continue block5;
                                    }
                                }
                            } else {
                                if (!TRegexDFAExecutorNode.$assertionsDisabled && !this.isUTF32()) {
                                    throw new AssertionError();
                                }
                                if (latin1 != null && (codeRange.isSubsetOf(TruffleString.CodeRange.LATIN_1) || c < 256)) {
                                    byteMatchers = TRegexDFAExecutorNode.asciiOrLatin1Matchers(codeRange, ascii, latin1);
                                    for (i = 0; i < byteMatchers.length; ++i) {
                                        if (!TRegexDFAExecutorNode.match(byteMatchers, i, c)) continue;
                                        ip = TRegexDFAExecutorNode.transitionMatch(state, i);
                                        continue block5;
                                    }
                                } else if (!(bmp == null || codeRange != TruffleString.CodeRange.BMP && (c > 65535 || codeRange != TruffleString.CodeRange.VALID && Character.isSurrogate((char)c)))) {
                                    this.getBMPProfile().enter();
                                    for (i = 0; i < bmp.length; ++i) {
                                        if (!TRegexDFAExecutorNode.match(bmp, i, c)) continue;
                                        ip = TRegexDFAExecutorNode.transitionMatch(state, i);
                                        continue block5;
                                    }
                                } else if (astral != null && codeRange.isSupersetOf(TruffleString.CodeRange.VALID)) {
                                    this.getAstralProfile().enter();
                                    for (i = 0; i < astral.length; ++i) {
                                        if (!TRegexDFAExecutorNode.match(astral, i, c)) continue;
                                        ip = TRegexDFAExecutorNode.transitionMatch(state, i);
                                        continue block5;
                                    }
                                }
                            }
                        }
                    }
                    ip = TRegexDFAExecutorNode.transitionNoMatch(state);
                    continue;
                }
                if (!TRegexDFAExecutorNode.$assertionsDisabled && !(curState instanceof DFAFindInnerLiteralStateNode)) {
                    throw new AssertionError();
                }
                if (!TRegexDFAExecutorNode.$assertionsDisabled && !this.isForward()) {
                    throw new AssertionError();
                }
                state = (DFAFindInnerLiteralStateNode)curState;
lbl275:
                // 2 sources

                while (this.inputHasNext(locals)) {
                    locals.setIndex(state.executeInnerLiteralSearch(locals, this, tString));
                    if (locals.getIndex() >= 0) {
                        if (this.innerLiteralPrefixMatcher != null && !TRegexDFAExecutorNode.prefixMatcherMatches(frame, this.innerLiteralPrefixMatcher, locals, codeRange, tString)) break block5;
                        if (this.innerLiteralPrefixMatcher == null && this.isSimpleCG()) {
                            locals.getCGData().results[0] = locals.getIndex();
                        }
                        this.inputIncRaw((TRegexExecutorLocals)locals, state.getInnerLiteral().getLiteral().encodedLength());
                        locals.setNextIndex(locals.getIndex());
                        ip = successors[0];
                        continue block5;
                    }
                    break block87;
                }
                break block87;
                break;
            }
            this.inputIncRaw(locals);
            ** GOTO lbl275
        }
        if (this.recordExecution()) {
            this.debugRecorder.finishRecording();
        }
        if (this.isSimpleCG()) {
            result = this.props.isSimpleCGMustCopy() != false ? locals.getCGData().currentResult : locals.getCGData().results;
            return (int[])(locals.getResultInt() == 0 ? result : null);
        }
        if (this.isGenericCG()) {
            return locals.getResultInt() == 0 ? locals.getCGData().currentResult : null;
        }
        return locals.getResultInt();
    }

    private static boolean prefixMatcherMatches(VirtualFrame frame, TRegexDFAExecutorNode prefixMatcher, TRegexDFAExecutorLocals locals, TruffleString.CodeRange codeRange, boolean tString) {
        Object result = prefixMatcher.execute(frame, locals.toInnerLiteralBackwardLocals(), codeRange, tString);
        return prefixMatcher.isSimpleCG() ? result != null : (Integer)result != -2;
    }

    private static CharMatcher[] asciiOrLatin1Matchers(TruffleString.CodeRange codeRange, CharMatcher[] ascii, CharMatcher[] latin1) {
        return codeRange == TruffleString.CodeRange.ASCII && ascii != null ? ascii : latin1;
    }

    private short initialStateSuccessor(TRegexDFAExecutorLocals locals, DFAAbstractStateNode curState, short[] successors, int i) {
        if (this.isGenericCG()) {
            locals.setLastIndex();
            short lastTransition = ((DFAInitialStateNode)curState).getCgLastTransition()[i];
            if (lastTransition >= 0) {
                locals.setLastTransition(lastTransition);
            }
        }
        return successors[i];
    }

    private void initNextIndex(TRegexDFAExecutorLocals locals) {
        locals.setNextIndex(locals.getIndex());
        if (this.recordExecution()) {
            this.getDebugRecorder().setInitialIndex(locals.getIndex());
        }
    }

    private static boolean match(CharMatcher[] matchers, int i, int c) {
        return matchers[i] != null && matchers[i].match(c);
    }

    private static int transitionMatch(DFAStateNode state, int i) {
        CompilerAsserts.partialEvaluationConstant(state);
        return state.getId() | 0x8000 | i << 16;
    }

    private static int transitionNoMatch(DFAStateNode state) {
        CompilerAsserts.partialEvaluationConstant(state);
        return state.getId() | 0x8000 | state.getSequentialMatchers().getNoMatchSuccessor() << 16;
    }

    private int execTransition(TRegexDFAExecutorLocals locals, DFAStateNode state, int i) {
        CompilerAsserts.partialEvaluationConstant(state);
        CompilerAsserts.partialEvaluationConstant(i);
        if (this.recordExecution()) {
            this.debugRecorder.recordTransition(locals.getIndex(), state.getId(), i);
        }
        state.successorFound(locals, this, i);
        return state.successors[i];
    }

    @Override
    public int getMinIndex(TRegexExecutorLocals locals) {
        return this.isForward() ? super.getMinIndex(locals) : ((TRegexDFAExecutorLocals)locals).getCurMinIndex();
    }

    private boolean validArgs(TRegexDFAExecutorLocals locals) {
        int initialIndex = locals.getIndex();
        int inputLength = this.getInputLength(locals);
        int fromIndex = locals.getFromIndex();
        int maxIndex = locals.getMaxIndex();
        return inputLength >= 0 && inputLength < 2147483627 && fromIndex >= 0 && fromIndex <= inputLength && initialIndex >= 0 && initialIndex <= maxIndex && maxIndex >= fromIndex && maxIndex <= inputLength;
    }

    private static int[] initResultOrder(int maxNumberOfNFAStates, int numberOfCaptureGroups, TRegexDFAExecutorProperties props) {
        int[] resultOrder = new int[maxNumberOfNFAStates];
        for (int i = 0; i < maxNumberOfNFAStates; ++i) {
            resultOrder[i] = i * (numberOfCaptureGroups * 2 + (props.tracksLastGroup() ? 1 : 0));
        }
        return resultOrder;
    }

    public TRegexDFAExecutorProperties getProperties() {
        return this.props;
    }

    public int getMaxNumberOfNFAStates() {
        return this.maxNumberOfNFAStates;
    }
}

