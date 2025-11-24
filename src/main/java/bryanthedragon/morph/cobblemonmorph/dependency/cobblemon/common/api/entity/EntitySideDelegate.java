/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.network.syncher.EntityDataAccessor
 *  net.minecraft.world.entity.Entity
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.entity;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\bf\u0018\u0000*\b\b\u0000\u0010\u0002*\u00020\u00012\u00020\u0003J\u0017\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0004\u001a\u00028\u0000H\u0016\u00a2\u0006\u0004\b\u0006\u0010\u0007J\u001b\u0010\n\u001a\u00020\u00052\n\u0010\t\u001a\u0006\u0012\u0002\b\u00030\bH\u0016\u00a2\u0006\u0004\b\n\u0010\u000bJ\u0017\u0010\f\u001a\u00020\u00052\u0006\u0010\u0004\u001a\u00028\u0000H\u0016\u00a2\u0006\u0004\b\f\u0010\u0007\u00a8\u0006\r"}, d2={"Lcom/cobblemon/mod/common/api/entity/EntitySideDelegate;", "Lnet/minecraft/world/entity/Entity;", "T", "", "entity", "", "initialize", "(Lnet/minecraft/world/entity/Entity;)V", "Lnet/minecraft/network/syncher/EntityDataAccessor;", "data", "onTrackedDataSet", "(Lnet/minecraft/network/syncher/EntityDataAccessor;)V", "tick", "common"})
public interface EntitySideDelegate<T extends Entity> {
    public void initialize(@NotNull T var1);

    public void tick(@NotNull T var1);

    public void onTrackedDataSet(@NotNull EntityDataAccessor<?> var1);

    @Metadata(mv={1, 8, 0}, k=3, xi=48)
    public static final class DefaultImpls {
        public static <T extends Entity> void initialize(@NotNull EntitySideDelegate<T> $this, @NotNull T entity2) {
            Intrinsics.checkNotNullParameter(entity2, (String)"entity");
        }

        public static <T extends Entity> void tick(@NotNull EntitySideDelegate<T> $this, @NotNull T entity2) {
            Intrinsics.checkNotNullParameter(entity2, (String)"entity");
        }

        public static <T extends Entity> void onTrackedDataSet(@NotNull EntitySideDelegate<T> $this, @NotNull EntityDataAccessor<?> data) {
            Intrinsics.checkNotNullParameter(data, (String)"data");
        }
    }
}

