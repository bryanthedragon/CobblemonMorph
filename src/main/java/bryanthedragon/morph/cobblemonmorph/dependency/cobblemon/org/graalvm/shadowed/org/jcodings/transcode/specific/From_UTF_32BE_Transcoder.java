
package org.graalvm.shadowed.org.jcodings.transcode.specific;

import org.graalvm.shadowed.org.jcodings.transcode.AsciiCompatibility;
import org.graalvm.shadowed.org.jcodings.transcode.TranscodeFunctions;
import org.graalvm.shadowed.org.jcodings.transcode.Transcoder;

public class From_UTF_32BE_Transcoder
extends Transcoder {
    public static final Transcoder INSTANCE = new From_UTF_32BE_Transcoder();

    protected From_UTF_32BE_Transcoder() {
        super("UTF-32BE", "UTF-8", 252, "Utf1632", 4, 4, 4, AsciiCompatibility.DECODER, 0);
    }

    @Override
    public int startToOutput(byte[] statep, byte[] s, int sStart, int l, byte[] o, int oStart, int oSize) {
        return TranscodeFunctions.funSoFromUTF32BE(statep, s, sStart, l, o, oStart, oSize);
    }
}

