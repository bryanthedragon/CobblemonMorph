
package com.oracle.truffle.api.interop;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.impl.Accessor;
import com.oracle.truffle.api.interop.ArityException;
import com.oracle.truffle.api.interop.ArrayIterator;
import com.oracle.truffle.api.interop.AssertUtils;
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
import com.oracle.truffle.api.interop.HashIterator;
import com.oracle.truffle.api.interop.InteropAccessor;
import com.oracle.truffle.api.interop.InteropException;
import com.oracle.truffle.api.interop.InvalidArrayIndexException;
import com.oracle.truffle.api.interop.InvalidBufferOffsetException;
import com.oracle.truffle.api.interop.StopIterationException;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.interop.UnknownIdentifierException;
import com.oracle.truffle.api.interop.UnknownKeyException;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.interop.UnsupportedTypeException;
import com.oracle.truffle.api.library.GenerateLibrary;
import com.oracle.truffle.api.library.Library;
import com.oracle.truffle.api.library.LibraryFactory;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.source.SourceSection;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.api.utilities.TriState;
import java.nio.ByteOrder;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Objects;

@GenerateLibrary(assertions=Asserts.class, receiverType=TruffleObject.class)
@GenerateLibrary.DefaultExport.Repeat(value={@GenerateLibrary.DefaultExport(value=DefaultBooleanExports.class), @GenerateLibrary.DefaultExport(value=DefaultIntegerExports.class), @GenerateLibrary.DefaultExport(value=DefaultByteExports.class), @GenerateLibrary.DefaultExport(value=DefaultShortExports.class), @GenerateLibrary.DefaultExport(value=DefaultLongExports.class), @GenerateLibrary.DefaultExport(value=DefaultFloatExports.class), @GenerateLibrary.DefaultExport(value=DefaultDoubleExports.class), @GenerateLibrary.DefaultExport(value=DefaultCharacterExports.class), @GenerateLibrary.DefaultExport(value=DefaultStringExports.class), @GenerateLibrary.DefaultExport(value=DefaultTStringExports.class)})
public abstract class InteropLibrary
extends Library {
    static final LibraryFactory<InteropLibrary> FACTORY = LibraryFactory.resolve(InteropLibrary.class);
    static final InteropLibrary UNCACHED = FACTORY.getUncached();

    protected InteropLibrary() {
    }

    public boolean isNull(Object receiver) {
        return false;
    }

    @GenerateLibrary.Abstract(ifExported={"asBoolean"})
    public boolean isBoolean(Object receiver) {
        return false;
    }

    @GenerateLibrary.Abstract(ifExported={"isBoolean"})
    public boolean asBoolean(Object receiver) throws UnsupportedMessageException {
        throw UnsupportedMessageException.create();
    }

    @GenerateLibrary.Abstract(ifExported={"execute"})
    public boolean isExecutable(Object receiver) {
        return false;
    }

    @GenerateLibrary.Abstract(ifExported={"isExecutable"})
    public Object execute(Object receiver, Object ... arguments) throws UnsupportedTypeException, ArityException, UnsupportedMessageException {
        throw UnsupportedMessageException.create();
    }

    @GenerateLibrary.Abstract(ifExported={"getExecutableName"})
    public boolean hasExecutableName(Object receiver) {
        return false;
    }

    @GenerateLibrary.Abstract(ifExported={"hasExecutableName"})
    public Object getExecutableName(Object receiver) throws UnsupportedMessageException {
        throw UnsupportedMessageException.create();
    }

    @GenerateLibrary.Abstract(ifExported={"getDeclaringMetaObject"})
    public boolean hasDeclaringMetaObject(Object receiver) {
        return false;
    }

    @GenerateLibrary.Abstract(ifExported={"hasDeclaringMetaObject"})
    public Object getDeclaringMetaObject(Object receiver) throws UnsupportedMessageException {
        throw UnsupportedMessageException.create();
    }

    @GenerateLibrary.Abstract(ifExported={"instantiate"})
    public boolean isInstantiable(Object receiver) {
        return false;
    }

    @GenerateLibrary.Abstract(ifExported={"isInstantiable"})
    public Object instantiate(Object receiver, Object ... arguments) throws UnsupportedTypeException, ArityException, UnsupportedMessageException {
        throw UnsupportedMessageException.create();
    }

    @GenerateLibrary.Abstract(ifExported={"asString", "asTruffleString"})
    public boolean isString(Object receiver) {
        return false;
    }

    @GenerateLibrary.Abstract(ifExported={"isString"})
    public String asString(Object receiver) throws UnsupportedMessageException {
        throw UnsupportedMessageException.create();
    }

    public TruffleString asTruffleString(Object receiver) throws UnsupportedMessageException {
        return TruffleString.fromJavaStringUncached(this.asString(receiver), TruffleString.Encoding.UTF_16);
    }

    @GenerateLibrary.Abstract(ifExported={"fitsInByte", "fitsInShort", "fitsInInt", "fitsInLong", "fitsInFloat", "fitsInDouble", "asByte", "asShort", "asInt", "asLong", "asFloat", "asDouble"})
    public boolean isNumber(Object receiver) {
        return false;
    }

    @GenerateLibrary.Abstract(ifExported={"isNumber"})
    public boolean fitsInByte(Object receiver) {
        return false;
    }

    @GenerateLibrary.Abstract(ifExported={"isNumber"})
    public boolean fitsInShort(Object receiver) {
        return false;
    }

    @GenerateLibrary.Abstract(ifExported={"isNumber"})
    public boolean fitsInInt(Object receiver) {
        return false;
    }

    @GenerateLibrary.Abstract(ifExported={"isNumber"})
    public boolean fitsInLong(Object receiver) {
        return false;
    }

    @GenerateLibrary.Abstract(ifExported={"isNumber"})
    public boolean fitsInFloat(Object receiver) {
        return false;
    }

    @GenerateLibrary.Abstract(ifExported={"isNumber"})
    public boolean fitsInDouble(Object receiver) {
        return false;
    }

    @GenerateLibrary.Abstract(ifExported={"isNumber"})
    public byte asByte(Object receiver) throws UnsupportedMessageException {
        throw UnsupportedMessageException.create();
    }

    @GenerateLibrary.Abstract(ifExported={"isNumber"})
    public short asShort(Object receiver) throws UnsupportedMessageException {
        throw UnsupportedMessageException.create();
    }

    @GenerateLibrary.Abstract(ifExported={"isNumber"})
    public int asInt(Object receiver) throws UnsupportedMessageException {
        throw UnsupportedMessageException.create();
    }

    @GenerateLibrary.Abstract(ifExported={"isNumber"})
    public long asLong(Object receiver) throws UnsupportedMessageException {
        throw UnsupportedMessageException.create();
    }

    @GenerateLibrary.Abstract(ifExported={"isNumber"})
    public float asFloat(Object receiver) throws UnsupportedMessageException {
        throw UnsupportedMessageException.create();
    }

    @GenerateLibrary.Abstract(ifExported={"isNumber"})
    public double asDouble(Object receiver) throws UnsupportedMessageException {
        throw UnsupportedMessageException.create();
    }

    @GenerateLibrary.Abstract(ifExported={"getMembers", "isMemberReadable", "readMember", "isMemberModifiable", "isMemberInsertable", "writeMember", "isMemberRemovable", "removeMember", "isMemberInvocable", "invokeMember", "isMemberInternal", "hasMemberReadSideEffects", "hasMemberWriteSideEffects", "isScope"})
    public boolean hasMembers(Object receiver) {
        return false;
    }

    @GenerateLibrary.Abstract(ifExported={"hasMembers", "isScope"})
    public Object getMembers(Object receiver, boolean includeInternal) throws UnsupportedMessageException {
        throw UnsupportedMessageException.create();
    }

    public final Object getMembers(Object receiver) throws UnsupportedMessageException {
        return this.getMembers(receiver, false);
    }

    @GenerateLibrary.Abstract(ifExported={"readMember"})
    public boolean isMemberReadable(Object receiver, String member) {
        return false;
    }

    @GenerateLibrary.Abstract(ifExported={"isMemberReadable"})
    public Object readMember(Object receiver, String member) throws UnsupportedMessageException, UnknownIdentifierException {
        throw UnsupportedMessageException.create();
    }

    @GenerateLibrary.Abstract(ifExported={"writeMember"})
    public boolean isMemberModifiable(Object receiver, String member) {
        return false;
    }

    @GenerateLibrary.Abstract(ifExported={"writeMember"})
    public boolean isMemberInsertable(Object receiver, String member) {
        return false;
    }

    @GenerateLibrary.Abstract(ifExported={"isMemberModifiable", "isMemberInsertable"})
    public void writeMember(Object receiver, String member, Object value2) throws UnsupportedMessageException, UnknownIdentifierException, UnsupportedTypeException {
        throw UnsupportedMessageException.create();
    }

    @GenerateLibrary.Abstract(ifExported={"removeMember"})
    public boolean isMemberRemovable(Object receiver, String member) {
        return false;
    }

    @GenerateLibrary.Abstract(ifExported={"isMemberRemovable"})
    public void removeMember(Object receiver, String member) throws UnsupportedMessageException, UnknownIdentifierException {
        throw UnsupportedMessageException.create();
    }

    @GenerateLibrary.Abstract(ifExported={"invokeMember"})
    public boolean isMemberInvocable(Object receiver, String member) {
        return false;
    }

    @GenerateLibrary.Abstract(ifExported={"isMemberInvocable"})
    public Object invokeMember(Object receiver, String member, Object ... arguments) throws UnsupportedMessageException, ArityException, UnknownIdentifierException, UnsupportedTypeException {
        throw UnsupportedMessageException.create();
    }

    public boolean isMemberInternal(Object receiver, String member) {
        return false;
    }

    public final boolean isMemberWritable(Object receiver, String member) {
        return this.isMemberModifiable(receiver, member) || this.isMemberInsertable(receiver, member);
    }

    public final boolean isMemberExisting(Object receiver, String member) {
        return this.isMemberReadable(receiver, member) || this.isMemberModifiable(receiver, member) || this.isMemberRemovable(receiver, member) || this.isMemberInvocable(receiver, member);
    }

    public boolean hasMemberReadSideEffects(Object receiver, String member) {
        return false;
    }

    public boolean hasMemberWriteSideEffects(Object receiver, String member) {
        return false;
    }

    @GenerateLibrary.Abstract(ifExported={"getHashSize", "isHashEntryReadable", "readHashValue", "readHashValueOrDefault", "isHashEntryModifiable", "isHashEntryInsertable", "writeHashEntry", "isHashEntryRemovable", "removeHashEntry", "getHashEntriesIterator", "getHashKeysIterator", "getHashValuesIterator"})
    public boolean hasHashEntries(Object receiver) {
        return false;
    }

    @GenerateLibrary.Abstract(ifExported={"hasHashEntries"})
    public long getHashSize(Object receiver) throws UnsupportedMessageException {
        throw UnsupportedMessageException.create();
    }

    @GenerateLibrary.Abstract(ifExported={"readHashValue"})
    public boolean isHashEntryReadable(Object receiver, Object key) {
        return false;
    }

    @GenerateLibrary.Abstract(ifExported={"isHashEntryReadable"})
    public Object readHashValue(Object receiver, Object key) throws UnsupportedMessageException, UnknownKeyException {
        throw UnsupportedMessageException.create();
    }

    public Object readHashValueOrDefault(Object receiver, Object key, Object defaultValue) throws UnsupportedMessageException {
        try {
            return this.readHashValue(receiver, key);
        }
        catch (UnknownKeyException e) {
            return defaultValue;
        }
    }

    @GenerateLibrary.Abstract(ifExported={"writeHashEntry"})
    public boolean isHashEntryModifiable(Object receiver, Object key) {
        return false;
    }

    @GenerateLibrary.Abstract(ifExported={"writeHashEntry"})
    public boolean isHashEntryInsertable(Object receiver, Object key) {
        return false;
    }

    public boolean isHashEntryWritable(Object receiver, Object key) {
        return this.isHashEntryModifiable(receiver, key) || this.isHashEntryInsertable(receiver, key);
    }

    @GenerateLibrary.Abstract(ifExported={"isHashEntryModifiable", "isHashEntryInsertable"})
    public void writeHashEntry(Object receiver, Object key, Object value2) throws UnsupportedMessageException, UnknownKeyException, UnsupportedTypeException {
        throw UnsupportedMessageException.create();
    }

    @GenerateLibrary.Abstract(ifExported={"removeHashEntry"})
    public boolean isHashEntryRemovable(Object receiver, Object key) {
        return false;
    }

    @GenerateLibrary.Abstract(ifExported={"isHashEntryRemovable"})
    public void removeHashEntry(Object receiver, Object key) throws UnsupportedMessageException, UnknownKeyException {
        throw UnsupportedMessageException.create();
    }

    public boolean isHashEntryExisting(Object receiver, Object key) {
        return this.isHashEntryReadable(receiver, key) || this.isHashEntryModifiable(receiver, key) || this.isHashEntryRemovable(receiver, key);
    }

    @GenerateLibrary.Abstract(ifExported={"hasHashEntries"})
    public Object getHashEntriesIterator(Object receiver) throws UnsupportedMessageException {
        throw UnsupportedMessageException.create();
    }

    public Object getHashKeysIterator(Object receiver) throws UnsupportedMessageException {
        Object entriesIterator = this.getHashEntriesIterator(receiver);
        return HashIterator.keys(entriesIterator);
    }

    public Object getHashValuesIterator(Object receiver) throws UnsupportedMessageException {
        Object entriesIterator = this.getHashEntriesIterator(receiver);
        return HashIterator.values(entriesIterator);
    }

    @GenerateLibrary.Abstract(ifExported={"readArrayElement", "writeArrayElement", "removeArrayElement", "isArrayElementModifiable", "isArrayElementRemovable", "isArrayElementReadable", "getArraySize"})
    public boolean hasArrayElements(Object receiver) {
        return false;
    }

    @GenerateLibrary.Abstract(ifExported={"hasArrayElements"})
    public Object readArrayElement(Object receiver, long index) throws UnsupportedMessageException, InvalidArrayIndexException {
        throw UnsupportedMessageException.create();
    }

    @GenerateLibrary.Abstract(ifExported={"hasArrayElements"})
    public long getArraySize(Object receiver) throws UnsupportedMessageException {
        throw UnsupportedMessageException.create();
    }

    @GenerateLibrary.Abstract(ifExported={"hasArrayElements"})
    public boolean isArrayElementReadable(Object receiver, long index) {
        return false;
    }

    @GenerateLibrary.Abstract(ifExported={"isArrayElementModifiable", "isArrayElementInsertable"})
    public void writeArrayElement(Object receiver, long index, Object value2) throws UnsupportedMessageException, UnsupportedTypeException, InvalidArrayIndexException {
        throw UnsupportedMessageException.create();
    }

    @GenerateLibrary.Abstract(ifExported={"isArrayElementRemovable"})
    public void removeArrayElement(Object receiver, long index) throws UnsupportedMessageException, InvalidArrayIndexException {
        throw UnsupportedMessageException.create();
    }

    @GenerateLibrary.Abstract(ifExported={"writeArrayElement"})
    public boolean isArrayElementModifiable(Object receiver, long index) {
        return false;
    }

    @GenerateLibrary.Abstract(ifExported={"writeArrayElement"})
    public boolean isArrayElementInsertable(Object receiver, long index) {
        return false;
    }

    @GenerateLibrary.Abstract(ifExported={"removeArrayElement"})
    public boolean isArrayElementRemovable(Object receiver, long index) {
        return false;
    }

    public final boolean isArrayElementWritable(Object receiver, long index) {
        return this.isArrayElementModifiable(receiver, index) || this.isArrayElementInsertable(receiver, index);
    }

    public final boolean isArrayElementExisting(Object receiver, long index) {
        return this.isArrayElementModifiable(receiver, index) || this.isArrayElementReadable(receiver, index) || this.isArrayElementRemovable(receiver, index);
    }

    @GenerateLibrary.Abstract(ifExported={"getBufferSize", "isBufferWritable", "readBufferByte", "readBufferShort", "readBufferInt", "readBufferLong", "readBufferFloat", "readBufferDouble", "writeBufferByte", "writeBufferShort", "writeBufferInt", "writeBufferLong", "writeBufferFloat", "writeBufferDouble"})
    public boolean hasBufferElements(Object receiver) {
        return false;
    }

    @GenerateLibrary.Abstract(ifExported={"writeBufferByte", "writeBufferShort", "writeBufferInt", "writeBufferLong", "writeBufferFloat", "writeBufferDouble"})
    public boolean isBufferWritable(Object receiver) throws UnsupportedMessageException {
        if (this.hasBufferElements(receiver)) {
            return false;
        }
        throw UnsupportedMessageException.create();
    }

    @GenerateLibrary.Abstract(ifExported={"hasBufferElements"})
    public long getBufferSize(Object receiver) throws UnsupportedMessageException {
        throw UnsupportedMessageException.create();
    }

    @GenerateLibrary.Abstract(ifExported={"hasBufferElements"})
    public byte readBufferByte(Object receiver, long byteOffset) throws UnsupportedMessageException, InvalidBufferOffsetException {
        throw UnsupportedMessageException.create();
    }

    @GenerateLibrary.Abstract(ifExported={"isBufferWritable"})
    public void writeBufferByte(Object receiver, long byteOffset, byte value2) throws UnsupportedMessageException, InvalidBufferOffsetException {
        throw UnsupportedMessageException.create();
    }

    @GenerateLibrary.Abstract(ifExported={"hasBufferElements"})
    public short readBufferShort(Object receiver, ByteOrder order, long byteOffset) throws UnsupportedMessageException, InvalidBufferOffsetException {
        throw UnsupportedMessageException.create();
    }

    @GenerateLibrary.Abstract(ifExported={"isBufferWritable"})
    public void writeBufferShort(Object receiver, ByteOrder order, long byteOffset, short value2) throws UnsupportedMessageException, InvalidBufferOffsetException {
        throw UnsupportedMessageException.create();
    }

    @GenerateLibrary.Abstract(ifExported={"hasBufferElements"})
    public int readBufferInt(Object receiver, ByteOrder order, long byteOffset) throws UnsupportedMessageException, InvalidBufferOffsetException {
        throw UnsupportedMessageException.create();
    }

    @GenerateLibrary.Abstract(ifExported={"isBufferWritable"})
    public void writeBufferInt(Object receiver, ByteOrder order, long byteOffset, int value2) throws UnsupportedMessageException, InvalidBufferOffsetException {
        throw UnsupportedMessageException.create();
    }

    @GenerateLibrary.Abstract(ifExported={"hasBufferElements"})
    public long readBufferLong(Object receiver, ByteOrder order, long byteOffset) throws UnsupportedMessageException, InvalidBufferOffsetException {
        throw UnsupportedMessageException.create();
    }

    @GenerateLibrary.Abstract(ifExported={"isBufferWritable"})
    public void writeBufferLong(Object receiver, ByteOrder order, long byteOffset, long value2) throws UnsupportedMessageException, InvalidBufferOffsetException {
        throw UnsupportedMessageException.create();
    }

    @GenerateLibrary.Abstract(ifExported={"hasBufferElements"})
    public float readBufferFloat(Object receiver, ByteOrder order, long byteOffset) throws UnsupportedMessageException, InvalidBufferOffsetException {
        throw UnsupportedMessageException.create();
    }

    @GenerateLibrary.Abstract(ifExported={"isBufferWritable"})
    public void writeBufferFloat(Object receiver, ByteOrder order, long byteOffset, float value2) throws UnsupportedMessageException, InvalidBufferOffsetException {
        throw UnsupportedMessageException.create();
    }

    @GenerateLibrary.Abstract(ifExported={"hasBufferElements"})
    public double readBufferDouble(Object receiver, ByteOrder order, long byteOffset) throws UnsupportedMessageException, InvalidBufferOffsetException {
        throw UnsupportedMessageException.create();
    }

    @GenerateLibrary.Abstract(ifExported={"isBufferWritable"})
    public void writeBufferDouble(Object receiver, ByteOrder order, long byteOffset, double value2) throws UnsupportedMessageException, InvalidBufferOffsetException {
        throw UnsupportedMessageException.create();
    }

    @GenerateLibrary.Abstract(ifExported={"asPointer"})
    public boolean isPointer(Object receiver) {
        return false;
    }

    @GenerateLibrary.Abstract(ifExported={"isPointer"})
    public long asPointer(Object receiver) throws UnsupportedMessageException {
        throw UnsupportedMessageException.create();
    }

    public void toNative(Object receiver) {
    }

    public Instant asInstant(Object receiver) throws UnsupportedMessageException {
        if (this.isDate(receiver) && this.isTime(receiver) && this.isTimeZone(receiver)) {
            LocalDate date = this.asDate(receiver);
            LocalTime time = this.asTime(receiver);
            ZoneId zone = this.asTimeZone(receiver);
            return InteropLibrary.toInstant(date, time, zone);
        }
        throw UnsupportedMessageException.create();
    }

    @CompilerDirectives.TruffleBoundary
    private static Instant toInstant(LocalDate date, LocalTime time, ZoneId zone) {
        return ZonedDateTime.of(date, time, zone).toInstant();
    }

    public final boolean isInstant(Object receiver) {
        return this.isDate(receiver) && this.isTime(receiver) && this.isTimeZone(receiver);
    }

    @GenerateLibrary.Abstract(ifExported={"asTimeZone", "asInstant"})
    public boolean isTimeZone(Object receiver) {
        return false;
    }

    @GenerateLibrary.Abstract(ifExported={"isTimeZone", "asInstant"})
    public ZoneId asTimeZone(Object receiver) throws UnsupportedMessageException {
        throw UnsupportedMessageException.create();
    }

    @GenerateLibrary.Abstract(ifExported={"asDate", "asInstant"})
    public boolean isDate(Object receiver) {
        return false;
    }

    @GenerateLibrary.Abstract(ifExported={"isDate", "asInstant"})
    public LocalDate asDate(Object receiver) throws UnsupportedMessageException {
        throw UnsupportedMessageException.create();
    }

    @GenerateLibrary.Abstract(ifExported={"asTime", "asInstant"})
    public boolean isTime(Object receiver) {
        return false;
    }

    @GenerateLibrary.Abstract(ifExported={"isTime", "asInstant"})
    public LocalTime asTime(Object receiver) throws UnsupportedMessageException {
        throw UnsupportedMessageException.create();
    }

    @GenerateLibrary.Abstract(ifExported={"asDuration"})
    public boolean isDuration(Object receiver) {
        return false;
    }

    @GenerateLibrary.Abstract(ifExported={"isDuration"})
    public Duration asDuration(Object receiver) throws UnsupportedMessageException {
        throw UnsupportedMessageException.create();
    }

    @GenerateLibrary.Abstract(ifExported={"throwException"})
    public boolean isException(Object receiver) {
        return InteropAccessor.EXCEPTION.isException(receiver);
    }

    @GenerateLibrary.Abstract(ifExported={"isException"})
    public RuntimeException throwException(Object receiver) throws UnsupportedMessageException {
        if (InteropAccessor.EXCEPTION.isException(receiver)) {
            throw InteropAccessor.EXCEPTION.throwException(receiver);
        }
        throw UnsupportedMessageException.create();
    }

    @GenerateLibrary.Abstract(ifExported={"getExceptionExitStatus", "isExceptionIncompleteSource"})
    public ExceptionType getExceptionType(Object receiver) throws UnsupportedMessageException {
        if (InteropAccessor.EXCEPTION.isException(receiver)) {
            return (ExceptionType)((Object)InteropAccessor.EXCEPTION.getExceptionType(receiver));
        }
        throw UnsupportedMessageException.create();
    }

    public boolean isExceptionIncompleteSource(Object receiver) throws UnsupportedMessageException {
        if (InteropAccessor.EXCEPTION.isException(receiver)) {
            return InteropAccessor.EXCEPTION.isExceptionIncompleteSource(receiver);
        }
        throw UnsupportedMessageException.create();
    }

    public int getExceptionExitStatus(Object receiver) throws UnsupportedMessageException {
        if (InteropAccessor.EXCEPTION.isException(receiver)) {
            return InteropAccessor.EXCEPTION.getExceptionExitStatus(receiver);
        }
        throw UnsupportedMessageException.create();
    }

    @GenerateLibrary.Abstract(ifExported={"getExceptionCause"})
    public boolean hasExceptionCause(Object receiver) {
        if (InteropAccessor.EXCEPTION.isException(receiver)) {
            return InteropAccessor.EXCEPTION.hasExceptionCause(receiver);
        }
        return false;
    }

    @GenerateLibrary.Abstract(ifExported={"hasExceptionCause"})
    public Object getExceptionCause(Object receiver) throws UnsupportedMessageException {
        if (InteropAccessor.EXCEPTION.isException(receiver)) {
            return InteropAccessor.EXCEPTION.getExceptionCause(receiver);
        }
        throw UnsupportedMessageException.create();
    }

    @GenerateLibrary.Abstract(ifExported={"getExceptionMessage"})
    public boolean hasExceptionMessage(Object receiver) {
        if (InteropAccessor.EXCEPTION.isException(receiver)) {
            return InteropAccessor.EXCEPTION.hasExceptionMessage(receiver);
        }
        return false;
    }

    @GenerateLibrary.Abstract(ifExported={"hasExceptionMessage"})
    public Object getExceptionMessage(Object receiver) throws UnsupportedMessageException {
        if (InteropAccessor.EXCEPTION.isException(receiver)) {
            return InteropAccessor.EXCEPTION.getExceptionMessage(receiver);
        }
        throw UnsupportedMessageException.create();
    }

    @GenerateLibrary.Abstract(ifExported={"getExceptionStackTrace"})
    public boolean hasExceptionStackTrace(Object receiver) {
        if (InteropAccessor.EXCEPTION.isException(receiver)) {
            return InteropAccessor.EXCEPTION.hasExceptionStackTrace(receiver);
        }
        return false;
    }

    @GenerateLibrary.Abstract(ifExported={"hasExceptionStackTrace"})
    public Object getExceptionStackTrace(Object receiver) throws UnsupportedMessageException {
        if (InteropAccessor.EXCEPTION.isException(receiver)) {
            return InteropAccessor.EXCEPTION.getExceptionStackTrace(receiver);
        }
        throw UnsupportedMessageException.create();
    }

    @GenerateLibrary.Abstract(ifExported={"getIterator"})
    public boolean hasIterator(Object receiver) {
        return this.hasArrayElements(receiver);
    }

    @GenerateLibrary.Abstract(ifExported={"hasIterator"})
    public Object getIterator(Object receiver) throws UnsupportedMessageException {
        if (!this.hasIterator(receiver)) {
            throw UnsupportedMessageException.create();
        }
        return new ArrayIterator(receiver);
    }

    @GenerateLibrary.Abstract(ifExported={"hasIteratorNextElement", "getIteratorNextElement"})
    public boolean isIterator(Object receiver) {
        return false;
    }

    @GenerateLibrary.Abstract(ifExported={"isIterator", "getIteratorNextElement"})
    public boolean hasIteratorNextElement(Object receiver) throws UnsupportedMessageException {
        throw UnsupportedMessageException.create();
    }

    @GenerateLibrary.Abstract(ifExported={"isIterator", "hasIteratorNextElement"})
    public Object getIteratorNextElement(Object receiver) throws UnsupportedMessageException, StopIterationException {
        throw UnsupportedMessageException.create();
    }

    @CompilerDirectives.TruffleBoundary
    @GenerateLibrary.Abstract(ifExported={"getSourceLocation"})
    public boolean hasSourceLocation(Object receiver) {
        if (InteropAccessor.EXCEPTION.isException(receiver)) {
            return InteropAccessor.EXCEPTION.hasSourceLocation(receiver);
        }
        return false;
    }

    @CompilerDirectives.TruffleBoundary
    @GenerateLibrary.Abstract(ifExported={"hasSourceLocation"})
    public SourceSection getSourceLocation(Object receiver) throws UnsupportedMessageException {
        if (InteropAccessor.EXCEPTION.isException(receiver)) {
            return InteropAccessor.EXCEPTION.getSourceLocation(receiver);
        }
        throw UnsupportedMessageException.create();
    }

    @GenerateLibrary.Abstract(ifExported={"getLanguage", "isScope"})
    public boolean hasLanguage(Object receiver) {
        return false;
    }

    @GenerateLibrary.Abstract(ifExported={"hasLanguage"})
    public Class<? extends TruffleLanguage<?>> getLanguage(Object receiver) throws UnsupportedMessageException {
        throw UnsupportedMessageException.create();
    }

    @GenerateLibrary.Abstract(ifExported={"getMetaObject"})
    public boolean hasMetaObject(Object receiver) {
        return false;
    }

    @GenerateLibrary.Abstract(ifExported={"hasMetaObject"})
    public Object getMetaObject(Object receiver) throws UnsupportedMessageException {
        throw UnsupportedMessageException.create();
    }

    @CompilerDirectives.TruffleBoundary
    @GenerateLibrary.Abstract(ifExported={"hasLanguage", "getLanguage", "isScope"})
    public Object toDisplayString(Object receiver, boolean allowSideEffects) {
        if (allowSideEffects) {
            return Objects.toString(receiver);
        }
        return receiver.getClass().getTypeName() + "@" + Integer.toHexString(System.identityHashCode(receiver));
    }

    public final Object toDisplayString(Object receiver) {
        return this.toDisplayString(receiver, true);
    }

    @GenerateLibrary.Abstract(ifExported={"getMetaQualifiedName", "getMetaSimpleName", "isMetaInstance"})
    public boolean isMetaObject(Object receiver) {
        return false;
    }

    @GenerateLibrary.Abstract(ifExported={"isMetaObject"})
    public Object getMetaQualifiedName(Object metaObject) throws UnsupportedMessageException {
        throw UnsupportedMessageException.create();
    }

    @GenerateLibrary.Abstract(ifExported={"isMetaObject"})
    public Object getMetaSimpleName(Object metaObject) throws UnsupportedMessageException {
        throw UnsupportedMessageException.create();
    }

    @GenerateLibrary.Abstract(ifExported={"isMetaObject"})
    public boolean isMetaInstance(Object receiver, Object instance) throws UnsupportedMessageException {
        throw UnsupportedMessageException.create();
    }

    @GenerateLibrary.Abstract(ifExported={"getMetaParents"})
    public boolean hasMetaParents(Object receiver) {
        return false;
    }

    @GenerateLibrary.Abstract(ifExported={"hasMetaParents"})
    public Object getMetaParents(Object receiver) throws UnsupportedMessageException {
        throw UnsupportedMessageException.create();
    }

    @GenerateLibrary.Abstract(ifExported={"isIdentical", "identityHashCode"})
    protected TriState isIdenticalOrUndefined(Object receiver, Object other) {
        return TriState.UNDEFINED;
    }

    public boolean isIdentical(Object receiver, Object other, InteropLibrary otherInterop) {
        TriState result = this.isIdenticalOrUndefined(receiver, other);
        if (result == TriState.UNDEFINED) {
            result = otherInterop.isIdenticalOrUndefined(other, receiver);
        }
        return result == TriState.TRUE;
    }

    public final boolean hasIdentity(Object receiver) {
        return this.isIdentical(receiver, receiver, this);
    }

    @GenerateLibrary.Abstract(ifExported={"isIdenticalOrUndefined"})
    public int identityHashCode(Object receiver) throws UnsupportedMessageException {
        throw UnsupportedMessageException.create();
    }

    @GenerateLibrary.Abstract(ifExported={"hasScopeParent"})
    public boolean isScope(Object receiver) {
        return false;
    }

    @GenerateLibrary.Abstract(ifExported={"getScopeParent"})
    public boolean hasScopeParent(Object receiver) {
        return false;
    }

    @GenerateLibrary.Abstract(ifExported={"hasScopeParent"})
    public Object getScopeParent(Object receiver) throws UnsupportedMessageException {
        throw UnsupportedMessageException.create();
    }

    public static LibraryFactory<InteropLibrary> getFactory() {
        return FACTORY;
    }

    public static InteropLibrary getUncached() {
        return UNCACHED;
    }

    public static InteropLibrary getUncached(Object v) {
        return FACTORY.getUncached(v);
    }

    protected final boolean assertAdopted() {
        assert (this.getRootNode() != null) : "Invalid library usage. Cached library must be adopted by a RootNode before it is executed.";
        return true;
    }

    @CompilerDirectives.TruffleBoundary
    public static boolean isValidValue(Object receiver) {
        return receiver instanceof TruffleObject || receiver instanceof Boolean || receiver instanceof Byte || receiver instanceof Short || receiver instanceof Character || receiver instanceof Integer || receiver instanceof Long || receiver instanceof Float || receiver instanceof Double || receiver instanceof String || receiver instanceof TruffleString;
    }

    @CompilerDirectives.TruffleBoundary
    public static boolean isValidProtocolValue(Object value2) {
        return InteropLibrary.isValidValue(value2) || value2 instanceof ByteOrder || value2 instanceof Instant || value2 instanceof ZoneId || value2 instanceof LocalDate || value2 instanceof LocalTime || value2 instanceof Duration || value2 instanceof ExceptionType || value2 instanceof SourceSection || value2 instanceof Class || value2 instanceof TriState || value2 instanceof InteropLibrary || value2 instanceof Object[];
    }

    static class Asserts
    extends InteropLibrary {
        @Node.Child
        private InteropLibrary delegate;

        Asserts(InteropLibrary delegate) {
            this.delegate = delegate;
        }

        private static boolean isMultiThreaded(Object receiver) {
            Accessor.EngineSupport engine = InteropAccessor.ACCESSOR.engineSupport();
            if (engine == null) {
                return false;
            }
            return engine.isMultiThreaded(receiver);
        }

        @Override
        public boolean accepts(Object receiver) {
            assert (AssertUtils.preCondition(receiver));
            return this.delegate.accepts(receiver);
        }

        @Override
        public boolean isNull(Object receiver) {
            assert (AssertUtils.preCondition(receiver));
            boolean result = this.delegate.isNull(receiver);
            assert (!result || this.notOtherType(receiver, Type.NULL));
            assert (AssertUtils.validProtocolReturn(receiver, result));
            return result;
        }

        private boolean notOtherType(Object receiver, Type type) {
            assert (type == Type.NULL || !this.delegate.isNull(receiver)) : AssertUtils.violationInvariant(receiver);
            assert (type == Type.BOOLEAN || !this.delegate.isBoolean(receiver)) : AssertUtils.violationInvariant(receiver);
            assert (type == Type.STRING || !this.delegate.isString(receiver)) : AssertUtils.violationInvariant(receiver);
            assert (type == Type.NUMBER || !this.delegate.isNumber(receiver)) : AssertUtils.violationInvariant(receiver);
            assert (type == Type.DATE_TIME_ZONE || !this.delegate.isDate(receiver) && !this.delegate.isTime(receiver) && !this.delegate.isTimeZone(receiver)) : AssertUtils.violationInvariant(receiver);
            assert (type == Type.DURATION || !this.delegate.isDuration(receiver)) : AssertUtils.violationInvariant(receiver);
            assert (type == Type.META_OBJECT || !this.delegate.isMetaObject(receiver)) : AssertUtils.violationInvariant(receiver);
            assert (type == Type.ITERATOR || !this.delegate.isIterator(receiver)) : AssertUtils.violationInvariant(receiver);
            return true;
        }

        @Override
        public boolean isBoolean(Object receiver) {
            if (CompilerDirectives.inCompiledCode()) {
                return this.delegate.isBoolean(receiver);
            }
            assert (AssertUtils.preCondition(receiver));
            boolean result = this.delegate.isBoolean(receiver);
            if (result) {
                try {
                    this.delegate.asBoolean(receiver);
                }
                catch (InteropException e) {
                    assert (false) : AssertUtils.violationInvariant(receiver);
                }
                catch (Exception exception) {
                    // empty catch block
                }
            }
            assert (!result || this.notOtherType(receiver, Type.BOOLEAN));
            assert (AssertUtils.validProtocolReturn(receiver, result));
            return result;
        }

        @Override
        public boolean asBoolean(Object receiver) throws UnsupportedMessageException {
            if (CompilerDirectives.inCompiledCode()) {
                return this.delegate.asBoolean(receiver);
            }
            assert (AssertUtils.preCondition(receiver));
            boolean wasBoolean = this.delegate.isBoolean(receiver);
            try {
                boolean result = this.delegate.asBoolean(receiver);
                assert (wasBoolean) : AssertUtils.violationInvariant(receiver);
                assert (this.notOtherType(receiver, Type.BOOLEAN));
                assert (AssertUtils.validProtocolReturn(receiver, result));
                return result;
            }
            catch (InteropException e) {
                assert (e instanceof UnsupportedMessageException) : AssertUtils.violationInvariant(receiver);
                assert (!wasBoolean) : AssertUtils.violationInvariant(receiver);
                throw e;
            }
        }

        @Override
        public boolean isExecutable(Object receiver) {
            assert (AssertUtils.preCondition(receiver));
            boolean result = this.delegate.isExecutable(receiver);
            return result;
        }

        @Override
        public Object execute(Object receiver, Object ... arguments) throws UnsupportedTypeException, ArityException, UnsupportedMessageException {
            if (CompilerDirectives.inCompiledCode()) {
                return this.delegate.execute(receiver, arguments);
            }
            assert (AssertUtils.preCondition(receiver));
            assert (AssertUtils.validProtocolArgument(receiver, arguments));
            assert (AssertUtils.validArguments(receiver, arguments));
            boolean wasExecutable = this.delegate.isExecutable(receiver);
            try {
                Object result = this.delegate.execute(receiver, arguments);
                assert (wasExecutable) : AssertUtils.violationInvariant(receiver, arguments);
                assert (AssertUtils.validInteropReturn(receiver, result));
                return result;
            }
            catch (InteropException e) {
                assert (e instanceof UnsupportedMessageException || e instanceof ArityException || e instanceof UnsupportedTypeException) : AssertUtils.violationInvariant(receiver, arguments);
                assert (!(e instanceof UnsupportedMessageException) || !wasExecutable) : AssertUtils.violationInvariant(receiver, arguments);
                throw e;
            }
        }

        @Override
        public boolean isInstantiable(Object receiver) {
            assert (AssertUtils.preCondition(receiver));
            boolean result = this.delegate.isInstantiable(receiver);
            assert (AssertUtils.validProtocolReturn(receiver, result));
            return result;
        }

        @Override
        public Object instantiate(Object receiver, Object ... arguments) throws UnsupportedTypeException, ArityException, UnsupportedMessageException {
            if (CompilerDirectives.inCompiledCode()) {
                return this.delegate.instantiate(receiver, arguments);
            }
            assert (AssertUtils.preCondition(receiver));
            assert (AssertUtils.validProtocolArgument(receiver, arguments));
            assert (AssertUtils.validArguments(receiver, arguments));
            boolean wasInstantiable = this.delegate.isInstantiable(receiver);
            try {
                Object result = this.delegate.instantiate(receiver, arguments);
                assert (wasInstantiable) : AssertUtils.violationInvariant(receiver, arguments);
                assert (AssertUtils.validInteropReturn(receiver, result));
                return result;
            }
            catch (InteropException e) {
                assert (e instanceof UnsupportedMessageException || e instanceof ArityException || e instanceof UnsupportedTypeException) : AssertUtils.violationInvariant(receiver, arguments);
                assert (!(e instanceof UnsupportedMessageException) || !wasInstantiable) : AssertUtils.violationInvariant(receiver, arguments);
                throw e;
            }
        }

        @Override
        public boolean isString(Object receiver) {
            if (CompilerDirectives.inCompiledCode()) {
                return this.delegate.isString(receiver);
            }
            assert (AssertUtils.preCondition(receiver));
            boolean result = this.delegate.isString(receiver);
            if (result) {
                try {
                    this.delegate.asString(receiver);
                }
                catch (InteropException e) {
                    assert (false) : AssertUtils.violationInvariant(receiver);
                }
                catch (Exception exception) {
                    // empty catch block
                }
            }
            assert (!result || this.notOtherType(receiver, Type.STRING));
            assert (AssertUtils.validProtocolReturn(receiver, result));
            return result;
        }

        @Override
        public String asString(Object receiver) throws UnsupportedMessageException {
            if (CompilerDirectives.inCompiledCode()) {
                return this.delegate.asString(receiver);
            }
            assert (AssertUtils.preCondition(receiver));
            boolean wasString = this.delegate.isString(receiver);
            try {
                String result = this.delegate.asString(receiver);
                assert (wasString) : AssertUtils.violationInvariant(receiver);
                assert (AssertUtils.validProtocolReturn(receiver, result));
                return result;
            }
            catch (InteropException e) {
                assert (e instanceof UnsupportedMessageException) : AssertUtils.violationInvariant(receiver);
                assert (!wasString) : AssertUtils.violationInvariant(receiver);
                throw e;
            }
        }

        @Override
        public TruffleString asTruffleString(Object receiver) throws UnsupportedMessageException {
            if (CompilerDirectives.inCompiledCode()) {
                return this.delegate.asTruffleString(receiver);
            }
            assert (AssertUtils.preCondition(receiver));
            boolean wasString = this.delegate.isString(receiver);
            try {
                TruffleString result = this.delegate.asTruffleString(receiver);
                assert (wasString) : AssertUtils.violationInvariant(receiver);
                return result;
            }
            catch (InteropException e) {
                assert (e instanceof UnsupportedMessageException) : AssertUtils.violationInvariant(receiver);
                assert (!wasString) : AssertUtils.violationInvariant(receiver);
                throw e;
            }
        }

        @Override
        public boolean isNumber(Object receiver) {
            assert (AssertUtils.preCondition(receiver));
            boolean result = this.delegate.isNumber(receiver);
            assert (!result || this.notOtherType(receiver, Type.NUMBER));
            assert (AssertUtils.validProtocolReturn(receiver, result));
            return result;
        }

        @Override
        public boolean fitsInByte(Object receiver) {
            if (CompilerDirectives.inCompiledCode()) {
                return this.delegate.fitsInByte(receiver);
            }
            assert (AssertUtils.preCondition(receiver));
            boolean fits2 = this.delegate.fitsInByte(receiver);
            assert (!fits2 || this.delegate.isNumber(receiver)) : AssertUtils.violationInvariant(receiver);
            assert (!fits2 || this.delegate.fitsInShort(receiver)) : AssertUtils.violationInvariant(receiver);
            assert (!fits2 || this.delegate.fitsInInt(receiver)) : AssertUtils.violationInvariant(receiver);
            assert (!fits2 || this.delegate.fitsInLong(receiver)) : AssertUtils.violationInvariant(receiver);
            assert (!fits2 || this.delegate.fitsInFloat(receiver)) : AssertUtils.violationInvariant(receiver);
            assert (!fits2 || this.delegate.fitsInDouble(receiver)) : AssertUtils.violationInvariant(receiver);
            if (fits2) {
                try {
                    this.delegate.asByte(receiver);
                }
                catch (InteropException e) {
                    assert (false) : AssertUtils.violationInvariant(receiver);
                }
                catch (Exception exception) {
                    // empty catch block
                }
            }
            assert (!fits2 || this.notOtherType(receiver, Type.NUMBER));
            assert (AssertUtils.validProtocolReturn(receiver, fits2));
            return fits2;
        }

        @Override
        public boolean fitsInShort(Object receiver) {
            if (CompilerDirectives.inCompiledCode()) {
                return this.delegate.fitsInShort(receiver);
            }
            assert (AssertUtils.preCondition(receiver));
            boolean fits2 = this.delegate.fitsInShort(receiver);
            assert (!fits2 || this.delegate.isNumber(receiver)) : AssertUtils.violationInvariant(receiver);
            assert (!fits2 || this.delegate.fitsInInt(receiver)) : AssertUtils.violationInvariant(receiver);
            assert (!fits2 || this.delegate.fitsInLong(receiver)) : AssertUtils.violationInvariant(receiver);
            assert (!fits2 || this.delegate.fitsInFloat(receiver)) : AssertUtils.violationInvariant(receiver);
            assert (!fits2 || this.delegate.fitsInDouble(receiver)) : AssertUtils.violationInvariant(receiver);
            if (fits2) {
                try {
                    this.delegate.asShort(receiver);
                }
                catch (InteropException e) {
                    assert (false) : AssertUtils.violationInvariant(receiver);
                }
                catch (Exception exception) {
                    // empty catch block
                }
            }
            assert (!fits2 || this.notOtherType(receiver, Type.NUMBER));
            assert (AssertUtils.validProtocolReturn(receiver, fits2));
            return fits2;
        }

        @Override
        public boolean fitsInInt(Object receiver) {
            if (CompilerDirectives.inCompiledCode()) {
                return this.delegate.fitsInInt(receiver);
            }
            assert (AssertUtils.preCondition(receiver));
            boolean fits2 = this.delegate.fitsInInt(receiver);
            assert (!fits2 || this.delegate.isNumber(receiver)) : AssertUtils.violationInvariant(receiver);
            assert (!fits2 || this.delegate.fitsInLong(receiver)) : AssertUtils.violationInvariant(receiver);
            assert (!fits2 || this.delegate.fitsInDouble(receiver)) : AssertUtils.violationInvariant(receiver);
            if (fits2) {
                try {
                    this.delegate.asInt(receiver);
                }
                catch (InteropException e) {
                    assert (false) : AssertUtils.violationInvariant(receiver);
                }
                catch (Exception exception) {
                    // empty catch block
                }
            }
            assert (!fits2 || this.notOtherType(receiver, Type.NUMBER));
            assert (AssertUtils.validProtocolReturn(receiver, fits2));
            return fits2;
        }

        @Override
        public boolean fitsInLong(Object receiver) {
            if (CompilerDirectives.inCompiledCode()) {
                return this.delegate.fitsInLong(receiver);
            }
            assert (AssertUtils.preCondition(receiver));
            boolean fits2 = this.delegate.fitsInLong(receiver);
            assert (!fits2 || this.delegate.isNumber(receiver)) : AssertUtils.violationInvariant(receiver);
            if (fits2) {
                try {
                    this.delegate.asLong(receiver);
                }
                catch (InteropException e) {
                    assert (false) : AssertUtils.violationInvariant(receiver);
                }
                catch (Exception exception) {
                    // empty catch block
                }
            }
            assert (!fits2 || this.notOtherType(receiver, Type.NUMBER));
            assert (AssertUtils.validProtocolReturn(receiver, fits2));
            return fits2;
        }

        @Override
        public boolean fitsInFloat(Object receiver) {
            if (CompilerDirectives.inCompiledCode()) {
                return this.delegate.fitsInFloat(receiver);
            }
            assert (AssertUtils.preCondition(receiver));
            boolean fits2 = this.delegate.fitsInFloat(receiver);
            assert (!fits2 || this.delegate.isNumber(receiver)) : AssertUtils.violationInvariant(receiver);
            if (fits2) {
                try {
                    this.delegate.asFloat(receiver);
                }
                catch (InteropException e) {
                    assert (false) : AssertUtils.violationInvariant(receiver);
                }
                catch (Exception exception) {
                    // empty catch block
                }
            }
            assert (!fits2 || this.notOtherType(receiver, Type.NUMBER));
            assert (AssertUtils.validProtocolReturn(receiver, fits2));
            return fits2;
        }

        @Override
        public boolean fitsInDouble(Object receiver) {
            if (CompilerDirectives.inCompiledCode()) {
                return this.delegate.fitsInDouble(receiver);
            }
            assert (AssertUtils.preCondition(receiver));
            boolean fits2 = this.delegate.fitsInDouble(receiver);
            assert (!fits2 || this.delegate.isNumber(receiver)) : AssertUtils.violationInvariant(receiver);
            if (fits2) {
                try {
                    this.delegate.asDouble(receiver);
                }
                catch (InteropException e) {
                    assert (false) : AssertUtils.violationInvariant(receiver);
                }
                catch (Exception exception) {
                    // empty catch block
                }
            }
            assert (!fits2 || this.notOtherType(receiver, Type.NUMBER));
            assert (AssertUtils.validProtocolReturn(receiver, fits2));
            return fits2;
        }

        @Override
        public byte asByte(Object receiver) throws UnsupportedMessageException {
            assert (AssertUtils.preCondition(receiver));
            try {
                byte result = this.delegate.asByte(receiver);
                assert (this.delegate.isNumber(receiver)) : AssertUtils.violationInvariant(receiver);
                assert (this.delegate.fitsInByte(receiver)) : AssertUtils.violationInvariant(receiver);
                assert (result == this.delegate.asShort(receiver)) : AssertUtils.violationInvariant(receiver);
                assert (result == this.delegate.asInt(receiver)) : AssertUtils.violationInvariant(receiver);
                assert ((long)result == this.delegate.asLong(receiver)) : AssertUtils.violationInvariant(receiver);
                assert ((float)result == this.delegate.asFloat(receiver)) : AssertUtils.violationInvariant(receiver);
                assert ((double)result == this.delegate.asDouble(receiver)) : AssertUtils.violationInvariant(receiver);
                assert (AssertUtils.validProtocolReturn(receiver, result));
                return result;
            }
            catch (InteropException e) {
                assert (e instanceof UnsupportedMessageException) : AssertUtils.violationInvariant(receiver);
                throw e;
            }
        }

        @Override
        public short asShort(Object receiver) throws UnsupportedMessageException {
            assert (AssertUtils.preCondition(receiver));
            try {
                short result = this.delegate.asShort(receiver);
                assert (this.delegate.isNumber(receiver)) : AssertUtils.violationInvariant(receiver);
                assert (this.delegate.fitsInShort(receiver)) : AssertUtils.violationInvariant(receiver);
                assert (result == this.delegate.asInt(receiver)) : AssertUtils.violationInvariant(receiver);
                assert ((long)result == this.delegate.asLong(receiver)) : AssertUtils.violationInvariant(receiver);
                assert ((float)result == this.delegate.asFloat(receiver)) : AssertUtils.violationInvariant(receiver);
                assert ((double)result == this.delegate.asDouble(receiver)) : AssertUtils.violationInvariant(receiver);
                assert (AssertUtils.validProtocolReturn(receiver, result));
                return result;
            }
            catch (InteropException e) {
                assert (e instanceof UnsupportedMessageException) : AssertUtils.violationInvariant(receiver);
                throw e;
            }
        }

        @Override
        public int asInt(Object receiver) throws UnsupportedMessageException {
            assert (AssertUtils.preCondition(receiver));
            try {
                int result = this.delegate.asInt(receiver);
                assert (this.delegate.isNumber(receiver)) : AssertUtils.violationInvariant(receiver);
                assert (this.delegate.fitsInInt(receiver)) : AssertUtils.violationInvariant(receiver);
                assert ((long)result == this.delegate.asLong(receiver)) : AssertUtils.violationInvariant(receiver);
                assert ((double)result == this.delegate.asDouble(receiver)) : AssertUtils.violationInvariant(receiver);
                assert (AssertUtils.validProtocolReturn(receiver, result));
                return result;
            }
            catch (InteropException e) {
                assert (e instanceof UnsupportedMessageException) : AssertUtils.violationInvariant(receiver);
                throw e;
            }
        }

        @Override
        public long asLong(Object receiver) throws UnsupportedMessageException {
            assert (AssertUtils.preCondition(receiver));
            try {
                long result = this.delegate.asLong(receiver);
                assert (this.delegate.isNumber(receiver)) : AssertUtils.violationInvariant(receiver);
                assert (this.delegate.fitsInLong(receiver)) : AssertUtils.violationInvariant(receiver);
                assert (AssertUtils.validProtocolReturn(receiver, result));
                return result;
            }
            catch (InteropException e) {
                assert (e instanceof UnsupportedMessageException) : AssertUtils.violationInvariant(receiver);
                throw e;
            }
        }

        @Override
        public float asFloat(Object receiver) throws UnsupportedMessageException {
            assert (AssertUtils.preCondition(receiver));
            try {
                float result = this.delegate.asFloat(receiver);
                assert (this.delegate.isNumber(receiver)) : AssertUtils.violationInvariant(receiver);
                assert (this.delegate.fitsInFloat(receiver)) : AssertUtils.violationInvariant(receiver);
                assert (AssertUtils.validProtocolReturn(receiver, Float.valueOf(result)));
                return result;
            }
            catch (InteropException e) {
                assert (e instanceof UnsupportedMessageException) : AssertUtils.violationInvariant(receiver);
                throw e;
            }
        }

        @Override
        public double asDouble(Object receiver) throws UnsupportedMessageException {
            assert (AssertUtils.preCondition(receiver));
            try {
                double result = this.delegate.asDouble(receiver);
                assert (this.delegate.isNumber(receiver)) : AssertUtils.violationInvariant(receiver);
                assert (this.delegate.fitsInDouble(receiver)) : AssertUtils.violationInvariant(receiver);
                assert (AssertUtils.validProtocolReturn(receiver, result));
                return result;
            }
            catch (InteropException e) {
                assert (e instanceof UnsupportedMessageException) : AssertUtils.violationInvariant(receiver);
                throw e;
            }
        }

        @Override
        public boolean hasMembers(Object receiver) {
            assert (AssertUtils.preCondition(receiver));
            boolean result = this.delegate.hasMembers(receiver);
            assert (AssertUtils.validProtocolReturn(receiver, result));
            return result;
        }

        @Override
        public Object readMember(Object receiver, String identifier) throws UnsupportedMessageException, UnknownIdentifierException {
            if (CompilerDirectives.inCompiledCode()) {
                return this.delegate.readMember(receiver, identifier);
            }
            assert (AssertUtils.preCondition(receiver));
            assert (AssertUtils.validProtocolArgument(receiver, identifier));
            boolean wasReadable = this.delegate.isMemberReadable(receiver, identifier);
            try {
                Object result = this.delegate.readMember(receiver, identifier);
                assert (this.delegate.hasMembers(receiver)) : AssertUtils.violationInvariant(receiver, identifier);
                assert (wasReadable || Asserts.isMultiThreaded(receiver)) : AssertUtils.violationInvariant(receiver, identifier);
                assert (AssertUtils.validInteropReturn(receiver, result));
                return result;
            }
            catch (InteropException e) {
                assert (e instanceof UnsupportedMessageException || e instanceof UnknownIdentifierException) : AssertUtils.violationPost(receiver, e);
                throw e;
            }
        }

        @Override
        public void writeMember(Object receiver, String identifier, Object value2) throws UnsupportedMessageException, UnknownIdentifierException, UnsupportedTypeException {
            if (CompilerDirectives.inCompiledCode()) {
                this.delegate.writeMember(receiver, identifier, value2);
                return;
            }
            assert (AssertUtils.preCondition(receiver));
            assert (AssertUtils.validProtocolArgument(receiver, identifier));
            assert (AssertUtils.validInteropArgument(receiver, value2));
            boolean wasWritable = this.delegate.isMemberModifiable(receiver, identifier) || this.delegate.isMemberInsertable(receiver, identifier);
            try {
                this.delegate.writeMember(receiver, identifier, value2);
                assert (this.delegate.hasMembers(receiver)) : AssertUtils.violationInvariant(receiver, identifier);
                assert (wasWritable || Asserts.isMultiThreaded(receiver)) : AssertUtils.violationInvariant(receiver, identifier);
            }
            catch (InteropException e) {
                assert (e instanceof UnsupportedMessageException || e instanceof UnknownIdentifierException || e instanceof UnsupportedTypeException) : AssertUtils.violationPost(receiver, e);
                throw e;
            }
        }

        @Override
        public void removeMember(Object receiver, String identifier) throws UnsupportedMessageException, UnknownIdentifierException {
            if (CompilerDirectives.inCompiledCode()) {
                this.delegate.removeMember(receiver, identifier);
                return;
            }
            assert (AssertUtils.preCondition(receiver));
            assert (AssertUtils.validProtocolArgument(receiver, identifier));
            boolean wasRemovable = this.delegate.isMemberRemovable(receiver, identifier);
            try {
                this.delegate.removeMember(receiver, identifier);
                assert (this.delegate.hasMembers(receiver)) : AssertUtils.violationInvariant(receiver, identifier);
                assert (wasRemovable || Asserts.isMultiThreaded(receiver)) : AssertUtils.violationInvariant(receiver, identifier);
            }
            catch (InteropException e) {
                assert (e instanceof UnsupportedMessageException || e instanceof UnknownIdentifierException) : AssertUtils.violationPost(receiver, e);
                throw e;
            }
        }

        @Override
        public Object invokeMember(Object receiver, String identifier, Object ... arguments) throws UnsupportedMessageException, ArityException, UnknownIdentifierException, UnsupportedTypeException {
            if (CompilerDirectives.inCompiledCode()) {
                return this.delegate.invokeMember(receiver, identifier, arguments);
            }
            assert (AssertUtils.preCondition(receiver));
            assert (AssertUtils.validProtocolArgument(receiver, identifier));
            assert (AssertUtils.validProtocolArgument(receiver, arguments));
            assert (AssertUtils.validArguments(receiver, arguments));
            boolean wasInvocable = this.delegate.isMemberInvocable(receiver, identifier);
            try {
                Object result = this.delegate.invokeMember(receiver, identifier, arguments);
                assert (this.delegate.hasMembers(receiver)) : AssertUtils.violationInvariant(receiver, identifier);
                assert (wasInvocable || Asserts.isMultiThreaded(receiver)) : AssertUtils.violationInvariant(receiver, identifier);
                assert (AssertUtils.validInteropReturn(receiver, result));
                return result;
            }
            catch (InteropException e) {
                assert (e instanceof UnsupportedMessageException || e instanceof ArityException || e instanceof UnknownIdentifierException || e instanceof UnsupportedTypeException) : AssertUtils.violationPost(receiver, e);
                throw e;
            }
        }

        @Override
        public Object getMembers(Object receiver, boolean internal) throws UnsupportedMessageException {
            assert (AssertUtils.preCondition(receiver));
            try {
                Object result = this.delegate.getMembers(receiver, internal);
                assert (AssertUtils.validInteropReturn(receiver, result));
                assert (AssertUtils.validProtocolArgument(receiver, internal));
                assert (Asserts.isMultiThreaded(receiver) || Asserts.assertMemberKeys(receiver, result, internal));
                assert (!this.delegate.hasScopeParent(receiver) || Asserts.assertScopeMembers(receiver, result, Asserts.getUncached().getMembers(this.delegate.getScopeParent(receiver), internal)));
                assert (AssertUtils.validInteropReturn(receiver, result));
                return result;
            }
            catch (InteropException e) {
                assert (e instanceof UnsupportedMessageException) : AssertUtils.violationPost(receiver, e);
                throw e;
            }
        }

        private static boolean assertMemberKeys(Object receiver, Object result, boolean internal) {
            long arraySize;
            assert (result != null) : AssertUtils.violationPost(receiver, result);
            InteropLibrary uncached = InteropLibrary.getFactory().getUncached(result);
            assert (uncached.hasArrayElements(result)) : AssertUtils.violationPost(receiver, result);
            try {
                arraySize = uncached.getArraySize(result);
            }
            catch (UnsupportedMessageException e) {
                assert (false) : AssertUtils.violationPost(receiver, e);
                return true;
            }
            for (long i = 0L; i < arraySize; ++i) {
                Object element;
                assert (uncached.isArrayElementReadable(result, i)) : AssertUtils.violationPost(receiver, result);
                try {
                    element = uncached.readArrayElement(result, i);
                }
                catch (InvalidArrayIndexException | UnsupportedMessageException e) {
                    assert (false) : AssertUtils.violationPost(receiver, result);
                    return true;
                }
                assert (InteropLibrary.getFactory().getUncached().isString(element)) : AssertUtils.violationPost(receiver, element);
                try {
                    InteropLibrary.getFactory().getUncached().asString(element);
                    continue;
                }
                catch (UnsupportedMessageException e) {
                    assert (false) : AssertUtils.violationInvariant(result, i);
                    continue;
                }
            }
            return true;
        }

        private static boolean assertScopeMembers(Object receiver, Object allMembers, Object parentMembers) {
            long parentSize;
            long allSize;
            assert (parentMembers != null) : AssertUtils.violationPost(receiver, parentMembers);
            InteropLibrary allUncached = InteropLibrary.getUncached(allMembers);
            InteropLibrary parentUncached = InteropLibrary.getUncached(parentMembers);
            assert (allUncached.hasArrayElements(allMembers)) : AssertUtils.violationPost(receiver, allMembers);
            assert (parentUncached.hasArrayElements(parentMembers)) : AssertUtils.violationPost(receiver, parentMembers);
            try {
                allSize = allUncached.getArraySize(allMembers);
                parentSize = parentUncached.getArraySize(parentMembers);
            }
            catch (UnsupportedMessageException e) {
                assert (false) : AssertUtils.violationPost(receiver, e);
                return true;
            }
            assert (AssertUtils.validScopeMemberLengths(allSize, parentSize, allMembers, parentMembers));
            long currentSize = allSize - parentSize;
            for (long i = 0L; i < parentSize; ++i) {
                String parentElementName;
                String allElementName;
                Object parentElement;
                Object allElement;
                assert (allUncached.isArrayElementReadable(allMembers, i + currentSize)) : AssertUtils.violationPost(receiver, allMembers);
                assert (parentUncached.isArrayElementReadable(parentMembers, i)) : AssertUtils.violationPost(receiver, parentMembers);
                try {
                    allElement = allUncached.readArrayElement(allMembers, i + currentSize);
                }
                catch (InvalidArrayIndexException | UnsupportedMessageException e) {
                    assert (false) : AssertUtils.violationPost(receiver, allMembers);
                    return true;
                }
                try {
                    parentElement = parentUncached.readArrayElement(parentMembers, i);
                }
                catch (InvalidArrayIndexException | UnsupportedMessageException e) {
                    assert (false) : AssertUtils.violationPost(receiver, parentMembers);
                    return true;
                }
                assert (InteropLibrary.getUncached().isString(allElement)) : AssertUtils.violationPost(receiver, allElement);
                assert (InteropLibrary.getUncached().isString(parentElement)) : AssertUtils.violationPost(receiver, parentElement);
                try {
                    allElementName = InteropLibrary.getUncached().asString(allElement);
                }
                catch (UnsupportedMessageException e) {
                    assert (false) : AssertUtils.violationInvariant(allElement);
                    return true;
                }
                try {
                    parentElementName = InteropLibrary.getUncached().asString(parentElement);
                }
                catch (UnsupportedMessageException e) {
                    assert (false) : AssertUtils.violationInvariant(parentElement);
                    return true;
                }
                assert (AssertUtils.validScopeMemberNames(allElementName, parentElementName, allMembers, parentMembers, i + currentSize, i));
            }
            return true;
        }

        @Override
        public boolean hasMemberReadSideEffects(Object receiver, String identifier) {
            assert (AssertUtils.preCondition(receiver));
            assert (AssertUtils.validProtocolArgument(receiver, identifier));
            boolean result = this.delegate.hasMemberReadSideEffects(receiver, identifier);
            assert (!result || this.delegate.hasMembers(receiver)) : AssertUtils.violationInvariant(receiver, identifier);
            assert (!result || this.delegate.isMemberReadable(receiver, identifier) || Asserts.isMultiThreaded(receiver)) : AssertUtils.violationInvariant(receiver, identifier);
            assert (AssertUtils.validProtocolReturn(receiver, result));
            return result;
        }

        @Override
        public boolean hasMemberWriteSideEffects(Object receiver, String identifier) {
            assert (AssertUtils.preCondition(receiver));
            assert (AssertUtils.validProtocolArgument(receiver, identifier));
            boolean result = this.delegate.hasMemberWriteSideEffects(receiver, identifier);
            assert (!result || this.delegate.hasMembers(receiver)) : AssertUtils.violationInvariant(receiver, identifier);
            assert (!result || this.delegate.isMemberWritable(receiver, identifier) || Asserts.isMultiThreaded(receiver)) : AssertUtils.violationInvariant(receiver, identifier);
            assert (AssertUtils.validProtocolReturn(receiver, result));
            return result;
        }

        @Override
        public boolean isMemberReadable(Object receiver, String identifier) {
            assert (AssertUtils.preCondition(receiver));
            assert (AssertUtils.validProtocolArgument(receiver, identifier));
            boolean result = this.delegate.isMemberReadable(receiver, identifier);
            assert (!result || this.delegate.hasMembers(receiver) && !this.delegate.isMemberInsertable(receiver, identifier)) : AssertUtils.violationInvariant(receiver, identifier);
            assert (AssertUtils.validProtocolReturn(receiver, result));
            return result;
        }

        @Override
        public boolean isMemberModifiable(Object receiver, String identifier) {
            assert (AssertUtils.preCondition(receiver));
            assert (AssertUtils.validInteropArgument(receiver, identifier));
            boolean result = this.delegate.isMemberModifiable(receiver, identifier);
            assert (!result || this.delegate.hasMembers(receiver) && !this.delegate.isMemberInsertable(receiver, identifier)) : AssertUtils.violationInvariant(receiver, identifier);
            assert (AssertUtils.validProtocolReturn(receiver, result));
            return result;
        }

        @Override
        public boolean isMemberInsertable(Object receiver, String identifier) {
            assert (AssertUtils.preCondition(receiver));
            assert (AssertUtils.validProtocolArgument(receiver, identifier));
            boolean result = this.delegate.isMemberInsertable(receiver, identifier);
            assert (!result || this.delegate.hasMembers(receiver) && !this.delegate.isMemberExisting(receiver, identifier)) : AssertUtils.violationInvariant(receiver, identifier);
            assert (AssertUtils.validProtocolReturn(receiver, result));
            return result;
        }

        @Override
        public boolean isMemberRemovable(Object receiver, String identifier) {
            assert (AssertUtils.preCondition(receiver));
            assert (AssertUtils.validProtocolArgument(receiver, identifier));
            boolean result = this.delegate.isMemberRemovable(receiver, identifier);
            assert (!result || this.delegate.hasMembers(receiver) && !this.delegate.isMemberInsertable(receiver, identifier)) : AssertUtils.violationInvariant(receiver, identifier);
            assert (AssertUtils.validProtocolReturn(receiver, result));
            return result;
        }

        @Override
        public boolean isMemberInvocable(Object receiver, String identifier) {
            assert (AssertUtils.preCondition(receiver));
            assert (AssertUtils.validProtocolArgument(receiver, identifier));
            boolean result = this.delegate.isMemberInvocable(receiver, identifier);
            assert (!result || this.delegate.hasMembers(receiver) && !this.delegate.isMemberInsertable(receiver, identifier)) : AssertUtils.violationInvariant(receiver, identifier);
            assert (AssertUtils.validProtocolReturn(receiver, result));
            return result;
        }

        @Override
        public boolean isMemberInternal(Object receiver, String identifier) {
            assert (AssertUtils.preCondition(receiver));
            assert (AssertUtils.validProtocolArgument(receiver, identifier));
            boolean result = this.delegate.isMemberInternal(receiver, identifier);
            assert (!result || this.delegate.hasMembers(receiver)) : AssertUtils.violationInvariant(receiver, identifier);
            assert (AssertUtils.validProtocolReturn(receiver, result));
            return result;
        }

        @Override
        public boolean hasHashEntries(Object receiver) {
            assert (AssertUtils.preCondition(receiver));
            boolean result = this.delegate.hasHashEntries(receiver);
            assert (AssertUtils.validProtocolReturn(receiver, result));
            return result;
        }

        @Override
        public long getHashSize(Object receiver) throws UnsupportedMessageException {
            assert (AssertUtils.preCondition(receiver));
            try {
                long result = this.delegate.getHashSize(receiver);
                assert (this.delegate.hasHashEntries(receiver)) : AssertUtils.violationInvariant(receiver);
                assert (AssertUtils.validProtocolReturn(receiver, result));
                return result;
            }
            catch (InteropException e) {
                assert (e instanceof UnsupportedMessageException) : AssertUtils.violationPost(receiver, e);
                assert (!this.delegate.hasHashEntries(receiver)) : AssertUtils.violationInvariant(receiver);
                throw e;
            }
        }

        @Override
        public boolean isHashEntryReadable(Object receiver, Object key) {
            assert (AssertUtils.preCondition(receiver));
            assert (AssertUtils.validInteropArgument(receiver, key));
            boolean result = this.delegate.isHashEntryReadable(receiver, key);
            assert (!result || this.delegate.hasHashEntries(receiver) && !this.delegate.isHashEntryInsertable(receiver, key)) : AssertUtils.violationInvariant(receiver, key);
            assert (AssertUtils.validProtocolReturn(receiver, result));
            return result;
        }

        @Override
        public Object readHashValue(Object receiver, Object key) throws UnsupportedMessageException, UnknownKeyException {
            if (CompilerDirectives.inCompiledCode()) {
                return this.delegate.readHashValue(receiver, key);
            }
            assert (AssertUtils.preCondition(receiver));
            assert (AssertUtils.validInteropArgument(receiver, key));
            boolean wasReadable = this.delegate.isHashEntryReadable(receiver, key);
            try {
                Object result = this.delegate.readHashValue(receiver, key);
                assert (this.delegate.hasHashEntries(receiver)) : AssertUtils.violationInvariant(receiver, key);
                assert (wasReadable || Asserts.isMultiThreaded(receiver)) : AssertUtils.violationInvariant(receiver, key);
                assert (AssertUtils.validInteropReturn(receiver, result));
                return result;
            }
            catch (InteropException e) {
                assert (e instanceof UnsupportedMessageException || e instanceof UnknownKeyException) : AssertUtils.violationPost(receiver, e);
                assert (!(e instanceof UnsupportedMessageException) || !wasReadable) : AssertUtils.violationInvariant(receiver, key);
                throw e;
            }
        }

        @Override
        public Object readHashValueOrDefault(Object receiver, Object key, Object defaultValue) throws UnsupportedMessageException {
            if (CompilerDirectives.inCompiledCode()) {
                return this.delegate.readHashValueOrDefault(receiver, key, defaultValue);
            }
            assert (AssertUtils.preCondition(receiver));
            assert (AssertUtils.validInteropArgument(receiver, key));
            assert (AssertUtils.validInteropArgument(receiver, defaultValue));
            try {
                Object result = this.delegate.readHashValueOrDefault(receiver, key, defaultValue);
                assert (this.delegate.hasHashEntries(receiver)) : AssertUtils.violationInvariant(receiver, key);
                assert (AssertUtils.validInteropReturn(receiver, result));
                return result;
            }
            catch (InteropException e) {
                assert (e instanceof UnsupportedMessageException) : AssertUtils.violationPost(receiver, e);
                throw e;
            }
        }

        @Override
        public boolean isHashEntryModifiable(Object receiver, Object key) {
            assert (AssertUtils.preCondition(receiver));
            assert (AssertUtils.validInteropArgument(receiver, key));
            boolean result = this.delegate.isHashEntryModifiable(receiver, key);
            assert (!result || this.delegate.hasHashEntries(receiver) && !this.delegate.isHashEntryInsertable(receiver, key)) : AssertUtils.violationInvariant(receiver, key);
            assert (AssertUtils.validProtocolReturn(receiver, result));
            return result;
        }

        @Override
        public boolean isHashEntryInsertable(Object receiver, Object key) {
            assert (AssertUtils.preCondition(receiver));
            assert (AssertUtils.validInteropArgument(receiver, key));
            boolean result = this.delegate.isHashEntryInsertable(receiver, key);
            assert (!result || this.delegate.hasHashEntries(receiver) && !this.delegate.isHashEntryExisting(receiver, key)) : AssertUtils.violationInvariant(receiver, key);
            assert (AssertUtils.validProtocolReturn(receiver, result));
            return result;
        }

        @Override
        public boolean isHashEntryWritable(Object receiver, Object key) {
            assert (AssertUtils.preCondition(receiver));
            assert (AssertUtils.validInteropArgument(receiver, key));
            boolean result = this.delegate.isHashEntryWritable(receiver, key);
            assert (result == (this.delegate.isHashEntryModifiable(receiver, key) || this.delegate.isHashEntryInsertable(receiver, key))) : AssertUtils.violationInvariant(receiver, key);
            assert (AssertUtils.validProtocolReturn(receiver, result));
            return result;
        }

        @Override
        public void writeHashEntry(Object receiver, Object key, Object value2) throws UnsupportedMessageException, UnknownKeyException, UnsupportedTypeException {
            if (CompilerDirectives.inCompiledCode()) {
                this.delegate.writeHashEntry(receiver, key, value2);
                return;
            }
            assert (AssertUtils.preCondition(receiver));
            assert (AssertUtils.validInteropArgument(receiver, key));
            assert (AssertUtils.validInteropArgument(receiver, value2));
            boolean wasWritable = this.delegate.isHashEntryModifiable(receiver, key) || this.delegate.isHashEntryInsertable(receiver, key);
            try {
                this.delegate.writeHashEntry(receiver, key, value2);
                assert (this.delegate.hasHashEntries(receiver)) : AssertUtils.violationInvariant(receiver, key);
                assert (wasWritable || Asserts.isMultiThreaded(receiver)) : AssertUtils.violationInvariant(receiver, key);
            }
            catch (InteropException e) {
                assert (e instanceof UnsupportedMessageException || e instanceof UnknownKeyException || e instanceof UnsupportedTypeException) : AssertUtils.violationPost(receiver, e);
                assert (!(e instanceof UnsupportedMessageException) || !wasWritable) : AssertUtils.violationInvariant(receiver, key);
                throw e;
            }
        }

        @Override
        public boolean isHashEntryRemovable(Object receiver, Object key) {
            assert (AssertUtils.preCondition(receiver));
            assert (AssertUtils.validInteropArgument(receiver, key));
            boolean result = this.delegate.isHashEntryRemovable(receiver, key);
            assert (!result || this.delegate.hasHashEntries(receiver) && !this.delegate.isHashEntryInsertable(receiver, key)) : AssertUtils.violationInvariant(receiver, key);
            assert (AssertUtils.validProtocolReturn(receiver, result));
            return result;
        }

        @Override
        public void removeHashEntry(Object receiver, Object key) throws UnsupportedMessageException, UnknownKeyException {
            if (CompilerDirectives.inCompiledCode()) {
                this.delegate.removeHashEntry(receiver, key);
                return;
            }
            assert (AssertUtils.preCondition(receiver));
            assert (AssertUtils.validInteropArgument(receiver, key));
            boolean wasRemovable = this.delegate.isHashEntryRemovable(receiver, key);
            try {
                this.delegate.removeHashEntry(receiver, key);
                assert (this.delegate.hasHashEntries(receiver)) : AssertUtils.violationInvariant(receiver, key);
                assert (wasRemovable || Asserts.isMultiThreaded(receiver)) : AssertUtils.violationInvariant(receiver, key);
            }
            catch (InteropException e) {
                assert (e instanceof UnsupportedMessageException || e instanceof UnknownKeyException) : AssertUtils.violationPost(receiver, e);
                assert (!(e instanceof UnsupportedMessageException) || !wasRemovable) : AssertUtils.violationInvariant(receiver, key);
                throw e;
            }
        }

        @Override
        public boolean isHashEntryExisting(Object receiver, Object key) {
            assert (AssertUtils.preCondition(receiver));
            assert (AssertUtils.validInteropArgument(receiver, key));
            boolean result = this.delegate.isHashEntryExisting(receiver, key);
            assert (result == (this.delegate.isHashEntryReadable(receiver, key) || this.delegate.isHashEntryModifiable(receiver, key) || this.delegate.isHashEntryRemovable(receiver, key))) : AssertUtils.violationInvariant(receiver, key);
            assert (AssertUtils.validProtocolReturn(receiver, result));
            return result;
        }

        @Override
        public Object getHashEntriesIterator(Object receiver) throws UnsupportedMessageException {
            if (CompilerDirectives.inCompiledCode()) {
                return this.delegate.getHashEntriesIterator(receiver);
            }
            assert (AssertUtils.preCondition(receiver));
            try {
                Object result = this.delegate.getHashEntriesIterator(receiver);
                assert (this.delegate.hasHashEntries(receiver)) : AssertUtils.violationInvariant(receiver);
                assert (Asserts.assertIterator(receiver, result));
                assert (AssertUtils.validInteropReturn(receiver, result));
                return result;
            }
            catch (InteropException e) {
                assert (e instanceof UnsupportedMessageException) : AssertUtils.violationPost(receiver, e);
                assert (!this.delegate.hasHashEntries(receiver)) : AssertUtils.violationInvariant(receiver);
                throw e;
            }
        }

        @Override
        public Object getHashKeysIterator(Object receiver) throws UnsupportedMessageException {
            if (CompilerDirectives.inCompiledCode()) {
                return this.delegate.getHashKeysIterator(receiver);
            }
            assert (AssertUtils.preCondition(receiver));
            try {
                Object result = this.delegate.getHashKeysIterator(receiver);
                assert (this.delegate.hasHashEntries(receiver)) : AssertUtils.violationInvariant(receiver);
                assert (Asserts.assertIterator(receiver, result));
                assert (AssertUtils.validInteropReturn(receiver, result));
                return result;
            }
            catch (InteropException e) {
                assert (e instanceof UnsupportedMessageException) : AssertUtils.violationPost(receiver, e);
                assert (!this.delegate.hasHashEntries(receiver)) : AssertUtils.violationInvariant(receiver);
                throw e;
            }
        }

        @Override
        public Object getHashValuesIterator(Object receiver) throws UnsupportedMessageException {
            if (CompilerDirectives.inCompiledCode()) {
                return this.delegate.getHashValuesIterator(receiver);
            }
            assert (AssertUtils.preCondition(receiver));
            try {
                Object result = this.delegate.getHashValuesIterator(receiver);
                assert (this.delegate.hasHashEntries(receiver)) : AssertUtils.violationInvariant(receiver);
                assert (Asserts.assertIterator(receiver, result));
                assert (AssertUtils.validInteropReturn(receiver, result));
                return result;
            }
            catch (InteropException e) {
                assert (e instanceof UnsupportedMessageException) : AssertUtils.violationPost(receiver, e);
                assert (!this.delegate.hasHashEntries(receiver)) : AssertUtils.violationInvariant(receiver);
                throw e;
            }
        }

        @Override
        public boolean hasArrayElements(Object receiver) {
            assert (AssertUtils.preCondition(receiver));
            return this.delegate.hasArrayElements(receiver);
        }

        @Override
        public Object readArrayElement(Object receiver, long index) throws UnsupportedMessageException, InvalidArrayIndexException {
            if (CompilerDirectives.inCompiledCode()) {
                return this.delegate.readArrayElement(receiver, index);
            }
            assert (AssertUtils.preCondition(receiver));
            assert (AssertUtils.validProtocolArgument(receiver, index));
            boolean wasReadable = this.delegate.isArrayElementReadable(receiver, index);
            try {
                Object result = this.delegate.readArrayElement(receiver, index);
                assert (this.delegate.hasArrayElements(receiver)) : AssertUtils.violationInvariant(receiver, index);
                assert (wasReadable || Asserts.isMultiThreaded(receiver)) : AssertUtils.violationInvariant(receiver, index);
                assert (AssertUtils.validInteropReturn(receiver, result));
                return result;
            }
            catch (InteropException e) {
                assert (e instanceof UnsupportedMessageException || e instanceof InvalidArrayIndexException) : AssertUtils.violationPost(receiver, e);
                throw e;
            }
        }

        @Override
        public void writeArrayElement(Object receiver, long index, Object value2) throws UnsupportedMessageException, UnsupportedTypeException, InvalidArrayIndexException {
            if (CompilerDirectives.inCompiledCode()) {
                this.delegate.writeArrayElement(receiver, index, value2);
                return;
            }
            assert (AssertUtils.preCondition(receiver));
            assert (AssertUtils.validProtocolArgument(receiver, index));
            assert (AssertUtils.validInteropArgument(receiver, value2));
            boolean wasWritable = this.delegate.isArrayElementModifiable(receiver, index) || this.delegate.isArrayElementInsertable(receiver, index);
            try {
                this.delegate.writeArrayElement(receiver, index, value2);
                assert (this.delegate.hasArrayElements(receiver)) : AssertUtils.violationInvariant(receiver, index);
                assert (wasWritable || Asserts.isMultiThreaded(receiver)) : AssertUtils.violationInvariant(receiver, index);
            }
            catch (InteropException e) {
                assert (e instanceof UnsupportedMessageException || e instanceof UnsupportedTypeException || e instanceof InvalidArrayIndexException) : AssertUtils.violationPost(receiver, e);
                throw e;
            }
        }

        @Override
        public void removeArrayElement(Object receiver, long index) throws UnsupportedMessageException, InvalidArrayIndexException {
            if (CompilerDirectives.inCompiledCode()) {
                this.delegate.removeArrayElement(receiver, index);
                return;
            }
            assert (AssertUtils.preCondition(receiver));
            assert (AssertUtils.validProtocolArgument(receiver, index));
            boolean wasRemovable = this.delegate.isArrayElementRemovable(receiver, index);
            try {
                this.delegate.removeArrayElement(receiver, index);
                assert (this.delegate.hasArrayElements(receiver)) : AssertUtils.violationInvariant(receiver, index);
                assert (wasRemovable || Asserts.isMultiThreaded(receiver)) : AssertUtils.violationInvariant(receiver, index);
            }
            catch (InteropException e) {
                assert (e instanceof UnsupportedMessageException || e instanceof InvalidArrayIndexException) : AssertUtils.violationPost(receiver, e);
                throw e;
            }
        }

        @Override
        public long getArraySize(Object receiver) throws UnsupportedMessageException {
            assert (AssertUtils.preCondition(receiver));
            try {
                long result = this.delegate.getArraySize(receiver);
                assert (this.delegate.hasArrayElements(receiver)) : AssertUtils.violationInvariant(receiver);
                assert (AssertUtils.validProtocolReturn(receiver, result));
                return result;
            }
            catch (InteropException e) {
                assert (e instanceof UnsupportedMessageException) : AssertUtils.violationPost(receiver, e);
                throw e;
            }
        }

        @Override
        public boolean isArrayElementReadable(Object receiver, long index) {
            assert (AssertUtils.preCondition(receiver));
            assert (AssertUtils.validProtocolArgument(receiver, index));
            boolean result = this.delegate.isArrayElementReadable(receiver, index);
            assert (!result || this.delegate.hasArrayElements(receiver) && !this.delegate.isArrayElementInsertable(receiver, index)) : AssertUtils.violationInvariant(receiver, index);
            assert (AssertUtils.validProtocolReturn(receiver, result));
            return result;
        }

        @Override
        public boolean isArrayElementModifiable(Object receiver, long index) {
            assert (AssertUtils.preCondition(receiver));
            assert (AssertUtils.validProtocolArgument(receiver, index));
            boolean result = this.delegate.isArrayElementModifiable(receiver, index);
            assert (!result || this.delegate.hasArrayElements(receiver) && !this.delegate.isArrayElementInsertable(receiver, index)) : AssertUtils.violationInvariant(receiver, index);
            assert (AssertUtils.validProtocolReturn(receiver, result));
            return result;
        }

        @Override
        public boolean isArrayElementInsertable(Object receiver, long index) {
            assert (AssertUtils.preCondition(receiver));
            assert (AssertUtils.validProtocolArgument(receiver, index));
            boolean result = this.delegate.isArrayElementInsertable(receiver, index);
            assert (!result || this.delegate.hasArrayElements(receiver) && !this.delegate.isArrayElementExisting(receiver, index)) : AssertUtils.violationInvariant(receiver, index);
            assert (AssertUtils.validProtocolReturn(receiver, result));
            return result;
        }

        @Override
        public boolean isArrayElementRemovable(Object receiver, long index) {
            assert (AssertUtils.preCondition(receiver));
            assert (AssertUtils.validProtocolArgument(receiver, index));
            boolean result = this.delegate.isArrayElementRemovable(receiver, index);
            assert (!result || this.delegate.hasArrayElements(receiver) && !this.delegate.isArrayElementInsertable(receiver, index)) : AssertUtils.violationInvariant(receiver, index);
            assert (AssertUtils.validProtocolReturn(receiver, result));
            return result;
        }

        @Override
        public boolean hasBufferElements(Object receiver) {
            assert (AssertUtils.preCondition(receiver));
            boolean result = this.delegate.hasBufferElements(receiver);
            assert (AssertUtils.validProtocolReturn(receiver, result));
            return result;
        }

        @Override
        public boolean isBufferWritable(Object receiver) throws UnsupportedMessageException {
            assert (AssertUtils.preCondition(receiver));
            try {
                boolean result = this.delegate.isBufferWritable(receiver);
                assert (this.delegate.hasBufferElements(receiver)) : AssertUtils.violationInvariant(receiver);
                assert (AssertUtils.validProtocolReturn(receiver, result));
                return result;
            }
            catch (InteropException e) {
                assert (e instanceof UnsupportedMessageException) : AssertUtils.violationPost(receiver, e);
                throw e;
            }
        }

        @Override
        public long getBufferSize(Object receiver) throws UnsupportedMessageException {
            assert (AssertUtils.preCondition(receiver));
            try {
                long result = this.delegate.getBufferSize(receiver);
                assert (this.delegate.hasBufferElements(receiver)) : AssertUtils.violationInvariant(receiver);
                assert (AssertUtils.validProtocolReturn(receiver, result));
                return result;
            }
            catch (InteropException e) {
                assert (e instanceof UnsupportedMessageException) : AssertUtils.violationPost(receiver, e);
                throw e;
            }
        }

        @Override
        public byte readBufferByte(Object receiver, long byteOffset) throws UnsupportedMessageException, InvalidBufferOffsetException {
            assert (AssertUtils.preCondition(receiver));
            assert (AssertUtils.validProtocolArgument(receiver, byteOffset));
            try {
                byte result = this.delegate.readBufferByte(receiver, byteOffset);
                assert (this.delegate.hasBufferElements(receiver)) : AssertUtils.violationInvariant(receiver, byteOffset);
                assert (AssertUtils.validProtocolReturn(receiver, result));
                return result;
            }
            catch (UnsupportedMessageException e) {
                assert (!this.delegate.hasBufferElements(receiver)) : AssertUtils.violationPost(receiver, e);
                throw e;
            }
            catch (InteropException e) {
                assert (e instanceof InvalidBufferOffsetException) : AssertUtils.violationPost(receiver, e);
                throw e;
            }
        }

        @Override
        public void writeBufferByte(Object receiver, long byteOffset, byte value2) throws UnsupportedMessageException, InvalidBufferOffsetException {
            assert (AssertUtils.preCondition(receiver));
            assert (AssertUtils.validProtocolArgument(receiver, byteOffset));
            assert (AssertUtils.validProtocolArgument(receiver, value2));
            try {
                this.delegate.writeBufferByte(receiver, byteOffset, value2);
                assert (this.delegate.hasBufferElements(receiver)) : AssertUtils.violationInvariant(receiver, byteOffset);
                assert (this.delegate.isBufferWritable(receiver)) : AssertUtils.violationInvariant(receiver, byteOffset);
            }
            catch (UnsupportedMessageException e) {
                assert (!this.delegate.isBufferWritable(receiver)) : AssertUtils.violationPost(receiver, e);
                throw e;
            }
            catch (InteropException e) {
                assert (e instanceof InvalidBufferOffsetException) : AssertUtils.violationPost(receiver, e);
                throw e;
            }
        }

        @Override
        public short readBufferShort(Object receiver, ByteOrder order, long byteOffset) throws UnsupportedMessageException, InvalidBufferOffsetException {
            assert (AssertUtils.preCondition(receiver));
            assert (AssertUtils.validProtocolArgument(receiver, order));
            assert (AssertUtils.validProtocolArgument(receiver, byteOffset));
            try {
                short result = this.delegate.readBufferShort(receiver, order, byteOffset);
                assert (this.delegate.hasBufferElements(receiver)) : AssertUtils.violationInvariant(receiver, byteOffset);
                assert (AssertUtils.validProtocolReturn(receiver, result));
                return result;
            }
            catch (UnsupportedMessageException e) {
                assert (!this.delegate.hasBufferElements(receiver)) : AssertUtils.violationPost(receiver, e);
                throw e;
            }
            catch (InteropException e) {
                assert (e instanceof InvalidBufferOffsetException) : AssertUtils.violationPost(receiver, e);
                throw e;
            }
        }

        @Override
        public void writeBufferShort(Object receiver, ByteOrder order, long byteOffset, short value2) throws UnsupportedMessageException, InvalidBufferOffsetException {
            assert (AssertUtils.preCondition(receiver));
            assert (AssertUtils.validProtocolArgument(receiver, order));
            assert (AssertUtils.validProtocolArgument(receiver, byteOffset));
            assert (AssertUtils.validProtocolArgument(receiver, value2));
            try {
                this.delegate.writeBufferShort(receiver, order, byteOffset, value2);
                assert (this.delegate.hasBufferElements(receiver)) : AssertUtils.violationInvariant(receiver, byteOffset);
                assert (this.delegate.isBufferWritable(receiver)) : AssertUtils.violationInvariant(receiver, byteOffset);
            }
            catch (UnsupportedMessageException e) {
                assert (!this.delegate.isBufferWritable(receiver)) : AssertUtils.violationPost(receiver, e);
                throw e;
            }
            catch (InteropException e) {
                assert (e instanceof InvalidBufferOffsetException) : AssertUtils.violationPost(receiver, e);
                throw e;
            }
        }

        @Override
        public int readBufferInt(Object receiver, ByteOrder order, long byteOffset) throws UnsupportedMessageException, InvalidBufferOffsetException {
            assert (AssertUtils.preCondition(receiver));
            assert (AssertUtils.validProtocolArgument(receiver, order));
            assert (AssertUtils.validProtocolArgument(receiver, byteOffset));
            try {
                int result = this.delegate.readBufferInt(receiver, order, byteOffset);
                assert (this.delegate.hasBufferElements(receiver)) : AssertUtils.violationInvariant(receiver, byteOffset);
                assert (AssertUtils.validProtocolReturn(receiver, result));
                return result;
            }
            catch (UnsupportedMessageException e) {
                assert (!this.delegate.hasBufferElements(receiver)) : AssertUtils.violationPost(receiver, e);
                throw e;
            }
            catch (InteropException e) {
                assert (e instanceof InvalidBufferOffsetException) : AssertUtils.violationPost(receiver, e);
                throw e;
            }
        }

        @Override
        public void writeBufferInt(Object receiver, ByteOrder order, long byteOffset, int value2) throws UnsupportedMessageException, InvalidBufferOffsetException {
            assert (AssertUtils.preCondition(receiver));
            assert (AssertUtils.validProtocolArgument(receiver, order));
            assert (AssertUtils.validProtocolArgument(receiver, byteOffset));
            assert (AssertUtils.validProtocolArgument(receiver, value2));
            try {
                this.delegate.writeBufferInt(receiver, order, byteOffset, value2);
                assert (this.delegate.hasBufferElements(receiver)) : AssertUtils.violationInvariant(receiver, byteOffset);
                assert (this.delegate.isBufferWritable(receiver)) : AssertUtils.violationInvariant(receiver, byteOffset);
            }
            catch (UnsupportedMessageException e) {
                assert (!this.delegate.isBufferWritable(receiver)) : AssertUtils.violationPost(receiver, e);
                throw e;
            }
            catch (InteropException e) {
                assert (e instanceof InvalidBufferOffsetException) : AssertUtils.violationPost(receiver, e);
                throw e;
            }
        }

        @Override
        public long readBufferLong(Object receiver, ByteOrder order, long byteOffset) throws UnsupportedMessageException, InvalidBufferOffsetException {
            assert (AssertUtils.preCondition(receiver));
            assert (AssertUtils.validProtocolArgument(receiver, order));
            assert (AssertUtils.validProtocolArgument(receiver, byteOffset));
            try {
                long result = this.delegate.readBufferLong(receiver, order, byteOffset);
                assert (this.delegate.hasBufferElements(receiver)) : AssertUtils.violationInvariant(receiver, byteOffset);
                assert (AssertUtils.validProtocolReturn(receiver, result));
                return result;
            }
            catch (UnsupportedMessageException e) {
                assert (!this.delegate.hasBufferElements(receiver)) : AssertUtils.violationPost(receiver, e);
                throw e;
            }
            catch (InteropException e) {
                assert (e instanceof InvalidBufferOffsetException) : AssertUtils.violationPost(receiver, e);
                throw e;
            }
        }

        @Override
        public void writeBufferLong(Object receiver, ByteOrder order, long byteOffset, long value2) throws UnsupportedMessageException, InvalidBufferOffsetException {
            assert (AssertUtils.preCondition(receiver));
            assert (AssertUtils.validProtocolArgument(receiver, order));
            assert (AssertUtils.validProtocolArgument(receiver, byteOffset));
            assert (AssertUtils.validProtocolArgument(receiver, value2));
            try {
                this.delegate.writeBufferLong(receiver, order, byteOffset, value2);
                assert (this.delegate.hasBufferElements(receiver)) : AssertUtils.violationInvariant(receiver, byteOffset);
                assert (this.delegate.isBufferWritable(receiver)) : AssertUtils.violationInvariant(receiver, byteOffset);
            }
            catch (UnsupportedMessageException e) {
                assert (!this.delegate.isBufferWritable(receiver)) : AssertUtils.violationPost(receiver, e);
                throw e;
            }
            catch (InteropException e) {
                assert (e instanceof InvalidBufferOffsetException) : AssertUtils.violationPost(receiver, e);
                throw e;
            }
        }

        @Override
        public float readBufferFloat(Object receiver, ByteOrder order, long byteOffset) throws UnsupportedMessageException, InvalidBufferOffsetException {
            assert (AssertUtils.preCondition(receiver));
            assert (AssertUtils.validProtocolArgument(receiver, order));
            assert (AssertUtils.validProtocolArgument(receiver, byteOffset));
            try {
                float result = this.delegate.readBufferFloat(receiver, order, byteOffset);
                assert (this.delegate.hasBufferElements(receiver)) : AssertUtils.violationInvariant(receiver, byteOffset);
                assert (AssertUtils.validProtocolReturn(receiver, Float.valueOf(result)));
                return result;
            }
            catch (UnsupportedMessageException e) {
                assert (!this.delegate.hasBufferElements(receiver)) : AssertUtils.violationPost(receiver, e);
                throw e;
            }
            catch (InteropException e) {
                assert (e instanceof InvalidBufferOffsetException) : AssertUtils.violationPost(receiver, e);
                throw e;
            }
        }

        @Override
        public void writeBufferFloat(Object receiver, ByteOrder order, long byteOffset, float value2) throws UnsupportedMessageException, InvalidBufferOffsetException {
            assert (AssertUtils.preCondition(receiver));
            assert (AssertUtils.validProtocolArgument(receiver, order));
            assert (AssertUtils.validProtocolArgument(receiver, byteOffset));
            assert (AssertUtils.validProtocolArgument(receiver, Float.valueOf(value2)));
            try {
                this.delegate.writeBufferFloat(receiver, order, byteOffset, value2);
                assert (this.delegate.hasBufferElements(receiver)) : AssertUtils.violationInvariant(receiver, byteOffset);
                assert (this.delegate.isBufferWritable(receiver)) : AssertUtils.violationInvariant(receiver, byteOffset);
            }
            catch (UnsupportedMessageException e) {
                assert (!this.delegate.isBufferWritable(receiver)) : AssertUtils.violationPost(receiver, e);
                throw e;
            }
            catch (InteropException e) {
                assert (e instanceof InvalidBufferOffsetException) : AssertUtils.violationPost(receiver, e);
                throw e;
            }
        }

        @Override
        public double readBufferDouble(Object receiver, ByteOrder order, long byteOffset) throws UnsupportedMessageException, InvalidBufferOffsetException {
            assert (AssertUtils.preCondition(receiver));
            assert (AssertUtils.validProtocolArgument(receiver, order));
            assert (AssertUtils.validProtocolArgument(receiver, byteOffset));
            try {
                double result = this.delegate.readBufferDouble(receiver, order, byteOffset);
                assert (this.delegate.hasBufferElements(receiver)) : AssertUtils.violationInvariant(receiver, byteOffset);
                assert (AssertUtils.validProtocolReturn(receiver, result));
                return result;
            }
            catch (UnsupportedMessageException e) {
                assert (!this.delegate.hasBufferElements(receiver)) : AssertUtils.violationPost(receiver, e);
                throw e;
            }
            catch (InteropException e) {
                assert (e instanceof InvalidBufferOffsetException) : AssertUtils.violationPost(receiver, e);
                throw e;
            }
        }

        @Override
        public void writeBufferDouble(Object receiver, ByteOrder order, long byteOffset, double value2) throws UnsupportedMessageException, InvalidBufferOffsetException {
            assert (AssertUtils.preCondition(receiver));
            assert (AssertUtils.validProtocolArgument(receiver, order));
            assert (AssertUtils.validProtocolArgument(receiver, byteOffset));
            assert (AssertUtils.validProtocolArgument(receiver, value2));
            try {
                this.delegate.writeBufferDouble(receiver, order, byteOffset, value2);
                assert (this.delegate.hasBufferElements(receiver)) : AssertUtils.violationInvariant(receiver, byteOffset);
                assert (this.delegate.isBufferWritable(receiver)) : AssertUtils.violationInvariant(receiver, byteOffset);
            }
            catch (UnsupportedMessageException e) {
                assert (!this.delegate.isBufferWritable(receiver)) : AssertUtils.violationPost(receiver, e);
                throw e;
            }
            catch (InteropException e) {
                assert (e instanceof InvalidBufferOffsetException) : AssertUtils.violationPost(receiver, e);
                throw e;
            }
        }

        @Override
        public boolean isPointer(Object receiver) {
            assert (AssertUtils.preCondition(receiver));
            boolean result = this.delegate.isPointer(receiver);
            return result;
        }

        @Override
        public void toNative(Object receiver) {
            assert (AssertUtils.preCondition(receiver));
            boolean wasPointer = this.delegate.isPointer(receiver);
            this.delegate.toNative(receiver);
            assert (!wasPointer || this.delegate.isPointer(receiver)) : AssertUtils.violationInvariant(receiver);
        }

        @Override
        public long asPointer(Object receiver) throws UnsupportedMessageException {
            if (CompilerDirectives.inCompiledCode()) {
                return this.delegate.asPointer(receiver);
            }
            assert (AssertUtils.preCondition(receiver));
            boolean wasPointer = this.delegate.isPointer(receiver);
            try {
                long result = this.delegate.asPointer(receiver);
                assert (wasPointer) : AssertUtils.violationInvariant(receiver);
                assert (AssertUtils.validProtocolReturn(receiver, result));
                return result;
            }
            catch (InteropException e) {
                assert (e instanceof UnsupportedMessageException) : AssertUtils.violationInvariant(receiver);
                assert (!wasPointer) : AssertUtils.violationInvariant(receiver);
                throw e;
            }
        }

        @Override
        public LocalDate asDate(Object receiver) throws UnsupportedMessageException {
            if (CompilerDirectives.inCompiledCode()) {
                return this.delegate.asDate(receiver);
            }
            assert (AssertUtils.preCondition(receiver));
            boolean hasDate = this.delegate.isDate(receiver);
            try {
                LocalDate result = this.delegate.asDate(receiver);
                assert (hasDate) : AssertUtils.violationInvariant(receiver);
                assert (!this.delegate.isTimeZone(receiver) || this.delegate.isTime(receiver)) : AssertUtils.violationInvariant(receiver);
                assert (this.notOtherType(receiver, Type.DATE_TIME_ZONE));
                assert (AssertUtils.validProtocolReturn(receiver, result));
                return result;
            }
            catch (InteropException e) {
                assert (e instanceof UnsupportedMessageException) : AssertUtils.violationInvariant(receiver);
                assert (!hasDate) : AssertUtils.violationInvariant(receiver);
                assert (!this.delegate.isTimeZone(receiver) || !this.delegate.isTime(receiver) || this.hasFixedTimeZone(receiver)) : AssertUtils.violationInvariant(receiver);
                throw e;
            }
        }

        @Override
        public LocalTime asTime(Object receiver) throws UnsupportedMessageException {
            if (CompilerDirectives.inCompiledCode()) {
                return this.delegate.asTime(receiver);
            }
            assert (AssertUtils.preCondition(receiver));
            boolean hasTime = this.delegate.isTime(receiver);
            try {
                LocalTime result = this.delegate.asTime(receiver);
                assert (hasTime) : AssertUtils.violationInvariant(receiver);
                assert (!this.delegate.isTimeZone(receiver) || this.delegate.isDate(receiver) || this.hasFixedTimeZone(receiver)) : AssertUtils.violationInvariant(receiver);
                assert (this.notOtherType(receiver, Type.DATE_TIME_ZONE));
                assert (AssertUtils.validProtocolReturn(receiver, result));
                return result;
            }
            catch (InteropException e) {
                assert (e instanceof UnsupportedMessageException) : AssertUtils.violationInvariant(receiver);
                assert (!hasTime) : AssertUtils.violationInvariant(receiver);
                assert (!this.delegate.isTimeZone(receiver) || !this.delegate.isDate(receiver)) : AssertUtils.violationInvariant(receiver);
                throw e;
            }
        }

        @Override
        public ZoneId asTimeZone(Object receiver) throws UnsupportedMessageException {
            if (CompilerDirectives.inCompiledCode()) {
                return this.delegate.asTimeZone(receiver);
            }
            assert (AssertUtils.preCondition(receiver));
            boolean hasTimeZone = this.delegate.isTimeZone(receiver);
            try {
                ZoneId result = this.delegate.asTimeZone(receiver);
                assert (hasTimeZone) : AssertUtils.violationInvariant(receiver);
                assert ((this.delegate.isDate(receiver) || result.getRules().isFixedOffset()) && this.delegate.isTime(receiver) || !this.delegate.isDate(receiver) && !this.delegate.isTime(receiver)) : AssertUtils.violationInvariant(receiver);
                assert (this.notOtherType(receiver, Type.DATE_TIME_ZONE));
                assert (AssertUtils.validProtocolReturn(receiver, result));
                return result;
            }
            catch (InteropException e) {
                assert (e instanceof UnsupportedMessageException) : AssertUtils.violationInvariant(receiver);
                assert (!hasTimeZone) : AssertUtils.violationInvariant(receiver);
                throw e;
            }
        }

        private boolean hasFixedTimeZone(Object receiver) {
            try {
                return this.delegate.asTimeZone(receiver).getRules().isFixedOffset();
            }
            catch (InteropException e) {
                throw CompilerDirectives.shouldNotReachHere(AssertUtils.violationInvariant(receiver));
            }
        }

        @Override
        public Duration asDuration(Object receiver) throws UnsupportedMessageException {
            if (CompilerDirectives.inCompiledCode()) {
                return this.delegate.asDuration(receiver);
            }
            assert (AssertUtils.preCondition(receiver));
            boolean wasDuration = this.delegate.isDuration(receiver);
            try {
                Duration result = this.delegate.asDuration(receiver);
                assert (wasDuration) : AssertUtils.violationInvariant(receiver);
                assert (this.notOtherType(receiver, Type.DURATION));
                assert (AssertUtils.validProtocolReturn(receiver, result));
                return result;
            }
            catch (InteropException e) {
                assert (e instanceof UnsupportedMessageException) : AssertUtils.violationInvariant(receiver);
                assert (!wasDuration) : AssertUtils.violationInvariant(receiver);
                throw e;
            }
        }

        @Override
        public Instant asInstant(Object receiver) throws UnsupportedMessageException {
            if (CompilerDirectives.inCompiledCode()) {
                return this.delegate.asInstant(receiver);
            }
            assert (AssertUtils.preCondition(receiver));
            boolean hasDateAndTime = this.delegate.isDate(receiver) && this.delegate.isTime(receiver) && this.delegate.isTimeZone(receiver);
            try {
                Instant result = this.delegate.asInstant(receiver);
                assert (hasDateAndTime) : AssertUtils.violationInvariant(receiver);
                assert (ZonedDateTime.of(this.delegate.asDate(receiver), this.delegate.asTime(receiver), this.delegate.asTimeZone(receiver)).toInstant().equals(result)) : AssertUtils.violationInvariant(receiver);
                assert (this.notOtherType(receiver, Type.DATE_TIME_ZONE));
                assert (AssertUtils.validProtocolReturn(receiver, result));
                return result;
            }
            catch (InteropException e) {
                assert (e instanceof UnsupportedMessageException) : AssertUtils.violationInvariant(receiver);
                assert (!hasDateAndTime) : AssertUtils.violationInvariant(receiver);
                throw e;
            }
        }

        @Override
        public boolean isDate(Object receiver) {
            assert (AssertUtils.preCondition(receiver));
            boolean result = this.delegate.isDate(receiver);
            assert (!this.delegate.isTimeZone(receiver) || this.delegate.isTime(receiver) && result || (!this.delegate.isTime(receiver) || this.hasFixedTimeZone(receiver)) && !result) : AssertUtils.violationInvariant(receiver);
            assert (!result || this.notOtherType(receiver, Type.DATE_TIME_ZONE));
            assert (AssertUtils.validProtocolReturn(receiver, result));
            return result;
        }

        @Override
        public boolean isTime(Object receiver) {
            assert (AssertUtils.preCondition(receiver));
            boolean result = this.delegate.isTime(receiver);
            assert (!this.delegate.isTimeZone(receiver) || (this.delegate.isDate(receiver) || this.hasFixedTimeZone(receiver)) && result || !this.delegate.isDate(receiver) && !result) : AssertUtils.violationInvariant(receiver);
            assert (!result || this.notOtherType(receiver, Type.DATE_TIME_ZONE));
            assert (AssertUtils.validProtocolReturn(receiver, result));
            return result;
        }

        @Override
        public boolean isTimeZone(Object receiver) {
            assert (AssertUtils.preCondition(receiver));
            boolean result = this.delegate.isTimeZone(receiver);
            assert (!result || (this.delegate.isDate(receiver) || this.hasFixedTimeZone(receiver)) && this.delegate.isTime(receiver) || !this.delegate.isDate(receiver) && !this.delegate.isTime(receiver)) : AssertUtils.violationInvariant(receiver);
            assert (!result || this.notOtherType(receiver, Type.DATE_TIME_ZONE));
            assert (AssertUtils.validProtocolReturn(receiver, result));
            return result;
        }

        @Override
        public boolean isDuration(Object receiver) {
            assert (AssertUtils.preCondition(receiver));
            boolean result = this.delegate.isDuration(receiver);
            assert (!result || this.notOtherType(receiver, Type.DURATION));
            assert (AssertUtils.validProtocolReturn(receiver, result));
            return result;
        }

        @Override
        public boolean isException(Object receiver) {
            assert (AssertUtils.preCondition(receiver));
            boolean result = this.delegate.isException(receiver);
            assert (AssertUtils.validProtocolReturn(receiver, result));
            return result;
        }

        @Override
        public ExceptionType getExceptionType(Object receiver) throws UnsupportedMessageException {
            assert (AssertUtils.preCondition(receiver));
            ExceptionType result = this.delegate.getExceptionType(receiver);
            assert (AssertUtils.validProtocolReturn(receiver, (Object)result));
            return result;
        }

        @Override
        public boolean isExceptionIncompleteSource(Object receiver) throws UnsupportedMessageException {
            boolean wasParseError;
            if (CompilerDirectives.inCompiledCode()) {
                return this.delegate.isExceptionIncompleteSource(receiver);
            }
            assert (AssertUtils.preCondition(receiver));
            try {
                wasParseError = this.delegate.getExceptionType(receiver) == ExceptionType.PARSE_ERROR;
            }
            catch (UnsupportedMessageException e) {
                wasParseError = false;
            }
            try {
                boolean result = this.delegate.isExceptionIncompleteSource(receiver);
                assert (!result || wasParseError) : AssertUtils.violationInvariant(receiver);
                assert (AssertUtils.validProtocolReturn(receiver, result));
                return result;
            }
            catch (InteropException e) {
                assert (e instanceof UnsupportedMessageException) : AssertUtils.violationInvariant(receiver);
                assert (!wasParseError) : AssertUtils.violationInvariant(receiver);
                throw e;
            }
        }

        @Override
        public int getExceptionExitStatus(Object receiver) throws UnsupportedMessageException {
            boolean wasExit;
            if (CompilerDirectives.inCompiledCode()) {
                return this.delegate.getExceptionExitStatus(receiver);
            }
            assert (AssertUtils.preCondition(receiver));
            try {
                wasExit = this.delegate.getExceptionType(receiver) == ExceptionType.EXIT;
            }
            catch (UnsupportedMessageException e) {
                wasExit = false;
            }
            try {
                int result = this.delegate.getExceptionExitStatus(receiver);
                assert (wasExit) : AssertUtils.violationInvariant(receiver);
                assert (AssertUtils.validProtocolReturn(receiver, result));
                return result;
            }
            catch (InteropException e) {
                assert (e instanceof UnsupportedMessageException) : AssertUtils.violationInvariant(receiver);
                assert (!wasExit) : AssertUtils.violationInvariant(receiver);
                throw e;
            }
        }

        @Override
        public RuntimeException throwException(Object receiver) throws UnsupportedMessageException {
            if (CompilerDirectives.inCompiledCode()) {
                return this.delegate.throwException(receiver);
            }
            assert (AssertUtils.preCondition(receiver));
            boolean wasException = this.delegate.isException(receiver);
            boolean wasAbstractTruffleException = false;
            boolean unsupported = false;
            try {
                try {
                    throw this.delegate.throwException(receiver);
                }
                catch (InteropException e) {
                    assert (e instanceof UnsupportedMessageException) : AssertUtils.violationInvariant(receiver);
                    assert (!wasException) : AssertUtils.violationInvariant(receiver);
                    unsupported = true;
                    throw e;
                }
                catch (Throwable e) {
                    wasAbstractTruffleException = InteropAccessor.EXCEPTION.isException(e);
                    throw e;
                }
            }
            catch (Throwable throwable) {
                if (!unsupported) {
                    assert (wasException) : AssertUtils.violationInvariant(receiver);
                    assert (wasAbstractTruffleException) : AssertUtils.violationInvariant(receiver);
                }
                throw throwable;
            }
        }

        @Override
        public boolean hasExceptionCause(Object receiver) {
            assert (AssertUtils.preCondition(receiver));
            boolean result = this.delegate.hasExceptionCause(receiver);
            assert (AssertUtils.validProtocolReturn(receiver, result));
            return result;
        }

        @Override
        public Object getExceptionCause(Object receiver) throws UnsupportedMessageException {
            if (CompilerDirectives.inCompiledCode()) {
                return this.delegate.getExceptionCause(receiver);
            }
            assert (AssertUtils.preCondition(receiver));
            boolean wasHasExceptionCause = this.delegate.hasExceptionCause(receiver);
            try {
                Object result = this.delegate.getExceptionCause(receiver);
                assert (wasHasExceptionCause) : AssertUtils.violationInvariant(receiver);
                assert (Asserts.assertException(receiver, result));
                assert (AssertUtils.validInteropReturn(receiver, result));
                return result;
            }
            catch (InteropException e) {
                assert (e instanceof UnsupportedMessageException) : AssertUtils.violationInvariant(receiver);
                assert (!wasHasExceptionCause) : AssertUtils.violationInvariant(receiver);
                throw e;
            }
        }

        private static boolean assertException(Object receiver, Object exception) {
            InteropLibrary uncached = InteropLibrary.getUncached(exception);
            assert (uncached.isException(exception)) : AssertUtils.violationPost(receiver, exception);
            return true;
        }

        @Override
        public boolean hasExceptionMessage(Object receiver) {
            assert (AssertUtils.preCondition(receiver));
            boolean result = this.delegate.hasExceptionMessage(receiver);
            assert (AssertUtils.validProtocolReturn(receiver, result));
            return result;
        }

        @Override
        public Object getExceptionMessage(Object receiver) throws UnsupportedMessageException {
            if (CompilerDirectives.inCompiledCode()) {
                return this.delegate.getExceptionMessage(receiver);
            }
            assert (AssertUtils.preCondition(receiver));
            boolean wasHasExceptionMessage = this.delegate.hasExceptionMessage(receiver);
            try {
                Object result = this.delegate.getExceptionMessage(receiver);
                assert (wasHasExceptionMessage) : AssertUtils.violationInvariant(receiver);
                assert (AssertUtils.assertString(receiver, result));
                assert (AssertUtils.validInteropReturn(receiver, result));
                return result;
            }
            catch (InteropException e) {
                assert (e instanceof UnsupportedMessageException) : AssertUtils.violationInvariant(receiver);
                assert (!wasHasExceptionMessage) : AssertUtils.violationInvariant(receiver);
                throw e;
            }
        }

        @Override
        public boolean hasExceptionStackTrace(Object receiver) {
            assert (AssertUtils.preCondition(receiver));
            boolean result = this.delegate.hasExceptionStackTrace(receiver);
            assert (AssertUtils.validProtocolReturn(receiver, result));
            return result;
        }

        @Override
        public Object getExceptionStackTrace(Object receiver) throws UnsupportedMessageException {
            if (CompilerDirectives.inCompiledCode()) {
                return this.delegate.getExceptionStackTrace(receiver);
            }
            assert (AssertUtils.preCondition(receiver));
            boolean wasHasExceptionStackTrace = this.delegate.hasExceptionStackTrace(receiver);
            try {
                Object result = this.delegate.getExceptionStackTrace(receiver);
                assert (wasHasExceptionStackTrace) : AssertUtils.violationInvariant(receiver);
                assert (Asserts.verifyStackTrace(receiver, result));
                assert (AssertUtils.validInteropReturn(receiver, result));
                return result;
            }
            catch (InteropException e) {
                assert (e instanceof UnsupportedMessageException) : AssertUtils.violationInvariant(receiver);
                assert (!wasHasExceptionStackTrace) : AssertUtils.violationInvariant(receiver);
                throw e;
            }
        }

        private static boolean verifyStackTrace(Object receiver, Object stackTrace) {
            assert (stackTrace != null) : AssertUtils.violationPost(receiver, stackTrace);
            InteropLibrary stackTraceLib = InteropLibrary.getFactory().getUncached(stackTrace);
            assert (stackTraceLib.hasArrayElements(stackTrace)) : AssertUtils.violationPost(receiver, stackTrace);
            return true;
        }

        @Override
        public boolean hasExecutableName(Object receiver) {
            assert (AssertUtils.preCondition(receiver));
            boolean result = this.delegate.hasExecutableName(receiver);
            assert (AssertUtils.validProtocolReturn(receiver, result));
            return result;
        }

        @Override
        public Object getExecutableName(Object receiver) throws UnsupportedMessageException {
            if (CompilerDirectives.inCompiledCode()) {
                return this.delegate.getExecutableName(receiver);
            }
            assert (AssertUtils.preCondition(receiver));
            boolean wasHasExecutableName = this.delegate.hasExecutableName(receiver);
            try {
                Object result = this.delegate.getExecutableName(receiver);
                assert (wasHasExecutableName) : AssertUtils.violationInvariant(receiver);
                assert (AssertUtils.assertString(receiver, result));
                assert (AssertUtils.validInteropReturn(receiver, result));
                return result;
            }
            catch (InteropException e) {
                assert (e instanceof UnsupportedMessageException) : AssertUtils.violationInvariant(receiver);
                assert (!wasHasExecutableName) : AssertUtils.violationInvariant(receiver);
                throw e;
            }
        }

        @Override
        public boolean hasDeclaringMetaObject(Object receiver) {
            assert (AssertUtils.preCondition(receiver));
            boolean result = this.delegate.hasDeclaringMetaObject(receiver);
            return result;
        }

        @Override
        public Object getDeclaringMetaObject(Object receiver) throws UnsupportedMessageException {
            if (CompilerDirectives.inCompiledCode()) {
                return this.delegate.getDeclaringMetaObject(receiver);
            }
            assert (AssertUtils.preCondition(receiver));
            boolean wasHasDeclaringMetaObject = this.delegate.hasDeclaringMetaObject(receiver);
            try {
                Object result = this.delegate.getDeclaringMetaObject(receiver);
                assert (wasHasDeclaringMetaObject) : AssertUtils.violationInvariant(receiver);
                assert (Asserts.verifyDeclaringMetaObject(receiver, result));
                assert (AssertUtils.validInteropReturn(receiver, result));
                return result;
            }
            catch (InteropException e) {
                assert (e instanceof UnsupportedMessageException) : AssertUtils.violationInvariant(receiver);
                assert (!wasHasDeclaringMetaObject) : AssertUtils.violationInvariant(receiver);
                throw e;
            }
        }

        private static boolean verifyDeclaringMetaObject(Object receiver, Object meta) {
            block6: {
                assert (meta != null) : AssertUtils.violationPost(receiver, meta);
                InteropLibrary metaLib = InteropLibrary.getFactory().getUncached(meta);
                assert (metaLib.isMetaObject(meta)) : AssertUtils.violationPost(receiver, meta);
                try {
                    assert (metaLib.getMetaSimpleName(meta) != null) : AssertUtils.violationPost(receiver, meta);
                    assert (metaLib.getMetaQualifiedName(meta) != null) : AssertUtils.violationPost(receiver, meta);
                }
                catch (UnsupportedMessageException e) {
                    if ($assertionsDisabled) break block6;
                    throw new AssertionError((Object)AssertUtils.violationPost(receiver, meta));
                }
            }
            return true;
        }

        @Override
        public Object toDisplayString(Object receiver, boolean allowSideEffects) {
            assert (AssertUtils.preCondition(receiver));
            assert (AssertUtils.validNonInteropArgument(receiver, allowSideEffects));
            Object result = this.delegate.toDisplayString(receiver, allowSideEffects);
            assert (AssertUtils.assertString(receiver, result));
            assert (AssertUtils.validInteropReturn(receiver, result));
            return result;
        }

        @Override
        public boolean hasIterator(Object receiver) {
            assert (AssertUtils.preCondition(receiver));
            boolean result = this.delegate.hasIterator(receiver);
            assert (AssertUtils.validProtocolReturn(receiver, result));
            return result;
        }

        @Override
        public Object getIterator(Object receiver) throws UnsupportedMessageException {
            if (CompilerDirectives.inCompiledCode()) {
                return this.delegate.getIterator(receiver);
            }
            assert (AssertUtils.preCondition(receiver));
            boolean wasHasIterator = this.delegate.hasIterator(receiver);
            try {
                Object result = this.delegate.getIterator(receiver);
                assert (wasHasIterator) : AssertUtils.violationInvariant(receiver);
                assert (Asserts.assertIterator(receiver, result));
                assert (AssertUtils.validProtocolReturn(receiver, result));
                return result;
            }
            catch (InteropException e) {
                assert (e instanceof UnsupportedMessageException) : AssertUtils.violationInvariant(receiver);
                assert (!wasHasIterator) : AssertUtils.violationInvariant(receiver);
                throw e;
            }
        }

        private static boolean assertIterator(Object receiver, Object iterator) {
            assert (iterator != null) : AssertUtils.violationPost(receiver, iterator);
            InteropLibrary uncached = InteropLibrary.getUncached(iterator);
            assert (uncached.isIterator(iterator)) : AssertUtils.violationPost(receiver, iterator);
            return true;
        }

        @Override
        public boolean isIterator(Object receiver) {
            assert (AssertUtils.preCondition(receiver));
            boolean result = this.delegate.isIterator(receiver);
            assert (!result || this.notOtherType(receiver, Type.ITERATOR));
            assert (AssertUtils.validProtocolReturn(receiver, result));
            return result;
        }

        @Override
        public boolean hasIteratorNextElement(Object receiver) throws UnsupportedMessageException {
            if (CompilerDirectives.inCompiledCode()) {
                return this.delegate.hasIteratorNextElement(receiver);
            }
            assert (AssertUtils.preCondition(receiver));
            boolean wasIterator = this.delegate.isIterator(receiver);
            try {
                boolean result = this.delegate.hasIteratorNextElement(receiver);
                assert (wasIterator) : AssertUtils.violationInvariant(receiver);
                assert (AssertUtils.validProtocolReturn(receiver, result));
                return result;
            }
            catch (InteropException e) {
                assert (e instanceof UnsupportedMessageException) : AssertUtils.violationInvariant(receiver);
                assert (!wasIterator) : AssertUtils.violationInvariant(receiver);
                throw e;
            }
        }

        @Override
        public Object getIteratorNextElement(Object receiver) throws UnsupportedMessageException, StopIterationException {
            if (CompilerDirectives.inCompiledCode()) {
                return this.delegate.getIteratorNextElement(receiver);
            }
            assert (AssertUtils.preCondition(receiver));
            boolean wasIterator = this.delegate.isIterator(receiver);
            try {
                Object result = this.delegate.getIteratorNextElement(receiver);
                assert (wasIterator) : AssertUtils.violationInvariant(receiver);
                assert (AssertUtils.validInteropReturn(receiver, result));
                return result;
            }
            catch (InteropException e) {
                assert (e instanceof UnsupportedMessageException || e instanceof StopIterationException) : AssertUtils.violationPost(receiver, e);
                throw e;
            }
        }

        @Override
        public boolean hasSourceLocation(Object receiver) {
            if (CompilerDirectives.inCompiledCode()) {
                return this.delegate.hasSourceLocation(receiver);
            }
            assert (AssertUtils.preCondition(receiver));
            boolean result = this.delegate.hasSourceLocation(receiver);
            if (result) {
                try {
                    assert (this.delegate.getSourceLocation(receiver) != null) : AssertUtils.violationPost(receiver, result);
                }
                catch (InteropException e) {
                    assert (false) : AssertUtils.violationInvariant(receiver);
                }
                catch (Exception exception) {}
            } else assert (this.assertHasNoSourceSection(receiver));
            assert (AssertUtils.validProtocolReturn(receiver, result));
            return result;
        }

        private boolean assertHasNoSourceSection(Object receiver) {
            try {
                this.delegate.getSourceLocation(receiver);
                assert (false) : AssertUtils.violationInvariant(receiver);
            }
            catch (UnsupportedMessageException unsupportedMessageException) {
                // empty catch block
            }
            return true;
        }

        @Override
        public SourceSection getSourceLocation(Object receiver) throws UnsupportedMessageException {
            if (CompilerDirectives.inCompiledCode()) {
                return this.delegate.getSourceLocation(receiver);
            }
            assert (AssertUtils.preCondition(receiver));
            boolean wasHasSourceLocation = this.delegate.hasSourceLocation(receiver);
            try {
                SourceSection result = this.delegate.getSourceLocation(receiver);
                assert (wasHasSourceLocation) : AssertUtils.violationInvariant(receiver);
                assert (result != null) : AssertUtils.violationPost(receiver, result);
                assert (AssertUtils.validProtocolReturn(receiver, result));
                return result;
            }
            catch (InteropException e) {
                assert (e instanceof UnsupportedMessageException) : AssertUtils.violationInvariant(receiver);
                assert (!wasHasSourceLocation) : AssertUtils.violationInvariant(receiver);
                throw e;
            }
        }

        @Override
        public boolean hasLanguage(Object receiver) {
            assert (AssertUtils.preCondition(receiver));
            boolean result = this.delegate.hasLanguage(receiver);
            if (result) {
                try {
                    assert (this.delegate.getLanguage(receiver) != null) : AssertUtils.violationPost(receiver, result);
                }
                catch (InteropException e) {
                    assert (false) : AssertUtils.violationInvariant(receiver);
                }
                catch (Exception exception) {}
            } else assert (this.assertHasNoLanguage(receiver));
            assert (AssertUtils.validProtocolReturn(receiver, result));
            return result;
        }

        private boolean assertHasNoLanguage(Object receiver) {
            try {
                this.delegate.getLanguage(receiver);
                assert (false) : AssertUtils.violationInvariant(receiver);
            }
            catch (UnsupportedMessageException unsupportedMessageException) {
                // empty catch block
            }
            return true;
        }

        @Override
        public Class<? extends TruffleLanguage<?>> getLanguage(Object receiver) throws UnsupportedMessageException {
            if (CompilerDirectives.inCompiledCode()) {
                return this.delegate.getLanguage(receiver);
            }
            assert (AssertUtils.preCondition(receiver));
            boolean wasHasLanguage = this.delegate.hasLanguage(receiver);
            try {
                Class<? extends TruffleLanguage<?>> result = this.delegate.getLanguage(receiver);
                assert (wasHasLanguage) : AssertUtils.violationInvariant(receiver);
                assert (AssertUtils.validProtocolReturn(receiver, result));
                return result;
            }
            catch (InteropException e) {
                assert (e instanceof UnsupportedMessageException) : AssertUtils.violationInvariant(receiver);
                assert (!wasHasLanguage) : AssertUtils.violationInvariant(receiver);
                throw e;
            }
        }

        @Override
        public boolean hasMetaObject(Object receiver) {
            assert (AssertUtils.preCondition(receiver));
            boolean result = this.delegate.hasMetaObject(receiver);
            if (result ? !$assertionsDisabled && !this.assertHasMetaObject(receiver, result) : !$assertionsDisabled && !this.assertHasNoMetaObject(receiver)) {
                throw new AssertionError();
            }
            assert (AssertUtils.validProtocolReturn(receiver, result));
            return result;
        }

        private boolean assertHasMetaObject(Object receiver, boolean result) {
            try {
                Object meta = this.delegate.getMetaObject(receiver);
                assert (Asserts.verifyMetaObject(receiver, meta));
            }
            catch (InteropException e) {
                assert (false) : AssertUtils.violationInvariant(receiver);
            }
            catch (Exception exception) {
                // empty catch block
            }
            return true;
        }

        private static boolean verifyMetaObject(Object receiver, Object meta) throws UnsupportedMessageException {
            assert (meta != null) : AssertUtils.violationPost(receiver, meta);
            InteropLibrary metaLib = InteropLibrary.getFactory().getUncached(meta);
            assert (metaLib.isMetaObject(meta)) : AssertUtils.violationPost(receiver, meta);
            assert (metaLib.getMetaSimpleName(meta) != null) : AssertUtils.violationPost(receiver, meta);
            assert (metaLib.getMetaQualifiedName(meta) != null) : AssertUtils.violationPost(receiver, meta);
            return true;
        }

        private boolean assertHasNoMetaObject(Object receiver) {
            try {
                this.delegate.getMetaObject(receiver);
                assert (false) : AssertUtils.violationInvariant(receiver);
            }
            catch (UnsupportedMessageException unsupportedMessageException) {
                // empty catch block
            }
            return true;
        }

        @Override
        public Object getMetaObject(Object receiver) throws UnsupportedMessageException {
            if (CompilerDirectives.inCompiledCode()) {
                return this.delegate.getMetaObject(receiver);
            }
            assert (AssertUtils.preCondition(receiver));
            boolean wasHasMetaObject = this.delegate.hasMetaObject(receiver);
            try {
                Object result = this.delegate.getMetaObject(receiver);
                assert (wasHasMetaObject) : AssertUtils.violationInvariant(receiver);
                assert (Asserts.verifyMetaObject(receiver, result));
                assert (AssertUtils.validInteropReturn(receiver, result));
                return result;
            }
            catch (InteropException e) {
                assert (e instanceof UnsupportedMessageException) : AssertUtils.violationInvariant(receiver);
                assert (!wasHasMetaObject) : AssertUtils.violationInvariant(receiver);
                throw e;
            }
        }

        @Override
        public boolean isMetaObject(Object receiver) {
            assert (AssertUtils.preCondition(receiver));
            boolean result = this.delegate.isMetaObject(receiver);
            if (result) {
                assert (this.assertMetaObject(receiver));
            } else {
                assert (this.assertNoMetaObject(receiver));
                assert (!result || this.notOtherType(receiver, Type.META_OBJECT));
            }
            assert (AssertUtils.validProtocolReturn(receiver, result));
            return result;
        }

        private boolean assertNoMetaObject(Object receiver) {
            try {
                this.delegate.isMetaInstance(receiver, receiver);
                assert (false) : AssertUtils.violationInvariant(receiver);
            }
            catch (UnsupportedMessageException unsupportedMessageException) {
                // empty catch block
            }
            try {
                this.delegate.getMetaSimpleName(receiver);
                assert (false) : AssertUtils.violationInvariant(receiver);
            }
            catch (UnsupportedMessageException unsupportedMessageException) {
                // empty catch block
            }
            try {
                this.delegate.getMetaQualifiedName(receiver);
                assert (false) : AssertUtils.violationInvariant(receiver);
            }
            catch (UnsupportedMessageException unsupportedMessageException) {
                // empty catch block
            }
            try {
                this.delegate.getMetaParents(receiver);
                assert (false) : AssertUtils.violationInvariant(receiver);
            }
            catch (UnsupportedMessageException unsupportedMessageException) {
                // empty catch block
            }
            return true;
        }

        private boolean assertMetaObject(Object receiver) {
            block10: {
                block9: {
                    block8: {
                        try {
                            this.delegate.isMetaInstance(receiver, receiver);
                        }
                        catch (UnsupportedMessageException e) {
                            if ($assertionsDisabled) break block8;
                            throw new AssertionError((Object)AssertUtils.violationInvariant(receiver));
                        }
                    }
                    try {
                        assert (AssertUtils.assertString(receiver, this.delegate.getMetaSimpleName(receiver))) : AssertUtils.violationInvariant(receiver);
                    }
                    catch (UnsupportedMessageException e) {
                        if ($assertionsDisabled) break block9;
                        throw new AssertionError((Object)AssertUtils.violationInvariant(receiver));
                    }
                }
                try {
                    assert (AssertUtils.assertString(receiver, this.delegate.getMetaQualifiedName(receiver))) : AssertUtils.violationInvariant(receiver);
                }
                catch (UnsupportedMessageException e) {
                    if ($assertionsDisabled) break block10;
                    throw new AssertionError((Object)AssertUtils.violationInvariant(receiver));
                }
            }
            return true;
        }

        @Override
        public Object getMetaQualifiedName(Object receiver) throws UnsupportedMessageException {
            if (CompilerDirectives.inCompiledCode()) {
                return this.delegate.getMetaQualifiedName(receiver);
            }
            assert (AssertUtils.preCondition(receiver));
            boolean wasMetaObject = this.delegate.isMetaObject(receiver);
            try {
                Object result = this.delegate.getMetaQualifiedName(receiver);
                assert (wasMetaObject) : AssertUtils.violationInvariant(receiver);
                assert (AssertUtils.assertString(receiver, result));
                assert (AssertUtils.validInteropReturn(receiver, result));
                return result;
            }
            catch (InteropException e) {
                assert (e instanceof UnsupportedMessageException) : AssertUtils.violationInvariant(receiver);
                assert (!wasMetaObject) : AssertUtils.violationInvariant(receiver);
                throw e;
            }
        }

        @Override
        public Object getMetaSimpleName(Object receiver) throws UnsupportedMessageException {
            if (CompilerDirectives.inCompiledCode()) {
                return this.delegate.getMetaSimpleName(receiver);
            }
            assert (AssertUtils.preCondition(receiver));
            boolean wasMetaObject = this.delegate.isMetaObject(receiver);
            try {
                Object result = this.delegate.getMetaSimpleName(receiver);
                assert (wasMetaObject) : AssertUtils.violationInvariant(receiver);
                assert (AssertUtils.assertString(receiver, result));
                assert (AssertUtils.validInteropReturn(receiver, result));
                return result;
            }
            catch (InteropException e) {
                assert (e instanceof UnsupportedMessageException) : AssertUtils.violationInvariant(receiver);
                assert (!wasMetaObject) : AssertUtils.violationInvariant(receiver);
                throw e;
            }
        }

        @Override
        public boolean isMetaInstance(Object receiver, Object instance) throws UnsupportedMessageException {
            if (CompilerDirectives.inCompiledCode()) {
                return this.delegate.isMetaInstance(receiver, instance);
            }
            assert (AssertUtils.preCondition(receiver));
            assert (AssertUtils.validInteropArgument(receiver, instance));
            boolean wasMetaObject = this.delegate.isMetaObject(receiver);
            try {
                boolean result = this.delegate.isMetaInstance(receiver, instance);
                assert (wasMetaObject) : AssertUtils.violationInvariant(receiver);
                assert (AssertUtils.validProtocolReturn(receiver, result));
                return result;
            }
            catch (InteropException e) {
                assert (e instanceof UnsupportedMessageException) : AssertUtils.violationInvariant(receiver);
                assert (!wasMetaObject) : AssertUtils.violationInvariant(receiver);
                throw e;
            }
        }

        @Override
        public boolean hasMetaParents(Object receiver) {
            assert (AssertUtils.preCondition(receiver));
            boolean wasMetaObject = this.delegate.isMetaObject(receiver);
            boolean result = this.delegate.hasMetaParents(receiver);
            if (result) {
                assert (wasMetaObject) : AssertUtils.violationInvariant(receiver);
            } else if (!wasMetaObject) assert (this.assertNoMetaObject(receiver));
            assert (AssertUtils.validProtocolReturn(receiver, result));
            return result;
        }

        @Override
        public Object getMetaParents(Object receiver) throws UnsupportedMessageException {
            if (CompilerDirectives.inCompiledCode()) {
                return this.delegate.getMetaParents(receiver);
            }
            boolean wasMetaObject = this.delegate.isMetaObject(receiver);
            boolean hadMetaParents = this.delegate.hasMetaParents(receiver);
            try {
                Object result = this.delegate.getMetaParents(receiver);
                assert (wasMetaObject) : AssertUtils.violationInvariant(receiver);
                assert (hadMetaParents) : AssertUtils.violationInvariant(receiver);
                assert (AssertUtils.validInteropReturn(receiver, result));
                return result;
            }
            catch (InteropException e) {
                assert (e instanceof UnsupportedMessageException) : AssertUtils.violationInvariant(receiver);
                assert (!hadMetaParents) : AssertUtils.violationInvariant(receiver);
                throw e;
            }
        }

        @Override
        protected TriState isIdenticalOrUndefined(Object receiver, Object other) {
            assert (AssertUtils.preCondition(receiver));
            assert (AssertUtils.validInteropArgument(receiver, other));
            TriState result = this.delegate.isIdenticalOrUndefined(receiver, other);
            assert (Asserts.verifyIsSameOrUndefined(this.delegate, result, receiver, other));
            assert (AssertUtils.validProtocolReturn(receiver, (Object)result));
            return result;
        }

        static boolean verifyIsSameOrUndefined(InteropLibrary library, TriState result, Object receiver, Object other) {
            if (result != TriState.UNDEFINED) {
                int hashCode = 0;
                try {
                    hashCode = library.identityHashCode(receiver);
                }
                catch (Exception t) {
                    throw CompilerDirectives.shouldNotReachHere(t);
                }
            }
            return true;
        }

        @Override
        public int identityHashCode(Object receiver) throws UnsupportedMessageException {
            int result;
            assert (AssertUtils.preCondition(receiver));
            try {
                result = this.delegate.identityHashCode(receiver);
                assert (this.delegate.hasIdentity(receiver)) : AssertUtils.violationInvariant(receiver);
            }
            catch (UnsupportedMessageException e) {
                assert (!this.delegate.hasIdentity(receiver)) : AssertUtils.violationInvariant(receiver);
                throw e;
            }
            assert (AssertUtils.validProtocolReturn(receiver, result));
            return result;
        }

        @Override
        public boolean isIdentical(Object receiver, Object other, InteropLibrary otherInterop) {
            assert (AssertUtils.preCondition(receiver));
            assert (AssertUtils.validInteropArgument(receiver, other));
            assert (AssertUtils.validProtocolArgument(receiver, otherInterop));
            boolean result = this.delegate.isIdentical(receiver, other, otherInterop);
            assert (this.verifyIsSame(result, receiver, other, otherInterop));
            assert (AssertUtils.validProtocolReturn(receiver, result));
            return result;
        }

        boolean verifyIsSame(boolean result, Object receiver, Object other, InteropLibrary otherInterop) {
            try {
                InteropLibrary otherDelegate = otherInterop;
                if (otherInterop instanceof Asserts) {
                    otherDelegate = ((Asserts)otherInterop).delegate;
                }
                assert (result == otherDelegate.isIdentical(other, receiver, this.delegate)) : AssertUtils.violationInvariant(receiver);
                if (result) assert (this.delegate.identityHashCode(receiver) == otherDelegate.identityHashCode(other)) : AssertUtils.violationInvariant(receiver);
                TriState state = this.delegate.isIdenticalOrUndefined(receiver, other);
                if (state != TriState.UNDEFINED) assert (this.delegate.isIdentical(receiver, receiver, this.delegate)) : AssertUtils.violationInvariant(receiver);
                Asserts.verifyIsSameOrUndefined(this.delegate, state, receiver, other);
                Asserts.verifyIsSameOrUndefined(otherDelegate, otherDelegate.isIdenticalOrUndefined(other, receiver), other, receiver);
            }
            catch (UnsupportedMessageException e) {
                throw CompilerDirectives.shouldNotReachHere(e);
            }
            return true;
        }

        @Override
        public boolean isScope(Object receiver) {
            assert (AssertUtils.preCondition(receiver));
            boolean result = this.delegate.isScope(receiver);
            assert (!result || this.delegate.hasMembers(receiver)) : AssertUtils.violationInvariant(receiver);
            assert (!result || this.delegate.hasLanguage(receiver)) : AssertUtils.violationInvariant(receiver);
            assert (AssertUtils.validProtocolReturn(receiver, result));
            return result;
        }

        @Override
        public boolean hasScopeParent(Object receiver) {
            boolean result;
            block11: {
                if (CompilerDirectives.inCompiledCode()) {
                    return this.delegate.hasScopeParent(receiver);
                }
                assert (AssertUtils.preCondition(receiver));
                result = this.delegate.hasScopeParent(receiver);
                if (result) {
                    assert (this.delegate.isScope(receiver)) : AssertUtils.violationInvariant(receiver);
                    try {
                        assert (AssertUtils.validScope(this.delegate.getScopeParent(receiver)));
                        break block11;
                    }
                    catch (UnsupportedMessageException e) {
                        assert (false) : AssertUtils.violationInvariant(receiver);
                        break block11;
                    }
                }
                try {
                    this.delegate.getScopeParent(receiver);
                    assert (false) : AssertUtils.violationInvariant(receiver);
                }
                catch (UnsupportedMessageException unsupportedMessageException) {
                    // empty catch block
                }
            }
            return result;
        }

        @Override
        public Object getScopeParent(Object receiver) throws UnsupportedMessageException {
            if (CompilerDirectives.inCompiledCode()) {
                return this.delegate.getScopeParent(receiver);
            }
            assert (AssertUtils.preCondition(receiver));
            boolean hadScopeParent = this.delegate.hasScopeParent(receiver);
            try {
                Object result = this.delegate.getScopeParent(receiver);
                assert (hadScopeParent) : AssertUtils.violationInvariant(receiver);
                assert (this.delegate.isScope(receiver)) : AssertUtils.violationInvariant(receiver);
                assert (AssertUtils.validScope(result));
                assert (AssertUtils.validInteropReturn(receiver, result));
                return result;
            }
            catch (InteropException e) {
                assert (e instanceof UnsupportedMessageException) : AssertUtils.violationInvariant(receiver);
                assert (!hadScopeParent) : AssertUtils.violationInvariant(receiver);
                throw e;
            }
        }

        public static enum Type {
            NULL,
            BOOLEAN,
            DATE_TIME_ZONE,
            DURATION,
            STRING,
            NUMBER,
            POINTER,
            META_OBJECT,
            ITERATOR;

        }
    }
}

