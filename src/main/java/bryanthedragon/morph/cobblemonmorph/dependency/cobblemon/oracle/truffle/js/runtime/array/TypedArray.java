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
import com.oracle.truffle.js.runtime.builtins.JSArrayBuffer;
import com.oracle.truffle.js.runtime.builtins.JSArrayBufferObject;
import com.oracle.truffle.js.runtime.builtins.JSArrayBufferView;
import com.oracle.truffle.js.runtime.builtins.JSSharedArrayBuffer;
import com.oracle.truffle.js.runtime.builtins.JSTypedArrayObject;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.Undefined;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public abstract class TypedArray extends ScriptArray {
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
      assert !JSArrayBuffer.isDetachedBuffer(buffer);

      return JSArrayBuffer.getDirectByteBuffer(buffer);
   }

   protected static byte[] getByteArray(Object buffer) {
      assert !JSArrayBuffer.isDetachedBuffer(buffer);

      return JSArrayBuffer.getByteArray(buffer);
   }

   public static JSArrayBufferObject getBufferFromTypedArray(JSDynamicObject typedArray) {
      return JSArrayBufferView.getArrayBuffer(typedArray);
   }

   public final int getOffset(JSDynamicObject object) {
      return this.offset ? JSArrayBufferView.typedArrayGetOffset(object) : 0;
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
   public ScriptArray removeRangeImpl(JSDynamicObject object, long start, long end) {
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

   public abstract Object getBufferElement(Object buffer, int index, boolean littleEndian, InteropLibrary interop);

   public abstract void setBufferElement(Object buffer, int index, boolean littleEndian, Object value, InteropLibrary interop);

   public static TypedArrayFactory[] factories() {
      return TypedArrayFactory.FACTORIES;
   }

   public static TypedArrayFactory[] factories(JSContext context) {
      return context.getContextOptions().isBigInt() ? TypedArrayFactory.FACTORIES : TypedArrayFactory.getNoBigIntFactories();
   }

   @CompilerDirectives.TruffleBoundary
   protected static JSException unsupportedBufferAccess(Object buffer, UnsupportedMessageException e) {
      return Errors.createTypeErrorInteropException(buffer, e, "buffer access", null);
   }

   public abstract static class AbstractUint32Array extends TypedArray.TypedIntArray {
      private AbstractUint32Array(TypedArrayFactory factory, boolean offset, byte bufferType) {
         super(factory, offset, bufferType);
      }

      @Override
      public Object getElement(JSDynamicObject object, long index) {
         if (this.hasElement(object, index)) {
            int value = this.getInt(object, (int)index, InteropLibrary.getUncached());
            return toUint32(value);
         } else {
            return Undefined.instance;
         }
      }

      protected static Number toUint32(int value) {
         return (Number)(value >= 0 ? value : (double)(value & 4294967295L));
      }

      @Override
      public Object getElementInBounds(JSDynamicObject object, long index) {
         assert this.hasElement(object, index);

         return toUint32(this.getInt(object, (int)index, InteropLibrary.getUncached()));
      }

      @Override
      public Object getBufferElement(Object buffer, int index, boolean littleEndian, InteropLibrary interop) {
         return toUint32(this.getBufferElementIntImpl(buffer, index, littleEndian, interop));
      }
   }

   public abstract static class AbstractUint8ClampedArray extends TypedArray.TypedIntArray {
      private AbstractUint8ClampedArray(TypedArrayFactory factory, boolean offset, byte bufferType) {
         super(factory, offset, bufferType);
      }

      @Override
      public TypedArray.TypedIntArray setElementImpl(JSDynamicObject object, long index, Object value, boolean strict) {
         if (this.hasElement(object, index)) {
            this.setInt(object, (int)index, toInt(JSRuntime.toDouble(value)), InteropLibrary.getUncached());
         }

         return this;
      }

      protected static int uint8Clamp(int value) {
         return value < 0 ? 0 : (value > 255 ? 255 : value);
      }

      public static int toInt(double value) {
         return (int)Math.rint(value);
      }

      @Override
      public final void setBufferElement(Object buffer, int index, boolean littleEndian, Object value, InteropLibrary interop) {
         this.setBufferElementIntImpl(buffer, index, littleEndian, toInt(JSRuntime.toDouble((Number)value)), interop);
      }
   }

   public static final class BigInt64Array extends TypedArray.TypedBigIntArray {
      BigInt64Array(TypedArrayFactory factory, boolean offset) {
         super(factory, offset, (byte)0);
      }

      @Override
      public long getBufferElementLongImpl(Object buffer, int index, boolean littleEndian, InteropLibrary interop) {
         return ByteArrayAccess.forOrder(littleEndian).getInt64(getByteArray(buffer), index);
      }

      @Override
      public void setBufferElementLongImpl(Object buffer, int index, boolean littleEndian, long value, InteropLibrary interop) {
         ByteArrayAccess.forOrder(littleEndian).putInt64(getByteArray(buffer), index, value);
      }

      @Override
      public long getLongImpl(Object buffer, int offset, int index, InteropLibrary interop) {
         return ByteArrayAccess.nativeOrder().getInt64(getByteArray(buffer), offset + index * 8);
      }

      @Override
      public void setLongImpl(Object buffer, int offset, int index, long value, InteropLibrary interop) {
         ByteArrayAccess.nativeOrder().putInt64(getByteArray(buffer), offset + index * 8, value);
      }
   }

   public static final class BigUint64Array extends TypedArray.TypedBigIntArray {
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
         return ByteArrayAccess.forOrder(littleEndian).getInt64(getByteArray(buffer), index);
      }

      @Override
      public void setBufferElementLongImpl(Object buffer, int index, boolean littleEndian, long value, InteropLibrary interop) {
         ByteArrayAccess.forOrder(littleEndian).putInt64(getByteArray(buffer), index, value);
      }

      @Override
      public long getLongImpl(Object buffer, int offset, int index, InteropLibrary interop) {
         return ByteArrayAccess.nativeOrder().getInt64(getByteArray(buffer), offset + index * 8);
      }

      @Override
      public void setLongImpl(Object buffer, int offset, int index, long value, InteropLibrary interop) {
         ByteArrayAccess.nativeOrder().putInt64(getByteArray(buffer), offset + index * 8, value);
      }
   }

   public static final class DirectBigInt64Array extends TypedArray.TypedBigIntArray {
      DirectBigInt64Array(TypedArrayFactory factory, boolean offset) {
         super(factory, offset, (byte)1);
      }

      @Override
      public long getBufferElementLongImpl(Object buffer, int index, boolean littleEndian, InteropLibrary interop) {
         return ByteBufferAccess.forOrder(littleEndian).getInt64(getDirectByteBuffer(buffer), index);
      }

      @Override
      public void setBufferElementLongImpl(Object buffer, int index, boolean littleEndian, long value, InteropLibrary interop) {
         ByteBufferAccess.forOrder(littleEndian).putInt64(getDirectByteBuffer(buffer), index, value);
      }

      @Override
      public long getLongImpl(Object buffer, int offset, int index, InteropLibrary interop) {
         return ByteBufferAccess.nativeOrder().getInt64(getDirectByteBuffer(buffer), offset + index * 8);
      }

      @Override
      public void setLongImpl(Object buffer, int offset, int index, long value, InteropLibrary interop) {
         ByteBufferAccess.nativeOrder().putInt64(getDirectByteBuffer(buffer), offset + index * 8, value);
      }

      @Override
      public long compareExchangeLong(JSTypedArrayObject typedArray, int index, long expectedValue, long newValue) {
         return ByteBufferAccess.nativeOrder()
            .compareExchangeInt64(getDirectByteBuffer(getBufferFromTypedArray(typedArray)), this.getOffset(typedArray) + index * 8, expectedValue, newValue);
      }

      @Override
      public BigInt compareExchangeBigInt(JSTypedArrayObject typedArray, int index, BigInt expectedValue, BigInt newValue) {
         return BigInt.valueOf(this.compareExchangeLong(typedArray, index, expectedValue.longValue(), newValue.longValue()));
      }
   }

   public static final class DirectBigUint64Array extends TypedArray.TypedBigIntArray {
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
         return ByteBufferAccess.forOrder(littleEndian).getInt64(getDirectByteBuffer(buffer), index);
      }

      @Override
      public void setBufferElementLongImpl(Object buffer, int index, boolean littleEndian, long value, InteropLibrary interop) {
         ByteBufferAccess.forOrder(littleEndian).putInt64(getDirectByteBuffer(buffer), index, value);
      }

      @Override
      public long getLongImpl(Object buffer, int offset, int index, InteropLibrary interop) {
         return ByteBufferAccess.nativeOrder().getInt64(getDirectByteBuffer(buffer), offset + index * 8);
      }

      @Override
      public void setLongImpl(Object buffer, int offset, int index, long value, InteropLibrary interop) {
         ByteBufferAccess.nativeOrder().putInt64(getDirectByteBuffer(buffer), offset + index * 8, value);
      }

      @Override
      public long compareExchangeLong(JSTypedArrayObject typedArray, int index, long expectedValue, long newValue) {
         return ByteBufferAccess.nativeOrder()
            .compareExchangeInt64(getDirectByteBuffer(getBufferFromTypedArray(typedArray)), this.getOffset(typedArray) + index * 8, expectedValue, newValue);
      }

      @Override
      public BigInt compareExchangeBigInt(JSTypedArrayObject typedArray, int index, BigInt expectedValue, BigInt newValue) {
         return BigInt.valueOfUnsigned(this.compareExchangeLong(typedArray, index, expectedValue.longValue(), newValue.longValue()));
      }
   }

   public static final class DirectFloat32Array extends TypedArray.TypedFloatArray {
      DirectFloat32Array(TypedArrayFactory factory, boolean offset) {
         super(factory, offset, (byte)1);
      }

      @Override
      public double getDoubleImpl(Object buffer, int offset, int index, InteropLibrary interop) {
         return ByteBufferAccess.nativeOrder().getFloat(getDirectByteBuffer(buffer), offset + index * 4);
      }

      @Override
      public void setDoubleImpl(Object buffer, int offset, int index, double value, InteropLibrary interop) {
         ByteBufferAccess.nativeOrder().putFloat(getDirectByteBuffer(buffer), offset + index * 4, (float)value);
      }

      public Number getBufferElement(Object buffer, int index, boolean littleEndian, InteropLibrary interop) {
         return (double)ByteBufferAccess.forOrder(littleEndian).getFloat(getDirectByteBuffer(buffer), index);
      }

      @Override
      public void setBufferElement(Object buffer, int index, boolean littleEndian, Object value, InteropLibrary interop) {
         ByteBufferAccess.forOrder(littleEndian).putFloat(getDirectByteBuffer(buffer), index, JSRuntime.floatValue((Number)value));
      }
   }

   public static final class DirectFloat64Array extends TypedArray.TypedFloatArray {
      DirectFloat64Array(TypedArrayFactory factory, boolean offset) {
         super(factory, offset, (byte)1);
      }

      @Override
      public double getDoubleImpl(Object buffer, int offset, int index, InteropLibrary interop) {
         return ByteBufferAccess.nativeOrder().getDouble(getDirectByteBuffer(buffer), offset + index * 8);
      }

      @Override
      public void setDoubleImpl(Object buffer, int offset, int index, double value, InteropLibrary interop) {
         ByteBufferAccess.nativeOrder().putDouble(getDirectByteBuffer(buffer), offset + index * 8, value);
      }

      public Number getBufferElement(Object buffer, int index, boolean littleEndian, InteropLibrary interop) {
         return ByteBufferAccess.forOrder(littleEndian).getDouble(getDirectByteBuffer(buffer), index);
      }

      @Override
      public void setBufferElement(Object buffer, int index, boolean littleEndian, Object value, InteropLibrary interop) {
         ByteBufferAccess.forOrder(littleEndian).putDouble(getDirectByteBuffer(buffer), index, JSRuntime.doubleValue((Number)value));
      }
   }

   public static final class DirectInt16Array extends TypedArray.TypedIntArray {
      DirectInt16Array(TypedArrayFactory factory, boolean offset) {
         super(factory, offset, (byte)1);
      }

      @Override
      public int getIntImpl(Object buffer, int offset, int index, InteropLibrary interop) {
         return ByteBufferAccess.nativeOrder().getInt16(getDirectByteBuffer(buffer), offset + index * 2);
      }

      @Override
      public void setIntImpl(Object buffer, int offset, int index, int value, InteropLibrary interop) {
         ByteBufferAccess.nativeOrder().putInt16(getDirectByteBuffer(buffer), offset + index * 2, (short)value);
      }

      @Override
      public int getBufferElementIntImpl(Object buffer, int index, boolean littleEndian, InteropLibrary interop) {
         return ByteBufferAccess.forOrder(littleEndian).getInt16(getDirectByteBuffer(buffer), index);
      }

      @Override
      public void setBufferElementIntImpl(Object buffer, int index, boolean littleEndian, int value, InteropLibrary interop) {
         ByteBufferAccess.forOrder(littleEndian).putInt16(getDirectByteBuffer(buffer), index, (short)value);
      }

      @Override
      public int compareExchangeInt(JSTypedArrayObject typedArrayObject, int index, int expectedValue, int newValue) {
         ByteBuffer byteBuffer = getDirectByteBuffer(getBufferFromTypedArray(typedArrayObject));
         int byteOffset = this.getOffset(typedArrayObject) + index * 2;
         return isCompareExchangeSupported(byteBuffer, byteOffset)
            ? (short)ByteBufferAccess.nativeOrder().compareExchangeInt16(byteBuffer, byteOffset, expectedValue, newValue)
            : (short)this.lockedReadModifyWriteShort(typedArrayObject, index, expectedValue, newValue);
      }
   }

   public static final class DirectInt32Array extends TypedArray.TypedIntArray {
      DirectInt32Array(TypedArrayFactory factory, boolean offset) {
         super(factory, offset, (byte)1);
      }

      @Override
      public int getIntImpl(Object buffer, int offset, int index, InteropLibrary interop) {
         return ByteBufferAccess.nativeOrder().getInt32(getDirectByteBuffer(buffer), offset + index * 4);
      }

      @Override
      public void setIntImpl(Object buffer, int offset, int index, int value, InteropLibrary interop) {
         ByteBufferAccess.nativeOrder().putInt32(getDirectByteBuffer(buffer), offset + index * 4, value);
      }

      @Override
      public int getBufferElementIntImpl(Object buffer, int index, boolean littleEndian, InteropLibrary interop) {
         return ByteBufferAccess.forOrder(littleEndian).getInt32(getDirectByteBuffer(buffer), index);
      }

      @Override
      public void setBufferElementIntImpl(Object buffer, int index, boolean littleEndian, int value, InteropLibrary interop) {
         ByteBufferAccess.forOrder(littleEndian).putInt32(getDirectByteBuffer(buffer), index, value);
      }

      @Override
      public int compareExchangeInt(JSTypedArrayObject typedArray, int index, int expectedValue, int newValue) {
         ByteBuffer byteBuffer = getDirectByteBuffer(getBufferFromTypedArray(typedArray));
         int bufferOffset = this.getOffset(typedArray) + index * 4;
         return ByteBufferAccess.nativeOrder().compareExchangeInt32(byteBuffer, bufferOffset, expectedValue, newValue);
      }
   }

   public static final class DirectInt8Array extends TypedArray.TypedIntArray {
      DirectInt8Array(TypedArrayFactory factory, boolean offset) {
         super(factory, offset, (byte)1);
      }

      @Override
      public int getIntImpl(Object buffer, int offset, int index, InteropLibrary interop) {
         return getDirectByteBuffer(buffer).get(offset + index * 1);
      }

      @Override
      public void setIntImpl(Object buffer, int offset, int index, int value, InteropLibrary interop) {
         getDirectByteBuffer(buffer).put(offset + index * 1, (byte)value);
      }

      @Override
      public int getBufferElementIntImpl(Object buffer, int index, boolean littleEndian, InteropLibrary interop) {
         return getDirectByteBuffer(buffer).get(index);
      }

      @Override
      public void setBufferElementIntImpl(Object buffer, int index, boolean littleEndian, int value, InteropLibrary interop) {
         getDirectByteBuffer(buffer).put(index, (byte)value);
      }

      @Override
      public int compareExchangeInt(JSTypedArrayObject typedArrayObject, int index, int expectedValue, int newValue) {
         ByteBuffer byteBuffer = getDirectByteBuffer(getBufferFromTypedArray(typedArrayObject));
         int byteOffset = this.getOffset(typedArrayObject) + index * 1;
         return isCompareExchangeSupported(byteBuffer, byteOffset)
            ? (byte)ByteBufferAccess.nativeOrder().compareExchangeInt8(byteBuffer, byteOffset, expectedValue, newValue)
            : (byte)this.lockedReadModifyWriteByte(typedArrayObject, index, expectedValue, newValue);
      }
   }

   public static final class DirectUint16Array extends TypedArray.TypedIntArray {
      DirectUint16Array(TypedArrayFactory factory, boolean offset) {
         super(factory, offset, (byte)1);
      }

      @Override
      public int getIntImpl(Object buffer, int offset, int index, InteropLibrary interop) {
         return ByteBufferAccess.nativeOrder().getUint16(getDirectByteBuffer(buffer), offset + index * 2);
      }

      @Override
      public void setIntImpl(Object buffer, int offset, int index, int value, InteropLibrary interop) {
         ByteBufferAccess.nativeOrder().putInt16(getDirectByteBuffer(buffer), offset + index * 2, (char)value);
      }

      @Override
      public int getBufferElementIntImpl(Object buffer, int index, boolean littleEndian, InteropLibrary interop) {
         return ByteBufferAccess.forOrder(littleEndian).getUint16(getDirectByteBuffer(buffer), index);
      }

      @Override
      public void setBufferElementIntImpl(Object buffer, int index, boolean littleEndian, int value, InteropLibrary interop) {
         ByteBufferAccess.forOrder(littleEndian).putInt16(getDirectByteBuffer(buffer), index, (char)value);
      }

      @Override
      public int compareExchangeInt(JSTypedArrayObject typedArrayObject, int index, int expectedValue, int newValue) {
         ByteBuffer byteBuffer = getDirectByteBuffer(getBufferFromTypedArray(typedArrayObject));
         int byteOffset = this.getOffset(typedArrayObject) + index * 2;
         return isCompareExchangeSupported(byteBuffer, byteOffset)
            ? ByteBufferAccess.nativeOrder().compareExchangeInt16(byteBuffer, byteOffset, expectedValue, newValue) & 65535
            : this.lockedReadModifyWriteShort(typedArrayObject, index, expectedValue, newValue) & 65535;
      }
   }

   public static final class DirectUint32Array extends TypedArray.AbstractUint32Array {
      DirectUint32Array(TypedArrayFactory factory, boolean offset) {
         super(factory, offset, (byte)1);
      }

      @Override
      public int getIntImpl(Object buffer, int offset, int index, InteropLibrary interop) {
         return ByteBufferAccess.nativeOrder().getInt32(getDirectByteBuffer(buffer), offset + index * 4);
      }

      @Override
      public void setIntImpl(Object buffer, int offset, int index, int value, InteropLibrary interop) {
         ByteBufferAccess.nativeOrder().putInt32(getDirectByteBuffer(buffer), offset + index * 4, value);
      }

      @Override
      public int getBufferElementIntImpl(Object buffer, int index, boolean littleEndian, InteropLibrary interop) {
         return ByteBufferAccess.forOrder(littleEndian).getInt32(getDirectByteBuffer(buffer), index);
      }

      @Override
      public void setBufferElementIntImpl(Object buffer, int index, boolean littleEndian, int value, InteropLibrary interop) {
         ByteBufferAccess.forOrder(littleEndian).putInt32(getDirectByteBuffer(buffer), index, value);
      }

      @Override
      public int compareExchangeInt(JSTypedArrayObject typedArray, int index, int expectedValue, int newValue) {
         ByteBuffer byteBuffer = getDirectByteBuffer(getBufferFromTypedArray(typedArray));
         int bufferOffset = this.getOffset(typedArray) + index * 4;
         return ByteBufferAccess.nativeOrder().compareExchangeInt32(byteBuffer, bufferOffset, expectedValue, newValue);
      }
   }

   public static final class DirectUint8Array extends TypedArray.TypedIntArray {
      DirectUint8Array(TypedArrayFactory factory, boolean offset) {
         super(factory, offset, (byte)1);
      }

      @Override
      public int getIntImpl(Object buffer, int offset, int index, InteropLibrary interop) {
         return getDirectByteBuffer(buffer).get(offset + index * 1) & 0xFF;
      }

      @Override
      public void setIntImpl(Object buffer, int offset, int index, int value, InteropLibrary interop) {
         getDirectByteBuffer(buffer).put(offset + index * 1, (byte)value);
      }

      @Override
      public int getBufferElementIntImpl(Object buffer, int index, boolean littleEndian, InteropLibrary interop) {
         return getDirectByteBuffer(buffer).get(index) & 0xFF;
      }

      @Override
      public void setBufferElementIntImpl(Object buffer, int index, boolean littleEndian, int value, InteropLibrary interop) {
         getDirectByteBuffer(buffer).put(index, (byte)value);
      }

      @Override
      public int compareExchangeInt(JSTypedArrayObject typedArrayObject, int index, int expectedValue, int newValue) {
         ByteBuffer byteBuffer = getDirectByteBuffer(getBufferFromTypedArray(typedArrayObject));
         int byteOffset = this.getOffset(typedArrayObject) + index * 1;
         return isCompareExchangeSupported(byteBuffer, byteOffset)
            ? ByteBufferAccess.nativeOrder().compareExchangeInt8(byteBuffer, byteOffset, expectedValue, newValue) & 0xFF
            : this.lockedReadModifyWriteByte(typedArrayObject, index, expectedValue, newValue) & 0xFF;
      }
   }

   public static final class DirectUint8ClampedArray extends TypedArray.AbstractUint8ClampedArray {
      DirectUint8ClampedArray(TypedArrayFactory factory, boolean offset) {
         super(factory, offset, (byte)1);
      }

      @Override
      public int getIntImpl(Object buffer, int offset, int index, InteropLibrary interop) {
         return getDirectByteBuffer(buffer).get(offset + index * 1) & 0xFF;
      }

      @Override
      public void setIntImpl(Object buffer, int offset, int index, int value, InteropLibrary interop) {
         getDirectByteBuffer(buffer).put(offset + index * 1, (byte)uint8Clamp(value));
      }

      @Override
      public int getBufferElementIntImpl(Object buffer, int index, boolean littleEndian, InteropLibrary interop) {
         return getDirectByteBuffer(buffer).get(index) & 0xFF;
      }

      @Override
      public void setBufferElementIntImpl(Object buffer, int index, boolean littleEndian, int value, InteropLibrary interop) {
         getDirectByteBuffer(buffer).put(index, (byte)uint8Clamp(value));
      }
   }

   public static final class Float32Array extends TypedArray.TypedFloatArray {
      Float32Array(TypedArrayFactory factory, boolean offset) {
         super(factory, offset, (byte)0);
      }

      @Override
      public double getDoubleImpl(Object buffer, int offset, int index, InteropLibrary interop) {
         return ByteArrayAccess.nativeOrder().getFloat(getByteArray(buffer), offset + index * 4);
      }

      @Override
      public void setDoubleImpl(Object buffer, int offset, int index, double value, InteropLibrary interop) {
         ByteArrayAccess.nativeOrder().putFloat(getByteArray(buffer), offset + index * 4, (float)value);
      }

      public Number getBufferElement(Object buffer, int index, boolean littleEndian, InteropLibrary interop) {
         return (double)ByteArrayAccess.forOrder(littleEndian).getFloat(getByteArray(buffer), index);
      }

      @Override
      public void setBufferElement(Object buffer, int index, boolean littleEndian, Object value, InteropLibrary interop) {
         ByteArrayAccess.forOrder(littleEndian).putFloat(getByteArray(buffer), index, JSRuntime.floatValue((Number)value));
      }
   }

   public static final class Float64Array extends TypedArray.TypedFloatArray {
      Float64Array(TypedArrayFactory factory, boolean offset) {
         super(factory, offset, (byte)0);
      }

      @Override
      public double getDoubleImpl(Object buffer, int offset, int index, InteropLibrary interop) {
         return ByteArrayAccess.nativeOrder().getDouble(getByteArray(buffer), offset + index * 8);
      }

      @Override
      public void setDoubleImpl(Object buffer, int offset, int index, double value, InteropLibrary interop) {
         ByteArrayAccess.nativeOrder().putDouble(getByteArray(buffer), offset + index * 8, value);
      }

      public Number getBufferElement(Object buffer, int index, boolean littleEndian, InteropLibrary interop) {
         return ByteArrayAccess.forOrder(littleEndian).getDouble(getByteArray(buffer), index);
      }

      @Override
      public void setBufferElement(Object buffer, int index, boolean littleEndian, Object value, InteropLibrary interop) {
         ByteArrayAccess.forOrder(littleEndian).putDouble(getByteArray(buffer), index, JSRuntime.doubleValue((Number)value));
      }
   }

   public static final class Int16Array extends TypedArray.TypedIntArray {
      Int16Array(TypedArrayFactory factory, boolean offset) {
         super(factory, offset, (byte)0);
      }

      @Override
      public int getIntImpl(Object buffer, int offset, int index, InteropLibrary interop) {
         return ByteArrayAccess.nativeOrder().getInt16(getByteArray(buffer), offset + index * 2);
      }

      @Override
      public void setIntImpl(Object buffer, int offset, int index, int value, InteropLibrary interop) {
         ByteArrayAccess.nativeOrder().putInt16(getByteArray(buffer), offset + index * 2, value);
      }

      @Override
      public int getBufferElementIntImpl(Object buffer, int index, boolean littleEndian, InteropLibrary interop) {
         return ByteArrayAccess.forOrder(littleEndian).getInt16(getByteArray(buffer), index);
      }

      @Override
      public void setBufferElementIntImpl(Object buffer, int index, boolean littleEndian, int value, InteropLibrary interop) {
         ByteArrayAccess.forOrder(littleEndian).putInt16(getByteArray(buffer), index, value);
      }
   }

   public static final class Int32Array extends TypedArray.TypedIntArray {
      Int32Array(TypedArrayFactory factory, boolean offset) {
         super(factory, offset, (byte)0);
      }

      @Override
      public int getIntImpl(Object buffer, int offset, int index, InteropLibrary interop) {
         return ByteArrayAccess.nativeOrder().getInt32(getByteArray(buffer), offset + index * 4);
      }

      @Override
      public void setIntImpl(Object buffer, int offset, int index, int value, InteropLibrary interop) {
         ByteArrayAccess.nativeOrder().putInt32(getByteArray(buffer), offset + index * 4, value);
      }

      @Override
      public int getBufferElementIntImpl(Object buffer, int index, boolean littleEndian, InteropLibrary interop) {
         return ByteArrayAccess.forOrder(littleEndian).getInt32(getByteArray(buffer), index);
      }

      @Override
      public void setBufferElementIntImpl(Object buffer, int index, boolean littleEndian, int value, InteropLibrary interop) {
         ByteArrayAccess.forOrder(littleEndian).putInt32(getByteArray(buffer), index, value);
      }
   }

   public static final class Int8Array extends TypedArray.TypedIntArray {
      Int8Array(TypedArrayFactory factory, boolean offset) {
         super(factory, offset, (byte)0);
      }

      @Override
      public int getIntImpl(Object buffer, int offset, int index, InteropLibrary interop) {
         return ByteArrayAccess.nativeOrder().getInt8(getByteArray(buffer), offset + index * 1);
      }

      @Override
      public void setIntImpl(Object buffer, int offset, int index, int value, InteropLibrary interop) {
         ByteArrayAccess.nativeOrder().putInt8(getByteArray(buffer), offset + index * 1, value);
      }

      @Override
      public int getBufferElementIntImpl(Object buffer, int index, boolean littleEndian, InteropLibrary interop) {
         return ByteArrayAccess.forOrder(littleEndian).getInt8(getByteArray(buffer), index);
      }

      @Override
      public void setBufferElementIntImpl(Object buffer, int index, boolean littleEndian, int value, InteropLibrary interop) {
         ByteArrayAccess.forOrder(littleEndian).putInt8(getByteArray(buffer), index, value);
      }
   }

   public static class InteropBigInt64Array extends TypedArray.InteropBigIntArray {
      InteropBigInt64Array(TypedArrayFactory factory, boolean offset) {
         super(factory, offset);
      }
   }

   public static class InteropBigIntArray extends TypedArray.TypedBigIntArray {
      InteropBigIntArray(TypedArrayFactory factory, boolean offset) {
         super(factory, offset, (byte)-1);
      }

      @Override
      public long getLongImpl(Object buffer, int offset, int index, InteropLibrary interop) {
         return readBufferLong(buffer, offset + index * 8, ByteOrder.nativeOrder(), interop);
      }

      @Override
      public void setLongImpl(Object buffer, int offset, int index, long value, InteropLibrary interop) {
         writeBufferLong(buffer, offset + index * 8, value, ByteOrder.nativeOrder(), interop);
      }

      @Override
      public long getBufferElementLongImpl(Object buffer, int index, boolean littleEndian, InteropLibrary interop) {
         return readBufferLong(buffer, index, littleEndian ? ByteOrder.LITTLE_ENDIAN : ByteOrder.BIG_ENDIAN, interop);
      }

      @Override
      public void setBufferElementLongImpl(Object buffer, int index, boolean littleEndian, long value, InteropLibrary interop) {
         writeBufferLong(buffer, index, value, littleEndian ? ByteOrder.LITTLE_ENDIAN : ByteOrder.BIG_ENDIAN, interop);
      }

      static long readBufferLong(Object buffer, int byteIndex, ByteOrder order, InteropLibrary interop) {
         try {
            return interop.readBufferLong(buffer, order, byteIndex);
         } catch (UnsupportedMessageException var5) {
            throw unsupportedBufferAccess(buffer, var5);
         } catch (InvalidBufferOffsetException var6) {
            throw Errors.createRangeErrorInvalidBufferOffset();
         }
      }

      static void writeBufferLong(Object buffer, int byteIndex, long value, ByteOrder order, InteropLibrary interop) {
         try {
            interop.writeBufferLong(buffer, order, byteIndex, value);
         } catch (UnsupportedMessageException var7) {
            throw Errors.createTypeErrorReadOnlyBuffer();
         } catch (InvalidBufferOffsetException var8) {
            throw Errors.createRangeErrorInvalidBufferOffset();
         }
      }
   }

   public static final class InteropBigUint64Array extends TypedArray.InteropBigIntArray {
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

   public static final class InteropFloat32Array extends TypedArray.TypedFloatArray {
      InteropFloat32Array(TypedArrayFactory factory, boolean offset) {
         super(factory, offset, (byte)-1);
      }

      @Override
      public double getDoubleImpl(Object buffer, int offset, int index, InteropLibrary interop) {
         return readBufferFloat(buffer, offset + index * 4, ByteOrder.nativeOrder(), interop);
      }

      @Override
      public void setDoubleImpl(Object buffer, int offset, int index, double value, InteropLibrary interop) {
         writeBufferFloat(buffer, offset + index * 4, (float)value, ByteOrder.nativeOrder(), interop);
      }

      public Number getBufferElement(Object buffer, int index, boolean littleEndian, InteropLibrary interop) {
         return (double)readBufferFloat(buffer, index, littleEndian ? ByteOrder.LITTLE_ENDIAN : ByteOrder.BIG_ENDIAN, interop);
      }

      @Override
      public void setBufferElement(Object buffer, int index, boolean littleEndian, Object value, InteropLibrary interop) {
         writeBufferFloat(buffer, index, JSRuntime.floatValue((Number)value), littleEndian ? ByteOrder.LITTLE_ENDIAN : ByteOrder.BIG_ENDIAN, interop);
      }

      static float readBufferFloat(Object buffer, int byteIndex, ByteOrder order, InteropLibrary interop) {
         try {
            return interop.readBufferFloat(buffer, order, byteIndex);
         } catch (UnsupportedMessageException var5) {
            throw unsupportedBufferAccess(buffer, var5);
         } catch (InvalidBufferOffsetException var6) {
            throw Errors.createRangeErrorInvalidBufferOffset();
         }
      }

      static void writeBufferFloat(Object buffer, int byteIndex, float value, ByteOrder order, InteropLibrary interop) {
         try {
            interop.writeBufferFloat(buffer, order, byteIndex, value);
         } catch (UnsupportedMessageException var6) {
            throw Errors.createTypeErrorReadOnlyBuffer();
         } catch (InvalidBufferOffsetException var7) {
            throw Errors.createRangeErrorInvalidBufferOffset();
         }
      }
   }

   public static final class InteropFloat64Array extends TypedArray.TypedFloatArray {
      InteropFloat64Array(TypedArrayFactory factory, boolean offset) {
         super(factory, offset, (byte)-1);
      }

      @Override
      public double getDoubleImpl(Object buffer, int offset, int index, InteropLibrary interop) {
         return readBufferDouble(buffer, offset + index * 8, ByteOrder.nativeOrder(), interop);
      }

      @Override
      public void setDoubleImpl(Object buffer, int offset, int index, double value, InteropLibrary interop) {
         writeBufferDouble(buffer, offset + index * 8, (float)value, ByteOrder.nativeOrder(), interop);
      }

      public Number getBufferElement(Object buffer, int index, boolean littleEndian, InteropLibrary interop) {
         return readBufferDouble(buffer, index, littleEndian ? ByteOrder.LITTLE_ENDIAN : ByteOrder.BIG_ENDIAN, interop);
      }

      @Override
      public void setBufferElement(Object buffer, int index, boolean littleEndian, Object value, InteropLibrary interop) {
         writeBufferDouble(buffer, index, JSRuntime.doubleValue((Number)value), littleEndian ? ByteOrder.LITTLE_ENDIAN : ByteOrder.BIG_ENDIAN, interop);
      }

      static double readBufferDouble(Object buffer, int byteIndex, ByteOrder order, InteropLibrary interop) {
         try {
            return interop.readBufferDouble(buffer, order, byteIndex);
         } catch (UnsupportedMessageException var5) {
            throw unsupportedBufferAccess(buffer, var5);
         } catch (InvalidBufferOffsetException var6) {
            throw Errors.createRangeErrorInvalidBufferOffset();
         }
      }

      static void writeBufferDouble(Object buffer, int byteIndex, double value, ByteOrder order, InteropLibrary interop) {
         try {
            interop.writeBufferDouble(buffer, order, byteIndex, value);
         } catch (UnsupportedMessageException var7) {
            throw Errors.createTypeErrorReadOnlyBuffer();
         } catch (InvalidBufferOffsetException var8) {
            throw Errors.createRangeErrorInvalidBufferOffset();
         }
      }
   }

   public static class InteropInt16Array extends TypedArray.InteropTwoByteIntArray {
      InteropInt16Array(TypedArrayFactory factory, boolean offset) {
         super(factory, offset);
      }
   }

   public static final class InteropInt32Array extends TypedArray.TypedIntArray {
      InteropInt32Array(TypedArrayFactory factory, boolean offset) {
         super(factory, offset, (byte)-1);
      }

      @Override
      public int getIntImpl(Object buffer, int offset, int index, InteropLibrary interop) {
         return readBufferInt(buffer, offset + index * 4, ByteOrder.nativeOrder(), interop);
      }

      @Override
      public void setIntImpl(Object buffer, int offset, int index, int value, InteropLibrary interop) {
         writeBufferInt(buffer, offset + index * 4, value, ByteOrder.nativeOrder(), interop);
      }

      @Override
      public int getBufferElementIntImpl(Object buffer, int index, boolean littleEndian, InteropLibrary interop) {
         return readBufferInt(buffer, index, littleEndian ? ByteOrder.LITTLE_ENDIAN : ByteOrder.BIG_ENDIAN, interop);
      }

      @Override
      public void setBufferElementIntImpl(Object buffer, int index, boolean littleEndian, int value, InteropLibrary interop) {
         writeBufferInt(buffer, index, value, littleEndian ? ByteOrder.LITTLE_ENDIAN : ByteOrder.BIG_ENDIAN, interop);
      }

      static int readBufferInt(Object buffer, int byteIndex, ByteOrder order, InteropLibrary interop) {
         try {
            return interop.readBufferInt(buffer, order, byteIndex);
         } catch (UnsupportedMessageException var5) {
            throw unsupportedBufferAccess(buffer, var5);
         } catch (InvalidBufferOffsetException var6) {
            throw Errors.createRangeErrorInvalidBufferOffset();
         }
      }

      static void writeBufferInt(Object buffer, int byteIndex, int value, ByteOrder order, InteropLibrary interop) {
         try {
            interop.writeBufferInt(buffer, order, byteIndex, value);
         } catch (UnsupportedMessageException var6) {
            throw Errors.createTypeErrorReadOnlyBuffer();
         } catch (InvalidBufferOffsetException var7) {
            throw Errors.createRangeErrorInvalidBufferOffset();
         }
      }
   }

   public static class InteropInt8Array extends TypedArray.InteropOneByteIntArray {
      InteropInt8Array(TypedArrayFactory factory, boolean offset) {
         super(factory, offset);
      }
   }

   public static class InteropOneByteIntArray extends TypedArray.TypedIntArray {
      InteropOneByteIntArray(TypedArrayFactory factory, boolean offset) {
         super(factory, offset, (byte)-1);
      }

      @Override
      public int getIntImpl(Object buffer, int offset, int index, InteropLibrary interop) {
         return readBufferByte(buffer, offset + index * 1, interop);
      }

      @Override
      public void setIntImpl(Object buffer, int offset, int index, int value, InteropLibrary interop) {
         writeBufferByte(buffer, offset + index * 1, (byte)value, interop);
      }

      @Override
      public int getBufferElementIntImpl(Object buffer, int index, boolean littleEndian, InteropLibrary interop) {
         return readBufferByte(buffer, index, interop);
      }

      @Override
      public void setBufferElementIntImpl(Object buffer, int index, boolean littleEndian, int value, InteropLibrary interop) {
         writeBufferByte(buffer, index, (byte)value, interop);
      }

      static byte readBufferByte(Object buffer, int byteIndex, InteropLibrary interop) {
         try {
            return interop.readBufferByte(buffer, byteIndex);
         } catch (UnsupportedMessageException var4) {
            throw unsupportedBufferAccess(buffer, var4);
         } catch (InvalidBufferOffsetException var5) {
            throw Errors.createRangeErrorInvalidBufferOffset();
         }
      }

      static void writeBufferByte(Object buffer, int byteIndex, byte value, InteropLibrary interop) {
         try {
            interop.writeBufferByte(buffer, byteIndex, value);
         } catch (UnsupportedMessageException var5) {
            throw Errors.createTypeErrorReadOnlyBuffer();
         } catch (InvalidBufferOffsetException var6) {
            throw Errors.createRangeErrorInvalidBufferOffset();
         }
      }
   }

   public static class InteropTwoByteIntArray extends TypedArray.TypedIntArray {
      InteropTwoByteIntArray(TypedArrayFactory factory, boolean offset) {
         super(factory, offset, (byte)-1);
      }

      @Override
      public int getIntImpl(Object buffer, int offset, int index, InteropLibrary interop) {
         return readBufferShort(buffer, offset + index * 2, ByteOrder.nativeOrder(), interop);
      }

      @Override
      public void setIntImpl(Object buffer, int offset, int index, int value, InteropLibrary interop) {
         writeBufferShort(buffer, offset + index * 2, (short)value, ByteOrder.nativeOrder(), interop);
      }

      @Override
      public int getBufferElementIntImpl(Object buffer, int index, boolean littleEndian, InteropLibrary interop) {
         return readBufferShort(buffer, index, littleEndian ? ByteOrder.LITTLE_ENDIAN : ByteOrder.BIG_ENDIAN, interop);
      }

      @Override
      public void setBufferElementIntImpl(Object buffer, int index, boolean littleEndian, int value, InteropLibrary interop) {
         writeBufferShort(buffer, index, (short)value, littleEndian ? ByteOrder.LITTLE_ENDIAN : ByteOrder.BIG_ENDIAN, interop);
      }

      static short readBufferShort(Object buffer, int byteIndex, ByteOrder order, InteropLibrary interop) {
         try {
            return interop.readBufferShort(buffer, order, byteIndex);
         } catch (UnsupportedMessageException var5) {
            throw unsupportedBufferAccess(buffer, var5);
         } catch (InvalidBufferOffsetException var6) {
            throw Errors.createRangeErrorInvalidBufferOffset();
         }
      }

      static void writeBufferShort(Object buffer, int byteIndex, short value, ByteOrder order, InteropLibrary interop) {
         try {
            interop.writeBufferShort(buffer, order, byteIndex, value);
         } catch (UnsupportedMessageException var6) {
            throw Errors.createTypeErrorReadOnlyBuffer();
         } catch (InvalidBufferOffsetException var7) {
            throw Errors.createRangeErrorInvalidBufferOffset();
         }
      }
   }

   public static final class InteropUint16Array extends TypedArray.InteropTwoByteIntArray {
      InteropUint16Array(TypedArrayFactory factory, boolean offset) {
         super(factory, offset);
      }

      @Override
      public int getIntImpl(Object buffer, int offset, int index, InteropLibrary interop) {
         return super.getIntImpl(buffer, offset, index, interop) & 65535;
      }

      @Override
      public int getBufferElementIntImpl(Object buffer, int index, boolean littleEndian, InteropLibrary interop) {
         return super.getBufferElementIntImpl(buffer, index, littleEndian, interop) & 65535;
      }
   }

   public static final class InteropUint32Array extends TypedArray.AbstractUint32Array {
      InteropUint32Array(TypedArrayFactory factory, boolean offset) {
         super(factory, offset, (byte)-1);
      }

      @Override
      public int getIntImpl(Object buffer, int offset, int index, InteropLibrary interop) {
         return TypedArray.InteropInt32Array.readBufferInt(buffer, offset + index * 4, ByteOrder.nativeOrder(), interop);
      }

      @Override
      public void setIntImpl(Object buffer, int offset, int index, int value, InteropLibrary interop) {
         TypedArray.InteropInt32Array.writeBufferInt(buffer, offset + index * 4, value, ByteOrder.nativeOrder(), interop);
      }

      @Override
      public int getBufferElementIntImpl(Object buffer, int index, boolean littleEndian, InteropLibrary interop) {
         return TypedArray.InteropInt32Array.readBufferInt(buffer, index, littleEndian ? ByteOrder.LITTLE_ENDIAN : ByteOrder.BIG_ENDIAN, interop);
      }

      @Override
      public void setBufferElementIntImpl(Object buffer, int index, boolean littleEndian, int value, InteropLibrary interop) {
         TypedArray.InteropInt32Array.writeBufferInt(buffer, index, value, littleEndian ? ByteOrder.LITTLE_ENDIAN : ByteOrder.BIG_ENDIAN, interop);
      }
   }

   public static final class InteropUint8Array extends TypedArray.InteropOneByteIntArray {
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

   public static final class InteropUint8ClampedArray extends TypedArray.AbstractUint8ClampedArray {
      InteropUint8ClampedArray(TypedArrayFactory factory, boolean offset) {
         super(factory, offset, (byte)-1);
      }

      @Override
      public int getIntImpl(Object buffer, int offset, int index, InteropLibrary interop) {
         return TypedArray.InteropInt8Array.readBufferByte(buffer, offset + index * 1, interop) & 0xFF;
      }

      @Override
      public void setIntImpl(Object buffer, int offset, int index, int value, InteropLibrary interop) {
         TypedArray.InteropInt8Array.writeBufferByte(buffer, offset + index * 1, (byte)uint8Clamp(value), interop);
      }

      @Override
      public int getBufferElementIntImpl(Object buffer, int index, boolean littleEndian, InteropLibrary interop) {
         return TypedArray.InteropInt8Array.readBufferByte(buffer, index, interop) & 0xFF;
      }

      @Override
      public void setBufferElementIntImpl(Object buffer, int index, boolean littleEndian, int value, InteropLibrary interop) {
         TypedArray.InteropInt8Array.writeBufferByte(buffer, index, (byte)uint8Clamp(value), interop);
      }
   }

   public abstract static class TypedBigIntArray extends TypedArray {
      protected TypedBigIntArray(TypedArrayFactory factory, boolean offset, byte bufferType) {
         super(factory, offset, bufferType);
      }

      @Override
      public Object getElement(JSDynamicObject object, long index) {
         return this.hasElement(object, index) ? this.getBigInt(object, (int)index, InteropLibrary.getUncached()) : Undefined.instance;
      }

      @Override
      public Object getElementInBounds(JSDynamicObject object, long index) {
         assert this.hasElement(object, index);

         return this.getBigInt(object, (int)index, InteropLibrary.getUncached());
      }

      public TypedArray.TypedBigIntArray setElementImpl(JSDynamicObject object, long index, Object value, boolean strict) {
         if (this.hasElement(object, index)) {
            this.setBigInt(object, (int)index, JSRuntime.toBigInt(value), InteropLibrary.getUncached());
         }

         return this;
      }

      public final BigInt getBigInt(JSDynamicObject object, int index, InteropLibrary interop) {
         return this.getBigIntImpl(getBufferFromTypedArray(object), this.getOffset(object), index, interop);
      }

      public final void setBigInt(JSDynamicObject object, int index, BigInt value, InteropLibrary interop) {
         this.setLongImpl(getBufferFromTypedArray(object), this.getOffset(object), index, value.longValue(), interop);
      }

      public BigInt getBigIntImpl(Object buffer, int offset, int index, InteropLibrary interop) {
         return BigInt.valueOf(this.getLongImpl(buffer, offset, index, interop));
      }

      public abstract long getLongImpl(Object buffer, int offset, int index, InteropLibrary interop);

      public abstract void setLongImpl(Object buffer, int offset, int index, long value, InteropLibrary interop);

      @Override
      public Object getBufferElement(Object buffer, int index, boolean littleEndian, InteropLibrary interop) {
         return BigInt.valueOf(this.getBufferElementLongImpl(buffer, index, littleEndian, interop));
      }

      public abstract long getBufferElementLongImpl(Object buffer, int index, boolean littleEndian, InteropLibrary interop);

      @Override
      public final void setBufferElement(Object buffer, int index, boolean littleEndian, Object value, InteropLibrary interop) {
         this.setBufferElementLongImpl(buffer, index, littleEndian, JSRuntime.toBigInt(value).longValue(), interop);
      }

      public abstract void setBufferElementLongImpl(Object buffer, int index, boolean littleEndian, long value, InteropLibrary interop);

      public long compareExchangeLong(JSTypedArrayObject typedArray, int index, long expectedValue, long newValue) {
         throw Errors.shouldNotReachHere();
      }

      public BigInt compareExchangeBigInt(JSTypedArrayObject typedArray, int index, BigInt expectedValue, BigInt newValue) {
         throw Errors.shouldNotReachHere();
      }
   }

   public abstract static class TypedFloatArray extends TypedArray {
      protected TypedFloatArray(TypedArrayFactory factory, boolean offset, byte bufferType) {
         super(factory, offset, bufferType);
      }

      @Override
      public final Object getElement(JSDynamicObject object, long index) {
         return this.hasElement(object, index) ? this.getDouble(object, (int)index, InteropLibrary.getUncached()) : Undefined.instance;
      }

      @Override
      public Object getElementInBounds(JSDynamicObject object, long index) {
         assert this.hasElement(object, index);

         return this.getDouble(object, (int)index, InteropLibrary.getUncached());
      }

      public final TypedArray.TypedFloatArray setElementImpl(JSDynamicObject object, long index, Object value, boolean strict) {
         if (this.hasElement(object, index)) {
            this.setDouble(object, (int)index, JSRuntime.toDouble(value), InteropLibrary.getUncached());
         }

         return this;
      }

      public final double getDouble(JSDynamicObject object, int index, InteropLibrary interop) {
         return this.getDoubleImpl(getBufferFromTypedArray(object), this.getOffset(object), index, interop);
      }

      public final void setDouble(JSDynamicObject object, int index, double value, InteropLibrary interop) {
         this.setDoubleImpl(getBufferFromTypedArray(object), this.getOffset(object), index, value, interop);
      }

      public abstract double getDoubleImpl(Object buffer, int offset, int index, InteropLibrary interop);

      public abstract void setDoubleImpl(Object buffer, int offset, int index, double value, InteropLibrary interop);
   }

   public abstract static class TypedIntArray extends TypedArray {
      protected TypedIntArray(TypedArrayFactory factory, boolean offset, byte bufferType) {
         super(factory, offset, bufferType);
      }

      @Override
      public Object getElement(JSDynamicObject object, long index) {
         return this.hasElement(object, index) ? this.getInt(object, (int)index, InteropLibrary.getUncached()) : Undefined.instance;
      }

      @Override
      public Object getElementInBounds(JSDynamicObject object, long index) {
         assert this.hasElement(object, index);

         return this.getInt(object, (int)index, InteropLibrary.getUncached());
      }

      public TypedArray.TypedIntArray setElementImpl(JSDynamicObject object, long index, Object value, boolean strict) {
         if (this.hasElement(object, index)) {
            this.setInt(object, (int)index, JSRuntime.toInt32(value), InteropLibrary.getUncached());
         }

         return this;
      }

      public final int getInt(JSDynamicObject object, int index, InteropLibrary interop) {
         return this.getIntImpl(getBufferFromTypedArray(object), this.getOffset(object), index, interop);
      }

      public final void setInt(JSDynamicObject object, int index, int value, InteropLibrary interop) {
         this.setIntImpl(getBufferFromTypedArray(object), this.getOffset(object), index, value, interop);
      }

      public abstract int getIntImpl(Object buffer, int offset, int index, InteropLibrary interop);

      public abstract void setIntImpl(Object buffer, int offset, int index, int value, InteropLibrary interop);

      @Override
      public Object getBufferElement(Object buffer, int index, boolean littleEndian, InteropLibrary interop) {
         return this.getBufferElementIntImpl(buffer, index, littleEndian, interop);
      }

      public abstract int getBufferElementIntImpl(Object buffer, int index, boolean littleEndian, InteropLibrary interop);

      @Override
      public void setBufferElement(Object buffer, int index, boolean littleEndian, Object value, InteropLibrary interop) {
         this.setBufferElementIntImpl(buffer, index, littleEndian, JSRuntime.toInt32((Number)value), interop);
      }

      public abstract void setBufferElementIntImpl(Object buffer, int index, boolean littleEndian, int value, InteropLibrary interop);

      public int compareExchangeInt(JSTypedArrayObject typedArray, int index, int expectedValue, int newValue) {
         throw Errors.shouldNotReachHere();
      }

      static boolean isCompareExchangeSupported(ByteBuffer byteBuffer, int byteOffset) {
         return (byteOffset & -4) <= byteBuffer.limit() - 4;
      }

      @CompilerDirectives.TruffleBoundary
      final int lockedReadModifyWriteByte(JSTypedArrayObject typedArrayObject, int index, int expectedValue, int newValue) {
         JSArrayBufferObject arrayBuffer = getBufferFromTypedArray(typedArrayObject);
         JSAgentWaiterList waiterList = JSSharedArrayBuffer.getWaiterList(arrayBuffer);
         waiterList.enterAtomicSection();

         byte var8;
         try {
            byte read = (byte)SharedMemorySync.doVolatileGet(typedArrayObject, index, this);
            if (read == (byte)expectedValue) {
               SharedMemorySync.doVolatilePut(typedArrayObject, index, (byte)newValue, this);
            }

            var8 = read;
         } finally {
            waiterList.leaveAtomicSection();
         }

         return var8;
      }

      @CompilerDirectives.TruffleBoundary
      final int lockedReadModifyWriteShort(JSTypedArrayObject typedArrayObject, int index, int expectedValue, int newValue) {
         JSArrayBufferObject arrayBuffer = getBufferFromTypedArray(typedArrayObject);
         JSAgentWaiterList waiterList = JSSharedArrayBuffer.getWaiterList(arrayBuffer);
         waiterList.enterAtomicSection();

         short var8;
         try {
            short read = (short)SharedMemorySync.doVolatileGet(typedArrayObject, index, this);
            if (read == (short)expectedValue) {
               SharedMemorySync.doVolatilePut(typedArrayObject, index, (short)newValue, this);
            }

            var8 = read;
         } finally {
            waiterList.leaveAtomicSection();
         }

         return var8;
      }
   }

   public static final class Uint16Array extends TypedArray.TypedIntArray {
      Uint16Array(TypedArrayFactory factory, boolean offset) {
         super(factory, offset, (byte)0);
      }

      @Override
      public int getIntImpl(Object buffer, int offset, int index, InteropLibrary interop) {
         return ByteArrayAccess.nativeOrder().getUint16(getByteArray(buffer), offset + index * 2);
      }

      @Override
      public void setIntImpl(Object buffer, int offset, int index, int value, InteropLibrary interop) {
         ByteArrayAccess.nativeOrder().putInt16(getByteArray(buffer), offset + index * 2, value);
      }

      @Override
      public int getBufferElementIntImpl(Object buffer, int index, boolean littleEndian, InteropLibrary interop) {
         return ByteArrayAccess.forOrder(littleEndian).getUint16(getByteArray(buffer), index);
      }

      @Override
      public void setBufferElementIntImpl(Object buffer, int index, boolean littleEndian, int value, InteropLibrary interop) {
         ByteArrayAccess.forOrder(littleEndian).putInt16(getByteArray(buffer), index, value);
      }
   }

   public static final class Uint32Array extends TypedArray.AbstractUint32Array {
      Uint32Array(TypedArrayFactory factory, boolean offset) {
         super(factory, offset, (byte)0);
      }

      @Override
      public int getIntImpl(Object buffer, int offset, int index, InteropLibrary interop) {
         return ByteArrayAccess.nativeOrder().getInt32(getByteArray(buffer), offset + index * 4);
      }

      @Override
      public void setIntImpl(Object buffer, int offset, int index, int value, InteropLibrary interop) {
         ByteArrayAccess.nativeOrder().putInt32(getByteArray(buffer), offset + index * 4, value);
      }

      @Override
      public int getBufferElementIntImpl(Object buffer, int index, boolean littleEndian, InteropLibrary interop) {
         return ByteArrayAccess.forOrder(littleEndian).getInt32(getByteArray(buffer), index);
      }

      @Override
      public void setBufferElementIntImpl(Object buffer, int index, boolean littleEndian, int value, InteropLibrary interop) {
         ByteArrayAccess.forOrder(littleEndian).putInt32(getByteArray(buffer), index, value);
      }
   }

   public static final class Uint8Array extends TypedArray.TypedIntArray {
      Uint8Array(TypedArrayFactory factory, boolean offset) {
         super(factory, offset, (byte)0);
      }

      @Override
      public int getIntImpl(Object buffer, int offset, int index, InteropLibrary interop) {
         return ByteArrayAccess.nativeOrder().getUint8(getByteArray(buffer), offset + index * 1);
      }

      @Override
      public void setIntImpl(Object buffer, int offset, int index, int value, InteropLibrary interop) {
         ByteArrayAccess.nativeOrder().putInt8(getByteArray(buffer), offset + index * 1, value);
      }

      @Override
      public int getBufferElementIntImpl(Object buffer, int index, boolean littleEndian, InteropLibrary interop) {
         return ByteArrayAccess.forOrder(littleEndian).getUint8(getByteArray(buffer), index);
      }

      @Override
      public void setBufferElementIntImpl(Object buffer, int index, boolean littleEndian, int value, InteropLibrary interop) {
         ByteArrayAccess.forOrder(littleEndian).putInt8(getByteArray(buffer), index, value);
      }
   }

   public static final class Uint8ClampedArray extends TypedArray.AbstractUint8ClampedArray {
      Uint8ClampedArray(TypedArrayFactory factory, boolean offset) {
         super(factory, offset, (byte)0);
      }

      @Override
      public int getIntImpl(Object buffer, int offset, int index, InteropLibrary interop) {
         return ByteArrayAccess.nativeOrder().getUint8(getByteArray(buffer), offset + index * 1);
      }

      @Override
      public void setIntImpl(Object buffer, int offset, int index, int value, InteropLibrary interop) {
         ByteArrayAccess.nativeOrder().putInt8(getByteArray(buffer), offset + index * 1, uint8Clamp(value));
      }

      @Override
      public int getBufferElementIntImpl(Object buffer, int index, boolean littleEndian, InteropLibrary interop) {
         return ByteArrayAccess.forOrder(littleEndian).getUint8(getByteArray(buffer), index);
      }

      @Override
      public void setBufferElementIntImpl(Object buffer, int index, boolean littleEndian, int value, InteropLibrary interop) {
         ByteArrayAccess.forOrder(littleEndian).putInt8(getByteArray(buffer), index, uint8Clamp(value));
      }
   }
}
