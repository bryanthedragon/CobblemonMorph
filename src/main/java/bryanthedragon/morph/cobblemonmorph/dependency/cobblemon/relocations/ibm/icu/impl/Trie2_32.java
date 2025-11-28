package com.cobblemon.mod.relocations.ibm.icu.impl;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;

public class Trie2_32 extends Trie2 {
   Trie2_32() {
   }

   public static Trie2_32 createFromSerialized(ByteBuffer bytes) throws IOException {
      return (Trie2_32)Trie2.createFromSerialized(bytes);
   }

   @Override
   public final int get(int codePoint) {
      if (codePoint >= 0) {
         if (codePoint < 55296 || codePoint > 56319 && codePoint <= 65535) {
            int ix = this.index[codePoint >> 5];
            ix = (ix << 2) + (codePoint & 31);
            return this.data32[ix];
         }

         if (codePoint <= 65535) {
            int ix = this.index[2048 + (codePoint - 55296 >> 5)];
            ix = (ix << 2) + (codePoint & 31);
            return this.data32[ix];
         }

         if (codePoint < this.highStart) {
            int ix = 2080 + (codePoint >> 11);
            int var4 = this.index[ix];
            var4 += codePoint >> 5 & 63;
            int var6 = this.index[var4];
            var6 = (var6 << 2) + (codePoint & 31);
            return this.data32[var6];
         }

         if (codePoint <= 1114111) {
            return this.data32[this.highValueIndex];
         }
      }

      return this.errorValue;
   }

   @Override
   public int getFromU16SingleLead(char codeUnit) {
      int ix = this.index[codeUnit >> 5];
      ix = (ix << 2) + (codeUnit & 31);
      return this.data32[ix];
   }

   public int serialize(OutputStream os) throws IOException {
      DataOutputStream dos = new DataOutputStream(os);
      int bytesWritten = 0;
      bytesWritten += this.serializeHeader(dos);

      for (int i = 0; i < this.dataLength; i++) {
         dos.writeInt(this.data32[i]);
      }

      return bytesWritten + this.dataLength * 4;
   }

   public int getSerializedLength() {
      return 16 + this.header.indexLength * 2 + this.dataLength * 4;
   }

   @Override
   int rangeEnd(int startingCP, int limit, int value) {
      int cp = startingCP;
      int block = 0;
      int index2Block = 0;

      label66:
      while (cp < limit) {
         char var11;
         if (cp >= 55296 && (cp <= 56319 || cp > 65535)) {
            if (cp < 65535) {
               var11 = 2048;
               block = this.index[var11 + (cp - 55296 >> 5)] << 2;
            } else {
               if (cp >= this.highStart) {
                  if (value == this.data32[this.highValueIndex]) {
                     cp = limit;
                  }
                  break;
               }

               int ix = 2080 + (cp >> 11);
               var11 = this.index[ix];
               block = this.index[var11 + (cp >> 5 & 63)] << 2;
            }
         } else {
            var11 = 0;
            block = this.index[cp >> 5] << 2;
         }

         if (var11 == this.index2NullOffset) {
            if (value != this.initialValue) {
               break;
            }

            cp += 2048;
         } else if (block == this.dataNullOffset) {
            if (value != this.initialValue) {
               break;
            }

            cp += 32;
         } else {
            int startIx = block + (cp & 31);
            int limitIx = block + 32;

            for (int ix = startIx; ix < limitIx; ix++) {
               if (this.data32[ix] != value) {
                  cp += ix - startIx;
                  break label66;
               }
            }

            cp += limitIx - startIx;
         }
      }

      if (cp > limit) {
         cp = limit;
      }

      return cp - 1;
   }
}
