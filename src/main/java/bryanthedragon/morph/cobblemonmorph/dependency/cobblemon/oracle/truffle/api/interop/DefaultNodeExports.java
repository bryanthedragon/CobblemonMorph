
package com.oracle.truffle.api.interop;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.frame.Frame;
import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.MaterializedFrame;
import com.oracle.truffle.api.interop.InteropAccessor;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.InvalidArrayIndexException;
import com.oracle.truffle.api.interop.NodeLibrary;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.interop.UnknownIdentifierException;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.library.ExportMessage;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.RootNode;
import com.oracle.truffle.api.source.SourceSection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

@ExportLibrary(value=NodeLibrary.class, receiverType=Node.class)
final class DefaultNodeExports {
    DefaultNodeExports() {
    }

    @ExportMessage
    static boolean hasScope(Node node, Frame frame) {
        return DefaultNodeExports.hasScopeSlowPath(node);
    }

    @CompilerDirectives.TruffleBoundary
    private static boolean hasScopeSlowPath(Node node) {
        RootNode root = node.getRootNode();
        TruffleLanguage<?> language = InteropAccessor.NODES.getLanguage(root);
        return language != null && (node == root || InteropAccessor.INSTRUMENT.isInstrumentable(node));
    }

    @ExportMessage
    static Object getScope(Node node, Frame frame, boolean nodeEnter) throws UnsupportedMessageException {
        return DefaultNodeExports.getScopeSlowPath(node, frame != null ? frame.materialize() : null);
    }

    @CompilerDirectives.TruffleBoundary
    private static Object getScopeSlowPath(Node node, MaterializedFrame frame) throws UnsupportedMessageException {
        RootNode root = node.getRootNode();
        TruffleLanguage<?> language = InteropAccessor.NODES.getLanguage(root);
        if (language != null && (node == root || InteropAccessor.INSTRUMENT.isInstrumentable(node))) {
            return DefaultNodeExports.createDefaultScope(root, frame, language.getClass());
        }
        throw UnsupportedMessageException.create();
    }

    private static boolean isInternal(Object identifier) {
        if (identifier == null) {
            return true;
        }
        return InteropAccessor.INSTRUMENT.isInputValueSlotIdentifier(identifier);
    }

    @CompilerDirectives.TruffleBoundary
    private static Object createDefaultScope(RootNode root, MaterializedFrame frame, Class<? extends TruffleLanguage<?>> language) {
        LinkedHashMap<String, Object> slotsMap = new LinkedHashMap<String, Object>();
        FrameDescriptor descriptor = frame == null ? root.getFrameDescriptor() : frame.getFrameDescriptor();
        for (Map.Entry<Object, Integer> entry : descriptor.getAuxiliarySlots().entrySet()) {
            if (DefaultNodeExports.isInternal(entry.getKey()) || frame != null && !InteropLibrary.isValidValue(frame.getAuxiliarySlot(entry.getValue()))) continue;
            slotsMap.put(Objects.toString(entry.getKey()), entry.getValue());
        }
        return new DefaultScope(slotsMap, root, frame, language);
    }

    @ExportLibrary(value=InteropLibrary.class)
    static final class DefaultScopeMembers
    implements TruffleObject {
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
            }
            return this.names[(int)index];
        }

        @ExportMessage
        boolean isArrayElementReadable(long index) {
            return index >= 0L && index < (long)this.names.length;
        }
    }

    @ExportLibrary(value=InteropLibrary.class)
    static final class DefaultScopeNull
    implements TruffleObject {
        private static final DefaultScopeNull INSTANCE = new DefaultScopeNull();

        private DefaultScopeNull() {
        }

        @ExportMessage
        boolean isNull() {
            return true;
        }
    }

    @ExportLibrary(value=InteropLibrary.class)
    static final class DefaultScope
    implements TruffleObject {
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
            return obj instanceof DefaultScope;
        }

        @ExportMessage
        boolean hasLanguage() {
            return this.language != null;
        }

        @ExportMessage
        Class<? extends TruffleLanguage<?>> getLanguage() throws UnsupportedMessageException {
            if (this.language == null) {
                throw UnsupportedMessageException.create();
            }
            return this.language;
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
                return DefaultScopeNull.INSTANCE;
            }
            Object slot = this.slots.get(member);
            if (slot == null) {
                throw UnknownIdentifierException.create(member);
            }
            return this.frame.getAuxiliarySlot((Integer)slot);
        }

        @ExportMessage
        @CompilerDirectives.TruffleBoundary
        Object getMembers(boolean includeInternal) {
            return new DefaultScopeMembers(this.slots.keySet());
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
        void writeMember(String member, Object value2) throws UnknownIdentifierException, UnsupportedMessageException {
            if (this.frame == null) {
                throw UnsupportedMessageException.create();
            }
            Object slot = this.slots.get(member);
            if (slot == null) {
                throw UnknownIdentifierException.create(member);
            }
            this.frame.setAuxiliarySlot((Integer)slot, value2);
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
}

