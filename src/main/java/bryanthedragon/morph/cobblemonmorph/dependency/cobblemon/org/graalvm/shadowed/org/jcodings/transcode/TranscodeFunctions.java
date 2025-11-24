
package org.graalvm.shadowed.org.jcodings.transcode;

import java.util.Arrays;
import org.graalvm.shadowed.org.jcodings.transcode.TranscodeTableSupport;
import org.graalvm.shadowed.org.jcodings.transcode.Transcoding;
import org.graalvm.shadowed.org.jcodings.transcode.specific.From_UTF8_MAC_Transcoder;

public class TranscodeFunctions {
    public static final int BE = 1;
    public static final int LE = 2;
    public static final int from_UTF_16BE_D8toDB_00toFF = Transcoding.WORDINDEX2INFO(39);
    public static final int from_UTF_16LE_00toFF_D8toDB = Transcoding.WORDINDEX2INFO(5);
    public static final byte G0_ASCII = 0;
    public static final byte G0_JISX0208_1978 = 1;
    public static final byte G0_JISX0208_1983 = 2;
    public static final byte G0_JISX0201_KATAKANA = 3;
    public static final int EMACS_MULE_LEADING_CODE_JISX0208_1978 = 144;
    public static final int EMACS_MULE_LEADING_CODE_JISX0208_1983 = 146;
    public static final byte[] tbl0208 = new byte[]{33, 35, 33, 86, 33, 87, 33, 34, 33, 38, 37, 114, 37, 33, 37, 35, 37, 37, 37, 39, 37, 41, 37, 99, 37, 101, 37, 103, 37, 67, 33, 60, 37, 34, 37, 36, 37, 38, 37, 40, 37, 42, 37, 43, 37, 45, 37, 47, 37, 49, 37, 51, 37, 53, 37, 55, 37, 57, 37, 59, 37, 61, 37, 63, 37, 65, 37, 68, 37, 70, 37, 72, 37, 74, 37, 75, 37, 76, 37, 77, 37, 78, 37, 79, 37, 82, 37, 85, 37, 88, 37, 91, 37, 94, 37, 95, 37, 96, 37, 97, 37, 98, 37, 100, 37, 102, 37, 104, 37, 105, 37, 106, 37, 107, 37, 108, 37, 109, 37, 111, 37, 115, 33, 43, 33, 44};
    public static final int iso2022jp_decoder_jisx0208_rest = Transcoding.WORDINDEX2INFO(16);
    public static final int iso2022jp_kddi_decoder_jisx0208_rest = Transcoding.WORDINDEX2INFO(16);
    private static final int STATUS_BUF_SIZE = 16;
    private static final int TOTAL_BUF_SIZE = 24;
    private static final int from_utf8_mac_nfc2 = Transcoding.WORDINDEX2INFO(35578);
    private static final int ESCAPE_END = 0;
    private static final int ESCAPE_NORMAL = 1;
    private static final int NEWLINE_NORMAL = 0;
    private static final int NEWLINE_JUST_AFTER_CR = 1;
    private static final int MET_LF = 1;
    private static final int MET_CRLF = 2;
    private static final int MET_CR = 4;

    public static int funSoToUTF16(byte[] statep, byte[] sBytes, int sStart, int l, byte[] o, int oStart, int osize) {
        int sp = 0;
        if (statep[sp] == 0) {
            o[oStart++] = -2;
            o[oStart++] = -1;
            statep[sp] = 1;
            return 2 + TranscodeFunctions.funSoToUTF16BE(statep, sBytes, sStart, l, o, oStart, osize);
        }
        return TranscodeFunctions.funSoToUTF16BE(statep, sBytes, sStart, l, o, oStart, osize);
    }

    public static int funSoToUTF16BE(byte[] statep, byte[] s, int sStart, int l, byte[] o, int oStart, int osize) {
        int s0 = s[sStart] & 0xFF;
        if ((s0 & 0x80) == 0) {
            o[oStart] = 0;
            o[oStart + 1] = (byte)s0;
            return 2;
        }
        if ((s0 & 0xE0) == 192) {
            int s1 = s[sStart + 1] & 0xFF;
            o[oStart] = (byte)(s0 >> 2 & 7);
            o[oStart + 1] = (byte)((s0 & 3) << 6 | s1 & 0x3F);
            return 2;
        }
        if ((s0 & 0xF0) == 224) {
            int s1 = s[sStart + 1] & 0xFF;
            int s2 = s[sStart + 2] & 0xFF;
            o[oStart] = (byte)(s0 << 4 | s1 >> 2 ^ 0x20);
            o[oStart + 1] = (byte)(s1 << 6 | s2 ^ 0x80);
            return 2;
        }
        int s1 = s[sStart + 1] & 0xFF;
        int s2 = s[sStart + 2] & 0xFF;
        int s3 = s[sStart + 3] & 0xFF;
        int w = ((s0 & 7) << 2 | s1 >> 4 & 3) - 1;
        o[oStart] = (byte)(0xD8 | w >> 2);
        o[oStart + 1] = (byte)(w << 6 | (s1 & 0xF) << 2 | (s2 >> 4) - 8);
        o[oStart + 2] = (byte)(0xDC | s2 >> 2 & 3);
        o[oStart + 3] = (byte)(s2 << 6 | s3 & 0xFFFFFF7F);
        return 4;
    }

    public static int funSoToUTF16LE(byte[] statep, byte[] s, int sStart, int l, byte[] o, int oStart, int osize) {
        int s0 = s[sStart] & 0xFF;
        if ((s0 & 0x80) == 0) {
            o[oStart + 1] = 0;
            o[oStart] = (byte)s0;
            return 2;
        }
        if ((s0 & 0xE0) == 192) {
            int s1 = s[sStart + 1] & 0xFF;
            o[oStart + 1] = (byte)(s0 >> 2 & 7);
            o[oStart] = (byte)((s0 & 3) << 6 | s1 & 0x3F);
            return 2;
        }
        if ((s0 & 0xF0) == 224) {
            int s1 = s[sStart + 1] & 0xFF;
            int s2 = s[sStart + 2] & 0xFF;
            o[oStart + 1] = (byte)(s0 << 4 | s1 >> 2 ^ 0x20);
            o[oStart] = (byte)(s1 << 6 | s2 ^ 0x80);
            return 2;
        }
        int s1 = s[sStart + 1] & 0xFF;
        int s2 = s[sStart + 2] & 0xFF;
        int s3 = s[sStart + 3] & 0xFF;
        int w = ((s0 & 7) << 2 | s1 >> 4 & 3) - 1;
        o[oStart + 1] = (byte)(0xD8 | w >> 2);
        o[oStart] = (byte)(w << 6 | (s1 & 0xF) << 2 | (s2 >> 4) - 8);
        o[oStart + 3] = (byte)(0xDC | s2 >> 2 & 3);
        o[oStart + 2] = (byte)(s2 << 6 | s3 & 0xFFFFFF7F);
        return 4;
    }

    public static int funSoToUTF32(byte[] statep, byte[] s, int sStart, int l, byte[] o, int oStart, int osize) {
        int sp = 0;
        if (statep[sp] == 0) {
            o[oStart++] = 0;
            o[oStart++] = 0;
            o[oStart++] = -2;
            o[oStart++] = -1;
            statep[sp] = 1;
            return 4 + TranscodeFunctions.funSoToUTF32BE(statep, s, sStart, l, o, oStart, osize);
        }
        return TranscodeFunctions.funSoToUTF32BE(statep, s, sStart, l, o, oStart, osize);
    }

    public static int funSoToUTF32BE(byte[] statep, byte[] s, int sStart, int l, byte[] o, int oStart, int osize) {
        int s0 = s[sStart] & 0xFF;
        o[oStart] = 0;
        if ((s0 & 0x80) == 0) {
            o[oStart + 2] = 0;
            o[oStart + 1] = 0;
            o[oStart + 3] = (byte)s0;
        } else if ((s0 & 0xE0) == 192) {
            int s1 = s[sStart + 1] & 0xFF;
            o[oStart + 1] = 0;
            o[oStart + 2] = (byte)(s0 >> 2 & 7);
            o[oStart + 3] = (byte)((s0 & 3) << 6 | s1 & 0x3F);
        } else if ((s0 & 0xF0) == 224) {
            int s1 = s[sStart + 1] & 0xFF;
            int s2 = s[sStart + 2] & 0xFF;
            o[oStart + 1] = 0;
            o[oStart + 2] = (byte)(s0 << 4 | s1 >> 2 ^ 0x20);
            o[oStart + 3] = (byte)(s1 << 6 | s2 ^ 0x80);
        } else {
            int s1 = s[sStart + 1] & 0xFF;
            int s2 = s[sStart + 2] & 0xFF;
            int s3 = s[sStart + 3] & 0xFF;
            o[oStart + 1] = (byte)((s0 & 7) << 2 | s1 >> 4 & 3);
            o[oStart + 2] = (byte)((s1 & 0xF) << 4 | s2 >> 2 & 0xF);
            o[oStart + 3] = (byte)((s2 & 3) << 6 | s3 & 0x3F);
        }
        return 4;
    }

    public static int funSoToUTF32LE(byte[] statep, byte[] s, int sStart, int l, byte[] o, int oStart, int osize) {
        o[oStart + 3] = 0;
        int s0 = s[sStart] & 0xFF;
        if ((s0 & 0x80) == 0) {
            o[oStart + 1] = 0;
            o[oStart + 2] = 0;
            o[oStart] = (byte)s0;
        } else if ((s[sStart] & 0xE0) == 192) {
            int s1 = s[sStart + 1] & 0xFF;
            o[oStart + 2] = 0;
            o[oStart + 1] = (byte)(s0 >> 2 & 7);
            o[oStart] = (byte)((s0 & 3) << 6 | s1 & 0x3F);
        } else if ((s[sStart] & 0xF0) == 224) {
            int s1 = s[sStart + 1] & 0xFF;
            int s2 = s[sStart + 2] & 0xFF;
            o[oStart + 2] = 0;
            o[oStart + 1] = (byte)(s0 << 4 | s1 >> 2 ^ 0x20);
            o[oStart] = (byte)(s1 << 6 | s2 ^ 0x80);
        } else {
            int s1 = s[sStart + 1] & 0xFF;
            int s2 = s[sStart + 2] & 0xFF;
            int s3 = s[sStart + 3] & 0xFF;
            o[oStart + 2] = (byte)((s0 & 7) << 2 | s1 >> 4 & 3);
            o[oStart + 1] = (byte)((s1 & 0xF) << 4 | s2 >> 2 & 0xF);
            o[oStart] = (byte)((s2 & 3) << 6 | s3 & 0x3F);
        }
        return 4;
    }

    public static int funSiFromUTF32(byte[] statep, byte[] s, int sStart, int l) {
        int s0 = s[sStart] & 0xFF;
        int s1 = s[sStart + 1] & 0xFF;
        int s2 = s[sStart + 2] & 0xFF;
        byte[] sp = statep;
        switch (sp[0]) {
            case 0: {
                int s3 = s[sStart + 3] & 0xFF;
                if (s0 == 0 && s1 == 0 && s2 == 254 && s3 == 255) {
                    sp[0] = 1;
                    return 10;
                }
                if (s0 != 255 || s1 != 254 || s2 != 0 || s3 != 0) break;
                sp[0] = 2;
                return 10;
            }
            case 1: {
                if ((s0 != 0 || 0 >= s1 || s1 > 16) && (s1 != 0 || s2 >= 216 && 223 >= s2)) break;
                return 15;
            }
            case 2: {
                int s3 = s[sStart + 3] & 0xFF;
                if (s3 != 0 || (0 >= s2 || s2 > 16) && (s2 != 0 || s1 >= 216 && 223 >= s1)) break;
                return 15;
            }
        }
        return 7;
    }

    public static int funSoFromUTF32(byte[] statep, byte[] s, int sStart, int l, byte[] o, int oStart, int osize) {
        switch (statep[0]) {
            case 1: {
                return TranscodeFunctions.funSoFromUTF32BE(statep, s, sStart, l, o, oStart, osize);
            }
            case 2: {
                return TranscodeFunctions.funSoFromUTF32LE(statep, s, sStart, l, o, oStart, osize);
            }
        }
        return 0;
    }

    public static int funSoFromUTF32BE(byte[] statep, byte[] s, int sStart, int l, byte[] o, int oStart, int osize) {
        int s1 = s[sStart + 1] & 0xFF;
        int s2 = s[sStart + 2] & 0xFF;
        int s3 = s[sStart + 3] & 0xFF;
        if (s1 == 0) {
            if (s2 == 0 && s3 < 128) {
                o[oStart] = (byte)s3;
                return 1;
            }
            if (s2 < 8) {
                o[oStart] = (byte)(0xC0 | s2 << 2 | s3 >> 6);
                o[oStart + 1] = (byte)(0x80 | s3 & 0x3F);
                return 2;
            }
            o[oStart] = (byte)(0xE0 | s2 >> 4);
            o[oStart + 1] = (byte)(0x80 | (s2 & 0xF) << 2 | s3 >> 6);
            o[oStart + 2] = (byte)(0x80 | s3 & 0x3F);
            return 3;
        }
        o[oStart] = (byte)(0xF0 | s1 >> 2);
        o[oStart + 1] = (byte)(0x80 | (s1 & 3) << 4 | s2 >> 4);
        o[oStart + 2] = (byte)(0x80 | (s2 & 0xF) << 2 | s3 >> 6);
        o[oStart + 3] = (byte)(0x80 | s3 & 0x3F);
        return 4;
    }

    public static int funSoFromUTF32LE(byte[] statep, byte[] s, int sStart, int l, byte[] o, int oStart, int osize) {
        int s0 = s[sStart] & 0xFF;
        int s1 = s[sStart + 1] & 0xFF;
        int s2 = s[sStart + 2] & 0xFF;
        if (s2 == 0) {
            if (s1 == 0 && s0 < 128) {
                o[oStart] = (byte)s0;
                return 1;
            }
            if (s1 < 8) {
                o[oStart] = (byte)(0xC0 | s1 << 2 | s0 >> 6);
                o[oStart + 1] = (byte)(0x80 | s0 & 0x3F);
                return 2;
            }
            o[oStart] = (byte)(0xE0 | s1 >> 4);
            o[oStart + 1] = (byte)(0x80 | (s1 & 0xF) << 2 | s0 >> 6);
            o[oStart + 2] = (byte)(0x80 | s0 & 0x3F);
            return 3;
        }
        o[oStart] = (byte)(0xF0 | s2 >> 2);
        o[oStart + 1] = (byte)(0x80 | (s2 & 3) << 4 | s1 >> 4);
        o[oStart + 2] = (byte)(0x80 | (s1 & 0xF) << 2 | s0 >> 6);
        o[oStart + 3] = (byte)(0x80 | s0 & 0x3F);
        return 4;
    }

    public static int funSiFromUTF16(byte[] statep, byte[] s, int sStart, int l) {
        int s0 = s[sStart] & 0xFF;
        byte[] sp = statep;
        switch (sp[0]) {
            case 0: {
                int s1 = s[sStart + 1] & 0xFF;
                if (s0 == 254 && s1 == 255) {
                    sp[0] = 1;
                    return 10;
                }
                if (s0 != 255 || s1 != 254) break;
                sp[0] = 2;
                return 10;
            }
            case 1: {
                if (s0 < 216 || 223 < s0) {
                    return 15;
                }
                if (s0 > 219) break;
                return from_UTF_16BE_D8toDB_00toFF;
            }
            case 2: {
                int s1 = s[sStart + 1] & 0xFF;
                if (s1 < 216 || 223 < s1) {
                    return 15;
                }
                if (s1 > 219) break;
                return from_UTF_16LE_00toFF_D8toDB;
            }
        }
        return 7;
    }

    public static int funSoFromUTF16(byte[] statep, byte[] s, int sStart, int l, byte[] o, int oStart, int osize) {
        switch (statep[0]) {
            case 1: {
                return TranscodeFunctions.funSoFromUTF16BE(statep, s, sStart, l, o, oStart, osize);
            }
            case 2: {
                return TranscodeFunctions.funSoFromUTF16LE(statep, s, sStart, l, o, oStart, osize);
            }
        }
        return 0;
    }

    public static int funSoFromUTF16BE(byte[] statep, byte[] s, int sStart, int l, byte[] o, int oStart, int osize) {
        int s0 = s[sStart] & 0xFF;
        int s1 = s[sStart + 1] & 0xFF;
        if (s0 == 0 && s1 < 128) {
            o[oStart] = (byte)s1;
            return 1;
        }
        if (s0 < 8) {
            o[oStart] = (byte)(0xC0 | s0 << 2 | s1 >> 6);
            o[oStart + 1] = (byte)(0x80 | s1 & 0x3F);
            return 2;
        }
        if ((s0 & 0xF8) != 216) {
            o[oStart] = (byte)(0xE0 | s0 >> 4);
            o[oStart + 1] = (byte)(0x80 | (s0 & 0xF) << 2 | s1 >> 6);
            o[oStart + 2] = (byte)(0x80 | s1 & 0x3F);
            return 3;
        }
        int s2 = s[sStart + 2] & 0xFF;
        int s3 = s[sStart + 3] & 0xFF;
        long u = ((s0 & 3) << 2 | s1 >> 6) + 1;
        o[oStart] = (byte)(0xF0L | u >> 2);
        o[oStart + 1] = (byte)(0x80L | (u & 3L) << 4 | (long)(s1 >> 2 & 0xF));
        o[oStart + 2] = (byte)(0x80 | (s1 & 3) << 4 | (s2 & 3) << 2 | s3 >> 6);
        o[oStart + 3] = (byte)(0x80 | s3 & 0x3F);
        return 4;
    }

    public static int funSoFromUTF16LE(byte[] statep, byte[] s, int sStart, int l, byte[] o, int oStart, int osize) {
        int s0 = s[sStart] & 0xFF;
        int s1 = s[sStart + 1] & 0xFF;
        if (s1 == 0 && s0 < 128) {
            o[oStart] = (byte)s0;
            return 1;
        }
        if (s1 < 8) {
            o[oStart] = (byte)(0xC0 | s1 << 2 | s0 >> 6);
            o[oStart + 1] = (byte)(0x80 | s0 & 0x3F);
            return 2;
        }
        if ((s1 & 0xF8) != 216) {
            o[oStart] = (byte)(0xE0 | s1 >> 4);
            o[oStart + 1] = (byte)(0x80 | (s1 & 0xF) << 2 | s0 >> 6);
            o[oStart + 2] = (byte)(0x80 | s0 & 0x3F);
            return 3;
        }
        int s2 = s[sStart + 2] & 0xFF;
        int s3 = s[sStart + 3] & 0xFF;
        long u = ((s1 & 3) << 2 | s0 >> 6) + 1;
        o[oStart] = (byte)(0xF0L | u >> 2);
        o[oStart + 1] = (byte)(0x80L | (u & 3L) << 4 | (long)(s0 >> 2 & 0xF));
        o[oStart + 2] = (byte)(0x80 | (s0 & 3) << 4 | (s3 & 3) << 2 | s2 >> 6);
        o[oStart + 3] = (byte)(0x80 | s2 & 0x3F);
        return 4;
    }

    public static int funSoEucjp2Sjis(byte[] statep, byte[] s, int sStart, int _l, byte[] o, int oStart, int osize) {
        int s0 = s[sStart] & 0xFF;
        int s1 = s[sStart + 1] & 0xFF;
        if (s0 == 142) {
            o[oStart] = (byte)s1;
            return 1;
        }
        int m = s0 & 1;
        int h = s0 + m >> 1;
        h += s0 < 223 ? 48 : 112;
        int l = s1 - m * 94 - 3;
        if (127 <= l) {
            ++l;
        }
        o[oStart] = (byte)h;
        o[oStart + 1] = (byte)l;
        return 2;
    }

    public static int funSoSjis2Eucjp(byte[] statep, byte[] s, int sStart, int _l, byte[] o, int oStart, int osize) {
        int s0 = s[sStart] & 0xFF;
        if (_l == 1) {
            o[oStart] = -114;
            o[oStart + 1] = (byte)s0;
            return 2;
        }
        int h = s0;
        int l = s[sStart + 1] & 0xFF;
        if (224 <= h) {
            h -= 64;
        }
        int n = l < 128 ? 97 : 96;
        h = h * 2 - 97;
        if (254 < (l += n)) {
            l -= 94;
            ++h;
        }
        o[oStart] = (byte)h;
        o[oStart + 1] = (byte)l;
        return 2;
    }

    public static int funSoFromGB18030(byte[] statep, byte[] s, int sStart, int l, byte[] o, int oStart, int osize) {
        long s0 = s[sStart] & 0xFF;
        long s1 = s[sStart + 1] & 0xFF;
        long s2 = s[sStart + 2] & 0xFF;
        long s3 = s[sStart + 3] & 0xFF;
        long u = (s0 - 144L) * 10L * 126L * 10L + (s1 - 48L) * 126L * 10L + (s2 - 129L) * 10L + (s3 - 48L) + 65536L & 0xFFFFFFFFL;
        o[oStart] = (byte)(0xF0L | u >>> 18);
        o[oStart + 1] = (byte)(0x80L | u >>> 12 & 0x3FL);
        o[oStart + 2] = (byte)(0x80L | u >>> 6 & 0x3FL);
        o[oStart + 3] = (byte)(0x80L | u & 0x3FL);
        return 4;
    }

    public static int funSioFromGB18030(byte[] statep, byte[] s, int sStart, int l, int info, byte[] o, int oStart, int osize) {
        long u;
        long s0 = s[sStart] & 0xFF;
        long s1 = s[sStart + 1] & 0xFF;
        long diff = info >> 8;
        if ((diff & 0x20000L) != 0L) {
            long s2 = s[sStart + 2] & 0xFF;
            long s3 = s[sStart + 3] & 0xFF;
            u = ((s0 * 10L + s1) * 126L + s2) * 10L + s3 - diff - 0x170000L & 0xFFFFFFFFL;
        } else {
            u = s0 * 256L + s1 + 24055L - diff & 0xFFFFFFFFL;
        }
        o[oStart] = (byte)(0xE0L | u >>> 12);
        o[oStart + 1] = (byte)(0x80L | u >>> 6 & 0x3FL);
        o[oStart + 2] = (byte)(0x80L | u & 0x3FL);
        return 3;
    }

    public static int funSoToGB18030(byte[] statep, byte[] s, int sStart, int l, byte[] o, int oStart, int osize) {
        long s0 = s[sStart] & 0xFF;
        long s1 = s[sStart + 1] & 0xFF;
        long s2 = s[sStart + 2] & 0xFF;
        long s3 = s[sStart + 3] & 0xFF;
        long u = (s0 & 7L) << 18 | (s1 & 0x3FL) << 12 | (s2 & 0x3FL) << 6 | s3 & 0x3FL;
        o[oStart + 3] = (byte)(48L + (u -= 65536L) % 10L);
        o[oStart + 2] = (byte)(129L + (u /= 10L) % 126L);
        o[oStart + 1] = (byte)(48L + (u /= 126L) % 10L);
        o[oStart] = (byte)(144L + u / 10L);
        return 4;
    }

    public static int funSioToGB18030(byte[] statep, byte[] s, int sStart, int l, int info, byte[] o, int oStart, int osize) {
        long s0 = s[sStart] & 0xFF;
        long s1 = s[sStart + 1] & 0xFF;
        long s2 = s[sStart + 2] & 0xFF;
        long diff = info >>> 8;
        long u = (s0 & 0xFL) << 12 | (s1 & 0x3FL) << 6 | s2 & 0x3FL;
        if ((diff & 0x20000L) != 0L) {
            u += diff + 0x170000L;
            u -= 1688980L;
            o[oStart + 3] = (byte)(48L + (u += 2L) % 10L);
            u /= 10L;
            o[oStart + 2] = (byte)(129L + (u += 50L) % 126L);
            u /= 126L;
            o[oStart + 1] = (byte)(48L + ++u % 10L);
            o[oStart] = (byte)(129L + (u /= 10L));
            return 4;
        }
        o[oStart + 1] = (byte)((u += diff - 24055L) % 256L);
        o[oStart] = (byte)(u / 256L);
        return 2;
    }

    public static int iso2022jpInit(byte[] state) {
        state[0] = 0;
        return 0;
    }

    public static int funSoCp50220Encoder(byte[] statep, byte[] s, int sStart, int l, byte[] o, int oStart, int oSize) {
        int s1;
        int s0;
        int output0 = oStart;
        byte[] sp = statep;
        if (sp[0] == 3) {
            int c = sp[2] & 0x7F;
            int p = (c - 33) * 2;
            byte[] pBytes = tbl0208;
            if (sp[1] != 2) {
                o[oStart++] = 27;
                o[oStart++] = 36;
                o[oStart++] = 66;
            }
            sp[0] = 2;
            o[oStart++] = pBytes[p++];
            s0 = s[sStart] & 0xFF;
            s1 = s[sStart + 1] & 0xFF;
            if (l == 2 && s0 == 142) {
                if (s1 == 222) {
                    o[oStart++] = (byte)(pBytes[p] + 1);
                    return oStart - output0;
                }
                if (s1 == 223 && 74 <= c && c <= 78) {
                    o[oStart++] = (byte)(pBytes[p] + 2);
                    return oStart - output0;
                }
            }
            o[oStart++] = pBytes[p];
        }
        s0 = s[sStart] & 0xFF;
        if (l == 2 && s0 == 142) {
            s1 = s[sStart + 1] & 0xFF;
            int p = (s1 - 161) * 2;
            byte[] pBytes = tbl0208;
            if (161 <= s1 && s1 <= 181 || 197 <= s1 && s1 <= 201 || 207 <= s1 && s1 <= 223) {
                if (sp[0] != 2) {
                    o[oStart++] = 27;
                    o[oStart++] = 36;
                    o[oStart++] = 66;
                    sp[0] = 2;
                }
                o[oStart++] = pBytes[p++];
                o[oStart++] = pBytes[p];
                return oStart - output0;
            }
            sp[2] = (byte)s1;
            sp[1] = sp[0];
            sp[0] = 3;
            return oStart - output0;
        }
        oStart += TranscodeFunctions.funSoCp5022xEncoder(statep, s, sStart, l, o, oStart, oSize);
        return oStart - output0;
    }

    public static int funSoCp5022xEncoder(byte[] statep, byte[] s, int sStart, int l, byte[] o, int oStart, int oSize) {
        int newstate;
        byte[] sp = statep;
        int output0 = oStart;
        if (l == 1) {
            newstate = 0;
        } else if ((s[sStart] & 0xFF) == 142) {
            ++sStart;
            l = 1;
            newstate = 3;
        } else {
            newstate = 2;
        }
        if (sp[0] != newstate) {
            if (newstate == 0) {
                o[oStart++] = 27;
                o[oStart++] = 40;
                o[oStart++] = 66;
            } else if (newstate == 3) {
                o[oStart++] = 27;
                o[oStart++] = 40;
                o[oStart++] = 73;
            } else {
                o[oStart++] = 27;
                o[oStart++] = 36;
                o[oStart++] = 66;
            }
            sp[0] = (byte)newstate;
        }
        int s0 = s[sStart] & 0xFF;
        if (l == 1) {
            o[oStart++] = (byte)(s0 & 0x7F);
        } else {
            int s1 = s[sStart + 1] & 0xFF;
            o[oStart++] = (byte)(s0 & 0x7F);
            o[oStart++] = (byte)(s1 & 0x7F);
        }
        return oStart - output0;
    }

    public static int finishCp50220Encoder(byte[] statep, byte[] o, int oStart, int size) {
        byte[] sp = statep;
        int output0 = oStart;
        if (sp[0] == 0) {
            return 0;
        }
        if (sp[0] == 3) {
            int c = sp[2] & 0x7F;
            int p = (c - 33) * 2;
            byte[] pBytes = tbl0208;
            if (sp[1] != 2) {
                o[oStart++] = 27;
                o[oStart++] = 36;
                o[oStart++] = 66;
            }
            sp[0] = 2;
            o[oStart++] = pBytes[p++];
            o[oStart++] = pBytes[p];
        }
        o[oStart++] = 27;
        o[oStart++] = 40;
        o[oStart++] = 66;
        sp[0] = 0;
        return oStart - output0;
    }

    public static int iso2022jpEncoderResetSequenceSize(byte[] statep) {
        byte[] sp = statep;
        if (sp[0] != 0) {
            return 3;
        }
        return 0;
    }

    public static int funSiIso50220jpDecoder(byte[] statep, byte[] s, int sStart, int l) {
        int s0 = s[sStart] & 0xFF;
        byte[] sp = statep;
        if (sp[0] == 0) {
            return 1;
        }
        if (33 <= s0 && s0 <= 126) {
            return iso2022jp_decoder_jisx0208_rest;
        }
        return 7;
    }

    public static int funSoIso2022jpDecoder(byte[] statep, byte[] s, int sStart, int l, byte[] o, int oStart, int oSize) {
        int s0 = s[sStart] & 0xFF;
        int s1 = s[sStart + 1] & 0xFF;
        byte[] sp = statep;
        if (s0 == 27) {
            if (s1 == 40) {
                switch (s[sStart + l - 1] & 0xFF) {
                    case 66: 
                    case 74: {
                        sp[0] = 0;
                    }
                }
            } else {
                switch (s[sStart + l - 1]) {
                    case 64: {
                        sp[0] = 1;
                        break;
                    }
                    case 66: {
                        sp[0] = 2;
                    }
                }
            }
            return 0;
        }
        o[oStart] = sp[0] == 1 ? -112 : -110;
        o[oStart + 1] = (byte)(s0 | 0x80);
        o[oStart + 2] = (byte)(s1 | 0x80);
        return 3;
    }

    public static int funSoStatelessIso2022jpToEucjp(byte[] statep, byte[] s, int sStart, int l, byte[] o, int oStart, int oSize) {
        o[oStart] = s[sStart + 1];
        o[oStart + 1] = s[sStart + 2];
        return 2;
    }

    public static int funSoEucjpToStatelessIso2022jp(byte[] statep, byte[] s, int sStart, int l, byte[] o, int oStart, int oSize) {
        o[oStart] = -110;
        o[oStart + 1] = s[sStart];
        o[oStart + 2] = s[sStart + 1];
        return 3;
    }

    public static int funSoIso2022jpEncoder(byte[] statep, byte[] s, int sStart, int l, byte[] o, int oStart, int oSize) {
        byte[] sp = statep;
        int output0 = oStart;
        int newstate = l == 1 ? 0 : ((s[sStart] & 0xFF) == 144 ? 1 : 2);
        if (sp[0] != newstate) {
            if (newstate == 0) {
                o[oStart++] = 27;
                o[oStart++] = 40;
                o[oStart++] = 66;
            } else if (newstate == 1) {
                o[oStart++] = 27;
                o[oStart++] = 36;
                o[oStart++] = 64;
            } else {
                o[oStart++] = 27;
                o[oStart++] = 36;
                o[oStart++] = 66;
            }
            sp[0] = (byte)newstate;
        }
        if (l == 1) {
            o[oStart++] = (byte)(s[sStart] & 0x7F);
        } else {
            o[oStart++] = (byte)(s[sStart + 1] & 0x7F);
            o[oStart++] = (byte)(s[sStart + 2] & 0x7F);
        }
        return oStart - output0;
    }

    public static int finishIso2022jpEncoder(byte[] statep, byte[] o, int oStart, int oSize) {
        byte[] sp = statep;
        int output0 = oStart;
        if (sp[0] == 0) {
            return 0;
        }
        o[oStart++] = 27;
        o[oStart++] = 40;
        o[oStart++] = 66;
        sp[0] = 0;
        return oStart - output0;
    }

    public static int funSiCp50221Decoder(byte[] statep, byte[] s, int sStart, int l) {
        byte[] sp = statep;
        int s0 = s[sStart] & 0xFF;
        switch (sp[0]) {
            case 0: {
                if (161 <= s0 && s0 <= 223) {
                    return 15;
                }
                return 1;
            }
            case 3: {
                int c = s0 & 0x7F;
                if (33 > c || c > 95) break;
                return 15;
            }
            case 1: {
                if ((33 > s0 || s0 > 40) && (48 > s0 || s0 > 116)) break;
                return iso2022jp_decoder_jisx0208_rest;
            }
            case 2: {
                if (!(33 <= s0 && s0 <= 40 || s0 == 45 || 48 <= s0 && s0 <= 116) && (121 > s0 || s0 > 124)) break;
                return iso2022jp_decoder_jisx0208_rest;
            }
        }
        return 7;
    }

    public static int funSoCp50221Decoder(byte[] statep, byte[] s, int sStart, int l, byte[] o, int oStart, int oSize) {
        int s0 = s[sStart] & 0xFF;
        byte[] sp = statep;
        switch (s0) {
            case 27: {
                int s1 = s[sStart + 1] & 0xFF;
                if (s1 == 40) {
                    switch (s[sStart + l - 1] & 0xFF) {
                        case 66: 
                        case 74: {
                            sp[0] = 0;
                            break;
                        }
                        case 73: {
                            sp[0] = 3;
                        }
                    }
                } else {
                    switch (s[sStart + l - 1] & 0xFF) {
                        case 64: {
                            sp[0] = 1;
                            break;
                        }
                        case 66: {
                            sp[0] = 2;
                        }
                    }
                }
                return 0;
            }
            case 14: {
                sp[0] = 3;
                return 0;
            }
            case 15: {
                sp[0] = 0;
                return 0;
            }
        }
        if (sp[0] == 3 || 161 <= s0 && s0 <= 223 && sp[0] == 0) {
            o[oStart] = -114;
            o[oStart + 1] = (byte)(s0 | 0x80);
        } else {
            int s1 = s[sStart + 1] & 0xFF;
            o[oStart] = (byte)(s0 | 0x80);
            o[oStart + 1] = (byte)(s1 | 0x80);
        }
        return 2;
    }

    public static int iso2022jpKddiInit(byte[] statep) {
        statep[0] = 0;
        return 0;
    }

    public static int funSiIso2022jpKddiDecoder(byte[] statep, byte[] s, int sStart, int l) {
        int s0 = s[sStart] & 0xFF;
        byte[] sp = statep;
        if (sp[0] == 0) {
            return 1;
        }
        if (33 <= s0 && s0 <= 126) {
            return iso2022jp_kddi_decoder_jisx0208_rest;
        }
        return 7;
    }

    public static int funSoIso2022jpKddiDecoder(byte[] statep, byte[] s, int sStart, int l, byte[] o, int oStart, int oSize) {
        int s0 = s[sStart] & 0xFF;
        int s1 = s[sStart + 1] & 0xFF;
        byte[] sp = statep;
        if (s0 == 27) {
            if (s1 == 40) {
                switch (s[sStart + l - 1] & 0xFF) {
                    case 66: 
                    case 74: {
                        sp[0] = 0;
                    }
                }
            } else {
                switch (s[sStart + l - 1] & 0xFF) {
                    case 64: {
                        sp[0] = 1;
                        break;
                    }
                    case 66: {
                        sp[0] = 2;
                    }
                }
            }
            return 0;
        }
        o[oStart] = sp[0] == 1 ? -112 : -110;
        o[oStart + 1] = (byte)(s0 | 0x80);
        o[oStart + 2] = (byte)(s1 | 0x80);
        return 3;
    }

    public static int funSoIso2022jpKddiEncoder(byte[] statep, byte[] s, int sStart, int l, byte[] o, int oStart, int oSize) {
        int s0 = s[sStart] & 0xFF;
        byte[] sp = statep;
        int output0 = oStart;
        byte newstate = l == 1 ? (byte)0 : (s0 == 144 ? (byte)1 : 2);
        if (sp[0] != newstate) {
            o[oStart++] = 27;
            switch (newstate) {
                case 0: {
                    o[oStart++] = 40;
                    o[oStart++] = 66;
                    break;
                }
                case 1: {
                    o[oStart++] = 36;
                    o[oStart++] = 64;
                    break;
                }
                default: {
                    o[oStart++] = 36;
                    o[oStart++] = 66;
                }
            }
            sp[0] = newstate;
        }
        if (l == 1) {
            o[oStart++] = (byte)(s0 & 0x7F);
        } else {
            int s1 = s[sStart + 1] & 0xFF;
            int s2 = s[sStart + 2] & 0xFF;
            o[oStart++] = (byte)(s1 & 0x7F);
            o[oStart++] = (byte)(s2 & 0x7F);
        }
        return oStart - output0;
    }

    public static int finishIso2022jpKddiEncoder(byte[] statep, byte[] o, int oStart, int oSize) {
        byte[] sp = statep;
        int output0 = oStart;
        if (sp[0] == 0) {
            return 0;
        }
        o[oStart++] = 27;
        o[oStart++] = 40;
        o[oStart++] = 66;
        sp[0] = 0;
        return oStart - output0;
    }

    public static int iso2022jpKddiEncoderResetSequence_size(byte[] statep) {
        byte[] sp = statep;
        if (sp[0] != 0) {
            return 3;
        }
        return 0;
    }

    public static int fromUtf8MacInit(byte[] state) {
        TranscodeFunctions.bufClear(state);
        return 0;
    }

    private static final int bufBytesize(byte[] p) {
        return (TranscodeFunctions.bufEnd(p) - TranscodeFunctions.bufBeg(p) + 16) % 16;
    }

    private static final byte bufAt(byte[] sp, int pos) {
        pos += TranscodeFunctions.bufBeg(sp);
        return sp[pos %= 16];
    }

    private static void bufClear(byte[] state) {
        assert (state.length >= 24) : "UTF8-MAC state not large enough";
        Arrays.fill(state, (byte)0);
    }

    public static int funSoFromUtf8Mac(byte[] statep, byte[] s, int sStart, int l, byte[] o, int oStart, int oSize) {
        byte[] sp = statep;
        int n = 0;
        switch (l) {
            case 1: {
                n = TranscodeFunctions.fromUtf8MacFinish(sp, o, oStart, oSize);
                break;
            }
            case 4: {
                n = TranscodeFunctions.fromUtf8MacFinish(sp, o, oStart, oSize);
                o[oStart + n++] = s[sStart++];
                o[oStart + n++] = s[sStart++];
                o[oStart + n++] = s[sStart++];
                o[oStart + n++] = s[sStart++];
                return n;
            }
        }
        TranscodeFunctions.bufPush(sp, s, sStart, l);
        return n += TranscodeFunctions.bufApply(sp, o, oStart);
    }

    private static void bufPush(byte[] sp, byte[] p, int pStart, int l) {
        int pend = pStart + l;
        while (pStart < pend) {
            sp[TranscodeFunctions.bufEndPostInc((byte[])sp)] = p[pStart++];
            TranscodeFunctions.bufEnd(sp, TranscodeFunctions.bufEnd(sp) % 16);
        }
    }

    private static int bufApply(byte[] sp, byte[] o, int oStart) {
        int n = 0;
        byte[] buf = new byte[]{0, 0, 0};
        if (TranscodeFunctions.bufBytesize(sp) < 3 || TranscodeFunctions.bufBytesize(sp) == 3 && TranscodeFunctions.bufAt(sp, 0) >= 224) {
            return 0;
        }
        int next_info = TranscodeFunctions.getInfo(from_utf8_mac_nfc2, sp);
        switch (next_info & 0x1F) {
            case 3: 
            case 5: {
                buf[n++] = Transcoding.getBT1(next_info);
                buf[n++] = Transcoding.getBT2(next_info);
                if (5 == (next_info & 0x1F)) {
                    buf[n++] = Transcoding.getBT3(next_info);
                }
                TranscodeFunctions.bufClear(sp);
                TranscodeFunctions.bufPush(sp, buf, 0, n);
                return 0;
            }
        }
        return TranscodeFunctions.bufOutputChar(sp, o, oStart);
    }

    private static boolean bufEmpty(byte[] sp) {
        return TranscodeFunctions.bufBeg(sp) == TranscodeFunctions.bufEnd(sp);
    }

    private static byte bufShift(byte[] sp) {
        byte c = sp[TranscodeFunctions.bufBegPostInc(sp)];
        TranscodeFunctions.bufBeg(sp, TranscodeFunctions.bufBeg(sp) % 16);
        return c;
    }

    private static boolean utf8Trailbyte(byte c) {
        return (c & 0xC0) == 128;
    }

    private static int bufOutputChar(byte[] sp, byte[] o, int oStart) {
        int n = 0;
        while (!TranscodeFunctions.bufEmpty(sp)) {
            o[oStart + n++] = TranscodeFunctions.bufShift(sp);
            if (TranscodeFunctions.utf8Trailbyte(sp[TranscodeFunctions.bufBeg(sp)])) continue;
            break;
        }
        return n;
    }

    private static int getInfo(int nextInfo, byte[] sp) {
        int next_byte;
        int pos = 0;
        while (pos < TranscodeFunctions.bufBytesize(sp) && ((nextInfo = (next_byte = TranscodeFunctions.bufAt(sp, pos++) & 0xFF) < TranscodeFunctions.UTF8MAC_BL_MIN_BYTE(nextInfo) || TranscodeFunctions.UTF8MAC_BL_MAX_BYTE(nextInfo) < next_byte ? 7 : TranscodeFunctions.UTF8MAC_BL_ACTION(nextInfo, (byte)next_byte)) & 3) == 0) {
        }
        return nextInfo;
    }

    public static int UTF8MAC_BL_MIN_BYTE(int nextInfo) {
        return From_UTF8_MAC_Transcoder.INSTANCE.byteArray[TranscodeFunctions.BL_BASE(nextInfo)] & 0xFF;
    }

    public static int UTF8MAC_BL_MAX_BYTE(int nextInfo) {
        return From_UTF8_MAC_Transcoder.INSTANCE.byteArray[TranscodeFunctions.BL_BASE(nextInfo) + 1] & 0xFF;
    }

    public static int UTF8MAC_BL_OFFSET(int nextInfo, int b) {
        return From_UTF8_MAC_Transcoder.INSTANCE.byteArray[TranscodeFunctions.BL_BASE(nextInfo) + 2 + b - TranscodeFunctions.UTF8MAC_BL_MIN_BYTE(nextInfo)] & 0xFF;
    }

    public static int UTF8MAC_BL_ACTION(int nextInfo, byte b) {
        return From_UTF8_MAC_Transcoder.INSTANCE.intArray[TranscodeFunctions.BL_INFO(nextInfo) + TranscodeFunctions.UTF8MAC_BL_OFFSET(nextInfo, b & 0xFF)];
    }

    private static int BL_BASE(int nextInfo) {
        return TranscodeFunctions.BYTE_ADDR(TranscodeFunctions.BYTE_LOOKUP_BASE(TranscodeFunctions.WORD_ADDR(nextInfo)));
    }

    private static int BL_INFO(int nextInfo) {
        return TranscodeFunctions.WORD_ADDR(TranscodeFunctions.BYTE_LOOKUP_INFO(TranscodeFunctions.WORD_ADDR(nextInfo)));
    }

    private static int BYTE_ADDR(int index) {
        return index;
    }

    private static int WORD_ADDR(int index) {
        return TranscodeTableSupport.INFO2WORDINDEX(index);
    }

    private static int BYTE_LOOKUP_BASE(int bl) {
        return From_UTF8_MAC_Transcoder.INSTANCE.intArray[bl];
    }

    private static int BYTE_LOOKUP_INFO(int bl) {
        return From_UTF8_MAC_Transcoder.INSTANCE.intArray[bl + 1];
    }

    private static int bufInt(byte[] statep, int base) {
        return statep[base] << 24 | statep[base + 1] << 16 | statep[base + 2] << 8 | statep[base + 3];
    }

    private static void bufInt(byte[] statep, int base, int val) {
        statep[base] = (byte)(val >>> 24 & 0xFF);
        statep[base + 1] = (byte)(val >>> 16 & 0xFF);
        statep[base + 2] = (byte)(val >>> 8 & 0xFF);
        statep[base + 3] = (byte)(val & 0xFF);
    }

    private static int bufBeg(byte[] statep) {
        return TranscodeFunctions.bufInt(statep, 16);
    }

    private static int bufEnd(byte[] statep) {
        return TranscodeFunctions.bufInt(statep, 20);
    }

    private static void bufBeg(byte[] statep, int end2) {
        TranscodeFunctions.bufInt(statep, 16, end2);
    }

    private static void bufEnd(byte[] statep, int end2) {
        TranscodeFunctions.bufInt(statep, 20, end2);
    }

    private static int bufEndPostInc(byte[] statep) {
        int end2 = TranscodeFunctions.bufInt(statep, 20);
        TranscodeFunctions.bufInt(statep, 20, end2 + 1);
        return end2;
    }

    private static int bufBegPostInc(byte[] statep) {
        int beg = TranscodeFunctions.bufInt(statep, 16);
        TranscodeFunctions.bufInt(statep, 16, beg + 1);
        return beg;
    }

    public static int fromUtf8MacFinish(byte[] statep, byte[] o, int oStart, int oSize) {
        return TranscodeFunctions.bufOutputAll(statep, o, oStart);
    }

    private static int bufOutputAll(byte[] sp, byte[] o, int oStart) {
        int n = 0;
        while (!TranscodeFunctions.bufEmpty(sp)) {
            o[oStart + n++] = TranscodeFunctions.bufShift(sp);
        }
        return n;
    }

    public static int escapeXmlAttrQuoteInit(byte[] statep) {
        statep[0] = 0;
        return 0;
    }

    public static int funSoEscapeXmlAttrQuote(byte[] statep, byte[] s, int sStart, int l, byte[] o, int oStart, int oSize) {
        byte[] sp = statep;
        int n = 0;
        if (sp[0] == 0) {
            sp[0] = 1;
            o[oStart + n++] = 34;
        }
        o[oStart + n++] = s[sStart];
        return n;
    }

    public static int escapeXmlAttrQuoteFinish(byte[] statep, byte[] o, int oStart, int oSize) {
        byte[] sp = statep;
        int n = 0;
        if (sp[0] == 0) {
            o[oStart + n++] = 34;
        }
        o[oStart + n++] = 34;
        sp[0] = 0;
        return n;
    }

    private static byte NEWLINE_STATE(byte[] sp) {
        return sp[0];
    }

    private static void NEWLINE_STATE(byte[] sp, int b) {
        sp[0] = (byte)b;
    }

    private static void NEWLINE_NEWLINES_MET(byte[] sp, int b) {
        sp[1] = (byte)b;
    }

    private static void NEWLINE_NEWLINES_MET_or_mask(byte[] sp, int b) {
        sp[1] = (byte)(sp[1] | (byte)b);
    }

    public static int universalNewlineInit(byte[] statep) {
        byte[] sp = statep;
        TranscodeFunctions.NEWLINE_STATE(sp, 0);
        TranscodeFunctions.NEWLINE_NEWLINES_MET(sp, 0);
        return 0;
    }

    public static int funSoUniversalNewline(byte[] statep, byte[] s, int sStart, int l, byte[] o, int oStart, int oSize) {
        int len;
        int s0 = s[sStart] & 0xFF;
        byte[] sp = statep;
        if (s0 == 10) {
            if (TranscodeFunctions.NEWLINE_STATE(sp) == 0) {
                TranscodeFunctions.NEWLINE_NEWLINES_MET_or_mask(sp, 1);
            } else {
                TranscodeFunctions.NEWLINE_NEWLINES_MET_or_mask(sp, 2);
            }
            o[oStart] = 10;
            len = 1;
            TranscodeFunctions.NEWLINE_STATE(sp, 0);
        } else {
            len = 0;
            if (TranscodeFunctions.NEWLINE_STATE(sp) == 1) {
                o[oStart] = 10;
                len = 1;
                TranscodeFunctions.NEWLINE_NEWLINES_MET_or_mask(sp, 4);
            }
            if (s0 == 13) {
                TranscodeFunctions.NEWLINE_STATE(sp, 1);
            } else {
                o[oStart + len++] = (byte)s0;
                TranscodeFunctions.NEWLINE_STATE(sp, 0);
            }
        }
        return len;
    }

    public static int universalNewlineFinish(byte[] statep, byte[] o, int oStart, int oSize) {
        byte[] sp = statep;
        int len = 0;
        if (TranscodeFunctions.NEWLINE_STATE(sp) == 1) {
            o[oStart] = 10;
            len = 1;
            TranscodeFunctions.NEWLINE_NEWLINES_MET_or_mask(sp, 4);
        }
        sp[0] = 0;
        return len;
    }
}

