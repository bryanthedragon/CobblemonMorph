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

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.PokeBalls;
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

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\b\u00c6\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u001a\u0010\u001bJ/\u0010\t\u001a\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\u0006H\u0002\u00a2\u0006\u0004\b\t\u0010\nJ\u000f\u0010\f\u001a\u00020\u000bH\u0016\u00a2\u0006\u0004\b\f\u0010\rJ'\u0010\u0015\u001a\u00020\u00142\u0006\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\u0011\u001a\u00020\u00102\u0006\u0010\u0013\u001a\u00020\u0012H\u0016\u00a2\u0006\u0004\b\u0015\u0010\u0016R\u0014\u0010\u0017\u001a\u00020\u00068\u0002X\u0082T\u00a2\u0006\u0006\n\u0004\b\u0017\u0010\u0018R\u0014\u0010\u0019\u001a\u00020\u00068\u0002X\u0082T\u00a2\u0006\u0006\n\u0004\b\u0019\u0010\u0018\u00a8\u0006\u001c"}, d2={"Lcom/cobblemon/mod/common/pokeball/catching/calculators/Gen1CaptureCalculator;", "Lcom/cobblemon/mod/common/api/pokeball/catching/calculators/CaptureCalculator;", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "pokemon", "", "catchRate", "", "ballValue", "f", "calculateShakes", "(Lcom/cobblemon/mod/common/pokemon/Pokemon;FII)I", "", "id", "()Ljava/lang/String;", "Lnet/minecraft/world/entity/LivingEntity;", "thrower", "Lcom/cobblemon/mod/common/entity/pokeball/EmptyPokeBallEntity;", "pokeBallEntity", "Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;", "target", "Lcom/cobblemon/mod/common/api/pokeball/catching/CaptureContext;", "processCapture", "(Lnet/minecraft/world/entity/LivingEntity;Lcom/cobblemon/mod/common/entity/pokeball/EmptyPokeBallEntity;Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;)Lcom/cobblemon/mod/common/api/pokeball/catching/CaptureContext;", "FRZ_SLEEP_THRESHOLD", "I", "PARA_BRN_PSN_THRESHOLD", "<init>", "()V", "common"})
public final class Gen1CaptureCalculator
implements CaptureCalculator {
    @NotNull
    public static final Gen1CaptureCalculator INSTANCE = new Gen1CaptureCalculator();
    private static final int FRZ_SLEEP_THRESHOLD = 25;
    private static final int PARA_BRN_PSN_THRESHOLD = 12;

    private Gen1CaptureCalculator() {
    }

    @Override
    @NotNull
    public String id() {
        return "generation_1";
    }

    @Override
    @NotNull
    public CaptureContext processCapture(@NotNull LivingEntity thrower, @NotNull EmptyPokeBallEntity pokeBallEntity, @NotNull PokemonEntity target) {
        PersistentStatus status;
        Intrinsics.checkNotNullParameter((Object)thrower, (String)"thrower");
        Intrinsics.checkNotNullParameter((Object)pokeBallEntity, (String)"pokeBallEntity");
        Intrinsics.checkNotNullParameter((Object)target, (String)"target");
        PokeBall pokeBall = pokeBallEntity.getPokeBall();
        Pokemon pokemon = target.getPokemon();
        if (pokeBall.getCatchRateModifier().isGuaranteed()) {
            return CaptureContext.Companion.successful$default(CaptureContext.Companion, false, 1, null);
        }
        PokeBall pokeBall2 = pokeBall;
        int nBound = Intrinsics.areEqual((Object)pokeBall2, (Object)PokeBalls.INSTANCE.getPOKE_BALL()) ? 255 : (Intrinsics.areEqual((Object)pokeBall2, (Object)PokeBalls.INSTANCE.getGREAT_BALL()) ? 200 : 150);
        int n = Random.Default.nextInt(nBound + 1);
        int usedThreshold = 0;
        PersistentStatusContainer persistentStatusContainer = pokemon.getStatus();
        PersistentStatus persistentStatus = status = persistentStatusContainer != null ? persistentStatusContainer.getStatus() : null;
        if ((status instanceof FrozenStatus || status instanceof SleepStatus) && n < 25) {
            usedThreshold = 25;
            return CaptureContext.Companion.successful$default(CaptureContext.Companion, false, 1, null);
        }
        if ((status instanceof ParalysisStatus || status instanceof BurnStatus || status instanceof PoisonStatus || status instanceof PoisonBadlyStatus) && n < 12) {
            usedThreshold = 12;
            return CaptureContext.Companion.successful$default(CaptureContext.Companion, false, 1, null);
        }
        if (n - usedThreshold > pokemon.getForm().getCatchRate()) {
            return new CaptureContext(0, false, false);
        }
        int m = Random.Default.nextInt(256);
        float ballValue = Intrinsics.areEqual((Object)pokeBall, (Object)PokeBalls.INSTANCE.getGREAT_BALL()) ? 8.0f : 12.0f;
        int f = MathKt.roundToInt((float)RangesKt.coerceIn((float)((float)pokemon.getHp() * 255.0f * 4.0f / ((float)pokemon.getCurrentHealth() * ballValue)), (float)1.0f, (float)255.0f));
        if (f >= m) {
            return CaptureContext.Companion.successful$default(CaptureContext.Companion, false, 1, null);
        }
        return new CaptureContext(this.calculateShakes(pokemon, this.getCatchRate(thrower, pokeBallEntity, target, pokemon.getForm().getCatchRate()), nBound, f), false, false);
    }

    private final int calculateShakes(Pokemon pokemon, float catchRate, int ballValue, int f) {
        float d = catchRate * 100.0f / (float)ballValue;
        if (d >= 256.0f) {
            return 3;
        }
        PersistentStatusContainer persistentStatusContainer = pokemon.getStatus();
        PersistentStatus persistentStatus = persistentStatusContainer != null ? persistentStatusContainer.getStatus() : null;
        int s = (persistentStatus instanceof FrozenStatus ? true : persistentStatus instanceof SleepStatus) ? 10 : ((((persistentStatus instanceof ParalysisStatus ? true : persistentStatus instanceof BurnStatus) ? true : persistentStatus instanceof PoisonStatus) ? true : persistentStatus instanceof PoisonBadlyStatus) ? 5 : 0);
        float x = d * (float)f / (float)255 + (float)s;
        return x < 10.0f ? 0 : (x < 30.0f ? 1 : (x < 70.0f ? 2 : 3));
    }

    @Override
    public float getCatchRate(@NotNull LivingEntity thrower, @NotNull EmptyPokeBallEntity pokeBallEntity, @NotNull PokemonEntity target, float catchRate) {
        return CaptureCalculator.DefaultImpls.getCatchRate(this, thrower, pokeBallEntity, target, catchRate);
    }
}

