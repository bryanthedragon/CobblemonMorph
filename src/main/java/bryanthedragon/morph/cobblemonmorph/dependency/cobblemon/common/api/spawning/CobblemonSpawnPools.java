/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.data.DataRegistry;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.condition.BucketPrecalculation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.condition.ContextPrecalculation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.condition.SpawningPrecalculation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.detail.SpawnPool;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.data.CobblemonDataProvider;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\b\u00c6\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\f\u0010\u0004J\r\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0003\u0010\u0004R\"\u0010\u0006\u001a\u00020\u00058\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\b\u0006\u0010\u0007\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000b\u00a8\u0006\r"}, d2={"Lcom/cobblemon/mod/common/api/spawning/CobblemonSpawnPools;", "", "", "load", "()V", "Lcom/cobblemon/mod/common/api/spawning/detail/SpawnPool;", "WORLD_SPAWN_POOL", "Lcom/cobblemon/mod/common/api/spawning/detail/SpawnPool;", "getWORLD_SPAWN_POOL", "()Lcom/cobblemon/mod/common/api/spawning/detail/SpawnPool;", "setWORLD_SPAWN_POOL", "(Lcom/cobblemon/mod/common/api/spawning/detail/SpawnPool;)V", "<init>", "common"})
public final class CobblemonSpawnPools {
    @NotNull
    public static final CobblemonSpawnPools INSTANCE = new CobblemonSpawnPools();
    public static SpawnPool WORLD_SPAWN_POOL;

    private CobblemonSpawnPools() {
    }

    @NotNull
    public final SpawnPool getWORLD_SPAWN_POOL() {
        SpawnPool spawnPool = WORLD_SPAWN_POOL;
        if (spawnPool != null) {
            return spawnPool;
        }
        Intrinsics.throwUninitializedPropertyAccessException((String)"WORLD_SPAWN_POOL");
        return null;
    }

    public final void setWORLD_SPAWN_POOL(@NotNull SpawnPool spawnPool) {
        Intrinsics.checkNotNullParameter((Object)spawnPool, (String)"<set-?>");
        WORLD_SPAWN_POOL = spawnPool;
    }

    public final void load() {
        SpawningPrecalculation[] spawningPrecalculationArray = new SpawningPrecalculation[]{ContextPrecalculation.INSTANCE, BucketPrecalculation.INSTANCE};
        this.setWORLD_SPAWN_POOL((SpawnPool)CobblemonDataProvider.INSTANCE.register((DataRegistry)new SpawnPool("world").addPrecalculators(spawningPrecalculationArray)));
    }
}

