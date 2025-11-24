
package com.oracle.truffle.js.nodes.intl;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.cast.JSToPrimitiveNode;
import com.oracle.truffle.js.nodes.intl.ToIntlMathematicalValueNodeGen;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.Symbol;
import java.math.BigDecimal;

public abstract class ToIntlMathematicalValue
extends JavaScriptBaseNode {
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

    public abstract Number executeNumber(Object var1);

    @CompilerDirectives.TruffleBoundary
    @Specialization
    protected Number doDouble(double value2) {
        if (this.partOfRange && Double.isFinite(value2) && !JSRuntime.isNegativeZero(value2)) {
            return BigDecimal.valueOf(value2);
        }
        return value2;
    }

    @CompilerDirectives.TruffleBoundary
    @Specialization
    protected Number doBigInt(BigInt value2) {
        return new BigDecimal(value2.bigIntegerValue());
    }

    @CompilerDirectives.TruffleBoundary
    @Specialization
    protected Number doString(TruffleString value2) {
        return ToIntlMathematicalValue.parseStringNumericLiteral(Strings.toJavaString(value2));
    }

    @Specialization
    protected Number doBoolean(boolean value2) {
        return value2 ? BigDecimal.ONE : BigDecimal.ZERO;
    }

    @Specialization(guards={"isUndefined(value)"})
    protected Number doUndefined(Object value2) {
        return Double.NaN;
    }

    @Specialization(guards={"isJSNull(value)"})
    protected Number doNull(Object value2) {
        return BigDecimal.ZERO;
    }

    @Specialization
    protected Number doSymbol(Symbol value2) {
        throw Errors.createTypeErrorCannotConvertToNumber("a Symbol value", this);
    }

    @Specialization(replaces={"doDouble", "doBigInt", "doString", "doBoolean", "doUndefined", "doNull", "doSymbol"})
    protected Number doGeneric(Object value2, @Cached(value="createHintNumber()") JSToPrimitiveNode toPrimitiveNode, @Cached(value="create(partOfRange)") ToIntlMathematicalValue nestedToIntlMVNode) {
        Object primValue = toPrimitiveNode.execute(value2);
        return nestedToIntlMVNode.executeNumber(primValue);
    }

    private static Number parseStringNumericLiteral(String s) {
        String trimmed = s.trim();
        if (trimmed.isEmpty()) {
            return BigDecimal.ZERO;
        }
        try {
            Number result = ToIntlMathematicalValue.parseStrNumericLiteral(trimmed);
            return result == null ? (Number)Double.NaN : (Number)result;
        }
        catch (ArithmeticException | NumberFormatException ex) {
            return Double.NaN;
        }
    }

    private static Number parseStrNumericLiteral(String s) {
        assert (s.length() >= 1);
        char ch0 = s.charAt(0);
        switch (ch0) {
            case '+': {
                return ToIntlMathematicalValue.parseStrUnsignedDecimalLiteral(s.substring(1));
            }
            case '-': {
                Number o = ToIntlMathematicalValue.parseStrUnsignedDecimalLiteral(s.substring(1));
                if (o instanceof BigDecimal) {
                    if (((BigDecimal)o).signum() == 0) {
                        return -0.0;
                    }
                    return ((BigDecimal)o).negate();
                }
                if (o instanceof Double) {
                    return -((Double)o).doubleValue();
                }
                assert (o == null);
                return null;
            }
            case '0': {
                if (s.length() == 1) {
                    return BigDecimal.ZERO;
                }
                char ch1 = s.charAt(1);
                switch (ch1) {
                    case 'B': 
                    case 'b': {
                        return ToIntlMathematicalValue.parseBinaryIntegerLiteral(s.substring(2));
                    }
                    case 'O': 
                    case 'o': {
                        return ToIntlMathematicalValue.parseOctalIntegerLiteral(s.substring(2));
                    }
                    case 'X': 
                    case 'x': {
                        return ToIntlMathematicalValue.parseHexIntegerLiteral(s.substring(2));
                    }
                }
            }
        }
        return ToIntlMathematicalValue.parseStrUnsignedDecimalLiteral(s);
    }

    private static BigDecimal parseBinaryIntegerLiteral(String s) {
        if (s.isEmpty()) {
            return null;
        }
        BigDecimal result = BigDecimal.ZERO;
        for (int i = 0; i < s.length(); ++i) {
            char c = s.charAt(i);
            if ('0' > c || c > '1') {
                return null;
            }
            result = result.multiply(TWO).add(BigDecimal.valueOf(c - 48));
        }
        return result;
    }

    private static BigDecimal parseOctalIntegerLiteral(String s) {
        if (s.isEmpty()) {
            return null;
        }
        BigDecimal result = BigDecimal.ZERO;
        for (int i = 0; i < s.length(); ++i) {
            char c = s.charAt(i);
            if ('0' > c || c > '7') {
                return null;
            }
            result = result.multiply(EIGHT).add(BigDecimal.valueOf(c - 48));
        }
        return result;
    }

    private static BigDecimal parseHexIntegerLiteral(String s) {
        if (s.isEmpty()) {
            return null;
        }
        BigDecimal result = BigDecimal.ZERO;
        for (int i = 0; i < s.length(); ++i) {
            int digit;
            char c = s.charAt(i);
            if ('0' <= c && c <= '9') {
                digit = c - 48;
            } else if ('a' <= c && c <= 'f') {
                digit = 10 + (c - 97);
            } else if ('A' <= c && c <= 'F') {
                digit = 10 + (c - 65);
            } else {
                return null;
            }
            result = result.multiply(SIXTEEN).add(BigDecimal.valueOf(digit));
        }
        return result;
    }

    private static Number parseStrUnsignedDecimalLiteral(String s) {
        Object digits;
        int fractionalPartLength;
        if (s.isEmpty()) {
            return null;
        }
        if ("Infinity".equals(s)) {
            return Double.POSITIVE_INFINITY;
        }
        int dotIndex = s.indexOf(46);
        int exponentIndex = Math.max(s.indexOf(101, dotIndex + 1), s.indexOf(69, dotIndex + 1));
        if (dotIndex == -1) {
            fractionalPartLength = 0;
            digits = exponentIndex == -1 ? s : s.substring(0, exponentIndex);
        } else {
            String integerPart = s.substring(0, dotIndex);
            String fractionalPart = exponentIndex == -1 ? s.substring(dotIndex + 1) : s.substring(dotIndex + 1, exponentIndex);
            fractionalPartLength = fractionalPart.length();
            digits = integerPart + fractionalPart;
        }
        BigDecimal result = ToIntlMathematicalValue.parseDecimalDigits((String)digits);
        if (result == null) {
            return null;
        }
        result = result.movePointLeft(fractionalPartLength);
        if (exponentIndex != -1) {
            String exponentPart = s.substring(exponentIndex + 1);
            int exponent = ToIntlMathematicalValue.parseSignedInteger(exponentPart);
            result = exponent > 0 ? result.movePointRight(exponent) : result.movePointLeft(exponent);
        }
        return result;
    }

    private static BigDecimal parseDecimalDigits(String s) {
        if (s.isEmpty()) {
            return null;
        }
        BigDecimal result = BigDecimal.ZERO;
        for (int i = 0; i < s.length(); ++i) {
            char c = s.charAt(i);
            if ('0' > c || c > '9') {
                return null;
            }
            result = result.multiply(BigDecimal.TEN).add(BigDecimal.valueOf(c - 48));
        }
        return result;
    }

    private static int parseSignedInteger(String s) {
        return Integer.parseInt(s);
    }
}

