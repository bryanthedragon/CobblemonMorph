/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.random.Random
 *  kotlin.random.Random$Default
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.util.Mth
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.phys.Vec3
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.spawner;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.SpawnCause;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.SpawnerManager;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.detail.SpawnPool;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.spawner.AreaSpawner;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.spawner.SpawningArea;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtils;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.PlayerExtensionsKt;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.random.Random;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u001f\u0012\u0006\u0010\u0016\u001a\u00020\u0007\u0012\u0006\u0010\u0018\u001a\u00020\u0017\u0012\u0006\u0010\u001a\u001a\u00020\u0019\u00a2\u0006\u0004\b\u001b\u0010\u001cJ\u0019\u0010\u0005\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0005\u0010\u0006J\u0011\u0010\b\u001a\u0004\u0018\u00010\u0007H\u0016\u00a2\u0006\u0004\b\b\u0010\tR\"\u0010\u000b\u001a\u00020\n8\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b\u000b\u0010\f\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\u0017\u0010\u0012\u001a\u00020\u00118\u0006\u00a2\u0006\f\n\u0004\b\u0012\u0010\u0013\u001a\u0004\b\u0014\u0010\u0015\u00a8\u0006\u001d"}, d2={"Lcom/cobblemon/mod/common/api/spawning/spawner/PlayerSpawner;", "Lcom/cobblemon/mod/common/api/spawning/spawner/AreaSpawner;", "Lcom/cobblemon/mod/common/api/spawning/SpawnCause;", "cause", "Lcom/cobblemon/mod/common/api/spawning/spawner/SpawningArea;", "getArea", "(Lcom/cobblemon/mod/common/api/spawning/SpawnCause;)Lcom/cobblemon/mod/common/api/spawning/spawner/SpawningArea;", "Lnet/minecraft/server/level/ServerPlayer;", "getCauseEntity", "()Lnet/minecraft/server/level/ServerPlayer;", "", "ticksBetweenSpawns", "F", "getTicksBetweenSpawns", "()F", "setTicksBetweenSpawns", "(F)V", "Ljava/util/UUID;", "uuid", "Ljava/util/UUID;", "getUuid", "()Ljava/util/UUID;", "player", "Lcom/cobblemon/mod/common/api/spawning/detail/SpawnPool;", "spawns", "Lcom/cobblemon/mod/common/api/spawning/SpawnerManager;", "manager", "<init>", "(Lnet/minecraft/server/level/ServerPlayer;Lcom/cobblemon/mod/common/api/spawning/detail/SpawnPool;Lcom/cobblemon/mod/common/api/spawning/SpawnerManager;)V", "common"})
public final class PlayerSpawner
extends AreaSpawner {
    @NotNull
    private final UUID uuid;
    private float ticksBetweenSpawns;

    public PlayerSpawner(@NotNull ServerPlayer player, @NotNull SpawnPool spawns2, @NotNull SpawnerManager manager) {
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        Intrinsics.checkNotNullParameter((Object)spawns2, (String)"spawns");
        Intrinsics.checkNotNullParameter((Object)manager, (String)"manager");
        String string = player.m_7755_().getString();
        Intrinsics.checkNotNullExpressionValue((Object)string, (String)"player.name.string");
        super(string, spawns2, manager);
        UUID uUID = player.m_20148_();
        Intrinsics.checkNotNullExpressionValue((Object)uUID, (String)"player.uuid");
        this.uuid = uUID;
        this.ticksBetweenSpawns = Cobblemon.INSTANCE.getConfig().getTicksBetweenSpawnAttempts();
    }

    @NotNull
    public final UUID getUuid() {
        return this.uuid;
    }

    @Override
    public float getTicksBetweenSpawns() {
        return this.ticksBetweenSpawns;
    }

    @Override
    public void setTicksBetweenSpawns(float f) {
        this.ticksBetweenSpawns = f;
    }

    @Nullable
    public ServerPlayer getCauseEntity() {
        return PlayerExtensionsKt.getPlayer(this.uuid);
    }

    @Override
    @Nullable
    public SpawningArea getArea(@NotNull SpawnCause cause) {
        Intrinsics.checkNotNullParameter((Object)cause, (String)"cause");
        ServerPlayer serverPlayer = PlayerExtensionsKt.getPlayer(this.uuid);
        if (serverPlayer == null) {
            return null;
        }
        ServerPlayer player = serverPlayer;
        int sliceDiameter = Cobblemon.INSTANCE.getConfig().getWorldSliceDiameter();
        int sliceHeight = Cobblemon.INSTANCE.getConfig().getWorldSliceHeight();
        Random.Default rand = Random.Default;
        Vec3 center = player.m_20182_();
        float r = MiscUtils.nextBetween((Random)rand, Cobblemon.INSTANCE.getConfig().getMinimumSliceDistanceFromPlayer(), Cobblemon.INSTANCE.getConfig().getMaximumSliceDistanceFromPlayer());
        double thetatemp = Math.atan(player.m_20184_().f_82481_ / player.m_20184_().f_82479_) + (double)MiscUtils.nextBetween((Random)rand, -1.5707964f, 1.5707964f);
        double theta = player.m_20184_().m_165924_() < 0.1 ? rand.nextDouble() * (double)2 * (double)((float)Math.PI) : (player.m_20184_().f_82479_ < 0.0 ? (double)((float)Math.PI) - thetatemp : thetatemp);
        double x = center.f_82479_ + (double)r * Math.cos(theta);
        double z = center.f_82481_ + (double)r * Math.sin(theta);
        Level level = player.m_9236_();
        Intrinsics.checkNotNull((Object)level, (String)"null cannot be cast to non-null type net.minecraft.server.world.ServerWorld");
        return new SpawningArea(cause, (ServerLevel)level, Mth.m_14165_((double)(x - (double)((float)sliceDiameter / 2.0f))), Mth.m_14165_((double)(center.f_82480_ - (double)((float)sliceHeight / 2.0f))), Mth.m_14165_((double)(z - (double)((float)sliceDiameter / 2.0f))), sliceDiameter, sliceHeight, sliceDiameter);
    }
}

