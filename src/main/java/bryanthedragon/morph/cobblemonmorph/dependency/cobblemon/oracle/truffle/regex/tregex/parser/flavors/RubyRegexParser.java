
package com.oracle.truffle.regex.tregex.parser.flavors;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.regex.AbstractRegexObject;
import com.oracle.truffle.regex.RegexFlags;
import com.oracle.truffle.regex.RegexLanguage;
import com.oracle.truffle.regex.RegexSource;
import com.oracle.truffle.regex.RegexSyntaxException;
import com.oracle.truffle.regex.UnsupportedRegexException;
import com.oracle.truffle.regex.charset.CodePointSet;
import com.oracle.truffle.regex.charset.CodePointSetAccumulator;
import com.oracle.truffle.regex.charset.Range;
import com.oracle.truffle.regex.charset.UnicodeProperties;
import com.oracle.truffle.regex.errors.RbErrorMessages;
import com.oracle.truffle.regex.tregex.buffer.CompilationBuffer;
import com.oracle.truffle.regex.tregex.buffer.IntArrayBuffer;
import com.oracle.truffle.regex.tregex.parser.RegexASTBuilder;
import com.oracle.truffle.regex.tregex.parser.RegexParser;
import com.oracle.truffle.regex.tregex.parser.RegexValidator;
import com.oracle.truffle.regex.tregex.parser.Token;
import com.oracle.truffle.regex.tregex.parser.ast.RegexAST;
import com.oracle.truffle.regex.tregex.parser.flavors.RubyCaseFolding;
import com.oracle.truffle.regex.tregex.parser.flavors.RubyCaseFoldingData;
import com.oracle.truffle.regex.tregex.parser.flavors.RubyCaseUnfoldingTrie;
import com.oracle.truffle.regex.tregex.parser.flavors.RubyFlags;
import com.oracle.truffle.regex.tregex.parser.flavors.RubySubexpressionCalls;
import com.oracle.truffle.regex.tregex.string.Encodings;
import com.oracle.truffle.regex.util.TBitSet;
import java.lang.invoke.LambdaMetafactory;
import java.math.BigInteger;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Predicate;
import org.graalvm.collections.Pair;

public final class RubyRegexParser
implements RegexValidator,
RegexParser {
    private static final Map<Character, CodePointSet> UNICODE_CHAR_CLASSES;
    private static final Map<Character, CodePointSet> ASCII_CHAR_CLASSES;
    private static final Map<String, CodePointSet> UNICODE_POSIX_CHAR_CLASSES;
    private static final Map<String, CodePointSet> ASCII_POSIX_CHAR_CLASSES;
    private static final CodePointSet NEWLINE_RETURN;
    private static final CodePointSet UNICODE_LINE_BREAKS;
    private static final CodePointSet ASCII_LINE_BREAKS;
    private static final TBitSet WHITESPACE;
    private final RegexSource inSource;
    private final String inPattern;
    private final String inFlags;
    private boolean silent;
    private int position;
    private final RegexASTBuilder astBuilder;
    private boolean startsWithBeginningAnchor;
    private final RubyFlags globalFlags;
    private final Deque<RubyFlags> flagsStack;
    private int lookbehindDepth;
    private final Deque<Group> groupStack;
    private Map<String, List<Integer>> namedCaptureGroups;
    private int groupIndex;
    private int numberOfCaptureGroups;
    private boolean canHaveQuantifier;
    private boolean hasSubexpressionCalls;
    private CodePointSetAccumulator curCharClass = new CodePointSetAccumulator();
    private CodePointSetAccumulator fullyFoldableCharacters = new CodePointSetAccumulator();
    private CodePointSetAccumulator charClassTmp = new CodePointSetAccumulator();
    private final List<CodePointSetAccumulator> charClassPool = new ArrayList<CodePointSetAccumulator>();
    private final IntArrayBuffer codepointsBuffer = new IntArrayBuffer();

    @CompilerDirectives.TruffleBoundary
    private RubyRegexParser(RegexSource source, RegexASTBuilder astBuilder) throws RegexSyntaxException {
        this.inSource = source;
        this.inPattern = source.getPattern();
        this.inFlags = source.getFlags();
        this.position = 0;
        this.startsWithBeginningAnchor = false;
        this.globalFlags = new RubyFlags(this.inFlags);
        this.flagsStack = new LinkedList<RubyFlags>();
        this.lookbehindDepth = 0;
        this.groupStack = new ArrayDeque<Group>();
        this.namedCaptureGroups = null;
        this.groupIndex = 0;
        this.canHaveQuantifier = false;
        this.hasSubexpressionCalls = false;
        this.astBuilder = astBuilder;
        this.silent = astBuilder == null;
    }

    public static RegexValidator createValidator(RegexSource source) throws RegexSyntaxException {
        return new RubyRegexParser(source, null);
    }

    public static RegexParser createParser(RegexLanguage language, RegexSource source, CompilationBuffer compilationBuffer) throws RegexSyntaxException {
        return new RubyRegexParser(source, new RegexASTBuilder(language, source, RubyRegexParser.makeTRegexFlags(false), compilationBuffer));
    }

    @Override
    public AbstractRegexObject getNamedCaptureGroups() {
        return AbstractRegexObject.createNamedCaptureGroupMapListInt(this.namedCaptureGroups);
    }

    @Override
    public AbstractRegexObject getFlags() {
        return this.globalFlags;
    }

    private static RegexFlags makeTRegexFlags(boolean sticky) {
        return RegexFlags.builder().unicode(true).sticky(sticky).build();
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public void validate() throws RegexSyntaxException {
        this.run();
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public RegexAST parse() throws RegexSyntaxException, UnsupportedRegexException {
        this.astBuilder.pushRootGroup();
        this.run();
        RegexAST ast = this.astBuilder.popRootGroup();
        if (this.hasSubexpressionCalls) {
            RubySubexpressionCalls.expandNonRecursiveSubexpressionCalls(ast);
        }
        ast.setFlags(RubyRegexParser.makeTRegexFlags(this.globalFlags.isSticky() || this.startsWithBeginningAnchor));
        return ast;
    }

    private RubyFlags getLocalFlags() {
        return this.flagsStack.peek();
    }

    private void setLocalFlags(RubyFlags newLocalFlags) {
        this.flagsStack.pop();
        this.flagsStack.push(newLocalFlags);
    }

    private int curChar() {
        return this.inPattern.codePointAt(this.position);
    }

    private int consumeChar() {
        int c = this.curChar();
        this.advance();
        return c;
    }

    private String getMany(Predicate<Integer> pred) {
        StringBuilder out = new StringBuilder();
        while (!this.atEnd() && pred.test(this.curChar())) {
            out.appendCodePoint(this.consumeChar());
        }
        return out.toString();
    }

    private String getUpTo(int count, Predicate<Integer> pred) {
        StringBuilder out = new StringBuilder();
        for (int found = 0; found < count && !this.atEnd() && pred.test(this.curChar()); ++found) {
            out.appendCodePoint(this.consumeChar());
        }
        return out.toString();
    }

    private void advance() {
        if (this.atEnd()) {
            throw this.syntaxErrorAtEnd("unexpected end of pattern");
        }
        this.advance(1);
    }

    private void retreat() {
        this.advance(-1);
    }

    private void advance(int len) {
        this.position = this.inPattern.offsetByCodePoints(this.position, len);
    }

    private boolean match(String next) {
        if (this.inPattern.regionMatches(this.position, next, 0, next.length())) {
            this.position += next.length();
            return true;
        }
        return false;
    }

    private void mustMatch(String next) {
        assert ("}".equals(next) || ")".equals(next));
        if (!this.match(next)) {
            throw this.syntaxErrorHere("}".equals(next) ? "expected }" : "expected )");
        }
    }

    private boolean atEnd() {
        return this.position >= this.inPattern.length();
    }

    private void bailOut(String reason) throws UnsupportedRegexException {
        if (!this.silent) {
            throw new UnsupportedRegexException(reason);
        }
    }

    private void pushGroup() {
        if (!this.silent) {
            this.astBuilder.pushGroup();
        }
    }

    private void pushAtomicGroup() {
        if (!this.silent) {
            this.astBuilder.pushAtomicGroup();
        }
    }

    private void pushCaptureGroup() {
        if (!this.silent) {
            this.astBuilder.pushCaptureGroup();
        }
    }

    private void pushLookAheadAssertion(boolean negate) {
        if (!this.silent) {
            this.astBuilder.pushLookAheadAssertion(negate);
        }
    }

    private void pushLookBehindAssertion(boolean negate) {
        if (!this.silent) {
            this.astBuilder.pushLookBehindAssertion(negate);
        }
    }

    private void popGroup() {
        if (!this.silent) {
            this.astBuilder.popGroup();
        }
    }

    private void nextSequence() {
        if (!this.silent) {
            this.astBuilder.nextSequence();
        }
    }

    private void addCharClass(CodePointSet charSet) {
        if (!this.silent) {
            this.astBuilder.addCharClass(charSet);
        }
    }

    private void addChar(int codepoint) {
        if (!this.silent) {
            this.astBuilder.addCharClass(CodePointSet.create(codepoint), true);
        }
    }

    private void addBackReference(int groupNumber) {
        if (!this.silent) {
            this.astBuilder.addBackReference(groupNumber);
        }
    }

    private void addSubexpressionCall(int groupNumber) {
        if (!this.silent) {
            this.astBuilder.addSubexpressionCall(groupNumber);
        }
    }

    private void addCaret() {
        if (!this.silent) {
            this.astBuilder.addCaret();
        }
    }

    private void addDollar() {
        if (!this.silent) {
            this.astBuilder.addDollar();
        }
    }

    private void addQuantifier(Token.Quantifier quantifier) {
        if (!this.silent) {
            this.astBuilder.addQuantifier(quantifier);
        }
    }

    private void addDeadNode() {
        if (!this.silent) {
            this.astBuilder.addDeadNode();
        }
    }

    private void wrapCurTermInAtomicGroup() {
        if (!this.silent) {
            this.astBuilder.wrapCurTermInAtomicGroup();
        }
    }

    private RegexSyntaxException syntaxErrorAtEnd(String message) {
        return RegexSyntaxException.createPattern(this.inSource, message, this.inPattern.length() - 1);
    }

    private RegexSyntaxException syntaxErrorHere(String message) {
        return RegexSyntaxException.createPattern(this.inSource, message, this.position);
    }

    private RegexSyntaxException syntaxErrorAt(String message, int pos) {
        return RegexSyntaxException.createPattern(this.inSource, message, pos);
    }

    private static boolean isOctDigit(int c) {
        return c >= 48 && c <= 55;
    }

    private static boolean isDecDigit(int c) {
        return c >= 48 && c <= 57;
    }

    private static boolean isHexDigit(int c) {
        return c >= 48 && c <= 57 || c >= 97 && c <= 102 || c >= 65 && c <= 70;
    }

    static boolean isAscii(int c) {
        return c < 128;
    }

    /*
     * Unable to fully structure code
     */
    private void scanForCaptureGroups() {
        restorePosition = this.position;
        this.numberOfCaptureGroups = 0;
        charClassDepth = 0;
        block10: while (!this.atEnd()) {
            switch (this.consumeChar()) {
                case 92: {
                    switch (this.curChar()) {
                        case 103: 
                        case 107: {
                            c = this.consumeChar();
                            if (!this.match("<")) continue block10;
                            this.parseGroupReference('>', true, true, c == 107, false);
                            continue block10;
                        }
                    }
lbl14:
                    // 2 sources

                    do {
                        if (this.match("c")) ** GOTO lbl14
                    } while (this.match("C-") || this.match("M-"));
                    this.advance();
                    continue block10;
                }
                case 91: {
                    ++charClassDepth;
                    if (this.match("]")) continue block10;
                    this.match("^]");
                    continue block10;
                }
                case 93: {
                    --charClassDepth;
                    continue block10;
                }
                case 40: {
                    if (charClassDepth != 0) continue block10;
                    if (this.match("?")) {
                        if (this.match("<")) {
                            if (this.curChar() == 61 || this.curChar() == 33) continue block10;
                            groupName = this.parseGroupName('>');
                            if (this.namedCaptureGroups == null) {
                                this.namedCaptureGroups = new HashMap<String, List<Integer>>();
                                this.numberOfCaptureGroups = 0;
                            }
                            ++this.numberOfCaptureGroups;
                            this.namedCaptureGroups.computeIfAbsent(groupName, (Function<String, List>)LambdaMetafactory.metafactory(null, null, null, (Ljava/lang/Object;)Ljava/lang/Object;, lambda$scanForCaptureGroups$0(java.lang.String ), (Ljava/lang/String;)Ljava/util/List;)());
                            this.namedCaptureGroups.get(groupName).add(this.numberOfCaptureGroups);
                            continue block10;
                        }
                        if (!this.match("(")) continue block10;
                        if (this.match("<")) {
                            this.parseGroupReference('>', true, true, true, false);
                            continue block10;
                        }
                        if (this.match("'")) {
                            this.parseGroupReference('\'', true, true, true, false);
                            continue block10;
                        }
                        if (!RubyRegexParser.isDecDigit(this.curChar())) continue block10;
                        this.parseGroupReference(')', true, false, true, false);
                        continue block10;
                    }
                    if (this.namedCaptureGroups != null) continue block10;
                    ++this.numberOfCaptureGroups;
                    continue block10;
                }
                case 35: {
                    if (charClassDepth != 0 || !this.globalFlags.isExtended()) continue block10;
                    endOfLine = this.inPattern.indexOf(10, this.position);
                    if (endOfLine >= 0) {
                        this.position = endOfLine + 1;
                        continue block10;
                    }
                    this.position = this.inPattern.length();
                    continue block10;
                }
            }
        }
        this.position = restorePosition;
    }

    private int numberOfCaptureGroups() {
        return this.numberOfCaptureGroups;
    }

    private boolean containsNamedCaptureGroups() {
        return this.namedCaptureGroups != null;
    }

    private void run() {
        this.scanForCaptureGroups();
        this.flagsStack.push(this.globalFlags);
        this.disjunction(true);
        this.flagsStack.pop();
        if (!this.atEnd()) {
            assert (this.curChar() == 41);
            throw this.syntaxErrorHere("unbalanced parenthesis");
        }
    }

    private void disjunction(boolean toplevel) {
        boolean beginningAnchor = this.beginningAnchor();
        if (beginningAnchor && !toplevel) {
            this.bailOut("\\G anchor is only supported in top-level alternatives");
        }
        while (true) {
            this.alternative();
            if (!this.match("|")) break;
            this.nextSequence();
            this.canHaveQuantifier = false;
            if (this.beginningAnchor() == beginningAnchor) continue;
            this.bailOut("\\G anchor is only supported when used at the start of all top-level alternatives");
        }
        if (beginningAnchor) {
            assert (toplevel);
            this.startsWithBeginningAnchor = true;
        }
    }

    private void disjunction() {
        this.disjunction(false);
    }

    private boolean beginningAnchor() {
        Quantifier quantifier;
        if (!this.match("\\G")) {
            return false;
        }
        while (!(this.atEnd() || this.curChar() != 42 && this.curChar() != 43 && this.curChar() != 63 && this.curChar() != 123 || (quantifier = this.parseQuantifier(this.consumeChar())) == null)) {
            if (quantifier.lower != 0) continue;
            return false;
        }
        return true;
    }

    private void alternative() {
        this.flagsStack.push(this.getLocalFlags());
        while (!this.atEnd() && this.curChar() != 124 && this.curChar() != 41) {
            this.term();
        }
        this.flagsStack.pop();
    }

    private void term() {
        int ch = this.consumeChar();
        if (this.getLocalFlags().isExtended()) {
            if (WHITESPACE.get(ch)) {
                return;
            }
            if (ch == 35) {
                this.comment();
                return;
            }
        }
        switch (ch) {
            case 92: {
                this.escape();
                break;
            }
            case 91: {
                this.characterClass();
                break;
            }
            case 42: 
            case 43: 
            case 63: 
            case 123: {
                this.quantifier(ch);
                break;
            }
            case 46: {
                this.dot();
                break;
            }
            case 40: {
                this.parens();
                break;
            }
            case 94: {
                this.caret();
                break;
            }
            case 36: {
                this.dollar();
                break;
            }
            default: {
                this.string(ch);
            }
        }
    }

    private void dot() {
        if (this.getLocalFlags().isMultiline()) {
            this.addCharClass(this.inSource.getEncoding().getFullSet());
        } else {
            this.addCharClass(CodePointSet.create(10).createInverse(this.inSource.getEncoding()));
        }
        this.canHaveQuantifier = true;
    }

    private void caret() {
        this.pushGroup();
        this.addCaret();
        this.nextSequence();
        this.pushLookBehindAssertion(false);
        this.addCharClass(CodePointSet.create(10));
        this.popGroup();
        this.pushLookAheadAssertion(false);
        this.addCharClass(this.inSource.getEncoding().getFullSet());
        this.popGroup();
        this.popGroup();
        this.canHaveQuantifier = true;
    }

    private void dollar() {
        this.pushGroup();
        this.addDollar();
        this.nextSequence();
        this.pushLookAheadAssertion(false);
        this.addCharClass(CodePointSet.create(10));
        this.popGroup();
        this.popGroup();
        this.canHaveQuantifier = true;
    }

    private void string(int firstCodepoint) {
        this.codepointsBuffer.clear();
        this.codepointsBuffer.add(firstCodepoint);
        block4: while (!this.atEnd() && this.curChar() != 124 && this.curChar() != 41) {
            int ch = this.consumeChar();
            if (this.getLocalFlags().isExtended()) {
                if (WHITESPACE.get(ch)) continue;
                if (ch == 35) {
                    this.comment();
                    continue;
                }
            }
            switch (ch) {
                case 92: {
                    if (this.isProperEscapeNext()) {
                        this.retreat();
                        break block4;
                    }
                    this.codepointsBuffer.add(this.fetchEscapedChar());
                    continue block4;
                }
                case 36: 
                case 40: 
                case 42: 
                case 43: 
                case 46: 
                case 63: 
                case 91: 
                case 94: 
                case 123: {
                    this.retreat();
                    break block4;
                }
                default: {
                    this.codepointsBuffer.add(ch);
                    continue block4;
                }
            }
        }
        boolean isQuantifierNext = this.isQuantifierNext();
        int last = this.codepointsBuffer.get(this.codepointsBuffer.length() - 1);
        if (!this.silent) {
            if (isQuantifierNext) {
                this.codepointsBuffer.setLength(this.codepointsBuffer.length() - 1);
            }
            if (this.getLocalFlags().isIgnoreCase()) {
                RubyCaseFolding.caseFoldUnfoldString(this.codepointsBuffer.toArray(), this.inSource.getEncoding().getFullSet(), this.astBuilder);
            } else {
                for (int i = 0; i < this.codepointsBuffer.length(); ++i) {
                    this.addChar(this.codepointsBuffer.get(i));
                }
            }
            if (isQuantifierNext) {
                this.buildChar(last);
            }
        }
        this.canHaveQuantifier = true;
    }

    private void buildChar(int codepoint) {
        if (!this.silent) {
            if (this.getLocalFlags().isIgnoreCase()) {
                RubyCaseFolding.caseFoldUnfoldString(new int[]{codepoint}, this.inSource.getEncoding().getFullSet(), this.astBuilder);
            } else {
                this.addChar(codepoint);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean isProperEscapeNext() {
        boolean oldSilent = this.silent;
        int oldPosition = this.position;
        try {
            this.silent = true;
            boolean bl = this.assertionEscape() || this.categoryEscape(false) || this.backreference() || this.namedBackreference() || this.lineBreak() || this.extendedGraphemeCluster() || this.keepCommand() || this.subexpressionCall() || this.stringEscape() || this.characterEscape().isPresent();
            return bl;
        }
        finally {
            this.silent = oldSilent;
            this.position = oldPosition;
        }
    }

    public boolean isQuantifierNext() {
        if (this.atEnd()) {
            return false;
        }
        switch (this.curChar()) {
            case 42: 
            case 43: 
            case 63: {
                return true;
            }
            case 123: {
                int oldPosition = this.position;
                try {
                    this.advance();
                    if (this.match("}") || this.match(",}")) {
                        boolean bl = false;
                        return bl;
                    }
                    this.getMany(RubyRegexParser::isDecDigit);
                    if (this.match(",")) {
                        this.getMany(RubyRegexParser::isDecDigit);
                    }
                    if (!this.match("}")) {
                        boolean bl = false;
                        return bl;
                    }
                    boolean bl = true;
                    return bl;
                }
                finally {
                    this.position = oldPosition;
                }
            }
        }
        return false;
    }

    private void comment() {
        while (!this.atEnd()) {
            int ch = this.consumeChar();
            if (ch == 92 && !this.atEnd()) {
                this.advance();
                continue;
            }
            if (ch != 10) continue;
            break;
        }
    }

    private void escape() {
        if (!(this.assertionEscape() || this.categoryEscape(false) || this.backreference() || this.namedBackreference() || this.lineBreak() || this.extendedGraphemeCluster() || this.keepCommand() || this.subexpressionCall() || this.stringEscape())) {
            Optional<Integer> characterEscape = this.characterEscape();
            if (characterEscape.isPresent()) {
                this.buildChar(characterEscape.get());
            } else {
                this.string(this.fetchEscapedChar());
            }
        }
        this.canHaveQuantifier = true;
    }

    private CodePointSet getUnicodeCharClass(char className) {
        if (this.inSource.getEncoding() == Encodings.ASCII) {
            return ASCII_CHAR_CLASSES.get(Character.valueOf(className));
        }
        return this.trimToEncoding(UNICODE_CHAR_CLASSES.get(Character.valueOf(className)));
    }

    private CodePointSet getUnicodePosixCharClass(String className) {
        if (this.inSource.getEncoding() == Encodings.ASCII) {
            return ASCII_POSIX_CHAR_CLASSES.get(className);
        }
        return this.trimToEncoding(UNICODE_POSIX_CHAR_CLASSES.get(className));
    }

    private CodePointSet trimToEncoding(CodePointSet codePointSet) {
        return this.inSource.getEncoding().getFullSet().createIntersectionSingleRange(codePointSet);
    }

    private boolean assertionEscape() {
        int restorePosition = this.position;
        switch (this.consumeChar()) {
            case 65: {
                this.addCaret();
                return true;
            }
            case 90: {
                this.pushGroup();
                this.addDollar();
                this.nextSequence();
                this.pushLookAheadAssertion(false);
                this.addCharClass(NEWLINE_RETURN);
                this.addDollar();
                this.popGroup();
                this.popGroup();
                return true;
            }
            case 122: {
                this.addDollar();
                return true;
            }
            case 71: {
                this.bailOut("\\G anchor is only supported at the beginning of top-level alternatives");
                return true;
            }
            case 98: {
                if (this.getLocalFlags().isAscii()) {
                    this.buildWordBoundaryAssertion(ASCII_CHAR_CLASSES.get(Character.valueOf('w')), ASCII_CHAR_CLASSES.get(Character.valueOf('W')));
                } else {
                    this.buildWordBoundaryAssertion(this.getUnicodeCharClass('w'), this.getUnicodeCharClass('W'));
                }
                return true;
            }
            case 66: {
                if (this.getLocalFlags().isAscii()) {
                    this.buildWordNonBoundaryAssertion(ASCII_CHAR_CLASSES.get(Character.valueOf('w')), ASCII_CHAR_CLASSES.get(Character.valueOf('W')));
                } else {
                    this.buildWordNonBoundaryAssertion(this.getUnicodeCharClass('w'), this.getUnicodeCharClass('W'));
                }
                return true;
            }
        }
        this.position = restorePosition;
        return false;
    }

    private void buildWordBoundaryAssertion(CodePointSet wordChars, CodePointSet nonWordChars) {
        this.pushGroup();
        this.pushGroup();
        this.addCaret();
        this.nextSequence();
        this.pushLookBehindAssertion(false);
        this.addCharClass(nonWordChars);
        this.popGroup();
        this.popGroup();
        this.pushLookAheadAssertion(false);
        this.addCharClass(wordChars);
        this.popGroup();
        this.nextSequence();
        this.pushLookBehindAssertion(false);
        this.addCharClass(wordChars);
        this.popGroup();
        this.pushGroup();
        this.pushLookAheadAssertion(false);
        this.addCharClass(nonWordChars);
        this.popGroup();
        this.nextSequence();
        this.addDollar();
        this.popGroup();
        this.popGroup();
    }

    private void buildWordNonBoundaryAssertion(CodePointSet wordChars, CodePointSet nonWordChars) {
        this.pushGroup();
        this.pushGroup();
        this.addCaret();
        this.nextSequence();
        this.pushLookBehindAssertion(false);
        this.addCharClass(nonWordChars);
        this.popGroup();
        this.popGroup();
        this.pushGroup();
        this.pushLookAheadAssertion(false);
        this.addCharClass(nonWordChars);
        this.popGroup();
        this.nextSequence();
        this.addDollar();
        this.popGroup();
        this.nextSequence();
        this.pushLookBehindAssertion(false);
        this.addCharClass(wordChars);
        this.popGroup();
        this.pushLookAheadAssertion(false);
        this.addCharClass(wordChars);
        this.popGroup();
        this.popGroup();
    }

    private boolean categoryEscape(boolean inCharClass) {
        int restorePosition = this.position;
        switch (this.curChar()) {
            case 68: 
            case 72: 
            case 83: 
            case 87: 
            case 100: 
            case 104: 
            case 115: 
            case 119: {
                CodePointSet charSet;
                char className = (char)this.curChar();
                this.advance();
                if (this.getLocalFlags().isAscii() || this.getLocalFlags().isDefault()) {
                    charSet = ASCII_CHAR_CLASSES.get(Character.valueOf(className));
                } else {
                    assert (this.getLocalFlags().isUnicode());
                    charSet = this.getUnicodeCharClass('w');
                }
                if (inCharClass) {
                    this.curCharClass.addSet(charSet);
                    if (this.getLocalFlags().isIgnoreCase() && className != 'w' && className != 'W') {
                        this.fullyFoldableCharacters.addSet(charSet);
                    }
                } else {
                    this.addCharClass(charSet);
                }
                return true;
            }
            case 80: 
            case 112: {
                boolean capitalP = this.curChar() == 80;
                this.advance();
                if (this.match("{")) {
                    CodePointSet property;
                    boolean negative;
                    String propertySpec = this.getMany(c -> c != 125);
                    if (this.atEnd()) {
                        this.position = restorePosition;
                        return false;
                    }
                    this.advance();
                    boolean caret = propertySpec.startsWith("^");
                    boolean bl = negative = !(!capitalP && !caret || capitalP && caret);
                    if (caret) {
                        propertySpec = propertySpec.substring(1);
                    }
                    if (UNICODE_POSIX_CHAR_CLASSES.containsKey(propertySpec.toLowerCase())) {
                        property = this.getUnicodePosixCharClass(propertySpec.toLowerCase());
                    } else if (UnicodeProperties.isSupportedGeneralCategory(propertySpec, true)) {
                        property = this.trimToEncoding(UnicodeProperties.getProperty("General_Category=" + propertySpec, true));
                    } else if (UnicodeProperties.isSupportedScript(propertySpec, true)) {
                        property = this.trimToEncoding(UnicodeProperties.getProperty("Script=" + propertySpec, true));
                    } else if (UnicodeProperties.isSupportedProperty(propertySpec, true)) {
                        property = this.trimToEncoding(UnicodeProperties.getProperty(propertySpec, true));
                    } else {
                        this.bailOut("unsupported Unicode property " + propertySpec);
                        property = CodePointSet.getEmpty();
                    }
                    if (negative) {
                        property = property.createInverse(Encodings.UTF_32);
                    }
                    if (inCharClass) {
                        this.curCharClass.addSet(property);
                        if (this.getLocalFlags().isIgnoreCase() && !propertySpec.equalsIgnoreCase("ascii")) {
                            this.fullyFoldableCharacters.addSet(property);
                        }
                    } else {
                        this.addCharClass(property);
                    }
                    return true;
                }
                this.position = restorePosition;
                return false;
            }
        }
        return false;
    }

    private boolean backreference() {
        int restorePosition = this.position;
        if (this.curChar() >= 49 && this.curChar() <= 57) {
            String number = this.getUpTo(4, RubyRegexParser::isDecDigit);
            int groupNumber = Integer.parseInt(number);
            if (groupNumber > 1000) {
                this.position = restorePosition;
                return false;
            }
            if (this.containsNamedCaptureGroups()) {
                throw this.syntaxErrorAt("numbered backref/call is not allowed. (use name)", restorePosition);
            }
            if (groupNumber > this.numberOfCaptureGroups()) {
                throw this.syntaxErrorAt(RbErrorMessages.invalidGroupReference(number), restorePosition);
            }
            if (this.lookbehindDepth > 0) {
                throw this.syntaxErrorAt("invalid pattern in look-behind", restorePosition);
            }
            if (groupNumber > this.groupIndex && groupNumber >= 10) {
                this.position = restorePosition;
                return false;
            }
            this.buildBackreference(groupNumber);
            return true;
        }
        return false;
    }

    private boolean namedBackreference() {
        if (this.match("k<")) {
            int nameStart = this.position;
            List<Integer> groupNumbers = this.parseGroupReference('>', true, true, true, true);
            int nameEnd = this.position - 1;
            this.buildNamedBackreference((Integer[])groupNumbers.stream().filter(groupNumber -> groupNumber <= this.groupIndex).toArray(Integer[]::new), this.inPattern.substring(nameStart, nameEnd));
            return true;
        }
        return false;
    }

    private List<Integer> parseGroupReference(char terminator, boolean allowNumeric, boolean allowNamed, boolean allowLevels, boolean resolveReference) {
        List<Integer> groupNumbers = null;
        int beginPos = this.position;
        if (this.curChar() == 45 || RubyRegexParser.isDecDigit(this.curChar())) {
            int groupNumber;
            if (!allowNumeric) {
                throw this.syntaxErrorHere("invalid group name");
            }
            int sign = this.match("-") ? -1 : 1;
            String groupName = this.getMany(RubyRegexParser::isDecDigit);
            try {
                groupNumber = sign * Integer.parseInt(groupName);
            }
            catch (NumberFormatException e) {
                throw this.syntaxErrorAt("invalid group name", beginPos);
            }
            if (groupNumber < 0) {
                groupNumber = this.numberOfCaptureGroups() + 1 + groupNumber;
            }
            if (this.containsNamedCaptureGroups()) {
                throw this.syntaxErrorAt("numbered backref/call is not allowed. (use name)", beginPos);
            }
            if (resolveReference) {
                if (groupNumber < 0 || groupNumber > this.numberOfCaptureGroups()) {
                    throw this.syntaxErrorAt(RbErrorMessages.invalidGroupReference(groupName), beginPos);
                }
                groupNumbers = new ArrayList<Integer>(1);
                groupNumbers.add(groupNumber);
            }
        } else {
            if (!allowNamed) {
                throw this.syntaxErrorAt("invalid group name", beginPos);
            }
            String groupName = this.getMany(c -> {
                if (allowLevels) {
                    return c != terminator && c != 43 && c != 45;
                }
                return c != terminator;
            });
            if (groupName.isEmpty()) {
                throw this.syntaxErrorAt("missing group name", beginPos);
            }
            if (resolveReference) {
                if (this.namedCaptureGroups == null || !this.namedCaptureGroups.containsKey(groupName)) {
                    throw this.syntaxErrorAt(RbErrorMessages.unknownGroupName(groupName), beginPos);
                }
                groupNumbers = this.namedCaptureGroups.get(groupName);
            }
        }
        if (allowLevels && (this.curChar() == 43 || this.curChar() == 45)) {
            this.advance();
            String level = this.getMany(RubyRegexParser::isDecDigit);
            if (level.isEmpty()) {
                throw this.syntaxErrorAt("invalid group name", beginPos);
            }
            this.bailOut("backreferences to other levels are not supported");
        }
        if (!this.match(Character.toString(terminator))) {
            throw this.syntaxErrorAt("invalid group name", beginPos);
        }
        if (this.lookbehindDepth > 0) {
            throw this.syntaxErrorAt("invalid pattern in look-behind", beginPos);
        }
        return groupNumbers;
    }

    private void buildNamedBackreference(Integer[] groupNumbers, String name) {
        if (groupNumbers.length == 0) {
            throw this.syntaxErrorHere(RbErrorMessages.undefinedReference(name));
        }
        if (groupNumbers.length == 1) {
            this.buildBackreference(groupNumbers[0]);
        } else {
            this.pushGroup();
            this.buildBackreference(groupNumbers[groupNumbers.length - 1]);
            for (int i = groupNumbers.length - 2; i >= 0; --i) {
                this.nextSequence();
                this.buildBackreference(groupNumbers[i]);
            }
            this.popGroup();
        }
    }

    private void buildBackreference(int groupNumber) {
        if (this.isCaptureGroupOpen(groupNumber)) {
            this.addDeadNode();
        } else if (this.getLocalFlags().isIgnoreCase()) {
            this.bailOut("case insensitive backreferences not supported");
        } else {
            this.addBackReference(groupNumber);
        }
    }

    private boolean isCaptureGroupOpen(int groupNumber) {
        for (Group openGroup : this.groupStack) {
            if (groupNumber != openGroup.groupNumber) continue;
            return true;
        }
        return false;
    }

    private boolean lineBreak() {
        if (this.curChar() == 82) {
            this.advance();
            CodePointSet lineBreaks = this.inSource.getEncoding().isUnicode() ? UNICODE_LINE_BREAKS : ASCII_LINE_BREAKS;
            this.pushGroup();
            this.addChar(13);
            this.addChar(10);
            this.nextSequence();
            this.addChar(13);
            this.pushLookAheadAssertion(true);
            this.addChar(10);
            this.popGroup();
            this.nextSequence();
            this.addCharClass(lineBreaks);
            this.popGroup();
            return true;
        }
        return false;
    }

    private boolean extendedGraphemeCluster() {
        if (this.curChar() == 88) {
            this.advance();
            this.bailOut("extended grapheme cluster escape not supported");
            return true;
        }
        return false;
    }

    private boolean keepCommand() {
        if (this.curChar() == 75) {
            this.advance();
            this.bailOut("keep command not supported");
            return true;
        }
        return false;
    }

    private boolean subexpressionCall() {
        if (this.match("g<")) {
            int nameStart = this.position;
            List<Integer> targetGroups = this.parseGroupReference('>', true, true, false, true);
            int nameEnd = this.position - 1;
            if (targetGroups.size() > 1) {
                throw this.syntaxErrorHere(RbErrorMessages.multiplexCall(this.inPattern.substring(nameStart, nameEnd)));
            }
            this.addSubexpressionCall(targetGroups.get(0));
            this.hasSubexpressionCalls = true;
            return true;
        }
        return false;
    }

    private boolean stringEscape() {
        int beginPos = this.position - 1;
        if (this.match("u{")) {
            this.getMany(c -> ASCII_POSIX_CHAR_CLASSES.get("space").contains((int)c));
            while (!this.match("}")) {
                String code = this.getMany(RubyRegexParser::isHexDigit);
                try {
                    int codePoint = Integer.parseInt(code, 16);
                    if (codePoint > 0x10FFFF) {
                        throw this.syntaxErrorAt(RbErrorMessages.invalidUnicodeEscape(code), beginPos);
                    }
                    this.buildChar(codePoint);
                }
                catch (NumberFormatException e) {
                    throw this.syntaxErrorAt(RbErrorMessages.badEscape(code), beginPos);
                }
                this.getMany(c -> WHITESPACE.get((int)c));
            }
            return true;
        }
        return false;
    }

    private int fetchEscapedChar() {
        int beginPos = this.position;
        int ch = this.consumeChar();
        switch (ch) {
            case 97: {
                return 7;
            }
            case 98: {
                return 8;
            }
            case 101: {
                return 27;
            }
            case 102: {
                return 12;
            }
            case 110: {
                return 10;
            }
            case 114: {
                return 13;
            }
            case 116: {
                return 9;
            }
            case 118: {
                return 11;
            }
            case 67: 
            case 99: {
                if (this.atEnd()) {
                    throw this.syntaxErrorAt("end pattern at control", beginPos);
                }
                if (ch == 67 && !this.match("-")) {
                    throw this.syntaxErrorAt("invalid control-code syntax", beginPos);
                }
                int c = this.consumeChar();
                if (c == 63) {
                    return 127;
                }
                if (c == 92) {
                    c = this.fetchEscapedChar();
                }
                return c & 0x9F;
            }
            case 77: {
                if (this.atEnd()) {
                    throw this.syntaxErrorAt("end pattern at meta", beginPos);
                }
                if (!this.match("-")) {
                    throw this.syntaxErrorAt("invalid meta-code syntax", beginPos);
                }
                if (this.atEnd()) {
                    throw this.syntaxErrorAt("end pattern at meta", beginPos);
                }
                int c = this.consumeChar();
                if (c == 92) {
                    c = this.fetchEscapedChar();
                }
                return c & 0xFF | 0x80;
            }
        }
        return ch;
    }

    private Optional<Integer> characterEscape() {
        int beginPos = this.position;
        switch (this.curChar()) {
            case 120: {
                this.advance();
                String code = this.getUpTo(2, RubyRegexParser::isHexDigit);
                int byteValue = Integer.parseInt(code, 16);
                if (byteValue > 127) {
                    this.bailOut("unsupported multibyte escape");
                }
                return Optional.of(byteValue);
            }
            case 117: {
                String code;
                this.advance();
                if (this.match("{")) {
                    code = this.getMany(RubyRegexParser::isHexDigit);
                    this.mustMatch("}");
                } else {
                    code = this.getUpTo(4, RubyRegexParser::isHexDigit);
                    if (code.length() < 4) {
                        throw this.syntaxErrorAt(RbErrorMessages.incompleteEscape(code), beginPos);
                    }
                }
                try {
                    int codePoint = Integer.parseInt(code, 16);
                    if (codePoint > 0x10FFFF) {
                        throw this.syntaxErrorAt(RbErrorMessages.invalidUnicodeEscape(code), beginPos);
                    }
                    return Optional.of(codePoint);
                }
                catch (NumberFormatException e) {
                    throw this.syntaxErrorAt(RbErrorMessages.badEscape(code), beginPos);
                }
            }
            case 48: 
            case 49: 
            case 50: 
            case 51: 
            case 52: 
            case 53: 
            case 54: 
            case 55: {
                String code = this.getUpTo(3, RubyRegexParser::isOctDigit);
                int codePoint = Integer.parseInt(code, 8);
                if (codePoint > 255) {
                    throw this.syntaxErrorAt("too big number", beginPos);
                }
                return Optional.of(codePoint);
            }
        }
        return Optional.empty();
    }

    private void characterClass() {
        this.curCharClassClear();
        this.collectCharClass();
        this.buildCharClass();
        this.canHaveQuantifier = true;
    }

    private void buildCharClass() {
        if (!this.silent) {
            if (this.getLocalFlags().isIgnoreCase()) {
                List<Pair<Integer, int[]>> multiCodePointExpansions = this.caseClosureMultiCodePoint();
                if (multiCodePointExpansions.size() > 0) {
                    this.pushGroup();
                    this.addCharClass(this.curCharClass.toCodePointSet());
                    for (Pair<Integer, int[]> pair : multiCodePointExpansions) {
                        this.nextSequence();
                        int from = pair.getLeft();
                        int[] to = pair.getRight();
                        boolean dropAsciiOnStart = !this.fullyFoldableCharacters.get().contains(from);
                        RubyCaseFolding.caseFoldUnfoldString(to, this.inSource.getEncoding().getFullSet(), dropAsciiOnStart, this.astBuilder);
                    }
                    this.popGroup();
                } else {
                    this.addCharClass(this.curCharClass.toCodePointSet());
                }
            } else {
                this.addCharClass(this.curCharClass.toCodePointSet());
            }
        }
    }

    private void collectCharClass() {
        boolean negated = false;
        int beginPos = this.position - 1;
        if (this.match("^")) {
            negated = true;
        }
        int firstPosInside = this.position;
        block12: while (true) {
            Optional<Integer> lowerBound;
            if (this.atEnd()) {
                throw this.syntaxErrorAt("unterminated character set", beginPos);
            }
            int rangeStart = this.position;
            boolean wasNestedCharClass = false;
            int ch = this.consumeChar();
            switch (ch) {
                case 93: {
                    if (this.position != firstPosInside + 1) break block12;
                    lowerBound = Optional.of(93);
                    break;
                }
                case 92: {
                    lowerBound = this.classEscape();
                    break;
                }
                case 91: {
                    if (this.nestedCharClass()) {
                        wasNestedCharClass = true;
                        lowerBound = Optional.empty();
                        break;
                    }
                    lowerBound = Optional.of(ch);
                    break;
                }
                case 38: {
                    if (this.match("&")) {
                        this.charClassIntersection();
                        break block12;
                    }
                    lowerBound = Optional.of(ch);
                    break;
                }
                default: {
                    lowerBound = Optional.of(ch);
                }
            }
            if (!wasNestedCharClass && this.match("-")) {
                Optional<Integer> upperBound;
                if (this.atEnd()) {
                    throw this.syntaxErrorAt("unterminated character set", beginPos);
                }
                ch = this.consumeChar();
                switch (ch) {
                    case 93: {
                        if (lowerBound.isPresent()) {
                            this.curCharClassAddCodePoint(lowerBound.get());
                        }
                        this.curCharClassAddCodePoint(45);
                        break block12;
                    }
                    case 92: {
                        upperBound = this.classEscape();
                        break;
                    }
                    case 91: {
                        if (this.nestedCharClass()) {
                            wasNestedCharClass = true;
                            upperBound = Optional.empty();
                            break;
                        }
                        upperBound = Optional.of(ch);
                        break;
                    }
                    case 38: {
                        if (this.match("&")) {
                            if (lowerBound.isPresent()) {
                                this.curCharClassAddCodePoint(lowerBound.get());
                            }
                            this.curCharClassAddCodePoint(45);
                            this.charClassIntersection();
                            break block12;
                        }
                        upperBound = Optional.of(ch);
                        break;
                    }
                    default: {
                        upperBound = Optional.of(ch);
                    }
                }
                if (wasNestedCharClass) continue;
                if (!lowerBound.isPresent() || !upperBound.isPresent() || upperBound.get() < lowerBound.get()) {
                    throw this.syntaxErrorAt(RbErrorMessages.badCharacterRange(this.inPattern.substring(rangeStart, this.position)), rangeStart);
                }
                this.curCharClassAddRange(lowerBound.get(), upperBound.get());
                continue;
            }
            if (!lowerBound.isPresent()) continue;
            this.curCharClassAddCodePoint(lowerBound.get());
        }
        if (this.getLocalFlags().isIgnoreCase()) {
            this.caseClosure();
        }
        if (negated) {
            this.curCharClass.invert(this.inSource.getEncoding());
        }
    }

    private void curCharClassClear() {
        this.curCharClass.clear();
        if (this.getLocalFlags().isIgnoreCase()) {
            this.fullyFoldableCharacters.clear();
        }
    }

    private void curCharClassAddCodePoint(int codepoint) {
        this.curCharClass.addCodePoint(codepoint);
        if (this.getLocalFlags().isIgnoreCase()) {
            this.fullyFoldableCharacters.addCodePoint(codepoint);
        }
    }

    private void curCharClassAddRange(int lower, int upper) {
        this.curCharClass.addRange(lower, upper);
        if (this.getLocalFlags().isIgnoreCase()) {
            this.fullyFoldableCharacters.addRange(lower, upper);
        }
    }

    private CodePointSetAccumulator acquireCodePointSetAccumulator() {
        if (this.charClassPool.isEmpty()) {
            return new CodePointSetAccumulator();
        }
        CodePointSetAccumulator accumulator = this.charClassPool.remove(this.charClassPool.size() - 1);
        accumulator.clear();
        return accumulator;
    }

    private void releaseCodePointSetAccumulator(CodePointSetAccumulator accumulator) {
        this.charClassPool.add(accumulator);
    }

    private void charClassIntersection() {
        CodePointSetAccumulator curCharClassBackup = this.curCharClass;
        CodePointSetAccumulator foldableCharsBackup = this.fullyFoldableCharacters;
        this.curCharClass = this.acquireCodePointSetAccumulator();
        if (this.getLocalFlags().isIgnoreCase()) {
            this.fullyFoldableCharacters = this.acquireCodePointSetAccumulator();
        }
        this.collectCharClass();
        curCharClassBackup.intersectWith(this.curCharClass.get());
        this.curCharClass = curCharClassBackup;
        if (this.getLocalFlags().isIgnoreCase()) {
            foldableCharsBackup.addSet(this.fullyFoldableCharacters.get());
            this.fullyFoldableCharacters = foldableCharsBackup;
        }
    }

    private boolean nestedCharClass() {
        CodePointSetAccumulator curCharClassBackup = this.curCharClass;
        this.curCharClass = this.acquireCodePointSetAccumulator();
        PosixClassParseResult parseResult = this.collectPosixCharClass();
        if (parseResult == PosixClassParseResult.TryNestedClass) {
            this.collectCharClass();
        }
        curCharClassBackup.addSet(this.curCharClass.get());
        this.releaseCodePointSetAccumulator(this.curCharClass);
        this.curCharClass = curCharClassBackup;
        return parseResult != PosixClassParseResult.NotNestedClass;
    }

    private PosixClassParseResult collectPosixCharClass() {
        String className;
        int restorePosition = this.position;
        if (!this.match(":")) {
            return PosixClassParseResult.TryNestedClass;
        }
        boolean negated = false;
        if (this.match("^")) {
            negated = true;
        }
        if ((className = this.getMany(c -> c != 92 && c != 58 && c != 93)).length() > 20) {
            this.position = restorePosition;
            return PosixClassParseResult.NotNestedClass;
        }
        if (this.match(":]")) {
            CodePointSet charSet;
            if (!UNICODE_POSIX_CHAR_CLASSES.containsKey(className)) {
                throw this.syntaxErrorAt("invalid POSIX bracket type", restorePosition);
            }
            if (this.getLocalFlags().isAscii()) {
                charSet = ASCII_POSIX_CHAR_CLASSES.get(className);
            } else {
                assert (this.getLocalFlags().isDefault() || this.getLocalFlags().isUnicode());
                charSet = this.getUnicodePosixCharClass(className);
            }
            if (negated) {
                charSet = charSet.createInverse(this.inSource.getEncoding());
            }
            this.curCharClass.addSet(charSet);
            if (this.getLocalFlags().isIgnoreCase() && !this.getLocalFlags().isAscii() && !className.equals("word") && !className.equals("ascii")) {
                this.fullyFoldableCharacters.addSet(charSet);
            }
            return PosixClassParseResult.WasNestedPosixClass;
        }
        this.position = restorePosition;
        return PosixClassParseResult.TryNestedClass;
    }

    private void caseFoldCharClass(BiConsumer<Integer, int[]> caseFoldItem) {
        if (this.curCharClass.get().size() < RubyCaseFoldingData.CASE_FOLD.size()) {
            for (Range r : this.curCharClass) {
                RubyCaseFoldingData.CASE_FOLD.subMap(r.lo, r.hi + 1).forEach((from, to) -> caseFoldItem.accept((Integer)from, (int[])to));
            }
        } else {
            RubyCaseFoldingData.CASE_FOLD.forEach((from, to) -> {
                if (this.curCharClass.get().contains((int)from)) {
                    caseFoldItem.accept((Integer)from, (int[])to);
                }
            });
        }
    }

    private boolean acceptableCaseFold(int from, int to) {
        return this.fullyFoldableCharacters.get().contains(from) || RubyRegexParser.isAscii(from) == RubyRegexParser.isAscii(to);
    }

    private void caseClosure() {
        this.charClassTmp.clear();
        this.caseFoldCharClass((from, to) -> {
            if (((int[])to).length == 1 && this.acceptableCaseFold((int)from, to[0])) {
                this.charClassTmp.addCodePoint(to[0]);
            }
            for (int unfolding : RubyCaseUnfoldingTrie.findSingleCharUnfoldings(to)) {
                if (unfolding == from || !this.acceptableCaseFold((int)from, unfolding)) continue;
                this.charClassTmp.addCodePoint(unfolding);
            }
        });
        for (Range r : this.curCharClass) {
            for (int codepoint = r.lo; codepoint <= r.hi; ++codepoint) {
                for (int unfolding : RubyCaseUnfoldingTrie.findSingleCharUnfoldings(codepoint)) {
                    if (!this.acceptableCaseFold(codepoint, unfolding)) continue;
                    this.charClassTmp.addCodePoint(unfolding);
                }
            }
        }
        this.charClassTmp.intersectWith(this.inSource.getEncoding().getFullSet());
        this.curCharClass.addSet(this.charClassTmp.get());
    }

    private List<Pair<Integer, int[]>> caseClosureMultiCodePoint() {
        ArrayList<Pair<Integer, int[]>> multiCodePointExpansions = new ArrayList<Pair<Integer, int[]>>();
        this.caseFoldCharClass((from, to) -> {
            if (((int[])to).length > 1) {
                assert (!RubyRegexParser.isAscii(from));
                multiCodePointExpansions.add(Pair.create(from, to));
            }
        });
        return multiCodePointExpansions;
    }

    private Optional<Integer> classEscape() {
        if (this.categoryEscape(true)) {
            return Optional.empty();
        }
        Optional<Integer> characterEscape = this.characterEscape();
        if (characterEscape.isPresent()) {
            return characterEscape;
        }
        return Optional.of(this.fetchEscapedChar());
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private void quantifier(int ch) {
        int start2 = this.position - 1;
        Quantifier quantifier = this.parseQuantifier(ch);
        if (quantifier != null) {
            if (!this.canHaveQuantifier) throw this.syntaxErrorAt("nothing to repeat", start2);
            this.addQuantifier(Token.createQuantifier(quantifier.lower, quantifier.upper, quantifier.greedy));
            if (!quantifier.possessive) return;
            this.wrapCurTermInAtomicGroup();
            return;
        } else {
            this.string(this.consumeChar());
        }
    }

    private Quantifier parseQuantifier(int ch) {
        int upper;
        int lower;
        int start2 = this.position - 1;
        if (ch == 123) {
            if (this.match("}") || this.match(",}")) {
                this.position = start2;
                return null;
            }
            Optional<Object> lowerBound = Optional.empty();
            Optional<Object> upperBound = Optional.empty();
            boolean canBeNonGreedy = true;
            String lower2 = this.getMany(RubyRegexParser::isDecDigit);
            if (!lower2.isEmpty()) {
                lowerBound = Optional.of(new BigInteger(lower2));
            }
            if (this.match(",")) {
                String upper2 = this.getMany(RubyRegexParser::isDecDigit);
                if (!upper2.isEmpty()) {
                    upperBound = Optional.of(new BigInteger(upper2));
                }
            } else {
                upperBound = lowerBound;
                canBeNonGreedy = false;
            }
            if (!this.match("}")) {
                this.position = start2;
                return null;
            }
            if (lowerBound.isPresent() && upperBound.isPresent() && ((BigInteger)lowerBound.get()).compareTo((BigInteger)upperBound.get()) > 0) {
                throw this.syntaxErrorAt("min repeat greater than max repeat", start2);
            }
            boolean greedy = true;
            if (canBeNonGreedy && this.match("?")) {
                greedy = false;
            }
            return new Quantifier(((BigInteger)lowerBound.orElse(BigInteger.ZERO)).intValue(), ((BigInteger)upperBound.orElse(BigInteger.valueOf(-1L))).intValue(), greedy, false);
        }
        switch (ch) {
            case 42: {
                lower = 0;
                upper = -1;
                break;
            }
            case 43: {
                lower = 1;
                upper = -1;
                break;
            }
            case 63: {
                lower = 0;
                upper = 1;
                break;
            }
            default: {
                throw new IllegalStateException("should not reach here");
            }
        }
        boolean greedy = true;
        boolean possessive = false;
        if (this.match("?")) {
            greedy = false;
        } else if (this.match("+")) {
            possessive = true;
        }
        return new Quantifier(lower, upper, greedy, possessive);
    }

    private void parens() {
        if (this.atEnd()) {
            throw this.syntaxErrorAtEnd("missing ), unterminated subpattern");
        }
        if (this.match("?")) {
            int ch1 = this.consumeChar();
            block0 : switch (ch1) {
                case 58: {
                    this.group(false);
                    break;
                }
                case 35: {
                    this.parenComment();
                    break;
                }
                case 60: {
                    int ch2 = this.consumeChar();
                    switch (ch2) {
                        case 61: {
                            this.lookbehind(false);
                            break block0;
                        }
                        case 33: {
                            this.lookbehind(true);
                            break block0;
                        }
                    }
                    this.retreat();
                    this.parseGroupName('>');
                    this.group(true);
                    break;
                }
                case 61: {
                    this.lookahead(false);
                    break;
                }
                case 33: {
                    this.lookahead(true);
                    break;
                }
                case 62: {
                    if (this.inSource.getOptions().isIgnoreAtomicGroups()) {
                        this.group(false);
                        break;
                    }
                    this.atomicGroup();
                    break;
                }
                case 40: {
                    this.conditionalBackreference();
                    break;
                }
                case 126: {
                    this.absentExpression();
                    break;
                }
                case 45: 
                case 97: 
                case 100: 
                case 105: 
                case 109: 
                case 117: 
                case 120: {
                    this.flags(ch1);
                    break;
                }
                default: {
                    throw this.syntaxErrorAt(RbErrorMessages.unknownExtension(ch1), this.position - 1);
                }
            }
        } else {
            this.group(!this.containsNamedCaptureGroups());
        }
    }

    private String parseGroupName(char terminator) {
        String groupName = this.getMany(c -> c != terminator);
        if (!this.match(Character.toString(terminator))) {
            throw this.syntaxErrorHere(RbErrorMessages.unterminatedName(terminator));
        }
        if (groupName.isEmpty()) {
            throw this.syntaxErrorHere("missing group name");
        }
        return groupName;
    }

    private void parenComment() {
        int beginPos = this.position - 2;
        while (true) {
            if (this.atEnd()) {
                throw this.syntaxErrorAt("missing ), unterminated comment", beginPos);
            }
            int ch = this.consumeChar();
            if (ch == 92 && !this.atEnd()) {
                this.advance();
                continue;
            }
            if (ch == 41) break;
        }
    }

    private void group(boolean capturing) {
        if (capturing) {
            ++this.groupIndex;
            this.groupStack.push(new Group(this.groupIndex));
            this.pushCaptureGroup();
        } else {
            this.pushGroup();
        }
        this.disjunction();
        if (this.match(")")) {
            this.popGroup();
            if (capturing) {
                this.groupStack.pop();
            }
        } else {
            throw this.syntaxErrorHere("missing ), unterminated subpattern");
        }
        this.canHaveQuantifier = true;
    }

    private void lookahead(boolean negate) {
        this.pushLookAheadAssertion(negate);
        this.disjunction();
        if (!this.match(")")) {
            throw this.syntaxErrorHere("missing ), unterminated subpattern");
        }
        this.popGroup();
        this.canHaveQuantifier = true;
    }

    private void lookbehind(boolean negate) {
        this.pushLookBehindAssertion(negate);
        ++this.lookbehindDepth;
        this.disjunction();
        --this.lookbehindDepth;
        if (!this.match(")")) {
            throw this.syntaxErrorHere("missing ), unterminated subpattern");
        }
        this.popGroup();
        this.canHaveQuantifier = true;
    }

    private void atomicGroup() {
        this.pushAtomicGroup();
        this.disjunction();
        if (!this.match(")")) {
            throw this.syntaxErrorHere("missing ), unterminated subpattern");
        }
        this.popGroup();
        this.canHaveQuantifier = true;
    }

    private void conditionalBackreference() {
        this.bailOut("conditional backreference groups not supported");
        if (this.match("<")) {
            this.parseGroupReference('>', true, true, true, true);
            this.mustMatch(")");
        } else if (this.match("'")) {
            this.parseGroupReference('\'', true, true, true, true);
            this.mustMatch(")");
        } else if (RubyRegexParser.isDecDigit(this.curChar())) {
            this.parseGroupReference(')', true, false, true, true);
        } else {
            throw this.syntaxErrorHere("invalid group name");
        }
        this.disjunction();
        if (this.match("|")) {
            this.disjunction();
            if (this.curChar() == 124) {
                throw this.syntaxErrorHere("conditional backref with more than two branches");
            }
        }
        if (!this.match(")")) {
            throw this.syntaxErrorHere("missing ), unterminated subpattern");
        }
        this.canHaveQuantifier = true;
    }

    private void absentExpression() {
        this.disjunction();
        if (!this.match(")")) {
            throw this.syntaxErrorHere("missing ), unterminated subpattern");
        }
        this.bailOut("absent expressions not supported");
        this.canHaveQuantifier = true;
    }

    private void flags(int ch0) {
        int ch = ch0;
        RubyFlags newFlags = this.getLocalFlags();
        boolean negative = false;
        while (ch != 41 && ch != 58) {
            if (ch == 45) {
                negative = true;
            } else if (RubyFlags.isValidFlagChar(ch)) {
                if (negative) {
                    if (RubyFlags.isTypeFlag(ch)) {
                        throw this.syntaxErrorHere("undefined group option");
                    }
                    newFlags = newFlags.delFlag(ch);
                } else {
                    newFlags = newFlags.addFlag(ch);
                }
            } else {
                if (Character.isAlphabetic(ch)) {
                    throw this.syntaxErrorHere("undefined group option");
                }
                throw this.syntaxErrorHere("missing -, : or )");
            }
            if (this.atEnd()) {
                throw this.syntaxErrorAtEnd("missing flag, -, : or )");
            }
            ch = this.consumeChar();
        }
        if (ch == 41) {
            this.openEndedLocalFlags(newFlags);
        } else {
            assert (ch == 58);
            this.localFlags(newFlags);
        }
    }

    private void localFlags(RubyFlags newFlags) {
        this.flagsStack.push(newFlags);
        this.group(false);
        this.flagsStack.pop();
    }

    private void openEndedLocalFlags(RubyFlags newFlags) {
        this.setLocalFlags(newFlags);
        this.canHaveQuantifier = false;
        this.pushGroup();
        this.disjunction();
        this.popGroup();
    }

    private static /* synthetic */ List lambda$scanForCaptureGroups$0(String k) {
        return new ArrayList();
    }

    static {
        NEWLINE_RETURN = CodePointSet.create(10, 10, 13, 13);
        UNICODE_LINE_BREAKS = CodePointSet.create(10, 12, 133, 133, 8232, 8233);
        ASCII_LINE_BREAKS = CodePointSet.create(10, 12);
        CodePointSet asciiRange = CodePointSet.create(0, 127);
        CodePointSet nonAsciiRange = CodePointSet.create(128, 0x10FFFF);
        UNICODE_CHAR_CLASSES = new HashMap<Character, CodePointSet>(8);
        ASCII_CHAR_CLASSES = new HashMap<Character, CodePointSet>(8);
        CodePointSet alpha = UnicodeProperties.getProperty("Alphabetic");
        CodePointSet digit = UnicodeProperties.getProperty("General_Category=Decimal_Number");
        CodePointSet space = UnicodeProperties.getProperty("White_Space");
        CodePointSet xdigit = CodePointSet.create(48, 57, 65, 70, 97, 102);
        CodePointSet word = alpha.union(UnicodeProperties.getProperty("General_Category=Mark")).union(digit).union(UnicodeProperties.getProperty("General_Category=Connector_Punctuation")).union(UnicodeProperties.getProperty("Join_Control"));
        UNICODE_CHAR_CLASSES.put(Character.valueOf('d'), digit);
        UNICODE_CHAR_CLASSES.put(Character.valueOf('h'), xdigit);
        UNICODE_CHAR_CLASSES.put(Character.valueOf('s'), space);
        UNICODE_CHAR_CLASSES.put(Character.valueOf('w'), word);
        Character[] characterArray = new Character[]{Character.valueOf('d'), Character.valueOf('h'), Character.valueOf('s'), Character.valueOf('w')};
        int n = characterArray.length;
        for (int i = 0; i < n; ++i) {
            char ctypeChar = characterArray[i].charValue();
            CodePointSet charSet = UNICODE_CHAR_CLASSES.get(Character.valueOf(ctypeChar));
            char complementCTypeChar = Character.toUpperCase(ctypeChar);
            CodePointSet complementCharSet = charSet.createInverse(Encodings.UTF_32);
            UNICODE_CHAR_CLASSES.put(Character.valueOf(complementCTypeChar), complementCharSet);
            ASCII_CHAR_CLASSES.put(Character.valueOf(ctypeChar), asciiRange.createIntersectionSingleRange(charSet));
            ASCII_CHAR_CLASSES.put(Character.valueOf(complementCTypeChar), complementCharSet.union(nonAsciiRange));
        }
        UNICODE_POSIX_CHAR_CLASSES = new HashMap<String, CodePointSet>(14);
        ASCII_POSIX_CHAR_CLASSES = new HashMap<String, CodePointSet>(14);
        CompilationBuffer buffer = new CompilationBuffer(Encodings.UTF_32);
        CodePointSet blank = UnicodeProperties.getProperty("General_Category=Space_Separator").union(CodePointSet.create(9, 9));
        CodePointSet cntrl = UnicodeProperties.getProperty("General_Category=Control");
        CodePointSet graph = space.union(UnicodeProperties.getProperty("General_Category=Control")).union(UnicodeProperties.getProperty("General_Category=Surrogate")).union(UnicodeProperties.getProperty("General_Category=Unassigned")).createInverse(Encodings.UTF_32);
        UNICODE_POSIX_CHAR_CLASSES.put("alpha", alpha);
        UNICODE_POSIX_CHAR_CLASSES.put("alnum", alpha.union(digit));
        UNICODE_POSIX_CHAR_CLASSES.put("blank", blank);
        UNICODE_POSIX_CHAR_CLASSES.put("cntrl", cntrl);
        UNICODE_POSIX_CHAR_CLASSES.put("digit", digit);
        UNICODE_POSIX_CHAR_CLASSES.put("graph", graph);
        UNICODE_POSIX_CHAR_CLASSES.put("lower", UnicodeProperties.getProperty("Lowercase"));
        UNICODE_POSIX_CHAR_CLASSES.put("print", graph.union(blank).subtract(cntrl, buffer));
        UNICODE_POSIX_CHAR_CLASSES.put("punct", UnicodeProperties.getProperty("General_Category=Punctuation").union(UnicodeProperties.getProperty("General_Category=Symbol").subtract(alpha, buffer)));
        UNICODE_POSIX_CHAR_CLASSES.put("space", space);
        UNICODE_POSIX_CHAR_CLASSES.put("upper", UnicodeProperties.getProperty("Uppercase"));
        UNICODE_POSIX_CHAR_CLASSES.put("xdigit", xdigit);
        UNICODE_POSIX_CHAR_CLASSES.put("word", word);
        UNICODE_POSIX_CHAR_CLASSES.put("ascii", UnicodeProperties.getProperty("ASCII"));
        for (Map.Entry<String, CodePointSet> entry : UNICODE_POSIX_CHAR_CLASSES.entrySet()) {
            ASCII_POSIX_CHAR_CLASSES.put(entry.getKey(), asciiRange.createIntersectionSingleRange(entry.getValue()));
        }
        WHITESPACE = TBitSet.valueOf(9, 10, 12, 13, 32);
    }

    private static enum PosixClassParseResult {
        WasNestedPosixClass,
        TryNestedClass,
        NotNestedClass;

    }

    private static final class Quantifier {
        public static final int INFINITY = -1;
        public final int lower;
        public final int upper;
        public final boolean greedy;
        public final boolean possessive;

        Quantifier(int lower, int upper, boolean greedy, boolean possessive) {
            this.lower = lower;
            this.upper = upper;
            this.greedy = greedy;
            this.possessive = possessive;
        }

        public String toString() {
            StringBuilder output = new StringBuilder();
            if (this.lower == 0 && this.upper == -1) {
                output.append("*");
            } else if (this.lower == 1 && this.upper == -1) {
                output.append("+");
            } else if (this.lower == 0 && this.upper == 1) {
                output.append("?");
            } else {
                output.append("{");
                output.append(this.lower);
                output.append(",");
                if (this.upper != -1) {
                    output.append(this.upper);
                }
                output.append("}");
            }
            if (!this.greedy) {
                output.append("?");
            }
            if (this.possessive) {
                output.append("+");
            }
            return output.toString();
        }
    }

    private static final class Group {
        public final int groupNumber;

        Group(int groupNumber) {
            this.groupNumber = groupNumber;
        }
    }
}

