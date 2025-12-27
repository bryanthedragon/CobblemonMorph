package com.oracle.truffle.js.runtime.builtins;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.InvalidArrayIndexException;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.interop.UnsupportedTypeException;
import com.oracle.truffle.api.library.LibraryExport;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.js.nodes.access.ReadElementNode;
import com.oracle.truffle.js.nodes.access.WriteElementNode;
import com.oracle.truffle.js.nodes.interop.ArrayElementInfoNode;
import com.oracle.truffle.js.nodes.interop.ArrayElementInfoNodeGen;
import com.oracle.truffle.js.nodes.interop.ExportValueNode;
import com.oracle.truffle.js.nodes.interop.ImportValueNode;
import com.oracle.truffle.js.nodes.interop.JSInteropGetIteratorNode;
import com.oracle.truffle.js.nodes.interop.KeyInfoNode;
import com.oracle.truffle.js.runtime.objects.JSNonProxyObjectGen;
import com.oracle.truffle.js.runtime.objects.JSObject;
import java.util.concurrent.locks.Lock;

@GeneratedBy(JSArgumentsObject.class)
public final class JSArgumentsObjectGen {
   private JSArgumentsObjectGen() {
   }

   static {
      LibraryExport.register(JSArgumentsObject.class, new JSArgumentsObjectGen.InteropLibraryExports());
   }

   @GeneratedBy(JSArgumentsObject.class)
   public static class InteropLibraryExports extends LibraryExport<InteropLibrary> {
      private InteropLibraryExports() {
         super(InteropLibrary.class, JSArgumentsObject.class, false, false, 0);
      }

      protected InteropLibrary createUncached(Object receiver) {
         assert receiver instanceof JSArgumentsObject;

         InteropLibrary uncached = new JSArgumentsObjectGen.InteropLibraryExports.Uncached(receiver);
         return uncached;
      }

      protected InteropLibrary createCached(Object receiver) {
         assert receiver instanceof JSArgumentsObject;

         return new JSArgumentsObjectGen.InteropLibraryExports.Cached(receiver);
      }

      @GeneratedBy(JSArgumentsObject.class)
      public static class Cached extends JSNonProxyObjectGen.InteropLibraryExports.Cached {
         @CompilerDirectives.CompilationFinal
         private volatile int state_0_;
         @Node.Child
         private KeyInfoNode keyInfo;
         @Node.Child
         private JSInteropGetIteratorNode getIterator;
         @Node.Child
         private ArrayElementInfoNode elementInfo;
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

            return ((JSArgumentsObject)receiver).getMembers(includeInternal);
         }

         @Override
         public boolean hasArrayElements(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            return ((JSArgumentsObject)receiver).hasArrayElements();
         }

         @Override
         public long getArraySize(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            return ((JSArgumentsObject)receiver).getArraySize();
         }

         @Override
         public Object readArrayElement(Object arg0Value_, long arg1Value) throws UnsupportedMessageException, InvalidArrayIndexException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            JSArgumentsObject arg0Value = (JSArgumentsObject)arg0Value_;
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

         private Object readArrayElementNode_AndSpecialize(JSArgumentsObject arg0Value, long arg1Value) throws InvalidArrayIndexException, UnsupportedMessageException {
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

            JSArgumentsObject arg0Value = (JSArgumentsObject)arg0Value_;
            return arg0Value.isArrayElementReadable(arg1Value, this);
         }

         @Override
         public void writeArrayElement(Object arg0Value_, long arg1Value, Object arg2Value) throws UnsupportedMessageException, UnsupportedTypeException, InvalidArrayIndexException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            JSArgumentsObject arg0Value = (JSArgumentsObject)arg0Value_;
            int state_0 = this.state_0_;
            if ((state_0 & 2) != 0) {
               arg0Value.writeArrayElement(
                  arg1Value,
                  arg2Value,
                  this.elementInfo,
                  this.writeArrayElementNode__writeArrayElement_castValueNode_,
                  this.writeArrayElementNode__writeArrayElement_writeNode_
               );
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               this.writeArrayElementNode_AndSpecialize(arg0Value, arg1Value, arg2Value);
            }
         }

         private void writeArrayElementNode_AndSpecialize(JSArgumentsObject arg0Value, long arg1Value, Object arg2Value) throws InvalidArrayIndexException, UnsupportedMessageException {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            try {
               int state_0 = this.state_0_;
               this.elementInfo = super.insert(this.elementInfo == null ? ArrayElementInfoNodeGen.create() : this.elementInfo);
               this.writeArrayElementNode__writeArrayElement_castValueNode_ = super.insert(ImportValueNode.create());
               this.writeArrayElementNode__writeArrayElement_writeNode_ = super.insert(WriteElementNode.createCachedInterop());
               int var11;
               this.state_0_ = var11 = state_0 | 2;
               lock.unlock();
               hasLock = false;
               arg0Value.writeArrayElement(
                  arg1Value,
                  arg2Value,
                  this.elementInfo,
                  this.writeArrayElementNode__writeArrayElement_castValueNode_,
                  this.writeArrayElementNode__writeArrayElement_writeNode_
               );
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }
         }

         @Override
         public boolean isArrayElementModifiable(Object arg0Value_, long arg1Value) {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            JSArgumentsObject arg0Value = (JSArgumentsObject)arg0Value_;
            int state_0 = this.state_0_;
            if ((state_0 & 4) != 0) {
               return arg0Value.isArrayElementModifiable(arg1Value, this.elementInfo);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return this.isArrayElementModifiableNode_AndSpecialize(arg0Value, arg1Value);
            }
         }

         private boolean isArrayElementModifiableNode_AndSpecialize(JSArgumentsObject arg0Value, long arg1Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            boolean var7;
            try {
               int state_0 = this.state_0_;
               this.elementInfo = super.insert(this.elementInfo == null ? ArrayElementInfoNodeGen.create() : this.elementInfo);
               int var11;
               this.state_0_ = var11 = state_0 | 4;
               lock.unlock();
               hasLock = false;
               var7 = arg0Value.isArrayElementModifiable(arg1Value, this.elementInfo);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }

            return var7;
         }

         @Override
         public boolean isArrayElementInsertable(Object arg0Value_, long arg1Value) {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            JSArgumentsObject arg0Value = (JSArgumentsObject)arg0Value_;
            int state_0 = this.state_0_;
            if ((state_0 & 8) != 0) {
               return arg0Value.isArrayElementInsertable(arg1Value, this.elementInfo);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return this.isArrayElementInsertableNode_AndSpecialize(arg0Value, arg1Value);
            }
         }

         private boolean isArrayElementInsertableNode_AndSpecialize(JSArgumentsObject arg0Value, long arg1Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            boolean var7;
            try {
               int state_0 = this.state_0_;
               this.elementInfo = super.insert(this.elementInfo == null ? ArrayElementInfoNodeGen.create() : this.elementInfo);
               int var11;
               this.state_0_ = var11 = state_0 | 8;
               lock.unlock();
               hasLock = false;
               var7 = arg0Value.isArrayElementInsertable(arg1Value, this.elementInfo);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }

            return var7;
         }
      }

      @GeneratedBy(JSArgumentsObject.class)
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
         public Object getMembers(Object receiver, boolean includeInternal) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((JSArgumentsObject)receiver).getMembers(includeInternal);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean hasArrayElements(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((JSArgumentsObject)receiver).hasArrayElements();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public long getArraySize(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((JSArgumentsObject)receiver).getArraySize();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Object readArrayElement(Object arg0Value_, long arg1Value) throws InvalidArrayIndexException, UnsupportedMessageException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            JSArgumentsObject arg0Value = (JSArgumentsObject)arg0Value_;
            return arg0Value.readArrayElement(arg1Value, this, JSObject.getUncachedRead(), ExportValueNode.getUncached());
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean isArrayElementReadable(Object arg0Value_, long arg1Value) {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            JSArgumentsObject arg0Value = (JSArgumentsObject)arg0Value_;
            return arg0Value.isArrayElementReadable(arg1Value, this);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public void writeArrayElement(Object arg0Value_, long arg1Value, Object arg2Value) throws InvalidArrayIndexException, UnsupportedMessageException, UnsupportedTypeException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            JSArgumentsObject arg0Value = (JSArgumentsObject)arg0Value_;
            arg0Value.writeArrayElement(arg1Value, arg2Value, ArrayElementInfoNodeGen.getUncached(), ImportValueNode.getUncached(), JSObject.getUncachedWrite());
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean isArrayElementModifiable(Object arg0Value_, long arg1Value) {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            JSArgumentsObject arg0Value = (JSArgumentsObject)arg0Value_;
            return arg0Value.isArrayElementModifiable(arg1Value, ArrayElementInfoNodeGen.getUncached());
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean isArrayElementInsertable(Object arg0Value_, long arg1Value) {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            JSArgumentsObject arg0Value = (JSArgumentsObject)arg0Value_;
            return arg0Value.isArrayElementInsertable(arg1Value, ArrayElementInfoNodeGen.getUncached());
         }
      }
   }
}
