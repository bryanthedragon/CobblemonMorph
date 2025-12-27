package com.oracle.truffle.host;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Fallback;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.interop.ArityException;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.InvalidArrayIndexException;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.interop.UnknownIdentifierException;
import com.oracle.truffle.api.interop.UnknownKeyException;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.interop.UnsupportedTypeException;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.library.ExportMessage;
import com.oracle.truffle.api.utilities.TriState;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import org.graalvm.polyglot.Value;
import org.graalvm.polyglot.proxy.Proxy;
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

@ExportLibrary(InteropLibrary.class)
final class HostProxy implements TruffleObject {
   static final int LIMIT = 5;
   private static final ProxyArray EMPTY = new ProxyArray() {
      @Override
      public void set(long index, Value value) {
         throw new ArrayIndexOutOfBoundsException();
      }

      @Override
      public long getSize() {
         return 0L;
      }

      @Override
      public Object get(long index) {
         throw new ArrayIndexOutOfBoundsException();
      }
   };
   final Proxy proxy;
   final HostContext context;

   HostProxy(HostContext context, Proxy proxy) {
      this.context = context;
      this.proxy = proxy;
   }

   static Object withContext(Object obj, HostContext context) {
      if (obj instanceof HostProxy) {
         HostProxy hostObject = (HostProxy)obj;
         return new HostProxy(context, hostObject.proxy);
      } else {
         throw CompilerDirectives.shouldNotReachHere("Parameter must be HostProxy.");
      }
   }

   @Override
   public boolean equals(Object obj) {
      return !(obj instanceof HostProxy) ? false : this.proxy == ((HostProxy)obj).proxy;
   }

   @Override
   public int hashCode() {
      return System.identityHashCode(this.proxy);
   }

   @ExportMessage
   boolean isInstantiable() {
      return this.proxy instanceof ProxyInstantiable;
   }

   @ExportMessage
   @CompilerDirectives.TruffleBoundary
   Object instantiate(
      Object[] arguments,
      @CachedLibrary("this") InteropLibrary library,
      @Cached.Shared("cache") @Cached(value = "this.context.getGuestToHostCache()", allowUncached = true) GuestToHostCodeCache cache
   ) throws UnsupportedMessageException {
      if (this.proxy instanceof ProxyInstantiable) {
         Value[] convertedArguments = cache.language.access.toValues(this.context.internalContext, arguments);
         Object result = GuestToHostRootNode.guestToHostCall(library, cache.instantiate, this.context, this.proxy, convertedArguments);
         return this.context.toGuestValue(library, result);
      } else {
         throw UnsupportedMessageException.create();
      }
   }

   @ExportMessage
   boolean isExecutable() {
      return this.proxy instanceof ProxyExecutable;
   }

   @ExportMessage
   @CompilerDirectives.TruffleBoundary
   Object execute(
      Object[] arguments,
      @CachedLibrary("this") InteropLibrary library,
      @Cached.Shared("cache") @Cached(value = "this.context.getGuestToHostCache()", allowUncached = true) GuestToHostCodeCache cache
   ) throws UnsupportedMessageException {
      if (this.proxy instanceof ProxyExecutable) {
         Value[] convertedArguments = this.context.language.access.toValues(this.context.internalContext, arguments);
         Object result = GuestToHostRootNode.guestToHostCall(library, cache.execute, this.context, this.proxy, convertedArguments);
         return this.context.toGuestValue(library, result);
      } else {
         throw UnsupportedMessageException.create();
      }
   }

   @ExportMessage
   boolean isPointer() {
      return this.proxy instanceof ProxyNativeObject;
   }

   @ExportMessage
   @CompilerDirectives.TruffleBoundary
   long asPointer(
      @CachedLibrary("this") InteropLibrary library,
      @Cached.Shared("cache") @Cached(value = "this.context.getGuestToHostCache()", allowUncached = true) GuestToHostCodeCache cache
   ) throws UnsupportedMessageException {
      if (this.proxy instanceof ProxyNativeObject) {
         return (Long)GuestToHostRootNode.guestToHostCall(library, cache.asPointer, this.context, this.proxy);
      } else {
         throw UnsupportedMessageException.create();
      }
   }

   @ExportMessage
   boolean hasArrayElements() {
      return this.proxy instanceof ProxyArray;
   }

   @ExportMessage
   @CompilerDirectives.TruffleBoundary
   Object readArrayElement(
      long index,
      @CachedLibrary("this") InteropLibrary library,
      @Cached.Shared("cache") @Cached(value = "this.context.getGuestToHostCache()", allowUncached = true) GuestToHostCodeCache cache
   ) throws UnsupportedMessageException {
      if (this.proxy instanceof ProxyArray) {
         Object result = GuestToHostRootNode.guestToHostCall(library, cache.arrayGet, this.context, this.proxy, index);
         return this.context.toGuestValue(library, result);
      } else {
         throw UnsupportedMessageException.create();
      }
   }

   @ExportMessage
   @CompilerDirectives.TruffleBoundary
   void writeArrayElement(
      long index,
      Object value,
      @CachedLibrary("this") InteropLibrary library,
      @Cached.Shared("cache") @Cached(value = "this.context.getGuestToHostCache()", allowUncached = true) GuestToHostCodeCache cache
   ) throws UnsupportedMessageException {
      if (this.proxy instanceof ProxyArray) {
         Value castValue = this.context.asValue(library, value);
         GuestToHostRootNode.guestToHostCall(library, cache.arraySet, this.context, this.proxy, index, castValue);
      } else {
         throw UnsupportedMessageException.create();
      }
   }

   @ExportMessage
   @CompilerDirectives.TruffleBoundary
   void removeArrayElement(
      long index,
      @CachedLibrary("this") InteropLibrary library,
      @Cached.Shared("cache") @Cached(value = "this.context.getGuestToHostCache()", allowUncached = true) GuestToHostCodeCache cache
   ) throws UnsupportedMessageException, InvalidArrayIndexException {
      if (this.proxy instanceof ProxyArray) {
         boolean result = (Boolean)GuestToHostRootNode.guestToHostCall(library, cache.arrayRemove, this.context, this.proxy, index);
         if (!result) {
            throw InvalidArrayIndexException.create(index);
         }
      } else {
         throw UnsupportedMessageException.create();
      }
   }

   @ExportMessage
   @CompilerDirectives.TruffleBoundary
   long getArraySize(
      @CachedLibrary("this") InteropLibrary library,
      @Cached.Shared("cache") @Cached(value = "this.context.getGuestToHostCache()", allowUncached = true) GuestToHostCodeCache cache
   ) throws UnsupportedMessageException {
      if (this.proxy instanceof ProxyArray) {
         return (Long)GuestToHostRootNode.guestToHostCall(library, cache.arraySize, this.context, this.proxy);
      } else {
         throw UnsupportedMessageException.create();
      }
   }

   @ExportMessage.Repeat(
      {@ExportMessage(name = "isArrayElementReadable"), @ExportMessage(name = "isArrayElementModifiable"), @ExportMessage(name = "isArrayElementRemovable")}
   )
   @CompilerDirectives.TruffleBoundary
   boolean isArrayElementExisting(
      long index,
      @CachedLibrary("this") InteropLibrary library,
      @Cached.Shared("cache") @Cached(value = "this.context.getGuestToHostCache()", allowUncached = true) GuestToHostCodeCache cache
   ) {
      if (!(this.proxy instanceof ProxyArray)) {
         return false;
      } else {
         long size = (Long)GuestToHostRootNode.guestToHostCall(library, cache.arraySize, this.context, this.proxy);
         return index >= 0L && index < size;
      }
   }

   @ExportMessage
   @CompilerDirectives.TruffleBoundary
   boolean isArrayElementInsertable(
      long index,
      @CachedLibrary("this") InteropLibrary library,
      @Cached.Shared("cache") @Cached(value = "this.context.getGuestToHostCache()", allowUncached = true) GuestToHostCodeCache cache
   ) {
      if (!(this.proxy instanceof ProxyArray)) {
         return false;
      } else {
         long size = (Long)GuestToHostRootNode.guestToHostCall(library, cache.arraySize, this.context, this.proxy);
         return index < 0L || index >= size;
      }
   }

   @ExportMessage
   boolean hasMembers() {
      return this.proxy instanceof ProxyObject;
   }

   @ExportMessage
   @CompilerDirectives.TruffleBoundary
   Object getMembers(
      boolean includeInternal,
      @CachedLibrary("this") InteropLibrary library,
      @Cached.Shared("cache") @Cached(value = "this.context.getGuestToHostCache()", allowUncached = true) GuestToHostCodeCache cache
   ) throws UnsupportedMessageException {
      if (this.proxy instanceof ProxyObject) {
         Object result = GuestToHostRootNode.guestToHostCall(library, cache.memberKeys, this.context, this.proxy);
         if (result == null) {
            result = EMPTY;
         }

         Object guestValue = this.context.toGuestValue(library, result);
         InteropLibrary interop = InteropLibrary.getFactory().getUncached();
         if (!interop.hasArrayElements(guestValue)) {
            if (guestValue instanceof HostObject) {
               HostObject hostObject = (HostObject)guestValue;
               if (hostObject.obj.getClass().isArray() && !hostObject.getHostClassCache().isArrayAccess()) {
                  throw illegalProxy(
                     this.context,
                     "getMemberKeys() returned a Java array %s, but allowArrayAccess in HostAccess is false.",
                     this.context.asValue(library, guestValue).toString()
                  );
               }

               if (hostObject.obj instanceof List && !hostObject.getHostClassCache().isListAccess()) {
                  throw illegalProxy(
                     this.context,
                     "getMemberKeys() returned a Java List %s, but allowListAccess in HostAccess is false.",
                     this.context.asValue(library, guestValue).toString()
                  );
               }
            }

            throw illegalProxy(
               this.context,
               "getMemberKeys() returned invalid value %s but must return an array of member key Strings.",
               this.context.asValue(library, guestValue).toString()
            );
         } else {
            for (int i = 0; i < interop.getArraySize(guestValue); i++) {
               try {
                  Object element = interop.readArrayElement(guestValue, i);
                  if (!interop.isString(element)) {
                     throw illegalProxy(
                        this.context,
                        "getMemberKeys() returned invalid value %s but must return an array of member key Strings.",
                        this.context.asValue(library, guestValue).toString()
                     );
                  }
               } catch (UnsupportedOperationException var9) {
                  CompilerDirectives.shouldNotReachHere(var9);
               } catch (InvalidArrayIndexException var10) {
               }
            }

            return guestValue;
         }
      } else {
         throw UnsupportedMessageException.create();
      }
   }

   @CompilerDirectives.TruffleBoundary
   static RuntimeException illegalProxy(HostContext context, String message, Object... parameters) {
      throw context.hostToGuestException(new IllegalStateException(String.format(message, parameters)));
   }

   @ExportMessage
   @CompilerDirectives.TruffleBoundary
   Object readMember(
      String member,
      @CachedLibrary("this") InteropLibrary library,
      @Cached.Shared("cache") @Cached(value = "this.context.getGuestToHostCache()", allowUncached = true) GuestToHostCodeCache cache
   ) throws UnsupportedMessageException, UnknownIdentifierException {
      if (this.proxy instanceof ProxyObject) {
         if (!this.isMemberExisting(member, library, cache)) {
            throw UnknownIdentifierException.create(member);
         } else {
            Object result = GuestToHostRootNode.guestToHostCall(library, cache.getMember, this.context, this.proxy, member);
            return this.context.toGuestValue(library, result);
         }
      } else {
         throw UnsupportedMessageException.create();
      }
   }

   @ExportMessage
   @CompilerDirectives.TruffleBoundary
   void writeMember(
      String member,
      Object value,
      @CachedLibrary("this") InteropLibrary library,
      @Cached.Shared("cache") @Cached(value = "this.context.getGuestToHostCache()", allowUncached = true) GuestToHostCodeCache cache
   ) throws UnsupportedMessageException {
      if (this.proxy instanceof ProxyObject) {
         Value castValue = this.context.asValue(library, value);
         GuestToHostRootNode.guestToHostCall(library, cache.putMember, this.context, this.proxy, member, castValue);
      } else {
         throw UnsupportedMessageException.create();
      }
   }

   @ExportMessage
   @CompilerDirectives.TruffleBoundary
   Object invokeMember(
      String member,
      Object[] arguments,
      @CachedLibrary("this") InteropLibrary library,
      @Cached.Shared("executables") @CachedLibrary(limit = "LIMIT") InteropLibrary executables,
      @Cached.Shared("cache") @Cached(value = "this.context.getGuestToHostCache()", allowUncached = true) GuestToHostCodeCache cache
   ) throws UnsupportedMessageException, UnsupportedTypeException, ArityException, UnknownIdentifierException {
      if (this.proxy instanceof ProxyObject) {
         if (!this.isMemberExisting(member, library, cache)) {
            throw UnknownIdentifierException.create(member);
         } else {
            Object memberObject;
            try {
               memberObject = this.readMember(member, library, cache);
            } catch (UnsupportedOperationException var8) {
               throw UnsupportedMessageException.create();
            }

            memberObject = this.context.toGuestValue(library, memberObject);
            if (executables.isExecutable(memberObject)) {
               return executables.execute(memberObject, arguments);
            } else {
               throw UnsupportedMessageException.create();
            }
         }
      } else {
         throw UnsupportedMessageException.create();
      }
   }

   @ExportMessage
   @CompilerDirectives.TruffleBoundary
   boolean isMemberInvocable(
      String member,
      @CachedLibrary("this") InteropLibrary library,
      @Cached.Shared("executables") @CachedLibrary(limit = "LIMIT") InteropLibrary executables,
      @Cached.Shared("cache") @Cached(value = "this.context.getGuestToHostCache()", allowUncached = true) GuestToHostCodeCache cache
   ) {
      if (this.proxy instanceof ProxyObject && this.isMemberExisting(member, library, cache)) {
         try {
            return executables.isExecutable(this.readMember(member, library, cache));
         } catch (UnknownIdentifierException | UnsupportedMessageException var6) {
            return false;
         }
      } else {
         return false;
      }
   }

   @ExportMessage
   @CompilerDirectives.TruffleBoundary
   void removeMember(
      String member,
      @CachedLibrary("this") InteropLibrary library,
      @Cached.Shared("cache") @Cached(value = "this.context.getGuestToHostCache()", allowUncached = true) GuestToHostCodeCache cache
   ) throws UnsupportedMessageException, UnknownIdentifierException {
      if (this.proxy instanceof ProxyObject) {
         if (!this.isMemberExisting(member, library, cache)) {
            throw UnknownIdentifierException.create(member);
         } else {
            boolean result = (Boolean)GuestToHostRootNode.guestToHostCall(library, cache.removeMember, this.context, this.proxy, member);
            if (!result) {
               throw UnknownIdentifierException.create(member);
            }
         }
      } else {
         throw UnsupportedMessageException.create();
      }
   }

   @ExportMessage.Repeat({@ExportMessage(name = "isMemberReadable"), @ExportMessage(name = "isMemberModifiable"), @ExportMessage(name = "isMemberRemovable")})
   @CompilerDirectives.TruffleBoundary
   boolean isMemberExisting(
      String member,
      @CachedLibrary("this") InteropLibrary library,
      @Cached.Shared("cache") @Cached(value = "this.context.getGuestToHostCache()", allowUncached = true) GuestToHostCodeCache cache
   ) {
      return this.proxy instanceof ProxyObject
         ? (Boolean)GuestToHostRootNode.guestToHostCall(library, cache.hasMember, this.context, this.proxy, member)
         : false;
   }

   @ExportMessage
   @CompilerDirectives.TruffleBoundary
   boolean isMemberInsertable(
      String member,
      @CachedLibrary("this") InteropLibrary library,
      @Cached.Shared("cache") @Cached(value = "this.context.getGuestToHostCache()", allowUncached = true) GuestToHostCodeCache cache
   ) {
      return this.proxy instanceof ProxyObject ? !this.isMemberExisting(member, library, cache) : false;
   }

   @CompilerDirectives.TruffleBoundary
   @ExportMessage
   boolean isDate() {
      return this.proxy instanceof ProxyDate;
   }

   @CompilerDirectives.TruffleBoundary
   @ExportMessage
   boolean isTime() {
      return this.proxy instanceof ProxyTime;
   }

   @CompilerDirectives.TruffleBoundary
   @ExportMessage
   boolean isTimeZone() {
      return this.proxy instanceof ProxyTimeZone;
   }

   @ExportMessage
   @CompilerDirectives.TruffleBoundary
   ZoneId asTimeZone(
      @CachedLibrary("this") InteropLibrary library,
      @Cached.Shared("cache") @Cached(value = "this.context.getGuestToHostCache()", allowUncached = true) GuestToHostCodeCache cache
   ) throws UnsupportedMessageException {
      if (this.proxy instanceof ProxyTimeZone) {
         return (ZoneId)GuestToHostRootNode.guestToHostCall(library, cache.asTimezone, this.context, this.proxy);
      } else {
         throw UnsupportedMessageException.create();
      }
   }

   @ExportMessage
   @CompilerDirectives.TruffleBoundary
   LocalDate asDate(
      @CachedLibrary("this") InteropLibrary library,
      @Cached.Shared("cache") @Cached(value = "this.context.getGuestToHostCache()", allowUncached = true) GuestToHostCodeCache cache
   ) throws UnsupportedMessageException {
      if (this.proxy instanceof ProxyDate) {
         return (LocalDate)GuestToHostRootNode.guestToHostCall(library, cache.asDate, this.context, this.proxy);
      } else {
         throw UnsupportedMessageException.create();
      }
   }

   @ExportMessage
   @CompilerDirectives.TruffleBoundary
   LocalTime asTime(
      @CachedLibrary("this") InteropLibrary library,
      @Cached.Shared("cache") @Cached(value = "this.context.getGuestToHostCache()", allowUncached = true) GuestToHostCodeCache cache
   ) throws UnsupportedMessageException {
      if (this.proxy instanceof ProxyTime) {
         return (LocalTime)GuestToHostRootNode.guestToHostCall(library, cache.asTime, this.context, this.proxy);
      } else {
         throw UnsupportedMessageException.create();
      }
   }

   @CompilerDirectives.TruffleBoundary
   @ExportMessage
   Instant asInstant(
      @CachedLibrary("this") InteropLibrary library,
      @Cached.Shared("cache") @Cached(value = "this.context.getGuestToHostCache()", allowUncached = true) GuestToHostCodeCache cache
   ) throws UnsupportedMessageException {
      if (this.proxy instanceof ProxyInstant) {
         return (Instant)GuestToHostRootNode.guestToHostCall(library, cache.asInstant, this.context, this.proxy);
      } else if (this.isDate() && this.isTime() && this.isTimeZone()) {
         return ZonedDateTime.of(this.asDate(library, cache), this.asTime(library, cache), this.asTimeZone(library, cache)).toInstant();
      } else {
         throw UnsupportedMessageException.create();
      }
   }

   @CompilerDirectives.TruffleBoundary
   @ExportMessage
   boolean isDuration() {
      return this.proxy instanceof ProxyDuration;
   }

   @ExportMessage
   @CompilerDirectives.TruffleBoundary
   Duration asDuration(
      @CachedLibrary("this") InteropLibrary library,
      @Cached.Shared("cache") @Cached(value = "this.context.getGuestToHostCache()", allowUncached = true) GuestToHostCodeCache cache
   ) throws UnsupportedMessageException {
      if (this.proxy instanceof ProxyDuration) {
         return (Duration)GuestToHostRootNode.guestToHostCall(library, cache.asDuration, this.context, this.proxy);
      } else {
         throw UnsupportedMessageException.create();
      }
   }

   @ExportMessage
   boolean hasLanguage() {
      return true;
   }

   @ExportMessage
   Class<? extends TruffleLanguage<?>> getLanguage() {
      return HostLanguage.class;
   }

   @ExportMessage
   @CompilerDirectives.TruffleBoundary
   Object toDisplayString(boolean config) {
      try {
         return this.proxy.toString();
      } catch (Throwable var3) {
         throw this.context.hostToGuestException(var3);
      }
   }

   @ExportMessage
   boolean hasMetaObject() {
      return true;
   }

   @ExportMessage
   Object getMetaObject() {
      Class<?> javaObject = this.proxy.getClass();
      return HostObject.forClass(javaObject, this.context);
   }

   @ExportMessage
   boolean hasIterator() {
      return this.proxy instanceof ProxyIterable;
   }

   @ExportMessage
   @CompilerDirectives.TruffleBoundary
   Object getIterator(
      @CachedLibrary("this") InteropLibrary library,
      @Cached.Shared("cache") @Cached(value = "this.context.getGuestToHostCache()", allowUncached = true) GuestToHostCodeCache cache
   ) throws UnsupportedMessageException {
      if (this.proxy instanceof ProxyIterable) {
         Object result = GuestToHostRootNode.guestToHostCall(library, cache.getIterator, this.context, this.proxy);
         Object guestValue = this.context.toGuestValue(library, result);
         InteropLibrary interop = InteropLibrary.getFactory().getUncached();
         if (!interop.isIterator(guestValue)) {
            throw illegalProxy(
               this.context, "getIterator() returned an invalid value %s but must return an iterator.", this.context.asValue(library, guestValue).toString()
            );
         } else {
            return guestValue;
         }
      } else {
         throw UnsupportedMessageException.create();
      }
   }

   @ExportMessage
   boolean isIterator() {
      return this.proxy instanceof ProxyIterator;
   }

   @ExportMessage
   @CompilerDirectives.TruffleBoundary
   boolean hasIteratorNextElement(
      @CachedLibrary("this") InteropLibrary library,
      @Cached.Shared("cache") @Cached(value = "this.context.getGuestToHostCache()", allowUncached = true) GuestToHostCodeCache cache
   ) throws UnsupportedMessageException {
      if (this.proxy instanceof ProxyIterator) {
         return (Boolean)GuestToHostRootNode.guestToHostCall(library, cache.hasIteratorNextElement, this.context, this.proxy);
      } else {
         throw UnsupportedMessageException.create();
      }
   }

   @ExportMessage
   @CompilerDirectives.TruffleBoundary
   Object getIteratorNextElement(
      @CachedLibrary("this") InteropLibrary library,
      @Cached.Shared("cache") @Cached(value = "this.context.getGuestToHostCache()", allowUncached = true) GuestToHostCodeCache cache
   ) throws UnsupportedMessageException {
      if (this.proxy instanceof ProxyIterator) {
         Object result = GuestToHostRootNode.guestToHostCall(library, cache.getIteratorNextElement, this.context, this.proxy);
         return this.context.toGuestValue(library, result);
      } else {
         throw UnsupportedMessageException.create();
      }
   }

   @ExportMessage
   @CompilerDirectives.TruffleBoundary
   boolean hasHashEntries() {
      return this.proxy instanceof ProxyHashMap;
   }

   @ExportMessage
   @CompilerDirectives.TruffleBoundary
   long getHashSize(
      @CachedLibrary("this") InteropLibrary library,
      @Cached.Shared("cache") @Cached(value = "this.context.getGuestToHostCache()", allowUncached = true) GuestToHostCodeCache cache
   ) throws UnsupportedMessageException {
      if (this.proxy instanceof ProxyHashMap) {
         return (Long)GuestToHostRootNode.guestToHostCall(library, cache.getHashSize, this.context, this.proxy);
      } else {
         throw UnsupportedMessageException.create();
      }
   }

   @ExportMessage.Repeat(
      {@ExportMessage(name = "isHashEntryReadable"), @ExportMessage(name = "isHashEntryModifiable"), @ExportMessage(name = "isHashEntryRemovable")}
   )
   @CompilerDirectives.TruffleBoundary
   boolean isHashValueExisting(
      Object key,
      @CachedLibrary("this") InteropLibrary library,
      @Cached.Shared("cache") @Cached(value = "this.context.getGuestToHostCache()", allowUncached = true) GuestToHostCodeCache cache
   ) {
      if (this.proxy instanceof ProxyHashMap) {
         Value keyValue = this.context.asValue(library, key);
         return (Boolean)GuestToHostRootNode.guestToHostCall(library, cache.hasHashEntry, this.context, this.proxy, keyValue);
      } else {
         return false;
      }
   }

   @ExportMessage
   @CompilerDirectives.TruffleBoundary
   Object readHashValue(
      Object key,
      @CachedLibrary("this") InteropLibrary library,
      @Cached.Shared("cache") @Cached(value = "this.context.getGuestToHostCache()", allowUncached = true) GuestToHostCodeCache cache
   ) throws UnsupportedMessageException, UnknownKeyException {
      if (this.proxy instanceof ProxyHashMap) {
         if (!this.isHashValueExisting(key, library, cache)) {
            throw UnknownKeyException.create(key);
         } else {
            Value keyValue = this.context.asValue(library, key);
            Object result = GuestToHostRootNode.guestToHostCall(library, cache.getHashValue, this.context, this.proxy, keyValue);
            return this.context.toGuestValue(library, result);
         }
      } else {
         throw UnsupportedMessageException.create();
      }
   }

   @ExportMessage
   @CompilerDirectives.TruffleBoundary
   boolean isHashEntryInsertable(
      Object key,
      @CachedLibrary("this") InteropLibrary library,
      @Cached.Shared("cache") @Cached(value = "this.context.getGuestToHostCache()", allowUncached = true) GuestToHostCodeCache cache
   ) {
      return this.proxy instanceof ProxyHashMap ? !this.isHashValueExisting(key, library, cache) : false;
   }

   @ExportMessage
   @CompilerDirectives.TruffleBoundary
   void writeHashEntry(
      Object key,
      Object value,
      @CachedLibrary("this") InteropLibrary library,
      @Cached.Shared("cache") @Cached(value = "this.context.getGuestToHostCache()", allowUncached = true) GuestToHostCodeCache cache
   ) throws UnsupportedMessageException {
      if (this.proxy instanceof ProxyHashMap) {
         Value keyValue = this.context.asValue(library, key);
         Value valueValue = this.context.asValue(library, value);
         GuestToHostRootNode.guestToHostCall(library, cache.putHashEntry, this.context, this.proxy, keyValue, valueValue);
      } else {
         throw UnsupportedMessageException.create();
      }
   }

   @ExportMessage
   @CompilerDirectives.TruffleBoundary
   void removeHashEntry(
      Object key,
      @CachedLibrary("this") InteropLibrary library,
      @Cached.Shared("cache") @Cached(value = "this.context.getGuestToHostCache()", allowUncached = true) GuestToHostCodeCache cache
   ) throws UnsupportedMessageException, UnknownKeyException {
      if (this.proxy instanceof ProxyHashMap) {
         if (!this.isHashValueExisting(key, library, cache)) {
            throw UnknownKeyException.create(key);
         } else {
            Value keyValue = this.context.asValue(library, key);
            GuestToHostRootNode.guestToHostCall(library, cache.removeHashEntry, this.context, this.proxy, keyValue);
         }
      } else {
         throw UnsupportedMessageException.create();
      }
   }

   @ExportMessage
   @CompilerDirectives.TruffleBoundary
   Object getHashEntriesIterator(
      @CachedLibrary("this") InteropLibrary library,
      @Cached.Shared("cache") @Cached(value = "this.context.getGuestToHostCache()", allowUncached = true) GuestToHostCodeCache cache
   ) throws UnsupportedMessageException {
      if (this.proxy instanceof ProxyHashMap) {
         Object result = GuestToHostRootNode.guestToHostCall(library, cache.getHashEntriesIterator, this.context, this.proxy);
         Object guestValue = this.context.toGuestValue(library, result);
         InteropLibrary interop = InteropLibrary.getFactory().getUncached();
         if (!interop.isIterator(guestValue)) {
            throw illegalProxy(
               this.context,
               "getHashEntriesIterator() returned an invalid value %s but must return an iterator.",
               this.context.asValue(library, guestValue).toString()
            );
         } else {
            return guestValue;
         }
      } else {
         throw UnsupportedMessageException.create();
      }
   }

   @ExportMessage
   @CompilerDirectives.TruffleBoundary
   static int identityHashCode(HostProxy receiver) {
      return System.identityHashCode(receiver.proxy);
   }

   public static boolean isProxyGuestObject(HostLanguage language, TruffleObject value) {
      return isProxyGuestObject(language, (Object)value);
   }

   public static boolean isProxyGuestObject(HostLanguage language, Object value) {
      Object unwrapped = HostLanguage.unwrapIfScoped(language, value);
      return unwrapped instanceof HostProxy;
   }

   public static Proxy toProxyHostObject(HostLanguage language, Object value) {
      Object v = HostLanguage.unwrapIfScoped(language, value);
      return ((HostProxy)v).proxy;
   }

   public static TruffleObject toProxyGuestObject(HostContext context, Proxy receiver) {
      return new HostProxy(context, receiver);
   }

   @ExportMessage
   static final class IsIdenticalOrUndefined {
      @Specialization
      static TriState doHostObject(HostProxy receiver, HostProxy other) {
         return receiver.proxy == other.proxy ? TriState.TRUE : TriState.FALSE;
      }

      @Fallback
      static TriState doOther(HostProxy receiver, Object other) {
         return TriState.UNDEFINED;
      }
   }
}
