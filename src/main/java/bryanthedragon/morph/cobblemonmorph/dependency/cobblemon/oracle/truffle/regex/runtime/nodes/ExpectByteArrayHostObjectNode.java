
package com.oracle.truffle.regex.runtime.nodes;

import com.oracle.truffle.api.dsl.GenerateUncached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.regex.RegexLanguage;

@GenerateUncached
public abstract class ExpectByteArrayHostObjectNode
extends Node {
    public abstract byte[] execute(Object var1);

    @Specialization
    static byte[] doByteArray(byte[] input) {
        return input;
    }

    @Specialization
    byte[] doBoxed(Object input) {
        return (byte[])RegexLanguage.RegexContext.get(this).getEnv().asHostObject(input);
    }
}

