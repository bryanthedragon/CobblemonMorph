
package com.oracle.js.parser;

import com.oracle.js.parser.ECMAErrors;
import com.oracle.js.parser.IdentUtils;
import com.oracle.js.parser.JSErrorType;
import com.oracle.js.parser.JSType;
import com.oracle.js.parser.Options;
import com.oracle.js.parser.ParserException;
import com.oracle.js.parser.ParserStrings;
import com.oracle.js.parser.Scanner;
import com.oracle.js.parser.Source;
import com.oracle.js.parser.Token;
import com.oracle.js.parser.TokenLookup;
import com.oracle.js.parser.TokenStream;
import com.oracle.js.parser.TokenType;
import com.oracle.truffle.api.strings.TruffleString;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public class Lexer
extends Scanner {
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

    public Lexer(Source source, TokenStream stream, boolean scripting, int ecmaScriptVersion, boolean shebang, boolean isModule, boolean allowBigInt) {
        this(source, 0, source.getLength(), stream, scripting, ecmaScriptVersion, shebang, isModule, false, allowBigInt);
    }

    public Lexer(Source source, int start2, int len, TokenStream stream, boolean scripting, int ecmaScriptVersion, boolean shebang, boolean isModule, boolean pauseOnFunctionBody, boolean allowBigInt) {
        super(source.getContent(), 1, start2, len);
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
        this.internedStrings = new HashMap<String, TruffleString>();
    }

    private Lexer(Lexer lexer, State state) {
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

    @Override
    State saveState() {
        return new State(this.position, this.limit, this.line, this.pendingLine, this.linePosition, this.last);
    }

    @Override
    void restoreState(Scanner.State state) {
        super.restoreState(state);
        State lexerState = (State)state;
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

    protected void add(TokenType type, int start2, int end2) {
        this.last = type;
        if (type == TokenType.EOL) {
            this.pendingLine = end2;
            this.linePosition = start2;
        } else {
            if (this.pendingLine != -1) {
                this.stream.put(Token.toDesc(TokenType.EOL, this.linePosition, this.pendingLine));
                this.pendingLine = -1;
            }
            this.stream.put(Token.toDesc(type, start2, end2 - start2));
        }
    }

    protected void add(TokenType type, int start2) {
        this.add(type, start2, this.position);
    }

    private void skipEOL(boolean addEOL) {
        if (this.ch0 == '\r') {
            this.skip(1);
            if (this.ch0 == '\n') {
                this.skip(1);
            }
        } else {
            this.skip(1);
        }
        ++this.line;
        if (addEOL) {
            this.add(TokenType.EOL, this.position, this.line);
        }
    }

    private void skipLine(boolean addEOL) {
        while (!this.isEOL(this.ch0) && !this.atEOF()) {
            this.skip(1);
        }
        this.skipEOL(addEOL);
    }

    public static boolean isJSWhitespace(char ch) {
        if (ch <= '\r') {
            return ch >= '\t';
        }
        if (ch < '\u1680') {
            return ch == ' ' || ch == '\u00a0';
        }
        return Lexer.isWhitespaceHigh(ch);
    }

    private static boolean isWhitespaceHigh(char ch) {
        return ch == '\u1680' || ch >= '\u2000' && (ch <= '\u200a' || ch == '\u2028' || ch == '\u2029' || ch == '\u202f' || ch == '\u205f' || ch == '\u3000' || ch == '\ufeff');
    }

    public static boolean isJSEOL(char ch) {
        return ch == '\n' || ch == '\r' || ch == '\u2028' || ch == '\u2029';
    }

    public static boolean isStringLineTerminator(char ch) {
        return ch == '\n' || ch == '\r';
    }

    protected boolean isStringDelimiter(char ch) {
        return ch == '\'' || ch == '\"';
    }

    private static boolean isTemplateDelimiter(char ch) {
        return ch == '`';
    }

    protected boolean isWhitespace(char ch) {
        return Lexer.isJSWhitespace(ch);
    }

    protected boolean isEOL(char ch) {
        return Lexer.isJSEOL(ch);
    }

    private void skipWhitespace(boolean addEOL) {
        while (this.isWhitespace(this.ch0)) {
            if (this.isEOL(this.ch0)) {
                this.skipEOL(addEOL);
                continue;
            }
            this.skip(1);
        }
    }

    private void skipUntilEOL() {
        while (!this.atEOF() && !this.isEOL(this.ch0)) {
            this.skip(1);
        }
    }

    private void skipSingleLineComment() {
        assert (this.ch0 == '/' && this.ch1 == '/');
        int start2 = this.position;
        this.skip(2);
        boolean directiveComment = (this.ch0 == '#' || this.ch0 == '@') && this.ch1 == ' ';
        this.skipUntilEOL();
        this.add(directiveComment ? TokenType.DIRECTIVE_COMMENT : TokenType.COMMENT, start2);
    }

    private void skipMultiLineComment() {
        assert (this.ch0 == '/' && this.ch1 == '*');
        int start2 = this.position;
        this.skip(2);
        while (!(this.atEOF() || this.ch0 == '*' && this.ch1 == '/')) {
            if (this.isEOL(this.ch0)) {
                this.skipEOL(true);
                continue;
            }
            this.skip(1);
        }
        if (this.atEOF()) {
            this.add(TokenType.ERROR, start2);
        } else {
            this.skip(2);
        }
        this.add(TokenType.COMMENT, start2);
    }

    private void skipShebang() {
        assert (this.shebang || this.scripting);
        assert (this.ch0 == '#');
        int start2 = this.position;
        this.skip(1);
        this.skipUntilEOL();
        this.add(TokenType.COMMENT, start2);
    }

    private void skipSingleLineHTMLOpenComment() {
        assert (!this.isModule);
        assert (this.ch0 == '<' && this.ch1 == '!' && this.ch2 == '-' && this.ch3 == '-');
        int start2 = this.position;
        this.skip(4);
        this.skipUntilEOL();
        this.add(TokenType.COMMENT, start2);
    }

    private void skipSingleLineHTMLCloseComment() {
        assert (!this.isModule);
        assert (this.ch0 == '-' && this.ch1 == '-' && this.ch2 == '>');
        int start2 = this.position;
        this.skip(3);
        this.skipUntilEOL();
        this.add(TokenType.COMMENT, start2);
    }

    private boolean seenEOL() {
        if (this.last == TokenType.EOL) {
            return true;
        }
        int idx = this.stream.last();
        block4: while (idx >= 0) {
            switch (Token.descType(this.stream.get(idx--))) {
                case COMMENT: {
                    continue block4;
                }
                case EOL: {
                    return true;
                }
            }
            break;
        }
        return false;
    }

    public RegexToken valueOfPattern(int start2, int length) {
        int savePosition = this.position;
        this.reset(start2);
        StringBuilder sb = new StringBuilder(length);
        this.skip(1);
        boolean inBrackets = false;
        while (!this.atEOF() && this.ch0 != '/' && !this.isEOL(this.ch0) || inBrackets) {
            if (this.ch0 == '\\') {
                sb.append(this.ch0);
                sb.append(this.ch1);
                this.skip(2);
                continue;
            }
            if (this.ch0 == '[') {
                inBrackets = true;
            } else if (this.ch0 == ']') {
                inBrackets = false;
            }
            sb.append(this.ch0);
            this.skip(1);
        }
        TruffleString regex = this.stringIntern(sb.toString());
        this.skip(1);
        TruffleString options = this.stringIntern(this.source.getString(this.position, this.scanIdentifier()));
        this.reset(savePosition);
        return new RegexToken(regex, options);
    }

    public boolean canStartLiteral(TokenType token) {
        return token.startsWith('/') || (this.scripting || XML_LITERALS) && token.startsWith('<');
    }

    protected boolean scanLiteral(long token, TokenType startTokenType, LineInfoReceiver lir) {
        if (!this.canStartLiteral(startTokenType)) {
            return false;
        }
        if (this.stream.get(this.stream.last()) != token) {
            return false;
        }
        State state = this.saveState();
        this.reset(Token.descPosition(token));
        if (this.ch0 == '/') {
            return this.scanRegEx();
        }
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

    private boolean scanRegEx() {
        assert (this.ch0 == '/');
        if (this.ch1 != '/' && this.ch1 != '*') {
            int start2 = this.position;
            this.skip(1);
            boolean inBrackets = false;
            while (!(this.atEOF() || this.ch0 == '/' && !inBrackets || this.isEOL(this.ch0))) {
                if (this.ch0 == '\\') {
                    this.skip(1);
                    if (this.isEOL(this.ch0)) {
                        this.reset(start2);
                        return false;
                    }
                    this.skip(1);
                    continue;
                }
                if (this.ch0 == '[') {
                    inBrackets = true;
                } else if (this.ch0 == ']') {
                    inBrackets = false;
                }
                this.skip(1);
            }
            if (this.ch0 == '/') {
                this.skip(1);
                while (!this.atEOF() && Character.isJavaIdentifierPart(this.ch0) || this.ch0 == '\\' && this.ch1 == 'u') {
                    this.skip(1);
                }
                this.add(TokenType.REGEX, start2);
                return true;
            }
            this.reset(start2);
        }
        return false;
    }

    private int consumeDigits(TokenType type, int base, boolean allowInitialSeparator, boolean allowSeparators) {
        int maxDigit = 0;
        boolean seenSeparator = false;
        boolean allowSeparator = allowInitialSeparator;
        while (true) {
            if (allowSeparator && this.ch0 == '_') {
                if (seenSeparator) {
                    this.error(Lexer.message(MSG_NUMERIC_LITERAL_MULTIPLE_SEPARATORS, new String[0]), type, this.position, this.limit - this.position);
                } else {
                    seenSeparator = true;
                    this.skip(1);
                }
            } else {
                int digit = Lexer.convertDigit(this.ch0, base);
                if (digit == -1) {
                    if (!seenSeparator) break;
                    this.error(Lexer.message(MSG_NUMERIC_LITERAL_TRAILING_SEPARATOR, new String[0]), type, this.position, this.limit - this.position);
                } else {
                    seenSeparator = false;
                    maxDigit = Math.max(maxDigit, digit);
                    this.skip(1);
                }
            }
            allowSeparator = allowSeparators;
        }
        return maxDigit;
    }

    protected static int convertDigit(char ch, int base) {
        int digit;
        if ('0' <= ch && ch <= '9') {
            digit = ch - 48;
        } else if ('A' <= ch && ch <= 'Z') {
            digit = ch - 65 + 10;
        } else if ('a' <= ch && ch <= 'z') {
            digit = ch - 97 + 10;
        } else {
            return -1;
        }
        return digit < base ? digit : -1;
    }

    private int hexSequence(int length, TokenType type) {
        int value2 = 0;
        for (int i = 0; i < length; ++i) {
            int digit = Lexer.convertDigit(this.ch0, 16);
            if (digit == -1) {
                this.error(Lexer.message(MSG_INVALID_HEX, new String[0]), type, this.position, this.limit - this.position);
                return i == 0 ? -1 : value2;
            }
            value2 = digit | value2 << 4;
            this.skip(1);
        }
        return value2;
    }

    private int varlenHexSequence(TokenType type) {
        assert (this.ch0 == '{');
        this.skip(1);
        int value2 = 0;
        int i = 0;
        while (!this.atEOF()) {
            if (this.ch0 == '}') {
                if (i != 0) {
                    this.skip(1);
                    return value2;
                }
                this.error(Lexer.message(MSG_INVALID_HEX, new String[0]), type, this.position, this.limit - this.position);
                this.skip(1);
                return -1;
            }
            int digit = Lexer.convertDigit(this.ch0, 16);
            if (digit == -1) {
                this.error(Lexer.message(MSG_INVALID_HEX, new String[0]), type, this.position, this.limit - this.position);
                return i == 0 ? -1 : value2;
            }
            if ((value2 = digit | value2 << 4) > 0x10FFFF) {
                this.error(Lexer.message(MSG_INVALID_HEX, new String[0]), type, this.position, this.limit - this.position);
                return -1;
            }
            this.skip(1);
            ++i;
        }
        return value2;
    }

    private int unicodeEscapeSequence(TokenType type) {
        if (this.ch0 == '{' && this.isES6()) {
            return this.varlenHexSequence(type);
        }
        return this.hexSequence(4, type);
    }

    private int octalSequence() {
        int digit;
        int value2 = 0;
        for (int i = 0; i < 3 && (digit = Lexer.convertDigit(this.ch0, 8)) != -1; ++i) {
            value2 = digit | value2 << 3;
            this.skip(1);
            if (i == 1 && value2 >= 32) break;
        }
        return value2;
    }

    public boolean checkIdentForKeyword(long token, String keyword) {
        int len = Token.descLength(token);
        int start2 = Token.descPosition(token);
        if (len != keyword.length()) {
            return false;
        }
        return this.content.regionMatches(start2, keyword, 0, len);
    }

    private TruffleString valueOfIdent(int start2, int length, boolean convertUnicode) {
        int end2 = start2 + length;
        StringBuilder sb = new StringBuilder(length);
        int pos = start2;
        while (pos < end2) {
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
                continue;
            }
            sb.append(curCh0);
            ++pos;
        }
        return this.stringIntern(sb.toString());
    }

    private void scanIdentifierOrKeyword() {
        int start2 = this.position;
        int length = this.scanIdentifier();
        TokenType type = TokenLookup.lookupKeyword(this.content, start2, length);
        if (type == TokenType.FUNCTION && this.pauseOnFunctionBody) {
            this.pauseOnNextLeftBrace = true;
        }
        this.add(type, start2);
    }

    private TruffleString valueOfString(int start2, int length, boolean strict) {
        int savePosition = this.position;
        int end2 = start2 + length;
        this.reset(start2);
        StringBuilder sb = new StringBuilder(length);
        block17: while (this.position < end2) {
            if (this.ch0 == '\\') {
                this.skip(1);
                char next = this.ch0;
                int afterSlash = this.position;
                this.skip(1);
                switch (next) {
                    case '0': 
                    case '1': 
                    case '2': 
                    case '3': 
                    case '4': 
                    case '5': 
                    case '6': 
                    case '7': {
                        if (strict && (next != '0' || this.ch0 >= '0' && this.ch0 <= '9')) {
                            this.error(Lexer.message(MSG_STRICT_NO_OCTAL, new String[0]), TokenType.STRING, this.position, this.limit - this.position);
                        }
                        this.reset(afterSlash);
                        int ch = this.octalSequence();
                        if (ch < 0) {
                            sb.append('\\');
                            sb.append('x');
                            continue block17;
                        }
                        sb.append((char)ch);
                        continue block17;
                    }
                    case '8': 
                    case '9': {
                        if (strict) {
                            this.error(Lexer.message(MSG_STRICT_NO_NONOCTALDECIMAL, new String[0]), TokenType.STRING, this.position, this.limit - this.position);
                        }
                        sb.append(next);
                        continue block17;
                    }
                    case 'n': {
                        sb.append('\n');
                        continue block17;
                    }
                    case 't': {
                        sb.append('\t');
                        continue block17;
                    }
                    case 'b': {
                        sb.append('\b');
                        continue block17;
                    }
                    case 'f': {
                        sb.append('\f');
                        continue block17;
                    }
                    case 'r': {
                        sb.append('\r');
                        continue block17;
                    }
                    case '\'': {
                        sb.append('\'');
                        continue block17;
                    }
                    case '\"': {
                        sb.append('\"');
                        continue block17;
                    }
                    case '\\': {
                        sb.append('\\');
                        continue block17;
                    }
                    case '\r': {
                        if (this.ch0 == '\n') {
                            this.skip(1);
                        }
                    }
                    case '\n': 
                    case '\u2028': 
                    case '\u2029': {
                        continue block17;
                    }
                    case 'x': {
                        int ch = this.hexSequence(2, TokenType.STRING);
                        if (ch < 0) {
                            sb.append('\\');
                            sb.append('x');
                            continue block17;
                        }
                        sb.append((char)ch);
                        continue block17;
                    }
                    case 'u': {
                        int ch = this.unicodeEscapeSequence(TokenType.STRING);
                        if (ch < 0) {
                            sb.append('\\');
                            sb.append('u');
                            continue block17;
                        }
                        if (ch <= 65535 && Character.isSurrogate((char)ch)) {
                            sb.append((char)ch);
                            continue block17;
                        }
                        sb.appendCodePoint(ch);
                        continue block17;
                    }
                    case 'v': {
                        sb.append('\u000b');
                        continue block17;
                    }
                }
                sb.append(next);
                continue;
            }
            if (this.ch0 == '\r') {
                sb.append('\n');
                this.skip(this.ch1 == '\n' ? 2 : 1);
                continue;
            }
            sb.append(this.ch0);
            this.skip(1);
        }
        this.reset(savePosition);
        return this.stringIntern(sb.toString());
    }

    protected void scanString(boolean add2) {
        TokenType type = TokenType.STRING;
        char quote = this.ch0;
        this.skip(1);
        State stringState = this.saveState();
        while (!this.atEOF() && this.ch0 != quote && !Lexer.isStringLineTerminator(this.ch0)) {
            if (this.ch0 == '\\') {
                type = TokenType.ESCSTRING;
                this.skip(1);
                if (!this.isEscapeCharacter(this.ch0)) {
                    this.error(Lexer.message(MSG_INVALID_ESCAPE_CHAR, new String[0]), TokenType.STRING, this.position, this.limit - this.position);
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
            this.error(Lexer.message(MSG_MISSING_CLOSE_QUOTE, new String[0]), TokenType.STRING, this.position, this.limit - this.position);
        }
        if (add2) {
            stringState.setLimit(this.position - 1);
            if (this.scripting && !stringState.isEmpty()) {
                switch (quote) {
                    case '`': {
                        this.add(TokenType.EXECSTRING, stringState.position, stringState.getLimit());
                        this.add(TokenType.LBRACE, stringState.position, stringState.position);
                        this.editString(type, stringState);
                        this.add(TokenType.RBRACE, stringState.getLimit(), stringState.getLimit());
                        break;
                    }
                    case '\"': {
                        this.editString(type, stringState);
                        break;
                    }
                    case '\'': {
                        this.add(type, stringState.position, stringState.getLimit());
                        break;
                    }
                }
            } else {
                this.add(type, stringState.position, stringState.getLimit());
            }
        }
    }

    private void scanTemplate() {
        assert (this.ch0 == '`');
        this.skip(1);
        this.scanTemplateString(TokenType.TEMPLATE);
    }

    protected final void scanTemplateSpan() {
        this.scanTemplateString(TokenType.TEMPLATE_MIDDLE);
    }

    private void scanTemplateString(TokenType type) {
        assert (type == TokenType.TEMPLATE || type == TokenType.TEMPLATE_MIDDLE);
        State stringState = this.saveState();
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
                    this.error(Lexer.message(MSG_INVALID_ESCAPE_CHAR, new String[0]), TokenType.TEMPLATE, this.position, this.limit - this.position);
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
        this.error(Lexer.message(MSG_MISSING_CLOSE_QUOTE, new String[0]), TokenType.TEMPLATE, this.position, this.limit - this.position);
    }

    protected boolean isEscapeCharacter(char ch) {
        return true;
    }

    private static String removeUnderscores(String string) {
        int pos = string.indexOf(95);
        if (pos < 0) {
            return string;
        }
        int lastPos = 0;
        StringBuilder sb = new StringBuilder(string.length());
        while (pos >= 0) {
            sb.append(string, lastPos, pos);
            lastPos = pos + 1;
            pos = string.indexOf(95, lastPos);
        }
        sb.append(string, lastPos, string.length());
        return sb.toString();
    }

    private static Number valueOf(String string, int radix) throws NumberFormatException {
        String valueString = Lexer.removeUnderscores(string);
        try {
            long value2 = Long.parseLong(valueString, radix);
            if (value2 >= Integer.MIN_VALUE && value2 <= Integer.MAX_VALUE) {
                return (int)value2;
            }
            return value2;
        }
        catch (NumberFormatException e) {
            if (radix == 10) {
                return Double.parseDouble(valueString);
            }
            if (radix == 16 && valueString.length() >= 15) {
                return new BigInteger(valueString, 16).doubleValue();
            }
            double value3 = 0.0;
            for (int i = 0; i < valueString.length(); ++i) {
                char ch = valueString.charAt(i);
                int digit = Lexer.convertDigit(ch, radix);
                value3 *= (double)radix;
                value3 += (double)digit;
            }
            return value3;
        }
    }

    private static BigInteger valueOfBigInt(String string) {
        String valueString = Lexer.removeUnderscores(string);
        if (valueString.length() > 2 && valueString.charAt(0) == '0') {
            switch (valueString.charAt(1)) {
                case 'X': 
                case 'x': {
                    return new BigInteger(valueString.substring(2), 16);
                }
                case 'O': 
                case 'o': {
                    return new BigInteger(valueString.substring(2), 8);
                }
                case 'B': 
                case 'b': {
                    return new BigInteger(valueString.substring(2), 2);
                }
            }
            return new BigInteger(valueString, 10);
        }
        return new BigInteger(valueString, 10);
    }

    protected void scanNumber() {
        int start2 = this.position;
        TokenType type = TokenType.DECIMAL;
        int digit = Lexer.convertDigit(this.ch0, 10);
        boolean numericSeparators = this.isES2020();
        if (digit == 0 && (this.ch1 == 'x' || this.ch1 == 'X') && Lexer.convertDigit(this.ch2, 16) != -1) {
            this.skip(3);
            type = TokenType.HEXADECIMAL;
            this.consumeDigits(type, 16, numericSeparators, numericSeparators);
        } else if (digit == 0 && this.isES6() && (this.ch1 == 'o' || this.ch1 == 'O') && Lexer.convertDigit(this.ch2, 8) != -1) {
            this.skip(3);
            type = TokenType.OCTAL;
            this.consumeDigits(type, 8, numericSeparators, numericSeparators);
        } else if (digit == 0 && this.isES6() && (this.ch1 == 'b' || this.ch1 == 'B') && Lexer.convertDigit(this.ch2, 2) != -1) {
            this.skip(3);
            type = TokenType.BINARY_NUMBER;
            this.consumeDigits(type, 2, numericSeparators, numericSeparators);
        } else {
            boolean octal;
            boolean bl = octal = digit == 0;
            if (digit != -1) {
                this.skip(1);
            }
            boolean allowSeparators = numericSeparators && !octal;
            int maxDigit = this.consumeDigits(type, 10, allowSeparators, allowSeparators);
            if (octal && maxDigit >= 8) {
                octal = false;
                type = TokenType.NON_OCTAL_DECIMAL;
            }
            if (octal && this.position - start2 > 1) {
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
        if (this.ch0 == 'n' && this.allowBigInt && (type == TokenType.DECIMAL || type == TokenType.BINARY_NUMBER || type == TokenType.OCTAL || type == TokenType.HEXADECIMAL)) {
            this.skip(1);
            type = TokenType.BIGINT;
        }
        if (Character.isJavaIdentifierStart(this.ch0)) {
            this.error(Lexer.message(MSG_MISSING_SPACE_AFTER_NUMBER, new String[0]), type, this.position, 1);
        }
        this.add(type, start2);
    }

    XMLToken valueOfXML(int start2, int length) {
        return new XMLToken(this.stringIntern(this.source.getString(start2, length)));
    }

    private boolean scanXMLLiteral() {
        assert (this.ch0 == '<' && Character.isJavaIdentifierStart(this.ch1));
        if (XML_LITERALS) {
            int start2 = this.position;
            int openCount = 0;
            do {
                if (this.ch0 == '<') {
                    if (this.ch1 == '/' && Character.isJavaIdentifierStart(this.ch2)) {
                        this.skip(3);
                        --openCount;
                    } else if (Character.isJavaIdentifierStart(this.ch1)) {
                        this.skip(2);
                        ++openCount;
                    } else if (this.ch1 == '?') {
                        this.skip(2);
                    } else if (this.ch1 == '!' && this.ch2 == '-' && this.ch3 == '-') {
                        this.skip(4);
                    } else {
                        this.reset(start2);
                        return false;
                    }
                    while (!this.atEOF() && this.ch0 != '>') {
                        if (this.ch0 == '/' && this.ch1 == '>') {
                            --openCount;
                            this.skip(1);
                            break;
                        }
                        if (this.ch0 == '\"' || this.ch0 == '\'') {
                            this.scanString(false);
                            continue;
                        }
                        this.skip(1);
                    }
                    if (this.ch0 != '>') {
                        this.reset(start2);
                        return false;
                    }
                    this.skip(1);
                    continue;
                }
                if (this.atEOF()) {
                    this.reset(start2);
                    return false;
                }
                this.skip(1);
            } while (openCount > 0);
            this.add(TokenType.XML, start2);
            return true;
        }
        return false;
    }

    private int scanIdentifier() {
        int codePoint;
        int start2 = this.position;
        if (this.ch0 == '\\' && this.ch1 == 'u') {
            this.skip(2);
            codePoint = this.unicodeEscapeSequence(TokenType.IDENT);
            if (!IdentUtils.isIdentifierStart(codePoint)) {
                this.error(Lexer.message(MSG_ILLEGAL_IDENTIFIER_CHARACTER, new String[0]), TokenType.IDENT, start2, this.position - start2);
            }
        } else if (IdentUtils.isIdentifierStart(this.ch0)) {
            this.skip(1);
        } else if (Character.isHighSurrogate(this.ch0) && Character.isLowSurrogate(this.ch1) && IdentUtils.isIdentifierStart(Character.toCodePoint(this.ch0, this.ch1))) {
            this.skip(2);
        } else {
            return 0;
        }
        while (!this.atEOF()) {
            if (this.ch0 == '\\' && this.ch1 == 'u') {
                this.skip(2);
                codePoint = this.unicodeEscapeSequence(TokenType.IDENT);
                if (IdentUtils.isIdentifierPart(codePoint)) continue;
                this.error(Lexer.message(MSG_ILLEGAL_IDENTIFIER_CHARACTER, new String[0]), TokenType.IDENT, start2, this.position - start2);
                continue;
            }
            if (IdentUtils.isIdentifierPart(this.ch0)) {
                this.skip(1);
                continue;
            }
            if (!Character.isHighSurrogate(this.ch0) || !Character.isLowSurrogate(this.ch1) || !IdentUtils.isIdentifierPart(Character.toCodePoint(this.ch0, this.ch1))) break;
            this.skip(2);
        }
        return this.position - start2;
    }

    private boolean identifierEqual(int aStart, int aLength, int bStart, int bLength) {
        if (aLength == bLength) {
            return this.content.regionMatches(aStart, this.content, bStart, aLength);
        }
        return false;
    }

    private static boolean isPrivateIdentifierStart(char ch) {
        return ch == '#';
    }

    private void scanPrivateIdentifier() {
        int start2 = this.position;
        assert (Lexer.isPrivateIdentifierStart(this.ch0));
        this.skip(1);
        if (this.scanIdentifier() != 0) {
            this.add(TokenType.PRIVATE_IDENT, start2);
        } else {
            this.add(TokenType.ERROR, start2);
        }
    }

    private boolean hasHereMarker(int identStart, int identLength) {
        this.skipWhitespace(false);
        return this.identifierEqual(identStart, identLength, this.position, this.scanIdentifier());
    }

    private void editString(TokenType stringType, State stringState) {
        EditStringLexer lexer = new EditStringLexer(this, stringType, stringState);
        lexer.lexify();
        this.last = stringType;
    }

    private boolean scanHereString(LineInfoReceiver lir, State oldState) {
        assert (this.ch0 == '<' && this.ch1 == '<');
        if (this.scripting) {
            boolean noStringEditing;
            boolean excludeLastEOL;
            State saved = this.saveState();
            boolean bl = excludeLastEOL = this.ch2 != '<';
            if (excludeLastEOL) {
                this.skip(2);
            } else {
                this.skip(3);
            }
            char quoteChar = this.ch0;
            boolean bl2 = noStringEditing = quoteChar == '\"' || quoteChar == '\'';
            if (noStringEditing) {
                this.skip(1);
            }
            int identStart = this.position;
            int identLength = this.scanIdentifier();
            if (noStringEditing) {
                if (this.ch0 != quoteChar) {
                    this.error(Lexer.message(MSG_HERE_NON_MATCHING_DELIMITER, new String[0]), this.last, this.position, 0);
                    this.restoreState(saved);
                    return false;
                }
                this.skip(1);
            }
            if (identLength == 0) {
                this.restoreState(saved);
                return false;
            }
            State restState = this.saveState();
            int lastLine = this.line;
            this.skipLine(false);
            ++lastLine;
            int lastLinePosition = this.position;
            restState.setLimit(this.position);
            if (oldState.position > this.position) {
                this.restoreState(oldState);
                this.skipLine(false);
            }
            State stringState = this.saveState();
            int stringEnd = this.position;
            while (!this.atEOF()) {
                this.skipWhitespace(false);
                if (this.hasHereMarker(identStart, identLength)) break;
                this.skipLine(false);
                ++lastLine;
                lastLinePosition = this.position;
                stringEnd = this.position;
            }
            lir.lineInfo(lastLine, lastLinePosition);
            stringState.setLimit(stringEnd);
            if (stringState.isEmpty() || this.atEOF()) {
                this.error(Lexer.message(MSG_HERE_MISSING_END_MARKER, this.source.getString(identStart, identLength)), this.last, this.position, 0);
                this.restoreState(saved);
                return false;
            }
            if (excludeLastEOL) {
                if (this.content.charAt(stringEnd - 1) == '\n') {
                    --stringEnd;
                }
                if (this.content.charAt(stringEnd - 1) == '\r') {
                    --stringEnd;
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
        }
        return false;
    }

    public void lexify() {
        while (!this.stream.isFull() || this.nested) {
            this.skipWhitespace(true);
            if (this.atEOF()) {
                if (this.nested) break;
                this.add(TokenType.EOF, this.position);
                break;
            }
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
                    continue;
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
            if (this.ch0 == '.' && Lexer.convertDigit(this.ch1, 10) != -1) {
                this.scanNumber();
                continue;
            }
            TokenType type = TokenLookup.lookupOperator(this.ch0, this.ch1, this.ch2, this.ch3, this.ecmaScriptVersion);
            if (type != null) {
                int typeLength = type.getLength();
                this.skip(typeLength);
                this.add(type, this.position - typeLength);
                if (this.canStartLiteral(type)) break;
                if (type == TokenType.LBRACE && this.pauseOnNextLeftBrace) {
                    this.pauseOnNextLeftBrace = false;
                    break;
                }
                if (type != TokenType.RBRACE || !this.pauseOnRightBrace) continue;
                break;
            }
            if (IdentUtils.isIdentifierStart(Character.isHighSurrogate(this.ch0) && Character.isLowSurrogate(this.ch1) ? Character.toCodePoint(this.ch0, this.ch1) : this.ch0) || this.ch0 == '\\' && this.ch1 == 'u') {
                this.scanIdentifierOrKeyword();
                continue;
            }
            if (this.isStringDelimiter(this.ch0)) {
                this.scanString(true);
                continue;
            }
            if ('0' <= this.ch0 && this.ch0 <= '9') {
                this.scanNumber();
                continue;
            }
            if (Lexer.isTemplateDelimiter(this.ch0) && this.isES6()) {
                this.scanTemplate();
                break;
            }
            if (Lexer.isTemplateDelimiter(this.ch0) && this.scripting) {
                this.scanString(true);
                continue;
            }
            if (Lexer.isPrivateIdentifierStart(this.ch0)) {
                this.scanPrivateIdentifier();
                continue;
            }
            this.skip(1);
            this.add(TokenType.ERROR, this.position - 1);
        }
    }

    Object getValueOf(long token, boolean strict) {
        return this.getValueOf(token, strict, true);
    }

    Object getValueOf(long token, boolean strict, boolean convertUnicode) {
        int start2 = Token.descPosition(token);
        int len = Token.descLength(token);
        switch (Token.descType(token)) {
            case DECIMAL: 
            case NON_OCTAL_DECIMAL: {
                return Lexer.valueOf(this.source.getString(start2, len), 10);
            }
            case HEXADECIMAL: {
                return Lexer.valueOf(this.source.getString(start2 + 2, len - 2), 16);
            }
            case OCTAL_LEGACY: {
                return Lexer.valueOf(this.source.getString(start2, len), 8);
            }
            case OCTAL: {
                return Lexer.valueOf(this.source.getString(start2 + 2, len - 2), 8);
            }
            case BINARY_NUMBER: {
                return Lexer.valueOf(this.source.getString(start2 + 2, len - 2), 2);
            }
            case BIGINT: {
                return Lexer.valueOfBigInt(this.source.getString(start2, len - 1));
            }
            case FLOATING: {
                String str = Lexer.removeUnderscores(this.source.getString(start2, len));
                double value2 = Double.parseDouble(str);
                if (str.indexOf(46) != -1) {
                    return value2;
                }
                if (JSType.isStrictlyRepresentableAsInt(value2)) {
                    return (int)value2;
                }
                if (JSType.isStrictlyRepresentableAsLong(value2)) {
                    return (long)value2;
                }
                return value2;
            }
            case STRING: {
                return this.stringIntern(this.source.getString(start2, len));
            }
            case ESCSTRING: {
                return this.valueOfString(start2, len, strict);
            }
            case IDENT: 
            case PRIVATE_IDENT: {
                return this.valueOfIdent(start2, len, convertUnicode);
            }
            case REGEX: {
                return this.valueOfPattern(start2, len);
            }
            case TEMPLATE: 
            case TEMPLATE_HEAD: 
            case TEMPLATE_MIDDLE: 
            case TEMPLATE_TAIL: {
                return this.valueOfString(start2, len, true);
            }
            case XML: {
                return this.valueOfXML(start2, len);
            }
            case DIRECTIVE_COMMENT: {
                return this.source.getString(start2, len);
            }
        }
        return null;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    TruffleString valueOfTaggedTemplateString(long token) {
        int savePosition = this.position;
        try {
            TruffleString truffleString = this.valueOfString(Token.descPosition(token), Token.descLength(token), true);
            return truffleString;
        }
        catch (ParserException ex) {
            TruffleString truffleString = null;
            return truffleString;
        }
        finally {
            this.reset(savePosition);
        }
    }

    public TruffleString valueOfRawString(long token) {
        int start2 = Token.descPosition(token);
        int length = Token.descLength(token);
        int savePosition = this.position;
        int end2 = start2 + length;
        this.reset(start2);
        StringBuilder sb = new StringBuilder(length);
        while (this.position < end2) {
            if (this.ch0 == '\r') {
                sb.append('\n');
                this.skip(this.ch1 == '\n' ? 2 : 1);
                continue;
            }
            sb.append(this.ch0);
            this.skip(1);
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

    protected static String message(String msgId, String ... args) {
        return ECMAErrors.getMessage(MSG_LEXER_ERROR + msgId, args);
    }

    protected void error(String message, TokenType type, int start2, int length) throws ParserException {
        long token = Token.toDesc(type, start2, length);
        int pos = Token.descPosition(token);
        int lineNum = this.source.getLine(pos);
        int columnNum = this.source.getColumn(pos);
        throw new ParserException(JSErrorType.SyntaxError, message, this.source, lineNum, columnNum, token);
    }

    public static class XMLToken
    extends LexerToken {
        public XMLToken(TruffleString expression) {
            super(expression);
        }
    }

    public static class RegexToken
    extends LexerToken {
        private final TruffleString options;

        public RegexToken(TruffleString expression, TruffleString options) {
            super(expression);
            this.options = options;
        }

        public String getOptions() {
            return this.options.toJavaStringUncached();
        }

        public TruffleString getOptionsTS() {
            return this.options;
        }

        public String toString() {
            return "/" + this.getExpression() + "/" + this.options;
        }
    }

    public static abstract class LexerToken {
        private final TruffleString expression;

        protected LexerToken(TruffleString expression) {
            this.expression = expression;
        }

        public String getExpression() {
            return this.expression.toJavaStringUncached();
        }

        public TruffleString getExpressionTS() {
            return this.expression;
        }
    }

    private static class EditStringLexer
    extends Lexer {
        final TokenType stringType;

        EditStringLexer(Lexer lexer, TokenType stringType, State stringState) {
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
                    continue;
                }
                if (this.ch0 == '$' && this.ch1 == '{') {
                    if (!primed || stringStart != this.position) {
                        if (primed) {
                            this.add(TokenType.ADD, stringStart, stringStart + 1);
                        }
                        this.add(this.stringType, stringStart, this.position);
                        primed = true;
                    }
                    this.skip(2);
                    Scanner.State expressionState = this.saveState();
                    int braceCount = 1;
                    while (!this.atEOF()) {
                        if (this.ch0 == '}') {
                            if (--braceCount == 0) {
                                break;
                            }
                        } else if (this.ch0 == '{') {
                            ++braceCount;
                        }
                        this.skip(1);
                    }
                    if (braceCount != 0) {
                        this.error(Lexer.message(Lexer.MSG_EDIT_STRING_MISSING_BRACE, new String[0]), TokenType.LBRACE, ((State)expressionState).position - 1, 1);
                    }
                    expressionState.setLimit(this.position);
                    this.skip(1);
                    stringStart = this.position;
                    this.add(TokenType.ADD, ((State)expressionState).position, ((State)expressionState).position + 1);
                    this.add(TokenType.LPAREN, ((State)expressionState).position, ((State)expressionState).position + 1);
                    Lexer lexer = new Lexer(this, (State)expressionState);
                    lexer.lexify();
                    this.add(TokenType.RPAREN, this.position - 1, this.position);
                    continue;
                }
                this.skip(1);
            }
            if (stringStart != this.limit) {
                if (primed) {
                    this.add(TokenType.ADD, stringStart, stringStart + 1);
                }
                this.add(this.stringType, stringStart, this.limit);
            }
        }
    }

    protected static interface LineInfoReceiver {
        public void lineInfo(int var1, int var2);
    }

    static class State
    extends Scanner.State {
        public final int pendingLine;
        public final int linePosition;
        public final TokenType last;

        State(int position, int limit, int line, int pendingLine, int linePosition, TokenType last) {
            super(position, limit, line);
            this.pendingLine = pendingLine;
            this.linePosition = linePosition;
            this.last = last;
        }
    }
}

