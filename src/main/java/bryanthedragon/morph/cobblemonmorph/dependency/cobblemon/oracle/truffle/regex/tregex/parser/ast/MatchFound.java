package com.oracle.truffle.regex.tregex.parser.ast;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.regex.tregex.buffer.CompilationBuffer;
import com.oracle.truffle.regex.tregex.util.json.JsonValue;

public class MatchFound extends Term {
   private RegexASTNode next;

   public MatchFound copy(RegexAST ast) {
      throw CompilerDirectives.shouldNotReachHere();
   }

   public MatchFound copyRecursive(RegexAST ast, CompilationBuffer compilationBuffer) {
      throw CompilerDirectives.shouldNotReachHere();
   }

   public RegexASTNode getNext() {
      return this.next;
   }

   public void setNext(RegexASTNode next) {
      this.next = next;
   }

   @Override
   public boolean equalsSemantic(RegexASTNode obj) {
      return obj instanceof MatchFound;
   }

   @Override
   public String toString() {
      return "::";
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public JsonValue toJson() {
      return this.toJson("MatchFound");
   }
}
