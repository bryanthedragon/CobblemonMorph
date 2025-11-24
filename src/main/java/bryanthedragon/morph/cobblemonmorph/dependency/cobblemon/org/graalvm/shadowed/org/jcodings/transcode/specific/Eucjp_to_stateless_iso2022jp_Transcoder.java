
package org.graalvm.shadowed.org.jcodings.transcode.specific;

import org.graalvm.shadowed.org.jcodings.transcode.AsciiCompatibility;
import org.graalvm.shadowed.org.jcodings.transcode.TranscodeFunctions;
import org.graalvm.shadowed.org.jcodings.transcode.Transcoder;

public class Eucjp_to_stateless_iso2022jp_Transcoder
extends Transcoder {
    public static final Transcoder INSTANCE = new Eucjp_to_stateless_iso2022jp_Transcoder();

    protected Eucjp_to_stateless_iso2022jp_Transcoder() {
        super("EUC-JP", "stateless-ISO-2022-JP", 192, "Iso2022", 1, 3, 3, AsciiCompatibility.CONVERTER, 0);
    }

    @Override
    public int startToOutput(byte[] statep, byte[] s, int sStart, int l, byte[] o, int oStart, int oSize) {
        return TranscodeFunctions.funSoEucjpToStatelessIso2022jp(statep, s, sStart, l, o, oStart, oSize);
    }
}

