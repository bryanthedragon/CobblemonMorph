package com.oracle.truffle.js.runtime.builtins;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.InvalidArrayIndexException;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.interop.UnsupportedTypeException;
import com.oracle.truffle.api.library.LibraryExport;
import com.oracle.truffle.api.nodes.DenyReplace;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.js.nodes.access.ReadElementNode;
import com.oracle.truffle.js.nodes.access.WriteElementNode;
import com.oracle.truffle.js.nodes.interop.ExportValueNode;
import com.oracle.truffle.js.nodes.interop.ImportValueNode;
import com.oracle.truffle.js.nodes.interop.JSInteropGetIteratorNode;
import com.oracle.truffle.js.nodes.interop.KeyInfoNode;
import com.oracle.truffle.js.runtime.objects.JSNonProxyObjectGen;
import com.oracle.truffle.js.runtime.objects.JSObject;
import java.util.concurrent.locks.Lock;

@GeneratedBy(JSTypedArrayObject.class)
final class JSTypedArrayObjectGen {
   private JSTypedArrayObjectGen() {
   }

   static {
      LibraryExport.register(JSTypedArrayObject.class, new JSTypedArrayObjectGen.InteropLibraryExports());
   }

   @GeneratedBy(JSTypedArrayObject.class)
   private static final class InteropLibraryExports extends LibraryExport<InteropLibrary> {
      private InteropLibraryExports() {
         super(InteropLibrary.class, JSTypedArrayObject.class, false, false, 0);
      }

      protected InteropLibrary createUncached(Object receiver) {
         assert receiver instanceof JSTypedArrayObject;

         InteropLibrary uncached = new JSTypedArrayObjectGen.InteropLibraryExports.Uncached(receiver);
         return uncached;
      }

      protected InteropLibrary createCached(Object receiver) {
         assert receiver instanceof JSTypedArrayObject;

         return new JSTypedArrayObjectGen.InteropLibraryExports.Cached(receiver);
      }

      @GeneratedBy(JSTypedArrayObject.class)
      private static final class Cached extends JSNonProxyObjectGen.InteropLibraryExports.Cached {
         @CompilerDirectives.CompilationFinal
         private volatile int state_0_;
         @Node.Child
         private KeyInfoNode keyInfo;
         @Node.Child
         private JSInteropGetIteratorNode getIterator;
         @Node.Child
         private ReadElementNode readArrayElementNode__readArrayElement_readNode_;
         @Node.Child
         private ExportValueNode readArrayElementNode__readArrayElement_exportNode_;
         @Node.Child
         private ImportValueNode writeArrayElementNode__writeArrayElement_castValueNode_;
         @Node.Child
         private WriteElementNode writeArrayElementNode__writeArrayElement_writeNode_;

         protected Cached(Object receiver) {
            super(receiver);
         }

         @Override
         public Object getMembers(Object receiver, boolean includeInternal) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            return ((JSTypedArrayObject)receiver).getMembers(includeInternal);
         }

         @Override
         public boolean hasArrayElements(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            return ((JSTypedArrayObject)receiver).hasArrayElements();
         }

         @Override
         public long getArraySize(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            return ((JSTypedArrayObject)receiver).getArraySize();
         }

         @Override
         public Object readArrayElement(Object arg0Value_, long arg1Value) throws UnsupportedMessageException, InvalidArrayIndexException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            JSTypedArrayObject arg0Value = (JSTypedArrayObject)arg0Value_;
            int state_0 = this.state_0_;
            if ((state_0 & 1) != 0) {
               return arg0Value.readArrayElement(
                  arg1Value, this, this.readArrayElementNode__readArrayElement_readNode_, this.readArrayElementNode__readArrayElement_exportNode_
               );
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return this.readArrayElementNode_AndSpecialize(arg0Value, arg1Value);
            }
         }

         private Object readArrayElementNode_AndSpecialize(JSTypedArrayObject arg0Value, long arg1Value) throws InvalidArrayIndexException, UnsupportedMessageException {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            Object var8;
            try {
               int state_0 = this.state_0_;
               InteropLibrary readArrayElementNode__readArrayElement_self__ = null;
               this.readArrayElementNode__readArrayElement_readNode_ = super.insert(ReadElementNode.create(JSObject.language(this).getJSContext()));
               this.readArrayElementNode__readArrayElement_exportNode_ = super.insert(ExportValueNode.create());
               int var12;
               this.state_0_ = var12 = state_0 | 1;
               lock.unlock();
               hasLock = false;
               var8 = arg0Value.readArrayElement(
                  arg1Value, this, this.readArrayElementNode__readArrayElement_readNode_, this.readArrayElementNode__readArrayElement_exportNode_
               );
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }

            return var8;
         }

         @Override
         public NodeCost getCost() {
            int state_0 = this.state_0_;
            return (state_0 & 1) == 0 ? NodeCost.UNINITIALIZED : NodeCost.MONOMORPHIC;
         }

         @Override
         public boolean isArrayElementReadable(Object arg0Value_, long arg1Value) {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            JSTypedArrayObject arg0Value = (JSTypedArrayObject)arg0Value_;
            return arg0Value.isArrayElementReadable(arg1Value, this);
         }

         @Override
         public boolean isArrayElementModifiable(Object arg0Value_, long arg1Value) {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            JSTypedArrayObject arg0Value = (JSTypedArrayObject)arg0Value_;
            return arg0Value.isArrayElementReadable(arg1Value, this);
         }

         @Override
         public void writeArrayElement(Object arg0Value_, long arg1Value, Object arg2Value) throws UnsupportedMessageException, UnsupportedTypeException, InvalidArrayIndexException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            JSTypedArrayObject arg0Value = (JSTypedArrayObject)arg0Value_;
            int state_0 = this.state_0_;
            if ((state_0 & 2) != 0) {
               arg0Value.writeArrayElement(
                  arg1Value,
                  arg2Value,
                  this.writeArrayElementNode__writeArrayElement_castValueNode_,
                  this.writeArrayElementNode__writeArrayElement_writeNode_,
                  this
               );
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               this.writeArrayElementNode_AndSpecialize(arg0Value, arg1Value, arg2Value);
            }
         }

         private void writeArrayElementNode_AndSpecialize(JSTypedArrayObject arg0Value, long arg1Value, Object arg2Value) throws InvalidArrayIndexException, UnsupportedMessageException {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            try {
               int state_0 = this.state_0_;
               InteropLibrary writeArrayElementNode__writeArrayElement_thisLibrary__ = null;
               this.writeArrayElementNode__writeArrayElement_castValueNode_ = super.insert(ImportValueNode.create());
               this.writeArrayElementNode__writeArrayElement_writeNode_ = super.insert(WriteElementNode.createCachedInterop());
               int var12;
               this.state_0_ = var12 = state_0 | 2;
               lock.unlock();
               hasLock = false;
               arg0Value.writeArrayElement(
                  arg1Value,
                  arg2Value,
                  this.writeArrayElementNode__writeArrayElement_castValueNode_,
                  this.writeArrayElementNode__writeArrayElement_writeNode_,
                  this
               );
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }
         }

         @Override
         public boolean isArrayElementInsertable(Object receiver, long index) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            return ((JSTypedArrayObject)receiver).isArrayElementInsertable(index);
         }
      }

      @GeneratedBy(JSTypedArrayObject.class)
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
         public Object getMembers(Object receiver, boolean includeInternal) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((JSTypedArrayObject)receiver).getMembers(includeInternal);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean hasArrayElements(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((JSTypedArrayObject)receiver).hasArrayElements();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public long getArraySize(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((JSTypedArrayObject)receiver).getArraySize();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Object readArrayElement(Object arg0Value_, long arg1Value) throws InvalidArrayIndexException, UnsupportedMessageException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            JSTypedArrayObject arg0Value = (JSTypedArrayObject)arg0Value_;
            return arg0Value.readArrayElement(arg1Value, this, JSObject.getUncachedRead(), ExportValueNode.getUncached());
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean isArrayElementReadable(Object arg0Value_, long arg1Value) {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            JSTypedArrayObject arg0Value = (JSTypedArrayObject)arg0Value_;
            return arg0Value.isArrayElementReadable(arg1Value, this);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean isArrayElementModifiable(Object arg0Value_, long arg1Value) {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            JSTypedArrayObject arg0Value = (JSTypedArrayObject)arg0Value_;
            return arg0Value.isArrayElementReadable(arg1Value, this);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public void writeArrayElement(Object arg0Value_, long arg1Value, Object arg2Value) throws InvalidArrayIndexException, UnsupportedMessageException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            JSTypedArrayObject arg0Value = (JSTypedArrayObject)arg0Value_;
            arg0Value.writeArrayElement(arg1Value, arg2Value, ImportValueNode.getUncached(), JSObject.getUncachedWrite(), this);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean isArrayElementInsertable(Object receiver, long index) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((JSTypedArrayObject)receiver).isArrayElementInsertable(index);
         }
      }
   }
}
