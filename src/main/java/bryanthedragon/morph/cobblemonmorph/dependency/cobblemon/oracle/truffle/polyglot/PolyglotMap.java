package com.oracle.truffle.polyglot;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.interop.InteropException;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.InvalidArrayIndexException;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.interop.UnknownIdentifierException;
import com.oracle.truffle.api.interop.UnknownKeyException;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.interop.UnsupportedTypeException;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.profiles.BranchProfile;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import java.util.Map.Entry;

class PolyglotMap<K, V> extends AbstractMap<K, V> implements PolyglotWrapper {
   final PolyglotLanguageContext languageContext;
   final Object guestObject;
   final PolyglotMap.Cache cache;

   PolyglotMap(PolyglotLanguageContext languageContext, Object obj, Class<K> keyClass, Type keyType, Class<V> valueClass, Type valueType) {
      this.guestObject = obj;
      this.languageContext = languageContext;
      this.cache = PolyglotMap.Cache.lookup(languageContext, obj.getClass(), keyClass, keyType, valueClass, valueType);
   }

   static <K, V> Map<K, V> create(
      PolyglotLanguageContext languageContext,
      Object foreignObject,
      boolean implementsFunction,
      Class<K> keyClass,
      Type keyType,
      Class<V> valueClass,
      Type valueType
   ) {
      return (Map<K, V>)(implementsFunction
         ? new PolyglotMapAndFunction<>(languageContext, foreignObject, keyClass, keyType, valueClass, valueType)
         : new PolyglotMap<>(languageContext, foreignObject, keyClass, keyType, valueClass, valueType));
   }

   @Override
   public PolyglotLanguageContext getLanguageContext() {
      return this.languageContext;
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
   public boolean containsKey(Object key) {
      return (Boolean)this.cache.containsKey.call(this.languageContext, this.guestObject, key);
   }

   @Override
   public Set<Entry<K, V>> entrySet() {
      return (Set<Entry<K, V>>)this.cache.entrySet.call(this.languageContext, this.guestObject, this);
   }

   @Override
   public V get(Object key) {
      return (V)this.cache.get.call(this.languageContext, this.guestObject, key);
   }

   @Override
   public V put(K key, V value) {
      V prev = this.get(key);
      this.cache.put.call(this.languageContext, this.guestObject, key, value);
      return prev;
   }

   @Override
   public V remove(Object key) {
      V prev = this.get(key);
      this.cache.remove.call(this.languageContext, this.guestObject, key);
      return prev;
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
      return o instanceof PolyglotMap ? PolyglotWrapper.equals(this.languageContext, this.guestObject, ((PolyglotMap)o).guestObject) : false;
   }

   @CompilerDirectives.TruffleBoundary
   private static int intValue(Object key) {
      return ((Number)key).intValue();
   }

   private abstract class AbstractEntrySet extends AbstractSet<Entry<K, V>> {
      @Override
      public boolean contains(Object o) {
         return PolyglotMap.this.containsKey(o);
      }

      @Override
      public boolean remove(Object o) {
         if (o instanceof Entry) {
            Entry<Object, Object> e = (Entry<Object, Object>)o;
            return (Boolean)PolyglotMap.this.cache.removeBoolean.call(PolyglotMap.this.languageContext, PolyglotMap.this.guestObject, e.getKey(), e.getValue());
         } else {
            return false;
         }
      }
   }

   static final class Cache {
      final PolyglotLanguageInstance languageInstance;
      final Class<?> receiverClass;
      final Class<?> keyClass;
      final Type keyType;
      final Class<?> valueClass;
      final Type valueType;
      final boolean memberKey;
      final boolean numberKey;
      final CallTarget entrySet;
      final CallTarget get;
      final CallTarget put;
      final CallTarget remove;
      final CallTarget removeBoolean;
      final CallTarget containsKey;
      final CallTarget hashEntriesIterator;
      final CallTarget hashSize;
      final CallTarget apply;

      Cache(PolyglotLanguageInstance languageInstance, Class<?> receiverClass, Class<?> keyClass, Type keyType, Class<?> valueClass, Type valueType) {
         this.languageInstance = languageInstance;
         this.receiverClass = receiverClass;
         this.keyClass = keyClass;
         this.keyType = keyType;
         this.valueClass = valueClass;
         this.valueType = valueType;
         this.memberKey = keyClass == Object.class || keyClass == String.class || keyClass == CharSequence.class;
         this.numberKey = keyClass == Object.class
            || keyClass == Number.class
            || keyClass == Integer.class
            || keyClass == Long.class
            || keyClass == Short.class
            || keyClass == Byte.class;
         this.get = PolyglotMapFactory.CacheFactory.GetNodeGen.create(this).getCallTarget();
         this.containsKey = PolyglotMapFactory.CacheFactory.ContainsKeyNodeGen.create(this).getCallTarget();
         this.entrySet = PolyglotMapFactory.CacheFactory.EntrySetNodeGen.create(this).getCallTarget();
         this.put = PolyglotMapFactory.CacheFactory.PutNodeGen.create(this).getCallTarget();
         this.remove = PolyglotMapFactory.CacheFactory.RemoveNodeGen.create(this).getCallTarget();
         this.removeBoolean = PolyglotMapFactory.CacheFactory.RemoveBooleanNodeGen.create(this).getCallTarget();
         this.hashEntriesIterator = PolyglotMapFactory.CacheFactory.HashEntriesIteratorNodeGen.create(this).getCallTarget();
         this.hashSize = PolyglotMapFactory.CacheFactory.HashSizeNodeGen.create(this).getCallTarget();
         this.apply = new PolyglotMap.Cache.Apply(this).getCallTarget();
      }

      static PolyglotMap.Cache lookup(
         PolyglotLanguageContext languageContext, Class<?> receiverClass, Class<?> keyClass, Type keyType, Class<?> valueClass, Type valueType
      ) {
         PolyglotMap.Cache.Key cacheKey = new PolyglotMap.Cache.Key(receiverClass, keyClass, keyType, valueClass, valueType);
         PolyglotMap.Cache cache = HostToGuestRootNode.lookupHostCodeCache(languageContext, cacheKey, PolyglotMap.Cache.class);
         if (cache == null) {
            cache = HostToGuestRootNode.installHostCodeCache(
               languageContext,
               cacheKey,
               new PolyglotMap.Cache(languageContext.getLanguageInstance(), receiverClass, keyClass, keyType, valueClass, valueType),
               PolyglotMap.Cache.class
            );
         }

         assert cache.receiverClass == receiverClass;

         assert cache.keyClass == keyClass;

         assert cache.keyType == keyType;

         assert cache.valueClass == valueClass;

         assert cache.valueType == valueType;

         return cache;
      }

      private static class Apply extends PolyglotMap.Cache.PolyglotMapNode {
         @Node.Child
         private PolyglotExecuteNode apply = PolyglotExecuteNodeGen.create();

         Apply(PolyglotMap.Cache cache) {
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

      abstract static class ContainsKeyNode extends PolyglotMap.Cache.PolyglotMapNode {
         ContainsKeyNode(PolyglotMap.Cache cache) {
            super(cache);
         }

         @Specialization(limit = "LIMIT")
         protected Object doCached(
            PolyglotLanguageContext languageContext,
            Object receiver,
            Object[] args,
            @CachedLibrary("receiver") InteropLibrary interop,
            @Cached PolyglotLanguageContext.ToGuestValueNode toGuest
         ) {
            Object key = args[2];
            if (interop.hasHashEntries(receiver)) {
               return interop.isHashEntryReadable(receiver, toGuest.execute(languageContext, key));
            } else {
               if (this.cache.memberKey && interop.hasMembers(receiver)) {
                  if (this.isObjectKey(key)) {
                     return interop.isMemberReadable(receiver, (String)key);
                  }
               } else if (this.cache.numberKey && interop.hasArrayElements(receiver) && this.isArrayKey(key)) {
                  return interop.isArrayElementReadable(receiver, PolyglotMap.intValue(key));
               }

               return false;
            }
         }

         @Override
         protected String getOperationName() {
            return "containsKey";
         }
      }

      abstract static class EntrySet extends PolyglotMap.Cache.PolyglotMapNode {
         EntrySet(PolyglotMap.Cache cache) {
            super(cache);
         }

         @Specialization(limit = "LIMIT")
         protected Object doCached(
            PolyglotLanguageContext languageContext,
            Object receiver,
            Object[] args,
            @CachedLibrary("receiver") InteropLibrary interop,
            @Cached PolyglotToHostNode toHost,
            @Cached BranchProfile error
         ) {
            PolyglotMap<Object, Object> originalMap = (PolyglotMap<Object, Object>)args[2];
            if (interop.hasHashEntries(receiver)) {
               return originalMap.new HashEntries();
            } else {
               List<?> keys = null;
               int keysSize = 0;
               long elemSize = 0L;
               if (this.cache.memberKey && interop.hasMembers(receiver)) {
                  Object truffleKeys;
                  try {
                     truffleKeys = interop.getMembers(receiver);
                  } catch (UnsupportedMessageException var15) {
                     error.enter();
                     return Collections.emptySet();
                  }

                  keys = PolyglotList.create(languageContext, truffleKeys, false, String.class, null);
                  keysSize = keys.size();
               } else if (this.cache.numberKey && interop.hasArrayElements(receiver)) {
                  try {
                     elemSize = interop.getArraySize(receiver);
                  } catch (UnsupportedMessageException var14) {
                     error.enter();
                     elemSize = 0L;
                  }
               }

               return originalMap.new LazyEntries(keys, keysSize, (int)elemSize);
            }
         }

         @Override
         protected String getOperationName() {
            return "entrySet";
         }
      }

      abstract static class GetNode extends PolyglotMap.Cache.PolyglotMapNode {
         GetNode(PolyglotMap.Cache cache) {
            super(cache);
         }

         @Override
         protected String getOperationName() {
            return "get";
         }

         @Specialization(limit = "LIMIT")
         protected Object doCached(
            PolyglotLanguageContext languageContext,
            Object receiver,
            Object[] args,
            @CachedLibrary("receiver") InteropLibrary interop,
            @Cached PolyglotLanguageContext.ToGuestValueNode toGuest,
            @Cached PolyglotToHostNode toHost,
            @Cached BranchProfile error
         ) {
            Object key = args[2];

            Object result;
            try {
               if (interop.hasHashEntries(receiver)) {
                  result = interop.readHashValue(receiver, toGuest.execute(languageContext, key));
               } else if (this.cache.memberKey && interop.hasMembers(receiver)) {
                  if (!this.isObjectKey(key)) {
                     return null;
                  }

                  result = interop.readMember(receiver, (String)key);
               } else {
                  if (!this.cache.numberKey || !interop.hasArrayElements(receiver)) {
                     return null;
                  }

                  if (!this.isArrayKey(key)) {
                     return null;
                  }

                  result = interop.readArrayElement(receiver, PolyglotMap.intValue(key));
               }
            } catch (InvalidArrayIndexException | UnknownKeyException | UnsupportedMessageException | UnknownIdentifierException var11) {
               error.enter();
               return null;
            }

            return toHost.execute(languageContext, result, this.cache.valueClass, this.cache.valueType);
         }
      }

      abstract static class HashEntriesIteratorNode extends PolyglotMap.Cache.PolyglotMapNode {
         HashEntriesIteratorNode(PolyglotMap.Cache cache) {
            super(cache);
         }

         @Override
         protected String getOperationName() {
            return "iterator";
         }

         @Specialization(limit = "LIMIT")
         protected Object doCached(
            PolyglotLanguageContext languageContext,
            Object receiver,
            Object[] args,
            @CachedLibrary("receiver") InteropLibrary interop,
            @Cached PolyglotToHostNode toHost,
            @Cached BranchProfile error
         ) {
            if (interop.hasHashEntries(receiver)) {
               try {
                  Object iterator = interop.getHashEntriesIterator(receiver);
                  Type useKeyType = (Type)(this.cache.keyType != null ? this.cache.keyType : Object.class);
                  Type useValueType = (Type)(this.cache.valueType != null ? this.cache.valueType : Object.class);
                  Type genericType = new PolyglotMap.ParameterizedTypeImpl(
                     Iterator.class, new PolyglotMap.ParameterizedTypeImpl(Entry.class, useKeyType, useValueType)
                  );
                  return toHost.execute(languageContext, iterator, Iterator.class, genericType);
               } catch (UnsupportedMessageException var11) {
                  error.enter();
                  throw PolyglotInteropErrors.mapUnsupported(languageContext, receiver, this.getKeyType(), this.getValueType(), "iterator");
               }
            } else {
               error.enter();
               throw PolyglotInteropErrors.mapUnsupported(languageContext, receiver, this.getKeyType(), this.getValueType(), "iterator");
            }
         }
      }

      abstract static class HashSizeNode extends PolyglotMap.Cache.PolyglotMapNode {
         HashSizeNode(PolyglotMap.Cache cache) {
            super(cache);
         }

         @Override
         protected String getOperationName() {
            return "size";
         }

         @Specialization(limit = "LIMIT")
         protected Object doCached(
            PolyglotLanguageContext languageContext,
            Object receiver,
            Object[] args,
            @CachedLibrary("receiver") InteropLibrary interop,
            @Cached BranchProfile error
         ) {
            if (interop.hasHashEntries(receiver)) {
               try {
                  return interop.getHashSize(receiver);
               } catch (UnsupportedMessageException var7) {
                  error.enter();
                  throw PolyglotInteropErrors.mapUnsupported(languageContext, receiver, this.getKeyType(), this.getValueType(), "size");
               }
            } else {
               error.enter();
               throw PolyglotInteropErrors.mapUnsupported(languageContext, receiver, this.getKeyType(), this.getValueType(), "size");
            }
         }
      }

      private static final class Key {
         final Class<?> receiverClass;
         final Class<?> keyClass;
         final Type keyType;
         final Class<?> valueClass;
         final Type valueType;

         Key(Class<?> receiverClass, Class<?> keyClass, Type keyType, Class<?> valueClass, Type valueType) {
            this.receiverClass = Objects.requireNonNull(receiverClass);
            this.keyClass = Objects.requireNonNull(keyClass);
            this.keyType = keyType;
            this.valueClass = Objects.requireNonNull(valueClass);
            this.valueType = valueType;
         }

         @Override
         public int hashCode() {
            int hashCode = 17;
            hashCode = hashCode * 31 + this.receiverClass.hashCode();
            hashCode = hashCode * 31 + this.keyClass.hashCode();
            hashCode = hashCode * 31 + (this.keyType != null ? this.keyType.hashCode() : 0);
            hashCode = hashCode * 31 + this.valueClass.hashCode();
            return hashCode * 31 + (this.valueType != null ? this.valueType.hashCode() : 0);
         }

         @Override
         public boolean equals(Object obj) {
            if (this == obj) {
               return true;
            } else if (obj != null && this.getClass() == obj.getClass()) {
               PolyglotMap.Cache.Key other = (PolyglotMap.Cache.Key)obj;
               return this.receiverClass == other.receiverClass
                  && this.keyClass == other.keyClass
                  && Objects.equals(this.keyType, other.keyType)
                  && this.valueClass == other.valueClass
                  && Objects.equals(this.valueType, other.valueType);
            } else {
               return false;
            }
         }
      }

      abstract static class PolyglotMapNode extends HostToGuestRootNode {
         static final int LIMIT = 5;
         final PolyglotMap.Cache cache;

         PolyglotMapNode(PolyglotMap.Cache cache) {
            super(cache.languageInstance);
            this.cache = cache;
         }

         @Override
         protected Class<? extends TruffleObject> getReceiverType() {
            return (Class<? extends TruffleObject>)this.cache.receiverClass;
         }

         @Override
         public final String getName() {
            return "PolyglotMap<" + this.cache.receiverClass + ", " + this.getKeyType() + ", " + this.getValueType() + ">." + this.getOperationName();
         }

         protected final boolean isObjectKey(Object key) {
            return this.cache.memberKey && this.cache.keyClass.isInstance(key) && key instanceof String;
         }

         protected final boolean isArrayKey(Object key) {
            return this.cache.numberKey && this.cache.keyClass.isInstance(key) && key instanceof Number;
         }

         protected Type getKeyType() {
            return (Type)(this.cache.keyType != null ? this.cache.keyType : this.cache.keyClass);
         }

         protected Type getValueType() {
            return (Type)(this.cache.valueType != null ? this.cache.valueType : this.cache.valueClass);
         }

         protected abstract String getOperationName();
      }

      abstract static class Put extends PolyglotMap.Cache.PolyglotMapNode {
         Put(PolyglotMap.Cache cache) {
            super(cache);
         }

         @Override
         protected String getOperationName() {
            return "put";
         }

         @Specialization(limit = "LIMIT")
         protected Object doCached(
            PolyglotLanguageContext languageContext,
            Object receiver,
            Object[] args,
            @CachedLibrary("receiver") InteropLibrary interop,
            @Cached PolyglotLanguageContext.ToGuestValueNode toGuest,
            @Cached BranchProfile error
         ) {
            Object key = args[2];
            Object guestValue = toGuest.execute(languageContext, args[3]);

            try {
               boolean supported = false;
               if (interop.hasHashEntries(receiver)) {
                  interop.writeHashEntry(receiver, toGuest.execute(languageContext, key), guestValue);
                  return null;
               } else {
                  if (this.cache.memberKey && interop.hasMembers(receiver)) {
                     supported = true;
                     if (this.isObjectKey(key)) {
                        interop.writeMember(receiver, (String)key, guestValue);
                        return null;
                     }
                  } else if (this.cache.numberKey && interop.hasArrayElements(receiver)) {
                     supported = true;
                     if (this.isArrayKey(key)) {
                        interop.writeArrayElement(receiver, PolyglotMap.intValue(key), guestValue);
                        return null;
                     }
                  }

                  error.enter();
                  if (!supported) {
                     throw PolyglotInteropErrors.mapUnsupported(languageContext, receiver, this.getKeyType(), this.getValueType(), "put");
                  } else {
                     throw PolyglotInteropErrors.invalidMapIdentifier(languageContext, receiver, this.getKeyType(), this.getValueType(), key);
                  }
               }
            } catch (InvalidArrayIndexException | UnknownKeyException | UnsupportedMessageException | UnsupportedTypeException | UnknownIdentifierException var10) {
               error.enter();
               throw this.error(languageContext, receiver, var10, key, guestValue);
            }
         }

         @CompilerDirectives.TruffleBoundary
         RuntimeException error(PolyglotLanguageContext languageContext, Object receiver, InteropException e, Object key, Object guestValue) {
            if (e instanceof UnknownIdentifierException || e instanceof InvalidArrayIndexException) {
               throw PolyglotInteropErrors.invalidMapIdentifier(languageContext, receiver, this.getKeyType(), this.getValueType(), key);
            } else if (e instanceof UnsupportedMessageException) {
               throw PolyglotInteropErrors.mapUnsupported(languageContext, receiver, this.getKeyType(), this.getValueType(), "put");
            } else if (e instanceof UnsupportedTypeException) {
               throw PolyglotInteropErrors.invalidMapValue(languageContext, receiver, this.getKeyType(), this.getValueType(), key, guestValue);
            } else {
               throw CompilerDirectives.shouldNotReachHere("unhandled error");
            }
         }
      }

      abstract static class RemoveBoolean extends PolyglotMap.Cache.PolyglotMapNode {
         RemoveBoolean(PolyglotMap.Cache cache) {
            super(cache);
         }

         @Override
         protected String getOperationName() {
            return "remove";
         }

         @Specialization(limit = "LIMIT")
         protected Object doCached(
            PolyglotLanguageContext languageContext,
            Object receiver,
            Object[] args,
            @CachedLibrary("receiver") InteropLibrary interop,
            @Cached PolyglotLanguageContext.ToGuestValueNode toGuest,
            @Cached BranchProfile error
         ) {
            Object key = args[2];
            Object expectedValue = args[3];

            try {
               boolean supported = false;
               if (interop.hasHashEntries(receiver)) {
                  Object guestKey = toGuest.execute(languageContext, key);
                  Object guestExcpectedValue = toGuest.execute(languageContext, expectedValue);
                  Object readValue = interop.readHashValue(receiver, guestKey);
                  if (!equalsBoundary(guestExcpectedValue, readValue)) {
                     return false;
                  } else {
                     interop.removeHashEntry(receiver, guestKey);
                     return true;
                  }
               } else {
                  if (this.cache.memberKey && interop.hasMembers(receiver)) {
                     supported = true;
                     if (this.isObjectKey(key)) {
                        String member = (String)key;
                        Object readValue = interop.readMember(receiver, member);
                        Object guestExpectedValue = toGuest.execute(languageContext, expectedValue);
                        if (!equalsBoundary(guestExpectedValue, readValue)) {
                           return false;
                        }

                        interop.removeMember(receiver, (String)key);
                        return true;
                     }
                  } else if (this.cache.numberKey && interop.hasArrayElements(receiver)) {
                     supported = true;
                     if (this.isArrayKey(key)) {
                        int index = PolyglotMap.intValue(key);
                        Object readValue = interop.readArrayElement(receiver, index);
                        Object guestExpectedValue = toGuest.execute(languageContext, expectedValue);
                        if (!equalsBoundary(guestExpectedValue, readValue)) {
                           return false;
                        }

                        interop.removeArrayElement(receiver, index);
                        return true;
                     }
                  }

                  error.enter();
                  if (!supported) {
                     throw PolyglotInteropErrors.mapUnsupported(languageContext, receiver, this.getKeyType(), this.getValueType(), "remove");
                  } else {
                     return false;
                  }
               }
            } catch (InvalidArrayIndexException | UnknownKeyException | UnknownIdentifierException var13) {
               error.enter();
               return false;
            } catch (UnsupportedMessageException var14) {
               error.enter();
               throw PolyglotInteropErrors.mapUnsupported(languageContext, receiver, this.getKeyType(), this.getValueType(), "remove");
            }
         }

         @CompilerDirectives.TruffleBoundary
         private static boolean equalsBoundary(Object expectedValue, Object readValue) {
            return Objects.equals(expectedValue, readValue);
         }
      }

      abstract static class RemoveNode extends PolyglotMap.Cache.PolyglotMapNode {
         RemoveNode(PolyglotMap.Cache cache) {
            super(cache);
         }

         @Override
         protected String getOperationName() {
            return "remove";
         }

         @Specialization(limit = "LIMIT")
         protected Object doCached(
            PolyglotLanguageContext languageContext,
            Object receiver,
            Object[] args,
            @CachedLibrary("receiver") InteropLibrary interop,
            @Cached PolyglotLanguageContext.ToGuestValueNode toGuest,
            @Cached BranchProfile error
         ) {
            Object key = args[2];

            try {
               boolean supported = false;
               if (interop.hasHashEntries(receiver)) {
                  interop.removeHashEntry(receiver, toGuest.execute(languageContext, key));
                  return null;
               } else {
                  if (this.cache.memberKey && interop.hasMembers(receiver)) {
                     supported = true;
                     if (this.isObjectKey(key)) {
                        interop.removeMember(receiver, (String)key);
                        return null;
                     }
                  } else if (this.cache.numberKey && interop.hasArrayElements(receiver)) {
                     supported = true;
                     if (this.isArrayKey(key)) {
                        interop.removeArrayElement(receiver, PolyglotMap.intValue(key));
                        return null;
                     }
                  }

                  error.enter();
                  if (!supported) {
                     throw PolyglotInteropErrors.mapUnsupported(languageContext, receiver, this.getKeyType(), this.getValueType(), "remove");
                  } else {
                     return null;
                  }
               }
            } catch (InvalidArrayIndexException | UnknownKeyException | UnknownIdentifierException var9) {
               error.enter();
               return null;
            } catch (UnsupportedMessageException var10) {
               error.enter();
               throw PolyglotInteropErrors.mapUnsupported(languageContext, receiver, this.getKeyType(), this.getValueType(), "remove");
            }
         }
      }
   }

   private final class EntryImpl implements Entry<K, V> {
      private final K key;

      EntryImpl(K key) {
         this.key = key;
      }

      @Override
      public K getKey() {
         return this.key;
      }

      @Override
      public V getValue() {
         return PolyglotMap.this.get(this.key);
      }

      @Override
      public V setValue(V value) {
         return PolyglotMap.this.put(this.key, value);
      }

      @Override
      public String toString() {
         return "Entry[key=" + this.key + ", value=" + PolyglotMap.this.get(this.key) + "]";
      }
   }

   private final class HashEntries extends PolyglotMap<K, V>.AbstractEntrySet {
      @Override
      public Iterator<Entry<K, V>> iterator() {
         return (Iterator<Entry<K, V>>)PolyglotMap.this.cache.hashEntriesIterator.call(PolyglotMap.this.languageContext, PolyglotMap.this.guestObject);
      }

      @Override
      public int size() {
         long size = (Long)PolyglotMap.this.cache.hashSize.call(PolyglotMap.this.languageContext, PolyglotMap.this.guestObject);
         return size > 2147483647L ? Integer.MAX_VALUE : (int)size;
      }
   }

   private final class LazyEntries extends PolyglotMap<K, V>.AbstractEntrySet {
      private final List<?> props;
      private final int keysSize;
      private final int elemSize;

      LazyEntries(List<?> keys, int keysSize, int elemSize) {
         assert keys != null || keysSize == 0;

         this.props = keys;
         this.keysSize = keysSize;
         this.elemSize = elemSize;
      }

      @Override
      public Iterator<Entry<K, V>> iterator() {
         if (this.keysSize > 0 && this.elemSize > 0) {
            return new PolyglotMap.LazyEntries.CombinedIterator();
         } else {
            return (Iterator<Entry<K, V>>)(this.keysSize > 0 ? new PolyglotMap.LazyEntries.LazyKeysIterator() : new PolyglotMap.LazyEntries.ElementsIterator());
         }
      }

      @Override
      public int size() {
         return (this.props != null ? this.props.size() : this.keysSize) + this.elemSize;
      }

      private final class CombinedIterator implements Iterator<Entry<K, V>> {
         private final Iterator<Entry<K, V>> elemIter = LazyEntries.this.new ElementsIterator();
         private final Iterator<Entry<K, V>> keysIter = LazyEntries.this.new LazyKeysIterator();
         private boolean isElemCurrent;

         @Override
         public boolean hasNext() {
            return this.elemIter.hasNext() || this.keysIter.hasNext();
         }

         public Entry<K, V> next() {
            if (this.elemIter.hasNext()) {
               this.isElemCurrent = true;
               return this.elemIter.next();
            } else if (this.keysIter.hasNext()) {
               this.isElemCurrent = false;
               return this.keysIter.next();
            } else {
               throw new NoSuchElementException();
            }
         }

         @Override
         public void remove() {
            if (this.isElemCurrent) {
               this.elemIter.remove();
            } else {
               this.keysIter.remove();
            }
         }
      }

      private final class ElementsIterator implements Iterator<Entry<K, V>> {
         private int index = 0;
         private boolean hasCurrentEntry;

         ElementsIterator() {
         }

         @Override
         public boolean hasNext() {
            return this.index < LazyEntries.this.elemSize;
         }

         public Entry<K, V> next() {
            if (this.hasNext()) {
               Number key;
               if (PolyglotMap.this.cache.keyClass == Long.class) {
                  key = (long)this.index;
               } else {
                  key = this.index;
               }

               this.index++;
               this.hasCurrentEntry = true;
               return PolyglotMap.this.new EntryImpl(PolyglotMap.this.cache.keyClass.cast(key));
            } else {
               throw new NoSuchElementException();
            }
         }

         @Override
         public void remove() {
            if (this.hasCurrentEntry) {
               PolyglotMap.this.cache
                  .removeBoolean
                  .call(PolyglotMap.this.languageContext, PolyglotMap.this.guestObject, PolyglotMap.this.cache.keyClass.cast(this.index - 1));
               this.hasCurrentEntry = false;
            } else {
               throw new IllegalStateException("No current entry.");
            }
         }
      }

      private final class LazyKeysIterator implements Iterator<Entry<K, V>> {
         private final int size;
         private int index;
         private int currentIndex = -1;

         LazyKeysIterator() {
            this.size = LazyEntries.this.props != null ? LazyEntries.this.props.size() : LazyEntries.this.keysSize;
            this.index = 0;
         }

         @Override
         public boolean hasNext() {
            return this.index < this.size;
         }

         public Entry<K, V> next() {
            if (this.hasNext()) {
               this.currentIndex = this.index;
               Object key = LazyEntries.this.props.get(this.index++);
               return PolyglotMap.this.new EntryImpl(key);
            } else {
               throw new NoSuchElementException();
            }
         }

         @Override
         public void remove() {
            if (this.currentIndex >= 0) {
               LazyEntries.this.props.remove(this.currentIndex);
               this.currentIndex = -1;
               this.index--;
            } else {
               throw new IllegalStateException("No current entry.");
            }
         }
      }
   }

   private static final class ParameterizedTypeImpl implements ParameterizedType {
      private final Type rawType;
      private final Type[] typeParameters;

      ParameterizedTypeImpl(Type rawType, Type... typeParameters) {
         this.rawType = rawType;
         this.typeParameters = typeParameters;
      }

      @Override
      public Type[] getActualTypeArguments() {
         return this.typeParameters;
      }

      @Override
      public Type getRawType() {
         return this.rawType;
      }

      @Override
      public Type getOwnerType() {
         return null;
      }
   }
}
