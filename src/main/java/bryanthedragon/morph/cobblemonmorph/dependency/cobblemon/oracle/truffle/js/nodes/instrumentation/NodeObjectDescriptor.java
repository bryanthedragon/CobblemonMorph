
package com.oracle.truffle.js.nodes.instrumentation;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.library.ExportMessage;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.instrumentation.NodeObjectDescriptorKeys;
import com.oracle.truffle.js.runtime.Strings;
import java.util.HashMap;
import java.util.Map;

@ExportLibrary(value=InteropLibrary.class)
public final class NodeObjectDescriptor
implements TruffleObject {
    private final Map<String, Object> data = new HashMap<String, Object>();

    @CompilerDirectives.TruffleBoundary
    public void addProperty(String name, Object value2) {
        this.data.put(name, value2);
    }

    @CompilerDirectives.TruffleBoundary
    public void addProperty(TruffleString name, Object value2) {
        this.addProperty(Strings.toJavaString(name), value2);
    }

    @ExportMessage
    boolean hasMembers() {
        return true;
    }

    @ExportMessage
    @CompilerDirectives.TruffleBoundary
    Object readMember(String key) {
        assert (this.data.containsKey(key));
        return this.data.get(key);
    }

    @ExportMessage
    @CompilerDirectives.TruffleBoundary
    Object getMembers(boolean includeInternal) {
        return new NodeObjectDescriptorKeys(this.data);
    }

    @ExportMessage
    @CompilerDirectives.TruffleBoundary
    boolean isMemberReadable(String key) {
        return this.data.containsKey(key);
    }
}

