/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.world.entity.ai.goal.Goal
 *  net.minecraft.world.entity.ai.goal.Goal$Flag
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.ai.goals;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.status.Statuses;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.ai.RestBehaviour;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.status.PersistentStatusContainer;
import java.util.EnumSet;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.world.entity.ai.goal.Goal;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0010\u001a\u00020\u000f\u00a2\u0006\u0004\b\u0012\u0010\u0013J\u000f\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0003\u0010\u0004J\u000f\u0010\u0005\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0005\u0010\u0004J\u0015\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016\u00a2\u0006\u0004\b\b\u0010\tJ\u000f\u0010\n\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\n\u0010\u0004J\u000f\u0010\f\u001a\u00020\u000bH\u0016\u00a2\u0006\u0004\b\f\u0010\rJ\r\u0010\u000e\u001a\u00020\u000b\u00a2\u0006\u0004\b\u000e\u0010\rR\u0014\u0010\u0010\u001a\u00020\u000f8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u0010\u0010\u0011\u00a8\u0006\u0014"}, d2={"Lcom/cobblemon/mod/common/entity/pokemon/ai/goals/WildRestGoal;", "Lnet/minecraft/world/entity/ai/goal/Goal;", "", "canStart", "()Z", "canStop", "Ljava/util/EnumSet;", "Lnet/minecraft/entity/ai/goal/Goal$Control;", "getControls", "()Ljava/util/EnumSet;", "shouldContinue", "", "start", "()V", "wake", "Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;", "pokemonEntity", "Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;", "<init>", "(Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;)V", "common"})
public final class WildRestGoal
extends Goal {
    @NotNull
    private final PokemonEntity pokemonEntity;

    public WildRestGoal(@NotNull PokemonEntity pokemonEntity) {
        Intrinsics.checkNotNullParameter((Object)pokemonEntity, (String)"pokemonEntity");
        this.pokemonEntity = pokemonEntity;
    }

    @NotNull
    public EnumSet<Goal.Flag> m_7684_() {
        EnumSet<Goal.Flag> enumSet = EnumSet.allOf(Goal.Flag.class);
        Intrinsics.checkNotNullExpressionValue(enumSet, (String)"allOf(Control::class.java)");
        return enumSet;
    }

    public boolean m_8036_() {
        RestBehaviour rest = this.pokemonEntity.getBehaviour().getResting();
        return this.pokemonEntity.getPokemon().isWild() && !(this.pokemonEntity.m_217043_().m_188501_() < 1.0f - rest.getSleepChance()) && this.pokemonEntity.canSleep() && !this.pokemonEntity.isBusy() && rest.getDepth().canSleep(this.pokemonEntity);
    }

    public boolean m_6767_() {
        return false;
    }

    public boolean m_8045_() {
        boolean bl;
        if (this.pokemonEntity.canSleep() && !this.pokemonEntity.getBehaviour().getResting().getDepth().shouldWake(this.pokemonEntity)) {
            bl = true;
        } else {
            this.wake();
            bl = false;
        }
        return bl;
    }

    public void m_8056_() {
        this.pokemonEntity.getPokemon().setStatus(new PersistentStatusContainer(Statuses.INSTANCE.getSLEEP(), 0, 2, null));
    }

    public final void wake() {
        if (this.pokemonEntity.getBattleId() == null) {
            this.pokemonEntity.getPokemon().setStatus(null);
        }
    }
}

