
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.mod.relocations.ibm.icu.util;

import com.cobblemon.mod.relocations.ibm.icu.util.DateRule;
import com.cobblemon.mod.relocations.ibm.icu.util.GregorianCalendar;
import java.util.Date;

class EasterRule
implements DateRule {
    private int daysAfterEaster;
    private GregorianCalendar calendar = new GregorianCalendar();

    public EasterRule(int daysAfterEaster, boolean isOrthodox) {
        this.daysAfterEaster = daysAfterEaster;
        if (isOrthodox) {
            this.calendar.setGregorianChange(new Date(Long.MAX_VALUE));
        }
    }

    @Override
    public Date firstAfter(Date start2) {
        return this.doFirstBetween(start2, null);
    }

    @Override
    public Date firstBetween(Date start2, Date end2) {
        return this.doFirstBetween(start2, end2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public boolean isOn(Date date) {
        GregorianCalendar gregorianCalendar = this.calendar;
        synchronized (gregorianCalendar) {
            this.calendar.setTime(date);
            int dayOfYear = this.calendar.get(6);
            this.calendar.setTime(this.computeInYear(this.calendar.getTime(), this.calendar));
            return this.calendar.get(6) == dayOfYear;
        }
    }

    @Override
    public boolean isBetween(Date start2, Date end2) {
        return this.firstBetween(start2, end2) != null;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private Date doFirstBetween(Date start2, Date end2) {
        GregorianCalendar gregorianCalendar = this.calendar;
        synchronized (gregorianCalendar) {
            Date result = this.computeInYear(start2, this.calendar);
            if (result.before(start2)) {
                this.calendar.setTime(start2);
                this.calendar.get(1);
                this.calendar.add(1, 1);
                result = this.computeInYear(this.calendar.getTime(), this.calendar);
            }
            if (end2 != null && !result.before(end2)) {
                return null;
            }
            return result;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private Date computeInYear(Date date, GregorianCalendar cal) {
        if (cal == null) {
            cal = this.calendar;
        }
        GregorianCalendar gregorianCalendar = cal;
        synchronized (gregorianCalendar) {
            cal.setTime(date);
            int year = cal.get(1);
            int g = year % 19;
            int i = 0;
            int j = 0;
            if (cal.getTime().after(cal.getGregorianChange())) {
                int c = year / 100;
                int h = (c - c / 4 - (8 * c + 13) / 25 + 19 * g + 15) % 30;
                i = h - h / 28 * (1 - h / 28 * (29 / (h + 1)) * ((21 - g) / 11));
                j = (year + year / 4 + i + 2 - c + c / 4) % 7;
            } else {
                i = (19 * g + 15) % 30;
                j = (year + year / 4 + i) % 7;
            }
            int l = i - j;
            int m = 3 + (l + 40) / 44;
            int d = l + 28 - 31 * (m / 4);
            cal.clear();
            cal.set(0, 1);
            cal.set(1, year);
            cal.set(2, m - 1);
            cal.set(5, d);
            cal.getTime();
            cal.add(5, this.daysAfterEaster);
            return cal.getTime();
        }
    }
}

