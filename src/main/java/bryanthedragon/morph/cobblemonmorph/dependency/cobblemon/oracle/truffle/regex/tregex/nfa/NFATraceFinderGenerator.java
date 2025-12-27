package com.oracle.truffle.regex.tregex.nfa;

import com.oracle.truffle.regex.UnsupportedRegexException;
import com.oracle.truffle.regex.result.PreCalculatedResultFactory;
import com.oracle.truffle.regex.tregex.parser.Counter;
import com.oracle.truffle.regex.tregex.parser.ast.GroupBoundaries;
import com.oracle.truffle.regex.tregex.string.Encodings;
import java.util.ArrayList;
import java.util.List;
import org.graalvm.collections.EconomicMap;

public final class NFATraceFinderGenerator {
   private final NFA originalNFA;
   private final List<NFAState> states;
   private final List<NFAState>[] duplicatedStatesMap;
   private final List<PreCalculatedResultFactory> resultList = new ArrayList<>();
   private final EconomicMap<PreCalculatedResultFactory, PreCalculatedResultFactory> resultDeDuplicationMap = EconomicMap.create();
   private final boolean trackLastGroup;
   private final Counter.ThresholdCounter stateID = new Counter.ThresholdCounter(3500, "TraceFinder NFA explosion");
   private final Counter.ThresholdCounter transitionID = new Counter.ThresholdCounter(3500, "TraceFinder NFA transition explosion");

   private NFATraceFinderGenerator(NFA originalNFA) {
      this.originalNFA = originalNFA;
      this.states = new ArrayList<>(originalNFA.getStates().length * 2);
      this.duplicatedStatesMap = new ArrayList[originalNFA.getStates().length];
      this.trackLastGroup = originalNFA.getAst().getOptions().getFlavor().usesLastGroupResultField();
   }

   public static NFA generateTraceFinder(NFA nfa) {
      return new NFATraceFinderGenerator(nfa).run();
   }

   private NFA run() {
      NFAState dummyInitialState = this.copy(this.originalNFA.getDummyInitialState());
      NFAStateTransition newAnchoredEntry = this.copyEntry(dummyInitialState, this.originalNFA.getReverseAnchoredEntry());
      NFAStateTransition newUnAnchoredEntry = this.copyEntry(dummyInitialState, this.originalNFA.getReverseUnAnchoredEntry());
      dummyInitialState.setPredecessors(new NFAStateTransition[]{newAnchoredEntry, newUnAnchoredEntry});
      ArrayList<NFATraceFinderGenerator.PathElement> graphPath = new ArrayList<>();

      for (NFAStateTransition entry : new NFAStateTransition[]{this.originalNFA.getAnchoredEntry()[0], this.originalNFA.getUnAnchoredEntry()[0]}) {
         label100:
         for (NFAStateTransition t : entry.getTarget().getSuccessors()) {
            NFATraceFinderGenerator.PathElement curElement = new NFATraceFinderGenerator.PathElement(t);

            while (true) {
               while (this.duplicatedStatesMap[curElement.getTransition().getTarget().getId()] != null) {
                  for (NFAState duplicate : this.duplicatedStatesMap[curElement.getTransition().getTarget().getId()]) {
                     int resultID = this.resultList.size();
                     if (resultID == 254) {
                        throw new UnsupportedRegexException("TraceFinder: too many possible results");
                     }

                     NFAState lastCopied = this.copy(entry.getTarget(), resultID);
                     PreCalculatedResultFactory result = this.resultFactory();
                     int iResult = 0;

                     for (int i = 0; i < graphPath.size(); i++) {
                        NFAStateTransition pathTransition = graphPath.get(i).getTransition();
                        NFAState copy = this.copy(pathTransition.getTarget(), resultID);
                        this.createTransition(lastCopied, copy, pathTransition, result, iResult);
                        iResult += this.getEncodedSize(copy);
                        lastCopied = copy;
                     }

                     this.createTransition(lastCopied, duplicate, curElement.getTransition(), result, iResult);

                     NFAState treeNode;
                     for (treeNode = duplicate; !treeNode.isFinalState(); treeNode = treeNode.getSuccessors()[0].getTarget()) {
                        iResult += this.getEncodedSize(treeNode);

                        assert ((NFAStateTransition[])treeNode.getSuccessors()).length == 1;

                        treeNode.addPossibleResult(resultID);
                        GroupBoundaries groupBoundaries = treeNode.getSuccessors()[0].getGroupBoundaries();
                        groupBoundaries.applyToResultFactory(result, iResult, this.trackLastGroup);
                     }

                     treeNode.addPossibleResult(resultID);
                     result.setLength(iResult);
                     PreCalculatedResultFactory existingResult = this.resultDeDuplicationMap.get(result);
                     if (existingResult == null) {
                        this.resultDeDuplicationMap.put(result, result);
                     } else {
                        result = existingResult;
                     }

                     this.resultList.add(result);

                     assert this.resultList.get(resultID) == result;
                  }

                  while (!graphPath.isEmpty() && !graphPath.get(graphPath.size() - 1).hasNextTransition()) {
                     graphPath.remove(graphPath.size() - 1);
                  }

                  if (graphPath.isEmpty()) {
                     continue label100;
                  }

                  curElement = new NFATraceFinderGenerator.PathElement(graphPath.get(graphPath.size() - 1).getNextTransition());
               }

               graphPath.add(curElement);
               curElement = new NFATraceFinderGenerator.PathElement(curElement.getNextTransition());
            }
         }
      }

      PreCalculatedResultFactory[] preCalculatedResults;
      if (this.resultDeDuplicationMap.size() == 1) {
         preCalculatedResults = new PreCalculatedResultFactory[]{this.resultList.get(0)};
      } else {
         preCalculatedResults = this.resultList.toArray(new PreCalculatedResultFactory[0]);
      }

      for (NFAState s : this.states) {
         s.linkPredecessors();
      }

      return new NFA(
         this.originalNFA.getAst(),
         dummyInitialState,
         null,
         null,
         newAnchoredEntry,
         newUnAnchoredEntry,
         this.states,
         this.stateID,
         this.transitionID,
         null,
         preCalculatedResults
      );
   }

   private NFAStateTransition createTransition(
      NFAState source, NFAState target, NFAStateTransition originalTransition, PreCalculatedResultFactory preCalcResult, int preCalcResultIndex
   ) {
      originalTransition.getGroupBoundaries().applyToResultFactory(preCalcResult, preCalcResultIndex, this.trackLastGroup);
      NFAStateTransition copy = new NFAStateTransition(
         (short)this.transitionID.inc(), source, target, originalTransition.getCodePointSet(), originalTransition.getGroupBoundaries()
      );
      source.setSuccessors(new NFAStateTransition[]{copy}, true);
      return copy;
   }

   private PreCalculatedResultFactory resultFactory() {
      return new PreCalculatedResultFactory(
         this.originalNFA.getAst().getNumberOfCaptureGroups(), this.originalNFA.getAst().getOptions().getFlavor().usesLastGroupResultField()
      );
   }

   private NFAStateTransition copyEntry(NFAState dummyInitialState, NFAStateTransition originalReverseEntry) {
      return new NFAStateTransition(
         (short)this.transitionID.inc(),
         this.copy(originalReverseEntry.getSource()),
         dummyInitialState,
         originalReverseEntry.getCodePointSet(),
         GroupBoundaries.getEmptyInstance(this.originalNFA.getAst().getLanguage())
      );
   }

   private NFAState copy(NFAState s) {
      NFAState copy = s.createTraceFinderCopy((short)this.stateID.inc());
      this.registerCopy(s, copy);
      return copy;
   }

   private NFAState copy(NFAState s, int resultID) {
      NFAState copy = this.copy(s);
      copy.addPossibleResult(resultID);
      return copy;
   }

   private void registerCopy(NFAState original, NFAState copy) {
      if (this.duplicatedStatesMap[original.getId()] == null) {
         this.duplicatedStatesMap[original.getId()] = new ArrayList<>();
      }

      this.duplicatedStatesMap[original.getId()].add(copy);
      this.states.add(copy);

      assert this.states.get(copy.getId()) == copy;
   }

   private int getEncodedSize(NFAState s) {
      Encodings.Encoding encoding = this.originalNFA.getAst().getEncoding();

      assert encoding.isFixedCodePointWidth(s.getCharSet());

      return encoding.getEncodedSize(s.getCharSet().getMin());
   }

   private static final class PathElement {
      private final NFAStateTransition transition;
      private int i = 0;

      private PathElement(NFAStateTransition transition) {
         this.transition = transition;
      }

      public NFAStateTransition getTransition() {
         return this.transition;
      }

      public boolean hasNextTransition() {
         return this.i < this.transition.getTarget().getSuccessors().length;
      }

      public NFAStateTransition getNextTransition() {
         return this.transition.getTarget().getSuccessors()[this.i++];
      }
   }
}
