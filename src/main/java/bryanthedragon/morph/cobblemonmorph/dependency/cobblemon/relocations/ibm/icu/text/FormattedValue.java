package com.cobblemon.mod.relocations.ibm.icu.text;

import java.text.AttributedCharacterIterator;

public interface FormattedValue extends CharSequence {
   @Override
   String toString();

   <A extends Appendable> A appendTo(A var1);

   boolean nextPosition(ConstrainedFieldPosition var1);

   AttributedCharacterIterator toCharacterIterator();
}
