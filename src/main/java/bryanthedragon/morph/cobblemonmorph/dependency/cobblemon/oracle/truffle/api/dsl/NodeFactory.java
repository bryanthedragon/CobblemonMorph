package com.oracle.truffle.api.dsl;

import com.oracle.truffle.api.nodes.Node;
import java.util.List;

public interface NodeFactory<T> {
   T createNode(Object... arguments);

   Class<T> getNodeClass();

   List<List<Class<?>>> getNodeSignatures();

   List<Class<? extends Node>> getExecutionSignature();

   default T getUncachedInstance() {
      return null;
   }
}
