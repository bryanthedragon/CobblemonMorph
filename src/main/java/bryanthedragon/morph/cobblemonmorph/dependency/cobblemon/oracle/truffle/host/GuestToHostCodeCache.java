
package com.oracle.truffle.host;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.TruffleOptions;
import com.oracle.truffle.api.interop.InteropException;
import com.oracle.truffle.api.interop.InvalidArrayIndexException;
import com.oracle.truffle.api.interop.StopIterationException;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.host.GuestToHostRootNode;
import com.oracle.truffle.host.HostInteropReflect;
import com.oracle.truffle.host.HostLanguage;
import com.oracle.truffle.host.HostMethodDesc;
import com.oracle.truffle.host.HostObject;
import java.lang.invoke.MethodHandle;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.NoSuchElementException;
import org.graalvm.polyglot.Value;
import org.graalvm.polyglot.proxy.ProxyArray;
import org.graalvm.polyglot.proxy.ProxyDate;
import org.graalvm.polyglot.proxy.ProxyDuration;
import org.graalvm.polyglot.proxy.ProxyExecutable;
import org.graalvm.polyglot.proxy.ProxyHashMap;
import org.graalvm.polyglot.proxy.ProxyInstant;
import org.graalvm.polyglot.proxy.ProxyInstantiable;
import org.graalvm.polyglot.proxy.ProxyIterable;
import org.graalvm.polyglot.proxy.ProxyIterator;
import org.graalvm.polyglot.proxy.ProxyNativeObject;
import org.graalvm.polyglot.proxy.ProxyObject;
import org.graalvm.polyglot.proxy.ProxyTime;
import org.graalvm.polyglot.proxy.ProxyTimeZone;

final class GuestToHostCodeCache {
    final HostLanguage language;
    final CallTarget methodHandleHostInvoke = new GuestToHostRootNode(HostObject.class, "doInvoke"){

        @Override
        protected Object executeImpl(Object receiver, Object[] callArguments) {
            Object ret;
            if (TruffleOptions.AOT) {
                throw CompilerDirectives.shouldNotReachHere("MHBase.invokeHandle can only be used in non AOT mode.");
            }
            MethodHandle methodHandle = (MethodHandle)callArguments[2];
            Object[] arguments = (Object[])callArguments[3];
            try {
                ret = HostMethodDesc.SingleMethod.MHBase.invokeHandle(methodHandle, receiver, arguments);
            }
            catch (Throwable e) {
                throw HostInteropReflect.rethrow(e);
            }
            return ret;
        }
    }.getCallTarget();
    final CallTarget reflectionHostInvoke = new GuestToHostRootNode(HostObject.class, "doInvoke"){

        @Override
        protected Object executeImpl(Object obj, Object[] callArguments) {
            Object ret;
            HostMethodDesc.SingleMethod.ReflectBase method = (HostMethodDesc.SingleMethod.ReflectBase)callArguments[2];
            Object[] arguments = (Object[])callArguments[3];
            try {
                ret = method.invoke(obj, arguments);
            }
            catch (Throwable e) {
                throw HostInteropReflect.rethrow(e);
            }
            return ret;
        }
    }.getCallTarget();
    final CallTarget execute = new GuestToHostRootNode(ProxyExecutable.class, "execute"){

        @Override
        @CompilerDirectives.TruffleBoundary
        protected Object executeImpl(Object proxy, Object[] arguments) throws UnsupportedMessageException {
            try {
                return ((ProxyExecutable)proxy).execute((Value[])arguments[2]);
            }
            catch (UnsupportedOperationException e) {
                throw UnsupportedMessageException.create();
            }
        }
    }.getCallTarget();
    final CallTarget asPointer = new GuestToHostRootNode(ProxyNativeObject.class, "asPointer"){

        @Override
        @CompilerDirectives.TruffleBoundary
        protected Object executeImpl(Object proxy, Object[] arguments) {
            return ((ProxyNativeObject)proxy).asPointer();
        }
    }.getCallTarget();
    final CallTarget instantiate = new GuestToHostRootNode(ProxyInstantiable.class, "newInstance"){

        @Override
        @CompilerDirectives.TruffleBoundary
        protected Object executeImpl(Object proxy, Object[] arguments) throws UnsupportedMessageException {
            try {
                return ((ProxyInstantiable)proxy).newInstance((Value[])arguments[2]);
            }
            catch (UnsupportedOperationException e) {
                throw UnsupportedMessageException.create();
            }
        }
    }.getCallTarget();
    final CallTarget arrayGet = new GuestToHostRootNode(ProxyArray.class, "get"){

        @Override
        protected Object executeImpl(Object proxy, Object[] arguments) throws InvalidArrayIndexException, UnsupportedMessageException {
            long index = (Long)arguments[2];
            try {
                return this.boundaryGet((ProxyArray)proxy, index);
            }
            catch (ArrayIndexOutOfBoundsException e) {
                throw InvalidArrayIndexException.create(index);
            }
            catch (UnsupportedOperationException e) {
                throw UnsupportedMessageException.create();
            }
        }

        @CompilerDirectives.TruffleBoundary
        private Object boundaryGet(ProxyArray proxy, long index) {
            return proxy.get(index);
        }
    }.getCallTarget();
    final CallTarget arraySet = new GuestToHostRootNode(ProxyArray.class, "set"){

        @Override
        protected Object executeImpl(Object proxy, Object[] arguments) throws InvalidArrayIndexException, UnsupportedMessageException {
            long index = (Long)arguments[2];
            try {
                this.boundarySet((ProxyArray)proxy, index, (Value)arguments[3]);
            }
            catch (ArrayIndexOutOfBoundsException e) {
                throw InvalidArrayIndexException.create(index);
            }
            catch (UnsupportedOperationException e) {
                throw UnsupportedMessageException.create();
            }
            return null;
        }

        @CompilerDirectives.TruffleBoundary
        private void boundarySet(ProxyArray proxy, long index, Value value2) {
            proxy.set(index, value2);
        }
    }.getCallTarget();
    final CallTarget arrayRemove = new GuestToHostRootNode(ProxyArray.class, "remove"){

        @Override
        protected Object executeImpl(Object proxy, Object[] arguments) throws InvalidArrayIndexException, UnsupportedMessageException {
            long index = (Long)arguments[2];
            try {
                return this.boundaryRemove((ProxyArray)proxy, index);
            }
            catch (ArrayIndexOutOfBoundsException e) {
                throw InvalidArrayIndexException.create(index);
            }
            catch (UnsupportedOperationException e) {
                throw UnsupportedMessageException.create();
            }
        }

        @CompilerDirectives.TruffleBoundary
        private boolean boundaryRemove(ProxyArray proxy, long index) {
            return proxy.remove(index);
        }
    }.getCallTarget();
    final CallTarget arraySize = new GuestToHostRootNode(ProxyArray.class, "getSize"){

        @Override
        @CompilerDirectives.TruffleBoundary
        protected Object executeImpl(Object proxy, Object[] arguments) {
            return ((ProxyArray)proxy).getSize();
        }
    }.getCallTarget();
    final CallTarget memberKeys = new GuestToHostRootNode(ProxyObject.class, "getMemberKeys"){

        @Override
        @CompilerDirectives.TruffleBoundary
        protected Object executeImpl(Object proxy, Object[] arguments) {
            return ((ProxyObject)proxy).getMemberKeys();
        }
    }.getCallTarget();
    final CallTarget getMember = new GuestToHostRootNode(ProxyObject.class, "getMember"){

        @Override
        protected Object executeImpl(Object proxy, Object[] arguments) throws UnsupportedMessageException {
            try {
                return this.boundaryGetMember((ProxyObject)proxy, (String)arguments[2]);
            }
            catch (UnsupportedOperationException e) {
                throw UnsupportedMessageException.create();
            }
        }

        @CompilerDirectives.TruffleBoundary
        private Object boundaryGetMember(ProxyObject proxy, String argument) {
            return proxy.getMember(argument);
        }
    }.getCallTarget();
    final CallTarget putMember = new GuestToHostRootNode(ProxyObject.class, "putMember"){

        @Override
        protected Object executeImpl(Object proxy, Object[] arguments) throws UnsupportedMessageException {
            try {
                this.boundaryPutMember((ProxyObject)proxy, (String)arguments[2], (Value)arguments[3]);
            }
            catch (UnsupportedOperationException e) {
                throw UnsupportedMessageException.create();
            }
            return null;
        }

        @CompilerDirectives.TruffleBoundary
        private void boundaryPutMember(ProxyObject proxy, String member, Value value2) {
            proxy.putMember(member, value2);
        }
    }.getCallTarget();
    final CallTarget removeMember = new GuestToHostRootNode(ProxyObject.class, "removeMember"){

        @Override
        protected Object executeImpl(Object proxy, Object[] arguments) throws UnsupportedMessageException {
            try {
                return this.removeBoundary((ProxyObject)proxy, (String)arguments[2]);
            }
            catch (UnsupportedOperationException e) {
                throw UnsupportedMessageException.create();
            }
        }

        @CompilerDirectives.TruffleBoundary
        private boolean removeBoundary(ProxyObject proxy, String member) {
            return proxy.removeMember(member);
        }
    }.getCallTarget();
    final CallTarget hasMember = new GuestToHostRootNode(ProxyObject.class, "hasMember"){

        @Override
        @CompilerDirectives.TruffleBoundary
        protected Object executeImpl(Object proxy, Object[] arguments) {
            return ((ProxyObject)proxy).hasMember((String)arguments[2]);
        }
    }.getCallTarget();
    final CallTarget asTimezone = new GuestToHostRootNode(ProxyTimeZone.class, "asTimeZone"){

        @Override
        @CompilerDirectives.TruffleBoundary
        protected Object executeImpl(Object proxy, Object[] arguments) {
            ZoneId zone = ((ProxyTimeZone)proxy).asTimeZone();
            if (zone == null) {
                throw CompilerDirectives.shouldNotReachHere("The returned zone must not be null.");
            }
            return zone;
        }
    }.getCallTarget();
    final CallTarget asDate = new GuestToHostRootNode(ProxyDate.class, "asDate"){

        @Override
        @CompilerDirectives.TruffleBoundary
        protected Object executeImpl(Object proxy, Object[] arguments) {
            LocalDate date = ((ProxyDate)proxy).asDate();
            if (date == null) {
                throw new AssertionError((Object)"The returned date must not be null.");
            }
            return date;
        }
    }.getCallTarget();
    final CallTarget asTime = new GuestToHostRootNode(ProxyTime.class, "asTime"){

        @Override
        @CompilerDirectives.TruffleBoundary
        protected Object executeImpl(Object proxy, Object[] arguments) {
            LocalTime time = ((ProxyTime)proxy).asTime();
            if (time == null) {
                throw new AssertionError((Object)"The returned time must not be null.");
            }
            return time;
        }
    }.getCallTarget();
    final CallTarget asInstant = new GuestToHostRootNode(ProxyInstant.class, "asInstant"){

        @Override
        @CompilerDirectives.TruffleBoundary
        protected Object executeImpl(Object proxy, Object[] arguments) {
            Instant instant = ((ProxyInstant)proxy).asInstant();
            if (instant == null) {
                throw new AssertionError((Object)"The returned instant must not be null.");
            }
            return instant;
        }
    }.getCallTarget();
    final CallTarget asDuration = new GuestToHostRootNode(ProxyDuration.class, "asDuration"){

        @Override
        @CompilerDirectives.TruffleBoundary
        protected Object executeImpl(Object proxy, Object[] arguments) {
            Duration duration = ((ProxyDuration)proxy).asDuration();
            if (duration == null) {
                throw new AssertionError((Object)"The returned duration must not be null.");
            }
            return duration;
        }
    }.getCallTarget();
    final CallTarget getIterator = new GuestToHostRootNode(ProxyIterable.class, "getIterator"){

        @Override
        @CompilerDirectives.TruffleBoundary
        protected Object executeImpl(Object proxy, Object[] arguments) {
            return ((ProxyIterable)proxy).getIterator();
        }
    }.getCallTarget();
    final CallTarget hasIteratorNextElement = new GuestToHostRootNode(ProxyIterator.class, "hasIteratorNextElement"){

        @Override
        @CompilerDirectives.TruffleBoundary
        protected Object executeImpl(Object proxy, Object[] arguments) {
            return ((ProxyIterator)proxy).hasNext();
        }
    }.getCallTarget();
    final CallTarget getIteratorNextElement = new GuestToHostRootNode(ProxyIterator.class, "getIteratorNextElement"){

        @Override
        @CompilerDirectives.TruffleBoundary
        protected Object executeImpl(Object proxy, Object[] arguments) throws StopIterationException, UnsupportedMessageException {
            try {
                return ((ProxyIterator)proxy).getNext();
            }
            catch (NoSuchElementException e) {
                throw StopIterationException.create();
            }
            catch (UnsupportedOperationException e) {
                throw UnsupportedMessageException.create();
            }
        }
    }.getCallTarget();
    final CallTarget hasHashEntry = new GuestToHostRootNode(ProxyHashMap.class, "hasEntry"){

        @Override
        @CompilerDirectives.TruffleBoundary
        protected Object executeImpl(Object receiver, Object[] arguments) throws InteropException {
            return ((ProxyHashMap)receiver).hasHashEntry((Value)arguments[2]);
        }
    }.getCallTarget();
    final CallTarget getHashSize = new GuestToHostRootNode(ProxyHashMap.class, "getSize"){

        @Override
        @CompilerDirectives.TruffleBoundary
        protected Object executeImpl(Object receiver, Object[] arguments) throws InteropException {
            return ((ProxyHashMap)receiver).getHashSize();
        }
    }.getCallTarget();
    final CallTarget getHashValue = new GuestToHostRootNode(ProxyHashMap.class, "getValue"){

        @Override
        @CompilerDirectives.TruffleBoundary
        protected Object executeImpl(Object receiver, Object[] arguments) throws InteropException {
            try {
                return ((ProxyHashMap)receiver).getHashValue((Value)arguments[2]);
            }
            catch (UnsupportedOperationException e) {
                throw UnsupportedMessageException.create();
            }
        }
    }.getCallTarget();
    final CallTarget putHashEntry = new GuestToHostRootNode(ProxyHashMap.class, "putEntry"){

        @Override
        @CompilerDirectives.TruffleBoundary
        protected Object executeImpl(Object receiver, Object[] arguments) throws InteropException {
            try {
                ((ProxyHashMap)receiver).putHashEntry((Value)arguments[2], (Value)arguments[3]);
                return null;
            }
            catch (UnsupportedOperationException e) {
                throw UnsupportedMessageException.create();
            }
        }
    }.getCallTarget();
    final CallTarget removeHashEntry = new GuestToHostRootNode(ProxyHashMap.class, "removeEntry"){

        @Override
        @CompilerDirectives.TruffleBoundary
        protected Object executeImpl(Object receiver, Object[] arguments) throws InteropException {
            try {
                return ((ProxyHashMap)receiver).removeHashEntry((Value)arguments[2]);
            }
            catch (UnsupportedOperationException e) {
                throw UnsupportedMessageException.create();
            }
        }
    }.getCallTarget();
    final CallTarget getHashEntriesIterator = new GuestToHostRootNode(ProxyHashMap.class, "getEntriesIterator"){

        @Override
        @CompilerDirectives.TruffleBoundary
        protected Object executeImpl(Object receiver, Object[] arguments) throws InteropException {
            return ((ProxyHashMap)receiver).getHashEntriesIterator();
        }
    }.getCallTarget();

    GuestToHostCodeCache(HostLanguage language) {
        this.language = language;
    }
}

