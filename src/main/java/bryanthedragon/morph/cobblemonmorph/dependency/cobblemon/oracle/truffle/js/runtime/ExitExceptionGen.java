package com.oracle.truffle.js.runtime;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.interop.ExceptionType;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.library.DynamicDispatchLibrary;
import com.oracle.truffle.api.library.LibraryExport;
import com.oracle.truffle.api.library.LibraryFactory;
import com.oracle.truffle.api.nodes.NodeCost;

@GeneratedBy(ExitException.class)
public final class ExitExceptionGen {
   private static final LibraryFactory<DynamicDispatchLibrary> DYNAMIC_DISPATCH_LIBRARY_ = LibraryFactory.resolve(DynamicDispatchLibrary.class);

   private ExitExceptionGen() {
   }

   static {
      LibraryExport.register(ExitException.class, new ExitExceptionGen.InteropLibraryExports());
   }

   @GeneratedBy(ExitException.class)
   public static class InteropLibraryExports extends LibraryExport<InteropLibrary> {
      private InteropLibraryExports() {
         super(InteropLibrary.class, ExitException.class, false, false, 0);
      }

      protected InteropLibrary createUncached(Object receiver) {
         assert receiver instanceof ExitException;

         InteropLibrary uncached = new ExitExceptionGen.InteropLibraryExports.Uncached(receiver);
         return uncached;
      }

      protected InteropLibrary createCached(Object receiver) {
         assert receiver instanceof ExitException;

         return new ExitExceptionGen.InteropLibraryExports.Cached(receiver);
      }

      @GeneratedBy(ExitException.class)
      public static class Cached extends InteropLibrary {
         private final Class<? extends ExitException> receiverClass_;

         protected Cached(Object receiver) {
            ExitException castReceiver = (ExitException)receiver;
            this.receiverClass_ = (Class<? extends ExitException>)castReceiver.getClass();
         }

         @Override
         public boolean accepts(Object receiver) {
            assert receiver.getClass() != this.receiverClass_ || ExitExceptionGen.DYNAMIC_DISPATCH_LIBRARY_.getUncached().dispatch(receiver) == null : "Invalid library export. Exported receiver with dynamic dispatch found but not expected.";

            return CompilerDirectives.isExact(receiver, this.receiverClass_);
         }

         @Override
         public ExceptionType getExceptionType(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return CompilerDirectives.castExact(receiver, this.receiverClass_).getExceptionType();
         }

         @Override
         public int getExceptionExitStatus(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return CompilerDirectives.castExact(receiver, this.receiverClass_).getExceptionExitStatus();
         }
      }

      @GeneratedBy(ExitException.class)
      public static class Uncached extends InteropLibrary {
         private final Class<? extends ExitException> receiverClass_;

         protected Uncached(Object receiver) {
            this.receiverClass_ = (Class<? extends ExitException>)((ExitException)receiver).getClass();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean accepts(Object receiver) {
            assert receiver.getClass() != this.receiverClass_ || ExitExceptionGen.DYNAMIC_DISPATCH_LIBRARY_.getUncached().dispatch(receiver) == null : "Invalid library export. Exported receiver with dynamic dispatch found but not expected.";

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
         public ExceptionType getExceptionType(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((ExitException)receiver).getExceptionType();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public int getExceptionExitStatus(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((ExitException)receiver).getExceptionExitStatus();
         }
      }
   }
}
