
package org.graalvm.word;

import org.graalvm.word.ComparableWord;

public interface PointerBase
extends ComparableWord {
    public boolean isNull();

    public boolean isNonNull();
}

