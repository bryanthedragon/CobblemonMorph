
package org.graalvm.shadowed.org.jcodings.constants;

import org.graalvm.shadowed.org.jcodings.util.CaseInsensitiveBytesHash;

public class PosixBracket {
    public static final byte[][] PBSNamesLower = new byte[][]{"alnum".getBytes(), "alpha".getBytes(), "blank".getBytes(), "cntrl".getBytes(), "digit".getBytes(), "graph".getBytes(), "lower".getBytes(), "print".getBytes(), "punct".getBytes(), "space".getBytes(), "upper".getBytes(), "xdigit".getBytes(), "ascii".getBytes(), "word".getBytes()};
    public static final int[] PBSValues = new int[]{13, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 14, 12};
    public static final CaseInsensitiveBytesHash<Integer> PBSTableUpper = new CaseInsensitiveBytesHash(PBSNamesLower.length + 5);

    static {
        for (int i = 0; i < PBSValues.length; ++i) {
            PBSTableUpper.put(PBSNamesLower[i], PBSValues[i]);
        }
    }
}

