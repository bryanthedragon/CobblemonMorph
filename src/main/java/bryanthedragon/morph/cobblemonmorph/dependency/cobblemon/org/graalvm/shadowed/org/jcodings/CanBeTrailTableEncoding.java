
package org.graalvm.shadowed.org.jcodings;

import org.graalvm.shadowed.org.jcodings.MultiByteEncoding;

public abstract class CanBeTrailTableEncoding
extends MultiByteEncoding {
    protected final boolean[] CanBeTrailTable;

    protected CanBeTrailTableEncoding(String name, int minLength, int maxLength, int[] EncLen, int[][] Trans, short[] CTypeTable, boolean[] CanBeTrailTable) {
        super(name, minLength, maxLength, EncLen, Trans, CTypeTable);
        this.CanBeTrailTable = CanBeTrailTable;
    }

    @Override
    public int leftAdjustCharHead(byte[] bytes, int p, int s, int end2) {
        int len;
        if (s <= p) {
            return s;
        }
        int p_ = s;
        if (this.CanBeTrailTable[bytes[p_] & 0xFF]) {
            while (p_ > p) {
                if (this.EncLen[bytes[--p_] & 0xFF] > 1) continue;
                ++p_;
                break;
            }
        }
        if (p_ + (len = this.length(bytes, p_, end2)) > s) {
            return p_;
        }
        return (p_ += len) + (s - p_ & 0xFFFFFFFE);
    }

    @Override
    public boolean isReverseMatchAllowed(byte[] bytes, int p, int end2) {
        return !this.CanBeTrailTable[bytes[p] & 0xFF];
    }
}

