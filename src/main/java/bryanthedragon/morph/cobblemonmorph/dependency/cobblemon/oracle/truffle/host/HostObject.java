package com.oracle.truffle.host;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.TruffleStackTrace;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Fallback;
import com.oracle.truffle.api.dsl.GenerateUncached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.exception.AbstractTruffleException;
import com.oracle.truffle.api.interop.ArityException;
import com.oracle.truffle.api.interop.ExceptionType;
import com.oracle.truffle.api.interop.InteropException;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.InvalidArrayIndexException;
import com.oracle.truffle.api.interop.InvalidBufferOffsetException;
import com.oracle.truffle.api.interop.StopIterationException;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.interop.UnknownIdentifierException;
import com.oracle.truffle.api.interop.UnknownKeyException;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.interop.UnsupportedTypeException;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.library.ExportMessage;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.profiles.ValueProfile;
import com.oracle.truffle.api.utilities.TriState;
import java.lang.reflect.Array;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ReadOnlyBufferException;
import java.sql.Date;
import java.sql.Time;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Map.Entry;
import org.graalvm.polyglot.impl.AbstractPolyglotImpl;
import org.graalvm.polyglot.proxy.Proxy;

@ExportLibrary(InteropLibrary.class)
final class HostObject implements TruffleObject {
   static final int LIMIT = 5;
   private static final Class<? extends ByteBuffer> HEAP_BYTE_BUFFER_CLASS = (Class<? extends ByteBuffer>)ByteBuffer.allocate(0).getClass();
   private static final Class<? extends ByteBuffer> HEAP_BYTE_BUFFER_R_CLASS = (Class<? extends ByteBuffer>)ByteBuffer.allocate(0)
      .asReadOnlyBuffer()
      .getClass();
   private static final Class<? extends ByteBuffer> DIRECT_BYTE_BUFFER_CLASS = (Class<? extends ByteBuffer>)ByteBuffer.allocateDirect(0).getClass();
   private static final Class<? extends ByteBuffer> DIRECT_BYTE_BUFFER_R_CLASS = (Class<? extends ByteBuffer>)ByteBuffer.allocateDirect(0)
      .asReadOnlyBuffer()
      .getClass();
   private static final ZoneId UTC = ZoneId.of("UTC");
   static final HostObject NULL = new HostObject(null, null, null);
   final Object obj;
   final HostContext context;
   private final Object extraInfo;

   private HostObject(Object obj, HostContext context, Object extraInfo) {
      this.obj = obj;
      this.context = context;
      this.extraInfo = extraInfo;
   }

   static HostObject forClass(Class<?> clazz, HostContext context) {
      assert clazz != null;

      return new HostObject(clazz, context, null);
   }

   static HostObject forStaticClass(Class<?> clazz, HostContext context) {
      assert clazz != null;

      return new HostObject(clazz, context, clazz);
   }

   static HostObject forObject(Object object, HostContext context) {
      assert object != null && !(object instanceof Class);

      return new HostObject(object, context, null);
   }

   static HostObject forException(Throwable object, HostContext context, HostException hostException) {
      Objects.requireNonNull(object);
      return new HostObject(object, context, hostException);
   }

   static boolean isInstance(HostLanguage language, Object v) {
      Object obj = HostLanguage.unwrapIfScoped(language, v);
      return obj instanceof HostObject || obj instanceof HostException;
   }

   static Object withContext(HostLanguage language, Object originalValue, HostContext context) {
      assert context != null;

      Object obj = HostLanguage.unwrapIfScoped(language, originalValue);
      if (obj instanceof HostObject) {
         HostObject hostObject = (HostObject)obj;
         return new HostObject(hostObject.obj, context, hostObject.extraInfo);
      } else if (obj instanceof HostException) {
         return new HostException(((HostException)obj).getOriginal(), context);
      } else {
         throw CompilerDirectives.shouldNotReachHere("Parameter must be HostObject or HostException.");
      }
   }

   static boolean isJavaInstance(HostLanguage language, Class<?> targetType, Object javaObject) {
      Object unboxed = unboxHostObject(language, javaObject);
      return unboxed != null ? targetType.isInstance(unboxed) : false;
   }

   static Object unboxHostObject(HostLanguage language, Object value) {
      Object v = HostLanguage.unwrapIfScoped(language, value);
      if (v instanceof HostObject) {
         return ((HostObject)v).obj;
      } else {
         return v instanceof HostException ? ((HostException)v).delegate.obj : null;
      }
   }

   static Object valueOf(HostLanguage language, Object value) {
      Object v = HostLanguage.unwrapIfScoped(language, value);
      if (v instanceof HostObject) {
         return ((HostObject)v).obj;
      } else {
         return v instanceof HostException ? ((HostException)v).delegate.obj : v;
      }
   }

   @Override
   public int hashCode() {
      return System.identityHashCode(this.obj);
   }

   boolean isClass() {
      return this.obj instanceof Class;
   }

   boolean isArrayClass() {
      return this.isClass() && this.asClass().isArray();
   }

   boolean isDefaultClass() {
      return this.isClass() && !this.asClass().isArray();
   }

   private static RuntimeException unboxEngineException(HostObject receiver, RuntimeException e) {
      AbstractPolyglotImpl.AbstractHostAccess access = receiver.context.language.access;
      return access.isEngineException(e) ? access.unboxEngineException(e) : null;
   }

   @ExportMessage
   boolean hasMembers() {
      return !this.isNull();
   }

   @ExportMessage
   Object getMembers(boolean includeInternal) throws UnsupportedMessageException {
      if (this.isNull()) {
         throw UnsupportedMessageException.create();
      } else {
         String[] fields = HostInteropReflect.findUniquePublicMemberNames(
            this.context, this.getLookupClass(), this.isStaticClass(), this.isClass(), includeInternal
         );
         return new HostObject.KeysArray(fields);
      }
   }

   @ExportMessage
   Object readMember(
      String name,
      @Cached.Shared("lookupField") @Cached HostObject.LookupFieldNode lookupField,
      @Cached.Shared("readField") @Cached HostObject.ReadFieldNode readField,
      @Cached.Shared("lookupMethod") @Cached HostObject.LookupMethodNode lookupMethod,
      @Cached HostObject.LookupInnerClassNode lookupInnerClass,
      @Cached.Shared("error") @Cached BranchProfile error
   ) throws UnsupportedMessageException, UnknownIdentifierException {
      if (this.isNull()) {
         error.enter();
         throw UnsupportedMessageException.create();
      } else {
         boolean isStatic = this.isStaticClass();
         Class<?> lookupClass = this.getLookupClass();
         HostFieldDesc foundField = lookupField.execute(this, lookupClass, name, isStatic);
         if (foundField != null) {
            return readField.execute(foundField, this);
         } else {
            HostMethodDesc foundMethod = lookupMethod.execute(this, lookupClass, name, isStatic);
            if (foundMethod != null) {
               return new HostFunction(foundMethod, this.obj, this.context);
            } else {
               if (isStatic) {
                  if ("class".equals(name)) {
                     return forClass(lookupClass, this.context);
                  }

                  Class<?> innerclass = lookupInnerClass.execute(lookupClass, name);
                  if (innerclass != null) {
                     return forStaticClass(innerclass, this.context);
                  }
               } else {
                  if (this.isClass() && "static".equals(name)) {
                     return forStaticClass(this.asClass(), this.context);
                  }

                  if ("super".equals(name) && HostAdapterFactory.isAdapterInstance(this.obj)) {
                     return HostAdapterFactory.getSuperAdapter(this);
                  }
               }

               error.enter();
               throw UnknownIdentifierException.create(name);
            }
         }
      }
   }

   @ExportMessage
   boolean isMemberInsertable(String member) {
      return false;
   }

   @ExportMessage
   void writeMember(
      String member,
      Object value,
      @Cached.Shared("lookupField") @Cached HostObject.LookupFieldNode lookupField,
      @Cached HostObject.WriteFieldNode writeField,
      @Cached.Shared("error") @Cached BranchProfile error
   ) throws UnsupportedMessageException, UnknownIdentifierException, UnsupportedTypeException {
      if (this.isNull()) {
         error.enter();
         throw UnsupportedMessageException.create();
      } else {
         HostFieldDesc f = lookupField.execute(this, this.getLookupClass(), member, this.isStaticClass());
         if (f == null) {
            error.enter();
            throw UnknownIdentifierException.create(member);
         } else {
            try {
               writeField.execute(f, this, value);
            } catch (NullPointerException | ClassCastException var8) {
               error.enter();
               throw UnsupportedTypeException.create(new Object[]{value}, getMessage(var8));
            }
         }
      }
   }

   @CompilerDirectives.TruffleBoundary
   private static String getMessage(RuntimeException e) {
      return e.getMessage();
   }

   @ExportMessage
   Object invokeMember(
      String name,
      Object[] args,
      @Cached.Shared("lookupMethod") @Cached HostObject.LookupMethodNode lookupMethod,
      @Cached.Shared("hostExecute") @Cached HostExecuteNode executeMethod,
      @Cached.Shared("lookupField") @Cached HostObject.LookupFieldNode lookupField,
      @Cached.Shared("readField") @Cached HostObject.ReadFieldNode readField,
      @CachedLibrary(limit = "5") InteropLibrary fieldValues,
      @Cached.Shared("error") @Cached BranchProfile error
   ) throws UnsupportedTypeException, ArityException, UnsupportedMessageException, UnknownIdentifierException {
      if (this.isNull()) {
         error.enter();
         throw UnsupportedMessageException.create();
      } else {
         boolean isStatic = this.isStaticClass();
         Class<?> lookupClass = this.getLookupClass();
         HostMethodDesc foundMethod = lookupMethod.execute(this, lookupClass, name, isStatic);
         if (foundMethod != null) {
            return executeMethod.execute(foundMethod, this.obj, args, this.context);
         } else {
            HostFieldDesc foundField = lookupField.execute(this, lookupClass, name, isStatic);
            if (foundField != null) {
               Object fieldValue = readField.execute(foundField, this);
               if (fieldValues.isExecutable(fieldValue)) {
                  return fieldValues.execute(fieldValue, args);
               }
            }

            error.enter();
            throw UnknownIdentifierException.create(name);
         }
      }
   }

   @ExportMessage
   boolean isArrayElementInsertable(
      long index, @Cached.Shared("isList") @Cached HostObject.IsListNode isList, @Cached.Shared("error") @Cached BranchProfile error
   ) {
      try {
         return isList.execute(this) && HostObject.GuestToHostCalls.getListSize(this) == index;
      } catch (Throwable var6) {
         error.enter();
         throw this.context.hostToGuestException(var6);
      }
   }

   @ExportMessage
   boolean hasArrayElements(
      @Cached.Shared("isList") @Cached HostObject.IsListNode isList,
      @Cached.Shared("isArray") @Cached HostObject.IsArrayNode isArray,
      @Cached.Shared("isMapEntry") @Cached HostObject.IsMapEntryNode isMapEntry
   ) {
      return isList.execute(this) || isArray.execute(this) || isMapEntry.execute(this);
   }

   @ExportMessage
   boolean hasBufferElements(@Cached.Shared("isBuffer") @Cached HostObject.IsBufferNode isBuffer) {
      return isBuffer.execute(this);
   }

   @ExportMessage
   boolean isBufferWritable(@Cached.Shared("isBuffer") @Cached HostObject.IsBufferNode isBuffer, @Cached.Shared("error") @Cached BranchProfile error) throws UnsupportedMessageException {
      if (isBuffer.execute(this)) {
         ByteBuffer buffer = (ByteBuffer)this.obj;
         return isPEFriendlyBuffer(buffer) ? !buffer.isReadOnly() : isBufferWritableBoundary(buffer);
      } else {
         error.enter();
         throw UnsupportedMessageException.create();
      }
   }

   @CompilerDirectives.TruffleBoundary
   private static boolean isBufferWritableBoundary(ByteBuffer buffer) {
      return !buffer.isReadOnly();
   }

   @ExportMessage
   long getBufferSize(@Cached.Shared("isBuffer") @Cached HostObject.IsBufferNode isBuffer, @Cached.Shared("error") @Cached BranchProfile error) throws UnsupportedMessageException {
      if (isBuffer.execute(this)) {
         ByteBuffer buffer = (ByteBuffer)this.obj;
         return isPEFriendlyBuffer(buffer) ? buffer.limit() : getBufferSizeBoundary(buffer);
      } else {
         error.enter();
         throw UnsupportedMessageException.create();
      }
   }

   @CompilerDirectives.TruffleBoundary
   private static long getBufferSizeBoundary(ByteBuffer buffer) {
      return buffer.limit();
   }

   private static boolean isPEFriendlyBuffer(ByteBuffer buffer) {
      Class<? extends ByteBuffer> clazz = (Class<? extends ByteBuffer>)buffer.getClass();
      boolean result = CompilerDirectives.isPartialEvaluationConstant(clazz)
         && (clazz == HEAP_BYTE_BUFFER_CLASS || clazz == HEAP_BYTE_BUFFER_R_CLASS || clazz == DIRECT_BYTE_BUFFER_CLASS || clazz == DIRECT_BYTE_BUFFER_R_CLASS);

      assert result : "Unexpected Buffer subclass";

      return result;
   }

   @ExportMessage
   public byte readBufferByte(
      long index,
      @Cached.Shared("isBuffer") @Cached HostObject.IsBufferNode isBuffer,
      @Cached.Shared("error") @Cached BranchProfile error,
      @Cached.Shared("classProfile") @Cached("createClassProfile()") ValueProfile classProfile
   ) throws UnsupportedMessageException, InvalidBufferOffsetException {
      if (!isBuffer.execute(this)) {
         error.enter();
         throw UnsupportedMessageException.create();
      } else if (index >= 0L && 2147483647L >= index) {
         try {
            ByteBuffer buffer = classProfile.profile((ByteBuffer)this.obj);
            return isPEFriendlyBuffer(buffer) ? buffer.get((int)index) : getBufferByteBoundary(buffer, (int)index);
         } catch (IndexOutOfBoundsException var7) {
            error.enter();
            throw InvalidBufferOffsetException.create(index, 1L);
         }
      } else {
         error.enter();
         throw InvalidBufferOffsetException.create(index, 1L);
      }
   }

   @CompilerDirectives.TruffleBoundary
   private static byte getBufferByteBoundary(ByteBuffer buffer, int index) {
      return buffer.get(index);
   }

   @ExportMessage
   public void writeBufferByte(
      long index,
      byte value,
      @Cached.Shared("isBuffer") @Cached HostObject.IsBufferNode isBuffer,
      @Cached.Shared("error") @Cached BranchProfile error,
      @Cached.Shared("classProfile") @Cached("createClassProfile()") ValueProfile classProfile
   ) throws InvalidBufferOffsetException, UnsupportedMessageException {
      if (!isBuffer.execute(this)) {
         error.enter();
         throw UnsupportedMessageException.create();
      } else if (index >= 0L && 2147483647L >= index) {
         try {
            ByteBuffer buffer = classProfile.profile((ByteBuffer)this.obj);
            if (isPEFriendlyBuffer(buffer)) {
               buffer.put((int)index, value);
            } else {
               putBufferByteBoundary(buffer, (int)index, value);
            }
         } catch (IndexOutOfBoundsException var8) {
            error.enter();
            throw InvalidBufferOffsetException.create(index, 1L);
         } catch (ReadOnlyBufferException var9) {
            error.enter();
            throw UnsupportedMessageException.create();
         }
      } else {
         error.enter();
         throw InvalidBufferOffsetException.create(index, 1L);
      }
   }

   @CompilerDirectives.TruffleBoundary
   private static void putBufferByteBoundary(ByteBuffer buffer, int index, byte value) {
      buffer.put(index, value);
   }

   @ExportMessage
   public short readBufferShort(
      ByteOrder order,
      long index,
      @Cached.Shared("isBuffer") @Cached HostObject.IsBufferNode isBuffer,
      @Cached.Shared("error") @Cached BranchProfile error,
      @Cached.Shared("classProfile") @Cached("createClassProfile()") ValueProfile classProfile
   ) throws UnsupportedMessageException, InvalidBufferOffsetException {
      if (!isBuffer.execute(this)) {
         error.enter();
         throw UnsupportedMessageException.create();
      } else if (index >= 0L && 2147483647L >= index) {
         try {
            ByteBuffer buffer = classProfile.profile((ByteBuffer)this.obj);
            ByteOrder originalOrder = buffer.order();
            buffer.order(order);
            short result = isPEFriendlyBuffer(buffer) ? buffer.getShort((int)index) : getBufferShortBoundary(buffer, (int)index);
            buffer.order(originalOrder);
            return result;
         } catch (IndexOutOfBoundsException var10) {
            error.enter();
            throw InvalidBufferOffsetException.create(index, 2L);
         }
      } else {
         error.enter();
         throw InvalidBufferOffsetException.create(index, 2L);
      }
   }

   @CompilerDirectives.TruffleBoundary
   private static short getBufferShortBoundary(ByteBuffer buffer, int index) {
      return buffer.getShort(index);
   }

   @ExportMessage
   public void writeBufferShort(
      ByteOrder order,
      long index,
      short value,
      @Cached.Shared("isBuffer") @Cached HostObject.IsBufferNode isBuffer,
      @Cached.Shared("error") @Cached BranchProfile error,
      @Cached.Shared("classProfile") @Cached("createClassProfile()") ValueProfile classProfile
   ) throws InvalidBufferOffsetException, UnsupportedMessageException {
      if (!isBuffer.execute(this)) {
         error.enter();
         throw UnsupportedMessageException.create();
      } else if (index >= 0L && 2147483647L >= index) {
         try {
            ByteBuffer buffer = classProfile.profile((ByteBuffer)this.obj);
            ByteOrder originalOrder = buffer.order();
            buffer.order(order);
            if (isPEFriendlyBuffer(buffer)) {
               buffer.putShort((int)index, value);
            } else {
               putBufferShortBoundary(buffer, (int)index, value);
            }

            buffer.order(originalOrder);
         } catch (IndexOutOfBoundsException var10) {
            error.enter();
            throw InvalidBufferOffsetException.create(index, 2L);
         } catch (ReadOnlyBufferException var11) {
            error.enter();
            throw UnsupportedMessageException.create();
         }
      } else {
         error.enter();
         throw InvalidBufferOffsetException.create(index, 2L);
      }
   }

   @CompilerDirectives.TruffleBoundary
   private static void putBufferShortBoundary(ByteBuffer buffer, int index, short value) {
      buffer.putShort(index, value);
   }

   @ExportMessage
   public int readBufferInt(
      ByteOrder order,
      long index,
      @Cached.Shared("isBuffer") @Cached HostObject.IsBufferNode isBuffer,
      @Cached.Shared("error") @Cached BranchProfile error,
      @Cached.Shared("classProfile") @Cached("createClassProfile()") ValueProfile classProfile
   ) throws UnsupportedMessageException, InvalidBufferOffsetException {
      if (!isBuffer.execute(this)) {
         error.enter();
         throw UnsupportedMessageException.create();
      } else if (index >= 0L && 2147483647L >= index) {
         try {
            ByteBuffer buffer = classProfile.profile((ByteBuffer)this.obj);
            ByteOrder originalOrder = buffer.order();
            buffer.order(order);
            int result = isPEFriendlyBuffer(buffer) ? buffer.getInt((int)index) : getBufferIntBoundary(buffer, (int)index);
            buffer.order(originalOrder);
            return result;
         } catch (IndexOutOfBoundsException var10) {
            error.enter();
            throw InvalidBufferOffsetException.create(index, 4L);
         }
      } else {
         error.enter();
         throw InvalidBufferOffsetException.create(index, 4L);
      }
   }

   @CompilerDirectives.TruffleBoundary
   private static int getBufferIntBoundary(ByteBuffer buffer, int index) {
      return buffer.getInt(index);
   }

   @ExportMessage
   public void writeBufferInt(
      ByteOrder order,
      long index,
      int value,
      @Cached.Shared("isBuffer") @Cached HostObject.IsBufferNode isBuffer,
      @Cached.Shared("error") @Cached BranchProfile error,
      @Cached.Shared("classProfile") @Cached("createClassProfile()") ValueProfile classProfile
   ) throws InvalidBufferOffsetException, UnsupportedMessageException {
      if (!isBuffer.execute(this)) {
         error.enter();
         throw UnsupportedMessageException.create();
      } else if (index >= 0L && 2147483647L >= index) {
         try {
            ByteBuffer buffer = classProfile.profile((ByteBuffer)this.obj);
            ByteOrder originalOrder = buffer.order();
            buffer.order(order);
            if (isPEFriendlyBuffer(buffer)) {
               buffer.putInt((int)index, value);
            } else {
               putBufferIntBoundary(buffer, (int)index, value);
            }

            buffer.order(originalOrder);
         } catch (IndexOutOfBoundsException var10) {
            error.enter();
            throw InvalidBufferOffsetException.create(index, 4L);
         } catch (ReadOnlyBufferException var11) {
            error.enter();
            throw UnsupportedMessageException.create();
         }
      } else {
         error.enter();
         throw InvalidBufferOffsetException.create(index, 4L);
      }
   }

   @CompilerDirectives.TruffleBoundary
   private static void putBufferIntBoundary(ByteBuffer buffer, int index, int value) {
      buffer.putInt(index, value);
   }

   @ExportMessage
   public long readBufferLong(
      ByteOrder order,
      long index,
      @Cached.Shared("isBuffer") @Cached HostObject.IsBufferNode isBuffer,
      @Cached.Shared("error") @Cached BranchProfile error,
      @Cached.Shared("classProfile") @Cached("createClassProfile()") ValueProfile classProfile
   ) throws UnsupportedMessageException, InvalidBufferOffsetException {
      if (!isBuffer.execute(this)) {
         error.enter();
         throw UnsupportedMessageException.create();
      } else if (index >= 0L && 2147483647L >= index) {
         try {
            ByteBuffer buffer = classProfile.profile((ByteBuffer)this.obj);
            ByteOrder originalOrder = buffer.order();
            buffer.order(order);
            long result = isPEFriendlyBuffer(buffer) ? buffer.getLong((int)index) : getBufferLongBoundary(buffer, (int)index);
            buffer.order(originalOrder);
            return result;
         } catch (IndexOutOfBoundsException var11) {
            error.enter();
            throw InvalidBufferOffsetException.create(index, 8L);
         }
      } else {
         error.enter();
         throw InvalidBufferOffsetException.create(index, 8L);
      }
   }

   @CompilerDirectives.TruffleBoundary
   private static long getBufferLongBoundary(ByteBuffer buffer, int index) {
      return buffer.getLong(index);
   }

   @ExportMessage
   public void writeBufferLong(
      ByteOrder order,
      long index,
      long value,
      @Cached.Shared("isBuffer") @Cached HostObject.IsBufferNode isBuffer,
      @Cached.Shared("error") @Cached BranchProfile error,
      @Cached.Shared("classProfile") @Cached("createClassProfile()") ValueProfile classProfile
   ) throws InvalidBufferOffsetException, UnsupportedMessageException {
      if (!isBuffer.execute(this)) {
         error.enter();
         throw UnsupportedMessageException.create();
      } else if (index >= 0L && 2147483647L >= index) {
         try {
            ByteBuffer buffer = classProfile.profile((ByteBuffer)this.obj);
            ByteOrder originalOrder = buffer.order();
            buffer.order(order);
            if (isPEFriendlyBuffer(buffer)) {
               buffer.putLong((int)index, value);
            } else {
               putBufferLongBoundary(buffer, (int)index, value);
            }

            buffer.order(originalOrder);
         } catch (IndexOutOfBoundsException var11) {
            error.enter();
            throw InvalidBufferOffsetException.create(index, 8L);
         } catch (ReadOnlyBufferException var12) {
            error.enter();
            throw UnsupportedMessageException.create();
         }
      } else {
         error.enter();
         throw InvalidBufferOffsetException.create(index, 8L);
      }
   }

   @CompilerDirectives.TruffleBoundary
   private static void putBufferLongBoundary(ByteBuffer buffer, int index, long value) {
      buffer.putLong(index, value);
   }

   @ExportMessage
   public float readBufferFloat(
      ByteOrder order,
      long index,
      @Cached.Shared("isBuffer") @Cached HostObject.IsBufferNode isBuffer,
      @Cached.Shared("error") @Cached BranchProfile error,
      @Cached.Shared("classProfile") @Cached("createClassProfile()") ValueProfile classProfile
   ) throws UnsupportedMessageException, InvalidBufferOffsetException {
      if (!isBuffer.execute(this)) {
         error.enter();
         throw UnsupportedMessageException.create();
      } else if (index >= 0L && 2147483647L >= index) {
         try {
            ByteBuffer buffer = classProfile.profile((ByteBuffer)this.obj);
            ByteOrder originalOrder = buffer.order();
            buffer.order(order);
            float result = isPEFriendlyBuffer(buffer) ? buffer.getFloat((int)index) : getBufferFloatBoundary(buffer, (int)index);
            buffer.order(originalOrder);
            return result;
         } catch (IndexOutOfBoundsException var10) {
            error.enter();
            throw InvalidBufferOffsetException.create(index, 4L);
         }
      } else {
         error.enter();
         throw InvalidBufferOffsetException.create(index, 4L);
      }
   }

   @CompilerDirectives.TruffleBoundary
   private static float getBufferFloatBoundary(ByteBuffer buffer, int index) {
      return buffer.getFloat(index);
   }

   @ExportMessage
   public void writeBufferFloat(
      ByteOrder order,
      long index,
      float value,
      @Cached.Shared("isBuffer") @Cached HostObject.IsBufferNode isBuffer,
      @Cached.Shared("error") @Cached BranchProfile error,
      @Cached.Shared("classProfile") @Cached("createClassProfile()") ValueProfile classProfile
   ) throws InvalidBufferOffsetException, UnsupportedMessageException {
      if (!isBuffer.execute(this)) {
         error.enter();
         throw UnsupportedMessageException.create();
      } else if (index >= 0L && 2147483647L >= index) {
         try {
            ByteBuffer buffer = classProfile.profile((ByteBuffer)this.obj);
            ByteOrder originalOrder = buffer.order();
            buffer.order(order);
            if (isPEFriendlyBuffer(buffer)) {
               buffer.putFloat((int)index, value);
            } else {
               putBufferFloatBoundary(buffer, (int)index, value);
            }

            buffer.order(originalOrder);
         } catch (IndexOutOfBoundsException var10) {
            error.enter();
            throw InvalidBufferOffsetException.create(index, 4L);
         } catch (ReadOnlyBufferException var11) {
            error.enter();
            throw UnsupportedMessageException.create();
         }
      } else {
         error.enter();
         throw InvalidBufferOffsetException.create(index, 4L);
      }
   }

   @CompilerDirectives.TruffleBoundary
   private static void putBufferFloatBoundary(ByteBuffer buffer, int index, float value) {
      buffer.putFloat(index, value);
   }

   @ExportMessage
   public double readBufferDouble(
      ByteOrder order,
      long index,
      @Cached.Shared("isBuffer") @Cached HostObject.IsBufferNode isBuffer,
      @Cached.Shared("error") @Cached BranchProfile error,
      @Cached.Shared("classProfile") @Cached("createClassProfile()") ValueProfile classProfile
   ) throws UnsupportedMessageException, InvalidBufferOffsetException {
      if (!isBuffer.execute(this)) {
         error.enter();
         throw UnsupportedMessageException.create();
      } else if (index >= 0L && 2147483647L >= index) {
         try {
            ByteBuffer buffer = classProfile.profile((ByteBuffer)this.obj);
            ByteOrder originalOrder = buffer.order();
            buffer.order(order);
            double result = isPEFriendlyBuffer(buffer) ? buffer.getDouble((int)index) : getBufferDoubleBoundary(buffer, (int)index);
            buffer.order(originalOrder);
            return result;
         } catch (IndexOutOfBoundsException var11) {
            error.enter();
            throw InvalidBufferOffsetException.create(index, 8L);
         }
      } else {
         error.enter();
         throw InvalidBufferOffsetException.create(index, 8L);
      }
   }

   @CompilerDirectives.TruffleBoundary
   private static double getBufferDoubleBoundary(ByteBuffer buffer, int index) {
      return buffer.getDouble(index);
   }

   @ExportMessage
   public void writeBufferDouble(
      ByteOrder order,
      long index,
      double value,
      @Cached.Shared("isBuffer") @Cached HostObject.IsBufferNode isBuffer,
      @Cached.Shared("error") @Cached BranchProfile error,
      @Cached.Shared("classProfile") @Cached("createClassProfile()") ValueProfile classProfile
   ) throws InvalidBufferOffsetException, UnsupportedMessageException {
      if (!isBuffer.execute(this)) {
         error.enter();
         throw UnsupportedMessageException.create();
      } else if (index >= 0L && 2147483647L >= index) {
         try {
            ByteBuffer buffer = classProfile.profile((ByteBuffer)this.obj);
            ByteOrder originalOrder = buffer.order();
            buffer.order(order);
            if (isPEFriendlyBuffer(buffer)) {
               buffer.putDouble((int)index, value);
            } else {
               putBufferDoubleBoundary(buffer, (int)index, value);
            }

            buffer.order(originalOrder);
         } catch (IndexOutOfBoundsException var11) {
            error.enter();
            throw InvalidBufferOffsetException.create(index, 8L);
         } catch (ReadOnlyBufferException var12) {
            error.enter();
            throw UnsupportedMessageException.create();
         }
      } else {
         error.enter();
         throw InvalidBufferOffsetException.create(index, 8L);
      }
   }

   @CompilerDirectives.TruffleBoundary
   private static void putBufferDoubleBoundary(ByteBuffer buffer, int index, double value) {
      buffer.putDouble(index, value);
   }

   @ExportMessage
   boolean isNull() {
      return this.obj == null;
   }

   @ExportMessage
   boolean isExecutable(@Cached.Shared("lookupFunctionalMethod") @Cached HostObject.LookupFunctionalMethodNode lookupMethod) {
      return !this.isNull() && !this.isClass() && lookupMethod.execute(this, this.getLookupClass()) != null;
   }

   @ExportMessage
   Object execute(
      Object[] args,
      @Cached.Shared("hostExecute") @Cached HostExecuteNode doExecute,
      @Cached.Shared("lookupFunctionalMethod") @Cached HostObject.LookupFunctionalMethodNode lookupMethod,
      @Cached.Shared("error") @Cached BranchProfile error
   ) throws UnsupportedMessageException, UnsupportedTypeException, ArityException {
      if (!this.isNull() && !this.isClass()) {
         HostMethodDesc method = lookupMethod.execute(this, this.getLookupClass());
         if (method != null) {
            return doExecute.execute(method, this.obj, args, this.context);
         }
      }

      error.enter();
      throw UnsupportedMessageException.create();
   }

   @ExportMessage
   boolean isNumber(@Cached.Shared("classProfile") @Cached("createClassProfile()") ValueProfile classProfile) {
      if (this.isNull()) {
         return false;
      } else {
         Class<?> c = classProfile.profile(this.obj).getClass();
         return c == Byte.class || c == Short.class || c == Integer.class || c == Long.class || c == Float.class || c == Double.class;
      }
   }

   private static boolean isJavaPrimitiveNumber(Object value) {
      return value instanceof Byte
         || value instanceof Short
         || value instanceof Integer
         || value instanceof Long
         || value instanceof Float
         || value instanceof Double;
   }

   @ExportMessage
   boolean fitsInByte(@CachedLibrary("this") InteropLibrary thisLibrary, @Cached.Shared("numbers") @CachedLibrary(limit = "LIMIT") InteropLibrary numbers) {
      return thisLibrary.isNumber(this) ? numbers.fitsInByte(this.obj) : false;
   }

   @ExportMessage
   boolean fitsInShort(@CachedLibrary("this") InteropLibrary thisLibrary, @Cached.Shared("numbers") @CachedLibrary(limit = "LIMIT") InteropLibrary numbers) {
      return thisLibrary.isNumber(this) ? numbers.fitsInShort(this.obj) : false;
   }

   @ExportMessage
   boolean fitsInInt(@CachedLibrary("this") InteropLibrary thisLibrary, @Cached.Shared("numbers") @CachedLibrary(limit = "LIMIT") InteropLibrary numbers) {
      return thisLibrary.isNumber(this) ? numbers.fitsInInt(this.obj) : false;
   }

   @ExportMessage
   boolean fitsInLong(@CachedLibrary("this") InteropLibrary thisLibrary, @Cached.Shared("numbers") @CachedLibrary(limit = "LIMIT") InteropLibrary numbers) {
      return thisLibrary.isNumber(this) ? numbers.fitsInLong(this.obj) : false;
   }

   @ExportMessage
   boolean fitsInFloat(@CachedLibrary("this") InteropLibrary thisLibrary, @Cached.Shared("numbers") @CachedLibrary(limit = "LIMIT") InteropLibrary numbers) {
      return thisLibrary.isNumber(this) ? numbers.fitsInFloat(this.obj) : false;
   }

   @ExportMessage
   boolean fitsInDouble(@CachedLibrary("this") InteropLibrary thisLibrary, @Cached.Shared("numbers") @CachedLibrary(limit = "LIMIT") InteropLibrary numbers) {
      return thisLibrary.isNumber(this) ? numbers.fitsInDouble(this.obj) : false;
   }

   @ExportMessage
   byte asByte(
      @CachedLibrary("this") InteropLibrary thisLibrary,
      @Cached.Shared("numbers") @CachedLibrary(limit = "LIMIT") InteropLibrary numbers,
      @Cached.Shared("error") @Cached BranchProfile error
   ) throws UnsupportedMessageException {
      if (thisLibrary.isNumber(this)) {
         return numbers.asByte(this.obj);
      } else {
         error.enter();
         throw UnsupportedMessageException.create();
      }
   }

   @ExportMessage
   short asShort(
      @CachedLibrary("this") InteropLibrary thisLibrary,
      @Cached.Shared("numbers") @CachedLibrary(limit = "LIMIT") InteropLibrary numbers,
      @Cached.Shared("error") @Cached BranchProfile error
   ) throws UnsupportedMessageException {
      if (thisLibrary.isNumber(this)) {
         return numbers.asShort(this.obj);
      } else {
         error.enter();
         throw UnsupportedMessageException.create();
      }
   }

   @ExportMessage
   int asInt(
      @CachedLibrary("this") InteropLibrary thisLibrary,
      @Cached.Shared("numbers") @CachedLibrary(limit = "LIMIT") InteropLibrary numbers,
      @Cached.Shared("error") @Cached BranchProfile error
   ) throws UnsupportedMessageException {
      if (thisLibrary.isNumber(this)) {
         return numbers.asInt(this.obj);
      } else {
         error.enter();
         throw UnsupportedMessageException.create();
      }
   }

   @ExportMessage
   long asLong(
      @CachedLibrary("this") InteropLibrary thisLibrary,
      @Cached.Shared("numbers") @CachedLibrary(limit = "LIMIT") InteropLibrary numbers,
      @Cached.Shared("error") @Cached BranchProfile error
   ) throws UnsupportedMessageException {
      if (thisLibrary.isNumber(this)) {
         return numbers.asLong(this.obj);
      } else {
         error.enter();
         throw UnsupportedMessageException.create();
      }
   }

   @ExportMessage
   float asFloat(
      @CachedLibrary("this") InteropLibrary thisLibrary,
      @Cached.Shared("numbers") @CachedLibrary(limit = "LIMIT") InteropLibrary numbers,
      @Cached.Shared("error") @Cached BranchProfile error
   ) throws UnsupportedMessageException {
      if (thisLibrary.isNumber(this)) {
         return numbers.asFloat(this.obj);
      } else {
         error.enter();
         throw UnsupportedMessageException.create();
      }
   }

   @ExportMessage
   double asDouble(
      @CachedLibrary("this") InteropLibrary thisLibrary,
      @Cached.Shared("numbers") @CachedLibrary(limit = "LIMIT") InteropLibrary numbers,
      @Cached.Shared("error") @Cached BranchProfile error
   ) throws UnsupportedMessageException {
      if (thisLibrary.isNumber(this)) {
         return numbers.asDouble(this.obj);
      } else {
         error.enter();
         throw UnsupportedMessageException.create();
      }
   }

   @ExportMessage
   boolean isString(@Cached.Shared("classProfile") @Cached("createClassProfile()") ValueProfile classProfile) {
      if (this.isNull()) {
         return false;
      } else {
         Class<?> c = classProfile.profile(this.obj).getClass();
         return c == String.class || c == Character.class;
      }
   }

   @ExportMessage
   String asString(
      @CachedLibrary("this") InteropLibrary thisLibrary,
      @Cached.Shared("numbers") @CachedLibrary(limit = "LIMIT") InteropLibrary strings,
      @Cached.Shared("error") @Cached BranchProfile error
   ) throws UnsupportedMessageException {
      if (thisLibrary.isString(this)) {
         return strings.asString(this.obj);
      } else {
         error.enter();
         throw UnsupportedMessageException.create();
      }
   }

   @ExportMessage
   boolean isBoolean() {
      return this.isNull() ? false : this.obj.getClass() == Boolean.class;
   }

   @ExportMessage
   boolean asBoolean(@Cached.Shared("error") @Cached BranchProfile error) throws UnsupportedMessageException {
      if (this.isBoolean()) {
         return (Boolean)this.obj;
      } else {
         error.enter();
         throw UnsupportedMessageException.create();
      }
   }

   @ExportMessage
   boolean isDate() {
      return this.obj instanceof LocalDate
         || this.obj instanceof LocalDateTime
         || this.obj instanceof Instant
         || this.obj instanceof ZonedDateTime
         || this.obj instanceof Date
         || isInstantDate(this.obj);
   }

   @ExportMessage
   @CompilerDirectives.TruffleBoundary
   LocalDate asDate() throws UnsupportedMessageException {
      if (this.obj instanceof LocalDate) {
         return (LocalDate)this.obj;
      } else if (this.obj instanceof LocalDateTime) {
         return ((LocalDateTime)this.obj).toLocalDate();
      } else if (this.obj instanceof Instant) {
         return ((Instant)this.obj).atZone(UTC).toLocalDate();
      } else if (this.obj instanceof ZonedDateTime) {
         return ((ZonedDateTime)this.obj).toLocalDate();
      } else if (this.obj instanceof Date) {
         return ((Date)this.obj).toLocalDate();
      } else if (isInstantDate(this.obj)) {
         return ((java.util.Date)this.obj).toInstant().atZone(UTC).toLocalDate();
      } else {
         throw UnsupportedMessageException.create();
      }
   }

   @ExportMessage
   boolean isTime() {
      return this.obj instanceof LocalTime
         || this.obj instanceof LocalDateTime
         || this.obj instanceof Instant
         || this.obj instanceof ZonedDateTime
         || this.obj instanceof Time
         || isInstantDate(this.obj);
   }

   @ExportMessage
   @CompilerDirectives.TruffleBoundary
   LocalTime asTime() throws UnsupportedMessageException {
      if (this.obj instanceof LocalTime) {
         return (LocalTime)this.obj;
      } else if (this.obj instanceof LocalDateTime) {
         return ((LocalDateTime)this.obj).toLocalTime();
      } else if (this.obj instanceof ZonedDateTime) {
         return ((ZonedDateTime)this.obj).toLocalTime();
      } else if (this.obj instanceof Instant) {
         return ((Instant)this.obj).atZone(UTC).toLocalTime();
      } else if (this.obj instanceof Time) {
         return ((Time)this.obj).toLocalTime();
      } else if (isInstantDate(this.obj)) {
         return ((java.util.Date)this.obj).toInstant().atZone(UTC).toLocalTime();
      } else {
         throw UnsupportedMessageException.create();
      }
   }

   private static boolean isInstantDate(Object v) {
      return v instanceof java.util.Date && !(v instanceof Time) && !(v instanceof Date);
   }

   @ExportMessage
   boolean isTimeZone() {
      return this.obj instanceof ZoneId || this.obj instanceof Instant || this.obj instanceof ZonedDateTime || isInstantDate(this.obj);
   }

   @ExportMessage
   ZoneId asTimeZone() throws UnsupportedMessageException {
      if (this.obj instanceof ZoneId) {
         return (ZoneId)this.obj;
      } else if (this.obj instanceof ZonedDateTime) {
         return ((ZonedDateTime)this.obj).getZone();
      } else if (this.obj instanceof Instant) {
         return UTC;
      } else if (isInstantDate(this.obj)) {
         return UTC;
      } else {
         throw UnsupportedMessageException.create();
      }
   }

   @ExportMessage
   @CompilerDirectives.TruffleBoundary
   Instant asInstant() throws UnsupportedMessageException {
      if (this.obj instanceof ZonedDateTime) {
         return ((ZonedDateTime)this.obj).toInstant();
      } else if (this.obj instanceof Instant) {
         return (Instant)this.obj;
      } else if (isInstantDate(this.obj)) {
         return ((java.util.Date)this.obj).toInstant();
      } else {
         throw UnsupportedMessageException.create();
      }
   }

   @ExportMessage
   boolean isDuration() {
      return this.obj instanceof Duration;
   }

   @ExportMessage
   Duration asDuration() throws UnsupportedMessageException {
      if (this.isDuration()) {
         return (Duration)this.obj;
      } else {
         throw UnsupportedMessageException.create();
      }
   }

   @ExportMessage
   boolean isException() {
      return this.obj instanceof Throwable;
   }

   @ExportMessage
   ExceptionType getExceptionType(@Cached.Shared("error") @Cached BranchProfile error) throws UnsupportedMessageException {
      if (this.isException()) {
         return this.obj instanceof InterruptedException ? ExceptionType.INTERRUPT : ExceptionType.RUNTIME_ERROR;
      } else {
         error.enter();
         throw UnsupportedMessageException.create();
      }
   }

   @ExportMessage
   boolean isExceptionIncompleteSource(@Cached.Shared("error") @Cached BranchProfile error) throws UnsupportedMessageException {
      if (this.isException()) {
         return false;
      } else {
         error.enter();
         throw UnsupportedMessageException.create();
      }
   }

   @ExportMessage
   int getExceptionExitStatus(@Cached.Shared("error") @Cached BranchProfile error) throws UnsupportedMessageException {
      error.enter();
      throw UnsupportedMessageException.create();
   }

   @ExportMessage
   @CompilerDirectives.TruffleBoundary
   boolean hasExceptionMessage() {
      return this.isException() && ((Throwable)this.obj).getMessage() != null;
   }

   @ExportMessage
   @CompilerDirectives.TruffleBoundary
   Object getExceptionMessage(@Cached.Shared("error") @Cached BranchProfile error) throws UnsupportedMessageException {
      String message = this.isException() ? ((Throwable)this.obj).getMessage() : null;
      if (message != null) {
         return message;
      } else {
         error.enter();
         throw UnsupportedMessageException.create();
      }
   }

   @ExportMessage
   @CompilerDirectives.TruffleBoundary
   boolean hasExceptionCause() {
      return this.isException() && ((Throwable)this.obj).getCause() instanceof AbstractTruffleException;
   }

   @ExportMessage
   @CompilerDirectives.TruffleBoundary
   Object getExceptionCause() throws UnsupportedMessageException {
      if (this.isException()) {
         Throwable cause = ((Throwable)this.obj).getCause();
         if (cause instanceof AbstractTruffleException) {
            return cause;
         }
      }

      throw UnsupportedMessageException.create();
   }

   @ExportMessage
   @CompilerDirectives.TruffleBoundary
   boolean hasExceptionStackTrace() {
      return this.isException() && TruffleStackTrace.fillIn((Throwable)this.obj) != null;
   }

   @ExportMessage
   @CompilerDirectives.TruffleBoundary
   Object getExceptionStackTrace() throws UnsupportedMessageException {
      if (this.isException()) {
         return HostAccessor.EXCEPTION.getExceptionStackTrace(this.obj);
      } else {
         throw UnsupportedMessageException.create();
      }
   }

   @ExportMessage
   RuntimeException throwException(@Cached.Shared("error") @Cached BranchProfile error) throws UnsupportedMessageException {
      if (this.isException()) {
         HostException ex = (HostException)this.extraInfo;
         if (ex == null) {
            ex = new HostException((Throwable)this.obj, this.context);
         }

         throw ex;
      } else {
         error.enter();
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
   String toDisplayString(boolean allowSideEffects) {
      return toStringImpl(this.context, this.obj, 0, allowSideEffects);
   }

   @CompilerDirectives.TruffleBoundary
   private static String toStringImpl(HostContext context, Object javaObject, int level, boolean allowSideEffects) {
      try {
         if (javaObject == null) {
            return "null";
         } else if (javaObject.getClass().isArray()) {
            return arrayToString(context, javaObject, level, allowSideEffects);
         } else if (javaObject instanceof Class) {
            return getTypeNameSafe((Class<?>)javaObject);
         } else {
            if (allowSideEffects && context != null) {
               Object hostObject = forObject(javaObject, context);

               try {
                  InteropLibrary thisLib = InteropLibrary.getUncached(hostObject);
                  if (thisLib.isBoolean(hostObject)) {
                     return Boolean.toString(thisLib.asBoolean(hostObject));
                  }

                  if (thisLib.isString(hostObject)) {
                     return thisLib.asString(hostObject);
                  }

                  if (thisLib.isNumber(hostObject)) {
                     assert isJavaPrimitiveNumber(javaObject) : javaObject;

                     return javaObject.toString();
                  }

                  if (thisLib.isMemberInvocable(hostObject, "toString")) {
                     Object result = thisLib.invokeMember(hostObject, "toString");
                     return InteropLibrary.getUncached().asString(result);
                  }
               } catch (InteropException var7) {
               }
            }

            return getTypeNameSafe(javaObject.getClass());
         }
      } catch (Throwable var8) {
         throw context.hostToGuestException(var8);
      }
   }

   @CompilerDirectives.TruffleBoundary
   private static String getTypeNameSafe(Class<?> type) {
      String typeName = type.getTypeName();
      int slash = typeName.indexOf(47);
      return slash != -1 ? typeName.substring(0, slash) : typeName;
   }

   private static String arrayToString(HostContext context, Object array, int level, boolean allowSideEffects) {
      CompilerAsserts.neverPartOfCompilation();
      if (array == null) {
         return "null";
      } else if (level > 0) {
         return "[...]";
      } else {
         int iMax = Array.getLength(array) - 1;
         if (iMax == -1) {
            return "[]";
         } else {
            StringBuilder b = new StringBuilder();
            b.append('[');
            int i = 0;

            while (true) {
               Object arrayValue = Array.get(array, i);
               b.append(toStringImpl(context, arrayValue, level + 1, allowSideEffects));
               if (i == iMax) {
                  return b.append(']').toString();
               }

               b.append(", ");
               i++;
            }
         }
      }
   }

   @ExportMessage
   boolean hasIterator(
      @Cached.Shared("isIterable") @Cached HostObject.IsIterableNode isIterable, @Cached.Shared("isArray") @Cached HostObject.IsArrayNode isArray
   ) {
      return isIterable.execute(this) || isArray.execute(this);
   }

   @ExportMessage
   boolean isIterator(@Cached.Shared("isIterator") @Cached HostObject.IsIteratorNode isIterator) {
      return isIterator.execute(this);
   }

   @ExportMessage
   boolean hasHashEntries(@Cached.Shared("isMap") @Cached HostObject.IsMapNode isMap) {
      return isMap.execute(this);
   }

   @ExportMessage.Repeat(
      {@ExportMessage(name = "isHashEntryReadable"), @ExportMessage(name = "isHashEntryModifiable"), @ExportMessage(name = "isHashEntryRemovable")}
   )
   boolean isHashEntryReadable(
      Object key, @Cached.Shared("isMap") @Cached HostObject.IsMapNode isMap, @Cached.Shared("containsKey") @Cached HostObject.ContainsKeyNode containsKey
   ) {
      return isMap.execute(this) && containsKey.execute(this, key);
   }

   @ExportMessage
   boolean isHashEntryInsertable(
      Object key, @Cached.Shared("isMap") @Cached HostObject.IsMapNode isMap, @Cached.Shared("containsKey") @Cached HostObject.ContainsKeyNode containsKey
   ) {
      return isMap.execute(this) && !containsKey.execute(this, key);
   }

   @ExportMessage
   boolean hasMetaObject() {
      return !this.isNull();
   }

   @ExportMessage
   Object getMetaObject() throws UnsupportedMessageException {
      if (this.hasMetaObject()) {
         Object javaObject = this.obj;
         Class<?> javaType = javaObject.getClass();
         return forClass(javaType, this.context);
      } else {
         throw UnsupportedMessageException.create();
      }
   }

   @ExportMessage
   boolean isMetaObject() {
      return this.isClass();
   }

   @ExportMessage
   @CompilerDirectives.TruffleBoundary
   Object getMetaQualifiedName() throws UnsupportedMessageException {
      if (this.isClass()) {
         return this.asClass().getTypeName();
      } else {
         throw UnsupportedMessageException.create();
      }
   }

   @ExportMessage
   @CompilerDirectives.TruffleBoundary
   Object getMetaSimpleName() throws UnsupportedMessageException {
      if (this.isClass()) {
         return this.asClass().getSimpleName();
      } else {
         throw UnsupportedMessageException.create();
      }
   }

   @ExportMessage
   @CompilerDirectives.TruffleBoundary
   boolean isMetaInstance(Object other, @CachedLibrary("this") InteropLibrary library, @Cached.Shared("error") @Cached BranchProfile error) throws UnsupportedMessageException {
      if (this.isClass()) {
         Class<?> c = this.asClass();
         HostLanguage language = this.context != null ? HostLanguage.get(library) : null;
         if (isInstance(language, other)) {
            Object otherHostObj = valueOf(language, other);
            return otherHostObj == null ? false : c.isInstance(otherHostObj);
         } else if (HostProxy.isProxyGuestObject(language, other)) {
            Proxy otherHost = HostProxy.toProxyHostObject(language, other);
            return c.isInstance(otherHost);
         } else {
            return HostToTypeNode.canConvert(
               other,
               c,
               c,
               HostToTypeNode.allowsImplementation(this.context, c),
               this.context,
               8,
               InteropLibrary.getFactory().getUncached(other),
               HostTargetMappingNode.getUncached()
            );
         }
      } else {
         error.enter();
         throw UnsupportedMessageException.create();
      }
   }

   @ExportMessage
   @CompilerDirectives.TruffleBoundary
   boolean hasMetaParents() {
      return this.isClass() && (this.asClass().getSuperclass() != null || this.asClass().getInterfaces().length > 0);
   }

   @ExportMessage
   @CompilerDirectives.TruffleBoundary
   Object getMetaParents() throws UnsupportedMessageException {
      if (!this.hasMetaParents()) {
         throw UnsupportedMessageException.create();
      } else {
         Class<?> superClass = this.asClass().getSuperclass();
         Class<?>[] interfaces = this.asClass().getInterfaces();
         HostObject[] metaObjects = new HostObject[superClass == null ? interfaces.length : interfaces.length + 1];
         int i = 0;
         if (superClass != null) {
            metaObjects[i++] = forClass(superClass, this.context);
         }

         for (int j = 0; j < interfaces.length; j++) {
            metaObjects[i++] = forClass(interfaces[j], this.context);
         }

         return new HostObject.TypesArray(metaObjects);
      }
   }

   boolean isStaticClass() {
      return this.extraInfo instanceof Class;
   }

   Class<?> getObjectClass() {
      return this.obj == null ? null : this.obj.getClass();
   }

   Class<?> asStaticClass() {
      assert this.isStaticClass();

      return (Class<?>)this.obj;
   }

   Class<?> asClass() {
      assert this.isClass();

      return (Class<?>)this.obj;
   }

   Class<?> getLookupClass() {
      if (this.obj == null) {
         return null;
      } else {
         return this.isStaticClass() ? this.asStaticClass() : this.obj.getClass();
      }
   }

   HostClassCache getHostClassCache() {
      assert this.context != null : "host cache must not be used for null";

      return HostClassCache.forInstance(this);
   }

   @ExportMessage
   static int identityHashCode(HostObject receiver) {
      return System.identityHashCode(receiver.obj);
   }

   @Override
   public boolean equals(Object o) {
      if (!(o instanceof HostObject)) {
         return false;
      } else {
         HostObject other = (HostObject)o;
         return this.obj == other.obj && this.extraInfo == other.extraInfo && this.context == other.context;
      }
   }

   @Override
   public String toString() {
      if (this.obj == null) {
         return "null";
      } else {
         return this.isClass()
            ? "JavaClass[" + this.asClass().getTypeName() + "]"
            : "JavaObject[" + this.obj + " (" + this.getObjectClass().getTypeName() + ")]";
      }
   }

   @GenerateUncached
   abstract static class ArrayGet extends Node {
      protected abstract Object execute(Object array, int index);

      @Specialization
      static boolean doBoolean(boolean[] array, int index) {
         return array[index];
      }

      @Specialization
      static byte doByte(byte[] array, int index) {
         return array[index];
      }

      @Specialization
      static short doShort(short[] array, int index) {
         return array[index];
      }

      @Specialization
      static char doChar(char[] array, int index) {
         return array[index];
      }

      @Specialization
      static int doInt(int[] array, int index) {
         return array[index];
      }

      @Specialization
      static long doLong(long[] array, int index) {
         return array[index];
      }

      @Specialization
      static float doFloat(float[] array, int index) {
         return array[index];
      }

      @Specialization
      static double doDouble(double[] array, int index) {
         return array[index];
      }

      @Specialization
      static Object doObject(Object[] array, int index) {
         return array[index];
      }
   }

   @GenerateUncached
   abstract static class ArraySet extends Node {
      protected abstract void execute(Object array, int index, Object value);

      @Specialization
      static void doBoolean(boolean[] array, int index, boolean value) {
         array[index] = value;
      }

      @Specialization
      static void doByte(byte[] array, int index, byte value) {
         array[index] = value;
      }

      @Specialization
      static void doShort(short[] array, int index, short value) {
         array[index] = value;
      }

      @Specialization
      static void doChar(char[] array, int index, char value) {
         array[index] = value;
      }

      @Specialization
      static void doInt(int[] array, int index, int value) {
         array[index] = value;
      }

      @Specialization
      static void doLong(long[] array, int index, long value) {
         array[index] = value;
      }

      @Specialization
      static void doFloat(float[] array, int index, float value) {
         array[index] = value;
      }

      @Specialization
      static void doDouble(double[] array, int index, double value) {
         array[index] = value;
      }

      @Specialization
      static void doObject(Object[] array, int index, Object value) {
         array[index] = value;
      }
   }

   @GenerateUncached
   abstract static class ContainsKeyNode extends Node {
      public abstract boolean execute(HostObject receiver, Object key);

      @Specialization(guards = "isMap.execute(receiver)", limit = "1")
      protected static boolean doMap(
         HostObject receiver,
         Object key,
         @Cached.Shared("isMap") @Cached HostObject.IsMapNode isMap,
         @Cached HostToTypeNode toHost,
         @Cached BranchProfile error
      ) {
         Object hostKey;
         try {
            hostKey = toHost.execute(receiver.context, key, Object.class, null, true);
         } catch (RuntimeException var9) {
            error.enter();
            RuntimeException ee = HostObject.unboxEngineException(receiver, var9);
            if (ee != null) {
               return false;
            }

            throw var9;
         }

         try {
            return HostObject.GuestToHostCalls.containsMapKey(receiver, hostKey);
         } catch (Throwable var8) {
            error.enter();
            throw receiver.context.hostToGuestException(var8);
         }
      }

      @Specialization(guards = "!isMap.execute(receiver)", limit = "1")
      protected static boolean doNotMap(HostObject receiver, Object key, @Cached.Shared("isMap") @Cached HostObject.IsMapNode isMap) {
         return false;
      }
   }

   @ExportMessage
   abstract static class GetArraySize {
      @Specialization(guards = "isArray.execute(receiver)", limit = "1")
      protected static long doArray(HostObject receiver, @Cached.Shared("isArray") @Cached HostObject.IsArrayNode isArray) {
         return Array.getLength(receiver.obj);
      }

      @Specialization(guards = "isList.execute(receiver)", limit = "1")
      protected static long doList(
         HostObject receiver, @Cached.Shared("isList") @Cached HostObject.IsListNode isList, @Cached.Shared("error") @Cached BranchProfile error
      ) {
         try {
            return HostObject.GuestToHostCalls.getListSize(receiver);
         } catch (Throwable var4) {
            error.enter();
            throw receiver.context.hostToGuestException(var4);
         }
      }

      @Specialization(guards = "isMapEntry.execute(receiver)", limit = "1")
      protected static long doMapEntry(HostObject receiver, @Cached.Shared("isMapEntry") @Cached HostObject.IsMapEntryNode isMapEntry) {
         return 2L;
      }

      @Specialization(guards = {"!isArray.execute(receiver)", "!isList.execute(receiver)", "!isMapEntry.execute(receiver)"}, limit = "1")
      protected static long doNotArrayOrList(
         HostObject receiver,
         @Cached.Shared("isArray") @Cached HostObject.IsArrayNode isArray,
         @Cached.Shared("isList") @Cached HostObject.IsListNode isList,
         @Cached.Shared("isMapEntry") @Cached HostObject.IsMapEntryNode isMapEntry
      ) throws UnsupportedMessageException {
         throw UnsupportedMessageException.create();
      }
   }

   @ExportMessage
   abstract static class GetHashEntriesIterator {
      @Specialization(guards = "isMap.execute(receiver)", limit = "1")
      protected static Object doMap(
         HostObject receiver,
         @Cached.Shared("isMap") @Cached HostObject.IsMapNode isMap,
         @Cached.Shared("toGuest") @Cached HostContext.ToGuestValueNode toGuest,
         @Cached.Shared("error") @Cached BranchProfile error
      ) {
         Object hostValue;
         try {
            hostValue = HostObject.GuestToHostCalls.getEntriesIterator(receiver);
         } catch (Throwable var6) {
            error.enter();
            throw receiver.context.hostToGuestException(var6);
         }

         return toGuest.execute(receiver.context, hostValue);
      }

      @Specialization(guards = "!isMap.execute(receiver)", limit = "1")
      protected static Object doNotMap(HostObject receiver, @Cached.Shared("isMap") @Cached HostObject.IsMapNode isMap) throws UnsupportedMessageException {
         throw UnsupportedMessageException.create();
      }
   }

   @ExportMessage
   abstract static class GetHashSize {
      @Specialization(guards = "isMap.execute(receiver)", limit = "1")
      protected static long doMap(
         HostObject receiver, @Cached.Shared("isMap") @Cached HostObject.IsMapNode isMap, @Cached.Shared("error") @Cached BranchProfile error
      ) {
         try {
            return HostObject.GuestToHostCalls.getMapSize(receiver);
         } catch (Throwable var4) {
            error.enter();
            throw receiver.context.hostToGuestException(var4);
         }
      }

      @Specialization(guards = "!isMap.execute(receiver)", limit = "1")
      protected static long doNotMap(HostObject receiver, @Cached.Shared("isMap") @Cached HostObject.IsMapNode isMap) throws UnsupportedMessageException {
         throw UnsupportedMessageException.create();
      }
   }

   @ExportMessage
   abstract static class GetIterator {
      @Specialization(guards = "isArray.execute(receiver)", limit = "1")
      protected static Object doArray(
         HostObject receiver,
         @Cached.Shared("isArray") @Cached HostObject.IsArrayNode isArray,
         @Cached.Shared("toGuest") @Cached HostContext.ToGuestValueNode toGuest
      ) {
         return toGuest.execute(receiver.context, arrayIteratorImpl(receiver));
      }

      @CompilerDirectives.TruffleBoundary
      private static Object arrayIteratorImpl(Object receiver) {
         return HostAccessor.INTEROP.createDefaultIterator(receiver);
      }

      @Specialization(guards = "isIterable.execute(receiver)", limit = "1")
      protected static Object doIterable(
         HostObject receiver,
         @Cached.Shared("isIterable") @Cached HostObject.IsIterableNode isIterable,
         @Cached.Shared("toGuest") @Cached HostContext.ToGuestValueNode toGuest,
         @Cached.Shared("error") @Cached BranchProfile error
      ) {
         Object hostValue;
         try {
            hostValue = HostObject.GuestToHostCalls.getIterator(receiver);
         } catch (Throwable var6) {
            error.enter();
            throw receiver.context.hostToGuestException(var6);
         }

         return toGuest.execute(receiver.context, hostValue);
      }

      @Specialization(guards = {"!isArray.execute(receiver)", "!isIterable.execute(receiver)"}, limit = "1")
      protected static Object doNotArrayOrIterable(
         HostObject receiver,
         @Cached.Shared("isArray") @Cached HostObject.IsArrayNode isArray,
         @Cached.Shared("isIterable") @Cached HostObject.IsIterableNode isIterable
      ) throws UnsupportedMessageException {
         throw UnsupportedMessageException.create();
      }
   }

   @ExportMessage
   abstract static class GetIteratorNextElement {
      @Specialization(guards = "isIterator.execute(receiver)", limit = "1")
      protected static Object doIterator(
         HostObject receiver,
         @Cached.Shared("isIterator") @Cached HostObject.IsIteratorNode isIterator,
         @Cached.Shared("toGuest") @Cached HostContext.ToGuestValueNode toGuest,
         @Cached.Shared("error") @Cached BranchProfile error,
         @Cached.Exclusive @Cached BranchProfile stopIteration
      ) throws StopIterationException {
         Object next;
         try {
            next = HostObject.GuestToHostCalls.getIteratorNext(receiver);
         } catch (NoSuchElementException var7) {
            stopIteration.enter();
            throw StopIterationException.create();
         } catch (Throwable var8) {
            error.enter();
            throw receiver.context.hostToGuestException(var8);
         }

         return toGuest.execute(receiver.context, next);
      }

      @Specialization(guards = "!isIterator.execute(receiver)", limit = "1")
      protected static Object doNotIterator(HostObject receiver, @Cached.Shared("isIterator") @Cached HostObject.IsIteratorNode isIterator) throws UnsupportedMessageException {
         throw UnsupportedMessageException.create();
      }
   }

   abstract static class GuestToHostCalls {
      private GuestToHostCalls() {
      }

      @CompilerDirectives.TruffleBoundary(allowInlining = true)
      static int getListSize(HostObject hostObject) {
         return ((List)hostObject.obj).size();
      }

      @CompilerDirectives.TruffleBoundary
      static void setListElement(HostObject receiver, long index, final Object hostValue) {
         List<Object> list = (List<Object>)receiver.obj;
         if (index == list.size()) {
            list.add(hostValue);
         } else {
            list.set((int)index, hostValue);
         }
      }

      @CompilerDirectives.TruffleBoundary
      static Object removeListElement(HostObject receiver, long index) {
         return ((List)receiver.obj).remove((int)index);
      }

      @CompilerDirectives.TruffleBoundary
      static Object readListElement(HostObject receiver, long index) {
         return ((List)receiver.obj).get((int)index);
      }

      @CompilerDirectives.TruffleBoundary
      static Object setMapEntryValue(HostObject receiver, Object value) {
         return ((Entry)receiver.obj).setValue(value);
      }

      @CompilerDirectives.TruffleBoundary
      static Object getMapEntryKey(HostObject receiver) {
         return ((Entry)receiver.obj).getKey();
      }

      @CompilerDirectives.TruffleBoundary
      static Object getMapEntryValue(HostObject receiver) {
         return ((Entry)receiver.obj).getValue();
      }

      @CompilerDirectives.TruffleBoundary
      static Object getIterator(HostObject receiver) {
         return ((Iterable)receiver.obj).iterator();
      }

      @CompilerDirectives.TruffleBoundary
      static boolean hasIteratorNext(HostObject receiver) {
         return ((Iterator)receiver.obj).hasNext();
      }

      @CompilerDirectives.TruffleBoundary
      static Object getIteratorNext(HostObject receiver) {
         return ((Iterator)receiver.obj).next();
      }

      @CompilerDirectives.TruffleBoundary
      static int getMapSize(HostObject receiver) {
         return ((Map)receiver.obj).size();
      }

      @CompilerDirectives.TruffleBoundary
      static Object getMapValue(HostObject receiver, Object key, Object defaultValue) {
         return ((Map)receiver.obj).getOrDefault(key, defaultValue);
      }

      @CompilerDirectives.TruffleBoundary
      static void putMapValue(HostObject receiver, Object key, Object value) {
         ((Map)receiver.obj).put(key, value);
      }

      @CompilerDirectives.TruffleBoundary
      static boolean removeMapValue(HostObject receiver, Object key) {
         Map<?, ?> map = (Map<?, ?>)receiver.obj;
         if (map.containsKey(key)) {
            map.remove(key);
            return true;
         } else {
            return false;
         }
      }

      @CompilerDirectives.TruffleBoundary
      static Object getEntriesIterator(HostObject receiver) {
         return ((Map)receiver.obj).entrySet().iterator();
      }

      @CompilerDirectives.TruffleBoundary
      static boolean containsMapKey(HostObject receiver, Object key) {
         return ((Map)receiver.obj).containsKey(key);
      }
   }

   @ExportMessage
   abstract static class HasIteratorNextElement {
      @Specialization(guards = "isIterator.execute(receiver)", limit = "1")
      protected static boolean doIterator(
         HostObject receiver, @Cached.Shared("isIterator") @Cached HostObject.IsIteratorNode isIterator, @Cached.Shared("error") @Cached BranchProfile error
      ) {
         try {
            return HostObject.GuestToHostCalls.hasIteratorNext(receiver);
         } catch (Throwable var4) {
            error.enter();
            throw receiver.context.hostToGuestException(var4);
         }
      }

      @Specialization(guards = "!isIterator.execute(receiver)", limit = "1")
      protected static boolean doNotIterator(HostObject receiver, @Cached.Shared("isIterator") @Cached HostObject.IsIteratorNode isIterator) throws UnsupportedMessageException {
         throw UnsupportedMessageException.create();
      }
   }

   @ExportMessage
   static class Instantiate {
      @Specialization(guards = "!receiver.isClass()")
      static Object doUnsupported(HostObject receiver, Object[] args) throws UnsupportedMessageException {
         throw UnsupportedMessageException.create();
      }

      @Specialization(guards = "receiver.isArrayClass()")
      static Object doArrayCached(
         HostObject receiver, Object[] args, @CachedLibrary(limit = "1") InteropLibrary indexes, @Cached.Shared("error") @Cached BranchProfile error
      ) throws UnsupportedMessageException, UnsupportedTypeException, ArityException {
         if (args.length != 1) {
            error.enter();
            throw ArityException.create(1, 1, args.length);
         } else {
            Object arg0 = args[0];
            if (indexes.fitsInInt(arg0)) {
               int length = indexes.asInt(arg0);
               Object array = Array.newInstance(receiver.asClass().getComponentType(), length);
               return HostObject.forObject(array, receiver.context);
            } else {
               error.enter();
               throw UnsupportedTypeException.create(args);
            }
         }
      }

      @Specialization(guards = "receiver.isDefaultClass()")
      static Object doObjectCached(
         HostObject receiver,
         Object[] arguments,
         @Cached.Shared("lookupConstructor") @Cached HostObject.LookupConstructorNode lookupConstructor,
         @Cached.Shared("hostExecute") @Cached HostExecuteNode executeMethod,
         @Cached.Shared("error") @Cached BranchProfile error
      ) throws UnsupportedMessageException, UnsupportedTypeException, ArityException {
         assert !receiver.isArrayClass();

         HostMethodDesc constructor = lookupConstructor.execute(receiver, receiver.asClass());
         if (constructor != null) {
            return executeMethod.execute(constructor, null, arguments, receiver.context);
         } else {
            error.enter();
            throw UnsupportedMessageException.create();
         }
      }
   }

   @ExportMessage
   static class IsArrayElementModifiable {
      @Specialization(guards = "isArray.execute(receiver)", limit = "1")
      static boolean doArray(HostObject receiver, long index, @Cached.Shared("isArray") @Cached HostObject.IsArrayNode isArray) {
         long size = Array.getLength(receiver.obj);
         return index >= 0L && index < size;
      }

      @Specialization(guards = "isList.execute(receiver)", limit = "1")
      static boolean doList(
         HostObject receiver, long index, @Cached.Shared("isList") @Cached HostObject.IsListNode isList, @Cached.Shared("error") @Cached BranchProfile error
      ) {
         try {
            long size = HostObject.GuestToHostCalls.getListSize(receiver);
            return index >= 0L && index < size;
         } catch (Throwable var7) {
            error.enter();
            throw receiver.context.hostToGuestException(var7);
         }
      }

      @Specialization(guards = "isMapEntry.execute(receiver)", limit = "1")
      static boolean doMapEntry(HostObject receiver, long index, @Cached.Shared("isMapEntry") @Cached HostObject.IsMapEntryNode isMapEntry) {
         return index == 1L;
      }

      @Specialization(guards = {"!isList.execute(receiver)", "!isArray.execute(receiver)", "!isMapEntry.execute(receiver)"}, limit = "1")
      static boolean doNotArrayOrList(
         HostObject receiver,
         long index,
         @Cached.Shared("isList") @Cached HostObject.IsListNode isList,
         @Cached.Shared("isArray") @Cached HostObject.IsArrayNode isArray,
         @Cached.Shared("isMapEntry") @Cached HostObject.IsMapEntryNode isMapEntry
      ) {
         return false;
      }
   }

   @ExportMessage
   static class IsArrayElementReadable {
      @Specialization(guards = "isArray.execute(receiver)", limit = "1")
      static boolean doArray(HostObject receiver, long index, @Cached.Shared("isArray") @Cached HostObject.IsArrayNode isArray) {
         long size = Array.getLength(receiver.obj);
         return index >= 0L && index < size;
      }

      @Specialization(guards = "isList.execute(receiver)", limit = "1")
      static boolean doList(
         HostObject receiver, long index, @Cached.Shared("isList") @Cached HostObject.IsListNode isList, @Cached.Shared("error") @Cached BranchProfile error
      ) {
         try {
            long size = HostObject.GuestToHostCalls.getListSize(receiver);
            return index >= 0L && index < size;
         } catch (Throwable var7) {
            error.enter();
            throw receiver.context.hostToGuestException(var7);
         }
      }

      @Specialization(guards = "isMapEntry.execute(receiver)", limit = "1")
      static boolean doMapEntry(HostObject receiver, long index, @Cached.Shared("isMapEntry") @Cached HostObject.IsMapEntryNode isMapEntry) {
         return index >= 0L && index < 2L;
      }

      @Specialization(guards = {"!isList.execute(receiver)", "!isArray.execute(receiver)", "!isMapEntry.execute(receiver)"}, limit = "1")
      static boolean doNotArrayOrList(
         HostObject receiver,
         long index,
         @Cached.Shared("isList") @Cached HostObject.IsListNode isList,
         @Cached.Shared("isArray") @Cached HostObject.IsArrayNode isArray,
         @Cached.Shared("isMapEntry") @Cached HostObject.IsMapEntryNode isMapEntry
      ) {
         return false;
      }
   }

   @ExportMessage
   static class IsArrayElementRemovable {
      @Specialization(guards = "isList.execute(receiver)", limit = "1")
      static boolean doList(
         HostObject receiver, long index, @Cached.Shared("isList") @Cached HostObject.IsListNode isList, @Cached.Shared("error") @Cached BranchProfile error
      ) {
         try {
            return index >= 0L && index < HostObject.GuestToHostCalls.getListSize(receiver);
         } catch (Throwable var6) {
            error.enter();
            throw receiver.context.hostToGuestException(var6);
         }
      }

      @Specialization(guards = "!isList.execute(receiver)", limit = "1")
      static boolean doOther(HostObject receiver, long index, @Cached.Shared("isList") @Cached HostObject.IsListNode isList) {
         return false;
      }
   }

   @GenerateUncached
   abstract static class IsArrayNode extends Node {
      public abstract boolean execute(HostObject receiver);

      @Specialization(guards = "receiver.obj == null")
      public boolean doNull(HostObject receiver) {
         return false;
      }

      @Specialization(guards = "receiver.obj != null")
      public boolean doDefault(HostObject receiver, @Cached(value = "receiver.getHostClassCache().isArrayAccess()", allowUncached = true) boolean isArrayAccess) {
         assert receiver.getHostClassCache().isArrayAccess() == isArrayAccess;

         return isArrayAccess && receiver.obj.getClass().isArray();
      }
   }

   @GenerateUncached
   abstract static class IsBufferNode extends Node {
      public abstract boolean execute(HostObject receiver);

      @Specialization(guards = "receiver.obj == null")
      public boolean doNull(HostObject receiver) {
         return false;
      }

      @Specialization(guards = "receiver.obj != null")
      public boolean doDefault(
         HostObject receiver, @Cached(value = "receiver.getHostClassCache().isBufferAccess()", allowUncached = true) boolean isBufferAccess
      ) {
         assert receiver.getHostClassCache().isBufferAccess() == isBufferAccess;

         return isBufferAccess && ByteBuffer.class.isAssignableFrom(receiver.obj.getClass());
      }
   }

   @ExportMessage
   static final class IsIdenticalOrUndefined {
      @Specialization
      static TriState doHostObject(HostObject receiver, HostObject other) {
         return TriState.valueOf(receiver.obj == other.obj && receiver.isStaticClass() == other.isStaticClass());
      }

      @Fallback
      static TriState doOther(HostObject receiver, Object other) {
         return TriState.UNDEFINED;
      }
   }

   @ExportMessage
   static class IsInstantiable {
      @Specialization(guards = "!receiver.isClass()")
      static boolean doUnsupported(HostObject receiver) {
         return false;
      }

      @Specialization(guards = "receiver.isArrayClass()")
      static boolean doArrayCached(HostObject receiver) {
         return true;
      }

      @Specialization(guards = "receiver.isDefaultClass()")
      static boolean doObjectCached(HostObject receiver, @Cached.Shared("lookupConstructor") @Cached HostObject.LookupConstructorNode lookupConstructor) {
         return lookupConstructor.execute(receiver, receiver.asClass()) != null;
      }
   }

   @GenerateUncached
   abstract static class IsIterableNode extends Node {
      public abstract boolean execute(HostObject receiver);

      @Specialization(guards = "receiver.obj == null")
      public boolean doNull(HostObject receiver) {
         return false;
      }

      @Specialization(guards = "receiver.obj != null")
      public boolean doDefault(
         HostObject receiver, @Cached(value = "receiver.getHostClassCache().isIterableAccess()", allowUncached = true) boolean isIterableAccess
      ) {
         assert receiver.getHostClassCache().isIterableAccess() == isIterableAccess;

         return isIterableAccess && receiver.obj instanceof Iterable;
      }
   }

   @GenerateUncached
   abstract static class IsIteratorNode extends Node {
      public abstract boolean execute(HostObject receiver);

      @Specialization(guards = "receiver.obj == null")
      public boolean doNull(HostObject receiver) {
         return false;
      }

      @Specialization(guards = "receiver.obj != null")
      public boolean doDefault(
         HostObject receiver, @Cached(value = "receiver.getHostClassCache().isIteratorAccess()", allowUncached = true) boolean isIteratorAccess
      ) {
         assert receiver.getHostClassCache().isIteratorAccess() == isIteratorAccess;

         return isIteratorAccess && receiver.obj instanceof Iterator;
      }
   }

   @GenerateUncached
   abstract static class IsListNode extends Node {
      public abstract boolean execute(HostObject receiver);

      @Specialization(guards = "receiver.obj == null")
      public boolean doNull(HostObject receiver) {
         return false;
      }

      @Specialization(guards = "receiver.obj != null")
      public boolean doDefault(HostObject receiver, @Cached(value = "receiver.getHostClassCache().isListAccess()", allowUncached = true) boolean isListAccess) {
         assert receiver.getHostClassCache().isListAccess() == isListAccess;

         return isListAccess && receiver.obj instanceof List;
      }
   }

   @GenerateUncached
   abstract static class IsMapEntryNode extends Node {
      public abstract boolean execute(HostObject receiver);

      @Specialization(guards = "receiver.obj == null")
      public boolean doNull(HostObject receiver) {
         return false;
      }

      @Specialization(guards = "receiver.obj != null")
      public boolean doDefault(HostObject receiver, @Cached(value = "receiver.getHostClassCache().isMapAccess()", allowUncached = true) boolean isMapAccess) {
         assert receiver.getHostClassCache().isMapAccess() == isMapAccess;

         return isMapAccess && receiver.obj instanceof Entry;
      }
   }

   @GenerateUncached
   abstract static class IsMapNode extends Node {
      public abstract boolean execute(HostObject receiver);

      @Specialization(guards = "receiver.obj == null")
      public boolean doNull(HostObject receiver) {
         return false;
      }

      @Specialization(guards = "receiver.obj != null")
      public boolean doDefault(HostObject receiver, @Cached(value = "receiver.getHostClassCache().isMapAccess()", allowUncached = true) boolean isMapAccess) {
         assert receiver.getHostClassCache().isMapAccess() == isMapAccess;

         return isMapAccess && receiver.obj instanceof Map;
      }
   }

   @ExportMessage
   static class IsMemberInternal {
      @Specialization(
         guards = {
               "receiver.isStaticClass()", "receiver.isStaticClass() == cachedStatic", "receiver.getLookupClass() == cachedClazz", "cachedName.equals(name)"
         },
         limit = "LIMIT"
      )
      static boolean doCached(
         HostObject receiver,
         String name,
         @Cached("receiver.isStaticClass()") boolean cachedStatic,
         @Cached("receiver.getLookupClass()") Class<?> cachedClazz,
         @Cached("name") String cachedName,
         @Cached("doUncached(receiver, name)") boolean cachedInternal
      ) {
         assert cachedInternal == doUncached(receiver, name);

         return cachedInternal;
      }

      @Specialization(replaces = "doCached")
      static boolean doUncached(HostObject receiver, String name) {
         return receiver.isNull() ? false : HostInteropReflect.isInternal(receiver, receiver.getLookupClass(), name, receiver.isStaticClass());
      }
   }

   @ExportMessage
   static class IsMemberInvocable {
      @Specialization(
         guards = {
               "receiver.isStaticClass()", "receiver.isStaticClass() == cachedStatic", "receiver.getLookupClass() == cachedClazz", "cachedName.equals(name)"
         },
         limit = "LIMIT"
      )
      static boolean doCached(
         HostObject receiver,
         String name,
         @Cached("receiver.isStaticClass()") boolean cachedStatic,
         @Cached("receiver.getLookupClass()") Class<?> cachedClazz,
         @Cached("name") String cachedName,
         @Cached("doUncached(receiver, name)") boolean cachedInvokable
      ) {
         assert cachedInvokable == doUncached(receiver, name);

         return cachedInvokable;
      }

      @Specialization(replaces = "doCached")
      static boolean doUncached(HostObject receiver, String name) {
         return receiver.isNull() ? false : HostInteropReflect.isInvokable(receiver, receiver.getLookupClass(), name, receiver.isStaticClass());
      }
   }

   @ExportMessage
   static class IsMemberModifiable {
      @Specialization(
         guards = {
               "receiver.isStaticClass()", "receiver.isStaticClass() == cachedStatic", "receiver.getLookupClass() == cachedClazz", "cachedName.equals(name)"
         },
         limit = "LIMIT"
      )
      static boolean doCached(
         HostObject receiver,
         String name,
         @Cached("receiver.isStaticClass()") boolean cachedStatic,
         @Cached("receiver.getLookupClass()") Class<?> cachedClazz,
         @Cached("name") String cachedName,
         @Cached("doUncached(receiver, name)") boolean cachedModifiable
      ) {
         assert cachedModifiable == doUncached(receiver, name);

         return cachedModifiable;
      }

      @Specialization(replaces = "doCached")
      static boolean doUncached(HostObject receiver, String name) {
         return receiver.isNull() ? false : HostInteropReflect.isModifiable(receiver, receiver.getLookupClass(), name, receiver.isStaticClass());
      }
   }

   @ExportMessage
   static class IsMemberReadable {
      @Specialization(
         guards = {
               "receiver.isStaticClass()", "receiver.isStaticClass() == cachedStatic", "receiver.getLookupClass() == cachedClazz", "cachedName.equals(name)"
         },
         limit = "LIMIT"
      )
      static boolean doCached(
         HostObject receiver,
         String name,
         @Cached("receiver.isStaticClass()") boolean cachedStatic,
         @Cached("receiver.getLookupClass()") Class<?> cachedClazz,
         @Cached("name") String cachedName,
         @Cached("doUncached(receiver, name)") boolean cachedReadable
      ) {
         assert cachedReadable == doUncached(receiver, name);

         return cachedReadable;
      }

      @Specialization(replaces = "doCached")
      static boolean doUncached(HostObject receiver, String name) {
         return receiver.isNull()
            ? false
            : HostInteropReflect.isReadable(receiver, receiver.getLookupClass(), name, receiver.isStaticClass(), receiver.isClass());
      }
   }

   @ExportLibrary(InteropLibrary.class)
   static final class KeysArray implements TruffleObject {
      @CompilerDirectives.CompilationFinal(dimensions = 1)
      private final String[] keys;

      KeysArray(String[] keys) {
         this.keys = keys;
      }

      @ExportMessage
      boolean hasArrayElements() {
         return true;
      }

      @ExportMessage
      long getArraySize() {
         return this.keys.length;
      }

      @ExportMessage
      boolean isArrayElementReadable(long idx) {
         return 0L <= idx && idx < this.keys.length;
      }

      @ExportMessage
      String readArrayElement(long idx, @Cached BranchProfile error) throws InvalidArrayIndexException {
         if (!this.isArrayElementReadable(idx)) {
            error.enter();
            throw InvalidArrayIndexException.create(idx);
         } else {
            return this.keys[(int)idx];
         }
      }
   }

   @GenerateUncached
   abstract static class LookupConstructorNode extends Node {
      static final int LIMIT = 3;

      public abstract HostMethodDesc execute(HostObject receiver, Class<?> clazz);

      @Specialization(guards = "clazz == cachedClazz", limit = "LIMIT")
      HostMethodDesc doCached(
         HostObject receiver, Class<?> clazz, @Cached("clazz") Class<?> cachedClazz, @Cached("doUncached(receiver, clazz)") HostMethodDesc cachedMethod
      ) {
         assert cachedMethod == this.doUncached(receiver, clazz);

         return cachedMethod;
      }

      @Specialization(replaces = "doCached")
      @CompilerDirectives.TruffleBoundary
      HostMethodDesc doUncached(HostObject receiver, Class<?> clazz) {
         return HostClassDesc.forClass(receiver.context, clazz).lookupConstructor();
      }
   }

   @GenerateUncached
   abstract static class LookupFieldNode extends Node {
      static final int LIMIT = 3;

      public abstract HostFieldDesc execute(HostObject receiver, Class<?> clazz, String name, boolean onlyStatic);

      @Specialization(guards = {"onlyStatic == cachedStatic", "clazz == cachedClazz", "cachedName.equals(name)"}, limit = "LIMIT")
      HostFieldDesc doCached(
         HostObject receiver,
         Class<?> clazz,
         String name,
         boolean onlyStatic,
         @Cached("onlyStatic") boolean cachedStatic,
         @Cached("clazz") Class<?> cachedClazz,
         @Cached("name") String cachedName,
         @Cached("doUncached(receiver, clazz, name, onlyStatic)") HostFieldDesc cachedField
      ) {
         assert cachedField == this.doUncached(receiver, clazz, name, onlyStatic);

         return cachedField;
      }

      @Specialization(replaces = "doCached")
      @CompilerDirectives.TruffleBoundary
      HostFieldDesc doUncached(HostObject receiver, Class<?> clazz, String name, boolean onlyStatic) {
         return HostInteropReflect.findField(receiver.context, clazz, name, onlyStatic);
      }
   }

   @GenerateUncached
   abstract static class LookupFunctionalMethodNode extends Node {
      static final int LIMIT = 3;

      public abstract HostMethodDesc execute(HostObject object, Class<?> clazz);

      @Specialization(guards = "clazz == cachedClazz", limit = "LIMIT")
      HostMethodDesc doCached(
         HostObject object, Class<?> clazz, @Cached("clazz") Class<?> cachedClazz, @Cached("doUncached(object, clazz)") HostMethodDesc cachedMethod
      ) {
         assert cachedMethod == doUncached(object, clazz);

         return cachedMethod;
      }

      @Specialization(replaces = "doCached")
      @CompilerDirectives.TruffleBoundary
      static HostMethodDesc doUncached(HostObject object, Class<?> clazz) {
         return HostClassDesc.forClass(object.context, clazz).getFunctionalMethod();
      }
   }

   @GenerateUncached
   abstract static class LookupInnerClassNode extends Node {
      static final int LIMIT = 3;

      public abstract Class<?> execute(Class<?> outerclass, String name);

      @Specialization(guards = {"clazz == cachedClazz", "cachedName.equals(name)"}, limit = "LIMIT")
      Class<?> doCached(
         Class<?> clazz,
         String name,
         @Cached("clazz") Class<?> cachedClazz,
         @Cached("name") String cachedName,
         @Cached("doUncached(clazz, name)") Class<?> cachedInnerClass
      ) {
         assert cachedInnerClass == this.doUncached(clazz, name);

         return cachedInnerClass;
      }

      @Specialization(replaces = "doCached")
      @CompilerDirectives.TruffleBoundary
      Class<?> doUncached(Class<?> clazz, String name) {
         return HostInteropReflect.findInnerClass(clazz, name);
      }
   }

   @GenerateUncached
   abstract static class LookupMethodNode extends Node {
      static final int LIMIT = 3;

      public abstract HostMethodDesc execute(HostObject receiver, Class<?> clazz, String name, boolean onlyStatic);

      @Specialization(guards = {"onlyStatic == cachedStatic", "clazz == cachedClazz", "cachedName.equals(name)"}, limit = "LIMIT")
      HostMethodDesc doCached(
         HostObject receiver,
         Class<?> clazz,
         String name,
         boolean onlyStatic,
         @Cached("onlyStatic") boolean cachedStatic,
         @Cached("clazz") Class<?> cachedClazz,
         @Cached("name") String cachedName,
         @Cached("doUncached(receiver, clazz, name, onlyStatic)") HostMethodDesc cachedMethod
      ) {
         assert cachedMethod == this.doUncached(receiver, clazz, name, onlyStatic);

         return cachedMethod;
      }

      @Specialization(replaces = "doCached")
      @CompilerDirectives.TruffleBoundary
      HostMethodDesc doUncached(HostObject receiver, Class<?> clazz, String name, boolean onlyStatic) {
         return HostInteropReflect.findMethod(receiver.context, clazz, name, onlyStatic);
      }
   }

   @ExportMessage
   abstract static class ReadArrayElement {
      @Specialization(guards = "isArray.execute(receiver)", limit = "1")
      protected static Object doArray(
         HostObject receiver,
         long index,
         @Cached HostObject.ArrayGet arrayGet,
         @Cached.Shared("isArray") @Cached HostObject.IsArrayNode isArray,
         @Cached.Shared("toGuest") @Cached HostContext.ToGuestValueNode toGuest,
         @Cached.Shared("error") @Cached BranchProfile error
      ) throws InvalidArrayIndexException {
         if (index >= 0L && 2147483647L >= index) {
            Object obj = receiver.obj;
            Object val = null;

            try {
               val = arrayGet.execute(obj, (int)index);
            } catch (ArrayIndexOutOfBoundsException var10) {
               error.enter();
               throw InvalidArrayIndexException.create(index);
            }

            return toGuest.execute(receiver.context, val);
         } else {
            error.enter();
            throw InvalidArrayIndexException.create(index);
         }
      }

      @CompilerDirectives.TruffleBoundary
      @Specialization(guards = "isList.execute(receiver)", limit = "1")
      protected static Object doList(
         HostObject receiver,
         long index,
         @Cached.Shared("isList") @Cached HostObject.IsListNode isList,
         @Cached.Shared("toGuest") @Cached HostContext.ToGuestValueNode toGuest,
         @Cached.Shared("error") @Cached BranchProfile error
      ) throws InvalidArrayIndexException {
         if (index >= 0L && 2147483647L >= index) {
            Object hostValue;
            try {
               hostValue = HostObject.GuestToHostCalls.readListElement(receiver, index);
            } catch (IndexOutOfBoundsException var8) {
               error.enter();
               throw InvalidArrayIndexException.create(index);
            } catch (Throwable var9) {
               error.enter();
               throw receiver.context.hostToGuestException(var9);
            }

            return toGuest.execute(receiver.context, hostValue);
         } else {
            error.enter();
            throw InvalidArrayIndexException.create(index);
         }
      }

      @Specialization(guards = "isMapEntry.execute(receiver)", limit = "1")
      protected static Object doMapEntry(
         HostObject receiver,
         long index,
         @Cached.Shared("isMapEntry") @Cached HostObject.IsMapEntryNode isMapEntry,
         @Cached.Shared("toGuest") @Cached HostContext.ToGuestValueNode toGuest,
         @Cached.Shared("error") @Cached BranchProfile error
      ) throws InvalidArrayIndexException {
         Object hostResult;
         if (index == 0L) {
            try {
               hostResult = HostObject.GuestToHostCalls.getMapEntryKey(receiver);
            } catch (Throwable var9) {
               error.enter();
               throw receiver.context.hostToGuestException(var9);
            }
         } else {
            if (index != 1L) {
               error.enter();
               throw InvalidArrayIndexException.create(index);
            }

            try {
               hostResult = HostObject.GuestToHostCalls.getMapEntryValue(receiver);
            } catch (Throwable var8) {
               error.enter();
               throw receiver.context.hostToGuestException(var8);
            }
         }

         return toGuest.execute(receiver.context, hostResult);
      }

      @Specialization(guards = {"!isArray.execute(receiver)", "!isList.execute(receiver)", "!isMapEntry.execute(receiver)"}, limit = "1")
      protected static Object doNotArrayOrList(
         HostObject receiver,
         long index,
         @Cached.Shared("isArray") @Cached HostObject.IsArrayNode isArray,
         @Cached.Shared("isList") @Cached HostObject.IsListNode isList,
         @Cached.Shared("isMapEntry") @Cached HostObject.IsMapEntryNode isMapEntry
      ) throws UnsupportedMessageException {
         throw UnsupportedMessageException.create();
      }
   }

   @GenerateUncached
   abstract static class ReadFieldNode extends Node {
      static final int LIMIT = 3;

      public abstract Object execute(HostFieldDesc field, HostObject object);

      @Specialization(guards = "field == cachedField", limit = "LIMIT")
      static Object doCached(HostFieldDesc field, HostObject object, @Cached("field") HostFieldDesc cachedField, @Cached HostContext.ToGuestValueNode toGuest) {
         Object val = cachedField.get(object.obj);
         return toGuest.execute(object.context, val);
      }

      @Specialization(replaces = "doCached")
      @CompilerDirectives.TruffleBoundary
      static Object doUncached(HostFieldDesc field, HostObject object, @Cached HostContext.ToGuestValueNode toGuest) {
         Object val = field.get(object.obj);
         return toGuest.execute(object.context, val);
      }
   }

   @ExportMessage
   abstract static class ReadHashValue {
      private static final Object UNDEFINED = new Object();

      @Specialization(guards = "isMap.execute(receiver)", limit = "1")
      protected static Object doMap(
         HostObject receiver,
         Object key,
         @Cached.Shared("isMap") @Cached HostObject.IsMapNode isMap,
         @Cached.Shared("toHost") @Cached HostToTypeNode toHost,
         @Cached.Shared("toGuest") @Cached HostContext.ToGuestValueNode toGuest,
         @Cached.Shared("error") @Cached BranchProfile error
      ) throws UnknownKeyException {
         Object hostKey;
         try {
            hostKey = toHost.execute(receiver.context, key, Object.class, null, true);
         } catch (RuntimeException var10) {
            error.enter();
            RuntimeException ee = HostObject.unboxEngineException(receiver, var10);
            if (ee != null) {
               throw UnknownKeyException.create(key);
            }

            throw var10;
         }

         Object hostResult;
         try {
            hostResult = HostObject.GuestToHostCalls.getMapValue(receiver, hostKey, UNDEFINED);
         } catch (Throwable var9) {
            error.enter();
            throw receiver.context.hostToGuestException(var9);
         }

         if (hostResult == UNDEFINED) {
            error.enter();
            throw UnknownKeyException.create(key);
         } else {
            return toGuest.execute(receiver.context, hostResult);
         }
      }

      @Specialization(guards = "!isMap.execute(receiver)", limit = "1")
      protected static Object doNotMap(HostObject receiver, Object key, @Cached.Shared("isMap") @Cached HostObject.IsMapNode isMap) throws UnsupportedMessageException {
         throw UnsupportedMessageException.create();
      }
   }

   @ExportMessage
   static class RemoveArrayElement {
      @Specialization(guards = "isList.execute(receiver)", limit = "1")
      static void doList(
         HostObject receiver, long index, @Cached.Shared("isList") @Cached HostObject.IsListNode isList, @Cached.Shared("error") @Cached BranchProfile error
      ) throws InvalidArrayIndexException {
         if (index >= 0L && 2147483647L >= index) {
            try {
               HostObject.GuestToHostCalls.removeListElement(receiver, index);
            } catch (IndexOutOfBoundsException var6) {
               error.enter();
               throw InvalidArrayIndexException.create(index);
            } catch (Throwable var7) {
               error.enter();
               throw receiver.context.hostToGuestException(var7);
            }
         } else {
            error.enter();
            throw InvalidArrayIndexException.create(index);
         }
      }

      @Specialization(guards = "!isList.execute(receiver)", limit = "1")
      static void doOther(HostObject receiver, long index, @Cached.Shared("isList") @Cached HostObject.IsListNode isList) throws UnsupportedMessageException {
         throw UnsupportedMessageException.create();
      }
   }

   @ExportMessage
   abstract static class RemoveHashEntry {
      @Specialization(guards = "isMap.execute(receiver)", limit = "1")
      protected static void doMap(
         HostObject receiver,
         Object key,
         @Cached.Shared("isMap") @Cached HostObject.IsMapNode isMap,
         @Cached.Shared("toHost") @Cached HostToTypeNode toHost,
         @Cached.Shared("error") @Cached BranchProfile error
      ) throws UnknownKeyException {
         Object hostKey;
         try {
            hostKey = toHost.execute(receiver.context, key, Object.class, null, true);
         } catch (RuntimeException var9) {
            error.enter();
            RuntimeException ee = HostObject.unboxEngineException(receiver, var9);
            if (ee != null) {
               throw UnknownKeyException.create(key);
            }

            throw var9;
         }

         boolean removed;
         try {
            removed = HostObject.GuestToHostCalls.removeMapValue(receiver, hostKey);
         } catch (Throwable var8) {
            error.enter();
            throw receiver.context.hostToGuestException(var8);
         }

         if (!removed) {
            error.enter();
            throw UnknownKeyException.create(key);
         }
      }

      @Specialization(guards = "!isMap.execute(receiver)", limit = "1")
      protected static void doNotMap(HostObject receiver, Object key, @Cached.Shared("isMap") @Cached HostObject.IsMapNode isMap) throws UnsupportedMessageException {
         throw UnsupportedMessageException.create();
      }
   }

   @ExportLibrary(InteropLibrary.class)
   static final class TypesArray implements TruffleObject {
      @CompilerDirectives.CompilationFinal(dimensions = 1)
      private final HostObject[] types;

      TypesArray(HostObject[] types) {
         this.types = types;
      }

      @ExportMessage
      boolean hasArrayElements() {
         return true;
      }

      @ExportMessage
      long getArraySize() {
         return this.types.length;
      }

      @ExportMessage
      boolean isArrayElementReadable(long idx) {
         return 0L <= idx && idx < this.types.length;
      }

      @ExportMessage
      Object readArrayElement(long idx, @Cached BranchProfile error) throws InvalidArrayIndexException {
         if (!this.isArrayElementReadable(idx)) {
            error.enter();
            throw InvalidArrayIndexException.create(idx);
         } else {
            return this.types[(int)idx];
         }
      }
   }

   @ExportMessage
   static class WriteArrayElement {
      @Specialization(guards = "isArray.execute(receiver)", limit = "1")
      static void doArray(
         HostObject receiver,
         long index,
         Object value,
         @Cached.Shared("toHost") @Cached HostToTypeNode toHostNode,
         @Cached.Shared("isArray") @Cached HostObject.IsArrayNode isArray,
         @Cached HostObject.ArraySet arraySet,
         @Cached.Shared("error") @Cached BranchProfile error
      ) throws InvalidArrayIndexException, UnsupportedTypeException {
         if (index >= 0L && 2147483647L >= index) {
            Object obj = receiver.obj;

            Object javaValue;
            try {
               javaValue = toHostNode.execute(receiver.context, value, obj.getClass().getComponentType(), null, true);
            } catch (RuntimeException var13) {
               error.enter();
               RuntimeException ee = HostObject.unboxEngineException(receiver, var13);
               if (ee != null) {
                  throw UnsupportedTypeException.create(new Object[]{value}, HostObject.getMessage(ee));
               }

               throw var13;
            }

            try {
               arraySet.execute(obj, (int)index, javaValue);
            } catch (ArrayIndexOutOfBoundsException var12) {
               error.enter();
               throw InvalidArrayIndexException.create(index);
            }
         } else {
            error.enter();
            throw InvalidArrayIndexException.create(index);
         }
      }

      @Specialization(guards = "isList.execute(receiver)", limit = "1")
      static void doList(
         HostObject receiver,
         long index,
         Object value,
         @Cached.Shared("isList") @Cached HostObject.IsListNode isList,
         @Cached.Shared("toHost") @Cached HostToTypeNode toHostNode,
         @Cached.Shared("error") @Cached BranchProfile error
      ) throws InvalidArrayIndexException, UnsupportedTypeException {
         if (index >= 0L && 2147483647L >= index) {
            Object javaValue;
            try {
               javaValue = toHostNode.execute(receiver.context, value, Object.class, null, true);
            } catch (RuntimeException var12) {
               error.enter();
               RuntimeException ee = HostObject.unboxEngineException(receiver, var12);
               if (ee != null) {
                  throw UnsupportedTypeException.create(new Object[]{value}, HostObject.getMessage(ee));
               }

               throw var12;
            }

            try {
               HostObject.GuestToHostCalls.setListElement(receiver, index, javaValue);
            } catch (IndexOutOfBoundsException var10) {
               error.enter();
               throw InvalidArrayIndexException.create(index);
            } catch (Throwable var11) {
               error.enter();
               throw receiver.context.hostToGuestException(var11);
            }
         } else {
            error.enter();
            throw InvalidArrayIndexException.create(index);
         }
      }

      @Specialization(guards = "isMapEntry.execute(receiver)", limit = "1")
      static void doMapEntry(
         HostObject receiver,
         long index,
         Object value,
         @Cached.Shared("isMapEntry") @Cached HostObject.IsMapEntryNode isMapEntry,
         @Cached.Shared("toHost") @Cached HostToTypeNode toHostNode,
         @Cached.Shared("error") @Cached BranchProfile error
      ) throws InvalidArrayIndexException, UnsupportedTypeException {
         if (index == 1L) {
            Object hostValue;
            try {
               hostValue = toHostNode.execute(receiver.context, value, Object.class, null, true);
            } catch (RuntimeException var11) {
               error.enter();
               RuntimeException ee = HostObject.unboxEngineException(receiver, var11);
               if (ee != null) {
                  throw UnsupportedTypeException.create(new Object[]{value}, HostObject.getMessage(ee));
               }

               throw var11;
            }

            try {
               HostObject.GuestToHostCalls.setMapEntryValue(receiver, hostValue);
            } catch (Throwable var10) {
               error.enter();
               throw receiver.context.hostToGuestException(var10);
            }
         } else {
            throw InvalidArrayIndexException.create(index);
         }
      }

      @Specialization(guards = {"!isList.execute(receiver)", "!isArray.execute(receiver)", "!isMapEntry.execute(receiver)"}, limit = "1")
      static void doNotArrayOrList(
         HostObject receiver,
         long index,
         Object value,
         @Cached.Shared("isList") @Cached HostObject.IsListNode isList,
         @Cached.Shared("isArray") @Cached HostObject.IsArrayNode isArray,
         @Cached.Shared("isMapEntry") @Cached HostObject.IsMapEntryNode isMapEntry
      ) throws UnsupportedMessageException {
         throw UnsupportedMessageException.create();
      }
   }

   @GenerateUncached
   abstract static class WriteFieldNode extends Node {
      static final int LIMIT = 3;

      public abstract void execute(HostFieldDesc field, HostObject object, Object value) throws UnsupportedTypeException, UnknownIdentifierException;

      @Specialization(guards = "field == cachedField", limit = "LIMIT")
      static void doCached(
         HostFieldDesc field,
         HostObject object,
         Object rawValue,
         @Cached("field") HostFieldDesc cachedField,
         @Cached HostToTypeNode toHost,
         @Cached BranchProfile error
      ) throws UnsupportedTypeException, UnknownIdentifierException {
         if (field.isFinal()) {
            error.enter();
            throw UnknownIdentifierException.create(field.getName());
         } else {
            try {
               Object value = toHost.execute(object.context, rawValue, cachedField.getType(), cachedField.getGenericType(), true);
               cachedField.set(object.obj, value);
            } catch (RuntimeException var8) {
               error.enter();
               RuntimeException ee = HostObject.unboxEngineException(object, var8);
               if (ee != null) {
                  throw HostInteropErrors.unsupportedTypeException(rawValue, ee);
               } else {
                  throw var8;
               }
            }
         }
      }

      @Specialization(replaces = "doCached")
      @CompilerDirectives.TruffleBoundary
      static void doUncached(HostFieldDesc field, HostObject object, Object rawValue, @Cached HostToTypeNode toHost) throws UnsupportedTypeException, UnknownIdentifierException {
         if (field.isFinal()) {
            throw UnknownIdentifierException.create(field.getName());
         } else {
            try {
               Object val = toHost.execute(object.context, rawValue, field.getType(), field.getGenericType(), true);
               field.set(object.obj, val);
            } catch (RuntimeException var6) {
               RuntimeException ee = HostObject.unboxEngineException(object, var6);
               if (ee != null) {
                  throw HostInteropErrors.unsupportedTypeException(rawValue, ee);
               } else {
                  throw var6;
               }
            }
         }
      }
   }

   @ExportMessage
   abstract static class WriteHashEntry {
      @Specialization(guards = "isMap.execute(receiver)", limit = "1")
      protected static void doMap(
         HostObject receiver,
         Object key,
         Object value,
         @Cached.Shared("isMap") @Cached HostObject.IsMapNode isMap,
         @Cached.Shared("toHost") @Cached HostToTypeNode toHost,
         @Cached.Shared("error") @Cached BranchProfile error
      ) throws UnsupportedTypeException {
         Object hostKey;
         try {
            hostKey = toHost.execute(receiver.context, key, Object.class, null, true);
         } catch (RuntimeException var12) {
            error.enter();
            RuntimeException ee = HostObject.unboxEngineException(receiver, var12);
            if (ee != null) {
               throw UnsupportedTypeException.create(new Object[]{key}, HostObject.getMessage(ee));
            }

            throw var12;
         }

         Object hostValue;
         try {
            hostValue = toHost.execute(receiver.context, value, Object.class, null, true);
         } catch (RuntimeException var11) {
            error.enter();
            RuntimeException ee = HostObject.unboxEngineException(receiver, var11);
            if (ee != null) {
               throw UnsupportedTypeException.create(new Object[]{value}, HostObject.getMessage(ee));
            }

            throw var11;
         }

         try {
            HostObject.GuestToHostCalls.putMapValue(receiver, hostKey, hostValue);
         } catch (Throwable var10) {
            error.enter();
            throw receiver.context.hostToGuestException(var10);
         }
      }

      @Specialization(guards = "!isMap.execute(receiver)", limit = "1")
      protected static void doNotMap(HostObject receiver, Object key, Object value, @Cached.Shared("isMap") @Cached HostObject.IsMapNode isMap) throws UnsupportedMessageException {
         throw UnsupportedMessageException.create();
      }
   }
}
