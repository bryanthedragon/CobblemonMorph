
package com.oracle.truffle.regex.tregex.string;

import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.regex.tregex.string.AbstractStringIterator;
import com.oracle.truffle.regex.tregex.string.StringBufferUTF16;

public interface AbstractString
extends Iterable<Integer> {
    public AbstractStringIterator iterator();

    public int encodedLength();

    public Object content();

    public AbstractString substring(int var1, int var2);

    public boolean regionMatches(int var1, AbstractString var2, int var3, int var4);

    public TruffleString asTString();

    public TruffleString.WithMask asTStringMask(TruffleString var1);

    default public String defaultToString() {
        StringBufferUTF16 sb = new StringBufferUTF16(this.encodedLength() * 2);
        AbstractStringIterator abstractStringIterator = this.iterator();
        while (abstractStringIterator.hasNext()) {
            int c = (Integer)abstractStringIterator.next();
            sb.append(c);
        }
        return sb.materialize().toString();
    }
}

