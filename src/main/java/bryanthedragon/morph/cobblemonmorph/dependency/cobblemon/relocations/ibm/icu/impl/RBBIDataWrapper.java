package com.cobblemon.mod.relocations.ibm.icu.impl;

import com.cobblemon.mod.relocations.ibm.icu.text.RuleBasedBreakIterator;
import com.cobblemon.mod.relocations.ibm.icu.util.CodePointTrie;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public final class RBBIDataWrapper {
   public RBBIDataWrapper.RBBIDataHeader fHeader;
   public RBBIDataWrapper.RBBIStateTable fFTable;
   public RBBIDataWrapper.RBBIStateTable fRTable;
   public CodePointTrie fTrie;
   public String fRuleSource;
   public int[] fStatusTable;
   public static final int DATA_FORMAT = 1114794784;
   public static final int FORMAT_VERSION = 100663296;
   private static final RBBIDataWrapper.IsAcceptable IS_ACCEPTABLE = new RBBIDataWrapper.IsAcceptable();
   public static final int DH_SIZE = 20;
   public static final int DH_MAGIC = 0;
   public static final int DH_FORMATVERSION = 1;
   public static final int DH_LENGTH = 2;
   public static final int DH_CATCOUNT = 3;
   public static final int DH_FTABLE = 4;
   public static final int DH_FTABLELEN = 5;
   public static final int DH_RTABLE = 6;
   public static final int DH_RTABLELEN = 7;
   public static final int DH_TRIE = 8;
   public static final int DH_TRIELEN = 9;
   public static final int DH_RULESOURCE = 10;
   public static final int DH_RULESOURCELEN = 11;
   public static final int DH_STATUSTABLE = 12;
   public static final int DH_STATUSTABLELEN = 13;
   public static final int ACCEPTING = 0;
   public static final int LOOKAHEAD = 1;
   public static final int TAGSIDX = 2;
   public static final int NEXTSTATES = 3;
   public static final int ACCEPTING_UNCONDITIONAL = 1;
   public static final int RBBI_LOOKAHEAD_HARD_BREAK = 1;
   public static final int RBBI_BOF_REQUIRED = 2;
   public static final int RBBI_8BITS_ROWS = 4;

   public static boolean equals(RBBIDataWrapper.RBBIStateTable left, RBBIDataWrapper.RBBIStateTable right) {
      if (left == right) {
         return true;
      } else {
         return left != null && right != null ? left.equals(right) : false;
      }
   }

   public int getRowIndex(int state) {
      return state * (this.fHeader.fCatCount + 3);
   }

   RBBIDataWrapper() {
   }

   public static RBBIDataWrapper get(ByteBuffer bytes) throws IOException {
      RBBIDataWrapper This = new RBBIDataWrapper();
      ICUBinary.readHeader(bytes, 1114794784, IS_ACCEPTABLE);
      This.fHeader = new RBBIDataWrapper.RBBIDataHeader();
      This.fHeader.fMagic = bytes.getInt();
      This.fHeader.fFormatVersion[0] = bytes.get();
      This.fHeader.fFormatVersion[1] = bytes.get();
      This.fHeader.fFormatVersion[2] = bytes.get();
      This.fHeader.fFormatVersion[3] = bytes.get();
      This.fHeader.fLength = bytes.getInt();
      This.fHeader.fCatCount = bytes.getInt();
      This.fHeader.fFTable = bytes.getInt();
      This.fHeader.fFTableLen = bytes.getInt();
      This.fHeader.fRTable = bytes.getInt();
      This.fHeader.fRTableLen = bytes.getInt();
      This.fHeader.fTrie = bytes.getInt();
      This.fHeader.fTrieLen = bytes.getInt();
      This.fHeader.fRuleSource = bytes.getInt();
      This.fHeader.fRuleSourceLen = bytes.getInt();
      This.fHeader.fStatusTable = bytes.getInt();
      This.fHeader.fStatusTableLen = bytes.getInt();
      ICUBinary.skipBytes(bytes, 24);
      if (This.fHeader.fMagic == 45472 && IS_ACCEPTABLE.isDataVersionAcceptable(This.fHeader.fFormatVersion)) {
         int pos = 80;
         if (This.fHeader.fFTable >= pos && This.fHeader.fFTable <= This.fHeader.fLength) {
            ICUBinary.skipBytes(bytes, This.fHeader.fFTable - pos);
            pos = This.fHeader.fFTable;
            This.fFTable = RBBIDataWrapper.RBBIStateTable.get(bytes, This.fHeader.fFTableLen);
            pos += This.fHeader.fFTableLen;
            ICUBinary.skipBytes(bytes, This.fHeader.fRTable - pos);
            pos = This.fHeader.fRTable;
            This.fRTable = RBBIDataWrapper.RBBIStateTable.get(bytes, This.fHeader.fRTableLen);
            pos += This.fHeader.fRTableLen;
            ICUBinary.skipBytes(bytes, This.fHeader.fTrie - pos);
            pos = This.fHeader.fTrie;
            ((Buffer)bytes).mark();
            This.fTrie = CodePointTrie.fromBinary(CodePointTrie.Type.FAST, null, bytes);
            ((Buffer)bytes).reset();
            if (pos > This.fHeader.fStatusTable) {
               throw new IOException("Break iterator Rule data corrupt");
            } else {
               ICUBinary.skipBytes(bytes, This.fHeader.fStatusTable - pos);
               pos = This.fHeader.fStatusTable;
               This.fStatusTable = ICUBinary.getInts(bytes, This.fHeader.fStatusTableLen / 4, This.fHeader.fStatusTableLen & 3);
               pos += This.fHeader.fStatusTableLen;
               if (pos > This.fHeader.fRuleSource) {
                  throw new IOException("Break iterator Rule data corrupt");
               } else {
                  ICUBinary.skipBytes(bytes, This.fHeader.fRuleSource - pos);
                  pos = This.fHeader.fRuleSource;
                  This.fRuleSource = new String(ICUBinary.getBytes(bytes, This.fHeader.fRuleSourceLen, 0), StandardCharsets.UTF_8);
                  if (RuleBasedBreakIterator.fDebugEnv != null && RuleBasedBreakIterator.fDebugEnv.indexOf("data") >= 0) {
                     This.dump(System.out);
                  }

                  return This;
               }
            }
         } else {
            throw new IOException("Break iterator Rule data corrupt");
         }
      } else {
         throw new IOException("Break Iterator Rule Data Magic Number Incorrect, or unsupported data version.");
      }
   }

   public void dump(PrintStream out) {
      if (this.fFTable == null) {
         throw new NullPointerException();
      } else {
         out.println("RBBI Data Wrapper dump ...");
         out.println();
         out.println("Forward State Table");
         this.dumpTable(out, this.fFTable);
         out.println("Reverse State Table");
         this.dumpTable(out, this.fRTable);
         this.dumpCharCategories(out);
         out.println("Source Rules: " + this.fRuleSource);
      }
   }

   public static String intToString(int n, int width) {
      StringBuilder dest = new StringBuilder(width);
      dest.append(n);

      while (dest.length() < width) {
         dest.insert(0, ' ');
      }

      return dest.toString();
   }

   public static String charToString(char n, int width) {
      StringBuilder dest = new StringBuilder(width);
      dest.append(n);

      while (dest.length() < width) {
         dest.insert(0, ' ');
      }

      return dest.toString();
   }

   public static String intToHexString(int n, int width) {
      StringBuilder dest = new StringBuilder(width);
      dest.append(Integer.toHexString(n));

      while (dest.length() < width) {
         dest.insert(0, ' ');
      }

      return dest.toString();
   }

   private void dumpTable(PrintStream out, RBBIDataWrapper.RBBIStateTable table) {
      if (table != null && table.fTable.length != 0) {
         StringBuilder header = new StringBuilder(" Row  Acc Look  Tag");

         for (char n = 0; n < this.fHeader.fCatCount; n++) {
            header.append(intToString(n, 5));
         }

         out.println(header.toString());

         for (char var6 = 0; var6 < header.length(); var6++) {
            out.print("-");
         }

         out.println();

         for (char state = 0; state < table.fNumStates; state++) {
            this.dumpRow(out, table, state);
         }

         out.println();
      } else {
         out.println("  -- null -- ");
      }
   }

   private void dumpRow(PrintStream out, RBBIDataWrapper.RBBIStateTable table, char state) {
      StringBuilder dest = new StringBuilder(this.fHeader.fCatCount * 5 + 20);
      dest.append(intToString(state, 4));
      int row = this.getRowIndex(state);
      if (table.fTable[row + 0] != 0) {
         dest.append(intToString(table.fTable[row + 0], 5));
      } else {
         dest.append("     ");
      }

      if (table.fTable[row + 1] != 0) {
         dest.append(intToString(table.fTable[row + 1], 5));
      } else {
         dest.append("     ");
      }

      dest.append(intToString(table.fTable[row + 2], 5));

      for (int col = 0; col < this.fHeader.fCatCount; col++) {
         dest.append(intToString(table.fTable[row + 3 + col], 5));
      }

      out.println(dest);
   }

   private void dumpCharCategories(PrintStream out) {
      int n = this.fHeader.fCatCount;
      String[] catStrings = new String[n + 1];
      int rangeStart = 0;
      int rangeEnd = 0;
      int lastCat = -1;
      int[] lastNewline = new int[n + 1];

      for (int category = 0; category <= this.fHeader.fCatCount; category++) {
         catStrings[category] = "";
      }

      out.println("\nCharacter Categories");
      out.println("--------------------");

      for (int char32 = 0; char32 <= 1114111; char32++) {
         int var10 = this.fTrie.get(char32);
         if (var10 < 0 || var10 > this.fHeader.fCatCount) {
            out.println("Error, bad category " + Integer.toHexString(var10) + " for char " + Integer.toHexString(char32));
            break;
         }

         if (var10 == lastCat) {
            rangeEnd = char32;
         } else {
            if (lastCat >= 0) {
               if (catStrings[lastCat].length() > lastNewline[lastCat] + 70) {
                  lastNewline[lastCat] = catStrings[lastCat].length() + 10;
                  catStrings[lastCat] = catStrings[lastCat] + "\n       ";
               }

               catStrings[lastCat] = catStrings[lastCat] + " " + Integer.toHexString(rangeStart);
               if (rangeEnd != rangeStart) {
                  catStrings[lastCat] = catStrings[lastCat] + "-" + Integer.toHexString(rangeEnd);
               }
            }

            lastCat = var10;
            rangeEnd = char32;
            rangeStart = char32;
         }
      }

      catStrings[lastCat] = catStrings[lastCat] + " " + Integer.toHexString(rangeStart);
      if (rangeEnd != rangeStart) {
         catStrings[lastCat] = catStrings[lastCat] + "-" + Integer.toHexString(rangeEnd);
      }

      for (int var11 = 0; var11 <= this.fHeader.fCatCount; var11++) {
         out.println(intToString(var11, 5) + "  " + catStrings[var11]);
      }

      out.println();
   }

   private static final class IsAcceptable implements ICUBinary.Authenticate {
      private IsAcceptable() {
      }

      @Override
      public boolean isDataVersionAcceptable(byte[] version) {
         int intVersion = (version[0] << 24) + (version[1] << 16) + (version[2] << 8) + version[3];
         return intVersion == 100663296;
      }
   }

   public static final class RBBIDataHeader {
      int fMagic = 0;
      byte[] fFormatVersion = new byte[4];
      int fLength;
      public int fCatCount;
      int fFTable;
      int fFTableLen;
      int fRTable;
      int fRTableLen;
      int fTrie;
      int fTrieLen;
      int fRuleSource;
      int fRuleSourceLen;
      int fStatusTable;
      int fStatusTableLen;
   }

   public static class RBBIStateTable {
      public int fNumStates;
      public int fRowLen;
      public int fDictCategoriesStart;
      public int fLookAheadResultsSize;
      public int fFlags;
      public static int fHeaderSize = 20;
      public char[] fTable;

      static RBBIDataWrapper.RBBIStateTable get(ByteBuffer bytes, int length) throws IOException {
         if (length == 0) {
            return null;
         } else if (length < fHeaderSize) {
            throw new IOException("Invalid RBBI state table length.");
         } else {
            RBBIDataWrapper.RBBIStateTable This = new RBBIDataWrapper.RBBIStateTable();
            This.fNumStates = bytes.getInt();
            This.fRowLen = bytes.getInt();
            This.fDictCategoriesStart = bytes.getInt();
            This.fLookAheadResultsSize = bytes.getInt();
            This.fFlags = bytes.getInt();
            int lengthOfTable = length - fHeaderSize;
            boolean use8Bits = (This.fFlags & 4) == 4;
            if (use8Bits) {
               This.fTable = new char[lengthOfTable];

               for (int i = 0; i < lengthOfTable; i++) {
                  byte b = bytes.get();
                  This.fTable[i] = (char)(255 & b);
               }

               ICUBinary.skipBytes(bytes, lengthOfTable & 1);
            } else {
               This.fTable = ICUBinary.getChars(bytes, lengthOfTable / 2, lengthOfTable & 1);
            }

            return This;
         }
      }

      public int put(DataOutputStream bytes) throws IOException {
         bytes.writeInt(this.fNumStates);
         bytes.writeInt(this.fRowLen);
         bytes.writeInt(this.fDictCategoriesStart);
         bytes.writeInt(this.fLookAheadResultsSize);
         bytes.writeInt(this.fFlags);
         if ((this.fFlags & 4) == 4) {
            int tableLen = this.fRowLen * this.fNumStates;

            for (int i = 0; i < tableLen; i++) {
               byte b = (byte)(this.fTable[i] & 255);
               bytes.writeByte(b);
            }
         } else {
            int tableLen = this.fRowLen * this.fNumStates / 2;

            for (int i = 0; i < tableLen; i++) {
               bytes.writeChar(this.fTable[i]);
            }
         }

         int bytesWritten;
         for (bytesWritten = fHeaderSize + this.fRowLen * this.fNumStates; bytesWritten % 8 != 0; bytesWritten++) {
            bytes.writeByte(0);
         }

         return bytesWritten;
      }

      @Override
      public boolean equals(Object other) {
         if (other == this) {
            return true;
         } else if (!(other instanceof RBBIDataWrapper.RBBIStateTable)) {
            return false;
         } else {
            RBBIDataWrapper.RBBIStateTable otherST = (RBBIDataWrapper.RBBIStateTable)other;
            if (this.fNumStates != otherST.fNumStates) {
               return false;
            } else if (this.fRowLen != otherST.fRowLen) {
               return false;
            } else if (this.fDictCategoriesStart != otherST.fDictCategoriesStart) {
               return false;
            } else if (this.fLookAheadResultsSize != otherST.fLookAheadResultsSize) {
               return false;
            } else {
               return this.fFlags != otherST.fFlags ? false : Arrays.equals(this.fTable, otherST.fTable);
            }
         }
      }
   }
}
