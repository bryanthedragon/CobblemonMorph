
package com.oracle.truffle.js.runtime;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.exception.AbstractTruffleException;
import com.oracle.truffle.api.interop.ExceptionType;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.source.Source;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.Strings;

public final class RegexCompilerInterface {
    private static final String REPEATED_REG_EXP_FLAG_MSG = "Repeated RegExp flag: %c";
    private static final String UNSUPPORTED_REG_EXP_FLAG_MSG = "Invalid regular expression flags";
    private static final String UNSUPPORTED_REG_EXP_FLAG_MSG_NASHORN = "Unsupported RegExp flag: %c";

    private RegexCompilerInterface() {
    }

    public static Object compile(TruffleString pattern, TruffleString flags, JSContext context, JSRealm realm) {
        return RegexCompilerInterface.compile(Strings.toJavaString(pattern), Strings.toJavaString(flags), context, realm, InteropLibrary.getUncached());
    }

    public static Object compile(String pattern, String flags, JSContext context, JSRealm realm) {
        return RegexCompilerInterface.compile(pattern, flags, context, realm, InteropLibrary.getUncached());
    }

    public static Object compile(String pattern, String flags, JSContext context, JSRealm realm, InteropLibrary isCompiledRegexNull) {
        Source regexSource = RegexCompilerInterface.createRegexSource(pattern, flags, context.getRegexOptions());
        Object compiledRegex = RegexCompilerInterface.compile(regexSource, flags, context, realm);
        if (isCompiledRegexNull.isNull(compiledRegex)) {
            throw Errors.createSyntaxError("regular expression not supported");
        }
        return compiledRegex;
    }

    @CompilerDirectives.TruffleBoundary
    private static Object compile(Source regexSource, String flags, JSContext context, JSRealm realm) {
        Object compiledRegex = realm.getCachedCompiledRegex(regexSource);
        if (compiledRegex != null) {
            return compiledRegex;
        }
        RegexCompilerInterface.validateFlags(flags, context.getEcmaScriptVersion(), context.isOptionNashornCompatibilityMode(), context.isOptionRegexpMatchIndices());
        try {
            compiledRegex = realm.getEnv().parseInternal(regexSource, new String[0]).call(new Object[0]);
            realm.putCachedCompiledRegex(regexSource, compiledRegex);
        }
        catch (AbstractTruffleException e) {
            throw RegexCompilerInterface.rethrowAsSyntaxError(e);
        }
        return compiledRegex;
    }

    @CompilerDirectives.TruffleBoundary
    public static Source createRegexSource(String pattern, String flags, String options) {
        String regexStr = options + "/" + pattern + "/" + flags;
        return Source.newBuilder("regex", regexStr, regexStr).mimeType("application/tregex").internal(true).build();
    }

    @CompilerDirectives.TruffleBoundary
    public static void validate(JSContext context, String pattern, String flags, int ecmaScriptVersion) {
        Source regexSource = RegexCompilerInterface.createRegexSource(pattern, flags, context.getRegexValidateOptions());
        if (context.isOptionNashornCompatibilityMode() && !flags.isEmpty()) {
            RegexCompilerInterface.validateFlags(flags, ecmaScriptVersion, true, context.isOptionRegexpMatchIndices());
        }
        JSRealm realm = JSRealm.get(null);
        try {
            realm.getEnv().parseInternal(regexSource, new String[0]).call(new Object[0]);
        }
        catch (AbstractTruffleException e) {
            throw RegexCompilerInterface.rethrowAsSyntaxError(e);
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
        for (int i = 0; i < flags.length(); ++i) {
            char ch = flags.charAt(i);
            boolean recognized = false;
            boolean repeated = false;
            switch (ch) {
                case 'i': {
                    recognized = true;
                    repeated = ignoreCase;
                    ignoreCase = true;
                    break;
                }
                case 'm': {
                    recognized = true;
                    repeated = multiline;
                    multiline = true;
                    break;
                }
                case 'g': {
                    recognized = true;
                    repeated = global;
                    global = true;
                    break;
                }
                case 'y': {
                    if (ecmaScriptVersion < 6) break;
                    recognized = true;
                    repeated = sticky;
                    sticky = true;
                    break;
                }
                case 'u': {
                    if (ecmaScriptVersion < 6) break;
                    recognized = true;
                    repeated = unicode;
                    unicode = true;
                    break;
                }
                case 's': {
                    if (ecmaScriptVersion < 9) break;
                    recognized = true;
                    repeated = dotAll;
                    dotAll = true;
                    break;
                }
                case 'd': {
                    if (!allowHasIndices) break;
                    recognized = true;
                    repeated = hasIndices;
                    hasIndices = true;
                }
            }
            if (!recognized) {
                throw RegexCompilerInterface.unsupportedFlagError(ch, nashornCompat);
            }
            if (!repeated) continue;
            throw RegexCompilerInterface.throwFlagError(REPEATED_REG_EXP_FLAG_MSG, ch);
        }
    }

    @CompilerDirectives.TruffleBoundary
    private static RuntimeException unsupportedFlagError(char ch, boolean nashornCompat) {
        if (nashornCompat) {
            throw RegexCompilerInterface.throwFlagError(UNSUPPORTED_REG_EXP_FLAG_MSG_NASHORN, ch);
        }
        throw Errors.createSyntaxError(UNSUPPORTED_REG_EXP_FLAG_MSG);
    }

    @CompilerDirectives.TruffleBoundary
    private static RuntimeException throwFlagError(String msg, char flag) {
        throw Errors.createSyntaxError(String.format(msg, Character.valueOf(flag)));
    }

    @CompilerDirectives.TruffleBoundary
    private static AbstractTruffleException rethrowAsSyntaxError(AbstractTruffleException e) {
        ExceptionType exceptionType;
        try {
            exceptionType = InteropLibrary.getUncached().getExceptionType(e);
        }
        catch (UnsupportedMessageException ume) {
            throw Errors.shouldNotReachHere();
        }
        if (exceptionType == ExceptionType.PARSE_ERROR) {
            throw Errors.createSyntaxError(e.getMessage());
        }
        throw e;
    }
}

