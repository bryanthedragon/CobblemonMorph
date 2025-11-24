
package com.oracle.truffle.api.impl.asm;

public final class Handle {
    private final int tag;
    private final String owner;
    private final String name;
    private final String descriptor;
    private final boolean isInterface;

    @Deprecated
    public Handle(int tag, String owner, String name, String descriptor) {
        this(tag, owner, name, descriptor, tag == 9);
    }

    public Handle(int tag, String owner, String name, String descriptor, boolean isInterface) {
        this.tag = tag;
        this.owner = owner;
        this.name = name;
        this.descriptor = descriptor;
        this.isInterface = isInterface;
    }

    public int getTag() {
        return this.tag;
    }

    public String getOwner() {
        return this.owner;
    }

    public String getName() {
        return this.name;
    }

    public String getDesc() {
        return this.descriptor;
    }

    public boolean isInterface() {
        return this.isInterface;
    }

    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof Handle)) {
            return false;
        }
        Handle handle2 = (Handle)object;
        return this.tag == handle2.tag && this.isInterface == handle2.isInterface && this.owner.equals(handle2.owner) && this.name.equals(handle2.name) && this.descriptor.equals(handle2.descriptor);
    }

    public int hashCode() {
        return this.tag + (this.isInterface ? 64 : 0) + this.owner.hashCode() * this.name.hashCode() * this.descriptor.hashCode();
    }

    public String toString() {
        return this.owner + '.' + this.name + this.descriptor + " (" + this.tag + (this.isInterface ? " itf" : "") + ')';
    }
}

