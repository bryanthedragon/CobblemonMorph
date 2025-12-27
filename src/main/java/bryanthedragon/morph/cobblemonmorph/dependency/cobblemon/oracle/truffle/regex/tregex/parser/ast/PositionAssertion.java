package com.oracle.truffle.regex.tregex.parser.ast;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.regex.tregex.buffer.CompilationBuffer;
import com.oracle.truffle.regex.tregex.util.json.JsonValue;

public class PositionAssertion extends Term {
   public final PositionAssertion.Type type;
   private RegexASTNode next;

   PositionAssertion(PositionAssertion.Type type) {
      this.type = type;
   }

   private PositionAssertion(PositionAssertion copy) {
      super(copy);
      this.type = copy.type;
   }

   public PositionAssertion copy(RegexAST ast) {
      return ast.register(new PositionAssertion(this));
   }

   @Override
   public Term copyRecursive(RegexAST ast, CompilationBuffer compilationBuffer) {
      return this.copy(ast);
   }

   public RegexASTNode getNext() {
      return this.next;
   }

   public void setNext(RegexASTNode next) {
      this.next = next;
   }

   @Override
   public boolean isCaret() {
      return this.type == PositionAssertion.Type.CARET;
   }

   @Override
   public boolean isDollar() {
      return this.type == PositionAssertion.Type.DOLLAR;
   }

   @Override
   public boolean equalsSemantic(RegexASTNode obj) {
      return obj instanceof PositionAssertion && ((PositionAssertion)obj).type == this.type;
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public String toString() {
      switch (this.type) {
         case CARET:
            return "^";
         case DOLLAR:
            return "$";
         default:
            throw CompilerDirectives.shouldNotReachHere();
      }
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public JsonValue toJson() {
      return this.toJson("PositionAssertion" + this.type);
   }

   public static enum Type {
      CARET,
      DOLLAR;
   }
}
