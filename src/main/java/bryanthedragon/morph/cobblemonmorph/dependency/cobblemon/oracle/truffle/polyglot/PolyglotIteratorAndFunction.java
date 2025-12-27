package com.oracle.truffle.polyglot;

import java.lang.reflect.Type;
import java.util.function.Function;

final class PolyglotIteratorAndFunction<T> extends PolyglotIterator<T> implements Function<Object, Object> {
   PolyglotIteratorAndFunction(Class<T> elementClass, Type elementType, Object iterable, PolyglotLanguageContext languageContext) {
      super(elementClass, elementType, iterable, languageContext);
   }

   @Override
   public Object apply(Object t) {
      return this.cache.apply.call(this.languageContext, this.guestObject, t);
   }
}
