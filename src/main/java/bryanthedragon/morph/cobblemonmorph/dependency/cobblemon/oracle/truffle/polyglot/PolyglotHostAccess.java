package com.oracle.truffle.polyglot;

import com.oracle.truffle.api.CompilerDirectives;
import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;
import org.graalvm.polyglot.PolyglotException;
import org.graalvm.polyglot.Value;
import org.graalvm.polyglot.impl.AbstractPolyglotImpl;

final class PolyglotHostAccess extends AbstractPolyglotImpl.AbstractHostAccess {
   final AbstractPolyglotImpl polyglot;

   protected PolyglotHostAccess(AbstractPolyglotImpl polyglot) {
      super(polyglot);
      this.polyglot = polyglot;
   }

   @Override
   public Object toGuestValue(Object polyglotContext, Object hostValue) {
      PolyglotContextImpl internalContext = (PolyglotContextImpl)polyglotContext;
      return toGuestValue(internalContext, hostValue);
   }

   static Object toGuestValue(PolyglotContextImpl context, Object hostValue) {
      if (hostValue instanceof Value) {
         Value receiverValue = (Value)hostValue;
         PolyglotLanguageContext languageContext = (PolyglotLanguageContext)context.getAPIAccess().getContext(receiverValue);
         PolyglotContextImpl valueContext = languageContext != null ? languageContext.context : null;
         Object valueReceiver = context.getAPIAccess().getReceiver(receiverValue);
         if (valueContext != context) {
            valueReceiver = context.migrateValue(valueReceiver, valueContext);
         }

         return valueReceiver;
      } else {
         return PolyglotWrapper.isInstance(hostValue) ? context.migrateHostWrapper(PolyglotWrapper.asInstance(hostValue)) : hostValue;
      }
   }

   @Override
   public <T> List<T> toList(Object internalContext, Object guestValue, boolean implementFunction, Class<T> elementClass, Type elementType) {
      PolyglotContextImpl context = (PolyglotContextImpl)internalContext;
      return PolyglotList.create(context.getHostContext(), guestValue, implementFunction, elementClass, elementType);
   }

   @Override
   public <K, V> Map<K, V> toMap(
      Object internalContext, Object foreignObject, boolean implementsFunction, Class<K> keyClass, Type keyType, Class<V> valueClass, Type valueType
   ) {
      PolyglotContextImpl context = (PolyglotContextImpl)internalContext;
      return PolyglotMap.create(context.getHostContext(), foreignObject, implementsFunction, keyClass, keyType, valueClass, valueType);
   }

   @Override
   public <K, V> Entry<K, V> toMapEntry(
      Object internalContext, Object foreignObject, boolean implementsFunction, Class<K> keyClass, Type keyType, Class<V> valueClass, Type valueType
   ) {
      PolyglotContextImpl context = (PolyglotContextImpl)internalContext;
      return PolyglotMapEntry.create(context.getHostContext(), foreignObject, implementsFunction, keyClass, keyType, valueClass, valueType);
   }

   @Override
   public <T> Function<?, ?> toFunction(Object internalContext, Object function, Class<?> returnClass, Type returnType, Class<?> paramClass, Type paramType) {
      PolyglotContextImpl context = (PolyglotContextImpl)internalContext;
      return PolyglotFunction.create(context.getHostContext(), function, returnClass, returnType, paramClass, paramType);
   }

   @Override
   public Object toObjectProxy(Object internalContext, Class<?> clazz, Object obj) throws IllegalArgumentException {
      PolyglotContextImpl context = (PolyglotContextImpl)internalContext;
      return PolyglotObjectProxyHandler.newProxyInstance(clazz, obj, context.getHostContext());
   }

   @Override
   public <T> T toFunctionProxy(Object internalContext, Class<T> functionalType, Object function) {
      PolyglotContextImpl context = (PolyglotContextImpl)internalContext;
      return PolyglotFunctionProxyHandler.create(functionalType, function, context.getHostContext());
   }

   @Override
   public <T> Iterable<T> toIterable(Object internalContext, Object iterable, boolean implementFunction, Class<T> elementClass, Type elementType) {
      PolyglotContextImpl context = (PolyglotContextImpl)internalContext;
      return PolyglotIterable.create(context.getHostContext(), iterable, implementFunction, elementClass, elementType);
   }

   @Override
   public <T> Iterator<T> toIterator(Object internalContext, Object iterable, boolean implementFunction, Class<T> elementClass, Type elementType) {
      PolyglotContextImpl context = (PolyglotContextImpl)internalContext;
      return PolyglotIterator.create(context.getHostContext(), iterable, implementFunction, elementClass, elementType);
   }

   @Override
   public PolyglotException toPolyglotException(Object internalContext, Throwable e) {
      PolyglotContextImpl context = (PolyglotContextImpl)internalContext;
      return PolyglotImpl.guestToHostException(context.getHostContext(), e, true);
   }

   @Override
   public Value toValue(Object internalContext, Object receiver) {
      PolyglotContextImpl context = (PolyglotContextImpl)internalContext;
      return context.getHostContext().asValue(receiver);
   }

   @Override
   public String getValueInfo(Object internalContext, Object value) {
      PolyglotContextImpl context = (PolyglotContextImpl)internalContext;
      return PolyglotValueDispatch.getValueInfo(context, value);
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public Value[] toValues(Object internalContext, Object[] values, int startIndex) {
      return ((PolyglotContextImpl)internalContext).getHostContext().toHostValues(values, startIndex);
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public Value[] toValues(Object internalContext, Object[] values) {
      return ((PolyglotContextImpl)internalContext).getHostContext().toHostValues(values);
   }

   @Override
   public boolean isEngineException(RuntimeException e) {
      return e instanceof PolyglotEngineException;
   }

   @Override
   public RuntimeException toEngineException(RuntimeException e) {
      return new PolyglotEngineException(e);
   }

   @Override
   public RuntimeException unboxEngineException(RuntimeException e) {
      return ((PolyglotEngineException)e).e;
   }

   @Override
   public void rethrowPolyglotException(Object internalContext, PolyglotException e) {
      PolyglotContextImpl context = (PolyglotContextImpl)internalContext;
      AbstractPolyglotImpl.APIAccess api = this.polyglot.getAPIAccess();
      PolyglotExceptionImpl exceptionImpl = (PolyglotExceptionImpl)api.getReceiver(e);
      if (exceptionImpl.context == context || exceptionImpl.context == null || exceptionImpl.isHostException()) {
         Throwable original = ((PolyglotExceptionImpl)api.getReceiver(e)).exception;
         if (original instanceof RuntimeException) {
            throw (RuntimeException)original;
         }

         if (original instanceof Error) {
            throw (Error)original;
         }
      }
   }
}
