
package com.oracle.js.parser;

import com.oracle.js.parser.ParserStrings;
import com.oracle.js.parser.TokenKind;
import com.oracle.truffle.api.strings.TruffleString;
import java.util.Locale;

public final class TokenType
extends Enum<TokenType> {
    public static final /* enum */ TokenType ERROR = new TokenType(TokenKind.SPECIAL, null);
    public static final /* enum */ TokenType EOF = new TokenType(TokenKind.SPECIAL, null);
    public static final /* enum */ TokenType EOL = new TokenType(TokenKind.SPECIAL, null);
    public static final /* enum */ TokenType COMMENT = new TokenType(TokenKind.SPECIAL, null);
    public static final /* enum */ TokenType DIRECTIVE_COMMENT = new TokenType(TokenKind.SPECIAL, null);
    public static final /* enum */ TokenType NOT = new TokenType(TokenKind.UNARY, "!", 15, false);
    public static final /* enum */ TokenType NE = new TokenType(TokenKind.BINARY, "!=", 9, true);
    public static final /* enum */ TokenType NE_STRICT = new TokenType(TokenKind.BINARY, "!==", 9, true);
    public static final /* enum */ TokenType MOD = new TokenType(TokenKind.BINARY, "%", 13, true);
    public static final /* enum */ TokenType ASSIGN_MOD = new TokenType(TokenKind.BINARY, "%=", 2, false);
    public static final /* enum */ TokenType BIT_AND = new TokenType(TokenKind.BINARY, "&", 8, true);
    public static final /* enum */ TokenType AND = new TokenType(TokenKind.BINARY, "&&", 5, true);
    public static final /* enum */ TokenType ASSIGN_BIT_AND = new TokenType(TokenKind.BINARY, "&=", 2, false);
    public static final /* enum */ TokenType ASSIGN_AND = new TokenType(TokenKind.BINARY, "&&=", 2, false, 12);
    public static final /* enum */ TokenType LPAREN = new TokenType(TokenKind.BRACKET, "(", 17, true);
    public static final /* enum */ TokenType RPAREN = new TokenType(TokenKind.BRACKET, ")", 0, true);
    public static final /* enum */ TokenType MUL = new TokenType(TokenKind.BINARY, "*", 13, true);
    public static final /* enum */ TokenType ASSIGN_MUL = new TokenType(TokenKind.BINARY, "*=", 2, false);
    public static final /* enum */ TokenType EXP = new TokenType(TokenKind.BINARY, "**", 14, false, 6);
    public static final /* enum */ TokenType ASSIGN_EXP = new TokenType(TokenKind.BINARY, "**=", 2, false, 6);
    public static final /* enum */ TokenType ADD = new TokenType(TokenKind.BINARY, "+", 12, true);
    public static final /* enum */ TokenType INCPREFIX = new TokenType(TokenKind.UNARY, "++", 16, true);
    public static final /* enum */ TokenType ASSIGN_ADD = new TokenType(TokenKind.BINARY, "+=", 2, false);
    public static final /* enum */ TokenType COMMARIGHT = new TokenType(TokenKind.BINARY, ",", 1, true);
    public static final /* enum */ TokenType SUB = new TokenType(TokenKind.BINARY, "-", 12, true);
    public static final /* enum */ TokenType DECPREFIX = new TokenType(TokenKind.UNARY, "--", 16, true);
    public static final /* enum */ TokenType ASSIGN_SUB = new TokenType(TokenKind.BINARY, "-=", 2, false);
    public static final /* enum */ TokenType PERIOD = new TokenType(TokenKind.BRACKET, ".", 18, true);
    public static final /* enum */ TokenType DIV = new TokenType(TokenKind.BINARY, "/", 13, true);
    public static final /* enum */ TokenType ASSIGN_DIV = new TokenType(TokenKind.BINARY, "/=", 2, false);
    public static final /* enum */ TokenType COLON = new TokenType(TokenKind.BINARY, ":");
    public static final /* enum */ TokenType SEMICOLON = new TokenType(TokenKind.BINARY, ";");
    public static final /* enum */ TokenType LT = new TokenType(TokenKind.BINARY, "<", 10, true);
    public static final /* enum */ TokenType SHL = new TokenType(TokenKind.BINARY, "<<", 11, true);
    public static final /* enum */ TokenType ASSIGN_SHL = new TokenType(TokenKind.BINARY, "<<=", 2, false);
    public static final /* enum */ TokenType LE = new TokenType(TokenKind.BINARY, "<=", 10, true);
    public static final /* enum */ TokenType ASSIGN = new TokenType(TokenKind.BINARY, "=", 2, false);
    public static final /* enum */ TokenType EQ = new TokenType(TokenKind.BINARY, "==", 9, true);
    public static final /* enum */ TokenType EQ_STRICT = new TokenType(TokenKind.BINARY, "===", 9, true);
    public static final /* enum */ TokenType ARROW = new TokenType(TokenKind.BINARY, "=>", 2, true);
    public static final /* enum */ TokenType GT = new TokenType(TokenKind.BINARY, ">", 10, true);
    public static final /* enum */ TokenType GE = new TokenType(TokenKind.BINARY, ">=", 10, true);
    public static final /* enum */ TokenType SAR = new TokenType(TokenKind.BINARY, ">>", 11, true);
    public static final /* enum */ TokenType ASSIGN_SAR = new TokenType(TokenKind.BINARY, ">>=", 2, false);
    public static final /* enum */ TokenType SHR = new TokenType(TokenKind.BINARY, ">>>", 11, true);
    public static final /* enum */ TokenType ASSIGN_SHR = new TokenType(TokenKind.BINARY, ">>>=", 2, false);
    public static final /* enum */ TokenType TERNARY = new TokenType(TokenKind.BINARY, "?", 3, false);
    public static final /* enum */ TokenType LBRACKET = new TokenType(TokenKind.BRACKET, "[", 18, true);
    public static final /* enum */ TokenType RBRACKET = new TokenType(TokenKind.BRACKET, "]", 0, true);
    public static final /* enum */ TokenType BIT_XOR = new TokenType(TokenKind.BINARY, "^", 7, true);
    public static final /* enum */ TokenType ASSIGN_BIT_XOR = new TokenType(TokenKind.BINARY, "^=", 2, false);
    public static final /* enum */ TokenType LBRACE = new TokenType(TokenKind.BRACKET, "{");
    public static final /* enum */ TokenType BIT_OR = new TokenType(TokenKind.BINARY, "|", 6, true);
    public static final /* enum */ TokenType ASSIGN_BIT_OR = new TokenType(TokenKind.BINARY, "|=", 2, false);
    public static final /* enum */ TokenType OR = new TokenType(TokenKind.BINARY, "||", 4, true);
    public static final /* enum */ TokenType ASSIGN_OR = new TokenType(TokenKind.BINARY, "||=", 2, false, 12);
    public static final /* enum */ TokenType RBRACE = new TokenType(TokenKind.BRACKET, "}");
    public static final /* enum */ TokenType BIT_NOT = new TokenType(TokenKind.UNARY, "~", 15, false);
    public static final /* enum */ TokenType ELLIPSIS = new TokenType(TokenKind.UNARY, "...");
    public static final /* enum */ TokenType NULLISHCOALESC = new TokenType(TokenKind.BINARY, "??", 4, true, 11);
    public static final /* enum */ TokenType ASSIGN_NULLCOAL = new TokenType(TokenKind.BINARY, "??=", 2, false, 12);
    public static final /* enum */ TokenType OPTIONAL_CHAIN = new TokenType(TokenKind.BRACKET, "?.", 18, true, 11);
    public static final /* enum */ TokenType AT = new TokenType(TokenKind.UNARY, "@", 0, true, 14);
    public static final /* enum */ TokenType ACCESSOR = new TokenType(TokenKind.CONTEXTUAL, "accessor");
    public static final /* enum */ TokenType AS = new TokenType(TokenKind.CONTEXTUAL, "as");
    public static final /* enum */ TokenType ASSERT = new TokenType(TokenKind.CONTEXTUAL, "assert");
    public static final /* enum */ TokenType ASYNC = new TokenType(TokenKind.CONTEXTUAL, "async");
    public static final /* enum */ TokenType AWAIT = new TokenType(TokenKind.CONTEXTUAL, "await");
    public static final /* enum */ TokenType BREAK = new TokenType(TokenKind.KEYWORD, "break");
    public static final /* enum */ TokenType CASE = new TokenType(TokenKind.KEYWORD, "case");
    public static final /* enum */ TokenType CATCH = new TokenType(TokenKind.KEYWORD, "catch");
    public static final /* enum */ TokenType CLASS = new TokenType(TokenKind.FUTURE, "class");
    public static final /* enum */ TokenType CONST = new TokenType(TokenKind.KEYWORD, "const");
    public static final /* enum */ TokenType CONTINUE = new TokenType(TokenKind.KEYWORD, "continue");
    public static final /* enum */ TokenType DEBUGGER = new TokenType(TokenKind.KEYWORD, "debugger");
    public static final /* enum */ TokenType DEFAULT = new TokenType(TokenKind.KEYWORD, "default");
    public static final /* enum */ TokenType DELETE = new TokenType(TokenKind.UNARY, "delete", 15, false);
    public static final /* enum */ TokenType DO = new TokenType(TokenKind.KEYWORD, "do");
    public static final /* enum */ TokenType ELSE = new TokenType(TokenKind.KEYWORD, "else");
    public static final /* enum */ TokenType ENUM = new TokenType(TokenKind.FUTURE, "enum");
    public static final /* enum */ TokenType EXPORT = new TokenType(TokenKind.FUTURE, "export");
    public static final /* enum */ TokenType EXTENDS = new TokenType(TokenKind.FUTURE, "extends");
    public static final /* enum */ TokenType FALSE = new TokenType(TokenKind.LITERAL, "false");
    public static final /* enum */ TokenType FINALLY = new TokenType(TokenKind.KEYWORD, "finally");
    public static final /* enum */ TokenType FOR = new TokenType(TokenKind.KEYWORD, "for");
    public static final /* enum */ TokenType FROM = new TokenType(TokenKind.CONTEXTUAL, "from");
    public static final /* enum */ TokenType FUNCTION = new TokenType(TokenKind.KEYWORD, "function");
    public static final /* enum */ TokenType GET = new TokenType(TokenKind.CONTEXTUAL, "get");
    public static final /* enum */ TokenType IF = new TokenType(TokenKind.KEYWORD, "if");
    public static final /* enum */ TokenType IMPLEMENTS = new TokenType(TokenKind.FUTURESTRICT, "implements");
    public static final /* enum */ TokenType IMPORT = new TokenType(TokenKind.FUTURE, "import");
    public static final /* enum */ TokenType IN = new TokenType(TokenKind.BINARY, "in", 10, true);
    public static final /* enum */ TokenType INSTANCEOF = new TokenType(TokenKind.BINARY, "instanceof", 10, true);
    public static final /* enum */ TokenType INTERFACE = new TokenType(TokenKind.FUTURESTRICT, "interface");
    public static final /* enum */ TokenType LET = new TokenType(TokenKind.FUTURESTRICT, "let");
    public static final /* enum */ TokenType NEW = new TokenType(TokenKind.UNARY, "new", 18, false);
    public static final /* enum */ TokenType NULL = new TokenType(TokenKind.LITERAL, "null");
    public static final /* enum */ TokenType OF = new TokenType(TokenKind.CONTEXTUAL, "of");
    public static final /* enum */ TokenType PACKAGE = new TokenType(TokenKind.FUTURESTRICT, "package");
    public static final /* enum */ TokenType PRIVATE = new TokenType(TokenKind.FUTURESTRICT, "private");
    public static final /* enum */ TokenType PROTECTED = new TokenType(TokenKind.FUTURESTRICT, "protected");
    public static final /* enum */ TokenType PUBLIC = new TokenType(TokenKind.FUTURESTRICT, "public");
    public static final /* enum */ TokenType RETURN = new TokenType(TokenKind.KEYWORD, "return");
    public static final /* enum */ TokenType SET = new TokenType(TokenKind.CONTEXTUAL, "set");
    public static final /* enum */ TokenType STATIC = new TokenType(TokenKind.FUTURESTRICT, "static");
    public static final /* enum */ TokenType SUPER = new TokenType(TokenKind.FUTURE, "super");
    public static final /* enum */ TokenType SWITCH = new TokenType(TokenKind.KEYWORD, "switch");
    public static final /* enum */ TokenType THIS = new TokenType(TokenKind.KEYWORD, "this");
    public static final /* enum */ TokenType THROW = new TokenType(TokenKind.KEYWORD, "throw");
    public static final /* enum */ TokenType TRUE = new TokenType(TokenKind.LITERAL, "true");
    public static final /* enum */ TokenType TRY = new TokenType(TokenKind.KEYWORD, "try");
    public static final /* enum */ TokenType TYPEOF = new TokenType(TokenKind.UNARY, "typeof", 15, false);
    public static final /* enum */ TokenType VAR = new TokenType(TokenKind.KEYWORD, "var");
    public static final /* enum */ TokenType VOID = new TokenType(TokenKind.UNARY, "void", 15, false);
    public static final /* enum */ TokenType WHILE = new TokenType(TokenKind.KEYWORD, "while");
    public static final /* enum */ TokenType WITH = new TokenType(TokenKind.KEYWORD, "with");
    public static final /* enum */ TokenType YIELD = new TokenType(TokenKind.FUTURESTRICT, "yield");
    public static final /* enum */ TokenType DECIMAL = new TokenType(TokenKind.LITERAL, null);
    public static final /* enum */ TokenType NON_OCTAL_DECIMAL = new TokenType(TokenKind.LITERAL, null);
    public static final /* enum */ TokenType HEXADECIMAL = new TokenType(TokenKind.LITERAL, null);
    public static final /* enum */ TokenType OCTAL_LEGACY = new TokenType(TokenKind.LITERAL, null);
    public static final /* enum */ TokenType OCTAL = new TokenType(TokenKind.LITERAL, null);
    public static final /* enum */ TokenType BINARY_NUMBER = new TokenType(TokenKind.LITERAL, null);
    public static final /* enum */ TokenType BIGINT = new TokenType(TokenKind.LITERAL, null);
    public static final /* enum */ TokenType FLOATING = new TokenType(TokenKind.LITERAL, null);
    public static final /* enum */ TokenType STRING = new TokenType(TokenKind.LITERAL, null);
    public static final /* enum */ TokenType ESCSTRING = new TokenType(TokenKind.LITERAL, null);
    public static final /* enum */ TokenType EXECSTRING = new TokenType(TokenKind.LITERAL, null);
    public static final /* enum */ TokenType IDENT = new TokenType(TokenKind.LITERAL, null);
    public static final /* enum */ TokenType REGEX = new TokenType(TokenKind.LITERAL, null);
    public static final /* enum */ TokenType XML = new TokenType(TokenKind.LITERAL, null);
    public static final /* enum */ TokenType OBJECT = new TokenType(TokenKind.LITERAL, null);
    public static final /* enum */ TokenType ARRAY = new TokenType(TokenKind.LITERAL, null);
    public static final /* enum */ TokenType TEMPLATE = new TokenType(TokenKind.LITERAL, null);
    public static final /* enum */ TokenType TEMPLATE_HEAD = new TokenType(TokenKind.LITERAL, null);
    public static final /* enum */ TokenType TEMPLATE_MIDDLE = new TokenType(TokenKind.LITERAL, null);
    public static final /* enum */ TokenType TEMPLATE_TAIL = new TokenType(TokenKind.LITERAL, null);
    public static final /* enum */ TokenType PRIVATE_IDENT = new TokenType(TokenKind.LITERAL, null);
    public static final /* enum */ TokenType COMMALEFT = new TokenType(TokenKind.IR, null);
    public static final /* enum */ TokenType DECPOSTFIX = new TokenType(TokenKind.IR, null);
    public static final /* enum */ TokenType INCPOSTFIX = new TokenType(TokenKind.IR, null);
    public static final /* enum */ TokenType SPREAD_ARGUMENT = new TokenType(TokenKind.IR, null);
    public static final /* enum */ TokenType SPREAD_ARRAY = new TokenType(TokenKind.IR, null);
    public static final /* enum */ TokenType SPREAD_OBJECT = new TokenType(TokenKind.IR, null);
    public static final /* enum */ TokenType YIELD_STAR = new TokenType(TokenKind.IR, null);
    public static final /* enum */ TokenType ASSIGN_INIT = new TokenType(TokenKind.IR, null);
    public static final /* enum */ TokenType NAMEDEVALUATION = new TokenType(TokenKind.IR, null);
    private TokenType next = null;
    private final TokenKind kind;
    private final String name;
    private final TruffleString nameTS;
    private final int precedence;
    private final boolean isLeftAssociative;
    private final int ecmaScriptVersion;
    private static final TokenType[] tokenValues;
    private static final /* synthetic */ TokenType[] $VALUES;

    public static TokenType[] values() {
        return (TokenType[])$VALUES.clone();
    }

    public static TokenType valueOf(String name) {
        return Enum.valueOf(TokenType.class, name);
    }

    private TokenType(TokenKind kind, String name) {
        this(kind, name, 0, false);
    }

    private TokenType(TokenKind kind, String name, int precedence, boolean isLeftAssociative) {
        this(kind, name, precedence, isLeftAssociative, 5);
    }

    private TokenType(TokenKind kind, String name, int precedence, boolean isLeftAssociative, int ecmaScriptVersion) {
        this.kind = kind;
        this.name = name;
        this.nameTS = name == null ? null : ParserStrings.constant(name);
        this.precedence = precedence;
        this.isLeftAssociative = isLeftAssociative;
        this.ecmaScriptVersion = ecmaScriptVersion;
    }

    public boolean needsParens(TokenType other, boolean isLeft) {
        return other.precedence != 0 && (this.precedence > other.precedence || this.precedence == other.precedence && this.isLeftAssociative && !isLeft);
    }

    public boolean isOperator(boolean in) {
        return this.kind == TokenKind.BINARY && (in || this != IN) && this.precedence != 0;
    }

    public int getLength() {
        assert (this.name != null) : "Token name not set";
        return this.name.length();
    }

    public String getName() {
        return this.name;
    }

    public TruffleString getNameTS() {
        return this.nameTS;
    }

    public String getNameOrType() {
        return this.name == null ? super.name().toLowerCase(Locale.ENGLISH) : this.getName();
    }

    public TokenType getNext() {
        return this.next;
    }

    void setNext(TokenType next) {
        this.next = next;
    }

    public TokenKind getKind() {
        return this.kind;
    }

    public int getPrecedence() {
        return this.precedence;
    }

    public boolean isLeftAssociative() {
        return this.isLeftAssociative;
    }

    public int getECMAScriptVersion() {
        return this.ecmaScriptVersion;
    }

    boolean startsWith(char c) {
        return this.name != null && !this.name.isEmpty() && this.name.charAt(0) == c;
    }

    static TokenType[] getValues() {
        return tokenValues;
    }

    public String toString() {
        return this.getNameOrType();
    }

    public boolean isAssignment() {
        switch (this) {
            case ASSIGN: 
            case ASSIGN_INIT: 
            case ASSIGN_ADD: 
            case ASSIGN_BIT_AND: 
            case ASSIGN_BIT_OR: 
            case ASSIGN_BIT_XOR: 
            case ASSIGN_DIV: 
            case ASSIGN_MOD: 
            case ASSIGN_EXP: 
            case ASSIGN_MUL: 
            case ASSIGN_SAR: 
            case ASSIGN_SHL: 
            case ASSIGN_SHR: 
            case ASSIGN_SUB: 
            case ASSIGN_AND: 
            case ASSIGN_OR: 
            case ASSIGN_NULLCOAL: {
                return true;
            }
        }
        return false;
    }

    public boolean isContextualKeyword() {
        return this.kind == TokenKind.CONTEXTUAL;
    }

    public boolean isFutureStrict() {
        return this.kind == TokenKind.FUTURESTRICT;
    }

    static {
        $VALUES = new TokenType[]{ERROR, EOF, EOL, COMMENT, DIRECTIVE_COMMENT, NOT, NE, NE_STRICT, MOD, ASSIGN_MOD, BIT_AND, AND, ASSIGN_BIT_AND, ASSIGN_AND, LPAREN, RPAREN, MUL, ASSIGN_MUL, EXP, ASSIGN_EXP, ADD, INCPREFIX, ASSIGN_ADD, COMMARIGHT, SUB, DECPREFIX, ASSIGN_SUB, PERIOD, DIV, ASSIGN_DIV, COLON, SEMICOLON, LT, SHL, ASSIGN_SHL, LE, ASSIGN, EQ, EQ_STRICT, ARROW, GT, GE, SAR, ASSIGN_SAR, SHR, ASSIGN_SHR, TERNARY, LBRACKET, RBRACKET, BIT_XOR, ASSIGN_BIT_XOR, LBRACE, BIT_OR, ASSIGN_BIT_OR, OR, ASSIGN_OR, RBRACE, BIT_NOT, ELLIPSIS, NULLISHCOALESC, ASSIGN_NULLCOAL, OPTIONAL_CHAIN, AT, ACCESSOR, AS, ASSERT, ASYNC, AWAIT, BREAK, CASE, CATCH, CLASS, CONST, CONTINUE, DEBUGGER, DEFAULT, DELETE, DO, ELSE, ENUM, EXPORT, EXTENDS, FALSE, FINALLY, FOR, FROM, FUNCTION, GET, IF, IMPLEMENTS, IMPORT, IN, INSTANCEOF, INTERFACE, LET, NEW, NULL, OF, PACKAGE, PRIVATE, PROTECTED, PUBLIC, RETURN, SET, STATIC, SUPER, SWITCH, THIS, THROW, TRUE, TRY, TYPEOF, VAR, VOID, WHILE, WITH, YIELD, DECIMAL, NON_OCTAL_DECIMAL, HEXADECIMAL, OCTAL_LEGACY, OCTAL, BINARY_NUMBER, BIGINT, FLOATING, STRING, ESCSTRING, EXECSTRING, IDENT, REGEX, XML, OBJECT, ARRAY, TEMPLATE, TEMPLATE_HEAD, TEMPLATE_MIDDLE, TEMPLATE_TAIL, PRIVATE_IDENT, COMMALEFT, DECPOSTFIX, INCPOSTFIX, SPREAD_ARGUMENT, SPREAD_ARRAY, SPREAD_OBJECT, YIELD_STAR, ASSIGN_INIT, NAMEDEVALUATION};
        tokenValues = TokenType.values();
    }
}

