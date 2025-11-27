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
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtils;

import net.minecraft.advancements.CriterionTrigger;

import org.jetbrains.annotations.NotNull;

@SuppressWarnings({ "unchecked", "rawtypes" })
public final class CobblemonCriteria {
    @NotNull
    public static final CobblemonCriteria INSTANCE = new CobblemonCriteria();
    @NotNull
    private static final SimpleCriterionTrigger<Pokemon, PickStarterCriterionCondition> PICK_STARTER = (SimpleCriterionTrigger)INSTANCE.create((CriterionTrigger)new SimpleCriterionTrigger(MiscUtils.cobblemonResource("pick_starter"), PickStarterCriterionCondition.class));
    @NotNull
    private static final SimpleCriterionTrigger<CountablePokemonTypeContext, CaughtPokemonCriterionCondition> CATCH_POKEMON = (SimpleCriterionTrigger)INSTANCE.create((CriterionTrigger)new SimpleCriterionTrigger(MiscUtils.cobblemonResource("catch_pokemon"), CaughtPokemonCriterionCondition.class));
    @NotNull
    private static final SimpleCriterionTrigger<CountableContext, SimpleCountableCriterionCondition> CATCH_SHINY_POKEMON = (SimpleCriterionTrigger)INSTANCE.create((CriterionTrigger)new SimpleCriterionTrigger(MiscUtils.cobblemonResource("catch_shiny_pokemon"), SimpleCountableCriterionCondition.class));
    @NotNull
    private static final SimpleCriterionTrigger<CountableContext, SimpleCountableCriterionCondition> EGG_HATCH = (SimpleCriterionTrigger)INSTANCE.create((CriterionTrigger)new SimpleCriterionTrigger(MiscUtils.cobblemonResource("eggs_hatched"), SimpleCountableCriterionCondition.class));
    @NotNull
    private static final SimpleCriterionTrigger<EvolvePokemonContext, EvolvePokemonCriterionCondition> EVOLVE_POKEMON = (SimpleCriterionTrigger)INSTANCE.create((CriterionTrigger)new SimpleCriterionTrigger(MiscUtils.cobblemonResource("pokemon_evolved"), EvolvePokemonCriterionCondition.class));
    @NotNull
    private static final BattleCountableCriterionTrigger WIN_BATTLE = (BattleCountableCriterionTrigger)INSTANCE.create((CriterionTrigger)new BattleCountableCriterionTrigger(MiscUtils.cobblemonResource("battles_won"), BattleCountableCriterionCondition.class));
    @NotNull
    private static final SimpleCriterionTrigger<CountableContext, SimpleCountableCriterionCondition> DEFEAT_POKEMON = (SimpleCriterionTrigger)INSTANCE.create((CriterionTrigger)new SimpleCriterionTrigger(MiscUtils.cobblemonResource("pokemon_defeated"), SimpleCountableCriterionCondition.class));
    @NotNull
    private static final AspectCriterionTrigger COLLECT_ASPECT = (AspectCriterionTrigger)INSTANCE.create((CriterionTrigger)new AspectCriterionTrigger(MiscUtils.cobblemonResource("aspects_collected"), AspectCriterionCondition.class));
    @NotNull
    private static final SimpleCriterionTrigger<PokemonInteractContext, PokemonInteractCriterion> POKEMON_INTERACT = (SimpleCriterionTrigger)INSTANCE.create((CriterionTrigger)new SimpleCriterionTrigger(MiscUtils.cobblemonResource("pokemon_interact"), PokemonInteractCriterion.class));
    @NotNull
    private static final SimpleCriterionTrigger<PartyCheckContext, PartyCheckCriterion> PARTY_CHECK = (SimpleCriterionTrigger)INSTANCE.create((CriterionTrigger)new SimpleCriterionTrigger(MiscUtils.cobblemonResource("party"), PartyCheckCriterion.class));
    @NotNull
    private static final SimpleCriterionTrigger<LevelUpContext, LevelUpCriterionCondition> LEVEL_UP = (SimpleCriterionTrigger)INSTANCE.create((CriterionTrigger)new SimpleCriterionTrigger(MiscUtils.cobblemonResource("level_up"), LevelUpCriterionCondition.class));
    @NotNull
    private static final SimpleCriterionTrigger<Pokemon, PickStarterCriterionCondition> PASTURE_USE = (SimpleCriterionTrigger)INSTANCE.create((CriterionTrigger)new SimpleCriterionTrigger(MiscUtils.cobblemonResource("pasture_use"), PickStarterCriterionCondition.class));
    @NotNull
    private static final SimpleCriterionTrigger<Pokemon, PickStarterCriterionCondition> RESURRECT_POKEMON = (SimpleCriterionTrigger)INSTANCE.create((CriterionTrigger)new SimpleCriterionTrigger(MiscUtils.cobblemonResource("resurrect_pokemon"), PickStarterCriterionCondition.class));
    @NotNull
    private static final SimpleCriterionTrigger<TradePokemonContext, TradePokemonCriterionCondition> TRADE_POKEMON = (SimpleCriterionTrigger)INSTANCE.create((CriterionTrigger)new SimpleCriterionTrigger(MiscUtils.cobblemonResource("trade_pokemon"), TradePokemonCriterionCondition.class));
    @NotNull
    private static final SimpleCriterionTrigger<PlantTumblestoneContext, PlantTumblestoneCriterionCondition> PLANT_TUMBLESTONE = (SimpleCriterionTrigger)INSTANCE.create((CriterionTrigger)new SimpleCriterionTrigger(MiscUtils.cobblemonResource("plant_tumblestone"), PlantTumblestoneCriterionCondition.class));

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

