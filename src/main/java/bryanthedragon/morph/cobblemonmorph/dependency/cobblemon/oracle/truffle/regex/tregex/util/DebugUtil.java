
package com.oracle.truffle.regex.tregex.util;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.regex.charset.Constants;
import com.oracle.truffle.regex.charset.SortedListOfRanges;
import com.oracle.truffle.regex.util.TBitSet;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DebugUtil {
    private static final TBitSet validSpecialCharsForFileNames = TBitSet.valueOf(36, 40, 41, 42, 43, 45, 46, 63, 91, 93, 94, 123, 124, 125);
    private static final Pattern specialChars = Pattern.compile("[\"\\\\\u0000-\u001f\u007f-\u009f]");

    @CompilerDirectives.TruffleBoundary
    public static String charToString(int c) {
        if (Constants.WORD_CHARS.contains(c)) {
            return String.valueOf((char)c);
        }
        if (c <= 255) {
            return String.format("\\x%02x", c);
        }
        if (c <= 65535) {
            return String.format("\\u%04x", c);
        }
        return String.format("\\u{%06x}", c);
    }

    @CompilerDirectives.TruffleBoundary
    public static String escapeString(String s) {
        StringBuilder ret = new StringBuilder();
        for (int i = 0; i < s.length(); ++i) {
            ret.append(DebugUtil.charToString(s.charAt(i)));
        }
        return ret.toString();
    }

    @CompilerDirectives.TruffleBoundary
    public static StringBuilder appendNodeId(StringBuilder sb, int id) {
        return sb.append(DebugUtil.nodeID(id));
    }

    @CompilerDirectives.TruffleBoundary
    public static String nodeID(int id) {
        return String.format("%04x", id);
    }

    @CompilerDirectives.TruffleBoundary
    public static String jsStringEscape(String str) {
        StringBuffer escapedString = new StringBuffer();
        Matcher m = specialChars.matcher(str);
        while (m.find()) {
            String replacement;
            char c = str.charAt(m.start());
            switch (c) {
                case '\"': {
                    replacement = "\\\\\"";
                    break;
                }
                case '\\': {
                    replacement = "\\\\\\\\";
                    break;
                }
                case '\r': {
                    replacement = "\\\\r";
                    break;
                }
                case '\n': {
                    replacement = "\\\\n";
                    break;
                }
                case '\t': {
                    replacement = "\\\\t";
                    break;
                }
                default: {
                    assert (Character.isISOControl(c));
                    replacement = String.format("\\\\u%04x", c);
                }
            }
            m.appendReplacement(escapedString, replacement);
        }
        m.appendTail(escapedString);
        return escapedString.toString();
    }

    @CompilerDirectives.TruffleBoundary
    public static String randomJsStringFromRanges(SortedListOfRanges ranges, int length) {
        Random random = new Random(System.currentTimeMillis());
        StringBuilder stringBuilder = new StringBuilder(length);
        for (int i = 0; i < length; ++i) {
            int hi;
            int rangeIndex = random.nextInt(ranges.size());
            int lo = ranges.getLo(rangeIndex);
            int randChar = lo + random.nextInt((hi = ranges.getHi(rangeIndex)) + 1 - lo);
            if (randChar == 34) {
                stringBuilder.append("\\\\\"");
                continue;
            }
            if (randChar == 92) {
                stringBuilder.append("\\\\\\\\");
                continue;
            }
            if (randChar > 65535) {
                stringBuilder.append(String.format("\\u{%06x}", randChar));
                continue;
            }
            if (randChar > 127 || Character.isISOControl(randChar)) {
                stringBuilder.append(String.format("\\u%04x", randChar));
                continue;
            }
            stringBuilder.append(randChar);
        }
        return stringBuilder.toString();
    }

    public static boolean isValidCharForFileName(int c) {
        return Character.isLetterOrDigit(c) || validSpecialCharsForFileNames.get(c);
    }

    public static class Timer {
        private long startTime = 0L;

        public void start() {
            this.startTime = System.nanoTime();
        }

        public long getElapsed() {
            return System.nanoTime() - this.startTime;
        }

        public String elapsedToString() {
            return Timer.elapsedToString(this.getElapsed());
        }

        public static String elapsedToString(long elapsed) {
            return String.format("%fms", (double)elapsed / 1000000.0);
        }
    }
}

