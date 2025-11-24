/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon;

import kotlin.Metadata;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0006\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0002\u0010\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006\u00a8\u0006\u0007"}, d2={"Lcom/cobblemon/mod/common/pokemon/OriginalTrainerType;", "", "<init>", "(Ljava/lang/String;I)V", "NONE", "PLAYER", "NPC", "common"})
public final class OriginalTrainerType
extends Enum<OriginalTrainerType> {
    public static final /* enum */ OriginalTrainerType NONE = new OriginalTrainerType();
    public static final /* enum */ OriginalTrainerType PLAYER = new OriginalTrainerType();
    public static final /* enum */ OriginalTrainerType NPC = new OriginalTrainerType();
    private static final /* synthetic */ OriginalTrainerType[] $VALUES;

    public static OriginalTrainerType[] values() {
        return (OriginalTrainerType[])$VALUES.clone();
    }

    public static OriginalTrainerType valueOf(String value2) {
        return Enum.valueOf(OriginalTrainerType.class, value2);
    }

    static {
        $VALUES = originalTrainerTypeArray = new OriginalTrainerType[]{OriginalTrainerType.NONE, OriginalTrainerType.PLAYER, OriginalTrainerType.NPC};
    }
}

