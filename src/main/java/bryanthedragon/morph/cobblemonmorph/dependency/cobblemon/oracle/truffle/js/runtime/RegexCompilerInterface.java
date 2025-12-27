package com.oracle.truffle.js.runtime;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.exception.AbstractTruffleException;
import com.oracle.truffle.api.interop.ExceptionType;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.source.Source;
import com.oracle.truffle.api.strings.TruffleString;

public final class RegexCompilerInterface {
   private static final String REPEATED_REG_EXP_FLAG_MSG = "Repeated RegExp flag: %c";
   private static final String UNSUPPORTED_REG_EXP_FLAG_MSG = "Invalid regular expression flags";
   private static final String UNSUPPORTED_REG_EXP_FLAG_MSG_NASHORN = "Unsupported RegExp flag: %c";

   private RegexCompilerInterface() {
   }

   public static Object compile(TruffleString pattern, TruffleString flags, JSContext context, JSRealm realm) {
      return compile(Strings.toJavaString(pattern), Strings.toJavaString(flags), context, realm, InteropLibrary.getUncached());
   }

   public static Object compile(String pattern, String flags, JSContext context, JSRealm realm) {
      return compile(pattern, flags, context, realm, InteropLibrary.getUncached());
   }

   public static Object compile(String pattern, String flags, JSContext context, JSRealm realm, InteropLibrary isCompiledRegexNull) {
      Source regexSource = createRegexSource(pattern, flags, context.getRegexOptions());
      Object compiledRegex = compile(regexSource, flags, context, realm);
      if (isCompiledRegexNull.isNull(compiledRegex)) {
         throw Errors.createSyntaxError("regular expression not supported");
      } else {
         return compiledRegex;
      }
   }

   @CompilerDirectives.TruffleBoundary
   private static Object compile(Source regexSource, String flags, JSContext context, JSRealm realm) {
      Object compiledRegex = realm.getCachedCompiledRegex(regexSource);
      if (compiledRegex != null) {
         return compiledRegex;
      } else {
         validateFlags(flags, context.getEcmaScriptVersion(), context.isOptionNashornCompatibilityMode(), context.isOptionRegexpMatchIndices());

         try {
            compiledRegex = realm.getEnv().parseInternal(regexSource).call();
            realm.putCachedCompiledRegex(regexSource, compiledRegex);
            return compiledRegex;
         } catch (AbstractTruffleException var6) {
            throw rethrowAsSyntaxError(var6);
         }
      }
   }

   @CompilerDirectives.TruffleBoundary
   public static Source createRegexSource(String pattern, String flags, String options) {
      String regexStr = options + "/" + pattern + "/" + flags;
      return Source.newBuilder("regex", regexStr, regexStr).mimeType("application/tregex").internal(true).build();
   }

   @CompilerDirectives.TruffleBoundary
   public static void validate(JSContext context, String pattern, String flags, int ecmaScriptVersion) {
      Source regexSource = createRegexSource(pattern, flags, context.getRegexValidateOptions());
      if (context.isOptionNashornCompatibilityMode() && !flags.isEmpty()) {
         validateFlags(flags, ecmaScriptVersion, true, context.isOptionRegexpMatchIndices());
      }

      JSRealm realm = JSRealm.get(null);

      try {
         realm.getEnv().parseInternal(regexSource).call();
      } catch (AbstractTruffleException var7) {
         throw rethrowAsSyntaxError(var7);
      }
   }

   @CompilerDirectives.TruffleBoundary
   public static void validateFlags(String flags, int ecmaScriptVersion, boolean nashornCompat, boolean allowHasIndices) {
      boolean ignoreCase = false;
      boolean multiline = false;
      boolean global = false;
      boolean sticky = false;
      boolean unicode = false;
      boolean dotAll = false;
      boolean hasIndices = false;

      for (int i = 0; i < flags.length(); i++) {
         char ch = flags.charAt(i);
         boolean recognized = false;
         boolean repeated = false;
         switch (ch) {
            case 'd':
               if (allowHasIndices) {
                  recognized = true;
                  repeated = hasIndices;
                  hasIndices = true;
               }
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
               break;
            case 'g':
               recognized = true;
               repeated = global;
               global = true;
               break;
            case 'i':
               recognized = true;
               repeated = ignoreCase;
               ignoreCase = true;
               break;
            case 'm':
               recognized = true;
               repeated = multiline;
               multiline = true;
               break;
            case 's':
               if (ecmaScriptVersion >= 9) {
                  recognized = true;
                  repeated = dotAll;
                  dotAll = true;
               }
               break;
            case 'u':
               if (ecmaScriptVersion >= 6) {
                  recognized = true;
                  repeated = unicode;
                  unicode = true;
               }
               break;
            case 'y':
               if (ecmaScriptVersion >= 6) {
                  recognized = true;
                  repeated = sticky;
                  sticky = true;
               }
         }

         if (!recognized) {
            throw unsupportedFlagError(ch, nashornCompat);
         }

         if (repeated) {
            throw throwFlagError("Repeated RegExp flag: %c", ch);
         }
      }
   }

   @CompilerDirectives.TruffleBoundary
   private static RuntimeException unsupportedFlagError(char ch, boolean nashornCompat) {
      if (nashornCompat) {
         throw throwFlagError("Unsupported RegExp flag: %c", ch);
      } else {
         throw Errors.createSyntaxError("Invalid regular expression flags");
      }
   }

   @CompilerDirectives.TruffleBoundary
   private static RuntimeException throwFlagError(String msg, char flag) {
      throw Errors.createSyntaxError(String.format(msg, flag));
   }

   @CompilerDirectives.TruffleBoundary
   private static AbstractTruffleException rethrowAsSyntaxError(AbstractTruffleException e) {
      ExceptionType exceptionType;
      try {
         exceptionType = InteropLibrary.getUncached().getExceptionType(e);
      } catch (UnsupportedMessageException var3) {
         throw Errors.shouldNotReachHere();
      }

      if (exceptionType == ExceptionType.PARSE_ERROR) {
         throw Errors.createSyntaxError(e.getMessage());
      } else {
         throw e;
      }
   }
}
