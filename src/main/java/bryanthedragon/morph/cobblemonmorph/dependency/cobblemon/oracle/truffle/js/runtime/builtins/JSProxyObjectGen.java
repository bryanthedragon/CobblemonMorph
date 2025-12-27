package com.oracle.truffle.js.runtime.builtins;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.interop.ArityException;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.interop.UnsupportedTypeException;
import com.oracle.truffle.api.library.LibraryExport;
import com.oracle.truffle.api.nodes.DenyReplace;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.js.nodes.interop.ExportValueNode;
import com.oracle.truffle.js.nodes.interop.JSInteropExecuteNode;
import com.oracle.truffle.js.nodes.interop.JSInteropExecuteNodeGen;
import com.oracle.truffle.js.nodes.interop.JSInteropGetIteratorNode;
import com.oracle.truffle.js.nodes.interop.JSInteropInstantiateNode;
import com.oracle.truffle.js.nodes.interop.JSInteropInstantiateNodeGen;
import com.oracle.truffle.js.nodes.interop.KeyInfoNode;
import com.oracle.truffle.js.nodes.unary.IsCallableNode;
import com.oracle.truffle.js.nodes.unary.IsCallableNodeGen;
import com.oracle.truffle.js.runtime.objects.JSObjectGen;
import java.util.concurrent.locks.Lock;

@GeneratedBy(JSProxyObject.class)
final class JSProxyObjectGen {
   private JSProxyObjectGen() {
   }

   static {
      LibraryExport.register(JSProxyObject.class, new JSProxyObjectGen.InteropLibraryExports());
   }

   @GeneratedBy(JSProxyObject.class)
   private static final class InteropLibraryExports extends LibraryExport<InteropLibrary> {
      private InteropLibraryExports() {
         super(InteropLibrary.class, JSProxyObject.class, false, false, 0);
      }

      protected InteropLibrary createUncached(Object receiver) {
         assert receiver instanceof JSProxyObject;

         InteropLibrary uncached = new JSProxyObjectGen.InteropLibraryExports.Uncached(receiver);
         return uncached;
      }

      protected InteropLibrary createCached(Object receiver) {
         assert receiver instanceof JSProxyObject;

         return new JSProxyObjectGen.InteropLibraryExports.Cached(receiver);
      }

      @GeneratedBy(JSProxyObject.class)
      private static final class Cached extends JSObjectGen.InteropLibraryExports.Cached {
         @CompilerDirectives.CompilationFinal
         private volatile int state_0_;
         @Node.Child
         private KeyInfoNode keyInfo;
         @Node.Child
         private JSInteropGetIteratorNode getIterator;
         @Node.Child
         private ExportValueNode exportValue;
         @Node.Child
         private IsCallableNode isExecutableNode__isExecutable_isCallable_;
         @Node.Child
         private JSInteropExecuteNode executeNode__execute_callNode_;
         @Node.Child
         private JSInteropInstantiateNode instantiateNode__instantiate_callNode_;

         protected Cached(Object receiver) {
            super(receiver);
         }

         @Override
         public boolean isExecutable(Object arg0Value_) {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            JSProxyObject arg0Value = (JSProxyObject)arg0Value_;
            int state_0 = this.state_0_;
            if ((state_0 & 1) != 0) {
               return arg0Value.isExecutable(this.isExecutableNode__isExecutable_isCallable_);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return this.isExecutableNode_AndSpecialize(arg0Value);
            }
         }

         private boolean isExecutableNode_AndSpecialize(JSProxyObject arg0Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            boolean var5;
            try {
               int state_0 = this.state_0_;
               this.isExecutableNode__isExecutable_isCallable_ = super.insert(IsCallableNode.create());
               int var9;
               this.state_0_ = var9 = state_0 | 1;
               lock.unlock();
               hasLock = false;
               var5 = arg0Value.isExecutable(this.isExecutableNode__isExecutable_isCallable_);
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
         public Object execute(Object arg0Value_, Object... arg1Value) throws UnsupportedTypeException, ArityException, UnsupportedMessageException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            JSProxyObject arg0Value = (JSProxyObject)arg0Value_;
            int state_0 = this.state_0_;
            if ((state_0 & 2) != 0) {
               return arg0Value.execute(arg1Value, this, this.executeNode__execute_callNode_, this.exportValue);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return this.executeNode_AndSpecialize(arg0Value, arg1Value);
            }
         }

         private Object executeNode_AndSpecialize(JSProxyObject arg0Value, Object[] arg1Value) throws UnsupportedMessageException {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            Object var7;
            try {
               int state_0 = this.state_0_;
               InteropLibrary executeNode__execute_self__ = null;
               this.executeNode__execute_callNode_ = super.insert(JSInteropExecuteNodeGen.create());
               this.exportValue = super.insert(this.exportValue == null ? ExportValueNode.create() : this.exportValue);
               int var11;
               this.state_0_ = var11 = state_0 | 2;
               lock.unlock();
               hasLock = false;
               var7 = arg0Value.execute(arg1Value, this, this.executeNode__execute_callNode_, this.exportValue);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }

            return var7;
         }

         @Override
         public boolean isInstantiable(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            return ((JSProxyObject)receiver).isInstantiable();
         }

         @Override
         public Object instantiate(Object arg0Value_, Object... arg1Value) throws UnsupportedTypeException, ArityException, UnsupportedMessageException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            JSProxyObject arg0Value = (JSProxyObject)arg0Value_;
            int state_0 = this.state_0_;
            if ((state_0 & 4) != 0) {
               return arg0Value.instantiate(arg1Value, this, this.instantiateNode__instantiate_callNode_, this.exportValue);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return this.instantiateNode_AndSpecialize(arg0Value, arg1Value);
            }
         }

         private Object instantiateNode_AndSpecialize(JSProxyObject arg0Value, Object[] arg1Value) throws UnsupportedMessageException {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            Object var7;
            try {
               int state_0 = this.state_0_;
               InteropLibrary instantiateNode__instantiate_self__ = null;
               this.instantiateNode__instantiate_callNode_ = super.insert(JSInteropInstantiateNodeGen.create());
               this.exportValue = super.insert(this.exportValue == null ? ExportValueNode.create() : this.exportValue);
               int var11;
               this.state_0_ = var11 = state_0 | 4;
               lock.unlock();
               hasLock = false;
               var7 = arg0Value.instantiate(arg1Value, this, this.instantiateNode__instantiate_callNode_, this.exportValue);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }

            return var7;
         }

         @Override
         public boolean hasMetaObject(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            return ((JSProxyObject)receiver).hasMetaObject();
         }

         @Override
         public Object getMetaObject(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            return ((JSProxyObject)receiver).getMetaObject();
         }
      }

      @GeneratedBy(JSProxyObject.class)
      @DenyReplace
      private static final class Uncached extends JSObjectGen.InteropLibraryExports.Uncached {
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
         public boolean isExecutable(Object arg0Value_) {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            JSProxyObject arg0Value = (JSProxyObject)arg0Value_;
            return arg0Value.isExecutable(IsCallableNodeGen.getUncached());
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Object execute(Object arg0Value_, Object... arg1Value) throws UnsupportedMessageException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            JSProxyObject arg0Value = (JSProxyObject)arg0Value_;
            return arg0Value.execute(arg1Value, this, JSInteropExecuteNodeGen.getUncached(), ExportValueNode.getUncached());
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean isInstantiable(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((JSProxyObject)receiver).isInstantiable();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Object instantiate(Object arg0Value_, Object... arg1Value) throws UnsupportedMessageException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            JSProxyObject arg0Value = (JSProxyObject)arg0Value_;
            return arg0Value.instantiate(arg1Value, this, JSInteropInstantiateNodeGen.getUncached(), ExportValueNode.getUncached());
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean hasMetaObject(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((JSProxyObject)receiver).hasMetaObject();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Object getMetaObject(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((JSProxyObject)receiver).getMetaObject();
         }
      }
   }
}
