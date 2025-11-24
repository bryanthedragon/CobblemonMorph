
package org.graalvm.shadowed.org.jcodings;

public interface Config {
    public static final int ENC_CODE_TO_MBC_MAXLEN = 7;
    public static final int ENC_MBC_CASE_FOLD_MAXLEN = 18;
    public static final int ENC_MAX_COMP_CASE_FOLD_CODE_LEN = 3;
    public static final int ENC_GET_CASE_FOLD_CODES_MAX_NUM = 13;
    public static final boolean USE_UNICODE_CASE_FOLD_TURKISH_AZERI = false;
    public static final boolean USE_UNICODE_ALL_LINE_TERMINATORS = false;
    public static final boolean USE_CRNL_AS_LINE_TERMINATOR = false;
    public static final boolean USE_UNICODE_PROPERTIES = true;
    public static final int CodePointMaskWidth = 3;
    public static final int CodePointMask = 7;
    public static final int SpecialIndexShift = 3;
    public static final int SpecialIndexWidth = 10;
    public static final int SpecialIndexMask = 8184;
    public static final int SpecialsLengthOffset = 25;
    public static final int CASE_UPCASE = 8192;
    public static final int CASE_DOWNCASE = 16384;
    public static final int CASE_TITLECASE = 32768;
    public static final int CASE_SPECIAL_OFFSET = 3;
    public static final int CASE_UP_SPECIAL = 65536;
    public static final int CASE_DOWN_SPECIAL = 131072;
    public static final int CASE_MODIFIED = 262144;
    public static final int CASE_FOLD = 524288;
    public static final int CASE_FOLD_TURKISH_AZERI = 0x100000;
    public static final int CASE_FOLD_LITHUANIAN = 0x200000;
    public static final int CASE_ASCII_ONLY = 0x400000;
    public static final int CASE_IS_TITLECASE = 0x800000;
    public static final int CASE_SPECIALS = 0x838000;
    public static final int INTERNAL_ENC_CASE_FOLD_MULTI_CHAR = 0x40000000;
    public static final int ENC_CASE_FOLD_MIN = 0x40000000;
    public static final int ENC_CASE_FOLD_DEFAULT = 0x40000000;
    public static final String UNICODE_VERSION_STRING = "12.1.0";
    public static final int UNICODE_VERSION_MAJOR = 12;
    public static final int UNICODE_VERSION_MINOR = 1;
    public static final int UNICODE_VERSION_TEENY = 0;
    public static final String UNICODE_EMOJI_VERSION_STRING = "12.0";
    public static final int UNICODE_EMOJI_VERSION_MAJOR = 12;
    public static final int UNICODE_EMOJI_VERSION_MINOR = 0;
}

