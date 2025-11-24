
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
import com.oracle.truffle.js.runtime.builtins.JSArrayObject;
import com.oracle.truffle.js.runtime.builtins.JSOrdinary;
import com.oracle.truffle.js.runtime.objects.JSAttributes;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
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

    public NashornJSONParser(TruffleString source, JSContext context) {
        this.source = source;
        this.context = context;
        this.length = Strings.length(source);
    }

    public Object parse() {
        Object value2 = this.parseLiteral();
        this.skipWhiteSpace();
        if (this.pos < this.length) {
            throw this.expectedError(this.pos, ERR_EOF_STR, NashornJSONParser.toString(this.peek()));
        }
        return value2;
    }

    private Object parseLiteral() {
        this.skipWhiteSpace();
        int c = this.peek();
        if (c == -1) {
            throw this.expectedError(this.pos, ERR_JSON_LITERAL, ERR_EOF_STR);
        }
        switch (c) {
            case 123: {
                return this.parseObject();
            }
            case 91: {
                return this.parseArray();
            }
            case 34: {
                return this.parseString();
            }
            case 102: {
                return this.parseKeyword(FALSE, Boolean.FALSE);
            }
            case 116: {
                return this.parseKeyword(TRUE, Boolean.TRUE);
            }
            case 110: {
                return this.parseKeyword(NULL, Null.instance);
            }
        }
        if (NashornJSONParser.isDigit(c) || c == 45) {
            return this.parseNumber();
        }
        if (c == 46) {
            throw this.numberError(this.pos);
        }
        throw this.expectedError(this.pos, ERR_JSON_LITERAL, NashornJSONParser.toString(c));
    }

    private Object parseObject() {
        JSObject jsobject = JSOrdinary.create(this.context, JSRealm.get(null));
        int state = 0;
        assert (this.peek() == 123);
        ++this.pos;
        block5: while (this.pos < this.length) {
            this.skipWhiteSpace();
            int c = this.peek();
            switch (c) {
                case 34: {
                    if (state == 1) {
                        throw this.expectedError(this.pos, ERR_COMMA_OR_RBRACE, NashornJSONParser.toString(c));
                    }
                    TruffleString id = this.parseString();
                    this.expectColon();
                    Object value2 = this.parseLiteral();
                    this.addObjectProperty(jsobject, id, value2);
                    state = 1;
                    continue block5;
                }
                case 44: {
                    if (state != 1) {
                        throw this.trailingCommaError(this.pos, NashornJSONParser.toString(c));
                    }
                    state = 2;
                    ++this.pos;
                    continue block5;
                }
                case 125: {
                    if (state == 2) {
                        throw this.trailingCommaError(this.pos, NashornJSONParser.toString(c));
                    }
                    ++this.pos;
                    return jsobject;
                }
            }
            throw this.expectedError(this.pos, ERR_COMMA_OR_RBRACE, NashornJSONParser.toString(c));
        }
        throw this.expectedError(this.pos, ERR_COMMA_OR_RBRACE, ERR_EOF_STR);
    }

    private void addObjectProperty(JSDynamicObject object, Object idStr, Object value2) {
        JSObjectUtil.defineDataProperty(this.context, object, idStr, value2, JSAttributes.getDefault());
    }

    private void expectColon() {
        this.skipWhiteSpace();
        int n = this.next();
        if (n != 58) {
            throw this.expectedError(this.pos - 1, ERR_COLON, NashornJSONParser.toString(n));
        }
    }

    private Object parseArray() {
        JSArrayObject jsarray = JSArray.createEmptyZeroLength(this.context, JSRealm.get(null));
        ScriptArray arrayData = JSAbstractArray.arrayGetArrayType(jsarray);
        int state = 0;
        assert (this.peek() == 91);
        ++this.pos;
        block4: while (this.pos < this.length) {
            this.skipWhiteSpace();
            int c = this.peek();
            switch (c) {
                case 44: {
                    if (state != 1) {
                        throw this.trailingCommaError(this.pos, NashornJSONParser.toString(c));
                    }
                    state = 2;
                    ++this.pos;
                    continue block4;
                }
                case 93: {
                    if (state == 2) {
                        throw this.trailingCommaError(this.pos, NashornJSONParser.toString(c));
                    }
                    ++this.pos;
                    return jsarray;
                }
            }
            if (state == 1) {
                throw this.expectedError(this.pos, ERR_COMMA_OR_RBRACKET, NashornJSONParser.toString(c));
            }
            long index = arrayData.length(jsarray);
            arrayData = arrayData.setElement(jsarray, index, this.parseLiteral(), true);
            JSAbstractArray.arraySetArrayType(jsarray, arrayData);
            state = 1;
        }
        throw this.expectedError(this.pos, ERR_COMMA_OR_RBRACKET, ERR_EOF_STR);
    }

    private TruffleString parseString() {
        int start2 = ++this.pos;
        TruffleStringBuilder sb = null;
        while (this.pos < this.length) {
            int c = this.next();
            if (c <= 31) {
                throw this.syntaxError(this.pos, ERR_STRING_CONTAINS_CONTROL_CHARACTER);
            }
            if (c == 92) {
                if (sb == null) {
                    sb = Strings.builderCreate(this.pos - start2 + 16);
                }
                Strings.builderAppend(sb, this.source, start2, this.pos - 1);
                Strings.builderAppend(sb, this.parseEscapeSequence());
                start2 = this.pos;
                continue;
            }
            if (c != 34) continue;
            if (sb != null) {
                Strings.builderAppend(sb, this.source, start2, this.pos - 1);
                return Strings.builderToString(sb);
            }
            return Strings.substring(this.context, this.source, start2, this.pos - 1 - start2);
        }
        throw this.error(NashornJSONParser.lexerMessage(MSG_MISSING_CLOSE_QUOTE, new String[0]), this.pos, this.length);
    }

    private char parseEscapeSequence() {
        int c = this.next();
        switch (c) {
            case 34: {
                return '\"';
            }
            case 92: {
                return '\\';
            }
            case 47: {
                return '/';
            }
            case 98: {
                return '\b';
            }
            case 102: {
                return '\f';
            }
            case 110: {
                return '\n';
            }
            case 114: {
                return '\r';
            }
            case 116: {
                return '\t';
            }
            case 117: {
                return this.parseUnicodeEscape();
            }
        }
        throw this.error(NashornJSONParser.lexerMessage(MSG_INVALID_ESCAPE_CHAR, new String[0]), this.pos - 1, this.length);
    }

    private char parseUnicodeEscape() {
        return (char)(this.parseHexDigit() << 12 | this.parseHexDigit() << 8 | this.parseHexDigit() << 4 | this.parseHexDigit());
    }

    private int parseHexDigit() {
        int c = this.next();
        if (c >= 48 && c <= 57) {
            return c - 48;
        }
        if (c >= 65 && c <= 70) {
            return c + 10 - 65;
        }
        if (c >= 97 && c <= 102) {
            return c + 10 - 97;
        }
        throw this.error(NashornJSONParser.lexerMessage(MSG_INVALID_HEX, new String[0]), this.pos - 1, this.length);
    }

    private static boolean isDigit(int c) {
        return c >= 48 && c <= 57;
    }

    private void skipDigits() {
        int c;
        while (this.pos < this.length && NashornJSONParser.isDigit(c = this.peek())) {
            ++this.pos;
        }
    }

    private Number parseNumber() {
        double d;
        int start2 = this.pos;
        int c = this.next();
        if (c == 45) {
            c = this.next();
        }
        if (!NashornJSONParser.isDigit(c)) {
            throw this.numberError(start2);
        }
        if (c != 48) {
            this.skipDigits();
        }
        if (this.peek() == 46) {
            ++this.pos;
            if (!NashornJSONParser.isDigit(this.next())) {
                throw this.numberError(this.pos - 1);
            }
            this.skipDigits();
        }
        if ((c = this.peek()) == 101 || c == 69) {
            ++this.pos;
            c = this.next();
            if (c == 45 || c == 43) {
                c = this.next();
            }
            if (!NashornJSONParser.isDigit(c)) {
                throw this.numberError(this.pos - 1);
            }
            this.skipDigits();
        }
        try {
            d = Strings.parseDouble(Strings.lazySubstring(this.source, start2, this.pos - start2));
        }
        catch (TruffleString.NumberFormatException e) {
            throw this.numberError(start2);
        }
        if (JSType.isRepresentableAsInt(d)) {
            return (int)d;
        }
        if (JSType.isRepresentableAsLong(d)) {
            return (long)d;
        }
        return d;
    }

    private Object parseKeyword(TruffleString keyword, Object value2) {
        if (!Strings.regionEquals(this.source, this.pos, keyword, 0, Strings.length(keyword))) {
            throw this.expectedError(this.pos, ERR_JSON_LITERAL, ERR_IDENT);
        }
        this.pos += Strings.length(keyword);
        return value2;
    }

    private int peek() {
        if (this.pos >= this.length) {
            return -1;
        }
        return Strings.charAt(this.source, this.pos);
    }

    private int next() {
        int next = this.peek();
        ++this.pos;
        return next;
    }

    private void skipWhiteSpace() {
        block3: while (this.pos < this.length) {
            switch (this.peek()) {
                case 9: 
                case 10: 
                case 13: 
                case 32: {
                    ++this.pos;
                    continue block3;
                }
            }
            return;
        }
    }

    private static String toString(int c) {
        return c == -1 ? ERR_EOF_STR : String.valueOf((char)c);
    }

    ParserException error(String message, int start2, int length) throws ParserException {
        long token = Token.toDesc(TokenType.STRING, start2, length);
        int pos = Token.descPosition(token);
        Source src = Source.sourceFor("<json>", Strings.toJavaString(this.source));
        int lineNum = src.getLine(pos);
        int columnNum = src.getColumn(pos);
        return new ParserException(JSErrorType.SyntaxError, message, src, lineNum, columnNum, token);
    }

    private ParserException error(String message, int start2) {
        return this.error(message, start2, this.length);
    }

    private ParserException numberError(int start2) {
        return this.error(NashornJSONParser.lexerMessage(MSG_JSON_INVALID_NUMBER, new String[0]), start2);
    }

    private ParserException expectedError(int start2, String expected, String found) {
        return this.context.isOptionNashornCompatibilityMode() ? this.error(NashornJSONParser.parserMessage(ERR_EXPECTED, expected, found), start2) : NashornJSONParser.expectedErrorV8(start2, found);
    }

    private static ParserException expectedErrorV8(int start2, String found) {
        char c = found.charAt(0);
        String entity2 = c == '\"' ? "string" : (Character.isDigit(c) ? "number" : String.format("token %s", found));
        String message = String.format("Unexpected %s in JSON at position %d", entity2, start2);
        return new ParserException(message);
    }

    private ParserException syntaxError(int start2, String reason) {
        String message = ECMAErrors.getMessage(MSG_SYNTAX_ERROR_INVALID_JSON, reason);
        return this.error(message, start2);
    }

    private static String lexerMessage(String msgId, String ... args) {
        return ECMAErrors.getMessage(MSG_LEXER_ERROR + msgId, args);
    }

    private static String parserMessage(String msgId, String ... args) {
        return ECMAErrors.getMessage(MSG_PARSER_ERROR + msgId, args);
    }

    private ParserException trailingCommaError(int start2, String found) {
        return this.context.isOptionNashornCompatibilityMode() ? this.error(NashornJSONParser.parserMessage(MSG_TRAILING_COMMA_IN_JSON, new String[0]), start2) : NashornJSONParser.expectedErrorV8(start2, found);
    }
}

