package com.cobblemon.mod.relocations.ibm.icu.util;

import java.util.Arrays;

public final class MutableCodePointTrie extends CodePointMap implements Cloneable {
   private static final int MAX_UNICODE = 1114111;
   private static final int UNICODE_LIMIT = 1114112;
   private static final int BMP_LIMIT = 65536;
   private static final int ASCII_LIMIT = 128;
   private static final int I_LIMIT = 69632;
   private static final int BMP_I_LIMIT = 4096;
   private static final int ASCII_I_LIMIT = 8;
   private static final int SMALL_DATA_BLOCKS_PER_BMP_BLOCK = 4;
   private static final byte ALL_SAME = 0;
   private static final byte MIXED = 1;
   private static final byte SAME_AS = 2;
   private static final int INITIAL_DATA_LENGTH = 16384;
   private static final int MEDIUM_DATA_LENGTH = 131072;
   private static final int MAX_DATA_LENGTH = 1114112;
   private static final byte I3_NULL = 0;
   private static final byte I3_BMP = 1;
   private static final byte I3_16 = 2;
   private static final byte I3_18 = 3;
   private static final int INDEX_3_18BIT_BLOCK_LENGTH = 36;
   private int[] index;
   private int index3NullOffset;
   private int[] data;
   private int dataLength;
   private int dataNullOffset;
   private int origInitialValue;
   private int initialValue;
   private int errorValue;
   private int highStart;
   private int highValue;
   private char[] index16;
   private byte[] flags = new byte[69632];

   public MutableCodePointTrie(int initialValue, int errorValue) {
      this.index = new int[4096];
      this.index3NullOffset = -1;
      this.data = new int[16384];
      this.dataNullOffset = -1;
      this.origInitialValue = initialValue;
      this.initialValue = initialValue;
      this.errorValue = errorValue;
      this.highValue = initialValue;
   }

   public MutableCodePointTrie clone() {
      try {
         MutableCodePointTrie builder = (MutableCodePointTrie)super.clone();
         int iCapacity = this.highStart <= 65536 ? 4096 : 69632;
         builder.index = new int[iCapacity];
         builder.flags = new byte[69632];
         int i = 0;

         for (int iLimit = this.highStart >> 4; i < iLimit; i++) {
            builder.index[i] = this.index[i];
            builder.flags[i] = this.flags[i];
         }

         builder.index3NullOffset = this.index3NullOffset;
         builder.data = (int[])this.data.clone();
         builder.dataLength = this.dataLength;
         builder.dataNullOffset = this.dataNullOffset;
         builder.origInitialValue = this.origInitialValue;
         builder.initialValue = this.initialValue;
         builder.errorValue = this.errorValue;
         builder.highStart = this.highStart;
         builder.highValue = this.highValue;

         assert this.index16 == null;

         return builder;
      } catch (CloneNotSupportedException var5) {
         return null;
      }
   }

   public static MutableCodePointTrie fromCodePointMap(CodePointMap map) {
      int errorValue = map.get(-1);
      int initialValue = map.get(1114111);
      MutableCodePointTrie mutableTrie = new MutableCodePointTrie(initialValue, errorValue);
      CodePointMap.Range range = new CodePointMap.Range();
      int start = 0;

      while (map.getRange(start, null, range)) {
         int end = range.getEnd();
         int value = range.getValue();
         if (value != initialValue) {
            if (start == end) {
               mutableTrie.set(start, value);
            } else {
               mutableTrie.setRange(start, end, value);
            }
         }

         start = end + 1;
      }

      return mutableTrie;
   }

   private void clear() {
      this.index3NullOffset = this.dataNullOffset = -1;
      this.dataLength = 0;
      this.highValue = this.initialValue = this.origInitialValue;
      this.highStart = 0;
      this.index16 = null;
   }

   @Override
   public int get(int c) {
      if (c < 0 || 1114111 < c) {
         return this.errorValue;
      } else if (c >= this.highStart) {
         return this.highValue;
      } else {
         int i = c >> 4;
         return this.flags[i] == 0 ? this.index[i] : this.data[this.index[i] + (c & 15)];
      }
   }

   private static final int maybeFilterValue(int value, int initialValue, int nullValue, CodePointMap.ValueFilter filter) {
      if (value == initialValue) {
         value = nullValue;
      } else if (filter != null) {
         value = filter.apply(value);
      }

      return value;
   }

   @Override
   public boolean getRange(int start, CodePointMap.ValueFilter filter, CodePointMap.Range range) {
      if (start < 0 || 1114111 < start) {
         return false;
      } else if (start >= this.highStart) {
         int value = this.highValue;
         if (filter != null) {
            value = filter.apply(value);
         }

         range.set(start, 1114111, value);
         return true;
      } else {
         int nullValue = this.initialValue;
         if (filter != null) {
            nullValue = filter.apply(nullValue);
         }

         int c = start;
         int trieValue = 0;
         int value = 0;
         boolean haveValue = false;
         int i = start >> 4;

         do {
            if (this.flags[i] == 0) {
               int trieValue2 = this.index[i];
               if (haveValue) {
                  if (trieValue2 != trieValue) {
                     if (filter == null || maybeFilterValue(trieValue2, this.initialValue, nullValue, filter) != value) {
                        range.set(start, c - 1, value);
                        return true;
                     }

                     trieValue = trieValue2;
                  }
               } else {
                  trieValue = trieValue2;
                  value = maybeFilterValue(trieValue2, this.initialValue, nullValue, filter);
                  haveValue = true;
               }

               c = c + 16 & -16;
            } else {
               int di = this.index[i] + (c & 15);
               int trieValue2 = this.data[di];
               if (haveValue) {
                  if (trieValue2 != trieValue) {
                     if (filter == null || maybeFilterValue(trieValue2, this.initialValue, nullValue, filter) != value) {
                        range.set(start, c - 1, value);
                        return true;
                     }

                     trieValue = trieValue2;
                  }
               } else {
                  trieValue = trieValue2;
                  value = maybeFilterValue(trieValue2, this.initialValue, nullValue, filter);
                  haveValue = true;
               }

               while ((++c & 15) != 0) {
                  trieValue2 = this.data[++di];
                  if (trieValue2 != trieValue) {
                     if (filter == null || maybeFilterValue(trieValue2, this.initialValue, nullValue, filter) != value) {
                        range.set(start, c - 1, value);
                        return true;
                     }

                     trieValue = trieValue2;
                  }
               }
            }

            i++;
         } while (c < this.highStart);

         assert haveValue;

         if (maybeFilterValue(this.highValue, this.initialValue, nullValue, filter) != value) {
            range.set(start, c - 1, value);
         } else {
            range.set(start, 1114111, value);
         }

         return true;
      }
   }

   private void writeBlock(int block, int value) {
      int limit = block + 16;
      Arrays.fill(this.data, block, limit, value);
   }

   public void set(int c, int value) {
      if (c >= 0 && 1114111 >= c) {
         this.ensureHighStart(c);
         int block = this.getDataBlock(c >> 4);
         this.data[block + (c & 15)] = value;
      } else {
         throw new IllegalArgumentException("invalid code point");
      }
   }

   private void fillBlock(int block, int start, int limit, int value) {
      Arrays.fill(this.data, block + start, block + limit, value);
   }

   public void setRange(int start, int end, int value) {
      if (start >= 0 && 1114111 >= start && end >= 0 && 1114111 >= end && start <= end) {
         this.ensureHighStart(end);
         int limit = end + 1;
         if ((start & 15) != 0) {
            int block = this.getDataBlock(start >> 4);
            int nextStart = start + 15 & -16;
            if (nextStart > limit) {
               this.fillBlock(block, start & 15, limit & 15, value);
               return;
            }

            this.fillBlock(block, start & 15, 16, value);
            start = nextStart;
         }

         int rest = limit & 15;

         for (int var7 = limit & -16; start < var7; start += 16) {
            int i = start >> 4;
            if (this.flags[i] == 0) {
               this.index[i] = value;
            } else {
               this.fillBlock(this.index[i], 0, 16, value);
            }
         }

         if (rest > 0) {
            int block = this.getDataBlock(start >> 4);
            this.fillBlock(block, 0, rest, value);
         }
      } else {
         throw new IllegalArgumentException("invalid code point range");
      }
   }

   public CodePointTrie buildImmutable(CodePointTrie.Type type, CodePointTrie.ValueWidth valueWidth) {
      if (type != null && valueWidth != null) {
         CodePointTrie var3;
         try {
            var3 = this.build(type, valueWidth);
         } finally {
            this.clear();
         }

         return var3;
      } else {
         throw new IllegalArgumentException("The type and valueWidth must be specified.");
      }
   }

   private void ensureHighStart(int c) {
      if (c >= this.highStart) {
         c = c + 512 & -512;
         int i = this.highStart >> 4;
         int iLimit = c >> 4;
         if (iLimit > this.index.length) {
            int[] newIndex = new int[69632];

            for (int j = 0; j < i; j++) {
               newIndex[j] = this.index[j];
            }

            this.index = newIndex;
         }

         do {
            this.flags[i] = 0;
            this.index[i] = this.initialValue;
         } while (++i < iLimit);

         this.highStart = c;
      }
   }

   private int allocDataBlock(int blockLength) {
      int newBlock = this.dataLength;
      int newTop = newBlock + blockLength;
      if (newTop > this.data.length) {
         int capacity;
         if (this.data.length < 131072) {
            capacity = 131072;
         } else {
            if (this.data.length >= 1114112) {
               throw new AssertionError();
            }

            capacity = 1114112;
         }

         int[] newData = new int[capacity];

         for (int j = 0; j < this.dataLength; j++) {
            newData[j] = this.data[j];
         }

         this.data = newData;
      }

      this.dataLength = newTop;
      return newBlock;
   }

   private int getDataBlock(int i) {
      if (this.flags[i] == 1) {
         return this.index[i];
      } else if (i >= 4096) {
         int newBlock = this.allocDataBlock(16);
         if (newBlock < 0) {
            return newBlock;
         } else {
            this.writeBlock(newBlock, this.index[i]);
            this.flags[i] = 1;
            this.index[i] = newBlock;
            return newBlock;
         }
      } else {
         int newBlock = this.allocDataBlock(64);
         int iStart = i & -4;
         int iLimit = iStart + 4;

         while ($assertionsDisabled || this.flags[iStart] == 0) {
            this.writeBlock(newBlock, this.index[iStart]);
            this.flags[iStart] = 1;
            this.index[iStart++] = newBlock;
            newBlock += 16;
            if (iStart >= iLimit) {
               return this.index[i];
            }
         }

         throw new AssertionError();
      }
   }

   private void maskValues(int mask) {
      this.initialValue &= mask;
      this.errorValue &= mask;
      this.highValue &= mask;
      int iLimit = this.highStart >> 4;

      for (int i = 0; i < iLimit; i++) {
         if (this.flags[i] == 0) {
            this.index[i] = this.index[i] & mask;
         }
      }

      for (int ix = 0; ix < this.dataLength; ix++) {
         this.data[ix] = this.data[ix] & mask;
      }
   }

   private static boolean equalBlocks(int[] s, int si, int[] t, int ti, int length) {
      while (length > 0 && s[si] == t[ti]) {
         si++;
         ti++;
         length--;
      }

      return length == 0;
   }

   private static boolean equalBlocks(char[] s, int si, int[] t, int ti, int length) {
      while (length > 0 && s[si] == t[ti]) {
         si++;
         ti++;
         length--;
      }

      return length == 0;
   }

   private static boolean equalBlocks(char[] s, int si, char[] t, int ti, int length) {
      while (length > 0 && s[si] == t[ti]) {
         si++;
         ti++;
         length--;
      }

      return length == 0;
   }

   private static boolean allValuesSameAs(int[] p, int pi, int length, int value) {
      int pLimit = pi + length;

      while (pi < pLimit && p[pi] == value) {
         pi++;
      }

      return pi == pLimit;
   }

   private static int findSameBlock(char[] p, int pStart, int length, char[] q, int qStart, int blockLength) {
      length -= blockLength;

      while (pStart <= length) {
         if (equalBlocks(p, pStart, q, qStart, blockLength)) {
            return pStart;
         }

         pStart++;
      }

      return -1;
   }

   private static int findAllSameBlock(int[] p, int start, int limit, int value, int blockLength) {
      limit -= blockLength;

      label27:
      for (int block = start; block <= limit; block++) {
         if (p[block] == value) {
            for (int i = 1; i != blockLength; i++) {
               if (p[block + i] != value) {
                  block += i;
                  continue label27;
               }
            }

            return block;
         }
      }

      return -1;
   }

   private static int getOverlap(int[] p, int length, int[] q, int qStart, int blockLength) {
      int overlap = blockLength - 1;

      assert overlap <= length;

      while (overlap > 0 && !equalBlocks(p, length - overlap, q, qStart, overlap)) {
         overlap--;
      }

      return overlap;
   }

   private static int getOverlap(char[] p, int length, int[] q, int qStart, int blockLength) {
      int overlap = blockLength - 1;

      assert overlap <= length;

      while (overlap > 0 && !equalBlocks(p, length - overlap, q, qStart, overlap)) {
         overlap--;
      }

      return overlap;
   }

   private static int getOverlap(char[] p, int length, char[] q, int qStart, int blockLength) {
      int overlap = blockLength - 1;

      assert overlap <= length;

      while (overlap > 0 && !equalBlocks(p, length - overlap, q, qStart, overlap)) {
         overlap--;
      }

      return overlap;
   }

   private static int getAllSameOverlap(int[] p, int length, int value, int blockLength) {
      int min = length - (blockLength - 1);
      int i = length;

      while (min < i && p[i - 1] == value) {
         i--;
      }

      return length - i;
   }

   private static boolean isStartOfSomeFastBlock(int dataOffset, int[] index, int fastILimit) {
      for (int i = 0; i < fastILimit; i += 4) {
         if (index[i] == dataOffset) {
            return true;
         }
      }

      return false;
   }

   private int findHighStart() {
      int i = this.highStart >> 4;

      while (i > 0) {
         boolean match;
         if (this.flags[--i] == 0) {
            match = this.index[i] == this.highValue;
         } else {
            label43: {
               int p = this.index[i];

               for (int j = 0; j != 16; j++) {
                  if (this.data[p + j] != this.highValue) {
                     match = false;
                     break label43;
                  }
               }

               match = true;
            }
         }

         if (!match) {
            return i + 1 << 4;
         }
      }

      return 0;
   }

   private int compactWholeDataBlocks(int fastILimit, MutableCodePointTrie.AllSameBlocks allSameBlocks) {
      int newDataCapacity = 128;
      newDataCapacity += 16;
      newDataCapacity += 4;
      int iLimit = this.highStart >> 4;
      int blockLength = 64;
      int inc = 4;

      for (int i = 0; i < iLimit; i += inc) {
         if (i == fastILimit) {
            blockLength = 16;
            inc = 1;
         }

         int value = this.index[i];
         if (this.flags[i] == 1) {
            int p = value;
            value = this.data[value];
            if (!allValuesSameAs(this.data, p + 1, blockLength - 1, value)) {
               newDataCapacity += blockLength;
               continue;
            }

            this.flags[i] = 0;
            this.index[i] = value;
         } else {
            assert this.flags[i] == 0;

            if (inc > 1) {
               boolean allSame = true;
               int next_i = i + inc;

               for (int j = i + 1; j < next_i; j++) {
                  assert this.flags[j] == 0;

                  if (this.index[j] != value) {
                     allSame = false;
                     break;
                  }
               }

               if (!allSame) {
                  if (this.getDataBlock(i) < 0) {
                     return -1;
                  }

                  newDataCapacity += blockLength;
                  continue;
               }
            }
         }

         int other;
         other = allSameBlocks.findOrAdd(i, inc, value);
         label70:
         if (other == -2) {
            int jInc = 4;

            for (int j = 0; j != i; j += jInc) {
               if (j == fastILimit) {
                  jInc = 1;
               }

               if (this.flags[j] == 0 && this.index[j] == value) {
                  allSameBlocks.add(j, jInc + inc, value);
                  other = j;
                  break label70;
               }
            }

            allSameBlocks.add(i, inc, value);
         }

         if (other >= 0) {
            this.flags[i] = 2;
            this.index[i] = other;
         } else {
            newDataCapacity += blockLength;
         }
      }

      return newDataCapacity;
   }

   private int compactData(int fastILimit, int[] newData, int dataNullIndex, MutableCodePointTrie.MixedBlocks mixedBlocks) {
      int newDataLength = 0;

      for (int i = 0; newDataLength < 128; i += 4) {
         this.index[i] = newDataLength;
         newDataLength += 64;
      }

      int blockLength = 64;
      mixedBlocks.init(newData.length, blockLength);
      mixedBlocks.extend(newData, 0, 0, newDataLength);
      int iLimit = this.highStart >> 4;
      int inc = 4;
      int fastLength = 0;

      for (int i = 8; i < iLimit; i += inc) {
         if (i == fastILimit) {
            blockLength = 16;
            inc = 1;
            fastLength = newDataLength;
            mixedBlocks.init(newData.length, blockLength);
            mixedBlocks.extend(newData, 0, 0, newDataLength);
         }

         if (this.flags[i] == 0) {
            int value = this.index[i];
            int n = mixedBlocks.findAllSameBlock(newData, value);

            while (n >= 0 && i == dataNullIndex && i >= fastILimit && n < fastLength && isStartOfSomeFastBlock(n, this.index, fastILimit)) {
               n = findAllSameBlock(newData, n + 1, newDataLength, value, blockLength);
            }

            if (n >= 0) {
               this.index[i] = n;
            } else {
               n = getAllSameOverlap(newData, newDataLength, value, blockLength);
               this.index[i] = newDataLength - n;

               int prevDataLength;
               for (prevDataLength = newDataLength; n < blockLength; n++) {
                  newData[newDataLength++] = value;
               }

               mixedBlocks.extend(newData, 0, prevDataLength, newDataLength);
            }
         } else if (this.flags[i] != 1) {
            int j = this.index[i];
            this.index[i] = this.index[j];
         } else {
            int block = this.index[i];
            int n = mixedBlocks.findBlock(newData, this.data, block);
            if (n >= 0) {
               this.index[i] = n;
            } else {
               n = getOverlap(newData, newDataLength, this.data, block, blockLength);
               this.index[i] = newDataLength - n;
               int prevDataLength = newDataLength;

               while (n < blockLength) {
                  newData[newDataLength++] = this.data[block + n++];
               }

               mixedBlocks.extend(newData, 0, prevDataLength, newDataLength);
            }
         }
      }

      return newDataLength;
   }

   private int compactIndex(int fastILimit, MutableCodePointTrie.MixedBlocks mixedBlocks) {
      int fastIndexLength = fastILimit >> 2;
      if (this.highStart >> 6 <= fastIndexLength) {
         this.index3NullOffset = 32767;
         return fastIndexLength;
      } else {
         char[] fastIndex = new char[fastIndexLength];
         int i3FirstNull = -1;
         int i = 0;

         for (int j = 0; i < fastILimit; j++) {
            int i3 = this.index[i];
            fastIndex[j] = (char)i3;
            if (i3 == this.dataNullOffset) {
               if (i3FirstNull < 0) {
                  i3FirstNull = j;
               } else if (this.index3NullOffset < 0 && j - i3FirstNull + 1 == 32) {
                  this.index3NullOffset = i3FirstNull;
               }
            } else {
               i3FirstNull = -1;
            }

            for (int iNext = i + 4; ++i < iNext; this.index[i] = i3) {
               i3 += 16;
            }
         }

         mixedBlocks.init(fastIndexLength, 32);
         mixedBlocks.extend(fastIndex, 0, 0, fastIndexLength);
         i = 0;
         i3FirstNull = this.index3NullOffset;
         boolean hasLongI3Blocks = false;
         int iStart = fastILimit < 4096 ? 0 : 4096;
         int iLimit = this.highStart >> 4;
         int ix = iStart;

         while (ix < iLimit) {
            int j = ix;
            int jLimit = ix + 32;
            int oredI3 = 0;
            boolean isNull = true;

            do {
               int i3 = this.index[j];
               oredI3 |= i3;
               if (i3 != this.dataNullOffset) {
                  isNull = false;
               }
            } while (++j < jLimit);

            if (isNull) {
               this.flags[ix] = 0;
               if (i3FirstNull < 0) {
                  if (oredI3 <= 65535) {
                     i += 32;
                  } else {
                     i += 36;
                     hasLongI3Blocks = true;
                  }

                  i3FirstNull = 0;
               }
            } else if (oredI3 <= 65535) {
               int n = mixedBlocks.findBlock(fastIndex, this.index, ix);
               if (n >= 0) {
                  this.flags[ix] = 1;
                  this.index[ix] = n;
               } else {
                  this.flags[ix] = 2;
                  i += 32;
               }
            } else {
               this.flags[ix] = 3;
               i += 36;
               hasLongI3Blocks = true;
            }

            ix = j;
         }

         ix = iLimit - iStart >> 5;
         int index1Length = ix + 31 >> 5;
         int index16Capacity = fastIndexLength + index1Length + i + ix + 1;
         this.index16 = Arrays.copyOf(fastIndex, index16Capacity);
         mixedBlocks.init(index16Capacity, 32);
         MutableCodePointTrie.MixedBlocks longI3Blocks = null;
         if (hasLongI3Blocks) {
            longI3Blocks = new MutableCodePointTrie.MixedBlocks();
            longI3Blocks.init(index16Capacity, 36);
         }

         char[] index2 = new char[ix];
         int i2Length = 0;
         i3FirstNull = this.index3NullOffset;
         int index3Start = fastIndexLength + index1Length;
         int indexLength = index3Start;

         for (int ixx = iStart; ixx < iLimit; ixx += 32) {
            byte f = this.flags[ixx];
            if (f == 0 && i3FirstNull < 0) {
               f = (byte)(this.dataNullOffset <= 65535 ? 2 : 3);
               i3FirstNull = 0;
            }

            int i3;
            if (f == 0) {
               i3 = this.index3NullOffset;
            } else if (f == 1) {
               i3 = this.index[ixx];
            } else if (f == 2) {
               int n = mixedBlocks.findBlock(this.index16, this.index, ixx);
               if (n >= 0) {
                  i3 = n;
               } else {
                  if (indexLength == index3Start) {
                     n = 0;
                  } else {
                     n = getOverlap(this.index16, indexLength, this.index, ixx, 32);
                  }

                  i3 = indexLength - n;
                  int prevIndexLength = indexLength;

                  while (n < 32) {
                     this.index16[indexLength++] = (char)this.index[ixx + n++];
                  }

                  mixedBlocks.extend(this.index16, index3Start, prevIndexLength, indexLength);
                  if (hasLongI3Blocks) {
                     longI3Blocks.extend(this.index16, index3Start, prevIndexLength, indexLength);
                  }
               }
            } else {
               assert f == 3;

               assert hasLongI3Blocks;

               int j = ixx;
               int jLimit = ixx + 32;
               int k = indexLength;

               do {
                  k++;
                  int v = this.index[j++];
                  int upperBits = (v & 196608) >> 2;
                  this.index16[k++] = (char)v;
                  v = this.index[j++];
                  upperBits |= (v & 196608) >> 4;
                  this.index16[k++] = (char)v;
                  v = this.index[j++];
                  upperBits |= (v & 196608) >> 6;
                  this.index16[k++] = (char)v;
                  v = this.index[j++];
                  upperBits |= (v & 196608) >> 8;
                  this.index16[k++] = (char)v;
                  v = this.index[j++];
                  upperBits |= (v & 196608) >> 10;
                  this.index16[k++] = (char)v;
                  v = this.index[j++];
                  upperBits |= (v & 196608) >> 12;
                  this.index16[k++] = (char)v;
                  v = this.index[j++];
                  upperBits |= (v & 196608) >> 14;
                  this.index16[k++] = (char)v;
                  v = this.index[j++];
                  upperBits |= (v & 196608) >> 16;
                  this.index16[k++] = (char)v;
                  this.index16[k - 9] = (char)upperBits;
               } while (j < jLimit);

               int n = longI3Blocks.findBlock(this.index16, this.index16, indexLength);
               if (n >= 0) {
                  i3 = n | 32768;
               } else {
                  if (indexLength == index3Start) {
                     n = 0;
                  } else {
                     n = getOverlap(this.index16, indexLength, this.index16, indexLength, 36);
                  }

                  i3 = indexLength - n | 32768;
                  int prevIndexLength = indexLength;
                  if (n > 0) {
                     int start = indexLength;

                     while (n < 36) {
                        this.index16[indexLength++] = this.index16[start + n++];
                     }
                  } else {
                     indexLength += 36;
                  }

                  mixedBlocks.extend(this.index16, index3Start, prevIndexLength, indexLength);
                  if (hasLongI3Blocks) {
                     longI3Blocks.extend(this.index16, index3Start, prevIndexLength, indexLength);
                  }
               }
            }

            if (this.index3NullOffset < 0 && i3FirstNull >= 0) {
               this.index3NullOffset = i3;
            }

            index2[i2Length++] = (char)i3;
         }

         assert i2Length == ix;

         assert indexLength <= index3Start + i;

         if (this.index3NullOffset < 0) {
            this.index3NullOffset = 32767;
         }

         if (indexLength >= 32799) {
            throw new IndexOutOfBoundsException("The trie data exceeds limitations of the data structure.");
         } else {
            int blockLength = 32;
            int i1 = fastIndexLength;

            for (int ixx = 0; ixx < i2Length; ixx += blockLength) {
               int n;
               if (i2Length - ixx >= blockLength) {
                  assert blockLength == 32;

                  n = mixedBlocks.findBlock(this.index16, index2, ixx);
               } else {
                  blockLength = i2Length - ixx;
                  n = findSameBlock(this.index16, index3Start, indexLength, index2, ixx, blockLength);
               }

               int i2;
               if (n >= 0) {
                  i2 = n;
               } else {
                  if (indexLength == index3Start) {
                     n = 0;
                  } else {
                     n = getOverlap(this.index16, indexLength, index2, ixx, blockLength);
                  }

                  i2 = indexLength - n;
                  int prevIndexLengthx = indexLength;

                  while (n < blockLength) {
                     this.index16[indexLength++] = index2[ixx + n++];
                  }

                  mixedBlocks.extend(this.index16, index3Start, prevIndexLengthx, indexLength);
               }

               this.index16[i1++] = (char)i2;
            }

            assert i1 == index3Start;

            assert indexLength <= index16Capacity;

            return indexLength;
         }
      }
   }

   private int compactTrie(int fastILimit) {
      assert (this.highStart & 511) == 0;

      this.highValue = this.get(1114111);
      int realHighStart = this.findHighStart();
      realHighStart = realHighStart + 511 & -512;
      if (realHighStart == 1114112) {
         this.highValue = this.initialValue;
      }

      int fastLimit = fastILimit << 4;
      if (realHighStart < fastLimit) {
         for (int i = realHighStart >> 4; i < fastILimit; i++) {
            this.flags[i] = 0;
            this.index[i] = this.highValue;
         }

         this.highStart = fastLimit;
      } else {
         this.highStart = realHighStart;
      }

      int[] asciiData = new int[128];

      for (int i = 0; i < 128; i++) {
         asciiData[i] = this.get(i);
      }

      MutableCodePointTrie.AllSameBlocks allSameBlocks = new MutableCodePointTrie.AllSameBlocks();
      int newDataCapacity = this.compactWholeDataBlocks(fastILimit, allSameBlocks);
      int[] newData = Arrays.copyOf(asciiData, newDataCapacity);
      int dataNullIndex = allSameBlocks.findMostUsed();
      MutableCodePointTrie.MixedBlocks mixedBlocks = new MutableCodePointTrie.MixedBlocks();
      int newDataLength = this.compactData(fastILimit, newData, dataNullIndex, mixedBlocks);

      assert newDataLength <= newDataCapacity;

      this.data = newData;
      this.dataLength = newDataLength;
      if (this.dataLength > 262159) {
         throw new IndexOutOfBoundsException("The trie data exceeds limitations of the data structure.");
      } else {
         if (dataNullIndex >= 0) {
            this.dataNullOffset = this.index[dataNullIndex];
            this.initialValue = this.data[this.dataNullOffset];
         } else {
            this.dataNullOffset = 1048575;
         }

         int indexLength = this.compactIndex(fastILimit, mixedBlocks);
         this.highStart = realHighStart;
         return indexLength;
      }
   }

   private CodePointTrie build(CodePointTrie.Type type, CodePointTrie.ValueWidth valueWidth) {
      switch (valueWidth) {
         case BITS_32:
            break;
         case BITS_16:
            this.maskValues(65535);
            break;
         case BITS_8:
            this.maskValues(255);
            break;
         default:
            throw new IllegalArgumentException();
      }

      int fastLimit = type == CodePointTrie.Type.FAST ? 65536 : 4096;
      int indexLength = this.compactTrie(fastLimit >> 4);
      if (valueWidth == CodePointTrie.ValueWidth.BITS_32 && (indexLength & 1) != 0) {
         this.index16[indexLength++] = '￮';
      }

      int length = indexLength * 2;
      if (valueWidth == CodePointTrie.ValueWidth.BITS_16) {
         if (((indexLength ^ this.dataLength) & 1) != 0) {
            this.data[this.dataLength++] = this.errorValue;
         }

         if (this.data[this.dataLength - 1] != this.errorValue || this.data[this.dataLength - 2] != this.highValue) {
            this.data[this.dataLength++] = this.highValue;
            this.data[this.dataLength++] = this.errorValue;
         }

         length += this.dataLength * 2;
      } else if (valueWidth == CodePointTrie.ValueWidth.BITS_32) {
         if (this.data[this.dataLength - 1] != this.errorValue || this.data[this.dataLength - 2] != this.highValue) {
            if (this.data[this.dataLength - 1] != this.highValue) {
               this.data[this.dataLength++] = this.highValue;
            }

            this.data[this.dataLength++] = this.errorValue;
         }

         length += this.dataLength * 4;
      } else {
         int and3 = length + this.dataLength & 3;
         if (and3 != 0 || this.data[this.dataLength - 1] != this.errorValue || this.data[this.dataLength - 2] != this.highValue) {
            if (and3 == 3 && this.data[this.dataLength - 1] == this.highValue) {
               this.data[this.dataLength++] = this.errorValue;
            } else {
               while (and3 != 2) {
                  this.data[this.dataLength++] = this.highValue;
                  and3 = and3 + 1 & 3;
               }

               this.data[this.dataLength++] = this.highValue;
               this.data[this.dataLength++] = this.errorValue;
            }
         }

         length += this.dataLength;
      }

      assert (length & 3) == 0;

      char[] trieIndex;
      if (this.highStart <= fastLimit) {
         trieIndex = new char[indexLength];
         int i = 0;

         for (int j = 0; j < indexLength; j++) {
            trieIndex[j] = (char)this.index[i];
            i += 4;
         }
      } else if (indexLength == this.index16.length) {
         trieIndex = this.index16;
         this.index16 = null;
      } else {
         trieIndex = Arrays.copyOf(this.index16, indexLength);
      }

      switch (valueWidth) {
         case BITS_32:
            int[] data32 = Arrays.copyOf(this.data, this.dataLength);
            return (CodePointTrie)(type == CodePointTrie.Type.FAST
               ? new CodePointTrie.Fast32(trieIndex, data32, this.highStart, this.index3NullOffset, this.dataNullOffset)
               : new CodePointTrie.Small32(trieIndex, data32, this.highStart, this.index3NullOffset, this.dataNullOffset));
         case BITS_16:
            char[] data16 = new char[this.dataLength];

            for (int i = 0; i < this.dataLength; i++) {
               data16[i] = (char)this.data[i];
            }

            return (CodePointTrie)(type == CodePointTrie.Type.FAST
               ? new CodePointTrie.Fast16(trieIndex, data16, this.highStart, this.index3NullOffset, this.dataNullOffset)
               : new CodePointTrie.Small16(trieIndex, data16, this.highStart, this.index3NullOffset, this.dataNullOffset));
         case BITS_8:
            byte[] data8 = new byte[this.dataLength];

            for (int i = 0; i < this.dataLength; i++) {
               data8[i] = (byte)this.data[i];
            }

            return (CodePointTrie)(type == CodePointTrie.Type.FAST
               ? new CodePointTrie.Fast8(trieIndex, data8, this.highStart, this.index3NullOffset, this.dataNullOffset)
               : new CodePointTrie.Small8(trieIndex, data8, this.highStart, this.index3NullOffset, this.dataNullOffset));
         default:
            throw new IllegalArgumentException();
      }
   }

   private static final class AllSameBlocks {
      static final int NEW_UNIQUE = -1;
      static final int OVERFLOW = -2;
      private static final int CAPACITY = 32;
      private int length;
      private int mostRecent;
      private int[] indexes = new int[32];
      private int[] values = new int[32];
      private int[] refCounts = new int[32];

      AllSameBlocks() {
         this.mostRecent = -1;
      }

      int findOrAdd(int index, int count, int value) {
         if (this.mostRecent >= 0 && this.values[this.mostRecent] == value) {
            this.refCounts[this.mostRecent] = this.refCounts[this.mostRecent] + count;
            return this.indexes[this.mostRecent];
         } else {
            for (int i = 0; i < this.length; i++) {
               if (this.values[i] == value) {
                  this.mostRecent = i;
                  this.refCounts[i] = this.refCounts[i] + count;
                  return this.indexes[i];
               }
            }

            if (this.length == 32) {
               return -2;
            } else {
               this.mostRecent = this.length;
               this.indexes[this.length] = index;
               this.values[this.length] = value;
               this.refCounts[this.length++] = count;
               return -1;
            }
         }
      }

      void add(int index, int count, int value) {
         assert this.length == 32;

         int least = -1;
         int leastCount = 69632;

         for (int i = 0; i < this.length; i++) {
            assert this.values[i] != value;

            if (this.refCounts[i] < leastCount) {
               least = i;
               leastCount = this.refCounts[i];
            }
         }

         assert least >= 0;

         this.mostRecent = least;
         this.indexes[least] = index;
         this.values[least] = value;
         this.refCounts[least] = count;
      }

      int findMostUsed() {
         if (this.length == 0) {
            return -1;
         } else {
            int max = -1;
            int maxCount = 0;

            for (int i = 0; i < this.length; i++) {
               if (this.refCounts[i] > maxCount) {
                  max = i;
                  maxCount = this.refCounts[i];
               }
            }

            return this.indexes[max];
         }
      }
   }

   private static final class MixedBlocks {
      private int[] table;
      private int length;
      private int shift;
      private int mask;
      private int blockLength;

      private MixedBlocks() {
      }

      void init(int maxLength, int newBlockLength) {
         int maxDataIndex = maxLength - newBlockLength + 1;
         int newLength;
         if (maxDataIndex <= 4095) {
            newLength = 6007;
            this.shift = 12;
            this.mask = 4095;
         } else if (maxDataIndex <= 32767) {
            newLength = 50021;
            this.shift = 15;
            this.mask = 32767;
         } else if (maxDataIndex <= 131071) {
            newLength = 200003;
            this.shift = 17;
            this.mask = 131071;
         } else {
            newLength = 1500007;
            this.shift = 21;
            this.mask = 2097151;
         }

         if (this.table != null && newLength <= this.table.length) {
            Arrays.fill(this.table, 0, newLength, 0);
         } else {
            this.table = new int[newLength];
         }

         this.length = newLength;
         this.blockLength = newBlockLength;
      }

      void extend(int[] data, int minStart, int prevDataLength, int newDataLength) {
         int start = prevDataLength - this.blockLength;
         if (start >= minStart) {
            start++;
         } else {
            start = minStart;
         }

         for (int end = newDataLength - this.blockLength; start <= end; start++) {
            int hashCode = this.makeHashCode(data, start);
            this.addEntry(data, null, start, hashCode, start);
         }
      }

      void extend(char[] data, int minStart, int prevDataLength, int newDataLength) {
         int start = prevDataLength - this.blockLength;
         if (start >= minStart) {
            start++;
         } else {
            start = minStart;
         }

         for (int end = newDataLength - this.blockLength; start <= end; start++) {
            int hashCode = this.makeHashCode(data, start);
            this.addEntry(null, data, start, hashCode, start);
         }
      }

      int findBlock(int[] data, int[] blockData, int blockStart) {
         int hashCode = this.makeHashCode(blockData, blockStart);
         int entryIndex = this.findEntry(data, null, blockData, null, blockStart, hashCode);
         return entryIndex >= 0 ? (this.table[entryIndex] & this.mask) - 1 : -1;
      }

      int findBlock(char[] data, int[] blockData, int blockStart) {
         int hashCode = this.makeHashCode(blockData, blockStart);
         int entryIndex = this.findEntry(null, data, blockData, null, blockStart, hashCode);
         return entryIndex >= 0 ? (this.table[entryIndex] & this.mask) - 1 : -1;
      }

      int findBlock(char[] data, char[] blockData, int blockStart) {
         int hashCode = this.makeHashCode(blockData, blockStart);
         int entryIndex = this.findEntry(null, data, null, blockData, blockStart, hashCode);
         return entryIndex >= 0 ? (this.table[entryIndex] & this.mask) - 1 : -1;
      }

      int findAllSameBlock(int[] data, int blockValue) {
         int hashCode = this.makeHashCode(blockValue);
         int entryIndex = this.findEntry(data, blockValue, hashCode);
         return entryIndex >= 0 ? (this.table[entryIndex] & this.mask) - 1 : -1;
      }

      private int makeHashCode(int[] blockData, int blockStart) {
         int blockLimit = blockStart + this.blockLength;
         int hashCode = blockData[blockStart++];

         do {
            hashCode = 37 * hashCode + blockData[blockStart++];
         } while (blockStart < blockLimit);

         return hashCode;
      }

      private int makeHashCode(char[] blockData, int blockStart) {
         int blockLimit = blockStart + this.blockLength;
         int hashCode = blockData[blockStart++];

         do {
            hashCode = 37 * hashCode + blockData[blockStart++];
         } while (blockStart < blockLimit);

         return hashCode;
      }

      private int makeHashCode(int blockValue) {
         int hashCode = blockValue;

         for (int i = 1; i < this.blockLength; i++) {
            hashCode = 37 * hashCode + blockValue;
         }

         return hashCode;
      }

      private void addEntry(int[] data32, char[] data16, int blockStart, int hashCode, int dataIndex) {
         assert 0 <= dataIndex && dataIndex < this.mask;

         int entryIndex = this.findEntry(data32, data16, data32, data16, blockStart, hashCode);
         if (entryIndex < 0) {
            this.table[~entryIndex] = hashCode << this.shift | dataIndex + 1;
         }
      }

      private int findEntry(int[] data32, char[] data16, int[] blockData32, char[] blockData16, int blockStart, int hashCode) {
         int shiftedHashCode = hashCode << this.shift;
         int initialEntryIndex = this.modulo(hashCode, this.length - 1) + 1;
         int entryIndex = initialEntryIndex;

         while (true) {
            int entry = this.table[entryIndex];
            if (entry == 0) {
               return ~entryIndex;
            }

            if ((entry & ~this.mask) == shiftedHashCode) {
               int dataIndex = (entry & this.mask) - 1;
               if (data32 != null
                  ? MutableCodePointTrie.equalBlocks(data32, dataIndex, blockData32, blockStart, this.blockLength)
                  : (
                     blockData32 != null
                        ? MutableCodePointTrie.equalBlocks(data16, dataIndex, blockData32, blockStart, this.blockLength)
                        : MutableCodePointTrie.equalBlocks(data16, dataIndex, blockData16, blockStart, this.blockLength)
                  )) {
                  return entryIndex;
               }
            }

            entryIndex = this.nextIndex(initialEntryIndex, entryIndex);
         }
      }

      private int findEntry(int[] data, int blockValue, int hashCode) {
         int shiftedHashCode = hashCode << this.shift;
         int initialEntryIndex = this.modulo(hashCode, this.length - 1) + 1;
         int entryIndex = initialEntryIndex;

         while (true) {
            int entry = this.table[entryIndex];
            if (entry == 0) {
               return ~entryIndex;
            }

            if ((entry & ~this.mask) == shiftedHashCode) {
               int dataIndex = (entry & this.mask) - 1;
               if (MutableCodePointTrie.allValuesSameAs(data, dataIndex, this.blockLength, blockValue)) {
                  return entryIndex;
               }
            }

            entryIndex = this.nextIndex(initialEntryIndex, entryIndex);
         }
      }

      private int nextIndex(int initialEntryIndex, int entryIndex) {
         return (entryIndex + initialEntryIndex) % this.length;
      }

      private int modulo(int n, int m) {
         int i = n % m;
         if (i < 0) {
            i += m;
         }

         return i;
      }
   }
}
