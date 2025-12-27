package com.oracle.truffle.regex.tregex.parser;

import com.oracle.truffle.regex.RegexLanguage;
import com.oracle.truffle.regex.tregex.parser.ast.Group;
import java.util.function.Function;

public final class RegexParserGlobals {
   final Group wordBoundarySubstituion;
   final Group nonWordBoundarySubstitution;
   final Group unicodeIgnoreCaseWordBoundarySubstitution;
   final Group unicodeIgnoreCaseNonWordBoundarySubsitution;
   final Group multiLineCaretSubstitution;
   final Group multiLineDollarSubsitution;
   final Group noLeadSurrogateBehind;
   final Group noTrailSurrogateAhead;

   public RegexParserGlobals(RegexLanguage language) {
      String wordBoundarySrc = "(?:^|(?<=\\W))(?=\\w)|(?<=\\w)(?:(?=\\W)|$)";
      String nonWordBoundarySrc = "(?:^|(?<=\\W))(?:(?=\\W)|$)|(?<=\\w)(?=\\w)";
      this.wordBoundarySubstituion = JSRegexParser.parseRootLess(language, "(?:^|(?<=\\W))(?=\\w)|(?<=\\w)(?:(?=\\W)|$)");
      this.nonWordBoundarySubstitution = JSRegexParser.parseRootLess(language, "(?:^|(?<=\\W))(?:(?=\\W)|$)|(?<=\\w)(?=\\w)");
      Function<String, String> includeExtraCases = s -> s.replace("\\w", "[\\w\\u017F\\u212A]").replace("\\W", "[^\\w\\u017F\\u212A]");
      this.unicodeIgnoreCaseWordBoundarySubstitution = JSRegexParser.parseRootLess(
         language, includeExtraCases.apply("(?:^|(?<=\\W))(?=\\w)|(?<=\\w)(?:(?=\\W)|$)")
      );
      this.unicodeIgnoreCaseNonWordBoundarySubsitution = JSRegexParser.parseRootLess(
         language, includeExtraCases.apply("(?:^|(?<=\\W))(?:(?=\\W)|$)|(?<=\\w)(?=\\w)")
      );
      this.multiLineCaretSubstitution = JSRegexParser.parseRootLess(language, "(?:^|(?<=[\\r\\n\\u2028\\u2029]))");
      this.multiLineDollarSubsitution = JSRegexParser.parseRootLess(language, "(?:$|(?=[\\r\\n\\u2028\\u2029]))");
      this.noLeadSurrogateBehind = JSRegexParser.parseRootLess(language, "(?:^|(?<=[^\\uD800-\\uDBFF]))");
      this.noTrailSurrogateAhead = JSRegexParser.parseRootLess(language, "(?:$|(?=[^\\uDC00-\\uDFFF]))");
   }
}
