
package org.graalvm.shadowed.org.jcodings.transcode.specific;

import org.graalvm.shadowed.org.jcodings.transcode.AsciiCompatibility;
import org.graalvm.shadowed.org.jcodings.transcode.TranscodeFunctions;
import org.graalvm.shadowed.org.jcodings.transcode.Transcoder;

public class Iso2022jp_encoder_Transcoder
extends Transcoder {
    public static final Transcoder INSTANCE = new Iso2022jp_encoder_Transcoder();

    protected Iso2022jp_encoder_Transcoder() {
        super("stateless-ISO-2022-JP", "ISO-2022-JP", 108, "Iso2022", 1, 3, 5, AsciiCompatibility.ENCODER, 1);
    }

    @Override
    public int stateInit(byte[] statep) {
        return TranscodeFunctions.iso2022jpInit(statep);
    }

    @Override
    public int stateFinish(byte[] state) {
        return TranscodeFunctions.iso2022jpInit(state);
    }

    @Override
    public int startToOutput(byte[] statep, byte[] s, int sStart, int l, byte[] o, int oStart, int oSize) {
        return TranscodeFunctions.funSoIso2022jpEncoder(statep, s, sStart, l, o, oStart, oSize);
    }

    @Override
    public boolean hasFinish() {
        return true;
    }

    @Override
    public int finish(byte[] statep, byte[] p, int start2, int size) {
        return TranscodeFunctions.finishIso2022jpEncoder(statep, p, start2, size);
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

