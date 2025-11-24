
package org.graalvm.shadowed.org.jcodings;

import org.graalvm.shadowed.org.jcodings.MultiByteEncoding;

public abstract class EucEncoding
extends MultiByteEncoding {
    protected EucEncoding(String name, int minLength, int maxLength, int[] EncLen, int[][] Trans, short[] CTypeTable) {
        super(name, minLength, maxLength, EncLen, Trans, CTypeTable);
    }

    protected abstract boolean isLead(int var1);

    @Override
    public int leftAdjustCharHead(byte[] bytes, int p, int s, int end2) {
        int p_;
        if (s <= p) {
            return s;
        }
        for (p_ = s; !this.isLead(bytes[p_] & 0xFF) && p_ > p; --p_) {
        }
        int len = this.length(bytes, p_, end2);
        if (p_ + len > s) {
            return p_;
        }
        return (p_ += len) + (s - p_ & 0xFFFFFFFE);
    }
}

