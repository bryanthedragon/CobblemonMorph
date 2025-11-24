/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  net.minecraft.network.syncher.EntityDataAccessor
 *  net.minecraft.world.entity.Entity
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.generic;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.entity.EntitySideDelegate;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.generic.GenericBedrockEntity;
import kotlin.Metadata;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\u00a2\u0006\u0004\b\u0003\u0010\u0004\u00a8\u0006\u0005"}, d2={"Lcom/cobblemon/mod/common/entity/generic/GenericBedrockServerDelegate;", "Lcom/cobblemon/mod/common/api/entity/EntitySideDelegate;", "Lcom/cobblemon/mod/common/entity/generic/GenericBedrockEntity;", "<init>", "()V", "common"})
public final class GenericBedrockServerDelegate
implements EntitySideDelegate<GenericBedrockEntity> {
    @Override
    public void initialize(@NotNull GenericBedrockEntity entity2) {
        EntitySideDelegate.DefaultImpls.initialize(this, (Entity)entity2);
    }

    @Override
    public void tick(@NotNull GenericBedrockEntity entity2) {
        EntitySideDelegate.DefaultImpls.tick(this, (Entity)entity2);
    }

    @Override
    public void onTrackedDataSet(@NotNull EntityDataAccessor<?> data) {
        EntitySideDelegate.DefaultImpls.onTrackedDataSet(this, data);
    }
}

