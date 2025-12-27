package com.oracle.truffle.js.runtime;

import org.graalvm.options.OptionValues;

public interface ParserOptions {
   int getEcmaScriptVersion();

   boolean isScripting();

   ParserOptions putOptions(OptionValues options);
}
