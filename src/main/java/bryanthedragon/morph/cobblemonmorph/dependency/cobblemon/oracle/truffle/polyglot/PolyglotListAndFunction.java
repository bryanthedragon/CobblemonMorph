package com.oracle.truffle.polyglot;

import java.lang.reflect.Type;
import java.util.function.Function;

class PolyglotListAndFunction<T> extends PolyglotList<T> implements Function<Object, Object> {
   PolyglotListAndFunction(Class<T> elementClass, Type elementType, Object array, PolyglotLanguageContext languageContext) {
      super(elementClass, elementType, array, languageContext);
   }

   @Override
   public Object apply(Object t) {
      return this.cache.apply.call(this.languageContext, this.guestObject, t);
   }
}
