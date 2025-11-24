/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.Mob
 *  net.minecraft.world.entity.ai.goal.LookAtPlayerGoal
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.ai.goals;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.status.Statuses;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonBehaviourFlag;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.ai.FormPokemonBehaviour;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.status.PersistentStatusContainer;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0004\u0018\u00002\u00020\u0001B'\u0012\u0006\u0010\f\u001a\u00020\u000b\u0012\u000e\u0010\u000f\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u000e0\r\u0012\u0006\u0010\u0011\u001a\u00020\u0010\u00a2\u0006\u0004\b\u0012\u0010\u0013J\r\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0003\u0010\u0004J\u000f\u0010\u0005\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0005\u0010\u0004J\u000f\u0010\u0006\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0006\u0010\u0004J\u000f\u0010\b\u001a\u00020\u0007H\u0016\u00a2\u0006\u0004\b\b\u0010\tJ\u000f\u0010\n\u001a\u00020\u0007H\u0016\u00a2\u0006\u0004\b\n\u0010\t\u00a8\u0006\u0014"}, d2={"Lcom/cobblemon/mod/common/entity/pokemon/ai/goals/PokemonLookAtEntityGoal;", "Lnet/minecraft/world/entity/ai/goal/LookAtPlayerGoal;", "", "canLook", "()Z", "canStart", "shouldContinue", "", "start", "()V", "stop", "Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;", "entity", "Ljava/lang/Class;", "Lnet/minecraft/world/entity/LivingEntity;", "targetType", "", "range", "<init>", "(Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;Ljava/lang/Class;F)V", "common"})
public final class PokemonLookAtEntityGoal
extends LookAtPlayerGoal {
    public PokemonLookAtEntityGoal(@NotNull PokemonEntity entity2, @NotNull Class<? extends LivingEntity> targetType, float range) {
        Intrinsics.checkNotNullParameter((Object)entity2, (String)"entity");
        Intrinsics.checkNotNullParameter(targetType, (String)"targetType");
        super((Mob)entity2, targetType, range);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final boolean canLook() {
        Mob mob = this.f_25512_;
        Intrinsics.checkNotNull((Object)mob, (String)"null cannot be cast to non-null type bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity");
        PokemonEntity pokemon = (PokemonEntity)mob;
        boolean bl = false;
        FormPokemonBehaviour behaviour = pokemon.getBehaviour();
        if (!behaviour.getMoving().getCanLook()) return false;
        PersistentStatusContainer persistentStatusContainer = pokemon.getPokemon().getStatus();
        if (Intrinsics.areEqual((Object)(persistentStatusContainer != null ? persistentStatusContainer.getStatus() : null), (Object)Statuses.INSTANCE.getSLEEP())) return false;
        if (pokemon.isBattling()) return false;
        if (!behaviour.getMoving().getLooksAtEntities()) return false;
        return true;
    }

    public boolean m_8036_() {
        return super.m_8036_() && this.canLook();
    }

    public boolean m_8045_() {
        return super.m_8045_() && this.canLook();
    }

    public void m_8056_() {
        super.m_8056_();
        Mob mob = this.f_25512_;
        Intrinsics.checkNotNull((Object)mob, (String)"null cannot be cast to non-null type bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity");
        ((PokemonEntity)mob).setBehaviourFlag(PokemonBehaviourFlag.LOOKING, true);
    }

    public void m_8041_() {
        super.m_8041_();
        Mob mob = this.f_25512_;
        Intrinsics.checkNotNull((Object)mob, (String)"null cannot be cast to non-null type bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity");
        ((PokemonEntity)mob).setBehaviourFlag(PokemonBehaviourFlag.LOOKING, false);
    }
}

