package com.oracle.truffle.regex.tregex.parser.ast.visitors;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.regex.tregex.automaton.StateSet;
import com.oracle.truffle.regex.tregex.buffer.LongArrayBuffer;
import com.oracle.truffle.regex.tregex.nfa.QuantifierGuard;
import com.oracle.truffle.regex.tregex.parser.Token;
import com.oracle.truffle.regex.tregex.parser.ast.Group;
import com.oracle.truffle.regex.tregex.parser.ast.GroupBoundaries;
import com.oracle.truffle.regex.tregex.parser.ast.LookAheadAssertion;
import com.oracle.truffle.regex.tregex.parser.ast.LookBehindAssertion;
import com.oracle.truffle.regex.tregex.parser.ast.PositionAssertion;
import com.oracle.truffle.regex.tregex.parser.ast.RegexAST;
import com.oracle.truffle.regex.tregex.parser.ast.RegexASTNode;
import com.oracle.truffle.regex.tregex.parser.ast.Sequence;
import com.oracle.truffle.regex.tregex.parser.ast.Term;
import com.oracle.truffle.regex.util.TBitSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;
import org.graalvm.collections.EconomicMap;
import org.graalvm.collections.EconomicSet;

public abstract class NFATraversalRegexASTVisitor {
   protected final RegexAST ast;
   private Term root;
   private final LongArrayBuffer curPath = new LongArrayBuffer(8);
   private final EconomicMap<RegexASTNode, Integer> insideLoops;
   private final StateSet<RegexAST, Group> insideEmptyGuardGroup;
   private RegexASTNode cur;
   private Set<LookBehindAssertion> traversableLookBehindAssertions;
   private boolean canTraverseCaret = false;
   private boolean forward = true;
   private boolean done = false;
   private final EconomicSet<NFATraversalRegexASTVisitor.DeduplicationKey> pathDeduplicationSet = EconomicSet.create();
   private final StateSet<RegexAST, RegexASTNode> lookAroundsOnPath;
   private final StateSet<RegexAST, RegexASTNode> dollarsOnPath;
   private final StateSet<RegexAST, RegexASTNode> caretsOnPath;
   private final int[] nodeVisitCount;
   private final List<NFATraversalRegexASTVisitor.CaptureGroupEvent> captureGroupEvents = new ArrayList<>();
   private TBitSet captureGroupUpdates;
   private TBitSet captureGroupClears;
   private int lastGroup = -1;
   private NFATraversalRegexASTVisitor.QuantifierGuardsLinkedList quantifierGuards = null;
   private QuantifierGuard[] quantifierGuardsResult = null;
   private final int[] quantifierGuardsLoop;
   private final int[] quantifierGuardsExited;
   private static final int PATH_GROUP_ALT_INDEX_OFFSET = 0;
   private static final int PATH_NODE_OFFSET = 16;
   private static final int PATH_GROUP_ACTION_OFFSET = 48;
   private static final long PATH_GROUP_ACTION_ENTER = 281474976710656L;
   private static final long PATH_GROUP_ACTION_EXIT = 562949953421312L;
   private static final long PATH_GROUP_ACTION_PASS_THROUGH = 1125899906842624L;
   private static final long PATH_GROUP_ACTION_ESCAPE = 2251799813685248L;
   private static final long PATH_GROUP_ACTION_ANY = 4222124650659840L;

   protected NFATraversalRegexASTVisitor(RegexAST ast) {
      this.ast = ast;
      this.insideLoops = EconomicMap.create();
      this.insideEmptyGuardGroup = StateSet.create(ast);
      this.lookAroundsOnPath = StateSet.create(ast);
      this.dollarsOnPath = StateSet.create(ast);
      this.caretsOnPath = StateSet.create(ast);
      this.nodeVisitCount = new int[ast.getNumberOfStates()];
      this.captureGroupUpdates = new TBitSet(ast.getNumberOfCaptureGroups() * 2);
      this.captureGroupClears = new TBitSet(ast.getNumberOfCaptureGroups() * 2);
      this.quantifierGuardsLoop = new int[ast.getQuantifierCount().getCount()];
      this.quantifierGuardsExited = new int[ast.getQuantifierCount().getCount()];
   }

   public Set<LookBehindAssertion> getTraversableLookBehindAssertions() {
      return this.traversableLookBehindAssertions;
   }

   public void setTraversableLookBehindAssertions(Set<LookBehindAssertion> traversableLookBehindAssertions) {
      this.traversableLookBehindAssertions = traversableLookBehindAssertions;
   }

   public boolean canTraverseCaret() {
      return this.canTraverseCaret;
   }

   public void setCanTraverseCaret(boolean canTraverseCaret) {
      this.canTraverseCaret = canTraverseCaret;
   }

   public void setReverse() {
      this.forward = false;
   }

   protected abstract boolean canTraverseLookArounds();

   protected void run(Term runRoot) {
      assert this.insideLoops.isEmpty();

      assert this.insideEmptyGuardGroup.isEmpty();

      assert this.curPath.isEmpty();

      assert this.dollarsOnPath.isEmpty();

      assert this.caretsOnPath.isEmpty();

      assert this.lookAroundsOnPath.isEmpty();

      assert this.nodeVisitsEmpty() : Arrays.toString(this.nodeVisitCount);

      assert Arrays.stream(this.quantifierGuardsLoop).allMatch(x -> x == 0);

      assert Arrays.stream(this.quantifierGuardsExited).allMatch(x -> x == 0);

      assert this.quantifierGuards == null;

      assert this.captureGroupEvents.isEmpty();

      assert this.captureGroupUpdates.isEmpty();

      assert this.captureGroupClears.isEmpty();

      assert this.lastGroup == -1;

      this.root = runRoot;
      if (this.useQuantifierGuards() && this.root.isGroup() && !this.root.getParent().isSubtreeRoot()) {
         Group emptyMatch = this.root.getParent().getParent().asGroup();
         this.pushQuantifierGuard(QuantifierGuard.createExitEmptyMatch(emptyMatch.getQuantifier()));
      }

      this.pathDeduplicationSet.clear();
      if (runRoot.isGroup() && runRoot.getParent().isSubtreeRoot()) {
         this.cur = runRoot;
      } else {
         this.advanceTerm(runRoot);
      }

      while (!this.done) {
         boolean foundNextTarget = false;

         while (!this.done && !foundNextTarget) {
            foundNextTarget = this.doAdvance();
         }

         if (this.done) {
            break;
         }

         RegexASTNode target = this.pathGetNode(this.curPath.peek());
         this.visit(target);
         if (target.isMatchFound()
            && this.forward
            && !this.dollarsOnPath()
            && this.lookAroundsOnPath.isEmpty()
            && !this.hasQuantifierGuards()
            && !this.caretsOnPath()) {
            this.insideLoops.clear();
            this.insideEmptyGuardGroup.clear();
            this.curPath.clear();
            this.clearCaptureGroupData();
            this.clearQuantifierGuards();
            this.quantifierGuardsResult = null;
            break;
         }

         this.quantifierGuardsResult = null;
         this.retreat();
      }

      if (this.useQuantifierGuards() && this.root.isGroup() && !this.root.getParent().isSubtreeRoot()) {
         Group emptyMatch = this.root.getParent().getParent().asGroup();
         this.popQuantifierGuard(QuantifierGuard.createExitEmptyMatch(emptyMatch.getQuantifier()));
      }

      this.done = false;
   }

   protected abstract void visit(RegexASTNode target);

   protected abstract void enterLookAhead(LookAheadAssertion assertion);

   protected abstract void leaveLookAhead(LookAheadAssertion assertion);

   protected boolean caretsOnPath() {
      return !this.caretsOnPath.isEmpty();
   }

   protected boolean dollarsOnPath() {
      return !this.dollarsOnPath.isEmpty();
   }

   protected boolean hasQuantifierGuards() {
      this.calcQuantifierGuards();
      return this.quantifierGuardsResult.length > 0;
   }

   protected QuantifierGuard[] getQuantifierGuardsOnPath() {
      this.calcQuantifierGuards();
      return this.quantifierGuardsResult;
   }

   protected void calcQuantifierGuards() {
      if (this.quantifierGuardsResult == null) {
         assert this.useQuantifierGuards() || this.quantifierGuards == null;

         RegexASTNode target = this.curPath.isEmpty() ? null : this.pathGetNode(this.curPath.peek());
         if (this.useQuantifierGuards()
            && target != null
            && target.isGroup()
            && !target.getParent().isSubtreeRoot()
            && target.getParent().getParent().asGroup().hasQuantifier()) {
            Group emptyMatch = target.getParent().getParent().asGroup();
            QuantifierGuard finalGuard = QuantifierGuard.createEnterEmptyMatch(emptyMatch.getQuantifier());
            this.pushQuantifierGuard(finalGuard);
            this.quantifierGuardsResult = this.quantifierGuards
               .toArray(
                  guard -> guard.getKind() == QuantifierGuard.Kind.enterEmptyMatch
                     || guard.getKind() == QuantifierGuard.Kind.exitEmptyMatch
                     || guard.getQuantifier() != emptyMatch.getQuantifier()
               );
            this.popQuantifierGuard(finalGuard);
         } else {
            this.quantifierGuardsResult = this.quantifierGuards == null ? QuantifierGuard.NO_GUARDS : this.quantifierGuards.toArray();
         }
      }
   }

   @CompilerDirectives.TruffleBoundary
   protected PositionAssertion getLastDollarOnPath() {
      assert this.dollarsOnPath();

      for (int i = this.curPath.length() - 1; i >= 0; i--) {
         long element = this.curPath.get(i);
         if (this.pathGetNode(element).isDollar()) {
            return (PositionAssertion)this.pathGetNode(element);
         }
      }

      throw CompilerDirectives.shouldNotReachHere();
   }

   protected GroupBoundaries getGroupBoundaries() {
      return this.ast.createGroupBoundaries(this.captureGroupUpdates, this.captureGroupClears, this.lastGroup);
   }

   private boolean doAdvance() {
      int extraEmptyLoopIterations = this.ast.getOptions().getFlavor().canHaveEmptyLoopIterations() ? 1 : 0;
      if (this.cur.isDead() || this.insideLoops.get(this.cur, 0) > extraEmptyLoopIterations) {
         return this.retreat();
      } else if (this.cur.isSequence()) {
         Sequence sequence = (Sequence)this.cur;
         if (!sequence.isEmpty()) {
            this.cur = this.forward ? sequence.getFirstTerm() : sequence.getLastTerm();
            return false;
         } else {
            Group parent = sequence.getParent();
            if (sequence.isExpandedQuantifier()) {
               assert this.pathGetNode(this.curPath.peek()) == parent && pathIsGroupEnter(this.curPath.peek());

               if (parent.hasNotUnrolledQuantifier() && parent.getQuantifier().getMin() > 0 && !this.isGroupExitOnPath(parent)) {
                  return this.retreat();
               }

               this.switchEnterToPassThrough(parent);
               this.unregisterInsideLoop(parent);
            } else {
               this.pushGroupExit(parent);
            }

            return this.advanceTerm(parent);
         }
      } else if (this.cur.isGroup()) {
         Group group = (Group)this.cur;
         this.pushGroupEnter(group, 1);
         if (group.hasEmptyGuard()) {
            this.insideEmptyGuardGroup.add(group);
         }

         if (group.isLoop()) {
            this.registerInsideLoop(group);
         }

         this.cur = group.getFirstAlternative();
         return this.deduplicatePath();
      } else {
         this.curPath.add(createPathElement(this.cur));
         if (this.cur.isPositionAssertion()) {
            PositionAssertion assertion = (PositionAssertion)this.cur;
            switch (assertion.type) {
               case CARET:
                  this.addToVisitedSet(this.caretsOnPath);
                  if (this.canTraverseCaret) {
                     return this.advanceTerm(assertion);
                  }

                  return this.retreat();
               case DOLLAR:
                  this.addToVisitedSet(this.dollarsOnPath);
                  return this.advanceTerm((Term)this.cur);
               default:
                  throw CompilerDirectives.shouldNotReachHere();
            }
         } else if (this.canTraverseLookArounds() && this.cur.isLookAheadAssertion()) {
            this.enterLookAhead((LookAheadAssertion)this.cur);
            this.addToVisitedSet(this.lookAroundsOnPath);
            return this.advanceTerm((Term)this.cur);
         } else if (this.canTraverseLookArounds() && this.cur.isLookBehindAssertion()) {
            this.addToVisitedSet(this.lookAroundsOnPath);
            return this.traversableLookBehindAssertions != null && !this.traversableLookBehindAssertions.contains(this.cur)
               ? this.retreat()
               : this.advanceTerm((LookBehindAssertion)this.cur);
         } else {
            assert this.cur.isCharacterClass()
               || this.cur.isBackReference()
               || this.cur.isMatchFound()
               || this.cur.isAtomicGroup()
               || !this.canTraverseLookArounds() && this.cur.isLookAroundAssertion();

            return this.forward && this.dollarsOnPath() && this.cur.isCharacterClass() ? this.retreat() : true;
         }
      }
   }

   private boolean advanceTerm(Term term) {
      if (this.ast.isNFAInitialState(term) || term.getParent().isSubtreeRoot() && (term.isPositionAssertion() || term.isMatchFound())) {
         assert term.isPositionAssertion() || term.isMatchFound();

         if (term.isPositionAssertion()) {
            this.cur = term.asPositionAssertion().getNext();
         } else {
            this.cur = term.asMatchFound().getNext();
         }

         return false;
      } else {
         Term curTerm = term;

         while (!curTerm.getParent().isSubtreeRoot()) {
            if (this.insideEmptyGuardGroup.contains(curTerm)) {
               return this.advanceEmptyGuard(curTerm);
            }

            Sequence parentSeq = (Sequence)curTerm.getParent();
            if (curTerm != (this.forward ? parentSeq.getLastTerm() : parentSeq.getFirstTerm())) {
               this.cur = parentSeq.getTerms().get(curTerm.getSeqIndex() + (this.forward ? 1 : -1));
               return false;
            }

            Group parentGroup = parentSeq.getParent();
            this.pushGroupExit(parentGroup);
            if (parentGroup.isLoop()) {
               this.cur = parentGroup;
               return false;
            }

            curTerm = parentGroup;
         }

         assert curTerm.isGroup();

         assert curTerm.getParent().isSubtreeRoot();

         if (this.insideEmptyGuardGroup.contains(curTerm)) {
            return this.advanceEmptyGuard(curTerm);
         } else {
            this.cur = curTerm.getSubTreeParent().getMatchFound();
            return false;
         }
      }
   }

   private boolean advanceEmptyGuard(Term curTerm) {
      Group parent = curTerm.getParent().getParent().asGroup();
      if (!parent.hasNotUnrolledQuantifier() || parent.getQuantifier().getMin() <= 0) {
         return this.retreat();
      } else {
         assert curTerm.isGroup();

         this.cur = curTerm;
         return true;
      }
   }

   private boolean retreat() {
      while (!this.curPath.isEmpty()) {
         long lastVisited = this.curPath.peek();
         RegexASTNode node = this.pathGetNode(lastVisited);
         if (!pathIsGroup(lastVisited)) {
            this.curPath.pop();
            if (this.canTraverseLookArounds() && node.isLookAroundAssertion()) {
               if (node.isLookAheadAssertion()) {
                  this.leaveLookAhead(node.asLookAheadAssertion());
               }

               this.removeFromVisitedSet(lastVisited, this.lookAroundsOnPath);
            } else if (node.isDollar()) {
               this.removeFromVisitedSet(lastVisited, this.dollarsOnPath);
            } else if (node.isCaret()) {
               this.removeFromVisitedSet(lastVisited, this.caretsOnPath);
            }
         } else {
            Group group = (Group)node;
            if (!pathIsGroupEnter(lastVisited) && !pathIsGroupPassThrough(lastVisited)) {
               if (this.ast.getOptions().getFlavor().failingEmptyChecksDontBacktrack()
                  && pathIsGroupExit(lastVisited)
                  && group.hasQuantifier()
                  && group.getQuantifier().hasZeroWidthIndex()
                  && (group.getFirstAlternative().isExpandedQuantifier() || group.getLastAlternative().isExpandedQuantifier())) {
                  this.switchExitToEscape(group);
                  return this.advanceTerm(group);
               }

               if (pathIsGroupExit(lastVisited)) {
                  this.popGroupExit(group);
               } else {
                  assert pathIsGroupEscape(lastVisited);

                  this.popGroupEscape(group);
               }
            } else {
               if (this.pathGroupHasNext(lastVisited)) {
                  if (pathIsGroupPassThrough(lastVisited)) {
                     this.registerInsideLoop(group);
                  }

                  this.switchNextGroupAlternative(group);
                  this.cur = this.pathGroupGetNext(lastVisited);
                  return this.deduplicatePath();
               }

               if (pathIsGroupEnter(lastVisited)) {
                  this.popGroupEnter(group);
               } else {
                  assert pathIsGroupPassThrough(lastVisited);

                  this.popGroupPassThrough(group);
               }

               assert this.noEmptyGuardGroupEnterOnPath(group);

               if (pathIsGroupEnter(lastVisited)) {
                  this.unregisterInsideLoop(group);
               }

               this.insideEmptyGuardGroup.remove(group);
            }
         }
      }

      this.done = true;
      return false;
   }

   private boolean deduplicatePath() {
      boolean captureGroupsMatter = this.ast.getOptions().getFlavor().backreferencesToUnmatchedGroupsFail();
      NFATraversalRegexASTVisitor.DeduplicationKey key;
      if (captureGroupsMatter) {
         key = NFATraversalRegexASTVisitor.CGSensitiveDeduplicationKey.create(
            this.cur, this.lookAroundsOnPath, this.dollarsOnPath, this.quantifierGuards, this.captureGroupUpdates, this.captureGroupClears, this.lastGroup
         );
      } else {
         key = NFATraversalRegexASTVisitor.DeduplicationKey.create(this.cur, this.lookAroundsOnPath, this.dollarsOnPath, this.quantifierGuards);
      }

      boolean isDuplicate = !this.pathDeduplicationSet.add(key);
      return isDuplicate ? this.retreat() : false;
   }

   private static long createPathElement(RegexASTNode node) {
      return (long)node.getId() << 16;
   }

   private static int pathGetNodeId(long pathElement) {
      return (int)(pathElement >>> 16);
   }

   private RegexASTNode pathGetNode(long pathElement) {
      return this.ast.getState(pathGetNodeId(pathElement));
   }

   private static int pathGetGroupAltIndex(long pathElement) {
      return (short)(pathElement >>> 0);
   }

   private static boolean pathIsGroup(long pathElement) {
      return (pathElement & 4222124650659840L) != 0L;
   }

   private static boolean pathIsGroupEnter(long pathElement) {
      return (pathElement & 281474976710656L) != 0L;
   }

   private static boolean pathIsGroupExit(long pathElement) {
      return (pathElement & 562949953421312L) != 0L;
   }

   private static boolean pathIsGroupPassThrough(long pathElement) {
      return (pathElement & 1125899906842624L) != 0L;
   }

   private static boolean pathIsGroupEscape(long pathElement) {
      return (pathElement & 2251799813685248L) != 0L;
   }

   private boolean pathGroupHasNext(long pathElement) {
      return pathGetGroupAltIndex(pathElement) < ((Group)this.pathGetNode(pathElement)).size();
   }

   private Sequence pathGroupGetNext(long pathElement) {
      return ((Group)this.pathGetNode(pathElement)).getAlternatives().get(pathGetGroupAltIndex(pathElement));
   }

   private boolean isGroupExitOnPath(Group group) {
      assert !this.curPath.isEmpty() && pathIsGroupEnter(this.curPath.peek()) && this.pathGetNode(this.curPath.peek()) == group;

      return this.curPath.length() >= 2
         && pathIsGroupExit(this.curPath.get(this.curPath.length() - 2))
         && this.pathGetNode(this.curPath.get(this.curPath.length() - 2)) == group;
   }

   private boolean noEmptyGuardGroupEnterOnPath(Group group) {
      if (!group.hasEmptyGuard()) {
         return true;
      } else {
         for (int i = 0; i < this.curPath.length(); i++) {
            if (this.pathGetNode(this.curPath.get(i)) == group && pathIsGroupEnter(this.curPath.get(i))) {
               return false;
            }
         }

         return true;
      }
   }

   private void pushGroupEnter(Group group, int groupAltIndex) {
      this.curPath.add(createPathElement(group) | groupAltIndex << 0 | 281474976710656L);
      if (group.isCapturing()) {
         this.captureGroupUpdate(group.getBoundaryIndexStart());
      }

      if (!this.ast.getOptions().getFlavor().nestedCaptureGroupsKeptOnLoopReentry() && group.hasQuantifier() && group.hasEnclosedCaptureGroups()) {
         int lo = Group.groupNumberToBoundaryIndexStart(group.getEnclosedCaptureGroupsLow());
         int hi = Group.groupNumberToBoundaryIndexEnd(group.getEnclosedCaptureGroupsHigh() - 1);
         this.captureGroupClear(lo, hi);
      }

      if (this.useQuantifierGuards()) {
         if (group.hasQuantifier()) {
            Token.Quantifier quantifier = group.getQuantifier();
            if (quantifier.hasIndex()) {
               if (this.quantifierGuardsLoop[quantifier.getIndex()] > 0 && this.quantifierGuardsExited[quantifier.getIndex()] == 0) {
                  this.pushQuantifierGuard(quantifier.isInfiniteLoop() ? QuantifierGuard.createLoopInc(quantifier) : QuantifierGuard.createLoop(quantifier));
               } else {
                  this.pushQuantifierGuard(QuantifierGuard.createEnter(quantifier));
               }
            }

            if (quantifier.hasZeroWidthIndex() && (group.getFirstAlternative().isExpandedQuantifier() || group.getLastAlternative().isExpandedQuantifier())) {
               this.pushQuantifierGuard(QuantifierGuard.createEnterZeroWidth(quantifier));
            }
         }

         if (this.ast.getOptions().getFlavor().emptyChecksMonitorCaptureGroups() && group.isCapturing()) {
            this.pushQuantifierGuard(QuantifierGuard.createUpdateCG(group.getBoundaryIndexStart()));
         }
      }
   }

   private int popGroupEnter(Group group) {
      assert pathIsGroupEnter(this.curPath.peek());

      if (this.useQuantifierGuards()) {
         if (this.ast.getOptions().getFlavor().emptyChecksMonitorCaptureGroups() && group.isCapturing()) {
            this.popQuantifierGuard(QuantifierGuard.createUpdateCG(group.getBoundaryIndexStart()));
         }

         if (group.hasQuantifier()) {
            Token.Quantifier quantifier = group.getQuantifier();
            if (quantifier.hasZeroWidthIndex() && (group.getFirstAlternative().isExpandedQuantifier() || group.getLastAlternative().isExpandedQuantifier())) {
               this.popQuantifierGuard(QuantifierGuard.createEnterZeroWidth(quantifier));
            }

            if (quantifier.hasIndex()) {
               if (this.quantifierGuardsLoop[quantifier.getIndex()] > 0 && this.quantifierGuardsExited[quantifier.getIndex()] == 0) {
                  this.popQuantifierGuard(quantifier.isInfiniteLoop() ? QuantifierGuard.createLoopInc(quantifier) : QuantifierGuard.createLoop(quantifier));
               } else {
                  this.popQuantifierGuard(QuantifierGuard.createEnter(quantifier));
               }
            }
         }
      }

      if (!this.ast.getOptions().getFlavor().nestedCaptureGroupsKeptOnLoopReentry() && group.hasQuantifier() && group.hasEnclosedCaptureGroups()) {
         this.popCaptureGroupEvent();
      }

      if (group.isCapturing()) {
         this.popCaptureGroupEvent();
      }

      return pathGetGroupAltIndex(this.curPath.pop());
   }

   private void switchNextGroupAlternative(Group group) {
      int groupAltIndex;
      if (pathIsGroupEnter(this.curPath.peek())) {
         groupAltIndex = this.popGroupEnter(group);
      } else {
         assert pathIsGroupPassThrough(this.curPath.peek());

         groupAltIndex = this.popGroupPassThrough(group);
      }

      this.pushGroupEnter(group, groupAltIndex + 1);
   }

   private void pushGroupExit(Group group) {
      this.curPath.add(createPathElement(group) | 562949953421312L);
      if (group.isCapturing()) {
         this.captureGroupUpdate(group.getBoundaryIndexEnd());
         if (this.ast.getOptions().getFlavor().usesLastGroupResultField() && group.getGroupNumber() != 0) {
            this.lastGroupUpdate(group.getGroupNumber());
         }
      }

      if (this.useQuantifierGuards()) {
         if (group.hasQuantifier()) {
            Token.Quantifier quantifier = group.getQuantifier();
            if (quantifier.hasIndex()) {
               this.quantifierGuardsLoop[quantifier.getIndex()]++;
            }

            if (quantifier.hasZeroWidthIndex()
               && (group.getFirstAlternative().isExpandedQuantifier() || group.getLastAlternative().isExpandedQuantifier())
               && (this.ast.getOptions().getFlavor().canHaveEmptyLoopIterations() || !this.root.isCharacterClass())) {
               this.pushQuantifierGuard(QuantifierGuard.createExitZeroWidth(quantifier));
            }
         }

         if (this.ast.getOptions().getFlavor().emptyChecksMonitorCaptureGroups() && group.isCapturing()) {
            this.pushQuantifierGuard(QuantifierGuard.createUpdateCG(group.getBoundaryIndexEnd()));
         }
      }
   }

   private void popGroupExit(Group group) {
      assert pathIsGroupExit(this.curPath.peek());

      if (this.useQuantifierGuards()) {
         if (this.ast.getOptions().getFlavor().emptyChecksMonitorCaptureGroups() && group.isCapturing()) {
            this.popQuantifierGuard(QuantifierGuard.createUpdateCG(group.getBoundaryIndexEnd()));
         }

         if (group.hasQuantifier()) {
            Token.Quantifier quantifier = group.getQuantifier();
            if (quantifier.hasZeroWidthIndex()
               && (group.getFirstAlternative().isExpandedQuantifier() || group.getLastAlternative().isExpandedQuantifier())
               && (this.ast.getOptions().getFlavor().canHaveEmptyLoopIterations() || !this.root.isCharacterClass())) {
               this.popQuantifierGuard(QuantifierGuard.createExitZeroWidth(quantifier));
            }

            if (quantifier.hasIndex()) {
               this.quantifierGuardsLoop[quantifier.getIndex()]--;
            }
         }
      }

      if (group.isCapturing()) {
         if (this.ast.getOptions().getFlavor().usesLastGroupResultField() && group.getGroupNumber() != 0) {
            this.popCaptureGroupEvent();
         }

         this.popCaptureGroupEvent();
      }

      this.curPath.pop();
   }

   private void pushGroupPassThrough(Group group, int groupAltIndex) {
      this.curPath.add(createPathElement(group) | 1125899906842624L | groupAltIndex << 0);
      if (this.useQuantifierGuards()) {
         if (group.hasQuantifier()) {
            Token.Quantifier quantifier = group.getQuantifier();
            if (quantifier.hasIndex()) {
               if (quantifier.getMin() > 0) {
                  this.quantifierGuardsExited[quantifier.getIndex()]++;
                  this.pushQuantifierGuard(QuantifierGuard.createExit(quantifier));
               } else {
                  this.pushQuantifierGuard(QuantifierGuard.createClear(quantifier));
               }
            }
         }

         if (this.ast.getOptions().getFlavor().emptyChecksMonitorCaptureGroups() && group.isCapturing()) {
            this.pushQuantifierGuard(QuantifierGuard.createUpdateCG(group.getBoundaryIndexStart()));
            this.pushQuantifierGuard(QuantifierGuard.createUpdateCG(group.getBoundaryIndexEnd()));
         }
      }
   }

   private int popGroupPassThrough(Group group) {
      assert pathIsGroupPassThrough(this.curPath.peek());

      if (this.useQuantifierGuards()) {
         if (this.ast.getOptions().getFlavor().emptyChecksMonitorCaptureGroups() && group.isCapturing()) {
            this.popQuantifierGuard(QuantifierGuard.createUpdateCG(group.getBoundaryIndexEnd()));
            this.popQuantifierGuard(QuantifierGuard.createUpdateCG(group.getBoundaryIndexStart()));
         }

         if (group.hasQuantifier()) {
            Token.Quantifier quantifier = group.getQuantifier();
            if (quantifier.hasIndex()) {
               if (quantifier.getMin() > 0) {
                  this.popQuantifierGuard(QuantifierGuard.createExit(quantifier));
                  this.quantifierGuardsExited[quantifier.getIndex()]--;
               } else {
                  this.popQuantifierGuard(QuantifierGuard.createClear(quantifier));
               }
            }
         }
      }

      return pathGetGroupAltIndex(this.curPath.pop());
   }

   private void switchEnterToPassThrough(Group group) {
      int groupAltIndex = this.popGroupEnter(group);
      this.pushGroupPassThrough(group, groupAltIndex);
   }

   private void switchExitToEscape(Group group) {
      this.popGroupExit(group);
      this.pushGroupEscape(group);
   }

   private void pushGroupEscape(Group group) {
      this.curPath.add(createPathElement(group) | 2251799813685248L);
      if (this.useQuantifierGuards()) {
         if (group.hasQuantifier()) {
            Token.Quantifier quantifier = group.getQuantifier();
            if (quantifier.hasIndex()) {
               this.quantifierGuardsExited[quantifier.getIndex()]++;
            }

            if (quantifier.hasZeroWidthIndex() && (group.getFirstAlternative().isExpandedQuantifier() || group.getLastAlternative().isExpandedQuantifier())) {
               this.pushQuantifierGuard(QuantifierGuard.createEscapeZeroWidth(quantifier));
            }
         }

         if (this.ast.getOptions().getFlavor().emptyChecksMonitorCaptureGroups() && group.isCapturing()) {
            this.pushQuantifierGuard(QuantifierGuard.createUpdateCG(group.getBoundaryIndexEnd()));
         }
      }
   }

   private void popGroupEscape(Group group) {
      assert pathIsGroupEscape(this.curPath.peek());

      if (this.useQuantifierGuards()) {
         if (this.ast.getOptions().getFlavor().emptyChecksMonitorCaptureGroups() && group.isCapturing()) {
            this.popQuantifierGuard(QuantifierGuard.createUpdateCG(group.getBoundaryIndexEnd()));
         }

         if (group.hasQuantifier()) {
            Token.Quantifier quantifier = group.getQuantifier();
            if (quantifier.hasZeroWidthIndex() && (group.getFirstAlternative().isExpandedQuantifier() || group.getLastAlternative().isExpandedQuantifier())) {
               this.popQuantifierGuard(QuantifierGuard.createEscapeZeroWidth(quantifier));
            }

            if (quantifier.hasIndex()) {
               this.quantifierGuardsExited[quantifier.getIndex()]--;
            }
         }
      }

      this.curPath.pop();
   }

   private void clearCaptureGroupData() {
      this.captureGroupEvents.clear();
      this.captureGroupUpdates.clear();
      this.captureGroupClears.clear();
      this.lastGroup = -1;
   }

   private void captureGroupUpdate(int boundary) {
      this.captureGroupEvents
         .add(
            new NFATraversalRegexASTVisitor.CaptureGroupEvent.CaptureGroupUpdate(
               boundary, this.captureGroupUpdates.get(boundary), this.captureGroupClears.get(boundary)
            )
         );
      this.captureGroupUpdates.set(boundary);
      this.captureGroupClears.clear(boundary);
   }

   private void captureGroupClear(int low, int high) {
      this.captureGroupEvents
         .add(new NFATraversalRegexASTVisitor.CaptureGroupEvent.CaptureGroupClears(this.captureGroupUpdates.copy(), this.captureGroupClears.copy()));
      this.captureGroupClears.setRange(low, high);
      this.captureGroupUpdates.clearRange(low, high);
   }

   private void lastGroupUpdate(int newLastGroup) {
      this.captureGroupEvents.add(new NFATraversalRegexASTVisitor.CaptureGroupEvent.LastGroupUpdate(this.lastGroup));
      this.lastGroup = newLastGroup;
   }

   private void popCaptureGroupEvent() {
      assert !this.captureGroupEvents.isEmpty();

      NFATraversalRegexASTVisitor.CaptureGroupEvent poppedEvent = this.captureGroupEvents.remove(this.captureGroupEvents.size() - 1);
      poppedEvent.undo(this);
   }

   private boolean useQuantifierGuards() {
      return !this.canTraverseLookArounds();
   }

   private void clearQuantifierGuards() {
      this.quantifierGuards = null;
   }

   private void pushQuantifierGuard(QuantifierGuard guard) {
      assert this.useQuantifierGuards();

      this.quantifierGuards = new NFATraversalRegexASTVisitor.QuantifierGuardsLinkedList(guard, this.quantifierGuards);
   }

   private void popQuantifierGuard(QuantifierGuard expectedGuard) {
      assert this.useQuantifierGuards();

      assert this.quantifierGuards != null;

      QuantifierGuard droppedGuard = this.quantifierGuards.getGuard();
      this.quantifierGuards = this.quantifierGuards.getPrev();

      assert droppedGuard.equals(expectedGuard);
   }

   private void addToVisitedSet(StateSet<RegexAST, RegexASTNode> visitedSet) {
      this.nodeVisitCount[this.cur.getId()]++;
      visitedSet.add(this.cur);
   }

   private void removeFromVisitedSet(long pathElement, StateSet<RegexAST, RegexASTNode> visitedSet) {
      if (--this.nodeVisitCount[pathGetNodeId(pathElement)] == 0) {
         visitedSet.remove(this.pathGetNode(pathElement));
      }
   }

   private boolean nodeVisitsEmpty() {
      for (int i : this.nodeVisitCount) {
         if (i != 0) {
            return false;
         }
      }

      return true;
   }

   private void registerInsideLoop(Group group) {
      this.insideLoops.put(group, this.insideLoops.get(group, 0) + 1);
   }

   private void unregisterInsideLoop(Group group) {
      int depth = this.insideLoops.get(group, 0);
      if (depth == 1) {
         this.insideLoops.removeKey(group);
      } else if (depth > 1) {
         this.insideLoops.put(group, depth - 1);
      }
   }

   private void dumpPath() {
      System.out.println("NEW PATH");

      for (int i = 0; i < this.curPath.length(); i++) {
         long element = this.curPath.get(i);
         if (pathIsGroup(element)) {
            Group group = (Group)this.pathGetNode(element);
            if (pathIsGroupEnter(element)) {
               System.out.println(String.format("ENTER (%d)   %s", pathGetGroupAltIndex(element), group));
            } else if (pathIsGroupExit(element)) {
               System.out.println(String.format("EXIT        %s", group));
            } else if (pathIsGroupPassThrough(element)) {
               System.out.println(String.format("PASSTHROUGH %s", group));
            } else {
               System.out.println(String.format("ESCAPE      %s", group));
            }
         } else {
            System.out.println(String.format("NODE        %s", this.pathGetNode(element)));
         }
      }
   }

   private static final class CGSensitiveDeduplicationKey extends NFATraversalRegexASTVisitor.DeduplicationKey {
      private final TBitSet captureGroupUpdates;
      private final TBitSet captureGroupClears;
      private final int lastGroup;

      protected CGSensitiveDeduplicationKey(
         RegexASTNode targetNode,
         StateSet<RegexAST, RegexASTNode> lookAroundsOnPath,
         StateSet<RegexAST, RegexASTNode> dollarsOnPath,
         NFATraversalRegexASTVisitor.QuantifierGuardsLinkedList quantifierGuards,
         TBitSet captureGroupUpdates,
         TBitSet captureGroupClears,
         int lastGroup
      ) {
         super(targetNode, lookAroundsOnPath, dollarsOnPath, quantifierGuards);
         this.captureGroupUpdates = captureGroupUpdates.copy();
         this.captureGroupClears = captureGroupClears.copy();
         this.lastGroup = lastGroup;
      }

      public static NFATraversalRegexASTVisitor.CGSensitiveDeduplicationKey create(
         RegexASTNode targetNode,
         StateSet<RegexAST, RegexASTNode> lookAroundsOnPath,
         StateSet<RegexAST, RegexASTNode> dollarsOnPath,
         NFATraversalRegexASTVisitor.QuantifierGuardsLinkedList quantifierGuards,
         TBitSet captureGroupUpdates,
         TBitSet captureGroupClears,
         int lastGroup
      ) {
         NFATraversalRegexASTVisitor.CGSensitiveDeduplicationKey key = new NFATraversalRegexASTVisitor.CGSensitiveDeduplicationKey(
            targetNode, lookAroundsOnPath, dollarsOnPath, quantifierGuards, captureGroupUpdates, captureGroupClears, lastGroup
         );
         key.hashCode = key.calculateHashCode();
         return key;
      }

      @Override
      public boolean equals(Object obj) {
         if (obj != null && this.getClass() == obj.getClass()) {
            NFATraversalRegexASTVisitor.CGSensitiveDeduplicationKey other = (NFATraversalRegexASTVisitor.CGSensitiveDeduplicationKey)obj;
            return this.nodesInvolved.equals(other.nodesInvolved)
               && Objects.equals(this.quantifierGuards, other.quantifierGuards)
               && Objects.equals(this.captureGroupUpdates, other.captureGroupUpdates)
               && Objects.equals(this.captureGroupClears, other.captureGroupClears)
               && this.lastGroup == other.lastGroup;
         } else {
            return false;
         }
      }

      @Override
      protected int calculateHashCode() {
         return Objects.hash(super.calculateHashCode(), this.captureGroupUpdates, this.captureGroupClears, this.lastGroup);
      }

      @Override
      public int hashCode() {
         return this.hashCode;
      }
   }

   private abstract static class CaptureGroupEvent {
      public abstract void undo(NFATraversalRegexASTVisitor visitor);

      private static final class CaptureGroupClears extends NFATraversalRegexASTVisitor.CaptureGroupEvent {
         private final TBitSet prevUpdates;
         private final TBitSet prevClears;

         CaptureGroupClears(TBitSet prevUpdates, TBitSet prevClears) {
            this.prevUpdates = prevUpdates;
            this.prevClears = prevClears;
         }

         @Override
         public void undo(NFATraversalRegexASTVisitor visitor) {
            visitor.captureGroupUpdates = this.prevUpdates;
            visitor.captureGroupClears = this.prevClears;
         }
      }

      private static final class CaptureGroupUpdate extends NFATraversalRegexASTVisitor.CaptureGroupEvent {
         private final int boundary;
         private final boolean prevUpdate;
         private final boolean prevClear;

         CaptureGroupUpdate(int boundary, boolean prevUpdate, boolean prevClear) {
            this.boundary = boundary;
            this.prevUpdate = prevUpdate;
            this.prevClear = prevClear;
         }

         @Override
         public void undo(NFATraversalRegexASTVisitor visitor) {
            if (this.prevUpdate) {
               visitor.captureGroupUpdates.set(this.boundary);
            } else {
               visitor.captureGroupUpdates.clear(this.boundary);
            }

            if (this.prevClear) {
               visitor.captureGroupClears.set(this.boundary);
            } else {
               visitor.captureGroupClears.clear(this.boundary);
            }
         }
      }

      private static final class LastGroupUpdate extends NFATraversalRegexASTVisitor.CaptureGroupEvent {
         private final int prevLastGroup;

         LastGroupUpdate(int prevLastGroup) {
            this.prevLastGroup = prevLastGroup;
         }

         @Override
         public void undo(NFATraversalRegexASTVisitor visitor) {
            visitor.lastGroup = this.prevLastGroup;
         }
      }
   }

   private static class DeduplicationKey {
      protected final StateSet<RegexAST, RegexASTNode> nodesInvolved;
      protected final NFATraversalRegexASTVisitor.QuantifierGuardsLinkedList quantifierGuards;
      protected int hashCode;

      protected DeduplicationKey(
         RegexASTNode targetNode,
         StateSet<RegexAST, RegexASTNode> lookAroundsOnPath,
         StateSet<RegexAST, RegexASTNode> dollarsOnPath,
         NFATraversalRegexASTVisitor.QuantifierGuardsLinkedList quantifierGuards
      ) {
         this.nodesInvolved = lookAroundsOnPath.copy();
         this.nodesInvolved.addAll(dollarsOnPath);
         this.nodesInvolved.add(targetNode);
         this.quantifierGuards = quantifierGuards;
      }

      public static NFATraversalRegexASTVisitor.DeduplicationKey create(
         RegexASTNode targetNode,
         StateSet<RegexAST, RegexASTNode> lookAroundsOnPath,
         StateSet<RegexAST, RegexASTNode> dollarsOnPath,
         NFATraversalRegexASTVisitor.QuantifierGuardsLinkedList quantifierGuards
      ) {
         NFATraversalRegexASTVisitor.DeduplicationKey key = new NFATraversalRegexASTVisitor.DeduplicationKey(
            targetNode, lookAroundsOnPath, dollarsOnPath, quantifierGuards
         );
         key.hashCode = key.calculateHashCode();
         return key;
      }

      @Override
      public boolean equals(Object obj) {
         if (obj != null && this.getClass() == obj.getClass()) {
            NFATraversalRegexASTVisitor.DeduplicationKey other = (NFATraversalRegexASTVisitor.DeduplicationKey)obj;
            return this.nodesInvolved.equals(other.nodesInvolved) && Objects.equals(this.quantifierGuards, other.quantifierGuards);
         } else {
            return false;
         }
      }

      protected int calculateHashCode() {
         return Objects.hash(this.nodesInvolved, this.quantifierGuards);
      }

      @Override
      public int hashCode() {
         return this.hashCode;
      }
   }

   private static final class QuantifierGuardsLinkedList {
      private final QuantifierGuard guard;
      private final NFATraversalRegexASTVisitor.QuantifierGuardsLinkedList prev;
      private final int length;
      private final int hashCode;

      QuantifierGuardsLinkedList(QuantifierGuard guard, NFATraversalRegexASTVisitor.QuantifierGuardsLinkedList prev) {
         this.guard = guard;
         this.prev = prev;
         this.length = prev == null ? 1 : prev.length + 1;
         this.hashCode = guard.hashCode() + 31 * (prev == null ? 0 : prev.hashCode);
      }

      public NFATraversalRegexASTVisitor.QuantifierGuardsLinkedList getPrev() {
         return this.prev;
      }

      public QuantifierGuard getGuard() {
         return this.guard;
      }

      public int getLength() {
         return this.length;
      }

      @Override
      public boolean equals(Object obj) {
         if (!(obj instanceof NFATraversalRegexASTVisitor.QuantifierGuardsLinkedList)) {
            return false;
         } else {
            NFATraversalRegexASTVisitor.QuantifierGuardsLinkedList other = (NFATraversalRegexASTVisitor.QuantifierGuardsLinkedList)obj;
            return this.hashCode == other.hashCode
               && this.length == other.length
               && this.guard.equals(other.guard)
               && (this.prev == null || this.prev.equals(other.prev));
         }
      }

      @Override
      public int hashCode() {
         return this.hashCode;
      }

      public QuantifierGuard[] toArray() {
         QuantifierGuard[] result = new QuantifierGuard[this.getLength()];
         NFATraversalRegexASTVisitor.QuantifierGuardsLinkedList cur = this;

         for (int i = result.length - 1; i >= 0; i--) {
            result[i] = cur.getGuard();
            cur = cur.getPrev();
         }

         return result;
      }

      public QuantifierGuard[] toArray(Predicate<QuantifierGuard> filter) {
         int resultSize = 0;

         for (NFATraversalRegexASTVisitor.QuantifierGuardsLinkedList cur = this; cur != null; cur = cur.getPrev()) {
            if (filter.test(cur.getGuard())) {
               resultSize++;
            }
         }

         QuantifierGuard[] result = new QuantifierGuard[resultSize];

         for (NFATraversalRegexASTVisitor.QuantifierGuardsLinkedList var5 = this; var5 != null; var5 = var5.getPrev()) {
            if (filter.test(var5.getGuard())) {
               result[--resultSize] = var5.getGuard();
            }
         }

         return result;
      }
   }
}
