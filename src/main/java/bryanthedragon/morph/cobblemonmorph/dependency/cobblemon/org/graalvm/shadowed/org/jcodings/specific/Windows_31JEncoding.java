
package org.graalvm.shadowed.org.jcodings.specific;

import org.graalvm.shadowed.org.jcodings.specific.BaseSJISEncoding;
import org.graalvm.shadowed.org.jcodings.specific.SJISEncoding;

public final class Windows_31JEncoding
extends BaseSJISEncoding {
    public static final Windows_31JEncoding INSTANCE = new Windows_31JEncoding();

    protected Windows_31JEncoding() {
        super("Windows-31J", SJISEncoding.SjisTrans);
    }

    @Override
    public String getCharsetName() {
        return "Windows-31J";
    }

    @Override
    public int length(byte[] bytes, int p, int end2) {
        return this.safeLengthForUptoTwo(bytes, p, end2);
    }
}

