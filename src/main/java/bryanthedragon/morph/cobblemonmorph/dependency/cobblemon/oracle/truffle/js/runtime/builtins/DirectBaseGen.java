
package com.oracle.truffle.js.runtime.builtins;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.InvalidBufferOffsetException;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.library.LibraryExport;
import com.oracle.truffle.js.runtime.builtins.JSArrayBufferObject;
import com.oracle.truffle.js.runtime.objects.JSNonProxyObjectGen;
import java.nio.ByteOrder;

@GeneratedBy(value=JSArrayBufferObject.DirectBase.class)
public final class DirectBaseGen {
    private DirectBaseGen() {
    }

    static {
        LibraryExport.register(JSArrayBufferObject.DirectBase.class, new InteropLibraryExports());
    }

    @GeneratedBy(value=JSArrayBufferObject.DirectBase.class)
    public static class InteropLibraryExports
    extends LibraryExport<InteropLibrary> {
        private InteropLibraryExports() {
            super(InteropLibrary.class, JSArrayBufferObject.DirectBase.class, false, false, 0);
        }

        @Override
        protected InteropLibrary createUncached(Object receiver) {
            assert (receiver instanceof JSArrayBufferObject.DirectBase);
            Uncached uncached = new Uncached(receiver);
            return uncached;
        }

        @Override
        protected InteropLibrary createCached(Object receiver) {
            assert (receiver instanceof JSArrayBufferObject.DirectBase);
            return new Cached(receiver);
        }

        @GeneratedBy(value=JSArrayBufferObject.DirectBase.class)
        public static class Uncached
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
                return ((JSArrayBufferObject.DirectBase)receiver).hasBufferElements();
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public long getBufferSize(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((JSArrayBufferObject.DirectBase)receiver).getBufferSize();
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public byte readBufferByte(Object receiver, long byteOffset) throws UnsupportedMessageException, InvalidBufferOffsetException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((JSArrayBufferObject.DirectBase)receiver).readBufferByte(byteOffset);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public short readBufferShort(Object receiver, ByteOrder order, long byteOffset) throws UnsupportedMessageException, InvalidBufferOffsetException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((JSArrayBufferObject.DirectBase)receiver).readBufferShort(order, byteOffset);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public int readBufferInt(Object receiver, ByteOrder order, long byteOffset) throws UnsupportedMessageException, InvalidBufferOffsetException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((JSArrayBufferObject.DirectBase)receiver).readBufferInt(order, byteOffset);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public long readBufferLong(Object receiver, ByteOrder order, long byteOffset) throws UnsupportedMessageException, InvalidBufferOffsetException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((JSArrayBufferObject.DirectBase)receiver).readBufferLong(order, byteOffset);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public float readBufferFloat(Object receiver, ByteOrder order, long byteOffset) throws UnsupportedMessageException, InvalidBufferOffsetException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((JSArrayBufferObject.DirectBase)receiver).readBufferFloat(order, byteOffset);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public double readBufferDouble(Object receiver, ByteOrder order, long byteOffset) throws UnsupportedMessageException, InvalidBufferOffsetException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((JSArrayBufferObject.DirectBase)receiver).readBufferDouble(order, byteOffset);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean isBufferWritable(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((JSArrayBufferObject.DirectBase)receiver).isBufferWritable();
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public void writeBufferByte(Object receiver, long byteOffset, byte value2) throws UnsupportedMessageException, InvalidBufferOffsetException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                ((JSArrayBufferObject.DirectBase)receiver).writeBufferByte(byteOffset, value2);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public void writeBufferShort(Object receiver, ByteOrder order, long byteOffset, short value2) throws UnsupportedMessageException, InvalidBufferOffsetException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                ((JSArrayBufferObject.DirectBase)receiver).writeBufferShort(order, byteOffset, value2);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public void writeBufferInt(Object receiver, ByteOrder order, long byteOffset, int value2) throws UnsupportedMessageException, InvalidBufferOffsetException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                ((JSArrayBufferObject.DirectBase)receiver).writeBufferInt(order, byteOffset, value2);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public void writeBufferLong(Object receiver, ByteOrder order, long byteOffset, long value2) throws UnsupportedMessageException, InvalidBufferOffsetException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                ((JSArrayBufferObject.DirectBase)receiver).writeBufferLong(order, byteOffset, value2);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public void writeBufferFloat(Object receiver, ByteOrder order, long byteOffset, float value2) throws UnsupportedMessageException, InvalidBufferOffsetException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                ((JSArrayBufferObject.DirectBase)receiver).writeBufferFloat(order, byteOffset, value2);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public void writeBufferDouble(Object receiver, ByteOrder order, long byteOffset, double value2) throws UnsupportedMessageException, InvalidBufferOffsetException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                ((JSArrayBufferObject.DirectBase)receiver).writeBufferDouble(order, byteOffset, value2);
            }
        }

        @GeneratedBy(value=JSArrayBufferObject.DirectBase.class)
        public static class Cached
        extends JSNonProxyObjectGen.InteropLibraryExports.Cached {
            protected Cached(Object receiver) {
                super(receiver);
            }

            @Override
            public boolean hasBufferElements(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                return ((JSArrayBufferObject.DirectBase)receiver).hasBufferElements();
            }

            @Override
            public long getBufferSize(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                return ((JSArrayBufferObject.DirectBase)receiver).getBufferSize();
            }

            @Override
            public byte readBufferByte(Object receiver, long byteOffset) throws UnsupportedMessageException, InvalidBufferOffsetException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                return ((JSArrayBufferObject.DirectBase)receiver).readBufferByte(byteOffset);
            }

            @Override
            public short readBufferShort(Object receiver, ByteOrder order, long byteOffset) throws UnsupportedMessageException, InvalidBufferOffsetException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                return ((JSArrayBufferObject.DirectBase)receiver).readBufferShort(order, byteOffset);
            }

            @Override
            public int readBufferInt(Object receiver, ByteOrder order, long byteOffset) throws UnsupportedMessageException, InvalidBufferOffsetException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                return ((JSArrayBufferObject.DirectBase)receiver).readBufferInt(order, byteOffset);
            }

            @Override
            public long readBufferLong(Object receiver, ByteOrder order, long byteOffset) throws UnsupportedMessageException, InvalidBufferOffsetException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                return ((JSArrayBufferObject.DirectBase)receiver).readBufferLong(order, byteOffset);
            }

            @Override
            public float readBufferFloat(Object receiver, ByteOrder order, long byteOffset) throws UnsupportedMessageException, InvalidBufferOffsetException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                return ((JSArrayBufferObject.DirectBase)receiver).readBufferFloat(order, byteOffset);
            }

            @Override
            public double readBufferDouble(Object receiver, ByteOrder order, long byteOffset) throws UnsupportedMessageException, InvalidBufferOffsetException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                return ((JSArrayBufferObject.DirectBase)receiver).readBufferDouble(order, byteOffset);
            }

            @Override
            public boolean isBufferWritable(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                return ((JSArrayBufferObject.DirectBase)receiver).isBufferWritable();
            }

            @Override
            public void writeBufferByte(Object receiver, long byteOffset, byte value2) throws UnsupportedMessageException, InvalidBufferOffsetException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                ((JSArrayBufferObject.DirectBase)receiver).writeBufferByte(byteOffset, value2);
            }

            @Override
            public void writeBufferShort(Object receiver, ByteOrder order, long byteOffset, short value2) throws UnsupportedMessageException, InvalidBufferOffsetException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                ((JSArrayBufferObject.DirectBase)receiver).writeBufferShort(order, byteOffset, value2);
            }

            @Override
            public void writeBufferInt(Object receiver, ByteOrder order, long byteOffset, int value2) throws UnsupportedMessageException, InvalidBufferOffsetException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                ((JSArrayBufferObject.DirectBase)receiver).writeBufferInt(order, byteOffset, value2);
            }

            @Override
            public void writeBufferLong(Object receiver, ByteOrder order, long byteOffset, long value2) throws UnsupportedMessageException, InvalidBufferOffsetException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                ((JSArrayBufferObject.DirectBase)receiver).writeBufferLong(order, byteOffset, value2);
            }

            @Override
            public void writeBufferFloat(Object receiver, ByteOrder order, long byteOffset, float value2) throws UnsupportedMessageException, InvalidBufferOffsetException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                ((JSArrayBufferObject.DirectBase)receiver).writeBufferFloat(order, byteOffset, value2);
            }

            @Override
            public void writeBufferDouble(Object receiver, ByteOrder order, long byteOffset, double value2) throws UnsupportedMessageException, InvalidBufferOffsetException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                ((JSArrayBufferObject.DirectBase)receiver).writeBufferDouble(order, byteOffset, value2);
            }
        }
    }
}

