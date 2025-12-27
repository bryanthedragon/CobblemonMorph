package com.oracle.truffle.regex;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.UnknownIdentifierException;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.library.ExportMessage;
import com.oracle.truffle.regex.tregex.util.json.Json;
import com.oracle.truffle.regex.tregex.util.json.JsonConvertible;
import com.oracle.truffle.regex.tregex.util.json.JsonValue;
import com.oracle.truffle.regex.util.TruffleReadOnlyKeysArray;

@ExportLibrary(InteropLibrary.class)
public final class RegexFlags extends AbstractConstantKeysObject implements JsonConvertible {
   private static final String PROP_SOURCE = "source";
   private static final String PROP_IGNORE_CASE = "ignoreCase";
   private static final String PROP_MULTILINE = "multiline";
   private static final String PROP_STICKY = "sticky";
   private static final String PROP_GLOBAL = "global";
   private static final String PROP_UNICODE = "unicode";
   private static final String PROP_DOT_ALL = "dotAll";
   private static final String PROP_HAS_INDICES = "hasIndices";
   private static final TruffleReadOnlyKeysArray KEYS = new TruffleReadOnlyKeysArray(
      "source", "ignoreCase", "multiline", "sticky", "global", "unicode", "dotAll", "hasIndices"
   );
   private static final int NONE = 0;
   private static final int IGNORE_CASE = 1;
   private static final int MULTILINE = 2;
   private static final int STICKY = 4;
   private static final int GLOBAL = 8;
   private static final int UNICODE = 16;
   private static final int DOT_ALL = 32;
   private static final int HAS_INDICES = 64;
   public static final RegexFlags DEFAULT = new RegexFlags("", 0);
   private final String source;
   private final int value;

   private RegexFlags(String source, int value) {
      this.source = source;
      this.value = value;
   }

   public static RegexFlags.Builder builder() {
      return new RegexFlags.Builder();
   }

   @CompilerDirectives.TruffleBoundary
   public static RegexFlags parseFlags(RegexSource source) throws RegexSyntaxException {
      String flagsStr = source.getFlags();
      if (flagsStr.isEmpty()) {
         return DEFAULT;
      } else {
         int flags = 0;

         for (int i = 0; i < flagsStr.length(); i++) {
            char ch = flagsStr.charAt(i);
            switch (ch) {
               case 'd':
                  flags = addFlag(source, flags, i, 64);
                  break;
               case 'e':
               case 'f':
               case 'h':
               case 'j':
               case 'k':
               case 'l':
               case 'n':
               case 'o':
               case 'p':
               case 'q':
               case 'r':
               case 't':
               case 'v':
               case 'w':
               case 'x':
               default:
                  throw RegexSyntaxException.createFlags(source, "Unsupported regex flag", i);
               case 'g':
                  flags = addFlag(source, flags, i, 8);
                  break;
               case 'i':
                  flags = addFlag(source, flags, i, 1);
                  break;
               case 'm':
                  flags = addFlag(source, flags, i, 2);
                  break;
               case 's':
                  flags = addFlag(source, flags, i, 32);
                  break;
               case 'u':
                  flags = addFlag(source, flags, i, 16);
                  break;
               case 'y':
                  flags = addFlag(source, flags, i, 4);
            }
         }

         return new RegexFlags(flagsStr, flags);
      }
   }

   private static int addFlag(RegexSource source, int flags, int i, int flag) {
      if ((flags & flag) != 0) {
         throw RegexSyntaxException.createFlags(source, "Repeated regex flag", i);
      } else {
         return flags | flag;
      }
   }

   public String getSource() {
      return this.source;
   }

   public boolean isIgnoreCase() {
      return this.isSet(1);
   }

   public boolean isMultiline() {
      return this.isSet(2);
   }

   public boolean isSticky() {
      return this.isSet(4);
   }

   public boolean isGlobal() {
      return this.isSet(8);
   }

   public boolean isUnicode() {
      return this.isSet(16);
   }

   public boolean isDotAll() {
      return this.isSet(32);
   }

   public boolean hasIndices() {
      return this.isSet(64);
   }

   public boolean isNone() {
      return this.value == 0;
   }

   private boolean isSet(int flag) {
      return (this.value & flag) != 0;
   }

   @Override
   public String toString() {
      return this.source;
   }

   @Override
   public int hashCode() {
      return this.value;
   }

   @Override
   public boolean equals(Object obj) {
      return obj == this || obj instanceof RegexFlags && this.value == ((RegexFlags)obj).value;
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public JsonValue toJson() {
      return Json.obj(
         Json.prop("ignoreCase", this.isIgnoreCase()),
         Json.prop("multiline", this.isMultiline()),
         Json.prop("global", this.isGlobal()),
         Json.prop("sticky", this.isSticky()),
         Json.prop("unicode", this.isUnicode()),
         Json.prop("dotAll", this.isDotAll()),
         Json.prop("hasIndices", this.hasIndices())
      );
   }

   @Override
   public TruffleReadOnlyKeysArray getKeys() {
      return KEYS;
   }

   @Override
   public boolean isMemberReadableImpl(String symbol) {
      switch (symbol) {
         case "source":
         case "ignoreCase":
         case "multiline":
         case "sticky":
         case "global":
         case "unicode":
         case "dotAll":
         case "hasIndices":
            return true;
         default:
            return false;
      }
   }

   @Override
   public Object readMemberImpl(String symbol) throws UnknownIdentifierException {
      switch (symbol) {
         case "source":
            return this.getSource();
         case "ignoreCase":
            return this.isIgnoreCase();
         case "multiline":
            return this.isMultiline();
         case "sticky":
            return this.isSticky();
         case "global":
            return this.isGlobal();
         case "unicode":
            return this.isUnicode();
         case "dotAll":
            return this.isDotAll();
         case "hasIndices":
            return this.hasIndices();
         default:
            CompilerDirectives.transferToInterpreterAndInvalidate();
            throw UnknownIdentifierException.create(symbol);
      }
   }

   @CompilerDirectives.TruffleBoundary
   @ExportMessage
   @Override
   public Object toDisplayString(boolean allowSideEffects) {
      return "TRegexJSFlags{flags=" + this.toString() + "}";
   }

   public static final class Builder {
      private int value;

      private Builder() {
      }

      public RegexFlags.Builder ignoreCase(boolean enabled) {
         this.updateFlag(enabled, 1);
         return this;
      }

      public RegexFlags.Builder multiline(boolean enabled) {
         this.updateFlag(enabled, 2);
         return this;
      }

      public RegexFlags.Builder sticky(boolean enabled) {
         this.updateFlag(enabled, 4);
         return this;
      }

      public RegexFlags.Builder global(boolean enabled) {
         this.updateFlag(enabled, 8);
         return this;
      }

      public RegexFlags.Builder unicode(boolean enabled) {
         this.updateFlag(enabled, 16);
         return this;
      }

      public RegexFlags.Builder dotAll(boolean enabled) {
         this.updateFlag(enabled, 32);
         return this;
      }

      public RegexFlags.Builder hasIndices(boolean enabled) {
         this.updateFlag(enabled, 64);
         return this;
      }

      @CompilerDirectives.TruffleBoundary
      public RegexFlags build() {
         return new RegexFlags(this.generateSource(), this.value);
      }

      private void updateFlag(boolean enabled, int bitMask) {
         if (enabled) {
            this.value |= bitMask;
         } else {
            this.value &= ~bitMask;
         }
      }

      private boolean isSet(int flag) {
         return (this.value & flag) != 0;
      }

      private String generateSource() {
         StringBuilder sb = new StringBuilder(7);
         if (this.isSet(1)) {
            sb.append("i");
         }

         if (this.isSet(2)) {
            sb.append("m");
         }

         if (this.isSet(4)) {
            sb.append("y");
         }

         if (this.isSet(8)) {
            sb.append("g");
         }

         if (this.isSet(16)) {
            sb.append("u");
         }

         if (this.isSet(32)) {
            sb.append("s");
         }

         if (this.isSet(64)) {
            sb.append("d");
         }

         return sb.toString();
      }
   }
}
