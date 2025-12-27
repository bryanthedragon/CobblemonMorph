package com.oracle.truffle.regex.tregex.nfa;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.regex.RegexLanguage;
import com.oracle.truffle.regex.tregex.automaton.AbstractTransition;
import com.oracle.truffle.regex.tregex.parser.ast.GroupBoundaries;
import com.oracle.truffle.regex.tregex.parser.ast.Term;
import com.oracle.truffle.regex.tregex.util.json.Json;
import com.oracle.truffle.regex.tregex.util.json.JsonConvertible;
import com.oracle.truffle.regex.tregex.util.json.JsonValue;

public class ASTTransition implements AbstractTransition<Term, ASTTransition>, JsonConvertible {
   private Term target;
   private GroupBoundaries groupBoundaries;

   public ASTTransition(RegexLanguage language) {
      this.groupBoundaries = GroupBoundaries.getEmptyInstance(language);
   }

   public ASTTransition(RegexLanguage language, Term target) {
      this.target = target;
      this.groupBoundaries = GroupBoundaries.getEmptyInstance(language);
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public int getId() {
      throw new UnsupportedOperationException();
   }

   @CompilerDirectives.TruffleBoundary
   public Term getSource() {
      throw new UnsupportedOperationException();
   }

   public Term getTarget() {
      return this.target;
   }

   public void setTarget(Term target) {
      this.target = target;
   }

   public GroupBoundaries getGroupBoundaries() {
      return this.groupBoundaries;
   }

   public void setGroupBoundaries(GroupBoundaries groupBoundaries) {
      this.groupBoundaries = groupBoundaries;
   }

   @Override
   public int hashCode() {
      return this.target.hashCode();
   }

   @Override
   public boolean equals(Object obj) {
      return obj instanceof ASTTransition && this.target == ((ASTTransition)obj).target;
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public JsonValue toJson() {
      return Json.obj(Json.prop("target", this.target.getId()), Json.prop("groupBoundaries", this.groupBoundaries));
   }
}
