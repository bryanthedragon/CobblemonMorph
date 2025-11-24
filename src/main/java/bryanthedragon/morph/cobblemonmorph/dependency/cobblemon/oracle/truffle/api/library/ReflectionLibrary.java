
package com.oracle.truffle.api.library;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.library.GenerateLibrary;
import com.oracle.truffle.api.library.Library;
import com.oracle.truffle.api.library.LibraryFactory;
import com.oracle.truffle.api.library.Message;
import com.oracle.truffle.api.library.ReflectionLibraryDefault;

@GenerateLibrary
@GenerateLibrary.DefaultExport(value=ReflectionLibraryDefault.class)
public abstract class ReflectionLibrary
extends Library {
    private static final LibraryFactory<ReflectionLibrary> FACTORY = LibraryFactory.resolve(ReflectionLibrary.class);
    static final ReflectionLibrary UNCACHED = FACTORY.getUncached();

    protected ReflectionLibrary() {
    }

    @CompilerDirectives.TruffleBoundary
    @GenerateLibrary.Abstract
    public Object send(Object receiver, Message message, Object ... args) throws Exception {
        throw new AbstractMethodError();
    }

    public static LibraryFactory<ReflectionLibrary> getFactory() {
        return FACTORY;
    }

    public static ReflectionLibrary getUncached() {
        return UNCACHED;
    }

    public static ReflectionLibrary getUncached(Object v) {
        return FACTORY.getUncached(v);
    }
}

