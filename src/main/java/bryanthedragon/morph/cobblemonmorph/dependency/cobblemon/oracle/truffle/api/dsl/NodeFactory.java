
package com.oracle.truffle.api.dsl;

import com.oracle.truffle.api.nodes.Node;
import java.util.List;

public interface NodeFactory<T> {
    public T createNode(Object ... var1);

    public Class<T> getNodeClass();

    public List<List<Class<?>>> getNodeSignatures();

    public List<Class<? extends Node>> getExecutionSignature();

    default public T getUncachedInstance() {
        return null;
    }
}

