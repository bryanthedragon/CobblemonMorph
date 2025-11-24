
package org.graalvm.word;

import org.graalvm.word.WordBase;

public interface ComparableWord
extends WordBase {
    public boolean equal(ComparableWord var1);

    public boolean notEqual(ComparableWord var1);
}

