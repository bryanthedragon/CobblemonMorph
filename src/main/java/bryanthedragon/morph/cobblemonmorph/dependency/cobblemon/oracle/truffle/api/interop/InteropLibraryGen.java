
package com.oracle.truffle.api.interop;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.interop.ArityException;
import com.oracle.truffle.api.interop.DefaultBooleanExports;
import com.oracle.truffle.api.interop.DefaultByteExports;
import com.oracle.truffle.api.interop.DefaultCharacterExports;
import com.oracle.truffle.api.interop.DefaultDoubleExports;
import com.oracle.truffle.api.interop.DefaultFloatExports;
import com.oracle.truffle.api.interop.DefaultIntegerExports;
import com.oracle.truffle.api.interop.DefaultLongExports;
import com.oracle.truffle.api.interop.DefaultShortExports;
import com.oracle.truffle.api.interop.DefaultStringExports;
import com.oracle.truffle.api.interop.DefaultTStringExports;
import com.oracle.truffle.api.interop.ExceptionType;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.InvalidArrayIndexException;
import com.oracle.truffle.api.interop.InvalidBufferOffsetException;
import com.oracle.truffle.api.interop.StopIterationException;
import com.oracle.truffle.api.interop.UnknownIdentifierException;
import com.oracle.truffle.api.interop.UnknownKeyException;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.interop.UnsupportedTypeException;
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

@GeneratedBy(value=InteropLibrary.class)
final class InteropLibraryGen
extends LibraryFactory<InteropLibrary> {
    private static final Class<InteropLibrary> LIBRARY_CLASS = InteropLibraryGen.lazyLibraryClass();
    private static final Message IS_NULL = new MessageImpl("isNull", 0, Boolean.TYPE, Object.class);
    private static final Message IS_BOOLEAN = new MessageImpl("isBoolean", 1, Boolean.TYPE, Object.class);
    private static final Message AS_BOOLEAN = new MessageImpl("asBoolean", 2, Boolean.TYPE, Object.class);
    private static final Message IS_EXECUTABLE = new MessageImpl("isExecutable", 3, Boolean.TYPE, Object.class);
    private static final Message EXECUTE = new MessageImpl("execute", 4, Object.class, Object.class, Object[].class);
    private static final Message HAS_EXECUTABLE_NAME = new MessageImpl("hasExecutableName", 5, Boolean.TYPE, Object.class);
    private static final Message GET_EXECUTABLE_NAME = new MessageImpl("getExecutableName", 6, Object.class, Object.class);
    private static final Message HAS_DECLARING_META_OBJECT = new MessageImpl("hasDeclaringMetaObject", 7, Boolean.TYPE, Object.class);
    private static final Message GET_DECLARING_META_OBJECT = new MessageImpl("getDeclaringMetaObject", 8, Object.class, Object.class);
    private static final Message IS_INSTANTIABLE = new MessageImpl("isInstantiable", 9, Boolean.TYPE, Object.class);
    private static final Message INSTANTIATE = new MessageImpl("instantiate", 10, Object.class, Object.class, Object[].class);
    private static final Message IS_STRING = new MessageImpl("isString", 11, Boolean.TYPE, Object.class);
    private static final Message AS_STRING = new MessageImpl("asString", 12, String.class, Object.class);
    private static final Message AS_TRUFFLE_STRING = new MessageImpl("asTruffleString", 13, TruffleString.class, Object.class);
    private static final Message IS_NUMBER = new MessageImpl("isNumber", 14, Boolean.TYPE, Object.class);
    private static final Message FITS_IN_BYTE = new MessageImpl("fitsInByte", 15, Boolean.TYPE, Object.class);
    private static final Message FITS_IN_SHORT = new MessageImpl("fitsInShort", 16, Boolean.TYPE, Object.class);
    private static final Message FITS_IN_INT = new MessageImpl("fitsInInt", 17, Boolean.TYPE, Object.class);
    private static final Message FITS_IN_LONG = new MessageImpl("fitsInLong", 18, Boolean.TYPE, Object.class);
    private static final Message FITS_IN_FLOAT = new MessageImpl("fitsInFloat", 19, Boolean.TYPE, Object.class);
    private static final Message FITS_IN_DOUBLE = new MessageImpl("fitsInDouble", 20, Boolean.TYPE, Object.class);
    private static final Message AS_BYTE = new MessageImpl("asByte", 21, Byte.TYPE, Object.class);
    private static final Message AS_SHORT = new MessageImpl("asShort", 22, Short.TYPE, Object.class);
    private static final Message AS_INT = new MessageImpl("asInt", 23, Integer.TYPE, Object.class);
    private static final Message AS_LONG = new MessageImpl("asLong", 24, Long.TYPE, Object.class);
    private static final Message AS_FLOAT = new MessageImpl("asFloat", 25, Float.TYPE, Object.class);
    private static final Message AS_DOUBLE = new MessageImpl("asDouble", 26, Double.TYPE, Object.class);
    private static final Message HAS_MEMBERS = new MessageImpl("hasMembers", 27, Boolean.TYPE, Object.class);
    private static final Message GET_MEMBERS = new MessageImpl("getMembers", 28, Object.class, Object.class, Boolean.TYPE);
    private static final Message IS_MEMBER_READABLE = new MessageImpl("isMemberReadable", 29, Boolean.TYPE, Object.class, String.class);
    private static final Message READ_MEMBER = new MessageImpl("readMember", 30, Object.class, Object.class, String.class);
    private static final Message IS_MEMBER_MODIFIABLE = new MessageImpl("isMemberModifiable", 31, Boolean.TYPE, Object.class, String.class);
    private static final Message IS_MEMBER_INSERTABLE = new MessageImpl("isMemberInsertable", 32, Boolean.TYPE, Object.class, String.class);
    private static final Message WRITE_MEMBER = new MessageImpl("writeMember", 33, Void.TYPE, Object.class, String.class, Object.class);
    private static final Message IS_MEMBER_REMOVABLE = new MessageImpl("isMemberRemovable", 34, Boolean.TYPE, Object.class, String.class);
    private static final Message REMOVE_MEMBER = new MessageImpl("removeMember", 35, Void.TYPE, Object.class, String.class);
    private static final Message IS_MEMBER_INVOCABLE = new MessageImpl("isMemberInvocable", 36, Boolean.TYPE, Object.class, String.class);
    private static final Message INVOKE_MEMBER = new MessageImpl("invokeMember", 37, Object.class, Object.class, String.class, Object[].class);
    private static final Message IS_MEMBER_INTERNAL = new MessageImpl("isMemberInternal", 38, Boolean.TYPE, Object.class, String.class);
    private static final Message HAS_MEMBER_READ_SIDE_EFFECTS = new MessageImpl("hasMemberReadSideEffects", 39, Boolean.TYPE, Object.class, String.class);
    private static final Message HAS_MEMBER_WRITE_SIDE_EFFECTS = new MessageImpl("hasMemberWriteSideEffects", 40, Boolean.TYPE, Object.class, String.class);
    private static final Message HAS_HASH_ENTRIES = new MessageImpl("hasHashEntries", 41, Boolean.TYPE, Object.class);
    private static final Message GET_HASH_SIZE = new MessageImpl("getHashSize", 42, Long.TYPE, Object.class);
    private static final Message IS_HASH_ENTRY_READABLE = new MessageImpl("isHashEntryReadable", 43, Boolean.TYPE, Object.class, Object.class);
    private static final Message READ_HASH_VALUE = new MessageImpl("readHashValue", 44, Object.class, Object.class, Object.class);
    private static final Message READ_HASH_VALUE_OR_DEFAULT = new MessageImpl("readHashValueOrDefault", 45, Object.class, Object.class, Object.class, Object.class);
    private static final Message IS_HASH_ENTRY_MODIFIABLE = new MessageImpl("isHashEntryModifiable", 46, Boolean.TYPE, Object.class, Object.class);
    private static final Message IS_HASH_ENTRY_INSERTABLE = new MessageImpl("isHashEntryInsertable", 47, Boolean.TYPE, Object.class, Object.class);
    private static final Message IS_HASH_ENTRY_WRITABLE = new MessageImpl("isHashEntryWritable", 48, Boolean.TYPE, Object.class, Object.class);
    private static final Message WRITE_HASH_ENTRY = new MessageImpl("writeHashEntry", 49, Void.TYPE, Object.class, Object.class, Object.class);
    private static final Message IS_HASH_ENTRY_REMOVABLE = new MessageImpl("isHashEntryRemovable", 50, Boolean.TYPE, Object.class, Object.class);
    private static final Message REMOVE_HASH_ENTRY = new MessageImpl("removeHashEntry", 51, Void.TYPE, Object.class, Object.class);
    private static final Message IS_HASH_ENTRY_EXISTING = new MessageImpl("isHashEntryExisting", 52, Boolean.TYPE, Object.class, Object.class);
    private static final Message GET_HASH_ENTRIES_ITERATOR = new MessageImpl("getHashEntriesIterator", 53, Object.class, Object.class);
    private static final Message GET_HASH_KEYS_ITERATOR = new MessageImpl("getHashKeysIterator", 54, Object.class, Object.class);
    private static final Message GET_HASH_VALUES_ITERATOR = new MessageImpl("getHashValuesIterator", 55, Object.class, Object.class);
    private static final Message HAS_ARRAY_ELEMENTS = new MessageImpl("hasArrayElements", 56, Boolean.TYPE, Object.class);
    private static final Message READ_ARRAY_ELEMENT = new MessageImpl("readArrayElement", 57, Object.class, Object.class, Long.TYPE);
    private static final Message GET_ARRAY_SIZE = new MessageImpl("getArraySize", 58, Long.TYPE, Object.class);
    private static final Message IS_ARRAY_ELEMENT_READABLE = new MessageImpl("isArrayElementReadable", 59, Boolean.TYPE, Object.class, Long.TYPE);
    private static final Message WRITE_ARRAY_ELEMENT = new MessageImpl("writeArrayElement", 60, Void.TYPE, Object.class, Long.TYPE, Object.class);
    private static final Message REMOVE_ARRAY_ELEMENT = new MessageImpl("removeArrayElement", 61, Void.TYPE, Object.class, Long.TYPE);
    private static final Message IS_ARRAY_ELEMENT_MODIFIABLE = new MessageImpl("isArrayElementModifiable", 62, Boolean.TYPE, Object.class, Long.TYPE);
    private static final Message IS_ARRAY_ELEMENT_INSERTABLE = new MessageImpl("isArrayElementInsertable", 63, Boolean.TYPE, Object.class, Long.TYPE);
    private static final Message IS_ARRAY_ELEMENT_REMOVABLE = new MessageImpl("isArrayElementRemovable", 64, Boolean.TYPE, Object.class, Long.TYPE);
    private static final Message HAS_BUFFER_ELEMENTS = new MessageImpl("hasBufferElements", 65, Boolean.TYPE, Object.class);
    private static final Message IS_BUFFER_WRITABLE = new MessageImpl("isBufferWritable", 66, Boolean.TYPE, Object.class);
    private static final Message GET_BUFFER_SIZE = new MessageImpl("getBufferSize", 67, Long.TYPE, Object.class);
    private static final Message READ_BUFFER_BYTE = new MessageImpl("readBufferByte", 68, Byte.TYPE, Object.class, Long.TYPE);
    private static final Message WRITE_BUFFER_BYTE = new MessageImpl("writeBufferByte", 69, Void.TYPE, Object.class, Long.TYPE, Byte.TYPE);
    private static final Message READ_BUFFER_SHORT = new MessageImpl("readBufferShort", 70, Short.TYPE, Object.class, ByteOrder.class, Long.TYPE);
    private static final Message WRITE_BUFFER_SHORT = new MessageImpl("writeBufferShort", 71, Void.TYPE, Object.class, ByteOrder.class, Long.TYPE, Short.TYPE);
    private static final Message READ_BUFFER_INT = new MessageImpl("readBufferInt", 72, Integer.TYPE, Object.class, ByteOrder.class, Long.TYPE);
    private static final Message WRITE_BUFFER_INT = new MessageImpl("writeBufferInt", 73, Void.TYPE, Object.class, ByteOrder.class, Long.TYPE, Integer.TYPE);
    private static final Message READ_BUFFER_LONG = new MessageImpl("readBufferLong", 74, Long.TYPE, Object.class, ByteOrder.class, Long.TYPE);
    private static final Message WRITE_BUFFER_LONG = new MessageImpl("writeBufferLong", 75, Void.TYPE, Object.class, ByteOrder.class, Long.TYPE, Long.TYPE);
    private static final Message READ_BUFFER_FLOAT = new MessageImpl("readBufferFloat", 76, Float.TYPE, Object.class, ByteOrder.class, Long.TYPE);
    private static final Message WRITE_BUFFER_FLOAT = new MessageImpl("writeBufferFloat", 77, Void.TYPE, Object.class, ByteOrder.class, Long.TYPE, Float.TYPE);
    private static final Message READ_BUFFER_DOUBLE = new MessageImpl("readBufferDouble", 78, Double.TYPE, Object.class, ByteOrder.class, Long.TYPE);
    private static final Message WRITE_BUFFER_DOUBLE = new MessageImpl("writeBufferDouble", 79, Void.TYPE, Object.class, ByteOrder.class, Long.TYPE, Double.TYPE);
    private static final Message IS_POINTER = new MessageImpl("isPointer", 80, Boolean.TYPE, Object.class);
    private static final Message AS_POINTER = new MessageImpl("asPointer", 81, Long.TYPE, Object.class);
    private static final Message TO_NATIVE = new MessageImpl("toNative", 82, Void.TYPE, Object.class);
    private static final Message AS_INSTANT = new MessageImpl("asInstant", 83, Instant.class, Object.class);
    private static final Message IS_TIME_ZONE = new MessageImpl("isTimeZone", 84, Boolean.TYPE, Object.class);
    private static final Message AS_TIME_ZONE = new MessageImpl("asTimeZone", 85, ZoneId.class, Object.class);
    private static final Message IS_DATE = new MessageImpl("isDate", 86, Boolean.TYPE, Object.class);
    private static final Message AS_DATE = new MessageImpl("asDate", 87, LocalDate.class, Object.class);
    private static final Message IS_TIME = new MessageImpl("isTime", 88, Boolean.TYPE, Object.class);
    private static final Message AS_TIME = new MessageImpl("asTime", 89, LocalTime.class, Object.class);
    private static final Message IS_DURATION = new MessageImpl("isDuration", 90, Boolean.TYPE, Object.class);
    private static final Message AS_DURATION = new MessageImpl("asDuration", 91, Duration.class, Object.class);
    private static final Message IS_EXCEPTION = new MessageImpl("isException", 92, Boolean.TYPE, Object.class);
    private static final Message THROW_EXCEPTION = new MessageImpl("throwException", 93, RuntimeException.class, Object.class);
    private static final Message GET_EXCEPTION_TYPE = new MessageImpl("getExceptionType", 94, ExceptionType.class, Object.class);
    private static final Message IS_EXCEPTION_INCOMPLETE_SOURCE = new MessageImpl("isExceptionIncompleteSource", 95, Boolean.TYPE, Object.class);
    private static final Message GET_EXCEPTION_EXIT_STATUS = new MessageImpl("getExceptionExitStatus", 96, Integer.TYPE, Object.class);
    private static final Message HAS_EXCEPTION_CAUSE = new MessageImpl("hasExceptionCause", 97, Boolean.TYPE, Object.class);
    private static final Message GET_EXCEPTION_CAUSE = new MessageImpl("getExceptionCause", 98, Object.class, Object.class);
    private static final Message HAS_EXCEPTION_MESSAGE = new MessageImpl("hasExceptionMessage", 99, Boolean.TYPE, Object.class);
    private static final Message GET_EXCEPTION_MESSAGE = new MessageImpl("getExceptionMessage", 100, Object.class, Object.class);
    private static final Message HAS_EXCEPTION_STACK_TRACE = new MessageImpl("hasExceptionStackTrace", 101, Boolean.TYPE, Object.class);
    private static final Message GET_EXCEPTION_STACK_TRACE = new MessageImpl("getExceptionStackTrace", 102, Object.class, Object.class);
    private static final Message HAS_ITERATOR = new MessageImpl("hasIterator", 103, Boolean.TYPE, Object.class);
    private static final Message GET_ITERATOR = new MessageImpl("getIterator", 104, Object.class, Object.class);
    private static final Message IS_ITERATOR = new MessageImpl("isIterator", 105, Boolean.TYPE, Object.class);
    private static final Message HAS_ITERATOR_NEXT_ELEMENT = new MessageImpl("hasIteratorNextElement", 106, Boolean.TYPE, Object.class);
    private static final Message GET_ITERATOR_NEXT_ELEMENT = new MessageImpl("getIteratorNextElement", 107, Object.class, Object.class);
    private static final Message HAS_SOURCE_LOCATION = new MessageImpl("hasSourceLocation", 108, Boolean.TYPE, Object.class);
    private static final Message GET_SOURCE_LOCATION = new MessageImpl("getSourceLocation", 109, SourceSection.class, Object.class);
    private static final Message HAS_LANGUAGE = new MessageImpl("hasLanguage", 110, Boolean.TYPE, Object.class);
    private static final Message GET_LANGUAGE = new MessageImpl("getLanguage", 111, Class.class, Object.class);
    private static final Message HAS_META_OBJECT = new MessageImpl("hasMetaObject", 112, Boolean.TYPE, Object.class);
    private static final Message GET_META_OBJECT = new MessageImpl("getMetaObject", 113, Object.class, Object.class);
    private static final Message TO_DISPLAY_STRING = new MessageImpl("toDisplayString", 114, Object.class, Object.class, Boolean.TYPE);
    private static final Message IS_META_OBJECT = new MessageImpl("isMetaObject", 115, Boolean.TYPE, Object.class);
    private static final Message GET_META_QUALIFIED_NAME = new MessageImpl("getMetaQualifiedName", 116, Object.class, Object.class);
    private static final Message GET_META_SIMPLE_NAME = new MessageImpl("getMetaSimpleName", 117, Object.class, Object.class);
    private static final Message IS_META_INSTANCE = new MessageImpl("isMetaInstance", 118, Boolean.TYPE, Object.class, Object.class);
    private static final Message HAS_META_PARENTS = new MessageImpl("hasMetaParents", 119, Boolean.TYPE, Object.class);
    private static final Message GET_META_PARENTS = new MessageImpl("getMetaParents", 120, Object.class, Object.class);
    private static final Message IS_IDENTICAL_OR_UNDEFINED = new MessageImpl("isIdenticalOrUndefined", 121, TriState.class, Object.class, Object.class);
    private static final Message IS_IDENTICAL = new MessageImpl("isIdentical", 122, Boolean.TYPE, Object.class, Object.class, InteropLibrary.class);
    private static final Message IDENTITY_HASH_CODE = new MessageImpl("identityHashCode", 123, Integer.TYPE, Object.class);
    private static final Message IS_SCOPE = new MessageImpl("isScope", 124, Boolean.TYPE, Object.class);
    private static final Message HAS_SCOPE_PARENT = new MessageImpl("hasScopeParent", 125, Boolean.TYPE, Object.class);
    private static final Message GET_SCOPE_PARENT = new MessageImpl("getScopeParent", 126, Object.class, Object.class);
    private static final InteropLibraryGen INSTANCE = new InteropLibraryGen();
    private static final LibraryFactory<DynamicDispatchLibrary> DYNAMIC_DISPATCH_LIBRARY_ = LibraryFactory.resolve(DynamicDispatchLibrary.class);

    private InteropLibraryGen() {
        super(LIBRARY_CLASS, Collections.unmodifiableList(Arrays.asList(IS_NULL, IS_BOOLEAN, AS_BOOLEAN, IS_EXECUTABLE, EXECUTE, HAS_EXECUTABLE_NAME, GET_EXECUTABLE_NAME, HAS_DECLARING_META_OBJECT, GET_DECLARING_META_OBJECT, IS_INSTANTIABLE, INSTANTIATE, IS_STRING, AS_STRING, AS_TRUFFLE_STRING, IS_NUMBER, FITS_IN_BYTE, FITS_IN_SHORT, FITS_IN_INT, FITS_IN_LONG, FITS_IN_FLOAT, FITS_IN_DOUBLE, AS_BYTE, AS_SHORT, AS_INT, AS_LONG, AS_FLOAT, AS_DOUBLE, HAS_MEMBERS, GET_MEMBERS, IS_MEMBER_READABLE, READ_MEMBER, IS_MEMBER_MODIFIABLE, IS_MEMBER_INSERTABLE, WRITE_MEMBER, IS_MEMBER_REMOVABLE, REMOVE_MEMBER, IS_MEMBER_INVOCABLE, INVOKE_MEMBER, IS_MEMBER_INTERNAL, HAS_MEMBER_READ_SIDE_EFFECTS, HAS_MEMBER_WRITE_SIDE_EFFECTS, HAS_HASH_ENTRIES, GET_HASH_SIZE, IS_HASH_ENTRY_READABLE, READ_HASH_VALUE, READ_HASH_VALUE_OR_DEFAULT, IS_HASH_ENTRY_MODIFIABLE, IS_HASH_ENTRY_INSERTABLE, IS_HASH_ENTRY_WRITABLE, WRITE_HASH_ENTRY, IS_HASH_ENTRY_REMOVABLE, REMOVE_HASH_ENTRY, IS_HASH_ENTRY_EXISTING, GET_HASH_ENTRIES_ITERATOR, GET_HASH_KEYS_ITERATOR, GET_HASH_VALUES_ITERATOR, HAS_ARRAY_ELEMENTS, READ_ARRAY_ELEMENT, GET_ARRAY_SIZE, IS_ARRAY_ELEMENT_READABLE, WRITE_ARRAY_ELEMENT, REMOVE_ARRAY_ELEMENT, IS_ARRAY_ELEMENT_MODIFIABLE, IS_ARRAY_ELEMENT_INSERTABLE, IS_ARRAY_ELEMENT_REMOVABLE, HAS_BUFFER_ELEMENTS, IS_BUFFER_WRITABLE, GET_BUFFER_SIZE, READ_BUFFER_BYTE, WRITE_BUFFER_BYTE, READ_BUFFER_SHORT, WRITE_BUFFER_SHORT, READ_BUFFER_INT, WRITE_BUFFER_INT, READ_BUFFER_LONG, WRITE_BUFFER_LONG, READ_BUFFER_FLOAT, WRITE_BUFFER_FLOAT, READ_BUFFER_DOUBLE, WRITE_BUFFER_DOUBLE, IS_POINTER, AS_POINTER, TO_NATIVE, AS_INSTANT, IS_TIME_ZONE, AS_TIME_ZONE, IS_DATE, AS_DATE, IS_TIME, AS_TIME, IS_DURATION, AS_DURATION, IS_EXCEPTION, THROW_EXCEPTION, GET_EXCEPTION_TYPE, IS_EXCEPTION_INCOMPLETE_SOURCE, GET_EXCEPTION_EXIT_STATUS, HAS_EXCEPTION_CAUSE, GET_EXCEPTION_CAUSE, HAS_EXCEPTION_MESSAGE, GET_EXCEPTION_MESSAGE, HAS_EXCEPTION_STACK_TRACE, GET_EXCEPTION_STACK_TRACE, HAS_ITERATOR, GET_ITERATOR, IS_ITERATOR, HAS_ITERATOR_NEXT_ELEMENT, GET_ITERATOR_NEXT_ELEMENT, HAS_SOURCE_LOCATION, GET_SOURCE_LOCATION, HAS_LANGUAGE, GET_LANGUAGE, HAS_META_OBJECT, GET_META_OBJECT, TO_DISPLAY_STRING, IS_META_OBJECT, GET_META_QUALIFIED_NAME, GET_META_SIMPLE_NAME, IS_META_INSTANCE, HAS_META_PARENTS, GET_META_PARENTS, IS_IDENTICAL_OR_UNDEFINED, IS_IDENTICAL, IDENTITY_HASH_CODE, IS_SCOPE, HAS_SCOPE_PARENT, GET_SCOPE_PARENT)));
    }

    @Override
    protected Class<?> getDefaultClass(Object receiver) {
        if (receiver instanceof Boolean) {
            return DefaultBooleanExports.class;
        }
        if (receiver instanceof Integer) {
            return DefaultIntegerExports.class;
        }
        if (receiver instanceof Byte) {
            return DefaultByteExports.class;
        }
        if (receiver instanceof Short) {
            return DefaultShortExports.class;
        }
        if (receiver instanceof Long) {
            return DefaultLongExports.class;
        }
        if (receiver instanceof Float) {
            return DefaultFloatExports.class;
        }
        if (receiver instanceof Double) {
            return DefaultDoubleExports.class;
        }
        if (receiver instanceof Character) {
            return DefaultCharacterExports.class;
        }
        if (receiver instanceof String) {
            return DefaultStringExports.class;
        }
        if (receiver instanceof TruffleString) {
            return DefaultTStringExports.class;
        }
        return InteropLibrary.class;
    }

    @Override
    protected InteropLibrary createAssertions(InteropLibrary delegate) {
        return new InteropLibrary.Asserts(delegate);
    }

    @Override
    protected InteropLibrary createProxy(ReflectionLibrary library) {
        return new Proxy(library);
    }

    @Override
    protected FinalBitSet createMessageBitSet(Message ... messages) {
        BitSet bitSet = new BitSet(2);
        for (Message message : messages) {
            bitSet.set(message.getId());
        }
        return FinalBitSet.valueOf(bitSet);
    }

    @Override
    protected InteropLibrary createDelegate(InteropLibrary delegateLibrary) {
        return new Delegate(delegateLibrary);
    }

    @Override
    protected Object genericDispatch(Library originalLib, Object receiver, Message message, Object[] args, int offset) throws Exception {
        InteropLibrary lib = (InteropLibrary)originalLib;
        if (message.getParameterCount() - 1 != args.length - offset) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            throw new IllegalArgumentException("Invalid number of arguments.");
        }
        switch (message.getId()) {
            case 0: {
                return lib.isNull(receiver);
            }
            case 1: {
                return lib.isBoolean(receiver);
            }
            case 2: {
                return lib.asBoolean(receiver);
            }
            case 3: {
                return lib.isExecutable(receiver);
            }
            case 4: {
                return lib.execute(receiver, (Object[])args[offset]);
            }
            case 5: {
                return lib.hasExecutableName(receiver);
            }
            case 6: {
                return lib.getExecutableName(receiver);
            }
            case 7: {
                return lib.hasDeclaringMetaObject(receiver);
            }
            case 8: {
                return lib.getDeclaringMetaObject(receiver);
            }
            case 9: {
                return lib.isInstantiable(receiver);
            }
            case 10: {
                return lib.instantiate(receiver, (Object[])args[offset]);
            }
            case 11: {
                return lib.isString(receiver);
            }
            case 12: {
                return lib.asString(receiver);
            }
            case 13: {
                return lib.asTruffleString(receiver);
            }
            case 14: {
                return lib.isNumber(receiver);
            }
            case 15: {
                return lib.fitsInByte(receiver);
            }
            case 16: {
                return lib.fitsInShort(receiver);
            }
            case 17: {
                return lib.fitsInInt(receiver);
            }
            case 18: {
                return lib.fitsInLong(receiver);
            }
            case 19: {
                return lib.fitsInFloat(receiver);
            }
            case 20: {
                return lib.fitsInDouble(receiver);
            }
            case 21: {
                return lib.asByte(receiver);
            }
            case 22: {
                return lib.asShort(receiver);
            }
            case 23: {
                return lib.asInt(receiver);
            }
            case 24: {
                return lib.asLong(receiver);
            }
            case 25: {
                return Float.valueOf(lib.asFloat(receiver));
            }
            case 26: {
                return lib.asDouble(receiver);
            }
            case 27: {
                return lib.hasMembers(receiver);
            }
            case 28: {
                return lib.getMembers(receiver, (Boolean)args[offset]);
            }
            case 29: {
                return lib.isMemberReadable(receiver, (String)args[offset]);
            }
            case 30: {
                return lib.readMember(receiver, (String)args[offset]);
            }
            case 31: {
                return lib.isMemberModifiable(receiver, (String)args[offset]);
            }
            case 32: {
                return lib.isMemberInsertable(receiver, (String)args[offset]);
            }
            case 33: {
                lib.writeMember(receiver, (String)args[offset], args[offset + 1]);
                return null;
            }
            case 34: {
                return lib.isMemberRemovable(receiver, (String)args[offset]);
            }
            case 35: {
                lib.removeMember(receiver, (String)args[offset]);
                return null;
            }
            case 36: {
                return lib.isMemberInvocable(receiver, (String)args[offset]);
            }
            case 37: {
                return lib.invokeMember(receiver, (String)args[offset], (Object[])args[offset + 1]);
            }
            case 38: {
                return lib.isMemberInternal(receiver, (String)args[offset]);
            }
            case 39: {
                return lib.hasMemberReadSideEffects(receiver, (String)args[offset]);
            }
            case 40: {
                return lib.hasMemberWriteSideEffects(receiver, (String)args[offset]);
            }
            case 41: {
                return lib.hasHashEntries(receiver);
            }
            case 42: {
                return lib.getHashSize(receiver);
            }
            case 43: {
                return lib.isHashEntryReadable(receiver, args[offset]);
            }
            case 44: {
                return lib.readHashValue(receiver, args[offset]);
            }
            case 45: {
                return lib.readHashValueOrDefault(receiver, args[offset], args[offset + 1]);
            }
            case 46: {
                return lib.isHashEntryModifiable(receiver, args[offset]);
            }
            case 47: {
                return lib.isHashEntryInsertable(receiver, args[offset]);
            }
            case 48: {
                return lib.isHashEntryWritable(receiver, args[offset]);
            }
            case 49: {
                lib.writeHashEntry(receiver, args[offset], args[offset + 1]);
                return null;
            }
            case 50: {
                return lib.isHashEntryRemovable(receiver, args[offset]);
            }
            case 51: {
                lib.removeHashEntry(receiver, args[offset]);
                return null;
            }
            case 52: {
                return lib.isHashEntryExisting(receiver, args[offset]);
            }
            case 53: {
                return lib.getHashEntriesIterator(receiver);
            }
            case 54: {
                return lib.getHashKeysIterator(receiver);
            }
            case 55: {
                return lib.getHashValuesIterator(receiver);
            }
            case 56: {
                return lib.hasArrayElements(receiver);
            }
            case 57: {
                return lib.readArrayElement(receiver, (Long)args[offset]);
            }
            case 58: {
                return lib.getArraySize(receiver);
            }
            case 59: {
                return lib.isArrayElementReadable(receiver, (Long)args[offset]);
            }
            case 60: {
                lib.writeArrayElement(receiver, (Long)args[offset], args[offset + 1]);
                return null;
            }
            case 61: {
                lib.removeArrayElement(receiver, (Long)args[offset]);
                return null;
            }
            case 62: {
                return lib.isArrayElementModifiable(receiver, (Long)args[offset]);
            }
            case 63: {
                return lib.isArrayElementInsertable(receiver, (Long)args[offset]);
            }
            case 64: {
                return lib.isArrayElementRemovable(receiver, (Long)args[offset]);
            }
            case 65: {
                return lib.hasBufferElements(receiver);
            }
            case 66: {
                return lib.isBufferWritable(receiver);
            }
            case 67: {
                return lib.getBufferSize(receiver);
            }
            case 68: {
                return lib.readBufferByte(receiver, (Long)args[offset]);
            }
            case 69: {
                lib.writeBufferByte(receiver, (Long)args[offset], (Byte)args[offset + 1]);
                return null;
            }
            case 70: {
                return lib.readBufferShort(receiver, (ByteOrder)args[offset], (Long)args[offset + 1]);
            }
            case 71: {
                lib.writeBufferShort(receiver, (ByteOrder)args[offset], (Long)args[offset + 1], (Short)args[offset + 2]);
                return null;
            }
            case 72: {
                return lib.readBufferInt(receiver, (ByteOrder)args[offset], (Long)args[offset + 1]);
            }
            case 73: {
                lib.writeBufferInt(receiver, (ByteOrder)args[offset], (Long)args[offset + 1], (Integer)args[offset + 2]);
                return null;
            }
            case 74: {
                return lib.readBufferLong(receiver, (ByteOrder)args[offset], (Long)args[offset + 1]);
            }
            case 75: {
                lib.writeBufferLong(receiver, (ByteOrder)args[offset], (Long)args[offset + 1], (Long)args[offset + 2]);
                return null;
            }
            case 76: {
                return Float.valueOf(lib.readBufferFloat(receiver, (ByteOrder)args[offset], (Long)args[offset + 1]));
            }
            case 77: {
                lib.writeBufferFloat(receiver, (ByteOrder)args[offset], (Long)args[offset + 1], ((Float)args[offset + 2]).floatValue());
                return null;
            }
            case 78: {
                return lib.readBufferDouble(receiver, (ByteOrder)args[offset], (Long)args[offset + 1]);
            }
            case 79: {
                lib.writeBufferDouble(receiver, (ByteOrder)args[offset], (Long)args[offset + 1], (Double)args[offset + 2]);
                return null;
            }
            case 80: {
                return lib.isPointer(receiver);
            }
            case 81: {
                return lib.asPointer(receiver);
            }
            case 82: {
                lib.toNative(receiver);
                return null;
            }
            case 83: {
                return lib.asInstant(receiver);
            }
            case 84: {
                return lib.isTimeZone(receiver);
            }
            case 85: {
                return lib.asTimeZone(receiver);
            }
            case 86: {
                return lib.isDate(receiver);
            }
            case 87: {
                return lib.asDate(receiver);
            }
            case 88: {
                return lib.isTime(receiver);
            }
            case 89: {
                return lib.asTime(receiver);
            }
            case 90: {
                return lib.isDuration(receiver);
            }
            case 91: {
                return lib.asDuration(receiver);
            }
            case 92: {
                return lib.isException(receiver);
            }
            case 93: {
                return lib.throwException(receiver);
            }
            case 94: {
                return lib.getExceptionType(receiver);
            }
            case 95: {
                return lib.isExceptionIncompleteSource(receiver);
            }
            case 96: {
                return lib.getExceptionExitStatus(receiver);
            }
            case 97: {
                return lib.hasExceptionCause(receiver);
            }
            case 98: {
                return lib.getExceptionCause(receiver);
            }
            case 99: {
                return lib.hasExceptionMessage(receiver);
            }
            case 100: {
                return lib.getExceptionMessage(receiver);
            }
            case 101: {
                return lib.hasExceptionStackTrace(receiver);
            }
            case 102: {
                return lib.getExceptionStackTrace(receiver);
            }
            case 103: {
                return lib.hasIterator(receiver);
            }
            case 104: {
                return lib.getIterator(receiver);
            }
            case 105: {
                return lib.isIterator(receiver);
            }
            case 106: {
                return lib.hasIteratorNextElement(receiver);
            }
            case 107: {
                return lib.getIteratorNextElement(receiver);
            }
            case 108: {
                return lib.hasSourceLocation(receiver);
            }
            case 109: {
                return lib.getSourceLocation(receiver);
            }
            case 110: {
                return lib.hasLanguage(receiver);
            }
            case 111: {
                return lib.getLanguage(receiver);
            }
            case 112: {
                return lib.hasMetaObject(receiver);
            }
            case 113: {
                return lib.getMetaObject(receiver);
            }
            case 114: {
                return lib.toDisplayString(receiver, (Boolean)args[offset]);
            }
            case 115: {
                return lib.isMetaObject(receiver);
            }
            case 116: {
                return lib.getMetaQualifiedName(receiver);
            }
            case 117: {
                return lib.getMetaSimpleName(receiver);
            }
            case 118: {
                return lib.isMetaInstance(receiver, args[offset]);
            }
            case 119: {
                return lib.hasMetaParents(receiver);
            }
            case 120: {
                return lib.getMetaParents(receiver);
            }
            case 121: {
                return lib.isIdenticalOrUndefined(receiver, args[offset]);
            }
            case 122: {
                return lib.isIdentical(receiver, args[offset], (InteropLibrary)args[offset + 1]);
            }
            case 123: {
                return lib.identityHashCode(receiver);
            }
            case 124: {
                return lib.isScope(receiver);
            }
            case 125: {
                return lib.hasScopeParent(receiver);
            }
            case 126: {
                return lib.getScopeParent(receiver);
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        throw new AbstractMethodError(message.toString());
    }

    @Override
    protected InteropLibrary createDispatchImpl(int limit) {
        return new CachedDispatchFirst(null, null, limit);
    }

    @Override
    protected InteropLibrary createUncachedDispatch() {
        return new UncachedDispatch();
    }

    private static Class<InteropLibrary> lazyLibraryClass() {
        try {
            return Class.forName("com.oracle.truffle.api.interop.InteropLibrary", false, InteropLibraryGen.class.getClassLoader());
        }
        catch (ClassNotFoundException e) {
            throw CompilerDirectives.shouldNotReachHere(e);
        }
    }

    static {
        LibraryExport.register(LIBRARY_CLASS, new Default());
        LibraryFactory.register(LIBRARY_CLASS, INSTANCE);
    }

    @GeneratedBy(value=InteropLibrary.class)
    private static abstract class CachedDispatch
    extends InteropLibrary {
        @Node.Child
        InteropLibrary library;
        @Node.Child
        CachedDispatch next;

        CachedDispatch(InteropLibrary library, CachedDispatch next) {
            this.library = library;
            this.next = next;
        }

        abstract int getLimit();

        @Override
        @ExplodeLoop
        public boolean isNull(Object receiver_) {
            while (true) {
                CachedDispatch current = this;
                do {
                    InteropLibrary thisLibrary;
                    if ((thisLibrary = current.library) == null || !thisLibrary.accepts(receiver_)) continue;
                    return thisLibrary.isNull(receiver_);
                } while ((current = current.next) != null);
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.specialize(receiver_);
            }
        }

        @Override
        @ExplodeLoop
        public boolean isBoolean(Object receiver_) {
            while (true) {
                CachedDispatch current = this;
                do {
                    InteropLibrary thisLibrary;
                    if ((thisLibrary = current.library) == null || !thisLibrary.accepts(receiver_)) continue;
                    return thisLibrary.isBoolean(receiver_);
                } while ((current = current.next) != null);
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.specialize(receiver_);
            }
        }

        @Override
        @ExplodeLoop
        public boolean asBoolean(Object receiver_) throws UnsupportedMessageException {
            while (true) {
                CachedDispatch current = this;
                do {
                    InteropLibrary thisLibrary;
                    if ((thisLibrary = current.library) == null || !thisLibrary.accepts(receiver_)) continue;
                    return thisLibrary.asBoolean(receiver_);
                } while ((current = current.next) != null);
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.specialize(receiver_);
            }
        }

        @Override
        @ExplodeLoop
        public boolean isExecutable(Object receiver_) {
            while (true) {
                CachedDispatch current = this;
                do {
                    InteropLibrary thisLibrary;
                    if ((thisLibrary = current.library) == null || !thisLibrary.accepts(receiver_)) continue;
                    return thisLibrary.isExecutable(receiver_);
                } while ((current = current.next) != null);
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.specialize(receiver_);
            }
        }

        @Override
        @ExplodeLoop
        public Object execute(Object receiver_, Object ... arguments) throws UnsupportedTypeException, ArityException, UnsupportedMessageException {
            while (true) {
                CachedDispatch current = this;
                do {
                    InteropLibrary thisLibrary;
                    if ((thisLibrary = current.library) == null || !thisLibrary.accepts(receiver_)) continue;
                    return thisLibrary.execute(receiver_, arguments);
                } while ((current = current.next) != null);
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.specialize(receiver_);
            }
        }

        @Override
        @ExplodeLoop
        public boolean hasExecutableName(Object receiver_) {
            while (true) {
                CachedDispatch current = this;
                do {
                    InteropLibrary thisLibrary;
                    if ((thisLibrary = current.library) == null || !thisLibrary.accepts(receiver_)) continue;
                    return thisLibrary.hasExecutableName(receiver_);
                } while ((current = current.next) != null);
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.specialize(receiver_);
            }
        }

        @Override
        @ExplodeLoop
        public Object getExecutableName(Object receiver_) throws UnsupportedMessageException {
            while (true) {
                CachedDispatch current = this;
                do {
                    InteropLibrary thisLibrary;
                    if ((thisLibrary = current.library) == null || !thisLibrary.accepts(receiver_)) continue;
                    return thisLibrary.getExecutableName(receiver_);
                } while ((current = current.next) != null);
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.specialize(receiver_);
            }
        }

        @Override
        @ExplodeLoop
        public boolean hasDeclaringMetaObject(Object receiver_) {
            while (true) {
                CachedDispatch current = this;
                do {
                    InteropLibrary thisLibrary;
                    if ((thisLibrary = current.library) == null || !thisLibrary.accepts(receiver_)) continue;
                    return thisLibrary.hasDeclaringMetaObject(receiver_);
                } while ((current = current.next) != null);
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.specialize(receiver_);
            }
        }

        @Override
        @ExplodeLoop
        public Object getDeclaringMetaObject(Object receiver_) throws UnsupportedMessageException {
            while (true) {
                CachedDispatch current = this;
                do {
                    InteropLibrary thisLibrary;
                    if ((thisLibrary = current.library) == null || !thisLibrary.accepts(receiver_)) continue;
                    return thisLibrary.getDeclaringMetaObject(receiver_);
                } while ((current = current.next) != null);
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.specialize(receiver_);
            }
        }

        @Override
        @ExplodeLoop
        public boolean isInstantiable(Object receiver_) {
            while (true) {
                CachedDispatch current = this;
                do {
                    InteropLibrary thisLibrary;
                    if ((thisLibrary = current.library) == null || !thisLibrary.accepts(receiver_)) continue;
                    return thisLibrary.isInstantiable(receiver_);
                } while ((current = current.next) != null);
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.specialize(receiver_);
            }
        }

        @Override
        @ExplodeLoop
        public Object instantiate(Object receiver_, Object ... arguments) throws UnsupportedTypeException, ArityException, UnsupportedMessageException {
            while (true) {
                CachedDispatch current = this;
                do {
                    InteropLibrary thisLibrary;
                    if ((thisLibrary = current.library) == null || !thisLibrary.accepts(receiver_)) continue;
                    return thisLibrary.instantiate(receiver_, arguments);
                } while ((current = current.next) != null);
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.specialize(receiver_);
            }
        }

        @Override
        @ExplodeLoop
        public boolean isString(Object receiver_) {
            while (true) {
                CachedDispatch current = this;
                do {
                    InteropLibrary thisLibrary;
                    if ((thisLibrary = current.library) == null || !thisLibrary.accepts(receiver_)) continue;
                    return thisLibrary.isString(receiver_);
                } while ((current = current.next) != null);
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.specialize(receiver_);
            }
        }

        @Override
        @ExplodeLoop
        public String asString(Object receiver_) throws UnsupportedMessageException {
            while (true) {
                CachedDispatch current = this;
                do {
                    InteropLibrary thisLibrary;
                    if ((thisLibrary = current.library) == null || !thisLibrary.accepts(receiver_)) continue;
                    return thisLibrary.asString(receiver_);
                } while ((current = current.next) != null);
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.specialize(receiver_);
            }
        }

        @Override
        @ExplodeLoop
        public TruffleString asTruffleString(Object receiver_) throws UnsupportedMessageException {
            while (true) {
                CachedDispatch current = this;
                do {
                    InteropLibrary thisLibrary;
                    if ((thisLibrary = current.library) == null || !thisLibrary.accepts(receiver_)) continue;
                    return thisLibrary.asTruffleString(receiver_);
                } while ((current = current.next) != null);
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.specialize(receiver_);
            }
        }

        @Override
        @ExplodeLoop
        public boolean isNumber(Object receiver_) {
            while (true) {
                CachedDispatch current = this;
                do {
                    InteropLibrary thisLibrary;
                    if ((thisLibrary = current.library) == null || !thisLibrary.accepts(receiver_)) continue;
                    return thisLibrary.isNumber(receiver_);
                } while ((current = current.next) != null);
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.specialize(receiver_);
            }
        }

        @Override
        @ExplodeLoop
        public boolean fitsInByte(Object receiver_) {
            while (true) {
                CachedDispatch current = this;
                do {
                    InteropLibrary thisLibrary;
                    if ((thisLibrary = current.library) == null || !thisLibrary.accepts(receiver_)) continue;
                    return thisLibrary.fitsInByte(receiver_);
                } while ((current = current.next) != null);
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.specialize(receiver_);
            }
        }

        @Override
        @ExplodeLoop
        public boolean fitsInShort(Object receiver_) {
            while (true) {
                CachedDispatch current = this;
                do {
                    InteropLibrary thisLibrary;
                    if ((thisLibrary = current.library) == null || !thisLibrary.accepts(receiver_)) continue;
                    return thisLibrary.fitsInShort(receiver_);
                } while ((current = current.next) != null);
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.specialize(receiver_);
            }
        }

        @Override
        @ExplodeLoop
        public boolean fitsInInt(Object receiver_) {
            while (true) {
                CachedDispatch current = this;
                do {
                    InteropLibrary thisLibrary;
                    if ((thisLibrary = current.library) == null || !thisLibrary.accepts(receiver_)) continue;
                    return thisLibrary.fitsInInt(receiver_);
                } while ((current = current.next) != null);
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.specialize(receiver_);
            }
        }

        @Override
        @ExplodeLoop
        public boolean fitsInLong(Object receiver_) {
            while (true) {
                CachedDispatch current = this;
                do {
                    InteropLibrary thisLibrary;
                    if ((thisLibrary = current.library) == null || !thisLibrary.accepts(receiver_)) continue;
                    return thisLibrary.fitsInLong(receiver_);
                } while ((current = current.next) != null);
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.specialize(receiver_);
            }
        }

        @Override
        @ExplodeLoop
        public boolean fitsInFloat(Object receiver_) {
            while (true) {
                CachedDispatch current = this;
                do {
                    InteropLibrary thisLibrary;
                    if ((thisLibrary = current.library) == null || !thisLibrary.accepts(receiver_)) continue;
                    return thisLibrary.fitsInFloat(receiver_);
                } while ((current = current.next) != null);
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.specialize(receiver_);
            }
        }

        @Override
        @ExplodeLoop
        public boolean fitsInDouble(Object receiver_) {
            while (true) {
                CachedDispatch current = this;
                do {
                    InteropLibrary thisLibrary;
                    if ((thisLibrary = current.library) == null || !thisLibrary.accepts(receiver_)) continue;
                    return thisLibrary.fitsInDouble(receiver_);
                } while ((current = current.next) != null);
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.specialize(receiver_);
            }
        }

        @Override
        @ExplodeLoop
        public byte asByte(Object receiver_) throws UnsupportedMessageException {
            while (true) {
                CachedDispatch current = this;
                do {
                    InteropLibrary thisLibrary;
                    if ((thisLibrary = current.library) == null || !thisLibrary.accepts(receiver_)) continue;
                    return thisLibrary.asByte(receiver_);
                } while ((current = current.next) != null);
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.specialize(receiver_);
            }
        }

        @Override
        @ExplodeLoop
        public short asShort(Object receiver_) throws UnsupportedMessageException {
            while (true) {
                CachedDispatch current = this;
                do {
                    InteropLibrary thisLibrary;
                    if ((thisLibrary = current.library) == null || !thisLibrary.accepts(receiver_)) continue;
                    return thisLibrary.asShort(receiver_);
                } while ((current = current.next) != null);
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.specialize(receiver_);
            }
        }

        @Override
        @ExplodeLoop
        public int asInt(Object receiver_) throws UnsupportedMessageException {
            while (true) {
                CachedDispatch current = this;
                do {
                    InteropLibrary thisLibrary;
                    if ((thisLibrary = current.library) == null || !thisLibrary.accepts(receiver_)) continue;
                    return thisLibrary.asInt(receiver_);
                } while ((current = current.next) != null);
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.specialize(receiver_);
            }
        }

        @Override
        @ExplodeLoop
        public long asLong(Object receiver_) throws UnsupportedMessageException {
            while (true) {
                CachedDispatch current = this;
                do {
                    InteropLibrary thisLibrary;
                    if ((thisLibrary = current.library) == null || !thisLibrary.accepts(receiver_)) continue;
                    return thisLibrary.asLong(receiver_);
                } while ((current = current.next) != null);
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.specialize(receiver_);
            }
        }

        @Override
        @ExplodeLoop
        public float asFloat(Object receiver_) throws UnsupportedMessageException {
            while (true) {
                CachedDispatch current = this;
                do {
                    InteropLibrary thisLibrary;
                    if ((thisLibrary = current.library) == null || !thisLibrary.accepts(receiver_)) continue;
                    return thisLibrary.asFloat(receiver_);
                } while ((current = current.next) != null);
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.specialize(receiver_);
            }
        }

        @Override
        @ExplodeLoop
        public double asDouble(Object receiver_) throws UnsupportedMessageException {
            while (true) {
                CachedDispatch current = this;
                do {
                    InteropLibrary thisLibrary;
                    if ((thisLibrary = current.library) == null || !thisLibrary.accepts(receiver_)) continue;
                    return thisLibrary.asDouble(receiver_);
                } while ((current = current.next) != null);
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.specialize(receiver_);
            }
        }

        @Override
        @ExplodeLoop
        public boolean hasMembers(Object receiver_) {
            while (true) {
                CachedDispatch current = this;
                do {
                    InteropLibrary thisLibrary;
                    if ((thisLibrary = current.library) == null || !thisLibrary.accepts(receiver_)) continue;
                    return thisLibrary.hasMembers(receiver_);
                } while ((current = current.next) != null);
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.specialize(receiver_);
            }
        }

        @Override
        @ExplodeLoop
        public Object getMembers(Object receiver_, boolean includeInternal) throws UnsupportedMessageException {
            while (true) {
                CachedDispatch current = this;
                do {
                    InteropLibrary thisLibrary;
                    if ((thisLibrary = current.library) == null || !thisLibrary.accepts(receiver_)) continue;
                    return thisLibrary.getMembers(receiver_, includeInternal);
                } while ((current = current.next) != null);
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.specialize(receiver_);
            }
        }

        @Override
        @ExplodeLoop
        public boolean isMemberReadable(Object receiver_, String member) {
            while (true) {
                CachedDispatch current = this;
                do {
                    InteropLibrary thisLibrary;
                    if ((thisLibrary = current.library) == null || !thisLibrary.accepts(receiver_)) continue;
                    return thisLibrary.isMemberReadable(receiver_, member);
                } while ((current = current.next) != null);
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.specialize(receiver_);
            }
        }

        @Override
        @ExplodeLoop
        public Object readMember(Object receiver_, String member) throws UnsupportedMessageException, UnknownIdentifierException {
            while (true) {
                CachedDispatch current = this;
                do {
                    InteropLibrary thisLibrary;
                    if ((thisLibrary = current.library) == null || !thisLibrary.accepts(receiver_)) continue;
                    return thisLibrary.readMember(receiver_, member);
                } while ((current = current.next) != null);
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.specialize(receiver_);
            }
        }

        @Override
        @ExplodeLoop
        public boolean isMemberModifiable(Object receiver_, String member) {
            while (true) {
                CachedDispatch current = this;
                do {
                    InteropLibrary thisLibrary;
                    if ((thisLibrary = current.library) == null || !thisLibrary.accepts(receiver_)) continue;
                    return thisLibrary.isMemberModifiable(receiver_, member);
                } while ((current = current.next) != null);
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.specialize(receiver_);
            }
        }

        @Override
        @ExplodeLoop
        public boolean isMemberInsertable(Object receiver_, String member) {
            while (true) {
                CachedDispatch current = this;
                do {
                    InteropLibrary thisLibrary;
                    if ((thisLibrary = current.library) == null || !thisLibrary.accepts(receiver_)) continue;
                    return thisLibrary.isMemberInsertable(receiver_, member);
                } while ((current = current.next) != null);
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.specialize(receiver_);
            }
        }

        @Override
        @ExplodeLoop
        public void writeMember(Object receiver_, String member, Object value2) throws UnsupportedMessageException, UnknownIdentifierException, UnsupportedTypeException {
            while (true) {
                CachedDispatch current = this;
                do {
                    InteropLibrary thisLibrary;
                    if ((thisLibrary = current.library) == null || !thisLibrary.accepts(receiver_)) continue;
                    thisLibrary.writeMember(receiver_, member, value2);
                    return;
                } while ((current = current.next) != null);
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.specialize(receiver_);
            }
        }

        @Override
        @ExplodeLoop
        public boolean isMemberRemovable(Object receiver_, String member) {
            while (true) {
                CachedDispatch current = this;
                do {
                    InteropLibrary thisLibrary;
                    if ((thisLibrary = current.library) == null || !thisLibrary.accepts(receiver_)) continue;
                    return thisLibrary.isMemberRemovable(receiver_, member);
                } while ((current = current.next) != null);
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.specialize(receiver_);
            }
        }

        @Override
        @ExplodeLoop
        public void removeMember(Object receiver_, String member) throws UnsupportedMessageException, UnknownIdentifierException {
            while (true) {
                CachedDispatch current = this;
                do {
                    InteropLibrary thisLibrary;
                    if ((thisLibrary = current.library) == null || !thisLibrary.accepts(receiver_)) continue;
                    thisLibrary.removeMember(receiver_, member);
                    return;
                } while ((current = current.next) != null);
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.specialize(receiver_);
            }
        }

        @Override
        @ExplodeLoop
        public boolean isMemberInvocable(Object receiver_, String member) {
            while (true) {
                CachedDispatch current = this;
                do {
                    InteropLibrary thisLibrary;
                    if ((thisLibrary = current.library) == null || !thisLibrary.accepts(receiver_)) continue;
                    return thisLibrary.isMemberInvocable(receiver_, member);
                } while ((current = current.next) != null);
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.specialize(receiver_);
            }
        }

        @Override
        @ExplodeLoop
        public Object invokeMember(Object receiver_, String member, Object ... arguments) throws UnsupportedMessageException, ArityException, UnknownIdentifierException, UnsupportedTypeException {
            while (true) {
                CachedDispatch current = this;
                do {
                    InteropLibrary thisLibrary;
                    if ((thisLibrary = current.library) == null || !thisLibrary.accepts(receiver_)) continue;
                    return thisLibrary.invokeMember(receiver_, member, arguments);
                } while ((current = current.next) != null);
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.specialize(receiver_);
            }
        }

        @Override
        @ExplodeLoop
        public boolean isMemberInternal(Object receiver_, String member) {
            while (true) {
                CachedDispatch current = this;
                do {
                    InteropLibrary thisLibrary;
                    if ((thisLibrary = current.library) == null || !thisLibrary.accepts(receiver_)) continue;
                    return thisLibrary.isMemberInternal(receiver_, member);
                } while ((current = current.next) != null);
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.specialize(receiver_);
            }
        }

        @Override
        @ExplodeLoop
        public boolean hasMemberReadSideEffects(Object receiver_, String member) {
            while (true) {
                CachedDispatch current = this;
                do {
                    InteropLibrary thisLibrary;
                    if ((thisLibrary = current.library) == null || !thisLibrary.accepts(receiver_)) continue;
                    return thisLibrary.hasMemberReadSideEffects(receiver_, member);
                } while ((current = current.next) != null);
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.specialize(receiver_);
            }
        }

        @Override
        @ExplodeLoop
        public boolean hasMemberWriteSideEffects(Object receiver_, String member) {
            while (true) {
                CachedDispatch current = this;
                do {
                    InteropLibrary thisLibrary;
                    if ((thisLibrary = current.library) == null || !thisLibrary.accepts(receiver_)) continue;
                    return thisLibrary.hasMemberWriteSideEffects(receiver_, member);
                } while ((current = current.next) != null);
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.specialize(receiver_);
            }
        }

        @Override
        @ExplodeLoop
        public boolean hasHashEntries(Object receiver_) {
            while (true) {
                CachedDispatch current = this;
                do {
                    InteropLibrary thisLibrary;
                    if ((thisLibrary = current.library) == null || !thisLibrary.accepts(receiver_)) continue;
                    return thisLibrary.hasHashEntries(receiver_);
                } while ((current = current.next) != null);
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.specialize(receiver_);
            }
        }

        @Override
        @ExplodeLoop
        public long getHashSize(Object receiver_) throws UnsupportedMessageException {
            while (true) {
                CachedDispatch current = this;
                do {
                    InteropLibrary thisLibrary;
                    if ((thisLibrary = current.library) == null || !thisLibrary.accepts(receiver_)) continue;
                    return thisLibrary.getHashSize(receiver_);
                } while ((current = current.next) != null);
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.specialize(receiver_);
            }
        }

        @Override
        @ExplodeLoop
        public boolean isHashEntryReadable(Object receiver_, Object key) {
            while (true) {
                CachedDispatch current = this;
                do {
                    InteropLibrary thisLibrary;
                    if ((thisLibrary = current.library) == null || !thisLibrary.accepts(receiver_)) continue;
                    return thisLibrary.isHashEntryReadable(receiver_, key);
                } while ((current = current.next) != null);
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.specialize(receiver_);
            }
        }

        @Override
        @ExplodeLoop
        public Object readHashValue(Object receiver_, Object key) throws UnsupportedMessageException, UnknownKeyException {
            while (true) {
                CachedDispatch current = this;
                do {
                    InteropLibrary thisLibrary;
                    if ((thisLibrary = current.library) == null || !thisLibrary.accepts(receiver_)) continue;
                    return thisLibrary.readHashValue(receiver_, key);
                } while ((current = current.next) != null);
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.specialize(receiver_);
            }
        }

        @Override
        @ExplodeLoop
        public Object readHashValueOrDefault(Object receiver_, Object key, Object defaultValue) throws UnsupportedMessageException {
            while (true) {
                CachedDispatch current = this;
                do {
                    InteropLibrary thisLibrary;
                    if ((thisLibrary = current.library) == null || !thisLibrary.accepts(receiver_)) continue;
                    return thisLibrary.readHashValueOrDefault(receiver_, key, defaultValue);
                } while ((current = current.next) != null);
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.specialize(receiver_);
            }
        }

        @Override
        @ExplodeLoop
        public boolean isHashEntryModifiable(Object receiver_, Object key) {
            while (true) {
                CachedDispatch current = this;
                do {
                    InteropLibrary thisLibrary;
                    if ((thisLibrary = current.library) == null || !thisLibrary.accepts(receiver_)) continue;
                    return thisLibrary.isHashEntryModifiable(receiver_, key);
                } while ((current = current.next) != null);
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.specialize(receiver_);
            }
        }

        @Override
        @ExplodeLoop
        public boolean isHashEntryInsertable(Object receiver_, Object key) {
            while (true) {
                CachedDispatch current = this;
                do {
                    InteropLibrary thisLibrary;
                    if ((thisLibrary = current.library) == null || !thisLibrary.accepts(receiver_)) continue;
                    return thisLibrary.isHashEntryInsertable(receiver_, key);
                } while ((current = current.next) != null);
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.specialize(receiver_);
            }
        }

        @Override
        @ExplodeLoop
        public boolean isHashEntryWritable(Object receiver_, Object key) {
            while (true) {
                CachedDispatch current = this;
                do {
                    InteropLibrary thisLibrary;
                    if ((thisLibrary = current.library) == null || !thisLibrary.accepts(receiver_)) continue;
                    return thisLibrary.isHashEntryWritable(receiver_, key);
                } while ((current = current.next) != null);
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.specialize(receiver_);
            }
        }

        @Override
        @ExplodeLoop
        public void writeHashEntry(Object receiver_, Object key, Object value2) throws UnsupportedMessageException, UnknownKeyException, UnsupportedTypeException {
            while (true) {
                CachedDispatch current = this;
                do {
                    InteropLibrary thisLibrary;
                    if ((thisLibrary = current.library) == null || !thisLibrary.accepts(receiver_)) continue;
                    thisLibrary.writeHashEntry(receiver_, key, value2);
                    return;
                } while ((current = current.next) != null);
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.specialize(receiver_);
            }
        }

        @Override
        @ExplodeLoop
        public boolean isHashEntryRemovable(Object receiver_, Object key) {
            while (true) {
                CachedDispatch current = this;
                do {
                    InteropLibrary thisLibrary;
                    if ((thisLibrary = current.library) == null || !thisLibrary.accepts(receiver_)) continue;
                    return thisLibrary.isHashEntryRemovable(receiver_, key);
                } while ((current = current.next) != null);
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.specialize(receiver_);
            }
        }

        @Override
        @ExplodeLoop
        public void removeHashEntry(Object receiver_, Object key) throws UnsupportedMessageException, UnknownKeyException {
            while (true) {
                CachedDispatch current = this;
                do {
                    InteropLibrary thisLibrary;
                    if ((thisLibrary = current.library) == null || !thisLibrary.accepts(receiver_)) continue;
                    thisLibrary.removeHashEntry(receiver_, key);
                    return;
                } while ((current = current.next) != null);
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.specialize(receiver_);
            }
        }

        @Override
        @ExplodeLoop
        public boolean isHashEntryExisting(Object receiver_, Object key) {
            while (true) {
                CachedDispatch current = this;
                do {
                    InteropLibrary thisLibrary;
                    if ((thisLibrary = current.library) == null || !thisLibrary.accepts(receiver_)) continue;
                    return thisLibrary.isHashEntryExisting(receiver_, key);
                } while ((current = current.next) != null);
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.specialize(receiver_);
            }
        }

        @Override
        @ExplodeLoop
        public Object getHashEntriesIterator(Object receiver_) throws UnsupportedMessageException {
            while (true) {
                CachedDispatch current = this;
                do {
                    InteropLibrary thisLibrary;
                    if ((thisLibrary = current.library) == null || !thisLibrary.accepts(receiver_)) continue;
                    return thisLibrary.getHashEntriesIterator(receiver_);
                } while ((current = current.next) != null);
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.specialize(receiver_);
            }
        }

        @Override
        @ExplodeLoop
        public Object getHashKeysIterator(Object receiver_) throws UnsupportedMessageException {
            while (true) {
                CachedDispatch current = this;
                do {
                    InteropLibrary thisLibrary;
                    if ((thisLibrary = current.library) == null || !thisLibrary.accepts(receiver_)) continue;
                    return thisLibrary.getHashKeysIterator(receiver_);
                } while ((current = current.next) != null);
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.specialize(receiver_);
            }
        }

        @Override
        @ExplodeLoop
        public Object getHashValuesIterator(Object receiver_) throws UnsupportedMessageException {
            while (true) {
                CachedDispatch current = this;
                do {
                    InteropLibrary thisLibrary;
                    if ((thisLibrary = current.library) == null || !thisLibrary.accepts(receiver_)) continue;
                    return thisLibrary.getHashValuesIterator(receiver_);
                } while ((current = current.next) != null);
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.specialize(receiver_);
            }
        }

        @Override
        @ExplodeLoop
        public boolean hasArrayElements(Object receiver_) {
            while (true) {
                CachedDispatch current = this;
                do {
                    InteropLibrary thisLibrary;
                    if ((thisLibrary = current.library) == null || !thisLibrary.accepts(receiver_)) continue;
                    return thisLibrary.hasArrayElements(receiver_);
                } while ((current = current.next) != null);
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.specialize(receiver_);
            }
        }

        @Override
        @ExplodeLoop
        public Object readArrayElement(Object receiver_, long index) throws UnsupportedMessageException, InvalidArrayIndexException {
            while (true) {
                CachedDispatch current = this;
                do {
                    InteropLibrary thisLibrary;
                    if ((thisLibrary = current.library) == null || !thisLibrary.accepts(receiver_)) continue;
                    return thisLibrary.readArrayElement(receiver_, index);
                } while ((current = current.next) != null);
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.specialize(receiver_);
            }
        }

        @Override
        @ExplodeLoop
        public long getArraySize(Object receiver_) throws UnsupportedMessageException {
            while (true) {
                CachedDispatch current = this;
                do {
                    InteropLibrary thisLibrary;
                    if ((thisLibrary = current.library) == null || !thisLibrary.accepts(receiver_)) continue;
                    return thisLibrary.getArraySize(receiver_);
                } while ((current = current.next) != null);
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.specialize(receiver_);
            }
        }

        @Override
        @ExplodeLoop
        public boolean isArrayElementReadable(Object receiver_, long index) {
            while (true) {
                CachedDispatch current = this;
                do {
                    InteropLibrary thisLibrary;
                    if ((thisLibrary = current.library) == null || !thisLibrary.accepts(receiver_)) continue;
                    return thisLibrary.isArrayElementReadable(receiver_, index);
                } while ((current = current.next) != null);
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.specialize(receiver_);
            }
        }

        @Override
        @ExplodeLoop
        public void writeArrayElement(Object receiver_, long index, Object value2) throws UnsupportedMessageException, UnsupportedTypeException, InvalidArrayIndexException {
            while (true) {
                CachedDispatch current = this;
                do {
                    InteropLibrary thisLibrary;
                    if ((thisLibrary = current.library) == null || !thisLibrary.accepts(receiver_)) continue;
                    thisLibrary.writeArrayElement(receiver_, index, value2);
                    return;
                } while ((current = current.next) != null);
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.specialize(receiver_);
            }
        }

        @Override
        @ExplodeLoop
        public void removeArrayElement(Object receiver_, long index) throws UnsupportedMessageException, InvalidArrayIndexException {
            while (true) {
                CachedDispatch current = this;
                do {
                    InteropLibrary thisLibrary;
                    if ((thisLibrary = current.library) == null || !thisLibrary.accepts(receiver_)) continue;
                    thisLibrary.removeArrayElement(receiver_, index);
                    return;
                } while ((current = current.next) != null);
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.specialize(receiver_);
            }
        }

        @Override
        @ExplodeLoop
        public boolean isArrayElementModifiable(Object receiver_, long index) {
            while (true) {
                CachedDispatch current = this;
                do {
                    InteropLibrary thisLibrary;
                    if ((thisLibrary = current.library) == null || !thisLibrary.accepts(receiver_)) continue;
                    return thisLibrary.isArrayElementModifiable(receiver_, index);
                } while ((current = current.next) != null);
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.specialize(receiver_);
            }
        }

        @Override
        @ExplodeLoop
        public boolean isArrayElementInsertable(Object receiver_, long index) {
            while (true) {
                CachedDispatch current = this;
                do {
                    InteropLibrary thisLibrary;
                    if ((thisLibrary = current.library) == null || !thisLibrary.accepts(receiver_)) continue;
                    return thisLibrary.isArrayElementInsertable(receiver_, index);
                } while ((current = current.next) != null);
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.specialize(receiver_);
            }
        }

        @Override
        @ExplodeLoop
        public boolean isArrayElementRemovable(Object receiver_, long index) {
            while (true) {
                CachedDispatch current = this;
                do {
                    InteropLibrary thisLibrary;
                    if ((thisLibrary = current.library) == null || !thisLibrary.accepts(receiver_)) continue;
                    return thisLibrary.isArrayElementRemovable(receiver_, index);
                } while ((current = current.next) != null);
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.specialize(receiver_);
            }
        }

        @Override
        @ExplodeLoop
        public boolean hasBufferElements(Object receiver_) {
            while (true) {
                CachedDispatch current = this;
                do {
                    InteropLibrary thisLibrary;
                    if ((thisLibrary = current.library) == null || !thisLibrary.accepts(receiver_)) continue;
                    return thisLibrary.hasBufferElements(receiver_);
                } while ((current = current.next) != null);
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.specialize(receiver_);
            }
        }

        @Override
        @ExplodeLoop
        public boolean isBufferWritable(Object receiver_) throws UnsupportedMessageException {
            while (true) {
                CachedDispatch current = this;
                do {
                    InteropLibrary thisLibrary;
                    if ((thisLibrary = current.library) == null || !thisLibrary.accepts(receiver_)) continue;
                    return thisLibrary.isBufferWritable(receiver_);
                } while ((current = current.next) != null);
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.specialize(receiver_);
            }
        }

        @Override
        @ExplodeLoop
        public long getBufferSize(Object receiver_) throws UnsupportedMessageException {
            while (true) {
                CachedDispatch current = this;
                do {
                    InteropLibrary thisLibrary;
                    if ((thisLibrary = current.library) == null || !thisLibrary.accepts(receiver_)) continue;
                    return thisLibrary.getBufferSize(receiver_);
                } while ((current = current.next) != null);
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.specialize(receiver_);
            }
        }

        @Override
        @ExplodeLoop
        public byte readBufferByte(Object receiver_, long byteOffset) throws UnsupportedMessageException, InvalidBufferOffsetException {
            while (true) {
                CachedDispatch current = this;
                do {
                    InteropLibrary thisLibrary;
                    if ((thisLibrary = current.library) == null || !thisLibrary.accepts(receiver_)) continue;
                    return thisLibrary.readBufferByte(receiver_, byteOffset);
                } while ((current = current.next) != null);
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.specialize(receiver_);
            }
        }

        @Override
        @ExplodeLoop
        public void writeBufferByte(Object receiver_, long byteOffset, byte value2) throws UnsupportedMessageException, InvalidBufferOffsetException {
            while (true) {
                CachedDispatch current = this;
                do {
                    InteropLibrary thisLibrary;
                    if ((thisLibrary = current.library) == null || !thisLibrary.accepts(receiver_)) continue;
                    thisLibrary.writeBufferByte(receiver_, byteOffset, value2);
                    return;
                } while ((current = current.next) != null);
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.specialize(receiver_);
            }
        }

        @Override
        @ExplodeLoop
        public short readBufferShort(Object receiver_, ByteOrder order, long byteOffset) throws UnsupportedMessageException, InvalidBufferOffsetException {
            while (true) {
                CachedDispatch current = this;
                do {
                    InteropLibrary thisLibrary;
                    if ((thisLibrary = current.library) == null || !thisLibrary.accepts(receiver_)) continue;
                    return thisLibrary.readBufferShort(receiver_, order, byteOffset);
                } while ((current = current.next) != null);
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.specialize(receiver_);
            }
        }

        @Override
        @ExplodeLoop
        public void writeBufferShort(Object receiver_, ByteOrder order, long byteOffset, short value2) throws UnsupportedMessageException, InvalidBufferOffsetException {
            while (true) {
                CachedDispatch current = this;
                do {
                    InteropLibrary thisLibrary;
                    if ((thisLibrary = current.library) == null || !thisLibrary.accepts(receiver_)) continue;
                    thisLibrary.writeBufferShort(receiver_, order, byteOffset, value2);
                    return;
                } while ((current = current.next) != null);
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.specialize(receiver_);
            }
        }

        @Override
        @ExplodeLoop
        public int readBufferInt(Object receiver_, ByteOrder order, long byteOffset) throws UnsupportedMessageException, InvalidBufferOffsetException {
            while (true) {
                CachedDispatch current = this;
                do {
                    InteropLibrary thisLibrary;
                    if ((thisLibrary = current.library) == null || !thisLibrary.accepts(receiver_)) continue;
                    return thisLibrary.readBufferInt(receiver_, order, byteOffset);
                } while ((current = current.next) != null);
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.specialize(receiver_);
            }
        }

        @Override
        @ExplodeLoop
        public void writeBufferInt(Object receiver_, ByteOrder order, long byteOffset, int value2) throws UnsupportedMessageException, InvalidBufferOffsetException {
            while (true) {
                CachedDispatch current = this;
                do {
                    InteropLibrary thisLibrary;
                    if ((thisLibrary = current.library) == null || !thisLibrary.accepts(receiver_)) continue;
                    thisLibrary.writeBufferInt(receiver_, order, byteOffset, value2);
                    return;
                } while ((current = current.next) != null);
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.specialize(receiver_);
            }
        }

        @Override
        @ExplodeLoop
        public long readBufferLong(Object receiver_, ByteOrder order, long byteOffset) throws UnsupportedMessageException, InvalidBufferOffsetException {
            while (true) {
                CachedDispatch current = this;
                do {
                    InteropLibrary thisLibrary;
                    if ((thisLibrary = current.library) == null || !thisLibrary.accepts(receiver_)) continue;
                    return thisLibrary.readBufferLong(receiver_, order, byteOffset);
                } while ((current = current.next) != null);
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.specialize(receiver_);
            }
        }

        @Override
        @ExplodeLoop
        public void writeBufferLong(Object receiver_, ByteOrder order, long byteOffset, long value2) throws UnsupportedMessageException, InvalidBufferOffsetException {
            while (true) {
                CachedDispatch current = this;
                do {
                    InteropLibrary thisLibrary;
                    if ((thisLibrary = current.library) == null || !thisLibrary.accepts(receiver_)) continue;
                    thisLibrary.writeBufferLong(receiver_, order, byteOffset, value2);
                    return;
                } while ((current = current.next) != null);
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.specialize(receiver_);
            }
        }

        @Override
        @ExplodeLoop
        public float readBufferFloat(Object receiver_, ByteOrder order, long byteOffset) throws UnsupportedMessageException, InvalidBufferOffsetException {
            while (true) {
                CachedDispatch current = this;
                do {
                    InteropLibrary thisLibrary;
                    if ((thisLibrary = current.library) == null || !thisLibrary.accepts(receiver_)) continue;
                    return thisLibrary.readBufferFloat(receiver_, order, byteOffset);
                } while ((current = current.next) != null);
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.specialize(receiver_);
            }
        }

        @Override
        @ExplodeLoop
        public void writeBufferFloat(Object receiver_, ByteOrder order, long byteOffset, float value2) throws UnsupportedMessageException, InvalidBufferOffsetException {
            while (true) {
                CachedDispatch current = this;
                do {
                    InteropLibrary thisLibrary;
                    if ((thisLibrary = current.library) == null || !thisLibrary.accepts(receiver_)) continue;
                    thisLibrary.writeBufferFloat(receiver_, order, byteOffset, value2);
                    return;
                } while ((current = current.next) != null);
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.specialize(receiver_);
            }
        }

        @Override
        @ExplodeLoop
        public double readBufferDouble(Object receiver_, ByteOrder order, long byteOffset) throws UnsupportedMessageException, InvalidBufferOffsetException {
            while (true) {
                CachedDispatch current = this;
                do {
                    InteropLibrary thisLibrary;
                    if ((thisLibrary = current.library) == null || !thisLibrary.accepts(receiver_)) continue;
                    return thisLibrary.readBufferDouble(receiver_, order, byteOffset);
                } while ((current = current.next) != null);
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.specialize(receiver_);
            }
        }

        @Override
        @ExplodeLoop
        public void writeBufferDouble(Object receiver_, ByteOrder order, long byteOffset, double value2) throws UnsupportedMessageException, InvalidBufferOffsetException {
            while (true) {
                CachedDispatch current = this;
                do {
                    InteropLibrary thisLibrary;
                    if ((thisLibrary = current.library) == null || !thisLibrary.accepts(receiver_)) continue;
                    thisLibrary.writeBufferDouble(receiver_, order, byteOffset, value2);
                    return;
                } while ((current = current.next) != null);
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.specialize(receiver_);
            }
        }

        @Override
        @ExplodeLoop
        public boolean isPointer(Object receiver_) {
            while (true) {
                CachedDispatch current = this;
                do {
                    InteropLibrary thisLibrary;
                    if ((thisLibrary = current.library) == null || !thisLibrary.accepts(receiver_)) continue;
                    return thisLibrary.isPointer(receiver_);
                } while ((current = current.next) != null);
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.specialize(receiver_);
            }
        }

        @Override
        @ExplodeLoop
        public long asPointer(Object receiver_) throws UnsupportedMessageException {
            while (true) {
                CachedDispatch current = this;
                do {
                    InteropLibrary thisLibrary;
                    if ((thisLibrary = current.library) == null || !thisLibrary.accepts(receiver_)) continue;
                    return thisLibrary.asPointer(receiver_);
                } while ((current = current.next) != null);
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.specialize(receiver_);
            }
        }

        @Override
        @ExplodeLoop
        public void toNative(Object receiver_) {
            while (true) {
                CachedDispatch current = this;
                do {
                    InteropLibrary thisLibrary;
                    if ((thisLibrary = current.library) == null || !thisLibrary.accepts(receiver_)) continue;
                    thisLibrary.toNative(receiver_);
                    return;
                } while ((current = current.next) != null);
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.specialize(receiver_);
            }
        }

        @Override
        @ExplodeLoop
        public Instant asInstant(Object receiver_) throws UnsupportedMessageException {
            while (true) {
                CachedDispatch current = this;
                do {
                    InteropLibrary thisLibrary;
                    if ((thisLibrary = current.library) == null || !thisLibrary.accepts(receiver_)) continue;
                    return thisLibrary.asInstant(receiver_);
                } while ((current = current.next) != null);
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.specialize(receiver_);
            }
        }

        @Override
        @ExplodeLoop
        public boolean isTimeZone(Object receiver_) {
            while (true) {
                CachedDispatch current = this;
                do {
                    InteropLibrary thisLibrary;
                    if ((thisLibrary = current.library) == null || !thisLibrary.accepts(receiver_)) continue;
                    return thisLibrary.isTimeZone(receiver_);
                } while ((current = current.next) != null);
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.specialize(receiver_);
            }
        }

        @Override
        @ExplodeLoop
        public ZoneId asTimeZone(Object receiver_) throws UnsupportedMessageException {
            while (true) {
                CachedDispatch current = this;
                do {
                    InteropLibrary thisLibrary;
                    if ((thisLibrary = current.library) == null || !thisLibrary.accepts(receiver_)) continue;
                    return thisLibrary.asTimeZone(receiver_);
                } while ((current = current.next) != null);
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.specialize(receiver_);
            }
        }

        @Override
        @ExplodeLoop
        public boolean isDate(Object receiver_) {
            while (true) {
                CachedDispatch current = this;
                do {
                    InteropLibrary thisLibrary;
                    if ((thisLibrary = current.library) == null || !thisLibrary.accepts(receiver_)) continue;
                    return thisLibrary.isDate(receiver_);
                } while ((current = current.next) != null);
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.specialize(receiver_);
            }
        }

        @Override
        @ExplodeLoop
        public LocalDate asDate(Object receiver_) throws UnsupportedMessageException {
            while (true) {
                CachedDispatch current = this;
                do {
                    InteropLibrary thisLibrary;
                    if ((thisLibrary = current.library) == null || !thisLibrary.accepts(receiver_)) continue;
                    return thisLibrary.asDate(receiver_);
                } while ((current = current.next) != null);
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.specialize(receiver_);
            }
        }

        @Override
        @ExplodeLoop
        public boolean isTime(Object receiver_) {
            while (true) {
                CachedDispatch current = this;
                do {
                    InteropLibrary thisLibrary;
                    if ((thisLibrary = current.library) == null || !thisLibrary.accepts(receiver_)) continue;
                    return thisLibrary.isTime(receiver_);
                } while ((current = current.next) != null);
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.specialize(receiver_);
            }
        }

        @Override
        @ExplodeLoop
        public LocalTime asTime(Object receiver_) throws UnsupportedMessageException {
            while (true) {
                CachedDispatch current = this;
                do {
                    InteropLibrary thisLibrary;
                    if ((thisLibrary = current.library) == null || !thisLibrary.accepts(receiver_)) continue;
                    return thisLibrary.asTime(receiver_);
                } while ((current = current.next) != null);
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.specialize(receiver_);
            }
        }

        @Override
        @ExplodeLoop
        public boolean isDuration(Object receiver_) {
            while (true) {
                CachedDispatch current = this;
                do {
                    InteropLibrary thisLibrary;
                    if ((thisLibrary = current.library) == null || !thisLibrary.accepts(receiver_)) continue;
                    return thisLibrary.isDuration(receiver_);
                } while ((current = current.next) != null);
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.specialize(receiver_);
            }
        }

        @Override
        @ExplodeLoop
        public Duration asDuration(Object receiver_) throws UnsupportedMessageException {
            while (true) {
                CachedDispatch current = this;
                do {
                    InteropLibrary thisLibrary;
                    if ((thisLibrary = current.library) == null || !thisLibrary.accepts(receiver_)) continue;
                    return thisLibrary.asDuration(receiver_);
                } while ((current = current.next) != null);
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.specialize(receiver_);
            }
        }

        @Override
        @ExplodeLoop
        public boolean isException(Object receiver_) {
            while (true) {
                CachedDispatch current = this;
                do {
                    InteropLibrary thisLibrary;
                    if ((thisLibrary = current.library) == null || !thisLibrary.accepts(receiver_)) continue;
                    return thisLibrary.isException(receiver_);
                } while ((current = current.next) != null);
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.specialize(receiver_);
            }
        }

        @Override
        @ExplodeLoop
        public RuntimeException throwException(Object receiver_) throws UnsupportedMessageException {
            while (true) {
                CachedDispatch current = this;
                do {
                    InteropLibrary thisLibrary;
                    if ((thisLibrary = current.library) == null || !thisLibrary.accepts(receiver_)) continue;
                    return thisLibrary.throwException(receiver_);
                } while ((current = current.next) != null);
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.specialize(receiver_);
            }
        }

        @Override
        @ExplodeLoop
        public ExceptionType getExceptionType(Object receiver_) throws UnsupportedMessageException {
            while (true) {
                CachedDispatch current = this;
                do {
                    InteropLibrary thisLibrary;
                    if ((thisLibrary = current.library) == null || !thisLibrary.accepts(receiver_)) continue;
                    return thisLibrary.getExceptionType(receiver_);
                } while ((current = current.next) != null);
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.specialize(receiver_);
            }
        }

        @Override
        @ExplodeLoop
        public boolean isExceptionIncompleteSource(Object receiver_) throws UnsupportedMessageException {
            while (true) {
                CachedDispatch current = this;
                do {
                    InteropLibrary thisLibrary;
                    if ((thisLibrary = current.library) == null || !thisLibrary.accepts(receiver_)) continue;
                    return thisLibrary.isExceptionIncompleteSource(receiver_);
                } while ((current = current.next) != null);
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.specialize(receiver_);
            }
        }

        @Override
        @ExplodeLoop
        public int getExceptionExitStatus(Object receiver_) throws UnsupportedMessageException {
            while (true) {
                CachedDispatch current = this;
                do {
                    InteropLibrary thisLibrary;
                    if ((thisLibrary = current.library) == null || !thisLibrary.accepts(receiver_)) continue;
                    return thisLibrary.getExceptionExitStatus(receiver_);
                } while ((current = current.next) != null);
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.specialize(receiver_);
            }
        }

        @Override
        @ExplodeLoop
        public boolean hasExceptionCause(Object receiver_) {
            while (true) {
                CachedDispatch current = this;
                do {
                    InteropLibrary thisLibrary;
                    if ((thisLibrary = current.library) == null || !thisLibrary.accepts(receiver_)) continue;
                    return thisLibrary.hasExceptionCause(receiver_);
                } while ((current = current.next) != null);
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.specialize(receiver_);
            }
        }

        @Override
        @ExplodeLoop
        public Object getExceptionCause(Object receiver_) throws UnsupportedMessageException {
            while (true) {
                CachedDispatch current = this;
                do {
                    InteropLibrary thisLibrary;
                    if ((thisLibrary = current.library) == null || !thisLibrary.accepts(receiver_)) continue;
                    return thisLibrary.getExceptionCause(receiver_);
                } while ((current = current.next) != null);
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.specialize(receiver_);
            }
        }

        @Override
        @ExplodeLoop
        public boolean hasExceptionMessage(Object receiver_) {
            while (true) {
                CachedDispatch current = this;
                do {
                    InteropLibrary thisLibrary;
                    if ((thisLibrary = current.library) == null || !thisLibrary.accepts(receiver_)) continue;
                    return thisLibrary.hasExceptionMessage(receiver_);
                } while ((current = current.next) != null);
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.specialize(receiver_);
            }
        }

        @Override
        @ExplodeLoop
        public Object getExceptionMessage(Object receiver_) throws UnsupportedMessageException {
            while (true) {
                CachedDispatch current = this;
                do {
                    InteropLibrary thisLibrary;
                    if ((thisLibrary = current.library) == null || !thisLibrary.accepts(receiver_)) continue;
                    return thisLibrary.getExceptionMessage(receiver_);
                } while ((current = current.next) != null);
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.specialize(receiver_);
            }
        }

        @Override
        @ExplodeLoop
        public boolean hasExceptionStackTrace(Object receiver_) {
            while (true) {
                CachedDispatch current = this;
                do {
                    InteropLibrary thisLibrary;
                    if ((thisLibrary = current.library) == null || !thisLibrary.accepts(receiver_)) continue;
                    return thisLibrary.hasExceptionStackTrace(receiver_);
                } while ((current = current.next) != null);
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.specialize(receiver_);
            }
        }

        @Override
        @ExplodeLoop
        public Object getExceptionStackTrace(Object receiver_) throws UnsupportedMessageException {
            while (true) {
                CachedDispatch current = this;
                do {
                    InteropLibrary thisLibrary;
                    if ((thisLibrary = current.library) == null || !thisLibrary.accepts(receiver_)) continue;
                    return thisLibrary.getExceptionStackTrace(receiver_);
                } while ((current = current.next) != null);
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.specialize(receiver_);
            }
        }

        @Override
        @ExplodeLoop
        public boolean hasIterator(Object receiver_) {
            while (true) {
                CachedDispatch current = this;
                do {
                    InteropLibrary thisLibrary;
                    if ((thisLibrary = current.library) == null || !thisLibrary.accepts(receiver_)) continue;
                    return thisLibrary.hasIterator(receiver_);
                } while ((current = current.next) != null);
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.specialize(receiver_);
            }
        }

        @Override
        @ExplodeLoop
        public Object getIterator(Object receiver_) throws UnsupportedMessageException {
            while (true) {
                CachedDispatch current = this;
                do {
                    InteropLibrary thisLibrary;
                    if ((thisLibrary = current.library) == null || !thisLibrary.accepts(receiver_)) continue;
                    return thisLibrary.getIterator(receiver_);
                } while ((current = current.next) != null);
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.specialize(receiver_);
            }
        }

        @Override
        @ExplodeLoop
        public boolean isIterator(Object receiver_) {
            while (true) {
                CachedDispatch current = this;
                do {
                    InteropLibrary thisLibrary;
                    if ((thisLibrary = current.library) == null || !thisLibrary.accepts(receiver_)) continue;
                    return thisLibrary.isIterator(receiver_);
                } while ((current = current.next) != null);
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.specialize(receiver_);
            }
        }

        @Override
        @ExplodeLoop
        public boolean hasIteratorNextElement(Object receiver_) throws UnsupportedMessageException {
            while (true) {
                CachedDispatch current = this;
                do {
                    InteropLibrary thisLibrary;
                    if ((thisLibrary = current.library) == null || !thisLibrary.accepts(receiver_)) continue;
                    return thisLibrary.hasIteratorNextElement(receiver_);
                } while ((current = current.next) != null);
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.specialize(receiver_);
            }
        }

        @Override
        @ExplodeLoop
        public Object getIteratorNextElement(Object receiver_) throws UnsupportedMessageException, StopIterationException {
            while (true) {
                CachedDispatch current = this;
                do {
                    InteropLibrary thisLibrary;
                    if ((thisLibrary = current.library) == null || !thisLibrary.accepts(receiver_)) continue;
                    return thisLibrary.getIteratorNextElement(receiver_);
                } while ((current = current.next) != null);
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.specialize(receiver_);
            }
        }

        @Override
        @ExplodeLoop
        public boolean hasSourceLocation(Object receiver_) {
            while (true) {
                CachedDispatch current = this;
                do {
                    InteropLibrary thisLibrary;
                    if ((thisLibrary = current.library) == null || !thisLibrary.accepts(receiver_)) continue;
                    return thisLibrary.hasSourceLocation(receiver_);
                } while ((current = current.next) != null);
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.specialize(receiver_);
            }
        }

        @Override
        @ExplodeLoop
        public SourceSection getSourceLocation(Object receiver_) throws UnsupportedMessageException {
            while (true) {
                CachedDispatch current = this;
                do {
                    InteropLibrary thisLibrary;
                    if ((thisLibrary = current.library) == null || !thisLibrary.accepts(receiver_)) continue;
                    return thisLibrary.getSourceLocation(receiver_);
                } while ((current = current.next) != null);
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.specialize(receiver_);
            }
        }

        @Override
        @ExplodeLoop
        public boolean hasLanguage(Object receiver_) {
            while (true) {
                CachedDispatch current = this;
                do {
                    InteropLibrary thisLibrary;
                    if ((thisLibrary = current.library) == null || !thisLibrary.accepts(receiver_)) continue;
                    return thisLibrary.hasLanguage(receiver_);
                } while ((current = current.next) != null);
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.specialize(receiver_);
            }
        }

        @Override
        @ExplodeLoop
        public Class<? extends TruffleLanguage<?>> getLanguage(Object receiver_) throws UnsupportedMessageException {
            while (true) {
                CachedDispatch current = this;
                do {
                    InteropLibrary thisLibrary;
                    if ((thisLibrary = current.library) == null || !thisLibrary.accepts(receiver_)) continue;
                    return thisLibrary.getLanguage(receiver_);
                } while ((current = current.next) != null);
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.specialize(receiver_);
            }
        }

        @Override
        @ExplodeLoop
        public boolean hasMetaObject(Object receiver_) {
            while (true) {
                CachedDispatch current = this;
                do {
                    InteropLibrary thisLibrary;
                    if ((thisLibrary = current.library) == null || !thisLibrary.accepts(receiver_)) continue;
                    return thisLibrary.hasMetaObject(receiver_);
                } while ((current = current.next) != null);
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.specialize(receiver_);
            }
        }

        @Override
        @ExplodeLoop
        public Object getMetaObject(Object receiver_) throws UnsupportedMessageException {
            while (true) {
                CachedDispatch current = this;
                do {
                    InteropLibrary thisLibrary;
                    if ((thisLibrary = current.library) == null || !thisLibrary.accepts(receiver_)) continue;
                    return thisLibrary.getMetaObject(receiver_);
                } while ((current = current.next) != null);
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.specialize(receiver_);
            }
        }

        @Override
        @ExplodeLoop
        public Object toDisplayString(Object receiver_, boolean allowSideEffects) {
            while (true) {
                CachedDispatch current = this;
                do {
                    InteropLibrary thisLibrary;
                    if ((thisLibrary = current.library) == null || !thisLibrary.accepts(receiver_)) continue;
                    return thisLibrary.toDisplayString(receiver_, allowSideEffects);
                } while ((current = current.next) != null);
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.specialize(receiver_);
            }
        }

        @Override
        @ExplodeLoop
        public boolean isMetaObject(Object receiver_) {
            while (true) {
                CachedDispatch current = this;
                do {
                    InteropLibrary thisLibrary;
                    if ((thisLibrary = current.library) == null || !thisLibrary.accepts(receiver_)) continue;
                    return thisLibrary.isMetaObject(receiver_);
                } while ((current = current.next) != null);
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.specialize(receiver_);
            }
        }

        @Override
        @ExplodeLoop
        public Object getMetaQualifiedName(Object receiver_) throws UnsupportedMessageException {
            while (true) {
                CachedDispatch current = this;
                do {
                    InteropLibrary thisLibrary;
                    if ((thisLibrary = current.library) == null || !thisLibrary.accepts(receiver_)) continue;
                    return thisLibrary.getMetaQualifiedName(receiver_);
                } while ((current = current.next) != null);
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.specialize(receiver_);
            }
        }

        @Override
        @ExplodeLoop
        public Object getMetaSimpleName(Object receiver_) throws UnsupportedMessageException {
            while (true) {
                CachedDispatch current = this;
                do {
                    InteropLibrary thisLibrary;
                    if ((thisLibrary = current.library) == null || !thisLibrary.accepts(receiver_)) continue;
                    return thisLibrary.getMetaSimpleName(receiver_);
                } while ((current = current.next) != null);
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.specialize(receiver_);
            }
        }

        @Override
        @ExplodeLoop
        public boolean isMetaInstance(Object receiver_, Object instance) throws UnsupportedMessageException {
            while (true) {
                CachedDispatch current = this;
                do {
                    InteropLibrary thisLibrary;
                    if ((thisLibrary = current.library) == null || !thisLibrary.accepts(receiver_)) continue;
                    return thisLibrary.isMetaInstance(receiver_, instance);
                } while ((current = current.next) != null);
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.specialize(receiver_);
            }
        }

        @Override
        @ExplodeLoop
        public boolean hasMetaParents(Object receiver_) {
            while (true) {
                CachedDispatch current = this;
                do {
                    InteropLibrary thisLibrary;
                    if ((thisLibrary = current.library) == null || !thisLibrary.accepts(receiver_)) continue;
                    return thisLibrary.hasMetaParents(receiver_);
                } while ((current = current.next) != null);
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.specialize(receiver_);
            }
        }

        @Override
        @ExplodeLoop
        public Object getMetaParents(Object receiver_) throws UnsupportedMessageException {
            while (true) {
                CachedDispatch current = this;
                do {
                    InteropLibrary thisLibrary;
                    if ((thisLibrary = current.library) == null || !thisLibrary.accepts(receiver_)) continue;
                    return thisLibrary.getMetaParents(receiver_);
                } while ((current = current.next) != null);
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.specialize(receiver_);
            }
        }

        @Override
        @ExplodeLoop
        protected TriState isIdenticalOrUndefined(Object receiver_, Object other) {
            while (true) {
                CachedDispatch current = this;
                do {
                    InteropLibrary thisLibrary;
                    if ((thisLibrary = current.library) == null || !thisLibrary.accepts(receiver_)) continue;
                    return thisLibrary.isIdenticalOrUndefined(receiver_, other);
                } while ((current = current.next) != null);
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.specialize(receiver_);
            }
        }

        @Override
        @ExplodeLoop
        public boolean isIdentical(Object receiver_, Object other, InteropLibrary otherInterop) {
            while (true) {
                CachedDispatch current = this;
                do {
                    InteropLibrary thisLibrary;
                    if ((thisLibrary = current.library) == null || !thisLibrary.accepts(receiver_)) continue;
                    return thisLibrary.isIdentical(receiver_, other, otherInterop);
                } while ((current = current.next) != null);
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.specialize(receiver_);
            }
        }

        @Override
        @ExplodeLoop
        public int identityHashCode(Object receiver_) throws UnsupportedMessageException {
            while (true) {
                CachedDispatch current = this;
                do {
                    InteropLibrary thisLibrary;
                    if ((thisLibrary = current.library) == null || !thisLibrary.accepts(receiver_)) continue;
                    return thisLibrary.identityHashCode(receiver_);
                } while ((current = current.next) != null);
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.specialize(receiver_);
            }
        }

        @Override
        @ExplodeLoop
        public boolean isScope(Object receiver_) {
            while (true) {
                CachedDispatch current = this;
                do {
                    InteropLibrary thisLibrary;
                    if ((thisLibrary = current.library) == null || !thisLibrary.accepts(receiver_)) continue;
                    return thisLibrary.isScope(receiver_);
                } while ((current = current.next) != null);
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.specialize(receiver_);
            }
        }

        @Override
        @ExplodeLoop
        public boolean hasScopeParent(Object receiver_) {
            while (true) {
                CachedDispatch current = this;
                do {
                    InteropLibrary thisLibrary;
                    if ((thisLibrary = current.library) == null || !thisLibrary.accepts(receiver_)) continue;
                    return thisLibrary.hasScopeParent(receiver_);
                } while ((current = current.next) != null);
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.specialize(receiver_);
            }
        }

        @Override
        @ExplodeLoop
        public Object getScopeParent(Object receiver_) throws UnsupportedMessageException {
            while (true) {
                CachedDispatch current = this;
                do {
                    InteropLibrary thisLibrary;
                    if ((thisLibrary = current.library) == null || !thisLibrary.accepts(receiver_)) continue;
                    return thisLibrary.getScopeParent(receiver_);
                } while ((current = current.next) != null);
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.specialize(receiver_);
            }
        }

        @Override
        public boolean accepts(Object receiver_) {
            return true;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private void specialize(Object receiver_) {
            Lock lock = this.getLock();
            lock.lock();
            try {
                CachedDispatch current = this;
                InteropLibrary thisLibrary = current.library;
                if (thisLibrary == null) {
                    this.library = this.insert((InteropLibrary)INSTANCE.create(receiver_));
                } else {
                    int count = 0;
                    do {
                        InteropLibrary currentLibrary;
                        if ((currentLibrary = current.library) != null && currentLibrary.accepts(receiver_)) {
                            return;
                        }
                        ++count;
                    } while ((current = current.next) != null);
                    if (count >= this.getLimit()) {
                        this.library = this.insert(new CachedToUncachedDispatch());
                        this.next = null;
                    } else {
                        this.next = this.insert(new CachedDispatchNext((InteropLibrary)INSTANCE.create(receiver_), this.next));
                    }
                }
            }
            finally {
                lock.unlock();
            }
        }
    }

    @GeneratedBy(value=InteropLibrary.class)
    private static final class CachedDispatchFirst
    extends CachedDispatch {
        private final int limit_;

        CachedDispatchFirst(InteropLibrary library, CachedDispatch next, int limit_) {
            super(library, next);
            this.limit_ = limit_;
        }

        @Override
        int getLimit() {
            return this.limit_;
        }

        @Override
        public NodeCost getCost() {
            if (this.library instanceof CachedToUncachedDispatch) {
                return NodeCost.MEGAMORPHIC;
            }
            CachedDispatch current = this;
            int count = 0;
            do {
                if (current.library == null) continue;
                ++count;
            } while ((current = current.next) != null);
            return NodeCost.fromCount(count);
        }
    }

    @GeneratedBy(value=InteropLibrary.class)
    private static final class CachedDispatchNext
    extends CachedDispatch {
        CachedDispatchNext(InteropLibrary library, CachedDispatch next) {
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

    @GeneratedBy(value=InteropLibrary.class)
    @DenyReplace
    private static final class UncachedDispatch
    extends InteropLibrary {
        private UncachedDispatch() {
        }

        @Override
        public NodeCost getCost() {
            return NodeCost.MEGAMORPHIC;
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public boolean isNull(Object receiver_) {
            return ((InteropLibrary)INSTANCE.getUncached(receiver_)).isNull(receiver_);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public boolean isBoolean(Object receiver_) {
            return ((InteropLibrary)INSTANCE.getUncached(receiver_)).isBoolean(receiver_);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public boolean asBoolean(Object receiver_) throws UnsupportedMessageException {
            return ((InteropLibrary)INSTANCE.getUncached(receiver_)).asBoolean(receiver_);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public boolean isExecutable(Object receiver_) {
            return ((InteropLibrary)INSTANCE.getUncached(receiver_)).isExecutable(receiver_);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public Object execute(Object receiver_, Object ... arguments) throws UnsupportedTypeException, ArityException, UnsupportedMessageException {
            return ((InteropLibrary)INSTANCE.getUncached(receiver_)).execute(receiver_, arguments);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public boolean hasExecutableName(Object receiver_) {
            return ((InteropLibrary)INSTANCE.getUncached(receiver_)).hasExecutableName(receiver_);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public Object getExecutableName(Object receiver_) throws UnsupportedMessageException {
            return ((InteropLibrary)INSTANCE.getUncached(receiver_)).getExecutableName(receiver_);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public boolean hasDeclaringMetaObject(Object receiver_) {
            return ((InteropLibrary)INSTANCE.getUncached(receiver_)).hasDeclaringMetaObject(receiver_);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public Object getDeclaringMetaObject(Object receiver_) throws UnsupportedMessageException {
            return ((InteropLibrary)INSTANCE.getUncached(receiver_)).getDeclaringMetaObject(receiver_);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public boolean isInstantiable(Object receiver_) {
            return ((InteropLibrary)INSTANCE.getUncached(receiver_)).isInstantiable(receiver_);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public Object instantiate(Object receiver_, Object ... arguments) throws UnsupportedTypeException, ArityException, UnsupportedMessageException {
            return ((InteropLibrary)INSTANCE.getUncached(receiver_)).instantiate(receiver_, arguments);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public boolean isString(Object receiver_) {
            return ((InteropLibrary)INSTANCE.getUncached(receiver_)).isString(receiver_);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public String asString(Object receiver_) throws UnsupportedMessageException {
            return ((InteropLibrary)INSTANCE.getUncached(receiver_)).asString(receiver_);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public TruffleString asTruffleString(Object receiver_) throws UnsupportedMessageException {
            return ((InteropLibrary)INSTANCE.getUncached(receiver_)).asTruffleString(receiver_);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public boolean isNumber(Object receiver_) {
            return ((InteropLibrary)INSTANCE.getUncached(receiver_)).isNumber(receiver_);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public boolean fitsInByte(Object receiver_) {
            return ((InteropLibrary)INSTANCE.getUncached(receiver_)).fitsInByte(receiver_);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public boolean fitsInShort(Object receiver_) {
            return ((InteropLibrary)INSTANCE.getUncached(receiver_)).fitsInShort(receiver_);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public boolean fitsInInt(Object receiver_) {
            return ((InteropLibrary)INSTANCE.getUncached(receiver_)).fitsInInt(receiver_);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public boolean fitsInLong(Object receiver_) {
            return ((InteropLibrary)INSTANCE.getUncached(receiver_)).fitsInLong(receiver_);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public boolean fitsInFloat(Object receiver_) {
            return ((InteropLibrary)INSTANCE.getUncached(receiver_)).fitsInFloat(receiver_);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public boolean fitsInDouble(Object receiver_) {
            return ((InteropLibrary)INSTANCE.getUncached(receiver_)).fitsInDouble(receiver_);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public byte asByte(Object receiver_) throws UnsupportedMessageException {
            return ((InteropLibrary)INSTANCE.getUncached(receiver_)).asByte(receiver_);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public short asShort(Object receiver_) throws UnsupportedMessageException {
            return ((InteropLibrary)INSTANCE.getUncached(receiver_)).asShort(receiver_);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public int asInt(Object receiver_) throws UnsupportedMessageException {
            return ((InteropLibrary)INSTANCE.getUncached(receiver_)).asInt(receiver_);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public long asLong(Object receiver_) throws UnsupportedMessageException {
            return ((InteropLibrary)INSTANCE.getUncached(receiver_)).asLong(receiver_);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public float asFloat(Object receiver_) throws UnsupportedMessageException {
            return ((InteropLibrary)INSTANCE.getUncached(receiver_)).asFloat(receiver_);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public double asDouble(Object receiver_) throws UnsupportedMessageException {
            return ((InteropLibrary)INSTANCE.getUncached(receiver_)).asDouble(receiver_);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public boolean hasMembers(Object receiver_) {
            return ((InteropLibrary)INSTANCE.getUncached(receiver_)).hasMembers(receiver_);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public Object getMembers(Object receiver_, boolean includeInternal) throws UnsupportedMessageException {
            return ((InteropLibrary)INSTANCE.getUncached(receiver_)).getMembers(receiver_, includeInternal);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public boolean isMemberReadable(Object receiver_, String member) {
            return ((InteropLibrary)INSTANCE.getUncached(receiver_)).isMemberReadable(receiver_, member);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public Object readMember(Object receiver_, String member) throws UnsupportedMessageException, UnknownIdentifierException {
            return ((InteropLibrary)INSTANCE.getUncached(receiver_)).readMember(receiver_, member);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public boolean isMemberModifiable(Object receiver_, String member) {
            return ((InteropLibrary)INSTANCE.getUncached(receiver_)).isMemberModifiable(receiver_, member);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public boolean isMemberInsertable(Object receiver_, String member) {
            return ((InteropLibrary)INSTANCE.getUncached(receiver_)).isMemberInsertable(receiver_, member);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public void writeMember(Object receiver_, String member, Object value2) throws UnsupportedMessageException, UnknownIdentifierException, UnsupportedTypeException {
            ((InteropLibrary)INSTANCE.getUncached(receiver_)).writeMember(receiver_, member, value2);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public boolean isMemberRemovable(Object receiver_, String member) {
            return ((InteropLibrary)INSTANCE.getUncached(receiver_)).isMemberRemovable(receiver_, member);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public void removeMember(Object receiver_, String member) throws UnsupportedMessageException, UnknownIdentifierException {
            ((InteropLibrary)INSTANCE.getUncached(receiver_)).removeMember(receiver_, member);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public boolean isMemberInvocable(Object receiver_, String member) {
            return ((InteropLibrary)INSTANCE.getUncached(receiver_)).isMemberInvocable(receiver_, member);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public Object invokeMember(Object receiver_, String member, Object ... arguments) throws UnsupportedMessageException, ArityException, UnknownIdentifierException, UnsupportedTypeException {
            return ((InteropLibrary)INSTANCE.getUncached(receiver_)).invokeMember(receiver_, member, arguments);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public boolean isMemberInternal(Object receiver_, String member) {
            return ((InteropLibrary)INSTANCE.getUncached(receiver_)).isMemberInternal(receiver_, member);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public boolean hasMemberReadSideEffects(Object receiver_, String member) {
            return ((InteropLibrary)INSTANCE.getUncached(receiver_)).hasMemberReadSideEffects(receiver_, member);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public boolean hasMemberWriteSideEffects(Object receiver_, String member) {
            return ((InteropLibrary)INSTANCE.getUncached(receiver_)).hasMemberWriteSideEffects(receiver_, member);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public boolean hasHashEntries(Object receiver_) {
            return ((InteropLibrary)INSTANCE.getUncached(receiver_)).hasHashEntries(receiver_);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public long getHashSize(Object receiver_) throws UnsupportedMessageException {
            return ((InteropLibrary)INSTANCE.getUncached(receiver_)).getHashSize(receiver_);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public boolean isHashEntryReadable(Object receiver_, Object key) {
            return ((InteropLibrary)INSTANCE.getUncached(receiver_)).isHashEntryReadable(receiver_, key);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public Object readHashValue(Object receiver_, Object key) throws UnsupportedMessageException, UnknownKeyException {
            return ((InteropLibrary)INSTANCE.getUncached(receiver_)).readHashValue(receiver_, key);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public Object readHashValueOrDefault(Object receiver_, Object key, Object defaultValue) throws UnsupportedMessageException {
            return ((InteropLibrary)INSTANCE.getUncached(receiver_)).readHashValueOrDefault(receiver_, key, defaultValue);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public boolean isHashEntryModifiable(Object receiver_, Object key) {
            return ((InteropLibrary)INSTANCE.getUncached(receiver_)).isHashEntryModifiable(receiver_, key);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public boolean isHashEntryInsertable(Object receiver_, Object key) {
            return ((InteropLibrary)INSTANCE.getUncached(receiver_)).isHashEntryInsertable(receiver_, key);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public boolean isHashEntryWritable(Object receiver_, Object key) {
            return ((InteropLibrary)INSTANCE.getUncached(receiver_)).isHashEntryWritable(receiver_, key);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public void writeHashEntry(Object receiver_, Object key, Object value2) throws UnsupportedMessageException, UnknownKeyException, UnsupportedTypeException {
            ((InteropLibrary)INSTANCE.getUncached(receiver_)).writeHashEntry(receiver_, key, value2);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public boolean isHashEntryRemovable(Object receiver_, Object key) {
            return ((InteropLibrary)INSTANCE.getUncached(receiver_)).isHashEntryRemovable(receiver_, key);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public void removeHashEntry(Object receiver_, Object key) throws UnsupportedMessageException, UnknownKeyException {
            ((InteropLibrary)INSTANCE.getUncached(receiver_)).removeHashEntry(receiver_, key);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public boolean isHashEntryExisting(Object receiver_, Object key) {
            return ((InteropLibrary)INSTANCE.getUncached(receiver_)).isHashEntryExisting(receiver_, key);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public Object getHashEntriesIterator(Object receiver_) throws UnsupportedMessageException {
            return ((InteropLibrary)INSTANCE.getUncached(receiver_)).getHashEntriesIterator(receiver_);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public Object getHashKeysIterator(Object receiver_) throws UnsupportedMessageException {
            return ((InteropLibrary)INSTANCE.getUncached(receiver_)).getHashKeysIterator(receiver_);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public Object getHashValuesIterator(Object receiver_) throws UnsupportedMessageException {
            return ((InteropLibrary)INSTANCE.getUncached(receiver_)).getHashValuesIterator(receiver_);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public boolean hasArrayElements(Object receiver_) {
            return ((InteropLibrary)INSTANCE.getUncached(receiver_)).hasArrayElements(receiver_);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public Object readArrayElement(Object receiver_, long index) throws UnsupportedMessageException, InvalidArrayIndexException {
            return ((InteropLibrary)INSTANCE.getUncached(receiver_)).readArrayElement(receiver_, index);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public long getArraySize(Object receiver_) throws UnsupportedMessageException {
            return ((InteropLibrary)INSTANCE.getUncached(receiver_)).getArraySize(receiver_);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public boolean isArrayElementReadable(Object receiver_, long index) {
            return ((InteropLibrary)INSTANCE.getUncached(receiver_)).isArrayElementReadable(receiver_, index);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public void writeArrayElement(Object receiver_, long index, Object value2) throws UnsupportedMessageException, UnsupportedTypeException, InvalidArrayIndexException {
            ((InteropLibrary)INSTANCE.getUncached(receiver_)).writeArrayElement(receiver_, index, value2);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public void removeArrayElement(Object receiver_, long index) throws UnsupportedMessageException, InvalidArrayIndexException {
            ((InteropLibrary)INSTANCE.getUncached(receiver_)).removeArrayElement(receiver_, index);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public boolean isArrayElementModifiable(Object receiver_, long index) {
            return ((InteropLibrary)INSTANCE.getUncached(receiver_)).isArrayElementModifiable(receiver_, index);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public boolean isArrayElementInsertable(Object receiver_, long index) {
            return ((InteropLibrary)INSTANCE.getUncached(receiver_)).isArrayElementInsertable(receiver_, index);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public boolean isArrayElementRemovable(Object receiver_, long index) {
            return ((InteropLibrary)INSTANCE.getUncached(receiver_)).isArrayElementRemovable(receiver_, index);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public boolean hasBufferElements(Object receiver_) {
            return ((InteropLibrary)INSTANCE.getUncached(receiver_)).hasBufferElements(receiver_);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public boolean isBufferWritable(Object receiver_) throws UnsupportedMessageException {
            return ((InteropLibrary)INSTANCE.getUncached(receiver_)).isBufferWritable(receiver_);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public long getBufferSize(Object receiver_) throws UnsupportedMessageException {
            return ((InteropLibrary)INSTANCE.getUncached(receiver_)).getBufferSize(receiver_);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public byte readBufferByte(Object receiver_, long byteOffset) throws UnsupportedMessageException, InvalidBufferOffsetException {
            return ((InteropLibrary)INSTANCE.getUncached(receiver_)).readBufferByte(receiver_, byteOffset);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public void writeBufferByte(Object receiver_, long byteOffset, byte value2) throws UnsupportedMessageException, InvalidBufferOffsetException {
            ((InteropLibrary)INSTANCE.getUncached(receiver_)).writeBufferByte(receiver_, byteOffset, value2);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public short readBufferShort(Object receiver_, ByteOrder order, long byteOffset) throws UnsupportedMessageException, InvalidBufferOffsetException {
            return ((InteropLibrary)INSTANCE.getUncached(receiver_)).readBufferShort(receiver_, order, byteOffset);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public void writeBufferShort(Object receiver_, ByteOrder order, long byteOffset, short value2) throws UnsupportedMessageException, InvalidBufferOffsetException {
            ((InteropLibrary)INSTANCE.getUncached(receiver_)).writeBufferShort(receiver_, order, byteOffset, value2);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public int readBufferInt(Object receiver_, ByteOrder order, long byteOffset) throws UnsupportedMessageException, InvalidBufferOffsetException {
            return ((InteropLibrary)INSTANCE.getUncached(receiver_)).readBufferInt(receiver_, order, byteOffset);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public void writeBufferInt(Object receiver_, ByteOrder order, long byteOffset, int value2) throws UnsupportedMessageException, InvalidBufferOffsetException {
            ((InteropLibrary)INSTANCE.getUncached(receiver_)).writeBufferInt(receiver_, order, byteOffset, value2);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public long readBufferLong(Object receiver_, ByteOrder order, long byteOffset) throws UnsupportedMessageException, InvalidBufferOffsetException {
            return ((InteropLibrary)INSTANCE.getUncached(receiver_)).readBufferLong(receiver_, order, byteOffset);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public void writeBufferLong(Object receiver_, ByteOrder order, long byteOffset, long value2) throws UnsupportedMessageException, InvalidBufferOffsetException {
            ((InteropLibrary)INSTANCE.getUncached(receiver_)).writeBufferLong(receiver_, order, byteOffset, value2);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public float readBufferFloat(Object receiver_, ByteOrder order, long byteOffset) throws UnsupportedMessageException, InvalidBufferOffsetException {
            return ((InteropLibrary)INSTANCE.getUncached(receiver_)).readBufferFloat(receiver_, order, byteOffset);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public void writeBufferFloat(Object receiver_, ByteOrder order, long byteOffset, float value2) throws UnsupportedMessageException, InvalidBufferOffsetException {
            ((InteropLibrary)INSTANCE.getUncached(receiver_)).writeBufferFloat(receiver_, order, byteOffset, value2);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public double readBufferDouble(Object receiver_, ByteOrder order, long byteOffset) throws UnsupportedMessageException, InvalidBufferOffsetException {
            return ((InteropLibrary)INSTANCE.getUncached(receiver_)).readBufferDouble(receiver_, order, byteOffset);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public void writeBufferDouble(Object receiver_, ByteOrder order, long byteOffset, double value2) throws UnsupportedMessageException, InvalidBufferOffsetException {
            ((InteropLibrary)INSTANCE.getUncached(receiver_)).writeBufferDouble(receiver_, order, byteOffset, value2);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public boolean isPointer(Object receiver_) {
            return ((InteropLibrary)INSTANCE.getUncached(receiver_)).isPointer(receiver_);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public long asPointer(Object receiver_) throws UnsupportedMessageException {
            return ((InteropLibrary)INSTANCE.getUncached(receiver_)).asPointer(receiver_);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public void toNative(Object receiver_) {
            ((InteropLibrary)INSTANCE.getUncached(receiver_)).toNative(receiver_);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public Instant asInstant(Object receiver_) throws UnsupportedMessageException {
            return ((InteropLibrary)INSTANCE.getUncached(receiver_)).asInstant(receiver_);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public boolean isTimeZone(Object receiver_) {
            return ((InteropLibrary)INSTANCE.getUncached(receiver_)).isTimeZone(receiver_);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public ZoneId asTimeZone(Object receiver_) throws UnsupportedMessageException {
            return ((InteropLibrary)INSTANCE.getUncached(receiver_)).asTimeZone(receiver_);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public boolean isDate(Object receiver_) {
            return ((InteropLibrary)INSTANCE.getUncached(receiver_)).isDate(receiver_);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public LocalDate asDate(Object receiver_) throws UnsupportedMessageException {
            return ((InteropLibrary)INSTANCE.getUncached(receiver_)).asDate(receiver_);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public boolean isTime(Object receiver_) {
            return ((InteropLibrary)INSTANCE.getUncached(receiver_)).isTime(receiver_);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public LocalTime asTime(Object receiver_) throws UnsupportedMessageException {
            return ((InteropLibrary)INSTANCE.getUncached(receiver_)).asTime(receiver_);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public boolean isDuration(Object receiver_) {
            return ((InteropLibrary)INSTANCE.getUncached(receiver_)).isDuration(receiver_);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public Duration asDuration(Object receiver_) throws UnsupportedMessageException {
            return ((InteropLibrary)INSTANCE.getUncached(receiver_)).asDuration(receiver_);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public boolean isException(Object receiver_) {
            return ((InteropLibrary)INSTANCE.getUncached(receiver_)).isException(receiver_);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public RuntimeException throwException(Object receiver_) throws UnsupportedMessageException {
            return ((InteropLibrary)INSTANCE.getUncached(receiver_)).throwException(receiver_);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public ExceptionType getExceptionType(Object receiver_) throws UnsupportedMessageException {
            return ((InteropLibrary)INSTANCE.getUncached(receiver_)).getExceptionType(receiver_);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public boolean isExceptionIncompleteSource(Object receiver_) throws UnsupportedMessageException {
            return ((InteropLibrary)INSTANCE.getUncached(receiver_)).isExceptionIncompleteSource(receiver_);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public int getExceptionExitStatus(Object receiver_) throws UnsupportedMessageException {
            return ((InteropLibrary)INSTANCE.getUncached(receiver_)).getExceptionExitStatus(receiver_);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public boolean hasExceptionCause(Object receiver_) {
            return ((InteropLibrary)INSTANCE.getUncached(receiver_)).hasExceptionCause(receiver_);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public Object getExceptionCause(Object receiver_) throws UnsupportedMessageException {
            return ((InteropLibrary)INSTANCE.getUncached(receiver_)).getExceptionCause(receiver_);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public boolean hasExceptionMessage(Object receiver_) {
            return ((InteropLibrary)INSTANCE.getUncached(receiver_)).hasExceptionMessage(receiver_);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public Object getExceptionMessage(Object receiver_) throws UnsupportedMessageException {
            return ((InteropLibrary)INSTANCE.getUncached(receiver_)).getExceptionMessage(receiver_);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public boolean hasExceptionStackTrace(Object receiver_) {
            return ((InteropLibrary)INSTANCE.getUncached(receiver_)).hasExceptionStackTrace(receiver_);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public Object getExceptionStackTrace(Object receiver_) throws UnsupportedMessageException {
            return ((InteropLibrary)INSTANCE.getUncached(receiver_)).getExceptionStackTrace(receiver_);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public boolean hasIterator(Object receiver_) {
            return ((InteropLibrary)INSTANCE.getUncached(receiver_)).hasIterator(receiver_);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public Object getIterator(Object receiver_) throws UnsupportedMessageException {
            return ((InteropLibrary)INSTANCE.getUncached(receiver_)).getIterator(receiver_);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public boolean isIterator(Object receiver_) {
            return ((InteropLibrary)INSTANCE.getUncached(receiver_)).isIterator(receiver_);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public boolean hasIteratorNextElement(Object receiver_) throws UnsupportedMessageException {
            return ((InteropLibrary)INSTANCE.getUncached(receiver_)).hasIteratorNextElement(receiver_);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public Object getIteratorNextElement(Object receiver_) throws UnsupportedMessageException, StopIterationException {
            return ((InteropLibrary)INSTANCE.getUncached(receiver_)).getIteratorNextElement(receiver_);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public boolean hasSourceLocation(Object receiver_) {
            return ((InteropLibrary)INSTANCE.getUncached(receiver_)).hasSourceLocation(receiver_);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public SourceSection getSourceLocation(Object receiver_) throws UnsupportedMessageException {
            return ((InteropLibrary)INSTANCE.getUncached(receiver_)).getSourceLocation(receiver_);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public boolean hasLanguage(Object receiver_) {
            return ((InteropLibrary)INSTANCE.getUncached(receiver_)).hasLanguage(receiver_);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public Class<? extends TruffleLanguage<?>> getLanguage(Object receiver_) throws UnsupportedMessageException {
            return ((InteropLibrary)INSTANCE.getUncached(receiver_)).getLanguage(receiver_);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public boolean hasMetaObject(Object receiver_) {
            return ((InteropLibrary)INSTANCE.getUncached(receiver_)).hasMetaObject(receiver_);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public Object getMetaObject(Object receiver_) throws UnsupportedMessageException {
            return ((InteropLibrary)INSTANCE.getUncached(receiver_)).getMetaObject(receiver_);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public Object toDisplayString(Object receiver_, boolean allowSideEffects) {
            return ((InteropLibrary)INSTANCE.getUncached(receiver_)).toDisplayString(receiver_, allowSideEffects);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public boolean isMetaObject(Object receiver_) {
            return ((InteropLibrary)INSTANCE.getUncached(receiver_)).isMetaObject(receiver_);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public Object getMetaQualifiedName(Object receiver_) throws UnsupportedMessageException {
            return ((InteropLibrary)INSTANCE.getUncached(receiver_)).getMetaQualifiedName(receiver_);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public Object getMetaSimpleName(Object receiver_) throws UnsupportedMessageException {
            return ((InteropLibrary)INSTANCE.getUncached(receiver_)).getMetaSimpleName(receiver_);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public boolean isMetaInstance(Object receiver_, Object instance) throws UnsupportedMessageException {
            return ((InteropLibrary)INSTANCE.getUncached(receiver_)).isMetaInstance(receiver_, instance);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public boolean hasMetaParents(Object receiver_) {
            return ((InteropLibrary)INSTANCE.getUncached(receiver_)).hasMetaParents(receiver_);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public Object getMetaParents(Object receiver_) throws UnsupportedMessageException {
            return ((InteropLibrary)INSTANCE.getUncached(receiver_)).getMetaParents(receiver_);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        protected TriState isIdenticalOrUndefined(Object receiver_, Object other) {
            return ((InteropLibrary)INSTANCE.getUncached(receiver_)).isIdenticalOrUndefined(receiver_, other);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public boolean isIdentical(Object receiver_, Object other, InteropLibrary otherInterop) {
            return ((InteropLibrary)INSTANCE.getUncached(receiver_)).isIdentical(receiver_, other, otherInterop);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public int identityHashCode(Object receiver_) throws UnsupportedMessageException {
            return ((InteropLibrary)INSTANCE.getUncached(receiver_)).identityHashCode(receiver_);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public boolean isScope(Object receiver_) {
            return ((InteropLibrary)INSTANCE.getUncached(receiver_)).isScope(receiver_);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public boolean hasScopeParent(Object receiver_) {
            return ((InteropLibrary)INSTANCE.getUncached(receiver_)).hasScopeParent(receiver_);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public Object getScopeParent(Object receiver_) throws UnsupportedMessageException {
            return ((InteropLibrary)INSTANCE.getUncached(receiver_)).getScopeParent(receiver_);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public boolean accepts(Object receiver_) {
            return true;
        }

        @Override
        public boolean isAdoptable() {
            return false;
        }
    }

    @GeneratedBy(value=InteropLibrary.class)
    private static final class CachedToUncachedDispatch
    extends InteropLibrary {
        private CachedToUncachedDispatch() {
        }

        @Override
        public NodeCost getCost() {
            return NodeCost.MEGAMORPHIC;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @CompilerDirectives.TruffleBoundary
        public boolean isNull(Object receiver_) {
            assert (this.assertAdopted());
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this.getParent());
            try {
                boolean bl = ((InteropLibrary)INSTANCE.getUncached(receiver_)).isNull(receiver_);
                return bl;
            }
            finally {
                encapsulating_.set(prev_);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @CompilerDirectives.TruffleBoundary
        public boolean isBoolean(Object receiver_) {
            assert (this.assertAdopted());
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this.getParent());
            try {
                boolean bl = ((InteropLibrary)INSTANCE.getUncached(receiver_)).isBoolean(receiver_);
                return bl;
            }
            finally {
                encapsulating_.set(prev_);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @CompilerDirectives.TruffleBoundary
        public boolean asBoolean(Object receiver_) throws UnsupportedMessageException {
            assert (this.assertAdopted());
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this.getParent());
            try {
                boolean bl = ((InteropLibrary)INSTANCE.getUncached(receiver_)).asBoolean(receiver_);
                return bl;
            }
            finally {
                encapsulating_.set(prev_);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @CompilerDirectives.TruffleBoundary
        public boolean isExecutable(Object receiver_) {
            assert (this.assertAdopted());
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this.getParent());
            try {
                boolean bl = ((InteropLibrary)INSTANCE.getUncached(receiver_)).isExecutable(receiver_);
                return bl;
            }
            finally {
                encapsulating_.set(prev_);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @CompilerDirectives.TruffleBoundary
        public Object execute(Object receiver_, Object ... arguments) throws UnsupportedTypeException, ArityException, UnsupportedMessageException {
            assert (this.assertAdopted());
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this.getParent());
            try {
                Object object = ((InteropLibrary)INSTANCE.getUncached(receiver_)).execute(receiver_, arguments);
                return object;
            }
            finally {
                encapsulating_.set(prev_);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @CompilerDirectives.TruffleBoundary
        public boolean hasExecutableName(Object receiver_) {
            assert (this.assertAdopted());
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this.getParent());
            try {
                boolean bl = ((InteropLibrary)INSTANCE.getUncached(receiver_)).hasExecutableName(receiver_);
                return bl;
            }
            finally {
                encapsulating_.set(prev_);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @CompilerDirectives.TruffleBoundary
        public Object getExecutableName(Object receiver_) throws UnsupportedMessageException {
            assert (this.assertAdopted());
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this.getParent());
            try {
                Object object = ((InteropLibrary)INSTANCE.getUncached(receiver_)).getExecutableName(receiver_);
                return object;
            }
            finally {
                encapsulating_.set(prev_);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @CompilerDirectives.TruffleBoundary
        public boolean hasDeclaringMetaObject(Object receiver_) {
            assert (this.assertAdopted());
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this.getParent());
            try {
                boolean bl = ((InteropLibrary)INSTANCE.getUncached(receiver_)).hasDeclaringMetaObject(receiver_);
                return bl;
            }
            finally {
                encapsulating_.set(prev_);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @CompilerDirectives.TruffleBoundary
        public Object getDeclaringMetaObject(Object receiver_) throws UnsupportedMessageException {
            assert (this.assertAdopted());
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this.getParent());
            try {
                Object object = ((InteropLibrary)INSTANCE.getUncached(receiver_)).getDeclaringMetaObject(receiver_);
                return object;
            }
            finally {
                encapsulating_.set(prev_);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @CompilerDirectives.TruffleBoundary
        public boolean isInstantiable(Object receiver_) {
            assert (this.assertAdopted());
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this.getParent());
            try {
                boolean bl = ((InteropLibrary)INSTANCE.getUncached(receiver_)).isInstantiable(receiver_);
                return bl;
            }
            finally {
                encapsulating_.set(prev_);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @CompilerDirectives.TruffleBoundary
        public Object instantiate(Object receiver_, Object ... arguments) throws UnsupportedTypeException, ArityException, UnsupportedMessageException {
            assert (this.assertAdopted());
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this.getParent());
            try {
                Object object = ((InteropLibrary)INSTANCE.getUncached(receiver_)).instantiate(receiver_, arguments);
                return object;
            }
            finally {
                encapsulating_.set(prev_);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @CompilerDirectives.TruffleBoundary
        public boolean isString(Object receiver_) {
            assert (this.assertAdopted());
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this.getParent());
            try {
                boolean bl = ((InteropLibrary)INSTANCE.getUncached(receiver_)).isString(receiver_);
                return bl;
            }
            finally {
                encapsulating_.set(prev_);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @CompilerDirectives.TruffleBoundary
        public String asString(Object receiver_) throws UnsupportedMessageException {
            assert (this.assertAdopted());
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this.getParent());
            try {
                String string = ((InteropLibrary)INSTANCE.getUncached(receiver_)).asString(receiver_);
                return string;
            }
            finally {
                encapsulating_.set(prev_);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @CompilerDirectives.TruffleBoundary
        public TruffleString asTruffleString(Object receiver_) throws UnsupportedMessageException {
            assert (this.assertAdopted());
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this.getParent());
            try {
                TruffleString truffleString = ((InteropLibrary)INSTANCE.getUncached(receiver_)).asTruffleString(receiver_);
                return truffleString;
            }
            finally {
                encapsulating_.set(prev_);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @CompilerDirectives.TruffleBoundary
        public boolean isNumber(Object receiver_) {
            assert (this.assertAdopted());
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this.getParent());
            try {
                boolean bl = ((InteropLibrary)INSTANCE.getUncached(receiver_)).isNumber(receiver_);
                return bl;
            }
            finally {
                encapsulating_.set(prev_);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @CompilerDirectives.TruffleBoundary
        public boolean fitsInByte(Object receiver_) {
            assert (this.assertAdopted());
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this.getParent());
            try {
                boolean bl = ((InteropLibrary)INSTANCE.getUncached(receiver_)).fitsInByte(receiver_);
                return bl;
            }
            finally {
                encapsulating_.set(prev_);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @CompilerDirectives.TruffleBoundary
        public boolean fitsInShort(Object receiver_) {
            assert (this.assertAdopted());
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this.getParent());
            try {
                boolean bl = ((InteropLibrary)INSTANCE.getUncached(receiver_)).fitsInShort(receiver_);
                return bl;
            }
            finally {
                encapsulating_.set(prev_);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @CompilerDirectives.TruffleBoundary
        public boolean fitsInInt(Object receiver_) {
            assert (this.assertAdopted());
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this.getParent());
            try {
                boolean bl = ((InteropLibrary)INSTANCE.getUncached(receiver_)).fitsInInt(receiver_);
                return bl;
            }
            finally {
                encapsulating_.set(prev_);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @CompilerDirectives.TruffleBoundary
        public boolean fitsInLong(Object receiver_) {
            assert (this.assertAdopted());
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this.getParent());
            try {
                boolean bl = ((InteropLibrary)INSTANCE.getUncached(receiver_)).fitsInLong(receiver_);
                return bl;
            }
            finally {
                encapsulating_.set(prev_);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @CompilerDirectives.TruffleBoundary
        public boolean fitsInFloat(Object receiver_) {
            assert (this.assertAdopted());
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this.getParent());
            try {
                boolean bl = ((InteropLibrary)INSTANCE.getUncached(receiver_)).fitsInFloat(receiver_);
                return bl;
            }
            finally {
                encapsulating_.set(prev_);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @CompilerDirectives.TruffleBoundary
        public boolean fitsInDouble(Object receiver_) {
            assert (this.assertAdopted());
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this.getParent());
            try {
                boolean bl = ((InteropLibrary)INSTANCE.getUncached(receiver_)).fitsInDouble(receiver_);
                return bl;
            }
            finally {
                encapsulating_.set(prev_);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @CompilerDirectives.TruffleBoundary
        public byte asByte(Object receiver_) throws UnsupportedMessageException {
            assert (this.assertAdopted());
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this.getParent());
            try {
                byte by = ((InteropLibrary)INSTANCE.getUncached(receiver_)).asByte(receiver_);
                return by;
            }
            finally {
                encapsulating_.set(prev_);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @CompilerDirectives.TruffleBoundary
        public short asShort(Object receiver_) throws UnsupportedMessageException {
            assert (this.assertAdopted());
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this.getParent());
            try {
                short s = ((InteropLibrary)INSTANCE.getUncached(receiver_)).asShort(receiver_);
                return s;
            }
            finally {
                encapsulating_.set(prev_);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @CompilerDirectives.TruffleBoundary
        public int asInt(Object receiver_) throws UnsupportedMessageException {
            assert (this.assertAdopted());
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this.getParent());
            try {
                int n = ((InteropLibrary)INSTANCE.getUncached(receiver_)).asInt(receiver_);
                return n;
            }
            finally {
                encapsulating_.set(prev_);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @CompilerDirectives.TruffleBoundary
        public long asLong(Object receiver_) throws UnsupportedMessageException {
            assert (this.assertAdopted());
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this.getParent());
            try {
                long l = ((InteropLibrary)INSTANCE.getUncached(receiver_)).asLong(receiver_);
                return l;
            }
            finally {
                encapsulating_.set(prev_);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @CompilerDirectives.TruffleBoundary
        public float asFloat(Object receiver_) throws UnsupportedMessageException {
            assert (this.assertAdopted());
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this.getParent());
            try {
                float f = ((InteropLibrary)INSTANCE.getUncached(receiver_)).asFloat(receiver_);
                return f;
            }
            finally {
                encapsulating_.set(prev_);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @CompilerDirectives.TruffleBoundary
        public double asDouble(Object receiver_) throws UnsupportedMessageException {
            assert (this.assertAdopted());
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this.getParent());
            try {
                double d = ((InteropLibrary)INSTANCE.getUncached(receiver_)).asDouble(receiver_);
                return d;
            }
            finally {
                encapsulating_.set(prev_);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @CompilerDirectives.TruffleBoundary
        public boolean hasMembers(Object receiver_) {
            assert (this.assertAdopted());
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this.getParent());
            try {
                boolean bl = ((InteropLibrary)INSTANCE.getUncached(receiver_)).hasMembers(receiver_);
                return bl;
            }
            finally {
                encapsulating_.set(prev_);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @CompilerDirectives.TruffleBoundary
        public Object getMembers(Object receiver_, boolean includeInternal) throws UnsupportedMessageException {
            assert (this.assertAdopted());
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this.getParent());
            try {
                Object object = ((InteropLibrary)INSTANCE.getUncached(receiver_)).getMembers(receiver_, includeInternal);
                return object;
            }
            finally {
                encapsulating_.set(prev_);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @CompilerDirectives.TruffleBoundary
        public boolean isMemberReadable(Object receiver_, String member) {
            assert (this.assertAdopted());
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this.getParent());
            try {
                boolean bl = ((InteropLibrary)INSTANCE.getUncached(receiver_)).isMemberReadable(receiver_, member);
                return bl;
            }
            finally {
                encapsulating_.set(prev_);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @CompilerDirectives.TruffleBoundary
        public Object readMember(Object receiver_, String member) throws UnsupportedMessageException, UnknownIdentifierException {
            assert (this.assertAdopted());
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this.getParent());
            try {
                Object object = ((InteropLibrary)INSTANCE.getUncached(receiver_)).readMember(receiver_, member);
                return object;
            }
            finally {
                encapsulating_.set(prev_);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @CompilerDirectives.TruffleBoundary
        public boolean isMemberModifiable(Object receiver_, String member) {
            assert (this.assertAdopted());
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this.getParent());
            try {
                boolean bl = ((InteropLibrary)INSTANCE.getUncached(receiver_)).isMemberModifiable(receiver_, member);
                return bl;
            }
            finally {
                encapsulating_.set(prev_);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @CompilerDirectives.TruffleBoundary
        public boolean isMemberInsertable(Object receiver_, String member) {
            assert (this.assertAdopted());
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this.getParent());
            try {
                boolean bl = ((InteropLibrary)INSTANCE.getUncached(receiver_)).isMemberInsertable(receiver_, member);
                return bl;
            }
            finally {
                encapsulating_.set(prev_);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @CompilerDirectives.TruffleBoundary
        public void writeMember(Object receiver_, String member, Object value2) throws UnsupportedMessageException, UnknownIdentifierException, UnsupportedTypeException {
            assert (this.assertAdopted());
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this.getParent());
            try {
                ((InteropLibrary)INSTANCE.getUncached(receiver_)).writeMember(receiver_, member, value2);
                return;
            }
            finally {
                encapsulating_.set(prev_);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @CompilerDirectives.TruffleBoundary
        public boolean isMemberRemovable(Object receiver_, String member) {
            assert (this.assertAdopted());
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this.getParent());
            try {
                boolean bl = ((InteropLibrary)INSTANCE.getUncached(receiver_)).isMemberRemovable(receiver_, member);
                return bl;
            }
            finally {
                encapsulating_.set(prev_);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @CompilerDirectives.TruffleBoundary
        public void removeMember(Object receiver_, String member) throws UnsupportedMessageException, UnknownIdentifierException {
            assert (this.assertAdopted());
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this.getParent());
            try {
                ((InteropLibrary)INSTANCE.getUncached(receiver_)).removeMember(receiver_, member);
                return;
            }
            finally {
                encapsulating_.set(prev_);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @CompilerDirectives.TruffleBoundary
        public boolean isMemberInvocable(Object receiver_, String member) {
            assert (this.assertAdopted());
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this.getParent());
            try {
                boolean bl = ((InteropLibrary)INSTANCE.getUncached(receiver_)).isMemberInvocable(receiver_, member);
                return bl;
            }
            finally {
                encapsulating_.set(prev_);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @CompilerDirectives.TruffleBoundary
        public Object invokeMember(Object receiver_, String member, Object ... arguments) throws UnsupportedMessageException, ArityException, UnknownIdentifierException, UnsupportedTypeException {
            assert (this.assertAdopted());
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this.getParent());
            try {
                Object object = ((InteropLibrary)INSTANCE.getUncached(receiver_)).invokeMember(receiver_, member, arguments);
                return object;
            }
            finally {
                encapsulating_.set(prev_);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @CompilerDirectives.TruffleBoundary
        public boolean isMemberInternal(Object receiver_, String member) {
            assert (this.assertAdopted());
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this.getParent());
            try {
                boolean bl = ((InteropLibrary)INSTANCE.getUncached(receiver_)).isMemberInternal(receiver_, member);
                return bl;
            }
            finally {
                encapsulating_.set(prev_);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @CompilerDirectives.TruffleBoundary
        public boolean hasMemberReadSideEffects(Object receiver_, String member) {
            assert (this.assertAdopted());
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this.getParent());
            try {
                boolean bl = ((InteropLibrary)INSTANCE.getUncached(receiver_)).hasMemberReadSideEffects(receiver_, member);
                return bl;
            }
            finally {
                encapsulating_.set(prev_);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @CompilerDirectives.TruffleBoundary
        public boolean hasMemberWriteSideEffects(Object receiver_, String member) {
            assert (this.assertAdopted());
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this.getParent());
            try {
                boolean bl = ((InteropLibrary)INSTANCE.getUncached(receiver_)).hasMemberWriteSideEffects(receiver_, member);
                return bl;
            }
            finally {
                encapsulating_.set(prev_);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @CompilerDirectives.TruffleBoundary
        public boolean hasHashEntries(Object receiver_) {
            assert (this.assertAdopted());
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this.getParent());
            try {
                boolean bl = ((InteropLibrary)INSTANCE.getUncached(receiver_)).hasHashEntries(receiver_);
                return bl;
            }
            finally {
                encapsulating_.set(prev_);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @CompilerDirectives.TruffleBoundary
        public long getHashSize(Object receiver_) throws UnsupportedMessageException {
            assert (this.assertAdopted());
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this.getParent());
            try {
                long l = ((InteropLibrary)INSTANCE.getUncached(receiver_)).getHashSize(receiver_);
                return l;
            }
            finally {
                encapsulating_.set(prev_);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @CompilerDirectives.TruffleBoundary
        public boolean isHashEntryReadable(Object receiver_, Object key) {
            assert (this.assertAdopted());
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this.getParent());
            try {
                boolean bl = ((InteropLibrary)INSTANCE.getUncached(receiver_)).isHashEntryReadable(receiver_, key);
                return bl;
            }
            finally {
                encapsulating_.set(prev_);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @CompilerDirectives.TruffleBoundary
        public Object readHashValue(Object receiver_, Object key) throws UnsupportedMessageException, UnknownKeyException {
            assert (this.assertAdopted());
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this.getParent());
            try {
                Object object = ((InteropLibrary)INSTANCE.getUncached(receiver_)).readHashValue(receiver_, key);
                return object;
            }
            finally {
                encapsulating_.set(prev_);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @CompilerDirectives.TruffleBoundary
        public Object readHashValueOrDefault(Object receiver_, Object key, Object defaultValue) throws UnsupportedMessageException {
            assert (this.assertAdopted());
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this.getParent());
            try {
                Object object = ((InteropLibrary)INSTANCE.getUncached(receiver_)).readHashValueOrDefault(receiver_, key, defaultValue);
                return object;
            }
            finally {
                encapsulating_.set(prev_);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @CompilerDirectives.TruffleBoundary
        public boolean isHashEntryModifiable(Object receiver_, Object key) {
            assert (this.assertAdopted());
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this.getParent());
            try {
                boolean bl = ((InteropLibrary)INSTANCE.getUncached(receiver_)).isHashEntryModifiable(receiver_, key);
                return bl;
            }
            finally {
                encapsulating_.set(prev_);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @CompilerDirectives.TruffleBoundary
        public boolean isHashEntryInsertable(Object receiver_, Object key) {
            assert (this.assertAdopted());
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this.getParent());
            try {
                boolean bl = ((InteropLibrary)INSTANCE.getUncached(receiver_)).isHashEntryInsertable(receiver_, key);
                return bl;
            }
            finally {
                encapsulating_.set(prev_);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @CompilerDirectives.TruffleBoundary
        public boolean isHashEntryWritable(Object receiver_, Object key) {
            assert (this.assertAdopted());
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this.getParent());
            try {
                boolean bl = ((InteropLibrary)INSTANCE.getUncached(receiver_)).isHashEntryWritable(receiver_, key);
                return bl;
            }
            finally {
                encapsulating_.set(prev_);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @CompilerDirectives.TruffleBoundary
        public void writeHashEntry(Object receiver_, Object key, Object value2) throws UnsupportedMessageException, UnknownKeyException, UnsupportedTypeException {
            assert (this.assertAdopted());
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this.getParent());
            try {
                ((InteropLibrary)INSTANCE.getUncached(receiver_)).writeHashEntry(receiver_, key, value2);
                return;
            }
            finally {
                encapsulating_.set(prev_);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @CompilerDirectives.TruffleBoundary
        public boolean isHashEntryRemovable(Object receiver_, Object key) {
            assert (this.assertAdopted());
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this.getParent());
            try {
                boolean bl = ((InteropLibrary)INSTANCE.getUncached(receiver_)).isHashEntryRemovable(receiver_, key);
                return bl;
            }
            finally {
                encapsulating_.set(prev_);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @CompilerDirectives.TruffleBoundary
        public void removeHashEntry(Object receiver_, Object key) throws UnsupportedMessageException, UnknownKeyException {
            assert (this.assertAdopted());
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this.getParent());
            try {
                ((InteropLibrary)INSTANCE.getUncached(receiver_)).removeHashEntry(receiver_, key);
                return;
            }
            finally {
                encapsulating_.set(prev_);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @CompilerDirectives.TruffleBoundary
        public boolean isHashEntryExisting(Object receiver_, Object key) {
            assert (this.assertAdopted());
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this.getParent());
            try {
                boolean bl = ((InteropLibrary)INSTANCE.getUncached(receiver_)).isHashEntryExisting(receiver_, key);
                return bl;
            }
            finally {
                encapsulating_.set(prev_);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @CompilerDirectives.TruffleBoundary
        public Object getHashEntriesIterator(Object receiver_) throws UnsupportedMessageException {
            assert (this.assertAdopted());
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this.getParent());
            try {
                Object object = ((InteropLibrary)INSTANCE.getUncached(receiver_)).getHashEntriesIterator(receiver_);
                return object;
            }
            finally {
                encapsulating_.set(prev_);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @CompilerDirectives.TruffleBoundary
        public Object getHashKeysIterator(Object receiver_) throws UnsupportedMessageException {
            assert (this.assertAdopted());
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this.getParent());
            try {
                Object object = ((InteropLibrary)INSTANCE.getUncached(receiver_)).getHashKeysIterator(receiver_);
                return object;
            }
            finally {
                encapsulating_.set(prev_);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @CompilerDirectives.TruffleBoundary
        public Object getHashValuesIterator(Object receiver_) throws UnsupportedMessageException {
            assert (this.assertAdopted());
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this.getParent());
            try {
                Object object = ((InteropLibrary)INSTANCE.getUncached(receiver_)).getHashValuesIterator(receiver_);
                return object;
            }
            finally {
                encapsulating_.set(prev_);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @CompilerDirectives.TruffleBoundary
        public boolean hasArrayElements(Object receiver_) {
            assert (this.assertAdopted());
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this.getParent());
            try {
                boolean bl = ((InteropLibrary)INSTANCE.getUncached(receiver_)).hasArrayElements(receiver_);
                return bl;
            }
            finally {
                encapsulating_.set(prev_);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @CompilerDirectives.TruffleBoundary
        public Object readArrayElement(Object receiver_, long index) throws UnsupportedMessageException, InvalidArrayIndexException {
            assert (this.assertAdopted());
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this.getParent());
            try {
                Object object = ((InteropLibrary)INSTANCE.getUncached(receiver_)).readArrayElement(receiver_, index);
                return object;
            }
            finally {
                encapsulating_.set(prev_);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @CompilerDirectives.TruffleBoundary
        public long getArraySize(Object receiver_) throws UnsupportedMessageException {
            assert (this.assertAdopted());
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this.getParent());
            try {
                long l = ((InteropLibrary)INSTANCE.getUncached(receiver_)).getArraySize(receiver_);
                return l;
            }
            finally {
                encapsulating_.set(prev_);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @CompilerDirectives.TruffleBoundary
        public boolean isArrayElementReadable(Object receiver_, long index) {
            assert (this.assertAdopted());
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this.getParent());
            try {
                boolean bl = ((InteropLibrary)INSTANCE.getUncached(receiver_)).isArrayElementReadable(receiver_, index);
                return bl;
            }
            finally {
                encapsulating_.set(prev_);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @CompilerDirectives.TruffleBoundary
        public void writeArrayElement(Object receiver_, long index, Object value2) throws UnsupportedMessageException, UnsupportedTypeException, InvalidArrayIndexException {
            assert (this.assertAdopted());
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this.getParent());
            try {
                ((InteropLibrary)INSTANCE.getUncached(receiver_)).writeArrayElement(receiver_, index, value2);
                return;
            }
            finally {
                encapsulating_.set(prev_);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @CompilerDirectives.TruffleBoundary
        public void removeArrayElement(Object receiver_, long index) throws UnsupportedMessageException, InvalidArrayIndexException {
            assert (this.assertAdopted());
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this.getParent());
            try {
                ((InteropLibrary)INSTANCE.getUncached(receiver_)).removeArrayElement(receiver_, index);
                return;
            }
            finally {
                encapsulating_.set(prev_);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @CompilerDirectives.TruffleBoundary
        public boolean isArrayElementModifiable(Object receiver_, long index) {
            assert (this.assertAdopted());
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this.getParent());
            try {
                boolean bl = ((InteropLibrary)INSTANCE.getUncached(receiver_)).isArrayElementModifiable(receiver_, index);
                return bl;
            }
            finally {
                encapsulating_.set(prev_);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @CompilerDirectives.TruffleBoundary
        public boolean isArrayElementInsertable(Object receiver_, long index) {
            assert (this.assertAdopted());
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this.getParent());
            try {
                boolean bl = ((InteropLibrary)INSTANCE.getUncached(receiver_)).isArrayElementInsertable(receiver_, index);
                return bl;
            }
            finally {
                encapsulating_.set(prev_);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @CompilerDirectives.TruffleBoundary
        public boolean isArrayElementRemovable(Object receiver_, long index) {
            assert (this.assertAdopted());
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this.getParent());
            try {
                boolean bl = ((InteropLibrary)INSTANCE.getUncached(receiver_)).isArrayElementRemovable(receiver_, index);
                return bl;
            }
            finally {
                encapsulating_.set(prev_);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @CompilerDirectives.TruffleBoundary
        public boolean hasBufferElements(Object receiver_) {
            assert (this.assertAdopted());
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this.getParent());
            try {
                boolean bl = ((InteropLibrary)INSTANCE.getUncached(receiver_)).hasBufferElements(receiver_);
                return bl;
            }
            finally {
                encapsulating_.set(prev_);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @CompilerDirectives.TruffleBoundary
        public boolean isBufferWritable(Object receiver_) throws UnsupportedMessageException {
            assert (this.assertAdopted());
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this.getParent());
            try {
                boolean bl = ((InteropLibrary)INSTANCE.getUncached(receiver_)).isBufferWritable(receiver_);
                return bl;
            }
            finally {
                encapsulating_.set(prev_);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @CompilerDirectives.TruffleBoundary
        public long getBufferSize(Object receiver_) throws UnsupportedMessageException {
            assert (this.assertAdopted());
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this.getParent());
            try {
                long l = ((InteropLibrary)INSTANCE.getUncached(receiver_)).getBufferSize(receiver_);
                return l;
            }
            finally {
                encapsulating_.set(prev_);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @CompilerDirectives.TruffleBoundary
        public byte readBufferByte(Object receiver_, long byteOffset) throws UnsupportedMessageException, InvalidBufferOffsetException {
            assert (this.assertAdopted());
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this.getParent());
            try {
                byte by = ((InteropLibrary)INSTANCE.getUncached(receiver_)).readBufferByte(receiver_, byteOffset);
                return by;
            }
            finally {
                encapsulating_.set(prev_);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @CompilerDirectives.TruffleBoundary
        public void writeBufferByte(Object receiver_, long byteOffset, byte value2) throws UnsupportedMessageException, InvalidBufferOffsetException {
            assert (this.assertAdopted());
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this.getParent());
            try {
                ((InteropLibrary)INSTANCE.getUncached(receiver_)).writeBufferByte(receiver_, byteOffset, value2);
                return;
            }
            finally {
                encapsulating_.set(prev_);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @CompilerDirectives.TruffleBoundary
        public short readBufferShort(Object receiver_, ByteOrder order, long byteOffset) throws UnsupportedMessageException, InvalidBufferOffsetException {
            assert (this.assertAdopted());
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this.getParent());
            try {
                short s = ((InteropLibrary)INSTANCE.getUncached(receiver_)).readBufferShort(receiver_, order, byteOffset);
                return s;
            }
            finally {
                encapsulating_.set(prev_);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @CompilerDirectives.TruffleBoundary
        public void writeBufferShort(Object receiver_, ByteOrder order, long byteOffset, short value2) throws UnsupportedMessageException, InvalidBufferOffsetException {
            assert (this.assertAdopted());
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this.getParent());
            try {
                ((InteropLibrary)INSTANCE.getUncached(receiver_)).writeBufferShort(receiver_, order, byteOffset, value2);
                return;
            }
            finally {
                encapsulating_.set(prev_);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @CompilerDirectives.TruffleBoundary
        public int readBufferInt(Object receiver_, ByteOrder order, long byteOffset) throws UnsupportedMessageException, InvalidBufferOffsetException {
            assert (this.assertAdopted());
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this.getParent());
            try {
                int n = ((InteropLibrary)INSTANCE.getUncached(receiver_)).readBufferInt(receiver_, order, byteOffset);
                return n;
            }
            finally {
                encapsulating_.set(prev_);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @CompilerDirectives.TruffleBoundary
        public void writeBufferInt(Object receiver_, ByteOrder order, long byteOffset, int value2) throws UnsupportedMessageException, InvalidBufferOffsetException {
            assert (this.assertAdopted());
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this.getParent());
            try {
                ((InteropLibrary)INSTANCE.getUncached(receiver_)).writeBufferInt(receiver_, order, byteOffset, value2);
                return;
            }
            finally {
                encapsulating_.set(prev_);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @CompilerDirectives.TruffleBoundary
        public long readBufferLong(Object receiver_, ByteOrder order, long byteOffset) throws UnsupportedMessageException, InvalidBufferOffsetException {
            assert (this.assertAdopted());
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this.getParent());
            try {
                long l = ((InteropLibrary)INSTANCE.getUncached(receiver_)).readBufferLong(receiver_, order, byteOffset);
                return l;
            }
            finally {
                encapsulating_.set(prev_);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @CompilerDirectives.TruffleBoundary
        public void writeBufferLong(Object receiver_, ByteOrder order, long byteOffset, long value2) throws UnsupportedMessageException, InvalidBufferOffsetException {
            assert (this.assertAdopted());
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this.getParent());
            try {
                ((InteropLibrary)INSTANCE.getUncached(receiver_)).writeBufferLong(receiver_, order, byteOffset, value2);
                return;
            }
            finally {
                encapsulating_.set(prev_);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @CompilerDirectives.TruffleBoundary
        public float readBufferFloat(Object receiver_, ByteOrder order, long byteOffset) throws UnsupportedMessageException, InvalidBufferOffsetException {
            assert (this.assertAdopted());
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this.getParent());
            try {
                float f = ((InteropLibrary)INSTANCE.getUncached(receiver_)).readBufferFloat(receiver_, order, byteOffset);
                return f;
            }
            finally {
                encapsulating_.set(prev_);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @CompilerDirectives.TruffleBoundary
        public void writeBufferFloat(Object receiver_, ByteOrder order, long byteOffset, float value2) throws UnsupportedMessageException, InvalidBufferOffsetException {
            assert (this.assertAdopted());
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this.getParent());
            try {
                ((InteropLibrary)INSTANCE.getUncached(receiver_)).writeBufferFloat(receiver_, order, byteOffset, value2);
                return;
            }
            finally {
                encapsulating_.set(prev_);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @CompilerDirectives.TruffleBoundary
        public double readBufferDouble(Object receiver_, ByteOrder order, long byteOffset) throws UnsupportedMessageException, InvalidBufferOffsetException {
            assert (this.assertAdopted());
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this.getParent());
            try {
                double d = ((InteropLibrary)INSTANCE.getUncached(receiver_)).readBufferDouble(receiver_, order, byteOffset);
                return d;
            }
            finally {
                encapsulating_.set(prev_);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @CompilerDirectives.TruffleBoundary
        public void writeBufferDouble(Object receiver_, ByteOrder order, long byteOffset, double value2) throws UnsupportedMessageException, InvalidBufferOffsetException {
            assert (this.assertAdopted());
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this.getParent());
            try {
                ((InteropLibrary)INSTANCE.getUncached(receiver_)).writeBufferDouble(receiver_, order, byteOffset, value2);
                return;
            }
            finally {
                encapsulating_.set(prev_);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @CompilerDirectives.TruffleBoundary
        public boolean isPointer(Object receiver_) {
            assert (this.assertAdopted());
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this.getParent());
            try {
                boolean bl = ((InteropLibrary)INSTANCE.getUncached(receiver_)).isPointer(receiver_);
                return bl;
            }
            finally {
                encapsulating_.set(prev_);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @CompilerDirectives.TruffleBoundary
        public long asPointer(Object receiver_) throws UnsupportedMessageException {
            assert (this.assertAdopted());
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this.getParent());
            try {
                long l = ((InteropLibrary)INSTANCE.getUncached(receiver_)).asPointer(receiver_);
                return l;
            }
            finally {
                encapsulating_.set(prev_);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @CompilerDirectives.TruffleBoundary
        public void toNative(Object receiver_) {
            assert (this.assertAdopted());
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this.getParent());
            try {
                ((InteropLibrary)INSTANCE.getUncached(receiver_)).toNative(receiver_);
                return;
            }
            finally {
                encapsulating_.set(prev_);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @CompilerDirectives.TruffleBoundary
        public Instant asInstant(Object receiver_) throws UnsupportedMessageException {
            assert (this.assertAdopted());
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this.getParent());
            try {
                Instant instant = ((InteropLibrary)INSTANCE.getUncached(receiver_)).asInstant(receiver_);
                return instant;
            }
            finally {
                encapsulating_.set(prev_);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @CompilerDirectives.TruffleBoundary
        public boolean isTimeZone(Object receiver_) {
            assert (this.assertAdopted());
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this.getParent());
            try {
                boolean bl = ((InteropLibrary)INSTANCE.getUncached(receiver_)).isTimeZone(receiver_);
                return bl;
            }
            finally {
                encapsulating_.set(prev_);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @CompilerDirectives.TruffleBoundary
        public ZoneId asTimeZone(Object receiver_) throws UnsupportedMessageException {
            assert (this.assertAdopted());
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this.getParent());
            try {
                ZoneId zoneId = ((InteropLibrary)INSTANCE.getUncached(receiver_)).asTimeZone(receiver_);
                return zoneId;
            }
            finally {
                encapsulating_.set(prev_);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @CompilerDirectives.TruffleBoundary
        public boolean isDate(Object receiver_) {
            assert (this.assertAdopted());
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this.getParent());
            try {
                boolean bl = ((InteropLibrary)INSTANCE.getUncached(receiver_)).isDate(receiver_);
                return bl;
            }
            finally {
                encapsulating_.set(prev_);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @CompilerDirectives.TruffleBoundary
        public LocalDate asDate(Object receiver_) throws UnsupportedMessageException {
            assert (this.assertAdopted());
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this.getParent());
            try {
                LocalDate localDate = ((InteropLibrary)INSTANCE.getUncached(receiver_)).asDate(receiver_);
                return localDate;
            }
            finally {
                encapsulating_.set(prev_);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @CompilerDirectives.TruffleBoundary
        public boolean isTime(Object receiver_) {
            assert (this.assertAdopted());
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this.getParent());
            try {
                boolean bl = ((InteropLibrary)INSTANCE.getUncached(receiver_)).isTime(receiver_);
                return bl;
            }
            finally {
                encapsulating_.set(prev_);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @CompilerDirectives.TruffleBoundary
        public LocalTime asTime(Object receiver_) throws UnsupportedMessageException {
            assert (this.assertAdopted());
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this.getParent());
            try {
                LocalTime localTime = ((InteropLibrary)INSTANCE.getUncached(receiver_)).asTime(receiver_);
                return localTime;
            }
            finally {
                encapsulating_.set(prev_);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @CompilerDirectives.TruffleBoundary
        public boolean isDuration(Object receiver_) {
            assert (this.assertAdopted());
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this.getParent());
            try {
                boolean bl = ((InteropLibrary)INSTANCE.getUncached(receiver_)).isDuration(receiver_);
                return bl;
            }
            finally {
                encapsulating_.set(prev_);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @CompilerDirectives.TruffleBoundary
        public Duration asDuration(Object receiver_) throws UnsupportedMessageException {
            assert (this.assertAdopted());
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this.getParent());
            try {
                Duration duration = ((InteropLibrary)INSTANCE.getUncached(receiver_)).asDuration(receiver_);
                return duration;
            }
            finally {
                encapsulating_.set(prev_);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @CompilerDirectives.TruffleBoundary
        public boolean isException(Object receiver_) {
            assert (this.assertAdopted());
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this.getParent());
            try {
                boolean bl = ((InteropLibrary)INSTANCE.getUncached(receiver_)).isException(receiver_);
                return bl;
            }
            finally {
                encapsulating_.set(prev_);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @CompilerDirectives.TruffleBoundary
        public RuntimeException throwException(Object receiver_) throws UnsupportedMessageException {
            assert (this.assertAdopted());
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this.getParent());
            try {
                RuntimeException runtimeException = ((InteropLibrary)INSTANCE.getUncached(receiver_)).throwException(receiver_);
                return runtimeException;
            }
            finally {
                encapsulating_.set(prev_);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @CompilerDirectives.TruffleBoundary
        public ExceptionType getExceptionType(Object receiver_) throws UnsupportedMessageException {
            assert (this.assertAdopted());
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this.getParent());
            try {
                ExceptionType exceptionType = ((InteropLibrary)INSTANCE.getUncached(receiver_)).getExceptionType(receiver_);
                return exceptionType;
            }
            finally {
                encapsulating_.set(prev_);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @CompilerDirectives.TruffleBoundary
        public boolean isExceptionIncompleteSource(Object receiver_) throws UnsupportedMessageException {
            assert (this.assertAdopted());
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this.getParent());
            try {
                boolean bl = ((InteropLibrary)INSTANCE.getUncached(receiver_)).isExceptionIncompleteSource(receiver_);
                return bl;
            }
            finally {
                encapsulating_.set(prev_);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @CompilerDirectives.TruffleBoundary
        public int getExceptionExitStatus(Object receiver_) throws UnsupportedMessageException {
            assert (this.assertAdopted());
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this.getParent());
            try {
                int n = ((InteropLibrary)INSTANCE.getUncached(receiver_)).getExceptionExitStatus(receiver_);
                return n;
            }
            finally {
                encapsulating_.set(prev_);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @CompilerDirectives.TruffleBoundary
        public boolean hasExceptionCause(Object receiver_) {
            assert (this.assertAdopted());
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this.getParent());
            try {
                boolean bl = ((InteropLibrary)INSTANCE.getUncached(receiver_)).hasExceptionCause(receiver_);
                return bl;
            }
            finally {
                encapsulating_.set(prev_);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @CompilerDirectives.TruffleBoundary
        public Object getExceptionCause(Object receiver_) throws UnsupportedMessageException {
            assert (this.assertAdopted());
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this.getParent());
            try {
                Object object = ((InteropLibrary)INSTANCE.getUncached(receiver_)).getExceptionCause(receiver_);
                return object;
            }
            finally {
                encapsulating_.set(prev_);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @CompilerDirectives.TruffleBoundary
        public boolean hasExceptionMessage(Object receiver_) {
            assert (this.assertAdopted());
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this.getParent());
            try {
                boolean bl = ((InteropLibrary)INSTANCE.getUncached(receiver_)).hasExceptionMessage(receiver_);
                return bl;
            }
            finally {
                encapsulating_.set(prev_);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @CompilerDirectives.TruffleBoundary
        public Object getExceptionMessage(Object receiver_) throws UnsupportedMessageException {
            assert (this.assertAdopted());
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this.getParent());
            try {
                Object object = ((InteropLibrary)INSTANCE.getUncached(receiver_)).getExceptionMessage(receiver_);
                return object;
            }
            finally {
                encapsulating_.set(prev_);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @CompilerDirectives.TruffleBoundary
        public boolean hasExceptionStackTrace(Object receiver_) {
            assert (this.assertAdopted());
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this.getParent());
            try {
                boolean bl = ((InteropLibrary)INSTANCE.getUncached(receiver_)).hasExceptionStackTrace(receiver_);
                return bl;
            }
            finally {
                encapsulating_.set(prev_);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @CompilerDirectives.TruffleBoundary
        public Object getExceptionStackTrace(Object receiver_) throws UnsupportedMessageException {
            assert (this.assertAdopted());
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this.getParent());
            try {
                Object object = ((InteropLibrary)INSTANCE.getUncached(receiver_)).getExceptionStackTrace(receiver_);
                return object;
            }
            finally {
                encapsulating_.set(prev_);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @CompilerDirectives.TruffleBoundary
        public boolean hasIterator(Object receiver_) {
            assert (this.assertAdopted());
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this.getParent());
            try {
                boolean bl = ((InteropLibrary)INSTANCE.getUncached(receiver_)).hasIterator(receiver_);
                return bl;
            }
            finally {
                encapsulating_.set(prev_);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @CompilerDirectives.TruffleBoundary
        public Object getIterator(Object receiver_) throws UnsupportedMessageException {
            assert (this.assertAdopted());
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this.getParent());
            try {
                Object object = ((InteropLibrary)INSTANCE.getUncached(receiver_)).getIterator(receiver_);
                return object;
            }
            finally {
                encapsulating_.set(prev_);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @CompilerDirectives.TruffleBoundary
        public boolean isIterator(Object receiver_) {
            assert (this.assertAdopted());
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this.getParent());
            try {
                boolean bl = ((InteropLibrary)INSTANCE.getUncached(receiver_)).isIterator(receiver_);
                return bl;
            }
            finally {
                encapsulating_.set(prev_);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @CompilerDirectives.TruffleBoundary
        public boolean hasIteratorNextElement(Object receiver_) throws UnsupportedMessageException {
            assert (this.assertAdopted());
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this.getParent());
            try {
                boolean bl = ((InteropLibrary)INSTANCE.getUncached(receiver_)).hasIteratorNextElement(receiver_);
                return bl;
            }
            finally {
                encapsulating_.set(prev_);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @CompilerDirectives.TruffleBoundary
        public Object getIteratorNextElement(Object receiver_) throws UnsupportedMessageException, StopIterationException {
            assert (this.assertAdopted());
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this.getParent());
            try {
                Object object = ((InteropLibrary)INSTANCE.getUncached(receiver_)).getIteratorNextElement(receiver_);
                return object;
            }
            finally {
                encapsulating_.set(prev_);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @CompilerDirectives.TruffleBoundary
        public boolean hasSourceLocation(Object receiver_) {
            assert (this.assertAdopted());
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this.getParent());
            try {
                boolean bl = ((InteropLibrary)INSTANCE.getUncached(receiver_)).hasSourceLocation(receiver_);
                return bl;
            }
            finally {
                encapsulating_.set(prev_);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @CompilerDirectives.TruffleBoundary
        public SourceSection getSourceLocation(Object receiver_) throws UnsupportedMessageException {
            assert (this.assertAdopted());
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this.getParent());
            try {
                SourceSection sourceSection = ((InteropLibrary)INSTANCE.getUncached(receiver_)).getSourceLocation(receiver_);
                return sourceSection;
            }
            finally {
                encapsulating_.set(prev_);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @CompilerDirectives.TruffleBoundary
        public boolean hasLanguage(Object receiver_) {
            assert (this.assertAdopted());
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this.getParent());
            try {
                boolean bl = ((InteropLibrary)INSTANCE.getUncached(receiver_)).hasLanguage(receiver_);
                return bl;
            }
            finally {
                encapsulating_.set(prev_);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @CompilerDirectives.TruffleBoundary
        public Class<? extends TruffleLanguage<?>> getLanguage(Object receiver_) throws UnsupportedMessageException {
            assert (this.assertAdopted());
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this.getParent());
            try {
                Class<? extends TruffleLanguage<?>> clazz = ((InteropLibrary)INSTANCE.getUncached(receiver_)).getLanguage(receiver_);
                return clazz;
            }
            finally {
                encapsulating_.set(prev_);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @CompilerDirectives.TruffleBoundary
        public boolean hasMetaObject(Object receiver_) {
            assert (this.assertAdopted());
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this.getParent());
            try {
                boolean bl = ((InteropLibrary)INSTANCE.getUncached(receiver_)).hasMetaObject(receiver_);
                return bl;
            }
            finally {
                encapsulating_.set(prev_);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @CompilerDirectives.TruffleBoundary
        public Object getMetaObject(Object receiver_) throws UnsupportedMessageException {
            assert (this.assertAdopted());
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this.getParent());
            try {
                Object object = ((InteropLibrary)INSTANCE.getUncached(receiver_)).getMetaObject(receiver_);
                return object;
            }
            finally {
                encapsulating_.set(prev_);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @CompilerDirectives.TruffleBoundary
        public Object toDisplayString(Object receiver_, boolean allowSideEffects) {
            assert (this.assertAdopted());
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this.getParent());
            try {
                Object object = ((InteropLibrary)INSTANCE.getUncached(receiver_)).toDisplayString(receiver_, allowSideEffects);
                return object;
            }
            finally {
                encapsulating_.set(prev_);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @CompilerDirectives.TruffleBoundary
        public boolean isMetaObject(Object receiver_) {
            assert (this.assertAdopted());
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this.getParent());
            try {
                boolean bl = ((InteropLibrary)INSTANCE.getUncached(receiver_)).isMetaObject(receiver_);
                return bl;
            }
            finally {
                encapsulating_.set(prev_);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @CompilerDirectives.TruffleBoundary
        public Object getMetaQualifiedName(Object receiver_) throws UnsupportedMessageException {
            assert (this.assertAdopted());
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this.getParent());
            try {
                Object object = ((InteropLibrary)INSTANCE.getUncached(receiver_)).getMetaQualifiedName(receiver_);
                return object;
            }
            finally {
                encapsulating_.set(prev_);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @CompilerDirectives.TruffleBoundary
        public Object getMetaSimpleName(Object receiver_) throws UnsupportedMessageException {
            assert (this.assertAdopted());
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this.getParent());
            try {
                Object object = ((InteropLibrary)INSTANCE.getUncached(receiver_)).getMetaSimpleName(receiver_);
                return object;
            }
            finally {
                encapsulating_.set(prev_);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @CompilerDirectives.TruffleBoundary
        public boolean isMetaInstance(Object receiver_, Object instance) throws UnsupportedMessageException {
            assert (this.assertAdopted());
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this.getParent());
            try {
                boolean bl = ((InteropLibrary)INSTANCE.getUncached(receiver_)).isMetaInstance(receiver_, instance);
                return bl;
            }
            finally {
                encapsulating_.set(prev_);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @CompilerDirectives.TruffleBoundary
        public boolean hasMetaParents(Object receiver_) {
            assert (this.assertAdopted());
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this.getParent());
            try {
                boolean bl = ((InteropLibrary)INSTANCE.getUncached(receiver_)).hasMetaParents(receiver_);
                return bl;
            }
            finally {
                encapsulating_.set(prev_);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @CompilerDirectives.TruffleBoundary
        public Object getMetaParents(Object receiver_) throws UnsupportedMessageException {
            assert (this.assertAdopted());
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this.getParent());
            try {
                Object object = ((InteropLibrary)INSTANCE.getUncached(receiver_)).getMetaParents(receiver_);
                return object;
            }
            finally {
                encapsulating_.set(prev_);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @CompilerDirectives.TruffleBoundary
        protected TriState isIdenticalOrUndefined(Object receiver_, Object other) {
            assert (this.assertAdopted());
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this.getParent());
            try {
                TriState triState = ((InteropLibrary)INSTANCE.getUncached(receiver_)).isIdenticalOrUndefined(receiver_, other);
                return triState;
            }
            finally {
                encapsulating_.set(prev_);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @CompilerDirectives.TruffleBoundary
        public boolean isIdentical(Object receiver_, Object other, InteropLibrary otherInterop) {
            assert (this.assertAdopted());
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this.getParent());
            try {
                boolean bl = ((InteropLibrary)INSTANCE.getUncached(receiver_)).isIdentical(receiver_, other, otherInterop);
                return bl;
            }
            finally {
                encapsulating_.set(prev_);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @CompilerDirectives.TruffleBoundary
        public int identityHashCode(Object receiver_) throws UnsupportedMessageException {
            assert (this.assertAdopted());
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this.getParent());
            try {
                int n = ((InteropLibrary)INSTANCE.getUncached(receiver_)).identityHashCode(receiver_);
                return n;
            }
            finally {
                encapsulating_.set(prev_);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @CompilerDirectives.TruffleBoundary
        public boolean isScope(Object receiver_) {
            assert (this.assertAdopted());
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this.getParent());
            try {
                boolean bl = ((InteropLibrary)INSTANCE.getUncached(receiver_)).isScope(receiver_);
                return bl;
            }
            finally {
                encapsulating_.set(prev_);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @CompilerDirectives.TruffleBoundary
        public boolean hasScopeParent(Object receiver_) {
            assert (this.assertAdopted());
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this.getParent());
            try {
                boolean bl = ((InteropLibrary)INSTANCE.getUncached(receiver_)).hasScopeParent(receiver_);
                return bl;
            }
            finally {
                encapsulating_.set(prev_);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @CompilerDirectives.TruffleBoundary
        public Object getScopeParent(Object receiver_) throws UnsupportedMessageException {
            assert (this.assertAdopted());
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this.getParent());
            try {
                Object object = ((InteropLibrary)INSTANCE.getUncached(receiver_)).getScopeParent(receiver_);
                return object;
            }
            finally {
                encapsulating_.set(prev_);
            }
        }

        @Override
        public boolean accepts(Object receiver_) {
            return true;
        }
    }

    @GeneratedBy(value=InteropLibrary.class)
    private static final class Delegate
    extends InteropLibrary {
        @Node.Child
        private InteropLibrary delegateLibrary;

        Delegate(InteropLibrary delegateLibrary) {
            this.delegateLibrary = delegateLibrary;
        }

        @Override
        public boolean isNull(Object receiver_) {
            if (InteropLibraryGen.isDelegated(this.delegateLibrary, 0)) {
                Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
                return ((InteropLibrary)InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate)).isNull(delegate);
            }
            return this.delegateLibrary.isNull(receiver_);
        }

        @Override
        public boolean isBoolean(Object receiver_) {
            if (InteropLibraryGen.isDelegated(this.delegateLibrary, 1)) {
                Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
                return ((InteropLibrary)InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate)).isBoolean(delegate);
            }
            return this.delegateLibrary.isBoolean(receiver_);
        }

        @Override
        public boolean asBoolean(Object receiver_) throws UnsupportedMessageException {
            if (InteropLibraryGen.isDelegated(this.delegateLibrary, 2)) {
                Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
                return ((InteropLibrary)InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate)).asBoolean(delegate);
            }
            return this.delegateLibrary.asBoolean(receiver_);
        }

        @Override
        public boolean isExecutable(Object receiver_) {
            if (InteropLibraryGen.isDelegated(this.delegateLibrary, 3)) {
                Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
                return ((InteropLibrary)InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate)).isExecutable(delegate);
            }
            return this.delegateLibrary.isExecutable(receiver_);
        }

        @Override
        public Object execute(Object receiver_, Object ... arguments) throws UnsupportedTypeException, ArityException, UnsupportedMessageException {
            if (InteropLibraryGen.isDelegated(this.delegateLibrary, 4)) {
                Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
                return ((InteropLibrary)InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate)).execute(delegate, arguments);
            }
            return this.delegateLibrary.execute(receiver_, arguments);
        }

        @Override
        public boolean hasExecutableName(Object receiver_) {
            if (InteropLibraryGen.isDelegated(this.delegateLibrary, 5)) {
                Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
                return ((InteropLibrary)InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate)).hasExecutableName(delegate);
            }
            return this.delegateLibrary.hasExecutableName(receiver_);
        }

        @Override
        public Object getExecutableName(Object receiver_) throws UnsupportedMessageException {
            if (InteropLibraryGen.isDelegated(this.delegateLibrary, 6)) {
                Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
                return ((InteropLibrary)InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate)).getExecutableName(delegate);
            }
            return this.delegateLibrary.getExecutableName(receiver_);
        }

        @Override
        public boolean hasDeclaringMetaObject(Object receiver_) {
            if (InteropLibraryGen.isDelegated(this.delegateLibrary, 7)) {
                Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
                return ((InteropLibrary)InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate)).hasDeclaringMetaObject(delegate);
            }
            return this.delegateLibrary.hasDeclaringMetaObject(receiver_);
        }

        @Override
        public Object getDeclaringMetaObject(Object receiver_) throws UnsupportedMessageException {
            if (InteropLibraryGen.isDelegated(this.delegateLibrary, 8)) {
                Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
                return ((InteropLibrary)InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate)).getDeclaringMetaObject(delegate);
            }
            return this.delegateLibrary.getDeclaringMetaObject(receiver_);
        }

        @Override
        public boolean isInstantiable(Object receiver_) {
            if (InteropLibraryGen.isDelegated(this.delegateLibrary, 9)) {
                Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
                return ((InteropLibrary)InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate)).isInstantiable(delegate);
            }
            return this.delegateLibrary.isInstantiable(receiver_);
        }

        @Override
        public Object instantiate(Object receiver_, Object ... arguments) throws UnsupportedTypeException, ArityException, UnsupportedMessageException {
            if (InteropLibraryGen.isDelegated(this.delegateLibrary, 10)) {
                Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
                return ((InteropLibrary)InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate)).instantiate(delegate, arguments);
            }
            return this.delegateLibrary.instantiate(receiver_, arguments);
        }

        @Override
        public boolean isString(Object receiver_) {
            if (InteropLibraryGen.isDelegated(this.delegateLibrary, 11)) {
                Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
                return ((InteropLibrary)InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate)).isString(delegate);
            }
            return this.delegateLibrary.isString(receiver_);
        }

        @Override
        public String asString(Object receiver_) throws UnsupportedMessageException {
            if (InteropLibraryGen.isDelegated(this.delegateLibrary, 12)) {
                Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
                return ((InteropLibrary)InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate)).asString(delegate);
            }
            return this.delegateLibrary.asString(receiver_);
        }

        @Override
        public TruffleString asTruffleString(Object receiver_) throws UnsupportedMessageException {
            if (InteropLibraryGen.isDelegated(this.delegateLibrary, 13)) {
                Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
                return ((InteropLibrary)InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate)).asTruffleString(delegate);
            }
            return this.delegateLibrary.asTruffleString(receiver_);
        }

        @Override
        public boolean isNumber(Object receiver_) {
            if (InteropLibraryGen.isDelegated(this.delegateLibrary, 14)) {
                Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
                return ((InteropLibrary)InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate)).isNumber(delegate);
            }
            return this.delegateLibrary.isNumber(receiver_);
        }

        @Override
        public boolean fitsInByte(Object receiver_) {
            if (InteropLibraryGen.isDelegated(this.delegateLibrary, 15)) {
                Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
                return ((InteropLibrary)InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate)).fitsInByte(delegate);
            }
            return this.delegateLibrary.fitsInByte(receiver_);
        }

        @Override
        public boolean fitsInShort(Object receiver_) {
            if (InteropLibraryGen.isDelegated(this.delegateLibrary, 16)) {
                Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
                return ((InteropLibrary)InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate)).fitsInShort(delegate);
            }
            return this.delegateLibrary.fitsInShort(receiver_);
        }

        @Override
        public boolean fitsInInt(Object receiver_) {
            if (InteropLibraryGen.isDelegated(this.delegateLibrary, 17)) {
                Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
                return ((InteropLibrary)InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate)).fitsInInt(delegate);
            }
            return this.delegateLibrary.fitsInInt(receiver_);
        }

        @Override
        public boolean fitsInLong(Object receiver_) {
            if (InteropLibraryGen.isDelegated(this.delegateLibrary, 18)) {
                Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
                return ((InteropLibrary)InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate)).fitsInLong(delegate);
            }
            return this.delegateLibrary.fitsInLong(receiver_);
        }

        @Override
        public boolean fitsInFloat(Object receiver_) {
            if (InteropLibraryGen.isDelegated(this.delegateLibrary, 19)) {
                Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
                return ((InteropLibrary)InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate)).fitsInFloat(delegate);
            }
            return this.delegateLibrary.fitsInFloat(receiver_);
        }

        @Override
        public boolean fitsInDouble(Object receiver_) {
            if (InteropLibraryGen.isDelegated(this.delegateLibrary, 20)) {
                Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
                return ((InteropLibrary)InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate)).fitsInDouble(delegate);
            }
            return this.delegateLibrary.fitsInDouble(receiver_);
        }

        @Override
        public byte asByte(Object receiver_) throws UnsupportedMessageException {
            if (InteropLibraryGen.isDelegated(this.delegateLibrary, 21)) {
                Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
                return ((InteropLibrary)InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate)).asByte(delegate);
            }
            return this.delegateLibrary.asByte(receiver_);
        }

        @Override
        public short asShort(Object receiver_) throws UnsupportedMessageException {
            if (InteropLibraryGen.isDelegated(this.delegateLibrary, 22)) {
                Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
                return ((InteropLibrary)InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate)).asShort(delegate);
            }
            return this.delegateLibrary.asShort(receiver_);
        }

        @Override
        public int asInt(Object receiver_) throws UnsupportedMessageException {
            if (InteropLibraryGen.isDelegated(this.delegateLibrary, 23)) {
                Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
                return ((InteropLibrary)InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate)).asInt(delegate);
            }
            return this.delegateLibrary.asInt(receiver_);
        }

        @Override
        public long asLong(Object receiver_) throws UnsupportedMessageException {
            if (InteropLibraryGen.isDelegated(this.delegateLibrary, 24)) {
                Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
                return ((InteropLibrary)InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate)).asLong(delegate);
            }
            return this.delegateLibrary.asLong(receiver_);
        }

        @Override
        public float asFloat(Object receiver_) throws UnsupportedMessageException {
            if (InteropLibraryGen.isDelegated(this.delegateLibrary, 25)) {
                Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
                return ((InteropLibrary)InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate)).asFloat(delegate);
            }
            return this.delegateLibrary.asFloat(receiver_);
        }

        @Override
        public double asDouble(Object receiver_) throws UnsupportedMessageException {
            if (InteropLibraryGen.isDelegated(this.delegateLibrary, 26)) {
                Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
                return ((InteropLibrary)InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate)).asDouble(delegate);
            }
            return this.delegateLibrary.asDouble(receiver_);
        }

        @Override
        public boolean hasMembers(Object receiver_) {
            if (InteropLibraryGen.isDelegated(this.delegateLibrary, 27)) {
                Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
                return ((InteropLibrary)InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate)).hasMembers(delegate);
            }
            return this.delegateLibrary.hasMembers(receiver_);
        }

        @Override
        public Object getMembers(Object receiver_, boolean includeInternal) throws UnsupportedMessageException {
            if (InteropLibraryGen.isDelegated(this.delegateLibrary, 28)) {
                Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
                return ((InteropLibrary)InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate)).getMembers(delegate, includeInternal);
            }
            return this.delegateLibrary.getMembers(receiver_, includeInternal);
        }

        @Override
        public boolean isMemberReadable(Object receiver_, String member) {
            if (InteropLibraryGen.isDelegated(this.delegateLibrary, 29)) {
                Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
                return ((InteropLibrary)InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate)).isMemberReadable(delegate, member);
            }
            return this.delegateLibrary.isMemberReadable(receiver_, member);
        }

        @Override
        public Object readMember(Object receiver_, String member) throws UnsupportedMessageException, UnknownIdentifierException {
            if (InteropLibraryGen.isDelegated(this.delegateLibrary, 30)) {
                Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
                return ((InteropLibrary)InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate)).readMember(delegate, member);
            }
            return this.delegateLibrary.readMember(receiver_, member);
        }

        @Override
        public boolean isMemberModifiable(Object receiver_, String member) {
            if (InteropLibraryGen.isDelegated(this.delegateLibrary, 31)) {
                Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
                return ((InteropLibrary)InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate)).isMemberModifiable(delegate, member);
            }
            return this.delegateLibrary.isMemberModifiable(receiver_, member);
        }

        @Override
        public boolean isMemberInsertable(Object receiver_, String member) {
            if (InteropLibraryGen.isDelegated(this.delegateLibrary, 32)) {
                Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
                return ((InteropLibrary)InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate)).isMemberInsertable(delegate, member);
            }
            return this.delegateLibrary.isMemberInsertable(receiver_, member);
        }

        @Override
        public void writeMember(Object receiver_, String member, Object value2) throws UnsupportedMessageException, UnknownIdentifierException, UnsupportedTypeException {
            if (InteropLibraryGen.isDelegated(this.delegateLibrary, 33)) {
                Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
                ((InteropLibrary)InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate)).writeMember(delegate, member, value2);
                return;
            }
            this.delegateLibrary.writeMember(receiver_, member, value2);
        }

        @Override
        public boolean isMemberRemovable(Object receiver_, String member) {
            if (InteropLibraryGen.isDelegated(this.delegateLibrary, 34)) {
                Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
                return ((InteropLibrary)InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate)).isMemberRemovable(delegate, member);
            }
            return this.delegateLibrary.isMemberRemovable(receiver_, member);
        }

        @Override
        public void removeMember(Object receiver_, String member) throws UnsupportedMessageException, UnknownIdentifierException {
            if (InteropLibraryGen.isDelegated(this.delegateLibrary, 35)) {
                Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
                ((InteropLibrary)InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate)).removeMember(delegate, member);
                return;
            }
            this.delegateLibrary.removeMember(receiver_, member);
        }

        @Override
        public boolean isMemberInvocable(Object receiver_, String member) {
            if (InteropLibraryGen.isDelegated(this.delegateLibrary, 36)) {
                Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
                return ((InteropLibrary)InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate)).isMemberInvocable(delegate, member);
            }
            return this.delegateLibrary.isMemberInvocable(receiver_, member);
        }

        @Override
        public Object invokeMember(Object receiver_, String member, Object ... arguments) throws UnsupportedMessageException, ArityException, UnknownIdentifierException, UnsupportedTypeException {
            if (InteropLibraryGen.isDelegated(this.delegateLibrary, 37)) {
                Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
                return ((InteropLibrary)InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate)).invokeMember(delegate, member, arguments);
            }
            return this.delegateLibrary.invokeMember(receiver_, member, arguments);
        }

        @Override
        public boolean isMemberInternal(Object receiver_, String member) {
            if (InteropLibraryGen.isDelegated(this.delegateLibrary, 38)) {
                Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
                return ((InteropLibrary)InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate)).isMemberInternal(delegate, member);
            }
            return this.delegateLibrary.isMemberInternal(receiver_, member);
        }

        @Override
        public boolean hasMemberReadSideEffects(Object receiver_, String member) {
            if (InteropLibraryGen.isDelegated(this.delegateLibrary, 39)) {
                Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
                return ((InteropLibrary)InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate)).hasMemberReadSideEffects(delegate, member);
            }
            return this.delegateLibrary.hasMemberReadSideEffects(receiver_, member);
        }

        @Override
        public boolean hasMemberWriteSideEffects(Object receiver_, String member) {
            if (InteropLibraryGen.isDelegated(this.delegateLibrary, 40)) {
                Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
                return ((InteropLibrary)InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate)).hasMemberWriteSideEffects(delegate, member);
            }
            return this.delegateLibrary.hasMemberWriteSideEffects(receiver_, member);
        }

        @Override
        public boolean hasHashEntries(Object receiver_) {
            if (InteropLibraryGen.isDelegated(this.delegateLibrary, 41)) {
                Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
                return ((InteropLibrary)InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate)).hasHashEntries(delegate);
            }
            return this.delegateLibrary.hasHashEntries(receiver_);
        }

        @Override
        public long getHashSize(Object receiver_) throws UnsupportedMessageException {
            if (InteropLibraryGen.isDelegated(this.delegateLibrary, 42)) {
                Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
                return ((InteropLibrary)InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate)).getHashSize(delegate);
            }
            return this.delegateLibrary.getHashSize(receiver_);
        }

        @Override
        public boolean isHashEntryReadable(Object receiver_, Object key) {
            if (InteropLibraryGen.isDelegated(this.delegateLibrary, 43)) {
                Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
                return ((InteropLibrary)InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate)).isHashEntryReadable(delegate, key);
            }
            return this.delegateLibrary.isHashEntryReadable(receiver_, key);
        }

        @Override
        public Object readHashValue(Object receiver_, Object key) throws UnsupportedMessageException, UnknownKeyException {
            if (InteropLibraryGen.isDelegated(this.delegateLibrary, 44)) {
                Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
                return ((InteropLibrary)InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate)).readHashValue(delegate, key);
            }
            return this.delegateLibrary.readHashValue(receiver_, key);
        }

        @Override
        public Object readHashValueOrDefault(Object receiver_, Object key, Object defaultValue) throws UnsupportedMessageException {
            if (InteropLibraryGen.isDelegated(this.delegateLibrary, 45)) {
                Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
                return ((InteropLibrary)InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate)).readHashValueOrDefault(delegate, key, defaultValue);
            }
            return this.delegateLibrary.readHashValueOrDefault(receiver_, key, defaultValue);
        }

        @Override
        public boolean isHashEntryModifiable(Object receiver_, Object key) {
            if (InteropLibraryGen.isDelegated(this.delegateLibrary, 46)) {
                Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
                return ((InteropLibrary)InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate)).isHashEntryModifiable(delegate, key);
            }
            return this.delegateLibrary.isHashEntryModifiable(receiver_, key);
        }

        @Override
        public boolean isHashEntryInsertable(Object receiver_, Object key) {
            if (InteropLibraryGen.isDelegated(this.delegateLibrary, 47)) {
                Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
                return ((InteropLibrary)InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate)).isHashEntryInsertable(delegate, key);
            }
            return this.delegateLibrary.isHashEntryInsertable(receiver_, key);
        }

        @Override
        public boolean isHashEntryWritable(Object receiver_, Object key) {
            if (InteropLibraryGen.isDelegated(this.delegateLibrary, 48)) {
                Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
                return ((InteropLibrary)InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate)).isHashEntryWritable(delegate, key);
            }
            return this.delegateLibrary.isHashEntryWritable(receiver_, key);
        }

        @Override
        public void writeHashEntry(Object receiver_, Object key, Object value2) throws UnsupportedMessageException, UnknownKeyException, UnsupportedTypeException {
            if (InteropLibraryGen.isDelegated(this.delegateLibrary, 49)) {
                Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
                ((InteropLibrary)InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate)).writeHashEntry(delegate, key, value2);
                return;
            }
            this.delegateLibrary.writeHashEntry(receiver_, key, value2);
        }

        @Override
        public boolean isHashEntryRemovable(Object receiver_, Object key) {
            if (InteropLibraryGen.isDelegated(this.delegateLibrary, 50)) {
                Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
                return ((InteropLibrary)InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate)).isHashEntryRemovable(delegate, key);
            }
            return this.delegateLibrary.isHashEntryRemovable(receiver_, key);
        }

        @Override
        public void removeHashEntry(Object receiver_, Object key) throws UnsupportedMessageException, UnknownKeyException {
            if (InteropLibraryGen.isDelegated(this.delegateLibrary, 51)) {
                Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
                ((InteropLibrary)InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate)).removeHashEntry(delegate, key);
                return;
            }
            this.delegateLibrary.removeHashEntry(receiver_, key);
        }

        @Override
        public boolean isHashEntryExisting(Object receiver_, Object key) {
            if (InteropLibraryGen.isDelegated(this.delegateLibrary, 52)) {
                Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
                return ((InteropLibrary)InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate)).isHashEntryExisting(delegate, key);
            }
            return this.delegateLibrary.isHashEntryExisting(receiver_, key);
        }

        @Override
        public Object getHashEntriesIterator(Object receiver_) throws UnsupportedMessageException {
            if (InteropLibraryGen.isDelegated(this.delegateLibrary, 53)) {
                Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
                return ((InteropLibrary)InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate)).getHashEntriesIterator(delegate);
            }
            return this.delegateLibrary.getHashEntriesIterator(receiver_);
        }

        @Override
        public Object getHashKeysIterator(Object receiver_) throws UnsupportedMessageException {
            if (InteropLibraryGen.isDelegated(this.delegateLibrary, 54)) {
                Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
                return ((InteropLibrary)InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate)).getHashKeysIterator(delegate);
            }
            return this.delegateLibrary.getHashKeysIterator(receiver_);
        }

        @Override
        public Object getHashValuesIterator(Object receiver_) throws UnsupportedMessageException {
            if (InteropLibraryGen.isDelegated(this.delegateLibrary, 55)) {
                Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
                return ((InteropLibrary)InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate)).getHashValuesIterator(delegate);
            }
            return this.delegateLibrary.getHashValuesIterator(receiver_);
        }

        @Override
        public boolean hasArrayElements(Object receiver_) {
            if (InteropLibraryGen.isDelegated(this.delegateLibrary, 56)) {
                Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
                return ((InteropLibrary)InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate)).hasArrayElements(delegate);
            }
            return this.delegateLibrary.hasArrayElements(receiver_);
        }

        @Override
        public Object readArrayElement(Object receiver_, long index) throws UnsupportedMessageException, InvalidArrayIndexException {
            if (InteropLibraryGen.isDelegated(this.delegateLibrary, 57)) {
                Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
                return ((InteropLibrary)InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate)).readArrayElement(delegate, index);
            }
            return this.delegateLibrary.readArrayElement(receiver_, index);
        }

        @Override
        public long getArraySize(Object receiver_) throws UnsupportedMessageException {
            if (InteropLibraryGen.isDelegated(this.delegateLibrary, 58)) {
                Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
                return ((InteropLibrary)InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate)).getArraySize(delegate);
            }
            return this.delegateLibrary.getArraySize(receiver_);
        }

        @Override
        public boolean isArrayElementReadable(Object receiver_, long index) {
            if (InteropLibraryGen.isDelegated(this.delegateLibrary, 59)) {
                Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
                return ((InteropLibrary)InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate)).isArrayElementReadable(delegate, index);
            }
            return this.delegateLibrary.isArrayElementReadable(receiver_, index);
        }

        @Override
        public void writeArrayElement(Object receiver_, long index, Object value2) throws UnsupportedMessageException, UnsupportedTypeException, InvalidArrayIndexException {
            if (InteropLibraryGen.isDelegated(this.delegateLibrary, 60)) {
                Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
                ((InteropLibrary)InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate)).writeArrayElement(delegate, index, value2);
                return;
            }
            this.delegateLibrary.writeArrayElement(receiver_, index, value2);
        }

        @Override
        public void removeArrayElement(Object receiver_, long index) throws UnsupportedMessageException, InvalidArrayIndexException {
            if (InteropLibraryGen.isDelegated(this.delegateLibrary, 61)) {
                Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
                ((InteropLibrary)InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate)).removeArrayElement(delegate, index);
                return;
            }
            this.delegateLibrary.removeArrayElement(receiver_, index);
        }

        @Override
        public boolean isArrayElementModifiable(Object receiver_, long index) {
            if (InteropLibraryGen.isDelegated(this.delegateLibrary, 62)) {
                Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
                return ((InteropLibrary)InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate)).isArrayElementModifiable(delegate, index);
            }
            return this.delegateLibrary.isArrayElementModifiable(receiver_, index);
        }

        @Override
        public boolean isArrayElementInsertable(Object receiver_, long index) {
            if (InteropLibraryGen.isDelegated(this.delegateLibrary, 63)) {
                Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
                return ((InteropLibrary)InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate)).isArrayElementInsertable(delegate, index);
            }
            return this.delegateLibrary.isArrayElementInsertable(receiver_, index);
        }

        @Override
        public boolean isArrayElementRemovable(Object receiver_, long index) {
            if (InteropLibraryGen.isDelegated(this.delegateLibrary, 64)) {
                Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
                return ((InteropLibrary)InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate)).isArrayElementRemovable(delegate, index);
            }
            return this.delegateLibrary.isArrayElementRemovable(receiver_, index);
        }

        @Override
        public boolean hasBufferElements(Object receiver_) {
            if (InteropLibraryGen.isDelegated(this.delegateLibrary, 65)) {
                Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
                return ((InteropLibrary)InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate)).hasBufferElements(delegate);
            }
            return this.delegateLibrary.hasBufferElements(receiver_);
        }

        @Override
        public boolean isBufferWritable(Object receiver_) throws UnsupportedMessageException {
            if (InteropLibraryGen.isDelegated(this.delegateLibrary, 66)) {
                Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
                return ((InteropLibrary)InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate)).isBufferWritable(delegate);
            }
            return this.delegateLibrary.isBufferWritable(receiver_);
        }

        @Override
        public long getBufferSize(Object receiver_) throws UnsupportedMessageException {
            if (InteropLibraryGen.isDelegated(this.delegateLibrary, 67)) {
                Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
                return ((InteropLibrary)InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate)).getBufferSize(delegate);
            }
            return this.delegateLibrary.getBufferSize(receiver_);
        }

        @Override
        public byte readBufferByte(Object receiver_, long byteOffset) throws UnsupportedMessageException, InvalidBufferOffsetException {
            if (InteropLibraryGen.isDelegated(this.delegateLibrary, 68)) {
                Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
                return ((InteropLibrary)InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate)).readBufferByte(delegate, byteOffset);
            }
            return this.delegateLibrary.readBufferByte(receiver_, byteOffset);
        }

        @Override
        public void writeBufferByte(Object receiver_, long byteOffset, byte value2) throws UnsupportedMessageException, InvalidBufferOffsetException {
            if (InteropLibraryGen.isDelegated(this.delegateLibrary, 69)) {
                Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
                ((InteropLibrary)InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate)).writeBufferByte(delegate, byteOffset, value2);
                return;
            }
            this.delegateLibrary.writeBufferByte(receiver_, byteOffset, value2);
        }

        @Override
        public short readBufferShort(Object receiver_, ByteOrder order, long byteOffset) throws UnsupportedMessageException, InvalidBufferOffsetException {
            if (InteropLibraryGen.isDelegated(this.delegateLibrary, 70)) {
                Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
                return ((InteropLibrary)InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate)).readBufferShort(delegate, order, byteOffset);
            }
            return this.delegateLibrary.readBufferShort(receiver_, order, byteOffset);
        }

        @Override
        public void writeBufferShort(Object receiver_, ByteOrder order, long byteOffset, short value2) throws UnsupportedMessageException, InvalidBufferOffsetException {
            if (InteropLibraryGen.isDelegated(this.delegateLibrary, 71)) {
                Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
                ((InteropLibrary)InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate)).writeBufferShort(delegate, order, byteOffset, value2);
                return;
            }
            this.delegateLibrary.writeBufferShort(receiver_, order, byteOffset, value2);
        }

        @Override
        public int readBufferInt(Object receiver_, ByteOrder order, long byteOffset) throws UnsupportedMessageException, InvalidBufferOffsetException {
            if (InteropLibraryGen.isDelegated(this.delegateLibrary, 72)) {
                Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
                return ((InteropLibrary)InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate)).readBufferInt(delegate, order, byteOffset);
            }
            return this.delegateLibrary.readBufferInt(receiver_, order, byteOffset);
        }

        @Override
        public void writeBufferInt(Object receiver_, ByteOrder order, long byteOffset, int value2) throws UnsupportedMessageException, InvalidBufferOffsetException {
            if (InteropLibraryGen.isDelegated(this.delegateLibrary, 73)) {
                Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
                ((InteropLibrary)InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate)).writeBufferInt(delegate, order, byteOffset, value2);
                return;
            }
            this.delegateLibrary.writeBufferInt(receiver_, order, byteOffset, value2);
        }

        @Override
        public long readBufferLong(Object receiver_, ByteOrder order, long byteOffset) throws UnsupportedMessageException, InvalidBufferOffsetException {
            if (InteropLibraryGen.isDelegated(this.delegateLibrary, 74)) {
                Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
                return ((InteropLibrary)InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate)).readBufferLong(delegate, order, byteOffset);
            }
            return this.delegateLibrary.readBufferLong(receiver_, order, byteOffset);
        }

        @Override
        public void writeBufferLong(Object receiver_, ByteOrder order, long byteOffset, long value2) throws UnsupportedMessageException, InvalidBufferOffsetException {
            if (InteropLibraryGen.isDelegated(this.delegateLibrary, 75)) {
                Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
                ((InteropLibrary)InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate)).writeBufferLong(delegate, order, byteOffset, value2);
                return;
            }
            this.delegateLibrary.writeBufferLong(receiver_, order, byteOffset, value2);
        }

        @Override
        public float readBufferFloat(Object receiver_, ByteOrder order, long byteOffset) throws UnsupportedMessageException, InvalidBufferOffsetException {
            if (InteropLibraryGen.isDelegated(this.delegateLibrary, 76)) {
                Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
                return ((InteropLibrary)InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate)).readBufferFloat(delegate, order, byteOffset);
            }
            return this.delegateLibrary.readBufferFloat(receiver_, order, byteOffset);
        }

        @Override
        public void writeBufferFloat(Object receiver_, ByteOrder order, long byteOffset, float value2) throws UnsupportedMessageException, InvalidBufferOffsetException {
            if (InteropLibraryGen.isDelegated(this.delegateLibrary, 77)) {
                Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
                ((InteropLibrary)InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate)).writeBufferFloat(delegate, order, byteOffset, value2);
                return;
            }
            this.delegateLibrary.writeBufferFloat(receiver_, order, byteOffset, value2);
        }

        @Override
        public double readBufferDouble(Object receiver_, ByteOrder order, long byteOffset) throws UnsupportedMessageException, InvalidBufferOffsetException {
            if (InteropLibraryGen.isDelegated(this.delegateLibrary, 78)) {
                Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
                return ((InteropLibrary)InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate)).readBufferDouble(delegate, order, byteOffset);
            }
            return this.delegateLibrary.readBufferDouble(receiver_, order, byteOffset);
        }

        @Override
        public void writeBufferDouble(Object receiver_, ByteOrder order, long byteOffset, double value2) throws UnsupportedMessageException, InvalidBufferOffsetException {
            if (InteropLibraryGen.isDelegated(this.delegateLibrary, 79)) {
                Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
                ((InteropLibrary)InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate)).writeBufferDouble(delegate, order, byteOffset, value2);
                return;
            }
            this.delegateLibrary.writeBufferDouble(receiver_, order, byteOffset, value2);
        }

        @Override
        public boolean isPointer(Object receiver_) {
            if (InteropLibraryGen.isDelegated(this.delegateLibrary, 80)) {
                Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
                return ((InteropLibrary)InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate)).isPointer(delegate);
            }
            return this.delegateLibrary.isPointer(receiver_);
        }

        @Override
        public long asPointer(Object receiver_) throws UnsupportedMessageException {
            if (InteropLibraryGen.isDelegated(this.delegateLibrary, 81)) {
                Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
                return ((InteropLibrary)InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate)).asPointer(delegate);
            }
            return this.delegateLibrary.asPointer(receiver_);
        }

        @Override
        public void toNative(Object receiver_) {
            if (InteropLibraryGen.isDelegated(this.delegateLibrary, 82)) {
                Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
                ((InteropLibrary)InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate)).toNative(delegate);
                return;
            }
            this.delegateLibrary.toNative(receiver_);
        }

        @Override
        public Instant asInstant(Object receiver_) throws UnsupportedMessageException {
            if (InteropLibraryGen.isDelegated(this.delegateLibrary, 83)) {
                Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
                return ((InteropLibrary)InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate)).asInstant(delegate);
            }
            return this.delegateLibrary.asInstant(receiver_);
        }

        @Override
        public boolean isTimeZone(Object receiver_) {
            if (InteropLibraryGen.isDelegated(this.delegateLibrary, 84)) {
                Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
                return ((InteropLibrary)InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate)).isTimeZone(delegate);
            }
            return this.delegateLibrary.isTimeZone(receiver_);
        }

        @Override
        public ZoneId asTimeZone(Object receiver_) throws UnsupportedMessageException {
            if (InteropLibraryGen.isDelegated(this.delegateLibrary, 85)) {
                Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
                return ((InteropLibrary)InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate)).asTimeZone(delegate);
            }
            return this.delegateLibrary.asTimeZone(receiver_);
        }

        @Override
        public boolean isDate(Object receiver_) {
            if (InteropLibraryGen.isDelegated(this.delegateLibrary, 86)) {
                Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
                return ((InteropLibrary)InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate)).isDate(delegate);
            }
            return this.delegateLibrary.isDate(receiver_);
        }

        @Override
        public LocalDate asDate(Object receiver_) throws UnsupportedMessageException {
            if (InteropLibraryGen.isDelegated(this.delegateLibrary, 87)) {
                Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
                return ((InteropLibrary)InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate)).asDate(delegate);
            }
            return this.delegateLibrary.asDate(receiver_);
        }

        @Override
        public boolean isTime(Object receiver_) {
            if (InteropLibraryGen.isDelegated(this.delegateLibrary, 88)) {
                Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
                return ((InteropLibrary)InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate)).isTime(delegate);
            }
            return this.delegateLibrary.isTime(receiver_);
        }

        @Override
        public LocalTime asTime(Object receiver_) throws UnsupportedMessageException {
            if (InteropLibraryGen.isDelegated(this.delegateLibrary, 89)) {
                Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
                return ((InteropLibrary)InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate)).asTime(delegate);
            }
            return this.delegateLibrary.asTime(receiver_);
        }

        @Override
        public boolean isDuration(Object receiver_) {
            if (InteropLibraryGen.isDelegated(this.delegateLibrary, 90)) {
                Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
                return ((InteropLibrary)InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate)).isDuration(delegate);
            }
            return this.delegateLibrary.isDuration(receiver_);
        }

        @Override
        public Duration asDuration(Object receiver_) throws UnsupportedMessageException {
            if (InteropLibraryGen.isDelegated(this.delegateLibrary, 91)) {
                Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
                return ((InteropLibrary)InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate)).asDuration(delegate);
            }
            return this.delegateLibrary.asDuration(receiver_);
        }

        @Override
        public boolean isException(Object receiver_) {
            if (InteropLibraryGen.isDelegated(this.delegateLibrary, 92)) {
                Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
                return ((InteropLibrary)InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate)).isException(delegate);
            }
            return this.delegateLibrary.isException(receiver_);
        }

        @Override
        public RuntimeException throwException(Object receiver_) throws UnsupportedMessageException {
            if (InteropLibraryGen.isDelegated(this.delegateLibrary, 93)) {
                Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
                return ((InteropLibrary)InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate)).throwException(delegate);
            }
            return this.delegateLibrary.throwException(receiver_);
        }

        @Override
        public ExceptionType getExceptionType(Object receiver_) throws UnsupportedMessageException {
            if (InteropLibraryGen.isDelegated(this.delegateLibrary, 94)) {
                Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
                return ((InteropLibrary)InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate)).getExceptionType(delegate);
            }
            return this.delegateLibrary.getExceptionType(receiver_);
        }

        @Override
        public boolean isExceptionIncompleteSource(Object receiver_) throws UnsupportedMessageException {
            if (InteropLibraryGen.isDelegated(this.delegateLibrary, 95)) {
                Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
                return ((InteropLibrary)InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate)).isExceptionIncompleteSource(delegate);
            }
            return this.delegateLibrary.isExceptionIncompleteSource(receiver_);
        }

        @Override
        public int getExceptionExitStatus(Object receiver_) throws UnsupportedMessageException {
            if (InteropLibraryGen.isDelegated(this.delegateLibrary, 96)) {
                Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
                return ((InteropLibrary)InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate)).getExceptionExitStatus(delegate);
            }
            return this.delegateLibrary.getExceptionExitStatus(receiver_);
        }

        @Override
        public boolean hasExceptionCause(Object receiver_) {
            if (InteropLibraryGen.isDelegated(this.delegateLibrary, 97)) {
                Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
                return ((InteropLibrary)InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate)).hasExceptionCause(delegate);
            }
            return this.delegateLibrary.hasExceptionCause(receiver_);
        }

        @Override
        public Object getExceptionCause(Object receiver_) throws UnsupportedMessageException {
            if (InteropLibraryGen.isDelegated(this.delegateLibrary, 98)) {
                Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
                return ((InteropLibrary)InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate)).getExceptionCause(delegate);
            }
            return this.delegateLibrary.getExceptionCause(receiver_);
        }

        @Override
        public boolean hasExceptionMessage(Object receiver_) {
            if (InteropLibraryGen.isDelegated(this.delegateLibrary, 99)) {
                Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
                return ((InteropLibrary)InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate)).hasExceptionMessage(delegate);
            }
            return this.delegateLibrary.hasExceptionMessage(receiver_);
        }

        @Override
        public Object getExceptionMessage(Object receiver_) throws UnsupportedMessageException {
            if (InteropLibraryGen.isDelegated(this.delegateLibrary, 100)) {
                Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
                return ((InteropLibrary)InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate)).getExceptionMessage(delegate);
            }
            return this.delegateLibrary.getExceptionMessage(receiver_);
        }

        @Override
        public boolean hasExceptionStackTrace(Object receiver_) {
            if (InteropLibraryGen.isDelegated(this.delegateLibrary, 101)) {
                Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
                return ((InteropLibrary)InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate)).hasExceptionStackTrace(delegate);
            }
            return this.delegateLibrary.hasExceptionStackTrace(receiver_);
        }

        @Override
        public Object getExceptionStackTrace(Object receiver_) throws UnsupportedMessageException {
            if (InteropLibraryGen.isDelegated(this.delegateLibrary, 102)) {
                Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
                return ((InteropLibrary)InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate)).getExceptionStackTrace(delegate);
            }
            return this.delegateLibrary.getExceptionStackTrace(receiver_);
        }

        @Override
        public boolean hasIterator(Object receiver_) {
            if (InteropLibraryGen.isDelegated(this.delegateLibrary, 103)) {
                Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
                return ((InteropLibrary)InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate)).hasIterator(delegate);
            }
            return this.delegateLibrary.hasIterator(receiver_);
        }

        @Override
        public Object getIterator(Object receiver_) throws UnsupportedMessageException {
            if (InteropLibraryGen.isDelegated(this.delegateLibrary, 104)) {
                Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
                return ((InteropLibrary)InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate)).getIterator(delegate);
            }
            return this.delegateLibrary.getIterator(receiver_);
        }

        @Override
        public boolean isIterator(Object receiver_) {
            if (InteropLibraryGen.isDelegated(this.delegateLibrary, 105)) {
                Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
                return ((InteropLibrary)InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate)).isIterator(delegate);
            }
            return this.delegateLibrary.isIterator(receiver_);
        }

        @Override
        public boolean hasIteratorNextElement(Object receiver_) throws UnsupportedMessageException {
            if (InteropLibraryGen.isDelegated(this.delegateLibrary, 106)) {
                Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
                return ((InteropLibrary)InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate)).hasIteratorNextElement(delegate);
            }
            return this.delegateLibrary.hasIteratorNextElement(receiver_);
        }

        @Override
        public Object getIteratorNextElement(Object receiver_) throws UnsupportedMessageException, StopIterationException {
            if (InteropLibraryGen.isDelegated(this.delegateLibrary, 107)) {
                Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
                return ((InteropLibrary)InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate)).getIteratorNextElement(delegate);
            }
            return this.delegateLibrary.getIteratorNextElement(receiver_);
        }

        @Override
        public boolean hasSourceLocation(Object receiver_) {
            if (InteropLibraryGen.isDelegated(this.delegateLibrary, 108)) {
                Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
                return ((InteropLibrary)InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate)).hasSourceLocation(delegate);
            }
            return this.delegateLibrary.hasSourceLocation(receiver_);
        }

        @Override
        public SourceSection getSourceLocation(Object receiver_) throws UnsupportedMessageException {
            if (InteropLibraryGen.isDelegated(this.delegateLibrary, 109)) {
                Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
                return ((InteropLibrary)InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate)).getSourceLocation(delegate);
            }
            return this.delegateLibrary.getSourceLocation(receiver_);
        }

        @Override
        public boolean hasLanguage(Object receiver_) {
            if (InteropLibraryGen.isDelegated(this.delegateLibrary, 110)) {
                Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
                return ((InteropLibrary)InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate)).hasLanguage(delegate);
            }
            return this.delegateLibrary.hasLanguage(receiver_);
        }

        @Override
        public Class<? extends TruffleLanguage<?>> getLanguage(Object receiver_) throws UnsupportedMessageException {
            if (InteropLibraryGen.isDelegated(this.delegateLibrary, 111)) {
                Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
                return ((InteropLibrary)InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate)).getLanguage(delegate);
            }
            return this.delegateLibrary.getLanguage(receiver_);
        }

        @Override
        public boolean hasMetaObject(Object receiver_) {
            if (InteropLibraryGen.isDelegated(this.delegateLibrary, 112)) {
                Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
                return ((InteropLibrary)InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate)).hasMetaObject(delegate);
            }
            return this.delegateLibrary.hasMetaObject(receiver_);
        }

        @Override
        public Object getMetaObject(Object receiver_) throws UnsupportedMessageException {
            if (InteropLibraryGen.isDelegated(this.delegateLibrary, 113)) {
                Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
                return ((InteropLibrary)InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate)).getMetaObject(delegate);
            }
            return this.delegateLibrary.getMetaObject(receiver_);
        }

        @Override
        public Object toDisplayString(Object receiver_, boolean allowSideEffects) {
            if (InteropLibraryGen.isDelegated(this.delegateLibrary, 114)) {
                Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
                return ((InteropLibrary)InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate)).toDisplayString(delegate, allowSideEffects);
            }
            return this.delegateLibrary.toDisplayString(receiver_, allowSideEffects);
        }

        @Override
        public boolean isMetaObject(Object receiver_) {
            if (InteropLibraryGen.isDelegated(this.delegateLibrary, 115)) {
                Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
                return ((InteropLibrary)InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate)).isMetaObject(delegate);
            }
            return this.delegateLibrary.isMetaObject(receiver_);
        }

        @Override
        public Object getMetaQualifiedName(Object receiver_) throws UnsupportedMessageException {
            if (InteropLibraryGen.isDelegated(this.delegateLibrary, 116)) {
                Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
                return ((InteropLibrary)InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate)).getMetaQualifiedName(delegate);
            }
            return this.delegateLibrary.getMetaQualifiedName(receiver_);
        }

        @Override
        public Object getMetaSimpleName(Object receiver_) throws UnsupportedMessageException {
            if (InteropLibraryGen.isDelegated(this.delegateLibrary, 117)) {
                Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
                return ((InteropLibrary)InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate)).getMetaSimpleName(delegate);
            }
            return this.delegateLibrary.getMetaSimpleName(receiver_);
        }

        @Override
        public boolean isMetaInstance(Object receiver_, Object instance) throws UnsupportedMessageException {
            if (InteropLibraryGen.isDelegated(this.delegateLibrary, 118)) {
                Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
                return ((InteropLibrary)InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate)).isMetaInstance(delegate, instance);
            }
            return this.delegateLibrary.isMetaInstance(receiver_, instance);
        }

        @Override
        public boolean hasMetaParents(Object receiver_) {
            if (InteropLibraryGen.isDelegated(this.delegateLibrary, 119)) {
                Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
                return ((InteropLibrary)InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate)).hasMetaParents(delegate);
            }
            return this.delegateLibrary.hasMetaParents(receiver_);
        }

        @Override
        public Object getMetaParents(Object receiver_) throws UnsupportedMessageException {
            if (InteropLibraryGen.isDelegated(this.delegateLibrary, 120)) {
                Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
                return ((InteropLibrary)InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate)).getMetaParents(delegate);
            }
            return this.delegateLibrary.getMetaParents(receiver_);
        }

        @Override
        protected TriState isIdenticalOrUndefined(Object receiver_, Object other) {
            if (InteropLibraryGen.isDelegated(this.delegateLibrary, 121)) {
                Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
                return ((InteropLibrary)InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate)).isIdenticalOrUndefined(delegate, other);
            }
            return this.delegateLibrary.isIdenticalOrUndefined(receiver_, other);
        }

        @Override
        public boolean isIdentical(Object receiver_, Object other, InteropLibrary otherInterop) {
            if (InteropLibraryGen.isDelegated(this.delegateLibrary, 122)) {
                Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
                return ((InteropLibrary)InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate)).isIdentical(delegate, other, otherInterop);
            }
            return this.delegateLibrary.isIdentical(receiver_, other, otherInterop);
        }

        @Override
        public int identityHashCode(Object receiver_) throws UnsupportedMessageException {
            if (InteropLibraryGen.isDelegated(this.delegateLibrary, 123)) {
                Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
                return ((InteropLibrary)InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate)).identityHashCode(delegate);
            }
            return this.delegateLibrary.identityHashCode(receiver_);
        }

        @Override
        public boolean isScope(Object receiver_) {
            if (InteropLibraryGen.isDelegated(this.delegateLibrary, 124)) {
                Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
                return ((InteropLibrary)InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate)).isScope(delegate);
            }
            return this.delegateLibrary.isScope(receiver_);
        }

        @Override
        public boolean hasScopeParent(Object receiver_) {
            if (InteropLibraryGen.isDelegated(this.delegateLibrary, 125)) {
                Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
                return ((InteropLibrary)InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate)).hasScopeParent(delegate);
            }
            return this.delegateLibrary.hasScopeParent(receiver_);
        }

        @Override
        public Object getScopeParent(Object receiver_) throws UnsupportedMessageException {
            if (InteropLibraryGen.isDelegated(this.delegateLibrary, 126)) {
                Object delegate = InteropLibraryGen.readDelegate(this.delegateLibrary, receiver_);
                return ((InteropLibrary)InteropLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate)).getScopeParent(delegate);
            }
            return this.delegateLibrary.getScopeParent(receiver_);
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

    @GeneratedBy(value=InteropLibrary.class)
    private static final class Proxy
    extends InteropLibrary {
        @Node.Child
        private ReflectionLibrary lib;

        Proxy(ReflectionLibrary lib) {
            this.lib = lib;
        }

        @Override
        public boolean isNull(Object receiver_) {
            try {
                return (Boolean)this.lib.send(receiver_, IS_NULL, new Object[0]);
            }
            catch (RuntimeException e_) {
                throw e_;
            }
            catch (Exception e_) {
                throw CompilerDirectives.shouldNotReachHere(e_);
            }
        }

        @Override
        public boolean isBoolean(Object receiver_) {
            try {
                return (Boolean)this.lib.send(receiver_, IS_BOOLEAN, new Object[0]);
            }
            catch (RuntimeException e_) {
                throw e_;
            }
            catch (Exception e_) {
                throw CompilerDirectives.shouldNotReachHere(e_);
            }
        }

        @Override
        public boolean asBoolean(Object receiver_) throws UnsupportedMessageException {
            try {
                return (Boolean)this.lib.send(receiver_, AS_BOOLEAN, new Object[0]);
            }
            catch (UnsupportedMessageException | RuntimeException e_) {
                throw e_;
            }
            catch (Exception e_) {
                throw CompilerDirectives.shouldNotReachHere(e_);
            }
        }

        @Override
        public boolean isExecutable(Object receiver_) {
            try {
                return (Boolean)this.lib.send(receiver_, IS_EXECUTABLE, new Object[0]);
            }
            catch (RuntimeException e_) {
                throw e_;
            }
            catch (Exception e_) {
                throw CompilerDirectives.shouldNotReachHere(e_);
            }
        }

        @Override
        public Object execute(Object receiver_, Object ... arguments) throws UnsupportedTypeException, ArityException, UnsupportedMessageException {
            try {
                return this.lib.send(receiver_, EXECUTE, new Object[]{arguments});
            }
            catch (ArityException | UnsupportedMessageException | UnsupportedTypeException | RuntimeException e_) {
                throw e_;
            }
            catch (Exception e_) {
                throw CompilerDirectives.shouldNotReachHere(e_);
            }
        }

        @Override
        public boolean hasExecutableName(Object receiver_) {
            try {
                return (Boolean)this.lib.send(receiver_, HAS_EXECUTABLE_NAME, new Object[0]);
            }
            catch (RuntimeException e_) {
                throw e_;
            }
            catch (Exception e_) {
                throw CompilerDirectives.shouldNotReachHere(e_);
            }
        }

        @Override
        public Object getExecutableName(Object receiver_) throws UnsupportedMessageException {
            try {
                return this.lib.send(receiver_, GET_EXECUTABLE_NAME, new Object[0]);
            }
            catch (UnsupportedMessageException | RuntimeException e_) {
                throw e_;
            }
            catch (Exception e_) {
                throw CompilerDirectives.shouldNotReachHere(e_);
            }
        }

        @Override
        public boolean hasDeclaringMetaObject(Object receiver_) {
            try {
                return (Boolean)this.lib.send(receiver_, HAS_DECLARING_META_OBJECT, new Object[0]);
            }
            catch (RuntimeException e_) {
                throw e_;
            }
            catch (Exception e_) {
                throw CompilerDirectives.shouldNotReachHere(e_);
            }
        }

        @Override
        public Object getDeclaringMetaObject(Object receiver_) throws UnsupportedMessageException {
            try {
                return this.lib.send(receiver_, GET_DECLARING_META_OBJECT, new Object[0]);
            }
            catch (UnsupportedMessageException | RuntimeException e_) {
                throw e_;
            }
            catch (Exception e_) {
                throw CompilerDirectives.shouldNotReachHere(e_);
            }
        }

        @Override
        public boolean isInstantiable(Object receiver_) {
            try {
                return (Boolean)this.lib.send(receiver_, IS_INSTANTIABLE, new Object[0]);
            }
            catch (RuntimeException e_) {
                throw e_;
            }
            catch (Exception e_) {
                throw CompilerDirectives.shouldNotReachHere(e_);
            }
        }

        @Override
        public Object instantiate(Object receiver_, Object ... arguments) throws UnsupportedTypeException, ArityException, UnsupportedMessageException {
            try {
                return this.lib.send(receiver_, INSTANTIATE, new Object[]{arguments});
            }
            catch (ArityException | UnsupportedMessageException | UnsupportedTypeException | RuntimeException e_) {
                throw e_;
            }
            catch (Exception e_) {
                throw CompilerDirectives.shouldNotReachHere(e_);
            }
        }

        @Override
        public boolean isString(Object receiver_) {
            try {
                return (Boolean)this.lib.send(receiver_, IS_STRING, new Object[0]);
            }
            catch (RuntimeException e_) {
                throw e_;
            }
            catch (Exception e_) {
                throw CompilerDirectives.shouldNotReachHere(e_);
            }
        }

        @Override
        public String asString(Object receiver_) throws UnsupportedMessageException {
            try {
                return (String)this.lib.send(receiver_, AS_STRING, new Object[0]);
            }
            catch (UnsupportedMessageException | RuntimeException e_) {
                throw e_;
            }
            catch (Exception e_) {
                throw CompilerDirectives.shouldNotReachHere(e_);
            }
        }

        @Override
        public TruffleString asTruffleString(Object receiver_) throws UnsupportedMessageException {
            try {
                return (TruffleString)this.lib.send(receiver_, AS_TRUFFLE_STRING, new Object[0]);
            }
            catch (UnsupportedMessageException | RuntimeException e_) {
                throw e_;
            }
            catch (Exception e_) {
                throw CompilerDirectives.shouldNotReachHere(e_);
            }
        }

        @Override
        public boolean isNumber(Object receiver_) {
            try {
                return (Boolean)this.lib.send(receiver_, IS_NUMBER, new Object[0]);
            }
            catch (RuntimeException e_) {
                throw e_;
            }
            catch (Exception e_) {
                throw CompilerDirectives.shouldNotReachHere(e_);
            }
        }

        @Override
        public boolean fitsInByte(Object receiver_) {
            try {
                return (Boolean)this.lib.send(receiver_, FITS_IN_BYTE, new Object[0]);
            }
            catch (RuntimeException e_) {
                throw e_;
            }
            catch (Exception e_) {
                throw CompilerDirectives.shouldNotReachHere(e_);
            }
        }

        @Override
        public boolean fitsInShort(Object receiver_) {
            try {
                return (Boolean)this.lib.send(receiver_, FITS_IN_SHORT, new Object[0]);
            }
            catch (RuntimeException e_) {
                throw e_;
            }
            catch (Exception e_) {
                throw CompilerDirectives.shouldNotReachHere(e_);
            }
        }

        @Override
        public boolean fitsInInt(Object receiver_) {
            try {
                return (Boolean)this.lib.send(receiver_, FITS_IN_INT, new Object[0]);
            }
            catch (RuntimeException e_) {
                throw e_;
            }
            catch (Exception e_) {
                throw CompilerDirectives.shouldNotReachHere(e_);
            }
        }

        @Override
        public boolean fitsInLong(Object receiver_) {
            try {
                return (Boolean)this.lib.send(receiver_, FITS_IN_LONG, new Object[0]);
            }
            catch (RuntimeException e_) {
                throw e_;
            }
            catch (Exception e_) {
                throw CompilerDirectives.shouldNotReachHere(e_);
            }
        }

        @Override
        public boolean fitsInFloat(Object receiver_) {
            try {
                return (Boolean)this.lib.send(receiver_, FITS_IN_FLOAT, new Object[0]);
            }
            catch (RuntimeException e_) {
                throw e_;
            }
            catch (Exception e_) {
                throw CompilerDirectives.shouldNotReachHere(e_);
            }
        }

        @Override
        public boolean fitsInDouble(Object receiver_) {
            try {
                return (Boolean)this.lib.send(receiver_, FITS_IN_DOUBLE, new Object[0]);
            }
            catch (RuntimeException e_) {
                throw e_;
            }
            catch (Exception e_) {
                throw CompilerDirectives.shouldNotReachHere(e_);
            }
        }

        @Override
        public byte asByte(Object receiver_) throws UnsupportedMessageException {
            try {
                return (Byte)this.lib.send(receiver_, AS_BYTE, new Object[0]);
            }
            catch (UnsupportedMessageException | RuntimeException e_) {
                throw e_;
            }
            catch (Exception e_) {
                throw CompilerDirectives.shouldNotReachHere(e_);
            }
        }

        @Override
        public short asShort(Object receiver_) throws UnsupportedMessageException {
            try {
                return (Short)this.lib.send(receiver_, AS_SHORT, new Object[0]);
            }
            catch (UnsupportedMessageException | RuntimeException e_) {
                throw e_;
            }
            catch (Exception e_) {
                throw CompilerDirectives.shouldNotReachHere(e_);
            }
        }

        @Override
        public int asInt(Object receiver_) throws UnsupportedMessageException {
            try {
                return (Integer)this.lib.send(receiver_, AS_INT, new Object[0]);
            }
            catch (UnsupportedMessageException | RuntimeException e_) {
                throw e_;
            }
            catch (Exception e_) {
                throw CompilerDirectives.shouldNotReachHere(e_);
            }
        }

        @Override
        public long asLong(Object receiver_) throws UnsupportedMessageException {
            try {
                return (Long)this.lib.send(receiver_, AS_LONG, new Object[0]);
            }
            catch (UnsupportedMessageException | RuntimeException e_) {
                throw e_;
            }
            catch (Exception e_) {
                throw CompilerDirectives.shouldNotReachHere(e_);
            }
        }

        @Override
        public float asFloat(Object receiver_) throws UnsupportedMessageException {
            try {
                return ((Float)this.lib.send(receiver_, AS_FLOAT, new Object[0])).floatValue();
            }
            catch (UnsupportedMessageException | RuntimeException e_) {
                throw e_;
            }
            catch (Exception e_) {
                throw CompilerDirectives.shouldNotReachHere(e_);
            }
        }

        @Override
        public double asDouble(Object receiver_) throws UnsupportedMessageException {
            try {
                return (Double)this.lib.send(receiver_, AS_DOUBLE, new Object[0]);
            }
            catch (UnsupportedMessageException | RuntimeException e_) {
                throw e_;
            }
            catch (Exception e_) {
                throw CompilerDirectives.shouldNotReachHere(e_);
            }
        }

        @Override
        public boolean hasMembers(Object receiver_) {
            try {
                return (Boolean)this.lib.send(receiver_, HAS_MEMBERS, new Object[0]);
            }
            catch (RuntimeException e_) {
                throw e_;
            }
            catch (Exception e_) {
                throw CompilerDirectives.shouldNotReachHere(e_);
            }
        }

        @Override
        public Object getMembers(Object receiver_, boolean includeInternal) throws UnsupportedMessageException {
            try {
                return this.lib.send(receiver_, GET_MEMBERS, includeInternal);
            }
            catch (UnsupportedMessageException | RuntimeException e_) {
                throw e_;
            }
            catch (Exception e_) {
                throw CompilerDirectives.shouldNotReachHere(e_);
            }
        }

        @Override
        public boolean isMemberReadable(Object receiver_, String member) {
            try {
                return (Boolean)this.lib.send(receiver_, IS_MEMBER_READABLE, member);
            }
            catch (RuntimeException e_) {
                throw e_;
            }
            catch (Exception e_) {
                throw CompilerDirectives.shouldNotReachHere(e_);
            }
        }

        @Override
        public Object readMember(Object receiver_, String member) throws UnsupportedMessageException, UnknownIdentifierException {
            try {
                return this.lib.send(receiver_, READ_MEMBER, member);
            }
            catch (UnknownIdentifierException | UnsupportedMessageException | RuntimeException e_) {
                throw e_;
            }
            catch (Exception e_) {
                throw CompilerDirectives.shouldNotReachHere(e_);
            }
        }

        @Override
        public boolean isMemberModifiable(Object receiver_, String member) {
            try {
                return (Boolean)this.lib.send(receiver_, IS_MEMBER_MODIFIABLE, member);
            }
            catch (RuntimeException e_) {
                throw e_;
            }
            catch (Exception e_) {
                throw CompilerDirectives.shouldNotReachHere(e_);
            }
        }

        @Override
        public boolean isMemberInsertable(Object receiver_, String member) {
            try {
                return (Boolean)this.lib.send(receiver_, IS_MEMBER_INSERTABLE, member);
            }
            catch (RuntimeException e_) {
                throw e_;
            }
            catch (Exception e_) {
                throw CompilerDirectives.shouldNotReachHere(e_);
            }
        }

        @Override
        public void writeMember(Object receiver_, String member, Object value2) throws UnsupportedMessageException, UnknownIdentifierException, UnsupportedTypeException {
            try {
                this.lib.send(receiver_, WRITE_MEMBER, member, value2);
                return;
            }
            catch (UnknownIdentifierException | UnsupportedMessageException | UnsupportedTypeException | RuntimeException e_) {
                throw e_;
            }
            catch (Exception e_) {
                throw CompilerDirectives.shouldNotReachHere(e_);
            }
        }

        @Override
        public boolean isMemberRemovable(Object receiver_, String member) {
            try {
                return (Boolean)this.lib.send(receiver_, IS_MEMBER_REMOVABLE, member);
            }
            catch (RuntimeException e_) {
                throw e_;
            }
            catch (Exception e_) {
                throw CompilerDirectives.shouldNotReachHere(e_);
            }
        }

        @Override
        public void removeMember(Object receiver_, String member) throws UnsupportedMessageException, UnknownIdentifierException {
            try {
                this.lib.send(receiver_, REMOVE_MEMBER, member);
                return;
            }
            catch (UnknownIdentifierException | UnsupportedMessageException | RuntimeException e_) {
                throw e_;
            }
            catch (Exception e_) {
                throw CompilerDirectives.shouldNotReachHere(e_);
            }
        }

        @Override
        public boolean isMemberInvocable(Object receiver_, String member) {
            try {
                return (Boolean)this.lib.send(receiver_, IS_MEMBER_INVOCABLE, member);
            }
            catch (RuntimeException e_) {
                throw e_;
            }
            catch (Exception e_) {
                throw CompilerDirectives.shouldNotReachHere(e_);
            }
        }

        @Override
        public Object invokeMember(Object receiver_, String member, Object ... arguments) throws UnsupportedMessageException, ArityException, UnknownIdentifierException, UnsupportedTypeException {
            try {
                return this.lib.send(receiver_, INVOKE_MEMBER, member, arguments);
            }
            catch (ArityException | UnknownIdentifierException | UnsupportedMessageException | UnsupportedTypeException | RuntimeException e_) {
                throw e_;
            }
            catch (Exception e_) {
                throw CompilerDirectives.shouldNotReachHere(e_);
            }
        }

        @Override
        public boolean isMemberInternal(Object receiver_, String member) {
            try {
                return (Boolean)this.lib.send(receiver_, IS_MEMBER_INTERNAL, member);
            }
            catch (RuntimeException e_) {
                throw e_;
            }
            catch (Exception e_) {
                throw CompilerDirectives.shouldNotReachHere(e_);
            }
        }

        @Override
        public boolean hasMemberReadSideEffects(Object receiver_, String member) {
            try {
                return (Boolean)this.lib.send(receiver_, HAS_MEMBER_READ_SIDE_EFFECTS, member);
            }
            catch (RuntimeException e_) {
                throw e_;
            }
            catch (Exception e_) {
                throw CompilerDirectives.shouldNotReachHere(e_);
            }
        }

        @Override
        public boolean hasMemberWriteSideEffects(Object receiver_, String member) {
            try {
                return (Boolean)this.lib.send(receiver_, HAS_MEMBER_WRITE_SIDE_EFFECTS, member);
            }
            catch (RuntimeException e_) {
                throw e_;
            }
            catch (Exception e_) {
                throw CompilerDirectives.shouldNotReachHere(e_);
            }
        }

        @Override
        public boolean hasHashEntries(Object receiver_) {
            try {
                return (Boolean)this.lib.send(receiver_, HAS_HASH_ENTRIES, new Object[0]);
            }
            catch (RuntimeException e_) {
                throw e_;
            }
            catch (Exception e_) {
                throw CompilerDirectives.shouldNotReachHere(e_);
            }
        }

        @Override
        public long getHashSize(Object receiver_) throws UnsupportedMessageException {
            try {
                return (Long)this.lib.send(receiver_, GET_HASH_SIZE, new Object[0]);
            }
            catch (UnsupportedMessageException | RuntimeException e_) {
                throw e_;
            }
            catch (Exception e_) {
                throw CompilerDirectives.shouldNotReachHere(e_);
            }
        }

        @Override
        public boolean isHashEntryReadable(Object receiver_, Object key) {
            try {
                return (Boolean)this.lib.send(receiver_, IS_HASH_ENTRY_READABLE, key);
            }
            catch (RuntimeException e_) {
                throw e_;
            }
            catch (Exception e_) {
                throw CompilerDirectives.shouldNotReachHere(e_);
            }
        }

        @Override
        public Object readHashValue(Object receiver_, Object key) throws UnsupportedMessageException, UnknownKeyException {
            try {
                return this.lib.send(receiver_, READ_HASH_VALUE, key);
            }
            catch (UnknownKeyException | UnsupportedMessageException | RuntimeException e_) {
                throw e_;
            }
            catch (Exception e_) {
                throw CompilerDirectives.shouldNotReachHere(e_);
            }
        }

        @Override
        public Object readHashValueOrDefault(Object receiver_, Object key, Object defaultValue) throws UnsupportedMessageException {
            try {
                return this.lib.send(receiver_, READ_HASH_VALUE_OR_DEFAULT, key, defaultValue);
            }
            catch (UnsupportedMessageException | RuntimeException e_) {
                throw e_;
            }
            catch (Exception e_) {
                throw CompilerDirectives.shouldNotReachHere(e_);
            }
        }

        @Override
        public boolean isHashEntryModifiable(Object receiver_, Object key) {
            try {
                return (Boolean)this.lib.send(receiver_, IS_HASH_ENTRY_MODIFIABLE, key);
            }
            catch (RuntimeException e_) {
                throw e_;
            }
            catch (Exception e_) {
                throw CompilerDirectives.shouldNotReachHere(e_);
            }
        }

        @Override
        public boolean isHashEntryInsertable(Object receiver_, Object key) {
            try {
                return (Boolean)this.lib.send(receiver_, IS_HASH_ENTRY_INSERTABLE, key);
            }
            catch (RuntimeException e_) {
                throw e_;
            }
            catch (Exception e_) {
                throw CompilerDirectives.shouldNotReachHere(e_);
            }
        }

        @Override
        public boolean isHashEntryWritable(Object receiver_, Object key) {
            try {
                return (Boolean)this.lib.send(receiver_, IS_HASH_ENTRY_WRITABLE, key);
            }
            catch (RuntimeException e_) {
                throw e_;
            }
            catch (Exception e_) {
                throw CompilerDirectives.shouldNotReachHere(e_);
            }
        }

        @Override
        public void writeHashEntry(Object receiver_, Object key, Object value2) throws UnsupportedMessageException, UnknownKeyException, UnsupportedTypeException {
            try {
                this.lib.send(receiver_, WRITE_HASH_ENTRY, key, value2);
                return;
            }
            catch (UnknownKeyException | UnsupportedMessageException | UnsupportedTypeException | RuntimeException e_) {
                throw e_;
            }
            catch (Exception e_) {
                throw CompilerDirectives.shouldNotReachHere(e_);
            }
        }

        @Override
        public boolean isHashEntryRemovable(Object receiver_, Object key) {
            try {
                return (Boolean)this.lib.send(receiver_, IS_HASH_ENTRY_REMOVABLE, key);
            }
            catch (RuntimeException e_) {
                throw e_;
            }
            catch (Exception e_) {
                throw CompilerDirectives.shouldNotReachHere(e_);
            }
        }

        @Override
        public void removeHashEntry(Object receiver_, Object key) throws UnsupportedMessageException, UnknownKeyException {
            try {
                this.lib.send(receiver_, REMOVE_HASH_ENTRY, key);
                return;
            }
            catch (UnknownKeyException | UnsupportedMessageException | RuntimeException e_) {
                throw e_;
            }
            catch (Exception e_) {
                throw CompilerDirectives.shouldNotReachHere(e_);
            }
        }

        @Override
        public boolean isHashEntryExisting(Object receiver_, Object key) {
            try {
                return (Boolean)this.lib.send(receiver_, IS_HASH_ENTRY_EXISTING, key);
            }
            catch (RuntimeException e_) {
                throw e_;
            }
            catch (Exception e_) {
                throw CompilerDirectives.shouldNotReachHere(e_);
            }
        }

        @Override
        public Object getHashEntriesIterator(Object receiver_) throws UnsupportedMessageException {
            try {
                return this.lib.send(receiver_, GET_HASH_ENTRIES_ITERATOR, new Object[0]);
            }
            catch (UnsupportedMessageException | RuntimeException e_) {
                throw e_;
            }
            catch (Exception e_) {
                throw CompilerDirectives.shouldNotReachHere(e_);
            }
        }

        @Override
        public Object getHashKeysIterator(Object receiver_) throws UnsupportedMessageException {
            try {
                return this.lib.send(receiver_, GET_HASH_KEYS_ITERATOR, new Object[0]);
            }
            catch (UnsupportedMessageException | RuntimeException e_) {
                throw e_;
            }
            catch (Exception e_) {
                throw CompilerDirectives.shouldNotReachHere(e_);
            }
        }

        @Override
        public Object getHashValuesIterator(Object receiver_) throws UnsupportedMessageException {
            try {
                return this.lib.send(receiver_, GET_HASH_VALUES_ITERATOR, new Object[0]);
            }
            catch (UnsupportedMessageException | RuntimeException e_) {
                throw e_;
            }
            catch (Exception e_) {
                throw CompilerDirectives.shouldNotReachHere(e_);
            }
        }

        @Override
        public boolean hasArrayElements(Object receiver_) {
            try {
                return (Boolean)this.lib.send(receiver_, HAS_ARRAY_ELEMENTS, new Object[0]);
            }
            catch (RuntimeException e_) {
                throw e_;
            }
            catch (Exception e_) {
                throw CompilerDirectives.shouldNotReachHere(e_);
            }
        }

        @Override
        public Object readArrayElement(Object receiver_, long index) throws UnsupportedMessageException, InvalidArrayIndexException {
            try {
                return this.lib.send(receiver_, READ_ARRAY_ELEMENT, index);
            }
            catch (InvalidArrayIndexException | UnsupportedMessageException | RuntimeException e_) {
                throw e_;
            }
            catch (Exception e_) {
                throw CompilerDirectives.shouldNotReachHere(e_);
            }
        }

        @Override
        public long getArraySize(Object receiver_) throws UnsupportedMessageException {
            try {
                return (Long)this.lib.send(receiver_, GET_ARRAY_SIZE, new Object[0]);
            }
            catch (UnsupportedMessageException | RuntimeException e_) {
                throw e_;
            }
            catch (Exception e_) {
                throw CompilerDirectives.shouldNotReachHere(e_);
            }
        }

        @Override
        public boolean isArrayElementReadable(Object receiver_, long index) {
            try {
                return (Boolean)this.lib.send(receiver_, IS_ARRAY_ELEMENT_READABLE, index);
            }
            catch (RuntimeException e_) {
                throw e_;
            }
            catch (Exception e_) {
                throw CompilerDirectives.shouldNotReachHere(e_);
            }
        }

        @Override
        public void writeArrayElement(Object receiver_, long index, Object value2) throws UnsupportedMessageException, UnsupportedTypeException, InvalidArrayIndexException {
            try {
                this.lib.send(receiver_, WRITE_ARRAY_ELEMENT, index, value2);
                return;
            }
            catch (InvalidArrayIndexException | UnsupportedMessageException | UnsupportedTypeException | RuntimeException e_) {
                throw e_;
            }
            catch (Exception e_) {
                throw CompilerDirectives.shouldNotReachHere(e_);
            }
        }

        @Override
        public void removeArrayElement(Object receiver_, long index) throws UnsupportedMessageException, InvalidArrayIndexException {
            try {
                this.lib.send(receiver_, REMOVE_ARRAY_ELEMENT, index);
                return;
            }
            catch (InvalidArrayIndexException | UnsupportedMessageException | RuntimeException e_) {
                throw e_;
            }
            catch (Exception e_) {
                throw CompilerDirectives.shouldNotReachHere(e_);
            }
        }

        @Override
        public boolean isArrayElementModifiable(Object receiver_, long index) {
            try {
                return (Boolean)this.lib.send(receiver_, IS_ARRAY_ELEMENT_MODIFIABLE, index);
            }
            catch (RuntimeException e_) {
                throw e_;
            }
            catch (Exception e_) {
                throw CompilerDirectives.shouldNotReachHere(e_);
            }
        }

        @Override
        public boolean isArrayElementInsertable(Object receiver_, long index) {
            try {
                return (Boolean)this.lib.send(receiver_, IS_ARRAY_ELEMENT_INSERTABLE, index);
            }
            catch (RuntimeException e_) {
                throw e_;
            }
            catch (Exception e_) {
                throw CompilerDirectives.shouldNotReachHere(e_);
            }
        }

        @Override
        public boolean isArrayElementRemovable(Object receiver_, long index) {
            try {
                return (Boolean)this.lib.send(receiver_, IS_ARRAY_ELEMENT_REMOVABLE, index);
            }
            catch (RuntimeException e_) {
                throw e_;
            }
            catch (Exception e_) {
                throw CompilerDirectives.shouldNotReachHere(e_);
            }
        }

        @Override
        public boolean hasBufferElements(Object receiver_) {
            try {
                return (Boolean)this.lib.send(receiver_, HAS_BUFFER_ELEMENTS, new Object[0]);
            }
            catch (RuntimeException e_) {
                throw e_;
            }
            catch (Exception e_) {
                throw CompilerDirectives.shouldNotReachHere(e_);
            }
        }

        @Override
        public boolean isBufferWritable(Object receiver_) throws UnsupportedMessageException {
            try {
                return (Boolean)this.lib.send(receiver_, IS_BUFFER_WRITABLE, new Object[0]);
            }
            catch (UnsupportedMessageException | RuntimeException e_) {
                throw e_;
            }
            catch (Exception e_) {
                throw CompilerDirectives.shouldNotReachHere(e_);
            }
        }

        @Override
        public long getBufferSize(Object receiver_) throws UnsupportedMessageException {
            try {
                return (Long)this.lib.send(receiver_, GET_BUFFER_SIZE, new Object[0]);
            }
            catch (UnsupportedMessageException | RuntimeException e_) {
                throw e_;
            }
            catch (Exception e_) {
                throw CompilerDirectives.shouldNotReachHere(e_);
            }
        }

        @Override
        public byte readBufferByte(Object receiver_, long byteOffset) throws UnsupportedMessageException, InvalidBufferOffsetException {
            try {
                return (Byte)this.lib.send(receiver_, READ_BUFFER_BYTE, byteOffset);
            }
            catch (InvalidBufferOffsetException | UnsupportedMessageException | RuntimeException e_) {
                throw e_;
            }
            catch (Exception e_) {
                throw CompilerDirectives.shouldNotReachHere(e_);
            }
        }

        @Override
        public void writeBufferByte(Object receiver_, long byteOffset, byte value2) throws UnsupportedMessageException, InvalidBufferOffsetException {
            try {
                this.lib.send(receiver_, WRITE_BUFFER_BYTE, byteOffset, value2);
                return;
            }
            catch (InvalidBufferOffsetException | UnsupportedMessageException | RuntimeException e_) {
                throw e_;
            }
            catch (Exception e_) {
                throw CompilerDirectives.shouldNotReachHere(e_);
            }
        }

        @Override
        public short readBufferShort(Object receiver_, ByteOrder order, long byteOffset) throws UnsupportedMessageException, InvalidBufferOffsetException {
            try {
                return (Short)this.lib.send(receiver_, READ_BUFFER_SHORT, order, byteOffset);
            }
            catch (InvalidBufferOffsetException | UnsupportedMessageException | RuntimeException e_) {
                throw e_;
            }
            catch (Exception e_) {
                throw CompilerDirectives.shouldNotReachHere(e_);
            }
        }

        @Override
        public void writeBufferShort(Object receiver_, ByteOrder order, long byteOffset, short value2) throws UnsupportedMessageException, InvalidBufferOffsetException {
            try {
                this.lib.send(receiver_, WRITE_BUFFER_SHORT, order, byteOffset, value2);
                return;
            }
            catch (InvalidBufferOffsetException | UnsupportedMessageException | RuntimeException e_) {
                throw e_;
            }
            catch (Exception e_) {
                throw CompilerDirectives.shouldNotReachHere(e_);
            }
        }

        @Override
        public int readBufferInt(Object receiver_, ByteOrder order, long byteOffset) throws UnsupportedMessageException, InvalidBufferOffsetException {
            try {
                return (Integer)this.lib.send(receiver_, READ_BUFFER_INT, order, byteOffset);
            }
            catch (InvalidBufferOffsetException | UnsupportedMessageException | RuntimeException e_) {
                throw e_;
            }
            catch (Exception e_) {
                throw CompilerDirectives.shouldNotReachHere(e_);
            }
        }

        @Override
        public void writeBufferInt(Object receiver_, ByteOrder order, long byteOffset, int value2) throws UnsupportedMessageException, InvalidBufferOffsetException {
            try {
                this.lib.send(receiver_, WRITE_BUFFER_INT, order, byteOffset, value2);
                return;
            }
            catch (InvalidBufferOffsetException | UnsupportedMessageException | RuntimeException e_) {
                throw e_;
            }
            catch (Exception e_) {
                throw CompilerDirectives.shouldNotReachHere(e_);
            }
        }

        @Override
        public long readBufferLong(Object receiver_, ByteOrder order, long byteOffset) throws UnsupportedMessageException, InvalidBufferOffsetException {
            try {
                return (Long)this.lib.send(receiver_, READ_BUFFER_LONG, order, byteOffset);
            }
            catch (InvalidBufferOffsetException | UnsupportedMessageException | RuntimeException e_) {
                throw e_;
            }
            catch (Exception e_) {
                throw CompilerDirectives.shouldNotReachHere(e_);
            }
        }

        @Override
        public void writeBufferLong(Object receiver_, ByteOrder order, long byteOffset, long value2) throws UnsupportedMessageException, InvalidBufferOffsetException {
            try {
                this.lib.send(receiver_, WRITE_BUFFER_LONG, order, byteOffset, value2);
                return;
            }
            catch (InvalidBufferOffsetException | UnsupportedMessageException | RuntimeException e_) {
                throw e_;
            }
            catch (Exception e_) {
                throw CompilerDirectives.shouldNotReachHere(e_);
            }
        }

        @Override
        public float readBufferFloat(Object receiver_, ByteOrder order, long byteOffset) throws UnsupportedMessageException, InvalidBufferOffsetException {
            try {
                return ((Float)this.lib.send(receiver_, READ_BUFFER_FLOAT, order, byteOffset)).floatValue();
            }
            catch (InvalidBufferOffsetException | UnsupportedMessageException | RuntimeException e_) {
                throw e_;
            }
            catch (Exception e_) {
                throw CompilerDirectives.shouldNotReachHere(e_);
            }
        }

        @Override
        public void writeBufferFloat(Object receiver_, ByteOrder order, long byteOffset, float value2) throws UnsupportedMessageException, InvalidBufferOffsetException {
            try {
                this.lib.send(receiver_, WRITE_BUFFER_FLOAT, order, byteOffset, Float.valueOf(value2));
                return;
            }
            catch (InvalidBufferOffsetException | UnsupportedMessageException | RuntimeException e_) {
                throw e_;
            }
            catch (Exception e_) {
                throw CompilerDirectives.shouldNotReachHere(e_);
            }
        }

        @Override
        public double readBufferDouble(Object receiver_, ByteOrder order, long byteOffset) throws UnsupportedMessageException, InvalidBufferOffsetException {
            try {
                return (Double)this.lib.send(receiver_, READ_BUFFER_DOUBLE, order, byteOffset);
            }
            catch (InvalidBufferOffsetException | UnsupportedMessageException | RuntimeException e_) {
                throw e_;
            }
            catch (Exception e_) {
                throw CompilerDirectives.shouldNotReachHere(e_);
            }
        }

        @Override
        public void writeBufferDouble(Object receiver_, ByteOrder order, long byteOffset, double value2) throws UnsupportedMessageException, InvalidBufferOffsetException {
            try {
                this.lib.send(receiver_, WRITE_BUFFER_DOUBLE, order, byteOffset, value2);
                return;
            }
            catch (InvalidBufferOffsetException | UnsupportedMessageException | RuntimeException e_) {
                throw e_;
            }
            catch (Exception e_) {
                throw CompilerDirectives.shouldNotReachHere(e_);
            }
        }

        @Override
        public boolean isPointer(Object receiver_) {
            try {
                return (Boolean)this.lib.send(receiver_, IS_POINTER, new Object[0]);
            }
            catch (RuntimeException e_) {
                throw e_;
            }
            catch (Exception e_) {
                throw CompilerDirectives.shouldNotReachHere(e_);
            }
        }

        @Override
        public long asPointer(Object receiver_) throws UnsupportedMessageException {
            try {
                return (Long)this.lib.send(receiver_, AS_POINTER, new Object[0]);
            }
            catch (UnsupportedMessageException | RuntimeException e_) {
                throw e_;
            }
            catch (Exception e_) {
                throw CompilerDirectives.shouldNotReachHere(e_);
            }
        }

        @Override
        public void toNative(Object receiver_) {
            try {
                this.lib.send(receiver_, TO_NATIVE, new Object[0]);
                return;
            }
            catch (RuntimeException e_) {
                throw e_;
            }
            catch (Exception e_) {
                throw CompilerDirectives.shouldNotReachHere(e_);
            }
        }

        @Override
        public Instant asInstant(Object receiver_) throws UnsupportedMessageException {
            try {
                return (Instant)this.lib.send(receiver_, AS_INSTANT, new Object[0]);
            }
            catch (UnsupportedMessageException | RuntimeException e_) {
                throw e_;
            }
            catch (Exception e_) {
                throw CompilerDirectives.shouldNotReachHere(e_);
            }
        }

        @Override
        public boolean isTimeZone(Object receiver_) {
            try {
                return (Boolean)this.lib.send(receiver_, IS_TIME_ZONE, new Object[0]);
            }
            catch (RuntimeException e_) {
                throw e_;
            }
            catch (Exception e_) {
                throw CompilerDirectives.shouldNotReachHere(e_);
            }
        }

        @Override
        public ZoneId asTimeZone(Object receiver_) throws UnsupportedMessageException {
            try {
                return (ZoneId)this.lib.send(receiver_, AS_TIME_ZONE, new Object[0]);
            }
            catch (UnsupportedMessageException | RuntimeException e_) {
                throw e_;
            }
            catch (Exception e_) {
                throw CompilerDirectives.shouldNotReachHere(e_);
            }
        }

        @Override
        public boolean isDate(Object receiver_) {
            try {
                return (Boolean)this.lib.send(receiver_, IS_DATE, new Object[0]);
            }
            catch (RuntimeException e_) {
                throw e_;
            }
            catch (Exception e_) {
                throw CompilerDirectives.shouldNotReachHere(e_);
            }
        }

        @Override
        public LocalDate asDate(Object receiver_) throws UnsupportedMessageException {
            try {
                return (LocalDate)this.lib.send(receiver_, AS_DATE, new Object[0]);
            }
            catch (UnsupportedMessageException | RuntimeException e_) {
                throw e_;
            }
            catch (Exception e_) {
                throw CompilerDirectives.shouldNotReachHere(e_);
            }
        }

        @Override
        public boolean isTime(Object receiver_) {
            try {
                return (Boolean)this.lib.send(receiver_, IS_TIME, new Object[0]);
            }
            catch (RuntimeException e_) {
                throw e_;
            }
            catch (Exception e_) {
                throw CompilerDirectives.shouldNotReachHere(e_);
            }
        }

        @Override
        public LocalTime asTime(Object receiver_) throws UnsupportedMessageException {
            try {
                return (LocalTime)this.lib.send(receiver_, AS_TIME, new Object[0]);
            }
            catch (UnsupportedMessageException | RuntimeException e_) {
                throw e_;
            }
            catch (Exception e_) {
                throw CompilerDirectives.shouldNotReachHere(e_);
            }
        }

        @Override
        public boolean isDuration(Object receiver_) {
            try {
                return (Boolean)this.lib.send(receiver_, IS_DURATION, new Object[0]);
            }
            catch (RuntimeException e_) {
                throw e_;
            }
            catch (Exception e_) {
                throw CompilerDirectives.shouldNotReachHere(e_);
            }
        }

        @Override
        public Duration asDuration(Object receiver_) throws UnsupportedMessageException {
            try {
                return (Duration)this.lib.send(receiver_, AS_DURATION, new Object[0]);
            }
            catch (UnsupportedMessageException | RuntimeException e_) {
                throw e_;
            }
            catch (Exception e_) {
                throw CompilerDirectives.shouldNotReachHere(e_);
            }
        }

        @Override
        public boolean isException(Object receiver_) {
            try {
                return (Boolean)this.lib.send(receiver_, IS_EXCEPTION, new Object[0]);
            }
            catch (RuntimeException e_) {
                throw e_;
            }
            catch (Exception e_) {
                throw CompilerDirectives.shouldNotReachHere(e_);
            }
        }

        @Override
        public RuntimeException throwException(Object receiver_) throws UnsupportedMessageException {
            try {
                return (RuntimeException)this.lib.send(receiver_, THROW_EXCEPTION, new Object[0]);
            }
            catch (UnsupportedMessageException | RuntimeException e_) {
                throw e_;
            }
            catch (Exception e_) {
                throw CompilerDirectives.shouldNotReachHere(e_);
            }
        }

        @Override
        public ExceptionType getExceptionType(Object receiver_) throws UnsupportedMessageException {
            try {
                return (ExceptionType)((Object)this.lib.send(receiver_, GET_EXCEPTION_TYPE, new Object[0]));
            }
            catch (UnsupportedMessageException | RuntimeException e_) {
                throw e_;
            }
            catch (Exception e_) {
                throw CompilerDirectives.shouldNotReachHere(e_);
            }
        }

        @Override
        public boolean isExceptionIncompleteSource(Object receiver_) throws UnsupportedMessageException {
            try {
                return (Boolean)this.lib.send(receiver_, IS_EXCEPTION_INCOMPLETE_SOURCE, new Object[0]);
            }
            catch (UnsupportedMessageException | RuntimeException e_) {
                throw e_;
            }
            catch (Exception e_) {
                throw CompilerDirectives.shouldNotReachHere(e_);
            }
        }

        @Override
        public int getExceptionExitStatus(Object receiver_) throws UnsupportedMessageException {
            try {
                return (Integer)this.lib.send(receiver_, GET_EXCEPTION_EXIT_STATUS, new Object[0]);
            }
            catch (UnsupportedMessageException | RuntimeException e_) {
                throw e_;
            }
            catch (Exception e_) {
                throw CompilerDirectives.shouldNotReachHere(e_);
            }
        }

        @Override
        public boolean hasExceptionCause(Object receiver_) {
            try {
                return (Boolean)this.lib.send(receiver_, HAS_EXCEPTION_CAUSE, new Object[0]);
            }
            catch (RuntimeException e_) {
                throw e_;
            }
            catch (Exception e_) {
                throw CompilerDirectives.shouldNotReachHere(e_);
            }
        }

        @Override
        public Object getExceptionCause(Object receiver_) throws UnsupportedMessageException {
            try {
                return this.lib.send(receiver_, GET_EXCEPTION_CAUSE, new Object[0]);
            }
            catch (UnsupportedMessageException | RuntimeException e_) {
                throw e_;
            }
            catch (Exception e_) {
                throw CompilerDirectives.shouldNotReachHere(e_);
            }
        }

        @Override
        public boolean hasExceptionMessage(Object receiver_) {
            try {
                return (Boolean)this.lib.send(receiver_, HAS_EXCEPTION_MESSAGE, new Object[0]);
            }
            catch (RuntimeException e_) {
                throw e_;
            }
            catch (Exception e_) {
                throw CompilerDirectives.shouldNotReachHere(e_);
            }
        }

        @Override
        public Object getExceptionMessage(Object receiver_) throws UnsupportedMessageException {
            try {
                return this.lib.send(receiver_, GET_EXCEPTION_MESSAGE, new Object[0]);
            }
            catch (UnsupportedMessageException | RuntimeException e_) {
                throw e_;
            }
            catch (Exception e_) {
                throw CompilerDirectives.shouldNotReachHere(e_);
            }
        }

        @Override
        public boolean hasExceptionStackTrace(Object receiver_) {
            try {
                return (Boolean)this.lib.send(receiver_, HAS_EXCEPTION_STACK_TRACE, new Object[0]);
            }
            catch (RuntimeException e_) {
                throw e_;
            }
            catch (Exception e_) {
                throw CompilerDirectives.shouldNotReachHere(e_);
            }
        }

        @Override
        public Object getExceptionStackTrace(Object receiver_) throws UnsupportedMessageException {
            try {
                return this.lib.send(receiver_, GET_EXCEPTION_STACK_TRACE, new Object[0]);
            }
            catch (UnsupportedMessageException | RuntimeException e_) {
                throw e_;
            }
            catch (Exception e_) {
                throw CompilerDirectives.shouldNotReachHere(e_);
            }
        }

        @Override
        public boolean hasIterator(Object receiver_) {
            try {
                return (Boolean)this.lib.send(receiver_, HAS_ITERATOR, new Object[0]);
            }
            catch (RuntimeException e_) {
                throw e_;
            }
            catch (Exception e_) {
                throw CompilerDirectives.shouldNotReachHere(e_);
            }
        }

        @Override
        public Object getIterator(Object receiver_) throws UnsupportedMessageException {
            try {
                return this.lib.send(receiver_, GET_ITERATOR, new Object[0]);
            }
            catch (UnsupportedMessageException | RuntimeException e_) {
                throw e_;
            }
            catch (Exception e_) {
                throw CompilerDirectives.shouldNotReachHere(e_);
            }
        }

        @Override
        public boolean isIterator(Object receiver_) {
            try {
                return (Boolean)this.lib.send(receiver_, IS_ITERATOR, new Object[0]);
            }
            catch (RuntimeException e_) {
                throw e_;
            }
            catch (Exception e_) {
                throw CompilerDirectives.shouldNotReachHere(e_);
            }
        }

        @Override
        public boolean hasIteratorNextElement(Object receiver_) throws UnsupportedMessageException {
            try {
                return (Boolean)this.lib.send(receiver_, HAS_ITERATOR_NEXT_ELEMENT, new Object[0]);
            }
            catch (UnsupportedMessageException | RuntimeException e_) {
                throw e_;
            }
            catch (Exception e_) {
                throw CompilerDirectives.shouldNotReachHere(e_);
            }
        }

        @Override
        public Object getIteratorNextElement(Object receiver_) throws UnsupportedMessageException, StopIterationException {
            try {
                return this.lib.send(receiver_, GET_ITERATOR_NEXT_ELEMENT, new Object[0]);
            }
            catch (StopIterationException | UnsupportedMessageException | RuntimeException e_) {
                throw e_;
            }
            catch (Exception e_) {
                throw CompilerDirectives.shouldNotReachHere(e_);
            }
        }

        @Override
        public boolean hasSourceLocation(Object receiver_) {
            try {
                return (Boolean)this.lib.send(receiver_, HAS_SOURCE_LOCATION, new Object[0]);
            }
            catch (RuntimeException e_) {
                throw e_;
            }
            catch (Exception e_) {
                throw CompilerDirectives.shouldNotReachHere(e_);
            }
        }

        @Override
        public SourceSection getSourceLocation(Object receiver_) throws UnsupportedMessageException {
            try {
                return (SourceSection)this.lib.send(receiver_, GET_SOURCE_LOCATION, new Object[0]);
            }
            catch (UnsupportedMessageException | RuntimeException e_) {
                throw e_;
            }
            catch (Exception e_) {
                throw CompilerDirectives.shouldNotReachHere(e_);
            }
        }

        @Override
        public boolean hasLanguage(Object receiver_) {
            try {
                return (Boolean)this.lib.send(receiver_, HAS_LANGUAGE, new Object[0]);
            }
            catch (RuntimeException e_) {
                throw e_;
            }
            catch (Exception e_) {
                throw CompilerDirectives.shouldNotReachHere(e_);
            }
        }

        @Override
        public Class<? extends TruffleLanguage<?>> getLanguage(Object receiver_) throws UnsupportedMessageException {
            try {
                return (Class)this.lib.send(receiver_, GET_LANGUAGE, new Object[0]);
            }
            catch (UnsupportedMessageException | RuntimeException e_) {
                throw e_;
            }
            catch (Exception e_) {
                throw CompilerDirectives.shouldNotReachHere(e_);
            }
        }

        @Override
        public boolean hasMetaObject(Object receiver_) {
            try {
                return (Boolean)this.lib.send(receiver_, HAS_META_OBJECT, new Object[0]);
            }
            catch (RuntimeException e_) {
                throw e_;
            }
            catch (Exception e_) {
                throw CompilerDirectives.shouldNotReachHere(e_);
            }
        }

        @Override
        public Object getMetaObject(Object receiver_) throws UnsupportedMessageException {
            try {
                return this.lib.send(receiver_, GET_META_OBJECT, new Object[0]);
            }
            catch (UnsupportedMessageException | RuntimeException e_) {
                throw e_;
            }
            catch (Exception e_) {
                throw CompilerDirectives.shouldNotReachHere(e_);
            }
        }

        @Override
        public Object toDisplayString(Object receiver_, boolean allowSideEffects) {
            try {
                return this.lib.send(receiver_, TO_DISPLAY_STRING, allowSideEffects);
            }
            catch (RuntimeException e_) {
                throw e_;
            }
            catch (Exception e_) {
                throw CompilerDirectives.shouldNotReachHere(e_);
            }
        }

        @Override
        public boolean isMetaObject(Object receiver_) {
            try {
                return (Boolean)this.lib.send(receiver_, IS_META_OBJECT, new Object[0]);
            }
            catch (RuntimeException e_) {
                throw e_;
            }
            catch (Exception e_) {
                throw CompilerDirectives.shouldNotReachHere(e_);
            }
        }

        @Override
        public Object getMetaQualifiedName(Object receiver_) throws UnsupportedMessageException {
            try {
                return this.lib.send(receiver_, GET_META_QUALIFIED_NAME, new Object[0]);
            }
            catch (UnsupportedMessageException | RuntimeException e_) {
                throw e_;
            }
            catch (Exception e_) {
                throw CompilerDirectives.shouldNotReachHere(e_);
            }
        }

        @Override
        public Object getMetaSimpleName(Object receiver_) throws UnsupportedMessageException {
            try {
                return this.lib.send(receiver_, GET_META_SIMPLE_NAME, new Object[0]);
            }
            catch (UnsupportedMessageException | RuntimeException e_) {
                throw e_;
            }
            catch (Exception e_) {
                throw CompilerDirectives.shouldNotReachHere(e_);
            }
        }

        @Override
        public boolean isMetaInstance(Object receiver_, Object instance) throws UnsupportedMessageException {
            try {
                return (Boolean)this.lib.send(receiver_, IS_META_INSTANCE, instance);
            }
            catch (UnsupportedMessageException | RuntimeException e_) {
                throw e_;
            }
            catch (Exception e_) {
                throw CompilerDirectives.shouldNotReachHere(e_);
            }
        }

        @Override
        public boolean hasMetaParents(Object receiver_) {
            try {
                return (Boolean)this.lib.send(receiver_, HAS_META_PARENTS, new Object[0]);
            }
            catch (RuntimeException e_) {
                throw e_;
            }
            catch (Exception e_) {
                throw CompilerDirectives.shouldNotReachHere(e_);
            }
        }

        @Override
        public Object getMetaParents(Object receiver_) throws UnsupportedMessageException {
            try {
                return this.lib.send(receiver_, GET_META_PARENTS, new Object[0]);
            }
            catch (UnsupportedMessageException | RuntimeException e_) {
                throw e_;
            }
            catch (Exception e_) {
                throw CompilerDirectives.shouldNotReachHere(e_);
            }
        }

        @Override
        protected TriState isIdenticalOrUndefined(Object receiver_, Object other) {
            try {
                return (TriState)((Object)this.lib.send(receiver_, IS_IDENTICAL_OR_UNDEFINED, other));
            }
            catch (RuntimeException e_) {
                throw e_;
            }
            catch (Exception e_) {
                throw CompilerDirectives.shouldNotReachHere(e_);
            }
        }

        @Override
        public boolean isIdentical(Object receiver_, Object other, InteropLibrary otherInterop) {
            try {
                return (Boolean)this.lib.send(receiver_, IS_IDENTICAL, other, otherInterop);
            }
            catch (RuntimeException e_) {
                throw e_;
            }
            catch (Exception e_) {
                throw CompilerDirectives.shouldNotReachHere(e_);
            }
        }

        @Override
        public int identityHashCode(Object receiver_) throws UnsupportedMessageException {
            try {
                return (Integer)this.lib.send(receiver_, IDENTITY_HASH_CODE, new Object[0]);
            }
            catch (UnsupportedMessageException | RuntimeException e_) {
                throw e_;
            }
            catch (Exception e_) {
                throw CompilerDirectives.shouldNotReachHere(e_);
            }
        }

        @Override
        public boolean isScope(Object receiver_) {
            try {
                return (Boolean)this.lib.send(receiver_, IS_SCOPE, new Object[0]);
            }
            catch (RuntimeException e_) {
                throw e_;
            }
            catch (Exception e_) {
                throw CompilerDirectives.shouldNotReachHere(e_);
            }
        }

        @Override
        public boolean hasScopeParent(Object receiver_) {
            try {
                return (Boolean)this.lib.send(receiver_, HAS_SCOPE_PARENT, new Object[0]);
            }
            catch (RuntimeException e_) {
                throw e_;
            }
            catch (Exception e_) {
                throw CompilerDirectives.shouldNotReachHere(e_);
            }
        }

        @Override
        public Object getScopeParent(Object receiver_) throws UnsupportedMessageException {
            try {
                return this.lib.send(receiver_, GET_SCOPE_PARENT, new Object[0]);
            }
            catch (UnsupportedMessageException | RuntimeException e_) {
                throw e_;
            }
            catch (Exception e_) {
                throw CompilerDirectives.shouldNotReachHere(e_);
            }
        }

        @Override
        public boolean accepts(Object receiver_) {
            return this.lib.accepts(receiver_);
        }
    }

    @GeneratedBy(value=InteropLibrary.class)
    private static class MessageImpl
    extends Message {
        MessageImpl(String name, int index, Class<?> returnType, Class<?> ... parameters) {
            super(LIBRARY_CLASS, name, index, returnType, parameters);
        }
    }

    @GeneratedBy(value=InteropLibrary.class)
    private static final class Default
    extends LibraryExport<InteropLibrary> {
        private Default() {
            super(InteropLibrary.class, Object.class, false, false, 0);
        }

        @Override
        protected InteropLibrary createUncached(Object receiver) {
            Uncached uncached = new Uncached(receiver);
            return uncached;
        }

        @Override
        protected InteropLibrary createCached(Object receiver) {
            return new Cached(receiver);
        }

        @GeneratedBy(value=InteropLibrary.class)
        @DenyReplace
        private static final class Uncached
        extends InteropLibrary {
            @Node.Child
            private DynamicDispatchLibrary dynamicDispatch_;
            private final Class<?> dynamicDispatchTarget_;

            protected Uncached(Object receiver) {
                this.dynamicDispatch_ = DYNAMIC_DISPATCH_LIBRARY_.getUncached(receiver);
                this.dynamicDispatchTarget_ = this.dynamicDispatch_.dispatch(receiver);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
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

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean isNull(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.isNull(receiver);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean isBoolean(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.isBoolean(receiver);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean asBoolean(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.asBoolean(receiver);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean isExecutable(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.isExecutable(receiver);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public Object execute(Object receiver, Object ... arguments) throws UnsupportedTypeException, ArityException, UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.execute(receiver, arguments);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean hasExecutableName(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.hasExecutableName(receiver);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public Object getExecutableName(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.getExecutableName(receiver);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean hasDeclaringMetaObject(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.hasDeclaringMetaObject(receiver);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public Object getDeclaringMetaObject(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.getDeclaringMetaObject(receiver);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean isInstantiable(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.isInstantiable(receiver);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public Object instantiate(Object receiver, Object ... arguments) throws UnsupportedTypeException, ArityException, UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.instantiate(receiver, arguments);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean isString(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.isString(receiver);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public String asString(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.asString(receiver);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public TruffleString asTruffleString(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.asTruffleString(receiver);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean isNumber(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.isNumber(receiver);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean fitsInByte(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.fitsInByte(receiver);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean fitsInShort(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.fitsInShort(receiver);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean fitsInInt(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.fitsInInt(receiver);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean fitsInLong(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.fitsInLong(receiver);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean fitsInFloat(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.fitsInFloat(receiver);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean fitsInDouble(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.fitsInDouble(receiver);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public byte asByte(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.asByte(receiver);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public short asShort(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.asShort(receiver);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public int asInt(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.asInt(receiver);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public long asLong(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.asLong(receiver);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public float asFloat(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.asFloat(receiver);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public double asDouble(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.asDouble(receiver);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean hasMembers(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.hasMembers(receiver);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public Object getMembers(Object receiver, boolean includeInternal) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.getMembers(receiver, includeInternal);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean isMemberReadable(Object receiver, String member) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.isMemberReadable(receiver, member);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public Object readMember(Object receiver, String member) throws UnsupportedMessageException, UnknownIdentifierException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.readMember(receiver, member);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean isMemberModifiable(Object receiver, String member) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.isMemberModifiable(receiver, member);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean isMemberInsertable(Object receiver, String member) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.isMemberInsertable(receiver, member);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public void writeMember(Object receiver, String member, Object value2) throws UnsupportedMessageException, UnknownIdentifierException, UnsupportedTypeException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                super.writeMember(receiver, member, value2);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean isMemberRemovable(Object receiver, String member) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.isMemberRemovable(receiver, member);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public void removeMember(Object receiver, String member) throws UnsupportedMessageException, UnknownIdentifierException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                super.removeMember(receiver, member);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean isMemberInvocable(Object receiver, String member) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.isMemberInvocable(receiver, member);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public Object invokeMember(Object receiver, String member, Object ... arguments) throws UnsupportedMessageException, ArityException, UnknownIdentifierException, UnsupportedTypeException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.invokeMember(receiver, member, arguments);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean isMemberInternal(Object receiver, String member) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.isMemberInternal(receiver, member);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean hasMemberReadSideEffects(Object receiver, String member) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.hasMemberReadSideEffects(receiver, member);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean hasMemberWriteSideEffects(Object receiver, String member) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.hasMemberWriteSideEffects(receiver, member);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean hasHashEntries(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.hasHashEntries(receiver);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public long getHashSize(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.getHashSize(receiver);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean isHashEntryReadable(Object receiver, Object key) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.isHashEntryReadable(receiver, key);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public Object readHashValue(Object receiver, Object key) throws UnsupportedMessageException, UnknownKeyException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.readHashValue(receiver, key);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public Object readHashValueOrDefault(Object receiver, Object key, Object defaultValue) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.readHashValueOrDefault(receiver, key, defaultValue);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean isHashEntryModifiable(Object receiver, Object key) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.isHashEntryModifiable(receiver, key);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean isHashEntryInsertable(Object receiver, Object key) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.isHashEntryInsertable(receiver, key);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean isHashEntryWritable(Object receiver, Object key) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.isHashEntryWritable(receiver, key);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public void writeHashEntry(Object receiver, Object key, Object value2) throws UnsupportedMessageException, UnknownKeyException, UnsupportedTypeException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                super.writeHashEntry(receiver, key, value2);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean isHashEntryRemovable(Object receiver, Object key) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.isHashEntryRemovable(receiver, key);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public void removeHashEntry(Object receiver, Object key) throws UnsupportedMessageException, UnknownKeyException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                super.removeHashEntry(receiver, key);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean isHashEntryExisting(Object receiver, Object key) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.isHashEntryExisting(receiver, key);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public Object getHashEntriesIterator(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.getHashEntriesIterator(receiver);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public Object getHashKeysIterator(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.getHashKeysIterator(receiver);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public Object getHashValuesIterator(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.getHashValuesIterator(receiver);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean hasArrayElements(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.hasArrayElements(receiver);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public Object readArrayElement(Object receiver, long index) throws UnsupportedMessageException, InvalidArrayIndexException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.readArrayElement(receiver, index);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public long getArraySize(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.getArraySize(receiver);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean isArrayElementReadable(Object receiver, long index) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.isArrayElementReadable(receiver, index);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public void writeArrayElement(Object receiver, long index, Object value2) throws UnsupportedMessageException, UnsupportedTypeException, InvalidArrayIndexException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                super.writeArrayElement(receiver, index, value2);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public void removeArrayElement(Object receiver, long index) throws UnsupportedMessageException, InvalidArrayIndexException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                super.removeArrayElement(receiver, index);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean isArrayElementModifiable(Object receiver, long index) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.isArrayElementModifiable(receiver, index);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean isArrayElementInsertable(Object receiver, long index) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.isArrayElementInsertable(receiver, index);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean isArrayElementRemovable(Object receiver, long index) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.isArrayElementRemovable(receiver, index);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean hasBufferElements(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.hasBufferElements(receiver);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean isBufferWritable(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.isBufferWritable(receiver);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public long getBufferSize(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.getBufferSize(receiver);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public byte readBufferByte(Object receiver, long byteOffset) throws UnsupportedMessageException, InvalidBufferOffsetException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.readBufferByte(receiver, byteOffset);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public void writeBufferByte(Object receiver, long byteOffset, byte value2) throws UnsupportedMessageException, InvalidBufferOffsetException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                super.writeBufferByte(receiver, byteOffset, value2);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public short readBufferShort(Object receiver, ByteOrder order, long byteOffset) throws UnsupportedMessageException, InvalidBufferOffsetException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.readBufferShort(receiver, order, byteOffset);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public void writeBufferShort(Object receiver, ByteOrder order, long byteOffset, short value2) throws UnsupportedMessageException, InvalidBufferOffsetException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                super.writeBufferShort(receiver, order, byteOffset, value2);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public int readBufferInt(Object receiver, ByteOrder order, long byteOffset) throws UnsupportedMessageException, InvalidBufferOffsetException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.readBufferInt(receiver, order, byteOffset);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public void writeBufferInt(Object receiver, ByteOrder order, long byteOffset, int value2) throws UnsupportedMessageException, InvalidBufferOffsetException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                super.writeBufferInt(receiver, order, byteOffset, value2);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public long readBufferLong(Object receiver, ByteOrder order, long byteOffset) throws UnsupportedMessageException, InvalidBufferOffsetException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.readBufferLong(receiver, order, byteOffset);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public void writeBufferLong(Object receiver, ByteOrder order, long byteOffset, long value2) throws UnsupportedMessageException, InvalidBufferOffsetException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                super.writeBufferLong(receiver, order, byteOffset, value2);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public float readBufferFloat(Object receiver, ByteOrder order, long byteOffset) throws UnsupportedMessageException, InvalidBufferOffsetException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.readBufferFloat(receiver, order, byteOffset);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public void writeBufferFloat(Object receiver, ByteOrder order, long byteOffset, float value2) throws UnsupportedMessageException, InvalidBufferOffsetException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                super.writeBufferFloat(receiver, order, byteOffset, value2);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public double readBufferDouble(Object receiver, ByteOrder order, long byteOffset) throws UnsupportedMessageException, InvalidBufferOffsetException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.readBufferDouble(receiver, order, byteOffset);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public void writeBufferDouble(Object receiver, ByteOrder order, long byteOffset, double value2) throws UnsupportedMessageException, InvalidBufferOffsetException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                super.writeBufferDouble(receiver, order, byteOffset, value2);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean isPointer(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.isPointer(receiver);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public long asPointer(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.asPointer(receiver);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public void toNative(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                super.toNative(receiver);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public Instant asInstant(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.asInstant(receiver);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean isTimeZone(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.isTimeZone(receiver);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public ZoneId asTimeZone(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.asTimeZone(receiver);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean isDate(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.isDate(receiver);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public LocalDate asDate(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.asDate(receiver);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean isTime(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.isTime(receiver);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public LocalTime asTime(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.asTime(receiver);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean isDuration(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.isDuration(receiver);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public Duration asDuration(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.asDuration(receiver);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean isException(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.isException(receiver);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public RuntimeException throwException(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.throwException(receiver);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public ExceptionType getExceptionType(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.getExceptionType(receiver);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean isExceptionIncompleteSource(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.isExceptionIncompleteSource(receiver);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public int getExceptionExitStatus(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.getExceptionExitStatus(receiver);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean hasExceptionCause(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.hasExceptionCause(receiver);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public Object getExceptionCause(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.getExceptionCause(receiver);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean hasExceptionMessage(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.hasExceptionMessage(receiver);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public Object getExceptionMessage(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.getExceptionMessage(receiver);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean hasExceptionStackTrace(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.hasExceptionStackTrace(receiver);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public Object getExceptionStackTrace(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.getExceptionStackTrace(receiver);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean hasIterator(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.hasIterator(receiver);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public Object getIterator(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.getIterator(receiver);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean isIterator(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.isIterator(receiver);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean hasIteratorNextElement(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.hasIteratorNextElement(receiver);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public Object getIteratorNextElement(Object receiver) throws UnsupportedMessageException, StopIterationException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.getIteratorNextElement(receiver);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean hasSourceLocation(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.hasSourceLocation(receiver);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public SourceSection getSourceLocation(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.getSourceLocation(receiver);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean hasLanguage(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.hasLanguage(receiver);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public Class<? extends TruffleLanguage<?>> getLanguage(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.getLanguage(receiver);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean hasMetaObject(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.hasMetaObject(receiver);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public Object getMetaObject(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.getMetaObject(receiver);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public Object toDisplayString(Object receiver, boolean allowSideEffects) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.toDisplayString(receiver, allowSideEffects);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean isMetaObject(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.isMetaObject(receiver);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public Object getMetaQualifiedName(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.getMetaQualifiedName(receiver);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public Object getMetaSimpleName(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.getMetaSimpleName(receiver);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean isMetaInstance(Object receiver, Object instance) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.isMetaInstance(receiver, instance);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean hasMetaParents(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.hasMetaParents(receiver);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public Object getMetaParents(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.getMetaParents(receiver);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            protected TriState isIdenticalOrUndefined(Object receiver, Object other) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.isIdenticalOrUndefined(receiver, other);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean isIdentical(Object receiver, Object other, InteropLibrary otherInterop) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.isIdentical(receiver, other, otherInterop);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public int identityHashCode(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.identityHashCode(receiver);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean isScope(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.isScope(receiver);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean hasScopeParent(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.hasScopeParent(receiver);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public Object getScopeParent(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.getScopeParent(receiver);
            }
        }

        @GeneratedBy(value=InteropLibrary.class)
        private static final class Cached
        extends InteropLibrary {
            @Node.Child
            private DynamicDispatchLibrary dynamicDispatch_;
            private final Class<?> dynamicDispatchTarget_;

            protected Cached(Object receiver) {
                this.dynamicDispatch_ = this.insert(DYNAMIC_DISPATCH_LIBRARY_.create(receiver));
                this.dynamicDispatchTarget_ = DYNAMIC_DISPATCH_LIBRARY_.getUncached(receiver).dispatch(receiver);
            }

            @Override
            public boolean accepts(Object receiver) {
                return this.dynamicDispatch_.accepts(receiver) && this.dynamicDispatch_.dispatch(receiver) == this.dynamicDispatchTarget_;
            }

            @Override
            public boolean isNull(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.isNull(this.dynamicDispatch_.cast(receiver));
            }

            @Override
            public boolean isBoolean(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.isBoolean(this.dynamicDispatch_.cast(receiver));
            }

            @Override
            public boolean asBoolean(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.asBoolean(this.dynamicDispatch_.cast(receiver));
            }

            @Override
            public boolean isExecutable(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.isExecutable(this.dynamicDispatch_.cast(receiver));
            }

            @Override
            public Object execute(Object receiver, Object ... arguments) throws UnsupportedTypeException, ArityException, UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.execute(this.dynamicDispatch_.cast(receiver), arguments);
            }

            @Override
            public boolean hasExecutableName(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.hasExecutableName(this.dynamicDispatch_.cast(receiver));
            }

            @Override
            public Object getExecutableName(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.getExecutableName(this.dynamicDispatch_.cast(receiver));
            }

            @Override
            public boolean hasDeclaringMetaObject(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.hasDeclaringMetaObject(this.dynamicDispatch_.cast(receiver));
            }

            @Override
            public Object getDeclaringMetaObject(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.getDeclaringMetaObject(this.dynamicDispatch_.cast(receiver));
            }

            @Override
            public boolean isInstantiable(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.isInstantiable(this.dynamicDispatch_.cast(receiver));
            }

            @Override
            public Object instantiate(Object receiver, Object ... arguments) throws UnsupportedTypeException, ArityException, UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.instantiate(this.dynamicDispatch_.cast(receiver), arguments);
            }

            @Override
            public boolean isString(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.isString(this.dynamicDispatch_.cast(receiver));
            }

            @Override
            public String asString(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.asString(this.dynamicDispatch_.cast(receiver));
            }

            @Override
            public TruffleString asTruffleString(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.asTruffleString(this.dynamicDispatch_.cast(receiver));
            }

            @Override
            public boolean isNumber(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.isNumber(this.dynamicDispatch_.cast(receiver));
            }

            @Override
            public boolean fitsInByte(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.fitsInByte(this.dynamicDispatch_.cast(receiver));
            }

            @Override
            public boolean fitsInShort(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.fitsInShort(this.dynamicDispatch_.cast(receiver));
            }

            @Override
            public boolean fitsInInt(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.fitsInInt(this.dynamicDispatch_.cast(receiver));
            }

            @Override
            public boolean fitsInLong(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.fitsInLong(this.dynamicDispatch_.cast(receiver));
            }

            @Override
            public boolean fitsInFloat(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.fitsInFloat(this.dynamicDispatch_.cast(receiver));
            }

            @Override
            public boolean fitsInDouble(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.fitsInDouble(this.dynamicDispatch_.cast(receiver));
            }

            @Override
            public byte asByte(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.asByte(this.dynamicDispatch_.cast(receiver));
            }

            @Override
            public short asShort(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.asShort(this.dynamicDispatch_.cast(receiver));
            }

            @Override
            public int asInt(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.asInt(this.dynamicDispatch_.cast(receiver));
            }

            @Override
            public long asLong(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.asLong(this.dynamicDispatch_.cast(receiver));
            }

            @Override
            public float asFloat(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.asFloat(this.dynamicDispatch_.cast(receiver));
            }

            @Override
            public double asDouble(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.asDouble(this.dynamicDispatch_.cast(receiver));
            }

            @Override
            public boolean hasMembers(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.hasMembers(this.dynamicDispatch_.cast(receiver));
            }

            @Override
            public Object getMembers(Object receiver, boolean includeInternal) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.getMembers(this.dynamicDispatch_.cast(receiver), includeInternal);
            }

            @Override
            public boolean isMemberReadable(Object receiver, String member) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.isMemberReadable(this.dynamicDispatch_.cast(receiver), member);
            }

            @Override
            public Object readMember(Object receiver, String member) throws UnsupportedMessageException, UnknownIdentifierException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.readMember(this.dynamicDispatch_.cast(receiver), member);
            }

            @Override
            public boolean isMemberModifiable(Object receiver, String member) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.isMemberModifiable(this.dynamicDispatch_.cast(receiver), member);
            }

            @Override
            public boolean isMemberInsertable(Object receiver, String member) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.isMemberInsertable(this.dynamicDispatch_.cast(receiver), member);
            }

            @Override
            public void writeMember(Object receiver, String member, Object value2) throws UnsupportedMessageException, UnknownIdentifierException, UnsupportedTypeException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                super.writeMember(this.dynamicDispatch_.cast(receiver), member, value2);
            }

            @Override
            public boolean isMemberRemovable(Object receiver, String member) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.isMemberRemovable(this.dynamicDispatch_.cast(receiver), member);
            }

            @Override
            public void removeMember(Object receiver, String member) throws UnsupportedMessageException, UnknownIdentifierException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                super.removeMember(this.dynamicDispatch_.cast(receiver), member);
            }

            @Override
            public boolean isMemberInvocable(Object receiver, String member) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.isMemberInvocable(this.dynamicDispatch_.cast(receiver), member);
            }

            @Override
            public Object invokeMember(Object receiver, String member, Object ... arguments) throws UnsupportedMessageException, ArityException, UnknownIdentifierException, UnsupportedTypeException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.invokeMember(this.dynamicDispatch_.cast(receiver), member, arguments);
            }

            @Override
            public boolean isMemberInternal(Object receiver, String member) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.isMemberInternal(this.dynamicDispatch_.cast(receiver), member);
            }

            @Override
            public boolean hasMemberReadSideEffects(Object receiver, String member) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.hasMemberReadSideEffects(this.dynamicDispatch_.cast(receiver), member);
            }

            @Override
            public boolean hasMemberWriteSideEffects(Object receiver, String member) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.hasMemberWriteSideEffects(this.dynamicDispatch_.cast(receiver), member);
            }

            @Override
            public boolean hasHashEntries(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.hasHashEntries(this.dynamicDispatch_.cast(receiver));
            }

            @Override
            public long getHashSize(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.getHashSize(this.dynamicDispatch_.cast(receiver));
            }

            @Override
            public boolean isHashEntryReadable(Object receiver, Object key) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.isHashEntryReadable(this.dynamicDispatch_.cast(receiver), key);
            }

            @Override
            public Object readHashValue(Object receiver, Object key) throws UnsupportedMessageException, UnknownKeyException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.readHashValue(this.dynamicDispatch_.cast(receiver), key);
            }

            @Override
            public Object readHashValueOrDefault(Object receiver, Object key, Object defaultValue) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.readHashValueOrDefault(this.dynamicDispatch_.cast(receiver), key, defaultValue);
            }

            @Override
            public boolean isHashEntryModifiable(Object receiver, Object key) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.isHashEntryModifiable(this.dynamicDispatch_.cast(receiver), key);
            }

            @Override
            public boolean isHashEntryInsertable(Object receiver, Object key) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.isHashEntryInsertable(this.dynamicDispatch_.cast(receiver), key);
            }

            @Override
            public boolean isHashEntryWritable(Object receiver, Object key) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.isHashEntryWritable(this.dynamicDispatch_.cast(receiver), key);
            }

            @Override
            public void writeHashEntry(Object receiver, Object key, Object value2) throws UnsupportedMessageException, UnknownKeyException, UnsupportedTypeException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                super.writeHashEntry(this.dynamicDispatch_.cast(receiver), key, value2);
            }

            @Override
            public boolean isHashEntryRemovable(Object receiver, Object key) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.isHashEntryRemovable(this.dynamicDispatch_.cast(receiver), key);
            }

            @Override
            public void removeHashEntry(Object receiver, Object key) throws UnsupportedMessageException, UnknownKeyException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                super.removeHashEntry(this.dynamicDispatch_.cast(receiver), key);
            }

            @Override
            public boolean isHashEntryExisting(Object receiver, Object key) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.isHashEntryExisting(this.dynamicDispatch_.cast(receiver), key);
            }

            @Override
            public Object getHashEntriesIterator(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.getHashEntriesIterator(this.dynamicDispatch_.cast(receiver));
            }

            @Override
            public Object getHashKeysIterator(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.getHashKeysIterator(this.dynamicDispatch_.cast(receiver));
            }

            @Override
            public Object getHashValuesIterator(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.getHashValuesIterator(this.dynamicDispatch_.cast(receiver));
            }

            @Override
            public boolean hasArrayElements(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.hasArrayElements(this.dynamicDispatch_.cast(receiver));
            }

            @Override
            public Object readArrayElement(Object receiver, long index) throws UnsupportedMessageException, InvalidArrayIndexException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.readArrayElement(this.dynamicDispatch_.cast(receiver), index);
            }

            @Override
            public long getArraySize(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.getArraySize(this.dynamicDispatch_.cast(receiver));
            }

            @Override
            public boolean isArrayElementReadable(Object receiver, long index) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.isArrayElementReadable(this.dynamicDispatch_.cast(receiver), index);
            }

            @Override
            public void writeArrayElement(Object receiver, long index, Object value2) throws UnsupportedMessageException, UnsupportedTypeException, InvalidArrayIndexException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                super.writeArrayElement(this.dynamicDispatch_.cast(receiver), index, value2);
            }

            @Override
            public void removeArrayElement(Object receiver, long index) throws UnsupportedMessageException, InvalidArrayIndexException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                super.removeArrayElement(this.dynamicDispatch_.cast(receiver), index);
            }

            @Override
            public boolean isArrayElementModifiable(Object receiver, long index) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.isArrayElementModifiable(this.dynamicDispatch_.cast(receiver), index);
            }

            @Override
            public boolean isArrayElementInsertable(Object receiver, long index) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.isArrayElementInsertable(this.dynamicDispatch_.cast(receiver), index);
            }

            @Override
            public boolean isArrayElementRemovable(Object receiver, long index) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.isArrayElementRemovable(this.dynamicDispatch_.cast(receiver), index);
            }

            @Override
            public boolean hasBufferElements(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.hasBufferElements(this.dynamicDispatch_.cast(receiver));
            }

            @Override
            public boolean isBufferWritable(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.isBufferWritable(this.dynamicDispatch_.cast(receiver));
            }

            @Override
            public long getBufferSize(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.getBufferSize(this.dynamicDispatch_.cast(receiver));
            }

            @Override
            public byte readBufferByte(Object receiver, long byteOffset) throws UnsupportedMessageException, InvalidBufferOffsetException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.readBufferByte(this.dynamicDispatch_.cast(receiver), byteOffset);
            }

            @Override
            public void writeBufferByte(Object receiver, long byteOffset, byte value2) throws UnsupportedMessageException, InvalidBufferOffsetException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                super.writeBufferByte(this.dynamicDispatch_.cast(receiver), byteOffset, value2);
            }

            @Override
            public short readBufferShort(Object receiver, ByteOrder order, long byteOffset) throws UnsupportedMessageException, InvalidBufferOffsetException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.readBufferShort(this.dynamicDispatch_.cast(receiver), order, byteOffset);
            }

            @Override
            public void writeBufferShort(Object receiver, ByteOrder order, long byteOffset, short value2) throws UnsupportedMessageException, InvalidBufferOffsetException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                super.writeBufferShort(this.dynamicDispatch_.cast(receiver), order, byteOffset, value2);
            }

            @Override
            public int readBufferInt(Object receiver, ByteOrder order, long byteOffset) throws UnsupportedMessageException, InvalidBufferOffsetException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.readBufferInt(this.dynamicDispatch_.cast(receiver), order, byteOffset);
            }

            @Override
            public void writeBufferInt(Object receiver, ByteOrder order, long byteOffset, int value2) throws UnsupportedMessageException, InvalidBufferOffsetException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                super.writeBufferInt(this.dynamicDispatch_.cast(receiver), order, byteOffset, value2);
            }

            @Override
            public long readBufferLong(Object receiver, ByteOrder order, long byteOffset) throws UnsupportedMessageException, InvalidBufferOffsetException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.readBufferLong(this.dynamicDispatch_.cast(receiver), order, byteOffset);
            }

            @Override
            public void writeBufferLong(Object receiver, ByteOrder order, long byteOffset, long value2) throws UnsupportedMessageException, InvalidBufferOffsetException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                super.writeBufferLong(this.dynamicDispatch_.cast(receiver), order, byteOffset, value2);
            }

            @Override
            public float readBufferFloat(Object receiver, ByteOrder order, long byteOffset) throws UnsupportedMessageException, InvalidBufferOffsetException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.readBufferFloat(this.dynamicDispatch_.cast(receiver), order, byteOffset);
            }

            @Override
            public void writeBufferFloat(Object receiver, ByteOrder order, long byteOffset, float value2) throws UnsupportedMessageException, InvalidBufferOffsetException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                super.writeBufferFloat(this.dynamicDispatch_.cast(receiver), order, byteOffset, value2);
            }

            @Override
            public double readBufferDouble(Object receiver, ByteOrder order, long byteOffset) throws UnsupportedMessageException, InvalidBufferOffsetException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.readBufferDouble(this.dynamicDispatch_.cast(receiver), order, byteOffset);
            }

            @Override
            public void writeBufferDouble(Object receiver, ByteOrder order, long byteOffset, double value2) throws UnsupportedMessageException, InvalidBufferOffsetException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                super.writeBufferDouble(this.dynamicDispatch_.cast(receiver), order, byteOffset, value2);
            }

            @Override
            public boolean isPointer(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.isPointer(this.dynamicDispatch_.cast(receiver));
            }

            @Override
            public long asPointer(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.asPointer(this.dynamicDispatch_.cast(receiver));
            }

            @Override
            public void toNative(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                super.toNative(this.dynamicDispatch_.cast(receiver));
            }

            @Override
            public Instant asInstant(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.asInstant(this.dynamicDispatch_.cast(receiver));
            }

            @Override
            public boolean isTimeZone(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.isTimeZone(this.dynamicDispatch_.cast(receiver));
            }

            @Override
            public ZoneId asTimeZone(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.asTimeZone(this.dynamicDispatch_.cast(receiver));
            }

            @Override
            public boolean isDate(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.isDate(this.dynamicDispatch_.cast(receiver));
            }

            @Override
            public LocalDate asDate(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.asDate(this.dynamicDispatch_.cast(receiver));
            }

            @Override
            public boolean isTime(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.isTime(this.dynamicDispatch_.cast(receiver));
            }

            @Override
            public LocalTime asTime(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.asTime(this.dynamicDispatch_.cast(receiver));
            }

            @Override
            public boolean isDuration(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.isDuration(this.dynamicDispatch_.cast(receiver));
            }

            @Override
            public Duration asDuration(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.asDuration(this.dynamicDispatch_.cast(receiver));
            }

            @Override
            public boolean isException(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.isException(this.dynamicDispatch_.cast(receiver));
            }

            @Override
            public RuntimeException throwException(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.throwException(this.dynamicDispatch_.cast(receiver));
            }

            @Override
            public ExceptionType getExceptionType(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.getExceptionType(this.dynamicDispatch_.cast(receiver));
            }

            @Override
            public boolean isExceptionIncompleteSource(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.isExceptionIncompleteSource(this.dynamicDispatch_.cast(receiver));
            }

            @Override
            public int getExceptionExitStatus(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.getExceptionExitStatus(this.dynamicDispatch_.cast(receiver));
            }

            @Override
            public boolean hasExceptionCause(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.hasExceptionCause(this.dynamicDispatch_.cast(receiver));
            }

            @Override
            public Object getExceptionCause(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.getExceptionCause(this.dynamicDispatch_.cast(receiver));
            }

            @Override
            public boolean hasExceptionMessage(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.hasExceptionMessage(this.dynamicDispatch_.cast(receiver));
            }

            @Override
            public Object getExceptionMessage(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.getExceptionMessage(this.dynamicDispatch_.cast(receiver));
            }

            @Override
            public boolean hasExceptionStackTrace(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.hasExceptionStackTrace(this.dynamicDispatch_.cast(receiver));
            }

            @Override
            public Object getExceptionStackTrace(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.getExceptionStackTrace(this.dynamicDispatch_.cast(receiver));
            }

            @Override
            public boolean hasIterator(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.hasIterator(this.dynamicDispatch_.cast(receiver));
            }

            @Override
            public Object getIterator(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.getIterator(this.dynamicDispatch_.cast(receiver));
            }

            @Override
            public boolean isIterator(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.isIterator(this.dynamicDispatch_.cast(receiver));
            }

            @Override
            public boolean hasIteratorNextElement(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.hasIteratorNextElement(this.dynamicDispatch_.cast(receiver));
            }

            @Override
            public Object getIteratorNextElement(Object receiver) throws UnsupportedMessageException, StopIterationException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.getIteratorNextElement(this.dynamicDispatch_.cast(receiver));
            }

            @Override
            public boolean hasSourceLocation(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.hasSourceLocation(this.dynamicDispatch_.cast(receiver));
            }

            @Override
            public SourceSection getSourceLocation(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.getSourceLocation(this.dynamicDispatch_.cast(receiver));
            }

            @Override
            public boolean hasLanguage(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.hasLanguage(this.dynamicDispatch_.cast(receiver));
            }

            @Override
            public Class<? extends TruffleLanguage<?>> getLanguage(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.getLanguage(this.dynamicDispatch_.cast(receiver));
            }

            @Override
            public boolean hasMetaObject(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.hasMetaObject(this.dynamicDispatch_.cast(receiver));
            }

            @Override
            public Object getMetaObject(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.getMetaObject(this.dynamicDispatch_.cast(receiver));
            }

            @Override
            public Object toDisplayString(Object receiver, boolean allowSideEffects) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.toDisplayString(this.dynamicDispatch_.cast(receiver), allowSideEffects);
            }

            @Override
            public boolean isMetaObject(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.isMetaObject(this.dynamicDispatch_.cast(receiver));
            }

            @Override
            public Object getMetaQualifiedName(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.getMetaQualifiedName(this.dynamicDispatch_.cast(receiver));
            }

            @Override
            public Object getMetaSimpleName(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.getMetaSimpleName(this.dynamicDispatch_.cast(receiver));
            }

            @Override
            public boolean isMetaInstance(Object receiver, Object instance) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.isMetaInstance(this.dynamicDispatch_.cast(receiver), instance);
            }

            @Override
            public boolean hasMetaParents(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.hasMetaParents(this.dynamicDispatch_.cast(receiver));
            }

            @Override
            public Object getMetaParents(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.getMetaParents(this.dynamicDispatch_.cast(receiver));
            }

            @Override
            protected TriState isIdenticalOrUndefined(Object receiver, Object other) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.isIdenticalOrUndefined(this.dynamicDispatch_.cast(receiver), other);
            }

            @Override
            public boolean isIdentical(Object receiver, Object other, InteropLibrary otherInterop) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.isIdentical(this.dynamicDispatch_.cast(receiver), other, otherInterop);
            }

            @Override
            public int identityHashCode(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.identityHashCode(this.dynamicDispatch_.cast(receiver));
            }

            @Override
            public boolean isScope(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.isScope(this.dynamicDispatch_.cast(receiver));
            }

            @Override
            public boolean hasScopeParent(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.hasScopeParent(this.dynamicDispatch_.cast(receiver));
            }

            @Override
            public Object getScopeParent(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.getScopeParent(this.dynamicDispatch_.cast(receiver));
            }
        }
    }
}

