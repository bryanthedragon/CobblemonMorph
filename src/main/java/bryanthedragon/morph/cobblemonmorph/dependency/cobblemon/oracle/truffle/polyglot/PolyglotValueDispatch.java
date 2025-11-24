
package com.oracle.truffle.polyglot;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.RootCallTarget;
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
import com.oracle.truffle.api.source.SourceSection;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.polyglot.EngineAccessor;
import com.oracle.truffle.polyglot.HostToGuestRootNode;
import com.oracle.truffle.polyglot.PolyglotContextImpl;
import com.oracle.truffle.polyglot.PolyglotEngineException;
import com.oracle.truffle.polyglot.PolyglotEngineImpl;
import com.oracle.truffle.polyglot.PolyglotFastThreadLocals;
import com.oracle.truffle.polyglot.PolyglotImpl;
import com.oracle.truffle.polyglot.PolyglotInteropErrors;
import com.oracle.truffle.polyglot.PolyglotLanguage;
import com.oracle.truffle.polyglot.PolyglotLanguageContext;
import com.oracle.truffle.polyglot.PolyglotLanguageInstance;
import com.oracle.truffle.polyglot.PolyglotToHostNode;
import com.oracle.truffle.polyglot.PolyglotToHostNodeGen;
import com.oracle.truffle.polyglot.PolyglotValueDispatchFactory;
import com.oracle.truffle.polyglot.PolyglotWrapper;
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
import org.graalvm.polyglot.TypeLiteral;
import org.graalvm.polyglot.Value;
import org.graalvm.polyglot.impl.AbstractPolyglotImpl;

abstract class PolyglotValueDispatch
extends AbstractPolyglotImpl.AbstractValueDispatch {
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
        if (context == null) {
            return null;
        }
        return ((PolyglotLanguageContext)context).context.api;
    }

    static <T extends Throwable> PolyglotException guestToHostException(PolyglotLanguageContext languageContext, T e, boolean entered) {
        throw PolyglotImpl.guestToHostException(languageContext, e, entered);
    }

    @Override
    public Value getArrayElement(Object languageContext, Object receiver, long index) {
        PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;
        Object prev = PolyglotValueDispatch.hostEnter(context);
        try {
            Value value2 = PolyglotValueDispatch.getArrayElementUnsupported(context, receiver);
            return value2;
        }
        catch (Throwable e) {
            throw PolyglotValueDispatch.guestToHostException(context, e, true);
        }
        finally {
            PolyglotValueDispatch.hostLeave(context, prev);
        }
    }

    @CompilerDirectives.TruffleBoundary
    static Value getArrayElementUnsupported(PolyglotLanguageContext context, Object receiver) {
        throw PolyglotValueDispatch.unsupported(context, receiver, "getArrayElement(long)", "hasArrayElements()");
    }

    @Override
    public void setArrayElement(Object languageContext, Object receiver, long index, Object value2) {
        PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;
        Object prev = PolyglotValueDispatch.hostEnter(context);
        try {
            PolyglotValueDispatch.setArrayElementUnsupported(context, receiver);
        }
        catch (Throwable e) {
            throw PolyglotValueDispatch.guestToHostException(context, e, true);
        }
        finally {
            PolyglotValueDispatch.hostLeave(context, prev);
        }
    }

    @CompilerDirectives.TruffleBoundary
    static void setArrayElementUnsupported(PolyglotLanguageContext context, Object receiver) {
        throw PolyglotValueDispatch.unsupported(context, receiver, "setArrayElement(long, Object)", "hasArrayElements()");
    }

    @Override
    public boolean removeArrayElement(Object languageContext, Object receiver, long index) {
        PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;
        Object prev = PolyglotValueDispatch.hostEnter(context);
        try {
            try {
                throw PolyglotValueDispatch.removeArrayElementUnsupported(context, receiver);
            }
            catch (Throwable e) {
                throw PolyglotValueDispatch.guestToHostException(context, e, true);
            }
        }
        catch (Throwable throwable) {
            PolyglotValueDispatch.hostLeave(context, prev);
            throw throwable;
        }
    }

    @CompilerDirectives.TruffleBoundary
    static RuntimeException removeArrayElementUnsupported(PolyglotLanguageContext context, Object receiver) {
        throw PolyglotValueDispatch.unsupported(context, receiver, "removeArrayElement(long, Object)", null);
    }

    @Override
    public long getArraySize(Object languageContext, Object receiver) {
        PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;
        Object prev = PolyglotValueDispatch.hostEnter(context);
        try {
            long l = PolyglotValueDispatch.getArraySizeUnsupported(context, receiver);
            return l;
        }
        catch (Throwable e) {
            throw PolyglotValueDispatch.guestToHostException(context, e, true);
        }
        finally {
            PolyglotValueDispatch.hostLeave(context, prev);
        }
    }

    @CompilerDirectives.TruffleBoundary
    static long getArraySizeUnsupported(PolyglotLanguageContext context, Object receiver) {
        throw PolyglotValueDispatch.unsupported(context, receiver, "getArraySize()", "hasArrayElements()");
    }

    @Override
    public boolean isBufferWritable(Object languageContext, Object receiver) throws UnsupportedOperationException {
        PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;
        Object prev = PolyglotValueDispatch.hostEnter(context);
        try {
            try {
                throw PolyglotValueDispatch.isBufferWritableUnsupported(context, receiver);
            }
            catch (Throwable e) {
                throw PolyglotValueDispatch.guestToHostException(context, e, true);
            }
        }
        catch (Throwable throwable) {
            PolyglotValueDispatch.hostLeave(context, prev);
            throw throwable;
        }
    }

    @CompilerDirectives.TruffleBoundary
    static RuntimeException isBufferWritableUnsupported(PolyglotLanguageContext context, Object receiver) {
        return PolyglotValueDispatch.unsupported(context, receiver, "isBufferWritable()", "hasBufferElements()");
    }

    @Override
    public long getBufferSize(Object languageContext, Object receiver) throws UnsupportedOperationException {
        PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;
        Object prev = PolyglotValueDispatch.hostEnter(context);
        try {
            try {
                throw PolyglotValueDispatch.getBufferSizeUnsupported(context, receiver);
            }
            catch (Throwable e) {
                throw PolyglotValueDispatch.guestToHostException(context, e, true);
            }
        }
        catch (Throwable throwable) {
            PolyglotValueDispatch.hostLeave(context, prev);
            throw throwable;
        }
    }

    @CompilerDirectives.TruffleBoundary
    static RuntimeException getBufferSizeUnsupported(PolyglotLanguageContext context, Object receiver) {
        return PolyglotValueDispatch.unsupported(context, receiver, "getBufferSize()", "hasBufferElements()");
    }

    @Override
    public byte readBufferByte(Object languageContext, Object receiver, long byteOffset) throws UnsupportedOperationException, IndexOutOfBoundsException {
        PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;
        Object prev = PolyglotValueDispatch.hostEnter(context);
        try {
            try {
                throw PolyglotValueDispatch.readBufferByteUnsupported(context, receiver);
            }
            catch (Throwable e) {
                throw PolyglotValueDispatch.guestToHostException(context, e, true);
            }
        }
        catch (Throwable throwable) {
            PolyglotValueDispatch.hostLeave(context, prev);
            throw throwable;
        }
    }

    @CompilerDirectives.TruffleBoundary
    static RuntimeException readBufferByteUnsupported(PolyglotLanguageContext context, Object receiver) {
        return PolyglotValueDispatch.unsupported(context, receiver, "readBufferByte()", "hasBufferElements()");
    }

    @Override
    public void writeBufferByte(Object languageContext, Object receiver, long byteOffset, byte value2) throws UnsupportedOperationException, IndexOutOfBoundsException {
        PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;
        Object prev = PolyglotValueDispatch.hostEnter(context);
        try {
            try {
                throw PolyglotValueDispatch.writeBufferByteUnsupported(context, receiver);
            }
            catch (Throwable e) {
                throw PolyglotValueDispatch.guestToHostException(context, e, true);
            }
        }
        catch (Throwable throwable) {
            PolyglotValueDispatch.hostLeave(context, prev);
            throw throwable;
        }
    }

    @CompilerDirectives.TruffleBoundary
    static RuntimeException writeBufferByteUnsupported(PolyglotLanguageContext context, Object receiver) {
        return PolyglotValueDispatch.unsupported(context, receiver, "writeBufferByte()", "hasBufferElements()");
    }

    @Override
    public short readBufferShort(Object languageContext, Object receiver, ByteOrder order, long byteOffset) throws UnsupportedOperationException, IndexOutOfBoundsException {
        PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;
        Object prev = PolyglotValueDispatch.hostEnter(context);
        try {
            try {
                throw PolyglotValueDispatch.readBufferShortUnsupported(context, receiver);
            }
            catch (Throwable e) {
                throw PolyglotValueDispatch.guestToHostException(context, e, true);
            }
        }
        catch (Throwable throwable) {
            PolyglotValueDispatch.hostLeave(context, prev);
            throw throwable;
        }
    }

    @CompilerDirectives.TruffleBoundary
    static RuntimeException readBufferShortUnsupported(PolyglotLanguageContext context, Object receiver) {
        return PolyglotValueDispatch.unsupported(context, receiver, "readBufferShort()", "hasBufferElements()");
    }

    @Override
    public void writeBufferShort(Object languageContext, Object receiver, ByteOrder order, long byteOffset, short value2) throws UnsupportedOperationException, IndexOutOfBoundsException {
        PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;
        Object prev = PolyglotValueDispatch.hostEnter(context);
        try {
            try {
                throw PolyglotValueDispatch.writeBufferShortUnsupported(context, receiver);
            }
            catch (Throwable e) {
                throw PolyglotValueDispatch.guestToHostException(context, e, true);
            }
        }
        catch (Throwable throwable) {
            PolyglotValueDispatch.hostLeave(context, prev);
            throw throwable;
        }
    }

    @CompilerDirectives.TruffleBoundary
    static RuntimeException writeBufferShortUnsupported(PolyglotLanguageContext context, Object receiver) {
        return PolyglotValueDispatch.unsupported(context, receiver, "writeBufferShort()", "hasBufferElements()");
    }

    @Override
    public int readBufferInt(Object languageContext, Object receiver, ByteOrder order, long byteOffset) throws UnsupportedOperationException, IndexOutOfBoundsException {
        PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;
        Object prev = PolyglotValueDispatch.hostEnter(context);
        try {
            try {
                throw PolyglotValueDispatch.readBufferIntUnsupported(context, receiver);
            }
            catch (Throwable e) {
                throw PolyglotValueDispatch.guestToHostException(context, e, true);
            }
        }
        catch (Throwable throwable) {
            PolyglotValueDispatch.hostLeave(context, prev);
            throw throwable;
        }
    }

    @CompilerDirectives.TruffleBoundary
    static RuntimeException readBufferIntUnsupported(PolyglotLanguageContext context, Object receiver) {
        return PolyglotValueDispatch.unsupported(context, receiver, "readBufferInt()", "hasBufferElements()");
    }

    @Override
    public void writeBufferInt(Object languageContext, Object receiver, ByteOrder order, long byteOffset, int value2) throws UnsupportedOperationException, IndexOutOfBoundsException {
        PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;
        Object prev = PolyglotValueDispatch.hostEnter(context);
        try {
            try {
                throw PolyglotValueDispatch.writeBufferIntUnsupported(context, receiver);
            }
            catch (Throwable e) {
                throw PolyglotValueDispatch.guestToHostException(context, e, true);
            }
        }
        catch (Throwable throwable) {
            PolyglotValueDispatch.hostLeave(context, prev);
            throw throwable;
        }
    }

    @CompilerDirectives.TruffleBoundary
    static RuntimeException writeBufferIntUnsupported(PolyglotLanguageContext context, Object receiver) {
        return PolyglotValueDispatch.unsupported(context, receiver, "writeBufferInt()", "hasBufferElements()");
    }

    @Override
    public long readBufferLong(Object languageContext, Object receiver, ByteOrder order, long byteOffset) throws UnsupportedOperationException, IndexOutOfBoundsException {
        PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;
        Object prev = PolyglotValueDispatch.hostEnter(context);
        try {
            try {
                throw PolyglotValueDispatch.readBufferLongUnsupported(context, receiver);
            }
            catch (Throwable e) {
                throw PolyglotValueDispatch.guestToHostException(context, e, true);
            }
        }
        catch (Throwable throwable) {
            PolyglotValueDispatch.hostLeave(context, prev);
            throw throwable;
        }
    }

    @CompilerDirectives.TruffleBoundary
    static RuntimeException readBufferLongUnsupported(PolyglotLanguageContext context, Object receiver) {
        return PolyglotValueDispatch.unsupported(context, receiver, "readBufferLong()", "hasBufferElements()");
    }

    @Override
    public void writeBufferLong(Object languageContext, Object receiver, ByteOrder order, long byteOffset, long value2) throws UnsupportedOperationException, IndexOutOfBoundsException {
        PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;
        Object prev = PolyglotValueDispatch.hostEnter(context);
        try {
            try {
                throw PolyglotValueDispatch.writeBufferLongUnsupported(context, receiver);
            }
            catch (Throwable e) {
                throw PolyglotValueDispatch.guestToHostException(context, e, true);
            }
        }
        catch (Throwable throwable) {
            PolyglotValueDispatch.hostLeave(context, prev);
            throw throwable;
        }
    }

    @CompilerDirectives.TruffleBoundary
    static RuntimeException writeBufferLongUnsupported(PolyglotLanguageContext context, Object receiver) {
        return PolyglotValueDispatch.unsupported(context, receiver, "writeBufferLong()", "hasBufferElements()");
    }

    @Override
    public float readBufferFloat(Object languageContext, Object receiver, ByteOrder order, long byteOffset) throws UnsupportedOperationException, IndexOutOfBoundsException {
        PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;
        Object prev = PolyglotValueDispatch.hostEnter(context);
        try {
            try {
                throw PolyglotValueDispatch.readBufferFloatUnsupported(context, receiver);
            }
            catch (Throwable e) {
                throw PolyglotValueDispatch.guestToHostException(context, e, true);
            }
        }
        catch (Throwable throwable) {
            PolyglotValueDispatch.hostLeave(context, prev);
            throw throwable;
        }
    }

    @CompilerDirectives.TruffleBoundary
    static RuntimeException readBufferFloatUnsupported(PolyglotLanguageContext context, Object receiver) {
        return PolyglotValueDispatch.unsupported(context, receiver, "readBufferFloat()", "hasBufferElements()");
    }

    @Override
    public void writeBufferFloat(Object languageContext, Object receiver, ByteOrder order, long byteOffset, float value2) throws UnsupportedOperationException, IndexOutOfBoundsException {
        PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;
        Object prev = PolyglotValueDispatch.hostEnter(context);
        try {
            try {
                throw PolyglotValueDispatch.writeBufferFloatUnsupported(context, receiver);
            }
            catch (Throwable e) {
                throw PolyglotValueDispatch.guestToHostException(context, e, true);
            }
        }
        catch (Throwable throwable) {
            PolyglotValueDispatch.hostLeave(context, prev);
            throw throwable;
        }
    }

    @CompilerDirectives.TruffleBoundary
    static RuntimeException writeBufferFloatUnsupported(PolyglotLanguageContext context, Object receiver) {
        return PolyglotValueDispatch.unsupported(context, receiver, "writeBufferFloat()", "hasBufferElements()");
    }

    @Override
    public double readBufferDouble(Object languageContext, Object receiver, ByteOrder order, long byteOffset) throws UnsupportedOperationException, IndexOutOfBoundsException {
        PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;
        Object prev = PolyglotValueDispatch.hostEnter(context);
        try {
            try {
                throw PolyglotValueDispatch.readBufferDoubleUnsupported(context, receiver);
            }
            catch (Throwable e) {
                throw PolyglotValueDispatch.guestToHostException(context, e, true);
            }
        }
        catch (Throwable throwable) {
            PolyglotValueDispatch.hostLeave(context, prev);
            throw throwable;
        }
    }

    @CompilerDirectives.TruffleBoundary
    static RuntimeException readBufferDoubleUnsupported(PolyglotLanguageContext context, Object receiver) {
        return PolyglotValueDispatch.unsupported(context, receiver, "readBufferDouble()", "hasBufferElements()");
    }

    @Override
    public void writeBufferDouble(Object languageContext, Object receiver, ByteOrder order, long byteOffset, double value2) throws UnsupportedOperationException, IndexOutOfBoundsException {
        PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;
        Object prev = PolyglotValueDispatch.hostEnter(context);
        try {
            try {
                throw PolyglotValueDispatch.writeBufferDoubleUnsupported(context, receiver);
            }
            catch (Throwable e) {
                throw PolyglotValueDispatch.guestToHostException(context, e, true);
            }
        }
        catch (Throwable throwable) {
            PolyglotValueDispatch.hostLeave(context, prev);
            throw throwable;
        }
    }

    @CompilerDirectives.TruffleBoundary
    static RuntimeException writeBufferDoubleUnsupported(PolyglotLanguageContext context, Object receiver) {
        return PolyglotValueDispatch.unsupported(context, receiver, "writeBufferDouble()", "hasBufferElements()");
    }

    @CompilerDirectives.TruffleBoundary
    protected static RuntimeException invalidBufferIndex(PolyglotLanguageContext context, Object receiver, long byteOffset, long size) {
        String message = String.format("Invalid buffer access of length %d at byte offset %d for buffer %s.", size, byteOffset, PolyglotValueDispatch.getValueInfo(context, receiver));
        throw PolyglotEngineException.bufferIndexOutOfBounds(message);
    }

    @Override
    public Value getMember(Object languageContext, Object receiver, String key) {
        PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;
        Object prev = PolyglotValueDispatch.hostEnter(context);
        try {
            Value value2 = PolyglotValueDispatch.getMemberUnsupported(context, receiver, key);
            return value2;
        }
        catch (Throwable e) {
            throw PolyglotValueDispatch.guestToHostException(context, e, true);
        }
        finally {
            PolyglotValueDispatch.hostLeave(context, prev);
        }
    }

    @CompilerDirectives.TruffleBoundary
    static Value getMemberUnsupported(PolyglotLanguageContext context, Object receiver, String key) {
        throw PolyglotValueDispatch.unsupported(context, receiver, "getMember(String)", "hasMembers()");
    }

    @Override
    public void putMember(Object languageContext, Object receiver, String key, Object member) {
        PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;
        Object prev = PolyglotValueDispatch.hostEnter(context);
        try {
            PolyglotValueDispatch.putMemberUnsupported(context, receiver);
        }
        catch (Throwable e) {
            throw PolyglotValueDispatch.guestToHostException(context, e, true);
        }
        finally {
            PolyglotValueDispatch.hostLeave(context, prev);
        }
    }

    @CompilerDirectives.TruffleBoundary
    static RuntimeException putMemberUnsupported(PolyglotLanguageContext context, Object receiver) {
        throw PolyglotValueDispatch.unsupported(context, receiver, "putMember(String, Object)", "hasMembers()");
    }

    @Override
    public boolean removeMember(Object languageContext, Object receiver, String key) {
        PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;
        Object prev = PolyglotValueDispatch.hostEnter(context);
        try {
            try {
                throw PolyglotValueDispatch.removeMemberUnsupported(context, receiver);
            }
            catch (Throwable e) {
                throw PolyglotValueDispatch.guestToHostException(context, e, true);
            }
        }
        catch (Throwable throwable) {
            PolyglotValueDispatch.hostLeave(context, prev);
            throw throwable;
        }
    }

    @CompilerDirectives.TruffleBoundary
    static RuntimeException removeMemberUnsupported(PolyglotLanguageContext context, Object receiver) {
        throw PolyglotValueDispatch.unsupported(context, receiver, "removeMember(String, Object)", null);
    }

    @Override
    public Value execute(Object languageContext, Object receiver, Object[] arguments) {
        PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;
        Object prev = PolyglotValueDispatch.hostEnter(context);
        try {
            try {
                throw PolyglotValueDispatch.executeUnsupported(context, receiver);
            }
            catch (Throwable e) {
                throw PolyglotValueDispatch.guestToHostException(context, e, true);
            }
        }
        catch (Throwable throwable) {
            PolyglotValueDispatch.hostLeave(context, prev);
            throw throwable;
        }
    }

    @Override
    public Value execute(Object languageContext, Object receiver) {
        PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;
        Object prev = PolyglotValueDispatch.hostEnter(context);
        try {
            try {
                throw PolyglotValueDispatch.executeUnsupported(context, receiver);
            }
            catch (Throwable e) {
                throw PolyglotValueDispatch.guestToHostException(context, e, true);
            }
        }
        catch (Throwable throwable) {
            PolyglotValueDispatch.hostLeave(context, prev);
            throw throwable;
        }
    }

    @CompilerDirectives.TruffleBoundary
    static RuntimeException executeUnsupported(PolyglotLanguageContext context, Object receiver) {
        throw PolyglotValueDispatch.unsupported(context, receiver, "execute(Object...)", "canExecute()");
    }

    @Override
    public Value newInstance(Object languageContext, Object receiver, Object[] arguments) {
        PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;
        Object prev = PolyglotValueDispatch.hostEnter(context);
        try {
            Value value2 = PolyglotValueDispatch.newInstanceUnsupported(context, receiver);
            return value2;
        }
        catch (Throwable e) {
            throw PolyglotValueDispatch.guestToHostException(context, e, true);
        }
        finally {
            PolyglotValueDispatch.hostLeave(context, prev);
        }
    }

    @CompilerDirectives.TruffleBoundary
    static Value newInstanceUnsupported(PolyglotLanguageContext context, Object receiver) {
        throw PolyglotValueDispatch.unsupported(context, receiver, "newInstance(Object...)", "canInstantiate()");
    }

    @Override
    public void executeVoid(Object languageContext, Object receiver, Object[] arguments) {
        PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;
        Object prev = PolyglotValueDispatch.hostEnter(context);
        try {
            PolyglotValueDispatch.executeVoidUnsupported(context, receiver);
        }
        catch (Throwable e) {
            throw PolyglotValueDispatch.guestToHostException(context, e, true);
        }
        finally {
            PolyglotValueDispatch.hostLeave(context, prev);
        }
    }

    @Override
    public void executeVoid(Object languageContext, Object receiver) {
        PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;
        Object prev = PolyglotValueDispatch.hostEnter(context);
        try {
            PolyglotValueDispatch.executeVoidUnsupported(context, receiver);
        }
        catch (Throwable e) {
            throw PolyglotValueDispatch.guestToHostException(context, e, true);
        }
        finally {
            PolyglotValueDispatch.hostLeave(context, prev);
        }
    }

    @CompilerDirectives.TruffleBoundary
    static void executeVoidUnsupported(PolyglotLanguageContext context, Object receiver) {
        throw PolyglotValueDispatch.unsupported(context, receiver, "executeVoid(Object...)", "canExecute()");
    }

    @Override
    public Value invoke(Object languageContext, Object receiver, String identifier, Object[] arguments) {
        PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;
        Object prev = PolyglotValueDispatch.hostEnter(context);
        try {
            try {
                throw PolyglotValueDispatch.invokeUnsupported(context, receiver, identifier);
            }
            catch (Throwable e) {
                throw PolyglotValueDispatch.guestToHostException(context, e, true);
            }
        }
        catch (Throwable throwable) {
            PolyglotValueDispatch.hostLeave(context, prev);
            throw throwable;
        }
    }

    @Override
    public Value invoke(Object languageContext, Object receiver, String identifier) {
        PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;
        Object prev = PolyglotValueDispatch.hostEnter(context);
        try {
            try {
                throw PolyglotValueDispatch.invokeUnsupported(context, receiver, identifier);
            }
            catch (Throwable e) {
                throw PolyglotValueDispatch.guestToHostException(context, e, true);
            }
        }
        catch (Throwable throwable) {
            PolyglotValueDispatch.hostLeave(context, prev);
            throw throwable;
        }
    }

    @CompilerDirectives.TruffleBoundary
    static RuntimeException invokeUnsupported(PolyglotLanguageContext context, Object receiver, String identifier) {
        throw PolyglotValueDispatch.unsupported(context, receiver, "invoke(" + identifier + ", Object...)", "canInvoke(String)");
    }

    @Override
    public String asString(Object languageContext, Object receiver) {
        PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;
        Object prev = PolyglotValueDispatch.hostEnter(context);
        try {
            String string = PolyglotValueDispatch.asStringUnsupported(context, receiver);
            return string;
        }
        catch (Throwable e) {
            throw PolyglotValueDispatch.guestToHostException(context, e, true);
        }
        finally {
            PolyglotValueDispatch.hostLeave(context, prev);
        }
    }

    protected static String asStringUnsupported(PolyglotLanguageContext context, Object receiver) {
        return PolyglotValueDispatch.invalidCastPrimitive(context, receiver, String.class, "asString()", "isString()", "Invalid coercion.");
    }

    @Override
    public boolean asBoolean(Object languageContext, Object receiver) {
        PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;
        Object prev = PolyglotValueDispatch.hostEnter(context);
        try {
            boolean bl = PolyglotValueDispatch.asBooleanUnsupported(context, receiver);
            return bl;
        }
        catch (Throwable e) {
            throw PolyglotValueDispatch.guestToHostException(context, e, true);
        }
        finally {
            PolyglotValueDispatch.hostLeave(context, prev);
        }
    }

    private static boolean isNullUncached(Object receiver) {
        return InteropLibrary.getFactory().getUncached().isNull(receiver);
    }

    protected static boolean asBooleanUnsupported(PolyglotLanguageContext context, Object receiver) {
        return PolyglotValueDispatch.invalidCastPrimitive(context, receiver, Boolean.TYPE, "asBoolean()", "isBoolean()", "Invalid or lossy primitive coercion.");
    }

    private static <T> T invalidCastPrimitive(PolyglotLanguageContext context, Object receiver, Class<T> clazz, String asMethodName, String isMethodName, String detail) {
        if (PolyglotValueDispatch.isNullUncached(receiver)) {
            throw PolyglotValueDispatch.nullCoercion(context, receiver, clazz, asMethodName, isMethodName);
        }
        throw PolyglotValueDispatch.cannotConvert(context, receiver, clazz, asMethodName, isMethodName, detail);
    }

    @Override
    public int asInt(Object languageContext, Object receiver) {
        PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;
        Object prev = PolyglotValueDispatch.hostEnter(context);
        try {
            int n = PolyglotValueDispatch.asIntUnsupported(context, receiver);
            return n;
        }
        catch (Throwable e) {
            throw PolyglotValueDispatch.guestToHostException(context, e, true);
        }
        finally {
            PolyglotValueDispatch.hostLeave(context, prev);
        }
    }

    protected static int asIntUnsupported(PolyglotLanguageContext context, Object receiver) {
        return PolyglotValueDispatch.invalidCastPrimitive(context, receiver, Integer.TYPE, "asInt()", "fitsInInt()", "Invalid or lossy primitive coercion.");
    }

    @Override
    public long asLong(Object languageContext, Object receiver) {
        PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;
        Object prev = PolyglotValueDispatch.hostEnter(context);
        try {
            long l = PolyglotValueDispatch.asLongUnsupported(context, receiver);
            return l;
        }
        catch (Throwable e) {
            throw PolyglotValueDispatch.guestToHostException(context, e, true);
        }
        finally {
            PolyglotValueDispatch.hostLeave(context, prev);
        }
    }

    protected static long asLongUnsupported(PolyglotLanguageContext context, Object receiver) {
        return PolyglotValueDispatch.invalidCastPrimitive(context, receiver, Long.TYPE, "asLong()", "fitsInLong()", "Invalid or lossy primitive coercion.");
    }

    @Override
    public double asDouble(Object languageContext, Object receiver) {
        PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;
        Object prev = PolyglotValueDispatch.hostEnter(context);
        try {
            double d = PolyglotValueDispatch.asDoubleUnsupported(context, receiver);
            return d;
        }
        catch (Throwable e) {
            throw PolyglotValueDispatch.guestToHostException(context, e, true);
        }
        finally {
            PolyglotValueDispatch.hostLeave(context, prev);
        }
    }

    protected static double asDoubleUnsupported(PolyglotLanguageContext context, Object receiver) {
        return PolyglotValueDispatch.invalidCastPrimitive(context, receiver, Double.TYPE, "asDouble()", "fitsInDouble()", "Invalid or lossy primitive coercion.");
    }

    @Override
    public float asFloat(Object languageContext, Object receiver) {
        PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;
        Object prev = PolyglotValueDispatch.hostEnter(context);
        try {
            float f = PolyglotValueDispatch.asFloatUnsupported(context, receiver);
            return f;
        }
        catch (Throwable e) {
            throw PolyglotValueDispatch.guestToHostException(context, e, true);
        }
        finally {
            PolyglotValueDispatch.hostLeave(context, prev);
        }
    }

    protected static float asFloatUnsupported(PolyglotLanguageContext context, Object receiver) {
        return PolyglotValueDispatch.invalidCastPrimitive(context, receiver, Float.TYPE, "asFloat()", "fitsInFloat()", "Invalid or lossy primitive coercion.").floatValue();
    }

    @Override
    public byte asByte(Object languageContext, Object receiver) {
        PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;
        Object prev = PolyglotValueDispatch.hostEnter(context);
        try {
            byte by = PolyglotValueDispatch.asByteUnsupported(context, receiver);
            return by;
        }
        catch (Throwable e) {
            throw PolyglotValueDispatch.guestToHostException(context, e, true);
        }
        finally {
            PolyglotValueDispatch.hostLeave(context, prev);
        }
    }

    protected static byte asByteUnsupported(PolyglotLanguageContext context, Object receiver) {
        return PolyglotValueDispatch.invalidCastPrimitive(context, receiver, Byte.TYPE, "asByte()", "fitsInByte()", "Invalid or lossy primitive coercion.");
    }

    @Override
    public short asShort(Object languageContext, Object receiver) {
        PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;
        Object prev = PolyglotValueDispatch.hostEnter(context);
        try {
            short s = PolyglotValueDispatch.asShortUnsupported(context, receiver);
            return s;
        }
        catch (Throwable e) {
            throw PolyglotValueDispatch.guestToHostException(context, e, true);
        }
        finally {
            PolyglotValueDispatch.hostLeave(context, prev);
        }
    }

    protected static short asShortUnsupported(PolyglotLanguageContext context, Object receiver) {
        return PolyglotValueDispatch.invalidCastPrimitive(context, receiver, Short.TYPE, "asShort()", "fitsInShort()", "Invalid or lossy primitive coercion.");
    }

    @Override
    public long asNativePointer(Object languageContext, Object receiver) {
        PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;
        Object prev = PolyglotValueDispatch.hostEnter(context);
        try {
            long l = PolyglotValueDispatch.asNativePointerUnsupported(context, receiver);
            return l;
        }
        catch (Throwable e) {
            throw PolyglotValueDispatch.guestToHostException(context, e, true);
        }
        finally {
            PolyglotValueDispatch.hostLeave(context, prev);
        }
    }

    static long asNativePointerUnsupported(PolyglotLanguageContext context, Object receiver) {
        throw PolyglotValueDispatch.cannotConvert(context, receiver, Long.TYPE, "asNativePointer()", "isNativeObject()", "Value cannot be converted to a native pointer.");
    }

    @Override
    public Object asHostObject(Object languageContext, Object receiver) {
        PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;
        Object prev = PolyglotValueDispatch.hostEnter(context);
        try {
            Object object = PolyglotValueDispatch.asHostObjectUnsupported(context, receiver);
            return object;
        }
        catch (Throwable e) {
            throw PolyglotValueDispatch.guestToHostException(context, e, true);
        }
        finally {
            PolyglotValueDispatch.hostLeave(context, prev);
        }
    }

    protected static Object asHostObjectUnsupported(PolyglotLanguageContext context, Object receiver) {
        throw PolyglotValueDispatch.cannotConvert(context, receiver, null, "asHostObject()", "isHostObject()", "Value is not a host object.");
    }

    @Override
    public Object asProxyObject(Object languageContext, Object receiver) {
        PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;
        Object prev = PolyglotValueDispatch.hostEnter(context);
        try {
            Object object = PolyglotValueDispatch.asProxyObjectUnsupported(context, receiver);
            return object;
        }
        catch (Throwable e) {
            throw PolyglotValueDispatch.guestToHostException(context, e, true);
        }
        finally {
            PolyglotValueDispatch.hostLeave(context, prev);
        }
    }

    protected static Object asProxyObjectUnsupported(PolyglotLanguageContext context, Object receiver) {
        throw PolyglotValueDispatch.cannotConvert(context, receiver, null, "asProxyObject()", "isProxyObject()", "Value is not a proxy object.");
    }

    @Override
    public LocalDate asDate(Object languageContext, Object receiver) {
        PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;
        Object prev = PolyglotValueDispatch.hostEnter(context);
        try {
            if (PolyglotValueDispatch.isNullUncached(receiver)) {
                LocalDate localDate = null;
                return localDate;
            }
            try {
                throw PolyglotValueDispatch.cannotConvert(context, receiver, null, "asDate()", "isDate()", "Value does not contain date information.");
            }
            catch (Throwable e) {
                throw PolyglotValueDispatch.guestToHostException(context, e, true);
            }
        }
        finally {
            PolyglotValueDispatch.hostLeave(context, prev);
        }
    }

    @Override
    public LocalTime asTime(Object languageContext, Object receiver) {
        PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;
        Object prev = PolyglotValueDispatch.hostEnter(context);
        try {
            if (PolyglotValueDispatch.isNullUncached(receiver)) {
                LocalTime localTime = null;
                return localTime;
            }
            try {
                throw PolyglotValueDispatch.cannotConvert(context, receiver, null, "asTime()", "isTime()", "Value does not contain time information.");
            }
            catch (Throwable e) {
                throw PolyglotValueDispatch.guestToHostException(context, e, true);
            }
        }
        finally {
            PolyglotValueDispatch.hostLeave(context, prev);
        }
    }

    @Override
    public ZoneId asTimeZone(Object languageContext, Object receiver) {
        PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;
        Object prev = PolyglotValueDispatch.hostEnter(context);
        try {
            if (PolyglotValueDispatch.isNullUncached(receiver)) {
                ZoneId zoneId = null;
                return zoneId;
            }
            try {
                throw PolyglotValueDispatch.cannotConvert(context, receiver, null, "asTimeZone()", "isTimeZone()", "Value does not contain time zone information.");
            }
            catch (Throwable e) {
                throw PolyglotValueDispatch.guestToHostException(context, e, true);
            }
        }
        finally {
            PolyglotValueDispatch.hostLeave(context, prev);
        }
    }

    @Override
    public Instant asInstant(Object languageContext, Object receiver) {
        PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;
        Object prev = PolyglotValueDispatch.hostEnter(context);
        try {
            if (PolyglotValueDispatch.isNullUncached(receiver)) {
                Instant instant = null;
                return instant;
            }
            try {
                throw PolyglotValueDispatch.cannotConvert(context, receiver, null, "asInstant()", "isInstant()", "Value does not contain instant information.");
            }
            catch (Throwable e) {
                throw PolyglotValueDispatch.guestToHostException(context, e, true);
            }
        }
        finally {
            PolyglotValueDispatch.hostLeave(context, prev);
        }
    }

    @Override
    public Duration asDuration(Object languageContext, Object receiver) {
        PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;
        Object prev = PolyglotValueDispatch.hostEnter(context);
        try {
            if (PolyglotValueDispatch.isNullUncached(receiver)) {
                Duration duration = null;
                return duration;
            }
            try {
                throw PolyglotValueDispatch.cannotConvert(context, receiver, null, "asDuration()", "isDuration()", "Value does not contain duration information.");
            }
            catch (Throwable e) {
                throw PolyglotValueDispatch.guestToHostException(context, e, true);
            }
        }
        finally {
            PolyglotValueDispatch.hostLeave(context, prev);
        }
    }

    @Override
    public RuntimeException throwException(Object languageContext, Object receiver) {
        PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;
        Object prev = PolyglotValueDispatch.hostEnter(context);
        try {
            try {
                throw PolyglotValueDispatch.unsupported(context, receiver, "throwException()", "isException()");
            }
            catch (Throwable e) {
                throw PolyglotValueDispatch.guestToHostException(context, e, true);
            }
        }
        catch (Throwable throwable) {
            PolyglotValueDispatch.hostLeave(context, prev);
            throw throwable;
        }
    }

    @Override
    public final Value getMetaObject(Object languageContext, Object receiver) {
        PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;
        Object prev = PolyglotValueDispatch.hostEnter(context);
        try {
            Value value2 = this.getMetaObjectImpl(context, receiver);
            return value2;
        }
        catch (Throwable e) {
            throw PolyglotValueDispatch.guestToHostException(context, e, true);
        }
        finally {
            PolyglotValueDispatch.hostLeave(context, prev);
        }
    }

    @Override
    public Value getIterator(Object languageContext, Object receiver) {
        PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;
        Object prev = PolyglotValueDispatch.hostEnter(context);
        try {
            Value value2 = PolyglotValueDispatch.getIteratorUnsupported(context, receiver);
            return value2;
        }
        catch (Throwable e) {
            throw PolyglotValueDispatch.guestToHostException(context, e, true);
        }
        finally {
            PolyglotValueDispatch.hostLeave(context, prev);
        }
    }

    @CompilerDirectives.TruffleBoundary
    static final Value getIteratorUnsupported(PolyglotLanguageContext context, Object receiver) {
        throw PolyglotValueDispatch.unsupported(context, receiver, "getIterator()", "hasIterator()");
    }

    @Override
    public boolean hasIteratorNextElement(Object languageContext, Object receiver) {
        PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;
        Object prev = PolyglotValueDispatch.hostEnter(context);
        try {
            boolean bl = PolyglotValueDispatch.hasIteratorNextElementUnsupported(context, receiver);
            return bl;
        }
        catch (Throwable e) {
            throw PolyglotValueDispatch.guestToHostException(context, e, true);
        }
        finally {
            PolyglotValueDispatch.hostLeave(context, prev);
        }
    }

    @CompilerDirectives.TruffleBoundary
    static final boolean hasIteratorNextElementUnsupported(PolyglotLanguageContext context, Object receiver) {
        throw PolyglotValueDispatch.unsupported(context, receiver, "hasIteratorNextElement()", "isIterator()");
    }

    @Override
    public Value getIteratorNextElement(Object languageContext, Object receiver) {
        PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;
        Object prev = PolyglotValueDispatch.hostEnter(context);
        try {
            Value value2 = PolyglotValueDispatch.getIteratorNextElementUnsupported(context, receiver);
            return value2;
        }
        catch (Throwable e) {
            throw PolyglotValueDispatch.guestToHostException(context, e, true);
        }
        finally {
            PolyglotValueDispatch.hostLeave(context, prev);
        }
    }

    @CompilerDirectives.TruffleBoundary
    static final Value getIteratorNextElementUnsupported(PolyglotLanguageContext context, Object receiver) {
        throw PolyglotValueDispatch.unsupported(context, receiver, "getIteratorNextElement()", "isIterator()");
    }

    @Override
    public long getHashSize(Object languageContext, Object receiver) {
        PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;
        Object prev = PolyglotValueDispatch.hostEnter(context);
        try {
            try {
                throw PolyglotValueDispatch.getHashSizeUnsupported(context, receiver);
            }
            catch (Throwable e) {
                throw PolyglotValueDispatch.guestToHostException(context, e, true);
            }
        }
        catch (Throwable throwable) {
            PolyglotValueDispatch.hostLeave(context, prev);
            throw throwable;
        }
    }

    @CompilerDirectives.TruffleBoundary
    static final RuntimeException getHashSizeUnsupported(PolyglotLanguageContext context, Object receiver) {
        throw PolyglotValueDispatch.unsupported(context, receiver, "getHashSize()", "hasHashEntries()");
    }

    @Override
    public Value getHashValue(Object languageContext, Object receiver, Object key) {
        PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;
        Object prev = PolyglotValueDispatch.hostEnter(context);
        try {
            try {
                throw PolyglotValueDispatch.getHashValueUnsupported(context, receiver, key);
            }
            catch (Throwable e) {
                throw PolyglotValueDispatch.guestToHostException(context, e, true);
            }
        }
        catch (Throwable throwable) {
            PolyglotValueDispatch.hostLeave(context, prev);
            throw throwable;
        }
    }

    @CompilerDirectives.TruffleBoundary
    static final RuntimeException getHashValueUnsupported(PolyglotLanguageContext context, Object receiver, Object key) {
        throw PolyglotValueDispatch.unsupported(context, receiver, "getHashValue(Object)", "hasHashEntries()");
    }

    @Override
    public Value getHashValueOrDefault(Object languageContext, Object receiver, Object key, Object defaultValue) {
        PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;
        Object prev = PolyglotValueDispatch.hostEnter(context);
        try {
            try {
                throw PolyglotValueDispatch.getHashValueOrDefaultUnsupported(context, receiver, key, defaultValue);
            }
            catch (Throwable e) {
                throw PolyglotValueDispatch.guestToHostException(context, e, true);
            }
        }
        catch (Throwable throwable) {
            PolyglotValueDispatch.hostLeave(context, prev);
            throw throwable;
        }
    }

    @CompilerDirectives.TruffleBoundary
    static final RuntimeException getHashValueOrDefaultUnsupported(PolyglotLanguageContext context, Object receiver, Object key, Object defaultValue) {
        throw PolyglotValueDispatch.unsupported(context, receiver, "getHashValueOrDefault(Object, Object)", "hasHashEntries()");
    }

    @Override
    public void putHashEntry(Object languageContext, Object receiver, Object key, Object value2) {
        PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;
        Object prev = PolyglotValueDispatch.hostEnter(context);
        try {
            PolyglotValueDispatch.putHashEntryUnsupported(context, receiver, key, value2);
        }
        catch (Throwable e) {
            throw PolyglotValueDispatch.guestToHostException(context, e, true);
        }
        finally {
            PolyglotValueDispatch.hostLeave(context, prev);
        }
    }

    @CompilerDirectives.TruffleBoundary
    static final RuntimeException putHashEntryUnsupported(PolyglotLanguageContext context, Object receiver, Object key, Object value2) {
        throw PolyglotValueDispatch.unsupported(context, receiver, "putHashEntry(Object, Object)", "hasHashEntries()");
    }

    @Override
    public boolean removeHashEntry(Object languageContext, Object receiver, Object key) {
        PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;
        Object prev = PolyglotValueDispatch.hostEnter(context);
        try {
            try {
                throw PolyglotValueDispatch.removeHashEntryUnsupported(context, receiver, key);
            }
            catch (Throwable e) {
                throw PolyglotValueDispatch.guestToHostException(context, e, true);
            }
        }
        catch (Throwable throwable) {
            PolyglotValueDispatch.hostLeave(context, prev);
            throw throwable;
        }
    }

    @CompilerDirectives.TruffleBoundary
    static final RuntimeException removeHashEntryUnsupported(PolyglotLanguageContext context, Object receiver, Object key) {
        throw PolyglotValueDispatch.unsupported(context, receiver, "removeHashEntry(Object)", "hasHashEntries()");
    }

    @Override
    public Value getHashEntriesIterator(Object languageContext, Object receiver) {
        PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;
        Object prev = PolyglotValueDispatch.hostEnter(context);
        try {
            try {
                throw PolyglotValueDispatch.getHashEntriesIteratorUnsupported(context, receiver);
            }
            catch (Throwable e) {
                throw PolyglotValueDispatch.guestToHostException(context, e, true);
            }
        }
        catch (Throwable throwable) {
            PolyglotValueDispatch.hostLeave(context, prev);
            throw throwable;
        }
    }

    @CompilerDirectives.TruffleBoundary
    static final RuntimeException getHashEntriesIteratorUnsupported(PolyglotLanguageContext context, Object receiver) {
        throw PolyglotValueDispatch.unsupported(context, receiver, "getHashEntriesIterator()", "hasHashEntries()");
    }

    @Override
    public Value getHashKeysIterator(Object languageContext, Object receiver) {
        PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;
        Object prev = PolyglotValueDispatch.hostEnter(context);
        try {
            try {
                throw PolyglotValueDispatch.getHashKeysIteratorUnsupported(context, receiver);
            }
            catch (Throwable e) {
                throw PolyglotValueDispatch.guestToHostException(context, e, true);
            }
        }
        catch (Throwable throwable) {
            PolyglotValueDispatch.hostLeave(context, prev);
            throw throwable;
        }
    }

    @CompilerDirectives.TruffleBoundary
    static final RuntimeException getHashKeysIteratorUnsupported(PolyglotLanguageContext context, Object receiver) {
        throw PolyglotValueDispatch.unsupported(context, receiver, "getHashKeysIterator()", "hasHashEntries()");
    }

    @Override
    public Value getHashValuesIterator(Object languageContext, Object receiver) {
        PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;
        Object prev = PolyglotValueDispatch.hostEnter(context);
        try {
            try {
                throw PolyglotValueDispatch.getHashValuesIteratorUnsupported(context, receiver);
            }
            catch (Throwable e) {
                throw PolyglotValueDispatch.guestToHostException(context, e, true);
            }
        }
        catch (Throwable throwable) {
            PolyglotValueDispatch.hostLeave(context, prev);
            throw throwable;
        }
    }

    @Override
    public void pin(Object languageContext, Object receiver) {
        PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;
        Object prev = PolyglotValueDispatch.hostEnter(context);
        try {
            this.languageInstance.sharing.engine.host.pin(receiver);
        }
        catch (Throwable e) {
            throw PolyglotValueDispatch.guestToHostException(context, e, true);
        }
        finally {
            PolyglotValueDispatch.hostLeave(context, prev);
        }
    }

    @CompilerDirectives.TruffleBoundary
    static final RuntimeException getHashValuesIteratorUnsupported(PolyglotLanguageContext context, Object receiver) {
        throw PolyglotValueDispatch.unsupported(context, receiver, "getHashValuesIterator()", "hasHashEntries()");
    }

    protected Value getMetaObjectImpl(PolyglotLanguageContext context, Object receiver) {
        InteropLibrary lib = InteropLibrary.getFactory().getUncached(receiver);
        if (lib.hasMetaObject(receiver)) {
            try {
                return PolyglotValueDispatch.asValue(context, lib.getMetaObject(receiver));
            }
            catch (UnsupportedMessageException e) {
                throw CompilerDirectives.shouldNotReachHere("Unexpected unsupported message.", e);
            }
        }
        return null;
    }

    private static Value asValue(PolyglotLanguageContext context, Object value2) {
        if (context == null) {
            return PolyglotImpl.getInstance().asValue(PolyglotFastThreadLocals.getContext(null), value2);
        }
        return context.asValue(value2);
    }

    static Object hostEnter(Object languageContext) {
        if (languageContext == null) {
            return null;
        }
        PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;
        PolyglotContextImpl c = context.context;
        try {
            return c.engine.enterIfNeeded(c, true);
        }
        catch (Throwable t) {
            throw PolyglotValueDispatch.guestToHostException(context, t, false);
        }
    }

    static void hostLeave(Object languageContext, Object prev) {
        if (languageContext == null) {
            return;
        }
        PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;
        try {
            PolyglotContextImpl c = context.context;
            c.engine.leaveIfNeeded(prev, c);
        }
        catch (Throwable t) {
            throw PolyglotValueDispatch.guestToHostException(context, t, false);
        }
    }

    @CompilerDirectives.TruffleBoundary
    protected static RuntimeException unsupported(PolyglotLanguageContext context, Object receiver, String message, String useToCheck) {
        String polyglotMessage = useToCheck != null ? String.format("Unsupported operation %s.%s for %s. You can ensure that the operation is supported using %s.%s.", Value.class.getSimpleName(), message, PolyglotValueDispatch.getValueInfo(context, receiver), Value.class.getSimpleName(), useToCheck) : String.format("Unsupported operation %s.%s for %s.", Value.class.getSimpleName(), message, PolyglotValueDispatch.getValueInfo(context, receiver));
        return PolyglotEngineException.unsupported(polyglotMessage);
    }

    @CompilerDirectives.TruffleBoundary
    static String getValueInfo(Object languageContext, Object receiver) {
        PolyglotContextImpl context = languageContext != null ? ((PolyglotLanguageContext)languageContext).context : null;
        return PolyglotValueDispatch.getValueInfo(context, receiver);
    }

    @CompilerDirectives.TruffleBoundary
    static String getValueInfo(PolyglotContextImpl context, Object receiver) {
        String valueToString;
        Object view;
        if (context == null) {
            return receiver.toString();
        }
        if (receiver == null) {
            assert (false) : "receiver should never be null";
            return "null";
        }
        PolyglotLanguage displayLanguage = EngineAccessor.EngineImpl.findObjectLanguage(context.engine, receiver);
        if (displayLanguage == null) {
            displayLanguage = context.engine.hostLanguage;
            view = context.getHostContext().getLanguageView(receiver);
        } else {
            view = receiver;
        }
        String metaObjectToString = UNKNOWN;
        try {
            InteropLibrary uncached = InteropLibrary.getFactory().getUncached(view);
            if (uncached.hasMetaObject(view)) {
                Object qualifiedName = INTEROP.getMetaQualifiedName(uncached.getMetaObject(view));
                metaObjectToString = PolyglotValueDispatch.truncateString(INTEROP.asString(qualifiedName), 140);
            }
            valueToString = PolyglotValueDispatch.truncateString(INTEROP.asString(uncached.toDisplayString(view)), 140);
        }
        catch (UnsupportedMessageException e) {
            throw CompilerDirectives.shouldNotReachHere(e);
        }
        String languageName = null;
        boolean hideType = false;
        if (displayLanguage.isHost()) {
            languageName = "Java";
            if (UNKNOWN.equals(metaObjectToString) && INTEROP.isNull(receiver)) {
                hideType = true;
            }
        } else {
            languageName = displayLanguage.getName();
        }
        if (hideType) {
            return String.format("'%s'(language: %s)", valueToString, languageName);
        }
        return String.format("'%s'(language: %s, type: %s)", valueToString, languageName, metaObjectToString);
    }

    private static String truncateString(String s, int i) {
        if (s.length() > i) {
            return s.substring(0, i - TRUNCATION_SUFFIX.length()) + TRUNCATION_SUFFIX;
        }
        return s;
    }

    @CompilerDirectives.TruffleBoundary
    protected static RuntimeException nullCoercion(Object languageContext, Object receiver, Class<?> targetType, String message, String useToCheck) {
        assert (PolyglotValueDispatch.isEnteredOrNull(languageContext));
        String valueInfo = PolyglotValueDispatch.getValueInfo(languageContext, receiver);
        throw PolyglotEngineException.nullPointer(String.format("Cannot convert null value %s to Java type '%s' using %s.%s. You can ensure that the operation is supported using %s.%s.", valueInfo, targetType, Value.class.getSimpleName(), message, Value.class.getSimpleName(), useToCheck));
    }

    static boolean isEnteredOrNull(Object languageContext) {
        if (languageContext == null) {
            return true;
        }
        PolyglotContextImpl context = ((PolyglotLanguageContext)languageContext).context;
        return !context.engine.needsEnter(context);
    }

    @CompilerDirectives.TruffleBoundary
    protected static RuntimeException cannotConvert(Object languageContext, Object receiver, Class<?> targetType, String message, String useToCheck, String reason) {
        assert (PolyglotValueDispatch.isEnteredOrNull(languageContext));
        String valueInfo = PolyglotValueDispatch.getValueInfo(languageContext, receiver);
        String targetTypeString = "";
        if (targetType != null) {
            targetTypeString = String.format("to Java type '%s'", targetType.getTypeName());
        }
        throw PolyglotEngineException.classCast(String.format("Cannot convert %s %s using %s.%s: %s You can ensure that the value can be converted using %s.%s.", valueInfo, targetTypeString, Value.class.getSimpleName(), message, reason, Value.class.getSimpleName(), useToCheck));
    }

    @CompilerDirectives.TruffleBoundary
    protected static RuntimeException invalidArrayIndex(PolyglotLanguageContext context, Object receiver, long index) {
        String message = String.format("Invalid array index %s for array %s.", index, PolyglotValueDispatch.getValueInfo(context, receiver));
        throw PolyglotEngineException.arrayIndexOutOfBounds(message);
    }

    @CompilerDirectives.TruffleBoundary
    protected static RuntimeException invalidArrayValue(PolyglotLanguageContext context, Object receiver, long identifier, Object value2) {
        throw PolyglotEngineException.classCast(String.format("Invalid array value %s for array %s and index %s.", PolyglotValueDispatch.getValueInfo(context, value2), PolyglotValueDispatch.getValueInfo(context, receiver), identifier));
    }

    @CompilerDirectives.TruffleBoundary
    protected static RuntimeException nonReadableMemberKey(PolyglotLanguageContext context, Object receiver, String identifier) {
        String message = String.format("Non readable or non-existent member key '%s' for object %s.", identifier, PolyglotValueDispatch.getValueInfo(context, receiver));
        throw PolyglotEngineException.unsupported(message);
    }

    @CompilerDirectives.TruffleBoundary
    protected static RuntimeException nonWritableMemberKey(PolyglotLanguageContext context, Object receiver, String identifier) {
        String message = String.format("Non writable or non-existent member key '%s' for object %s.", identifier, PolyglotValueDispatch.getValueInfo(context, receiver));
        throw PolyglotEngineException.unsupported(message);
    }

    @CompilerDirectives.TruffleBoundary
    protected static RuntimeException nonRemovableMemberKey(PolyglotLanguageContext context, Object receiver, String identifier) {
        String message = String.format("Non removable or non-existent member key '%s' for object %s.", identifier, PolyglotValueDispatch.getValueInfo(context, receiver));
        throw PolyglotEngineException.unsupported(message);
    }

    @CompilerDirectives.TruffleBoundary
    protected static RuntimeException invalidMemberValue(PolyglotLanguageContext context, Object receiver, String identifier, Object value2) {
        String message = String.format("Invalid member value %s for object %s and member key '%s'.", PolyglotValueDispatch.getValueInfo(context, value2), PolyglotValueDispatch.getValueInfo(context, receiver), identifier);
        throw PolyglotEngineException.illegalArgument(message);
    }

    @CompilerDirectives.TruffleBoundary
    protected static RuntimeException stopIteration(PolyglotLanguageContext context, Object receiver) {
        String message = String.format("Iteration was stopped for iterator %s.", PolyglotValueDispatch.getValueInfo(context, receiver));
        throw PolyglotEngineException.noSuchElement(message);
    }

    @CompilerDirectives.TruffleBoundary
    protected static RuntimeException nonReadableIteratorElement() {
        throw PolyglotEngineException.unsupported("Iterator element is not readable.");
    }

    @CompilerDirectives.TruffleBoundary
    protected static RuntimeException invalidHashValue(PolyglotLanguageContext context, Object receiver, Object key, Object value2) {
        String message = String.format("Invalid hash value %s for object %s and hash key %s.", PolyglotValueDispatch.getValueInfo(context, value2), PolyglotValueDispatch.getValueInfo(context, receiver), PolyglotValueDispatch.getValueInfo(context, key));
        throw PolyglotEngineException.illegalArgument(message);
    }

    @CompilerDirectives.TruffleBoundary
    protected static RuntimeException invalidExecuteArgumentType(PolyglotLanguageContext context, Object receiver, UnsupportedTypeException e) {
        String originalMessage = e.getMessage() == null ? "" : e.getMessage() + " ";
        String[] formattedArgs = PolyglotValueDispatch.formatArgs(context, e.getSuppliedValues());
        throw PolyglotEngineException.illegalArgument(String.format("Invalid argument when executing %s. %sProvided arguments: %s.", PolyglotValueDispatch.getValueInfo(context, receiver), originalMessage, Arrays.asList(formattedArgs)));
    }

    @CompilerDirectives.TruffleBoundary
    protected static RuntimeException invalidInvokeArgumentType(PolyglotLanguageContext context, Object receiver, String member, UnsupportedTypeException e) {
        String originalMessage = e.getMessage() == null ? "" : e.getMessage();
        String[] formattedArgs = PolyglotValueDispatch.formatArgs(context, e.getSuppliedValues());
        String message = String.format("Invalid argument when invoking '%s' on %s. %sProvided arguments: %s.", member, PolyglotValueDispatch.getValueInfo(context, receiver), originalMessage, Arrays.asList(formattedArgs));
        throw PolyglotEngineException.illegalArgument(message);
    }

    @CompilerDirectives.TruffleBoundary
    protected static RuntimeException invalidInstantiateArgumentType(PolyglotLanguageContext context, Object receiver, Object[] arguments) {
        String[] formattedArgs = PolyglotValueDispatch.formatArgs(context, arguments);
        String message = String.format("Invalid argument when instantiating %s with arguments %s.", PolyglotValueDispatch.getValueInfo(context, receiver), Arrays.asList(formattedArgs));
        throw PolyglotEngineException.illegalArgument(message);
    }

    @CompilerDirectives.TruffleBoundary
    protected static RuntimeException invalidInstantiateArity(PolyglotLanguageContext context, Object receiver, Object[] arguments, int expectedMin, int expectedMax, int actual) {
        String[] formattedArgs = PolyglotValueDispatch.formatArgs(context, arguments);
        String message = String.format("Invalid argument count when instantiating %s with arguments %s. %s", PolyglotValueDispatch.getValueInfo(context, receiver), Arrays.asList(formattedArgs), PolyglotValueDispatch.formatExpectedArguments(expectedMin, expectedMax, actual));
        throw PolyglotEngineException.illegalArgument(message);
    }

    @CompilerDirectives.TruffleBoundary
    protected static RuntimeException invalidExecuteArity(PolyglotLanguageContext context, Object receiver, Object[] arguments, int expectedMin, int expectedMax, int actual) {
        String[] formattedArgs = PolyglotValueDispatch.formatArgs(context, arguments);
        String message = String.format("Invalid argument count when executing %s with arguments %s. %s", PolyglotValueDispatch.getValueInfo(context, receiver), Arrays.asList(formattedArgs), PolyglotValueDispatch.formatExpectedArguments(expectedMin, expectedMax, actual));
        throw PolyglotEngineException.illegalArgument(message);
    }

    @CompilerDirectives.TruffleBoundary
    protected static RuntimeException invalidInvokeArity(PolyglotLanguageContext context, Object receiver, String member, Object[] arguments, int expectedMin, int expectedMax, int actual) {
        String[] formattedArgs = PolyglotValueDispatch.formatArgs(context, arguments);
        String message = String.format("Invalid argument count when invoking '%s' on %s with arguments %s. %s", member, PolyglotValueDispatch.getValueInfo(context, receiver), Arrays.asList(formattedArgs), PolyglotValueDispatch.formatExpectedArguments(expectedMin, expectedMax, actual));
        throw PolyglotEngineException.illegalArgument(message);
    }

    static String formatExpectedArguments(int expectedMinArity, int expectedMaxArity, int actualArity) {
        String actual = actualArity < 0 ? "unknown" : String.valueOf(actualArity);
        Object expected = expectedMinArity == expectedMaxArity ? String.valueOf(expectedMinArity) : (expectedMaxArity < 0 ? expectedMinArity + "+" : expectedMinArity + "-" + expectedMaxArity);
        return String.format("Expected %s argument(s) but got %s.", expected, actual);
    }

    private static String[] formatArgs(Object languageContext, Object[] arguments) {
        String[] formattedArgs = new String[arguments.length];
        for (int i = 0; i < arguments.length; ++i) {
            formattedArgs[i] = PolyglotValueDispatch.getValueInfo(languageContext, arguments[i]);
        }
        return formattedArgs;
    }

    @Override
    public final String toString(Object languageContext, Object receiver) {
        PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;
        Object prev = PolyglotValueDispatch.hostEnter(context);
        try {
            String string = this.toStringImpl(context, receiver);
            return string;
        }
        catch (Throwable e) {
            throw PolyglotValueDispatch.guestToHostException(context, e, true);
        }
        finally {
            PolyglotValueDispatch.hostLeave(context, prev);
        }
    }

    protected String toStringImpl(Object languageContext, Object receiver) throws AssertionError {
        InteropLibrary lib = InteropLibrary.getFactory().getUncached(receiver);
        Object result = lib.toDisplayString(receiver);
        InteropLibrary resultLib = InteropLibrary.getFactory().getUncached(result);
        try {
            return resultLib.asString(result);
        }
        catch (UnsupportedMessageException e) {
            throw CompilerDirectives.shouldNotReachHere("toDisplayString must be coercible to java.lang.String, but is not.", e);
        }
    }

    @Override
    public org.graalvm.polyglot.SourceSection getSourceLocation(Object languageContext, Object receiver) {
        if (languageContext == null) {
            return null;
        }
        PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;
        Object prev = PolyglotValueDispatch.hostEnter(context);
        try {
            InteropLibrary lib = InteropLibrary.getFactory().getUncached(receiver);
            SourceSection result = null;
            if (lib.hasSourceLocation(receiver)) {
                try {
                    result = lib.getSourceLocation(receiver);
                }
                catch (UnsupportedMessageException unsupportedMessageException) {
                    // empty catch block
                }
            }
            if (result == null) {
                org.graalvm.polyglot.SourceSection sourceSection = null;
                return sourceSection;
            }
            org.graalvm.polyglot.SourceSection sourceSection = PolyglotImpl.getPolyglotSourceSection(this.impl, result);
            return sourceSection;
        }
        catch (Throwable e) {
            throw PolyglotValueDispatch.guestToHostException(context, e, true);
        }
        finally {
            PolyglotValueDispatch.hostLeave(context, prev);
        }
    }

    @Override
    public boolean isMetaObject(Object languageContext, Object receiver) {
        return false;
    }

    @Override
    public boolean equalsImpl(Object languageContext, Object receiver, Object obj) {
        if (receiver == obj) {
            return true;
        }
        return PolyglotWrapper.equals(languageContext, receiver, obj);
    }

    @Override
    public int hashCodeImpl(Object languageContext, Object receiver) {
        return PolyglotWrapper.hashCode(languageContext, receiver);
    }

    @Override
    public boolean isMetaInstance(Object languageContext, Object receiver, Object instance) {
        PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;
        Object prev = PolyglotValueDispatch.hostEnter(context);
        try {
            try {
                throw PolyglotValueDispatch.unsupported(context, receiver, "isMetaInstance(Object)", "isMetaObject()");
            }
            catch (Throwable e) {
                throw PolyglotValueDispatch.guestToHostException(context, e, true);
            }
        }
        catch (Throwable throwable) {
            PolyglotValueDispatch.hostLeave(context, prev);
            throw throwable;
        }
    }

    @Override
    public String getMetaQualifiedName(Object languageContext, Object receiver) {
        PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;
        Object prev = PolyglotValueDispatch.hostEnter(context);
        try {
            try {
                throw PolyglotValueDispatch.unsupported(context, receiver, "getMetaQualifiedName()", "isMetaObject()");
            }
            catch (Throwable e) {
                throw PolyglotValueDispatch.guestToHostException(context, e, true);
            }
        }
        catch (Throwable throwable) {
            PolyglotValueDispatch.hostLeave(context, prev);
            throw throwable;
        }
    }

    @Override
    public String getMetaSimpleName(Object languageContext, Object receiver) {
        PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;
        Object prev = PolyglotValueDispatch.hostEnter(context);
        try {
            try {
                throw PolyglotValueDispatch.unsupported(context, receiver, "getMetaSimpleName()", "isMetaObject()");
            }
            catch (Throwable e) {
                throw PolyglotValueDispatch.guestToHostException(context, e, true);
            }
        }
        catch (Throwable throwable) {
            PolyglotValueDispatch.hostLeave(context, prev);
            throw throwable;
        }
    }

    @Override
    public boolean hasMetaParents(Object languageContext, Object receiver) {
        return false;
    }

    @Override
    public Value getMetaParents(Object languageContext, Object receiver) {
        PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;
        Object prev = PolyglotValueDispatch.hostEnter(context);
        try {
            try {
                throw PolyglotValueDispatch.unsupported(context, receiver, "getMetaParents()", "hasMetaParents()");
            }
            catch (Throwable e) {
                throw PolyglotValueDispatch.guestToHostException(context, e, true);
            }
        }
        catch (Throwable throwable) {
            PolyglotValueDispatch.hostLeave(context, prev);
            throw throwable;
        }
    }

    static CallTarget createTarget(InteropNode root) {
        RootCallTarget target = root.getCallTarget();
        Class<?>[] types = root.getArgumentTypes();
        if (types != null) {
            EngineAccessor.RUNTIME.initializeProfile(target, types);
        }
        return target;
    }

    static PolyglotValueDispatch createInteropValue(PolyglotLanguageInstance languageInstance, TruffleObject receiver, Class<?> receiverType) {
        return new InteropValue(languageInstance.getImpl(), languageInstance, receiver, receiverType);
    }

    static PolyglotValueDispatch createHostNull(PolyglotImpl polyglot) {
        return new HostNull(polyglot);
    }

    static void createDefaultValues(PolyglotImpl polyglot, PolyglotLanguageInstance languageInstance, Map<Class<?>, PolyglotValueDispatch> valueCache) {
        PolyglotValueDispatch.addDefaultValue(polyglot, languageInstance, valueCache, false);
        PolyglotValueDispatch.addDefaultValue(polyglot, languageInstance, valueCache, "");
        PolyglotValueDispatch.addDefaultValue(polyglot, languageInstance, valueCache, TruffleString.fromJavaStringUncached("", TruffleString.Encoding.UTF_16));
        PolyglotValueDispatch.addDefaultValue(polyglot, languageInstance, valueCache, Character.valueOf('a'));
        PolyglotValueDispatch.addDefaultValue(polyglot, languageInstance, valueCache, (byte)0);
        PolyglotValueDispatch.addDefaultValue(polyglot, languageInstance, valueCache, (short)0);
        PolyglotValueDispatch.addDefaultValue(polyglot, languageInstance, valueCache, 0);
        PolyglotValueDispatch.addDefaultValue(polyglot, languageInstance, valueCache, 0L);
        PolyglotValueDispatch.addDefaultValue(polyglot, languageInstance, valueCache, Float.valueOf(0.0f));
        PolyglotValueDispatch.addDefaultValue(polyglot, languageInstance, valueCache, 0.0);
    }

    static void addDefaultValue(PolyglotImpl polyglot, PolyglotLanguageInstance languageInstance, Map<Class<?>, PolyglotValueDispatch> valueCache, Object primitive) {
        valueCache.put(primitive.getClass(), new PrimitiveValue(polyglot, languageInstance, primitive));
    }

    static final class InteropValue
    extends PolyglotValueDispatch {
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
            this.asClassLiteral = InteropValue.createTarget(new AsClassLiteralNode(this));
            this.asTypeLiteral = InteropValue.createTarget(new AsTypeLiteralNode(this));
            this.isNativePointer = InteropValue.createTarget(PolyglotValueDispatchFactory.InteropValueFactory.IsNativePointerNodeGen.create(this));
            this.asNativePointer = InteropValue.createTarget(PolyglotValueDispatchFactory.InteropValueFactory.AsNativePointerNodeGen.create(this));
            this.hasArrayElements = InteropValue.createTarget(PolyglotValueDispatchFactory.InteropValueFactory.HasArrayElementsNodeGen.create(this));
            this.getArrayElement = InteropValue.createTarget(PolyglotValueDispatchFactory.InteropValueFactory.GetArrayElementNodeGen.create(this));
            this.setArrayElement = InteropValue.createTarget(PolyglotValueDispatchFactory.InteropValueFactory.SetArrayElementNodeGen.create(this));
            this.removeArrayElement = InteropValue.createTarget(PolyglotValueDispatchFactory.InteropValueFactory.RemoveArrayElementNodeGen.create(this));
            this.getArraySize = InteropValue.createTarget(PolyglotValueDispatchFactory.InteropValueFactory.GetArraySizeNodeGen.create(this));
            this.hasBufferElements = InteropValue.createTarget(PolyglotValueDispatchFactory.InteropValueFactory.HasBufferElementsNodeGen.create(this));
            this.isBufferWritable = InteropValue.createTarget(PolyglotValueDispatchFactory.InteropValueFactory.IsBufferWritableNodeGen.create(this));
            this.getBufferSize = InteropValue.createTarget(PolyglotValueDispatchFactory.InteropValueFactory.GetBufferSizeNodeGen.create(this));
            this.readBufferByte = InteropValue.createTarget(PolyglotValueDispatchFactory.InteropValueFactory.ReadBufferByteNodeGen.create(this));
            this.writeBufferByte = InteropValue.createTarget(PolyglotValueDispatchFactory.InteropValueFactory.WriteBufferByteNodeGen.create(this));
            this.readBufferShort = InteropValue.createTarget(PolyglotValueDispatchFactory.InteropValueFactory.ReadBufferShortNodeGen.create(this));
            this.writeBufferShort = InteropValue.createTarget(PolyglotValueDispatchFactory.InteropValueFactory.WriteBufferShortNodeGen.create(this));
            this.readBufferInt = InteropValue.createTarget(PolyglotValueDispatchFactory.InteropValueFactory.ReadBufferIntNodeGen.create(this));
            this.writeBufferInt = InteropValue.createTarget(PolyglotValueDispatchFactory.InteropValueFactory.WriteBufferIntNodeGen.create(this));
            this.readBufferLong = InteropValue.createTarget(PolyglotValueDispatchFactory.InteropValueFactory.ReadBufferLongNodeGen.create(this));
            this.writeBufferLong = InteropValue.createTarget(PolyglotValueDispatchFactory.InteropValueFactory.WriteBufferLongNodeGen.create(this));
            this.readBufferFloat = InteropValue.createTarget(PolyglotValueDispatchFactory.InteropValueFactory.ReadBufferFloatNodeGen.create(this));
            this.writeBufferFloat = InteropValue.createTarget(PolyglotValueDispatchFactory.InteropValueFactory.WriteBufferFloatNodeGen.create(this));
            this.readBufferDouble = InteropValue.createTarget(PolyglotValueDispatchFactory.InteropValueFactory.ReadBufferDoubleNodeGen.create(this));
            this.writeBufferDouble = InteropValue.createTarget(PolyglotValueDispatchFactory.InteropValueFactory.WriteBufferDoubleNodeGen.create(this));
            this.hasMember = InteropValue.createTarget(PolyglotValueDispatchFactory.InteropValueFactory.HasMemberNodeGen.create(this));
            this.getMember = InteropValue.createTarget(PolyglotValueDispatchFactory.InteropValueFactory.GetMemberNodeGen.create(this));
            this.putMember = InteropValue.createTarget(PolyglotValueDispatchFactory.InteropValueFactory.PutMemberNodeGen.create(this));
            this.removeMember = InteropValue.createTarget(PolyglotValueDispatchFactory.InteropValueFactory.RemoveMemberNodeGen.create(this));
            this.isNull = InteropValue.createTarget(PolyglotValueDispatchFactory.InteropValueFactory.IsNullNodeGen.create(this));
            this.execute = InteropValue.createTarget(new ExecuteNode(this));
            this.executeNoArgs = InteropValue.createTarget(new ExecuteNoArgsNode(this));
            this.executeVoid = InteropValue.createTarget(new ExecuteVoidNode(this));
            this.executeVoidNoArgs = InteropValue.createTarget(new ExecuteVoidNoArgsNode(this));
            this.newInstance = InteropValue.createTarget(PolyglotValueDispatchFactory.InteropValueFactory.NewInstanceNodeGen.create(this));
            this.canInstantiate = InteropValue.createTarget(PolyglotValueDispatchFactory.InteropValueFactory.CanInstantiateNodeGen.create(this));
            this.canExecute = InteropValue.createTarget(PolyglotValueDispatchFactory.InteropValueFactory.CanExecuteNodeGen.create(this));
            this.canInvoke = InteropValue.createTarget(PolyglotValueDispatchFactory.InteropValueFactory.CanInvokeNodeGen.create(this));
            this.invoke = InteropValue.createTarget(new InvokeNode(this));
            this.invokeNoArgs = InteropValue.createTarget(new InvokeNoArgsNode(this));
            this.hasMembers = InteropValue.createTarget(PolyglotValueDispatchFactory.InteropValueFactory.HasMembersNodeGen.create(this));
            this.getMemberKeys = InteropValue.createTarget(PolyglotValueDispatchFactory.InteropValueFactory.GetMemberKeysNodeGen.create(this));
            this.isDate = InteropValue.createTarget(PolyglotValueDispatchFactory.InteropValueFactory.IsDateNodeGen.create(this));
            this.asDate = InteropValue.createTarget(PolyglotValueDispatchFactory.InteropValueFactory.AsDateNodeGen.create(this));
            this.isTime = InteropValue.createTarget(PolyglotValueDispatchFactory.InteropValueFactory.IsTimeNodeGen.create(this));
            this.asTime = InteropValue.createTarget(PolyglotValueDispatchFactory.InteropValueFactory.AsTimeNodeGen.create(this));
            this.isTimeZone = InteropValue.createTarget(PolyglotValueDispatchFactory.InteropValueFactory.IsTimeZoneNodeGen.create(this));
            this.asTimeZone = InteropValue.createTarget(PolyglotValueDispatchFactory.InteropValueFactory.AsTimeZoneNodeGen.create(this));
            this.asInstant = InteropValue.createTarget(PolyglotValueDispatchFactory.InteropValueFactory.AsInstantNodeGen.create(this));
            this.isDuration = InteropValue.createTarget(PolyglotValueDispatchFactory.InteropValueFactory.IsDurationNodeGen.create(this));
            this.asDuration = InteropValue.createTarget(PolyglotValueDispatchFactory.InteropValueFactory.AsDurationNodeGen.create(this));
            this.isException = InteropValue.createTarget(PolyglotValueDispatchFactory.InteropValueFactory.IsExceptionNodeGen.create(this));
            this.throwException = InteropValue.createTarget(PolyglotValueDispatchFactory.InteropValueFactory.ThrowExceptionNodeGen.create(this));
            this.isMetaObject = InteropValue.createTarget(PolyglotValueDispatchFactory.InteropValueFactory.IsMetaObjectNodeGen.create(this));
            this.isMetaInstance = InteropValue.createTarget(PolyglotValueDispatchFactory.InteropValueFactory.IsMetaInstanceNodeGen.create(this));
            this.getMetaQualifiedName = InteropValue.createTarget(PolyglotValueDispatchFactory.InteropValueFactory.GetMetaQualifiedNameNodeGen.create(this));
            this.getMetaSimpleName = InteropValue.createTarget(PolyglotValueDispatchFactory.InteropValueFactory.GetMetaSimpleNameNodeGen.create(this));
            this.hasMetaParents = InteropValue.createTarget(PolyglotValueDispatchFactory.InteropValueFactory.HasMetaParentsNodeGen.create(this));
            this.getMetaParents = InteropValue.createTarget(PolyglotValueDispatchFactory.InteropValueFactory.GetMetaParentsNodeGen.create(this));
            this.hasIterator = InteropValue.createTarget(PolyglotValueDispatchFactory.InteropValueFactory.HasIteratorNodeGen.create(this));
            this.getIterator = InteropValue.createTarget(PolyglotValueDispatchFactory.InteropValueFactory.GetIteratorNodeGen.create(this));
            this.isIterator = InteropValue.createTarget(PolyglotValueDispatchFactory.InteropValueFactory.IsIteratorNodeGen.create(this));
            this.hasIteratorNextElement = InteropValue.createTarget(PolyglotValueDispatchFactory.InteropValueFactory.HasIteratorNextElementNodeGen.create(this));
            this.getIteratorNextElement = InteropValue.createTarget(PolyglotValueDispatchFactory.InteropValueFactory.GetIteratorNextElementNodeGen.create(this));
            this.hasHashEntries = InteropValue.createTarget(PolyglotValueDispatchFactory.InteropValueFactory.HasHashEntriesNodeGen.create(this));
            this.getHashSize = InteropValue.createTarget(PolyglotValueDispatchFactory.InteropValueFactory.GetHashSizeNodeGen.create(this));
            this.hasHashEntry = InteropValue.createTarget(PolyglotValueDispatchFactory.InteropValueFactory.HasHashEntryNodeGen.create(this));
            this.getHashValue = InteropValue.createTarget(PolyglotValueDispatchFactory.InteropValueFactory.GetHashValueNodeGen.create(this));
            this.getHashValueOrDefault = InteropValue.createTarget(PolyglotValueDispatchFactory.InteropValueFactory.GetHashValueOrDefaultNodeGen.create(this));
            this.putHashEntry = InteropValue.createTarget(PolyglotValueDispatchFactory.InteropValueFactory.PutHashEntryNodeGen.create(this));
            this.removeHashEntry = InteropValue.createTarget(PolyglotValueDispatchFactory.InteropValueFactory.RemoveHashEntryNodeGen.create(this));
            this.getHashEntriesIterator = InteropValue.createTarget(PolyglotValueDispatchFactory.InteropValueFactory.GetHashEntriesIteratorNodeGen.create(this));
            this.getHashKeysIterator = InteropValue.createTarget(PolyglotValueDispatchFactory.InteropValueFactory.GetHashKeysIteratorNodeGen.create(this));
            this.getHashValuesIterator = InteropValue.createTarget(PolyglotValueDispatchFactory.InteropValueFactory.GetHashValuesIteratorNodeGen.create(this));
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
        public void setArrayElement(Object languageContext, Object receiver, long index, Object value2) {
            EngineAccessor.RUNTIME.callProfiled(this.setArrayElement, languageContext, receiver, index, value2);
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
        public void writeBufferByte(Object languageContext, Object receiver, long byteOffset, byte value2) throws UnsupportedOperationException, IndexOutOfBoundsException {
            EngineAccessor.RUNTIME.callProfiled(this.writeBufferByte, languageContext, receiver, byteOffset, value2);
        }

        @Override
        public short readBufferShort(Object languageContext, Object receiver, ByteOrder order, long byteOffset) throws UnsupportedOperationException, IndexOutOfBoundsException {
            return (Short)EngineAccessor.RUNTIME.callProfiled(this.readBufferShort, languageContext, receiver, order, byteOffset);
        }

        @Override
        public void writeBufferShort(Object languageContext, Object receiver, ByteOrder order, long byteOffset, short value2) throws UnsupportedOperationException, IndexOutOfBoundsException {
            EngineAccessor.RUNTIME.callProfiled(this.writeBufferShort, languageContext, receiver, order, byteOffset, value2);
        }

        @Override
        public int readBufferInt(Object languageContext, Object receiver, ByteOrder order, long byteOffset) throws UnsupportedOperationException, IndexOutOfBoundsException {
            return (Integer)EngineAccessor.RUNTIME.callProfiled(this.readBufferInt, languageContext, receiver, order, byteOffset);
        }

        @Override
        public void writeBufferInt(Object languageContext, Object receiver, ByteOrder order, long byteOffset, int value2) throws UnsupportedOperationException, IndexOutOfBoundsException {
            EngineAccessor.RUNTIME.callProfiled(this.writeBufferInt, languageContext, receiver, order, byteOffset, value2);
        }

        @Override
        public long readBufferLong(Object languageContext, Object receiver, ByteOrder order, long byteOffset) throws UnsupportedOperationException, IndexOutOfBoundsException {
            return (Long)EngineAccessor.RUNTIME.callProfiled(this.readBufferLong, languageContext, receiver, order, byteOffset);
        }

        @Override
        public void writeBufferLong(Object languageContext, Object receiver, ByteOrder order, long byteOffset, long value2) throws UnsupportedOperationException, IndexOutOfBoundsException {
            EngineAccessor.RUNTIME.callProfiled(this.writeBufferLong, languageContext, receiver, order, byteOffset, value2);
        }

        @Override
        public float readBufferFloat(Object languageContext, Object receiver, ByteOrder order, long byteOffset) throws UnsupportedOperationException, IndexOutOfBoundsException {
            return ((Float)EngineAccessor.RUNTIME.callProfiled(this.readBufferFloat, languageContext, receiver, order, byteOffset)).floatValue();
        }

        @Override
        public void writeBufferFloat(Object languageContext, Object receiver, ByteOrder order, long byteOffset, float value2) throws UnsupportedOperationException, IndexOutOfBoundsException {
            EngineAccessor.RUNTIME.callProfiled(this.writeBufferFloat, languageContext, receiver, order, byteOffset, Float.valueOf(value2));
        }

        @Override
        public double readBufferDouble(Object languageContext, Object receiver, ByteOrder order, long byteOffset) throws UnsupportedOperationException, IndexOutOfBoundsException {
            return (Double)EngineAccessor.RUNTIME.callProfiled(this.readBufferDouble, languageContext, receiver, order, byteOffset);
        }

        @Override
        public void writeBufferDouble(Object languageContext, Object receiver, ByteOrder order, long byteOffset, double value2) throws UnsupportedOperationException, IndexOutOfBoundsException {
            EngineAccessor.RUNTIME.callProfiled(this.writeBufferDouble, languageContext, receiver, order, byteOffset, value2);
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
            if (keys == null) {
                return Collections.emptySet();
            }
            return new MemberSet(languageContext, receiver, keys);
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
            Object prev = InteropValue.hostEnter(context);
            try {
                boolean bl = this.getEngine().host.isHostObject(receiver);
                return bl;
            }
            catch (Throwable e) {
                throw InteropValue.guestToHostException(context, e, true);
            }
            finally {
                InteropValue.hostLeave(context, prev);
            }
        }

        private PolyglotEngineImpl getEngine() {
            return this.languageInstance.sharing.engine;
        }

        @Override
        public boolean isProxyObject(Object languageContext, Object receiver) {
            PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;
            Object prev = InteropValue.hostEnter(context);
            try {
                boolean bl = this.getEngine().host.isHostProxy(receiver);
                return bl;
            }
            catch (Throwable e) {
                throw InteropValue.guestToHostException(context, e, true);
            }
            finally {
                InteropValue.hostLeave(context, prev);
            }
        }

        @Override
        public Object asProxyObject(Object languageContext, Object receiver) {
            if (this.isProxyObject(languageContext, receiver)) {
                return this.getEngine().host.unboxProxyObject(receiver);
            }
            return super.asProxyObject(languageContext, receiver);
        }

        @Override
        public Object asHostObject(Object languageContext, Object receiver) {
            if (this.isHostObject(languageContext, receiver)) {
                return this.getEngine().host.unboxHostObject(receiver);
            }
            return super.asHostObject(languageContext, receiver);
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
            Object c = InteropValue.hostEnter(context);
            try {
                boolean bl = UNCACHED_INTEROP.isNumber(receiver);
                return bl;
            }
            catch (Throwable e) {
                throw InteropValue.guestToHostException(context, e, true);
            }
            finally {
                InteropValue.hostLeave(context, c);
            }
        }

        @Override
        public boolean fitsInByte(Object languageContext, Object receiver) {
            PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;
            Object c = InteropValue.hostEnter(context);
            try {
                boolean bl = UNCACHED_INTEROP.fitsInByte(receiver);
                return bl;
            }
            catch (Throwable e) {
                throw InteropValue.guestToHostException(context, e, true);
            }
            finally {
                InteropValue.hostLeave(context, c);
            }
        }

        @Override
        public byte asByte(Object languageContext, Object receiver) {
            PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;
            Object c = InteropValue.hostEnter(context);
            try {
                byte by = UNCACHED_INTEROP.asByte(receiver);
                return by;
            }
            catch (UnsupportedMessageException e) {
                byte by = InteropValue.asByteUnsupported(context, receiver);
                return by;
            }
            catch (Throwable e) {
                throw InteropValue.guestToHostException(context, e, true);
            }
            finally {
                InteropValue.hostLeave(context, c);
            }
        }

        @Override
        public boolean isString(Object languageContext, Object receiver) {
            PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;
            Object c = InteropValue.hostEnter(context);
            try {
                boolean bl = UNCACHED_INTEROP.isString(receiver);
                return bl;
            }
            catch (Throwable e) {
                throw InteropValue.guestToHostException(context, e, true);
            }
            finally {
                InteropValue.hostLeave(context, c);
            }
        }

        @Override
        public String asString(Object languageContext, Object receiver) {
            PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;
            Object c = InteropValue.hostEnter(context);
            try {
                if (PolyglotValueDispatch.isNullUncached(receiver)) {
                    String string = null;
                    return string;
                }
                String string = UNCACHED_INTEROP.asString(receiver);
                return string;
            }
            catch (UnsupportedMessageException e) {
                String string = InteropValue.asStringUnsupported(context, receiver);
                return string;
            }
            catch (Throwable e) {
                throw InteropValue.guestToHostException(context, e, true);
            }
            finally {
                InteropValue.hostLeave(context, c);
            }
        }

        @Override
        public boolean fitsInInt(Object languageContext, Object receiver) {
            PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;
            Object c = InteropValue.hostEnter(context);
            try {
                boolean bl = UNCACHED_INTEROP.fitsInInt(receiver);
                return bl;
            }
            catch (Throwable e) {
                throw InteropValue.guestToHostException(context, e, true);
            }
            finally {
                InteropValue.hostLeave(context, c);
            }
        }

        @Override
        public int asInt(Object languageContext, Object receiver) {
            PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;
            Object c = InteropValue.hostEnter(context);
            try {
                int n = UNCACHED_INTEROP.asInt(receiver);
                return n;
            }
            catch (UnsupportedMessageException e) {
                int n = InteropValue.asIntUnsupported(context, receiver);
                return n;
            }
            catch (Throwable e) {
                throw InteropValue.guestToHostException(context, e, true);
            }
            finally {
                InteropValue.hostLeave(context, c);
            }
        }

        @Override
        public boolean isBoolean(Object languageContext, Object receiver) {
            PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;
            Object c = InteropValue.hostEnter(context);
            try {
                boolean bl = InteropLibrary.getFactory().getUncached().isBoolean(receiver);
                return bl;
            }
            catch (Throwable e) {
                throw InteropValue.guestToHostException(context, e, true);
            }
            finally {
                InteropValue.hostLeave(context, c);
            }
        }

        @Override
        public boolean asBoolean(Object languageContext, Object receiver) {
            PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;
            Object c = InteropValue.hostEnter(context);
            try {
                boolean bl = InteropLibrary.getFactory().getUncached().asBoolean(receiver);
                return bl;
            }
            catch (UnsupportedMessageException e) {
                boolean bl = InteropValue.asBooleanUnsupported(context, receiver);
                return bl;
            }
            catch (Throwable e) {
                throw InteropValue.guestToHostException(context, e, true);
            }
            finally {
                InteropValue.hostLeave(context, c);
            }
        }

        @Override
        public boolean fitsInFloat(Object languageContext, Object receiver) {
            PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;
            Object c = InteropValue.hostEnter(context);
            try {
                boolean bl = InteropLibrary.getFactory().getUncached().fitsInFloat(receiver);
                return bl;
            }
            catch (Throwable e) {
                throw InteropValue.guestToHostException(context, e, true);
            }
            finally {
                InteropValue.hostLeave(context, c);
            }
        }

        @Override
        public float asFloat(Object languageContext, Object receiver) {
            PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;
            Object c = InteropValue.hostEnter(context);
            try {
                float f = UNCACHED_INTEROP.asFloat(receiver);
                return f;
            }
            catch (UnsupportedMessageException e) {
                float f = InteropValue.asFloatUnsupported(context, receiver);
                return f;
            }
            catch (Throwable e) {
                throw InteropValue.guestToHostException(context, e, true);
            }
            finally {
                InteropValue.hostLeave(context, c);
            }
        }

        @Override
        public boolean fitsInDouble(Object languageContext, Object receiver) {
            PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;
            Object c = InteropValue.hostEnter(context);
            try {
                boolean bl = UNCACHED_INTEROP.fitsInDouble(receiver);
                return bl;
            }
            catch (Throwable e) {
                throw InteropValue.guestToHostException(context, e, true);
            }
            finally {
                InteropValue.hostLeave(context, c);
            }
        }

        @Override
        public double asDouble(Object languageContext, Object receiver) {
            PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;
            Object c = InteropValue.hostEnter(context);
            try {
                double d = UNCACHED_INTEROP.asDouble(receiver);
                return d;
            }
            catch (UnsupportedMessageException e) {
                double d = InteropValue.asDoubleUnsupported(context, receiver);
                return d;
            }
            catch (Throwable e) {
                throw InteropValue.guestToHostException(context, e, true);
            }
            finally {
                InteropValue.hostLeave(context, c);
            }
        }

        @Override
        public boolean fitsInLong(Object languageContext, Object receiver) {
            PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;
            Object c = InteropValue.hostEnter(context);
            try {
                boolean bl = UNCACHED_INTEROP.fitsInLong(receiver);
                return bl;
            }
            catch (Throwable e) {
                throw InteropValue.guestToHostException(context, e, true);
            }
            finally {
                InteropValue.hostLeave(context, c);
            }
        }

        @Override
        public long asLong(Object languageContext, Object receiver) {
            PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;
            Object c = InteropValue.hostEnter(context);
            try {
                long l = UNCACHED_INTEROP.asLong(receiver);
                return l;
            }
            catch (UnsupportedMessageException e) {
                long l = InteropValue.asLongUnsupported(context, receiver);
                return l;
            }
            catch (Throwable e) {
                throw InteropValue.guestToHostException(context, e, true);
            }
            finally {
                InteropValue.hostLeave(context, c);
            }
        }

        @Override
        public boolean fitsInShort(Object languageContext, Object receiver) {
            PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;
            Object c = InteropValue.hostEnter(context);
            try {
                boolean bl = UNCACHED_INTEROP.fitsInShort(receiver);
                return bl;
            }
            catch (Throwable e) {
                throw InteropValue.guestToHostException(context, e, true);
            }
            finally {
                InteropValue.hostLeave(context, c);
            }
        }

        @Override
        public short asShort(Object languageContext, Object receiver) {
            PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;
            Object c = InteropValue.hostEnter(context);
            try {
                short s = UNCACHED_INTEROP.asShort(receiver);
                return s;
            }
            catch (UnsupportedMessageException e) {
                short s = InteropValue.asShortUnsupported(context, receiver);
                return s;
            }
            catch (Throwable e) {
                throw InteropValue.guestToHostException(context, e, true);
            }
            finally {
                InteropValue.hostLeave(context, c);
            }
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
        public void putHashEntry(Object languageContext, Object receiver, Object key, Object value2) {
            EngineAccessor.RUNTIME.callProfiled(this.putHashEntry, languageContext, receiver, key, value2);
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

        static abstract class GetHashValuesIteratorNode
        extends InteropNode {
            GetHashValuesIteratorNode(InteropValue interop) {
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

            @Specialization(limit="CACHE_LIMIT")
            static Object doCached(PolyglotLanguageContext context, Object receiver, Object[] args, @CachedLibrary(value="receiver") InteropLibrary hashes, @Cached(value="createToHost()") PolyglotLanguageContext.ToHostValueNode toHost, @Cached BranchProfile unsupported) {
                try {
                    return toHost.execute(context, hashes.getHashValuesIterator(receiver));
                }
                catch (UnsupportedMessageException e) {
                    unsupported.enter();
                    throw PolyglotValueDispatch.getHashEntriesIteratorUnsupported(context, receiver);
                }
            }
        }

        static abstract class GetHashKeysIteratorNode
        extends InteropNode {
            GetHashKeysIteratorNode(InteropValue interop) {
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

            @Specialization(limit="CACHE_LIMIT")
            static Object doCached(PolyglotLanguageContext context, Object receiver, Object[] args, @CachedLibrary(value="receiver") InteropLibrary hashes, @Cached(value="createToHost()") PolyglotLanguageContext.ToHostValueNode toHost, @Cached BranchProfile unsupported) {
                try {
                    return toHost.execute(context, hashes.getHashKeysIterator(receiver));
                }
                catch (UnsupportedMessageException e) {
                    unsupported.enter();
                    throw PolyglotValueDispatch.getHashEntriesIteratorUnsupported(context, receiver);
                }
            }
        }

        static abstract class GetHashEntriesIteratorNode
        extends InteropNode {
            GetHashEntriesIteratorNode(InteropValue interop) {
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

            @Specialization(limit="CACHE_LIMIT")
            static Object doCached(PolyglotLanguageContext context, Object receiver, Object[] args, @CachedLibrary(value="receiver") InteropLibrary hashes, @Cached(value="createToHost()") PolyglotLanguageContext.ToHostValueNode toHost, @Cached BranchProfile unsupported) {
                try {
                    return toHost.execute(context, hashes.getHashEntriesIterator(receiver));
                }
                catch (UnsupportedMessageException e) {
                    unsupported.enter();
                    throw PolyglotValueDispatch.getHashEntriesIteratorUnsupported(context, receiver);
                }
            }
        }

        static abstract class RemoveHashEntryNode
        extends InteropNode {
            protected RemoveHashEntryNode(InteropValue interop) {
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

            @Specialization(limit="CACHE_LIMIT")
            static Object doCached(PolyglotLanguageContext context, Object receiver, Object[] args, @CachedLibrary(value="receiver") InteropLibrary hashes, @Cached PolyglotLanguageContext.ToGuestValueNode toGuestKey, @Cached BranchProfile unsupported, @Cached BranchProfile invalidKey) {
                Boolean result;
                Object hostKey = args[2];
                Object key = toGuestKey.execute(context, hostKey);
                try {
                    hashes.removeHashEntry(receiver, key);
                    result = Boolean.TRUE;
                }
                catch (UnsupportedMessageException e) {
                    unsupported.enter();
                    if (!hashes.hasHashEntries(receiver) || hashes.isHashEntryExisting(receiver, key)) {
                        throw PolyglotValueDispatch.removeHashEntryUnsupported(context, receiver, key);
                    }
                    result = Boolean.FALSE;
                }
                catch (UnknownKeyException e) {
                    invalidKey.enter();
                    result = Boolean.FALSE;
                }
                return result;
            }
        }

        static abstract class PutHashEntryNode
        extends InteropNode {
            protected PutHashEntryNode(InteropValue interop) {
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

            @Specialization(limit="CACHE_LIMIT")
            static Object doCached(PolyglotLanguageContext context, Object receiver, Object[] args, @CachedLibrary(value="receiver") InteropLibrary hashes, @Cached PolyglotLanguageContext.ToGuestValueNode toGuestKey, @Cached PolyglotLanguageContext.ToGuestValueNode toGuestValue, @Cached BranchProfile unsupported, @Cached BranchProfile invalidKey, @Cached BranchProfile invalidValue) {
                Object hostKey = args[2];
                Object hostValue = args[3];
                Object key = toGuestKey.execute(context, hostKey);
                Object value2 = toGuestValue.execute(context, hostValue);
                try {
                    hashes.writeHashEntry(receiver, key, value2);
                }
                catch (UnknownKeyException | UnsupportedMessageException e) {
                    unsupported.enter();
                    throw PolyglotValueDispatch.putHashEntryUnsupported(context, receiver, key, value2);
                }
                catch (UnsupportedTypeException e) {
                    invalidValue.enter();
                    throw PolyglotValueDispatch.invalidHashValue(context, receiver, key, value2);
                }
                return null;
            }
        }

        static abstract class GetHashValueOrDefaultNode
        extends InteropNode {
            protected GetHashValueOrDefaultNode(InteropValue interop) {
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

            @Specialization(limit="CACHE_LIMIT")
            static Object doCached(PolyglotLanguageContext context, Object receiver, Object[] args, @CachedLibrary(value="receiver") InteropLibrary hashes, @Cached PolyglotLanguageContext.ToGuestValueNode toGuestKey, @Cached PolyglotLanguageContext.ToGuestValueNode toGuestDefaultValue, @Cached(value="createToHost()") PolyglotLanguageContext.ToHostValueNode toHost, @Cached BranchProfile unsupported, @Cached BranchProfile invalidKey) {
                Object hostKey = args[2];
                Object hostDefaultValue = args[3];
                Object key = toGuestKey.execute(context, hostKey);
                Object defaultValue = toGuestDefaultValue.execute(context, hostDefaultValue);
                try {
                    return toHost.execute(context, hashes.readHashValueOrDefault(receiver, key, hostDefaultValue));
                }
                catch (UnsupportedMessageException e) {
                    unsupported.enter();
                    throw PolyglotValueDispatch.getHashValueUnsupported(context, receiver, key);
                }
            }
        }

        static abstract class GetHashValueNode
        extends InteropNode {
            protected GetHashValueNode(InteropValue interop) {
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

            @Specialization(limit="CACHE_LIMIT")
            static Object doCached(PolyglotLanguageContext context, Object receiver, Object[] args, @CachedLibrary(value="receiver") InteropLibrary hashes, @Cached PolyglotLanguageContext.ToGuestValueNode toGuestKey, @Cached(value="createToHost()") PolyglotLanguageContext.ToHostValueNode toHost, @Cached BranchProfile unsupported, @Cached BranchProfile invalidKey) {
                Object hostKey = args[2];
                Object key = toGuestKey.execute(context, hostKey);
                try {
                    return toHost.execute(context, hashes.readHashValue(receiver, key));
                }
                catch (UnsupportedMessageException e) {
                    unsupported.enter();
                    throw PolyglotValueDispatch.getHashValueUnsupported(context, receiver, key);
                }
                catch (UnknownKeyException e) {
                    invalidKey.enter();
                    if (hashes.isHashEntryExisting(receiver, key)) {
                        throw PolyglotValueDispatch.getHashValueUnsupported(context, receiver, key);
                    }
                    return null;
                }
            }
        }

        static abstract class HasHashEntryNode
        extends InteropNode {
            protected HasHashEntryNode(InteropValue interop) {
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

            @Specialization(limit="CACHE_LIMIT")
            static Object doCached(PolyglotLanguageContext context, Object receiver, Object[] args, @CachedLibrary(value="receiver") InteropLibrary hashes, @Cached PolyglotLanguageContext.ToGuestValueNode toGuestKey) {
                Object hostKey = args[2];
                Object key = toGuestKey.execute(context, hostKey);
                return hashes.isHashEntryExisting(receiver, key);
            }
        }

        static abstract class GetHashSizeNode
        extends InteropNode {
            protected GetHashSizeNode(InteropValue interop) {
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

            @Specialization(limit="CACHE_LIMIT")
            static Object doCached(PolyglotLanguageContext context, Object receiver, Object[] args, @CachedLibrary(value="receiver") InteropLibrary hashes, @Cached BranchProfile unsupported) {
                try {
                    return hashes.getHashSize(receiver);
                }
                catch (UnsupportedMessageException e) {
                    unsupported.enter();
                    throw PolyglotValueDispatch.getHashSizeUnsupported(context, receiver);
                }
            }
        }

        static abstract class HasHashEntriesNode
        extends InteropNode {
            protected HasHashEntriesNode(InteropValue interop) {
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

            @Specialization(limit="CACHE_LIMIT")
            static Object doCached(PolyglotLanguageContext context, Object receiver, Object[] args, @CachedLibrary(value="receiver") InteropLibrary hashes) {
                return hashes.hasHashEntries(receiver);
            }
        }

        static abstract class GetIteratorNextElementNode
        extends InteropNode {
            protected GetIteratorNextElementNode(InteropValue interop) {
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

            @Specialization(limit="CACHE_LIMIT")
            static Object doCached(PolyglotLanguageContext context, Object receiver, Object[] args, @CachedLibrary(value="receiver") InteropLibrary iterators, @Cached(value="createToHost()") PolyglotLanguageContext.ToHostValueNode toHost, @Cached BranchProfile unsupported, @Cached BranchProfile stop) {
                try {
                    return toHost.execute(context, iterators.getIteratorNextElement(receiver));
                }
                catch (UnsupportedMessageException e) {
                    unsupported.enter();
                    throw PolyglotValueDispatch.nonReadableIteratorElement();
                }
                catch (StopIterationException e) {
                    stop.enter();
                    throw PolyglotValueDispatch.stopIteration(context, receiver);
                }
            }
        }

        static abstract class HasIteratorNextElementNode
        extends InteropNode {
            protected HasIteratorNextElementNode(InteropValue interop) {
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

            @Specialization(limit="CACHE_LIMIT")
            static Object doCached(PolyglotLanguageContext context, Object receiver, Object[] args, @CachedLibrary(value="receiver") InteropLibrary iterators, @Cached BranchProfile unsupported) {
                try {
                    return iterators.hasIteratorNextElement(receiver);
                }
                catch (UnsupportedMessageException e) {
                    unsupported.enter();
                    return PolyglotValueDispatch.hasIteratorNextElementUnsupported(context, receiver);
                }
            }
        }

        static abstract class IsIteratorNode
        extends InteropNode {
            protected IsIteratorNode(InteropValue interop) {
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

            @Specialization(limit="CACHE_LIMIT")
            static Object doCached(PolyglotLanguageContext context, Object receiver, Object[] args, @CachedLibrary(value="receiver") InteropLibrary iterators) {
                return iterators.isIterator(receiver);
            }
        }

        static abstract class GetIteratorNode
        extends InteropNode {
            protected GetIteratorNode(InteropValue interop) {
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

            @Specialization(limit="CACHE_LIMIT")
            static Object doCached(PolyglotLanguageContext context, Object receiver, Object[] args, @CachedLibrary(value="receiver") InteropLibrary iterators, @Cached(value="createToHost()") PolyglotLanguageContext.ToHostValueNode toHost, @Cached BranchProfile unsupported) {
                try {
                    return toHost.execute(context, iterators.getIterator(receiver));
                }
                catch (UnsupportedMessageException e) {
                    unsupported.enter();
                    return PolyglotValueDispatch.getIteratorUnsupported(context, receiver);
                }
            }
        }

        static abstract class HasIteratorNode
        extends InteropNode {
            protected HasIteratorNode(InteropValue interop) {
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

            @Specialization(limit="CACHE_LIMIT")
            static Object doCached(PolyglotLanguageContext context, Object receiver, Object[] args, @CachedLibrary(value="receiver") InteropLibrary iterators) {
                return iterators.hasIterator(receiver);
            }
        }

        static abstract class GetMetaParentsNode
        extends InteropNode {
            protected GetMetaParentsNode(InteropValue interop) {
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

            @Specialization(limit="CACHE_LIMIT")
            static Object doCached(PolyglotLanguageContext context, Object receiver, Object[] args, @CachedLibrary(value="receiver") InteropLibrary objects, @Cached(value="createToHost()") PolyglotLanguageContext.ToHostValueNode toHost, @Cached BranchProfile unsupported) {
                try {
                    return toHost.execute(context, objects.getMetaParents(receiver));
                }
                catch (UnsupportedMessageException e) {
                    unsupported.enter();
                    throw PolyglotValueDispatch.unsupported(context, receiver, "getMetaParents()", "hasMetaParents()");
                }
            }
        }

        static abstract class HasMetaParentsNode
        extends InteropNode {
            protected HasMetaParentsNode(InteropValue interop) {
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

            @Specialization(limit="CACHE_LIMIT")
            static boolean doCached(PolyglotLanguageContext context, Object receiver, Object[] args, @CachedLibrary(value="receiver") InteropLibrary objects, @Cached BranchProfile unsupported) {
                return objects.hasMetaParents(receiver);
            }
        }

        static abstract class IsMetaInstanceNode
        extends InteropNode {
            protected IsMetaInstanceNode(InteropValue interop) {
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

            @Specialization(limit="CACHE_LIMIT")
            static boolean doCached(PolyglotLanguageContext context, Object receiver, Object[] args, @CachedLibrary(value="receiver") InteropLibrary objects, @Cached PolyglotLanguageContext.ToGuestValueNode toGuest, @Cached BranchProfile unsupported) {
                try {
                    return objects.isMetaInstance(receiver, toGuest.execute(context, args[2]));
                }
                catch (UnsupportedMessageException e) {
                    unsupported.enter();
                    throw PolyglotValueDispatch.unsupported(context, receiver, "isMetaInstance()", "isMetaObject()");
                }
            }
        }

        static abstract class GetMetaSimpleNameNode
        extends InteropNode {
            protected GetMetaSimpleNameNode(InteropValue interop) {
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

            @Specialization(limit="CACHE_LIMIT")
            static String doCached(PolyglotLanguageContext context, Object receiver, Object[] args, @CachedLibrary(value="receiver") InteropLibrary objects, @CachedLibrary(limit="1") InteropLibrary toString, @Cached BranchProfile unsupported) {
                try {
                    return toString.asString(objects.getMetaSimpleName(receiver));
                }
                catch (UnsupportedMessageException e) {
                    unsupported.enter();
                    throw PolyglotValueDispatch.unsupported(context, receiver, "getMetaSimpleName()", "isMetaObject()");
                }
            }
        }

        static abstract class GetMetaQualifiedNameNode
        extends InteropNode {
            protected GetMetaQualifiedNameNode(InteropValue interop) {
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

            @Specialization(limit="CACHE_LIMIT")
            static String doCached(PolyglotLanguageContext context, Object receiver, Object[] args, @CachedLibrary(value="receiver") InteropLibrary objects, @CachedLibrary(limit="1") InteropLibrary toString, @Cached BranchProfile unsupported) {
                try {
                    return toString.asString(objects.getMetaQualifiedName(receiver));
                }
                catch (UnsupportedMessageException e) {
                    unsupported.enter();
                    throw PolyglotValueDispatch.unsupported(context, receiver, "getMetaQualifiedName()", "isMetaObject()");
                }
            }
        }

        static abstract class IsMetaObjectNode
        extends InteropNode {
            protected IsMetaObjectNode(InteropValue interop) {
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

            @Specialization(limit="CACHE_LIMIT")
            static boolean doCached(PolyglotLanguageContext context, Object receiver, Object[] args, @CachedLibrary(value="receiver") InteropLibrary objects) {
                return objects.isMetaObject(receiver);
            }
        }

        static abstract class ThrowExceptionNode
        extends InteropNode {
            protected ThrowExceptionNode(InteropValue interop) {
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

            @Specialization(limit="CACHE_LIMIT")
            static Object doCached(PolyglotLanguageContext context, Object receiver, Object[] args, @CachedLibrary(value="receiver") InteropLibrary objects, @Cached BranchProfile unsupported) {
                try {
                    throw objects.throwException(receiver);
                }
                catch (UnsupportedMessageException e) {
                    unsupported.enter();
                    throw PolyglotValueDispatch.unsupported(context, receiver, "throwException()", "isException()");
                }
            }
        }

        static abstract class IsExceptionNode
        extends InteropNode {
            protected IsExceptionNode(InteropValue interop) {
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

            @Specialization(limit="CACHE_LIMIT")
            static Object doCached(PolyglotLanguageContext context, Object receiver, Object[] args, @CachedLibrary(value="receiver") InteropLibrary objects) {
                return objects.isException(receiver);
            }
        }

        private static class InvokeNoArgsNode
        extends AbstractInvokeNode {
            protected InvokeNoArgsNode(InteropValue interop) {
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
                return this.executeShared(context, receiver, key, ExecuteVoidNoArgsNode.NO_ARGS);
            }
        }

        private static class InvokeNode
        extends AbstractInvokeNode {
            @Node.Child
            private PolyglotLanguageContext.ToGuestValuesNode toGuestValues = PolyglotLanguageContext.ToGuestValuesNode.create();

            protected InvokeNode(InteropValue interop) {
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

        private static abstract class AbstractInvokeNode
        extends InteropNode {
            @Node.Child
            private InteropLibrary objects = InteropLibrary.getFactory().createDispatched(5);
            private final PolyglotLanguageContext.ToHostValueNode toHostValue;
            private final BranchProfile invalidArgument = BranchProfile.create();
            private final BranchProfile arity = BranchProfile.create();
            private final BranchProfile unsupported = BranchProfile.create();
            private final BranchProfile unknownIdentifier = BranchProfile.create();

            protected AbstractInvokeNode(InteropValue interop) {
                super(interop);
                this.toHostValue = PolyglotLanguageContext.ToHostValueNode.create(interop.impl);
            }

            protected final Object executeShared(PolyglotLanguageContext context, Object receiver, String key, Object[] guestArguments) {
                try {
                    return this.toHostValue.execute(context, this.objects.invokeMember(receiver, key, guestArguments));
                }
                catch (UnsupportedMessageException e) {
                    this.unsupported.enter();
                    throw PolyglotValueDispatch.invokeUnsupported(context, receiver, key);
                }
                catch (UnknownIdentifierException e) {
                    this.unknownIdentifier.enter();
                    throw PolyglotValueDispatch.nonReadableMemberKey(context, receiver, key);
                }
                catch (UnsupportedTypeException e) {
                    this.invalidArgument.enter();
                    throw PolyglotValueDispatch.invalidInvokeArgumentType(context, receiver, key, e);
                }
                catch (ArityException e) {
                    this.arity.enter();
                    throw PolyglotValueDispatch.invalidInvokeArity(context, receiver, key, guestArguments, e.getExpectedMinArity(), e.getExpectedMaxArity(), e.getActualArity());
                }
            }
        }

        static abstract class NewInstanceNode
        extends InteropNode {
            @Node.Child
            private PolyglotLanguageContext.ToGuestValuesNode toGuestValues = PolyglotLanguageContext.ToGuestValuesNode.create();
            private final PolyglotLanguageContext.ToHostValueNode toHostValue;

            protected NewInstanceNode(InteropValue interop) {
                super(interop);
                this.toHostValue = PolyglotLanguageContext.ToHostValueNode.create(interop.impl);
            }

            @Override
            protected Class<?>[] getArgumentTypes() {
                return new Class[]{PolyglotLanguageContext.class, this.polyglot.receiverType, Object[].class};
            }

            @Specialization(limit="CACHE_LIMIT")
            static Object doCached(PolyglotLanguageContext context, Object receiver, Object[] args, @CachedLibrary(value="receiver") InteropLibrary instantiables, @Cached PolyglotLanguageContext.ToGuestValuesNode toGuestValues, @Cached(value="createToHost()") PolyglotLanguageContext.ToHostValueNode toHostValue, @Cached BranchProfile arity, @Cached BranchProfile invalidArgument, @Cached BranchProfile unsupported) {
                Object[] instantiateArguments = toGuestValues.apply(context, (Object[])args[2]);
                try {
                    return toHostValue.execute(context, instantiables.instantiate(receiver, instantiateArguments));
                }
                catch (UnsupportedTypeException e) {
                    invalidArgument.enter();
                    throw PolyglotValueDispatch.invalidInstantiateArgumentType(context, receiver, instantiateArguments);
                }
                catch (ArityException e) {
                    arity.enter();
                    throw PolyglotValueDispatch.invalidInstantiateArity(context, receiver, instantiateArguments, e.getExpectedMinArity(), e.getExpectedMaxArity(), e.getActualArity());
                }
                catch (UnsupportedMessageException e) {
                    unsupported.enter();
                    return PolyglotValueDispatch.newInstanceUnsupported(context, receiver);
                }
            }

            @Override
            protected String getOperationName() {
                return "newInstance";
            }
        }

        private static class ExecuteNoArgsNode
        extends AbstractExecuteNode {
            private final PolyglotLanguageContext.ToHostValueNode toHostValue;

            protected ExecuteNoArgsNode(InteropValue interop) {
                super(interop);
                this.toHostValue = PolyglotLanguageContext.ToHostValueNode.create(interop.impl);
            }

            @Override
            protected Class<?>[] getArgumentTypes() {
                return new Class[]{PolyglotLanguageContext.class, this.polyglot.receiverType};
            }

            @Override
            protected Object executeImpl(PolyglotLanguageContext context, Object receiver, Object[] args) {
                return this.toHostValue.execute(context, this.executeShared(context, receiver, ExecuteVoidNoArgsNode.NO_ARGS));
            }

            @Override
            protected String getOperationName() {
                return "execute";
            }
        }

        private static class ExecuteNode
        extends AbstractExecuteNode {
            private final PolyglotLanguageContext.ToHostValueNode toHostValue;

            protected ExecuteNode(InteropValue interop) {
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

        private static class ExecuteVoidNoArgsNode
        extends AbstractExecuteNode {
            private static final Object[] NO_ARGS = new Object[0];

            protected ExecuteVoidNoArgsNode(InteropValue interop) {
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

        private static class ExecuteVoidNode
        extends AbstractExecuteNode {
            protected ExecuteVoidNode(InteropValue interop) {
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

        private static abstract class AbstractExecuteNode
        extends InteropNode {
            @Node.Child
            private InteropLibrary executables = InteropLibrary.getFactory().createDispatched(5);
            @Node.Child
            private PolyglotLanguageContext.ToGuestValuesNode toGuestValues = PolyglotLanguageContext.ToGuestValuesNode.create();
            private final BranchProfile invalidArgument = BranchProfile.create();
            private final BranchProfile arity = BranchProfile.create();
            private final BranchProfile unsupported = BranchProfile.create();

            protected AbstractExecuteNode(InteropValue interop) {
                super(interop);
            }

            protected final Object executeShared(PolyglotLanguageContext context, Object receiver, Object[] args) {
                Object[] guestArguments = this.toGuestValues.apply(context, args);
                try {
                    return this.executables.execute(receiver, guestArguments);
                }
                catch (UnsupportedTypeException e) {
                    this.invalidArgument.enter();
                    throw PolyglotValueDispatch.invalidExecuteArgumentType(context, receiver, e);
                }
                catch (ArityException e) {
                    this.arity.enter();
                    throw PolyglotValueDispatch.invalidExecuteArity(context, receiver, guestArguments, e.getExpectedMinArity(), e.getExpectedMaxArity(), e.getActualArity());
                }
                catch (UnsupportedMessageException e) {
                    this.unsupported.enter();
                    throw PolyglotValueDispatch.executeUnsupported(context, receiver);
                }
            }
        }

        static abstract class CanInstantiateNode
        extends InteropNode {
            protected CanInstantiateNode(InteropValue interop) {
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

            @Specialization(limit="CACHE_LIMIT")
            static Object doCached(PolyglotLanguageContext context, Object receiver, Object[] args, @CachedLibrary(value="receiver") InteropLibrary instantiables) {
                return instantiables.isInstantiable(receiver);
            }
        }

        static abstract class CanExecuteNode
        extends InteropNode {
            protected CanExecuteNode(InteropValue interop) {
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

            @Specialization(limit="CACHE_LIMIT")
            static Object doCached(PolyglotLanguageContext context, Object receiver, Object[] args, @CachedLibrary(value="receiver") InteropLibrary executables) {
                return executables.isExecutable(receiver);
            }
        }

        static abstract class CanInvokeNode
        extends AbstractMemberInfoNode {
            protected CanInvokeNode(InteropValue interop) {
                super(interop);
            }

            @Override
            protected String getOperationName() {
                return "canInvoke";
            }

            @Specialization(limit="CACHE_LIMIT")
            static Object doCached(PolyglotLanguageContext context, Object receiver, Object[] args, @CachedLibrary(value="receiver") InteropLibrary objects) {
                String key = (String)args[2];
                return objects.isMemberInvocable(receiver, key);
            }
        }

        static abstract class HasMemberNode
        extends AbstractMemberInfoNode {
            protected HasMemberNode(InteropValue interop) {
                super(interop);
            }

            @Override
            protected String getOperationName() {
                return "hasMember";
            }

            @Specialization(limit="CACHE_LIMIT")
            static Object doCached(PolyglotLanguageContext context, Object receiver, Object[] args, @CachedLibrary(value="receiver") InteropLibrary objects) {
                String key = (String)args[2];
                return objects.isMemberExisting(receiver, key);
            }
        }

        private static abstract class AbstractMemberInfoNode
        extends InteropNode {
            protected AbstractMemberInfoNode(InteropValue interop) {
                super(interop);
            }

            @Override
            protected final Class<?>[] getArgumentTypes() {
                return new Class[]{PolyglotLanguageContext.class, this.polyglot.receiverType, String.class};
            }
        }

        static abstract class HasMembersNode
        extends InteropNode {
            protected HasMembersNode(InteropValue interop) {
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

            @Specialization(limit="CACHE_LIMIT")
            static Object doCached(PolyglotLanguageContext context, Object receiver, Object[] args, @CachedLibrary(value="receiver") InteropLibrary objects) {
                return objects.hasMembers(receiver);
            }
        }

        static abstract class IsNullNode
        extends InteropNode {
            protected IsNullNode(InteropValue interop) {
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

            @Specialization(limit="CACHE_LIMIT")
            static Object doCached(PolyglotLanguageContext context, Object receiver, Object[] args, @CachedLibrary(value="receiver") InteropLibrary values) {
                return values.isNull(receiver);
            }
        }

        static abstract class RemoveMemberNode
        extends InteropNode {
            protected RemoveMemberNode(InteropValue interop) {
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

            @Specialization(limit="CACHE_LIMIT")
            static Object doCached(PolyglotLanguageContext context, Object receiver, Object[] args, @CachedLibrary(value="receiver") InteropLibrary objects, @Cached BranchProfile unsupported, @Cached BranchProfile unknown) {
                Boolean value2;
                String key = (String)args[2];
                try {
                    assert (key != null) : "should be handled already";
                    objects.removeMember(receiver, key);
                    value2 = Boolean.TRUE;
                }
                catch (UnsupportedMessageException e) {
                    unsupported.enter();
                    if (!objects.hasMembers(receiver)) {
                        throw PolyglotValueDispatch.removeMemberUnsupported(context, receiver);
                    }
                    if (objects.isMemberExisting(receiver, key)) {
                        throw PolyglotValueDispatch.nonRemovableMemberKey(context, receiver, key);
                    }
                    value2 = Boolean.FALSE;
                }
                catch (UnknownIdentifierException e) {
                    unknown.enter();
                    if (objects.isMemberExisting(receiver, key)) {
                        throw PolyglotValueDispatch.nonRemovableMemberKey(context, receiver, key);
                    }
                    value2 = Boolean.FALSE;
                }
                return value2;
            }
        }

        static abstract class PutMemberNode
        extends InteropNode {
            protected PutMemberNode(InteropValue interop) {
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
            static Object doCached(PolyglotLanguageContext context, Object receiver, Object[] args, @CachedLibrary(limit="CACHE_LIMIT") InteropLibrary objects, @Cached PolyglotLanguageContext.ToGuestValueNode toGuestValue, @Cached BranchProfile unsupported, @Cached BranchProfile invalidValue, @Cached BranchProfile unknown) {
                String key = (String)args[2];
                Object originalValue = args[3];
                Object value2 = toGuestValue.execute(context, originalValue);
                assert (key != null);
                try {
                    objects.writeMember(receiver, key, value2);
                }
                catch (UnsupportedMessageException e) {
                    unsupported.enter();
                    throw PolyglotValueDispatch.putMemberUnsupported(context, receiver);
                }
                catch (UnknownIdentifierException e) {
                    unknown.enter();
                    throw PolyglotValueDispatch.nonWritableMemberKey(context, receiver, key);
                }
                catch (UnsupportedTypeException e) {
                    invalidValue.enter();
                    throw PolyglotValueDispatch.invalidMemberValue(context, receiver, key, value2);
                }
                return null;
            }
        }

        static abstract class GetMemberNode
        extends InteropNode {
            protected GetMemberNode(InteropValue interop) {
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

            @Specialization(limit="CACHE_LIMIT")
            static Object doCached(PolyglotLanguageContext context, Object receiver, Object[] args, @CachedLibrary(value="receiver") InteropLibrary objects, @Cached(value="createToHost()") PolyglotLanguageContext.ToHostValueNode toHost, @Cached BranchProfile unsupported, @Cached BranchProfile unknown) {
                Value value2;
                String key = (String)args[2];
                try {
                    assert (key != null) : "should be handled already";
                    value2 = toHost.execute(context, objects.readMember(receiver, key));
                }
                catch (UnsupportedMessageException e) {
                    unsupported.enter();
                    if (objects.hasMembers(receiver)) {
                        value2 = null;
                    }
                    return PolyglotValueDispatch.getMemberUnsupported(context, receiver, key);
                }
                catch (UnknownIdentifierException e) {
                    unknown.enter();
                    value2 = null;
                }
                return value2;
            }
        }

        static abstract class WriteBufferDoubleNode
        extends InteropNode {
            protected WriteBufferDoubleNode(InteropValue interop) {
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

            @Specialization(limit="CACHE_LIMIT")
            static Object doCached(PolyglotLanguageContext context, Object receiver, Object[] args, @CachedLibrary(value="receiver") InteropLibrary buffers, @Cached BranchProfile unsupported, @Cached BranchProfile invalidIndex, @Cached BranchProfile invalidValue) {
                ByteOrder order = (ByteOrder)args[2];
                long byteOffset = (Long)args[3];
                double value2 = (Double)args[4];
                try {
                    buffers.writeBufferDouble(receiver, order, byteOffset, value2);
                }
                catch (UnsupportedMessageException e) {
                    unsupported.enter();
                    if (buffers.hasBufferElements(receiver)) {
                        throw PolyglotValueDispatch.unsupported(context, receiver, "writeBufferDouble()", "isBufferWritable()");
                    }
                    throw PolyglotValueDispatch.writeBufferDoubleUnsupported(context, receiver);
                }
                catch (InvalidBufferOffsetException e) {
                    invalidIndex.enter();
                    throw PolyglotValueDispatch.invalidBufferIndex(context, receiver, e.getByteOffset(), e.getLength());
                }
                return null;
            }
        }

        static abstract class ReadBufferDoubleNode
        extends InteropNode {
            protected ReadBufferDoubleNode(InteropValue interop) {
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

            @Specialization(limit="CACHE_LIMIT")
            static Object doCached(PolyglotLanguageContext context, Object receiver, Object[] args, @CachedLibrary(value="receiver") InteropLibrary buffers, @Cached(value="createToHost()") PolyglotLanguageContext.ToHostValueNode toHost, @Cached BranchProfile unsupported, @Cached BranchProfile unknown) {
                ByteOrder order = (ByteOrder)args[2];
                long byteOffset = (Long)args[3];
                try {
                    return buffers.readBufferDouble(receiver, order, byteOffset);
                }
                catch (UnsupportedMessageException e) {
                    unsupported.enter();
                    throw PolyglotValueDispatch.readBufferDoubleUnsupported(context, receiver);
                }
                catch (InvalidBufferOffsetException e) {
                    unknown.enter();
                    throw PolyglotValueDispatch.invalidBufferIndex(context, receiver, e.getByteOffset(), e.getLength());
                }
            }
        }

        static abstract class WriteBufferFloatNode
        extends InteropNode {
            protected WriteBufferFloatNode(InteropValue interop) {
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

            @Specialization(limit="CACHE_LIMIT")
            static Object doCached(PolyglotLanguageContext context, Object receiver, Object[] args, @CachedLibrary(value="receiver") InteropLibrary buffers, @Cached BranchProfile unsupported, @Cached BranchProfile invalidIndex, @Cached BranchProfile invalidValue) {
                ByteOrder order = (ByteOrder)args[2];
                long byteOffset = (Long)args[3];
                float value2 = ((Float)args[4]).floatValue();
                try {
                    buffers.writeBufferFloat(receiver, order, byteOffset, value2);
                }
                catch (UnsupportedMessageException e) {
                    unsupported.enter();
                    if (buffers.hasBufferElements(receiver)) {
                        throw PolyglotValueDispatch.unsupported(context, receiver, "writeBufferFloat()", "isBufferWritable()");
                    }
                    throw PolyglotValueDispatch.writeBufferFloatUnsupported(context, receiver);
                }
                catch (InvalidBufferOffsetException e) {
                    invalidIndex.enter();
                    throw PolyglotValueDispatch.invalidBufferIndex(context, receiver, e.getByteOffset(), e.getLength());
                }
                return null;
            }
        }

        static abstract class ReadBufferFloatNode
        extends InteropNode {
            protected ReadBufferFloatNode(InteropValue interop) {
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

            @Specialization(limit="CACHE_LIMIT")
            static Object doCached(PolyglotLanguageContext context, Object receiver, Object[] args, @CachedLibrary(value="receiver") InteropLibrary buffers, @Cached(value="createToHost()") PolyglotLanguageContext.ToHostValueNode toHost, @Cached BranchProfile unsupported, @Cached BranchProfile unknown) {
                ByteOrder order = (ByteOrder)args[2];
                long byteOffset = (Long)args[3];
                try {
                    return Float.valueOf(buffers.readBufferFloat(receiver, order, byteOffset));
                }
                catch (UnsupportedMessageException e) {
                    unsupported.enter();
                    throw PolyglotValueDispatch.readBufferFloatUnsupported(context, receiver);
                }
                catch (InvalidBufferOffsetException e) {
                    unknown.enter();
                    throw PolyglotValueDispatch.invalidBufferIndex(context, receiver, e.getByteOffset(), e.getLength());
                }
            }
        }

        static abstract class WriteBufferLongNode
        extends InteropNode {
            protected WriteBufferLongNode(InteropValue interop) {
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

            @Specialization(limit="CACHE_LIMIT")
            static Object doCached(PolyglotLanguageContext context, Object receiver, Object[] args, @CachedLibrary(value="receiver") InteropLibrary buffers, @Cached BranchProfile unsupported, @Cached BranchProfile invalidIndex, @Cached BranchProfile invalidValue) {
                ByteOrder order = (ByteOrder)args[2];
                long byteOffset = (Long)args[3];
                long value2 = (Long)args[4];
                try {
                    buffers.writeBufferLong(receiver, order, byteOffset, value2);
                }
                catch (UnsupportedMessageException e) {
                    unsupported.enter();
                    if (buffers.hasBufferElements(receiver)) {
                        throw PolyglotValueDispatch.unsupported(context, receiver, "writeBufferLong()", "isBufferWritable()");
                    }
                    throw PolyglotValueDispatch.writeBufferLongUnsupported(context, receiver);
                }
                catch (InvalidBufferOffsetException e) {
                    invalidIndex.enter();
                    throw PolyglotValueDispatch.invalidBufferIndex(context, receiver, e.getByteOffset(), e.getLength());
                }
                return null;
            }
        }

        static abstract class ReadBufferLongNode
        extends InteropNode {
            protected ReadBufferLongNode(InteropValue interop) {
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

            @Specialization(limit="CACHE_LIMIT")
            static Object doCached(PolyglotLanguageContext context, Object receiver, Object[] args, @CachedLibrary(value="receiver") InteropLibrary buffers, @Cached(value="createToHost()") PolyglotLanguageContext.ToHostValueNode toHost, @Cached BranchProfile unsupported, @Cached BranchProfile unknown) {
                ByteOrder order = (ByteOrder)args[2];
                long byteOffset = (Long)args[3];
                try {
                    return buffers.readBufferLong(receiver, order, byteOffset);
                }
                catch (UnsupportedMessageException e) {
                    unsupported.enter();
                    throw PolyglotValueDispatch.readBufferLongUnsupported(context, receiver);
                }
                catch (InvalidBufferOffsetException e) {
                    unknown.enter();
                    throw PolyglotValueDispatch.invalidBufferIndex(context, receiver, e.getByteOffset(), e.getLength());
                }
            }
        }

        static abstract class WriteBufferIntNode
        extends InteropNode {
            protected WriteBufferIntNode(InteropValue interop) {
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

            @Specialization(limit="CACHE_LIMIT")
            static Object doCached(PolyglotLanguageContext context, Object receiver, Object[] args, @CachedLibrary(value="receiver") InteropLibrary buffers, @Cached BranchProfile unsupported, @Cached BranchProfile invalidIndex, @Cached BranchProfile invalidValue) {
                ByteOrder order = (ByteOrder)args[2];
                long byteOffset = (Long)args[3];
                int value2 = (Integer)args[4];
                try {
                    buffers.writeBufferInt(receiver, order, byteOffset, value2);
                }
                catch (UnsupportedMessageException e) {
                    unsupported.enter();
                    if (buffers.hasBufferElements(receiver)) {
                        throw PolyglotValueDispatch.unsupported(context, receiver, "writeBufferInt()", "isBufferWritable()");
                    }
                    throw PolyglotValueDispatch.writeBufferIntUnsupported(context, receiver);
                }
                catch (InvalidBufferOffsetException e) {
                    invalidIndex.enter();
                    throw PolyglotValueDispatch.invalidBufferIndex(context, receiver, e.getByteOffset(), e.getLength());
                }
                return null;
            }
        }

        static abstract class ReadBufferIntNode
        extends InteropNode {
            protected ReadBufferIntNode(InteropValue interop) {
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

            @Specialization(limit="CACHE_LIMIT")
            static Object doCached(PolyglotLanguageContext context, Object receiver, Object[] args, @CachedLibrary(value="receiver") InteropLibrary buffers, @Cached(value="createToHost()") PolyglotLanguageContext.ToHostValueNode toHost, @Cached BranchProfile unsupported, @Cached BranchProfile unknown) {
                ByteOrder order = (ByteOrder)args[2];
                long byteOffset = (Long)args[3];
                try {
                    return buffers.readBufferInt(receiver, order, byteOffset);
                }
                catch (UnsupportedMessageException e) {
                    unsupported.enter();
                    throw PolyglotValueDispatch.readBufferIntUnsupported(context, receiver);
                }
                catch (InvalidBufferOffsetException e) {
                    unknown.enter();
                    throw PolyglotValueDispatch.invalidBufferIndex(context, receiver, e.getByteOffset(), e.getLength());
                }
            }
        }

        static abstract class WriteBufferShortNode
        extends InteropNode {
            protected WriteBufferShortNode(InteropValue interop) {
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

            @Specialization(limit="CACHE_LIMIT")
            static Object doCached(PolyglotLanguageContext context, Object receiver, Object[] args, @CachedLibrary(value="receiver") InteropLibrary buffers, @Cached BranchProfile unsupported, @Cached BranchProfile invalidIndex, @Cached BranchProfile invalidValue) {
                ByteOrder order = (ByteOrder)args[2];
                long byteOffset = (Long)args[3];
                short value2 = (Short)args[4];
                try {
                    buffers.writeBufferShort(receiver, order, byteOffset, value2);
                }
                catch (UnsupportedMessageException e) {
                    unsupported.enter();
                    if (buffers.hasBufferElements(receiver)) {
                        throw PolyglotValueDispatch.unsupported(context, receiver, "writeBufferShort()", "isBufferWritable()");
                    }
                    throw PolyglotValueDispatch.writeBufferShortUnsupported(context, receiver);
                }
                catch (InvalidBufferOffsetException e) {
                    invalidIndex.enter();
                    throw PolyglotValueDispatch.invalidBufferIndex(context, receiver, e.getByteOffset(), e.getLength());
                }
                return null;
            }
        }

        static abstract class ReadBufferShortNode
        extends InteropNode {
            protected ReadBufferShortNode(InteropValue interop) {
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

            @Specialization(limit="CACHE_LIMIT")
            static Object doCached(PolyglotLanguageContext context, Object receiver, Object[] args, @CachedLibrary(value="receiver") InteropLibrary buffers, @Cached(value="createToHost()") PolyglotLanguageContext.ToHostValueNode toHost, @Cached BranchProfile unsupported, @Cached BranchProfile unknown) {
                ByteOrder order = (ByteOrder)args[2];
                long byteOffset = (Long)args[3];
                try {
                    return buffers.readBufferShort(receiver, order, byteOffset);
                }
                catch (UnsupportedMessageException e) {
                    unsupported.enter();
                    throw PolyglotValueDispatch.readBufferShortUnsupported(context, receiver);
                }
                catch (InvalidBufferOffsetException e) {
                    unknown.enter();
                    throw PolyglotValueDispatch.invalidBufferIndex(context, receiver, e.getByteOffset(), e.getLength());
                }
            }
        }

        static abstract class WriteBufferByteNode
        extends InteropNode {
            protected WriteBufferByteNode(InteropValue interop) {
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

            @Specialization(limit="CACHE_LIMIT")
            static Object doCached(PolyglotLanguageContext context, Object receiver, Object[] args, @CachedLibrary(value="receiver") InteropLibrary buffers, @Cached BranchProfile unsupported, @Cached BranchProfile invalidIndex, @Cached BranchProfile invalidValue) {
                long byteOffset = (Long)args[2];
                byte value2 = (Byte)args[3];
                try {
                    buffers.writeBufferByte(receiver, byteOffset, value2);
                }
                catch (UnsupportedMessageException e) {
                    unsupported.enter();
                    if (buffers.hasBufferElements(receiver)) {
                        throw PolyglotValueDispatch.unsupported(context, receiver, "writeBufferByte()", "isBufferWritable()");
                    }
                    throw PolyglotValueDispatch.writeBufferByteUnsupported(context, receiver);
                }
                catch (InvalidBufferOffsetException e) {
                    invalidIndex.enter();
                    throw PolyglotValueDispatch.invalidBufferIndex(context, receiver, e.getByteOffset(), e.getLength());
                }
                return null;
            }
        }

        static abstract class ReadBufferByteNode
        extends InteropNode {
            protected ReadBufferByteNode(InteropValue interop) {
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

            @Specialization(limit="CACHE_LIMIT")
            static Object doCached(PolyglotLanguageContext context, Object receiver, Object[] args, @CachedLibrary(value="receiver") InteropLibrary buffers, @Cached(value="createToHost()") PolyglotLanguageContext.ToHostValueNode toHost, @Cached BranchProfile unsupported, @Cached BranchProfile unknown) {
                long byteOffset = (Long)args[2];
                try {
                    return buffers.readBufferByte(receiver, byteOffset);
                }
                catch (UnsupportedMessageException e) {
                    unsupported.enter();
                    throw PolyglotValueDispatch.readBufferByteUnsupported(context, receiver);
                }
                catch (InvalidBufferOffsetException e) {
                    unknown.enter();
                    throw PolyglotValueDispatch.invalidBufferIndex(context, receiver, e.getByteOffset(), e.getLength());
                }
            }
        }

        static abstract class GetBufferSizeNode
        extends InteropNode {
            protected GetBufferSizeNode(InteropValue interop) {
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

            @Specialization(limit="CACHE_LIMIT")
            static Object doCached(PolyglotLanguageContext context, Object receiver, Object[] args, @CachedLibrary(value="receiver") InteropLibrary buffers, @Cached BranchProfile unsupported) {
                try {
                    return buffers.getBufferSize(receiver);
                }
                catch (UnsupportedMessageException e) {
                    unsupported.enter();
                    throw PolyglotValueDispatch.getBufferSizeUnsupported(context, receiver);
                }
            }
        }

        static abstract class IsBufferWritableNode
        extends InteropNode {
            protected IsBufferWritableNode(InteropValue interop) {
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

            @Specialization(limit="CACHE_LIMIT")
            static Object doCached(PolyglotLanguageContext context, Object receiver, Object[] args, @CachedLibrary(value="receiver") InteropLibrary buffers, @Cached BranchProfile unsupported) {
                try {
                    return buffers.isBufferWritable(receiver);
                }
                catch (UnsupportedMessageException e) {
                    unsupported.enter();
                    throw PolyglotValueDispatch.getBufferSizeUnsupported(context, receiver);
                }
            }
        }

        static abstract class HasBufferElementsNode
        extends InteropNode {
            protected HasBufferElementsNode(InteropValue interop) {
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

            @Specialization(limit="CACHE_LIMIT")
            static Object doCached(PolyglotLanguageContext context, Object receiver, Object[] args, @CachedLibrary(value="receiver") InteropLibrary buffers) {
                return buffers.hasBufferElements(receiver);
            }
        }

        static abstract class GetArraySizeNode
        extends InteropNode {
            protected GetArraySizeNode(InteropValue interop) {
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

            @Specialization(limit="CACHE_LIMIT")
            static Object doCached(PolyglotLanguageContext context, Object receiver, Object[] args, @CachedLibrary(value="receiver") InteropLibrary arrays, @Cached BranchProfile unsupported) {
                try {
                    return arrays.getArraySize(receiver);
                }
                catch (UnsupportedMessageException e) {
                    unsupported.enter();
                    return PolyglotValueDispatch.getArraySizeUnsupported(context, receiver);
                }
            }
        }

        static abstract class RemoveArrayElementNode
        extends InteropNode {
            protected RemoveArrayElementNode(InteropValue interop) {
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

            @Specialization(limit="CACHE_LIMIT")
            static Object doCached(PolyglotLanguageContext context, Object receiver, Object[] args, @CachedLibrary(value="receiver") InteropLibrary arrays, @Cached BranchProfile unsupported, @Cached BranchProfile invalidIndex) {
                Boolean value2;
                long index = (Long)args[2];
                try {
                    arrays.removeArrayElement(receiver, index);
                    value2 = Boolean.TRUE;
                }
                catch (UnsupportedMessageException e) {
                    unsupported.enter();
                    throw PolyglotValueDispatch.removeArrayElementUnsupported(context, receiver);
                }
                catch (InvalidArrayIndexException e) {
                    invalidIndex.enter();
                    throw PolyglotValueDispatch.invalidArrayIndex(context, receiver, index);
                }
                return value2;
            }
        }

        static abstract class SetArrayElementNode
        extends InteropNode {
            protected SetArrayElementNode(InteropValue interop) {
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

            @Specialization(limit="CACHE_LIMIT")
            static Object doCached(PolyglotLanguageContext context, Object receiver, Object[] args, @CachedLibrary(value="receiver") InteropLibrary arrays, @Cached PolyglotLanguageContext.ToGuestValueNode toGuestValue, @Cached BranchProfile unsupported, @Cached BranchProfile invalidIndex, @Cached BranchProfile invalidValue) {
                long index = (Long)args[2];
                Object value2 = toGuestValue.execute(context, args[3]);
                try {
                    arrays.writeArrayElement(receiver, index, value2);
                }
                catch (UnsupportedMessageException e) {
                    unsupported.enter();
                    PolyglotValueDispatch.setArrayElementUnsupported(context, receiver);
                }
                catch (UnsupportedTypeException e) {
                    invalidValue.enter();
                    throw PolyglotValueDispatch.invalidArrayValue(context, receiver, index, value2);
                }
                catch (InvalidArrayIndexException e) {
                    invalidIndex.enter();
                    throw PolyglotValueDispatch.invalidArrayIndex(context, receiver, index);
                }
                return null;
            }
        }

        static abstract class GetArrayElementNode
        extends InteropNode {
            protected GetArrayElementNode(InteropValue interop) {
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

            @Specialization(limit="CACHE_LIMIT")
            static Object doCached(PolyglotLanguageContext context, Object receiver, Object[] args, @CachedLibrary(value="receiver") InteropLibrary arrays, @Cached(value="createToHost()") PolyglotLanguageContext.ToHostValueNode toHost, @Cached BranchProfile unsupported, @Cached BranchProfile unknown) {
                long index = (Long)args[2];
                try {
                    return toHost.execute(context, arrays.readArrayElement(receiver, index));
                }
                catch (UnsupportedMessageException e) {
                    unsupported.enter();
                    return PolyglotValueDispatch.getArrayElementUnsupported(context, receiver);
                }
                catch (InvalidArrayIndexException e) {
                    unknown.enter();
                    throw PolyglotValueDispatch.invalidArrayIndex(context, receiver, index);
                }
            }
        }

        static abstract class GetMemberKeysNode
        extends InteropNode {
            protected GetMemberKeysNode(InteropValue interop) {
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

            @Specialization(limit="CACHE_LIMIT")
            static Object doCached(PolyglotLanguageContext context, Object receiver, Object[] args, @CachedLibrary(value="receiver") InteropLibrary objects, @Cached(value="createToHost()") PolyglotLanguageContext.ToHostValueNode toHost, @Cached BranchProfile unsupported) {
                try {
                    return toHost.execute(context, objects.getMembers(receiver));
                }
                catch (UnsupportedMessageException e) {
                    return null;
                }
            }
        }

        static abstract class HasArrayElementsNode
        extends InteropNode {
            protected HasArrayElementsNode(InteropValue interop) {
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

            @Specialization(limit="CACHE_LIMIT")
            static Object doCached(PolyglotLanguageContext context, Object receiver, Object[] args, @CachedLibrary(value="receiver") InteropLibrary arrays) {
                return arrays.hasArrayElements(receiver);
            }
        }

        static abstract class AsNativePointerNode
        extends InteropNode {
            protected AsNativePointerNode(InteropValue interop) {
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

            @Specialization(limit="CACHE_LIMIT")
            static Object doCached(PolyglotLanguageContext context, Object receiver, Object[] args, @CachedLibrary(value="receiver") InteropLibrary natives, @Cached BranchProfile unsupported) {
                try {
                    return natives.asPointer(receiver);
                }
                catch (UnsupportedMessageException e) {
                    unsupported.enter();
                    throw PolyglotValueDispatch.cannotConvert(context, receiver, Long.TYPE, "asNativePointer()", "isNativeObject()", "Value cannot be converted to a native pointer.");
                }
            }
        }

        static abstract class IsNativePointerNode
        extends InteropNode {
            protected IsNativePointerNode(InteropValue interop) {
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

            @Specialization(limit="CACHE_LIMIT")
            static Object doCached(PolyglotLanguageContext context, Object receiver, Object[] args, @CachedLibrary(value="receiver") InteropLibrary natives) {
                return natives.isPointer(receiver);
            }
        }

        private static class AsTypeLiteralNode
        extends InteropNode {
            @Node.Child
            PolyglotToHostNode toHost = PolyglotToHostNodeGen.create();

            protected AsTypeLiteralNode(InteropValue interop) {
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
                TypeLiteral typeLiteral = (TypeLiteral)args[2];
                return this.toHost.execute(context, receiver, typeLiteral.getRawType(), typeLiteral.getType());
            }
        }

        private static class AsClassLiteralNode
        extends InteropNode {
            @Node.Child
            PolyglotToHostNode toHost = PolyglotToHostNodeGen.create();

            protected AsClassLiteralNode(InteropValue interop) {
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
                return this.toHost.execute(context, receiver, (Class)args[2], null);
            }
        }

        static abstract class AsInstantNode
        extends InteropNode {
            protected AsInstantNode(InteropValue interop) {
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

            @Specialization(limit="CACHE_LIMIT")
            static Object doCached(PolyglotLanguageContext context, Object receiver, Object[] args, @CachedLibrary(value="receiver") InteropLibrary objects, @Cached BranchProfile unsupported) {
                try {
                    return objects.asInstant(receiver);
                }
                catch (UnsupportedMessageException e) {
                    unsupported.enter();
                    if (objects.isNull(receiver)) {
                        return null;
                    }
                    throw PolyglotValueDispatch.cannotConvert(context, receiver, null, "asInstant()", "hasInstant()", "Value does not contain instant information.");
                }
            }
        }

        static abstract class AsDurationNode
        extends InteropNode {
            protected AsDurationNode(InteropValue interop) {
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

            @Specialization(limit="CACHE_LIMIT")
            static Object doCached(PolyglotLanguageContext context, Object receiver, Object[] args, @CachedLibrary(value="receiver") InteropLibrary objects, @Cached BranchProfile unsupported) {
                try {
                    return objects.asDuration(receiver);
                }
                catch (UnsupportedMessageException e) {
                    unsupported.enter();
                    if (objects.isNull(receiver)) {
                        return null;
                    }
                    throw PolyglotValueDispatch.cannotConvert(context, receiver, null, "asDuration()", "isDuration()", "Value does not contain duration information.");
                }
            }
        }

        static abstract class IsDurationNode
        extends InteropNode {
            protected IsDurationNode(InteropValue interop) {
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

            @Specialization(limit="CACHE_LIMIT")
            static Object doCached(PolyglotLanguageContext context, Object receiver, Object[] args, @CachedLibrary(value="receiver") InteropLibrary objects) {
                return objects.isDuration(receiver);
            }
        }

        static abstract class AsTimeZoneNode
        extends InteropNode {
            protected AsTimeZoneNode(InteropValue interop) {
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

            @Specialization(limit="CACHE_LIMIT")
            static Object doCached(PolyglotLanguageContext context, Object receiver, Object[] args, @CachedLibrary(value="receiver") InteropLibrary objects, @Cached BranchProfile unsupported) {
                try {
                    return objects.asTimeZone(receiver);
                }
                catch (UnsupportedMessageException e) {
                    unsupported.enter();
                    if (objects.isNull(receiver)) {
                        return null;
                    }
                    throw PolyglotValueDispatch.cannotConvert(context, receiver, null, "asTimeZone()", "isTimeZone()", "Value does not contain time-zone information.");
                }
            }
        }

        static abstract class IsTimeZoneNode
        extends InteropNode {
            protected IsTimeZoneNode(InteropValue interop) {
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

            @Specialization(limit="CACHE_LIMIT")
            static Object doCached(PolyglotLanguageContext context, Object receiver, Object[] args, @CachedLibrary(value="receiver") InteropLibrary objects) {
                return objects.isTimeZone(receiver);
            }
        }

        static abstract class AsTimeNode
        extends InteropNode {
            protected AsTimeNode(InteropValue interop) {
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

            @Specialization(limit="CACHE_LIMIT")
            static Object doCached(PolyglotLanguageContext context, Object receiver, Object[] args, @CachedLibrary(value="receiver") InteropLibrary objects, @Cached BranchProfile unsupported) {
                try {
                    return objects.asTime(receiver);
                }
                catch (UnsupportedMessageException e) {
                    unsupported.enter();
                    if (objects.isNull(receiver)) {
                        return null;
                    }
                    throw PolyglotValueDispatch.cannotConvert(context, receiver, null, "asTime()", "isTime()", "Value does not contain time information.");
                }
            }
        }

        static abstract class IsTimeNode
        extends InteropNode {
            protected IsTimeNode(InteropValue interop) {
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

            @Specialization(limit="CACHE_LIMIT")
            static Object doCached(PolyglotLanguageContext context, Object receiver, Object[] args, @CachedLibrary(value="receiver") InteropLibrary objects) {
                return objects.isTime(receiver);
            }
        }

        static abstract class AsDateNode
        extends InteropNode {
            protected AsDateNode(InteropValue interop) {
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

            @Specialization(limit="CACHE_LIMIT")
            static Object doCached(PolyglotLanguageContext context, Object receiver, Object[] args, @CachedLibrary(value="receiver") InteropLibrary objects, @Cached BranchProfile unsupported) {
                try {
                    return objects.asDate(receiver);
                }
                catch (UnsupportedMessageException e) {
                    unsupported.enter();
                    if (objects.isNull(receiver)) {
                        return null;
                    }
                    throw PolyglotValueDispatch.cannotConvert(context, receiver, null, "asDate()", "isDate()", "Value does not contain date information.");
                }
            }
        }

        static abstract class IsDateNode
        extends InteropNode {
            protected IsDateNode(InteropValue interop) {
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

            @Specialization(limit="CACHE_LIMIT")
            static Object doCached(PolyglotLanguageContext context, Object receiver, Object[] args, @CachedLibrary(value="receiver") InteropLibrary objects) {
                return objects.isDate(receiver);
            }
        }

        private final class MemberSet
        extends AbstractSet<String> {
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
                if (!(o instanceof String)) {
                    return false;
                }
                return InteropValue.this.hasMember(this.context, this.receiver, (String)o);
            }

            @Override
            public Iterator<String> iterator() {
                return new Iterator<String>(){
                    int index = 0;

                    @Override
                    public boolean hasNext() {
                        return this.index < MemberSet.this.size();
                    }

                    @Override
                    public String next() {
                        Value arrayElement;
                        if (this.index >= MemberSet.this.size()) {
                            throw new NoSuchElementException();
                        }
                        if ((arrayElement = MemberSet.this.keys.getArrayElement(this.index++)).isString()) {
                            return arrayElement.asString();
                        }
                        return null;
                    }
                };
            }

            @Override
            public int size() {
                int size = this.cachedSize;
                if (size != -1) {
                    return size;
                }
                this.cachedSize = size = (int)this.keys.getArraySize();
                return size;
            }
        }
    }

    static final class HostValue
    extends PolyglotValueDispatch {
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
            } else if (this.isHostObject(languageContext, receiver)) {
                hostValue = this.asHostObject(languageContext, receiver);
            } else {
                throw new ClassCastException();
            }
            return targetType.cast(hostValue);
        }
    }

    static abstract class InteropNode
    extends HostToGuestRootNode {
        protected static final int CACHE_LIMIT = 5;
        protected final InteropValue polyglot;

        protected abstract String getOperationName();

        protected InteropNode(InteropValue polyglot) {
            super(polyglot.languageInstance);
            this.polyglot = polyglot;
        }

        protected abstract Class<?>[] getArgumentTypes();

        protected Class<? extends Object> getReceiverType() {
            return this.polyglot.receiverType;
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

    private static final class HostNull
    extends PolyglotValueDispatch {
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
            if (targetType == Value.class) {
                return (T)this.polyglot.hostNull;
            }
            return null;
        }

        @Override
        public <T> T as(Object languageContext, Object receiver, TypeLiteral<T> targetType) {
            return this.as(languageContext, receiver, targetType.getRawType());
        }
    }

    static final class PrimitiveValue
    extends PolyglotValueDispatch {
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
            }
            catch (UnsupportedMessageException e) {
                return super.asBoolean(languageContext, receiver);
            }
        }

        @Override
        public String asString(Object languageContext, Object receiver) {
            try {
                return this.interop.asString(receiver);
            }
            catch (UnsupportedMessageException e) {
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
            }
            catch (UnsupportedMessageException e) {
                return super.asByte(languageContext, receiver);
            }
        }

        @Override
        public short asShort(Object languageContext, Object receiver) {
            try {
                return this.interop.asShort(receiver);
            }
            catch (UnsupportedMessageException e) {
                return super.asShort(languageContext, receiver);
            }
        }

        @Override
        public int asInt(Object languageContext, Object receiver) {
            try {
                return this.interop.asInt(receiver);
            }
            catch (UnsupportedMessageException e) {
                return super.asInt(languageContext, receiver);
            }
        }

        @Override
        public long asLong(Object languageContext, Object receiver) {
            try {
                return this.interop.asLong(receiver);
            }
            catch (UnsupportedMessageException e) {
                return super.asLong(languageContext, receiver);
            }
        }

        @Override
        public float asFloat(Object languageContext, Object receiver) {
            try {
                return this.interop.asFloat(receiver);
            }
            catch (UnsupportedMessageException e) {
                return super.asFloat(languageContext, receiver);
            }
        }

        @Override
        public double asDouble(Object languageContext, Object receiver) {
            try {
                return this.interop.asDouble(receiver);
            }
            catch (UnsupportedMessageException e) {
                return super.asDouble(languageContext, receiver);
            }
        }

        @Override
        public <T> T as(Object languageContext, Object receiver, Class<T> targetType) {
            PolyglotLanguageContext context = (PolyglotLanguageContext)languageContext;
            Object prev = PrimitiveValue.hostEnter(context);
            try {
                if (context != null) {
                    T t = this.language.engine.host.toHostType(null, context.context.getHostContextImpl(), receiver, targetType, targetType);
                    return t;
                }
                Object result = EngineAccessor.HOST.convertPrimitiveLossy(receiver, targetType);
                if (result == null) {
                    throw PolyglotInteropErrors.cannotConvertPrimitive(null, receiver, targetType);
                }
                Object object = result;
                return (T)object;
            }
            catch (Throwable e) {
                throw PrimitiveValue.guestToHostException(context, e, true);
            }
            finally {
                PrimitiveValue.hostLeave(context, prev);
            }
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
            if (languageContext == null || this.language == null) {
                return receiver;
            }
            PolyglotContextImpl c = ((PolyglotLanguageContext)languageContext).context;
            return c.getContext(this.language).getLanguageViewNoCheck(receiver);
        }
    }
}

