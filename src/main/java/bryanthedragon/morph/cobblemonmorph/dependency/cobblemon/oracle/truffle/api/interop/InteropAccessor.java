package com.oracle.truffle.api.interop;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.impl.Accessor;
import com.oracle.truffle.api.nodes.Node;

final class InteropAccessor extends Accessor {
   static final InteropAccessor ACCESSOR = new InteropAccessor();
   static final Accessor.LanguageSupport LANGUAGE = ACCESSOR.languageSupport();
   static final Accessor.ExceptionSupport EXCEPTION = ACCESSOR.exceptionSupport();
   static final Accessor.InstrumentSupport INSTRUMENT = ACCESSOR.instrumentSupport();
   static final Accessor.NodeSupport NODES = ACCESSOR.nodeSupport();
   static final Accessor.HostSupport HOST = ACCESSOR.hostSupport();

   private InteropAccessor() {
   }

   static Object checkInteropType(Object obj) {
      assert checkInteropTypeImpl(obj);

      return obj;
   }

   private static boolean checkInteropTypeImpl(Object obj) {
      if (InteropLibrary.isValidValue(obj)) {
         return true;
      } else {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         Class<?> clazz = obj != null ? obj.getClass() : null;
         return yieldAnError(clazz);
      }
   }

   @CompilerDirectives.TruffleBoundary
   private static boolean yieldAnError(Class<?> clazz) {
      StringBuilder sb = new StringBuilder();
      sb.append(clazz == null ? "null" : clazz.getName());
      sb.append(" isn't allowed Truffle interop type!\n");
      if (clazz == null) {
         throw new NullPointerException(sb.toString());
      } else {
         throw new ClassCastException(sb.toString());
      }
   }

   static final class EmptyTruffleObject implements TruffleObject {
      static final InteropAccessor.EmptyTruffleObject INSTANCE = new InteropAccessor.EmptyTruffleObject();
   }

   static class InteropImpl extends Accessor.InteropSupport {
      @Override
      public boolean isTruffleObject(Object value) {
         return value instanceof TruffleObject;
      }

      @Override
      public void checkInteropType(Object result) {
         InteropAccessor.checkInteropType(result);
      }

      @Override
      public boolean isExecutableObject(Object value) {
         return InteropLibrary.getUncached().isExecutable(value);
      }

      @Override
      public boolean isScopeObject(Object receiver) {
         InteropLibrary interop = InteropLibrary.getUncached();
         return interop.isScope(receiver) && interop.hasMembers(receiver);
      }

      @Override
      public Object createDefaultNodeObject(Node node) {
         return InteropAccessor.EmptyTruffleObject.INSTANCE;
      }

      @Override
      public Object createDefaultIterator(Object receiver) {
         return new ArrayIterator(receiver);
      }

      @Override
      public Node createDispatchedInteropLibrary(int limit) {
         return InteropLibrary.getFactory().createDispatched(limit);
      }

      @Override
      public Node getUncachedInteropLibrary() {
         return InteropLibrary.getUncached();
      }

      @Override
      public long unboxPointer(Node library, Object value) {
         InteropLibrary interop = (InteropLibrary)library;
         if (!interop.isPointer(value)) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            throw new IllegalArgumentException("value is not an interop pointer");
         } else {
            try {
               return interop.asPointer(value);
            } catch (UnsupportedMessageException var5) {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               throw new IllegalArgumentException("value is not an interop pointer");
            }
         }
      }
   }
}
