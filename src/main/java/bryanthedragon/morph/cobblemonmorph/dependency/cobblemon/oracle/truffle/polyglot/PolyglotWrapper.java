package com.oracle.truffle.polyglot;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.interop.InteropLibrary;
import java.lang.reflect.Proxy;

interface PolyglotWrapper {
   Object getGuestObject();

   PolyglotContextImpl getContext();

   PolyglotLanguageContext getLanguageContext();

   static boolean isInstance(Object v) {
      if (v == null) {
         return false;
      } else {
         return v instanceof Proxy ? isHostProxy(v) : v instanceof PolyglotWrapper;
      }
   }

   @CompilerDirectives.TruffleBoundary
   static boolean isHostProxy(Object v) {
      return Proxy.isProxyClass(v.getClass()) ? Proxy.getInvocationHandler(v) instanceof PolyglotWrapper : false;
   }

   static PolyglotWrapper asInstance(Object v) {
      return v instanceof Proxy ? getHostProxy(v) : (PolyglotWrapper)v;
   }

   @CompilerDirectives.TruffleBoundary
   static PolyglotWrapper getHostProxy(Object v) {
      return (PolyglotWrapper)Proxy.getInvocationHandler(v);
   }

   @CompilerDirectives.TruffleBoundary
   static boolean equalsProxy(PolyglotWrapper wrapper, Object other) {
      if (other == null) {
         return false;
      } else {
         return Proxy.isProxyClass(other.getClass())
            ? equals(wrapper.getLanguageContext(), wrapper.getGuestObject(), getHostProxy(other).getGuestObject())
            : false;
      }
   }

   @CompilerDirectives.TruffleBoundary
   static boolean equals(Object context, Object receiver, Object obj) {
      if (obj == null) {
         return false;
      } else if (receiver == obj) {
         return true;
      } else {
         PolyglotLanguageContext languageContext = (PolyglotLanguageContext)context;
         if (languageContext != null) {
            PolyglotContextImpl.State localContextState = languageContext.context.state;
            if (localContextState.isInvalidOrClosed()) {
               return false;
            }
         }

         Object prev = null;

         try {
            prev = PolyglotValueDispatch.hostEnter(languageContext);
         } catch (Throwable var19) {
            return false;
         }

         boolean var7;
         try {
            InteropLibrary receiverLib = InteropLibrary.getUncached(receiver);
            InteropLibrary objLib = InteropLibrary.getUncached(obj);
            var7 = receiverLib.isIdentical(receiver, obj, objLib);
         } catch (Throwable var17) {
            throw PolyglotImpl.guestToHostException(languageContext, var17, true);
         } finally {
            try {
               PolyglotValueDispatch.hostLeave(languageContext, prev);
            } catch (Throwable var16) {
            }
         }

         return var7;
      }
   }

   @CompilerDirectives.TruffleBoundary
   static int hashCode(Object context, Object receiver) {
      PolyglotLanguageContext languageContext = (PolyglotLanguageContext)context;
      if (languageContext != null) {
         PolyglotContextImpl.State localContextState = languageContext.context.state;
         if (localContextState.isInvalidOrClosed()) {
            return System.identityHashCode(receiver);
         }
      }

      Object prev = null;

      try {
         prev = PolyglotValueDispatch.hostEnter(languageContext);
      } catch (Throwable var16) {
         return System.identityHashCode(receiver);
      }

      int var5;
      try {
         InteropLibrary receiverLib = InteropLibrary.getUncached(receiver);
         if (!receiverLib.hasIdentity(receiver)) {
            return System.identityHashCode(receiver);
         }

         var5 = receiverLib.identityHashCode(receiver);
      } catch (Throwable var17) {
         throw PolyglotImpl.guestToHostException(languageContext, var17, true);
      } finally {
         try {
            PolyglotValueDispatch.hostLeave(languageContext, prev);
         } catch (Throwable var15) {
         }
      }

      return var5;
   }

   static String toString(PolyglotWrapper thisObj) {
      PolyglotLanguageContext thisContext = thisObj.getLanguageContext();
      Object thisGuestObject = thisObj.getGuestObject();
      if (thisContext != null) {
         try {
            return thisContext.asValue(thisGuestObject).toString();
         } catch (Exception var4) {
         }
      }

      return "Error in toString()";
   }
}
