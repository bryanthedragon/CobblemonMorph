package org.graalvm.shadowed.org.jcodings.unicode;

import java.io.DataInputStream;
import java.io.IOException;
import org.graalvm.shadowed.org.jcodings.ApplyAllCaseFoldFunction;
import org.graalvm.shadowed.org.jcodings.CaseFoldCodeItem;
import org.graalvm.shadowed.org.jcodings.CodeRange;
import org.graalvm.shadowed.org.jcodings.IntHolder;
import org.graalvm.shadowed.org.jcodings.MultiByteEncoding;
import org.graalvm.shadowed.org.jcodings.exception.CharacterPropertyException;
import org.graalvm.shadowed.org.jcodings.exception.EncodingError;
import org.graalvm.shadowed.org.jcodings.util.ArrayReader;
import org.graalvm.shadowed.org.jcodings.util.CaseInsensitiveBytesHash;
import org.graalvm.shadowed.org.jcodings.util.IntArrayHash;
import org.graalvm.shadowed.org.jcodings.util.IntHash;

public abstract class UnicodeEncoding extends MultiByteEncoding {
   private static final int PROPERTY_NAME_MAX_SIZE = 45;
   static final int I_WITH_DOT_ABOVE = 304;
   static final int DOTLESS_i = 305;
   static final int DOT_ABOVE = 775;
   static final int CASE_MAPPING_SLACK = 12;
   static final short[] UNICODE_ISO_8859_1_CTypeTable = new short[]{
      16392,
      16392,
      16392,
      16392,
      16392,
      16392,
      16392,
      16392,
      16392,
      16908,
      16905,
      16904,
      16904,
      16904,
      16392,
      16392,
      16392,
      16392,
      16392,
      16392,
      16392,
      16392,
      16392,
      16392,
      16392,
      16392,
      16392,
      16392,
      16392,
      16392,
      16392,
      16392,
      17028,
      16800,
      16800,
      16800,
      16800,
      16800,
      16800,
      16800,
      16800,
      16800,
      16800,
      16800,
      16800,
      16800,
      16800,
      16800,
      30896,
      30896,
      30896,
      30896,
      30896,
      30896,
      30896,
      30896,
      30896,
      30896,
      16800,
      16800,
      16800,
      16800,
      16800,
      16800,
      16800,
      31906,
      31906,
      31906,
      31906,
      31906,
      31906,
      29858,
      29858,
      29858,
      29858,
      29858,
      29858,
      29858,
      29858,
      29858,
      29858,
      29858,
      29858,
      29858,
      29858,
      29858,
      29858,
      29858,
      29858,
      29858,
      29858,
      16800,
      16800,
      16800,
      16800,
      20896,
      16800,
      30946,
      30946,
      30946,
      30946,
      30946,
      30946,
      28898,
      28898,
      28898,
      28898,
      28898,
      28898,
      28898,
      28898,
      28898,
      28898,
      28898,
      28898,
      28898,
      28898,
      28898,
      28898,
      28898,
      28898,
      28898,
      28898,
      16800,
      16800,
      16800,
      16800,
      16392,
      8,
      8,
      8,
      8,
      8,
      648,
      8,
      8,
      8,
      8,
      8,
      8,
      8,
      8,
      8,
      8,
      8,
      8,
      8,
      8,
      8,
      8,
      8,
      8,
      8,
      8,
      8,
      8,
      8,
      8,
      8,
      8,
      644,
      416,
      160,
      160,
      160,
      160,
      160,
      160,
      160,
      160,
      12514,
      416,
      160,
      168,
      160,
      160,
      160,
      160,
      4256,
      4256,
      160,
      12514,
      160,
      416,
      160,
      4256,
      12514,
      416,
      4256,
      4256,
      4256,
      416,
      13474,
      13474,
      13474,
      13474,
      13474,
      13474,
      13474,
      13474,
      13474,
      13474,
      13474,
      13474,
      13474,
      13474,
      13474,
      13474,
      13474,
      13474,
      13474,
      13474,
      13474,
      13474,
      13474,
      160,
      13474,
      13474,
      13474,
      13474,
      13474,
      13474,
      13474,
      12514,
      12514,
      12514,
      12514,
      12514,
      12514,
      12514,
      12514,
      12514,
      12514,
      12514,
      12514,
      12514,
      12514,
      12514,
      12514,
      12514,
      12514,
      12514,
      12514,
      12514,
      12514,
      12514,
      12514,
      160,
      12514,
      12514,
      12514,
      12514,
      12514,
      12514,
      12514,
      12514
   };

   protected UnicodeEncoding(String name, int minLength, int maxLength, int[] EncLen, int[][] Trans) {
      super(name, minLength, maxLength, EncLen, Trans, UNICODE_ISO_8859_1_CTypeTable);
      this.isUnicode = true;
   }

   protected UnicodeEncoding(String name, int minLength, int maxLength, int[] EncLen) {
      this(name, minLength, maxLength, EncLen, null);
   }

   @Override
   public String getCharsetName() {
      return new String(this.getName());
   }

   @Override
   public boolean isCodeCType(int code, int ctype) {
      if (ctype <= 14 && code < 256) {
         return this.isCodeCTypeInternal(code, ctype);
      } else if (ctype > UnicodeCodeRange.CodeRangeTable.length) {
         throw new InternalError("undefined type (bug)");
      } else {
         return CodeRange.isInCodeRange(UnicodeCodeRange.CodeRangeTable[ctype].getRange(), code);
      }
   }

   public static boolean isInCodeRange(UnicodeCodeRange range, int code) {
      return CodeRange.isInCodeRange(range.getRange(), code);
   }

   protected final int[] ctypeCodeRange(int ctype) {
      if (ctype >= UnicodeCodeRange.CodeRangeTable.length) {
         throw new InternalError("undefined type (bug)");
      } else {
         return UnicodeCodeRange.CodeRangeTable[ctype].getRange();
      }
   }

   @Override
   public int propertyNameToCType(byte[] name, int p, int end) {
      byte[] buf = new byte[45];
      int len = 0;
      int p_ = p;

      while (p_ < end) {
         int code = this.mbcToCode(name, p_, end);
         if (code != 32 && code != 45 && code != 95) {
            if (code >= 128) {
               throw new CharacterPropertyException(EncodingError.ERR_INVALID_CHAR_PROPERTY_NAME, name, p, end);
            }

            buf[len++] = (byte)code;
            if (len >= 45) {
               throw new CharacterPropertyException(EncodingError.ERR_INVALID_CHAR_PROPERTY_NAME, name, p, end);
            }
         }

         p_ += this.length(name, p_, end);
      }

      Integer ctype = UnicodeEncoding.CTypeName.Values.get(buf, 0, len);
      if (ctype == null) {
         throw new CharacterPropertyException(EncodingError.ERR_INVALID_CHAR_PROPERTY_NAME, name, p, end);
      } else {
         return ctype;
      }
   }

   @Override
   public int mbcCaseFold(int flag, byte[] bytes, IntHolder pp, int end, byte[] fold) {
      int p = pp.value;
      int foldP = 0;
      int code = this.mbcToCode(bytes, p, end);
      int len = this.length(bytes, p, end);
      pp.value += len;
      UnicodeEncoding.CodeList to = UnicodeEncoding.CaseFold.Values.get(code);
      if (to != null) {
         if (to.codes.length == 1) {
            return this.codeToMbc(to.codes[0], fold, foldP);
         } else {
            int rlen = 0;

            for (int i = 0; i < to.codes.length; i++) {
               len = this.codeToMbc(to.codes[i], fold, foldP);
               foldP += len;
               rlen += len;
            }

            return rlen;
         }
      } else {
         for (int i = 0; i < len; i++) {
            fold[foldP++] = bytes[p++];
         }

         return len;
      }
   }

   @Override
   public void applyAllCaseFold(int flag, ApplyAllCaseFoldFunction fun, Object arg) {
      int[] code = new int[]{0};

      for (int i = 0; i < UnicodeEncoding.CaseUnfold11.From.length; i++) {
         int from = UnicodeEncoding.CaseUnfold11.From[i];
         UnicodeEncoding.CodeList to = UnicodeEncoding.CaseUnfold11.To[i];

         for (int j = 0; j < to.codes.length; j++) {
            code[0] = from;
            fun.apply(to.codes[j], code, 1, arg);
            code[0] = to.codes[j];
            fun.apply(from, code, 1, arg);

            for (int k = 0; k < j; k++) {
               code[0] = to.codes[k];
               fun.apply(to.codes[j], code, 1, arg);
               code[0] = to.codes[j];
               fun.apply(to.codes[k], code, 1, arg);
            }
         }
      }

      for (int i = 0; i < UnicodeEncoding.CaseUnfold11.Locale_From.length; i++) {
         int from = UnicodeEncoding.CaseUnfold11.Locale_From[i];
         UnicodeEncoding.CodeList to = UnicodeEncoding.CaseUnfold11.Locale_To[i];

         for (int j = 0; j < to.codes.length; j++) {
            code[0] = from;
            fun.apply(to.codes[j], code, 1, arg);
            code[0] = to.codes[j];
            fun.apply(from, code, 1, arg);

            for (int k = 0; k < j; k++) {
               code[0] = to.codes[k];
               fun.apply(to.codes[j], code, 1, arg);
               code[0] = to.codes[j];
               fun.apply(to.codes[k], code, 1, arg);
            }
         }
      }

      if ((flag & 1073741824) != 0) {
         for (int i = 0; i < UnicodeEncoding.CaseUnfold12.From.length; i++) {
            int[] from = UnicodeEncoding.CaseUnfold12.From[i];
            UnicodeEncoding.CodeList to = UnicodeEncoding.CaseUnfold12.To[i];

            for (int j = 0; j < to.codes.length; j++) {
               fun.apply(to.codes[j], from, 2, arg);

               for (int k = 0; k < to.codes.length; k++) {
                  if (k != j) {
                     code[0] = to.codes[k];
                     fun.apply(to.codes[j], code, 1, arg);
                  }
               }
            }
         }

         for (int i = 0; i < UnicodeEncoding.CaseUnfold12.Locale_From.length; i++) {
            int[] from = UnicodeEncoding.CaseUnfold12.Locale_From[i];
            UnicodeEncoding.CodeList to = UnicodeEncoding.CaseUnfold12.Locale_To[i];

            for (int j = 0; j < to.codes.length; j++) {
               fun.apply(to.codes[j], from, 2, arg);

               for (int kx = 0; kx < to.codes.length; kx++) {
                  if (kx != j) {
                     code[0] = to.codes[kx];
                     fun.apply(to.codes[j], code, 1, arg);
                  }
               }
            }
         }

         for (int i = 0; i < UnicodeEncoding.CaseUnfold13.From.length; i++) {
            int[] from = UnicodeEncoding.CaseUnfold13.From[i];
            UnicodeEncoding.CodeList to = UnicodeEncoding.CaseUnfold13.To[i];

            for (int j = 0; j < to.codes.length; j++) {
               fun.apply(to.codes[j], from, 3, arg);

               for (int kxx = 0; kxx < to.codes.length; kxx++) {
                  if (kxx != j) {
                     code[0] = to.codes[kxx];
                     fun.apply(to.codes[j], code, 1, arg);
                  }
               }
            }
         }
      }
   }

   @Override
   public CaseFoldCodeItem[] caseFoldCodesByString(int flag, byte[] bytes, int p, int end) {
      int code = this.mbcToCode(bytes, p, end);
      int len = this.length(bytes, p, end);
      int n = 0;
      int fn = 0;
      UnicodeEncoding.CodeList to = UnicodeEncoding.CaseFold.Values.get(code);
      CaseFoldCodeItem[] items = null;
      if (to != null) {
         items = new CaseFoldCodeItem[13];
         if (to.codes.length == 1) {
            int origCode = code;
            items[0] = CaseFoldCodeItem.create(len, to.codes[0]);
            n++;
            code = to.codes[0];
            to = UnicodeEncoding.CaseUnfold11.Values.get(code);
            if (to != null) {
               for (int i = 0; i < to.codes.length; i++) {
                  if (to.codes[i] != origCode) {
                     items[n] = CaseFoldCodeItem.create(len, to.codes[i]);
                     n++;
                  }
               }
            }
         } else if ((flag & 1073741824) != 0) {
            int[][] cs = new int[3][4];
            int[] ncs = new int[3];

            for (fn = 0; fn < to.codes.length; fn++) {
               cs[fn][0] = to.codes[fn];
               UnicodeEncoding.CodeList z3 = UnicodeEncoding.CaseUnfold11.Values.get(cs[fn][0]);
               if (z3 == null) {
                  ncs[fn] = 1;
               } else {
                  for (int ix = 0; ix < z3.codes.length; ix++) {
                     cs[fn][ix + 1] = z3.codes[ix];
                  }

                  ncs[fn] = z3.codes.length + 1;
               }
            }

            if (fn == 2) {
               for (int ix = 0; ix < ncs[0]; ix++) {
                  for (int j = 0; j < ncs[1]; j++) {
                     items[n] = CaseFoldCodeItem.create(len, cs[0][ix], cs[1][j]);
                     n++;
                  }
               }

               UnicodeEncoding.CodeList z2 = UnicodeEncoding.CaseUnfold12.Values.get(to.codes);
               if (z2 != null) {
                  for (int ix = 0; ix < z2.codes.length; ix++) {
                     if (z2.codes[ix] != code) {
                        items[n] = CaseFoldCodeItem.create(len, z2.codes[ix]);
                        n++;
                     }
                  }
               }
            } else {
               for (int ixx = 0; ixx < ncs[0]; ixx++) {
                  for (int j = 0; j < ncs[1]; j++) {
                     for (int k = 0; k < ncs[2]; k++) {
                        items[n] = CaseFoldCodeItem.create(len, cs[0][ixx], cs[1][j], cs[2][k]);
                        n++;
                     }
                  }
               }

               UnicodeEncoding.CodeList z2 = UnicodeEncoding.CaseUnfold13.Values.get(to.codes);
               if (z2 != null) {
                  for (int ixx = 0; ixx < z2.codes.length; ixx++) {
                     if (z2.codes[ixx] != code) {
                        items[n] = CaseFoldCodeItem.create(len, z2.codes[ixx]);
                        n++;
                     }
                  }
               }
            }

            flag = 0;
         }
      } else {
         to = UnicodeEncoding.CaseUnfold11.Values.get(code);
         if (to != null) {
            items = new CaseFoldCodeItem[13];

            for (int ixxx = 0; ixxx < to.codes.length; ixxx++) {
               items[n] = CaseFoldCodeItem.create(len, to.codes[ixxx]);
               n++;
            }
         }
      }

      if ((flag & 1073741824) != 0) {
         if (items == null) {
            items = new CaseFoldCodeItem[13];
         }

         p += len;
         if (p < end) {
            code = this.mbcToCode(bytes, p, end);
            to = UnicodeEncoding.CaseFold.Values.get(code);
            int codes1;
            if (to != null && to.codes.length == 1) {
               codes1 = to.codes[0];
            } else {
               codes1 = code;
            }

            int clen = this.length(bytes, p, end);
            len += clen;
            UnicodeEncoding.CodeList z2 = UnicodeEncoding.CaseUnfold12.Values.get(code, codes1);
            if (z2 != null) {
               for (int ixxx = 0; ixxx < z2.codes.length; ixxx++) {
                  items[n] = CaseFoldCodeItem.create(len, z2.codes[ixxx]);
                  n++;
               }
            }

            p += clen;
            if (p < end) {
               code = this.mbcToCode(bytes, p, end);
               to = UnicodeEncoding.CaseFold.Values.get(code);
               int codes2;
               if (to != null && to.codes.length == 1) {
                  codes2 = to.codes[0];
               } else {
                  codes2 = code;
               }

               clen = this.length(bytes, p, end);
               len += clen;
               z2 = UnicodeEncoding.CaseUnfold13.Values.get(code, codes1, codes2);
               if (z2 != null) {
                  for (int ixxx = 0; ixxx < z2.codes.length; ixxx++) {
                     items[n] = CaseFoldCodeItem.create(len, z2.codes[ixxx]);
                     n++;
                  }
               }
            }
         }
      }

      if (items == null || n == 0) {
         return CaseFoldCodeItem.EMPTY_FOLD_CODES;
      } else if (n < items.length) {
         CaseFoldCodeItem[] tmp = new CaseFoldCodeItem[n];
         System.arraycopy(items, 0, tmp, 0, n);
         return tmp;
      } else {
         return items;
      }
   }

   @Override
   public final int caseMap(IntHolder flagP, byte[] bytes, IntHolder pp, int end, byte[] to, int toP, int toEnd) {
      int flags = flagP.value;
      int toStart = toP;
      toEnd -= 12;
      flags |= (flags & 24576) << 3;

      while (pp.value < end && toP <= toEnd) {
         int length = this.length(bytes, pp.value, end);
         if (length < 0) {
            return length;
         }

         int code = this.mbcToCode(bytes, pp.value, end);
         pp.value += length;
         if (code <= 122) {
            if (code >= 97 && code <= 122) {
               if ((flags & 8192) != 0) {
                  flags |= 262144;
                  if ((flags & 1048576) != 0 && code == 105) {
                     code = 304;
                  } else {
                     code -= 32;
                  }
               }
            } else if (code >= 65 && code <= 90 && (flags & 540672) != 0) {
               flags |= 262144;
               if ((flags & 1048576) != 0 && code == 73) {
                  code = 305;
               } else {
                  code += 32;
               }
            }
         } else if ((flags & 4194304) == 0 && code >= 181) {
            if (code == 304) {
               if ((flags & 540672) != 0) {
                  flags |= 262144;
                  code = 105;
                  if ((flags & 1048576) == 0) {
                     toP += this.codeToMbc(code, to, toP);
                     code = 775;
                  }
               }
            } else if (code == 305) {
               if ((flags & 8192) != 0) {
                  flags |= 262144;
                  code = 73;
               }
            } else {
               UnicodeEncoding.CodeList folded;
               if ((folded = UnicodeEncoding.CaseFold.Values.get(code)) != null) {
                  if ((flags & 32768) != 0 && code >= 7312 && code <= 7359) {
                     flags |= 262144;
                     code -= 3008;
                  } else if (((flags & 32768) == 0 || (folded.flags & 8388608) == 0) && (flags & folded.flags) != 0) {
                     boolean specialCopy = false;
                     flags |= 262144;
                     int[] codes;
                     int start;
                     int finish;
                     if ((flags & folded.flags & 8617984) != 0) {
                        codes = UnicodeEncoding.CaseMappingSpecials.Values;
                        int specialStart = (folded.flags & 8184) >>> 3;
                        if ((folded.flags & 8388608) != 0) {
                           if ((flags & 24576) == 24576) {
                              specialCopy = true;
                           } else {
                              specialStart += extractLength(codes[specialStart]);
                           }
                        }

                        if (!specialCopy && (folded.flags & 32768) != 0) {
                           if ((flags & 32768) != 0) {
                              specialCopy = true;
                           } else {
                              specialStart += extractLength(codes[specialStart]);
                           }
                        }

                        if (!specialCopy && (folded.flags & 131072) != 0 && (flags & 131072) == 0) {
                           specialStart += extractLength(codes[specialStart]);
                        }

                        start = specialStart;
                        finish = specialStart + extractLength(codes[specialStart]);
                        code = extractCode(codes[specialStart]);
                     } else {
                        codes = folded.codes;
                        start = 0;
                        finish = folded.codes.length;
                        code = codes[0];
                     }

                     for (int i = start + 1; i < finish; i++) {
                        toP += this.codeToMbc(code, to, toP);
                        code = codes[i];
                     }
                  }
               } else if ((folded = UnicodeEncoding.CaseUnfold11.Values.get(code)) != null
                  && ((flags & 32768) == 0 || (folded.flags & 8388608) == 0)
                  && (flags & folded.flags) != 0) {
                  flags |= 262144;
                  code = folded.codes[(flags & folded.flags & 32768) != 0 ? 1 : 0];
               }
            }
         }

         toP += this.codeToMbc(code, to, toP);
         if ((flags & 32768) != 0) {
            flags ^= 253952;
         }
      }

      flagP.value = flags;
      return toP - toStart;
   }

   private static Object[] readFoldN(int fromSize, String table) {
      try {
         DataInputStream dis = ArrayReader.openStream(table);
         int size = dis.readInt();
         int[][] from = new int[size][];
         UnicodeEncoding.CodeList[] to = new UnicodeEncoding.CodeList[size];

         for (int i = 0; i < size; i++) {
            from[i] = new int[fromSize];

            for (int j = 0; j < fromSize; j++) {
               from[i][j] = dis.readInt();
            }

            to[i] = new UnicodeEncoding.CodeList(dis);
         }

         dis.close();
         return new Object[]{from, to};
      } catch (IOException var8) {
         throw new RuntimeException(var8);
      }
   }

   private static int extractLength(int packed) {
      return packed >>> 25;
   }

   private static int extractCode(int packed) {
      return packed & 33554431;
   }

   static class CTypeName {
      private static final CaseInsensitiveBytesHash<Integer> Values = initializeCTypeNameTable();

      private static CaseInsensitiveBytesHash<Integer> initializeCTypeNameTable() {
         CaseInsensitiveBytesHash<Integer> table = new CaseInsensitiveBytesHash<>();

         for (int i = 0; i < UnicodeCodeRange.CodeRangeTable.length; i++) {
            table.putDirect(UnicodeCodeRange.CodeRangeTable[i].name, i);
         }

         return table;
      }
   }

   private static class CaseFold {
      static final IntHash<UnicodeEncoding.CodeList> Values = read("CaseFold");

      static IntHash<UnicodeEncoding.CodeList> read(String table) {
         try {
            DataInputStream dis = ArrayReader.openStream(table);
            int size = dis.readInt();
            IntHash<UnicodeEncoding.CodeList> hash = new IntHash<>(size);

            for (int i = 0; i < size; i++) {
               hash.putDirect(dis.readInt(), new UnicodeEncoding.CodeList(dis));
            }

            dis.close();
            return hash;
         } catch (IOException var5) {
            throw new RuntimeException(var5);
         }
      }
   }

   private static class CaseMappingSpecials {
      static final int[] Values = ArrayReader.readIntArray("CaseMappingSpecials");
   }

   private static class CaseUnfold11 {
      private static final int[] From;
      private static final UnicodeEncoding.CodeList[] To;
      private static final int[] Locale_From;
      private static final UnicodeEncoding.CodeList[] Locale_To;
      static final IntHash<UnicodeEncoding.CodeList> Values = initializeUnfold1Hash();

      static Object[] read(String table) {
         try {
            DataInputStream dis = ArrayReader.openStream(table);
            int size = dis.readInt();
            int[] from = new int[size];
            UnicodeEncoding.CodeList[] to = new UnicodeEncoding.CodeList[size];

            for (int i = 0; i < size; i++) {
               from[i] = dis.readInt();
               to[i] = new UnicodeEncoding.CodeList(dis);
            }

            dis.close();
            return new Object[]{from, to};
         } catch (IOException var6) {
            throw new RuntimeException(var6);
         }
      }

      static IntHash<UnicodeEncoding.CodeList> initializeUnfold1Hash() {
         IntHash<UnicodeEncoding.CodeList> hash = new IntHash<>(From.length + Locale_From.length);

         for (int i = 0; i < From.length; i++) {
            hash.putDirect(From[i], To[i]);
         }

         for (int i = 0; i < Locale_From.length; i++) {
            hash.putDirect(Locale_From[i], Locale_To[i]);
         }

         return hash;
      }

      static {
         Object[] unfold = read("CaseUnfold_11");
         From = (int[])unfold[0];
         To = (UnicodeEncoding.CodeList[])unfold[1];
         unfold = read("CaseUnfold_11_Locale");
         Locale_From = (int[])unfold[0];
         Locale_To = (UnicodeEncoding.CodeList[])unfold[1];
      }
   }

   private static class CaseUnfold12 {
      private static final int[][] From;
      private static final UnicodeEncoding.CodeList[] To;
      private static final int[][] Locale_From;
      private static final UnicodeEncoding.CodeList[] Locale_To;
      static final IntArrayHash<UnicodeEncoding.CodeList> Values = initializeUnfold2Hash();

      private static IntArrayHash<UnicodeEncoding.CodeList> initializeUnfold2Hash() {
         IntArrayHash<UnicodeEncoding.CodeList> unfold2 = new IntArrayHash<>(From.length + Locale_From.length);

         for (int i = 0; i < From.length; i++) {
            unfold2.putDirect(From[i], To[i]);
         }

         for (int i = 0; i < Locale_From.length; i++) {
            unfold2.putDirect(Locale_From[i], Locale_To[i]);
         }

         return unfold2;
      }

      static {
         Object[] unfold = UnicodeEncoding.readFoldN(2, "CaseUnfold_12");
         From = (int[][])unfold[0];
         To = (UnicodeEncoding.CodeList[])unfold[1];
         unfold = UnicodeEncoding.readFoldN(2, "CaseUnfold_12_Locale");
         Locale_From = (int[][])unfold[0];
         Locale_To = (UnicodeEncoding.CodeList[])unfold[1];
      }
   }

   private static class CaseUnfold13 {
      private static final int[][] From;
      private static final UnicodeEncoding.CodeList[] To;
      static final IntArrayHash<UnicodeEncoding.CodeList> Values = initializeUnfold3Hash();

      private static IntArrayHash<UnicodeEncoding.CodeList> initializeUnfold3Hash() {
         IntArrayHash<UnicodeEncoding.CodeList> unfold3 = new IntArrayHash<>(From.length);

         for (int i = 0; i < From.length; i++) {
            unfold3.putDirect(From[i], To[i]);
         }

         return unfold3;
      }

      static {
         Object[] unfold = UnicodeEncoding.readFoldN(3, "CaseUnfold_13");
         From = (int[][])unfold[0];
         To = (UnicodeEncoding.CodeList[])unfold[1];
      }
   }

   private static class CodeList {
      final int[] codes;
      final int flags;

      CodeList(DataInputStream dis) throws IOException {
         int packed = dis.readInt();
         this.flags = packed & -8;
         int length = packed & 7;
         this.codes = new int[length];

         for (int j = 0; j < length; j++) {
            this.codes[j] = dis.readInt();
         }
      }
   }
}
