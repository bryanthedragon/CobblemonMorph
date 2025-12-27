package com.oracle.truffle.polyglot;

import java.lang.reflect.Type;
import java.util.function.Function;

public class PolyglotMapEntryAndFunction<K, V> extends PolyglotMapEntry<K, V> implements Function<Object, Object> {
   PolyglotMapEntryAndFunction(PolyglotLanguageContext languageContext, Object obj, Class<K> keyClass, Type keyType, Class<V> valueClass, Type valueType) {
      super(languageContext, obj, keyClass, keyType, valueClass, valueType);
   }

   @Override
   public Object apply(Object t) {
      return this.cache.apply.call(this.languageContext, this.guestObject, t);
   }
}
