package com.oracle.truffle.api.object;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.library.Library;
import com.oracle.truffle.api.library.LibraryExport;
import com.oracle.truffle.api.library.LibraryFactory;
import com.oracle.truffle.api.library.Message;
import com.oracle.truffle.api.library.ReflectionLibrary;
import com.oracle.truffle.api.nodes.DenyReplace;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.api.utilities.FinalBitSet;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Collections;
import java.util.concurrent.locks.Lock;

@GeneratedBy(DynamicObjectLibrary.class)
final class DynamicObjectLibraryGen extends LibraryFactory<DynamicObjectLibrary> {
   private static final Class<DynamicObjectLibrary> LIBRARY_CLASS = lazyLibraryClass();
   private static final Message GET_SHAPE = new DynamicObjectLibraryGen.MessageImpl("getShape", 0, Shape.class, DynamicObject.class);
   private static final Message GET_OR_DEFAULT = new DynamicObjectLibraryGen.MessageImpl(
      "getOrDefault", 1, Object.class, DynamicObject.class, Object.class, Object.class
   );
   private static final Message GET_INT_OR_DEFAULT = new DynamicObjectLibraryGen.MessageImpl(
      "getIntOrDefault", 2, int.class, DynamicObject.class, Object.class, Object.class
   );
   private static final Message GET_DOUBLE_OR_DEFAULT = new DynamicObjectLibraryGen.MessageImpl(
      "getDoubleOrDefault", 3, double.class, DynamicObject.class, Object.class, Object.class
   );
   private static final Message GET_LONG_OR_DEFAULT = new DynamicObjectLibraryGen.MessageImpl(
      "getLongOrDefault", 4, long.class, DynamicObject.class, Object.class, Object.class
   );
   private static final Message PUT = new DynamicObjectLibraryGen.MessageImpl("put", 5, void.class, DynamicObject.class, Object.class, Object.class);
   private static final Message PUT_INT = new DynamicObjectLibraryGen.MessageImpl("putInt", 6, void.class, DynamicObject.class, Object.class, int.class);
   private static final Message PUT_DOUBLE = new DynamicObjectLibraryGen.MessageImpl(
      "putDouble", 7, void.class, DynamicObject.class, Object.class, double.class
   );
   private static final Message PUT_LONG = new DynamicObjectLibraryGen.MessageImpl("putLong", 8, void.class, DynamicObject.class, Object.class, long.class);
   private static final Message PUT_IF_PRESENT = new DynamicObjectLibraryGen.MessageImpl(
      "putIfPresent", 9, boolean.class, DynamicObject.class, Object.class, Object.class
   );
   private static final Message PUT_WITH_FLAGS = new DynamicObjectLibraryGen.MessageImpl(
      "putWithFlags", 10, void.class, DynamicObject.class, Object.class, Object.class, int.class
   );
   private static final Message PUT_CONSTANT = new DynamicObjectLibraryGen.MessageImpl(
      "putConstant", 11, void.class, DynamicObject.class, Object.class, Object.class, int.class
   );
   private static final Message REMOVE_KEY = new DynamicObjectLibraryGen.MessageImpl("removeKey", 12, boolean.class, DynamicObject.class, Object.class);
   private static final Message SET_DYNAMIC_TYPE = new DynamicObjectLibraryGen.MessageImpl(
      "setDynamicType", 13, boolean.class, DynamicObject.class, Object.class
   );
   private static final Message GET_DYNAMIC_TYPE = new DynamicObjectLibraryGen.MessageImpl("getDynamicType", 14, Object.class, DynamicObject.class);
   private static final Message CONTAINS_KEY = new DynamicObjectLibraryGen.MessageImpl("containsKey", 15, boolean.class, DynamicObject.class, Object.class);
   private static final Message GET_SHAPE_FLAGS = new DynamicObjectLibraryGen.MessageImpl("getShapeFlags", 16, int.class, DynamicObject.class);
   private static final Message SET_SHAPE_FLAGS = new DynamicObjectLibraryGen.MessageImpl("setShapeFlags", 17, boolean.class, DynamicObject.class, int.class);
   private static final Message GET_PROPERTY = new DynamicObjectLibraryGen.MessageImpl("getProperty", 18, Property.class, DynamicObject.class, Object.class);
   private static final Message SET_PROPERTY_FLAGS = new DynamicObjectLibraryGen.MessageImpl(
      "setPropertyFlags", 19, boolean.class, DynamicObject.class, Object.class, int.class
   );
   private static final Message MARK_SHARED = new DynamicObjectLibraryGen.MessageImpl("markShared", 20, void.class, DynamicObject.class);
   private static final Message IS_SHARED = new DynamicObjectLibraryGen.MessageImpl("isShared", 21, boolean.class, DynamicObject.class);
   private static final Message UPDATE_SHAPE = new DynamicObjectLibraryGen.MessageImpl("updateShape", 22, boolean.class, DynamicObject.class);
   private static final Message RESET_SHAPE = new DynamicObjectLibraryGen.MessageImpl("resetShape", 23, boolean.class, DynamicObject.class, Shape.class);
   private static final Message GET_KEY_ARRAY = new DynamicObjectLibraryGen.MessageImpl("getKeyArray", 24, Object[].class, DynamicObject.class);
   private static final Message GET_PROPERTY_ARRAY = new DynamicObjectLibraryGen.MessageImpl("getPropertyArray", 25, Property[].class, DynamicObject.class);
   private static final DynamicObjectLibraryGen INSTANCE = new DynamicObjectLibraryGen();

   private DynamicObjectLibraryGen() {
      super(
         LIBRARY_CLASS,
         Collections.unmodifiableList(
            Arrays.asList(
               GET_SHAPE,
               GET_OR_DEFAULT,
               GET_INT_OR_DEFAULT,
               GET_DOUBLE_OR_DEFAULT,
               GET_LONG_OR_DEFAULT,
               PUT,
               PUT_INT,
               PUT_DOUBLE,
               PUT_LONG,
               PUT_IF_PRESENT,
               PUT_WITH_FLAGS,
               PUT_CONSTANT,
               REMOVE_KEY,
               SET_DYNAMIC_TYPE,
               GET_DYNAMIC_TYPE,
               CONTAINS_KEY,
               GET_SHAPE_FLAGS,
               SET_SHAPE_FLAGS,
               GET_PROPERTY,
               SET_PROPERTY_FLAGS,
               MARK_SHARED,
               IS_SHARED,
               UPDATE_SHAPE,
               RESET_SHAPE,
               GET_KEY_ARRAY,
               GET_PROPERTY_ARRAY
            )
         )
      );
   }

   @Override
   protected Class<?> getDefaultClass(Object receiver) {
      return DynamicObjectLibrary.class;
   }

   protected DynamicObjectLibrary createProxy(ReflectionLibrary library) {
      return new DynamicObjectLibraryGen.Proxy(library);
   }

   @Override
   protected FinalBitSet createMessageBitSet(Message... messages) {
      BitSet bitSet = new BitSet(2);

      for (Message message : messages) {
         bitSet.set(message.getId());
      }

      return FinalBitSet.valueOf(bitSet);
   }

   protected DynamicObjectLibrary createDelegate(DynamicObjectLibrary delegateLibrary) {
      return new DynamicObjectLibraryGen.Delegate(delegateLibrary);
   }

   @Override
   protected Object genericDispatch(Library originalLib, Object receiver, Message message, Object[] args, int offset) throws Exception {
      DynamicObjectLibrary lib = (DynamicObjectLibrary)originalLib;
      if (message.getParameterCount() - 1 != args.length - offset) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         throw new IllegalArgumentException("Invalid number of arguments.");
      } else {
         switch (message.getId()) {
            case 0:
               return lib.getShape((DynamicObject)receiver);
            case 1:
               return lib.getOrDefault((DynamicObject)receiver, args[offset], args[offset + 1]);
            case 2:
               return lib.getIntOrDefault((DynamicObject)receiver, args[offset], args[offset + 1]);
            case 3:
               return lib.getDoubleOrDefault((DynamicObject)receiver, args[offset], args[offset + 1]);
            case 4:
               return lib.getLongOrDefault((DynamicObject)receiver, args[offset], args[offset + 1]);
            case 5:
               lib.put((DynamicObject)receiver, args[offset], args[offset + 1]);
               return null;
            case 6:
               lib.putInt((DynamicObject)receiver, args[offset], (Integer)args[offset + 1]);
               return null;
            case 7:
               lib.putDouble((DynamicObject)receiver, args[offset], (Double)args[offset + 1]);
               return null;
            case 8:
               lib.putLong((DynamicObject)receiver, args[offset], (Long)args[offset + 1]);
               return null;
            case 9:
               return lib.putIfPresent((DynamicObject)receiver, args[offset], args[offset + 1]);
            case 10:
               lib.putWithFlags((DynamicObject)receiver, args[offset], args[offset + 1], (Integer)args[offset + 2]);
               return null;
            case 11:
               lib.putConstant((DynamicObject)receiver, args[offset], args[offset + 1], (Integer)args[offset + 2]);
               return null;
            case 12:
               return lib.removeKey((DynamicObject)receiver, args[offset]);
            case 13:
               return lib.setDynamicType((DynamicObject)receiver, args[offset]);
            case 14:
               return lib.getDynamicType((DynamicObject)receiver);
            case 15:
               return lib.containsKey((DynamicObject)receiver, args[offset]);
            case 16:
               return lib.getShapeFlags((DynamicObject)receiver);
            case 17:
               return lib.setShapeFlags((DynamicObject)receiver, (Integer)args[offset]);
            case 18:
               return lib.getProperty((DynamicObject)receiver, args[offset]);
            case 19:
               return lib.setPropertyFlags((DynamicObject)receiver, args[offset], (Integer)args[offset + 1]);
            case 20:
               lib.markShared((DynamicObject)receiver);
               return null;
            case 21:
               return lib.isShared((DynamicObject)receiver);
            case 22:
               return lib.updateShape((DynamicObject)receiver);
            case 23:
               return lib.resetShape((DynamicObject)receiver, (Shape)args[offset]);
            case 24:
               return lib.getKeyArray((DynamicObject)receiver);
            case 25:
               return lib.getPropertyArray((DynamicObject)receiver);
            default:
               CompilerDirectives.transferToInterpreterAndInvalidate();
               throw new AbstractMethodError(message.toString());
         }
      }
   }

   protected DynamicObjectLibrary createDispatchImpl(int limit) {
      return new DynamicObjectLibraryGen.CachedDispatchFirst(null, null, limit);
   }

   protected DynamicObjectLibrary createUncachedDispatch() {
      return new DynamicObjectLibraryGen.UncachedDispatch();
   }

   private static Class<DynamicObjectLibrary> lazyLibraryClass() {
      try {
         return (Class<DynamicObjectLibrary>)Class.forName(
            "com.oracle.truffle.api.object.DynamicObjectLibrary", false, DynamicObjectLibraryGen.class.getClassLoader()
         );
      } catch (ClassNotFoundException var1) {
         throw CompilerDirectives.shouldNotReachHere(var1);
      }
   }

   static {
      LibraryExport.register(LIBRARY_CLASS, new DynamicObjectLibraryGen.Default());
      LibraryFactory.register(LIBRARY_CLASS, INSTANCE);
   }

   @GeneratedBy(DynamicObjectLibrary.class)
   private abstract static class CachedDispatch extends DynamicObjectLibrary {
      @Node.Child
      DynamicObjectLibrary library;
      @Node.Child
      DynamicObjectLibraryGen.CachedDispatch next;

      CachedDispatch(DynamicObjectLibrary library, DynamicObjectLibraryGen.CachedDispatch next) {
         this.library = library;
         this.next = next;
      }

      abstract int getLimit();

      @ExplodeLoop
      @Override
      public Shape getShape(DynamicObject receiver_) {
         while (true) {
            DynamicObjectLibraryGen.CachedDispatch current = this;

            do {
               DynamicObjectLibrary thisLibrary = current.library;
               if (thisLibrary != null && thisLibrary.accepts(receiver_)) {
                  return thisLibrary.getShape(receiver_);
               }

               current = current.next;
            } while (current != null);

            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.specialize(receiver_);
         }
      }

      @ExplodeLoop
      @Override
      public Object getOrDefault(DynamicObject receiver_, Object key, Object defaultValue) {
         while (true) {
            DynamicObjectLibraryGen.CachedDispatch current = this;

            do {
               DynamicObjectLibrary thisLibrary = current.library;
               if (thisLibrary != null && thisLibrary.accepts(receiver_)) {
                  return thisLibrary.getOrDefault(receiver_, key, defaultValue);
               }

               current = current.next;
            } while (current != null);

            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.specialize(receiver_);
         }
      }

      @ExplodeLoop
      @Override
      public int getIntOrDefault(DynamicObject receiver_, Object key, Object defaultValue) throws UnexpectedResultException {
         while (true) {
            DynamicObjectLibraryGen.CachedDispatch current = this;

            do {
               DynamicObjectLibrary thisLibrary = current.library;
               if (thisLibrary != null && thisLibrary.accepts(receiver_)) {
                  return thisLibrary.getIntOrDefault(receiver_, key, defaultValue);
               }

               current = current.next;
            } while (current != null);

            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.specialize(receiver_);
         }
      }

      @ExplodeLoop
      @Override
      public double getDoubleOrDefault(DynamicObject receiver_, Object key, Object defaultValue) throws UnexpectedResultException {
         while (true) {
            DynamicObjectLibraryGen.CachedDispatch current = this;

            do {
               DynamicObjectLibrary thisLibrary = current.library;
               if (thisLibrary != null && thisLibrary.accepts(receiver_)) {
                  return thisLibrary.getDoubleOrDefault(receiver_, key, defaultValue);
               }

               current = current.next;
            } while (current != null);

            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.specialize(receiver_);
         }
      }

      @ExplodeLoop
      @Override
      public long getLongOrDefault(DynamicObject receiver_, Object key, Object defaultValue) throws UnexpectedResultException {
         while (true) {
            DynamicObjectLibraryGen.CachedDispatch current = this;

            do {
               DynamicObjectLibrary thisLibrary = current.library;
               if (thisLibrary != null && thisLibrary.accepts(receiver_)) {
                  return thisLibrary.getLongOrDefault(receiver_, key, defaultValue);
               }

               current = current.next;
            } while (current != null);

            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.specialize(receiver_);
         }
      }

      @ExplodeLoop
      @Override
      public void put(DynamicObject receiver_, Object key, Object value) {
         while (true) {
            DynamicObjectLibraryGen.CachedDispatch current = this;

            do {
               DynamicObjectLibrary thisLibrary = current.library;
               if (thisLibrary != null && thisLibrary.accepts(receiver_)) {
                  thisLibrary.put(receiver_, key, value);
                  return;
               }

               current = current.next;
            } while (current != null);

            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.specialize(receiver_);
         }
      }

      @ExplodeLoop
      @Override
      public void putInt(DynamicObject receiver_, Object key, int value) {
         while (true) {
            DynamicObjectLibraryGen.CachedDispatch current = this;

            do {
               DynamicObjectLibrary thisLibrary = current.library;
               if (thisLibrary != null && thisLibrary.accepts(receiver_)) {
                  thisLibrary.putInt(receiver_, key, value);
                  return;
               }

               current = current.next;
            } while (current != null);

            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.specialize(receiver_);
         }
      }

      @ExplodeLoop
      @Override
      public void putDouble(DynamicObject receiver_, Object key, double value) {
         while (true) {
            DynamicObjectLibraryGen.CachedDispatch current = this;

            do {
               DynamicObjectLibrary thisLibrary = current.library;
               if (thisLibrary != null && thisLibrary.accepts(receiver_)) {
                  thisLibrary.putDouble(receiver_, key, value);
                  return;
               }

               current = current.next;
            } while (current != null);

            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.specialize(receiver_);
         }
      }

      @ExplodeLoop
      @Override
      public void putLong(DynamicObject receiver_, Object key, long value) {
         while (true) {
            DynamicObjectLibraryGen.CachedDispatch current = this;

            do {
               DynamicObjectLibrary thisLibrary = current.library;
               if (thisLibrary != null && thisLibrary.accepts(receiver_)) {
                  thisLibrary.putLong(receiver_, key, value);
                  return;
               }

               current = current.next;
            } while (current != null);

            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.specialize(receiver_);
         }
      }

      @ExplodeLoop
      @Override
      public boolean putIfPresent(DynamicObject receiver_, Object key, Object value) {
         while (true) {
            DynamicObjectLibraryGen.CachedDispatch current = this;

            do {
               DynamicObjectLibrary thisLibrary = current.library;
               if (thisLibrary != null && thisLibrary.accepts(receiver_)) {
                  return thisLibrary.putIfPresent(receiver_, key, value);
               }

               current = current.next;
            } while (current != null);

            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.specialize(receiver_);
         }
      }

      @ExplodeLoop
      @Override
      public void putWithFlags(DynamicObject receiver_, Object key, Object value, int flags) {
         while (true) {
            DynamicObjectLibraryGen.CachedDispatch current = this;

            do {
               DynamicObjectLibrary thisLibrary = current.library;
               if (thisLibrary != null && thisLibrary.accepts(receiver_)) {
                  thisLibrary.putWithFlags(receiver_, key, value, flags);
                  return;
               }

               current = current.next;
            } while (current != null);

            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.specialize(receiver_);
         }
      }

      @ExplodeLoop
      @Override
      public void putConstant(DynamicObject receiver_, Object key, Object value, int flags) {
         while (true) {
            DynamicObjectLibraryGen.CachedDispatch current = this;

            do {
               DynamicObjectLibrary thisLibrary = current.library;
               if (thisLibrary != null && thisLibrary.accepts(receiver_)) {
                  thisLibrary.putConstant(receiver_, key, value, flags);
                  return;
               }

               current = current.next;
            } while (current != null);

            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.specialize(receiver_);
         }
      }

      @ExplodeLoop
      @Override
      public boolean removeKey(DynamicObject receiver_, Object key) {
         while (true) {
            DynamicObjectLibraryGen.CachedDispatch current = this;

            do {
               DynamicObjectLibrary thisLibrary = current.library;
               if (thisLibrary != null && thisLibrary.accepts(receiver_)) {
                  return thisLibrary.removeKey(receiver_, key);
               }

               current = current.next;
            } while (current != null);

            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.specialize(receiver_);
         }
      }

      @ExplodeLoop
      @Override
      public boolean setDynamicType(DynamicObject receiver_, Object type) {
         while (true) {
            DynamicObjectLibraryGen.CachedDispatch current = this;

            do {
               DynamicObjectLibrary thisLibrary = current.library;
               if (thisLibrary != null && thisLibrary.accepts(receiver_)) {
                  return thisLibrary.setDynamicType(receiver_, type);
               }

               current = current.next;
            } while (current != null);

            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.specialize(receiver_);
         }
      }

      @ExplodeLoop
      @Override
      public Object getDynamicType(DynamicObject receiver_) {
         while (true) {
            DynamicObjectLibraryGen.CachedDispatch current = this;

            do {
               DynamicObjectLibrary thisLibrary = current.library;
               if (thisLibrary != null && thisLibrary.accepts(receiver_)) {
                  return thisLibrary.getDynamicType(receiver_);
               }

               current = current.next;
            } while (current != null);

            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.specialize(receiver_);
         }
      }

      @ExplodeLoop
      @Override
      public boolean containsKey(DynamicObject receiver_, Object key) {
         while (true) {
            DynamicObjectLibraryGen.CachedDispatch current = this;

            do {
               DynamicObjectLibrary thisLibrary = current.library;
               if (thisLibrary != null && thisLibrary.accepts(receiver_)) {
                  return thisLibrary.containsKey(receiver_, key);
               }

               current = current.next;
            } while (current != null);

            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.specialize(receiver_);
         }
      }

      @ExplodeLoop
      @Override
      public int getShapeFlags(DynamicObject receiver_) {
         while (true) {
            DynamicObjectLibraryGen.CachedDispatch current = this;

            do {
               DynamicObjectLibrary thisLibrary = current.library;
               if (thisLibrary != null && thisLibrary.accepts(receiver_)) {
                  return thisLibrary.getShapeFlags(receiver_);
               }

               current = current.next;
            } while (current != null);

            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.specialize(receiver_);
         }
      }

      @ExplodeLoop
      @Override
      public boolean setShapeFlags(DynamicObject receiver_, int flags) {
         while (true) {
            DynamicObjectLibraryGen.CachedDispatch current = this;

            do {
               DynamicObjectLibrary thisLibrary = current.library;
               if (thisLibrary != null && thisLibrary.accepts(receiver_)) {
                  return thisLibrary.setShapeFlags(receiver_, flags);
               }

               current = current.next;
            } while (current != null);

            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.specialize(receiver_);
         }
      }

      @ExplodeLoop
      @Override
      public Property getProperty(DynamicObject receiver_, Object key) {
         while (true) {
            DynamicObjectLibraryGen.CachedDispatch current = this;

            do {
               DynamicObjectLibrary thisLibrary = current.library;
               if (thisLibrary != null && thisLibrary.accepts(receiver_)) {
                  return thisLibrary.getProperty(receiver_, key);
               }

               current = current.next;
            } while (current != null);

            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.specialize(receiver_);
         }
      }

      @ExplodeLoop
      @Override
      public boolean setPropertyFlags(DynamicObject receiver_, Object key, int propertyFlags) {
         while (true) {
            DynamicObjectLibraryGen.CachedDispatch current = this;

            do {
               DynamicObjectLibrary thisLibrary = current.library;
               if (thisLibrary != null && thisLibrary.accepts(receiver_)) {
                  return thisLibrary.setPropertyFlags(receiver_, key, propertyFlags);
               }

               current = current.next;
            } while (current != null);

            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.specialize(receiver_);
         }
      }

      @ExplodeLoop
      @Override
      public void markShared(DynamicObject receiver_) {
         while (true) {
            DynamicObjectLibraryGen.CachedDispatch current = this;

            do {
               DynamicObjectLibrary thisLibrary = current.library;
               if (thisLibrary != null && thisLibrary.accepts(receiver_)) {
                  thisLibrary.markShared(receiver_);
                  return;
               }

               current = current.next;
            } while (current != null);

            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.specialize(receiver_);
         }
      }

      @ExplodeLoop
      @Override
      public boolean isShared(DynamicObject receiver_) {
         while (true) {
            DynamicObjectLibraryGen.CachedDispatch current = this;

            do {
               DynamicObjectLibrary thisLibrary = current.library;
               if (thisLibrary != null && thisLibrary.accepts(receiver_)) {
                  return thisLibrary.isShared(receiver_);
               }

               current = current.next;
            } while (current != null);

            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.specialize(receiver_);
         }
      }

      @ExplodeLoop
      @Override
      public boolean updateShape(DynamicObject receiver_) {
         while (true) {
            DynamicObjectLibraryGen.CachedDispatch current = this;

            do {
               DynamicObjectLibrary thisLibrary = current.library;
               if (thisLibrary != null && thisLibrary.accepts(receiver_)) {
                  return thisLibrary.updateShape(receiver_);
               }

               current = current.next;
            } while (current != null);

            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.specialize(receiver_);
         }
      }

      @ExplodeLoop
      @Override
      public boolean resetShape(DynamicObject receiver_, Shape otherShape) {
         while (true) {
            DynamicObjectLibraryGen.CachedDispatch current = this;

            do {
               DynamicObjectLibrary thisLibrary = current.library;
               if (thisLibrary != null && thisLibrary.accepts(receiver_)) {
                  return thisLibrary.resetShape(receiver_, otherShape);
               }

               current = current.next;
            } while (current != null);

            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.specialize(receiver_);
         }
      }

      @ExplodeLoop
      @Override
      public Object[] getKeyArray(DynamicObject receiver_) {
         while (true) {
            DynamicObjectLibraryGen.CachedDispatch current = this;

            do {
               DynamicObjectLibrary thisLibrary = current.library;
               if (thisLibrary != null && thisLibrary.accepts(receiver_)) {
                  return thisLibrary.getKeyArray(receiver_);
               }

               current = current.next;
            } while (current != null);

            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.specialize(receiver_);
         }
      }

      @ExplodeLoop
      @Override
      public Property[] getPropertyArray(DynamicObject receiver_) {
         while (true) {
            DynamicObjectLibraryGen.CachedDispatch current = this;

            do {
               DynamicObjectLibrary thisLibrary = current.library;
               if (thisLibrary != null && thisLibrary.accepts(receiver_)) {
                  return thisLibrary.getPropertyArray(receiver_);
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

      private void specialize(DynamicObject receiver_) {
         Lock lock = this.getLock();
         lock.lock();

         try {
            DynamicObjectLibraryGen.CachedDispatch current = this;
            DynamicObjectLibrary thisLibrary = this.library;
            if (thisLibrary == null) {
               this.library = this.insert(DynamicObjectLibraryGen.INSTANCE.create(receiver_));
            } else {
               int count = 0;

               do {
                  DynamicObjectLibrary currentLibrary = current.library;
                  if (currentLibrary != null && currentLibrary.accepts(receiver_)) {
                     return;
                  }

                  count++;
                  current = current.next;
               } while (current != null);

               if (count >= this.getLimit()) {
                  this.library = this.insert(new DynamicObjectLibraryGen.CachedToUncachedDispatch());
                  this.next = null;
               } else {
                  this.next = this.insert(new DynamicObjectLibraryGen.CachedDispatchNext(DynamicObjectLibraryGen.INSTANCE.create(receiver_), this.next));
               }
            }
         } finally {
            lock.unlock();
         }
      }
   }

   @GeneratedBy(DynamicObjectLibrary.class)
   private static final class CachedDispatchFirst extends DynamicObjectLibraryGen.CachedDispatch {
      private final int limit_;

      CachedDispatchFirst(DynamicObjectLibrary library, DynamicObjectLibraryGen.CachedDispatch next, int limit_) {
         super(library, next);
         this.limit_ = limit_;
      }

      @Override
      int getLimit() {
         return this.limit_;
      }

      @Override
      public NodeCost getCost() {
         if (this.library instanceof DynamicObjectLibraryGen.CachedToUncachedDispatch) {
            return NodeCost.MEGAMORPHIC;
         } else {
            DynamicObjectLibraryGen.CachedDispatch current = this;
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

   @GeneratedBy(DynamicObjectLibrary.class)
   private static final class CachedDispatchNext extends DynamicObjectLibraryGen.CachedDispatch {
      CachedDispatchNext(DynamicObjectLibrary library, DynamicObjectLibraryGen.CachedDispatch next) {
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

   @GeneratedBy(DynamicObjectLibrary.class)
   private static final class CachedToUncachedDispatch extends DynamicObjectLibrary {
      @Override
      public NodeCost getCost() {
         return NodeCost.MEGAMORPHIC;
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public Shape getShape(DynamicObject receiver_) {
         assert this.getRootNode() != null : "Invalid library usage. Cached library must be adopted by a RootNode before it is executed.";

         return DynamicObjectLibraryGen.INSTANCE.getUncached(receiver_).getShape(receiver_);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public Object getOrDefault(DynamicObject receiver_, Object key, Object defaultValue) {
         assert this.getRootNode() != null : "Invalid library usage. Cached library must be adopted by a RootNode before it is executed.";

         return DynamicObjectLibraryGen.INSTANCE.getUncached(receiver_).getOrDefault(receiver_, key, defaultValue);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public int getIntOrDefault(DynamicObject receiver_, Object key, Object defaultValue) throws UnexpectedResultException {
         assert this.getRootNode() != null : "Invalid library usage. Cached library must be adopted by a RootNode before it is executed.";

         return DynamicObjectLibraryGen.INSTANCE.getUncached(receiver_).getIntOrDefault(receiver_, key, defaultValue);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public double getDoubleOrDefault(DynamicObject receiver_, Object key, Object defaultValue) throws UnexpectedResultException {
         assert this.getRootNode() != null : "Invalid library usage. Cached library must be adopted by a RootNode before it is executed.";

         return DynamicObjectLibraryGen.INSTANCE.getUncached(receiver_).getDoubleOrDefault(receiver_, key, defaultValue);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public long getLongOrDefault(DynamicObject receiver_, Object key, Object defaultValue) throws UnexpectedResultException {
         assert this.getRootNode() != null : "Invalid library usage. Cached library must be adopted by a RootNode before it is executed.";

         return DynamicObjectLibraryGen.INSTANCE.getUncached(receiver_).getLongOrDefault(receiver_, key, defaultValue);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public void put(DynamicObject receiver_, Object key, Object value) {
         assert this.getRootNode() != null : "Invalid library usage. Cached library must be adopted by a RootNode before it is executed.";

         DynamicObjectLibraryGen.INSTANCE.getUncached(receiver_).put(receiver_, key, value);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public void putInt(DynamicObject receiver_, Object key, int value) {
         assert this.getRootNode() != null : "Invalid library usage. Cached library must be adopted by a RootNode before it is executed.";

         DynamicObjectLibraryGen.INSTANCE.getUncached(receiver_).putInt(receiver_, key, value);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public void putDouble(DynamicObject receiver_, Object key, double value) {
         assert this.getRootNode() != null : "Invalid library usage. Cached library must be adopted by a RootNode before it is executed.";

         DynamicObjectLibraryGen.INSTANCE.getUncached(receiver_).putDouble(receiver_, key, value);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public void putLong(DynamicObject receiver_, Object key, long value) {
         assert this.getRootNode() != null : "Invalid library usage. Cached library must be adopted by a RootNode before it is executed.";

         DynamicObjectLibraryGen.INSTANCE.getUncached(receiver_).putLong(receiver_, key, value);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public boolean putIfPresent(DynamicObject receiver_, Object key, Object value) {
         assert this.getRootNode() != null : "Invalid library usage. Cached library must be adopted by a RootNode before it is executed.";

         return DynamicObjectLibraryGen.INSTANCE.getUncached(receiver_).putIfPresent(receiver_, key, value);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public void putWithFlags(DynamicObject receiver_, Object key, Object value, int flags) {
         assert this.getRootNode() != null : "Invalid library usage. Cached library must be adopted by a RootNode before it is executed.";

         DynamicObjectLibraryGen.INSTANCE.getUncached(receiver_).putWithFlags(receiver_, key, value, flags);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public void putConstant(DynamicObject receiver_, Object key, Object value, int flags) {
         assert this.getRootNode() != null : "Invalid library usage. Cached library must be adopted by a RootNode before it is executed.";

         DynamicObjectLibraryGen.INSTANCE.getUncached(receiver_).putConstant(receiver_, key, value, flags);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public boolean removeKey(DynamicObject receiver_, Object key) {
         assert this.getRootNode() != null : "Invalid library usage. Cached library must be adopted by a RootNode before it is executed.";

         return DynamicObjectLibraryGen.INSTANCE.getUncached(receiver_).removeKey(receiver_, key);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public boolean setDynamicType(DynamicObject receiver_, Object type) {
         assert this.getRootNode() != null : "Invalid library usage. Cached library must be adopted by a RootNode before it is executed.";

         return DynamicObjectLibraryGen.INSTANCE.getUncached(receiver_).setDynamicType(receiver_, type);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public Object getDynamicType(DynamicObject receiver_) {
         assert this.getRootNode() != null : "Invalid library usage. Cached library must be adopted by a RootNode before it is executed.";

         return DynamicObjectLibraryGen.INSTANCE.getUncached(receiver_).getDynamicType(receiver_);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public boolean containsKey(DynamicObject receiver_, Object key) {
         assert this.getRootNode() != null : "Invalid library usage. Cached library must be adopted by a RootNode before it is executed.";

         return DynamicObjectLibraryGen.INSTANCE.getUncached(receiver_).containsKey(receiver_, key);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public int getShapeFlags(DynamicObject receiver_) {
         assert this.getRootNode() != null : "Invalid library usage. Cached library must be adopted by a RootNode before it is executed.";

         return DynamicObjectLibraryGen.INSTANCE.getUncached(receiver_).getShapeFlags(receiver_);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public boolean setShapeFlags(DynamicObject receiver_, int flags) {
         assert this.getRootNode() != null : "Invalid library usage. Cached library must be adopted by a RootNode before it is executed.";

         return DynamicObjectLibraryGen.INSTANCE.getUncached(receiver_).setShapeFlags(receiver_, flags);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public Property getProperty(DynamicObject receiver_, Object key) {
         assert this.getRootNode() != null : "Invalid library usage. Cached library must be adopted by a RootNode before it is executed.";

         return DynamicObjectLibraryGen.INSTANCE.getUncached(receiver_).getProperty(receiver_, key);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public boolean setPropertyFlags(DynamicObject receiver_, Object key, int propertyFlags) {
         assert this.getRootNode() != null : "Invalid library usage. Cached library must be adopted by a RootNode before it is executed.";

         return DynamicObjectLibraryGen.INSTANCE.getUncached(receiver_).setPropertyFlags(receiver_, key, propertyFlags);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public void markShared(DynamicObject receiver_) {
         assert this.getRootNode() != null : "Invalid library usage. Cached library must be adopted by a RootNode before it is executed.";

         DynamicObjectLibraryGen.INSTANCE.getUncached(receiver_).markShared(receiver_);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public boolean isShared(DynamicObject receiver_) {
         assert this.getRootNode() != null : "Invalid library usage. Cached library must be adopted by a RootNode before it is executed.";

         return DynamicObjectLibraryGen.INSTANCE.getUncached(receiver_).isShared(receiver_);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public boolean updateShape(DynamicObject receiver_) {
         assert this.getRootNode() != null : "Invalid library usage. Cached library must be adopted by a RootNode before it is executed.";

         return DynamicObjectLibraryGen.INSTANCE.getUncached(receiver_).updateShape(receiver_);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public boolean resetShape(DynamicObject receiver_, Shape otherShape) {
         assert this.getRootNode() != null : "Invalid library usage. Cached library must be adopted by a RootNode before it is executed.";

         return DynamicObjectLibraryGen.INSTANCE.getUncached(receiver_).resetShape(receiver_, otherShape);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public Object[] getKeyArray(DynamicObject receiver_) {
         assert this.getRootNode() != null : "Invalid library usage. Cached library must be adopted by a RootNode before it is executed.";

         return DynamicObjectLibraryGen.INSTANCE.getUncached(receiver_).getKeyArray(receiver_);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public Property[] getPropertyArray(DynamicObject receiver_) {
         assert this.getRootNode() != null : "Invalid library usage. Cached library must be adopted by a RootNode before it is executed.";

         return DynamicObjectLibraryGen.INSTANCE.getUncached(receiver_).getPropertyArray(receiver_);
      }

      @Override
      public boolean accepts(Object receiver_) {
         return true;
      }
   }

   @GeneratedBy(DynamicObjectLibrary.class)
   private static final class Default extends LibraryExport<DynamicObjectLibrary> {
      private Default() {
         super(DynamicObjectLibrary.class, DynamicObject.class, false, false, 0);
      }

      protected DynamicObjectLibrary createUncached(Object receiver) {
         assert receiver instanceof DynamicObject;

         DynamicObjectLibrary uncached = new DynamicObjectLibraryGen.Default.Uncached(receiver);
         return uncached;
      }

      protected DynamicObjectLibrary createCached(Object receiver) {
         assert receiver instanceof DynamicObject;

         return new DynamicObjectLibraryGen.Default.Cached(receiver);
      }

      @GeneratedBy(DynamicObjectLibrary.class)
      private static final class Cached extends DynamicObjectLibrary {
         private final Class<? extends DynamicObject> receiverClass_;

         protected Cached(Object receiver) {
            DynamicObject castReceiver = (DynamicObject)receiver;
            this.receiverClass_ = (Class<? extends DynamicObject>)castReceiver.getClass();
         }

         @Override
         public boolean accepts(Object receiver) {
            return CompilerDirectives.isExact(receiver, this.receiverClass_);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Shape getShape(DynamicObject receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            throw new AbstractMethodError();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Object getOrDefault(DynamicObject receiver, Object key, Object defaultValue) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            throw new AbstractMethodError();
         }

         @Override
         public int getIntOrDefault(DynamicObject receiver, Object key, Object defaultValue) throws UnexpectedResultException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.getIntOrDefault(CompilerDirectives.castExact(receiver, this.receiverClass_), key, defaultValue);
         }

         @Override
         public double getDoubleOrDefault(DynamicObject receiver, Object key, Object defaultValue) throws UnexpectedResultException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.getDoubleOrDefault(CompilerDirectives.castExact(receiver, this.receiverClass_), key, defaultValue);
         }

         @Override
         public long getLongOrDefault(DynamicObject receiver, Object key, Object defaultValue) throws UnexpectedResultException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.getLongOrDefault(CompilerDirectives.castExact(receiver, this.receiverClass_), key, defaultValue);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public void put(DynamicObject receiver, Object key, Object value) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            throw new AbstractMethodError();
         }

         @Override
         public void putInt(DynamicObject receiver, Object key, int value) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            super.putInt(CompilerDirectives.castExact(receiver, this.receiverClass_), key, value);
         }

         @Override
         public void putDouble(DynamicObject receiver, Object key, double value) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            super.putDouble(CompilerDirectives.castExact(receiver, this.receiverClass_), key, value);
         }

         @Override
         public void putLong(DynamicObject receiver, Object key, long value) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            super.putLong(CompilerDirectives.castExact(receiver, this.receiverClass_), key, value);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean putIfPresent(DynamicObject receiver, Object key, Object value) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            throw new AbstractMethodError();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public void putWithFlags(DynamicObject receiver, Object key, Object value, int flags) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            throw new AbstractMethodError();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public void putConstant(DynamicObject receiver, Object key, Object value, int flags) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            throw new AbstractMethodError();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean removeKey(DynamicObject receiver, Object key) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            throw new AbstractMethodError();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean setDynamicType(DynamicObject receiver, Object type) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            throw new AbstractMethodError();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Object getDynamicType(DynamicObject receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            throw new AbstractMethodError();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean containsKey(DynamicObject receiver, Object key) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            throw new AbstractMethodError();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public int getShapeFlags(DynamicObject receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            throw new AbstractMethodError();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean setShapeFlags(DynamicObject receiver, int flags) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            throw new AbstractMethodError();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Property getProperty(DynamicObject receiver, Object key) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            throw new AbstractMethodError();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean setPropertyFlags(DynamicObject receiver, Object key, int propertyFlags) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            throw new AbstractMethodError();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public void markShared(DynamicObject receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            throw new AbstractMethodError();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean isShared(DynamicObject receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            throw new AbstractMethodError();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean updateShape(DynamicObject receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            throw new AbstractMethodError();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean resetShape(DynamicObject receiver, Shape otherShape) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            throw new AbstractMethodError();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Object[] getKeyArray(DynamicObject receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            throw new AbstractMethodError();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Property[] getPropertyArray(DynamicObject receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            throw new AbstractMethodError();
         }
      }

      @GeneratedBy(DynamicObjectLibrary.class)
      @DenyReplace
      private static final class Uncached extends DynamicObjectLibrary {
         private final Class<? extends DynamicObject> receiverClass_;

         protected Uncached(Object receiver) {
            this.receiverClass_ = (Class<? extends DynamicObject>)((DynamicObject)receiver).getClass();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean accepts(Object receiver) {
            return CompilerDirectives.isExact(receiver, this.receiverClass_);
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
         public Shape getShape(DynamicObject receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            throw new AbstractMethodError();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Object getOrDefault(DynamicObject receiver, Object key, Object defaultValue) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            throw new AbstractMethodError();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public int getIntOrDefault(DynamicObject receiver, Object key, Object defaultValue) throws UnexpectedResultException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.getIntOrDefault(receiver, key, defaultValue);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public double getDoubleOrDefault(DynamicObject receiver, Object key, Object defaultValue) throws UnexpectedResultException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.getDoubleOrDefault(receiver, key, defaultValue);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public long getLongOrDefault(DynamicObject receiver, Object key, Object defaultValue) throws UnexpectedResultException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.getLongOrDefault(receiver, key, defaultValue);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public void put(DynamicObject receiver, Object key, Object value) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            throw new AbstractMethodError();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public void putInt(DynamicObject receiver, Object key, int value) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            super.putInt(receiver, key, value);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public void putDouble(DynamicObject receiver, Object key, double value) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            super.putDouble(receiver, key, value);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public void putLong(DynamicObject receiver, Object key, long value) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            super.putLong(receiver, key, value);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean putIfPresent(DynamicObject receiver, Object key, Object value) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            throw new AbstractMethodError();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public void putWithFlags(DynamicObject receiver, Object key, Object value, int flags) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            throw new AbstractMethodError();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public void putConstant(DynamicObject receiver, Object key, Object value, int flags) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            throw new AbstractMethodError();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean removeKey(DynamicObject receiver, Object key) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            throw new AbstractMethodError();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean setDynamicType(DynamicObject receiver, Object type) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            throw new AbstractMethodError();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Object getDynamicType(DynamicObject receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            throw new AbstractMethodError();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean containsKey(DynamicObject receiver, Object key) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            throw new AbstractMethodError();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public int getShapeFlags(DynamicObject receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            throw new AbstractMethodError();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean setShapeFlags(DynamicObject receiver, int flags) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            throw new AbstractMethodError();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Property getProperty(DynamicObject receiver, Object key) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            throw new AbstractMethodError();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean setPropertyFlags(DynamicObject receiver, Object key, int propertyFlags) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            throw new AbstractMethodError();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public void markShared(DynamicObject receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            throw new AbstractMethodError();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean isShared(DynamicObject receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            throw new AbstractMethodError();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean updateShape(DynamicObject receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            throw new AbstractMethodError();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean resetShape(DynamicObject receiver, Shape otherShape) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            throw new AbstractMethodError();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Object[] getKeyArray(DynamicObject receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            throw new AbstractMethodError();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Property[] getPropertyArray(DynamicObject receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            throw new AbstractMethodError();
         }
      }
   }

   @GeneratedBy(DynamicObjectLibrary.class)
   private static final class Delegate extends DynamicObjectLibrary {
      @Node.Child
      private DynamicObjectLibrary delegateLibrary;

      Delegate(DynamicObjectLibrary delegateLibrary) {
         this.delegateLibrary = delegateLibrary;
      }

      @Override
      public Shape getShape(DynamicObject receiver_) {
         if (DynamicObjectLibraryGen.isDelegated(this.delegateLibrary, 0)) {
            Object delegate = DynamicObjectLibraryGen.readDelegate(this.delegateLibrary, receiver_);
            return DynamicObjectLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate).getShape((DynamicObject)delegate);
         } else {
            return this.delegateLibrary.getShape(receiver_);
         }
      }

      @Override
      public Object getOrDefault(DynamicObject receiver_, Object key, Object defaultValue) {
         if (DynamicObjectLibraryGen.isDelegated(this.delegateLibrary, 1)) {
            Object delegate = DynamicObjectLibraryGen.readDelegate(this.delegateLibrary, receiver_);
            return DynamicObjectLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate).getOrDefault((DynamicObject)delegate, key, defaultValue);
         } else {
            return this.delegateLibrary.getOrDefault(receiver_, key, defaultValue);
         }
      }

      @Override
      public int getIntOrDefault(DynamicObject receiver_, Object key, Object defaultValue) throws UnexpectedResultException {
         if (DynamicObjectLibraryGen.isDelegated(this.delegateLibrary, 2)) {
            Object delegate = DynamicObjectLibraryGen.readDelegate(this.delegateLibrary, receiver_);
            return DynamicObjectLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate).getIntOrDefault((DynamicObject)delegate, key, defaultValue);
         } else {
            return this.delegateLibrary.getIntOrDefault(receiver_, key, defaultValue);
         }
      }

      @Override
      public double getDoubleOrDefault(DynamicObject receiver_, Object key, Object defaultValue) throws UnexpectedResultException {
         if (DynamicObjectLibraryGen.isDelegated(this.delegateLibrary, 3)) {
            Object delegate = DynamicObjectLibraryGen.readDelegate(this.delegateLibrary, receiver_);
            return DynamicObjectLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate).getDoubleOrDefault((DynamicObject)delegate, key, defaultValue);
         } else {
            return this.delegateLibrary.getDoubleOrDefault(receiver_, key, defaultValue);
         }
      }

      @Override
      public long getLongOrDefault(DynamicObject receiver_, Object key, Object defaultValue) throws UnexpectedResultException {
         if (DynamicObjectLibraryGen.isDelegated(this.delegateLibrary, 4)) {
            Object delegate = DynamicObjectLibraryGen.readDelegate(this.delegateLibrary, receiver_);
            return DynamicObjectLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate).getLongOrDefault((DynamicObject)delegate, key, defaultValue);
         } else {
            return this.delegateLibrary.getLongOrDefault(receiver_, key, defaultValue);
         }
      }

      @Override
      public void put(DynamicObject receiver_, Object key, Object value) {
         if (DynamicObjectLibraryGen.isDelegated(this.delegateLibrary, 5)) {
            Object delegate = DynamicObjectLibraryGen.readDelegate(this.delegateLibrary, receiver_);
            DynamicObjectLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate).put((DynamicObject)delegate, key, value);
         } else {
            this.delegateLibrary.put(receiver_, key, value);
         }
      }

      @Override
      public void putInt(DynamicObject receiver_, Object key, int value) {
         if (DynamicObjectLibraryGen.isDelegated(this.delegateLibrary, 6)) {
            Object delegate = DynamicObjectLibraryGen.readDelegate(this.delegateLibrary, receiver_);
            DynamicObjectLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate).putInt((DynamicObject)delegate, key, value);
         } else {
            this.delegateLibrary.putInt(receiver_, key, value);
         }
      }

      @Override
      public void putDouble(DynamicObject receiver_, Object key, double value) {
         if (DynamicObjectLibraryGen.isDelegated(this.delegateLibrary, 7)) {
            Object delegate = DynamicObjectLibraryGen.readDelegate(this.delegateLibrary, receiver_);
            DynamicObjectLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate).putDouble((DynamicObject)delegate, key, value);
         } else {
            this.delegateLibrary.putDouble(receiver_, key, value);
         }
      }

      @Override
      public void putLong(DynamicObject receiver_, Object key, long value) {
         if (DynamicObjectLibraryGen.isDelegated(this.delegateLibrary, 8)) {
            Object delegate = DynamicObjectLibraryGen.readDelegate(this.delegateLibrary, receiver_);
            DynamicObjectLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate).putLong((DynamicObject)delegate, key, value);
         } else {
            this.delegateLibrary.putLong(receiver_, key, value);
         }
      }

      @Override
      public boolean putIfPresent(DynamicObject receiver_, Object key, Object value) {
         if (DynamicObjectLibraryGen.isDelegated(this.delegateLibrary, 9)) {
            Object delegate = DynamicObjectLibraryGen.readDelegate(this.delegateLibrary, receiver_);
            return DynamicObjectLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate).putIfPresent((DynamicObject)delegate, key, value);
         } else {
            return this.delegateLibrary.putIfPresent(receiver_, key, value);
         }
      }

      @Override
      public void putWithFlags(DynamicObject receiver_, Object key, Object value, int flags) {
         if (DynamicObjectLibraryGen.isDelegated(this.delegateLibrary, 10)) {
            Object delegate = DynamicObjectLibraryGen.readDelegate(this.delegateLibrary, receiver_);
            DynamicObjectLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate).putWithFlags((DynamicObject)delegate, key, value, flags);
         } else {
            this.delegateLibrary.putWithFlags(receiver_, key, value, flags);
         }
      }

      @Override
      public void putConstant(DynamicObject receiver_, Object key, Object value, int flags) {
         if (DynamicObjectLibraryGen.isDelegated(this.delegateLibrary, 11)) {
            Object delegate = DynamicObjectLibraryGen.readDelegate(this.delegateLibrary, receiver_);
            DynamicObjectLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate).putConstant((DynamicObject)delegate, key, value, flags);
         } else {
            this.delegateLibrary.putConstant(receiver_, key, value, flags);
         }
      }

      @Override
      public boolean removeKey(DynamicObject receiver_, Object key) {
         if (DynamicObjectLibraryGen.isDelegated(this.delegateLibrary, 12)) {
            Object delegate = DynamicObjectLibraryGen.readDelegate(this.delegateLibrary, receiver_);
            return DynamicObjectLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate).removeKey((DynamicObject)delegate, key);
         } else {
            return this.delegateLibrary.removeKey(receiver_, key);
         }
      }

      @Override
      public boolean setDynamicType(DynamicObject receiver_, Object type) {
         if (DynamicObjectLibraryGen.isDelegated(this.delegateLibrary, 13)) {
            Object delegate = DynamicObjectLibraryGen.readDelegate(this.delegateLibrary, receiver_);
            return DynamicObjectLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate).setDynamicType((DynamicObject)delegate, type);
         } else {
            return this.delegateLibrary.setDynamicType(receiver_, type);
         }
      }

      @Override
      public Object getDynamicType(DynamicObject receiver_) {
         if (DynamicObjectLibraryGen.isDelegated(this.delegateLibrary, 14)) {
            Object delegate = DynamicObjectLibraryGen.readDelegate(this.delegateLibrary, receiver_);
            return DynamicObjectLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate).getDynamicType((DynamicObject)delegate);
         } else {
            return this.delegateLibrary.getDynamicType(receiver_);
         }
      }

      @Override
      public boolean containsKey(DynamicObject receiver_, Object key) {
         if (DynamicObjectLibraryGen.isDelegated(this.delegateLibrary, 15)) {
            Object delegate = DynamicObjectLibraryGen.readDelegate(this.delegateLibrary, receiver_);
            return DynamicObjectLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate).containsKey((DynamicObject)delegate, key);
         } else {
            return this.delegateLibrary.containsKey(receiver_, key);
         }
      }

      @Override
      public int getShapeFlags(DynamicObject receiver_) {
         if (DynamicObjectLibraryGen.isDelegated(this.delegateLibrary, 16)) {
            Object delegate = DynamicObjectLibraryGen.readDelegate(this.delegateLibrary, receiver_);
            return DynamicObjectLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate).getShapeFlags((DynamicObject)delegate);
         } else {
            return this.delegateLibrary.getShapeFlags(receiver_);
         }
      }

      @Override
      public boolean setShapeFlags(DynamicObject receiver_, int flags) {
         if (DynamicObjectLibraryGen.isDelegated(this.delegateLibrary, 17)) {
            Object delegate = DynamicObjectLibraryGen.readDelegate(this.delegateLibrary, receiver_);
            return DynamicObjectLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate).setShapeFlags((DynamicObject)delegate, flags);
         } else {
            return this.delegateLibrary.setShapeFlags(receiver_, flags);
         }
      }

      @Override
      public Property getProperty(DynamicObject receiver_, Object key) {
         if (DynamicObjectLibraryGen.isDelegated(this.delegateLibrary, 18)) {
            Object delegate = DynamicObjectLibraryGen.readDelegate(this.delegateLibrary, receiver_);
            return DynamicObjectLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate).getProperty((DynamicObject)delegate, key);
         } else {
            return this.delegateLibrary.getProperty(receiver_, key);
         }
      }

      @Override
      public boolean setPropertyFlags(DynamicObject receiver_, Object key, int propertyFlags) {
         if (DynamicObjectLibraryGen.isDelegated(this.delegateLibrary, 19)) {
            Object delegate = DynamicObjectLibraryGen.readDelegate(this.delegateLibrary, receiver_);
            return DynamicObjectLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate).setPropertyFlags((DynamicObject)delegate, key, propertyFlags);
         } else {
            return this.delegateLibrary.setPropertyFlags(receiver_, key, propertyFlags);
         }
      }

      @Override
      public void markShared(DynamicObject receiver_) {
         if (DynamicObjectLibraryGen.isDelegated(this.delegateLibrary, 20)) {
            Object delegate = DynamicObjectLibraryGen.readDelegate(this.delegateLibrary, receiver_);
            DynamicObjectLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate).markShared((DynamicObject)delegate);
         } else {
            this.delegateLibrary.markShared(receiver_);
         }
      }

      @Override
      public boolean isShared(DynamicObject receiver_) {
         if (DynamicObjectLibraryGen.isDelegated(this.delegateLibrary, 21)) {
            Object delegate = DynamicObjectLibraryGen.readDelegate(this.delegateLibrary, receiver_);
            return DynamicObjectLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate).isShared((DynamicObject)delegate);
         } else {
            return this.delegateLibrary.isShared(receiver_);
         }
      }

      @Override
      public boolean updateShape(DynamicObject receiver_) {
         if (DynamicObjectLibraryGen.isDelegated(this.delegateLibrary, 22)) {
            Object delegate = DynamicObjectLibraryGen.readDelegate(this.delegateLibrary, receiver_);
            return DynamicObjectLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate).updateShape((DynamicObject)delegate);
         } else {
            return this.delegateLibrary.updateShape(receiver_);
         }
      }

      @Override
      public boolean resetShape(DynamicObject receiver_, Shape otherShape) {
         if (DynamicObjectLibraryGen.isDelegated(this.delegateLibrary, 23)) {
            Object delegate = DynamicObjectLibraryGen.readDelegate(this.delegateLibrary, receiver_);
            return DynamicObjectLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate).resetShape((DynamicObject)delegate, otherShape);
         } else {
            return this.delegateLibrary.resetShape(receiver_, otherShape);
         }
      }

      @Override
      public Object[] getKeyArray(DynamicObject receiver_) {
         if (DynamicObjectLibraryGen.isDelegated(this.delegateLibrary, 24)) {
            Object delegate = DynamicObjectLibraryGen.readDelegate(this.delegateLibrary, receiver_);
            return DynamicObjectLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate).getKeyArray((DynamicObject)delegate);
         } else {
            return this.delegateLibrary.getKeyArray(receiver_);
         }
      }

      @Override
      public Property[] getPropertyArray(DynamicObject receiver_) {
         if (DynamicObjectLibraryGen.isDelegated(this.delegateLibrary, 25)) {
            Object delegate = DynamicObjectLibraryGen.readDelegate(this.delegateLibrary, receiver_);
            return DynamicObjectLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate).getPropertyArray((DynamicObject)delegate);
         } else {
            return this.delegateLibrary.getPropertyArray(receiver_);
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

   @GeneratedBy(DynamicObjectLibrary.class)
   private static class MessageImpl extends Message {
      MessageImpl(String name, int index, Class<?> returnType, Class<?>... parameters) {
         super(DynamicObjectLibraryGen.LIBRARY_CLASS, name, index, returnType, parameters);
      }
   }

   @GeneratedBy(DynamicObjectLibrary.class)
   private static final class Proxy extends DynamicObjectLibrary {
      @Node.Child
      private ReflectionLibrary lib;

      Proxy(ReflectionLibrary lib) {
         this.lib = lib;
      }

      @Override
      public Shape getShape(DynamicObject receiver_) {
         try {
            return (Shape)this.lib.send(receiver_, DynamicObjectLibraryGen.GET_SHAPE);
         } catch (RuntimeException var3) {
            throw var3;
         } catch (Exception var4) {
            throw CompilerDirectives.shouldNotReachHere(var4);
         }
      }

      @Override
      public Object getOrDefault(DynamicObject receiver_, Object key, Object defaultValue) {
         try {
            return this.lib.send(receiver_, DynamicObjectLibraryGen.GET_OR_DEFAULT, key, defaultValue);
         } catch (RuntimeException var5) {
            throw var5;
         } catch (Exception var6) {
            throw CompilerDirectives.shouldNotReachHere(var6);
         }
      }

      @Override
      public int getIntOrDefault(DynamicObject receiver_, Object key, Object defaultValue) throws UnexpectedResultException {
         try {
            return (Integer)this.lib.send(receiver_, DynamicObjectLibraryGen.GET_INT_OR_DEFAULT, key, defaultValue);
         } catch (RuntimeException | UnexpectedResultException var5) {
            throw var5;
         } catch (Exception var6) {
            throw CompilerDirectives.shouldNotReachHere(var6);
         }
      }

      @Override
      public double getDoubleOrDefault(DynamicObject receiver_, Object key, Object defaultValue) throws UnexpectedResultException {
         try {
            return (Double)this.lib.send(receiver_, DynamicObjectLibraryGen.GET_DOUBLE_OR_DEFAULT, key, defaultValue);
         } catch (RuntimeException | UnexpectedResultException var5) {
            throw var5;
         } catch (Exception var6) {
            throw CompilerDirectives.shouldNotReachHere(var6);
         }
      }

      @Override
      public long getLongOrDefault(DynamicObject receiver_, Object key, Object defaultValue) throws UnexpectedResultException {
         try {
            return (Long)this.lib.send(receiver_, DynamicObjectLibraryGen.GET_LONG_OR_DEFAULT, key, defaultValue);
         } catch (RuntimeException | UnexpectedResultException var5) {
            throw var5;
         } catch (Exception var6) {
            throw CompilerDirectives.shouldNotReachHere(var6);
         }
      }

      @Override
      public void put(DynamicObject receiver_, Object key, Object value) {
         try {
            this.lib.send(receiver_, DynamicObjectLibraryGen.PUT, key, value);
         } catch (RuntimeException var5) {
            throw var5;
         } catch (Exception var6) {
            throw CompilerDirectives.shouldNotReachHere(var6);
         }
      }

      @Override
      public void putInt(DynamicObject receiver_, Object key, int value) {
         try {
            this.lib.send(receiver_, DynamicObjectLibraryGen.PUT_INT, key, value);
         } catch (RuntimeException var5) {
            throw var5;
         } catch (Exception var6) {
            throw CompilerDirectives.shouldNotReachHere(var6);
         }
      }

      @Override
      public void putDouble(DynamicObject receiver_, Object key, double value) {
         try {
            this.lib.send(receiver_, DynamicObjectLibraryGen.PUT_DOUBLE, key, value);
         } catch (RuntimeException var6) {
            throw var6;
         } catch (Exception var7) {
            throw CompilerDirectives.shouldNotReachHere(var7);
         }
      }

      @Override
      public void putLong(DynamicObject receiver_, Object key, long value) {
         try {
            this.lib.send(receiver_, DynamicObjectLibraryGen.PUT_LONG, key, value);
         } catch (RuntimeException var6) {
            throw var6;
         } catch (Exception var7) {
            throw CompilerDirectives.shouldNotReachHere(var7);
         }
      }

      @Override
      public boolean putIfPresent(DynamicObject receiver_, Object key, Object value) {
         try {
            return (Boolean)this.lib.send(receiver_, DynamicObjectLibraryGen.PUT_IF_PRESENT, key, value);
         } catch (RuntimeException var5) {
            throw var5;
         } catch (Exception var6) {
            throw CompilerDirectives.shouldNotReachHere(var6);
         }
      }

      @Override
      public void putWithFlags(DynamicObject receiver_, Object key, Object value, int flags) {
         try {
            this.lib.send(receiver_, DynamicObjectLibraryGen.PUT_WITH_FLAGS, key, value, flags);
         } catch (RuntimeException var6) {
            throw var6;
         } catch (Exception var7) {
            throw CompilerDirectives.shouldNotReachHere(var7);
         }
      }

      @Override
      public void putConstant(DynamicObject receiver_, Object key, Object value, int flags) {
         try {
            this.lib.send(receiver_, DynamicObjectLibraryGen.PUT_CONSTANT, key, value, flags);
         } catch (RuntimeException var6) {
            throw var6;
         } catch (Exception var7) {
            throw CompilerDirectives.shouldNotReachHere(var7);
         }
      }

      @Override
      public boolean removeKey(DynamicObject receiver_, Object key) {
         try {
            return (Boolean)this.lib.send(receiver_, DynamicObjectLibraryGen.REMOVE_KEY, key);
         } catch (RuntimeException var4) {
            throw var4;
         } catch (Exception var5) {
            throw CompilerDirectives.shouldNotReachHere(var5);
         }
      }

      @Override
      public boolean setDynamicType(DynamicObject receiver_, Object type) {
         try {
            return (Boolean)this.lib.send(receiver_, DynamicObjectLibraryGen.SET_DYNAMIC_TYPE, type);
         } catch (RuntimeException var4) {
            throw var4;
         } catch (Exception var5) {
            throw CompilerDirectives.shouldNotReachHere(var5);
         }
      }

      @Override
      public Object getDynamicType(DynamicObject receiver_) {
         try {
            return this.lib.send(receiver_, DynamicObjectLibraryGen.GET_DYNAMIC_TYPE);
         } catch (RuntimeException var3) {
            throw var3;
         } catch (Exception var4) {
            throw CompilerDirectives.shouldNotReachHere(var4);
         }
      }

      @Override
      public boolean containsKey(DynamicObject receiver_, Object key) {
         try {
            return (Boolean)this.lib.send(receiver_, DynamicObjectLibraryGen.CONTAINS_KEY, key);
         } catch (RuntimeException var4) {
            throw var4;
         } catch (Exception var5) {
            throw CompilerDirectives.shouldNotReachHere(var5);
         }
      }

      @Override
      public int getShapeFlags(DynamicObject receiver_) {
         try {
            return (Integer)this.lib.send(receiver_, DynamicObjectLibraryGen.GET_SHAPE_FLAGS);
         } catch (RuntimeException var3) {
            throw var3;
         } catch (Exception var4) {
            throw CompilerDirectives.shouldNotReachHere(var4);
         }
      }

      @Override
      public boolean setShapeFlags(DynamicObject receiver_, int flags) {
         try {
            return (Boolean)this.lib.send(receiver_, DynamicObjectLibraryGen.SET_SHAPE_FLAGS, flags);
         } catch (RuntimeException var4) {
            throw var4;
         } catch (Exception var5) {
            throw CompilerDirectives.shouldNotReachHere(var5);
         }
      }

      @Override
      public Property getProperty(DynamicObject receiver_, Object key) {
         try {
            return (Property)this.lib.send(receiver_, DynamicObjectLibraryGen.GET_PROPERTY, key);
         } catch (RuntimeException var4) {
            throw var4;
         } catch (Exception var5) {
            throw CompilerDirectives.shouldNotReachHere(var5);
         }
      }

      @Override
      public boolean setPropertyFlags(DynamicObject receiver_, Object key, int propertyFlags) {
         try {
            return (Boolean)this.lib.send(receiver_, DynamicObjectLibraryGen.SET_PROPERTY_FLAGS, key, propertyFlags);
         } catch (RuntimeException var5) {
            throw var5;
         } catch (Exception var6) {
            throw CompilerDirectives.shouldNotReachHere(var6);
         }
      }

      @Override
      public void markShared(DynamicObject receiver_) {
         try {
            this.lib.send(receiver_, DynamicObjectLibraryGen.MARK_SHARED);
         } catch (RuntimeException var3) {
            throw var3;
         } catch (Exception var4) {
            throw CompilerDirectives.shouldNotReachHere(var4);
         }
      }

      @Override
      public boolean isShared(DynamicObject receiver_) {
         try {
            return (Boolean)this.lib.send(receiver_, DynamicObjectLibraryGen.IS_SHARED);
         } catch (RuntimeException var3) {
            throw var3;
         } catch (Exception var4) {
            throw CompilerDirectives.shouldNotReachHere(var4);
         }
      }

      @Override
      public boolean updateShape(DynamicObject receiver_) {
         try {
            return (Boolean)this.lib.send(receiver_, DynamicObjectLibraryGen.UPDATE_SHAPE);
         } catch (RuntimeException var3) {
            throw var3;
         } catch (Exception var4) {
            throw CompilerDirectives.shouldNotReachHere(var4);
         }
      }

      @Override
      public boolean resetShape(DynamicObject receiver_, Shape otherShape) {
         try {
            return (Boolean)this.lib.send(receiver_, DynamicObjectLibraryGen.RESET_SHAPE, otherShape);
         } catch (RuntimeException var4) {
            throw var4;
         } catch (Exception var5) {
            throw CompilerDirectives.shouldNotReachHere(var5);
         }
      }

      @Override
      public Object[] getKeyArray(DynamicObject receiver_) {
         try {
            return (Object[])this.lib.send(receiver_, DynamicObjectLibraryGen.GET_KEY_ARRAY);
         } catch (RuntimeException var3) {
            throw var3;
         } catch (Exception var4) {
            throw CompilerDirectives.shouldNotReachHere(var4);
         }
      }

      @Override
      public Property[] getPropertyArray(DynamicObject receiver_) {
         try {
            return (Property[])this.lib.send(receiver_, DynamicObjectLibraryGen.GET_PROPERTY_ARRAY);
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

   @GeneratedBy(DynamicObjectLibrary.class)
   @DenyReplace
   private static final class UncachedDispatch extends DynamicObjectLibrary {
      @Override
      public NodeCost getCost() {
         return NodeCost.MEGAMORPHIC;
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public Shape getShape(DynamicObject receiver_) {
         return DynamicObjectLibraryGen.INSTANCE.getUncached(receiver_).getShape(receiver_);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public Object getOrDefault(DynamicObject receiver_, Object key, Object defaultValue) {
         return DynamicObjectLibraryGen.INSTANCE.getUncached(receiver_).getOrDefault(receiver_, key, defaultValue);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public int getIntOrDefault(DynamicObject receiver_, Object key, Object defaultValue) throws UnexpectedResultException {
         return DynamicObjectLibraryGen.INSTANCE.getUncached(receiver_).getIntOrDefault(receiver_, key, defaultValue);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public double getDoubleOrDefault(DynamicObject receiver_, Object key, Object defaultValue) throws UnexpectedResultException {
         return DynamicObjectLibraryGen.INSTANCE.getUncached(receiver_).getDoubleOrDefault(receiver_, key, defaultValue);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public long getLongOrDefault(DynamicObject receiver_, Object key, Object defaultValue) throws UnexpectedResultException {
         return DynamicObjectLibraryGen.INSTANCE.getUncached(receiver_).getLongOrDefault(receiver_, key, defaultValue);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public void put(DynamicObject receiver_, Object key, Object value) {
         DynamicObjectLibraryGen.INSTANCE.getUncached(receiver_).put(receiver_, key, value);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public void putInt(DynamicObject receiver_, Object key, int value) {
         DynamicObjectLibraryGen.INSTANCE.getUncached(receiver_).putInt(receiver_, key, value);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public void putDouble(DynamicObject receiver_, Object key, double value) {
         DynamicObjectLibraryGen.INSTANCE.getUncached(receiver_).putDouble(receiver_, key, value);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public void putLong(DynamicObject receiver_, Object key, long value) {
         DynamicObjectLibraryGen.INSTANCE.getUncached(receiver_).putLong(receiver_, key, value);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public boolean putIfPresent(DynamicObject receiver_, Object key, Object value) {
         return DynamicObjectLibraryGen.INSTANCE.getUncached(receiver_).putIfPresent(receiver_, key, value);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public void putWithFlags(DynamicObject receiver_, Object key, Object value, int flags) {
         DynamicObjectLibraryGen.INSTANCE.getUncached(receiver_).putWithFlags(receiver_, key, value, flags);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public void putConstant(DynamicObject receiver_, Object key, Object value, int flags) {
         DynamicObjectLibraryGen.INSTANCE.getUncached(receiver_).putConstant(receiver_, key, value, flags);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public boolean removeKey(DynamicObject receiver_, Object key) {
         return DynamicObjectLibraryGen.INSTANCE.getUncached(receiver_).removeKey(receiver_, key);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public boolean setDynamicType(DynamicObject receiver_, Object type) {
         return DynamicObjectLibraryGen.INSTANCE.getUncached(receiver_).setDynamicType(receiver_, type);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public Object getDynamicType(DynamicObject receiver_) {
         return DynamicObjectLibraryGen.INSTANCE.getUncached(receiver_).getDynamicType(receiver_);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public boolean containsKey(DynamicObject receiver_, Object key) {
         return DynamicObjectLibraryGen.INSTANCE.getUncached(receiver_).containsKey(receiver_, key);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public int getShapeFlags(DynamicObject receiver_) {
         return DynamicObjectLibraryGen.INSTANCE.getUncached(receiver_).getShapeFlags(receiver_);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public boolean setShapeFlags(DynamicObject receiver_, int flags) {
         return DynamicObjectLibraryGen.INSTANCE.getUncached(receiver_).setShapeFlags(receiver_, flags);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public Property getProperty(DynamicObject receiver_, Object key) {
         return DynamicObjectLibraryGen.INSTANCE.getUncached(receiver_).getProperty(receiver_, key);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public boolean setPropertyFlags(DynamicObject receiver_, Object key, int propertyFlags) {
         return DynamicObjectLibraryGen.INSTANCE.getUncached(receiver_).setPropertyFlags(receiver_, key, propertyFlags);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public void markShared(DynamicObject receiver_) {
         DynamicObjectLibraryGen.INSTANCE.getUncached(receiver_).markShared(receiver_);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public boolean isShared(DynamicObject receiver_) {
         return DynamicObjectLibraryGen.INSTANCE.getUncached(receiver_).isShared(receiver_);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public boolean updateShape(DynamicObject receiver_) {
         return DynamicObjectLibraryGen.INSTANCE.getUncached(receiver_).updateShape(receiver_);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public boolean resetShape(DynamicObject receiver_, Shape otherShape) {
         return DynamicObjectLibraryGen.INSTANCE.getUncached(receiver_).resetShape(receiver_, otherShape);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public Object[] getKeyArray(DynamicObject receiver_) {
         return DynamicObjectLibraryGen.INSTANCE.getUncached(receiver_).getKeyArray(receiver_);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public Property[] getPropertyArray(DynamicObject receiver_) {
         return DynamicObjectLibraryGen.INSTANCE.getUncached(receiver_).getPropertyArray(receiver_);
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
