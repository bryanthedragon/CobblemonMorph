
package com.oracle.truffle.js.runtime.builtins;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.ImportStatic;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.InvalidBufferOffsetException;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.library.ExportMessage;
import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSAgentWaiterList;
import com.oracle.truffle.js.runtime.JSConfig;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.array.ByteArrayAccess;
import com.oracle.truffle.js.runtime.array.ByteBufferAccess;
import com.oracle.truffle.js.runtime.builtins.JSArrayBuffer;
import com.oracle.truffle.js.runtime.builtins.JSSharedArrayBuffer;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSNonProxyObject;
import com.oracle.truffle.js.runtime.util.DirectByteBufferHelper;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public abstract class JSArrayBufferObject
extends JSNonProxyObject {
    public static final TruffleString CLASS_NAME = Strings.constant("ArrayBuffer");
    public static final Object PROTOTYPE_NAME = Strings.concat(CLASS_NAME, Strings.DOT_PROTOTYPE);

    protected JSArrayBufferObject(Shape shape) {
        super(shape);
    }

    @Override
    public TruffleString getClassName() {
        return CLASS_NAME;
    }

    public abstract int getByteLength();

    public abstract void detachArrayBuffer();

    public abstract boolean isDetached();

    public static byte[] getByteArray(Object thisObj) {
        assert (JSArrayBuffer.isJSHeapArrayBuffer(thisObj));
        return ((Heap)thisObj).getByteArray();
    }

    public static ByteBuffer getDirectByteBuffer(Object thisObj) {
        assert (JSArrayBuffer.isJSDirectArrayBuffer(thisObj) || JSSharedArrayBuffer.isJSSharedArrayBuffer(thisObj));
        return DirectByteBufferHelper.cast(((DirectBase)thisObj).getByteBuffer());
    }

    public static Object getInteropBuffer(Object thisObj) {
        assert (JSArrayBuffer.isJSInteropArrayBuffer(thisObj));
        return ((Interop)thisObj).getInteropBuffer();
    }

    public static JSAgentWaiterList getWaiterList(JSDynamicObject thisObj) {
        return ((Shared)thisObj).getWaiterList();
    }

    public static void setWaiterList(JSDynamicObject thisObj, JSAgentWaiterList waiterList) {
        ((Shared)thisObj).setWaiterList(waiterList);
    }

    public static JSArrayBufferObject createHeapArrayBuffer(Shape shape, byte[] byteArray) {
        return new Heap(shape, byteArray);
    }

    public static JSArrayBufferObject createDirectArrayBuffer(Shape shape, ByteBuffer byteBuffer) {
        return new Direct(shape, byteBuffer);
    }

    public static JSArrayBufferObject createSharedArrayBuffer(Shape shape, ByteBuffer byteBuffer, JSAgentWaiterList waiterList) {
        return new Shared(shape, byteBuffer, waiterList);
    }

    public static JSArrayBufferObject createInteropArrayBuffer(Shape shape, Object interopBuffer) {
        return new Interop(shape, interopBuffer);
    }

    static final class DetachedBufferIndexOutOfBoundsException
    extends IndexOutOfBoundsException {
        private static final IndexOutOfBoundsException INSTANCE = new DetachedBufferIndexOutOfBoundsException();

        private DetachedBufferIndexOutOfBoundsException() {
        }

        @Override
        public Throwable fillInStackTrace() {
            return this;
        }
    }

    @ExportLibrary(value=InteropLibrary.class)
    @ImportStatic(value={JSConfig.class})
    public static final class Interop
    extends JSArrayBufferObject {
        Object interopBuffer;

        protected Interop(Shape shape, Object interopBuffer) {
            super(shape);
            this.interopBuffer = interopBuffer;
        }

        public int getByteLength(InteropLibrary interop) {
            try {
                return this.isDetached() ? 0 : Math.toIntExact(interop.getBufferSize(this.interopBuffer));
            }
            catch (UnsupportedMessageException | ArithmeticException e) {
                return 0;
            }
        }

        @Override
        public int getByteLength() {
            return this.isDetached() ? 0 : this.getByteLength(InteropLibrary.getUncached(this.interopBuffer));
        }

        public Object getInteropBuffer() {
            return this.interopBuffer;
        }

        @Override
        public boolean isDetached() {
            return this.interopBuffer == null;
        }

        @Override
        public void detachArrayBuffer() {
            this.interopBuffer = null;
        }

        @ExportMessage
        boolean hasBufferElements() {
            return true;
        }

        @ExportMessage
        long getBufferSize(@Cached @Cached.Shared(value="errorBranch") BranchProfile errorBranch, @CachedLibrary(limit="InteropLibraryLimit") @Cached.Shared(value="interop") InteropLibrary interop) {
            if (this.isDetached()) {
                errorBranch.enter();
                return 0L;
            }
            return this.getByteLength(interop);
        }

        @ExportMessage
        byte readBufferByte(long byteOffset, @Cached @Cached.Shared(value="errorBranch") BranchProfile errorBranch, @CachedLibrary(limit="InteropLibraryLimit") @Cached.Shared(value="interop") InteropLibrary interop) throws UnsupportedMessageException, InvalidBufferOffsetException {
            if (this.isDetached()) {
                errorBranch.enter();
                throw InvalidBufferOffsetException.create(byteOffset, 1L);
            }
            return interop.readBufferByte(this.interopBuffer, byteOffset);
        }

        @ExportMessage
        short readBufferShort(ByteOrder order, long byteOffset, @Cached @Cached.Shared(value="errorBranch") BranchProfile errorBranch, @CachedLibrary(limit="InteropLibraryLimit") @Cached.Shared(value="interop") InteropLibrary interop) throws UnsupportedMessageException, InvalidBufferOffsetException {
            if (this.isDetached()) {
                errorBranch.enter();
                throw InvalidBufferOffsetException.create(byteOffset, 2L);
            }
            return interop.readBufferShort(this.interopBuffer, order, byteOffset);
        }

        @ExportMessage
        int readBufferInt(ByteOrder order, long byteOffset, @Cached @Cached.Shared(value="errorBranch") BranchProfile errorBranch, @CachedLibrary(limit="InteropLibraryLimit") @Cached.Shared(value="interop") InteropLibrary interop) throws UnsupportedMessageException, InvalidBufferOffsetException {
            if (this.isDetached()) {
                errorBranch.enter();
                throw InvalidBufferOffsetException.create(byteOffset, 4L);
            }
            return interop.readBufferInt(this.interopBuffer, order, byteOffset);
        }

        @ExportMessage
        long readBufferLong(ByteOrder order, long byteOffset, @Cached @Cached.Shared(value="errorBranch") BranchProfile errorBranch, @CachedLibrary(limit="InteropLibraryLimit") @Cached.Shared(value="interop") InteropLibrary interop) throws UnsupportedMessageException, InvalidBufferOffsetException {
            if (this.isDetached()) {
                errorBranch.enter();
                throw InvalidBufferOffsetException.create(byteOffset, 8L);
            }
            return interop.readBufferLong(this.interopBuffer, order, byteOffset);
        }

        @ExportMessage
        float readBufferFloat(ByteOrder order, long byteOffset, @Cached @Cached.Shared(value="errorBranch") BranchProfile errorBranch, @CachedLibrary(limit="InteropLibraryLimit") @Cached.Shared(value="interop") InteropLibrary interop) throws UnsupportedMessageException, InvalidBufferOffsetException {
            if (this.isDetached()) {
                errorBranch.enter();
                throw InvalidBufferOffsetException.create(byteOffset, 4L);
            }
            return interop.readBufferFloat(this.interopBuffer, order, byteOffset);
        }

        @ExportMessage
        double readBufferDouble(ByteOrder order, long byteOffset, @Cached @Cached.Shared(value="errorBranch") BranchProfile errorBranch, @CachedLibrary(limit="InteropLibraryLimit") @Cached.Shared(value="interop") InteropLibrary interop) throws UnsupportedMessageException, InvalidBufferOffsetException {
            if (this.isDetached()) {
                errorBranch.enter();
                throw InvalidBufferOffsetException.create(byteOffset, 8L);
            }
            return interop.readBufferDouble(this.interopBuffer, order, byteOffset);
        }

        @ExportMessage
        boolean isBufferWritable(@CachedLibrary(limit="InteropLibraryLimit") @Cached.Shared(value="interop") InteropLibrary interop) throws UnsupportedMessageException {
            return interop.isBufferWritable(this.interopBuffer);
        }

        @ExportMessage
        void writeBufferByte(long byteOffset, byte value2, @Cached @Cached.Shared(value="errorBranch") BranchProfile errorBranch, @CachedLibrary(limit="InteropLibraryLimit") @Cached.Shared(value="interop") InteropLibrary interop) throws UnsupportedMessageException, InvalidBufferOffsetException {
            if (this.isDetached()) {
                errorBranch.enter();
                throw InvalidBufferOffsetException.create(byteOffset, 1L);
            }
            interop.writeBufferByte(this.interopBuffer, byteOffset, value2);
        }

        @ExportMessage
        void writeBufferShort(ByteOrder order, long byteOffset, short value2, @Cached @Cached.Shared(value="errorBranch") BranchProfile errorBranch, @CachedLibrary(limit="InteropLibraryLimit") @Cached.Shared(value="interop") InteropLibrary interop) throws UnsupportedMessageException, InvalidBufferOffsetException {
            if (this.isDetached()) {
                errorBranch.enter();
                throw InvalidBufferOffsetException.create(byteOffset, 2L);
            }
            interop.writeBufferShort(this.interopBuffer, order, byteOffset, value2);
        }

        @ExportMessage
        void writeBufferInt(ByteOrder order, long byteOffset, int value2, @Cached @Cached.Shared(value="errorBranch") BranchProfile errorBranch, @CachedLibrary(limit="InteropLibraryLimit") @Cached.Shared(value="interop") InteropLibrary interop) throws UnsupportedMessageException, InvalidBufferOffsetException {
            if (this.isDetached()) {
                errorBranch.enter();
                throw InvalidBufferOffsetException.create(byteOffset, 4L);
            }
            interop.writeBufferInt(this.interopBuffer, order, byteOffset, value2);
        }

        @ExportMessage
        void writeBufferLong(ByteOrder order, long byteOffset, long value2, @Cached @Cached.Shared(value="errorBranch") BranchProfile errorBranch, @CachedLibrary(limit="InteropLibraryLimit") @Cached.Shared(value="interop") InteropLibrary interop) throws UnsupportedMessageException, InvalidBufferOffsetException {
            if (this.isDetached()) {
                errorBranch.enter();
                throw InvalidBufferOffsetException.create(byteOffset, 8L);
            }
            interop.writeBufferLong(this.interopBuffer, order, byteOffset, value2);
        }

        @ExportMessage
        void writeBufferFloat(ByteOrder order, long byteOffset, float value2, @Cached @Cached.Shared(value="errorBranch") BranchProfile errorBranch, @CachedLibrary(limit="InteropLibraryLimit") @Cached.Shared(value="interop") InteropLibrary interop) throws UnsupportedMessageException, InvalidBufferOffsetException {
            if (this.isDetached()) {
                errorBranch.enter();
                throw InvalidBufferOffsetException.create(byteOffset, 4L);
            }
            interop.writeBufferFloat(this.interopBuffer, order, byteOffset, value2);
        }

        @ExportMessage
        void writeBufferDouble(ByteOrder order, long byteOffset, double value2, @Cached @Cached.Shared(value="errorBranch") BranchProfile errorBranch, @CachedLibrary(limit="InteropLibraryLimit") @Cached.Shared(value="interop") InteropLibrary interop) throws UnsupportedMessageException, InvalidBufferOffsetException {
            if (this.isDetached()) {
                errorBranch.enter();
                throw InvalidBufferOffsetException.create(byteOffset, 8L);
            }
            interop.writeBufferDouble(this.interopBuffer, order, byteOffset, value2);
        }
    }

    public static final class Shared
    extends DirectBase {
        JSAgentWaiterList waiterList;

        protected Shared(Shape shape, ByteBuffer byteBuffer, JSAgentWaiterList waiterList) {
            super(shape, byteBuffer);
            this.waiterList = waiterList;
        }

        public JSAgentWaiterList getWaiterList() {
            return this.waiterList;
        }

        public void setWaiterList(JSAgentWaiterList waiterList) {
            this.waiterList = waiterList;
        }

        @Override
        public void detachArrayBuffer() {
            throw Errors.unsupported("SharedArrayBuffer cannot be detached");
        }

        @Override
        public boolean isDetached() {
            return false;
        }
    }

    public static final class Direct
    extends DirectBase {
        protected Direct(Shape shape, ByteBuffer byteBuffer) {
            super(shape, byteBuffer);
        }

        @Override
        public void detachArrayBuffer() {
            this.byteBuffer = null;
        }

        @Override
        public boolean isDetached() {
            return this.byteBuffer == null;
        }
    }

    @ExportLibrary(value=InteropLibrary.class)
    public static abstract class DirectBase
    extends JSArrayBufferObject {
        ByteBuffer byteBuffer;

        protected DirectBase(Shape shape, ByteBuffer byteBuffer) {
            super(shape);
            this.byteBuffer = byteBuffer;
        }

        public final ByteBuffer getByteBuffer() {
            return this.byteBuffer;
        }

        public final void setByteBuffer(ByteBuffer byteBuffer) {
            this.byteBuffer = byteBuffer;
        }

        @Override
        public final int getByteLength() {
            return this.byteBuffer.limit();
        }

        @Override
        public abstract void detachArrayBuffer();

        @ExportMessage
        final boolean hasBufferElements() {
            return true;
        }

        @ExportMessage
        final long getBufferSize() {
            return this.isDetached() ? 0L : (long)this.getByteLength();
        }

        private void ensureNotDetached() {
            if (this.isDetached()) {
                throw DetachedBufferIndexOutOfBoundsException.INSTANCE;
            }
        }

        @ExportMessage
        final byte readBufferByte(long byteOffset) throws InvalidBufferOffsetException {
            try {
                this.ensureNotDetached();
                return this.byteBuffer.get(Math.toIntExact(byteOffset));
            }
            catch (ArithmeticException | IndexOutOfBoundsException e) {
                throw InvalidBufferOffsetException.create(byteOffset, 1L);
            }
        }

        @ExportMessage
        final short readBufferShort(ByteOrder order, long byteOffset) throws InvalidBufferOffsetException {
            try {
                this.ensureNotDetached();
                return (short)ByteBufferAccess.forOrder(order == ByteOrder.LITTLE_ENDIAN).getInt16(this.byteBuffer, Math.toIntExact(byteOffset));
            }
            catch (ArithmeticException | IndexOutOfBoundsException e) {
                throw InvalidBufferOffsetException.create(byteOffset, 2L);
            }
        }

        @ExportMessage
        final int readBufferInt(ByteOrder order, long byteOffset) throws InvalidBufferOffsetException {
            try {
                this.ensureNotDetached();
                return ByteBufferAccess.forOrder(order == ByteOrder.LITTLE_ENDIAN).getInt32(this.byteBuffer, Math.toIntExact(byteOffset));
            }
            catch (ArithmeticException | IndexOutOfBoundsException e) {
                throw InvalidBufferOffsetException.create(byteOffset, 4L);
            }
        }

        @ExportMessage
        final long readBufferLong(ByteOrder order, long byteOffset) throws InvalidBufferOffsetException {
            try {
                this.ensureNotDetached();
                return ByteBufferAccess.forOrder(order == ByteOrder.LITTLE_ENDIAN).getInt64(this.byteBuffer, Math.toIntExact(byteOffset));
            }
            catch (ArithmeticException | IndexOutOfBoundsException e) {
                throw InvalidBufferOffsetException.create(byteOffset, 8L);
            }
        }

        @ExportMessage
        final float readBufferFloat(ByteOrder order, long byteOffset) throws InvalidBufferOffsetException {
            try {
                this.ensureNotDetached();
                return ByteBufferAccess.forOrder(order == ByteOrder.LITTLE_ENDIAN).getFloat(this.byteBuffer, Math.toIntExact(byteOffset));
            }
            catch (ArithmeticException | IndexOutOfBoundsException e) {
                throw InvalidBufferOffsetException.create(byteOffset, 4L);
            }
        }

        @ExportMessage
        final double readBufferDouble(ByteOrder order, long byteOffset) throws InvalidBufferOffsetException {
            try {
                this.ensureNotDetached();
                return ByteBufferAccess.forOrder(order == ByteOrder.LITTLE_ENDIAN).getDouble(this.byteBuffer, Math.toIntExact(byteOffset));
            }
            catch (ArithmeticException | IndexOutOfBoundsException e) {
                throw InvalidBufferOffsetException.create(byteOffset, 8L);
            }
        }

        @ExportMessage
        final boolean isBufferWritable() {
            return this.hasBufferElements();
        }

        @ExportMessage
        final void writeBufferByte(long byteOffset, byte value2) throws InvalidBufferOffsetException {
            try {
                this.ensureNotDetached();
                this.byteBuffer.put(Math.toIntExact(byteOffset), value2);
            }
            catch (ArithmeticException | IndexOutOfBoundsException e) {
                throw InvalidBufferOffsetException.create(byteOffset, 1L);
            }
        }

        @ExportMessage
        final void writeBufferShort(ByteOrder order, long byteOffset, short value2) throws InvalidBufferOffsetException {
            try {
                this.ensureNotDetached();
                ByteBufferAccess.forOrder(order == ByteOrder.LITTLE_ENDIAN).putInt16(this.byteBuffer, Math.toIntExact(byteOffset), value2);
            }
            catch (ArithmeticException | IndexOutOfBoundsException e) {
                throw InvalidBufferOffsetException.create(byteOffset, 2L);
            }
        }

        @ExportMessage
        final void writeBufferInt(ByteOrder order, long byteOffset, int value2) throws InvalidBufferOffsetException {
            try {
                this.ensureNotDetached();
                ByteBufferAccess.forOrder(order == ByteOrder.LITTLE_ENDIAN).putInt32(this.byteBuffer, Math.toIntExact(byteOffset), value2);
            }
            catch (ArithmeticException | IndexOutOfBoundsException e) {
                throw InvalidBufferOffsetException.create(byteOffset, 4L);
            }
        }

        @ExportMessage
        final void writeBufferLong(ByteOrder order, long byteOffset, long value2) throws InvalidBufferOffsetException {
            try {
                this.ensureNotDetached();
                ByteBufferAccess.forOrder(order == ByteOrder.LITTLE_ENDIAN).putInt64(this.byteBuffer, Math.toIntExact(byteOffset), value2);
            }
            catch (ArithmeticException | IndexOutOfBoundsException e) {
                throw InvalidBufferOffsetException.create(byteOffset, 8L);
            }
        }

        @ExportMessage
        final void writeBufferFloat(ByteOrder order, long byteOffset, float value2) throws InvalidBufferOffsetException {
            try {
                this.ensureNotDetached();
                ByteBufferAccess.forOrder(order == ByteOrder.LITTLE_ENDIAN).putFloat(this.byteBuffer, Math.toIntExact(byteOffset), value2);
            }
            catch (ArithmeticException | IndexOutOfBoundsException e) {
                throw InvalidBufferOffsetException.create(byteOffset, 4L);
            }
        }

        @ExportMessage
        final void writeBufferDouble(ByteOrder order, long byteOffset, double value2) throws InvalidBufferOffsetException {
            try {
                this.ensureNotDetached();
                ByteBufferAccess.forOrder(order == ByteOrder.LITTLE_ENDIAN).putDouble(this.byteBuffer, Math.toIntExact(byteOffset), value2);
            }
            catch (ArithmeticException | IndexOutOfBoundsException e) {
                throw InvalidBufferOffsetException.create(byteOffset, 8L);
            }
        }
    }

    @ExportLibrary(value=InteropLibrary.class)
    public static final class Heap
    extends JSArrayBufferObject {
        byte[] byteArray;

        protected Heap(Shape shape, byte[] byteArray) {
            super(shape);
            this.byteArray = byteArray;
        }

        public byte[] getByteArray() {
            return this.byteArray;
        }

        @Override
        public void detachArrayBuffer() {
            this.byteArray = null;
        }

        @Override
        public boolean isDetached() {
            return this.byteArray == null;
        }

        @Override
        public int getByteLength() {
            return this.byteArray.length;
        }

        @ExportMessage
        boolean hasBufferElements() {
            return true;
        }

        @ExportMessage
        long getBufferSize() {
            return this.isDetached() ? 0L : (long)this.getByteLength();
        }

        private void ensureNotDetached() throws IndexOutOfBoundsException {
            if (this.isDetached()) {
                throw DetachedBufferIndexOutOfBoundsException.INSTANCE;
            }
        }

        @ExportMessage
        byte readBufferByte(long byteOffset) throws InvalidBufferOffsetException {
            try {
                this.ensureNotDetached();
                return this.byteArray[Math.toIntExact(byteOffset)];
            }
            catch (ArithmeticException | IndexOutOfBoundsException e) {
                throw InvalidBufferOffsetException.create(byteOffset, 1L);
            }
        }

        @ExportMessage
        short readBufferShort(ByteOrder order, long byteOffset) throws InvalidBufferOffsetException {
            try {
                this.ensureNotDetached();
                return (short)ByteArrayAccess.forOrder(order == ByteOrder.LITTLE_ENDIAN).getInt16(this.byteArray, Math.toIntExact(byteOffset));
            }
            catch (ArithmeticException | IndexOutOfBoundsException e) {
                throw InvalidBufferOffsetException.create(byteOffset, 2L);
            }
        }

        @ExportMessage
        int readBufferInt(ByteOrder order, long byteOffset) throws InvalidBufferOffsetException {
            try {
                this.ensureNotDetached();
                return ByteArrayAccess.forOrder(order == ByteOrder.LITTLE_ENDIAN).getInt32(this.byteArray, Math.toIntExact(byteOffset));
            }
            catch (ArithmeticException | IndexOutOfBoundsException e) {
                throw InvalidBufferOffsetException.create(byteOffset, 4L);
            }
        }

        @ExportMessage
        long readBufferLong(ByteOrder order, long byteOffset) throws InvalidBufferOffsetException {
            try {
                this.ensureNotDetached();
                return ByteArrayAccess.forOrder(order == ByteOrder.LITTLE_ENDIAN).getInt64(this.byteArray, Math.toIntExact(byteOffset));
            }
            catch (ArithmeticException | IndexOutOfBoundsException e) {
                throw InvalidBufferOffsetException.create(byteOffset, 8L);
            }
        }

        @ExportMessage
        float readBufferFloat(ByteOrder order, long byteOffset) throws InvalidBufferOffsetException {
            try {
                this.ensureNotDetached();
                return ByteArrayAccess.forOrder(order == ByteOrder.LITTLE_ENDIAN).getFloat(this.byteArray, Math.toIntExact(byteOffset));
            }
            catch (ArithmeticException | IndexOutOfBoundsException e) {
                throw InvalidBufferOffsetException.create(byteOffset, 4L);
            }
        }

        @ExportMessage
        double readBufferDouble(ByteOrder order, long byteOffset) throws InvalidBufferOffsetException {
            try {
                this.ensureNotDetached();
                return ByteArrayAccess.forOrder(order == ByteOrder.LITTLE_ENDIAN).getDouble(this.byteArray, Math.toIntExact(byteOffset));
            }
            catch (ArithmeticException | IndexOutOfBoundsException e) {
                throw InvalidBufferOffsetException.create(byteOffset, 8L);
            }
        }

        @ExportMessage
        boolean isBufferWritable() {
            return this.hasBufferElements();
        }

        @ExportMessage
        void writeBufferByte(long byteOffset, byte value2) throws InvalidBufferOffsetException {
            try {
                this.ensureNotDetached();
                this.byteArray[Math.toIntExact((long)byteOffset)] = value2;
            }
            catch (ArithmeticException | IndexOutOfBoundsException e) {
                throw InvalidBufferOffsetException.create(byteOffset, 1L);
            }
        }

        @ExportMessage
        void writeBufferShort(ByteOrder order, long byteOffset, short value2) throws InvalidBufferOffsetException {
            try {
                this.ensureNotDetached();
                ByteArrayAccess.forOrder(order == ByteOrder.LITTLE_ENDIAN).putInt16(this.byteArray, Math.toIntExact(byteOffset), value2);
            }
            catch (ArithmeticException | IndexOutOfBoundsException e) {
                throw InvalidBufferOffsetException.create(byteOffset, 2L);
            }
        }

        @ExportMessage
        void writeBufferInt(ByteOrder order, long byteOffset, int value2) throws InvalidBufferOffsetException {
            try {
                this.ensureNotDetached();
                ByteArrayAccess.forOrder(order == ByteOrder.LITTLE_ENDIAN).putInt32(this.byteArray, Math.toIntExact(byteOffset), value2);
            }
            catch (ArithmeticException | IndexOutOfBoundsException e) {
                throw InvalidBufferOffsetException.create(byteOffset, 4L);
            }
        }

        @ExportMessage
        void writeBufferLong(ByteOrder order, long byteOffset, long value2) throws InvalidBufferOffsetException {
            try {
                this.ensureNotDetached();
                ByteArrayAccess.forOrder(order == ByteOrder.LITTLE_ENDIAN).putInt64(this.byteArray, Math.toIntExact(byteOffset), value2);
            }
            catch (ArithmeticException | IndexOutOfBoundsException e) {
                throw InvalidBufferOffsetException.create(byteOffset, 8L);
            }
        }

        @ExportMessage
        void writeBufferFloat(ByteOrder order, long byteOffset, float value2) throws InvalidBufferOffsetException {
            try {
                this.ensureNotDetached();
                ByteArrayAccess.forOrder(order == ByteOrder.LITTLE_ENDIAN).putFloat(this.byteArray, Math.toIntExact(byteOffset), value2);
            }
            catch (ArithmeticException | IndexOutOfBoundsException e) {
                throw InvalidBufferOffsetException.create(byteOffset, 4L);
            }
        }

        @ExportMessage
        void writeBufferDouble(ByteOrder order, long byteOffset, double value2) throws InvalidBufferOffsetException {
            try {
                this.ensureNotDetached();
                ByteArrayAccess.forOrder(order == ByteOrder.LITTLE_ENDIAN).putDouble(this.byteArray, Math.toIntExact(byteOffset), value2);
            }
            catch (ArithmeticException | IndexOutOfBoundsException e) {
                throw InvalidBufferOffsetException.create(byteOffset, 8L);
            }
        }
    }
}

