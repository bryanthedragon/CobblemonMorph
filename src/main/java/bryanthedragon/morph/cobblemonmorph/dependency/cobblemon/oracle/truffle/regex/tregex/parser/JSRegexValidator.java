
package com.oracle.truffle.regex.tregex.parser;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.regex.RegexFlags;
import com.oracle.truffle.regex.RegexSource;
import com.oracle.truffle.regex.RegexSyntaxException;
import com.oracle.truffle.regex.tregex.parser.RegexLexer;
import com.oracle.truffle.regex.tregex.parser.RegexValidator;
import com.oracle.truffle.regex.tregex.parser.Token;
import java.util.ArrayList;

public class JSRegexValidator
implements RegexValidator {
    private final RegexSource source;
    private final RegexFlags flags;
    private final RegexLexer lexer;

    public JSRegexValidator(RegexSource source) {
        this.source = source;
        this.flags = RegexFlags.parseFlags(source);
        this.lexer = new RegexLexer(source, this.flags);
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public void validate() throws RegexSyntaxException {
        this.parseDryRun();
    }

    private void parseDryRun() throws RegexSyntaxException {
        ArrayList<RegexStackElem> syntaxStack = new ArrayList<RegexStackElem>();
        CurTermState curTermState = CurTermState.Null;
        while (this.lexer.hasNext()) {
            Token token = this.lexer.next();
            block0 : switch (token.kind) {
                case caret: 
                case dollar: 
                case wordBoundary: 
                case nonWordBoundary: 
                case backReference: 
                case charClass: {
                    curTermState = CurTermState.Other;
                    break;
                }
                case quantifier: {
                    switch (curTermState) {
                        case Null: {
                            throw this.syntaxError("Quantifier without target");
                        }
                        case LookAheadAssertion: {
                            if (!this.flags.isUnicode()) break;
                            throw this.syntaxError("Quantifier on lookahead assertion");
                        }
                        case LookBehindAssertion: {
                            throw this.syntaxError("Quantifier on lookbehind assertion");
                        }
                    }
                    curTermState = CurTermState.Other;
                    break;
                }
                case alternation: {
                    curTermState = CurTermState.Null;
                    break;
                }
                case captureGroupBegin: 
                case nonCaptureGroupBegin: {
                    syntaxStack.add(RegexStackElem.Group);
                    curTermState = CurTermState.Null;
                    break;
                }
                case lookAheadAssertionBegin: {
                    syntaxStack.add(RegexStackElem.LookAheadAssertion);
                    curTermState = CurTermState.Null;
                    break;
                }
                case lookBehindAssertionBegin: {
                    syntaxStack.add(RegexStackElem.LookBehindAssertion);
                    curTermState = CurTermState.Null;
                    break;
                }
                case groupEnd: {
                    if (syntaxStack.isEmpty()) {
                        throw this.syntaxError("Unmatched ')'");
                    }
                    RegexStackElem poppedElem = (RegexStackElem)((Object)syntaxStack.remove(syntaxStack.size() - 1));
                    switch (poppedElem) {
                        case LookAheadAssertion: {
                            curTermState = CurTermState.LookAheadAssertion;
                            break block0;
                        }
                        case LookBehindAssertion: {
                            curTermState = CurTermState.LookBehindAssertion;
                            break block0;
                        }
                        case Group: {
                            curTermState = CurTermState.Other;
                        }
                    }
                }
            }
        }
        if (!syntaxStack.isEmpty()) {
            throw this.syntaxError("Unterminated group");
        }
    }

    private RegexSyntaxException syntaxError(String msg) {
        return RegexSyntaxException.createPattern(this.source, msg, this.lexer.getLastTokenPosition());
    }

    private static enum CurTermState {
        Null,
        LookAheadAssertion,
        LookBehindAssertion,
        Other;

    }

    private static enum RegexStackElem {
        Group,
        LookAheadAssertion,
        LookBehindAssertion;

    }
}

