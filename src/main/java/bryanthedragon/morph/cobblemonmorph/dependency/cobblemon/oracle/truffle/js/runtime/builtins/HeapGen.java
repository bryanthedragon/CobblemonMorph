
package com.oracle.truffle.js.runtime.builtins;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.InvalidBufferOffsetException;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.library.LibraryExport;
import com.oracle.truffle.api.nodes.DenyReplace;
import com.oracle.truffle.js.runtime.builtins.JSArrayBufferObject;
import com.oracle.truffle.js.runtime.objects.JSNonProxyObjectGen;
import java.nio.ByteOrder;

@GeneratedBy(value=JSArrayBufferObject.Heap.class)
final class HeapGen {
    private HeapGen() {
    }

    static {
        LibraryExport.register(JSArrayBufferObject.Heap.class, new InteropLibraryExports());
    }

    @GeneratedBy(value=JSArrayBufferObject.Heap.class)
    private static final class InteropLibraryExports
    extends LibraryExport<InteropLibrary> {
        private InteropLibraryExports() {
            super(InteropLibrary.class, JSArrayBufferObject.Heap.class, false, false, 0);
        }

        @Override
        protected InteropLibrary createUncached(Object receiver) {
            assert (receiver instanceof JSArrayBufferObject.Heap);
            Uncached uncached = new Uncached(receiver);
            return uncached;
        }

        @Override
        protected InteropLibrary createCached(Object receiver) {
            assert (receiver instanceof JSArrayBufferObject.Heap);
            return new Cached(receiver);
        }

        @GeneratedBy(value=JSArrayBufferObject.Heap.class)
        @DenyReplace
        private static final class Uncached
        extends JSNonProxyObjectGen.InteropLibraryExports.Uncached {
            protected Uncached(Object receiver) {
                super(receiver);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean accepts(Object receiver) {
                return super.accepts(receiver);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean hasBufferElements(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((JSArrayBufferObject.Heap)receiver).hasBufferElements();
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public long getBufferSize(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((JSArrayBufferObject.Heap)receiver).getBufferSize();
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public byte readBufferByte(Object receiver, long byteOffset) throws UnsupportedMessageException, InvalidBufferOffsetException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((JSArrayBufferObject.Heap)receiver).readBufferByte(byteOffset);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public short readBufferShort(Object receiver, ByteOrder order, long byteOffset) throws UnsupportedMessageException, InvalidBufferOffsetException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((JSArrayBufferObject.Heap)receiver).readBufferShort(order, byteOffset);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public int readBufferInt(Object receiver, ByteOrder order, long byteOffset) throws UnsupportedMessageException, InvalidBufferOffsetException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((JSArrayBufferObject.Heap)receiver).readBufferInt(order, byteOffset);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public long readBufferLong(Object receiver, ByteOrder order, long byteOffset) throws UnsupportedMessageException, InvalidBufferOffsetException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((JSArrayBufferObject.Heap)receiver).readBufferLong(order, byteOffset);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public float readBufferFloat(Object receiver, ByteOrder order, long byteOffset) throws UnsupportedMessageException, InvalidBufferOffsetException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((JSArrayBufferObject.Heap)receiver).readBufferFloat(order, byteOffset);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public double readBufferDouble(Object receiver, ByteOrder order, long byteOffset) throws UnsupportedMessageException, InvalidBufferOffsetException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((JSArrayBufferObject.Heap)receiver).readBufferDouble(order, byteOffset);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean isBufferWritable(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((JSArrayBufferObject.Heap)receiver).isBufferWritable();
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public void writeBufferByte(Object receiver, long byteOffset, byte value2) throws UnsupportedMessageException, InvalidBufferOffsetException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                ((JSArrayBufferObject.Heap)receiver).writeBufferByte(byteOffset, value2);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public void writeBufferShort(Object receiver, ByteOrder order, long byteOffset, short value2) throws UnsupportedMessageException, InvalidBufferOffsetException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                ((JSArrayBufferObject.Heap)receiver).writeBufferShort(order, byteOffset, value2);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public void writeBufferInt(Object receiver, ByteOrder order, long byteOffset, int value2) throws UnsupportedMessageException, InvalidBufferOffsetException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                ((JSArrayBufferObject.Heap)receiver).writeBufferInt(order, byteOffset, value2);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public void writeBufferLong(Object receiver, ByteOrder order, long byteOffset, long value2) throws UnsupportedMessageException, InvalidBufferOffsetException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                ((JSArrayBufferObject.Heap)receiver).writeBufferLong(order, byteOffset, value2);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public void writeBufferFloat(Object receiver, ByteOrder order, long byteOffset, float value2) throws UnsupportedMessageException, InvalidBufferOffsetException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                ((JSArrayBufferObject.Heap)receiver).writeBufferFloat(order, byteOffset, value2);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public void writeBufferDouble(Object receiver, ByteOrder order, long byteOffset, double value2) throws UnsupportedMessageException, InvalidBufferOffsetException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                ((JSArrayBufferObject.Heap)receiver).writeBufferDouble(order, byteOffset, value2);
            }
        }

        @GeneratedBy(value=JSArrayBufferObject.Heap.class)
        private static final class Cached
        extends JSNonProxyObjectGen.InteropLibraryExports.Cached {
            protected Cached(Object receiver) {
                super(receiver);
            }

            @Override
            public boolean hasBufferElements(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                return ((JSArrayBufferObject.Heap)receiver).hasBufferElements();
            }

            @Override
            public long getBufferSize(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                return ((JSArrayBufferObject.Heap)receiver).getBufferSize();
            }

            @Override
            public byte readBufferByte(Object receiver, long byteOffset) throws UnsupportedMessageException, InvalidBufferOffsetException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                return ((JSArrayBufferObject.Heap)receiver).readBufferByte(byteOffset);
            }

            @Override
            public short readBufferShort(Object receiver, ByteOrder order, long byteOffset) throws UnsupportedMessageException, InvalidBufferOffsetException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                return ((JSArrayBufferObject.Heap)receiver).readBufferShort(order, byteOffset);
            }

            @Override
            public int readBufferInt(Object receiver, ByteOrder order, long byteOffset) throws UnsupportedMessageException, InvalidBufferOffsetException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                return ((JSArrayBufferObject.Heap)receiver).readBufferInt(order, byteOffset);
            }

            @Override
            public long readBufferLong(Object receiver, ByteOrder order, long byteOffset) throws UnsupportedMessageException, InvalidBufferOffsetException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                return ((JSArrayBufferObject.Heap)receiver).readBufferLong(order, byteOffset);
            }

            @Override
            public float readBufferFloat(Object receiver, ByteOrder order, long byteOffset) throws UnsupportedMessageException, InvalidBufferOffsetException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                return ((JSArrayBufferObject.Heap)receiver).readBufferFloat(order, byteOffset);
            }

            @Override
            public double readBufferDouble(Object receiver, ByteOrder order, long byteOffset) throws UnsupportedMessageException, InvalidBufferOffsetException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                return ((JSArrayBufferObject.Heap)receiver).readBufferDouble(order, byteOffset);
            }

            @Override
            public boolean isBufferWritable(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                return ((JSArrayBufferObject.Heap)receiver).isBufferWritable();
            }

            @Override
            public void writeBufferByte(Object receiver, long byteOffset, byte value2) throws UnsupportedMessageException, InvalidBufferOffsetException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                ((JSArrayBufferObject.Heap)receiver).writeBufferByte(byteOffset, value2);
            }

            @Override
            public void writeBufferShort(Object receiver, ByteOrder order, long byteOffset, short value2) throws UnsupportedMessageException, InvalidBufferOffsetException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                ((JSArrayBufferObject.Heap)receiver).writeBufferShort(order, byteOffset, value2);
            }

            @Override
            public void writeBufferInt(Object receiver, ByteOrder order, long byteOffset, int value2) throws UnsupportedMessageException, InvalidBufferOffsetException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                ((JSArrayBufferObject.Heap)receiver).writeBufferInt(order, byteOffset, value2);
            }

            @Override
            public void writeBufferLong(Object receiver, ByteOrder order, long byteOffset, long value2) throws UnsupportedMessageException, InvalidBufferOffsetException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                ((JSArrayBufferObject.Heap)receiver).writeBufferLong(order, byteOffset, value2);
            }

            @Override
            public void writeBufferFloat(Object receiver, ByteOrder order, long byteOffset, float value2) throws UnsupportedMessageException, InvalidBufferOffsetException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                ((JSArrayBufferObject.Heap)receiver).writeBufferFloat(order, byteOffset, value2);
            }

            @Override
            public void writeBufferDouble(Object receiver, ByteOrder order, long byteOffset, double value2) throws UnsupportedMessageException, InvalidBufferOffsetException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                ((JSArrayBufferObject.Heap)receiver).writeBufferDouble(order, byteOffset, value2);
            }
        }
    }
}

