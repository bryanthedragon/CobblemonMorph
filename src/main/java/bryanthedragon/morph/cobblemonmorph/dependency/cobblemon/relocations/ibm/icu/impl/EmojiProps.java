package com.cobblemon.mod.relocations.ibm.icu.impl;

import com.cobblemon.mod.relocations.ibm.icu.text.UnicodeSet;
import com.cobblemon.mod.relocations.ibm.icu.util.BytesTrie;
import com.cobblemon.mod.relocations.ibm.icu.util.CharsTrie;
import com.cobblemon.mod.relocations.ibm.icu.util.CodePointMap;
import com.cobblemon.mod.relocations.ibm.icu.util.CodePointTrie;
import com.cobblemon.mod.relocations.ibm.icu.util.ICUUncheckedIOException;
import java.io.IOException;
import java.nio.ByteBuffer;

public final class EmojiProps {
   private static final EmojiProps.IsAcceptable IS_ACCEPTABLE = new EmojiProps.IsAcceptable();
   private static final int DATA_FORMAT = 1164799850;
   private static final int IX_CPTRIE_OFFSET = 0;
   private static final int IX_BASIC_EMOJI_TRIE_OFFSET = 4;
   private static final int IX_RGI_EMOJI_ZWJ_SEQUENCE_TRIE_OFFSET = 9;
   private static final int BIT_EMOJI = 0;
   private static final int BIT_EMOJI_PRESENTATION = 1;
   private static final int BIT_EMOJI_MODIFIER = 2;
   private static final int BIT_EMOJI_MODIFIER_BASE = 3;
   private static final int BIT_EMOJI_COMPONENT = 4;
   private static final int BIT_EXTENDED_PICTOGRAPHIC = 5;
   private static final int BIT_BASIC_EMOJI = 6;
   public static final EmojiProps INSTANCE = new EmojiProps();
   private CodePointTrie.Fast8 cpTrie = null;
   private String[] stringTries = new String[6];
   private static final byte[] bitFlags = new byte[]{0, 1, 2, 3, 4, -1, -1, 5, 6, -1, -1, -1, -1, -1, 6};

   private static int getStringTrieIndex(int i) {
      return i - 4;
   }

   private EmojiProps() {
      ByteBuffer bytes = ICUBinary.getRequiredData("uemoji.icu");

      try {
         ICUBinary.readHeaderAndDataVersion(bytes, 1164799850, IS_ACCEPTABLE);
         int startPos = bytes.position();
         int cpTrieOffset = bytes.getInt();
         int indexesLength = cpTrieOffset / 4;
         if (indexesLength <= 9) {
            throw new ICUUncheckedIOException("Emoji properties data: not enough indexes");
         } else {
            int[] inIndexes = new int[indexesLength];
            inIndexes[0] = cpTrieOffset;

            for (int i = 1; i < indexesLength; i++) {
               inIndexes[i] = bytes.getInt();
            }

            int i = 0;
            int offset = inIndexes[i++];
            int nextOffset = inIndexes[i];
            this.cpTrie = CodePointTrie.Fast8.fromBinary(bytes);
            int pos = bytes.position() - startPos;

            assert nextOffset >= pos;

            ICUBinary.skipBytes(bytes, nextOffset - pos);
            int var16 = inIndexes[4];
            ICUBinary.skipBytes(bytes, var16 - nextOffset);

            for (int var13 = 4; var13 <= 9; var13++) {
               offset = inIndexes[var13];
               nextOffset = inIndexes[var13 + 1];
               if (nextOffset > offset) {
                  this.stringTries[getStringTrieIndex(var13)] = ICUBinary.getString(bytes, (nextOffset - offset) / 2, 0);
               }
            }
         }
      } catch (IOException var10) {
         throw new ICUUncheckedIOException(var10);
      }
   }

   public UnicodeSet addPropertyStarts(UnicodeSet set) {
      CodePointMap.Range range = new CodePointMap.Range();

      for (int start = 0; this.cpTrie.getRange(start, null, range); start = range.getEnd() + 1) {
         set.add(start);
      }

      return set;
   }

   public boolean hasBinaryProperty(int c, int which) {
      if (which >= 57 && 71 >= which) {
         int bit = bitFlags[which - 57];
         if (bit < 0) {
            return false;
         } else {
            int bits = this.cpTrie.get(c);
            return (bits >> bit & 1) != 0;
         }
      } else {
         return false;
      }
   }

   public boolean hasBinaryProperty(CharSequence s, int which) {
      int length = s.length();
      if (length == 0) {
         return false;
      } else if (which >= 65 && 71 >= which) {
         int firstProp = which;
         int lastProp = which;
         if (which == 71) {
            firstProp = 65;
            lastProp = 70;
         }

         for (int prop = firstProp; prop <= lastProp; prop++) {
            String trieUChars = this.stringTries[prop - 65];
            if (trieUChars != null) {
               CharsTrie trie = new CharsTrie(trieUChars, 0);
               BytesTrie.Result result = trie.next(s, 0, length);
               if (result.hasValue()) {
                  return true;
               }
            }
         }

         return false;
      } else {
         return false;
      }
   }

   public void addStrings(int which, UnicodeSet set) {
      if (which >= 65 && 71 >= which) {
         int firstProp = which;
         int lastProp = which;
         if (which == 71) {
            firstProp = 65;
            lastProp = 70;
         }

         for (int prop = firstProp; prop <= lastProp; prop++) {
            String trieUChars = this.stringTries[prop - 65];
            if (trieUChars != null) {
               for (CharsTrie.Entry entry : new CharsTrie(trieUChars, 0)) {
                  set.add(entry.chars);
               }
            }
         }
      }
   }

   private static final class IsAcceptable implements ICUBinary.Authenticate {
      private IsAcceptable() {
      }

      @Override
      public boolean isDataVersionAcceptable(byte[] version) {
         return version[0] == 1;
      }
   }
}
