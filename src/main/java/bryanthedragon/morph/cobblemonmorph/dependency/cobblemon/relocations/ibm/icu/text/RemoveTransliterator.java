
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.mod.relocations.ibm.icu.text;

import com.cobblemon.mod.relocations.ibm.icu.text.Replaceable;
import com.cobblemon.mod.relocations.ibm.icu.text.Transliterator;
import com.cobblemon.mod.relocations.ibm.icu.text.UnicodeSet;

class RemoveTransliterator
extends Transliterator {
    private static final String _ID = "Any-Remove";

    static void register() {
        Transliterator.registerFactory(_ID, new Transliterator.Factory(){

            @Override
            public Transliterator getInstance(String ID) {
                return new RemoveTransliterator();
            }
        });
        Transliterator.registerSpecialInverse("Remove", "Null", false);
    }

    public RemoveTransliterator() {
        super(_ID, null);
    }

    @Override
    protected void handleTransliterate(Replaceable text, Transliterator.Position index, boolean incremental) {
        text.replace(index.start, index.limit, "");
        int len = index.limit - index.start;
        index.contextLimit -= len;
        index.limit -= len;
    }

    @Override
    public void addSourceTargetSet(UnicodeSet inputFilter, UnicodeSet sourceSet, UnicodeSet targetSet) {
        UnicodeSet myFilter = this.getFilterAsUnicodeSet(inputFilter);
        sourceSet.addAll(myFilter);
    }
}

