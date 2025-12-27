package com.oracle.truffle.js.parser;

import com.oracle.truffle.api.source.Source;
import com.oracle.truffle.js.nodes.NodeFactory;
import com.oracle.truffle.js.runtime.JSContext;

public interface SnapshotProvider {
   Object apply(NodeFactory nodeFactory, JSContext context, Source source);
}
