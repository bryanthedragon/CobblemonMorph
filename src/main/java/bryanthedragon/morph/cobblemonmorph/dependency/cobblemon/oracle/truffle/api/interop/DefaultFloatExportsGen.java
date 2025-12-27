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

@GeneratedBy(DefaultFloatExports.class)
final class DefaultFloatExportsGen {
   private static final LibraryFactory<DynamicDispatchLibrary> DYNAMIC_DISPATCH_LIBRARY_ = LibraryFactory.resolve(DynamicDispatchLibrary.class);

   private DefaultFloatExportsGen() {
   }

   static {
      LibraryExport.register(DefaultFloatExports.class, new DefaultFloatExportsGen.InteropLibraryExports());
   }

   @GeneratedBy(DefaultFloatExports.class)
   private static final class InteropLibraryExports extends LibraryExport<InteropLibrary> {
      private static final DefaultFloatExportsGen.InteropLibraryExports.Uncached UNCACHED = new DefaultFloatExportsGen.InteropLibraryExports.Uncached();
      private static final DefaultFloatExportsGen.InteropLibraryExports.Cached CACHE = new DefaultFloatExportsGen.InteropLibraryExports.Cached();

      private InteropLibraryExports() {
         super(InteropLibrary.class, Float.class, true, false, 0);
      }

      protected InteropLibrary createUncached(Object receiver) {
         assert receiver instanceof Float;

         InteropLibrary uncached = UNCACHED;
         return uncached;
      }

      protected InteropLibrary createCached(Object receiver) {
         assert receiver instanceof Float;

         return CACHE;
      }

      @GeneratedBy(DefaultFloatExports.class)
      private static final class Cached extends InteropLibrary {
         protected Cached() {
         }

         @Override
         public boolean accepts(Object receiver) {
            assert !(receiver instanceof Float) || DefaultFloatExportsGen.DYNAMIC_DISPATCH_LIBRARY_.getUncached().dispatch(receiver) == null : "Invalid library export. Exported receiver with dynamic dispatch found but not expected.";

            return receiver instanceof Float;
         }

         @Override
         public boolean isAdoptable() {
            return false;
         }

         @Override
         public boolean fitsInByte(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultFloatExports.fitsInByte((Float)receiver);
         }

         @Override
         public boolean fitsInInt(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultFloatExports.fitsInInt((Float)receiver);
         }

         @Override
         public boolean fitsInShort(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultFloatExports.fitsInShort((Float)receiver);
         }

         @Override
         public boolean fitsInLong(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultFloatExports.fitsInLong((Float)receiver);
         }

         @Override
         public byte asByte(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultFloatExports.asByte((Float)receiver);
         }

         @Override
         public short asShort(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultFloatExports.asShort((Float)receiver);
         }

         @Override
         public int asInt(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultFloatExports.asInt((Float)receiver);
         }

         @Override
         public long asLong(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultFloatExports.asLong((Float)receiver);
         }

         @Override
         public boolean isNumber(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultFloatExports.isNumber((Float)receiver);
         }

         @Override
         public boolean fitsInFloat(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultFloatExports.fitsInFloat((Float)receiver);
         }

         @Override
         public float asFloat(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultFloatExports.asFloat((Float)receiver);
         }

         @Override
         public boolean fitsInDouble(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultFloatExports.fitsInDouble((Float)receiver);
         }

         @Override
         public double asDouble(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultFloatExports.asDouble((Float)receiver);
         }

         @Override
         public boolean hasLanguage(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultFloatExports.hasLanguage((Float)receiver);
         }

         @Override
         public Class<? extends TruffleLanguage<?>> getLanguage(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultFloatExports.getLanguage((Float)receiver);
         }

         @Override
         public boolean hasSourceLocation(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultFloatExports.hasSourceLocation((Float)receiver);
         }

         @Override
         public SourceSection getSourceLocation(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultFloatExports.getSourceLocation((Float)receiver);
         }

         @Override
         public boolean hasMetaObject(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultFloatExports.hasMetaObject((Float)receiver);
         }

         @Override
         public Object getMetaObject(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultFloatExports.getMetaObject((Float)receiver);
         }

         @Override
         public Object toDisplayString(Object receiver, boolean allowSideEffects) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultFloatExports.toDisplayString((Float)receiver, allowSideEffects);
         }
      }

      @GeneratedBy(DefaultFloatExports.class)
      @DenyReplace
      private static final class Uncached extends InteropLibrary {
         protected Uncached() {
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean accepts(Object receiver) {
            assert !(receiver instanceof Float) || DefaultFloatExportsGen.DYNAMIC_DISPATCH_LIBRARY_.getUncached().dispatch(receiver) == null : "Invalid library export. Exported receiver with dynamic dispatch found but not expected.";

            return receiver instanceof Float;
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

            return DefaultFloatExports.fitsInByte((Float)receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean fitsInInt(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultFloatExports.fitsInInt((Float)receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean fitsInShort(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultFloatExports.fitsInShort((Float)receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean fitsInLong(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultFloatExports.fitsInLong((Float)receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public byte asByte(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultFloatExports.asByte((Float)receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public short asShort(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultFloatExports.asShort((Float)receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public int asInt(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultFloatExports.asInt((Float)receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public long asLong(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultFloatExports.asLong((Float)receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean isNumber(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultFloatExports.isNumber((Float)receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean fitsInFloat(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultFloatExports.fitsInFloat((Float)receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public float asFloat(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultFloatExports.asFloat((Float)receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean fitsInDouble(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultFloatExports.fitsInDouble((Float)receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public double asDouble(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultFloatExports.asDouble((Float)receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean hasLanguage(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultFloatExports.hasLanguage((Float)receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Class<? extends TruffleLanguage<?>> getLanguage(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultFloatExports.getLanguage((Float)receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean hasSourceLocation(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultFloatExports.hasSourceLocation((Float)receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public SourceSection getSourceLocation(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultFloatExports.getSourceLocation((Float)receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean hasMetaObject(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultFloatExports.hasMetaObject((Float)receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Object getMetaObject(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultFloatExports.getMetaObject((Float)receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Object toDisplayString(Object receiver, boolean allowSideEffects) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultFloatExports.toDisplayString((Float)receiver, allowSideEffects);
         }
      }
   }
}
