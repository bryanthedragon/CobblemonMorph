
package com.oracle.truffle.regex.errors;

import com.oracle.truffle.api.CompilerDirectives;

public interface PyErrorMessages {
    public static final String BAD_ESCAPE_END_OF_PATTERN = "bad escape (end of pattern)";
    public static final String CANNOT_REFER_TO_AN_OPEN_GROUP = "cannot refer to an open group";
    public static final String CANNOT_REFER_TO_GROUP_DEFINED_IN_THE_SAME_LOOKBEHIND_SUBPATTERN = "cannot refer to group defined in the same lookbehind subpattern";
    public static final String CONDITIONAL_BACKREF_WITH_MORE_THAN_TWO_BRANCHES = "conditional backref with more than two branches";
    public static final String INLINE_FLAGS_CANNOT_TURN_OFF_FLAGS_A_U_AND_L = "bad inline flags: cannot turn off flags 'a', 'u' and 'L'";
    public static final String INLINE_FLAGS_CANNOT_TURN_OFF_GLOBAL_FLAG = "bad inline flags: cannot turn off global flag";
    public static final String INLINE_FLAGS_CANNOT_TURN_ON_GLOBAL_FLAG = "bad inline flags: cannot turn on global flag";
    public static final String INLINE_FLAGS_CANNOT_USE_L_FLAG_WITH_A_STR_PATTERN = "bad inline flags: cannot use 'L' flag with a str pattern";
    public static final String INLINE_FLAGS_CANNOT_USE_U_FLAG_WITH_A_BYTES_PATTERN = "bad inline flags: cannot use 'u' flag with a bytes pattern";
    public static final String INLINE_FLAGS_FLAGS_A_U_AND_L_ARE_INCOMPATIBLE = "bad inline flags: flags 'a', 'u' and 'L' are incompatible";
    public static final String INLINE_FLAGS_FLAG_TURNED_ON_AND_OFF = "bad inline flags: flag turned on and off";
    public static final String MIN_REPEAT_GREATER_THAN_MAX_REPEAT = "min repeat greater than max repeat";
    public static final String MISSING_COLON = "missing :";
    public static final String MISSING_DASH_COLON_PAREN = "missing -, : or )";
    public static final String MISSING_FLAG = "missing flag";
    public static final String MISSING_GROUP_NAME = "missing group name";
    public static final String MULTIPLE_REPEAT = "multiple repeat";
    public static final String NEGATIVE_GROUP_NUMBER = "negative group number";
    public static final String NOTHING_TO_REPEAT = "nothing to repeat";
    public static final String UNBALANCED_PARENTHESIS = "unbalanced parenthesis";
    public static final String UNEXPECTED_END_OF_PATTERN = "unexpected end of pattern";
    public static final String UNKNOWN_FLAG = "unknown flag";
    public static final String UNTERMINATED_CHARACTER_SET = "unterminated character set";
    public static final String UNTERMINATED_COMMENT = "missing ), unterminated comment";
    public static final String UNTERMINATED_NAME = "missing ), unterminated name";
    public static final String UNTERMINATED_NAME_ANGLE_BRACKET = "missing >, unterminated name";
    public static final String UNTERMINATED_SUBPATTERN = "missing ), unterminated subpattern";

    @CompilerDirectives.TruffleBoundary
    public static String badCharacterInGroupName(String name) {
        return "bad character in group name '" + name + "'";
    }

    @CompilerDirectives.TruffleBoundary
    public static String badCharacterRange(String range) {
        return "bad character range " + range;
    }

    @CompilerDirectives.TruffleBoundary
    public static String badEscape(int chr) {
        return "bad escape \\" + new String(Character.toChars(chr));
    }

    @CompilerDirectives.TruffleBoundary
    public static String incompleteEscapeU(char chr, String code) {
        return "incomplete escape \\" + chr + code;
    }

    @CompilerDirectives.TruffleBoundary
    public static String incompleteEscapeX(String code) {
        return "incomplete escape \\x" + code;
    }

    @CompilerDirectives.TruffleBoundary
    public static String invalidGroupReference(String ref) {
        return "invalid group reference " + ref;
    }

    @CompilerDirectives.TruffleBoundary
    public static String invalidOctalEscape(String code) {
        return "octal escape value \\" + code + " outside of range 0-0o377";
    }

    @CompilerDirectives.TruffleBoundary
    public static String invalidUnicodeEscape(char chr, String code) {
        return "bad escape \\" + chr + code;
    }

    @CompilerDirectives.TruffleBoundary
    public static String missing(String name) {
        return "missing " + name;
    }

    @CompilerDirectives.TruffleBoundary
    public static String missingUnterminatedName(char terminator) {
        return "missing " + terminator + ", unterminated name";
    }

    @CompilerDirectives.TruffleBoundary
    public static String redefinitionOfGroupName(String name, int newId, int oldId) {
        return String.format("redefinition of group name '%s' as group %d; was group %d", name, newId, oldId);
    }

    @CompilerDirectives.TruffleBoundary
    public static String undefinedCharacterName(String name) {
        return "undefined character name '" + name + "'";
    }

    @CompilerDirectives.TruffleBoundary
    public static String unknownExtensionLt(int chr) {
        return "unknown extension ?<" + new String(Character.toChars(chr));
    }

    @CompilerDirectives.TruffleBoundary
    public static String unknownExtensionP(int chr) {
        return "unknown extension ?P" + new String(Character.toChars(chr));
    }

    @CompilerDirectives.TruffleBoundary
    public static String unknownExtensionQ(int chr) {
        return "unknown extension ?" + new String(Character.toChars(chr));
    }

    @CompilerDirectives.TruffleBoundary
    public static String unknownGroupName(String name) {
        return "unknown group name '" + name + "'";
    }
}

