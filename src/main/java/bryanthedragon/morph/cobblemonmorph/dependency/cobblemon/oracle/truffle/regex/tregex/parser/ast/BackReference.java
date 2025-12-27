package com.oracle.truffle.regex.tregex.parser.ast;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.regex.tregex.buffer.CompilationBuffer;
import com.oracle.truffle.regex.tregex.util.json.Json;
import com.oracle.truffle.regex.tregex.util.json.JsonValue;

public class BackReference extends QuantifiableTerm {
   private final int groupNr;

   BackReference(int referencedGroupNr) {
      this.groupNr = referencedGroupNr;
   }

   private BackReference(BackReference copy) {
      super(copy);
      this.groupNr = copy.groupNr;
   }

   public BackReference copy(RegexAST ast) {
      return ast.register(new BackReference(this));
   }

   public BackReference copyRecursive(RegexAST ast, CompilationBuffer compilationBuffer) {
      return this.copy(ast);
   }

   public int getGroupNr() {
      return this.groupNr;
   }

   public boolean isNestedBackReference() {
      return this.isFlagSet(64);
   }

   public void setNestedBackReference() {
      this.setFlag(64, true);
   }

   public boolean isForwardReference() {
      return this.isFlagSet(128);
   }

   public void setForwardReference() {
      this.setFlag(128, true);
   }

   public boolean isNestedOrForwardReference() {
      return this.isFlagSet(192);
   }

   @Override
   public boolean isUnrollingCandidate() {
      return this.hasQuantifier() && this.getQuantifier().isUnrollTrivial();
   }

   @Override
   public boolean equalsSemantic(RegexASTNode obj, boolean ignoreQuantifier) {
      return obj instanceof BackReference && ((BackReference)obj).groupNr == this.groupNr && (ignoreQuantifier || this.quantifierEquals((BackReference)obj));
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public String toString() {
      return "\\" + this.groupNr + this.quantifierToString();
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public JsonValue toJson() {
      return this.toJson("BackReference").append(Json.prop("groupNr", this.groupNr));
   }
}
