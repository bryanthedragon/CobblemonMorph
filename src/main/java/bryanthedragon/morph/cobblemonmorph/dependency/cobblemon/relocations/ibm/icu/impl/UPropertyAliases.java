package com.cobblemon.mod.relocations.ibm.icu.impl;

import com.cobblemon.mod.relocations.ibm.icu.util.BytesTrie;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.MissingResourceException;

public final class UPropertyAliases {
   private static final int IX_VALUE_MAPS_OFFSET = 0;
   private static final int IX_BYTE_TRIES_OFFSET = 1;
   private static final int IX_NAME_GROUPS_OFFSET = 2;
   private static final int IX_RESERVED3_OFFSET = 3;
   private int[] valueMaps;
   private byte[] bytesTries;
   private String nameGroups;
   private static final UPropertyAliases.IsAcceptable IS_ACCEPTABLE = new UPropertyAliases.IsAcceptable();
   private static final int DATA_FORMAT = 1886282093;
   public static final UPropertyAliases INSTANCE;

   private void load(ByteBuffer bytes) throws IOException {
      ICUBinary.readHeader(bytes, 1886282093, IS_ACCEPTABLE);
      int indexesLength = bytes.getInt() / 4;
      if (indexesLength < 8) {
         throw new IOException("pnames.icu: not enough indexes");
      } else {
         int[] inIndexes = new int[indexesLength];
         inIndexes[0] = indexesLength * 4;

         for (int i = 1; i < indexesLength; i++) {
            inIndexes[i] = bytes.getInt();
         }

         int offset = inIndexes[0];
         int nextOffset = inIndexes[1];
         int numInts = (nextOffset - offset) / 4;
         this.valueMaps = ICUBinary.getInts(bytes, numInts, 0);
         int var13 = inIndexes[2];
         int numBytes = var13 - nextOffset;
         this.bytesTries = new byte[numBytes];
         bytes.get(this.bytesTries);
         nextOffset = inIndexes[3];
         numBytes = nextOffset - var13;
         StringBuilder sb = new StringBuilder(numBytes);

         for (int i = 0; i < numBytes; i++) {
            sb.append((char)bytes.get());
         }

         this.nameGroups = sb.toString();
      }
   }

   private UPropertyAliases() throws IOException {
      ByteBuffer bytes = ICUBinary.getRequiredData("pnames.icu");
      this.load(bytes);
   }

   private int findProperty(int property) {
      int i = 1;

      for (int numRanges = this.valueMaps[0]; numRanges > 0; numRanges--) {
         int start = this.valueMaps[i];
         int limit = this.valueMaps[i + 1];
         i += 2;
         if (property < start) {
            break;
         }

         if (property < limit) {
            return i + (property - start) * 2;
         }

         i += (limit - start) * 2;
      }

      return 0;
   }

   private int findPropertyValueNameGroup(int valueMapIndex, int value) {
      if (valueMapIndex == 0) {
         return 0;
      } else {
         int var10001 = ++valueMapIndex;
         valueMapIndex++;
         int numRanges = this.valueMaps[var10001];
         if (numRanges < 16) {
            while (numRanges > 0) {
               int start = this.valueMaps[valueMapIndex];
               int limit = this.valueMaps[valueMapIndex + 1];
               valueMapIndex += 2;
               if (value < start) {
                  break;
               }

               if (value < limit) {
                  return this.valueMaps[valueMapIndex + value - start];
               }

               valueMapIndex += limit - start;
               numRanges--;
            }
         } else {
            int valuesStart = valueMapIndex;
            int nameGroupOffsetsStart = valueMapIndex + numRanges - 16;

            do {
               int v = this.valueMaps[valueMapIndex];
               if (value < v) {
                  break;
               }

               if (value == v) {
                  return this.valueMaps[nameGroupOffsetsStart + valueMapIndex - valuesStart];
               }
            } while (++valueMapIndex < nameGroupOffsetsStart);
         }

         return 0;
      }
   }

   private String getName(int nameGroupsIndex, int nameIndex) {
      int numNames = this.nameGroups.charAt(nameGroupsIndex++);
      if (nameIndex >= 0 && numNames > nameIndex) {
         while (nameIndex > 0) {
            while (0 != this.nameGroups.charAt(nameGroupsIndex++)) {
            }

            nameIndex--;
         }

         int nameStart = nameGroupsIndex;

         while (0 != this.nameGroups.charAt(nameGroupsIndex)) {
            nameGroupsIndex++;
         }

         return nameStart == nameGroupsIndex ? null : this.nameGroups.substring(nameStart, nameGroupsIndex);
      } else {
         throw new IllegalIcuArgumentException("Invalid property (value) name choice");
      }
   }

   private static int asciiToLowercase(int c) {
      return 65 <= c && c <= 90 ? c + 32 : c;
   }

   private boolean containsName(BytesTrie trie, CharSequence name) {
      BytesTrie.Result result = BytesTrie.Result.NO_VALUE;

      for (int i = 0; i < name.length(); i++) {
         int c = name.charAt(i);
         if (c != 45 && c != 95 && c != 32 && (9 > c || c > 13)) {
            if (!result.hasNext()) {
               return false;
            }

            c = asciiToLowercase(c);
            result = trie.next(c);
         }
      }

      return result.hasValue();
   }

   public String getPropertyName(int property, int nameChoice) {
      int valueMapIndex = this.findProperty(property);
      if (valueMapIndex == 0) {
         throw new IllegalArgumentException("Invalid property enum " + property + " (0x" + Integer.toHexString(property) + ")");
      } else {
         return this.getName(this.valueMaps[valueMapIndex], nameChoice);
      }
   }

   public String getPropertyValueName(int property, int value, int nameChoice) {
      int valueMapIndex = this.findProperty(property);
      if (valueMapIndex == 0) {
         throw new IllegalArgumentException("Invalid property enum " + property + " (0x" + Integer.toHexString(property) + ")");
      } else {
         int nameGroupOffset = this.findPropertyValueNameGroup(this.valueMaps[valueMapIndex + 1], value);
         if (nameGroupOffset == 0) {
            throw new IllegalArgumentException("Property " + property + " (0x" + Integer.toHexString(property) + ") does not have named values");
         } else {
            return this.getName(nameGroupOffset, nameChoice);
         }
      }
   }

   private int getPropertyOrValueEnum(int bytesTrieOffset, CharSequence alias) {
      BytesTrie trie = new BytesTrie(this.bytesTries, bytesTrieOffset);
      return this.containsName(trie, alias) ? trie.getValue() : -1;
   }

   public int getPropertyEnum(CharSequence alias) {
      return this.getPropertyOrValueEnum(0, alias);
   }

   public int getPropertyValueEnum(int property, CharSequence alias) {
      int valueMapIndex = this.findProperty(property);
      if (valueMapIndex == 0) {
         throw new IllegalArgumentException("Invalid property enum " + property + " (0x" + Integer.toHexString(property) + ")");
      } else {
         valueMapIndex = this.valueMaps[valueMapIndex + 1];
         if (valueMapIndex == 0) {
            throw new IllegalArgumentException("Property " + property + " (0x" + Integer.toHexString(property) + ") does not have named values");
         } else {
            return this.getPropertyOrValueEnum(this.valueMaps[valueMapIndex], alias);
         }
      }
   }

   public int getPropertyValueEnumNoThrow(int property, CharSequence alias) {
      int valueMapIndex = this.findProperty(property);
      if (valueMapIndex == 0) {
         return -1;
      } else {
         valueMapIndex = this.valueMaps[valueMapIndex + 1];
         return valueMapIndex == 0 ? -1 : this.getPropertyOrValueEnum(this.valueMaps[valueMapIndex], alias);
      }
   }

   public static int compare(String stra, String strb) {
      int istra = 0;
      int istrb = 0;
      int cstra = 0;
      int cstrb = 0;

      while (true) {
         if (istra < stra.length()) {
            cstra = stra.charAt(istra);
            switch (cstra) {
               case 9:
               case 10:
               case 11:
               case 12:
               case 13:
               case 32:
               case 45:
               case 95:
                  istra++;
                  continue;
            }
         }

         while (true) {
            if (istrb < strb.length()) {
               cstrb = strb.charAt(istrb);
               switch (cstrb) {
                  case 9:
                  case 10:
                  case 11:
                  case 12:
                  case 13:
                  case 32:
                  case 45:
                  case 95:
                     istrb++;
                     continue;
               }
            }

            boolean endstra = istra == stra.length();
            boolean endstrb = istrb == strb.length();
            if (endstra) {
               if (endstrb) {
                  return 0;
               }

               cstra = 0;
            } else if (endstrb) {
               cstrb = 0;
            }

            int rc = asciiToLowercase(cstra) - asciiToLowercase(cstrb);
            if (rc != 0) {
               return rc;
            }

            istra++;
            istrb++;
            break;
         }
      }
   }

   static {
      try {
         INSTANCE = new UPropertyAliases();
      } catch (IOException var2) {
         MissingResourceException mre = new MissingResourceException("Could not construct UPropertyAliases. Missing pnames.icu", "", "");
         mre.initCause(var2);
         throw mre;
      }
   }

   private static final class IsAcceptable implements ICUBinary.Authenticate {
      private IsAcceptable() {
      }

      @Override
      public boolean isDataVersionAcceptable(byte[] version) {
         return version[0] == 2;
      }
   }
}
