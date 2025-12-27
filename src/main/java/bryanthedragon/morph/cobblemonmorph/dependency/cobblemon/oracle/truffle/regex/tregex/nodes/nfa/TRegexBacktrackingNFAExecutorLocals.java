package com.oracle.truffle.regex.tregex.nodes.nfa;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.regex.tregex.nfa.PureNFATransition;
import com.oracle.truffle.regex.tregex.nodes.TRegexExecutorLocals;
import com.oracle.truffle.regex.tregex.parser.Token;
import com.oracle.truffle.regex.util.BitSets;
import java.util.Arrays;

public final class TRegexBacktrackingNFAExecutorLocals extends TRegexExecutorLocals {
   private final int stackFrameSize;
   private final int nQuantifierCounts;
   private final int nZeroWidthQuantifiers;
   private final int[] zeroWidthTermEnclosedCGLow;
   private final int[] zeroWidthQuantifierCGOffsets;
   private final int[] stackFrameBuffer;
   private final int stackBase;
   private final TRegexBacktrackingNFAExecutorLocals.Stack stack;
   private int sp;
   private final int[] result;
   private final long[] transitionBitSet;
   private final boolean trackLastGroup;
   private final boolean dontOverwriteLastGroup;
   private int lastResultSp = -1;
   private int lastResultIndex = -1;
   private int lastInnerLiteralIndex;
   private int lastInitialStateIndex;

   public TRegexBacktrackingNFAExecutorLocals(
      Object input,
      int fromIndex,
      int index,
      int maxIndex,
      int nCaptureGroups,
      int nQuantifiers,
      int nZeroWidthQuantifiers,
      int[] zeroWidthTermEnclosedCGLow,
      int[] zeroWidthQuantifierCGOffsets,
      boolean allocateStackFrameBuffer,
      int maxNTransitions,
      boolean trackLastGroup,
      boolean dontOverwriteLastGroup
   ) {
      this(
         input,
         fromIndex,
         index,
         maxIndex,
         nCaptureGroups,
         nQuantifiers,
         nZeroWidthQuantifiers,
         zeroWidthTermEnclosedCGLow,
         zeroWidthQuantifierCGOffsets,
         allocateStackFrameBuffer
            ? new int[getStackFrameSize(nCaptureGroups, nQuantifiers, nZeroWidthQuantifiers, zeroWidthQuantifierCGOffsets, trackLastGroup)]
            : null,
         new TRegexBacktrackingNFAExecutorLocals.Stack(
            new int[getStackFrameSize(nCaptureGroups, nQuantifiers, nZeroWidthQuantifiers, zeroWidthQuantifierCGOffsets, trackLastGroup) * 4]
         ),
         0,
         BitSets.createBitSetArray(maxNTransitions),
         trackLastGroup,
         dontOverwriteLastGroup
      );
      this.setIndex(fromIndex);
      this.clearCaptureGroups();
   }

   private TRegexBacktrackingNFAExecutorLocals(
      Object input,
      int fromIndex,
      int index,
      int maxIndex,
      int nCaptureGroups,
      int nQuantifiers,
      int nZeroWidthQuantifiers,
      int[] zeroWidthTermEnclosedCGLow,
      int[] zeroWidthQuantifierCGOffsets,
      int[] stackFrameBuffer,
      TRegexBacktrackingNFAExecutorLocals.Stack stack,
      int stackBase,
      long[] transitionBitSet,
      boolean trackLastGroup,
      boolean dontOverwriteLastGroup
   ) {
      super(input, fromIndex, maxIndex, index);
      this.stackFrameSize = getStackFrameSize(nCaptureGroups, nQuantifiers, nZeroWidthQuantifiers, zeroWidthQuantifierCGOffsets, trackLastGroup);
      this.nQuantifierCounts = nQuantifiers;
      this.nZeroWidthQuantifiers = nZeroWidthQuantifiers;
      this.zeroWidthTermEnclosedCGLow = zeroWidthTermEnclosedCGLow;
      this.zeroWidthQuantifierCGOffsets = zeroWidthQuantifierCGOffsets;
      this.stackFrameBuffer = stackFrameBuffer;
      this.stack = stack;
      this.stackBase = stackBase;
      this.sp = stackBase;
      this.result = new int[nCaptureGroups * 2 + (trackLastGroup ? 1 : 0)];
      this.transitionBitSet = transitionBitSet;
      this.trackLastGroup = trackLastGroup;
      this.dontOverwriteLastGroup = dontOverwriteLastGroup;
   }

   private int[] stack() {
      return this.stack.stack;
   }

   private static int getStackFrameSize(
      int nCaptureGroups, int nQuantifiers, int nZeroWidthQuantifiers, int[] zeroWidthQuantifierCGOffsets, boolean trackLastGroup
   ) {
      return 2
         + nCaptureGroups * 2
         + (trackLastGroup ? 1 : 0)
         + nQuantifiers
         + nZeroWidthQuantifiers
         + zeroWidthQuantifierCGOffsets[zeroWidthQuantifierCGOffsets.length - 1];
   }

   public TRegexBacktrackingNFAExecutorLocals createSubNFALocals(boolean newDontOverwriteLastGroup) {
      this.dupFrame();
      if (this.trackLastGroup && newDontOverwriteLastGroup) {
         this.stack()[this.offsetLastGroup() + this.stackFrameSize] = -1;
      }

      return this.newSubLocals(newDontOverwriteLastGroup);
   }

   public TRegexBacktrackingNFAExecutorLocals createSubNFALocals(PureNFATransition t, boolean newDontOverwriteLastGroup) {
      this.dupFrame();
      if (this.trackLastGroup && newDontOverwriteLastGroup) {
         this.stack()[this.offsetLastGroup() + this.stackFrameSize] = -1;
      }

      t.getGroupBoundaries()
         .applyExploded(
            this.stack(),
            this.offsetCaptureGroups() + this.stackFrameSize,
            this.offsetLastGroup() + this.stackFrameSize,
            this.getIndex(),
            this.trackLastGroup,
            this.dontOverwriteLastGroup
         );
      return this.newSubLocals(newDontOverwriteLastGroup);
   }

   private TRegexBacktrackingNFAExecutorLocals newSubLocals(boolean newDontOverwriteLastGroup) {
      return new TRegexBacktrackingNFAExecutorLocals(
         this.getInput(),
         this.getFromIndex(),
         this.getIndex(),
         this.getMaxIndex(),
         this.result.length / 2,
         this.nQuantifierCounts,
         this.nZeroWidthQuantifiers,
         this.zeroWidthTermEnclosedCGLow,
         this.zeroWidthQuantifierCGOffsets,
         this.stackFrameBuffer,
         this.stack,
         this.sp + this.stackFrameSize,
         this.transitionBitSet,
         this.trackLastGroup,
         newDontOverwriteLastGroup
      );
   }

   private int offsetIP() {
      return this.sp + 1;
   }

   private int offsetCaptureGroups() {
      return this.sp + 2;
   }

   private int offsetLastGroup() {
      return this.trackLastGroup ? this.sp + 2 + this.result.length - 1 : -1;
   }

   private int offsetQuantifierCounts() {
      return this.sp + 2 + this.result.length;
   }

   private int offsetZeroWidthQuantifierIndices() {
      return this.sp + 2 + this.result.length + this.nQuantifierCounts;
   }

   private int offsetZeroWidthQuantifierCG() {
      return this.sp + 2 + this.result.length + this.nQuantifierCounts + this.nZeroWidthQuantifiers;
   }

   private int offsetQuantifierCount(Token.Quantifier q) {
      CompilerAsserts.partialEvaluationConstant(q.getIndex());
      return this.offsetQuantifierCounts() + q.getIndex();
   }

   private int offsetZeroWidthQuantifierIndex(Token.Quantifier q) {
      CompilerAsserts.partialEvaluationConstant(q.getZeroWidthIndex());
      return this.offsetZeroWidthQuantifierIndices() + q.getZeroWidthIndex();
   }

   private int offsetZeroWidthQuantifierCG(Token.Quantifier q) {
      return this.offsetZeroWidthQuantifierCG(q.getZeroWidthIndex());
   }

   private int offsetZeroWidthQuantifierCG(int zeroWidthIndex) {
      CompilerAsserts.partialEvaluationConstant(zeroWidthIndex);
      return this.offsetZeroWidthQuantifierCG() + this.zeroWidthQuantifierCGOffsets[zeroWidthIndex];
   }

   public void apply(PureNFATransition t, int index) {
      t.getGroupBoundaries()
         .applyExploded(this.stack(), this.offsetCaptureGroups(), this.offsetLastGroup(), index, this.trackLastGroup, this.dontOverwriteLastGroup);
   }

   public void resetToInitialState() {
      this.clearCaptureGroups();
      this.clearQuantifierCounts();
   }

   protected void clearCaptureGroups() {
      Arrays.fill(this.stack(), this.offsetCaptureGroups(), this.offsetCaptureGroups() + this.result.length, -1);
   }

   protected void clearQuantifierCounts() {
      Arrays.fill(this.stack(), this.offsetQuantifierCounts(), this.offsetQuantifierCounts() + this.nQuantifierCounts, 0);
   }

   public void push() {
      this.sp = this.sp + this.stackFrameSize;
   }

   public void pushFrame(int[] frame) {
      this.ensureSize(this.sp + 2 * this.stackFrameSize);
      this.push();
      this.writeFrame(frame);
   }

   public void readFrame(int[] to) {
      assert to == this.stackFrameBuffer || to.length >= this.stackFrameSize;

      System.arraycopy(this.stack(), this.sp, to, 0, this.stackFrameSize);
   }

   public void writeFrame(int[] from) {
      System.arraycopy(from, 0, this.stack(), this.sp, this.stackFrameSize);
   }

   public void dupFrame() {
      this.dupFrame(1);
   }

   public void dupFrame(int n) {
      int minSize = this.sp + this.stackFrameSize * (n + 1);
      this.ensureSize(minSize);
      int targetFrame = this.sp;

      for (int i = 0; i < n; i++) {
         targetFrame += this.stackFrameSize;
         System.arraycopy(this.stack(), this.sp, this.stack(), targetFrame, this.stackFrameSize);
      }
   }

   private void ensureSize(int minSize) {
      if (this.stack().length < minSize) {
         int newLength = this.stack().length << 1;

         while (newLength < minSize) {
            newLength <<= 1;
         }

         this.stack.stack = Arrays.copyOf(this.stack(), newLength);
      }
   }

   public void pushResult(PureNFATransition t, int index) {
      t.getGroupBoundaries().applyExploded(this.result, 0, this.result.length - 1, index, this.trackLastGroup, this.dontOverwriteLastGroup);
      this.pushResult();
   }

   public void pushResult() {
      this.lastResultSp = this.sp;
      this.lastResultIndex = this.getIndex();
   }

   public void setResult() {
      System.arraycopy(this.stack(), this.offsetCaptureGroups(), this.result, 0, this.result.length);
   }

   public boolean canPopResult() {
      return this.lastResultSp == this.sp;
   }

   public int[] popResult() {
      if (this.lastResultSp < 0) {
         return null;
      } else {
         this.setIndex(this.lastResultIndex);
         return this.result;
      }
   }

   public boolean canPop() {
      return this.sp > this.stackBase;
   }

   public int pop() {
      assert this.sp > this.stackBase;

      this.sp = this.sp - this.stackFrameSize;
      this.restoreIndex();
      return this.stack()[this.offsetIP()];
   }

   public void saveIndex(int index) {
      this.stack()[this.sp] = index;
   }

   public void restoreIndex() {
      this.setIndex(this.stack()[this.sp]);
   }

   public int getPc() {
      return this.stack()[this.offsetIP()];
   }

   public int setPc(int pc) {
      return this.stack()[this.offsetIP()] = pc;
   }

   public int getCaptureGroupBoundary(int boundary) {
      return this.stack()[this.offsetCaptureGroups() + boundary];
   }

   public void setCaptureGroupBoundary(int boundary, int index) {
      this.stack()[this.offsetCaptureGroups() + boundary] = index;
   }

   public int getCaptureGroupStart(int groupNumber) {
      return this.getCaptureGroupBoundary(groupNumber * 2);
   }

   public int getCaptureGroupEnd(int groupNumber) {
      return this.getCaptureGroupBoundary(groupNumber * 2 + 1);
   }

   public void overwriteCaptureGroups(int[] captureGroups) {
      assert captureGroups.length == this.result.length;

      if (this.trackLastGroup) {
         System.arraycopy(captureGroups, 0, this.stack(), this.offsetCaptureGroups(), captureGroups.length - 1);
         this.setLastGroup(captureGroups[captureGroups.length - 1]);
      } else {
         System.arraycopy(captureGroups, 0, this.stack(), this.offsetCaptureGroups(), captureGroups.length);
      }
   }

   public void setLastGroup(int newLastGroup) {
      if (this.trackLastGroup && newLastGroup != -1 && (!this.dontOverwriteLastGroup || this.stack()[this.offsetLastGroup()] == -1)) {
         this.stack()[this.offsetLastGroup()] = newLastGroup;
      }
   }

   public int getQuantifierCount(Token.Quantifier q) {
      return this.stack()[this.offsetQuantifierCount(q)];
   }

   public void setQuantifierCount(Token.Quantifier q, int count) {
      this.stack()[this.offsetQuantifierCount(q)] = count;
   }

   public void resetQuantifierCount(Token.Quantifier q) {
      this.stack()[this.offsetQuantifierCount(q)] = 0;
   }

   public void incQuantifierCount(Token.Quantifier q) {
      this.stack()[this.offsetQuantifierCount(q)]++;
   }

   public int getZeroWidthQuantifierGuardIndex(Token.Quantifier q) {
      return this.stack()[this.offsetZeroWidthQuantifierIndex(q)];
   }

   public void setZeroWidthQuantifierGuardIndex(Token.Quantifier q) {
      this.stack()[this.offsetZeroWidthQuantifierIndex(q)] = this.getIndex();
   }

   public boolean isResultUnmodifiedByZeroWidthQuantifier(Token.Quantifier q) {
      int start = this.offsetCaptureGroups() + 2 * this.zeroWidthTermEnclosedCGLow[q.getZeroWidthIndex()];
      int length = this.zeroWidthQuantifierCGOffsets[q.getZeroWidthIndex() + 1] - this.zeroWidthQuantifierCGOffsets[q.getZeroWidthIndex()];

      for (int i = 0; i < length; i++) {
         if (this.stack()[this.offsetZeroWidthQuantifierCG(q) + i] != this.stack()[start + i]) {
            return false;
         }
      }

      return true;
   }

   public void setZeroWidthQuantifierResults(Token.Quantifier q) {
      int start = this.offsetCaptureGroups() + 2 * this.zeroWidthTermEnclosedCGLow[q.getZeroWidthIndex()];
      int length = this.zeroWidthQuantifierCGOffsets[q.getZeroWidthIndex() + 1] - this.zeroWidthQuantifierCGOffsets[q.getZeroWidthIndex()];
      System.arraycopy(this.stack(), start, this.stack(), this.offsetZeroWidthQuantifierCG(q), length);
   }

   public long[] getTransitionBitSet() {
      return this.transitionBitSet;
   }

   public int getLastInnerLiteralIndex() {
      return this.lastInnerLiteralIndex;
   }

   public void setLastInnerLiteralIndex(int i) {
      this.lastInnerLiteralIndex = i;
   }

   public int getLastInitialStateIndex() {
      return this.lastInitialStateIndex;
   }

   public void setLastInitialStateIndex(int i) {
      this.lastInitialStateIndex = i;
   }

   public int[] getStackFrameBuffer() {
      return this.stackFrameBuffer;
   }

   @CompilerDirectives.TruffleBoundary
   public void printStack(int curPc) {
      System.out.println("STACK SNAPSHOT");
      System.out.println("==============");

      for (int i = this.sp; i >= 0; i -= this.stackFrameSize) {
         System.out.print(String.format("pc: %d, i: %d,\n  cg: [", i == this.sp ? curPc : this.stack()[i + 1], this.stack()[i]));

         for (int j = this.offsetCaptureGroups(); j < this.offsetQuantifierCounts(); j++) {
            System.out.print(String.format("%d, ", this.stack()[i + j - this.sp]));
         }

         System.out.print(String.format(",\n  quant: ["));

         for (int j = this.offsetQuantifierCounts(); j < this.offsetZeroWidthQuantifierIndices(); j++) {
            System.out.print(String.format("%d, ", this.stack()[i + j - this.sp]));
         }

         System.out.print(String.format("],\n  zwq-indices: ["));

         for (int j = this.offsetZeroWidthQuantifierIndices(); j < this.offsetZeroWidthQuantifierCG(); j++) {
            System.out.print(String.format("%d, ", this.stack()[i + j - this.sp]));
         }

         System.out.print(String.format("],\n  zwq-cg: {\n"));

         for (int zwq = 0; zwq < this.nZeroWidthQuantifiers; zwq++) {
            System.out.print(String.format("    %d: [", zwq));

            for (int j = this.offsetZeroWidthQuantifierCG(zwq); j < this.offsetZeroWidthQuantifierCG(zwq + 1); j++) {
               System.out.print(String.format("%d, ", this.stack()[i + j - this.sp]));
            }

            System.out.print("],\n");
         }

         System.out.println("}\n");
      }
   }

   private static final class Stack {
      private int[] stack;

      Stack(int[] stack) {
         this.stack = stack;
      }
   }
}
