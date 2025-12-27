package com.oracle.truffle.js.builtins.helper;

import com.oracle.js.parser.ECMAErrors;
import com.oracle.js.parser.JSErrorType;
import com.oracle.js.parser.JSType;
import com.oracle.js.parser.ParserException;
import com.oracle.js.parser.Source;
import com.oracle.js.parser.Token;
import com.oracle.js.parser.TokenType;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.api.strings.TruffleStringBuilder;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.array.ScriptArray;
import com.oracle.truffle.js.runtime.builtins.JSAbstractArray;
import com.oracle.truffle.js.runtime.builtins.JSArray;
import com.oracle.truffle.js.runtime.builtins.JSOrdinary;
import com.oracle.truffle.js.runtime.objects.JSAttributes;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObjectUtil;
import com.oracle.truffle.js.runtime.objects.Null;

public class NashornJSONParser {
   private final TruffleString source;
   private final JSContext context;
   private final int length;
   private int pos = 0;
   private static final int EOF = -1;
   private static final TruffleString TRUE = Strings.constant("true");
   private static final TruffleString FALSE = Strings.constant("false");
   private static final TruffleString NULL = Strings.constant("null");
   private static final String MSG_INVALID_ESCAPE_CHAR = "invalid.escape.char";
   private static final String MSG_INVALID_HEX = "invalid.hex";
   private static final String MSG_JSON_INVALID_NUMBER = "json.invalid.number";
   private static final String MSG_LEXER_ERROR = "lexer.error.";
   private static final String MSG_MISSING_CLOSE_QUOTE = "missing.close.quote";
   private static final String MSG_PARSER_ERROR = "parser.error.";
   private static final String MSG_SYNTAX_ERROR_INVALID_JSON = "syntax.error.invalid.json";
   private static final String MSG_TRAILING_COMMA_IN_JSON = "trailing.comma.in.json";
   private static final String ERR_COLON = ":";
   private static final String ERR_COMMA_OR_RBRACE = ", or }";
   private static final String ERR_COMMA_OR_RBRACKET = ", or ]";
   private static final String ERR_EOF_STR = "eof";
   private static final String ERR_EXPECTED = "expected";
   private static final String ERR_IDENT = "ident";
   private static final String ERR_JSON_LITERAL = "json literal";
   private static final String ERR_STRING_CONTAINS_CONTROL_CHARACTER = "String contains control character";
   private static final int STATE_EMPTY = 0;
   private static final int STATE_ELEMENT_PARSED = 1;
   private static final int STATE_COMMA_PARSED = 2;

   public NashornJSONParser(final TruffleString source, final JSContext context) {
      this.source = source;
      this.context = context;
      this.length = Strings.length(source);
   }

   public Object parse() {
      Object value = this.parseLiteral();
      this.skipWhiteSpace();
      if (this.pos < this.length) {
         throw this.expectedError(this.pos, "eof", toString(this.peek()));
      } else {
         return value;
      }
   }

   private Object parseLiteral() {
      this.skipWhiteSpace();
      int c = this.peek();
      if (c == -1) {
         throw this.expectedError(this.pos, "json literal", "eof");
      } else {
         switch (c) {
            case 34:
               return this.parseString();
            case 91:
               return this.parseArray();
            case 102:
               return this.parseKeyword(FALSE, Boolean.FALSE);
            case 110:
               return this.parseKeyword(NULL, Null.instance);
            case 116:
               return this.parseKeyword(TRUE, Boolean.TRUE);
            case 123:
               return this.parseObject();
            default:
               if (isDigit(c) || c == 45) {
                  return this.parseNumber();
               } else if (c == 46) {
                  throw this.numberError(this.pos);
               } else {
                  throw this.expectedError(this.pos, "json literal", toString(c));
               }
         }
      }
   }

   private Object parseObject() {
      JSDynamicObject jsobject = JSOrdinary.create(this.context, JSRealm.get(null));
      int state = 0;

      assert this.peek() == 123;

      this.pos++;

      while (this.pos < this.length) {
         this.skipWhiteSpace();
         int c = this.peek();
         switch (c) {
            case 34:
               if (state == 1) {
                  throw this.expectedError(this.pos, ", or }", toString(c));
               }

               Object id = this.parseString();
               this.expectColon();
               Object value = this.parseLiteral();
               this.addObjectProperty(jsobject, id, value);
               state = 1;
               break;
            case 44:
               if (state != 1) {
                  throw this.trailingCommaError(this.pos, toString(c));
               }

               state = 2;
               this.pos++;
               break;
            case 125:
               if (state == 2) {
                  throw this.trailingCommaError(this.pos, toString(c));
               }

               this.pos++;
               return jsobject;
            default:
               throw this.expectedError(this.pos, ", or }", toString(c));
         }
      }

      throw this.expectedError(this.pos, ", or }", "eof");
   }

   private void addObjectProperty(final JSDynamicObject object, final Object idStr, final Object value) {
      JSObjectUtil.defineDataProperty(this.context, object, idStr, value, JSAttributes.getDefault());
   }

   private void expectColon() {
      this.skipWhiteSpace();
      int n = this.next();
      if (n != 58) {
         throw this.expectedError(this.pos - 1, ":", toString(n));
      }
   }

   private Object parseArray() {
      JSDynamicObject jsarray = JSArray.createEmptyZeroLength(this.context, JSRealm.get(null));
      ScriptArray arrayData = JSAbstractArray.arrayGetArrayType(jsarray);
      int state = 0;

      assert this.peek() == 91;

      this.pos++;

      while (this.pos < this.length) {
         this.skipWhiteSpace();
         int c = this.peek();
         switch (c) {
            case 44:
               if (state != 1) {
                  throw this.trailingCommaError(this.pos, toString(c));
               }

               state = 2;
               this.pos++;
               break;
            case 93:
               if (state == 2) {
                  throw this.trailingCommaError(this.pos, toString(c));
               }

               this.pos++;
               return jsarray;
            default:
               if (state == 1) {
                  throw this.expectedError(this.pos, ", or ]", toString(c));
               }

               long index = arrayData.length(jsarray);
               arrayData = arrayData.setElement(jsarray, index, this.parseLiteral(), true);
               JSAbstractArray.arraySetArrayType(jsarray, arrayData);
               state = 1;
         }
      }

      throw this.expectedError(this.pos, ", or ]", "eof");
   }

   private TruffleString parseString() {
      int start = ++this.pos;
      TruffleStringBuilder sb = null;

      while (this.pos < this.length) {
         int c = this.next();
         if (c <= 31) {
            throw this.syntaxError(this.pos, "String contains control character");
         }

         if (c == 92) {
            if (sb == null) {
               sb = Strings.builderCreate(this.pos - start + 16);
            }

            Strings.builderAppend(sb, this.source, start, this.pos - 1);
            Strings.builderAppend(sb, this.parseEscapeSequence());
            start = this.pos;
         } else if (c == 34) {
            if (sb != null) {
               Strings.builderAppend(sb, this.source, start, this.pos - 1);
               return Strings.builderToString(sb);
            }

            return Strings.substring(this.context, this.source, start, this.pos - 1 - start);
         }
      }

      throw this.error(lexerMessage("missing.close.quote"), this.pos, this.length);
   }

   private char parseEscapeSequence() {
      int c = this.next();
      switch (c) {
         case 34:
            return '"';
         case 47:
            return '/';
         case 92:
            return '\\';
         case 98:
            return '\b';
         case 102:
            return '\f';
         case 110:
            return '\n';
         case 114:
            return '\r';
         case 116:
            return '\t';
         case 117:
            return this.parseUnicodeEscape();
         default:
            throw this.error(lexerMessage("invalid.escape.char"), this.pos - 1, this.length);
      }
   }

   private char parseUnicodeEscape() {
      return (char)(this.parseHexDigit() << 12 | this.parseHexDigit() << 8 | this.parseHexDigit() << 4 | this.parseHexDigit());
   }

   private int parseHexDigit() {
      int c = this.next();
      if (c >= 48 && c <= 57) {
         return c - 48;
      } else if (c >= 65 && c <= 70) {
         return c + 10 - 65;
      } else if (c >= 97 && c <= 102) {
         return c + 10 - 97;
      } else {
         throw this.error(lexerMessage("invalid.hex"), this.pos - 1, this.length);
      }
   }

   private static boolean isDigit(final int c) {
      return c >= 48 && c <= 57;
   }

   private void skipDigits() {
      while (this.pos < this.length) {
         int c = this.peek();
         if (isDigit(c)) {
            this.pos++;
            continue;
         }
         break;
      }
   }

   private Number parseNumber() {
      int start = this.pos;
      int c = this.next();
      if (c == 45) {
         c = this.next();
      }

      if (!isDigit(c)) {
         throw this.numberError(start);
      } else {
         if (c != 48) {
            this.skipDigits();
         }

         if (this.peek() == 46) {
            this.pos++;
            if (!isDigit(this.next())) {
               throw this.numberError(this.pos - 1);
            }

            this.skipDigits();
         }

         c = this.peek();
         if (c == 101 || c == 69) {
            this.pos++;
            c = this.next();
            if (c == 45 || c == 43) {
               c = this.next();
            }

            if (!isDigit(c)) {
               throw this.numberError(this.pos - 1);
            }

            this.skipDigits();
         }

         double d;
         try {
            d = Strings.parseDouble(Strings.lazySubstring(this.source, start, this.pos - start));
         } catch (TruffleString.NumberFormatException var6) {
            throw this.numberError(start);
         }

         if (JSType.isRepresentableAsInt(d)) {
            return (int)d;
         } else {
            return (Number)(JSType.isRepresentableAsLong(d) ? (long)d : d);
         }
      }
   }

   private Object parseKeyword(final TruffleString keyword, final Object value) {
      if (!Strings.regionEquals(this.source, this.pos, keyword, 0, Strings.length(keyword))) {
         throw this.expectedError(this.pos, "json literal", "ident");
      } else {
         this.pos = this.pos + Strings.length(keyword);
         return value;
      }
   }

   private int peek() {
      return this.pos >= this.length ? -1 : Strings.charAt(this.source, this.pos);
   }

   private int next() {
      int next = this.peek();
      this.pos++;
      return next;
   }

   private void skipWhiteSpace() {
      while (this.pos < this.length) {
         switch (this.peek()) {
            case 9:
            case 10:
            case 13:
            case 32:
               this.pos++;
               break;
            default:
               return;
         }
      }
   }

   private static String toString(final int c) {
      return c == -1 ? "eof" : String.valueOf((char)c);
   }

   ParserException error(final String message, final int start, final int length) throws ParserException {
      long token = Token.toDesc(TokenType.STRING, start, length);
      int pos = Token.descPosition(token);
      Source src = Source.sourceFor("<json>", Strings.toJavaString(this.source));
      int lineNum = src.getLine(pos);
      int columnNum = src.getColumn(pos);
      return new ParserException(JSErrorType.SyntaxError, message, src, lineNum, columnNum, token);
   }

   private ParserException error(final String message, final int start) {
      return this.error(message, start, this.length);
   }

   private ParserException numberError(final int start) {
      return this.error(lexerMessage("json.invalid.number"), start);
   }

   private ParserException expectedError(final int start, final String expected, final String found) {
      return this.context.isOptionNashornCompatibilityMode() ? this.error(parserMessage("expected", expected, found), start) : expectedErrorV8(start, found);
   }

   private static ParserException expectedErrorV8(final int start, final String found) {
      char c = found.charAt(0);
      String entity;
      if (c == '"') {
         entity = "string";
      } else if (Character.isDigit(c)) {
         entity = "number";
      } else {
         entity = String.format("token %s", found);
      }

      String message = String.format("Unexpected %s in JSON at position %d", entity, start);
      return new ParserException(message);
   }

   private ParserException syntaxError(final int start, final String reason) {
      String message = ECMAErrors.getMessage("syntax.error.invalid.json", reason);
      return this.error(message, start);
   }

   private static String lexerMessage(final String msgId, String... args) {
      return ECMAErrors.getMessage("lexer.error." + msgId, args);
   }

   private static String parserMessage(final String msgId, String... args) {
      return ECMAErrors.getMessage("parser.error." + msgId, args);
   }

   private ParserException trailingCommaError(int start, String found) {
      return this.context.isOptionNashornCompatibilityMode() ? this.error(parserMessage("trailing.comma.in.json"), start) : expectedErrorV8(start, found);
   }
}
