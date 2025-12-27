package org.graalvm.shadowed.org.jcodings.specific;

public final class NonStrictSJISEncoding extends BaseSJISEncoding {
   public static final NonStrictSJISEncoding INSTANCE = new NonStrictSJISEncoding();

   protected NonStrictSJISEncoding() {
      super("Shift_JIS", null);
   }

   @Override
   public int length(byte[] bytes, int p, int end) {
      return this.length(bytes[p]);
   }
}
