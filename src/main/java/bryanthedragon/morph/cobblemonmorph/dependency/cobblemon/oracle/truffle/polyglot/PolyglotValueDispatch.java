package com.oracle.truffle.polyglot;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.interop.ArityException;
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
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.strings.TruffleString;
import java.nio.ByteOrder;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.AbstractSet;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.PolyglotException;
import org.graalvm.polyglot.SourceSection;
import org.graalvm.polyglot.TypeLiteral;
import org.graalvm.polyglot.Value;
import org.graalvm.polyglot.impl.AbstractPolyglotImpl;

abstract class PolyglotValueDispatch extends AbstractPolyglotImpl.AbstractValueDispatch {
   private static final String TRUNCATION_SUFFIX = "...";
   private static final String UNKNOWN = "Unknown";
   static final InteropLibrary UNCACHED_INTEROP = InteropLibrary.getFactory().getUncached();
   final PolyglotImpl impl;
   final PolyglotLanguageInstance languageInstance;
   private static final int CHARACTER_LIMIT = 140;
   private static final InteropLibrary INTEROP = InteropLibrary.getFactory().getUncached();

   PolyglotValueDispatch(PolyglotImpl impl, PolyglotLanguageInstance languageInstance) {
      super(impl);
      this.impl = impl;
      this.languageInstance = languageInstance;
   }

   @Override
   public final Context getContext(Object context) {
      return context == null ? null : ((PolyglotLanguageContext)context).context.api;
   }

   static <T extends Throwable> PolyglotException guestToHostException(PolyglotLanguageContext languageContext, T e, boolean entered) {
      throw PolyglotImpl.guestToHostException(languageContext, e, entered);
   }

   @Override
   public Value getArrayElement(Object languageContext, Object receiver, long index) {
      PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;
      Object prev = hostEnter(context);

      Value e;
      try {
         e = getArrayElementUnsupported(context, receiver);
      } catch (Throwable var11) {
         throw guestToHostException(context, var11, true);
      } finally {
         hostLeave(context, prev);
      }

      return e;
   }

   @CompilerDirectives.TruffleBoundary
   static Value getArrayElementUnsupported(PolyglotLanguageContext context, Object receiver) {
      throw unsupported(context, receiver, "getArrayElement(long)", "hasArrayElements()");
   }

   @Override
   public void setArrayElement(Object languageContext, Object receiver, long index, Object value) {
      PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;
      Object prev = hostEnter(context);

      try {
         setArrayElementUnsupported(context, receiver);
      } catch (Throwable var12) {
         throw guestToHostException(context, var12, true);
      } finally {
         hostLeave(context, prev);
      }
   }

   @CompilerDirectives.TruffleBoundary
   static void setArrayElementUnsupported(PolyglotLanguageContext context, Object receiver) {
      throw unsupported(context, receiver, "setArrayElement(long, Object)", "hasArrayElements()");
   }

   @Override
   public boolean removeArrayElement(Object languageContext, Object receiver, long index) {
      PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;
      Object prev = hostEnter(context);

      try {
         throw removeArrayElementUnsupported(context, receiver);
      } catch (Throwable var11) {
         throw guestToHostException(context, var11, true);
      } finally {
         hostLeave(context, prev);
      }
   }

   @CompilerDirectives.TruffleBoundary
   static RuntimeException removeArrayElementUnsupported(PolyglotLanguageContext context, Object receiver) {
      throw unsupported(context, receiver, "removeArrayElement(long, Object)", null);
   }

   @Override
   public long getArraySize(Object languageContext, Object receiver) {
      PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;
      Object prev = hostEnter(context);

      long e;
      try {
         e = getArraySizeUnsupported(context, receiver);
      } catch (Throwable var10) {
         throw guestToHostException(context, var10, true);
      } finally {
         hostLeave(context, prev);
      }

      return e;
   }

   @CompilerDirectives.TruffleBoundary
   static long getArraySizeUnsupported(PolyglotLanguageContext context, Object receiver) {
      throw unsupported(context, receiver, "getArraySize()", "hasArrayElements()");
   }

   @Override
   public boolean isBufferWritable(Object languageContext, Object receiver) throws UnsupportedOperationException {
      PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;
      Object prev = hostEnter(context);

      try {
         throw isBufferWritableUnsupported(context, receiver);
      } catch (Throwable var9) {
         throw guestToHostException(context, var9, true);
      } finally {
         hostLeave(context, prev);
      }
   }

   @CompilerDirectives.TruffleBoundary
   static RuntimeException isBufferWritableUnsupported(PolyglotLanguageContext context, Object receiver) {
      return unsupported(context, receiver, "isBufferWritable()", "hasBufferElements()");
   }

   @Override
   public long getBufferSize(Object languageContext, Object receiver) throws UnsupportedOperationException {
      PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;
      Object prev = hostEnter(context);

      try {
         throw getBufferSizeUnsupported(context, receiver);
      } catch (Throwable var9) {
         throw guestToHostException(context, var9, true);
      } finally {
         hostLeave(context, prev);
      }
   }

   @CompilerDirectives.TruffleBoundary
   static RuntimeException getBufferSizeUnsupported(PolyglotLanguageContext context, Object receiver) {
      return unsupported(context, receiver, "getBufferSize()", "hasBufferElements()");
   }

   @Override
   public byte readBufferByte(Object languageContext, Object receiver, long byteOffset) throws UnsupportedOperationException, IndexOutOfBoundsException {
      PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;
      Object prev = hostEnter(context);

      try {
         throw readBufferByteUnsupported(context, receiver);
      } catch (Throwable var11) {
         throw guestToHostException(context, var11, true);
      } finally {
         hostLeave(context, prev);
      }
   }

   @CompilerDirectives.TruffleBoundary
   static RuntimeException readBufferByteUnsupported(PolyglotLanguageContext context, Object receiver) {
      return unsupported(context, receiver, "readBufferByte()", "hasBufferElements()");
   }

   @Override
   public void writeBufferByte(Object languageContext, Object receiver, long byteOffset, byte value) throws UnsupportedOperationException, IndexOutOfBoundsException {
      PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;
      Object prev = hostEnter(context);

      try {
         throw writeBufferByteUnsupported(context, receiver);
      } catch (Throwable var12) {
         throw guestToHostException(context, var12, true);
      } finally {
         hostLeave(context, prev);
      }
   }

   @CompilerDirectives.TruffleBoundary
   static RuntimeException writeBufferByteUnsupported(PolyglotLanguageContext context, Object receiver) {
      return unsupported(context, receiver, "writeBufferByte()", "hasBufferElements()");
   }

   @Override
   public short readBufferShort(Object languageContext, Object receiver, ByteOrder order, long byteOffset) throws UnsupportedOperationException, IndexOutOfBoundsException {
      PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;
      Object prev = hostEnter(context);

      try {
         throw readBufferShortUnsupported(context, receiver);
      } catch (Throwable var12) {
         throw guestToHostException(context, var12, true);
      } finally {
         hostLeave(context, prev);
      }
   }

   @CompilerDirectives.TruffleBoundary
   static RuntimeException readBufferShortUnsupported(PolyglotLanguageContext context, Object receiver) {
      return unsupported(context, receiver, "readBufferShort()", "hasBufferElements()");
   }

   @Override
   public void writeBufferShort(Object languageContext, Object receiver, ByteOrder order, long byteOffset, short value) throws UnsupportedOperationException, IndexOutOfBoundsException {
      PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;
      Object prev = hostEnter(context);

      try {
         throw writeBufferShortUnsupported(context, receiver);
      } catch (Throwable var13) {
         throw guestToHostException(context, var13, true);
      } finally {
         hostLeave(context, prev);
      }
   }

   @CompilerDirectives.TruffleBoundary
   static RuntimeException writeBufferShortUnsupported(PolyglotLanguageContext context, Object receiver) {
      return unsupported(context, receiver, "writeBufferShort()", "hasBufferElements()");
   }

   @Override
   public int readBufferInt(Object languageContext, Object receiver, ByteOrder order, long byteOffset) throws UnsupportedOperationException, IndexOutOfBoundsException {
      PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;
      Object prev = hostEnter(context);

      try {
         throw readBufferIntUnsupported(context, receiver);
      } catch (Throwable var12) {
         throw guestToHostException(context, var12, true);
      } finally {
         hostLeave(context, prev);
      }
   }

   @CompilerDirectives.TruffleBoundary
   static RuntimeException readBufferIntUnsupported(PolyglotLanguageContext context, Object receiver) {
      return unsupported(context, receiver, "readBufferInt()", "hasBufferElements()");
   }

   @Override
   public void writeBufferInt(Object languageContext, Object receiver, ByteOrder order, long byteOffset, int value) throws UnsupportedOperationException, IndexOutOfBoundsException {
      PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;
      Object prev = hostEnter(context);

      try {
         throw writeBufferIntUnsupported(context, receiver);
      } catch (Throwable var13) {
         throw guestToHostException(context, var13, true);
      } finally {
         hostLeave(context, prev);
      }
   }

   @CompilerDirectives.TruffleBoundary
   static RuntimeException writeBufferIntUnsupported(PolyglotLanguageContext context, Object receiver) {
      return unsupported(context, receiver, "writeBufferInt()", "hasBufferElements()");
   }

   @Override
   public long readBufferLong(Object languageContext, Object receiver, ByteOrder order, long byteOffset) throws UnsupportedOperationException, IndexOutOfBoundsException {
      PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;
      Object prev = hostEnter(context);

      try {
         throw readBufferLongUnsupported(context, receiver);
      } catch (Throwable var12) {
         throw guestToHostException(context, var12, true);
      } finally {
         hostLeave(context, prev);
      }
   }

   @CompilerDirectives.TruffleBoundary
   static RuntimeException readBufferLongUnsupported(PolyglotLanguageContext context, Object receiver) {
      return unsupported(context, receiver, "readBufferLong()", "hasBufferElements()");
   }

   @Override
   public void writeBufferLong(Object languageContext, Object receiver, ByteOrder order, long byteOffset, long value) throws UnsupportedOperationException, IndexOutOfBoundsException {
      PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;
      Object prev = hostEnter(context);

      try {
         throw writeBufferLongUnsupported(context, receiver);
      } catch (Throwable var14) {
         throw guestToHostException(context, var14, true);
      } finally {
         hostLeave(context, prev);
      }
   }

   @CompilerDirectives.TruffleBoundary
   static RuntimeException writeBufferLongUnsupported(PolyglotLanguageContext context, Object receiver) {
      return unsupported(context, receiver, "writeBufferLong()", "hasBufferElements()");
   }

   @Override
   public float readBufferFloat(Object languageContext, Object receiver, ByteOrder order, long byteOffset) throws UnsupportedOperationException, IndexOutOfBoundsException {
      PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;
      Object prev = hostEnter(context);

      try {
         throw readBufferFloatUnsupported(context, receiver);
      } catch (Throwable var12) {
         throw guestToHostException(context, var12, true);
      } finally {
         hostLeave(context, prev);
      }
   }

   @CompilerDirectives.TruffleBoundary
   static RuntimeException readBufferFloatUnsupported(PolyglotLanguageContext context, Object receiver) {
      return unsupported(context, receiver, "readBufferFloat()", "hasBufferElements()");
   }

   @Override
   public void writeBufferFloat(Object languageContext, Object receiver, ByteOrder order, long byteOffset, float value) throws UnsupportedOperationException, IndexOutOfBoundsException {
      PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;
      Object prev = hostEnter(context);

      try {
         throw writeBufferFloatUnsupported(context, receiver);
      } catch (Throwable var13) {
         throw guestToHostException(context, var13, true);
      } finally {
         hostLeave(context, prev);
      }
   }

   @CompilerDirectives.TruffleBoundary
   static RuntimeException writeBufferFloatUnsupported(PolyglotLanguageContext context, Object receiver) {
      return unsupported(context, receiver, "writeBufferFloat()", "hasBufferElements()");
   }

   @Override
   public double readBufferDouble(Object languageContext, Object receiver, ByteOrder order, long byteOffset) throws UnsupportedOperationException, IndexOutOfBoundsException {
      PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;
      Object prev = hostEnter(context);

      try {
         throw readBufferDoubleUnsupported(context, receiver);
      } catch (Throwable var12) {
         throw guestToHostException(context, var12, true);
      } finally {
         hostLeave(context, prev);
      }
   }

   @CompilerDirectives.TruffleBoundary
   static RuntimeException readBufferDoubleUnsupported(PolyglotLanguageContext context, Object receiver) {
      return unsupported(context, receiver, "readBufferDouble()", "hasBufferElements()");
   }

   @Override
   public void writeBufferDouble(Object languageContext, Object receiver, ByteOrder order, long byteOffset, double value) throws UnsupportedOperationException, IndexOutOfBoundsException {
      PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;
      Object prev = hostEnter(context);

      try {
         throw writeBufferDoubleUnsupported(context, receiver);
      } catch (Throwable var14) {
         throw guestToHostException(context, var14, true);
      } finally {
         hostLeave(context, prev);
      }
   }

   @CompilerDirectives.TruffleBoundary
   static RuntimeException writeBufferDoubleUnsupported(PolyglotLanguageContext context, Object receiver) {
      return unsupported(context, receiver, "writeBufferDouble()", "hasBufferElements()");
   }

   @CompilerDirectives.TruffleBoundary
   protected static RuntimeException invalidBufferIndex(PolyglotLanguageContext context, Object receiver, long byteOffset, long size) {
      String message = String.format("Invalid buffer access of length %d at byte offset %d for buffer %s.", size, byteOffset, getValueInfo(context, receiver));
      throw PolyglotEngineException.bufferIndexOutOfBounds(message);
   }

   @Override
   public Value getMember(Object languageContext, Object receiver, String key) {
      PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;
      Object prev = hostEnter(context);

      Value e;
      try {
         e = getMemberUnsupported(context, receiver, key);
      } catch (Throwable var10) {
         throw guestToHostException(context, var10, true);
      } finally {
         hostLeave(context, prev);
      }

      return e;
   }

   @CompilerDirectives.TruffleBoundary
   static Value getMemberUnsupported(PolyglotLanguageContext context, Object receiver, String key) {
      throw unsupported(context, receiver, "getMember(String)", "hasMembers()");
   }

   @Override
   public void putMember(Object languageContext, Object receiver, String key, Object member) {
      PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;
      Object prev = hostEnter(context);

      try {
         putMemberUnsupported(context, receiver);
      } catch (Throwable var11) {
         throw guestToHostException(context, var11, true);
      } finally {
         hostLeave(context, prev);
      }
   }

   @CompilerDirectives.TruffleBoundary
   static RuntimeException putMemberUnsupported(PolyglotLanguageContext context, Object receiver) {
      throw unsupported(context, receiver, "putMember(String, Object)", "hasMembers()");
   }

   @Override
   public boolean removeMember(Object languageContext, Object receiver, String key) {
      PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;
      Object prev = hostEnter(context);

      try {
         throw removeMemberUnsupported(context, receiver);
      } catch (Throwable var10) {
         throw guestToHostException(context, var10, true);
      } finally {
         hostLeave(context, prev);
      }
   }

   @CompilerDirectives.TruffleBoundary
   static RuntimeException removeMemberUnsupported(PolyglotLanguageContext context, Object receiver) {
      throw unsupported(context, receiver, "removeMember(String, Object)", null);
   }

   @Override
   public Value execute(Object languageContext, Object receiver, Object[] arguments) {
      PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;
      Object prev = hostEnter(context);

      try {
         throw executeUnsupported(context, receiver);
      } catch (Throwable var10) {
         throw guestToHostException(context, var10, true);
      } finally {
         hostLeave(context, prev);
      }
   }

   @Override
   public Value execute(Object languageContext, Object receiver) {
      PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;
      Object prev = hostEnter(context);

      try {
         throw executeUnsupported(context, receiver);
      } catch (Throwable var9) {
         throw guestToHostException(context, var9, true);
      } finally {
         hostLeave(context, prev);
      }
   }

   @CompilerDirectives.TruffleBoundary
   static RuntimeException executeUnsupported(PolyglotLanguageContext context, Object receiver) {
      throw unsupported(context, receiver, "execute(Object...)", "canExecute()");
   }

   @Override
   public Value newInstance(Object languageContext, Object receiver, Object[] arguments) {
      PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;
      Object prev = hostEnter(context);

      Value e;
      try {
         e = newInstanceUnsupported(context, receiver);
      } catch (Throwable var10) {
         throw guestToHostException(context, var10, true);
      } finally {
         hostLeave(context, prev);
      }

      return e;
   }

   @CompilerDirectives.TruffleBoundary
   static Value newInstanceUnsupported(PolyglotLanguageContext context, Object receiver) {
      throw unsupported(context, receiver, "newInstance(Object...)", "canInstantiate()");
   }

   @Override
   public void executeVoid(Object languageContext, Object receiver, Object[] arguments) {
      PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;
      Object prev = hostEnter(context);

      try {
         executeVoidUnsupported(context, receiver);
      } catch (Throwable var10) {
         throw guestToHostException(context, var10, true);
      } finally {
         hostLeave(context, prev);
      }
   }

   @Override
   public void executeVoid(Object languageContext, Object receiver) {
      PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;
      Object prev = hostEnter(context);

      try {
         executeVoidUnsupported(context, receiver);
      } catch (Throwable var9) {
         throw guestToHostException(context, var9, true);
      } finally {
         hostLeave(context, prev);
      }
   }

   @CompilerDirectives.TruffleBoundary
   static void executeVoidUnsupported(PolyglotLanguageContext context, Object receiver) {
      throw unsupported(context, receiver, "executeVoid(Object...)", "canExecute()");
   }

   @Override
   public Value invoke(Object languageContext, Object receiver, String identifier, Object[] arguments) {
      PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;
      Object prev = hostEnter(context);

      try {
         throw invokeUnsupported(context, receiver, identifier);
      } catch (Throwable var11) {
         throw guestToHostException(context, var11, true);
      } finally {
         hostLeave(context, prev);
      }
   }

   @Override
   public Value invoke(Object languageContext, Object receiver, String identifier) {
      PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;
      Object prev = hostEnter(context);

      try {
         throw invokeUnsupported(context, receiver, identifier);
      } catch (Throwable var10) {
         throw guestToHostException(context, var10, true);
      } finally {
         hostLeave(context, prev);
      }
   }

   @CompilerDirectives.TruffleBoundary
   static RuntimeException invokeUnsupported(PolyglotLanguageContext context, Object receiver, String identifier) {
      throw unsupported(context, receiver, "invoke(" + identifier + ", Object...)", "canInvoke(String)");
   }

   @Override
   public String asString(Object languageContext, Object receiver) {
      PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;
      Object prev = hostEnter(context);

      String e;
      try {
         e = asStringUnsupported(context, receiver);
      } catch (Throwable var9) {
         throw guestToHostException(context, var9, true);
      } finally {
         hostLeave(context, prev);
      }

      return e;
   }

   protected static String asStringUnsupported(PolyglotLanguageContext context, Object receiver) {
      return invalidCastPrimitive(context, receiver, String.class, "asString()", "isString()", "Invalid coercion.");
   }

   @Override
   public boolean asBoolean(Object languageContext, Object receiver) {
      PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;
      Object prev = hostEnter(context);

      boolean e;
      try {
         e = asBooleanUnsupported(context, receiver);
      } catch (Throwable var9) {
         throw guestToHostException(context, var9, true);
      } finally {
         hostLeave(context, prev);
      }

      return e;
   }

   private static boolean isNullUncached(Object receiver) {
      return InteropLibrary.getFactory().getUncached().isNull(receiver);
   }

   protected static boolean asBooleanUnsupported(PolyglotLanguageContext context, Object receiver) {
      return invalidCastPrimitive(context, receiver, boolean.class, "asBoolean()", "isBoolean()", "Invalid or lossy primitive coercion.");
   }

   private static <T> T invalidCastPrimitive(
      PolyglotLanguageContext context, Object receiver, Class<T> clazz, String asMethodName, String isMethodName, String detail
   ) {
      if (isNullUncached(receiver)) {
         throw nullCoercion(context, receiver, clazz, asMethodName, isMethodName);
      } else {
         throw cannotConvert(context, receiver, clazz, asMethodName, isMethodName, detail);
      }
   }

   @Override
   public int asInt(Object languageContext, Object receiver) {
      PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;
      Object prev = hostEnter(context);

      int e;
      try {
         e = asIntUnsupported(context, receiver);
      } catch (Throwable var9) {
         throw guestToHostException(context, var9, true);
      } finally {
         hostLeave(context, prev);
      }

      return e;
   }

   protected static int asIntUnsupported(PolyglotLanguageContext context, Object receiver) {
      return invalidCastPrimitive(context, receiver, int.class, "asInt()", "fitsInInt()", "Invalid or lossy primitive coercion.");
   }

   @Override
   public long asLong(Object languageContext, Object receiver) {
      PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;
      Object prev = hostEnter(context);

      long e;
      try {
         e = asLongUnsupported(context, receiver);
      } catch (Throwable var10) {
         throw guestToHostException(context, var10, true);
      } finally {
         hostLeave(context, prev);
      }

      return e;
   }

   protected static long asLongUnsupported(PolyglotLanguageContext context, Object receiver) {
      return invalidCastPrimitive(context, receiver, long.class, "asLong()", "fitsInLong()", "Invalid or lossy primitive coercion.");
   }

   @Override
   public double asDouble(Object languageContext, Object receiver) {
      PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;
      Object prev = hostEnter(context);

      double e;
      try {
         e = asDoubleUnsupported(context, receiver);
      } catch (Throwable var10) {
         throw guestToHostException(context, var10, true);
      } finally {
         hostLeave(context, prev);
      }

      return e;
   }

   protected static double asDoubleUnsupported(PolyglotLanguageContext context, Object receiver) {
      return invalidCastPrimitive(context, receiver, double.class, "asDouble()", "fitsInDouble()", "Invalid or lossy primitive coercion.");
   }

   @Override
   public float asFloat(Object languageContext, Object receiver) {
      PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;
      Object prev = hostEnter(context);

      float e;
      try {
         e = asFloatUnsupported(context, receiver);
      } catch (Throwable var9) {
         throw guestToHostException(context, var9, true);
      } finally {
         hostLeave(context, prev);
      }

      return e;
   }

   protected static float asFloatUnsupported(PolyglotLanguageContext context, Object receiver) {
      return invalidCastPrimitive(context, receiver, float.class, "asFloat()", "fitsInFloat()", "Invalid or lossy primitive coercion.");
   }

   @Override
   public byte asByte(Object languageContext, Object receiver) {
      PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;
      Object prev = hostEnter(context);

      byte e;
      try {
         e = asByteUnsupported(context, receiver);
      } catch (Throwable var9) {
         throw guestToHostException(context, var9, true);
      } finally {
         hostLeave(context, prev);
      }

      return e;
   }

   protected static byte asByteUnsupported(PolyglotLanguageContext context, Object receiver) {
      return invalidCastPrimitive(context, receiver, byte.class, "asByte()", "fitsInByte()", "Invalid or lossy primitive coercion.");
   }

   @Override
   public short asShort(Object languageContext, Object receiver) {
      PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;
      Object prev = hostEnter(context);

      short e;
      try {
         e = asShortUnsupported(context, receiver);
      } catch (Throwable var9) {
         throw guestToHostException(context, var9, true);
      } finally {
         hostLeave(context, prev);
      }

      return e;
   }

   protected static short asShortUnsupported(PolyglotLanguageContext context, Object receiver) {
      return invalidCastPrimitive(context, receiver, short.class, "asShort()", "fitsInShort()", "Invalid or lossy primitive coercion.");
   }

   @Override
   public long asNativePointer(Object languageContext, Object receiver) {
      PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;
      Object prev = hostEnter(context);

      long e;
      try {
         e = asNativePointerUnsupported(context, receiver);
      } catch (Throwable var10) {
         throw guestToHostException(context, var10, true);
      } finally {
         hostLeave(context, prev);
      }

      return e;
   }

   static long asNativePointerUnsupported(PolyglotLanguageContext context, Object receiver) {
      throw cannotConvert(context, receiver, long.class, "asNativePointer()", "isNativeObject()", "Value cannot be converted to a native pointer.");
   }

   @Override
   public Object asHostObject(Object languageContext, Object receiver) {
      PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;
      Object prev = hostEnter(context);

      Object e;
      try {
         e = asHostObjectUnsupported(context, receiver);
      } catch (Throwable var9) {
         throw guestToHostException(context, var9, true);
      } finally {
         hostLeave(context, prev);
      }

      return e;
   }

   protected static Object asHostObjectUnsupported(PolyglotLanguageContext context, Object receiver) {
      throw cannotConvert(context, receiver, null, "asHostObject()", "isHostObject()", "Value is not a host object.");
   }

   @Override
   public Object asProxyObject(Object languageContext, Object receiver) {
      PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;
      Object prev = hostEnter(context);

      Object e;
      try {
         e = asProxyObjectUnsupported(context, receiver);
      } catch (Throwable var9) {
         throw guestToHostException(context, var9, true);
      } finally {
         hostLeave(context, prev);
      }

      return e;
   }

   protected static Object asProxyObjectUnsupported(PolyglotLanguageContext context, Object receiver) {
      throw cannotConvert(context, receiver, null, "asProxyObject()", "isProxyObject()", "Value is not a proxy object.");
   }

   @Override
   public LocalDate asDate(Object languageContext, Object receiver) {
      PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;
      Object prev = hostEnter(context);

      Object e;
      try {
         if (!isNullUncached(receiver)) {
            throw cannotConvert(context, receiver, null, "asDate()", "isDate()", "Value does not contain date information.");
         }

         e = null;
      } catch (Throwable var9) {
         throw guestToHostException(context, var9, true);
      } finally {
         hostLeave(context, prev);
      }

      return (LocalDate)e;
   }

   @Override
   public LocalTime asTime(Object languageContext, Object receiver) {
      PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;
      Object prev = hostEnter(context);

      Object e;
      try {
         if (!isNullUncached(receiver)) {
            throw cannotConvert(context, receiver, null, "asTime()", "isTime()", "Value does not contain time information.");
         }

         e = null;
      } catch (Throwable var9) {
         throw guestToHostException(context, var9, true);
      } finally {
         hostLeave(context, prev);
      }

      return (LocalTime)e;
   }

   @Override
   public ZoneId asTimeZone(Object languageContext, Object receiver) {
      PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;
      Object prev = hostEnter(context);

      Object e;
      try {
         if (!isNullUncached(receiver)) {
            throw cannotConvert(context, receiver, null, "asTimeZone()", "isTimeZone()", "Value does not contain time zone information.");
         }

         e = null;
      } catch (Throwable var9) {
         throw guestToHostException(context, var9, true);
      } finally {
         hostLeave(context, prev);
      }

      return (ZoneId)e;
   }

   @Override
   public Instant asInstant(Object languageContext, Object receiver) {
      PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;
      Object prev = hostEnter(context);

      Object e;
      try {
         if (!isNullUncached(receiver)) {
            throw cannotConvert(context, receiver, null, "asInstant()", "isInstant()", "Value does not contain instant information.");
         }

         e = null;
      } catch (Throwable var9) {
         throw guestToHostException(context, var9, true);
      } finally {
         hostLeave(context, prev);
      }

      return (Instant)e;
   }

   @Override
   public Duration asDuration(Object languageContext, Object receiver) {
      PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;
      Object prev = hostEnter(context);

      Object e;
      try {
         if (!isNullUncached(receiver)) {
            throw cannotConvert(context, receiver, null, "asDuration()", "isDuration()", "Value does not contain duration information.");
         }

         e = null;
      } catch (Throwable var9) {
         throw guestToHostException(context, var9, true);
      } finally {
         hostLeave(context, prev);
      }

      return (Duration)e;
   }

   @Override
   public RuntimeException throwException(Object languageContext, Object receiver) {
      PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;
      Object prev = hostEnter(context);

      try {
         throw unsupported(context, receiver, "throwException()", "isException()");
      } catch (Throwable var9) {
         throw guestToHostException(context, var9, true);
      } finally {
         hostLeave(context, prev);
      }
   }

   @Override
   public final Value getMetaObject(Object languageContext, Object receiver) {
      PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;
      Object prev = hostEnter(context);

      Value e;
      try {
         e = this.getMetaObjectImpl(context, receiver);
      } catch (Throwable var9) {
         throw guestToHostException(context, var9, true);
      } finally {
         hostLeave(context, prev);
      }

      return e;
   }

   @Override
   public Value getIterator(Object languageContext, Object receiver) {
      PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;
      Object prev = hostEnter(context);

      Value e;
      try {
         e = getIteratorUnsupported(context, receiver);
      } catch (Throwable var9) {
         throw guestToHostException(context, var9, true);
      } finally {
         hostLeave(context, prev);
      }

      return e;
   }

   @CompilerDirectives.TruffleBoundary
   static final Value getIteratorUnsupported(PolyglotLanguageContext context, Object receiver) {
      throw unsupported(context, receiver, "getIterator()", "hasIterator()");
   }

   @Override
   public boolean hasIteratorNextElement(Object languageContext, Object receiver) {
      PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;
      Object prev = hostEnter(context);

      boolean e;
      try {
         e = hasIteratorNextElementUnsupported(context, receiver);
      } catch (Throwable var9) {
         throw guestToHostException(context, var9, true);
      } finally {
         hostLeave(context, prev);
      }

      return e;
   }

   @CompilerDirectives.TruffleBoundary
   static final boolean hasIteratorNextElementUnsupported(PolyglotLanguageContext context, Object receiver) {
      throw unsupported(context, receiver, "hasIteratorNextElement()", "isIterator()");
   }

   @Override
   public Value getIteratorNextElement(Object languageContext, Object receiver) {
      PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;
      Object prev = hostEnter(context);

      Value e;
      try {
         e = getIteratorNextElementUnsupported(context, receiver);
      } catch (Throwable var9) {
         throw guestToHostException(context, var9, true);
      } finally {
         hostLeave(context, prev);
      }

      return e;
   }

   @CompilerDirectives.TruffleBoundary
   static final Value getIteratorNextElementUnsupported(PolyglotLanguageContext context, Object receiver) {
      throw unsupported(context, receiver, "getIteratorNextElement()", "isIterator()");
   }

   @Override
   public long getHashSize(Object languageContext, Object receiver) {
      PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;
      Object prev = hostEnter(context);

      try {
         throw getHashSizeUnsupported(context, receiver);
      } catch (Throwable var9) {
         throw guestToHostException(context, var9, true);
      } finally {
         hostLeave(context, prev);
      }
   }

   @CompilerDirectives.TruffleBoundary
   static final RuntimeException getHashSizeUnsupported(PolyglotLanguageContext context, Object receiver) {
      throw unsupported(context, receiver, "getHashSize()", "hasHashEntries()");
   }

   @Override
   public Value getHashValue(Object languageContext, Object receiver, Object key) {
      PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;
      Object prev = hostEnter(context);

      try {
         throw getHashValueUnsupported(context, receiver, key);
      } catch (Throwable var10) {
         throw guestToHostException(context, var10, true);
      } finally {
         hostLeave(context, prev);
      }
   }

   @CompilerDirectives.TruffleBoundary
   static final RuntimeException getHashValueUnsupported(PolyglotLanguageContext context, Object receiver, Object key) {
      throw unsupported(context, receiver, "getHashValue(Object)", "hasHashEntries()");
   }

   @Override
   public Value getHashValueOrDefault(Object languageContext, Object receiver, Object key, Object defaultValue) {
      PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;
      Object prev = hostEnter(context);

      try {
         throw getHashValueOrDefaultUnsupported(context, receiver, key, defaultValue);
      } catch (Throwable var11) {
         throw guestToHostException(context, var11, true);
      } finally {
         hostLeave(context, prev);
      }
   }

   @CompilerDirectives.TruffleBoundary
   static final RuntimeException getHashValueOrDefaultUnsupported(PolyglotLanguageContext context, Object receiver, Object key, Object defaultValue) {
      throw unsupported(context, receiver, "getHashValueOrDefault(Object, Object)", "hasHashEntries()");
   }

   @Override
   public void putHashEntry(Object languageContext, Object receiver, Object key, Object value) {
      PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;
      Object prev = hostEnter(context);

      try {
         putHashEntryUnsupported(context, receiver, key, value);
      } catch (Throwable var11) {
         throw guestToHostException(context, var11, true);
      } finally {
         hostLeave(context, prev);
      }
   }

   @CompilerDirectives.TruffleBoundary
   static final RuntimeException putHashEntryUnsupported(PolyglotLanguageContext context, Object receiver, Object key, Object value) {
      throw unsupported(context, receiver, "putHashEntry(Object, Object)", "hasHashEntries()");
   }

   @Override
   public boolean removeHashEntry(Object languageContext, Object receiver, Object key) {
      PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;
      Object prev = hostEnter(context);

      try {
         throw removeHashEntryUnsupported(context, receiver, key);
      } catch (Throwable var10) {
         throw guestToHostException(context, var10, true);
      } finally {
         hostLeave(context, prev);
      }
   }

   @CompilerDirectives.TruffleBoundary
   static final RuntimeException removeHashEntryUnsupported(PolyglotLanguageContext context, Object receiver, Object key) {
      throw unsupported(context, receiver, "removeHashEntry(Object)", "hasHashEntries()");
   }

   @Override
   public Value getHashEntriesIterator(Object languageContext, Object receiver) {
      PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;
      Object prev = hostEnter(context);

      try {
         throw getHashEntriesIteratorUnsupported(context, receiver);
      } catch (Throwable var9) {
         throw guestToHostException(context, var9, true);
      } finally {
         hostLeave(context, prev);
      }
   }

   @CompilerDirectives.TruffleBoundary
   static final RuntimeException getHashEntriesIteratorUnsupported(PolyglotLanguageContext context, Object receiver) {
      throw unsupported(context, receiver, "getHashEntriesIterator()", "hasHashEntries()");
   }

   @Override
   public Value getHashKeysIterator(Object languageContext, Object receiver) {
      PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;
      Object prev = hostEnter(context);

      try {
         throw getHashKeysIteratorUnsupported(context, receiver);
      } catch (Throwable var9) {
         throw guestToHostException(context, var9, true);
      } finally {
         hostLeave(context, prev);
      }
   }

   @CompilerDirectives.TruffleBoundary
   static final RuntimeException getHashKeysIteratorUnsupported(PolyglotLanguageContext context, Object receiver) {
      throw unsupported(context, receiver, "getHashKeysIterator()", "hasHashEntries()");
   }

   @Override
   public Value getHashValuesIterator(Object languageContext, Object receiver) {
      PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;
      Object prev = hostEnter(context);

      try {
         throw getHashValuesIteratorUnsupported(context, receiver);
      } catch (Throwable var9) {
         throw guestToHostException(context, var9, true);
      } finally {
         hostLeave(context, prev);
      }
   }

   @Override
   public void pin(Object languageContext, Object receiver) {
      PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;
      Object prev = hostEnter(context);

      try {
         this.languageInstance.sharing.engine.host.pin(receiver);
      } catch (Throwable var9) {
         throw guestToHostException(context, var9, true);
      } finally {
         hostLeave(context, prev);
      }
   }

   @CompilerDirectives.TruffleBoundary
   static final RuntimeException getHashValuesIteratorUnsupported(PolyglotLanguageContext context, Object receiver) {
      throw unsupported(context, receiver, "getHashValuesIterator()", "hasHashEntries()");
   }

   protected Value getMetaObjectImpl(PolyglotLanguageContext context, Object receiver) {
      InteropLibrary lib = InteropLibrary.getFactory().getUncached(receiver);
      if (lib.hasMetaObject(receiver)) {
         try {
            return asValue(context, lib.getMetaObject(receiver));
         } catch (UnsupportedMessageException var5) {
            throw CompilerDirectives.shouldNotReachHere("Unexpected unsupported message.", var5);
         }
      } else {
         return null;
      }
   }

   private static Value asValue(PolyglotLanguageContext context, Object value) {
      return context == null ? PolyglotImpl.getInstance().asValue(PolyglotFastThreadLocals.getContext(null), value) : context.asValue(value);
   }

   static Object hostEnter(Object languageContext) {
      if (languageContext == null) {
         return null;
      } else {
         PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;
         PolyglotContextImpl c = context.context;

         try {
            return c.engine.enterIfNeeded(c, true);
         } catch (Throwable var4) {
            throw guestToHostException(context, var4, false);
         }
      }
   }

   static void hostLeave(Object languageContext, Object prev) {
      if (languageContext != null) {
         PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;

         try {
            PolyglotContextImpl c = context.context;
            c.engine.leaveIfNeeded(prev, c);
         } catch (Throwable var4) {
            throw guestToHostException(context, var4, false);
         }
      }
   }

   @CompilerDirectives.TruffleBoundary
   protected static RuntimeException unsupported(PolyglotLanguageContext context, Object receiver, String message, String useToCheck) {
      String polyglotMessage;
      if (useToCheck != null) {
         polyglotMessage = String.format(
            "Unsupported operation %s.%s for %s. You can ensure that the operation is supported using %s.%s.",
            Value.class.getSimpleName(),
            message,
            getValueInfo(context, receiver),
            Value.class.getSimpleName(),
            useToCheck
         );
      } else {
         polyglotMessage = String.format("Unsupported operation %s.%s for %s.", Value.class.getSimpleName(), message, getValueInfo(context, receiver));
      }

      return PolyglotEngineException.unsupported(polyglotMessage);
   }

   @CompilerDirectives.TruffleBoundary
   static String getValueInfo(Object languageContext, Object receiver) {
      PolyglotContextImpl context = languageContext != null ? ((PolyglotLanguageContext)languageContext).context : null;
      return getValueInfo(context, receiver);
   }

   @CompilerDirectives.TruffleBoundary
   static String getValueInfo(PolyglotContextImpl context, Object receiver) {
      if (context == null) {
         return receiver.toString();
      } else if (receiver == null) {
         assert false : "receiver should never be null";

         return "null";
      } else {
         PolyglotLanguage displayLanguage = EngineAccessor.EngineImpl.findObjectLanguage(context.engine, receiver);
         Object view;
         if (displayLanguage == null) {
            displayLanguage = context.engine.hostLanguage;
            view = context.getHostContext().getLanguageView(receiver);
         } else {
            view = receiver;
         }

         String metaObjectToString = "Unknown";

         String valueToString;
         try {
            InteropLibrary uncached = InteropLibrary.getFactory().getUncached(view);
            if (uncached.hasMetaObject(view)) {
               Object qualifiedName = INTEROP.getMetaQualifiedName(uncached.getMetaObject(view));
               metaObjectToString = truncateString(INTEROP.asString(qualifiedName), 140);
            }

            valueToString = truncateString(INTEROP.asString(uncached.toDisplayString(view)), 140);
         } catch (UnsupportedMessageException var8) {
            throw CompilerDirectives.shouldNotReachHere(var8);
         }

         String languageName = null;
         boolean hideType = false;
         if (displayLanguage.isHost()) {
            languageName = "Java";
            if ("Unknown".equals(metaObjectToString) && INTEROP.isNull(receiver)) {
               hideType = true;
            }
         } else {
            languageName = displayLanguage.getName();
         }

         return hideType
            ? String.format("'%s'(language: %s)", valueToString, languageName)
            : String.format("'%s'(language: %s, type: %s)", valueToString, languageName, metaObjectToString);
      }
   }

   private static String truncateString(String s, int i) {
      return s.length() > i ? s.substring(0, i - "...".length()) + "..." : s;
   }

   @CompilerDirectives.TruffleBoundary
   protected static RuntimeException nullCoercion(Object languageContext, Object receiver, Class<?> targetType, String message, String useToCheck) {
      assert isEnteredOrNull(languageContext);

      String valueInfo = getValueInfo(languageContext, receiver);
      throw PolyglotEngineException.nullPointer(
         String.format(
            "Cannot convert null value %s to Java type '%s' using %s.%s. You can ensure that the operation is supported using %s.%s.",
            valueInfo,
            targetType,
            Value.class.getSimpleName(),
            message,
            Value.class.getSimpleName(),
            useToCheck
         )
      );
   }

   static boolean isEnteredOrNull(Object languageContext) {
      if (languageContext == null) {
         return true;
      } else {
         PolyglotContextImpl context = ((PolyglotLanguageContext)languageContext).context;
         return !context.engine.needsEnter(context);
      }
   }

   @CompilerDirectives.TruffleBoundary
   protected static RuntimeException cannotConvert(
      Object languageContext, Object receiver, Class<?> targetType, String message, String useToCheck, String reason
   ) {
      assert isEnteredOrNull(languageContext);

      String valueInfo = getValueInfo(languageContext, receiver);
      String targetTypeString = "";
      if (targetType != null) {
         targetTypeString = String.format("to Java type '%s'", targetType.getTypeName());
      }

      throw PolyglotEngineException.classCast(
         String.format(
            "Cannot convert %s %s using %s.%s: %s You can ensure that the value can be converted using %s.%s.",
            valueInfo,
            targetTypeString,
            Value.class.getSimpleName(),
            message,
            reason,
            Value.class.getSimpleName(),
            useToCheck
         )
      );
   }

   @CompilerDirectives.TruffleBoundary
   protected static RuntimeException invalidArrayIndex(PolyglotLanguageContext context, Object receiver, long index) {
      String message = String.format("Invalid array index %s for array %s.", index, getValueInfo(context, receiver));
      throw PolyglotEngineException.arrayIndexOutOfBounds(message);
   }

   @CompilerDirectives.TruffleBoundary
   protected static RuntimeException invalidArrayValue(PolyglotLanguageContext context, Object receiver, long identifier, Object value) {
      throw PolyglotEngineException.classCast(
         String.format("Invalid array value %s for array %s and index %s.", getValueInfo(context, value), getValueInfo(context, receiver), identifier)
      );
   }

   @CompilerDirectives.TruffleBoundary
   protected static RuntimeException nonReadableMemberKey(PolyglotLanguageContext context, Object receiver, String identifier) {
      String message = String.format("Non readable or non-existent member key '%s' for object %s.", identifier, getValueInfo(context, receiver));
      throw PolyglotEngineException.unsupported(message);
   }

   @CompilerDirectives.TruffleBoundary
   protected static RuntimeException nonWritableMemberKey(PolyglotLanguageContext context, Object receiver, String identifier) {
      String message = String.format("Non writable or non-existent member key '%s' for object %s.", identifier, getValueInfo(context, receiver));
      throw PolyglotEngineException.unsupported(message);
   }

   @CompilerDirectives.TruffleBoundary
   protected static RuntimeException nonRemovableMemberKey(PolyglotLanguageContext context, Object receiver, String identifier) {
      String message = String.format("Non removable or non-existent member key '%s' for object %s.", identifier, getValueInfo(context, receiver));
      throw PolyglotEngineException.unsupported(message);
   }

   @CompilerDirectives.TruffleBoundary
   protected static RuntimeException invalidMemberValue(PolyglotLanguageContext context, Object receiver, String identifier, Object value) {
      String message = String.format(
         "Invalid member value %s for object %s and member key '%s'.", getValueInfo(context, value), getValueInfo(context, receiver), identifier
      );
      throw PolyglotEngineException.illegalArgument(message);
   }

   @CompilerDirectives.TruffleBoundary
   protected static RuntimeException stopIteration(PolyglotLanguageContext context, Object receiver) {
      String message = String.format("Iteration was stopped for iterator %s.", getValueInfo(context, receiver));
      throw PolyglotEngineException.noSuchElement(message);
   }

   @CompilerDirectives.TruffleBoundary
   protected static RuntimeException nonReadableIteratorElement() {
      throw PolyglotEngineException.unsupported("Iterator element is not readable.");
   }

   @CompilerDirectives.TruffleBoundary
   protected static RuntimeException invalidHashValue(PolyglotLanguageContext context, Object receiver, Object key, Object value) {
      String message = String.format(
         "Invalid hash value %s for object %s and hash key %s.", getValueInfo(context, value), getValueInfo(context, receiver), getValueInfo(context, key)
      );
      throw PolyglotEngineException.illegalArgument(message);
   }

   @CompilerDirectives.TruffleBoundary
   protected static RuntimeException invalidExecuteArgumentType(PolyglotLanguageContext context, Object receiver, UnsupportedTypeException e) {
      String originalMessage = e.getMessage() == null ? "" : e.getMessage() + " ";
      String[] formattedArgs = formatArgs(context, e.getSuppliedValues());
      throw PolyglotEngineException.illegalArgument(
         String.format(
            "Invalid argument when executing %s. %sProvided arguments: %s.", getValueInfo(context, receiver), originalMessage, Arrays.asList(formattedArgs)
         )
      );
   }

   @CompilerDirectives.TruffleBoundary
   protected static RuntimeException invalidInvokeArgumentType(PolyglotLanguageContext context, Object receiver, String member, UnsupportedTypeException e) {
      String originalMessage = e.getMessage() == null ? "" : e.getMessage();
      String[] formattedArgs = formatArgs(context, e.getSuppliedValues());
      String message = String.format(
         "Invalid argument when invoking '%s' on %s. %sProvided arguments: %s.",
         member,
         getValueInfo(context, receiver),
         originalMessage,
         Arrays.asList(formattedArgs)
      );
      throw PolyglotEngineException.illegalArgument(message);
   }

   @CompilerDirectives.TruffleBoundary
   protected static RuntimeException invalidInstantiateArgumentType(PolyglotLanguageContext context, Object receiver, Object[] arguments) {
      String[] formattedArgs = formatArgs(context, arguments);
      String message = String.format("Invalid argument when instantiating %s with arguments %s.", getValueInfo(context, receiver), Arrays.asList(formattedArgs));
      throw PolyglotEngineException.illegalArgument(message);
   }

   @CompilerDirectives.TruffleBoundary
   protected static RuntimeException invalidInstantiateArity(
      PolyglotLanguageContext context, Object receiver, Object[] arguments, int expectedMin, int expectedMax, int actual
   ) {
      String[] formattedArgs = formatArgs(context, arguments);
      String message = String.format(
         "Invalid argument count when instantiating %s with arguments %s. %s",
         getValueInfo(context, receiver),
         Arrays.asList(formattedArgs),
         formatExpectedArguments(expectedMin, expectedMax, actual)
      );
      throw PolyglotEngineException.illegalArgument(message);
   }

   @CompilerDirectives.TruffleBoundary
   protected static RuntimeException invalidExecuteArity(
      PolyglotLanguageContext context, Object receiver, Object[] arguments, int expectedMin, int expectedMax, int actual
   ) {
      String[] formattedArgs = formatArgs(context, arguments);
      String message = String.format(
         "Invalid argument count when executing %s with arguments %s. %s",
         getValueInfo(context, receiver),
         Arrays.asList(formattedArgs),
         formatExpectedArguments(expectedMin, expectedMax, actual)
      );
      throw PolyglotEngineException.illegalArgument(message);
   }

   @CompilerDirectives.TruffleBoundary
   protected static RuntimeException invalidInvokeArity(
      PolyglotLanguageContext context, Object receiver, String member, Object[] arguments, int expectedMin, int expectedMax, int actual
   ) {
      String[] formattedArgs = formatArgs(context, arguments);
      String message = String.format(
         "Invalid argument count when invoking '%s' on %s with arguments %s. %s",
         member,
         getValueInfo(context, receiver),
         Arrays.asList(formattedArgs),
         formatExpectedArguments(expectedMin, expectedMax, actual)
      );
      throw PolyglotEngineException.illegalArgument(message);
   }

   static String formatExpectedArguments(int expectedMinArity, int expectedMaxArity, int actualArity) {
      String actual;
      if (actualArity < 0) {
         actual = "unknown";
      } else {
         actual = String.valueOf(actualArity);
      }

      String expected;
      if (expectedMinArity == expectedMaxArity) {
         expected = String.valueOf(expectedMinArity);
      } else if (expectedMaxArity < 0) {
         expected = expectedMinArity + "+";
      } else {
         expected = expectedMinArity + "-" + expectedMaxArity;
      }

      return String.format("Expected %s argument(s) but got %s.", expected, actual);
   }

   private static String[] formatArgs(Object languageContext, Object[] arguments) {
      String[] formattedArgs = new String[arguments.length];

      for (int i = 0; i < arguments.length; i++) {
         formattedArgs[i] = getValueInfo(languageContext, arguments[i]);
      }

      return formattedArgs;
   }

   @Override
   public final String toString(Object languageContext, Object receiver) {
      PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;
      Object prev = hostEnter(context);

      String e;
      try {
         e = this.toStringImpl(context, receiver);
      } catch (Throwable var9) {
         throw guestToHostException(context, var9, true);
      } finally {
         hostLeave(context, prev);
      }

      return e;
   }

   protected String toStringImpl(Object languageContext, Object receiver) throws AssertionError {
      InteropLibrary lib = InteropLibrary.getFactory().getUncached(receiver);
      Object result = lib.toDisplayString(receiver);
      InteropLibrary resultLib = InteropLibrary.getFactory().getUncached(result);

      try {
         return resultLib.asString(result);
      } catch (UnsupportedMessageException var7) {
         throw CompilerDirectives.shouldNotReachHere("toDisplayString must be coercible to java.lang.String, but is not.", var7);
      }
   }

   @Override
   public SourceSection getSourceLocation(Object languageContext, Object receiver) {
      if (languageContext == null) {
         return null;
      } else {
         PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;
         Object prev = hostEnter(context);

         Object var7;
         try {
            InteropLibrary lib = InteropLibrary.getFactory().getUncached(receiver);
            com.oracle.truffle.api.source.SourceSection result = null;
            if (lib.hasSourceLocation(receiver)) {
               try {
                  result = lib.getSourceLocation(receiver);
               } catch (UnsupportedMessageException var12) {
               }
            }

            if (result != null) {
               return PolyglotImpl.getPolyglotSourceSection(this.impl, result);
            }

            var7 = null;
         } catch (Throwable var13) {
            throw guestToHostException(context, var13, true);
         } finally {
            hostLeave(context, prev);
         }

         return (SourceSection)var7;
      }
   }

   @Override
   public boolean isMetaObject(Object languageContext, Object receiver) {
      return false;
   }

   @Override
   public boolean equalsImpl(Object languageContext, Object receiver, Object obj) {
      return receiver == obj ? true : PolyglotWrapper.equals(languageContext, receiver, obj);
   }

   @Override
   public int hashCodeImpl(Object languageContext, Object receiver) {
      return PolyglotWrapper.hashCode(languageContext, receiver);
   }

   @Override
   public boolean isMetaInstance(Object languageContext, Object receiver, Object instance) {
      PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;
      Object prev = hostEnter(context);

      try {
         throw unsupported(context, receiver, "isMetaInstance(Object)", "isMetaObject()");
      } catch (Throwable var10) {
         throw guestToHostException(context, var10, true);
      } finally {
         hostLeave(context, prev);
      }
   }

   @Override
   public String getMetaQualifiedName(Object languageContext, Object receiver) {
      PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;
      Object prev = hostEnter(context);

      try {
         throw unsupported(context, receiver, "getMetaQualifiedName()", "isMetaObject()");
      } catch (Throwable var9) {
         throw guestToHostException(context, var9, true);
      } finally {
         hostLeave(context, prev);
      }
   }

   @Override
   public String getMetaSimpleName(Object languageContext, Object receiver) {
      PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;
      Object prev = hostEnter(context);

      try {
         throw unsupported(context, receiver, "getMetaSimpleName()", "isMetaObject()");
      } catch (Throwable var9) {
         throw guestToHostException(context, var9, true);
      } finally {
         hostLeave(context, prev);
      }
   }

   @Override
   public boolean hasMetaParents(Object languageContext, Object receiver) {
      return false;
   }

   @Override
   public Value getMetaParents(Object languageContext, Object receiver) {
      PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;
      Object prev = hostEnter(context);

      try {
         throw unsupported(context, receiver, "getMetaParents()", "hasMetaParents()");
      } catch (Throwable var9) {
         throw guestToHostException(context, var9, true);
      } finally {
         hostLeave(context, prev);
      }
   }

   static CallTarget createTarget(PolyglotValueDispatch.InteropNode root) {
      CallTarget target = root.getCallTarget();
      Class<?>[] types = root.getArgumentTypes();
      if (types != null) {
         EngineAccessor.RUNTIME.initializeProfile(target, types);
      }

      return target;
   }

   static PolyglotValueDispatch createInteropValue(PolyglotLanguageInstance languageInstance, TruffleObject receiver, Class<?> receiverType) {
      return new PolyglotValueDispatch.InteropValue(languageInstance.getImpl(), languageInstance, receiver, receiverType);
   }

   static PolyglotValueDispatch createHostNull(PolyglotImpl polyglot) {
      return new PolyglotValueDispatch.HostNull(polyglot);
   }

   static void createDefaultValues(PolyglotImpl polyglot, PolyglotLanguageInstance languageInstance, Map<Class<?>, PolyglotValueDispatch> valueCache) {
      addDefaultValue(polyglot, languageInstance, valueCache, false);
      addDefaultValue(polyglot, languageInstance, valueCache, "");
      addDefaultValue(polyglot, languageInstance, valueCache, TruffleString.fromJavaStringUncached("", TruffleString.Encoding.UTF_16));
      addDefaultValue(polyglot, languageInstance, valueCache, 'a');
      addDefaultValue(polyglot, languageInstance, valueCache, (byte)0);
      addDefaultValue(polyglot, languageInstance, valueCache, (short)0);
      addDefaultValue(polyglot, languageInstance, valueCache, 0);
      addDefaultValue(polyglot, languageInstance, valueCache, 0L);
      addDefaultValue(polyglot, languageInstance, valueCache, 0.0F);
      addDefaultValue(polyglot, languageInstance, valueCache, 0.0);
   }

   static void addDefaultValue(
      PolyglotImpl polyglot, PolyglotLanguageInstance languageInstance, Map<Class<?>, PolyglotValueDispatch> valueCache, Object primitive
   ) {
      valueCache.put(primitive.getClass(), new PolyglotValueDispatch.PrimitiveValue(polyglot, languageInstance, primitive));
   }

   private static final class HostNull extends PolyglotValueDispatch {
      private final PolyglotImpl polyglot;

      HostNull(PolyglotImpl polyglot) {
         super(polyglot, null);
         this.polyglot = polyglot;
      }

      @Override
      public boolean isNull(Object languageContext, Object receiver) {
         return true;
      }

      @Override
      public <T> T as(Object languageContext, Object receiver, Class<T> targetType) {
         return (T)(targetType == Value.class ? this.polyglot.hostNull : null);
      }

      @Override
      public <T> T as(Object languageContext, Object receiver, TypeLiteral<T> targetType) {
         return this.as(languageContext, receiver, targetType.getRawType());
      }
   }

   static final class HostValue extends PolyglotValueDispatch {
      HostValue(PolyglotImpl polyglot) {
         super(polyglot, null);
      }

      @Override
      public boolean isHostObject(Object languageContext, Object receiver) {
         return EngineAccessor.HOST.isDisconnectedHostObject(receiver);
      }

      @Override
      public Object asHostObject(Object languageContext, Object receiver) {
         return EngineAccessor.HOST.unboxDisconnectedHostObject(receiver);
      }

      @Override
      public boolean isProxyObject(Object languageContext, Object receiver) {
         return EngineAccessor.HOST.isDisconnectedHostProxy(receiver);
      }

      @Override
      public Object asProxyObject(Object languageContext, Object receiver) {
         return EngineAccessor.HOST.unboxDisconnectedHostProxy(receiver);
      }

      @Override
      public <T> T as(Object languageContext, Object receiver, Class<T> targetType) {
         return this.asImpl(languageContext, receiver, targetType);
      }

      @Override
      public <T> T as(Object languageContext, Object receiver, TypeLiteral<T> targetType) {
         return this.asImpl(languageContext, receiver, targetType.getRawType());
      }

      <T> T asImpl(Object languageContext, Object receiver, Class<T> targetType) {
         Object hostValue;
         if (this.isProxyObject(languageContext, receiver)) {
            hostValue = this.asProxyObject(languageContext, receiver);
         } else {
            if (!this.isHostObject(languageContext, receiver)) {
               throw new ClassCastException();
            }

            hostValue = this.asHostObject(languageContext, receiver);
         }

         return targetType.cast(hostValue);
      }
   }

   abstract static class InteropNode extends HostToGuestRootNode {
      protected static final int CACHE_LIMIT = 5;
      protected final PolyglotValueDispatch.InteropValue polyglot;

      protected abstract String getOperationName();

      protected InteropNode(PolyglotValueDispatch.InteropValue polyglot) {
         super(polyglot.languageInstance);
         this.polyglot = polyglot;
      }

      protected abstract Class<?>[] getArgumentTypes();

      @Override
      protected Class<? extends Object> getReceiverType() {
         return (Class<? extends Object>)this.polyglot.receiverType;
      }

      protected final PolyglotLanguageContext.ToHostValueNode createToHost() {
         return PolyglotLanguageContext.ToHostValueNode.create(this.getImpl());
      }

      @Override
      public final String getName() {
         return "org.graalvm.polyglot.Value<" + this.polyglot.receiverType.getSimpleName() + ">." + this.getOperationName();
      }

      protected final AbstractPolyglotImpl getImpl() {
         return this.polyglot.impl;
      }

      @Override
      public final String toString() {
         return this.getName();
      }
   }

   static final class InteropValue extends PolyglotValueDispatch {
      final CallTarget isNativePointer;
      final CallTarget asNativePointer;
      final CallTarget hasArrayElements;
      final CallTarget getArrayElement;
      final CallTarget setArrayElement;
      final CallTarget removeArrayElement;
      final CallTarget getArraySize;
      final CallTarget hasBufferElements;
      final CallTarget isBufferWritable;
      final CallTarget getBufferSize;
      final CallTarget readBufferByte;
      final CallTarget writeBufferByte;
      final CallTarget readBufferShort;
      final CallTarget writeBufferShort;
      final CallTarget readBufferInt;
      final CallTarget writeBufferInt;
      final CallTarget readBufferLong;
      final CallTarget writeBufferLong;
      final CallTarget readBufferFloat;
      final CallTarget writeBufferFloat;
      final CallTarget readBufferDouble;
      final CallTarget writeBufferDouble;
      final CallTarget hasMembers;
      final CallTarget hasMember;
      final CallTarget getMember;
      final CallTarget putMember;
      final CallTarget removeMember;
      final CallTarget isNull;
      final CallTarget canExecute;
      final CallTarget execute;
      final CallTarget canInstantiate;
      final CallTarget newInstance;
      final CallTarget executeNoArgs;
      final CallTarget executeVoid;
      final CallTarget executeVoidNoArgs;
      final CallTarget canInvoke;
      final CallTarget invoke;
      final CallTarget invokeNoArgs;
      final CallTarget getMemberKeys;
      final CallTarget isDate;
      final CallTarget asDate;
      final CallTarget isTime;
      final CallTarget asTime;
      final CallTarget isTimeZone;
      final CallTarget asTimeZone;
      final CallTarget asInstant;
      final CallTarget isDuration;
      final CallTarget asDuration;
      final CallTarget isException;
      final CallTarget throwException;
      final CallTarget isMetaObject;
      final CallTarget isMetaInstance;
      final CallTarget getMetaQualifiedName;
      final CallTarget getMetaSimpleName;
      final CallTarget hasMetaParents;
      final CallTarget getMetaParents;
      final CallTarget hasIterator;
      final CallTarget getIterator;
      final CallTarget isIterator;
      final CallTarget hasIteratorNextElement;
      final CallTarget getIteratorNextElement;
      final CallTarget hasHashEntries;
      final CallTarget getHashSize;
      final CallTarget hasHashEntry;
      final CallTarget getHashValue;
      final CallTarget getHashValueOrDefault;
      final CallTarget putHashEntry;
      final CallTarget removeHashEntry;
      final CallTarget getHashEntriesIterator;
      final CallTarget getHashKeysIterator;
      final CallTarget getHashValuesIterator;
      final CallTarget asClassLiteral;
      final CallTarget asTypeLiteral;
      final Class<?> receiverType;

      InteropValue(PolyglotImpl polyglot, PolyglotLanguageInstance languageInstance, Object receiverObject, Class<?> receiverType) {
         super(polyglot, languageInstance);
         this.receiverType = receiverType;
         this.asClassLiteral = createTarget(new PolyglotValueDispatch.InteropValue.AsClassLiteralNode(this));
         this.asTypeLiteral = createTarget(new PolyglotValueDispatch.InteropValue.AsTypeLiteralNode(this));
         this.isNativePointer = createTarget(PolyglotValueDispatchFactory.InteropValueFactory.IsNativePointerNodeGen.create(this));
         this.asNativePointer = createTarget(PolyglotValueDispatchFactory.InteropValueFactory.AsNativePointerNodeGen.create(this));
         this.hasArrayElements = createTarget(PolyglotValueDispatchFactory.InteropValueFactory.HasArrayElementsNodeGen.create(this));
         this.getArrayElement = createTarget(PolyglotValueDispatchFactory.InteropValueFactory.GetArrayElementNodeGen.create(this));
         this.setArrayElement = createTarget(PolyglotValueDispatchFactory.InteropValueFactory.SetArrayElementNodeGen.create(this));
         this.removeArrayElement = createTarget(PolyglotValueDispatchFactory.InteropValueFactory.RemoveArrayElementNodeGen.create(this));
         this.getArraySize = createTarget(PolyglotValueDispatchFactory.InteropValueFactory.GetArraySizeNodeGen.create(this));
         this.hasBufferElements = createTarget(PolyglotValueDispatchFactory.InteropValueFactory.HasBufferElementsNodeGen.create(this));
         this.isBufferWritable = createTarget(PolyglotValueDispatchFactory.InteropValueFactory.IsBufferWritableNodeGen.create(this));
         this.getBufferSize = createTarget(PolyglotValueDispatchFactory.InteropValueFactory.GetBufferSizeNodeGen.create(this));
         this.readBufferByte = createTarget(PolyglotValueDispatchFactory.InteropValueFactory.ReadBufferByteNodeGen.create(this));
         this.writeBufferByte = createTarget(PolyglotValueDispatchFactory.InteropValueFactory.WriteBufferByteNodeGen.create(this));
         this.readBufferShort = createTarget(PolyglotValueDispatchFactory.InteropValueFactory.ReadBufferShortNodeGen.create(this));
         this.writeBufferShort = createTarget(PolyglotValueDispatchFactory.InteropValueFactory.WriteBufferShortNodeGen.create(this));
         this.readBufferInt = createTarget(PolyglotValueDispatchFactory.InteropValueFactory.ReadBufferIntNodeGen.create(this));
         this.writeBufferInt = createTarget(PolyglotValueDispatchFactory.InteropValueFactory.WriteBufferIntNodeGen.create(this));
         this.readBufferLong = createTarget(PolyglotValueDispatchFactory.InteropValueFactory.ReadBufferLongNodeGen.create(this));
         this.writeBufferLong = createTarget(PolyglotValueDispatchFactory.InteropValueFactory.WriteBufferLongNodeGen.create(this));
         this.readBufferFloat = createTarget(PolyglotValueDispatchFactory.InteropValueFactory.ReadBufferFloatNodeGen.create(this));
         this.writeBufferFloat = createTarget(PolyglotValueDispatchFactory.InteropValueFactory.WriteBufferFloatNodeGen.create(this));
         this.readBufferDouble = createTarget(PolyglotValueDispatchFactory.InteropValueFactory.ReadBufferDoubleNodeGen.create(this));
         this.writeBufferDouble = createTarget(PolyglotValueDispatchFactory.InteropValueFactory.WriteBufferDoubleNodeGen.create(this));
         this.hasMember = createTarget(PolyglotValueDispatchFactory.InteropValueFactory.HasMemberNodeGen.create(this));
         this.getMember = createTarget(PolyglotValueDispatchFactory.InteropValueFactory.GetMemberNodeGen.create(this));
         this.putMember = createTarget(PolyglotValueDispatchFactory.InteropValueFactory.PutMemberNodeGen.create(this));
         this.removeMember = createTarget(PolyglotValueDispatchFactory.InteropValueFactory.RemoveMemberNodeGen.create(this));
         this.isNull = createTarget(PolyglotValueDispatchFactory.InteropValueFactory.IsNullNodeGen.create(this));
         this.execute = createTarget(new PolyglotValueDispatch.InteropValue.ExecuteNode(this));
         this.executeNoArgs = createTarget(new PolyglotValueDispatch.InteropValue.ExecuteNoArgsNode(this));
         this.executeVoid = createTarget(new PolyglotValueDispatch.InteropValue.ExecuteVoidNode(this));
         this.executeVoidNoArgs = createTarget(new PolyglotValueDispatch.InteropValue.ExecuteVoidNoArgsNode(this));
         this.newInstance = createTarget(PolyglotValueDispatchFactory.InteropValueFactory.NewInstanceNodeGen.create(this));
         this.canInstantiate = createTarget(PolyglotValueDispatchFactory.InteropValueFactory.CanInstantiateNodeGen.create(this));
         this.canExecute = createTarget(PolyglotValueDispatchFactory.InteropValueFactory.CanExecuteNodeGen.create(this));
         this.canInvoke = createTarget(PolyglotValueDispatchFactory.InteropValueFactory.CanInvokeNodeGen.create(this));
         this.invoke = createTarget(new PolyglotValueDispatch.InteropValue.InvokeNode(this));
         this.invokeNoArgs = createTarget(new PolyglotValueDispatch.InteropValue.InvokeNoArgsNode(this));
         this.hasMembers = createTarget(PolyglotValueDispatchFactory.InteropValueFactory.HasMembersNodeGen.create(this));
         this.getMemberKeys = createTarget(PolyglotValueDispatchFactory.InteropValueFactory.GetMemberKeysNodeGen.create(this));
         this.isDate = createTarget(PolyglotValueDispatchFactory.InteropValueFactory.IsDateNodeGen.create(this));
         this.asDate = createTarget(PolyglotValueDispatchFactory.InteropValueFactory.AsDateNodeGen.create(this));
         this.isTime = createTarget(PolyglotValueDispatchFactory.InteropValueFactory.IsTimeNodeGen.create(this));
         this.asTime = createTarget(PolyglotValueDispatchFactory.InteropValueFactory.AsTimeNodeGen.create(this));
         this.isTimeZone = createTarget(PolyglotValueDispatchFactory.InteropValueFactory.IsTimeZoneNodeGen.create(this));
         this.asTimeZone = createTarget(PolyglotValueDispatchFactory.InteropValueFactory.AsTimeZoneNodeGen.create(this));
         this.asInstant = createTarget(PolyglotValueDispatchFactory.InteropValueFactory.AsInstantNodeGen.create(this));
         this.isDuration = createTarget(PolyglotValueDispatchFactory.InteropValueFactory.IsDurationNodeGen.create(this));
         this.asDuration = createTarget(PolyglotValueDispatchFactory.InteropValueFactory.AsDurationNodeGen.create(this));
         this.isException = createTarget(PolyglotValueDispatchFactory.InteropValueFactory.IsExceptionNodeGen.create(this));
         this.throwException = createTarget(PolyglotValueDispatchFactory.InteropValueFactory.ThrowExceptionNodeGen.create(this));
         this.isMetaObject = createTarget(PolyglotValueDispatchFactory.InteropValueFactory.IsMetaObjectNodeGen.create(this));
         this.isMetaInstance = createTarget(PolyglotValueDispatchFactory.InteropValueFactory.IsMetaInstanceNodeGen.create(this));
         this.getMetaQualifiedName = createTarget(PolyglotValueDispatchFactory.InteropValueFactory.GetMetaQualifiedNameNodeGen.create(this));
         this.getMetaSimpleName = createTarget(PolyglotValueDispatchFactory.InteropValueFactory.GetMetaSimpleNameNodeGen.create(this));
         this.hasMetaParents = createTarget(PolyglotValueDispatchFactory.InteropValueFactory.HasMetaParentsNodeGen.create(this));
         this.getMetaParents = createTarget(PolyglotValueDispatchFactory.InteropValueFactory.GetMetaParentsNodeGen.create(this));
         this.hasIterator = createTarget(PolyglotValueDispatchFactory.InteropValueFactory.HasIteratorNodeGen.create(this));
         this.getIterator = createTarget(PolyglotValueDispatchFactory.InteropValueFactory.GetIteratorNodeGen.create(this));
         this.isIterator = createTarget(PolyglotValueDispatchFactory.InteropValueFactory.IsIteratorNodeGen.create(this));
         this.hasIteratorNextElement = createTarget(PolyglotValueDispatchFactory.InteropValueFactory.HasIteratorNextElementNodeGen.create(this));
         this.getIteratorNextElement = createTarget(PolyglotValueDispatchFactory.InteropValueFactory.GetIteratorNextElementNodeGen.create(this));
         this.hasHashEntries = createTarget(PolyglotValueDispatchFactory.InteropValueFactory.HasHashEntriesNodeGen.create(this));
         this.getHashSize = createTarget(PolyglotValueDispatchFactory.InteropValueFactory.GetHashSizeNodeGen.create(this));
         this.hasHashEntry = createTarget(PolyglotValueDispatchFactory.InteropValueFactory.HasHashEntryNodeGen.create(this));
         this.getHashValue = createTarget(PolyglotValueDispatchFactory.InteropValueFactory.GetHashValueNodeGen.create(this));
         this.getHashValueOrDefault = createTarget(PolyglotValueDispatchFactory.InteropValueFactory.GetHashValueOrDefaultNodeGen.create(this));
         this.putHashEntry = createTarget(PolyglotValueDispatchFactory.InteropValueFactory.PutHashEntryNodeGen.create(this));
         this.removeHashEntry = createTarget(PolyglotValueDispatchFactory.InteropValueFactory.RemoveHashEntryNodeGen.create(this));
         this.getHashEntriesIterator = createTarget(PolyglotValueDispatchFactory.InteropValueFactory.GetHashEntriesIteratorNodeGen.create(this));
         this.getHashKeysIterator = createTarget(PolyglotValueDispatchFactory.InteropValueFactory.GetHashKeysIteratorNodeGen.create(this));
         this.getHashValuesIterator = createTarget(PolyglotValueDispatchFactory.InteropValueFactory.GetHashValuesIteratorNodeGen.create(this));
      }

      @Override
      public <T> T as(Object languageContext, Object receiver, Class<T> targetType) {
         return (T)EngineAccessor.RUNTIME.callProfiled(this.asClassLiteral, languageContext, receiver, targetType);
      }

      @Override
      public <T> T as(Object languageContext, Object receiver, TypeLiteral<T> targetType) {
         return (T)EngineAccessor.RUNTIME.callProfiled(this.asTypeLiteral, languageContext, receiver, targetType);
      }

      @Override
      public boolean isNativePointer(Object languageContext, Object receiver) {
         return (Boolean)EngineAccessor.RUNTIME.callProfiled(this.isNativePointer, languageContext, receiver);
      }

      @Override
      public boolean hasArrayElements(Object languageContext, Object receiver) {
         return (Boolean)EngineAccessor.RUNTIME.callProfiled(this.hasArrayElements, languageContext, receiver);
      }

      @Override
      public Value getArrayElement(Object languageContext, Object receiver, long index) {
         return (Value)EngineAccessor.RUNTIME.callProfiled(this.getArrayElement, languageContext, receiver, index);
      }

      @Override
      public void setArrayElement(Object languageContext, Object receiver, long index, Object value) {
         EngineAccessor.RUNTIME.callProfiled(this.setArrayElement, languageContext, receiver, index, value);
      }

      @Override
      public boolean removeArrayElement(Object languageContext, Object receiver, long index) {
         return (Boolean)EngineAccessor.RUNTIME.callProfiled(this.removeArrayElement, languageContext, receiver, index);
      }

      @Override
      public long getArraySize(Object languageContext, Object receiver) {
         return (Long)EngineAccessor.RUNTIME.callProfiled(this.getArraySize, languageContext, receiver);
      }

      @Override
      public boolean hasBufferElements(Object languageContext, Object receiver) {
         return (Boolean)EngineAccessor.RUNTIME.callProfiled(this.hasBufferElements, languageContext, receiver);
      }

      @Override
      public boolean isBufferWritable(Object languageContext, Object receiver) {
         return (Boolean)EngineAccessor.RUNTIME.callProfiled(this.isBufferWritable, languageContext, receiver);
      }

      @Override
      public long getBufferSize(Object languageContext, Object receiver) throws UnsupportedOperationException {
         return (Long)EngineAccessor.RUNTIME.callProfiled(this.getBufferSize, languageContext, receiver);
      }

      @Override
      public byte readBufferByte(Object languageContext, Object receiver, long byteOffset) throws UnsupportedOperationException, IndexOutOfBoundsException {
         return (Byte)EngineAccessor.RUNTIME.callProfiled(this.readBufferByte, languageContext, receiver, byteOffset);
      }

      @Override
      public void writeBufferByte(Object languageContext, Object receiver, long byteOffset, byte value) throws UnsupportedOperationException, IndexOutOfBoundsException {
         EngineAccessor.RUNTIME.callProfiled(this.writeBufferByte, languageContext, receiver, byteOffset, value);
      }

      @Override
      public short readBufferShort(Object languageContext, Object receiver, ByteOrder order, long byteOffset) throws UnsupportedOperationException, IndexOutOfBoundsException {
         return (Short)EngineAccessor.RUNTIME.callProfiled(this.readBufferShort, languageContext, receiver, order, byteOffset);
      }

      @Override
      public void writeBufferShort(Object languageContext, Object receiver, ByteOrder order, long byteOffset, short value) throws UnsupportedOperationException, IndexOutOfBoundsException {
         EngineAccessor.RUNTIME.callProfiled(this.writeBufferShort, languageContext, receiver, order, byteOffset, value);
      }

      @Override
      public int readBufferInt(Object languageContext, Object receiver, ByteOrder order, long byteOffset) throws UnsupportedOperationException, IndexOutOfBoundsException {
         return (Integer)EngineAccessor.RUNTIME.callProfiled(this.readBufferInt, languageContext, receiver, order, byteOffset);
      }

      @Override
      public void writeBufferInt(Object languageContext, Object receiver, ByteOrder order, long byteOffset, int value) throws UnsupportedOperationException, IndexOutOfBoundsException {
         EngineAccessor.RUNTIME.callProfiled(this.writeBufferInt, languageContext, receiver, order, byteOffset, value);
      }

      @Override
      public long readBufferLong(Object languageContext, Object receiver, ByteOrder order, long byteOffset) throws UnsupportedOperationException, IndexOutOfBoundsException {
         return (Long)EngineAccessor.RUNTIME.callProfiled(this.readBufferLong, languageContext, receiver, order, byteOffset);
      }

      @Override
      public void writeBufferLong(Object languageContext, Object receiver, ByteOrder order, long byteOffset, long value) throws UnsupportedOperationException, IndexOutOfBoundsException {
         EngineAccessor.RUNTIME.callProfiled(this.writeBufferLong, languageContext, receiver, order, byteOffset, value);
      }

      @Override
      public float readBufferFloat(Object languageContext, Object receiver, ByteOrder order, long byteOffset) throws UnsupportedOperationException, IndexOutOfBoundsException {
         return (Float)EngineAccessor.RUNTIME.callProfiled(this.readBufferFloat, languageContext, receiver, order, byteOffset);
      }

      @Override
      public void writeBufferFloat(Object languageContext, Object receiver, ByteOrder order, long byteOffset, float value) throws UnsupportedOperationException, IndexOutOfBoundsException {
         EngineAccessor.RUNTIME.callProfiled(this.writeBufferFloat, languageContext, receiver, order, byteOffset, value);
      }

      @Override
      public double readBufferDouble(Object languageContext, Object receiver, ByteOrder order, long byteOffset) throws UnsupportedOperationException, IndexOutOfBoundsException {
         return (Double)EngineAccessor.RUNTIME.callProfiled(this.readBufferDouble, languageContext, receiver, order, byteOffset);
      }

      @Override
      public void writeBufferDouble(Object languageContext, Object receiver, ByteOrder order, long byteOffset, double value) throws UnsupportedOperationException, IndexOutOfBoundsException {
         EngineAccessor.RUNTIME.callProfiled(this.writeBufferDouble, languageContext, receiver, order, byteOffset, value);
      }

      @Override
      public boolean hasMembers(Object languageContext, Object receiver) {
         return (Boolean)EngineAccessor.RUNTIME.callProfiled(this.hasMembers, languageContext, receiver);
      }

      @Override
      public Value getMember(Object languageContext, Object receiver, String key) {
         return (Value)EngineAccessor.RUNTIME.callProfiled(this.getMember, languageContext, receiver, key);
      }

      @Override
      public boolean hasMember(Object languageContext, Object receiver, String key) {
         return (Boolean)EngineAccessor.RUNTIME.callProfiled(this.hasMember, languageContext, receiver, key);
      }

      @Override
      public void putMember(Object languageContext, Object receiver, String key, Object member) {
         EngineAccessor.RUNTIME.callProfiled(this.putMember, languageContext, receiver, key, member);
      }

      @Override
      public boolean removeMember(Object languageContext, Object receiver, String key) {
         return (Boolean)EngineAccessor.RUNTIME.callProfiled(this.removeMember, languageContext, receiver, key);
      }

      @Override
      public Set<String> getMemberKeys(Object languageContext, Object receiver) {
         Value keys = (Value)EngineAccessor.RUNTIME.callProfiled(this.getMemberKeys, languageContext, receiver);
         return (Set<String>)(keys == null ? Collections.emptySet() : new PolyglotValueDispatch.InteropValue.MemberSet(languageContext, receiver, keys));
      }

      @Override
      public long asNativePointer(Object languageContext, Object receiver) {
         return (Long)EngineAccessor.RUNTIME.callProfiled(this.asNativePointer, languageContext, receiver);
      }

      @Override
      public boolean isDate(Object languageContext, Object receiver) {
         return (Boolean)EngineAccessor.RUNTIME.callProfiled(this.isDate, languageContext, receiver);
      }

      @Override
      public LocalDate asDate(Object languageContext, Object receiver) {
         return (LocalDate)EngineAccessor.RUNTIME.callProfiled(this.asDate, languageContext, receiver);
      }

      @Override
      public boolean isTime(Object languageContext, Object receiver) {
         return (Boolean)EngineAccessor.RUNTIME.callProfiled(this.isTime, languageContext, receiver);
      }

      @Override
      public LocalTime asTime(Object languageContext, Object receiver) {
         return (LocalTime)EngineAccessor.RUNTIME.callProfiled(this.asTime, languageContext, receiver);
      }

      @Override
      public boolean isTimeZone(Object languageContext, Object receiver) {
         return (Boolean)EngineAccessor.RUNTIME.callProfiled(this.isTimeZone, languageContext, receiver);
      }

      @Override
      public ZoneId asTimeZone(Object languageContext, Object receiver) {
         return (ZoneId)EngineAccessor.RUNTIME.callProfiled(this.asTimeZone, languageContext, receiver);
      }

      @Override
      public Instant asInstant(Object languageContext, Object receiver) {
         return (Instant)EngineAccessor.RUNTIME.callProfiled(this.asInstant, languageContext, receiver);
      }

      @Override
      public boolean isDuration(Object languageContext, Object receiver) {
         return (Boolean)EngineAccessor.RUNTIME.callProfiled(this.isDuration, languageContext, receiver);
      }

      @Override
      public Duration asDuration(Object languageContext, Object receiver) {
         return (Duration)EngineAccessor.RUNTIME.callProfiled(this.asDuration, languageContext, receiver);
      }

      @Override
      public boolean isHostObject(Object languageContext, Object receiver) {
         PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;
         Object prev = hostEnter(context);

         boolean e;
         try {
            e = this.getEngine().host.isHostObject(receiver);
         } catch (Throwable var9) {
            throw guestToHostException(context, var9, true);
         } finally {
            hostLeave(context, prev);
         }

         return e;
      }

      private PolyglotEngineImpl getEngine() {
         return this.languageInstance.sharing.engine;
      }

      @Override
      public boolean isProxyObject(Object languageContext, Object receiver) {
         PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;
         Object prev = hostEnter(context);

         boolean e;
         try {
            e = this.getEngine().host.isHostProxy(receiver);
         } catch (Throwable var9) {
            throw guestToHostException(context, var9, true);
         } finally {
            hostLeave(context, prev);
         }

         return e;
      }

      @Override
      public Object asProxyObject(Object languageContext, Object receiver) {
         return this.isProxyObject(languageContext, receiver)
            ? this.getEngine().host.unboxProxyObject(receiver)
            : super.asProxyObject(languageContext, receiver);
      }

      @Override
      public Object asHostObject(Object languageContext, Object receiver) {
         return this.isHostObject(languageContext, receiver) ? this.getEngine().host.unboxHostObject(receiver) : super.asHostObject(languageContext, receiver);
      }

      @Override
      public boolean isNull(Object languageContext, Object receiver) {
         return (Boolean)EngineAccessor.RUNTIME.callProfiled(this.isNull, languageContext, receiver);
      }

      @Override
      public boolean canExecute(Object languageContext, Object receiver) {
         return (Boolean)EngineAccessor.RUNTIME.callProfiled(this.canExecute, languageContext, receiver);
      }

      @Override
      public void executeVoid(Object languageContext, Object receiver, Object[] arguments) {
         EngineAccessor.RUNTIME.callProfiled(this.executeVoid, languageContext, receiver, arguments);
      }

      @Override
      public void executeVoid(Object languageContext, Object receiver) {
         EngineAccessor.RUNTIME.callProfiled(this.executeVoidNoArgs, languageContext, receiver);
      }

      @Override
      public Value execute(Object languageContext, Object receiver, Object[] arguments) {
         return (Value)EngineAccessor.RUNTIME.callProfiled(this.execute, languageContext, receiver, arguments);
      }

      @Override
      public Value execute(Object languageContext, Object receiver) {
         return (Value)EngineAccessor.RUNTIME.callProfiled(this.executeNoArgs, languageContext, receiver);
      }

      @Override
      public boolean canInstantiate(Object languageContext, Object receiver) {
         return (Boolean)EngineAccessor.RUNTIME.callProfiled(this.canInstantiate, languageContext, receiver);
      }

      @Override
      public Value newInstance(Object languageContext, Object receiver, Object[] arguments) {
         return (Value)EngineAccessor.RUNTIME.callProfiled(this.newInstance, languageContext, receiver, arguments);
      }

      @Override
      public boolean canInvoke(Object languageContext, String identifier, Object receiver) {
         return (Boolean)EngineAccessor.RUNTIME.callProfiled(this.canInvoke, languageContext, receiver, identifier);
      }

      @Override
      public Value invoke(Object languageContext, Object receiver, String identifier, Object[] arguments) {
         return (Value)EngineAccessor.RUNTIME.callProfiled(this.invoke, languageContext, receiver, identifier, arguments);
      }

      @Override
      public Value invoke(Object languageContext, Object receiver, String identifier) {
         return (Value)EngineAccessor.RUNTIME.callProfiled(this.invokeNoArgs, languageContext, receiver, identifier);
      }

      @Override
      public boolean isException(Object languageContext, Object receiver) {
         return (Boolean)EngineAccessor.RUNTIME.callProfiled(this.isException, languageContext, receiver);
      }

      @Override
      public RuntimeException throwException(Object languageContext, Object receiver) {
         EngineAccessor.RUNTIME.callProfiled(this.throwException, languageContext, receiver);
         throw super.throwException(languageContext, receiver);
      }

      @Override
      public boolean isNumber(Object languageContext, Object receiver) {
         PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;
         Object c = hostEnter(context);

         boolean e;
         try {
            e = UNCACHED_INTEROP.isNumber(receiver);
         } catch (Throwable var9) {
            throw guestToHostException(context, var9, true);
         } finally {
            hostLeave(context, c);
         }

         return e;
      }

      @Override
      public boolean fitsInByte(Object languageContext, Object receiver) {
         PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;
         Object c = hostEnter(context);

         boolean e;
         try {
            e = UNCACHED_INTEROP.fitsInByte(receiver);
         } catch (Throwable var9) {
            throw guestToHostException(context, var9, true);
         } finally {
            hostLeave(context, c);
         }

         return e;
      }

      @Override
      public byte asByte(Object languageContext, Object receiver) {
         PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;
         Object c = hostEnter(context);

         byte var6;
         try {
            try {
               return UNCACHED_INTEROP.asByte(receiver);
            } catch (UnsupportedMessageException var11) {
               var6 = asByteUnsupported(context, receiver);
            }
         } catch (Throwable var12) {
            throw guestToHostException(context, var12, true);
         } finally {
            hostLeave(context, c);
         }

         return var6;
      }

      @Override
      public boolean isString(Object languageContext, Object receiver) {
         PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;
         Object c = hostEnter(context);

         boolean e;
         try {
            e = UNCACHED_INTEROP.isString(receiver);
         } catch (Throwable var9) {
            throw guestToHostException(context, var9, true);
         } finally {
            hostLeave(context, c);
         }

         return e;
      }

      @Override
      public String asString(Object languageContext, Object receiver) {
         PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;
         Object c = hostEnter(context);

         Object e;
         try {
            try {
               if (!PolyglotValueDispatch.isNullUncached(receiver)) {
                  return UNCACHED_INTEROP.asString(receiver);
               }

               e = null;
            } catch (UnsupportedMessageException var11) {
               return asStringUnsupported(context, receiver);
            }
         } catch (Throwable var12) {
            throw guestToHostException(context, var12, true);
         } finally {
            hostLeave(context, c);
         }

         return (String)e;
      }

      @Override
      public boolean fitsInInt(Object languageContext, Object receiver) {
         PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;
         Object c = hostEnter(context);

         boolean e;
         try {
            e = UNCACHED_INTEROP.fitsInInt(receiver);
         } catch (Throwable var9) {
            throw guestToHostException(context, var9, true);
         } finally {
            hostLeave(context, c);
         }

         return e;
      }

      @Override
      public int asInt(Object languageContext, Object receiver) {
         PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;
         Object c = hostEnter(context);

         int var6;
         try {
            try {
               return UNCACHED_INTEROP.asInt(receiver);
            } catch (UnsupportedMessageException var11) {
               var6 = asIntUnsupported(context, receiver);
            }
         } catch (Throwable var12) {
            throw guestToHostException(context, var12, true);
         } finally {
            hostLeave(context, c);
         }

         return var6;
      }

      @Override
      public boolean isBoolean(Object languageContext, Object receiver) {
         PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;
         Object c = hostEnter(context);

         boolean e;
         try {
            e = InteropLibrary.getFactory().getUncached().isBoolean(receiver);
         } catch (Throwable var9) {
            throw guestToHostException(context, var9, true);
         } finally {
            hostLeave(context, c);
         }

         return e;
      }

      @Override
      public boolean asBoolean(Object languageContext, Object receiver) {
         PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;
         Object c = hostEnter(context);

         boolean var6;
         try {
            try {
               return InteropLibrary.getFactory().getUncached().asBoolean(receiver);
            } catch (UnsupportedMessageException var11) {
               var6 = asBooleanUnsupported(context, receiver);
            }
         } catch (Throwable var12) {
            throw guestToHostException(context, var12, true);
         } finally {
            hostLeave(context, c);
         }

         return var6;
      }

      @Override
      public boolean fitsInFloat(Object languageContext, Object receiver) {
         PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;
         Object c = hostEnter(context);

         boolean e;
         try {
            e = InteropLibrary.getFactory().getUncached().fitsInFloat(receiver);
         } catch (Throwable var9) {
            throw guestToHostException(context, var9, true);
         } finally {
            hostLeave(context, c);
         }

         return e;
      }

      @Override
      public float asFloat(Object languageContext, Object receiver) {
         PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;
         Object c = hostEnter(context);

         float var6;
         try {
            try {
               return UNCACHED_INTEROP.asFloat(receiver);
            } catch (UnsupportedMessageException var11) {
               var6 = asFloatUnsupported(context, receiver);
            }
         } catch (Throwable var12) {
            throw guestToHostException(context, var12, true);
         } finally {
            hostLeave(context, c);
         }

         return var6;
      }

      @Override
      public boolean fitsInDouble(Object languageContext, Object receiver) {
         PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;
         Object c = hostEnter(context);

         boolean e;
         try {
            e = UNCACHED_INTEROP.fitsInDouble(receiver);
         } catch (Throwable var9) {
            throw guestToHostException(context, var9, true);
         } finally {
            hostLeave(context, c);
         }

         return e;
      }

      @Override
      public double asDouble(Object languageContext, Object receiver) {
         PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;
         Object c = hostEnter(context);

         double var6;
         try {
            try {
               return UNCACHED_INTEROP.asDouble(receiver);
            } catch (UnsupportedMessageException var12) {
               var6 = asDoubleUnsupported(context, receiver);
            }
         } catch (Throwable var13) {
            throw guestToHostException(context, var13, true);
         } finally {
            hostLeave(context, c);
         }

         return var6;
      }

      @Override
      public boolean fitsInLong(Object languageContext, Object receiver) {
         PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;
         Object c = hostEnter(context);

         boolean e;
         try {
            e = UNCACHED_INTEROP.fitsInLong(receiver);
         } catch (Throwable var9) {
            throw guestToHostException(context, var9, true);
         } finally {
            hostLeave(context, c);
         }

         return e;
      }

      @Override
      public long asLong(Object languageContext, Object receiver) {
         PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;
         Object c = hostEnter(context);

         long var6;
         try {
            try {
               return UNCACHED_INTEROP.asLong(receiver);
            } catch (UnsupportedMessageException var12) {
               var6 = asLongUnsupported(context, receiver);
            }
         } catch (Throwable var13) {
            throw guestToHostException(context, var13, true);
         } finally {
            hostLeave(context, c);
         }

         return var6;
      }

      @Override
      public boolean fitsInShort(Object languageContext, Object receiver) {
         PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;
         Object c = hostEnter(context);

         boolean e;
         try {
            e = UNCACHED_INTEROP.fitsInShort(receiver);
         } catch (Throwable var9) {
            throw guestToHostException(context, var9, true);
         } finally {
            hostLeave(context, c);
         }

         return e;
      }

      @Override
      public short asShort(Object languageContext, Object receiver) {
         PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;
         Object c = hostEnter(context);

         short var6;
         try {
            try {
               return UNCACHED_INTEROP.asShort(receiver);
            } catch (UnsupportedMessageException var11) {
               var6 = asShortUnsupported(context, receiver);
            }
         } catch (Throwable var12) {
            throw guestToHostException(context, var12, true);
         } finally {
            hostLeave(context, c);
         }

         return var6;
      }

      @Override
      public boolean isMetaObject(Object languageContext, Object receiver) {
         return (Boolean)EngineAccessor.RUNTIME.callProfiled(this.isMetaObject, languageContext, receiver);
      }

      @Override
      public boolean isMetaInstance(Object languageContext, Object receiver, Object instance) {
         return (Boolean)EngineAccessor.RUNTIME.callProfiled(this.isMetaInstance, languageContext, receiver, instance);
      }

      @Override
      public String getMetaQualifiedName(Object languageContext, Object receiver) {
         return (String)EngineAccessor.RUNTIME.callProfiled(this.getMetaQualifiedName, languageContext, receiver);
      }

      @Override
      public String getMetaSimpleName(Object languageContext, Object receiver) {
         return (String)EngineAccessor.RUNTIME.callProfiled(this.getMetaSimpleName, languageContext, receiver);
      }

      @Override
      public boolean hasMetaParents(Object languageContext, Object receiver) {
         return (Boolean)EngineAccessor.RUNTIME.callProfiled(this.hasMetaParents, languageContext, receiver);
      }

      @Override
      public Value getMetaParents(Object languageContext, Object receiver) {
         return (Value)EngineAccessor.RUNTIME.callProfiled(this.getMetaParents, languageContext, receiver);
      }

      @Override
      public boolean hasIterator(Object languageContext, Object receiver) {
         return (Boolean)EngineAccessor.RUNTIME.callProfiled(this.hasIterator, languageContext, receiver);
      }

      @Override
      public Value getIterator(Object languageContext, Object receiver) {
         return (Value)EngineAccessor.RUNTIME.callProfiled(this.getIterator, languageContext, receiver);
      }

      @Override
      public boolean isIterator(Object languageContext, Object receiver) {
         return (Boolean)EngineAccessor.RUNTIME.callProfiled(this.isIterator, languageContext, receiver);
      }

      @Override
      public boolean hasIteratorNextElement(Object languageContext, Object receiver) {
         return (Boolean)EngineAccessor.RUNTIME.callProfiled(this.hasIteratorNextElement, languageContext, receiver);
      }

      @Override
      public Value getIteratorNextElement(Object languageContext, Object receiver) {
         return (Value)EngineAccessor.RUNTIME.callProfiled(this.getIteratorNextElement, languageContext, receiver);
      }

      @Override
      public boolean hasHashEntries(Object languageContext, Object receiver) {
         return (Boolean)EngineAccessor.RUNTIME.callProfiled(this.hasHashEntries, languageContext, receiver);
      }

      @Override
      public long getHashSize(Object languageContext, Object receiver) {
         return (Long)EngineAccessor.RUNTIME.callProfiled(this.getHashSize, languageContext, receiver);
      }

      @Override
      public boolean hasHashEntry(Object languageContext, Object receiver, Object key) {
         return (Boolean)EngineAccessor.RUNTIME.callProfiled(this.hasHashEntry, languageContext, receiver, key);
      }

      @Override
      public Value getHashValue(Object languageContext, Object receiver, Object key) {
         return (Value)EngineAccessor.RUNTIME.callProfiled(this.getHashValue, languageContext, receiver, key);
      }

      @Override
      public Value getHashValueOrDefault(Object languageContext, Object receiver, Object key, Object defaultValue) {
         return (Value)EngineAccessor.RUNTIME.callProfiled(this.getHashValueOrDefault, languageContext, receiver, key, defaultValue);
      }

      @Override
      public void putHashEntry(Object languageContext, Object receiver, Object key, Object value) {
         EngineAccessor.RUNTIME.callProfiled(this.putHashEntry, languageContext, receiver, key, value);
      }

      @Override
      public boolean removeHashEntry(Object languageContext, Object receiver, Object key) {
         return (Boolean)EngineAccessor.RUNTIME.callProfiled(this.removeHashEntry, languageContext, receiver, key);
      }

      @Override
      public Value getHashEntriesIterator(Object languageContext, Object receiver) {
         return (Value)EngineAccessor.RUNTIME.callProfiled(this.getHashEntriesIterator, languageContext, receiver);
      }

      @Override
      public Value getHashKeysIterator(Object languageContext, Object receiver) {
         return (Value)EngineAccessor.RUNTIME.callProfiled(this.getHashKeysIterator, languageContext, receiver);
      }

      @Override
      public Value getHashValuesIterator(Object languageContext, Object receiver) {
         return (Value)EngineAccessor.RUNTIME.callProfiled(this.getHashValuesIterator, languageContext, receiver);
      }

      private abstract static class AbstractExecuteNode extends PolyglotValueDispatch.InteropNode {
         @Node.Child
         private InteropLibrary executables = InteropLibrary.getFactory().createDispatched(5);
         @Node.Child
         private PolyglotLanguageContext.ToGuestValuesNode toGuestValues = PolyglotLanguageContext.ToGuestValuesNode.create();
         private final BranchProfile invalidArgument = BranchProfile.create();
         private final BranchProfile arity = BranchProfile.create();
         private final BranchProfile unsupported = BranchProfile.create();

         protected AbstractExecuteNode(PolyglotValueDispatch.InteropValue interop) {
            super(interop);
         }

         protected final Object executeShared(PolyglotLanguageContext context, Object receiver, Object[] args) {
            Object[] guestArguments = this.toGuestValues.apply(context, args);

            try {
               return this.executables.execute(receiver, guestArguments);
            } catch (UnsupportedTypeException var6) {
               this.invalidArgument.enter();
               throw PolyglotValueDispatch.invalidExecuteArgumentType(context, receiver, var6);
            } catch (ArityException var7) {
               this.arity.enter();
               throw PolyglotValueDispatch.invalidExecuteArity(
                  context, receiver, guestArguments, var7.getExpectedMinArity(), var7.getExpectedMaxArity(), var7.getActualArity()
               );
            } catch (UnsupportedMessageException var8) {
               this.unsupported.enter();
               throw PolyglotValueDispatch.executeUnsupported(context, receiver);
            }
         }
      }

      private abstract static class AbstractInvokeNode extends PolyglotValueDispatch.InteropNode {
         @Node.Child
         private InteropLibrary objects = InteropLibrary.getFactory().createDispatched(5);
         private final PolyglotLanguageContext.ToHostValueNode toHostValue;
         private final BranchProfile invalidArgument = BranchProfile.create();
         private final BranchProfile arity = BranchProfile.create();
         private final BranchProfile unsupported = BranchProfile.create();
         private final BranchProfile unknownIdentifier = BranchProfile.create();

         protected AbstractInvokeNode(PolyglotValueDispatch.InteropValue interop) {
            super(interop);
            this.toHostValue = PolyglotLanguageContext.ToHostValueNode.create(interop.impl);
         }

         protected final Object executeShared(PolyglotLanguageContext context, Object receiver, String key, Object[] guestArguments) {
            try {
               return this.toHostValue.execute(context, this.objects.invokeMember(receiver, key, guestArguments));
            } catch (UnsupportedMessageException var6) {
               this.unsupported.enter();
               throw PolyglotValueDispatch.invokeUnsupported(context, receiver, key);
            } catch (UnknownIdentifierException var7) {
               this.unknownIdentifier.enter();
               throw PolyglotValueDispatch.nonReadableMemberKey(context, receiver, key);
            } catch (UnsupportedTypeException var8) {
               this.invalidArgument.enter();
               throw PolyglotValueDispatch.invalidInvokeArgumentType(context, receiver, key, var8);
            } catch (ArityException var9) {
               this.arity.enter();
               throw PolyglotValueDispatch.invalidInvokeArity(
                  context, receiver, key, guestArguments, var9.getExpectedMinArity(), var9.getExpectedMaxArity(), var9.getActualArity()
               );
            }
         }
      }

      private abstract static class AbstractMemberInfoNode extends PolyglotValueDispatch.InteropNode {
         protected AbstractMemberInfoNode(PolyglotValueDispatch.InteropValue interop) {
            super(interop);
         }

         @Override
         protected final Class<?>[] getArgumentTypes() {
            return new Class[]{PolyglotLanguageContext.class, this.polyglot.receiverType, String.class};
         }
      }

      private static class AsClassLiteralNode extends PolyglotValueDispatch.InteropNode {
         @Node.Child
         PolyglotToHostNode toHost = PolyglotToHostNodeGen.create();

         protected AsClassLiteralNode(PolyglotValueDispatch.InteropValue interop) {
            super(interop);
         }

         @Override
         protected Class<?>[] getArgumentTypes() {
            return new Class[]{PolyglotLanguageContext.class, this.polyglot.receiverType, Class.class};
         }

         @Override
         protected String getOperationName() {
            return "as";
         }

         @Override
         protected Object executeImpl(PolyglotLanguageContext context, Object receiver, Object[] args) {
            return this.toHost.execute(context, receiver, (Class<?>)args[2], null);
         }
      }

      abstract static class AsDateNode extends PolyglotValueDispatch.InteropNode {
         protected AsDateNode(PolyglotValueDispatch.InteropValue interop) {
            super(interop);
         }

         @Override
         protected Class<?>[] getArgumentTypes() {
            return new Class[]{PolyglotLanguageContext.class, this.polyglot.receiverType};
         }

         @Override
         protected String getOperationName() {
            return "asDate";
         }

         @Specialization(limit = "CACHE_LIMIT")
         static Object doCached(
            PolyglotLanguageContext context,
            Object receiver,
            Object[] args,
            @CachedLibrary("receiver") InteropLibrary objects,
            @Cached BranchProfile unsupported
         ) {
            try {
               return objects.asDate(receiver);
            } catch (UnsupportedMessageException var6) {
               unsupported.enter();
               if (objects.isNull(receiver)) {
                  return null;
               } else {
                  throw PolyglotValueDispatch.cannotConvert(context, receiver, null, "asDate()", "isDate()", "Value does not contain date information.");
               }
            }
         }
      }

      abstract static class AsDurationNode extends PolyglotValueDispatch.InteropNode {
         protected AsDurationNode(PolyglotValueDispatch.InteropValue interop) {
            super(interop);
         }

         @Override
         protected Class<?>[] getArgumentTypes() {
            return new Class[]{PolyglotLanguageContext.class, this.polyglot.receiverType};
         }

         @Override
         protected String getOperationName() {
            return "asDuration";
         }

         @Specialization(limit = "CACHE_LIMIT")
         static Object doCached(
            PolyglotLanguageContext context,
            Object receiver,
            Object[] args,
            @CachedLibrary("receiver") InteropLibrary objects,
            @Cached BranchProfile unsupported
         ) {
            try {
               return objects.asDuration(receiver);
            } catch (UnsupportedMessageException var6) {
               unsupported.enter();
               if (objects.isNull(receiver)) {
                  return null;
               } else {
                  throw PolyglotValueDispatch.cannotConvert(
                     context, receiver, null, "asDuration()", "isDuration()", "Value does not contain duration information."
                  );
               }
            }
         }
      }

      abstract static class AsInstantNode extends PolyglotValueDispatch.InteropNode {
         protected AsInstantNode(PolyglotValueDispatch.InteropValue interop) {
            super(interop);
         }

         @Override
         protected Class<?>[] getArgumentTypes() {
            return new Class[]{PolyglotLanguageContext.class, this.polyglot.receiverType};
         }

         @Override
         protected String getOperationName() {
            return "getInstant";
         }

         @Specialization(limit = "CACHE_LIMIT")
         static Object doCached(
            PolyglotLanguageContext context,
            Object receiver,
            Object[] args,
            @CachedLibrary("receiver") InteropLibrary objects,
            @Cached BranchProfile unsupported
         ) {
            try {
               return objects.asInstant(receiver);
            } catch (UnsupportedMessageException var6) {
               unsupported.enter();
               if (objects.isNull(receiver)) {
                  return null;
               } else {
                  throw PolyglotValueDispatch.cannotConvert(
                     context, receiver, null, "asInstant()", "hasInstant()", "Value does not contain instant information."
                  );
               }
            }
         }
      }

      abstract static class AsNativePointerNode extends PolyglotValueDispatch.InteropNode {
         protected AsNativePointerNode(PolyglotValueDispatch.InteropValue interop) {
            super(interop);
         }

         @Override
         protected Class<?>[] getArgumentTypes() {
            return new Class[]{PolyglotLanguageContext.class, this.polyglot.receiverType};
         }

         @Override
         protected String getOperationName() {
            return "asNativePointer";
         }

         @Specialization(limit = "CACHE_LIMIT")
         static Object doCached(
            PolyglotLanguageContext context,
            Object receiver,
            Object[] args,
            @CachedLibrary("receiver") InteropLibrary natives,
            @Cached BranchProfile unsupported
         ) {
            try {
               return natives.asPointer(receiver);
            } catch (UnsupportedMessageException var6) {
               unsupported.enter();
               throw PolyglotValueDispatch.cannotConvert(
                  context, receiver, long.class, "asNativePointer()", "isNativeObject()", "Value cannot be converted to a native pointer."
               );
            }
         }
      }

      abstract static class AsTimeNode extends PolyglotValueDispatch.InteropNode {
         protected AsTimeNode(PolyglotValueDispatch.InteropValue interop) {
            super(interop);
         }

         @Override
         protected Class<?>[] getArgumentTypes() {
            return new Class[]{PolyglotLanguageContext.class, this.polyglot.receiverType};
         }

         @Override
         protected String getOperationName() {
            return "asTime";
         }

         @Specialization(limit = "CACHE_LIMIT")
         static Object doCached(
            PolyglotLanguageContext context,
            Object receiver,
            Object[] args,
            @CachedLibrary("receiver") InteropLibrary objects,
            @Cached BranchProfile unsupported
         ) {
            try {
               return objects.asTime(receiver);
            } catch (UnsupportedMessageException var6) {
               unsupported.enter();
               if (objects.isNull(receiver)) {
                  return null;
               } else {
                  throw PolyglotValueDispatch.cannotConvert(context, receiver, null, "asTime()", "isTime()", "Value does not contain time information.");
               }
            }
         }
      }

      abstract static class AsTimeZoneNode extends PolyglotValueDispatch.InteropNode {
         protected AsTimeZoneNode(PolyglotValueDispatch.InteropValue interop) {
            super(interop);
         }

         @Override
         protected Class<?>[] getArgumentTypes() {
            return new Class[]{PolyglotLanguageContext.class, this.polyglot.receiverType};
         }

         @Override
         protected String getOperationName() {
            return "asTimeZone";
         }

         @Specialization(limit = "CACHE_LIMIT")
         static Object doCached(
            PolyglotLanguageContext context,
            Object receiver,
            Object[] args,
            @CachedLibrary("receiver") InteropLibrary objects,
            @Cached BranchProfile unsupported
         ) {
            try {
               return objects.asTimeZone(receiver);
            } catch (UnsupportedMessageException var6) {
               unsupported.enter();
               if (objects.isNull(receiver)) {
                  return null;
               } else {
                  throw PolyglotValueDispatch.cannotConvert(
                     context, receiver, null, "asTimeZone()", "isTimeZone()", "Value does not contain time-zone information."
                  );
               }
            }
         }
      }

      private static class AsTypeLiteralNode extends PolyglotValueDispatch.InteropNode {
         @Node.Child
         PolyglotToHostNode toHost = PolyglotToHostNodeGen.create();

         protected AsTypeLiteralNode(PolyglotValueDispatch.InteropValue interop) {
            super(interop);
         }

         @Override
         protected Class<?>[] getArgumentTypes() {
            return new Class[]{PolyglotLanguageContext.class, this.polyglot.receiverType, TypeLiteral.class};
         }

         @Override
         protected String getOperationName() {
            return "as";
         }

         @Override
         protected Object executeImpl(PolyglotLanguageContext context, Object receiver, Object[] args) {
            TypeLiteral<?> typeLiteral = (TypeLiteral<?>)args[2];
            return this.toHost.execute(context, receiver, typeLiteral.getRawType(), typeLiteral.getType());
         }
      }

      abstract static class CanExecuteNode extends PolyglotValueDispatch.InteropNode {
         protected CanExecuteNode(PolyglotValueDispatch.InteropValue interop) {
            super(interop);
         }

         @Override
         protected String getOperationName() {
            return "canExecute";
         }

         @Override
         protected Class<?>[] getArgumentTypes() {
            return new Class[]{PolyglotLanguageContext.class, this.polyglot.receiverType};
         }

         @Specialization(limit = "CACHE_LIMIT")
         static Object doCached(PolyglotLanguageContext context, Object receiver, Object[] args, @CachedLibrary("receiver") InteropLibrary executables) {
            return executables.isExecutable(receiver);
         }
      }

      abstract static class CanInstantiateNode extends PolyglotValueDispatch.InteropNode {
         protected CanInstantiateNode(PolyglotValueDispatch.InteropValue interop) {
            super(interop);
         }

         @Override
         protected Class<?>[] getArgumentTypes() {
            return new Class[]{PolyglotLanguageContext.class, this.polyglot.receiverType};
         }

         @Override
         protected String getOperationName() {
            return "canInstantiate";
         }

         @Specialization(limit = "CACHE_LIMIT")
         static Object doCached(PolyglotLanguageContext context, Object receiver, Object[] args, @CachedLibrary("receiver") InteropLibrary instantiables) {
            return instantiables.isInstantiable(receiver);
         }
      }

      abstract static class CanInvokeNode extends PolyglotValueDispatch.InteropValue.AbstractMemberInfoNode {
         protected CanInvokeNode(PolyglotValueDispatch.InteropValue interop) {
            super(interop);
         }

         @Override
         protected String getOperationName() {
            return "canInvoke";
         }

         @Specialization(limit = "CACHE_LIMIT")
         static Object doCached(PolyglotLanguageContext context, Object receiver, Object[] args, @CachedLibrary("receiver") InteropLibrary objects) {
            String key = (String)args[2];
            return objects.isMemberInvocable(receiver, key);
         }
      }

      private static class ExecuteNoArgsNode extends PolyglotValueDispatch.InteropValue.AbstractExecuteNode {
         private final PolyglotLanguageContext.ToHostValueNode toHostValue;

         protected ExecuteNoArgsNode(PolyglotValueDispatch.InteropValue interop) {
            super(interop);
            this.toHostValue = PolyglotLanguageContext.ToHostValueNode.create(interop.impl);
         }

         @Override
         protected Class<?>[] getArgumentTypes() {
            return new Class[]{PolyglotLanguageContext.class, this.polyglot.receiverType};
         }

         @Override
         protected Object executeImpl(PolyglotLanguageContext context, Object receiver, Object[] args) {
            return this.toHostValue.execute(context, this.executeShared(context, receiver, PolyglotValueDispatch.InteropValue.ExecuteVoidNoArgsNode.NO_ARGS));
         }

         @Override
         protected String getOperationName() {
            return "execute";
         }
      }

      private static class ExecuteNode extends PolyglotValueDispatch.InteropValue.AbstractExecuteNode {
         private final PolyglotLanguageContext.ToHostValueNode toHostValue;

         protected ExecuteNode(PolyglotValueDispatch.InteropValue interop) {
            super(interop);
            this.toHostValue = PolyglotLanguageContext.ToHostValueNode.create(interop.impl);
         }

         @Override
         protected Class<?>[] getArgumentTypes() {
            return new Class[]{PolyglotLanguageContext.class, this.polyglot.receiverType, Object[].class};
         }

         @Override
         protected Object executeImpl(PolyglotLanguageContext context, Object receiver, Object[] args) {
            return this.toHostValue.execute(context, this.executeShared(context, receiver, (Object[])args[2]));
         }

         @Override
         protected String getOperationName() {
            return "execute";
         }
      }

      private static class ExecuteVoidNoArgsNode extends PolyglotValueDispatch.InteropValue.AbstractExecuteNode {
         private static final Object[] NO_ARGS = new Object[0];

         protected ExecuteVoidNoArgsNode(PolyglotValueDispatch.InteropValue interop) {
            super(interop);
         }

         @Override
         protected Class<?>[] getArgumentTypes() {
            return new Class[]{PolyglotLanguageContext.class, this.polyglot.receiverType};
         }

         @Override
         protected Object executeImpl(PolyglotLanguageContext context, Object receiver, Object[] args) {
            this.executeShared(context, receiver, NO_ARGS);
            return null;
         }

         @Override
         protected String getOperationName() {
            return "executeVoid";
         }
      }

      private static class ExecuteVoidNode extends PolyglotValueDispatch.InteropValue.AbstractExecuteNode {
         protected ExecuteVoidNode(PolyglotValueDispatch.InteropValue interop) {
            super(interop);
         }

         @Override
         protected Class<?>[] getArgumentTypes() {
            return new Class[]{PolyglotLanguageContext.class, this.polyglot.receiverType, Object[].class};
         }

         @Override
         protected Object executeImpl(PolyglotLanguageContext context, Object receiver, Object[] args) {
            this.executeShared(context, receiver, (Object[])args[2]);
            return null;
         }

         @Override
         protected String getOperationName() {
            return "executeVoid";
         }
      }

      abstract static class GetArrayElementNode extends PolyglotValueDispatch.InteropNode {
         protected GetArrayElementNode(PolyglotValueDispatch.InteropValue interop) {
            super(interop);
         }

         @Override
         protected Class<?>[] getArgumentTypes() {
            return new Class[]{PolyglotLanguageContext.class, this.polyglot.receiverType, Long.class};
         }

         @Override
         protected String getOperationName() {
            return "getArrayElement";
         }

         @Specialization(limit = "CACHE_LIMIT")
         static Object doCached(
            PolyglotLanguageContext context,
            Object receiver,
            Object[] args,
            @CachedLibrary("receiver") InteropLibrary arrays,
            @Cached("createToHost()") PolyglotLanguageContext.ToHostValueNode toHost,
            @Cached BranchProfile unsupported,
            @Cached BranchProfile unknown
         ) {
            long index = (Long)args[2];

            try {
               return toHost.execute(context, arrays.readArrayElement(receiver, index));
            } catch (UnsupportedMessageException var10) {
               unsupported.enter();
               return PolyglotValueDispatch.getArrayElementUnsupported(context, receiver);
            } catch (InvalidArrayIndexException var11) {
               unknown.enter();
               throw PolyglotValueDispatch.invalidArrayIndex(context, receiver, index);
            }
         }
      }

      abstract static class GetArraySizeNode extends PolyglotValueDispatch.InteropNode {
         protected GetArraySizeNode(PolyglotValueDispatch.InteropValue interop) {
            super(interop);
         }

         @Override
         protected Class<?>[] getArgumentTypes() {
            return new Class[]{PolyglotLanguageContext.class, this.polyglot.receiverType};
         }

         @Override
         protected String getOperationName() {
            return "getArraySize";
         }

         @Specialization(limit = "CACHE_LIMIT")
         static Object doCached(
            PolyglotLanguageContext context,
            Object receiver,
            Object[] args,
            @CachedLibrary("receiver") InteropLibrary arrays,
            @Cached BranchProfile unsupported
         ) {
            try {
               return arrays.getArraySize(receiver);
            } catch (UnsupportedMessageException var6) {
               unsupported.enter();
               return PolyglotValueDispatch.getArraySizeUnsupported(context, receiver);
            }
         }
      }

      abstract static class GetBufferSizeNode extends PolyglotValueDispatch.InteropNode {
         protected GetBufferSizeNode(PolyglotValueDispatch.InteropValue interop) {
            super(interop);
         }

         @Override
         protected Class<?>[] getArgumentTypes() {
            return new Class[]{PolyglotLanguageContext.class, this.polyglot.receiverType};
         }

         @Override
         protected String getOperationName() {
            return "getBufferSize";
         }

         @Specialization(limit = "CACHE_LIMIT")
         static Object doCached(
            PolyglotLanguageContext context,
            Object receiver,
            Object[] args,
            @CachedLibrary("receiver") InteropLibrary buffers,
            @Cached BranchProfile unsupported
         ) {
            try {
               return buffers.getBufferSize(receiver);
            } catch (UnsupportedMessageException var6) {
               unsupported.enter();
               throw PolyglotValueDispatch.getBufferSizeUnsupported(context, receiver);
            }
         }
      }

      abstract static class GetHashEntriesIteratorNode extends PolyglotValueDispatch.InteropNode {
         GetHashEntriesIteratorNode(PolyglotValueDispatch.InteropValue interop) {
            super(interop);
         }

         @Override
         protected Class<?>[] getArgumentTypes() {
            return new Class[]{PolyglotLanguageContext.class, this.polyglot.receiverType};
         }

         @Override
         protected String getOperationName() {
            return "getHashEntriesIterator";
         }

         @Specialization(limit = "CACHE_LIMIT")
         static Object doCached(
            PolyglotLanguageContext context,
            Object receiver,
            Object[] args,
            @CachedLibrary("receiver") InteropLibrary hashes,
            @Cached("createToHost()") PolyglotLanguageContext.ToHostValueNode toHost,
            @Cached BranchProfile unsupported
         ) {
            try {
               return toHost.execute(context, hashes.getHashEntriesIterator(receiver));
            } catch (UnsupportedMessageException var7) {
               unsupported.enter();
               throw PolyglotValueDispatch.getHashEntriesIteratorUnsupported(context, receiver);
            }
         }
      }

      abstract static class GetHashKeysIteratorNode extends PolyglotValueDispatch.InteropNode {
         GetHashKeysIteratorNode(PolyglotValueDispatch.InteropValue interop) {
            super(interop);
         }

         @Override
         protected Class<?>[] getArgumentTypes() {
            return new Class[]{PolyglotLanguageContext.class, this.polyglot.receiverType};
         }

         @Override
         protected String getOperationName() {
            return "getHashKeysIterator";
         }

         @Specialization(limit = "CACHE_LIMIT")
         static Object doCached(
            PolyglotLanguageContext context,
            Object receiver,
            Object[] args,
            @CachedLibrary("receiver") InteropLibrary hashes,
            @Cached("createToHost()") PolyglotLanguageContext.ToHostValueNode toHost,
            @Cached BranchProfile unsupported
         ) {
            try {
               return toHost.execute(context, hashes.getHashKeysIterator(receiver));
            } catch (UnsupportedMessageException var7) {
               unsupported.enter();
               throw PolyglotValueDispatch.getHashEntriesIteratorUnsupported(context, receiver);
            }
         }
      }

      abstract static class GetHashSizeNode extends PolyglotValueDispatch.InteropNode {
         protected GetHashSizeNode(PolyglotValueDispatch.InteropValue interop) {
            super(interop);
         }

         @Override
         protected Class<?>[] getArgumentTypes() {
            return new Class[]{PolyglotLanguageContext.class, this.polyglot.receiverType};
         }

         @Override
         protected String getOperationName() {
            return "getHashSize";
         }

         @Specialization(limit = "CACHE_LIMIT")
         static Object doCached(
            PolyglotLanguageContext context,
            Object receiver,
            Object[] args,
            @CachedLibrary("receiver") InteropLibrary hashes,
            @Cached BranchProfile unsupported
         ) {
            try {
               return hashes.getHashSize(receiver);
            } catch (UnsupportedMessageException var6) {
               unsupported.enter();
               throw PolyglotValueDispatch.getHashSizeUnsupported(context, receiver);
            }
         }
      }

      abstract static class GetHashValueNode extends PolyglotValueDispatch.InteropNode {
         protected GetHashValueNode(PolyglotValueDispatch.InteropValue interop) {
            super(interop);
         }

         @Override
         protected Class<?>[] getArgumentTypes() {
            return new Class[]{PolyglotLanguageContext.class, this.polyglot.receiverType, Object.class};
         }

         @Override
         protected String getOperationName() {
            return "getHashValue";
         }

         @Specialization(limit = "CACHE_LIMIT")
         static Object doCached(
            PolyglotLanguageContext context,
            Object receiver,
            Object[] args,
            @CachedLibrary("receiver") InteropLibrary hashes,
            @Cached PolyglotLanguageContext.ToGuestValueNode toGuestKey,
            @Cached("createToHost()") PolyglotLanguageContext.ToHostValueNode toHost,
            @Cached BranchProfile unsupported,
            @Cached BranchProfile invalidKey
         ) {
            Object hostKey = args[2];
            Object key = toGuestKey.execute(context, hostKey);

            try {
               return toHost.execute(context, hashes.readHashValue(receiver, key));
            } catch (UnsupportedMessageException var11) {
               unsupported.enter();
               throw PolyglotValueDispatch.getHashValueUnsupported(context, receiver, key);
            } catch (UnknownKeyException var12) {
               invalidKey.enter();
               if (hashes.isHashEntryExisting(receiver, key)) {
                  throw PolyglotValueDispatch.getHashValueUnsupported(context, receiver, key);
               } else {
                  return null;
               }
            }
         }
      }

      abstract static class GetHashValueOrDefaultNode extends PolyglotValueDispatch.InteropNode {
         protected GetHashValueOrDefaultNode(PolyglotValueDispatch.InteropValue interop) {
            super(interop);
         }

         @Override
         protected Class<?>[] getArgumentTypes() {
            return new Class[]{PolyglotLanguageContext.class, this.polyglot.receiverType, Object.class, Object.class};
         }

         @Override
         protected String getOperationName() {
            return "getHashValueOrDefault";
         }

         @Specialization(limit = "CACHE_LIMIT")
         static Object doCached(
            PolyglotLanguageContext context,
            Object receiver,
            Object[] args,
            @CachedLibrary("receiver") InteropLibrary hashes,
            @Cached PolyglotLanguageContext.ToGuestValueNode toGuestKey,
            @Cached PolyglotLanguageContext.ToGuestValueNode toGuestDefaultValue,
            @Cached("createToHost()") PolyglotLanguageContext.ToHostValueNode toHost,
            @Cached BranchProfile unsupported,
            @Cached BranchProfile invalidKey
         ) {
            Object hostKey = args[2];
            Object hostDefaultValue = args[3];
            Object key = toGuestKey.execute(context, hostKey);
            Object defaultValue = toGuestDefaultValue.execute(context, hostDefaultValue);

            try {
               return toHost.execute(context, hashes.readHashValueOrDefault(receiver, key, hostDefaultValue));
            } catch (UnsupportedMessageException var14) {
               unsupported.enter();
               throw PolyglotValueDispatch.getHashValueUnsupported(context, receiver, key);
            }
         }
      }

      abstract static class GetHashValuesIteratorNode extends PolyglotValueDispatch.InteropNode {
         GetHashValuesIteratorNode(PolyglotValueDispatch.InteropValue interop) {
            super(interop);
         }

         @Override
         protected Class<?>[] getArgumentTypes() {
            return new Class[]{PolyglotLanguageContext.class, this.polyglot.receiverType};
         }

         @Override
         protected String getOperationName() {
            return "getHashValuesIterator";
         }

         @Specialization(limit = "CACHE_LIMIT")
         static Object doCached(
            PolyglotLanguageContext context,
            Object receiver,
            Object[] args,
            @CachedLibrary("receiver") InteropLibrary hashes,
            @Cached("createToHost()") PolyglotLanguageContext.ToHostValueNode toHost,
            @Cached BranchProfile unsupported
         ) {
            try {
               return toHost.execute(context, hashes.getHashValuesIterator(receiver));
            } catch (UnsupportedMessageException var7) {
               unsupported.enter();
               throw PolyglotValueDispatch.getHashEntriesIteratorUnsupported(context, receiver);
            }
         }
      }

      abstract static class GetIteratorNextElementNode extends PolyglotValueDispatch.InteropNode {
         protected GetIteratorNextElementNode(PolyglotValueDispatch.InteropValue interop) {
            super(interop);
         }

         @Override
         protected Class<?>[] getArgumentTypes() {
            return new Class[]{PolyglotLanguageContext.class, this.polyglot.receiverType};
         }

         @Override
         protected String getOperationName() {
            return "getIteratorNextElement";
         }

         @Specialization(limit = "CACHE_LIMIT")
         static Object doCached(
            PolyglotLanguageContext context,
            Object receiver,
            Object[] args,
            @CachedLibrary("receiver") InteropLibrary iterators,
            @Cached("createToHost()") PolyglotLanguageContext.ToHostValueNode toHost,
            @Cached BranchProfile unsupported,
            @Cached BranchProfile stop
         ) {
            try {
               return toHost.execute(context, iterators.getIteratorNextElement(receiver));
            } catch (UnsupportedMessageException var8) {
               unsupported.enter();
               throw PolyglotValueDispatch.nonReadableIteratorElement();
            } catch (StopIterationException var9) {
               stop.enter();
               throw PolyglotValueDispatch.stopIteration(context, receiver);
            }
         }
      }

      abstract static class GetIteratorNode extends PolyglotValueDispatch.InteropNode {
         protected GetIteratorNode(PolyglotValueDispatch.InteropValue interop) {
            super(interop);
         }

         @Override
         protected Class<?>[] getArgumentTypes() {
            return new Class[]{PolyglotLanguageContext.class, this.polyglot.receiverType};
         }

         @Override
         protected String getOperationName() {
            return "getIterator";
         }

         @Specialization(limit = "CACHE_LIMIT")
         static Object doCached(
            PolyglotLanguageContext context,
            Object receiver,
            Object[] args,
            @CachedLibrary("receiver") InteropLibrary iterators,
            @Cached("createToHost()") PolyglotLanguageContext.ToHostValueNode toHost,
            @Cached BranchProfile unsupported
         ) {
            try {
               return toHost.execute(context, iterators.getIterator(receiver));
            } catch (UnsupportedMessageException var7) {
               unsupported.enter();
               return PolyglotValueDispatch.getIteratorUnsupported(context, receiver);
            }
         }
      }

      abstract static class GetMemberKeysNode extends PolyglotValueDispatch.InteropNode {
         protected GetMemberKeysNode(PolyglotValueDispatch.InteropValue interop) {
            super(interop);
         }

         @Override
         protected Class<?>[] getArgumentTypes() {
            return new Class[]{PolyglotLanguageContext.class, this.polyglot.receiverType};
         }

         @Override
         protected String getOperationName() {
            return "getMemberKeys";
         }

         @Specialization(limit = "CACHE_LIMIT")
         static Object doCached(
            PolyglotLanguageContext context,
            Object receiver,
            Object[] args,
            @CachedLibrary("receiver") InteropLibrary objects,
            @Cached("createToHost()") PolyglotLanguageContext.ToHostValueNode toHost,
            @Cached BranchProfile unsupported
         ) {
            try {
               return toHost.execute(context, objects.getMembers(receiver));
            } catch (UnsupportedMessageException var7) {
               return null;
            }
         }
      }

      abstract static class GetMemberNode extends PolyglotValueDispatch.InteropNode {
         protected GetMemberNode(PolyglotValueDispatch.InteropValue interop) {
            super(interop);
         }

         @Override
         protected Class<?>[] getArgumentTypes() {
            return new Class[]{PolyglotLanguageContext.class, this.polyglot.receiverType, String.class};
         }

         @Override
         protected String getOperationName() {
            return "getMember";
         }

         @Specialization(limit = "CACHE_LIMIT")
         static Object doCached(
            PolyglotLanguageContext context,
            Object receiver,
            Object[] args,
            @CachedLibrary("receiver") InteropLibrary objects,
            @Cached("createToHost()") PolyglotLanguageContext.ToHostValueNode toHost,
            @Cached BranchProfile unsupported,
            @Cached BranchProfile unknown
         ) {
            String key = (String)args[2];

            Object value;
            try {
               assert key != null : "should be handled already";

               value = toHost.execute(context, objects.readMember(receiver, key));
            } catch (UnsupportedMessageException var10) {
               unsupported.enter();
               if (!objects.hasMembers(receiver)) {
                  return PolyglotValueDispatch.getMemberUnsupported(context, receiver, key);
               }

               value = null;
            } catch (UnknownIdentifierException var11) {
               unknown.enter();
               value = null;
            }

            return value;
         }
      }

      abstract static class GetMetaParentsNode extends PolyglotValueDispatch.InteropNode {
         protected GetMetaParentsNode(PolyglotValueDispatch.InteropValue interop) {
            super(interop);
         }

         @Override
         protected Class<?>[] getArgumentTypes() {
            return new Class[]{PolyglotLanguageContext.class, this.polyglot.receiverType};
         }

         @Override
         protected String getOperationName() {
            return "getMetaParents";
         }

         @Specialization(limit = "CACHE_LIMIT")
         static Object doCached(
            PolyglotLanguageContext context,
            Object receiver,
            Object[] args,
            @CachedLibrary("receiver") InteropLibrary objects,
            @Cached("createToHost()") PolyglotLanguageContext.ToHostValueNode toHost,
            @Cached BranchProfile unsupported
         ) {
            try {
               return toHost.execute(context, objects.getMetaParents(receiver));
            } catch (UnsupportedMessageException var7) {
               unsupported.enter();
               throw PolyglotValueDispatch.unsupported(context, receiver, "getMetaParents()", "hasMetaParents()");
            }
         }
      }

      abstract static class GetMetaQualifiedNameNode extends PolyglotValueDispatch.InteropNode {
         protected GetMetaQualifiedNameNode(PolyglotValueDispatch.InteropValue interop) {
            super(interop);
         }

         @Override
         protected Class<?>[] getArgumentTypes() {
            return new Class[]{PolyglotLanguageContext.class, this.polyglot.receiverType};
         }

         @Override
         protected String getOperationName() {
            return "getMetaQualifiedName";
         }

         @Specialization(limit = "CACHE_LIMIT")
         static String doCached(
            PolyglotLanguageContext context,
            Object receiver,
            Object[] args,
            @CachedLibrary("receiver") InteropLibrary objects,
            @CachedLibrary(limit = "1") InteropLibrary toString,
            @Cached BranchProfile unsupported
         ) {
            try {
               return toString.asString(objects.getMetaQualifiedName(receiver));
            } catch (UnsupportedMessageException var7) {
               unsupported.enter();
               throw PolyglotValueDispatch.unsupported(context, receiver, "getMetaQualifiedName()", "isMetaObject()");
            }
         }
      }

      abstract static class GetMetaSimpleNameNode extends PolyglotValueDispatch.InteropNode {
         protected GetMetaSimpleNameNode(PolyglotValueDispatch.InteropValue interop) {
            super(interop);
         }

         @Override
         protected Class<?>[] getArgumentTypes() {
            return new Class[]{PolyglotLanguageContext.class, this.polyglot.receiverType};
         }

         @Override
         protected String getOperationName() {
            return "getMetaSimpleName";
         }

         @Specialization(limit = "CACHE_LIMIT")
         static String doCached(
            PolyglotLanguageContext context,
            Object receiver,
            Object[] args,
            @CachedLibrary("receiver") InteropLibrary objects,
            @CachedLibrary(limit = "1") InteropLibrary toString,
            @Cached BranchProfile unsupported
         ) {
            try {
               return toString.asString(objects.getMetaSimpleName(receiver));
            } catch (UnsupportedMessageException var7) {
               unsupported.enter();
               throw PolyglotValueDispatch.unsupported(context, receiver, "getMetaSimpleName()", "isMetaObject()");
            }
         }
      }

      abstract static class HasArrayElementsNode extends PolyglotValueDispatch.InteropNode {
         protected HasArrayElementsNode(PolyglotValueDispatch.InteropValue interop) {
            super(interop);
         }

         @Override
         protected Class<?>[] getArgumentTypes() {
            return new Class[]{PolyglotLanguageContext.class, this.polyglot.receiverType};
         }

         @Override
         protected String getOperationName() {
            return "hasArrayElements";
         }

         @Specialization(limit = "CACHE_LIMIT")
         static Object doCached(PolyglotLanguageContext context, Object receiver, Object[] args, @CachedLibrary("receiver") InteropLibrary arrays) {
            return arrays.hasArrayElements(receiver);
         }
      }

      abstract static class HasBufferElementsNode extends PolyglotValueDispatch.InteropNode {
         protected HasBufferElementsNode(PolyglotValueDispatch.InteropValue interop) {
            super(interop);
         }

         @Override
         protected Class<?>[] getArgumentTypes() {
            return new Class[]{PolyglotLanguageContext.class, this.polyglot.receiverType};
         }

         @Override
         protected String getOperationName() {
            return "hasBufferElements";
         }

         @Specialization(limit = "CACHE_LIMIT")
         static Object doCached(PolyglotLanguageContext context, Object receiver, Object[] args, @CachedLibrary("receiver") InteropLibrary buffers) {
            return buffers.hasBufferElements(receiver);
         }
      }

      abstract static class HasHashEntriesNode extends PolyglotValueDispatch.InteropNode {
         protected HasHashEntriesNode(PolyglotValueDispatch.InteropValue interop) {
            super(interop);
         }

         @Override
         protected Class<?>[] getArgumentTypes() {
            return new Class[]{PolyglotLanguageContext.class, this.polyglot.receiverType};
         }

         @Override
         protected String getOperationName() {
            return "hasHashEntries";
         }

         @Specialization(limit = "CACHE_LIMIT")
         static Object doCached(PolyglotLanguageContext context, Object receiver, Object[] args, @CachedLibrary("receiver") InteropLibrary hashes) {
            return hashes.hasHashEntries(receiver);
         }
      }

      abstract static class HasHashEntryNode extends PolyglotValueDispatch.InteropNode {
         protected HasHashEntryNode(PolyglotValueDispatch.InteropValue interop) {
            super(interop);
         }

         @Override
         protected Class<?>[] getArgumentTypes() {
            return new Class[]{PolyglotLanguageContext.class, this.polyglot.receiverType, Object.class};
         }

         @Override
         protected String getOperationName() {
            return "hasHashEntry";
         }

         @Specialization(limit = "CACHE_LIMIT")
         static Object doCached(
            PolyglotLanguageContext context,
            Object receiver,
            Object[] args,
            @CachedLibrary("receiver") InteropLibrary hashes,
            @Cached PolyglotLanguageContext.ToGuestValueNode toGuestKey
         ) {
            Object hostKey = args[2];
            Object key = toGuestKey.execute(context, hostKey);
            return hashes.isHashEntryExisting(receiver, key);
         }
      }

      abstract static class HasIteratorNextElementNode extends PolyglotValueDispatch.InteropNode {
         protected HasIteratorNextElementNode(PolyglotValueDispatch.InteropValue interop) {
            super(interop);
         }

         @Override
         protected Class<?>[] getArgumentTypes() {
            return new Class[]{PolyglotLanguageContext.class, this.polyglot.receiverType};
         }

         @Override
         protected String getOperationName() {
            return "hasIteratorNextElement";
         }

         @Specialization(limit = "CACHE_LIMIT")
         static Object doCached(
            PolyglotLanguageContext context,
            Object receiver,
            Object[] args,
            @CachedLibrary("receiver") InteropLibrary iterators,
            @Cached BranchProfile unsupported
         ) {
            try {
               return iterators.hasIteratorNextElement(receiver);
            } catch (UnsupportedMessageException var6) {
               unsupported.enter();
               return PolyglotValueDispatch.hasIteratorNextElementUnsupported(context, receiver);
            }
         }
      }

      abstract static class HasIteratorNode extends PolyglotValueDispatch.InteropNode {
         protected HasIteratorNode(PolyglotValueDispatch.InteropValue interop) {
            super(interop);
         }

         @Override
         protected Class<?>[] getArgumentTypes() {
            return new Class[]{PolyglotLanguageContext.class, this.polyglot.receiverType};
         }

         @Override
         protected String getOperationName() {
            return "hasIterator";
         }

         @Specialization(limit = "CACHE_LIMIT")
         static Object doCached(PolyglotLanguageContext context, Object receiver, Object[] args, @CachedLibrary("receiver") InteropLibrary iterators) {
            return iterators.hasIterator(receiver);
         }
      }

      abstract static class HasMemberNode extends PolyglotValueDispatch.InteropValue.AbstractMemberInfoNode {
         protected HasMemberNode(PolyglotValueDispatch.InteropValue interop) {
            super(interop);
         }

         @Override
         protected String getOperationName() {
            return "hasMember";
         }

         @Specialization(limit = "CACHE_LIMIT")
         static Object doCached(PolyglotLanguageContext context, Object receiver, Object[] args, @CachedLibrary("receiver") InteropLibrary objects) {
            String key = (String)args[2];
            return objects.isMemberExisting(receiver, key);
         }
      }

      abstract static class HasMembersNode extends PolyglotValueDispatch.InteropNode {
         protected HasMembersNode(PolyglotValueDispatch.InteropValue interop) {
            super(interop);
         }

         @Override
         protected Class<?>[] getArgumentTypes() {
            return new Class[]{PolyglotLanguageContext.class, this.polyglot.receiverType};
         }

         @Override
         protected String getOperationName() {
            return "hasMembers";
         }

         @Specialization(limit = "CACHE_LIMIT")
         static Object doCached(PolyglotLanguageContext context, Object receiver, Object[] args, @CachedLibrary("receiver") InteropLibrary objects) {
            return objects.hasMembers(receiver);
         }
      }

      abstract static class HasMetaParentsNode extends PolyglotValueDispatch.InteropNode {
         protected HasMetaParentsNode(PolyglotValueDispatch.InteropValue interop) {
            super(interop);
         }

         @Override
         protected Class<?>[] getArgumentTypes() {
            return new Class[]{PolyglotLanguageContext.class, this.polyglot.receiverType};
         }

         @Override
         protected String getOperationName() {
            return "hasMetaParents";
         }

         @Specialization(limit = "CACHE_LIMIT")
         static boolean doCached(
            PolyglotLanguageContext context,
            Object receiver,
            Object[] args,
            @CachedLibrary("receiver") InteropLibrary objects,
            @Cached BranchProfile unsupported
         ) {
            return objects.hasMetaParents(receiver);
         }
      }

      private static class InvokeNoArgsNode extends PolyglotValueDispatch.InteropValue.AbstractInvokeNode {
         protected InvokeNoArgsNode(PolyglotValueDispatch.InteropValue interop) {
            super(interop);
         }

         @Override
         protected Class<?>[] getArgumentTypes() {
            return new Class[]{PolyglotLanguageContext.class, this.polyglot.receiverType, String.class};
         }

         @Override
         protected String getOperationName() {
            return "invoke";
         }

         @Override
         protected Object executeImpl(PolyglotLanguageContext context, Object receiver, Object[] args) {
            String key = (String)args[2];
            return this.executeShared(context, receiver, key, PolyglotValueDispatch.InteropValue.ExecuteVoidNoArgsNode.NO_ARGS);
         }
      }

      private static class InvokeNode extends PolyglotValueDispatch.InteropValue.AbstractInvokeNode {
         @Node.Child
         private PolyglotLanguageContext.ToGuestValuesNode toGuestValues = PolyglotLanguageContext.ToGuestValuesNode.create();

         protected InvokeNode(PolyglotValueDispatch.InteropValue interop) {
            super(interop);
         }

         @Override
         protected Class<?>[] getArgumentTypes() {
            return new Class[]{PolyglotLanguageContext.class, this.polyglot.receiverType, String.class, Object[].class};
         }

         @Override
         protected String getOperationName() {
            return "invoke";
         }

         @Override
         protected Object executeImpl(PolyglotLanguageContext context, Object receiver, Object[] args) {
            String key = (String)args[2];
            Object[] guestArguments = this.toGuestValues.apply(context, (Object[])args[3]);
            return this.executeShared(context, receiver, key, guestArguments);
         }
      }

      abstract static class IsBufferWritableNode extends PolyglotValueDispatch.InteropNode {
         protected IsBufferWritableNode(PolyglotValueDispatch.InteropValue interop) {
            super(interop);
         }

         @Override
         protected Class<?>[] getArgumentTypes() {
            return new Class[]{PolyglotLanguageContext.class, this.polyglot.receiverType};
         }

         @Override
         protected String getOperationName() {
            return "isBufferWritable";
         }

         @Specialization(limit = "CACHE_LIMIT")
         static Object doCached(
            PolyglotLanguageContext context,
            Object receiver,
            Object[] args,
            @CachedLibrary("receiver") InteropLibrary buffers,
            @Cached BranchProfile unsupported
         ) {
            try {
               return buffers.isBufferWritable(receiver);
            } catch (UnsupportedMessageException var6) {
               unsupported.enter();
               throw PolyglotValueDispatch.getBufferSizeUnsupported(context, receiver);
            }
         }
      }

      abstract static class IsDateNode extends PolyglotValueDispatch.InteropNode {
         protected IsDateNode(PolyglotValueDispatch.InteropValue interop) {
            super(interop);
         }

         @Override
         protected Class<?>[] getArgumentTypes() {
            return new Class[]{PolyglotLanguageContext.class, this.polyglot.receiverType};
         }

         @Override
         protected String getOperationName() {
            return "isDate";
         }

         @Specialization(limit = "CACHE_LIMIT")
         static Object doCached(PolyglotLanguageContext context, Object receiver, Object[] args, @CachedLibrary("receiver") InteropLibrary objects) {
            return objects.isDate(receiver);
         }
      }

      abstract static class IsDurationNode extends PolyglotValueDispatch.InteropNode {
         protected IsDurationNode(PolyglotValueDispatch.InteropValue interop) {
            super(interop);
         }

         @Override
         protected Class<?>[] getArgumentTypes() {
            return new Class[]{PolyglotLanguageContext.class, this.polyglot.receiverType};
         }

         @Override
         protected String getOperationName() {
            return "isDuration";
         }

         @Specialization(limit = "CACHE_LIMIT")
         static Object doCached(PolyglotLanguageContext context, Object receiver, Object[] args, @CachedLibrary("receiver") InteropLibrary objects) {
            return objects.isDuration(receiver);
         }
      }

      abstract static class IsExceptionNode extends PolyglotValueDispatch.InteropNode {
         protected IsExceptionNode(PolyglotValueDispatch.InteropValue interop) {
            super(interop);
         }

         @Override
         protected Class<?>[] getArgumentTypes() {
            return new Class[]{PolyglotLanguageContext.class, this.polyglot.receiverType};
         }

         @Override
         protected String getOperationName() {
            return "isException";
         }

         @Specialization(limit = "CACHE_LIMIT")
         static Object doCached(PolyglotLanguageContext context, Object receiver, Object[] args, @CachedLibrary("receiver") InteropLibrary objects) {
            return objects.isException(receiver);
         }
      }

      abstract static class IsIteratorNode extends PolyglotValueDispatch.InteropNode {
         protected IsIteratorNode(PolyglotValueDispatch.InteropValue interop) {
            super(interop);
         }

         @Override
         protected Class<?>[] getArgumentTypes() {
            return new Class[]{PolyglotLanguageContext.class, this.polyglot.receiverType};
         }

         @Override
         protected String getOperationName() {
            return "isIterator";
         }

         @Specialization(limit = "CACHE_LIMIT")
         static Object doCached(PolyglotLanguageContext context, Object receiver, Object[] args, @CachedLibrary("receiver") InteropLibrary iterators) {
            return iterators.isIterator(receiver);
         }
      }

      abstract static class IsMetaInstanceNode extends PolyglotValueDispatch.InteropNode {
         protected IsMetaInstanceNode(PolyglotValueDispatch.InteropValue interop) {
            super(interop);
         }

         @Override
         protected Class<?>[] getArgumentTypes() {
            return new Class[]{PolyglotLanguageContext.class, this.polyglot.receiverType, null};
         }

         @Override
         protected String getOperationName() {
            return "isMetaInstance";
         }

         @Specialization(limit = "CACHE_LIMIT")
         static boolean doCached(
            PolyglotLanguageContext context,
            Object receiver,
            Object[] args,
            @CachedLibrary("receiver") InteropLibrary objects,
            @Cached PolyglotLanguageContext.ToGuestValueNode toGuest,
            @Cached BranchProfile unsupported
         ) {
            try {
               return objects.isMetaInstance(receiver, toGuest.execute(context, args[2]));
            } catch (UnsupportedMessageException var7) {
               unsupported.enter();
               throw PolyglotValueDispatch.unsupported(context, receiver, "isMetaInstance()", "isMetaObject()");
            }
         }
      }

      abstract static class IsMetaObjectNode extends PolyglotValueDispatch.InteropNode {
         protected IsMetaObjectNode(PolyglotValueDispatch.InteropValue interop) {
            super(interop);
         }

         @Override
         protected Class<?>[] getArgumentTypes() {
            return new Class[]{PolyglotLanguageContext.class, this.polyglot.receiverType};
         }

         @Override
         protected String getOperationName() {
            return "isMetaObject";
         }

         @Specialization(limit = "CACHE_LIMIT")
         static boolean doCached(PolyglotLanguageContext context, Object receiver, Object[] args, @CachedLibrary("receiver") InteropLibrary objects) {
            return objects.isMetaObject(receiver);
         }
      }

      abstract static class IsNativePointerNode extends PolyglotValueDispatch.InteropNode {
         protected IsNativePointerNode(PolyglotValueDispatch.InteropValue interop) {
            super(interop);
         }

         @Override
         protected Class<?>[] getArgumentTypes() {
            return new Class[]{PolyglotLanguageContext.class, this.polyglot.receiverType};
         }

         @Override
         protected String getOperationName() {
            return "isNativePointer";
         }

         @Specialization(limit = "CACHE_LIMIT")
         static Object doCached(PolyglotLanguageContext context, Object receiver, Object[] args, @CachedLibrary("receiver") InteropLibrary natives) {
            return natives.isPointer(receiver);
         }
      }

      abstract static class IsNullNode extends PolyglotValueDispatch.InteropNode {
         protected IsNullNode(PolyglotValueDispatch.InteropValue interop) {
            super(interop);
         }

         @Override
         protected Class<?>[] getArgumentTypes() {
            return new Class[]{PolyglotLanguageContext.class, this.polyglot.receiverType};
         }

         @Override
         protected String getOperationName() {
            return "isNull";
         }

         @Specialization(limit = "CACHE_LIMIT")
         static Object doCached(PolyglotLanguageContext context, Object receiver, Object[] args, @CachedLibrary("receiver") InteropLibrary values) {
            return values.isNull(receiver);
         }
      }

      abstract static class IsTimeNode extends PolyglotValueDispatch.InteropNode {
         protected IsTimeNode(PolyglotValueDispatch.InteropValue interop) {
            super(interop);
         }

         @Override
         protected Class<?>[] getArgumentTypes() {
            return new Class[]{PolyglotLanguageContext.class, this.polyglot.receiverType};
         }

         @Override
         protected String getOperationName() {
            return "isTime";
         }

         @Specialization(limit = "CACHE_LIMIT")
         static Object doCached(PolyglotLanguageContext context, Object receiver, Object[] args, @CachedLibrary("receiver") InteropLibrary objects) {
            return objects.isTime(receiver);
         }
      }

      abstract static class IsTimeZoneNode extends PolyglotValueDispatch.InteropNode {
         protected IsTimeZoneNode(PolyglotValueDispatch.InteropValue interop) {
            super(interop);
         }

         @Override
         protected Class<?>[] getArgumentTypes() {
            return new Class[]{PolyglotLanguageContext.class, this.polyglot.receiverType};
         }

         @Override
         protected String getOperationName() {
            return "isTimeZone";
         }

         @Specialization(limit = "CACHE_LIMIT")
         static Object doCached(PolyglotLanguageContext context, Object receiver, Object[] args, @CachedLibrary("receiver") InteropLibrary objects) {
            return objects.isTimeZone(receiver);
         }
      }

      private final class MemberSet extends AbstractSet<String> {
         private final Object context;
         private final Object receiver;
         private final Value keys;
         private int cachedSize = -1;

         MemberSet(Object languageContext, Object receiver, Value keys) {
            this.context = languageContext;
            this.receiver = receiver;
            this.keys = keys;
         }

         @Override
         public boolean contains(Object o) {
            return !(o instanceof String) ? false : InteropValue.this.hasMember(this.context, this.receiver, (String)o);
         }

         @Override
         public Iterator<String> iterator() {
            return new Iterator<String>() {
               int index = 0;

               @Override
               public boolean hasNext() {
                  return this.index < MemberSet.this.size();
               }

               public String next() {
                  if (this.index >= MemberSet.this.size()) {
                     throw new NoSuchElementException();
                  } else {
                     Value arrayElement = MemberSet.this.keys.getArrayElement(this.index++);
                     return arrayElement.isString() ? arrayElement.asString() : null;
                  }
               }
            };
         }

         @Override
         public int size() {
            int size = this.cachedSize;
            if (size != -1) {
               return size;
            } else {
               this.cachedSize = size = (int)this.keys.getArraySize();
               return size;
            }
         }
      }

      abstract static class NewInstanceNode extends PolyglotValueDispatch.InteropNode {
         @Node.Child
         private PolyglotLanguageContext.ToGuestValuesNode toGuestValues = PolyglotLanguageContext.ToGuestValuesNode.create();
         private final PolyglotLanguageContext.ToHostValueNode toHostValue;

         protected NewInstanceNode(PolyglotValueDispatch.InteropValue interop) {
            super(interop);
            this.toHostValue = PolyglotLanguageContext.ToHostValueNode.create(interop.impl);
         }

         @Override
         protected Class<?>[] getArgumentTypes() {
            return new Class[]{PolyglotLanguageContext.class, this.polyglot.receiverType, Object[].class};
         }

         @Specialization(limit = "CACHE_LIMIT")
         static Object doCached(
            PolyglotLanguageContext context,
            Object receiver,
            Object[] args,
            @CachedLibrary("receiver") InteropLibrary instantiables,
            @Cached PolyglotLanguageContext.ToGuestValuesNode toGuestValues,
            @Cached("createToHost()") PolyglotLanguageContext.ToHostValueNode toHostValue,
            @Cached BranchProfile arity,
            @Cached BranchProfile invalidArgument,
            @Cached BranchProfile unsupported
         ) {
            Object[] instantiateArguments = toGuestValues.apply(context, (Object[])args[2]);

            try {
               return toHostValue.execute(context, instantiables.instantiate(receiver, instantiateArguments));
            } catch (UnsupportedTypeException var11) {
               invalidArgument.enter();
               throw PolyglotValueDispatch.invalidInstantiateArgumentType(context, receiver, instantiateArguments);
            } catch (ArityException var12) {
               arity.enter();
               throw PolyglotValueDispatch.invalidInstantiateArity(
                  context, receiver, instantiateArguments, var12.getExpectedMinArity(), var12.getExpectedMaxArity(), var12.getActualArity()
               );
            } catch (UnsupportedMessageException var13) {
               unsupported.enter();
               return PolyglotValueDispatch.newInstanceUnsupported(context, receiver);
            }
         }

         @Override
         protected String getOperationName() {
            return "newInstance";
         }
      }

      abstract static class PutHashEntryNode extends PolyglotValueDispatch.InteropNode {
         protected PutHashEntryNode(PolyglotValueDispatch.InteropValue interop) {
            super(interop);
         }

         @Override
         protected Class<?>[] getArgumentTypes() {
            return new Class[]{PolyglotLanguageContext.class, this.polyglot.receiverType, Object.class, Object.class};
         }

         @Override
         protected String getOperationName() {
            return "putHashEntry";
         }

         @Specialization(limit = "CACHE_LIMIT")
         static Object doCached(
            PolyglotLanguageContext context,
            Object receiver,
            Object[] args,
            @CachedLibrary("receiver") InteropLibrary hashes,
            @Cached PolyglotLanguageContext.ToGuestValueNode toGuestKey,
            @Cached PolyglotLanguageContext.ToGuestValueNode toGuestValue,
            @Cached BranchProfile unsupported,
            @Cached BranchProfile invalidKey,
            @Cached BranchProfile invalidValue
         ) {
            Object hostKey = args[2];
            Object hostValue = args[3];
            Object key = toGuestKey.execute(context, hostKey);
            Object value = toGuestValue.execute(context, hostValue);

            try {
               hashes.writeHashEntry(receiver, key, value);
               return null;
            } catch (UnknownKeyException | UnsupportedMessageException var14) {
               unsupported.enter();
               throw PolyglotValueDispatch.putHashEntryUnsupported(context, receiver, key, value);
            } catch (UnsupportedTypeException var15) {
               invalidValue.enter();
               throw PolyglotValueDispatch.invalidHashValue(context, receiver, key, value);
            }
         }
      }

      abstract static class PutMemberNode extends PolyglotValueDispatch.InteropNode {
         protected PutMemberNode(PolyglotValueDispatch.InteropValue interop) {
            super(interop);
         }

         @Override
         protected String getOperationName() {
            return "putMember";
         }

         @Override
         protected Class<?>[] getArgumentTypes() {
            return new Class[]{PolyglotLanguageContext.class, this.polyglot.receiverType, String.class, null};
         }

         @Specialization
         static Object doCached(
            PolyglotLanguageContext context,
            Object receiver,
            Object[] args,
            @CachedLibrary(limit = "CACHE_LIMIT") InteropLibrary objects,
            @Cached PolyglotLanguageContext.ToGuestValueNode toGuestValue,
            @Cached BranchProfile unsupported,
            @Cached BranchProfile invalidValue,
            @Cached BranchProfile unknown
         ) {
            String key = (String)args[2];
            Object originalValue = args[3];
            Object value = toGuestValue.execute(context, originalValue);

            assert key != null;

            try {
               objects.writeMember(receiver, key, value);
               return null;
            } catch (UnsupportedMessageException var12) {
               unsupported.enter();
               throw PolyglotValueDispatch.putMemberUnsupported(context, receiver);
            } catch (UnknownIdentifierException var13) {
               unknown.enter();
               throw PolyglotValueDispatch.nonWritableMemberKey(context, receiver, key);
            } catch (UnsupportedTypeException var14) {
               invalidValue.enter();
               throw PolyglotValueDispatch.invalidMemberValue(context, receiver, key, value);
            }
         }
      }

      abstract static class ReadBufferByteNode extends PolyglotValueDispatch.InteropNode {
         protected ReadBufferByteNode(PolyglotValueDispatch.InteropValue interop) {
            super(interop);
         }

         @Override
         protected Class<?>[] getArgumentTypes() {
            return new Class[]{PolyglotLanguageContext.class, this.polyglot.receiverType, Long.class};
         }

         @Override
         protected String getOperationName() {
            return "readBufferByte";
         }

         @Specialization(limit = "CACHE_LIMIT")
         static Object doCached(
            PolyglotLanguageContext context,
            Object receiver,
            Object[] args,
            @CachedLibrary("receiver") InteropLibrary buffers,
            @Cached("createToHost()") PolyglotLanguageContext.ToHostValueNode toHost,
            @Cached BranchProfile unsupported,
            @Cached BranchProfile unknown
         ) {
            long byteOffset = (Long)args[2];

            try {
               return buffers.readBufferByte(receiver, byteOffset);
            } catch (UnsupportedMessageException var10) {
               unsupported.enter();
               throw PolyglotValueDispatch.readBufferByteUnsupported(context, receiver);
            } catch (InvalidBufferOffsetException var11) {
               unknown.enter();
               throw PolyglotValueDispatch.invalidBufferIndex(context, receiver, var11.getByteOffset(), var11.getLength());
            }
         }
      }

      abstract static class ReadBufferDoubleNode extends PolyglotValueDispatch.InteropNode {
         protected ReadBufferDoubleNode(PolyglotValueDispatch.InteropValue interop) {
            super(interop);
         }

         @Override
         protected Class<?>[] getArgumentTypes() {
            return new Class[]{PolyglotLanguageContext.class, this.polyglot.receiverType, ByteOrder.class, Long.class};
         }

         @Override
         protected String getOperationName() {
            return "readBufferDouble";
         }

         @Specialization(limit = "CACHE_LIMIT")
         static Object doCached(
            PolyglotLanguageContext context,
            Object receiver,
            Object[] args,
            @CachedLibrary("receiver") InteropLibrary buffers,
            @Cached("createToHost()") PolyglotLanguageContext.ToHostValueNode toHost,
            @Cached BranchProfile unsupported,
            @Cached BranchProfile unknown
         ) {
            ByteOrder order = (ByteOrder)args[2];
            long byteOffset = (Long)args[3];

            try {
               return buffers.readBufferDouble(receiver, order, byteOffset);
            } catch (UnsupportedMessageException var11) {
               unsupported.enter();
               throw PolyglotValueDispatch.readBufferDoubleUnsupported(context, receiver);
            } catch (InvalidBufferOffsetException var12) {
               unknown.enter();
               throw PolyglotValueDispatch.invalidBufferIndex(context, receiver, var12.getByteOffset(), var12.getLength());
            }
         }
      }

      abstract static class ReadBufferFloatNode extends PolyglotValueDispatch.InteropNode {
         protected ReadBufferFloatNode(PolyglotValueDispatch.InteropValue interop) {
            super(interop);
         }

         @Override
         protected Class<?>[] getArgumentTypes() {
            return new Class[]{PolyglotLanguageContext.class, this.polyglot.receiverType, ByteOrder.class, Long.class};
         }

         @Override
         protected String getOperationName() {
            return "readBufferFloat";
         }

         @Specialization(limit = "CACHE_LIMIT")
         static Object doCached(
            PolyglotLanguageContext context,
            Object receiver,
            Object[] args,
            @CachedLibrary("receiver") InteropLibrary buffers,
            @Cached("createToHost()") PolyglotLanguageContext.ToHostValueNode toHost,
            @Cached BranchProfile unsupported,
            @Cached BranchProfile unknown
         ) {
            ByteOrder order = (ByteOrder)args[2];
            long byteOffset = (Long)args[3];

            try {
               return buffers.readBufferFloat(receiver, order, byteOffset);
            } catch (UnsupportedMessageException var11) {
               unsupported.enter();
               throw PolyglotValueDispatch.readBufferFloatUnsupported(context, receiver);
            } catch (InvalidBufferOffsetException var12) {
               unknown.enter();
               throw PolyglotValueDispatch.invalidBufferIndex(context, receiver, var12.getByteOffset(), var12.getLength());
            }
         }
      }

      abstract static class ReadBufferIntNode extends PolyglotValueDispatch.InteropNode {
         protected ReadBufferIntNode(PolyglotValueDispatch.InteropValue interop) {
            super(interop);
         }

         @Override
         protected Class<?>[] getArgumentTypes() {
            return new Class[]{PolyglotLanguageContext.class, this.polyglot.receiverType, ByteOrder.class, Long.class};
         }

         @Override
         protected String getOperationName() {
            return "readBufferInt";
         }

         @Specialization(limit = "CACHE_LIMIT")
         static Object doCached(
            PolyglotLanguageContext context,
            Object receiver,
            Object[] args,
            @CachedLibrary("receiver") InteropLibrary buffers,
            @Cached("createToHost()") PolyglotLanguageContext.ToHostValueNode toHost,
            @Cached BranchProfile unsupported,
            @Cached BranchProfile unknown
         ) {
            ByteOrder order = (ByteOrder)args[2];
            long byteOffset = (Long)args[3];

            try {
               return buffers.readBufferInt(receiver, order, byteOffset);
            } catch (UnsupportedMessageException var11) {
               unsupported.enter();
               throw PolyglotValueDispatch.readBufferIntUnsupported(context, receiver);
            } catch (InvalidBufferOffsetException var12) {
               unknown.enter();
               throw PolyglotValueDispatch.invalidBufferIndex(context, receiver, var12.getByteOffset(), var12.getLength());
            }
         }
      }

      abstract static class ReadBufferLongNode extends PolyglotValueDispatch.InteropNode {
         protected ReadBufferLongNode(PolyglotValueDispatch.InteropValue interop) {
            super(interop);
         }

         @Override
         protected Class<?>[] getArgumentTypes() {
            return new Class[]{PolyglotLanguageContext.class, this.polyglot.receiverType, ByteOrder.class, Long.class};
         }

         @Override
         protected String getOperationName() {
            return "readBufferLong";
         }

         @Specialization(limit = "CACHE_LIMIT")
         static Object doCached(
            PolyglotLanguageContext context,
            Object receiver,
            Object[] args,
            @CachedLibrary("receiver") InteropLibrary buffers,
            @Cached("createToHost()") PolyglotLanguageContext.ToHostValueNode toHost,
            @Cached BranchProfile unsupported,
            @Cached BranchProfile unknown
         ) {
            ByteOrder order = (ByteOrder)args[2];
            long byteOffset = (Long)args[3];

            try {
               return buffers.readBufferLong(receiver, order, byteOffset);
            } catch (UnsupportedMessageException var11) {
               unsupported.enter();
               throw PolyglotValueDispatch.readBufferLongUnsupported(context, receiver);
            } catch (InvalidBufferOffsetException var12) {
               unknown.enter();
               throw PolyglotValueDispatch.invalidBufferIndex(context, receiver, var12.getByteOffset(), var12.getLength());
            }
         }
      }

      abstract static class ReadBufferShortNode extends PolyglotValueDispatch.InteropNode {
         protected ReadBufferShortNode(PolyglotValueDispatch.InteropValue interop) {
            super(interop);
         }

         @Override
         protected Class<?>[] getArgumentTypes() {
            return new Class[]{PolyglotLanguageContext.class, this.polyglot.receiverType, ByteOrder.class, Long.class};
         }

         @Override
         protected String getOperationName() {
            return "readBufferShort";
         }

         @Specialization(limit = "CACHE_LIMIT")
         static Object doCached(
            PolyglotLanguageContext context,
            Object receiver,
            Object[] args,
            @CachedLibrary("receiver") InteropLibrary buffers,
            @Cached("createToHost()") PolyglotLanguageContext.ToHostValueNode toHost,
            @Cached BranchProfile unsupported,
            @Cached BranchProfile unknown
         ) {
            ByteOrder order = (ByteOrder)args[2];
            long byteOffset = (Long)args[3];

            try {
               return buffers.readBufferShort(receiver, order, byteOffset);
            } catch (UnsupportedMessageException var11) {
               unsupported.enter();
               throw PolyglotValueDispatch.readBufferShortUnsupported(context, receiver);
            } catch (InvalidBufferOffsetException var12) {
               unknown.enter();
               throw PolyglotValueDispatch.invalidBufferIndex(context, receiver, var12.getByteOffset(), var12.getLength());
            }
         }
      }

      abstract static class RemoveArrayElementNode extends PolyglotValueDispatch.InteropNode {
         protected RemoveArrayElementNode(PolyglotValueDispatch.InteropValue interop) {
            super(interop);
         }

         @Override
         protected Class<?>[] getArgumentTypes() {
            return new Class[]{PolyglotLanguageContext.class, this.polyglot.receiverType, Long.class};
         }

         @Override
         protected String getOperationName() {
            return "removeArrayElement";
         }

         @Specialization(limit = "CACHE_LIMIT")
         static Object doCached(
            PolyglotLanguageContext context,
            Object receiver,
            Object[] args,
            @CachedLibrary("receiver") InteropLibrary arrays,
            @Cached BranchProfile unsupported,
            @Cached BranchProfile invalidIndex
         ) {
            long index = (Long)args[2];

            try {
               arrays.removeArrayElement(receiver, index);
               return Boolean.TRUE;
            } catch (UnsupportedMessageException var10) {
               unsupported.enter();
               throw PolyglotValueDispatch.removeArrayElementUnsupported(context, receiver);
            } catch (InvalidArrayIndexException var11) {
               invalidIndex.enter();
               throw PolyglotValueDispatch.invalidArrayIndex(context, receiver, index);
            }
         }
      }

      abstract static class RemoveHashEntryNode extends PolyglotValueDispatch.InteropNode {
         protected RemoveHashEntryNode(PolyglotValueDispatch.InteropValue interop) {
            super(interop);
         }

         @Override
         protected Class<?>[] getArgumentTypes() {
            return new Class[]{PolyglotLanguageContext.class, this.polyglot.receiverType, Object.class};
         }

         @Override
         protected String getOperationName() {
            return "removeHashEntry";
         }

         @Specialization(limit = "CACHE_LIMIT")
         static Object doCached(
            PolyglotLanguageContext context,
            Object receiver,
            Object[] args,
            @CachedLibrary("receiver") InteropLibrary hashes,
            @Cached PolyglotLanguageContext.ToGuestValueNode toGuestKey,
            @Cached BranchProfile unsupported,
            @Cached BranchProfile invalidKey
         ) {
            Object hostKey = args[2];
            Object key = toGuestKey.execute(context, hostKey);

            Boolean result;
            try {
               hashes.removeHashEntry(receiver, key);
               result = Boolean.TRUE;
            } catch (UnsupportedMessageException var11) {
               unsupported.enter();
               if (!hashes.hasHashEntries(receiver) || hashes.isHashEntryExisting(receiver, key)) {
                  throw PolyglotValueDispatch.removeHashEntryUnsupported(context, receiver, key);
               }

               result = Boolean.FALSE;
            } catch (UnknownKeyException var12) {
               invalidKey.enter();
               result = Boolean.FALSE;
            }

            return result;
         }
      }

      abstract static class RemoveMemberNode extends PolyglotValueDispatch.InteropNode {
         protected RemoveMemberNode(PolyglotValueDispatch.InteropValue interop) {
            super(interop);
         }

         @Override
         protected String getOperationName() {
            return "removeMember";
         }

         @Override
         protected Class<?>[] getArgumentTypes() {
            return new Class[]{PolyglotLanguageContext.class, this.polyglot.receiverType, String.class};
         }

         @Specialization(limit = "CACHE_LIMIT")
         static Object doCached(
            PolyglotLanguageContext context,
            Object receiver,
            Object[] args,
            @CachedLibrary("receiver") InteropLibrary objects,
            @Cached BranchProfile unsupported,
            @Cached BranchProfile unknown
         ) {
            String key = (String)args[2];

            Object value;
            try {
               assert key != null : "should be handled already";

               objects.removeMember(receiver, key);
               value = Boolean.TRUE;
            } catch (UnsupportedMessageException var9) {
               unsupported.enter();
               if (!objects.hasMembers(receiver)) {
                  throw PolyglotValueDispatch.removeMemberUnsupported(context, receiver);
               }

               if (objects.isMemberExisting(receiver, key)) {
                  throw PolyglotValueDispatch.nonRemovableMemberKey(context, receiver, key);
               }

               value = Boolean.FALSE;
            } catch (UnknownIdentifierException var10) {
               unknown.enter();
               if (objects.isMemberExisting(receiver, key)) {
                  throw PolyglotValueDispatch.nonRemovableMemberKey(context, receiver, key);
               }

               value = Boolean.FALSE;
            }

            return value;
         }
      }

      abstract static class SetArrayElementNode extends PolyglotValueDispatch.InteropNode {
         protected SetArrayElementNode(PolyglotValueDispatch.InteropValue interop) {
            super(interop);
         }

         @Override
         protected Class<?>[] getArgumentTypes() {
            return new Class[]{PolyglotLanguageContext.class, this.polyglot.receiverType, Long.class, null};
         }

         @Override
         protected String getOperationName() {
            return "setArrayElement";
         }

         @Specialization(limit = "CACHE_LIMIT")
         static Object doCached(
            PolyglotLanguageContext context,
            Object receiver,
            Object[] args,
            @CachedLibrary("receiver") InteropLibrary arrays,
            @Cached PolyglotLanguageContext.ToGuestValueNode toGuestValue,
            @Cached BranchProfile unsupported,
            @Cached BranchProfile invalidIndex,
            @Cached BranchProfile invalidValue
         ) {
            long index = (Long)args[2];
            Object value = toGuestValue.execute(context, args[3]);

            try {
               arrays.writeArrayElement(receiver, index, value);
            } catch (UnsupportedMessageException var12) {
               unsupported.enter();
               PolyglotValueDispatch.setArrayElementUnsupported(context, receiver);
            } catch (UnsupportedTypeException var13) {
               invalidValue.enter();
               throw PolyglotValueDispatch.invalidArrayValue(context, receiver, index, value);
            } catch (InvalidArrayIndexException var14) {
               invalidIndex.enter();
               throw PolyglotValueDispatch.invalidArrayIndex(context, receiver, index);
            }

            return null;
         }
      }

      abstract static class ThrowExceptionNode extends PolyglotValueDispatch.InteropNode {
         protected ThrowExceptionNode(PolyglotValueDispatch.InteropValue interop) {
            super(interop);
         }

         @Override
         protected Class<?>[] getArgumentTypes() {
            return new Class[]{PolyglotLanguageContext.class, this.polyglot.receiverType};
         }

         @Override
         protected String getOperationName() {
            return "throwException";
         }

         @Specialization(limit = "CACHE_LIMIT")
         static Object doCached(
            PolyglotLanguageContext context,
            Object receiver,
            Object[] args,
            @CachedLibrary("receiver") InteropLibrary objects,
            @Cached BranchProfile unsupported
         ) {
            try {
               throw objects.throwException(receiver);
            } catch (UnsupportedMessageException var6) {
               unsupported.enter();
               throw PolyglotValueDispatch.unsupported(context, receiver, "throwException()", "isException()");
            }
         }
      }

      abstract static class WriteBufferByteNode extends PolyglotValueDispatch.InteropNode {
         protected WriteBufferByteNode(PolyglotValueDispatch.InteropValue interop) {
            super(interop);
         }

         @Override
         protected Class<?>[] getArgumentTypes() {
            return new Class[]{PolyglotLanguageContext.class, this.polyglot.receiverType, Long.class, Byte.class};
         }

         @Override
         protected String getOperationName() {
            return "writeBufferByte";
         }

         @Specialization(limit = "CACHE_LIMIT")
         static Object doCached(
            PolyglotLanguageContext context,
            Object receiver,
            Object[] args,
            @CachedLibrary("receiver") InteropLibrary buffers,
            @Cached BranchProfile unsupported,
            @Cached BranchProfile invalidIndex,
            @Cached BranchProfile invalidValue
         ) {
            long byteOffset = (Long)args[2];
            byte value = (Byte)args[3];

            try {
               buffers.writeBufferByte(receiver, byteOffset, value);
               return null;
            } catch (UnsupportedMessageException var11) {
               unsupported.enter();
               if (buffers.hasBufferElements(receiver)) {
                  throw PolyglotValueDispatch.unsupported(context, receiver, "writeBufferByte()", "isBufferWritable()");
               } else {
                  throw PolyglotValueDispatch.writeBufferByteUnsupported(context, receiver);
               }
            } catch (InvalidBufferOffsetException var12) {
               invalidIndex.enter();
               throw PolyglotValueDispatch.invalidBufferIndex(context, receiver, var12.getByteOffset(), var12.getLength());
            }
         }
      }

      abstract static class WriteBufferDoubleNode extends PolyglotValueDispatch.InteropNode {
         protected WriteBufferDoubleNode(PolyglotValueDispatch.InteropValue interop) {
            super(interop);
         }

         @Override
         protected Class<?>[] getArgumentTypes() {
            return new Class[]{PolyglotLanguageContext.class, this.polyglot.receiverType, ByteOrder.class, Long.class, Double.class};
         }

         @Override
         protected String getOperationName() {
            return "writeBufferDouble";
         }

         @Specialization(limit = "CACHE_LIMIT")
         static Object doCached(
            PolyglotLanguageContext context,
            Object receiver,
            Object[] args,
            @CachedLibrary("receiver") InteropLibrary buffers,
            @Cached BranchProfile unsupported,
            @Cached BranchProfile invalidIndex,
            @Cached BranchProfile invalidValue
         ) {
            ByteOrder order = (ByteOrder)args[2];
            long byteOffset = (Long)args[3];
            double value = (Double)args[4];

            try {
               buffers.writeBufferDouble(receiver, order, byteOffset, value);
               return null;
            } catch (UnsupportedMessageException var13) {
               unsupported.enter();
               if (buffers.hasBufferElements(receiver)) {
                  throw PolyglotValueDispatch.unsupported(context, receiver, "writeBufferDouble()", "isBufferWritable()");
               } else {
                  throw PolyglotValueDispatch.writeBufferDoubleUnsupported(context, receiver);
               }
            } catch (InvalidBufferOffsetException var14) {
               invalidIndex.enter();
               throw PolyglotValueDispatch.invalidBufferIndex(context, receiver, var14.getByteOffset(), var14.getLength());
            }
         }
      }

      abstract static class WriteBufferFloatNode extends PolyglotValueDispatch.InteropNode {
         protected WriteBufferFloatNode(PolyglotValueDispatch.InteropValue interop) {
            super(interop);
         }

         @Override
         protected Class<?>[] getArgumentTypes() {
            return new Class[]{PolyglotLanguageContext.class, this.polyglot.receiverType, ByteOrder.class, Long.class, Float.class};
         }

         @Override
         protected String getOperationName() {
            return "writeBufferFloat";
         }

         @Specialization(limit = "CACHE_LIMIT")
         static Object doCached(
            PolyglotLanguageContext context,
            Object receiver,
            Object[] args,
            @CachedLibrary("receiver") InteropLibrary buffers,
            @Cached BranchProfile unsupported,
            @Cached BranchProfile invalidIndex,
            @Cached BranchProfile invalidValue
         ) {
            ByteOrder order = (ByteOrder)args[2];
            long byteOffset = (Long)args[3];
            float value = (Float)args[4];

            try {
               buffers.writeBufferFloat(receiver, order, byteOffset, value);
               return null;
            } catch (UnsupportedMessageException var12) {
               unsupported.enter();
               if (buffers.hasBufferElements(receiver)) {
                  throw PolyglotValueDispatch.unsupported(context, receiver, "writeBufferFloat()", "isBufferWritable()");
               } else {
                  throw PolyglotValueDispatch.writeBufferFloatUnsupported(context, receiver);
               }
            } catch (InvalidBufferOffsetException var13) {
               invalidIndex.enter();
               throw PolyglotValueDispatch.invalidBufferIndex(context, receiver, var13.getByteOffset(), var13.getLength());
            }
         }
      }

      abstract static class WriteBufferIntNode extends PolyglotValueDispatch.InteropNode {
         protected WriteBufferIntNode(PolyglotValueDispatch.InteropValue interop) {
            super(interop);
         }

         @Override
         protected Class<?>[] getArgumentTypes() {
            return new Class[]{PolyglotLanguageContext.class, this.polyglot.receiverType, ByteOrder.class, Long.class, Integer.class};
         }

         @Override
         protected String getOperationName() {
            return "writeBufferInt";
         }

         @Specialization(limit = "CACHE_LIMIT")
         static Object doCached(
            PolyglotLanguageContext context,
            Object receiver,
            Object[] args,
            @CachedLibrary("receiver") InteropLibrary buffers,
            @Cached BranchProfile unsupported,
            @Cached BranchProfile invalidIndex,
            @Cached BranchProfile invalidValue
         ) {
            ByteOrder order = (ByteOrder)args[2];
            long byteOffset = (Long)args[3];
            int value = (Integer)args[4];

            try {
               buffers.writeBufferInt(receiver, order, byteOffset, value);
               return null;
            } catch (UnsupportedMessageException var12) {
               unsupported.enter();
               if (buffers.hasBufferElements(receiver)) {
                  throw PolyglotValueDispatch.unsupported(context, receiver, "writeBufferInt()", "isBufferWritable()");
               } else {
                  throw PolyglotValueDispatch.writeBufferIntUnsupported(context, receiver);
               }
            } catch (InvalidBufferOffsetException var13) {
               invalidIndex.enter();
               throw PolyglotValueDispatch.invalidBufferIndex(context, receiver, var13.getByteOffset(), var13.getLength());
            }
         }
      }

      abstract static class WriteBufferLongNode extends PolyglotValueDispatch.InteropNode {
         protected WriteBufferLongNode(PolyglotValueDispatch.InteropValue interop) {
            super(interop);
         }

         @Override
         protected Class<?>[] getArgumentTypes() {
            return new Class[]{PolyglotLanguageContext.class, this.polyglot.receiverType, ByteOrder.class, Long.class, Long.class};
         }

         @Override
         protected String getOperationName() {
            return "writeBufferLong";
         }

         @Specialization(limit = "CACHE_LIMIT")
         static Object doCached(
            PolyglotLanguageContext context,
            Object receiver,
            Object[] args,
            @CachedLibrary("receiver") InteropLibrary buffers,
            @Cached BranchProfile unsupported,
            @Cached BranchProfile invalidIndex,
            @Cached BranchProfile invalidValue
         ) {
            ByteOrder order = (ByteOrder)args[2];
            long byteOffset = (Long)args[3];
            long value = (Long)args[4];

            try {
               buffers.writeBufferLong(receiver, order, byteOffset, value);
               return null;
            } catch (UnsupportedMessageException var13) {
               unsupported.enter();
               if (buffers.hasBufferElements(receiver)) {
                  throw PolyglotValueDispatch.unsupported(context, receiver, "writeBufferLong()", "isBufferWritable()");
               } else {
                  throw PolyglotValueDispatch.writeBufferLongUnsupported(context, receiver);
               }
            } catch (InvalidBufferOffsetException var14) {
               invalidIndex.enter();
               throw PolyglotValueDispatch.invalidBufferIndex(context, receiver, var14.getByteOffset(), var14.getLength());
            }
         }
      }

      abstract static class WriteBufferShortNode extends PolyglotValueDispatch.InteropNode {
         protected WriteBufferShortNode(PolyglotValueDispatch.InteropValue interop) {
            super(interop);
         }

         @Override
         protected Class<?>[] getArgumentTypes() {
            return new Class[]{PolyglotLanguageContext.class, this.polyglot.receiverType, ByteOrder.class, Long.class, Short.class};
         }

         @Override
         protected String getOperationName() {
            return "writeBufferShort";
         }

         @Specialization(limit = "CACHE_LIMIT")
         static Object doCached(
            PolyglotLanguageContext context,
            Object receiver,
            Object[] args,
            @CachedLibrary("receiver") InteropLibrary buffers,
            @Cached BranchProfile unsupported,
            @Cached BranchProfile invalidIndex,
            @Cached BranchProfile invalidValue
         ) {
            ByteOrder order = (ByteOrder)args[2];
            long byteOffset = (Long)args[3];
            short value = (Short)args[4];

            try {
               buffers.writeBufferShort(receiver, order, byteOffset, value);
               return null;
            } catch (UnsupportedMessageException var12) {
               unsupported.enter();
               if (buffers.hasBufferElements(receiver)) {
                  throw PolyglotValueDispatch.unsupported(context, receiver, "writeBufferShort()", "isBufferWritable()");
               } else {
                  throw PolyglotValueDispatch.writeBufferShortUnsupported(context, receiver);
               }
            } catch (InvalidBufferOffsetException var13) {
               invalidIndex.enter();
               throw PolyglotValueDispatch.invalidBufferIndex(context, receiver, var13.getByteOffset(), var13.getLength());
            }
         }
      }
   }

   static final class PrimitiveValue extends PolyglotValueDispatch {
      private final InteropLibrary interop;
      private final PolyglotLanguage language;

      private PrimitiveValue(PolyglotImpl impl, PolyglotLanguageInstance instance, Object primitiveValue) {
         super(impl, instance);
         this.interop = InteropLibrary.getFactory().getUncached(primitiveValue);
         this.language = instance != null ? instance.language : null;
      }

      @Override
      public boolean isString(Object languageContext, Object receiver) {
         return this.interop.isString(receiver);
      }

      @Override
      public boolean isBoolean(Object languageContext, Object receiver) {
         return this.interop.isBoolean(receiver);
      }

      @Override
      public boolean asBoolean(Object languageContext, Object receiver) {
         try {
            return this.interop.asBoolean(receiver);
         } catch (UnsupportedMessageException var4) {
            return super.asBoolean(languageContext, receiver);
         }
      }

      @Override
      public String asString(Object languageContext, Object receiver) {
         try {
            return this.interop.asString(receiver);
         } catch (UnsupportedMessageException var4) {
            return super.asString(languageContext, receiver);
         }
      }

      @Override
      public boolean isNumber(Object languageContext, Object receiver) {
         return this.interop.isNumber(receiver);
      }

      @Override
      public boolean fitsInByte(Object languageContext, Object receiver) {
         return this.interop.fitsInByte(receiver);
      }

      @Override
      public boolean fitsInShort(Object languageContext, Object receiver) {
         return this.interop.fitsInShort(receiver);
      }

      @Override
      public boolean fitsInInt(Object languageContext, Object receiver) {
         return this.interop.fitsInInt(receiver);
      }

      @Override
      public boolean fitsInLong(Object languageContext, Object receiver) {
         return this.interop.fitsInLong(receiver);
      }

      @Override
      public boolean fitsInFloat(Object languageContext, Object receiver) {
         return this.interop.fitsInFloat(receiver);
      }

      @Override
      public boolean fitsInDouble(Object languageContext, Object receiver) {
         return this.interop.fitsInDouble(receiver);
      }

      @Override
      public byte asByte(Object languageContext, Object receiver) {
         try {
            return this.interop.asByte(receiver);
         } catch (UnsupportedMessageException var4) {
            return super.asByte(languageContext, receiver);
         }
      }

      @Override
      public short asShort(Object languageContext, Object receiver) {
         try {
            return this.interop.asShort(receiver);
         } catch (UnsupportedMessageException var4) {
            return super.asShort(languageContext, receiver);
         }
      }

      @Override
      public int asInt(Object languageContext, Object receiver) {
         try {
            return this.interop.asInt(receiver);
         } catch (UnsupportedMessageException var4) {
            return super.asInt(languageContext, receiver);
         }
      }

      @Override
      public long asLong(Object languageContext, Object receiver) {
         try {
            return this.interop.asLong(receiver);
         } catch (UnsupportedMessageException var4) {
            return super.asLong(languageContext, receiver);
         }
      }

      @Override
      public float asFloat(Object languageContext, Object receiver) {
         try {
            return this.interop.asFloat(receiver);
         } catch (UnsupportedMessageException var4) {
            return super.asFloat(languageContext, receiver);
         }
      }

      @Override
      public double asDouble(Object languageContext, Object receiver) {
         try {
            return this.interop.asDouble(receiver);
         } catch (UnsupportedMessageException var4) {
            return super.asDouble(languageContext, receiver);
         }
      }

      @Override
      public <T> T as(Object languageContext, Object receiver, Class<T> targetType) {
         PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;
         Object prev = hostEnter(context);

         T result;
         try {
            if (context == null) {
               result = (T)EngineAccessor.HOST.convertPrimitiveLossy(receiver, targetType);
               if (result == null) {
                  throw PolyglotInteropErrors.cannotConvertPrimitive(null, receiver, targetType);
               }

               return result;
            }

            result = this.language.engine.host.toHostType(null, context.context.getHostContextImpl(), receiver, targetType, targetType);
         } catch (Throwable var11) {
            throw guestToHostException(context, (T)var11, true);
         } finally {
            hostLeave(context, prev);
         }

         return result;
      }

      @Override
      public <T> T as(Object languageContext, Object receiver, TypeLiteral<T> targetType) {
         return this.as(languageContext, receiver, targetType.getRawType());
      }

      @Override
      public Value getMetaObjectImpl(PolyglotLanguageContext languageContext, Object receiver) {
         return super.getMetaObjectImpl(languageContext, this.getLanguageView(languageContext, receiver));
      }

      @Override
      protected String toStringImpl(Object languageContext, Object receiver) throws AssertionError {
         return super.toStringImpl(languageContext, this.getLanguageView(languageContext, receiver));
      }

      private Object getLanguageView(Object languageContext, Object receiver) {
         if (languageContext != null && this.language != null) {
            PolyglotContextImpl c = ((PolyglotLanguageContext)languageContext).context;
            return c.getContext(this.language).getLanguageViewNoCheck(receiver);
         } else {
            return receiver;
         }
      }
   }
}
