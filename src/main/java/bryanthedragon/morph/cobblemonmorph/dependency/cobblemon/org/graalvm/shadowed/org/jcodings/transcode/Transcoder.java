
package org.graalvm.shadowed.org.jcodings.transcode;

import org.graalvm.shadowed.org.jcodings.exception.InternalException;
import org.graalvm.shadowed.org.jcodings.transcode.AsciiCompatibility;
import org.graalvm.shadowed.org.jcodings.transcode.GenericTranscoder;
import org.graalvm.shadowed.org.jcodings.transcode.Transcoding;
import org.graalvm.shadowed.org.jcodings.transcode.TranscodingInstruction;
import org.graalvm.shadowed.org.jcodings.util.ArrayReader;
import org.graalvm.shadowed.org.jcodings.util.BytesHash;
import org.graalvm.shadowed.org.jcodings.util.ObjHash;

public abstract class Transcoder
implements TranscodingInstruction {
    final byte[] source;
    final byte[] destination;
    final int hashCode;
    final int treeStart;
    final byte[] byteArray;
    final int[] intArray;
    public final int inputUnitLength;
    public final int maxInput;
    public final int maxOutput;
    public final AsciiCompatibility compatibility;
    final int stateSize;
    static final ObjHash<String, byte[]> byteArrayHash = new ObjHash();
    static final ObjHash<String, int[]> wordArrayHash = new ObjHash();

    protected Transcoder(byte[] source, byte[] destination, int treeStart, String arrayKey, int inputUnitLength, int maxInput, int maxOutput, AsciiCompatibility compatibility, int stateSize) {
        this.source = source;
        this.destination = destination;
        this.hashCode = BytesHash.hashCode(this.source, 0, this.source.length);
        this.treeStart = treeStart;
        byte[] bytes = byteArrayHash.get(arrayKey);
        if (bytes == null) {
            bytes = ArrayReader.readByteArray("Transcoder_" + arrayKey + "_ByteArray");
            byteArrayHash.put(arrayKey, bytes);
        }
        this.byteArray = bytes;
        int[] ints = wordArrayHash.get(arrayKey);
        if (ints == null) {
            ints = ArrayReader.readIntArray("Transcoder_" + arrayKey + "_WordArray");
            wordArrayHash.put(arrayKey, ints);
        }
        this.intArray = ints;
        this.inputUnitLength = inputUnitLength;
        this.maxInput = maxInput;
        this.maxOutput = maxOutput;
        this.compatibility = compatibility;
        this.stateSize = stateSize;
    }

    protected Transcoder(String source, String destination, int treeStart, String arrayKey, int inputUnitLength, int maxInput, int maxOutput, AsciiCompatibility compatibility, int stateSize) {
        this(source.getBytes(), destination.getBytes(), treeStart, arrayKey, inputUnitLength, maxInput, maxOutput, compatibility, stateSize);
    }

    public byte[] getSource() {
        return this.source;
    }

    public byte[] getDestination() {
        return this.destination;
    }

    public boolean hasStateInit() {
        return false;
    }

    public int stateInit(byte[] statep) {
        return 0;
    }

    public int stateFinish(byte[] stateFinish) {
        return 0;
    }

    public int infoToInfo(byte[] statep, int o) {
        throw new RuntimeException("unimplemented infoToInfo needed in " + this);
    }

    public int startToInfo(byte[] statep, byte[] s, int sStart, int l) {
        throw new RuntimeException("unimplemented startToInfo needed in " + this);
    }

    public int infoToOutput(byte[] statep, int nextInfo, byte[] p, int start2, int size) {
        throw new RuntimeException("unimplemented intoToOutput needed in " + this);
    }

    public boolean hasFinish() {
        return false;
    }

    public int finish(byte[] statep, byte[] p, int start2, int size) {
        return 0;
    }

    public int resetSize(byte[] statep) {
        return 0;
    }

    public int resetState(byte[] statep, byte[] p, int start2, int size) {
        return 0;
    }

    public int startToOutput(byte[] statep, byte[] s, int sStart, int l, byte[] o, int oStart, int oSize) {
        throw new RuntimeException("unimplemented startToOutput needed in " + this);
    }

    public int startInfoToOutput(byte[] statep, byte[] s, int sStart, int l, int info, byte[] o, int oStart, int oSize) {
        throw new RuntimeException("unimplemented startInfoToOutput needed in " + this);
    }

    public final Transcoding transcoding(int flags) {
        Transcoding tc = new Transcoding(this, flags);
        if (this.hasStateInit()) {
            this.stateInit(tc.state);
        }
        return tc;
    }

    public static Transcoder load(String name) {
        throw new InternalException("transcoder class <%n> not found", name);
    }

    public String toString() {
        return new String(this.source) + " => " + new String(this.destination);
    }

    public String toStringFull() {
        int i;
        String s = "Transcoder (" + new String(this.source) + " => " + new String(this.destination) + ")\n";
        s = s + "  class: " + this.getClass().getSimpleName() + "\n";
        s = s + "  treeStart: " + this.treeStart + "\n";
        s = s + "  byteArray:" + this.byteArray.length + " (";
        for (i = 0; i < 20; ++i) {
            s = s + (this.byteArray[i] & 0xFF) + ", ";
        }
        s = s + "...)\n";
        s = s + "  wordArray:" + this.intArray.length + " (";
        for (i = 0; i < 20; ++i) {
            s = s + ((long)this.intArray[i] & 0xFFFFFFFFL) + ", ";
        }
        s = s + "...)\n";
        s = s + "  input unit length: " + this.inputUnitLength + "\n";
        s = s + "  max input: " + this.maxInput + "\n";
        s = s + "  max output: " + this.maxOutput + "\n";
        s = s + "  compatibility: " + (Object)((Object)this.compatibility) + "\n";
        s = s + "  state size: " + this.stateSize + "\n";
        return s;
    }

    static final class GenericTranscoderEntry {
        final byte[] source;
        final byte[] destination;
        final String arrayKey;
        final int treeStart;
        final int inputUnitLength;
        final int maxInput;
        final int maxOutput;
        final int stateSize;
        final AsciiCompatibility compatibility;

        GenericTranscoderEntry(String source, String destination, int treeStart, String arrayKey, int inputUnitLength, int maxInput, int maxOutput, AsciiCompatibility compatibility, int stateSize) {
            this.source = source.getBytes();
            this.destination = destination.getBytes();
            this.treeStart = treeStart;
            this.arrayKey = arrayKey;
            this.inputUnitLength = inputUnitLength;
            this.maxInput = maxInput;
            this.maxOutput = maxOutput;
            this.compatibility = compatibility;
            this.stateSize = stateSize;
        }

        Transcoder createTranscoder() {
            return new GenericTranscoder(this.source, this.destination, this.treeStart, this.arrayKey, this.inputUnitLength, this.maxInput, this.maxOutput, this.compatibility, this.stateSize);
        }
    }
}

