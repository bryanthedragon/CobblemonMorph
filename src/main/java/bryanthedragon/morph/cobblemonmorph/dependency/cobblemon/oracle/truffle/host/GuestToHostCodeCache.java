package com.oracle.truffle.host;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.TruffleOptions;
import com.oracle.truffle.api.interop.InteropException;
import com.oracle.truffle.api.interop.InvalidArrayIndexException;
import com.oracle.truffle.api.interop.StopIterationException;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
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
   final CallTarget methodHandleHostInvoke = (new GuestToHostRootNode(HostObject.class, "doInvoke") {
      @Override
      protected Object executeImpl(Object receiver, Object[] callArguments) {
         if (TruffleOptions.AOT) {
            throw CompilerDirectives.shouldNotReachHere("MHBase.invokeHandle can only be used in non AOT mode.");
         } else {
            MethodHandle methodHandle = (MethodHandle)callArguments[2];
            Object[] arguments = (Object[])callArguments[3];

            try {
               return HostMethodDesc.SingleMethod.MHBase.invokeHandle(methodHandle, receiver, arguments);
            } catch (Throwable var7) {
               throw HostInteropReflect.rethrow(var7);
            }
         }
      }
   }).getCallTarget();
   final CallTarget reflectionHostInvoke = (new GuestToHostRootNode(HostObject.class, "doInvoke") {
      @Override
      protected Object executeImpl(Object obj, Object[] callArguments) {
         HostMethodDesc.SingleMethod.ReflectBase method = (HostMethodDesc.SingleMethod.ReflectBase)callArguments[2];
         Object[] arguments = (Object[])callArguments[3];

         try {
            return method.invoke(obj, arguments);
         } catch (Throwable var7) {
            throw HostInteropReflect.rethrow(var7);
         }
      }
   }).getCallTarget();
   final CallTarget execute = (new GuestToHostRootNode(ProxyExecutable.class, "execute") {
      @CompilerDirectives.TruffleBoundary
      @Override
      protected Object executeImpl(Object proxy, Object[] arguments) throws UnsupportedMessageException {
         try {
            return ((ProxyExecutable)proxy).execute((Value[])arguments[2]);
         } catch (UnsupportedOperationException var4) {
            throw UnsupportedMessageException.create();
         }
      }
   }).getCallTarget();
   final CallTarget asPointer = (new GuestToHostRootNode(ProxyNativeObject.class, "asPointer") {
      @CompilerDirectives.TruffleBoundary
      @Override
      protected Object executeImpl(Object proxy, Object[] arguments) {
         return ((ProxyNativeObject)proxy).asPointer();
      }
   }).getCallTarget();
   final CallTarget instantiate = (new GuestToHostRootNode(ProxyInstantiable.class, "newInstance") {
      @CompilerDirectives.TruffleBoundary
      @Override
      protected Object executeImpl(Object proxy, Object[] arguments) throws UnsupportedMessageException {
         try {
            return ((ProxyInstantiable)proxy).newInstance((Value[])arguments[2]);
         } catch (UnsupportedOperationException var4) {
            throw UnsupportedMessageException.create();
         }
      }
   }).getCallTarget();
   final CallTarget arrayGet = (new GuestToHostRootNode(ProxyArray.class, "get") {
      @Override
      protected Object executeImpl(Object proxy, Object[] arguments) throws InvalidArrayIndexException, UnsupportedMessageException {
         long index = (Long)arguments[2];

         try {
            return this.boundaryGet((ProxyArray)proxy, index);
         } catch (ArrayIndexOutOfBoundsException var6) {
            throw InvalidArrayIndexException.create(index);
         } catch (UnsupportedOperationException var7) {
            throw UnsupportedMessageException.create();
         }
      }

      @CompilerDirectives.TruffleBoundary
      private Object boundaryGet(ProxyArray proxy, long index) {
         return proxy.get(index);
      }
   }).getCallTarget();
   final CallTarget arraySet = (new GuestToHostRootNode(ProxyArray.class, "set") {
      @Override
      protected Object executeImpl(Object proxy, Object[] arguments) throws InvalidArrayIndexException, UnsupportedMessageException {
         long index = (Long)arguments[2];

         try {
            this.boundarySet((ProxyArray)proxy, index, (Value)arguments[3]);
            return null;
         } catch (ArrayIndexOutOfBoundsException var6) {
            throw InvalidArrayIndexException.create(index);
         } catch (UnsupportedOperationException var7) {
            throw UnsupportedMessageException.create();
         }
      }

      @CompilerDirectives.TruffleBoundary
      private void boundarySet(ProxyArray proxy, long index, Value value) {
         proxy.set(index, value);
      }
   }).getCallTarget();
   final CallTarget arrayRemove = (new GuestToHostRootNode(ProxyArray.class, "remove") {
      @Override
      protected Object executeImpl(Object proxy, Object[] arguments) throws InvalidArrayIndexException, UnsupportedMessageException {
         long index = (Long)arguments[2];

         try {
            return this.boundaryRemove((ProxyArray)proxy, index);
         } catch (ArrayIndexOutOfBoundsException var6) {
            throw InvalidArrayIndexException.create(index);
         } catch (UnsupportedOperationException var7) {
            throw UnsupportedMessageException.create();
         }
      }

      @CompilerDirectives.TruffleBoundary
      private boolean boundaryRemove(ProxyArray proxy, long index) {
         return proxy.remove(index);
      }
   }).getCallTarget();
   final CallTarget arraySize = (new GuestToHostRootNode(ProxyArray.class, "getSize") {
      @CompilerDirectives.TruffleBoundary
      @Override
      protected Object executeImpl(Object proxy, Object[] arguments) {
         return ((ProxyArray)proxy).getSize();
      }
   }).getCallTarget();
   final CallTarget memberKeys = (new GuestToHostRootNode(ProxyObject.class, "getMemberKeys") {
      @CompilerDirectives.TruffleBoundary
      @Override
      protected Object executeImpl(Object proxy, Object[] arguments) {
         return ((ProxyObject)proxy).getMemberKeys();
      }
   }).getCallTarget();
   final CallTarget getMember = (new GuestToHostRootNode(ProxyObject.class, "getMember") {
      @Override
      protected Object executeImpl(Object proxy, Object[] arguments) throws UnsupportedMessageException {
         try {
            return this.boundaryGetMember((ProxyObject)proxy, (String)arguments[2]);
         } catch (UnsupportedOperationException var4) {
            throw UnsupportedMessageException.create();
         }
      }

      @CompilerDirectives.TruffleBoundary
      private Object boundaryGetMember(ProxyObject proxy, String argument) {
         return proxy.getMember(argument);
      }
   }).getCallTarget();
   final CallTarget putMember = (new GuestToHostRootNode(ProxyObject.class, "putMember") {
      @Override
      protected Object executeImpl(Object proxy, Object[] arguments) throws UnsupportedMessageException {
         try {
            this.boundaryPutMember((ProxyObject)proxy, (String)arguments[2], (Value)arguments[3]);
            return null;
         } catch (UnsupportedOperationException var4) {
            throw UnsupportedMessageException.create();
         }
      }

      @CompilerDirectives.TruffleBoundary
      private void boundaryPutMember(ProxyObject proxy, String member, Value value) {
         proxy.putMember(member, value);
      }
   }).getCallTarget();
   final CallTarget removeMember = (new GuestToHostRootNode(ProxyObject.class, "removeMember") {
      @Override
      protected Object executeImpl(Object proxy, Object[] arguments) throws UnsupportedMessageException {
         try {
            return this.removeBoundary((ProxyObject)proxy, (String)arguments[2]);
         } catch (UnsupportedOperationException var4) {
            throw UnsupportedMessageException.create();
         }
      }

      @CompilerDirectives.TruffleBoundary
      private boolean removeBoundary(ProxyObject proxy, String member) {
         return proxy.removeMember(member);
      }
   }).getCallTarget();
   final CallTarget hasMember = (new GuestToHostRootNode(ProxyObject.class, "hasMember") {
      @CompilerDirectives.TruffleBoundary
      @Override
      protected Object executeImpl(Object proxy, Object[] arguments) {
         return ((ProxyObject)proxy).hasMember((String)arguments[2]);
      }
   }).getCallTarget();
   final CallTarget asTimezone = (new GuestToHostRootNode(ProxyTimeZone.class, "asTimeZone") {
      @CompilerDirectives.TruffleBoundary
      @Override
      protected Object executeImpl(Object proxy, Object[] arguments) {
         ZoneId zone = ((ProxyTimeZone)proxy).asTimeZone();
         if (zone == null) {
            throw CompilerDirectives.shouldNotReachHere("The returned zone must not be null.");
         } else {
            return zone;
         }
      }
   }).getCallTarget();
   final CallTarget asDate = (new GuestToHostRootNode(ProxyDate.class, "asDate") {
      @CompilerDirectives.TruffleBoundary
      @Override
      protected Object executeImpl(Object proxy, Object[] arguments) {
         LocalDate date = ((ProxyDate)proxy).asDate();
         if (date == null) {
            throw new AssertionError("The returned date must not be null.");
         } else {
            return date;
         }
      }
   }).getCallTarget();
   final CallTarget asTime = (new GuestToHostRootNode(ProxyTime.class, "asTime") {
      @CompilerDirectives.TruffleBoundary
      @Override
      protected Object executeImpl(Object proxy, Object[] arguments) {
         LocalTime time = ((ProxyTime)proxy).asTime();
         if (time == null) {
            throw new AssertionError("The returned time must not be null.");
         } else {
            return time;
         }
      }
   }).getCallTarget();
   final CallTarget asInstant = (new GuestToHostRootNode(ProxyInstant.class, "asInstant") {
      @CompilerDirectives.TruffleBoundary
      @Override
      protected Object executeImpl(Object proxy, Object[] arguments) {
         Instant instant = ((ProxyInstant)proxy).asInstant();
         if (instant == null) {
            throw new AssertionError("The returned instant must not be null.");
         } else {
            return instant;
         }
      }
   }).getCallTarget();
   final CallTarget asDuration = (new GuestToHostRootNode(ProxyDuration.class, "asDuration") {
      @CompilerDirectives.TruffleBoundary
      @Override
      protected Object executeImpl(Object proxy, Object[] arguments) {
         Duration duration = ((ProxyDuration)proxy).asDuration();
         if (duration == null) {
            throw new AssertionError("The returned duration must not be null.");
         } else {
            return duration;
         }
      }
   }).getCallTarget();
   final CallTarget getIterator = (new GuestToHostRootNode(ProxyIterable.class, "getIterator") {
      @CompilerDirectives.TruffleBoundary
      @Override
      protected Object executeImpl(Object proxy, Object[] arguments) {
         return ((ProxyIterable)proxy).getIterator();
      }
   }).getCallTarget();
   final CallTarget hasIteratorNextElement = (new GuestToHostRootNode(ProxyIterator.class, "hasIteratorNextElement") {
      @CompilerDirectives.TruffleBoundary
      @Override
      protected Object executeImpl(Object proxy, Object[] arguments) {
         return ((ProxyIterator)proxy).hasNext();
      }
   }).getCallTarget();
   final CallTarget getIteratorNextElement = (new GuestToHostRootNode(ProxyIterator.class, "getIteratorNextElement") {
      @CompilerDirectives.TruffleBoundary
      @Override
      protected Object executeImpl(Object proxy, Object[] arguments) throws StopIterationException, UnsupportedMessageException {
         try {
            return ((ProxyIterator)proxy).getNext();
         } catch (NoSuchElementException var4) {
            throw StopIterationException.create();
         } catch (UnsupportedOperationException var5) {
            throw UnsupportedMessageException.create();
         }
      }
   }).getCallTarget();
   final CallTarget hasHashEntry = (new GuestToHostRootNode(ProxyHashMap.class, "hasEntry") {
      @CompilerDirectives.TruffleBoundary
      @Override
      protected Object executeImpl(Object receiver, Object[] arguments) throws InteropException {
         return ((ProxyHashMap)receiver).hasHashEntry((Value)arguments[2]);
      }
   }).getCallTarget();
   final CallTarget getHashSize = (new GuestToHostRootNode(ProxyHashMap.class, "getSize") {
      @CompilerDirectives.TruffleBoundary
      @Override
      protected Object executeImpl(Object receiver, Object[] arguments) throws InteropException {
         return ((ProxyHashMap)receiver).getHashSize();
      }
   }).getCallTarget();
   final CallTarget getHashValue = (new GuestToHostRootNode(ProxyHashMap.class, "getValue") {
      @CompilerDirectives.TruffleBoundary
      @Override
      protected Object executeImpl(Object receiver, Object[] arguments) throws InteropException {
         try {
            return ((ProxyHashMap)receiver).getHashValue((Value)arguments[2]);
         } catch (UnsupportedOperationException var4) {
            throw UnsupportedMessageException.create();
         }
      }
   }).getCallTarget();
   final CallTarget putHashEntry = (new GuestToHostRootNode(ProxyHashMap.class, "putEntry") {
      @CompilerDirectives.TruffleBoundary
      @Override
      protected Object executeImpl(Object receiver, Object[] arguments) throws InteropException {
         try {
            ((ProxyHashMap)receiver).putHashEntry((Value)arguments[2], (Value)arguments[3]);
            return null;
         } catch (UnsupportedOperationException var4) {
            throw UnsupportedMessageException.create();
         }
      }
   }).getCallTarget();
   final CallTarget removeHashEntry = (new GuestToHostRootNode(ProxyHashMap.class, "removeEntry") {
      @CompilerDirectives.TruffleBoundary
      @Override
      protected Object executeImpl(Object receiver, Object[] arguments) throws InteropException {
         try {
            return ((ProxyHashMap)receiver).removeHashEntry((Value)arguments[2]);
         } catch (UnsupportedOperationException var4) {
            throw UnsupportedMessageException.create();
         }
      }
   }).getCallTarget();
   final CallTarget getHashEntriesIterator = (new GuestToHostRootNode(ProxyHashMap.class, "getEntriesIterator") {
      @CompilerDirectives.TruffleBoundary
      @Override
      protected Object executeImpl(Object receiver, Object[] arguments) throws InteropException {
         return ((ProxyHashMap)receiver).getHashEntriesIterator();
      }
   }).getCallTarget();

   GuestToHostCodeCache(HostLanguage language) {
      this.language = language;
   }
}
