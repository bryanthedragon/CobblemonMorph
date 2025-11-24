/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.core.BlockPos
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.world.level.block.state.BlockState
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.SpawnCause;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.WorldSlice;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.AreaSpawningContext;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.influence.SpawningInfluence;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b&\u0018\u00002\u00020\u0001Bc\u0012\u0006\u0010\b\u001a\u00020\u0007\u0012\u0006\u0010\n\u001a\u00020\t\u0012\u0006\u0010\f\u001a\u00020\u000b\u0012\u0006\u0010\u000e\u001a\u00020\r\u0012\u0006\u0010\u000f\u001a\u00020\r\u0012\u0006\u0010\u0011\u001a\u00020\u0010\u0012\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00130\u0012\u0012\u0006\u0010\u0015\u001a\u00020\r\u0012\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00020\u0016\u0012\u0006\u0010\u0019\u001a\u00020\u0018\u00a2\u0006\u0004\b\u001a\u0010\u001bR\u0017\u0010\u0003\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0003\u0010\u0004\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u001c"}, d2={"Lcom/cobblemon/mod/common/api/spawning/context/FlooredSpawningContext;", "Lcom/cobblemon/mod/common/api/spawning/context/AreaSpawningContext;", "Lnet/minecraft/world/level/block/state/BlockState;", "baseBlock", "Lnet/minecraft/world/level/block/state/BlockState;", "getBaseBlock", "()Lnet/minecraft/world/level/block/state/BlockState;", "Lcom/cobblemon/mod/common/api/spawning/SpawnCause;", "cause", "Lnet/minecraft/server/level/ServerLevel;", "world", "Lnet/minecraft/core/BlockPos;", "position", "", "light", "skyLight", "", "canSeeSky", "", "Lcom/cobblemon/mod/common/api/spawning/influence/SpawningInfluence;", "influences", "height", "", "nearbyBlocks", "Lcom/cobblemon/mod/common/api/spawning/WorldSlice;", "slice", "<init>", "(Lcom/cobblemon/mod/common/api/spawning/SpawnCause;Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/core/BlockPos;IIZLjava/util/List;ILjava/util/List;Lcom/cobblemon/mod/common/api/spawning/WorldSlice;)V", "common"})
public abstract class FlooredSpawningContext
extends AreaSpawningContext {
    @NotNull
    private final BlockState baseBlock;

    public FlooredSpawningContext(@NotNull SpawnCause cause, @NotNull ServerLevel world, @NotNull BlockPos position, int light, int skyLight, boolean canSeeSky, @NotNull List<SpawningInfluence> influences, int height, @NotNull List<? extends BlockState> nearbyBlocks, @NotNull WorldSlice slice) {
        Intrinsics.checkNotNullParameter((Object)cause, (String)"cause");
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        Intrinsics.checkNotNullParameter((Object)position, (String)"position");
        Intrinsics.checkNotNullParameter(influences, (String)"influences");
        Intrinsics.checkNotNullParameter(nearbyBlocks, (String)"nearbyBlocks");
        Intrinsics.checkNotNullParameter((Object)slice, (String)"slice");
        super(cause, world, position, light, skyLight, canSeeSky, influences, height, nearbyBlocks, slice);
        this.baseBlock = WorldSlice.getBlockState$default(slice, position.m_123341_(), position.m_123342_(), position.m_123343_(), null, 8, null);
    }

    @NotNull
    public final BlockState getBaseBlock() {
        return this.baseBlock;
    }
}

