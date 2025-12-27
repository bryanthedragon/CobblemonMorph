package com.oracle.truffle.regex.tregex.nodes.nfa;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.frame.MaterializedFrame;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.regex.RegexRootNode;
import com.oracle.truffle.regex.charset.CharMatchers;
import com.oracle.truffle.regex.charset.CodePointSet;
import com.oracle.truffle.regex.tregex.buffer.CompilationBuffer;
import com.oracle.truffle.regex.tregex.matchers.CharMatcher;
import com.oracle.truffle.regex.tregex.nfa.PureNFA;
import com.oracle.truffle.regex.tregex.nfa.PureNFAState;
import com.oracle.truffle.regex.tregex.nfa.PureNFATransition;
import com.oracle.truffle.regex.tregex.nfa.QuantifierGuard;
import com.oracle.truffle.regex.tregex.nodes.TRegexExecutorLocals;
import com.oracle.truffle.regex.tregex.nodes.TRegexExecutorNode;
import com.oracle.truffle.regex.tregex.nodes.input.InputIndexOfStringNode;
import com.oracle.truffle.regex.tregex.nodes.input.InputRegionMatchesNode;
import com.oracle.truffle.regex.tregex.parser.CaseFoldTable;
import com.oracle.truffle.regex.tregex.parser.Token;
import com.oracle.truffle.regex.tregex.parser.ast.Group;
import com.oracle.truffle.regex.tregex.parser.ast.InnerLiteral;
import com.oracle.truffle.regex.tregex.parser.ast.LookBehindAssertion;
import com.oracle.truffle.regex.tregex.parser.ast.QuantifiableTerm;
import com.oracle.truffle.regex.tregex.parser.ast.RegexAST;
import com.oracle.truffle.regex.tregex.parser.ast.RegexASTSubtreeRootNode;
import java.util.List;

public final class TRegexBacktrackingNFAExecutorNode extends TRegexBacktrackerSubExecutorNode {
   private static final int FLAG_WRITES_CAPTURE_GROUPS = 1;
   private static final int FLAG_FORWARD = 2;
   private static final int FLAG_IGNORE_CASE = 4;
   private static final int FLAG_UNICODE = 8;
   private static final int FLAG_BACKREF_WITH_NULL_TARGET_FAILS = 16;
   private static final int FLAG_MONITOR_CAPTURE_GROUPS_IN_EMPTY_CHECK = 32;
   private static final int FLAG_TRANSITION_MATCHES_STEP_BY_STEP = 64;
   private static final int FLAG_TRACK_LAST_GROUP = 128;
   private static final int FLAG_RETURNS_FIRST_GROUP = 256;
   private static final int FLAG_MUST_ADVANCE = 512;
   private static final int FLAG_LONE_SURROGATES = 1024;
   private static final int FLAG_LOOPBACK_INITIAL_STATE = 2048;
   private static final int FLAG_USE_MERGE_EXPLODE = 4096;
   private final PureNFA nfa;
   private final int nQuantifiers;
   private final int nZeroWidthQuantifiers;
   private final int maxNTransitions;
   private final int flags;
   private final InnerLiteral innerLiteral;
   @CompilerDirectives.CompilationFinal(dimensions = 1)
   private final CharMatcher[] matchers;
   private final int[] zeroWidthTermEnclosedCGLow;
   private final int[] zeroWidthQuantifierCGOffsets;
   @Node.Child
   InputRegionMatchesNode regionMatchesNode;
   @Node.Child
   InputIndexOfStringNode indexOfNode;
   private final CharMatcher loopbackInitialStateMatcher;
   private static final int IP_BEGIN = -1;
   private static final int IP_BACKTRACK = -2;
   private static final int IP_END = -3;

   public TRegexBacktrackingNFAExecutorNode(
      RegexAST ast,
      PureNFA nfa,
      int numberOfTransitions,
      TRegexBacktrackerSubExecutorNode[] subExecutors,
      boolean mustAdvance,
      CompilationBuffer compilationBuffer
   ) {
      super(ast, numberOfTransitions, subExecutors);
      RegexASTSubtreeRootNode subtree = nfa.getASTSubtree(ast);
      this.nfa = nfa;
      this.flags = createFlags(ast, nfa, mustAdvance, subtree);
      this.nQuantifiers = ast.getQuantifierCount().getCount();
      this.nZeroWidthQuantifiers = ast.getZeroWidthQuantifiables().size();
      List<QuantifiableTerm> zeroWidthQuantifiables = ast.getZeroWidthQuantifiables();
      this.zeroWidthTermEnclosedCGLow = new int[this.nZeroWidthQuantifiers];
      this.zeroWidthQuantifierCGOffsets = new int[this.zeroWidthTermEnclosedCGLow.length + 1];
      int offset = 0;

      for (int i = 0; i < this.nZeroWidthQuantifiers; i++) {
         QuantifiableTerm quantifiable = zeroWidthQuantifiables.get(i);
         if (quantifiable.isGroup()) {
            Group group = quantifiable.asGroup();
            this.zeroWidthTermEnclosedCGLow[i] = group.getEnclosedCaptureGroupsLow();
            offset += 2 * (group.getEnclosedCaptureGroupsHigh() - group.getEnclosedCaptureGroupsLow());
         }

         this.zeroWidthQuantifierCGOffsets[i + 1] = offset;
      }

      if (nfa.isRoot() && ast.getProperties().hasInnerLiteral()) {
         this.innerLiteral = ast.extractInnerLiteral();
      } else {
         this.innerLiteral = null;
      }

      if (this.isLoopbackInitialState() && this.innerLiteral == null) {
         CodePointSet initialCharSet = nfa.getMergedInitialStateCharSet(ast, compilationBuffer);
         this.loopbackInitialStateMatcher = initialCharSet == null ? null : CharMatchers.createMatcher(initialCharSet, compilationBuffer);
      } else {
         this.loopbackInitialStateMatcher = null;
      }

      nfa.materializeGroupBoundaries();
      this.matchers = new CharMatcher[nfa.getNumberOfStates()];
      int maxTransitions = 0;

      for (int i = 0; i < this.matchers.length; i++) {
         PureNFAState s = nfa.getState(i);
         if (s.isCharacterClass()) {
            this.matchers[i] = CharMatchers.createMatcher(s.getCharSet(), compilationBuffer);
         }

         maxTransitions = Math.max(maxTransitions, s.getSuccessors(this.isForward()).length);
         s.initIsDeterministic(this.isForward(), compilationBuffer);
      }

      this.maxNTransitions = maxTransitions;
   }

   private TRegexBacktrackingNFAExecutorNode(TRegexBacktrackingNFAExecutorNode copy) {
      super(copy);
      this.nfa = copy.nfa;
      this.nQuantifiers = copy.nQuantifiers;
      this.nZeroWidthQuantifiers = copy.nZeroWidthQuantifiers;
      this.maxNTransitions = copy.maxNTransitions;
      this.flags = copy.flags;
      this.innerLiteral = copy.innerLiteral;
      this.matchers = copy.matchers;
      this.zeroWidthTermEnclosedCGLow = copy.zeroWidthTermEnclosedCGLow;
      this.zeroWidthQuantifierCGOffsets = copy.zeroWidthQuantifierCGOffsets;
      this.loopbackInitialStateMatcher = copy.loopbackInitialStateMatcher;
   }

   @Override
   public TRegexBacktrackerSubExecutorNode shallowCopy() {
      return new TRegexBacktrackingNFAExecutorNode(this);
   }

   private static int createFlags(RegexAST ast, PureNFA nfa, boolean mustAdvance, RegexASTSubtreeRootNode subtree) {
      int flags = 0;
      flags = setFlag(flags, 1, subtree.hasCaptureGroups());
      flags = setFlag(flags, 2, !(subtree instanceof LookBehindAssertion));
      flags = setFlag(flags, 4, ast.getFlags().isIgnoreCase());
      flags = setFlag(flags, 8, ast.getFlags().isUnicode());
      flags = setFlag(flags, 16, ast.getOptions().getFlavor().backreferencesToUnmatchedGroupsFail());
      flags = setFlag(flags, 32, ast.getOptions().getFlavor().emptyChecksMonitorCaptureGroups());
      flags = setFlag(flags, 64, ast.getOptions().getFlavor().emptyChecksMonitorCaptureGroups());
      flags = setFlag(flags, 128, ast.getOptions().getFlavor().usesLastGroupResultField());
      flags = setFlag(flags, 256, !isFlagSet(flags, 2) && ast.getOptions().getFlavor().lookBehindsRunLeftToRight());
      flags = setFlag(flags, 512, mustAdvance);
      flags = setFlag(flags, 1024, ast.getProperties().hasLoneSurrogates());
      flags = setFlag(flags, 2048, nfa.isRoot() && !ast.getFlags().isSticky() && !ast.getRoot().startsWithCaret());
      return setFlag(flags, 4096, nfa.getNumberOfStates() <= 4000);
   }

   @Override
   public boolean writesCaptureGroups() {
      return this.isFlagSet(1);
   }

   @Override
   public String getName() {
      return "bt";
   }

   @Override
   public boolean isForward() {
      return this.isFlagSet(2);
   }

   public boolean isBackrefWithNullTargetFails() {
      return this.isFlagSet(16);
   }

   public boolean isMonitorCaptureGroupsInEmptyCheck() {
      return this.isFlagSet(32);
   }

   public boolean isTransitionMatchesStepByStep() {
      return this.isFlagSet(64);
   }

   public boolean isTrackLastGroup() {
      return this.isFlagSet(128);
   }

   public boolean isIgnoreCase() {
      return this.isFlagSet(4);
   }

   public boolean isUnicode() {
      return this.isFlagSet(8);
   }

   public boolean isMustAdvance() {
      return this.isFlagSet(512);
   }

   public boolean isLoneSurrogates() {
      return this.isFlagSet(1024);
   }

   public boolean isLoopbackInitialState() {
      return this.isFlagSet(2048);
   }

   public boolean isUseMergeExplode() {
      return this.isFlagSet(4096);
   }

   private boolean isFlagSet(int flag) {
      return isFlagSet(this.flags, flag);
   }

   @Override
   public TRegexExecutorLocals createLocals(Object input, int fromIndex, int index, int maxIndex) {
      return new TRegexBacktrackingNFAExecutorLocals(
         input,
         fromIndex,
         index,
         maxIndex,
         this.getNumberOfCaptureGroups(),
         this.nQuantifiers,
         this.nZeroWidthQuantifiers,
         this.zeroWidthTermEnclosedCGLow,
         this.zeroWidthQuantifierCGOffsets,
         this.isTransitionMatchesStepByStep(),
         this.maxNTransitions,
         this.isTrackLastGroup(),
         this.returnsFirstGroup()
      );
   }

   @Override
   public Object execute(VirtualFrame frame, TRegexExecutorLocals abstractLocals, TruffleString.CodeRange codeRange, boolean tString) {
      TRegexBacktrackingNFAExecutorLocals locals = (TRegexBacktrackingNFAExecutorLocals)abstractLocals;
      CompilerDirectives.ensureVirtualized(locals);
      if (this.innerLiteral != null) {
         locals.setIndex(locals.getFromIndex());
         int innerLiteralIndex = this.findInnerLiteral(locals, tString);
         if (innerLiteralIndex < 0) {
            return null;
         }

         locals.setLastInnerLiteralIndex(innerLiteralIndex);
         locals.setIndex(innerLiteralIndex);
         this.rewindUpTo(locals, locals.getFromIndex(), this.innerLiteral.getMaxPrefixSize());
      }

      if (this.isLoopbackInitialState()) {
         locals.setLastInitialStateIndex(locals.getIndex());
      }

      if (this.isUseMergeExplode()) {
         this.runMergeExplode(frame, locals, codeRange, tString);
      } else {
         this.runSlowPath(frame.materialize(), locals, codeRange, tString);
      }

      return locals.popResult();
   }

   @CompilerDirectives.TruffleBoundary
   private void runSlowPath(MaterializedFrame frame, TRegexBacktrackingNFAExecutorLocals locals, TruffleString.CodeRange codeRange, boolean tString) {
      this.runMergeExplode(frame, locals, codeRange, tString);
   }

   @ExplodeLoop(kind = ExplodeLoop.LoopExplosionKind.MERGE_EXPLODE)
   private void runMergeExplode(VirtualFrame frame, TRegexBacktrackingNFAExecutorLocals locals, TruffleString.CodeRange codeRange, boolean tString) {
      int ip = -1;

      label112:
      while (true) {
         locals.incLoopCount(this);
         if (CompilerDirectives.inInterpreter()) {
            RegexRootNode.checkThreadInterrupted();
         }

         CompilerAsserts.partialEvaluationConstant(ip);
         if (ip != -1) {
            if (ip == -2) {
               if (locals.canPopResult()) {
                  break;
               }

               if (!locals.canPop()) {
                  if (!this.isLoopbackInitialState()) {
                     break;
                  }

                  assert this.isForward();

                  locals.setIndex(locals.getLastInitialStateIndex());
                  if (!this.inputHasNext(locals)) {
                     break;
                  }

                  this.inputSkip(locals);
                  if (this.innerLiteral != null) {
                     if (locals.getLastInitialStateIndex() == locals.getLastInnerLiteralIndex()) {
                        int innerLiteralIndex = this.findInnerLiteral(locals, tString);
                        if (innerLiteralIndex < 0) {
                           break;
                        }

                        locals.setLastInnerLiteralIndex(innerLiteralIndex);
                        locals.setIndex(innerLiteralIndex);
                        this.rewindUpTo(locals, locals.getFromIndex(), this.innerLiteral.getMaxPrefixSize());
                     }
                  } else if (this.loopbackInitialStateMatcher != null) {
                     assert this.isForward();

                     while (this.inputHasNext(locals) && !this.loopbackInitialStateMatcher.match(this.inputReadAndDecode(locals))) {
                        this.inputAdvance(locals);
                     }
                  }

                  locals.setLastInitialStateIndex(locals.getIndex());
                  locals.resetToInitialState();
                  ip = this.nfa.getUnAnchoredInitialState(this.isForward()).getId();
               } else {
                  int nextIp = locals.pop();

                  for (int i = 0; i < this.nfa.getNumberOfStates(); i++) {
                     int stateId = this.nfa.getState(i).getId();
                     CompilerAsserts.partialEvaluationConstant(stateId);
                     if (stateId == nextIp) {
                        ip = stateId;
                        continue label112;
                     }
                  }

                  return;
               }
            } else {
               if (ip == -3) {
                  break;
               }

               PureNFAState curState = this.nfa.getState(ip);
               CompilerAsserts.partialEvaluationConstant(curState);
               PureNFATransition[] successors = curState.getSuccessors(this.isForward());
               CompilerAsserts.partialEvaluationConstant(successors);
               CompilerAsserts.partialEvaluationConstant(successors.length);
               int nextIp = this.runState(frame, locals, codeRange, tString, curState);

               for (int ix = 0; ix < successors.length; ix++) {
                  int targetIp = successors[ix].getTarget(this.isForward()).getId();
                  if (targetIp == nextIp) {
                     CompilerAsserts.partialEvaluationConstant(targetIp);
                     ip = targetIp;
                     continue label112;
                  }
               }

               if (nextIp != -2) {
                  assert nextIp == -3;
                  break;
               }

               ip = -2;
            }
         } else if (this.nfa.getAnchoredInitialState(this.isForward()) != this.nfa.getUnAnchoredInitialState(this.isForward()) && this.inputAtBegin(locals)) {
            ip = this.nfa.getAnchoredInitialState(this.isForward()).getId();
         } else {
            ip = this.nfa.getUnAnchoredInitialState(this.isForward()).getId();
         }
      }
   }

   @ExplodeLoop
   private int runState(
      VirtualFrame frame, TRegexBacktrackingNFAExecutorLocals locals, TruffleString.CodeRange codeRange, boolean tString, PureNFAState curState
   ) {
      CompilerAsserts.partialEvaluationConstant(curState);
      if (this.isAcceptableFinalState(curState, locals)) {
         locals.setResult();
         locals.pushResult();
         return -3;
      } else {
         if (curState.isSubMatcher() && !this.canInlineLookAroundIntoTransition(curState)) {
            TRegexBacktrackingNFAExecutorLocals subLocals = locals.createSubNFALocals(this.subExecutorReturnsFirstGroup(curState));
            int[] subMatchResult = this.runSubMatcher(frame, subLocals, codeRange, tString, curState);
            if (subMatchFailed(curState, subMatchResult)) {
               return -2;
            }

            if (!curState.isSubMatcherNegated() && this.getSubExecutor(curState).writesCaptureGroups()) {
               locals.overwriteCaptureGroups(subMatchResult);
            }

            if (!curState.isLookAround()) {
               locals.saveIndex(subLocals.getIndex());
               locals.restoreIndex();
            }
         }

         if (curState.isBackReference() && !this.canInlineBackReferenceIntoTransition()) {
            int backrefResult = this.matchBackReferenceGeneric(
               locals, locals.getCaptureGroupStart(curState.getBackRefNumber()), locals.getCaptureGroupEnd(curState.getBackRefNumber())
            );
            if (backrefResult < 0) {
               return -2;
            }

            locals.setIndex(backrefResult);
         }

         PureNFATransition[] successors = curState.getSuccessors(this.isForward());
         CompilerAsserts.partialEvaluationConstant(successors);
         CompilerAsserts.partialEvaluationConstant(successors.length);
         boolean atEnd = this.inputAtEnd(locals);
         int c = atEnd ? 0 : this.inputReadAndDecode(locals);
         int index = locals.getIndex();
         if (curState.isDeterministic()) {
            if (this.isTransitionMatchesStepByStep()) {
               int[] currentFrame = locals.getStackFrameBuffer();
               locals.readFrame(currentFrame);

               for (int i = 0; i < successors.length; i++) {
                  PureNFATransition transition = successors[i];
                  CompilerAsserts.partialEvaluationConstant(transition);
                  if (this.tryUpdateState(frame, locals, codeRange, tString, transition, index, atEnd, c)) {
                     locals.restoreIndex();
                     return transition.getTarget(this.isForward()).getId();
                  }

                  locals.writeFrame(currentFrame);
               }

               return -2;
            } else {
               for (int i = 0; i < successors.length; i++) {
                  PureNFATransition transition = successors[i];
                  CompilerAsserts.partialEvaluationConstant(transition);
                  if (this.transitionMatches(frame, locals, codeRange, tString, transition, index, atEnd, c)) {
                     this.updateState(locals, transition, index);
                     locals.restoreIndex();
                     return transition.getTarget(this.isForward()).getId();
                  }
               }

               return -2;
            }
         } else if (this.isTransitionMatchesStepByStep()) {
            boolean hasMatchingTransition = false;
            boolean transitionToFinalStateWins = false;
            int[] currentFrame = locals.getStackFrameBuffer();
            locals.readFrame(currentFrame);

            for (int ix = successors.length - 1; ix >= 0; ix--) {
               PureNFATransition transition = successors[ix];
               CompilerAsserts.partialEvaluationConstant(transition);
               if (this.tryUpdateState(frame, locals, codeRange, tString, transition, index, atEnd, c)) {
                  hasMatchingTransition = true;
                  PureNFAState target = transition.getTarget(this.isForward());
                  CompilerAsserts.partialEvaluationConstant(target);
                  if (this.isAcceptableFinalState(target, locals)) {
                     locals.setResult();
                     locals.pushResult();
                     transitionToFinalStateWins = true;
                     locals.writeFrame(currentFrame);
                  } else {
                     locals.setPc(target.getId());
                     transitionToFinalStateWins = false;
                     locals.pushFrame(currentFrame);
                  }
               } else {
                  locals.writeFrame(currentFrame);
               }
            }

            if (transitionToFinalStateWins) {
               return -3;
            } else if (hasMatchingTransition) {
               locals.pop();
               return locals.getPc();
            } else {
               return -2;
            }
         } else {
            long[] transitionBitSet = locals.getTransitionBitSet();
            CompilerDirectives.ensureVirtualized(transitionBitSet);
            int bitSetWords = (successors.length - 1 >> 6) + 1;
            CompilerAsserts.partialEvaluationConstant(bitSetWords);
            int lastMatch = 0;
            int lastFinal = 0;
            int nMatched = -1;

            for (int iBS = 0; iBS < bitSetWords; iBS++) {
               CompilerAsserts.partialEvaluationConstant(iBS);
               long bs = 0L;
               long bit = 1L;
               int iStart = successors.length - (iBS << 6) - 1;
               int iEnd = Math.max(-1, iStart - 64);
               CompilerAsserts.partialEvaluationConstant(iStart);
               CompilerAsserts.partialEvaluationConstant(iEnd);

               for (int ixx = iStart; ixx > iEnd; ixx--) {
                  PureNFATransition transition = successors[ixx];
                  CompilerAsserts.partialEvaluationConstant(transition);
                  if (this.transitionMatches(frame, locals, codeRange, tString, transition, index, atEnd, c)) {
                     bs |= bit;
                     lastMatch = ixx;
                     if (this.isAcceptableFinalState(transition.getTarget(this.isForward()), locals)) {
                        locals.setResult();
                        lastFinal = ixx;
                        nMatched--;
                     }
                  }

                  bit <<= 1;
               }

               transitionBitSet[iBS] = bs;
            }

            for (int iBS = 0; iBS < bitSetWords; iBS++) {
               nMatched += Long.bitCount(transitionBitSet[iBS]);
            }

            if (nMatched > 0) {
               locals.dupFrame(nMatched);
            }

            for (int iBS = 0; iBS < bitSetWords; iBS++) {
               CompilerAsserts.partialEvaluationConstant(iBS);
               long bs = transitionBitSet[iBS];
               int iStart = successors.length - (iBS << 6) - 1;
               int iEnd = Math.max(-1, iStart - 64);
               CompilerAsserts.partialEvaluationConstant(iStart);
               CompilerAsserts.partialEvaluationConstant(iEnd);

               for (int ixx = iStart; ixx > iEnd; ixx--) {
                  PureNFATransition transition = successors[ixx];
                  CompilerAsserts.partialEvaluationConstant(transition);
                  PureNFAState target = transition.getTarget(this.isForward());
                  CompilerAsserts.partialEvaluationConstant(target);
                  if ((bs & 1L) != 0L) {
                     if (this.isAcceptableFinalState(target, locals)) {
                        if (ixx == lastFinal) {
                           locals.pushResult(transition, index);
                        }

                        if (ixx == lastMatch) {
                           return -3;
                        }
                     } else {
                        this.updateState(locals, transition, index);
                        if (ixx == lastMatch) {
                           locals.restoreIndex();
                           return target.getId();
                        }

                        locals.setPc(target.getId());
                        locals.push();
                     }
                  }

                  bs >>>= 1;
               }
            }

            return -2;
         }
      }
   }

   private boolean isAcceptableFinalState(PureNFAState state, TRegexBacktrackingNFAExecutorLocals locals) {
      return state.isFinalState(this.isForward()) && (!this.isMustAdvance() || locals.getIndex() != locals.getFromIndex());
   }

   public boolean returnsFirstGroup() {
      return this.isFlagSet(256);
   }

   private TRegexExecutorNode getSubExecutor(PureNFAState subMatcherState) {
      return this.subExecutors[subMatcherState.getSubtreeId()];
   }

   protected boolean lookAroundExecutorIsLiteral(PureNFAState s) {
      return this.getSubExecutor(s) instanceof TRegexLiteralLookAroundExecutorNode;
   }

   private boolean subExecutorReturnsFirstGroup(PureNFAState s) {
      TRegexExecutorNode executor = this.getSubExecutor(s);
      return executor instanceof TRegexBacktrackingNFAExecutorNode ? ((TRegexBacktrackingNFAExecutorNode)executor).returnsFirstGroup() : false;
   }

   private boolean canInlineLookAroundIntoTransition(PureNFAState s) {
      return s.isLookAround()
         && (s.getPredecessors().length == 1 || this.lookAroundExecutorIsLiteral(s))
         && (s.isSubMatcherNegated() || !this.getSubExecutor(s).writesCaptureGroups());
   }

   private boolean checkSubMatcherInline(
      VirtualFrame frame,
      TRegexBacktrackingNFAExecutorLocals locals,
      TruffleString.CodeRange codeRange,
      boolean tString,
      PureNFATransition transition,
      PureNFAState target
   ) {
      if (this.lookAroundExecutorIsLiteral(target)) {
         TRegexLiteralLookAroundExecutorNode literal = (TRegexLiteralLookAroundExecutorNode)this.getSubExecutor(target);
         int saveIndex = locals.getIndex();
         int saveNextIndex = locals.getNextIndex();
         boolean result = (Boolean)literal.execute(frame, locals, codeRange, tString);
         locals.setIndex(saveIndex);
         locals.setNextIndex(saveNextIndex);
         return result;
      } else {
         return !subMatchFailed(
            target, this.runSubMatcher(frame, locals.createSubNFALocals(transition, this.subExecutorReturnsFirstGroup(target)), codeRange, tString, target)
         );
      }
   }

   protected int[] runSubMatcher(
      VirtualFrame frame, TRegexBacktrackingNFAExecutorLocals subLocals, TruffleString.CodeRange codeRange, boolean tString, PureNFAState subMatcherState
   ) {
      return (int[])this.getSubExecutor(subMatcherState).execute(frame, subLocals, codeRange, tString);
   }

   protected static boolean subMatchFailed(PureNFAState curState, Object subMatchResult) {
      return subMatchResult == null != curState.isSubMatcherNegated();
   }

   @ExplodeLoop
   protected boolean transitionMatches(
      VirtualFrame frame,
      TRegexBacktrackingNFAExecutorLocals locals,
      TruffleString.CodeRange codeRange,
      boolean tString,
      PureNFATransition transition,
      int index,
      boolean atEnd,
      int c
   ) {
      PureNFAState target = transition.getTarget(this.isForward());
      CompilerAsserts.partialEvaluationConstant(target);
      if (transition.hasCaretGuard() && index != 0) {
         return false;
      } else if (transition.hasDollarGuard() && index < locals.getMaxIndex()) {
         return false;
      } else {
         int nGuards = transition.getQuantifierGuards().length;

         for (int i = this.isForward() ? 0 : nGuards - 1; this.isForward() ? i < nGuards : i >= 0; i = this.inputIncRaw(i)) {
            QuantifierGuard guard = transition.getQuantifierGuards()[i];
            CompilerAsserts.partialEvaluationConstant(guard);
            Token.Quantifier q = guard.getQuantifier();
            CompilerAsserts.partialEvaluationConstant(q);
            switch (this.isForward() ? guard.getKind() : guard.getKindReverse()) {
               case loop:
                  if (locals.getQuantifierCount(q) == q.getMax()) {
                     return false;
                  }
                  break;
               case exit:
                  if (locals.getQuantifierCount(q) < q.getMin()) {
                     return false;
                  }
                  break;
               case exitZeroWidth:
                  if (locals.getZeroWidthQuantifierGuardIndex(q) == index
                     && (!this.isMonitorCaptureGroupsInEmptyCheck() || locals.isResultUnmodifiedByZeroWidthQuantifier(q))
                     && (!q.hasIndex() || locals.getQuantifierCount(q) > q.getMin())) {
                     return false;
                  }
                  break;
               case escapeZeroWidth:
                  if (locals.getZeroWidthQuantifierGuardIndex(q) != index
                     || this.isMonitorCaptureGroupsInEmptyCheck() && !locals.isResultUnmodifiedByZeroWidthQuantifier(q)) {
                     return false;
                  }
                  break;
               case enterEmptyMatch:
                  if (locals.getQuantifierCount(q) >= q.getMin()) {
                     return false;
                  }
            }
         }

         switch (target.getKind()) {
            case 0:
               assert !target.isInitialState(this.isForward());

               return target.isAnchoredFinalState(this.isForward()) ? atEnd : true;
            case 1:
               return !atEnd && this.matchers[target.getId()].match(c);
            case 2:
               if (this.canInlineLookAroundIntoTransition(target)) {
                  return this.checkSubMatcherInline(frame, locals, codeRange, tString, transition, target);
               }

               return true;
            case 3:
               if (this.canInlineBackReferenceIntoTransition()) {
                  int start = getBackRefBoundary(locals, transition, target.getBackRefNumber() * 2, index);
                  int end = getBackRefBoundary(locals, transition, target.getBackRefNumber() * 2 + 1, index);
                  if (start >= 0 && end >= 0) {
                     return this.matchBackReferenceSimple(locals, start, end, index);
                  }

                  return !this.isBackrefWithNullTargetFails();
               }

               return true;
            case 4:
               return true;
            default:
               throw CompilerDirectives.shouldNotReachHere();
         }
      }
   }

   protected static int getBackRefBoundary(TRegexBacktrackingNFAExecutorLocals locals, PureNFATransition transition, int cgIndex, int index) {
      return transition.getGroupBoundaries().getUpdateIndices().get(cgIndex)
         ? index
         : (transition.getGroupBoundaries().getClearIndices().get(cgIndex) ? -1 : locals.getCaptureGroupBoundary(cgIndex));
   }

   @ExplodeLoop
   protected void updateState(TRegexBacktrackingNFAExecutorLocals locals, PureNFATransition transition, int index) {
      CompilerAsserts.partialEvaluationConstant(transition);
      locals.apply(transition, index);
      int nGuards = transition.getQuantifierGuards().length;

      for (int i = this.isForward() ? 0 : nGuards - 1; this.isForward() ? i < nGuards : i >= 0; i += this.isForward() ? 1 : -1) {
         QuantifierGuard guard = transition.getQuantifierGuards()[i];
         CompilerAsserts.partialEvaluationConstant(guard);
         Token.Quantifier q = guard.getQuantifier();
         CompilerAsserts.partialEvaluationConstant(q);
         switch (this.isForward() ? guard.getKind() : guard.getKindReverse()) {
            case loop:
            case enter:
            case loopInc:
               locals.incQuantifierCount(q);
               break;
            case exit:
            case exitReset:
               locals.resetQuantifierCount(q);
            case exitZeroWidth:
            case escapeZeroWidth:
            default:
               break;
            case enterEmptyMatch:
               if (!transition.hasCaretGuard() && !transition.hasDollarGuard()) {
                  locals.setQuantifierCount(q, q.getMin());
               } else {
                  locals.incQuantifierCount(q);
               }
               break;
            case enterZeroWidth:
               locals.setZeroWidthQuantifierGuardIndex(q);
               locals.setZeroWidthQuantifierResults(q);
         }
      }

      locals.saveIndex(this.getNewIndex(locals, transition.getTarget(this.isForward()), index));
   }

   @ExplodeLoop
   protected boolean tryUpdateState(
      VirtualFrame frame,
      TRegexBacktrackingNFAExecutorLocals locals,
      TruffleString.CodeRange codeRange,
      boolean tString,
      PureNFATransition transition,
      int index,
      boolean atEnd,
      int c
   ) {
      CompilerAsserts.partialEvaluationConstant(transition);
      PureNFAState target = transition.getTarget(this.isForward());
      CompilerAsserts.partialEvaluationConstant(target);
      if (transition.hasCaretGuard() && index != 0) {
         return false;
      } else if (transition.hasDollarGuard() && index < locals.getMaxIndex()) {
         return false;
      } else {
         switch (target.getKind()) {
            case 0:
               assert !target.isInitialState(this.isForward());

               if (target.isAnchoredFinalState(this.isForward()) && !atEnd) {
                  return false;
               }
               break;
            case 1:
               if (atEnd || !this.matchers[target.getId()].match(c)) {
                  return false;
               }
               break;
            case 2:
               if (this.canInlineLookAroundIntoTransition(target) && !this.checkSubMatcherInline(frame, locals, codeRange, tString, transition, target)) {
                  return false;
               }
               break;
            case 3:
               if (this.canInlineBackReferenceIntoTransition()) {
                  int start = getBackRefBoundary(locals, transition, target.getBackRefNumber() * 2, index);
                  int end = getBackRefBoundary(locals, transition, target.getBackRefNumber() * 2 + 1, index);
                  if ((start < 0 || end < 0) && this.isBackrefWithNullTargetFails()) {
                     return false;
                  }

                  if (!this.matchBackReferenceSimple(locals, start, end, index)) {
                     return false;
                  }
               }
            case 4:
               break;
            default:
               throw CompilerDirectives.shouldNotReachHere();
         }

         int nGuards = transition.getQuantifierGuards().length;

         for (int i = this.isForward() ? 0 : nGuards - 1; this.isForward() ? i < nGuards : i >= 0; i = this.inputIncRaw(i)) {
            QuantifierGuard guard = transition.getQuantifierGuards()[i];
            CompilerAsserts.partialEvaluationConstant(guard);
            Token.Quantifier q = guard.getQuantifier();
            CompilerAsserts.partialEvaluationConstant(q);
            switch (this.isForward() ? guard.getKind() : guard.getKindReverse()) {
               case loop:
                  if (locals.getQuantifierCount(q) == q.getMax()) {
                     return false;
                  }

                  locals.incQuantifierCount(q);
                  break;
               case exit:
                  if (locals.getQuantifierCount(q) < q.getMin()) {
                     return false;
                  }

                  locals.resetQuantifierCount(q);
                  break;
               case exitZeroWidth:
                  if (locals.getZeroWidthQuantifierGuardIndex(q) == index
                     && (!this.isMonitorCaptureGroupsInEmptyCheck() || locals.isResultUnmodifiedByZeroWidthQuantifier(q))
                     && (!q.hasIndex() || locals.getQuantifierCount(q) > q.getMin())) {
                     return false;
                  }
                  break;
               case escapeZeroWidth:
                  if (locals.getZeroWidthQuantifierGuardIndex(q) != index
                     || this.isMonitorCaptureGroupsInEmptyCheck() && !locals.isResultUnmodifiedByZeroWidthQuantifier(q)) {
                     return false;
                  }
                  break;
               case enterEmptyMatch:
                  if (locals.getQuantifierCount(q) >= q.getMin()) {
                     return false;
                  }

                  if (!transition.hasCaretGuard() && !transition.hasDollarGuard()) {
                     locals.setQuantifierCount(q, q.getMin());
                  } else {
                     locals.incQuantifierCount(q);
                  }
                  break;
               case enter:
               case loopInc:
                  locals.incQuantifierCount(q);
                  break;
               case exitReset:
                  locals.resetQuantifierCount(q);
                  break;
               case enterZeroWidth:
                  locals.setZeroWidthQuantifierGuardIndex(q);
                  locals.setZeroWidthQuantifierResults(q);
                  break;
               case updateCG:
                  locals.setCaptureGroupBoundary(guard.getIndex(), index);
            }
         }

         locals.saveIndex(this.getNewIndex(locals, target, index));
         return true;
      }
   }

   private int getNewIndex(TRegexBacktrackingNFAExecutorLocals locals, PureNFAState target, int index) {
      CompilerAsserts.partialEvaluationConstant(target.getKind());
      switch (target.getKind()) {
         case 0:
            return index;
         case 1:
            return locals.getNextIndex();
         case 2:
            return index;
         case 3:
            if (this.canInlineBackReferenceIntoTransition()) {
               int end = locals.getCaptureGroupEnd(target.getBackRefNumber());
               int start = locals.getCaptureGroupStart(target.getBackRefNumber());
               if (start >= 0 && end >= 0) {
                  int length = end - start;
                  return this.isForward() ? index + length : index - length;
               }

               return index;
            }

            return index;
         case 4:
            return index;
         default:
            throw CompilerDirectives.shouldNotReachHere();
      }
   }

   private boolean canInlineBackReferenceIntoTransition() {
      return !this.isIgnoreCase() && !this.isLoneSurrogates();
   }

   private boolean matchBackReferenceSimple(TRegexBacktrackingNFAExecutorLocals locals, int backrefStart, int backrefEnd, int index) {
      assert !this.isIgnoreCase() && !this.isLoneSurrogates();

      if (this.regionMatchesNode == null) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         this.regionMatchesNode = this.insert(InputRegionMatchesNode.create());
      }

      int inputLength = locals.getMaxIndex();
      int backrefLength = backrefEnd - backrefStart;
      if (backrefLength == 0) {
         return true;
      } else {
         return (this.isForward() ? index + backrefLength <= inputLength : index - backrefLength >= 0)
            ? this.regionMatchesNode
               .execute(
                  locals.getInput(), backrefStart, locals.getInput(), this.isForward() ? index : index - backrefLength, backrefLength, null, this.getEncoding()
               )
            : false;
      }
   }

   private int matchBackReferenceGeneric(TRegexBacktrackingNFAExecutorLocals locals, int backrefStart, int backrefEnd) {
      assert this.isIgnoreCase() || this.isLoneSurrogates();

      if (!this.isBackrefWithNullTargetFails() || backrefStart >= 0 && backrefEnd >= 0) {
         int index = locals.getIndex();
         int inputLength = locals.getMaxIndex();
         int iBR = this.isForward() ? backrefStart : backrefEnd;

         int i;
         for (i = index; this.inputBoundsCheck(iBR, backrefStart, backrefEnd); i = this.inputIncRaw(i)) {
            if (!this.inputBoundsCheck(i, 0, inputLength)) {
               return -1;
            }

            int codePointBR = this.inputReadRaw(locals, iBR);
            if (this.isUTF16()
               && this.isUnicode()
               && this.inputUTF16IsHighSurrogate(codePointBR)
               && this.inputBoundsCheck(this.inputIncRaw(iBR), backrefStart, backrefEnd)) {
               int lowSurrogate = this.inputReadRaw(locals, this.inputIncRaw(iBR));
               if (this.inputUTF16IsLowSurrogate(lowSurrogate)) {
                  codePointBR = this.inputUTF16ToCodePoint(codePointBR, lowSurrogate);
                  iBR = this.inputIncRaw(iBR);
               }
            }

            int codePointI = this.inputReadRaw(locals, i);
            if (this.isUTF16() && this.isUnicode() && this.inputUTF16IsHighSurrogate(codePointI) && this.inputBoundsCheck(this.inputIncRaw(i), 0, inputLength)) {
               int lowSurrogate = this.inputReadRaw(locals, this.inputIncRaw(i));
               if (this.inputUTF16IsLowSurrogate(lowSurrogate)) {
                  codePointI = this.inputUTF16ToCodePoint(codePointI, lowSurrogate);
                  i = this.inputIncRaw(i);
               }
            }

            if (this.isIgnoreCase() ? !this.equalsIgnoreCase(codePointBR, codePointI) : codePointBR != codePointI) {
               return -1;
            }

            iBR = this.inputIncRaw(iBR);
         }

         return i;
      } else {
         return -1;
      }
   }

   private int findInnerLiteral(TRegexBacktrackingNFAExecutorLocals locals, boolean tString) {
      if (this.indexOfNode == null) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         this.indexOfNode = this.insert(InputIndexOfStringNode.create());
      }

      return this.indexOfNode
         .execute(
            locals.getInput(),
            locals.getIndex(),
            locals.getMaxIndex(),
            this.innerLiteral.getLiteralContent(tString),
            this.innerLiteral.getMaskContent(tString),
            this.getEncoding()
         );
   }

   private boolean inputBoundsCheck(int i, int min, int max) {
      return this.isForward() ? i < max : i > min;
   }

   private boolean equalsIgnoreCase(int a, int b) {
      return CaseFoldTable.equalsIgnoreCase(
         a, b, this.isUnicode() ? CaseFoldTable.CaseFoldingAlgorithm.ECMAScriptUnicode : CaseFoldTable.CaseFoldingAlgorithm.ECMAScriptNonUnicode
      );
   }

   private static int setFlag(int flags, int flag, boolean value) {
      return value ? flags | flag : flags & ~flag;
   }

   private static boolean isFlagSet(int flags, int flag) {
      return (flags & flag) != 0;
   }
}
