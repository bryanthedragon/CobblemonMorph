package org.graalvm.shadowed.org.jcodings;

public final class CaseFoldCodeItem {
   public static final CaseFoldCodeItem[] EMPTY_FOLD_CODES = new CaseFoldCodeItem[0];
   public final int byteLen;
   public final int[] code;

   private CaseFoldCodeItem(int byteLen, int[] code) {
      this.byteLen = byteLen;
      this.code = code;
   }

   public static CaseFoldCodeItem create(int byteLen, int code1) {
      return new CaseFoldCodeItem(byteLen, new int[]{code1});
   }

   public static CaseFoldCodeItem create(int byteLen, int code1, int code2) {
      return new CaseFoldCodeItem(byteLen, new int[]{code1, code2});
   }

   public static CaseFoldCodeItem create(int byteLen, int code1, int code2, int code3) {
      return new CaseFoldCodeItem(byteLen, new int[]{code1, code2, code3});
   }
}
