package com.oracle.truffle.regex.tregex.parser.flavors;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.relocations.ibm.icu.lang.UCharacter;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.regex.AbstractRegexObject;
import com.oracle.truffle.regex.RegexLanguage;
import com.oracle.truffle.regex.RegexSource;
import com.oracle.truffle.regex.RegexSyntaxException;
import com.oracle.truffle.regex.UnsupportedRegexException;
import com.oracle.truffle.regex.chardata.UnicodeCharacterAliases;
import com.oracle.truffle.regex.charset.CodePointSet;
import com.oracle.truffle.regex.charset.CodePointSetAccumulator;
import com.oracle.truffle.regex.charset.Range;
import com.oracle.truffle.regex.charset.UnicodeProperties;
import com.oracle.truffle.regex.errors.PyErrorMessages;
import com.oracle.truffle.regex.tregex.buffer.CompilationBuffer;
import com.oracle.truffle.regex.tregex.parser.CaseFoldTable;
import com.oracle.truffle.regex.tregex.parser.JSRegexParser;
import com.oracle.truffle.regex.tregex.parser.RegexParser;
import com.oracle.truffle.regex.tregex.parser.RegexValidator;
import com.oracle.truffle.regex.tregex.parser.ast.RegexAST;
import com.oracle.truffle.regex.tregex.string.Encodings;
import com.oracle.truffle.regex.util.TBitSet;
import java.math.BigInteger;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class PythonRegexParser implements RegexValidator, RegexParser {
   private static final TBitSet SYNTAX_CHARACTERS = TBitSet.valueOf(36, 40, 41, 42, 43, 46, 63, 91, 92, 93, 94, 123, 124, 125);
   private static final TBitSet CHAR_CLASS_SYNTAX_CHARACTERS = TBitSet.valueOf(45, 92, 93, 94);
   private static final Map<Character, String> UNICODE_CHAR_CLASS_REPLACEMENTS = new HashMap<>();
   private static final Map<Character, CodePointSet> UNICODE_CHAR_CLASS_SETS = new HashMap<>();
   public static final Pattern WORD_CHARS_PATTERN = Pattern.compile("\\\\[wW]");
   public static final String WORD_BOUNDARY = "(?:(?:^|(?<=\\W))(?=\\w)|(?<=\\w)(?:(?=\\W)|$))";
   public static final String WORD_NON_BOUNDARY = "(?:^(?=\\W)|(?<=\\W)$|(?<=\\W)(?=\\W)|(?<=\\w)(?=\\w))";
   private static final String ASCII_WHITESPACE = "\\x09-\\x0d\\x20";
   private static final String ASCII_NON_WHITESPACE = "\\x00-\\x08\\x0e-\\x1f\\x21-\\u{10ffff}";
   private static final TBitSet WHITESPACE = TBitSet.valueOf(9, 10, 11, 12, 13, 32);
   private static final CodePointSet XID_START = UnicodeProperties.getProperty("XID_Start").union(CodePointSet.create(95));
   private static final CodePointSet XID_CONTINUE = UnicodeProperties.getProperty("XID_Continue");
   private final RegexSource inSource;
   private final String inPattern;
   private final String inFlags;
   private final PythonREMode mode;
   private boolean silent;
   private int position;
   private final StringBuilder outPattern;
   private PythonFlags globalFlags;
   private final Deque<PythonFlags> flagsStack;
   private final Deque<PythonRegexParser.Lookbehind> lookbehindStack;
   private final Deque<PythonRegexParser.Group> groupStack;
   private Map<String, Integer> namedCaptureGroups;
   private int groups;
   private PythonRegexParser.TermCategory lastTerm;
   private final CodePointSetAccumulator curCharClass = new CodePointSetAccumulator();
   private final CodePointSetAccumulator charClassCaseFoldTmp = new CodePointSetAccumulator();
   private final RegexLanguage language;
   private final CompilationBuffer compilationBuffer;

   @CompilerDirectives.TruffleBoundary
   private PythonRegexParser(RegexLanguage language, RegexSource source, CompilationBuffer compilationBuffer, PythonREMode mode) throws RegexSyntaxException {
      this.language = language;
      this.inSource = source;
      this.compilationBuffer = compilationBuffer;
      this.inPattern = source.getPattern();
      this.inFlags = source.getFlags();
      this.mode = mode == PythonREMode.None ? PythonREMode.fromEncoding(source.getEncoding()) : mode;
      this.position = 0;
      this.outPattern = new StringBuilder(this.inPattern.length());
      this.globalFlags = new PythonFlags(this.inFlags);
      this.flagsStack = new LinkedList<>();
      this.lookbehindStack = new ArrayDeque<>();
      this.groupStack = new ArrayDeque<>();
      this.namedCaptureGroups = null;
      this.groups = 0;
      this.lastTerm = PythonRegexParser.TermCategory.None;
   }

   public static RegexValidator createValidator(RegexSource source, PythonREMode mode) throws RegexSyntaxException {
      return new PythonRegexParser(null, source, null, mode);
   }

   public static RegexParser createParser(RegexLanguage language, RegexSource source, CompilationBuffer compilationBuffer, PythonREMode mode) throws RegexSyntaxException {
      return new PythonRegexParser(language, source, compilationBuffer, mode);
   }

   @Override
   public AbstractRegexObject getNamedCaptureGroups() {
      return AbstractRegexObject.createNamedCaptureGroupMapInt(this.namedCaptureGroups);
   }

   @Override
   public AbstractRegexObject getFlags() {
      return this.getGlobalFlags();
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public void validate() throws RegexSyntaxException {
      this.silent = true;
      this.run();
   }

   private RegexSource toECMAScriptRegex() throws RegexSyntaxException, UnsupportedRegexException {
      this.silent = false;
      this.run();
      if (this.inSource.getOptions().getPythonMethod() == PythonMethod.fullmatch) {
         this.outPattern.insert(0, "(?:");
         this.outPattern.append(")$");
      }

      boolean sticky = this.inSource.getOptions().getPythonMethod() == PythonMethod.match
         || this.inSource.getOptions().getPythonMethod() == PythonMethod.fullmatch;
      String outFlags = sticky ? "suy" : "su";
      return new RegexSource(this.outPattern.toString(), outFlags, this.inSource.getOptions(), this.inSource.getSource());
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public RegexAST parse() throws RegexSyntaxException, UnsupportedRegexException {
      RegexSource ecmascriptSource = this.toECMAScriptRegex();
      JSRegexParser ecmascriptParser = new JSRegexParser(this.language, ecmascriptSource, this.compilationBuffer, this.inSource);
      return ecmascriptParser.parse();
   }

   private PythonFlags getLocalFlags() {
      return this.flagsStack.isEmpty() ? this.globalFlags : this.flagsStack.peek();
   }

   private PythonFlags getGlobalFlags() {
      return this.globalFlags;
   }

   private int curChar() {
      switch (this.mode) {
         case Str:
            return this.inPattern.codePointAt(this.position);
         case Bytes:
            return this.inPattern.charAt(this.position);
         default:
            throw CompilerDirectives.shouldNotReachHere();
      }
   }

   private int consumeChar() {
      int c = this.curChar();
      this.advance();
      return c;
   }

   private String getMany(Predicate<Integer> pred) {
      StringBuilder out = new StringBuilder();

      while (!this.atEnd() && pred.test(this.curChar())) {
         out.appendCodePoint(this.consumeChar());
      }

      return out.toString();
   }

   private String getUpTo(int count, Predicate<Integer> pred) {
      StringBuilder out = new StringBuilder();

      for (int found = 0; found < count && !this.atEnd() && pred.test(this.curChar()); found++) {
         out.appendCodePoint(this.consumeChar());
      }

      return out.toString();
   }

   private String getUntil(char terminator, String name) {
      StringBuilder out = new StringBuilder();

      while (!this.atEnd() && this.curChar() != terminator) {
         out.appendCodePoint(this.consumeChar());
      }

      if (out.length() == 0) {
         throw this.syntaxErrorHere(PyErrorMessages.missing(name));
      } else if (this.atEnd()) {
         throw this.syntaxErrorAtRel(PyErrorMessages.missingUnterminatedName(terminator), out.length());
      } else if (this.curChar() == 125) {
         this.advance();
         return out.toString();
      } else {
         throw this.syntaxErrorHere(PyErrorMessages.missing(Character.toString(terminator)));
      }
   }

   private void advance() {
      this.advance(1);
   }

   private void retreat() {
      this.advance(-1);
   }

   private void retreat(int len) {
      this.advance(-len);
   }

   private void advance(int len) {
      switch (this.mode) {
         case Str:
            this.position = this.inPattern.offsetByCodePoints(this.position, len);
            break;
         case Bytes:
            this.position += len;
      }
   }

   private boolean match(String match) {
      if (this.inPattern.regionMatches(this.position, match, 0, match.length())) {
         this.position = this.position + match.length();
         return true;
      } else {
         return false;
      }
   }

   private boolean atEnd() {
      return this.position >= this.inPattern.length();
   }

   private void mustHaveMore() {
      if (this.atEnd()) {
         throw this.syntaxErrorHere("unexpected end of pattern");
      }
   }

   private void bailOut(String reason) throws UnsupportedRegexException {
      if (!this.silent) {
         throw new UnsupportedRegexException(reason);
      }
   }

   private void emitSnippet(String snippet) {
      if (!this.silent) {
         this.outPattern.append(snippet);
      }
   }

   private void emitRawCodepoint(int codepoint) {
      if (!this.silent) {
         this.outPattern.appendCodePoint(codepoint);
      }
   }

   private void emitCharNoCasing(int codepoint, boolean inCharClass) {
      if (!this.silent) {
         TBitSet syntaxChars = inCharClass ? CHAR_CLASS_SYNTAX_CHARACTERS : SYNTAX_CHARACTERS;
         if (syntaxChars.get(codepoint)) {
            this.emitSnippet("\\");
         }

         this.emitRawCodepoint(codepoint);
      }
   }

   private void emitChar(int codepoint) {
      this.emitChar(codepoint, false);
   }

   private void emitChar(int codepoint, boolean inCharClass) {
      if (!this.silent) {
         if (this.getLocalFlags().isIgnoreCase()) {
            this.curCharClass.clear();
            this.curCharClass.addRange(codepoint, codepoint);
            this.caseFold();
            if (this.curCharClass.matchesSingleChar()) {
               this.emitCharNoCasing(codepoint, inCharClass);
            } else if (inCharClass) {
               this.emitCharSetNoCasing();
            } else {
               this.emitSnippet("[");
               this.emitCharSetNoCasing();
               this.emitSnippet("]");
            }
         } else {
            this.emitCharNoCasing(codepoint, inCharClass);
         }
      }
   }

   private void emitString(String string) {
      if (!this.silent) {
         int i = 0;

         while (i < string.length()) {
            this.emitChar(string.codePointAt(i));
            i = string.offsetByCodePoints(i, 1);
         }
      }
   }

   private void emitCharSet() {
      if (!this.silent) {
         this.caseFold();
         this.emitCharSetNoCasing();
      }
   }

   private void emitCharSetNoCasing() {
      this.emitCharSetNoCasing(this.curCharClass);
   }

   private void emitCharSetNoCasing(Iterable<Range> charSet) {
      if (!this.silent) {
         for (Range r : charSet) {
            if (r.isSingle()) {
               this.emitCharNoCasing(r.lo, true);
            } else {
               this.emitCharNoCasing(r.lo, true);
               this.emitSnippet("-");
               this.emitCharNoCasing(r.hi, true);
            }
         }
      }
   }

   private void caseFold() {
      if (this.getLocalFlags().isIgnoreCase()) {
         if (this.getLocalFlags().isLocale()) {
            this.bailOut("locale-specific case folding is not supported");
         }

         CaseFoldTable.CaseFoldingAlgorithm caseFolding = this.getLocalFlags().isUnicode(this.mode)
            ? CaseFoldTable.CaseFoldingAlgorithm.PythonUnicode
            : CaseFoldTable.CaseFoldingAlgorithm.PythonAscii;
         CaseFoldTable.applyCaseFold(this.curCharClass, this.charClassCaseFoldTmp, caseFolding);
      }
   }

   private RegexSyntaxException syntaxErrorAtRel(String message, int offset) {
      int atPosition = this.mode == PythonREMode.Str ? this.inPattern.offsetByCodePoints(this.position, -offset) : this.position - offset;
      return this.syntaxErrorAtAbs(message, atPosition);
   }

   private RegexSyntaxException syntaxErrorAtAbs(String message, int atPosition) {
      int reportedPosition = this.mode == PythonREMode.Str ? this.inPattern.codePointCount(0, atPosition) : atPosition;
      return this.syntaxError(message, reportedPosition);
   }

   private RegexSyntaxException syntaxErrorHere(String message) {
      return this.syntaxErrorAtAbs(message, this.position);
   }

   private RegexSyntaxException syntaxError(String message, int atPosition) {
      return RegexSyntaxException.createPattern(this.inSource, message, atPosition);
   }

   private static boolean isAsciiLetter(int c) {
      return c >= 97 && c <= 122 || c >= 65 && c <= 90;
   }

   private static boolean isOctDigit(int c) {
      return c >= 48 && c <= 55;
   }

   private static boolean isDecDigit(int c) {
      return c >= 48 && c <= 57;
   }

   private static boolean isHexDigit(int c) {
      return c >= 48 && c <= 57 || c >= 97 && c <= 102 || c >= 65 && c <= 70;
   }

   private void run() {
      PythonFlags startFlags;
      do {
         startFlags = this.globalFlags;
         this.disjunction();
      } while (!this.globalFlags.equals(startFlags));

      this.globalFlags = this.globalFlags.fixFlags(this.inSource, this.mode);
      if (!this.atEnd()) {
         assert this.curChar() == 41;

         throw this.syntaxErrorAtRel("unbalanced parenthesis", 0);
      }
   }

   private void disjunction() {
      while (true) {
         this.alternative();
         if (!this.match("|")) {
            return;
         }

         this.emitSnippet("|");
      }
   }

   private void alternative() {
      while (!this.atEnd() && this.curChar() != 124 && this.curChar() != 41) {
         this.term();
      }
   }

   private void term() {
      int ch = this.consumeChar();
      if (this.getLocalFlags().isVerbose()) {
         if (WHITESPACE.get(ch)) {
            return;
         }

         if (ch == 35) {
            this.comment();
            return;
         }
      }

      switch (ch) {
         case 36:
            if (this.getLocalFlags().isMultiLine()) {
               this.emitSnippet("(?:$|(?=\n))");
            } else {
               this.emitSnippet("(?:$|(?=\n$))");
            }

            this.lastTerm = PythonRegexParser.TermCategory.Assertion;
            break;
         case 40:
            this.parens();
            break;
         case 42:
         case 43:
         case 63:
         case 123:
            this.quantifier(ch);
            break;
         case 46:
            if (this.getLocalFlags().isDotAll()) {
               this.emitSnippet(".");
            } else {
               this.emitSnippet("[^\n]");
            }

            this.lastTerm = PythonRegexParser.TermCategory.Atom;
            break;
         case 91:
            this.characterClass();
            this.lastTerm = PythonRegexParser.TermCategory.Atom;
            break;
         case 92:
            this.escape();
            break;
         case 94:
            if (this.getLocalFlags().isMultiLine()) {
               this.emitSnippet("(?:^|(?<=\n))");
            } else {
               this.emitSnippet("^");
            }

            this.lastTerm = PythonRegexParser.TermCategory.Assertion;
            break;
         default:
            this.emitChar(ch);
            this.lastTerm = PythonRegexParser.TermCategory.Atom;
      }
   }

   private void comment() {
      while (!this.atEnd()) {
         int ch = this.consumeChar();
         if (ch == 92 && !this.atEnd()) {
            this.advance();
         } else {
            if (ch != 10) {
               continue;
            }
            break;
         }
      }
   }

   private void escape() {
      if (this.atEnd()) {
         throw this.syntaxErrorAtRel("bad escape (end of pattern)", 1);
      } else if (this.assertionEscape()) {
         this.lastTerm = PythonRegexParser.TermCategory.Assertion;
      } else if (this.categoryEscape(false)) {
         this.lastTerm = PythonRegexParser.TermCategory.Atom;
      } else if (this.backreference()) {
         this.lastTerm = PythonRegexParser.TermCategory.Atom;
      } else {
         this.characterEscape(false);
         this.lastTerm = PythonRegexParser.TermCategory.Atom;
      }
   }

   private boolean assertionEscape() {
      switch (this.consumeChar()) {
         case 65:
            this.emitSnippet("^");
            return true;
         case 66:
            if (this.getLocalFlags().isUnicode(this.mode)) {
               this.emitWordBoundaryAssertion("(?:^(?=\\W)|(?<=\\W)$|(?<=\\W)(?=\\W)|(?<=\\w)(?=\\w))", UNICODE_CHAR_CLASS_REPLACEMENTS.get('w'));
            } else if (this.getLocalFlags().isLocale()) {
               this.bailOut("locale-specific word boundary assertions not supported");
            } else {
               this.emitWordBoundaryAssertion("(?:^(?=\\W)|(?<=\\W)$|(?<=\\W)(?=\\W)|(?<=\\w)(?=\\w))", "\\w");
            }

            return true;
         case 90:
            this.emitSnippet("$");
            return true;
         case 98:
            if (this.getLocalFlags().isUnicode(this.mode)) {
               this.emitWordBoundaryAssertion("(?:(?:^|(?<=\\W))(?=\\w)|(?<=\\w)(?:(?=\\W)|$))", UNICODE_CHAR_CLASS_REPLACEMENTS.get('w'));
            } else if (this.getLocalFlags().isLocale()) {
               this.bailOut("locale-specific word boundary assertions not supported");
            } else {
               this.emitWordBoundaryAssertion("(?:(?:^|(?<=\\W))(?=\\w)|(?<=\\w)(?:(?=\\W)|$))", "\\w");
            }

            return true;
         default:
            this.retreat();
            return false;
      }
   }

   private void emitWordBoundaryAssertion(String snippetTemplate, String wordCharsStr) {
      Matcher matcher = WORD_CHARS_PATTERN.matcher(snippetTemplate);

      int lastAppendPosition;
      for (lastAppendPosition = 0; matcher.find(); lastAppendPosition = matcher.end()) {
         this.emitSnippet(snippetTemplate.substring(lastAppendPosition, matcher.start()));
         if (matcher.group().equals("\\w")) {
            this.emitSnippet("[" + wordCharsStr + "]");
         } else {
            assert matcher.group().equals("\\W");

            this.emitSnippet("[^" + wordCharsStr + "]");
         }
      }

      this.emitSnippet(snippetTemplate.substring(lastAppendPosition));
   }

   private boolean categoryEscape(boolean inCharClass) {
      switch (this.curChar()) {
         case 68:
         case 83:
         case 87:
         case 100:
         case 115:
         case 119:
            char className = (char)this.curChar();
            this.advance();
            if (this.getLocalFlags().isUnicode(this.mode)) {
               if (inCharClass) {
                  if (UNICODE_CHAR_CLASS_REPLACEMENTS.containsKey(className)) {
                     this.emitSnippet(UNICODE_CHAR_CLASS_REPLACEMENTS.get(className));
                  } else {
                     this.emitCharSetNoCasing(UNICODE_CHAR_CLASS_SETS.get(className));
                  }
               } else if (UNICODE_CHAR_CLASS_REPLACEMENTS.containsKey(className)) {
                  this.emitSnippet("[" + UNICODE_CHAR_CLASS_REPLACEMENTS.get(className) + "]");
               } else if (UNICODE_CHAR_CLASS_REPLACEMENTS.containsKey(Character.toLowerCase(className))) {
                  this.emitSnippet("[^" + UNICODE_CHAR_CLASS_REPLACEMENTS.get(Character.toLowerCase(className)) + "]");
               } else {
                  this.emitSnippet("[");
                  this.emitCharSetNoCasing(UNICODE_CHAR_CLASS_SETS.get(className));
                  this.emitSnippet("]");
               }
            } else if (!this.getLocalFlags().isLocale() || className != 'w' && className != 'W') {
               if ((this.mode == PythonREMode.Bytes || this.getLocalFlags().isAscii()) && (className == 's' || className == 'S')) {
                  String snippet = className == 's' ? "\\x09-\\x0d\\x20" : "\\x00-\\x08\\x0e-\\x1f\\x21-\\u{10ffff}";
                  this.emitSnippet(inCharClass ? snippet : "[" + snippet + "]");
               } else {
                  this.emitSnippet("\\" + className);
               }
            } else {
               this.bailOut("locale-specific definitions of word characters are not supported");
            }

            return true;
         default:
            return false;
      }
   }

   private boolean backreference() {
      if (this.curChar() >= 49 && this.curChar() <= 57) {
         String octalEscape = this.getUpTo(3, PythonRegexParser::isOctDigit);
         if (octalEscape.length() == 3) {
            int codePoint = Integer.parseInt(octalEscape, 8);
            if (codePoint > 255) {
               throw this.syntaxErrorAtRel(PyErrorMessages.invalidOctalEscape(octalEscape), 1 + octalEscape.length());
            } else {
               this.emitChar(codePoint);
               return true;
            }
         } else {
            this.retreat(octalEscape.length());
            String number = this.getUpTo(2, PythonRegexParser::isDecDigit);
            int groupNumber = Integer.parseInt(number);
            if (groupNumber > this.groups) {
               throw this.syntaxErrorAtRel(PyErrorMessages.invalidGroupReference(number), number.length());
            } else {
               this.verifyGroupReference(groupNumber, number);
               if (this.getLocalFlags().isIgnoreCase()) {
                  this.bailOut("case insensitive backreferences not supported");
               } else {
                  this.emitSnippet("(?:\\" + number + ")");
               }

               return true;
            }
         }
      } else {
         return false;
      }
   }

   private void verifyGroupReference(int groupNumber, String groupName) throws RegexSyntaxException {
      for (PythonRegexParser.Group openGroup : this.groupStack) {
         if (groupNumber == openGroup.groupNumber) {
            throw this.syntaxErrorAtRel("cannot refer to an open group", groupName.length() + 1);
         }
      }

      for (PythonRegexParser.Lookbehind openLookbehind : this.lookbehindStack) {
         if (groupNumber >= openLookbehind.containedGroups) {
            throw this.syntaxErrorHere("cannot refer to group defined in the same lookbehind subpattern");
         }
      }
   }

   private void characterEscape(boolean inCharClass) {
      this.emitChar(this.silentCharacterEscape(), inCharClass);
   }

   private int silentCharacterEscape() {
      int ch = this.consumeChar();
      switch (ch) {
         case 78:
            if (this.mode != PythonREMode.Str) {
               throw this.syntaxErrorAtRel(PyErrorMessages.badEscape(ch), 2);
            } else if (!this.match("{")) {
               throw this.syntaxErrorHere(PyErrorMessages.missing("{"));
            } else {
               String characterName = this.getUntil('}', "character name");
               int codePoint = lookupCharacterByName(characterName);
               if (codePoint == -1) {
                  throw this.syntaxErrorAtRel(PyErrorMessages.undefinedCharacterName(characterName), characterName.length() + 4);
               }

               return codePoint;
            }
         case 79:
         case 80:
         case 81:
         case 82:
         case 83:
         case 84:
         case 86:
         case 87:
         case 88:
         case 89:
         case 90:
         case 91:
         case 93:
         case 94:
         case 95:
         case 96:
         case 99:
         case 100:
         case 101:
         case 103:
         case 104:
         case 105:
         case 106:
         case 107:
         case 108:
         case 109:
         case 111:
         case 112:
         case 113:
         case 115:
         case 119:
         default:
            if (isOctDigit(ch)) {
               this.retreat();
               String codex = this.getUpTo(3, PythonRegexParser::isOctDigit);
               int codePoint = Integer.parseInt(codex, 8);
               if (codePoint > 255) {
                  throw this.syntaxErrorAtRel(PyErrorMessages.invalidOctalEscape(codex), 1 + codex.length());
               }

               return codePoint;
            } else {
               if (!isAsciiLetter(ch) && !isDecDigit(ch)) {
                  return ch;
               }

               throw this.syntaxErrorAtRel(PyErrorMessages.badEscape(ch), 2);
            }
         case 85:
         case 117:
            if (this.mode == PythonREMode.Str) {
               char escapeLead = (char)ch;
               int escapeLength;
               switch (escapeLead) {
                  case 'U':
                     escapeLength = 8;
                     break;
                  case 'u':
                     escapeLength = 4;
                     break;
                  default:
                     throw CompilerDirectives.shouldNotReachHere();
               }

               String codex = this.getUpTo(escapeLength, PythonRegexParser::isHexDigit);
               if (codex.length() < escapeLength) {
                  throw this.syntaxErrorAtRel(PyErrorMessages.incompleteEscapeU(escapeLead, codex), 2 + codex.length());
               }

               try {
                  int codePoint = Integer.parseInt(codex, 16);
                  if (codePoint > 1114111) {
                     throw this.syntaxErrorAtRel(PyErrorMessages.invalidUnicodeEscape(escapeLead, codex), 2 + codex.length());
                  }

                  return codePoint;
               } catch (NumberFormatException var6) {
                  throw this.syntaxErrorAtRel(PyErrorMessages.incompleteEscapeU(escapeLead, codex), 2 + codex.length());
               }
            }

            throw this.syntaxErrorAtRel(PyErrorMessages.badEscape(this.curChar()), 1);
         case 92:
            return 92;
         case 97:
            return 7;
         case 98:
            return 8;
         case 102:
            return 12;
         case 110:
            return 10;
         case 114:
            return 13;
         case 116:
            return 9;
         case 118:
            return 11;
         case 120:
            String code = this.getUpTo(2, PythonRegexParser::isHexDigit);
            if (code.length() < 2) {
               throw this.syntaxErrorAtRel(PyErrorMessages.incompleteEscapeX(code), 2 + code.length());
            } else {
               return Integer.parseInt(code, 16);
            }
      }
   }

   public static int lookupCharacterByName(String characterName) {
      String normalizedName = characterName.trim().toUpperCase(Locale.ROOT);
      return UnicodeCharacterAliases.CHARACTER_ALIASES.containsKey(normalizedName)
         ? UnicodeCharacterAliases.CHARACTER_ALIASES.get(normalizedName)
         : UCharacter.getCharFromName(characterName);
   }

   private void characterClass() {
      this.emitSnippet("[");
      int start = this.position - 1;
      if (this.match("^")) {
         this.emitSnippet("^");
      }

      int firstPosInside = this.position;

      while (!this.atEnd()) {
         int rangeStart = this.position;
         int ch = this.consumeChar();
         Optional<Integer> lowerBound;
         switch (ch) {
            case 92:
               lowerBound = this.classEscape();
               break;
            case 93:
               if (this.position != firstPosInside + 1) {
                  this.emitSnippet("]");
                  return;
               }

               lowerBound = Optional.of(93);
               break;
            default:
               lowerBound = Optional.of(ch);
         }

         if (this.match("-")) {
            if (this.atEnd()) {
               throw this.syntaxErrorAtAbs("unterminated character set", start);
            }

            ch = this.consumeChar();
            Optional<Integer> upperBound;
            switch (ch) {
               case 92:
                  upperBound = this.classEscape();
                  break;
               case 93:
                  if (lowerBound.isPresent()) {
                     this.emitChar(lowerBound.get(), true);
                  }

                  this.emitChar(45, true);
                  this.emitSnippet("]");
                  return;
               default:
                  upperBound = Optional.of(ch);
            }

            if (!lowerBound.isPresent() || !upperBound.isPresent() || upperBound.get() < lowerBound.get()) {
               throw this.syntaxErrorAtAbs(PyErrorMessages.badCharacterRange(this.inPattern.substring(rangeStart, this.position)), rangeStart);
            }

            this.curCharClass.clear();
            this.curCharClass.addRange(lowerBound.get(), upperBound.get());
            this.emitCharSet();
         } else if (lowerBound.isPresent()) {
            this.emitChar(lowerBound.get(), true);
         }
      }

      throw this.syntaxErrorAtAbs("unterminated character set", start);
   }

   private Optional<Integer> classEscape() {
      return this.categoryEscape(true) ? Optional.empty() : Optional.of(this.silentCharacterEscape());
   }

   private void quantifier(int ch) {
      int start = this.position - 1;
      if (ch == 123) {
         if (this.match("}")) {
            this.emitString(this.inPattern.substring(start, this.position));
            this.lastTerm = PythonRegexParser.TermCategory.Atom;
            return;
         }

         if (this.match(",}")) {
            this.emitSnippet("*");
         } else {
            Optional<BigInteger> lowerBound = Optional.empty();
            Optional<BigInteger> upperBound = Optional.empty();
            String lower = this.getMany(PythonRegexParser::isDecDigit);
            if (!lower.isEmpty()) {
               lowerBound = Optional.of(new BigInteger(lower));
            }

            if (this.match(",")) {
               String upper = this.getMany(PythonRegexParser::isDecDigit);
               if (!upper.isEmpty()) {
                  upperBound = Optional.of(new BigInteger(upper));
               }
            } else {
               upperBound = lowerBound;
            }

            if (!this.match("}")) {
               this.emitString(this.inPattern.substring(start, this.position));
               this.lastTerm = PythonRegexParser.TermCategory.Atom;
               return;
            }

            if (lowerBound.isPresent() && upperBound.isPresent() && lowerBound.get().compareTo(upperBound.get()) > 0) {
               throw this.syntaxErrorAtAbs("min repeat greater than max repeat", start + 1);
            }

            if (lowerBound.isPresent()) {
               this.emitSnippet(this.inPattern.substring(start, this.position));
            } else {
               this.emitSnippet("{0,");

               assert this.inPattern.charAt(start) == '{' && this.inPattern.charAt(start + 1) == ',';

               this.emitSnippet(this.inPattern.substring(start + 2, this.position));
            }
         }
      } else {
         this.emitRawCodepoint(ch);
      }

      switch (this.lastTerm) {
         case None:
         case Assertion:
            throw this.syntaxErrorAtAbs("nothing to repeat", start);
         case Quantifier:
            throw this.syntaxErrorAtAbs("multiple repeat", start);
         case Atom:
            if (this.match("?")) {
               this.emitSnippet("?");
            }

            this.lastTerm = PythonRegexParser.TermCategory.Quantifier;
      }
   }

   private void parens() {
      int start = this.position - 1;
      if (!this.atEnd()) {
         int ch0 = this.consumeChar();
         switch (ch0) {
            case 63:
               this.mustHaveMore();
               int ch1 = this.consumeChar();
               switch (ch1) {
                  case 33:
                     this.lookahead(false);
                     return;
                  case 35:
                     this.parenComment();
                     return;
                  case 40:
                     this.conditionalBackreference();
                     return;
                  case 45:
                  case 76:
                  case 97:
                  case 105:
                  case 109:
                  case 115:
                  case 116:
                  case 117:
                  case 120:
                     this.flags(ch1);
                     return;
                  case 58:
                     this.group(false, Optional.empty(), start);
                     return;
                  case 60:
                     this.mustHaveMore();
                     int ch2 = this.consumeChar();
                     switch (ch2) {
                        case 33:
                           this.lookbehind(false);
                           return;
                        case 61:
                           this.lookbehind(true);
                           return;
                        default:
                           throw this.syntaxErrorAtRel(PyErrorMessages.unknownExtensionLt(ch2), 3);
                     }
                  case 61:
                     this.lookahead(true);
                     return;
                  case 80:
                     this.mustHaveMore();
                     int ch2 = this.consumeChar();
                     switch (ch2) {
                        case 60:
                           String groupName = this.parseGroupName('>');
                           this.group(true, Optional.of(groupName), start);
                           return;
                        case 61:
                           this.namedBackreference();
                           return;
                        default:
                           throw this.syntaxErrorAtRel(PyErrorMessages.unknownExtensionP(ch2), 3);
                     }
                  default:
                     throw this.syntaxErrorAtRel(PyErrorMessages.unknownExtensionQ(ch1), 2);
               }
            default:
               this.retreat();
               this.group(true, Optional.empty(), start);
         }
      } else {
         throw this.syntaxErrorAtAbs("missing ), unterminated subpattern", start);
      }
   }

   private String parseGroupName(char terminator) {
      assert terminator == '>' || terminator == ')';

      String groupName = this.getMany(c -> c != terminator);
      if (groupName.isEmpty()) {
         throw this.syntaxErrorHere("missing group name");
      } else if (!this.match(Character.toString(terminator))) {
         throw this.syntaxErrorAtRel(terminator == ')' ? "missing ), unterminated name" : "missing >, unterminated name", groupName.length());
      } else if (!checkGroupName(groupName)) {
         throw this.syntaxErrorAtRel(PyErrorMessages.badCharacterInGroupName(groupName), groupName.length() + 1);
      } else {
         return groupName;
      }
   }

   private static boolean checkGroupName(String groupName) {
      if (groupName.isEmpty()) {
         return false;
      } else {
         for (int i = 0; i < groupName.length(); i = groupName.offsetByCodePoints(i, 1)) {
            int ch = groupName.codePointAt(i);
            if (i == 0 && !XID_START.contains(ch)) {
               return false;
            }

            if (i > 0 && !XID_CONTINUE.contains(ch)) {
               return false;
            }
         }

         return true;
      }
   }

   private void namedBackreference() {
      String groupName = this.parseGroupName(')');
      if (this.namedCaptureGroups != null && this.namedCaptureGroups.containsKey(groupName)) {
         int groupNumber = this.namedCaptureGroups.get(groupName);
         this.verifyGroupReference(groupNumber, groupName);
         this.emitSnippet("\\" + groupNumber);
         this.lastTerm = PythonRegexParser.TermCategory.Atom;
      } else {
         throw this.syntaxErrorAtRel(PyErrorMessages.unknownGroupName(groupName), groupName.length() + 1);
      }
   }

   private void parenComment() {
      int start = this.position - 3;
      this.getMany(c -> c != 41);
      if (!this.match(")")) {
         throw this.syntaxErrorAtAbs("missing ), unterminated comment", start);
      }
   }

   private void group(boolean capturing, Optional<String> optName, int start) {
      if (capturing) {
         this.groups++;
         this.groupStack.push(new PythonRegexParser.Group(this.groups));
         this.emitSnippet("(");
      } else {
         this.emitSnippet("(?:");
      }

      optName.ifPresent(name -> {
         if (this.namedCaptureGroups == null) {
            this.namedCaptureGroups = new HashMap<>();
         }

         if (this.namedCaptureGroups.containsKey(name)) {
            throw this.syntaxErrorAtRel(PyErrorMessages.redefinitionOfGroupName(name, this.groups, this.namedCaptureGroups.get(name)), name.length() + 1);
         } else {
            this.namedCaptureGroups.put(name, this.groups);
         }
      });
      this.disjunction();
      if (this.match(")")) {
         this.emitSnippet(")");
         if (capturing) {
            this.groupStack.pop();
         }

         this.lastTerm = PythonRegexParser.TermCategory.Atom;
      } else {
         throw this.syntaxErrorAtAbs("missing ), unterminated subpattern", start);
      }
   }

   private void lookahead(boolean positive) {
      int start = this.position - 3;
      if (positive) {
         this.emitSnippet("(?:(?=");
      } else {
         this.emitSnippet("(?:(?!");
      }

      this.disjunction();
      if (this.match(")")) {
         this.emitSnippet("))");
         this.lastTerm = PythonRegexParser.TermCategory.Atom;
      } else {
         throw this.syntaxErrorAtAbs("missing ), unterminated subpattern", start);
      }
   }

   private void lookbehind(boolean positive) {
      int start = this.position - 4;
      if (positive) {
         this.emitSnippet("(?:(?<=");
      } else {
         this.emitSnippet("(?:(?<!");
      }

      this.lookbehindStack.push(new PythonRegexParser.Lookbehind(this.groups + 1));
      this.disjunction();
      this.lookbehindStack.pop();
      if (this.match(")")) {
         this.emitSnippet("))");
         this.lastTerm = PythonRegexParser.TermCategory.Atom;
      } else {
         throw this.syntaxErrorAtAbs("missing ), unterminated subpattern", start);
      }
   }

   private void conditionalBackreference() {
      int start = this.position - 3;
      this.bailOut("conditional backreference groups not supported");
      String groupId = this.getMany(c -> c != 41);
      if (groupId.isEmpty()) {
         throw this.syntaxErrorHere("missing group name");
      } else if (!this.match(Character.toString(')'))) {
         throw this.syntaxErrorAtRel("missing ), unterminated name", groupId.length());
      } else {
         int groupNumber;
         if (checkGroupName(groupId)) {
            if (this.namedCaptureGroups == null || !this.namedCaptureGroups.containsKey(groupId)) {
               throw this.syntaxErrorAtRel(PyErrorMessages.unknownGroupName(groupId), groupId.length() + 1);
            }

            groupNumber = this.namedCaptureGroups.get(groupId);
         } else {
            try {
               groupNumber = Integer.parseInt(groupId);
               if (groupNumber < 0) {
                  throw new NumberFormatException("negative group number");
               }
            } catch (NumberFormatException var5) {
               throw this.syntaxErrorAtRel(PyErrorMessages.badCharacterInGroupName(groupId), groupId.length() + 1);
            }
         }

         if (!this.lookbehindStack.isEmpty()) {
            this.verifyGroupReference(groupNumber, groupId);
         }

         this.disjunction();
         if (this.match("|")) {
            this.disjunction();
            if (this.curChar() == 124) {
               throw this.syntaxErrorHere("conditional backref with more than two branches");
            }
         }

         if (!this.match(")")) {
            throw this.syntaxErrorAtAbs("missing ), unterminated subpattern", start);
         } else {
            this.lastTerm = PythonRegexParser.TermCategory.Atom;
         }
      }
   }

   private void flags(int ch0) {
      int start = this.position - 3;
      int ch = ch0;

      PythonFlags positiveFlags;
      for (positiveFlags = PythonFlags.EMPTY_INSTANCE; PythonFlags.isValidFlagChar(ch); ch = this.consumeChar()) {
         positiveFlags = positiveFlags.addFlag(ch);
         if (this.mode == PythonREMode.Str && ch == 76) {
            throw this.syntaxErrorHere("bad inline flags: cannot use 'L' flag with a str pattern");
         }

         if (this.mode == PythonREMode.Bytes && ch == 117) {
            throw this.syntaxErrorHere("bad inline flags: cannot use 'u' flag with a bytes pattern");
         }

         if (positiveFlags.numberOfTypeFlags() > 1) {
            throw this.syntaxErrorHere("bad inline flags: flags 'a', 'u' and 'L' are incompatible");
         }

         if (this.atEnd()) {
            throw this.syntaxErrorHere("missing -, : or )");
         }
      }

      switch (ch) {
         case 41:
            this.globalFlags = this.globalFlags.addFlags(positiveFlags);
            break;
         case 45:
            if (positiveFlags.includesGlobalFlags()) {
               throw this.syntaxErrorAtRel("bad inline flags: cannot turn on global flag", 1);
            }

            if (this.atEnd()) {
               throw this.syntaxErrorHere("missing flag");
            }

            ch = this.consumeChar();
            if (!PythonFlags.isValidFlagChar(ch)) {
               if (Character.isAlphabetic(ch)) {
                  throw this.syntaxErrorAtRel("unknown flag", 1);
               }

               throw this.syntaxErrorAtRel("missing flag", 1);
            }

            PythonFlags negativeFlags;
            for (negativeFlags = PythonFlags.EMPTY_INSTANCE; PythonFlags.isValidFlagChar(ch); ch = this.consumeChar()) {
               negativeFlags = negativeFlags.addFlag(ch);
               if (PythonFlags.isTypeFlagChar(ch)) {
                  throw this.syntaxErrorHere("bad inline flags: cannot turn off flags 'a', 'u' and 'L'");
               }

               if (this.atEnd()) {
                  throw this.syntaxErrorHere("missing :");
               }
            }

            if (ch != 58) {
               if (Character.isAlphabetic(ch)) {
                  throw this.syntaxErrorAtRel("unknown flag", 1);
               }

               throw this.syntaxErrorAtRel("missing :", 1);
            }

            if (negativeFlags.includesGlobalFlags()) {
               throw this.syntaxErrorAtRel("bad inline flags: cannot turn off global flag", 1);
            }

            this.localFlags(positiveFlags, negativeFlags, start);
            break;
         case 58:
            if (positiveFlags.includesGlobalFlags()) {
               throw this.syntaxErrorAtRel("bad inline flags: cannot turn on global flag", 1);
            }

            this.localFlags(positiveFlags, PythonFlags.EMPTY_INSTANCE, start);
            break;
         default:
            if (Character.isAlphabetic(ch)) {
               throw this.syntaxErrorAtRel("unknown flag", 1);
            }

            throw this.syntaxErrorAtRel("missing -, : or )", 1);
      }
   }

   private void localFlags(PythonFlags positiveFlags, PythonFlags negativeFlags, int start) {
      if (positiveFlags.overlaps(negativeFlags)) {
         throw this.syntaxErrorAtRel("bad inline flags: flag turned on and off", 1);
      } else {
         PythonFlags newFlags = this.getLocalFlags().addFlags(positiveFlags).delFlags(negativeFlags);
         if (positiveFlags.numberOfTypeFlags() > 0) {
            PythonFlags otherTypes = PythonFlags.TYPE_FLAGS_INSTANCE.delFlags(positiveFlags);
            newFlags = newFlags.delFlags(otherTypes);
         }

         this.flagsStack.push(newFlags);
         this.group(false, Optional.empty(), start);
         this.flagsStack.pop();
      }
   }

   static {
      UNICODE_CHAR_CLASS_REPLACEMENTS.put('d', "\\p{General_Category=Decimal_Number}");
      UNICODE_CHAR_CLASS_REPLACEMENTS.put('D', "\\P{General_Category=Decimal_Number}");
      UNICODE_CHAR_CLASS_SETS.put('d', UnicodeProperties.getProperty("General_Category=Decimal_Number"));
      UNICODE_CHAR_CLASS_SETS.put('D', UnicodeProperties.getProperty("General_Category=Decimal_Number").createInverse(Encodings.UTF_32));
      UNICODE_CHAR_CLASS_REPLACEMENTS.put('s', "\\p{White_Space}\u001c-\u001f");
      CodePointSet unicodeSpaces = UnicodeProperties.getProperty("White_Space");
      CodePointSet spaces = unicodeSpaces.union(CodePointSet.createNoDedup(28, 31));
      CodePointSet nonSpaces = spaces.createInverse(Encodings.UTF_32);
      UNICODE_CHAR_CLASS_SETS.put('s', spaces);
      UNICODE_CHAR_CLASS_SETS.put('S', nonSpaces);
      String alphaStr = "\\p{General_Category=Letter}";
      String numericStr = "\\p{General_Category=Number}參拾兩零六陸什\\u{2f890}";
      String wordCharsStr = alphaStr + numericStr + "_";
      UNICODE_CHAR_CLASS_REPLACEMENTS.put('w', wordCharsStr);
      CodePointSet alpha = UnicodeProperties.getProperty("General_Category=Letter");
      CodePointSet numericExtras = CodePointSet.createNoDedup(63851, 63859, 63864, 63922, 63953, 63955, 63997, 194704);
      CodePointSet numeric = UnicodeProperties.getProperty("General_Category=Number").union(numericExtras);
      CodePointSet wordChars = alpha.union(numeric).union(CodePointSet.create(95));
      CodePointSet nonWordChars = wordChars.createInverse(Encodings.UTF_32);
      UNICODE_CHAR_CLASS_SETS.put('w', wordChars);
      UNICODE_CHAR_CLASS_SETS.put('W', nonWordChars);
   }

   private static final class Group {
      public final int groupNumber;

      Group(int groupNumber) {
         this.groupNumber = groupNumber;
      }
   }

   private static final class Lookbehind {
      public final int containedGroups;

      Lookbehind(int containedGroups) {
         this.containedGroups = containedGroups;
      }
   }

   private static enum TermCategory {
      Assertion,
      Atom,
      Quantifier,
      None;
   }
}
