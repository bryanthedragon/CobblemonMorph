/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.math.MathKt
 *  kotlin.random.Random
 *  kotlin.ranges.RangesKt
 *  net.minecraft.world.entity.LivingEntity
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokeball.catching.calculators;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.catching.CaptureContext;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.catching.calculators.CaptureCalculator;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokeball.EmptyPokeBallEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokeball.PokeBall;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.status.PersistentStatus;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.status.PersistentStatusContainer;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.status.statuses.persistent.BurnStatus;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.status.statuses.persistent.FrozenStatus;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.status.statuses.persistent.ParalysisStatus;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.status.statuses.persistent.PoisonBadlyStatus;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.status.statuses.persistent.PoisonStatus;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.status.statuses.persistent.SleepStatus;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.math.MathKt;
import kotlin.random.Random;
import kotlin.ranges.RangesKt;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u000f\u001a\u00020\u000e\u00a2\u0006\u0004\b\u0013\u0010\u0014J\u000f\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0003\u0010\u0004J'\u0010\f\u001a\u00020\u000b2\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\u00072\u0006\u0010\n\u001a\u00020\tH\u0016\u00a2\u0006\u0004\b\f\u0010\rR\u0017\u0010\u000f\u001a\u00020\u000e8\u0006\u00a2\u0006\f\n\u0004\b\u000f\u0010\u0010\u001a\u0004\b\u0011\u0010\u0012\u00a8\u0006\u0015"}, d2={"Lcom/cobblemon/mod/common/pokeball/catching/calculators/Gen2CaptureCalculator;", "Lcom/cobblemon/mod/common/api/pokeball/catching/calculators/CaptureCalculator;", "", "id", "()Ljava/lang/String;", "Lnet/minecraft/world/entity/LivingEntity;", "thrower", "Lcom/cobblemon/mod/common/entity/pokeball/EmptyPokeBallEntity;", "pokeBallEntity", "Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;", "target", "Lcom/cobblemon/mod/common/api/pokeball/catching/CaptureContext;", "processCapture", "(Lnet/minecraft/world/entity/LivingEntity;Lcom/cobblemon/mod/common/entity/pokeball/EmptyPokeBallEntity;Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;)Lcom/cobblemon/mod/common/api/pokeball/catching/CaptureContext;", "", "bugsFixed", "Z", "getBugsFixed", "()Z", "<init>", "(Z)V", "common"})
public final class Gen2CaptureCalculator
implements CaptureCalculator {
    private final boolean bugsFixed;

    public Gen2CaptureCalculator(boolean bugsFixed) {
        this.bugsFixed = bugsFixed;
    }

    public final boolean getBugsFixed() {
        return this.bugsFixed;
    }

    @Override
    @NotNull
    public String id() {
        return "generation_2" + (this.bugsFixed ? "_fixed" : "");
    }

    @Override
    @NotNull
    public CaptureContext processCapture(@NotNull LivingEntity thrower, @NotNull EmptyPokeBallEntity pokeBallEntity, @NotNull PokemonEntity target) {
        Intrinsics.checkNotNullParameter((Object)thrower, (String)"thrower");
        Intrinsics.checkNotNullParameter((Object)pokeBallEntity, (String)"pokeBallEntity");
        Intrinsics.checkNotNullParameter((Object)target, (String)"target");
        PokeBall pokeBall = pokeBallEntity.getPokeBall();
        Pokemon pokemon = target.getPokemon();
        if (pokeBall.getCatchRateModifier().isGuaranteed()) {
            return CaptureContext.Companion.successful$default(CaptureContext.Companion, false, 1, null);
        }
        float catchRate = this.getCatchRate(thrower, pokeBallEntity, target, pokemon.getForm().getCatchRate());
        float modifiedRate = pokeBall.getCatchRateModifier().isValid(thrower, pokemon) ? pokeBall.getCatchRateModifier().modifyCatchRate(catchRate, thrower, pokemon) : catchRate;
        PersistentStatusContainer persistentStatusContainer = pokemon.getStatus();
        PersistentStatus status = persistentStatusContainer != null ? persistentStatusContainer.getStatus() : null;
        int bonusStatus = status instanceof SleepStatus || status instanceof FrozenStatus ? 10 : (this.bugsFixed && (status instanceof ParalysisStatus || status instanceof BurnStatus || status instanceof PoisonStatus || status instanceof PoisonBadlyStatus) ? 5 : 1);
        int modifiedCatchRate = MathKt.roundToInt((float)RangesKt.coerceAtMost((float)Math.max((3.0f * (float)pokemon.getHp() - 2.0f * (float)pokemon.getCurrentHealth()) * modifiedRate / (3.0f * (float)pokemon.getHp()) + (float)bonusStatus, 1.0f), (float)255.0f));
        if (Random.Default.nextInt(256) <= modifiedCatchRate) {
            return CaptureContext.Companion.successful$default(CaptureContext.Companion, false, 1, null);
        }
        int shakeProbability = modifiedCatchRate <= 1 ? 63 : (modifiedCatchRate == 2 ? 75 : (modifiedCatchRate == 3 ? 84 : (modifiedCatchRate == 4 ? 90 : (modifiedCatchRate == 5 ? 95 : (modifiedCatchRate <= 7 ? 103 : (modifiedCatchRate <= 10 ? 113 : (modifiedCatchRate <= 15 ? 126 : (modifiedCatchRate <= 20 ? 134 : (modifiedCatchRate <= 30 ? 149 : (modifiedCatchRate <= 40 ? 160 : (modifiedCatchRate <= 50 ? 169 : (modifiedCatchRate <= 60 ? 177 : (modifiedCatchRate <= 80 ? 191 : (modifiedCatchRate <= 100 ? 201 : (modifiedCatchRate <= 120 ? 211 : (modifiedCatchRate <= 140 ? 200 : (modifiedCatchRate <= 160 ? 227 : (modifiedCatchRate <= 180 ? 234 : (modifiedCatchRate <= 200 ? 240 : (modifiedCatchRate <= 220 ? 246 : (modifiedCatchRate <= 240 ? 251 : (modifiedCatchRate <= 254 ? 253 : 255))))))))))))))))))))));
        int shakes = 0;
        int n = 3;
        int n2 = 0;
        while (n2 < n) {
            int it = n2++;
            boolean bl = false;
            if (Random.Default.nextInt(256) >= shakeProbability) {
                return new CaptureContext(shakes, false, false);
            }
            ++shakes;
        }
        return CaptureContext.Companion.successful$default(CaptureContext.Companion, false, 1, null);
    }

    @Override
    public float getCatchRate(@NotNull LivingEntity thrower, @NotNull EmptyPokeBallEntity pokeBallEntity, @NotNull PokemonEntity target, float catchRate) {
        return CaptureCalculator.DefaultImpls.getCatchRate(this, thrower, pokeBallEntity, target, catchRate);
    }
}

