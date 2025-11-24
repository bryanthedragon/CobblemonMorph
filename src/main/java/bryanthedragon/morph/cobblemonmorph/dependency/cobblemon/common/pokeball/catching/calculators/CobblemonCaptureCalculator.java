/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  kotlin.math.MathKt
 *  kotlin.random.Random
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.entity.LivingEntity
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokeball.catching.calculators;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.actor.BattleActor;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.catching.CaptureContext;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.catching.calculators.CaptureCalculator;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.catching.calculators.CriticalCaptureProvider;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.catching.calculators.PokedexProgressCaptureMultiplierProvider;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ActiveBattlePokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.BattleRegistry;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.actor.PlayerBattleActor;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.config.CobblemonConfig;
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
import java.util.Collection;
import java.util.Iterator;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.math.MathKt;
import kotlin.random.Random;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u00c6\u0002\u0018\u00002\u00020\u00012\u00020\u00022\u00020\u0003B\t\b\u0002\u00a2\u0006\u0004\b\u0017\u0010\u0018J!\u0010\t\u001a\u0004\u0018\u00010\b2\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u0006H\u0002\u00a2\u0006\u0004\b\t\u0010\nJ\u000f\u0010\f\u001a\u00020\u000bH\u0016\u00a2\u0006\u0004\b\f\u0010\rJ'\u0010\u0015\u001a\u00020\u00142\u0006\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\u0011\u001a\u00020\u00102\u0006\u0010\u0013\u001a\u00020\u0012H\u0016\u00a2\u0006\u0004\b\u0015\u0010\u0016\u00a8\u0006\u0019"}, d2={"Lcom/cobblemon/mod/common/pokeball/catching/calculators/CobblemonCaptureCalculator;", "Lcom/cobblemon/mod/common/api/pokeball/catching/calculators/CaptureCalculator;", "Lcom/cobblemon/mod/common/api/pokeball/catching/calculators/CriticalCaptureProvider;", "Lcom/cobblemon/mod/common/api/pokeball/catching/calculators/PokedexProgressCaptureMultiplierProvider;", "Lnet/minecraft/server/level/ServerPlayer;", "player", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "pokemon", "", "findHighestThrowerLevel", "(Lnet/minecraft/server/level/ServerPlayer;Lcom/cobblemon/mod/common/pokemon/Pokemon;)Ljava/lang/Integer;", "", "id", "()Ljava/lang/String;", "Lnet/minecraft/world/entity/LivingEntity;", "thrower", "Lcom/cobblemon/mod/common/entity/pokeball/EmptyPokeBallEntity;", "pokeBallEntity", "Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;", "target", "Lcom/cobblemon/mod/common/api/pokeball/catching/CaptureContext;", "processCapture", "(Lnet/minecraft/world/entity/LivingEntity;Lcom/cobblemon/mod/common/entity/pokeball/EmptyPokeBallEntity;Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;)Lcom/cobblemon/mod/common/api/pokeball/catching/CaptureContext;", "<init>", "()V", "common"})
@SourceDebugExtension(value={"SMAP\nCobblemonCaptureCalculator.kt\nKotlin\n*S Kotlin\n*F\n+ 1 CobblemonCaptureCalculator.kt\ncom/cobblemon/mod/common/pokeball/catching/calculators/CobblemonCaptureCalculator\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,112:1\n288#2:113\n1747#2,3:114\n289#2:117\n1#3:118\n*S KotlinDebug\n*F\n+ 1 CobblemonCaptureCalculator.kt\ncom/cobblemon/mod/common/pokeball/catching/calculators/CobblemonCaptureCalculator\n*L\n105#1:113\n106#1:114,3\n105#1:117\n*E\n"})
public final class CobblemonCaptureCalculator
implements CaptureCalculator,
CriticalCaptureProvider,
PokedexProgressCaptureMultiplierProvider {
    @NotNull
    public static final CobblemonCaptureCalculator INSTANCE = new CobblemonCaptureCalculator();

    private CobblemonCaptureCalculator() {
    }

    @Override
    @NotNull
    public String id() {
        return "cobblemon";
    }

    @Override
    @NotNull
    public CaptureContext processCapture(@NotNull LivingEntity thrower, @NotNull EmptyPokeBallEntity pokeBallEntity, @NotNull PokemonEntity target) {
        Integer highestLevelThrower;
        Intrinsics.checkNotNullParameter((Object)thrower, (String)"thrower");
        Intrinsics.checkNotNullParameter((Object)pokeBallEntity, (String)"pokeBallEntity");
        Intrinsics.checkNotNullParameter((Object)target, (String)"target");
        PokeBall pokeBall = pokeBallEntity.getPokeBall();
        Pokemon pokemon = target.getPokemon();
        if (pokeBall.getCatchRateModifier().isGuaranteed()) {
            return CaptureContext.Companion.successful$default(CaptureContext.Companion, false, 1, null);
        }
        float darkGrass = 1.0f;
        float inBattleModifier = target.getBattleId() != null ? 1.0f : 0.5f;
        float catchRate = this.getCatchRate(thrower, pokeBallEntity, target, pokemon.getForm().getCatchRate());
        boolean validModifier = pokeBall.getCatchRateModifier().isValid(thrower, pokemon);
        PersistentStatusContainer persistentStatusContainer = pokemon.getStatus();
        PersistentStatus persistentStatus = persistentStatusContainer != null ? persistentStatusContainer.getStatus() : null;
        float bonusStatus = (persistentStatus instanceof SleepStatus ? true : persistentStatus instanceof FrozenStatus) ? 2.5f : ((((persistentStatus instanceof ParalysisStatus ? true : persistentStatus instanceof BurnStatus) ? true : persistentStatus instanceof PoisonStatus) ? true : persistentStatus instanceof PoisonBadlyStatus) ? 1.5f : 1.0f);
        int bonusLevel = pokemon.getLevel() < 13 ? Math.max((36 - 2 * pokemon.getLevel()) / 10, 1) : 1;
        float ballBonus = validModifier ? pokeBall.getCatchRateModifier().value(thrower, pokemon) : 1.0f;
        float modifiedCatchRate = ((Number)pokeBall.getCatchRateModifier().behavior(thrower, pokemon).getMutator().invoke((Object)Float.valueOf((3.0f * (float)pokemon.getHp() - 2.0f * (float)pokemon.getCurrentHealth()) * darkGrass * catchRate * inBattleModifier), (Object)Float.valueOf(ballBonus))).floatValue() / (3.0f * (float)pokemon.getHp());
        modifiedCatchRate *= bonusStatus * (float)bonusLevel;
        if (thrower instanceof ServerPlayer && (highestLevelThrower = this.findHighestThrowerLevel((ServerPlayer)thrower, pokemon)) != null && highestLevelThrower < pokemon.getLevel()) {
            CobblemonConfig config = Cobblemon.INSTANCE.getConfig();
            modifiedCatchRate *= Math.max(0.1f, Math.min(1.0f, 1.0f - (float)((pokemon.getLevel() - highestLevelThrower) / (config.getMaxPokemonLevel() / 2))));
        }
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

    /*
     * Enabled aggressive block sorting
     */
    private final Integer findHighestThrowerLevel(ServerPlayer player, Pokemon pokemon) {
        Comparable comparable;
        BattleActor battleActor;
        block10: {
            BattleActor element$iv;
            boolean bl;
            PokemonEntity pokemonEntity = pokemon.getEntity();
            if (pokemonEntity == null) {
                return null;
            }
            PokemonEntity entity2 = pokemonEntity;
            UUID uUID = entity2.getBattleId();
            if (uUID == null) {
                return null;
            }
            UUID battleId = uUID;
            PokemonBattle pokemonBattle = BattleRegistry.INSTANCE.getBattle(battleId);
            if (pokemonBattle == null) {
                return null;
            }
            PokemonBattle battle2 = pokemonBattle;
            Iterable<BattleActor> $this$firstOrNull$iv = battle2.getActors();
            boolean $i$f$firstOrNull = false;
            Iterator<BattleActor> iterator = $this$firstOrNull$iv.iterator();
            do {
                block13: {
                    boolean bl2;
                    block11: {
                        Iterator iterator2;
                        block15: {
                            block12: {
                                Iterable $this$any$iv;
                                block14: {
                                    if (!iterator.hasNext()) break block12;
                                    BattleActor actor = element$iv = iterator.next();
                                    boolean bl3 = false;
                                    if (!(actor instanceof PlayerBattleActor) || !Intrinsics.areEqual((Object)player.m_20148_(), (Object)actor.getUuid())) break block13;
                                    $this$any$iv = actor.getActivePokemon();
                                    boolean $i$f$any = false;
                                    if (!($this$any$iv instanceof Collection) || !((Collection)$this$any$iv).isEmpty()) break block14;
                                    bl2 = false;
                                    break block11;
                                }
                                iterator2 = $this$any$iv.iterator();
                                break block15;
                            }
                            battleActor = null;
                            break block10;
                        }
                        while (iterator2.hasNext()) {
                            Object element$iv2 = iterator2.next();
                            ActiveBattlePokemon active = (ActiveBattlePokemon)element$iv2;
                            boolean bl4 = false;
                            Object object = active.getBattlePokemon();
                            if (!Intrinsics.areEqual((Object)(object != null && (object = ((BattlePokemon)object).getEffectedPokemon()) != null ? ((Pokemon)object).getUuid() : null), (Object)pokemon.getUuid())) continue;
                            bl2 = true;
                            break block11;
                        }
                        bl2 = false;
                    }
                    if (bl2) {
                        bl = true;
                        continue;
                    }
                }
                bl = false;
            } while (!bl);
            battleActor = element$iv;
        }
        BattleActor battleActor2 = battleActor;
        if (battleActor2 == null) {
            return null;
        }
        BattleActor actor = battleActor2;
        Iterator iterator = ((Iterable)actor.getSide().getOppositeSide().getActivePokemon()).iterator();
        if (!iterator.hasNext()) {
            comparable = null;
            return (Integer)comparable;
        }
        ActiveBattlePokemon it = (ActiveBattlePokemon)iterator.next();
        boolean bl = false;
        Object object = it.getBattlePokemon();
        Comparable comparable2 = Integer.valueOf(object != null && (object = ((BattlePokemon)object).getEffectedPokemon()) != null ? ((Pokemon)object).getLevel() : 1);
        while (true) {
            if (!iterator.hasNext()) {
                comparable = comparable2;
                return (Integer)comparable;
            }
            ActiveBattlePokemon it2 = (ActiveBattlePokemon)iterator.next();
            $i$a$-maxOfOrNull-CobblemonCaptureCalculator$findHighestThrowerLevel$1 = false;
            Object object2 = it2.getBattlePokemon();
            Comparable comparable3 = Integer.valueOf(object2 != null && (object2 = ((BattlePokemon)object2).getEffectedPokemon()) != null ? ((Pokemon)object2).getLevel() : 1);
            if (comparable2.compareTo(comparable3) >= 0) continue;
            comparable2 = comparable3;
        }
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
}

