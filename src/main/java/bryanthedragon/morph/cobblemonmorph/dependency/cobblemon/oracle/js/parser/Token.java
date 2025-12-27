package com.oracle.js.parser;

public final class Token {
   public static final int LENGTH_MASK = 268435455;
   private static final int LENGTH_SHIFT = 8;
   private static final int POSITION_SHIFT = 36;

   private Token() {
   }

   public static long toDesc(final TokenType type, final int position, final int length) {
      assert length >= 0;

      assert position <= 268435455 && length <= 268435455;

      return (long)position << 36 | (long)length << 8 | type.ordinal();
   }

   public static int descPosition(final long token) {
      return (int)(token >>> 36);
   }

   public static long withDelimiter(final long token) {
      TokenType tokenType = descType(token);
      switch (tokenType) {
         case STRING:
         case ESCSTRING:
         case EXECSTRING:
         case TEMPLATE:
         case TEMPLATE_TAIL: {
            int start = descPosition(token) - 1;
            int len = descLength(token) + 2;
            return toDesc(tokenType, start, len);
         }
         case TEMPLATE_HEAD:
         case TEMPLATE_MIDDLE: {
            int start = descPosition(token) - 1;
            int len = descLength(token) + 3;
            return toDesc(tokenType, start, len);
         }
         default:
            return token;
      }
   }

   public static int descLength(final long token) {
      return (int)(token >>> 8 & 268435455L);
   }

   public static TokenType descType(final long token) {
      return TokenType.getValues()[(int)token & 0xFF];
   }

   public static long recast(final long token, final TokenType newType) {
      return token & -256L | newType.ordinal();
   }

   public static String toString(final Source source, final long token, final boolean verbose) {
      TokenType type = descType(token);
      String result;
      if (source != null && type.getKind() == TokenKind.LITERAL) {
         result = source.getString(token);
      } else {
         result = type.getNameOrType();
      }

      if (verbose) {
         int position = descPosition(token);
         int length = descLength(token);
         result = result + " (" + position + ", " + length + ")";
      }

      return result;
   }

   public static String toString(final Source source, final long token) {
      return toString(source, token, false);
   }
}
