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

@GeneratedBy(DynamicDispatchLibrary.class)
final class DynamicDispatchLibraryGen extends LibraryFactory<DynamicDispatchLibrary> {
   private static final Class<DynamicDispatchLibrary> LIBRARY_CLASS = lazyLibraryClass();
   private static final Message DISPATCH = new DynamicDispatchLibraryGen.MessageImpl("dispatch", 0, Class.class, Object.class);
   private static final DynamicDispatchLibraryGen INSTANCE = new DynamicDispatchLibraryGen();

   private DynamicDispatchLibraryGen() {
      super(LIBRARY_CLASS, Collections.unmodifiableList(Arrays.asList(DISPATCH)));
   }

   @Override
   protected Class<?> getDefaultClass(Object receiver) {
      return DynamicDispatchLibrary.class;
   }

   protected DynamicDispatchLibrary createProxy(ReflectionLibrary library) {
      return new DynamicDispatchLibraryGen.Proxy(library);
   }

   @Override
   protected FinalBitSet createMessageBitSet(Message... messages) {
      BitSet bitSet = new BitSet(2);

      for (Message message : messages) {
         bitSet.set(message.getId());
      }

      return FinalBitSet.valueOf(bitSet);
   }

   protected DynamicDispatchLibrary createDelegate(DynamicDispatchLibrary delegateLibrary) {
      return new DynamicDispatchLibraryGen.Delegate(delegateLibrary);
   }

   @Override
   protected Object genericDispatch(Library originalLib, Object receiver, Message message, Object[] args, int offset) throws Exception {
      DynamicDispatchLibrary lib = (DynamicDispatchLibrary)originalLib;
      if (message.getParameterCount() - 1 != args.length - offset) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         throw new IllegalArgumentException("Invalid number of arguments.");
      } else {
         switch (message.getId()) {
            case 0:
               return lib.dispatch(receiver);
            default:
               CompilerDirectives.transferToInterpreterAndInvalidate();
               throw new AbstractMethodError(message.toString());
         }
      }
   }

   protected DynamicDispatchLibrary createDispatchImpl(int limit) {
      return new DynamicDispatchLibraryGen.CachedDispatchFirst(null, null, limit);
   }

   protected DynamicDispatchLibrary createUncachedDispatch() {
      return new DynamicDispatchLibraryGen.UncachedDispatch();
   }

   private static Class<DynamicDispatchLibrary> lazyLibraryClass() {
      try {
         return (Class<DynamicDispatchLibrary>)Class.forName(
            "com.oracle.truffle.api.library.DynamicDispatchLibrary", false, DynamicDispatchLibraryGen.class.getClassLoader()
         );
      } catch (ClassNotFoundException var1) {
         throw CompilerDirectives.shouldNotReachHere(var1);
      }
   }

   static {
      LibraryExport.register(LIBRARY_CLASS, new DynamicDispatchLibraryGen.Default());
      LibraryFactory.register(LIBRARY_CLASS, INSTANCE);
   }

   @GeneratedBy(DynamicDispatchLibrary.class)
   private abstract static class CachedDispatch extends DynamicDispatchLibrary {
      @Node.Child
      DynamicDispatchLibrary library;
      @Node.Child
      DynamicDispatchLibraryGen.CachedDispatch next;

      CachedDispatch(DynamicDispatchLibrary library, DynamicDispatchLibraryGen.CachedDispatch next) {
         this.library = library;
         this.next = next;
      }

      abstract int getLimit();

      @ExplodeLoop
      @Override
      public Class<?> dispatch(Object receiver_) {
         while (true) {
            DynamicDispatchLibraryGen.CachedDispatch current = this;

            do {
               DynamicDispatchLibrary thisLibrary = current.library;
               if (thisLibrary != null && thisLibrary.accepts(receiver_)) {
                  return thisLibrary.dispatch(receiver_);
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

      @Override
      public Object cast(Object receiver) {
         return receiver;
      }

      private void specialize(Object receiver_) {
         Lock lock = this.getLock();
         lock.lock();

         try {
            DynamicDispatchLibraryGen.CachedDispatch current = this;
            DynamicDispatchLibrary thisLibrary = this.library;
            if (thisLibrary == null) {
               this.library = this.insert(DynamicDispatchLibraryGen.INSTANCE.create(receiver_));
            } else {
               int count = 0;

               do {
                  DynamicDispatchLibrary currentLibrary = current.library;
                  if (currentLibrary != null && currentLibrary.accepts(receiver_)) {
                     return;
                  }

                  count++;
                  current = current.next;
               } while (current != null);

               if (count >= this.getLimit()) {
                  this.library = this.insert(new DynamicDispatchLibraryGen.CachedToUncachedDispatch());
                  this.next = null;
               } else {
                  this.next = this.insert(new DynamicDispatchLibraryGen.CachedDispatchNext(DynamicDispatchLibraryGen.INSTANCE.create(receiver_), this.next));
               }
            }
         } finally {
            lock.unlock();
         }
      }
   }

   @GeneratedBy(DynamicDispatchLibrary.class)
   private static final class CachedDispatchFirst extends DynamicDispatchLibraryGen.CachedDispatch {
      private final int limit_;

      CachedDispatchFirst(DynamicDispatchLibrary library, DynamicDispatchLibraryGen.CachedDispatch next, int limit_) {
         super(library, next);
         this.limit_ = limit_;
      }

      @Override
      int getLimit() {
         return this.limit_;
      }

      @Override
      public NodeCost getCost() {
         if (this.library instanceof DynamicDispatchLibraryGen.CachedToUncachedDispatch) {
            return NodeCost.MEGAMORPHIC;
         } else {
            DynamicDispatchLibraryGen.CachedDispatch current = this;
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

   @GeneratedBy(DynamicDispatchLibrary.class)
   private static final class CachedDispatchNext extends DynamicDispatchLibraryGen.CachedDispatch {
      CachedDispatchNext(DynamicDispatchLibrary library, DynamicDispatchLibraryGen.CachedDispatch next) {
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

   @GeneratedBy(DynamicDispatchLibrary.class)
   private static final class CachedToUncachedDispatch extends DynamicDispatchLibrary {
      @Override
      public NodeCost getCost() {
         return NodeCost.MEGAMORPHIC;
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public Class<?> dispatch(Object receiver_) {
         assert this.getRootNode() != null : "Invalid library usage. Cached library must be adopted by a RootNode before it is executed.";

         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this.getParent());

         Class var4;
         try {
            var4 = DynamicDispatchLibraryGen.INSTANCE.getUncached(receiver_).dispatch(receiver_);
         } finally {
            encapsulating_.set(prev_);
         }

         return var4;
      }

      @Override
      public boolean accepts(Object receiver_) {
         return true;
      }

      @Override
      public Object cast(Object receiver) {
         return receiver;
      }
   }

   @GeneratedBy(DynamicDispatchLibrary.class)
   private static final class Default extends LibraryExport<DynamicDispatchLibrary> {
      private Default() {
         super(DynamicDispatchLibrary.class, Object.class, false, false, 0);
      }

      protected DynamicDispatchLibrary createUncached(Object receiver) {
         DynamicDispatchLibrary uncached = new DynamicDispatchLibraryGen.Default.Uncached(receiver);
         return uncached;
      }

      protected DynamicDispatchLibrary createCached(Object receiver) {
         return new DynamicDispatchLibraryGen.Default.Cached(receiver);
      }

      @GeneratedBy(DynamicDispatchLibrary.class)
      private static final class Cached extends DynamicDispatchLibrary {
         private final Class<? extends Object> receiverClass_;

         protected Cached(Object receiver) {
            this.receiverClass_ = (Class<? extends Object>)receiver.getClass();
         }

         @Override
         public Object cast(Object receiver) {
            return CompilerDirectives.castExact(receiver, this.receiverClass_);
         }

         @Override
         public boolean accepts(Object receiver) {
            return CompilerDirectives.isExact(receiver, this.receiverClass_);
         }

         @Override
         public Class<?> dispatch(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.dispatch(CompilerDirectives.castExact(receiver, this.receiverClass_));
         }
      }

      @GeneratedBy(DynamicDispatchLibrary.class)
      @DenyReplace
      private static final class Uncached extends DynamicDispatchLibrary {
         protected Uncached(Object receiver) {
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean accepts(Object receiver) {
            return true;
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Object cast(Object receiver) {
            return receiver;
         }

         @Override
         public boolean isAdoptable() {
            return false;
         }

         @Override
         public NodeCost getCost() {
            return NodeCost.MEGAMORPHIC;
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Class<?> dispatch(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.dispatch(receiver);
         }
      }
   }

   @GeneratedBy(DynamicDispatchLibrary.class)
   private static final class Delegate extends DynamicDispatchLibrary {
      @Node.Child
      private DynamicDispatchLibrary delegateLibrary;

      Delegate(DynamicDispatchLibrary delegateLibrary) {
         this.delegateLibrary = delegateLibrary;
      }

      @Override
      public Object cast(Object receiver) {
         return this.delegateLibrary.cast(receiver);
      }

      @Override
      public Class<?> dispatch(Object receiver_) {
         if (LibraryFactory.isDelegated(this.delegateLibrary, 0)) {
            Object delegate = LibraryFactory.readDelegate(this.delegateLibrary, receiver_);
            return LibraryFactory.getDelegateLibrary(this.delegateLibrary, delegate).dispatch(delegate);
         } else {
            return this.delegateLibrary.dispatch(receiver_);
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

   @GeneratedBy(DynamicDispatchLibrary.class)
   private static class MessageImpl extends Message {
      MessageImpl(String name, int index, Class<?> returnType, Class<?>... parameters) {
         super(DynamicDispatchLibraryGen.LIBRARY_CLASS, name, index, returnType, parameters);
      }
   }

   @GeneratedBy(DynamicDispatchLibrary.class)
   private static final class Proxy extends DynamicDispatchLibrary {
      @Node.Child
      private ReflectionLibrary lib;

      Proxy(ReflectionLibrary lib) {
         this.lib = lib;
      }

      @Override
      public Object cast(Object receiver) {
         return receiver;
      }

      @Override
      public Class<?> dispatch(Object receiver_) {
         try {
            return (Class<?>)this.lib.send(receiver_, DynamicDispatchLibraryGen.DISPATCH);
         } catch (RuntimeException var3) {
            throw var3;
         } catch (Exception var4) {
            throw CompilerDirectives.shouldNotReachHere(var4);
         }
      }

      @Override
      public boolean accepts(Object receiver_) {
         return this.lib.accepts(receiver_);
      }
   }

   @GeneratedBy(DynamicDispatchLibrary.class)
   @DenyReplace
   private static final class UncachedDispatch extends DynamicDispatchLibrary {
      @Override
      public NodeCost getCost() {
         return NodeCost.MEGAMORPHIC;
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public Class<?> dispatch(Object receiver_) {
         return DynamicDispatchLibraryGen.INSTANCE.getUncached(receiver_).dispatch(receiver_);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public boolean accepts(Object receiver_) {
         return true;
      }

      @Override
      public Object cast(Object receiver) {
         return receiver;
      }

      @Override
      public boolean isAdoptable() {
         return false;
      }
   }
}
