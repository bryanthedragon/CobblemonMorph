
package com.oracle.truffle.js.runtime;

import org.graalvm.options.OptionValues;

public interface ParserOptions {
    public int getEcmaScriptVersion();

    public boolean isScripting();

    public ParserOptions putOptions(OptionValues var1);
}

