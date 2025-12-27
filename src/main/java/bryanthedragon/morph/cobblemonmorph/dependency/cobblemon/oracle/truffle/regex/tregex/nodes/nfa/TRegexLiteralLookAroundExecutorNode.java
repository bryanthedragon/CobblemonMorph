package com.oracle.truffle.regex.tregex.nodes.nfa;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.regex.charset.CharMatchers;
import com.oracle.truffle.regex.tregex.buffer.CompilationBuffer;
import com.oracle.truffle.regex.tregex.matchers.CharMatcher;
import com.oracle.truffle.regex.tregex.nodes.TRegexExecutorLocals;
import com.oracle.truffle.regex.tregex.parser.ast.LookAroundAssertion;
import com.oracle.truffle.regex.tregex.parser.ast.RegexAST;

public final class TRegexLiteralLookAroundExecutorNode extends TRegexBacktrackerSubExecutorNode {
   private final boolean forward;
   private final boolean negated;
   @CompilerDirectives.CompilationFinal(dimensions = 1)
   private CharMatcher[] matchers;

   private TRegexLiteralLookAroundExecutorNode(RegexAST ast, int numberOfTransitions, boolean forward, boolean negated, CharMatcher[] matchers) {
      super(ast, numberOfTransitions, null);
      this.forward = forward;
      this.negated = negated;
      this.matchers = matchers;
   }

   private TRegexLiteralLookAroundExecutorNode(TRegexLiteralLookAroundExecutorNode copy) {
      super(copy);
      this.forward = copy.forward;
      this.negated = copy.negated;
      this.matchers = copy.matchers;
   }

   public static TRegexLiteralLookAroundExecutorNode create(RegexAST ast, LookAroundAssertion lookAround, CompilationBuffer compilationBuffer) {
      assert lookAround.isLiteral();

      boolean forward = lookAround.isLookAheadAssertion();
      boolean negated = lookAround.isNegated();
      CharMatcher[] matchers = new CharMatcher[lookAround.getLiteralLength()];

      for (int i = 0; i < matchers.length; i++) {
         CharMatcher matcher = CharMatchers.createMatcher(lookAround.getGroup().getFirstAlternative().get(i).asCharacterClass().getCharSet(), compilationBuffer);
         matchers[forward ? i : matchers.length - (i + 1)] = matcher;
      }

      return new TRegexLiteralLookAroundExecutorNode(ast, matchers.length, forward, negated, matchers);
   }

   public TRegexLiteralLookAroundExecutorNode shallowCopy() {
      return new TRegexLiteralLookAroundExecutorNode(this);
   }

   @Override
   public String getName() {
      return "la";
   }

   @Override
   public boolean isForward() {
      return this.forward;
   }

   @Override
   public boolean writesCaptureGroups() {
      return false;
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public TRegexExecutorLocals createLocals(Object input, int fromIndex, int index, int maxIndex) {
      throw new UnsupportedOperationException();
   }

   @ExplodeLoop
   @Override
   public Object execute(VirtualFrame frame, TRegexExecutorLocals abstractLocals, TruffleString.CodeRange codeRange, boolean tString) {
      TRegexBacktrackingNFAExecutorLocals locals = (TRegexBacktrackingNFAExecutorLocals)abstractLocals;

      for (int i = 0; i < this.matchers.length; i++) {
         if (!this.inputHasNext(locals) || !this.matchers[i].match(this.inputReadAndDecode(locals))) {
            return this.negated;
         }

         this.inputAdvance(locals);
      }

      return !this.negated;
   }
}
