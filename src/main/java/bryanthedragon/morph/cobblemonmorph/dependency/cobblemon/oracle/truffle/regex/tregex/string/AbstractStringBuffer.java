
package com.oracle.truffle.regex.tregex.string;

import com.oracle.truffle.regex.tregex.string.AbstractString;
import com.oracle.truffle.regex.tregex.string.Encodings;

public interface AbstractStringBuffer {
    public Encodings.Encoding getEncoding();

    public void append(int var1);

    public void appendOR(int var1, int var2);

    public void appendXOR(int var1, int var2);

    public void clear();

    public AbstractString materialize();
}

