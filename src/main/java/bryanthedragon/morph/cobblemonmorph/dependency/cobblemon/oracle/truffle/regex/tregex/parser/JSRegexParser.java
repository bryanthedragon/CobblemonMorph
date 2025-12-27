package com.oracle.truffle.regex.tregex.parser;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.regex.AbstractRegexObject;
import com.oracle.truffle.regex.RegexFlags;
import com.oracle.truffle.regex.RegexLanguage;
import com.oracle.truffle.regex.RegexOptions;
import com.oracle.truffle.regex.RegexSource;
import com.oracle.truffle.regex.RegexSyntaxException;
import com.oracle.truffle.regex.tregex.buffer.CompilationBuffer;
import com.oracle.truffle.regex.tregex.parser.ast.Group;
import com.oracle.truffle.regex.tregex.parser.ast.RegexAST;
import com.oracle.truffle.regex.tregex.parser.ast.RegexASTRootNode;
import com.oracle.truffle.regex.tregex.parser.ast.RegexASTSubtreeRootNode;
import com.oracle.truffle.regex.tregex.parser.ast.Term;
import com.oracle.truffle.regex.tregex.string.Encodings;

public final class JSRegexParser implements RegexParser {
   private final RegexParserGlobals globals;
   private final RegexSource source;
   private final RegexFlags flags;
   private final RegexLexer lexer;
   private final RegexASTBuilder astBuilder;

   @CompilerDirectives.TruffleBoundary
   public JSRegexParser(RegexLanguage language, RegexSource source, CompilationBuffer compilationBuffer) throws RegexSyntaxException {
      this.globals = language.parserGlobals;
      this.source = source;
      this.flags = RegexFlags.parseFlags(source);
      this.lexer = new RegexLexer(source, this.flags);
      this.astBuilder = new RegexASTBuilder(language, source, this.flags, compilationBuffer);
   }

   public JSRegexParser(RegexLanguage language, RegexSource source, CompilationBuffer compilationBuffer, RegexSource originalSource) throws RegexSyntaxException {
      this.globals = language.parserGlobals;
      this.source = source;
      this.flags = RegexFlags.parseFlags(source);
      this.lexer = new RegexLexer(source, this.flags);
      this.astBuilder = new RegexASTBuilder(language, originalSource, this.flags, compilationBuffer);
   }

   public static Group parseRootLess(RegexLanguage language, String pattern) throws RegexSyntaxException {
      return new JSRegexParser(language, new RegexSource(pattern, "", RegexOptions.DEFAULT, null), new CompilationBuffer(Encodings.UTF_16_RAW))
         .parse(false)
         .getRoot();
   }

   public RegexFlags getFlags() {
      return this.flags;
   }

   @Override
   public AbstractRegexObject getNamedCaptureGroups() {
      return AbstractRegexObject.createNamedCaptureGroupMapInt(this.lexer.getNamedCaptureGroups());
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public RegexAST parse() throws RegexSyntaxException {
      return this.parse(true);
   }

   private RegexAST parse(boolean rootCapture) throws RegexSyntaxException {
      this.astBuilder.pushRootGroup(rootCapture);
      Token token = null;

      while (this.lexer.hasNext()) {
         Token.Kind prevKind = token == null ? null : token.kind;
         token = this.lexer.next();
         if (!this.source.getOptions().getFlavor().nestedCaptureGroupsKeptOnLoopReentry()
            && token.kind != Token.Kind.quantifier
            && this.astBuilder.getCurTerm() != null
            && this.astBuilder.getCurTerm().isBackReference()
            && this.astBuilder.getCurTerm().asBackReference().isNestedOrForwardReference()
            && !isNestedInLookBehindAssertion(this.astBuilder.getCurTerm())) {
            this.astBuilder.removeCurTerm();
         }

         switch (token.kind) {
            case caret:
               if (prevKind != Token.Kind.caret) {
                  if (this.flags.isMultiline()) {
                     this.astBuilder.addCopy(token, this.globals.multiLineCaretSubstitution);
                  } else {
                     this.astBuilder.addPositionAssertion(token);
                  }
               }
               break;
            case dollar:
               if (prevKind != Token.Kind.dollar) {
                  if (this.flags.isMultiline()) {
                     this.astBuilder.addCopy(token, this.globals.multiLineDollarSubsitution);
                  } else {
                     this.astBuilder.addPositionAssertion(token);
                  }
               }
               break;
            case wordBoundary:
               if (prevKind == Token.Kind.wordBoundary) {
                  break;
               }

               if (prevKind == Token.Kind.nonWordBoundary) {
                  this.astBuilder.replaceCurTermWithDeadNode();
               } else {
                  if (this.flags.isUnicode() && this.flags.isIgnoreCase()) {
                     this.astBuilder.addCopy(token, this.globals.unicodeIgnoreCaseWordBoundarySubstitution);
                     break;
                  }

                  this.astBuilder.addCopy(token, this.globals.wordBoundarySubstituion);
               }
               break;
            case nonWordBoundary:
               if (prevKind == Token.Kind.nonWordBoundary) {
                  break;
               }

               if (prevKind == Token.Kind.wordBoundary) {
                  this.astBuilder.replaceCurTermWithDeadNode();
               } else {
                  if (this.flags.isUnicode() && this.flags.isIgnoreCase()) {
                     this.astBuilder.addCopy(token, this.globals.unicodeIgnoreCaseNonWordBoundarySubsitution);
                     break;
                  }

                  this.astBuilder.addCopy(token, this.globals.nonWordBoundarySubstitution);
               }
               break;
            case backReference:
               this.astBuilder.addBackReference((Token.BackReference)token);
               break;
            case quantifier:
               if (this.astBuilder.getCurTerm() == null) {
                  throw this.syntaxError("Quantifier without target");
               }

               if (this.flags.isUnicode() && this.astBuilder.getCurTerm().isLookAheadAssertion()) {
                  throw this.syntaxError("Quantifier on lookahead assertion");
               }

               if (this.astBuilder.getCurTerm().isLookBehindAssertion()) {
                  throw this.syntaxError("Quantifier on lookbehind assertion");
               }

               this.astBuilder.addQuantifier((Token.Quantifier)token);
               break;
            case alternation:
               this.astBuilder.nextSequence();
               break;
            case captureGroupBegin:
               this.astBuilder.pushCaptureGroup(token);
               break;
            case nonCaptureGroupBegin:
               this.astBuilder.pushGroup(token);
               break;
            case lookAheadAssertionBegin:
               this.astBuilder.pushLookAheadAssertion(token, ((Token.LookAheadAssertionBegin)token).isNegated());
               break;
            case lookBehindAssertionBegin:
               this.astBuilder.pushLookBehindAssertion(token, ((Token.LookBehindAssertionBegin)token).isNegated());
               break;
            case groupEnd:
               if (this.astBuilder.getCurGroup().getParent() instanceof RegexASTRootNode) {
                  throw this.syntaxError("Unmatched ')'");
               }

               this.astBuilder.popGroup(token);
               break;
            case charClass:
               this.astBuilder.addCharClass((Token.CharacterClass)token);
         }
      }

      if (!this.astBuilder.curGroupIsRoot()) {
         throw this.syntaxError("Unterminated group");
      } else {
         return this.astBuilder.popRootGroup();
      }
   }

   private static boolean isNestedInLookBehindAssertion(Term t) {
      for (RegexASTSubtreeRootNode parent = t.getSubTreeParent(); parent.isLookAroundAssertion(); parent = parent.getParent().getSubTreeParent()) {
         if (parent.isLookBehindAssertion()) {
            return true;
         }
      }

      return false;
   }

   private RegexSyntaxException syntaxError(String msg) {
      return RegexSyntaxException.createPattern(this.source, msg, this.lexer.getLastTokenPosition());
   }
}
