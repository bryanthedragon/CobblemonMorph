
package com.oracle.truffle.api.debug;

import com.oracle.truffle.api.debug.DebugValue;
import com.oracle.truffle.api.debug.DebuggerSession;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.nodes.LanguageInfo;
import java.util.AbstractList;

final class ValueInteropList
extends AbstractList<DebugValue> {
    static final InteropLibrary INTEROP = InteropLibrary.getFactory().getUncached();
    private final DebuggerSession session;
    private final LanguageInfo language;
    private final Object list;

    ValueInteropList(DebuggerSession session, LanguageInfo language, Object list) {
        this.session = session;
        this.language = language;
        this.list = list;
    }

    @Override
    public DebugValue get(int index) {
        return new DebugValue.ArrayElementValue(this.session, this.language, null, this.list, index);
    }

    @Override
    public DebugValue set(int index, DebugValue newValue) {
        Object oldValue = null;
        DebugValue currentValue = this.get(index);
        if (INTEROP.isArrayElementReadable(this.list, index)) {
            oldValue = currentValue.get();
        }
        currentValue.set(newValue);
        if (oldValue != null) {
            return new DebugValue.HeapValue(this.session, String.valueOf(index), oldValue);
        }
        return null;
    }

    @Override
    public int size() {
        try {
            return (int)INTEROP.getArraySize(this.list);
        }
        catch (UnsupportedMessageException e) {
            return 0;
        }
    }
}

