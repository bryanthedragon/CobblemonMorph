/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  net.minecraft.advancements.CriterionTrigger
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion.AspectCriterionCondition;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion.AspectCriterionTrigger;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion.BattleCountableCriterionCondition;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion.BattleCountableCriterionTrigger;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion.CaughtPokemonCriterionCondition;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion.CountableContext;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion.CountablePokemonTypeContext;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion.EvolvePokemonContext;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion.EvolvePokemonCriterionCondition;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion.LevelUpContext;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion.LevelUpCriterionCondition;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion.PartyCheckContext;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion.PartyCheckCriterion;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion.PickStarterCriterionCondition;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion.PlantTumblestoneContext;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion.PlantTumblestoneCriterionCondition;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion.PokemonInteractContext;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion.PokemonInteractCriterion;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion.SimpleCountableCriterionCondition;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion.SimpleCriterionTrigger;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion.TradePokemonContext;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion.TradePokemonCriterionCondition;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt;
import kotlin.Metadata;
import net.minecraft.advancements.CriterionTrigger;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings({ "unchecked", "rawtypes" })
@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0090\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u00c6\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b@\u0010AJ%\u0010\u0005\u001a\u00028\u0000\"\f\b\u0000\u0010\u0003*\u0006\u0012\u0002\b\u00030\u00022\u0006\u0010\u0004\u001a\u00028\u0000H\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006R#\u0010\n\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\t0\u00078\u0006\u00a2\u0006\f\n\u0004\b\n\u0010\u000b\u001a\u0004\b\f\u0010\rR#\u0010\u0010\u001a\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u000f0\u00078\u0006\u00a2\u0006\f\n\u0004\b\u0010\u0010\u000b\u001a\u0004\b\u0011\u0010\rR\u0017\u0010\u0013\u001a\u00020\u00128\u0006\u00a2\u0006\f\n\u0004\b\u0013\u0010\u0014\u001a\u0004\b\u0015\u0010\u0016R#\u0010\u0017\u001a\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u000f0\u00078\u0006\u00a2\u0006\f\n\u0004\b\u0017\u0010\u000b\u001a\u0004\b\u0018\u0010\rR#\u0010\u0019\u001a\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u000f0\u00078\u0006\u00a2\u0006\f\n\u0004\b\u0019\u0010\u000b\u001a\u0004\b\u001a\u0010\rR#\u0010\u001d\u001a\u000e\u0012\u0004\u0012\u00020\u001b\u0012\u0004\u0012\u00020\u001c0\u00078\u0006\u00a2\u0006\f\n\u0004\b\u001d\u0010\u000b\u001a\u0004\b\u001e\u0010\rR#\u0010!\u001a\u000e\u0012\u0004\u0012\u00020\u001f\u0012\u0004\u0012\u00020 0\u00078\u0006\u00a2\u0006\f\n\u0004\b!\u0010\u000b\u001a\u0004\b\"\u0010\rR#\u0010%\u001a\u000e\u0012\u0004\u0012\u00020#\u0012\u0004\u0012\u00020$0\u00078\u0006\u00a2\u0006\f\n\u0004\b%\u0010\u000b\u001a\u0004\b&\u0010\rR#\u0010)\u001a\u000e\u0012\u0004\u0012\u00020'\u0012\u0004\u0012\u00020(0\u00078\u0006\u00a2\u0006\f\n\u0004\b)\u0010\u000b\u001a\u0004\b*\u0010\rR#\u0010+\u001a\u000e\u0012\u0004\u0012\u00020'\u0012\u0004\u0012\u00020(0\u00078\u0006\u00a2\u0006\f\n\u0004\b+\u0010\u000b\u001a\u0004\b,\u0010\rR#\u0010/\u001a\u000e\u0012\u0004\u0012\u00020-\u0012\u0004\u0012\u00020.0\u00078\u0006\u00a2\u0006\f\n\u0004\b/\u0010\u000b\u001a\u0004\b0\u0010\rR#\u00103\u001a\u000e\u0012\u0004\u0012\u000201\u0012\u0004\u0012\u0002020\u00078\u0006\u00a2\u0006\f\n\u0004\b3\u0010\u000b\u001a\u0004\b4\u0010\rR#\u00105\u001a\u000e\u0012\u0004\u0012\u00020'\u0012\u0004\u0012\u00020(0\u00078\u0006\u00a2\u0006\f\n\u0004\b5\u0010\u000b\u001a\u0004\b6\u0010\rR#\u00109\u001a\u000e\u0012\u0004\u0012\u000207\u0012\u0004\u0012\u0002080\u00078\u0006\u00a2\u0006\f\n\u0004\b9\u0010\u000b\u001a\u0004\b:\u0010\rR\u0017\u0010<\u001a\u00020;8\u0006\u00a2\u0006\f\n\u0004\b<\u0010=\u001a\u0004\b>\u0010?\u00a8\u0006B"}, d2={"Lcom/cobblemon/mod/common/advancement/CobblemonCriteria;", "", "Lnet/minecraft/advancements/CriterionTrigger;", "T", "criteria", "create", "(Lnet/minecraft/advancements/CriterionTrigger;)Lnet/minecraft/advancements/CriterionTrigger;", "Lcom/cobblemon/mod/common/advancement/criterion/SimpleCriterionTrigger;", "Lcom/cobblemon/mod/common/advancement/criterion/CountablePokemonTypeContext;", "Lcom/cobblemon/mod/common/advancement/criterion/CaughtPokemonCriterionCondition;", "CATCH_POKEMON", "Lcom/cobblemon/mod/common/advancement/criterion/SimpleCriterionTrigger;", "getCATCH_POKEMON", "()Lcom/cobblemon/mod/common/advancement/criterion/SimpleCriterionTrigger;", "Lcom/cobblemon/mod/common/advancement/criterion/CountableContext;", "Lcom/cobblemon/mod/common/advancement/criterion/SimpleCountableCriterionCondition;", "CATCH_SHINY_POKEMON", "getCATCH_SHINY_POKEMON", "Lcom/cobblemon/mod/common/advancement/criterion/AspectCriterionTrigger;", "COLLECT_ASPECT", "Lcom/cobblemon/mod/common/advancement/criterion/AspectCriterionTrigger;", "getCOLLECT_ASPECT", "()Lcom/cobblemon/mod/common/advancement/criterion/AspectCriterionTrigger;", "DEFEAT_POKEMON", "getDEFEAT_POKEMON", "EGG_HATCH", "getEGG_HATCH", "Lcom/cobblemon/mod/common/advancement/criterion/EvolvePokemonContext;", "Lcom/cobblemon/mod/common/advancement/criterion/EvolvePokemonCriterionCondition;", "EVOLVE_POKEMON", "getEVOLVE_POKEMON", "Lcom/cobblemon/mod/common/advancement/criterion/LevelUpContext;", "Lcom/cobblemon/mod/common/advancement/criterion/LevelUpCriterionCondition;", "LEVEL_UP", "getLEVEL_UP", "Lcom/cobblemon/mod/common/advancement/criterion/PartyCheckContext;", "Lcom/cobblemon/mod/common/advancement/criterion/PartyCheckCriterion;", "PARTY_CHECK", "getPARTY_CHECK", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "Lcom/cobblemon/mod/common/advancement/criterion/PickStarterCriterionCondition;", "PASTURE_USE", "getPASTURE_USE", "PICK_STARTER", "getPICK_STARTER", "Lcom/cobblemon/mod/common/advancement/criterion/PlantTumblestoneContext;", "Lcom/cobblemon/mod/common/advancement/criterion/PlantTumblestoneCriterionCondition;", "PLANT_TUMBLESTONE", "getPLANT_TUMBLESTONE", "Lcom/cobblemon/mod/common/advancement/criterion/PokemonInteractContext;", "Lcom/cobblemon/mod/common/advancement/criterion/PokemonInteractCriterion;", "POKEMON_INTERACT", "getPOKEMON_INTERACT", "RESURRECT_POKEMON", "getRESURRECT_POKEMON", "Lcom/cobblemon/mod/common/advancement/criterion/TradePokemonContext;", "Lcom/cobblemon/mod/common/advancement/criterion/TradePokemonCriterionCondition;", "TRADE_POKEMON", "getTRADE_POKEMON", "Lcom/cobblemon/mod/common/advancement/criterion/BattleCountableCriterionTrigger;", "WIN_BATTLE", "Lcom/cobblemon/mod/common/advancement/criterion/BattleCountableCriterionTrigger;", "getWIN_BATTLE", "()Lcom/cobblemon/mod/common/advancement/criterion/BattleCountableCriterionTrigger;", "<init>", "()V", "common"})
public final class CobblemonCriteria {
    @NotNull
    public static final CobblemonCriteria INSTANCE = new CobblemonCriteria();
    @NotNull
    private static final SimpleCriterionTrigger<Pokemon, PickStarterCriterionCondition> PICK_STARTER = (SimpleCriterionTrigger)INSTANCE.create((CriterionTrigger)new SimpleCriterionTrigger(MiscUtilsKt.cobblemonResource("pick_starter"), PickStarterCriterionCondition.class));
    @NotNull
    private static final SimpleCriterionTrigger<CountablePokemonTypeContext, CaughtPokemonCriterionCondition> CATCH_POKEMON = (SimpleCriterionTrigger)INSTANCE.create((CriterionTrigger)new SimpleCriterionTrigger(MiscUtilsKt.cobblemonResource("catch_pokemon"), CaughtPokemonCriterionCondition.class));
    @NotNull
    private static final SimpleCriterionTrigger<CountableContext, SimpleCountableCriterionCondition> CATCH_SHINY_POKEMON = (SimpleCriterionTrigger)INSTANCE.create((CriterionTrigger)new SimpleCriterionTrigger(MiscUtilsKt.cobblemonResource("catch_shiny_pokemon"), SimpleCountableCriterionCondition.class));
    @NotNull
    private static final SimpleCriterionTrigger<CountableContext, SimpleCountableCriterionCondition> EGG_HATCH = (SimpleCriterionTrigger)INSTANCE.create((CriterionTrigger)new SimpleCriterionTrigger(MiscUtilsKt.cobblemonResource("eggs_hatched"), SimpleCountableCriterionCondition.class));
    @NotNull
    private static final SimpleCriterionTrigger<EvolvePokemonContext, EvolvePokemonCriterionCondition> EVOLVE_POKEMON = (SimpleCriterionTrigger)INSTANCE.create((CriterionTrigger)new SimpleCriterionTrigger(MiscUtilsKt.cobblemonResource("pokemon_evolved"), EvolvePokemonCriterionCondition.class));
    @NotNull
    private static final BattleCountableCriterionTrigger WIN_BATTLE = (BattleCountableCriterionTrigger)INSTANCE.create((CriterionTrigger)new BattleCountableCriterionTrigger(MiscUtilsKt.cobblemonResource("battles_won"), BattleCountableCriterionCondition.class));
    @NotNull
    private static final SimpleCriterionTrigger<CountableContext, SimpleCountableCriterionCondition> DEFEAT_POKEMON = (SimpleCriterionTrigger)INSTANCE.create((CriterionTrigger)new SimpleCriterionTrigger(MiscUtilsKt.cobblemonResource("pokemon_defeated"), SimpleCountableCriterionCondition.class));
    @NotNull
    private static final AspectCriterionTrigger COLLECT_ASPECT = (AspectCriterionTrigger)INSTANCE.create((CriterionTrigger)new AspectCriterionTrigger(MiscUtilsKt.cobblemonResource("aspects_collected"), AspectCriterionCondition.class));
    @NotNull
    private static final SimpleCriterionTrigger<PokemonInteractContext, PokemonInteractCriterion> POKEMON_INTERACT = (SimpleCriterionTrigger)INSTANCE.create((CriterionTrigger)new SimpleCriterionTrigger(MiscUtilsKt.cobblemonResource("pokemon_interact"), PokemonInteractCriterion.class));
    @NotNull
    private static final SimpleCriterionTrigger<PartyCheckContext, PartyCheckCriterion> PARTY_CHECK = (SimpleCriterionTrigger)INSTANCE.create((CriterionTrigger)new SimpleCriterionTrigger(MiscUtilsKt.cobblemonResource("party"), PartyCheckCriterion.class));
    @NotNull
    private static final SimpleCriterionTrigger<LevelUpContext, LevelUpCriterionCondition> LEVEL_UP = (SimpleCriterionTrigger)INSTANCE.create((CriterionTrigger)new SimpleCriterionTrigger(MiscUtilsKt.cobblemonResource("level_up"), LevelUpCriterionCondition.class));
    @NotNull
    private static final SimpleCriterionTrigger<Pokemon, PickStarterCriterionCondition> PASTURE_USE = (SimpleCriterionTrigger)INSTANCE.create((CriterionTrigger)new SimpleCriterionTrigger(MiscUtilsKt.cobblemonResource("pasture_use"), PickStarterCriterionCondition.class));
    @NotNull
    private static final SimpleCriterionTrigger<Pokemon, PickStarterCriterionCondition> RESURRECT_POKEMON = (SimpleCriterionTrigger)INSTANCE.create((CriterionTrigger)new SimpleCriterionTrigger(MiscUtilsKt.cobblemonResource("resurrect_pokemon"), PickStarterCriterionCondition.class));
    @NotNull
    private static final SimpleCriterionTrigger<TradePokemonContext, TradePokemonCriterionCondition> TRADE_POKEMON = (SimpleCriterionTrigger)INSTANCE.create((CriterionTrigger)new SimpleCriterionTrigger(MiscUtilsKt.cobblemonResource("trade_pokemon"), TradePokemonCriterionCondition.class));
    @NotNull
    private static final SimpleCriterionTrigger<PlantTumblestoneContext, PlantTumblestoneCriterionCondition> PLANT_TUMBLESTONE = (SimpleCriterionTrigger)INSTANCE.create((CriterionTrigger)new SimpleCriterionTrigger(MiscUtilsKt.cobblemonResource("plant_tumblestone"), PlantTumblestoneCriterionCondition.class));

    private CobblemonCriteria() {
    }

    @NotNull
    public final SimpleCriterionTrigger<Pokemon, PickStarterCriterionCondition> getPICK_STARTER() {
        return PICK_STARTER;
    }

    @NotNull
    public final SimpleCriterionTrigger<CountablePokemonTypeContext, CaughtPokemonCriterionCondition> getCATCH_POKEMON() {
        return CATCH_POKEMON;
    }

    @NotNull
    public final SimpleCriterionTrigger<CountableContext, SimpleCountableCriterionCondition> getCATCH_SHINY_POKEMON() {
        return CATCH_SHINY_POKEMON;
    }

    @NotNull
    public final SimpleCriterionTrigger<CountableContext, SimpleCountableCriterionCondition> getEGG_HATCH() {
        return EGG_HATCH;
    }

    @NotNull
    public final SimpleCriterionTrigger<EvolvePokemonContext, EvolvePokemonCriterionCondition> getEVOLVE_POKEMON() {
        return EVOLVE_POKEMON;
    }

    @NotNull
    public final BattleCountableCriterionTrigger getWIN_BATTLE() {
        return WIN_BATTLE;
    }

    @NotNull
    public final SimpleCriterionTrigger<CountableContext, SimpleCountableCriterionCondition> getDEFEAT_POKEMON() {
        return DEFEAT_POKEMON;
    }

    @NotNull
    public final AspectCriterionTrigger getCOLLECT_ASPECT() {
        return COLLECT_ASPECT;
    }

    @NotNull
    public final SimpleCriterionTrigger<PokemonInteractContext, PokemonInteractCriterion> getPOKEMON_INTERACT() {
        return POKEMON_INTERACT;
    }

    @NotNull
    public final SimpleCriterionTrigger<PartyCheckContext, PartyCheckCriterion> getPARTY_CHECK() {
        return PARTY_CHECK;
    }

    @NotNull
    public final SimpleCriterionTrigger<LevelUpContext, LevelUpCriterionCondition> getLEVEL_UP() {
        return LEVEL_UP;
    }

    @NotNull
    public final SimpleCriterionTrigger<Pokemon, PickStarterCriterionCondition> getPASTURE_USE() {
        return PASTURE_USE;
    }

    @NotNull
    public final SimpleCriterionTrigger<Pokemon, PickStarterCriterionCondition> getRESURRECT_POKEMON() {
        return RESURRECT_POKEMON;
    }

    @NotNull
    public final SimpleCriterionTrigger<TradePokemonContext, TradePokemonCriterionCondition> getTRADE_POKEMON() {
        return TRADE_POKEMON;
    }

    @NotNull
    public final SimpleCriterionTrigger<PlantTumblestoneContext, PlantTumblestoneCriterionCondition> getPLANT_TUMBLESTONE() {
        return PLANT_TUMBLESTONE;
    }

    private final <T extends CriterionTrigger<?>> T create(T criteria) {
        return Cobblemon.INSTANCE.getImplementation().registerCriteria(criteria);
    }
}

