
package com.oracle.truffle.js.runtime;

import com.oracle.truffle.api.exception.AbstractTruffleException;
import com.oracle.truffle.api.interop.ExceptionType;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.library.ExportMessage;
import com.oracle.truffle.api.nodes.Node;

@ExportLibrary(value=InteropLibrary.class)
public class ExitException
extends AbstractTruffleException {
    private static final long serialVersionUID = -1456196298096686373L;
    private final int status;

    public ExitException(int status) {
        this(status, null);
    }

    public ExitException(int status, Node location) {
        super(location);
        this.status = status;
    }

    public int getStatus() {
        return this.status;
    }

    @ExportMessage
    public ExceptionType getExceptionType() {
        return ExceptionType.EXIT;
    }

    @ExportMessage
    public int getExceptionExitStatus() {
        return this.getStatus();
    }
}

