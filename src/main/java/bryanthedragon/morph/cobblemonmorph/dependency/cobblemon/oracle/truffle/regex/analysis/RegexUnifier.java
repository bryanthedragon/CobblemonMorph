
package com.oracle.truffle.regex.analysis;

import com.oracle.truffle.regex.RegexFlags;
import com.oracle.truffle.regex.RegexSource;
import com.oracle.truffle.regex.RegexSyntaxException;
import com.oracle.truffle.regex.tregex.parser.RegexLexer;
import com.oracle.truffle.regex.tregex.parser.Token;

public final class RegexUnifier {
    private final RegexSource source;
    private final RegexLexer lexer;
    private final StringBuilder dump;

    public RegexUnifier(RegexSource source) {
        this.source = source;
        this.lexer = new RegexLexer(source, RegexFlags.parseFlags(source));
        this.dump = new StringBuilder(source.getPattern().length());
    }

    public String getUnifiedPattern() throws RegexSyntaxException {
        this.dump.append("/");
        while (this.lexer.hasNext()) {
            Token token = this.lexer.next();
            switch (token.kind) {
                case caret: {
                    this.dump.append("^");
                    break;
                }
                case dollar: {
                    this.dump.append("$");
                    break;
                }
                case wordBoundary: {
                    this.dump.append("\\b");
                    break;
                }
                case nonWordBoundary: {
                    this.dump.append("\\B");
                    break;
                }
                case backReference: {
                    this.dump.append("\\").append(((Token.BackReference)token).getGroupNr());
                    break;
                }
                case quantifier: {
                    Token.Quantifier quantifier = (Token.Quantifier)token;
                    if (quantifier.getMin() == 0 && quantifier.getMax() == 1) {
                        this.dump.append("?");
                    } else if (quantifier.getMin() == 0 && quantifier.isInfiniteLoop()) {
                        this.dump.append("*");
                    } else if (quantifier.getMin() == 1 && quantifier.isInfiniteLoop()) {
                        this.dump.append("+");
                    } else {
                        String lowerBound = quantifier.getMin() == -1 ? "Inf" : Integer.toString(quantifier.getMin());
                        this.dump.append("{").append(lowerBound);
                        if (quantifier.getMax() != quantifier.getMin()) {
                            this.dump.append(",");
                            if (!quantifier.isInfiniteLoop()) {
                                this.dump.append(quantifier.getMax());
                            }
                        }
                        this.dump.append("}");
                    }
                    if (quantifier.isGreedy()) break;
                    this.dump.append("?");
                    break;
                }
                case alternation: {
                    this.dump.append("|");
                    break;
                }
                case captureGroupBegin: {
                    this.dump.append("(");
                    break;
                }
                case nonCaptureGroupBegin: {
                    this.dump.append("(?:");
                    break;
                }
                case lookAheadAssertionBegin: {
                    this.dump.append(((Token.LookAheadAssertionBegin)token).isNegated() ? "(?!" : "(?=");
                    break;
                }
                case lookBehindAssertionBegin: {
                    this.dump.append(((Token.LookBehindAssertionBegin)token).isNegated() ? "(?<!" : "(?<=");
                    break;
                }
                case groupEnd: {
                    this.dump.append(")");
                    break;
                }
                case charClass: {
                    if (((Token.CharacterClass)token).getCodePointSet().matchesSingleChar()) {
                        this.dump.append("x");
                        break;
                    }
                    this.dump.append("[c]");
                }
            }
        }
        this.dump.append("/");
        this.dump.append(this.source.getFlags());
        return this.dump.toString();
    }
}

