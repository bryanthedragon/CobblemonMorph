
package com.oracle.js.parser;

public interface RecompilableScriptFunctionData {
    public RecompilableScriptFunctionData getScriptFunctionData(int var1);

    public int getFunctionNodeId();

    public int getFunctionFlags();

    public Object getEndParserState();
}

