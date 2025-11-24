/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.collections.SetsKt
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.math.MathKt
 *  kotlin.random.Random
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.entity.LivingEntity
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokeball.catching.calculators;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.PokeBalls;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.catching.CaptureContext;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.catching.calculators.CaptureCalculator;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.catching.calculators.CriticalCaptureProvider;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.catching.calculators.PokedexProgressCaptureMultiplierProvider;
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
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u00c6\u0002\u0018\u00002\u00020\u00012\u00020\u00022\u00020\u0003B\t\b\u0002\u00a2\u0006\u0004\b\u0014\u0010\u0015J\u000f\u0010\u0005\u001a\u00020\u0004H\u0016\u00a2\u0006\u0004\b\u0005\u0010\u0006J'\u0010\u000e\u001a\u00020\r2\u0006\u0010\b\u001a\u00020\u00072\u0006\u0010\n\u001a\u00020\t2\u0006\u0010\f\u001a\u00020\u000bH\u0016\u00a2\u0006\u0004\b\u000e\u0010\u000fR\u001a\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00110\u00108\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u0012\u0010\u0013\u00a8\u0006\u0016"}, d2={"Lcom/cobblemon/mod/common/pokeball/catching/calculators/Gen6CaptureCalculator;", "Lcom/cobblemon/mod/common/api/pokeball/catching/calculators/CaptureCalculator;", "Lcom/cobblemon/mod/common/api/pokeball/catching/calculators/CriticalCaptureProvider;", "Lcom/cobblemon/mod/common/api/pokeball/catching/calculators/PokedexProgressCaptureMultiplierProvider;", "", "id", "()Ljava/lang/String;", "Lnet/minecraft/world/entity/LivingEntity;", "thrower", "Lcom/cobblemon/mod/common/entity/pokeball/EmptyPokeBallEntity;", "pokeBallEntity", "Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;", "target", "Lcom/cobblemon/mod/common/api/pokeball/catching/CaptureContext;", "processCapture", "(Lnet/minecraft/world/entity/LivingEntity;Lcom/cobblemon/mod/common/entity/pokeball/EmptyPokeBallEntity;Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;)Lcom/cobblemon/mod/common/api/pokeball/catching/CaptureContext;", "", "Lcom/cobblemon/mod/common/pokeball/PokeBall;", "apricornPokeballs", "Ljava/util/Set;", "<init>", "()V", "common"})
public final class Gen6CaptureCalculator
implements CaptureCalculator,
CriticalCaptureProvider,
PokedexProgressCaptureMultiplierProvider {
    @NotNull
    public static final Gen6CaptureCalculator INSTANCE = new Gen6CaptureCalculator();
    @NotNull
    private static final Set<PokeBall> apricornPokeballs;

    private Gen6CaptureCalculator() {
    }

    @Override
    @NotNull
    public String id() {
        return "generation_6";
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
        int darkGrass = thrower instanceof ServerPlayer ? MathKt.roundToInt((float)this.caughtMultiplierFor((ServerPlayer)thrower)) : 1;
        float catchRate = this.getCatchRate(thrower, pokeBallEntity, target, pokemon.getForm().getCatchRate());
        boolean validModifier = pokeBall.getCatchRateModifier().isValid(thrower, pokemon);
        PersistentStatusContainer persistentStatusContainer = pokemon.getStatus();
        PersistentStatus persistentStatus = persistentStatusContainer != null ? persistentStatusContainer.getStatus() : null;
        float bonusStatus = (persistentStatus instanceof SleepStatus ? true : persistentStatus instanceof FrozenStatus) ? 2.5f : ((((persistentStatus instanceof ParalysisStatus ? true : persistentStatus instanceof BurnStatus) ? true : persistentStatus instanceof PoisonStatus) ? true : persistentStatus instanceof PoisonBadlyStatus) ? 1.5f : 1.0f);
        float rate = 0.0f;
        float ballBonus = 0.0f;
        if (apricornPokeballs.contains(pokeBall)) {
            rate = validModifier ? pokeBall.getCatchRateModifier().modifyCatchRate(catchRate, thrower, pokemon) : 1.0f;
            ballBonus = 1.0f;
        } else {
            rate = catchRate;
            ballBonus = validModifier ? pokeBall.getCatchRateModifier().value(thrower, pokemon) : 1.0f;
        }
        float modifiedCatchRate = ((Number)pokeBall.getCatchRateModifier().behavior(thrower, pokemon).getMutator().invoke((Object)Float.valueOf((3.0f * (float)pokemon.getHp() - 2.0f * (float)pokemon.getCurrentHealth()) * (float)darkGrass * rate), (Object)Float.valueOf(ballBonus))).floatValue() / (3.0f * (float)pokemon.getHp()) * bonusStatus;
        boolean critical = thrower instanceof ServerPlayer ? this.shouldHaveCriticalCapture((ServerPlayer)thrower, modifiedCatchRate) : false;
        int shakeProbability = MathKt.roundToInt((float)(65536.0f / (float)Math.pow(255.0f / modifiedCatchRate, 0.1875f)));
        int shakes = 0;
        int n = 4;
        for (int i = 0; i < n; ++i) {
            int it = i;
            boolean bl = false;
            int n2 = Random.Default.nextInt(65537);
            if (n2 < shakeProbability) {
                ++shakes;
            }
            if (it != 0 || !critical) continue;
            return new CaptureContext(1, shakes == 1, true);
        }
        return new CaptureContext(shakes, shakes == 4, false);
    }

    @Override
    public float getCatchRate(@NotNull LivingEntity thrower, @NotNull EmptyPokeBallEntity pokeBallEntity, @NotNull PokemonEntity target, float catchRate) {
        return CaptureCalculator.DefaultImpls.getCatchRate(this, thrower, pokeBallEntity, target, catchRate);
    }

    @Override
    public boolean shouldHaveCriticalCapture(@NotNull ServerPlayer player, float modifiedCatchRate) {
        return CriticalCaptureProvider.DefaultImpls.shouldHaveCriticalCapture(this, player, modifiedCatchRate);
    }

    @Override
    public float caughtMultiplierFor(@NotNull ServerPlayer player) {
        return PokedexProgressCaptureMultiplierProvider.DefaultImpls.caughtMultiplierFor(this, player);
    }

    static {
        Object[] objectArray = new PokeBall[]{PokeBalls.INSTANCE.getHEAVY_BALL(), PokeBalls.INSTANCE.getLURE_BALL(), PokeBalls.INSTANCE.getFRIEND_BALL(), PokeBalls.INSTANCE.getLOVE_BALL(), PokeBalls.INSTANCE.getLEVEL_BALL(), PokeBalls.INSTANCE.getFAST_BALL(), PokeBalls.INSTANCE.getMOON_BALL()};
        apricornPokeballs = SetsKt.setOf((Object[])objectArray);
    }
}

