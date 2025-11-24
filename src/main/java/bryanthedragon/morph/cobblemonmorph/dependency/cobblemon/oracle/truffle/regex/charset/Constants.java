
package com.oracle.truffle.regex.charset;

import com.oracle.truffle.regex.charset.CodePointSet;

public final class Constants {
    public static final int MAX_CODE_POINT = 0x10FFFF;
    public static final CodePointSet ASCII_RANGE = CodePointSet.createNoDedup(0, 127);
    public static final CodePointSet BYTE_RANGE = CodePointSet.createNoDedup(0, 255);
    public static final CodePointSet BMP_RANGE = CodePointSet.createNoDedup(0, 65535);
    public static final CodePointSet BMP_RANGE_WITHOUT_LATIN1 = CodePointSet.createNoDedup(256, 65535);
    public static final CodePointSet UTF8_TWO_BYTE_RANGE = CodePointSet.createNoDedup(128, 2047);
    public static final CodePointSet UTF8_THREE_BYTE_RANGE = CodePointSet.createNoDedup(2048, 65535);
    public static final CodePointSet BMP_WITHOUT_SURROGATES = CodePointSet.createNoDedup(0, 55295, 57344, 65535);
    public static final CodePointSet BMP_WITHOUT_LATIN1_WITHOUT_SURROGATES = CodePointSet.createNoDedup(256, 55295, 57344, 65535);
    public static final CodePointSet ASTRAL_SYMBOLS = CodePointSet.createNoDedup(65536, 0x10FFFF);
    public static final CodePointSet SURROGATES = CodePointSet.createNoDedup(55296, 57343);
    public static final CodePointSet LEAD_SURROGATES = CodePointSet.createNoDedup(55296, 56319);
    public static final CodePointSet TRAIL_SURROGATES = CodePointSet.createNoDedup(56320, 57343);
    public static final CodePointSet ASTRAL_SYMBOLS_AND_LONE_SURROGATES = CodePointSet.createNoDedup(55296, 57343, 65536, 0x10FFFF);
    public static final CodePointSet DIGITS = CodePointSet.createNoDedup(48, 57);
    public static final CodePointSet NON_DIGITS = CodePointSet.createNoDedup(0, 47, 58, 0x10FFFF);
    public static final CodePointSet WORD_CHARS = CodePointSet.createNoDedup(48, 57, 65, 90, 95, 95, 97, 122);
    public static final CodePointSet NON_WORD_CHARS = CodePointSet.createNoDedup(0, 47, 58, 64, 91, 94, 96, 96, 123, 0x10FFFF);
    public static final CodePointSet WORD_CHARS_UNICODE_IGNORE_CASE = CodePointSet.createNoDedup(48, 57, 65, 90, 95, 95, 97, 122, 383, 383, 8490, 8490);
    public static final CodePointSet NON_WORD_CHARS_UNICODE_IGNORE_CASE = CodePointSet.createNoDedup(0, 47, 58, 64, 91, 94, 96, 96, 123, 382, 384, 8489, 8491, 0x10FFFF);
    public static final CodePointSet WHITE_SPACE = CodePointSet.createNoDedup(9, 13, 32, 32, 160, 160, 5760, 5760, 8192, 8202, 8232, 8233, 8239, 8239, 8287, 8287, 12288, 12288, 65279, 65279);
    public static final CodePointSet NON_WHITE_SPACE = CodePointSet.createNoDedup(0, 8, 14, 31, 33, 159, 161, 5759, 5761, 8191, 8203, 8231, 8234, 8238, 8240, 8286, 8288, 12287, 12289, 65278, 65280, 0x10FFFF);
    public static final CodePointSet LEGACY_WHITE_SPACE = CodePointSet.createNoDedup(9, 13, 32, 32, 160, 160, 5760, 5760, 6158, 6158, 8192, 8202, 8232, 8233, 8239, 8239, 8287, 8287, 12288, 12288, 65279, 65279);
    public static final CodePointSet LEGACY_NON_WHITE_SPACE = CodePointSet.createNoDedup(0, 8, 14, 31, 33, 159, 161, 5759, 5761, 6157, 6159, 8191, 8203, 8231, 8234, 8238, 8240, 8286, 8288, 12287, 12289, 65278, 65280, 0x10FFFF);
    public static final CodePointSet LINE_TERMINATOR = CodePointSet.createNoDedup(10, 10, 13, 13, 8232, 8233);
    public static final CodePointSet DOT = CodePointSet.createNoDedup(0, 9, 11, 12, 14, 8231, 8234, 0x10FFFF);
    public static final CodePointSet DOT_ALL = CodePointSet.createNoDedup(0, 0x10FFFF);
    public static final CodePointSet HEX_CHARS = CodePointSet.createNoDedup(48, 57, 65, 70, 97, 102);
    public static final CodePointSet[] CONSTANT_CODE_POINT_SETS = new CodePointSet[]{DIGITS, NON_DIGITS, WORD_CHARS, NON_WORD_CHARS, WHITE_SPACE, NON_WHITE_SPACE, LINE_TERMINATOR, DOT, HEX_CHARS};
}

