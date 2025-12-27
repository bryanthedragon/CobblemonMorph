package com.oracle.truffle.polyglot;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.profiles.BranchProfile;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.Objects;

class PolyglotIterable<T> implements Iterable<T>, PolyglotWrapper {
   final Object guestObject;
   final PolyglotLanguageContext languageContext;
   final PolyglotIterable.Cache cache;

   PolyglotIterable(Class<T> elementClass, Type elementType, Object iterable, PolyglotLanguageContext languageContext) {
      this.guestObject = iterable;
      this.languageContext = languageContext;
      this.cache = PolyglotIterable.Cache.lookup(languageContext, iterable.getClass(), elementClass, elementType);
   }

   @Override
   public Object getGuestObject() {
      return this.guestObject;
   }

   @Override
   public PolyglotContextImpl getContext() {
      return this.languageContext.context;
   }

   @Override
   public PolyglotLanguageContext getLanguageContext() {
      return this.languageContext;
   }

   @Override
   public Iterator<T> iterator() {
      return (Iterator<T>)this.cache.getIterator.call(this.languageContext, this.guestObject);
   }

   @Override
   public String toString() {
      return PolyglotWrapper.toString(this);
   }

   @Override
   public int hashCode() {
      return PolyglotWrapper.hashCode(this.languageContext, this.guestObject);
   }

   @Override
   public boolean equals(Object o) {
      return o instanceof PolyglotIterable ? PolyglotWrapper.equals(this.languageContext, this.guestObject, ((PolyglotIterable)o).guestObject) : false;
   }

   @CompilerDirectives.TruffleBoundary
   static <T> PolyglotIterable<T> create(
      PolyglotLanguageContext languageContext, Object iterable, boolean implementFunction, Class<T> elementClass, Type elementType
   ) {
      return (PolyglotIterable<T>)(implementFunction
         ? new PolyglotIterableAndFunction<>(elementClass, elementType, iterable, languageContext)
         : new PolyglotIterable<>(elementClass, elementType, iterable, languageContext));
   }

   static final class Cache {
      final PolyglotLanguageInstance languageInstance;
      final Class<?> receiverClass;
      final Class<?> valueClass;
      final Type valueType;
      final CallTarget getIterator;
      final CallTarget apply;
      final Type iteratorType;

      private Cache(PolyglotLanguageInstance languageInstance, Class<?> receiverClass, Class<?> valueClass, Type valueType) {
         this.languageInstance = languageInstance;
         this.receiverClass = receiverClass;
         this.valueClass = valueClass;
         this.valueType = valueType;
         this.getIterator = PolyglotIterableFactory.CacheFactory.GetIteratorNodeGen.create(this).getCallTarget();
         this.apply = new PolyglotIterable.Cache.Apply(this).getCallTarget();
         this.iteratorType = new PolyglotIterable.Cache.ParameterizedIteratorType(valueType);
      }

      static PolyglotIterable.Cache lookup(PolyglotLanguageContext languageContext, Class<?> receiverClass, Class<?> valueClass, Type valueType) {
         PolyglotIterable.Cache.Key cacheKey = new PolyglotIterable.Cache.Key(receiverClass, valueClass, valueType);
         PolyglotIterable.Cache cache = HostToGuestRootNode.lookupHostCodeCache(languageContext, cacheKey, PolyglotIterable.Cache.class);
         if (cache == null) {
            cache = HostToGuestRootNode.installHostCodeCache(
               languageContext,
               cacheKey,
               new PolyglotIterable.Cache(languageContext.getLanguageInstance(), receiverClass, valueClass, valueType),
               PolyglotIterable.Cache.class
            );
         }

         assert cache.receiverClass == receiverClass;

         assert cache.valueClass == valueClass;

         assert cache.valueType == valueType;

         return cache;
      }

      private static class Apply extends PolyglotIterable.Cache.PolyglotIterableNode {
         @Node.Child
         private PolyglotExecuteNode apply = PolyglotExecuteNodeGen.create();

         Apply(PolyglotIterable.Cache cache) {
            super(cache);
         }

         @Override
         protected String getOperationName() {
            return "apply";
         }

         @Override
         protected Object executeImpl(PolyglotLanguageContext languageContext, Object receiver, Object[] args) {
            return this.apply.execute(languageContext, receiver, args[2]);
         }
      }

      abstract static class GetIteratorNode extends PolyglotIterable.Cache.PolyglotIterableNode {
         GetIteratorNode(PolyglotIterable.Cache cache) {
            super(cache);
         }

         @Override
         protected String getOperationName() {
            return "iterator";
         }

         @Specialization(limit = "LIMIT")
         Object doCached(
            PolyglotLanguageContext languageContext,
            Object receiver,
            Object[] args,
            @CachedLibrary("receiver") InteropLibrary iterables,
            @Cached PolyglotToHostNode toHost,
            @Cached BranchProfile error
         ) {
            try {
               return toHost.execute(languageContext, iterables.getIterator(receiver), Iterator.class, this.cache.iteratorType);
            } catch (UnsupportedMessageException var8) {
               error.enter();
               throw PolyglotInteropErrors.iterableUnsupported(languageContext, receiver, this.cache.valueType, "iterator()");
            }
         }
      }

      private static final class Key {
         private final Class<?> receiverClass;
         private final Class<?> valueClass;
         private final Type valueType;

         Key(Class<?> receiverClass, Class<?> valueClass, Type valueType) {
            this.receiverClass = Objects.requireNonNull(receiverClass);
            this.valueClass = Objects.requireNonNull(valueClass);
            this.valueType = valueType;
         }

         @Override
         public int hashCode() {
            int res = this.receiverClass.hashCode();
            res = res * 31 + this.valueClass.hashCode();
            return res * 31 + (this.valueType == null ? 0 : this.valueType.hashCode());
         }

         @Override
         public boolean equals(Object obj) {
            if (this == obj) {
               return true;
            } else if (obj != null && this.getClass() == obj.getClass()) {
               PolyglotIterable.Cache.Key other = (PolyglotIterable.Cache.Key)obj;
               return this.receiverClass == other.receiverClass && this.valueClass == other.valueClass && Objects.equals(this.valueType, other.valueType);
            } else {
               return false;
            }
         }
      }

      private static final class ParameterizedIteratorType implements ParameterizedType {
         private final Type valueType;

         ParameterizedIteratorType(Type valueType) {
            this.valueType = valueType;
         }

         @Override
         public Type[] getActualTypeArguments() {
            return new Type[]{this.valueType};
         }

         @Override
         public Type getRawType() {
            return Iterator.class;
         }

         @Override
         public Type getOwnerType() {
            return null;
         }
      }

      abstract static class PolyglotIterableNode extends HostToGuestRootNode {
         static final int LIMIT = 5;
         final PolyglotIterable.Cache cache;

         PolyglotIterableNode(PolyglotIterable.Cache cache) {
            super(cache.languageInstance);
            this.cache = cache;
         }

         @Override
         protected final Class<? extends TruffleObject> getReceiverType() {
            return (Class<? extends TruffleObject>)this.cache.receiverClass;
         }

         @Override
         public final String getName() {
            return "PolyglotIterable<" + this.cache.receiverClass + ", " + this.cache.valueType + ">." + this.getOperationName();
         }

         protected abstract String getOperationName();
      }
   }
}
