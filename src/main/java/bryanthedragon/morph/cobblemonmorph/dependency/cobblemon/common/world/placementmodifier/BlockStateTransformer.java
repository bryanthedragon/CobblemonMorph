/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.functions.Function1
 *  net.minecraft.world.level.block.state.BlockState
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.world.placementmodifier;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.codec.CodecMapped;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.data.ArbitrarilyMappedSerializableCompanion;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.world.placementmodifier.BerryTransformBlockStateTransformer;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.world.placementmodifier.BlockStateTransformerType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.world.placementmodifier.NoneBlockStateTransformer;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\bf\u0018\u0000 \n2\u00020\u0001:\u0001\nJ\u0017\u0010\u0004\u001a\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0002H&\u00a2\u0006\u0004\b\u0004\u0010\u0005R\u0014\u0010\t\u001a\u00020\u00068&X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0007\u0010\b\u00a8\u0006\u000b"}, d2={"Lcom/cobblemon/mod/common/world/placementmodifier/BlockStateTransformer;", "Lcom/cobblemon/mod/common/api/codec/CodecMapped;", "Lnet/minecraft/world/level/block/state/BlockState;", "blockState", "transform", "(Lnet/minecraft/world/level/block/state/BlockState;)Lnet/minecraft/world/level/block/state/BlockState;", "Lcom/cobblemon/mod/common/world/placementmodifier/BlockStateTransformerType;", "getType", "()Lcom/cobblemon/mod/common/world/placementmodifier/BlockStateTransformerType;", "type", "Companion", "common"})
public interface BlockStateTransformer
extends CodecMapped {
    @NotNull
    public static final Companion Companion = bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.world.placementmodifier.BlockStateTransformer$Companion.$$INSTANCE;

    @NotNull
    public BlockStateTransformerType getType();

    @NotNull
    public BlockState transform(@NotNull BlockState var1);

    static {
        bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.world.placementmodifier.BlockStateTransformer$Companion.$$INSTANCE.registerSubtype(BlockStateTransformerType.NONE, NoneBlockStateTransformer.class, NoneBlockStateTransformer.Companion.getCODEC());
        bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.world.placementmodifier.BlockStateTransformer$Companion.$$INSTANCE.registerSubtype(BlockStateTransformerType.BERRY_TRANSFORM, BerryTransformBlockStateTransformer.class, BerryTransformBlockStateTransformer.Companion.getCODEC());
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0004\u0010\u0005\u00a8\u0006\u0006"}, d2={"Lcom/cobblemon/mod/common/world/placementmodifier/BlockStateTransformer$Companion;", "Lcom/cobblemon/mod/common/api/data/ArbitrarilyMappedSerializableCompanion;", "Lcom/cobblemon/mod/common/world/placementmodifier/BlockStateTransformer;", "Lcom/cobblemon/mod/common/world/placementmodifier/BlockStateTransformerType;", "<init>", "()V", "common"})
    public static final class Companion
    extends ArbitrarilyMappedSerializableCompanion<BlockStateTransformer, BlockStateTransformerType> {
        static final /* synthetic */ Companion $$INSTANCE;

        private Companion() {
            super(1.INSTANCE, (Function1)2.INSTANCE, (Function1)3.INSTANCE);
        }

        static {
            $$INSTANCE = new Companion();
        }
    }
}

