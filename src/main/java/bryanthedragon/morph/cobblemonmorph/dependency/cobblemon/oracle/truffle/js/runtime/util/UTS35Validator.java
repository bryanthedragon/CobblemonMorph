
package com.oracle.truffle.js.runtime.util;

import java.util.regex.Pattern;

public class UTS35Validator {
    private static final Pattern LOCALE_ID_PATTERN = Pattern.compile(UTS35Validator.unicodeLocaleID());

    public static boolean isWellFormedUnicodeBCP47LocaleIdentifier(String languageTag) {
        return LOCALE_ID_PATTERN.matcher(languageTag).matches();
    }

    public static boolean isDigit(char c) {
        return '0' <= c && c <= '9';
    }

    public static boolean isAlpha(char c) {
        return 'A' <= c && c <= 'Z' || 'a' <= c && c <= 'z';
    }

    public static boolean isAlphanum(char c) {
        return 'a' <= c && c <= 'z' || 'A' <= c && c <= 'Z' || '0' <= c && c <= '9';
    }

    public static boolean isStructurallyValidLanguageSubtag(String language) {
        int length = language.length();
        if (length < 2 || length == 4 || length > 8) {
            return false;
        }
        for (int i = 0; i < length; ++i) {
            if (UTS35Validator.isAlpha(language.charAt(i))) continue;
            return false;
        }
        return true;
    }

    public static boolean isStructurallyValidRegionSubtag(String region) {
        int length = region.length();
        return length == 2 && UTS35Validator.isAlpha(region.charAt(0)) && UTS35Validator.isAlpha(region.charAt(1)) || length == 3 && UTS35Validator.isDigit(region.charAt(0)) && UTS35Validator.isDigit(region.charAt(1)) && UTS35Validator.isDigit(region.charAt(2));
    }

    public static boolean isStructurallyValidScriptSubtag(String script) {
        return script.length() == 4 && UTS35Validator.isAlpha(script.charAt(0)) && UTS35Validator.isAlpha(script.charAt(1)) && UTS35Validator.isAlpha(script.charAt(2)) && UTS35Validator.isAlpha(script.charAt(3));
    }

    public static boolean isStructurallyValidType(String type) {
        int alphanumStart = 0;
        for (int i = 0; i < type.length(); ++i) {
            char c = type.charAt(i);
            if (UTS35Validator.isAlphanum(c)) continue;
            if (c == '-') {
                int alphanumLength = i - alphanumStart;
                if (3 <= alphanumLength && alphanumLength <= 8) {
                    alphanumStart = i + 1;
                    continue;
                }
                return false;
            }
            return false;
        }
        int alphanumLength = type.length() - alphanumStart;
        return 3 <= alphanumLength && alphanumLength <= 8;
    }

    private static String unicodeLanguageID() {
        return UTS35Validator.group(UTS35Validator.unicodeLanguageSubtag() + UTS35Validator.group(UTS35Validator.sep() + UTS35Validator.unicodeScriptSubtag()) + "?" + UTS35Validator.group(UTS35Validator.sep() + UTS35Validator.unicodeRegionSubtag()) + "?" + UTS35Validator.group(UTS35Validator.sep() + UTS35Validator.unicodeVariantSubtag()) + "*");
    }

    private static String unicodeLanguageSubtag() {
        return UTS35Validator.group(UTS35Validator.alpha() + "{2,3}|" + UTS35Validator.alpha() + "{5,8}");
    }

    private static String unicodeScriptSubtag() {
        return UTS35Validator.alpha() + "{4}";
    }

    private static String unicodeRegionSubtag() {
        return UTS35Validator.group(UTS35Validator.alpha() + "{2}|" + UTS35Validator.digit() + "{3}");
    }

    private static String unicodeVariantSubtag() {
        return UTS35Validator.group(UTS35Validator.alphanum() + "{5,8}|" + UTS35Validator.digit() + UTS35Validator.alphanum() + "{3}");
    }

    private static String sep() {
        return "-";
    }

    private static String digit() {
        return "[0-9]";
    }

    private static String alpha() {
        return "[A-Za-z]";
    }

    private static String alphanum() {
        return "[0-9A-Za-z]";
    }

    private static String unicodeLocaleID() {
        return UTS35Validator.group(UTS35Validator.unicodeLanguageID() + UTS35Validator.extensions() + "*" + UTS35Validator.puExtensions() + "?");
    }

    private static String extensions() {
        return UTS35Validator.group(UTS35Validator.unicodeLocaleExtensions() + "|" + UTS35Validator.transformedExtentensions() + "|" + UTS35Validator.otherExtensions());
    }

    private static String unicodeLocaleExtensions() {
        return UTS35Validator.group(UTS35Validator.sep() + "[uU]" + UTS35Validator.group(UTS35Validator.group(UTS35Validator.sep() + UTS35Validator.keyword()) + "+|" + UTS35Validator.group(UTS35Validator.sep() + UTS35Validator.attribute()) + "+" + UTS35Validator.group(UTS35Validator.sep() + UTS35Validator.keyword()) + "*"));
    }

    private static String transformedExtentensions() {
        return UTS35Validator.group(UTS35Validator.sep() + "[tT]" + UTS35Validator.group(UTS35Validator.group(UTS35Validator.sep() + UTS35Validator.tLang() + UTS35Validator.group(UTS35Validator.sep() + UTS35Validator.tField()) + "*") + "|" + UTS35Validator.group(UTS35Validator.sep() + UTS35Validator.tField()) + "+"));
    }

    private static String puExtensions() {
        return UTS35Validator.group(UTS35Validator.sep() + "[xX]" + UTS35Validator.group(UTS35Validator.sep() + UTS35Validator.alphanum() + "{1,8}") + "+");
    }

    private static String otherExtensions() {
        return UTS35Validator.group(UTS35Validator.sep() + "[0-9a-svwyzA-SVWYZ]" + UTS35Validator.group(UTS35Validator.sep() + UTS35Validator.alphanum() + "{2,8}") + "+");
    }

    private static String keyword() {
        return UTS35Validator.group(UTS35Validator.key() + UTS35Validator.group(UTS35Validator.sep() + UTS35Validator.type()) + "?");
    }

    private static String key() {
        return UTS35Validator.group(UTS35Validator.alphanum() + UTS35Validator.alpha());
    }

    private static String type() {
        return UTS35Validator.group(UTS35Validator.alphanum() + "{3,8}" + UTS35Validator.group(UTS35Validator.sep() + UTS35Validator.alphanum() + "{3,8}") + "*");
    }

    private static String attribute() {
        return UTS35Validator.alphanum() + "{3,8}";
    }

    private static String tLang() {
        return UTS35Validator.group(UTS35Validator.unicodeLanguageSubtag() + UTS35Validator.group(UTS35Validator.sep() + UTS35Validator.unicodeScriptSubtag()) + "?" + UTS35Validator.group(UTS35Validator.sep() + UTS35Validator.unicodeRegionSubtag()) + "?" + UTS35Validator.group(UTS35Validator.sep() + UTS35Validator.unicodeVariantSubtag()) + "*");
    }

    private static String tField() {
        return UTS35Validator.group(UTS35Validator.tKey() + UTS35Validator.tValue());
    }

    private static String tKey() {
        return UTS35Validator.group(UTS35Validator.alpha() + UTS35Validator.digit());
    }

    private static String tValue() {
        return UTS35Validator.group(UTS35Validator.sep() + UTS35Validator.alphanum() + "{3,8}") + "+";
    }

    private static String group(String expression) {
        return "(?:" + expression + ")";
    }
}

