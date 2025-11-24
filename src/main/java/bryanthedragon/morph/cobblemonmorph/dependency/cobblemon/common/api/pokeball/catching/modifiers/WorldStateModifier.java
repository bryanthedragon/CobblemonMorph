/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.functions.Function2
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.world.entity.LivingEntity
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.catching.modifiers;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.catching.CatchRateModifier;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import kotlin.Metadata;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0016\u0018\u00002\u00020\u0001B?\u00126\u0010\u0017\u001a2\u0012\u0013\u0012\u00110\u0002\u00a2\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(\u0003\u0012\u0013\u0012\u00110\u0015\u00a2\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(\u0016\u0012\u0004\u0012\u00020\f0\u0012\u00a2\u0006\u0004\b\u0019\u0010\u001aJ\u001f\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0004H\u0016\u00a2\u0006\u0004\b\u0007\u0010\bJ\u001f\u0010\n\u001a\u00020\t2\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0004H\u0016\u00a2\u0006\u0004\b\n\u0010\u000bJ'\u0010\u000e\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\f2\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0004H\u0016\u00a2\u0006\u0004\b\u000e\u0010\u000fJ\u001f\u0010\u0010\u001a\u00020\f2\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0004H\u0016\u00a2\u0006\u0004\b\u0010\u0010\u0011RD\u0010\u0017\u001a2\u0012\u0013\u0012\u00110\u0002\u00a2\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(\u0003\u0012\u0013\u0012\u00110\u0015\u00a2\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(\u0016\u0012\u0004\u0012\u00020\f0\u00128\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u0017\u0010\u0018\u00a8\u0006\u001b"}, d2={"Lcom/cobblemon/mod/common/api/pokeball/catching/modifiers/WorldStateModifier;", "Lcom/cobblemon/mod/common/api/pokeball/catching/CatchRateModifier;", "Lnet/minecraft/world/entity/LivingEntity;", "thrower", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "pokemon", "Lcom/cobblemon/mod/common/api/pokeball/catching/CatchRateModifier$Behavior;", "behavior", "(Lnet/minecraft/world/entity/LivingEntity;Lcom/cobblemon/mod/common/pokemon/Pokemon;)Lcom/cobblemon/mod/common/api/pokeball/catching/CatchRateModifier$Behavior;", "", "isValid", "(Lnet/minecraft/world/entity/LivingEntity;Lcom/cobblemon/mod/common/pokemon/Pokemon;)Z", "", "currentCatchRate", "modifyCatchRate", "(FLnet/minecraft/world/entity/LivingEntity;Lcom/cobblemon/mod/common/pokemon/Pokemon;)F", "value", "(Lnet/minecraft/world/entity/LivingEntity;Lcom/cobblemon/mod/common/pokemon/Pokemon;)F", "Lkotlin/Function2;", "Lkotlin/ParameterName;", "name", "Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;", "entity", "calculator", "Lkotlin/jvm/functions/Function2;", "<init>", "(Lkotlin/jvm/functions/Function2;)V", "common"})
public class WorldStateModifier
implements CatchRateModifier {
    @NotNull
    private final Function2<LivingEntity, PokemonEntity, Float> calculator;

    public WorldStateModifier(@NotNull Function2<? super LivingEntity, ? super PokemonEntity, Float> calculator) {
        Intrinsics.checkNotNullParameter(calculator, (String)"calculator");
        this.calculator = calculator;
    }

    @Override
    public float value(@NotNull LivingEntity thrower, @NotNull Pokemon pokemon) {
        Intrinsics.checkNotNullParameter((Object)thrower, (String)"thrower");
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        PokemonEntity pokemonEntity = pokemon.getEntity();
        if (pokemonEntity == null) {
            return 1.0f;
        }
        PokemonEntity entity2 = pokemonEntity;
        return ((Number)this.calculator.invoke((Object)thrower, (Object)entity2)).floatValue();
    }

    @Override
    @NotNull
    public CatchRateModifier.Behavior behavior(@NotNull LivingEntity thrower, @NotNull Pokemon pokemon) {
        Intrinsics.checkNotNullParameter((Object)thrower, (String)"thrower");
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        return CatchRateModifier.Behavior.MULTIPLY;
    }

    @Override
    public boolean isValid(@NotNull LivingEntity thrower, @NotNull Pokemon pokemon) {
        Intrinsics.checkNotNullParameter((Object)thrower, (String)"thrower");
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        return true;
    }

    @Override
    public float modifyCatchRate(float currentCatchRate, @NotNull LivingEntity thrower, @NotNull Pokemon pokemon) {
        Intrinsics.checkNotNullParameter((Object)thrower, (String)"thrower");
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        return ((Number)this.behavior(thrower, pokemon).getMutator().invoke((Object)Float.valueOf(currentCatchRate), (Object)Float.valueOf(this.value(thrower, pokemon)))).floatValue();
    }

    @Override
    public boolean isGuaranteed() {
        return CatchRateModifier.DefaultImpls.isGuaranteed(this);
    }
}

