package org.graalvm.shadowed.org.jcodings;

public abstract class CanBeTrailTableEncoding extends MultiByteEncoding {
   protected final boolean[] CanBeTrailTable;

   protected CanBeTrailTableEncoding(String name, int minLength, int maxLength, int[] EncLen, int[][] Trans, short[] CTypeTable, boolean[] CanBeTrailTable) {
      super(name, minLength, maxLength, EncLen, Trans, CTypeTable);
      this.CanBeTrailTable = CanBeTrailTable;
   }

   @Override
   public int leftAdjustCharHead(byte[] bytes, int p, int s, int end) {
      if (s <= p) {
         return s;
      } else {
         int p_ = s;
         if (this.CanBeTrailTable[bytes[s] & 255]) {
            while (p_ > p) {
               if (this.EncLen[bytes[--p_] & 255] <= 1) {
                  p_++;
                  break;
               }
            }
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

   @Override
   public boolean isReverseMatchAllowed(byte[] bytes, int p, int end) {
      return !this.CanBeTrailTable[bytes[p] & 255];
   }
}
