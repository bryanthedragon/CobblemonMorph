
package com.oracle.truffle.polyglot;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.polyglot.PolyglotContextImpl;
import com.oracle.truffle.polyglot.PolyglotImpl;
import com.oracle.truffle.polyglot.PolyglotLanguageContext;
import com.oracle.truffle.polyglot.PolyglotValueDispatch;
import java.lang.reflect.Proxy;

interface PolyglotWrapper {
    public Object getGuestObject();

    public PolyglotContextImpl getContext();

    public PolyglotLanguageContext getLanguageContext();

    public static boolean isInstance(Object v) {
        if (v == null) {
            return false;
        }
        if (v instanceof Proxy) {
            return PolyglotWrapper.isHostProxy(v);
        }
        return v instanceof PolyglotWrapper;
    }

    @CompilerDirectives.TruffleBoundary
    public static boolean isHostProxy(Object v) {
        if (Proxy.isProxyClass(v.getClass())) {
            return Proxy.getInvocationHandler(v) instanceof PolyglotWrapper;
        }
        return false;
    }

    public static PolyglotWrapper asInstance(Object v) {
        if (v instanceof Proxy) {
            return PolyglotWrapper.getHostProxy(v);
        }
        return (PolyglotWrapper)v;
    }

    @CompilerDirectives.TruffleBoundary
    public static PolyglotWrapper getHostProxy(Object v) {
        return (PolyglotWrapper)((Object)Proxy.getInvocationHandler(v));
    }

    @CompilerDirectives.TruffleBoundary
    public static boolean equalsProxy(PolyglotWrapper wrapper, Object other) {
        if (other == null) {
            return false;
        }
        if (Proxy.isProxyClass(other.getClass())) {
            return PolyglotWrapper.equals(wrapper.getLanguageContext(), wrapper.getGuestObject(), PolyglotWrapper.getHostProxy(other).getGuestObject());
        }
        return false;
    }

    @CompilerDirectives.TruffleBoundary
    public static boolean equals(Object context, Object receiver, Object obj) {
        PolyglotContextImpl.State localContextState;
        if (obj == null) {
            return false;
        }
        if (receiver == obj) {
            return true;
        }
        PolyglotLanguageContext languageContext = (PolyglotLanguageContext)context;
        if (languageContext != null && (localContextState = languageContext.context.state).isInvalidOrClosed()) {
            return false;
        }
        Object prev = null;
        try {
            prev = PolyglotValueDispatch.hostEnter(languageContext);
        }
        catch (Throwable t) {
            return false;
        }
        try {
            InteropLibrary receiverLib = InteropLibrary.getUncached(receiver);
            InteropLibrary objLib = InteropLibrary.getUncached(obj);
            boolean bl = receiverLib.isIdentical(receiver, obj, objLib);
            return bl;
        }
        catch (Throwable t) {
            throw PolyglotImpl.guestToHostException(languageContext, t, true);
        }
        finally {
            try {
                PolyglotValueDispatch.hostLeave(languageContext, prev);
            }
            catch (Throwable throwable) {}
        }
    }

    @CompilerDirectives.TruffleBoundary
    public static int hashCode(Object context, Object receiver) {
        int n;
        PolyglotContextImpl.State localContextState;
        PolyglotLanguageContext languageContext = (PolyglotLanguageContext)context;
        if (languageContext != null && (localContextState = languageContext.context.state).isInvalidOrClosed()) {
            return System.identityHashCode(receiver);
        }
        Object prev = null;
        try {
            prev = PolyglotValueDispatch.hostEnter(languageContext);
        }
        catch (Throwable t) {
            return System.identityHashCode(receiver);
        }
        try {
            InteropLibrary receiverLib = InteropLibrary.getUncached(receiver);
            if (receiverLib.hasIdentity(receiver)) {
                int n2 = receiverLib.identityHashCode(receiver);
                return n2;
            }
            n = System.identityHashCode(receiver);
        }
        catch (Throwable t) {
            throw PolyglotImpl.guestToHostException(languageContext, t, true);
        }
        finally {
            try {
                PolyglotValueDispatch.hostLeave(languageContext, prev);
            }
            catch (Throwable throwable) {}
        }
        return n;
    }

    public static String toString(PolyglotWrapper thisObj) {
        PolyglotLanguageContext thisContext = thisObj.getLanguageContext();
        Object thisGuestObject = thisObj.getGuestObject();
        if (thisContext != null) {
            try {
                return thisContext.asValue(thisGuestObject).toString();
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        return "Error in toString()";
    }
}

