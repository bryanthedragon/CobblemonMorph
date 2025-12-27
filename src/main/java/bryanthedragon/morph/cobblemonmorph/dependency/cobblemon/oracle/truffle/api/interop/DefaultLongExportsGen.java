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

@GeneratedBy(DefaultLongExports.class)
final class DefaultLongExportsGen {
   private static final LibraryFactory<DynamicDispatchLibrary> DYNAMIC_DISPATCH_LIBRARY_ = LibraryFactory.resolve(DynamicDispatchLibrary.class);

   private DefaultLongExportsGen() {
   }

   static {
      LibraryExport.register(DefaultLongExports.class, new DefaultLongExportsGen.InteropLibraryExports());
   }

   @GeneratedBy(DefaultLongExports.class)
   private static final class InteropLibraryExports extends LibraryExport<InteropLibrary> {
      private static final DefaultLongExportsGen.InteropLibraryExports.Uncached UNCACHED = new DefaultLongExportsGen.InteropLibraryExports.Uncached();
      private static final DefaultLongExportsGen.InteropLibraryExports.Cached CACHE = new DefaultLongExportsGen.InteropLibraryExports.Cached();

      private InteropLibraryExports() {
         super(InteropLibrary.class, Long.class, true, false, 0);
      }

      protected InteropLibrary createUncached(Object receiver) {
         assert receiver instanceof Long;

         InteropLibrary uncached = UNCACHED;
         return uncached;
      }

      protected InteropLibrary createCached(Object receiver) {
         assert receiver instanceof Long;

         return CACHE;
      }

      @GeneratedBy(DefaultLongExports.class)
      private static final class Cached extends InteropLibrary {
         protected Cached() {
         }

         @Override
         public boolean accepts(Object receiver) {
            assert !(receiver instanceof Long) || DefaultLongExportsGen.DYNAMIC_DISPATCH_LIBRARY_.getUncached().dispatch(receiver) == null : "Invalid library export. Exported receiver with dynamic dispatch found but not expected.";

            return receiver instanceof Long;
         }

         @Override
         public boolean isAdoptable() {
            return false;
         }

         @Override
         public boolean fitsInByte(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultLongExports.fitsInByte((Long)receiver);
         }

         @Override
         public boolean fitsInInt(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultLongExports.fitsInInt((Long)receiver);
         }

         @Override
         public boolean fitsInShort(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultLongExports.fitsInShort((Long)receiver);
         }

         @Override
         public boolean fitsInFloat(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultLongExports.fitsInFloat((Long)receiver);
         }

         @Override
         public boolean fitsInDouble(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultLongExports.fitsInDouble((Long)receiver);
         }

         @Override
         public byte asByte(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultLongExports.asByte((Long)receiver);
         }

         @Override
         public short asShort(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultLongExports.asShort((Long)receiver);
         }

         @Override
         public int asInt(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultLongExports.asInt((Long)receiver);
         }

         @Override
         public float asFloat(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultLongExports.asFloat((Long)receiver);
         }

         @Override
         public double asDouble(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultLongExports.asDouble((Long)receiver);
         }

         @Override
         public boolean isNumber(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultLongExports.isNumber((Long)receiver);
         }

         @Override
         public boolean fitsInLong(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultLongExports.fitsInLong((Long)receiver);
         }

         @Override
         public long asLong(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultLongExports.asLong((Long)receiver);
         }

         @Override
         public boolean hasLanguage(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultLongExports.hasLanguage((Long)receiver);
         }

         @Override
         public Class<? extends TruffleLanguage<?>> getLanguage(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultLongExports.getLanguage((Long)receiver);
         }

         @Override
         public boolean hasSourceLocation(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultLongExports.hasSourceLocation((Long)receiver);
         }

         @Override
         public SourceSection getSourceLocation(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultLongExports.getSourceLocation((Long)receiver);
         }

         @Override
         public boolean hasMetaObject(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultLongExports.hasMetaObject((Long)receiver);
         }

         @Override
         public Object getMetaObject(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultLongExports.getMetaObject((Long)receiver);
         }

         @Override
         public Object toDisplayString(Object receiver, boolean allowSideEffects) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultLongExports.toDisplayString((Long)receiver, allowSideEffects);
         }
      }

      @GeneratedBy(DefaultLongExports.class)
      @DenyReplace
      private static final class Uncached extends InteropLibrary {
         protected Uncached() {
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean accepts(Object receiver) {
            assert !(receiver instanceof Long) || DefaultLongExportsGen.DYNAMIC_DISPATCH_LIBRARY_.getUncached().dispatch(receiver) == null : "Invalid library export. Exported receiver with dynamic dispatch found but not expected.";

            return receiver instanceof Long;
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
         public boolean fitsInByte(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultLongExports.fitsInByte((Long)receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean fitsInInt(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultLongExports.fitsInInt((Long)receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean fitsInShort(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultLongExports.fitsInShort((Long)receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean fitsInFloat(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultLongExports.fitsInFloat((Long)receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean fitsInDouble(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultLongExports.fitsInDouble((Long)receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public byte asByte(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultLongExports.asByte((Long)receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public short asShort(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultLongExports.asShort((Long)receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public int asInt(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultLongExports.asInt((Long)receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public float asFloat(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultLongExports.asFloat((Long)receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public double asDouble(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultLongExports.asDouble((Long)receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean isNumber(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultLongExports.isNumber((Long)receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean fitsInLong(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultLongExports.fitsInLong((Long)receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public long asLong(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultLongExports.asLong((Long)receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean hasLanguage(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultLongExports.hasLanguage((Long)receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Class<? extends TruffleLanguage<?>> getLanguage(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultLongExports.getLanguage((Long)receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean hasSourceLocation(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultLongExports.hasSourceLocation((Long)receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public SourceSection getSourceLocation(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultLongExports.getSourceLocation((Long)receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean hasMetaObject(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultLongExports.hasMetaObject((Long)receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Object getMetaObject(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultLongExports.getMetaObject((Long)receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Object toDisplayString(Object receiver, boolean allowSideEffects) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultLongExports.toDisplayString((Long)receiver, allowSideEffects);
         }
      }
   }
}
