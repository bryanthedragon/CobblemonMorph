/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.commands.arguments.EntityAnchorArgument$Anchor
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.entity.ai.goal.Goal
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.ai.goals;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.status.Statuses;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonBehaviourFlag;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.ai.FormPokemonBehaviour;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.status.PersistentStatusContainer;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.BlockPosExtensionsKt;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.Goal;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\t\u001a\u00020\b\u00a2\u0006\u0004\b\u000b\u0010\fJ\u000f\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0003\u0010\u0004J\u000f\u0010\u0006\u001a\u00020\u0005H\u0016\u00a2\u0006\u0004\b\u0006\u0010\u0007R\u0014\u0010\t\u001a\u00020\b8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\t\u0010\n\u00a8\u0006\r"}, d2={"Lcom/cobblemon/mod/common/entity/pokemon/ai/goals/PokemonPointAtSpawnGoal;", "Lnet/minecraft/world/entity/ai/goal/Goal;", "", "canStart", "()Z", "", "tick", "()V", "Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;", "pokemonEntity", "Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;", "<init>", "(Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;)V", "common"})
public final class PokemonPointAtSpawnGoal
extends Goal {
    @NotNull
    private final PokemonEntity pokemonEntity;

    public PokemonPointAtSpawnGoal(@NotNull PokemonEntity pokemonEntity) {
        Intrinsics.checkNotNullParameter((Object)pokemonEntity, (String)"pokemonEntity");
        this.pokemonEntity = pokemonEntity;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean m_8036_() {
        FormPokemonBehaviour behaviour = this.pokemonEntity.getBehaviour();
        if (!this.pokemonEntity.getBehaviour().getIdle().getPointsAtSpawn()) return false;
        if (!behaviour.getMoving().getCanLook()) return false;
        PersistentStatusContainer persistentStatusContainer = this.pokemonEntity.getPokemon().getStatus();
        if (Intrinsics.areEqual((Object)(persistentStatusContainer != null ? persistentStatusContainer.getStatus() : null), (Object)Statuses.INSTANCE.getSLEEP())) return false;
        if (this.pokemonEntity.isBattling()) return false;
        return true;
    }

    public void m_8037_() {
        if (!this.pokemonEntity.getBehaviourFlag(PokemonBehaviourFlag.LOOKING) && this.pokemonEntity.getNavigation().m_26571_()) {
            BlockPos blockPos2 = this.pokemonEntity.m_9236_().m_220360_();
            Intrinsics.checkNotNullExpressionValue((Object)blockPos2, (String)"pokemonEntity.world.spawnPos");
            this.pokemonEntity.m_7618_(EntityAnchorArgument.Anchor.EYES, BlockPosExtensionsKt.toVec3d(blockPos2));
        }
    }
}

