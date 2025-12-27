package com.oracle.truffle.js.nodes.intl;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.cast.JSToPrimitiveNode;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.Symbol;
import java.math.BigDecimal;

public abstract class ToIntlMathematicalValue extends JavaScriptBaseNode {
   final boolean partOfRange;
   private static final BigDecimal TWO = BigDecimal.valueOf(2L);
   private static final BigDecimal EIGHT = BigDecimal.valueOf(8L);
   private static final BigDecimal SIXTEEN = BigDecimal.valueOf(16L);

   protected ToIntlMathematicalValue(boolean partOfRange) {
      this.partOfRange = partOfRange;
   }

   public static ToIntlMathematicalValue create(boolean partOfRange) {
      return ToIntlMathematicalValueNodeGen.create(partOfRange);
   }

   public abstract Number executeNumber(Object value);

   @CompilerDirectives.TruffleBoundary
   @Specialization
   protected Number doDouble(double value) {
      return (Number)(this.partOfRange && Double.isFinite(value) && !JSRuntime.isNegativeZero(value) ? BigDecimal.valueOf(value) : value);
   }

   @CompilerDirectives.TruffleBoundary
   @Specialization
   protected Number doBigInt(BigInt value) {
      return new BigDecimal(value.bigIntegerValue());
   }

   @CompilerDirectives.TruffleBoundary
   @Specialization
   protected Number doString(TruffleString value) {
      return parseStringNumericLiteral(Strings.toJavaString(value));
   }

   @Specialization
   protected Number doBoolean(boolean value) {
      return value ? BigDecimal.ONE : BigDecimal.ZERO;
   }

   @Specialization(guards = "isUndefined(value)")
   protected Number doUndefined(Object value) {
      return Double.NaN;
   }

   @Specialization(guards = "isJSNull(value)")
   protected Number doNull(Object value) {
      return BigDecimal.ZERO;
   }

   @Specialization
   protected Number doSymbol(Symbol value) {
      throw Errors.createTypeErrorCannotConvertToNumber("a Symbol value", this);
   }

   @Specialization(replaces = {"doDouble", "doBigInt", "doString", "doBoolean", "doUndefined", "doNull", "doSymbol"})
   protected Number doGeneric(
      Object value, @Cached("createHintNumber()") JSToPrimitiveNode toPrimitiveNode, @Cached("create(partOfRange)") ToIntlMathematicalValue nestedToIntlMVNode
   ) {
      Object primValue = toPrimitiveNode.execute(value);
      return nestedToIntlMVNode.executeNumber(primValue);
   }

   private static Number parseStringNumericLiteral(String s) {
      String trimmed = s.trim();
      if (trimmed.isEmpty()) {
         return BigDecimal.ZERO;
      } else {
         try {
            Number result = parseStrNumericLiteral(trimmed);
            return (Number)(result == null ? Double.NaN : result);
         } catch (NumberFormatException | ArithmeticException var3) {
            return Double.NaN;
         }
      }
   }

   private static Number parseStrNumericLiteral(String s) {
      assert s.length() >= 1;

      char ch0 = s.charAt(0);
      switch (ch0) {
         case '+':
            return parseStrUnsignedDecimalLiteral(s.substring(1));
         case '-':
            Number o = parseStrUnsignedDecimalLiteral(s.substring(1));
            if (o instanceof BigDecimal) {
               if (((BigDecimal)o).signum() == 0) {
                  return -0.0;
               }

               return ((BigDecimal)o).negate();
            } else if (o instanceof Double) {
               return -(Double)o;
            } else {
               assert o == null;

               return null;
            }
         case '0':
            if (s.length() == 1) {
               return BigDecimal.ZERO;
            } else {
               char ch1 = s.charAt(1);
               switch (ch1) {
                  case 'B':
                  case 'b':
                     return parseBinaryIntegerLiteral(s.substring(2));
                  case 'O':
                  case 'o':
                     return parseOctalIntegerLiteral(s.substring(2));
                  case 'X':
                  case 'x':
                     return parseHexIntegerLiteral(s.substring(2));
               }
            }
         default:
            return parseStrUnsignedDecimalLiteral(s);
      }
   }

   private static BigDecimal parseBinaryIntegerLiteral(String s) {
      if (s.isEmpty()) {
         return null;
      } else {
         BigDecimal result = BigDecimal.ZERO;

         for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if ('0' > c || c > '1') {
               return null;
            }

            result = result.multiply(TWO).add(BigDecimal.valueOf((long)(c - '0')));
         }

         return result;
      }
   }

   private static BigDecimal parseOctalIntegerLiteral(String s) {
      if (s.isEmpty()) {
         return null;
      } else {
         BigDecimal result = BigDecimal.ZERO;

         for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if ('0' > c || c > '7') {
               return null;
            }

            result = result.multiply(EIGHT).add(BigDecimal.valueOf((long)(c - '0')));
         }

         return result;
      }
   }

   private static BigDecimal parseHexIntegerLiteral(String s) {
      if (s.isEmpty()) {
         return null;
      } else {
         BigDecimal result = BigDecimal.ZERO;

         for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            int digit;
            if ('0' <= c && c <= '9') {
               digit = c - '0';
            } else if ('a' <= c && c <= 'f') {
               digit = 10 + (c - 'a');
            } else {
               if ('A' > c || c > 'F') {
                  return null;
               }

               digit = 10 + (c - 'A');
            }

            result = result.multiply(SIXTEEN).add(BigDecimal.valueOf((long)digit));
         }

         return result;
      }
   }

   private static Number parseStrUnsignedDecimalLiteral(String s) {
      if (s.isEmpty()) {
         return null;
      } else if ("Infinity".equals(s)) {
         return Double.POSITIVE_INFINITY;
      } else {
         int dotIndex = s.indexOf(46);
         int exponentIndex = Math.max(s.indexOf(101, dotIndex + 1), s.indexOf(69, dotIndex + 1));
         int fractionalPartLength;
         String digits;
         if (dotIndex == -1) {
            fractionalPartLength = 0;
            if (exponentIndex == -1) {
               digits = s;
            } else {
               digits = s.substring(0, exponentIndex);
            }
         } else {
            String integerPart = s.substring(0, dotIndex);
            String fractionalPart;
            if (exponentIndex == -1) {
               fractionalPart = s.substring(dotIndex + 1);
            } else {
               fractionalPart = s.substring(dotIndex + 1, exponentIndex);
            }

            fractionalPartLength = fractionalPart.length();
            digits = integerPart + fractionalPart;
         }

         BigDecimal result = parseDecimalDigits(digits);
         if (result == null) {
            return null;
         } else {
            result = result.movePointLeft(fractionalPartLength);
            if (exponentIndex != -1) {
               String exponentPart = s.substring(exponentIndex + 1);
               int exponent = parseSignedInteger(exponentPart);
               if (exponent > 0) {
                  result = result.movePointRight(exponent);
               } else {
                  result = result.movePointLeft(exponent);
               }
            }

            return result;
         }
      }
   }

   private static BigDecimal parseDecimalDigits(String s) {
      if (s.isEmpty()) {
         return null;
      } else {
         BigDecimal result = BigDecimal.ZERO;

         for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if ('0' > c || c > '9') {
               return null;
            }

            result = result.multiply(BigDecimal.TEN).add(BigDecimal.valueOf((long)(c - '0')));
         }

         return result;
      }
   }

   private static int parseSignedInteger(String s) {
      return Integer.parseInt(s);
   }
}
