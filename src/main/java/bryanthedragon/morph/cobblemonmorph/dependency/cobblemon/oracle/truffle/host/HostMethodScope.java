
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
import com.oracle.truffle.host.HostEngineException;
import com.oracle.truffle.host.HostLanguage;
import com.oracle.truffle.host.HostMethodDesc;
import java.lang.reflect.Field;
import java.util.Arrays;
import sun.misc.Unsafe;

final class HostMethodScope {
    private static final ScopedObject[] EMTPY_SCOPE_ARRAY = new ScopedObject[0];
    private static final Unsafe UNSAFE = HostMethodScope.getUnsafe();
    private ScopedObject[] scope;
    private int nextDynamicIndex;

    HostMethodScope(ScopedObject[] staticScope) {
        this.scope = staticScope;
        this.nextDynamicIndex = staticScope.length;
    }

    HostMethodScope(int initialDynamicCapacity) {
        this.scope = new ScopedObject[initialDynamicCapacity];
        this.nextDynamicIndex = 0;
    }

    static HostMethodScope openDynamic(HostMethodDesc.SingleMethod method, int argumentCount, BranchProfile seenScope) {
        if (method.hasScopedParameters()) {
            seenScope.enter();
            return new HostMethodScope(argumentCount);
        }
        return null;
    }

    static HostMethodScope openStatic(HostMethodDesc.SingleMethod method) {
        CompilerAsserts.partialEvaluationConstant(method);
        if (method.hasScopedParameters()) {
            int[] scopePos = method.getScopedParameters();
            ScopedObject[] scopeArray = scopePos.length > 0 ? new ScopedObject[scopePos.length] : EMTPY_SCOPE_ARRAY;
            return new HostMethodScope(scopeArray);
        }
        return null;
    }

    static Object addToScopeDynamic(HostMethodScope scope, Object value2) {
        if (scope != null) {
            assert (!(value2 instanceof ScopedObject));
            return scope.addToScopeDynamicImpl(value2);
        }
        return value2;
    }

    static Object addToScopeStatic(HostMethodScope scope, HostMethodDesc.SingleMethod method, int argumentIndex, Object value2) {
        CompilerAsserts.partialEvaluationConstant(method);
        if (scope != null) {
            assert (!(value2 instanceof ScopedObject));
            int[] scopePos = method.getScopedParameters();
            int targetIndex = scopePos[argumentIndex];
            if (targetIndex != -1) {
                scope.scope[targetIndex] = new ScopedObject(scope, value2, targetIndex);
                return scope.scope[targetIndex];
            }
        }
        return value2;
    }

    static void pin(Object value2) {
        if (value2 instanceof ScopedObject) {
            ((ScopedObject)value2).pin();
        }
    }

    static void closeStatic(HostMethodScope scope, HostMethodDesc.SingleMethod method, BranchProfile seenDynamicScope) {
        if (method.hasScopedParameters()) {
            ScopedObject o;
            int i;
            int[] scopePos = method.getScopedParameters();
            ScopedObject[] array = scope.scope;
            for (i = 0; i < scopePos.length; ++i) {
                o = array[i];
                if (o == null) continue;
                o.release();
            }
            for (i = scopePos.length; i < array.length; ++i) {
                seenDynamicScope.enter();
                o = array[i];
                if (o == null) continue;
                o.release();
            }
        } else assert (scope == null);
    }

    static void closeDynamic(HostMethodScope scope, HostMethodDesc.SingleMethod method) {
        if (method.hasScopedParameters()) {
            ScopedObject[] array = scope.scope;
            for (int i = 0; i < array.length; ++i) {
                ScopedObject o = array[i];
                if (o == null) continue;
                o.release();
            }
        } else assert (scope == null);
    }

    @CompilerDirectives.TruffleBoundary
    private synchronized Object addToScopeDynamicImpl(Object argument) {
        int index = this.nextDynamicIndex;
        ScopedObject[] localScope = this.scope;
        if (index >= localScope.length) {
            this.scope = localScope = Arrays.copyOf(localScope, localScope.length << 1);
        }
        if (index < 0) {
            throw HostMethodScope.createReleaseException("Too many scoped values created for scoped method instance.");
        }
        ScopedObject newArgument = localScope[index] = new ScopedObject(this, argument, index);
        this.nextDynamicIndex = index + 1;
        return newArgument;
    }

    @CompilerDirectives.TruffleBoundary
    private static RuntimeException createReleaseException(String message) {
        return HostEngineException.toEngineException(HostLanguage.get(null).access, new IllegalStateException("This scoped object has already been released. " + message));
    }

    private static Unsafe getUnsafe() {
        try {
            return Unsafe.getUnsafe();
        }
        catch (SecurityException securityException) {
            try {
                Field theUnsafeInstance = Unsafe.class.getDeclaredField("theUnsafe");
                theUnsafeInstance.setAccessible(true);
                return (Unsafe)theUnsafeInstance.get(Unsafe.class);
            }
            catch (Exception e) {
                throw new RuntimeException("exception while trying to get Unsafe.theUnsafe via reflection:", e);
            }
        }
    }

    @ExportLibrary(value=ReflectionLibrary.class)
    static final class ScopedObject
    implements TruffleObject {
        static final Object OTHER_VALUE;
        static final ReflectionLibrary OTHER_VALUE_UNCACHED;
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
        Object send(Message message, Object[] args, @CachedLibrary(limit="5") ReflectionLibrary library, @Cached BranchProfile seenError, @Cached BranchProfile seenOther) throws Exception {
            if (message.getLibraryClass() != InteropLibrary.class) {
                seenOther.enter();
                return ScopedObject.fallbackSend(message, args);
            }
            Object d = this.delegate;
            if (d == null) {
                seenError.enter();
                throw HostMethodScope.createReleaseException("Released objects cannot be accessed. Avoid accessing scoped objects after their corresponding method has finished execution. Alternatively, use Value.pin() to prevent a scoped object from being released after the host call completed.");
            }
            assert (d != null) : "delegate must not be null here";
            Object returnValue = library.send(d, message, args);
            if (message.getReturnType() == Object.class && !(d instanceof PinnedObject)) {
                return HostMethodScope.addToScopeDynamic(this.scope, returnValue);
            }
            return returnValue;
        }

        @CompilerDirectives.TruffleBoundary
        private static Object fallbackSend(Message message, Object[] args) throws Exception {
            return OTHER_VALUE_UNCACHED.send(OTHER_VALUE, message, args);
        }

        void release() {
            Object d = this.delegate;
            assert (d != null);
            if (d instanceof PinnedObject || !UNSAFE.compareAndSwapObject(this, DELEGATE_OFFSET, d, null)) {
                return;
            }
            assert (this.delegate == null) : "Scoped objects can only be released once.";
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        void pin() {
            HostMethodScope s;
            PinnedObject update;
            Object expect;
            do {
                s = this.scope;
                expect = this.delegate;
                if (expect instanceof PinnedObject) {
                    return;
                }
                if (expect != null) continue;
                throw HostMethodScope.createReleaseException("Released objects cannot be pinned.");
            } while (!UNSAFE.compareAndSwapObject(this, DELEGATE_OFFSET, expect, update = new PinnedObject(expect)));
            this.scope = null;
            HostMethodScope hostMethodScope = s;
            synchronized (hostMethodScope) {
                s.scope[this.index] = null;
            }
            assert (this.delegate != null) : "delegate must not be set to null after pinning ";
        }

        Object unwrapForGuest() {
            Object d = this.delegate;
            if (d == null) {
                throw HostMethodScope.createReleaseException("Released objects cannot be converted to a guest value.");
            }
            if (d instanceof PinnedObject) {
                return ((PinnedObject)d).delegate;
            }
            return d;
        }

        static {
            Field f;
            OTHER_VALUE = new Object();
            OTHER_VALUE_UNCACHED = ReflectionLibrary.getFactory().getUncached(OTHER_VALUE);
            try {
                f = ScopedObject.class.getDeclaredField("delegate");
            }
            catch (NoSuchFieldException | SecurityException e) {
                throw CompilerDirectives.shouldNotReachHere(e);
            }
            DELEGATE_OFFSET = UNSAFE.objectFieldOffset(f);
        }
    }

    @ExportLibrary(value=InteropLibrary.class, delegateTo="delegate")
    static final class PinnedObject
    implements TruffleObject {
        final Object delegate;

        PinnedObject(Object delegate) {
            this.delegate = delegate;
        }
    }
}

