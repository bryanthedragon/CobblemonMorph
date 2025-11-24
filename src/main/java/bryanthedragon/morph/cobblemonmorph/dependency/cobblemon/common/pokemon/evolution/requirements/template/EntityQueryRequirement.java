/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.entity.LivingEntity
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.requirements.template;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.requirement.EvolutionRequirement;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001J\u0017\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0005\u0010\u0006J\u001f\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\u0007H&\u00a2\u0006\u0004\b\u0005\u0010\t\u00a8\u0006\n"}, d2={"Lcom/cobblemon/mod/common/pokemon/evolution/requirements/template/EntityQueryRequirement;", "Lcom/cobblemon/mod/common/api/pokemon/evolution/requirement/EvolutionRequirement;", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "pokemon", "", "check", "(Lcom/cobblemon/mod/common/pokemon/Pokemon;)Z", "Lnet/minecraft/world/entity/LivingEntity;", "queriedEntity", "(Lcom/cobblemon/mod/common/pokemon/Pokemon;Lnet/minecraft/world/entity/LivingEntity;)Z", "common"})
public interface EntityQueryRequirement
extends EvolutionRequirement {
    @Override
    public boolean check(@NotNull Pokemon var1);

    public boolean check(@NotNull Pokemon var1, @NotNull LivingEntity var2);

    @Metadata(mv={1, 8, 0}, k=3, xi=48)
    public static final class DefaultImpls {
        public static boolean check(@NotNull EntityQueryRequirement $this, @NotNull Pokemon pokemon) {
            LivingEntity livingEntity;
            Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
            PokemonEntity pokemonEntity = pokemon.getEntity();
            if (pokemonEntity != null) {
                livingEntity = (LivingEntity)pokemonEntity;
            } else {
                ServerPlayer serverPlayer = pokemon.getOwnerPlayer();
                if (serverPlayer != null) {
                    livingEntity = (LivingEntity)serverPlayer;
                } else {
                    return false;
                }
            }
            LivingEntity queriedEntity = livingEntity;
            return $this.check(pokemon, queriedEntity);
        }
    }
}

