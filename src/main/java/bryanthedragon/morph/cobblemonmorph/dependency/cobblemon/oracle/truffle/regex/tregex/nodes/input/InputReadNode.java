
package com.oracle.truffle.regex.tregex.nodes.input;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.GenerateUncached;
import com.oracle.truffle.api.dsl.ImportStatic;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.InvalidArrayIndexException;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.interop.UnsupportedTypeException;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.regex.runtime.nodes.ToCharNode;
import com.oracle.truffle.regex.tregex.nodes.input.InputReadNodeGen;
import com.oracle.truffle.regex.tregex.string.Encodings;

@GenerateUncached
@ImportStatic(value={Encodings.class})
public abstract class InputReadNode
extends Node {
    public static InputReadNode create() {
        return InputReadNodeGen.create();
    }

    public abstract int execute(Object var1, int var2, Encodings.Encoding var3);

    @Specialization
    static int doBytes(byte[] input, int index, Encodings.Encoding encoding) {
        return Byte.toUnsignedInt(input[index]);
    }

    @Specialization
    static int doString(String input, int index, Encodings.Encoding encoding) {
        return input.charAt(index);
    }

    @Specialization(guards={"encoding != UTF_16", "encoding != UTF_32", "encoding != UTF_16_RAW"})
    static int doTStringUTF8(TruffleString input, int index, Encodings.Encoding encoding, @Cached TruffleString.ReadByteNode readRawNode) {
        return readRawNode.execute(input, index, encoding.getTStringEncoding());
    }

    @Specialization(guards={"encoding == UTF_16 || encoding == UTF_16_RAW"})
    static int doTStringUTF16(TruffleString input, int index, Encodings.Encoding encoding, @Cached TruffleString.ReadCharUTF16Node readRawNode) {
        return readRawNode.execute(input, index);
    }

    @Specialization(guards={"encoding == UTF_32"})
    static int doTStringUTF32(TruffleString input, int index, Encodings.Encoding encoding, @Cached TruffleString.CodePointAtIndexNode readRawNode) {
        return readRawNode.execute(input, index, TruffleString.Encoding.UTF_32);
    }

    @Specialization(guards={"inputs.hasArrayElements(input)"}, limit="2")
    static int doBoxedCharArray(Object input, int index, Encodings.Encoding encoding, @CachedLibrary(value="input") InteropLibrary inputs, @Cached ToCharNode toCharNode) {
        try {
            return toCharNode.execute(inputs.readArrayElement(input, index));
        }
        catch (InvalidArrayIndexException | UnsupportedMessageException | UnsupportedTypeException e) {
            throw CompilerDirectives.shouldNotReachHere();
        }
    }

    public static int readWithMask(Object input, int indexInput, String mask, int indexMask, Encodings.Encoding encoding, InputReadNode charAtNode) {
        CompilerAsserts.partialEvaluationConstant(mask == null);
        int c = charAtNode.execute(input, indexInput, encoding);
        return mask == null ? c : c | mask.charAt(indexMask);
    }

    public static int readWithMask(Object input, int indexInput, byte[] mask, int indexMask, Encodings.Encoding encoding, InputReadNode charAtNode) {
        CompilerAsserts.partialEvaluationConstant(mask == null);
        int c = charAtNode.execute(input, indexInput, encoding);
        return mask == null ? c : c | Byte.toUnsignedInt(mask[indexMask]);
    }
}

