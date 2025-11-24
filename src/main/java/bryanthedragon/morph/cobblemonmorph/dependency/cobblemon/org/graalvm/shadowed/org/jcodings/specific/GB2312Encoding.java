
package org.graalvm.shadowed.org.jcodings.specific;

import org.graalvm.shadowed.org.jcodings.specific.EUCKREncoding;

public final class GB2312Encoding
extends EUCKREncoding {
    public static final GB2312Encoding INSTANCE = new GB2312Encoding();

    protected GB2312Encoding() {
        super("GB2312");
    }
}

