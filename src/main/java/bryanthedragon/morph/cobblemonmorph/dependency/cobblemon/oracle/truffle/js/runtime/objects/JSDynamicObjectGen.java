package com.oracle.truffle.js.runtime.objects;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.library.DynamicDispatchLibrary;
import com.oracle.truffle.api.library.LibraryExport;
import com.oracle.truffle.api.library.LibraryFactory;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.utilities.TriState;

@GeneratedBy(JSDynamicObject.class)
public final class JSDynamicObjectGen {
   private static final LibraryFactory<DynamicDispatchLibrary> DYNAMIC_DISPATCH_LIBRARY_ = LibraryFactory.resolve(DynamicDispatchLibrary.class);

   private JSDynamicObjectGen() {
   }

   static {
      LibraryExport.register(JSDynamicObject.class, new JSDynamicObjectGen.InteropLibraryExports());
   }

   @GeneratedBy(JSDynamicObject.class)
   public static class InteropLibraryExports extends LibraryExport<InteropLibrary> {
      private InteropLibraryExports() {
         super(InteropLibrary.class, JSDynamicObject.class, false, false, 0);
      }

      protected InteropLibrary createUncached(Object receiver) {
         assert receiver instanceof JSDynamicObject;

         InteropLibrary uncached = new JSDynamicObjectGen.InteropLibraryExports.Uncached(receiver);
         return uncached;
      }

      protected InteropLibrary createCached(Object receiver) {
         assert receiver instanceof JSDynamicObject;

         return new JSDynamicObjectGen.InteropLibraryExports.Cached(receiver);
      }

      @GeneratedBy(JSDynamicObject.class)
      public static class Cached extends InteropLibrary {
         private final Class<? extends JSDynamicObject> receiverClass_;
         @CompilerDirectives.CompilationFinal
         private int state_0_;

         protected Cached(Object receiver) {
            JSDynamicObject castReceiver = (JSDynamicObject)receiver;
            this.receiverClass_ = (Class<? extends JSDynamicObject>)castReceiver.getClass();
         }

         @Override
         public boolean accepts(Object receiver) {
            assert receiver.getClass() != this.receiverClass_ || JSDynamicObjectGen.DYNAMIC_DISPATCH_LIBRARY_.getUncached().dispatch(receiver) == null : "Invalid library export. Exported receiver with dynamic dispatch found but not expected.";

            return CompilerDirectives.isExact(receiver, this.receiverClass_);
         }

         @Override
         protected TriState isIdenticalOrUndefined(Object arg0Value_, Object arg1Value) {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            JSDynamicObject arg0Value = CompilerDirectives.castExact(arg0Value_, this.receiverClass_);
            int state_0 = this.state_0_;
            if (state_0 != 0) {
               if ((state_0 & 1) != 0 && arg1Value instanceof JSDynamicObject) {
                  JSDynamicObject arg1Value_ = (JSDynamicObject)arg1Value;
                  return JSDynamicObject.IsIdenticalOrUndefined.doHostObject(arg0Value, arg1Value_);
               }

               if ((state_0 & 2) != 0 && fallbackGuard_(state_0, arg0Value, arg1Value)) {
                  return JSDynamicObject.IsIdenticalOrUndefined.doOther(arg0Value, arg1Value);
               }
            }

            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value, arg1Value);
         }

         private TriState executeAndSpecialize(JSDynamicObject arg0Value, Object arg1Value) {
            int state_0 = this.state_0_;
            if (arg1Value instanceof JSDynamicObject) {
               JSDynamicObject arg1Value_ = (JSDynamicObject)arg1Value;
               int var6;
               this.state_0_ = var6 = state_0 | 1;
               return JSDynamicObject.IsIdenticalOrUndefined.doHostObject(arg0Value, arg1Value_);
            } else {
               int var5;
               this.state_0_ = var5 = state_0 | 2;
               return JSDynamicObject.IsIdenticalOrUndefined.doOther(arg0Value, arg1Value);
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
         public int identityHashCode(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            return CompilerDirectives.castExact(receiver, this.receiverClass_).identityHashCode();
         }

         private static boolean fallbackGuard_(int state_0, JSDynamicObject arg0Value, Object arg1Value) {
            return (state_0 & 1) != 0 || !(arg1Value instanceof JSDynamicObject);
         }
      }

      @GeneratedBy(JSDynamicObject.class)
      public static class Uncached extends InteropLibrary {
         private final Class<? extends JSDynamicObject> receiverClass_;

         protected Uncached(Object receiver) {
            this.receiverClass_ = (Class<? extends JSDynamicObject>)((JSDynamicObject)receiver).getClass();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean accepts(Object receiver) {
            assert receiver.getClass() != this.receiverClass_ || JSDynamicObjectGen.DYNAMIC_DISPATCH_LIBRARY_.getUncached().dispatch(receiver) == null : "Invalid library export. Exported receiver with dynamic dispatch found but not expected.";

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

            JSDynamicObject arg0Value = (JSDynamicObject)arg0Value_;
            if (arg1Value instanceof JSDynamicObject) {
               JSDynamicObject arg1Value_ = (JSDynamicObject)arg1Value;
               return JSDynamicObject.IsIdenticalOrUndefined.doHostObject(arg0Value, arg1Value_);
            } else {
               return JSDynamicObject.IsIdenticalOrUndefined.doOther(arg0Value, arg1Value);
            }
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public int identityHashCode(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((JSDynamicObject)receiver).identityHashCode();
         }
      }
   }
}
