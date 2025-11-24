/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.core.BlockPos
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.tags.BlockTags
 *  net.minecraft.tags.TagKey
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.level.block.Block
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.influence;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.SpawnBucket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.SpawningContext;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.calculators.SpawningContextCalculator;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.detail.SpawnAction;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.detail.SpawnDetail;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.influence.SpawningInfluence;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0016\u0018\u00002\u00020\u0001B\u0007\u00a2\u0006\u0004\b\u000e\u0010\u000fJ\u001f\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0004H\u0016\u00a2\u0006\u0004\b\u0007\u0010\bR8\u0010\f\u001a&\u0012\f\u0012\n \u000b*\u0004\u0018\u00010\n0\n \u000b*\u0012\u0012\f\u0012\n \u000b*\u0004\u0018\u00010\n0\n\u0018\u00010\t0\t8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\f\u0010\r\u00a8\u0006\u0010"}, d2={"Lcom/cobblemon/mod/common/api/spawning/influence/RestrictedSpawnBlocksInfluence;", "Lcom/cobblemon/mod/common/api/spawning/influence/SpawningInfluence;", "Lcom/cobblemon/mod/common/api/spawning/detail/SpawnDetail;", "detail", "Lcom/cobblemon/mod/common/api/spawning/context/SpawningContext;", "ctx", "", "affectSpawnable", "(Lcom/cobblemon/mod/common/api/spawning/detail/SpawnDetail;Lcom/cobblemon/mod/common/api/spawning/context/SpawningContext;)Z", "Lnet/minecraft/tags/TagKey;", "Lnet/minecraft/world/level/block/Block;", "kotlin.jvm.PlatformType", "restrictedBlocks", "Lnet/minecraft/tags/TagKey;", "<init>", "()V", "common"})
public class RestrictedSpawnBlocksInfluence
implements SpawningInfluence {
    private final TagKey<Block> restrictedBlocks = BlockTags.f_13034_;

    @Override
    public boolean affectSpawnable(@NotNull SpawnDetail detail, @NotNull SpawningContext ctx) {
        Intrinsics.checkNotNullParameter((Object)detail, (String)"detail");
        Intrinsics.checkNotNullParameter((Object)ctx, (String)"ctx");
        if (!Intrinsics.areEqual((Object)detail.getType(), (Object)"pokemon")) {
            return true;
        }
        BlockPos pokemonSpawnPos = ctx.getPosition();
        return !ctx.getWorld().m_8055_(pokemonSpawnPos.m_175288_(pokemonSpawnPos.m_123342_() + 1)).m_204336_(this.restrictedBlocks);
    }

    @Override
    public boolean isExpired() {
        return SpawningInfluence.DefaultImpls.isExpired(this);
    }

    @Override
    public float affectWeight(@NotNull SpawnDetail detail, @NotNull SpawningContext ctx, float weight) {
        return SpawningInfluence.DefaultImpls.affectWeight(this, detail, ctx, weight);
    }

    @Override
    public void affectAction(@NotNull SpawnAction<?> action2) {
        SpawningInfluence.DefaultImpls.affectAction(this, action2);
    }

    @Override
    public void affectSpawn(@NotNull Entity entity2) {
        SpawningInfluence.DefaultImpls.affectSpawn(this, entity2);
    }

    @Override
    public float affectBucketWeight(@NotNull SpawnBucket bucket, float weight) {
        return SpawningInfluence.DefaultImpls.affectBucketWeight(this, bucket, weight);
    }

    @Override
    public boolean isAllowedPosition(@NotNull ServerLevel world, @NotNull BlockPos pos, @NotNull SpawningContextCalculator<?, ?> contextCalculator) {
        return SpawningInfluence.DefaultImpls.isAllowedPosition(this, world, pos, contextCalculator);
    }
}

