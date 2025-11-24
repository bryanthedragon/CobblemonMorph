
package org.graalvm.shadowed.org.jcodings.transcode.specific;

import org.graalvm.shadowed.org.jcodings.transcode.AsciiCompatibility;
import org.graalvm.shadowed.org.jcodings.transcode.TranscodeFunctions;
import org.graalvm.shadowed.org.jcodings.transcode.Transcoder;
import org.graalvm.shadowed.org.jcodings.transcode.Transcoding;

public class Universal_newline_Transcoder
extends Transcoder {
    private static final int universal_newline = Transcoding.WORDINDEX2INFO(1);
    public static final Transcoder INSTANCE = new Universal_newline_Transcoder();

    protected Universal_newline_Transcoder() {
        super("", "universal_newline", universal_newline, "Newline", 1, 1, 2, AsciiCompatibility.CONVERTER, 2);
    }

    @Override
    public int stateInit(byte[] statep) {
        return TranscodeFunctions.universalNewlineInit(statep);
    }

    @Override
    public int stateFinish(byte[] state) {
        return TranscodeFunctions.universalNewlineInit(state);
    }

    @Override
    public int startToOutput(byte[] statep, byte[] s, int sStart, int l, byte[] o, int oStart, int oSize) {
        return TranscodeFunctions.funSoUniversalNewline(statep, s, sStart, l, o, oStart, oSize);
    }

    @Override
    public boolean hasFinish() {
        return true;
    }

    @Override
    public int finish(byte[] statep, byte[] p, int start2, int size) {
        return TranscodeFunctions.universalNewlineFinish(statep, p, start2, size);
    }

    @Override
    public int resetSize(byte[] statep) {
        return TranscodeFunctions.iso2022jpEncoderResetSequenceSize(statep);
    }

    @Override
    public int resetState(byte[] statep, byte[] p, int start2, int size) {
        return TranscodeFunctions.finishIso2022jpEncoder(statep, p, start2, size);
    }
}

