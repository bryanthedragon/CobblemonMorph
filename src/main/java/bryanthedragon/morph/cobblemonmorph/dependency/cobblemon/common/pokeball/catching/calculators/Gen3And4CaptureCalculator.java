/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.collections.SetsKt
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.math.MathKt
 *  kotlin.random.Random
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
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.math.MathKt;
import kotlin.random.Random;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u00c6\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0012\u0010\u0013J\u000f\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0003\u0010\u0004J'\u0010\f\u001a\u00020\u000b2\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\u00072\u0006\u0010\n\u001a\u00020\tH\u0016\u00a2\u0006\u0004\b\f\u0010\rR\u001a\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000e8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u0010\u0010\u0011\u00a8\u0006\u0014"}, d2={"Lcom/cobblemon/mod/common/pokeball/catching/calculators/Gen3And4CaptureCalculator;", "Lcom/cobblemon/mod/common/api/pokeball/catching/calculators/CaptureCalculator;", "", "id", "()Ljava/lang/String;", "Lnet/minecraft/world/entity/LivingEntity;", "thrower", "Lcom/cobblemon/mod/common/entity/pokeball/EmptyPokeBallEntity;", "pokeBallEntity", "Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;", "target", "Lcom/cobblemon/mod/common/api/pokeball/catching/CaptureContext;", "processCapture", "(Lnet/minecraft/world/entity/LivingEntity;Lcom/cobblemon/mod/common/entity/pokeball/EmptyPokeBallEntity;Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;)Lcom/cobblemon/mod/common/api/pokeball/catching/CaptureContext;", "", "Lcom/cobblemon/mod/common/pokeball/PokeBall;", "apricornPokeballs", "Ljava/util/Set;", "<init>", "()V", "common"})
public final class Gen3And4CaptureCalculator
implements CaptureCalculator {
    @NotNull
    public static final Gen3And4CaptureCalculator INSTANCE = new Gen3And4CaptureCalculator();
    @NotNull
    private static final Set<PokeBall> apricornPokeballs;

    private Gen3And4CaptureCalculator() {
    }

    @Override
    @NotNull
    public String id() {
        return "generation_3_and_4";
    }

    @Override
    @NotNull
    public CaptureContext processCapture(@NotNull LivingEntity thrower, @NotNull EmptyPokeBallEntity pokeBallEntity, @NotNull PokemonEntity target) {
        float f;
        Intrinsics.checkNotNullParameter((Object)thrower, (String)"thrower");
        Intrinsics.checkNotNullParameter((Object)pokeBallEntity, (String)"pokeBallEntity");
        Intrinsics.checkNotNullParameter((Object)target, (String)"target");
        PokeBall pokeBall = pokeBallEntity.getPokeBall();
        Pokemon pokemon = target.getPokemon();
        if (pokeBall.getCatchRateModifier().isGuaranteed()) {
            return CaptureContext.Companion.successful$default(CaptureContext.Companion, false, 1, null);
        }
        float catchRate = this.getCatchRate(thrower, pokeBallEntity, target, pokemon.getForm().getCatchRate());
        boolean validModifier = pokeBall.getCatchRateModifier().isValid(thrower, pokemon);
        PersistentStatusContainer persistentStatusContainer = pokemon.getStatus();
        PersistentStatus persistentStatus = persistentStatusContainer != null ? persistentStatusContainer.getStatus() : null;
        float bonusStatus = (persistentStatus instanceof SleepStatus ? true : persistentStatus instanceof FrozenStatus) ? 2.0f : ((((persistentStatus instanceof ParalysisStatus ? true : persistentStatus instanceof BurnStatus) ? true : persistentStatus instanceof PoisonStatus) ? true : persistentStatus instanceof PoisonBadlyStatus) ? 1.5f : 1.0f);
        float rate = 0.0f;
        if (apricornPokeballs.contains(pokeBall)) {
            rate = validModifier ? pokeBall.getCatchRateModifier().modifyCatchRate(catchRate, thrower, pokemon) : 1.0f;
            f = 1.0f;
        } else {
            rate = catchRate;
            f = validModifier ? pokeBall.getCatchRateModifier().value(thrower, pokemon) : 1.0f;
        }
        float ballBonus = f;
        float modifiedCatchRate = ((Number)pokeBall.getCatchRateModifier().behavior(thrower, pokemon).getMutator().invoke((Object)Float.valueOf((3.0f * (float)pokemon.getHp() - 2.0f * (float)pokemon.getCurrentHealth()) * rate), (Object)Float.valueOf(ballBonus))).floatValue() / (3.0f * (float)pokemon.getHp()) * bonusStatus;
        int shakeProbability = MathKt.roundToInt((float)(1048560.0f / (float)MathKt.roundToInt((double)Math.sqrt(Math.sqrt(MathKt.roundToInt((float)(1.671168E7f / modifiedCatchRate)))))));
        int shakes = 0;
        int n = 4;
        for (int i = 0; i < n; ++i) {
            int it = i;
            boolean bl = false;
            int n2 = Random.Default.nextInt(65537);
            if (n2 >= shakeProbability) continue;
            ++shakes;
        }
        return new CaptureContext(shakes, shakes == 4, false);
    }

    @Override
    public float getCatchRate(@NotNull LivingEntity thrower, @NotNull EmptyPokeBallEntity pokeBallEntity, @NotNull PokemonEntity target, float catchRate) {
        return CaptureCalculator.DefaultImpls.getCatchRate(this, thrower, pokeBallEntity, target, catchRate);
    }

    static {
        Object[] objectArray = new PokeBall[]{PokeBalls.INSTANCE.getHEAVY_BALL(), PokeBalls.INSTANCE.getLURE_BALL(), PokeBalls.INSTANCE.getFRIEND_BALL(), PokeBalls.INSTANCE.getLOVE_BALL(), PokeBalls.INSTANCE.getLEVEL_BALL(), PokeBalls.INSTANCE.getFAST_BALL(), PokeBalls.INSTANCE.getMOON_BALL()};
        apricornPokeballs = SetsKt.setOf((Object[])objectArray);
    }
}

