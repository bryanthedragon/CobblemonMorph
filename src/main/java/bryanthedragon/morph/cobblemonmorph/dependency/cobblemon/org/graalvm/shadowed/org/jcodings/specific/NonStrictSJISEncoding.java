
package org.graalvm.shadowed.org.jcodings.specific;

import org.graalvm.shadowed.org.jcodings.specific.BaseSJISEncoding;

public final class NonStrictSJISEncoding
extends BaseSJISEncoding {
    public static final NonStrictSJISEncoding INSTANCE = new NonStrictSJISEncoding();

    protected NonStrictSJISEncoding() {
        super("Shift_JIS", null);
    }

    @Override
    public int length(byte[] bytes, int p, int end2) {
        return this.length(bytes[p]);
    }
}

