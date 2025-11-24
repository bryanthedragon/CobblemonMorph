
package com.oracle.truffle.api.dsl;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.nodes.Node;
import java.util.Arrays;
import java.util.Objects;

public final class UnsupportedSpecializationException
extends RuntimeException {
    private static final long serialVersionUID = -2122892028296836269L;
    private final Node node;
    private final Node[] suppliedNodes;
    private final Object[] suppliedValues;

    @CompilerDirectives.TruffleBoundary
    public UnsupportedSpecializationException(Node node, Node[] suppliedNodes, Object ... suppliedValues) {
        Objects.requireNonNull(suppliedNodes, "The suppliedNodes parameter must not be null.");
        if (suppliedNodes.length != suppliedValues.length) {
            throw new IllegalArgumentException("The length of suppliedNodes must match the length of suppliedValues.");
        }
        this.node = node;
        this.suppliedNodes = suppliedNodes;
        this.suppliedValues = suppliedValues;
    }

    @Override
    public String getMessage() {
        StringBuilder str = new StringBuilder();
        str.append("Unexpected values provided for ").append(this.node).append(": ").append(Arrays.toString(this.suppliedValues)).append(", [");
        for (int i = 0; i < this.suppliedValues.length; ++i) {
            str.append(i == 0 ? "" : ",").append(this.suppliedValues[i] == null ? "null" : this.suppliedValues[i].getClass().getSimpleName());
        }
        return str.append("]").toString();
    }

    public Node getNode() {
        return this.node;
    }

    public Node[] getSuppliedNodes() {
        return this.suppliedNodes;
    }

    public Object[] getSuppliedValues() {
        return this.suppliedValues;
    }
}

