
package com.oracle.truffle.host;

import com.oracle.truffle.api.exception.AbstractTruffleException;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.host.HostContext;
import com.oracle.truffle.host.HostObject;

@ExportLibrary(value=InteropLibrary.class, delegateTo="delegate")
final class HostException
extends AbstractTruffleException {
    private final Throwable original;
    final HostObject delegate;

    HostException(Throwable original, HostContext context) {
        this.original = original;
        this.delegate = HostObject.forException(original, context, this);
    }

    Throwable getOriginal() {
        return this.original;
    }

    @Override
    public String getMessage() {
        return this.getOriginal().getMessage();
    }
}

