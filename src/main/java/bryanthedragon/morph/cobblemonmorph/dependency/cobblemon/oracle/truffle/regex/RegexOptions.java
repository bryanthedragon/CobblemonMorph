package com.oracle.truffle.regex;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.source.Source;
import com.oracle.truffle.regex.tregex.parser.flavors.ECMAScriptFlavor;
import com.oracle.truffle.regex.tregex.parser.flavors.PythonFlavor;
import com.oracle.truffle.regex.tregex.parser.flavors.PythonMethod;
import com.oracle.truffle.regex.tregex.parser.flavors.RegexFlavor;
import com.oracle.truffle.regex.tregex.parser.flavors.RubyFlavor;
import com.oracle.truffle.regex.tregex.string.Encodings;
import java.util.Arrays;
import java.util.Objects;

public final class RegexOptions {
   private static final int U180E_WHITESPACE = 1;
   public static final String U180E_WHITESPACE_NAME = "U180EWhitespace";
   private static final int REGRESSION_TEST_MODE = 2;
   public static final String REGRESSION_TEST_MODE_NAME = "RegressionTestMode";
   private static final int DUMP_AUTOMATA = 4;
   public static final String DUMP_AUTOMATA_NAME = "DumpAutomata";
   private static final int STEP_EXECUTION = 8;
   public static final String STEP_EXECUTION_NAME = "StepExecution";
   private static final int ALWAYS_EAGER = 16;
   public static final String ALWAYS_EAGER_NAME = "AlwaysEager";
   private static final int UTF_16_EXPLODE_ASTRAL_SYMBOLS = 32;
   public static final String UTF_16_EXPLODE_ASTRAL_SYMBOLS_NAME = "UTF16ExplodeAstralSymbols";
   private static final int VALIDATE = 64;
   public static final String VALIDATE_NAME = "Validate";
   private static final int IGNORE_ATOMIC_GROUPS = 128;
   public static final String IGNORE_ATOMIC_GROUPS_NAME = "IgnoreAtomicGroups";
   private static final int GENERATE_DFA_IMMEDIATELY = 256;
   private static final String GENERATE_DFA_IMMEDIATELY_NAME = "GenerateDFAImmediately";
   private static final int BOOLEAN_MATCH = 512;
   private static final String BOOLEAN_MATCH_NAME = "BooleanMatch";
   private static final int MUST_ADVANCE = 1024;
   public static final String MUST_ADVANCE_NAME = "MustAdvance";
   public static final String FLAVOR_NAME = "Flavor";
   public static final String FLAVOR_PYTHON = "Python";
   public static final String FLAVOR_PYTHON_STR = "PythonStr";
   public static final String FLAVOR_PYTHON_BYTES = "PythonBytes";
   public static final String FLAVOR_RUBY = "Ruby";
   public static final String FLAVOR_ECMASCRIPT = "ECMAScript";
   private static final String[] FLAVOR_OPTIONS = new String[]{"Python", "PythonStr", "PythonBytes", "Ruby", "ECMAScript"};
   public static final String ENCODING_NAME = "Encoding";
   public static final String PYTHON_METHOD_NAME = "PythonMethod";
   public static final String PYTHON_METHOD_SEARCH = "search";
   public static final String PYTHON_METHOD_MATCH = "match";
   public static final String PYTHON_METHOD_FULLMATCH = "fullmatch";
   private static final String[] PYTHON_METHOD_OPTIONS = new String[]{"search", "match", "fullmatch"};
   public static final RegexOptions DEFAULT = new RegexOptions(0, ECMAScriptFlavor.INSTANCE, Encodings.UTF_16_RAW, null);
   private final int options;
   private final RegexFlavor flavor;
   private final Encodings.Encoding encoding;
   private final PythonMethod pythonMethod;

   private RegexOptions(int options, RegexFlavor flavor, Encodings.Encoding encoding, PythonMethod pythonMethod) {
      this.options = options;
      this.flavor = flavor;
      this.encoding = encoding;
      this.pythonMethod = pythonMethod;
   }

   public static RegexOptions.Builder builder(Source source, String sourceString) {
      return new RegexOptions.Builder(source, sourceString);
   }

   private boolean isBitSet(int bit) {
      return (this.options & bit) != 0;
   }

   public boolean isU180EWhitespace() {
      return this.isBitSet(1);
   }

   public boolean isRegressionTestMode() {
      return this.isBitSet(2);
   }

   public boolean isDumpAutomata() {
      return this.isBitSet(4);
   }

   public boolean isDumpAutomataWithSourceSections() {
      return this.isDumpAutomata() && this.getFlavor() == ECMAScriptFlavor.INSTANCE;
   }

   public boolean isStepExecution() {
      return this.isBitSet(8);
   }

   public boolean isGenerateDFAImmediately() {
      return this.isBitSet(256);
   }

   public boolean isBooleanMatch() {
      return this.isBitSet(512);
   }

   public boolean isAlwaysEager() {
      return this.isBitSet(16);
   }

   public boolean isUTF16ExplodeAstralSymbols() {
      return this.isBitSet(32);
   }

   public boolean isValidate() {
      return this.isBitSet(64);
   }

   public boolean isIgnoreAtomicGroups() {
      return this.isBitSet(128);
   }

   public boolean isMustAdvance() {
      return this.isBitSet(1024);
   }

   public RegexFlavor getFlavor() {
      return this.flavor;
   }

   public Encodings.Encoding getEncoding() {
      return this.encoding;
   }

   public PythonMethod getPythonMethod() {
      return this.pythonMethod;
   }

   public RegexOptions withEncoding(Encodings.Encoding newEnc) {
      return newEnc == this.encoding ? this : new RegexOptions(this.options, this.flavor, newEnc, this.pythonMethod);
   }

   public RegexOptions withoutPythonMethod() {
      return this.pythonMethod == null ? this : new RegexOptions(this.options, this.flavor, this.encoding, null);
   }

   public RegexOptions withBooleanMatch() {
      return new RegexOptions(this.options | 512, this.flavor, this.encoding, this.pythonMethod);
   }

   public RegexOptions withoutBooleanMatch() {
      return new RegexOptions(this.options & -513, this.flavor, this.encoding, this.pythonMethod);
   }

   @Override
   public int hashCode() {
      int prime = 31;
      int hash = this.options;
      hash = 31 * hash + Objects.hashCode(this.flavor);
      hash = 31 * hash + this.encoding.hashCode();
      return 31 * hash + Objects.hashCode(this.pythonMethod);
   }

   @Override
   public boolean equals(Object obj) {
      if (obj == this) {
         return true;
      } else if (!(obj instanceof RegexOptions)) {
         return false;
      } else {
         RegexOptions other = (RegexOptions)obj;
         return this.options == other.options && this.flavor == other.flavor && this.encoding == other.encoding && this.pythonMethod == other.pythonMethod;
      }
   }

   @Override
   public String toString() {
      StringBuilder sb = new StringBuilder();
      if (this.isU180EWhitespace()) {
         sb.append("U180EWhitespace=true,");
      }

      if (this.isRegressionTestMode()) {
         sb.append("RegressionTestMode=true,");
      }

      if (this.isDumpAutomata()) {
         sb.append("DumpAutomata=true,");
      }

      if (this.isStepExecution()) {
         sb.append("StepExecution=true,");
      }

      if (this.isAlwaysEager()) {
         sb.append("AlwaysEager=true,");
      }

      if (this.isUTF16ExplodeAstralSymbols()) {
         sb.append("UTF16ExplodeAstralSymbols=true,");
      }

      if (this.isValidate()) {
         sb.append("Validate=true,");
      }

      if (this.isIgnoreAtomicGroups()) {
         sb.append("IgnoreAtomicGroups=true,");
      }

      if (this.isGenerateDFAImmediately()) {
         sb.append("GenerateDFAImmediately=true,");
      }

      if (this.isBooleanMatch()) {
         sb.append("BooleanMatch=true,");
      }

      if (this.isMustAdvance()) {
         sb.append("MustAdvance=true,");
      }

      if (this.flavor == PythonFlavor.STR_INSTANCE) {
         sb.append("Flavor=PythonStr,");
      } else if (this.flavor == PythonFlavor.BYTES_INSTANCE) {
         sb.append("Flavor=PythonBytes,");
      } else if (this.flavor == RubyFlavor.INSTANCE) {
         sb.append("Flavor=Ruby,");
      }

      sb.append("Encoding=" + this.encoding.getName() + ",");
      if (this.pythonMethod == PythonMethod.search) {
         sb.append("PythonMethod=search,");
      } else if (this.pythonMethod == PythonMethod.match) {
         sb.append("PythonMethod=match,");
      } else if (this.pythonMethod == PythonMethod.fullmatch) {
         sb.append("PythonMethod=fullmatch,");
      }

      return sb.toString();
   }

   public static final class Builder {
      private final Source source;
      private final String src;
      private int options;
      private RegexFlavor flavor;
      private Encodings.Encoding encoding = Encodings.UTF_16_RAW;
      private PythonMethod pythonMethod;

      private Builder(Source source, String sourceString) {
         this.source = source;
         this.src = sourceString;
         this.options = 0;
         this.flavor = ECMAScriptFlavor.INSTANCE;
      }

      @CompilerDirectives.TruffleBoundary
      public int parseOptions() throws RegexSyntaxException {
         int i = 0;

         while (i < this.src.length()) {
            switch (this.src.charAt(i)) {
               case ',':
                  i++;
                  break;
               case '-':
               case '.':
               case '0':
               case '1':
               case '2':
               case '3':
               case '4':
               case '5':
               case '6':
               case '7':
               case '8':
               case '9':
               case ':':
               case ';':
               case '<':
               case '=':
               case '>':
               case '?':
               case '@':
               case 'C':
               case 'H':
               case 'J':
               case 'K':
               case 'L':
               case 'N':
               case 'O':
               case 'Q':
               case 'T':
               default:
                  throw this.optionsSyntaxErrorUnexpectedKey(i);
               case '/':
                  return i;
               case 'A':
                  i = this.parseBooleanOption(i, "AlwaysEager", 16);
                  break;
               case 'B':
                  i = this.parseBooleanOption(i, "BooleanMatch", 512);
                  break;
               case 'D':
                  i = this.parseBooleanOption(i, "DumpAutomata", 4);
                  break;
               case 'E':
                  i = this.parseEncoding(i);
                  break;
               case 'F':
                  i = this.parseFlavor(i);
                  break;
               case 'G':
                  i = this.parseBooleanOption(i, "GenerateDFAImmediately", 256);
                  break;
               case 'I':
                  i = this.parseBooleanOption(i, "IgnoreAtomicGroups", 128);
                  break;
               case 'M':
                  i = this.parseBooleanOption(i, "MustAdvance", 1024);
                  break;
               case 'P':
                  i = this.parsePythonMethod(i);
                  break;
               case 'R':
                  i = this.parseBooleanOption(i, "RegressionTestMode", 2);
                  break;
               case 'S':
                  i = this.parseBooleanOption(i, "StepExecution", 8);
                  break;
               case 'U':
                  if (i + 1 >= this.src.length()) {
                     throw this.optionsSyntaxErrorUnexpectedKey(i);
                  }

                  switch (this.src.charAt(i + 1)) {
                     case '1':
                        i = this.parseBooleanOption(i, "U180EWhitespace", 1);
                        continue;
                     case 'T':
                        i = this.parseBooleanOption(i, "UTF16ExplodeAstralSymbols", 32);
                        continue;
                     default:
                        throw this.optionsSyntaxErrorUnexpectedKey(i);
                  }
               case 'V':
                  i = this.parseBooleanOption(i, "Validate", 64);
            }
         }

         return i;
      }

      private int expectOptionName(int i, String key) {
         if (this.src.regionMatches(i, key, 0, key.length()) && this.src.charAt(i + key.length()) == '=') {
            return i + key.length() + 1;
         } else {
            throw this.optionsSyntaxErrorUnexpectedKey(i);
         }
      }

      private int expectValue(int i, String value, String... expected) {
         if (!this.src.regionMatches(i, value, 0, value.length())) {
            throw this.optionsSyntaxErrorUnexpectedValue(i, expected);
         } else {
            return i + value.length();
         }
      }

      private int parseBooleanOption(int i, String key, int flag) throws RegexSyntaxException {
         int iVal = this.expectOptionName(i, key);
         if (this.src.regionMatches(iVal, "true", 0, "true".length())) {
            this.options |= flag;
            return iVal + "true".length();
         } else if (!this.src.regionMatches(iVal, "false", 0, "false".length())) {
            throw this.optionsSyntaxErrorUnexpectedValue(iVal, "true", "false");
         } else {
            return iVal + "false".length();
         }
      }

      private int parseFlavor(int i) throws RegexSyntaxException {
         int iVal = this.expectOptionName(i, "Flavor");
         if (iVal >= this.src.length()) {
            throw this.optionsSyntaxErrorUnexpectedValue(iVal, RegexOptions.FLAVOR_OPTIONS);
         } else {
            switch (this.src.charAt(iVal)) {
               case 'E':
                  this.flavor = ECMAScriptFlavor.INSTANCE;
                  return this.expectValue(iVal, "ECMAScript", RegexOptions.FLAVOR_OPTIONS);
               case 'P':
                  if (iVal + 6 >= this.src.length()) {
                     this.flavor = PythonFlavor.INSTANCE;
                     return this.expectValue(iVal, "Python", RegexOptions.FLAVOR_OPTIONS);
                  } else {
                     switch (this.src.charAt(iVal + 6)) {
                        case 'B':
                           this.flavor = PythonFlavor.BYTES_INSTANCE;
                           this.encoding = Encodings.LATIN_1;
                           return this.expectValue(iVal, "PythonBytes", RegexOptions.FLAVOR_OPTIONS);
                        case 'S':
                           this.flavor = PythonFlavor.STR_INSTANCE;
                           this.encoding = Encodings.UTF_16;
                           return this.expectValue(iVal, "PythonStr", RegexOptions.FLAVOR_OPTIONS);
                        default:
                           throw this.optionsSyntaxErrorUnexpectedValue(iVal, RegexOptions.FLAVOR_OPTIONS);
                     }
                  }
               case 'R':
                  this.flavor = RubyFlavor.INSTANCE;
                  return this.expectValue(iVal, "Ruby", RegexOptions.FLAVOR_OPTIONS);
               default:
                  throw this.optionsSyntaxErrorUnexpectedValue(iVal, RegexOptions.FLAVOR_OPTIONS);
            }
         }
      }

      private int parseEncoding(int i) throws RegexSyntaxException {
         int iVal = this.expectOptionName(i, "Encoding");
         if (iVal >= this.src.length()) {
            throw this.optionsSyntaxErrorUnexpectedValue(iVal, Encodings.ALL_NAMES);
         } else {
            switch (this.src.charAt(iVal)) {
               case 'A':
                  this.encoding = Encodings.ASCII;
                  return this.expectValue(iVal, Encodings.ASCII.getName(), Encodings.ALL_NAMES);
               case 'B':
                  this.encoding = Encodings.BYTES;
                  return this.expectValue(iVal, "BYTES", Encodings.ALL_NAMES);
               case 'L':
                  this.encoding = Encodings.LATIN_1;
                  return this.expectValue(iVal, Encodings.LATIN_1.getName(), Encodings.ALL_NAMES);
               case 'U':
                  if (iVal + 4 >= this.src.length()) {
                     throw this.optionsSyntaxErrorUnexpectedValue(iVal, RegexOptions.FLAVOR_OPTIONS);
                  } else {
                     switch (this.src.charAt(iVal + 4)) {
                        case '1':
                           this.encoding = Encodings.UTF_16;
                           return this.expectValue(iVal, Encodings.UTF_16.getName(), Encodings.ALL_NAMES);
                        case '3':
                           this.encoding = Encodings.UTF_32;
                           return this.expectValue(iVal, Encodings.UTF_32.getName(), Encodings.ALL_NAMES);
                        case '8':
                           this.encoding = Encodings.UTF_8;
                           return this.expectValue(iVal, Encodings.UTF_8.getName(), Encodings.ALL_NAMES);
                        default:
                           throw this.optionsSyntaxErrorUnexpectedValue(iVal, Encodings.ALL_NAMES);
                     }
                  }
               default:
                  throw this.optionsSyntaxErrorUnexpectedValue(iVal, Encodings.ALL_NAMES);
            }
         }
      }

      private int parsePythonMethod(int i) throws RegexSyntaxException {
         int iVal = this.expectOptionName(i, "PythonMethod");
         if (iVal >= this.src.length()) {
            throw this.optionsSyntaxErrorUnexpectedValue(iVal, RegexOptions.PYTHON_METHOD_OPTIONS);
         } else {
            switch (this.src.charAt(iVal)) {
               case 'f':
                  this.pythonMethod = PythonMethod.fullmatch;
                  return this.expectValue(iVal, "fullmatch", RegexOptions.PYTHON_METHOD_OPTIONS);
               case 'm':
                  this.pythonMethod = PythonMethod.match;
                  return this.expectValue(iVal, "match", RegexOptions.PYTHON_METHOD_OPTIONS);
               case 's':
                  this.pythonMethod = PythonMethod.search;
                  return this.expectValue(iVal, "search", RegexOptions.PYTHON_METHOD_OPTIONS);
               default:
                  throw this.optionsSyntaxErrorUnexpectedValue(iVal, RegexOptions.PYTHON_METHOD_OPTIONS);
            }
         }
      }

      @CompilerDirectives.TruffleBoundary
      private RegexSyntaxException optionsSyntaxErrorUnexpectedKey(int i) {
         int eqlPos = this.src.indexOf(61, i);
         return this.optionsSyntaxError(String.format("unexpected option '%s'", this.src.substring(i, eqlPos < 0 ? this.src.length() : eqlPos)), i);
      }

      @CompilerDirectives.TruffleBoundary
      private RegexSyntaxException optionsSyntaxErrorUnexpectedValue(int i, String... expected) {
         int commaPos = this.src.indexOf(44, i);
         String value = this.src.substring(i, commaPos < 0 ? this.src.length() : commaPos);
         return this.optionsSyntaxError(String.format("unexpected value '%s', expected one of %s", value, Arrays.toString((Object[])expected)), i);
      }

      @CompilerDirectives.TruffleBoundary
      private RegexSyntaxException optionsSyntaxError(String msg, int i) {
         return RegexSyntaxException.createOptions(this.source, String.format("Invalid options syntax in '%s': %s", this.src, msg), i);
      }

      private boolean isBitSet(int bit) {
         return (this.options & bit) != 0;
      }

      public RegexOptions.Builder u180eWhitespace(boolean enabled) {
         this.updateOption(enabled, 1);
         return this;
      }

      public RegexOptions.Builder regressionTestMode(boolean enabled) {
         this.updateOption(enabled, 2);
         return this;
      }

      public RegexOptions.Builder dumpAutomata(boolean enabled) {
         this.updateOption(enabled, 4);
         return this;
      }

      public RegexOptions.Builder stepExecution(boolean enabled) {
         this.updateOption(enabled, 8);
         return this;
      }

      public RegexOptions.Builder alwaysEager(boolean enabled) {
         this.updateOption(enabled, 16);
         return this;
      }

      public RegexOptions.Builder utf16ExplodeAstralSymbols(boolean enabled) {
         this.updateOption(enabled, 32);
         return this;
      }

      public boolean isUtf16ExplodeAstralSymbols() {
         return this.isBitSet(32);
      }

      public RegexOptions.Builder validate(boolean enabled) {
         this.updateOption(enabled, 64);
         return this;
      }

      public RegexOptions.Builder ignoreAtomicGroups(boolean enabled) {
         this.updateOption(enabled, 128);
         return this;
      }

      public RegexOptions.Builder generateDFAImmediately(boolean enabled) {
         this.updateOption(enabled, 256);
         return this;
      }

      public RegexOptions.Builder booleanMatch(boolean enabled) {
         this.updateOption(enabled, 512);
         return this;
      }

      public RegexOptions.Builder mustAdvance(boolean enabled) {
         this.updateOption(enabled, 1024);
         return this;
      }

      public RegexOptions.Builder flavor(RegexFlavor flavor) {
         this.flavor = flavor;
         if (flavor == PythonFlavor.BYTES_INSTANCE) {
            this.encoding = Encodings.LATIN_1;
         }

         if (flavor == PythonFlavor.STR_INSTANCE) {
            this.encoding = Encodings.UTF_16;
         }

         return this;
      }

      public RegexFlavor getFlavor() {
         return this.flavor;
      }

      public RegexOptions.Builder encoding(Encodings.Encoding encoding) {
         this.encoding = encoding;
         return this;
      }

      public Encodings.Encoding getEncoding() {
         return this.encoding;
      }

      public RegexOptions.Builder pythonMethod(PythonMethod pythonMethod) {
         this.pythonMethod = pythonMethod;
         return this;
      }

      public PythonMethod getPythonMethod() {
         return this.pythonMethod;
      }

      public RegexOptions build() {
         return new RegexOptions(this.options, this.flavor, this.encoding, this.pythonMethod);
      }

      private void updateOption(boolean enabled, int bitMask) {
         if (enabled) {
            this.options |= bitMask;
         } else {
            this.options &= ~bitMask;
         }
      }
   }
}
