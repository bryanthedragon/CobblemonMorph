package com.oracle.truffle.regex.tregex.parser.ast;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.regex.tregex.buffer.CompilationBuffer;
import com.oracle.truffle.regex.tregex.util.json.JsonValue;

public class RegexASTRootNode extends RegexASTSubtreeRootNode {
   RegexASTRootNode() {
      this.setId(0);
   }

   private RegexASTRootNode(RegexASTRootNode copy, RegexAST ast) {
      super(copy, ast);
   }

   private RegexASTRootNode(RegexASTRootNode copy, RegexAST ast, CompilationBuffer compilationBuffer) {
      super(copy, ast, compilationBuffer);
   }

   @Override
   public RegexASTSubtreeRootNode copy(RegexAST ast) {
      return new RegexASTRootNode(this, ast);
   }

   public RegexASTSubtreeRootNode copyRecursive(RegexAST ast, CompilationBuffer compilationBuffer) {
      return new RegexASTRootNode(this, ast, compilationBuffer);
   }

   @Override
   public String getPrefix() {
      return "ROOT";
   }

   @Override
   public boolean equalsSemantic(RegexASTNode obj) {
      return this == obj || obj instanceof RegexASTRootNode && ((RegexASTRootNode)obj).getGroup().equalsSemantic(this.getGroup());
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public String toString() {
      return this.getGroup().toString();
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public JsonValue toJson() {
      return this.getGroup().toJson();
   }
}
