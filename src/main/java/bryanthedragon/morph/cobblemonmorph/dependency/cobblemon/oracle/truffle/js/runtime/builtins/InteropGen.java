package com.oracle.truffle.js.runtime.builtins;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.InvalidBufferOffsetException;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.library.LibraryExport;
import com.oracle.truffle.api.library.LibraryFactory;
import com.oracle.truffle.api.nodes.DenyReplace;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.js.nodes.interop.JSInteropGetIteratorNode;
import com.oracle.truffle.js.nodes.interop.KeyInfoNode;
import com.oracle.truffle.js.runtime.objects.JSNonProxyObjectGen;
import java.nio.ByteOrder;
import java.util.concurrent.locks.Lock;

@GeneratedBy(JSArrayBufferObject.Interop.class)
final class InteropGen {
   private static final LibraryFactory<InteropLibrary> INTEROP_LIBRARY_ = LibraryFactory.resolve(InteropLibrary.class);

   private InteropGen() {
   }

   static {
      LibraryExport.register(JSArrayBufferObject.Interop.class, new InteropGen.InteropLibraryExports());
   }

   @GeneratedBy(JSArrayBufferObject.Interop.class)
   private static final class InteropLibraryExports extends LibraryExport<InteropLibrary> {
      private InteropLibraryExports() {
         super(InteropLibrary.class, JSArrayBufferObject.Interop.class, false, false, 0);
      }

      protected InteropLibrary createUncached(Object receiver) {
         assert receiver instanceof JSArrayBufferObject.Interop;

         InteropLibrary uncached = new InteropGen.InteropLibraryExports.Uncached(receiver);
         return uncached;
      }

      protected InteropLibrary createCached(Object receiver) {
         assert receiver instanceof JSArrayBufferObject.Interop;

         return new InteropGen.InteropLibraryExports.Cached(receiver);
      }

      @GeneratedBy(JSArrayBufferObject.Interop.class)
      private static final class Cached extends JSNonProxyObjectGen.InteropLibraryExports.Cached {
         @CompilerDirectives.CompilationFinal
         private volatile int state_0_;
         @Node.Child
         private KeyInfoNode keyInfo;
         @Node.Child
         private JSInteropGetIteratorNode getIterator;
         @CompilerDirectives.CompilationFinal
         private BranchProfile errorBranch;
         @Node.Child
         private InteropLibrary interop;

         protected Cached(Object receiver) {
            super(receiver);
         }

         @Override
         public boolean hasBufferElements(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            return ((JSArrayBufferObject.Interop)receiver).hasBufferElements();
         }

         @Override
         public long getBufferSize(Object arg0Value_) throws UnsupportedMessageException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            JSArrayBufferObject.Interop arg0Value = (JSArrayBufferObject.Interop)arg0Value_;
            int state_0 = this.state_0_;
            if ((state_0 & 1) != 0) {
               return arg0Value.getBufferSize(this.errorBranch, this.interop);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return this.getBufferSizeNode_AndSpecialize(arg0Value);
            }
         }

         private long getBufferSizeNode_AndSpecialize(JSArrayBufferObject.Interop arg0Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            long var5;
            try {
               int state_0 = this.state_0_;
               this.errorBranch = this.errorBranch == null ? BranchProfile.create() : this.errorBranch;
               this.interop = super.insert(this.interop == null ? InteropGen.INTEROP_LIBRARY_.createDispatched(5) : this.interop);
               int var10;
               this.state_0_ = var10 = state_0 | 1;
               lock.unlock();
               hasLock = false;
               var5 = arg0Value.getBufferSize(this.errorBranch, this.interop);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }

            return var5;
         }

         @Override
         public NodeCost getCost() {
            int state_0 = this.state_0_;
            return (state_0 & 1) == 0 ? NodeCost.UNINITIALIZED : NodeCost.MONOMORPHIC;
         }

         @Override
         public byte readBufferByte(Object arg0Value_, long arg1Value) throws UnsupportedMessageException, InvalidBufferOffsetException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            JSArrayBufferObject.Interop arg0Value = (JSArrayBufferObject.Interop)arg0Value_;
            int state_0 = this.state_0_;
            if ((state_0 & 2) != 0) {
               return arg0Value.readBufferByte(arg1Value, this.errorBranch, this.interop);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return this.readBufferByteNode_AndSpecialize(arg0Value, arg1Value);
            }
         }

         private byte readBufferByteNode_AndSpecialize(JSArrayBufferObject.Interop arg0Value, long arg1Value) throws UnsupportedMessageException, InvalidBufferOffsetException {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            byte var7;
            try {
               int state_0 = this.state_0_;
               this.errorBranch = this.errorBranch == null ? BranchProfile.create() : this.errorBranch;
               this.interop = super.insert(this.interop == null ? InteropGen.INTEROP_LIBRARY_.createDispatched(5) : this.interop);
               int var11;
               this.state_0_ = var11 = state_0 | 2;
               lock.unlock();
               hasLock = false;
               var7 = arg0Value.readBufferByte(arg1Value, this.errorBranch, this.interop);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }

            return var7;
         }

         @Override
         public short readBufferShort(Object arg0Value_, ByteOrder arg1Value, long arg2Value) throws UnsupportedMessageException, InvalidBufferOffsetException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            JSArrayBufferObject.Interop arg0Value = (JSArrayBufferObject.Interop)arg0Value_;
            int state_0 = this.state_0_;
            if ((state_0 & 4) != 0) {
               return arg0Value.readBufferShort(arg1Value, arg2Value, this.errorBranch, this.interop);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return this.readBufferShortNode_AndSpecialize(arg0Value, arg1Value, arg2Value);
            }
         }

         private short readBufferShortNode_AndSpecialize(JSArrayBufferObject.Interop arg0Value, ByteOrder arg1Value, long arg2Value) throws UnsupportedMessageException, InvalidBufferOffsetException {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            short var8;
            try {
               int state_0 = this.state_0_;
               this.errorBranch = this.errorBranch == null ? BranchProfile.create() : this.errorBranch;
               this.interop = super.insert(this.interop == null ? InteropGen.INTEROP_LIBRARY_.createDispatched(5) : this.interop);
               int var12;
               this.state_0_ = var12 = state_0 | 4;
               lock.unlock();
               hasLock = false;
               var8 = arg0Value.readBufferShort(arg1Value, arg2Value, this.errorBranch, this.interop);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }

            return var8;
         }

         @Override
         public int readBufferInt(Object arg0Value_, ByteOrder arg1Value, long arg2Value) throws UnsupportedMessageException, InvalidBufferOffsetException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            JSArrayBufferObject.Interop arg0Value = (JSArrayBufferObject.Interop)arg0Value_;
            int state_0 = this.state_0_;
            if ((state_0 & 8) != 0) {
               return arg0Value.readBufferInt(arg1Value, arg2Value, this.errorBranch, this.interop);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return this.readBufferIntNode_AndSpecialize(arg0Value, arg1Value, arg2Value);
            }
         }

         private int readBufferIntNode_AndSpecialize(JSArrayBufferObject.Interop arg0Value, ByteOrder arg1Value, long arg2Value) throws UnsupportedMessageException, InvalidBufferOffsetException {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            int var8;
            try {
               int state_0 = this.state_0_;
               this.errorBranch = this.errorBranch == null ? BranchProfile.create() : this.errorBranch;
               this.interop = super.insert(this.interop == null ? InteropGen.INTEROP_LIBRARY_.createDispatched(5) : this.interop);
               int var12;
               this.state_0_ = var12 = state_0 | 8;
               lock.unlock();
               hasLock = false;
               var8 = arg0Value.readBufferInt(arg1Value, arg2Value, this.errorBranch, this.interop);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }

            return var8;
         }

         @Override
         public long readBufferLong(Object arg0Value_, ByteOrder arg1Value, long arg2Value) throws UnsupportedMessageException, InvalidBufferOffsetException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            JSArrayBufferObject.Interop arg0Value = (JSArrayBufferObject.Interop)arg0Value_;
            int state_0 = this.state_0_;
            if ((state_0 & 16) != 0) {
               return arg0Value.readBufferLong(arg1Value, arg2Value, this.errorBranch, this.interop);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return this.readBufferLongNode_AndSpecialize(arg0Value, arg1Value, arg2Value);
            }
         }

         private long readBufferLongNode_AndSpecialize(JSArrayBufferObject.Interop arg0Value, ByteOrder arg1Value, long arg2Value) throws UnsupportedMessageException, InvalidBufferOffsetException {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            long var8;
            try {
               int state_0 = this.state_0_;
               this.errorBranch = this.errorBranch == null ? BranchProfile.create() : this.errorBranch;
               this.interop = super.insert(this.interop == null ? InteropGen.INTEROP_LIBRARY_.createDispatched(5) : this.interop);
               int var13;
               this.state_0_ = var13 = state_0 | 16;
               lock.unlock();
               hasLock = false;
               var8 = arg0Value.readBufferLong(arg1Value, arg2Value, this.errorBranch, this.interop);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }

            return var8;
         }

         @Override
         public float readBufferFloat(Object arg0Value_, ByteOrder arg1Value, long arg2Value) throws UnsupportedMessageException, InvalidBufferOffsetException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            JSArrayBufferObject.Interop arg0Value = (JSArrayBufferObject.Interop)arg0Value_;
            int state_0 = this.state_0_;
            if ((state_0 & 32) != 0) {
               return arg0Value.readBufferFloat(arg1Value, arg2Value, this.errorBranch, this.interop);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return this.readBufferFloatNode_AndSpecialize(arg0Value, arg1Value, arg2Value);
            }
         }

         private float readBufferFloatNode_AndSpecialize(JSArrayBufferObject.Interop arg0Value, ByteOrder arg1Value, long arg2Value) throws UnsupportedMessageException, InvalidBufferOffsetException {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            float var8;
            try {
               int state_0 = this.state_0_;
               this.errorBranch = this.errorBranch == null ? BranchProfile.create() : this.errorBranch;
               this.interop = super.insert(this.interop == null ? InteropGen.INTEROP_LIBRARY_.createDispatched(5) : this.interop);
               int var12;
               this.state_0_ = var12 = state_0 | 32;
               lock.unlock();
               hasLock = false;
               var8 = arg0Value.readBufferFloat(arg1Value, arg2Value, this.errorBranch, this.interop);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }

            return var8;
         }

         @Override
         public double readBufferDouble(Object arg0Value_, ByteOrder arg1Value, long arg2Value) throws UnsupportedMessageException, InvalidBufferOffsetException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            JSArrayBufferObject.Interop arg0Value = (JSArrayBufferObject.Interop)arg0Value_;
            int state_0 = this.state_0_;
            if ((state_0 & 64) != 0) {
               return arg0Value.readBufferDouble(arg1Value, arg2Value, this.errorBranch, this.interop);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return this.readBufferDoubleNode_AndSpecialize(arg0Value, arg1Value, arg2Value);
            }
         }

         private double readBufferDoubleNode_AndSpecialize(JSArrayBufferObject.Interop arg0Value, ByteOrder arg1Value, long arg2Value) throws UnsupportedMessageException, InvalidBufferOffsetException {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            double var8;
            try {
               int state_0 = this.state_0_;
               this.errorBranch = this.errorBranch == null ? BranchProfile.create() : this.errorBranch;
               this.interop = super.insert(this.interop == null ? InteropGen.INTEROP_LIBRARY_.createDispatched(5) : this.interop);
               int var13;
               this.state_0_ = var13 = state_0 | 64;
               lock.unlock();
               hasLock = false;
               var8 = arg0Value.readBufferDouble(arg1Value, arg2Value, this.errorBranch, this.interop);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }

            return var8;
         }

         @Override
         public boolean isBufferWritable(Object arg0Value_) throws UnsupportedMessageException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            JSArrayBufferObject.Interop arg0Value = (JSArrayBufferObject.Interop)arg0Value_;
            int state_0 = this.state_0_;
            if ((state_0 & 128) != 0) {
               return arg0Value.isBufferWritable(this.interop);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return this.isBufferWritableNode_AndSpecialize(arg0Value);
            }
         }

         private boolean isBufferWritableNode_AndSpecialize(JSArrayBufferObject.Interop arg0Value) throws UnsupportedMessageException {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            boolean var5;
            try {
               int state_0 = this.state_0_;
               this.interop = super.insert(this.interop == null ? InteropGen.INTEROP_LIBRARY_.createDispatched(5) : this.interop);
               int var9;
               this.state_0_ = var9 = state_0 | 128;
               lock.unlock();
               hasLock = false;
               var5 = arg0Value.isBufferWritable(this.interop);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }

            return var5;
         }

         @Override
         public void writeBufferByte(Object arg0Value_, long arg1Value, byte arg2Value) throws UnsupportedMessageException, InvalidBufferOffsetException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            JSArrayBufferObject.Interop arg0Value = (JSArrayBufferObject.Interop)arg0Value_;
            int state_0 = this.state_0_;
            if ((state_0 & 256) != 0) {
               arg0Value.writeBufferByte(arg1Value, arg2Value, this.errorBranch, this.interop);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               this.writeBufferByteNode_AndSpecialize(arg0Value, arg1Value, arg2Value);
            }
         }

         private void writeBufferByteNode_AndSpecialize(JSArrayBufferObject.Interop arg0Value, long arg1Value, byte arg2Value) throws UnsupportedMessageException, InvalidBufferOffsetException {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            try {
               int state_0 = this.state_0_;
               this.errorBranch = this.errorBranch == null ? BranchProfile.create() : this.errorBranch;
               this.interop = super.insert(this.interop == null ? InteropGen.INTEROP_LIBRARY_.createDispatched(5) : this.interop);
               int var11;
               this.state_0_ = var11 = state_0 | 256;
               lock.unlock();
               hasLock = false;
               arg0Value.writeBufferByte(arg1Value, arg2Value, this.errorBranch, this.interop);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }
         }

         @Override
         public void writeBufferShort(Object arg0Value_, ByteOrder arg1Value, long arg2Value, short arg3Value) throws UnsupportedMessageException, InvalidBufferOffsetException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            JSArrayBufferObject.Interop arg0Value = (JSArrayBufferObject.Interop)arg0Value_;
            int state_0 = this.state_0_;
            if ((state_0 & 512) != 0) {
               arg0Value.writeBufferShort(arg1Value, arg2Value, arg3Value, this.errorBranch, this.interop);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               this.writeBufferShortNode_AndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value);
            }
         }

         private void writeBufferShortNode_AndSpecialize(JSArrayBufferObject.Interop arg0Value, ByteOrder arg1Value, long arg2Value, short arg3Value) throws UnsupportedMessageException, InvalidBufferOffsetException {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            try {
               int state_0 = this.state_0_;
               this.errorBranch = this.errorBranch == null ? BranchProfile.create() : this.errorBranch;
               this.interop = super.insert(this.interop == null ? InteropGen.INTEROP_LIBRARY_.createDispatched(5) : this.interop);
               int var12;
               this.state_0_ = var12 = state_0 | 512;
               lock.unlock();
               hasLock = false;
               arg0Value.writeBufferShort(arg1Value, arg2Value, arg3Value, this.errorBranch, this.interop);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }
         }

         @Override
         public void writeBufferInt(Object arg0Value_, ByteOrder arg1Value, long arg2Value, int arg3Value) throws UnsupportedMessageException, InvalidBufferOffsetException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            JSArrayBufferObject.Interop arg0Value = (JSArrayBufferObject.Interop)arg0Value_;
            int state_0 = this.state_0_;
            if ((state_0 & 1024) != 0) {
               arg0Value.writeBufferInt(arg1Value, arg2Value, arg3Value, this.errorBranch, this.interop);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               this.writeBufferIntNode_AndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value);
            }
         }

         private void writeBufferIntNode_AndSpecialize(JSArrayBufferObject.Interop arg0Value, ByteOrder arg1Value, long arg2Value, int arg3Value) throws UnsupportedMessageException, InvalidBufferOffsetException {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            try {
               int state_0 = this.state_0_;
               this.errorBranch = this.errorBranch == null ? BranchProfile.create() : this.errorBranch;
               this.interop = super.insert(this.interop == null ? InteropGen.INTEROP_LIBRARY_.createDispatched(5) : this.interop);
               int var12;
               this.state_0_ = var12 = state_0 | 1024;
               lock.unlock();
               hasLock = false;
               arg0Value.writeBufferInt(arg1Value, arg2Value, arg3Value, this.errorBranch, this.interop);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }
         }

         @Override
         public void writeBufferLong(Object arg0Value_, ByteOrder arg1Value, long arg2Value, long arg3Value) throws UnsupportedMessageException, InvalidBufferOffsetException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            JSArrayBufferObject.Interop arg0Value = (JSArrayBufferObject.Interop)arg0Value_;
            int state_0 = this.state_0_;
            if ((state_0 & 2048) != 0) {
               arg0Value.writeBufferLong(arg1Value, arg2Value, arg3Value, this.errorBranch, this.interop);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               this.writeBufferLongNode_AndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value);
            }
         }

         private void writeBufferLongNode_AndSpecialize(JSArrayBufferObject.Interop arg0Value, ByteOrder arg1Value, long arg2Value, long arg3Value) throws UnsupportedMessageException, InvalidBufferOffsetException {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            try {
               int state_0 = this.state_0_;
               this.errorBranch = this.errorBranch == null ? BranchProfile.create() : this.errorBranch;
               this.interop = super.insert(this.interop == null ? InteropGen.INTEROP_LIBRARY_.createDispatched(5) : this.interop);
               int var13;
               this.state_0_ = var13 = state_0 | 2048;
               lock.unlock();
               hasLock = false;
               arg0Value.writeBufferLong(arg1Value, arg2Value, arg3Value, this.errorBranch, this.interop);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }
         }

         @Override
         public void writeBufferFloat(Object arg0Value_, ByteOrder arg1Value, long arg2Value, float arg3Value) throws UnsupportedMessageException, InvalidBufferOffsetException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            JSArrayBufferObject.Interop arg0Value = (JSArrayBufferObject.Interop)arg0Value_;
            int state_0 = this.state_0_;
            if ((state_0 & 4096) != 0) {
               arg0Value.writeBufferFloat(arg1Value, arg2Value, arg3Value, this.errorBranch, this.interop);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               this.writeBufferFloatNode_AndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value);
            }
         }

         private void writeBufferFloatNode_AndSpecialize(JSArrayBufferObject.Interop arg0Value, ByteOrder arg1Value, long arg2Value, float arg3Value) throws UnsupportedMessageException, InvalidBufferOffsetException {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            try {
               int state_0 = this.state_0_;
               this.errorBranch = this.errorBranch == null ? BranchProfile.create() : this.errorBranch;
               this.interop = super.insert(this.interop == null ? InteropGen.INTEROP_LIBRARY_.createDispatched(5) : this.interop);
               int var12;
               this.state_0_ = var12 = state_0 | 4096;
               lock.unlock();
               hasLock = false;
               arg0Value.writeBufferFloat(arg1Value, arg2Value, arg3Value, this.errorBranch, this.interop);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }
         }

         @Override
         public void writeBufferDouble(Object arg0Value_, ByteOrder arg1Value, long arg2Value, double arg3Value) throws UnsupportedMessageException, InvalidBufferOffsetException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            JSArrayBufferObject.Interop arg0Value = (JSArrayBufferObject.Interop)arg0Value_;
            int state_0 = this.state_0_;
            if ((state_0 & 8192) != 0) {
               arg0Value.writeBufferDouble(arg1Value, arg2Value, arg3Value, this.errorBranch, this.interop);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               this.writeBufferDoubleNode_AndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value);
            }
         }

         private void writeBufferDoubleNode_AndSpecialize(JSArrayBufferObject.Interop arg0Value, ByteOrder arg1Value, long arg2Value, double arg3Value) throws UnsupportedMessageException, InvalidBufferOffsetException {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            try {
               int state_0 = this.state_0_;
               this.errorBranch = this.errorBranch == null ? BranchProfile.create() : this.errorBranch;
               this.interop = super.insert(this.interop == null ? InteropGen.INTEROP_LIBRARY_.createDispatched(5) : this.interop);
               int var13;
               this.state_0_ = var13 = state_0 | 8192;
               lock.unlock();
               hasLock = false;
               arg0Value.writeBufferDouble(arg1Value, arg2Value, arg3Value, this.errorBranch, this.interop);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }
         }
      }

      @GeneratedBy(JSArrayBufferObject.Interop.class)
      @DenyReplace
      private static final class Uncached extends JSNonProxyObjectGen.InteropLibraryExports.Uncached {
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

            return ((JSArrayBufferObject.Interop)receiver).hasBufferElements();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public long getBufferSize(Object arg0Value_) {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            JSArrayBufferObject.Interop arg0Value = (JSArrayBufferObject.Interop)arg0Value_;
            return arg0Value.getBufferSize(BranchProfile.getUncached(), InteropGen.INTEROP_LIBRARY_.getUncached());
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public byte readBufferByte(Object arg0Value_, long arg1Value) throws UnsupportedMessageException, InvalidBufferOffsetException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            JSArrayBufferObject.Interop arg0Value = (JSArrayBufferObject.Interop)arg0Value_;
            return arg0Value.readBufferByte(arg1Value, BranchProfile.getUncached(), InteropGen.INTEROP_LIBRARY_.getUncached());
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public short readBufferShort(Object arg0Value_, ByteOrder arg1Value, long arg2Value) throws UnsupportedMessageException, InvalidBufferOffsetException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            JSArrayBufferObject.Interop arg0Value = (JSArrayBufferObject.Interop)arg0Value_;
            return arg0Value.readBufferShort(arg1Value, arg2Value, BranchProfile.getUncached(), InteropGen.INTEROP_LIBRARY_.getUncached());
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public int readBufferInt(Object arg0Value_, ByteOrder arg1Value, long arg2Value) throws UnsupportedMessageException, InvalidBufferOffsetException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            JSArrayBufferObject.Interop arg0Value = (JSArrayBufferObject.Interop)arg0Value_;
            return arg0Value.readBufferInt(arg1Value, arg2Value, BranchProfile.getUncached(), InteropGen.INTEROP_LIBRARY_.getUncached());
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public long readBufferLong(Object arg0Value_, ByteOrder arg1Value, long arg2Value) throws UnsupportedMessageException, InvalidBufferOffsetException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            JSArrayBufferObject.Interop arg0Value = (JSArrayBufferObject.Interop)arg0Value_;
            return arg0Value.readBufferLong(arg1Value, arg2Value, BranchProfile.getUncached(), InteropGen.INTEROP_LIBRARY_.getUncached());
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public float readBufferFloat(Object arg0Value_, ByteOrder arg1Value, long arg2Value) throws UnsupportedMessageException, InvalidBufferOffsetException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            JSArrayBufferObject.Interop arg0Value = (JSArrayBufferObject.Interop)arg0Value_;
            return arg0Value.readBufferFloat(arg1Value, arg2Value, BranchProfile.getUncached(), InteropGen.INTEROP_LIBRARY_.getUncached());
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public double readBufferDouble(Object arg0Value_, ByteOrder arg1Value, long arg2Value) throws UnsupportedMessageException, InvalidBufferOffsetException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            JSArrayBufferObject.Interop arg0Value = (JSArrayBufferObject.Interop)arg0Value_;
            return arg0Value.readBufferDouble(arg1Value, arg2Value, BranchProfile.getUncached(), InteropGen.INTEROP_LIBRARY_.getUncached());
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean isBufferWritable(Object arg0Value_) throws UnsupportedMessageException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            JSArrayBufferObject.Interop arg0Value = (JSArrayBufferObject.Interop)arg0Value_;
            return arg0Value.isBufferWritable(InteropGen.INTEROP_LIBRARY_.getUncached());
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public void writeBufferByte(Object arg0Value_, long arg1Value, byte arg2Value) throws UnsupportedMessageException, InvalidBufferOffsetException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            JSArrayBufferObject.Interop arg0Value = (JSArrayBufferObject.Interop)arg0Value_;
            arg0Value.writeBufferByte(arg1Value, arg2Value, BranchProfile.getUncached(), InteropGen.INTEROP_LIBRARY_.getUncached());
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public void writeBufferShort(Object arg0Value_, ByteOrder arg1Value, long arg2Value, short arg3Value) throws UnsupportedMessageException, InvalidBufferOffsetException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            JSArrayBufferObject.Interop arg0Value = (JSArrayBufferObject.Interop)arg0Value_;
            arg0Value.writeBufferShort(arg1Value, arg2Value, arg3Value, BranchProfile.getUncached(), InteropGen.INTEROP_LIBRARY_.getUncached());
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public void writeBufferInt(Object arg0Value_, ByteOrder arg1Value, long arg2Value, int arg3Value) throws UnsupportedMessageException, InvalidBufferOffsetException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            JSArrayBufferObject.Interop arg0Value = (JSArrayBufferObject.Interop)arg0Value_;
            arg0Value.writeBufferInt(arg1Value, arg2Value, arg3Value, BranchProfile.getUncached(), InteropGen.INTEROP_LIBRARY_.getUncached());
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public void writeBufferLong(Object arg0Value_, ByteOrder arg1Value, long arg2Value, long arg3Value) throws UnsupportedMessageException, InvalidBufferOffsetException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            JSArrayBufferObject.Interop arg0Value = (JSArrayBufferObject.Interop)arg0Value_;
            arg0Value.writeBufferLong(arg1Value, arg2Value, arg3Value, BranchProfile.getUncached(), InteropGen.INTEROP_LIBRARY_.getUncached());
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public void writeBufferFloat(Object arg0Value_, ByteOrder arg1Value, long arg2Value, float arg3Value) throws UnsupportedMessageException, InvalidBufferOffsetException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            JSArrayBufferObject.Interop arg0Value = (JSArrayBufferObject.Interop)arg0Value_;
            arg0Value.writeBufferFloat(arg1Value, arg2Value, arg3Value, BranchProfile.getUncached(), InteropGen.INTEROP_LIBRARY_.getUncached());
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public void writeBufferDouble(Object arg0Value_, ByteOrder arg1Value, long arg2Value, double arg3Value) throws UnsupportedMessageException, InvalidBufferOffsetException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            JSArrayBufferObject.Interop arg0Value = (JSArrayBufferObject.Interop)arg0Value_;
            arg0Value.writeBufferDouble(arg1Value, arg2Value, arg3Value, BranchProfile.getUncached(), InteropGen.INTEROP_LIBRARY_.getUncached());
         }
      }
   }
}
