
package com.oracle.truffle.regex.tregex.nodes.dfa;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.regex.tregex.nodes.dfa.AllTransitionsInOneTreeMatcher;
import com.oracle.truffle.regex.tregex.nodes.dfa.DFAAbstractStateNode;
import com.oracle.truffle.regex.tregex.nodes.dfa.DFASimpleCG;
import com.oracle.truffle.regex.tregex.nodes.dfa.DFASimpleCGTransition;
import com.oracle.truffle.regex.tregex.nodes.dfa.Matchers;
import com.oracle.truffle.regex.tregex.nodes.dfa.SequentialMatchers;
import com.oracle.truffle.regex.tregex.nodes.dfa.TRegexDFAExecutorLocals;
import com.oracle.truffle.regex.tregex.nodes.dfa.TRegexDFAExecutorNode;
import com.oracle.truffle.regex.tregex.parser.ast.InnerLiteral;
import com.oracle.truffle.regex.tregex.string.AbstractString;
import com.oracle.truffle.regex.tregex.string.Encodings;
import com.oracle.truffle.regex.tregex.util.DebugUtil;
import com.oracle.truffle.regex.tregex.util.json.Json;
import com.oracle.truffle.regex.tregex.util.json.JsonArray;
import com.oracle.truffle.regex.tregex.util.json.JsonConvertible;
import com.oracle.truffle.regex.tregex.util.json.JsonValue;
import java.util.Arrays;

public class DFAStateNode
extends DFAAbstractStateNode {
    private static final byte FLAG_FINAL_STATE = 1;
    private static final byte FLAG_ANCHORED_FINAL_STATE = 2;
    private static final byte FLAG_HAS_BACKWARD_PREFIX_STATE = 4;
    private static final byte FLAG_UTF_16_MUST_DECODE = 8;
    private final byte flags;
    private final short loopTransitionIndex;
    protected final IndexOfCall indexOfCall;
    private final Matchers matchers;
    private final DFASimpleCG simpleCG;

    DFAStateNode(DFAStateNode nodeSplitCopy, short copyID) {
        this(copyID, nodeSplitCopy.flags, nodeSplitCopy.loopTransitionIndex, nodeSplitCopy.indexOfCall, Arrays.copyOf(nodeSplitCopy.getSuccessors(), nodeSplitCopy.getSuccessors().length), nodeSplitCopy.getMatchers(), nodeSplitCopy.simpleCG);
    }

    public DFAStateNode(short id, byte flags, short loopTransitionIndex, IndexOfCall indexOfCall, short[] successors, Matchers matchers, DFASimpleCG simpleCG) {
        super(id, successors);
        assert (id > 0);
        this.flags = flags;
        this.loopTransitionIndex = loopTransitionIndex;
        this.indexOfCall = indexOfCall;
        this.matchers = matchers;
        this.simpleCG = simpleCG;
    }

    public static byte buildFlags(boolean finalState, boolean anchoredFinalState, boolean hasBackwardPrefixState, boolean utf16MustDecode) {
        byte flags = 0;
        if (finalState) {
            flags = (byte)(flags | 1);
        }
        if (anchoredFinalState) {
            flags = (byte)(flags | 2);
        }
        if (hasBackwardPrefixState) {
            flags = (byte)(flags | 4);
        }
        if (utf16MustDecode) {
            flags = (byte)(flags | 8);
        }
        return flags;
    }

    @Override
    public DFAStateNode createNodeSplitCopy(short copyID) {
        return new DFAStateNode(this, copyID);
    }

    public final Matchers getMatchers() {
        return this.matchers;
    }

    public final SequentialMatchers getSequentialMatchers() {
        return (SequentialMatchers)this.matchers;
    }

    public boolean isFinalState() {
        return this.flagIsSet((byte)1);
    }

    public boolean isAnchoredFinalState() {
        return this.flagIsSet((byte)2);
    }

    public boolean hasBackwardPrefixState() {
        return this.flagIsSet((byte)4);
    }

    public boolean utf16MustDecode() {
        return this.flagIsSet((byte)8);
    }

    private boolean flagIsSet(byte flag) {
        return (this.flags & flag) != 0;
    }

    public boolean hasLoopToSelf() {
        return this.loopTransitionIndex >= 0;
    }

    boolean isLoopToSelf(int transitionIndex) {
        return this.hasLoopToSelf() && transitionIndex == this.getLoopToSelf();
    }

    short getLoopToSelf() {
        assert (this.hasLoopToSelf());
        return this.loopTransitionIndex;
    }

    boolean treeTransitionMatching() {
        return this.matchers instanceof AllTransitionsInOneTreeMatcher;
    }

    AllTransitionsInOneTreeMatcher getTreeMatcher() {
        return (AllTransitionsInOneTreeMatcher)this.matchers;
    }

    boolean canDoIndexOf() {
        return this.hasLoopToSelf() && this.indexOfCall != null;
    }

    void beforeFindSuccessor(TRegexDFAExecutorLocals locals, TRegexDFAExecutorNode executor) {
        CompilerAsserts.partialEvaluationConstant(this);
        this.checkFinalState(locals, executor);
    }

    void afterIndexOf(TRegexDFAExecutorLocals locals, TRegexDFAExecutorNode executor, int preLoopIndex, int postLoopIndex) {
        locals.setIndex(postLoopIndex);
        if (this.simpleCG != null && locals.getIndex() > preLoopIndex) {
            int curIndex = locals.getIndex();
            executor.inputSkipReverse(locals);
            this.applySimpleCGTransition(this.simpleCG.getTransitions()[this.getLoopToSelf()], executor, locals);
            locals.setIndex(curIndex);
        }
        this.checkFinalState(locals, executor);
    }

    private void checkFinalState(TRegexDFAExecutorLocals locals, TRegexDFAExecutorNode executor) {
        CompilerAsserts.partialEvaluationConstant(this);
        if (this.isFinalState()) {
            this.storeResult(locals, executor, false);
            if (this.simpleCG != null) {
                this.applySimpleCGFinalTransition(this.simpleCG.getTransitionToFinalState(), executor, locals);
            }
        }
    }

    void atEnd(TRegexDFAExecutorLocals locals, TRegexDFAExecutorNode executor) {
        boolean anchored;
        CompilerAsserts.partialEvaluationConstant(this);
        boolean bl = anchored = this.isAnchoredFinalState() && executor.inputAtEnd(locals);
        if (this.isFinalState() || anchored) {
            this.storeResult(locals, executor, anchored);
            if (this.simpleCG != null) {
                if (this.isAnchoredFinalState()) {
                    this.applySimpleCGFinalTransition(this.simpleCG.getTransitionToAnchoredFinalState(), executor, locals);
                } else if (this.isFinalState()) {
                    this.applySimpleCGFinalTransition(this.simpleCG.getTransitionToFinalState(), executor, locals);
                }
            }
        }
    }

    void successorFound(TRegexDFAExecutorLocals locals, TRegexDFAExecutorNode executor, int i) {
        if (this.simpleCG != null) {
            this.applySimpleCGTransition(this.simpleCG.getTransitions()[i], executor, locals);
        }
    }

    void storeResult(TRegexDFAExecutorLocals locals, TRegexDFAExecutorNode executor, boolean anchored) {
        CompilerAsserts.partialEvaluationConstant(this);
        if (executor.isSimpleCG()) {
            if (executor.getProperties().isSimpleCGMustCopy()) {
                System.arraycopy(locals.getCGData().results, 0, locals.getCGData().currentResult, 0, locals.getCGData().currentResult.length);
            }
            locals.setResultInt(0);
        } else {
            locals.setResultInt(locals.getIndex());
        }
    }

    void applySimpleCGTransition(DFASimpleCGTransition transition, TRegexDFAExecutorNode executor, TRegexDFAExecutorLocals locals) {
        transition.apply(locals.getCGData().results, locals.getIndex(), executor.getProperties().tracksLastGroup());
    }

    void applySimpleCGFinalTransition(DFASimpleCGTransition transition, TRegexDFAExecutorNode executor, TRegexDFAExecutorLocals locals) {
        transition.applyFinal(locals.getCGData(), locals.getIndex(), executor.getProperties().isSimpleCGMustCopy(), executor.getProperties().tracksLastGroup());
    }

    @CompilerDirectives.TruffleBoundary
    public String toString() {
        StringBuilder sb = new StringBuilder();
        DebugUtil.appendNodeId(sb, this.getId()).append(": ");
        if (!this.treeTransitionMatching()) {
            sb.append(this.getSequentialMatchers().size()).append(" successors");
        }
        if (this.isAnchoredFinalState()) {
            sb.append(", AFS");
        }
        if (this.isFinalState()) {
            sb.append(", FS");
        }
        sb.append(":\n");
        if (this.treeTransitionMatching()) {
            sb.append("      ").append(this.getTreeMatcher()).append("\n      successors: ").append(Arrays.toString(this.successors)).append("\n");
        } else {
            for (int i = 0; i < this.getSequentialMatchers().size(); ++i) {
                sb.append("      ").append(i).append(": ").append(this.getSequentialMatchers().toString(i)).append(" -> ");
                DebugUtil.appendNodeId(sb, this.getSuccessors()[i]).append("\n");
            }
        }
        return sb.toString();
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public JsonValue toJson() {
        JsonArray transitions = Json.array(new JsonConvertible[0]);
        if (this.matchers != null) {
            for (int i = 0; i < this.getSequentialMatchers().size(); ++i) {
                transitions.append(Json.obj(Json.prop("matcher", this.getSequentialMatchers().toString(i)), Json.prop("target", this.successors[i])));
            }
        }
        return Json.obj(Json.prop("id", this.getId()), Json.prop("anchoredFinalState", this.isAnchoredFinalState()), Json.prop("finalState", this.isFinalState()), Json.prop("loopToSelf", this.hasLoopToSelf()), Json.prop("transitions", transitions));
    }

    public static final class IndexOfStringCall
    extends IndexOfCall {
        private final int literalLength;
        private final InnerLiteral literal;

        public IndexOfStringCall(AbstractString str, AbstractString mask) {
            this.literalLength = str.encodedLength();
            this.literal = new InnerLiteral(str, mask, 0);
        }

        @Override
        public int execute(TRegexDFAExecutorNode executor, Object input, int fromIndex, int maxIndex, Encodings.Encoding encoding, boolean tString) {
            return executor.getIndexOfStringNode().execute(input, fromIndex, maxIndex, this.literal.getLiteralContent(tString), this.literal.getMaskContent(tString), encoding);
        }

        @Override
        public int encodedLength() {
            return this.literalLength;
        }
    }

    public static final class IndexOfAnyByteCall
    extends IndexOfAnyCall {
        @CompilerDirectives.CompilationFinal(dimensions=1)
        private final byte[] bytes;

        public IndexOfAnyByteCall(byte[] bytes) {
            this.bytes = bytes;
        }

        @Override
        public int execute(TRegexDFAExecutorNode executor, Object input, int fromIndex, int maxIndex, Encodings.Encoding encoding, boolean tString) {
            return executor.getIndexOfNode().execute(input, fromIndex, maxIndex, this.bytes, encoding);
        }
    }

    public static final class IndexOfAnyCharCall
    extends IndexOfAnyCall {
        @CompilerDirectives.CompilationFinal(dimensions=1)
        private final char[] chars;

        public IndexOfAnyCharCall(char[] chars) {
            this.chars = chars;
        }

        @Override
        public int execute(TRegexDFAExecutorNode executor, Object input, int fromIndex, int maxIndex, Encodings.Encoding encoding, boolean tString) {
            return executor.getIndexOfNode().execute(input, fromIndex, maxIndex, this.chars, encoding);
        }
    }

    public static final class IndexOfAnyIntCall
    extends IndexOfAnyCall {
        @CompilerDirectives.CompilationFinal(dimensions=1)
        private final int[] ints;

        public IndexOfAnyIntCall(int[] ints) {
            this.ints = ints;
        }

        @Override
        public int execute(TRegexDFAExecutorNode executor, Object input, int fromIndex, int maxIndex, Encodings.Encoding encoding, boolean tString) {
            return executor.getIndexOfNode().execute(input, fromIndex, maxIndex, this.ints, encoding);
        }
    }

    public static abstract class IndexOfAnyCall
    extends IndexOfCall {
        @Override
        public int encodedLength() {
            return 1;
        }
    }

    public static abstract class IndexOfCall {
        public abstract int execute(TRegexDFAExecutorNode var1, Object var2, int var3, int var4, Encodings.Encoding var5, boolean var6);

        public abstract int encodedLength();
    }
}

