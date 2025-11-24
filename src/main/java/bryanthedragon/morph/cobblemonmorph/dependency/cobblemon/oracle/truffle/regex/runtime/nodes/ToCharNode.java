
package com.oracle.truffle.regex.runtime.nodes;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GenerateUncached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.interop.UnsupportedTypeException;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.regex.runtime.nodes.ToCharNodeGen;

@GenerateUncached
public abstract class ToCharNode
extends Node {
    public abstract char execute(Object var1) throws UnsupportedTypeException;

    @Specialization
    static char doByte(byte arg) {
        return (char)Byte.toUnsignedInt(arg);
    }

    @Specialization
    static char doChar(char arg) {
        return arg;
    }

    @Specialization(guards={"args.fitsInInt(arg)"}, limit="2")
    static char doLong(Object arg, @CachedLibrary(value="arg") InteropLibrary args) throws UnsupportedTypeException {
        try {
            int asInt = args.asInt(arg);
            if (asInt > 65535) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                throw UnsupportedTypeException.create(new Object[]{arg});
            }
            return (char)asInt;
        }
        catch (UnsupportedMessageException e) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            throw UnsupportedTypeException.create(new Object[]{arg});
        }
    }

    public static ToCharNode create() {
        return ToCharNodeGen.create();
    }
}

