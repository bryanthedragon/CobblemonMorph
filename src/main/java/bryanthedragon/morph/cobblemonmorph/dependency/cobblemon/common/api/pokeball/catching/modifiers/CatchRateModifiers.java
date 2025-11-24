/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.collections.ArraysKt
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.functions.Function2
 *  kotlin.jvm.functions.Function3
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.entity.LivingEntity
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.catching.modifiers;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.catching.CatchRateModifier;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.catching.modifiers.BattleModifier;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.catching.modifiers.CatchRateModifiers;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.catching.modifiers.DynamicMultiplierModifier;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.catching.modifiers.MultiplierModifier;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.catching.modifiers.WorldStateModifier;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.status.Status;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.types.ElementalType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ActiveBattlePokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.BattleRegistry;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.status.PersistentStatusContainer;
import java.util.Collection;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000H\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0017\b\u00c6\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b0\u00101J)\u0010\b\u001a\u00020\u00072\u0006\u0010\u0003\u001a\u00020\u00022\u0012\u0010\u0006\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00050\u0004\"\u00020\u0005\u00a2\u0006\u0004\b\b\u0010\tJ0\u0010\u0011\u001a\u00020\u00102!\u0010\u000f\u001a\u001d\u0012\u0013\u0012\u00110\u000b\u00a2\u0006\f\b\f\u0012\b\b\r\u0012\u0004\b\b(\u000e\u0012\u0004\u0012\u00020\u00020\n\u00a2\u0006\u0004\b\u0011\u0010\u0012J)\u0010\u0015\u001a\u00020\u00072\u0006\u0010\u0003\u001a\u00020\u00022\u0012\u0010\u0014\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00130\u0004\"\u00020\u0013\u00a2\u0006\u0004\b\u0015\u0010\u0016R\u0017\u0010\u0017\u001a\u00020\u00108\u0006\u00a2\u0006\f\n\u0004\b\u0017\u0010\u0018\u001a\u0004\b\u0019\u0010\u001aR\u0017\u0010\u001c\u001a\u00020\u001b8\u0006\u00a2\u0006\f\n\u0004\b\u001c\u0010\u001d\u001a\u0004\b\u001e\u0010\u001fR\u0017\u0010 \u001a\u00020\u00108\u0006\u00a2\u0006\f\n\u0004\b \u0010\u0018\u001a\u0004\b!\u0010\u001aR\u0017\u0010\"\u001a\u00020\u00078\u0006\u00a2\u0006\f\n\u0004\b\"\u0010#\u001a\u0004\b$\u0010%R\u0017\u0010&\u001a\u00020\u00078\u0006\u00a2\u0006\f\n\u0004\b&\u0010#\u001a\u0004\b'\u0010%R\u0017\u0010(\u001a\u00020\u001b8\u0006\u00a2\u0006\f\n\u0004\b(\u0010\u001d\u001a\u0004\b)\u0010\u001fR\u0017\u0010*\u001a\u00020\u001b8\u0006\u00a2\u0006\f\n\u0004\b*\u0010\u001d\u001a\u0004\b+\u0010\u001fR\u0017\u0010,\u001a\u00020\u001b8\u0006\u00a2\u0006\f\n\u0004\b,\u0010\u001d\u001a\u0004\b-\u0010\u001fR\u0017\u0010.\u001a\u00020\u00078\u0006\u00a2\u0006\f\n\u0004\b.\u0010#\u001a\u0004\b/\u0010%\u00a8\u00062"}, d2={"Lcom/cobblemon/mod/common/api/pokeball/catching/modifiers/CatchRateModifiers;", "", "", "multiplier", "", "Lcom/cobblemon/mod/common/api/pokemon/status/Status;", "status", "Lcom/cobblemon/mod/common/api/pokeball/catching/CatchRateModifier;", "statusBoosting", "(F[Lcom/cobblemon/mod/common/api/pokemon/status/Status;)Lcom/cobblemon/mod/common/api/pokeball/catching/CatchRateModifier;", "Lkotlin/Function1;", "", "Lkotlin/ParameterName;", "name", "turn", "multiplierCalculator", "Lcom/cobblemon/mod/common/api/pokeball/catching/modifiers/BattleModifier;", "turnBased", "(Lkotlin/jvm/functions/Function1;)Lcom/cobblemon/mod/common/api/pokeball/catching/modifiers/BattleModifier;", "Lcom/cobblemon/mod/common/api/types/ElementalType;", "types", "typeBoosting", "(F[Lcom/cobblemon/mod/common/api/types/ElementalType;)Lcom/cobblemon/mod/common/api/pokeball/catching/CatchRateModifier;", "LEVEL", "Lcom/cobblemon/mod/common/api/pokeball/catching/modifiers/BattleModifier;", "getLEVEL", "()Lcom/cobblemon/mod/common/api/pokeball/catching/modifiers/BattleModifier;", "Lcom/cobblemon/mod/common/api/pokeball/catching/modifiers/WorldStateModifier;", "LIGHT_LEVEL", "Lcom/cobblemon/mod/common/api/pokeball/catching/modifiers/WorldStateModifier;", "getLIGHT_LEVEL", "()Lcom/cobblemon/mod/common/api/pokeball/catching/modifiers/WorldStateModifier;", "LOVE", "getLOVE", "MOON_PHASES", "Lcom/cobblemon/mod/common/api/pokeball/catching/CatchRateModifier;", "getMOON_PHASES", "()Lcom/cobblemon/mod/common/api/pokeball/catching/CatchRateModifier;", "NEST", "getNEST", "PARK", "getPARK", "SAFARI", "getSAFARI", "SUBMERGED_IN_WATER", "getSUBMERGED_IN_WATER", "WEIGHT_BASED", "getWEIGHT_BASED", "<init>", "()V", "common"})
public final class CatchRateModifiers {
    @NotNull
    public static final CatchRateModifiers INSTANCE = new CatchRateModifiers();
    @NotNull
    private static final BattleModifier LEVEL = new BattleModifier((Function3<? super ServerPlayer, ? super Iterable<ActiveBattlePokemon>, ? super Pokemon, Float>)((Function3)LEVEL.1.INSTANCE));
    @NotNull
    private static final WorldStateModifier SUBMERGED_IN_WATER = new WorldStateModifier((Function2<? super LivingEntity, ? super PokemonEntity, Float>)((Function2)SUBMERGED_IN_WATER.1.INSTANCE));
    @NotNull
    private static final CatchRateModifier NEST = new DynamicMultiplierModifier((Function2<? super LivingEntity, ? super Pokemon, Float>)((Function2)NEST.1.INSTANCE), (Function2<? super LivingEntity, ? super Pokemon, Boolean>)((Function2)NEST.2.INSTANCE));
    @NotNull
    private static final BattleModifier LOVE = new BattleModifier((Function3<? super ServerPlayer, ? super Iterable<ActiveBattlePokemon>, ? super Pokemon, Float>)((Function3)LOVE.1.INSTANCE));
    @NotNull
    private static final CatchRateModifier MOON_PHASES = new WorldStateModifier((Function2<? super LivingEntity, ? super PokemonEntity, Float>)((Function2)MOON_PHASES.1.INSTANCE));
    @NotNull
    private static final WorldStateModifier LIGHT_LEVEL = new WorldStateModifier((Function2<? super LivingEntity, ? super PokemonEntity, Float>)((Function2)LIGHT_LEVEL.1.INSTANCE));
    @NotNull
    private static final WorldStateModifier SAFARI = new WorldStateModifier((Function2<? super LivingEntity, ? super PokemonEntity, Float>)((Function2)SAFARI.1.INSTANCE));
    @NotNull
    private static final WorldStateModifier PARK = new WorldStateModifier((Function2<? super LivingEntity, ? super PokemonEntity, Float>)((Function2)PARK.1.INSTANCE));
    @NotNull
    private static final CatchRateModifier WEIGHT_BASED = new DynamicMultiplierModifier((Function2<? super LivingEntity, ? super Pokemon, Float>)((Function2)WEIGHT_BASED.1.INSTANCE), (Function2<? super LivingEntity, ? super Pokemon, Boolean>)((Function2)WEIGHT_BASED.2.INSTANCE));

    private CatchRateModifiers() {
    }

    @NotNull
    public final BattleModifier getLEVEL() {
        return LEVEL;
    }

    @NotNull
    public final WorldStateModifier getSUBMERGED_IN_WATER() {
        return SUBMERGED_IN_WATER;
    }

    @NotNull
    public final CatchRateModifier getNEST() {
        return NEST;
    }

    @NotNull
    public final BattleModifier getLOVE() {
        return LOVE;
    }

    @NotNull
    public final CatchRateModifier getMOON_PHASES() {
        return MOON_PHASES;
    }

    @NotNull
    public final WorldStateModifier getLIGHT_LEVEL() {
        return LIGHT_LEVEL;
    }

    @NotNull
    public final WorldStateModifier getSAFARI() {
        return SAFARI;
    }

    @NotNull
    public final WorldStateModifier getPARK() {
        return PARK;
    }

    @NotNull
    public final CatchRateModifier getWEIGHT_BASED() {
        return WEIGHT_BASED;
    }

    @NotNull
    public final CatchRateModifier typeBoosting(float multiplier, ElementalType ... types) {
        Intrinsics.checkNotNullParameter((Object)types, (String)"types");
        return new MultiplierModifier(multiplier, (Function2<? super LivingEntity, ? super Pokemon, Boolean>)((Function2)new Function2<LivingEntity, Pokemon, Boolean>(types){
            final /* synthetic */ ElementalType[] $types;
            {
                this.$types = $types;
                super(2);
            }

            /*
             * WARNING - void declaration
             */
            @NotNull
            public final Boolean invoke(@NotNull LivingEntity livingEntity, @NotNull Pokemon pokemon) {
                boolean bl;
                block3: {
                    void $this$any$iv;
                    Intrinsics.checkNotNullParameter((Object)livingEntity, (String)"<anonymous parameter 0>");
                    Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
                    Iterable<ElementalType> iterable = pokemon.getTypes();
                    Object[] objectArray = this.$types;
                    boolean $i$f$any = false;
                    if ($this$any$iv instanceof Collection && ((Collection)$this$any$iv).isEmpty()) {
                        bl = false;
                    } else {
                        for (T element$iv : $this$any$iv) {
                            ElementalType type = (ElementalType)element$iv;
                            boolean bl2 = false;
                            if (!ArraysKt.contains((Object[])objectArray, (Object)type)) continue;
                            bl = true;
                            break block3;
                        }
                        bl = false;
                    }
                }
                return bl;
            }
        }));
    }

    @NotNull
    public final CatchRateModifier statusBoosting(float multiplier, Status ... status) {
        Intrinsics.checkNotNullParameter((Object)status, (String)"status");
        return new MultiplierModifier(multiplier, (Function2<? super LivingEntity, ? super Pokemon, Boolean>)((Function2)new Function2<LivingEntity, Pokemon, Boolean>(status){
            final /* synthetic */ Status[] $status;
            {
                this.$status = $status;
                super(2);
            }

            @NotNull
            public final Boolean invoke(@NotNull LivingEntity livingEntity, @NotNull Pokemon pokemon) {
                Intrinsics.checkNotNullParameter((Object)livingEntity, (String)"<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
                Object object = pokemon.getStatus();
                if (object == null || (object = ((PersistentStatusContainer)object).getStatus()) == null) {
                    return false;
                }
                return ArraysKt.contains((Object[])this.$status, (Object)object);
            }
        }));
    }

    @NotNull
    public final BattleModifier turnBased(@NotNull Function1<? super Integer, Float> multiplierCalculator) {
        Intrinsics.checkNotNullParameter(multiplierCalculator, (String)"multiplierCalculator");
        return new BattleModifier((Function3<? super ServerPlayer, ? super Iterable<ActiveBattlePokemon>, ? super Pokemon, Float>)((Function3)new Function3<ServerPlayer, Iterable<? extends ActiveBattlePokemon>, Pokemon, Float>(multiplierCalculator){
            final /* synthetic */ Function1<Integer, Float> $multiplierCalculator;
            {
                this.$multiplierCalculator = $multiplierCalculator;
                super(3);
            }

            @NotNull
            public final Float invoke(@NotNull ServerPlayer player, @NotNull Iterable<ActiveBattlePokemon> iterable, @NotNull Pokemon pokemon) {
                Intrinsics.checkNotNullParameter((Object)player, (String)"player");
                Intrinsics.checkNotNullParameter(iterable, (String)"<anonymous parameter 1>");
                Intrinsics.checkNotNullParameter((Object)pokemon, (String)"<anonymous parameter 2>");
                PokemonBattle pokemonBattle = BattleRegistry.INSTANCE.getBattleByParticipatingPlayer(player);
                if (pokemonBattle == null) {
                    return Float.valueOf(1.0f);
                }
                PokemonBattle battle2 = pokemonBattle;
                return (Float)this.$multiplierCalculator.invoke((Object)battle2.getTurn());
            }
        }));
    }
}

