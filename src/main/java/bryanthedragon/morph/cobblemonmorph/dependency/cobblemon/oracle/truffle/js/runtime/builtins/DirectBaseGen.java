package com.oracle.truffle.js.runtime.builtins;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.InvalidBufferOffsetException;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.library.LibraryExport;
import com.oracle.truffle.js.runtime.objects.JSNonProxyObjectGen;
import java.nio.ByteOrder;

@GeneratedBy(JSArrayBufferObject.DirectBase.class)
public final class DirectBaseGen {
   private DirectBaseGen() {
   }

   static {
      LibraryExport.register(JSArrayBufferObject.DirectBase.class, new DirectBaseGen.InteropLibraryExports());
   }

   @GeneratedBy(JSArrayBufferObject.DirectBase.class)
   public static class InteropLibraryExports extends LibraryExport<InteropLibrary> {
      private InteropLibraryExports() {
         super(InteropLibrary.class, JSArrayBufferObject.DirectBase.class, false, false, 0);
      }

      protected InteropLibrary createUncached(Object receiver) {
         assert receiver instanceof JSArrayBufferObject.DirectBase;

         InteropLibrary uncached = new DirectBaseGen.InteropLibraryExports.Uncached(receiver);
         return uncached;
      }

      protected InteropLibrary createCached(Object receiver) {
         assert receiver instanceof JSArrayBufferObject.DirectBase;

         return new DirectBaseGen.InteropLibraryExports.Cached(receiver);
      }

      @GeneratedBy(JSArrayBufferObject.DirectBase.class)
      public static class Cached extends JSNonProxyObjectGen.InteropLibraryExports.Cached {
         protected Cached(Object receiver) {
            super(receiver);
         }

         @Override
         public boolean hasBufferElements(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            return ((JSArrayBufferObject.DirectBase)receiver).hasBufferElements();
         }

         @Override
         public long getBufferSize(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            return ((JSArrayBufferObject.DirectBase)receiver).getBufferSize();
         }

         @Override
         public byte readBufferByte(Object receiver, long byteOffset) throws UnsupportedMessageException, InvalidBufferOffsetException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            return ((JSArrayBufferObject.DirectBase)receiver).readBufferByte(byteOffset);
         }

         @Override
         public short readBufferShort(Object receiver, ByteOrder order, long byteOffset) throws UnsupportedMessageException, InvalidBufferOffsetException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            return ((JSArrayBufferObject.DirectBase)receiver).readBufferShort(order, byteOffset);
         }

         @Override
         public int readBufferInt(Object receiver, ByteOrder order, long byteOffset) throws UnsupportedMessageException, InvalidBufferOffsetException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            return ((JSArrayBufferObject.DirectBase)receiver).readBufferInt(order, byteOffset);
         }

         @Override
         public long readBufferLong(Object receiver, ByteOrder order, long byteOffset) throws UnsupportedMessageException, InvalidBufferOffsetException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            return ((JSArrayBufferObject.DirectBase)receiver).readBufferLong(order, byteOffset);
         }

         @Override
         public float readBufferFloat(Object receiver, ByteOrder order, long byteOffset) throws UnsupportedMessageException, InvalidBufferOffsetException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            return ((JSArrayBufferObject.DirectBase)receiver).readBufferFloat(order, byteOffset);
         }

         @Override
         public double readBufferDouble(Object receiver, ByteOrder order, long byteOffset) throws UnsupportedMessageException, InvalidBufferOffsetException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            return ((JSArrayBufferObject.DirectBase)receiver).readBufferDouble(order, byteOffset);
         }

         @Override
         public boolean isBufferWritable(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            return ((JSArrayBufferObject.DirectBase)receiver).isBufferWritable();
         }

         @Override
         public void writeBufferByte(Object receiver, long byteOffset, byte value) throws UnsupportedMessageException, InvalidBufferOffsetException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            ((JSArrayBufferObject.DirectBase)receiver).writeBufferByte(byteOffset, value);
         }

         @Override
         public void writeBufferShort(Object receiver, ByteOrder order, long byteOffset, short value) throws UnsupportedMessageException, InvalidBufferOffsetException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            ((JSArrayBufferObject.DirectBase)receiver).writeBufferShort(order, byteOffset, value);
         }

         @Override
         public void writeBufferInt(Object receiver, ByteOrder order, long byteOffset, int value) throws UnsupportedMessageException, InvalidBufferOffsetException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            ((JSArrayBufferObject.DirectBase)receiver).writeBufferInt(order, byteOffset, value);
         }

         @Override
         public void writeBufferLong(Object receiver, ByteOrder order, long byteOffset, long value) throws UnsupportedMessageException, InvalidBufferOffsetException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            ((JSArrayBufferObject.DirectBase)receiver).writeBufferLong(order, byteOffset, value);
         }

         @Override
         public void writeBufferFloat(Object receiver, ByteOrder order, long byteOffset, float value) throws UnsupportedMessageException, InvalidBufferOffsetException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            ((JSArrayBufferObject.DirectBase)receiver).writeBufferFloat(order, byteOffset, value);
         }

         @Override
         public void writeBufferDouble(Object receiver, ByteOrder order, long byteOffset, double value) throws UnsupportedMessageException, InvalidBufferOffsetException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            ((JSArrayBufferObject.DirectBase)receiver).writeBufferDouble(order, byteOffset, value);
         }
      }

      @GeneratedBy(JSArrayBufferObject.DirectBase.class)
      public static class Uncached extends JSNonProxyObjectGen.InteropLibraryExports.Uncached {
         protected Uncached(Object receiver) {
            super(receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean accepts(Object receiver) {
            return super.accepts(receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean hasBufferElements(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((JSArrayBufferObject.DirectBase)receiver).hasBufferElements();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public long getBufferSize(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((JSArrayBufferObject.DirectBase)receiver).getBufferSize();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public byte readBufferByte(Object receiver, long byteOffset) throws UnsupportedMessageException, InvalidBufferOffsetException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((JSArrayBufferObject.DirectBase)receiver).readBufferByte(byteOffset);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public short readBufferShort(Object receiver, ByteOrder order, long byteOffset) throws UnsupportedMessageException, InvalidBufferOffsetException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((JSArrayBufferObject.DirectBase)receiver).readBufferShort(order, byteOffset);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public int readBufferInt(Object receiver, ByteOrder order, long byteOffset) throws UnsupportedMessageException, InvalidBufferOffsetException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((JSArrayBufferObject.DirectBase)receiver).readBufferInt(order, byteOffset);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public long readBufferLong(Object receiver, ByteOrder order, long byteOffset) throws UnsupportedMessageException, InvalidBufferOffsetException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((JSArrayBufferObject.DirectBase)receiver).readBufferLong(order, byteOffset);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public float readBufferFloat(Object receiver, ByteOrder order, long byteOffset) throws UnsupportedMessageException, InvalidBufferOffsetException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((JSArrayBufferObject.DirectBase)receiver).readBufferFloat(order, byteOffset);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public double readBufferDouble(Object receiver, ByteOrder order, long byteOffset) throws UnsupportedMessageException, InvalidBufferOffsetException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((JSArrayBufferObject.DirectBase)receiver).readBufferDouble(order, byteOffset);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean isBufferWritable(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((JSArrayBufferObject.DirectBase)receiver).isBufferWritable();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public void writeBufferByte(Object receiver, long byteOffset, byte value) throws UnsupportedMessageException, InvalidBufferOffsetException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            ((JSArrayBufferObject.DirectBase)receiver).writeBufferByte(byteOffset, value);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public void writeBufferShort(Object receiver, ByteOrder order, long byteOffset, short value) throws UnsupportedMessageException, InvalidBufferOffsetException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            ((JSArrayBufferObject.DirectBase)receiver).writeBufferShort(order, byteOffset, value);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public void writeBufferInt(Object receiver, ByteOrder order, long byteOffset, int value) throws UnsupportedMessageException, InvalidBufferOffsetException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            ((JSArrayBufferObject.DirectBase)receiver).writeBufferInt(order, byteOffset, value);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public void writeBufferLong(Object receiver, ByteOrder order, long byteOffset, long value) throws UnsupportedMessageException, InvalidBufferOffsetException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            ((JSArrayBufferObject.DirectBase)receiver).writeBufferLong(order, byteOffset, value);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public void writeBufferFloat(Object receiver, ByteOrder order, long byteOffset, float value) throws UnsupportedMessageException, InvalidBufferOffsetException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            ((JSArrayBufferObject.DirectBase)receiver).writeBufferFloat(order, byteOffset, value);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public void writeBufferDouble(Object receiver, ByteOrder order, long byteOffset, double value) throws UnsupportedMessageException, InvalidBufferOffsetException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            ((JSArrayBufferObject.DirectBase)receiver).writeBufferDouble(order, byteOffset, value);
         }
      }
   }
}
