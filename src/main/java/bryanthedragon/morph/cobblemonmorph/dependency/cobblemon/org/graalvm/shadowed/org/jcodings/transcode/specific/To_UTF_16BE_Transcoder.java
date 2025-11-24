
package org.graalvm.shadowed.org.jcodings.transcode.specific;

import org.graalvm.shadowed.org.jcodings.transcode.AsciiCompatibility;
import org.graalvm.shadowed.org.jcodings.transcode.TranscodeFunctions;
import org.graalvm.shadowed.org.jcodings.transcode.Transcoder;

public class To_UTF_16BE_Transcoder
extends Transcoder {
    public static final Transcoder INSTANCE = new To_UTF_16BE_Transcoder();

    protected To_UTF_16BE_Transcoder() {
        super("UTF-8", "UTF-16BE", 416, "Utf1632", 1, 4, 4, AsciiCompatibility.ENCODER, 0);
    }

    @Override
    public int startToOutput(byte[] statep, byte[] s, int sStart, int l, byte[] o, int oStart, int oSize) {
        return TranscodeFunctions.funSoToUTF16BE(statep, s, sStart, l, o, oStart, oSize);
    }
}

