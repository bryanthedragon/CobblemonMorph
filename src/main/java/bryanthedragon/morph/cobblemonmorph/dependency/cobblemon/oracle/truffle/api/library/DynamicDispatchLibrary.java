
package com.oracle.truffle.api.library;

import com.oracle.truffle.api.library.GenerateLibrary;
import com.oracle.truffle.api.library.Library;
import com.oracle.truffle.api.library.LibraryFactory;

@GenerateLibrary(dynamicDispatchEnabled=false)
public abstract class DynamicDispatchLibrary
extends Library {
    static final LibraryFactory<DynamicDispatchLibrary> FACTORY = LibraryFactory.resolve(DynamicDispatchLibrary.class);

    protected DynamicDispatchLibrary() {
    }

    @GenerateLibrary.Abstract
    public Class<?> dispatch(Object receiver) {
        return null;
    }

    public abstract Object cast(Object var1);

    public static LibraryFactory<DynamicDispatchLibrary> getFactory() {
        return FACTORY;
    }
}

