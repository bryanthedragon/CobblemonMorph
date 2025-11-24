/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.world.entity.LivingEntity
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.catching.modifiers;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.catching.CatchRateModifier;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.stats.Stat;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0007\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B:\u0012\u0006\u0010!\u001a\u00020 \u0012!\u0010\u0018\u001a\u001d\u0012\u0013\u0012\u00110\u0015\u00a2\u0006\f\b\u0016\u0012\b\b\u0017\u0012\u0004\b\b(\u0012\u0012\u0004\u0012\u00020\t0\u0014\u0012\u0006\u0010\u001c\u001a\u00020\u000e\u00a2\u0006\u0004\b%\u0010&J\u001f\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0004H\u0016\u00a2\u0006\u0004\b\u0007\u0010\bJ\u000f\u0010\n\u001a\u00020\tH\u0016\u00a2\u0006\u0004\b\n\u0010\u000bJ\u001f\u0010\f\u001a\u00020\t2\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0004H\u0016\u00a2\u0006\u0004\b\f\u0010\rJ'\u0010\u0010\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0004H\u0016\u00a2\u0006\u0004\b\u0010\u0010\u0011J\u001f\u0010\u0012\u001a\u00020\u000e2\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0004H\u0016\u00a2\u0006\u0004\b\u0012\u0010\u0013R2\u0010\u0018\u001a\u001d\u0012\u0013\u0012\u00110\u0015\u00a2\u0006\f\b\u0016\u0012\b\b\u0017\u0012\u0004\b\b(\u0012\u0012\u0004\u0012\u00020\t0\u00148\u0006\u00a2\u0006\f\n\u0004\b\u0018\u0010\u0019\u001a\u0004\b\u001a\u0010\u001bR\u0017\u0010\u001c\u001a\u00020\u000e8\u0006\u00a2\u0006\f\n\u0004\b\u001c\u0010\u001d\u001a\u0004\b\u001e\u0010\u001fR\u0017\u0010!\u001a\u00020 8\u0006\u00a2\u0006\f\n\u0004\b!\u0010\"\u001a\u0004\b#\u0010$\u00a8\u0006'"}, d2={"Lcom/cobblemon/mod/common/api/pokeball/catching/modifiers/BaseStatModifier;", "Lcom/cobblemon/mod/common/api/pokeball/catching/CatchRateModifier;", "Lnet/minecraft/world/entity/LivingEntity;", "thrower", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "pokemon", "Lcom/cobblemon/mod/common/api/pokeball/catching/CatchRateModifier$Behavior;", "behavior", "(Lnet/minecraft/world/entity/LivingEntity;Lcom/cobblemon/mod/common/pokemon/Pokemon;)Lcom/cobblemon/mod/common/api/pokeball/catching/CatchRateModifier$Behavior;", "", "isGuaranteed", "()Z", "isValid", "(Lnet/minecraft/world/entity/LivingEntity;Lcom/cobblemon/mod/common/pokemon/Pokemon;)Z", "", "currentCatchRate", "modifyCatchRate", "(FLnet/minecraft/world/entity/LivingEntity;Lcom/cobblemon/mod/common/pokemon/Pokemon;)F", "value", "(Lnet/minecraft/world/entity/LivingEntity;Lcom/cobblemon/mod/common/pokemon/Pokemon;)F", "Lkotlin/Function1;", "", "Lkotlin/ParameterName;", "name", "comparator", "Lkotlin/jvm/functions/Function1;", "getComparator", "()Lkotlin/jvm/functions/Function1;", "multiplier", "F", "getMultiplier", "()F", "Lcom/cobblemon/mod/common/api/pokemon/stats/Stat;", "stat", "Lcom/cobblemon/mod/common/api/pokemon/stats/Stat;", "getStat", "()Lcom/cobblemon/mod/common/api/pokemon/stats/Stat;", "<init>", "(Lcom/cobblemon/mod/common/api/pokemon/stats/Stat;Lkotlin/jvm/functions/Function1;F)V", "common"})
public final class BaseStatModifier
implements CatchRateModifier {
    @NotNull
    private final Stat stat;
    @NotNull
    private final Function1<Integer, Boolean> comparator;
    private final float multiplier;

    public BaseStatModifier(@NotNull Stat stat, @NotNull Function1<? super Integer, Boolean> comparator, float multiplier) {
        Intrinsics.checkNotNullParameter((Object)stat, (String)"stat");
        Intrinsics.checkNotNullParameter(comparator, (String)"comparator");
        this.stat = stat;
        this.comparator = comparator;
        this.multiplier = multiplier;
        if (this.stat.getType() != Stat.Type.PERMANENT) {
            throw new IllegalArgumentException(this.stat.getIdentifier() + " is not of type PERMANENT");
        }
    }

    @NotNull
    public final Stat getStat() {
        return this.stat;
    }

    @NotNull
    public final Function1<Integer, Boolean> getComparator() {
        return this.comparator;
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
        return pokemon.getForm().getBaseStats().get(this.stat) != null;
    }

    @Override
    public float modifyCatchRate(float currentCatchRate, @NotNull LivingEntity thrower, @NotNull Pokemon pokemon) {
        Intrinsics.checkNotNullParameter((Object)thrower, (String)"thrower");
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        return ((Number)this.behavior(thrower, pokemon).getMutator().invoke((Object)Float.valueOf(currentCatchRate), (Object)Float.valueOf(this.value(thrower, pokemon)))).floatValue();
    }
}

