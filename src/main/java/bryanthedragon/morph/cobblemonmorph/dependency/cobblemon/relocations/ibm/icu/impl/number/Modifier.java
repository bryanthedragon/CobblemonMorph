
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.relocations.ibm.icu.impl.number;

import com.cobblemon.mod.relocations.ibm.icu.impl.FormattedStringBuilder;
import com.cobblemon.mod.relocations.ibm.icu.impl.StandardPlural;
import com.cobblemon.mod.relocations.ibm.icu.impl.number.ModifierStore;
import java.text.Format;

public interface Modifier {
    public int apply(FormattedStringBuilder var1, int var2, int var3);

    public int getPrefixLength();

    public int getCodePointCount();

    public boolean isStrong();

    public boolean containsField(Format.Field var1);

    public Parameters getParameters();

    public boolean semanticallyEquivalent(Modifier var1);

    public static class Parameters {
        public ModifierStore obj;
        public Signum signum;
        public StandardPlural plural;
    }

    public static enum Signum {
        NEG,
        NEG_ZERO,
        POS_ZERO,
        POS;

        static final int COUNT;

        static {
            COUNT = Signum.values().length;
        }
    }
}

