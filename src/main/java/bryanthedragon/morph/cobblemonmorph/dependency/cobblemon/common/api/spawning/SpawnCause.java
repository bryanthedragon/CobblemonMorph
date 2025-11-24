/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.world.entity.Entity
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.SpawnBucket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.spawner.Spawner;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0016\u0018\u00002\u00020\u0001B#\u0012\u0006\u0010\r\u001a\u00020\f\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u0007\u00a2\u0006\u0004\b\u0011\u0010\u0012R\u0017\u0010\u0003\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0003\u0010\u0004\u001a\u0004\b\u0005\u0010\u0006R\u0019\u0010\b\u001a\u0004\u0018\u00010\u00078\u0006\u00a2\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\n\u0010\u000bR\u0017\u0010\r\u001a\u00020\f8\u0006\u00a2\u0006\f\n\u0004\b\r\u0010\u000e\u001a\u0004\b\u000f\u0010\u0010\u00a8\u0006\u0013"}, d2={"Lcom/cobblemon/mod/common/api/spawning/SpawnCause;", "", "Lcom/cobblemon/mod/common/api/spawning/SpawnBucket;", "bucket", "Lcom/cobblemon/mod/common/api/spawning/SpawnBucket;", "getBucket", "()Lcom/cobblemon/mod/common/api/spawning/SpawnBucket;", "Lnet/minecraft/world/entity/Entity;", "entity", "Lnet/minecraft/world/entity/Entity;", "getEntity", "()Lnet/minecraft/world/entity/Entity;", "Lcom/cobblemon/mod/common/api/spawning/spawner/Spawner;", "spawner", "Lcom/cobblemon/mod/common/api/spawning/spawner/Spawner;", "getSpawner", "()Lcom/cobblemon/mod/common/api/spawning/spawner/Spawner;", "<init>", "(Lcom/cobblemon/mod/common/api/spawning/spawner/Spawner;Lcom/cobblemon/mod/common/api/spawning/SpawnBucket;Lnet/minecraft/world/entity/Entity;)V", "common"})
public class SpawnCause {
    @NotNull
    private final Spawner spawner;
    @NotNull
    private final SpawnBucket bucket;
    @Nullable
    private final Entity entity;

    public SpawnCause(@NotNull Spawner spawner, @NotNull SpawnBucket bucket, @Nullable Entity entity2) {
        Intrinsics.checkNotNullParameter((Object)spawner, (String)"spawner");
        Intrinsics.checkNotNullParameter((Object)bucket, (String)"bucket");
        this.spawner = spawner;
        this.bucket = bucket;
        this.entity = entity2;
    }

    public /* synthetic */ SpawnCause(Spawner spawner, SpawnBucket spawnBucket, Entity entity2, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 4) != 0) {
            entity2 = null;
        }
        this(spawner, spawnBucket, entity2);
    }

    @NotNull
    public final Spawner getSpawner() {
        return this.spawner;
    }

    @NotNull
    public final SpawnBucket getBucket() {
        return this.bucket;
    }

    @Nullable
    public final Entity getEntity() {
        return this.entity;
    }
}

