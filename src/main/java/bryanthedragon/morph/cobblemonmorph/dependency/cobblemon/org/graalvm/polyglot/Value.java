
package org.graalvm.polyglot;

import java.nio.ByteOrder;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Objects;
import java.util.Set;
import org.graalvm.polyglot.AbstractValue;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Engine;
import org.graalvm.polyglot.PolyglotException;
import org.graalvm.polyglot.SourceSection;
import org.graalvm.polyglot.TypeLiteral;
import org.graalvm.polyglot.impl.AbstractPolyglotImpl;
import org.graalvm.polyglot.proxy.Proxy;

public final class Value
extends AbstractValue {
    Value(AbstractPolyglotImpl.AbstractValueDispatch dispatch, Object context, Object receiver) {
        super(dispatch, context, receiver);
    }

    public Value getMetaObject() {
        return this.dispatch.getMetaObject(this.context, this.receiver);
    }

    public boolean isMetaObject() {
        return this.dispatch.isMetaObject(this.context, this.receiver);
    }

    public String getMetaQualifiedName() {
        return this.dispatch.getMetaQualifiedName(this.context, this.receiver);
    }

    public String getMetaSimpleName() {
        return this.dispatch.getMetaSimpleName(this.context, this.receiver);
    }

    public boolean isMetaInstance(Object instance) {
        return this.dispatch.isMetaInstance(this.context, this.receiver, instance);
    }

    public boolean hasMetaParents() {
        return this.dispatch.hasMetaParents(this.context, this.receiver);
    }

    public Value getMetaParents() {
        return this.dispatch.getMetaParents(this.context, this.receiver);
    }

    public boolean hasArrayElements() {
        return this.dispatch.hasArrayElements(this.context, this.receiver);
    }

    public Value getArrayElement(long index) {
        return this.dispatch.getArrayElement(this.context, this.receiver, index);
    }

    public void setArrayElement(long index, Object value2) {
        this.dispatch.setArrayElement(this.context, this.receiver, index, value2);
    }

    public boolean removeArrayElement(long index) {
        return this.dispatch.removeArrayElement(this.context, this.receiver, index);
    }

    public long getArraySize() {
        return this.dispatch.getArraySize(this.context, this.receiver);
    }

    public boolean hasBufferElements() {
        return this.dispatch.hasBufferElements(this.context, this.receiver);
    }

    public boolean isBufferWritable() throws UnsupportedOperationException {
        return this.dispatch.isBufferWritable(this.context, this.receiver);
    }

    public long getBufferSize() throws UnsupportedOperationException {
        return this.dispatch.getBufferSize(this.context, this.receiver);
    }

    public byte readBufferByte(long byteOffset) throws UnsupportedOperationException, IndexOutOfBoundsException {
        return this.dispatch.readBufferByte(this.context, this.receiver, byteOffset);
    }

    public void writeBufferByte(long byteOffset, byte value2) throws UnsupportedOperationException, IndexOutOfBoundsException {
        this.dispatch.writeBufferByte(this.context, this.receiver, byteOffset, value2);
    }

    public short readBufferShort(ByteOrder order, long byteOffset) throws UnsupportedOperationException, IndexOutOfBoundsException {
        return this.dispatch.readBufferShort(this.context, this.receiver, order, byteOffset);
    }

    public void writeBufferShort(ByteOrder order, long byteOffset, short value2) throws UnsupportedOperationException, IndexOutOfBoundsException {
        this.dispatch.writeBufferShort(this.context, this.receiver, order, byteOffset, value2);
    }

    public int readBufferInt(ByteOrder order, long byteOffset) throws UnsupportedOperationException, IndexOutOfBoundsException {
        return this.dispatch.readBufferInt(this.context, this.receiver, order, byteOffset);
    }

    public void writeBufferInt(ByteOrder order, long byteOffset, int value2) throws UnsupportedOperationException, IndexOutOfBoundsException {
        this.dispatch.writeBufferInt(this.context, this.receiver, order, byteOffset, value2);
    }

    public long readBufferLong(ByteOrder order, long byteOffset) throws UnsupportedOperationException, IndexOutOfBoundsException {
        return this.dispatch.readBufferLong(this.context, this.receiver, order, byteOffset);
    }

    public void writeBufferLong(ByteOrder order, long byteOffset, long value2) throws UnsupportedOperationException, IndexOutOfBoundsException {
        this.dispatch.writeBufferLong(this.context, this.receiver, order, byteOffset, value2);
    }

    public float readBufferFloat(ByteOrder order, long byteOffset) throws UnsupportedOperationException, IndexOutOfBoundsException {
        return this.dispatch.readBufferFloat(this.context, this.receiver, order, byteOffset);
    }

    public void writeBufferFloat(ByteOrder order, long byteOffset, float value2) throws UnsupportedOperationException, IndexOutOfBoundsException {
        this.dispatch.writeBufferFloat(this.context, this.receiver, order, byteOffset, value2);
    }

    public double readBufferDouble(ByteOrder order, long byteOffset) throws UnsupportedOperationException, IndexOutOfBoundsException {
        return this.dispatch.readBufferDouble(this.context, this.receiver, order, byteOffset);
    }

    public void writeBufferDouble(ByteOrder order, long byteOffset, double value2) throws UnsupportedOperationException, IndexOutOfBoundsException {
        this.dispatch.writeBufferDouble(this.context, this.receiver, order, byteOffset, value2);
    }

    public boolean hasMembers() {
        return this.dispatch.hasMembers(this.context, this.receiver);
    }

    public boolean hasMember(String identifier) {
        Objects.requireNonNull(identifier, "identifier");
        return this.dispatch.hasMember(this.context, this.receiver, identifier);
    }

    public Value getMember(String identifier) {
        Objects.requireNonNull(identifier, "identifier");
        return this.dispatch.getMember(this.context, this.receiver, identifier);
    }

    public Set<String> getMemberKeys() {
        return this.dispatch.getMemberKeys(this.context, this.receiver);
    }

    public void putMember(String identifier, Object value2) {
        Objects.requireNonNull(identifier, "identifier");
        this.dispatch.putMember(this.context, this.receiver, identifier, value2);
    }

    public boolean removeMember(String identifier) {
        Objects.requireNonNull(identifier, "identifier");
        return this.dispatch.removeMember(this.context, this.receiver, identifier);
    }

    public boolean canExecute() {
        return this.dispatch.canExecute(this.context, this.receiver);
    }

    public Value execute(Object ... arguments) {
        if (arguments.length == 0) {
            return this.dispatch.execute(this.context, this.receiver);
        }
        return this.dispatch.execute(this.context, this.receiver, arguments);
    }

    public void executeVoid(Object ... arguments) {
        if (arguments.length == 0) {
            this.dispatch.executeVoid(this.context, this.receiver);
        } else {
            this.dispatch.executeVoid(this.context, this.receiver, arguments);
        }
    }

    public boolean canInstantiate() {
        return this.dispatch.canInstantiate(this.context, this.receiver);
    }

    public Value newInstance(Object ... arguments) {
        Objects.requireNonNull(arguments, "arguments");
        return this.dispatch.newInstance(this.context, this.receiver, arguments);
    }

    public boolean canInvokeMember(String identifier) {
        Objects.requireNonNull(identifier, "identifier");
        return this.dispatch.canInvoke(this.context, identifier, this.receiver);
    }

    public Value invokeMember(String identifier, Object ... arguments) {
        Objects.requireNonNull(identifier, "identifier");
        if (arguments.length == 0) {
            return this.dispatch.invoke(this.context, this.receiver, identifier);
        }
        return this.dispatch.invoke(this.context, this.receiver, identifier, arguments);
    }

    public boolean isString() {
        return this.dispatch.isString(this.context, this.receiver);
    }

    public String asString() {
        return this.dispatch.asString(this.context, this.receiver);
    }

    public boolean fitsInInt() {
        return this.dispatch.fitsInInt(this.context, this.receiver);
    }

    public int asInt() {
        return this.dispatch.asInt(this.context, this.receiver);
    }

    public boolean isBoolean() {
        return this.dispatch.isBoolean(this.context, this.receiver);
    }

    public boolean asBoolean() {
        return this.dispatch.asBoolean(this.context, this.receiver);
    }

    public boolean isNumber() {
        return this.dispatch.isNumber(this.context, this.receiver);
    }

    public boolean fitsInLong() {
        return this.dispatch.fitsInLong(this.context, this.receiver);
    }

    public long asLong() {
        return this.dispatch.asLong(this.context, this.receiver);
    }

    public boolean fitsInDouble() {
        return this.dispatch.fitsInDouble(this.context, this.receiver);
    }

    public double asDouble() {
        return this.dispatch.asDouble(this.context, this.receiver);
    }

    public boolean fitsInFloat() {
        return this.dispatch.fitsInFloat(this.context, this.receiver);
    }

    public float asFloat() {
        return this.dispatch.asFloat(this.context, this.receiver);
    }

    public boolean fitsInByte() {
        return this.dispatch.fitsInByte(this.context, this.receiver);
    }

    public byte asByte() {
        return this.dispatch.asByte(this.context, this.receiver);
    }

    public boolean fitsInShort() {
        return this.dispatch.fitsInShort(this.context, this.receiver);
    }

    public short asShort() {
        return this.dispatch.asShort(this.context, this.receiver);
    }

    public boolean isNull() {
        return this.dispatch.isNull(this.context, this.receiver);
    }

    public boolean isNativePointer() {
        return this.dispatch.isNativePointer(this.context, this.receiver);
    }

    public long asNativePointer() {
        return this.dispatch.asNativePointer(this.context, this.receiver);
    }

    public boolean isHostObject() {
        return this.dispatch.isHostObject(this.context, this.receiver);
    }

    public <T> T asHostObject() {
        return (T)this.dispatch.asHostObject(this.context, this.receiver);
    }

    public boolean isProxyObject() {
        return this.dispatch.isProxyObject(this.context, this.receiver);
    }

    public <T extends Proxy> T asProxyObject() {
        return (T)((Proxy)this.dispatch.asProxyObject(this.context, this.receiver));
    }

    public <T> T as(Class<T> targetType) throws ClassCastException, IllegalStateException, PolyglotException {
        Objects.requireNonNull(targetType, "targetType");
        if (targetType == Value.class) {
            return (T)this;
        }
        return this.dispatch.as(this.context, this.receiver, targetType);
    }

    public <T> T as(TypeLiteral<T> targetType) {
        Objects.requireNonNull(targetType, "targetType");
        return this.dispatch.as(this.context, this.receiver, targetType);
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public SourceSection getSourceLocation() {
        return this.dispatch.getSourceLocation(this.context, this.receiver);
    }

    public boolean isDate() {
        return this.dispatch.isDate(this.context, this.receiver);
    }

    public LocalDate asDate() {
        return this.dispatch.asDate(this.context, this.receiver);
    }

    public boolean isTime() {
        return this.dispatch.isTime(this.context, this.receiver);
    }

    public LocalTime asTime() {
        return this.dispatch.asTime(this.context, this.receiver);
    }

    public boolean isInstant() {
        return this.isDate() && this.isTime() && this.isTimeZone();
    }

    public Instant asInstant() {
        return this.dispatch.asInstant(this.context, this.receiver);
    }

    public boolean isTimeZone() {
        return this.dispatch.isTimeZone(this.context, this.receiver);
    }

    public ZoneId asTimeZone() {
        return this.dispatch.asTimeZone(this.context, this.receiver);
    }

    public boolean isDuration() {
        return this.dispatch.isDuration(this.context, this.receiver);
    }

    public Duration asDuration() {
        return this.dispatch.asDuration(this.context, this.receiver);
    }

    public boolean isException() {
        return this.dispatch.isException(this.context, this.receiver);
    }

    public RuntimeException throwException() {
        return this.dispatch.throwException(this.context, this.receiver);
    }

    public Context getContext() {
        Context c = this.dispatch.getContext(this.context);
        if (c != null && c.currentAPI != null) {
            return c.currentAPI;
        }
        return c;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    public boolean hasIterator() {
        return this.dispatch.hasIterator(this.context, this.receiver);
    }

    public Value getIterator() {
        return this.dispatch.getIterator(this.context, this.receiver);
    }

    public boolean isIterator() {
        return this.dispatch.isIterator(this.context, this.receiver);
    }

    public boolean hasIteratorNextElement() {
        return this.dispatch.hasIteratorNextElement(this.context, this.receiver);
    }

    public Value getIteratorNextElement() {
        return this.dispatch.getIteratorNextElement(this.context, this.receiver);
    }

    public boolean hasHashEntries() {
        return this.dispatch.hasHashEntries(this.context, this.receiver);
    }

    public long getHashSize() throws UnsupportedOperationException {
        return this.dispatch.getHashSize(this.context, this.receiver);
    }

    public boolean hasHashEntry(Object key) {
        return this.dispatch.hasHashEntry(this.context, this.receiver, key);
    }

    public Value getHashValue(Object key) throws UnsupportedOperationException {
        return this.dispatch.getHashValue(this.context, this.receiver, key);
    }

    public Value getHashValueOrDefault(Object key, Object defaultValue) throws UnsupportedOperationException {
        return this.dispatch.getHashValueOrDefault(this.context, this.receiver, key, defaultValue);
    }

    public void putHashEntry(Object key, Object value2) throws IllegalArgumentException, UnsupportedOperationException {
        this.dispatch.putHashEntry(this.context, this.receiver, key, value2);
    }

    public boolean removeHashEntry(Object key) throws UnsupportedOperationException {
        return this.dispatch.removeHashEntry(this.context, this.receiver, key);
    }

    public Value getHashEntriesIterator() throws UnsupportedOperationException {
        return this.dispatch.getHashEntriesIterator(this.context, this.receiver);
    }

    public Value getHashKeysIterator() throws UnsupportedOperationException {
        return this.dispatch.getHashKeysIterator(this.context, this.receiver);
    }

    public Value getHashValuesIterator() throws UnsupportedOperationException {
        return this.dispatch.getHashValuesIterator(this.context, this.receiver);
    }

    public static Value asValue(Object o) {
        if (o instanceof Value) {
            return (Value)o;
        }
        return Engine.getImpl().asValue(o);
    }

    public void pin() {
        this.dispatch.pin(this.context, this.receiver);
    }
}

