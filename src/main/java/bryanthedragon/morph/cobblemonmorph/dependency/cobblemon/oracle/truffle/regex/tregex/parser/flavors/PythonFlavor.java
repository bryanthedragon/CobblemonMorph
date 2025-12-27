package com.oracle.truffle.regex.tregex.parser.flavors;

import com.oracle.truffle.regex.RegexLanguage;
import com.oracle.truffle.regex.RegexSource;
import com.oracle.truffle.regex.tregex.buffer.CompilationBuffer;
import com.oracle.truffle.regex.tregex.parser.RegexParser;
import com.oracle.truffle.regex.tregex.parser.RegexValidator;

public final class PythonFlavor extends RegexFlavor {
   public static final PythonFlavor INSTANCE = new PythonFlavor(PythonREMode.None);
   public static final PythonFlavor STR_INSTANCE = new PythonFlavor(PythonREMode.Str);
   public static final PythonFlavor BYTES_INSTANCE = new PythonFlavor(PythonREMode.Bytes);
   private final PythonREMode mode;

   private PythonFlavor(PythonREMode mode) {
      super(61);
      this.mode = mode;
   }

   @Override
   public RegexValidator createValidator(RegexSource source) {
      return PythonRegexParser.createValidator(source, this.mode);
   }

   @Override
   public RegexParser createParser(RegexLanguage language, RegexSource source, CompilationBuffer compilationBuffer) {
      return PythonRegexParser.createParser(language, source, compilationBuffer, this.mode);
   }
}
