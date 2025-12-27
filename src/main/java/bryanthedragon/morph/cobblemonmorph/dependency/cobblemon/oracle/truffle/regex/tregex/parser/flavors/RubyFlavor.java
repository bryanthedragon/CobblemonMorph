package com.oracle.truffle.regex.tregex.parser.flavors;

import com.oracle.truffle.regex.RegexLanguage;
import com.oracle.truffle.regex.RegexSource;
import com.oracle.truffle.regex.tregex.buffer.CompilationBuffer;
import com.oracle.truffle.regex.tregex.parser.RegexParser;
import com.oracle.truffle.regex.tregex.parser.RegexValidator;

public final class RubyFlavor extends RegexFlavor {
   public static final RubyFlavor INSTANCE = new RubyFlavor();

   private RubyFlavor() {
      super(15);
   }

   @Override
   public RegexValidator createValidator(RegexSource source) {
      return RubyRegexParser.createValidator(source);
   }

   @Override
   public RegexParser createParser(RegexLanguage language, RegexSource source, CompilationBuffer compilationBuffer) {
      return RubyRegexParser.createParser(language, source, compilationBuffer);
   }
}
