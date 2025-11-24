
package org.graalvm.shadowed.org.jcodings;

import org.graalvm.shadowed.org.jcodings.ApplyAllCaseFoldFunction;
import org.graalvm.shadowed.org.jcodings.CaseFoldCodeItem;
import org.graalvm.shadowed.org.jcodings.SingleByteEncoding;

public abstract class CaseFoldMapEncoding
extends SingleByteEncoding {
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
        for (int i = 0; i < mapSize; ++i) {
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

    protected final CaseFoldCodeItem[] getCaseFoldCodesByStringWithMap(int mapSize, int[][] map, boolean essTsettFlag, int flag, byte[] bytes, int p, int end2) {
        int b = bytes[p] & 0xFF;
        if (65 <= b && b <= 90) {
            CaseFoldCodeItem item0 = CaseFoldCodeItem.create(1, b + 32);
            if (b == 83 && essTsettFlag && end2 > p + 1 && (bytes[p + 1] == 83 || bytes[p + 1] == 115)) {
                CaseFoldCodeItem item1 = CaseFoldCodeItem.create(2, 223);
                return new CaseFoldCodeItem[]{item0, item1};
            }
            return new CaseFoldCodeItem[]{item0};
        }
        if (97 <= b && b <= 122) {
            CaseFoldCodeItem item0 = CaseFoldCodeItem.create(1, b - 32);
            if (b == 115 && essTsettFlag && end2 > p + 1 && (bytes[p + 1] == 115 || bytes[p + 1] == 83)) {
                CaseFoldCodeItem item1 = CaseFoldCodeItem.create(2, 223);
                return new CaseFoldCodeItem[]{item0, item1};
            }
            return new CaseFoldCodeItem[]{item0};
        }
        if (b == 223 && essTsettFlag) {
            CaseFoldCodeItem item0 = CaseFoldCodeItem.create(1, 115, 115);
            CaseFoldCodeItem item1 = CaseFoldCodeItem.create(1, 83, 83);
            CaseFoldCodeItem item2 = CaseFoldCodeItem.create(1, 115, 83);
            CaseFoldCodeItem item3 = CaseFoldCodeItem.create(1, 83, 115);
            return new CaseFoldCodeItem[]{item0, item1, item2, item3};
        }
        for (int i = 0; i < mapSize; ++i) {
            if (b == map[i][0]) {
                return new CaseFoldCodeItem[]{CaseFoldCodeItem.create(1, map[i][1])};
            }
            if (b != map[i][1]) continue;
            return new CaseFoldCodeItem[]{CaseFoldCodeItem.create(1, map[i][0])};
        }
        return CaseFoldCodeItem.EMPTY_FOLD_CODES;
    }

    @Override
    public void applyAllCaseFold(int flag, ApplyAllCaseFoldFunction fun, Object arg) {
        this.applyAllCaseFoldWithMap(this.CaseFoldMap.length, this.CaseFoldMap, this.foldFlag, flag, fun, arg);
    }

    @Override
    public CaseFoldCodeItem[] caseFoldCodesByString(int flag, byte[] bytes, int p, int end2) {
        return this.getCaseFoldCodesByStringWithMap(this.CaseFoldMap.length, this.CaseFoldMap, this.foldFlag, flag, bytes, p, end2);
    }

    @Override
    public boolean isCodeCType(int code, int ctype) {
        return code < 256 ? this.isCodeCTypeInternal(code, ctype) : false;
    }
}

