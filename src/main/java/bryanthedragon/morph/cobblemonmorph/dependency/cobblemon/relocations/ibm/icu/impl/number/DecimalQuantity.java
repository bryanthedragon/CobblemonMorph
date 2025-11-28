package com.cobblemon.mod.relocations.ibm.icu.impl.number;

import com.cobblemon.mod.relocations.ibm.icu.impl.StandardPlural;
import com.cobblemon.mod.relocations.ibm.icu.text.PluralRules;
import java.math.BigDecimal;
import java.math.MathContext;
import java.text.FieldPosition;

public interface DecimalQuantity extends PluralRules.IFixedDecimal {
   void setMinInteger(int var1);

   void setMinFraction(int var1);

   void applyMaxInteger(int var1);

   void roundToIncrement(BigDecimal var1, MathContext var2);

   void roundToNickel(int var1, MathContext var2);

   void roundToMagnitude(int var1, MathContext var2);

   void roundToInfinity();

   void multiplyBy(BigDecimal var1);

   void negate();

   void adjustMagnitude(int var1);

   int getMagnitude() throws ArithmeticException;

   int getExponent();

   void adjustExponent(int var1);

   void resetExponent();

   boolean isZeroish();

   boolean isNegative();

   Modifier.Signum signum();

   @Override
   boolean isInfinite();

   @Override
   boolean isNaN();

   double toDouble();

   BigDecimal toBigDecimal();

   long toLong(boolean var1);

   void setToBigDecimal(BigDecimal var1);

   int maxRepresentableDigits();

   StandardPlural getStandardPlural(PluralRules var1);

   byte getDigit(int var1);

   int getUpperDisplayMagnitude();

   int getLowerDisplayMagnitude();

   String toPlainString();

   String toExponentString();

   DecimalQuantity createCopy();

   void copyFrom(DecimalQuantity var1);

   long getPositionFingerprint();

   void populateUFieldPosition(FieldPosition var1);
}
