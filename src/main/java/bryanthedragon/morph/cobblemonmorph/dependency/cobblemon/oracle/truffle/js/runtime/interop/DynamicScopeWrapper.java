
package com.oracle.truffle.js.runtime.interop;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.interop.UnknownIdentifierException;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.library.ExportMessage;
import com.oracle.truffle.api.object.DynamicObjectLibrary;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.interop.ExportValueNode;
import com.oracle.truffle.js.runtime.Properties;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.interop.InteropList;
import com.oracle.truffle.js.runtime.objects.Dead;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSProperty;
import java.util.ArrayList;

@ExportLibrary(value=InteropLibrary.class)
public final class DynamicScopeWrapper
implements TruffleObject {
    final JSDynamicObject scope;

    public DynamicScopeWrapper(JSDynamicObject scope) {
        this.scope = scope;
    }

    boolean isConst(TruffleString name, DynamicObjectLibrary access) {
        return JSProperty.isConst(Properties.getProperty(access, this.scope, name));
    }

    @ExportMessage
    boolean hasMembers() {
        return true;
    }

    @ExportMessage
    @CompilerDirectives.TruffleBoundary
    Object getMembers(boolean includeInternal, @CachedLibrary(value="this.scope") DynamicObjectLibrary access) {
        ArrayList<String> keys = new ArrayList<String>();
        for (Object key : access.getKeyArray(this.scope)) {
            Object value2;
            if (!Strings.isTString(key) || (value2 = Properties.getOrDefault(access, this.scope, key, null)) == null || value2 == Dead.instance()) continue;
            keys.add(Strings.toJavaString((TruffleString)key));
        }
        return InteropList.create(keys);
    }

    @ExportMessage
    boolean isMemberReadable(String name, @Cached @Cached.Shared(value="fromJavaStringNode") TruffleString.FromJavaStringNode fromJavaStringNode, @CachedLibrary(value="this.scope") DynamicObjectLibrary access) {
        TruffleString tsName = Strings.fromJavaString(fromJavaStringNode, name);
        return this.isMemberReadableIntl(tsName, access);
    }

    private boolean isMemberReadableIntl(TruffleString tsName, DynamicObjectLibrary access) {
        Object value2 = Properties.getOrDefault(access, this.scope, tsName, null);
        return value2 != null && value2 != Dead.instance();
    }

    @ExportMessage
    boolean isMemberModifiable(String name, @Cached @Cached.Shared(value="fromJavaStringNode") TruffleString.FromJavaStringNode fromJavaStringNode, @CachedLibrary(value="this.scope") DynamicObjectLibrary access) {
        TruffleString tsName = Strings.fromJavaString(fromJavaStringNode, name);
        return this.isMemberReadableIntl(tsName, access) && !this.isConst(tsName, access);
    }

    @ExportMessage
    boolean isMemberInsertable(String name) {
        return false;
    }

    @ExportMessage
    Object readMember(String name, @Cached @Cached.Shared(value="fromJavaStringNode") TruffleString.FromJavaStringNode fromJavaStringNode, @CachedLibrary(value="this.scope") DynamicObjectLibrary access, @Cached ExportValueNode exportValueNode) throws UnknownIdentifierException {
        TruffleString tsName = Strings.fromJavaString(fromJavaStringNode, name);
        Object value2 = Properties.getOrDefault(access, this.scope, tsName, null);
        if (value2 == null || value2 == Dead.instance()) {
            throw UnknownIdentifierException.create(name);
        }
        return exportValueNode.execute(value2);
    }

    @ExportMessage
    void writeMember(String name, Object value2, @Cached @Cached.Shared(value="fromJavaStringNode") TruffleString.FromJavaStringNode fromJavaStringNode, @CachedLibrary(value="this.scope") DynamicObjectLibrary access) throws UnsupportedMessageException, UnknownIdentifierException {
        TruffleString tsName = Strings.fromJavaString(fromJavaStringNode, name);
        Object curValue = Properties.getOrDefault(access, this.scope, tsName, null);
        if (curValue == null || curValue == Dead.instance()) {
            throw UnknownIdentifierException.create(name);
        }
        if (this.isConst(tsName, access)) {
            throw UnsupportedMessageException.create();
        }
        Properties.putIfPresent(access, this.scope, tsName, value2);
    }
}

