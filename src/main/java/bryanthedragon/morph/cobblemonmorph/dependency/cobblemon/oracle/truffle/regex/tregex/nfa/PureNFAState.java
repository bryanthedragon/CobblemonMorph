package com.oracle.truffle.regex.tregex.nfa;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.regex.charset.CodePointSet;
import com.oracle.truffle.regex.charset.CodePointSetAccumulator;
import com.oracle.truffle.regex.tregex.automaton.BasicState;
import com.oracle.truffle.regex.tregex.buffer.CompilationBuffer;
import com.oracle.truffle.regex.tregex.parser.ast.RegexAST;
import com.oracle.truffle.regex.tregex.parser.ast.Term;
import com.oracle.truffle.regex.tregex.util.json.Json;
import com.oracle.truffle.regex.tregex.util.json.JsonConvertible;
import com.oracle.truffle.regex.tregex.util.json.JsonObject;
import java.util.Arrays;

public final class PureNFAState extends BasicState<PureNFAState, PureNFATransition> {
   private static final PureNFATransition[] EMPTY_TRANSITIONS = new PureNFATransition[0];
   public static final byte KIND_INITIAL_OR_FINAL_STATE = 0;
   public static final byte KIND_CHARACTER_CLASS = 1;
   public static final byte KIND_SUB_MATCHER = 2;
   public static final byte KIND_BACK_REFERENCE = 3;
   public static final byte KIND_EMPTY_MATCH = 4;
   private static final byte FLAG_IS_LOOK_AROUND = 16;
   private static final byte FLAG_IS_SUB_MATCHER_NEGATED = 32;
   private static final byte FLAG_IS_DETERMINISTIC = 64;
   private final int astNodeId;
   private final int extraId;
   private final byte kind;
   private final CodePointSet charSet;

   public PureNFAState(int id, Term t) {
      super(id, EMPTY_TRANSITIONS);
      this.astNodeId = t.getId();
      this.kind = getKind(t);
      this.extraId = this.isSubMatcher() ? t.asSubtreeRootNode().getSubTreeId() : (this.isBackReference() ? t.asBackReference().getGroupNr() : -1);
      this.charSet = this.isCharacterClass() ? t.asCharacterClass().getCharSet() : null;
      this.setLookAround(t.isLookAroundAssertion());
      if (t.isLookAroundAssertion()) {
         this.setSubMatcherNegated(t.asLookAroundAssertion().isNegated());
      }
   }

   private int getAstNodeId() {
      return this.astNodeId;
   }

   public Term getAstNode(RegexAST ast) {
      return (Term)ast.getState(this.astNodeId);
   }

   public byte getKind() {
      return this.kind;
   }

   public boolean isCharacterClass() {
      return this.kind == 1;
   }

   public boolean isSubMatcher() {
      return this.kind == 2;
   }

   public boolean isLookAhead(RegexAST ast) {
      return this.isSubMatcher() && this.getAstNode(ast).isLookAheadAssertion();
   }

   public boolean isLookBehind(RegexAST ast) {
      return this.isSubMatcher() && this.getAstNode(ast).isLookBehindAssertion();
   }

   public boolean isAtomicGroup() {
      return this.isSubMatcher() && !this.isLookAround();
   }

   public boolean isBackReference() {
      return this.kind == 3;
   }

   public boolean isEmptyMatch() {
      return this.kind == 4;
   }

   public CodePointSet getCharSet() {
      assert this.isCharacterClass();

      return this.charSet;
   }

   public int getSubtreeId() {
      assert this.isSubMatcher();

      return this.extraId;
   }

   public int getBackRefNumber() {
      assert this.isBackReference();

      return this.extraId;
   }

   public boolean isLookAround() {
      return this.getFlag((short)16);
   }

   public void setLookAround(boolean value) {
      this.setFlag((short)16, value);
   }

   public boolean isSubMatcherNegated() {
      return this.getFlag((short)32);
   }

   public void setSubMatcherNegated(boolean value) {
      this.setFlag((short)32, value);
   }

   public boolean isDeterministic() {
      return this.getFlag((short)64);
   }

   public void setDeterministic(boolean value) {
      this.setFlag((short)64, value);
   }

   public void initIsDeterministic(boolean forward, CompilationBuffer compilationBuffer) {
      this.setDeterministic(this.calcIsDeterministic(forward, compilationBuffer));
   }

   private boolean calcIsDeterministic(boolean forward, CompilationBuffer compilationBuffer) {
      PureNFATransition[] successors = this.getSuccessors(forward);
      if (successors.length <= 1) {
         return true;
      } else if (!successors[0].getTarget(forward).isCharacterClass()) {
         return false;
      } else {
         CodePointSetAccumulator acc = compilationBuffer.getCodePointSetAccumulator1();
         if (successors.length > 8) {
            acc.addSet(successors[0].getTarget(forward).getCharSet());
         }

         for (int i = 1; i < successors.length; i++) {
            PureNFAState target = successors[i].getTarget(forward);
            if (!target.isCharacterClass()) {
               return false;
            }

            if (successors.length <= 8) {
               for (int j = 0; j < i; j++) {
                  if (successors[j].getTarget(forward).getCharSet().intersects(target.getCharSet())) {
                     return false;
                  }
               }
            } else {
               if (target.getCharSet().intersects(acc.get())) {
                  return false;
               }

               acc.addSet(target.getCharSet());
            }
         }

         return true;
      }
   }

   protected PureNFATransition[] createTransitionsArray(int length) {
      return new PureNFATransition[length];
   }

   public void addLoopBackNext(PureNFATransition transition) {
      PureNFATransition[] newSuccessors = Arrays.copyOf(this.getSuccessors(), this.getSuccessors().length + 1);
      newSuccessors[newSuccessors.length - 1] = transition;
      this.setSuccessors(newSuccessors);
   }

   public void removeLoopBackNext() {
      this.setSuccessors(Arrays.copyOf(this.getSuccessors(), this.getSuccessors().length - 1));
   }

   @Override
   protected boolean hasTransitionToUnAnchoredFinalState(boolean forward) {
      for (PureNFATransition t : this.getSuccessors(forward)) {
         if (t.getTarget(forward).isUnAnchoredFinalState(forward)) {
            return true;
         }
      }

      return false;
   }

   private static byte getKind(Term t) {
      if (t.isCharacterClass()) {
         return 1;
      } else if (t.isMatchFound() || t.isPositionAssertion()) {
         return 0;
      } else if (t.isSubtreeRoot()) {
         return 2;
      } else if (t.isBackReference()) {
         return 3;
      } else if (!t.isGroup()) {
         throw CompilerDirectives.shouldNotReachHere();
      } else {
         return (byte)(t.getParent().isSubtreeRoot() ? 0 : 4);
      }
   }

   public boolean canMatchZeroWidth() {
      return this.isSubMatcher() || this.isBackReference() || this.isEmptyMatch();
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public String toString() {
      return this.getId() + " " + this.toStringIntl();
   }

   @CompilerDirectives.TruffleBoundary
   private String toStringIntl() {
      switch (this.getKind()) {
         case 0:
            if (this.isUnAnchoredInitialState()) {
               return "I";
            } else if (this.isAnchoredInitialState()) {
               return "^I";
            } else if (this.isUnAnchoredFinalState()) {
               return "F";
            } else {
               if (this.isAnchoredFinalState()) {
                  return "F$";
               }

               return "Dummy Initial State";
            }
         case 1:
            return this.charSet.toString();
         case 2:
            return "?=" + this.getSubtreeId();
         case 3:
            return "\\" + this.getBackRefNumber();
         case 4:
            return "EMPTY";
         default:
            throw CompilerDirectives.shouldNotReachHere();
      }
   }

   @CompilerDirectives.TruffleBoundary
   public JsonObject toJson(RegexAST ast) {
      return Json.obj(
         Json.prop("id", this.getId()),
         Json.prop("stateSet", Json.array(new int[]{this.getAstNodeId()})),
         Json.prop("sourceSections", RegexAST.sourceSectionsToJson(ast.getSourceSections(this.getAstNode(ast)))),
         Json.prop("matcherBuilder", (JsonConvertible)(this.isCharacterClass() ? Json.val(this.charSet.toString()) : Json.nullValue())),
         Json.prop("subMatcher", (JsonConvertible)(this.isSubMatcher() ? Json.val(this.getSubtreeId()) : Json.nullValue())),
         Json.prop("backReference", (JsonConvertible)(this.isBackReference() ? Json.val(this.getBackRefNumber()) : Json.nullValue())),
         Json.prop("anchoredFinalState", this.isAnchoredFinalState()),
         Json.prop("unAnchoredFinalState", this.isUnAnchoredFinalState()),
         Json.prop("transitions", Arrays.stream(this.getSuccessors()).map(x -> Json.val(x.getId())))
      );
   }
}
