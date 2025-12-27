package com.oracle.truffle.api.interop;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.frame.Frame;
import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.MaterializedFrame;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.library.ExportMessage;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.RootNode;
import com.oracle.truffle.api.source.SourceSection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.Map.Entry;

@ExportLibrary(value = NodeLibrary.class, receiverType = Node.class)
final class DefaultNodeExports {
   @ExportMessage
   static boolean hasScope(Node node, Frame frame) {
      return hasScopeSlowPath(node);
   }

   @CompilerDirectives.TruffleBoundary
   private static boolean hasScopeSlowPath(Node node) {
      RootNode root = node.getRootNode();
      TruffleLanguage<?> language = InteropAccessor.NODES.getLanguage(root);
      return language != null && (node == root || InteropAccessor.INSTRUMENT.isInstrumentable(node));
   }

   @ExportMessage
   static Object getScope(Node node, Frame frame, boolean nodeEnter) throws UnsupportedMessageException {
      return getScopeSlowPath(node, frame != null ? frame.materialize() : null);
   }

   @CompilerDirectives.TruffleBoundary
   private static Object getScopeSlowPath(Node node, MaterializedFrame frame) throws UnsupportedMessageException {
      RootNode root = node.getRootNode();
      TruffleLanguage<?> language = InteropAccessor.NODES.getLanguage(root);
      if (language == null || node != root && !InteropAccessor.INSTRUMENT.isInstrumentable(node)) {
         throw UnsupportedMessageException.create();
      } else {
         return createDefaultScope(root, frame, (Class<? extends TruffleLanguage<?>>)language.getClass());
      }
   }

   private static boolean isInternal(Object identifier) {
      return identifier == null ? true : InteropAccessor.INSTRUMENT.isInputValueSlotIdentifier(identifier);
   }

   @CompilerDirectives.TruffleBoundary
   private static Object createDefaultScope(RootNode root, MaterializedFrame frame, Class<? extends TruffleLanguage<?>> language) {
      LinkedHashMap<String, Object> slotsMap = new LinkedHashMap<>();
      FrameDescriptor descriptor = frame == null ? root.getFrameDescriptor() : frame.getFrameDescriptor();

      for (Entry<Object, Integer> entry : descriptor.getAuxiliarySlots().entrySet()) {
         if (!isInternal(entry.getKey()) && (frame == null || InteropLibrary.isValidValue(frame.getAuxiliarySlot(entry.getValue())))) {
            slotsMap.put(Objects.toString(entry.getKey()), entry.getValue());
         }
      }

      return new DefaultNodeExports.DefaultScope(slotsMap, root, frame, language);
   }

   @ExportLibrary(InteropLibrary.class)
   static final class DefaultScope implements TruffleObject {
      private final Map<String, Object> slots;
      private final RootNode root;
      private final Frame frame;
      private final Class<? extends TruffleLanguage<?>> language;

      private DefaultScope(Map<String, Object> slots, RootNode root, Frame frame, Class<? extends TruffleLanguage<?>> language) {
         this.slots = slots;
         this.root = root;
         this.frame = frame;
         this.language = language;
      }

      public static boolean isInstance(TruffleObject obj) {
         return obj instanceof DefaultNodeExports.DefaultScope;
      }

      @ExportMessage
      boolean hasLanguage() {
         return this.language != null;
      }

      @ExportMessage
      Class<? extends TruffleLanguage<?>> getLanguage() throws UnsupportedMessageException {
         if (this.language == null) {
            throw UnsupportedMessageException.create();
         } else {
            return this.language;
         }
      }

      @ExportMessage
      boolean isScope() {
         return true;
      }

      @ExportMessage
      boolean hasMembers() {
         return true;
      }

      @ExportMessage
      @CompilerDirectives.TruffleBoundary
      Object readMember(String member) throws UnknownIdentifierException {
         if (this.frame == null) {
            return DefaultNodeExports.DefaultScopeNull.INSTANCE;
         } else {
            Object slot = this.slots.get(member);
            if (slot == null) {
               throw UnknownIdentifierException.create(member);
            } else {
               return this.frame.getAuxiliarySlot((Integer)slot);
            }
         }
      }

      @ExportMessage
      @CompilerDirectives.TruffleBoundary
      Object getMembers(boolean includeInternal) {
         return new DefaultNodeExports.DefaultScopeMembers(this.slots.keySet());
      }

      @ExportMessage
      @CompilerDirectives.TruffleBoundary
      boolean isMemberReadable(String member) {
         return this.slots.containsKey(member);
      }

      @ExportMessage
      @CompilerDirectives.TruffleBoundary
      boolean isMemberModifiable(String member) {
         return this.slots.containsKey(member) && this.frame != null;
      }

      @ExportMessage
      @CompilerDirectives.TruffleBoundary
      void writeMember(String member, Object value) throws UnknownIdentifierException, UnsupportedMessageException {
         if (this.frame == null) {
            throw UnsupportedMessageException.create();
         } else {
            Object slot = this.slots.get(member);
            if (slot == null) {
               throw UnknownIdentifierException.create(member);
            } else {
               this.frame.setAuxiliarySlot((Integer)slot, value);
            }
         }
      }

      @ExportMessage
      boolean isMemberInsertable(String member) {
         return false;
      }

      @ExportMessage
      @CompilerDirectives.TruffleBoundary
      boolean hasSourceLocation() {
         return this.root.getSourceSection() != null;
      }

      @ExportMessage
      @CompilerDirectives.TruffleBoundary
      SourceSection getSourceLocation() {
         return this.root.getSourceSection();
      }

      @ExportMessage
      @CompilerDirectives.TruffleBoundary
      Object toDisplayString(boolean allowSideEffects) {
         String name = this.root.getName();
         if (name == null) {
            name = "local";
         }

         return name;
      }
   }

   @ExportLibrary(InteropLibrary.class)
   static final class DefaultScopeMembers implements TruffleObject {
      final String[] names;

      DefaultScopeMembers(Set<String> names) {
         this.names = names.toArray(new String[0]);
      }

      @ExportMessage
      boolean hasArrayElements() {
         return true;
      }

      @ExportMessage
      long getArraySize() {
         return this.names.length;
      }

      @ExportMessage
      Object readArrayElement(long index) throws InvalidArrayIndexException {
         if (!this.isArrayElementReadable(index)) {
            throw InvalidArrayIndexException.create(index);
         } else {
            return this.names[(int)index];
         }
      }

      @ExportMessage
      boolean isArrayElementReadable(long index) {
         return index >= 0L && index < this.names.length;
      }
   }

   @ExportLibrary(InteropLibrary.class)
   static final class DefaultScopeNull implements TruffleObject {
      private static final DefaultNodeExports.DefaultScopeNull INSTANCE = new DefaultNodeExports.DefaultScopeNull();

      private DefaultScopeNull() {
      }

      @ExportMessage
      boolean isNull() {
         return true;
      }
   }
}
