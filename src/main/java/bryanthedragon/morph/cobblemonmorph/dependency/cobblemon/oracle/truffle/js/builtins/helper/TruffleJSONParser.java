package com.oracle.truffle.js.builtins.helper;

import com.oracle.js.parser.ParserException;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.api.strings.TruffleStringBuilder;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSException;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.array.ScriptArray;
import com.oracle.truffle.js.runtime.builtins.JSAbstractArray;
import com.oracle.truffle.js.runtime.builtins.JSArray;
import com.oracle.truffle.js.runtime.builtins.JSArrayObject;
import com.oracle.truffle.js.runtime.builtins.JSOrdinary;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.Null;

public class TruffleJSONParser {
   protected final JSContext context;
   protected int pos;
   protected int len;
   protected TruffleString parseStr;
   protected int parseDepth;
   protected static final char[] NullLiteral = new char[]{'n', 'u', 'l', 'l'};
   protected static final char[] BooleanTrueLiteral = new char[]{'t', 'r', 'u', 'e'};
   protected static final char[] BooleanFalseLiteral = new char[]{'f', 'a', 'l', 's', 'e'};
   protected static final int MAX_PARSE_DEPTH = 100000;
   private static final String MALFORMED_NUMBER = "malformed number";

   public TruffleJSONParser(JSContext context) {
      this.context = context;
   }

   public Object parse(TruffleString value, JSRealm realm) {
      this.pos = 0;
      this.parseDepth = 0;
      this.parseStr = value;
      this.len = Strings.length(this.parseStr);

      try {
         this.skipWhitespace();
         Object result = this.parseJSONValue(realm);
         this.skipWhitespace();
         if (this.posValid()) {
            this.error("JSON cannot be fully parsed");
         }

         return result;
      } catch (StackOverflowError var11) {
         throwStackError();
      } catch (JSException var12) {
         throw var12;
      } catch (IndexOutOfBoundsException var13) {
         throwSyntaxError(this.unexpectedEndOfInputMessage());
      } catch (Exception var14) {
         throwSyntaxError(null);
      } finally {
         this.parseStr = null;
      }

      return null;
   }

   private String unexpectedEndOfInputMessage() {
      return this.context.isOptionNashornCompatibilityMode() ? "Unexpected end of input" : "Unexpected end of JSON input";
   }

   protected Object parseJSONValue(JSRealm realm) {
      char c = this.get();
      if (isStringQuote(c)) {
         return this.parseJSONString();
      } else if (isObjectStart(c)) {
         return this.parseJSONObject(realm);
      } else if (isArrayStart(c)) {
         return this.parseJSONArray(realm);
      } else if (this.isNullLiteral(c)) {
         return this.parseNullLiteral();
      } else if (this.isBooleanLiteral(c)) {
         return this.parseBooleanLiteral();
      } else {
         return isNumber(c) ? this.parseJSONNumber() : this.unexpectedToken();
      }
   }

   protected static boolean isNumber(char cur) {
      return cur == '-' || JSRuntime.isAsciiDigit(cur);
   }

   protected static boolean isObjectStart(char c) {
      return c == '{';
   }

   protected static boolean isArrayStart(char c) {
      return c == '[';
   }

   private Object parseJSONObject(JSRealm realm) {
      assert isObjectStart(this.get());

      this.incDepth();
      this.skipChar('{');
      this.skipWhitespace();
      JSObject object = JSOrdinary.create(this.context, realm);
      if (this.get() != '}') {
         this.parseJSONMemberList(object, realm);
         if (this.get() != '}') {
            if (this.get() == '"') {
               this.unexpectedString();
            } else {
               this.unexpectedToken();
            }
         }
      }

      this.skipChar('}');
      this.skipWhitespace();
      this.decDepth();
      return object;
   }

   private void parseJSONMemberList(JSObject object, JSRealm realm) {
      TruffleJSONParser.Member member = this.parseJSONMember(realm);
      JSRuntime.createDataProperty(object, member.getKey(), member.getValue());

      while (this.get() == ',') {
         this.skipChar(',');
         this.skipWhitespace();
         member = this.parseJSONMember(realm);
         JSRuntime.createDataProperty(object, member.getKey(), member.getValue());
      }
   }

   private TruffleJSONParser.Member parseJSONMember(JSRealm realm) {
      TruffleString jsonString = this.parseJSONString();
      this.expectChar(':');
      this.skipWhitespace();
      Object jsonValue = this.parseJSONValue(realm);
      return new TruffleJSONParser.Member(jsonString, jsonValue);
   }

   private Object parseJSONArray(JSRealm realm) {
      assert isArrayStart(this.get());

      this.incDepth();
      this.skipChar('[');
      this.skipWhitespace();
      JSArrayObject array = JSArray.createEmptyZeroLength(this.context, realm);
      if (this.get() != ']') {
         this.parseJSONElementList(array, realm);
         if (this.get() != ']') {
            this.error("closing quote ] expected");
         }
      }

      this.skipChar(']');
      this.skipWhitespace();
      this.decDepth();
      return array;
   }

   private void incDepth() {
      this.parseDepth++;
      if (this.parseDepth > 100000) {
         throwStackError();
      }
   }

   protected static void throwStackError() {
      throw Errors.createRangeError("Cannot parse JSON constructs nested that deep");
   }

   protected static void throwSyntaxError(String msg) {
      throw Errors.createSyntaxError(msg == null ? "Cannot parse JSON" : msg);
   }

   protected void decDepth() {
      this.parseDepth--;
   }

   protected ScriptArray parseJSONElementList(JSArrayObject arrayObject, JSRealm realm) {
      int index = 0;
      ScriptArray scriptArray = JSAbstractArray.arrayGetArrayType(arrayObject);

      for (scriptArray = scriptArray.setElement(arrayObject, index, this.parseJSONValue(realm), false);
         this.get() == ',';
         scriptArray = scriptArray.setElement(arrayObject, ++index, this.parseJSONValue(realm), false)
      ) {
         this.skipChar(',');
         this.skipWhitespace();
      }

      JSAbstractArray.arraySetArrayType(arrayObject, scriptArray);
      return scriptArray;
   }

   protected TruffleString parseJSONString() {
      if (!isStringQuote(this.get())) {
         if (isDigit(this.get())) {
            this.unexpectedNumber();
         } else {
            this.unexpectedToken();
         }
      }

      this.skipChar('"');
      TruffleString str = this.parseJSONStringCharacters();
      if (!isStringQuote(this.get())) {
         this.error("String quote expected");
      }

      this.skipChar('"');
      this.skipWhitespace();
      return str;
   }

   protected static boolean isStringQuote(char c) {
      return c == '"';
   }

   protected static boolean isDigit(char c) {
      return '0' <= c && c <= '9';
   }

   protected TruffleString parseJSONStringCharacters() {
      int startPos = this.pos;
      boolean hasEscapes = false;
      int firstEscape = -1;

      for (char c = this.get(); !isStringQuote(c); c = this.get()) {
         if (c < ' ') {
            this.error("invalid string");
         } else if (c == '\\') {
            if (!hasEscapes) {
               firstEscape = this.pos;
            }

            hasEscapes = true;
            this.skipChar('\\');
         }

         this.skipChar();
      }

      int sLength = this.pos - startPos;
      TruffleString s = Strings.substring(this.context, this.parseStr, startPos, sLength);
      return hasEscapes ? this.unquoteJSON(s, sLength, firstEscape - startPos) : s;
   }

   protected TruffleString unquoteJSON(TruffleString string, int sLength, int posFirstBackslash) {
      assert sLength == Strings.length(string);

      assert posFirstBackslash >= 0;

      int posBackslash = posFirstBackslash;
      int curPos = 0;

      TruffleStringBuilder builder;
      for (builder = Strings.builderCreate(sLength); posBackslash >= 0; posBackslash = Strings.indexOf(string, 92, curPos)) {
         Strings.builderAppend(builder, string, curPos, posBackslash);
         curPos = posBackslash;
         char c = Strings.charAt(string, posBackslash + 1);
         switch (c) {
            case '"':
               Strings.builderAppend(builder, '"');
               break;
            case '/':
               Strings.builderAppend(builder, '/');
               break;
            case '\\':
               Strings.builderAppend(builder, '\\');
               break;
            case 'b':
               Strings.builderAppend(builder, '\b');
               break;
            case 'f':
               Strings.builderAppend(builder, '\f');
               break;
            case 'n':
               Strings.builderAppend(builder, '\n');
               break;
            case 'r':
               Strings.builderAppend(builder, '\r');
               break;
            case 't':
               Strings.builderAppend(builder, '\t');
               break;
            case 'u':
               this.unquoteJSONUnicode(string, posBackslash, builder);
               curPos = posBackslash + 4;
               break;
            default:
               this.error("wrong escape sequence");
         }

         curPos += 2;
      }

      if (curPos < sLength) {
         Strings.builderAppend(builder, string, curPos, sLength);
      }

      return Strings.builderToString(builder);
   }

   protected int hexDigitValue(char c) {
      int value = JSRuntime.valueInHex(c);
      if (value < 0) {
         this.error("invalid string");
         return -1;
      } else {
         return value;
      }
   }

   protected void unquoteJSONUnicode(TruffleString string, int posBackslash, TruffleStringBuilder builder) {
      char c1 = Strings.charAt(string, posBackslash + 2);
      char c2 = Strings.charAt(string, posBackslash + 3);
      char c3 = Strings.charAt(string, posBackslash + 4);
      char c4 = Strings.charAt(string, posBackslash + 5);
      char unencodedC = (char)(this.hexDigitValue(c1) << 12 | this.hexDigitValue(c2) << 8 | this.hexDigitValue(c3) << 4 | this.hexDigitValue(c4));
      Strings.builderAppend(builder, unencodedC);
   }

   protected Number parseJSONNumber() {
      int sign = 1;
      if (this.get() == '-') {
         this.skipChar();
         sign = -1;
      }

      if (!this.posValid()) {
         this.error("malformed number");
      }

      int startPos = this.pos;
      int fractionPos = -1;
      boolean firstPosIsZero = false;

      for (char c = this.get(); JSRuntime.isAsciiDigit(c) || c == '.'; c = this.get(this.pos)) {
         if (c == '.') {
            if (fractionPos >= 0) {
               this.error("malformed number");
            }

            fractionPos = this.pos;
         } else if (this.pos == startPos && c == '0') {
            firstPosIsZero = true;
         }

         this.skipChar();
         if (!this.posValid()) {
            break;
         }
      }

      if (this.pos == startPos) {
         this.unexpectedToken();
      } else if (firstPosIsZero && startPos + 1 < this.len) {
         char var11 = this.get(startPos + 1);
         if (var11 == 'x' || var11 == 'X' || JSRuntime.isAsciiDigit(var11)) {
            this.error("octal and hexadecimal not allowed");
         }
      }

      if (fractionPos == startPos || fractionPos == this.pos - 1) {
         this.error("malformed number");
      }

      boolean hasExponent = false;
      if (this.posValid() && this.isExponentPart()) {
         hasExponent = true;
         this.skipExponent();
      }

      int endPos = this.pos;
      this.skipWhitespace();
      if (firstPosIsZero && endPos - startPos == 1) {
         return (Number)(sign == 1 ? 0 : -0.0);
      } else {
         if (fractionPos == -1 && !hasExponent && endPos - startPos <= 16) {
            int radix = 10;
            long safeInt = JSRuntime.parseSafeInteger(this.parseStr, startPos, endPos, 10);

            assert safeInt != 0L;

            if (safeInt != Long.MIN_VALUE) {
               safeInt *= sign;
               if (JSRuntime.longIsRepresentableAsInt(safeInt)) {
                  return (int)safeInt;
               }

               return (double)safeInt;
            }
         }

         TruffleString valueStr = Strings.lazySubstring(this.parseStr, startPos, endPos - startPos);
         return this.parseAsDouble(sign, valueStr);
      }
   }

   protected Number parseAsDouble(int sign, TruffleString valueStr) {
      try {
         return Strings.parseDouble(valueStr) * sign;
      } catch (TruffleString.NumberFormatException var4) {
         this.error("malformed number");
         throw CompilerDirectives.shouldNotReachHere();
      }
   }

   protected void skipExponent() {
      this.skipChar();
      char cur = this.get();
      if (cur == '-') {
         this.skipChar('-');
      } else if (cur == '+') {
         this.skipChar('+');
      }

      if (!this.posValid()) {
         this.error("malformed number");
      }

      cur = this.get();

      int startPos;
      for (startPos = this.pos; JSRuntime.isAsciiDigit(cur); cur = this.get(this.pos)) {
         this.skipChar();
         if (!this.posValid()) {
            break;
         }
      }

      if (this.pos == startPos) {
         this.error("Expected number but found ident");
      }
   }

   protected boolean isExponentPart() {
      return this.get() == 'e' || this.get() == 'E';
   }

   protected boolean isNullLiteral(char c) {
      return c == 'n' && this.isLiteral(NullLiteral, 1);
   }

   protected Object parseNullLiteral() {
      assert this.isNullLiteral(this.get());

      this.skipString(Strings.NULL);
      return Null.instance;
   }

   protected boolean isBooleanLiteral(char c) {
      return c == 't' && this.isLiteral(BooleanTrueLiteral, 1) || c == 'f' && this.isLiteral(BooleanFalseLiteral, 1);
   }

   protected Object parseBooleanLiteral() {
      assert this.isBooleanLiteral(this.get());

      if (this.get() == 't') {
         this.skipString(Strings.TRUE);
         return true;
      } else if (this.get() == 'f') {
         this.skipString(Strings.FALSE);
         return false;
      } else {
         return this.error("cannot parse JSONBooleanLiteral");
      }
   }

   protected static boolean isWhitespace(char c) {
      return c == ' ' || c == '\n' || c == '\r' || c == '\t';
   }

   protected Object error(String message) {
      if (this.context.isOptionNashornCompatibilityMode()) {
         NashornJSONParser parser = new NashornJSONParser(this.parseStr, this.context);

         try {
            parser.parse();
         } catch (ParserException var5) {
            String msg = var5.getMessage().replace("\r\n", "\n");
            throw Errors.createSyntaxError("Invalid JSON: " + msg);
         }

         throw Errors.shouldNotReachHere("JSON parser did not throw error as expected");
      } else {
         assert !message.contains("at position");

         throw Errors.createSyntaxError(message + " at position " + this.pos);
      }
   }

   private Object unexpectedToken() {
      this.error("Unexpected token " + this.get() + " in JSON");
      return null;
   }

   private Object unexpectedString() {
      this.error("Unexpected string in JSON");
      return null;
   }

   private Object unexpectedNumber() {
      this.error("Unexpected number in JSON");
      return null;
   }

   protected char get() {
      return this.get(this.pos);
   }

   protected char get(int posParam) {
      return Strings.charAt(this.parseStr, posParam);
   }

   protected void skipString(TruffleString expected) {
      int length = Strings.length(expected);

      assert this.len >= this.pos + length;

      assert Strings.equals(Strings.lazySubstring(this.parseStr, this.pos, length), expected);

      this.pos += length;
      this.skipWhitespace();
   }

   protected void expectChar(char expected) {
      if (this.get(this.pos) != expected) {
         this.error(expected + " expected");
      }

      this.skipChar(expected);
   }

   protected void skipChar() {
      assert this.posValid();

      this.pos++;
   }

   protected void skipChar(char expected) {
      assert this.get(this.pos) == expected;

      this.skipChar();
   }

   protected void skipWhitespace() {
      while (this.posValid() && isWhitespace(this.get())) {
         this.pos++;
      }
   }

   protected boolean posValid() {
      return this.pos < this.len;
   }

   protected boolean isLiteral(char[] literal) {
      return this.isLiteral(literal, 0);
   }

   protected boolean isLiteral(char[] literal, int startPos) {
      if (this.len < this.pos + literal.length) {
         return false;
      } else {
         for (int i = startPos; i < literal.length; i++) {
            if (this.get(this.pos + i) != literal[i]) {
               return false;
            }
         }

         return true;
      }
   }

   protected final class Member {
      private final TruffleString key;
      private final Object value;

      public Member(TruffleString key, Object value) {
         this.key = key;
         this.value = value;
      }

      public TruffleString getKey() {
         return this.key;
      }

      public Object getValue() {
         return this.value;
      }
   }
}
