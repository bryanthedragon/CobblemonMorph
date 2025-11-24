
package com.oracle.truffle.api.strings;

import com.oracle.truffle.api.strings.TSCodeRange;
import com.oracle.truffle.api.strings.TStringGuards;
import com.oracle.truffle.api.strings.TruffleString;

final class Stride {
    Stride() {
    }

    static boolean isStride(int stride) {
        return 0 <= stride && stride <= 2;
    }

    static int fromCodeRange(int codeRange, TruffleString.Encoding encoding) {
        if (TStringGuards.isUTF16(encoding)) {
            return Stride.fromCodeRangeUTF16(codeRange);
        }
        if (TStringGuards.isUTF32(encoding)) {
            return Stride.fromCodeRangeUTF32(codeRange);
        }
        return 0;
    }

    static int fromCodeRangeUTF16(int codeRange) {
        return TSCodeRange.toStrideUTF16(codeRange);
    }

    static int fromCodeRangeUTF32(int codeRange) {
        return TSCodeRange.toStrideUTF32(codeRange);
    }
}

