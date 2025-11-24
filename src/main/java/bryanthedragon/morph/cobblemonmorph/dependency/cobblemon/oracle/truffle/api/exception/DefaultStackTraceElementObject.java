
package com.oracle.truffle.api.exception;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.library.ExportMessage;
import com.oracle.truffle.api.nodes.RootNode;
import com.oracle.truffle.api.source.SourceSection;

@ExportLibrary(value=InteropLibrary.class)
final class DefaultStackTraceElementObject
implements TruffleObject {
    private final RootNode rootNode;
    private final SourceSection sourceSection;

    DefaultStackTraceElementObject(RootNode rootNode, SourceSection sourceSection) {
        this.rootNode = rootNode;
        this.sourceSection = sourceSection;
    }

    @ExportMessage
    @CompilerDirectives.TruffleBoundary
    boolean hasExecutableName() {
        return this.rootNode.getName() != null;
    }

    @ExportMessage
    @CompilerDirectives.TruffleBoundary
    Object getExecutableName() {
        return this.rootNode.getName();
    }

    @ExportMessage
    boolean hasSourceLocation() {
        return this.sourceSection != null;
    }

    @ExportMessage
    SourceSection getSourceLocation() throws UnsupportedMessageException {
        if (this.sourceSection == null) {
            throw UnsupportedMessageException.create();
        }
        return this.sourceSection;
    }

    @ExportMessage
    boolean hasDeclaringMetaObject() {
        return false;
    }

    @ExportMessage
    Object getDeclaringMetaObject() throws UnsupportedMessageException {
        throw UnsupportedMessageException.create();
    }
}

