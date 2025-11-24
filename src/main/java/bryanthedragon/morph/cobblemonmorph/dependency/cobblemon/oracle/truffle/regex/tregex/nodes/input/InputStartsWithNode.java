
package com.oracle.truffle.regex.tregex.nodes.input;

import com.oracle.truffle.api.ArrayUtils;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.ImportStatic;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.strings.AbstractTruffleString;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.regex.tregex.nodes.input.InputLengthNode;
import com.oracle.truffle.regex.tregex.nodes.input.InputReadNode;
import com.oracle.truffle.regex.tregex.nodes.input.InputStartsWithNodeGen;
import com.oracle.truffle.regex.tregex.string.Encodings;
import com.oracle.truffle.regex.util.TRegexGuards;

@ImportStatic(value={TRegexGuards.class})
public abstract class InputStartsWithNode
extends Node {
    public static InputStartsWithNode create() {
        return InputStartsWithNodeGen.create();
    }

    public abstract boolean execute(Object var1, Object var2, Object var3, Encodings.Encoding var4);

    @Specialization(guards={"mask == null"})
    public boolean doBytes(byte[] input, byte[] prefix, Object mask, Encodings.Encoding encoding) {
        return ArrayUtils.regionEqualsWithOrMask(input, 0, prefix, 0, prefix.length, null);
    }

    @Specialization(guards={"mask != null"})
    public boolean doBytesMask(byte[] input, byte[] prefix, byte[] mask, Encodings.Encoding encoding) {
        return ArrayUtils.regionEqualsWithOrMask(input, 0, prefix, 0, prefix.length, mask);
    }

    @Specialization(guards={"mask == null"})
    public boolean doString(String input, String prefix, Object mask, Encodings.Encoding encoding) {
        return input.startsWith(prefix);
    }

    @Specialization(guards={"mask != null"})
    public boolean doStringMask(String input, String prefix, String mask, Encodings.Encoding encoding) {
        return ArrayUtils.regionEqualsWithOrMask(input, 0, prefix, 0, mask.length(), mask);
    }

    @Specialization(guards={"mask == null"})
    public boolean doTString(TruffleString input, TruffleString prefix, Object mask, Encodings.Encoding encoding, @Cached TruffleString.RegionEqualByteIndexNode regionEqualsNode) {
        int len2;
        int len1 = input.byteLength(encoding.getTStringEncoding());
        return len1 >= (len2 = prefix.byteLength(encoding.getTStringEncoding())) && regionEqualsNode.execute((AbstractTruffleString)input, 0, prefix, 0, len2, encoding.getTStringEncoding());
    }

    @Specialization(guards={"mask != null"})
    public boolean doTStringMask(TruffleString input, TruffleString prefix, TruffleString.WithMask mask, Encodings.Encoding encoding, @Cached TruffleString.RegionEqualByteIndexNode regionEqualsNode) {
        int len2;
        int len1 = input.byteLength(encoding.getTStringEncoding());
        return len1 >= (len2 = prefix.byteLength(encoding.getTStringEncoding())) && regionEqualsNode.execute((AbstractTruffleString)input, 0, mask, 0, len2, encoding.getTStringEncoding());
    }

    @Specialization(guards={"neitherByteArrayNorString(input)", "mask == null"})
    public boolean doTruffleObjBytes(Object input, byte[] prefix, Object mask, Encodings.Encoding encoding, @Cached InputLengthNode lengthNode, @Cached InputReadNode charAtNode) {
        return InputStartsWithNode.startsWithTruffleObj(input, prefix, null, encoding, lengthNode, charAtNode);
    }

    @Specialization(guards={"neitherByteArrayNorString(input)", "mask != null"})
    public boolean doTruffleObjBytesMask(Object input, byte[] prefix, byte[] mask, Encodings.Encoding encoding, @Cached InputLengthNode lengthNode, @Cached InputReadNode charAtNode) {
        assert (mask.length == prefix.length);
        return InputStartsWithNode.startsWithTruffleObj(input, prefix, mask, encoding, lengthNode, charAtNode);
    }

    @Specialization(guards={"neitherByteArrayNorString(input)", "mask == null"})
    public boolean doTruffleObjString(Object input, String prefix, Object mask, Encodings.Encoding encoding, @Cached InputLengthNode lengthNode, @Cached InputReadNode charAtNode) {
        return InputStartsWithNode.startsWithTruffleObj(input, prefix, null, encoding, lengthNode, charAtNode);
    }

    @Specialization(guards={"neitherByteArrayNorString(input)", "mask != null"})
    public boolean doTruffleObjStringMask(Object input, String prefix, String mask, Encodings.Encoding encoding, @Cached InputLengthNode lengthNode, @Cached InputReadNode charAtNode) {
        assert (mask.length() == prefix.length());
        return InputStartsWithNode.startsWithTruffleObj(input, prefix, mask, encoding, lengthNode, charAtNode);
    }

    private static boolean startsWithTruffleObj(Object input, byte[] prefix, byte[] mask, Encodings.Encoding encoding, InputLengthNode lengthNode, InputReadNode charAtNode) {
        if (lengthNode.execute(input, encoding) < prefix.length) {
            return false;
        }
        for (int i = 0; i < prefix.length; ++i) {
            if (InputReadNode.readWithMask(input, i, mask, i, encoding, charAtNode) == Byte.toUnsignedInt(prefix[i])) continue;
            return false;
        }
        return true;
    }

    private static boolean startsWithTruffleObj(Object input, String prefix, String mask, Encodings.Encoding encoding, InputLengthNode lengthNode, InputReadNode charAtNode) {
        if (lengthNode.execute(input, encoding) < prefix.length()) {
            return false;
        }
        for (int i = 0; i < prefix.length(); ++i) {
            if (InputReadNode.readWithMask(input, i, mask, i, encoding, charAtNode) == prefix.charAt(i)) continue;
            return false;
        }
        return true;
    }
}

