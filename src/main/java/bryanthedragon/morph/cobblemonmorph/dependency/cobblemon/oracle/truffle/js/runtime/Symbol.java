
package com.oracle.truffle.js.runtime;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.dsl.Fallback;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.library.ExportMessage;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.api.utilities.TriState;
import com.oracle.truffle.js.lang.JavaScriptLanguage;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.interop.JSMetaType;
import com.oracle.truffle.js.runtime.objects.Undefined;

@ExportLibrary(value=InteropLibrary.class)
public final class Symbol
implements TruffleObject {
    public static final Symbol SYMBOL_HAS_INSTANCE = Symbol.create(Strings.constant("Symbol.hasInstance"));
    public static final Symbol SYMBOL_IS_CONCAT_SPREADABLE = Symbol.create(Strings.constant("Symbol.isConcatSpreadable"));
    public static final Symbol SYMBOL_ITERATOR = Symbol.create(Strings.constant("Symbol.iterator"));
    public static final Symbol SYMBOL_ASYNC_ITERATOR = Symbol.create(Strings.constant("Symbol.asyncIterator"));
    public static final Symbol SYMBOL_MATCH = Symbol.create(Strings.constant("Symbol.match"));
    public static final Symbol SYMBOL_MATCH_ALL = Symbol.create(Strings.constant("Symbol.matchAll"));
    public static final Symbol SYMBOL_REPLACE = Symbol.create(Strings.constant("Symbol.replace"));
    public static final Symbol SYMBOL_SEARCH = Symbol.create(Strings.constant("Symbol.search"));
    public static final Symbol SYMBOL_SPECIES = Symbol.create(Strings.constant("Symbol.species"));
    public static final Symbol SYMBOL_SPLIT = Symbol.create(Strings.constant("Symbol.split"));
    public static final Symbol SYMBOL_TO_PRIMITIVE = Symbol.create(Strings.constant("Symbol.toPrimitive"));
    public static final Symbol SYMBOL_TO_STRING_TAG = Symbol.create(Strings.constant("Symbol.toStringTag"));
    public static final Symbol SYMBOL_UNSCOPABLES = Symbol.create(Strings.constant("Symbol.unscopables"));
    private final TruffleString description;

    private Symbol(TruffleString description) {
        this.description = description;
    }

    public static Symbol create(TruffleString description) {
        return new Symbol(description);
    }

    public Object getDescription() {
        return this.description == null ? Undefined.instance : this.description;
    }

    public TruffleString getName() {
        return this.description == null ? Strings.EMPTY_STRING : this.description;
    }

    @CompilerDirectives.TruffleBoundary
    public String toString() {
        return Strings.toJavaString(this.toTString());
    }

    public TruffleString toTString() {
        return Strings.concatAll(Strings.SYMBOL_PAREN_OPEN, this.getName(), Strings.PAREN_CLOSE);
    }

    @CompilerDirectives.TruffleBoundary
    public TruffleString toFunctionNameString() {
        return this.description == null ? Strings.EMPTY_STRING : Strings.concatAll(Strings.BRACKET_OPEN, this.description, Strings.BRACKET_CLOSE);
    }

    public boolean equals(Object obj) {
        return this == obj;
    }

    public int hashCode() {
        return super.hashCode();
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
    Object toDisplayString(boolean allowSideEffects) {
        return this.toString();
    }

    @ExportMessage
    boolean hasMetaObject() {
        return true;
    }

    @ExportMessage
    Object getMetaObject() {
        return JSMetaType.JS_SYMBOL;
    }

    @CompilerDirectives.TruffleBoundary
    @ExportMessage
    int identityHashCode() {
        return super.hashCode();
    }

    @ExportMessage
    static final class IsIdenticalOrUndefined {
        IsIdenticalOrUndefined() {
        }

        @Specialization
        static TriState doHostObject(Symbol receiver, Symbol other) {
            return TriState.valueOf(receiver == other);
        }

        @Fallback
        static TriState doOther(Symbol receiver, Object other) {
            return TriState.UNDEFINED;
        }
    }
}

