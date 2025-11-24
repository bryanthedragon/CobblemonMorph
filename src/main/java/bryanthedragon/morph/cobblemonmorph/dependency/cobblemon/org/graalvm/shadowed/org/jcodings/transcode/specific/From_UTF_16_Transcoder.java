
package org.graalvm.shadowed.org.jcodings.transcode.specific;

import org.graalvm.shadowed.org.jcodings.transcode.AsciiCompatibility;
import org.graalvm.shadowed.org.jcodings.transcode.TranscodeFunctions;
import org.graalvm.shadowed.org.jcodings.transcode.Transcoder;

public class From_UTF_16_Transcoder
extends Transcoder {
    public static final Transcoder INSTANCE = new From_UTF_16_Transcoder();

    protected From_UTF_16_Transcoder() {
        super("UTF-16", "UTF-8", 276, "Utf1632", 2, 4, 4, AsciiCompatibility.DECODER, 1);
    }

    @Override
    public boolean hasStateInit() {
        return true;
    }

    @Override
    public int stateInit(byte[] statep) {
        statep[0] = 0;
        return 0;
    }

    @Override
    public int startToInfo(byte[] statep, byte[] s, int sStart, int l) {
        return TranscodeFunctions.funSiFromUTF16(statep, s, sStart, l);
    }

    @Override
    public int startToOutput(byte[] statep, byte[] s, int sStart, int l, byte[] o, int oStart, int oSize) {
        return TranscodeFunctions.funSoFromUTF16(statep, s, sStart, l, o, oStart, oSize);
    }
}

