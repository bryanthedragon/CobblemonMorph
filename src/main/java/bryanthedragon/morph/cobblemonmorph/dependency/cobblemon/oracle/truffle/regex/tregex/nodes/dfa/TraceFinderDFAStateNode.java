package com.oracle.truffle.regex.tregex.nodes.dfa;

public class TraceFinderDFAStateNode extends BackwardDFAStateNode {
   public static final byte NO_PRE_CALC_RESULT = -1;
   private final byte preCalculatedUnAnchoredResult;
   private final byte preCalculatedAnchoredResult;

   public TraceFinderDFAStateNode(
      short id,
      byte flags,
      short loopTransitionIndex,
      DFAStateNode.IndexOfCall indexOfCall,
      short[] successors,
      Matchers matchers,
      byte preCalculatedUnAnchoredResult,
      byte preCalculatedAnchoredResult
   ) {
      super(id, flags, loopTransitionIndex, indexOfCall, successors, matchers, null);
      this.preCalculatedUnAnchoredResult = preCalculatedUnAnchoredResult;
      this.preCalculatedAnchoredResult = initPreCalculatedAnchoredResult(preCalculatedUnAnchoredResult, preCalculatedAnchoredResult);
   }

   private TraceFinderDFAStateNode(TraceFinderDFAStateNode copy, short copyID) {
      super(copy, copyID);
      this.preCalculatedUnAnchoredResult = copy.preCalculatedUnAnchoredResult;
      this.preCalculatedAnchoredResult = copy.preCalculatedAnchoredResult;
   }

   private static byte initPreCalculatedAnchoredResult(byte preCalculatedUnAnchoredResult, byte preCalculatedAnchoredResult) {
      return Byte.toUnsignedInt(preCalculatedUnAnchoredResult) < Byte.toUnsignedInt(preCalculatedAnchoredResult) ? -1 : preCalculatedAnchoredResult;
   }

   @Override
   public DFAStateNode createNodeSplitCopy(short copyID) {
      return new TraceFinderDFAStateNode(this, copyID);
   }

   private boolean hasPreCalculatedUnAnchoredResult() {
      return this.preCalculatedUnAnchoredResult != -1;
   }

   private int getPreCalculatedUnAnchoredResult() {
      return Byte.toUnsignedInt(this.preCalculatedUnAnchoredResult);
   }

   private boolean hasPreCalculatedAnchoredResult() {
      return this.preCalculatedAnchoredResult != -1;
   }

   private int getPreCalculatedAnchoredResult() {
      return Byte.toUnsignedInt(this.preCalculatedAnchoredResult);
   }

   @Override
   void storeResult(TRegexDFAExecutorLocals locals, TRegexDFAExecutorNode executor, boolean anchored) {
      if (this.hasPreCalculatedAnchoredResult() && anchored) {
         locals.setResultInt(this.getPreCalculatedAnchoredResult());
      } else {
         assert this.hasPreCalculatedUnAnchoredResult();

         locals.setResultInt(this.getPreCalculatedUnAnchoredResult());
      }
   }
}
