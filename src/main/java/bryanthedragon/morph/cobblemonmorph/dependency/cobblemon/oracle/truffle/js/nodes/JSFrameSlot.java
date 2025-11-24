
package com.oracle.truffle.js.nodes;

import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.FrameSlotKind;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.runtime.JSFrameUtil;
import com.oracle.truffle.js.runtime.util.InternalSlotId;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public final class JSFrameSlot {
    private final int index;
    private final int flags;
    private final Object identifier;
    private final Object info;
    private final FrameSlotKind kind;
    private boolean hasBeenDeclared;
    private int mappedParameterIndex = -1;

    public JSFrameSlot(int index, Object identifier, int flags, FrameSlotKind kind) {
        this.index = index;
        this.flags = flags;
        this.identifier = Objects.requireNonNull(identifier);
        this.info = FrameSlotFlags.of(flags);
        this.kind = kind;
    }

    public JSFrameSlot(int index, Object identifier, int flags) {
        this(index, identifier, flags, FrameSlotKind.Illegal);
    }

    public static JSFrameSlot fromIndexedFrameSlot(FrameDescriptor desc, int index) {
        return new JSFrameSlot(index, desc.getSlotName(index), JSFrameUtil.getFlags(desc, index), desc.getSlotKind(index));
    }

    public int getIndex() {
        return this.index;
    }

    public Object getIdentifier() {
        return this.identifier;
    }

    public int getFlags() {
        return this.flags;
    }

    public Object getInfo() {
        return this.info;
    }

    public FrameSlotKind getKind() {
        return this.kind;
    }

    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = 31 * result + this.index;
        result = 31 * result + this.flags;
        result = 31 * result + this.identifier.hashCode();
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof JSFrameSlot)) {
            return false;
        }
        JSFrameSlot other = (JSFrameSlot)obj;
        if (this.index != other.index) {
            return false;
        }
        if (this.flags != other.flags) {
            return false;
        }
        return this.identifier.equals(other.identifier);
    }

    public String toString() {
        return "FrameSlot[" + this.index + ", " + this.identifier + (String)(this.flags != 0 ? ", " + this.flags : "") + (String)(this.kind != FrameSlotKind.Illegal ? ", " + this.kind : "") + "]";
    }

    public static boolean isAllowedIdentifierType(Object identifier) {
        return identifier instanceof TruffleString || identifier instanceof InternalSlotId;
    }

    public boolean hasBeenDeclared() {
        return this.hasBeenDeclared;
    }

    public void setHasBeenDeclared(boolean declared) {
        this.hasBeenDeclared = declared;
    }

    public int getMappedParameterIndex() {
        return this.mappedParameterIndex;
    }

    public void setMappedParameterIndex(int mappedParameterIndex) {
        this.mappedParameterIndex = mappedParameterIndex;
    }

    static final class FrameSlotFlags {
        private static final Map<Integer, Integer> cachedFlags = new ConcurrentHashMap<Integer, Integer>();

        private FrameSlotFlags() {
        }

        static Integer of(int flags) {
            Integer boxed = flags;
            if (flags < -128 || flags > 127) {
                Integer cached = cachedFlags.get(boxed);
                if (cached != null) {
                    return cached;
                }
                cached = cachedFlags.putIfAbsent(boxed, boxed);
                if (cached != null) {
                    return cached;
                }
            }
            return boxed;
        }
    }
}

