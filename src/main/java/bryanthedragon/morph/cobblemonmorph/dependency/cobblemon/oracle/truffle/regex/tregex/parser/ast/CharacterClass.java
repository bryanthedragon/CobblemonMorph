package com.oracle.truffle.regex.tregex.parser.ast;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.regex.charset.CodePointSet;
import com.oracle.truffle.regex.tregex.automaton.StateSet;
import com.oracle.truffle.regex.tregex.buffer.CompilationBuffer;
import com.oracle.truffle.regex.tregex.string.AbstractStringBuffer;
import com.oracle.truffle.regex.tregex.util.json.Json;
import com.oracle.truffle.regex.tregex.util.json.JsonObject;
import com.oracle.truffle.regex.tregex.util.json.JsonValue;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

public class CharacterClass extends QuantifiableTerm {
   private CodePointSet charSet;
   private StateSet<GlobalSubTreeIndex, LookBehindAssertion> lookBehindEntries;

   CharacterClass(CodePointSet charSet) {
      this.charSet = charSet;
   }

   private CharacterClass(CharacterClass copy, CodePointSet charSet) {
      super(copy);
      this.charSet = charSet;
   }

   public CharacterClass copy(RegexAST ast) {
      return ast.register(new CharacterClass(this, this.charSet));
   }

   public CharacterClass copyRecursive(RegexAST ast, CompilationBuffer compilationBuffer) {
      return ast.register(new CharacterClass(this, ast.getEncoding().getFullSet().createIntersection(this.charSet, compilationBuffer)));
   }

   public Sequence getParent() {
      return (Sequence)super.getParent();
   }

   public CodePointSet getCharSet() {
      return this.charSet;
   }

   public void setCharSet(CodePointSet charSet) {
      this.charSet = charSet;
   }

   public boolean wasSingleChar() {
      return this.isFlagSet(262144);
   }

   public void setWasSingleChar() {
      this.setWasSingleChar(true);
   }

   public void setWasSingleChar(boolean value) {
      this.setFlag(262144, value);
   }

   @Override
   public boolean isUnrollingCandidate() {
      return this.hasQuantifier() && this.getQuantifier().isWithinThreshold(20);
   }

   public void addLookBehindEntry(RegexAST ast, LookBehindAssertion lookBehindEntry) {
      if (this.lookBehindEntries == null) {
         this.lookBehindEntries = StateSet.create(ast.getSubtrees());
      }

      this.lookBehindEntries.add(lookBehindEntry);
   }

   public boolean hasLookBehindEntries() {
      return this.lookBehindEntries != null;
   }

   public Set<LookBehindAssertion> getLookBehindEntries() {
      return (Set<LookBehindAssertion>)(this.lookBehindEntries == null ? Collections.emptySet() : this.lookBehindEntries);
   }

   public void extractSingleChar(AbstractStringBuffer literal, AbstractStringBuffer mask) {
      if (this.charSet.matchesSingleChar()) {
         literal.append(this.charSet.getMin());

         assert mask.getEncoding().getEncodedSize(0) == 1;

         for (int i = 0; i < mask.getEncoding().getEncodedSize(this.charSet.getMin()); i++) {
            mask.append(0);
         }
      } else {
         assert this.charSet.matches2CharsWith1BitDifference();

         int c1 = this.charSet.getMin();
         int c2 = this.charSet.getMax();
         literal.appendOR(c1, c2);
         mask.appendXOR(c1, c2);
      }
   }

   @Override
   public boolean equalsSemantic(RegexASTNode obj, boolean ignoreQuantifier) {
      return obj instanceof CharacterClass
         && ((CharacterClass)obj).getCharSet().equals(this.charSet)
         && (ignoreQuantifier || this.quantifierEquals((CharacterClass)obj));
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public String toString() {
      return this.charSet.toString() + this.quantifierToString();
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public JsonValue toJson() {
      JsonObject json = this.toJson("CharacterClass").append(Json.prop("charSet", this.charSet));
      if (this.lookBehindEntries != null) {
         json.append(Json.prop("lookBehindEntries", this.lookBehindEntries.stream().map(RegexASTNode::astNodeId).collect(Collectors.toList())));
      }

      return json;
   }
}
