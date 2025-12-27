package com.oracle.truffle.regex.tregex.parser;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.regex.RegexFlags;
import com.oracle.truffle.regex.RegexLanguage;
import com.oracle.truffle.regex.RegexOptions;
import com.oracle.truffle.regex.RegexSource;
import com.oracle.truffle.regex.charset.CodePointSet;
import com.oracle.truffle.regex.charset.CodePointSetAccumulator;
import com.oracle.truffle.regex.charset.Constants;
import com.oracle.truffle.regex.tregex.buffer.CompilationBuffer;
import com.oracle.truffle.regex.tregex.buffer.IntRangesBuffer;
import com.oracle.truffle.regex.tregex.parser.ast.AtomicGroup;
import com.oracle.truffle.regex.tregex.parser.ast.BackReference;
import com.oracle.truffle.regex.tregex.parser.ast.CharacterClass;
import com.oracle.truffle.regex.tregex.parser.ast.Group;
import com.oracle.truffle.regex.tregex.parser.ast.LookAheadAssertion;
import com.oracle.truffle.regex.tregex.parser.ast.LookBehindAssertion;
import com.oracle.truffle.regex.tregex.parser.ast.PositionAssertion;
import com.oracle.truffle.regex.tregex.parser.ast.QuantifiableTerm;
import com.oracle.truffle.regex.tregex.parser.ast.RegexAST;
import com.oracle.truffle.regex.tregex.parser.ast.RegexASTNode;
import com.oracle.truffle.regex.tregex.parser.ast.RegexASTRootNode;
import com.oracle.truffle.regex.tregex.parser.ast.RegexASTSubtreeRootNode;
import com.oracle.truffle.regex.tregex.parser.ast.Sequence;
import com.oracle.truffle.regex.tregex.parser.ast.SubexpressionCall;
import com.oracle.truffle.regex.tregex.parser.ast.Term;
import com.oracle.truffle.regex.tregex.parser.ast.visitors.DepthFirstTraversalRegexASTVisitor;
import com.oracle.truffle.regex.tregex.parser.ast.visitors.NodeCountVisitor;
import com.oracle.truffle.regex.tregex.parser.ast.visitors.SetSourceSectionVisitor;
import com.oracle.truffle.regex.tregex.string.Encodings;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public final class RegexASTBuilder {
   private final RegexParserGlobals globals;
   private final RegexFlags flags;
   private final RegexOptions options;
   private final Encodings.Encoding encoding;
   private final RegexAST ast;
   private final RegexProperties properties;
   private final Counter.ThresholdCounter groupCount;
   private final NodeCountVisitor countVisitor;
   private final SetSourceSectionVisitor setSourceSectionVisitor;
   private final CompilationBuffer compilationBuffer;
   private Group curGroup;
   private Sequence curSequence;
   private Term curTerm;

   @CompilerDirectives.TruffleBoundary
   public RegexASTBuilder(RegexLanguage language, RegexSource source, RegexFlags flags, CompilationBuffer compilationBuffer) {
      this.globals = language.parserGlobals;
      this.flags = flags;
      this.options = source.getOptions();
      this.encoding = source.getEncoding();
      this.ast = new RegexAST(language, source, flags);
      this.properties = this.ast.getProperties();
      this.groupCount = this.ast.getGroupCount();
      this.countVisitor = new NodeCountVisitor();
      this.setSourceSectionVisitor = this.options.isDumpAutomataWithSourceSections() ? new SetSourceSectionVisitor(this.ast) : null;
      this.compilationBuffer = compilationBuffer;
   }

   public Group getCurGroup() {
      return this.curGroup;
   }

   public Sequence getCurSequence() {
      return this.curSequence;
   }

   public Term getCurTerm() {
      return this.curTerm;
   }

   public boolean curGroupIsRoot() {
      return this.curGroup == this.ast.getRoot();
   }

   public void pushRootGroup() {
      this.pushRootGroup(true);
   }

   public void pushRootGroup(boolean rootCapture) {
      RegexASTRootNode rootParent = this.ast.createRootNode();
      this.ast.setRoot(this.pushGroup(null, rootCapture, rootParent));
      if (this.options.isDumpAutomataWithSourceSections()) {
         this.ast
            .addSourceSections(
               this.ast.getRoot(),
               Arrays.asList(
                  this.ast.getSource().getSource().createSection(0, 1),
                  this.ast.getSource().getSource().createSection(this.ast.getSource().getPattern().length() + 1, 1)
               )
            );
      }
   }

   public RegexAST popRootGroup() {
      this.optimizeGroup();
      this.ast.getRoot().setEnclosedCaptureGroupsHigh(this.groupCount.getCount());
      return this.ast;
   }

   public void pushGroup(Token token) {
      this.pushGroup(token, false, null);
   }

   public void pushGroup() {
      this.pushGroup(null);
   }

   public void pushCaptureGroup(Token token) {
      this.pushGroup(token, true, null);
   }

   public void pushCaptureGroup() {
      this.pushCaptureGroup(null);
   }

   public void pushLookAheadAssertion(Token token, boolean negate) {
      LookAheadAssertion lookAhead = this.ast.createLookAheadAssertion(negate);
      this.ast.addSourceSection(lookAhead, token);
      this.addTerm(lookAhead);
      this.pushGroup(token, false, lookAhead);
   }

   public void pushLookAheadAssertion(boolean negate) {
      this.pushLookAheadAssertion(null, negate);
   }

   public void pushLookBehindAssertion(Token token, boolean negate) {
      LookBehindAssertion lookBehind = this.ast.createLookBehindAssertion(negate);
      this.ast.addSourceSection(lookBehind, token);
      this.addTerm(lookBehind);
      this.pushGroup(token, false, lookBehind);
   }

   public void pushLookBehindAssertion(boolean negate) {
      this.pushLookBehindAssertion(null, negate);
   }

   public void pushAtomicGroup(Token token) {
      AtomicGroup atomicGroup = this.ast.createAtomicGroup();
      this.ast.addSourceSection(atomicGroup, token);
      this.addTerm(atomicGroup);
      this.pushGroup(token, false, atomicGroup);
   }

   public void pushAtomicGroup() {
      this.pushAtomicGroup(null);
   }

   private Group pushGroup(Token token, boolean capture, RegexASTSubtreeRootNode parent) {
      Group group = capture ? this.ast.createCaptureGroup(this.groupCount.inc()) : this.ast.createGroup();
      if (parent != null) {
         parent.setGroup(group);
      } else {
         this.addTerm(group);
      }

      this.ast.addSourceSection(group, token);
      this.curGroup = group;
      this.curGroup.setEnclosedCaptureGroupsLow(this.groupCount.getCount());
      this.nextSequence();
      return group;
   }

   public void popGroup(Token token) {
      if (this.tryMergeSingleCharClassAlternations()) {
         this.curGroup.removeLastSequence();
         this.ast.getNodeCount().dec();
      }

      this.optimizeGroup();
      this.curGroup.setEnclosedCaptureGroupsHigh(this.groupCount.getCount());
      this.ast.addSourceSection(this.curGroup, token);
      if (this.curGroup.getParent().isSubtreeRoot()) {
         this.ast.addSourceSection(this.curGroup.getParent(), token);
      }

      RegexASTNode parent = this.curGroup.getParent();
      if (parent.isSubtreeRoot()) {
         this.curTerm = (Term)parent;
         this.curSequence = parent.getParent().asSequence();
      } else {
         this.curTerm = this.curGroup;
         this.curSequence = (Sequence)parent;
      }

      this.curGroup = this.curSequence.getParent();
   }

   public void popGroup() {
      this.popGroup(null);
   }

   public void nextSequence() {
      if (!this.tryMergeSingleCharClassAlternations()) {
         this.curSequence = this.curGroup.addSequence(this.ast);
         this.curTerm = null;
      }
   }

   private void addTerm(Term term) {
      this.curSequence.add(term);
      this.curTerm = term;
   }

   public void addCharClass(Token.CharacterClass token) {
      CodePointSet codePointSet = this.pruneCharClass(token.getCodePointSet());
      if (this.flags.isUnicode()) {
         if (codePointSet.matchesNothing()) {
            this.addTerm(this.createCharClass(CodePointSet.getEmpty(), token));
         } else {
            this.addTerm(this.translateUnicodeCharClass(codePointSet, token));
         }
      } else {
         this.addTerm(this.createCharClass(codePointSet, token, token.wasSingleChar()));
      }
   }

   public void addCharClass(CodePointSet charSet, boolean wasSingleChar) {
      this.addCharClass(Token.createCharClass(charSet, wasSingleChar));
   }

   public void addCharClass(CodePointSet charSet) {
      this.addCharClass(charSet, charSet.matchesSingleChar());
   }

   private CodePointSet pruneCharClass(CodePointSet cps) {
      return this.encoding.getFullSet().createIntersection(cps, this.compilationBuffer);
   }

   private CharacterClass createCharClass(CodePointSet charSet, Token token) {
      return this.createCharClass(charSet, token, false);
   }

   private CharacterClass createCharClass(CodePointSet charSet, Token token, boolean wasSingleChar) {
      CharacterClass characterClass = this.ast.createCharacterClass(charSet);
      this.ast.addSourceSection(characterClass, token);
      if (wasSingleChar) {
         characterClass.setWasSingleChar();
      }

      return characterClass;
   }

   private Term translateUnicodeCharClass(CodePointSet codePointSet, Token.CharacterClass token) {
      if (this.options.isUTF16ExplodeAstralSymbols() && !Constants.BMP_WITHOUT_SURROGATES.contains(codePointSet)) {
         Group group = this.ast.createGroup();
         group.setEnclosedCaptureGroupsLow(this.groupCount.getCount());
         group.setEnclosedCaptureGroupsHigh(this.groupCount.getCount());
         IntRangesBuffer tmp = this.compilationBuffer.getIntRangesBuffer1();
         CodePointSet bmpRanges = codePointSet.createIntersection(Constants.BMP_WITHOUT_SURROGATES, tmp);
         CodePointSet astralRanges = codePointSet.createIntersection(Constants.ASTRAL_SYMBOLS, tmp);
         CodePointSet loneLeadSurrogateRanges = codePointSet.createIntersection(Constants.LEAD_SURROGATES, tmp);
         CodePointSet loneTrailSurrogateRanges = codePointSet.createIntersection(Constants.TRAIL_SURROGATES, tmp);

         assert astralRanges.matchesSomething() || loneLeadSurrogateRanges.matchesSomething() || loneTrailSurrogateRanges.matchesSomething();

         if (bmpRanges.matchesSomething()) {
            Sequence bmpAlternative = group.addSequence(this.ast);
            bmpAlternative.add(this.createCharClass(bmpRanges, token));
         }

         if (loneLeadSurrogateRanges.matchesSomething()) {
            Sequence loneLeadSurrogateAlternative = group.addSequence(this.ast);
            loneLeadSurrogateAlternative.add(this.createCharClass(loneLeadSurrogateRanges, token));
            loneLeadSurrogateAlternative.add(this.globals.noTrailSurrogateAhead.copyRecursive(this.ast, this.compilationBuffer));
         }

         if (loneTrailSurrogateRanges.matchesSomething()) {
            Sequence loneTrailSurrogateAlternative = group.addSequence(this.ast);
            loneTrailSurrogateAlternative.add(this.globals.noLeadSurrogateBehind.copyRecursive(this.ast, this.compilationBuffer));
            loneTrailSurrogateAlternative.add(this.createCharClass(loneTrailSurrogateRanges, token));
         }

         if (astralRanges.matchesSomething()) {
            CodePointSetAccumulator completeRanges = this.compilationBuffer.getCodePointSetAccumulator1();
            completeRanges.clear();
            char curLead = Character.highSurrogate(astralRanges.getLo(0));
            CodePointSetAccumulator curTrails = this.compilationBuffer.getCodePointSetAccumulator2();
            curTrails.clear();

            for (int i = 0; i < astralRanges.size(); i++) {
               char startLead = Character.highSurrogate(astralRanges.getLo(i));
               char startTrail = Character.lowSurrogate(astralRanges.getLo(i));
               char endLead = Character.highSurrogate(astralRanges.getHi(i));
               char endTrail = Character.lowSurrogate(astralRanges.getHi(i));
               if (startLead > curLead) {
                  if (!curTrails.isEmpty()) {
                     Sequence finishedAlternative = group.addSequence(this.ast);
                     finishedAlternative.add(this.createCharClass(CodePointSet.create(curLead), token));
                     finishedAlternative.add(this.createCharClass(curTrails.toCodePointSet(), token));
                  }

                  curLead = startLead;
                  curTrails.clear();
               }

               if (startLead == endLead) {
                  curTrails.addRange(startTrail, endTrail);
               } else {
                  if (startTrail != Constants.TRAIL_SURROGATES.getLo(0)) {
                     curTrails.addRange(startTrail, Constants.TRAIL_SURROGATES.getHi(0));

                     assert startLead < '\uffff';

                     startLead++;
                  }

                  if (!curTrails.isEmpty()) {
                     Sequence finishedAlternative = group.addSequence(this.ast);
                     finishedAlternative.add(this.createCharClass(CodePointSet.create(curLead), token));
                     finishedAlternative.add(this.createCharClass(curTrails.toCodePointSet(), token));
                  }

                  curLead = endLead;
                  curTrails.clear();
                  if (endTrail != Constants.TRAIL_SURROGATES.getHi(0)) {
                     curTrails.addRange(Constants.TRAIL_SURROGATES.getLo(0), endTrail);

                     assert endLead > 0;

                     endLead--;
                  }

                  if (startLead <= endLead) {
                     completeRanges.addRange(startLead, endLead);
                  }
               }
            }

            if (!curTrails.isEmpty()) {
               Sequence lastAlternative = group.addSequence(this.ast);
               lastAlternative.add(this.createCharClass(CodePointSet.create(curLead), token));
               lastAlternative.add(this.createCharClass(curTrails.toCodePointSet(), token));
            }

            if (!completeRanges.isEmpty()) {
               Sequence completeRangesAlt = this.ast.createSequence();
               group.insertFirst(completeRangesAlt);
               completeRangesAlt.add(this.createCharClass(completeRanges.toCodePointSet(), token));
               completeRangesAlt.add(this.createCharClass(Constants.TRAIL_SURROGATES, token));
            }
         }

         assert group.size() != 1 || group.getFirstAlternative().getTerms().size() != 1;

         return group;
      } else {
         return this.createCharClass(codePointSet, token, token.wasSingleChar());
      }
   }

   public void addBackReference(Token.BackReference token) {
      BackReference backReference = this.ast.createBackReference(token.getGroupNr());
      this.ast.addSourceSection(backReference, token);
      this.addTerm(backReference);
      if (backReference.getGroupNr() >= this.groupCount.getCount()) {
         backReference.setForwardReference();
      } else if (isNestedBackReference(backReference)) {
         backReference.setNestedBackReference();
      }
   }

   public void addBackReference(int groupNumber) {
      this.addBackReference(Token.createBackReference(groupNumber));
   }

   private static boolean isNestedBackReference(BackReference backReference) {
      RegexASTNode parent = backReference.getParent().getParent();

      while (parent.asGroup().getGroupNumber() != backReference.getGroupNr()) {
         parent = parent.getParent();
         if (parent.isRoot()) {
            return false;
         }

         if (parent.isSubtreeRoot()) {
            parent = parent.getParent();
         }

         parent = parent.getParent();
      }

      return true;
   }

   public void addSubexpressionCall(int groupNumber) {
      SubexpressionCall subexpressionCall = this.ast.createSubexpressionCall(groupNumber);
      this.addTerm(subexpressionCall);
   }

   public void addPositionAssertion(Token token) {
      PositionAssertion.Type type;
      if (token.kind == Token.Kind.caret) {
         type = PositionAssertion.Type.CARET;
      } else {
         if (token.kind != Token.Kind.dollar) {
            throw new IllegalArgumentException("unexpected token kind: " + token.kind);
         }

         type = PositionAssertion.Type.DOLLAR;
      }

      PositionAssertion positionAssertion = this.ast.createPositionAssertion(type);
      this.ast.addSourceSection(positionAssertion, token);
      this.addTerm(positionAssertion);
   }

   public void addCaret() {
      this.addPositionAssertion(Token.createCaret());
   }

   public void addDollar() {
      this.addPositionAssertion(Token.createDollar());
   }

   public void addQuantifier(Token.Quantifier quantifier) {
      assert this.curTerm == this.curSequence.getLastTerm();

      if (quantifier.getMin() == -1) {
         this.replaceCurTermWithDeadNode();
      } else if (quantifier.getMax() == 0) {
         this.removeCurTerm();
      } else {
         boolean curTermIsZeroWidthGroup = this.curTerm.isGroup() && this.curTerm.asGroup().isAlwaysZeroWidth();
         if (this.options.getFlavor().canHaveEmptyLoopIterations()) {
            if (quantifier.getMin() == 0 && this.curTerm.isCharacterClass() && this.curTerm.asCharacterClass().getCharSet().matchesNothing()) {
               this.removeCurTerm();
               return;
            }
         } else if (quantifier.getMin() == 0
            && (
               this.curTerm.isLookAroundAssertion()
                  || curTermIsZeroWidthGroup
                  || this.curTerm.isCharacterClass() && this.curTerm.asCharacterClass().getCharSet().matchesNothing()
            )) {
            this.removeCurTerm();
            return;
         }

         if (quantifier.getMin() <= 0 || !this.curTerm.isLookAroundAssertion() && !curTermIsZeroWidthGroup) {
            if (quantifier.getMin() != 1 || quantifier.getMax() != 1) {
               this.curTerm = this.addQuantifier(this.curTerm, quantifier);
               if (this.curSequence.size() > 1) {
                  Term prevTerm = this.curSequence.getTerms().get(this.curSequence.size() - 2);
                  if (prevTerm.isQuantifiableTerm()) {
                     QuantifiableTerm prev = prevTerm.asQuantifiableTerm();
                     if (prev.hasQuantifier() && this.curTerm.asQuantifiableTerm().equalsSemantic(prev, true)) {
                        this.removeCurTerm();
                        long min = (long)prev.getQuantifier().getMin() + quantifier.getMin();
                        long max = !prev.getQuantifier().isInfiniteLoop() && !quantifier.isInfiniteLoop()
                           ? (long)prev.getQuantifier().getMax() + quantifier.getMax()
                           : -1L;
                        if (min > 2147483647L) {
                           this.replaceCurTermWithDeadNode();
                           return;
                        }

                        if (max > 2147483647L) {
                           max = -1L;
                        }

                        this.setQuantifier(prev, Token.createQuantifier((int)min, (int)max, prev.getQuantifier().isGreedy() || quantifier.isGreedy()));
                     }
                  }
               }
            }
         }
      }
   }

   private QuantifiableTerm addQuantifier(Term term, Token.Quantifier quantifier) {
      QuantifiableTerm quantifiableTerm = (QuantifiableTerm)(term.isQuantifiableTerm() ? term.asQuantifiableTerm() : this.wrapTermInGroup(term));
      if (quantifiableTerm.hasQuantifier()) {
         quantifiableTerm = this.wrapTermInGroup(term);
      }

      this.ast.addSourceSection(quantifiableTerm, quantifier);
      this.setQuantifier(quantifiableTerm, quantifier);
      return quantifiableTerm;
   }

   private void setQuantifier(QuantifiableTerm term, Token.Quantifier quantifier) {
      term.setQuantifier(quantifier);
      if (!term.isUnrollingCandidate()) {
         this.properties.setLargeCountedRepetitions();
      }

      this.properties.setQuantifiers();
   }

   private Group wrapTermInGroup(Term term) {
      Group wrapperGroup = this.ast.createGroup();
      if (term.isGroup()) {
         wrapperGroup.setEnclosedCaptureGroupsLow(term.asGroup().getEnclosedCaptureGroupsLow());
         wrapperGroup.setEnclosedCaptureGroupsHigh(term.asGroup().getEnclosedCaptureGroupsHigh());
      } else if (term.isAtomicGroup()) {
         wrapperGroup.setEnclosedCaptureGroupsLow(term.asAtomicGroup().getEnclosedCaptureGroupsLow());
         wrapperGroup.setEnclosedCaptureGroupsHigh(term.asAtomicGroup().getEnclosedCaptureGroupsHigh());
      }

      Sequence wrapperSequence = wrapperGroup.addSequence(this.ast);
      term.getParent().asSequence().replace(term.getSeqIndex(), wrapperGroup);
      wrapperSequence.add(term);
      return wrapperGroup;
   }

   public void addCopy(Token token, Group sourceGroup) {
      Group copy = sourceGroup.copyRecursive(this.ast, this.compilationBuffer);
      if (this.options.isDumpAutomataWithSourceSections()) {
         this.setSourceSectionVisitor.run(copy, token);
      }

      this.addTerm(copy);
   }

   public void removeCurTerm() {
      this.ast.getNodeCount().dec(this.countVisitor.count(this.curSequence.getLastTerm()));
      this.curSequence.removeLastTerm();
      this.curTerm = this.curSequence.isEmpty() ? null : this.curSequence.getLastTerm();
   }

   public void addDeadNode() {
      this.addTerm(this.createCharClass(CodePointSet.getEmpty(), null));
   }

   public void replaceCurTermWithDeadNode() {
      this.removeCurTerm();
      this.addDeadNode();
   }

   public void wrapCurTermInGroup() {
      this.curTerm = this.wrapTermInGroup(this.curTerm);
   }

   public void wrapCurTermInAtomicGroup() {
      Group atomicGroupContents = this.wrapTermInGroup(this.curTerm);
      AtomicGroup atomicGroup = this.ast.createAtomicGroup();
      this.curSequence.replace(atomicGroupContents.getSeqIndex(), atomicGroup);
      atomicGroup.setGroup(atomicGroupContents);
      this.curTerm = atomicGroup;
   }

   private void optimizeGroup() {
      sortAlternatives(this.curGroup);
      this.mergeCommonPrefixes(this.curGroup);
   }

   private boolean tryMergeSingleCharClassAlternations() {
      if (this.curGroup.size() > 1 && this.curSequence.isSingleCharClass()) {
         assert this.curSequence == this.curGroup.getAlternatives().get(this.curGroup.size() - 1);

         Sequence prevSequence = this.curGroup.getAlternatives().get(this.curGroup.size() - 2);
         if (prevSequence.isSingleCharClass()) {
            this.mergeCharClasses((CharacterClass)prevSequence.getFirstTerm(), (CharacterClass)this.curSequence.getFirstTerm());
            this.curSequence.removeLastTerm();
            this.ast.getNodeCount().dec();
            return true;
         }
      }

      return false;
   }

   private void mergeCharClasses(CharacterClass dst, CharacterClass src) {
      dst.setCharSet(dst.getCharSet().union(src.getCharSet()));
      dst.setWasSingleChar(false);
      this.ast.addSourceSections(dst, this.ast.getSourceSections(src));
   }

   private static void sortAlternatives(Group group) {
      if (group.size() >= 2) {
         int begin = 0;

         while (begin + 1 < group.size()) {
            int end = findSingleCharAlternatives(group, begin);
            if (end > begin + 1) {
               group.getAlternatives().subList(begin, end).sort(Comparator.comparingInt(a -> a.getFirstTerm().asCharacterClass().getCharSet().getMin()));
               begin = end;
            } else {
               begin++;
            }
         }
      }
   }

   private void mergeCommonPrefixes(Group group) {
      if (group.size() >= 2) {
         ArrayList<Sequence> newAlternatives = null;
         RegexASTBuilder.IsDeterministicVisitor isDeterministicVisitor = new RegexASTBuilder.IsDeterministicVisitor();
         int lastEnd = 0;
         int begin = 0;

         while (begin + 1 < group.size()) {
            int end = findMatchingAlternatives(group, begin);
            if (end >= 0 && isDeterministicVisitor.isDeterministic(group.getAlternatives().get(begin).getFirstTerm())) {
               if (newAlternatives == null) {
                  newAlternatives = new ArrayList<>();
               }

               for (int i = lastEnd; i < begin; i++) {
                  newAlternatives.add(group.getAlternatives().get(i));
               }

               lastEnd = end;
               int prefixSize = 1;

               while (
                  alternativesAreEqualAt(group, begin, end, prefixSize)
                     && isDeterministicVisitor.isDeterministic(group.getAlternatives().get(begin).get(prefixSize))
               ) {
                  prefixSize++;
               }

               Sequence prefixSeq = this.ast.createSequence();
               Group innerGroup = this.ast.createGroup();
               int enclosedCGLo = Integer.MAX_VALUE;
               int enclosedCGHi = Integer.MIN_VALUE;
               boolean emptyAlt = false;

               for (int i = begin; i < end; i++) {
                  Sequence s = group.getAlternatives().get(i);

                  assert s.size() >= prefixSize;

                  for (int j = 0; j < prefixSize; j++) {
                     Term t = s.getTerms().get(j);
                     if (i == begin) {
                        prefixSeq.add(t);
                     } else {
                        this.ast.addSourceSections(prefixSeq.getTerms().get(j), this.ast.getSourceSections(t));
                        this.ast.getNodeCount().dec(this.countVisitor.count(t));
                     }
                  }

                  if (i > begin
                     && s.size() - prefixSize == 1
                     && s.getLastTerm().isCharacterClass()
                     && !s.getLastTerm().asCharacterClass().hasQuantifier()
                     && innerGroup.getLastAlternative().isSingleCharClass()) {
                     this.mergeCharClasses(innerGroup.getLastAlternative().getFirstTerm().asCharacterClass(), s.getLastTerm().asCharacterClass());
                  } else if (prefixSize == s.size()) {
                     if (!emptyAlt) {
                        innerGroup.addSequence(this.ast);
                     }

                     emptyAlt = true;
                  } else {
                     Sequence copy = innerGroup.addSequence(this.ast);

                     for (int jx = prefixSize; jx < s.size(); jx++) {
                        Term t = s.getTerms().get(jx);
                        copy.add(t);
                        if (t.isGroup()) {
                           Group g = t.asGroup();
                           if (g.getEnclosedCaptureGroupsLow() != g.getEnclosedCaptureGroupsHigh()) {
                              enclosedCGLo = Math.min(enclosedCGLo, g.getEnclosedCaptureGroupsLow());
                              enclosedCGHi = Math.max(enclosedCGHi, g.getEnclosedCaptureGroupsHigh());
                           }

                           if (g.isCapturing()) {
                              enclosedCGLo = Math.min(enclosedCGLo, g.getGroupNumber());
                              enclosedCGHi = Math.max(enclosedCGHi, g.getGroupNumber() + 1);
                           }
                        }
                     }
                  }
               }

               if (enclosedCGLo != Integer.MAX_VALUE) {
                  innerGroup.setEnclosedCaptureGroupsLow(enclosedCGLo);
                  innerGroup.setEnclosedCaptureGroupsHigh(enclosedCGHi);
               }

               if (!innerGroup.isEmpty() && (innerGroup.size() != 1 || !innerGroup.getFirstAlternative().isEmpty())) {
                  this.mergeCommonPrefixes(innerGroup);
                  prefixSeq.add(innerGroup);
               }

               newAlternatives.add(prefixSeq);
               begin = end;
            } else {
               begin++;
            }
         }

         if (newAlternatives != null) {
            for (int i = lastEnd; i < group.size(); i++) {
               newAlternatives.add(group.getAlternatives().get(i));
            }

            group.setAlternatives(newAlternatives);
         }
      }
   }

   private static boolean alternativesAreEqualAt(Group group, int altBegin, int altEnd, int iTerm) {
      if (group.getAlternatives().get(altBegin).size() <= iTerm) {
         return false;
      } else {
         Term cmp = group.getAlternatives().get(altBegin).getTerms().get(iTerm);

         for (int i = altBegin + 1; i < altEnd; i++) {
            Sequence s = group.getAlternatives().get(i);
            if (s.size() <= iTerm) {
               return false;
            }

            if (!s.getTerms().get(iTerm).equalsSemantic(cmp)) {
               return false;
            }
         }

         return true;
      }
   }

   private static int findMatchingAlternatives(Group group, int begin) {
      if (group.getAlternatives().get(begin).isEmpty()) {
         return -1;
      } else {
         Term cmp = group.getAlternatives().get(begin).getFirstTerm();
         int ret = -1;

         for (int i = begin + 1; i < group.size(); i++) {
            Sequence s = group.getAlternatives().get(i);
            if (s.isEmpty() || !cmp.equalsSemantic(s.getFirstTerm())) {
               return ret;
            }

            ret = i + 1;
         }

         return ret;
      }
   }

   private static int findSingleCharAlternatives(Group group, int begin) {
      int ret = -1;
      int i = begin;

      while (i < group.size()) {
         Sequence s = group.getAlternatives().get(i);
         if (!s.isEmpty() && s.getFirstTerm().isCharacterClass()) {
            CharacterClass firstCC = s.getFirstTerm().asCharacterClass();
            if (firstCC.wasSingleChar() && (!firstCC.hasQuantifier() || firstCC.getQuantifier().getMin() != 0)) {
               ret = i + 1;
               i++;
               continue;
            }

            return ret;
         }

         return ret;
      }

      return ret;
   }

   private static final class IsDeterministicVisitor extends DepthFirstTraversalRegexASTVisitor {
      private boolean result;

      public boolean isDeterministic(RegexASTNode node) {
         this.result = true;
         this.run(node);
         return this.result;
      }

      private static boolean hasNonDeterministicQuantifier(QuantifiableTerm term) {
         if (term.hasQuantifier()) {
            Token.Quantifier quantifier = term.getQuantifier();
            if (quantifier.getMin() != quantifier.getMax()) {
               return true;
            }
         }

         return false;
      }

      @Override
      protected void visit(BackReference backReference) {
         if (hasNonDeterministicQuantifier(backReference)) {
            this.result = false;
         }
      }

      @Override
      protected void visit(Group group) {
         if (hasNonDeterministicQuantifier(group)) {
            this.result = false;
         } else {
            if (group.getAlternatives().size() > 1) {
               this.result = false;
            }
         }
      }

      @Override
      protected void visit(CharacterClass characterClass) {
         if (hasNonDeterministicQuantifier(characterClass)) {
            this.result = false;
         }
      }
   }
}
