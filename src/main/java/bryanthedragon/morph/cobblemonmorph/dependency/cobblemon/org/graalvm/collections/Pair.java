
package org.graalvm.collections;

import java.util.Objects;

public final class Pair<L, R> {
    private static final Pair<Object, Object> EMPTY = new Pair<Object, Object>(null, null);
    private final L left;
    private final R right;

    public static <L, R> Pair<L, R> empty() {
        return EMPTY;
    }

    public static <L, R> Pair<L, R> createLeft(L left) {
        if (left == null) {
            return Pair.empty();
        }
        return new Pair<L, Object>(left, null);
    }

    public static <L, R> Pair<L, R> createRight(R right) {
        if (right == null) {
            return Pair.empty();
        }
        return new Pair<Object, R>(null, right);
    }

    public static <L, R> Pair<L, R> create(L left, R right) {
        if (right == null && left == null) {
            return Pair.empty();
        }
        return new Pair<L, R>(left, right);
    }

    private Pair(L left, R right) {
        this.left = left;
        this.right = right;
    }

    public L getLeft() {
        return this.left;
    }

    public R getRight() {
        return this.right;
    }

    public int hashCode() {
        return Objects.hashCode(this.left) + 31 * Objects.hashCode(this.right);
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof Pair) {
            Pair pair = (Pair)obj;
            return Objects.equals(this.left, pair.left) && Objects.equals(this.right, pair.right);
        }
        return false;
    }

    public String toString() {
        return "(" + this.left + ", " + this.right + ")";
    }
}

