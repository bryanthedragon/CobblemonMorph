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

@GeneratedBy(DefaultByteExports.class)
final class DefaultByteExportsGen {
   private static final LibraryFactory<DynamicDispatchLibrary> DYNAMIC_DISPATCH_LIBRARY_ = LibraryFactory.resolve(DynamicDispatchLibrary.class);

   private DefaultByteExportsGen() {
   }

   static {
      LibraryExport.register(DefaultByteExports.class, new DefaultByteExportsGen.InteropLibraryExports());
   }

   @GeneratedBy(DefaultByteExports.class)
   private static final class InteropLibraryExports extends LibraryExport<InteropLibrary> {
      private static final DefaultByteExportsGen.InteropLibraryExports.Uncached UNCACHED = new DefaultByteExportsGen.InteropLibraryExports.Uncached();
      private static final DefaultByteExportsGen.InteropLibraryExports.Cached CACHE = new DefaultByteExportsGen.InteropLibraryExports.Cached();

      private InteropLibraryExports() {
         super(InteropLibrary.class, Byte.class, true, false, 0);
      }

      protected InteropLibrary createUncached(Object receiver) {
         assert receiver instanceof Byte;

         InteropLibrary uncached = UNCACHED;
         return uncached;
      }

      protected InteropLibrary createCached(Object receiver) {
         assert receiver instanceof Byte;

         return CACHE;
      }

      @GeneratedBy(DefaultByteExports.class)
      private static final class Cached extends InteropLibrary {
         protected Cached() {
         }

         @Override
         public boolean accepts(Object receiver) {
            assert !(receiver instanceof Byte) || DefaultByteExportsGen.DYNAMIC_DISPATCH_LIBRARY_.getUncached().dispatch(receiver) == null : "Invalid library export. Exported receiver with dynamic dispatch found but not expected.";

            return receiver instanceof Byte;
         }

         @Override
         public boolean isAdoptable() {
            return false;
         }

         @Override
         public boolean isNumber(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultByteExports.isNumber((Byte)receiver);
         }

         @Override
         public boolean fitsInByte(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultByteExports.fitsInByte((Byte)receiver);
         }

         @Override
         public boolean fitsInInt(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultByteExports.fitsInInt((Byte)receiver);
         }

         @Override
         public boolean fitsInShort(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultByteExports.fitsInShort((Byte)receiver);
         }

         @Override
         public boolean fitsInLong(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultByteExports.fitsInLong((Byte)receiver);
         }

         @Override
         public boolean fitsInFloat(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultByteExports.fitsInFloat((Byte)receiver);
         }

         @Override
         public boolean fitsInDouble(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultByteExports.fitsInDouble((Byte)receiver);
         }

         @Override
         public byte asByte(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultByteExports.asByte((Byte)receiver);
         }

         @Override
         public short asShort(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultByteExports.asShort((Byte)receiver);
         }

         @Override
         public int asInt(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultByteExports.asInt((Byte)receiver);
         }

         @Override
         public long asLong(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultByteExports.asLong((Byte)receiver);
         }

         @Override
         public float asFloat(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultByteExports.asFloat((Byte)receiver);
         }

         @Override
         public double asDouble(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultByteExports.asDouble((Byte)receiver);
         }

         @Override
         public boolean hasLanguage(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultByteExports.hasLanguage((Byte)receiver);
         }

         @Override
         public Class<? extends TruffleLanguage<?>> getLanguage(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultByteExports.getLanguage((Byte)receiver);
         }

         @Override
         public boolean hasSourceLocation(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultByteExports.hasSourceLocation((Byte)receiver);
         }

         @Override
         public SourceSection getSourceLocation(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultByteExports.getSourceLocation((Byte)receiver);
         }

         @Override
         public boolean hasMetaObject(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultByteExports.hasMetaObject((Byte)receiver);
         }

         @Override
         public Object getMetaObject(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultByteExports.getMetaObject((Byte)receiver);
         }

         @Override
         public Object toDisplayString(Object receiver, boolean allowSideEffects) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultByteExports.toDisplayString((Byte)receiver, allowSideEffects);
         }
      }

      @GeneratedBy(DefaultByteExports.class)
      @DenyReplace
      private static final class Uncached extends InteropLibrary {
         protected Uncached() {
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean accepts(Object receiver) {
            assert !(receiver instanceof Byte) || DefaultByteExportsGen.DYNAMIC_DISPATCH_LIBRARY_.getUncached().dispatch(receiver) == null : "Invalid library export. Exported receiver with dynamic dispatch found but not expected.";

            return receiver instanceof Byte;
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
         public boolean isNumber(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultByteExports.isNumber((Byte)receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean fitsInByte(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultByteExports.fitsInByte((Byte)receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean fitsInInt(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultByteExports.fitsInInt((Byte)receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean fitsInShort(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultByteExports.fitsInShort((Byte)receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean fitsInLong(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultByteExports.fitsInLong((Byte)receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean fitsInFloat(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultByteExports.fitsInFloat((Byte)receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean fitsInDouble(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultByteExports.fitsInDouble((Byte)receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public byte asByte(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultByteExports.asByte((Byte)receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public short asShort(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultByteExports.asShort((Byte)receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public int asInt(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultByteExports.asInt((Byte)receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public long asLong(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultByteExports.asLong((Byte)receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public float asFloat(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultByteExports.asFloat((Byte)receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public double asDouble(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultByteExports.asDouble((Byte)receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean hasLanguage(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultByteExports.hasLanguage((Byte)receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Class<? extends TruffleLanguage<?>> getLanguage(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultByteExports.getLanguage((Byte)receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean hasSourceLocation(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultByteExports.hasSourceLocation((Byte)receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public SourceSection getSourceLocation(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultByteExports.getSourceLocation((Byte)receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean hasMetaObject(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultByteExports.hasMetaObject((Byte)receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Object getMetaObject(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultByteExports.getMetaObject((Byte)receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Object toDisplayString(Object receiver, boolean allowSideEffects) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultByteExports.toDisplayString((Byte)receiver, allowSideEffects);
         }
      }
   }
}
