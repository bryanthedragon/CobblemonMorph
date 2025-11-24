
package com.oracle.truffle.api.source;

import org.graalvm.polyglot.io.ByteSequence;

class ByteSequenceWrapper
implements ByteSequence {
    private final ByteSequence delegate;

    ByteSequenceWrapper(ByteSequence delegate) {
        this.delegate = delegate;
    }

    @Override
    public int length() {
        return this.delegate.length();
    }

    @Override
    public byte byteAt(int index) {
        return this.delegate.byteAt(index);
    }

    @Override
    public ByteSequence subSequence(int start2, int end2) {
        return this.delegate.subSequence(start2, end2);
    }

    public boolean equals(Object obj) {
        if (obj instanceof ByteSequenceWrapper) {
            return this.delegate.equals(((ByteSequenceWrapper)obj).delegate);
        }
        return this.delegate.equals(obj);
    }

    public int hashCode() {
        return this.delegate.hashCode();
    }

    public String toString() {
        return this.delegate.toString();
    }
}

