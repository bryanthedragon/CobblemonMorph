
package com.oracle.truffle.api.source;

class CharSequenceWrapper
implements CharSequence {
    private final CharSequence delegate;

    CharSequenceWrapper(CharSequence delegate) {
        this.delegate = delegate;
    }

    @Override
    public int length() {
        return this.delegate.length();
    }

    @Override
    public char charAt(int index) {
        return this.delegate.charAt(index);
    }

    @Override
    public CharSequence subSequence(int start2, int end2) {
        return this.delegate.subSequence(start2, end2);
    }

    public boolean equals(Object obj) {
        if (obj instanceof CharSequenceWrapper) {
            return this.delegate.equals(((CharSequenceWrapper)obj).delegate);
        }
        return this.delegate.equals(obj);
    }

    public int hashCode() {
        return this.delegate.hashCode();
    }

    @Override
    public String toString() {
        return this.delegate.toString();
    }
}

