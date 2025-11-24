/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.core.BlockPos
 *  net.minecraft.server.level.ServerLevel
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.spawner;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.SpawnCause;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.SpawnerManager;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.detail.SpawnPool;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.spawner.AreaSpawner;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.spawner.SpawningArea;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0007\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0016\u0018\u00002\u00020\u0001BI\u0012\u0006\u0010 \u001a\u00020\u001f\u0012\u0006\u0010\"\u001a\u00020!\u0012\u0006\u0010$\u001a\u00020#\u0012\u0006\u0010\u001b\u001a\u00020\u001a\u0012\u0006\u0010\r\u001a\u00020\f\u0012\u0006\u0010\b\u001a\u00020\u0007\u0012\u0006\u0010\u0018\u001a\u00020\u0007\u0012\b\b\u0002\u0010\u0012\u001a\u00020\u0011\u00a2\u0006\u0004\b%\u0010&J\u0019\u0010\u0005\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0005\u0010\u0006R\u0017\u0010\b\u001a\u00020\u00078\u0006\u00a2\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\n\u0010\u000bR\u0017\u0010\r\u001a\u00020\f8\u0006\u00a2\u0006\f\n\u0004\b\r\u0010\u000e\u001a\u0004\b\u000f\u0010\u0010R\"\u0010\u0012\u001a\u00020\u00118\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b\u0012\u0010\u0013\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017R\u0017\u0010\u0018\u001a\u00020\u00078\u0006\u00a2\u0006\f\n\u0004\b\u0018\u0010\t\u001a\u0004\b\u0019\u0010\u000bR\u0017\u0010\u001b\u001a\u00020\u001a8\u0006\u00a2\u0006\f\n\u0004\b\u001b\u0010\u001c\u001a\u0004\b\u001d\u0010\u001e\u00a8\u0006'"}, d2={"Lcom/cobblemon/mod/common/api/spawning/spawner/FixedAreaSpawner;", "Lcom/cobblemon/mod/common/api/spawning/spawner/AreaSpawner;", "Lcom/cobblemon/mod/common/api/spawning/SpawnCause;", "cause", "Lcom/cobblemon/mod/common/api/spawning/spawner/SpawningArea;", "getArea", "(Lcom/cobblemon/mod/common/api/spawning/SpawnCause;)Lcom/cobblemon/mod/common/api/spawning/spawner/SpawningArea;", "", "horizontalRadius", "I", "getHorizontalRadius", "()I", "Lnet/minecraft/core/BlockPos;", "position", "Lnet/minecraft/core/BlockPos;", "getPosition", "()Lnet/minecraft/core/BlockPos;", "", "ticksBetweenSpawns", "F", "getTicksBetweenSpawns", "()F", "setTicksBetweenSpawns", "(F)V", "verticalRadius", "getVerticalRadius", "Lnet/minecraft/server/level/ServerLevel;", "world", "Lnet/minecraft/server/level/ServerLevel;", "getWorld", "()Lnet/minecraft/server/level/ServerLevel;", "", "name", "Lcom/cobblemon/mod/common/api/spawning/detail/SpawnPool;", "spawns", "Lcom/cobblemon/mod/common/api/spawning/SpawnerManager;", "manager", "<init>", "(Ljava/lang/String;Lcom/cobblemon/mod/common/api/spawning/detail/SpawnPool;Lcom/cobblemon/mod/common/api/spawning/SpawnerManager;Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/core/BlockPos;IIF)V", "common"})
public class FixedAreaSpawner
extends AreaSpawner {
    @NotNull
    private final ServerLevel world;
    @NotNull
    private final BlockPos position;
    private final int horizontalRadius;
    private final int verticalRadius;
    private float ticksBetweenSpawns;

    public FixedAreaSpawner(@NotNull String name, @NotNull SpawnPool spawns2, @NotNull SpawnerManager manager, @NotNull ServerLevel world, @NotNull BlockPos position, int horizontalRadius, int verticalRadius, float ticksBetweenSpawns) {
        Intrinsics.checkNotNullParameter((Object)name, (String)"name");
        Intrinsics.checkNotNullParameter((Object)spawns2, (String)"spawns");
        Intrinsics.checkNotNullParameter((Object)manager, (String)"manager");
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        Intrinsics.checkNotNullParameter((Object)position, (String)"position");
        super(name, spawns2, manager);
        this.world = world;
        this.position = position;
        this.horizontalRadius = horizontalRadius;
        this.verticalRadius = verticalRadius;
        this.ticksBetweenSpawns = ticksBetweenSpawns;
    }

    public /* synthetic */ FixedAreaSpawner(String string, SpawnPool spawnPool, SpawnerManager spawnerManager, ServerLevel serverLevel, BlockPos blockPos2, int n, int n2, float f, int n3, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n3 & 0x80) != 0) {
            f = 20.0f;
        }
        this(string, spawnPool, spawnerManager, serverLevel, blockPos2, n, n2, f);
    }

    @NotNull
    public final ServerLevel getWorld() {
        return this.world;
    }

    @NotNull
    public final BlockPos getPosition() {
        return this.position;
    }

    public final int getHorizontalRadius() {
        return this.horizontalRadius;
    }

    public final int getVerticalRadius() {
        return this.verticalRadius;
    }

    @Override
    public float getTicksBetweenSpawns() {
        return this.ticksBetweenSpawns;
    }

    @Override
    public void setTicksBetweenSpawns(float f) {
        this.ticksBetweenSpawns = f;
    }

    @Override
    @Nullable
    public SpawningArea getArea(@NotNull SpawnCause cause) {
        Intrinsics.checkNotNullParameter((Object)cause, (String)"cause");
        BlockPos basePos = this.position.m_7918_(-this.horizontalRadius, -this.verticalRadius, -this.horizontalRadius);
        return new SpawningArea(cause, this.world, basePos.m_123341_(), basePos.m_123342_(), basePos.m_123343_(), this.horizontalRadius * 2 + 1, this.verticalRadius * 2 + 1, this.horizontalRadius * 2 + 1);
    }
}

