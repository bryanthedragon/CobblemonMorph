
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.relocations.ibm.icu.text;

import com.cobblemon.mod.relocations.ibm.icu.text.Replaceable;
import com.cobblemon.mod.relocations.ibm.icu.text.UTF16;

public class ReplaceableString
implements Replaceable {
    private StringBuffer buf;

    public ReplaceableString(String str) {
        this.buf = new StringBuffer(str);
    }

    public ReplaceableString(StringBuffer buf) {
        this.buf = buf;
    }

    public ReplaceableString() {
        this.buf = new StringBuffer();
    }

    public String toString() {
        return this.buf.toString();
    }

    public String substring(int start2, int limit) {
        return this.buf.substring(start2, limit);
    }

    @Override
    public int length() {
        return this.buf.length();
    }

    @Override
    public char charAt(int offset) {
        return this.buf.charAt(offset);
    }

    @Override
    public int char32At(int offset) {
        return UTF16.charAt(this.buf, offset);
    }

    @Override
    public void getChars(int srcStart, int srcLimit, char[] dst, int dstStart) {
        if (srcStart != srcLimit) {
            this.buf.getChars(srcStart, srcLimit, dst, dstStart);
        }
    }

    @Override
    public void replace(int start2, int limit, String text) {
        this.buf.replace(start2, limit, text);
    }

    @Override
    public void replace(int start2, int limit, char[] chars, int charsStart, int charsLen) {
        this.buf.delete(start2, limit);
        this.buf.insert(start2, chars, charsStart, charsLen);
    }

    @Override
    public void copy(int start2, int limit, int dest) {
        if (start2 == limit && start2 >= 0 && start2 <= this.buf.length()) {
            return;
        }
        char[] text = new char[limit - start2];
        this.getChars(start2, limit, text, 0);
        this.replace(dest, dest, text, 0, limit - start2);
    }

    @Override
    public boolean hasMetaData() {
        return false;
    }
}

