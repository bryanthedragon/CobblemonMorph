package com.oracle.truffle.api.interop;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.library.DynamicDispatchLibrary;
import com.oracle.truffle.api.library.Library;
import com.oracle.truffle.api.library.LibraryExport;
import com.oracle.truffle.api.library.LibraryFactory;
import com.oracle.truffle.api.library.Message;
import com.oracle.truffle.api.library.ReflectionLibrary;
import com.oracle.truffle.api.nodes.DenyReplace;
import com.oracle.truffle.api.nodes.EncapsulatingNodeReference;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.source.SourceSection;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.api.utilities.FinalBitSet;
import com.oracle.truffle.api.utilities.TriState;
import java.nio.ByteOrder;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Collections;
import java.util.concurrent.locks.Lock;

@GeneratedBy(InteropLibrary.class)
final class InteropLibraryGen extends LibraryFactory<InteropLibrary> {
   private static final Class<InteropLibrary> LIBRARY_CLASS = lazyLibraryClass();
   private static final Message IS_NULL = new InteropLibraryGen.MessageImpl("isNull", 0, boolean.class, Object.class);
   private static final Message IS_BOOLEAN = new InteropLibraryGen.MessageImpl("isBoolean", 1, boolean.class, Object.class);
   private static final Message AS_BOOLEAN = new InteropLibraryGen.MessageImpl("asBoolean", 2, boolean.class, Object.class);
   private static final Message IS_EXECUTABLE = new InteropLibraryGen.MessageImpl("isExecutable", 3, boolean.class, Object.class);
   private static final Message EXECUTE = new InteropLibraryGen.MessageImpl("execute", 4, Object.class, Object.class, Object[].class);
   private static final Message HAS_EXECUTABLE_NAME = new InteropLibraryGen.MessageImpl("hasExecutableName", 5, boolean.class, Object.class);
   private static final Message GET_EXECUTABLE_NAME = new InteropLibraryGen.MessageImpl("getExecutableName", 6, Object.class, Object.class);
   private static final Message HAS_DECLARING_META_OBJECT = new InteropLibraryGen.MessageImpl("hasDeclaringMetaObject", 7, boolean.class, Object.class);
   private static final Message GET_DECLARING_META_OBJECT = new InteropLibraryGen.MessageImpl("getDeclaringMetaObject", 8, Object.class, Object.class);
   private static final Message IS_INSTANTIABLE = new InteropLibraryGen.MessageImpl("isInstantiable", 9, boolean.class, Object.class);
   private static final Message INSTANTIATE = new InteropLibraryGen.MessageImpl("instantiate", 10, Object.class, Object.class, Object[].class);
   private static final Message IS_STRING = new InteropLibraryGen.MessageImpl("isString", 11, boolean.class, Object.class);
   private static final Message AS_STRING = new InteropLibraryGen.MessageImpl("asString", 12, String.class, Object.class);
   private static final Message AS_TRUFFLE_STRING = new InteropLibraryGen.MessageImpl("asTruffleString", 13, TruffleString.class, Object.class);
   private static final Message IS_NUMBER = new InteropLibraryGen.MessageImpl("isNumber", 14, boolean.class, Object.class);
   private static final Message FITS_IN_BYTE = new InteropLibraryGen.MessageImpl("fitsInByte", 15, boolean.class, Object.class);
   private static final Message FITS_IN_SHORT = new InteropLibraryGen.MessageImpl("fitsInShort", 16, boolean.class, Object.class);
   private static final Message FITS_IN_INT = new InteropLibraryGen.MessageImpl("fitsInInt", 17, boolean.class, Object.class);
   private static final Message FITS_IN_LONG = new InteropLibraryGen.MessageImpl("fitsInLong", 18, boolean.class, Object.class);
   private static final Message FITS_IN_FLOAT = new InteropLibraryGen.MessageImpl("fitsInFloat", 19, boolean.class, Object.class);
   private static final Message FITS_IN_DOUBLE = new InteropLibraryGen.MessageImpl("fitsInDouble", 20, boolean.class, Object.class);
   private static final Message AS_BYTE = new InteropLibraryGen.MessageImpl("asByte", 21, byte.class, Object.class);
   private static final Message AS_SHORT = new InteropLibraryGen.MessageImpl("asShort", 22, short.class, Object.class);
   private static final Message AS_INT = new InteropLibraryGen.MessageImpl("asInt", 23, int.class, Object.class);
   private static final Message AS_LONG = new InteropLibraryGen.MessageImpl("asLong", 24, long.class, Object.class);
   private static final Message AS_FLOAT = new InteropLibraryGen.MessageImpl("asFloat", 25, float.class, Object.class);
   private static final Message AS_DOUBLE = new InteropLibraryGen.MessageImpl("asDouble", 26, double.class, Object.class);
   private static final Message HAS_MEMBERS = new InteropLibraryGen.MessageImpl("hasMembers", 27, boolean.class, Object.class);
   private static final Message GET_MEMBERS = new InteropLibraryGen.MessageImpl("getMembers", 28, Object.class, Object.class, boolean.class);
   private static final Message IS_MEMBER_READABLE = new InteropLibraryGen.MessageImpl("isMemberReadable", 29, boolean.class, Object.class, String.class);
   private static final Message READ_MEMBER = new InteropLibraryGen.MessageImpl("readMember", 30, Object.class, Object.class, String.class);
   private static final Message IS_MEMBER_MODIFIABLE = new InteropLibraryGen.MessageImpl("isMemberModifiable", 31, boolean.class, Object.class, String.class);
   private static final Message IS_MEMBER_INSERTABLE = new InteropLibraryGen.MessageImpl("isMemberInsertable", 32, boolean.class, Object.class, String.class);
   private static final Message WRITE_MEMBER = new InteropLibraryGen.MessageImpl("writeMember", 33, void.class, Object.class, String.class, Object.class);
   private static final Message IS_MEMBER_REMOVABLE = new InteropLibraryGen.MessageImpl("isMemberRemovable", 34, boolean.class, Object.class, String.class);
   private static final Message REMOVE_MEMBER = new InteropLibraryGen.MessageImpl("removeMember", 35, void.class, Object.class, String.class);
   private static final Message IS_MEMBER_INVOCABLE = new InteropLibraryGen.MessageImpl("isMemberInvocable", 36, boolean.class, Object.class, String.class);
   private static final Message INVOKE_MEMBER = new InteropLibraryGen.MessageImpl("invokeMember", 37, Object.class, Object.class, String.class, Object[].class);
   private static final Message IS_MEMBER_INTERNAL = new InteropLibraryGen.MessageImpl("isMemberInternal", 38, boolean.class, Object.class, String.class);
   private static final Message HAS_MEMBER_READ_SIDE_EFFECTS = new InteropLibraryGen.MessageImpl(
      "hasMemberReadSideEffects", 39, boolean.class, Object.class, String.class
   );
   private static final Message HAS_MEMBER_WRITE_SIDE_EFFECTS = new InteropLibraryGen.MessageImpl(
      "hasMemberWriteSideEffects", 40, boolean.class, Object.class, String.class
   );
   private static final Message HAS_HASH_ENTRIES = new InteropLibraryGen.MessageImpl("hasHashEntries", 41, boolean.class, Object.class);
   private static final Message GET_HASH_SIZE = new InteropLibraryGen.MessageImpl("getHashSize", 42, long.class, Object.class);
   private static final Message IS_HASH_ENTRY_READABLE = new InteropLibraryGen.MessageImpl("isHashEntryReadable", 43, boolean.class, Object.class, Object.class);
   private static final Message READ_HASH_VALUE = new InteropLibraryGen.MessageImpl("readHashValue", 44, Object.class, Object.class, Object.class);
   private static final Message READ_HASH_VALUE_OR_DEFAULT = new InteropLibraryGen.MessageImpl(
      "readHashValueOrDefault", 45, Object.class, Object.class, Object.class, Object.class
   );
   private static final Message IS_HASH_ENTRY_MODIFIABLE = new InteropLibraryGen.MessageImpl(
      "isHashEntryModifiable", 46, boolean.class, Object.class, Object.class
   );
   private static final Message IS_HASH_ENTRY_INSERTABLE = new InteropLibraryGen.MessageImpl(
      "isHashEntryInsertable", 47, boolean.class, Object.class, Object.class
   );
   private static final Message IS_HASH_ENTRY_WRITABLE = new InteropLibraryGen.MessageImpl("isHashEntryWritable", 48, boolean.class, Object.class, Object.class);
   private static final Message WRITE_HASH_ENTRY = new InteropLibraryGen.MessageImpl("writeHashEntry", 49, void.class, Object.class, Object.class, Object.class);
   private static final Message IS_HASH_ENTRY_REMOVABLE = new InteropLibraryGen.MessageImpl(
      "isHashEntryRemovable", 50, boolean.class, Object.class, Object.class
   );
   private static final Message REMOVE_HASH_ENTRY = new InteropLibraryGen.MessageImpl("removeHashEntry", 51, void.class, Object.class, Object.class);
   private static final Message IS_HASH_ENTRY_EXISTING = new InteropLibraryGen.MessageImpl("isHashEntryExisting", 52, boolean.class, Object.class, Object.class);
   private static final Message GET_HASH_ENTRIES_ITERATOR = new InteropLibraryGen.MessageImpl("getHashEntriesIterator", 53, Object.class, Object.class);
   private static final Message GET_HASH_KEYS_ITERATOR = new InteropLibraryGen.MessageImpl("getHashKeysIterator", 54, Object.class, Object.class);
   private static final Message GET_HASH_VALUES_ITERATOR = new InteropLibraryGen.MessageImpl("getHashValuesIterator", 55, Object.class, Object.class);
   private static final Message HAS_ARRAY_ELEMENTS = new InteropLibraryGen.MessageImpl("hasArrayElements", 56, boolean.class, Object.class);
   private static final Message READ_ARRAY_ELEMENT = new InteropLibraryGen.MessageImpl("readArrayElement", 57, Object.class, Object.class, long.class);
   private static final Message GET_ARRAY_SIZE = new InteropLibraryGen.MessageImpl("getArraySize", 58, long.class, Object.class);
   private static final Message IS_ARRAY_ELEMENT_READABLE = new InteropLibraryGen.MessageImpl(
      "isArrayElementReadable", 59, boolean.class, Object.class, long.class
   );
   private static final Message WRITE_ARRAY_ELEMENT = new InteropLibraryGen.MessageImpl(
      "writeArrayElement", 60, void.class, Object.class, long.class, Object.class
   );
   private static final Message REMOVE_ARRAY_ELEMENT = new InteropLibraryGen.MessageImpl("removeArrayElement", 61, void.class, Object.class, long.class);
   private static final Message IS_ARRAY_ELEMENT_MODIFIABLE = new InteropLibraryGen.MessageImpl(
      "isArrayElementModifiable", 62, boolean.class, Object.class, long.class
   );
   private static final Message IS_ARRAY_ELEMENT_INSERTABLE = new InteropLibraryGen.MessageImpl(
      "isArrayElementInsertable", 63, boolean.class, Object.class, long.class
   );
   private static final Message IS_ARRAY_ELEMENT_REMOVABLE = new InteropLibraryGen.MessageImpl(
      "isArrayElementRemovable", 64, boolean.class, Object.class, long.class
   );
   private static final Message HAS_BUFFER_ELEMENTS = new InteropLibraryGen.MessageImpl("hasBufferElements", 65, boolean.class, Object.class);
   private static final Message IS_BUFFER_WRITABLE = new InteropLibraryGen.MessageImpl("isBufferWritable", 66, boolean.class, Object.class);
   private static final Message GET_BUFFER_SIZE = new InteropLibraryGen.MessageImpl("getBufferSize", 67, long.class, Object.class);
   private static final Message READ_BUFFER_BYTE = new InteropLibraryGen.MessageImpl("readBufferByte", 68, byte.class, Object.class, long.class);
   private static final Message WRITE_BUFFER_BYTE = new InteropLibraryGen.MessageImpl("writeBufferByte", 69, void.class, Object.class, long.class, byte.class);
   private static final Message READ_BUFFER_SHORT = new InteropLibraryGen.MessageImpl(
      "readBufferShort", 70, short.class, Object.class, ByteOrder.class, long.class
   );
   private static final Message WRITE_BUFFER_SHORT = new InteropLibraryGen.MessageImpl(
      "writeBufferShort", 71, void.class, Object.class, ByteOrder.class, long.class, short.class
   );
   private static final Message READ_BUFFER_INT = new InteropLibraryGen.MessageImpl("readBufferInt", 72, int.class, Object.class, ByteOrder.class, long.class);
   private static final Message WRITE_BUFFER_INT = new InteropLibraryGen.MessageImpl(
      "writeBufferInt", 73, void.class, Object.class, ByteOrder.class, long.class, int.class
   );
   private static final Message READ_BUFFER_LONG = new InteropLibraryGen.MessageImpl(
      "readBufferLong", 74, long.class, Object.class, ByteOrder.class, long.class
   );
   private static final Message WRITE_BUFFER_LONG = new InteropLibraryGen.MessageImpl(
      "writeBufferLong", 75, void.class, Object.class, ByteOrder.class, long.class, long.class
   );
   private static final Message READ_BUFFER_FLOAT = new InteropLibraryGen.MessageImpl(
      "readBufferFloat", 76, float.class, Object.class, ByteOrder.class, long.class
   );
   private static final Message WRITE_BUFFER_FLOAT = new InteropLibraryGen.MessageImpl(
      "writeBufferFloat", 77, void.class, Object.class, ByteOrder.class, long.class, float.class
   );
   private static final Message READ_BUFFER_DOUBLE = new InteropLibraryGen.MessageImpl(
      "readBufferDouble", 78, double.class, Object.class, ByteOrder.class, long.class
   );
   private static final Message WRITE_BUFFER_DOUBLE = new InteropLibraryGen.MessageImpl(
      "writeBufferDouble", 79, void.class, Object.class, ByteOrder.class, long.class, double.class
   );
   private static final Message IS_POINTER = new InteropLibraryGen.MessageImpl("isPointer", 80, boolean.class, Object.class);
   private static final Message AS_POINTER = new InteropLibraryGen.MessageImpl("asPointer", 81, long.class, Object.class);
   private static final Message TO_NATIVE = new InteropLibraryGen.MessageImpl("toNative", 82, void.class, Object.class);
   private static final Message AS_INSTANT = new InteropLibraryGen.MessageImpl("asInstant", 83, Instant.class, Object.class);
   private static final Message IS_TIME_ZONE = new InteropLibraryGen.MessageImpl("isTimeZone", 84, boolean.class, Object.class);
   private static final Message AS_TIME_ZONE = new InteropLibraryGen.MessageImpl("asTimeZone", 85, ZoneId.class, Object.class);
   private static final Message IS_DATE = new InteropLibraryGen.MessageImpl("isDate", 86, boolean.class, Object.class);
   private static final Message AS_DATE = new InteropLibraryGen.MessageImpl("asDate", 87, LocalDate.class, Object.class);
   private static final Message IS_TIME = new InteropLibraryGen.MessageImpl("isTime", 88, boolean.class, Object.class);
   private static final Message AS_TIME = new InteropLibraryGen.MessageImpl("asTime", 89, LocalTime.class, Object.class);
   private static final Message IS_DURATION = new InteropLibraryGen.MessageImpl("isDuration", 90, boolean.class, Object.class);
   private static final Message AS_DURATION = new InteropLibraryGen.MessageImpl("asDuration", 91, Duration.class, Object.class);
   private static final Message IS_EXCEPTION = new InteropLibraryGen.MessageImpl("isException", 92, boolean.class, Object.class);
   private static final Message THROW_EXCEPTION = new InteropLibraryGen.MessageImpl("throwException", 93, RuntimeException.class, Object.class);
   private static final Message GET_EXCEPTION_TYPE = new InteropLibraryGen.MessageImpl("getExceptionType", 94, ExceptionType.class, Object.class);
   private static final Message IS_EXCEPTION_INCOMPLETE_SOURCE = new InteropLibraryGen.MessageImpl(
      "isExceptionIncompleteSource", 95, boolean.class, Object.class
   );
   private static final Message GET_EXCEPTION_EXIT_STATUS = new InteropLibraryGen.MessageImpl("getExceptionExitStatus", 96, int.class, Object.class);
   private static final Message HAS_EXCEPTION_CAUSE = new InteropLibraryGen.MessageImpl("hasExceptionCause", 97, boolean.class, Object.class);
   private static final Message GET_EXCEPTION_CAUSE = new InteropLibraryGen.MessageImpl("getExceptionCause", 98, Object.class, Object.class);
   private static final Message HAS_EXCEPTION_MESSAGE = new InteropLibraryGen.MessageImpl("hasExceptionMessage", 99, boolean.class, Object.class);
   private static final Message GET_EXCEPTION_MESSAGE = new InteropLibraryGen.MessageImpl("getExceptionMessage", 100, Object.class, Object.class);
   private static final Message HAS_EXCEPTION_STACK_TRACE = new InteropLibraryGen.MessageImpl("hasExceptionStackTrace", 101, boolean.class, Object.class);
   private static final Message GET_EXCEPTION_STACK_TRACE = new InteropLibraryGen.MessageImpl("getExceptionStackTrace", 102, Object.class, Object.class);
   private static final Message HAS_ITERATOR = new InteropLibraryGen.MessageImpl("hasIterator", 103, boolean.class, Object.class);
   private static final Message GET_ITERATOR = new InteropLibraryGen.MessageImpl("getIterator", 104, Object.class, Object.class);
   private static final Message IS_ITERATOR = new InteropLibraryGen.MessageImpl("isIterator", 105, boolean.class, Object.class);
   private static final Message HAS_ITERATOR_NEXT_ELEMENT = new InteropLibraryGen.MessageImpl("hasIteratorNextElement", 106, boolean.class, Object.class);
   private static final Message GET_ITERATOR_NEXT_ELEMENT = new InteropLibraryGen.MessageImpl("getIteratorNextElement", 107, Object.class, Object.class);
   private static final Message HAS_SOURCE_LOCATION = new InteropLibraryGen.MessageImpl("hasSourceLocation", 108, boolean.class, Object.class);
   private static final Message GET_SOURCE_LOCATION = new InteropLibraryGen.MessageImpl("getSourceLocation", 109, SourceSection.class, Object.class);
   private static final Message HAS_LANGUAGE = new InteropLibraryGen.MessageImpl("hasLanguage", 110, boolean.class, Object.class);
   private static final Message GET_LANGUAGE = new InteropLibraryGen.MessageImpl("getLanguage", 111, Class.class, Object.class);
   private static final Message HAS_META_OBJECT = new InteropLibraryGen.MessageImpl("hasMetaObject", 112, boolean.class, Object.class);
   private static final Message GET_META_OBJECT = new InteropLibraryGen.MessageImpl("getMetaObject", 113, Object.class, Object.class);
   private static final Message TO_DISPLAY_STRING = new InteropLibraryGen.MessageImpl("toDisplayString", 114, Object.class, Object.class, boolean.class);
   private static final Message IS_META_OBJECT = new InteropLibraryGen.MessageImpl("isMetaObject", 115, boolean.class, Object.class);
   private static final Message GET_META_QUALIFIED_NAME = new InteropLibraryGen.MessageImpl("getMetaQualifiedName", 116, Object.class, Object.class);
   private static final Message GET_META_SIMPLE_NAME = new InteropLibraryGen.MessageImpl("getMetaSimpleName", 117, Object.class, Object.class);
   private static final Message IS_META_INSTANCE = new InteropLibraryGen.MessageImpl("isMetaInstance", 118, boolean.class, Object.class, Object.class);
   private static final Message HAS_META_PARENTS = new InteropLibraryGen.MessageImpl("hasMetaParents", 119, boolean.class, Object.class);
   private static final Message GET_META_PARENTS = new InteropLibraryGen.MessageImpl("getMetaParents", 120, Object.class, Object.class);
   private static final Message IS_IDENTICAL_OR_UNDEFINED = new InteropLibraryGen.MessageImpl(
      "isIdenticalOrUndefined", 121, TriState.class, Object.class, Object.class
   );
   private static final Message IS_IDENTICAL = new InteropLibraryGen.MessageImpl(
      "isIdentical", 122, boolean.class, Object.class, Object.class, InteropLibrary.class
   );
   private static final Message IDENTITY_HASH_CODE = new InteropLibraryGen.MessageImpl("identityHashCode", 123, int.class, Object.class);
   private static final Message IS_SCOPE = new InteropLibraryGen.MessageImpl("isScope", 124, boolean.class, Object.class);
   private static final Message HAS_SCOPE_PARENT = new InteropLibraryGen.MessageImpl("hasScopeParent", 125, boolean.class, Object.class);
   private static final Message GET_SCOPE_PARENT = new InteropLibraryGen.MessageImpl("getScopeParent", 126, Object.class, Object.class);
   private static final InteropLibraryGen INSTANCE = new InteropLibraryGen();
   private static final LibraryFactory<DynamicDispatchLibrary> DYNAMIC_DISPATCH_LIBRARY_ = LibraryFactory.resolve(DynamicDispatchLibrary.class);

   private InteropLibraryGen() {
      super(
         LIBRARY_CLASS,
         Collections.unmodifiableList(
            Arrays.asList(
               IS_NULL,
               IS_BOOLEAN,
               AS_BOOLEAN,
               IS_EXECUTABLE,
               EXECUTE,
               HAS_EXECUTABLE_NAME,
               GET_EXECUTABLE_NAME,
               HAS_DECLARING_META_OBJECT,
               GET_DECLARING_META_OBJECT,
               IS_INSTANTIABLE,
               INSTANTIATE,
               IS_STRING,
               AS_STRING,
               AS_TRUFFLE_STRING,
               IS_NUMBER,
               FITS_IN_BYTE,
               FITS_IN_SHORT,
               FITS_IN_INT,
               FITS_IN_LONG,
               FITS_IN_FLOAT,
               FITS_IN_DOUBLE,
               AS_BYTE,
               AS_SHORT,
               AS_INT,
               AS_LONG,
               AS_FLOAT,
               AS_DOUBLE,
               HAS_MEMBERS,
               GET_MEMBERS,
               IS_MEMBER_READABLE,
               READ_MEMBER,
               IS_MEMBER_MODIFIABLE,
               IS_MEMBER_INSERTABLE,
               WRITE_MEMBER,
               IS_MEMBER_REMOVABLE,
               REMOVE_MEMBER,
               IS_MEMBER_INVOCABLE,
               INVOKE_MEMBER,
               IS_MEMBER_INTERNAL,
               HAS_MEMBER_READ_SIDE_EFFECTS,
               HAS_MEMBER_WRITE_SIDE_EFFECTS,
               HAS_HASH_ENTRIES,
               GET_HASH_SIZE,
               IS_HASH_ENTRY_READABLE,
               READ_HASH_VALUE,
               READ_HASH_VALUE_OR_DEFAULT,
               IS_HASH_ENTRY_MODIFIABLE,
               IS_HASH_ENTRY_INSERTABLE,
               IS_HASH_ENTRY_WRITABLE,
               WRITE_HASH_ENTRY,
               IS_HASH_ENTRY_REMOVABLE,
               REMOVE_HASH_ENTRY,
               IS_HASH_ENTRY_EXISTING,
               GET_HASH_ENTRIES_ITERATOR,
               GET_HASH_KEYS_ITERATOR,
               GET_HASH_VALUES_ITERATOR,
               HAS_ARRAY_ELEMENTS,
               READ_ARRAY_ELEMENT,
               GET_ARRAY_SIZE,
               IS_ARRAY_ELEMENT_READABLE,
               WRITE_ARRAY_ELEMENT,
               REMOVE_ARRAY_ELEMENT,
               IS_ARRAY_ELEMENT_MODIFIABLE,
               IS_ARRAY_ELEMENT_INSERTABLE,
               IS_ARRAY_ELEMENT_REMOVABLE,
               HAS_BUFFER_ELEMENTS,
               IS_BUFFER_WRITABLE,
               GET_BUFFER_SIZE,
               READ_BUFFER_BYTE,
               WRITE_BUFFER_BYTE,
               READ_BUFFER_SHORT,
               WRITE_BUFFER_SHORT,
               READ_BUFFER_INT,
               WRITE_BUFFER_INT,
               READ_BUFFER_LONG,
               WRITE_BUFFER_LONG,
               READ_BUFFER_FLOAT,
               WRITE_BUFFER_FLOAT,
               READ_BUFFER_DOUBLE,
               WRITE_BUFFER_DOUBLE,
               IS_POINTER,
               AS_POINTER,
               TO_NATIVE,
               AS_INSTANT,
               IS_TIME_ZONE,
               AS_TIME_ZONE,
               IS_DATE,
               AS_DATE,
               IS_TIME,
               AS_TIME,
               IS_DURATION,
               AS_DURATION,
               IS_EXCEPTION,
               THROW_EXCEPTION,
               GET_EXCEPTION_TYPE,
               IS_EXCEPTION_INCOMPLETE_SOURCE,
               GET_EXCEPTION_EXIT_STATUS,
               HAS_EXCEPTION_CAUSE,
               GET_EXCEPTION_CAUSE,
               HAS_EXCEPTION_MESSAGE,
               GET_EXCEPTION_MESSAGE,
               HAS_EXCEPTION_STACK_TRACE,
               GET_EXCEPTION_STACK_TRACE,
               HAS_ITERATOR,
               GET_ITERATOR,
               IS_ITERATOR,
               HAS_ITERATOR_NEXT_ELEMENT,
               GET_ITERATOR_NEXT_ELEMENT,
               HAS_SOURCE_LOCATION,
               GET_SOURCE_LOCATION,
               HAS_LANGUAGE,
               GET_LANGUAGE,
               HAS_META_OBJECT,
               GET_META_OBJECT,
               TO_DISPLAY_STRING,
               IS_META_OBJECT,
               GET_META_QUALIFIED_NAME,
               GET_META_SIMPLE_NAME,
               IS_META_INSTANCE,
               HAS_META_PARENTS,
               GET_META_PARENTS,
               IS_IDENTICAL_OR_UNDEFINED,
               IS_IDENTICAL,
               IDENTITY_HASH_CODE,
               IS_SCOPE,
               HAS_SCOPE_PARENT,
               GET_SCOPE_PARENT
            )
         )
      );
   }

   @Override
   protected Class<?> getDefaultClass(Object receiver) {
      if (receiver instanceof Boolean) {
         return DefaultBooleanExports.class;
      } else if (receiver instanceof Integer) {
         return DefaultIntegerExports.class;
      } else if (receiver instanceof Byte) {
         return DefaultByteExports.class;
      } else if (receiver instanceof Short) {
         return DefaultShortExports.class;
      } else if (receiver instanceof Long) {
         return DefaultLongExports.class;
      } else if (receiver instanceof Float) {
         return DefaultFloatExports.class;
      } else if (receiver instanceof Double) {
         return DefaultDoubleExports.class;
      } else if (receiver instanceof Character) {
         return DefaultCharacterExports.class;
      } else if (receiver instanceof String) {
         return DefaultStringExports.class;
      } else {
         return receiver instanceof TruffleString ? DefaultTStringExports.class : InteropLibrary.class;
      }
   }

   protected InteropLibrary createAssertions(InteropLibrary delegate) {
      return new InteropLibrary.Asserts(delegate);
   }

   protected InteropLibrary createProxy(ReflectionLibrary library) {
      return new InteropLibraryGen.Proxy(library);
   }

   @Override
   protected FinalBitSet createMessageBitSet(Message... messages) {
      BitSet bitSet = new BitSet(2);

      for (Message message : messages) {
         bitSet.set(message.getId());
      }

      return FinalBitSet.valueOf(bitSet);
   }

   protected InteropLibrary createDelegate(InteropLibrary delegateLibrary) {
      return new InteropLibraryGen.Delegate(delegateLibrary);
   }

   @Override
   protected Object genericDispatch(Library originalLib, Object receiver, Message message, Object[] args, int offset) throws Exception {
      InteropLibrary lib = (InteropLibrary)originalLib;
      if (message.getParameterCount() - 1 != args.length - offset) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         throw new IllegalArgumentException("Invalid number of arguments.");
      } else {
         switch (message.getId()) {
            case 0:
               return lib.isNull(receiver);
            case 1:
               return lib.isBoolean(receiver);
            case 2:
               return lib.asBoolean(receiver);
            case 3:
               return lib.isExecutable(receiver);
            case 4:
               return lib.execute(receiver, (Object[])args[offset]);
            case 5:
               return lib.hasExecutableName(receiver);
            case 6:
               return lib.getExecutableName(receiver);
            case 7:
               return lib.hasDeclaringMetaObject(receiver);
            case 8:
               return lib.getDeclaringMetaObject(receiver);
            case 9:
               return lib.isInstantiable(receiver);
            case 10:
               return lib.instantiate(receiver, (Object[])args[offset]);
            case 11:
               return lib.isString(receiver);
            case 12:
               return lib.asString(receiver);
            case 13:
               return lib.asTruffleString(receiver);
            case 14:
               return lib.isNumber(receiver);
            case 15:
               return lib.fitsInByte(receiver);
            case 16:
               return lib.fitsInShort(receiver);
            case 17:
               return lib.fitsInInt(receiver);
            case 18:
               return lib.fitsInLong(receiver);
            case 19:
               return lib.fitsInFloat(receiver);
            case 20:
               return lib.fitsInDouble(receiver);
            case 21:
               return lib.asByte(receiver);
            case 22:
               return lib.asShort(receiver);
            case 23:
               return lib.asInt(receiver);
            case 24:
               return lib.asLong(receiver);
            case 25:
               return lib.asFloat(receiver);
            case 26:
               return lib.asDouble(receiver);
            case 27:
               return lib.hasMembers(receiver);
            case 28:
               return lib.getMembers(receiver, (Boolean)args[offset]);
            case 29:
               return lib.isMemberReadable(receiver, (String)args[offset]);
            case 30:
               return lib.readMember(receiver, (String)args[offset]);
            case 31:
               return lib.isMemberModifiable(receiver, (String)args[offset]);
            case 32:
               return lib.isMemberInsertable(receiver, (String)args[offset]);
            case 33:
               lib.writeMember(receiver, (String)args[offset], args[offset + 1]);
               return null;
            case 34:
               return lib.isMemberRemovable(receiver, (String)args[offset]);
            case 35:
               lib.removeMember(receiver, (String)args[offset]);
               return null;
            case 36:
               return lib.isMemberInvocable(receiver, (String)args[offset]);
            case 37:
               return lib.invokeMember(receiver, (String)args[offset], (Object[])args[offset + 1]);
            case 38:
               return lib.isMemberInternal(receiver, (String)args[offset]);
            case 39:
               return lib.hasMemberReadSideEffects(receiver, (String)args[offset]);
            case 40:
               return lib.hasMemberWriteSideEffects(receiver, (String)args[offset]);
            case 41:
               return lib.hasHashEntries(receiver);
            case 42:
               return lib.getHashSize(receiver);
            case 43:
               return lib.isHashEntryReadable(receiver, args[offset]);
            case 44:
               return lib.readHashValue(receiver, args[offset]);
            case 45:
               return lib.readHashValueOrDefault(receiver, args[offset], args[offset + 1]);
            case 46:
               return lib.isHashEntryModifiable(receiver, args[offset]);
            case 47:
               return lib.isHashEntryInsertable(receiver, args[offset]);
            case 48:
               return lib.isHashEntryWritable(receiver, args[offset]);
            case 49:
               lib.writeHashEntry(receiver, args[offset], args[offset + 1]);
               return null;
            case 50:
               return lib.isHashEntryRemovable(receiver, args[offset]);
            case 51:
               lib.removeHashEntry(receiver, args[offset]);
               return null;
            case 52:
               return lib.isHashEntryExisting(receiver, args[offset]);
            case 53:
               return lib.getHashEntriesIterator(receiver);
            case 54:
               return lib.getHashKeysIterator(receiver);
            case 55:
               return lib.getHashValuesIterator(receiver);
            case 56:
               return lib.hasArrayElements(receiver);
            case 57:
               return lib.readArrayElement(receiver, (Long)args[offset]);
            case 58:
               return lib.getArraySize(receiver);
            case 59:
               return lib.isArrayElementReadable(receiver, (Long)args[offset]);
            case 60:
               lib.writeArrayElement(receiver, (Long)args[offset], args[offset + 1]);
               return null;
            case 61:
               lib.removeArrayElement(receiver, (Long)args[offset]);
               return null;
            case 62:
               return lib.isArrayElementModifiable(receiver, (Long)args[offset]);
            case 63:
               return lib.isArrayElementInsertable(receiver, (Long)args[offset]);
            case 64:
               return lib.isArrayElementRemovable(receiver, (Long)args[offset]);
            case 65:
               return lib.hasBufferElements(receiver);
            case 66:
               return lib.isBufferWritable(receiver);
            case 67:
               return lib.getBufferSize(receiver);
            case 68:
               return lib.readBufferByte(receiver, (Long)args[offset]);
            case 69:
               lib.writeBufferByte(receiver, (Long)args[offset], (Byte)args[offset + 1]);
               return null;
            case 70:
               return lib.readBufferShort(receiver, (ByteOrder)args[offset], (Long)args[offset + 1]);
            case 71:
               lib.writeBufferShort(receiver, (ByteOrder)args[offset], (Long)args[offset + 1], (Short)args[offset + 2]);
               return null;
            case 72:
               return lib.readBufferInt(receiver, (ByteOrder)args[offset], (Long)args[offset + 1]);
            case 73:
               lib.writeBufferInt(receiver, (ByteOrder)args[offset], (Long)args[offset + 1], (Integer)args[offset + 2]);
               return null;
            case 74:
               return lib.readBufferLong(receiver, (ByteOrder)args[offset], (Long)args[offset + 1]);
            case 75:
               lib.writeBufferLong(receiver, (ByteOrder)args[offset], (Long)args[offset + 1], (Long)args[offset + 2]);
               return null;
            case 76:
               return lib.readBufferFloat(receiver, (ByteOrder)args[offset], (Long)args[offset + 1]);
            case 77:
               lib.writeBufferFloat(receiver, (ByteOrder)args[offset], (Long)args[offset + 1], (Float)args[offset + 2]);
               return null;
            case 78:
               return lib.readBufferDouble(receiver, (ByteOrder)args[offset], (Long)args[offset + 1]);
            case 79:
               lib.writeBufferDouble(receiver, (ByteOrder)args[offset], (Long)args[offset + 1], (Double)args[offset + 2]);
               return null;
            case 80:
               return lib.isPointer(receiver);
            case 81:
               return lib.asPointer(receiver);
            case 82:
               lib.toNative(receiver);
               return null;
            case 83:
               return lib.asInstant(receiver);
            case 84:
               return lib.isTimeZone(receiver);
            case 85:
               return lib.asTimeZone(receiver);
            case 86:
               return lib.isDate(receiver);
            case 87:
               return lib.asDate(receiver);
            case 88:
               return lib.isTime(receiver);
            case 89:
               return lib.asTime(receiver);
            case 90:
               return lib.isDuration(receiver);
            case 91:
               return lib.asDuration(receiver);
            case 92:
               return lib.isException(receiver);
            case 93:
               return lib.throwException(receiver);
            case 94:
               return lib.getExceptionType(receiver);
            case 95:
               return lib.isExceptionIncompleteSource(receiver);
            case 96:
               return lib.getExceptionExitStatus(receiver);
            case 97:
               return lib.hasExceptionCause(receiver);
            case 98:
               return lib.getExceptionCause(receiver);
            case 99:
               return lib.hasExceptionMessage(receiver);
            case 100:
               return lib.getExceptionMessage(receiver);
            case 101:
               return lib.hasExceptionStackTrace(receiver);
            case 102:
               return lib.getExceptionStackTrace(receiver);
            case 103:
               return lib.hasIterator(receiver);
            case 104:
               return lib.getIterator(receiver);
            case 105:
               return lib.isIterator(receiver);
            case 106:
               return lib.hasIteratorNextElement(receiver);
            case 107:
               return lib.getIteratorNextElement(receiver);
            case 108:
               return lib.hasSourceLocation(receiver);
            case 109:
               return lib.getSourceLocation(receiver);
            case 110:
               return lib.hasLanguage(receiver);
            case 111:
               return lib.getLanguage(receiver);
            case 112:
               return lib.hasMetaObject(receiver);
            case 113:
               return lib.getMetaObject(receiver);
            case 114:
               return lib.toDisplayString(receiver, (Boolean)args[offset]);
            case 115:
               return lib.isMetaObject(receiver);
            case 116:
               return lib.getMetaQualifiedName(receiver);
            case 117:
               return lib.getMetaSimpleName(receiver);
            case 118:
               return lib.isMetaInstance(receiver, args[offset]);
            case 119:
               return lib.hasMetaParents(receiver);
            case 120:
               return lib.getMetaParents(receiver);
            case 121:
               return lib.isIdenticalOrUndefined(receiver, args[offset]);
            case 122:
               return lib.isIdentical(receiver, args[offset], (InteropLibrary)args[offset + 1]);
            case 123:
               return lib.identityHashCode(receiver);
            case 124:
               return lib.isScope(receiver);
            case 125:
               return lib.hasScopeParent(receiver);
            case 126:
               return lib.getScopeParent(receiver);
            default:
               CompilerDirectives.transferToInterpreterAndInvalidate();
               throw new AbstractMethodError(message.toString());
         }
      }
   }

   protected InteropLibrary createDispatchImpl(int limit) {
      return new InteropLibraryGen.CachedDispatchFirst(null, null, limit);
   }

   protected InteropLibrary createUncachedDispatch() {
      return new InteropLibraryGen.UncachedDispatch();
   }

   private static Class<InteropLibrary> lazyLibraryClass() {
      try {
         return (Class<InteropLibrary>)Class.forName("com.oracle.truffle.api.interop.InteropLibrary", false, InteropLibraryGen.class.getClassLoader());
      } catch (ClassNotFoundException var1) {
         throw CompilerDirectives.shouldNotReachHere(var1);
      }
   }

   static {
      LibraryExport.register(LIBRARY_CLASS, new InteropLibraryGen.Default());
      LibraryFactory.register(LIBRARY_CLASS, INSTANCE);
   }

   @GeneratedBy(InteropLibrary.class)
   private abstract static class CachedDispatch extends InteropLibrary {
      @Node.Child
      InteropLibrary library;
      @Node.Child
      InteropLibraryGen.CachedDispatch next;

      CachedDispatch(InteropLibrary library, InteropLibraryGen.CachedDispatch next) {
         this.library = library;
         this.next = next;
      }

      abstract int getLimit();

      @ExplodeLoop
      @Override
      public boolean isNull(Object receiver_) {
         while (true) {
            InteropLibraryGen.CachedDispatch current = this;

            do {
               InteropLibrary thisLibrary = current.library;
               if (thisLibrary != null && thisLibrary.accepts(receiver_)) {
                  return thisLibrary.isNull(receiver_);
               }

               current = current.next;
            } while (current != null);

            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.specialize(receiver_);
         }
      }

      @ExplodeLoop
      @Override
      public boolean isBoolean(Object receiver_) {
         while (true) {
            InteropLibraryGen.CachedDispatch current = this;

            do {
               InteropLibrary thisLibrary = current.library;
               if (thisLibrary != null && thisLibrary.accepts(receiver_)) {
                  return thisLibrary.isBoolean(receiver_);
               }

               current = current.next;
            } while (current != null);

            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.specialize(receiver_);
         }
      }

      @ExplodeLoop
      @Override
      public boolean asBoolean(Object receiver_) throws UnsupportedMessageException {
         while (true) {
            InteropLibraryGen.CachedDispatch current = this;

            do {
               InteropLibrary thisLibrary = current.library;
               if (thisLibrary != null && thisLibrary.accepts(receiver_)) {
                  return thisLibrary.asBoolean(receiver_);
               }

               current = current.next;
            } while (current != null);

            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.specialize(receiver_);
         }
      }

      @ExplodeLoop
      @Override
      public boolean isExecutable(Object receiver_) {
         while (true) {
            InteropLibraryGen.CachedDispatch current = this;

            do {
               InteropLibrary thisLibrary = current.library;
               if (thisLibrary != null && thisLibrary.accepts(receiver_)) {
                  return thisLibrary.isExecutable(receiver_);
               }

               current = current.next;
            } while (current != null);

            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.specialize(receiver_);
         }
      }

      @ExplodeLoop
      @Override
      public Object execute(Object receiver_, Object... arguments) throws UnsupportedTypeException, ArityException, UnsupportedMessageException {
         while (true) {
            InteropLibraryGen.CachedDispatch current = this;

            do {
               InteropLibrary thisLibrary = current.library;
               if (thisLibrary != null && thisLibrary.accepts(receiver_)) {
                  return thisLibrary.execute(receiver_, arguments);
               }

               current = current.next;
            } while (current != null);

            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.specialize(receiver_);
         }
      }

      @ExplodeLoop
      @Override
      public boolean hasExecutableName(Object receiver_) {
         while (true) {
            InteropLibraryGen.CachedDispatch current = this;

            do {
               InteropLibrary thisLibrary = current.library;
               if (thisLibrary != null && thisLibrary.accepts(receiver_)) {
                  return thisLibrary.hasExecutableName(receiver_);
               }

               current = current.next;
            } while (current != null);

            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.specialize(receiver_);
         }
      }

      @ExplodeLoop
      @Override
      public Object getExecutableName(Object receiver_) throws UnsupportedMessageException {
         while (true) {
            InteropLibraryGen.CachedDispatch current = this;

            do {
               InteropLibrary thisLibrary = current.library;
               if (thisLibrary != null && thisLibrary.accepts(receiver_)) {
                  return thisLibrary.getExecutableName(receiver_);
               }

               current = current.next;
            } while (current != null);

            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.specialize(receiver_);
         }
      }

      @ExplodeLoop
      @Override
      public boolean hasDeclaringMetaObject(Object receiver_) {
         while (true) {
            InteropLibraryGen.CachedDispatch current = this;

            do {
               InteropLibrary thisLibrary = current.library;
               if (thisLibrary != null && thisLibrary.accepts(receiver_)) {
                  return thisLibrary.hasDeclaringMetaObject(receiver_);
               }

               current = current.next;
            } while (current != null);

            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.specialize(receiver_);
         }
      }

      @ExplodeLoop
      @Override
      public Object getDeclaringMetaObject(Object receiver_) throws UnsupportedMessageException {
         while (true) {
            InteropLibraryGen.CachedDispatch current = this;

            do {
               InteropLibrary thisLibrary = current.library;
               if (thisLibrary != null && thisLibrary.accepts(receiver_)) {
                  return thisLibrary.getDeclaringMetaObject(receiver_);
               }

               current = current.next;
            } while (current != null);

            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.specialize(receiver_);
         }
      }

      @ExplodeLoop
      @Override
      public boolean isInstantiable(Object receiver_) {
         while (true) {
            InteropLibraryGen.CachedDispatch current = this;

            do {
               InteropLibrary thisLibrary = current.library;
               if (thisLibrary != null && thisLibrary.accepts(receiver_)) {
                  return thisLibrary.isInstantiable(receiver_);
               }

               current = current.next;
            } while (current != null);

            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.specialize(receiver_);
         }
      }

      @ExplodeLoop
      @Override
      public Object instantiate(Object receiver_, Object... arguments) throws UnsupportedTypeException, ArityException, UnsupportedMessageException {
         while (true) {
            InteropLibraryGen.CachedDispatch current = this;

            do {
               InteropLibrary thisLibrary = current.library;
               if (thisLibrary != null && thisLibrary.accepts(receiver_)) {
                  return thisLibrary.instantiate(receiver_, arguments);
               }

               current = current.next;
            } while (current != null);

            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.specialize(receiver_);
         }
      }

      @ExplodeLoop
      @Override
      public boolean isString(Object receiver_) {
         while (true) {
            InteropLibraryGen.CachedDispatch current = this;

            do {
               InteropLibrary thisLibrary = current.library;
               if (thisLibrary != null && thisLibrary.accepts(receiver_)) {
                  return thisLibrary.isString(receiver_);
               }

               current = current.next;
            } while (current != null);

            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.specialize(receiver_);
         }
      }

      @ExplodeLoop
      @Override
      public String asString(Object receiver_) throws UnsupportedMessageException {
         while (true) {
            InteropLibraryGen.CachedDispatch current = this;

            do {
               InteropLibrary thisLibrary = current.library;
               if (thisLibrary != null && thisLibrary.accepts(receiver_)) {
                  return thisLibrary.asString(receiver_);
               }

               current = current.next;
            } while (current != null);

            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.specialize(receiver_);
         }
      }

      @ExplodeLoop
      @Override
      public TruffleString asTruffleString(Object receiver_) throws UnsupportedMessageException {
         while (true) {
            InteropLibraryGen.CachedDispatch current = this;

            do {
               InteropLibrary thisLibrary = current.library;
               if (thisLibrary != null && thisLibrary.accepts(receiver_)) {
                  return thisLibrary.asTruffleString(receiver_);
               }

               current = current.next;
            } while (current != null);

            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.specialize(receiver_);
         }
      }

      @ExplodeLoop
      @Override
      public boolean isNumber(Object receiver_) {
         while (true) {
            InteropLibraryGen.CachedDispatch current = this;

            do {
               InteropLibrary thisLibrary = current.library;
               if (thisLibrary != null && thisLibrary.accepts(receiver_)) {
                  return thisLibrary.isNumber(receiver_);
               }

               current = current.next;
            } while (current != null);

            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.specialize(receiver_);
         }
      }

      @ExplodeLoop
      @Override
      public boolean fitsInByte(Object receiver_) {
         while (true) {
            InteropLibraryGen.CachedDispatch current = this;

            do {
               InteropLibrary thisLibrary = current.library;
               if (thisLibrary != null && thisLibrary.accepts(receiver_)) {
                  return thisLibrary.fitsInByte(receiver_);
               }

               current = current.next;
            } while (current != null);

            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.specialize(receiver_);
         }
      }

      @ExplodeLoop
      @Override
      public boolean fitsInShort(Object receiver_) {
         while (true) {
            InteropLibraryGen.CachedDispatch current = this;

            do {
               InteropLibrary thisLibrary = current.library;
               if (thisLibrary != null && thisLibrary.accepts(receiver_)) {
                  return thisLibrary.fitsInShort(receiver_);
               }

               current = current.next;
            } while (current != null);

            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.specialize(receiver_);
         }
      }

      @ExplodeLoop
      @Override
      public boolean fitsInInt(Object receiver_) {
         while (true) {
            InteropLibraryGen.CachedDispatch current = this;

            do {
               InteropLibrary thisLibrary = current.library;
               if (thisLibrary != null && thisLibrary.accepts(receiver_)) {
                  return thisLibrary.fitsInInt(receiver_);
               }

               current = current.next;
            } while (current != null);

            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.specialize(receiver_);
         }
      }

      @ExplodeLoop
      @Override
      public boolean fitsInLong(Object receiver_) {
         while (true) {
            InteropLibraryGen.CachedDispatch current = this;

            do {
               InteropLibrary thisLibrary = current.library;
               if (thisLibrary != null && thisLibrary.accepts(receiver_)) {
                  return thisLibrary.fitsInLong(receiver_);
               }

               current = current.next;
            } while (current != null);

            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.specialize(receiver_);
         }
      }

      @ExplodeLoop
      @Override
      public boolean fitsInFloat(Object receiver_) {
         while (true) {
            InteropLibraryGen.CachedDispatch current = this;

            do {
               InteropLibrary thisLibrary = current.library;
               if (thisLibrary != null && thisLibrary.accepts(receiver_)) {
                  return thisLibrary.fitsInFloat(receiver_);
               }

               current = current.next;
            } while (current != null);

            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.specialize(receiver_);
         }
      }

      @ExplodeLoop
      @Override
      public boolean fitsInDouble(Object receiver_) {
         while (true) {
            InteropLibraryGen.CachedDispatch current = this;

            do {
               InteropLibrary thisLibrary = current.library;
               if (thisLibrary != null && thisLibrary.accepts(receiver_)) {
                  return thisLibrary.fitsInDouble(receiver_);
               }

               current = current.next;
            } while (current != null);

            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.specialize(receiver_);
         }
      }

      @ExplodeLoop
      @Override
      public byte asByte(Object receiver_) throws UnsupportedMessageException {
         while (true) {
            InteropLibraryGen.CachedDispatch current = this;

            do {
               InteropLibrary thisLibrary = current.library;
               if (thisLibrary != null && thisLibrary.accepts(receiver_)) {
                  return thisLibrary.asByte(receiver_);
               }

               current = current.next;
            } while (current != null);

            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.specialize(receiver_);
         }
      }

      @ExplodeLoop
      @Override
      public short asShort(Object receiver_) throws UnsupportedMessageException {
         while (true) {
            InteropLibraryGen.CachedDispatch current = this;

            do {
               InteropLibrary thisLibrary = current.library;
               if (thisLibrary != null && thisLibrary.accepts(receiver_)) {
                  return thisLibrary.asShort(receiver_);
               }

               current = current.next;
            } while (current != null);

            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.specialize(receiver_);
         }
      }

      @ExplodeLoop
      @Override
      public int asInt(Object receiver_) throws UnsupportedMessageException {
         while (true) {
            InteropLibraryGen.CachedDispatch current = this;

            do {
               InteropLibrary thisLibrary = current.library;
               if (thisLibrary != null && thisLibrary.accepts(receiver_)) {
                  return thisLibrary.asInt(receiver_);
               }

               current = current.next;
            } while (current != null);

            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.specialize(receiver_);
         }
      }

      @ExplodeLoop
      @Override
      public long asLong(Object receiver_) throws UnsupportedMessageException {
         while (true) {
            InteropLibraryGen.CachedDispatch current = this;

            do {
               InteropLibrary thisLibrary = current.library;
               if (thisLibrary != null && thisLibrary.accepts(receiver_)) {
                  return thisLibrary.asLong(receiver_);
               }

               current = current.next;
            } while (current != null);

            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.specialize(receiver_);
         }
      }

      @ExplodeLoop
      @Override
      public float asFloat(Object receiver_) throws UnsupportedMessageException {
         while (true) {
            InteropLibraryGen.CachedDispatch current = this;

            do {
               InteropLibrary thisLibrary = current.library;
               if (thisLibrary != null && thisLibrary.accepts(receiver_)) {
                  return thisLibrary.asFloat(receiver_);
               }

               current = current.next;
            } while (current != null);

            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.specialize(receiver_);
         }
      }

      @ExplodeLoop
      @Override
      public double asDouble(Object receiver_) throws UnsupportedMessageException {
         while (true) {
            InteropLibraryGen.CachedDispatch current = this;

            do {
               InteropLibrary thisLibrary = current.library;
               if (thisLibrary != null && thisLibrary.accepts(receiver_)) {
                  return thisLibrary.asDouble(receiver_);
               }

               current = current.next;
            } while (current != null);

            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.specialize(receiver_);
         }
      }

      @ExplodeLoop
      @Override
      public boolean hasMembers(Object receiver_) {
         while (true) {
            InteropLibraryGen.CachedDispatch current = this;

            do {
               InteropLibrary thisLibrary = current.library;
               if (thisLibrary != null && thisLibrary.accepts(receiver_)) {
                  return thisLibrary.hasMembers(receiver_);
               }

               current = current.next;
            } while (current != null);

            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.specialize(receiver_);
         }
      }

      @ExplodeLoop
      @Override
      public Object getMembers(Object receiver_, boolean includeInternal) throws UnsupportedMessageException {
         while (true) {
            InteropLibraryGen.CachedDispatch current = this;

            do {
               InteropLibrary thisLibrary = current.library;
               if (thisLibrary != null && thisLibrary.accepts(receiver_)) {
                  return thisLibrary.getMembers(receiver_, includeInternal);
               }

               current = current.next;
            } while (current != null);

            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.specialize(receiver_);
         }
      }

      @ExplodeLoop
      @Override
      public boolean isMemberReadable(Object receiver_, String member) {
         while (true) {
            InteropLibraryGen.CachedDispatch current = this;

            do {
               InteropLibrary thisLibrary = current.library;
               if (thisLibrary != null && thisLibrary.accepts(receiver_)) {
                  return thisLibrary.isMemberReadable(receiver_, member);
               }

               current = current.next;
            } while (current != null);

            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.specialize(receiver_);
         }
      }

      @ExplodeLoop
      @Override
      public Object readMember(Object receiver_, String member) throws UnsupportedMessageException, UnknownIdentifierException {
         while (true) {
            InteropLibraryGen.CachedDispatch current = this;

            do {
               InteropLibrary thisLibrary = current.library;
               if (thisLibrary != null && thisLibrary.accepts(receiver_)) {
                  return thisLibrary.readMember(receiver_, member);
               }

               current = current.next;
            } while (current != null);

            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.specialize(receiver_);
         }
      }

      @ExplodeLoop
      @Override
      public boolean isMemberModifiable(Object receiver_, String member) {
         while (true) {
            InteropLibraryGen.CachedDispatch current = this;

            do {
               InteropLibrary thisLibrary = current.library;
               if (thisLibrary != null && thisLibrary.accepts(receiver_)) {
                  return thisLibrary.isMemberModifiable(receiver_, member);
               }

               current = current.next;
            } while (current != null);

            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.specialize(receiver_);
         }
      }

      @ExplodeLoop
      @Override
      public boolean isMemberInsertable(Object receiver_, String member) {
         while (true) {
            InteropLibraryGen.CachedDispatch current = this;

            do {
               InteropLibrary thisLibrary = current.library;
               if (thisLibrary != null && thisLibrary.accepts(receiver_)) {
                  return thisLibrary.isMemberInsertable(receiver_, member);
               }

               current = current.next;
            } while (current != null);

            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.specialize(receiver_);
         }
      }

      @ExplodeLoop
      @Override
      public void writeMember(Object receiver_, String member, Object value) throws UnsupportedMessageException, UnknownIdentifierException, UnsupportedTypeException {
         while (true) {
            InteropLibraryGen.CachedDispatch current = this;

            do {
               InteropLibrary thisLibrary = current.library;
               if (thisLibrary != null && thisLibrary.accepts(receiver_)) {
                  thisLibrary.writeMember(receiver_, member, value);
                  return;
               }

               current = current.next;
            } while (current != null);

            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.specialize(receiver_);
         }
      }

      @ExplodeLoop
      @Override
      public boolean isMemberRemovable(Object receiver_, String member) {
         while (true) {
            InteropLibraryGen.CachedDispatch current = this;

            do {
               InteropLibrary thisLibrary = current.library;
               if (thisLibrary != null && thisLibrary.accepts(receiver_)) {
                  return thisLibrary.isMemberRemovable(receiver_, member);
               }

               current = current.next;
            } while (current != null);

            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.specialize(receiver_);
         }
      }

      @ExplodeLoop
      @Override
      public void removeMember(Object receiver_, String member) throws UnsupportedMessageException, UnknownIdentifierException {
         while (true) {
            InteropLibraryGen.CachedDispatch current = this;

            do {
               InteropLibrary thisLibrary = current.library;
               if (thisLibrary != null && thisLibrary.accepts(receiver_)) {
                  thisLibrary.removeMember(receiver_, member);
                  return;
               }

               current = current.next;
            } while (current != null);

            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.specialize(receiver_);
         }
      }

      @ExplodeLoop
      @Override
      public boolean isMemberInvocable(Object receiver_, String member) {
         while (true) {
            InteropLibraryGen.CachedDispatch current = this;

            do {
               InteropLibrary thisLibrary = current.library;
               if (thisLibrary != null && thisLibrary.accepts(receiver_)) {
                  return thisLibrary.isMemberInvocable(receiver_, member);
               }

               current = current.next;
            } while (current != null);

            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.specialize(receiver_);
         }
      }

      @ExplodeLoop
      @Override
      public Object invokeMember(Object receiver_, String member, Object... arguments) throws UnsupportedMessageException, ArityException, UnknownIdentifierException, UnsupportedTypeException {
         while (true) {
            InteropLibraryGen.CachedDispatch current = this;

            do {
               InteropLibrary thisLibrary = current.library;
               if (thisLibrary != null && thisLibrary.accepts(receiver_)) {
                  return thisLibrary.invokeMember(receiver_, member, arguments);
               }

               current = current.next;
            } while (current != null);

            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.specialize(receiver_);
         }
      }

      @ExplodeLoop
      @Override
      public boolean isMemberInternal(Object receiver_, String member) {
         while (true) {
            InteropLibraryGen.CachedDispatch current = this;

            do {
               InteropLibrary thisLibrary = current.library;
               if (thisLibrary != null && thisLibrary.accepts(receiver_)) {
                  return thisLibrary.isMemberInternal(receiver_, member);
               }

               current = current.next;
            } while (current != null);

            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.specialize(receiver_);
         }
      }

      @ExplodeLoop
      @Override
      public boolean hasMemberReadSideEffects(Object receiver_, String member) {
         while (true) {
            InteropLibraryGen.CachedDispatch current = this;

            do {
               InteropLibrary thisLibrary = current.library;
               if (thisLibrary != null && thisLibrary.accepts(receiver_)) {
                  return thisLibrary.hasMemberReadSideEffects(receiver_, member);
               }

               current = current.next;
            } while (current != null);

            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.specialize(receiver_);
         }
      }

      @ExplodeLoop
      @Override
      public boolean hasMemberWriteSideEffects(Object receiver_, String member) {
         while (true) {
            InteropLibraryGen.CachedDispatch current = this;

            do {
               InteropLibrary thisLibrary = current.library;
               if (thisLibrary != null && thisLibrary.accepts(receiver_)) {
                  return thisLibrary.hasMemberWriteSideEffects(receiver_, member);
               }

               current = current.next;
            } while (current != null);

            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.specialize(receiver_);
         }
      }

      @ExplodeLoop
      @Override
      public boolean hasHashEntries(Object receiver_) {
         while (true) {
            InteropLibraryGen.CachedDispatch current = this;

            do {
               InteropLibrary thisLibrary = current.library;
               if (thisLibrary != null && thisLibrary.accepts(receiver_)) {
                  return thisLibrary.hasHashEntries(receiver_);
               }

               current = current.next;
            } while (current != null);

            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.specialize(receiver_);
         }
      }

      @ExplodeLoop
      @Override
      public long getHashSize(Object receiver_) throws UnsupportedMessageException {
         while (true) {
            InteropLibraryGen.CachedDispatch current = this;

            do {
               InteropLibrary thisLibrary = current.library;
               if (thisLibrary != null && thisLibrary.accepts(receiver_)) {
                  return thisLibrary.getHashSize(receiver_);
               }

               current = current.next;
            } while (current != null);

            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.specialize(receiver_);
         }
      }

      @ExplodeLoop
      @Override
      public boolean isHashEntryReadable(Object receiver_, Object key) {
         while (true) {
            InteropLibraryGen.CachedDispatch current = this;

            do {
               InteropLibrary thisLibrary = current.library;
               if (thisLibrary != null && thisLibrary.accepts(receiver_)) {
                  return thisLibrary.isHashEntryReadable(receiver_, key);
               }

               current = current.next;
            } while (current != null);

            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.specialize(receiver_);
         }
      }

      @ExplodeLoop
      @Override
      public Object readHashValue(Object receiver_, Object key) throws UnsupportedMessageException, UnknownKeyException {
         while (true) {
            InteropLibraryGen.CachedDispatch current = this;

            do {
               InteropLibrary thisLibrary = current.library;
               if (thisLibrary != null && thisLibrary.accepts(receiver_)) {
                  return thisLibrary.readHashValue(receiver_, key);
               }

               current = current.next;
            } while (current != null);

            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.specialize(receiver_);
         }
      }

      @ExplodeLoop
      @Override
      public Object readHashValueOrDefault(Object receiver_, Object key, Object defaultValue) throws UnsupportedMessageException {
         while (true) {
            InteropLibraryGen.CachedDispatch current = this;

            do {
               InteropLibrary thisLibrary = current.library;
               if (thisLibrary != null && thisLibrary.accepts(receiver_)) {
                  return thisLibrary.readHashValueOrDefault(receiver_, key, defaultValue);
               }

               current = current.next;
            } while (current != null);

            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.specialize(receiver_);
         }
      }

      @ExplodeLoop
      @Override
      public boolean isHashEntryModifiable(Object receiver_, Object key) {
         while (true) {
            InteropLibraryGen.CachedDispatch current = this;

            do {
               InteropLibrary thisLibrary = current.library;
               if (thisLibrary != null && thisLibrary.accepts(receiver_)) {
                  return thisLibrary.isHashEntryModifiable(receiver_, key);
               }

               current = current.next;
            } while (current != null);

            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.specialize(receiver_);
         }
      }

      @ExplodeLoop
      @Override
      public boolean isHashEntryInsertable(Object receiver_, Object key) {
         while (true) {
            InteropLibraryGen.CachedDispatch current = this;

            do {
               InteropLibrary thisLibrary = current.library;
               if (thisLibrary != null && thisLibrary.accepts(receiver_)) {
                  return thisLibrary.isHashEntryInsertable(receiver_, key);
               }

               current = current.next;
            } while (current != null);

            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.specialize(receiver_);
         }
      }

      @ExplodeLoop
      @Override
      public boolean isHashEntryWritable(Object receiver_, Object key) {
         while (true) {
            InteropLibraryGen.CachedDispatch current = this;

            do {
               InteropLibrary thisLibrary = current.library;
               if (thisLibrary != null && thisLibrary.accepts(receiver_)) {
                  return thisLibrary.isHashEntryWritable(receiver_, key);
               }

               current = current.next;
            } while (current != null);

            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.specialize(receiver_);
         }
      }

      @ExplodeLoop
      @Override
      public void writeHashEntry(Object receiver_, Object key, Object value) throws UnsupportedMessageException, UnknownKeyException, UnsupportedTypeException {
         while (true) {
            InteropLibraryGen.CachedDispatch current = this;

            do {
               InteropLibrary thisLibrary = current.library;
               if (thisLibrary != null && thisLibrary.accepts(receiver_)) {
                  thisLibrary.writeHashEntry(receiver_, key, value);
                  return;
               }

               current = current.next;
            } while (current != null);

            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.specialize(receiver_);
         }
      }

      @ExplodeLoop
      @Override
      public boolean isHashEntryRemovable(Object receiver_, Object key) {
         while (true) {
            InteropLibraryGen.CachedDispatch current = this;

            do {
               InteropLibrary thisLibrary = current.library;
               if (thisLibrary != null && thisLibrary.accepts(receiver_)) {
                  return thisLibrary.isHashEntryRemovable(receiver_, key);
               }

               current = current.next;
            } while (current != null);

            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.specialize(receiver_);
         }
      }

      @ExplodeLoop
      @Override
      public void removeHashEntry(Object receiver_, Object key) throws UnsupportedMessageException, UnknownKeyException {
         while (true) {
            InteropLibraryGen.CachedDispatch current = this;

            do {
               InteropLibrary thisLibrary = current.library;
               if (thisLibrary != null && thisLibrary.accepts(receiver_)) {
                  thisLibrary.removeHashEntry(receiver_, key);
                  return;
               }

               current = current.next;
            } while (current != null);

            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.specialize(receiver_);
         }
      }

      @ExplodeLoop
      @Override
      public boolean isHashEntryExisting(Object receiver_, Object key) {
         while (true) {
            InteropLibraryGen.CachedDispatch current = this;

            do {
               InteropLibrary thisLibrary = current.library;
               if (thisLibrary != null && thisLibrary.accepts(receiver_)) {
                  return thisLibrary.isHashEntryExisting(receiver_, key);
               }

               current = current.next;
            } while (current != null);

            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.specialize(receiver_);
         }
      }

      @ExplodeLoop
      @Override
      public Object getHashEntriesIterator(Object receiver_) throws UnsupportedMessageException {
         while (true) {
            InteropLibraryGen.CachedDispatch current = this;

            do {
               InteropLibrary thisLibrary = current.library;
               if (thisLibrary != null && thisLibrary.accepts(receiver_)) {
                  return thisLibrary.getHashEntriesIterator(receiver_);
               }

               current = current.next;
            } while (current != null);

            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.specialize(receiver_);
         }
      }

      @ExplodeLoop
      @Override
      public Object getHashKeysIterator(Object receiver_) throws UnsupportedMessageException {
         while (true) {
            InteropLibraryGen.CachedDispatch current = this;

            do {
               InteropLibrary thisLibrary = current.library;
               if (thisLibrary != null && thisLibrary.accepts(receiver_)) {
                  return thisLibrary.getHashKeysIterator(receiver_);
               }

               current = current.next;
            } while (current != null);

            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.specialize(receiver_);
         }
      }

      @ExplodeLoop
      @Override
      public Object getHashValuesIterator(Object receiver_) throws UnsupportedMessageException {
         while (true) {
            InteropLibraryGen.CachedDispatch current = this;

            do {
               InteropLibrary thisLibrary = current.library;
               if (thisLibrary != null && thisLibrary.accepts(receiver_)) {
                  return thisLibrary.getHashValuesIterator(receiver_);
               }

               current = current.next;
            } while (current != null);

            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.specialize(receiver_);
         }
      }

      @ExplodeLoop
      @Override
      public boolean hasArrayElements(Object receiver_) {
         while (true) {
            InteropLibraryGen.CachedDispatch current = this;

            do {
               InteropLibrary thisLibrary = current.library;
               if (thisLibrary != null && thisLibrary.accepts(receiver_)) {
                  return thisLibrary.hasArrayElements(receiver_);
               }

               current = current.next;
            } while (current != null);

            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.specialize(receiver_);
         }
      }

      @ExplodeLoop
      @Override
      public Object readArrayElement(Object receiver_, long index) throws UnsupportedMessageException, InvalidArrayIndexException {
         while (true) {
            InteropLibraryGen.CachedDispatch current = this;

            do {
               InteropLibrary thisLibrary = current.library;
               if (thisLibrary != null && thisLibrary.accepts(receiver_)) {
                  return thisLibrary.readArrayElement(receiver_, index);
               }

               current = current.next;
            } while (current != null);

            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.specialize(receiver_);
         }
      }

      @ExplodeLoop
      @Override
      public long getArraySize(Object receiver_) throws UnsupportedMessageException {
         while (true) {
            InteropLibraryGen.CachedDispatch current = this;

            do {
               InteropLibrary thisLibrary = current.library;
               if (thisLibrary != null && thisLibrary.accepts(receiver_)) {
                  return thisLibrary.getArraySize(receiver_);
               }

               current = current.next;
            } while (current != null);

            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.specialize(receiver_);
         }
      }

      @ExplodeLoop
      @Override
      public boolean isArrayElementReadable(Object receiver_, long index) {
         while (true) {
            InteropLibraryGen.CachedDispatch current = this;

            do {
               InteropLibrary thisLibrary = current.library;
               if (thisLibrary != null && thisLibrary.accepts(receiver_)) {
                  return thisLibrary.isArrayElementReadable(receiver_, index);
               }

               current = current.next;
            } while (current != null);

            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.specialize(receiver_);
         }
      }

      @ExplodeLoop
      @Override
      public void writeArrayElement(Object receiver_, long index, Object value) throws UnsupportedMessageException, UnsupportedTypeException, InvalidArrayIndexException {
         while (true) {
            InteropLibraryGen.CachedDispatch current = this;

            do {
               InteropLibrary thisLibrary = current.library;
               if (thisLibrary != null && thisLibrary.accepts(receiver_)) {
                  thisLibrary.writeArrayElement(receiver_, index, value);
                  return;
               }

               current = current.next;
            } while (current != null);

            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.specialize(receiver_);
         }
      }

      @ExplodeLoop
      @Override
      public void removeArrayElement(Object receiver_, long index) throws UnsupportedMessageException, InvalidArrayIndexException {
         while (true) {
            InteropLibraryGen.CachedDispatch current = this;

            do {
               InteropLibrary thisLibrary = current.library;
               if (thisLibrary != null && thisLibrary.accepts(receiver_)) {
                  thisLibrary.removeArrayElement(receiver_, index);
                  return;
               }

               current = current.next;
            } while (current != null);

            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.specialize(receiver_);
         }
      }

      @ExplodeLoop
      @Override
      public boolean isArrayElementModifiable(Object receiver_, long index) {
         while (true) {
            InteropLibraryGen.CachedDispatch current = this;

            do {
               InteropLibrary thisLibrary = current.library;
               if (thisLibrary != null && thisLibrary.accepts(receiver_)) {
                  return thisLibrary.isArrayElementModifiable(receiver_, index);
               }

               current = current.next;
            } while (current != null);

            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.specialize(receiver_);
         }
      }

      @ExplodeLoop
      @Override
      public boolean isArrayElementInsertable(Object receiver_, long index) {
         while (true) {
            InteropLibraryGen.CachedDispatch current = this;

            do {
               InteropLibrary thisLibrary = current.library;
               if (thisLibrary != null && thisLibrary.accepts(receiver_)) {
                  return thisLibrary.isArrayElementInsertable(receiver_, index);
               }

               current = current.next;
            } while (current != null);

            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.specialize(receiver_);
         }
      }

      @ExplodeLoop
      @Override
      public boolean isArrayElementRemovable(Object receiver_, long index) {
         while (true) {
            InteropLibraryGen.CachedDispatch current = this;

            do {
               InteropLibrary thisLibrary = current.library;
               if (thisLibrary != null && thisLibrary.accepts(receiver_)) {
                  return thisLibrary.isArrayElementRemovable(receiver_, index);
               }

               current = current.next;
            } while (current != null);

            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.specialize(receiver_);
         }
      }

      @ExplodeLoop
      @Override
      public boolean hasBufferElements(Object receiver_) {
         while (true) {
            InteropLibraryGen.CachedDispatch current = this;

            do {
               InteropLibrary thisLibrary = current.library;
               if (thisLibrary != null && thisLibrary.accepts(receiver_)) {
                  return thisLibrary.hasBufferElements(receiver_);
               }

               current = current.next;
            } while (current != null);

            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.specialize(receiver_);
         }
      }

      @ExplodeLoop
      @Override
      public boolean isBufferWritable(Object receiver_) throws UnsupportedMessageException {
         while (true) {
            InteropLibraryGen.CachedDispatch current = this;

            do {
               InteropLibrary thisLibrary = current.library;
               if (thisLibrary != null && thisLibrary.accepts(receiver_)) {
                  return thisLibrary.isBufferWritable(receiver_);
               }

               current = current.next;
            } while (current != null);

            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.specialize(receiver_);
         }
      }

      @ExplodeLoop
      @Override
      public long getBufferSize(Object receiver_) throws UnsupportedMessageException {
         while (true) {
            InteropLibraryGen.CachedDispatch current = this;

            do {
               InteropLibrary thisLibrary = current.library;
               if (thisLibrary != null && thisLibrary.accepts(receiver_)) {
                  return thisLibrary.getBufferSize(receiver_);
               }

               current = current.next;
            } while (current != null);

            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.specialize(receiver_);
         }
      }

      @ExplodeLoop
      @Override
      public byte readBufferByte(Object receiver_, long byteOffset) throws UnsupportedMessageException, InvalidBufferOffsetException {
         while (true) {
            InteropLibraryGen.CachedDispatch current = this;

            do {
               InteropLibrary thisLibrary = current.library;
               if (thisLibrary != null && thisLibrary.accepts(receiver_)) {
                  return thisLibrary.readBufferByte(receiver_, byteOffset);
               }

               current = current.next;
            } while (current != null);

            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.specialize(receiver_);
         }
      }

      @ExplodeLoop
      @Override
      public void writeBufferByte(Object receiver_, long byteOffset, byte value) throws UnsupportedMessageException, InvalidBufferOffsetException {
         while (true) {
            InteropLibraryGen.CachedDispatch current = this;

            do {
               InteropLibrary thisLibrary = current.library;
               if (thisLibrary != null && thisLibrary.accepts(receiver_)) {
                  thisLibrary.writeBufferByte(receiver_, byteOffset, value);
                  return;
               }

               current = current.next;
            } while (current != null);

            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.specialize(receiver_);
         }
      }

      @ExplodeLoop
      @Override
      public short readBufferShort(Object receiver_, ByteOrder order, long byteOffset) throws UnsupportedMessageException, InvalidBufferOffsetException {
         while (true) {
            InteropLibraryGen.CachedDispatch current = this;

            do {
               InteropLibrary thisLibrary = current.library;
               if (thisLibrary != null && thisLibrary.accepts(receiver_)) {
                  return thisLibrary.readBufferShort(receiver_, order, byteOffset);
               }

               current = current.next;
            } while (current != null);

            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.specialize(receiver_);
         }
      }

      @ExplodeLoop
      @Override
      public void writeBufferShort(Object receiver_, ByteOrder order, long byteOffset, short value) throws UnsupportedMessageException, InvalidBufferOffsetException {
         while (true) {
            InteropLibraryGen.CachedDispatch current = this;

            do {
               InteropLibrary thisLibrary = current.library;
               if (thisLibrary != null && thisLibrary.accepts(receiver_)) {
                  thisLibrary.writeBufferShort(receiver_, order, byteOffset, value);
                  return;
               }

               current = current.next;
            } while (current != null);

            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.specialize(receiver_);
         }
      }

      @ExplodeLoop
      @Override
      public int readBufferInt(Object receiver_, ByteOrder order, long byteOffset) throws UnsupportedMessageException, InvalidBufferOffsetException {
         while (true) {
            InteropLibraryGen.CachedDispatch current = this;

            do {
               InteropLibrary thisLibrary = current.library;
               if (thisLibrary != null && thisLibrary.accepts(receiver_)) {
                  return thisLibrary.readBufferInt(receiver_, order, byteOffset);
               }

               current = current.next;
            } while (current != null);

            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.specialize(receiver_);
         }
      }

      @ExplodeLoop
      @Override
      public void writeBufferInt(Object receiver_, ByteOrder order, long byteOffset, int value) throws UnsupportedMessageException, InvalidBufferOffsetException {
         while (true) {
            InteropLibraryGen.CachedDispatch current = this;

            do {
               InteropLibrary thisLibrary = current.library;
               if (thisLibrary != null && thisLibrary.accepts(receiver_)) {
                  thisLibrary.writeBufferInt(receiver_, order, byteOffset, value);
                  return;
               }

               current = current.next;
            } while (current != null);

            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.specialize(receiver_);
         }
      }

      @ExplodeLoop
      @Override
      public long readBufferLong(Object receiver_, ByteOrder order, long byteOffset) throws UnsupportedMessageException, InvalidBufferOffsetException {
         while (true) {
            InteropLibraryGen.CachedDispatch current = this;

            do {
               InteropLibrary thisLibrary = current.library;
               if (thisLibrary != null && thisLibrary.accepts(receiver_)) {
                  return thisLibrary.readBufferLong(receiver_, order, byteOffset);
               }

               current = current.next;
            } while (current != null);

            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.specialize(receiver_);
         }
      }

      @ExplodeLoop
      @Override
      public void writeBufferLong(Object receiver_, ByteOrder order, long byteOffset, long value) throws UnsupportedMessageException, InvalidBufferOffsetException {
         while (true) {
            InteropLibraryGen.CachedDispatch current = this;

            do {
               InteropLibrary thisLibrary = current.library;
               if (thisLibrary != null && thisLibrary.accepts(receiver_)) {
                  thisLibrary.writeBufferLong(receiver_, order, byteOffset, value);
                  return;
               }

               current = current.next;
            } while (current != null);

            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.specialize(receiver_);
         }
      }

      @ExplodeLoop
      @Override
      public float readBufferFloat(Object receiver_, ByteOrder order, long byteOffset) throws UnsupportedMessageException, InvalidBufferOffsetException {
         while (true) {
            InteropLibraryGen.CachedDispatch current = this;

            do {
               InteropLibrary thisLibrary = current.library;
               if (thisLibrary != null && thisLibrary.accepts(receiver_)) {
                  return thisLibrary.readBufferFloat(receiver_, order, byteOffset);
               }

               current = current.next;
            } while (current != null);

            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.specialize(receiver_);
         }
      }

      @ExplodeLoop
      @Override
      public void writeBufferFloat(Object receiver_, ByteOrder order, long byteOffset, float value) throws UnsupportedMessageException, InvalidBufferOffsetException {
         while (true) {
            InteropLibraryGen.CachedDispatch current = this;

            do {
               InteropLibrary thisLibrary = current.library;
               if (thisLibrary != null && thisLibrary.accepts(receiver_)) {
                  thisLibrary.writeBufferFloat(receiver_, order, byteOffset, value);
                  return;
               }

               current = current.next;
            } while (current != null);

            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.specialize(receiver_);
         }
      }

      @ExplodeLoop
      @Override
      public double readBufferDouble(Object receiver_, ByteOrder order, long byteOffset) throws UnsupportedMessageException, InvalidBufferOffsetException {
         while (true) {
            InteropLibraryGen.CachedDispatch current = this;

            do {
               InteropLibrary thisLibrary = current.library;
               if (thisLibrary != null && thisLibrary.accepts(receiver_)) {
                  return thisLibrary.readBufferDouble(receiver_, order, byteOffset);
               }

               current = current.next;
            } while (current != null);

            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.specialize(receiver_);
         }
      }

      @ExplodeLoop
      @Override
      public void writeBufferDouble(Object receiver_, ByteOrder order, long byteOffset, double value) throws UnsupportedMessageException, InvalidBufferOffsetException {
         while (true) {
            InteropLibraryGen.CachedDispatch current = this;

            do {
               InteropLibrary thisLibrary = current.library;
               if (thisLibrary != null && thisLibrary.accepts(receiver_)) {
                  thisLibrary.writeBufferDouble(receiver_, order, byteOffset, value);
                  return;
               }

               current = current.next;
            } while (current != null);

            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.specialize(receiver_);
         }
      }

      @ExplodeLoop
      @Override
      public boolean isPointer(Object receiver_) {
         while (true) {
            InteropLibraryGen.CachedDispatch current = this;

            do {
               InteropLibrary thisLibrary = current.library;
               if (thisLibrary != null && thisLibrary.accepts(receiver_)) {
                  return thisLibrary.isPointer(receiver_);
               }

               current = current.next;
            } while (current != null);

            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.specialize(receiver_);
         }
      }

      @ExplodeLoop
      @Override
      public long asPointer(Object receiver_) throws UnsupportedMessageException {
         while (true) {
            InteropLibraryGen.CachedDispatch current = this;

            do {
               InteropLibrary thisLibrary = current.library;
               if (thisLibrary != null && thisLibrary.accepts(receiver_)) {
                  return thisLibrary.asPointer(receiver_);
               }

               current = current.next;
            } while (current != null);

            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.specialize(receiver_);
         }
      }

      @ExplodeLoop
      @Override
      public void toNative(Object receiver_) {
         while (true) {
            InteropLibraryGen.CachedDispatch current = this;

            do {
               InteropLibrary thisLibrary = current.library;
               if (thisLibrary != null && thisLibrary.accepts(receiver_)) {
                  thisLibrary.toNative(receiver_);
                  return;
               }

               current = current.next;
            } while (current != null);

            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.specialize(receiver_);
         }
      }

      @ExplodeLoop
      @Override
      public Instant asInstant(Object receiver_) throws UnsupportedMessageException {
         while (true) {
            InteropLibraryGen.CachedDispatch current = this;

            do {
               InteropLibrary thisLibrary = current.library;
               if (thisLibrary != null && thisLibrary.accepts(receiver_)) {
                  return thisLibrary.asInstant(receiver_);
               }

               current = current.next;
            } while (current != null);

            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.specialize(receiver_);
         }
      }

      @ExplodeLoop
      @Override
      public boolean isTimeZone(Object receiver_) {
         while (true) {
            InteropLibraryGen.CachedDispatch current = this;

            do {
               InteropLibrary thisLibrary = current.library;
               if (thisLibrary != null && thisLibrary.accepts(receiver_)) {
                  return thisLibrary.isTimeZone(receiver_);
               }

               current = current.next;
            } while (current != null);

            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.specialize(receiver_);
         }
      }

      @ExplodeLoop
      @Override
      public ZoneId asTimeZone(Object receiver_) throws UnsupportedMessageException {
         while (true) {
            InteropLibraryGen.CachedDispatch current = this;

            do {
               InteropLibrary thisLibrary = current.library;
               if (thisLibrary != null && thisLibrary.accepts(receiver_)) {
                  return thisLibrary.asTimeZone(receiver_);
               }

               current = current.next;
            } while (current != null);

            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.specialize(receiver_);
         }
      }

      @ExplodeLoop
      @Override
      public boolean isDate(Object receiver_) {
         while (true) {
            InteropLibraryGen.CachedDispatch current = this;

            do {
               InteropLibrary thisLibrary = current.library;
               if (thisLibrary != null && thisLibrary.accepts(receiver_)) {
                  return thisLibrary.isDate(receiver_);
               }

               current = current.next;
            } while (current != null);

            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.specialize(receiver_);
         }
      }

      @ExplodeLoop
      @Override
      public LocalDate asDate(Object receiver_) throws UnsupportedMessageException {
         while (true) {
            InteropLibraryGen.CachedDispatch current = this;

            do {
               InteropLibrary thisLibrary = current.library;
               if (thisLibrary != null && thisLibrary.accepts(receiver_)) {
                  return thisLibrary.asDate(receiver_);
               }

               current = current.next;
            } while (current != null);

            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.specialize(receiver_);
         }
      }

      @ExplodeLoop
      @Override
      public boolean isTime(Object receiver_) {
         while (true) {
            InteropLibraryGen.CachedDispatch current = this;

            do {
               InteropLibrary thisLibrary = current.library;
               if (thisLibrary != null && thisLibrary.accepts(receiver_)) {
                  return thisLibrary.isTime(receiver_);
               }

               current = current.next;
            } while (current != null);

            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.specialize(receiver_);
         }
      }

      @ExplodeLoop
      @Override
      public LocalTime asTime(Object receiver_) throws UnsupportedMessageException {
         while (true) {
            InteropLibraryGen.CachedDispatch current = this;

            do {
               InteropLibrary thisLibrary = current.library;
               if (thisLibrary != null && thisLibrary.accepts(receiver_)) {
                  return thisLibrary.asTime(receiver_);
               }

               current = current.next;
            } while (current != null);

            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.specialize(receiver_);
         }
      }

      @ExplodeLoop
      @Override
      public boolean isDuration(Object receiver_) {
         while (true) {
            InteropLibraryGen.CachedDispatch current = this;

            do {
               InteropLibrary thisLibrary = current.library;
               if (thisLibrary != null && thisLibrary.accepts(receiver_)) {
                  return thisLibrary.isDuration(receiver_);
               }

               current = current.next;
            } while (current != null);

            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.specialize(receiver_);
         }
      }

      @ExplodeLoop
      @Override
      public Duration asDuration(Object receiver_) throws UnsupportedMessageException {
         while (true) {
            InteropLibraryGen.CachedDispatch current = this;

            do {
               InteropLibrary thisLibrary = current.library;
               if (thisLibrary != null && thisLibrary.accepts(receiver_)) {
                  return thisLibrary.asDuration(receiver_);
               }

               current = current.next;
            } while (current != null);

            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.specialize(receiver_);
         }
      }

      @ExplodeLoop
      @Override
      public boolean isException(Object receiver_) {
         while (true) {
            InteropLibraryGen.CachedDispatch current = this;

            do {
               InteropLibrary thisLibrary = current.library;
               if (thisLibrary != null && thisLibrary.accepts(receiver_)) {
                  return thisLibrary.isException(receiver_);
               }

               current = current.next;
            } while (current != null);

            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.specialize(receiver_);
         }
      }

      @ExplodeLoop
      @Override
      public RuntimeException throwException(Object receiver_) throws UnsupportedMessageException {
         while (true) {
            InteropLibraryGen.CachedDispatch current = this;

            do {
               InteropLibrary thisLibrary = current.library;
               if (thisLibrary != null && thisLibrary.accepts(receiver_)) {
                  return thisLibrary.throwException(receiver_);
               }

               current = current.next;
            } while (current != null);

            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.specialize(receiver_);
         }
      }

      @ExplodeLoop
      @Override
      public ExceptionType getExceptionType(Object receiver_) throws UnsupportedMessageException {
         while (true) {
            InteropLibraryGen.CachedDispatch current = this;

            do {
               InteropLibrary thisLibrary = current.library;
               if (thisLibrary != null && thisLibrary.accepts(receiver_)) {
                  return thisLibrary.getExceptionType(receiver_);
               }

               current = current.next;
            } while (current != null);

            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.specialize(receiver_);
         }
      }

      @ExplodeLoop
      @Override
      public boolean isExceptionIncompleteSource(Object receiver_) throws UnsupportedMessageException {
         while (true) {
            InteropLibraryGen.CachedDispatch current = this;

            do {
               InteropLibrary thisLibrary = current.library;
               if (thisLibrary != null && thisLibrary.accepts(receiver_)) {
                  return thisLibrary.isExceptionIncompleteSource(receiver_);
               }

               current = current.next;
            } while (current != null);

            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.specialize(receiver_);
         }
      }

      @ExplodeLoop
      @Override
      public int getExceptionExitStatus(Object receiver_) throws UnsupportedMessageException {
         while (true) {
            InteropLibraryGen.CachedDispatch current = this;

            do {
               InteropLibrary thisLibrary = current.library;
               if (thisLibrary != null && thisLibrary.accepts(receiver_)) {
                  return thisLibrary.getExceptionExitStatus(receiver_);
               }

               current = current.next;
            } while (current != null);

            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.specialize(receiver_);
         }
      }

      @ExplodeLoop
      @Override
      public boolean hasExceptionCause(Object receiver_) {
         while (true) {
            InteropLibraryGen.CachedDispatch current = this;

            do {
               InteropLibrary thisLibrary = current.library;
               if (thisLibrary != null && thisLibrary.accepts(receiver_)) {
                  return thisLibrary.hasExceptionCause(receiver_);
               }

               current = current.next;
            } while (current != null);

            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.specialize(receiver_);
         }
      }

      @ExplodeLoop
      @Override
      public Object getExceptionCause(Object receiver_) throws UnsupportedMessageException {
         while (true) {
            InteropLibraryGen.CachedDispatch current = this;

            do {
               InteropLibrary thisLibrary = current.library;
               if (thisLibrary != null && thisLibrary.accepts(receiver_)) {
                  return thisLibrary.getExceptionCause(receiver_);
               }

               current = current.next;
            } while (current != null);

            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.specialize(receiver_);
         }
      }

      @ExplodeLoop
      @Override
      public boolean hasExceptionMessage(Object receiver_) {
         while (true) {
            InteropLibraryGen.CachedDispatch current = this;

            do {
               InteropLibrary thisLibrary = current.library;
               if (thisLibrary != null && thisLibrary.accepts(receiver_)) {
                  return thisLibrary.hasExceptionMessage(receiver_);
               }

               current = current.next;
            } while (current != null);

            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.specialize(receiver_);
         }
      }

      @ExplodeLoop
      @Override
      public Object getExceptionMessage(Object receiver_) throws UnsupportedMessageException {
         while (true) {
            InteropLibraryGen.CachedDispatch current = this;

            do {
               InteropLibrary thisLibrary = current.library;
               if (thisLibrary != null && thisLibrary.accepts(receiver_)) {
                  return thisLibrary.getExceptionMessage(receiver_);
               }

               current = current.next;
            } while (current != null);

            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.specialize(receiver_);
         }
      }

      @ExplodeLoop
      @Override
      public boolean hasExceptionStackTrace(Object receiver_) {
         while (true) {
            InteropLibraryGen.CachedDispatch current = this;

            do {
               InteropLibrary thisLibrary = current.library;
               if (thisLibrary != null && thisLibrary.accepts(receiver_)) {
                  return thisLibrary.hasExceptionStackTrace(receiver_);
               }

               current = current.next;
            } while (current != null);

            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.specialize(receiver_);
         }
      }

      @ExplodeLoop
      @Override
      public Object getExceptionStackTrace(Object receiver_) throws UnsupportedMessageException {
         while (true) {
            InteropLibraryGen.CachedDispatch current = this;

            do {
               InteropLibrary thisLibrary = current.library;
               if (thisLibrary != null && thisLibrary.accepts(receiver_)) {
                  return thisLibrary.getExceptionStackTrace(receiver_);
               }

               current = current.next;
            } while (current != null);

            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.specialize(receiver_);
         }
      }

      @ExplodeLoop
      @Override
      public boolean hasIterator(Object receiver_) {
         while (true) {
            InteropLibraryGen.CachedDispatch current = this;

            do {
               InteropLibrary thisLibrary = current.library;
               if (thisLibrary != null && thisLibrary.accepts(receiver_)) {
                  return thisLibrary.hasIterator(receiver_);
               }

               current = current.next;
            } while (current != null);

            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.specialize(receiver_);
         }
      }

      @ExplodeLoop
      @Override
      public Object getIterator(Object receiver_) throws UnsupportedMessageException {
         while (true) {
            InteropLibraryGen.CachedDispatch current = this;

            do {
               InteropLibrary thisLibrary = current.library;
               if (thisLibrary != null && thisLibrary.accepts(receiver_)) {
                  return thisLibrary.getIterator(receiver_);
               }

               current = current.next;
            } while (current != null);

            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.specialize(receiver_);
         }
      }

      @ExplodeLoop
      @Override
      public boolean isIterator(Object receiver_) {
         while (true) {
            InteropLibraryGen.CachedDispatch current = this;

            do {
               InteropLibrary thisLibrary = current.library;
               if (thisLibrary != null && thisLibrary.accepts(receiver_)) {
                  return thisLibrary.isIterator(receiver_);
               }

               current = current.next;
            } while (current != null);

            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.specialize(receiver_);
         }
      }

      @ExplodeLoop
      @Override
      public boolean hasIteratorNextElement(Object receiver_) throws UnsupportedMessageException {
         while (true) {
            InteropLibraryGen.CachedDispatch current = this;

            do {
               InteropLibrary thisLibrary = current.library;
               if (thisLibrary != null && thisLibrary.accepts(receiver_)) {
                  return thisLibrary.hasIteratorNextElement(receiver_);
               }

               current = current.next;
            } while (current != null);

            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.specialize(receiver_);
         }
      }

      @ExplodeLoop
      @Override
      public Object getIteratorNextElement(Object receiver_) throws UnsupportedMessageException, StopIterationException {
         while (true) {
            InteropLibraryGen.CachedDispatch current = this;

            do {
               InteropLibrary thisLibrary = current.library;
               if (thisLibrary != null && thisLibrary.accepts(receiver_)) {
                  return thisLibrary.getIteratorNextElement(receiver_);
               }

               current = current.next;
            } while (current != null);

            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.specialize(receiver_);
         }
      }

      @ExplodeLoop
      @Override
      public boolean hasSourceLocation(Object receiver_) {
         while (true) {
            InteropLibraryGen.CachedDispatch current = this;

            do {
               InteropLibrary thisLibrary = current.library;
               if (thisLibrary != null && thisLibrary.accepts(receiver_)) {
                  return thisLibrary.hasSourceLocation(receiver_);
               }

               current = current.next;
            } while (current != null);

            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.specialize(receiver_);
         }
      }

      @ExplodeLoop
      @Override
      public SourceSection getSourceLocation(Object receiver_) throws UnsupportedMessageException {
         while (true) {
            InteropLibraryGen.CachedDispatch current = this;

            do {
               InteropLibrary thisLibrary = current.library;
               if (thisLibrary != null && thisLibrary.accepts(receiver_)) {
                  return thisLibrary.getSourceLocation(receiver_);
               }

               current = current.next;
            } while (current != null);

            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.specialize(receiver_);
         }
      }

      @ExplodeLoop
      @Override
      public boolean hasLanguage(Object receiver_) {
         while (true) {
            InteropLibraryGen.CachedDispatch current = this;

            do {
               InteropLibrary thisLibrary = current.library;
               if (thisLibrary != null && thisLibrary.accepts(receiver_)) {
                  return thisLibrary.hasLanguage(receiver_);
               }

               current = current.next;
            } while (current != null);

            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.specialize(receiver_);
         }
      }

      @ExplodeLoop
      @Override
      public Class<? extends TruffleLanguage<?>> getLanguage(Object receiver_) throws UnsupportedMessageException {
         while (true) {
            InteropLibraryGen.CachedDispatch current = this;

            do {
               InteropLibrary thisLibrary = current.library;
               if (thisLibrary != null && thisLibrary.accepts(receiver_)) {
                  return thisLibrary.getLanguage(receiver_);
               }

               current = current.next;
            } while (current != null);

            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.specialize(receiver_);
         }
      }

      @ExplodeLoop
      @Override
      public boolean hasMetaObject(Object receiver_) {
         while (true) {
            InteropLibraryGen.CachedDispatch current = this;

            do {
               InteropLibrary thisLibrary = current.library;
               if (thisLibrary != null && thisLibrary.accepts(receiver_)) {
                  return thisLibrary.hasMetaObject(receiver_);
               }

               current = current.next;
            } while (current != null);

            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.specialize(receiver_);
         }
      }

      @ExplodeLoop
      @Override
      public Object getMetaObject(Object receiver_) throws UnsupportedMessageException {
         while (true) {
            InteropLibraryGen.CachedDispatch current = this;

            do {
               InteropLibrary thisLibrary = current.library;
               if (thisLibrary != null && thisLibrary.accepts(receiver_)) {
                  return thisLibrary.getMetaObject(receiver_);
               }

               current = current.next;
            } while (current != null);

            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.specialize(receiver_);
         }
      }

      @ExplodeLoop
      @Override
      public Object toDisplayString(Object receiver_, boolean allowSideEffects) {
         while (true) {
            InteropLibraryGen.CachedDispatch current = this;

            do {
               InteropLibrary thisLibrary = current.library;
               if (thisLibrary != null && thisLibrary.accepts(receiver_)) {
                  return thisLibrary.toDisplayString(receiver_, allowSideEffects);
               }

               current = current.next;
            } while (current != null);

            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.specialize(receiver_);
         }
      }

      @ExplodeLoop
      @Override
      public boolean isMetaObject(Object receiver_) {
         while (true) {
            InteropLibraryGen.CachedDispatch current = this;

            do {
               InteropLibrary thisLibrary = current.library;
               if (thisLibrary != null && thisLibrary.accepts(receiver_)) {
                  return thisLibrary.isMetaObject(receiver_);
               }

               current = current.next;
            } while (current != null);

            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.specialize(receiver_);
         }
      }

      @ExplodeLoop
      @Override
      public Object getMetaQualifiedName(Object receiver_) throws UnsupportedMessageException {
         while (true) {
            InteropLibraryGen.CachedDispatch current = this;

            do {
               InteropLibrary thisLibrary = current.library;
               if (thisLibrary != null && thisLibrary.accepts(receiver_)) {
                  return thisLibrary.getMetaQualifiedName(receiver_);
               }

               current = current.next;
            } while (current != null);

            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.specialize(receiver_);
         }
      }

      @ExplodeLoop
      @Override
      public Object getMetaSimpleName(Object receiver_) throws UnsupportedMessageException {
         while (true) {
            InteropLibraryGen.CachedDispatch current = this;

            do {
               InteropLibrary thisLibrary = current.library;
               if (thisLibrary != null && thisLibrary.accepts(receiver_)) {
                  return thisLibrary.getMetaSimpleName(receiver_);
               }

               current = current.next;
            } while (current != null);

            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.specialize(receiver_);
         }
      }

      @ExplodeLoop
      @Override
      public boolean isMetaInstance(Object receiver_, Object instance) throws UnsupportedMessageException {
         while (true) {
            InteropLibraryGen.CachedDispatch current = this;

            do {
               InteropLibrary thisLibrary = current.library;
               if (thisLibrary != null && thisLibrary.accepts(receiver_)) {
                  return thisLibrary.isMetaInstance(receiver_, instance);
               }

               current = current.next;
            } while (current != null);

            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.specialize(receiver_);
         }
      }

      @ExplodeLoop
      @Override
      public boolean hasMetaParents(Object receiver_) {
         while (true) {
            InteropLibraryGen.CachedDispatch current = this;

            do {
               InteropLibrary thisLibrary = current.library;
               if (thisLibrary != null && thisLibrary.accepts(receiver_)) {
                  return thisLibrary.hasMetaParents(receiver_);
               }

               current = current.next;
            } while (current != null);

            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.specialize(receiver_);
         }
      }

      @ExplodeLoop
      @Override
      public Object getMetaParents(Object receiver_) throws UnsupportedMessageException {
         while (true) {
            InteropLibraryGen.CachedDispatch current = this;

            do {
               InteropLibrary thisLibrary = current.library;
               if (thisLibrary != null && thisLibrary.accepts(receiver_)) {
                  return thisLibrary.getMetaParents(receiver_);
               }

               current = current.next;
            } while (current != null);

            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.specialize(receiver_);
         }
      }

      @ExplodeLoop
      @Override
      protected TriState isIdenticalOrUndefined(Object receiver_, Object other) {
         while (true) {
            InteropLibraryGen.CachedDispatch current = this;

            do {
               InteropLibrary thisLibrary = current.library;
               if (thisLibrary != null && thisLibrary.accepts(receiver_)) {
                  return thisLibrary.isIdenticalOrUndefined(receiver_, other);
               }

               current = current.next;
            } while (current != null);

            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.specialize(receiver_);
         }
      }

      @ExplodeLoop
      @Override
      public boolean isIdentical(Object receiver_, Object other, InteropLibrary otherInterop) {
         while (true) {
            InteropLibraryGen.CachedDispatch current = this;

            do {
               InteropLibrary thisLibrary = current.library;
               if (thisLibrary != null && thisLibrary.accepts(receiver_)) {
                  return thisLibrary.isIdentical(receiver_, other, otherInterop);
               }

               current = current.next;
            } while (current != null);

            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.specialize(receiver_);
         }
      }

      @ExplodeLoop
      @Override
      public int identityHashCode(Object receiver_) throws UnsupportedMessageException {
         while (true) {
            InteropLibraryGen.CachedDispatch current = this;

            do {
               InteropLibrary thisLibrary = current.library;
               if (thisLibrary != null && thisLibrary.accepts(receiver_)) {
                  return thisLibrary.identityHashCode(receiver_);
               }

               current = current.next;
            } while (current != null);

            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.specialize(receiver_);
         }
      }

      @ExplodeLoop
      @Override
      public boolean isScope(Object receiver_) {
         while (true) {
            InteropLibraryGen.CachedDispatch current = this;

            do {
               InteropLibrary thisLibrary = current.library;
               if (thisLibrary != null && thisLibrary.accepts(receiver_)) {
                  return thisLibrary.isScope(receiver_);
               }

               current = current.next;
            } while (current != null);

            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.specialize(receiver_);
         }
      }

      @ExplodeLoop
      @Override
      public boolean hasScopeParent(Object receiver_) {
         while (true) {
            InteropLibraryGen.CachedDispatch current = this;

            do {
               InteropLibrary thisLibrary = current.library;
               if (thisLibrary != null && thisLibrary.accepts(receiver_)) {
                  return thisLibrary.hasScopeParent(receiver_);
               }

               current = current.next;
            } while (current != null);

            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.specialize(receiver_);
         }
      }

      @ExplodeLoop
      @Override
      public Object getScopeParent(Object receiver_) throws UnsupportedMessageException {
         while (true) {
            InteropLibraryGen.CachedDispatch current = this;

            do {
               InteropLibrary thisLibrary = current.library;
               if (thisLibrary != null && thisLibrary.accepts(receiver_)) {
                  return thisLibrary.getScopeParent(receiver_);
               }

               current = current.next;
            } while (current != null);

            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.specialize(receiver_);
         }
      }

      @Override
      public boolean accepts(Object receiver_) {
         return true;
      }

      private void specialize(Object receiver_) {
         Lock lock = this.getLock();
         lock.lock();

         try {
            InteropLibraryGen.CachedDispatch current = this;
            InteropLibrary thisLibrary = this.library;
            if (thisLibrary == null) {
               this.library = this.insert(InteropLibraryGen.INSTANCE.create(receiver_));
            } else {
               int count = 0;

               do {
                  InteropLibrary currentLibrary = current.library;
                  if (currentLibrary != null && currentLibrary.accepts(receiver_)) {
                     return;
                  }

                  count++;
                  current = current.next;
               } while (current != null);

               if (count >= this.getLimit()) {
                  this.library = this.insert(new InteropLibraryGen.CachedToUncachedDispatch());
                  this.next = null;
               } else {
                  this.next = this.insert(new InteropLibraryGen.CachedDispatchNext(InteropLibraryGen.INSTANCE.create(receiver_), this.next));
               }
            }
         } finally {
            lock.unlock();
         }
      }
   }

   @GeneratedBy(InteropLibrary.class)
   private static final class CachedDispatchFirst extends InteropLibraryGen.CachedDispatch {
      private final int limit_;

      CachedDispatchFirst(InteropLibrary library, InteropLibraryGen.CachedDispatch next, int limit_) {
         super(library, next);
         this.limit_ = limit_;
      }

      @Override
      int getLimit() {
         return this.limit_;
      }

      @Override
      public NodeCost getCost() {
         if (this.library instanceof InteropLibraryGen.CachedToUncachedDispatch) {
            return NodeCost.MEGAMORPHIC;
         } else {
            InteropLibraryGen.CachedDispatch current = this;
            int count = 0;

            do {
               if (current.library != null) {
                  count++;
               }

               current = current.next;
            } while (current != null);

            return NodeCost.fromCount(count);
         }
      }
   }

   @GeneratedBy(InteropLibrary.class)
   private static final class CachedDispatchNext extends InteropLibraryGen.CachedDispatch {
      CachedDispatchNext(InteropLibrary library, InteropLibraryGen.CachedDispatch next) {
         super(library, next);
      }

      @Override
      int getLimit() {
         throw CompilerDirectives.shouldNotReachHere();
      }

      @Override
      public NodeCost getCost() {
         return NodeCost.NONE;
      }
   }

   @GeneratedBy(InteropLibrary.class)
   private static final class CachedToUncachedDispatch extends InteropLibrary {
      @Override
      public NodeCost getCost() {
         return NodeCost.MEGAMORPHIC;
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public boolean isNull(Object receiver_) {
         assert this.assertAdopted();

         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this.getParent());

         boolean var4;
         try {
            var4 = InteropLibraryGen.INSTANCE.getUncached(receiver_).isNull(receiver_);
         } finally {
            encapsulating_.set(prev_);
         }

         return var4;
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public boolean isBoolean(Object receiver_) {
         assert this.assertAdopted();

         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this.getParent());

         boolean var4;
         try {
            var4 = InteropLibraryGen.INSTANCE.getUncached(receiver_).isBoolean(receiver_);
         } finally {
            encapsulating_.set(prev_);
         }

         return var4;
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public boolean asBoolean(Object receiver_) throws UnsupportedMessageException {
         assert this.assertAdopted();

         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this.getParent());

         boolean var4;
         try {
            var4 = InteropLibraryGen.INSTANCE.getUncached(receiver_).asBoolean(receiver_);
         } finally {
            encapsulating_.set(prev_);
         }

         return var4;
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public boolean isExecutable(Object receiver_) {
         assert this.assertAdopted();

         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this.getParent());

         boolean var4;
         try {
            var4 = InteropLibraryGen.INSTANCE.getUncached(receiver_).isExecutable(receiver_);
         } finally {
            encapsulating_.set(prev_);
         }

         return var4;
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public Object execute(Object receiver_, Object... arguments) throws UnsupportedTypeException, ArityException, UnsupportedMessageException {
         assert this.assertAdopted();

         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this.getParent());

         Object var5;
         try {
            var5 = InteropLibraryGen.INSTANCE.getUncached(receiver_).execute(receiver_, arguments);
         } finally {
            encapsulating_.set(prev_);
         }

         return var5;
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public boolean hasExecutableName(Object receiver_) {
         assert this.assertAdopted();

         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this.getParent());

         boolean var4;
         try {
            var4 = InteropLibraryGen.INSTANCE.getUncached(receiver_).hasExecutableName(receiver_);
         } finally {
            encapsulating_.set(prev_);
         }

         return var4;
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public Object getExecutableName(Object receiver_) throws UnsupportedMessageException {
         assert this.assertAdopted();

         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this.getParent());

         Object var4;
         try {
            var4 = InteropLibraryGen.INSTANCE.getUncached(receiver_).getExecutableName(receiver_);
         } finally {
            encapsulating_.set(prev_);
         }

         return var4;
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public boolean hasDeclaringMetaObject(Object receiver_) {
         assert this.assertAdopted();

         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this.getParent());

         boolean var4;
         try {
            var4 = InteropLibraryGen.INSTANCE.getUncached(receiver_).hasDeclaringMetaObject(receiver_);
         } finally {
            encapsulating_.set(prev_);
         }

         return var4;
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public Object getDeclaringMetaObject(Object receiver_) throws UnsupportedMessageException {
         assert this.assertAdopted();

         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this.getParent());

         Object var4;
         try {
            var4 = InteropLibraryGen.INSTANCE.getUncached(receiver_).getDeclaringMetaObject(receiver_);
         } finally {
            encapsulating_.set(prev_);
         }

         return var4;
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public boolean isInstantiable(Object receiver_) {
         assert this.assertAdopted();

         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this.getParent());

         boolean var4;
         try {
            var4 = InteropLibraryGen.INSTANCE.getUncached(receiver_).isInstantiable(receiver_);
         } finally {
            encapsulating_.set(prev_);
         }

         return var4;
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public Object instantiate(Object receiver_, Object... arguments) throws UnsupportedTypeException, ArityException, UnsupportedMessageException {
         assert this.assertAdopted();

         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this.getParent());

         Object var5;
         try {
            var5 = InteropLibraryGen.INSTANCE.getUncached(receiver_).instantiate(receiver_, arguments);
         } finally {
            encapsulating_.set(prev_);
         }

         return var5;
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public boolean isString(Object receiver_) {
         assert this.assertAdopted();

         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this.getParent());

         boolean var4;
         try {
            var4 = InteropLibraryGen.INSTANCE.getUncached(receiver_).isString(receiver_);
         } finally {
            encapsulating_.set(prev_);
         }

         return var4;
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public String asString(Object receiver_) throws UnsupportedMessageException {
         assert this.assertAdopted();

         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this.getParent());

         String var4;
         try {
            var4 = InteropLibraryGen.INSTANCE.getUncached(receiver_).asString(receiver_);
         } finally {
            encapsulating_.set(prev_);
         }

         return var4;
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public TruffleString asTruffleString(Object receiver_) throws UnsupportedMessageException {
         assert this.assertAdopted();

         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this.getParent());

         TruffleString var4;
         try {
            var4 = InteropLibraryGen.INSTANCE.getUncached(receiver_).asTruffleString(receiver_);
         } finally {
            encapsulating_.set(prev_);
         }

         return var4;
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public boolean isNumber(Object receiver_) {
         assert this.assertAdopted();

         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this.getParent());

         boolean var4;
         try {
            var4 = InteropLibraryGen.INSTANCE.getUncached(receiver_).isNumber(receiver_);
         } finally {
            encapsulating_.set(prev_);
         }

         return var4;
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public boolean fitsInByte(Object receiver_) {
         assert this.assertAdopted();

         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this.getParent());

         boolean var4;
         try {
            var4 = InteropLibraryGen.INSTANCE.getUncached(receiver_).fitsInByte(receiver_);
         } finally {
            encapsulating_.set(prev_);
         }

         return var4;
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public boolean fitsInShort(Object receiver_) {
         assert this.assertAdopted();

         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this.getParent());

         boolean var4;
         try {
            var4 = InteropLibraryGen.INSTANCE.getUncached(receiver_).fitsInShort(receiver_);
         } finally {
            encapsulating_.set(prev_);
         }

         return var4;
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public boolean fitsInInt(Object receiver_) {
         assert this.assertAdopted();

         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this.getParent());

         boolean var4;
         try {
            var4 = InteropLibraryGen.INSTANCE.getUncached(receiver_).fitsInInt(receiver_);
         } finally {
            encapsulating_.set(prev_);
         }

         return var4;
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public boolean fitsInLong(Object receiver_) {
         assert this.assertAdopted();

         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this.getParent());

         boolean var4;
         try {
            var4 = InteropLibraryGen.INSTANCE.getUncached(receiver_).fitsInLong(receiver_);
         } finally {
            encapsulating_.set(prev_);
         }

         return var4;
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public boolean fitsInFloat(Object receiver_) {
         assert this.assertAdopted();

         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this.getParent());

         boolean var4;
         try {
            var4 = InteropLibraryGen.INSTANCE.getUncached(receiver_).fitsInFloat(receiver_);
         } finally {
            encapsulating_.set(prev_);
         }

         return var4;
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public boolean fitsInDouble(Object receiver_) {
         assert this.assertAdopted();

         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this.getParent());

         boolean var4;
         try {
            var4 = InteropLibraryGen.INSTANCE.getUncached(receiver_).fitsInDouble(receiver_);
         } finally {
            encapsulating_.set(prev_);
         }

         return var4;
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public byte asByte(Object receiver_) throws UnsupportedMessageException {
         assert this.assertAdopted();

         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this.getParent());

         byte var4;
         try {
            var4 = InteropLibraryGen.INSTANCE.getUncached(receiver_).asByte(receiver_);
         } finally {
            encapsulating_.set(prev_);
         }

         return var4;
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public short asShort(Object receiver_) throws UnsupportedMessageException {
         assert this.assertAdopted();

         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this.getParent());

         short var4;
         try {
            var4 = InteropLibraryGen.INSTANCE.getUncached(receiver_).asShort(receiver_);
         } finally {
            encapsulating_.set(prev_);
         }

         return var4;
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public int asInt(Object receiver_) throws UnsupportedMessageException {
         assert this.assertAdopted();

         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this.getParent());

         int var4;
         try {
            var4 = InteropLibraryGen.INSTANCE.getUncached(receiver_).asInt(receiver_);
         } finally {
            encapsulating_.set(prev_);
         }

         return var4;
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public long asLong(Object receiver_) throws UnsupportedMessageException {
         assert this.assertAdopted();

         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this.getParent());

         long var4;
         try {
            var4 = InteropLibraryGen.INSTANCE.getUncached(receiver_).asLong(receiver_);
         } finally {
            encapsulating_.set(prev_);
         }

         return var4;
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public float asFloat(Object receiver_) throws UnsupportedMessageException {
         assert this.assertAdopted();

         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this.getParent());

         float var4;
         try {
            var4 = InteropLibraryGen.INSTANCE.getUncached(receiver_).asFloat(receiver_);
         } finally {
            encapsulating_.set(prev_);
         }

         return var4;
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public double asDouble(Object receiver_) throws UnsupportedMessageException {
         assert this.assertAdopted();

         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this.getParent());

         double var4;
         try {
            var4 = InteropLibraryGen.INSTANCE.getUncached(receiver_).asDouble(receiver_);
         } finally {
            encapsulating_.set(prev_);
         }

         return var4;
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public boolean hasMembers(Object receiver_) {
         assert this.assertAdopted();

         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this.getParent());

         boolean var4;
         try {
            var4 = InteropLibraryGen.INSTANCE.getUncached(receiver_).hasMembers(receiver_);
         } finally {
            encapsulating_.set(prev_);
         }

         return var4;
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public Object getMembers(Object receiver_, boolean includeInternal) throws UnsupportedMessageException {
         assert this.assertAdopted();

         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this.getParent());

         Object var5;
         try {
            var5 = InteropLibraryGen.INSTANCE.getUncached(receiver_).getMembers(receiver_, includeInternal);
         } finally {
            encapsulating_.set(prev_);
         }

         return var5;
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public boolean isMemberReadable(Object receiver_, String member) {
         assert this.assertAdopted();

         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this.getParent());

         boolean var5;
         try {
            var5 = InteropLibraryGen.INSTANCE.getUncached(receiver_).isMemberReadable(receiver_, member);
         } finally {
            encapsulating_.set(prev_);
         }

         return var5;
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public Object readMember(Object receiver_, String member) throws UnsupportedMessageException, UnknownIdentifierException {
         assert this.assertAdopted();

         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this.getParent());

         Object var5;
         try {
            var5 = InteropLibraryGen.INSTANCE.getUncached(receiver_).readMember(receiver_, member);
         } finally {
            encapsulating_.set(prev_);
         }

         return var5;
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public boolean isMemberModifiable(Object receiver_, String member) {
         assert this.assertAdopted();

         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this.getParent());

         boolean var5;
         try {
            var5 = InteropLibraryGen.INSTANCE.getUncached(receiver_).isMemberModifiable(receiver_, member);
         } finally {
            encapsulating_.set(prev_);
         }

         return var5;
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public boolean isMemberInsertable(Object receiver_, String member) {
         assert this.assertAdopted();

         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this.getParent());

         boolean var5;
         try {
            var5 = InteropLibraryGen.INSTANCE.getUncached(receiver_).isMemberInsertable(receiver_, member);
         } finally {
            encapsulating_.set(prev_);
         }

         return var5;
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public void writeMember(Object receiver_, String member, Object value) throws UnsupportedMessageException, UnknownIdentifierException, UnsupportedTypeException {
         assert this.assertAdopted();

         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this.getParent());

         try {
            InteropLibraryGen.INSTANCE.getUncached(receiver_).writeMember(receiver_, member, value);
         } finally {
            encapsulating_.set(prev_);
         }
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public boolean isMemberRemovable(Object receiver_, String member) {
         assert this.assertAdopted();

         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this.getParent());

         boolean var5;
         try {
            var5 = InteropLibraryGen.INSTANCE.getUncached(receiver_).isMemberRemovable(receiver_, member);
         } finally {
            encapsulating_.set(prev_);
         }

         return var5;
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public void removeMember(Object receiver_, String member) throws UnsupportedMessageException, UnknownIdentifierException {
         assert this.assertAdopted();

         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this.getParent());

         try {
            InteropLibraryGen.INSTANCE.getUncached(receiver_).removeMember(receiver_, member);
         } finally {
            encapsulating_.set(prev_);
         }
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public boolean isMemberInvocable(Object receiver_, String member) {
         assert this.assertAdopted();

         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this.getParent());

         boolean var5;
         try {
            var5 = InteropLibraryGen.INSTANCE.getUncached(receiver_).isMemberInvocable(receiver_, member);
         } finally {
            encapsulating_.set(prev_);
         }

         return var5;
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public Object invokeMember(Object receiver_, String member, Object... arguments) throws UnsupportedMessageException, ArityException, UnknownIdentifierException, UnsupportedTypeException {
         assert this.assertAdopted();

         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this.getParent());

         Object var6;
         try {
            var6 = InteropLibraryGen.INSTANCE.getUncached(receiver_).invokeMember(receiver_, member, arguments);
         } finally {
            encapsulating_.set(prev_);
         }

         return var6;
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public boolean isMemberInternal(Object receiver_, String member) {
         assert this.assertAdopted();

         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this.getParent());

         boolean var5;
         try {
            var5 = InteropLibraryGen.INSTANCE.getUncached(receiver_).isMemberInternal(receiver_, member);
         } finally {
            encapsulating_.set(prev_);
         }

         return var5;
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public boolean hasMemberReadSideEffects(Object receiver_, String member) {
         assert this.assertAdopted();

         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this.getParent());

         boolean var5;
         try {
            var5 = InteropLibraryGen.INSTANCE.getUncached(receiver_).hasMemberReadSideEffects(receiver_, member);
         } finally {
            encapsulating_.set(prev_);
         }

         return var5;
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public boolean hasMemberWriteSideEffects(Object receiver_, String member) {
         assert this.assertAdopted();

         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this.getParent());

         boolean var5;
         try {
            var5 = InteropLibraryGen.INSTANCE.getUncached(receiver_).hasMemberWriteSideEffects(receiver_, member);
         } finally {
            encapsulating_.set(prev_);
         }

         return var5;
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public boolean hasHashEntries(Object receiver_) {
         assert this.assertAdopted();

         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this.getParent());

         boolean var4;
         try {
            var4 = InteropLibraryGen.INSTANCE.getUncached(receiver_).hasHashEntries(receiver_);
         } finally {
            encapsulating_.set(prev_);
         }

         return var4;
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public long getHashSize(Object receiver_) throws UnsupportedMessageException {
         assert this.assertAdopted();

         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this.getParent());

         long var4;
         try {
            var4 = InteropLibraryGen.INSTANCE.getUncached(receiver_).getHashSize(receiver_);
         } finally {
            encapsulating_.set(prev_);
         }

         return var4;
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public boolean isHashEntryReadable(Object receiver_, Object key) {
         assert this.assertAdopted();

         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this.getParent());

         boolean var5;
         try {
            var5 = InteropLibraryGen.INSTANCE.getUncached(receiver_).isHashEntryReadable(receiver_, key);
         } finally {
            encapsulating_.set(prev_);
         }

         return var5;
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public Object readHashValue(Object receiver_, Object key) throws UnsupportedMessageException, UnknownKeyException {
         assert this.assertAdopted();

         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this.getParent());

         Object var5;
         try {
            var5 = InteropLibraryGen.INSTANCE.getUncached(receiver_).readHashValue(receiver_, key);
         } finally {
            encapsulating_.set(prev_);
         }

         return var5;
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public Object readHashValueOrDefault(Object receiver_, Object key, Object defaultValue) throws UnsupportedMessageException {
         assert this.assertAdopted();

         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this.getParent());

         Object var6;
         try {
            var6 = InteropLibraryGen.INSTANCE.getUncached(receiver_).readHashValueOrDefault(receiver_, key, defaultValue);
         } finally {
            encapsulating_.set(prev_);
         }

         return var6;
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public boolean isHashEntryModifiable(Object receiver_, Object key) {
         assert this.assertAdopted();

         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this.getParent());

         boolean var5;
         try {
            var5 = InteropLibraryGen.INSTANCE.getUncached(receiver_).isHashEntryModifiable(receiver_, key);
         } finally {
            encapsulating_.set(prev_);
         }

         return var5;
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public boolean isHashEntryInsertable(Object receiver_, Object key) {
         assert this.assertAdopted();

         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this.getParent());

         boolean var5;
         try {
            var5 = InteropLibraryGen.INSTANCE.getUncached(receiver_).isHashEntryInsertable(receiver_, key);
         } finally {
            encapsulating_.set(prev_);
         }

         return var5;
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public boolean isHashEntryWritable(Object receiver_, Object key) {
         assert this.assertAdopted();

         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this.getParent());

         boolean var5;
         try {
            var5 = InteropLibraryGen.INSTANCE.getUncached(receiver_).isHashEntryWritable(receiver_, key);
         } finally {
            encapsulating_.set(prev_);
         }

         return var5;
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public void writeHashEntry(Object receiver_, Object key, Object value) throws UnsupportedMessageException, UnknownKeyException, UnsupportedTypeException {
         assert this.assertAdopted();

         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this.getParent());

         try {
            InteropLibraryGen.INSTANCE.getUncached(receiver_).writeHashEntry(receiver_, key, value);
         } finally {
            encapsulating_.set(prev_);
         }
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public boolean isHashEntryRemovable(Object receiver_, Object key) {
         assert this.assertAdopted();

         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this.getParent());

         boolean var5;
         try {
            var5 = InteropLibraryGen.INSTANCE.getUncached(receiver_).isHashEntryRemovable(receiver_, key);
         } finally {
            encapsulating_.set(prev_);
         }

         return var5;
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public void removeHashEntry(Object receiver_, Object key) throws UnsupportedMessageException, UnknownKeyException {
         assert this.assertAdopted();

         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this.getParent());

         try {
            InteropLibraryGen.INSTANCE.getUncached(receiver_).removeHashEntry(receiver_, key);
         } finally {
            encapsulating_.set(prev_);
         }
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public boolean isHashEntryExisting(Object receiver_, Object key) {
         assert this.assertAdopted();

         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this.getParent());

         boolean var5;
         try {
            var5 = InteropLibraryGen.INSTANCE.getUncached(receiver_).isHashEntryExisting(receiver_, key);
         } finally {
            encapsulating_.set(prev_);
         }

         return var5;
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public Object getHashEntriesIterator(Object receiver_) throws UnsupportedMessageException {
         assert this.assertAdopted();

         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this.getParent());

         Object var4;
         try {
            var4 = InteropLibraryGen.INSTANCE.getUncached(receiver_).getHashEntriesIterator(receiver_);
         } finally {
            encapsulating_.set(prev_);
         }

         return var4;
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public Object getHashKeysIterator(Object receiver_) throws UnsupportedMessageException {
         assert this.assertAdopted();

         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this.getParent());

         Object var4;
         try {
            var4 = InteropLibraryGen.INSTANCE.getUncached(receiver_).getHashKeysIterator(receiver_);
         } finally {
            encapsulating_.set(prev_);
         }

         return var4;
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public Object getHashValuesIterator(Object receiver_) throws UnsupportedMessageException {
         assert this.assertAdopted();

         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this.getParent());

         Object var4;
         try {
            var4 = InteropLibraryGen.INSTANCE.getUncached(receiver_).getHashValuesIterator(receiver_);
         } finally {
            encapsulating_.set(prev_);
         }

         return var4;
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public boolean hasArrayElements(Object receiver_) {
         assert this.assertAdopted();

         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this.getParent());

         boolean var4;
         try {
            var4 = InteropLibraryGen.INSTANCE.getUncached(receiver_).hasArrayElements(receiver_);
         } finally {
            encapsulating_.set(prev_);
         }

         return var4;
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public Object readArrayElement(Object receiver_, long index) throws UnsupportedMessageException, InvalidArrayIndexException {
         assert this.assertAdopted();

         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this.getParent());

         Object var6;
         try {
            var6 = InteropLibraryGen.INSTANCE.getUncached(receiver_).readArrayElement(receiver_, index);
         } finally {
            encapsulating_.set(prev_);
         }

         return var6;
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public long getArraySize(Object receiver_) throws UnsupportedMessageException {
         assert this.assertAdopted();

         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this.getParent());

         long var4;
         try {
            var4 = InteropLibraryGen.INSTANCE.getUncached(receiver_).getArraySize(receiver_);
         } finally {
            encapsulating_.set(prev_);
         }

         return var4;
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public boolean isArrayElementReadable(Object receiver_, long index) {
         assert this.assertAdopted();

         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this.getParent());

         boolean var6;
         try {
            var6 = InteropLibraryGen.INSTANCE.getUncached(receiver_).isArrayElementReadable(receiver_, index);
         } finally {
            encapsulating_.set(prev_);
         }

         return var6;
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public void writeArrayElement(Object receiver_, long index, Object value) throws UnsupportedMessageException, UnsupportedTypeException, InvalidArrayIndexException {
         assert this.assertAdopted();

         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this.getParent());

         try {
            InteropLibraryGen.INSTANCE.getUncached(receiver_).writeArrayElement(receiver_, index, value);
         } finally {
            encapsulating_.set(prev_);
         }
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public void removeArrayElement(Object receiver_, long index) throws UnsupportedMessageException, InvalidArrayIndexException {
         assert this.assertAdopted();

         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this.getParent());

         try {
            InteropLibraryGen.INSTANCE.getUncached(receiver_).removeArrayElement(receiver_, index);
         } finally {
            encapsulating_.set(prev_);
         }
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public boolean isArrayElementModifiable(Object receiver_, long index) {
         assert this.assertAdopted();

         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this.getParent());

         boolean var6;
         try {
            var6 = InteropLibraryGen.INSTANCE.getUncached(receiver_).isArrayElementModifiable(receiver_, index);
         } finally {
            encapsulating_.set(prev_);
         }

         return var6;
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public boolean isArrayElementInsertable(Object receiver_, long index) {
         assert this.assertAdopted();

         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this.getParent());

         boolean var6;
         try {
            var6 = InteropLibraryGen.INSTANCE.getUncached(receiver_).isArrayElementInsertable(receiver_, index);
         } finally {
            encapsulating_.set(prev_);
         }

         return var6;
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public boolean isArrayElementRemovable(Object receiver_, long index) {
         assert this.assertAdopted();

         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this.getParent());

         boolean var6;
         try {
            var6 = InteropLibraryGen.INSTANCE.getUncached(receiver_).isArrayElementRemovable(receiver_, index);
         } finally {
            encapsulating_.set(prev_);
         }

         return var6;
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public boolean hasBufferElements(Object receiver_) {
         assert this.assertAdopted();

         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this.getParent());

         boolean var4;
         try {
            var4 = InteropLibraryGen.INSTANCE.getUncached(receiver_).hasBufferElements(receiver_);
         } finally {
            encapsulating_.set(prev_);
         }

         return var4;
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public boolean isBufferWritable(Object receiver_) throws UnsupportedMessageException {
         assert this.assertAdopted();

         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this.getParent());

         boolean var4;
         try {
            var4 = InteropLibraryGen.INSTANCE.getUncached(receiver_).isBufferWritable(receiver_);
         } finally {
            encapsulating_.set(prev_);
         }

         return var4;
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public long getBufferSize(Object receiver_) throws UnsupportedMessageException {
         assert this.assertAdopted();

         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this.getParent());

         long var4;
         try {
            var4 = InteropLibraryGen.INSTANCE.getUncached(receiver_).getBufferSize(receiver_);
         } finally {
            encapsulating_.set(prev_);
         }

         return var4;
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public byte readBufferByte(Object receiver_, long byteOffset) throws UnsupportedMessageException, InvalidBufferOffsetException {
         assert this.assertAdopted();

         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this.getParent());

         byte var6;
         try {
            var6 = InteropLibraryGen.INSTANCE.getUncached(receiver_).readBufferByte(receiver_, byteOffset);
         } finally {
            encapsulating_.set(prev_);
         }

         return var6;
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public void writeBufferByte(Object receiver_, long byteOffset, byte value) throws UnsupportedMessageException, InvalidBufferOffsetException {
         assert this.assertAdopted();

         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this.getParent());

         try {
            InteropLibraryGen.INSTANCE.getUncached(receiver_).writeBufferByte(receiver_, byteOffset, value);
         } finally {
            encapsulating_.set(prev_);
         }
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public short readBufferShort(Object receiver_, ByteOrder order, long byteOffset) throws UnsupportedMessageException, InvalidBufferOffsetException {
         assert this.assertAdopted();

         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this.getParent());

         short var7;
         try {
            var7 = InteropLibraryGen.INSTANCE.getUncached(receiver_).readBufferShort(receiver_, order, byteOffset);
         } finally {
            encapsulating_.set(prev_);
         }

         return var7;
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public void writeBufferShort(Object receiver_, ByteOrder order, long byteOffset, short value) throws UnsupportedMessageException, InvalidBufferOffsetException {
         assert this.assertAdopted();

         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this.getParent());

         try {
            InteropLibraryGen.INSTANCE.getUncached(receiver_).writeBufferShort(receiver_, order, byteOffset, value);
         } finally {
            encapsulating_.set(prev_);
         }
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public int readBufferInt(Object receiver_, ByteOrder order, long byteOffset) throws UnsupportedMessageException, InvalidBufferOffsetException {
         assert this.assertAdopted();

         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this.getParent());

         int var7;
         try {
            var7 = InteropLibraryGen.INSTANCE.getUncached(receiver_).readBufferInt(receiver_, order, byteOffset);
         } finally {
            encapsulating_.set(prev_);
         }

         return var7;
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public void writeBufferInt(Object receiver_, ByteOrder order, long byteOffset, int value) throws UnsupportedMessageException, InvalidBufferOffsetException {
         assert this.assertAdopted();

         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this.getParent());

         try {
            InteropLibraryGen.INSTANCE.getUncached(receiver_).writeBufferInt(receiver_, order, byteOffset, value);
         } finally {
            encapsulating_.set(prev_);
         }
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public long readBufferLong(Object receiver_, ByteOrder order, long byteOffset) throws UnsupportedMessageException, InvalidBufferOffsetException {
         assert this.assertAdopted();

         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this.getParent());

         long var7;
         try {
            var7 = InteropLibraryGen.INSTANCE.getUncached(receiver_).readBufferLong(receiver_, order, byteOffset);
         } finally {
            encapsulating_.set(prev_);
         }

         return var7;
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public void writeBufferLong(Object receiver_, ByteOrder order, long byteOffset, long value) throws UnsupportedMessageException, InvalidBufferOffsetException {
         assert this.assertAdopted();

         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this.getParent());

         try {
            InteropLibraryGen.INSTANCE.getUncached(receiver_).writeBufferLong(receiver_, order, byteOffset, value);
         } finally {
            encapsulating_.set(prev_);
         }
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public float readBufferFloat(Object receiver_, ByteOrder order, long byteOffset) throws UnsupportedMessageException, InvalidBufferOffsetException {
         assert this.assertAdopted();

         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this.getParent());

         float var7;
         try {
            var7 = InteropLibraryGen.INSTANCE.getUncached(receiver_).readBufferFloat(receiver_, order, byteOffset);
         } finally {
            encapsulating_.set(prev_);
         }

         return var7;
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public void writeBufferFloat(Object receiver_, ByteOrder order, long byteOffset, float value) throws UnsupportedMessageException, InvalidBufferOffsetException {
         assert this.assertAdopted();

         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this.getParent());

         try {
            InteropLibraryGen.INSTANCE.getUncached(receiver_).writeBufferFloat(receiver_, order, byteOffset, value);
         } finally {
            encapsulating_.set(prev_);
         }
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public double readBufferDouble(Object receiver_, ByteOrder order, long byteOffset) throws UnsupportedMessageException, InvalidBufferOffsetException {
         assert this.assertAdopted();

         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this.getParent());

         double var7;
         try {
            var7 = InteropLibraryGen.INSTANCE.getUncached(receiver_).readBufferDouble(receiver_, order, byteOffset);
         } finally {
            encapsulating_.set(prev_);
         }

         return var7;
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public void writeBufferDouble(Object receiver_, ByteOrder order, long byteOffset, double value) throws UnsupportedMessageException, InvalidBufferOffsetException {
         assert this.assertAdopted();

         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this.getParent());

         try {
            InteropLibraryGen.INSTANCE.getUncached(receiver_).writeBufferDouble(receiver_, order, byteOffset, value);
         } finally {
            encapsulating_.set(prev_);
         }
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public boolean isPointer(Object receiver_) {
         assert this.assertAdopted();

         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this.getParent());

         boolean var4;
         try {
            var4 = InteropLibraryGen.INSTANCE.getUncached(receiver_).isPointer(receiver_);
         } finally {
            encapsulating_.set(prev_);
         }

         return var4;
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public long asPointer(Object receiver_) throws UnsupportedMessageException {
         assert this.assertAdopted();

         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this.getParent());

         long var4;
         try {
            var4 = InteropLibraryGen.INSTANCE.getUncached(receiver_).asPointer(receiver_);
         } finally {
            encapsulating_.set(prev_);
         }

         return var4;
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public void toNative(Object receiver_) {
         assert this.assertAdopted();

         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this.getParent());

         try {
            InteropLibraryGen.INSTANCE.getUncached(receiver_).toNative(receiver_);
         } finally {
            encapsulating_.set(prev_);
         }
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public Instant asInstant(Object receiver_) throws UnsupportedMessageException {
         assert this.assertAdopted();

         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this.getParent());

         Instant var4;
         try {
            var4 = InteropLibraryGen.INSTANCE.getUncached(receiver_).asInstant(receiver_);
         } finally {
            encapsulating_.set(prev_);
         }

         return var4;
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public boolean isTimeZone(Object receiver_) {
         assert this.assertAdopted();

         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this.getParent());

         boolean var4;
         try {
            var4 = InteropLibraryGen.INSTANCE.getUncached(receiver_).isTimeZone(receiver_);
         } finally {
            encapsulating_.set(prev_);
         }

         return var4;
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public ZoneId asTimeZone(Object receiver_) throws UnsupportedMessageException {
         assert this.assertAdopted();

         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this.getParent());

         ZoneId var4;
         try {
            var4 = InteropLibraryGen.INSTANCE.getUncached(receiver_).asTimeZone(receiver_);
         } finally {
            encapsulating_.set(prev_);
         }

         return var4;
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public boolean isDate(Object receiver_) {
         assert this.assertAdopted();

         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this.getParent());

         boolean var4;
         try {
            var4 = InteropLibraryGen.INSTANCE.getUncached(receiver_).isDate(receiver_);
         } finally {
            encapsulating_.set(prev_);
         }

         return var4;
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public LocalDate asDate(Object receiver_) throws UnsupportedMessageException {
         assert this.assertAdopted();

         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this.getParent());

         LocalDate var4;
         try {
            var4 = InteropLibraryGen.INSTANCE.getUncached(receiver_).asDate(receiver_);
         } finally {
            encapsulating_.set(prev_);
         }

         return var4;
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public boolean isTime(Object receiver_) {
         assert this.assertAdopted();

         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this.getParent());

         boolean var4;
         try {
            var4 = InteropLibraryGen.INSTANCE.getUncached(receiver_).isTime(receiver_);
         } finally {
            encapsulating_.set(prev_);
         }

         return var4;
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public LocalTime asTime(Object receiver_) throws UnsupportedMessageException {
         assert this.assertAdopted();

         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this.getParent());

         LocalTime var4;
         try {
            var4 = InteropLibraryGen.INSTANCE.getUncached(receiver_).asTime(receiver_);
         } finally {
            encapsulating_.set(prev_);
         }

         return var4;
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public boolean isDuration(Object receiver_) {
         assert this.assertAdopted();

         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this.getParent());

         boolean var4;
         try {
            var4 = InteropLibraryGen.INSTANCE.getUncached(receiver_).isDuration(receiver_);
         } finally {
            encapsulating_.set(prev_);
         }

         return var4;
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public Duration asDuration(Object receiver_) throws UnsupportedMessageException {
         assert this.assertAdopted();

         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this.getParent());

         Duration var4;
         try {
            var4 = InteropLibraryGen.INSTANCE.getUncached(receiver_).asDuration(receiver_);
         } finally {
            encapsulating_.set(prev_);
         }

         return var4;
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public boolean isException(Object receiver_) {
         assert this.assertAdopted();

         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this.getParent());

         boolean var4;
         try {
            var4 = InteropLibraryGen.INSTANCE.getUncached(receiver_).isException(receiver_);
         } finally {
            encapsulating_.set(prev_);
         }

         return var4;
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public RuntimeException throwException(Object receiver_) throws UnsupportedMessageException {
         assert this.assertAdopted();

         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this.getParent());

         RuntimeException var4;
         try {
            var4 = InteropLibraryGen.INSTANCE.getUncached(receiver_).throwException(receiver_);
         } finally {
            encapsulating_.set(prev_);
         }

         return var4;
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public ExceptionType getExceptionType(Object receiver_) throws UnsupportedMessageException {
         assert this.assertAdopted();

         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this.getParent());

         ExceptionType var4;
         try {
            var4 = InteropLibraryGen.INSTANCE.getUncached(receiver_).getExceptionType(receiver_);
         } finally {
            encapsulating_.set(prev_);
         }

         return var4;
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public boolean isExceptionIncompleteSource(Object receiver_) throws UnsupportedMessageException {
         assert this.assertAdopted();

         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this.getParent());

         boolean var4;
         try {
            var4 = InteropLibraryGen.INSTANCE.getUncached(receiver_).isExceptionIncompleteSource(receiver_);
         } finally {
            encapsulating_.set(prev_);
         }

         return var4;
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public int getExceptionExitStatus(Object receiver_) throws UnsupportedMessageException {
         assert this.assertAdopted();

         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this.getParent());

         int var4;
         try {
            var4 = InteropLibraryGen.INSTANCE.getUncached(receiver_).getExceptionExitStatus(receiver_);
         } finally {
            encapsulating_.set(prev_);
         }

         return var4;
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public boolean hasExceptionCause(Object receiver_) {
         assert this.assertAdopted();

         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this.getParent());

         boolean var4;
         try {
            var4 = InteropLibraryGen.INSTANCE.getUncached(receiver_).hasExceptionCause(receiver_);
         } finally {
            encapsulating_.set(prev_);
         }

         return var4;
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public Object getExceptionCause(Object receiver_) throws UnsupportedMessageException {
         assert this.assertAdopted();

         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this.getParent());

         Object var4;
         try {
            var4 = InteropLibraryGen.INSTANCE.getUncached(receiver_).getExceptionCause(receiver_);
         } finally {
            encapsulating_.set(prev_);
         }

         return var4;
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public boolean hasExceptionMessage(Object receiver_) {
         assert this.assertAdopted();

         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this.getParent());

         boolean var4;
         try {
            var4 = InteropLibraryGen.INSTANCE.getUncached(receiver_).hasExceptionMessage(receiver_);
         } finally {
            encapsulating_.set(prev_);
         }

         return var4;
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public Object getExceptionMessage(Object receiver_) throws UnsupportedMessageException {
         assert this.assertAdopted();

         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this.getParent());

         Object var4;
         try {
            var4 = InteropLibraryGen.INSTANCE.getUncached(receiver_).getExceptionMessage(receiver_);
         } finally {
            encapsulating_.set(prev_);
         }

         return var4;
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public boolean hasExceptionStackTrace(Object receiver_) {
         assert this.assertAdopted();

         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this.getParent());

         boolean var4;
         try {
            var4 = InteropLibraryGen.INSTANCE.getUncached(receiver_).hasExceptionStackTrace(receiver_);
         } finally {
            encapsulating_.set(prev_);
         }

         return var4;
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public Object getExceptionStackTrace(Object receiver_) throws UnsupportedMessageException {
         assert this.assertAdopted();

         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this.getParent());

         Object var4;
         try {
            var4 = InteropLibraryGen.INSTANCE.getUncached(receiver_).getExceptionStackTrace(receiver_);
         } finally {
            encapsulating_.set(prev_);
         }

         return var4;
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public boolean hasIterator(Object receiver_) {
         assert this.assertAdopted();

         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this.getParent());

         boolean var4;
         try {
            var4 = InteropLibraryGen.INSTANCE.getUncached(receiver_).hasIterator(receiver_);
         } finally {
            encapsulating_.set(prev_);
         }

         return var4;
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public Object getIterator(Object receiver_) throws UnsupportedMessageException {
         assert this.assertAdopted();

         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this.getParent());

         Object var4;
         try {
            var4 = InteropLibraryGen.INSTANCE.getUncached(receiver_).getIterator(receiver_);
         } finally {
            encapsulating_.set(prev_);
         }

         return var4;
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public boolean isIterator(Object receiver_) {
         assert this.assertAdopted();

         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this.getParent());

         boolean var4;
         try {
            var4 = InteropLibraryGen.INSTANCE.getUncached(receiver_).isIterator(receiver_);
         } finally {
            encapsulating_.set(prev_);
         }

         return var4;
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public boolean hasIteratorNextElement(Object receiver_) throws UnsupportedMessageException {
         assert this.assertAdopted();

         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this.getParent());

         boolean var4;
         try {
            var4 = InteropLibraryGen.INSTANCE.getUncached(receiver_).hasIteratorNextElement(receiver_);
         } finally {
            encapsulating_.set(prev_);
         }

         return var4;
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public Object getIteratorNextElement(Object receiver_) throws UnsupportedMessageException, StopIterationException {
         assert this.assertAdopted();

         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this.getParent());

         Object var4;
         try {
            var4 = InteropLibraryGen.INSTANCE.getUncached(receiver_).getIteratorNextElement(receiver_);
         } finally {
            encapsulating_.set(prev_);
         }

         return var4;
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public boolean hasSourceLocation(Object receiver_) {
         assert this.assertAdopted();

         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this.getParent());

         boolean var4;
         try {
            var4 = InteropLibraryGen.INSTANCE.getUncached(receiver_).hasSourceLocation(receiver_);
         } finally {
            encapsulating_.set(prev_);
         }

         return var4;
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public SourceSection getSourceLocation(Object receiver_) throws UnsupportedMessageException {
         assert this.assertAdopted();

         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this.getParent());

         SourceSection var4;
         try {
            var4 = InteropLibraryGen.INSTANCE.getUncached(receiver_).getSourceLocation(receiver_);
         } finally {
            encapsulating_.set(prev_);
         }

         return var4;
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public boolean hasLanguage(Object receiver_) {
         assert this.assertAdopted();

         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this.getParent());

         boolean var4;
         try {
            var4 = InteropLibraryGen.INSTANCE.getUncached(receiver_).hasLanguage(receiver_);
         } finally {
            encapsulating_.set(prev_);
         }

         return var4;
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public Class<? extends TruffleLanguage<?>> getLanguage(Object receiver_) throws UnsupportedMessageException {
         assert this.assertAdopted();

         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this.getParent());

         Class var4;
         try {
            var4 = InteropLibraryGen.INSTANCE.getUncached(receiver_).getLanguage(receiver_);
         } finally {
            encapsulating_.set(prev_);
         }

         return var4;
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public boolean hasMetaObject(Object receiver_) {
         assert this.assertAdopted();

         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this.getParent());

         boolean var4;
         try {
            var4 = InteropLibraryGen.INSTANCE.getUncached(receiver_).hasMetaObject(receiver_);
         } finally {
            encapsulating_.set(prev_);
         }

         return var4;
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public Object getMetaObject(Object receiver_) throws UnsupportedMessageException {
         assert this.assertAdopted();

         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this.getParent());

         Object var4;
         try {
            var4 = InteropLibraryGen.INSTANCE.getUncached(receiver_).getMetaObject(receiver_);
         } finally {
            encapsulating_.set(prev_);
         }

         return var4;
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public Object toDisplayString(Object receiver_, boolean allowSideEffects) {
         assert this.assertAdopted();

         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this.getParent());

         Object var5;
         try {
            var5 = InteropLibraryGen.INSTANCE.getUncached(receiver_).toDisplayString(receiver_, allowSideEffects);
         } finally {
            encapsulating_.set(prev_);
         }

         return var5;
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public boolean isMetaObject(Object receiver_) {
         assert this.assertAdopted();

         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this.getParent());

         boolean var4;
         try {
            var4 = InteropLibraryGen.INSTANCE.getUncached(receiver_).isMetaObject(receiver_);
         } finally {
            encapsulating_.set(prev_);
         }

         return var4;
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public Object getMetaQualifiedName(Object receiver_) throws UnsupportedMessageException {
         assert this.assertAdopted();

         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this.getParent());

         Object var4;
         try {
            var4 = InteropLibraryGen.INSTANCE.getUncached(receiver_).getMetaQualifiedName(receiver_);
         } finally {
            encapsulating_.set(prev_);
         }

         return var4;
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public Object getMetaSimpleName(Object receiver_) throws UnsupportedMessageException {
         assert this.assertAdopted();

         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this.getParent());

         Object var4;
         try {
            var4 = InteropLibraryGen.INSTANCE.getUncached(receiver_).getMetaSimpleName(receiver_);
         } finally {
            encapsulating_.set(prev_);
         }

         return var4;
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public boolean isMetaInstance(Object receiver_, Object instance) throws UnsupportedMessageException {
         assert this.assertAdopted();

         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this.getParent());

         boolean var5;
         try {
            var5 = InteropLibraryGen.INSTANCE.getUncached(receiver_).isMetaInstance(receiver_, instance);
         } finally {
            encapsulating_.set(prev_);
         }

         return var5;
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public boolean hasMetaParents(Object receiver_) {
         assert this.assertAdopted();

         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this.getParent());

         boolean var4;
         try {
            var4 = InteropLibraryGen.INSTANCE.getUncached(receiver_).hasMetaParents(receiver_);
         } finally {
            encapsulating_.set(prev_);
         }

         return var4;
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public Object getMetaParents(Object receiver_) throws UnsupportedMessageException {
         assert this.assertAdopted();

         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this.getParent());

         Object var4;
         try {
            var4 = InteropLibraryGen.INSTANCE.getUncached(receiver_).getMetaParents(receiver_);
         } finally {
            encapsulating_.set(prev_);
         }

         return var4;
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      protected TriState isIdenticalOrUndefined(Object receiver_, Object other) {
         assert this.assertAdopted();

         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this.getParent());

         TriState var5;
         try {
            var5 = InteropLibraryGen.INSTANCE.getUncached(receiver_).isIdenticalOrUndefined(receiver_, other);
         } finally {
            encapsulating_.set(prev_);
         }

         return var5;
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public boolean isIdentical(Object receiver_, Object other, InteropLibrary otherInterop) {
         assert this.assertAdopted();

         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this.getParent());

         boolean var6;
         try {
            var6 = InteropLibraryGen.INSTANCE.getUncached(receiver_).isIdentical(receiver_, other, otherInterop);
         } finally {
            encapsulating_.set(prev_);
         }

         return var6;
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public int identityHashCode(Object receiver_) throws UnsupportedMessageException {
         assert this.assertAdopted();

         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this.getParent());

         int var4;
         try {
            var4 = InteropLibraryGen.INSTANCE.getUncached(receiver_).identityHashCode(receiver_);
         } finally {
            encapsulating_.set(prev_);
         }

         return var4;
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public boolean isScope(Object receiver_) {
         assert this.assertAdopted();

         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this.getParent());

         boolean var4;
         try {
            var4 = InteropLibraryGen.INSTANCE.getUncached(receiver_).isScope(receiver_);
         } finally {
            encapsulating_.set(prev_);
         }

         return var4;
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public boolean hasScopeParent(Object receiver_) {
         assert this.assertAdopted();

         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this.getParent());

         boolean var4;
         try {
            var4 = InteropLibraryGen.INSTANCE.getUncached(receiver_).hasScopeParent(receiver_);
         } finally {
            encapsulating_.set(prev_);
         }

         return var4;
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public Object getScopeParent(Object receiver_) throws UnsupportedMessageException {
         assert this.assertAdopted();

         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this.getParent());

         Object var4;
         try {
            var4 = InteropLibraryGen.INSTANCE.getUncached(receiver_).getScopeParent(receiver_);
         } finally {
            encapsulating_.set(prev_);
         }

         return var4;
      }

      @Override
      public boolean accepts(Object receiver_) {
         return true;
      }
   }

   @GeneratedBy(InteropLibrary.class)
   private static final class Default extends LibraryExport<InteropLibrary> {
      private Default() {
         super(InteropLibrary.class, Object.class, false, false, 0);
      }

      protected InteropLibrary createUncached(Object receiver) {
         InteropLibrary uncached = new InteropLibraryGen.Default.Uncached(receiver);
         return uncached;
      }

      protected InteropLibrary createCached(Object receiver) {
         return new InteropLibraryGen.Default.Cached(receiver);
      }

      @GeneratedBy(InteropLibrary.class)
      private static final class Cached extends InteropLibrary {
         @Node.Child
         private DynamicDispatchLibrary dynamicDispatch_;
         private final Class<?> dynamicDispatchTarget_;

         protected Cached(Object receiver) {
            this.dynamicDispatch_ = this.insert(InteropLibraryGen.DYNAMIC_DISPATCH_LIBRARY_.create(receiver));
            this.dynamicDispatchTarget_ = InteropLibraryGen.DYNAMIC_DISPATCH_LIBRARY_.getUncached(receiver).dispatch(receiver);
         }

         @Override
         public boolean accepts(Object receiver) {
            return this.dynamicDispatch_.accepts(receiver) && this.dynamicDispatch_.dispatch(receiver) == this.dynamicDispatchTarget_;
         }

         @Override
         public boolean isNull(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.isNull(this.dynamicDispatch_.cast(receiver));
         }

         @Override
         public boolean isBoolean(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.isBoolean(this.dynamicDispatch_.cast(receiver));
         }

         @Override
         public boolean asBoolean(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.asBoolean(this.dynamicDispatch_.cast(receiver));
         }

         @Override
         public boolean isExecutable(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.isExecutable(this.dynamicDispatch_.cast(receiver));
         }

         @Override
         public Object execute(Object receiver, Object... arguments) throws UnsupportedTypeException, ArityException, UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.execute(this.dynamicDispatch_.cast(receiver), arguments);
         }

         @Override
         public boolean hasExecutableName(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.hasExecutableName(this.dynamicDispatch_.cast(receiver));
         }

         @Override
         public Object getExecutableName(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.getExecutableName(this.dynamicDispatch_.cast(receiver));
         }

         @Override
         public boolean hasDeclaringMetaObject(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.hasDeclaringMetaObject(this.dynamicDispatch_.cast(receiver));
         }

         @Override
         public Object getDeclaringMetaObject(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.getDeclaringMetaObject(this.dynamicDispatch_.cast(receiver));
         }

         @Override
         public boolean isInstantiable(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.isInstantiable(this.dynamicDispatch_.cast(receiver));
         }

         @Override
         public Object instantiate(Object receiver, Object... arguments) throws UnsupportedTypeException, ArityException, UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.instantiate(this.dynamicDispatch_.cast(receiver), arguments);
         }

         @Override
         public boolean isString(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.isString(this.dynamicDispatch_.cast(receiver));
         }

         @Override
         public String asString(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.asString(this.dynamicDispatch_.cast(receiver));
         }

         @Override
         public TruffleString asTruffleString(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.asTruffleString(this.dynamicDispatch_.cast(receiver));
         }

         @Override
         public boolean isNumber(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.isNumber(this.dynamicDispatch_.cast(receiver));
         }

         @Override
         public boolean fitsInByte(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.fitsInByte(this.dynamicDispatch_.cast(receiver));
         }

         @Override
         public boolean fitsInShort(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.fitsInShort(this.dynamicDispatch_.cast(receiver));
         }

         @Override
         public boolean fitsInInt(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.fitsInInt(this.dynamicDispatch_.cast(receiver));
         }

         @Override
         public boolean fitsInLong(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.fitsInLong(this.dynamicDispatch_.cast(receiver));
         }

         @Override
         public boolean fitsInFloat(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.fitsInFloat(this.dynamicDispatch_.cast(receiver));
         }

         @Override
         public boolean fitsInDouble(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.fitsInDouble(this.dynamicDispatch_.cast(receiver));
         }

         @Override
         public byte asByte(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.asByte(this.dynamicDispatch_.cast(receiver));
         }

         @Override
         public short asShort(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.asShort(this.dynamicDispatch_.cast(receiver));
         }

         @Override
         public int asInt(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.asInt(this.dynamicDispatch_.cast(receiver));
         }

         @Override
         public long asLong(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.asLong(this.dynamicDispatch_.cast(receiver));
         }

         @Override
         public float asFloat(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.asFloat(this.dynamicDispatch_.cast(receiver));
         }

         @Override
         public double asDouble(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.asDouble(this.dynamicDispatch_.cast(receiver));
         }

         @Override
         public boolean hasMembers(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.hasMembers(this.dynamicDispatch_.cast(receiver));
         }

         @Override
         public Object getMembers(Object receiver, boolean includeInternal) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.getMembers(this.dynamicDispatch_.cast(receiver), includeInternal);
         }

         @Override
         public boolean isMemberReadable(Object receiver, String member) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.isMemberReadable(this.dynamicDispatch_.cast(receiver), member);
         }

         @Override
         public Object readMember(Object receiver, String member) throws UnsupportedMessageException, UnknownIdentifierException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.readMember(this.dynamicDispatch_.cast(receiver), member);
         }

         @Override
         public boolean isMemberModifiable(Object receiver, String member) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.isMemberModifiable(this.dynamicDispatch_.cast(receiver), member);
         }

         @Override
         public boolean isMemberInsertable(Object receiver, String member) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.isMemberInsertable(this.dynamicDispatch_.cast(receiver), member);
         }

         @Override
         public void writeMember(Object receiver, String member, Object value) throws UnsupportedMessageException, UnknownIdentifierException, UnsupportedTypeException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            super.writeMember(this.dynamicDispatch_.cast(receiver), member, value);
         }

         @Override
         public boolean isMemberRemovable(Object receiver, String member) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.isMemberRemovable(this.dynamicDispatch_.cast(receiver), member);
         }

         @Override
         public void removeMember(Object receiver, String member) throws UnsupportedMessageException, UnknownIdentifierException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            super.removeMember(this.dynamicDispatch_.cast(receiver), member);
         }

         @Override
         public boolean isMemberInvocable(Object receiver, String member) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.isMemberInvocable(this.dynamicDispatch_.cast(receiver), member);
         }

         @Override
         public Object invokeMember(Object receiver, String member, Object... arguments) throws UnsupportedMessageException, ArityException, UnknownIdentifierException, UnsupportedTypeException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.invokeMember(this.dynamicDispatch_.cast(receiver), member, arguments);
         }

         @Override
         public boolean isMemberInternal(Object receiver, String member) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.isMemberInternal(this.dynamicDispatch_.cast(receiver), member);
         }

         @Override
         public boolean hasMemberReadSideEffects(Object receiver, String member) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.hasMemberReadSideEffects(this.dynamicDispatch_.cast(receiver), member);
         }

         @Override
         public boolean hasMemberWriteSideEffects(Object receiver, String member) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.hasMemberWriteSideEffects(this.dynamicDispatch_.cast(receiver), member);
         }

         @Override
         public boolean hasHashEntries(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.hasHashEntries(this.dynamicDispatch_.cast(receiver));
         }

         @Override
         public long getHashSize(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.getHashSize(this.dynamicDispatch_.cast(receiver));
         }

         @Override
         public boolean isHashEntryReadable(Object receiver, Object key) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.isHashEntryReadable(this.dynamicDispatch_.cast(receiver), key);
         }

         @Override
         public Object readHashValue(Object receiver, Object key) throws UnsupportedMessageException, UnknownKeyException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.readHashValue(this.dynamicDispatch_.cast(receiver), key);
         }

         @Override
         public Object readHashValueOrDefault(Object receiver, Object key, Object defaultValue) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.readHashValueOrDefault(this.dynamicDispatch_.cast(receiver), key, defaultValue);
         }

         @Override
         public boolean isHashEntryModifiable(Object receiver, Object key) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.isHashEntryModifiable(this.dynamicDispatch_.cast(receiver), key);
         }

         @Override
         public boolean isHashEntryInsertable(Object receiver, Object key) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.isHashEntryInsertable(this.dynamicDispatch_.cast(receiver), key);
         }

         @Override
         public boolean isHashEntryWritable(Object receiver, Object key) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.isHashEntryWritable(this.dynamicDispatch_.cast(receiver), key);
         }

         @Override
         public void writeHashEntry(Object receiver, Object key, Object value) throws UnsupportedMessageException, UnknownKeyException, UnsupportedTypeException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            super.writeHashEntry(this.dynamicDispatch_.cast(receiver), key, value);
         }

         @Override
         public boolean isHashEntryRemovable(Object receiver, Object key) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.isHashEntryRemovable(this.dynamicDispatch_.cast(receiver), key);
         }

         @Override
         public void removeHashEntry(Object receiver, Object key) throws UnsupportedMessageException, UnknownKeyException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            super.removeHashEntry(this.dynamicDispatch_.cast(receiver), key);
         }

         @Override
         public boolean isHashEntryExisting(Object receiver, Object key) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.isHashEntryExisting(this.dynamicDispatch_.cast(receiver), key);
         }

         @Override
         public Object getHashEntriesIterator(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.getHashEntriesIterator(this.dynamicDispatch_.cast(receiver));
         }

         @Override
         public Object getHashKeysIterator(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.getHashKeysIterator(this.dynamicDispatch_.cast(receiver));
         }

         @Override
         public Object getHashValuesIterator(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.getHashValuesIterator(this.dynamicDispatch_.cast(receiver));
         }

         @Override
         public boolean hasArrayElements(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.hasArrayElements(this.dynamicDispatch_.cast(receiver));
         }

         @Override
         public Object readArrayElement(Object receiver, long index) throws UnsupportedMessageException, InvalidArrayIndexException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.readArrayElement(this.dynamicDispatch_.cast(receiver), index);
         }

         @Override
         public long getArraySize(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.getArraySize(this.dynamicDispatch_.cast(receiver));
         }

         @Override
         public boolean isArrayElementReadable(Object receiver, long index) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.isArrayElementReadable(this.dynamicDispatch_.cast(receiver), index);
         }

         @Override
         public void writeArrayElement(Object receiver, long index, Object value) throws UnsupportedMessageException, UnsupportedTypeException, InvalidArrayIndexException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            super.writeArrayElement(this.dynamicDispatch_.cast(receiver), index, value);
         }

         @Override
         public void removeArrayElement(Object receiver, long index) throws UnsupportedMessageException, InvalidArrayIndexException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            super.removeArrayElement(this.dynamicDispatch_.cast(receiver), index);
         }

         @Override
         public boolean isArrayElementModifiable(Object receiver, long index) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.isArrayElementModifiable(this.dynamicDispatch_.cast(receiver), index);
         }

         @Override
         public boolean isArrayElementInsertable(Object receiver, long index) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.isArrayElementInsertable(this.dynamicDispatch_.cast(receiver), index);
         }

         @Override
         public boolean isArrayElementRemovable(Object receiver, long index) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.isArrayElementRemovable(this.dynamicDispatch_.cast(receiver), index);
         }

         @Override
         public boolean hasBufferElements(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.hasBufferElements(this.dynamicDispatch_.cast(receiver));
         }

         @Override
         public boolean isBufferWritable(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.isBufferWritable(this.dynamicDispatch_.cast(receiver));
         }

         @Override
         public long getBufferSize(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.getBufferSize(this.dynamicDispatch_.cast(receiver));
         }

         @Override
         public byte readBufferByte(Object receiver, long byteOffset) throws UnsupportedMessageException, InvalidBufferOffsetException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.readBufferByte(this.dynamicDispatch_.cast(receiver), byteOffset);
         }

         @Override
         public void writeBufferByte(Object receiver, long byteOffset, byte value) throws UnsupportedMessageException, InvalidBufferOffsetException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            super.writeBufferByte(this.dynamicDispatch_.cast(receiver), byteOffset, value);
         }

         @Override
         public short readBufferShort(Object receiver, ByteOrder order, long byteOffset) throws UnsupportedMessageException, InvalidBufferOffsetException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.readBufferShort(this.dynamicDispatch_.cast(receiver), order, byteOffset);
         }

         @Override
         public void writeBufferShort(Object receiver, ByteOrder order, long byteOffset, short value) throws UnsupportedMessageException, InvalidBufferOffsetException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            super.writeBufferShort(this.dynamicDispatch_.cast(receiver), order, byteOffset, value);
         }

         @Override
         public int readBufferInt(Object receiver, ByteOrder order, long byteOffset) throws UnsupportedMessageException, InvalidBufferOffsetException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.readBufferInt(this.dynamicDispatch_.cast(receiver), order, byteOffset);
         }

         @Override
         public void writeBufferInt(Object receiver, ByteOrder order, long byteOffset, int value) throws UnsupportedMessageException, InvalidBufferOffsetException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            super.writeBufferInt(this.dynamicDispatch_.cast(receiver), order, byteOffset, value);
         }

         @Override
         public long readBufferLong(Object receiver, ByteOrder order, long byteOffset) throws UnsupportedMessageException, InvalidBufferOffsetException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.readBufferLong(this.dynamicDispatch_.cast(receiver), order, byteOffset);
         }

         @Override
         public void writeBufferLong(Object receiver, ByteOrder order, long byteOffset, long value) throws UnsupportedMessageException, InvalidBufferOffsetException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            super.writeBufferLong(this.dynamicDispatch_.cast(receiver), order, byteOffset, value);
         }

         @Override
         public float readBufferFloat(Object receiver, ByteOrder order, long byteOffset) throws UnsupportedMessageException, InvalidBufferOffsetException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.readBufferFloat(this.dynamicDispatch_.cast(receiver), order, byteOffset);
         }

         @Override
         public void writeBufferFloat(Object receiver, ByteOrder order, long byteOffset, float value) throws UnsupportedMessageException, InvalidBufferOffsetException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            super.writeBufferFloat(this.dynamicDispatch_.cast(receiver), order, byteOffset, value);
         }

         @Override
         public double readBufferDouble(Object receiver, ByteOrder order, long byteOffset) throws UnsupportedMessageException, InvalidBufferOffsetException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.readBufferDouble(this.dynamicDispatch_.cast(receiver), order, byteOffset);
         }

         @Override
         public void writeBufferDouble(Object receiver, ByteOrder order, long byteOffset, double value) throws UnsupportedMessageException, InvalidBufferOffsetException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            super.writeBufferDouble(this.dynamicDispatch_.cast(receiver), order, byteOffset, value);
         }

         @Override
         public boolean isPointer(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.isPointer(this.dynamicDispatch_.cast(receiver));
         }

         @Override
         public long asPointer(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.asPointer(this.dynamicDispatch_.cast(receiver));
         }

         @Override
         public void toNative(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            super.toNative(this.dynamicDispatch_.cast(receiver));
         }

         @Override
         public Instant asInstant(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.asInstant(this.dynamicDispatch_.cast(receiver));
         }

         @Override
         public boolean isTimeZone(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.isTimeZone(this.dynamicDispatch_.cast(receiver));
         }

         @Override
         public ZoneId asTimeZone(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.asTimeZone(this.dynamicDispatch_.cast(receiver));
         }

         @Override
         public boolean isDate(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.isDate(this.dynamicDispatch_.cast(receiver));
         }

         @Override
         public LocalDate asDate(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.asDate(this.dynamicDispatch_.cast(receiver));
         }

         @Override
         public boolean isTime(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.isTime(this.dynamicDispatch_.cast(receiver));
         }

         @Override
         public LocalTime asTime(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.asTime(this.dynamicDispatch_.cast(receiver));
         }

         @Override
         public boolean isDuration(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.isDuration(this.dynamicDispatch_.cast(receiver));
         }

         @Override
         public Duration asDuration(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.asDuration(this.dynamicDispatch_.cast(receiver));
         }

         @Override
         public boolean isException(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.isException(this.dynamicDispatch_.cast(receiver));
         }

         @Override
         public RuntimeException throwException(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.throwException(this.dynamicDispatch_.cast(receiver));
         }

         @Override
         public ExceptionType getExceptionType(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.getExceptionType(this.dynamicDispatch_.cast(receiver));
         }

         @Override
         public boolean isExceptionIncompleteSource(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.isExceptionIncompleteSource(this.dynamicDispatch_.cast(receiver));
         }

         @Override
         public int getExceptionExitStatus(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.getExceptionExitStatus(this.dynamicDispatch_.cast(receiver));
         }

         @Override
         public boolean hasExceptionCause(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.hasExceptionCause(this.dynamicDispatch_.cast(receiver));
         }

         @Override
         public Object getExceptionCause(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.getExceptionCause(this.dynamicDispatch_.cast(receiver));
         }

         @Override
         public boolean hasExceptionMessage(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.hasExceptionMessage(this.dynamicDispatch_.cast(receiver));
         }

         @Override
         public Object getExceptionMessage(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.getExceptionMessage(this.dynamicDispatch_.cast(receiver));
         }

         @Override
         public boolean hasExceptionStackTrace(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.hasExceptionStackTrace(this.dynamicDispatch_.cast(receiver));
         }

         @Override
         public Object getExceptionStackTrace(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.getExceptionStackTrace(this.dynamicDispatch_.cast(receiver));
         }

         @Override
         public boolean hasIterator(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.hasIterator(this.dynamicDispatch_.cast(receiver));
         }

         @Override
         public Object getIterator(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.getIterator(this.dynamicDispatch_.cast(receiver));
         }

         @Override
         public boolean isIterator(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.isIterator(this.dynamicDispatch_.cast(receiver));
         }

         @Override
         public boolean hasIteratorNextElement(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.hasIteratorNextElement(this.dynamicDispatch_.cast(receiver));
         }

         @Override
         public Object getIteratorNextElement(Object receiver) throws UnsupportedMessageException, StopIterationException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.getIteratorNextElement(this.dynamicDispatch_.cast(receiver));
         }

         @Override
         public boolean hasSourceLocation(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.hasSourceLocation(this.dynamicDispatch_.cast(receiver));
         }

         @Override
         public SourceSection getSourceLocation(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.getSourceLocation(this.dynamicDispatch_.cast(receiver));
         }

         @Override
         public boolean hasLanguage(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.hasLanguage(this.dynamicDispatch_.cast(receiver));
         }

         @Override
         public Class<? extends TruffleLanguage<?>> getLanguage(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.getLanguage(this.dynamicDispatch_.cast(receiver));
         }

         @Override
         public boolean hasMetaObject(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.hasMetaObject(this.dynamicDispatch_.cast(receiver));
         }

         @Override
         public Object getMetaObject(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.getMetaObject(this.dynamicDispatch_.cast(receiver));
         }

         @Override
         public Object toDisplayString(Object receiver, boolean allowSideEffects) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.toDisplayString(this.dynamicDispatch_.cast(receiver), allowSideEffects);
         }

         @Override
         public boolean isMetaObject(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.isMetaObject(this.dynamicDispatch_.cast(receiver));
         }

         @Override
         public Object getMetaQualifiedName(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.getMetaQualifiedName(this.dynamicDispatch_.cast(receiver));
         }

         @Override
         public Object getMetaSimpleName(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.getMetaSimpleName(this.dynamicDispatch_.cast(receiver));
         }

         @Override
         public boolean isMetaInstance(Object receiver, Object instance) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.isMetaInstance(this.dynamicDispatch_.cast(receiver), instance);
         }

         @Override
         public boolean hasMetaParents(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.hasMetaParents(this.dynamicDispatch_.cast(receiver));
         }

         @Override
         public Object getMetaParents(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.getMetaParents(this.dynamicDispatch_.cast(receiver));
         }

         @Override
         protected TriState isIdenticalOrUndefined(Object receiver, Object other) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.isIdenticalOrUndefined(this.dynamicDispatch_.cast(receiver), other);
         }

         @Override
         public boolean isIdentical(Object receiver, Object other, InteropLibrary otherInterop) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.isIdentical(this.dynamicDispatch_.cast(receiver), other, otherInterop);
         }

         @Override
         public int identityHashCode(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.identityHashCode(this.dynamicDispatch_.cast(receiver));
         }

         @Override
         public boolean isScope(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.isScope(this.dynamicDispatch_.cast(receiver));
         }

         @Override
         public boolean hasScopeParent(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.hasScopeParent(this.dynamicDispatch_.cast(receiver));
         }

         @Override
         public Object getScopeParent(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.getScopeParent(this.dynamicDispatch_.cast(receiver));
         }
      }

      @GeneratedBy(InteropLibrary.class)
      @DenyReplace
      private static final class Uncached extends InteropLibrary {
         @Node.Child
         private DynamicDispatchLibrary dynamicDispatch_;
         private final Class<?> dynamicDispatchTarget_;

         protected Uncached(Object receiver) {
            this.dynamicDispatch_ = InteropLibraryGen.DYNAMIC_DISPATCH_LIBRARY_.getUncached(receiver);
            this.dynamicDispatchTarget_ = this.dynamicDispatch_.dispatch(receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean accepts(Object receiver) {
            return this.dynamicDispatch_.accepts(receiver) && this.dynamicDispatch_.dispatch(receiver) == this.dynamicDispatchTarget_;
         }

         @Override
         public boolean isAdoptable() {
            return false;
         }

         @Override
         public NodeCost getCost() {
            return NodeCost.MEGAMORPHIC;
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean isNull(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.isNull(receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean isBoolean(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.isBoolean(receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean asBoolean(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.asBoolean(receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean isExecutable(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.isExecutable(receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Object execute(Object receiver, Object... arguments) throws UnsupportedTypeException, ArityException, UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.execute(receiver, arguments);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean hasExecutableName(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.hasExecutableName(receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Object getExecutableName(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.getExecutableName(receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean hasDeclaringMetaObject(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.hasDeclaringMetaObject(receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Object getDeclaringMetaObject(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.getDeclaringMetaObject(receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean isInstantiable(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.isInstantiable(receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Object instantiate(Object receiver, Object... arguments) throws UnsupportedTypeException, ArityException, UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.instantiate(receiver, arguments);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean isString(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.isString(receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public String asString(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.asString(receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public TruffleString asTruffleString(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.asTruffleString(receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean isNumber(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.isNumber(receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean fitsInByte(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.fitsInByte(receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean fitsInShort(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.fitsInShort(receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean fitsInInt(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.fitsInInt(receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean fitsInLong(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.fitsInLong(receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean fitsInFloat(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.fitsInFloat(receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean fitsInDouble(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.fitsInDouble(receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public byte asByte(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.asByte(receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public short asShort(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.asShort(receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public int asInt(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.asInt(receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public long asLong(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.asLong(receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public float asFloat(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.asFloat(receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public double asDouble(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.asDouble(receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean hasMembers(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.hasMembers(receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Object getMembers(Object receiver, boolean includeInternal) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.getMembers(receiver, includeInternal);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean isMemberReadable(Object receiver, String member) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.isMemberReadable(receiver, member);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Object readMember(Object receiver, String member) throws UnsupportedMessageException, UnknownIdentifierException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.readMember(receiver, member);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean isMemberModifiable(Object receiver, String member) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.isMemberModifiable(receiver, member);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean isMemberInsertable(Object receiver, String member) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.isMemberInsertable(receiver, member);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public void writeMember(Object receiver, String member, Object value) throws UnsupportedMessageException, UnknownIdentifierException, UnsupportedTypeException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            super.writeMember(receiver, member, value);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean isMemberRemovable(Object receiver, String member) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.isMemberRemovable(receiver, member);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public void removeMember(Object receiver, String member) throws UnsupportedMessageException, UnknownIdentifierException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            super.removeMember(receiver, member);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean isMemberInvocable(Object receiver, String member) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.isMemberInvocable(receiver, member);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Object invokeMember(Object receiver, String member, Object... arguments) throws UnsupportedMessageException, ArityException, UnknownIdentifierException, UnsupportedTypeException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.invokeMember(receiver, member, arguments);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean isMemberInternal(Object receiver, String member) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.isMemberInternal(receiver, member);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean hasMemberReadSideEffects(Object receiver, String member) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.hasMemberReadSideEffects(receiver, member);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean hasMemberWriteSideEffects(Object receiver, String member) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.hasMemberWriteSideEffects(receiver, member);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean hasHashEntries(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.hasHashEntries(receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public long getHashSize(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.getHashSize(receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean isHashEntryReadable(Object receiver, Object key) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.isHashEntryReadable(receiver, key);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Object readHashValue(Object receiver, Object key) throws UnsupportedMessageException, UnknownKeyException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.readHashValue(receiver, key);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Object readHashValueOrDefault(Object receiver, Object key, Object defaultValue) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.readHashValueOrDefault(receiver, key, defaultValue);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean isHashEntryModifiable(Object receiver, Object key) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.isHashEntryModifiable(receiver, key);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean isHashEntryInsertable(Object receiver, Object key) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.isHashEntryInsertable(receiver, key);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean isHashEntryWritable(Object receiver, Object key) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.isHashEntryWritable(receiver, key);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public void writeHashEntry(Object receiver, Object key, Object value) throws UnsupportedMessageException, UnknownKeyException, UnsupportedTypeException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            super.writeHashEntry(receiver, key, value);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean isHashEntryRemovable(Object receiver, Object key) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.isHashEntryRemovable(receiver, key);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public void removeHashEntry(Object receiver, Object key) throws UnsupportedMessageException, UnknownKeyException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            super.removeHashEntry(receiver, key);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean isHashEntryExisting(Object receiver, Object key) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.isHashEntryExisting(receiver, key);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Object getHashEntriesIterator(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.getHashEntriesIterator(receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Object getHashKeysIterator(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.getHashKeysIterator(receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Object getHashValuesIterator(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.getHashValuesIterator(receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean hasArrayElements(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.hasArrayElements(receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Object readArrayElement(Object receiver, long index) throws UnsupportedMessageException, InvalidArrayIndexException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.readArrayElement(receiver, index);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public long getArraySize(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.getArraySize(receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean isArrayElementReadable(Object receiver, long index) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.isArrayElementReadable(receiver, index);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public void writeArrayElement(Object receiver, long index, Object value) throws UnsupportedMessageException, UnsupportedTypeException, InvalidArrayIndexException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            super.writeArrayElement(receiver, index, value);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public void removeArrayElement(Object receiver, long index) throws UnsupportedMessageException, InvalidArrayIndexException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            super.removeArrayElement(receiver, index);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean isArrayElementModifiable(Object receiver, long index) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.isArrayElementModifiable(receiver, index);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean isArrayElementInsertable(Object receiver, long index) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.isArrayElementInsertable(receiver, index);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean isArrayElementRemovable(Object receiver, long index) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.isArrayElementRemovable(receiver, index);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean hasBufferElements(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.hasBufferElements(receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean isBufferWritable(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.isBufferWritable(receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public long getBufferSize(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.getBufferSize(receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public byte readBufferByte(Object receiver, long byteOffset) throws UnsupportedMessageException, InvalidBufferOffsetException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.readBufferByte(receiver, byteOffset);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public void writeBufferByte(Object receiver, long byteOffset, byte value) throws UnsupportedMessageException, InvalidBufferOffsetException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            super.writeBufferByte(receiver, byteOffset, value);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public short readBufferShort(Object receiver, ByteOrder order, long byteOffset) throws UnsupportedMessageException, InvalidBufferOffsetException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.readBufferShort(receiver, order, byteOffset);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public void writeBufferShort(Object receiver, ByteOrder order, long byteOffset, short value) throws UnsupportedMessageException, InvalidBufferOffsetException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            super.writeBufferShort(receiver, order, byteOffset, value);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public int readBufferInt(Object receiver, ByteOrder order, long byteOffset) throws UnsupportedMessageException, InvalidBufferOffsetException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.readBufferInt(receiver, order, byteOffset);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public void writeBufferInt(Object receiver, ByteOrder order, long byteOffset, int value) throws UnsupportedMessageException, InvalidBufferOffsetException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            super.writeBufferInt(receiver, order, byteOffset, value);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public long readBufferLong(Object receiver, ByteOrder order, long byteOffset) throws UnsupportedMessageException, InvalidBufferOffsetException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.readBufferLong(receiver, order, byteOffset);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public void writeBufferLong(Object receiver, ByteOrder order, long byteOffset, long value) throws UnsupportedMessageException, InvalidBufferOffsetException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            super.writeBufferLong(receiver, order, byteOffset, value);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public float readBufferFloat(Object receiver, ByteOrder order, long byteOffset) throws UnsupportedMessageException, InvalidBufferOffsetException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.readBufferFloat(receiver, order, byteOffset);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public void writeBufferFloat(Object receiver, ByteOrder order, long byteOffset, float value) throws UnsupportedMessageException, InvalidBufferOffsetException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            super.writeBufferFloat(receiver, order, byteOffset, value);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public double readBufferDouble(Object receiver, ByteOrder order, long byteOffset) throws UnsupportedMessageException, InvalidBufferOffsetException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.readBufferDouble(receiver, order, byteOffset);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public void writeBufferDouble(Object receiver, ByteOrder order, long byteOffset, double value) throws UnsupportedMessageException, InvalidBufferOffsetException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            super.writeBufferDouble(receiver, order, byteOffset, value);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean isPointer(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.isPointer(receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public long asPointer(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.asPointer(receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public void toNative(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            super.toNative(receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Instant asInstant(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.asInstant(receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean isTimeZone(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.isTimeZone(receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public ZoneId asTimeZone(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.asTimeZone(receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean isDate(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.isDate(receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public LocalDate asDate(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.asDate(receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean isTime(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.isTime(receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public LocalTime asTime(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.asTime(receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean isDuration(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.isDuration(receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Duration asDuration(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.asDuration(receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean isException(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.isException(receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public RuntimeException throwException(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.throwException(receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public ExceptionType getExceptionType(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.getExceptionType(receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean isExceptionIncompleteSource(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.isExceptionIncompleteSource(receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public int getExceptionExitStatus(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.getExceptionExitStatus(receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean hasExceptionCause(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.hasExceptionCause(receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Object getExceptionCause(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.getExceptionCause(receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean hasExceptionMessage(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.hasExceptionMessage(receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Object getExceptionMessage(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.getExceptionMessage(receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean hasExceptionStackTrace(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.hasExceptionStackTrace(receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Object getExceptionStackTrace(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.getExceptionStackTrace(receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean hasIterator(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.hasIterator(receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Object getIterator(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.getIterator(receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean isIterator(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.isIterator(receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean hasIteratorNextElement(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.hasIteratorNextElement(receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Object getIteratorNextElement(Object receiver) throws UnsupportedMessageException, StopIterationException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.getIteratorNextElement(receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean hasSourceLocation(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.hasSourceLocation(receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public SourceSection getSourceLocation(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.getSourceLocation(receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean hasLanguage(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.hasLanguage(receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Class<? extends TruffleLanguage<?>> getLanguage(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.getLanguage(receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean hasMetaObject(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.hasMetaObject(receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Object getMetaObject(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.getMetaObject(receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Object toDisplayString(Object receiver, boolean allowSideEffects) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.toDisplayString(receiver, allowSideEffects);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean isMetaObject(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.isMetaObject(receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Object getMetaQualifiedName(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.getMetaQualifiedName(receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Object getMetaSimpleName(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.getMetaSimpleName(receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean isMetaInstance(Object receiver, Object instance) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.isMetaInstance(receiver, instance);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean hasMetaParents(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.hasMetaParents(receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Object getMetaParents(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.getMetaParents(receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         protected TriState isIdenticalOrUndefined(Object receiver, Object other) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.isIdenticalOrUndefined(receiver, other);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean isIdentical(Object receiver, Object other, InteropLibrary otherInterop) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.isIdentical(receiver, other, otherInterop);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public int identityHashCode(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.identityHashCode(receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean isScope(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.isScope(receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean hasScopeParent(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.hasScopeParent(receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Object getScopeParent(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return super.getScopeParent(receiver);
         }
      }
   }

   @GeneratedBy(InteropLibrary.class)
   private static final class Delegate extends InteropLibrary {
      @Node.Child
      private InteropLibrary delegateLibrary;

      Delegate(InteropLibrary delegateLibrary) {
         this.delegateLibrary = delegateLibrary;
      }

      @Override
      public boolean isNull(Object receiver_) {
         if (InteropLibraryGen.isDelegated(this.delegateLibrary, 0)) {
            Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
            return InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate).isNull(delegate);
         } else {
            return this.delegateLibrary.isNull(receiver_);
         }
      }

      @Override
      public boolean isBoolean(Object receiver_) {
         if (InteropLibraryGen.isDelegated(this.delegateLibrary, 1)) {
            Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
            return InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate).isBoolean(delegate);
         } else {
            return this.delegateLibrary.isBoolean(receiver_);
         }
      }

      @Override
      public boolean asBoolean(Object receiver_) throws UnsupportedMessageException {
         if (InteropLibraryGen.isDelegated(this.delegateLibrary, 2)) {
            Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
            return InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate).asBoolean(delegate);
         } else {
            return this.delegateLibrary.asBoolean(receiver_);
         }
      }

      @Override
      public boolean isExecutable(Object receiver_) {
         if (InteropLibraryGen.isDelegated(this.delegateLibrary, 3)) {
            Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
            return InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate).isExecutable(delegate);
         } else {
            return this.delegateLibrary.isExecutable(receiver_);
         }
      }

      @Override
      public Object execute(Object receiver_, Object... arguments) throws UnsupportedTypeException, ArityException, UnsupportedMessageException {
         if (InteropLibraryGen.isDelegated(this.delegateLibrary, 4)) {
            Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
            return InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate).execute(delegate, arguments);
         } else {
            return this.delegateLibrary.execute(receiver_, arguments);
         }
      }

      @Override
      public boolean hasExecutableName(Object receiver_) {
         if (InteropLibraryGen.isDelegated(this.delegateLibrary, 5)) {
            Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
            return InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate).hasExecutableName(delegate);
         } else {
            return this.delegateLibrary.hasExecutableName(receiver_);
         }
      }

      @Override
      public Object getExecutableName(Object receiver_) throws UnsupportedMessageException {
         if (InteropLibraryGen.isDelegated(this.delegateLibrary, 6)) {
            Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
            return InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate).getExecutableName(delegate);
         } else {
            return this.delegateLibrary.getExecutableName(receiver_);
         }
      }

      @Override
      public boolean hasDeclaringMetaObject(Object receiver_) {
         if (InteropLibraryGen.isDelegated(this.delegateLibrary, 7)) {
            Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
            return InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate).hasDeclaringMetaObject(delegate);
         } else {
            return this.delegateLibrary.hasDeclaringMetaObject(receiver_);
         }
      }

      @Override
      public Object getDeclaringMetaObject(Object receiver_) throws UnsupportedMessageException {
         if (InteropLibraryGen.isDelegated(this.delegateLibrary, 8)) {
            Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
            return InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate).getDeclaringMetaObject(delegate);
         } else {
            return this.delegateLibrary.getDeclaringMetaObject(receiver_);
         }
      }

      @Override
      public boolean isInstantiable(Object receiver_) {
         if (InteropLibraryGen.isDelegated(this.delegateLibrary, 9)) {
            Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
            return InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate).isInstantiable(delegate);
         } else {
            return this.delegateLibrary.isInstantiable(receiver_);
         }
      }

      @Override
      public Object instantiate(Object receiver_, Object... arguments) throws UnsupportedTypeException, ArityException, UnsupportedMessageException {
         if (InteropLibraryGen.isDelegated(this.delegateLibrary, 10)) {
            Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
            return InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate).instantiate(delegate, arguments);
         } else {
            return this.delegateLibrary.instantiate(receiver_, arguments);
         }
      }

      @Override
      public boolean isString(Object receiver_) {
         if (InteropLibraryGen.isDelegated(this.delegateLibrary, 11)) {
            Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
            return InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate).isString(delegate);
         } else {
            return this.delegateLibrary.isString(receiver_);
         }
      }

      @Override
      public String asString(Object receiver_) throws UnsupportedMessageException {
         if (InteropLibraryGen.isDelegated(this.delegateLibrary, 12)) {
            Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
            return InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate).asString(delegate);
         } else {
            return this.delegateLibrary.asString(receiver_);
         }
      }

      @Override
      public TruffleString asTruffleString(Object receiver_) throws UnsupportedMessageException {
         if (InteropLibraryGen.isDelegated(this.delegateLibrary, 13)) {
            Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
            return InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate).asTruffleString(delegate);
         } else {
            return this.delegateLibrary.asTruffleString(receiver_);
         }
      }

      @Override
      public boolean isNumber(Object receiver_) {
         if (InteropLibraryGen.isDelegated(this.delegateLibrary, 14)) {
            Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
            return InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate).isNumber(delegate);
         } else {
            return this.delegateLibrary.isNumber(receiver_);
         }
      }

      @Override
      public boolean fitsInByte(Object receiver_) {
         if (InteropLibraryGen.isDelegated(this.delegateLibrary, 15)) {
            Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
            return InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate).fitsInByte(delegate);
         } else {
            return this.delegateLibrary.fitsInByte(receiver_);
         }
      }

      @Override
      public boolean fitsInShort(Object receiver_) {
         if (InteropLibraryGen.isDelegated(this.delegateLibrary, 16)) {
            Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
            return InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate).fitsInShort(delegate);
         } else {
            return this.delegateLibrary.fitsInShort(receiver_);
         }
      }

      @Override
      public boolean fitsInInt(Object receiver_) {
         if (InteropLibraryGen.isDelegated(this.delegateLibrary, 17)) {
            Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
            return InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate).fitsInInt(delegate);
         } else {
            return this.delegateLibrary.fitsInInt(receiver_);
         }
      }

      @Override
      public boolean fitsInLong(Object receiver_) {
         if (InteropLibraryGen.isDelegated(this.delegateLibrary, 18)) {
            Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
            return InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate).fitsInLong(delegate);
         } else {
            return this.delegateLibrary.fitsInLong(receiver_);
         }
      }

      @Override
      public boolean fitsInFloat(Object receiver_) {
         if (InteropLibraryGen.isDelegated(this.delegateLibrary, 19)) {
            Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
            return InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate).fitsInFloat(delegate);
         } else {
            return this.delegateLibrary.fitsInFloat(receiver_);
         }
      }

      @Override
      public boolean fitsInDouble(Object receiver_) {
         if (InteropLibraryGen.isDelegated(this.delegateLibrary, 20)) {
            Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
            return InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate).fitsInDouble(delegate);
         } else {
            return this.delegateLibrary.fitsInDouble(receiver_);
         }
      }

      @Override
      public byte asByte(Object receiver_) throws UnsupportedMessageException {
         if (InteropLibraryGen.isDelegated(this.delegateLibrary, 21)) {
            Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
            return InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate).asByte(delegate);
         } else {
            return this.delegateLibrary.asByte(receiver_);
         }
      }

      @Override
      public short asShort(Object receiver_) throws UnsupportedMessageException {
         if (InteropLibraryGen.isDelegated(this.delegateLibrary, 22)) {
            Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
            return InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate).asShort(delegate);
         } else {
            return this.delegateLibrary.asShort(receiver_);
         }
      }

      @Override
      public int asInt(Object receiver_) throws UnsupportedMessageException {
         if (InteropLibraryGen.isDelegated(this.delegateLibrary, 23)) {
            Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
            return InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate).asInt(delegate);
         } else {
            return this.delegateLibrary.asInt(receiver_);
         }
      }

      @Override
      public long asLong(Object receiver_) throws UnsupportedMessageException {
         if (InteropLibraryGen.isDelegated(this.delegateLibrary, 24)) {
            Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
            return InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate).asLong(delegate);
         } else {
            return this.delegateLibrary.asLong(receiver_);
         }
      }

      @Override
      public float asFloat(Object receiver_) throws UnsupportedMessageException {
         if (InteropLibraryGen.isDelegated(this.delegateLibrary, 25)) {
            Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
            return InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate).asFloat(delegate);
         } else {
            return this.delegateLibrary.asFloat(receiver_);
         }
      }

      @Override
      public double asDouble(Object receiver_) throws UnsupportedMessageException {
         if (InteropLibraryGen.isDelegated(this.delegateLibrary, 26)) {
            Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
            return InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate).asDouble(delegate);
         } else {
            return this.delegateLibrary.asDouble(receiver_);
         }
      }

      @Override
      public boolean hasMembers(Object receiver_) {
         if (InteropLibraryGen.isDelegated(this.delegateLibrary, 27)) {
            Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
            return InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate).hasMembers(delegate);
         } else {
            return this.delegateLibrary.hasMembers(receiver_);
         }
      }

      @Override
      public Object getMembers(Object receiver_, boolean includeInternal) throws UnsupportedMessageException {
         if (InteropLibraryGen.isDelegated(this.delegateLibrary, 28)) {
            Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
            return InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate).getMembers(delegate, includeInternal);
         } else {
            return this.delegateLibrary.getMembers(receiver_, includeInternal);
         }
      }

      @Override
      public boolean isMemberReadable(Object receiver_, String member) {
         if (InteropLibraryGen.isDelegated(this.delegateLibrary, 29)) {
            Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
            return InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate).isMemberReadable(delegate, member);
         } else {
            return this.delegateLibrary.isMemberReadable(receiver_, member);
         }
      }

      @Override
      public Object readMember(Object receiver_, String member) throws UnsupportedMessageException, UnknownIdentifierException {
         if (InteropLibraryGen.isDelegated(this.delegateLibrary, 30)) {
            Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
            return InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate).readMember(delegate, member);
         } else {
            return this.delegateLibrary.readMember(receiver_, member);
         }
      }

      @Override
      public boolean isMemberModifiable(Object receiver_, String member) {
         if (InteropLibraryGen.isDelegated(this.delegateLibrary, 31)) {
            Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
            return InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate).isMemberModifiable(delegate, member);
         } else {
            return this.delegateLibrary.isMemberModifiable(receiver_, member);
         }
      }

      @Override
      public boolean isMemberInsertable(Object receiver_, String member) {
         if (InteropLibraryGen.isDelegated(this.delegateLibrary, 32)) {
            Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
            return InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate).isMemberInsertable(delegate, member);
         } else {
            return this.delegateLibrary.isMemberInsertable(receiver_, member);
         }
      }

      @Override
      public void writeMember(Object receiver_, String member, Object value) throws UnsupportedMessageException, UnknownIdentifierException, UnsupportedTypeException {
         if (InteropLibraryGen.isDelegated(this.delegateLibrary, 33)) {
            Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
            InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate).writeMember(delegate, member, value);
         } else {
            this.delegateLibrary.writeMember(receiver_, member, value);
         }
      }

      @Override
      public boolean isMemberRemovable(Object receiver_, String member) {
         if (InteropLibraryGen.isDelegated(this.delegateLibrary, 34)) {
            Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
            return InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate).isMemberRemovable(delegate, member);
         } else {
            return this.delegateLibrary.isMemberRemovable(receiver_, member);
         }
      }

      @Override
      public void removeMember(Object receiver_, String member) throws UnsupportedMessageException, UnknownIdentifierException {
         if (InteropLibraryGen.isDelegated(this.delegateLibrary, 35)) {
            Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
            InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate).removeMember(delegate, member);
         } else {
            this.delegateLibrary.removeMember(receiver_, member);
         }
      }

      @Override
      public boolean isMemberInvocable(Object receiver_, String member) {
         if (InteropLibraryGen.isDelegated(this.delegateLibrary, 36)) {
            Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
            return InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate).isMemberInvocable(delegate, member);
         } else {
            return this.delegateLibrary.isMemberInvocable(receiver_, member);
         }
      }

      @Override
      public Object invokeMember(Object receiver_, String member, Object... arguments) throws UnsupportedMessageException, ArityException, UnknownIdentifierException, UnsupportedTypeException {
         if (InteropLibraryGen.isDelegated(this.delegateLibrary, 37)) {
            Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
            return InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate).invokeMember(delegate, member, arguments);
         } else {
            return this.delegateLibrary.invokeMember(receiver_, member, arguments);
         }
      }

      @Override
      public boolean isMemberInternal(Object receiver_, String member) {
         if (InteropLibraryGen.isDelegated(this.delegateLibrary, 38)) {
            Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
            return InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate).isMemberInternal(delegate, member);
         } else {
            return this.delegateLibrary.isMemberInternal(receiver_, member);
         }
      }

      @Override
      public boolean hasMemberReadSideEffects(Object receiver_, String member) {
         if (InteropLibraryGen.isDelegated(this.delegateLibrary, 39)) {
            Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
            return InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate).hasMemberReadSideEffects(delegate, member);
         } else {
            return this.delegateLibrary.hasMemberReadSideEffects(receiver_, member);
         }
      }

      @Override
      public boolean hasMemberWriteSideEffects(Object receiver_, String member) {
         if (InteropLibraryGen.isDelegated(this.delegateLibrary, 40)) {
            Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
            return InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate).hasMemberWriteSideEffects(delegate, member);
         } else {
            return this.delegateLibrary.hasMemberWriteSideEffects(receiver_, member);
         }
      }

      @Override
      public boolean hasHashEntries(Object receiver_) {
         if (InteropLibraryGen.isDelegated(this.delegateLibrary, 41)) {
            Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
            return InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate).hasHashEntries(delegate);
         } else {
            return this.delegateLibrary.hasHashEntries(receiver_);
         }
      }

      @Override
      public long getHashSize(Object receiver_) throws UnsupportedMessageException {
         if (InteropLibraryGen.isDelegated(this.delegateLibrary, 42)) {
            Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
            return InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate).getHashSize(delegate);
         } else {
            return this.delegateLibrary.getHashSize(receiver_);
         }
      }

      @Override
      public boolean isHashEntryReadable(Object receiver_, Object key) {
         if (InteropLibraryGen.isDelegated(this.delegateLibrary, 43)) {
            Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
            return InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate).isHashEntryReadable(delegate, key);
         } else {
            return this.delegateLibrary.isHashEntryReadable(receiver_, key);
         }
      }

      @Override
      public Object readHashValue(Object receiver_, Object key) throws UnsupportedMessageException, UnknownKeyException {
         if (InteropLibraryGen.isDelegated(this.delegateLibrary, 44)) {
            Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
            return InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate).readHashValue(delegate, key);
         } else {
            return this.delegateLibrary.readHashValue(receiver_, key);
         }
      }

      @Override
      public Object readHashValueOrDefault(Object receiver_, Object key, Object defaultValue) throws UnsupportedMessageException {
         if (InteropLibraryGen.isDelegated(this.delegateLibrary, 45)) {
            Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
            return InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate).readHashValueOrDefault(delegate, key, defaultValue);
         } else {
            return this.delegateLibrary.readHashValueOrDefault(receiver_, key, defaultValue);
         }
      }

      @Override
      public boolean isHashEntryModifiable(Object receiver_, Object key) {
         if (InteropLibraryGen.isDelegated(this.delegateLibrary, 46)) {
            Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
            return InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate).isHashEntryModifiable(delegate, key);
         } else {
            return this.delegateLibrary.isHashEntryModifiable(receiver_, key);
         }
      }

      @Override
      public boolean isHashEntryInsertable(Object receiver_, Object key) {
         if (InteropLibraryGen.isDelegated(this.delegateLibrary, 47)) {
            Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
            return InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate).isHashEntryInsertable(delegate, key);
         } else {
            return this.delegateLibrary.isHashEntryInsertable(receiver_, key);
         }
      }

      @Override
      public boolean isHashEntryWritable(Object receiver_, Object key) {
         if (InteropLibraryGen.isDelegated(this.delegateLibrary, 48)) {
            Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
            return InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate).isHashEntryWritable(delegate, key);
         } else {
            return this.delegateLibrary.isHashEntryWritable(receiver_, key);
         }
      }

      @Override
      public void writeHashEntry(Object receiver_, Object key, Object value) throws UnsupportedMessageException, UnknownKeyException, UnsupportedTypeException {
         if (InteropLibraryGen.isDelegated(this.delegateLibrary, 49)) {
            Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
            InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate).writeHashEntry(delegate, key, value);
         } else {
            this.delegateLibrary.writeHashEntry(receiver_, key, value);
         }
      }

      @Override
      public boolean isHashEntryRemovable(Object receiver_, Object key) {
         if (InteropLibraryGen.isDelegated(this.delegateLibrary, 50)) {
            Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
            return InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate).isHashEntryRemovable(delegate, key);
         } else {
            return this.delegateLibrary.isHashEntryRemovable(receiver_, key);
         }
      }

      @Override
      public void removeHashEntry(Object receiver_, Object key) throws UnsupportedMessageException, UnknownKeyException {
         if (InteropLibraryGen.isDelegated(this.delegateLibrary, 51)) {
            Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
            InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate).removeHashEntry(delegate, key);
         } else {
            this.delegateLibrary.removeHashEntry(receiver_, key);
         }
      }

      @Override
      public boolean isHashEntryExisting(Object receiver_, Object key) {
         if (InteropLibraryGen.isDelegated(this.delegateLibrary, 52)) {
            Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
            return InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate).isHashEntryExisting(delegate, key);
         } else {
            return this.delegateLibrary.isHashEntryExisting(receiver_, key);
         }
      }

      @Override
      public Object getHashEntriesIterator(Object receiver_) throws UnsupportedMessageException {
         if (InteropLibraryGen.isDelegated(this.delegateLibrary, 53)) {
            Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
            return InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate).getHashEntriesIterator(delegate);
         } else {
            return this.delegateLibrary.getHashEntriesIterator(receiver_);
         }
      }

      @Override
      public Object getHashKeysIterator(Object receiver_) throws UnsupportedMessageException {
         if (InteropLibraryGen.isDelegated(this.delegateLibrary, 54)) {
            Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
            return InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate).getHashKeysIterator(delegate);
         } else {
            return this.delegateLibrary.getHashKeysIterator(receiver_);
         }
      }

      @Override
      public Object getHashValuesIterator(Object receiver_) throws UnsupportedMessageException {
         if (InteropLibraryGen.isDelegated(this.delegateLibrary, 55)) {
            Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
            return InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate).getHashValuesIterator(delegate);
         } else {
            return this.delegateLibrary.getHashValuesIterator(receiver_);
         }
      }

      @Override
      public boolean hasArrayElements(Object receiver_) {
         if (InteropLibraryGen.isDelegated(this.delegateLibrary, 56)) {
            Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
            return InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate).hasArrayElements(delegate);
         } else {
            return this.delegateLibrary.hasArrayElements(receiver_);
         }
      }

      @Override
      public Object readArrayElement(Object receiver_, long index) throws UnsupportedMessageException, InvalidArrayIndexException {
         if (InteropLibraryGen.isDelegated(this.delegateLibrary, 57)) {
            Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
            return InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate).readArrayElement(delegate, index);
         } else {
            return this.delegateLibrary.readArrayElement(receiver_, index);
         }
      }

      @Override
      public long getArraySize(Object receiver_) throws UnsupportedMessageException {
         if (InteropLibraryGen.isDelegated(this.delegateLibrary, 58)) {
            Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
            return InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate).getArraySize(delegate);
         } else {
            return this.delegateLibrary.getArraySize(receiver_);
         }
      }

      @Override
      public boolean isArrayElementReadable(Object receiver_, long index) {
         if (InteropLibraryGen.isDelegated(this.delegateLibrary, 59)) {
            Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
            return InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate).isArrayElementReadable(delegate, index);
         } else {
            return this.delegateLibrary.isArrayElementReadable(receiver_, index);
         }
      }

      @Override
      public void writeArrayElement(Object receiver_, long index, Object value) throws UnsupportedMessageException, UnsupportedTypeException, InvalidArrayIndexException {
         if (InteropLibraryGen.isDelegated(this.delegateLibrary, 60)) {
            Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
            InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate).writeArrayElement(delegate, index, value);
         } else {
            this.delegateLibrary.writeArrayElement(receiver_, index, value);
         }
      }

      @Override
      public void removeArrayElement(Object receiver_, long index) throws UnsupportedMessageException, InvalidArrayIndexException {
         if (InteropLibraryGen.isDelegated(this.delegateLibrary, 61)) {
            Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
            InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate).removeArrayElement(delegate, index);
         } else {
            this.delegateLibrary.removeArrayElement(receiver_, index);
         }
      }

      @Override
      public boolean isArrayElementModifiable(Object receiver_, long index) {
         if (InteropLibraryGen.isDelegated(this.delegateLibrary, 62)) {
            Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
            return InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate).isArrayElementModifiable(delegate, index);
         } else {
            return this.delegateLibrary.isArrayElementModifiable(receiver_, index);
         }
      }

      @Override
      public boolean isArrayElementInsertable(Object receiver_, long index) {
         if (InteropLibraryGen.isDelegated(this.delegateLibrary, 63)) {
            Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
            return InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate).isArrayElementInsertable(delegate, index);
         } else {
            return this.delegateLibrary.isArrayElementInsertable(receiver_, index);
         }
      }

      @Override
      public boolean isArrayElementRemovable(Object receiver_, long index) {
         if (InteropLibraryGen.isDelegated(this.delegateLibrary, 64)) {
            Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
            return InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate).isArrayElementRemovable(delegate, index);
         } else {
            return this.delegateLibrary.isArrayElementRemovable(receiver_, index);
         }
      }

      @Override
      public boolean hasBufferElements(Object receiver_) {
         if (InteropLibraryGen.isDelegated(this.delegateLibrary, 65)) {
            Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
            return InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate).hasBufferElements(delegate);
         } else {
            return this.delegateLibrary.hasBufferElements(receiver_);
         }
      }

      @Override
      public boolean isBufferWritable(Object receiver_) throws UnsupportedMessageException {
         if (InteropLibraryGen.isDelegated(this.delegateLibrary, 66)) {
            Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
            return InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate).isBufferWritable(delegate);
         } else {
            return this.delegateLibrary.isBufferWritable(receiver_);
         }
      }

      @Override
      public long getBufferSize(Object receiver_) throws UnsupportedMessageException {
         if (InteropLibraryGen.isDelegated(this.delegateLibrary, 67)) {
            Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
            return InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate).getBufferSize(delegate);
         } else {
            return this.delegateLibrary.getBufferSize(receiver_);
         }
      }

      @Override
      public byte readBufferByte(Object receiver_, long byteOffset) throws UnsupportedMessageException, InvalidBufferOffsetException {
         if (InteropLibraryGen.isDelegated(this.delegateLibrary, 68)) {
            Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
            return InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate).readBufferByte(delegate, byteOffset);
         } else {
            return this.delegateLibrary.readBufferByte(receiver_, byteOffset);
         }
      }

      @Override
      public void writeBufferByte(Object receiver_, long byteOffset, byte value) throws UnsupportedMessageException, InvalidBufferOffsetException {
         if (InteropLibraryGen.isDelegated(this.delegateLibrary, 69)) {
            Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
            InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate).writeBufferByte(delegate, byteOffset, value);
         } else {
            this.delegateLibrary.writeBufferByte(receiver_, byteOffset, value);
         }
      }

      @Override
      public short readBufferShort(Object receiver_, ByteOrder order, long byteOffset) throws UnsupportedMessageException, InvalidBufferOffsetException {
         if (InteropLibraryGen.isDelegated(this.delegateLibrary, 70)) {
            Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
            return InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate).readBufferShort(delegate, order, byteOffset);
         } else {
            return this.delegateLibrary.readBufferShort(receiver_, order, byteOffset);
         }
      }

      @Override
      public void writeBufferShort(Object receiver_, ByteOrder order, long byteOffset, short value) throws UnsupportedMessageException, InvalidBufferOffsetException {
         if (InteropLibraryGen.isDelegated(this.delegateLibrary, 71)) {
            Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
            InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate).writeBufferShort(delegate, order, byteOffset, value);
         } else {
            this.delegateLibrary.writeBufferShort(receiver_, order, byteOffset, value);
         }
      }

      @Override
      public int readBufferInt(Object receiver_, ByteOrder order, long byteOffset) throws UnsupportedMessageException, InvalidBufferOffsetException {
         if (InteropLibraryGen.isDelegated(this.delegateLibrary, 72)) {
            Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
            return InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate).readBufferInt(delegate, order, byteOffset);
         } else {
            return this.delegateLibrary.readBufferInt(receiver_, order, byteOffset);
         }
      }

      @Override
      public void writeBufferInt(Object receiver_, ByteOrder order, long byteOffset, int value) throws UnsupportedMessageException, InvalidBufferOffsetException {
         if (InteropLibraryGen.isDelegated(this.delegateLibrary, 73)) {
            Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
            InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate).writeBufferInt(delegate, order, byteOffset, value);
         } else {
            this.delegateLibrary.writeBufferInt(receiver_, order, byteOffset, value);
         }
      }

      @Override
      public long readBufferLong(Object receiver_, ByteOrder order, long byteOffset) throws UnsupportedMessageException, InvalidBufferOffsetException {
         if (InteropLibraryGen.isDelegated(this.delegateLibrary, 74)) {
            Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
            return InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate).readBufferLong(delegate, order, byteOffset);
         } else {
            return this.delegateLibrary.readBufferLong(receiver_, order, byteOffset);
         }
      }

      @Override
      public void writeBufferLong(Object receiver_, ByteOrder order, long byteOffset, long value) throws UnsupportedMessageException, InvalidBufferOffsetException {
         if (InteropLibraryGen.isDelegated(this.delegateLibrary, 75)) {
            Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
            InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate).writeBufferLong(delegate, order, byteOffset, value);
         } else {
            this.delegateLibrary.writeBufferLong(receiver_, order, byteOffset, value);
         }
      }

      @Override
      public float readBufferFloat(Object receiver_, ByteOrder order, long byteOffset) throws UnsupportedMessageException, InvalidBufferOffsetException {
         if (InteropLibraryGen.isDelegated(this.delegateLibrary, 76)) {
            Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
            return InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate).readBufferFloat(delegate, order, byteOffset);
         } else {
            return this.delegateLibrary.readBufferFloat(receiver_, order, byteOffset);
         }
      }

      @Override
      public void writeBufferFloat(Object receiver_, ByteOrder order, long byteOffset, float value) throws UnsupportedMessageException, InvalidBufferOffsetException {
         if (InteropLibraryGen.isDelegated(this.delegateLibrary, 77)) {
            Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
            InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate).writeBufferFloat(delegate, order, byteOffset, value);
         } else {
            this.delegateLibrary.writeBufferFloat(receiver_, order, byteOffset, value);
         }
      }

      @Override
      public double readBufferDouble(Object receiver_, ByteOrder order, long byteOffset) throws UnsupportedMessageException, InvalidBufferOffsetException {
         if (InteropLibraryGen.isDelegated(this.delegateLibrary, 78)) {
            Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
            return InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate).readBufferDouble(delegate, order, byteOffset);
         } else {
            return this.delegateLibrary.readBufferDouble(receiver_, order, byteOffset);
         }
      }

      @Override
      public void writeBufferDouble(Object receiver_, ByteOrder order, long byteOffset, double value) throws UnsupportedMessageException, InvalidBufferOffsetException {
         if (InteropLibraryGen.isDelegated(this.delegateLibrary, 79)) {
            Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
            InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate).writeBufferDouble(delegate, order, byteOffset, value);
         } else {
            this.delegateLibrary.writeBufferDouble(receiver_, order, byteOffset, value);
         }
      }

      @Override
      public boolean isPointer(Object receiver_) {
         if (InteropLibraryGen.isDelegated(this.delegateLibrary, 80)) {
            Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
            return InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate).isPointer(delegate);
         } else {
            return this.delegateLibrary.isPointer(receiver_);
         }
      }

      @Override
      public long asPointer(Object receiver_) throws UnsupportedMessageException {
         if (InteropLibraryGen.isDelegated(this.delegateLibrary, 81)) {
            Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
            return InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate).asPointer(delegate);
         } else {
            return this.delegateLibrary.asPointer(receiver_);
         }
      }

      @Override
      public void toNative(Object receiver_) {
         if (InteropLibraryGen.isDelegated(this.delegateLibrary, 82)) {
            Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
            InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate).toNative(delegate);
         } else {
            this.delegateLibrary.toNative(receiver_);
         }
      }

      @Override
      public Instant asInstant(Object receiver_) throws UnsupportedMessageException {
         if (InteropLibraryGen.isDelegated(this.delegateLibrary, 83)) {
            Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
            return InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate).asInstant(delegate);
         } else {
            return this.delegateLibrary.asInstant(receiver_);
         }
      }

      @Override
      public boolean isTimeZone(Object receiver_) {
         if (InteropLibraryGen.isDelegated(this.delegateLibrary, 84)) {
            Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
            return InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate).isTimeZone(delegate);
         } else {
            return this.delegateLibrary.isTimeZone(receiver_);
         }
      }

      @Override
      public ZoneId asTimeZone(Object receiver_) throws UnsupportedMessageException {
         if (InteropLibraryGen.isDelegated(this.delegateLibrary, 85)) {
            Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
            return InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate).asTimeZone(delegate);
         } else {
            return this.delegateLibrary.asTimeZone(receiver_);
         }
      }

      @Override
      public boolean isDate(Object receiver_) {
         if (InteropLibraryGen.isDelegated(this.delegateLibrary, 86)) {
            Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
            return InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate).isDate(delegate);
         } else {
            return this.delegateLibrary.isDate(receiver_);
         }
      }

      @Override
      public LocalDate asDate(Object receiver_) throws UnsupportedMessageException {
         if (InteropLibraryGen.isDelegated(this.delegateLibrary, 87)) {
            Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
            return InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate).asDate(delegate);
         } else {
            return this.delegateLibrary.asDate(receiver_);
         }
      }

      @Override
      public boolean isTime(Object receiver_) {
         if (InteropLibraryGen.isDelegated(this.delegateLibrary, 88)) {
            Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
            return InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate).isTime(delegate);
         } else {
            return this.delegateLibrary.isTime(receiver_);
         }
      }

      @Override
      public LocalTime asTime(Object receiver_) throws UnsupportedMessageException {
         if (InteropLibraryGen.isDelegated(this.delegateLibrary, 89)) {
            Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
            return InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate).asTime(delegate);
         } else {
            return this.delegateLibrary.asTime(receiver_);
         }
      }

      @Override
      public boolean isDuration(Object receiver_) {
         if (InteropLibraryGen.isDelegated(this.delegateLibrary, 90)) {
            Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
            return InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate).isDuration(delegate);
         } else {
            return this.delegateLibrary.isDuration(receiver_);
         }
      }

      @Override
      public Duration asDuration(Object receiver_) throws UnsupportedMessageException {
         if (InteropLibraryGen.isDelegated(this.delegateLibrary, 91)) {
            Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
            return InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate).asDuration(delegate);
         } else {
            return this.delegateLibrary.asDuration(receiver_);
         }
      }

      @Override
      public boolean isException(Object receiver_) {
         if (InteropLibraryGen.isDelegated(this.delegateLibrary, 92)) {
            Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
            return InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate).isException(delegate);
         } else {
            return this.delegateLibrary.isException(receiver_);
         }
      }

      @Override
      public RuntimeException throwException(Object receiver_) throws UnsupportedMessageException {
         if (InteropLibraryGen.isDelegated(this.delegateLibrary, 93)) {
            Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
            return InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate).throwException(delegate);
         } else {
            return this.delegateLibrary.throwException(receiver_);
         }
      }

      @Override
      public ExceptionType getExceptionType(Object receiver_) throws UnsupportedMessageException {
         if (InteropLibraryGen.isDelegated(this.delegateLibrary, 94)) {
            Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
            return InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate).getExceptionType(delegate);
         } else {
            return this.delegateLibrary.getExceptionType(receiver_);
         }
      }

      @Override
      public boolean isExceptionIncompleteSource(Object receiver_) throws UnsupportedMessageException {
         if (InteropLibraryGen.isDelegated(this.delegateLibrary, 95)) {
            Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
            return InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate).isExceptionIncompleteSource(delegate);
         } else {
            return this.delegateLibrary.isExceptionIncompleteSource(receiver_);
         }
      }

      @Override
      public int getExceptionExitStatus(Object receiver_) throws UnsupportedMessageException {
         if (InteropLibraryGen.isDelegated(this.delegateLibrary, 96)) {
            Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
            return InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate).getExceptionExitStatus(delegate);
         } else {
            return this.delegateLibrary.getExceptionExitStatus(receiver_);
         }
      }

      @Override
      public boolean hasExceptionCause(Object receiver_) {
         if (InteropLibraryGen.isDelegated(this.delegateLibrary, 97)) {
            Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
            return InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate).hasExceptionCause(delegate);
         } else {
            return this.delegateLibrary.hasExceptionCause(receiver_);
         }
      }

      @Override
      public Object getExceptionCause(Object receiver_) throws UnsupportedMessageException {
         if (InteropLibraryGen.isDelegated(this.delegateLibrary, 98)) {
            Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
            return InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate).getExceptionCause(delegate);
         } else {
            return this.delegateLibrary.getExceptionCause(receiver_);
         }
      }

      @Override
      public boolean hasExceptionMessage(Object receiver_) {
         if (InteropLibraryGen.isDelegated(this.delegateLibrary, 99)) {
            Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
            return InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate).hasExceptionMessage(delegate);
         } else {
            return this.delegateLibrary.hasExceptionMessage(receiver_);
         }
      }

      @Override
      public Object getExceptionMessage(Object receiver_) throws UnsupportedMessageException {
         if (InteropLibraryGen.isDelegated(this.delegateLibrary, 100)) {
            Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
            return InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate).getExceptionMessage(delegate);
         } else {
            return this.delegateLibrary.getExceptionMessage(receiver_);
         }
      }

      @Override
      public boolean hasExceptionStackTrace(Object receiver_) {
         if (InteropLibraryGen.isDelegated(this.delegateLibrary, 101)) {
            Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
            return InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate).hasExceptionStackTrace(delegate);
         } else {
            return this.delegateLibrary.hasExceptionStackTrace(receiver_);
         }
      }

      @Override
      public Object getExceptionStackTrace(Object receiver_) throws UnsupportedMessageException {
         if (InteropLibraryGen.isDelegated(this.delegateLibrary, 102)) {
            Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
            return InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate).getExceptionStackTrace(delegate);
         } else {
            return this.delegateLibrary.getExceptionStackTrace(receiver_);
         }
      }

      @Override
      public boolean hasIterator(Object receiver_) {
         if (InteropLibraryGen.isDelegated(this.delegateLibrary, 103)) {
            Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
            return InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate).hasIterator(delegate);
         } else {
            return this.delegateLibrary.hasIterator(receiver_);
         }
      }

      @Override
      public Object getIterator(Object receiver_) throws UnsupportedMessageException {
         if (InteropLibraryGen.isDelegated(this.delegateLibrary, 104)) {
            Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
            return InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate).getIterator(delegate);
         } else {
            return this.delegateLibrary.getIterator(receiver_);
         }
      }

      @Override
      public boolean isIterator(Object receiver_) {
         if (InteropLibraryGen.isDelegated(this.delegateLibrary, 105)) {
            Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
            return InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate).isIterator(delegate);
         } else {
            return this.delegateLibrary.isIterator(receiver_);
         }
      }

      @Override
      public boolean hasIteratorNextElement(Object receiver_) throws UnsupportedMessageException {
         if (InteropLibraryGen.isDelegated(this.delegateLibrary, 106)) {
            Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
            return InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate).hasIteratorNextElement(delegate);
         } else {
            return this.delegateLibrary.hasIteratorNextElement(receiver_);
         }
      }

      @Override
      public Object getIteratorNextElement(Object receiver_) throws UnsupportedMessageException, StopIterationException {
         if (InteropLibraryGen.isDelegated(this.delegateLibrary, 107)) {
            Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
            return InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate).getIteratorNextElement(delegate);
         } else {
            return this.delegateLibrary.getIteratorNextElement(receiver_);
         }
      }

      @Override
      public boolean hasSourceLocation(Object receiver_) {
         if (InteropLibraryGen.isDelegated(this.delegateLibrary, 108)) {
            Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
            return InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate).hasSourceLocation(delegate);
         } else {
            return this.delegateLibrary.hasSourceLocation(receiver_);
         }
      }

      @Override
      public SourceSection getSourceLocation(Object receiver_) throws UnsupportedMessageException {
         if (InteropLibraryGen.isDelegated(this.delegateLibrary, 109)) {
            Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
            return InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate).getSourceLocation(delegate);
         } else {
            return this.delegateLibrary.getSourceLocation(receiver_);
         }
      }

      @Override
      public boolean hasLanguage(Object receiver_) {
         if (InteropLibraryGen.isDelegated(this.delegateLibrary, 110)) {
            Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
            return InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate).hasLanguage(delegate);
         } else {
            return this.delegateLibrary.hasLanguage(receiver_);
         }
      }

      @Override
      public Class<? extends TruffleLanguage<?>> getLanguage(Object receiver_) throws UnsupportedMessageException {
         if (InteropLibraryGen.isDelegated(this.delegateLibrary, 111)) {
            Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
            return InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate).getLanguage(delegate);
         } else {
            return this.delegateLibrary.getLanguage(receiver_);
         }
      }

      @Override
      public boolean hasMetaObject(Object receiver_) {
         if (InteropLibraryGen.isDelegated(this.delegateLibrary, 112)) {
            Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
            return InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate).hasMetaObject(delegate);
         } else {
            return this.delegateLibrary.hasMetaObject(receiver_);
         }
      }

      @Override
      public Object getMetaObject(Object receiver_) throws UnsupportedMessageException {
         if (InteropLibraryGen.isDelegated(this.delegateLibrary, 113)) {
            Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
            return InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate).getMetaObject(delegate);
         } else {
            return this.delegateLibrary.getMetaObject(receiver_);
         }
      }

      @Override
      public Object toDisplayString(Object receiver_, boolean allowSideEffects) {
         if (InteropLibraryGen.isDelegated(this.delegateLibrary, 114)) {
            Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
            return InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate).toDisplayString(delegate, allowSideEffects);
         } else {
            return this.delegateLibrary.toDisplayString(receiver_, allowSideEffects);
         }
      }

      @Override
      public boolean isMetaObject(Object receiver_) {
         if (InteropLibraryGen.isDelegated(this.delegateLibrary, 115)) {
            Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
            return InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate).isMetaObject(delegate);
         } else {
            return this.delegateLibrary.isMetaObject(receiver_);
         }
      }

      @Override
      public Object getMetaQualifiedName(Object receiver_) throws UnsupportedMessageException {
         if (InteropLibraryGen.isDelegated(this.delegateLibrary, 116)) {
            Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
            return InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate).getMetaQualifiedName(delegate);
         } else {
            return this.delegateLibrary.getMetaQualifiedName(receiver_);
         }
      }

      @Override
      public Object getMetaSimpleName(Object receiver_) throws UnsupportedMessageException {
         if (InteropLibraryGen.isDelegated(this.delegateLibrary, 117)) {
            Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
            return InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate).getMetaSimpleName(delegate);
         } else {
            return this.delegateLibrary.getMetaSimpleName(receiver_);
         }
      }

      @Override
      public boolean isMetaInstance(Object receiver_, Object instance) throws UnsupportedMessageException {
         if (InteropLibraryGen.isDelegated(this.delegateLibrary, 118)) {
            Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
            return InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate).isMetaInstance(delegate, instance);
         } else {
            return this.delegateLibrary.isMetaInstance(receiver_, instance);
         }
      }

      @Override
      public boolean hasMetaParents(Object receiver_) {
         if (InteropLibraryGen.isDelegated(this.delegateLibrary, 119)) {
            Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
            return InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate).hasMetaParents(delegate);
         } else {
            return this.delegateLibrary.hasMetaParents(receiver_);
         }
      }

      @Override
      public Object getMetaParents(Object receiver_) throws UnsupportedMessageException {
         if (InteropLibraryGen.isDelegated(this.delegateLibrary, 120)) {
            Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
            return InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate).getMetaParents(delegate);
         } else {
            return this.delegateLibrary.getMetaParents(receiver_);
         }
      }

      @Override
      protected TriState isIdenticalOrUndefined(Object receiver_, Object other) {
         if (InteropLibraryGen.isDelegated(this.delegateLibrary, 121)) {
            Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
            return InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate).isIdenticalOrUndefined(delegate, other);
         } else {
            return this.delegateLibrary.isIdenticalOrUndefined(receiver_, other);
         }
      }

      @Override
      public boolean isIdentical(Object receiver_, Object other, InteropLibrary otherInterop) {
         if (InteropLibraryGen.isDelegated(this.delegateLibrary, 122)) {
            Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
            return InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate).isIdentical(delegate, other, otherInterop);
         } else {
            return this.delegateLibrary.isIdentical(receiver_, other, otherInterop);
         }
      }

      @Override
      public int identityHashCode(Object receiver_) throws UnsupportedMessageException {
         if (InteropLibraryGen.isDelegated(this.delegateLibrary, 123)) {
            Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
            return InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate).identityHashCode(delegate);
         } else {
            return this.delegateLibrary.identityHashCode(receiver_);
         }
      }

      @Override
      public boolean isScope(Object receiver_) {
         if (InteropLibraryGen.isDelegated(this.delegateLibrary, 124)) {
            Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
            return InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate).isScope(delegate);
         } else {
            return this.delegateLibrary.isScope(receiver_);
         }
      }

      @Override
      public boolean hasScopeParent(Object receiver_) {
         if (InteropLibraryGen.isDelegated(this.delegateLibrary, 125)) {
            Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
            return InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate).hasScopeParent(delegate);
         } else {
            return this.delegateLibrary.hasScopeParent(receiver_);
         }
      }

      @Override
      public Object getScopeParent(Object receiver_) throws UnsupportedMessageException {
         if (InteropLibraryGen.isDelegated(this.delegateLibrary, 126)) {
            Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
            return InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate).getScopeParent(delegate);
         } else {
            return this.delegateLibrary.getScopeParent(receiver_);
         }
      }

      @Override
      public boolean accepts(Object receiver_) {
         return this.delegateLibrary.accepts(receiver_);
      }

      @Override
      public NodeCost getCost() {
         return NodeCost.NONE;
      }

      @Override
      public boolean isAdoptable() {
         return this.delegateLibrary.isAdoptable();
      }
   }

   @GeneratedBy(InteropLibrary.class)
   private static class MessageImpl extends Message {
      MessageImpl(String name, int index, Class<?> returnType, Class<?>... parameters) {
         super(InteropLibraryGen.LIBRARY_CLASS, name, index, returnType, parameters);
      }
   }

   @GeneratedBy(InteropLibrary.class)
   private static final class Proxy extends InteropLibrary {
      @Node.Child
      private ReflectionLibrary lib;

      Proxy(ReflectionLibrary lib) {
         this.lib = lib;
      }

      @Override
      public boolean isNull(Object receiver_) {
         try {
            return (Boolean)this.lib.send(receiver_, InteropLibraryGen.IS_NULL);
         } catch (RuntimeException var3) {
            throw var3;
         } catch (Exception var4) {
            throw CompilerDirectives.shouldNotReachHere(var4);
         }
      }

      @Override
      public boolean isBoolean(Object receiver_) {
         try {
            return (Boolean)this.lib.send(receiver_, InteropLibraryGen.IS_BOOLEAN);
         } catch (RuntimeException var3) {
            throw var3;
         } catch (Exception var4) {
            throw CompilerDirectives.shouldNotReachHere(var4);
         }
      }

      @Override
      public boolean asBoolean(Object receiver_) throws UnsupportedMessageException {
         try {
            return (Boolean)this.lib.send(receiver_, InteropLibraryGen.AS_BOOLEAN);
         } catch (RuntimeException | UnsupportedMessageException var3) {
            throw var3;
         } catch (Exception var4) {
            throw CompilerDirectives.shouldNotReachHere(var4);
         }
      }

      @Override
      public boolean isExecutable(Object receiver_) {
         try {
            return (Boolean)this.lib.send(receiver_, InteropLibraryGen.IS_EXECUTABLE);
         } catch (RuntimeException var3) {
            throw var3;
         } catch (Exception var4) {
            throw CompilerDirectives.shouldNotReachHere(var4);
         }
      }

      @Override
      public Object execute(Object receiver_, Object... arguments) throws UnsupportedTypeException, ArityException, UnsupportedMessageException {
         try {
            return this.lib.send(receiver_, InteropLibraryGen.EXECUTE, (Object)arguments);
         } catch (ArityException | UnsupportedMessageException | RuntimeException | UnsupportedTypeException var4) {
            throw var4;
         } catch (Exception var5) {
            throw CompilerDirectives.shouldNotReachHere(var5);
         }
      }

      @Override
      public boolean hasExecutableName(Object receiver_) {
         try {
            return (Boolean)this.lib.send(receiver_, InteropLibraryGen.HAS_EXECUTABLE_NAME);
         } catch (RuntimeException var3) {
            throw var3;
         } catch (Exception var4) {
            throw CompilerDirectives.shouldNotReachHere(var4);
         }
      }

      @Override
      public Object getExecutableName(Object receiver_) throws UnsupportedMessageException {
         try {
            return this.lib.send(receiver_, InteropLibraryGen.GET_EXECUTABLE_NAME);
         } catch (RuntimeException | UnsupportedMessageException var3) {
            throw var3;
         } catch (Exception var4) {
            throw CompilerDirectives.shouldNotReachHere(var4);
         }
      }

      @Override
      public boolean hasDeclaringMetaObject(Object receiver_) {
         try {
            return (Boolean)this.lib.send(receiver_, InteropLibraryGen.HAS_DECLARING_META_OBJECT);
         } catch (RuntimeException var3) {
            throw var3;
         } catch (Exception var4) {
            throw CompilerDirectives.shouldNotReachHere(var4);
         }
      }

      @Override
      public Object getDeclaringMetaObject(Object receiver_) throws UnsupportedMessageException {
         try {
            return this.lib.send(receiver_, InteropLibraryGen.GET_DECLARING_META_OBJECT);
         } catch (RuntimeException | UnsupportedMessageException var3) {
            throw var3;
         } catch (Exception var4) {
            throw CompilerDirectives.shouldNotReachHere(var4);
         }
      }

      @Override
      public boolean isInstantiable(Object receiver_) {
         try {
            return (Boolean)this.lib.send(receiver_, InteropLibraryGen.IS_INSTANTIABLE);
         } catch (RuntimeException var3) {
            throw var3;
         } catch (Exception var4) {
            throw CompilerDirectives.shouldNotReachHere(var4);
         }
      }

      @Override
      public Object instantiate(Object receiver_, Object... arguments) throws UnsupportedTypeException, ArityException, UnsupportedMessageException {
         try {
            return this.lib.send(receiver_, InteropLibraryGen.INSTANTIATE, (Object)arguments);
         } catch (ArityException | UnsupportedMessageException | RuntimeException | UnsupportedTypeException var4) {
            throw var4;
         } catch (Exception var5) {
            throw CompilerDirectives.shouldNotReachHere(var5);
         }
      }

      @Override
      public boolean isString(Object receiver_) {
         try {
            return (Boolean)this.lib.send(receiver_, InteropLibraryGen.IS_STRING);
         } catch (RuntimeException var3) {
            throw var3;
         } catch (Exception var4) {
            throw CompilerDirectives.shouldNotReachHere(var4);
         }
      }

      @Override
      public String asString(Object receiver_) throws UnsupportedMessageException {
         try {
            return (String)this.lib.send(receiver_, InteropLibraryGen.AS_STRING);
         } catch (RuntimeException | UnsupportedMessageException var3) {
            throw var3;
         } catch (Exception var4) {
            throw CompilerDirectives.shouldNotReachHere(var4);
         }
      }

      @Override
      public TruffleString asTruffleString(Object receiver_) throws UnsupportedMessageException {
         try {
            return (TruffleString)this.lib.send(receiver_, InteropLibraryGen.AS_TRUFFLE_STRING);
         } catch (RuntimeException | UnsupportedMessageException var3) {
            throw var3;
         } catch (Exception var4) {
            throw CompilerDirectives.shouldNotReachHere(var4);
         }
      }

      @Override
      public boolean isNumber(Object receiver_) {
         try {
            return (Boolean)this.lib.send(receiver_, InteropLibraryGen.IS_NUMBER);
         } catch (RuntimeException var3) {
            throw var3;
         } catch (Exception var4) {
            throw CompilerDirectives.shouldNotReachHere(var4);
         }
      }

      @Override
      public boolean fitsInByte(Object receiver_) {
         try {
            return (Boolean)this.lib.send(receiver_, InteropLibraryGen.FITS_IN_BYTE);
         } catch (RuntimeException var3) {
            throw var3;
         } catch (Exception var4) {
            throw CompilerDirectives.shouldNotReachHere(var4);
         }
      }

      @Override
      public boolean fitsInShort(Object receiver_) {
         try {
            return (Boolean)this.lib.send(receiver_, InteropLibraryGen.FITS_IN_SHORT);
         } catch (RuntimeException var3) {
            throw var3;
         } catch (Exception var4) {
            throw CompilerDirectives.shouldNotReachHere(var4);
         }
      }

      @Override
      public boolean fitsInInt(Object receiver_) {
         try {
            return (Boolean)this.lib.send(receiver_, InteropLibraryGen.FITS_IN_INT);
         } catch (RuntimeException var3) {
            throw var3;
         } catch (Exception var4) {
            throw CompilerDirectives.shouldNotReachHere(var4);
         }
      }

      @Override
      public boolean fitsInLong(Object receiver_) {
         try {
            return (Boolean)this.lib.send(receiver_, InteropLibraryGen.FITS_IN_LONG);
         } catch (RuntimeException var3) {
            throw var3;
         } catch (Exception var4) {
            throw CompilerDirectives.shouldNotReachHere(var4);
         }
      }

      @Override
      public boolean fitsInFloat(Object receiver_) {
         try {
            return (Boolean)this.lib.send(receiver_, InteropLibraryGen.FITS_IN_FLOAT);
         } catch (RuntimeException var3) {
            throw var3;
         } catch (Exception var4) {
            throw CompilerDirectives.shouldNotReachHere(var4);
         }
      }

      @Override
      public boolean fitsInDouble(Object receiver_) {
         try {
            return (Boolean)this.lib.send(receiver_, InteropLibraryGen.FITS_IN_DOUBLE);
         } catch (RuntimeException var3) {
            throw var3;
         } catch (Exception var4) {
            throw CompilerDirectives.shouldNotReachHere(var4);
         }
      }

      @Override
      public byte asByte(Object receiver_) throws UnsupportedMessageException {
         try {
            return (Byte)this.lib.send(receiver_, InteropLibraryGen.AS_BYTE);
         } catch (RuntimeException | UnsupportedMessageException var3) {
            throw var3;
         } catch (Exception var4) {
            throw CompilerDirectives.shouldNotReachHere(var4);
         }
      }

      @Override
      public short asShort(Object receiver_) throws UnsupportedMessageException {
         try {
            return (Short)this.lib.send(receiver_, InteropLibraryGen.AS_SHORT);
         } catch (RuntimeException | UnsupportedMessageException var3) {
            throw var3;
         } catch (Exception var4) {
            throw CompilerDirectives.shouldNotReachHere(var4);
         }
      }

      @Override
      public int asInt(Object receiver_) throws UnsupportedMessageException {
         try {
            return (Integer)this.lib.send(receiver_, InteropLibraryGen.AS_INT);
         } catch (RuntimeException | UnsupportedMessageException var3) {
            throw var3;
         } catch (Exception var4) {
            throw CompilerDirectives.shouldNotReachHere(var4);
         }
      }

      @Override
      public long asLong(Object receiver_) throws UnsupportedMessageException {
         try {
            return (Long)this.lib.send(receiver_, InteropLibraryGen.AS_LONG);
         } catch (RuntimeException | UnsupportedMessageException var3) {
            throw var3;
         } catch (Exception var4) {
            throw CompilerDirectives.shouldNotReachHere(var4);
         }
      }

      @Override
      public float asFloat(Object receiver_) throws UnsupportedMessageException {
         try {
            return (Float)this.lib.send(receiver_, InteropLibraryGen.AS_FLOAT);
         } catch (RuntimeException | UnsupportedMessageException var3) {
            throw var3;
         } catch (Exception var4) {
            throw CompilerDirectives.shouldNotReachHere(var4);
         }
      }

      @Override
      public double asDouble(Object receiver_) throws UnsupportedMessageException {
         try {
            return (Double)this.lib.send(receiver_, InteropLibraryGen.AS_DOUBLE);
         } catch (RuntimeException | UnsupportedMessageException var3) {
            throw var3;
         } catch (Exception var4) {
            throw CompilerDirectives.shouldNotReachHere(var4);
         }
      }

      @Override
      public boolean hasMembers(Object receiver_) {
         try {
            return (Boolean)this.lib.send(receiver_, InteropLibraryGen.HAS_MEMBERS);
         } catch (RuntimeException var3) {
            throw var3;
         } catch (Exception var4) {
            throw CompilerDirectives.shouldNotReachHere(var4);
         }
      }

      @Override
      public Object getMembers(Object receiver_, boolean includeInternal) throws UnsupportedMessageException {
         try {
            return this.lib.send(receiver_, InteropLibraryGen.GET_MEMBERS, includeInternal);
         } catch (RuntimeException | UnsupportedMessageException var4) {
            throw var4;
         } catch (Exception var5) {
            throw CompilerDirectives.shouldNotReachHere(var5);
         }
      }

      @Override
      public boolean isMemberReadable(Object receiver_, String member) {
         try {
            return (Boolean)this.lib.send(receiver_, InteropLibraryGen.IS_MEMBER_READABLE, member);
         } catch (RuntimeException var4) {
            throw var4;
         } catch (Exception var5) {
            throw CompilerDirectives.shouldNotReachHere(var5);
         }
      }

      @Override
      public Object readMember(Object receiver_, String member) throws UnsupportedMessageException, UnknownIdentifierException {
         try {
            return this.lib.send(receiver_, InteropLibraryGen.READ_MEMBER, member);
         } catch (UnknownIdentifierException | RuntimeException | UnsupportedMessageException var4) {
            throw var4;
         } catch (Exception var5) {
            throw CompilerDirectives.shouldNotReachHere(var5);
         }
      }

      @Override
      public boolean isMemberModifiable(Object receiver_, String member) {
         try {
            return (Boolean)this.lib.send(receiver_, InteropLibraryGen.IS_MEMBER_MODIFIABLE, member);
         } catch (RuntimeException var4) {
            throw var4;
         } catch (Exception var5) {
            throw CompilerDirectives.shouldNotReachHere(var5);
         }
      }

      @Override
      public boolean isMemberInsertable(Object receiver_, String member) {
         try {
            return (Boolean)this.lib.send(receiver_, InteropLibraryGen.IS_MEMBER_INSERTABLE, member);
         } catch (RuntimeException var4) {
            throw var4;
         } catch (Exception var5) {
            throw CompilerDirectives.shouldNotReachHere(var5);
         }
      }

      @Override
      public void writeMember(Object receiver_, String member, Object value) throws UnsupportedMessageException, UnknownIdentifierException, UnsupportedTypeException {
         try {
            this.lib.send(receiver_, InteropLibraryGen.WRITE_MEMBER, member, value);
         } catch (UnknownIdentifierException | UnsupportedTypeException | RuntimeException | UnsupportedMessageException var5) {
            throw var5;
         } catch (Exception var6) {
            throw CompilerDirectives.shouldNotReachHere(var6);
         }
      }

      @Override
      public boolean isMemberRemovable(Object receiver_, String member) {
         try {
            return (Boolean)this.lib.send(receiver_, InteropLibraryGen.IS_MEMBER_REMOVABLE, member);
         } catch (RuntimeException var4) {
            throw var4;
         } catch (Exception var5) {
            throw CompilerDirectives.shouldNotReachHere(var5);
         }
      }

      @Override
      public void removeMember(Object receiver_, String member) throws UnsupportedMessageException, UnknownIdentifierException {
         try {
            this.lib.send(receiver_, InteropLibraryGen.REMOVE_MEMBER, member);
         } catch (UnknownIdentifierException | RuntimeException | UnsupportedMessageException var4) {
            throw var4;
         } catch (Exception var5) {
            throw CompilerDirectives.shouldNotReachHere(var5);
         }
      }

      @Override
      public boolean isMemberInvocable(Object receiver_, String member) {
         try {
            return (Boolean)this.lib.send(receiver_, InteropLibraryGen.IS_MEMBER_INVOCABLE, member);
         } catch (RuntimeException var4) {
            throw var4;
         } catch (Exception var5) {
            throw CompilerDirectives.shouldNotReachHere(var5);
         }
      }

      @Override
      public Object invokeMember(Object receiver_, String member, Object... arguments) throws UnsupportedMessageException, ArityException, UnknownIdentifierException, UnsupportedTypeException {
         try {
            return this.lib.send(receiver_, InteropLibraryGen.INVOKE_MEMBER, member, arguments);
         } catch (ArityException | UnknownIdentifierException | UnsupportedTypeException | RuntimeException | UnsupportedMessageException var5) {
            throw var5;
         } catch (Exception var6) {
            throw CompilerDirectives.shouldNotReachHere(var6);
         }
      }

      @Override
      public boolean isMemberInternal(Object receiver_, String member) {
         try {
            return (Boolean)this.lib.send(receiver_, InteropLibraryGen.IS_MEMBER_INTERNAL, member);
         } catch (RuntimeException var4) {
            throw var4;
         } catch (Exception var5) {
            throw CompilerDirectives.shouldNotReachHere(var5);
         }
      }

      @Override
      public boolean hasMemberReadSideEffects(Object receiver_, String member) {
         try {
            return (Boolean)this.lib.send(receiver_, InteropLibraryGen.HAS_MEMBER_READ_SIDE_EFFECTS, member);
         } catch (RuntimeException var4) {
            throw var4;
         } catch (Exception var5) {
            throw CompilerDirectives.shouldNotReachHere(var5);
         }
      }

      @Override
      public boolean hasMemberWriteSideEffects(Object receiver_, String member) {
         try {
            return (Boolean)this.lib.send(receiver_, InteropLibraryGen.HAS_MEMBER_WRITE_SIDE_EFFECTS, member);
         } catch (RuntimeException var4) {
            throw var4;
         } catch (Exception var5) {
            throw CompilerDirectives.shouldNotReachHere(var5);
         }
      }

      @Override
      public boolean hasHashEntries(Object receiver_) {
         try {
            return (Boolean)this.lib.send(receiver_, InteropLibraryGen.HAS_HASH_ENTRIES);
         } catch (RuntimeException var3) {
            throw var3;
         } catch (Exception var4) {
            throw CompilerDirectives.shouldNotReachHere(var4);
         }
      }

      @Override
      public long getHashSize(Object receiver_) throws UnsupportedMessageException {
         try {
            return (Long)this.lib.send(receiver_, InteropLibraryGen.GET_HASH_SIZE);
         } catch (RuntimeException | UnsupportedMessageException var3) {
            throw var3;
         } catch (Exception var4) {
            throw CompilerDirectives.shouldNotReachHere(var4);
         }
      }

      @Override
      public boolean isHashEntryReadable(Object receiver_, Object key) {
         try {
            return (Boolean)this.lib.send(receiver_, InteropLibraryGen.IS_HASH_ENTRY_READABLE, key);
         } catch (RuntimeException var4) {
            throw var4;
         } catch (Exception var5) {
            throw CompilerDirectives.shouldNotReachHere(var5);
         }
      }

      @Override
      public Object readHashValue(Object receiver_, Object key) throws UnsupportedMessageException, UnknownKeyException {
         try {
            return this.lib.send(receiver_, InteropLibraryGen.READ_HASH_VALUE, key);
         } catch (UnknownKeyException | RuntimeException | UnsupportedMessageException var4) {
            throw var4;
         } catch (Exception var5) {
            throw CompilerDirectives.shouldNotReachHere(var5);
         }
      }

      @Override
      public Object readHashValueOrDefault(Object receiver_, Object key, Object defaultValue) throws UnsupportedMessageException {
         try {
            return this.lib.send(receiver_, InteropLibraryGen.READ_HASH_VALUE_OR_DEFAULT, key, defaultValue);
         } catch (RuntimeException | UnsupportedMessageException var5) {
            throw var5;
         } catch (Exception var6) {
            throw CompilerDirectives.shouldNotReachHere(var6);
         }
      }

      @Override
      public boolean isHashEntryModifiable(Object receiver_, Object key) {
         try {
            return (Boolean)this.lib.send(receiver_, InteropLibraryGen.IS_HASH_ENTRY_MODIFIABLE, key);
         } catch (RuntimeException var4) {
            throw var4;
         } catch (Exception var5) {
            throw CompilerDirectives.shouldNotReachHere(var5);
         }
      }

      @Override
      public boolean isHashEntryInsertable(Object receiver_, Object key) {
         try {
            return (Boolean)this.lib.send(receiver_, InteropLibraryGen.IS_HASH_ENTRY_INSERTABLE, key);
         } catch (RuntimeException var4) {
            throw var4;
         } catch (Exception var5) {
            throw CompilerDirectives.shouldNotReachHere(var5);
         }
      }

      @Override
      public boolean isHashEntryWritable(Object receiver_, Object key) {
         try {
            return (Boolean)this.lib.send(receiver_, InteropLibraryGen.IS_HASH_ENTRY_WRITABLE, key);
         } catch (RuntimeException var4) {
            throw var4;
         } catch (Exception var5) {
            throw CompilerDirectives.shouldNotReachHere(var5);
         }
      }

      @Override
      public void writeHashEntry(Object receiver_, Object key, Object value) throws UnsupportedMessageException, UnknownKeyException, UnsupportedTypeException {
         try {
            this.lib.send(receiver_, InteropLibraryGen.WRITE_HASH_ENTRY, key, value);
         } catch (UnknownKeyException | UnsupportedTypeException | RuntimeException | UnsupportedMessageException var5) {
            throw var5;
         } catch (Exception var6) {
            throw CompilerDirectives.shouldNotReachHere(var6);
         }
      }

      @Override
      public boolean isHashEntryRemovable(Object receiver_, Object key) {
         try {
            return (Boolean)this.lib.send(receiver_, InteropLibraryGen.IS_HASH_ENTRY_REMOVABLE, key);
         } catch (RuntimeException var4) {
            throw var4;
         } catch (Exception var5) {
            throw CompilerDirectives.shouldNotReachHere(var5);
         }
      }

      @Override
      public void removeHashEntry(Object receiver_, Object key) throws UnsupportedMessageException, UnknownKeyException {
         try {
            this.lib.send(receiver_, InteropLibraryGen.REMOVE_HASH_ENTRY, key);
         } catch (UnknownKeyException | RuntimeException | UnsupportedMessageException var4) {
            throw var4;
         } catch (Exception var5) {
            throw CompilerDirectives.shouldNotReachHere(var5);
         }
      }

      @Override
      public boolean isHashEntryExisting(Object receiver_, Object key) {
         try {
            return (Boolean)this.lib.send(receiver_, InteropLibraryGen.IS_HASH_ENTRY_EXISTING, key);
         } catch (RuntimeException var4) {
            throw var4;
         } catch (Exception var5) {
            throw CompilerDirectives.shouldNotReachHere(var5);
         }
      }

      @Override
      public Object getHashEntriesIterator(Object receiver_) throws UnsupportedMessageException {
         try {
            return this.lib.send(receiver_, InteropLibraryGen.GET_HASH_ENTRIES_ITERATOR);
         } catch (RuntimeException | UnsupportedMessageException var3) {
            throw var3;
         } catch (Exception var4) {
            throw CompilerDirectives.shouldNotReachHere(var4);
         }
      }

      @Override
      public Object getHashKeysIterator(Object receiver_) throws UnsupportedMessageException {
         try {
            return this.lib.send(receiver_, InteropLibraryGen.GET_HASH_KEYS_ITERATOR);
         } catch (RuntimeException | UnsupportedMessageException var3) {
            throw var3;
         } catch (Exception var4) {
            throw CompilerDirectives.shouldNotReachHere(var4);
         }
      }

      @Override
      public Object getHashValuesIterator(Object receiver_) throws UnsupportedMessageException {
         try {
            return this.lib.send(receiver_, InteropLibraryGen.GET_HASH_VALUES_ITERATOR);
         } catch (RuntimeException | UnsupportedMessageException var3) {
            throw var3;
         } catch (Exception var4) {
            throw CompilerDirectives.shouldNotReachHere(var4);
         }
      }

      @Override
      public boolean hasArrayElements(Object receiver_) {
         try {
            return (Boolean)this.lib.send(receiver_, InteropLibraryGen.HAS_ARRAY_ELEMENTS);
         } catch (RuntimeException var3) {
            throw var3;
         } catch (Exception var4) {
            throw CompilerDirectives.shouldNotReachHere(var4);
         }
      }

      @Override
      public Object readArrayElement(Object receiver_, long index) throws UnsupportedMessageException, InvalidArrayIndexException {
         try {
            return this.lib.send(receiver_, InteropLibraryGen.READ_ARRAY_ELEMENT, index);
         } catch (InvalidArrayIndexException | RuntimeException | UnsupportedMessageException var5) {
            throw var5;
         } catch (Exception var6) {
            throw CompilerDirectives.shouldNotReachHere(var6);
         }
      }

      @Override
      public long getArraySize(Object receiver_) throws UnsupportedMessageException {
         try {
            return (Long)this.lib.send(receiver_, InteropLibraryGen.GET_ARRAY_SIZE);
         } catch (RuntimeException | UnsupportedMessageException var3) {
            throw var3;
         } catch (Exception var4) {
            throw CompilerDirectives.shouldNotReachHere(var4);
         }
      }

      @Override
      public boolean isArrayElementReadable(Object receiver_, long index) {
         try {
            return (Boolean)this.lib.send(receiver_, InteropLibraryGen.IS_ARRAY_ELEMENT_READABLE, index);
         } catch (RuntimeException var5) {
            throw var5;
         } catch (Exception var6) {
            throw CompilerDirectives.shouldNotReachHere(var6);
         }
      }

      @Override
      public void writeArrayElement(Object receiver_, long index, Object value) throws UnsupportedMessageException, UnsupportedTypeException, InvalidArrayIndexException {
         try {
            this.lib.send(receiver_, InteropLibraryGen.WRITE_ARRAY_ELEMENT, index, value);
         } catch (UnsupportedTypeException | InvalidArrayIndexException | RuntimeException | UnsupportedMessageException var6) {
            throw var6;
         } catch (Exception var7) {
            throw CompilerDirectives.shouldNotReachHere(var7);
         }
      }

      @Override
      public void removeArrayElement(Object receiver_, long index) throws UnsupportedMessageException, InvalidArrayIndexException {
         try {
            this.lib.send(receiver_, InteropLibraryGen.REMOVE_ARRAY_ELEMENT, index);
         } catch (InvalidArrayIndexException | RuntimeException | UnsupportedMessageException var5) {
            throw var5;
         } catch (Exception var6) {
            throw CompilerDirectives.shouldNotReachHere(var6);
         }
      }

      @Override
      public boolean isArrayElementModifiable(Object receiver_, long index) {
         try {
            return (Boolean)this.lib.send(receiver_, InteropLibraryGen.IS_ARRAY_ELEMENT_MODIFIABLE, index);
         } catch (RuntimeException var5) {
            throw var5;
         } catch (Exception var6) {
            throw CompilerDirectives.shouldNotReachHere(var6);
         }
      }

      @Override
      public boolean isArrayElementInsertable(Object receiver_, long index) {
         try {
            return (Boolean)this.lib.send(receiver_, InteropLibraryGen.IS_ARRAY_ELEMENT_INSERTABLE, index);
         } catch (RuntimeException var5) {
            throw var5;
         } catch (Exception var6) {
            throw CompilerDirectives.shouldNotReachHere(var6);
         }
      }

      @Override
      public boolean isArrayElementRemovable(Object receiver_, long index) {
         try {
            return (Boolean)this.lib.send(receiver_, InteropLibraryGen.IS_ARRAY_ELEMENT_REMOVABLE, index);
         } catch (RuntimeException var5) {
            throw var5;
         } catch (Exception var6) {
            throw CompilerDirectives.shouldNotReachHere(var6);
         }
      }

      @Override
      public boolean hasBufferElements(Object receiver_) {
         try {
            return (Boolean)this.lib.send(receiver_, InteropLibraryGen.HAS_BUFFER_ELEMENTS);
         } catch (RuntimeException var3) {
            throw var3;
         } catch (Exception var4) {
            throw CompilerDirectives.shouldNotReachHere(var4);
         }
      }

      @Override
      public boolean isBufferWritable(Object receiver_) throws UnsupportedMessageException {
         try {
            return (Boolean)this.lib.send(receiver_, InteropLibraryGen.IS_BUFFER_WRITABLE);
         } catch (RuntimeException | UnsupportedMessageException var3) {
            throw var3;
         } catch (Exception var4) {
            throw CompilerDirectives.shouldNotReachHere(var4);
         }
      }

      @Override
      public long getBufferSize(Object receiver_) throws UnsupportedMessageException {
         try {
            return (Long)this.lib.send(receiver_, InteropLibraryGen.GET_BUFFER_SIZE);
         } catch (RuntimeException | UnsupportedMessageException var3) {
            throw var3;
         } catch (Exception var4) {
            throw CompilerDirectives.shouldNotReachHere(var4);
         }
      }

      @Override
      public byte readBufferByte(Object receiver_, long byteOffset) throws UnsupportedMessageException, InvalidBufferOffsetException {
         try {
            return (Byte)this.lib.send(receiver_, InteropLibraryGen.READ_BUFFER_BYTE, byteOffset);
         } catch (InvalidBufferOffsetException | RuntimeException | UnsupportedMessageException var5) {
            throw var5;
         } catch (Exception var6) {
            throw CompilerDirectives.shouldNotReachHere(var6);
         }
      }

      @Override
      public void writeBufferByte(Object receiver_, long byteOffset, byte value) throws UnsupportedMessageException, InvalidBufferOffsetException {
         try {
            this.lib.send(receiver_, InteropLibraryGen.WRITE_BUFFER_BYTE, byteOffset, value);
         } catch (InvalidBufferOffsetException | RuntimeException | UnsupportedMessageException var6) {
            throw var6;
         } catch (Exception var7) {
            throw CompilerDirectives.shouldNotReachHere(var7);
         }
      }

      @Override
      public short readBufferShort(Object receiver_, ByteOrder order, long byteOffset) throws UnsupportedMessageException, InvalidBufferOffsetException {
         try {
            return (Short)this.lib.send(receiver_, InteropLibraryGen.READ_BUFFER_SHORT, order, byteOffset);
         } catch (InvalidBufferOffsetException | RuntimeException | UnsupportedMessageException var6) {
            throw var6;
         } catch (Exception var7) {
            throw CompilerDirectives.shouldNotReachHere(var7);
         }
      }

      @Override
      public void writeBufferShort(Object receiver_, ByteOrder order, long byteOffset, short value) throws UnsupportedMessageException, InvalidBufferOffsetException {
         try {
            this.lib.send(receiver_, InteropLibraryGen.WRITE_BUFFER_SHORT, order, byteOffset, value);
         } catch (InvalidBufferOffsetException | RuntimeException | UnsupportedMessageException var7) {
            throw var7;
         } catch (Exception var8) {
            throw CompilerDirectives.shouldNotReachHere(var8);
         }
      }

      @Override
      public int readBufferInt(Object receiver_, ByteOrder order, long byteOffset) throws UnsupportedMessageException, InvalidBufferOffsetException {
         try {
            return (Integer)this.lib.send(receiver_, InteropLibraryGen.READ_BUFFER_INT, order, byteOffset);
         } catch (InvalidBufferOffsetException | RuntimeException | UnsupportedMessageException var6) {
            throw var6;
         } catch (Exception var7) {
            throw CompilerDirectives.shouldNotReachHere(var7);
         }
      }

      @Override
      public void writeBufferInt(Object receiver_, ByteOrder order, long byteOffset, int value) throws UnsupportedMessageException, InvalidBufferOffsetException {
         try {
            this.lib.send(receiver_, InteropLibraryGen.WRITE_BUFFER_INT, order, byteOffset, value);
         } catch (InvalidBufferOffsetException | RuntimeException | UnsupportedMessageException var7) {
            throw var7;
         } catch (Exception var8) {
            throw CompilerDirectives.shouldNotReachHere(var8);
         }
      }

      @Override
      public long readBufferLong(Object receiver_, ByteOrder order, long byteOffset) throws UnsupportedMessageException, InvalidBufferOffsetException {
         try {
            return (Long)this.lib.send(receiver_, InteropLibraryGen.READ_BUFFER_LONG, order, byteOffset);
         } catch (InvalidBufferOffsetException | RuntimeException | UnsupportedMessageException var6) {
            throw var6;
         } catch (Exception var7) {
            throw CompilerDirectives.shouldNotReachHere(var7);
         }
      }

      @Override
      public void writeBufferLong(Object receiver_, ByteOrder order, long byteOffset, long value) throws UnsupportedMessageException, InvalidBufferOffsetException {
         try {
            this.lib.send(receiver_, InteropLibraryGen.WRITE_BUFFER_LONG, order, byteOffset, value);
         } catch (InvalidBufferOffsetException | RuntimeException | UnsupportedMessageException var8) {
            throw var8;
         } catch (Exception var9) {
            throw CompilerDirectives.shouldNotReachHere(var9);
         }
      }

      @Override
      public float readBufferFloat(Object receiver_, ByteOrder order, long byteOffset) throws UnsupportedMessageException, InvalidBufferOffsetException {
         try {
            return (Float)this.lib.send(receiver_, InteropLibraryGen.READ_BUFFER_FLOAT, order, byteOffset);
         } catch (InvalidBufferOffsetException | RuntimeException | UnsupportedMessageException var6) {
            throw var6;
         } catch (Exception var7) {
            throw CompilerDirectives.shouldNotReachHere(var7);
         }
      }

      @Override
      public void writeBufferFloat(Object receiver_, ByteOrder order, long byteOffset, float value) throws UnsupportedMessageException, InvalidBufferOffsetException {
         try {
            this.lib.send(receiver_, InteropLibraryGen.WRITE_BUFFER_FLOAT, order, byteOffset, value);
         } catch (InvalidBufferOffsetException | RuntimeException | UnsupportedMessageException var7) {
            throw var7;
         } catch (Exception var8) {
            throw CompilerDirectives.shouldNotReachHere(var8);
         }
      }

      @Override
      public double readBufferDouble(Object receiver_, ByteOrder order, long byteOffset) throws UnsupportedMessageException, InvalidBufferOffsetException {
         try {
            return (Double)this.lib.send(receiver_, InteropLibraryGen.READ_BUFFER_DOUBLE, order, byteOffset);
         } catch (InvalidBufferOffsetException | RuntimeException | UnsupportedMessageException var6) {
            throw var6;
         } catch (Exception var7) {
            throw CompilerDirectives.shouldNotReachHere(var7);
         }
      }

      @Override
      public void writeBufferDouble(Object receiver_, ByteOrder order, long byteOffset, double value) throws UnsupportedMessageException, InvalidBufferOffsetException {
         try {
            this.lib.send(receiver_, InteropLibraryGen.WRITE_BUFFER_DOUBLE, order, byteOffset, value);
         } catch (InvalidBufferOffsetException | RuntimeException | UnsupportedMessageException var8) {
            throw var8;
         } catch (Exception var9) {
            throw CompilerDirectives.shouldNotReachHere(var9);
         }
      }

      @Override
      public boolean isPointer(Object receiver_) {
         try {
            return (Boolean)this.lib.send(receiver_, InteropLibraryGen.IS_POINTER);
         } catch (RuntimeException var3) {
            throw var3;
         } catch (Exception var4) {
            throw CompilerDirectives.shouldNotReachHere(var4);
         }
      }

      @Override
      public long asPointer(Object receiver_) throws UnsupportedMessageException {
         try {
            return (Long)this.lib.send(receiver_, InteropLibraryGen.AS_POINTER);
         } catch (RuntimeException | UnsupportedMessageException var3) {
            throw var3;
         } catch (Exception var4) {
            throw CompilerDirectives.shouldNotReachHere(var4);
         }
      }

      @Override
      public void toNative(Object receiver_) {
         try {
            this.lib.send(receiver_, InteropLibraryGen.TO_NATIVE);
         } catch (RuntimeException var3) {
            throw var3;
         } catch (Exception var4) {
            throw CompilerDirectives.shouldNotReachHere(var4);
         }
      }

      @Override
      public Instant asInstant(Object receiver_) throws UnsupportedMessageException {
         try {
            return (Instant)this.lib.send(receiver_, InteropLibraryGen.AS_INSTANT);
         } catch (RuntimeException | UnsupportedMessageException var3) {
            throw var3;
         } catch (Exception var4) {
            throw CompilerDirectives.shouldNotReachHere(var4);
         }
      }

      @Override
      public boolean isTimeZone(Object receiver_) {
         try {
            return (Boolean)this.lib.send(receiver_, InteropLibraryGen.IS_TIME_ZONE);
         } catch (RuntimeException var3) {
            throw var3;
         } catch (Exception var4) {
            throw CompilerDirectives.shouldNotReachHere(var4);
         }
      }

      @Override
      public ZoneId asTimeZone(Object receiver_) throws UnsupportedMessageException {
         try {
            return (ZoneId)this.lib.send(receiver_, InteropLibraryGen.AS_TIME_ZONE);
         } catch (RuntimeException | UnsupportedMessageException var3) {
            throw var3;
         } catch (Exception var4) {
            throw CompilerDirectives.shouldNotReachHere(var4);
         }
      }

      @Override
      public boolean isDate(Object receiver_) {
         try {
            return (Boolean)this.lib.send(receiver_, InteropLibraryGen.IS_DATE);
         } catch (RuntimeException var3) {
            throw var3;
         } catch (Exception var4) {
            throw CompilerDirectives.shouldNotReachHere(var4);
         }
      }

      @Override
      public LocalDate asDate(Object receiver_) throws UnsupportedMessageException {
         try {
            return (LocalDate)this.lib.send(receiver_, InteropLibraryGen.AS_DATE);
         } catch (RuntimeException | UnsupportedMessageException var3) {
            throw var3;
         } catch (Exception var4) {
            throw CompilerDirectives.shouldNotReachHere(var4);
         }
      }

      @Override
      public boolean isTime(Object receiver_) {
         try {
            return (Boolean)this.lib.send(receiver_, InteropLibraryGen.IS_TIME);
         } catch (RuntimeException var3) {
            throw var3;
         } catch (Exception var4) {
            throw CompilerDirectives.shouldNotReachHere(var4);
         }
      }

      @Override
      public LocalTime asTime(Object receiver_) throws UnsupportedMessageException {
         try {
            return (LocalTime)this.lib.send(receiver_, InteropLibraryGen.AS_TIME);
         } catch (RuntimeException | UnsupportedMessageException var3) {
            throw var3;
         } catch (Exception var4) {
            throw CompilerDirectives.shouldNotReachHere(var4);
         }
      }

      @Override
      public boolean isDuration(Object receiver_) {
         try {
            return (Boolean)this.lib.send(receiver_, InteropLibraryGen.IS_DURATION);
         } catch (RuntimeException var3) {
            throw var3;
         } catch (Exception var4) {
            throw CompilerDirectives.shouldNotReachHere(var4);
         }
      }

      @Override
      public Duration asDuration(Object receiver_) throws UnsupportedMessageException {
         try {
            return (Duration)this.lib.send(receiver_, InteropLibraryGen.AS_DURATION);
         } catch (RuntimeException | UnsupportedMessageException var3) {
            throw var3;
         } catch (Exception var4) {
            throw CompilerDirectives.shouldNotReachHere(var4);
         }
      }

      @Override
      public boolean isException(Object receiver_) {
         try {
            return (Boolean)this.lib.send(receiver_, InteropLibraryGen.IS_EXCEPTION);
         } catch (RuntimeException var3) {
            throw var3;
         } catch (Exception var4) {
            throw CompilerDirectives.shouldNotReachHere(var4);
         }
      }

      @Override
      public RuntimeException throwException(Object receiver_) throws UnsupportedMessageException {
         try {
            return (RuntimeException)this.lib.send(receiver_, InteropLibraryGen.THROW_EXCEPTION);
         } catch (RuntimeException | UnsupportedMessageException var3) {
            throw var3;
         } catch (Exception var4) {
            throw CompilerDirectives.shouldNotReachHere(var4);
         }
      }

      @Override
      public ExceptionType getExceptionType(Object receiver_) throws UnsupportedMessageException {
         try {
            return (ExceptionType)this.lib.send(receiver_, InteropLibraryGen.GET_EXCEPTION_TYPE);
         } catch (RuntimeException | UnsupportedMessageException var3) {
            throw var3;
         } catch (Exception var4) {
            throw CompilerDirectives.shouldNotReachHere(var4);
         }
      }

      @Override
      public boolean isExceptionIncompleteSource(Object receiver_) throws UnsupportedMessageException {
         try {
            return (Boolean)this.lib.send(receiver_, InteropLibraryGen.IS_EXCEPTION_INCOMPLETE_SOURCE);
         } catch (RuntimeException | UnsupportedMessageException var3) {
            throw var3;
         } catch (Exception var4) {
            throw CompilerDirectives.shouldNotReachHere(var4);
         }
      }

      @Override
      public int getExceptionExitStatus(Object receiver_) throws UnsupportedMessageException {
         try {
            return (Integer)this.lib.send(receiver_, InteropLibraryGen.GET_EXCEPTION_EXIT_STATUS);
         } catch (RuntimeException | UnsupportedMessageException var3) {
            throw var3;
         } catch (Exception var4) {
            throw CompilerDirectives.shouldNotReachHere(var4);
         }
      }

      @Override
      public boolean hasExceptionCause(Object receiver_) {
         try {
            return (Boolean)this.lib.send(receiver_, InteropLibraryGen.HAS_EXCEPTION_CAUSE);
         } catch (RuntimeException var3) {
            throw var3;
         } catch (Exception var4) {
            throw CompilerDirectives.shouldNotReachHere(var4);
         }
      }

      @Override
      public Object getExceptionCause(Object receiver_) throws UnsupportedMessageException {
         try {
            return this.lib.send(receiver_, InteropLibraryGen.GET_EXCEPTION_CAUSE);
         } catch (RuntimeException | UnsupportedMessageException var3) {
            throw var3;
         } catch (Exception var4) {
            throw CompilerDirectives.shouldNotReachHere(var4);
         }
      }

      @Override
      public boolean hasExceptionMessage(Object receiver_) {
         try {
            return (Boolean)this.lib.send(receiver_, InteropLibraryGen.HAS_EXCEPTION_MESSAGE);
         } catch (RuntimeException var3) {
            throw var3;
         } catch (Exception var4) {
            throw CompilerDirectives.shouldNotReachHere(var4);
         }
      }

      @Override
      public Object getExceptionMessage(Object receiver_) throws UnsupportedMessageException {
         try {
            return this.lib.send(receiver_, InteropLibraryGen.GET_EXCEPTION_MESSAGE);
         } catch (RuntimeException | UnsupportedMessageException var3) {
            throw var3;
         } catch (Exception var4) {
            throw CompilerDirectives.shouldNotReachHere(var4);
         }
      }

      @Override
      public boolean hasExceptionStackTrace(Object receiver_) {
         try {
            return (Boolean)this.lib.send(receiver_, InteropLibraryGen.HAS_EXCEPTION_STACK_TRACE);
         } catch (RuntimeException var3) {
            throw var3;
         } catch (Exception var4) {
            throw CompilerDirectives.shouldNotReachHere(var4);
         }
      }

      @Override
      public Object getExceptionStackTrace(Object receiver_) throws UnsupportedMessageException {
         try {
            return this.lib.send(receiver_, InteropLibraryGen.GET_EXCEPTION_STACK_TRACE);
         } catch (RuntimeException | UnsupportedMessageException var3) {
            throw var3;
         } catch (Exception var4) {
            throw CompilerDirectives.shouldNotReachHere(var4);
         }
      }

      @Override
      public boolean hasIterator(Object receiver_) {
         try {
            return (Boolean)this.lib.send(receiver_, InteropLibraryGen.HAS_ITERATOR);
         } catch (RuntimeException var3) {
            throw var3;
         } catch (Exception var4) {
            throw CompilerDirectives.shouldNotReachHere(var4);
         }
      }

      @Override
      public Object getIterator(Object receiver_) throws UnsupportedMessageException {
         try {
            return this.lib.send(receiver_, InteropLibraryGen.GET_ITERATOR);
         } catch (RuntimeException | UnsupportedMessageException var3) {
            throw var3;
         } catch (Exception var4) {
            throw CompilerDirectives.shouldNotReachHere(var4);
         }
      }

      @Override
      public boolean isIterator(Object receiver_) {
         try {
            return (Boolean)this.lib.send(receiver_, InteropLibraryGen.IS_ITERATOR);
         } catch (RuntimeException var3) {
            throw var3;
         } catch (Exception var4) {
            throw CompilerDirectives.shouldNotReachHere(var4);
         }
      }

      @Override
      public boolean hasIteratorNextElement(Object receiver_) throws UnsupportedMessageException {
         try {
            return (Boolean)this.lib.send(receiver_, InteropLibraryGen.HAS_ITERATOR_NEXT_ELEMENT);
         } catch (RuntimeException | UnsupportedMessageException var3) {
            throw var3;
         } catch (Exception var4) {
            throw CompilerDirectives.shouldNotReachHere(var4);
         }
      }

      @Override
      public Object getIteratorNextElement(Object receiver_) throws UnsupportedMessageException, StopIterationException {
         try {
            return this.lib.send(receiver_, InteropLibraryGen.GET_ITERATOR_NEXT_ELEMENT);
         } catch (StopIterationException | RuntimeException | UnsupportedMessageException var3) {
            throw var3;
         } catch (Exception var4) {
            throw CompilerDirectives.shouldNotReachHere(var4);
         }
      }

      @Override
      public boolean hasSourceLocation(Object receiver_) {
         try {
            return (Boolean)this.lib.send(receiver_, InteropLibraryGen.HAS_SOURCE_LOCATION);
         } catch (RuntimeException var3) {
            throw var3;
         } catch (Exception var4) {
            throw CompilerDirectives.shouldNotReachHere(var4);
         }
      }

      @Override
      public SourceSection getSourceLocation(Object receiver_) throws UnsupportedMessageException {
         try {
            return (SourceSection)this.lib.send(receiver_, InteropLibraryGen.GET_SOURCE_LOCATION);
         } catch (RuntimeException | UnsupportedMessageException var3) {
            throw var3;
         } catch (Exception var4) {
            throw CompilerDirectives.shouldNotReachHere(var4);
         }
      }

      @Override
      public boolean hasLanguage(Object receiver_) {
         try {
            return (Boolean)this.lib.send(receiver_, InteropLibraryGen.HAS_LANGUAGE);
         } catch (RuntimeException var3) {
            throw var3;
         } catch (Exception var4) {
            throw CompilerDirectives.shouldNotReachHere(var4);
         }
      }

      @Override
      public Class<? extends TruffleLanguage<?>> getLanguage(Object receiver_) throws UnsupportedMessageException {
         try {
            return (Class<? extends TruffleLanguage<?>>)this.lib.send(receiver_, InteropLibraryGen.GET_LANGUAGE);
         } catch (RuntimeException | UnsupportedMessageException var3) {
            throw var3;
         } catch (Exception var4) {
            throw CompilerDirectives.shouldNotReachHere(var4);
         }
      }

      @Override
      public boolean hasMetaObject(Object receiver_) {
         try {
            return (Boolean)this.lib.send(receiver_, InteropLibraryGen.HAS_META_OBJECT);
         } catch (RuntimeException var3) {
            throw var3;
         } catch (Exception var4) {
            throw CompilerDirectives.shouldNotReachHere(var4);
         }
      }

      @Override
      public Object getMetaObject(Object receiver_) throws UnsupportedMessageException {
         try {
            return this.lib.send(receiver_, InteropLibraryGen.GET_META_OBJECT);
         } catch (RuntimeException | UnsupportedMessageException var3) {
            throw var3;
         } catch (Exception var4) {
            throw CompilerDirectives.shouldNotReachHere(var4);
         }
      }

      @Override
      public Object toDisplayString(Object receiver_, boolean allowSideEffects) {
         try {
            return this.lib.send(receiver_, InteropLibraryGen.TO_DISPLAY_STRING, allowSideEffects);
         } catch (RuntimeException var4) {
            throw var4;
         } catch (Exception var5) {
            throw CompilerDirectives.shouldNotReachHere(var5);
         }
      }

      @Override
      public boolean isMetaObject(Object receiver_) {
         try {
            return (Boolean)this.lib.send(receiver_, InteropLibraryGen.IS_META_OBJECT);
         } catch (RuntimeException var3) {
            throw var3;
         } catch (Exception var4) {
            throw CompilerDirectives.shouldNotReachHere(var4);
         }
      }

      @Override
      public Object getMetaQualifiedName(Object receiver_) throws UnsupportedMessageException {
         try {
            return this.lib.send(receiver_, InteropLibraryGen.GET_META_QUALIFIED_NAME);
         } catch (RuntimeException | UnsupportedMessageException var3) {
            throw var3;
         } catch (Exception var4) {
            throw CompilerDirectives.shouldNotReachHere(var4);
         }
      }

      @Override
      public Object getMetaSimpleName(Object receiver_) throws UnsupportedMessageException {
         try {
            return this.lib.send(receiver_, InteropLibraryGen.GET_META_SIMPLE_NAME);
         } catch (RuntimeException | UnsupportedMessageException var3) {
            throw var3;
         } catch (Exception var4) {
            throw CompilerDirectives.shouldNotReachHere(var4);
         }
      }

      @Override
      public boolean isMetaInstance(Object receiver_, Object instance) throws UnsupportedMessageException {
         try {
            return (Boolean)this.lib.send(receiver_, InteropLibraryGen.IS_META_INSTANCE, instance);
         } catch (RuntimeException | UnsupportedMessageException var4) {
            throw var4;
         } catch (Exception var5) {
            throw CompilerDirectives.shouldNotReachHere(var5);
         }
      }

      @Override
      public boolean hasMetaParents(Object receiver_) {
         try {
            return (Boolean)this.lib.send(receiver_, InteropLibraryGen.HAS_META_PARENTS);
         } catch (RuntimeException var3) {
            throw var3;
         } catch (Exception var4) {
            throw CompilerDirectives.shouldNotReachHere(var4);
         }
      }

      @Override
      public Object getMetaParents(Object receiver_) throws UnsupportedMessageException {
         try {
            return this.lib.send(receiver_, InteropLibraryGen.GET_META_PARENTS);
         } catch (RuntimeException | UnsupportedMessageException var3) {
            throw var3;
         } catch (Exception var4) {
            throw CompilerDirectives.shouldNotReachHere(var4);
         }
      }

      @Override
      protected TriState isIdenticalOrUndefined(Object receiver_, Object other) {
         try {
            return (TriState)this.lib.send(receiver_, InteropLibraryGen.IS_IDENTICAL_OR_UNDEFINED, other);
         } catch (RuntimeException var4) {
            throw var4;
         } catch (Exception var5) {
            throw CompilerDirectives.shouldNotReachHere(var5);
         }
      }

      @Override
      public boolean isIdentical(Object receiver_, Object other, InteropLibrary otherInterop) {
         try {
            return (Boolean)this.lib.send(receiver_, InteropLibraryGen.IS_IDENTICAL, other, otherInterop);
         } catch (RuntimeException var5) {
            throw var5;
         } catch (Exception var6) {
            throw CompilerDirectives.shouldNotReachHere(var6);
         }
      }

      @Override
      public int identityHashCode(Object receiver_) throws UnsupportedMessageException {
         try {
            return (Integer)this.lib.send(receiver_, InteropLibraryGen.IDENTITY_HASH_CODE);
         } catch (RuntimeException | UnsupportedMessageException var3) {
            throw var3;
         } catch (Exception var4) {
            throw CompilerDirectives.shouldNotReachHere(var4);
         }
      }

      @Override
      public boolean isScope(Object receiver_) {
         try {
            return (Boolean)this.lib.send(receiver_, InteropLibraryGen.IS_SCOPE);
         } catch (RuntimeException var3) {
            throw var3;
         } catch (Exception var4) {
            throw CompilerDirectives.shouldNotReachHere(var4);
         }
      }

      @Override
      public boolean hasScopeParent(Object receiver_) {
         try {
            return (Boolean)this.lib.send(receiver_, InteropLibraryGen.HAS_SCOPE_PARENT);
         } catch (RuntimeException var3) {
            throw var3;
         } catch (Exception var4) {
            throw CompilerDirectives.shouldNotReachHere(var4);
         }
      }

      @Override
      public Object getScopeParent(Object receiver_) throws UnsupportedMessageException {
         try {
            return this.lib.send(receiver_, InteropLibraryGen.GET_SCOPE_PARENT);
         } catch (RuntimeException | UnsupportedMessageException var3) {
            throw var3;
         } catch (Exception var4) {
            throw CompilerDirectives.shouldNotReachHere(var4);
         }
      }

      @Override
      public boolean accepts(Object receiver_) {
         return this.lib.accepts(receiver_);
      }
   }

   @GeneratedBy(InteropLibrary.class)
   @DenyReplace
   private static final class UncachedDispatch extends InteropLibrary {
      @Override
      public NodeCost getCost() {
         return NodeCost.MEGAMORPHIC;
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public boolean isNull(Object receiver_) {
         return InteropLibraryGen.INSTANCE.getUncached(receiver_).isNull(receiver_);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public boolean isBoolean(Object receiver_) {
         return InteropLibraryGen.INSTANCE.getUncached(receiver_).isBoolean(receiver_);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public boolean asBoolean(Object receiver_) throws UnsupportedMessageException {
         return InteropLibraryGen.INSTANCE.getUncached(receiver_).asBoolean(receiver_);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public boolean isExecutable(Object receiver_) {
         return InteropLibraryGen.INSTANCE.getUncached(receiver_).isExecutable(receiver_);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public Object execute(Object receiver_, Object... arguments) throws UnsupportedTypeException, ArityException, UnsupportedMessageException {
         return InteropLibraryGen.INSTANCE.getUncached(receiver_).execute(receiver_, arguments);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public boolean hasExecutableName(Object receiver_) {
         return InteropLibraryGen.INSTANCE.getUncached(receiver_).hasExecutableName(receiver_);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public Object getExecutableName(Object receiver_) throws UnsupportedMessageException {
         return InteropLibraryGen.INSTANCE.getUncached(receiver_).getExecutableName(receiver_);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public boolean hasDeclaringMetaObject(Object receiver_) {
         return InteropLibraryGen.INSTANCE.getUncached(receiver_).hasDeclaringMetaObject(receiver_);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public Object getDeclaringMetaObject(Object receiver_) throws UnsupportedMessageException {
         return InteropLibraryGen.INSTANCE.getUncached(receiver_).getDeclaringMetaObject(receiver_);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public boolean isInstantiable(Object receiver_) {
         return InteropLibraryGen.INSTANCE.getUncached(receiver_).isInstantiable(receiver_);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public Object instantiate(Object receiver_, Object... arguments) throws UnsupportedTypeException, ArityException, UnsupportedMessageException {
         return InteropLibraryGen.INSTANCE.getUncached(receiver_).instantiate(receiver_, arguments);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public boolean isString(Object receiver_) {
         return InteropLibraryGen.INSTANCE.getUncached(receiver_).isString(receiver_);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public String asString(Object receiver_) throws UnsupportedMessageException {
         return InteropLibraryGen.INSTANCE.getUncached(receiver_).asString(receiver_);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public TruffleString asTruffleString(Object receiver_) throws UnsupportedMessageException {
         return InteropLibraryGen.INSTANCE.getUncached(receiver_).asTruffleString(receiver_);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public boolean isNumber(Object receiver_) {
         return InteropLibraryGen.INSTANCE.getUncached(receiver_).isNumber(receiver_);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public boolean fitsInByte(Object receiver_) {
         return InteropLibraryGen.INSTANCE.getUncached(receiver_).fitsInByte(receiver_);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public boolean fitsInShort(Object receiver_) {
         return InteropLibraryGen.INSTANCE.getUncached(receiver_).fitsInShort(receiver_);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public boolean fitsInInt(Object receiver_) {
         return InteropLibraryGen.INSTANCE.getUncached(receiver_).fitsInInt(receiver_);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public boolean fitsInLong(Object receiver_) {
         return InteropLibraryGen.INSTANCE.getUncached(receiver_).fitsInLong(receiver_);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public boolean fitsInFloat(Object receiver_) {
         return InteropLibraryGen.INSTANCE.getUncached(receiver_).fitsInFloat(receiver_);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public boolean fitsInDouble(Object receiver_) {
         return InteropLibraryGen.INSTANCE.getUncached(receiver_).fitsInDouble(receiver_);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public byte asByte(Object receiver_) throws UnsupportedMessageException {
         return InteropLibraryGen.INSTANCE.getUncached(receiver_).asByte(receiver_);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public short asShort(Object receiver_) throws UnsupportedMessageException {
         return InteropLibraryGen.INSTANCE.getUncached(receiver_).asShort(receiver_);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public int asInt(Object receiver_) throws UnsupportedMessageException {
         return InteropLibraryGen.INSTANCE.getUncached(receiver_).asInt(receiver_);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public long asLong(Object receiver_) throws UnsupportedMessageException {
         return InteropLibraryGen.INSTANCE.getUncached(receiver_).asLong(receiver_);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public float asFloat(Object receiver_) throws UnsupportedMessageException {
         return InteropLibraryGen.INSTANCE.getUncached(receiver_).asFloat(receiver_);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public double asDouble(Object receiver_) throws UnsupportedMessageException {
         return InteropLibraryGen.INSTANCE.getUncached(receiver_).asDouble(receiver_);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public boolean hasMembers(Object receiver_) {
         return InteropLibraryGen.INSTANCE.getUncached(receiver_).hasMembers(receiver_);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public Object getMembers(Object receiver_, boolean includeInternal) throws UnsupportedMessageException {
         return InteropLibraryGen.INSTANCE.getUncached(receiver_).getMembers(receiver_, includeInternal);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public boolean isMemberReadable(Object receiver_, String member) {
         return InteropLibraryGen.INSTANCE.getUncached(receiver_).isMemberReadable(receiver_, member);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public Object readMember(Object receiver_, String member) throws UnsupportedMessageException, UnknownIdentifierException {
         return InteropLibraryGen.INSTANCE.getUncached(receiver_).readMember(receiver_, member);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public boolean isMemberModifiable(Object receiver_, String member) {
         return InteropLibraryGen.INSTANCE.getUncached(receiver_).isMemberModifiable(receiver_, member);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public boolean isMemberInsertable(Object receiver_, String member) {
         return InteropLibraryGen.INSTANCE.getUncached(receiver_).isMemberInsertable(receiver_, member);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public void writeMember(Object receiver_, String member, Object value) throws UnsupportedMessageException, UnknownIdentifierException, UnsupportedTypeException {
         InteropLibraryGen.INSTANCE.getUncached(receiver_).writeMember(receiver_, member, value);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public boolean isMemberRemovable(Object receiver_, String member) {
         return InteropLibraryGen.INSTANCE.getUncached(receiver_).isMemberRemovable(receiver_, member);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public void removeMember(Object receiver_, String member) throws UnsupportedMessageException, UnknownIdentifierException {
         InteropLibraryGen.INSTANCE.getUncached(receiver_).removeMember(receiver_, member);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public boolean isMemberInvocable(Object receiver_, String member) {
         return InteropLibraryGen.INSTANCE.getUncached(receiver_).isMemberInvocable(receiver_, member);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public Object invokeMember(Object receiver_, String member, Object... arguments) throws UnsupportedMessageException, ArityException, UnknownIdentifierException, UnsupportedTypeException {
         return InteropLibraryGen.INSTANCE.getUncached(receiver_).invokeMember(receiver_, member, arguments);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public boolean isMemberInternal(Object receiver_, String member) {
         return InteropLibraryGen.INSTANCE.getUncached(receiver_).isMemberInternal(receiver_, member);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public boolean hasMemberReadSideEffects(Object receiver_, String member) {
         return InteropLibraryGen.INSTANCE.getUncached(receiver_).hasMemberReadSideEffects(receiver_, member);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public boolean hasMemberWriteSideEffects(Object receiver_, String member) {
         return InteropLibraryGen.INSTANCE.getUncached(receiver_).hasMemberWriteSideEffects(receiver_, member);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public boolean hasHashEntries(Object receiver_) {
         return InteropLibraryGen.INSTANCE.getUncached(receiver_).hasHashEntries(receiver_);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public long getHashSize(Object receiver_) throws UnsupportedMessageException {
         return InteropLibraryGen.INSTANCE.getUncached(receiver_).getHashSize(receiver_);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public boolean isHashEntryReadable(Object receiver_, Object key) {
         return InteropLibraryGen.INSTANCE.getUncached(receiver_).isHashEntryReadable(receiver_, key);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public Object readHashValue(Object receiver_, Object key) throws UnsupportedMessageException, UnknownKeyException {
         return InteropLibraryGen.INSTANCE.getUncached(receiver_).readHashValue(receiver_, key);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public Object readHashValueOrDefault(Object receiver_, Object key, Object defaultValue) throws UnsupportedMessageException {
         return InteropLibraryGen.INSTANCE.getUncached(receiver_).readHashValueOrDefault(receiver_, key, defaultValue);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public boolean isHashEntryModifiable(Object receiver_, Object key) {
         return InteropLibraryGen.INSTANCE.getUncached(receiver_).isHashEntryModifiable(receiver_, key);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public boolean isHashEntryInsertable(Object receiver_, Object key) {
         return InteropLibraryGen.INSTANCE.getUncached(receiver_).isHashEntryInsertable(receiver_, key);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public boolean isHashEntryWritable(Object receiver_, Object key) {
         return InteropLibraryGen.INSTANCE.getUncached(receiver_).isHashEntryWritable(receiver_, key);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public void writeHashEntry(Object receiver_, Object key, Object value) throws UnsupportedMessageException, UnknownKeyException, UnsupportedTypeException {
         InteropLibraryGen.INSTANCE.getUncached(receiver_).writeHashEntry(receiver_, key, value);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public boolean isHashEntryRemovable(Object receiver_, Object key) {
         return InteropLibraryGen.INSTANCE.getUncached(receiver_).isHashEntryRemovable(receiver_, key);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public void removeHashEntry(Object receiver_, Object key) throws UnsupportedMessageException, UnknownKeyException {
         InteropLibraryGen.INSTANCE.getUncached(receiver_).removeHashEntry(receiver_, key);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public boolean isHashEntryExisting(Object receiver_, Object key) {
         return InteropLibraryGen.INSTANCE.getUncached(receiver_).isHashEntryExisting(receiver_, key);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public Object getHashEntriesIterator(Object receiver_) throws UnsupportedMessageException {
         return InteropLibraryGen.INSTANCE.getUncached(receiver_).getHashEntriesIterator(receiver_);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public Object getHashKeysIterator(Object receiver_) throws UnsupportedMessageException {
         return InteropLibraryGen.INSTANCE.getUncached(receiver_).getHashKeysIterator(receiver_);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public Object getHashValuesIterator(Object receiver_) throws UnsupportedMessageException {
         return InteropLibraryGen.INSTANCE.getUncached(receiver_).getHashValuesIterator(receiver_);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public boolean hasArrayElements(Object receiver_) {
         return InteropLibraryGen.INSTANCE.getUncached(receiver_).hasArrayElements(receiver_);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public Object readArrayElement(Object receiver_, long index) throws UnsupportedMessageException, InvalidArrayIndexException {
         return InteropLibraryGen.INSTANCE.getUncached(receiver_).readArrayElement(receiver_, index);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public long getArraySize(Object receiver_) throws UnsupportedMessageException {
         return InteropLibraryGen.INSTANCE.getUncached(receiver_).getArraySize(receiver_);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public boolean isArrayElementReadable(Object receiver_, long index) {
         return InteropLibraryGen.INSTANCE.getUncached(receiver_).isArrayElementReadable(receiver_, index);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public void writeArrayElement(Object receiver_, long index, Object value) throws UnsupportedMessageException, UnsupportedTypeException, InvalidArrayIndexException {
         InteropLibraryGen.INSTANCE.getUncached(receiver_).writeArrayElement(receiver_, index, value);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public void removeArrayElement(Object receiver_, long index) throws UnsupportedMessageException, InvalidArrayIndexException {
         InteropLibraryGen.INSTANCE.getUncached(receiver_).removeArrayElement(receiver_, index);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public boolean isArrayElementModifiable(Object receiver_, long index) {
         return InteropLibraryGen.INSTANCE.getUncached(receiver_).isArrayElementModifiable(receiver_, index);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public boolean isArrayElementInsertable(Object receiver_, long index) {
         return InteropLibraryGen.INSTANCE.getUncached(receiver_).isArrayElementInsertable(receiver_, index);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public boolean isArrayElementRemovable(Object receiver_, long index) {
         return InteropLibraryGen.INSTANCE.getUncached(receiver_).isArrayElementRemovable(receiver_, index);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public boolean hasBufferElements(Object receiver_) {
         return InteropLibraryGen.INSTANCE.getUncached(receiver_).hasBufferElements(receiver_);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public boolean isBufferWritable(Object receiver_) throws UnsupportedMessageException {
         return InteropLibraryGen.INSTANCE.getUncached(receiver_).isBufferWritable(receiver_);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public long getBufferSize(Object receiver_) throws UnsupportedMessageException {
         return InteropLibraryGen.INSTANCE.getUncached(receiver_).getBufferSize(receiver_);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public byte readBufferByte(Object receiver_, long byteOffset) throws UnsupportedMessageException, InvalidBufferOffsetException {
         return InteropLibraryGen.INSTANCE.getUncached(receiver_).readBufferByte(receiver_, byteOffset);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public void writeBufferByte(Object receiver_, long byteOffset, byte value) throws UnsupportedMessageException, InvalidBufferOffsetException {
         InteropLibraryGen.INSTANCE.getUncached(receiver_).writeBufferByte(receiver_, byteOffset, value);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public short readBufferShort(Object receiver_, ByteOrder order, long byteOffset) throws UnsupportedMessageException, InvalidBufferOffsetException {
         return InteropLibraryGen.INSTANCE.getUncached(receiver_).readBufferShort(receiver_, order, byteOffset);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public void writeBufferShort(Object receiver_, ByteOrder order, long byteOffset, short value) throws UnsupportedMessageException, InvalidBufferOffsetException {
         InteropLibraryGen.INSTANCE.getUncached(receiver_).writeBufferShort(receiver_, order, byteOffset, value);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public int readBufferInt(Object receiver_, ByteOrder order, long byteOffset) throws UnsupportedMessageException, InvalidBufferOffsetException {
         return InteropLibraryGen.INSTANCE.getUncached(receiver_).readBufferInt(receiver_, order, byteOffset);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public void writeBufferInt(Object receiver_, ByteOrder order, long byteOffset, int value) throws UnsupportedMessageException, InvalidBufferOffsetException {
         InteropLibraryGen.INSTANCE.getUncached(receiver_).writeBufferInt(receiver_, order, byteOffset, value);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public long readBufferLong(Object receiver_, ByteOrder order, long byteOffset) throws UnsupportedMessageException, InvalidBufferOffsetException {
         return InteropLibraryGen.INSTANCE.getUncached(receiver_).readBufferLong(receiver_, order, byteOffset);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public void writeBufferLong(Object receiver_, ByteOrder order, long byteOffset, long value) throws UnsupportedMessageException, InvalidBufferOffsetException {
         InteropLibraryGen.INSTANCE.getUncached(receiver_).writeBufferLong(receiver_, order, byteOffset, value);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public float readBufferFloat(Object receiver_, ByteOrder order, long byteOffset) throws UnsupportedMessageException, InvalidBufferOffsetException {
         return InteropLibraryGen.INSTANCE.getUncached(receiver_).readBufferFloat(receiver_, order, byteOffset);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public void writeBufferFloat(Object receiver_, ByteOrder order, long byteOffset, float value) throws UnsupportedMessageException, InvalidBufferOffsetException {
         InteropLibraryGen.INSTANCE.getUncached(receiver_).writeBufferFloat(receiver_, order, byteOffset, value);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public double readBufferDouble(Object receiver_, ByteOrder order, long byteOffset) throws UnsupportedMessageException, InvalidBufferOffsetException {
         return InteropLibraryGen.INSTANCE.getUncached(receiver_).readBufferDouble(receiver_, order, byteOffset);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public void writeBufferDouble(Object receiver_, ByteOrder order, long byteOffset, double value) throws UnsupportedMessageException, InvalidBufferOffsetException {
         InteropLibraryGen.INSTANCE.getUncached(receiver_).writeBufferDouble(receiver_, order, byteOffset, value);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public boolean isPointer(Object receiver_) {
         return InteropLibraryGen.INSTANCE.getUncached(receiver_).isPointer(receiver_);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public long asPointer(Object receiver_) throws UnsupportedMessageException {
         return InteropLibraryGen.INSTANCE.getUncached(receiver_).asPointer(receiver_);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public void toNative(Object receiver_) {
         InteropLibraryGen.INSTANCE.getUncached(receiver_).toNative(receiver_);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public Instant asInstant(Object receiver_) throws UnsupportedMessageException {
         return InteropLibraryGen.INSTANCE.getUncached(receiver_).asInstant(receiver_);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public boolean isTimeZone(Object receiver_) {
         return InteropLibraryGen.INSTANCE.getUncached(receiver_).isTimeZone(receiver_);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public ZoneId asTimeZone(Object receiver_) throws UnsupportedMessageException {
         return InteropLibraryGen.INSTANCE.getUncached(receiver_).asTimeZone(receiver_);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public boolean isDate(Object receiver_) {
         return InteropLibraryGen.INSTANCE.getUncached(receiver_).isDate(receiver_);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public LocalDate asDate(Object receiver_) throws UnsupportedMessageException {
         return InteropLibraryGen.INSTANCE.getUncached(receiver_).asDate(receiver_);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public boolean isTime(Object receiver_) {
         return InteropLibraryGen.INSTANCE.getUncached(receiver_).isTime(receiver_);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public LocalTime asTime(Object receiver_) throws UnsupportedMessageException {
         return InteropLibraryGen.INSTANCE.getUncached(receiver_).asTime(receiver_);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public boolean isDuration(Object receiver_) {
         return InteropLibraryGen.INSTANCE.getUncached(receiver_).isDuration(receiver_);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public Duration asDuration(Object receiver_) throws UnsupportedMessageException {
         return InteropLibraryGen.INSTANCE.getUncached(receiver_).asDuration(receiver_);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public boolean isException(Object receiver_) {
         return InteropLibraryGen.INSTANCE.getUncached(receiver_).isException(receiver_);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public RuntimeException throwException(Object receiver_) throws UnsupportedMessageException {
         return InteropLibraryGen.INSTANCE.getUncached(receiver_).throwException(receiver_);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public ExceptionType getExceptionType(Object receiver_) throws UnsupportedMessageException {
         return InteropLibraryGen.INSTANCE.getUncached(receiver_).getExceptionType(receiver_);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public boolean isExceptionIncompleteSource(Object receiver_) throws UnsupportedMessageException {
         return InteropLibraryGen.INSTANCE.getUncached(receiver_).isExceptionIncompleteSource(receiver_);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public int getExceptionExitStatus(Object receiver_) throws UnsupportedMessageException {
         return InteropLibraryGen.INSTANCE.getUncached(receiver_).getExceptionExitStatus(receiver_);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public boolean hasExceptionCause(Object receiver_) {
         return InteropLibraryGen.INSTANCE.getUncached(receiver_).hasExceptionCause(receiver_);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public Object getExceptionCause(Object receiver_) throws UnsupportedMessageException {
         return InteropLibraryGen.INSTANCE.getUncached(receiver_).getExceptionCause(receiver_);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public boolean hasExceptionMessage(Object receiver_) {
         return InteropLibraryGen.INSTANCE.getUncached(receiver_).hasExceptionMessage(receiver_);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public Object getExceptionMessage(Object receiver_) throws UnsupportedMessageException {
         return InteropLibraryGen.INSTANCE.getUncached(receiver_).getExceptionMessage(receiver_);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public boolean hasExceptionStackTrace(Object receiver_) {
         return InteropLibraryGen.INSTANCE.getUncached(receiver_).hasExceptionStackTrace(receiver_);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public Object getExceptionStackTrace(Object receiver_) throws UnsupportedMessageException {
         return InteropLibraryGen.INSTANCE.getUncached(receiver_).getExceptionStackTrace(receiver_);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public boolean hasIterator(Object receiver_) {
         return InteropLibraryGen.INSTANCE.getUncached(receiver_).hasIterator(receiver_);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public Object getIterator(Object receiver_) throws UnsupportedMessageException {
         return InteropLibraryGen.INSTANCE.getUncached(receiver_).getIterator(receiver_);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public boolean isIterator(Object receiver_) {
         return InteropLibraryGen.INSTANCE.getUncached(receiver_).isIterator(receiver_);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public boolean hasIteratorNextElement(Object receiver_) throws UnsupportedMessageException {
         return InteropLibraryGen.INSTANCE.getUncached(receiver_).hasIteratorNextElement(receiver_);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public Object getIteratorNextElement(Object receiver_) throws UnsupportedMessageException, StopIterationException {
         return InteropLibraryGen.INSTANCE.getUncached(receiver_).getIteratorNextElement(receiver_);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public boolean hasSourceLocation(Object receiver_) {
         return InteropLibraryGen.INSTANCE.getUncached(receiver_).hasSourceLocation(receiver_);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public SourceSection getSourceLocation(Object receiver_) throws UnsupportedMessageException {
         return InteropLibraryGen.INSTANCE.getUncached(receiver_).getSourceLocation(receiver_);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public boolean hasLanguage(Object receiver_) {
         return InteropLibraryGen.INSTANCE.getUncached(receiver_).hasLanguage(receiver_);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public Class<? extends TruffleLanguage<?>> getLanguage(Object receiver_) throws UnsupportedMessageException {
         return InteropLibraryGen.INSTANCE.getUncached(receiver_).getLanguage(receiver_);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public boolean hasMetaObject(Object receiver_) {
         return InteropLibraryGen.INSTANCE.getUncached(receiver_).hasMetaObject(receiver_);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public Object getMetaObject(Object receiver_) throws UnsupportedMessageException {
         return InteropLibraryGen.INSTANCE.getUncached(receiver_).getMetaObject(receiver_);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public Object toDisplayString(Object receiver_, boolean allowSideEffects) {
         return InteropLibraryGen.INSTANCE.getUncached(receiver_).toDisplayString(receiver_, allowSideEffects);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public boolean isMetaObject(Object receiver_) {
         return InteropLibraryGen.INSTANCE.getUncached(receiver_).isMetaObject(receiver_);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public Object getMetaQualifiedName(Object receiver_) throws UnsupportedMessageException {
         return InteropLibraryGen.INSTANCE.getUncached(receiver_).getMetaQualifiedName(receiver_);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public Object getMetaSimpleName(Object receiver_) throws UnsupportedMessageException {
         return InteropLibraryGen.INSTANCE.getUncached(receiver_).getMetaSimpleName(receiver_);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public boolean isMetaInstance(Object receiver_, Object instance) throws UnsupportedMessageException {
         return InteropLibraryGen.INSTANCE.getUncached(receiver_).isMetaInstance(receiver_, instance);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public boolean hasMetaParents(Object receiver_) {
         return InteropLibraryGen.INSTANCE.getUncached(receiver_).hasMetaParents(receiver_);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public Object getMetaParents(Object receiver_) throws UnsupportedMessageException {
         return InteropLibraryGen.INSTANCE.getUncached(receiver_).getMetaParents(receiver_);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      protected TriState isIdenticalOrUndefined(Object receiver_, Object other) {
         return InteropLibraryGen.INSTANCE.getUncached(receiver_).isIdenticalOrUndefined(receiver_, other);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public boolean isIdentical(Object receiver_, Object other, InteropLibrary otherInterop) {
         return InteropLibraryGen.INSTANCE.getUncached(receiver_).isIdentical(receiver_, other, otherInterop);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public int identityHashCode(Object receiver_) throws UnsupportedMessageException {
         return InteropLibraryGen.INSTANCE.getUncached(receiver_).identityHashCode(receiver_);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public boolean isScope(Object receiver_) {
         return InteropLibraryGen.INSTANCE.getUncached(receiver_).isScope(receiver_);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public boolean hasScopeParent(Object receiver_) {
         return InteropLibraryGen.INSTANCE.getUncached(receiver_).hasScopeParent(receiver_);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public Object getScopeParent(Object receiver_) throws UnsupportedMessageException {
         return InteropLibraryGen.INSTANCE.getUncached(receiver_).getScopeParent(receiver_);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public boolean accepts(Object receiver_) {
         return true;
      }

      @Override
      public boolean isAdoptable() {
         return false;
      }
   }
}
