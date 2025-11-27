
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.relocations.ibm.icu.impl;

import com.cobblemon.mod.relocations.ibm.icu.text.UCharacterIterator;

public final class UCharArrayIterator
extends UCharacterIterator {
    private final char[] text;
    private final int start;
    private final int limit;
    private int pos;

    public UCharArrayIterator(char[] text, int start2, int limit) {
        if (start2 < 0 || limit > text.length || start2 > limit) {
            throw new IllegalArgumentException("start: " + start2 + " or limit: " + limit + " out of range [0, " + text.length + ")");
        }
        this.text = text;
        this.start = start2;
        this.limit = limit;
        this.pos = start2;
    }

    @Override
    public int current() {
        return this.pos < this.limit ? this.text[this.pos] : -1;
    }

    @Override
    public int getLength() {
        return this.limit - this.start;
    }

    @Override
    public int getIndex() {
        return this.pos - this.start;
    }

    @Override
    public int next() {
        return this.pos < this.limit ? this.text[this.pos++] : -1;
    }

    @Override
    public int previous() {
        return this.pos > this.start ? this.text[--this.pos] : -1;
    }

    @Override
    public void setIndex(int index) {
        if (index < 0 || index > this.limit - this.start) {
            throw new IndexOutOfBoundsException("index: " + index + " out of range [0, " + (this.limit - this.start) + ")");
        }
        this.pos = this.start + index;
    }

    @Override
    public int getText(char[] fillIn, int offset) {
        int len = this.limit - this.start;
        System.arraycopy(this.text, this.start, fillIn, offset, len);
        return len;
    }

    @Override
    public Object clone() {
        try {
            return super.clone();
        }
        catch (CloneNotSupportedException e) {
            return null;
        }
    }
}

