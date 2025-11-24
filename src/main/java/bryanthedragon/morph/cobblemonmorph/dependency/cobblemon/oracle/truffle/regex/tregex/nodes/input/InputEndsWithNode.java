
package com.oracle.truffle.regex.tregex.nodes.input;

import com.oracle.truffle.api.ArrayUtils;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.ImportStatic;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.strings.AbstractTruffleString;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.regex.tregex.nodes.input.InputEndsWithNodeGen;
import com.oracle.truffle.regex.tregex.nodes.input.InputLengthNode;
import com.oracle.truffle.regex.tregex.nodes.input.InputReadNode;
import com.oracle.truffle.regex.tregex.string.Encodings;
import com.oracle.truffle.regex.util.TRegexGuards;

@ImportStatic(value={TRegexGuards.class})
public abstract class InputEndsWithNode
extends Node {
    public static InputEndsWithNode create() {
        return InputEndsWithNodeGen.create();
    }

    public abstract boolean execute(Object var1, Object var2, Object var3, Encodings.Encoding var4);

    @Specialization(guards={"mask == null"})
    public boolean doBytes(byte[] input, byte[] suffix, Object mask, Encodings.Encoding encoding) {
        return ArrayUtils.regionEqualsWithOrMask(input, input.length - suffix.length, suffix, 0, suffix.length, null);
    }

    @Specialization(guards={"mask != null"})
    public boolean doBytesMask(byte[] input, byte[] suffix, byte[] mask, Encodings.Encoding encoding) {
        return ArrayUtils.regionEqualsWithOrMask(input, input.length - suffix.length, suffix, 0, mask.length, mask);
    }

    @Specialization(guards={"mask == null"})
    public boolean doString(String input, String suffix, Object mask, Encodings.Encoding encoding) {
        return input.endsWith(suffix);
    }

    @Specialization(guards={"mask != null"})
    public boolean doStringMask(String input, String suffix, String mask, Encodings.Encoding encoding) {
        return ArrayUtils.regionEqualsWithOrMask(input, input.length() - suffix.length(), suffix, 0, mask.length(), mask);
    }

    @Specialization(guards={"mask == null"})
    public boolean doTString(TruffleString input, TruffleString suffix, Object mask, Encodings.Encoding encoding, @Cached TruffleString.RegionEqualByteIndexNode regionEqualsNode) {
        int len2;
        int len1 = input.byteLength(encoding.getTStringEncoding());
        return len1 >= (len2 = suffix.byteLength(encoding.getTStringEncoding())) && regionEqualsNode.execute((AbstractTruffleString)input, len1 - len2, suffix, 0, len2, encoding.getTStringEncoding());
    }

    @Specialization(guards={"mask != null"})
    public boolean doTStringMask(TruffleString input, TruffleString suffix, TruffleString.WithMask mask, Encodings.Encoding encoding, @Cached TruffleString.RegionEqualByteIndexNode regionEqualsNode) {
        int len2;
        int len1 = input.byteLength(encoding.getTStringEncoding());
        return len1 >= (len2 = suffix.byteLength(encoding.getTStringEncoding())) && regionEqualsNode.execute((AbstractTruffleString)input, len1 - len2, mask, 0, len2, encoding.getTStringEncoding());
    }

    @Specialization(guards={"neitherByteArrayNorString(input)", "mask == null"})
    public boolean doTruffleObjBytes(Object input, byte[] suffix, Object mask, Encodings.Encoding encoding, @Cached InputLengthNode lengthNode, @Cached InputReadNode charAtNode) {
        return InputEndsWithNode.endsWithTruffleObj(input, suffix, null, encoding, lengthNode, charAtNode);
    }

    @Specialization(guards={"neitherByteArrayNorString(input)", "mask != null"})
    public boolean doTruffleObjBytesMask(Object input, byte[] suffix, byte[] mask, Encodings.Encoding encoding, @Cached InputLengthNode lengthNode, @Cached InputReadNode charAtNode) {
        assert (mask.length == suffix.length);
        return InputEndsWithNode.endsWithTruffleObj(input, suffix, mask, encoding, lengthNode, charAtNode);
    }

    @Specialization(guards={"neitherByteArrayNorString(input)", "mask == null"})
    public boolean doTruffleObjString(Object input, String suffix, Object mask, Encodings.Encoding encoding, @Cached InputLengthNode lengthNode, @Cached InputReadNode charAtNode) {
        return InputEndsWithNode.endsWithTruffleObj(input, suffix, null, encoding, lengthNode, charAtNode);
    }

    @Specialization(guards={"neitherByteArrayNorString(input)", "mask != null"})
    public boolean doTruffleObjStringMask(Object input, String suffix, String mask, Encodings.Encoding encoding, @Cached InputLengthNode lengthNode, @Cached InputReadNode charAtNode) {
        assert (mask.length() == suffix.length());
        return InputEndsWithNode.endsWithTruffleObj(input, suffix, mask, encoding, lengthNode, charAtNode);
    }

    private static boolean endsWithTruffleObj(Object input, byte[] suffix, byte[] mask, Encodings.Encoding encoding, InputLengthNode lengthNode, InputReadNode charAtNode) {
        int inputLength = lengthNode.execute(input, encoding);
        if (inputLength < suffix.length) {
            return false;
        }
        int offset = inputLength - suffix.length;
        for (int i = 0; i < suffix.length; ++i) {
            if (InputReadNode.readWithMask(input, offset + i, mask, i, encoding, charAtNode) == Byte.toUnsignedInt(suffix[i])) continue;
            return false;
        }
        return true;
    }

    private static boolean endsWithTruffleObj(Object input, String suffix, String mask, Encodings.Encoding encoding, InputLengthNode lengthNode, InputReadNode charAtNode) {
        int inputLength = lengthNode.execute(input, encoding);
        if (inputLength < suffix.length()) {
            return false;
        }
        int offset = inputLength - suffix.length();
        for (int i = 0; i < suffix.length(); ++i) {
            if (InputReadNode.readWithMask(input, offset + i, mask, i, encoding, charAtNode) == suffix.charAt(i)) continue;
            return false;
        }
        return true;
    }
}

