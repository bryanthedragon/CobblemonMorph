package com.cobblemon.mod.relocations.ibm.icu.util;

import com.cobblemon.mod.relocations.ibm.icu.impl.Grego;
import java.util.Date;

public class AnnualTimeZoneRule extends TimeZoneRule {
   private static final long serialVersionUID = -8870666707791230688L;
   public static final int MAX_YEAR = Integer.MAX_VALUE;
   private final DateTimeRule dateTimeRule;
   private final int startYear;
   private final int endYear;

   public AnnualTimeZoneRule(String name, int rawOffset, int dstSavings, DateTimeRule dateTimeRule, int startYear, int endYear) {
      super(name, rawOffset, dstSavings);
      this.dateTimeRule = dateTimeRule;
      this.startYear = startYear;
      this.endYear = endYear;
   }

   public DateTimeRule getRule() {
      return this.dateTimeRule;
   }

   public int getStartYear() {
      return this.startYear;
   }

   public int getEndYear() {
      return this.endYear;
   }

   public Date getStartInYear(int year, int prevRawOffset, int prevDSTSavings) {
      if (year >= this.startYear && year <= this.endYear) {
         int type = this.dateTimeRule.getDateRuleType();
         long ruleDay;
         if (type == 0) {
            ruleDay = Grego.fieldsToDay(year, this.dateTimeRule.getRuleMonth(), this.dateTimeRule.getRuleDayOfMonth());
         } else {
            boolean after = true;
            if (type == 1) {
               int weeks = this.dateTimeRule.getRuleWeekInMonth();
               if (weeks > 0) {
                  ruleDay = Grego.fieldsToDay(year, this.dateTimeRule.getRuleMonth(), 1);
                  ruleDay += 7 * (weeks - 1);
               } else {
                  after = false;
                  ruleDay = Grego.fieldsToDay(year, this.dateTimeRule.getRuleMonth(), Grego.monthLength(year, this.dateTimeRule.getRuleMonth()));
                  ruleDay += 7 * (weeks + 1);
               }
            } else {
               int month = this.dateTimeRule.getRuleMonth();
               int dom = this.dateTimeRule.getRuleDayOfMonth();
               if (type == 3) {
                  after = false;
                  if (month == 1 && dom == 29 && !Grego.isLeapYear(year)) {
                     dom--;
                  }
               }

               ruleDay = Grego.fieldsToDay(year, month, dom);
            }

            int dow = Grego.dayOfWeek(ruleDay);
            int delta = this.dateTimeRule.getRuleDayOfWeek() - dow;
            if (after) {
               delta = delta < 0 ? delta + 7 : delta;
            } else {
               delta = delta > 0 ? delta - 7 : delta;
            }

            ruleDay += delta;
         }

         long ruleTime = ruleDay * 86400000L + this.dateTimeRule.getRuleMillisInDay();
         if (this.dateTimeRule.getTimeRuleType() != 2) {
            ruleTime -= prevRawOffset;
         }

         if (this.dateTimeRule.getTimeRuleType() == 0) {
            ruleTime -= prevDSTSavings;
         }

         return new Date(ruleTime);
      } else {
         return null;
      }
   }

   @Override
   public Date getFirstStart(int prevRawOffset, int prevDSTSavings) {
      return this.getStartInYear(this.startYear, prevRawOffset, prevDSTSavings);
   }

   @Override
   public Date getFinalStart(int prevRawOffset, int prevDSTSavings) {
      return this.endYear == Integer.MAX_VALUE ? null : this.getStartInYear(this.endYear, prevRawOffset, prevDSTSavings);
   }

   @Override
   public Date getNextStart(long base, int prevRawOffset, int prevDSTSavings, boolean inclusive) {
      int[] fields = Grego.timeToFields(base, null);
      int year = fields[0];
      if (year < this.startYear) {
         return this.getFirstStart(prevRawOffset, prevDSTSavings);
      } else {
         Date d = this.getStartInYear(year, prevRawOffset, prevDSTSavings);
         if (d != null && (d.getTime() < base || !inclusive && d.getTime() == base)) {
            d = this.getStartInYear(year + 1, prevRawOffset, prevDSTSavings);
         }

         return d;
      }
   }

   @Override
   public Date getPreviousStart(long base, int prevRawOffset, int prevDSTSavings, boolean inclusive) {
      int[] fields = Grego.timeToFields(base, null);
      int year = fields[0];
      if (year > this.endYear) {
         return this.getFinalStart(prevRawOffset, prevDSTSavings);
      } else {
         Date d = this.getStartInYear(year, prevRawOffset, prevDSTSavings);
         if (d != null && (d.getTime() > base || !inclusive && d.getTime() == base)) {
            d = this.getStartInYear(year - 1, prevRawOffset, prevDSTSavings);
         }

         return d;
      }
   }

   @Override
   public boolean isEquivalentTo(TimeZoneRule other) {
      if (!(other instanceof AnnualTimeZoneRule)) {
         return false;
      } else {
         AnnualTimeZoneRule otherRule = (AnnualTimeZoneRule)other;
         return this.startYear == otherRule.startYear && this.endYear == otherRule.endYear && this.dateTimeRule.equals(otherRule.dateTimeRule)
            ? super.isEquivalentTo(other)
            : false;
      }
   }

   @Override
   public boolean isTransitionRule() {
      return true;
   }

   @Override
   public String toString() {
      StringBuilder buf = new StringBuilder();
      buf.append(super.toString());
      buf.append(", rule={" + this.dateTimeRule + "}");
      buf.append(", startYear=" + this.startYear);
      buf.append(", endYear=");
      if (this.endYear == Integer.MAX_VALUE) {
         buf.append("max");
      } else {
         buf.append(this.endYear);
      }

      return buf.toString();
   }
}
