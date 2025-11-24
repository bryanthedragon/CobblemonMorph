
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.mod.relocations.ibm.icu.text;

import com.cobblemon.mod.relocations.ibm.icu.text.Replaceable;
import com.cobblemon.mod.relocations.ibm.icu.text.Transliterator;
import com.cobblemon.mod.relocations.ibm.icu.text.UnicodeSet;

class NullTransliterator
extends Transliterator {
    static final String SHORT_ID = "Null";
    static final String _ID = "Any-Null";

    public NullTransliterator() {
        super(_ID, null);
    }

    @Override
    protected void handleTransliterate(Replaceable text, Transliterator.Position offsets, boolean incremental) {
        offsets.start = offsets.limit;
    }

    @Override
    public void addSourceTargetSet(UnicodeSet inputFilter, UnicodeSet sourceSet, UnicodeSet targetSet) {
    }
}

