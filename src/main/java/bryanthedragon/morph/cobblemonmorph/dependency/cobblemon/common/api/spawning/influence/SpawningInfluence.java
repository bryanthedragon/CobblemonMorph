/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.core.BlockPos
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.world.entity.Entity
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.influence;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.SpawnBucket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.SpawningContext;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.calculators.SpawningContextCalculator;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.detail.SpawnAction;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.detail.SpawnDetail;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000T\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\bf\u0018\u00002\u00020\u0001J\u001b\u0010\u0005\u001a\u00020\u00042\n\u0010\u0003\u001a\u0006\u0012\u0002\b\u00030\u0002H\u0016\u00a2\u0006\u0004\b\u0005\u0010\u0006J\u001f\u0010\u000b\u001a\u00020\t2\u0006\u0010\b\u001a\u00020\u00072\u0006\u0010\n\u001a\u00020\tH\u0016\u00a2\u0006\u0004\b\u000b\u0010\fJ\u0017\u0010\u000f\u001a\u00020\u00042\u0006\u0010\u000e\u001a\u00020\rH\u0016\u00a2\u0006\u0004\b\u000f\u0010\u0010J\u001f\u0010\u0016\u001a\u00020\u00152\u0006\u0010\u0012\u001a\u00020\u00112\u0006\u0010\u0014\u001a\u00020\u0013H\u0016\u00a2\u0006\u0004\b\u0016\u0010\u0017J'\u0010\u0018\u001a\u00020\t2\u0006\u0010\u0012\u001a\u00020\u00112\u0006\u0010\u0014\u001a\u00020\u00132\u0006\u0010\n\u001a\u00020\tH\u0016\u00a2\u0006\u0004\b\u0018\u0010\u0019J/\u0010 \u001a\u00020\u00152\u0006\u0010\u001b\u001a\u00020\u001a2\u0006\u0010\u001d\u001a\u00020\u001c2\u000e\u0010\u001f\u001a\n\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u001eH\u0016\u00a2\u0006\u0004\b \u0010!J\u000f\u0010\"\u001a\u00020\u0015H\u0016\u00a2\u0006\u0004\b\"\u0010#\u00a8\u0006$"}, d2={"Lcom/cobblemon/mod/common/api/spawning/influence/SpawningInfluence;", "", "Lcom/cobblemon/mod/common/api/spawning/detail/SpawnAction;", "action", "", "affectAction", "(Lcom/cobblemon/mod/common/api/spawning/detail/SpawnAction;)V", "Lcom/cobblemon/mod/common/api/spawning/SpawnBucket;", "bucket", "", "weight", "affectBucketWeight", "(Lcom/cobblemon/mod/common/api/spawning/SpawnBucket;F)F", "Lnet/minecraft/world/entity/Entity;", "entity", "affectSpawn", "(Lnet/minecraft/world/entity/Entity;)V", "Lcom/cobblemon/mod/common/api/spawning/detail/SpawnDetail;", "detail", "Lcom/cobblemon/mod/common/api/spawning/context/SpawningContext;", "ctx", "", "affectSpawnable", "(Lcom/cobblemon/mod/common/api/spawning/detail/SpawnDetail;Lcom/cobblemon/mod/common/api/spawning/context/SpawningContext;)Z", "affectWeight", "(Lcom/cobblemon/mod/common/api/spawning/detail/SpawnDetail;Lcom/cobblemon/mod/common/api/spawning/context/SpawningContext;F)F", "Lnet/minecraft/server/level/ServerLevel;", "world", "Lnet/minecraft/core/BlockPos;", "pos", "Lcom/cobblemon/mod/common/api/spawning/context/calculators/SpawningContextCalculator;", "contextCalculator", "isAllowedPosition", "(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/core/BlockPos;Lcom/cobblemon/mod/common/api/spawning/context/calculators/SpawningContextCalculator;)Z", "isExpired", "()Z", "common"})
public interface SpawningInfluence {
    public boolean isExpired();

    public boolean affectSpawnable(@NotNull SpawnDetail var1, @NotNull SpawningContext var2);

    public float affectWeight(@NotNull SpawnDetail var1, @NotNull SpawningContext var2, float var3);

    public void affectAction(@NotNull SpawnAction<?> var1);

    public void affectSpawn(@NotNull Entity var1);

    public float affectBucketWeight(@NotNull SpawnBucket var1, float var2);

    public boolean isAllowedPosition(@NotNull ServerLevel var1, @NotNull BlockPos var2, @NotNull SpawningContextCalculator<?, ?> var3);

    @Metadata(mv={1, 8, 0}, k=3, xi=48)
    public static final class DefaultImpls {
        public static boolean isExpired(@NotNull SpawningInfluence $this) {
            return false;
        }

        public static boolean affectSpawnable(@NotNull SpawningInfluence $this, @NotNull SpawnDetail detail, @NotNull SpawningContext ctx) {
            Intrinsics.checkNotNullParameter((Object)detail, (String)"detail");
            Intrinsics.checkNotNullParameter((Object)ctx, (String)"ctx");
            return true;
        }

        public static float affectWeight(@NotNull SpawningInfluence $this, @NotNull SpawnDetail detail, @NotNull SpawningContext ctx, float weight) {
            Intrinsics.checkNotNullParameter((Object)detail, (String)"detail");
            Intrinsics.checkNotNullParameter((Object)ctx, (String)"ctx");
            return weight;
        }

        public static void affectAction(@NotNull SpawningInfluence $this, @NotNull SpawnAction<?> action2) {
            Intrinsics.checkNotNullParameter(action2, (String)"action");
        }

        public static void affectSpawn(@NotNull SpawningInfluence $this, @NotNull Entity entity2) {
            Intrinsics.checkNotNullParameter((Object)entity2, (String)"entity");
        }

        public static float affectBucketWeight(@NotNull SpawningInfluence $this, @NotNull SpawnBucket bucket, float weight) {
            Intrinsics.checkNotNullParameter((Object)bucket, (String)"bucket");
            return weight;
        }

        public static boolean isAllowedPosition(@NotNull SpawningInfluence $this, @NotNull ServerLevel world, @NotNull BlockPos pos, @NotNull SpawningContextCalculator<?, ?> contextCalculator) {
            Intrinsics.checkNotNullParameter((Object)world, (String)"world");
            Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
            Intrinsics.checkNotNullParameter(contextCalculator, (String)"contextCalculator");
            return true;
        }
    }
}

