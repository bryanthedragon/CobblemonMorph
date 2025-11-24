
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.mod.relocations.ibm.icu.text;

import com.cobblemon.mod.relocations.ibm.icu.impl.SimpleFilteredSentenceBreakIterator;
import com.cobblemon.mod.relocations.ibm.icu.text.BreakIterator;
import com.cobblemon.mod.relocations.ibm.icu.util.ULocale;
import java.util.Locale;

public abstract class FilteredBreakIteratorBuilder {
    public static final FilteredBreakIteratorBuilder getInstance(Locale where) {
        return new SimpleFilteredSentenceBreakIterator.Builder(where);
    }

    public static final FilteredBreakIteratorBuilder getInstance(ULocale where) {
        return new SimpleFilteredSentenceBreakIterator.Builder(where);
    }

    public static final FilteredBreakIteratorBuilder getEmptyInstance() {
        return new SimpleFilteredSentenceBreakIterator.Builder();
    }

    public abstract boolean suppressBreakAfter(CharSequence var1);

    public abstract boolean unsuppressBreakAfter(CharSequence var1);

    public abstract BreakIterator wrapIteratorWithFilter(BreakIterator var1);

    @Deprecated
    protected FilteredBreakIteratorBuilder() {
    }
}

