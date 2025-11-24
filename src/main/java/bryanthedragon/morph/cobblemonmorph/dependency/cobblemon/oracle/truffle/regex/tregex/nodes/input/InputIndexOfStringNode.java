
package com.oracle.truffle.regex.tregex.nodes.input;

import com.oracle.truffle.api.ArrayUtils;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.ImportStatic;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.strings.AbstractTruffleString;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.regex.RegexRootNode;
import com.oracle.truffle.regex.tregex.nodes.input.InputIndexOfStringNodeGen;
import com.oracle.truffle.regex.tregex.nodes.input.InputLengthNode;
import com.oracle.truffle.regex.tregex.nodes.input.InputRegionMatchesNode;
import com.oracle.truffle.regex.tregex.string.Encodings;
import com.oracle.truffle.regex.util.TRegexGuards;

@ImportStatic(value={TRegexGuards.class})
public abstract class InputIndexOfStringNode
extends Node {
    public static InputIndexOfStringNode create() {
        return InputIndexOfStringNodeGen.create();
    }

    public abstract int execute(Object var1, int var2, int var3, Object var4, Object var5, Encodings.Encoding var6);

    @Specialization(guards={"mask == null"})
    public int doBytes(byte[] input, int fromIndex, int maxIndex, byte[] match, Object mask, Encodings.Encoding encoding) {
        return ArrayUtils.indexOfWithOrMask(input, fromIndex, maxIndex - fromIndex, match, null);
    }

    @Specialization(guards={"mask != null"})
    public int doBytesMask(byte[] input, int fromIndex, int maxIndex, byte[] match, byte[] mask, Encodings.Encoding encoding) {
        return ArrayUtils.indexOfWithOrMask(input, fromIndex, maxIndex - fromIndex, match, mask);
    }

    @Specialization(guards={"mask == null"})
    public int doString(String input, int fromIndex, int maxIndex, String match, Object mask, Encodings.Encoding encoding) {
        int result = InputIndexOfStringNode.stringIndexOf(input, fromIndex, match);
        return result >= maxIndex ? -1 : result;
    }

    @CompilerDirectives.TruffleBoundary
    private static int stringIndexOf(String input, int fromIndex, String match) {
        return input.indexOf(match, fromIndex);
    }

    @Specialization(guards={"mask != null"})
    public int doStringMask(String input, int fromIndex, int maxIndex, String match, String mask, Encodings.Encoding encoding) {
        return ArrayUtils.indexOfWithOrMask(input, fromIndex, maxIndex - fromIndex, match, mask);
    }

    @Specialization(guards={"mask == null"})
    public int doTString(TruffleString input, int fromIndex, int maxIndex, TruffleString match, Object mask, Encodings.Encoding encoding, @Cached TruffleString.ByteIndexOfStringNode indexOfStringNode) {
        int fromByteIndex = fromIndex << encoding.getStride();
        if (fromByteIndex >= input.byteLength(encoding.getTStringEncoding())) {
            return -1;
        }
        return indexOfStringNode.execute((AbstractTruffleString)input, match, fromByteIndex, maxIndex << encoding.getStride(), encoding.getTStringEncoding()) >> encoding.getStride();
    }

    @Specialization(guards={"mask != null"})
    public int doTStringMask(TruffleString input, int fromIndex, int maxIndex, TruffleString match, TruffleString.WithMask mask, Encodings.Encoding encoding, @Cached TruffleString.ByteIndexOfStringNode indexOfStringNode) {
        int fromByteIndex = fromIndex << encoding.getStride();
        if (fromByteIndex >= input.byteLength(encoding.getTStringEncoding())) {
            return -1;
        }
        return indexOfStringNode.execute((AbstractTruffleString)input, mask, fromByteIndex, maxIndex << encoding.getStride(), encoding.getTStringEncoding()) >> encoding.getStride();
    }

    @Specialization(guards={"neitherByteArrayNorString(input)"})
    public int doTruffleObjBytes(Object input, int fromIndex, int maxIndex, byte[] match, Object mask, Encodings.Encoding encoding, @Cached InputLengthNode lengthNode, @Cached InputRegionMatchesNode regionMatchesNode) {
        if (maxIndex > lengthNode.execute(input, encoding)) {
            return -1;
        }
        if (fromIndex + match.length > maxIndex) {
            return -1;
        }
        for (int i = fromIndex; i <= maxIndex - match.length; ++i) {
            if (CompilerDirectives.inInterpreter()) {
                RegexRootNode.checkThreadInterrupted();
            }
            if (!regionMatchesNode.execute(input, i, match, 0, match.length, mask, encoding)) continue;
            return i;
        }
        return -1;
    }

    @Specialization(guards={"neitherByteArrayNorString(input)"})
    public int doTruffleObjString(Object input, int fromIndex, int maxIndex, String match, Object mask, Encodings.Encoding encoding, @Cached InputLengthNode lengthNode, @Cached InputRegionMatchesNode regionMatchesNode) {
        if (maxIndex > lengthNode.execute(input, encoding)) {
            return -1;
        }
        if (fromIndex + match.length() > maxIndex) {
            return -1;
        }
        for (int i = fromIndex; i <= maxIndex - match.length(); ++i) {
            if (CompilerDirectives.inInterpreter()) {
                RegexRootNode.checkThreadInterrupted();
            }
            if (!regionMatchesNode.execute(input, i, match, 0, match.length(), mask, encoding)) continue;
            return i;
        }
        return -1;
    }
}

