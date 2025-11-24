
package com.oracle.truffle.js.parser;

import com.oracle.truffle.api.source.Source;
import com.oracle.truffle.js.nodes.ScriptNode;
import com.oracle.truffle.js.parser.SnapshotProvider;
import com.oracle.truffle.js.runtime.Evaluator;
import com.oracle.truffle.js.runtime.JSContext;
import java.nio.ByteBuffer;

public interface JSParser
extends Evaluator {
    public ScriptNode parseScript(JSContext var1, Source var2, ByteBuffer var3);

    public ScriptNode parseScript(JSContext var1, Source var2, SnapshotProvider var3);
}

