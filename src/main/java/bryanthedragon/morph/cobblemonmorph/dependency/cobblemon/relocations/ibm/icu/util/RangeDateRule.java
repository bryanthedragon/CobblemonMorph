
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.mod.relocations.ibm.icu.util;

import com.cobblemon.mod.relocations.ibm.icu.util.DateRule;
import com.cobblemon.mod.relocations.ibm.icu.util.Range;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RangeDateRule
implements DateRule {
    List<Range> ranges = new ArrayList<Range>(2);

    public void add(DateRule rule) {
        this.add(new Date(Long.MIN_VALUE), rule);
    }

    public void add(Date start2, DateRule rule) {
        this.ranges.add(new Range(start2, rule));
    }

    @Override
    public Date firstAfter(Date start2) {
        int index = this.startIndex(start2);
        if (index == this.ranges.size()) {
            index = 0;
        }
        Date result = null;
        Range r = this.rangeAt(index);
        Range e = this.rangeAt(index + 1);
        if (r != null && r.rule != null) {
            result = e != null ? r.rule.firstBetween(start2, e.start) : r.rule.firstAfter(start2);
        }
        return result;
    }

    @Override
    public Date firstBetween(Date start2, Date end2) {
        if (end2 == null) {
            return this.firstAfter(start2);
        }
        int index = this.startIndex(start2);
        Date result = null;
        Range next = this.rangeAt(index);
        while (result == null && next != null && !next.start.after(end2)) {
            Range r = next;
            next = this.rangeAt(index + 1);
            if (r.rule == null) continue;
            Date e = next != null && !next.start.after(end2) ? next.start : end2;
            result = r.rule.firstBetween(start2, e);
        }
        return result;
    }

    @Override
    public boolean isOn(Date date) {
        Range r = this.rangeAt(this.startIndex(date));
        return r != null && r.rule != null && r.rule.isOn(date);
    }

    @Override
    public boolean isBetween(Date start2, Date end2) {
        return this.firstBetween(start2, end2) == null;
    }

    private int startIndex(Date start2) {
        int lastIndex = this.ranges.size();
        int i = 0;
        while (i < this.ranges.size()) {
            Range r = this.ranges.get(i);
            if (start2.before(r.start)) break;
            lastIndex = i++;
        }
        return lastIndex;
    }

    private Range rangeAt(int index) {
        return index < this.ranges.size() ? this.ranges.get(index) : null;
    }
}

