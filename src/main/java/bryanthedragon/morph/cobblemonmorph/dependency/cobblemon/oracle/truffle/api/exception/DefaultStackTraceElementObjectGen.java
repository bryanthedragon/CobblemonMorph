package com.oracle.truffle.api.exception;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.library.DynamicDispatchLibrary;
import com.oracle.truffle.api.library.LibraryExport;
import com.oracle.truffle.api.library.LibraryFactory;
import com.oracle.truffle.api.nodes.DenyReplace;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.source.SourceSection;

@GeneratedBy(DefaultStackTraceElementObject.class)
final class DefaultStackTraceElementObjectGen {
   private static final LibraryFactory<DynamicDispatchLibrary> DYNAMIC_DISPATCH_LIBRARY_ = LibraryFactory.resolve(DynamicDispatchLibrary.class);

   private DefaultStackTraceElementObjectGen() {
   }

   static {
      LibraryExport.register(DefaultStackTraceElementObject.class, new DefaultStackTraceElementObjectGen.InteropLibraryExports());
   }

   @GeneratedBy(DefaultStackTraceElementObject.class)
   private static final class InteropLibraryExports extends LibraryExport<InteropLibrary> {
      private static final DefaultStackTraceElementObjectGen.InteropLibraryExports.Uncached UNCACHED = new DefaultStackTraceElementObjectGen.InteropLibraryExports.Uncached();
      private static final DefaultStackTraceElementObjectGen.InteropLibraryExports.Cached CACHE = new DefaultStackTraceElementObjectGen.InteropLibraryExports.Cached();

      private InteropLibraryExports() {
         super(InteropLibrary.class, DefaultStackTraceElementObject.class, false, false, 0);
      }

      protected InteropLibrary createUncached(Object receiver) {
         assert receiver instanceof DefaultStackTraceElementObject;

         InteropLibrary uncached = UNCACHED;
         return uncached;
      }

      protected InteropLibrary createCached(Object receiver) {
         assert receiver instanceof DefaultStackTraceElementObject;

         return CACHE;
      }

      @GeneratedBy(DefaultStackTraceElementObject.class)
      private static final class Cached extends InteropLibrary {
         protected Cached() {
         }

         @Override
         public boolean accepts(Object receiver) {
            assert !(receiver instanceof DefaultStackTraceElementObject)
               || DefaultStackTraceElementObjectGen.DYNAMIC_DISPATCH_LIBRARY_.getUncached().dispatch(receiver) == null : "Invalid library export. Exported receiver with dynamic dispatch found but not expected.";

            return receiver instanceof DefaultStackTraceElementObject;
         }

         @Override
         public boolean isAdoptable() {
            return false;
         }

         @Override
         public boolean hasExecutableName(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((DefaultStackTraceElementObject)receiver).hasExecutableName();
         }

         @Override
         public Object getExecutableName(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((DefaultStackTraceElementObject)receiver).getExecutableName();
         }

         @Override
         public boolean hasSourceLocation(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((DefaultStackTraceElementObject)receiver).hasSourceLocation();
         }

         @Override
         public SourceSection getSourceLocation(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((DefaultStackTraceElementObject)receiver).getSourceLocation();
         }

         @Override
         public boolean hasDeclaringMetaObject(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((DefaultStackTraceElementObject)receiver).hasDeclaringMetaObject();
         }

         @Override
         public Object getDeclaringMetaObject(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((DefaultStackTraceElementObject)receiver).getDeclaringMetaObject();
         }
      }

      @GeneratedBy(DefaultStackTraceElementObject.class)
      @DenyReplace
      private static final class Uncached extends InteropLibrary {
         protected Uncached() {
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean accepts(Object receiver) {
            assert !(receiver instanceof DefaultStackTraceElementObject)
               || DefaultStackTraceElementObjectGen.DYNAMIC_DISPATCH_LIBRARY_.getUncached().dispatch(receiver) == null : "Invalid library export. Exported receiver with dynamic dispatch found but not expected.";

            return receiver instanceof DefaultStackTraceElementObject;
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
         public boolean hasExecutableName(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((DefaultStackTraceElementObject)receiver).hasExecutableName();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Object getExecutableName(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((DefaultStackTraceElementObject)receiver).getExecutableName();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean hasSourceLocation(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((DefaultStackTraceElementObject)receiver).hasSourceLocation();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public SourceSection getSourceLocation(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((DefaultStackTraceElementObject)receiver).getSourceLocation();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean hasDeclaringMetaObject(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((DefaultStackTraceElementObject)receiver).hasDeclaringMetaObject();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Object getDeclaringMetaObject(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((DefaultStackTraceElementObject)receiver).getDeclaringMetaObject();
         }
      }
   }
}
