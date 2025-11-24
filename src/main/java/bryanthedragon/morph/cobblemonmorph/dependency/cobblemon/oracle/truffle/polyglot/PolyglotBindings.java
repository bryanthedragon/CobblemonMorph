
package com.oracle.truffle.polyglot;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.InvalidArrayIndexException;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.interop.UnknownIdentifierException;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.library.ExportMessage;
import com.oracle.truffle.api.utilities.TriState;
import com.oracle.truffle.polyglot.PolyglotContextImpl;
import com.oracle.truffle.polyglot.PolyglotLanguageContext;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import org.graalvm.polyglot.Value;

@ExportLibrary(value=InteropLibrary.class)
final class PolyglotBindings
implements TruffleObject {
    private final PolyglotContextImpl context;
    private final PolyglotLanguageContext languageContext;
    private volatile Map<String, Value> bindings;

    PolyglotBindings(PolyglotContextImpl context) {
        this(context, null);
    }

    PolyglotBindings(PolyglotLanguageContext languageContext) {
        this(languageContext.context, languageContext);
    }

    private PolyglotBindings(PolyglotContextImpl context, PolyglotLanguageContext languageContext) {
        Objects.requireNonNull(context);
        this.context = context;
        this.languageContext = languageContext;
    }

    public Map<String, Value> getBindings() {
        Map<String, Value> localBindings = this.bindings;
        if (localBindings == null) {
            this.bindings = localBindings = this.context.getPolyglotGuestBindings();
        }
        return localBindings;
    }

    @ExportMessage
    boolean hasMembers() {
        return true;
    }

    @ExportMessage
    @CompilerDirectives.TruffleBoundary
    Object readMember(String member) throws UnknownIdentifierException {
        Value value2 = this.getBindings().get(member);
        if (value2 == null) {
            throw UnknownIdentifierException.create(member);
        }
        if (this.languageContext != null) {
            return this.context.toGuestValue(null, value2, false);
        }
        return this.context.getAPIAccess().getReceiver(value2);
    }

    @ExportMessage
    @CompilerDirectives.TruffleBoundary
    void writeMember(String member, Object value2) {
        Value v = this.languageContext != null ? this.languageContext.asValue(value2) : this.context.asValue(value2);
        this.getBindings().put(member, v);
    }

    @ExportMessage
    @CompilerDirectives.TruffleBoundary
    void removeMember(String member) throws UnknownIdentifierException {
        Value ret = this.getBindings().remove(member);
        if (ret == null) {
            throw UnknownIdentifierException.create(member);
        }
    }

    @ExportMessage
    @CompilerDirectives.TruffleBoundary
    Object getMembers(boolean includeInternal) {
        return new Members(this.getBindings().keySet());
    }

    @ExportMessage.Repeat(value={@ExportMessage(name="isMemberReadable"), @ExportMessage(name="isMemberModifiable"), @ExportMessage(name="isMemberRemovable")})
    @CompilerDirectives.TruffleBoundary
    boolean isMemberExisting(String member) {
        return this.getBindings().containsKey(member);
    }

    @ExportMessage
    boolean isMemberInsertable(String member) {
        return !this.isMemberExisting(member);
    }

    @ExportMessage
    TriState isIdenticalOrUndefined(Object other) {
        if (this == other) {
            return TriState.TRUE;
        }
        return TriState.UNDEFINED;
    }

    @ExportMessage
    @CompilerDirectives.TruffleBoundary
    int identityHashCode() {
        return System.identityHashCode(this);
    }

    @ExportLibrary(value=InteropLibrary.class)
    static final class Members
    implements TruffleObject {
        final String[] names;

        Members(Set<String> names) {
            this.names = names.toArray(new String[0]);
        }

        @ExportMessage
        boolean hasArrayElements() {
            return true;
        }

        @ExportMessage
        long getArraySize() {
            return this.names.length;
        }

        @ExportMessage
        Object readArrayElement(long index) throws InvalidArrayIndexException {
            if (!this.isArrayElementReadable(index)) {
                throw InvalidArrayIndexException.create(index);
            }
            return this.names[(int)index];
        }

        @ExportMessage
        boolean isArrayElementReadable(long index) {
            return index >= 0L && index < (long)this.names.length;
        }
    }
}

