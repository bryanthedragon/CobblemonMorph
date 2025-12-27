package org.graalvm.shadowed.org.jcodings.specific;

import org.graalvm.shadowed.org.jcodings.SingleByteEncoding;
import org.graalvm.shadowed.org.jcodings.ascii.AsciiTables;

public final class ASCIIEncoding extends SingleByteEncoding {
   public static final ASCIIEncoding INSTANCE = new ASCIIEncoding();

   protected ASCIIEncoding() {
      super("ASCII-8BIT", AsciiTables.AsciiCtypeTable, AsciiTables.ToLowerCaseTable);
   }

   @Override
   public final byte[] toLowerCaseTable() {
      return this.LowerCaseTable;
   }

   @Override
   public String getCharsetName() {
      return "ISO-8859-1";
   }

   @Override
   public boolean isCodeCType(int code, int ctype) {
      return code < 128 ? this.isCodeCTypeInternal(code, ctype) : false;
   }
}
