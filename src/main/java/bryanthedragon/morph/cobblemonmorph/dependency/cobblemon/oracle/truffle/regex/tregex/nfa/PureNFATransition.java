package com.oracle.truffle.regex.tregex.nfa;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.regex.tregex.automaton.AbstractTransition;
import com.oracle.truffle.regex.tregex.parser.ast.GroupBoundaries;
import com.oracle.truffle.regex.tregex.parser.ast.RegexAST;
import com.oracle.truffle.regex.tregex.util.json.Json;
import com.oracle.truffle.regex.tregex.util.json.JsonValue;

public final class PureNFATransition implements AbstractTransition<PureNFAState, PureNFATransition> {
   private final int id;
   private final PureNFAState source;
   private final PureNFAState target;
   private final GroupBoundaries groupBoundaries;
   private final boolean caretGuard;
   private final boolean dollarGuard;
   @CompilerDirectives.CompilationFinal(dimensions = 1)
   private final QuantifierGuard[] quantifierGuards;

   public PureNFATransition(
      int id,
      PureNFAState source,
      PureNFAState target,
      GroupBoundaries groupBoundaries,
      boolean caretGuard,
      boolean dollarGuard,
      QuantifierGuard[] quantifierGuards
   ) {
      this.id = id;
      this.source = source;
      this.target = target;
      this.caretGuard = caretGuard;
      this.groupBoundaries = groupBoundaries;
      this.dollarGuard = dollarGuard;
      this.quantifierGuards = quantifierGuards;
   }

   @Override
   public int getId() {
      return this.id;
   }

   public PureNFAState getSource() {
      return this.source;
   }

   public PureNFAState getTarget() {
      return this.target;
   }

   public GroupBoundaries getGroupBoundaries() {
      return this.groupBoundaries;
   }

   public boolean hasCaretGuard() {
      return this.caretGuard;
   }

   public boolean hasDollarGuard() {
      return this.dollarGuard;
   }

   public QuantifierGuard[] getQuantifierGuards() {
      return this.quantifierGuards;
   }

   public boolean hasAnyGuards() {
      return this.caretGuard || this.dollarGuard || this.quantifierGuards.length > 0;
   }

   @CompilerDirectives.TruffleBoundary
   public JsonValue toJson(RegexAST ast) {
      return Json.obj(
         Json.prop("id", this.id),
         Json.prop("source", this.source.getId()),
         Json.prop("target", this.target.getId()),
         Json.prop("groupBoundaries", this.groupBoundaries),
         Json.prop("sourceSections", this.groupBoundaries.indexUpdateSourceSectionsToJson(ast))
      );
   }
}
