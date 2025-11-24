
package com.oracle.truffle.js.runtime.interop;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.ImportStatic;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.InvalidArrayIndexException;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.interop.UnknownIdentifierException;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.interop.UnsupportedTypeException;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.library.ExportMessage;
import com.oracle.truffle.js.lang.JavaScriptLanguage;
import com.oracle.truffle.js.runtime.JSConfig;

@ExportLibrary(value=InteropLibrary.class)
@ImportStatic(value={JSConfig.class})
public final class TopScopeObject
implements TruffleObject {
    @CompilerDirectives.CompilationFinal(dimensions=1)
    private static final String[] NAMES = new String[]{"scriptEngineImport", "global", "global"};
    private final Object[] objects;
    private final int scopeIndex;

    public TopScopeObject(Object[] objects) {
        this.objects = objects;
        this.scopeIndex = objects[0] != null ? 0 : 1;
    }

    private TopScopeObject(Object[] objects, int index) {
        this.objects = objects;
        this.scopeIndex = index;
    }

    public static TopScopeObject empty() {
        return new TopScopeObject(new Object[0], 0);
    }

    @ExportMessage
    boolean hasLanguage() {
        return true;
    }

    @ExportMessage
    Class<? extends TruffleLanguage<?>> getLanguage() {
        return JavaScriptLanguage.class;
    }

    @ExportMessage
    boolean isScope() {
        return true;
    }

    @ExportMessage
    Object toDisplayString(boolean allowSideEffects) {
        return NAMES[this.scopeIndex];
    }

    @ExportMessage
    boolean hasScopeParent() {
        return this.scopeIndex < NAMES.length - 1;
    }

    @ExportMessage
    Object getScopeParent() throws UnsupportedMessageException {
        if (this.scopeIndex < NAMES.length - 1) {
            return new TopScopeObject(this.objects, this.scopeIndex + 1);
        }
        throw UnsupportedMessageException.create();
    }

    @ExportMessage
    boolean hasMembers() {
        return true;
    }

    @ExportMessage
    @CompilerDirectives.TruffleBoundary
    Object getMembers(boolean includeInternal, @Cached.Shared(value="interop") @CachedLibrary(limit="InteropLibraryLimit") InteropLibrary interop) throws UnsupportedMessageException {
        int length = NAMES.length;
        Object[] keys = new Object[length - this.scopeIndex];
        for (int i = this.scopeIndex; i < length; ++i) {
            keys[i - this.scopeIndex] = interop.getMembers(this.objects[i]);
        }
        return new MergedPropertyNames(keys);
    }

    @ExportMessage
    boolean isMemberReadable(String member, @Cached.Shared(value="interop") @CachedLibrary(limit="InteropLibraryLimit") InteropLibrary interop) {
        int length = NAMES.length;
        for (int i = this.scopeIndex; i < length; ++i) {
            if (!interop.isMemberReadable(this.objects[i], member)) continue;
            return true;
        }
        return false;
    }

    @ExportMessage
    Object readMember(String member, @Cached.Shared(value="interop") @CachedLibrary(limit="InteropLibraryLimit") InteropLibrary interop) throws UnknownIdentifierException, UnsupportedMessageException {
        int length = NAMES.length;
        for (int i = this.scopeIndex; i < length; ++i) {
            Object scope = this.objects[i];
            if (!interop.isMemberReadable(scope, member)) continue;
            return interop.readMember(scope, member);
        }
        throw UnknownIdentifierException.create(member);
    }

    @ExportMessage
    boolean isMemberModifiable(String member, @Cached.Shared(value="interop") @CachedLibrary(limit="InteropLibraryLimit") InteropLibrary interop) {
        int length = NAMES.length;
        for (int i = this.scopeIndex; i < length; ++i) {
            Object scope = this.objects[i];
            if (!interop.isMemberModifiable(scope, member)) continue;
            return true;
        }
        return false;
    }

    @ExportMessage
    boolean isMemberInsertable(String member, @Cached.Shared(value="interop") @CachedLibrary(limit="InteropLibraryLimit") InteropLibrary interop) {
        int length = NAMES.length;
        boolean wasInsertable = false;
        for (int i = this.scopeIndex; i < length; ++i) {
            Object scope = this.objects[i];
            if (interop.isMemberExisting(scope, member)) {
                return false;
            }
            if (!interop.isMemberInsertable(scope, member)) continue;
            wasInsertable = true;
        }
        return wasInsertable;
    }

    @ExportMessage
    boolean hasMemberReadSideEffects(String member, @Cached.Shared(value="interop") @CachedLibrary(limit="InteropLibraryLimit") InteropLibrary interop) {
        int length = NAMES.length;
        for (int i = this.scopeIndex; i < length; ++i) {
            Object scope = this.objects[i];
            if (!interop.isMemberReadable(scope, member)) continue;
            return interop.hasMemberReadSideEffects(scope, member);
        }
        return false;
    }

    @ExportMessage
    boolean hasMemberWriteSideEffects(String member, @Cached.Shared(value="interop") @CachedLibrary(limit="InteropLibraryLimit") InteropLibrary interop) {
        int length = NAMES.length;
        for (int i = this.scopeIndex; i < length; ++i) {
            Object scope = this.objects[i];
            if (!interop.isMemberWritable(scope, member)) continue;
            return interop.hasMemberWriteSideEffects(scope, member);
        }
        return false;
    }

    @ExportMessage
    void writeMember(String member, Object value2, @Cached.Shared(value="interop") @CachedLibrary(limit="InteropLibraryLimit") InteropLibrary interop) throws UnknownIdentifierException, UnsupportedMessageException, UnsupportedTypeException {
        int length = NAMES.length;
        Object firstInsertableScope = null;
        for (int i = this.scopeIndex; i < length; ++i) {
            Object scope = this.objects[i];
            if (interop.isMemberExisting(scope, member)) {
                if (interop.isMemberModifiable(scope, member)) {
                    interop.writeMember(scope, member, value2);
                    return;
                }
                throw UnsupportedMessageException.create();
            }
            if (!interop.isMemberInsertable(scope, member) || firstInsertableScope != null) continue;
            firstInsertableScope = scope;
        }
        if (firstInsertableScope != null) {
            interop.writeMember(firstInsertableScope, member, value2);
            return;
        }
        throw UnsupportedMessageException.create();
    }

    @ExportMessage
    boolean isMemberRemovable(String member, @Cached.Shared(value="interop") @CachedLibrary(limit="InteropLibraryLimit") InteropLibrary interop) {
        int length = NAMES.length;
        for (int i = this.scopeIndex; i < length; ++i) {
            Object scope = this.objects[i];
            if (interop.isMemberRemovable(scope, member)) {
                return true;
            }
            if (!interop.isMemberExisting(scope, member)) continue;
            return false;
        }
        return false;
    }

    @ExportMessage
    void removeMember(String member, @Cached.Shared(value="interop") @CachedLibrary(limit="InteropLibraryLimit") InteropLibrary interop) throws UnsupportedMessageException, UnknownIdentifierException {
        int length = NAMES.length;
        for (int i = this.scopeIndex; i < length; ++i) {
            Object scope = this.objects[i];
            if (interop.isMemberRemovable(scope, member)) {
                interop.removeMember(scope, member);
                return;
            }
            if (interop.isMemberExisting(scope, member)) break;
        }
        throw UnsupportedMessageException.create();
    }

    @ExportLibrary(value=InteropLibrary.class)
    @ImportStatic(value={JSConfig.class})
    static final class MergedPropertyNames
    implements TruffleObject {
        private final Object[] keys;
        private final long[] size;

        private MergedPropertyNames(Object[] keys) throws UnsupportedMessageException {
            this.keys = keys;
            this.size = new long[keys.length];
            long s = 0L;
            InteropLibrary interop = InteropLibrary.getUncached();
            for (int i = 0; i < keys.length; ++i) {
                this.size[i] = s += interop.getArraySize(keys[i]);
            }
        }

        @ExportMessage
        boolean hasArrayElements() {
            return true;
        }

        @ExportMessage
        long getArraySize() {
            return this.size[this.size.length - 1];
        }

        @ExportMessage
        boolean isArrayElementReadable(long index, @Cached.Shared(value="interop") @CachedLibrary(limit="InteropLibraryLimit") InteropLibrary interop) {
            if (index >= 0L) {
                for (int i = 0; i < this.keys.length; ++i) {
                    if (index >= this.size[i]) continue;
                    long start2 = i == 0 ? 0L : this.size[i - 1];
                    return interop.isArrayElementReadable(this.keys[i], index - start2);
                }
            }
            return false;
        }

        @ExportMessage
        Object readArrayElement(long index, @Cached.Shared(value="interop") @CachedLibrary(limit="InteropLibraryLimit") InteropLibrary interop) throws InvalidArrayIndexException, UnsupportedMessageException {
            if (index >= 0L) {
                for (int i = 0; i < this.keys.length; ++i) {
                    if (index >= this.size[i]) continue;
                    long start2 = i == 0 ? 0L : this.size[i - 1];
                    return interop.readArrayElement(this.keys[i], index - start2);
                }
            }
            throw InvalidArrayIndexException.create(index);
        }
    }
}

