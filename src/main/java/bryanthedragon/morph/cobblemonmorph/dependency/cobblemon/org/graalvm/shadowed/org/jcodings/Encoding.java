
package org.graalvm.shadowed.org.jcodings;

import java.nio.charset.Charset;
import org.graalvm.shadowed.org.jcodings.ApplyAllCaseFoldFunction;
import org.graalvm.shadowed.org.jcodings.CaseFoldCodeItem;
import org.graalvm.shadowed.org.jcodings.IntHolder;
import org.graalvm.shadowed.org.jcodings.ascii.AsciiTables;
import org.graalvm.shadowed.org.jcodings.exception.EncodingException;
import org.graalvm.shadowed.org.jcodings.exception.InternalException;
import org.graalvm.shadowed.org.jcodings.util.BytesHash;

public abstract class Encoding
implements Cloneable {
    public static final int CHAR_INVALID = -1;
    private static int count;
    protected final int minLength;
    protected final int maxLength;
    private final boolean isFixedWidth;
    private final boolean isSingleByte;
    private boolean isAsciiCompatible;
    protected boolean isUnicode = false;
    protected boolean isUTF8 = false;
    private byte[] name;
    private int hashCode;
    private int index;
    private Charset charset = null;
    private boolean isDummy = false;
    private String stringName;
    public static final byte NEW_LINE = 10;

    protected Encoding(String name, int minLength, int maxLength) {
        this.setName(name);
        this.minLength = minLength;
        this.maxLength = maxLength;
        this.isFixedWidth = minLength == maxLength;
        this.isSingleByte = this.isFixedWidth && minLength == 1;
        this.index = count++;
        this.isAsciiCompatible = minLength == 1;
    }

    protected final void setName(String name) {
        this.name = name.getBytes();
        this.hashCode = BytesHash.hashCode(this.name, 0, this.name.length);
        this.stringName = name;
    }

    protected final void setName(byte[] name) {
        this.name = name;
        this.hashCode = BytesHash.hashCode(this.name, 0, this.name.length);
        this.stringName = new String(name);
    }

    protected final void setDummy() {
        this.isDummy = true;
        this.isAsciiCompatible = false;
    }

    public final String toString() {
        return this.stringName;
    }

    public final boolean equals(Object other) {
        return this == other;
    }

    public final int hashCode() {
        return this.hashCode;
    }

    public final int getIndex() {
        return this.index;
    }

    public final byte[] getName() {
        return this.name;
    }

    public final boolean isDummy() {
        return this.isDummy;
    }

    public final boolean isAsciiCompatible() {
        return this.isAsciiCompatible;
    }

    public final boolean isUnicode() {
        return this.isUnicode;
    }

    public final boolean isUTF8() {
        return this.isUTF8;
    }

    public Charset getCharset() {
        if (this.charset == null) {
            this.charset = Charset.forName(this.getCharsetName());
        }
        return this.charset;
    }

    public String getCharsetName() {
        return this.stringName;
    }

    Encoding replicate(byte[] name) {
        try {
            Encoding clone = (Encoding)this.clone();
            clone.setName(name);
            clone.index = count++;
            return clone;
        }
        catch (CloneNotSupportedException cnse) {
            throw new EncodingException("could not replicate <%n> encoding", new String(name));
        }
    }

    public abstract int length(byte var1);

    public abstract int length(byte[] var1, int var2, int var3);

    public final int maxLength() {
        return this.maxLength;
    }

    @Deprecated
    public final int maxLengthDistance() {
        return this.maxLength();
    }

    public final int minLength() {
        return this.minLength;
    }

    public abstract boolean isNewLine(byte[] var1, int var2, int var3);

    public abstract int mbcToCode(byte[] var1, int var2, int var3);

    public abstract int codeToMbcLength(int var1);

    public abstract int codeToMbc(int var1, byte[] var2, int var3);

    public abstract int mbcCaseFold(int var1, byte[] var2, IntHolder var3, int var4, byte[] var5);

    public byte[] toLowerCaseTable() {
        return null;
    }

    public abstract void applyAllCaseFold(int var1, ApplyAllCaseFoldFunction var2, Object var3);

    public abstract CaseFoldCodeItem[] caseFoldCodesByString(int var1, byte[] var2, int var3, int var4);

    public abstract int propertyNameToCType(byte[] var1, int var2, int var3);

    public abstract boolean isCodeCType(int var1, int var2);

    public abstract int[] ctypeCodeRange(int var1, IntHolder var2);

    public abstract int leftAdjustCharHead(byte[] var1, int var2, int var3, int var4);

    public abstract boolean isReverseMatchAllowed(byte[] var1, int var2, int var3);

    public abstract int caseMap(IntHolder var1, byte[] var2, IntHolder var3, int var4, byte[] var5, int var6, int var7);

    public final int rightAdjustCharHead(byte[] bytes, int p, int s, int end2) {
        int p_ = this.leftAdjustCharHead(bytes, p, s, end2);
        if (p_ < s) {
            p_ += this.length(bytes, p_, end2);
        }
        return p_;
    }

    public final int rightAdjustCharHeadWithPrev(byte[] bytes, int p, int s, int end2, IntHolder prev) {
        int p_ = this.leftAdjustCharHead(bytes, p, s, end2);
        if (p_ < s) {
            if (prev != null) {
                prev.value = p_;
            }
            p_ += this.length(bytes, p_, end2);
        } else if (prev != null) {
            prev.value = -1;
        }
        return p_;
    }

    public final int prevCharHead(byte[] bytes, int p, int s, int end2) {
        if (s <= p) {
            return -1;
        }
        return this.leftAdjustCharHead(bytes, p, s - 1, end2);
    }

    public final int stepBack(byte[] bytes, int p, int s, int end2, int n) {
        while (s != -1 && n-- > 0) {
            if (s <= p) {
                return -1;
            }
            s = this.leftAdjustCharHead(bytes, p, s - 1, end2);
        }
        return s;
    }

    public final int step(byte[] bytes, int p, int end2, int n) {
        int q = p;
        while (n-- > 0) {
            q += this.length(bytes, q, end2);
        }
        return q <= end2 ? q : -1;
    }

    public abstract int strLength(byte[] var1, int var2, int var3);

    public abstract int strCodeAt(byte[] var1, int var2, int var3, int var4);

    public final int strLengthNull(byte[] bytes, int p, int end2) {
        int n = 0;
        while (true) {
            if (bytes[p] == 0) {
                int len = this.minLength();
                if (len == 1) {
                    return n;
                }
                int q = p + 1;
                while (len > 1 && bytes[q] == 0) {
                    ++q;
                    --len;
                }
                if (len == 1) {
                    return n;
                }
            }
            p += this.length(bytes, p, end2);
            ++n;
        }
    }

    public final int strByteLengthNull(byte[] bytes, int p, int end2) {
        int start2 = 0;
        int p_ = 0;
        while (true) {
            if (bytes[p_] == 0) {
                int len = this.minLength();
                if (len == 1) {
                    return p_ - start2;
                }
                int q = p_ + 1;
                while (len > 1) {
                    if (q >= bytes.length) {
                        return p_ - start2;
                    }
                    if (bytes[q] != 0) break;
                    ++q;
                    --len;
                }
                if (len == 1) {
                    return p_ - start2;
                }
            }
            p_ += this.length(bytes, p_, end2);
        }
    }

    public final int strNCmp(byte[] bytes, int p, int end2, byte[] ascii, int asciiP, int n) {
        while (n-- > 0) {
            if (p >= end2) {
                return ascii[asciiP];
            }
            int c = this.mbcToCode(bytes, p, end2);
            int x = ascii[asciiP] - c;
            if (x != 0) {
                return x;
            }
            ++asciiP;
            p += this.length(bytes, p, end2);
        }
        return 0;
    }

    public final boolean isNewLine(int code) {
        return this.isCodeCType(code, 0);
    }

    public final boolean isGraph(int code) {
        return this.isCodeCType(code, 5);
    }

    public final boolean isPrint(int code) {
        return this.isCodeCType(code, 7);
    }

    public final boolean isAlnum(int code) {
        return this.isCodeCType(code, 13);
    }

    public final boolean isAlpha(int code) {
        return this.isCodeCType(code, 1);
    }

    public final boolean isLower(int code) {
        return this.isCodeCType(code, 6);
    }

    public final boolean isUpper(int code) {
        return this.isCodeCType(code, 10);
    }

    public final boolean isCntrl(int code) {
        return this.isCodeCType(code, 3);
    }

    public final boolean isPunct(int code) {
        return this.isCodeCType(code, 8);
    }

    public final boolean isSpace(int code) {
        return this.isCodeCType(code, 9);
    }

    public final boolean isBlank(int code) {
        return this.isCodeCType(code, 2);
    }

    public final boolean isDigit(int code) {
        return this.isCodeCType(code, 4);
    }

    public final boolean isXDigit(int code) {
        return this.isCodeCType(code, 11);
    }

    public final boolean isWord(int code) {
        return this.isCodeCType(code, 12);
    }

    public final boolean isMbcWord(byte[] bytes, int p, int end2) {
        return this.isWord(this.mbcToCode(bytes, p, end2));
    }

    public final boolean isSbWord(int code) {
        return Encoding.isAscii(code) && this.isWord(code);
    }

    public final boolean isMbcHead(byte[] bytes, int p, int end2) {
        return this.length(bytes, p, end2) != 1;
    }

    public boolean isMbcCrnl(byte[] bytes, int p, int end2) {
        return this.mbcToCode(bytes, p, end2) == 13 && this.isNewLine(bytes, p + this.length(bytes, p, end2), end2);
    }

    public static int digitVal(int code) {
        return code - 48;
    }

    public static int odigitVal(int code) {
        return Encoding.digitVal(code);
    }

    public final int xdigitVal(int code) {
        if (this.isDigit(code)) {
            return Encoding.digitVal(code);
        }
        return this.isUpper(code) ? code - 65 + 10 : code - 97 + 10;
    }

    public static boolean isMbcAscii(byte b) {
        return (b & 0xFF) < 128;
    }

    public static boolean isAscii(int code) {
        return code < 128;
    }

    public static boolean isAscii(byte b) {
        return b >= 0;
    }

    public static byte asciiToLower(int c) {
        return AsciiTables.ToLowerCaseTable[c];
    }

    public static byte asciiToUpper(int c) {
        return AsciiTables.ToUpperCaseTable[c];
    }

    public static boolean isWordGraphPrint(int ctype) {
        return ctype == 12 || ctype == 5 || ctype == 7;
    }

    @Deprecated
    public final int mbcodeStartPosition() {
        return this.minLength() > 1 ? 0 : 128;
    }

    public final boolean isSingleByte() {
        return this.isSingleByte;
    }

    public final boolean isFixedWidth() {
        return this.isFixedWidth;
    }

    public static Encoding load(String name) {
        throw new InternalException("transcoder class <%n> not found", name);
    }
}

