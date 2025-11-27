
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.relocations.ibm.icu.text;

import com.cobblemon.mod.relocations.ibm.icu.text.Replaceable;
import com.cobblemon.mod.relocations.ibm.icu.text.Transliterator;
import com.cobblemon.mod.relocations.ibm.icu.text.UnicodeReplacer;
import com.cobblemon.mod.relocations.ibm.icu.text.UnicodeSet;

class FunctionReplacer
implements UnicodeReplacer {
    private Transliterator translit;
    private UnicodeReplacer replacer;

    public FunctionReplacer(Transliterator theTranslit, UnicodeReplacer theReplacer) {
        this.translit = theTranslit;
        this.replacer = theReplacer;
    }

    @Override
    public int replace(Replaceable text, int start2, int limit, int[] cursor) {
        int len = this.replacer.replace(text, start2, limit, cursor);
        limit = start2 + len;
        limit = this.translit.transliterate(text, start2, limit);
        return limit - start2;
    }

    @Override
    public String toReplacerPattern(boolean escapeUnprintable) {
        StringBuilder rule = new StringBuilder("&");
        rule.append(this.translit.getID());
        rule.append("( ");
        rule.append(this.replacer.toReplacerPattern(escapeUnprintable));
        rule.append(" )");
        return rule.toString();
    }

    @Override
    public void addReplacementSetTo(UnicodeSet toUnionTo) {
        toUnionTo.addAll(this.translit.getTargetSet());
    }
}

