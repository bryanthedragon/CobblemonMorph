
package com.oracle.truffle.regex.tregex.parser;

import com.oracle.truffle.regex.AbstractRegexObject;
import com.oracle.truffle.regex.RegexSyntaxException;
import com.oracle.truffle.regex.UnsupportedRegexException;
import com.oracle.truffle.regex.tregex.parser.ast.RegexAST;

public interface RegexParser {
    public RegexAST parse() throws RegexSyntaxException, UnsupportedRegexException;

    public AbstractRegexObject getFlags();

    public AbstractRegexObject getNamedCaptureGroups();
}

