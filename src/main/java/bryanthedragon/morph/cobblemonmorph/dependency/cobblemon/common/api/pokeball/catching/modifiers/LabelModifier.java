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
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import java.util.Arrays;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0007\n\u0002\b\u0005\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u000e\u0018\u00002\u00020\u0001B+\u0012\u0006\u0010\u001d\u001a\u00020\u000e\u0012\u0006\u0010\u001a\u001a\u00020\t\u0012\u0012\u0010\u0016\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00150\u0014\"\u00020\u0015\u00a2\u0006\u0004\b!\u0010\"J\u001f\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0004H\u0016\u00a2\u0006\u0004\b\u0007\u0010\bJ\u000f\u0010\n\u001a\u00020\tH\u0016\u00a2\u0006\u0004\b\n\u0010\u000bJ\u001f\u0010\f\u001a\u00020\t2\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0004H\u0016\u00a2\u0006\u0004\b\f\u0010\rJ'\u0010\u0010\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0004H\u0016\u00a2\u0006\u0004\b\u0010\u0010\u0011J\u001f\u0010\u0012\u001a\u00020\u000e2\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0004H\u0016\u00a2\u0006\u0004\b\u0012\u0010\u0013R\u001f\u0010\u0016\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00150\u00148\u0006\u00a2\u0006\f\n\u0004\b\u0016\u0010\u0017\u001a\u0004\b\u0018\u0010\u0019R\u0017\u0010\u001a\u001a\u00020\t8\u0006\u00a2\u0006\f\n\u0004\b\u001a\u0010\u001b\u001a\u0004\b\u001c\u0010\u000bR\u0017\u0010\u001d\u001a\u00020\u000e8\u0006\u00a2\u0006\f\n\u0004\b\u001d\u0010\u001e\u001a\u0004\b\u001f\u0010 \u00a8\u0006#"}, d2={"Lcom/cobblemon/mod/common/api/pokeball/catching/modifiers/LabelModifier;", "Lcom/cobblemon/mod/common/api/pokeball/catching/CatchRateModifier;", "Lnet/minecraft/world/entity/LivingEntity;", "thrower", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "pokemon", "Lcom/cobblemon/mod/common/api/pokeball/catching/CatchRateModifier$Behavior;", "behavior", "(Lnet/minecraft/world/entity/LivingEntity;Lcom/cobblemon/mod/common/pokemon/Pokemon;)Lcom/cobblemon/mod/common/api/pokeball/catching/CatchRateModifier$Behavior;", "", "isGuaranteed", "()Z", "isValid", "(Lnet/minecraft/world/entity/LivingEntity;Lcom/cobblemon/mod/common/pokemon/Pokemon;)Z", "", "currentCatchRate", "modifyCatchRate", "(FLnet/minecraft/world/entity/LivingEntity;Lcom/cobblemon/mod/common/pokemon/Pokemon;)F", "value", "(Lnet/minecraft/world/entity/LivingEntity;Lcom/cobblemon/mod/common/pokemon/Pokemon;)F", "", "", "labels", "[Ljava/lang/String;", "getLabels", "()[Ljava/lang/String;", "matching", "Z", "getMatching", "multiplier", "F", "getMultiplier", "()F", "<init>", "(FZ[Ljava/lang/String;)V", "common"})
public final class LabelModifier
implements CatchRateModifier {
    private final float multiplier;
    private final boolean matching;
    @NotNull
    private final String[] labels;

    public LabelModifier(float multiplier, boolean matching, String ... labels) {
        Intrinsics.checkNotNullParameter((Object)labels, (String)"labels");
        this.multiplier = multiplier;
        this.matching = matching;
        this.labels = labels;
    }

    public final float getMultiplier() {
        return this.multiplier;
    }

    public final boolean getMatching() {
        return this.matching;
    }

    @NotNull
    public final String[] getLabels() {
        return this.labels;
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
        boolean bl;
        Intrinsics.checkNotNullParameter((Object)thrower, (String)"thrower");
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        if (this.matching) {
            String[] stringArray = this.labels;
            bl = pokemon.hasLabels(Arrays.copyOf(stringArray, stringArray.length));
        } else {
            String[] stringArray = this.labels;
            bl = !pokemon.hasLabels(Arrays.copyOf(stringArray, stringArray.length));
        }
        return bl;
    }

    @Override
    public float modifyCatchRate(float currentCatchRate, @NotNull LivingEntity thrower, @NotNull Pokemon pokemon) {
        Intrinsics.checkNotNullParameter((Object)thrower, (String)"thrower");
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        return ((Number)this.behavior(thrower, pokemon).getMutator().invoke((Object)Float.valueOf(currentCatchRate), (Object)Float.valueOf(this.value(thrower, pokemon)))).floatValue();
    }
}

