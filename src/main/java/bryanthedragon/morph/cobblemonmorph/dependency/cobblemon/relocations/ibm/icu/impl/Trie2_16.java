
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.mod.relocations.ibm.icu.impl;

import com.cobblemon.mod.relocations.ibm.icu.impl.Trie2;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;

public final class Trie2_16
extends Trie2 {
    Trie2_16() {
    }

    public static Trie2_16 createFromSerialized(ByteBuffer bytes) throws IOException {
        return (Trie2_16)Trie2.createFromSerialized(bytes);
    }

    @Override
    public final int get(int codePoint) {
        if (codePoint >= 0) {
            if (codePoint < 55296 || codePoint > 56319 && codePoint <= 65535) {
                int ix = this.index[codePoint >> 5];
                ix = (ix << 2) + (codePoint & 0x1F);
                char value2 = this.index[ix];
                return value2;
            }
            if (codePoint <= 65535) {
                int ix = this.index[2048 + (codePoint - 55296 >> 5)];
                ix = (ix << 2) + (codePoint & 0x1F);
                char value3 = this.index[ix];
                return value3;
            }
            if (codePoint < this.highStart) {
                int ix = 2080 + (codePoint >> 11);
                ix = this.index[ix];
                ix += codePoint >> 5 & 0x3F;
                ix = this.index[ix];
                ix = (ix << 2) + (codePoint & 0x1F);
                char value4 = this.index[ix];
                return value4;
            }
            if (codePoint <= 0x10FFFF) {
                char value5 = this.index[this.highValueIndex];
                return value5;
            }
        }
        return this.errorValue;
    }

    @Override
    public int getFromU16SingleLead(char codeUnit) {
        int ix = this.index[codeUnit >> 5];
        ix = (ix << 2) + (codeUnit & 0x1F);
        char value2 = this.index[ix];
        return value2;
    }

    public int serialize(OutputStream os) throws IOException {
        DataOutputStream dos = new DataOutputStream(os);
        int bytesWritten = 0;
        bytesWritten += this.serializeHeader(dos);
        for (int i = 0; i < this.dataLength; ++i) {
            dos.writeChar(this.index[this.data16 + i]);
        }
        return bytesWritten += this.dataLength * 2;
    }

    public int getSerializedLength() {
        return 16 + (this.header.indexLength + this.dataLength) * 2;
    }

    @Override
    int rangeEnd(int startingCP, int limit, int value2) {
        int cp = startingCP;
        int block = 0;
        int index2Block = 0;
        block0: while (cp < limit) {
            if (cp < 55296 || cp > 56319 && cp <= 65535) {
                index2Block = 0;
                block = this.index[cp >> 5] << 2;
            } else if (cp < 65535) {
                index2Block = 2048;
                block = this.index[index2Block + (cp - 55296 >> 5)] << 2;
            } else if (cp < this.highStart) {
                int ix = 2080 + (cp >> 11);
                index2Block = this.index[ix];
                block = this.index[index2Block + (cp >> 5 & 0x3F)] << 2;
            } else {
                if (value2 != this.index[this.highValueIndex]) break;
                cp = limit;
                break;
            }
            if (index2Block == this.index2NullOffset) {
                if (value2 != this.initialValue) break;
                cp += 2048;
                continue;
            }
            if (block == this.dataNullOffset) {
                if (value2 != this.initialValue) break;
                cp += 32;
                continue;
            }
            int startIx = block + (cp & 0x1F);
            int limitIx = block + 32;
            for (int ix = startIx; ix < limitIx; ++ix) {
                if (this.index[ix] == value2) continue;
                cp += ix - startIx;
                break block0;
            }
            cp += limitIx - startIx;
        }
        if (cp > limit) {
            cp = limit;
        }
        return cp - 1;
    }
}

