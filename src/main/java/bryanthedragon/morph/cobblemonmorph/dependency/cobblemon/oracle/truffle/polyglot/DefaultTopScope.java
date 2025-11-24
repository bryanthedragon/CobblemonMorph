
package com.oracle.truffle.polyglot;

import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.interop.UnknownIdentifierException;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.library.ExportMessage;
import com.oracle.truffle.polyglot.PolyglotBindings;
import java.util.Collections;

@ExportLibrary(value=InteropLibrary.class)
final class DefaultTopScope
implements TruffleObject {
    private static final Object EMPTY_MEMBERS = new PolyglotBindings.Members(Collections.emptySet());

    DefaultTopScope() {
    }

    @ExportMessage
    boolean hasMembers() {
        return true;
    }

    @ExportMessage
    Object readMember(String member) throws UnknownIdentifierException {
        throw UnknownIdentifierException.create(member);
    }

    @ExportMessage
    Object getMembers(boolean includeInternal) {
        return EMPTY_MEMBERS;
    }

    @ExportMessage
    boolean isMemberReadable(String member) {
        return false;
    }
}

