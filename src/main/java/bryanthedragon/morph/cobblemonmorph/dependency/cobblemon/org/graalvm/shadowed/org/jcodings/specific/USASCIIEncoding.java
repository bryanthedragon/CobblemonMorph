
package org.graalvm.shadowed.org.jcodings.specific;

import org.graalvm.shadowed.org.jcodings.SingleByteEncoding;
import org.graalvm.shadowed.org.jcodings.ascii.AsciiTables;

public final class USASCIIEncoding
extends SingleByteEncoding {
    public static final USASCIIEncoding INSTANCE = new USASCIIEncoding();

    protected USASCIIEncoding() {
        super("US-ASCII", AsciiTables.AsciiCtypeTable, AsciiTables.ToLowerCaseTable);
    }

    @Override
    public int length(byte[] bytes, int p, int end2) {
        return (bytes[p] & 0x80) == 0 ? 1 : -1;
    }

    @Override
    public final byte[] toLowerCaseTable() {
        return this.LowerCaseTable;
    }

    @Override
    public String getCharsetName() {
        return "US-ASCII";
    }

    @Override
    public boolean isCodeCType(int code, int ctype) {
        return code < 128 ? this.isCodeCTypeInternal(code, ctype) : false;
    }
}

