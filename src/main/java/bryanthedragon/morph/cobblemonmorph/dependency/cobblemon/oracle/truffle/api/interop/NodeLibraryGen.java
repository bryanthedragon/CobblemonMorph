package com.oracle.truffle.api.interop;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.frame.Frame;
import com.oracle.truffle.api.library.DynamicDispatchLibrary;
import com.oracle.truffle.api.library.Library;
import com.oracle.truffle.api.library.LibraryExport;
import com.oracle.truffle.api.library.LibraryFactory;
import com.oracle.truffle.api.library.Message;
import com.oracle.truffle.api.library.ReflectionLibrary;
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

@GeneratedBy(NodeLibrary.class)
final class NodeLibraryGen extends LibraryFactory<NodeLibrary> {
   private static final Class<NodeLibrary> LIBRARY_CLASS = lazyLibraryClass();
   private static final Message HAS_SCOPE = new NodeLibraryGen.MessageImpl("hasScope", 0, boolean.class, Object.class, Frame.class);
   private static final Message GET_SCOPE = new NodeLibraryGen.MessageImpl("getScope", 1, Object.class, Object.class, Frame.class, boolean.class);
   private static final Message HAS_RECEIVER_MEMBER = new NodeLibraryGen.MessageImpl("hasReceiverMember", 2, boolean.class, Object.class, Frame.class);
   private static final Message GET_RECEIVER_MEMBER = new NodeLibraryGen.MessageImpl("getReceiverMember", 3, Object.class, Object.class, Frame.class);
   private static final Message HAS_ROOT_INSTANCE = new NodeLibraryGen.MessageImpl("hasRootInstance", 4, boolean.class, Object.class, Frame.class);
   private static final Message GET_ROOT_INSTANCE = new NodeLibraryGen.MessageImpl("getRootInstance", 5, Object.class, Object.class, Frame.class);
   private static final Message GET_VIEW = new NodeLibraryGen.MessageImpl("getView", 6, Object.class, Object.class, Frame.class, Object.class);
   private static final NodeLibraryGen INSTANCE = new NodeLibraryGen();
   private static final LibraryFactory<DynamicDispatchLibrary> DYNAMIC_DISPATCH_LIBRARY_ = LibraryFactory.resolve(DynamicDispatchLibrary.class);

   private NodeLibraryGen() {
      super(
         LIBRARY_CLASS,
         Collections.unmodifiableList(
            Arrays.asList(HAS_SCOPE, GET_SCOPE, HAS_RECEIVER_MEMBER, GET_RECEIVER_MEMBER, HAS_ROOT_INSTANCE, GET_ROOT_INSTANCE, GET_VIEW)
         )
      );
   }

   @Override
   protected Class<?> getDefaultClass(Object receiver) {
      return receiver instanceof Node ? DefaultNodeExports.class : NodeLibrary.class;
   }

   protected NodeLibrary createAssertions(NodeLibrary delegate) {
      return new NodeLibrary.Asserts(delegate);
   }

   protected NodeLibrary createProxy(ReflectionLibrary library) {
      return new NodeLibraryGen.Proxy(library);
   }

   @Override
   protected FinalBitSet createMessageBitSet(Message... messages) {
      BitSet bitSet = new BitSet(2);

      for (Message message : messages) {
         bitSet.set(message.getId());
      }

      return FinalBitSet.valueOf(bitSet);
   }

   protected NodeLibrary createDelegate(NodeLibrary delegateLibrary) {
      return new NodeLibraryGen.Delegate(delegateLibrary);
   }

   @Override
   protected Object genericDispatch(Library originalLib, Object receiver, Message message, Object[] args, int offset) throws Exception {
      NodeLibrary lib = (NodeLibrary)originalLib;
      if (message.getParameterCount() - 1 != args.length - offset) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         throw new IllegalArgumentException("Invalid number of arguments.");
      } else {
         switch (message.getId()) {
            case 0:
               return lib.hasScope(receiver, (Frame)args[offset]);
            case 1:
               return lib.getScope(receiver, (Frame)args[offset], (Boolean)args[offset + 1]);
            case 2:
               return lib.hasReceiverMember(receiver, (Frame)args[offset]);
            case 3:
               return lib.getReceiverMember(receiver, (Frame)args[offset]);
            case 4:
               return lib.hasRootInstance(receiver, (Frame)args[offset]);
            case 5:
               return lib.getRootInstance(receiver, (Frame)args[offset]);
            case 6:
               return lib.getView(receiver, (Frame)args[offset], args[offset + 1]);
            default:
               CompilerDirectives.transferToInterpreterAndInvalidate();
               throw new AbstractMethodError(message.toString());
         }
      }
   }

   protected NodeLibrary createDispatchImpl(int limit) {
      return new NodeLibraryGen.CachedDispatchFirst(null, null, limit);
   }

   protected NodeLibrary createUncachedDispatch() {
      return new NodeLibraryGen.UncachedDispatch();
   }

   private static Class<NodeLibrary> lazyLibraryClass() {
      try {
         return (Class<NodeLibrary>)Class.forName("com.oracle.truffle.api.interop.NodeLibrary", false, NodeLibraryGen.class.getClassLoader());
      } catch (ClassNotFoundException var1) {
         throw CompilerDirectives.shouldNotReachHere(var1);
      }
   }

   static {
      LibraryExport.register(LIBRARY_CLASS, new NodeLibraryGen.Default());
      LibraryFactory.register(LIBRARY_CLASS, INSTANCE);
   }

   @GeneratedBy(NodeLibrary.class)
   private abstract static class CachedDispatch extends NodeLibrary {
      @Node.Child
      NodeLibrary library;
      @Node.Child
      NodeLibraryGen.CachedDispatch next;

      CachedDispatch(NodeLibrary library, NodeLibraryGen.CachedDispatch next) {
         this.library = library;
         this.next = next;
      }

      abstract int getLimit();

      @ExplodeLoop
      @Override
      public boolean hasScope(Object receiver_, Frame frame) {
         while (true) {
            NodeLibraryGen.CachedDispatch current = this;

            do {
               NodeLibrary thisLibrary = current.library;
               if (thisLibrary != null && thisLibrary.accepts(receiver_)) {
                  return thisLibrary.hasScope(receiver_, frame);
               }

               current = current.next;
            } while (current != null);

            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.specialize(receiver_);
         }
      }

      @ExplodeLoop
      @Override
      public Object getScope(Object receiver_, Frame frame, boolean nodeEnter) throws UnsupportedMessageException {
         while (true) {
            NodeLibraryGen.CachedDispatch current = this;

            do {
               NodeLibrary thisLibrary = current.library;
               if (thisLibrary != null && thisLibrary.accepts(receiver_)) {
                  return thisLibrary.getScope(receiver_, frame, nodeEnter);
               }

               current = current.next;
            } while (current != null);

            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.specialize(receiver_);
         }
      }

      @ExplodeLoop
      @Override
      public boolean hasReceiverMember(Object receiver_, Frame frame) {
         while (true) {
            NodeLibraryGen.CachedDispatch current = this;

            do {
               NodeLibrary thisLibrary = current.library;
               if (thisLibrary != null && thisLibrary.accepts(receiver_)) {
                  return thisLibrary.hasReceiverMember(receiver_, frame);
               }

               current = current.next;
            } while (current != null);

            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.specialize(receiver_);
         }
      }

      @ExplodeLoop
      @Override
      public Object getReceiverMember(Object receiver_, Frame frame) throws UnsupportedMessageException {
         while (true) {
            NodeLibraryGen.CachedDispatch current = this;

            do {
               NodeLibrary thisLibrary = current.library;
               if (thisLibrary != null && thisLibrary.accepts(receiver_)) {
                  return thisLibrary.getReceiverMember(receiver_, frame);
               }

               current = current.next;
            } while (current != null);

            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.specialize(receiver_);
         }
      }

      @ExplodeLoop
      @Override
      public boolean hasRootInstance(Object receiver_, Frame frame) {
         while (true) {
            NodeLibraryGen.CachedDispatch current = this;

            do {
               NodeLibrary thisLibrary = current.library;
               if (thisLibrary != null && thisLibrary.accepts(receiver_)) {
                  return thisLibrary.hasRootInstance(receiver_, frame);
               }

               current = current.next;
            } while (current != null);

            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.specialize(receiver_);
         }
      }

      @ExplodeLoop
      @Override
      public Object getRootInstance(Object receiver_, Frame frame) throws UnsupportedMessageException {
         while (true) {
            NodeLibraryGen.CachedDispatch current = this;

            do {
               NodeLibrary thisLibrary = current.library;
               if (thisLibrary != null && thisLibrary.accepts(receiver_)) {
                  return thisLibrary.getRootInstance(receiver_, frame);
               }

               current = current.next;
            } while (current != null);

            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.specialize(receiver_);
         }
      }

      @ExplodeLoop
      @Override
      public Object getView(Object receiver_, Frame frame, Object value) {
         while (true) {
            NodeLibraryGen.CachedDispatch current = this;

            do {
               NodeLibrary thisLibrary = current.library;
               if (thisLibrary != null && thisLibrary.accepts(receiver_)) {
                  return thisLibrary.getView(receiver_, frame, value);
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
            NodeLibraryGen.CachedDispatch current = this;
            NodeLibrary thisLibrary = this.library;
            if (thisLibrary == null) {
               this.library = this.insert(NodeLibraryGen.INSTANCE.create(receiver_));
            } else {
               int count = 0;

               do {
                  NodeLibrary currentLibrary = current.library;
                  if (currentLibrary != null && currentLibrary.accepts(receiver_)) {
                     return;
                  }

                  count++;
                  current = current.next;
               } while (current != null);

               if (count >= this.getLimit()) {
                  this.library = this.insert(new NodeLibraryGen.CachedToUncachedDispatch());
                  this.next = null;
               } else {
                  this.next = this.insert(new NodeLibraryGen.CachedDispatchNext(NodeLibraryGen.INSTANCE.create(receiver_), this.next));
               }
            }
         } finally {
            lock.unlock();
         }
      }
   }

   @GeneratedBy(NodeLibrary.class)
   private static final class CachedDispatchFirst extends NodeLibraryGen.CachedDispatch {
      private final int limit_;

      CachedDispatchFirst(NodeLibrary library, NodeLibraryGen.CachedDispatch next, int limit_) {
         super(library, next);
         this.limit_ = limit_;
      }

      @Override
      int getLimit() {
         return this.limit_;
      }

      @Override
      public NodeCost getCost() {
         if (this.library instanceof NodeLibraryGen.CachedToUncachedDispatch) {
            return NodeCost.MEGAMORPHIC;
         } else {
            NodeLibraryGen.CachedDispatch current = this;
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

   @GeneratedBy(NodeLibrary.class)
   private static final class CachedDispatchNext extends NodeLibraryGen.CachedDispatch {
      CachedDispatchNext(NodeLibrary library, NodeLibraryGen.CachedDispatch next) {
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

   @GeneratedBy(NodeLibrary.class)
   private static final class CachedToUncachedDispatch extends NodeLibrary {
      @Override
      public NodeCost getCost() {
         return NodeCost.MEGAMORPHIC;
      }

      @Override
      public boolean hasScope(Object receiver_, Frame frame) {
         assert this.getRootNode() != null : "Invalid library usage. Cached library must be adopted by a RootNode before it is executed.";

         CompilerDirectives.transferToInterpreterAndInvalidate();
         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this.getParent());

         boolean var5;
         try {
            var5 = NodeLibraryGen.INSTANCE.getUncached(receiver_).hasScope(receiver_, frame);
         } finally {
            encapsulating_.set(prev_);
         }

         return var5;
      }

      @Override
      public Object getScope(Object receiver_, Frame frame, boolean nodeEnter) throws UnsupportedMessageException {
         assert this.getRootNode() != null : "Invalid library usage. Cached library must be adopted by a RootNode before it is executed.";

         CompilerDirectives.transferToInterpreterAndInvalidate();
         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this.getParent());

         Object var6;
         try {
            var6 = NodeLibraryGen.INSTANCE.getUncached(receiver_).getScope(receiver_, frame, nodeEnter);
         } finally {
            encapsulating_.set(prev_);
         }

         return var6;
      }

      @Override
      public boolean hasReceiverMember(Object receiver_, Frame frame) {
         assert this.getRootNode() != null : "Invalid library usage. Cached library must be adopted by a RootNode before it is executed.";

         CompilerDirectives.transferToInterpreterAndInvalidate();
         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this.getParent());

         boolean var5;
         try {
            var5 = NodeLibraryGen.INSTANCE.getUncached(receiver_).hasReceiverMember(receiver_, frame);
         } finally {
            encapsulating_.set(prev_);
         }

         return var5;
      }

      @Override
      public Object getReceiverMember(Object receiver_, Frame frame) throws UnsupportedMessageException {
         assert this.getRootNode() != null : "Invalid library usage. Cached library must be adopted by a RootNode before it is executed.";

         CompilerDirectives.transferToInterpreterAndInvalidate();
         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this.getParent());

         Object var5;
         try {
            var5 = NodeLibraryGen.INSTANCE.getUncached(receiver_).getReceiverMember(receiver_, frame);
         } finally {
            encapsulating_.set(prev_);
         }

         return var5;
      }

      @Override
      public boolean hasRootInstance(Object receiver_, Frame frame) {
         assert this.getRootNode() != null : "Invalid library usage. Cached library must be adopted by a RootNode before it is executed.";

         CompilerDirectives.transferToInterpreterAndInvalidate();
         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this.getParent());

         boolean var5;
         try {
            var5 = NodeLibraryGen.INSTANCE.getUncached(receiver_).hasRootInstance(receiver_, frame);
         } finally {
            encapsulating_.set(prev_);
         }

         return var5;
      }

      @Override
      public Object getRootInstance(Object receiver_, Frame frame) throws UnsupportedMessageException {
         assert this.getRootNode() != null : "Invalid library usage. Cached library must be adopted by a RootNode before it is executed.";

         CompilerDirectives.transferToInterpreterAndInvalidate();
         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this.getParent());

         Object var5;
         try {
            var5 = NodeLibraryGen.INSTANCE.getUncached(receiver_).getRootInstance(receiver_, frame);
         } finally {
            encapsulating_.set(prev_);
         }

         return var5;
      }

      @Override
      public Object getView(Object receiver_, Frame frame, Object value) {
         assert this.getRootNode() != null : "Invalid library usage. Cached library must be adopted by a RootNode before it is executed.";

         CompilerDirectives.transferToInterpreterAndInvalidate();
         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this.getParent());

         Object var6;
         try {
            var6 = NodeLibraryGen.INSTANCE.getUncached(receiver_).getView(receiver_, frame, value);
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

   @GeneratedBy(NodeLibrary.class)
   private static final class Default extends LibraryExport<NodeLibrary> {
      private Default() {
         super(NodeLibrary.class, Object.class, false, false, 0);
      }

      protected NodeLibrary createUncached(Object receiver) {
         NodeLibrary uncached = new NodeLibraryGen.Default.Uncached(receiver);
         return uncached;
      }

      protected NodeLibrary createCached(Object receiver) {
         return new NodeLibraryGen.Default.Cached(receiver);
      }

      @GeneratedBy(NodeLibrary.class)
      private static final class Cached extends NodeLibrary {
         @Node.Child
         private DynamicDispatchLibrary dynamicDispatch_;
         private final Class<?> dynamicDispatchTarget_;

         protected Cached(Object receiver) {
            this.dynamicDispatch_ = this.insert(NodeLibraryGen.DYNAMIC_DISPATCH_LIBRARY_.create(receiver));
            this.dynamicDispatchTarget_ = NodeLibraryGen.DYNAMIC_DISPATCH_LIBRARY_.getUncached(receiver).dispatch(receiver);
         }

         @Override
         public boolean accepts(Object receiver) {
            return this.dynamicDispatch_.accepts(receiver) && this.dynamicDispatch_.dispatch(receiver) == this.dynamicDispatchTarget_;
         }

         @Override
         public boolean hasScope(Object receiver, Frame frame) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.hasScope(this.dynamicDispatch_.cast(receiver), frame);
         }

         @Override
         public Object getScope(Object receiver, Frame frame, boolean nodeEnter) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.getScope(this.dynamicDispatch_.cast(receiver), frame, nodeEnter);
         }

         @Override
         public boolean hasReceiverMember(Object receiver, Frame frame) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.hasReceiverMember(this.dynamicDispatch_.cast(receiver), frame);
         }

         @Override
         public Object getReceiverMember(Object receiver, Frame frame) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.getReceiverMember(this.dynamicDispatch_.cast(receiver), frame);
         }

         @Override
         public boolean hasRootInstance(Object receiver, Frame frame) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.hasRootInstance(this.dynamicDispatch_.cast(receiver), frame);
         }

         @Override
         public Object getRootInstance(Object receiver, Frame frame) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.getRootInstance(this.dynamicDispatch_.cast(receiver), frame);
         }

         @Override
         public Object getView(Object receiver, Frame frame, Object value) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.getView(this.dynamicDispatch_.cast(receiver), frame, value);
         }
      }

      @GeneratedBy(NodeLibrary.class)
      @DenyReplace
      private static final class Uncached extends NodeLibrary {
         @Node.Child
         private DynamicDispatchLibrary dynamicDispatch_;
         private final Class<?> dynamicDispatchTarget_;

         protected Uncached(Object receiver) {
            this.dynamicDispatch_ = NodeLibraryGen.DYNAMIC_DISPATCH_LIBRARY_.getUncached(receiver);
            this.dynamicDispatchTarget_ = this.dynamicDispatch_.dispatch(receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean accepts(Object receiver) {
            return this.dynamicDispatch_.accepts(receiver) && this.dynamicDispatch_.dispatch(receiver) == this.dynamicDispatchTarget_;
         }

         @Override
         public boolean isAdoptable() {
            return false;
         }

         @Override
         public NodeCost getCost() {
            return NodeCost.MEGAMORPHIC;
         }

         @Override
         public boolean hasScope(Object receiver, Frame frame) {
            CompilerDirectives.transferToInterpreterAndInvalidate();

            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.hasScope(receiver, frame);
         }

         @Override
         public Object getScope(Object receiver, Frame frame, boolean nodeEnter) throws UnsupportedMessageException {
            CompilerDirectives.transferToInterpreterAndInvalidate();

            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.getScope(receiver, frame, nodeEnter);
         }

         @Override
         public boolean hasReceiverMember(Object receiver, Frame frame) {
            CompilerDirectives.transferToInterpreterAndInvalidate();

            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.hasReceiverMember(receiver, frame);
         }

         @Override
         public Object getReceiverMember(Object receiver, Frame frame) throws UnsupportedMessageException {
            CompilerDirectives.transferToInterpreterAndInvalidate();

            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.getReceiverMember(receiver, frame);
         }

         @Override
         public boolean hasRootInstance(Object receiver, Frame frame) {
            CompilerDirectives.transferToInterpreterAndInvalidate();

            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.hasRootInstance(receiver, frame);
         }

         @Override
         public Object getRootInstance(Object receiver, Frame frame) throws UnsupportedMessageException {
            CompilerDirectives.transferToInterpreterAndInvalidate();

            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.getRootInstance(receiver, frame);
         }

         @Override
         public Object getView(Object receiver, Frame frame, Object value) {
            CompilerDirectives.transferToInterpreterAndInvalidate();

            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.getView(receiver, frame, value);
         }
      }
   }

   @GeneratedBy(NodeLibrary.class)
   private static final class Delegate extends NodeLibrary {
      @Node.Child
      private NodeLibrary delegateLibrary;

      Delegate(NodeLibrary delegateLibrary) {
         this.delegateLibrary = delegateLibrary;
      }

      @Override
      public boolean hasScope(Object receiver_, Frame frame) {
         if (NodeLibraryGen.isDelegated(this.delegateLibrary, 0)) {
            Object delegate = NodeLibraryGen.readDelegate(this.delegateLibrary, receiver_);
            return NodeLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate).hasScope(delegate, frame);
         } else {
            return this.delegateLibrary.hasScope(receiver_, frame);
         }
      }

      @Override
      public Object getScope(Object receiver_, Frame frame, boolean nodeEnter) throws UnsupportedMessageException {
         if (NodeLibraryGen.isDelegated(this.delegateLibrary, 1)) {
            Object delegate = NodeLibraryGen.readDelegate(this.delegateLibrary, receiver_);
            return NodeLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate).getScope(delegate, frame, nodeEnter);
         } else {
            return this.delegateLibrary.getScope(receiver_, frame, nodeEnter);
         }
      }

      @Override
      public boolean hasReceiverMember(Object receiver_, Frame frame) {
         if (NodeLibraryGen.isDelegated(this.delegateLibrary, 2)) {
            Object delegate = NodeLibraryGen.readDelegate(this.delegateLibrary, receiver_);
            return NodeLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate).hasReceiverMember(delegate, frame);
         } else {
            return this.delegateLibrary.hasReceiverMember(receiver_, frame);
         }
      }

      @Override
      public Object getReceiverMember(Object receiver_, Frame frame) throws UnsupportedMessageException {
         if (NodeLibraryGen.isDelegated(this.delegateLibrary, 3)) {
            Object delegate = NodeLibraryGen.readDelegate(this.delegateLibrary, receiver_);
            return NodeLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate).getReceiverMember(delegate, frame);
         } else {
            return this.delegateLibrary.getReceiverMember(receiver_, frame);
         }
      }

      @Override
      public boolean hasRootInstance(Object receiver_, Frame frame) {
         if (NodeLibraryGen.isDelegated(this.delegateLibrary, 4)) {
            Object delegate = NodeLibraryGen.readDelegate(this.delegateLibrary, receiver_);
            return NodeLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate).hasRootInstance(delegate, frame);
         } else {
            return this.delegateLibrary.hasRootInstance(receiver_, frame);
         }
      }

      @Override
      public Object getRootInstance(Object receiver_, Frame frame) throws UnsupportedMessageException {
         if (NodeLibraryGen.isDelegated(this.delegateLibrary, 5)) {
            Object delegate = NodeLibraryGen.readDelegate(this.delegateLibrary, receiver_);
            return NodeLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate).getRootInstance(delegate, frame);
         } else {
            return this.delegateLibrary.getRootInstance(receiver_, frame);
         }
      }

      @Override
      public Object getView(Object receiver_, Frame frame, Object value) {
         if (NodeLibraryGen.isDelegated(this.delegateLibrary, 6)) {
            Object delegate = NodeLibraryGen.readDelegate(this.delegateLibrary, receiver_);
            return NodeLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate).getView(delegate, frame, value);
         } else {
            return this.delegateLibrary.getView(receiver_, frame, value);
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

   @GeneratedBy(NodeLibrary.class)
   private static class MessageImpl extends Message {
      MessageImpl(String name, int index, Class<?> returnType, Class<?>... parameters) {
         super(NodeLibraryGen.LIBRARY_CLASS, name, index, returnType, parameters);
      }
   }

   @GeneratedBy(NodeLibrary.class)
   private static final class Proxy extends NodeLibrary {
      @Node.Child
      private ReflectionLibrary lib;

      Proxy(ReflectionLibrary lib) {
         this.lib = lib;
      }

      @Override
      public boolean hasScope(Object receiver_, Frame frame) {
         try {
            return (Boolean)this.lib.send(receiver_, NodeLibraryGen.HAS_SCOPE, frame);
         } catch (RuntimeException var4) {
            throw var4;
         } catch (Exception var5) {
            throw CompilerDirectives.shouldNotReachHere(var5);
         }
      }

      @Override
      public Object getScope(Object receiver_, Frame frame, boolean nodeEnter) throws UnsupportedMessageException {
         try {
            return this.lib.send(receiver_, NodeLibraryGen.GET_SCOPE, frame, nodeEnter);
         } catch (RuntimeException | UnsupportedMessageException var5) {
            throw var5;
         } catch (Exception var6) {
            throw CompilerDirectives.shouldNotReachHere(var6);
         }
      }

      @Override
      public boolean hasReceiverMember(Object receiver_, Frame frame) {
         try {
            return (Boolean)this.lib.send(receiver_, NodeLibraryGen.HAS_RECEIVER_MEMBER, frame);
         } catch (RuntimeException var4) {
            throw var4;
         } catch (Exception var5) {
            throw CompilerDirectives.shouldNotReachHere(var5);
         }
      }

      @Override
      public Object getReceiverMember(Object receiver_, Frame frame) throws UnsupportedMessageException {
         try {
            return this.lib.send(receiver_, NodeLibraryGen.GET_RECEIVER_MEMBER, frame);
         } catch (RuntimeException | UnsupportedMessageException var4) {
            throw var4;
         } catch (Exception var5) {
            throw CompilerDirectives.shouldNotReachHere(var5);
         }
      }

      @Override
      public boolean hasRootInstance(Object receiver_, Frame frame) {
         try {
            return (Boolean)this.lib.send(receiver_, NodeLibraryGen.HAS_ROOT_INSTANCE, frame);
         } catch (RuntimeException var4) {
            throw var4;
         } catch (Exception var5) {
            throw CompilerDirectives.shouldNotReachHere(var5);
         }
      }

      @Override
      public Object getRootInstance(Object receiver_, Frame frame) throws UnsupportedMessageException {
         try {
            return this.lib.send(receiver_, NodeLibraryGen.GET_ROOT_INSTANCE, frame);
         } catch (RuntimeException | UnsupportedMessageException var4) {
            throw var4;
         } catch (Exception var5) {
            throw CompilerDirectives.shouldNotReachHere(var5);
         }
      }

      @Override
      public Object getView(Object receiver_, Frame frame, Object value) {
         try {
            return this.lib.send(receiver_, NodeLibraryGen.GET_VIEW, frame, value);
         } catch (RuntimeException var5) {
            throw var5;
         } catch (Exception var6) {
            throw CompilerDirectives.shouldNotReachHere(var6);
         }
      }

      @Override
      public boolean accepts(Object receiver_) {
         return this.lib.accepts(receiver_);
      }
   }

   @GeneratedBy(NodeLibrary.class)
   @DenyReplace
   private static final class UncachedDispatch extends NodeLibrary {
      @Override
      public NodeCost getCost() {
         return NodeCost.MEGAMORPHIC;
      }

      @Override
      public boolean hasScope(Object receiver_, Frame frame) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return NodeLibraryGen.INSTANCE.getUncached(receiver_).hasScope(receiver_, frame);
      }

      @Override
      public Object getScope(Object receiver_, Frame frame, boolean nodeEnter) throws UnsupportedMessageException {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return NodeLibraryGen.INSTANCE.getUncached(receiver_).getScope(receiver_, frame, nodeEnter);
      }

      @Override
      public boolean hasReceiverMember(Object receiver_, Frame frame) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return NodeLibraryGen.INSTANCE.getUncached(receiver_).hasReceiverMember(receiver_, frame);
      }

      @Override
      public Object getReceiverMember(Object receiver_, Frame frame) throws UnsupportedMessageException {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return NodeLibraryGen.INSTANCE.getUncached(receiver_).getReceiverMember(receiver_, frame);
      }

      @Override
      public boolean hasRootInstance(Object receiver_, Frame frame) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return NodeLibraryGen.INSTANCE.getUncached(receiver_).hasRootInstance(receiver_, frame);
      }

      @Override
      public Object getRootInstance(Object receiver_, Frame frame) throws UnsupportedMessageException {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return NodeLibraryGen.INSTANCE.getUncached(receiver_).getRootInstance(receiver_, frame);
      }

      @Override
      public Object getView(Object receiver_, Frame frame, Object value) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return NodeLibraryGen.INSTANCE.getUncached(receiver_).getView(receiver_, frame, value);
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
