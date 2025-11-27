
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.relocations.ibm.icu.text;

import com.cobblemon.mod.relocations.ibm.icu.text.NFRuleSet;
import com.cobblemon.mod.relocations.ibm.icu.text.RuleBasedNumberFormat;

interface RBNFPostProcessor {
    public void init(RuleBasedNumberFormat var1, String var2);

    public void process(StringBuilder var1, NFRuleSet var2);
}

