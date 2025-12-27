package com.oracle.truffle.regex.tregex.dfa;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.regex.charset.CodePointSet;
import com.oracle.truffle.regex.tregex.automaton.BasicState;
import com.oracle.truffle.regex.tregex.automaton.TransitionSet;
import com.oracle.truffle.regex.tregex.buffer.CompilationBuffer;
import com.oracle.truffle.regex.tregex.buffer.IntArrayBuffer;
import com.oracle.truffle.regex.tregex.nfa.NFA;
import com.oracle.truffle.regex.tregex.nfa.NFAState;
import com.oracle.truffle.regex.tregex.nfa.NFAStateTransition;
import com.oracle.truffle.regex.tregex.util.DebugUtil;
import com.oracle.truffle.regex.tregex.util.json.Json;
import com.oracle.truffle.regex.tregex.util.json.JsonConvertible;
import com.oracle.truffle.regex.tregex.util.json.JsonValue;
import java.util.Arrays;

public final class DFAStateNodeBuilder extends BasicState<DFAStateNodeBuilder, DFAStateTransitionBuilder> implements JsonConvertible {
   private static final short FLAG_OVERRIDE_FINAL_STATE = 16;
   private static final short FLAG_FINAL_STATE_SUCCESSOR = 32;
   private static final short FLAG_BACKWARD_PREFIX_STATE = 64;
   private static final short FLAG_FORWARD = 128;
   private static final short FLAG_PRIORITY_SENSITIVE = 256;
   private static final DFAStateTransitionBuilder[] EMPTY_TRANSITIONS = new DFAStateTransitionBuilder[0];
   private static final DFAStateTransitionBuilder[] NODE_SPLIT_TAINTED = new DFAStateTransitionBuilder[0];
   private static final String NODE_SPLIT_UNINITIALIZED_PRECEDING_TRANSITIONS_ERROR_MSG = "this state node builder was altered by the node splitter and does not have valid information about preceding transitions!";
   private TransitionSet<NFA, NFAState, NFAStateTransition> nfaTransitionSet;
   private short backwardPrefixState = -1;
   private NFAStateTransition anchoredFinalStateTransition;
   private NFAStateTransition unAnchoredFinalStateTransition;
   private byte preCalculatedUnAnchoredResult = -1;
   private byte preCalculatedAnchoredResult = -1;

   DFAStateNodeBuilder(
      int id,
      TransitionSet<NFA, NFAState, NFAStateTransition> nfaStateSet,
      boolean isBackwardPrefixState,
      boolean isInitialState,
      boolean forward,
      boolean prioritySensitive
   ) {
      super(id, EMPTY_TRANSITIONS);

      assert id <= 32767;

      this.nfaTransitionSet = nfaStateSet;
      this.setFlag((short)64, isBackwardPrefixState);
      this.setFlag((short)128, forward);
      this.setFlag((short)256, prioritySensitive);
      this.setUnAnchoredInitialState(isInitialState);
      if (isBackwardPrefixState) {
         this.backwardPrefixState = (short)id;
      }
   }

   private DFAStateNodeBuilder(DFAStateNodeBuilder copy, short copyID) {
      super(copyID, copy.getFlags(), EMPTY_TRANSITIONS);
      this.nfaTransitionSet = copy.nfaTransitionSet;
      this.backwardPrefixState = copy.backwardPrefixState;
      DFAStateTransitionBuilder[] transitions = new DFAStateTransitionBuilder[((DFAStateTransitionBuilder[])copy.getSuccessors()).length];

      for (int i = 0; i < transitions.length; i++) {
         transitions[i] = copy.getSuccessors()[i].createNodeSplitCopy();
      }

      this.setSuccessors(transitions);
      this.setPredecessors(NODE_SPLIT_TAINTED);
      this.anchoredFinalStateTransition = copy.anchoredFinalStateTransition;
      this.unAnchoredFinalStateTransition = copy.unAnchoredFinalStateTransition;
      this.preCalculatedAnchoredResult = copy.preCalculatedAnchoredResult;
      this.preCalculatedUnAnchoredResult = copy.preCalculatedUnAnchoredResult;
   }

   public DFAStateNodeBuilder createNodeSplitCopy(short copyID) {
      return new DFAStateNodeBuilder(this, copyID);
   }

   public void nodeSplitUpdateSuccessors(short[] newSuccessors, DFAStateNodeBuilder[] stateIndexMap) {
      for (int i = 0; i < ((DFAStateTransitionBuilder[])this.getSuccessors()).length; i++) {
         DFAStateNodeBuilder successor = stateIndexMap[newSuccessors[i]];

         assert successor != null;

         successor.setPredecessors(NODE_SPLIT_TAINTED);
         this.getSuccessors()[i].setTarget(successor);
      }

      if (this.hasBackwardPrefixState()) {
         assert newSuccessors.length == ((DFAStateTransitionBuilder[])this.getSuccessors()).length + 1;

         this.backwardPrefixState = newSuccessors[newSuccessors.length - 1];
      }
   }

   public void setNfaTransitionSet(TransitionSet<NFA, NFAState, NFAStateTransition> nfaTransitionSet) {
      this.nfaTransitionSet = nfaTransitionSet;
   }

   public TransitionSet<NFA, NFAState, NFAStateTransition> getNfaTransitionSet() {
      return this.nfaTransitionSet;
   }

   public void setOverrideFinalState(boolean overrideFinalState) {
      this.setFlag((short)16, overrideFinalState);
   }

   public boolean isFinalStateSuccessor() {
      return this.getFlag((short)32);
   }

   public void setFinalStateSuccessor() {
      this.setFlag((short)32);
   }

   public boolean isBackwardPrefixState() {
      return this.getFlag((short)64);
   }

   public void setIsBackwardPrefixState(boolean backwardPrefixState) {
      this.setFlag((short)64, backwardPrefixState);
   }

   @Override
   public boolean isUnAnchoredFinalState() {
      return this.getFlag((short)24);
   }

   @Override
   public boolean isFinalState() {
      return this.getFlag((short)28);
   }

   public boolean isForward() {
      return this.getFlag((short)128);
   }

   public boolean isPrioritySensitive() {
      return this.getFlag((short)256);
   }

   public int getNumberOfSuccessors() {
      return this.getSuccessors().length + (this.hasBackwardPrefixState() ? 1 : 0);
   }

   protected DFAStateTransitionBuilder[] createTransitionsArray(int length) {
      return new DFAStateTransitionBuilder[length];
   }

   public boolean coversFullCharSpace(CompilationBuffer compilationBuffer) {
      IntArrayBuffer indicesBuf = compilationBuffer.getIntRangesBuffer1();
      indicesBuf.ensureCapacity(this.getSuccessors().length);
      int[] indices = indicesBuf.getBuffer();
      Arrays.fill(indices, 0, this.getSuccessors().length, 0);
      int nextLo = compilationBuffer.getEncoding().getMinValue();

      while (true) {
         int i = this.findNextLo(indices, nextLo);
         if (i < 0) {
            return false;
         }

         CodePointSet ranges = this.getSuccessors()[i].getCodePointSet();
         if (ranges.getHi(indices[i]) == compilationBuffer.getEncoding().getMaxValue()) {
            return true;
         }

         nextLo = ranges.getHi(indices[i]) + 1;
         indices[i]++;
      }
   }

   private int findNextLo(int[] indices, int findLo) {
      for (int i = 0; i < ((DFAStateTransitionBuilder[])this.getSuccessors()).length; i++) {
         CodePointSet ranges = this.getSuccessors()[i].getCodePointSet();
         if (indices[i] != ranges.size() && ranges.getLo(indices[i]) == findLo) {
            return i;
         }
      }

      return -1;
   }

   public DFAStateTransitionBuilder[] getPredecessors() {
      if (super.getPredecessors() == NODE_SPLIT_TAINTED) {
         throw CompilerDirectives.shouldNotReachHere(
            "this state node builder was altered by the node splitter and does not have valid information about preceding transitions!"
         );
      } else {
         return (DFAStateTransitionBuilder[])super.getPredecessors();
      }
   }

   public boolean hasBackwardPrefixState() {
      return this.backwardPrefixState >= 0;
   }

   public short getBackwardPrefixState() {
      return this.backwardPrefixState;
   }

   public void setBackwardPrefixState(short backwardPrefixState) {
      this.backwardPrefixState = backwardPrefixState;
   }

   public void setAnchoredFinalStateTransition(NFAStateTransition anchoredFinalStateTransition) {
      this.anchoredFinalStateTransition = anchoredFinalStateTransition;
   }

   public NFAStateTransition getAnchoredFinalStateTransition() {
      return this.anchoredFinalStateTransition;
   }

   public void setUnAnchoredFinalStateTransition(NFAStateTransition unAnchoredFinalStateTransition) {
      this.unAnchoredFinalStateTransition = unAnchoredFinalStateTransition;
   }

   public NFAStateTransition getUnAnchoredFinalStateTransition() {
      return this.unAnchoredFinalStateTransition;
   }

   public byte getPreCalculatedUnAnchoredResult() {
      return this.preCalculatedUnAnchoredResult;
   }

   public byte getPreCalculatedAnchoredResult() {
      return this.preCalculatedAnchoredResult;
   }

   void updatePreCalcUnAnchoredResult(int newResult) {
      if (newResult >= 0 && (this.preCalculatedUnAnchoredResult == -1 || Byte.toUnsignedInt(this.preCalculatedUnAnchoredResult) > newResult)) {
         this.preCalculatedUnAnchoredResult = (byte)newResult;
      }
   }

   private void updatePreCalcAnchoredResult(int newResult) {
      if (newResult >= 0 && (this.preCalculatedAnchoredResult == -1 || Byte.toUnsignedInt(this.preCalculatedAnchoredResult) > newResult)) {
         this.preCalculatedAnchoredResult = (byte)newResult;
      }
   }

   public void clearPreCalculatedResults() {
      this.preCalculatedUnAnchoredResult = -1;
      this.preCalculatedAnchoredResult = -1;
   }

   public DFAStateNodeBuilder updateFinalStateData(DFAGenerator dfaGenerator) {
      boolean forward = dfaGenerator.isForward();
      boolean traceFinder = dfaGenerator.getNfa().isTraceFinderNFA();

      for (NFAStateTransition t : (NFAStateTransition[])this.nfaTransitionSet.getTransitions()) {
         NFAState target = t.getTarget(forward);
         if (target.hasTransitionToAnchoredFinalState(forward) && this.anchoredFinalStateTransition == null) {
            if (traceFinder && this.isBackwardPrefixState()) {
               for (NFAStateTransition t2 : target.getSuccessors(forward)) {
                  NFAState target2 = t2.getTarget(forward);
                  if (target2.isAnchoredFinalState(forward) && target2.hasPrefixStates()) {
                     this.setAnchoredFinalState();
                     this.setAnchoredFinalStateTransition(t2);
                  }
               }
            } else {
               this.setAnchoredFinalState();
               this.setAnchoredFinalStateTransition(target.getFirstTransitionToFinalState(forward));
            }
         }

         if (target.hasTransitionToUnAnchoredFinalState(forward)) {
            if (traceFinder && this.isBackwardPrefixState()) {
               for (NFAStateTransition t2x : target.getSuccessors(forward)) {
                  NFAState target2 = t2x.getTarget(forward);
                  if (target2.isUnAnchoredFinalState(forward) && target2.hasPrefixStates()) {
                     this.setUnAnchoredFinalState();
                     this.setUnAnchoredFinalStateTransition(t2x);
                  }
               }
            } else {
               this.setUnAnchoredFinalState();
               this.setUnAnchoredFinalStateTransition(target.getTransitionToUnAnchoredFinalState(forward));
            }

            if (forward) {
               return this;
            }
         }

         if (traceFinder) {
            for (NFAStateTransition t2xx : target.getSuccessors(forward)) {
               NFAState target2 = t2xx.getTarget(forward);
               if (!this.isBackwardPrefixState() || target2.hasPrefixStates()) {
                  if (target2.isAnchoredFinalState(forward)) {
                     assert target2.hasPossibleResults() && target2.getPossibleResults().numberOfSetBits() == 1;

                     this.updatePreCalcAnchoredResult(target2.getPossibleResults().iterator().nextInt());
                  }

                  if (target2.isUnAnchoredFinalState(forward)) {
                     assert target2.hasPossibleResults() && target2.getPossibleResults().numberOfSetBits() == 1;

                     this.updatePreCalcUnAnchoredResult(target2.getPossibleResults().iterator().nextInt());
                  }
               }
            }
         }
      }

      return this;
   }

   public String stateSetToString() {
      StringBuilder sb = new StringBuilder(this.nfaTransitionSet.toString());
      if (this.preCalculatedUnAnchoredResult != -1) {
         sb.append("_r").append(this.preCalculatedUnAnchoredResult);
      }

      if (this.preCalculatedAnchoredResult != -1) {
         sb.append("_rA").append(this.preCalculatedAnchoredResult);
      }

      return sb.toString();
   }

   @Override
   public int hashCode() {
      int hashCode;
      if (this.isPrioritySensitive()) {
         hashCode = 1;

         for (int i = 0; i < this.nfaTransitionSet.size(); i++) {
            hashCode = 31 * hashCode + ((NFAStateTransition)this.nfaTransitionSet.getTransition(i)).getTarget().hashCode();
         }
      } else {
         hashCode = this.nfaTransitionSet.getTargetStateSet().hashCode();
      }

      if (this.isBackwardPrefixState()) {
         hashCode *= 31;
      }

      return hashCode;
   }

   @Override
   public boolean equals(Object obj) {
      if (obj == this) {
         return true;
      } else if (!(obj instanceof DFAStateNodeBuilder)) {
         return false;
      } else {
         DFAStateNodeBuilder o = (DFAStateNodeBuilder)obj;
         if (this.isBackwardPrefixState() != o.isBackwardPrefixState()) {
            return false;
         } else if (this.isPrioritySensitive()) {
            if (this.nfaTransitionSet.size() != o.nfaTransitionSet.size()) {
               return false;
            } else {
               for (int i = 0; i < this.nfaTransitionSet.size(); i++) {
                  if (!((NFAStateTransition)this.nfaTransitionSet.getTransition(i))
                     .getTarget(this.isForward())
                     .equals(((NFAStateTransition)o.nfaTransitionSet.getTransition(i)).getTarget())) {
                     return false;
                  }
               }

               return true;
            }
         } else {
            return this.nfaTransitionSet.getTargetStateSet().equals(o.nfaTransitionSet.getTargetStateSet());
         }
      }
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   protected boolean hasTransitionToUnAnchoredFinalState(boolean forward) {
      throw new UnsupportedOperationException();
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public String toString() {
      StringBuilder sb = new StringBuilder();
      return DebugUtil.appendNodeId(sb, this.getId()).append(": ").append(this.stateSetToString()).toString();
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public JsonValue toJson() {
      return Json.obj(
         Json.prop("id", this.getId()),
         Json.prop(
            "stateSet", Json.array(Arrays.stream((NFAStateTransition[])this.nfaTransitionSet.getTransitions()).map(x -> Json.val(x.getTarget().getId())))
         ),
         Json.prop("finalState", this.isUnAnchoredFinalState()),
         Json.prop("anchoredFinalState", this.isAnchoredFinalState()),
         Json.prop("transitions", Arrays.stream(this.getSuccessors()).map(x -> Json.val(x.getId())))
      );
   }
}
