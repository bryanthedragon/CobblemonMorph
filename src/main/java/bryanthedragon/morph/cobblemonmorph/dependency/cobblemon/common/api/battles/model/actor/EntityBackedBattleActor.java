/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  net.minecraft.world.entity.LivingEntity
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.actor;

import net.minecraft.world.entity.LivingEntity;

import org.jetbrains.annotations.Nullable;

public interface EntityBackedBattleActor<T extends LivingEntity> {
    @Nullable
    public T getEntity();
}

