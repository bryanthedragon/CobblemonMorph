
package org.graalvm.shadowed.org.jcodings.transcode.specific;

import org.graalvm.shadowed.org.jcodings.transcode.AsciiCompatibility;
import org.graalvm.shadowed.org.jcodings.transcode.TranscodeFunctions;
import org.graalvm.shadowed.org.jcodings.transcode.Transcoder;

public class From_GB18030_Transcoder
extends Transcoder {
    public static final Transcoder INSTANCE = new From_GB18030_Transcoder();

    protected From_GB18030_Transcoder() {
        super("GB18030", "UTF-8", 57668, "Gb18030", 1, 4, 4, AsciiCompatibility.CONVERTER, 0);
    }

    @Override
    public int startToOutput(byte[] statep, byte[] s, int sStart, int l, byte[] o, int oStart, int oSize) {
        return TranscodeFunctions.funSoFromGB18030(statep, s, sStart, l, o, oStart, oSize);
    }

    @Override
    public int startInfoToOutput(byte[] statep, byte[] s, int sStart, int l, int info, byte[] o, int oStart, int oSize) {
        return TranscodeFunctions.funSioFromGB18030(statep, s, sStart, l, info, o, oStart, oSize);
    }
}

