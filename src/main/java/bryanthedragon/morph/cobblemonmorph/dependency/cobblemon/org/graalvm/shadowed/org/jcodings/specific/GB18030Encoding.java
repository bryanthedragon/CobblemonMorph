
package org.graalvm.shadowed.org.jcodings.specific;

import org.graalvm.shadowed.org.jcodings.IntHolder;
import org.graalvm.shadowed.org.jcodings.MultiByteEncoding;
import org.graalvm.shadowed.org.jcodings.ascii.AsciiTables;

public final class GB18030Encoding
extends MultiByteEncoding {
    private static final String GB18030 = "GB18030";
    private static final int C1 = 0;
    private static final int C2 = 1;
    private static final int C4 = 2;
    private static final int CM = 3;
    private static final int[] GB18030_MAP = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 0};
    private static final int[][] GB18030Trans = new int[][]{{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, -2}, {-2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, -2, -2, -2, -2, -2, -2, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -2, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -2}, {-2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, -2}, {-2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2}};
    public static final GB18030Encoding INSTANCE = new GB18030Encoding();

    protected GB18030Encoding() {
        super(GB18030, 1, 4, null, GB18030Trans, AsciiTables.AsciiCtypeTable);
    }

    @Override
    public int length(byte[] bytes, int p, int end2) {
        int s = this.TransZero[bytes[p] & 0xFF];
        if (s < 0) {
            return s == -1 ? 1 : -1;
        }
        return this.lengthForTwoUptoFour(bytes, p, end2, s);
    }

    private int lengthForTwoUptoFour(byte[] bytes, int p, int end2, int s) {
        if (++p == end2) {
            return this.missing(1);
        }
        if ((s = this.Trans[s][bytes[p] & 0xFF]) < 0) {
            return s == -1 ? 2 : -1;
        }
        return this.lengthForThreeUptoFour(bytes, p, end2, s);
    }

    private int lengthForThreeUptoFour(byte[] bytes, int p, int end2, int s) {
        if (++p == end2) {
            return this.missing(2);
        }
        if ((s = this.Trans[s][bytes[p] & 0xFF]) < 0) {
            return s == -1 ? 3 : -1;
        }
        if (++p == end2) {
            return this.missing(1);
        }
        return (s = this.Trans[s][bytes[p] & 0xFF]) == -1 ? 4 : -1;
    }

    @Override
    public int mbcToCode(byte[] bytes, int p, int end2) {
        return this.mbnMbcToCode(bytes, p, end2);
    }

    @Override
    public int codeToMbcLength(int code) {
        return this.mb4CodeToMbcLength(code);
    }

    @Override
    public int codeToMbc(int code, byte[] bytes, int p) {
        return this.mb4CodeToMbc(code, bytes, p);
    }

    @Override
    public int mbcCaseFold(int flag, byte[] bytes, IntHolder pp, int end2, byte[] lower) {
        return this.mbnMbcCaseFold(flag, bytes, pp, end2, lower);
    }

    @Override
    public boolean isCodeCType(int code, int ctype) {
        return this.mb4IsCodeCType(code, ctype);
    }

    @Override
    public int[] ctypeCodeRange(int ctype, IntHolder sbOut) {
        return null;
    }

    @Override
    public String getCharsetName() {
        return GB18030;
    }

    @Override
    public int leftAdjustCharHead(byte[] bytes, int start2, int s, int end2) {
        State state = State.START;
        block150: for (int p = s; p >= start2; --p) {
            int pByte = bytes[p] & 0xFF;
            switch (state) {
                case START: {
                    switch (GB18030_MAP[pByte]) {
                        case 0: {
                            return s;
                        }
                        case 1: {
                            state = State.One_C2;
                            break;
                        }
                        case 2: {
                            state = State.One_C4;
                            break;
                        }
                        case 3: {
                            state = State.One_CM;
                        }
                    }
                    continue block150;
                }
                case One_C2: {
                    switch (GB18030_MAP[pByte]) {
                        case 0: 
                        case 1: 
                        case 2: {
                            return s;
                        }
                        case 3: {
                            state = State.Odd_CM_One_CX;
                        }
                    }
                    continue block150;
                }
                case One_C4: {
                    switch (GB18030_MAP[pByte]) {
                        case 0: 
                        case 1: 
                        case 2: {
                            return s;
                        }
                        case 3: {
                            state = State.One_CMC4;
                        }
                    }
                    continue block150;
                }
                case One_CM: {
                    switch (GB18030_MAP[pByte]) {
                        case 0: 
                        case 1: {
                            return s;
                        }
                        case 2: {
                            state = State.Odd_C4CM;
                            break;
                        }
                        case 3: {
                            state = State.Odd_CM_One_CX;
                        }
                    }
                    continue block150;
                }
                case Odd_CM_One_CX: {
                    switch (GB18030_MAP[pByte]) {
                        case 0: 
                        case 1: 
                        case 2: {
                            return s - 1;
                        }
                        case 3: {
                            state = State.Even_CM_One_CX;
                        }
                    }
                    continue block150;
                }
                case Even_CM_One_CX: {
                    switch (GB18030_MAP[pByte]) {
                        case 0: 
                        case 1: 
                        case 2: {
                            return s;
                        }
                        case 3: {
                            state = State.Odd_CM_One_CX;
                        }
                    }
                    continue block150;
                }
                case One_CMC4: {
                    switch (GB18030_MAP[pByte]) {
                        case 0: 
                        case 1: {
                            return s - 1;
                        }
                        case 2: {
                            state = State.One_C4_Odd_CMC4;
                            break;
                        }
                        case 3: {
                            state = State.Even_CM_One_CX;
                        }
                    }
                    continue block150;
                }
                case Odd_CMC4: {
                    switch (GB18030_MAP[pByte]) {
                        case 0: 
                        case 1: {
                            return s - 1;
                        }
                        case 2: {
                            state = State.One_C4_Odd_CMC4;
                            break;
                        }
                        case 3: {
                            state = State.Odd_CM_Odd_CMC4;
                        }
                    }
                    continue block150;
                }
                case One_C4_Odd_CMC4: {
                    switch (GB18030_MAP[pByte]) {
                        case 0: 
                        case 1: 
                        case 2: {
                            return s - 1;
                        }
                        case 3: {
                            state = State.Even_CMC4;
                        }
                    }
                    continue block150;
                }
                case Even_CMC4: {
                    switch (GB18030_MAP[pByte]) {
                        case 0: 
                        case 1: {
                            return s - 3;
                        }
                        case 2: {
                            state = State.One_C4_Even_CMC4;
                            break;
                        }
                        case 3: {
                            state = State.Odd_CM_Even_CMC4;
                        }
                    }
                    continue block150;
                }
                case One_C4_Even_CMC4: {
                    switch (GB18030_MAP[pByte]) {
                        case 0: 
                        case 1: 
                        case 2: {
                            return s - 3;
                        }
                        case 3: {
                            state = State.Odd_CMC4;
                        }
                    }
                    continue block150;
                }
                case Odd_CM_Odd_CMC4: {
                    switch (GB18030_MAP[pByte]) {
                        case 0: 
                        case 1: 
                        case 2: {
                            return s - 3;
                        }
                        case 3: {
                            state = State.Even_CM_Odd_CMC4;
                        }
                    }
                    continue block150;
                }
                case Even_CM_Odd_CMC4: {
                    switch (GB18030_MAP[pByte]) {
                        case 0: 
                        case 1: 
                        case 2: {
                            return s - 1;
                        }
                        case 3: {
                            state = State.Odd_CM_Odd_CMC4;
                        }
                    }
                    continue block150;
                }
                case Odd_CM_Even_CMC4: {
                    switch (GB18030_MAP[pByte]) {
                        case 0: 
                        case 1: 
                        case 2: {
                            return s - 1;
                        }
                        case 3: {
                            state = State.Even_CM_Even_CMC4;
                        }
                    }
                    continue block150;
                }
                case Even_CM_Even_CMC4: {
                    switch (GB18030_MAP[pByte]) {
                        case 0: 
                        case 1: 
                        case 2: {
                            return s - 3;
                        }
                        case 3: {
                            state = State.Odd_CM_Even_CMC4;
                        }
                    }
                    continue block150;
                }
                case Odd_C4CM: {
                    switch (GB18030_MAP[pByte]) {
                        case 0: 
                        case 1: 
                        case 2: {
                            return s;
                        }
                        case 3: {
                            state = State.One_CM_Odd_C4CM;
                        }
                    }
                    continue block150;
                }
                case One_CM_Odd_C4CM: {
                    switch (GB18030_MAP[pByte]) {
                        case 0: 
                        case 1: {
                            return s - 2;
                        }
                        case 2: {
                            state = State.Even_C4CM;
                            break;
                        }
                        case 3: {
                            state = State.Even_CM_Odd_C4CM;
                        }
                    }
                    continue block150;
                }
                case Even_C4CM: {
                    switch (GB18030_MAP[pByte]) {
                        case 0: 
                        case 1: 
                        case 2: {
                            return s - 2;
                        }
                        case 3: {
                            state = State.One_CM_Even_C4CM;
                        }
                    }
                    continue block150;
                }
                case One_CM_Even_C4CM: {
                    switch (GB18030_MAP[pByte]) {
                        case 0: 
                        case 1: {
                            return s - 0;
                        }
                        case 2: {
                            state = State.Odd_C4CM;
                            break;
                        }
                        case 3: {
                            state = State.Even_CM_Even_C4CM;
                        }
                    }
                    continue block150;
                }
                case Even_CM_Odd_C4CM: {
                    switch (GB18030_MAP[pByte]) {
                        case 0: 
                        case 1: 
                        case 2: {
                            return s - 0;
                        }
                        case 3: {
                            state = State.Odd_CM_Odd_C4CM;
                        }
                    }
                    continue block150;
                }
                case Odd_CM_Odd_C4CM: {
                    switch (GB18030_MAP[pByte]) {
                        case 0: 
                        case 1: 
                        case 2: {
                            return s - 2;
                        }
                        case 3: {
                            state = State.Even_CM_Odd_C4CM;
                        }
                    }
                    continue block150;
                }
                case Even_CM_Even_C4CM: {
                    switch (GB18030_MAP[pByte]) {
                        case 0: 
                        case 1: 
                        case 2: {
                            return s - 2;
                        }
                        case 3: {
                            state = State.Odd_CM_Even_C4CM;
                        }
                    }
                    continue block150;
                }
                case Odd_CM_Even_C4CM: {
                    switch (GB18030_MAP[pByte]) {
                        case 0: 
                        case 1: 
                        case 2: {
                            return s - 0;
                        }
                        case 3: {
                            state = State.Even_CM_Even_C4CM;
                        }
                    }
                }
            }
        }
        switch (state) {
            case START: {
                return s - 0;
            }
            case One_C2: {
                return s - 0;
            }
            case One_C4: {
                return s - 0;
            }
            case One_CM: {
                return s - 0;
            }
            case Odd_CM_One_CX: {
                return s - 1;
            }
            case Even_CM_One_CX: {
                return s - 0;
            }
            case One_CMC4: {
                return s - 1;
            }
            case Odd_CMC4: {
                return s - 1;
            }
            case One_C4_Odd_CMC4: {
                return s - 1;
            }
            case Even_CMC4: {
                return s - 3;
            }
            case One_C4_Even_CMC4: {
                return s - 3;
            }
            case Odd_CM_Odd_CMC4: {
                return s - 3;
            }
            case Even_CM_Odd_CMC4: {
                return s - 1;
            }
            case Odd_CM_Even_CMC4: {
                return s - 1;
            }
            case Even_CM_Even_CMC4: {
                return s - 3;
            }
            case Odd_C4CM: {
                return s - 0;
            }
            case One_CM_Odd_C4CM: {
                return s - 2;
            }
            case Even_C4CM: {
                return s - 2;
            }
            case One_CM_Even_C4CM: {
                return s - 0;
            }
            case Even_CM_Odd_C4CM: {
                return s - 0;
            }
            case Odd_CM_Odd_C4CM: {
                return s - 2;
            }
            case Even_CM_Even_C4CM: {
                return s - 2;
            }
            case Odd_CM_Even_C4CM: {
                return s - 0;
            }
        }
        return s;
    }

    @Override
    public boolean isReverseMatchAllowed(byte[] bytes, int p, int end2) {
        return GB18030_MAP[bytes[p] & 0xFF] == 0;
    }

    private static enum State {
        START,
        One_C2,
        One_C4,
        One_CM,
        Odd_CM_One_CX,
        Even_CM_One_CX,
        One_CMC4,
        Odd_CMC4,
        One_C4_Odd_CMC4,
        Even_CMC4,
        One_C4_Even_CMC4,
        Odd_CM_Odd_CMC4,
        Even_CM_Odd_CMC4,
        Odd_CM_Even_CMC4,
        Even_CM_Even_CMC4,
        Odd_C4CM,
        One_CM_Odd_C4CM,
        Even_C4CM,
        One_CM_Even_C4CM,
        Even_CM_Odd_C4CM,
        Odd_CM_Odd_C4CM,
        Even_CM_Even_C4CM,
        Odd_CM_Even_C4CM;

    }
}

