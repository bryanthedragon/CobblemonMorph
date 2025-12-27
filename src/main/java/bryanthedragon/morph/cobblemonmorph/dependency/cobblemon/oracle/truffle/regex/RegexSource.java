package com.oracle.truffle.regex;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.source.Source;
import com.oracle.truffle.regex.tregex.string.Encodings;
import com.oracle.truffle.regex.tregex.util.DebugUtil;
import com.oracle.truffle.regex.tregex.util.json.Json;
import com.oracle.truffle.regex.tregex.util.json.JsonConvertible;
import com.oracle.truffle.regex.tregex.util.json.JsonValue;

public final class RegexSource implements JsonConvertible {
   private final String pattern;
   private final String flags;
   private final RegexOptions options;
   private final Source source;
   private boolean hashComputed = false;
   private int cachedHash;

   public RegexSource(String pattern, String flags, RegexOptions options, Source source) {
      this.pattern = pattern;
      this.flags = flags;
      this.options = options;
      this.source = source;
   }

   public String getPattern() {
      return this.pattern;
   }

   public String getFlags() {
      return this.flags;
   }

   public RegexOptions getOptions() {
      return this.options;
   }

   public Encodings.Encoding getEncoding() {
      return this.options.getEncoding();
   }

   public Source getSource() {
      return this.source;
   }

   public RegexSource withBooleanMatch() {
      return new RegexSource(this.pattern, this.flags, this.options.withBooleanMatch(), this.source);
   }

   public RegexSource withoutBooleanMatch() {
      return new RegexSource(this.pattern, this.flags, this.options.withoutBooleanMatch(), this.source);
   }

   @Override
   public int hashCode() {
      if (!this.hashComputed) {
         int prime = 31;
         int hash = 1;
         hash = 31 * hash + this.pattern.hashCode();
         hash = 31 * hash + this.flags.hashCode();
         hash = 31 * hash + this.options.hashCode();
         this.cachedHash = hash;
         this.hashComputed = true;
      }

      return this.cachedHash;
   }

   @Override
   public boolean equals(Object obj) {
      return this == obj
         || obj instanceof RegexSource
            && this.pattern.equals(((RegexSource)obj).pattern)
            && this.flags.equals(((RegexSource)obj).flags)
            && this.options.equals(((RegexSource)obj).options);
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public String toString() {
      return "/" + this.pattern + "/" + this.flags;
   }

   @CompilerDirectives.TruffleBoundary
   public String toStringEscaped() {
      StringBuilder sb = new StringBuilder(this.pattern.length() + 2);
      sb.append('/');

      for (int i = 0; i < this.pattern.length(); i++) {
         int c = this.pattern.codePointAt(i);
         if (32 <= c && c <= 126) {
            sb.appendCodePoint(c);
         } else {
            sb.append("\\u");
            if (c > 65535) {
               i++;
               sb.append(String.format("{%06x}", c));
            } else {
               sb.append(String.format("%04x", c));
            }
         }
      }

      return sb.append('/').append(this.flags).toString();
   }

   @CompilerDirectives.TruffleBoundary
   public String toFileName() {
      StringBuilder sb = new StringBuilder(20);
      int i = 0;

      while (i < Math.min(this.pattern.length(), 20)) {
         int c = this.pattern.codePointAt(i);
         if (DebugUtil.isValidCharForFileName(c)) {
            sb.appendCodePoint(c);
         } else {
            sb.append('_');
         }

         if (c > 65535) {
            i += 2;
         } else {
            i++;
         }
      }

      if (!this.flags.isEmpty()) {
         sb.append('_').append(this.flags);
      }

      return sb.toString();
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public JsonValue toJson() {
      return Json.obj(Json.prop("pattern", this.pattern), Json.prop("flags", this.flags));
   }
}
