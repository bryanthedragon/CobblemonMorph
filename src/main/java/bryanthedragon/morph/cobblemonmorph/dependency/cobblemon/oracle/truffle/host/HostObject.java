
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
import com.oracle.truffle.host.HostAccessor;
import com.oracle.truffle.host.HostAdapterFactory;
import com.oracle.truffle.host.HostClassCache;
import com.oracle.truffle.host.HostClassDesc;
import com.oracle.truffle.host.HostContext;
import com.oracle.truffle.host.HostException;
import com.oracle.truffle.host.HostExecuteNode;
import com.oracle.truffle.host.HostFieldDesc;
import com.oracle.truffle.host.HostFunction;
import com.oracle.truffle.host.HostInteropErrors;
import com.oracle.truffle.host.HostInteropReflect;
import com.oracle.truffle.host.HostLanguage;
import com.oracle.truffle.host.HostMethodDesc;
import com.oracle.truffle.host.HostProxy;
import com.oracle.truffle.host.HostTargetMappingNode;
import com.oracle.truffle.host.HostToTypeNode;
import java.lang.reflect.Array;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ReadOnlyBufferException;
import java.sql.Time;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import org.graalvm.polyglot.impl.AbstractPolyglotImpl;
import org.graalvm.polyglot.proxy.Proxy;

@ExportLibrary(value=InteropLibrary.class)
final class HostObject
implements TruffleObject {
    static final int LIMIT = 5;
    private static final Class<? extends ByteBuffer> HEAP_BYTE_BUFFER_CLASS = ByteBuffer.allocate(0).getClass();
    private static final Class<? extends ByteBuffer> HEAP_BYTE_BUFFER_R_CLASS = ByteBuffer.allocate(0).asReadOnlyBuffer().getClass();
    private static final Class<? extends ByteBuffer> DIRECT_BYTE_BUFFER_CLASS = ByteBuffer.allocateDirect(0).getClass();
    private static final Class<? extends ByteBuffer> DIRECT_BYTE_BUFFER_R_CLASS = ByteBuffer.allocateDirect(0).asReadOnlyBuffer().getClass();
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
        assert (clazz != null);
        return new HostObject(clazz, context, null);
    }

    static HostObject forStaticClass(Class<?> clazz, HostContext context) {
        assert (clazz != null);
        return new HostObject(clazz, context, clazz);
    }

    static HostObject forObject(Object object, HostContext context) {
        assert (object != null && !(object instanceof Class));
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
        assert (context != null);
        Object obj = HostLanguage.unwrapIfScoped(language, originalValue);
        if (obj instanceof HostObject) {
            HostObject hostObject = (HostObject)obj;
            return new HostObject(hostObject.obj, context, hostObject.extraInfo);
        }
        if (obj instanceof HostException) {
            return new HostException(((HostException)obj).getOriginal(), context);
        }
        throw CompilerDirectives.shouldNotReachHere("Parameter must be HostObject or HostException.");
    }

    static boolean isJavaInstance(HostLanguage language, Class<?> targetType, Object javaObject) {
        Object unboxed = HostObject.unboxHostObject(language, javaObject);
        if (unboxed != null) {
            return targetType.isInstance(unboxed);
        }
        return false;
    }

    static Object unboxHostObject(HostLanguage language, Object value2) {
        Object v = HostLanguage.unwrapIfScoped(language, value2);
        if (v instanceof HostObject) {
            return ((HostObject)v).obj;
        }
        if (v instanceof HostException) {
            return ((HostException)v).delegate.obj;
        }
        return null;
    }

    static Object valueOf(HostLanguage language, Object value2) {
        Object v = HostLanguage.unwrapIfScoped(language, value2);
        if (v instanceof HostObject) {
            return ((HostObject)v).obj;
        }
        if (v instanceof HostException) {
            return ((HostException)v).delegate.obj;
        }
        return v;
    }

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
        if (access.isEngineException(e)) {
            return access.unboxEngineException(e);
        }
        return null;
    }

    @ExportMessage
    boolean hasMembers() {
        return !this.isNull();
    }

    @ExportMessage
    Object getMembers(boolean includeInternal) throws UnsupportedMessageException {
        if (this.isNull()) {
            throw UnsupportedMessageException.create();
        }
        String[] fields = HostInteropReflect.findUniquePublicMemberNames(this.context, this.getLookupClass(), this.isStaticClass(), this.isClass(), includeInternal);
        return new KeysArray(fields);
    }

    @ExportMessage
    Object readMember(String name, @Cached.Shared(value="lookupField") @Cached LookupFieldNode lookupField, @Cached.Shared(value="readField") @Cached ReadFieldNode readField, @Cached.Shared(value="lookupMethod") @Cached LookupMethodNode lookupMethod, @Cached LookupInnerClassNode lookupInnerClass, @Cached.Shared(value="error") @Cached BranchProfile error) throws UnsupportedMessageException, UnknownIdentifierException {
        if (this.isNull()) {
            error.enter();
            throw UnsupportedMessageException.create();
        }
        boolean isStatic = this.isStaticClass();
        Class<?> lookupClass = this.getLookupClass();
        HostFieldDesc foundField = lookupField.execute(this, lookupClass, name, isStatic);
        if (foundField != null) {
            return readField.execute(foundField, this);
        }
        HostMethodDesc foundMethod = lookupMethod.execute(this, lookupClass, name, isStatic);
        if (foundMethod != null) {
            return new HostFunction(foundMethod, this.obj, this.context);
        }
        if (isStatic) {
            LookupInnerClassNode lookupInnerClassNode = lookupInnerClass;
            if ("class".equals(name)) {
                return HostObject.forClass(lookupClass, this.context);
            }
            Class<?> innerclass = lookupInnerClassNode.execute(lookupClass, name);
            if (innerclass != null) {
                return HostObject.forStaticClass(innerclass, this.context);
            }
        } else {
            if (this.isClass() && "static".equals(name)) {
                return HostObject.forStaticClass(this.asClass(), this.context);
            }
            if ("super".equals(name) && HostAdapterFactory.isAdapterInstance(this.obj)) {
                return HostAdapterFactory.getSuperAdapter(this);
            }
        }
        error.enter();
        throw UnknownIdentifierException.create(name);
    }

    @ExportMessage
    boolean isMemberInsertable(String member) {
        return false;
    }

    @ExportMessage
    void writeMember(String member, Object value2, @Cached.Shared(value="lookupField") @Cached LookupFieldNode lookupField, @Cached WriteFieldNode writeField, @Cached.Shared(value="error") @Cached BranchProfile error) throws UnsupportedMessageException, UnknownIdentifierException, UnsupportedTypeException {
        if (this.isNull()) {
            error.enter();
            throw UnsupportedMessageException.create();
        }
        HostFieldDesc f = lookupField.execute(this, this.getLookupClass(), member, this.isStaticClass());
        if (f == null) {
            error.enter();
            throw UnknownIdentifierException.create(member);
        }
        try {
            writeField.execute(f, this, value2);
        }
        catch (ClassCastException | NullPointerException e) {
            error.enter();
            throw UnsupportedTypeException.create(new Object[]{value2}, HostObject.getMessage(e));
        }
    }

    @CompilerDirectives.TruffleBoundary
    private static String getMessage(RuntimeException e) {
        return e.getMessage();
    }

    @ExportMessage
    Object invokeMember(String name, Object[] args, @Cached.Shared(value="lookupMethod") @Cached LookupMethodNode lookupMethod, @Cached.Shared(value="hostExecute") @Cached HostExecuteNode executeMethod, @Cached.Shared(value="lookupField") @Cached LookupFieldNode lookupField, @Cached.Shared(value="readField") @Cached ReadFieldNode readField, @CachedLibrary(limit="5") InteropLibrary fieldValues, @Cached.Shared(value="error") @Cached BranchProfile error) throws UnsupportedTypeException, ArityException, UnsupportedMessageException, UnknownIdentifierException {
        Object fieldValue;
        if (this.isNull()) {
            error.enter();
            throw UnsupportedMessageException.create();
        }
        boolean isStatic = this.isStaticClass();
        Class<?> lookupClass = this.getLookupClass();
        HostMethodDesc foundMethod = lookupMethod.execute(this, lookupClass, name, isStatic);
        if (foundMethod != null) {
            return executeMethod.execute(foundMethod, this.obj, args, this.context);
        }
        HostFieldDesc foundField = lookupField.execute(this, lookupClass, name, isStatic);
        if (foundField != null && fieldValues.isExecutable(fieldValue = readField.execute(foundField, this))) {
            return fieldValues.execute(fieldValue, args);
        }
        error.enter();
        throw UnknownIdentifierException.create(name);
    }

    @ExportMessage
    boolean isArrayElementInsertable(long index, @Cached.Shared(value="isList") @Cached IsListNode isList, @Cached.Shared(value="error") @Cached BranchProfile error) {
        try {
            return isList.execute(this) && (long)GuestToHostCalls.getListSize(this) == index;
        }
        catch (Throwable t) {
            error.enter();
            throw this.context.hostToGuestException(t);
        }
    }

    @ExportMessage
    boolean hasArrayElements(@Cached.Shared(value="isList") @Cached IsListNode isList, @Cached.Shared(value="isArray") @Cached IsArrayNode isArray, @Cached.Shared(value="isMapEntry") @Cached IsMapEntryNode isMapEntry) {
        return isList.execute(this) || isArray.execute(this) || isMapEntry.execute(this);
    }

    @ExportMessage
    boolean hasBufferElements(@Cached.Shared(value="isBuffer") @Cached IsBufferNode isBuffer) {
        return isBuffer.execute(this);
    }

    @ExportMessage
    boolean isBufferWritable(@Cached.Shared(value="isBuffer") @Cached IsBufferNode isBuffer, @Cached.Shared(value="error") @Cached BranchProfile error) throws UnsupportedMessageException {
        if (isBuffer.execute(this)) {
            ByteBuffer buffer = (ByteBuffer)this.obj;
            return HostObject.isPEFriendlyBuffer(buffer) ? !buffer.isReadOnly() : HostObject.isBufferWritableBoundary(buffer);
        }
        error.enter();
        throw UnsupportedMessageException.create();
    }

    @CompilerDirectives.TruffleBoundary
    private static boolean isBufferWritableBoundary(ByteBuffer buffer) {
        return !buffer.isReadOnly();
    }

    @ExportMessage
    long getBufferSize(@Cached.Shared(value="isBuffer") @Cached IsBufferNode isBuffer, @Cached.Shared(value="error") @Cached BranchProfile error) throws UnsupportedMessageException {
        if (isBuffer.execute(this)) {
            ByteBuffer buffer = (ByteBuffer)this.obj;
            return HostObject.isPEFriendlyBuffer(buffer) ? (long)buffer.limit() : HostObject.getBufferSizeBoundary(buffer);
        }
        error.enter();
        throw UnsupportedMessageException.create();
    }

    @CompilerDirectives.TruffleBoundary
    private static long getBufferSizeBoundary(ByteBuffer buffer) {
        return buffer.limit();
    }

    private static boolean isPEFriendlyBuffer(ByteBuffer buffer) {
        boolean result;
        Class<?> clazz = buffer.getClass();
        boolean bl = result = CompilerDirectives.isPartialEvaluationConstant(clazz) && (clazz == HEAP_BYTE_BUFFER_CLASS || clazz == HEAP_BYTE_BUFFER_R_CLASS || clazz == DIRECT_BYTE_BUFFER_CLASS || clazz == DIRECT_BYTE_BUFFER_R_CLASS);
        assert (result) : "Unexpected Buffer subclass";
        return result;
    }

    @ExportMessage
    public byte readBufferByte(long index, @Cached.Shared(value="isBuffer") @Cached IsBufferNode isBuffer, @Cached.Shared(value="error") @Cached BranchProfile error, @Cached.Shared(value="classProfile") @Cached(value="createClassProfile()") ValueProfile classProfile) throws UnsupportedMessageException, InvalidBufferOffsetException {
        if (!isBuffer.execute(this)) {
            error.enter();
            throw UnsupportedMessageException.create();
        }
        if (index < 0L || Integer.MAX_VALUE < index) {
            error.enter();
            throw InvalidBufferOffsetException.create(index, 1L);
        }
        try {
            ByteBuffer buffer = (ByteBuffer)classProfile.profile(this.obj);
            return HostObject.isPEFriendlyBuffer(buffer) ? buffer.get((int)index) : HostObject.getBufferByteBoundary(buffer, (int)index);
        }
        catch (IndexOutOfBoundsException e) {
            error.enter();
            throw InvalidBufferOffsetException.create(index, 1L);
        }
    }

    @CompilerDirectives.TruffleBoundary
    private static byte getBufferByteBoundary(ByteBuffer buffer, int index) {
        return buffer.get(index);
    }

    @ExportMessage
    public void writeBufferByte(long index, byte value2, @Cached.Shared(value="isBuffer") @Cached IsBufferNode isBuffer, @Cached.Shared(value="error") @Cached BranchProfile error, @Cached.Shared(value="classProfile") @Cached(value="createClassProfile()") ValueProfile classProfile) throws InvalidBufferOffsetException, UnsupportedMessageException {
        if (!isBuffer.execute(this)) {
            error.enter();
            throw UnsupportedMessageException.create();
        }
        if (index < 0L || Integer.MAX_VALUE < index) {
            error.enter();
            throw InvalidBufferOffsetException.create(index, 1L);
        }
        try {
            ByteBuffer buffer = (ByteBuffer)classProfile.profile(this.obj);
            if (HostObject.isPEFriendlyBuffer(buffer)) {
                buffer.put((int)index, value2);
            } else {
                HostObject.putBufferByteBoundary(buffer, (int)index, value2);
            }
        }
        catch (IndexOutOfBoundsException e) {
            error.enter();
            throw InvalidBufferOffsetException.create(index, 1L);
        }
        catch (ReadOnlyBufferException e) {
            error.enter();
            throw UnsupportedMessageException.create();
        }
    }

    @CompilerDirectives.TruffleBoundary
    private static void putBufferByteBoundary(ByteBuffer buffer, int index, byte value2) {
        buffer.put(index, value2);
    }

    @ExportMessage
    public short readBufferShort(ByteOrder order, long index, @Cached.Shared(value="isBuffer") @Cached IsBufferNode isBuffer, @Cached.Shared(value="error") @Cached BranchProfile error, @Cached.Shared(value="classProfile") @Cached(value="createClassProfile()") ValueProfile classProfile) throws UnsupportedMessageException, InvalidBufferOffsetException {
        if (!isBuffer.execute(this)) {
            error.enter();
            throw UnsupportedMessageException.create();
        }
        if (index < 0L || Integer.MAX_VALUE < index) {
            error.enter();
            throw InvalidBufferOffsetException.create(index, 2L);
        }
        try {
            ByteBuffer buffer = (ByteBuffer)classProfile.profile(this.obj);
            ByteOrder originalOrder = buffer.order();
            buffer.order(order);
            short result = HostObject.isPEFriendlyBuffer(buffer) ? buffer.getShort((int)index) : HostObject.getBufferShortBoundary(buffer, (int)index);
            buffer.order(originalOrder);
            return result;
        }
        catch (IndexOutOfBoundsException e) {
            error.enter();
            throw InvalidBufferOffsetException.create(index, 2L);
        }
    }

    @CompilerDirectives.TruffleBoundary
    private static short getBufferShortBoundary(ByteBuffer buffer, int index) {
        return buffer.getShort(index);
    }

    @ExportMessage
    public void writeBufferShort(ByteOrder order, long index, short value2, @Cached.Shared(value="isBuffer") @Cached IsBufferNode isBuffer, @Cached.Shared(value="error") @Cached BranchProfile error, @Cached.Shared(value="classProfile") @Cached(value="createClassProfile()") ValueProfile classProfile) throws InvalidBufferOffsetException, UnsupportedMessageException {
        if (!isBuffer.execute(this)) {
            error.enter();
            throw UnsupportedMessageException.create();
        }
        if (index < 0L || Integer.MAX_VALUE < index) {
            error.enter();
            throw InvalidBufferOffsetException.create(index, 2L);
        }
        try {
            ByteBuffer buffer = (ByteBuffer)classProfile.profile(this.obj);
            ByteOrder originalOrder = buffer.order();
            buffer.order(order);
            if (HostObject.isPEFriendlyBuffer(buffer)) {
                buffer.putShort((int)index, value2);
            } else {
                HostObject.putBufferShortBoundary(buffer, (int)index, value2);
            }
            buffer.order(originalOrder);
        }
        catch (IndexOutOfBoundsException e) {
            error.enter();
            throw InvalidBufferOffsetException.create(index, 2L);
        }
        catch (ReadOnlyBufferException e) {
            error.enter();
            throw UnsupportedMessageException.create();
        }
    }

    @CompilerDirectives.TruffleBoundary
    private static void putBufferShortBoundary(ByteBuffer buffer, int index, short value2) {
        buffer.putShort(index, value2);
    }

    @ExportMessage
    public int readBufferInt(ByteOrder order, long index, @Cached.Shared(value="isBuffer") @Cached IsBufferNode isBuffer, @Cached.Shared(value="error") @Cached BranchProfile error, @Cached.Shared(value="classProfile") @Cached(value="createClassProfile()") ValueProfile classProfile) throws UnsupportedMessageException, InvalidBufferOffsetException {
        if (!isBuffer.execute(this)) {
            error.enter();
            throw UnsupportedMessageException.create();
        }
        if (index < 0L || Integer.MAX_VALUE < index) {
            error.enter();
            throw InvalidBufferOffsetException.create(index, 4L);
        }
        try {
            ByteBuffer buffer = (ByteBuffer)classProfile.profile(this.obj);
            ByteOrder originalOrder = buffer.order();
            buffer.order(order);
            int result = HostObject.isPEFriendlyBuffer(buffer) ? buffer.getInt((int)index) : HostObject.getBufferIntBoundary(buffer, (int)index);
            buffer.order(originalOrder);
            return result;
        }
        catch (IndexOutOfBoundsException e) {
            error.enter();
            throw InvalidBufferOffsetException.create(index, 4L);
        }
    }

    @CompilerDirectives.TruffleBoundary
    private static int getBufferIntBoundary(ByteBuffer buffer, int index) {
        return buffer.getInt(index);
    }

    @ExportMessage
    public void writeBufferInt(ByteOrder order, long index, int value2, @Cached.Shared(value="isBuffer") @Cached IsBufferNode isBuffer, @Cached.Shared(value="error") @Cached BranchProfile error, @Cached.Shared(value="classProfile") @Cached(value="createClassProfile()") ValueProfile classProfile) throws InvalidBufferOffsetException, UnsupportedMessageException {
        if (!isBuffer.execute(this)) {
            error.enter();
            throw UnsupportedMessageException.create();
        }
        if (index < 0L || Integer.MAX_VALUE < index) {
            error.enter();
            throw InvalidBufferOffsetException.create(index, 4L);
        }
        try {
            ByteBuffer buffer = (ByteBuffer)classProfile.profile(this.obj);
            ByteOrder originalOrder = buffer.order();
            buffer.order(order);
            if (HostObject.isPEFriendlyBuffer(buffer)) {
                buffer.putInt((int)index, value2);
            } else {
                HostObject.putBufferIntBoundary(buffer, (int)index, value2);
            }
            buffer.order(originalOrder);
        }
        catch (IndexOutOfBoundsException e) {
            error.enter();
            throw InvalidBufferOffsetException.create(index, 4L);
        }
        catch (ReadOnlyBufferException e) {
            error.enter();
            throw UnsupportedMessageException.create();
        }
    }

    @CompilerDirectives.TruffleBoundary
    private static void putBufferIntBoundary(ByteBuffer buffer, int index, int value2) {
        buffer.putInt(index, value2);
    }

    @ExportMessage
    public long readBufferLong(ByteOrder order, long index, @Cached.Shared(value="isBuffer") @Cached IsBufferNode isBuffer, @Cached.Shared(value="error") @Cached BranchProfile error, @Cached.Shared(value="classProfile") @Cached(value="createClassProfile()") ValueProfile classProfile) throws UnsupportedMessageException, InvalidBufferOffsetException {
        if (!isBuffer.execute(this)) {
            error.enter();
            throw UnsupportedMessageException.create();
        }
        if (index < 0L || Integer.MAX_VALUE < index) {
            error.enter();
            throw InvalidBufferOffsetException.create(index, 8L);
        }
        try {
            ByteBuffer buffer = (ByteBuffer)classProfile.profile(this.obj);
            ByteOrder originalOrder = buffer.order();
            buffer.order(order);
            long result = HostObject.isPEFriendlyBuffer(buffer) ? buffer.getLong((int)index) : HostObject.getBufferLongBoundary(buffer, (int)index);
            buffer.order(originalOrder);
            return result;
        }
        catch (IndexOutOfBoundsException e) {
            error.enter();
            throw InvalidBufferOffsetException.create(index, 8L);
        }
    }

    @CompilerDirectives.TruffleBoundary
    private static long getBufferLongBoundary(ByteBuffer buffer, int index) {
        return buffer.getLong(index);
    }

    @ExportMessage
    public void writeBufferLong(ByteOrder order, long index, long value2, @Cached.Shared(value="isBuffer") @Cached IsBufferNode isBuffer, @Cached.Shared(value="error") @Cached BranchProfile error, @Cached.Shared(value="classProfile") @Cached(value="createClassProfile()") ValueProfile classProfile) throws InvalidBufferOffsetException, UnsupportedMessageException {
        if (!isBuffer.execute(this)) {
            error.enter();
            throw UnsupportedMessageException.create();
        }
        if (index < 0L || Integer.MAX_VALUE < index) {
            error.enter();
            throw InvalidBufferOffsetException.create(index, 8L);
        }
        try {
            ByteBuffer buffer = (ByteBuffer)classProfile.profile(this.obj);
            ByteOrder originalOrder = buffer.order();
            buffer.order(order);
            if (HostObject.isPEFriendlyBuffer(buffer)) {
                buffer.putLong((int)index, value2);
            } else {
                HostObject.putBufferLongBoundary(buffer, (int)index, value2);
            }
            buffer.order(originalOrder);
        }
        catch (IndexOutOfBoundsException e) {
            error.enter();
            throw InvalidBufferOffsetException.create(index, 8L);
        }
        catch (ReadOnlyBufferException e) {
            error.enter();
            throw UnsupportedMessageException.create();
        }
    }

    @CompilerDirectives.TruffleBoundary
    private static void putBufferLongBoundary(ByteBuffer buffer, int index, long value2) {
        buffer.putLong(index, value2);
    }

    @ExportMessage
    public float readBufferFloat(ByteOrder order, long index, @Cached.Shared(value="isBuffer") @Cached IsBufferNode isBuffer, @Cached.Shared(value="error") @Cached BranchProfile error, @Cached.Shared(value="classProfile") @Cached(value="createClassProfile()") ValueProfile classProfile) throws UnsupportedMessageException, InvalidBufferOffsetException {
        if (!isBuffer.execute(this)) {
            error.enter();
            throw UnsupportedMessageException.create();
        }
        if (index < 0L || Integer.MAX_VALUE < index) {
            error.enter();
            throw InvalidBufferOffsetException.create(index, 4L);
        }
        try {
            ByteBuffer buffer = (ByteBuffer)classProfile.profile(this.obj);
            ByteOrder originalOrder = buffer.order();
            buffer.order(order);
            float result = HostObject.isPEFriendlyBuffer(buffer) ? buffer.getFloat((int)index) : HostObject.getBufferFloatBoundary(buffer, (int)index);
            buffer.order(originalOrder);
            return result;
        }
        catch (IndexOutOfBoundsException e) {
            error.enter();
            throw InvalidBufferOffsetException.create(index, 4L);
        }
    }

    @CompilerDirectives.TruffleBoundary
    private static float getBufferFloatBoundary(ByteBuffer buffer, int index) {
        return buffer.getFloat(index);
    }

    @ExportMessage
    public void writeBufferFloat(ByteOrder order, long index, float value2, @Cached.Shared(value="isBuffer") @Cached IsBufferNode isBuffer, @Cached.Shared(value="error") @Cached BranchProfile error, @Cached.Shared(value="classProfile") @Cached(value="createClassProfile()") ValueProfile classProfile) throws InvalidBufferOffsetException, UnsupportedMessageException {
        if (!isBuffer.execute(this)) {
            error.enter();
            throw UnsupportedMessageException.create();
        }
        if (index < 0L || Integer.MAX_VALUE < index) {
            error.enter();
            throw InvalidBufferOffsetException.create(index, 4L);
        }
        try {
            ByteBuffer buffer = (ByteBuffer)classProfile.profile(this.obj);
            ByteOrder originalOrder = buffer.order();
            buffer.order(order);
            if (HostObject.isPEFriendlyBuffer(buffer)) {
                buffer.putFloat((int)index, value2);
            } else {
                HostObject.putBufferFloatBoundary(buffer, (int)index, value2);
            }
            buffer.order(originalOrder);
        }
        catch (IndexOutOfBoundsException e) {
            error.enter();
            throw InvalidBufferOffsetException.create(index, 4L);
        }
        catch (ReadOnlyBufferException e) {
            error.enter();
            throw UnsupportedMessageException.create();
        }
    }

    @CompilerDirectives.TruffleBoundary
    private static void putBufferFloatBoundary(ByteBuffer buffer, int index, float value2) {
        buffer.putFloat(index, value2);
    }

    @ExportMessage
    public double readBufferDouble(ByteOrder order, long index, @Cached.Shared(value="isBuffer") @Cached IsBufferNode isBuffer, @Cached.Shared(value="error") @Cached BranchProfile error, @Cached.Shared(value="classProfile") @Cached(value="createClassProfile()") ValueProfile classProfile) throws UnsupportedMessageException, InvalidBufferOffsetException {
        if (!isBuffer.execute(this)) {
            error.enter();
            throw UnsupportedMessageException.create();
        }
        if (index < 0L || Integer.MAX_VALUE < index) {
            error.enter();
            throw InvalidBufferOffsetException.create(index, 8L);
        }
        try {
            ByteBuffer buffer = (ByteBuffer)classProfile.profile(this.obj);
            ByteOrder originalOrder = buffer.order();
            buffer.order(order);
            double result = HostObject.isPEFriendlyBuffer(buffer) ? buffer.getDouble((int)index) : HostObject.getBufferDoubleBoundary(buffer, (int)index);
            buffer.order(originalOrder);
            return result;
        }
        catch (IndexOutOfBoundsException e) {
            error.enter();
            throw InvalidBufferOffsetException.create(index, 8L);
        }
    }

    @CompilerDirectives.TruffleBoundary
    private static double getBufferDoubleBoundary(ByteBuffer buffer, int index) {
        return buffer.getDouble(index);
    }

    @ExportMessage
    public void writeBufferDouble(ByteOrder order, long index, double value2, @Cached.Shared(value="isBuffer") @Cached IsBufferNode isBuffer, @Cached.Shared(value="error") @Cached BranchProfile error, @Cached.Shared(value="classProfile") @Cached(value="createClassProfile()") ValueProfile classProfile) throws InvalidBufferOffsetException, UnsupportedMessageException {
        if (!isBuffer.execute(this)) {
            error.enter();
            throw UnsupportedMessageException.create();
        }
        if (index < 0L || Integer.MAX_VALUE < index) {
            error.enter();
            throw InvalidBufferOffsetException.create(index, 8L);
        }
        try {
            ByteBuffer buffer = (ByteBuffer)classProfile.profile(this.obj);
            ByteOrder originalOrder = buffer.order();
            buffer.order(order);
            if (HostObject.isPEFriendlyBuffer(buffer)) {
                buffer.putDouble((int)index, value2);
            } else {
                HostObject.putBufferDoubleBoundary(buffer, (int)index, value2);
            }
            buffer.order(originalOrder);
        }
        catch (IndexOutOfBoundsException e) {
            error.enter();
            throw InvalidBufferOffsetException.create(index, 8L);
        }
        catch (ReadOnlyBufferException e) {
            error.enter();
            throw UnsupportedMessageException.create();
        }
    }

    @CompilerDirectives.TruffleBoundary
    private static void putBufferDoubleBoundary(ByteBuffer buffer, int index, double value2) {
        buffer.putDouble(index, value2);
    }

    @ExportMessage
    boolean isNull() {
        return this.obj == null;
    }

    @ExportMessage
    boolean isExecutable(@Cached.Shared(value="lookupFunctionalMethod") @Cached LookupFunctionalMethodNode lookupMethod) {
        return !this.isNull() && !this.isClass() && lookupMethod.execute(this, this.getLookupClass()) != null;
    }

    @ExportMessage
    Object execute(Object[] args, @Cached.Shared(value="hostExecute") @Cached HostExecuteNode doExecute, @Cached.Shared(value="lookupFunctionalMethod") @Cached LookupFunctionalMethodNode lookupMethod, @Cached.Shared(value="error") @Cached BranchProfile error) throws UnsupportedMessageException, UnsupportedTypeException, ArityException {
        HostMethodDesc method;
        if (!this.isNull() && !this.isClass() && (method = lookupMethod.execute(this, this.getLookupClass())) != null) {
            return doExecute.execute(method, this.obj, args, this.context);
        }
        error.enter();
        throw UnsupportedMessageException.create();
    }

    @ExportMessage
    boolean isNumber(@Cached.Shared(value="classProfile") @Cached(value="createClassProfile()") ValueProfile classProfile) {
        if (this.isNull()) {
            return false;
        }
        Class<?> c = classProfile.profile(this.obj).getClass();
        return c == Byte.class || c == Short.class || c == Integer.class || c == Long.class || c == Float.class || c == Double.class;
    }

    private static boolean isJavaPrimitiveNumber(Object value2) {
        return value2 instanceof Byte || value2 instanceof Short || value2 instanceof Integer || value2 instanceof Long || value2 instanceof Float || value2 instanceof Double;
    }

    @ExportMessage
    boolean fitsInByte(@CachedLibrary(value="this") InteropLibrary thisLibrary, @Cached.Shared(value="numbers") @CachedLibrary(limit="LIMIT") InteropLibrary numbers) {
        if (thisLibrary.isNumber(this)) {
            return numbers.fitsInByte(this.obj);
        }
        return false;
    }

    @ExportMessage
    boolean fitsInShort(@CachedLibrary(value="this") InteropLibrary thisLibrary, @Cached.Shared(value="numbers") @CachedLibrary(limit="LIMIT") InteropLibrary numbers) {
        if (thisLibrary.isNumber(this)) {
            return numbers.fitsInShort(this.obj);
        }
        return false;
    }

    @ExportMessage
    boolean fitsInInt(@CachedLibrary(value="this") InteropLibrary thisLibrary, @Cached.Shared(value="numbers") @CachedLibrary(limit="LIMIT") InteropLibrary numbers) {
        if (thisLibrary.isNumber(this)) {
            return numbers.fitsInInt(this.obj);
        }
        return false;
    }

    @ExportMessage
    boolean fitsInLong(@CachedLibrary(value="this") InteropLibrary thisLibrary, @Cached.Shared(value="numbers") @CachedLibrary(limit="LIMIT") InteropLibrary numbers) {
        if (thisLibrary.isNumber(this)) {
            return numbers.fitsInLong(this.obj);
        }
        return false;
    }

    @ExportMessage
    boolean fitsInFloat(@CachedLibrary(value="this") InteropLibrary thisLibrary, @Cached.Shared(value="numbers") @CachedLibrary(limit="LIMIT") InteropLibrary numbers) {
        if (thisLibrary.isNumber(this)) {
            return numbers.fitsInFloat(this.obj);
        }
        return false;
    }

    @ExportMessage
    boolean fitsInDouble(@CachedLibrary(value="this") InteropLibrary thisLibrary, @Cached.Shared(value="numbers") @CachedLibrary(limit="LIMIT") InteropLibrary numbers) {
        if (thisLibrary.isNumber(this)) {
            return numbers.fitsInDouble(this.obj);
        }
        return false;
    }

    @ExportMessage
    byte asByte(@CachedLibrary(value="this") InteropLibrary thisLibrary, @Cached.Shared(value="numbers") @CachedLibrary(limit="LIMIT") InteropLibrary numbers, @Cached.Shared(value="error") @Cached BranchProfile error) throws UnsupportedMessageException {
        if (thisLibrary.isNumber(this)) {
            return numbers.asByte(this.obj);
        }
        error.enter();
        throw UnsupportedMessageException.create();
    }

    @ExportMessage
    short asShort(@CachedLibrary(value="this") InteropLibrary thisLibrary, @Cached.Shared(value="numbers") @CachedLibrary(limit="LIMIT") InteropLibrary numbers, @Cached.Shared(value="error") @Cached BranchProfile error) throws UnsupportedMessageException {
        if (thisLibrary.isNumber(this)) {
            return numbers.asShort(this.obj);
        }
        error.enter();
        throw UnsupportedMessageException.create();
    }

    @ExportMessage
    int asInt(@CachedLibrary(value="this") InteropLibrary thisLibrary, @Cached.Shared(value="numbers") @CachedLibrary(limit="LIMIT") InteropLibrary numbers, @Cached.Shared(value="error") @Cached BranchProfile error) throws UnsupportedMessageException {
        if (thisLibrary.isNumber(this)) {
            return numbers.asInt(this.obj);
        }
        error.enter();
        throw UnsupportedMessageException.create();
    }

    @ExportMessage
    long asLong(@CachedLibrary(value="this") InteropLibrary thisLibrary, @Cached.Shared(value="numbers") @CachedLibrary(limit="LIMIT") InteropLibrary numbers, @Cached.Shared(value="error") @Cached BranchProfile error) throws UnsupportedMessageException {
        if (thisLibrary.isNumber(this)) {
            return numbers.asLong(this.obj);
        }
        error.enter();
        throw UnsupportedMessageException.create();
    }

    @ExportMessage
    float asFloat(@CachedLibrary(value="this") InteropLibrary thisLibrary, @Cached.Shared(value="numbers") @CachedLibrary(limit="LIMIT") InteropLibrary numbers, @Cached.Shared(value="error") @Cached BranchProfile error) throws UnsupportedMessageException {
        if (thisLibrary.isNumber(this)) {
            return numbers.asFloat(this.obj);
        }
        error.enter();
        throw UnsupportedMessageException.create();
    }

    @ExportMessage
    double asDouble(@CachedLibrary(value="this") InteropLibrary thisLibrary, @Cached.Shared(value="numbers") @CachedLibrary(limit="LIMIT") InteropLibrary numbers, @Cached.Shared(value="error") @Cached BranchProfile error) throws UnsupportedMessageException {
        if (thisLibrary.isNumber(this)) {
            return numbers.asDouble(this.obj);
        }
        error.enter();
        throw UnsupportedMessageException.create();
    }

    @ExportMessage
    boolean isString(@Cached.Shared(value="classProfile") @Cached(value="createClassProfile()") ValueProfile classProfile) {
        if (this.isNull()) {
            return false;
        }
        Class<?> c = classProfile.profile(this.obj).getClass();
        return c == String.class || c == Character.class;
    }

    @ExportMessage
    String asString(@CachedLibrary(value="this") InteropLibrary thisLibrary, @Cached.Shared(value="numbers") @CachedLibrary(limit="LIMIT") InteropLibrary strings, @Cached.Shared(value="error") @Cached BranchProfile error) throws UnsupportedMessageException {
        if (thisLibrary.isString(this)) {
            return strings.asString(this.obj);
        }
        error.enter();
        throw UnsupportedMessageException.create();
    }

    @ExportMessage
    boolean isBoolean() {
        if (this.isNull()) {
            return false;
        }
        return this.obj.getClass() == Boolean.class;
    }

    @ExportMessage
    boolean asBoolean(@Cached.Shared(value="error") @Cached BranchProfile error) throws UnsupportedMessageException {
        if (this.isBoolean()) {
            return (Boolean)this.obj;
        }
        error.enter();
        throw UnsupportedMessageException.create();
    }

    @ExportMessage
    boolean isDate() {
        return this.obj instanceof LocalDate || this.obj instanceof LocalDateTime || this.obj instanceof Instant || this.obj instanceof ZonedDateTime || this.obj instanceof java.sql.Date || HostObject.isInstantDate(this.obj);
    }

    @ExportMessage
    @CompilerDirectives.TruffleBoundary
    LocalDate asDate() throws UnsupportedMessageException {
        if (this.obj instanceof LocalDate) {
            return (LocalDate)this.obj;
        }
        if (this.obj instanceof LocalDateTime) {
            return ((LocalDateTime)this.obj).toLocalDate();
        }
        if (this.obj instanceof Instant) {
            return ((Instant)this.obj).atZone(UTC).toLocalDate();
        }
        if (this.obj instanceof ZonedDateTime) {
            return ((ZonedDateTime)this.obj).toLocalDate();
        }
        if (this.obj instanceof java.sql.Date) {
            return ((java.sql.Date)this.obj).toLocalDate();
        }
        if (HostObject.isInstantDate(this.obj)) {
            return ((Date)this.obj).toInstant().atZone(UTC).toLocalDate();
        }
        throw UnsupportedMessageException.create();
    }

    @ExportMessage
    boolean isTime() {
        return this.obj instanceof LocalTime || this.obj instanceof LocalDateTime || this.obj instanceof Instant || this.obj instanceof ZonedDateTime || this.obj instanceof Time || HostObject.isInstantDate(this.obj);
    }

    @ExportMessage
    @CompilerDirectives.TruffleBoundary
    LocalTime asTime() throws UnsupportedMessageException {
        if (this.obj instanceof LocalTime) {
            return (LocalTime)this.obj;
        }
        if (this.obj instanceof LocalDateTime) {
            return ((LocalDateTime)this.obj).toLocalTime();
        }
        if (this.obj instanceof ZonedDateTime) {
            return ((ZonedDateTime)this.obj).toLocalTime();
        }
        if (this.obj instanceof Instant) {
            return ((Instant)this.obj).atZone(UTC).toLocalTime();
        }
        if (this.obj instanceof Time) {
            return ((Time)this.obj).toLocalTime();
        }
        if (HostObject.isInstantDate(this.obj)) {
            return ((Date)this.obj).toInstant().atZone(UTC).toLocalTime();
        }
        throw UnsupportedMessageException.create();
    }

    private static boolean isInstantDate(Object v) {
        return v instanceof Date && !(v instanceof Time) && !(v instanceof java.sql.Date);
    }

    @ExportMessage
    boolean isTimeZone() {
        return this.obj instanceof ZoneId || this.obj instanceof Instant || this.obj instanceof ZonedDateTime || HostObject.isInstantDate(this.obj);
    }

    @ExportMessage
    ZoneId asTimeZone() throws UnsupportedMessageException {
        if (this.obj instanceof ZoneId) {
            return (ZoneId)this.obj;
        }
        if (this.obj instanceof ZonedDateTime) {
            return ((ZonedDateTime)this.obj).getZone();
        }
        if (this.obj instanceof Instant) {
            return UTC;
        }
        if (HostObject.isInstantDate(this.obj)) {
            return UTC;
        }
        throw UnsupportedMessageException.create();
    }

    @ExportMessage
    @CompilerDirectives.TruffleBoundary
    Instant asInstant() throws UnsupportedMessageException {
        if (this.obj instanceof ZonedDateTime) {
            return ((ZonedDateTime)this.obj).toInstant();
        }
        if (this.obj instanceof Instant) {
            return (Instant)this.obj;
        }
        if (HostObject.isInstantDate(this.obj)) {
            return ((Date)this.obj).toInstant();
        }
        throw UnsupportedMessageException.create();
    }

    @ExportMessage
    boolean isDuration() {
        return this.obj instanceof Duration;
    }

    @ExportMessage
    Duration asDuration() throws UnsupportedMessageException {
        if (this.isDuration()) {
            return (Duration)this.obj;
        }
        throw UnsupportedMessageException.create();
    }

    @ExportMessage
    boolean isException() {
        return this.obj instanceof Throwable;
    }

    @ExportMessage
    ExceptionType getExceptionType(@Cached.Shared(value="error") @Cached BranchProfile error) throws UnsupportedMessageException {
        if (this.isException()) {
            return this.obj instanceof InterruptedException ? ExceptionType.INTERRUPT : ExceptionType.RUNTIME_ERROR;
        }
        error.enter();
        throw UnsupportedMessageException.create();
    }

    @ExportMessage
    boolean isExceptionIncompleteSource(@Cached.Shared(value="error") @Cached BranchProfile error) throws UnsupportedMessageException {
        if (this.isException()) {
            return false;
        }
        error.enter();
        throw UnsupportedMessageException.create();
    }

    @ExportMessage
    int getExceptionExitStatus(@Cached.Shared(value="error") @Cached BranchProfile error) throws UnsupportedMessageException {
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
    Object getExceptionMessage(@Cached.Shared(value="error") @Cached BranchProfile error) throws UnsupportedMessageException {
        String message;
        String string = message = this.isException() ? ((Throwable)this.obj).getMessage() : null;
        if (message != null) {
            return message;
        }
        error.enter();
        throw UnsupportedMessageException.create();
    }

    @ExportMessage
    @CompilerDirectives.TruffleBoundary
    boolean hasExceptionCause() {
        return this.isException() && ((Throwable)this.obj).getCause() instanceof AbstractTruffleException;
    }

    @ExportMessage
    @CompilerDirectives.TruffleBoundary
    Object getExceptionCause() throws UnsupportedMessageException {
        Throwable cause;
        if (this.isException() && (cause = ((Throwable)this.obj).getCause()) instanceof AbstractTruffleException) {
            return cause;
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
        }
        throw UnsupportedMessageException.create();
    }

    @ExportMessage
    RuntimeException throwException(@Cached.Shared(value="error") @Cached BranchProfile error) throws UnsupportedMessageException {
        if (this.isException()) {
            HostException ex = (HostException)this.extraInfo;
            if (ex == null) {
                ex = new HostException((Throwable)this.obj, this.context);
            }
            throw ex;
        }
        error.enter();
        throw UnsupportedMessageException.create();
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
        return HostObject.toStringImpl(this.context, this.obj, 0, allowSideEffects);
    }

    @CompilerDirectives.TruffleBoundary
    private static String toStringImpl(HostContext context, Object javaObject, int level, boolean allowSideEffects) {
        try {
            if (javaObject == null) {
                return "null";
            }
            if (javaObject.getClass().isArray()) {
                return HostObject.arrayToString(context, javaObject, level, allowSideEffects);
            }
            if (javaObject instanceof Class) {
                return HostObject.getTypeNameSafe((Class)javaObject);
            }
            if (allowSideEffects && context != null) {
                HostObject hostObject = HostObject.forObject(javaObject, context);
                try {
                    InteropLibrary thisLib = InteropLibrary.getUncached(hostObject);
                    if (thisLib.isBoolean(hostObject)) {
                        return Boolean.toString(thisLib.asBoolean(hostObject));
                    }
                    if (thisLib.isString(hostObject)) {
                        return thisLib.asString(hostObject);
                    }
                    if (thisLib.isNumber(hostObject)) {
                        assert (HostObject.isJavaPrimitiveNumber(javaObject)) : javaObject;
                        return javaObject.toString();
                    }
                    if (thisLib.isMemberInvocable(hostObject, "toString")) {
                        Object result = thisLib.invokeMember(hostObject, "toString", new Object[0]);
                        return InteropLibrary.getUncached().asString(result);
                    }
                }
                catch (InteropException interopException) {
                    // empty catch block
                }
            }
            return HostObject.getTypeNameSafe(javaObject.getClass());
        }
        catch (Throwable t) {
            throw context.hostToGuestException(t);
        }
    }

    @CompilerDirectives.TruffleBoundary
    private static String getTypeNameSafe(Class<?> type) {
        String typeName = type.getTypeName();
        int slash = typeName.indexOf(47);
        if (slash != -1) {
            return typeName.substring(0, slash);
        }
        return typeName;
    }

    private static String arrayToString(HostContext context, Object array, int level, boolean allowSideEffects) {
        CompilerAsserts.neverPartOfCompilation();
        if (array == null) {
            return "null";
        }
        if (level > 0) {
            return "[...]";
        }
        int iMax = Array.getLength(array) - 1;
        if (iMax == -1) {
            return "[]";
        }
        StringBuilder b = new StringBuilder();
        b.append('[');
        int i = 0;
        while (true) {
            Object arrayValue = Array.get(array, i);
            b.append(HostObject.toStringImpl(context, arrayValue, level + 1, allowSideEffects));
            if (i == iMax) {
                return b.append(']').toString();
            }
            b.append(", ");
            ++i;
        }
    }

    @ExportMessage
    boolean hasIterator(@Cached.Shared(value="isIterable") @Cached IsIterableNode isIterable, @Cached.Shared(value="isArray") @Cached IsArrayNode isArray) {
        return isIterable.execute(this) || isArray.execute(this);
    }

    @ExportMessage
    boolean isIterator(@Cached.Shared(value="isIterator") @Cached IsIteratorNode isIterator) {
        return isIterator.execute(this);
    }

    @ExportMessage
    boolean hasHashEntries(@Cached.Shared(value="isMap") @Cached IsMapNode isMap) {
        return isMap.execute(this);
    }

    @ExportMessage.Repeat(value={@ExportMessage(name="isHashEntryReadable"), @ExportMessage(name="isHashEntryModifiable"), @ExportMessage(name="isHashEntryRemovable")})
    boolean isHashEntryReadable(Object key, @Cached.Shared(value="isMap") @Cached IsMapNode isMap, @Cached.Shared(value="containsKey") @Cached ContainsKeyNode containsKey) {
        return isMap.execute(this) && containsKey.execute(this, key);
    }

    @ExportMessage
    boolean isHashEntryInsertable(Object key, @Cached.Shared(value="isMap") @Cached IsMapNode isMap, @Cached.Shared(value="containsKey") @Cached ContainsKeyNode containsKey) {
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
            return HostObject.forClass(javaType, this.context);
        }
        throw UnsupportedMessageException.create();
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
        }
        throw UnsupportedMessageException.create();
    }

    @ExportMessage
    @CompilerDirectives.TruffleBoundary
    Object getMetaSimpleName() throws UnsupportedMessageException {
        if (this.isClass()) {
            return this.asClass().getSimpleName();
        }
        throw UnsupportedMessageException.create();
    }

    @ExportMessage
    @CompilerDirectives.TruffleBoundary
    boolean isMetaInstance(Object other, @CachedLibrary(value="this") InteropLibrary library, @Cached.Shared(value="error") @Cached BranchProfile error) throws UnsupportedMessageException {
        if (this.isClass()) {
            HostLanguage language;
            Class<?> c = this.asClass();
            HostLanguage hostLanguage = language = this.context != null ? HostLanguage.get(library) : null;
            if (HostObject.isInstance(language, other)) {
                Object otherHostObj = HostObject.valueOf(language, other);
                if (otherHostObj == null) {
                    return false;
                }
                return c.isInstance(otherHostObj);
            }
            if (HostProxy.isProxyGuestObject(language, other)) {
                Proxy otherHost = HostProxy.toProxyHostObject(language, other);
                return c.isInstance(otherHost);
            }
            boolean canConvert = HostToTypeNode.canConvert(other, c, c, HostToTypeNode.allowsImplementation(this.context, c), this.context, 8, InteropLibrary.getFactory().getUncached(other), HostTargetMappingNode.getUncached());
            return canConvert;
        }
        error.enter();
        throw UnsupportedMessageException.create();
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
        }
        Class<?> superClass = this.asClass().getSuperclass();
        Class<?>[] interfaces = this.asClass().getInterfaces();
        HostObject[] metaObjects = new HostObject[superClass == null ? interfaces.length : interfaces.length + 1];
        int i = 0;
        if (superClass != null) {
            metaObjects[i++] = HostObject.forClass(superClass, this.context);
        }
        for (int j = 0; j < interfaces.length; ++j) {
            metaObjects[i++] = HostObject.forClass(interfaces[j], this.context);
        }
        return new TypesArray(metaObjects);
    }

    boolean isStaticClass() {
        return this.extraInfo instanceof Class;
    }

    Class<?> getObjectClass() {
        return this.obj == null ? null : this.obj.getClass();
    }

    Class<?> asStaticClass() {
        assert (this.isStaticClass());
        return (Class)this.obj;
    }

    Class<?> asClass() {
        assert (this.isClass());
        return (Class)this.obj;
    }

    Class<?> getLookupClass() {
        if (this.obj == null) {
            return null;
        }
        if (this.isStaticClass()) {
            return this.asStaticClass();
        }
        return this.obj.getClass();
    }

    HostClassCache getHostClassCache() {
        assert (this.context != null) : "host cache must not be used for null";
        return HostClassCache.forInstance(this);
    }

    @ExportMessage
    static int identityHashCode(HostObject receiver) {
        return System.identityHashCode(receiver.obj);
    }

    public boolean equals(Object o) {
        if (o instanceof HostObject) {
            HostObject other = (HostObject)o;
            return this.obj == other.obj && this.extraInfo == other.extraInfo && this.context == other.context;
        }
        return false;
    }

    public String toString() {
        if (this.obj == null) {
            return "null";
        }
        if (this.isClass()) {
            return "JavaClass[" + this.asClass().getTypeName() + "]";
        }
        return "JavaObject[" + this.obj + " (" + this.getObjectClass().getTypeName() + ")]";
    }

    static abstract class GuestToHostCalls {
        private GuestToHostCalls() {
        }

        @CompilerDirectives.TruffleBoundary(allowInlining=true)
        static int getListSize(HostObject hostObject) {
            return ((List)hostObject.obj).size();
        }

        @CompilerDirectives.TruffleBoundary
        static void setListElement(HostObject receiver, long index, Object hostValue) {
            List list = (List)receiver.obj;
            if (index == (long)list.size()) {
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
        static Object setMapEntryValue(HostObject receiver, Object value2) {
            return ((Map.Entry)receiver.obj).setValue(value2);
        }

        @CompilerDirectives.TruffleBoundary
        static Object getMapEntryKey(HostObject receiver) {
            return ((Map.Entry)receiver.obj).getKey();
        }

        @CompilerDirectives.TruffleBoundary
        static Object getMapEntryValue(HostObject receiver) {
            return ((Map.Entry)receiver.obj).getValue();
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
        static void putMapValue(HostObject receiver, Object key, Object value2) {
            ((Map)receiver.obj).put(key, value2);
        }

        @CompilerDirectives.TruffleBoundary
        static boolean removeMapValue(HostObject receiver, Object key) {
            Map map = (Map)receiver.obj;
            if (map.containsKey(key)) {
                map.remove(key);
                return true;
            }
            return false;
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

    @GenerateUncached
    static abstract class IsMapEntryNode
    extends Node {
        IsMapEntryNode() {
        }

        public abstract boolean execute(HostObject var1);

        @Specialization(guards={"receiver.obj == null"})
        public boolean doNull(HostObject receiver) {
            return false;
        }

        @Specialization(guards={"receiver.obj != null"})
        public boolean doDefault(HostObject receiver, @Cached(value="receiver.getHostClassCache().isMapAccess()", allowUncached=true) boolean isMapAccess) {
            assert (receiver.getHostClassCache().isMapAccess() == isMapAccess);
            return isMapAccess && receiver.obj instanceof Map.Entry;
        }
    }

    @GenerateUncached
    static abstract class ContainsKeyNode
    extends Node {
        ContainsKeyNode() {
        }

        public abstract boolean execute(HostObject var1, Object var2);

        @Specialization(guards={"isMap.execute(receiver)"}, limit="1")
        protected static boolean doMap(HostObject receiver, Object key, @Cached.Shared(value="isMap") @Cached IsMapNode isMap, @Cached HostToTypeNode toHost, @Cached BranchProfile error) {
            Object hostKey;
            try {
                hostKey = toHost.execute(receiver.context, key, Object.class, null, true);
            }
            catch (RuntimeException e) {
                error.enter();
                RuntimeException ee = HostObject.unboxEngineException(receiver, e);
                if (ee != null) {
                    return false;
                }
                throw e;
            }
            try {
                return GuestToHostCalls.containsMapKey(receiver, hostKey);
            }
            catch (Throwable t) {
                error.enter();
                throw receiver.context.hostToGuestException(t);
            }
        }

        @Specialization(guards={"!isMap.execute(receiver)"}, limit="1")
        protected static boolean doNotMap(HostObject receiver, Object key, @Cached.Shared(value="isMap") @Cached IsMapNode isMap) {
            return false;
        }
    }

    @GenerateUncached
    static abstract class IsMapNode
    extends Node {
        IsMapNode() {
        }

        public abstract boolean execute(HostObject var1);

        @Specialization(guards={"receiver.obj == null"})
        public boolean doNull(HostObject receiver) {
            return false;
        }

        @Specialization(guards={"receiver.obj != null"})
        public boolean doDefault(HostObject receiver, @Cached(value="receiver.getHostClassCache().isMapAccess()", allowUncached=true) boolean isMapAccess) {
            assert (receiver.getHostClassCache().isMapAccess() == isMapAccess);
            return isMapAccess && receiver.obj instanceof Map;
        }
    }

    @GenerateUncached
    static abstract class IsIteratorNode
    extends Node {
        IsIteratorNode() {
        }

        public abstract boolean execute(HostObject var1);

        @Specialization(guards={"receiver.obj == null"})
        public boolean doNull(HostObject receiver) {
            return false;
        }

        @Specialization(guards={"receiver.obj != null"})
        public boolean doDefault(HostObject receiver, @Cached(value="receiver.getHostClassCache().isIteratorAccess()", allowUncached=true) boolean isIteratorAccess) {
            assert (receiver.getHostClassCache().isIteratorAccess() == isIteratorAccess);
            return isIteratorAccess && receiver.obj instanceof Iterator;
        }
    }

    @GenerateUncached
    static abstract class IsIterableNode
    extends Node {
        IsIterableNode() {
        }

        public abstract boolean execute(HostObject var1);

        @Specialization(guards={"receiver.obj == null"})
        public boolean doNull(HostObject receiver) {
            return false;
        }

        @Specialization(guards={"receiver.obj != null"})
        public boolean doDefault(HostObject receiver, @Cached(value="receiver.getHostClassCache().isIterableAccess()", allowUncached=true) boolean isIterableAccess) {
            assert (receiver.getHostClassCache().isIterableAccess() == isIterableAccess);
            return isIterableAccess && receiver.obj instanceof Iterable;
        }
    }

    @GenerateUncached
    static abstract class IsBufferNode
    extends Node {
        IsBufferNode() {
        }

        public abstract boolean execute(HostObject var1);

        @Specialization(guards={"receiver.obj == null"})
        public boolean doNull(HostObject receiver) {
            return false;
        }

        @Specialization(guards={"receiver.obj != null"})
        public boolean doDefault(HostObject receiver, @Cached(value="receiver.getHostClassCache().isBufferAccess()", allowUncached=true) boolean isBufferAccess) {
            assert (receiver.getHostClassCache().isBufferAccess() == isBufferAccess);
            return isBufferAccess && ByteBuffer.class.isAssignableFrom(receiver.obj.getClass());
        }
    }

    @GenerateUncached
    static abstract class IsArrayNode
    extends Node {
        IsArrayNode() {
        }

        public abstract boolean execute(HostObject var1);

        @Specialization(guards={"receiver.obj == null"})
        public boolean doNull(HostObject receiver) {
            return false;
        }

        @Specialization(guards={"receiver.obj != null"})
        public boolean doDefault(HostObject receiver, @Cached(value="receiver.getHostClassCache().isArrayAccess()", allowUncached=true) boolean isArrayAccess) {
            assert (receiver.getHostClassCache().isArrayAccess() == isArrayAccess);
            return isArrayAccess && receiver.obj.getClass().isArray();
        }
    }

    @GenerateUncached
    static abstract class IsListNode
    extends Node {
        IsListNode() {
        }

        public abstract boolean execute(HostObject var1);

        @Specialization(guards={"receiver.obj == null"})
        public boolean doNull(HostObject receiver) {
            return false;
        }

        @Specialization(guards={"receiver.obj != null"})
        public boolean doDefault(HostObject receiver, @Cached(value="receiver.getHostClassCache().isListAccess()", allowUncached=true) boolean isListAccess) {
            assert (receiver.getHostClassCache().isListAccess() == isListAccess);
            return isListAccess && receiver.obj instanceof List;
        }
    }

    @GenerateUncached
    static abstract class WriteFieldNode
    extends Node {
        static final int LIMIT = 3;

        WriteFieldNode() {
        }

        public abstract void execute(HostFieldDesc var1, HostObject var2, Object var3) throws UnsupportedTypeException, UnknownIdentifierException;

        @Specialization(guards={"field == cachedField"}, limit="LIMIT")
        static void doCached(HostFieldDesc field, HostObject object, Object rawValue, @Cached(value="field") HostFieldDesc cachedField, @Cached HostToTypeNode toHost, @Cached BranchProfile error) throws UnsupportedTypeException, UnknownIdentifierException {
            if (field.isFinal()) {
                error.enter();
                throw UnknownIdentifierException.create(field.getName());
            }
            try {
                Object value2 = toHost.execute(object.context, rawValue, cachedField.getType(), cachedField.getGenericType(), true);
                cachedField.set(object.obj, value2);
            }
            catch (RuntimeException e) {
                error.enter();
                RuntimeException ee = HostObject.unboxEngineException(object, e);
                if (ee != null) {
                    throw HostInteropErrors.unsupportedTypeException(rawValue, (Throwable)ee);
                }
                throw e;
            }
        }

        @Specialization(replaces={"doCached"})
        @CompilerDirectives.TruffleBoundary
        static void doUncached(HostFieldDesc field, HostObject object, Object rawValue, @Cached HostToTypeNode toHost) throws UnsupportedTypeException, UnknownIdentifierException {
            if (field.isFinal()) {
                throw UnknownIdentifierException.create(field.getName());
            }
            try {
                Object val = toHost.execute(object.context, rawValue, field.getType(), field.getGenericType(), true);
                field.set(object.obj, val);
            }
            catch (RuntimeException e) {
                RuntimeException ee = HostObject.unboxEngineException(object, e);
                if (ee != null) {
                    throw HostInteropErrors.unsupportedTypeException(rawValue, (Throwable)ee);
                }
                throw e;
            }
        }
    }

    @GenerateUncached
    static abstract class ReadFieldNode
    extends Node {
        static final int LIMIT = 3;

        ReadFieldNode() {
        }

        public abstract Object execute(HostFieldDesc var1, HostObject var2);

        @Specialization(guards={"field == cachedField"}, limit="LIMIT")
        static Object doCached(HostFieldDesc field, HostObject object, @Cached(value="field") HostFieldDesc cachedField, @Cached HostContext.ToGuestValueNode toGuest) {
            Object val = cachedField.get(object.obj);
            return toGuest.execute(object.context, val);
        }

        @Specialization(replaces={"doCached"})
        @CompilerDirectives.TruffleBoundary
        static Object doUncached(HostFieldDesc field, HostObject object, @Cached HostContext.ToGuestValueNode toGuest) {
            Object val = field.get(object.obj);
            return toGuest.execute(object.context, val);
        }
    }

    @GenerateUncached
    static abstract class LookupMethodNode
    extends Node {
        static final int LIMIT = 3;

        LookupMethodNode() {
        }

        public abstract HostMethodDesc execute(HostObject var1, Class<?> var2, String var3, boolean var4);

        @Specialization(guards={"onlyStatic == cachedStatic", "clazz == cachedClazz", "cachedName.equals(name)"}, limit="LIMIT")
        HostMethodDesc doCached(HostObject receiver, Class<?> clazz, String name, boolean onlyStatic, @Cached(value="onlyStatic") boolean cachedStatic, @Cached(value="clazz") Class<?> cachedClazz, @Cached(value="name") String cachedName, @Cached(value="doUncached(receiver, clazz, name, onlyStatic)") HostMethodDesc cachedMethod) {
            assert (cachedMethod == this.doUncached(receiver, clazz, name, onlyStatic));
            return cachedMethod;
        }

        @Specialization(replaces={"doCached"})
        @CompilerDirectives.TruffleBoundary
        HostMethodDesc doUncached(HostObject receiver, Class<?> clazz, String name, boolean onlyStatic) {
            return HostInteropReflect.findMethod(receiver.context, clazz, name, onlyStatic);
        }
    }

    @GenerateUncached
    static abstract class LookupInnerClassNode
    extends Node {
        static final int LIMIT = 3;

        LookupInnerClassNode() {
        }

        public abstract Class<?> execute(Class<?> var1, String var2);

        @Specialization(guards={"clazz == cachedClazz", "cachedName.equals(name)"}, limit="LIMIT")
        Class<?> doCached(Class<?> clazz, String name, @Cached(value="clazz") Class<?> cachedClazz, @Cached(value="name") String cachedName, @Cached(value="doUncached(clazz, name)") Class<?> cachedInnerClass) {
            assert (cachedInnerClass == this.doUncached(clazz, name));
            return cachedInnerClass;
        }

        @Specialization(replaces={"doCached"})
        @CompilerDirectives.TruffleBoundary
        Class<?> doUncached(Class<?> clazz, String name) {
            return HostInteropReflect.findInnerClass(clazz, name);
        }
    }

    @GenerateUncached
    static abstract class LookupFunctionalMethodNode
    extends Node {
        static final int LIMIT = 3;

        LookupFunctionalMethodNode() {
        }

        public abstract HostMethodDesc execute(HostObject var1, Class<?> var2);

        @Specialization(guards={"clazz == cachedClazz"}, limit="LIMIT")
        HostMethodDesc doCached(HostObject object, Class<?> clazz, @Cached(value="clazz") Class<?> cachedClazz, @Cached(value="doUncached(object, clazz)") HostMethodDesc cachedMethod) {
            assert (cachedMethod == LookupFunctionalMethodNode.doUncached(object, clazz));
            return cachedMethod;
        }

        @Specialization(replaces={"doCached"})
        @CompilerDirectives.TruffleBoundary
        static HostMethodDesc doUncached(HostObject object, Class<?> clazz) {
            return HostClassDesc.forClass(object.context, clazz).getFunctionalMethod();
        }
    }

    @GenerateUncached
    static abstract class LookupFieldNode
    extends Node {
        static final int LIMIT = 3;

        LookupFieldNode() {
        }

        public abstract HostFieldDesc execute(HostObject var1, Class<?> var2, String var3, boolean var4);

        @Specialization(guards={"onlyStatic == cachedStatic", "clazz == cachedClazz", "cachedName.equals(name)"}, limit="LIMIT")
        HostFieldDesc doCached(HostObject receiver, Class<?> clazz, String name, boolean onlyStatic, @Cached(value="onlyStatic") boolean cachedStatic, @Cached(value="clazz") Class<?> cachedClazz, @Cached(value="name") String cachedName, @Cached(value="doUncached(receiver, clazz, name, onlyStatic)") HostFieldDesc cachedField) {
            assert (cachedField == this.doUncached(receiver, clazz, name, onlyStatic));
            return cachedField;
        }

        @Specialization(replaces={"doCached"})
        @CompilerDirectives.TruffleBoundary
        HostFieldDesc doUncached(HostObject receiver, Class<?> clazz, String name, boolean onlyStatic) {
            return HostInteropReflect.findField(receiver.context, clazz, name, onlyStatic);
        }
    }

    @GenerateUncached
    static abstract class LookupConstructorNode
    extends Node {
        static final int LIMIT = 3;

        LookupConstructorNode() {
        }

        public abstract HostMethodDesc execute(HostObject var1, Class<?> var2);

        @Specialization(guards={"clazz == cachedClazz"}, limit="LIMIT")
        HostMethodDesc doCached(HostObject receiver, Class<?> clazz, @Cached(value="clazz") Class<?> cachedClazz, @Cached(value="doUncached(receiver, clazz)") HostMethodDesc cachedMethod) {
            assert (cachedMethod == this.doUncached(receiver, clazz));
            return cachedMethod;
        }

        @Specialization(replaces={"doCached"})
        @CompilerDirectives.TruffleBoundary
        HostMethodDesc doUncached(HostObject receiver, Class<?> clazz) {
            return HostClassDesc.forClass(receiver.context, clazz).lookupConstructor();
        }
    }

    @GenerateUncached
    static abstract class ArrayGet
    extends Node {
        ArrayGet() {
        }

        protected abstract Object execute(Object var1, int var2);

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
    static abstract class ArraySet
    extends Node {
        ArraySet() {
        }

        protected abstract void execute(Object var1, int var2, Object var3);

        @Specialization
        static void doBoolean(boolean[] array, int index, boolean value2) {
            array[index] = value2;
        }

        @Specialization
        static void doByte(byte[] array, int index, byte value2) {
            array[index] = value2;
        }

        @Specialization
        static void doShort(short[] array, int index, short value2) {
            array[index] = value2;
        }

        @Specialization
        static void doChar(char[] array, int index, char value2) {
            array[index] = value2;
        }

        @Specialization
        static void doInt(int[] array, int index, int value2) {
            array[index] = value2;
        }

        @Specialization
        static void doLong(long[] array, int index, long value2) {
            array[index] = value2;
        }

        @Specialization
        static void doFloat(float[] array, int index, float value2) {
            array[index] = value2;
        }

        @Specialization
        static void doDouble(double[] array, int index, double value2) {
            array[index] = value2;
        }

        @Specialization
        static void doObject(Object[] array, int index, Object value2) {
            array[index] = value2;
        }
    }

    @ExportMessage
    static final class IsIdenticalOrUndefined {
        IsIdenticalOrUndefined() {
        }

        @Specialization
        static TriState doHostObject(HostObject receiver, HostObject other) {
            return TriState.valueOf(receiver.obj == other.obj && receiver.isStaticClass() == other.isStaticClass());
        }

        @Fallback
        static TriState doOther(HostObject receiver, Object other) {
            return TriState.UNDEFINED;
        }
    }

    @ExportLibrary(value=InteropLibrary.class)
    static final class TypesArray
    implements TruffleObject {
        @CompilerDirectives.CompilationFinal(dimensions=1)
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
            return 0L <= idx && idx < (long)this.types.length;
        }

        @ExportMessage
        Object readArrayElement(long idx, @Cached BranchProfile error) throws InvalidArrayIndexException {
            if (!this.isArrayElementReadable(idx)) {
                error.enter();
                throw InvalidArrayIndexException.create(idx);
            }
            return this.types[(int)idx];
        }
    }

    @ExportMessage
    static abstract class GetHashEntriesIterator {
        GetHashEntriesIterator() {
        }

        @Specialization(guards={"isMap.execute(receiver)"}, limit="1")
        protected static Object doMap(HostObject receiver, @Cached.Shared(value="isMap") @Cached IsMapNode isMap, @Cached.Shared(value="toGuest") @Cached HostContext.ToGuestValueNode toGuest, @Cached.Shared(value="error") @Cached BranchProfile error) {
            Object hostValue;
            try {
                hostValue = GuestToHostCalls.getEntriesIterator(receiver);
            }
            catch (Throwable t) {
                error.enter();
                throw receiver.context.hostToGuestException(t);
            }
            return toGuest.execute(receiver.context, hostValue);
        }

        @Specialization(guards={"!isMap.execute(receiver)"}, limit="1")
        protected static Object doNotMap(HostObject receiver, @Cached.Shared(value="isMap") @Cached IsMapNode isMap) throws UnsupportedMessageException {
            throw UnsupportedMessageException.create();
        }
    }

    @ExportMessage
    static abstract class RemoveHashEntry {
        RemoveHashEntry() {
        }

        @Specialization(guards={"isMap.execute(receiver)"}, limit="1")
        protected static void doMap(HostObject receiver, Object key, @Cached.Shared(value="isMap") @Cached IsMapNode isMap, @Cached.Shared(value="toHost") @Cached HostToTypeNode toHost, @Cached.Shared(value="error") @Cached BranchProfile error) throws UnknownKeyException {
            boolean removed;
            Object hostKey;
            try {
                hostKey = toHost.execute(receiver.context, key, Object.class, null, true);
            }
            catch (RuntimeException e) {
                error.enter();
                RuntimeException ee = HostObject.unboxEngineException(receiver, e);
                if (ee != null) {
                    throw UnknownKeyException.create(key);
                }
                throw e;
            }
            try {
                removed = GuestToHostCalls.removeMapValue(receiver, hostKey);
            }
            catch (Throwable t) {
                error.enter();
                throw receiver.context.hostToGuestException(t);
            }
            if (!removed) {
                error.enter();
                throw UnknownKeyException.create(key);
            }
        }

        @Specialization(guards={"!isMap.execute(receiver)"}, limit="1")
        protected static void doNotMap(HostObject receiver, Object key, @Cached.Shared(value="isMap") @Cached IsMapNode isMap) throws UnsupportedMessageException {
            throw UnsupportedMessageException.create();
        }
    }

    @ExportMessage
    static abstract class WriteHashEntry {
        WriteHashEntry() {
        }

        @Specialization(guards={"isMap.execute(receiver)"}, limit="1")
        protected static void doMap(HostObject receiver, Object key, Object value2, @Cached.Shared(value="isMap") @Cached IsMapNode isMap, @Cached.Shared(value="toHost") @Cached HostToTypeNode toHost, @Cached.Shared(value="error") @Cached BranchProfile error) throws UnsupportedTypeException {
            Object hostValue;
            Object hostKey;
            try {
                hostKey = toHost.execute(receiver.context, key, Object.class, null, true);
            }
            catch (RuntimeException e) {
                error.enter();
                RuntimeException ee = HostObject.unboxEngineException(receiver, e);
                if (ee != null) {
                    throw UnsupportedTypeException.create(new Object[]{key}, HostObject.getMessage(ee));
                }
                throw e;
            }
            try {
                hostValue = toHost.execute(receiver.context, value2, Object.class, null, true);
            }
            catch (RuntimeException e) {
                error.enter();
                RuntimeException ee = HostObject.unboxEngineException(receiver, e);
                if (ee != null) {
                    throw UnsupportedTypeException.create(new Object[]{value2}, HostObject.getMessage(ee));
                }
                throw e;
            }
            try {
                GuestToHostCalls.putMapValue(receiver, hostKey, hostValue);
            }
            catch (Throwable t) {
                error.enter();
                throw receiver.context.hostToGuestException(t);
            }
        }

        @Specialization(guards={"!isMap.execute(receiver)"}, limit="1")
        protected static void doNotMap(HostObject receiver, Object key, Object value2, @Cached.Shared(value="isMap") @Cached IsMapNode isMap) throws UnsupportedMessageException {
            throw UnsupportedMessageException.create();
        }
    }

    @ExportMessage
    static abstract class ReadHashValue {
        private static final Object UNDEFINED = new Object();

        ReadHashValue() {
        }

        @Specialization(guards={"isMap.execute(receiver)"}, limit="1")
        protected static Object doMap(HostObject receiver, Object key, @Cached.Shared(value="isMap") @Cached IsMapNode isMap, @Cached.Shared(value="toHost") @Cached HostToTypeNode toHost, @Cached.Shared(value="toGuest") @Cached HostContext.ToGuestValueNode toGuest, @Cached.Shared(value="error") @Cached BranchProfile error) throws UnknownKeyException {
            Object hostResult;
            Object hostKey;
            try {
                hostKey = toHost.execute(receiver.context, key, Object.class, null, true);
            }
            catch (RuntimeException e) {
                error.enter();
                RuntimeException ee = HostObject.unboxEngineException(receiver, e);
                if (ee != null) {
                    throw UnknownKeyException.create(key);
                }
                throw e;
            }
            try {
                hostResult = GuestToHostCalls.getMapValue(receiver, hostKey, UNDEFINED);
            }
            catch (Throwable t) {
                error.enter();
                throw receiver.context.hostToGuestException(t);
            }
            if (hostResult == UNDEFINED) {
                error.enter();
                throw UnknownKeyException.create(key);
            }
            return toGuest.execute(receiver.context, hostResult);
        }

        @Specialization(guards={"!isMap.execute(receiver)"}, limit="1")
        protected static Object doNotMap(HostObject receiver, Object key, @Cached.Shared(value="isMap") @Cached IsMapNode isMap) throws UnsupportedMessageException {
            throw UnsupportedMessageException.create();
        }
    }

    @ExportMessage
    static abstract class GetHashSize {
        GetHashSize() {
        }

        @Specialization(guards={"isMap.execute(receiver)"}, limit="1")
        protected static long doMap(HostObject receiver, @Cached.Shared(value="isMap") @Cached IsMapNode isMap, @Cached.Shared(value="error") @Cached BranchProfile error) {
            try {
                return GuestToHostCalls.getMapSize(receiver);
            }
            catch (Throwable t) {
                error.enter();
                throw receiver.context.hostToGuestException(t);
            }
        }

        @Specialization(guards={"!isMap.execute(receiver)"}, limit="1")
        protected static long doNotMap(HostObject receiver, @Cached.Shared(value="isMap") @Cached IsMapNode isMap) throws UnsupportedMessageException {
            throw UnsupportedMessageException.create();
        }
    }

    @ExportMessage
    static abstract class GetIteratorNextElement {
        GetIteratorNextElement() {
        }

        @Specialization(guards={"isIterator.execute(receiver)"}, limit="1")
        protected static Object doIterator(HostObject receiver, @Cached.Shared(value="isIterator") @Cached IsIteratorNode isIterator, @Cached.Shared(value="toGuest") @Cached HostContext.ToGuestValueNode toGuest, @Cached.Shared(value="error") @Cached BranchProfile error, @Cached.Exclusive @Cached BranchProfile stopIteration) throws StopIterationException {
            Object next;
            try {
                next = GuestToHostCalls.getIteratorNext(receiver);
            }
            catch (NoSuchElementException e) {
                stopIteration.enter();
                throw StopIterationException.create();
            }
            catch (Throwable t) {
                error.enter();
                throw receiver.context.hostToGuestException(t);
            }
            return toGuest.execute(receiver.context, next);
        }

        @Specialization(guards={"!isIterator.execute(receiver)"}, limit="1")
        protected static Object doNotIterator(HostObject receiver, @Cached.Shared(value="isIterator") @Cached IsIteratorNode isIterator) throws UnsupportedMessageException {
            throw UnsupportedMessageException.create();
        }
    }

    @ExportMessage
    static abstract class HasIteratorNextElement {
        HasIteratorNextElement() {
        }

        @Specialization(guards={"isIterator.execute(receiver)"}, limit="1")
        protected static boolean doIterator(HostObject receiver, @Cached.Shared(value="isIterator") @Cached IsIteratorNode isIterator, @Cached.Shared(value="error") @Cached BranchProfile error) {
            try {
                return GuestToHostCalls.hasIteratorNext(receiver);
            }
            catch (Throwable t) {
                error.enter();
                throw receiver.context.hostToGuestException(t);
            }
        }

        @Specialization(guards={"!isIterator.execute(receiver)"}, limit="1")
        protected static boolean doNotIterator(HostObject receiver, @Cached.Shared(value="isIterator") @Cached IsIteratorNode isIterator) throws UnsupportedMessageException {
            throw UnsupportedMessageException.create();
        }
    }

    @ExportMessage
    static abstract class GetIterator {
        GetIterator() {
        }

        @Specialization(guards={"isArray.execute(receiver)"}, limit="1")
        protected static Object doArray(HostObject receiver, @Cached.Shared(value="isArray") @Cached IsArrayNode isArray, @Cached.Shared(value="toGuest") @Cached HostContext.ToGuestValueNode toGuest) {
            return toGuest.execute(receiver.context, GetIterator.arrayIteratorImpl(receiver));
        }

        @CompilerDirectives.TruffleBoundary
        private static Object arrayIteratorImpl(Object receiver) {
            return HostAccessor.INTEROP.createDefaultIterator(receiver);
        }

        @Specialization(guards={"isIterable.execute(receiver)"}, limit="1")
        protected static Object doIterable(HostObject receiver, @Cached.Shared(value="isIterable") @Cached IsIterableNode isIterable, @Cached.Shared(value="toGuest") @Cached HostContext.ToGuestValueNode toGuest, @Cached.Shared(value="error") @Cached BranchProfile error) {
            Object hostValue;
            try {
                hostValue = GuestToHostCalls.getIterator(receiver);
            }
            catch (Throwable t) {
                error.enter();
                throw receiver.context.hostToGuestException(t);
            }
            return toGuest.execute(receiver.context, hostValue);
        }

        @Specialization(guards={"!isArray.execute(receiver)", "!isIterable.execute(receiver)"}, limit="1")
        protected static Object doNotArrayOrIterable(HostObject receiver, @Cached.Shared(value="isArray") @Cached IsArrayNode isArray, @Cached.Shared(value="isIterable") @Cached IsIterableNode isIterable) throws UnsupportedMessageException {
            throw UnsupportedMessageException.create();
        }
    }

    @ExportMessage
    static class Instantiate {
        Instantiate() {
        }

        @Specialization(guards={"!receiver.isClass()"})
        static Object doUnsupported(HostObject receiver, Object[] args) throws UnsupportedMessageException {
            throw UnsupportedMessageException.create();
        }

        @Specialization(guards={"receiver.isArrayClass()"})
        static Object doArrayCached(HostObject receiver, Object[] args, @CachedLibrary(limit="1") InteropLibrary indexes, @Cached.Shared(value="error") @Cached BranchProfile error) throws UnsupportedMessageException, UnsupportedTypeException, ArityException {
            if (args.length != 1) {
                error.enter();
                throw ArityException.create(1, 1, args.length);
            }
            Object arg0 = args[0];
            if (!indexes.fitsInInt(arg0)) {
                error.enter();
                throw UnsupportedTypeException.create(args);
            }
            int length = indexes.asInt(arg0);
            Object array = Array.newInstance(receiver.asClass().getComponentType(), length);
            return HostObject.forObject(array, receiver.context);
        }

        @Specialization(guards={"receiver.isDefaultClass()"})
        static Object doObjectCached(HostObject receiver, Object[] arguments, @Cached.Shared(value="lookupConstructor") @Cached LookupConstructorNode lookupConstructor, @Cached.Shared(value="hostExecute") @Cached HostExecuteNode executeMethod, @Cached.Shared(value="error") @Cached BranchProfile error) throws UnsupportedMessageException, UnsupportedTypeException, ArityException {
            assert (!receiver.isArrayClass());
            HostMethodDesc constructor = lookupConstructor.execute(receiver, receiver.asClass());
            if (constructor != null) {
                return executeMethod.execute(constructor, null, arguments, receiver.context);
            }
            error.enter();
            throw UnsupportedMessageException.create();
        }
    }

    @ExportMessage
    static class IsInstantiable {
        IsInstantiable() {
        }

        @Specialization(guards={"!receiver.isClass()"})
        static boolean doUnsupported(HostObject receiver) {
            return false;
        }

        @Specialization(guards={"receiver.isArrayClass()"})
        static boolean doArrayCached(HostObject receiver) {
            return true;
        }

        @Specialization(guards={"receiver.isDefaultClass()"})
        static boolean doObjectCached(HostObject receiver, @Cached.Shared(value="lookupConstructor") @Cached LookupConstructorNode lookupConstructor) {
            return lookupConstructor.execute(receiver, receiver.asClass()) != null;
        }
    }

    @ExportMessage
    static abstract class GetArraySize {
        GetArraySize() {
        }

        @Specialization(guards={"isArray.execute(receiver)"}, limit="1")
        protected static long doArray(HostObject receiver, @Cached.Shared(value="isArray") @Cached IsArrayNode isArray) {
            return Array.getLength(receiver.obj);
        }

        @Specialization(guards={"isList.execute(receiver)"}, limit="1")
        protected static long doList(HostObject receiver, @Cached.Shared(value="isList") @Cached IsListNode isList, @Cached.Shared(value="error") @Cached BranchProfile error) {
            try {
                return GuestToHostCalls.getListSize(receiver);
            }
            catch (Throwable t) {
                error.enter();
                throw receiver.context.hostToGuestException(t);
            }
        }

        @Specialization(guards={"isMapEntry.execute(receiver)"}, limit="1")
        protected static long doMapEntry(HostObject receiver, @Cached.Shared(value="isMapEntry") @Cached IsMapEntryNode isMapEntry) {
            return 2L;
        }

        @Specialization(guards={"!isArray.execute(receiver)", "!isList.execute(receiver)", "!isMapEntry.execute(receiver)"}, limit="1")
        protected static long doNotArrayOrList(HostObject receiver, @Cached.Shared(value="isArray") @Cached IsArrayNode isArray, @Cached.Shared(value="isList") @Cached IsListNode isList, @Cached.Shared(value="isMapEntry") @Cached IsMapEntryNode isMapEntry) throws UnsupportedMessageException {
            throw UnsupportedMessageException.create();
        }
    }

    @ExportMessage
    static abstract class ReadArrayElement {
        ReadArrayElement() {
        }

        @Specialization(guards={"isArray.execute(receiver)"}, limit="1")
        protected static Object doArray(HostObject receiver, long index, @Cached ArrayGet arrayGet, @Cached.Shared(value="isArray") @Cached IsArrayNode isArray, @Cached.Shared(value="toGuest") @Cached HostContext.ToGuestValueNode toGuest, @Cached.Shared(value="error") @Cached BranchProfile error) throws InvalidArrayIndexException {
            if (index < 0L || Integer.MAX_VALUE < index) {
                error.enter();
                throw InvalidArrayIndexException.create(index);
            }
            Object obj = receiver.obj;
            Object val = null;
            try {
                val = arrayGet.execute(obj, (int)index);
            }
            catch (ArrayIndexOutOfBoundsException outOfBounds) {
                error.enter();
                throw InvalidArrayIndexException.create(index);
            }
            return toGuest.execute(receiver.context, val);
        }

        @CompilerDirectives.TruffleBoundary
        @Specialization(guards={"isList.execute(receiver)"}, limit="1")
        protected static Object doList(HostObject receiver, long index, @Cached.Shared(value="isList") @Cached IsListNode isList, @Cached.Shared(value="toGuest") @Cached HostContext.ToGuestValueNode toGuest, @Cached.Shared(value="error") @Cached BranchProfile error) throws InvalidArrayIndexException {
            Object hostValue;
            if (index < 0L || Integer.MAX_VALUE < index) {
                error.enter();
                throw InvalidArrayIndexException.create(index);
            }
            try {
                hostValue = GuestToHostCalls.readListElement(receiver, index);
            }
            catch (IndexOutOfBoundsException e) {
                error.enter();
                throw InvalidArrayIndexException.create(index);
            }
            catch (Throwable t) {
                error.enter();
                throw receiver.context.hostToGuestException(t);
            }
            return toGuest.execute(receiver.context, hostValue);
        }

        @Specialization(guards={"isMapEntry.execute(receiver)"}, limit="1")
        protected static Object doMapEntry(HostObject receiver, long index, @Cached.Shared(value="isMapEntry") @Cached IsMapEntryNode isMapEntry, @Cached.Shared(value="toGuest") @Cached HostContext.ToGuestValueNode toGuest, @Cached.Shared(value="error") @Cached BranchProfile error) throws InvalidArrayIndexException {
            Object hostResult;
            if (index == 0L) {
                try {
                    hostResult = GuestToHostCalls.getMapEntryKey(receiver);
                }
                catch (Throwable t) {
                    error.enter();
                    throw receiver.context.hostToGuestException(t);
                }
            } else if (index == 1L) {
                try {
                    hostResult = GuestToHostCalls.getMapEntryValue(receiver);
                }
                catch (Throwable t) {
                    error.enter();
                    throw receiver.context.hostToGuestException(t);
                }
            } else {
                error.enter();
                throw InvalidArrayIndexException.create(index);
            }
            return toGuest.execute(receiver.context, hostResult);
        }

        @Specialization(guards={"!isArray.execute(receiver)", "!isList.execute(receiver)", "!isMapEntry.execute(receiver)"}, limit="1")
        protected static Object doNotArrayOrList(HostObject receiver, long index, @Cached.Shared(value="isArray") @Cached IsArrayNode isArray, @Cached.Shared(value="isList") @Cached IsListNode isList, @Cached.Shared(value="isMapEntry") @Cached IsMapEntryNode isMapEntry) throws UnsupportedMessageException {
            throw UnsupportedMessageException.create();
        }
    }

    @ExportMessage
    static class RemoveArrayElement {
        RemoveArrayElement() {
        }

        @Specialization(guards={"isList.execute(receiver)"}, limit="1")
        static void doList(HostObject receiver, long index, @Cached.Shared(value="isList") @Cached IsListNode isList, @Cached.Shared(value="error") @Cached BranchProfile error) throws InvalidArrayIndexException {
            if (index < 0L || Integer.MAX_VALUE < index) {
                error.enter();
                throw InvalidArrayIndexException.create(index);
            }
            try {
                GuestToHostCalls.removeListElement(receiver, index);
            }
            catch (IndexOutOfBoundsException outOfBounds) {
                error.enter();
                throw InvalidArrayIndexException.create(index);
            }
            catch (Throwable t) {
                error.enter();
                throw receiver.context.hostToGuestException(t);
            }
        }

        @Specialization(guards={"!isList.execute(receiver)"}, limit="1")
        static void doOther(HostObject receiver, long index, @Cached.Shared(value="isList") @Cached IsListNode isList) throws UnsupportedMessageException {
            throw UnsupportedMessageException.create();
        }
    }

    @ExportMessage
    static class IsArrayElementRemovable {
        IsArrayElementRemovable() {
        }

        @Specialization(guards={"isList.execute(receiver)"}, limit="1")
        static boolean doList(HostObject receiver, long index, @Cached.Shared(value="isList") @Cached IsListNode isList, @Cached.Shared(value="error") @Cached BranchProfile error) {
            try {
                return index >= 0L && index < (long)GuestToHostCalls.getListSize(receiver);
            }
            catch (Throwable t) {
                error.enter();
                throw receiver.context.hostToGuestException(t);
            }
        }

        @Specialization(guards={"!isList.execute(receiver)"}, limit="1")
        static boolean doOther(HostObject receiver, long index, @Cached.Shared(value="isList") @Cached IsListNode isList) {
            return false;
        }
    }

    @ExportMessage
    static class WriteArrayElement {
        WriteArrayElement() {
        }

        @Specialization(guards={"isArray.execute(receiver)"}, limit="1")
        static void doArray(HostObject receiver, long index, Object value2, @Cached.Shared(value="toHost") @Cached HostToTypeNode toHostNode, @Cached.Shared(value="isArray") @Cached IsArrayNode isArray, @Cached ArraySet arraySet, @Cached.Shared(value="error") @Cached BranchProfile error) throws InvalidArrayIndexException, UnsupportedTypeException {
            Object javaValue;
            if (index < 0L || Integer.MAX_VALUE < index) {
                error.enter();
                throw InvalidArrayIndexException.create(index);
            }
            Object obj = receiver.obj;
            try {
                javaValue = toHostNode.execute(receiver.context, value2, obj.getClass().getComponentType(), null, true);
            }
            catch (RuntimeException e) {
                error.enter();
                RuntimeException ee = HostObject.unboxEngineException(receiver, e);
                if (ee != null) {
                    throw UnsupportedTypeException.create(new Object[]{value2}, HostObject.getMessage(ee));
                }
                throw e;
            }
            try {
                arraySet.execute(obj, (int)index, javaValue);
            }
            catch (ArrayIndexOutOfBoundsException e) {
                error.enter();
                throw InvalidArrayIndexException.create(index);
            }
        }

        @Specialization(guards={"isList.execute(receiver)"}, limit="1")
        static void doList(HostObject receiver, long index, Object value2, @Cached.Shared(value="isList") @Cached IsListNode isList, @Cached.Shared(value="toHost") @Cached HostToTypeNode toHostNode, @Cached.Shared(value="error") @Cached BranchProfile error) throws InvalidArrayIndexException, UnsupportedTypeException {
            Object javaValue;
            if (index < 0L || Integer.MAX_VALUE < index) {
                error.enter();
                throw InvalidArrayIndexException.create(index);
            }
            try {
                javaValue = toHostNode.execute(receiver.context, value2, Object.class, null, true);
            }
            catch (RuntimeException e) {
                error.enter();
                RuntimeException ee = HostObject.unboxEngineException(receiver, e);
                if (ee != null) {
                    throw UnsupportedTypeException.create(new Object[]{value2}, HostObject.getMessage(ee));
                }
                throw e;
            }
            try {
                GuestToHostCalls.setListElement(receiver, index, javaValue);
            }
            catch (IndexOutOfBoundsException e) {
                error.enter();
                throw InvalidArrayIndexException.create(index);
            }
            catch (Throwable t) {
                error.enter();
                throw receiver.context.hostToGuestException(t);
            }
        }

        @Specialization(guards={"isMapEntry.execute(receiver)"}, limit="1")
        static void doMapEntry(HostObject receiver, long index, Object value2, @Cached.Shared(value="isMapEntry") @Cached IsMapEntryNode isMapEntry, @Cached.Shared(value="toHost") @Cached HostToTypeNode toHostNode, @Cached.Shared(value="error") @Cached BranchProfile error) throws InvalidArrayIndexException, UnsupportedTypeException {
            if (index == 1L) {
                Object hostValue;
                try {
                    hostValue = toHostNode.execute(receiver.context, value2, Object.class, null, true);
                }
                catch (RuntimeException e) {
                    error.enter();
                    RuntimeException ee = HostObject.unboxEngineException(receiver, e);
                    if (ee != null) {
                        throw UnsupportedTypeException.create(new Object[]{value2}, HostObject.getMessage(ee));
                    }
                    throw e;
                }
                try {
                    GuestToHostCalls.setMapEntryValue(receiver, hostValue);
                }
                catch (Throwable t) {
                    error.enter();
                    throw receiver.context.hostToGuestException(t);
                }
            }
            throw InvalidArrayIndexException.create(index);
        }

        @Specialization(guards={"!isList.execute(receiver)", "!isArray.execute(receiver)", "!isMapEntry.execute(receiver)"}, limit="1")
        static void doNotArrayOrList(HostObject receiver, long index, Object value2, @Cached.Shared(value="isList") @Cached IsListNode isList, @Cached.Shared(value="isArray") @Cached IsArrayNode isArray, @Cached.Shared(value="isMapEntry") @Cached IsMapEntryNode isMapEntry) throws UnsupportedMessageException {
            throw UnsupportedMessageException.create();
        }
    }

    @ExportMessage
    static class IsArrayElementModifiable {
        IsArrayElementModifiable() {
        }

        @Specialization(guards={"isArray.execute(receiver)"}, limit="1")
        static boolean doArray(HostObject receiver, long index, @Cached.Shared(value="isArray") @Cached IsArrayNode isArray) {
            long size = Array.getLength(receiver.obj);
            return index >= 0L && index < size;
        }

        @Specialization(guards={"isList.execute(receiver)"}, limit="1")
        static boolean doList(HostObject receiver, long index, @Cached.Shared(value="isList") @Cached IsListNode isList, @Cached.Shared(value="error") @Cached BranchProfile error) {
            try {
                long size = GuestToHostCalls.getListSize(receiver);
                return index >= 0L && index < size;
            }
            catch (Throwable t) {
                error.enter();
                throw receiver.context.hostToGuestException(t);
            }
        }

        @Specialization(guards={"isMapEntry.execute(receiver)"}, limit="1")
        static boolean doMapEntry(HostObject receiver, long index, @Cached.Shared(value="isMapEntry") @Cached IsMapEntryNode isMapEntry) {
            return index == 1L;
        }

        @Specialization(guards={"!isList.execute(receiver)", "!isArray.execute(receiver)", "!isMapEntry.execute(receiver)"}, limit="1")
        static boolean doNotArrayOrList(HostObject receiver, long index, @Cached.Shared(value="isList") @Cached IsListNode isList, @Cached.Shared(value="isArray") @Cached IsArrayNode isArray, @Cached.Shared(value="isMapEntry") @Cached IsMapEntryNode isMapEntry) {
            return false;
        }
    }

    @ExportMessage
    static class IsArrayElementReadable {
        IsArrayElementReadable() {
        }

        @Specialization(guards={"isArray.execute(receiver)"}, limit="1")
        static boolean doArray(HostObject receiver, long index, @Cached.Shared(value="isArray") @Cached IsArrayNode isArray) {
            long size = Array.getLength(receiver.obj);
            return index >= 0L && index < size;
        }

        @Specialization(guards={"isList.execute(receiver)"}, limit="1")
        static boolean doList(HostObject receiver, long index, @Cached.Shared(value="isList") @Cached IsListNode isList, @Cached.Shared(value="error") @Cached BranchProfile error) {
            try {
                long size = GuestToHostCalls.getListSize(receiver);
                return index >= 0L && index < size;
            }
            catch (Throwable t) {
                error.enter();
                throw receiver.context.hostToGuestException(t);
            }
        }

        @Specialization(guards={"isMapEntry.execute(receiver)"}, limit="1")
        static boolean doMapEntry(HostObject receiver, long index, @Cached.Shared(value="isMapEntry") @Cached IsMapEntryNode isMapEntry) {
            return index >= 0L && index < 2L;
        }

        @Specialization(guards={"!isList.execute(receiver)", "!isArray.execute(receiver)", "!isMapEntry.execute(receiver)"}, limit="1")
        static boolean doNotArrayOrList(HostObject receiver, long index, @Cached.Shared(value="isList") @Cached IsListNode isList, @Cached.Shared(value="isArray") @Cached IsArrayNode isArray, @Cached.Shared(value="isMapEntry") @Cached IsMapEntryNode isMapEntry) {
            return false;
        }
    }

    @ExportMessage
    static class IsMemberInvocable {
        IsMemberInvocable() {
        }

        @Specialization(guards={"receiver.isStaticClass()", "receiver.isStaticClass() == cachedStatic", "receiver.getLookupClass() == cachedClazz", "cachedName.equals(name)"}, limit="LIMIT")
        static boolean doCached(HostObject receiver, String name, @Cached(value="receiver.isStaticClass()") boolean cachedStatic, @Cached(value="receiver.getLookupClass()") Class<?> cachedClazz, @Cached(value="name") String cachedName, @Cached(value="doUncached(receiver, name)") boolean cachedInvokable) {
            assert (cachedInvokable == IsMemberInvocable.doUncached(receiver, name));
            return cachedInvokable;
        }

        @Specialization(replaces={"doCached"})
        static boolean doUncached(HostObject receiver, String name) {
            if (receiver.isNull()) {
                return false;
            }
            return HostInteropReflect.isInvokable(receiver, receiver.getLookupClass(), name, receiver.isStaticClass());
        }
    }

    @ExportMessage
    static class IsMemberInternal {
        IsMemberInternal() {
        }

        @Specialization(guards={"receiver.isStaticClass()", "receiver.isStaticClass() == cachedStatic", "receiver.getLookupClass() == cachedClazz", "cachedName.equals(name)"}, limit="LIMIT")
        static boolean doCached(HostObject receiver, String name, @Cached(value="receiver.isStaticClass()") boolean cachedStatic, @Cached(value="receiver.getLookupClass()") Class<?> cachedClazz, @Cached(value="name") String cachedName, @Cached(value="doUncached(receiver, name)") boolean cachedInternal) {
            assert (cachedInternal == IsMemberInternal.doUncached(receiver, name));
            return cachedInternal;
        }

        @Specialization(replaces={"doCached"})
        static boolean doUncached(HostObject receiver, String name) {
            if (receiver.isNull()) {
                return false;
            }
            return HostInteropReflect.isInternal(receiver, receiver.getLookupClass(), name, receiver.isStaticClass());
        }
    }

    @ExportMessage
    static class IsMemberModifiable {
        IsMemberModifiable() {
        }

        @Specialization(guards={"receiver.isStaticClass()", "receiver.isStaticClass() == cachedStatic", "receiver.getLookupClass() == cachedClazz", "cachedName.equals(name)"}, limit="LIMIT")
        static boolean doCached(HostObject receiver, String name, @Cached(value="receiver.isStaticClass()") boolean cachedStatic, @Cached(value="receiver.getLookupClass()") Class<?> cachedClazz, @Cached(value="name") String cachedName, @Cached(value="doUncached(receiver, name)") boolean cachedModifiable) {
            assert (cachedModifiable == IsMemberModifiable.doUncached(receiver, name));
            return cachedModifiable;
        }

        @Specialization(replaces={"doCached"})
        static boolean doUncached(HostObject receiver, String name) {
            if (receiver.isNull()) {
                return false;
            }
            return HostInteropReflect.isModifiable(receiver, receiver.getLookupClass(), name, receiver.isStaticClass());
        }
    }

    @ExportLibrary(value=InteropLibrary.class)
    static final class KeysArray
    implements TruffleObject {
        @CompilerDirectives.CompilationFinal(dimensions=1)
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
            return 0L <= idx && idx < (long)this.keys.length;
        }

        @ExportMessage
        String readArrayElement(long idx, @Cached BranchProfile error) throws InvalidArrayIndexException {
            if (!this.isArrayElementReadable(idx)) {
                error.enter();
                throw InvalidArrayIndexException.create(idx);
            }
            return this.keys[(int)idx];
        }
    }

    @ExportMessage
    static class IsMemberReadable {
        IsMemberReadable() {
        }

        @Specialization(guards={"receiver.isStaticClass()", "receiver.isStaticClass() == cachedStatic", "receiver.getLookupClass() == cachedClazz", "cachedName.equals(name)"}, limit="LIMIT")
        static boolean doCached(HostObject receiver, String name, @Cached(value="receiver.isStaticClass()") boolean cachedStatic, @Cached(value="receiver.getLookupClass()") Class<?> cachedClazz, @Cached(value="name") String cachedName, @Cached(value="doUncached(receiver, name)") boolean cachedReadable) {
            assert (cachedReadable == IsMemberReadable.doUncached(receiver, name));
            return cachedReadable;
        }

        @Specialization(replaces={"doCached"})
        static boolean doUncached(HostObject receiver, String name) {
            if (receiver.isNull()) {
                return false;
            }
            return HostInteropReflect.isReadable(receiver, receiver.getLookupClass(), name, receiver.isStaticClass(), receiver.isClass());
        }
    }
}

