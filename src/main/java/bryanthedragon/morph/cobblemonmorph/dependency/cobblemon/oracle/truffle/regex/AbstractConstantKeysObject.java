
package com.oracle.truffle.regex;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.ImportStatic;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.UnknownIdentifierException;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.library.ExportMessage;
import com.oracle.truffle.api.profiles.ValueProfile;
import com.oracle.truffle.regex.AbstractRegexObject;
import com.oracle.truffle.regex.util.TruffleReadOnlyKeysArray;

@ExportLibrary(value=InteropLibrary.class)
public abstract class AbstractConstantKeysObject
extends AbstractRegexObject {
    public abstract TruffleReadOnlyKeysArray getKeys();

    public abstract boolean isMemberReadableImpl(String var1);

    public abstract Object readMemberImpl(String var1) throws UnknownIdentifierException;

    @ExportMessage
    public boolean hasMembers() {
        return true;
    }

    @ExportMessage
    public Object getMembers(boolean includeInternal) {
        return this.getKeys();
    }

    @ExportMessage
    public static abstract class ReadMember {
        @Specialization(guards={"symbol == cachedSymbol"}, limit="8")
        public static Object readIdentity(AbstractConstantKeysObject receiver, String symbol, @Cached(value="symbol") String cachedSymbol, @Cached(value="createClassProfile()") @Cached.Exclusive ValueProfile classProfile) throws UnknownIdentifierException {
            return ReadMember.read(receiver, cachedSymbol, classProfile);
        }

        @Specialization(guards={"symbol.equals(cachedSymbol)"}, limit="8", replaces={"readIdentity"})
        public static Object readEquals(AbstractConstantKeysObject receiver, String symbol, @Cached(value="symbol") String cachedSymbol, @Cached(value="createClassProfile()") @Cached.Exclusive ValueProfile classProfile) throws UnknownIdentifierException {
            return ReadMember.read(receiver, cachedSymbol, classProfile);
        }

        @Specialization(replaces={"readEquals"})
        public static Object read(AbstractConstantKeysObject receiver, String symbol, @Cached(value="createClassProfile()") @Cached.Shared(value="classProfile") ValueProfile classProfile) throws UnknownIdentifierException {
            return classProfile.profile(receiver).readMemberImpl(symbol);
        }
    }

    @ExportMessage
    @ImportStatic(value={CompilerDirectives.class})
    public static abstract class IsMemberReadable {
        @Specialization(guards={"symbol == cachedSymbol", "isExact(receiver, cachedClass)", "result"}, limit="8")
        public static boolean cacheIdentity(AbstractConstantKeysObject receiver, String symbol, @Cached(value="symbol") String cachedSymbol, @Cached(value="receiver.getClass()") Class<?> cachedClass, @Cached(value="receiver.isMemberReadableImpl(cachedSymbol)") boolean result) {
            return result;
        }

        @Specialization(guards={"symbol.equals(cachedSymbol)", "isExact(receiver, cachedClass)", "result"}, limit="8", replaces={"cacheIdentity"})
        public static boolean cacheEquals(AbstractConstantKeysObject receiver, String symbol, @Cached(value="symbol") String cachedSymbol, @Cached(value="receiver.getClass()") Class<?> cachedClass, @Cached(value="receiver.isMemberReadableImpl(cachedSymbol)") boolean result) {
            return result;
        }

        @Specialization(replaces={"cacheEquals"})
        public static boolean isReadable(AbstractConstantKeysObject receiver, String symbol, @Cached(value="createClassProfile()") @Cached.Shared(value="classProfile") ValueProfile classProfile) {
            return classProfile.profile(receiver).isMemberReadableImpl(symbol);
        }
    }
}

