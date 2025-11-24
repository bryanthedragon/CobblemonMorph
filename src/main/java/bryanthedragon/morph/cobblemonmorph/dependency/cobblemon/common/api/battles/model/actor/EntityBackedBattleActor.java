/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  net.minecraft.world.entity.LivingEntity
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.actor;

import kotlin.Metadata;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0004\bf\u0018\u0000*\b\b\u0000\u0010\u0002*\u00020\u00012\u00020\u0003R\u0016\u0010\u0006\u001a\u0004\u0018\u00018\u00008&X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005\u00a8\u0006\u0007"}, d2={"Lcom/cobblemon/mod/common/api/battles/model/actor/EntityBackedBattleActor;", "Lnet/minecraft/world/entity/LivingEntity;", "T", "", "getEntity", "()Lnet/minecraft/world/entity/LivingEntity;", "entity", "common"})
public interface EntityBackedBattleActor<T extends LivingEntity> {
    @Nullable
    public T getEntity();
}

