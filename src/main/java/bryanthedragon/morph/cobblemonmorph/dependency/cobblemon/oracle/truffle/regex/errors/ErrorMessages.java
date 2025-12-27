package com.oracle.truffle.regex.errors;

public interface ErrorMessages {
   String CHAR_CLASS_RANGE_OUT_OF_ORDER = "Range out of order in character class";
   String EMPTY_GROUP_NAME = "Empty named capture group name";
   String ENDS_WITH_UNFINISHED_ESCAPE_SEQUENCE = "Ends with an unfinished escape sequence";
   String ENDS_WITH_UNFINISHED_UNICODE_PROPERTY = "Ends with an unfinished Unicode property escape";
   String INCOMPLETE_QUANTIFIER = "Incomplete quantifier";
   String INVALID_CHARACTER_CLASS = "Invalid character class";
   String INVALID_CONTROL_CHAR_ESCAPE = "Invalid control char escape";
   String INVALID_ESCAPE = "Invalid escape";
   String INVALID_GROUP_NAME_PART = "Invalid character in group name";
   String INVALID_GROUP_NAME_START = "Invalid character at start of group name";
   String INVALID_UNICODE_ESCAPE = "Invalid Unicode escape";
   String INVALID_UNICODE_PROPERTY = "Invalid Unicode property escape";
   String MISSING_GROUP_FOR_BACKREFERENCE = "Missing capture group for backreference";
   String MISSING_GROUP_NAME = "Missing group name in named capture group reference";
   String MULTIPLE_GROUPS_SAME_NAME = "Multiple named capture groups with the same name";
   String QUANTIFIER_ON_LOOKAHEAD_ASSERTION = "Quantifier on lookahead assertion";
   String QUANTIFIER_ON_LOOKBEHIND_ASSERTION = "Quantifier on lookbehind assertion";
   String QUANTIFIER_ON_QUANTIFIER = "Quantifier on quantifier";
   String QUANTIFIER_OUT_OF_ORDER = "Numbers out of order in {} quantifier";
   String QUANTIFIER_WITHOUT_TARGET = "Quantifier without target";
   String UNMATCHED_LEFT_BRACKET = "Unterminated character class";
   String UNMATCHED_RIGHT_BRACKET = "Unmatched ']'";
   String UNMATCHED_RIGHT_PARENTHESIS = "Unmatched ')'";
   String UNMATCHED_RIGHT_BRACE = "Unmatched '}'";
   String UNTERMINATED_GROUP = "Unterminated group";
   String UNTERMINATED_GROUP_NAME = "Unterminated group name";
   String REPEATED_FLAG = "Repeated regex flag";
   String UNSUPPORTED_FLAG = "Unsupported regex flag";
}
