package com.oracle.truffle.regex.tregex.parser.ast;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.regex.tregex.buffer.CompilationBuffer;
import com.oracle.truffle.regex.tregex.util.json.JsonValue;

public class LookAheadAssertion extends LookAroundAssertion {
   LookAheadAssertion(boolean negated) {
      super(negated);
   }

   private LookAheadAssertion(LookAheadAssertion copy, RegexAST ast) {
      super(copy, ast);
   }

   private LookAheadAssertion(LookAheadAssertion copy, RegexAST ast, CompilationBuffer compilationBuffer) {
      super(copy, ast, compilationBuffer);
   }

   public LookAheadAssertion copy(RegexAST ast) {
      return ast.register(new LookAheadAssertion(this, ast));
   }

   public LookAheadAssertion copyRecursive(RegexAST ast, CompilationBuffer compilationBuffer) {
      return ast.register(new LookAheadAssertion(this, ast, compilationBuffer));
   }

   @Override
   public String getPrefix() {
      return this.isNegated() ? "?!" : "?=";
   }

   @Override
   public boolean equalsSemantic(RegexASTNode obj) {
      return this == obj || obj.isLookAheadAssertion() && this.groupEqualsSemantic(obj.asLookAheadAssertion());
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public JsonValue toJson() {
      return this.toJson(this.isNegated() ? "NegativeLookAheadAssertion" : "LookAheadAssertion");
   }
}
