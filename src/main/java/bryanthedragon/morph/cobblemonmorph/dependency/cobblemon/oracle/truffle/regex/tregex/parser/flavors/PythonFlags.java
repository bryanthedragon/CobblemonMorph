package com.oracle.truffle.regex.tregex.parser.flavors;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.UnknownIdentifierException;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.library.ExportMessage;
import com.oracle.truffle.regex.AbstractConstantKeysObject;
import com.oracle.truffle.regex.RegexSource;
import com.oracle.truffle.regex.RegexSyntaxException;
import com.oracle.truffle.regex.util.TBitSet;
import com.oracle.truffle.regex.util.TruffleReadOnlyKeysArray;

@ExportLibrary(InteropLibrary.class)
public final class PythonFlags extends AbstractConstantKeysObject {
   private static final String PROP_ASCII = "ASCII";
   private static final String PROP_DOTALL = "DOTALL";
   private static final String PROP_IGNORECASE = "IGNORECASE";
   private static final String PROP_LOCALE = "LOCALE";
   private static final String PROP_MULTILINE = "MULTILINE";
   private static final String PROP_TEMPLATE = "TEMPLATE";
   private static final String PROP_UNICODE = "UNICODE";
   private static final String PROP_VERBOSE = "VERBOSE";
   private static final TruffleReadOnlyKeysArray KEYS = new TruffleReadOnlyKeysArray(
      "ASCII", "DOTALL", "IGNORECASE", "LOCALE", "MULTILINE", "TEMPLATE", "UNICODE", "VERBOSE"
   );
   private final int value;
   private static final TBitSet ALL_FLAG_CHARS = TBitSet.valueOf(76, 97, 105, 109, 115, 116, 117, 120);
   private static final TBitSet TYPE_FLAG_CHARS = TBitSet.valueOf(76, 97, 117);
   private static final String FLAGS = "iLmsxatu";
   private static final int FLAG_IGNORE_CASE = 1;
   private static final int FLAG_LOCALE = 2;
   private static final int FLAG_MULTILINE = 4;
   private static final int FLAG_DOT_ALL = 8;
   private static final int FLAG_VERBOSE = 16;
   private static final int FLAG_ASCII = 32;
   private static final int FLAG_TEMPLATE = 64;
   private static final int FLAG_UNICODE = 128;
   private static final int[] FLAG_LOOKUP = new int[]{32, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 2, 4, 0, 0, 0, 0, 0, 8, 64, 128, 0, 0, 16};
   private static final int TYPE_FLAGS = 162;
   private static final int GLOBAL_FLAGS = 64;
   public static final PythonFlags EMPTY_INSTANCE = new PythonFlags("");
   public static final PythonFlags TYPE_FLAGS_INSTANCE = new PythonFlags(162);

   public PythonFlags(String source) {
      int bits = 0;

      for (int i = 0; i < source.length(); i++) {
         bits |= maskForFlag(source.charAt(i));
      }

      this.value = bits;
   }

   private PythonFlags(int value) {
      this.value = value;
   }

   private static int maskForFlag(int flagChar) {
      assert ALL_FLAG_CHARS.get(flagChar);

      return FLAG_LOOKUP[(flagChar | 32) - 97];
   }

   private boolean hasFlag(int flag) {
      return (this.value & flag) != 0;
   }

   public boolean isIgnoreCase() {
      return this.hasFlag(1);
   }

   public boolean isLocale() {
      return this.hasFlag(2);
   }

   public boolean isMultiLine() {
      return this.hasFlag(4);
   }

   public boolean isDotAll() {
      return this.hasFlag(8);
   }

   public boolean isVerbose() {
      return this.hasFlag(16);
   }

   public boolean isAscii() {
      return this.hasFlag(32);
   }

   public boolean isTemplate() {
      return this.hasFlag(64);
   }

   public boolean isUnicodeExplicitlySet() {
      return this.hasFlag(128);
   }

   public boolean isUnicode(PythonREMode mode) {
      switch (mode) {
         case Str:
            return this.isUnicodeExplicitlySet() || !this.isAscii();
         case Bytes:
            return this.isUnicodeExplicitlySet();
         case None:
         default:
            throw CompilerDirectives.shouldNotReachHere();
      }
   }

   public PythonFlags addFlag(int flagChar) {
      return new PythonFlags(this.value | maskForFlag(flagChar));
   }

   public PythonFlags addFlags(PythonFlags otherFlags) {
      return new PythonFlags(this.value | otherFlags.value);
   }

   public PythonFlags delFlags(PythonFlags otherFlags) {
      return new PythonFlags(this.value & ~otherFlags.value);
   }

   public PythonFlags fixFlags(RegexSource source, PythonREMode mode) {
      switch (mode) {
         case Str:
            if (this.isLocale()) {
               throw RegexSyntaxException.createFlags(source, "cannot use LOCALE flag with a str pattern");
            } else if (this.isAscii() && this.isUnicodeExplicitlySet()) {
               throw RegexSyntaxException.createFlags(source, "ASCII and UNICODE flags are incompatible");
            } else {
               if (!this.isAscii() && !this.isUnicodeExplicitlySet()) {
                  return new PythonFlags(this.value | 128);
               }

               return this;
            }
         case Bytes:
            if (this.isUnicodeExplicitlySet()) {
               throw RegexSyntaxException.createFlags(source, "cannot use UNICODE flag with a bytes pattern");
            } else {
               if (this.isAscii() && this.isLocale()) {
                  throw RegexSyntaxException.createFlags(source, "ASCII and LOCALE flags are incompatible");
               }

               return this;
            }
         default:
            throw CompilerDirectives.shouldNotReachHere();
      }
   }

   public static boolean isValidFlagChar(int candidateChar) {
      return ALL_FLAG_CHARS.get(candidateChar);
   }

   public static boolean isTypeFlagChar(int candidateChar) {
      return TYPE_FLAG_CHARS.get(candidateChar);
   }

   public int numberOfTypeFlags() {
      return Integer.bitCount(this.value & 162);
   }

   public boolean includesGlobalFlags() {
      return (this.value & 64) != 0;
   }

   public boolean overlaps(PythonFlags otherFlags) {
      return (this.value & otherFlags.value) != 0;
   }

   @Override
   public boolean equals(Object other) {
      return other instanceof PythonFlags && this.value == ((PythonFlags)other).value;
   }

   @Override
   public int hashCode() {
      return this.value;
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public String toString() {
      char[] out = new char[Integer.bitCount(this.value)];
      int iOut = 0;

      for (int i = 0; i < "iLmsxatu".length(); i++) {
         char flag = "iLmsxatu".charAt(i);
         if (this.hasFlag(maskForFlag(flag))) {
            out[iOut++] = flag;
         }
      }

      return new String(out);
   }

   @CompilerDirectives.TruffleBoundary
   @ExportMessage
   @Override
   public Object toDisplayString(boolean allowSideEffects) {
      return "TRegexPythonFlags{flags=" + this.toString() + "}";
   }

   @Override
   public TruffleReadOnlyKeysArray getKeys() {
      return KEYS;
   }

   @Override
   public boolean isMemberReadableImpl(String symbol) {
      switch (symbol) {
         case "ASCII":
         case "DOTALL":
         case "IGNORECASE":
         case "LOCALE":
         case "MULTILINE":
         case "TEMPLATE":
         case "UNICODE":
         case "VERBOSE":
            return true;
         default:
            return false;
      }
   }

   @Override
   public Object readMemberImpl(String symbol) throws UnknownIdentifierException {
      switch (symbol) {
         case "ASCII":
            return this.isAscii();
         case "DOTALL":
            return this.isDotAll();
         case "IGNORECASE":
            return this.isIgnoreCase();
         case "LOCALE":
            return this.isLocale();
         case "MULTILINE":
            return this.isMultiLine();
         case "TEMPLATE":
            return this.isTemplate();
         case "UNICODE":
            return this.isUnicodeExplicitlySet();
         case "VERBOSE":
            return this.isVerbose();
         default:
            CompilerDirectives.transferToInterpreterAndInvalidate();
            throw UnknownIdentifierException.create(symbol);
      }
   }
}
