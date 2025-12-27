package com.oracle.truffle.polyglot;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.interop.ExceptionType;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.library.DynamicDispatchLibrary;
import com.oracle.truffle.api.library.LibraryExport;
import com.oracle.truffle.api.library.LibraryFactory;
import com.oracle.truffle.api.nodes.DenyReplace;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.source.SourceSection;

@GeneratedBy(PolyglotEngineImpl.InterruptExecution.class)
final class InterruptExecutionGen {
   private static final LibraryFactory<DynamicDispatchLibrary> DYNAMIC_DISPATCH_LIBRARY_ = LibraryFactory.resolve(DynamicDispatchLibrary.class);

   private InterruptExecutionGen() {
   }

   static {
      LibraryExport.register(PolyglotEngineImpl.InterruptExecution.class, new InterruptExecutionGen.InteropLibraryExports());
   }

   @GeneratedBy(PolyglotEngineImpl.InterruptExecution.class)
   private static final class InteropLibraryExports extends LibraryExport<InteropLibrary> {
      private static final InterruptExecutionGen.InteropLibraryExports.Uncached UNCACHED = new InterruptExecutionGen.InteropLibraryExports.Uncached();
      private static final InterruptExecutionGen.InteropLibraryExports.Cached CACHE = new InterruptExecutionGen.InteropLibraryExports.Cached();

      private InteropLibraryExports() {
         super(InteropLibrary.class, PolyglotEngineImpl.InterruptExecution.class, false, false, 0);
      }

      protected InteropLibrary createUncached(Object receiver) {
         assert receiver instanceof PolyglotEngineImpl.InterruptExecution;

         InteropLibrary uncached = UNCACHED;
         return uncached;
      }

      protected InteropLibrary createCached(Object receiver) {
         assert receiver instanceof PolyglotEngineImpl.InterruptExecution;

         return CACHE;
      }

      @GeneratedBy(PolyglotEngineImpl.InterruptExecution.class)
      private static final class Cached extends InteropLibrary {
         protected Cached() {
         }

         @Override
         public boolean accepts(Object receiver) {
            assert !(receiver instanceof PolyglotEngineImpl.InterruptExecution)
               || InterruptExecutionGen.DYNAMIC_DISPATCH_LIBRARY_.getUncached().dispatch(receiver) == null : "Invalid library export. Exported receiver with dynamic dispatch found but not expected.";

            return receiver instanceof PolyglotEngineImpl.InterruptExecution;
         }

         @Override
         public boolean isAdoptable() {
            return false;
         }

         @Override
         public ExceptionType getExceptionType(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((PolyglotEngineImpl.InterruptExecution)receiver).getExceptionType();
         }

         @Override
         public boolean hasSourceLocation(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((PolyglotEngineImpl.InterruptExecution)receiver).hasSourceLocation();
         }

         @Override
         public SourceSection getSourceLocation(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((PolyglotEngineImpl.InterruptExecution)receiver).getSourceSection();
         }
      }

      @GeneratedBy(PolyglotEngineImpl.InterruptExecution.class)
      @DenyReplace
      private static final class Uncached extends InteropLibrary {
         protected Uncached() {
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean accepts(Object receiver) {
            assert !(receiver instanceof PolyglotEngineImpl.InterruptExecution)
               || InterruptExecutionGen.DYNAMIC_DISPATCH_LIBRARY_.getUncached().dispatch(receiver) == null : "Invalid library export. Exported receiver with dynamic dispatch found but not expected.";

            return receiver instanceof PolyglotEngineImpl.InterruptExecution;
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
         public ExceptionType getExceptionType(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((PolyglotEngineImpl.InterruptExecution)receiver).getExceptionType();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean hasSourceLocation(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((PolyglotEngineImpl.InterruptExecution)receiver).hasSourceLocation();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public SourceSection getSourceLocation(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((PolyglotEngineImpl.InterruptExecution)receiver).getSourceSection();
         }
      }
   }
}
