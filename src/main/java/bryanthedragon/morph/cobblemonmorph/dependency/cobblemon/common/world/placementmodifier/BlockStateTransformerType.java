/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.world.placementmodifier;

import kotlin.Metadata;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0005\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0002\u0010\u0003j\u0002\b\u0004j\u0002\b\u0005\u00a8\u0006\u0006"}, d2={"Lcom/cobblemon/mod/common/world/placementmodifier/BlockStateTransformerType;", "", "<init>", "(Ljava/lang/String;I)V", "NONE", "BERRY_TRANSFORM", "common"})
public final class BlockStateTransformerType
extends Enum<BlockStateTransformerType> {
    public static final /* enum */ BlockStateTransformerType NONE = new BlockStateTransformerType();
    public static final /* enum */ BlockStateTransformerType BERRY_TRANSFORM = new BlockStateTransformerType();
    private static final /* synthetic */ BlockStateTransformerType[] $VALUES;

    public static BlockStateTransformerType[] values() {
        return (BlockStateTransformerType[])$VALUES.clone();
    }

    public static BlockStateTransformerType valueOf(String value2) {
        return Enum.valueOf(BlockStateTransformerType.class, value2);
    }

    static {
        $VALUES = blockStateTransformerTypeArray = new BlockStateTransformerType[]{BlockStateTransformerType.NONE, BlockStateTransformerType.BERRY_TRANSFORM};
    }
}

