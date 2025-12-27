package com.oracle.truffle.regex.tregex.parser;

import com.oracle.truffle.regex.RegexSyntaxException;

public interface RegexValidator {
   void validate() throws RegexSyntaxException;
}
