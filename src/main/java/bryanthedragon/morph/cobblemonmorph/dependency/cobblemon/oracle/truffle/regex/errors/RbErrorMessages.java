
package com.oracle.truffle.regex.errors;

import com.oracle.truffle.api.CompilerDirectives;

public interface RbErrorMessages {
    public static final String CONDITIONAL_BACKREF_WITH_MORE_THAN_TWO_BRANCHES = "conditional backref with more than two branches";
    public static final String END_PATTERN_AT_CONTROL = "end pattern at control";
    public static final String END_PATTERN_AT_META = "end pattern at meta";
    public static final String EXPECTED_BRACE = "expected }";
    public static final String EXPECTED_PAREN = "expected )";
    public static final String INVALID_CONTROL_CODE_SYNTAX = "invalid control-code syntax";
    public static final String INVALID_GROUP_NAME = "invalid group name";
    public static final String INVALID_META_CODE_SYNTAX = "invalid meta-code syntax";
    public static final String INVALID_PATTERN_IN_LOOK_BEHIND = "invalid pattern in look-behind";
    public static final String INVALID_POSIX_BRACKET_TYPE = "invalid POSIX bracket type";
    public static final String MIN_REPEAT_GREATER_THAN_MAX_REPEAT = "min repeat greater than max repeat";
    public static final String MISSING_DASH_COLON_PAREN = "missing -, : or )";
    public static final String MISSING_FLAG_DASH_COLON_PAREN = "missing flag, -, : or )";
    public static final String MISSING_GROUP_NAME = "missing group name";
    public static final String NOTHING_TO_REPEAT = "nothing to repeat";
    public static final String NUMBERED_BACKREF_CALL_IS_NOT_ALLOWED = "numbered backref/call is not allowed. (use name)";
    public static final String TOO_BIG_NUMBER = "too big number";
    public static final String UNBALANCED_PARENTHESIS = "unbalanced parenthesis";
    public static final String UNDEFINED_GROUP_OPTION = "undefined group option";
    public static final String UNEXPECTED_END_OF_PATTERN = "unexpected end of pattern";
    public static final String UNTERMINATED_CHARACTER_SET = "unterminated character set";
    public static final String UNTERMINATED_COMMENT = "missing ), unterminated comment";
    public static final String UNTERMINATED_SUBPATTERN = "missing ), unterminated subpattern";

    @CompilerDirectives.TruffleBoundary
    public static String badCharacterRange(String range) {
        return "bad character range " + range;
    }

    @CompilerDirectives.TruffleBoundary
    public static String badEscape(String code) {
        return "bad escape \\u" + code;
    }

    @CompilerDirectives.TruffleBoundary
    public static String incompleteEscape(String code) {
        return "incomplete escape \\u" + code;
    }

    @CompilerDirectives.TruffleBoundary
    public static String invalidGroupReference(String ref) {
        return "invalid group reference " + ref;
    }

    @CompilerDirectives.TruffleBoundary
    public static String invalidUnicodeEscape(String code) {
        return "unicode escape value " + code + " outside of range 0-0x10FFFF";
    }

    @CompilerDirectives.TruffleBoundary
    public static String multiplexCall(String name) {
        return "multiplex definition name <" + name + "> call";
    }

    @CompilerDirectives.TruffleBoundary
    public static String undefinedReference(String name) {
        return "undefined name <" + name + "> reference";
    }

    @CompilerDirectives.TruffleBoundary
    public static String unknownExtension(int c) {
        return "unknown extension ?" + new String(Character.toChars(c));
    }

    @CompilerDirectives.TruffleBoundary
    public static String unknownGroupName(String name) {
        return "unknown group name " + name;
    }

    @CompilerDirectives.TruffleBoundary
    public static String unterminatedName(char terminator) {
        return "missing " + terminator + ", unterminated name";
    }
}

