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

@GeneratedBy(DefaultDoubleExports.class)
final class DefaultDoubleExportsGen {
   private static final LibraryFactory<DynamicDispatchLibrary> DYNAMIC_DISPATCH_LIBRARY_ = LibraryFactory.resolve(DynamicDispatchLibrary.class);

   private DefaultDoubleExportsGen() {
   }

   static {
      LibraryExport.register(DefaultDoubleExports.class, new DefaultDoubleExportsGen.InteropLibraryExports());
   }

   @GeneratedBy(DefaultDoubleExports.class)
   private static final class InteropLibraryExports extends LibraryExport<InteropLibrary> {
      private static final DefaultDoubleExportsGen.InteropLibraryExports.Uncached UNCACHED = new DefaultDoubleExportsGen.InteropLibraryExports.Uncached();
      private static final DefaultDoubleExportsGen.InteropLibraryExports.Cached CACHE = new DefaultDoubleExportsGen.InteropLibraryExports.Cached();

      private InteropLibraryExports() {
         super(InteropLibrary.class, Double.class, true, false, 0);
      }

      protected InteropLibrary createUncached(Object receiver) {
         assert receiver instanceof Double;

         InteropLibrary uncached = UNCACHED;
         return uncached;
      }

      protected InteropLibrary createCached(Object receiver) {
         assert receiver instanceof Double;

         return CACHE;
      }

      @GeneratedBy(DefaultDoubleExports.class)
      private static final class Cached extends InteropLibrary {
         protected Cached() {
         }

         @Override
         public boolean accepts(Object receiver) {
            assert !(receiver instanceof Double) || DefaultDoubleExportsGen.DYNAMIC_DISPATCH_LIBRARY_.getUncached().dispatch(receiver) == null : "Invalid library export. Exported receiver with dynamic dispatch found but not expected.";

            return receiver instanceof Double;
         }

         @Override
         public boolean isAdoptable() {
            return false;
         }

         @Override
         public boolean fitsInByte(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultDoubleExports.fitsInByte((Double)receiver);
         }

         @Override
         public boolean fitsInInt(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultDoubleExports.fitsInInt((Double)receiver);
         }

         @Override
         public boolean fitsInShort(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultDoubleExports.fitsInShort((Double)receiver);
         }

         @Override
         public boolean fitsInLong(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultDoubleExports.fitsInLong((Double)receiver);
         }

         @Override
         public boolean fitsInFloat(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultDoubleExports.fitsInFloat((Double)receiver);
         }

         @Override
         public byte asByte(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultDoubleExports.asByte((Double)receiver);
         }

         @Override
         public short asShort(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultDoubleExports.asShort((Double)receiver);
         }

         @Override
         public int asInt(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultDoubleExports.asInt((Double)receiver);
         }

         @Override
         public long asLong(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultDoubleExports.asLong((Double)receiver);
         }

         @Override
         public float asFloat(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultDoubleExports.asFloat((Double)receiver);
         }

         @Override
         public boolean isNumber(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultDoubleExports.isNumber((Double)receiver);
         }

         @Override
         public boolean fitsInDouble(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultDoubleExports.fitsInDouble((Double)receiver);
         }

         @Override
         public double asDouble(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultDoubleExports.asDouble((Double)receiver);
         }

         @Override
         public boolean hasLanguage(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultDoubleExports.hasLanguage((Double)receiver);
         }

         @Override
         public Class<? extends TruffleLanguage<?>> getLanguage(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultDoubleExports.getLanguage((Double)receiver);
         }

         @Override
         public boolean hasSourceLocation(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultDoubleExports.hasSourceLocation((Double)receiver);
         }

         @Override
         public SourceSection getSourceLocation(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultDoubleExports.getSourceLocation((Double)receiver);
         }

         @Override
         public boolean hasMetaObject(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultDoubleExports.hasMetaObject((Double)receiver);
         }

         @Override
         public Object getMetaObject(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultDoubleExports.getMetaObject((Double)receiver);
         }

         @Override
         public Object toDisplayString(Object receiver, boolean allowSideEffects) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultDoubleExports.toDisplayString((Double)receiver, allowSideEffects);
         }
      }

      @GeneratedBy(DefaultDoubleExports.class)
      @DenyReplace
      private static final class Uncached extends InteropLibrary {
         protected Uncached() {
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean accepts(Object receiver) {
            assert !(receiver instanceof Double) || DefaultDoubleExportsGen.DYNAMIC_DISPATCH_LIBRARY_.getUncached().dispatch(receiver) == null : "Invalid library export. Exported receiver with dynamic dispatch found but not expected.";

            return receiver instanceof Double;
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

            return DefaultDoubleExports.fitsInByte((Double)receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean fitsInInt(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultDoubleExports.fitsInInt((Double)receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean fitsInShort(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultDoubleExports.fitsInShort((Double)receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean fitsInLong(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultDoubleExports.fitsInLong((Double)receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean fitsInFloat(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultDoubleExports.fitsInFloat((Double)receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public byte asByte(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultDoubleExports.asByte((Double)receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public short asShort(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultDoubleExports.asShort((Double)receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public int asInt(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultDoubleExports.asInt((Double)receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public long asLong(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultDoubleExports.asLong((Double)receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public float asFloat(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultDoubleExports.asFloat((Double)receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean isNumber(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultDoubleExports.isNumber((Double)receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean fitsInDouble(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultDoubleExports.fitsInDouble((Double)receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public double asDouble(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultDoubleExports.asDouble((Double)receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean hasLanguage(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultDoubleExports.hasLanguage((Double)receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Class<? extends TruffleLanguage<?>> getLanguage(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultDoubleExports.getLanguage((Double)receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean hasSourceLocation(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultDoubleExports.hasSourceLocation((Double)receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public SourceSection getSourceLocation(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultDoubleExports.getSourceLocation((Double)receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean hasMetaObject(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultDoubleExports.hasMetaObject((Double)receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Object getMetaObject(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultDoubleExports.getMetaObject((Double)receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Object toDisplayString(Object receiver, boolean allowSideEffects) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultDoubleExports.toDisplayString((Double)receiver, allowSideEffects);
         }
      }
   }
}
