/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net;

import kotlin.Metadata;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\b\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0002\u0010\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006j\u0002\b\u0007j\u0002\b\b\u00a8\u0006\t"}, d2={"Lcom/cobblemon/mod/common/net/IntSize;", "", "<init>", "(Ljava/lang/String;I)V", "INT", "SHORT", "U_SHORT", "BYTE", "U_BYTE", "common"})
public final class IntSize
extends Enum<IntSize> {
    public static final /* enum */ IntSize INT = new IntSize();
    public static final /* enum */ IntSize SHORT = new IntSize();
    public static final /* enum */ IntSize U_SHORT = new IntSize();
    public static final /* enum */ IntSize BYTE = new IntSize();
    public static final /* enum */ IntSize U_BYTE = new IntSize();
    private static final /* synthetic */ IntSize[] $VALUES;

    public static IntSize[] values() {
        return (IntSize[])$VALUES.clone();
    }

    public static IntSize valueOf(String value2) {
        return Enum.valueOf(IntSize.class, value2);
    }

    static {
        $VALUES = intSizeArray = new IntSize[]{IntSize.INT, IntSize.SHORT, IntSize.U_SHORT, IntSize.BYTE, IntSize.U_BYTE};
    }
}

