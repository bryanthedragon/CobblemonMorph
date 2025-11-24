
package com.oracle.truffle.polyglot;

import com.oracle.truffle.polyglot.PolyglotIterable;
import com.oracle.truffle.polyglot.PolyglotLanguageContext;
import java.lang.reflect.Type;
import java.util.function.Function;

final class PolyglotIterableAndFunction<T>
extends PolyglotIterable<T>
implements Function<Object, Object> {
    PolyglotIterableAndFunction(Class<T> elementClass, Type elementType, Object iterable, PolyglotLanguageContext languageContext) {
        super(elementClass, elementType, iterable, languageContext);
    }

    @Override
    public Object apply(Object t) {
        return this.cache.apply.call(this.languageContext, this.guestObject, t);
    }
}

