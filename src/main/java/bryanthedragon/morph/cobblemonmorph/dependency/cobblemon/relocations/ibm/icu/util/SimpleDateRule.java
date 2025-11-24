
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.mod.relocations.ibm.icu.util;

import com.cobblemon.mod.relocations.ibm.icu.util.Calendar;
import com.cobblemon.mod.relocations.ibm.icu.util.DateRule;
import com.cobblemon.mod.relocations.ibm.icu.util.GregorianCalendar;
import java.util.Date;

public class SimpleDateRule
implements DateRule {
    private Calendar calendar = new GregorianCalendar();
    private int month;
    private int dayOfMonth;
    private int dayOfWeek;

    public SimpleDateRule(int month, int dayOfMonth) {
        this.month = month;
        this.dayOfMonth = dayOfMonth;
        this.dayOfWeek = 0;
    }

    SimpleDateRule(int month, int dayOfMonth, Calendar cal) {
        this.month = month;
        this.dayOfMonth = dayOfMonth;
        this.dayOfWeek = 0;
        this.calendar = cal;
    }

    public SimpleDateRule(int month, int dayOfMonth, int dayOfWeek, boolean after2) {
        this.month = month;
        this.dayOfMonth = dayOfMonth;
        this.dayOfWeek = after2 ? dayOfWeek : -dayOfWeek;
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
        Calendar c;
        Calendar calendar = c = this.calendar;
        synchronized (calendar) {
            c.setTime(date);
            int dayOfYear = c.get(6);
            c.setTime(this.computeInYear(c.get(1), c));
            return c.get(6) == dayOfYear;
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
        Calendar c;
        Calendar calendar = c = this.calendar;
        synchronized (calendar) {
            c.setTime(start2);
            int year = c.get(1);
            int mon = c.get(2);
            if (mon > this.month) {
                ++year;
            }
            Date result = this.computeInYear(year, c);
            if (mon == this.month && result.before(start2)) {
                result = this.computeInYear(year + 1, c);
            }
            if (end2 != null && result.after(end2)) {
                return null;
            }
            return result;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private Date computeInYear(int year, Calendar c) {
        Calendar calendar = c;
        synchronized (calendar) {
            c.clear();
            c.set(0, c.getMaximum(0));
            c.set(1, year);
            c.set(2, this.month);
            c.set(5, this.dayOfMonth);
            if (this.dayOfWeek != 0) {
                c.setTime(c.getTime());
                int weekday = c.get(7);
                int delta = 0;
                delta = this.dayOfWeek > 0 ? (this.dayOfWeek - weekday + 7) % 7 : -((this.dayOfWeek + weekday + 7) % 7);
                c.add(5, delta);
            }
            return c.getTime();
        }
    }
}

