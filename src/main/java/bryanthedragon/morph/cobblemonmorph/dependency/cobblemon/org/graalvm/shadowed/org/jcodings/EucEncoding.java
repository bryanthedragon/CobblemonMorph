package org.graalvm.shadowed.org.jcodings;

public abstract class EucEncoding extends MultiByteEncoding {
   protected EucEncoding(String name, int minLength, int maxLength, int[] EncLen, int[][] Trans, short[] CTypeTable) {
      super(name, minLength, maxLength, EncLen, Trans, CTypeTable);
   }

   protected abstract boolean isLead(int var1);

   @Override
   public int leftAdjustCharHead(byte[] bytes, int p, int s, int end) {
      if (s <= p) {
         return s;
      } else {
         int p_ = s;

         while (!this.isLead(bytes[p_] & 255) && p_ > p) {
            p_--;
         }

         int len = this.length(bytes, p_, end);
         if (p_ + len > s) {
            return p_;
         } else {
            p_ += len;
            return p_ + (s - p_ & -2);
         }
      }
   }
}
