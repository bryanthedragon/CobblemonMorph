
package com.oracle.js.parser;

import com.oracle.js.parser.Source;
import com.oracle.js.parser.TokenKind;
import com.oracle.js.parser.TokenType;

public final class Token {
    public static final int LENGTH_MASK = 0xFFFFFFF;
    private static final int LENGTH_SHIFT = 8;
    private static final int POSITION_SHIFT = 36;

    private Token() {
    }

    public static long toDesc(TokenType type, int position, int length) {
        assert (length >= 0);
        assert (position <= 0xFFFFFFF && length <= 0xFFFFFFF);
        return (long)position << 36 | (long)length << 8 | (long)type.ordinal();
    }

    public static int descPosition(long token) {
        return (int)(token >>> 36);
    }

    public static long withDelimiter(long token) {
        TokenType tokenType = Token.descType(token);
        switch (tokenType) {
            case STRING: 
            case ESCSTRING: 
            case EXECSTRING: 
            case TEMPLATE: 
            case TEMPLATE_TAIL: {
                int start2 = Token.descPosition(token) - 1;
                int len = Token.descLength(token) + 2;
                return Token.toDesc(tokenType, start2, len);
            }
            case TEMPLATE_HEAD: 
            case TEMPLATE_MIDDLE: {
                int start3 = Token.descPosition(token) - 1;
                int len = Token.descLength(token) + 3;
                return Token.toDesc(tokenType, start3, len);
            }
        }
        return token;
    }

    public static int descLength(long token) {
        return (int)(token >>> 8 & 0xFFFFFFFL);
    }

    public static TokenType descType(long token) {
        return TokenType.getValues()[(int)token & 0xFF];
    }

    public static long recast(long token, TokenType newType) {
        return token & 0xFFFFFFFFFFFFFF00L | (long)newType.ordinal();
    }

    public static String toString(Source source, long token, boolean verbose) {
        TokenType type = Token.descType(token);
        Object result = source != null && type.getKind() == TokenKind.LITERAL ? source.getString(token) : type.getNameOrType();
        if (verbose) {
            int position = Token.descPosition(token);
            int length = Token.descLength(token);
            result = (String)result + " (" + position + ", " + length + ")";
        }
        return result;
    }

    public static String toString(Source source, long token) {
        return Token.toString(source, token, false);
    }
}

