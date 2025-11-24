
package com.oracle.truffle.regex.tregex.string;

import java.util.PrimitiveIterator;

public abstract class AbstractStringIterator
implements PrimitiveIterator.OfInt {
    protected int i;

    public int getIndex() {
        return this.i;
    }
}

