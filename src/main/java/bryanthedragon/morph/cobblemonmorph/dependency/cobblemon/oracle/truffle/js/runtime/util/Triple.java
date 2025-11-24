
package com.oracle.truffle.js.runtime.util;

import com.oracle.truffle.api.CompilerDirectives;
import java.util.Objects;

public final class Triple<T, U, P> {
    private final T first;
    private final U second;
    private final P third;

    public Triple(T first, U second, P third) {
        this.first = first;
        this.second = second;
        this.third = third;
    }

    public T getFirst() {
        return this.first;
    }

    public U getSecond() {
        return this.second;
    }

    public P getThird() {
        return this.third;
    }

    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = 31 * result + Objects.hashCode(this.first);
        result = 31 * result + Objects.hashCode(this.second);
        result = 31 * result + Objects.hashCode(this.third);
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Triple)) {
            return false;
        }
        Triple other = (Triple)obj;
        return Objects.equals(this.first, other.first) && Objects.equals(this.second, other.second) && Objects.equals(this.third, other.third);
    }

    @CompilerDirectives.TruffleBoundary
    public String toString() {
        return "(" + this.first + ", " + this.second + ", " + this.third + ")";
    }
}

