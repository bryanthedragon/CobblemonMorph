package org.graalvm.shadowed.org.jcodings;

public abstract class ISOEncoding extends CaseFoldMapEncoding {
   public static int SHARP_s = 223;

   protected ISOEncoding(String name, short[] CTypeTable, byte[] LowerCaseTable, int[][] CaseFoldMap) {
      this(name, CTypeTable, LowerCaseTable, CaseFoldMap, true);
   }

   protected ISOEncoding(String name, short[] CTypeTable, byte[] LowerCaseTable, int[][] CaseFoldMap, boolean foldFlag) {
      super(name, CTypeTable, LowerCaseTable, CaseFoldMap, foldFlag);
   }

   @Override
   public String getCharsetName() {
      return new String(this.getName());
   }

   @Override
   public int mbcCaseFold(int flag, byte[] bytes, IntHolder pp, int end, byte[] lower) {
      int p = pp.value;
      int lowerP = 0;
      if (bytes[p] == -33 && (flag & 1073741824) != 0) {
         lower[lowerP++] = 115;
         lower[lowerP] = 115;
         pp.value++;
         return 2;
      } else {
         lower[lowerP] = this.LowerCaseTable[bytes[p] & 255];
         pp.value++;
         return 1;
      }
   }

   @Override
   public boolean isCodeCType(int code, int ctype) {
      return code < 256 ? this.isCodeCTypeInternal(code, ctype) : false;
   }
}
