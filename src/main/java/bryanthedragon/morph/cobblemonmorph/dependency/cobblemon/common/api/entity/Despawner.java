/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  net.minecraft.world.entity.Entity
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.entity;

import kotlin.Metadata;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\bf\u0018\u0000*\b\b\u0000\u0010\u0002*\u00020\u00012\u00020\u0003J\u0017\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0004\u001a\u00028\u0000H&\u00a2\u0006\u0004\b\u0006\u0010\u0007J\u0017\u0010\t\u001a\u00020\b2\u0006\u0010\u0004\u001a\u00028\u0000H&\u00a2\u0006\u0004\b\t\u0010\n\u00a8\u0006\u000b"}, d2={"Lcom/cobblemon/mod/common/api/entity/Despawner;", "Lnet/minecraft/world/entity/Entity;", "T", "", "entity", "", "beginTracking", "(Lnet/minecraft/world/entity/Entity;)V", "", "shouldDespawn", "(Lnet/minecraft/world/entity/Entity;)Z", "common"})
public interface Despawner<T extends Entity> {
    public void beginTracking(@NotNull T var1);

    public boolean shouldDespawn(@NotNull T var1);
}

