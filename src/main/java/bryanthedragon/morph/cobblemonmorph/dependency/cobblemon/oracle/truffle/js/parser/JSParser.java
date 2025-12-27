package com.oracle.truffle.js.parser;

import com.oracle.truffle.api.source.Source;
import com.oracle.truffle.js.nodes.ScriptNode;
import com.oracle.truffle.js.runtime.Evaluator;
import com.oracle.truffle.js.runtime.JSContext;
import java.nio.ByteBuffer;

public interface JSParser extends Evaluator {
   ScriptNode parseScript(JSContext context, Source source, ByteBuffer binary);

   ScriptNode parseScript(JSContext context, Source source, SnapshotProvider snapshotProvider);
}
