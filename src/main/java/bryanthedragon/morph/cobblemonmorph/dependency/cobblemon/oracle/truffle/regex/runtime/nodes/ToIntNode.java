
package com.oracle.truffle.regex.runtime.nodes;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GenerateUncached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.interop.UnsupportedTypeException;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.regex.runtime.nodes.ToIntNodeGen;

@GenerateUncached
public abstract class ToIntNode
extends Node {
    public abstract int execute(Object var1) throws UnsupportedTypeException;

    @Specialization
    static int doPrimitiveInt(int arg) {
        return arg;
    }

    @Specialization(guards={"args.fitsInInt(arg)"}, limit="2")
    static int doBoxed(Object arg, @CachedLibrary(value="arg") InteropLibrary args) throws UnsupportedTypeException {
        try {
            return args.asInt(arg);
        }
        catch (UnsupportedMessageException e) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            throw UnsupportedTypeException.create(new Object[]{arg});
        }
    }

    public static ToIntNode create() {
        return ToIntNodeGen.create();
    }
}

