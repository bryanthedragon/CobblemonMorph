package com.oracle.truffle.host;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.library.ExportMessage;
import com.oracle.truffle.api.library.Message;
import com.oracle.truffle.api.library.ReflectionLibrary;
import com.oracle.truffle.api.profiles.BranchProfile;
import java.lang.reflect.Field;
import java.util.Arrays;
import sun.misc.Unsafe;

final class HostMethodScope {
   private static final HostMethodScope.ScopedObject[] EMTPY_SCOPE_ARRAY = new HostMethodScope.ScopedObject[0];
   private static final Unsafe UNSAFE = getUnsafe();
   private HostMethodScope.ScopedObject[] scope;
   private int nextDynamicIndex;

   HostMethodScope(HostMethodScope.ScopedObject[] staticScope) {
      this.scope = staticScope;
      this.nextDynamicIndex = staticScope.length;
   }

   HostMethodScope(int initialDynamicCapacity) {
      this.scope = new HostMethodScope.ScopedObject[initialDynamicCapacity];
      this.nextDynamicIndex = 0;
   }

   static HostMethodScope openDynamic(HostMethodDesc.SingleMethod method, int argumentCount, BranchProfile seenScope) {
      if (method.hasScopedParameters()) {
         seenScope.enter();
         return new HostMethodScope(argumentCount);
      } else {
         return null;
      }
   }

   static HostMethodScope openStatic(HostMethodDesc.SingleMethod method) {
      CompilerAsserts.partialEvaluationConstant(method);
      if (method.hasScopedParameters()) {
         int[] scopePos = method.getScopedParameters();
         HostMethodScope.ScopedObject[] scopeArray;
         if (scopePos.length > 0) {
            scopeArray = new HostMethodScope.ScopedObject[scopePos.length];
         } else {
            scopeArray = EMTPY_SCOPE_ARRAY;
         }

         return new HostMethodScope(scopeArray);
      } else {
         return null;
      }
   }

   static Object addToScopeDynamic(HostMethodScope scope, Object value) {
      if (scope != null) {
         assert !(value instanceof HostMethodScope.ScopedObject);

         return scope.addToScopeDynamicImpl(value);
      } else {
         return value;
      }
   }

   static Object addToScopeStatic(HostMethodScope scope, HostMethodDesc.SingleMethod method, int argumentIndex, Object value) {
      CompilerAsserts.partialEvaluationConstant(method);
      if (scope != null) {
         assert !(value instanceof HostMethodScope.ScopedObject);

         int[] scopePos = method.getScopedParameters();
         int targetIndex = scopePos[argumentIndex];
         if (targetIndex != -1) {
            return scope.scope[targetIndex] = new HostMethodScope.ScopedObject(scope, value, targetIndex);
         }
      }

      return value;
   }

   static void pin(Object value) {
      if (value instanceof HostMethodScope.ScopedObject) {
         ((HostMethodScope.ScopedObject)value).pin();
      }
   }

   static void closeStatic(HostMethodScope scope, HostMethodDesc.SingleMethod method, BranchProfile seenDynamicScope) {
      if (method.hasScopedParameters()) {
         int[] scopePos = method.getScopedParameters();
         HostMethodScope.ScopedObject[] array = scope.scope;

         for (int i = 0; i < scopePos.length; i++) {
            HostMethodScope.ScopedObject o = array[i];
            if (o != null) {
               o.release();
            }
         }

         for (int ix = scopePos.length; ix < array.length; ix++) {
            seenDynamicScope.enter();
            HostMethodScope.ScopedObject o = array[ix];
            if (o != null) {
               o.release();
            }
         }
      } else {
         assert scope == null;
      }
   }

   static void closeDynamic(HostMethodScope scope, HostMethodDesc.SingleMethod method) {
      if (method.hasScopedParameters()) {
         HostMethodScope.ScopedObject[] array = scope.scope;

         for (int i = 0; i < array.length; i++) {
            HostMethodScope.ScopedObject o = array[i];
            if (o != null) {
               o.release();
            }
         }
      } else {
         assert scope == null;
      }
   }

   @CompilerDirectives.TruffleBoundary
   private synchronized Object addToScopeDynamicImpl(Object argument) {
      HostMethodScope.ScopedObject[] localScope = this.scope;
      int index = this.nextDynamicIndex;
      if (index >= localScope.length) {
         this.scope = localScope = Arrays.copyOf(localScope, localScope.length << 1);
      }

      if (index < 0) {
         throw createReleaseException("Too many scoped values created for scoped method instance.");
      } else {
         Object newArgument = localScope[index] = new HostMethodScope.ScopedObject(this, argument, index);
         this.nextDynamicIndex = index + 1;
         return newArgument;
      }
   }

   @CompilerDirectives.TruffleBoundary
   private static RuntimeException createReleaseException(String message) {
      return HostEngineException.toEngineException(
         HostLanguage.get(null).access, new IllegalStateException("This scoped object has already been released. " + message)
      );
   }

   private static Unsafe getUnsafe() {
      try {
         return Unsafe.getUnsafe();
      } catch (SecurityException var2) {
         try {
            Field theUnsafeInstance = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafeInstance.setAccessible(true);
            return (Unsafe)theUnsafeInstance.get(Unsafe.class);
         } catch (Exception var1) {
            throw new RuntimeException("exception while trying to get Unsafe.theUnsafe via reflection:", var1);
         }
      }
   }

   @ExportLibrary(value = InteropLibrary.class, delegateTo = "delegate")
   static final class PinnedObject implements TruffleObject {
      final Object delegate;

      PinnedObject(Object delegate) {
         this.delegate = delegate;
      }
   }

   @ExportLibrary(ReflectionLibrary.class)
   static final class ScopedObject implements TruffleObject {
      static final Object OTHER_VALUE = new Object();
      static final ReflectionLibrary OTHER_VALUE_UNCACHED = ReflectionLibrary.getFactory().getUncached(OTHER_VALUE);
      static final long DELEGATE_OFFSET;
      volatile Object delegate;
      volatile HostMethodScope scope;
      private final int index;

      ScopedObject(HostMethodScope scope, Object delegate, int index) {
         this.delegate = delegate;
         this.scope = scope;
         this.index = index;
      }

      @ExportMessage
      Object send(
         Message message,
         Object[] args,
         @CachedLibrary(limit = "5") ReflectionLibrary library,
         @Cached BranchProfile seenError,
         @Cached BranchProfile seenOther
      ) throws Exception {
         if (message.getLibraryClass() != InteropLibrary.class) {
            seenOther.enter();
            return fallbackSend(message, args);
         } else {
            Object d = this.delegate;
            if (d == null) {
               seenError.enter();
               throw HostMethodScope.createReleaseException(
                  "Released objects cannot be accessed. Avoid accessing scoped objects after their corresponding method has finished execution. Alternatively, use Value.pin() to prevent a scoped object from being released after the host call completed."
               );
            } else {
               assert d != null : "delegate must not be null here";

               Object returnValue = library.send(d, message, args);
               return message.getReturnType() == Object.class && !(d instanceof HostMethodScope.PinnedObject)
                  ? HostMethodScope.addToScopeDynamic(this.scope, returnValue)
                  : returnValue;
            }
         }
      }

      @CompilerDirectives.TruffleBoundary
      private static Object fallbackSend(Message message, Object[] args) throws Exception {
         return OTHER_VALUE_UNCACHED.send(OTHER_VALUE, message, args);
      }

      void release() {
         Object d = this.delegate;

         assert d != null;

         if (!(d instanceof HostMethodScope.PinnedObject) && HostMethodScope.UNSAFE.compareAndSwapObject(this, DELEGATE_OFFSET, d, null)) {
            assert this.delegate == null : "Scoped objects can only be released once.";
         }
      }

      void pin() {
         Object expect;
         HostMethodScope s;
         Object update;
         do {
            s = this.scope;
            expect = this.delegate;
            if (expect instanceof HostMethodScope.PinnedObject) {
               return;
            }

            if (expect == null) {
               throw HostMethodScope.createReleaseException("Released objects cannot be pinned.");
            }

            update = new HostMethodScope.PinnedObject(expect);
         } while (!HostMethodScope.UNSAFE.compareAndSwapObject(this, DELEGATE_OFFSET, expect, update));

         this.scope = null;
         synchronized (s) {
            s.scope[this.index] = null;
         }

         assert this.delegate != null : "delegate must not be set to null after pinning ";
      }

      Object unwrapForGuest() {
         Object d = this.delegate;
         if (d == null) {
            throw HostMethodScope.createReleaseException("Released objects cannot be converted to a guest value.");
         } else {
            return d instanceof HostMethodScope.PinnedObject ? ((HostMethodScope.PinnedObject)d).delegate : d;
         }
      }

      static {
         Field f;
         try {
            f = HostMethodScope.ScopedObject.class.getDeclaredField("delegate");
         } catch (SecurityException | NoSuchFieldException var2) {
            throw CompilerDirectives.shouldNotReachHere(var2);
         }

         DELEGATE_OFFSET = HostMethodScope.UNSAFE.objectFieldOffset(f);
      }
   }
}
