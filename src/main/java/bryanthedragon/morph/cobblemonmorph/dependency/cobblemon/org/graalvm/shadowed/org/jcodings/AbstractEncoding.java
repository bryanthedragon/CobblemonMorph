package org.graalvm.shadowed.org.jcodings;

import org.graalvm.shadowed.org.jcodings.ascii.AsciiTables;
import org.graalvm.shadowed.org.jcodings.constants.PosixBracket;
import org.graalvm.shadowed.org.jcodings.exception.CharacterPropertyException;
import org.graalvm.shadowed.org.jcodings.exception.EncodingError;

abstract class AbstractEncoding extends Encoding {
   private final short[] CTypeTable;

   protected AbstractEncoding(String name, int minLength, int maxLength, short[] CTypeTable) {
      super(name, minLength, maxLength);
      this.CTypeTable = CTypeTable;
   }

   private static int CTypeToBit(int ctype) {
      return 1 << ctype;
   }

   protected final boolean isCodeCTypeInternal(int code, int ctype) {
      return (this.CTypeTable[code] & CTypeToBit(ctype)) != 0;
   }

   @Override
   public boolean isNewLine(byte[] bytes, int p, int end) {
      return p < end ? bytes[p] == 10 : false;
   }

   protected final int asciiMbcCaseFold(int flag, byte[] bytes, IntHolder pp, int end, byte[] lower) {
      lower[0] = AsciiTables.ToLowerCaseTable[bytes[pp.value] & 255];
      pp.value++;
      return 1;
   }

   @Override
   public int mbcCaseFold(int flag, byte[] bytes, IntHolder pp, int end, byte[] lower) {
      return this.asciiMbcCaseFold(flag, bytes, pp, end, lower);
   }

   protected final void asciiApplyAllCaseFold(int flag, ApplyAllCaseFoldFunction fun, Object arg) {
      int[] code = new int[]{0};

      for (int i = 0; i < AsciiTables.LowerMap.length; i++) {
         code[0] = AsciiTables.LowerMap[i][1];
         fun.apply(AsciiTables.LowerMap[i][0], code, 1, arg);
         code[0] = AsciiTables.LowerMap[i][0];
         fun.apply(AsciiTables.LowerMap[i][1], code, 1, arg);
      }
   }

   @Override
   public void applyAllCaseFold(int flag, ApplyAllCaseFoldFunction fun, Object arg) {
      this.asciiApplyAllCaseFold(flag, fun, arg);
   }

   protected final CaseFoldCodeItem[] asciiCaseFoldCodesByString(int flag, byte[] bytes, int p, int end) {
      int b = bytes[p] & 255;
      if (65 <= b && b <= 90) {
         return new CaseFoldCodeItem[]{CaseFoldCodeItem.create(1, b + 32)};
      } else {
         return 97 <= b && b <= 122 ? new CaseFoldCodeItem[]{CaseFoldCodeItem.create(1, b - 32)} : CaseFoldCodeItem.EMPTY_FOLD_CODES;
      }
   }

   @Override
   public CaseFoldCodeItem[] caseFoldCodesByString(int flag, byte[] bytes, int p, int end) {
      return this.asciiCaseFoldCodesByString(flag, bytes, p, end);
   }

   int asciiOnlyCaseMap(IntHolder flagP, byte[] bytes, IntHolder pp, int end, byte[] to, int toP, int toEnd) {
      int toStart = toP;
      int flags = flagP.value;

      while (pp.value < end && toP < toEnd) {
         int length = this.length(bytes, pp.value, end);
         if (length < 0) {
            return length;
         }

         int code = this.mbcToCode(bytes, pp.value, end);
         pp.value += length;
         if (code >= 97 && code <= 122 && (flags & 8192) != 0) {
            flags |= 262144;
            code -= 32;
         } else if (code >= 65 && code <= 90 && (flags & 540672) != 0) {
            flags |= 262144;
            code += 32;
         }

         toP += this.codeToMbc(code, to, toP);
         if ((flags & 32768) != 0) {
            flags ^= 57344;
         }
      }

      flagP.value = flags;
      return toP - toStart;
   }

   int singleByteAsciiOnlyCaseMap(IntHolder flagP, byte[] bytes, IntHolder pp, int end, byte[] to, int toP, int toEnd) {
      int toStart = toP;
      int flags = flagP.value;

      while (pp.value < end && toP < toEnd) {
         int code = bytes[pp.value++] & 255;
         if (code >= 97 && code <= 122 && (flags & 8192) != 0) {
            flags |= 262144;
            code -= 32;
         } else if (code >= 65 && code <= 90 && (flags & 540672) != 0) {
            flags |= 262144;
            code += 32;
         }

         to[toP++] = (byte)code;
         if ((flags & 32768) != 0) {
            flags ^= 57344;
         }
      }

      flagP.value = flags;
      return toP - toStart;
   }

   @Override
   public int propertyNameToCType(byte[] bytes, int p, int end) {
      Integer ctype = PosixBracket.PBSTableUpper.get(bytes, p, end);
      if (ctype != null) {
         return ctype;
      } else {
         throw new CharacterPropertyException(EncodingError.ERR_INVALID_CHAR_PROPERTY_NAME, bytes, p, end - p);
      }
   }
}
