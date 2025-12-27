package com.oracle.js.parser;

public interface RecompilableScriptFunctionData {
   RecompilableScriptFunctionData getScriptFunctionData(int functionId);

   int getFunctionNodeId();

   int getFunctionFlags();

   Object getEndParserState();
}
