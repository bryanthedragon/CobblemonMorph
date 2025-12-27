package com.oracle.truffle.api.library;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.nodes.DenyReplace;
import com.oracle.truffle.api.nodes.EncapsulatingNodeReference;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.utilities.FinalBitSet;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Collections;
import java.util.concurrent.locks.Lock;

@GeneratedBy(ReflectionLibrary.class)
final class ReflectionLibraryGen extends LibraryFactory<ReflectionLibrary> {
   private static final Class<ReflectionLibrary> LIBRARY_CLASS = lazyLibraryClass();
   private static final Message SEND = new ReflectionLibraryGen.MessageImpl("send", 0, Object.class, Object.class, Message.class, Object[].class);
   private static final ReflectionLibraryGen INSTANCE = new ReflectionLibraryGen();

   private ReflectionLibraryGen() {
      super(LIBRARY_CLASS, Collections.unmodifiableList(Arrays.asList(SEND)));
   }

   @Override
   protected Class<?> getDefaultClass(Object receiver) {
      return ReflectionLibraryDefault.class;
   }

   protected ReflectionLibrary createProxy(ReflectionLibrary library) {
      return new ReflectionLibraryGen.Proxy(library);
   }

   @Override
   protected FinalBitSet createMessageBitSet(Message... messages) {
      BitSet bitSet = new BitSet(2);

      for (Message message : messages) {
         bitSet.set(message.getId());
      }

      return FinalBitSet.valueOf(bitSet);
   }

   protected ReflectionLibrary createDelegate(ReflectionLibrary delegateLibrary) {
      return new ReflectionLibraryGen.Delegate(delegateLibrary);
   }

   @Override
   protected Object genericDispatch(Library originalLib, Object receiver, Message message, Object[] args, int offset) throws Exception {
      ReflectionLibrary lib = (ReflectionLibrary)originalLib;
      if (message.getParameterCount() - 1 != args.length - offset) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         throw new IllegalArgumentException("Invalid number of arguments.");
      } else {
         switch (message.getId()) {
            case 0:
               return lib.send(receiver, (Message)args[offset], (Object[])args[offset + 1]);
            default:
               CompilerDirectives.transferToInterpreterAndInvalidate();
               throw new AbstractMethodError(message.toString());
         }
      }
   }

   protected ReflectionLibrary createDispatchImpl(int limit) {
      return new ReflectionLibraryGen.CachedDispatchFirst(null, null, limit);
   }

   protected ReflectionLibrary createUncachedDispatch() {
      return new ReflectionLibraryGen.UncachedDispatch();
   }

   private static Class<ReflectionLibrary> lazyLibraryClass() {
      try {
         return (Class<ReflectionLibrary>)Class.forName("com.oracle.truffle.api.library.ReflectionLibrary", false, ReflectionLibraryGen.class.getClassLoader());
      } catch (ClassNotFoundException var1) {
         throw CompilerDirectives.shouldNotReachHere(var1);
      }
   }

   static {
      LibraryFactory.register(LIBRARY_CLASS, INSTANCE);
   }

   @GeneratedBy(ReflectionLibrary.class)
   private abstract static class CachedDispatch extends ReflectionLibrary {
      @Node.Child
      ReflectionLibrary library;
      @Node.Child
      ReflectionLibraryGen.CachedDispatch next;

      CachedDispatch(ReflectionLibrary library, ReflectionLibraryGen.CachedDispatch next) {
         this.library = library;
         this.next = next;
      }

      abstract int getLimit();

      @ExplodeLoop
      @Override
      public Object send(Object receiver_, Message message, Object... args) throws Exception {
         while (true) {
            ReflectionLibraryGen.CachedDispatch current = this;

            do {
               ReflectionLibrary thisLibrary = current.library;
               if (thisLibrary != null && thisLibrary.accepts(receiver_)) {
                  return thisLibrary.send(receiver_, message, args);
               }

               current = current.next;
            } while (current != null);

            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.specialize(receiver_);
         }
      }

      @Override
      public boolean accepts(Object receiver_) {
         return true;
      }

      private void specialize(Object receiver_) {
         Lock lock = this.getLock();
         lock.lock();

         try {
            ReflectionLibraryGen.CachedDispatch current = this;
            ReflectionLibrary thisLibrary = this.library;
            if (thisLibrary == null) {
               this.library = this.insert(ReflectionLibraryGen.INSTANCE.create(receiver_));
            } else {
               int count = 0;

               do {
                  ReflectionLibrary currentLibrary = current.library;
                  if (currentLibrary != null && currentLibrary.accepts(receiver_)) {
                     return;
                  }

                  count++;
                  current = current.next;
               } while (current != null);

               if (count >= this.getLimit()) {
                  this.library = this.insert(new ReflectionLibraryGen.CachedToUncachedDispatch());
                  this.next = null;
               } else {
                  this.next = this.insert(new ReflectionLibraryGen.CachedDispatchNext(ReflectionLibraryGen.INSTANCE.create(receiver_), this.next));
               }
            }
         } finally {
            lock.unlock();
         }
      }
   }

   @GeneratedBy(ReflectionLibrary.class)
   private static final class CachedDispatchFirst extends ReflectionLibraryGen.CachedDispatch {
      private final int limit_;

      CachedDispatchFirst(ReflectionLibrary library, ReflectionLibraryGen.CachedDispatch next, int limit_) {
         super(library, next);
         this.limit_ = limit_;
      }

      @Override
      int getLimit() {
         return this.limit_;
      }

      @Override
      public NodeCost getCost() {
         if (this.library instanceof ReflectionLibraryGen.CachedToUncachedDispatch) {
            return NodeCost.MEGAMORPHIC;
         } else {
            ReflectionLibraryGen.CachedDispatch current = this;
            int count = 0;

            do {
               if (current.library != null) {
                  count++;
               }

               current = current.next;
            } while (current != null);

            return NodeCost.fromCount(count);
         }
      }
   }

   @GeneratedBy(ReflectionLibrary.class)
   private static final class CachedDispatchNext extends ReflectionLibraryGen.CachedDispatch {
      CachedDispatchNext(ReflectionLibrary library, ReflectionLibraryGen.CachedDispatch next) {
         super(library, next);
      }

      @Override
      int getLimit() {
         throw CompilerDirectives.shouldNotReachHere();
      }

      @Override
      public NodeCost getCost() {
         return NodeCost.NONE;
      }
   }

   @GeneratedBy(ReflectionLibrary.class)
   private static final class CachedToUncachedDispatch extends ReflectionLibrary {
      @Override
      public NodeCost getCost() {
         return NodeCost.MEGAMORPHIC;
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public Object send(Object receiver_, Message message, Object... args) throws Exception {
         assert this.getRootNode() != null : "Invalid library usage. Cached library must be adopted by a RootNode before it is executed.";

         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this.getParent());

         Object var6;
         try {
            var6 = ReflectionLibraryGen.INSTANCE.getUncached(receiver_).send(receiver_, message, args);
         } finally {
            encapsulating_.set(prev_);
         }

         return var6;
      }

      @Override
      public boolean accepts(Object receiver_) {
         return true;
      }
   }

   @GeneratedBy(ReflectionLibrary.class)
   private static final class Delegate extends ReflectionLibrary {
      @Node.Child
      private ReflectionLibrary delegateLibrary;

      Delegate(ReflectionLibrary delegateLibrary) {
         this.delegateLibrary = delegateLibrary;
      }

      @Override
      public Object send(Object receiver_, Message message, Object... args) throws Exception {
         if (LibraryFactory.isDelegated(this.delegateLibrary, 0)) {
            Object delegate = LibraryFactory.readDelegate(this.delegateLibrary, receiver_);
            return LibraryFactory.getDelegateLibrary(this.delegateLibrary, delegate).send(delegate, message, args);
         } else {
            return this.delegateLibrary.send(receiver_, message, args);
         }
      }

      @Override
      public boolean accepts(Object receiver_) {
         return this.delegateLibrary.accepts(receiver_);
      }

      @Override
      public NodeCost getCost() {
         return NodeCost.NONE;
      }

      @Override
      public boolean isAdoptable() {
         return this.delegateLibrary.isAdoptable();
      }
   }

   @GeneratedBy(ReflectionLibrary.class)
   private static class MessageImpl extends Message {
      MessageImpl(String name, int index, Class<?> returnType, Class<?>... parameters) {
         super(ReflectionLibraryGen.LIBRARY_CLASS, name, index, returnType, parameters);
      }
   }

   @GeneratedBy(ReflectionLibrary.class)
   private static final class Proxy extends ReflectionLibrary {
      @Node.Child
      private ReflectionLibrary lib;

      Proxy(ReflectionLibrary lib) {
         this.lib = lib;
      }

      @Override
      public Object send(Object receiver_, Message message, Object... args) throws Exception {
         try {
            return this.lib.send(receiver_, ReflectionLibraryGen.SEND, message, args);
         } catch (Exception var5) {
            throw var5;
         }
      }

      @Override
      public boolean accepts(Object receiver_) {
         return this.lib.accepts(receiver_);
      }
   }

   @GeneratedBy(ReflectionLibrary.class)
   @DenyReplace
   private static final class UncachedDispatch extends ReflectionLibrary {
      @Override
      public NodeCost getCost() {
         return NodeCost.MEGAMORPHIC;
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public Object send(Object receiver_, Message message, Object... args) throws Exception {
         return ReflectionLibraryGen.INSTANCE.getUncached(receiver_).send(receiver_, message, args);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public boolean accepts(Object receiver_) {
         return true;
      }

      @Override
      public boolean isAdoptable() {
         return false;
      }
   }
}
