package org.graalvm.shadowed.org.jcodings.specific;

public final class NonStrictEUCJPEncoding extends BaseEUCJPEncoding {
   public static final NonStrictEUCJPEncoding INSTANCE = new NonStrictEUCJPEncoding();

   protected NonStrictEUCJPEncoding() {
      super(null);
   }

   @Override
   public int length(byte[] bytes, int p, int end) {
      return this.length(bytes[p]);
   }
}
