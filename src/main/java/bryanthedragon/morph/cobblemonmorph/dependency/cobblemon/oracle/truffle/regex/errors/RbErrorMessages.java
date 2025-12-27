package com.oracle.truffle.regex.errors;

import com.oracle.truffle.api.CompilerDirectives;

public interface RbErrorMessages {
   String CONDITIONAL_BACKREF_WITH_MORE_THAN_TWO_BRANCHES = "conditional backref with more than two branches";
   String END_PATTERN_AT_CONTROL = "end pattern at control";
   String END_PATTERN_AT_META = "end pattern at meta";
   String EXPECTED_BRACE = "expected }";
   String EXPECTED_PAREN = "expected )";
   String INVALID_CONTROL_CODE_SYNTAX = "invalid control-code syntax";
   String INVALID_GROUP_NAME = "invalid group name";
   String INVALID_META_CODE_SYNTAX = "invalid meta-code syntax";
   String INVALID_PATTERN_IN_LOOK_BEHIND = "invalid pattern in look-behind";
   String INVALID_POSIX_BRACKET_TYPE = "invalid POSIX bracket type";
   String MIN_REPEAT_GREATER_THAN_MAX_REPEAT = "min repeat greater than max repeat";
   String MISSING_DASH_COLON_PAREN = "missing -, : or )";
   String MISSING_FLAG_DASH_COLON_PAREN = "missing flag, -, : or )";
   String MISSING_GROUP_NAME = "missing group name";
   String NOTHING_TO_REPEAT = "nothing to repeat";
   String NUMBERED_BACKREF_CALL_IS_NOT_ALLOWED = "numbered backref/call is not allowed. (use name)";
   String TOO_BIG_NUMBER = "too big number";
   String UNBALANCED_PARENTHESIS = "unbalanced parenthesis";
   String UNDEFINED_GROUP_OPTION = "undefined group option";
   String UNEXPECTED_END_OF_PATTERN = "unexpected end of pattern";
   String UNTERMINATED_CHARACTER_SET = "unterminated character set";
   String UNTERMINATED_COMMENT = "missing ), unterminated comment";
   String UNTERMINATED_SUBPATTERN = "missing ), unterminated subpattern";

   @CompilerDirectives.TruffleBoundary
   static String badCharacterRange(String range) {
      return "bad character range " + range;
   }

   @CompilerDirectives.TruffleBoundary
   static String badEscape(String code) {
      return "bad escape \\u" + code;
   }

   @CompilerDirectives.TruffleBoundary
   static String incompleteEscape(String code) {
      return "incomplete escape \\u" + code;
   }

   @CompilerDirectives.TruffleBoundary
   static String invalidGroupReference(String ref) {
      return "invalid group reference " + ref;
   }

   @CompilerDirectives.TruffleBoundary
   static String invalidUnicodeEscape(String code) {
      return "unicode escape value " + code + " outside of range 0-0x10FFFF";
   }

   @CompilerDirectives.TruffleBoundary
   static String multiplexCall(String name) {
      return "multiplex definition name <" + name + "> call";
   }

   @CompilerDirectives.TruffleBoundary
   static String undefinedReference(String name) {
      return "undefined name <" + name + "> reference";
   }

   @CompilerDirectives.TruffleBoundary
   static String unknownExtension(int c) {
      return "unknown extension ?" + new String(Character.toChars(c));
   }

   @CompilerDirectives.TruffleBoundary
   static String unknownGroupName(String name) {
      return "unknown group name " + name;
   }

   @CompilerDirectives.TruffleBoundary
   static String unterminatedName(char terminator) {
      return "missing " + terminator + ", unterminated name";
   }
}
