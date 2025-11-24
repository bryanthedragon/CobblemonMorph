
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.mod.relocations.ibm.icu.text;

import com.cobblemon.mod.relocations.ibm.icu.text.ConstrainedFieldPosition;
import java.text.AttributedCharacterIterator;

public interface FormattedValue
extends CharSequence {
    @Override
    public String toString();

    public <A extends Appendable> A appendTo(A var1);

    public boolean nextPosition(ConstrainedFieldPosition var1);

    public AttributedCharacterIterator toCharacterIterator();
}

