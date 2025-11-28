package com.cobblemon.mod.relocations.ibm.icu.impl.number;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;

public final class DecimalQuantity_DualStorageBCD extends DecimalQuantity_AbstractBCD {
   private byte[] bcdBytes;
   private long bcdLong = 0L;
   private boolean usingBytes = false;

   @Override
   public int maxRepresentableDigits() {
      return Integer.MAX_VALUE;
   }

   public DecimalQuantity_DualStorageBCD() {
      this.setBcdToZero();
      this.flags = 0;
   }

   public DecimalQuantity_DualStorageBCD(long input) {
      this.setToLong(input);
   }

   public DecimalQuantity_DualStorageBCD(int input) {
      this.setToInt(input);
   }

   public DecimalQuantity_DualStorageBCD(double input) {
      this.setToDouble(input);
   }

   public DecimalQuantity_DualStorageBCD(BigInteger input) {
      this.setToBigInteger(input);
   }

   public DecimalQuantity_DualStorageBCD(BigDecimal input) {
      this.setToBigDecimal(input);
   }

   public DecimalQuantity_DualStorageBCD(DecimalQuantity_DualStorageBCD other) {
      this.copyFrom(other);
   }

   public DecimalQuantity_DualStorageBCD(Number number) {
      if (number instanceof Long) {
         this.setToLong(number.longValue());
      } else if (number instanceof Integer) {
         this.setToInt(number.intValue());
      } else if (number instanceof Float) {
         this.setToDouble(number.doubleValue());
      } else if (number instanceof Double) {
         this.setToDouble(number.doubleValue());
      } else if (number instanceof BigInteger) {
         this.setToBigInteger((BigInteger)number);
      } else if (number instanceof BigDecimal) {
         this.setToBigDecimal((BigDecimal)number);
      } else {
         if (!(number instanceof com.cobblemon.mod.relocations.ibm.icu.math.BigDecimal)) {
            throw new IllegalArgumentException("Number is of an unsupported type: " + number.getClass().getName());
         }

         this.setToBigDecimal(((com.cobblemon.mod.relocations.ibm.icu.math.BigDecimal)number).toBigDecimal());
      }
   }

   @Override
   public DecimalQuantity createCopy() {
      return new DecimalQuantity_DualStorageBCD(this);
   }

   public static DecimalQuantity fromExponentString(String num) {
      if (!num.contains("e") && !num.contains("c") && !num.contains("E") && !num.contains("C")) {
         int numFracDigit = getVisibleFractionCount(num);
         DecimalQuantity_DualStorageBCD dq = new DecimalQuantity_DualStorageBCD(new BigDecimal(num));
         dq.setMinFraction(numFracDigit);
         return dq;
      } else {
         int ePos = num.lastIndexOf(101);
         if (ePos < 0) {
            ePos = num.lastIndexOf(99);
         }

         if (ePos < 0) {
            ePos = num.lastIndexOf(69);
         }

         if (ePos < 0) {
            ePos = num.lastIndexOf(67);
         }

         int expNumPos = ePos + 1;
         String exponentStr = num.substring(expNumPos);
         int exponent = Integer.parseInt(exponentStr);
         String fractionStr = num.substring(0, ePos);
         BigDecimal fraction = new BigDecimal(fractionStr);
         DecimalQuantity_DualStorageBCD dq = new DecimalQuantity_DualStorageBCD(fraction);
         int numFracDigit = getVisibleFractionCount(fractionStr);
         dq.setMinFraction(numFracDigit);
         dq.adjustExponent(exponent);
         return dq;
      }
   }

   private static int getVisibleFractionCount(String value) {
      int decimalPos = value.indexOf(46) + 1;
      return decimalPos == 0 ? 0 : value.length() - decimalPos;
   }

   @Override
   protected byte getDigitPos(int position) {
      if (this.usingBytes) {
         return position >= 0 && position < this.precision ? this.bcdBytes[position] : 0;
      } else {
         return position >= 0 && position < 16 ? (byte)(this.bcdLong >>> position * 4 & 15L) : 0;
      }
   }

   @Override
   protected void setDigitPos(int position, byte value) {
      assert position >= 0;

      if (this.usingBytes) {
         this.ensureCapacity(position + 1);
         this.bcdBytes[position] = value;
      } else if (position >= 16) {
         this.switchStorage();
         this.ensureCapacity(position + 1);
         this.bcdBytes[position] = value;
      } else {
         int shift = position * 4;
         this.bcdLong = this.bcdLong & ~(15L << shift) | (long)value << shift;
      }
   }

   @Override
   protected void shiftLeft(int numDigits) {
      if (!this.usingBytes && this.precision + numDigits > 16) {
         this.switchStorage();
      }

      if (this.usingBytes) {
         this.ensureCapacity(this.precision + numDigits);
         System.arraycopy(this.bcdBytes, 0, this.bcdBytes, numDigits, this.precision);
         Arrays.fill(this.bcdBytes, 0, numDigits, (byte)0);
      } else {
         this.bcdLong <<= numDigits * 4;
      }

      this.scale -= numDigits;
      this.precision += numDigits;
   }

   @Override
   protected void shiftRight(int numDigits) {
      if (this.usingBytes) {
         int i;
         for (i = 0; i < this.precision - numDigits; i++) {
            this.bcdBytes[i] = this.bcdBytes[i + numDigits];
         }

         while (i < this.precision) {
            this.bcdBytes[i] = 0;
            i++;
         }
      } else {
         this.bcdLong >>>= numDigits * 4;
      }

      this.scale += numDigits;
      this.precision -= numDigits;
   }

   @Override
   protected void popFromLeft(int numDigits) {
      assert numDigits <= this.precision;

      if (this.usingBytes) {
         for (int i = this.precision - 1; i >= this.precision - numDigits; i--) {
            this.bcdBytes[i] = 0;
         }
      } else {
         this.bcdLong = this.bcdLong & (1L << (this.precision - numDigits) * 4) - 1L;
      }

      this.precision -= numDigits;
   }

   @Override
   protected void setBcdToZero() {
      if (this.usingBytes) {
         this.bcdBytes = null;
         this.usingBytes = false;
      }

      this.bcdLong = 0L;
      this.scale = 0;
      this.precision = 0;
      this.isApproximate = false;
      this.origDouble = 0.0;
      this.origDelta = 0;
      this.exponent = 0;
   }

   @Override
   protected void readIntToBcd(int n) {
      assert n != 0;

      long result = 0L;

      int i;
      for (i = 16; n != 0; i--) {
         result = (result >>> 4) + (n % 10L << 60);
         n /= 10;
      }

      assert !this.usingBytes;

      this.bcdLong = result >>> i * 4;
      this.scale = 0;
      this.precision = 16 - i;
   }

   @Override
   protected void readLongToBcd(long n) {
      assert n != 0L;

      if (n >= 10000000000000000L) {
         this.ensureCapacity();

         int i;
         for (i = 0; n != 0L; i++) {
            this.bcdBytes[i] = (byte)(n % 10L);
            n /= 10L;
         }

         assert this.usingBytes;

         this.scale = 0;
         this.precision = i;
      } else {
         long result = 0L;

         int ix;
         for (ix = 16; n != 0L; ix--) {
            result = (result >>> 4) + (n % 10L << 60);
            n /= 10L;
         }

         assert ix >= 0;

         assert !this.usingBytes;

         this.bcdLong = result >>> ix * 4;
         this.scale = 0;
         this.precision = 16 - ix;
      }
   }

   @Override
   protected void readBigIntegerToBcd(BigInteger n) {
      assert n.signum() != 0;

      this.ensureCapacity();

      int i;
      for (i = 0; n.signum() != 0; i++) {
         BigInteger[] temp = n.divideAndRemainder(BigInteger.TEN);
         this.ensureCapacity(i + 1);
         this.bcdBytes[i] = temp[1].byteValue();
         n = temp[0];
      }

      this.scale = 0;
      this.precision = i;
   }

   @Override
   protected BigDecimal bcdToBigDecimal() {
      if (this.usingBytes) {
         BigDecimal result = new BigDecimal(this.toNumberString());
         if (this.isNegative()) {
            result = result.negate();
         }

         return result;
      } else {
         long tempLong = 0L;

         for (int shift = this.precision - 1; shift >= 0; shift--) {
            tempLong = tempLong * 10L + this.getDigitPos(shift);
         }

         BigDecimal result = BigDecimal.valueOf(tempLong);
         long newScale = result.scale() + this.scale + this.exponent;
         if (newScale <= -2147483648L) {
            result = BigDecimal.ZERO;
         } else {
            result = result.scaleByPowerOfTen(this.scale + this.exponent);
         }

         if (this.isNegative()) {
            result = result.negate();
         }

         return result;
      }
   }

   @Override
   protected void compact() {
      if (this.usingBytes) {
         int delta = 0;

         while (delta < this.precision && this.bcdBytes[delta] == 0) {
            delta++;
         }

         if (delta == this.precision) {
            this.setBcdToZero();
            return;
         }

         this.shiftRight(delta);
         int leading = this.precision - 1;

         while (leading >= 0 && this.bcdBytes[leading] == 0) {
            leading--;
         }

         this.precision = leading + 1;
         if (this.precision <= 16) {
            this.switchStorage();
         }
      } else {
         if (this.bcdLong == 0L) {
            this.setBcdToZero();
            return;
         }

         int delta = Long.numberOfTrailingZeros(this.bcdLong) / 4;
         this.bcdLong >>>= delta * 4;
         this.scale += delta;
         this.precision = 16 - Long.numberOfLeadingZeros(this.bcdLong) / 4;
      }
   }

   private void ensureCapacity() {
      this.ensureCapacity(40);
   }

   private void ensureCapacity(int capacity) {
      if (capacity != 0) {
         int oldCapacity = this.usingBytes ? this.bcdBytes.length : 0;
         if (!this.usingBytes) {
            this.bcdBytes = new byte[capacity];
         } else if (oldCapacity < capacity) {
            byte[] bcd1 = new byte[capacity * 2];
            System.arraycopy(this.bcdBytes, 0, bcd1, 0, oldCapacity);
            this.bcdBytes = bcd1;
         }

         this.usingBytes = true;
      }
   }

   private void switchStorage() {
      if (this.usingBytes) {
         this.bcdLong = 0L;

         for (int i = this.precision - 1; i >= 0; i--) {
            this.bcdLong <<= 4;
            this.bcdLong = this.bcdLong | this.bcdBytes[i];
         }

         this.bcdBytes = null;
         this.usingBytes = false;
      } else {
         this.ensureCapacity();

         for (int i = 0; i < this.precision; i++) {
            this.bcdBytes[i] = (byte)(this.bcdLong & 15L);
            this.bcdLong >>>= 4;
         }

         assert this.usingBytes;
      }
   }

   @Override
   protected void copyBcdFrom(DecimalQuantity _other) {
      DecimalQuantity_DualStorageBCD other = (DecimalQuantity_DualStorageBCD)_other;
      this.setBcdToZero();
      if (other.usingBytes) {
         this.ensureCapacity(other.precision);
         System.arraycopy(other.bcdBytes, 0, this.bcdBytes, 0, other.precision);
      } else {
         this.bcdLong = other.bcdLong;
      }
   }

   @Deprecated
   public String checkHealth() {
      if (this.usingBytes) {
         if (this.bcdLong != 0L) {
            return "Value in bcdLong but we are in byte mode";
         }

         if (this.precision == 0) {
            return "Zero precision but we are in byte mode";
         }

         if (this.precision > this.bcdBytes.length) {
            return "Precision exceeds length of byte array";
         }

         if (this.getDigitPos(this.precision - 1) == 0) {
            return "Most significant digit is zero in byte mode";
         }

         if (this.getDigitPos(0) == 0) {
            return "Least significant digit is zero in long mode";
         }

         for (int i = 0; i < this.precision; i++) {
            if (this.getDigitPos(i) >= 10) {
               return "Digit exceeding 10 in byte array";
            }

            if (this.getDigitPos(i) < 0) {
               return "Digit below 0 in byte array";
            }
         }

         for (int i = this.precision; i < this.bcdBytes.length; i++) {
            if (this.getDigitPos(i) != 0) {
               return "Nonzero digits outside of range in byte array";
            }
         }
      } else {
         if (this.bcdBytes != null) {
            for (int ix = 0; ix < this.bcdBytes.length; ix++) {
               if (this.bcdBytes[ix] != 0) {
                  return "Nonzero digits in byte array but we are in long mode";
               }
            }
         }

         if (this.precision == 0 && this.bcdLong != 0L) {
            return "Value in bcdLong even though precision is zero";
         }

         if (this.precision > 16) {
            return "Precision exceeds length of long";
         }

         if (this.precision != 0 && this.getDigitPos(this.precision - 1) == 0) {
            return "Most significant digit is zero in long mode";
         }

         if (this.precision != 0 && this.getDigitPos(0) == 0) {
            return "Least significant digit is zero in long mode";
         }

         for (int ixx = 0; ixx < this.precision; ixx++) {
            if (this.getDigitPos(ixx) >= 10) {
               return "Digit exceeding 10 in long";
            }

            if (this.getDigitPos(ixx) < 0) {
               return "Digit below 0 in long (?!)";
            }
         }

         for (int ixx = this.precision; ixx < 16; ixx++) {
            if (this.getDigitPos(ixx) != 0) {
               return "Nonzero digits outside of range in long";
            }
         }
      }

      return null;
   }

   @Deprecated
   public boolean isUsingBytes() {
      return this.usingBytes;
   }

   @Override
   public String toString() {
      return String.format(
         "<DecimalQuantity %d:%d %s %s%s>", this.lReqPos, this.rReqPos, this.usingBytes ? "bytes" : "long", this.isNegative() ? "-" : "", this.toNumberString()
      );
   }

   private String toNumberString() {
      StringBuilder sb = new StringBuilder();
      if (this.usingBytes) {
         if (this.precision == 0) {
            sb.append('0');
         }

         for (int i = this.precision - 1; i >= 0; i--) {
            sb.append(this.bcdBytes[i]);
         }
      } else {
         sb.append(Long.toHexString(this.bcdLong));
      }

      sb.append("E");
      sb.append(this.scale);
      return sb.toString();
   }
}
