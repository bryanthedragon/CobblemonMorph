
package com.oracle.truffle.js.nodes.cast;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.SlowPathException;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JSNodeUtil;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.cast.JSStringToNumberNodeGen;
import com.oracle.truffle.js.nodes.cast.JSTrimWhitespaceNode;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Strings;

public abstract class JSStringToNumberNode
extends JavaScriptBaseNode {
    static final int PREFIX_LENGTH = 2;
    static final int SAFE_HEX_DIGITS = 15;
    static final int SAFE_OCTAL_DIGITS = 19;
    static final int SAFE_BINARY_DIGITS = 55;
    static final int MAX_SAFE_INTEGER_LENGTH = 17;
    static final int SMALL_INT_LENGTH = 9;
    @Node.Child
    private TruffleString.ReadCharUTF16Node stringReadNode;
    @Node.Child
    private JSTrimWhitespaceNode trimWhitespaceNode;

    public final double executeString(TruffleString input) {
        if (this.trimWhitespaceNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.trimWhitespaceNode = this.insert(JSTrimWhitespaceNode.create());
        }
        return this.executeNoTrim(this.trimWhitespaceNode.executeString(input));
    }

    protected char charAt(TruffleString s, int i) {
        if (this.stringReadNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.stringReadNode = this.insert(TruffleString.ReadCharUTF16Node.create());
        }
        return Strings.charAt(this.stringReadNode, s, i);
    }

    protected abstract double executeNoTrim(TruffleString var1);

    public static JSStringToNumberNode create() {
        return JSStringToNumberNodeGen.create();
    }

    protected final boolean startsWithI(TruffleString input) {
        return Strings.length(input) >= Strings.length(Strings.INFINITY) && Strings.length(input) <= Strings.length(Strings.INFINITY) + 1 && (this.charAt(input, 0) == 'I' || this.charAt(input, 1) == 'I');
    }

    protected final boolean startsWithValidDouble(TruffleString input) {
        char firstChar = this.charAt(input, 0);
        if (JSRuntime.isAsciiDigit(firstChar) || firstChar == '-' || firstChar == '.' || firstChar == '+') {
            if (Strings.length(input) >= 2) {
                char secondChar = this.charAt(input, 1);
                return JSRuntime.isAsciiDigit(secondChar) || secondChar == '.' || secondChar == 'e' || secondChar == 'E';
            }
            return true;
        }
        return false;
    }

    protected final boolean startsWithValidInt(TruffleString input) {
        char firstChar = this.charAt(input, 0);
        return !(!JSRuntime.isAsciiDigit(firstChar) && firstChar != '-' && firstChar != '+' || Strings.length(input) >= 2 && !JSRuntime.isAsciiDigit(this.charAt(input, 1)));
    }

    protected final boolean allDigits(TruffleString input, int maxLength) {
        assert (Strings.length(input) <= maxLength);
        for (int i = 0; i < maxLength; ++i) {
            if (i >= Strings.length(input)) {
                return true;
            }
            if (JSRuntime.isAsciiDigit(this.charAt(input, i))) continue;
            return false;
        }
        return false;
    }

    protected final boolean isHex(TruffleString input) {
        return Strings.length(input) >= 2 && this.charAt(input, 0) == '0' && (this.charAt(input, 1) == 'x' || this.charAt(input, 1) == 'X');
    }

    protected final boolean isOctal(TruffleString input) {
        return Strings.length(input) >= 2 && this.charAt(input, 0) == '0' && (this.charAt(input, 1) == 'o' || this.charAt(input, 1) == 'O');
    }

    protected final boolean isBinary(TruffleString input) {
        return Strings.length(input) >= 2 && this.charAt(input, 0) == '0' && (this.charAt(input, 1) == 'b' || this.charAt(input, 1) == 'B');
    }

    @Specialization(guards={"stringLength(input) == 0"})
    protected double doLengthIsZero(TruffleString input) {
        return 0.0;
    }

    @Specialization(guards={"startsWithI(input)"})
    protected double doInfinity(TruffleString input, @Cached ConditionProfile endsWithInfinity, @Cached TruffleString.RegionEqualByteIndexNode regionEqualsNode) {
        if (endsWithInfinity.profile(Strings.endsWith(regionEqualsNode, input, Strings.INFINITY))) {
            return JSRuntime.identifyInfinity(this.charAt(input, 0), Strings.length(input));
        }
        return Double.NaN;
    }

    @Specialization(guards={"stringLength(input) > 0", "!startsWithI(input)", "!startsWithValidDouble(input)", "!isHex(input)", "!isOctal(input)", "!isBinary(input)"})
    protected double doNaN(TruffleString input) {
        return Double.NaN;
    }

    @Specialization(guards={"isHex(input)", "stringLength(input) <= SAFE_HEX_DIGITS"})
    @CompilerDirectives.TruffleBoundary
    protected double doHexSafe(TruffleString input) {
        return JSStringToNumberNode.safeIntegerToDouble(JSRuntime.parseSafeInteger(input, 2, Strings.length(input), 16));
    }

    @Specialization(guards={"isHex(input)", "stringLength(input) > SAFE_HEX_DIGITS"})
    @CompilerDirectives.TruffleBoundary
    protected double doHex(TruffleString input) {
        try {
            return Strings.parseBigInteger(Strings.lazySubstring(input, 2), 16).doubleValue();
        }
        catch (NumberFormatException ex) {
            return Double.NaN;
        }
    }

    @Specialization(guards={"isOctal(input)", "stringLength(input) <= SAFE_OCTAL_DIGITS"})
    @CompilerDirectives.TruffleBoundary
    protected double doOctalSafe(TruffleString input) {
        return JSStringToNumberNode.safeIntegerToDouble(JSRuntime.parseSafeInteger(input, 2, Strings.length(input), 8));
    }

    @Specialization(guards={"isOctal(input)", "stringLength(input) > SAFE_OCTAL_DIGITS"})
    @CompilerDirectives.TruffleBoundary
    protected double doOctal(TruffleString input) {
        try {
            return Strings.parseBigInteger(Strings.lazySubstring(input, 2), 8).doubleValue();
        }
        catch (NumberFormatException ex) {
            return Double.NaN;
        }
    }

    @Specialization(guards={"isBinary(input)", "stringLength(input) <= SAFE_BINARY_DIGITS"})
    @CompilerDirectives.TruffleBoundary
    protected double doBinarySafe(TruffleString input) {
        return JSStringToNumberNode.safeIntegerToDouble(JSRuntime.parseSafeInteger(input, 2, Strings.length(input), 2));
    }

    @Specialization(guards={"isBinary(input)", "stringLength(input) > SAFE_BINARY_DIGITS"})
    @CompilerDirectives.TruffleBoundary
    protected double doBinary(TruffleString input) {
        try {
            return Strings.parseBigInteger(Strings.lazySubstring(input, 2), 2).doubleValue();
        }
        catch (NumberFormatException ex) {
            return Double.NaN;
        }
    }

    @Specialization(guards={"stringLength(input) > 0", "stringLength(input) <= SMALL_INT_LENGTH", "allDigits(input, SMALL_INT_LENGTH)"})
    protected double doSmallPosInt(TruffleString input) {
        int result = 0;
        int len = Strings.length(input);
        for (int pos = 0; pos < len; ++pos) {
            char c = this.charAt(input, pos);
            assert (JSRuntime.isAsciiDigit(c));
            result *= 10;
            result += c - 48;
        }
        assert (result >= 0 && JSStringToNumberNode.checkLongResult(result, input));
        return result;
    }

    @Specialization(guards={"stringLength(input) > 0", "stringLength(input) <= MAX_SAFE_INTEGER_LENGTH", "startsWithValidInt(input)"}, rewriteOn={SlowPathException.class}, replaces={"doSmallPosInt"})
    protected double doInteger(TruffleString input) throws SlowPathException {
        long result = JSRuntime.parseSafeInteger(input);
        if (result == Long.MIN_VALUE) {
            throw JSNodeUtil.slowPathException();
        }
        assert (JSStringToNumberNode.checkLongResult(result, input));
        return result;
    }

    @CompilerDirectives.TruffleBoundary
    @Specialization(guards={"stringLength(input) > 0", "startsWithValidDouble(input)"}, replaces={"doInteger"})
    protected double doDouble(TruffleString input) {
        return JSRuntime.parseDoubleOrNaN(input);
    }

    private static double safeIntegerToDouble(long result) {
        if (result == Long.MIN_VALUE) {
            return Double.NaN;
        }
        assert (JSRuntime.isSafeInteger(result));
        return result;
    }

    @CompilerDirectives.TruffleBoundary
    private static boolean checkLongResult(long result, TruffleString input) {
        return Double.compare(result, JSRuntime.parseDoubleOrNaN(input)) == 0;
    }
}

