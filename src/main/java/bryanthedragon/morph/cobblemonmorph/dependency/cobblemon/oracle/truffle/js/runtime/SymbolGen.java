package com.oracle.truffle.js.runtime;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.library.DynamicDispatchLibrary;
import com.oracle.truffle.api.library.LibraryExport;
import com.oracle.truffle.api.library.LibraryFactory;
import com.oracle.truffle.api.nodes.DenyReplace;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.utilities.TriState;

@GeneratedBy(Symbol.class)
final class SymbolGen {
   private static final LibraryFactory<DynamicDispatchLibrary> DYNAMIC_DISPATCH_LIBRARY_ = LibraryFactory.resolve(DynamicDispatchLibrary.class);

   private SymbolGen() {
   }

   static {
      LibraryExport.register(Symbol.class, new SymbolGen.InteropLibraryExports());
   }

   @GeneratedBy(Symbol.class)
   private static final class InteropLibraryExports extends LibraryExport<InteropLibrary> {
      private InteropLibraryExports() {
         super(InteropLibrary.class, Symbol.class, false, false, 0);
      }

      protected InteropLibrary createUncached(Object receiver) {
         assert receiver instanceof Symbol;

         InteropLibrary uncached = new SymbolGen.InteropLibraryExports.Uncached();
         return uncached;
      }

      protected InteropLibrary createCached(Object receiver) {
         assert receiver instanceof Symbol;

         return new SymbolGen.InteropLibraryExports.Cached();
      }

      @GeneratedBy(Symbol.class)
      private static final class Cached extends InteropLibrary {
         @CompilerDirectives.CompilationFinal
         private int state_0_;

         protected Cached() {
         }

         @Override
         public boolean accepts(Object receiver) {
            assert !(receiver instanceof Symbol) || SymbolGen.DYNAMIC_DISPATCH_LIBRARY_.getUncached().dispatch(receiver) == null : "Invalid library export. Exported receiver with dynamic dispatch found but not expected.";

            return receiver instanceof Symbol;
         }

         @Override
         protected TriState isIdenticalOrUndefined(Object arg0Value_, Object arg1Value) {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            Symbol arg0Value = (Symbol)arg0Value_;
            int state_0 = this.state_0_;
            if (state_0 != 0) {
               if ((state_0 & 1) != 0 && arg1Value instanceof Symbol) {
                  Symbol arg1Value_ = (Symbol)arg1Value;
                  return Symbol.IsIdenticalOrUndefined.doHostObject(arg0Value, arg1Value_);
               }

               if ((state_0 & 2) != 0 && fallbackGuard_(state_0, arg0Value, arg1Value)) {
                  return Symbol.IsIdenticalOrUndefined.doOther(arg0Value, arg1Value);
               }
            }

            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value, arg1Value);
         }

         private TriState executeAndSpecialize(Symbol arg0Value, Object arg1Value) {
            int state_0 = this.state_0_;
            if (arg1Value instanceof Symbol) {
               Symbol arg1Value_ = (Symbol)arg1Value;
               int var6;
               this.state_0_ = var6 = state_0 | 1;
               return Symbol.IsIdenticalOrUndefined.doHostObject(arg0Value, arg1Value_);
            } else {
               int var5;
               this.state_0_ = var5 = state_0 | 2;
               return Symbol.IsIdenticalOrUndefined.doOther(arg0Value, arg1Value);
            }
         }

         @Override
         public NodeCost getCost() {
            int state_0 = this.state_0_;
            if (state_0 == 0) {
               return NodeCost.UNINITIALIZED;
            } else {
               return (state_0 & state_0 - 1) == 0 ? NodeCost.MONOMORPHIC : NodeCost.POLYMORPHIC;
            }
         }

         @Override
         public boolean hasLanguage(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            return ((Symbol)receiver).hasLanguage();
         }

         @Override
         public Class<? extends TruffleLanguage<?>> getLanguage(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            return ((Symbol)receiver).getLanguage();
         }

         @Override
         public Object toDisplayString(Object receiver, boolean allowSideEffects) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            return ((Symbol)receiver).toDisplayString(allowSideEffects);
         }

         @Override
         public boolean hasMetaObject(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            return ((Symbol)receiver).hasMetaObject();
         }

         @Override
         public Object getMetaObject(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            return ((Symbol)receiver).getMetaObject();
         }

         @Override
         public int identityHashCode(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            return ((Symbol)receiver).identityHashCode();
         }

         private static boolean fallbackGuard_(int state_0, Symbol arg0Value, Object arg1Value) {
            return (state_0 & 1) != 0 || !(arg1Value instanceof Symbol);
         }
      }

      @GeneratedBy(Symbol.class)
      @DenyReplace
      private static final class Uncached extends InteropLibrary {
         protected Uncached() {
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean accepts(Object receiver) {
            assert !(receiver instanceof Symbol) || SymbolGen.DYNAMIC_DISPATCH_LIBRARY_.getUncached().dispatch(receiver) == null : "Invalid library export. Exported receiver with dynamic dispatch found but not expected.";

            return receiver instanceof Symbol;
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
         public TriState isIdenticalOrUndefined(Object arg0Value_, Object arg1Value) {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            Symbol arg0Value = (Symbol)arg0Value_;
            if (arg1Value instanceof Symbol) {
               Symbol arg1Value_ = (Symbol)arg1Value;
               return Symbol.IsIdenticalOrUndefined.doHostObject(arg0Value, arg1Value_);
            } else {
               return Symbol.IsIdenticalOrUndefined.doOther(arg0Value, arg1Value);
            }
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean hasLanguage(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((Symbol)receiver).hasLanguage();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Class<? extends TruffleLanguage<?>> getLanguage(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((Symbol)receiver).getLanguage();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Object toDisplayString(Object receiver, boolean allowSideEffects) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((Symbol)receiver).toDisplayString(allowSideEffects);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean hasMetaObject(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((Symbol)receiver).hasMetaObject();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Object getMetaObject(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((Symbol)receiver).getMetaObject();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public int identityHashCode(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((Symbol)receiver).identityHashCode();
         }
      }
   }
}
