/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.TamableAnimal
 *  net.minecraft.world.entity.ai.goal.FollowOwnerGoal
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.ai.goals;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.goal.FollowOwnerGoal;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0006\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0006\u0018\u00002\u00020\u0001B/\u0012\u0006\u0010\b\u001a\u00020\u0007\u0012\u0006\u0010\r\u001a\u00020\f\u0012\u0006\u0010\u000f\u001a\u00020\u000e\u0012\u0006\u0010\u0010\u001a\u00020\u000e\u0012\u0006\u0010\u0011\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0012\u0010\u0013J\r\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0003\u0010\u0004J\u000f\u0010\u0005\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0005\u0010\u0004J\u000f\u0010\u0006\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0006\u0010\u0004R\u0017\u0010\b\u001a\u00020\u00078\u0006\u00a2\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\n\u0010\u000b\u00a8\u0006\u0014"}, d2={"Lcom/cobblemon/mod/common/entity/pokemon/ai/goals/PokemonFollowOwnerGoal;", "Lnet/minecraft/world/entity/ai/goal/FollowOwnerGoal;", "", "canMove", "()Z", "canStart", "shouldContinue", "Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;", "entity", "Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;", "getEntity", "()Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;", "", "speed", "", "minDistance", "maxDistance", "leavesAllowed", "<init>", "(Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;DFFZ)V", "common"})
public final class PokemonFollowOwnerGoal
extends FollowOwnerGoal {
    @NotNull
    private final PokemonEntity entity;

    public PokemonFollowOwnerGoal(@NotNull PokemonEntity entity2, double speed, float minDistance, float maxDistance, boolean leavesAllowed) {
        Intrinsics.checkNotNullParameter((Object)entity2, (String)"entity");
        super((TamableAnimal)entity2, speed, minDistance, maxDistance, leavesAllowed);
        this.entity = entity2;
    }

    @NotNull
    public final PokemonEntity getEntity() {
        return this.entity;
    }

    public final boolean canMove() {
        return this.entity.getBehaviour().getMoving().getWalk().getCanWalk() || this.entity.getBehaviour().getMoving().getFly().getCanFly();
    }

    public boolean m_8036_() {
        return super.m_8036_() && this.canMove() && !this.entity.isBusy() && this.entity.getTethering() == null;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean m_8045_() {
        if (!super.m_8045_()) return false;
        if (!this.canMove()) return false;
        if (this.entity.getTethering() != null) return false;
        LivingEntity livingEntity = this.entity.m_269323_();
        float f = livingEntity != null ? livingEntity.m_20270_((Entity)this.entity) : 0.0f;
        if (!(f > this.f_25290_ / (float)2)) return false;
        return true;
    }
}

