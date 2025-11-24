
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.mod.relocations.ibm.icu.impl.number;

import com.cobblemon.mod.relocations.ibm.icu.impl.number.AffixPatternProvider;
import com.cobblemon.mod.relocations.ibm.icu.impl.number.AffixUtils;
import com.cobblemon.mod.relocations.ibm.icu.impl.number.DecimalFormatProperties;
import com.cobblemon.mod.relocations.ibm.icu.impl.number.DecimalQuantity_DualStorageBCD;
import com.cobblemon.mod.relocations.ibm.icu.impl.number.Padder;

public class PatternStringParser {
    public static final int IGNORE_ROUNDING_NEVER = 0;
    public static final int IGNORE_ROUNDING_IF_CURRENCY = 1;
    public static final int IGNORE_ROUNDING_ALWAYS = 2;

    public static ParsedPatternInfo parseToPatternInfo(String patternString) {
        ParserState state = new ParserState(patternString);
        ParsedPatternInfo result = new ParsedPatternInfo(patternString);
        PatternStringParser.consumePattern(state, result);
        return result;
    }

    public static DecimalFormatProperties parseToProperties(String pattern, int ignoreRounding) {
        DecimalFormatProperties properties2 = new DecimalFormatProperties();
        PatternStringParser.parseToExistingPropertiesImpl(pattern, properties2, ignoreRounding);
        return properties2;
    }

    public static DecimalFormatProperties parseToProperties(String pattern) {
        return PatternStringParser.parseToProperties(pattern, 0);
    }

    public static void parseToExistingProperties(String pattern, DecimalFormatProperties properties2, int ignoreRounding) {
        PatternStringParser.parseToExistingPropertiesImpl(pattern, properties2, ignoreRounding);
    }

    public static void parseToExistingProperties(String pattern, DecimalFormatProperties properties2) {
        PatternStringParser.parseToExistingProperties(pattern, properties2, 0);
    }

    private static void consumePattern(ParserState state, ParsedPatternInfo result) {
        result.positive = new ParsedSubpatternInfo();
        PatternStringParser.consumeSubpattern(state, result.positive);
        if (state.peek() == 59) {
            state.next();
            if (state.peek() != -1) {
                result.negative = new ParsedSubpatternInfo();
                PatternStringParser.consumeSubpattern(state, result.negative);
            }
        }
        if (state.peek() != -1) {
            throw state.toParseException("Found unquoted special character");
        }
    }

    private static void consumeSubpattern(ParserState state, ParsedSubpatternInfo result) {
        PatternStringParser.consumePadding(state, result, Padder.PadPosition.BEFORE_PREFIX);
        result.prefixEndpoints = PatternStringParser.consumeAffix(state, result);
        PatternStringParser.consumePadding(state, result, Padder.PadPosition.AFTER_PREFIX);
        PatternStringParser.consumeFormat(state, result);
        PatternStringParser.consumeExponent(state, result);
        PatternStringParser.consumePadding(state, result, Padder.PadPosition.BEFORE_SUFFIX);
        result.suffixEndpoints = PatternStringParser.consumeAffix(state, result);
        PatternStringParser.consumePadding(state, result, Padder.PadPosition.AFTER_SUFFIX);
    }

    private static void consumePadding(ParserState state, ParsedSubpatternInfo result, Padder.PadPosition paddingLocation) {
        if (state.peek() != 42) {
            return;
        }
        if (result.paddingLocation != null) {
            throw state.toParseException("Cannot have multiple pad specifiers");
        }
        result.paddingLocation = paddingLocation;
        state.next();
        result.paddingEndpoints |= (long)state.offset;
        PatternStringParser.consumeLiteral(state);
        result.paddingEndpoints |= (long)state.offset << 32;
    }

    /*
     * Enabled aggressive block sorting
     */
    private static long consumeAffix(ParserState state, ParsedSubpatternInfo result) {
        long endpoints = state.offset;
        while (true) {
            switch (state.peek()) {
                case -1: 
                case 35: 
                case 42: 
                case 44: 
                case 46: 
                case 48: 
                case 49: 
                case 50: 
                case 51: 
                case 52: 
                case 53: 
                case 54: 
                case 55: 
                case 56: 
                case 57: 
                case 59: 
                case 64: {
                    return endpoints |= (long)state.offset << 32;
                }
                case 37: {
                    result.hasPercentSign = true;
                    break;
                }
                case 8240: {
                    result.hasPerMilleSign = true;
                    break;
                }
                case 164: {
                    result.hasCurrencySign = true;
                    break;
                }
                case 45: {
                    result.hasMinusSign = true;
                    break;
                }
                case 43: {
                    result.hasPlusSign = true;
                }
            }
            PatternStringParser.consumeLiteral(state);
        }
    }

    private static void consumeLiteral(ParserState state) {
        if (state.peek() == -1) {
            throw state.toParseException("Expected unquoted literal but found EOL");
        }
        if (state.peek() == 39) {
            state.next();
            while (state.peek() != 39) {
                if (state.peek() == -1) {
                    throw state.toParseException("Expected quoted literal but found EOL");
                }
                state.next();
            }
            state.next();
        } else {
            state.next();
        }
    }

    private static void consumeFormat(ParserState state, ParsedSubpatternInfo result) {
        PatternStringParser.consumeIntegerFormat(state, result);
        if (state.peek() == 46) {
            state.next();
            result.hasDecimal = true;
            ++result.widthExceptAffixes;
            PatternStringParser.consumeFractionFormat(state, result);
        } else if (state.peek() == 164) {
            switch (state.peek2()) {
                case 35: 
                case 48: 
                case 49: 
                case 50: 
                case 51: 
                case 52: 
                case 53: 
                case 54: 
                case 55: 
                case 56: 
                case 57: {
                    break;
                }
                default: {
                    return;
                }
            }
            result.hasCurrencySign = true;
            result.hasCurrencyDecimal = true;
            result.hasDecimal = true;
            ++result.widthExceptAffixes;
            state.next();
            PatternStringParser.consumeFractionFormat(state, result);
        }
    }

    private static void consumeIntegerFormat(ParserState state, ParsedSubpatternInfo result) {
        block6: while (true) {
            switch (state.peek()) {
                case 44: {
                    ++result.widthExceptAffixes;
                    result.groupingSizes <<= 16;
                    break;
                }
                case 35: {
                    if (result.integerNumerals > 0) {
                        throw state.toParseException("# cannot follow 0 before decimal point");
                    }
                    ++result.widthExceptAffixes;
                    ++result.groupingSizes;
                    if (result.integerAtSigns > 0) {
                        ++result.integerTrailingHashSigns;
                    } else {
                        ++result.integerLeadingHashSigns;
                    }
                    ++result.integerTotal;
                    break;
                }
                case 64: {
                    if (result.integerNumerals > 0) {
                        throw state.toParseException("Cannot mix 0 and @");
                    }
                    if (result.integerTrailingHashSigns > 0) {
                        throw state.toParseException("Cannot nest # inside of a run of @");
                    }
                    ++result.widthExceptAffixes;
                    ++result.groupingSizes;
                    ++result.integerAtSigns;
                    ++result.integerTotal;
                    break;
                }
                case 48: 
                case 49: 
                case 50: 
                case 51: 
                case 52: 
                case 53: 
                case 54: 
                case 55: 
                case 56: 
                case 57: {
                    if (result.integerAtSigns > 0) {
                        throw state.toParseException("Cannot mix @ and 0");
                    }
                    ++result.widthExceptAffixes;
                    ++result.groupingSizes;
                    ++result.integerNumerals;
                    ++result.integerTotal;
                    if (state.peek() != 48 && result.rounding == null) {
                        result.rounding = new DecimalQuantity_DualStorageBCD();
                    }
                    if (result.rounding == null) break;
                    result.rounding.appendDigit((byte)(state.peek() - 48), 0, true);
                    break;
                }
                default: {
                    break block6;
                }
            }
            state.next();
        }
        short grouping1 = (short)(result.groupingSizes & 0xFFFFL);
        short grouping2 = (short)(result.groupingSizes >>> 16 & 0xFFFFL);
        short grouping3 = (short)(result.groupingSizes >>> 32 & 0xFFFFL);
        if (grouping1 == 0 && grouping2 != -1) {
            throw state.toParseException("Trailing grouping separator is invalid");
        }
        if (grouping2 == 0 && grouping3 != -1) {
            throw state.toParseException("Grouping width of zero is invalid");
        }
    }

    private static void consumeFractionFormat(ParserState state, ParsedSubpatternInfo result) {
        int zeroCounter = 0;
        while (true) {
            switch (state.peek()) {
                case 35: {
                    ++result.widthExceptAffixes;
                    ++result.fractionHashSigns;
                    ++result.fractionTotal;
                    ++zeroCounter;
                    break;
                }
                case 48: 
                case 49: 
                case 50: 
                case 51: 
                case 52: 
                case 53: 
                case 54: 
                case 55: 
                case 56: 
                case 57: {
                    if (result.fractionHashSigns > 0) {
                        throw state.toParseException("0 cannot follow # after decimal point");
                    }
                    ++result.widthExceptAffixes;
                    ++result.fractionNumerals;
                    ++result.fractionTotal;
                    if (state.peek() == 48) {
                        ++zeroCounter;
                        break;
                    }
                    if (result.rounding == null) {
                        result.rounding = new DecimalQuantity_DualStorageBCD();
                    }
                    result.rounding.appendDigit((byte)(state.peek() - 48), zeroCounter, false);
                    zeroCounter = 0;
                    break;
                }
                default: {
                    return;
                }
            }
            state.next();
        }
    }

    private static void consumeExponent(ParserState state, ParsedSubpatternInfo result) {
        if (state.peek() != 69) {
            return;
        }
        if ((result.groupingSizes & 0xFFFF0000L) != 0xFFFF0000L) {
            throw state.toParseException("Cannot have grouping separator in scientific notation");
        }
        state.next();
        ++result.widthExceptAffixes;
        if (state.peek() == 43) {
            state.next();
            result.exponentHasPlusSign = true;
            ++result.widthExceptAffixes;
        }
        while (state.peek() == 48) {
            state.next();
            ++result.exponentZeros;
            ++result.widthExceptAffixes;
        }
    }

    private static void parseToExistingPropertiesImpl(String pattern, DecimalFormatProperties properties2, int ignoreRounding) {
        if (pattern == null || pattern.length() == 0) {
            properties2.clear();
            return;
        }
        ParsedPatternInfo patternInfo = PatternStringParser.parseToPatternInfo(pattern);
        PatternStringParser.patternInfoToProperties(properties2, patternInfo, ignoreRounding);
    }

    private static void patternInfoToProperties(DecimalFormatProperties properties2, ParsedPatternInfo patternInfo, int _ignoreRounding) {
        int minFrac;
        int minInt;
        boolean ignoreRounding;
        ParsedSubpatternInfo positive = patternInfo.positive;
        if (_ignoreRounding == 0) {
            ignoreRounding = false;
        } else if (_ignoreRounding == 1) {
            ignoreRounding = positive.hasCurrencySign;
        } else {
            assert (_ignoreRounding == 2);
            ignoreRounding = true;
        }
        short grouping1 = (short)(positive.groupingSizes & 0xFFFFL);
        short grouping2 = (short)(positive.groupingSizes >>> 16 & 0xFFFFL);
        short grouping3 = (short)(positive.groupingSizes >>> 32 & 0xFFFFL);
        if (grouping2 != -1) {
            properties2.setGroupingSize(grouping1);
            properties2.setGroupingUsed(true);
        } else {
            properties2.setGroupingSize(-1);
            properties2.setGroupingUsed(false);
        }
        if (grouping3 != -1) {
            properties2.setSecondaryGroupingSize(grouping2);
        } else {
            properties2.setSecondaryGroupingSize(-1);
        }
        if (positive.integerTotal == 0 && positive.fractionTotal > 0) {
            minInt = 0;
            minFrac = Math.max(1, positive.fractionNumerals);
        } else if (positive.integerNumerals == 0 && positive.fractionNumerals == 0) {
            minInt = 1;
            minFrac = 0;
        } else {
            minInt = positive.integerNumerals;
            minFrac = positive.fractionNumerals;
        }
        if (positive.integerAtSigns > 0) {
            properties2.setMinimumFractionDigits(-1);
            properties2.setMaximumFractionDigits(-1);
            properties2.setRoundingIncrement(null);
            properties2.setMinimumSignificantDigits(positive.integerAtSigns);
            properties2.setMaximumSignificantDigits(positive.integerAtSigns + positive.integerTrailingHashSigns);
        } else if (positive.rounding != null) {
            if (!ignoreRounding) {
                properties2.setMinimumFractionDigits(minFrac);
                properties2.setMaximumFractionDigits(positive.fractionTotal);
                properties2.setRoundingIncrement(positive.rounding.toBigDecimal().setScale(positive.fractionNumerals));
            } else {
                properties2.setMinimumFractionDigits(-1);
                properties2.setMaximumFractionDigits(-1);
                properties2.setRoundingIncrement(null);
            }
            properties2.setMinimumSignificantDigits(-1);
            properties2.setMaximumSignificantDigits(-1);
        } else {
            if (!ignoreRounding) {
                properties2.setMinimumFractionDigits(minFrac);
                properties2.setMaximumFractionDigits(positive.fractionTotal);
                properties2.setRoundingIncrement(null);
            } else {
                properties2.setMinimumFractionDigits(-1);
                properties2.setMaximumFractionDigits(-1);
                properties2.setRoundingIncrement(null);
            }
            properties2.setMinimumSignificantDigits(-1);
            properties2.setMaximumSignificantDigits(-1);
        }
        if (positive.hasDecimal && positive.fractionTotal == 0) {
            properties2.setDecimalSeparatorAlwaysShown(true);
        } else {
            properties2.setDecimalSeparatorAlwaysShown(false);
        }
        properties2.setCurrencyAsDecimal(positive.hasCurrencyDecimal);
        if (positive.exponentZeros > 0) {
            properties2.setExponentSignAlwaysShown(positive.exponentHasPlusSign);
            properties2.setMinimumExponentDigits(positive.exponentZeros);
            if (positive.integerAtSigns == 0) {
                properties2.setMinimumIntegerDigits(positive.integerNumerals);
                properties2.setMaximumIntegerDigits(positive.integerTotal);
            } else {
                properties2.setMinimumIntegerDigits(1);
                properties2.setMaximumIntegerDigits(-1);
            }
        } else {
            properties2.setExponentSignAlwaysShown(false);
            properties2.setMinimumExponentDigits(-1);
            properties2.setMinimumIntegerDigits(minInt);
            properties2.setMaximumIntegerDigits(-1);
        }
        String posPrefix = patternInfo.getString(256);
        String posSuffix = patternInfo.getString(0);
        if (positive.paddingLocation != null) {
            int paddingWidth = positive.widthExceptAffixes + AffixUtils.estimateLength(posPrefix) + AffixUtils.estimateLength(posSuffix);
            properties2.setFormatWidth(paddingWidth);
            String rawPaddingString = patternInfo.getString(1024);
            if (rawPaddingString.length() == 1) {
                properties2.setPadString(rawPaddingString);
            } else if (rawPaddingString.length() == 2) {
                if (rawPaddingString.charAt(0) == '\'') {
                    properties2.setPadString("'");
                } else {
                    properties2.setPadString(rawPaddingString);
                }
            } else {
                properties2.setPadString(rawPaddingString.substring(1, rawPaddingString.length() - 1));
            }
            assert (positive.paddingLocation != null);
            properties2.setPadPosition(positive.paddingLocation);
        } else {
            properties2.setFormatWidth(-1);
            properties2.setPadString(null);
            properties2.setPadPosition(null);
        }
        properties2.setPositivePrefixPattern(posPrefix);
        properties2.setPositiveSuffixPattern(posSuffix);
        if (patternInfo.negative != null) {
            properties2.setNegativePrefixPattern(patternInfo.getString(768));
            properties2.setNegativeSuffixPattern(patternInfo.getString(512));
        } else {
            properties2.setNegativePrefixPattern(null);
            properties2.setNegativeSuffixPattern(null);
        }
        if (positive.hasPercentSign) {
            properties2.setMagnitudeMultiplier(2);
        } else if (positive.hasPerMilleSign) {
            properties2.setMagnitudeMultiplier(3);
        } else {
            properties2.setMagnitudeMultiplier(0);
        }
    }

    private static class ParserState {
        final String pattern;
        int offset;

        ParserState(String pattern) {
            this.pattern = pattern;
            this.offset = 0;
        }

        int peek() {
            if (this.offset == this.pattern.length()) {
                return -1;
            }
            return this.pattern.codePointAt(this.offset);
        }

        int peek2() {
            if (this.offset == this.pattern.length()) {
                return -1;
            }
            int cp1 = this.pattern.codePointAt(this.offset);
            int offset2 = this.offset + Character.charCount(cp1);
            if (offset2 == this.pattern.length()) {
                return -1;
            }
            return this.pattern.codePointAt(offset2);
        }

        int next() {
            int codePoint = this.peek();
            this.offset += Character.charCount(codePoint);
            return codePoint;
        }

        IllegalArgumentException toParseException(String message) {
            StringBuilder sb = new StringBuilder();
            sb.append("Malformed pattern for ICU DecimalFormat: \"");
            sb.append(this.pattern);
            sb.append("\": ");
            sb.append(message);
            sb.append(" at position ");
            sb.append(this.offset);
            return new IllegalArgumentException(sb.toString());
        }
    }

    public static class ParsedSubpatternInfo {
        public long groupingSizes = 0xFFFFFFFF0000L;
        public int integerLeadingHashSigns = 0;
        public int integerTrailingHashSigns = 0;
        public int integerNumerals = 0;
        public int integerAtSigns = 0;
        public int integerTotal = 0;
        public int fractionNumerals = 0;
        public int fractionHashSigns = 0;
        public int fractionTotal = 0;
        public boolean hasDecimal = false;
        public int widthExceptAffixes = 0;
        public Padder.PadPosition paddingLocation = null;
        public DecimalQuantity_DualStorageBCD rounding = null;
        public boolean exponentHasPlusSign = false;
        public int exponentZeros = 0;
        public boolean hasPercentSign = false;
        public boolean hasPerMilleSign = false;
        public boolean hasCurrencySign = false;
        public boolean hasCurrencyDecimal = false;
        public boolean hasMinusSign = false;
        public boolean hasPlusSign = false;
        public long prefixEndpoints = 0L;
        public long suffixEndpoints = 0L;
        public long paddingEndpoints = 0L;
    }

    public static class ParsedPatternInfo
    implements AffixPatternProvider {
        public String pattern;
        public ParsedSubpatternInfo positive;
        public ParsedSubpatternInfo negative;

        private ParsedPatternInfo(String pattern) {
            this.pattern = pattern;
        }

        @Override
        public char charAt(int flags, int index) {
            long endpoints = this.getEndpoints(flags);
            int left = (int)(endpoints & 0xFFFFFFFFFFFFFFFFL);
            int right = (int)(endpoints >>> 32);
            if (index < 0 || index >= right - left) {
                throw new IndexOutOfBoundsException();
            }
            return this.pattern.charAt(left + index);
        }

        @Override
        public int length(int flags) {
            return ParsedPatternInfo.getLengthFromEndpoints(this.getEndpoints(flags));
        }

        public static int getLengthFromEndpoints(long endpoints) {
            int left = (int)(endpoints & 0xFFFFFFFFFFFFFFFFL);
            int right = (int)(endpoints >>> 32);
            return right - left;
        }

        @Override
        public String getString(int flags) {
            int right;
            long endpoints = this.getEndpoints(flags);
            int left = (int)(endpoints & 0xFFFFFFFFFFFFFFFFL);
            if (left == (right = (int)(endpoints >>> 32))) {
                return "";
            }
            return this.pattern.substring(left, right);
        }

        private long getEndpoints(int flags) {
            boolean padding;
            boolean prefix = (flags & 0x100) != 0;
            boolean isNegative = (flags & 0x200) != 0;
            boolean bl = padding = (flags & 0x400) != 0;
            if (isNegative && padding) {
                return this.negative.paddingEndpoints;
            }
            if (padding) {
                return this.positive.paddingEndpoints;
            }
            if (prefix && isNegative) {
                return this.negative.prefixEndpoints;
            }
            if (prefix) {
                return this.positive.prefixEndpoints;
            }
            if (isNegative) {
                return this.negative.suffixEndpoints;
            }
            return this.positive.suffixEndpoints;
        }

        @Override
        public boolean positiveHasPlusSign() {
            return this.positive.hasPlusSign;
        }

        @Override
        public boolean hasNegativeSubpattern() {
            return this.negative != null;
        }

        @Override
        public boolean negativeHasMinusSign() {
            return this.negative.hasMinusSign;
        }

        @Override
        public boolean hasCurrencySign() {
            return this.positive.hasCurrencySign || this.negative != null && this.negative.hasCurrencySign;
        }

        @Override
        public boolean containsSymbolType(int type) {
            return AffixUtils.containsType(this.pattern, type);
        }

        @Override
        public boolean hasBody() {
            return this.positive.integerTotal > 0;
        }

        @Override
        public boolean currencyAsDecimal() {
            return this.positive.hasCurrencyDecimal;
        }
    }
}

