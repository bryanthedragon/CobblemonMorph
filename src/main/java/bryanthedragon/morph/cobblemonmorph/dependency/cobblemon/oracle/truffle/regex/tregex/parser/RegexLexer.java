
package com.oracle.truffle.regex.tregex.parser;

import com.oracle.truffle.api.ArrayUtils;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.regex.RegexFlags;
import com.oracle.truffle.regex.RegexSource;
import com.oracle.truffle.regex.RegexSyntaxException;
import com.oracle.truffle.regex.charset.CodePointSet;
import com.oracle.truffle.regex.charset.CodePointSetAccumulator;
import com.oracle.truffle.regex.charset.Constants;
import com.oracle.truffle.regex.charset.UnicodeProperties;
import com.oracle.truffle.regex.tregex.parser.CaseFoldTable;
import com.oracle.truffle.regex.tregex.parser.Token;
import com.oracle.truffle.regex.tregex.string.Encodings;
import com.oracle.truffle.regex.util.TBitSet;
import java.math.BigInteger;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public final class RegexLexer {
    private static final TBitSet PREDEFINED_CHAR_CLASSES = TBitSet.valueOf(68, 83, 87, 100, 115, 119);
    private static final TBitSet SYNTAX_CHARS = TBitSet.valueOf(36, 40, 41, 42, 43, 46, 47, 63, 91, 92, 93, 94, 123, 124, 125);
    private static final CodePointSet ID_START = UnicodeProperties.getProperty("ID_Start");
    private static final CodePointSet ID_CONTINUE = UnicodeProperties.getProperty("ID_Continue");
    private final RegexSource source;
    private final String pattern;
    private final RegexFlags flags;
    private final Encodings.Encoding encoding;
    private Token lastToken;
    private int curStartIndex = 0;
    private int index = 0;
    private int nGroups = 1;
    private boolean identifiedAllGroups = false;
    private Map<String, Integer> namedCaptureGroups = null;
    private final CodePointSetAccumulator curCharClass = new CodePointSetAccumulator();
    private final CodePointSetAccumulator charClassCaseFoldTmp = new CodePointSetAccumulator();
    private static final EnumSet<Token.Kind> QUANTIFIER_PREV = EnumSet.of(Token.Kind.charClass, Token.Kind.groupEnd, Token.Kind.backReference);

    public RegexLexer(RegexSource source, RegexFlags flags) {
        this.source = source;
        this.pattern = source.getPattern();
        this.flags = flags;
        this.encoding = source.getEncoding();
    }

    public boolean hasNext() {
        return !this.atEnd();
    }

    public Token next() throws RegexSyntaxException {
        this.curStartIndex = this.index;
        Token t = this.getNext();
        this.setSourceSection(t, this.curStartIndex, this.index);
        this.lastToken = t;
        return t;
    }

    public int getLastTokenPosition() {
        return this.curStartIndex;
    }

    private void setSourceSection(Token t, int startIndex, int endIndex) {
        if (this.source.getOptions().isDumpAutomataWithSourceSections()) {
            t.setSourceSection(this.source.getSource().createSection(startIndex + 1, endIndex - startIndex));
        }
    }

    private char curChar() {
        return this.pattern.charAt(this.index);
    }

    private char consumeChar() {
        char c = this.pattern.charAt(this.index);
        this.advance();
        return c;
    }

    private boolean findChars(char ... chars) {
        if (this.atEnd()) {
            return false;
        }
        int i = ArrayUtils.indexOf(this.pattern, this.index, this.pattern.length(), chars);
        if (i < 0) {
            this.index = this.pattern.length();
            return false;
        }
        this.index = i;
        return true;
    }

    private void advance() {
        this.advance(1);
    }

    private void retreat() {
        this.advance(-1);
    }

    private void advance(int len) {
        this.index += len;
    }

    private boolean lookahead(String match) {
        if (this.pattern.length() - this.index < match.length()) {
            return false;
        }
        return this.pattern.regionMatches(this.index, match, 0, match.length());
    }

    private boolean consumingLookahead(String match) {
        boolean matches2 = this.lookahead(match);
        if (matches2) {
            this.advance(match.length());
        }
        return matches2;
    }

    private boolean atEnd() {
        return this.index >= this.pattern.length();
    }

    public int numberOfCaptureGroups() throws RegexSyntaxException {
        if (!this.identifiedAllGroups) {
            this.identifyCaptureGroups();
            this.identifiedAllGroups = true;
        }
        return this.nGroups;
    }

    public Map<String, Integer> getNamedCaptureGroups() throws RegexSyntaxException {
        if (!this.identifiedAllGroups) {
            this.identifyCaptureGroups();
            this.identifiedAllGroups = true;
        }
        return this.namedCaptureGroups;
    }

    private boolean hasNamedCaptureGroups() throws RegexSyntaxException {
        return this.getNamedCaptureGroups() != null;
    }

    private void registerCaptureGroup() {
        if (!this.identifiedAllGroups) {
            ++this.nGroups;
        }
    }

    private void registerNamedCaptureGroup(String name) {
        if (!this.identifiedAllGroups) {
            if (this.namedCaptureGroups == null) {
                this.namedCaptureGroups = new HashMap<String, Integer>();
            }
            if (this.namedCaptureGroups.containsKey(name)) {
                throw this.syntaxError("Multiple named capture groups with the same name");
            }
            this.namedCaptureGroups.put(name, this.nGroups);
        }
        this.registerCaptureGroup();
    }

    private void identifyCaptureGroups() throws RegexSyntaxException {
        boolean insideCharClass = false;
        int restoreIndex = this.index;
        block6: while (this.findChars('\\', '[', ']', '(')) {
            switch (this.consumeChar()) {
                case '\\': {
                    this.advance();
                    continue block6;
                }
                case '[': {
                    insideCharClass = true;
                    continue block6;
                }
                case ']': {
                    insideCharClass = false;
                    continue block6;
                }
                case '(': {
                    if (insideCharClass) continue block6;
                    this.parseGroupBegin();
                    continue block6;
                }
            }
            throw CompilerDirectives.shouldNotReachHere();
        }
        this.index = restoreIndex;
    }

    private Token charClass(int codePoint) {
        if (this.flags.isIgnoreCase()) {
            this.curCharClass.clear();
            this.curCharClass.appendRange(codePoint, codePoint);
            return this.charClass(false);
        }
        return Token.createCharClass(CodePointSet.create(codePoint), true);
    }

    private Token charClass(CodePointSet codePointSet) {
        if (this.flags.isIgnoreCase()) {
            this.curCharClass.clear();
            this.curCharClass.addSet(codePointSet);
            return this.charClass(false);
        }
        return Token.createCharClass(codePointSet);
    }

    private Token charClass(boolean invert) {
        boolean wasSingleChar;
        boolean bl = wasSingleChar = !invert && this.curCharClass.matchesSingleChar();
        if (this.flags.isIgnoreCase()) {
            CaseFoldTable.CaseFoldingAlgorithm caseFolding = this.flags.isUnicode() ? CaseFoldTable.CaseFoldingAlgorithm.ECMAScriptUnicode : CaseFoldTable.CaseFoldingAlgorithm.ECMAScriptNonUnicode;
            CaseFoldTable.applyCaseFold(this.curCharClass, this.charClassCaseFoldTmp, caseFolding);
        }
        CodePointSet cps = this.curCharClass.toCodePointSet();
        return Token.createCharClass(invert ? cps.createInverse(this.encoding) : cps, wasSingleChar);
    }

    private Token getNext() throws RegexSyntaxException {
        char c = this.consumeChar();
        switch (c) {
            case '.': {
                return Token.createCharClass(this.flags.isDotAll() ? Constants.DOT_ALL : Constants.DOT);
            }
            case '^': {
                return Token.createCaret();
            }
            case '$': {
                return Token.createDollar();
            }
            case '*': 
            case '+': 
            case '?': 
            case '{': {
                return this.parseQuantifier(c);
            }
            case '}': {
                if (this.flags.isUnicode()) {
                    throw this.syntaxError("Unmatched '}'");
                }
                return this.charClass(c);
            }
            case '|': {
                return Token.createAlternation();
            }
            case '(': {
                return this.parseGroupBegin();
            }
            case ')': {
                return Token.createGroupEnd();
            }
            case '[': {
                return this.parseCharClass();
            }
            case ']': {
                if (this.flags.isUnicode()) {
                    throw this.syntaxError("Unmatched ']'");
                }
                return this.charClass(c);
            }
            case '\\': {
                return this.parseEscape();
            }
        }
        if (this.flags.isUnicode() && Character.isHighSurrogate(c)) {
            return this.charClass(this.finishSurrogatePair(c));
        }
        return this.charClass(c);
    }

    private Token parseEscape() throws RegexSyntaxException {
        if (this.atEnd()) {
            throw this.syntaxError("Ends with an unfinished escape sequence");
        }
        char c = this.consumeChar();
        if ('1' <= c && c <= '9') {
            int restoreIndex = this.index;
            int backRefNumber = this.parseInteger(c - 48);
            if (backRefNumber < this.numberOfCaptureGroups()) {
                return Token.createBackReference(backRefNumber);
            }
            if (this.flags.isUnicode()) {
                throw this.syntaxError("Missing capture group for backreference");
            }
            this.index = restoreIndex;
        }
        switch (c) {
            case 'k': {
                if (this.flags.isUnicode() || this.hasNamedCaptureGroups()) {
                    if (this.atEnd()) {
                        throw this.syntaxError("Ends with an unfinished escape sequence");
                    }
                    if (this.consumeChar() != '<') {
                        throw this.syntaxError("Missing group name in named capture group reference");
                    }
                    String groupName = this.parseGroupName();
                    if (this.namedCaptureGroups != null && this.namedCaptureGroups.containsKey(groupName)) {
                        return Token.createBackReference(this.namedCaptureGroups.get(groupName));
                    }
                    Map<String, Integer> allNamedCaptureGroups = this.getNamedCaptureGroups();
                    if (allNamedCaptureGroups != null && allNamedCaptureGroups.containsKey(groupName)) {
                        return Token.createBackReference(allNamedCaptureGroups.get(groupName));
                    }
                    throw this.syntaxError("Missing capture group for backreference");
                }
                return this.charClass(c);
            }
            case 'b': {
                return Token.createWordBoundary();
            }
            case 'B': {
                return Token.createNonWordBoundary();
            }
        }
        if (RegexLexer.isPredefCharClass(c)) {
            return Token.createCharClass(this.parsePredefCharClass(c));
        }
        if (this.flags.isUnicode() && (c == 'p' || c == 'P')) {
            return this.charClass(this.parseUnicodeCharacterProperty(c == 'P'));
        }
        return this.charClass(this.parseEscapeChar(c, false));
    }

    private Token parseGroupBegin() throws RegexSyntaxException {
        if (this.consumingLookahead("?=")) {
            return Token.createLookAheadAssertionBegin(false);
        }
        if (this.consumingLookahead("?!")) {
            return Token.createLookAheadAssertionBegin(true);
        }
        if (this.consumingLookahead("?<=")) {
            return Token.createLookBehindAssertionBegin(false);
        }
        if (this.consumingLookahead("?<!")) {
            return Token.createLookBehindAssertionBegin(true);
        }
        if (this.consumingLookahead("?:")) {
            return Token.createNonCaptureGroupBegin();
        }
        if (this.consumingLookahead("?<")) {
            String groupName = this.parseGroupName();
            this.registerNamedCaptureGroup(groupName);
            return Token.createCaptureGroupBegin();
        }
        this.registerCaptureGroup();
        return Token.createCaptureGroupBegin();
    }

    private int parseCodePointInGroupName() throws RegexSyntaxException {
        if (this.consumingLookahead("\\u")) {
            int unicodeEscape = this.parseUnicodeEscapeChar(true);
            if (unicodeEscape < 0) {
                throw this.syntaxError("Invalid Unicode escape");
            }
            return unicodeEscape;
        }
        if (this.atEnd()) {
            throw this.syntaxError("Unterminated group name");
        }
        if (this.consumingLookahead(">")) {
            return -1;
        }
        int c = this.consumeChar();
        return Character.isHighSurrogate((char)c) ? this.finishSurrogatePair((char)c) : c;
    }

    private String parseGroupName() throws RegexSyntaxException {
        StringBuilder groupName = new StringBuilder();
        int codePoint = this.parseCodePointInGroupName();
        if (codePoint == -1) {
            throw this.syntaxError("Empty named capture group name");
        }
        if (!ID_START.contains(codePoint) && codePoint != 36 && codePoint != 95) {
            throw this.syntaxError("Invalid character at start of group name");
        }
        groupName.appendCodePoint(codePoint);
        while ((codePoint = this.parseCodePointInGroupName()) != -1) {
            if (!ID_CONTINUE.contains(codePoint) && codePoint != 36 && codePoint != 8204 && codePoint != 8205) {
                throw this.syntaxError("Invalid character in group name");
            }
            groupName.appendCodePoint(codePoint);
        }
        return groupName.toString();
    }

    private Token parseQuantifier(char c) throws RegexSyntaxException {
        boolean greedy;
        int min2;
        int max2 = -1;
        if (c == '{') {
            int resetIndex = this.index;
            BigInteger literalMin = this.parseDecimal();
            if (literalMin.compareTo(BigInteger.ZERO) < 0) {
                return this.countedRepetitionSyntaxError(resetIndex);
            }
            int n = min2 = literalMin.compareTo(BigInteger.valueOf(Integer.MAX_VALUE)) <= 0 ? literalMin.intValue() : -1;
            if (this.consumingLookahead(",}")) {
                greedy = !this.consumingLookahead("?");
            } else if (this.consumingLookahead("}")) {
                max2 = min2;
                greedy = !this.consumingLookahead("?");
            } else {
                BigInteger literalMax;
                if (!this.consumingLookahead(",") || (literalMax = this.parseDecimal()).compareTo(BigInteger.ZERO) < 0 || !this.consumingLookahead("}")) {
                    return this.countedRepetitionSyntaxError(resetIndex);
                }
                max2 = literalMax.compareTo(BigInteger.valueOf(Integer.MAX_VALUE)) <= 0 ? literalMax.intValue() : -1;
                boolean bl = greedy = !this.consumingLookahead("?");
                if (literalMin.compareTo(literalMax) > 0) {
                    throw this.syntaxError("Numbers out of order in {} quantifier");
                }
            }
        } else {
            greedy = !this.consumingLookahead("?");
            int n = min2 = c == '+' ? 1 : 0;
            if (c == '?') {
                max2 = 1;
            }
        }
        if (this.lastToken == null) {
            throw this.syntaxError("Quantifier without target");
        }
        if (this.lastToken.kind == Token.Kind.quantifier) {
            throw this.syntaxError("Quantifier on quantifier");
        }
        if (!QUANTIFIER_PREV.contains((Object)this.lastToken.kind)) {
            throw this.syntaxError("Quantifier without target");
        }
        return Token.createQuantifier(min2, max2, greedy);
    }

    private Token countedRepetitionSyntaxError(int resetIndex) throws RegexSyntaxException {
        if (this.flags.isUnicode()) {
            throw this.syntaxError("Incomplete quantifier");
        }
        this.index = resetIndex;
        return this.charClass(123);
    }

    private Token parseCharClass() throws RegexSyntaxException {
        boolean invert = this.consumingLookahead("^");
        this.curCharClass.clear();
        while (!this.atEnd()) {
            char c = this.consumeChar();
            if (c == ']') {
                return this.charClass(invert);
            }
            this.parseCharClassRange(c);
        }
        throw this.syntaxError("Unterminated character class");
    }

    private CodePointSet parseCharClassAtomPredefCharClass(char c) throws RegexSyntaxException {
        if (c == '\\') {
            if (this.atEnd()) {
                throw this.syntaxError("Ends with an unfinished escape sequence");
            }
            if (this.isEscapeCharClass(this.curChar())) {
                return this.parseEscapeCharClass(this.consumeChar());
            }
        }
        return null;
    }

    private int parseCharClassAtomCodePoint(char c) throws RegexSyntaxException {
        if (c == '\\') {
            assert (!this.atEnd());
            assert (!this.isEscapeCharClass(this.curChar()));
            return this.parseEscapeChar(this.consumeChar(), true);
        }
        if (this.flags.isUnicode() && Character.isHighSurrogate(c)) {
            return this.finishSurrogatePair(c);
        }
        return c;
    }

    private void parseCharClassRange(char c) throws RegexSyntaxException {
        int firstAtomCP;
        CodePointSet firstAtomCC = this.parseCharClassAtomPredefCharClass(c);
        int n = firstAtomCP = firstAtomCC == null ? this.parseCharClassAtomCodePoint(c) : -1;
        if (this.consumingLookahead("-")) {
            if (this.atEnd() || this.lookahead("]")) {
                this.addCharClassAtom(firstAtomCC, firstAtomCP);
                this.curCharClass.addRange(45, 45);
            } else {
                int secondAtomCP;
                char nextC = this.consumeChar();
                CodePointSet secondAtomCC = this.parseCharClassAtomPredefCharClass(nextC);
                int n2 = secondAtomCP = secondAtomCC == null ? this.parseCharClassAtomCodePoint(nextC) : -1;
                if (firstAtomCC != null || secondAtomCC != null) {
                    if (this.flags.isUnicode()) {
                        throw this.syntaxError("Invalid character class");
                    }
                    this.addCharClassAtom(firstAtomCC, firstAtomCP);
                    this.addCharClassAtom(secondAtomCC, secondAtomCP);
                    this.curCharClass.addRange(45, 45);
                } else {
                    if (secondAtomCP < firstAtomCP) {
                        throw this.syntaxError("Range out of order in character class");
                    }
                    this.curCharClass.addRange(firstAtomCP, secondAtomCP);
                }
            }
        } else {
            this.addCharClassAtom(firstAtomCC, firstAtomCP);
        }
    }

    private void addCharClassAtom(CodePointSet preDefCharClass, int codePoint) {
        if (preDefCharClass != null) {
            this.curCharClass.addSet(preDefCharClass);
        } else {
            this.curCharClass.addRange(codePoint, codePoint);
        }
    }

    private CodePointSet parseEscapeCharClass(char c) throws RegexSyntaxException {
        if (RegexLexer.isPredefCharClass(c)) {
            return this.parsePredefCharClass(c);
        }
        if (this.flags.isUnicode() && (c == 'p' || c == 'P')) {
            return this.parseUnicodeCharacterProperty(c == 'P');
        }
        throw CompilerDirectives.shouldNotReachHere();
    }

    private CodePointSet parsePredefCharClass(char c) {
        switch (c) {
            case 's': {
                if (this.source.getOptions().isU180EWhitespace()) {
                    return Constants.LEGACY_WHITE_SPACE;
                }
                return Constants.WHITE_SPACE;
            }
            case 'S': {
                if (this.source.getOptions().isU180EWhitespace()) {
                    return Constants.LEGACY_NON_WHITE_SPACE;
                }
                return Constants.NON_WHITE_SPACE;
            }
            case 'd': {
                return Constants.DIGITS;
            }
            case 'D': {
                return Constants.NON_DIGITS;
            }
            case 'w': {
                if (this.flags.isUnicode() && this.flags.isIgnoreCase()) {
                    return Constants.WORD_CHARS_UNICODE_IGNORE_CASE;
                }
                return Constants.WORD_CHARS;
            }
            case 'W': {
                if (this.flags.isUnicode() && this.flags.isIgnoreCase()) {
                    return Constants.NON_WORD_CHARS_UNICODE_IGNORE_CASE;
                }
                return Constants.NON_WORD_CHARS;
            }
        }
        throw CompilerDirectives.shouldNotReachHere();
    }

    private CodePointSet parseUnicodeCharacterProperty(boolean invert) throws RegexSyntaxException {
        if (!this.consumingLookahead("{")) {
            throw this.syntaxError("Invalid Unicode property escape");
        }
        int namePos = this.index;
        while (!this.atEnd() && this.curChar() != '}') {
            this.advance();
        }
        if (!this.consumingLookahead("}")) {
            throw this.syntaxError("Ends with an unfinished Unicode property escape");
        }
        try {
            CodePointSet propertySet = this.encoding.getFullSet().createIntersection(UnicodeProperties.getProperty(this.pattern.substring(namePos, this.index - 1)), this.curCharClass.getTmp());
            return invert ? propertySet.createInverse(this.encoding) : propertySet;
        }
        catch (IllegalArgumentException e) {
            throw this.syntaxError(e.getMessage());
        }
    }

    private int parseUnicodeEscapeChar(boolean unicodeMode) throws RegexSyntaxException {
        if (unicodeMode && this.consumingLookahead("{")) {
            int value2 = this.parseHex(1, Integer.MAX_VALUE, 0x10FFFF, "Invalid Unicode escape");
            if (!this.consumingLookahead("}")) {
                throw this.syntaxError("Invalid Unicode escape");
            }
            return value2;
        }
        int value3 = this.parseHex(4, 4, 65535, "Invalid Unicode escape");
        if (unicodeMode && Character.isHighSurrogate((char)value3)) {
            int resetIndex = this.index;
            if (this.consumingLookahead("\\u") && !this.lookahead("{")) {
                char lead = (char)value3;
                char trail = (char)this.parseHex(4, 4, 65535, "Invalid Unicode escape");
                if (Character.isLowSurrogate(trail)) {
                    return Character.toCodePoint(lead, trail);
                }
                this.index = resetIndex;
            } else {
                this.index = resetIndex;
            }
        }
        return value3;
    }

    private int parseEscapeChar(char c, boolean inCharClass) throws RegexSyntaxException {
        if (inCharClass && c == 98) {
            return 8;
        }
        switch (c) {
            case 48: {
                if (this.flags.isUnicode() && !this.atEnd() && RegexLexer.isDecimal(this.curChar())) {
                    throw this.syntaxError("Invalid escape");
                }
                if (!this.flags.isUnicode() && !this.atEnd() && RegexLexer.isOctal(this.curChar())) {
                    return this.parseOctal(0);
                }
                return 0;
            }
            case 116: {
                return 9;
            }
            case 110: {
                return 10;
            }
            case 118: {
                return 11;
            }
            case 102: {
                return 12;
            }
            case 114: {
                return 13;
            }
            case 99: {
                if (this.atEnd()) {
                    this.retreat();
                    return this.escapeCharSyntaxError('\\', "Invalid control char escape");
                }
                char controlLetter = this.curChar();
                if (!this.flags.isUnicode() && (RegexLexer.isDecimal(controlLetter) || controlLetter == '_') && inCharClass) {
                    this.advance();
                    return controlLetter % 32;
                }
                if (!('a' <= controlLetter && controlLetter <= 'z' || 'A' <= controlLetter && controlLetter <= 'Z')) {
                    this.retreat();
                    return this.escapeCharSyntaxError('\\', "Invalid control char escape");
                }
                this.advance();
                return Character.toUpperCase(controlLetter) - 64;
            }
            case 117: {
                int unicodeEscape = this.parseUnicodeEscapeChar(this.flags.isUnicode());
                return unicodeEscape < 0 ? c : unicodeEscape;
            }
            case 120: {
                int value2 = this.parseHex(2, 2, 255, "Invalid escape");
                return value2 < 0 ? c : value2;
            }
            case 45: {
                if (!inCharClass) {
                    return this.escapeCharSyntaxError((char)c, "Invalid escape");
                }
                return c;
            }
        }
        if (!this.flags.isUnicode() && RegexLexer.isOctal((char)c)) {
            return this.parseOctal(c - 48);
        }
        if (!SYNTAX_CHARS.get(c)) {
            return this.escapeCharSyntaxError((char)c, "Invalid escape");
        }
        return c;
    }

    private int finishSurrogatePair(char c) {
        assert (Character.isHighSurrogate(c));
        if (!this.atEnd() && Character.isLowSurrogate(this.curChar())) {
            char lead = c;
            char trail = this.consumeChar();
            return Character.toCodePoint(lead, trail);
        }
        return c;
    }

    private char escapeCharSyntaxError(char c, String msg) throws RegexSyntaxException {
        if (this.flags.isUnicode()) {
            throw this.syntaxError(msg);
        }
        return c;
    }

    private BigInteger parseDecimal() {
        if (this.atEnd() || !RegexLexer.isDecimal(this.curChar())) {
            return BigInteger.valueOf(-1L);
        }
        return this.parseDecimal(BigInteger.ZERO);
    }

    private BigInteger parseDecimal(BigInteger firstDigit) {
        BigInteger ret = firstDigit;
        while (!this.atEnd() && RegexLexer.isDecimal(this.curChar())) {
            ret = ret.multiply(BigInteger.TEN);
            ret = ret.add(BigInteger.valueOf(this.consumeChar() - 48));
        }
        return ret;
    }

    private int parseInteger(int firstDigit) {
        int ret = firstDigit;
        int initialIndex = this.index;
        while (!this.atEnd() && RegexLexer.isDecimal(this.curChar())) {
            this.advance();
        }
        int terminalIndex = this.index;
        for (int i = initialIndex; i < terminalIndex; ++i) {
            int nextDigit = this.pattern.charAt(i) - 48;
            if (ret > 0xCCCCCCC) {
                return Integer.MAX_VALUE;
            }
            if ((ret *= 10) > Integer.MAX_VALUE - nextDigit) {
                return Integer.MAX_VALUE;
            }
            ret += nextDigit;
        }
        return ret;
    }

    private int parseOctal(int firstDigit) {
        int ret = firstDigit;
        for (int i = 0; !this.atEnd() && RegexLexer.isOctal(this.curChar()) && i < 2; ++i) {
            if (ret * 8 > 255) {
                return ret;
            }
            ret *= 8;
            ret += this.consumeChar() - 48;
        }
        return ret;
    }

    private int parseHex(int minDigits, int maxDigits, int maxValue, String errorMsg) throws RegexSyntaxException {
        int ret = 0;
        int initialIndex = this.index;
        for (int i = 0; i < maxDigits; ++i) {
            if (this.atEnd() || !RegexLexer.isHex(this.curChar())) {
                if (i >= minDigits) break;
                if (this.flags.isUnicode()) {
                    throw this.syntaxError(errorMsg);
                }
                this.index = initialIndex;
                return -1;
            }
            char c = this.consumeChar();
            ret *= 16;
            ret = c >= 'a' ? (ret += c - 87) : (c >= 'A' ? (ret += c - 55) : (ret += c - 48));
            if (ret <= maxValue) continue;
            throw this.syntaxError(errorMsg);
        }
        return ret;
    }

    private static boolean isDecimal(char c) {
        return '0' <= c && c <= '9';
    }

    private static boolean isOctal(char c) {
        return '0' <= c && c <= '7';
    }

    private static boolean isHex(char c) {
        return '0' <= c && c <= '9' || 'a' <= c && c <= 'f' || 'A' <= c && c <= 'F';
    }

    private static boolean isPredefCharClass(char c) {
        return PREDEFINED_CHAR_CLASSES.get(c);
    }

    private boolean isEscapeCharClass(char c) {
        return RegexLexer.isPredefCharClass(c) || this.flags.isUnicode() && (c == 'p' || c == 'P');
    }

    private RegexSyntaxException syntaxError(String msg) {
        return RegexSyntaxException.createPattern(this.source, msg, this.curStartIndex);
    }
}

