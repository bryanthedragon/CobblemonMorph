package com.cobblemon.mod.relocations.ibm.icu.impl;

import com.cobblemon.mod.relocations.ibm.icu.util.ICUException;
import com.cobblemon.mod.relocations.ibm.icu.util.TimeZone;
import com.cobblemon.mod.relocations.ibm.icu.util.UResourceBundle;
import com.cobblemon.mod.relocations.ibm.icu.util.UResourceBundleIterator;
import java.util.Arrays;

public class EraRules {
   private static final int MAX_ENCODED_START_YEAR = 32767;
   private static final int MIN_ENCODED_START_YEAR = -32768;
   public static final int MIN_ENCODED_START = encodeDate(-32768, 1, 1);
   private static final int YEAR_MASK = -65536;
   private static final int MONTH_MASK = 65280;
   private static final int DAY_MASK = 255;
   private int[] startDates;
   private int numEras;
   private int currentEra;

   private EraRules(int[] startDates, int numEras) {
      this.startDates = startDates;
      this.numEras = numEras;
      this.initCurrentEra();
   }

   public static EraRules getInstance(CalType calType, boolean includeTentativeEra) {
      UResourceBundle supplementalDataRes = UResourceBundle.getBundleInstance(
         "com/cobblemon/mod/relocations/ibm/icu/impl/data/icudt71b", "supplementalData", ICUResourceBundle.ICU_DATA_CLASS_LOADER
      );
      UResourceBundle calendarDataRes = supplementalDataRes.get("calendarData");
      UResourceBundle calendarTypeRes = calendarDataRes.get(calType.getId());
      UResourceBundle erasRes = calendarTypeRes.get("eras");
      int numEras = erasRes.getSize();
      int firstTentativeIdx = Integer.MAX_VALUE;
      int[] startDates = new int[numEras];
      UResourceBundleIterator itr = erasRes.getIterator();

      while (itr.hasNext()) {
         UResourceBundle eraRuleRes = itr.next();
         String eraIdxStr = eraRuleRes.getKey();
         int eraIdx = -1;

         try {
            eraIdx = Integer.parseInt(eraIdxStr);
         } catch (NumberFormatException var19) {
            throw new ICUException("Invalid era rule key:" + eraIdxStr + " in era rule data for " + calType.getId());
         }

         if (eraIdx < 0 || eraIdx >= numEras) {
            throw new ICUException("Era rule key:" + eraIdxStr + " in era rule data for " + calType.getId() + " must be in range [0, " + (numEras - 1) + "]");
         }

         if (isSet(startDates[eraIdx])) {
            throw new ICUException("Duplicated era rule for rule key:" + eraIdxStr + " in era rule data for " + calType.getId());
         }

         boolean hasName = true;
         boolean hasEnd = false;
         UResourceBundleIterator ruleItr = eraRuleRes.getIterator();

         while (ruleItr.hasNext()) {
            UResourceBundle res = ruleItr.next();
            String key = res.getKey();
            if (key.equals("start")) {
               int[] fields = res.getIntVector();
               if (fields.length != 3 || !isValidRuleStartDate(fields[0], fields[1], fields[2])) {
                  throw new ICUException("Invalid era rule date data:" + Arrays.toString(fields) + " in era rule data for " + calType.getId());
               }

               startDates[eraIdx] = encodeDate(fields[0], fields[1], fields[2]);
            } else if (key.equals("named")) {
               String val = res.getString();
               if (val.equals("false")) {
                  hasName = false;
               }
            } else if (key.equals("end")) {
               hasEnd = true;
            }
         }

         if (isSet(startDates[eraIdx])) {
            if (hasEnd) {
            }
         } else {
            if (!hasEnd) {
               throw new ICUException("Missing era start/end rule date for key:" + eraIdxStr + " in era rule data for " + calType.getId());
            }

            if (eraIdx != 0) {
               throw new ICUException("Era data for " + eraIdxStr + " in era rule data for " + calType.getId() + " has only end rule.");
            }

            startDates[eraIdx] = MIN_ENCODED_START;
         }

         if (hasName) {
            if (eraIdx >= firstTentativeIdx) {
               throw new ICUException("Non-tentative era(" + eraIdx + ") must be placed before the first tentative era");
            }
         } else if (eraIdx < firstTentativeIdx) {
            firstTentativeIdx = eraIdx;
         }
      }

      return firstTentativeIdx < Integer.MAX_VALUE && !includeTentativeEra ? new EraRules(startDates, firstTentativeIdx) : new EraRules(startDates, numEras);
   }

   public int getNumberOfEras() {
      return this.numEras;
   }

   public int[] getStartDate(int eraIdx, int[] fillIn) {
      if (eraIdx >= 0 && eraIdx < this.numEras) {
         return decodeDate(this.startDates[eraIdx], fillIn);
      } else {
         throw new IllegalArgumentException("eraIdx is out of range");
      }
   }

   public int getStartYear(int eraIdx) {
      if (eraIdx >= 0 && eraIdx < this.numEras) {
         int[] fields = decodeDate(this.startDates[eraIdx], null);
         return fields[0];
      } else {
         throw new IllegalArgumentException("eraIdx is out of range");
      }
   }

   public int getEraIndex(int year, int month, int day) {
      if (month >= 1 && month <= 12 && day >= 1 && day <= 31) {
         int high = this.numEras;
         int low;
         if (compareEncodedDateWithYMD(this.startDates[this.getCurrentEraIndex()], year, month, day) <= 0) {
            low = this.getCurrentEraIndex();
         } else {
            low = 0;
         }

         while (low < high - 1) {
            int i = (low + high) / 2;
            if (compareEncodedDateWithYMD(this.startDates[i], year, month, day) <= 0) {
               low = i;
            } else {
               high = i;
            }
         }

         return low;
      } else {
         throw new IllegalArgumentException("Illegal date - year:" + year + "month:" + month + "day:" + day);
      }
   }

   public int getCurrentEraIndex() {
      return this.currentEra;
   }

   private void initCurrentEra() {
      long localMillis = System.currentTimeMillis();
      TimeZone zone = TimeZone.getDefault();
      localMillis += zone.getOffset(localMillis);
      int[] fields = Grego.timeToFields(localMillis, null);
      int currentEncodedDate = encodeDate(fields[0], fields[1] + 1, fields[2]);
      int eraIdx = this.numEras - 1;

      while (eraIdx > 0 && currentEncodedDate < this.startDates[eraIdx]) {
         eraIdx--;
      }

      this.currentEra = eraIdx;
   }

   private static boolean isSet(int startDate) {
      return startDate != 0;
   }

   private static boolean isValidRuleStartDate(int year, int month, int day) {
      return year >= -32768 && year <= 32767 && month >= 1 && month <= 12 && day >= 1 && day <= 31;
   }

   private static int encodeDate(int year, int month, int day) {
      return year << 16 | month << 8 | day;
   }

   private static int[] decodeDate(int encodedDate, int[] fillIn) {
      int year;
      int month;
      int day;
      if (encodedDate == MIN_ENCODED_START) {
         year = Integer.MIN_VALUE;
         month = 1;
         day = 1;
      } else {
         year = (encodedDate & -65536) >> 16;
         month = (encodedDate & 0xFF00) >> 8;
         day = encodedDate & 0xFF;
      }

      if (fillIn != null && fillIn.length >= 3) {
         fillIn[0] = year;
         fillIn[1] = month;
         fillIn[2] = day;
         return fillIn;
      } else {
         return new int[]{year, month, day};
      }
   }

   private static int compareEncodedDateWithYMD(int encoded, int year, int month, int day) {
      if (year < -32768) {
         if (encoded == MIN_ENCODED_START) {
            return year <= Integer.MIN_VALUE && month <= 1 && day <= 1 ? 0 : -1;
         } else {
            return 1;
         }
      } else if (year > 32767) {
         return -1;
      } else {
         int tmp = encodeDate(year, month, day);
         if (encoded < tmp) {
            return -1;
         } else {
            return encoded == tmp ? 0 : 1;
         }
      }
   }
}
