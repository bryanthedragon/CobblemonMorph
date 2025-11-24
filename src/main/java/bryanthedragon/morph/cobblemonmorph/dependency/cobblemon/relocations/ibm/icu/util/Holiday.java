
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.mod.relocations.ibm.icu.util;

import com.cobblemon.mod.relocations.ibm.icu.util.DateRule;
import com.cobblemon.mod.relocations.ibm.icu.util.ULocale;
import com.cobblemon.mod.relocations.ibm.icu.util.UResourceBundle;
import java.util.Date;
import java.util.Locale;
import java.util.MissingResourceException;

public abstract class Holiday
implements DateRule {
    private String name;
    private DateRule rule;
    private static Holiday[] noHolidays = new Holiday[0];

    public static Holiday[] getHolidays() {
        return Holiday.getHolidays(ULocale.getDefault(ULocale.Category.FORMAT));
    }

    public static Holiday[] getHolidays(Locale locale) {
        return Holiday.getHolidays(ULocale.forLocale(locale));
    }

    public static Holiday[] getHolidays(ULocale locale) {
        Holiday[] result = noHolidays;
        try {
            UResourceBundle bundle = UResourceBundle.getBundleInstance("com.cobblemon.mod.relocations.ibm.icu.impl.data.HolidayBundle", locale);
            result = (Holiday[])bundle.getObject("holidays");
        }
        catch (MissingResourceException missingResourceException) {
            // empty catch block
        }
        return result;
    }

    @Override
    public Date firstAfter(Date start2) {
        return this.rule.firstAfter(start2);
    }

    @Override
    public Date firstBetween(Date start2, Date end2) {
        return this.rule.firstBetween(start2, end2);
    }

    @Override
    public boolean isOn(Date date) {
        return this.rule.isOn(date);
    }

    @Override
    public boolean isBetween(Date start2, Date end2) {
        return this.rule.isBetween(start2, end2);
    }

    protected Holiday(String name, DateRule rule) {
        this.name = name;
        this.rule = rule;
    }

    public String getDisplayName() {
        return this.getDisplayName(ULocale.getDefault(ULocale.Category.DISPLAY));
    }

    public String getDisplayName(Locale locale) {
        return this.getDisplayName(ULocale.forLocale(locale));
    }

    public String getDisplayName(ULocale locale) {
        String dispName = this.name;
        try {
            UResourceBundle bundle = UResourceBundle.getBundleInstance("com.cobblemon.mod.relocations.ibm.icu.impl.data.HolidayBundle", locale);
            dispName = bundle.getString(this.name);
        }
        catch (MissingResourceException missingResourceException) {
            // empty catch block
        }
        return dispName;
    }

    public DateRule getRule() {
        return this.rule;
    }

    public void setRule(DateRule rule) {
        this.rule = rule;
    }
}

