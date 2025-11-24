
package com.oracle.truffle.regex.errors;

public interface ErrorMessages {
    public static final String CHAR_CLASS_RANGE_OUT_OF_ORDER = "Range out of order in character class";
    public static final String EMPTY_GROUP_NAME = "Empty named capture group name";
    public static final String ENDS_WITH_UNFINISHED_ESCAPE_SEQUENCE = "Ends with an unfinished escape sequence";
    public static final String ENDS_WITH_UNFINISHED_UNICODE_PROPERTY = "Ends with an unfinished Unicode property escape";
    public static final String INCOMPLETE_QUANTIFIER = "Incomplete quantifier";
    public static final String INVALID_CHARACTER_CLASS = "Invalid character class";
    public static final String INVALID_CONTROL_CHAR_ESCAPE = "Invalid control char escape";
    public static final String INVALID_ESCAPE = "Invalid escape";
    public static final String INVALID_GROUP_NAME_PART = "Invalid character in group name";
    public static final String INVALID_GROUP_NAME_START = "Invalid character at start of group name";
    public static final String INVALID_UNICODE_ESCAPE = "Invalid Unicode escape";
    public static final String INVALID_UNICODE_PROPERTY = "Invalid Unicode property escape";
    public static final String MISSING_GROUP_FOR_BACKREFERENCE = "Missing capture group for backreference";
    public static final String MISSING_GROUP_NAME = "Missing group name in named capture group reference";
    public static final String MULTIPLE_GROUPS_SAME_NAME = "Multiple named capture groups with the same name";
    public static final String QUANTIFIER_ON_LOOKAHEAD_ASSERTION = "Quantifier on lookahead assertion";
    public static final String QUANTIFIER_ON_LOOKBEHIND_ASSERTION = "Quantifier on lookbehind assertion";
    public static final String QUANTIFIER_ON_QUANTIFIER = "Quantifier on quantifier";
    public static final String QUANTIFIER_OUT_OF_ORDER = "Numbers out of order in {} quantifier";
    public static final String QUANTIFIER_WITHOUT_TARGET = "Quantifier without target";
    public static final String UNMATCHED_LEFT_BRACKET = "Unterminated character class";
    public static final String UNMATCHED_RIGHT_BRACKET = "Unmatched ']'";
    public static final String UNMATCHED_RIGHT_PARENTHESIS = "Unmatched ')'";
    public static final String UNMATCHED_RIGHT_BRACE = "Unmatched '}'";
    public static final String UNTERMINATED_GROUP = "Unterminated group";
    public static final String UNTERMINATED_GROUP_NAME = "Unterminated group name";
    public static final String REPEATED_FLAG = "Repeated regex flag";
    public static final String UNSUPPORTED_FLAG = "Unsupported regex flag";
}

