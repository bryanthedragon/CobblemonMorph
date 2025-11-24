/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.world.entity.Entity
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.entity;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.Cancelable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.SpawningContext;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\u0018\u0000*\b\b\u0000\u0010\u0002*\u00020\u00012\u00020\u0003B\u0017\u0012\u0006\u0010\t\u001a\u00028\u0000\u0012\u0006\u0010\u0005\u001a\u00020\u0004\u00a2\u0006\u0004\b\r\u0010\u000eR\u0017\u0010\u0005\u001a\u00020\u00048\u0006\u00a2\u0006\f\n\u0004\b\u0005\u0010\u0006\u001a\u0004\b\u0007\u0010\bR\u0017\u0010\t\u001a\u00028\u00008\u0006\u00a2\u0006\f\n\u0004\b\t\u0010\n\u001a\u0004\b\u000b\u0010\f\u00a8\u0006\u000f"}, d2={"Lcom/cobblemon/mod/common/api/events/entity/SpawnEvent;", "Lnet/minecraft/world/entity/Entity;", "T", "Lcom/cobblemon/mod/common/api/events/Cancelable;", "Lcom/cobblemon/mod/common/api/spawning/context/SpawningContext;", "ctx", "Lcom/cobblemon/mod/common/api/spawning/context/SpawningContext;", "getCtx", "()Lcom/cobblemon/mod/common/api/spawning/context/SpawningContext;", "entity", "Lnet/minecraft/world/entity/Entity;", "getEntity", "()Lnet/minecraft/world/entity/Entity;", "<init>", "(Lnet/minecraft/world/entity/Entity;Lcom/cobblemon/mod/common/api/spawning/context/SpawningContext;)V", "common"})
public final class SpawnEvent<T extends Entity>
extends Cancelable {
    @NotNull
    private final T entity;
    @NotNull
    private final SpawningContext ctx;

    public SpawnEvent(@NotNull T entity2, @NotNull SpawningContext ctx) {
        Intrinsics.checkNotNullParameter(entity2, (String)"entity");
        Intrinsics.checkNotNullParameter((Object)ctx, (String)"ctx");
        this.entity = entity2;
        this.ctx = ctx;
    }

    @NotNull
    public final T getEntity() {
        return this.entity;
    }

    @NotNull
    public final SpawningContext getCtx() {
        return this.ctx;
    }
}

