package com.cobblemon.mod.relocations.ibm.icu.impl;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Iterator;
import java.util.NoSuchElementException;

public abstract class Trie2 implements Iterable<Trie2.Range> {
   private static Trie2.ValueMapper defaultValueMapper = new Trie2.ValueMapper() {
      @Override
      public int map(int in) {
         return in;
      }
   };
   Trie2.UTrie2Header header;
   char[] index;
   int data16;
   int[] data32;
   int indexLength;
   int dataLength;
   int index2NullOffset;
   int initialValue;
   int errorValue;
   int highStart;
   int highValueIndex;
   int dataNullOffset;
   int fHash;
   static final int UTRIE2_OPTIONS_VALUE_BITS_MASK = 15;
   static final int UTRIE2_SHIFT_1 = 11;
   static final int UTRIE2_SHIFT_2 = 5;
   static final int UTRIE2_SHIFT_1_2 = 6;
   static final int UTRIE2_OMITTED_BMP_INDEX_1_LENGTH = 32;
   static final int UTRIE2_CP_PER_INDEX_1_ENTRY = 2048;
   static final int UTRIE2_INDEX_2_BLOCK_LENGTH = 64;
   static final int UTRIE2_INDEX_2_MASK = 63;
   static final int UTRIE2_DATA_BLOCK_LENGTH = 32;
   static final int UTRIE2_DATA_MASK = 31;
   static final int UTRIE2_INDEX_SHIFT = 2;
   static final int UTRIE2_DATA_GRANULARITY = 4;
   static final int UTRIE2_INDEX_2_OFFSET = 0;
   static final int UTRIE2_LSCP_INDEX_2_OFFSET = 2048;
   static final int UTRIE2_LSCP_INDEX_2_LENGTH = 32;
   static final int UTRIE2_INDEX_2_BMP_LENGTH = 2080;
   static final int UTRIE2_UTF8_2B_INDEX_2_OFFSET = 2080;
   static final int UTRIE2_UTF8_2B_INDEX_2_LENGTH = 32;
   static final int UTRIE2_INDEX_1_OFFSET = 2112;
   static final int UTRIE2_MAX_INDEX_1_LENGTH = 512;
   static final int UTRIE2_BAD_UTF8_DATA_OFFSET = 128;
   static final int UTRIE2_DATA_START_OFFSET = 192;
   static final int UNEWTRIE2_INDEX_GAP_OFFSET = 2080;
   static final int UNEWTRIE2_INDEX_GAP_LENGTH = 576;
   static final int UNEWTRIE2_MAX_INDEX_2_LENGTH = 35488;
   static final int UNEWTRIE2_INDEX_1_LENGTH = 544;
   static final int UNEWTRIE2_MAX_DATA_LENGTH = 1115264;

   public static Trie2 createFromSerialized(ByteBuffer bytes) throws IOException {
      ByteOrder outerByteOrder = bytes.order();

      Object var6;
      try {
         Trie2.UTrie2Header header = new Trie2.UTrie2Header();
         header.signature = bytes.getInt();
         switch (header.signature) {
            case 845771348:
               boolean isBigEndian = outerByteOrder == ByteOrder.BIG_ENDIAN;
               bytes.order(isBigEndian ? ByteOrder.LITTLE_ENDIAN : ByteOrder.BIG_ENDIAN);
               header.signature = 1416784178;
            case 1416784178:
               break;
            default:
               throw new IllegalArgumentException("Buffer does not contain a serialized UTrie2");
         }

         header.options = bytes.getChar();
         header.indexLength = bytes.getChar();
         header.shiftedDataLength = bytes.getChar();
         header.index2NullOffset = bytes.getChar();
         header.dataNullOffset = bytes.getChar();
         header.shiftedHighStart = bytes.getChar();
         if ((header.options & 15) > 1) {
            throw new IllegalArgumentException("UTrie2 serialized format error.");
         }

         Trie2 This;
         Trie2.ValueWidth width;
         if ((header.options & 15) == 0) {
            width = Trie2.ValueWidth.BITS_16;
            This = new Trie2_16();
         } else {
            width = Trie2.ValueWidth.BITS_32;
            This = new Trie2_32();
         }

         This.header = header;
         This.indexLength = header.indexLength;
         This.dataLength = header.shiftedDataLength << 2;
         This.index2NullOffset = header.index2NullOffset;
         This.dataNullOffset = header.dataNullOffset;
         This.highStart = header.shiftedHighStart << 11;
         This.highValueIndex = This.dataLength - 4;
         if (width == Trie2.ValueWidth.BITS_16) {
            This.highValueIndex = This.highValueIndex + This.indexLength;
         }

         int indexArraySize = This.indexLength;
         if (width == Trie2.ValueWidth.BITS_16) {
            indexArraySize += This.dataLength;
         }

         This.index = ICUBinary.getChars(bytes, indexArraySize, 0);
         if (width == Trie2.ValueWidth.BITS_16) {
            This.data16 = This.indexLength;
         } else {
            This.data32 = ICUBinary.getInts(bytes, This.dataLength, 0);
         }

         switch (width) {
            case BITS_16:
               This.data32 = null;
               This.initialValue = This.index[This.dataNullOffset];
               This.errorValue = This.index[This.data16 + 128];
               break;
            case BITS_32:
               This.data16 = 0;
               This.initialValue = This.data32[This.dataNullOffset];
               This.errorValue = This.data32[128];
               break;
            default:
               throw new IllegalArgumentException("UTrie2 serialized format error.");
         }

         var6 = This;
      } finally {
         bytes.order(outerByteOrder);
      }

      return (Trie2)var6;
   }

   public static int getVersion(InputStream is, boolean littleEndianOk) throws IOException {
      if (!is.markSupported()) {
         throw new IllegalArgumentException("Input stream must support mark().");
      } else {
         is.mark(4);
         byte[] sig = new byte[4];
         int read = is.read(sig);
         is.reset();
         if (read != sig.length) {
            return 0;
         } else if (sig[0] == 84 && sig[1] == 114 && sig[2] == 105 && sig[3] == 101) {
            return 1;
         } else if (sig[0] == 84 && sig[1] == 114 && sig[2] == 105 && sig[3] == 50) {
            return 2;
         } else {
            if (littleEndianOk) {
               if (sig[0] == 101 && sig[1] == 105 && sig[2] == 114 && sig[3] == 84) {
                  return 1;
               }

               if (sig[0] == 50 && sig[1] == 105 && sig[2] == 114 && sig[3] == 84) {
                  return 2;
               }
            }

            return 0;
         }
      }
   }

   public abstract int get(int var1);

   public abstract int getFromU16SingleLead(char var1);

   @Override
   public final boolean equals(Object other) {
      if (!(other instanceof Trie2)) {
         return false;
      } else {
         Trie2 OtherTrie = (Trie2)other;
         Iterator<Trie2.Range> otherIter = OtherTrie.iterator();

         for (Trie2.Range rangeFromThis : this) {
            if (!otherIter.hasNext()) {
               return false;
            }

            Trie2.Range rangeFromOther = otherIter.next();
            if (!rangeFromThis.equals(rangeFromOther)) {
               return false;
            }
         }

         return otherIter.hasNext() ? false : this.errorValue == OtherTrie.errorValue && this.initialValue == OtherTrie.initialValue;
      }
   }

   @Override
   public int hashCode() {
      if (this.fHash == 0) {
         int hash = initHash();

         for (Trie2.Range r : this) {
            hash = hashInt(hash, r.hashCode());
         }

         if (hash == 0) {
            hash = 1;
         }

         this.fHash = hash;
      }

      return this.fHash;
   }

   @Override
   public Iterator<Trie2.Range> iterator() {
      return this.iterator(defaultValueMapper);
   }

   public Iterator<Trie2.Range> iterator(Trie2.ValueMapper mapper) {
      return new Trie2.Trie2Iterator(mapper);
   }

   public Iterator<Trie2.Range> iteratorForLeadSurrogate(char lead, Trie2.ValueMapper mapper) {
      return new Trie2.Trie2Iterator(lead, mapper);
   }

   public Iterator<Trie2.Range> iteratorForLeadSurrogate(char lead) {
      return new Trie2.Trie2Iterator(lead, defaultValueMapper);
   }

   protected int serializeHeader(DataOutputStream dos) throws IOException {
      int bytesWritten = 0;
      dos.writeInt(this.header.signature);
      dos.writeShort(this.header.options);
      dos.writeShort(this.header.indexLength);
      dos.writeShort(this.header.shiftedDataLength);
      dos.writeShort(this.header.index2NullOffset);
      dos.writeShort(this.header.dataNullOffset);
      dos.writeShort(this.header.shiftedHighStart);
      bytesWritten += 16;

      for (int i = 0; i < this.header.indexLength; i++) {
         dos.writeChar(this.index[i]);
      }

      return bytesWritten + this.header.indexLength;
   }

   public Trie2.CharSequenceIterator charSequenceIterator(CharSequence text, int index) {
      return new Trie2.CharSequenceIterator(text, index);
   }

   int rangeEnd(int start, int limitp, int val) {
      int limit = Math.min(this.highStart, limitp);
      int c = start + 1;

      while (c < limit && this.get(c) == val) {
         c++;
      }

      if (c >= this.highStart) {
         c = limitp;
      }

      return c - 1;
   }

   private static int initHash() {
      return -2128831035;
   }

   private static int hashByte(int h, int b) {
      h *= 16777619;
      return h ^ b;
   }

   private static int hashUChar32(int h, int c) {
      h = hashByte(h, c & 0xFF);
      h = hashByte(h, c >> 8 & 0xFF);
      return hashByte(h, c >> 16);
   }

   private static int hashInt(int h, int i) {
      h = hashByte(h, i & 0xFF);
      h = hashByte(h, i >> 8 & 0xFF);
      h = hashByte(h, i >> 16 & 0xFF);
      return hashByte(h, i >> 24 & 0xFF);
   }

   public class CharSequenceIterator implements Iterator<Trie2.CharSequenceValues> {
      private CharSequence text;
      private int textLength;
      private int index;
      private Trie2.CharSequenceValues fResults = new Trie2.CharSequenceValues();

      CharSequenceIterator(CharSequence t, int index) {
         this.text = t;
         this.textLength = this.text.length();
         this.set(index);
      }

      public void set(int i) {
         if (i >= 0 && i <= this.textLength) {
            this.index = i;
         } else {
            throw new IndexOutOfBoundsException();
         }
      }

      @Override
      public final boolean hasNext() {
         return this.index < this.textLength;
      }

      public final boolean hasPrevious() {
         return this.index > 0;
      }

      public Trie2.CharSequenceValues next() {
         int c = Character.codePointAt(this.text, this.index);
         int val = Trie2.this.get(c);
         this.fResults.index = this.index;
         this.fResults.codePoint = c;
         this.fResults.value = val;
         this.index++;
         if (c >= 65536) {
            this.index++;
         }

         return this.fResults;
      }

      public Trie2.CharSequenceValues previous() {
         int c = Character.codePointBefore(this.text, this.index);
         int val = Trie2.this.get(c);
         this.index--;
         if (c >= 65536) {
            this.index--;
         }

         this.fResults.index = this.index;
         this.fResults.codePoint = c;
         this.fResults.value = val;
         return this.fResults;
      }

      @Override
      public void remove() {
         throw new UnsupportedOperationException("Trie2.CharSequenceIterator does not support remove().");
      }
   }

   public static class CharSequenceValues {
      public int index;
      public int codePoint;
      public int value;
   }

   public static class Range {
      public int startCodePoint;
      public int endCodePoint;
      public int value;
      public boolean leadSurrogate;

      @Override
      public boolean equals(Object other) {
         if (other != null && other.getClass().equals(this.getClass())) {
            Trie2.Range tother = (Trie2.Range)other;
            return this.startCodePoint == tother.startCodePoint
               && this.endCodePoint == tother.endCodePoint
               && this.value == tother.value
               && this.leadSurrogate == tother.leadSurrogate;
         } else {
            return false;
         }
      }

      @Override
      public int hashCode() {
         int h = Trie2.initHash();
         h = Trie2.hashUChar32(h, this.startCodePoint);
         h = Trie2.hashUChar32(h, this.endCodePoint);
         h = Trie2.hashInt(h, this.value);
         return Trie2.hashByte(h, this.leadSurrogate ? 1 : 0);
      }
   }

   class Trie2Iterator implements Iterator<Trie2.Range> {
      private Trie2.ValueMapper mapper;
      private Trie2.Range returnValue = new Trie2.Range();
      private int nextStart;
      private int limitCP;
      private boolean doingCodePoints = true;
      private boolean doLeadSurrogates = true;

      Trie2Iterator(Trie2.ValueMapper vm) {
         this.mapper = vm;
         this.nextStart = 0;
         this.limitCP = 1114112;
         this.doLeadSurrogates = true;
      }

      Trie2Iterator(char leadSurrogate, Trie2.ValueMapper vm) {
         if (leadSurrogate >= '\ud800' && leadSurrogate <= '\udbff') {
            this.mapper = vm;
            this.nextStart = leadSurrogate - 'ퟀ' << 10;
            this.limitCP = this.nextStart + 1024;
            this.doLeadSurrogates = false;
         } else {
            throw new IllegalArgumentException("Bad lead surrogate value.");
         }
      }

      public Trie2.Range next() {
         if (!this.hasNext()) {
            throw new NoSuchElementException();
         } else {
            if (this.nextStart >= this.limitCP) {
               this.doingCodePoints = false;
               this.nextStart = 55296;
            }

            int endOfRange = 0;
            int val = 0;
            int mappedVal = 0;
            if (this.doingCodePoints) {
               val = Trie2.this.get(this.nextStart);
               mappedVal = this.mapper.map(val);
               endOfRange = Trie2.this.rangeEnd(this.nextStart, this.limitCP, val);

               while (endOfRange < this.limitCP - 1) {
                  val = Trie2.this.get(endOfRange + 1);
                  if (this.mapper.map(val) != mappedVal) {
                     break;
                  }

                  endOfRange = Trie2.this.rangeEnd(endOfRange + 1, this.limitCP, val);
               }
            } else {
               val = Trie2.this.getFromU16SingleLead((char)this.nextStart);
               mappedVal = this.mapper.map(val);

               for (endOfRange = this.rangeEndLS((char)this.nextStart); endOfRange < 56319; endOfRange = this.rangeEndLS((char)(endOfRange + 1))) {
                  val = Trie2.this.getFromU16SingleLead((char)(endOfRange + 1));
                  if (this.mapper.map(val) != mappedVal) {
                     break;
                  }
               }
            }

            this.returnValue.startCodePoint = this.nextStart;
            this.returnValue.endCodePoint = endOfRange;
            this.returnValue.value = mappedVal;
            this.returnValue.leadSurrogate = !this.doingCodePoints;
            this.nextStart = endOfRange + 1;
            return this.returnValue;
         }
      }

      @Override
      public boolean hasNext() {
         return this.doingCodePoints && (this.doLeadSurrogates || this.nextStart < this.limitCP) || this.nextStart < 56320;
      }

      @Override
      public void remove() {
         throw new UnsupportedOperationException();
      }

      private int rangeEndLS(char startingLS) {
         if (startingLS >= '\udbff') {
            return 56319;
         } else {
            int val = Trie2.this.getFromU16SingleLead(startingLS);
            int c = startingLS + 1;

            while (c <= 56319 && Trie2.this.getFromU16SingleLead((char)c) == val) {
               c++;
            }

            return c - 1;
         }
      }
   }

   static class UTrie2Header {
      int signature;
      int options;
      int indexLength;
      int shiftedDataLength;
      int index2NullOffset;
      int dataNullOffset;
      int shiftedHighStart;
   }

   public interface ValueMapper {
      int map(int var1);
   }

   static enum ValueWidth {
      BITS_16,
      BITS_32;
   }
}
