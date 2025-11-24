
package com.oracle.truffle.api.strings;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.TruffleOptions;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.api.strings.AbstractTruffleString;
import com.oracle.truffle.api.strings.JCodingsDisabled;
import com.oracle.truffle.api.strings.JCodingsImpl;
import com.oracle.truffle.api.strings.TStringAccessor;
import com.oracle.truffle.api.strings.TStringInternalNodes;
import com.oracle.truffle.api.strings.TruffleString;

interface JCodings {
    public static final boolean ENABLED = !TruffleOptions.AOT || TStringAccessor.getNeedsAllEncodings();
    public static final JCodings INSTANCE = ENABLED ? new JCodingsImpl() : new JCodingsDisabled();

    public static JCodings getInstance() {
        return INSTANCE;
    }

    public static byte[] asByteArray(Object array) {
        if (array instanceof AbstractTruffleString.NativePointer) {
            return ((AbstractTruffleString.NativePointer)array).getBytes();
        }
        return (byte[])array;
    }

    public Encoding get(String var1);

    public Encoding get(TruffleString.Encoding var1);

    public String name(Encoding var1);

    public int minLength(Encoding var1);

    public int maxLength(Encoding var1);

    public boolean isFixedWidth(Encoding var1);

    public boolean isSingleByte(Encoding var1);

    @CompilerDirectives.TruffleBoundary
    public int getCodePointLength(Encoding var1, int var2);

    @CompilerDirectives.TruffleBoundary
    public int getPreviousCodePointIndex(Encoding var1, byte[] var2, int var3, int var4, int var5);

    @CompilerDirectives.TruffleBoundary
    public int getCodePointLength(Encoding var1, byte[] var2, int var3, int var4);

    @CompilerDirectives.TruffleBoundary
    public int readCodePoint(Encoding var1, byte[] var2, int var3, int var4);

    @CompilerDirectives.TruffleBoundary
    public int writeCodePoint(Encoding var1, int var2, byte[] var3, int var4);

    @CompilerDirectives.TruffleBoundary
    public int codePointIndexToRaw(Node var1, AbstractTruffleString var2, byte[] var3, int var4, int var5, boolean var6, Encoding var7);

    public int decode(AbstractTruffleString var1, byte[] var2, int var3, Encoding var4, TruffleString.ErrorHandling var5);

    public long calcStringAttributes(Node var1, Object var2, int var3, int var4, TruffleString.Encoding var5, ConditionProfile var6, ConditionProfile var7);

    public TruffleString transcode(Node var1, AbstractTruffleString var2, Object var3, int var4, TruffleString.Encoding var5, BranchProfile var6, ConditionProfile var7, TStringInternalNodes.FromBufferWithStringCompactionNode var8);

    public static interface Encoding {
    }
}

