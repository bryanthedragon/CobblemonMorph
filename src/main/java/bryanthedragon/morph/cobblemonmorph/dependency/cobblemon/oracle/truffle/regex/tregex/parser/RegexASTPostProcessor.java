package com.oracle.truffle.regex.tregex.parser;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.regex.RegexFlags;
import com.oracle.truffle.regex.charset.CodePointSet;
import com.oracle.truffle.regex.charset.Constants;
import com.oracle.truffle.regex.tregex.buffer.CompilationBuffer;
import com.oracle.truffle.regex.tregex.buffer.ObjectArrayBuffer;
import com.oracle.truffle.regex.tregex.parser.ast.BackReference;
import com.oracle.truffle.regex.tregex.parser.ast.CalcASTPropsVisitor;
import com.oracle.truffle.regex.tregex.parser.ast.CharacterClass;
import com.oracle.truffle.regex.tregex.parser.ast.Group;
import com.oracle.truffle.regex.tregex.parser.ast.LookAroundAssertion;
import com.oracle.truffle.regex.tregex.parser.ast.PositionAssertion;
import com.oracle.truffle.regex.tregex.parser.ast.QuantifiableTerm;
import com.oracle.truffle.regex.tregex.parser.ast.RegexAST;
import com.oracle.truffle.regex.tregex.parser.ast.Sequence;
import com.oracle.truffle.regex.tregex.parser.ast.SubexpressionCall;
import com.oracle.truffle.regex.tregex.parser.ast.Term;
import com.oracle.truffle.regex.tregex.parser.ast.visitors.CopyVisitor;
import com.oracle.truffle.regex.tregex.parser.ast.visitors.DepthFirstTraversalRegexASTVisitor;
import com.oracle.truffle.regex.tregex.parser.ast.visitors.InitIDVisitor;
import com.oracle.truffle.regex.tregex.parser.ast.visitors.MarkLookBehindEntriesVisitor;
import com.oracle.truffle.regex.tregex.parser.ast.visitors.NodeCountVisitor;
import com.oracle.truffle.regex.tregex.string.Encodings;
import java.util.ArrayList;
import java.util.Optional;

public class RegexASTPostProcessor {
   private final RegexAST ast;
   private final RegexProperties properties;
   private final RegexFlags flags;
   private final CompilationBuffer compilationBuffer;

   public RegexASTPostProcessor(RegexAST ast, CompilationBuffer compilationBuffer) {
      this.ast = ast;
      this.properties = ast.getProperties();
      this.flags = ast.getFlags();
      this.compilationBuffer = compilationBuffer;
   }

   public void prepareForDFA() {
      if (this.ast.getOptions().isBooleanMatch()) {
         RegexASTPostProcessor.DisableCaptureGroupsVisitor.disableCaptureGroups(this.ast);
      }

      RegexASTPostProcessor.OptimizeLookAroundsVisitor.optimizeLookArounds(this.ast, this.compilationBuffer);
      if (this.properties.hasQuantifiers()) {
         RegexASTPostProcessor.UnrollQuantifiersVisitor.unrollQuantifiers(this.ast, this.compilationBuffer);
      }

      CalcASTPropsVisitor.run(this.ast, this.compilationBuffer);
      this.ast.createPrefix();
      InitIDVisitor.init(this.ast);
      if (this.ast.canTransformToDFA()) {
         new MarkLookBehindEntriesVisitor(this.ast).run();
      }

      this.checkInnerLiteral();
   }

   private void checkInnerLiteral() {
      if (!this.ast.isLiteralString()
         && !this.ast.getRoot().startsWithCaret()
         && !this.ast.getRoot().endsWithDollar()
         && this.ast.getRoot().size() == 1
         && !this.flags.isSticky()) {
         ArrayList<Term> terms = this.ast.getRoot().getFirstAlternative().getTerms();
         int literalStart = -1;
         int literalEnd = -1;

         for (int i = 0; i < terms.size(); i++) {
            Term t = terms.get(i);
            if (!t.isCharacterClass()
               || !t.asCharacterClass().getCharSet().matchesSingleChar() && !t.asCharacterClass().getCharSet().matches2CharsWith1BitDifference()
               || !this.ast.getEncoding().isFixedCodePointWidth(t.asCharacterClass().getCharSet())
               || this.ast.getEncoding() == Encodings.UTF_16 && t.asCharacterClass().getCharSet().intersects(Constants.SURROGATES)) {
               if (literalStart >= 0 || t.hasLoops() || t.hasBackReferences()) {
                  break;
               }

               if (t.getMaxPath() > 4) {
                  return;
               }
            } else {
               if (literalStart < 0) {
                  literalStart = i;
               }

               literalEnd = i + 1;
            }
         }

         if (literalStart >= 0 && (literalStart > 0 || literalEnd - literalStart > 1)) {
            this.properties.setInnerLiteral(literalStart, literalEnd);
         }
      }
   }

   private static final class DisableCaptureGroupsVisitor extends DepthFirstTraversalRegexASTVisitor {
      private final RegexAST ast;

      private DisableCaptureGroupsVisitor(RegexAST ast) {
         this.ast = ast;
      }

      public static void disableCaptureGroups(RegexAST ast) {
         new RegexASTPostProcessor.DisableCaptureGroupsVisitor(ast).run(ast.getRoot());
      }

      @Override
      protected void visit(Group group) {
         if (group.isCapturing() && !this.ast.isGroupReferenced(group.getGroupNumber())) {
            group.clearGroupNumber();
         }
      }
   }

   private static final class OptimizeLookAroundsVisitor extends DepthFirstTraversalRegexASTVisitor {
      private final RegexAST ast;
      private final CompilationBuffer compilationBuffer;
      private final NodeCountVisitor countVisitor = new NodeCountVisitor();

      private OptimizeLookAroundsVisitor(RegexAST ast, CompilationBuffer compilationBuffer) {
         this.ast = ast;
         this.compilationBuffer = compilationBuffer;
      }

      public static void optimizeLookArounds(RegexAST ast, CompilationBuffer compilationBuffer) {
         new RegexASTPostProcessor.OptimizeLookAroundsVisitor(ast, compilationBuffer).run(ast.getRoot());
      }

      @Override
      protected void leave(Sequence sequence) {
         for (int i = 0; i < sequence.size(); i++) {
            Term term = sequence.get(i);
            if (term.isLookAroundAssertion()) {
               Optional<Term> replacement = this.optimizeLookAround((LookAroundAssertion)term);
               if (replacement != null) {
                  if (replacement.isPresent()) {
                     sequence.replace(i, replacement.get());
                  } else {
                     sequence.removeTerm(i, this.compilationBuffer);
                     i--;
                  }
               }
            }
         }
      }

      private Optional<Term> optimizeLookAround(LookAroundAssertion lookaround) {
         Group group = lookaround.getGroup();
         if (group.size() == 1 && group.getFirstAlternative().isEmpty()) {
            if (lookaround.isNegated()) {
               this.ast.getNodeCount().dec(this.countVisitor.count(lookaround));
               return Optional.of(this.ast.createCharacterClass(CodePointSet.getEmpty()));
            } else {
               this.ast.getNodeCount().dec(this.countVisitor.count(lookaround));
               return Optional.empty();
            }
         } else {
            if (!lookaround.isNegated()) {
               if (group.size() == 1 && group.getFirstAlternative().size() == 1 && group.getFirstAlternative().getFirstTerm().isPositionAssertion()) {
                  this.ast.getNodeCount().dec(this.countVisitor.count(lookaround));
                  PositionAssertion positionAssertion = (PositionAssertion)group.getFirstAlternative().getFirstTerm();
                  this.ast.register(positionAssertion);
                  return Optional.of(positionAssertion);
               }

               int innerPositionAssertion = -1;

               for (int i = 0; i < group.size(); i++) {
                  Sequence s = group.getAlternatives().get(i);
                  if (s.size() == 1 && s.getFirstTerm().isPositionAssertion()) {
                     innerPositionAssertion = i;
                     break;
                  }
               }

               if (innerPositionAssertion >= 0) {
                  Sequence removed = group.getAlternatives().remove(innerPositionAssertion);
                  Group wrapGroup = this.ast.createGroup();
                  wrapGroup.setEnclosedCaptureGroupsLow(group.getEnclosedCaptureGroupsLow());
                  wrapGroup.setEnclosedCaptureGroupsHigh(group.getEnclosedCaptureGroupsHigh());
                  wrapGroup.add(removed);
                  Sequence wrapSeq = wrapGroup.addSequence(this.ast);
                  wrapSeq.add(lookaround);
                  return Optional.of(wrapGroup);
               }
            }

            if (lookaround.isNegated() && group.size() == 1 && group.getFirstAlternative().isSingleCharClass()) {
               CharacterClass cc = group.getFirstAlternative().getFirstTerm().asCharacterClass();

               assert !this.ast.getFlags().isUnicode()
                  || !this.ast.getOptions().isUTF16ExplodeAstralSymbols()
                  || cc.getCharSet().matchesNothing()
                  || cc.getCharSet().getMax() <= 65535;

               if (cc.getCharSet().isEmpty()) {
                  return Optional.empty();
               } else {
                  Group wrapGroup = this.ast.createGroup();
                  Sequence positionAssertionSeq = wrapGroup.addSequence(this.ast);
                  positionAssertionSeq.add(
                     this.ast.createPositionAssertion(lookaround.isLookAheadAssertion() ? PositionAssertion.Type.DOLLAR : PositionAssertion.Type.CARET)
                  );
                  Sequence wrapSeq = wrapGroup.addSequence(this.ast);
                  wrapSeq.add(lookaround);
                  lookaround.setNegated(false);
                  cc.setCharSet(cc.getCharSet().createInverse(this.ast.getEncoding()));
                  return Optional.of(wrapGroup);
               }
            } else {
               return null;
            }
         }
      }
   }

   private static final class UnrollQuantifiersVisitor extends DepthFirstTraversalRegexASTVisitor {
      private final RegexAST ast;
      private final RegexASTPostProcessor.UnrollQuantifiersVisitor.ShouldUnrollQuantifierVisitor shouldUnrollVisitor = new RegexASTPostProcessor.UnrollQuantifiersVisitor.ShouldUnrollQuantifierVisitor();
      private final RegexASTPostProcessor.UnrollQuantifiersVisitor.QuantifierExpander quantifierExpander;

      private UnrollQuantifiersVisitor(RegexAST ast, CompilationBuffer compilationBuffer) {
         this.ast = ast;
         this.quantifierExpander = new RegexASTPostProcessor.UnrollQuantifiersVisitor.QuantifierExpander(ast, compilationBuffer);
      }

      public static void unrollQuantifiers(RegexAST ast, CompilationBuffer compilationBuffer) {
         new RegexASTPostProcessor.UnrollQuantifiersVisitor(ast, compilationBuffer).run(ast.getRoot());
      }

      @Override
      protected void visit(BackReference backReference) {
         if (backReference.hasNotUnrolledQuantifier()) {
            this.quantifierExpander.expandQuantifier(backReference, this.shouldUnroll(backReference));
         }
      }

      @Override
      protected void visit(CharacterClass characterClass) {
         if (characterClass.hasNotUnrolledQuantifier()) {
            this.quantifierExpander.expandQuantifier(characterClass, this.shouldUnroll(characterClass));
         }
      }

      @Override
      protected void leave(Group group) {
         if (group.hasNotUnrolledQuantifier() && !group.getFirstAlternative().isExpandedQuantifier() && !group.getLastAlternative().isExpandedQuantifier()) {
            this.quantifierExpander.expandQuantifier(group, this.shouldUnroll(group) && this.shouldUnrollVisitor.shouldUnroll(group));
         }
      }

      @Override
      protected void visit(SubexpressionCall subexpressionCall) {
         throw CompilerDirectives.shouldNotReachHere("subexpression calls should be expanded by the parser");
      }

      private boolean shouldUnroll(QuantifiableTerm term) {
         return term.getQuantifier().isUnrollTrivial() || this.ast.getNumberOfNodes() <= 4000 && term.isUnrollingCandidate();
      }

      private static final class QuantifierExpander {
         private final RegexAST ast;
         private final CompilationBuffer compilationBuffer;
         private final CopyVisitor copyVisitor;
         private Group curGroup;
         private Sequence curSequence;
         private Term curTerm;

         QuantifierExpander(RegexAST ast, CompilationBuffer compilationBuffer) {
            this.ast = ast;
            this.compilationBuffer = compilationBuffer;
            this.copyVisitor = new CopyVisitor(ast);
         }

         private void pushGroup() {
            Group group = this.ast.createGroup();
            this.curSequence.add(group);
            this.curGroup = group;
            this.nextSequence();
         }

         private void popGroup() {
            this.curTerm = this.curGroup;
            this.curSequence = this.curGroup.getParent().asSequence();
            this.curGroup = this.curSequence.getParent();
         }

         private void nextSequence() {
            this.curSequence = this.curGroup.addSequence(this.ast);
            this.curTerm = null;
         }

         private void addTerm(Term term) {
            this.curSequence.add(term);
            this.curTerm = term;
         }

         private void createOptionalBranch(QuantifiableTerm term, Token.Quantifier quantifier, boolean copy, boolean unroll, int recurse, boolean emptyGuard) {
            this.addTerm((Term)(copy ? this.copyVisitor.copy(term) : term));
            this.curTerm.setExpandedQuantifier(false);
            ((QuantifiableTerm)this.curTerm).setQuantifier(null);
            this.curTerm.setEmptyGuard(emptyGuard);
            this.createOptional(term, quantifier, true, unroll, recurse - 1, emptyGuard);
         }

         private void createOptional(QuantifiableTerm term, Token.Quantifier quantifier, boolean copy, boolean unroll, int recurse, boolean emptyGuard) {
            if (recurse >= 0) {
               if (copy) {
                  this.pushGroup();
               }

               this.curGroup.setExpandedQuantifier(unroll);
               this.curGroup.setQuantifier(quantifier);
               if (term.isGroup()) {
                  this.curGroup.setEnclosedCaptureGroupsLow(term.asGroup().getEnclosedCaptureGroupsLow());
                  this.curGroup.setEnclosedCaptureGroupsHigh(term.asGroup().getEnclosedCaptureGroupsHigh());
               }

               if (quantifier.isGreedy()) {
                  this.createOptionalBranch(term, quantifier, copy, unroll, recurse, emptyGuard);
                  this.nextSequence();
                  this.curSequence.setExpandedQuantifier(true);
               } else {
                  this.curSequence.setExpandedQuantifier(true);
                  this.nextSequence();
                  this.createOptionalBranch(term, quantifier, copy, unroll, recurse, emptyGuard);
               }

               this.popGroup();
            }
         }

         private void expandQuantifier(QuantifiableTerm toExpand, boolean unroll) {
            assert toExpand.hasNotUnrolledQuantifier();

            Token.Quantifier quantifier = toExpand.getQuantifier();

            assert !unroll || toExpand.isUnrollingCandidate();

            this.curTerm = toExpand;
            this.curSequence = (Sequence)this.curTerm.getParent();
            this.curGroup = this.curSequence.getParent();
            ObjectArrayBuffer<Term> buf = this.compilationBuffer.getObjectBuffer1();
            if (unroll && quantifier.getMin() > 0) {
               int size = this.curSequence.size();

               for (int i = toExpand.getSeqIndex() + 1; i < size; i++) {
                  buf.add(this.curSequence.getLastTerm());
                  this.curSequence.removeLastTerm();
               }

               toExpand.setExpandedQuantifier(true);

               for (int i = 0; i < quantifier.getMin() - 1; i++) {
                  Term term = this.copyVisitor.copy(toExpand);
                  term.setExpandedQuantifier(true);
                  this.curSequence.add(term);
                  this.curTerm = term;
               }
            } else {
               assert !unroll || quantifier.getMin() == 0;

               this.curGroup = this.ast.createGroup();
               this.curGroup.addSequence(this.ast);
               this.curSequence.replace(toExpand.getSeqIndex(), this.curGroup);
               this.curSequence = this.curGroup.getFirstAlternative();
               this.curTerm = null;
            }

            this.createOptional(
               toExpand,
               quantifier,
               unroll && quantifier.getMin() > 0,
               unroll,
               unroll && !quantifier.isInfiniteLoop() ? quantifier.getMax() - quantifier.getMin() - 1 : 0,
               !this.ast.getOptions().getFlavor().canHaveEmptyLoopIterations()
            );
            if (!unroll || quantifier.isInfiniteLoop()) {
               ((Group)this.curTerm).setLoop(true);
            }

            if (unroll && quantifier.getMin() > 0) {
               for (int i = buf.length() - 1; i >= 0; i--) {
                  this.curSequence.add(buf.get(i));
               }
            }
         }
      }

      private static final class ShouldUnrollQuantifierVisitor extends DepthFirstTraversalRegexASTVisitor {
         private Group root;
         private boolean result;

         boolean shouldUnroll(Group group) {
            assert group.hasQuantifier();

            this.result = true;
            this.root = group;
            this.run(group);
            return this.result;
         }

         @Override
         protected void visit(BackReference backReference) {
            this.result = false;
         }

         @Override
         protected void visit(Group group) {
            if (group != this.root && group.hasNotUnrolledQuantifier()) {
               this.result = false;
            }
         }
      }
   }
}
