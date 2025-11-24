
package org.graalvm.shadowed.org.jcodings.specific;

import org.graalvm.shadowed.org.jcodings.ApplyAllCaseFoldFunction;
import org.graalvm.shadowed.org.jcodings.CaseFoldCodeItem;
import org.graalvm.shadowed.org.jcodings.ISOEncoding;
import org.graalvm.shadowed.org.jcodings.IntHolder;

public final class ISO8859_1Encoding
extends ISOEncoding {
    static final short[] ISO8859_1CtypeTable = new short[]{16392, 16392, 16392, 16392, 16392, 16392, 16392, 16392, 16392, 16908, 16905, 16904, 16904, 16904, 16392, 16392, 16392, 16392, 16392, 16392, 16392, 16392, 16392, 16392, 16392, 16392, 16392, 16392, 16392, 16392, 16392, 16392, 17028, 16800, 16800, 16800, 16800, 16800, 16800, 16800, 16800, 16800, 16800, 16800, 16800, 16800, 16800, 16800, 30896, 30896, 30896, 30896, 30896, 30896, 30896, 30896, 30896, 30896, 16800, 16800, 16800, 16800, 16800, 16800, 16800, 31906, 31906, 31906, 31906, 31906, 31906, 29858, 29858, 29858, 29858, 29858, 29858, 29858, 29858, 29858, 29858, 29858, 29858, 29858, 29858, 29858, 29858, 29858, 29858, 29858, 29858, 16800, 16800, 16800, 16800, 20896, 16800, 30946, 30946, 30946, 30946, 30946, 30946, 28898, 28898, 28898, 28898, 28898, 28898, 28898, 28898, 28898, 28898, 28898, 28898, 28898, 28898, 28898, 28898, 28898, 28898, 28898, 28898, 16800, 16800, 16800, 16800, 16392, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 644, 416, 160, 160, 160, 160, 160, 160, 160, 160, 12514, 416, 160, 416, 160, 160, 160, 160, 4256, 4256, 160, 12514, 160, 416, 160, 4256, 12514, 416, 4256, 4256, 4256, 416, 13474, 13474, 13474, 13474, 13474, 13474, 13474, 13474, 13474, 13474, 13474, 13474, 13474, 13474, 13474, 13474, 13474, 13474, 13474, 13474, 13474, 13474, 13474, 160, 13474, 13474, 13474, 13474, 13474, 13474, 13474, 12514, 12514, 12514, 12514, 12514, 12514, 12514, 12514, 12514, 12514, 12514, 12514, 12514, 12514, 12514, 12514, 12514, 12514, 12514, 12514, 12514, 12514, 12514, 12514, 160, 12514, 12514, 12514, 12514, 12514, 12514, 12514, 12514};
    static final byte[] ISO8859_1ToLowerCaseTable = new byte[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 123, 124, 125, 126, 127, -128, -127, -126, -125, -124, -123, -122, -121, -120, -119, -118, -117, -116, -115, -114, -113, -112, -111, -110, -109, -108, -107, -106, -105, -104, -103, -102, -101, -100, -99, -98, -97, -96, -95, -94, -93, -92, -91, -90, -89, -88, -87, -86, -85, -84, -83, -82, -81, -80, -79, -78, -77, -76, -75, -74, -73, -72, -71, -70, -69, -68, -67, -66, -65, -32, -31, -30, -29, -28, -27, -26, -25, -24, -23, -22, -21, -20, -19, -18, -17, -16, -15, -14, -13, -12, -11, -10, -41, -8, -7, -6, -5, -4, -3, -2, -33, -32, -31, -30, -29, -28, -27, -26, -25, -24, -23, -22, -21, -20, -19, -18, -17, -16, -15, -14, -13, -12, -11, -10, -9, -8, -7, -6, -5, -4, -3, -2, -1};
    static final byte[] ISO8859_1ToUpperCaseTable = new byte[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 123, 124, 125, 126, 127, -128, -127, -126, -125, -124, -123, -122, -121, -120, -119, -118, -117, -116, -115, -114, -113, -112, -111, -110, -109, -108, -107, -106, -105, -104, -103, -102, -101, -100, -99, -98, -97, -96, -95, -94, -93, -92, -91, -90, -89, -88, -87, -86, -85, -84, -83, -82, -81, -80, -79, -78, -77, -76, -75, -74, -73, -72, -71, -70, -69, -68, -67, -66, -65, -64, -63, -62, -61, -60, -59, -58, -57, -56, -55, -54, -53, -52, -51, -50, -49, -48, -47, -46, -45, -44, -43, -42, -41, -40, -39, -38, -37, -36, -35, -34, -33, -64, -63, -62, -61, -60, -59, -58, -57, -56, -55, -54, -53, -52, -51, -50, -49, -48, -47, -46, -45, -44, -43, -42, -9, -40, -39, -38, -37, -36, -35, -34, -1};
    static final int[][] ISO8859_1CaseFoldMap = new int[][]{{192, 224}, {193, 225}, {194, 226}, {195, 227}, {196, 228}, {197, 229}, {198, 230}, {199, 231}, {200, 232}, {201, 233}, {202, 234}, {203, 235}, {204, 236}, {205, 237}, {206, 238}, {207, 239}, {208, 240}, {209, 241}, {210, 242}, {211, 243}, {212, 244}, {213, 245}, {214, 246}, {216, 248}, {217, 249}, {218, 250}, {219, 251}, {220, 252}, {221, 253}, {222, 254}};
    public static final ISO8859_1Encoding INSTANCE = new ISO8859_1Encoding();

    protected ISO8859_1Encoding() {
        super("ISO-8859-1", ISO8859_1CtypeTable, ISO8859_1ToLowerCaseTable, ISO8859_1CaseFoldMap);
    }

    @Override
    public void applyAllCaseFold(int flag, ApplyAllCaseFoldFunction fun, Object arg) {
        this.applyAllCaseFoldWithMap(this.CaseFoldMap.length, this.CaseFoldMap, true, flag, fun, arg);
    }

    @Override
    public CaseFoldCodeItem[] caseFoldCodesByString(int flag, byte[] bytes, int p, int end2) {
        int b = bytes[p] & 0xFF;
        if (65 <= b && b <= 90) {
            CaseFoldCodeItem item0 = CaseFoldCodeItem.create(1, b + 32);
            if (b == 83 && end2 > p + 1 && (bytes[p + 1] == 83 || bytes[p + 1] == 115)) {
                CaseFoldCodeItem item1 = CaseFoldCodeItem.create(2, SHARP_s);
                return new CaseFoldCodeItem[]{item0, item1};
            }
            return new CaseFoldCodeItem[]{item0};
        }
        if (97 <= b && b <= 122) {
            CaseFoldCodeItem item0 = CaseFoldCodeItem.create(1, b - 32);
            if (b == 115 && end2 > p + 1 && (bytes[p + 1] == 115 || bytes[p + 1] == 83)) {
                CaseFoldCodeItem item1 = CaseFoldCodeItem.create(2, SHARP_s);
                return new CaseFoldCodeItem[]{item0, item1};
            }
            return new CaseFoldCodeItem[]{item0};
        }
        if (192 <= b && b <= 207) {
            return new CaseFoldCodeItem[]{CaseFoldCodeItem.create(1, b + 32)};
        }
        if (208 <= b && b <= SHARP_s) {
            if (b == SHARP_s) {
                CaseFoldCodeItem item0 = CaseFoldCodeItem.create(1, 115, 115);
                CaseFoldCodeItem item1 = CaseFoldCodeItem.create(1, 83, 83);
                CaseFoldCodeItem item2 = CaseFoldCodeItem.create(1, 115, 83);
                CaseFoldCodeItem item3 = CaseFoldCodeItem.create(1, 83, 115);
                return new CaseFoldCodeItem[]{item0, item1, item2, item3};
            }
            if (b != 215) {
                return new CaseFoldCodeItem[]{CaseFoldCodeItem.create(1, b + 32)};
            }
        } else {
            if (224 <= b && b <= 239) {
                return new CaseFoldCodeItem[]{CaseFoldCodeItem.create(1, b - 32)};
            }
            if (240 <= b && b <= 254 && b != 247) {
                return new CaseFoldCodeItem[]{CaseFoldCodeItem.create(1, b - 32)};
            }
        }
        return CaseFoldCodeItem.EMPTY_FOLD_CODES;
    }

    @Override
    public int caseMap(IntHolder flagP, byte[] bytes, IntHolder pp, int end2, byte[] to, int toP, int toEnd) {
        int toStart = toP;
        int flags = flagP.value;
        while (pp.value < end2 && toP < toEnd) {
            int code;
            if ((code = bytes[pp.value++] & 0xFF) == SHARP_s) {
                if ((flags & 0x2000) != 0) {
                    to[toP++] = 83;
                    code = ((flags |= 0x40000) & 0x8000) != 0 ? 115 : 83;
                } else if ((flags & 0x80000) != 0) {
                    flags |= 0x40000;
                    to[toP++] = 115;
                    code = 115;
                }
            } else if ((ISO8859_1CtypeTable[code] & 0x400) != 0 && (flags & 0x84000) != 0) {
                flags |= 0x40000;
                code += 32;
            } else if (code != 170 && code != 186 && code != 181 && code != 255 && (ISO8859_1CtypeTable[code] & 0x40) != 0 && (flags & 0x2000) != 0) {
                flags |= 0x40000;
                code -= 32;
            }
            to[toP++] = (byte)code;
            if ((flags & 0x8000) == 0) continue;
            flags ^= 0xE000;
        }
        flagP.value = flags;
        return toP - toStart;
    }
}

