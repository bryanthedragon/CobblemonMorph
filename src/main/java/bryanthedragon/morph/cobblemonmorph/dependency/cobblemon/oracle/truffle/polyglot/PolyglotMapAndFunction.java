package com.oracle.truffle.polyglot;

import java.lang.reflect.Type;
import java.util.function.Function;

class PolyglotMapAndFunction<K, V> extends PolyglotMap<K, V> implements Function<Object[], Object> {
   PolyglotMapAndFunction(PolyglotLanguageContext languageContext, Object obj, Class<K> keyClass, Type keyType, Class<V> valueClass, Type valueType) {
      super(languageContext, obj, keyClass, keyType, valueClass, valueType);
   }

   public final Object apply(Object[] arguments) {
      return this.cache.apply.call(this.languageContext, this.guestObject, arguments);
   }
}
