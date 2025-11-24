
package org.graalvm.word.impl;

import org.graalvm.word.WordBase;

public abstract class WordBoxFactory {
    protected static WordBoxFactory boxFactory;

    protected abstract <T extends WordBase> T boxImpl(long var1);

    public static <T extends WordBase> T box(long val) {
        return boxFactory.boxImpl(val);
    }
}

