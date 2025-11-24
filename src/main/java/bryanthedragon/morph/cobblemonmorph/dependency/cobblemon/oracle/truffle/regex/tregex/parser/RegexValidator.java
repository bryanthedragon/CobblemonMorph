
package com.oracle.truffle.regex.tregex.parser;

import com.oracle.truffle.regex.RegexSyntaxException;

public interface RegexValidator {
    public void validate() throws RegexSyntaxException;
}

