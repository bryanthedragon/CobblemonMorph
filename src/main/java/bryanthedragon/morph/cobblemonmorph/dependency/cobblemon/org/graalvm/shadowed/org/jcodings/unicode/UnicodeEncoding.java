
package org.graalvm.shadowed.org.jcodings.unicode;

import java.io.DataInputStream;
import java.io.IOException;
import org.graalvm.shadowed.org.jcodings.ApplyAllCaseFoldFunction;
import org.graalvm.shadowed.org.jcodings.CaseFoldCodeItem;
import org.graalvm.shadowed.org.jcodings.CodeRange;
import org.graalvm.shadowed.org.jcodings.IntHolder;
import org.graalvm.shadowed.org.jcodings.MultiByteEncoding;
import org.graalvm.shadowed.org.jcodings.exception.CharacterPropertyException;
import org.graalvm.shadowed.org.jcodings.exception.EncodingError;
import org.graalvm.shadowed.org.jcodings.unicode.UnicodeCodeRange;
import org.graalvm.shadowed.org.jcodings.util.ArrayReader;
import org.graalvm.shadowed.org.jcodings.util.CaseInsensitiveBytesHash;
import org.graalvm.shadowed.org.jcodings.util.IntArrayHash;
import org.graalvm.shadowed.org.jcodings.util.IntHash;

public abstract class UnicodeEncoding
extends MultiByteEncoding {
    private static final int PROPERTY_NAME_MAX_SIZE = 45;
    static final int I_WITH_DOT_ABOVE = 304;
    static final int DOTLESS_i = 305;
    static final int DOT_ABOVE = 775;
    static final int CASE_MAPPING_SLACK = 12;
    static final short[] UNICODE_ISO_8859_1_CTypeTable = new short[]{16392, 16392, 16392, 16392, 16392, 16392, 16392, 16392, 16392, 16908, 16905, 16904, 16904, 16904, 16392, 16392, 16392, 16392, 16392, 16392, 16392, 16392, 16392, 16392, 16392, 16392, 16392, 16392, 16392, 16392, 16392, 16392, 17028, 16800, 16800, 16800, 16800, 16800, 16800, 16800, 16800, 16800, 16800, 16800, 16800, 16800, 16800, 16800, 30896, 30896, 30896, 30896, 30896, 30896, 30896, 30896, 30896, 30896, 16800, 16800, 16800, 16800, 16800, 16800, 16800, 31906, 31906, 31906, 31906, 31906, 31906, 29858, 29858, 29858, 29858, 29858, 29858, 29858, 29858, 29858, 29858, 29858, 29858, 29858, 29858, 29858, 29858, 29858, 29858, 29858, 29858, 16800, 16800, 16800, 16800, 20896, 16800, 30946, 30946, 30946, 30946, 30946, 30946, 28898, 28898, 28898, 28898, 28898, 28898, 28898, 28898, 28898, 28898, 28898, 28898, 28898, 28898, 28898, 28898, 28898, 28898, 28898, 28898, 16800, 16800, 16800, 16800, 16392, 8, 8, 8, 8, 8, 648, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 644, 416, 160, 160, 160, 160, 160, 160, 160, 160, 12514, 416, 160, 168, 160, 160, 160, 160, 4256, 4256, 160, 12514, 160, 416, 160, 4256, 12514, 416, 4256, 4256, 4256, 416, 13474, 13474, 13474, 13474, 13474, 13474, 13474, 13474, 13474, 13474, 13474, 13474, 13474, 13474, 13474, 13474, 13474, 13474, 13474, 13474, 13474, 13474, 13474, 160, 13474, 13474, 13474, 13474, 13474, 13474, 13474, 12514, 12514, 12514, 12514, 12514, 12514, 12514, 12514, 12514, 12514, 12514, 12514, 12514, 12514, 12514, 12514, 12514, 12514, 12514, 12514, 12514, 12514, 12514, 12514, 160, 12514, 12514, 12514, 12514, 12514, 12514, 12514, 12514};

    protected UnicodeEncoding(String name, int minLength, int maxLength, int[] EncLen, int[][] Trans) {
        super(name, minLength, maxLength, EncLen, Trans, UNICODE_ISO_8859_1_CTypeTable);
        this.isUnicode = true;
    }

    protected UnicodeEncoding(String name, int minLength, int maxLength, int[] EncLen) {
        this(name, minLength, maxLength, EncLen, null);
    }

    @Override
    public String getCharsetName() {
        return new String(this.getName());
    }

    @Override
    public boolean isCodeCType(int code, int ctype) {
        if (ctype <= 14 && code < 256) {
            return this.isCodeCTypeInternal(code, ctype);
        }
        if (ctype > UnicodeCodeRange.CodeRangeTable.length) {
            throw new InternalError("undefined type (bug)");
        }
        return CodeRange.isInCodeRange(UnicodeCodeRange.CodeRangeTable[ctype].getRange(), code);
    }

    public static boolean isInCodeRange(UnicodeCodeRange range, int code) {
        return CodeRange.isInCodeRange(range.getRange(), code);
    }

    protected final int[] ctypeCodeRange(int ctype) {
        if (ctype >= UnicodeCodeRange.CodeRangeTable.length) {
            throw new InternalError("undefined type (bug)");
        }
        return UnicodeCodeRange.CodeRangeTable[ctype].getRange();
    }

    @Override
    public int propertyNameToCType(byte[] name, int p, int end2) {
        byte[] buf = new byte[45];
        int len = 0;
        for (int p_ = p; p_ < end2; p_ += this.length(name, p_, end2)) {
            int code = this.mbcToCode(name, p_, end2);
            if (code == 32 || code == 45 || code == 95) continue;
            if (code >= 128) {
                throw new CharacterPropertyException(EncodingError.ERR_INVALID_CHAR_PROPERTY_NAME, name, p, end2);
            }
            buf[len++] = (byte)code;
            if (len < 45) continue;
            throw new CharacterPropertyException(EncodingError.ERR_INVALID_CHAR_PROPERTY_NAME, name, p, end2);
        }
        Integer ctype = (Integer)CTypeName.Values.get(buf, 0, len);
        if (ctype == null) {
            throw new CharacterPropertyException(EncodingError.ERR_INVALID_CHAR_PROPERTY_NAME, name, p, end2);
        }
        return ctype;
    }

    @Override
    public int mbcCaseFold(int flag, byte[] bytes, IntHolder pp, int end2, byte[] fold) {
        int p = pp.value;
        int foldP = 0;
        int code = this.mbcToCode(bytes, p, end2);
        int len = this.length(bytes, p, end2);
        pp.value += len;
        CodeList to = CaseFold.Values.get(code);
        if (to != null) {
            if (to.codes.length == 1) {
                return this.codeToMbc(to.codes[0], fold, foldP);
            }
            int rlen = 0;
            for (int i = 0; i < to.codes.length; ++i) {
                len = this.codeToMbc(to.codes[i], fold, foldP);
                foldP += len;
                rlen += len;
            }
            return rlen;
        }
        for (int i = 0; i < len; ++i) {
            fold[foldP++] = bytes[p++];
        }
        return len;
    }

    @Override
    public void applyAllCaseFold(int flag, ApplyAllCaseFoldFunction fun, Object arg) {
        int k;
        int j;
        CodeList to;
        int from;
        int i;
        int[] code = new int[]{0};
        for (i = 0; i < CaseUnfold11.From.length; ++i) {
            from = CaseUnfold11.From[i];
            to = CaseUnfold11.To[i];
            for (j = 0; j < to.codes.length; ++j) {
                code[0] = from;
                fun.apply(to.codes[j], code, 1, arg);
                code[0] = to.codes[j];
                fun.apply(from, code, 1, arg);
                for (k = 0; k < j; ++k) {
                    code[0] = to.codes[k];
                    fun.apply(to.codes[j], code, 1, arg);
                    code[0] = to.codes[j];
                    fun.apply(to.codes[k], code, 1, arg);
                }
            }
        }
        for (i = 0; i < CaseUnfold11.Locale_From.length; ++i) {
            from = CaseUnfold11.Locale_From[i];
            to = CaseUnfold11.Locale_To[i];
            for (j = 0; j < to.codes.length; ++j) {
                code[0] = from;
                fun.apply(to.codes[j], code, 1, arg);
                code[0] = to.codes[j];
                fun.apply(from, code, 1, arg);
                for (k = 0; k < j; ++k) {
                    code[0] = to.codes[k];
                    fun.apply(to.codes[j], code, 1, arg);
                    code[0] = to.codes[j];
                    fun.apply(to.codes[k], code, 1, arg);
                }
            }
        }
        if ((flag & 0x40000000) != 0) {
            for (i = 0; i < CaseUnfold12.From.length; ++i) {
                int[] from2 = CaseUnfold12.From[i];
                to = CaseUnfold12.To[i];
                for (j = 0; j < to.codes.length; ++j) {
                    fun.apply(to.codes[j], from2, 2, arg);
                    for (k = 0; k < to.codes.length; ++k) {
                        if (k == j) continue;
                        code[0] = to.codes[k];
                        fun.apply(to.codes[j], code, 1, arg);
                    }
                }
            }
            for (i = 0; i < CaseUnfold12.Locale_From.length; ++i) {
                int[] from3 = CaseUnfold12.Locale_From[i];
                to = CaseUnfold12.Locale_To[i];
                for (j = 0; j < to.codes.length; ++j) {
                    fun.apply(to.codes[j], from3, 2, arg);
                    for (k = 0; k < to.codes.length; ++k) {
                        if (k == j) continue;
                        code[0] = to.codes[k];
                        fun.apply(to.codes[j], code, 1, arg);
                    }
                }
            }
            for (i = 0; i < CaseUnfold13.From.length; ++i) {
                int[] from4 = CaseUnfold13.From[i];
                to = CaseUnfold13.To[i];
                for (j = 0; j < to.codes.length; ++j) {
                    fun.apply(to.codes[j], from4, 3, arg);
                    for (k = 0; k < to.codes.length; ++k) {
                        if (k == j) continue;
                        code[0] = to.codes[k];
                        fun.apply(to.codes[j], code, 1, arg);
                    }
                }
            }
        }
    }

    @Override
    public CaseFoldCodeItem[] caseFoldCodesByString(int flag, byte[] bytes, int p, int end2) {
        int code = this.mbcToCode(bytes, p, end2);
        int len = this.length(bytes, p, end2);
        int n = 0;
        int fn = 0;
        CodeList to = CaseFold.Values.get(code);
        CaseFoldCodeItem[] items = null;
        if (to != null) {
            items = new CaseFoldCodeItem[13];
            if (to.codes.length == 1) {
                int origCode = code;
                items[0] = CaseFoldCodeItem.create(len, to.codes[0]);
                ++n;
                code = to.codes[0];
                to = CaseUnfold11.Values.get(code);
                if (to != null) {
                    for (int i = 0; i < to.codes.length; ++i) {
                        if (to.codes[i] == origCode) continue;
                        items[n] = CaseFoldCodeItem.create(len, to.codes[i]);
                        ++n;
                    }
                }
            } else if ((flag & 0x40000000) != 0) {
                CodeList z2;
                int j;
                int i;
                int[][] cs = new int[3][4];
                int[] ncs = new int[3];
                for (fn = 0; fn < to.codes.length; ++fn) {
                    cs[fn][0] = to.codes[fn];
                    CodeList z3 = CaseUnfold11.Values.get(cs[fn][0]);
                    if (z3 != null) {
                        for (i = 0; i < z3.codes.length; ++i) {
                            cs[fn][i + 1] = z3.codes[i];
                        }
                        ncs[fn] = z3.codes.length + 1;
                        continue;
                    }
                    ncs[fn] = 1;
                }
                if (fn == 2) {
                    for (int i2 = 0; i2 < ncs[0]; ++i2) {
                        for (j = 0; j < ncs[1]; ++j) {
                            items[n] = CaseFoldCodeItem.create(len, cs[0][i2], cs[1][j]);
                            ++n;
                        }
                    }
                    z2 = CaseUnfold12.Values.get(to.codes);
                    if (z2 != null) {
                        for (i = 0; i < z2.codes.length; ++i) {
                            if (z2.codes[i] == code) continue;
                            items[n] = CaseFoldCodeItem.create(len, z2.codes[i]);
                            ++n;
                        }
                    }
                } else {
                    for (int i3 = 0; i3 < ncs[0]; ++i3) {
                        for (j = 0; j < ncs[1]; ++j) {
                            for (int k = 0; k < ncs[2]; ++k) {
                                items[n] = CaseFoldCodeItem.create(len, cs[0][i3], cs[1][j], cs[2][k]);
                                ++n;
                            }
                        }
                    }
                    z2 = CaseUnfold13.Values.get(to.codes);
                    if (z2 != null) {
                        for (i = 0; i < z2.codes.length; ++i) {
                            if (z2.codes[i] == code) continue;
                            items[n] = CaseFoldCodeItem.create(len, z2.codes[i]);
                            ++n;
                        }
                    }
                }
                flag = 0;
            }
        } else {
            to = CaseUnfold11.Values.get(code);
            if (to != null) {
                items = new CaseFoldCodeItem[13];
                for (int i = 0; i < to.codes.length; ++i) {
                    items[n] = CaseFoldCodeItem.create(len, to.codes[i]);
                    ++n;
                }
            }
        }
        if ((flag & 0x40000000) != 0) {
            if (items == null) {
                items = new CaseFoldCodeItem[13];
            }
            if ((p += len) < end2) {
                int codes0 = code;
                code = this.mbcToCode(bytes, p, end2);
                to = CaseFold.Values.get(code);
                int codes1 = to != null && to.codes.length == 1 ? to.codes[0] : code;
                int clen = this.length(bytes, p, end2);
                len += clen;
                CodeList z2 = CaseUnfold12.Values.get(codes0, codes1);
                if (z2 != null) {
                    for (int i = 0; i < z2.codes.length; ++i) {
                        items[n] = CaseFoldCodeItem.create(len, z2.codes[i]);
                        ++n;
                    }
                }
                if ((p += clen) < end2) {
                    code = this.mbcToCode(bytes, p, end2);
                    to = CaseFold.Values.get(code);
                    int codes2 = to != null && to.codes.length == 1 ? to.codes[0] : code;
                    clen = this.length(bytes, p, end2);
                    len += clen;
                    z2 = CaseUnfold13.Values.get(codes0, codes1, codes2);
                    if (z2 != null) {
                        for (int i = 0; i < z2.codes.length; ++i) {
                            items[n] = CaseFoldCodeItem.create(len, z2.codes[i]);
                            ++n;
                        }
                    }
                }
            }
        }
        if (items == null || n == 0) {
            return CaseFoldCodeItem.EMPTY_FOLD_CODES;
        }
        if (n < items.length) {
            CaseFoldCodeItem[] tmp = new CaseFoldCodeItem[n];
            System.arraycopy(items, 0, tmp, 0, n);
            return tmp;
        }
        return items;
    }

    @Override
    public final int caseMap(IntHolder flagP, byte[] bytes, IntHolder pp, int end2, byte[] to, int toP, int toEnd) {
        int flags = flagP.value;
        int toStart = toP;
        toEnd -= 12;
        flags |= (flags & 0x6000) << 3;
        while (pp.value < end2 && toP <= toEnd) {
            int length = this.length(bytes, pp.value, end2);
            if (length < 0) {
                return length;
            }
            int code = this.mbcToCode(bytes, pp.value, end2);
            pp.value += length;
            if (code <= 122) {
                if (code >= 97 && code <= 122) {
                    if ((flags & 0x2000) != 0) {
                        code = ((flags |= 0x40000) & 0x100000) != 0 && code == 105 ? 304 : (code -= 32);
                    }
                } else if (code >= 65 && code <= 90 && (flags & 0x84000) != 0) {
                    code = ((flags |= 0x40000) & 0x100000) != 0 && code == 73 ? 305 : (code += 32);
                }
            } else if ((flags & 0x400000) == 0 && code >= 181) {
                if (code == 304) {
                    if ((flags & 0x84000) != 0) {
                        code = 105;
                        if (((flags |= 0x40000) & 0x100000) == 0) {
                            toP += this.codeToMbc(code, to, toP);
                            code = 775;
                        }
                    }
                } else if (code == 305) {
                    if ((flags & 0x2000) != 0) {
                        flags |= 0x40000;
                        code = 73;
                    }
                } else {
                    CodeList folded = CaseFold.Values.get(code);
                    if (folded != null) {
                        if ((flags & 0x8000) != 0 && code >= 7312 && code <= 7359) {
                            flags |= 0x40000;
                            code -= 3008;
                        } else if (((flags & 0x8000) == 0 || (folded.flags & 0x800000) == 0) && (flags & folded.flags) != 0) {
                            int finish;
                            int start2;
                            int[] codes;
                            boolean specialCopy = false;
                            if (((flags |= 0x40000) & folded.flags & 0x838000) != 0) {
                                codes = CaseMappingSpecials.Values;
                                int specialStart = (folded.flags & 0x1FF8) >>> 3;
                                if ((folded.flags & 0x800000) != 0) {
                                    if ((flags & 0x6000) == 24576) {
                                        specialCopy = true;
                                    } else {
                                        specialStart += UnicodeEncoding.extractLength(codes[specialStart]);
                                    }
                                }
                                if (!specialCopy && (folded.flags & 0x8000) != 0) {
                                    if ((flags & 0x8000) != 0) {
                                        specialCopy = true;
                                    } else {
                                        specialStart += UnicodeEncoding.extractLength(codes[specialStart]);
                                    }
                                }
                                if (!specialCopy && (folded.flags & 0x20000) != 0 && (flags & 0x20000) == 0) {
                                    specialStart += UnicodeEncoding.extractLength(codes[specialStart]);
                                }
                                start2 = specialStart;
                                finish = start2 + UnicodeEncoding.extractLength(codes[specialStart]);
                                code = UnicodeEncoding.extractCode(codes[specialStart]);
                            } else {
                                codes = folded.codes;
                                start2 = 0;
                                finish = folded.codes.length;
                                code = codes[0];
                            }
                            for (int i = start2 + 1; i < finish; ++i) {
                                toP += this.codeToMbc(code, to, toP);
                                code = codes[i];
                            }
                        }
                    } else {
                        folded = CaseUnfold11.Values.get(code);
                        if (folded != null && ((flags & 0x8000) == 0 || (folded.flags & 0x800000) == 0) && (flags & folded.flags) != 0) {
                            code = folded.codes[((flags |= 0x40000) & folded.flags & 0x8000) != 0 ? 1 : 0];
                        }
                    }
                }
            }
            toP += this.codeToMbc(code, to, toP);
            if ((flags & 0x8000) == 0) continue;
            flags ^= 0x3E000;
        }
        flagP.value = flags;
        return toP - toStart;
    }

    private static Object[] readFoldN(int fromSize, String table) {
        try {
            DataInputStream dis = ArrayReader.openStream(table);
            int size = dis.readInt();
            int[][] from = new int[size][];
            CodeList[] to = new CodeList[size];
            for (int i = 0; i < size; ++i) {
                from[i] = new int[fromSize];
                for (int j = 0; j < fromSize; ++j) {
                    from[i][j] = dis.readInt();
                }
                to[i] = new CodeList(dis);
            }
            dis.close();
            return new Object[]{from, to};
        }
        catch (IOException iot) {
            throw new RuntimeException(iot);
        }
    }

    private static int extractLength(int packed) {
        return packed >>> 25;
    }

    private static int extractCode(int packed) {
        return packed & 0x1FFFFFF;
    }

    static class CTypeName {
        private static final CaseInsensitiveBytesHash<Integer> Values = CTypeName.initializeCTypeNameTable();

        CTypeName() {
        }

        private static CaseInsensitiveBytesHash<Integer> initializeCTypeNameTable() {
            CaseInsensitiveBytesHash<Integer> table = new CaseInsensitiveBytesHash<Integer>();
            for (int i = 0; i < UnicodeCodeRange.CodeRangeTable.length; ++i) {
                table.putDirect(UnicodeCodeRange.CodeRangeTable[i].name, i);
            }
            return table;
        }
    }

    private static class CaseFold {
        static final IntHash<CodeList> Values = CaseFold.read("CaseFold");

        private CaseFold() {
        }

        static IntHash<CodeList> read(String table) {
            try {
                DataInputStream dis = ArrayReader.openStream(table);
                int size = dis.readInt();
                IntHash<CodeList> hash = new IntHash<CodeList>(size);
                for (int i = 0; i < size; ++i) {
                    hash.putDirect(dis.readInt(), new CodeList(dis));
                }
                dis.close();
                return hash;
            }
            catch (IOException iot) {
                throw new RuntimeException(iot);
            }
        }
    }

    private static class CodeList {
        final int[] codes;
        final int flags;

        CodeList(DataInputStream dis) throws IOException {
            int packed = dis.readInt();
            this.flags = packed & 0xFFFFFFF8;
            int length = packed & 7;
            this.codes = new int[length];
            for (int j = 0; j < length; ++j) {
                this.codes[j] = dis.readInt();
            }
        }
    }

    private static class CaseUnfold11 {
        private static final int[] From;
        private static final CodeList[] To;
        private static final int[] Locale_From;
        private static final CodeList[] Locale_To;
        static final IntHash<CodeList> Values;

        private CaseUnfold11() {
        }

        static Object[] read(String table) {
            try {
                DataInputStream dis = ArrayReader.openStream(table);
                int size = dis.readInt();
                int[] from = new int[size];
                CodeList[] to = new CodeList[size];
                for (int i = 0; i < size; ++i) {
                    from[i] = dis.readInt();
                    to[i] = new CodeList(dis);
                }
                dis.close();
                return new Object[]{from, to};
            }
            catch (IOException iot) {
                throw new RuntimeException(iot);
            }
        }

        static IntHash<CodeList> initializeUnfold1Hash() {
            int i;
            IntHash<CodeList> hash = new IntHash<CodeList>(From.length + Locale_From.length);
            for (i = 0; i < From.length; ++i) {
                hash.putDirect(From[i], To[i]);
            }
            for (i = 0; i < Locale_From.length; ++i) {
                hash.putDirect(Locale_From[i], Locale_To[i]);
            }
            return hash;
        }

        static {
            Object[] unfold = CaseUnfold11.read("CaseUnfold_11");
            From = (int[])unfold[0];
            To = (CodeList[])unfold[1];
            unfold = CaseUnfold11.read("CaseUnfold_11_Locale");
            Locale_From = (int[])unfold[0];
            Locale_To = (CodeList[])unfold[1];
            Values = CaseUnfold11.initializeUnfold1Hash();
        }
    }

    private static class CaseUnfold12 {
        private static final int[][] From;
        private static final CodeList[] To;
        private static final int[][] Locale_From;
        private static final CodeList[] Locale_To;
        static final IntArrayHash<CodeList> Values;

        private CaseUnfold12() {
        }

        private static IntArrayHash<CodeList> initializeUnfold2Hash() {
            int i;
            IntArrayHash<CodeList> unfold2 = new IntArrayHash<CodeList>(From.length + Locale_From.length);
            for (i = 0; i < From.length; ++i) {
                unfold2.putDirect(From[i], To[i]);
            }
            for (i = 0; i < Locale_From.length; ++i) {
                unfold2.putDirect(Locale_From[i], Locale_To[i]);
            }
            return unfold2;
        }

        static {
            Object[] unfold = UnicodeEncoding.readFoldN(2, "CaseUnfold_12");
            From = (int[][])unfold[0];
            To = (CodeList[])unfold[1];
            unfold = UnicodeEncoding.readFoldN(2, "CaseUnfold_12_Locale");
            Locale_From = (int[][])unfold[0];
            Locale_To = (CodeList[])unfold[1];
            Values = CaseUnfold12.initializeUnfold2Hash();
        }
    }

    private static class CaseUnfold13 {
        private static final int[][] From;
        private static final CodeList[] To;
        static final IntArrayHash<CodeList> Values;

        private CaseUnfold13() {
        }

        private static IntArrayHash<CodeList> initializeUnfold3Hash() {
            IntArrayHash<CodeList> unfold3 = new IntArrayHash<CodeList>(From.length);
            for (int i = 0; i < From.length; ++i) {
                unfold3.putDirect(From[i], To[i]);
            }
            return unfold3;
        }

        static {
            Object[] unfold = UnicodeEncoding.readFoldN(3, "CaseUnfold_13");
            From = (int[][])unfold[0];
            To = (CodeList[])unfold[1];
            Values = CaseUnfold13.initializeUnfold3Hash();
        }
    }

    private static class CaseMappingSpecials {
        static final int[] Values = ArrayReader.readIntArray("CaseMappingSpecials");

        private CaseMappingSpecials() {
        }
    }
}

