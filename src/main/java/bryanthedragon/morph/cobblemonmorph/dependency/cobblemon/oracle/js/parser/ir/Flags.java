package com.oracle.js.parser.ir;

public interface Flags<T extends LexicalContextNode> {
   int getFlags();

   boolean getFlag(int flag);

   T setFlag(final LexicalContext lc, int flag);

   T setFlags(final LexicalContext lc, int flags);
}
