package com.oracle.truffle.polyglot;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.TruffleSafepoint;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.InvalidArrayIndexException;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.interop.UnsupportedTypeException;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.profiles.BranchProfile;
import java.lang.reflect.Type;
import java.util.AbstractList;
import java.util.List;
import java.util.Objects;

class PolyglotList<T> extends AbstractList<T> implements PolyglotWrapper {
   final Object guestObject;
   final PolyglotLanguageContext languageContext;
   final PolyglotList.Cache cache;

   PolyglotList(Class<T> elementClass, Type elementType, Object array, PolyglotLanguageContext languageContext) {
      this.guestObject = array;
      this.languageContext = languageContext;
      this.cache = PolyglotList.Cache.lookup(languageContext, array.getClass(), elementClass, elementType);
   }

   @Override
   public Object getGuestObject() {
      return this.guestObject;
   }

   @Override
   public PolyglotLanguageContext getLanguageContext() {
      return this.languageContext;
   }

   @Override
   public PolyglotContextImpl getContext() {
      return this.languageContext.context;
   }

   @CompilerDirectives.TruffleBoundary
   public static <T> List<T> create(PolyglotLanguageContext languageContext, Object array, boolean implementFunction, Class<T> elementClass, Type elementType) {
      return (List<T>)(implementFunction
         ? new PolyglotListAndFunction<>(elementClass, elementType, array, languageContext)
         : new PolyglotList<>(elementClass, elementType, array, languageContext));
   }

   @Override
   public T get(int index) {
      return (T)this.cache.get.call(this.languageContext, this.guestObject, index);
   }

   @Override
   public boolean add(T element) {
      return (Boolean)this.cache.add.call(this.languageContext, this.guestObject, element);
   }

   @Override
   public void add(int index, T element) {
      this.cache.addAtIndex.call(this.languageContext, this.guestObject, index, element);
   }

   @Override
   public T set(int index, T element) {
      T prev = this.get(index);
      this.cache.set.call(this.languageContext, this.guestObject, index, element);
      return prev;
   }

   @Override
   public T remove(int index) {
      T prev = this.get(index);
      this.cache.remove.call(this.languageContext, this.guestObject, index);
      return prev;
   }

   @Override
   public int size() {
      return (Integer)this.cache.size.call(this.languageContext, this.guestObject);
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
      return o instanceof PolyglotList ? PolyglotWrapper.equals(this.languageContext, this.guestObject, ((PolyglotList)o).guestObject) : false;
   }

   static final class Cache {
      final PolyglotLanguageInstance languageInstance;
      final Class<?> receiverClass;
      final Class<?> valueClass;
      final Type valueType;
      final CallTarget get;
      final CallTarget add;
      final CallTarget addAtIndex;
      final CallTarget set;
      final CallTarget remove;
      final CallTarget size;
      final CallTarget apply;

      Cache(PolyglotLanguageInstance languageInstance, Class<?> receiverClass, Class<?> valueClass, Type valueType) {
         this.languageInstance = languageInstance;
         this.receiverClass = receiverClass;
         this.valueClass = valueClass;
         this.valueType = valueType;
         this.get = PolyglotListFactory.CacheFactory.GetNodeGen.create(this).getCallTarget();
         this.add = PolyglotListFactory.CacheFactory.AddNodeGen.create(this).getCallTarget();
         this.addAtIndex = PolyglotListFactory.CacheFactory.AddAtIndexNodeGen.create(this).getCallTarget();
         this.size = PolyglotListFactory.CacheFactory.SizeNodeGen.create(this).getCallTarget();
         this.set = PolyglotListFactory.CacheFactory.SetNodeGen.create(this).getCallTarget();
         this.remove = PolyglotListFactory.CacheFactory.RemoveNodeGen.create(this).getCallTarget();
         this.apply = new PolyglotList.Cache.Apply(this).getCallTarget();
      }

      static PolyglotList.Cache lookup(PolyglotLanguageContext languageContext, Class<?> receiverClass, Class<?> valueClass, Type valueType) {
         PolyglotList.Cache.Key cacheKey = new PolyglotList.Cache.Key(receiverClass, valueClass, valueType);
         PolyglotList.Cache cache = HostToGuestRootNode.lookupHostCodeCache(languageContext, cacheKey, PolyglotList.Cache.class);
         if (cache == null) {
            cache = HostToGuestRootNode.installHostCodeCache(
               languageContext,
               cacheKey,
               new PolyglotList.Cache(languageContext.getLanguageInstance(), receiverClass, valueClass, valueType),
               PolyglotList.Cache.class
            );
         }

         assert cache.receiverClass == receiverClass;

         assert cache.valueClass == valueClass;

         assert cache.valueType == valueType;

         return cache;
      }

      abstract static class AddAtIndexNode extends PolyglotList.Cache.PolyglotListNode {
         AddAtIndexNode(PolyglotList.Cache cache) {
            super(cache);
         }

         @Override
         protected String getOperationName() {
            return "add";
         }

         @Specialization(limit = "LIMIT")
         Object doCached(
            PolyglotLanguageContext languageContext,
            Object receiver,
            Object[] args,
            @CachedLibrary("receiver") InteropLibrary interop,
            @Cached PolyglotLanguageContext.ToGuestValueNode toGuest,
            @Cached BranchProfile error
         ) {
            Object key = args[2];

            assert key instanceof Integer;

            int index = (Integer)key;
            if (index < 0) {
               error.enter();
               throw PolyglotInteropErrors.invalidListIndex(languageContext, receiver, this.cache.valueType, index);
            } else {
               Object value = toGuest.execute(languageContext, args[3]);

               try {
                  long size = interop.getArraySize(receiver);
                  if (!interop.isArrayElementInsertable(receiver, size)) {
                     error.enter();
                     throw PolyglotInteropErrors.listUnsupported(languageContext, receiver, this.cache.valueType, "add");
                  }

                  long cur = size;

                  while (cur > index) {
                     interop.writeArrayElement(receiver, cur, interop.readArrayElement(receiver, cur - 1L));
                     cur--;
                     TruffleSafepoint.poll(interop);
                  }

                  interop.writeArrayElement(receiver, index, value);
               } catch (UnsupportedMessageException var14) {
                  error.enter();
                  throw PolyglotInteropErrors.listUnsupported(languageContext, receiver, this.cache.valueType, "add");
               } catch (UnsupportedTypeException var15) {
                  error.enter();
                  throw PolyglotInteropErrors.invalidListValue(languageContext, receiver, this.cache.valueType, index, value);
               } catch (InvalidArrayIndexException var16) {
                  error.enter();
                  throw PolyglotInteropErrors.invalidListIndex(languageContext, receiver, this.cache.valueType, index);
               }

               return true;
            }
         }
      }

      abstract static class AddNode extends PolyglotList.Cache.PolyglotListNode {
         AddNode(PolyglotList.Cache cache) {
            super(cache);
         }

         @Override
         protected String getOperationName() {
            return "add";
         }

         @Specialization(limit = "LIMIT")
         Object doCached(
            PolyglotLanguageContext languageContext,
            Object receiver,
            Object[] args,
            @CachedLibrary("receiver") InteropLibrary interop,
            @Cached PolyglotLanguageContext.ToGuestValueNode toGuest,
            @Cached BranchProfile error
         ) {
            Object value = toGuest.execute(languageContext, args[2]);
            long size = 0L;

            try {
               size = interop.getArraySize(receiver);
               if (!interop.isArrayElementInsertable(receiver, size)) {
                  error.enter();
                  throw PolyglotInteropErrors.listUnsupported(languageContext, receiver, this.cache.valueType, "add");
               }

               interop.writeArrayElement(receiver, size, value);
            } catch (UnsupportedMessageException var11) {
               error.enter();
               throw PolyglotInteropErrors.listUnsupported(languageContext, receiver, this.cache.valueType, "add");
            } catch (UnsupportedTypeException var12) {
               error.enter();
               throw PolyglotInteropErrors.invalidListValue(languageContext, receiver, this.cache.valueType, size, value);
            } catch (InvalidArrayIndexException var13) {
               error.enter();
               throw PolyglotInteropErrors.invalidListIndex(languageContext, receiver, this.cache.valueType, size);
            }

            return true;
         }
      }

      private static class Apply extends PolyglotList.Cache.PolyglotListNode {
         @Node.Child
         private PolyglotExecuteNode apply = PolyglotExecuteNodeGen.create();

         Apply(PolyglotList.Cache cache) {
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

      abstract static class GetNode extends PolyglotList.Cache.PolyglotListNode {
         GetNode(PolyglotList.Cache cache) {
            super(cache);
         }

         @Specialization(limit = "LIMIT")
         Object doCached(
            PolyglotLanguageContext languageContext,
            Object receiver,
            Object[] args,
            @CachedLibrary("receiver") InteropLibrary interop,
            @Cached PolyglotToHostNode toHost,
            @Cached BranchProfile error
         ) {
            Object key = args[2];
            Object result = null;

            assert key instanceof Integer;

            int index = (Integer)key;

            try {
               return toHost.execute(languageContext, interop.readArrayElement(receiver, index), this.cache.valueClass, this.cache.valueType);
            } catch (InvalidArrayIndexException var11) {
               error.enter();
               throw PolyglotInteropErrors.invalidListIndex(languageContext, receiver, this.cache.valueType, index);
            } catch (UnsupportedMessageException var12) {
               error.enter();
               throw PolyglotInteropErrors.listUnsupported(languageContext, receiver, this.cache.valueType, "get()");
            }
         }

         @Override
         protected String getOperationName() {
            return "get";
         }
      }

      private static final class Key {
         final Class<?> receiverClass;
         final Class<?> valueClass;
         final Type valueType;

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
               PolyglotList.Cache.Key other = (PolyglotList.Cache.Key)obj;
               return this.receiverClass == other.receiverClass && this.valueClass == other.valueClass && Objects.equals(this.valueType, other.valueType);
            } else {
               return false;
            }
         }
      }

      abstract static class PolyglotListNode extends HostToGuestRootNode {
         static final int LIMIT = 5;
         final PolyglotList.Cache cache;

         PolyglotListNode(PolyglotList.Cache cache) {
            super(cache.languageInstance);
            this.cache = cache;
         }

         @Override
         protected Class<? extends TruffleObject> getReceiverType() {
            return (Class<? extends TruffleObject>)this.cache.receiverClass;
         }

         @Override
         public final String getName() {
            return "PolyglotList<" + this.cache.receiverClass + ", " + this.cache.valueType + ">." + this.getOperationName();
         }

         protected abstract String getOperationName();
      }

      abstract static class RemoveNode extends PolyglotList.Cache.PolyglotListNode {
         RemoveNode(PolyglotList.Cache cache) {
            super(cache);
         }

         @Override
         protected String getOperationName() {
            return "remove";
         }

         @Specialization(limit = "LIMIT")
         Object doCached(
            PolyglotLanguageContext languageContext,
            Object receiver,
            Object[] args,
            @CachedLibrary("receiver") InteropLibrary interop,
            @Cached BranchProfile error
         ) {
            Object key = args[2];

            assert key instanceof Integer;

            int index = (Integer)key;

            try {
               interop.removeArrayElement(receiver, index);
               return null;
            } catch (InvalidArrayIndexException var9) {
               error.enter();
               throw PolyglotInteropErrors.invalidListIndex(languageContext, receiver, this.cache.valueType, index);
            } catch (UnsupportedMessageException var10) {
               error.enter();
               throw PolyglotInteropErrors.listUnsupported(languageContext, receiver, this.cache.valueType, "remove");
            }
         }
      }

      abstract static class SetNode extends PolyglotList.Cache.PolyglotListNode {
         SetNode(PolyglotList.Cache cache) {
            super(cache);
         }

         @Override
         protected String getOperationName() {
            return "set";
         }

         @Specialization(limit = "LIMIT")
         Object doCached(
            PolyglotLanguageContext languageContext,
            Object receiver,
            Object[] args,
            @CachedLibrary("receiver") InteropLibrary interop,
            @Cached PolyglotLanguageContext.ToGuestValueNode toGuest,
            @Cached BranchProfile error
         ) {
            Object key = args[2];

            assert key instanceof Integer;

            int index = (Integer)key;
            Object value = toGuest.execute(languageContext, args[3]);

            try {
               interop.writeArrayElement(receiver, index, value);
               return null;
            } catch (InvalidArrayIndexException var11) {
               error.enter();
               throw PolyglotInteropErrors.invalidListIndex(languageContext, receiver, this.cache.valueType, index);
            } catch (UnsupportedMessageException var12) {
               error.enter();
               throw PolyglotInteropErrors.listUnsupported(languageContext, receiver, this.cache.valueType, "set");
            } catch (UnsupportedTypeException var13) {
               error.enter();
               throw PolyglotInteropErrors.invalidListValue(languageContext, receiver, this.cache.valueType, ((Integer)key).intValue(), value);
            }
         }
      }

      abstract static class SizeNode extends PolyglotList.Cache.PolyglotListNode {
         SizeNode(PolyglotList.Cache cache) {
            super(cache);
         }

         @Specialization(limit = "LIMIT")
         Object doCached(PolyglotLanguageContext languageContext, Object receiver, Object[] args, @CachedLibrary("receiver") InteropLibrary interop) {
            try {
               return (int)interop.getArraySize(receiver);
            } catch (UnsupportedMessageException var6) {
               return 0;
            }
         }

         @Override
         protected String getOperationName() {
            return "size";
         }
      }
   }
}
