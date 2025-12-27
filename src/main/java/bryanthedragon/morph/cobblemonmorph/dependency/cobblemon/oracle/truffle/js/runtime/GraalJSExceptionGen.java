package com.oracle.truffle.js.runtime;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.library.DynamicDispatchLibrary;
import com.oracle.truffle.api.library.LibraryExport;
import com.oracle.truffle.api.library.LibraryFactory;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.source.SourceSection;
import com.oracle.truffle.api.utilities.TriState;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.util.concurrent.locks.Lock;

@GeneratedBy(GraalJSException.class)
public final class GraalJSExceptionGen {
   private static final LibraryFactory<DynamicDispatchLibrary> DYNAMIC_DISPATCH_LIBRARY_ = LibraryFactory.resolve(DynamicDispatchLibrary.class);
   private static final LibraryFactory<InteropLibrary> INTEROP_LIBRARY_ = LibraryFactory.resolve(InteropLibrary.class);

   private GraalJSExceptionGen() {
   }

   static {
      LibraryExport.register(GraalJSException.class, new GraalJSExceptionGen.InteropLibraryExports());
   }

   @GeneratedBy(GraalJSException.class)
   public static class InteropLibraryExports extends LibraryExport<InteropLibrary> {
      private InteropLibraryExports() {
         super(InteropLibrary.class, GraalJSException.class, false, false, 0);
      }

      protected InteropLibrary createUncached(Object receiver) {
         assert receiver instanceof GraalJSException;

         InteropLibrary uncached = new GraalJSExceptionGen.InteropLibraryExports.Uncached(receiver);
         return uncached;
      }

      protected InteropLibrary createCached(Object receiver) {
         assert receiver instanceof GraalJSException;

         return new GraalJSExceptionGen.InteropLibraryExports.Cached(receiver);
      }

      @GeneratedBy(GraalJSException.class)
      public static class Cached extends InteropLibrary {
         private final Class<? extends GraalJSException> receiverClass_;
         @CompilerDirectives.CompilationFinal
         private volatile int state_0_;
         @CompilerDirectives.CompilationFinal
         private volatile int exclude_;
         @Node.Child
         private InteropLibrary thisLib;
         @Node.Child
         private InteropLibrary otherLib;
         @Node.Child
         private InteropLibrary identityHashCodeNode__identityHashCode_delegateLib_;

         protected Cached(Object receiver) {
            GraalJSException castReceiver = (GraalJSException)receiver;
            this.receiverClass_ = (Class<? extends GraalJSException>)castReceiver.getClass();
         }

         @Override
         public boolean accepts(Object receiver) {
            assert receiver.getClass() != this.receiverClass_ || GraalJSExceptionGen.DYNAMIC_DISPATCH_LIBRARY_.getUncached().dispatch(receiver) == null : "Invalid library export. Exported receiver with dynamic dispatch found but not expected.";

            return CompilerDirectives.isExact(receiver, this.receiverClass_);
         }

         @Override
         protected TriState isIdenticalOrUndefined(Object arg0Value_, Object arg1Value) {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            GraalJSException arg0Value = CompilerDirectives.castExact(arg0Value_, this.receiverClass_);
            int state_0 = this.state_0_;
            if ((state_0 & 7) != 0) {
               if ((state_0 & 1) != 0 && arg1Value instanceof GraalJSException) {
                  GraalJSException arg1Value_ = (GraalJSException)arg1Value;
                  return GraalJSException.IsIdenticalOrUndefined.doException(arg0Value, arg1Value_, this.thisLib, this.otherLib);
               }

               if ((state_0 & 2) != 0 && arg1Value instanceof JSDynamicObject) {
                  JSDynamicObject arg1Value_ = (JSDynamicObject)arg1Value;
                  return GraalJSException.IsIdenticalOrUndefined.doJSObject(arg0Value, arg1Value_);
               }

               if ((state_0 & 4) != 0 && !GraalJSException.IsIdenticalOrUndefined.isGraalJSException(arg1Value)) {
                  return GraalJSException.IsIdenticalOrUndefined.doOther(arg0Value, arg1Value, this.thisLib, this.otherLib);
               }
            }

            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.isIdenticalOrUndefinedAndSpecialize(arg0Value, arg1Value);
         }

         private TriState isIdenticalOrUndefinedAndSpecialize(GraalJSException arg0Value, Object arg1Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            TriState arg1Value_;
            try {
               int state_0 = this.state_0_;
               int exclude = this.exclude_;
               if (arg1Value instanceof GraalJSException) {
                  GraalJSException arg1Value_x = (GraalJSException)arg1Value;
                  this.thisLib = super.insert(this.thisLib == null ? GraalJSExceptionGen.INTEROP_LIBRARY_.createDispatched(5) : this.thisLib);
                  this.otherLib = super.insert(this.otherLib == null ? GraalJSExceptionGen.INTEROP_LIBRARY_.createDispatched(5) : this.otherLib);
                  int var15;
                  this.state_0_ = var15 = state_0 | 1;
                  lock.unlock();
                  hasLock = false;
                  return GraalJSException.IsIdenticalOrUndefined.doException(arg0Value, arg1Value_x, this.thisLib, this.otherLib);
               }

               if (exclude == 0 && arg1Value instanceof JSDynamicObject) {
                  JSDynamicObject arg1Value_x = (JSDynamicObject)arg1Value;
                  int var14;
                  this.state_0_ = var14 = state_0 | 2;
                  lock.unlock();
                  hasLock = false;
                  return GraalJSException.IsIdenticalOrUndefined.doJSObject(arg0Value, arg1Value_x);
               }

               if (GraalJSException.IsIdenticalOrUndefined.isGraalJSException(arg1Value)) {
                  throw new UnsupportedSpecializationException(this, new Node[]{null, null}, arg0Value, arg1Value);
               }

               this.thisLib = super.insert(this.thisLib == null ? GraalJSExceptionGen.INTEROP_LIBRARY_.createDispatched(5) : this.thisLib);
               this.otherLib = super.insert(this.otherLib == null ? GraalJSExceptionGen.INTEROP_LIBRARY_.createDispatched(5) : this.otherLib);
               int var16;
               this.exclude_ = var16 = exclude | 1;
               state_0 &= -3;
               int var13;
               this.state_0_ = var13 = state_0 | 4;
               lock.unlock();
               hasLock = false;
               arg1Value_ = GraalJSException.IsIdenticalOrUndefined.doOther(arg0Value, arg1Value, this.thisLib, this.otherLib);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }

            return arg1Value_;
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
         public boolean hasSourceLocation(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            return CompilerDirectives.castExact(receiver, this.receiverClass_).hasSourceLocation();
         }

         @Override
         public SourceSection getSourceLocation(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            return CompilerDirectives.castExact(receiver, this.receiverClass_).getSourceLocationInterop();
         }

         @Override
         public boolean hasLanguage(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            return CompilerDirectives.castExact(receiver, this.receiverClass_).hasLanguage();
         }

         @Override
         public Class<? extends TruffleLanguage<?>> getLanguage(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            return CompilerDirectives.castExact(receiver, this.receiverClass_).getLanguage();
         }

         @Override
         public Object toDisplayString(Object receiver, boolean allowSideEffects) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            return CompilerDirectives.castExact(receiver, this.receiverClass_).toDisplayString(allowSideEffects);
         }

         @Override
         public int identityHashCode(Object arg0Value_) throws UnsupportedMessageException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            GraalJSException arg0Value = CompilerDirectives.castExact(arg0Value_, this.receiverClass_);
            int state_0 = this.state_0_;
            if ((state_0 & 8) != 0) {
               return arg0Value.identityHashCode(this.identityHashCodeNode__identityHashCode_delegateLib_);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return this.identityHashCodeNode_AndSpecialize(arg0Value);
            }
         }

         private int identityHashCodeNode_AndSpecialize(GraalJSException arg0Value) throws UnsupportedMessageException {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            int var5;
            try {
               int state_0 = this.state_0_;
               this.identityHashCodeNode__identityHashCode_delegateLib_ = super.insert(GraalJSExceptionGen.INTEROP_LIBRARY_.createDispatched(5));
               int var9;
               this.state_0_ = var9 = state_0 | 8;
               lock.unlock();
               hasLock = false;
               var5 = arg0Value.identityHashCode(this.identityHashCodeNode__identityHashCode_delegateLib_);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }

            return var5;
         }
      }

      @GeneratedBy(GraalJSException.class)
      public static class Uncached extends InteropLibrary {
         private final Class<? extends GraalJSException> receiverClass_;

         protected Uncached(Object receiver) {
            this.receiverClass_ = (Class<? extends GraalJSException>)((GraalJSException)receiver).getClass();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean accepts(Object receiver) {
            assert receiver.getClass() != this.receiverClass_ || GraalJSExceptionGen.DYNAMIC_DISPATCH_LIBRARY_.getUncached().dispatch(receiver) == null : "Invalid library export. Exported receiver with dynamic dispatch found but not expected.";

            return CompilerDirectives.isExact(receiver, this.receiverClass_);
         }

         @Override
         public final boolean isAdoptable() {
            return false;
         }

         @Override
         public final NodeCost getCost() {
            return NodeCost.MEGAMORPHIC;
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public TriState isIdenticalOrUndefined(Object arg0Value_, Object arg1Value) {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            GraalJSException arg0Value = (GraalJSException)arg0Value_;
            if (arg1Value instanceof GraalJSException) {
               GraalJSException arg1Value_ = (GraalJSException)arg1Value;
               return GraalJSException.IsIdenticalOrUndefined.doException(
                  arg0Value, arg1Value_, GraalJSExceptionGen.INTEROP_LIBRARY_.getUncached(), GraalJSExceptionGen.INTEROP_LIBRARY_.getUncached()
               );
            } else if (!GraalJSException.IsIdenticalOrUndefined.isGraalJSException(arg1Value)) {
               return GraalJSException.IsIdenticalOrUndefined.doOther(
                  arg0Value, arg1Value, GraalJSExceptionGen.INTEROP_LIBRARY_.getUncached(), GraalJSExceptionGen.INTEROP_LIBRARY_.getUncached()
               );
            } else {
               throw new UnsupportedSpecializationException(this, new Node[]{null, null}, arg0Value, arg1Value);
            }
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean hasSourceLocation(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((GraalJSException)receiver).hasSourceLocation();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public SourceSection getSourceLocation(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((GraalJSException)receiver).getSourceLocationInterop();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean hasLanguage(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((GraalJSException)receiver).hasLanguage();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Class<? extends TruffleLanguage<?>> getLanguage(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((GraalJSException)receiver).getLanguage();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Object toDisplayString(Object receiver, boolean allowSideEffects) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((GraalJSException)receiver).toDisplayString(allowSideEffects);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public int identityHashCode(Object arg0Value_) throws UnsupportedMessageException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            GraalJSException arg0Value = (GraalJSException)arg0Value_;
            return arg0Value.identityHashCode(GraalJSExceptionGen.INTEROP_LIBRARY_.getUncached());
         }
      }
   }
}
