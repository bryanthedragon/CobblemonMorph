package com.oracle.truffle.regex.tregex.parser.ast;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.regex.tregex.buffer.CompilationBuffer;
import com.oracle.truffle.regex.tregex.util.json.Json;
import com.oracle.truffle.regex.tregex.util.json.JsonValue;

public class SubexpressionCall extends QuantifiableTerm {
   private final int groupNr;

   SubexpressionCall(int groupNr) {
      this.groupNr = groupNr;
   }

   private SubexpressionCall(SubexpressionCall copy) {
      super(copy);
      this.groupNr = copy.groupNr;
   }

   public SubexpressionCall copy(RegexAST ast) {
      return ast.register(new SubexpressionCall(this));
   }

   public SubexpressionCall copyRecursive(RegexAST ast, CompilationBuffer compilationBuffer) {
      return this.copy(ast);
   }

   public Sequence getParent() {
      return (Sequence)super.getParent();
   }

   public int getGroupNr() {
      return this.groupNr;
   }

   @Override
   public boolean isUnrollingCandidate() {
      return this.hasQuantifier() && this.getQuantifier().isWithinThreshold(6);
   }

   @Override
   public boolean equalsSemantic(RegexASTNode obj, boolean ignoreQuantifier) {
      return obj instanceof SubexpressionCall
         && ((SubexpressionCall)obj).groupNr == this.groupNr
         && (ignoreQuantifier || this.quantifierEquals((BackReference)obj));
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public String toString() {
      return "\\g<" + this.groupNr + ">" + this.quantifierToString();
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public JsonValue toJson() {
      return this.toJson("SubexpressionCall").append(Json.prop("groupNr", this.groupNr));
   }
}
