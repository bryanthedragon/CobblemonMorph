package com.oracle.js.parser;

public final class TokenLookup {
   private static final TokenType[] table = new TokenType[95];
   private static final int tableBase = 32;
   private static final int tableLimit = 126;
   private static final int tableLength = 95;

   private TokenLookup() {
   }

   public static TokenType lookupKeyword(final String content, final int position, final int length) {
      char first = content.charAt(position);
      if ('a' <= first && first <= 'z') {
         int index = first - ' ';

         for (TokenType tokenType = table[index]; tokenType != null; tokenType = tokenType.getNext()) {
            int tokenLength = tokenType.getLength();
            if (tokenLength == length) {
               if (content.regionMatches(position, tokenType.getName(), 0, length)) {
                  return tokenType;
               }
            } else if (tokenLength < length) {
               break;
            }
         }
      }

      return TokenType.IDENT;
   }

   public static TokenType lookupOperator(final char ch0, final char ch1, final char ch2, final char ch3, final int ecmaScriptVersion) {
      if (' ' < ch0 && ch0 <= '~' && ('a' > ch0 || ch0 > 'z')) {
         int index = ch0 - ' ';

         for (TokenType tokenType = table[index]; tokenType != null; tokenType = tokenType.getNext()) {
            if (tokenType.getECMAScriptVersion() <= ecmaScriptVersion) {
               String name = tokenType.getName();
               switch (name.length()) {
                  case 1:
                     return tokenType;
                  case 2:
                     if (name.charAt(1) == ch1 && (tokenType != TokenType.OPTIONAL_CHAIN || ch2 < '0' || '9' < ch2)) {
                        return tokenType;
                     }
                     break;
                  case 3:
                     if (name.charAt(1) == ch1 && name.charAt(2) == ch2) {
                        return tokenType;
                     }
                     break;
                  case 4:
                     if (name.charAt(1) == ch1 && name.charAt(2) == ch2 && name.charAt(3) == ch3) {
                        return tokenType;
                     }
               }
            }
         }
      }

      return null;
   }

   static {
      for (TokenType tokenType : TokenType.getValues()) {
         String name = tokenType.getName();
         if (name != null && tokenType.getKind() != TokenKind.SPECIAL) {
            char first = name.charAt(0);
            int index = first - ' ';

            assert index < 95 : "Token name does not fit lookup table";

            int length = tokenType.getLength();
            TokenType prev = null;

            TokenType next;
            for (next = table[index]; next != null && next.getLength() > length; next = next.getNext()) {
               prev = next;
            }

            tokenType.setNext(next);
            if (prev == null) {
               table[index] = tokenType;
            } else {
               prev.setNext(tokenType);
            }
         }
      }
   }
}
