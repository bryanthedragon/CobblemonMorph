/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.math.SimpleMathExtensionsKt;
import kotlin.Metadata;

@Metadata(mv={1, 8, 0}, k=2, xi=48, d1={"\u0000\u0014\n\u0002\u0010\u0005\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0006\u001a\u001d\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0001\u001a\u00020\u00002\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006\u001a%\u0010\b\u001a\u00020\u00002\u0006\u0010\u0001\u001a\u00020\u00002\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0007\u001a\u00020\u0004\u00a2\u0006\u0004\b\b\u0010\t\u00a8\u0006\n"}, d2={"", "byte", "", "bit", "", "getBitForByte", "(BI)Z", "on", "setBitForByte", "(BIZ)B", "common"})
public final class BitUtilitiesKt {
    public static final byte setBitForByte(byte by, int bit, boolean on) {
        int bitAsByte = SimpleMathExtensionsKt.pow(2, bit - 1);
        return on ? (byte)(by | (byte)bitAsByte) : (byte)(by & (byte)(-bitAsByte - 1));
    }

    public static final boolean getBitForByte(byte by, int bit) {
        int bitAsByte = SimpleMathExtensionsKt.pow(2, bit - 1);
        return (byte)(by & (byte)bitAsByte) != 0;
    }
}

