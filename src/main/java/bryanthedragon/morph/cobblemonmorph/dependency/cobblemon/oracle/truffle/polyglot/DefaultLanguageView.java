
package com.oracle.truffle.polyglot;

import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.library.ExportMessage;

@ExportLibrary(value=InteropLibrary.class, delegateTo="delegate")
final class DefaultLanguageView<C>
implements TruffleObject {
    private final TruffleLanguage<C> language;
    protected final Object delegate;

    DefaultLanguageView(TruffleLanguage<C> language, Object delegate) {
        this.language = language;
        this.delegate = delegate;
    }

    @ExportMessage
    boolean hasLanguage() {
        return true;
    }

    @ExportMessage
    Object toDisplayString(boolean allowSideEffects, @CachedLibrary(value="this.delegate") InteropLibrary delegateLibrary) {
        return delegateLibrary.toDisplayString(this.delegate, allowSideEffects);
    }

    @ExportMessage
    Class<? extends TruffleLanguage<?>> getLanguage() {
        return this.language.getClass();
    }
}

