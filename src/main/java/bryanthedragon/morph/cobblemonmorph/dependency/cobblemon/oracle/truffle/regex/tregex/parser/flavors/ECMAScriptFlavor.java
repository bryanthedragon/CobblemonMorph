package com.oracle.truffle.regex.tregex.parser.flavors;

import com.oracle.truffle.regex.RegexLanguage;
import com.oracle.truffle.regex.RegexSource;
import com.oracle.truffle.regex.tregex.buffer.CompilationBuffer;
import com.oracle.truffle.regex.tregex.parser.JSRegexParser;
import com.oracle.truffle.regex.tregex.parser.JSRegexValidator;
import com.oracle.truffle.regex.tregex.parser.RegexParser;
import com.oracle.truffle.regex.tregex.parser.RegexValidator;

public final class ECMAScriptFlavor extends RegexFlavor {
   public static final ECMAScriptFlavor INSTANCE = new ECMAScriptFlavor();

   private ECMAScriptFlavor() {
      super(0);
   }

   @Override
   public RegexValidator createValidator(RegexSource source) {
      return new JSRegexValidator(source);
   }

   @Override
   public RegexParser createParser(RegexLanguage language, RegexSource source, CompilationBuffer compilationBuffer) {
      return new JSRegexParser(language, source, compilationBuffer);
   }
}
