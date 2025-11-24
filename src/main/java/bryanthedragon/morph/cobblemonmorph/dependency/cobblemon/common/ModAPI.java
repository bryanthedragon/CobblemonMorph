/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common;

import kotlin.Metadata;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0005\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0002\u0010\u0003j\u0002\b\u0004j\u0002\b\u0005\u00a8\u0006\u0006"}, d2={"Lcom/cobblemon/mod/common/ModAPI;", "", "<init>", "(Ljava/lang/String;I)V", "FABRIC", "FORGE", "common"})
public final class ModAPI
extends Enum<ModAPI> {
    public static final /* enum */ ModAPI FABRIC = new ModAPI();
    public static final /* enum */ ModAPI FORGE = new ModAPI();
    private static final /* synthetic */ ModAPI[] $VALUES;

    public static ModAPI[] values() {
        return (ModAPI[])$VALUES.clone();
    }

    public static ModAPI valueOf(String value2) {
        return Enum.valueOf(ModAPI.class, value2);
    }

    static {
        $VALUES = modAPIArray = new ModAPI[]{ModAPI.FABRIC, ModAPI.FORGE};
    }
}

