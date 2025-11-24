
package org.graalvm.shadowed.org.jcodings;

public final class CodeRange {
    public static boolean isInCodeRange(int[] p, int code) {
        return CodeRange.isInCodeRange(p, 0, code);
    }

    public static boolean isInCodeRange(int[] p, int offset, int code) {
        int n;
        int low = 0;
        int high = n = p[offset];
        while (low < high) {
            int x = low + high >> 1;
            if (code > p[(x << 1) + 2 + offset]) {
                low = x + 1;
                continue;
            }
            high = x;
        }
        return low < n && code >= p[(low << 1) + 1 + offset];
    }
}

