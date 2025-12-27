package com.oracle.truffle.js.runtime.builtins;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.interop.ExceptionType;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.library.LibraryExport;
import com.oracle.truffle.api.library.LibraryFactory;
import com.oracle.truffle.api.nodes.DenyReplace;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.utilities.TriState;
import com.oracle.truffle.js.nodes.interop.JSInteropGetIteratorNode;
import com.oracle.truffle.js.nodes.interop.KeyInfoNode;
import com.oracle.truffle.js.runtime.GraalJSException;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSNonProxyObjectGen;
import java.util.concurrent.locks.Lock;

@GeneratedBy(JSErrorObject.class)
final class JSErrorObjectGen {
   private static final LibraryFactory<InteropLibrary> INTEROP_LIBRARY_ = LibraryFactory.resolve(InteropLibrary.class);

   private JSErrorObjectGen() {
   }

   static {
      LibraryExport.register(JSErrorObject.class, new JSErrorObjectGen.InteropLibraryExports());
   }

   @GeneratedBy(JSErrorObject.class)
   private static final class InteropLibraryExports extends LibraryExport<InteropLibrary> {
      private InteropLibraryExports() {
         super(InteropLibrary.class, JSErrorObject.class, false, false, 0);
      }

      protected InteropLibrary createUncached(Object receiver) {
         assert receiver instanceof JSErrorObject;

         InteropLibrary uncached = new JSErrorObjectGen.InteropLibraryExports.Uncached(receiver);
         return uncached;
      }

      protected InteropLibrary createCached(Object receiver) {
         assert receiver instanceof JSErrorObject;

         return new JSErrorObjectGen.InteropLibraryExports.Cached(receiver);
      }

      @GeneratedBy(JSErrorObject.class)
      private static final class Cached extends JSNonProxyObjectGen.InteropLibraryExports.Cached {
         @CompilerDirectives.CompilationFinal
         private volatile int state_0_;
         @Node.Child
         private KeyInfoNode keyInfo;
         @Node.Child
         private JSInteropGetIteratorNode getIterator;
         @Node.Child
         private InteropLibrary getExceptionTypeNode__getExceptionType_exceptions_;
         @Node.Child
         private InteropLibrary isExceptionIncompleteSourceNode__isExceptionIncompleteSource_exceptions_;
         @Node.Child
         private InteropLibrary hasExceptionMessageNode__hasExceptionMessage_exceptions_;
         @Node.Child
         private InteropLibrary getExceptionMessageNode__getExceptionMessage_exceptions_;

         protected Cached(Object receiver) {
            super(receiver);
         }

         @Override
         protected TriState isIdenticalOrUndefined(Object arg0Value_, Object arg1Value) {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            JSErrorObject arg0Value = (JSErrorObject)arg0Value_;
            int state_0 = this.state_0_;
            if ((state_0 & 7) != 0) {
               if ((state_0 & 1) != 0 && arg1Value instanceof JSDynamicObject) {
                  JSDynamicObject arg1Value_ = (JSDynamicObject)arg1Value;
                  return JSErrorObject.IsIdenticalOrUndefined.doError(arg0Value, arg1Value_);
               }

               if ((state_0 & 2) != 0 && arg1Value instanceof GraalJSException) {
                  GraalJSException arg1Value_ = (GraalJSException)arg1Value;
                  return JSErrorObject.IsIdenticalOrUndefined.doException(arg0Value, arg1Value_);
               }

               if ((state_0 & 4) != 0 && isIdenticalOrUndefinedFallbackGuard_(state_0, arg0Value, arg1Value)) {
                  return JSErrorObject.IsIdenticalOrUndefined.doOther(arg0Value, arg1Value);
               }
            }

            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.isIdenticalOrUndefinedAndSpecialize(arg0Value, arg1Value);
         }

         private TriState isIdenticalOrUndefinedAndSpecialize(JSErrorObject arg0Value, Object arg1Value) {
            int state_0 = this.state_0_;
            if (arg1Value instanceof JSDynamicObject) {
               JSDynamicObject arg1Value_ = (JSDynamicObject)arg1Value;
               int var7;
               this.state_0_ = var7 = state_0 | 1;
               return JSErrorObject.IsIdenticalOrUndefined.doError(arg0Value, arg1Value_);
            } else if (arg1Value instanceof GraalJSException) {
               GraalJSException arg1Value_ = (GraalJSException)arg1Value;
               int var6;
               this.state_0_ = var6 = state_0 | 2;
               return JSErrorObject.IsIdenticalOrUndefined.doException(arg0Value, arg1Value_);
            } else {
               int var5;
               this.state_0_ = var5 = state_0 | 4;
               return JSErrorObject.IsIdenticalOrUndefined.doOther(arg0Value, arg1Value);
            }
         }

         @Override
         public NodeCost getCost() {
            int state_0 = this.state_0_;
            if ((state_0 & 7) == 0) {
               return NodeCost.UNINITIALIZED;
            } else {
               return (state_0 & 7 & (state_0 & 7) - 1) == 0 ? NodeCost.MONOMORPHIC : NodeCost.POLYMORPHIC;
            }
         }

         @Override
         public boolean isException(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            return ((JSErrorObject)receiver).isException();
         }

         @Override
         public RuntimeException throwException(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            return ((JSErrorObject)receiver).throwException();
         }

         @Override
         public ExceptionType getExceptionType(Object arg0Value_) throws UnsupportedMessageException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            JSErrorObject arg0Value = (JSErrorObject)arg0Value_;
            int state_0 = this.state_0_;
            if ((state_0 & 8) != 0) {
               return arg0Value.getExceptionType(this.getExceptionTypeNode__getExceptionType_exceptions_);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return this.getExceptionTypeNode_AndSpecialize(arg0Value);
            }
         }

         private ExceptionType getExceptionTypeNode_AndSpecialize(JSErrorObject arg0Value) throws UnsupportedMessageException {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            ExceptionType var5;
            try {
               int state_0 = this.state_0_;
               this.getExceptionTypeNode__getExceptionType_exceptions_ = super.insert(JSErrorObjectGen.INTEROP_LIBRARY_.createDispatched(5));
               int var9;
               this.state_0_ = var9 = state_0 | 8;
               lock.unlock();
               hasLock = false;
               var5 = arg0Value.getExceptionType(this.getExceptionTypeNode__getExceptionType_exceptions_);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }

            return var5;
         }

         @Override
         public boolean isExceptionIncompleteSource(Object arg0Value_) throws UnsupportedMessageException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            JSErrorObject arg0Value = (JSErrorObject)arg0Value_;
            int state_0 = this.state_0_;
            if ((state_0 & 16) != 0) {
               return arg0Value.isExceptionIncompleteSource(this.isExceptionIncompleteSourceNode__isExceptionIncompleteSource_exceptions_);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return this.isExceptionIncompleteSourceNode_AndSpecialize(arg0Value);
            }
         }

         private boolean isExceptionIncompleteSourceNode_AndSpecialize(JSErrorObject arg0Value) throws UnsupportedMessageException {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            boolean var5;
            try {
               int state_0 = this.state_0_;
               this.isExceptionIncompleteSourceNode__isExceptionIncompleteSource_exceptions_ = super.insert(
                  JSErrorObjectGen.INTEROP_LIBRARY_.createDispatched(5)
               );
               int var9;
               this.state_0_ = var9 = state_0 | 16;
               lock.unlock();
               hasLock = false;
               var5 = arg0Value.isExceptionIncompleteSource(this.isExceptionIncompleteSourceNode__isExceptionIncompleteSource_exceptions_);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }

            return var5;
         }

         @Override
         public boolean hasExceptionMessage(Object arg0Value_) {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            JSErrorObject arg0Value = (JSErrorObject)arg0Value_;
            int state_0 = this.state_0_;
            if ((state_0 & 32) != 0) {
               return arg0Value.hasExceptionMessage(this.hasExceptionMessageNode__hasExceptionMessage_exceptions_);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return this.hasExceptionMessageNode_AndSpecialize(arg0Value);
            }
         }

         private boolean hasExceptionMessageNode_AndSpecialize(JSErrorObject arg0Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            boolean var5;
            try {
               int state_0 = this.state_0_;
               this.hasExceptionMessageNode__hasExceptionMessage_exceptions_ = super.insert(JSErrorObjectGen.INTEROP_LIBRARY_.createDispatched(5));
               int var9;
               this.state_0_ = var9 = state_0 | 32;
               lock.unlock();
               hasLock = false;
               var5 = arg0Value.hasExceptionMessage(this.hasExceptionMessageNode__hasExceptionMessage_exceptions_);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }

            return var5;
         }

         @Override
         public Object getExceptionMessage(Object arg0Value_) throws UnsupportedMessageException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            JSErrorObject arg0Value = (JSErrorObject)arg0Value_;
            int state_0 = this.state_0_;
            if ((state_0 & 64) != 0) {
               return arg0Value.getExceptionMessage(this.getExceptionMessageNode__getExceptionMessage_exceptions_);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return this.getExceptionMessageNode_AndSpecialize(arg0Value);
            }
         }

         private Object getExceptionMessageNode_AndSpecialize(JSErrorObject arg0Value) throws UnsupportedMessageException {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            Object var5;
            try {
               int state_0 = this.state_0_;
               this.getExceptionMessageNode__getExceptionMessage_exceptions_ = super.insert(JSErrorObjectGen.INTEROP_LIBRARY_.createDispatched(5));
               int var9;
               this.state_0_ = var9 = state_0 | 64;
               lock.unlock();
               hasLock = false;
               var5 = arg0Value.getExceptionMessage(this.getExceptionMessageNode__getExceptionMessage_exceptions_);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }

            return var5;
         }

         private static boolean isIdenticalOrUndefinedFallbackGuard_(int state_0, JSErrorObject arg0Value, Object arg1Value) {
            return (state_0 & 1) == 0 && arg1Value instanceof JSDynamicObject ? false : (state_0 & 2) != 0 || !(arg1Value instanceof GraalJSException);
         }
      }

      @GeneratedBy(JSErrorObject.class)
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
         public TriState isIdenticalOrUndefined(Object arg0Value_, Object arg1Value) {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            JSErrorObject arg0Value = (JSErrorObject)arg0Value_;
            if (arg1Value instanceof JSDynamicObject) {
               JSDynamicObject arg1Value_ = (JSDynamicObject)arg1Value;
               return JSErrorObject.IsIdenticalOrUndefined.doError(arg0Value, arg1Value_);
            } else if (arg1Value instanceof GraalJSException) {
               GraalJSException arg1Value_ = (GraalJSException)arg1Value;
               return JSErrorObject.IsIdenticalOrUndefined.doException(arg0Value, arg1Value_);
            } else {
               return JSErrorObject.IsIdenticalOrUndefined.doOther(arg0Value, arg1Value);
            }
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean isException(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((JSErrorObject)receiver).isException();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public RuntimeException throwException(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((JSErrorObject)receiver).throwException();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public ExceptionType getExceptionType(Object arg0Value_) throws UnsupportedMessageException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            JSErrorObject arg0Value = (JSErrorObject)arg0Value_;
            return arg0Value.getExceptionType(JSErrorObjectGen.INTEROP_LIBRARY_.getUncached());
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean isExceptionIncompleteSource(Object arg0Value_) throws UnsupportedMessageException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            JSErrorObject arg0Value = (JSErrorObject)arg0Value_;
            return arg0Value.isExceptionIncompleteSource(JSErrorObjectGen.INTEROP_LIBRARY_.getUncached());
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean hasExceptionMessage(Object arg0Value_) {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            JSErrorObject arg0Value = (JSErrorObject)arg0Value_;
            return arg0Value.hasExceptionMessage(JSErrorObjectGen.INTEROP_LIBRARY_.getUncached());
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Object getExceptionMessage(Object arg0Value_) throws UnsupportedMessageException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            JSErrorObject arg0Value = (JSErrorObject)arg0Value_;
            return arg0Value.getExceptionMessage(JSErrorObjectGen.INTEROP_LIBRARY_.getUncached());
         }
      }
   }
}
