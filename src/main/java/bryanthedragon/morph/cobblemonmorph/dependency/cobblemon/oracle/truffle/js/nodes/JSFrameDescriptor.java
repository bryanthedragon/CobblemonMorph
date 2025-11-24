
package com.oracle.truffle.js.nodes;

import com.oracle.js.parser.ir.Scope;
import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.FrameSlotKind;
import com.oracle.truffle.js.nodes.JSFrameSlot;
import com.oracle.truffle.js.nodes.access.ScopeFrameNode;
import com.oracle.truffle.js.runtime.objects.Undefined;
import java.util.Objects;
import java.util.StringJoiner;
import org.graalvm.collections.EconomicMap;

public final class JSFrameDescriptor {
    private final Object defaultValue;
    private final EconomicMap<Object, JSFrameSlot> identifierToSlotMap = EconomicMap.create();
    private int size;
    private FrameDescriptor frameDescriptor;

    public JSFrameDescriptor() {
        this(Undefined.instance);
    }

    public JSFrameDescriptor(Object defaultValue) {
        this.defaultValue = defaultValue;
    }

    public JSFrameSlot addFrameSlot(Object identifier) {
        return this.addFrameSlot(identifier, 0, FrameSlotKind.Illegal);
    }

    public JSFrameSlot addFrameSlot(Object identifier, FrameSlotKind kind) {
        return this.addFrameSlot(identifier, 0, kind);
    }

    public JSFrameSlot addFrameSlot(Object identifier, int flags, FrameSlotKind kind) {
        CompilerAsserts.neverPartOfCompilation();
        Objects.requireNonNull(identifier, "identifier");
        Objects.requireNonNull(kind, "kind");
        if (this.isClosed()) {
            throw new IllegalArgumentException("frame slot registration is closed: " + identifier);
        }
        if (this.identifierToSlotMap.containsKey(identifier)) {
            throw new IllegalArgumentException("duplicate frame slot: " + identifier);
        }
        int index = this.size;
        Object slotName = JSFrameDescriptor.toSlotName(identifier);
        assert (JSFrameSlot.isAllowedIdentifierType(slotName)) : slotName.getClass();
        JSFrameSlot slot = new JSFrameSlot(index, slotName, JSFrameDescriptor.toSlotFlags(flags), kind);
        ++this.size;
        this.identifierToSlotMap.put(identifier, slot);
        assert (this.identifierToSlotMap.size() == this.size);
        return slot;
    }

    public JSFrameSlot findFrameSlot(Object identifier) {
        CompilerAsserts.neverPartOfCompilation();
        return (JSFrameSlot)this.identifierToSlotMap.get(identifier);
    }

    public JSFrameSlot findOrAddFrameSlot(Object identifier) {
        CompilerAsserts.neverPartOfCompilation();
        JSFrameSlot result = this.findFrameSlot(identifier);
        if (result != null) {
            return result;
        }
        return this.addFrameSlot(identifier);
    }

    public JSFrameSlot findOrAddFrameSlot(Object identifier, FrameSlotKind kind) {
        CompilerAsserts.neverPartOfCompilation();
        JSFrameSlot result = this.findFrameSlot(identifier);
        if (result != null) {
            return result;
        }
        return this.addFrameSlot(identifier, kind);
    }

    public JSFrameSlot findOrAddFrameSlot(Object identifier, int flags, FrameSlotKind kind) {
        CompilerAsserts.neverPartOfCompilation();
        JSFrameSlot result = this.findFrameSlot(identifier);
        if (result != null) {
            return result;
        }
        return this.addFrameSlot(identifier, flags, kind);
    }

    public int getSize() {
        return this.size;
    }

    public boolean contains(Object identifier) {
        CompilerAsserts.neverPartOfCompilation();
        return this.identifierToSlotMap.containsKey(identifier);
    }

    public Iterable<Object> getIdentifiers() {
        CompilerAsserts.neverPartOfCompilation();
        return this.identifierToSlotMap.getKeys();
    }

    public Iterable<JSFrameSlot> getSlots() {
        CompilerAsserts.neverPartOfCompilation();
        return this.identifierToSlotMap.getValues();
    }

    public FrameDescriptor toFrameDescriptor() {
        FrameDescriptor descriptor;
        if (this.frameDescriptor != null) {
            return this.frameDescriptor;
        }
        FrameDescriptor.Builder b = FrameDescriptor.newBuilder(this.size);
        b.defaultValue(this.defaultValue);
        for (JSFrameSlot slot : this.identifierToSlotMap.getValues()) {
            int index = b.addSlot(slot.getKind(), slot.getIdentifier(), slot.getInfo());
            assert (slot.getIndex() == index);
        }
        this.frameDescriptor = descriptor = b.build();
        return descriptor;
    }

    public boolean isClosed() {
        return this.frameDescriptor != null;
    }

    public static JSFrameDescriptor createFunctionFrameDescriptor() {
        return new JSFrameDescriptor(Undefined.instance);
    }

    public static JSFrameDescriptor createBlockFrameDescriptor() {
        JSFrameDescriptor desc = new JSFrameDescriptor(Undefined.instance);
        desc.addFrameSlot(ScopeFrameNode.PARENT_SCOPE_IDENTIFIER, FrameSlotKind.Object);
        return desc;
    }

    private static int toSlotFlags(int flags) {
        return flags & 0x80BE4113;
    }

    private static Object toSlotName(Object identifier) {
        if (identifier instanceof ScopedIdentifier) {
            return ((ScopedIdentifier)identifier).identifier;
        }
        return identifier;
    }

    public static ScopedIdentifier scopedIdentifier(Object identifier, Scope scope) {
        return new ScopedIdentifier(identifier, scope);
    }

    public String toString() {
        StringJoiner slots = new StringJoiner(", ", "{", "}");
        for (JSFrameSlot slot : this.identifierToSlotMap.getValues()) {
            slots.add(slot.getIdentifier().toString());
        }
        return "FrameDescriptor[size=" + this.size + ", slots=" + slots + "]";
    }

    private static final class ScopedIdentifier {
        final Object identifier;
        final Scope scope;

        ScopedIdentifier(Object identifier, Scope scope) {
            this.identifier = Objects.requireNonNull(identifier);
            this.scope = scope;
        }

        public int hashCode() {
            return Objects.hash(this.identifier, this.scope);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof ScopedIdentifier)) {
                return false;
            }
            ScopedIdentifier other = (ScopedIdentifier)obj;
            return Objects.equals(this.identifier, other.identifier) && Objects.equals(this.scope, other.scope);
        }

        public String toString() {
            return this.identifier.toString();
        }
    }
}

