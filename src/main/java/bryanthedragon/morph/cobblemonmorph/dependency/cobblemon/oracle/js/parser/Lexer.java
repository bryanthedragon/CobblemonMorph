package com.oracle.js.parser;

import com.oracle.truffle.api.strings.TruffleString;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public class Lexer extends Scanner {
   private static final boolean XML_LITERALS = Options.getBooleanProperty("lexer.xmlliterals");
   private static final String MSG_EDIT_STRING_MISSING_BRACE = "edit.string.missing.brace";
   private static final String MSG_HERE_MISSING_END_MARKER = "here.missing.end.marker";
   private static final String MSG_HERE_NON_MATCHING_DELIMITER = "here.non.matching.delimiter";
   private static final String MSG_ILLEGAL_IDENTIFIER_CHARACTER = "illegal.identifier.character";
   private static final String MSG_INVALID_ESCAPE_CHAR = "invalid.escape.char";
   private static final String MSG_INVALID_HEX = "invalid.hex";
   private static final String MSG_LEXER_ERROR = "lexer.error.";
   private static final String MSG_MISSING_CLOSE_QUOTE = "missing.close.quote";
   private static final String MSG_MISSING_SPACE_AFTER_NUMBER = "missing.space.after.number";
   private static final String MSG_NUMERIC_LITERAL_MULTIPLE_SEPARATORS = "numeric.literal.multiple.separators";
   private static final String MSG_NUMERIC_LITERAL_TRAILING_SEPARATOR = "numeric.literal.trailing.separator";
   private static final String MSG_STRICT_NO_NONOCTALDECIMAL = "strict.no.nonoctaldecimal";
   private static final String MSG_STRICT_NO_OCTAL = "strict.no.octal";
   private final Source source;
   private final TokenStream stream;
   private final boolean scripting;
   private final boolean shebang;
   private final int ecmaScriptVersion;
   private final boolean nested;
   private final boolean isModule;
   private final boolean allowBigInt;
   int pendingLine;
   private int linePosition;
   private TokenType last;
   private final boolean pauseOnFunctionBody;
   private boolean pauseOnNextLeftBrace;
   boolean pauseOnRightBrace;
   private final Map<String, TruffleString> internedStrings;
   private static final int JAVASCRIPT_WHITESPACE_HIGH_START = 5760;

   public Lexer(
      final Source source,
      final TokenStream stream,
      final boolean scripting,
      final int ecmaScriptVersion,
      final boolean shebang,
      final boolean isModule,
      final boolean allowBigInt
   ) {
      this(source, 0, source.getLength(), stream, scripting, ecmaScriptVersion, shebang, isModule, false, allowBigInt);
   }

   public Lexer(
      final Source source,
      final int start,
      final int len,
      final TokenStream stream,
      final boolean scripting,
      final int ecmaScriptVersion,
      final boolean shebang,
      final boolean isModule,
      final boolean pauseOnFunctionBody,
      final boolean allowBigInt
   ) {
      super(source.getContent(), 1, start, len);
      this.source = source;
      this.stream = stream;
      this.scripting = scripting;
      this.ecmaScriptVersion = ecmaScriptVersion;
      this.shebang = shebang;
      this.nested = false;
      this.isModule = isModule;
      this.allowBigInt = allowBigInt;
      this.pendingLine = 1;
      this.last = TokenType.EOL;
      this.pauseOnFunctionBody = pauseOnFunctionBody;
      this.internedStrings = new HashMap<>();
   }

   private Lexer(final Lexer lexer, final Lexer.State state) {
      super(lexer, state);
      this.source = lexer.source;
      this.stream = lexer.stream;
      this.scripting = lexer.scripting;
      this.ecmaScriptVersion = lexer.ecmaScriptVersion;
      this.shebang = lexer.shebang;
      this.nested = true;
      this.isModule = lexer.isModule;
      this.allowBigInt = lexer.allowBigInt;
      this.pendingLine = state.pendingLine;
      this.linePosition = state.linePosition;
      this.last = TokenType.EOL;
      this.pauseOnFunctionBody = false;
      this.internedStrings = lexer.internedStrings;
   }

   Lexer.State saveState() {
      return new Lexer.State(this.position, this.limit, this.line, this.pendingLine, this.linePosition, this.last);
   }

   @Override
   void restoreState(final Scanner.State state) {
      super.restoreState(state);
      Lexer.State lexerState = (Lexer.State)state;
      this.pendingLine = lexerState.pendingLine;
      this.linePosition = lexerState.linePosition;
      this.last = lexerState.last;
   }

   private boolean isES6() {
      return this.ecmaScriptVersion >= 6;
   }

   private boolean isES2020() {
      return this.ecmaScriptVersion >= 11;
   }

   protected void add(final TokenType type, final int start, final int end) {
      this.last = type;
      if (type == TokenType.EOL) {
         this.pendingLine = end;
         this.linePosition = start;
      } else {
         if (this.pendingLine != -1) {
            this.stream.put(Token.toDesc(TokenType.EOL, this.linePosition, this.pendingLine));
            this.pendingLine = -1;
         }

         this.stream.put(Token.toDesc(type, start, end - start));
      }
   }

   protected void add(final TokenType type, final int start) {
      this.add(type, start, this.position);
   }

   private void skipEOL(final boolean addEOL) {
      if (this.ch0 == '\r') {
         this.skip(1);
         if (this.ch0 == '\n') {
            this.skip(1);
         }
      } else {
         this.skip(1);
      }

      this.line++;
      if (addEOL) {
         this.add(TokenType.EOL, this.position, this.line);
      }
   }

   private void skipLine(final boolean addEOL) {
      while (!this.isEOL(this.ch0) && !this.atEOF()) {
         this.skip(1);
      }

      this.skipEOL(addEOL);
   }

   public static boolean isJSWhitespace(final char ch) {
      if (ch <= '\r') {
         return ch >= '\t';
      } else {
         return ch >= 5760 ? isWhitespaceHigh(ch) : ch == ' ' || ch == 160;
      }
   }

   private static boolean isWhitespaceHigh(final char ch) {
      return ch == 5760 || ch >= 8192 && (ch <= 8202 || ch == 8232 || ch == 8233 || ch == 8239 || ch == 8287 || ch == 12288 || ch == '\ufeff');
   }

   public static boolean isJSEOL(final char ch) {
      return ch == '\n' || ch == '\r' || ch == 8232 || ch == 8233;
   }

   public static boolean isStringLineTerminator(final char ch) {
      return ch == '\n' || ch == '\r';
   }

   protected boolean isStringDelimiter(final char ch) {
      return ch == '\'' || ch == '"';
   }

   private static boolean isTemplateDelimiter(char ch) {
      return ch == '`';
   }

   protected boolean isWhitespace(final char ch) {
      return isJSWhitespace(ch);
   }

   protected boolean isEOL(final char ch) {
      return isJSEOL(ch);
   }

   private void skipWhitespace(final boolean addEOL) {
      while (this.isWhitespace(this.ch0)) {
         if (this.isEOL(this.ch0)) {
            this.skipEOL(addEOL);
         } else {
            this.skip(1);
         }
      }
   }

   private void skipUntilEOL() {
      while (!this.atEOF() && !this.isEOL(this.ch0)) {
         this.skip(1);
      }
   }

   private void skipSingleLineComment() {
      assert this.ch0 == '/' && this.ch1 == '/';

      int start = this.position;
      this.skip(2);
      boolean directiveComment = (this.ch0 == '#' || this.ch0 == '@') && this.ch1 == ' ';
      this.skipUntilEOL();
      this.add(directiveComment ? TokenType.DIRECTIVE_COMMENT : TokenType.COMMENT, start);
   }

   private void skipMultiLineComment() {
      assert this.ch0 == '/' && this.ch1 == '*';

      int start = this.position;
      this.skip(2);

      while (!this.atEOF() && (this.ch0 != '*' || this.ch1 != '/')) {
         if (this.isEOL(this.ch0)) {
            this.skipEOL(true);
         } else {
            this.skip(1);
         }
      }

      if (this.atEOF()) {
         this.add(TokenType.ERROR, start);
      } else {
         this.skip(2);
      }

      this.add(TokenType.COMMENT, start);
   }

   private void skipShebang() {
      assert this.shebang || this.scripting;

      assert this.ch0 == '#';

      int start = this.position;
      this.skip(1);
      this.skipUntilEOL();
      this.add(TokenType.COMMENT, start);
   }

   private void skipSingleLineHTMLOpenComment() {
      assert !this.isModule;

      assert this.ch0 == '<' && this.ch1 == '!' && this.ch2 == '-' && this.ch3 == '-';

      int start = this.position;
      this.skip(4);
      this.skipUntilEOL();
      this.add(TokenType.COMMENT, start);
   }

   private void skipSingleLineHTMLCloseComment() {
      assert !this.isModule;

      assert this.ch0 == '-' && this.ch1 == '-' && this.ch2 == '>';

      int start = this.position;
      this.skip(3);
      this.skipUntilEOL();
      this.add(TokenType.COMMENT, start);
   }

   private boolean seenEOL() {
      if (this.last == TokenType.EOL) {
         return true;
      } else {
         int idx = this.stream.last();

         while (idx >= 0) {
            switch (Token.descType(this.stream.get(idx--))) {
               case COMMENT:
                  break;
               case EOL:
                  return true;
               default:
                  return false;
            }
         }

         return false;
      }
   }

   public Lexer.RegexToken valueOfPattern(final int start, final int length) {
      int savePosition = this.position;
      this.reset(start);
      StringBuilder sb = new StringBuilder(length);
      this.skip(1);
      boolean inBrackets = false;

      while (!this.atEOF() && this.ch0 != '/' && !this.isEOL(this.ch0) || inBrackets) {
         if (this.ch0 == '\\') {
            sb.append(this.ch0);
            sb.append(this.ch1);
            this.skip(2);
         } else {
            if (this.ch0 == '[') {
               inBrackets = true;
            } else if (this.ch0 == ']') {
               inBrackets = false;
            }

            sb.append(this.ch0);
            this.skip(1);
         }
      }

      TruffleString regex = this.stringIntern(sb.toString());
      this.skip(1);
      TruffleString options = this.stringIntern(this.source.getString(this.position, this.scanIdentifier()));
      this.reset(savePosition);
      return new Lexer.RegexToken(regex, options);
   }

   public boolean canStartLiteral(final TokenType token) {
      return token.startsWith('/') || (this.scripting || XML_LITERALS) && token.startsWith('<');
   }

   protected boolean scanLiteral(final long token, final TokenType startTokenType, final Lexer.LineInfoReceiver lir) {
      if (!this.canStartLiteral(startTokenType)) {
         return false;
      } else if (this.stream.get(this.stream.last()) != token) {
         return false;
      } else {
         Lexer.State state = this.saveState();
         this.reset(Token.descPosition(token));
         if (this.ch0 == '/') {
            return this.scanRegEx();
         } else {
            if (this.ch0 == '<') {
               if (this.ch1 == '<') {
                  return this.scanHereString(lir, state);
               }

               if (Character.isJavaIdentifierStart(this.ch1)) {
                  return this.scanXMLLiteral();
               }
            }

            return false;
         }
      }
   }

   private boolean scanRegEx() {
      assert this.ch0 == '/';

      if (this.ch1 != '/' && this.ch1 != '*') {
         int start = this.position;
         this.skip(1);
         boolean inBrackets = false;

         while (!this.atEOF() && (this.ch0 != '/' || inBrackets) && !this.isEOL(this.ch0)) {
            if (this.ch0 == '\\') {
               this.skip(1);
               if (this.isEOL(this.ch0)) {
                  this.reset(start);
                  return false;
               }

               this.skip(1);
            } else {
               if (this.ch0 == '[') {
                  inBrackets = true;
               } else if (this.ch0 == ']') {
                  inBrackets = false;
               }

               this.skip(1);
            }
         }

         if (this.ch0 == '/') {
            this.skip(1);

            while (!this.atEOF() && Character.isJavaIdentifierPart(this.ch0) || this.ch0 == '\\' && this.ch1 == 'u') {
               this.skip(1);
            }

            this.add(TokenType.REGEX, start);
            return true;
         }

         this.reset(start);
      }

      return false;
   }

   private int consumeDigits(TokenType type, int base, boolean allowInitialSeparator, boolean allowSeparators) {
      int maxDigit = 0;
      boolean seenSeparator = false;
      boolean allowSeparator = allowInitialSeparator;

      while (true) {
         if (!allowSeparator || this.ch0 != '_') {
            int digit = convertDigit(this.ch0, base);
            if (digit == -1) {
               if (!seenSeparator) {
                  return maxDigit;
               }

               this.error(message("numeric.literal.trailing.separator"), type, this.position, this.limit - this.position);
            } else {
               seenSeparator = false;
               maxDigit = Math.max(maxDigit, digit);
               this.skip(1);
            }
         } else if (seenSeparator) {
            this.error(message("numeric.literal.multiple.separators"), type, this.position, this.limit - this.position);
         } else {
            seenSeparator = true;
            this.skip(1);
         }

         allowSeparator = allowSeparators;
      }
   }

   protected static int convertDigit(final char ch, final int base) {
      int digit;
      if ('0' <= ch && ch <= '9') {
         digit = ch - '0';
      } else if ('A' <= ch && ch <= 'Z') {
         digit = ch - 'A' + 10;
      } else {
         if ('a' > ch || ch > 'z') {
            return -1;
         }

         digit = ch - 'a' + 10;
      }

      return digit < base ? digit : -1;
   }

   private int hexSequence(final int length, final TokenType type) {
      int value = 0;

      for (int i = 0; i < length; i++) {
         int digit = convertDigit(this.ch0, 16);
         if (digit == -1) {
            this.error(message("invalid.hex"), type, this.position, this.limit - this.position);
            return i == 0 ? -1 : value;
         }

         value = digit | value << 4;
         this.skip(1);
      }

      return value;
   }

   private int varlenHexSequence(final TokenType type) {
      assert this.ch0 == '{';

      this.skip(1);
      int value = 0;

      for (int i = 0; !this.atEOF(); i++) {
         if (this.ch0 == '}') {
            if (i != 0) {
               this.skip(1);
               return value;
            }

            this.error(message("invalid.hex"), type, this.position, this.limit - this.position);
            this.skip(1);
            return -1;
         }

         int digit = convertDigit(this.ch0, 16);
         if (digit == -1) {
            this.error(message("invalid.hex"), type, this.position, this.limit - this.position);
            return i == 0 ? -1 : value;
         }

         value = digit | value << 4;
         if (value > 1114111) {
            this.error(message("invalid.hex"), type, this.position, this.limit - this.position);
            return -1;
         }

         this.skip(1);
      }

      return value;
   }

   private int unicodeEscapeSequence(final TokenType type) {
      return this.ch0 == 123 && this.isES6() ? this.varlenHexSequence(type) : this.hexSequence(4, type);
   }

   private int octalSequence() {
      int value = 0;

      for (int i = 0; i < 3; i++) {
         int digit = convertDigit(this.ch0, 8);
         if (digit == -1) {
            break;
         }

         value = digit | value << 3;
         this.skip(1);
         if (i == 1 && value >= 32) {
            break;
         }
      }

      return value;
   }

   public boolean checkIdentForKeyword(final long token, final String keyword) {
      int len = Token.descLength(token);
      int start = Token.descPosition(token);
      return len != keyword.length() ? false : this.content.regionMatches(start, keyword, 0, len);
   }

   private TruffleString valueOfIdent(final int start, final int length, final boolean convertUnicode) {
      int end = start + length;
      StringBuilder sb = new StringBuilder(length);
      int pos = start;

      while (pos < end) {
         char curCh0 = this.content.charAt(pos);
         if (convertUnicode && curCh0 == '\\' && this.charAt(pos + 1) == 'u') {
            int savePosition = this.position;
            this.reset(pos + 2);
            int ch = this.unicodeEscapeSequence(TokenType.IDENT);
            if (Character.isBmpCodePoint(ch) && this.isWhitespace((char)ch)) {
               return null;
            }

            if (ch < 0) {
               sb.append('\\');
               sb.append('u');
            } else {
               sb.appendCodePoint(ch);
            }

            pos = this.position;
            this.reset(savePosition);
         } else {
            sb.append(curCh0);
            pos++;
         }
      }

      return this.stringIntern(sb.toString());
   }

   private void scanIdentifierOrKeyword() {
      int start = this.position;
      int length = this.scanIdentifier();
      TokenType type = TokenLookup.lookupKeyword(this.content, start, length);
      if (type == TokenType.FUNCTION && this.pauseOnFunctionBody) {
         this.pauseOnNextLeftBrace = true;
      }

      this.add(type, start);
   }

   private TruffleString valueOfString(final int start, final int length, final boolean strict) {
      int savePosition = this.position;
      int end = start + length;
      this.reset(start);
      StringBuilder sb = new StringBuilder(length);

      while (this.position < end) {
         if (this.ch0 == '\\') {
            this.skip(1);
            char next = this.ch0;
            int afterSlash = this.position;
            this.skip(1);
            switch (next) {
               case '\n':
               case '\u2028':
               case '\u2029':
                  break;
               case '\r':
                  if (this.ch0 == '\n') {
                     this.skip(1);
                  }
                  break;
               case '"':
                  sb.append('"');
                  break;
               case '\'':
                  sb.append('\'');
                  break;
               case '0':
               case '1':
               case '2':
               case '3':
               case '4':
               case '5':
               case '6':
               case '7':
                  if (strict && (next != '0' || this.ch0 >= '0' && this.ch0 <= '9')) {
                     this.error(message("strict.no.octal"), TokenType.STRING, this.position, this.limit - this.position);
                  }

                  this.reset(afterSlash);
                  int chxx = this.octalSequence();
                  if (chxx < 0) {
                     sb.append('\\');
                     sb.append('x');
                  } else {
                     sb.append((char)chxx);
                  }
                  break;
               case '8':
               case '9':
                  if (strict) {
                     this.error(message("strict.no.nonoctaldecimal"), TokenType.STRING, this.position, this.limit - this.position);
                  }

                  sb.append(next);
                  break;
               case '\\':
                  sb.append('\\');
                  break;
               case 'b':
                  sb.append('\b');
                  break;
               case 'f':
                  sb.append('\f');
                  break;
               case 'n':
                  sb.append('\n');
                  break;
               case 'r':
                  sb.append('\r');
                  break;
               case 't':
                  sb.append('\t');
                  break;
               case 'u':
                  int chx = this.unicodeEscapeSequence(TokenType.STRING);
                  if (chx < 0) {
                     sb.append('\\');
                     sb.append('u');
                  } else {
                     if (chx <= 65535 && Character.isSurrogate((char)chx)) {
                        sb.append((char)chx);
                        continue;
                     }

                     sb.appendCodePoint(chx);
                  }
                  break;
               case 'v':
                  sb.append('\u000b');
                  break;
               case 'x':
                  int ch = this.hexSequence(2, TokenType.STRING);
                  if (ch < 0) {
                     sb.append('\\');
                     sb.append('x');
                  } else {
                     sb.append((char)ch);
                  }
                  break;
               default:
                  sb.append(next);
            }
         } else if (this.ch0 == '\r') {
            sb.append('\n');
            this.skip(this.ch1 == '\n' ? 2 : 1);
         } else {
            sb.append(this.ch0);
            this.skip(1);
         }
      }

      this.reset(savePosition);
      return this.stringIntern(sb.toString());
   }

   protected void scanString(final boolean add) {
      TokenType type = TokenType.STRING;
      char quote = this.ch0;
      this.skip(1);
      Lexer.State stringState = this.saveState();

      while (!this.atEOF() && this.ch0 != quote && !isStringLineTerminator(this.ch0)) {
         if (this.ch0 == '\\') {
            type = TokenType.ESCSTRING;
            this.skip(1);
            if (!this.isEscapeCharacter(this.ch0)) {
               this.error(message("invalid.escape.char"), TokenType.STRING, this.position, this.limit - this.position);
            }

            if (this.isEOL(this.ch0)) {
               this.skipEOL(false);
               continue;
            }
         }

         this.skip(1);
      }

      if (this.ch0 == quote) {
         this.skip(1);
      } else {
         this.error(message("missing.close.quote"), TokenType.STRING, this.position, this.limit - this.position);
      }

      if (add) {
         stringState.setLimit(this.position - 1);
         if (this.scripting && !stringState.isEmpty()) {
            switch (quote) {
               case '"':
                  this.editString(type, stringState);
                  break;
               case '\'':
                  this.add(type, stringState.position, stringState.getLimit());
                  break;
               case '`':
                  this.add(TokenType.EXECSTRING, stringState.position, stringState.getLimit());
                  this.add(TokenType.LBRACE, stringState.position, stringState.position);
                  this.editString(type, stringState);
                  this.add(TokenType.RBRACE, stringState.getLimit(), stringState.getLimit());
            }
         } else {
            this.add(type, stringState.position, stringState.getLimit());
         }
      }
   }

   private void scanTemplate() {
      assert this.ch0 == '`';

      this.skip(1);
      this.scanTemplateString(TokenType.TEMPLATE);
   }

   protected final void scanTemplateSpan() {
      this.scanTemplateString(TokenType.TEMPLATE_MIDDLE);
   }

   private void scanTemplateString(TokenType type) {
      assert type == TokenType.TEMPLATE || type == TokenType.TEMPLATE_MIDDLE;

      Lexer.State stringState = this.saveState();

      while (!this.atEOF()) {
         if (this.ch0 == '`') {
            this.skip(1);
            stringState.setLimit(this.position - 1);
            this.add(type == TokenType.TEMPLATE ? type : TokenType.TEMPLATE_TAIL, stringState.position, stringState.getLimit());
            return;
         }

         if (this.ch0 == '$' && this.ch1 == '{') {
            this.skip(2);
            stringState.setLimit(this.position - 2);
            this.add(type == TokenType.TEMPLATE ? TokenType.TEMPLATE_HEAD : type, stringState.position, stringState.getLimit());
            return;
         }

         if (this.ch0 == '\\') {
            this.skip(1);
            if (!this.isEscapeCharacter(this.ch0)) {
               this.error(message("invalid.escape.char"), TokenType.TEMPLATE, this.position, this.limit - this.position);
            }

            if (this.isEOL(this.ch0)) {
               this.skipEOL(false);
               continue;
            }
         } else if (this.isEOL(this.ch0)) {
            this.skipEOL(false);
            continue;
         }

         this.skip(1);
      }

      this.error(message("missing.close.quote"), TokenType.TEMPLATE, this.position, this.limit - this.position);
   }

   protected boolean isEscapeCharacter(final char ch) {
      return true;
   }

   private static String removeUnderscores(String string) {
      int pos = string.indexOf(95);
      if (pos < 0) {
         return string;
      } else {
         int lastPos = 0;

         StringBuilder sb;
         for (sb = new StringBuilder(string.length()); pos >= 0; pos = string.indexOf(95, lastPos)) {
            sb.append(string, lastPos, pos);
            lastPos = pos + 1;
         }

         sb.append(string, lastPos, string.length());
         return sb.toString();
      }
   }

   private static Number valueOf(final String string, final int radix) throws NumberFormatException {
      String valueString = removeUnderscores(string);

      try {
         long value = Long.parseLong(valueString, radix);
         return (Number)(value >= -2147483648L && value <= 2147483647L ? (int)value : value);
      } catch (NumberFormatException var9) {
         if (radix == 10) {
            return Double.parseDouble(valueString);
         } else if (radix == 16 && valueString.length() >= 15) {
            return new BigInteger(valueString, 16).doubleValue();
         } else {
            double valuex = 0.0;

            for (int i = 0; i < valueString.length(); i++) {
               char ch = valueString.charAt(i);
               int digit = convertDigit(ch, radix);
               valuex *= radix;
               valuex += digit;
            }

            return valuex;
         }
      }
   }

   private static BigInteger valueOfBigInt(final String string) {
      String valueString = removeUnderscores(string);
      if (valueString.length() > 2 && valueString.charAt(0) == '0') {
         switch (valueString.charAt(1)) {
            case 'B':
            case 'b':
               return new BigInteger(valueString.substring(2), 2);
            case 'O':
            case 'o':
               return new BigInteger(valueString.substring(2), 8);
            case 'X':
            case 'x':
               return new BigInteger(valueString.substring(2), 16);
            default:
               return new BigInteger(valueString, 10);
         }
      } else {
         return new BigInteger(valueString, 10);
      }
   }

   protected void scanNumber() {
      int start = this.position;
      TokenType type = TokenType.DECIMAL;
      int digit = convertDigit(this.ch0, 10);
      boolean numericSeparators = this.isES2020();
      if (digit == 0 && (this.ch1 == 'x' || this.ch1 == 'X') && convertDigit(this.ch2, 16) != -1) {
         this.skip(3);
         type = TokenType.HEXADECIMAL;
         this.consumeDigits(type, 16, numericSeparators, numericSeparators);
      } else if (digit == 0 && this.isES6() && (this.ch1 == 'o' || this.ch1 == 'O') && convertDigit(this.ch2, 8) != -1) {
         this.skip(3);
         type = TokenType.OCTAL;
         this.consumeDigits(type, 8, numericSeparators, numericSeparators);
      } else if (digit == 0 && this.isES6() && (this.ch1 == 'b' || this.ch1 == 'B') && convertDigit(this.ch2, 2) != -1) {
         this.skip(3);
         type = TokenType.BINARY_NUMBER;
         this.consumeDigits(type, 2, numericSeparators, numericSeparators);
      } else {
         boolean octal = digit == 0;
         if (digit != -1) {
            this.skip(1);
         }

         boolean allowSeparators = numericSeparators && !octal;
         int maxDigit = this.consumeDigits(type, 10, allowSeparators, allowSeparators);
         if (octal && maxDigit >= 8) {
            octal = false;
            type = TokenType.NON_OCTAL_DECIMAL;
         }

         if (octal && this.position - start > 1) {
            type = TokenType.OCTAL_LEGACY;
         } else if (this.ch0 == '.' || this.ch0 == 'E' || this.ch0 == 'e') {
            type = TokenType.FLOATING;
            if (this.ch0 == '.') {
               this.skip(1);
               this.consumeDigits(type, 10, false, numericSeparators);
            }

            if (this.ch0 == 'E' || this.ch0 == 'e') {
               this.skip(1);
               if (this.ch0 == '+' || this.ch0 == '-') {
                  this.skip(1);
               }

               this.consumeDigits(type, 10, false, numericSeparators);
            }
         }
      }

      if (this.ch0 == 'n'
         && this.allowBigInt
         && (type == TokenType.DECIMAL || type == TokenType.BINARY_NUMBER || type == TokenType.OCTAL || type == TokenType.HEXADECIMAL)) {
         this.skip(1);
         type = TokenType.BIGINT;
      }

      if (Character.isJavaIdentifierStart(this.ch0)) {
         this.error(message("missing.space.after.number"), type, this.position, 1);
      }

      this.add(type, start);
   }

   Lexer.XMLToken valueOfXML(final int start, final int length) {
      return new Lexer.XMLToken(this.stringIntern(this.source.getString(start, length)));
   }

   private boolean scanXMLLiteral() {
      assert this.ch0 == '<' && Character.isJavaIdentifierStart(this.ch1);

      if (!XML_LITERALS) {
         return false;
      } else {
         int start = this.position;
         int openCount = 0;

         do {
            if (this.ch0 == '<') {
               if (this.ch1 == '/' && Character.isJavaIdentifierStart(this.ch2)) {
                  this.skip(3);
                  openCount--;
               } else if (Character.isJavaIdentifierStart(this.ch1)) {
                  this.skip(2);
                  openCount++;
               } else if (this.ch1 == '?') {
                  this.skip(2);
               } else {
                  if (this.ch1 != '!' || this.ch2 != '-' || this.ch3 != '-') {
                     this.reset(start);
                     return false;
                  }

                  this.skip(4);
               }

               while (!this.atEOF() && this.ch0 != '>') {
                  if (this.ch0 == '/' && this.ch1 == '>') {
                     openCount--;
                     this.skip(1);
                     break;
                  }

                  if (this.ch0 != '"' && this.ch0 != '\'') {
                     this.skip(1);
                  } else {
                     this.scanString(false);
                  }
               }

               if (this.ch0 != '>') {
                  this.reset(start);
                  return false;
               }

               this.skip(1);
            } else {
               if (this.atEOF()) {
                  this.reset(start);
                  return false;
               }

               this.skip(1);
            }
         } while (openCount > 0);

         this.add(TokenType.XML, start);
         return true;
      }
   }

   private int scanIdentifier() {
      int start = this.position;
      if (this.ch0 == '\\' && this.ch1 == 'u') {
         this.skip(2);
         int codePoint = this.unicodeEscapeSequence(TokenType.IDENT);
         if (!IdentUtils.isIdentifierStart(codePoint)) {
            this.error(message("illegal.identifier.character"), TokenType.IDENT, start, this.position - start);
         }
      } else if (IdentUtils.isIdentifierStart(this.ch0)) {
         this.skip(1);
      } else {
         if (!Character.isHighSurrogate(this.ch0)
            || !Character.isLowSurrogate(this.ch1)
            || !IdentUtils.isIdentifierStart(Character.toCodePoint(this.ch0, this.ch1))) {
            return 0;
         }

         this.skip(2);
      }

      while (!this.atEOF()) {
         if (this.ch0 == '\\' && this.ch1 == 'u') {
            this.skip(2);
            int codePoint = this.unicodeEscapeSequence(TokenType.IDENT);
            if (!IdentUtils.isIdentifierPart(codePoint)) {
               this.error(message("illegal.identifier.character"), TokenType.IDENT, start, this.position - start);
            }
         } else if (IdentUtils.isIdentifierPart(this.ch0)) {
            this.skip(1);
         } else {
            if (!Character.isHighSurrogate(this.ch0)
               || !Character.isLowSurrogate(this.ch1)
               || !IdentUtils.isIdentifierPart(Character.toCodePoint(this.ch0, this.ch1))) {
               break;
            }

            this.skip(2);
         }
      }

      return this.position - start;
   }

   private boolean identifierEqual(final int aStart, final int aLength, final int bStart, final int bLength) {
      return aLength == bLength ? this.content.regionMatches(aStart, this.content, bStart, aLength) : false;
   }

   private static boolean isPrivateIdentifierStart(char ch) {
      return ch == '#';
   }

   private void scanPrivateIdentifier() {
      int start = this.position;

      assert isPrivateIdentifierStart(this.ch0);

      this.skip(1);
      if (this.scanIdentifier() != 0) {
         this.add(TokenType.PRIVATE_IDENT, start);
      } else {
         this.add(TokenType.ERROR, start);
      }
   }

   private boolean hasHereMarker(final int identStart, final int identLength) {
      this.skipWhitespace(false);
      return this.identifierEqual(identStart, identLength, this.position, this.scanIdentifier());
   }

   private void editString(final TokenType stringType, final Lexer.State stringState) {
      Lexer.EditStringLexer lexer = new Lexer.EditStringLexer(this, stringType, stringState);
      lexer.lexify();
      this.last = stringType;
   }

   private boolean scanHereString(final Lexer.LineInfoReceiver lir, final Lexer.State oldState) {
      assert this.ch0 == '<' && this.ch1 == '<';

      if (this.scripting) {
         Lexer.State saved = this.saveState();
         boolean excludeLastEOL = this.ch2 != '<';
         if (excludeLastEOL) {
            this.skip(2);
         } else {
            this.skip(3);
         }

         char quoteChar = this.ch0;
         boolean noStringEditing = quoteChar == '"' || quoteChar == '\'';
         if (noStringEditing) {
            this.skip(1);
         }

         int identStart = this.position;
         int identLength = this.scanIdentifier();
         if (noStringEditing) {
            if (this.ch0 != quoteChar) {
               this.error(message("here.non.matching.delimiter"), this.last, this.position, 0);
               this.restoreState(saved);
               return false;
            }

            this.skip(1);
         }

         if (identLength == 0) {
            this.restoreState(saved);
            return false;
         } else {
            Lexer.State restState = this.saveState();
            int lastLine = this.line;
            this.skipLine(false);
            lastLine++;
            int lastLinePosition = this.position;
            restState.setLimit(this.position);
            if (oldState.position > this.position) {
               this.restoreState(oldState);
               this.skipLine(false);
            }

            Lexer.State stringState = this.saveState();

            int stringEnd;
            for (stringEnd = this.position; !this.atEOF(); stringEnd = this.position) {
               this.skipWhitespace(false);
               if (this.hasHereMarker(identStart, identLength)) {
                  break;
               }

               this.skipLine(false);
               lastLine++;
               lastLinePosition = this.position;
            }

            lir.lineInfo(lastLine, lastLinePosition);
            stringState.setLimit(stringEnd);
            if (!stringState.isEmpty() && !this.atEOF()) {
               if (excludeLastEOL) {
                  if (this.content.charAt(stringEnd - 1) == '\n') {
                     stringEnd--;
                  }

                  if (this.content.charAt(stringEnd - 1) == '\r') {
                     stringEnd--;
                  }

                  stringState.setLimit(stringEnd);
               }

               if (!noStringEditing && !stringState.isEmpty()) {
                  this.editString(TokenType.STRING, stringState);
               } else {
                  this.add(TokenType.STRING, stringState.position, stringState.getLimit());
               }

               Lexer restLexer = new Lexer(this, restState);
               restLexer.lexify();
               return true;
            } else {
               this.error(message("here.missing.end.marker", this.source.getString(identStart, identLength)), this.last, this.position, 0);
               this.restoreState(saved);
               return false;
            }
         }
      } else {
         return false;
      }
   }

   public void lexify() {
      label142:
      while (true) {
         while (!this.stream.isFull() || this.nested) {
            this.skipWhitespace(true);
            if (this.atEOF()) {
               if (!this.nested) {
                  this.add(TokenType.EOF, this.position);
               }
               break;
            } else {
               if (this.ch0 == '/') {
                  if (this.ch1 == '/') {
                     this.skipSingleLineComment();
                     continue;
                  }

                  if (this.ch1 == '*') {
                     this.skipMultiLineComment();
                     continue;
                  }
               } else {
                  if (this.ch0 == '#' && (this.ch1 == '!' && this.position == 0 && this.shebang || this.scripting)) {
                     this.skipShebang();
                     continue label142;
                  }

                  if (!this.isModule) {
                     if (this.ch0 == '<' && this.ch1 == '!' && this.ch2 == '-' && this.ch3 == '-') {
                        this.skipSingleLineHTMLOpenComment();
                        continue;
                     }

                     if (this.ch0 == '-' && this.ch1 == '-' && this.ch2 == '>' && this.seenEOL() && this.linePosition > 0) {
                        this.skipSingleLineHTMLCloseComment();
                        continue;
                     }
                  }
               }

               if (this.ch0 == '.' && convertDigit(this.ch1, 10) != -1) {
                  this.scanNumber();
               } else {
                  TokenType type;
                  if ((type = TokenLookup.lookupOperator(this.ch0, this.ch1, this.ch2, this.ch3, this.ecmaScriptVersion)) != null) {
                     int typeLength = type.getLength();
                     this.skip(typeLength);
                     this.add(type, this.position - typeLength);
                     if (!this.canStartLiteral(type)) {
                        if (type == TokenType.LBRACE && this.pauseOnNextLeftBrace) {
                           this.pauseOnNextLeftBrace = false;
                        } else if (type != TokenType.RBRACE || !this.pauseOnRightBrace) {
                           continue;
                        }
                     }
                     break;
                  } else if (IdentUtils.isIdentifierStart(
                        Character.isHighSurrogate(this.ch0) && Character.isLowSurrogate(this.ch1) ? Character.toCodePoint(this.ch0, this.ch1) : this.ch0
                     )
                     || this.ch0 == '\\' && this.ch1 == 'u') {
                     this.scanIdentifierOrKeyword();
                  } else if (this.isStringDelimiter(this.ch0)) {
                     this.scanString(true);
                  } else if ('0' <= this.ch0 && this.ch0 <= '9') {
                     this.scanNumber();
                  } else {
                     if (isTemplateDelimiter(this.ch0) && this.isES6()) {
                        this.scanTemplate();
                        break;
                     }

                     if (isTemplateDelimiter(this.ch0) && this.scripting) {
                        this.scanString(true);
                     } else if (isPrivateIdentifierStart(this.ch0)) {
                        this.scanPrivateIdentifier();
                     } else {
                        this.skip(1);
                        this.add(TokenType.ERROR, this.position - 1);
                     }
                  }
               }
            }
         }

         return;
      }
   }

   Object getValueOf(final long token, final boolean strict) {
      return this.getValueOf(token, strict, true);
   }

   Object getValueOf(final long token, final boolean strict, final boolean convertUnicode) {
      int start = Token.descPosition(token);
      int len = Token.descLength(token);
      switch (Token.descType(token)) {
         case DECIMAL:
         case NON_OCTAL_DECIMAL:
            return valueOf(this.source.getString(start, len), 10);
         case HEXADECIMAL:
            return valueOf(this.source.getString(start + 2, len - 2), 16);
         case OCTAL_LEGACY:
            return valueOf(this.source.getString(start, len), 8);
         case OCTAL:
            return valueOf(this.source.getString(start + 2, len - 2), 8);
         case BINARY_NUMBER:
            return valueOf(this.source.getString(start + 2, len - 2), 2);
         case BIGINT:
            return valueOfBigInt(this.source.getString(start, len - 1));
         case FLOATING:
            String str = removeUnderscores(this.source.getString(start, len));
            double value = Double.parseDouble(str);
            if (str.indexOf(46) != -1) {
               return value;
            } else if (JSType.isStrictlyRepresentableAsInt(value)) {
               return (int)value;
            } else {
               if (JSType.isStrictlyRepresentableAsLong(value)) {
                  return (long)value;
               }

               return value;
            }
         case STRING:
            return this.stringIntern(this.source.getString(start, len));
         case ESCSTRING:
            return this.valueOfString(start, len, strict);
         case IDENT:
         case PRIVATE_IDENT:
            return this.valueOfIdent(start, len, convertUnicode);
         case REGEX:
            return this.valueOfPattern(start, len);
         case TEMPLATE:
         case TEMPLATE_HEAD:
         case TEMPLATE_MIDDLE:
         case TEMPLATE_TAIL:
            return this.valueOfString(start, len, true);
         case XML:
            return this.valueOfXML(start, len);
         case DIRECTIVE_COMMENT:
            return this.source.getString(start, len);
         default:
            return null;
      }
   }

   TruffleString valueOfTaggedTemplateString(final long token) {
      int savePosition = this.position;

      Object var5;
      try {
         return this.valueOfString(Token.descPosition(token), Token.descLength(token), true);
      } catch (ParserException var9) {
         var5 = null;
      } finally {
         this.reset(savePosition);
      }

      return (TruffleString)var5;
   }

   public TruffleString valueOfRawString(final long token) {
      int start = Token.descPosition(token);
      int length = Token.descLength(token);
      int savePosition = this.position;
      int end = start + length;
      this.reset(start);
      StringBuilder sb = new StringBuilder(length);

      while (this.position < end) {
         if (this.ch0 == '\r') {
            sb.append('\n');
            this.skip(this.ch1 == '\n' ? 2 : 1);
         } else {
            sb.append(this.ch0);
            this.skip(1);
         }
      }

      this.reset(savePosition);
      return this.stringIntern(sb.toString());
   }

   public TruffleString stringIntern(TruffleString candidate) {
      TruffleString interned = this.internedStrings.putIfAbsent(candidate.toJavaStringUncached(), candidate);
      return interned == null ? candidate : interned;
   }

   public TruffleString stringIntern(String candidate) {
      TruffleString interned = this.internedStrings.get(candidate);
      if (interned == null) {
         interned = ParserStrings.fromJavaString(candidate);
         this.internedStrings.put(candidate, interned);
      }

      return interned;
   }

   protected static String message(final String msgId, final String... args) {
      return ECMAErrors.getMessage("lexer.error." + msgId, args);
   }

   protected void error(final String message, final TokenType type, final int start, final int length) throws ParserException {
      long token = Token.toDesc(type, start, length);
      int pos = Token.descPosition(token);
      int lineNum = this.source.getLine(pos);
      int columnNum = this.source.getColumn(pos);
      throw new ParserException(JSErrorType.SyntaxError, message, this.source, lineNum, columnNum, token);
   }

   private static class EditStringLexer extends Lexer {
      final TokenType stringType;

      EditStringLexer(final Lexer lexer, final TokenType stringType, final Lexer.State stringState) {
         super(lexer, stringState);
         this.stringType = stringType;
      }

      @Override
      public void lexify() {
         int stringStart = this.position;
         boolean primed = false;

         while (!this.atEOF()) {
            if (this.ch0 == '\\' && this.stringType == TokenType.ESCSTRING) {
               this.skip(2);
            } else if (this.ch0 == '$' && this.ch1 == '{') {
               if (!primed || stringStart != this.position) {
                  if (primed) {
                     this.add(TokenType.ADD, stringStart, stringStart + 1);
                  }

                  this.add(this.stringType, stringStart, this.position);
                  primed = true;
               }

               this.skip(2);
               Lexer.State expressionState = this.saveState();
               int braceCount = 1;

               while (true) {
                  label74: {
                     if (!this.atEOF()) {
                        if (this.ch0 != '}') {
                           if (this.ch0 == '{') {
                              braceCount++;
                           }
                           break label74;
                        }

                        if (--braceCount != 0) {
                           break label74;
                        }
                     }

                     if (braceCount != 0) {
                        this.error(Lexer.message("edit.string.missing.brace"), TokenType.LBRACE, expressionState.position - 1, 1);
                     }

                     expressionState.setLimit(this.position);
                     this.skip(1);
                     stringStart = this.position;
                     this.add(TokenType.ADD, expressionState.position, expressionState.position + 1);
                     this.add(TokenType.LPAREN, expressionState.position, expressionState.position + 1);
                     Lexer lexer = new Lexer(this, expressionState);
                     lexer.lexify();
                     this.add(TokenType.RPAREN, this.position - 1, this.position);
                     break;
                  }

                  this.skip(1);
               }
            } else {
               this.skip(1);
            }
         }

         if (stringStart != this.limit) {
            if (primed) {
               this.add(TokenType.ADD, stringStart, stringStart + 1);
            }

            this.add(this.stringType, stringStart, this.limit);
         }
      }
   }

   public abstract static class LexerToken {
      private final TruffleString expression;

      protected LexerToken(final TruffleString expression) {
         this.expression = expression;
      }

      public String getExpression() {
         return this.expression.toJavaStringUncached();
      }

      public TruffleString getExpressionTS() {
         return this.expression;
      }
   }

   protected interface LineInfoReceiver {
      void lineInfo(int line, int linePosition);
   }

   public static class RegexToken extends Lexer.LexerToken {
      private final TruffleString options;

      public RegexToken(final TruffleString expression, final TruffleString options) {
         super(expression);
         this.options = options;
      }

      public String getOptions() {
         return this.options.toJavaStringUncached();
      }

      public TruffleString getOptionsTS() {
         return this.options;
      }

      @Override
      public String toString() {
         return "/" + this.getExpression() + "/" + this.options;
      }
   }

   static class State extends Scanner.State {
      public final int pendingLine;
      public final int linePosition;
      public final TokenType last;

      State(final int position, final int limit, final int line, final int pendingLine, final int linePosition, final TokenType last) {
         super(position, limit, line);
         this.pendingLine = pendingLine;
         this.linePosition = linePosition;
         this.last = last;
      }
   }

   public static class XMLToken extends Lexer.LexerToken {
      public XMLToken(final TruffleString expression) {
         super(expression);
      }
   }
}
