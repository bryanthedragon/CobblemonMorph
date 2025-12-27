package com.oracle.truffle.regex.tregex.parser;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.regex.RegexFlags;
import com.oracle.truffle.regex.RegexSource;
import com.oracle.truffle.regex.RegexSyntaxException;
import java.util.ArrayList;
import java.util.List;

public class JSRegexValidator implements RegexValidator {
   private final RegexSource source;
   private final RegexFlags flags;
   private final RegexLexer lexer;

   public JSRegexValidator(RegexSource source) {
      this.source = source;
      this.flags = RegexFlags.parseFlags(source);
      this.lexer = new RegexLexer(source, this.flags);
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public void validate() throws RegexSyntaxException {
      this.parseDryRun();
   }

   private void parseDryRun() throws RegexSyntaxException {
      List<JSRegexValidator.RegexStackElem> syntaxStack = new ArrayList<>();
      JSRegexValidator.CurTermState curTermState = JSRegexValidator.CurTermState.Null;

      while (this.lexer.hasNext()) {
         Token token = this.lexer.next();
         switch (token.kind) {
            case caret:
            case dollar:
            case wordBoundary:
            case nonWordBoundary:
            case backReference:
            case charClass:
               curTermState = JSRegexValidator.CurTermState.Other;
               break;
            case quantifier:
               switch (curTermState) {
                  case Null:
                     throw this.syntaxError("Quantifier without target");
                  case LookAheadAssertion:
                     if (this.flags.isUnicode()) {
                        throw this.syntaxError("Quantifier on lookahead assertion");
                     }
                  case Other:
                  default:
                     curTermState = JSRegexValidator.CurTermState.Other;
                     continue;
                  case LookBehindAssertion:
                     throw this.syntaxError("Quantifier on lookbehind assertion");
               }
            case alternation:
               curTermState = JSRegexValidator.CurTermState.Null;
               break;
            case captureGroupBegin:
            case nonCaptureGroupBegin:
               syntaxStack.add(JSRegexValidator.RegexStackElem.Group);
               curTermState = JSRegexValidator.CurTermState.Null;
               break;
            case lookAheadAssertionBegin:
               syntaxStack.add(JSRegexValidator.RegexStackElem.LookAheadAssertion);
               curTermState = JSRegexValidator.CurTermState.Null;
               break;
            case lookBehindAssertionBegin:
               syntaxStack.add(JSRegexValidator.RegexStackElem.LookBehindAssertion);
               curTermState = JSRegexValidator.CurTermState.Null;
               break;
            case groupEnd:
               if (syntaxStack.isEmpty()) {
                  throw this.syntaxError("Unmatched ')'");
               }

               JSRegexValidator.RegexStackElem poppedElem = syntaxStack.remove(syntaxStack.size() - 1);
               switch (poppedElem) {
                  case LookAheadAssertion:
                     curTermState = JSRegexValidator.CurTermState.LookAheadAssertion;
                     break;
                  case LookBehindAssertion:
                     curTermState = JSRegexValidator.CurTermState.LookBehindAssertion;
                     break;
                  case Group:
                     curTermState = JSRegexValidator.CurTermState.Other;
               }
         }
      }

      if (!syntaxStack.isEmpty()) {
         throw this.syntaxError("Unterminated group");
      }
   }

   private RegexSyntaxException syntaxError(String msg) {
      return RegexSyntaxException.createPattern(this.source, msg, this.lexer.getLastTokenPosition());
   }

   private static enum CurTermState {
      Null,
      LookAheadAssertion,
      LookBehindAssertion,
      Other;
   }

   private static enum RegexStackElem {
      Group,
      LookAheadAssertion,
      LookBehindAssertion;
   }
}
