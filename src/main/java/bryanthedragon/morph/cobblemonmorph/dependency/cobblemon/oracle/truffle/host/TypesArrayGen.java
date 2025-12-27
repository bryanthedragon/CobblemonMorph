package com.oracle.truffle.host;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.InvalidArrayIndexException;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.library.DynamicDispatchLibrary;
import com.oracle.truffle.api.library.LibraryExport;
import com.oracle.truffle.api.library.LibraryFactory;
import com.oracle.truffle.api.nodes.DenyReplace;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.profiles.BranchProfile;
import java.util.concurrent.locks.Lock;

@GeneratedBy(HostObject.TypesArray.class)
final class TypesArrayGen {
   private static final LibraryFactory<DynamicDispatchLibrary> DYNAMIC_DISPATCH_LIBRARY_ = LibraryFactory.resolve(DynamicDispatchLibrary.class);

   private TypesArrayGen() {
   }

   static {
      LibraryExport.register(HostObject.TypesArray.class, new TypesArrayGen.InteropLibraryExports());
   }

   @GeneratedBy(HostObject.TypesArray.class)
   private static final class InteropLibraryExports extends LibraryExport<InteropLibrary> {
      private InteropLibraryExports() {
         super(InteropLibrary.class, HostObject.TypesArray.class, false, false, 0);
      }

      protected InteropLibrary createUncached(Object receiver) {
         assert receiver instanceof HostObject.TypesArray;

         InteropLibrary uncached = new TypesArrayGen.InteropLibraryExports.Uncached();
         return uncached;
      }

      protected InteropLibrary createCached(Object receiver) {
         assert receiver instanceof HostObject.TypesArray;

         return new TypesArrayGen.InteropLibraryExports.Cached();
      }

      @GeneratedBy(HostObject.TypesArray.class)
      private static final class Cached extends InteropLibrary {
         @CompilerDirectives.CompilationFinal
         private volatile int state_0_;
         @CompilerDirectives.CompilationFinal
         private BranchProfile error_;

         protected Cached() {
         }

         @Override
         public boolean accepts(Object receiver) {
            assert !(receiver instanceof HostObject.TypesArray) || TypesArrayGen.DYNAMIC_DISPATCH_LIBRARY_.getUncached().dispatch(receiver) == null : "Invalid library export. Exported receiver with dynamic dispatch found but not expected.";

            return receiver instanceof HostObject.TypesArray;
         }

         @Override
         public boolean hasArrayElements(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            return ((HostObject.TypesArray)receiver).hasArrayElements();
         }

         @Override
         public long getArraySize(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            return ((HostObject.TypesArray)receiver).getArraySize();
         }

         @Override
         public boolean isArrayElementReadable(Object receiver, long index) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            return ((HostObject.TypesArray)receiver).isArrayElementReadable(index);
         }

         @Override
         public Object readArrayElement(Object arg0Value_, long arg1Value) throws UnsupportedMessageException, InvalidArrayIndexException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            HostObject.TypesArray arg0Value = (HostObject.TypesArray)arg0Value_;
            int state_0 = this.state_0_;
            if (state_0 != 0) {
               return arg0Value.readArrayElement(arg1Value, this.error_);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return this.executeAndSpecialize(arg0Value, arg1Value);
            }
         }

         private Object executeAndSpecialize(HostObject.TypesArray arg0Value, long arg1Value) throws InvalidArrayIndexException {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            Object var7;
            try {
               int state_0 = this.state_0_;
               this.error_ = BranchProfile.create();
               int var11;
               this.state_0_ = var11 = state_0 | 1;
               lock.unlock();
               hasLock = false;
               var7 = arg0Value.readArrayElement(arg1Value, this.error_);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }

            return var7;
         }

         @Override
         public NodeCost getCost() {
            int state_0 = this.state_0_;
            return state_0 == 0 ? NodeCost.UNINITIALIZED : NodeCost.MONOMORPHIC;
         }
      }

      @GeneratedBy(HostObject.TypesArray.class)
      @DenyReplace
      private static final class Uncached extends InteropLibrary {
         protected Uncached() {
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean accepts(Object receiver) {
            assert !(receiver instanceof HostObject.TypesArray) || TypesArrayGen.DYNAMIC_DISPATCH_LIBRARY_.getUncached().dispatch(receiver) == null : "Invalid library export. Exported receiver with dynamic dispatch found but not expected.";

            return receiver instanceof HostObject.TypesArray;
         }

         @Override
         public boolean isAdoptable() {
            return false;
         }

         @Override
         public NodeCost getCost() {
            return NodeCost.MEGAMORPHIC;
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean hasArrayElements(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((HostObject.TypesArray)receiver).hasArrayElements();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public long getArraySize(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((HostObject.TypesArray)receiver).getArraySize();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean isArrayElementReadable(Object receiver, long index) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((HostObject.TypesArray)receiver).isArrayElementReadable(index);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Object readArrayElement(Object arg0Value_, long arg1Value) throws InvalidArrayIndexException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            HostObject.TypesArray arg0Value = (HostObject.TypesArray)arg0Value_;
            return arg0Value.readArrayElement(arg1Value, BranchProfile.getUncached());
         }
      }
   }
}
