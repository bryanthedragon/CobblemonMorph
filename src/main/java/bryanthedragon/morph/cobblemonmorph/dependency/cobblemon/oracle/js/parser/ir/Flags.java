
package com.oracle.js.parser.ir;

import com.oracle.js.parser.ir.LexicalContext;
import com.oracle.js.parser.ir.LexicalContextNode;

public interface Flags<T extends LexicalContextNode> {
    public int getFlags();

    public boolean getFlag(int var1);

    public T setFlag(LexicalContext var1, int var2);

    public T setFlags(LexicalContext var1, int var2);
}

