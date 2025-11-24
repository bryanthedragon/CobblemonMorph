
package com.oracle.js.parser;

import com.oracle.js.parser.ECMAErrors;
import com.oracle.js.parser.ErrorManager;
import com.oracle.js.parser.JSErrorType;
import com.oracle.js.parser.Lexer;
import com.oracle.js.parser.ParserException;
import com.oracle.js.parser.Source;
import com.oracle.js.parser.Token;
import com.oracle.js.parser.TokenKind;
import com.oracle.js.parser.TokenStream;
import com.oracle.js.parser.TokenType;
import com.oracle.js.parser.ir.IdentNode;
import com.oracle.js.parser.ir.LiteralNode;
import com.oracle.truffle.api.strings.TruffleString;
import java.math.BigInteger;
import java.util.function.Function;

public abstract class AbstractParser {
    private static final String MSG_EXPECTED = "expected";
    private static final String MSG_EXPECTED_STMT = "expected.stmt";
    private static final String MSG_PARSER_ERROR = "parser.error.";
    protected final Source source;
    protected final ErrorManager errors;
    protected TokenStream stream;
    protected int k;
    protected long previousToken;
    protected long token;
    protected TokenType type;
    protected TokenType last;
    protected int start;
    protected int finish;
    protected int line;
    protected int linePosition;
    protected Lexer lexer;
    protected boolean isStrictMode;
    protected final int lineOffset;
    private static final String SOURCE_URL_PREFIX = "sourceURL=";

    protected AbstractParser(Source source, ErrorManager errors, boolean strict, int lineOffset) {
        if (source.getLength() > 0xFFFFFFF) {
            throw new RuntimeException("Source exceeds size limit of 268435455 bytes");
        }
        this.source = source;
        this.errors = errors;
        this.k = -1;
        this.token = Token.toDesc(TokenType.EOL, 0, 1);
        this.type = TokenType.EOL;
        this.last = TokenType.EOL;
        this.isStrictMode = strict;
        this.lineOffset = lineOffset;
    }

    protected final long getToken(int i) {
        while (i > this.stream.last()) {
            if (this.stream.isFull()) {
                this.stream.grow();
            }
            this.lexer.lexify();
        }
        return this.stream.get(i);
    }

    protected final TokenType T(int i) {
        return Token.descType(this.getToken(i));
    }

    protected final TokenType next() {
        do {
            this.nextOrEOL();
        } while (this.type == TokenType.EOL || this.type == TokenType.COMMENT);
        return this.type;
    }

    protected final TokenType nextOrEOL() {
        do {
            this.nextToken();
            if (this.type != TokenType.DIRECTIVE_COMMENT) continue;
            this.checkDirectiveComment();
        } while (this.type == TokenType.COMMENT || this.type == TokenType.DIRECTIVE_COMMENT);
        return this.type;
    }

    private void checkDirectiveComment() {
        if (this.source.getExplicitURL() != null) {
            return;
        }
        String comment = (String)this.lexer.getValueOf(this.token, this.isStrictMode);
        if (comment.regionMatches(4, SOURCE_URL_PREFIX, 0, SOURCE_URL_PREFIX.length())) {
            this.source.setExplicitURL(comment.substring(4 + SOURCE_URL_PREFIX.length()));
        }
    }

    private TokenType nextToken() {
        if (this.type != TokenType.EOF) {
            boolean comment;
            ++this.k;
            long lastToken = this.token;
            boolean bl = comment = this.type == TokenType.COMMENT;
            if (!comment) {
                this.last = this.type;
                this.previousToken = this.token;
            }
            this.token = this.getToken(this.k);
            this.type = Token.descType(this.token);
            if (!comment && this.last != TokenType.EOL) {
                this.finish = this.start + Token.descLength(lastToken);
            }
            if (this.type == TokenType.EOL) {
                this.line = Token.descLength(this.token);
                this.linePosition = Token.descPosition(this.token);
            } else {
                this.start = Token.descPosition(this.token);
            }
        }
        return this.type;
    }

    protected static String message(String msgId, String ... args) {
        return ECMAErrors.getMessage(MSG_PARSER_ERROR + msgId, args);
    }

    protected static String message(String msgId, IdentNode ident) {
        return ECMAErrors.getMessage(MSG_PARSER_ERROR + msgId, ident.getName());
    }

    protected final ParserException error(String message, long errorToken) {
        return this.error(JSErrorType.SyntaxError, message, errorToken);
    }

    protected final ParserException error(JSErrorType errorType, String message, long errorToken) {
        int position = Token.descPosition(errorToken);
        int lineNum = this.source.getLine(position);
        int columnNum = this.source.getColumn(position);
        return new ParserException(errorType, message, this.source, lineNum, columnNum, errorToken);
    }

    protected final ParserException error(String message) {
        return this.error(JSErrorType.SyntaxError, message);
    }

    protected final ParserException error(JSErrorType errorType, String message) {
        int position = Token.descPosition(this.token);
        int column = this.source.getColumn(position);
        return new ParserException(errorType, message, this.source, this.line, column, this.token);
    }

    protected final void warning(JSErrorType errorType, String message, long errorToken) {
        this.errors.warning(this.error(errorType, message, errorToken));
    }

    protected final String expectMessage(TokenType expected) {
        String tokenString = Token.toString(this.source, this.token);
        String msg = expected == null ? AbstractParser.message(MSG_EXPECTED_STMT, tokenString) : AbstractParser.message(MSG_EXPECTED, expected.getNameOrType(), tokenString);
        return msg;
    }

    protected final String expectMessage(TokenType expected, long errorToken) {
        String expectedName = expected.getNameOrType();
        String tokenString = Token.toString(this.source, errorToken);
        return AbstractParser.message(MSG_EXPECTED, expectedName, tokenString);
    }

    protected final void expect(TokenType expected) throws ParserException {
        this.expectDontAdvance(expected);
        this.next();
    }

    protected final void expectDontAdvance(TokenType expected) throws ParserException {
        if (this.type != expected) {
            throw this.error(this.expectMessage(expected));
        }
    }

    protected final Object getValueNoEscape() {
        try {
            return this.lexer.getValueOf(this.token, this.isStrictMode, false);
        }
        catch (ParserException e) {
            this.errors.error(e);
            return null;
        }
    }

    protected final Object getValue() {
        return this.getValue(this.token);
    }

    protected final Object getValue(long valueToken) {
        try {
            return this.lexer.getValueOf(valueToken, this.isStrictMode);
        }
        catch (ParserException e) {
            this.errors.error(e);
            return null;
        }
    }

    protected final boolean isNonStrictModeIdent() {
        return !this.isStrictMode && this.type.getKind() == TokenKind.FUTURESTRICT;
    }

    protected final IdentNode getIdent() {
        long identToken = this.token;
        if (this.type == TokenType.IDENT) {
            TruffleString ident = (TruffleString)this.getValue(identToken);
            this.next();
            return this.createIdentNode(identToken, this.finish, ident);
        }
        if (this.type.isContextualKeyword() || this.isNonStrictModeIdent()) {
            TruffleString ident = this.lexer.stringIntern(this.type.getNameTS());
            this.next();
            return new IdentNode(identToken, this.finish, ident);
        }
        throw this.error(this.expectMessage(TokenType.IDENT));
    }

    protected IdentNode createIdentNode(long identToken, int identFinish, TruffleString name) {
        assert (this.isInterned(name)) : name;
        return new IdentNode(identToken, identFinish, name);
    }

    private boolean isInterned(TruffleString name) {
        return AbstractParser.isSame(this.lexer.stringIntern(name.toJavaStringUncached()), name);
    }

    private static boolean isSame(Object a, Object b) {
        return a == b;
    }

    protected final boolean isIdentifierName() {
        return this.isIdentifierName(this.token);
    }

    protected final boolean isIdentifierName(long currentToken) {
        TokenType currentType = Token.descType(currentToken);
        assert (currentType != TokenType.IDENT);
        TokenKind kind = currentType.getKind();
        if (kind == TokenKind.KEYWORD || kind == TokenKind.FUTURE || kind == TokenKind.FUTURESTRICT || kind == TokenKind.CONTEXTUAL) {
            return true;
        }
        if (kind == TokenKind.LITERAL) {
            switch (currentType) {
                case FALSE: 
                case NULL: 
                case TRUE: {
                    return true;
                }
            }
            return false;
        }
        long identToken = Token.recast(currentToken, TokenType.IDENT);
        TruffleString ident = (TruffleString)this.getValue(identToken);
        return ident != null && !ident.isEmpty() && Character.isJavaIdentifierStart(ident.toJavaStringUncached().charAt(0));
    }

    protected final IdentNode getIdentifierName() {
        if (this.type == TokenType.IDENT) {
            return this.getIdent();
        }
        if (this.isIdentifierName()) {
            long identToken = Token.recast(this.token, TokenType.IDENT);
            TruffleString ident = (TruffleString)this.getValue(identToken);
            assert (this.isInterned(ident));
            this.next();
            return this.createIdentNode(identToken, this.finish, ident);
        }
        this.expect(TokenType.IDENT);
        return null;
    }

    protected final LiteralNode<?> getLiteral() throws ParserException {
        long literalToken = this.token;
        Object value2 = this.getValue();
        this.next();
        LiteralNode<Object> node = null;
        if (value2 == null) {
            node = LiteralNode.newInstance(literalToken, this.finish);
        } else if (value2 instanceof BigInteger) {
            node = LiteralNode.newInstance(literalToken, this.finish, (BigInteger)value2);
        } else if (value2 instanceof Number) {
            node = LiteralNode.newInstance(literalToken, this.finish, (Number)value2, this.getNumberToStringConverter());
        } else if (value2 instanceof TruffleString) {
            node = LiteralNode.newInstance(literalToken, (TruffleString)value2);
        } else if (value2 instanceof Lexer.LexerToken) {
            this.validateLexerToken((Lexer.LexerToken)value2);
            node = LiteralNode.newInstance(literalToken, this.finish, (Lexer.LexerToken)value2);
        } else assert (false) : "unknown type for LiteralNode: " + value2.getClass();
        return node;
    }

    protected void validateLexerToken(Lexer.LexerToken lexerToken) {
    }

    protected Function<Number, TruffleString> getNumberToStringConverter() {
        return null;
    }
}

