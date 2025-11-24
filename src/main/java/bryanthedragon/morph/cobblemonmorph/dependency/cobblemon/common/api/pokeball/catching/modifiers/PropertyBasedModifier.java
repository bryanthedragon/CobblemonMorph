/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.world.entity.LivingEntity
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.catching.modifiers;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.catching.CatchRateModifier;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonProperties;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0007\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0019\u001a\u00020\u0018\u0012\u0006\u0010\u0014\u001a\u00020\u000e\u00a2\u0006\u0004\b\u001d\u0010\u001eJ\u001f\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0004H\u0016\u00a2\u0006\u0004\b\u0007\u0010\bJ\u000f\u0010\n\u001a\u00020\tH\u0016\u00a2\u0006\u0004\b\n\u0010\u000bJ\u001f\u0010\f\u001a\u00020\t2\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0004H\u0016\u00a2\u0006\u0004\b\f\u0010\rJ'\u0010\u0010\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0004H\u0016\u00a2\u0006\u0004\b\u0010\u0010\u0011J\u001f\u0010\u0012\u001a\u00020\u000e2\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0004H\u0016\u00a2\u0006\u0004\b\u0012\u0010\u0013R\u0017\u0010\u0014\u001a\u00020\u000e8\u0006\u00a2\u0006\f\n\u0004\b\u0014\u0010\u0015\u001a\u0004\b\u0016\u0010\u0017R\u0017\u0010\u0019\u001a\u00020\u00188\u0006\u00a2\u0006\f\n\u0004\b\u0019\u0010\u001a\u001a\u0004\b\u001b\u0010\u001c\u00a8\u0006\u001f"}, d2={"Lcom/cobblemon/mod/common/api/pokeball/catching/modifiers/PropertyBasedModifier;", "Lcom/cobblemon/mod/common/api/pokeball/catching/CatchRateModifier;", "Lnet/minecraft/world/entity/LivingEntity;", "thrower", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "pokemon", "Lcom/cobblemon/mod/common/api/pokeball/catching/CatchRateModifier$Behavior;", "behavior", "(Lnet/minecraft/world/entity/LivingEntity;Lcom/cobblemon/mod/common/pokemon/Pokemon;)Lcom/cobblemon/mod/common/api/pokeball/catching/CatchRateModifier$Behavior;", "", "isGuaranteed", "()Z", "isValid", "(Lnet/minecraft/world/entity/LivingEntity;Lcom/cobblemon/mod/common/pokemon/Pokemon;)Z", "", "currentCatchRate", "modifyCatchRate", "(FLnet/minecraft/world/entity/LivingEntity;Lcom/cobblemon/mod/common/pokemon/Pokemon;)F", "value", "(Lnet/minecraft/world/entity/LivingEntity;Lcom/cobblemon/mod/common/pokemon/Pokemon;)F", "multiplier", "F", "getMultiplier", "()F", "Lcom/cobblemon/mod/common/api/pokemon/PokemonProperties;", "property", "Lcom/cobblemon/mod/common/api/pokemon/PokemonProperties;", "getProperty", "()Lcom/cobblemon/mod/common/api/pokemon/PokemonProperties;", "<init>", "(Lcom/cobblemon/mod/common/api/pokemon/PokemonProperties;F)V", "common"})
public final class PropertyBasedModifier
implements CatchRateModifier {
    @NotNull
    private final PokemonProperties property;
    private final float multiplier;

    public PropertyBasedModifier(@NotNull PokemonProperties property, float multiplier) {
        Intrinsics.checkNotNullParameter((Object)property, (String)"property");
        this.property = property;
        this.multiplier = multiplier;
    }

    @NotNull
    public final PokemonProperties getProperty() {
        return this.property;
    }

    public final float getMultiplier() {
        return this.multiplier;
    }

    @Override
    public boolean isGuaranteed() {
        return false;
    }

    @Override
    public float value(@NotNull LivingEntity thrower, @NotNull Pokemon pokemon) {
        Intrinsics.checkNotNullParameter((Object)thrower, (String)"thrower");
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        return this.multiplier;
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
        return this.property.matches(pokemon);
    }

    @Override
    public float modifyCatchRate(float currentCatchRate, @NotNull LivingEntity thrower, @NotNull Pokemon pokemon) {
        Intrinsics.checkNotNullParameter((Object)thrower, (String)"thrower");
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        return ((Number)this.behavior(thrower, pokemon).getMutator().invoke((Object)Float.valueOf(currentCatchRate), (Object)Float.valueOf(this.value(thrower, pokemon)))).floatValue();
    }
}

