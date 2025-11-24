/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.world.entity.Entity
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.detail;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0015\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0002\u00a2\u0006\u0004\b\b\u0010\tR\u001d\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0004\u0010\u0005\u001a\u0004\b\u0006\u0010\u0007\u00a8\u0006\n"}, d2={"Lcom/cobblemon/mod/common/api/spawning/detail/EntitySpawnResult;", "", "", "Lnet/minecraft/world/entity/Entity;", "entities", "Ljava/util/List;", "getEntities", "()Ljava/util/List;", "<init>", "(Ljava/util/List;)V", "common"})
public final class EntitySpawnResult {
    @NotNull
    private final List<Entity> entities;

    public EntitySpawnResult(@NotNull List<? extends Entity> entities2) {
        Intrinsics.checkNotNullParameter(entities2, (String)"entities");
        this.entities = entities2;
    }

    @NotNull
    public final List<Entity> getEntities() {
        return this.entities;
    }
}

