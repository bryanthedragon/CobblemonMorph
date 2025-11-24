
package com.oracle.truffle.regex.runtime.nodes;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.GenerateUncached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.InvalidArrayIndexException;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.interop.UnsupportedTypeException;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.regex.runtime.nodes.ToCharNode;
import com.oracle.truffle.regex.runtime.nodes.ToStringNodeGen;

@GenerateUncached
public abstract class ToStringNode
extends Node {
    public static ToStringNode create() {
        return ToStringNodeGen.create();
    }

    public abstract String execute(Object var1) throws UnsupportedTypeException;

    @Specialization
    static String doString(String input) {
        return input;
    }

    @Specialization(guards={"inputs.isString(input)"}, limit="2")
    static String doBoxedString(Object input, @CachedLibrary(value="input") InteropLibrary inputs) throws UnsupportedTypeException {
        try {
            return inputs.asString(input);
        }
        catch (UnsupportedMessageException e) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            throw UnsupportedTypeException.create(new Object[]{input});
        }
    }

    @Specialization(guards={"inputs.hasArrayElements(input)"}, limit="2")
    static String doBoxedCharArray(Object input, @CachedLibrary(value="input") InteropLibrary inputs, @Cached ToCharNode toCharNode) throws UnsupportedTypeException {
        try {
            long inputLength = inputs.getArraySize(input);
            if (inputLength > Integer.MAX_VALUE) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                throw UnsupportedTypeException.create(new Object[]{input});
            }
            StringBuilder sb = ToStringNode.createStringBuilder((int)inputLength);
            int i = 0;
            while ((long)i < inputLength) {
                ToStringNode.stringBuilderAppend(sb, toCharNode.execute(inputs.readArrayElement(input, i)));
                ++i;
            }
            return ToStringNode.stringBuilderToString(sb);
        }
        catch (InvalidArrayIndexException | UnsupportedMessageException e) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            throw UnsupportedTypeException.create(new Object[]{input});
        }
    }

    @CompilerDirectives.TruffleBoundary
    private static StringBuilder createStringBuilder(int inputLength) {
        return new StringBuilder(inputLength);
    }

    @CompilerDirectives.TruffleBoundary
    private static void stringBuilderAppend(StringBuilder stringBuilder, char c) {
        stringBuilder.append(c);
    }

    @CompilerDirectives.TruffleBoundary
    private static String stringBuilderToString(StringBuilder stringBuilder) {
        return stringBuilder.toString();
    }
}

