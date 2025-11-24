
package com.oracle.truffle.js.runtime.array;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.InvalidBufferOffsetException;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.builtins.helper.SharedMemorySync;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSAgentWaiterList;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSException;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.array.ByteArrayAccess;
import com.oracle.truffle.js.runtime.array.ByteBufferAccess;
import com.oracle.truffle.js.runtime.array.ScriptArray;
import com.oracle.truffle.js.runtime.array.TypedArrayFactory;
import com.oracle.truffle.js.runtime.builtins.JSArrayBuffer;
import com.oracle.truffle.js.runtime.builtins.JSArrayBufferObject;
import com.oracle.truffle.js.runtime.builtins.JSArrayBufferView;
import com.oracle.truffle.js.runtime.builtins.JSSharedArrayBuffer;
import com.oracle.truffle.js.runtime.builtins.JSTypedArrayObject;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.Undefined;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public abstract class TypedArray
extends ScriptArray {
    private final int bytesPerElement;
    private final boolean offset;
    private final byte bufferType;
    private final TruffleString name;
    private final TypedArrayFactory factory;
    protected static final byte BUFFER_TYPE_ARRAY = 0;
    protected static final byte BUFFER_TYPE_DIRECT = 1;
    protected static final byte BUFFER_TYPE_INTEROP = -1;
    static final int INT8_BYTES_PER_ELEMENT = 1;
    static final int UINT8_BYTES_PER_ELEMENT = 1;
    static final int INT16_BYTES_PER_ELEMENT = 2;
    static final int UINT16_BYTES_PER_ELEMENT = 2;
    static final int INT32_BYTES_PER_ELEMENT = 4;
    static final int UINT32_BYTES_PER_ELEMENT = 4;
    static final int BIGINT64_BYTES_PER_ELEMENT = 8;
    static final int BIGUINT64_BYTES_PER_ELEMENT = 8;
    static final int FLOAT32_BYTES_PER_ELEMENT = 4;
    static final int FLOAT64_BYTES_PER_ELEMENT = 8;

    protected TypedArray(TypedArrayFactory factory, boolean offset, byte bufferType) {
        this.bytesPerElement = factory.getBytesPerElement();
        this.offset = offset;
        this.bufferType = bufferType;
        this.name = factory.getName();
        this.factory = factory;
    }

    @Override
    public final long length(JSDynamicObject object) {
        return this.lengthInt(object);
    }

    @Override
    public final int lengthInt(JSDynamicObject object) {
        return JSArrayBufferView.typedArrayGetLength(object);
    }

    @Override
    public final TypedArray setLengthImpl(JSDynamicObject object, long len, ScriptArray.ProfileHolder profile) {
        return this;
    }

    @Override
    public final long firstElementIndex(JSDynamicObject object) {
        return 0L;
    }

    @Override
    public final long lastElementIndex(JSDynamicObject object) {
        return this.length(object) - 1L;
    }

    @Override
    public final long nextElementIndex(JSDynamicObject object, long index) {
        return index + 1L;
    }

    @Override
    public final long previousElementIndex(JSDynamicObject object, long index) {
        return index - 1L;
    }

    @Override
    public final ScriptArray deleteElementImpl(JSDynamicObject object, long index, boolean strict) {
        return this;
    }

    @Override
    public final boolean hasElement(JSDynamicObject object, long index) {
        return 0L <= index && index < this.length(object);
    }

    protected static ByteBuffer getDirectByteBuffer(Object buffer) {
        assert (!JSArrayBuffer.isDetachedBuffer(buffer));
        return JSArrayBuffer.getDirectByteBuffer(buffer);
    }

    protected static byte[] getByteArray(Object buffer) {
        assert (!JSArrayBuffer.isDetachedBuffer(buffer));
        return JSArrayBuffer.getByteArray(buffer);
    }

    public static JSArrayBufferObject getBufferFromTypedArray(JSDynamicObject typedArray) {
        return JSArrayBufferView.getArrayBuffer(typedArray);
    }

    public final int getOffset(JSDynamicObject object) {
        if (this.offset) {
            return JSArrayBufferView.typedArrayGetOffset(object);
        }
        return 0;
    }

    public final TypedArrayFactory getFactory() {
        return this.factory;
    }

    public final int bytesPerElement() {
        return this.bytesPerElement;
    }

    public final TruffleString getName() {
        return this.name;
    }

    @Override
    public boolean isHolesType() {
        return false;
    }

    @Override
    public boolean hasHoles(JSDynamicObject object) {
        return false;
    }

    @Override
    public ScriptArray removeRangeImpl(JSDynamicObject object, long start2, long end2) {
        throw Errors.unsupported("cannot removeRange() on TypedArray");
    }

    @Override
    public ScriptArray addRangeImpl(JSDynamicObject object, long atOffset, int size) {
        throw Errors.unsupported("cannot addRange() on TypedArray");
    }

    @Override
    public boolean isSealed() {
        return false;
    }

    @Override
    public boolean isFrozen() {
        return false;
    }

    @Override
    public boolean isLengthNotWritable() {
        return false;
    }

    @Override
    public ScriptArray seal() {
        return this;
    }

    @Override
    public ScriptArray freeze() {
        return this;
    }

    @Override
    public ScriptArray setLengthNotWritable() {
        return this;
    }

    @Override
    public ScriptArray preventExtensions() {
        return this;
    }

    public final boolean isDirect() {
        return this.bufferType > 0;
    }

    public final boolean isInterop() {
        return this.bufferType < 0;
    }

    public final boolean hasOffset() {
        return this.offset;
    }

    public abstract Object getBufferElement(Object var1, int var2, boolean var3, InteropLibrary var4);

    public abstract void setBufferElement(Object var1, int var2, boolean var3, Object var4, InteropLibrary var5);

    public static TypedArrayFactory[] factories() {
        return TypedArrayFactory.FACTORIES;
    }

    public static TypedArrayFactory[] factories(JSContext context) {
        if (context.getContextOptions().isBigInt()) {
            return TypedArrayFactory.FACTORIES;
        }
        return TypedArrayFactory.getNoBigIntFactories();
    }

    @CompilerDirectives.TruffleBoundary
    protected static JSException unsupportedBufferAccess(Object buffer, UnsupportedMessageException e) {
        return Errors.createTypeErrorInteropException(buffer, e, "buffer access", null);
    }

    public static final class InteropFloat64Array
    extends TypedFloatArray {
        InteropFloat64Array(TypedArrayFactory factory, boolean offset) {
            super(factory, offset, (byte)-1);
        }

        @Override
        public double getDoubleImpl(Object buffer, int offset, int index, InteropLibrary interop) {
            return InteropFloat64Array.readBufferDouble(buffer, offset + index * 8, ByteOrder.nativeOrder(), interop);
        }

        @Override
        public void setDoubleImpl(Object buffer, int offset, int index, double value2, InteropLibrary interop) {
            InteropFloat64Array.writeBufferDouble(buffer, offset + index * 8, (float)value2, ByteOrder.nativeOrder(), interop);
        }

        @Override
        public Number getBufferElement(Object buffer, int index, boolean littleEndian, InteropLibrary interop) {
            return InteropFloat64Array.readBufferDouble(buffer, index, littleEndian ? ByteOrder.LITTLE_ENDIAN : ByteOrder.BIG_ENDIAN, interop);
        }

        @Override
        public void setBufferElement(Object buffer, int index, boolean littleEndian, Object value2, InteropLibrary interop) {
            InteropFloat64Array.writeBufferDouble(buffer, index, JSRuntime.doubleValue((Number)value2), littleEndian ? ByteOrder.LITTLE_ENDIAN : ByteOrder.BIG_ENDIAN, interop);
        }

        static double readBufferDouble(Object buffer, int byteIndex, ByteOrder order, InteropLibrary interop) {
            try {
                return interop.readBufferDouble(buffer, order, byteIndex);
            }
            catch (UnsupportedMessageException e) {
                throw InteropFloat64Array.unsupportedBufferAccess(buffer, e);
            }
            catch (InvalidBufferOffsetException e) {
                throw Errors.createRangeErrorInvalidBufferOffset();
            }
        }

        static void writeBufferDouble(Object buffer, int byteIndex, double value2, ByteOrder order, InteropLibrary interop) {
            try {
                interop.writeBufferDouble(buffer, order, byteIndex, value2);
            }
            catch (UnsupportedMessageException e) {
                throw Errors.createTypeErrorReadOnlyBuffer();
            }
            catch (InvalidBufferOffsetException e) {
                throw Errors.createRangeErrorInvalidBufferOffset();
            }
        }
    }

    public static final class DirectFloat64Array
    extends TypedFloatArray {
        DirectFloat64Array(TypedArrayFactory factory, boolean offset) {
            super(factory, offset, (byte)1);
        }

        @Override
        public double getDoubleImpl(Object buffer, int offset, int index, InteropLibrary interop) {
            return ByteBufferAccess.nativeOrder().getDouble(DirectFloat64Array.getDirectByteBuffer(buffer), offset + index * 8);
        }

        @Override
        public void setDoubleImpl(Object buffer, int offset, int index, double value2, InteropLibrary interop) {
            ByteBufferAccess.nativeOrder().putDouble(DirectFloat64Array.getDirectByteBuffer(buffer), offset + index * 8, value2);
        }

        @Override
        public Number getBufferElement(Object buffer, int index, boolean littleEndian, InteropLibrary interop) {
            return ByteBufferAccess.forOrder(littleEndian).getDouble(DirectFloat64Array.getDirectByteBuffer(buffer), index);
        }

        @Override
        public void setBufferElement(Object buffer, int index, boolean littleEndian, Object value2, InteropLibrary interop) {
            ByteBufferAccess.forOrder(littleEndian).putDouble(DirectFloat64Array.getDirectByteBuffer(buffer), index, JSRuntime.doubleValue((Number)value2));
        }
    }

    public static final class Float64Array
    extends TypedFloatArray {
        Float64Array(TypedArrayFactory factory, boolean offset) {
            super(factory, offset, (byte)0);
        }

        @Override
        public double getDoubleImpl(Object buffer, int offset, int index, InteropLibrary interop) {
            return ByteArrayAccess.nativeOrder().getDouble(Float64Array.getByteArray(buffer), offset + index * 8);
        }

        @Override
        public void setDoubleImpl(Object buffer, int offset, int index, double value2, InteropLibrary interop) {
            ByteArrayAccess.nativeOrder().putDouble(Float64Array.getByteArray(buffer), offset + index * 8, value2);
        }

        @Override
        public Number getBufferElement(Object buffer, int index, boolean littleEndian, InteropLibrary interop) {
            return ByteArrayAccess.forOrder(littleEndian).getDouble(Float64Array.getByteArray(buffer), index);
        }

        @Override
        public void setBufferElement(Object buffer, int index, boolean littleEndian, Object value2, InteropLibrary interop) {
            ByteArrayAccess.forOrder(littleEndian).putDouble(Float64Array.getByteArray(buffer), index, JSRuntime.doubleValue((Number)value2));
        }
    }

    public static final class InteropFloat32Array
    extends TypedFloatArray {
        InteropFloat32Array(TypedArrayFactory factory, boolean offset) {
            super(factory, offset, (byte)-1);
        }

        @Override
        public double getDoubleImpl(Object buffer, int offset, int index, InteropLibrary interop) {
            return InteropFloat32Array.readBufferFloat(buffer, offset + index * 4, ByteOrder.nativeOrder(), interop);
        }

        @Override
        public void setDoubleImpl(Object buffer, int offset, int index, double value2, InteropLibrary interop) {
            InteropFloat32Array.writeBufferFloat(buffer, offset + index * 4, (float)value2, ByteOrder.nativeOrder(), interop);
        }

        @Override
        public Number getBufferElement(Object buffer, int index, boolean littleEndian, InteropLibrary interop) {
            return (double)InteropFloat32Array.readBufferFloat(buffer, index, littleEndian ? ByteOrder.LITTLE_ENDIAN : ByteOrder.BIG_ENDIAN, interop);
        }

        @Override
        public void setBufferElement(Object buffer, int index, boolean littleEndian, Object value2, InteropLibrary interop) {
            InteropFloat32Array.writeBufferFloat(buffer, index, JSRuntime.floatValue((Number)value2), littleEndian ? ByteOrder.LITTLE_ENDIAN : ByteOrder.BIG_ENDIAN, interop);
        }

        static float readBufferFloat(Object buffer, int byteIndex, ByteOrder order, InteropLibrary interop) {
            try {
                return interop.readBufferFloat(buffer, order, byteIndex);
            }
            catch (UnsupportedMessageException e) {
                throw InteropFloat32Array.unsupportedBufferAccess(buffer, e);
            }
            catch (InvalidBufferOffsetException e) {
                throw Errors.createRangeErrorInvalidBufferOffset();
            }
        }

        static void writeBufferFloat(Object buffer, int byteIndex, float value2, ByteOrder order, InteropLibrary interop) {
            try {
                interop.writeBufferFloat(buffer, order, byteIndex, value2);
            }
            catch (UnsupportedMessageException e) {
                throw Errors.createTypeErrorReadOnlyBuffer();
            }
            catch (InvalidBufferOffsetException e) {
                throw Errors.createRangeErrorInvalidBufferOffset();
            }
        }
    }

    public static final class DirectFloat32Array
    extends TypedFloatArray {
        DirectFloat32Array(TypedArrayFactory factory, boolean offset) {
            super(factory, offset, (byte)1);
        }

        @Override
        public double getDoubleImpl(Object buffer, int offset, int index, InteropLibrary interop) {
            return ByteBufferAccess.nativeOrder().getFloat(DirectFloat32Array.getDirectByteBuffer(buffer), offset + index * 4);
        }

        @Override
        public void setDoubleImpl(Object buffer, int offset, int index, double value2, InteropLibrary interop) {
            ByteBufferAccess.nativeOrder().putFloat(DirectFloat32Array.getDirectByteBuffer(buffer), offset + index * 4, (float)value2);
        }

        @Override
        public Number getBufferElement(Object buffer, int index, boolean littleEndian, InteropLibrary interop) {
            return (double)ByteBufferAccess.forOrder(littleEndian).getFloat(DirectFloat32Array.getDirectByteBuffer(buffer), index);
        }

        @Override
        public void setBufferElement(Object buffer, int index, boolean littleEndian, Object value2, InteropLibrary interop) {
            ByteBufferAccess.forOrder(littleEndian).putFloat(DirectFloat32Array.getDirectByteBuffer(buffer), index, JSRuntime.floatValue((Number)value2));
        }
    }

    public static final class Float32Array
    extends TypedFloatArray {
        Float32Array(TypedArrayFactory factory, boolean offset) {
            super(factory, offset, (byte)0);
        }

        @Override
        public double getDoubleImpl(Object buffer, int offset, int index, InteropLibrary interop) {
            return ByteArrayAccess.nativeOrder().getFloat(Float32Array.getByteArray(buffer), offset + index * 4);
        }

        @Override
        public void setDoubleImpl(Object buffer, int offset, int index, double value2, InteropLibrary interop) {
            ByteArrayAccess.nativeOrder().putFloat(Float32Array.getByteArray(buffer), offset + index * 4, (float)value2);
        }

        @Override
        public Number getBufferElement(Object buffer, int index, boolean littleEndian, InteropLibrary interop) {
            return (double)ByteArrayAccess.forOrder(littleEndian).getFloat(Float32Array.getByteArray(buffer), index);
        }

        @Override
        public void setBufferElement(Object buffer, int index, boolean littleEndian, Object value2, InteropLibrary interop) {
            ByteArrayAccess.forOrder(littleEndian).putFloat(Float32Array.getByteArray(buffer), index, JSRuntime.floatValue((Number)value2));
        }
    }

    public static abstract class TypedFloatArray
    extends TypedArray {
        protected TypedFloatArray(TypedArrayFactory factory, boolean offset, byte bufferType) {
            super(factory, offset, bufferType);
        }

        @Override
        public final Object getElement(JSDynamicObject object, long index) {
            if (this.hasElement(object, index)) {
                return this.getDouble(object, (int)index, InteropLibrary.getUncached());
            }
            return Undefined.instance;
        }

        @Override
        public Object getElementInBounds(JSDynamicObject object, long index) {
            assert (this.hasElement(object, index));
            return this.getDouble(object, (int)index, InteropLibrary.getUncached());
        }

        @Override
        public final TypedFloatArray setElementImpl(JSDynamicObject object, long index, Object value2, boolean strict) {
            if (this.hasElement(object, index)) {
                this.setDouble(object, (int)index, JSRuntime.toDouble(value2), InteropLibrary.getUncached());
            }
            return this;
        }

        public final double getDouble(JSDynamicObject object, int index, InteropLibrary interop) {
            return this.getDoubleImpl(TypedFloatArray.getBufferFromTypedArray(object), this.getOffset(object), index, interop);
        }

        public final void setDouble(JSDynamicObject object, int index, double value2, InteropLibrary interop) {
            this.setDoubleImpl(TypedFloatArray.getBufferFromTypedArray(object), this.getOffset(object), index, value2, interop);
        }

        public abstract double getDoubleImpl(Object var1, int var2, int var3, InteropLibrary var4);

        public abstract void setDoubleImpl(Object var1, int var2, int var3, double var4, InteropLibrary var6);
    }

    public static final class InteropBigUint64Array
    extends InteropBigIntArray {
        InteropBigUint64Array(TypedArrayFactory factory, boolean offset) {
            super(factory, offset);
        }

        @Override
        public BigInt getBigIntImpl(Object buffer, int offset, int index, InteropLibrary interop) {
            return BigInt.valueOfUnsigned(this.getLongImpl(buffer, offset, index, interop));
        }

        @Override
        public Object getBufferElement(Object buffer, int index, boolean littleEndian, InteropLibrary interop) {
            return BigInt.valueOfUnsigned(this.getBufferElementLongImpl(buffer, index, littleEndian, interop));
        }
    }

    public static final class DirectBigUint64Array
    extends TypedBigIntArray {
        DirectBigUint64Array(TypedArrayFactory factory, boolean offset) {
            super(factory, offset, (byte)1);
        }

        @Override
        public Object getBufferElement(Object buffer, int index, boolean littleEndian, InteropLibrary interop) {
            return BigInt.valueOfUnsigned(this.getBufferElementLongImpl(buffer, index, littleEndian, interop));
        }

        @Override
        public BigInt getBigIntImpl(Object buffer, int offset, int index, InteropLibrary interop) {
            return BigInt.valueOfUnsigned(this.getLongImpl(buffer, offset, index, interop));
        }

        @Override
        public long getBufferElementLongImpl(Object buffer, int index, boolean littleEndian, InteropLibrary interop) {
            return ByteBufferAccess.forOrder(littleEndian).getInt64(DirectBigUint64Array.getDirectByteBuffer(buffer), index);
        }

        @Override
        public void setBufferElementLongImpl(Object buffer, int index, boolean littleEndian, long value2, InteropLibrary interop) {
            ByteBufferAccess.forOrder(littleEndian).putInt64(DirectBigUint64Array.getDirectByteBuffer(buffer), index, value2);
        }

        @Override
        public long getLongImpl(Object buffer, int offset, int index, InteropLibrary interop) {
            return ByteBufferAccess.nativeOrder().getInt64(DirectBigUint64Array.getDirectByteBuffer(buffer), offset + index * 8);
        }

        @Override
        public void setLongImpl(Object buffer, int offset, int index, long value2, InteropLibrary interop) {
            ByteBufferAccess.nativeOrder().putInt64(DirectBigUint64Array.getDirectByteBuffer(buffer), offset + index * 8, value2);
        }

        @Override
        public long compareExchangeLong(JSTypedArrayObject typedArray, int index, long expectedValue, long newValue) {
            return ByteBufferAccess.nativeOrder().compareExchangeInt64(DirectBigUint64Array.getDirectByteBuffer(DirectBigUint64Array.getBufferFromTypedArray(typedArray)), this.getOffset(typedArray) + index * 8, expectedValue, newValue);
        }

        @Override
        public BigInt compareExchangeBigInt(JSTypedArrayObject typedArray, int index, BigInt expectedValue, BigInt newValue) {
            return BigInt.valueOfUnsigned(this.compareExchangeLong(typedArray, index, expectedValue.longValue(), newValue.longValue()));
        }
    }

    public static final class BigUint64Array
    extends TypedBigIntArray {
        BigUint64Array(TypedArrayFactory factory, boolean offset) {
            super(factory, offset, (byte)0);
        }

        @Override
        public Object getBufferElement(Object buffer, int index, boolean littleEndian, InteropLibrary interop) {
            return BigInt.valueOfUnsigned(this.getBufferElementLongImpl(buffer, index, littleEndian, interop));
        }

        @Override
        public BigInt getBigIntImpl(Object buffer, int offset, int index, InteropLibrary interop) {
            return BigInt.valueOfUnsigned(this.getLongImpl(buffer, offset, index, interop));
        }

        @Override
        public long getBufferElementLongImpl(Object buffer, int index, boolean littleEndian, InteropLibrary interop) {
            return ByteArrayAccess.forOrder(littleEndian).getInt64(BigUint64Array.getByteArray(buffer), index);
        }

        @Override
        public void setBufferElementLongImpl(Object buffer, int index, boolean littleEndian, long value2, InteropLibrary interop) {
            ByteArrayAccess.forOrder(littleEndian).putInt64(BigUint64Array.getByteArray(buffer), index, value2);
        }

        @Override
        public long getLongImpl(Object buffer, int offset, int index, InteropLibrary interop) {
            return ByteArrayAccess.nativeOrder().getInt64(BigUint64Array.getByteArray(buffer), offset + index * 8);
        }

        @Override
        public void setLongImpl(Object buffer, int offset, int index, long value2, InteropLibrary interop) {
            ByteArrayAccess.nativeOrder().putInt64(BigUint64Array.getByteArray(buffer), offset + index * 8, value2);
        }
    }

    public static class InteropBigIntArray
    extends TypedBigIntArray {
        InteropBigIntArray(TypedArrayFactory factory, boolean offset) {
            super(factory, offset, (byte)-1);
        }

        @Override
        public long getLongImpl(Object buffer, int offset, int index, InteropLibrary interop) {
            return InteropBigIntArray.readBufferLong(buffer, offset + index * 8, ByteOrder.nativeOrder(), interop);
        }

        @Override
        public void setLongImpl(Object buffer, int offset, int index, long value2, InteropLibrary interop) {
            InteropBigIntArray.writeBufferLong(buffer, offset + index * 8, value2, ByteOrder.nativeOrder(), interop);
        }

        @Override
        public long getBufferElementLongImpl(Object buffer, int index, boolean littleEndian, InteropLibrary interop) {
            return InteropBigIntArray.readBufferLong(buffer, index, littleEndian ? ByteOrder.LITTLE_ENDIAN : ByteOrder.BIG_ENDIAN, interop);
        }

        @Override
        public void setBufferElementLongImpl(Object buffer, int index, boolean littleEndian, long value2, InteropLibrary interop) {
            InteropBigIntArray.writeBufferLong(buffer, index, value2, littleEndian ? ByteOrder.LITTLE_ENDIAN : ByteOrder.BIG_ENDIAN, interop);
        }

        static long readBufferLong(Object buffer, int byteIndex, ByteOrder order, InteropLibrary interop) {
            try {
                return interop.readBufferLong(buffer, order, byteIndex);
            }
            catch (UnsupportedMessageException e) {
                throw InteropBigIntArray.unsupportedBufferAccess(buffer, e);
            }
            catch (InvalidBufferOffsetException e) {
                throw Errors.createRangeErrorInvalidBufferOffset();
            }
        }

        static void writeBufferLong(Object buffer, int byteIndex, long value2, ByteOrder order, InteropLibrary interop) {
            try {
                interop.writeBufferLong(buffer, order, byteIndex, value2);
            }
            catch (UnsupportedMessageException e) {
                throw Errors.createTypeErrorReadOnlyBuffer();
            }
            catch (InvalidBufferOffsetException e) {
                throw Errors.createRangeErrorInvalidBufferOffset();
            }
        }
    }

    public static class InteropBigInt64Array
    extends InteropBigIntArray {
        InteropBigInt64Array(TypedArrayFactory factory, boolean offset) {
            super(factory, offset);
        }
    }

    public static final class DirectBigInt64Array
    extends TypedBigIntArray {
        DirectBigInt64Array(TypedArrayFactory factory, boolean offset) {
            super(factory, offset, (byte)1);
        }

        @Override
        public long getBufferElementLongImpl(Object buffer, int index, boolean littleEndian, InteropLibrary interop) {
            return ByteBufferAccess.forOrder(littleEndian).getInt64(DirectBigInt64Array.getDirectByteBuffer(buffer), index);
        }

        @Override
        public void setBufferElementLongImpl(Object buffer, int index, boolean littleEndian, long value2, InteropLibrary interop) {
            ByteBufferAccess.forOrder(littleEndian).putInt64(DirectBigInt64Array.getDirectByteBuffer(buffer), index, value2);
        }

        @Override
        public long getLongImpl(Object buffer, int offset, int index, InteropLibrary interop) {
            return ByteBufferAccess.nativeOrder().getInt64(DirectBigInt64Array.getDirectByteBuffer(buffer), offset + index * 8);
        }

        @Override
        public void setLongImpl(Object buffer, int offset, int index, long value2, InteropLibrary interop) {
            ByteBufferAccess.nativeOrder().putInt64(DirectBigInt64Array.getDirectByteBuffer(buffer), offset + index * 8, value2);
        }

        @Override
        public long compareExchangeLong(JSTypedArrayObject typedArray, int index, long expectedValue, long newValue) {
            return ByteBufferAccess.nativeOrder().compareExchangeInt64(DirectBigInt64Array.getDirectByteBuffer(DirectBigInt64Array.getBufferFromTypedArray(typedArray)), this.getOffset(typedArray) + index * 8, expectedValue, newValue);
        }

        @Override
        public BigInt compareExchangeBigInt(JSTypedArrayObject typedArray, int index, BigInt expectedValue, BigInt newValue) {
            return BigInt.valueOf(this.compareExchangeLong(typedArray, index, expectedValue.longValue(), newValue.longValue()));
        }
    }

    public static final class BigInt64Array
    extends TypedBigIntArray {
        BigInt64Array(TypedArrayFactory factory, boolean offset) {
            super(factory, offset, (byte)0);
        }

        @Override
        public long getBufferElementLongImpl(Object buffer, int index, boolean littleEndian, InteropLibrary interop) {
            return ByteArrayAccess.forOrder(littleEndian).getInt64(BigInt64Array.getByteArray(buffer), index);
        }

        @Override
        public void setBufferElementLongImpl(Object buffer, int index, boolean littleEndian, long value2, InteropLibrary interop) {
            ByteArrayAccess.forOrder(littleEndian).putInt64(BigInt64Array.getByteArray(buffer), index, value2);
        }

        @Override
        public long getLongImpl(Object buffer, int offset, int index, InteropLibrary interop) {
            return ByteArrayAccess.nativeOrder().getInt64(BigInt64Array.getByteArray(buffer), offset + index * 8);
        }

        @Override
        public void setLongImpl(Object buffer, int offset, int index, long value2, InteropLibrary interop) {
            ByteArrayAccess.nativeOrder().putInt64(BigInt64Array.getByteArray(buffer), offset + index * 8, value2);
        }
    }

    public static abstract class TypedBigIntArray
    extends TypedArray {
        protected TypedBigIntArray(TypedArrayFactory factory, boolean offset, byte bufferType) {
            super(factory, offset, bufferType);
        }

        @Override
        public Object getElement(JSDynamicObject object, long index) {
            if (this.hasElement(object, index)) {
                return this.getBigInt(object, (int)index, InteropLibrary.getUncached());
            }
            return Undefined.instance;
        }

        @Override
        public Object getElementInBounds(JSDynamicObject object, long index) {
            assert (this.hasElement(object, index));
            return this.getBigInt(object, (int)index, InteropLibrary.getUncached());
        }

        @Override
        public TypedBigIntArray setElementImpl(JSDynamicObject object, long index, Object value2, boolean strict) {
            if (this.hasElement(object, index)) {
                this.setBigInt(object, (int)index, JSRuntime.toBigInt(value2), InteropLibrary.getUncached());
            }
            return this;
        }

        public final BigInt getBigInt(JSDynamicObject object, int index, InteropLibrary interop) {
            return this.getBigIntImpl(TypedBigIntArray.getBufferFromTypedArray(object), this.getOffset(object), index, interop);
        }

        public final void setBigInt(JSDynamicObject object, int index, BigInt value2, InteropLibrary interop) {
            this.setLongImpl(TypedBigIntArray.getBufferFromTypedArray(object), this.getOffset(object), index, value2.longValue(), interop);
        }

        public BigInt getBigIntImpl(Object buffer, int offset, int index, InteropLibrary interop) {
            return BigInt.valueOf(this.getLongImpl(buffer, offset, index, interop));
        }

        public abstract long getLongImpl(Object var1, int var2, int var3, InteropLibrary var4);

        public abstract void setLongImpl(Object var1, int var2, int var3, long var4, InteropLibrary var6);

        @Override
        public Object getBufferElement(Object buffer, int index, boolean littleEndian, InteropLibrary interop) {
            return BigInt.valueOf(this.getBufferElementLongImpl(buffer, index, littleEndian, interop));
        }

        public abstract long getBufferElementLongImpl(Object var1, int var2, boolean var3, InteropLibrary var4);

        @Override
        public final void setBufferElement(Object buffer, int index, boolean littleEndian, Object value2, InteropLibrary interop) {
            this.setBufferElementLongImpl(buffer, index, littleEndian, JSRuntime.toBigInt(value2).longValue(), interop);
        }

        public abstract void setBufferElementLongImpl(Object var1, int var2, boolean var3, long var4, InteropLibrary var6);

        public long compareExchangeLong(JSTypedArrayObject typedArray, int index, long expectedValue, long newValue) {
            throw Errors.shouldNotReachHere();
        }

        public BigInt compareExchangeBigInt(JSTypedArrayObject typedArray, int index, BigInt expectedValue, BigInt newValue) {
            throw Errors.shouldNotReachHere();
        }
    }

    public static final class InteropUint32Array
    extends AbstractUint32Array {
        InteropUint32Array(TypedArrayFactory factory, boolean offset) {
            super(factory, offset, (byte)-1);
        }

        @Override
        public int getIntImpl(Object buffer, int offset, int index, InteropLibrary interop) {
            return InteropInt32Array.readBufferInt(buffer, offset + index * 4, ByteOrder.nativeOrder(), interop);
        }

        @Override
        public void setIntImpl(Object buffer, int offset, int index, int value2, InteropLibrary interop) {
            InteropInt32Array.writeBufferInt(buffer, offset + index * 4, value2, ByteOrder.nativeOrder(), interop);
        }

        @Override
        public int getBufferElementIntImpl(Object buffer, int index, boolean littleEndian, InteropLibrary interop) {
            return InteropInt32Array.readBufferInt(buffer, index, littleEndian ? ByteOrder.LITTLE_ENDIAN : ByteOrder.BIG_ENDIAN, interop);
        }

        @Override
        public void setBufferElementIntImpl(Object buffer, int index, boolean littleEndian, int value2, InteropLibrary interop) {
            InteropInt32Array.writeBufferInt(buffer, index, value2, littleEndian ? ByteOrder.LITTLE_ENDIAN : ByteOrder.BIG_ENDIAN, interop);
        }
    }

    public static final class DirectUint32Array
    extends AbstractUint32Array {
        DirectUint32Array(TypedArrayFactory factory, boolean offset) {
            super(factory, offset, (byte)1);
        }

        @Override
        public int getIntImpl(Object buffer, int offset, int index, InteropLibrary interop) {
            return ByteBufferAccess.nativeOrder().getInt32(DirectUint32Array.getDirectByteBuffer(buffer), offset + index * 4);
        }

        @Override
        public void setIntImpl(Object buffer, int offset, int index, int value2, InteropLibrary interop) {
            ByteBufferAccess.nativeOrder().putInt32(DirectUint32Array.getDirectByteBuffer(buffer), offset + index * 4, value2);
        }

        @Override
        public int getBufferElementIntImpl(Object buffer, int index, boolean littleEndian, InteropLibrary interop) {
            return ByteBufferAccess.forOrder(littleEndian).getInt32(DirectUint32Array.getDirectByteBuffer(buffer), index);
        }

        @Override
        public void setBufferElementIntImpl(Object buffer, int index, boolean littleEndian, int value2, InteropLibrary interop) {
            ByteBufferAccess.forOrder(littleEndian).putInt32(DirectUint32Array.getDirectByteBuffer(buffer), index, value2);
        }

        @Override
        public int compareExchangeInt(JSTypedArrayObject typedArray, int index, int expectedValue, int newValue) {
            ByteBuffer byteBuffer = DirectUint32Array.getDirectByteBuffer(DirectUint32Array.getBufferFromTypedArray(typedArray));
            int bufferOffset = this.getOffset(typedArray) + index * 4;
            return ByteBufferAccess.nativeOrder().compareExchangeInt32(byteBuffer, bufferOffset, expectedValue, newValue);
        }
    }

    public static final class Uint32Array
    extends AbstractUint32Array {
        Uint32Array(TypedArrayFactory factory, boolean offset) {
            super(factory, offset, (byte)0);
        }

        @Override
        public int getIntImpl(Object buffer, int offset, int index, InteropLibrary interop) {
            return ByteArrayAccess.nativeOrder().getInt32(Uint32Array.getByteArray(buffer), offset + index * 4);
        }

        @Override
        public void setIntImpl(Object buffer, int offset, int index, int value2, InteropLibrary interop) {
            ByteArrayAccess.nativeOrder().putInt32(Uint32Array.getByteArray(buffer), offset + index * 4, value2);
        }

        @Override
        public int getBufferElementIntImpl(Object buffer, int index, boolean littleEndian, InteropLibrary interop) {
            return ByteArrayAccess.forOrder(littleEndian).getInt32(Uint32Array.getByteArray(buffer), index);
        }

        @Override
        public void setBufferElementIntImpl(Object buffer, int index, boolean littleEndian, int value2, InteropLibrary interop) {
            ByteArrayAccess.forOrder(littleEndian).putInt32(Uint32Array.getByteArray(buffer), index, value2);
        }
    }

    public static abstract class AbstractUint32Array
    extends TypedIntArray {
        private AbstractUint32Array(TypedArrayFactory factory, boolean offset, byte bufferType) {
            super(factory, offset, bufferType);
        }

        @Override
        public Object getElement(JSDynamicObject object, long index) {
            if (this.hasElement(object, index)) {
                int value2 = this.getInt(object, (int)index, InteropLibrary.getUncached());
                return AbstractUint32Array.toUint32(value2);
            }
            return Undefined.instance;
        }

        protected static Number toUint32(int value2) {
            if (value2 >= 0) {
                return value2;
            }
            return (double)((long)value2 & 0xFFFFFFFFL);
        }

        @Override
        public Object getElementInBounds(JSDynamicObject object, long index) {
            assert (this.hasElement(object, index));
            return AbstractUint32Array.toUint32(this.getInt(object, (int)index, InteropLibrary.getUncached()));
        }

        @Override
        public Object getBufferElement(Object buffer, int index, boolean littleEndian, InteropLibrary interop) {
            return AbstractUint32Array.toUint32(this.getBufferElementIntImpl(buffer, index, littleEndian, interop));
        }
    }

    public static final class InteropInt32Array
    extends TypedIntArray {
        InteropInt32Array(TypedArrayFactory factory, boolean offset) {
            super(factory, offset, (byte)-1);
        }

        @Override
        public int getIntImpl(Object buffer, int offset, int index, InteropLibrary interop) {
            return InteropInt32Array.readBufferInt(buffer, offset + index * 4, ByteOrder.nativeOrder(), interop);
        }

        @Override
        public void setIntImpl(Object buffer, int offset, int index, int value2, InteropLibrary interop) {
            InteropInt32Array.writeBufferInt(buffer, offset + index * 4, value2, ByteOrder.nativeOrder(), interop);
        }

        @Override
        public int getBufferElementIntImpl(Object buffer, int index, boolean littleEndian, InteropLibrary interop) {
            return InteropInt32Array.readBufferInt(buffer, index, littleEndian ? ByteOrder.LITTLE_ENDIAN : ByteOrder.BIG_ENDIAN, interop);
        }

        @Override
        public void setBufferElementIntImpl(Object buffer, int index, boolean littleEndian, int value2, InteropLibrary interop) {
            InteropInt32Array.writeBufferInt(buffer, index, value2, littleEndian ? ByteOrder.LITTLE_ENDIAN : ByteOrder.BIG_ENDIAN, interop);
        }

        static int readBufferInt(Object buffer, int byteIndex, ByteOrder order, InteropLibrary interop) {
            try {
                return interop.readBufferInt(buffer, order, byteIndex);
            }
            catch (UnsupportedMessageException e) {
                throw InteropInt32Array.unsupportedBufferAccess(buffer, e);
            }
            catch (InvalidBufferOffsetException e) {
                throw Errors.createRangeErrorInvalidBufferOffset();
            }
        }

        static void writeBufferInt(Object buffer, int byteIndex, int value2, ByteOrder order, InteropLibrary interop) {
            try {
                interop.writeBufferInt(buffer, order, byteIndex, value2);
            }
            catch (UnsupportedMessageException e) {
                throw Errors.createTypeErrorReadOnlyBuffer();
            }
            catch (InvalidBufferOffsetException e) {
                throw Errors.createRangeErrorInvalidBufferOffset();
            }
        }
    }

    public static final class DirectInt32Array
    extends TypedIntArray {
        DirectInt32Array(TypedArrayFactory factory, boolean offset) {
            super(factory, offset, (byte)1);
        }

        @Override
        public int getIntImpl(Object buffer, int offset, int index, InteropLibrary interop) {
            return ByteBufferAccess.nativeOrder().getInt32(DirectInt32Array.getDirectByteBuffer(buffer), offset + index * 4);
        }

        @Override
        public void setIntImpl(Object buffer, int offset, int index, int value2, InteropLibrary interop) {
            ByteBufferAccess.nativeOrder().putInt32(DirectInt32Array.getDirectByteBuffer(buffer), offset + index * 4, value2);
        }

        @Override
        public int getBufferElementIntImpl(Object buffer, int index, boolean littleEndian, InteropLibrary interop) {
            return ByteBufferAccess.forOrder(littleEndian).getInt32(DirectInt32Array.getDirectByteBuffer(buffer), index);
        }

        @Override
        public void setBufferElementIntImpl(Object buffer, int index, boolean littleEndian, int value2, InteropLibrary interop) {
            ByteBufferAccess.forOrder(littleEndian).putInt32(DirectInt32Array.getDirectByteBuffer(buffer), index, value2);
        }

        @Override
        public int compareExchangeInt(JSTypedArrayObject typedArray, int index, int expectedValue, int newValue) {
            ByteBuffer byteBuffer = DirectInt32Array.getDirectByteBuffer(DirectInt32Array.getBufferFromTypedArray(typedArray));
            int bufferOffset = this.getOffset(typedArray) + index * 4;
            return ByteBufferAccess.nativeOrder().compareExchangeInt32(byteBuffer, bufferOffset, expectedValue, newValue);
        }
    }

    public static final class Int32Array
    extends TypedIntArray {
        Int32Array(TypedArrayFactory factory, boolean offset) {
            super(factory, offset, (byte)0);
        }

        @Override
        public int getIntImpl(Object buffer, int offset, int index, InteropLibrary interop) {
            return ByteArrayAccess.nativeOrder().getInt32(Int32Array.getByteArray(buffer), offset + index * 4);
        }

        @Override
        public void setIntImpl(Object buffer, int offset, int index, int value2, InteropLibrary interop) {
            ByteArrayAccess.nativeOrder().putInt32(Int32Array.getByteArray(buffer), offset + index * 4, value2);
        }

        @Override
        public int getBufferElementIntImpl(Object buffer, int index, boolean littleEndian, InteropLibrary interop) {
            return ByteArrayAccess.forOrder(littleEndian).getInt32(Int32Array.getByteArray(buffer), index);
        }

        @Override
        public void setBufferElementIntImpl(Object buffer, int index, boolean littleEndian, int value2, InteropLibrary interop) {
            ByteArrayAccess.forOrder(littleEndian).putInt32(Int32Array.getByteArray(buffer), index, value2);
        }
    }

    public static final class InteropUint16Array
    extends InteropTwoByteIntArray {
        InteropUint16Array(TypedArrayFactory factory, boolean offset) {
            super(factory, offset);
        }

        @Override
        public int getIntImpl(Object buffer, int offset, int index, InteropLibrary interop) {
            return super.getIntImpl(buffer, offset, index, interop) & 0xFFFF;
        }

        @Override
        public int getBufferElementIntImpl(Object buffer, int index, boolean littleEndian, InteropLibrary interop) {
            return super.getBufferElementIntImpl(buffer, index, littleEndian, interop) & 0xFFFF;
        }
    }

    public static final class DirectUint16Array
    extends TypedIntArray {
        DirectUint16Array(TypedArrayFactory factory, boolean offset) {
            super(factory, offset, (byte)1);
        }

        @Override
        public int getIntImpl(Object buffer, int offset, int index, InteropLibrary interop) {
            return ByteBufferAccess.nativeOrder().getUint16(DirectUint16Array.getDirectByteBuffer(buffer), offset + index * 2);
        }

        @Override
        public void setIntImpl(Object buffer, int offset, int index, int value2, InteropLibrary interop) {
            ByteBufferAccess.nativeOrder().putInt16(DirectUint16Array.getDirectByteBuffer(buffer), offset + index * 2, (char)value2);
        }

        @Override
        public int getBufferElementIntImpl(Object buffer, int index, boolean littleEndian, InteropLibrary interop) {
            return ByteBufferAccess.forOrder(littleEndian).getUint16(DirectUint16Array.getDirectByteBuffer(buffer), index);
        }

        @Override
        public void setBufferElementIntImpl(Object buffer, int index, boolean littleEndian, int value2, InteropLibrary interop) {
            ByteBufferAccess.forOrder(littleEndian).putInt16(DirectUint16Array.getDirectByteBuffer(buffer), index, (char)value2);
        }

        @Override
        public int compareExchangeInt(JSTypedArrayObject typedArrayObject, int index, int expectedValue, int newValue) {
            int byteOffset;
            ByteBuffer byteBuffer = DirectUint16Array.getDirectByteBuffer(DirectUint16Array.getBufferFromTypedArray(typedArrayObject));
            if (DirectUint16Array.isCompareExchangeSupported(byteBuffer, byteOffset = this.getOffset(typedArrayObject) + index * 2)) {
                return ByteBufferAccess.nativeOrder().compareExchangeInt16(byteBuffer, byteOffset, expectedValue, newValue) & 0xFFFF;
            }
            return this.lockedReadModifyWriteShort(typedArrayObject, index, expectedValue, newValue) & 0xFFFF;
        }
    }

    public static final class Uint16Array
    extends TypedIntArray {
        Uint16Array(TypedArrayFactory factory, boolean offset) {
            super(factory, offset, (byte)0);
        }

        @Override
        public int getIntImpl(Object buffer, int offset, int index, InteropLibrary interop) {
            return ByteArrayAccess.nativeOrder().getUint16(Uint16Array.getByteArray(buffer), offset + index * 2);
        }

        @Override
        public void setIntImpl(Object buffer, int offset, int index, int value2, InteropLibrary interop) {
            ByteArrayAccess.nativeOrder().putInt16(Uint16Array.getByteArray(buffer), offset + index * 2, value2);
        }

        @Override
        public int getBufferElementIntImpl(Object buffer, int index, boolean littleEndian, InteropLibrary interop) {
            return ByteArrayAccess.forOrder(littleEndian).getUint16(Uint16Array.getByteArray(buffer), index);
        }

        @Override
        public void setBufferElementIntImpl(Object buffer, int index, boolean littleEndian, int value2, InteropLibrary interop) {
            ByteArrayAccess.forOrder(littleEndian).putInt16(Uint16Array.getByteArray(buffer), index, value2);
        }
    }

    public static class InteropTwoByteIntArray
    extends TypedIntArray {
        InteropTwoByteIntArray(TypedArrayFactory factory, boolean offset) {
            super(factory, offset, (byte)-1);
        }

        @Override
        public int getIntImpl(Object buffer, int offset, int index, InteropLibrary interop) {
            return InteropTwoByteIntArray.readBufferShort(buffer, offset + index * 2, ByteOrder.nativeOrder(), interop);
        }

        @Override
        public void setIntImpl(Object buffer, int offset, int index, int value2, InteropLibrary interop) {
            InteropTwoByteIntArray.writeBufferShort(buffer, offset + index * 2, (short)value2, ByteOrder.nativeOrder(), interop);
        }

        @Override
        public int getBufferElementIntImpl(Object buffer, int index, boolean littleEndian, InteropLibrary interop) {
            return InteropTwoByteIntArray.readBufferShort(buffer, index, littleEndian ? ByteOrder.LITTLE_ENDIAN : ByteOrder.BIG_ENDIAN, interop);
        }

        @Override
        public void setBufferElementIntImpl(Object buffer, int index, boolean littleEndian, int value2, InteropLibrary interop) {
            InteropTwoByteIntArray.writeBufferShort(buffer, index, (short)value2, littleEndian ? ByteOrder.LITTLE_ENDIAN : ByteOrder.BIG_ENDIAN, interop);
        }

        static short readBufferShort(Object buffer, int byteIndex, ByteOrder order, InteropLibrary interop) {
            try {
                return interop.readBufferShort(buffer, order, byteIndex);
            }
            catch (UnsupportedMessageException e) {
                throw InteropTwoByteIntArray.unsupportedBufferAccess(buffer, e);
            }
            catch (InvalidBufferOffsetException e) {
                throw Errors.createRangeErrorInvalidBufferOffset();
            }
        }

        static void writeBufferShort(Object buffer, int byteIndex, short value2, ByteOrder order, InteropLibrary interop) {
            try {
                interop.writeBufferShort(buffer, order, byteIndex, value2);
            }
            catch (UnsupportedMessageException e) {
                throw Errors.createTypeErrorReadOnlyBuffer();
            }
            catch (InvalidBufferOffsetException e) {
                throw Errors.createRangeErrorInvalidBufferOffset();
            }
        }
    }

    public static class InteropInt16Array
    extends InteropTwoByteIntArray {
        InteropInt16Array(TypedArrayFactory factory, boolean offset) {
            super(factory, offset);
        }
    }

    public static final class DirectInt16Array
    extends TypedIntArray {
        DirectInt16Array(TypedArrayFactory factory, boolean offset) {
            super(factory, offset, (byte)1);
        }

        @Override
        public int getIntImpl(Object buffer, int offset, int index, InteropLibrary interop) {
            return ByteBufferAccess.nativeOrder().getInt16(DirectInt16Array.getDirectByteBuffer(buffer), offset + index * 2);
        }

        @Override
        public void setIntImpl(Object buffer, int offset, int index, int value2, InteropLibrary interop) {
            ByteBufferAccess.nativeOrder().putInt16(DirectInt16Array.getDirectByteBuffer(buffer), offset + index * 2, (short)value2);
        }

        @Override
        public int getBufferElementIntImpl(Object buffer, int index, boolean littleEndian, InteropLibrary interop) {
            return ByteBufferAccess.forOrder(littleEndian).getInt16(DirectInt16Array.getDirectByteBuffer(buffer), index);
        }

        @Override
        public void setBufferElementIntImpl(Object buffer, int index, boolean littleEndian, int value2, InteropLibrary interop) {
            ByteBufferAccess.forOrder(littleEndian).putInt16(DirectInt16Array.getDirectByteBuffer(buffer), index, (short)value2);
        }

        @Override
        public int compareExchangeInt(JSTypedArrayObject typedArrayObject, int index, int expectedValue, int newValue) {
            int byteOffset;
            ByteBuffer byteBuffer = DirectInt16Array.getDirectByteBuffer(DirectInt16Array.getBufferFromTypedArray(typedArrayObject));
            if (DirectInt16Array.isCompareExchangeSupported(byteBuffer, byteOffset = this.getOffset(typedArrayObject) + index * 2)) {
                return (short)ByteBufferAccess.nativeOrder().compareExchangeInt16(byteBuffer, byteOffset, expectedValue, newValue);
            }
            return (short)this.lockedReadModifyWriteShort(typedArrayObject, index, expectedValue, newValue);
        }
    }

    public static final class Int16Array
    extends TypedIntArray {
        Int16Array(TypedArrayFactory factory, boolean offset) {
            super(factory, offset, (byte)0);
        }

        @Override
        public int getIntImpl(Object buffer, int offset, int index, InteropLibrary interop) {
            return ByteArrayAccess.nativeOrder().getInt16(Int16Array.getByteArray(buffer), offset + index * 2);
        }

        @Override
        public void setIntImpl(Object buffer, int offset, int index, int value2, InteropLibrary interop) {
            ByteArrayAccess.nativeOrder().putInt16(Int16Array.getByteArray(buffer), offset + index * 2, value2);
        }

        @Override
        public int getBufferElementIntImpl(Object buffer, int index, boolean littleEndian, InteropLibrary interop) {
            return ByteArrayAccess.forOrder(littleEndian).getInt16(Int16Array.getByteArray(buffer), index);
        }

        @Override
        public void setBufferElementIntImpl(Object buffer, int index, boolean littleEndian, int value2, InteropLibrary interop) {
            ByteArrayAccess.forOrder(littleEndian).putInt16(Int16Array.getByteArray(buffer), index, value2);
        }
    }

    public static final class InteropUint8ClampedArray
    extends AbstractUint8ClampedArray {
        InteropUint8ClampedArray(TypedArrayFactory factory, boolean offset) {
            super(factory, offset, (byte)-1);
        }

        @Override
        public int getIntImpl(Object buffer, int offset, int index, InteropLibrary interop) {
            return InteropInt8Array.readBufferByte(buffer, offset + index * 1, interop) & 0xFF;
        }

        @Override
        public void setIntImpl(Object buffer, int offset, int index, int value2, InteropLibrary interop) {
            InteropInt8Array.writeBufferByte(buffer, offset + index * 1, (byte)InteropUint8ClampedArray.uint8Clamp(value2), interop);
        }

        @Override
        public int getBufferElementIntImpl(Object buffer, int index, boolean littleEndian, InteropLibrary interop) {
            return InteropInt8Array.readBufferByte(buffer, index, interop) & 0xFF;
        }

        @Override
        public void setBufferElementIntImpl(Object buffer, int index, boolean littleEndian, int value2, InteropLibrary interop) {
            InteropInt8Array.writeBufferByte(buffer, index, (byte)InteropUint8ClampedArray.uint8Clamp(value2), interop);
        }
    }

    public static final class DirectUint8ClampedArray
    extends AbstractUint8ClampedArray {
        DirectUint8ClampedArray(TypedArrayFactory factory, boolean offset) {
            super(factory, offset, (byte)1);
        }

        @Override
        public int getIntImpl(Object buffer, int offset, int index, InteropLibrary interop) {
            return DirectUint8ClampedArray.getDirectByteBuffer(buffer).get(offset + index * 1) & 0xFF;
        }

        @Override
        public void setIntImpl(Object buffer, int offset, int index, int value2, InteropLibrary interop) {
            DirectUint8ClampedArray.getDirectByteBuffer(buffer).put(offset + index * 1, (byte)DirectUint8ClampedArray.uint8Clamp(value2));
        }

        @Override
        public int getBufferElementIntImpl(Object buffer, int index, boolean littleEndian, InteropLibrary interop) {
            return DirectUint8ClampedArray.getDirectByteBuffer(buffer).get(index) & 0xFF;
        }

        @Override
        public void setBufferElementIntImpl(Object buffer, int index, boolean littleEndian, int value2, InteropLibrary interop) {
            DirectUint8ClampedArray.getDirectByteBuffer(buffer).put(index, (byte)DirectUint8ClampedArray.uint8Clamp(value2));
        }
    }

    public static final class Uint8ClampedArray
    extends AbstractUint8ClampedArray {
        Uint8ClampedArray(TypedArrayFactory factory, boolean offset) {
            super(factory, offset, (byte)0);
        }

        @Override
        public int getIntImpl(Object buffer, int offset, int index, InteropLibrary interop) {
            return ByteArrayAccess.nativeOrder().getUint8(Uint8ClampedArray.getByteArray(buffer), offset + index * 1);
        }

        @Override
        public void setIntImpl(Object buffer, int offset, int index, int value2, InteropLibrary interop) {
            ByteArrayAccess.nativeOrder().putInt8(Uint8ClampedArray.getByteArray(buffer), offset + index * 1, Uint8ClampedArray.uint8Clamp(value2));
        }

        @Override
        public int getBufferElementIntImpl(Object buffer, int index, boolean littleEndian, InteropLibrary interop) {
            return ByteArrayAccess.forOrder(littleEndian).getUint8(Uint8ClampedArray.getByteArray(buffer), index);
        }

        @Override
        public void setBufferElementIntImpl(Object buffer, int index, boolean littleEndian, int value2, InteropLibrary interop) {
            ByteArrayAccess.forOrder(littleEndian).putInt8(Uint8ClampedArray.getByteArray(buffer), index, Uint8ClampedArray.uint8Clamp(value2));
        }
    }

    public static abstract class AbstractUint8ClampedArray
    extends TypedIntArray {
        private AbstractUint8ClampedArray(TypedArrayFactory factory, boolean offset, byte bufferType) {
            super(factory, offset, bufferType);
        }

        @Override
        public TypedIntArray setElementImpl(JSDynamicObject object, long index, Object value2, boolean strict) {
            if (this.hasElement(object, index)) {
                this.setInt(object, (int)index, AbstractUint8ClampedArray.toInt(JSRuntime.toDouble(value2)), InteropLibrary.getUncached());
            }
            return this;
        }

        protected static int uint8Clamp(int value2) {
            return value2 < 0 ? 0 : (value2 > 255 ? 255 : value2);
        }

        public static int toInt(double value2) {
            return (int)Math.rint(value2);
        }

        @Override
        public final void setBufferElement(Object buffer, int index, boolean littleEndian, Object value2, InteropLibrary interop) {
            this.setBufferElementIntImpl(buffer, index, littleEndian, AbstractUint8ClampedArray.toInt(JSRuntime.toDouble((Number)value2)), interop);
        }
    }

    public static final class InteropUint8Array
    extends InteropOneByteIntArray {
        InteropUint8Array(TypedArrayFactory factory, boolean offset) {
            super(factory, offset);
        }

        @Override
        public int getIntImpl(Object buffer, int offset, int index, InteropLibrary interop) {
            return super.getIntImpl(buffer, offset, index, interop) & 0xFF;
        }

        @Override
        public int getBufferElementIntImpl(Object buffer, int index, boolean littleEndian, InteropLibrary interop) {
            return super.getBufferElementIntImpl(buffer, index, littleEndian, interop) & 0xFF;
        }
    }

    public static final class DirectUint8Array
    extends TypedIntArray {
        DirectUint8Array(TypedArrayFactory factory, boolean offset) {
            super(factory, offset, (byte)1);
        }

        @Override
        public int getIntImpl(Object buffer, int offset, int index, InteropLibrary interop) {
            return DirectUint8Array.getDirectByteBuffer(buffer).get(offset + index * 1) & 0xFF;
        }

        @Override
        public void setIntImpl(Object buffer, int offset, int index, int value2, InteropLibrary interop) {
            DirectUint8Array.getDirectByteBuffer(buffer).put(offset + index * 1, (byte)value2);
        }

        @Override
        public int getBufferElementIntImpl(Object buffer, int index, boolean littleEndian, InteropLibrary interop) {
            return DirectUint8Array.getDirectByteBuffer(buffer).get(index) & 0xFF;
        }

        @Override
        public void setBufferElementIntImpl(Object buffer, int index, boolean littleEndian, int value2, InteropLibrary interop) {
            DirectUint8Array.getDirectByteBuffer(buffer).put(index, (byte)value2);
        }

        @Override
        public int compareExchangeInt(JSTypedArrayObject typedArrayObject, int index, int expectedValue, int newValue) {
            int byteOffset;
            ByteBuffer byteBuffer = DirectUint8Array.getDirectByteBuffer(DirectUint8Array.getBufferFromTypedArray(typedArrayObject));
            if (DirectUint8Array.isCompareExchangeSupported(byteBuffer, byteOffset = this.getOffset(typedArrayObject) + index * 1)) {
                return ByteBufferAccess.nativeOrder().compareExchangeInt8(byteBuffer, byteOffset, expectedValue, newValue) & 0xFF;
            }
            return this.lockedReadModifyWriteByte(typedArrayObject, index, expectedValue, newValue) & 0xFF;
        }
    }

    public static final class Uint8Array
    extends TypedIntArray {
        Uint8Array(TypedArrayFactory factory, boolean offset) {
            super(factory, offset, (byte)0);
        }

        @Override
        public int getIntImpl(Object buffer, int offset, int index, InteropLibrary interop) {
            return ByteArrayAccess.nativeOrder().getUint8(Uint8Array.getByteArray(buffer), offset + index * 1);
        }

        @Override
        public void setIntImpl(Object buffer, int offset, int index, int value2, InteropLibrary interop) {
            ByteArrayAccess.nativeOrder().putInt8(Uint8Array.getByteArray(buffer), offset + index * 1, value2);
        }

        @Override
        public int getBufferElementIntImpl(Object buffer, int index, boolean littleEndian, InteropLibrary interop) {
            return ByteArrayAccess.forOrder(littleEndian).getUint8(Uint8Array.getByteArray(buffer), index);
        }

        @Override
        public void setBufferElementIntImpl(Object buffer, int index, boolean littleEndian, int value2, InteropLibrary interop) {
            ByteArrayAccess.forOrder(littleEndian).putInt8(Uint8Array.getByteArray(buffer), index, value2);
        }
    }

    public static class InteropOneByteIntArray
    extends TypedIntArray {
        InteropOneByteIntArray(TypedArrayFactory factory, boolean offset) {
            super(factory, offset, (byte)-1);
        }

        @Override
        public int getIntImpl(Object buffer, int offset, int index, InteropLibrary interop) {
            return InteropOneByteIntArray.readBufferByte(buffer, offset + index * 1, interop);
        }

        @Override
        public void setIntImpl(Object buffer, int offset, int index, int value2, InteropLibrary interop) {
            InteropOneByteIntArray.writeBufferByte(buffer, offset + index * 1, (byte)value2, interop);
        }

        @Override
        public int getBufferElementIntImpl(Object buffer, int index, boolean littleEndian, InteropLibrary interop) {
            return InteropOneByteIntArray.readBufferByte(buffer, index, interop);
        }

        @Override
        public void setBufferElementIntImpl(Object buffer, int index, boolean littleEndian, int value2, InteropLibrary interop) {
            InteropOneByteIntArray.writeBufferByte(buffer, index, (byte)value2, interop);
        }

        static byte readBufferByte(Object buffer, int byteIndex, InteropLibrary interop) {
            try {
                return interop.readBufferByte(buffer, byteIndex);
            }
            catch (UnsupportedMessageException e) {
                throw InteropOneByteIntArray.unsupportedBufferAccess(buffer, e);
            }
            catch (InvalidBufferOffsetException e) {
                throw Errors.createRangeErrorInvalidBufferOffset();
            }
        }

        static void writeBufferByte(Object buffer, int byteIndex, byte value2, InteropLibrary interop) {
            try {
                interop.writeBufferByte(buffer, byteIndex, value2);
            }
            catch (UnsupportedMessageException e) {
                throw Errors.createTypeErrorReadOnlyBuffer();
            }
            catch (InvalidBufferOffsetException e) {
                throw Errors.createRangeErrorInvalidBufferOffset();
            }
        }
    }

    public static class InteropInt8Array
    extends InteropOneByteIntArray {
        InteropInt8Array(TypedArrayFactory factory, boolean offset) {
            super(factory, offset);
        }
    }

    public static final class DirectInt8Array
    extends TypedIntArray {
        DirectInt8Array(TypedArrayFactory factory, boolean offset) {
            super(factory, offset, (byte)1);
        }

        @Override
        public int getIntImpl(Object buffer, int offset, int index, InteropLibrary interop) {
            return DirectInt8Array.getDirectByteBuffer(buffer).get(offset + index * 1);
        }

        @Override
        public void setIntImpl(Object buffer, int offset, int index, int value2, InteropLibrary interop) {
            DirectInt8Array.getDirectByteBuffer(buffer).put(offset + index * 1, (byte)value2);
        }

        @Override
        public int getBufferElementIntImpl(Object buffer, int index, boolean littleEndian, InteropLibrary interop) {
            return DirectInt8Array.getDirectByteBuffer(buffer).get(index);
        }

        @Override
        public void setBufferElementIntImpl(Object buffer, int index, boolean littleEndian, int value2, InteropLibrary interop) {
            DirectInt8Array.getDirectByteBuffer(buffer).put(index, (byte)value2);
        }

        @Override
        public int compareExchangeInt(JSTypedArrayObject typedArrayObject, int index, int expectedValue, int newValue) {
            int byteOffset;
            ByteBuffer byteBuffer = DirectInt8Array.getDirectByteBuffer(DirectInt8Array.getBufferFromTypedArray(typedArrayObject));
            if (DirectInt8Array.isCompareExchangeSupported(byteBuffer, byteOffset = this.getOffset(typedArrayObject) + index * 1)) {
                return (byte)ByteBufferAccess.nativeOrder().compareExchangeInt8(byteBuffer, byteOffset, expectedValue, newValue);
            }
            return (byte)this.lockedReadModifyWriteByte(typedArrayObject, index, expectedValue, newValue);
        }
    }

    public static final class Int8Array
    extends TypedIntArray {
        Int8Array(TypedArrayFactory factory, boolean offset) {
            super(factory, offset, (byte)0);
        }

        @Override
        public int getIntImpl(Object buffer, int offset, int index, InteropLibrary interop) {
            return ByteArrayAccess.nativeOrder().getInt8(Int8Array.getByteArray(buffer), offset + index * 1);
        }

        @Override
        public void setIntImpl(Object buffer, int offset, int index, int value2, InteropLibrary interop) {
            ByteArrayAccess.nativeOrder().putInt8(Int8Array.getByteArray(buffer), offset + index * 1, value2);
        }

        @Override
        public int getBufferElementIntImpl(Object buffer, int index, boolean littleEndian, InteropLibrary interop) {
            return ByteArrayAccess.forOrder(littleEndian).getInt8(Int8Array.getByteArray(buffer), index);
        }

        @Override
        public void setBufferElementIntImpl(Object buffer, int index, boolean littleEndian, int value2, InteropLibrary interop) {
            ByteArrayAccess.forOrder(littleEndian).putInt8(Int8Array.getByteArray(buffer), index, value2);
        }
    }

    public static abstract class TypedIntArray
    extends TypedArray {
        protected TypedIntArray(TypedArrayFactory factory, boolean offset, byte bufferType) {
            super(factory, offset, bufferType);
        }

        @Override
        public Object getElement(JSDynamicObject object, long index) {
            if (this.hasElement(object, index)) {
                return this.getInt(object, (int)index, InteropLibrary.getUncached());
            }
            return Undefined.instance;
        }

        @Override
        public Object getElementInBounds(JSDynamicObject object, long index) {
            assert (this.hasElement(object, index));
            return this.getInt(object, (int)index, InteropLibrary.getUncached());
        }

        @Override
        public TypedIntArray setElementImpl(JSDynamicObject object, long index, Object value2, boolean strict) {
            if (this.hasElement(object, index)) {
                this.setInt(object, (int)index, JSRuntime.toInt32(value2), InteropLibrary.getUncached());
            }
            return this;
        }

        public final int getInt(JSDynamicObject object, int index, InteropLibrary interop) {
            return this.getIntImpl(TypedIntArray.getBufferFromTypedArray(object), this.getOffset(object), index, interop);
        }

        public final void setInt(JSDynamicObject object, int index, int value2, InteropLibrary interop) {
            this.setIntImpl(TypedIntArray.getBufferFromTypedArray(object), this.getOffset(object), index, value2, interop);
        }

        public abstract int getIntImpl(Object var1, int var2, int var3, InteropLibrary var4);

        public abstract void setIntImpl(Object var1, int var2, int var3, int var4, InteropLibrary var5);

        @Override
        public Object getBufferElement(Object buffer, int index, boolean littleEndian, InteropLibrary interop) {
            return this.getBufferElementIntImpl(buffer, index, littleEndian, interop);
        }

        public abstract int getBufferElementIntImpl(Object var1, int var2, boolean var3, InteropLibrary var4);

        @Override
        public void setBufferElement(Object buffer, int index, boolean littleEndian, Object value2, InteropLibrary interop) {
            this.setBufferElementIntImpl(buffer, index, littleEndian, JSRuntime.toInt32((Number)value2), interop);
        }

        public abstract void setBufferElementIntImpl(Object var1, int var2, boolean var3, int var4, InteropLibrary var5);

        public int compareExchangeInt(JSTypedArrayObject typedArray, int index, int expectedValue, int newValue) {
            throw Errors.shouldNotReachHere();
        }

        static boolean isCompareExchangeSupported(ByteBuffer byteBuffer, int byteOffset) {
            return (byteOffset & 0xFFFFFFFC) <= byteBuffer.limit() - 4;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @CompilerDirectives.TruffleBoundary
        final int lockedReadModifyWriteByte(JSTypedArrayObject typedArrayObject, int index, int expectedValue, int newValue) {
            JSArrayBufferObject arrayBuffer = TypedIntArray.getBufferFromTypedArray(typedArrayObject);
            JSAgentWaiterList waiterList = JSSharedArrayBuffer.getWaiterList(arrayBuffer);
            waiterList.enterAtomicSection();
            try {
                byte read2 = (byte)SharedMemorySync.doVolatileGet(typedArrayObject, index, this);
                if (read2 == (byte)expectedValue) {
                    SharedMemorySync.doVolatilePut(typedArrayObject, index, (byte)newValue, this);
                }
                byte by = read2;
                return by;
            }
            finally {
                waiterList.leaveAtomicSection();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @CompilerDirectives.TruffleBoundary
        final int lockedReadModifyWriteShort(JSTypedArrayObject typedArrayObject, int index, int expectedValue, int newValue) {
            JSArrayBufferObject arrayBuffer = TypedIntArray.getBufferFromTypedArray(typedArrayObject);
            JSAgentWaiterList waiterList = JSSharedArrayBuffer.getWaiterList(arrayBuffer);
            waiterList.enterAtomicSection();
            try {
                short read2 = (short)SharedMemorySync.doVolatileGet(typedArrayObject, index, this);
                if (read2 == (short)expectedValue) {
                    SharedMemorySync.doVolatilePut(typedArrayObject, index, (short)newValue, this);
                }
                short s = read2;
                return s;
            }
            finally {
                waiterList.leaveAtomicSection();
            }
        }
    }
}

