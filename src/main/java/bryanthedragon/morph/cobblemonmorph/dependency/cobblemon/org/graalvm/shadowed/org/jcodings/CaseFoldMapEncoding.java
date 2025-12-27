package org.graalvm.shadowed.org.jcodings;

public abstract class CaseFoldMapEncoding extends SingleByteEncoding {
   protected final int[][] CaseFoldMap;
   protected final boolean foldFlag;
   static final int[] SS = new int[]{115, 115};

   protected CaseFoldMapEncoding(String name, short[] CTypeTable, byte[] LowerCaseTable, int[][] CaseFoldMap) {
      this(name, CTypeTable, LowerCaseTable, CaseFoldMap, true);
   }

   protected CaseFoldMapEncoding(String name, short[] CTypeTable, byte[] LowerCaseTable, int[][] CaseFoldMap, boolean foldFlag) {
      super(name, CTypeTable, LowerCaseTable);
      this.CaseFoldMap = CaseFoldMap;
      this.foldFlag = foldFlag;
   }

   protected final int applyAllCaseFoldWithMap(int mapSize, int[][] map, boolean essTsettFlag, int flag, ApplyAllCaseFoldFunction fun, Object arg) {
      this.asciiApplyAllCaseFold(flag, fun, arg);
      int[] code = new int[]{0};

      for (int i = 0; i < mapSize; i++) {
         code[0] = map[i][1];
         fun.apply(map[i][0], code, 1, arg);
         code[0] = map[i][0];
         fun.apply(map[i][1], code, 1, arg);
      }

      if (essTsettFlag) {
         this.ssApplyAllCaseFold(flag, fun, arg);
      }

      return 0;
   }

   private void ssApplyAllCaseFold(int flag, ApplyAllCaseFoldFunction fun, Object arg) {
      fun.apply(223, SS, 2, arg);
   }

   protected final CaseFoldCodeItem[] getCaseFoldCodesByStringWithMap(int mapSize, int[][] map, boolean essTsettFlag, int flag, byte[] bytes, int p, int end) {
      int b = bytes[p] & 255;
      if (65 <= b && b <= 90) {
         CaseFoldCodeItem item0 = CaseFoldCodeItem.create(1, b + 32);
         if (b != 83 || !essTsettFlag || end <= p + 1 || bytes[p + 1] != 83 && bytes[p + 1] != 115) {
            return new CaseFoldCodeItem[]{item0};
         } else {
            CaseFoldCodeItem item1 = CaseFoldCodeItem.create(2, 223);
            return new CaseFoldCodeItem[]{item0, item1};
         }
      } else if (97 <= b && b <= 122) {
         CaseFoldCodeItem item0 = CaseFoldCodeItem.create(1, b - 32);
         if (b != 115 || !essTsettFlag || end <= p + 1 || bytes[p + 1] != 115 && bytes[p + 1] != 83) {
            return new CaseFoldCodeItem[]{item0};
         } else {
            CaseFoldCodeItem item1 = CaseFoldCodeItem.create(2, 223);
            return new CaseFoldCodeItem[]{item0, item1};
         }
      } else if (b == 223 && essTsettFlag) {
         CaseFoldCodeItem item0 = CaseFoldCodeItem.create(1, 115, 115);
         CaseFoldCodeItem item1 = CaseFoldCodeItem.create(1, 83, 83);
         CaseFoldCodeItem item2 = CaseFoldCodeItem.create(1, 115, 83);
         CaseFoldCodeItem item3 = CaseFoldCodeItem.create(1, 83, 115);
         return new CaseFoldCodeItem[]{item0, item1, item2, item3};
      } else {
         for (int i = 0; i < mapSize; i++) {
            if (b == map[i][0]) {
               return new CaseFoldCodeItem[]{CaseFoldCodeItem.create(1, map[i][1])};
            }

            if (b == map[i][1]) {
               return new CaseFoldCodeItem[]{CaseFoldCodeItem.create(1, map[i][0])};
            }
         }

         return CaseFoldCodeItem.EMPTY_FOLD_CODES;
      }
   }

   @Override
   public void applyAllCaseFold(int flag, ApplyAllCaseFoldFunction fun, Object arg) {
      this.applyAllCaseFoldWithMap(this.CaseFoldMap.length, this.CaseFoldMap, this.foldFlag, flag, fun, arg);
   }

   @Override
   public CaseFoldCodeItem[] caseFoldCodesByString(int flag, byte[] bytes, int p, int end) {
      return this.getCaseFoldCodesByStringWithMap(this.CaseFoldMap.length, this.CaseFoldMap, this.foldFlag, flag, bytes, p, end);
   }

   @Override
   public boolean isCodeCType(int code, int ctype) {
      return code < 256 ? this.isCodeCTypeInternal(code, ctype) : false;
   }
}
