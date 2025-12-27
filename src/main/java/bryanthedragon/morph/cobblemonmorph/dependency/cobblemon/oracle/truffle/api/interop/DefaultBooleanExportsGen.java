package com.oracle.truffle.api.interop;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.library.DynamicDispatchLibrary;
import com.oracle.truffle.api.library.LibraryExport;
import com.oracle.truffle.api.library.LibraryFactory;
import com.oracle.truffle.api.nodes.DenyReplace;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.source.SourceSection;

@GeneratedBy(DefaultBooleanExports.class)
final class DefaultBooleanExportsGen {
   private static final LibraryFactory<DynamicDispatchLibrary> DYNAMIC_DISPATCH_LIBRARY_ = LibraryFactory.resolve(DynamicDispatchLibrary.class);

   private DefaultBooleanExportsGen() {
   }

   static {
      LibraryExport.register(DefaultBooleanExports.class, new DefaultBooleanExportsGen.InteropLibraryExports());
   }

   @GeneratedBy(DefaultBooleanExports.class)
   private static final class InteropLibraryExports extends LibraryExport<InteropLibrary> {
      private static final DefaultBooleanExportsGen.InteropLibraryExports.Uncached UNCACHED = new DefaultBooleanExportsGen.InteropLibraryExports.Uncached();
      private static final DefaultBooleanExportsGen.InteropLibraryExports.Cached CACHE = new DefaultBooleanExportsGen.InteropLibraryExports.Cached();

      private InteropLibraryExports() {
         super(InteropLibrary.class, Boolean.class, true, false, 0);
      }

      protected InteropLibrary createUncached(Object receiver) {
         assert receiver instanceof Boolean;

         InteropLibrary uncached = UNCACHED;
         return uncached;
      }

      protected InteropLibrary createCached(Object receiver) {
         assert receiver instanceof Boolean;

         return CACHE;
      }

      @GeneratedBy(DefaultBooleanExports.class)
      private static final class Cached extends InteropLibrary {
         protected Cached() {
         }

         @Override
         public boolean accepts(Object receiver) {
            assert !(receiver instanceof Boolean) || DefaultBooleanExportsGen.DYNAMIC_DISPATCH_LIBRARY_.getUncached().dispatch(receiver) == null : "Invalid library export. Exported receiver with dynamic dispatch found but not expected.";

            return receiver instanceof Boolean;
         }

         @Override
         public boolean isAdoptable() {
            return false;
         }

         @Override
         public boolean isBoolean(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultBooleanExports.isBoolean((Boolean)receiver);
         }

         @Override
         public boolean asBoolean(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultBooleanExports.asBoolean((Boolean)receiver);
         }

         @Override
         public boolean hasLanguage(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultBooleanExports.hasLanguage((Boolean)receiver);
         }

         @Override
         public Class<? extends TruffleLanguage<?>> getLanguage(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultBooleanExports.getLanguage((Boolean)receiver);
         }

         @Override
         public boolean hasSourceLocation(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultBooleanExports.hasSourceLocation((Boolean)receiver);
         }

         @Override
         public SourceSection getSourceLocation(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultBooleanExports.getSourceLocation((Boolean)receiver);
         }

         @Override
         public boolean hasMetaObject(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultBooleanExports.hasMetaObject((Boolean)receiver);
         }

         @Override
         public Object getMetaObject(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultBooleanExports.getMetaObject((Boolean)receiver);
         }

         @Override
         public Object toDisplayString(Object receiver, boolean allowSideEffects) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultBooleanExports.toDisplayString((Boolean)receiver, allowSideEffects);
         }
      }

      @GeneratedBy(DefaultBooleanExports.class)
      @DenyReplace
      private static final class Uncached extends InteropLibrary {
         protected Uncached() {
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean accepts(Object receiver) {
            assert !(receiver instanceof Boolean) || DefaultBooleanExportsGen.DYNAMIC_DISPATCH_LIBRARY_.getUncached().dispatch(receiver) == null : "Invalid library export. Exported receiver with dynamic dispatch found but not expected.";

            return receiver instanceof Boolean;
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
         public boolean isBoolean(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultBooleanExports.isBoolean((Boolean)receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean asBoolean(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultBooleanExports.asBoolean((Boolean)receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean hasLanguage(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultBooleanExports.hasLanguage((Boolean)receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Class<? extends TruffleLanguage<?>> getLanguage(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultBooleanExports.getLanguage((Boolean)receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean hasSourceLocation(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultBooleanExports.hasSourceLocation((Boolean)receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public SourceSection getSourceLocation(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultBooleanExports.getSourceLocation((Boolean)receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean hasMetaObject(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultBooleanExports.hasMetaObject((Boolean)receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Object getMetaObject(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultBooleanExports.getMetaObject((Boolean)receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Object toDisplayString(Object receiver, boolean allowSideEffects) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultBooleanExports.toDisplayString((Boolean)receiver, allowSideEffects);
         }
      }
   }
}
