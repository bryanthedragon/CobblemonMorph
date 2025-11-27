
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.relocations.ibm.icu.number;

import com.cobblemon.mod.relocations.ibm.icu.impl.FormattedStringBuilder;
import com.cobblemon.mod.relocations.ibm.icu.impl.FormattedValueStringBuilderImpl;
import com.cobblemon.mod.relocations.ibm.icu.impl.Utility;
import com.cobblemon.mod.relocations.ibm.icu.impl.number.DecimalQuantity;
import com.cobblemon.mod.relocations.ibm.icu.text.ConstrainedFieldPosition;
import com.cobblemon.mod.relocations.ibm.icu.text.FormattedValue;
import com.cobblemon.mod.relocations.ibm.icu.text.PluralRules;
import com.cobblemon.mod.relocations.ibm.icu.util.MeasureUnit;
import com.cobblemon.mod.relocations.ibm.icu.util.NounClass;
import com.cobblemon.mod.relocations.ibm.icu.util.UResourceTypeMismatchException;
import java.math.BigDecimal;
import java.text.AttributedCharacterIterator;

public class FormattedNumber
implements FormattedValue {
    final FormattedStringBuilder string;
    final DecimalQuantity fq;
    final MeasureUnit outputUnit;
    final String gender;

    FormattedNumber(FormattedStringBuilder nsb, DecimalQuantity fq, MeasureUnit outputUnit, String gender) {
        this.string = nsb;
        this.fq = fq;
        this.outputUnit = outputUnit;
        this.gender = gender;
    }

    @Override
    public String toString() {
        return this.string.toString();
    }

    @Override
    public int length() {
        return this.string.length();
    }

    @Override
    public char charAt(int index) {
        return this.string.charAt(index);
    }

    @Override
    public CharSequence subSequence(int start2, int end2) {
        return this.string.subString(start2, end2);
    }

    @Override
    public <A extends Appendable> A appendTo(A appendable) {
        return Utility.appendTo(this.string, appendable);
    }

    @Override
    public boolean nextPosition(ConstrainedFieldPosition cfpos) {
        return FormattedValueStringBuilderImpl.nextPosition(this.string, cfpos, null);
    }

    @Override
    public AttributedCharacterIterator toCharacterIterator() {
        return FormattedValueStringBuilderImpl.toCharacterIterator(this.string, null);
    }

    public BigDecimal toBigDecimal() {
        return this.fq.toBigDecimal();
    }

    public MeasureUnit getOutputUnit() {
        return this.outputUnit;
    }

    public NounClass getNounClass() {
        if (this.gender == null || this.gender.isEmpty()) {
            return NounClass.OTHER;
        }
        if (this.gender.equals("neuter")) {
            return NounClass.NEUTER;
        }
        if (this.gender.equals("feminine")) {
            return NounClass.FEMININE;
        }
        if (this.gender.equals("masculine")) {
            return NounClass.MASCULINE;
        }
        if (this.gender.equals("animate")) {
            return NounClass.ANIMATE;
        }
        if (this.gender.equals("inanimate")) {
            return NounClass.INANIMATE;
        }
        if (this.gender.equals("personal")) {
            return NounClass.PERSONAL;
        }
        if (this.gender.equals("common")) {
            return NounClass.COMMON;
        }
        throw new UResourceTypeMismatchException("there are noun classes that are not supported yet");
    }

    @Deprecated
    public String getGender() {
        if (this.gender == null) {
            return "";
        }
        return this.gender;
    }

    @Deprecated
    public PluralRules.IFixedDecimal getFixedDecimal() {
        return this.fq;
    }
}

