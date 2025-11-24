
package com.oracle.truffle.regex.tregex.nodes.input;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GenerateUncached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.regex.tregex.nodes.input.InputLengthNodeGen;
import com.oracle.truffle.regex.tregex.string.Encodings;

@GenerateUncached
public abstract class InputLengthNode
extends Node {
    public static InputLengthNode create() {
        return InputLengthNodeGen.create();
    }

    public abstract int execute(Object var1, Encodings.Encoding var2);

    @Specialization
    static int doBytes(byte[] input, Encodings.Encoding encoding) {
        return input.length;
    }

    @Specialization
    static int doString(String input, Encodings.Encoding encoding) {
        return input.length();
    }

    @Specialization
    static int doTString(TruffleString input, Encodings.Encoding encoding) {
        return input.byteLength(encoding.getTStringEncoding()) >> encoding.getStride();
    }

    @Specialization(guards={"inputs.hasArrayElements(input)"}, limit="2")
    static int doTruffleObj(Object input, Encodings.Encoding encoding, @CachedLibrary(value="input") InteropLibrary inputs) {
        try {
            long length = inputs.getArraySize(input);
            if (length > Integer.MAX_VALUE) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                throw CompilerDirectives.shouldNotReachHere();
            }
            return (int)length;
        }
        catch (UnsupportedMessageException e) {
            throw CompilerDirectives.shouldNotReachHere();
        }
    }
}

