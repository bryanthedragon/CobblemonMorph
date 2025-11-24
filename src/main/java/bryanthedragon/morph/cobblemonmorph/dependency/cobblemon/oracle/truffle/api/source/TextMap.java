
package com.oracle.truffle.api.source;

import java.util.ArrayList;

final class TextMap {
    private final int[] nlOffsets;
    private final int textLength;
    private final int newlineLength;
    private final int[] newlineLengths;
    final boolean finalNL;

    TextMap(int[] nlOffsets, int textLength, int newlineLength, int[] newlineLengths, boolean finalNL) {
        this.nlOffsets = nlOffsets;
        this.textLength = textLength;
        this.newlineLength = newlineLength;
        this.newlineLengths = newlineLengths;
        this.finalNL = finalNL;
    }

    public static TextMap fromCharSequence(CharSequence text) {
        int[] newlineLengths;
        ArrayList<Integer> lines;
        int textLength = text.length();
        int newlineLength = 0;
        ArrayList<Integer> nlLengths = null;
        block0: do {
            lines = new ArrayList<Integer>();
            lines.add(0);
            int offset = 0;
            if (newlineLength == -1) {
                nlLengths = new ArrayList<Integer>();
                newlineLength = -2;
            }
            while (offset < textLength) {
                int nlLength;
                int nlIndex;
                char c = '\u0000';
                for (nlIndex = offset; nlIndex < textLength && (c = text.charAt(nlIndex)) != '\n' && c != '\r'; ++nlIndex) {
                }
                if (nlIndex >= textLength || (newlineLength = TextMap.adjustNewlineLength(nlLength = TextMap.getNewlineLength(c, text, textLength, nlIndex), newlineLength, nlLengths)) == -1) continue block0;
                offset = nlIndex + nlLength;
                lines.add(offset);
            }
        } while (newlineLength == -1);
        lines.add(Integer.MAX_VALUE);
        int[] nlOffsets = TextMap.list2ints(lines);
        if (nlLengths != null) {
            assert (nlLengths.size() == lines.size() - 2);
            newlineLengths = TextMap.list2ints(nlLengths);
        } else {
            newlineLengths = null;
        }
        boolean finalNL = textLength > 0 && textLength == nlOffsets[nlOffsets.length - 2];
        return new TextMap(nlOffsets, textLength, newlineLength, newlineLengths, finalNL);
    }

    private static int getNewlineLength(char c, CharSequence text, int textLength, int nlIndex) {
        if (c == '\r' && nlIndex + 1 < textLength && text.charAt(nlIndex + 1) == '\n') {
            return 2;
        }
        return 1;
    }

    private static int adjustNewlineLength(int nlLength, int oldNewlineLength, ArrayList<Integer> nlLengths) {
        int newlineLength = oldNewlineLength;
        if (newlineLength >= 0) {
            if (newlineLength == 0) {
                newlineLength = nlLength;
            } else if (newlineLength != nlLength) {
                newlineLength = -1;
            }
        } else {
            nlLengths.add(nlLength);
        }
        return newlineLength;
    }

    private static int[] list2ints(ArrayList<Integer> list) {
        int size = list.size();
        int[] array = new int[size];
        for (int i = 0; i < size; ++i) {
            array[i] = list.get(i);
        }
        return array;
    }

    public int offsetToLine(int offset) throws IllegalArgumentException {
        if (offset < 0 || offset > this.textLength) {
            throw new IllegalArgumentException("offset out of bounds");
        }
        return TextMap.binarySearchLine(this.nlOffsets, offset) + 1;
    }

    private static int binarySearchLine(int[] a, int key) {
        int low = 0;
        int high = a.length - 1;
        int mid = 0;
        while (low <= high) {
            mid = low + high >>> 1;
            int midVal = a[mid];
            if (midVal < key) {
                low = mid + 1;
                continue;
            }
            if (midVal > key) {
                high = mid - 1;
                continue;
            }
            high = mid;
            break;
        }
        return high;
    }

    public int offsetToCol(int offset) throws IllegalArgumentException {
        return 1 + offset - this.nlOffsets[this.offsetToLine(offset) - 1];
    }

    public int length() {
        return this.textLength;
    }

    public int lineCount() {
        if (this.textLength == 0) {
            return 0;
        }
        return this.finalNL ? this.nlOffsets.length - 2 : this.nlOffsets.length - 1;
    }

    public int lineStartOffset(int line) throws IllegalArgumentException {
        if (this.lineOutOfRange(line)) {
            throw new IllegalArgumentException("line out of bounds");
        }
        return this.nlOffsets[line - 1];
    }

    public int lineLength(int line) throws IllegalArgumentException {
        if (this.lineOutOfRange(line)) {
            throw new IllegalArgumentException("line out of bounds");
        }
        if (line == this.nlOffsets.length - 1) {
            return this.textLength - this.nlOffsets[line - 1];
        }
        int nlLength = this.newlineLengths != null ? this.newlineLengths[line - 1] : this.newlineLength;
        return this.nlOffsets[line] - this.nlOffsets[line - 1] - nlLength;
    }

    public int lineColumnToOffset(int line, int column) {
        int lineStartOffset = this.lineStartOffset(line);
        if (column > this.lineLength(line) + 1) {
            throw new IllegalArgumentException("column out of range");
        }
        int charIndex = lineStartOffset + column - 1;
        return charIndex;
    }

    private boolean lineOutOfRange(int line) {
        return line <= 0 || line >= this.nlOffsets.length;
    }
}

