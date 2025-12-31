/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.battles.BattleFaintedEvent;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.battles.BattleFledEvent;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.battles.BattleStartedEvent;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.battles.BattleVictoryEvent;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.battles.instruction.FormeChangeEvent;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.battles.instruction.MegaEvolutionEvent;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.battles.instruction.TerastallizationEvent;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.battles.instruction.ZMoveUsedEvent;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.berry.BerryHarvestEvent;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.berry.BerryMutationOfferEvent;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.berry.BerryMutationResultEvent;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.berry.BerryYieldCalculationEvent;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.cooking.PokeSnackSpawnPokemonEvent;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.drops.LootDroppedEvent;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.entity.*;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.farming.ApricornHarvestEvent;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.fishing.*;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.item.LeftoversCreatedEvent;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokeball.PokeBallCaptureCalculatedEvent;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokeball.PokemonCatchRateEvent;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokeball.ThrownPokeballHitEvent;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokedex.scanning.PokemonScannedEvent;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokemon.*;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokemon.evolution.EvolutionAcceptedEvent;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokemon.evolution.EvolutionCompleteEvent;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokemon.evolution.EvolutionDisplayEvent;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokemon.evolution.EvolutionTestedEvent;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokemon.healing.PokemonHealedEvent;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokemon.interaction.ExperienceCandyUseEvent;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokemon.interaction.PokemonInteractionGUICreationEvent;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.starter.StarterChosenEvent;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.storage.*;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.world.BigRootPropagatedEvent;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.CancelableObservable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.EventObservable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.Observable.Companion.filter;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.Observable.Companion.map;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.SimpleObservable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.riding.events.SelectDriverEvent;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity;

import net.minecraft.server.level.ServerPlayer;

@Suppress("unused")
public final class CobblemonEvents {

    public final POKEMON_PROPERTY_INITIALISED = SimpleObservable<Unit>()

    public final COBBLEMON_INITIALISED = SimpleObservable<Unit>()

    public final DATA_SYNCHRONIZED = SimpleObservable<ServerPlayer>()

    public final SHOULDER_MOUNT = CancelableObservable<ShoulderMountEvent>()

    public final FRIENDSHIP_UPDATED = EventObservable<FriendshipUpdatedEvent>()

    public final FULLNESS_UPDATED = EventObservable<FullnessUpdatedEvent>()

    public final POKEMON_FAINTED = EventObservable<PokemonFaintedEvent>()

    public final EVOLUTION_ACCEPTED = CancelableObservable<EvolutionAcceptedEvent>()

    public final EVOLUTION_DISPLAY = EventObservable<EvolutionDisplayEvent>()

    public final EVOLUTION_TESTED = EventObservable<EvolutionTestedEvent>()

    public final EVOLUTION_COMPLETE = EventObservable<EvolutionCompleteEvent>()

    public final POKEMON_NICKNAMED = CancelableObservable<PokemonNicknamedEvent>()

    public final THROWN_POKEBALL_HIT = CancelableObservable<ThrownPokeballHitEvent>()

    public final POKEMON_CATCH_RATE = EventObservable<PokemonCatchRateEvent>()

    public final POKE_BALL_CAPTURE_CALCULATED = EventObservable<PokeBallCaptureCalculatedEvent>()

    public final POKEMON_CAPTURED = EventObservable<PokemonCapturedEvent>()

    public final FOSSIL_REVIVED = EventObservable<FossilRevivedEvent>()

    public final BATTLE_STARTED_PRE = CancelableObservable<BattleStartedEvent.Pre>()

    public final BATTLE_STARTED_POST = EventObservable<BattleStartedEvent.Post>()

    public final BATTLE_FLED = EventObservable<BattleFledEvent>()

    public final BATTLE_VICTORY = EventObservable<BattleVictoryEvent>()

    public final BATTLE_FAINTED = EventObservable<BattleFaintedEvent>()

    // instructions
    public final MEGA_EVOLUTION = EventObservable<MegaEvolutionEvent>()

    public final TERASTALLIZATION = EventObservable<TerastallizationEvent>()

    public final ZPOWER_USED = EventObservable<ZMoveUsedEvent>()

    public final FORME_CHANGE = EventObservable<FormeChangeEvent>()

    public final POKEMON_SENT_PRE = CancelableObservable<PokemonSentEvent.Pre>()

    public final POKEMON_SENT_POST = EventObservable<PokemonSentEvent.Post>()
    
    public final POKEMON_RECALL_PRE = CancelableObservable<PokemonRecallEvent.Pre>()

    public final POKEMON_RECALL_POST = EventObservable<PokemonRecallEvent.Post>()

    public final TRADE_EVENT_PRE = CancelableObservable<TradeEvent.Pre>()

    public final TRADE_EVENT_POST = EventObservable<TradeEvent.Post>()

    public final RIDE_EVENT_PRE = CancelableObservable<RidePokemonEvent.Pre>()

    public final RIDE_EVENT_APPLY_STAMINA = EventObservable<RidePokemonEvent.ApplyStamina>()

    public final RIDE_EVENT_POST = EventObservable<RidePokemonEvent.Post>()

    public final LEVEL_UP_EVENT = EventObservable<LevelUpEvent>()

    public final POKEMON_HEALED = CancelableObservable<PokemonHealedEvent>()

            /** CLIENT ONLY! */
    public final POKEMON_INTERACTION_GUI_CREATION = EventObservable<PokemonInteractionGUICreationEvent>()

    public final POKEMON_ENTITY_SAVE = EventObservable<PokemonEntitySaveEvent>()

    public final POKEMON_ENTITY_LOAD = CancelableObservable<PokemonEntityLoadEvent>()

    public final POKEMON_ENTITY_SAVE_TO_WORLD = CancelableObservable<PokemonEntitySaveToWorldEvent>()

    public final ENTITY_SPAWN = CancelableObservable<SpawnEvent<*>>()

    public final SHINY_CHANCE_CALCULATION = EventObservable<ShinyChanceCalculationEvent>()

    public final POKEMON_ENTITY_SPAWN = ENTITY_SPAWN.pipe(filter { it.entity is PokemonEntity }, map {it as SpawnEvent<PokemonEntity>;})

    public final EXPERIENCE_GAINED_EVENT_PRE = CancelableObservable<ExperienceGainedEvent.Pre>()

    public final EXPERIENCE_GAINED_EVENT_POST = EventObservable<ExperienceGainedEvent.Post>()

    public final EXPERIENCE_CANDY_USE_PRE = CancelableObservable<ExperienceCandyUseEvent.Pre>()

    public final EXPERIENCE_CANDY_USE_POST = EventObservable<ExperienceCandyUseEvent.Post>()

    public final EV_GAINED_EVENT_PRE = CancelableObservable<EvGainedEvent.Pre>()

    public final EV_GAINED_EVENT_POST = EventObservable<EvGainedEvent.Post>()

    public final HYPER_TRAINED_IV_PRE = CancelableObservable<HyperTrainedIvEvent.Pre>()

    public final HYPER_TRAINED_IV_POST = EventObservable<HyperTrainedIvEvent.Post>()

    public final POKEMON_RELEASED_EVENT_PRE = CancelableObservable<ReleasePokemonEvent.Pre>()

    public final POKEMON_RELEASED_EVENT_POST = EventObservable<ReleasePokemonEvent.Post>()

    public final RENAME_PC_BOX_EVENT_PRE = CancelableObservable<RenamePCBoxEvent.Pre>()

    public final RENAME_PC_BOX_EVENT_POST = EventObservable<RenamePCBoxEvent.Post>()

    public final WALLPAPER_COLLECTION_EVENT = EventObservable<WallpaperCollectionEvent>()

    public final WALLPAPER_UNLOCKED_EVENT = CancelableObservable<WallpaperUnlockedEvent>()

    public final CHANGE_PC_BOX_WALLPAPER_EVENT_PRE = CancelableObservable<ChangePCBoxWallpaperEvent.Pre>()

    public final CHANGE_PC_BOX_WALLPAPER_EVENT_POST = EventObservable<ChangePCBoxWallpaperEvent.Post>()

    public final LOOT_DROPPED = CancelableObservable<LootDroppedEvent>()

    public final STARTER_CHOSEN = CancelableObservable<StarterChosenEvent>()

    public final POKEMON_SCANNED = EventObservable<PokemonScannedEvent>()

    public final APRICORN_HARVESTED = EventObservable<ApricornHarvestEvent>()

    // Berries
    public final BERRY_HARVEST = EventObservable<BerryHarvestEvent>()

    public final BERRY_MUTATION_OFFER = EventObservable<BerryMutationOfferEvent>()

    public final BERRY_MUTATION_RESULT = EventObservable<BerryMutationResultEvent>()

    public final BERRY_YIELD = EventObservable<BerryYieldCalculationEvent>()

    public final LEFTOVERS_CREATED = CancelableObservable<LeftoversCreatedEvent>()

    public final BIG_ROOT_PROPAGATED = CancelableObservable<BigRootPropagatedEvent>()

    public final HELD_ITEM_PRE = CancelableObservable<HeldItemEvent.Pre>()

    public final HELD_ITEM_POST = EventObservable<HeldItemEvent.Post>()

    public final COSMETIC_ITEM_PRE = CancelableObservable<HeldItemEvent.Pre>()

    public final COSMETIC_ITEM_POST = EventObservable<HeldItemEvent.Post>()

    public final POKEMON_GAINED = EventObservable<PokemonGainedEvent>()

    public final POKEMON_SEEN = CancelableObservable<PokemonSeenEvent>()

    public final POKEMON_ASPECTS_CHANGED = EventObservable<PokemonAspectsChangedEvent>()

    public final POKEDEX_DATA_CHANGED_PRE = CancelableObservable<PokedexDataChangedEvent.Pre>()

    public final POKEDEX_DATA_CHANGED_POST = EventObservable<PokedexDataChangedEvent.Post>()

    // Fishing
    public final BAIT_SET = CancelableObservable<BaitSetEvent>()

    public final BAIT_SET_PRE = CancelableObservable<BaitSetEvent>()

    public final BAIT_CONSUMED = CancelableObservable<BaitConsumedEvent>()

    public final POKEROD_CAST_PRE = CancelableObservable<PokerodCastEvent.Pre>()

    public final POKEROD_CAST_POST = EventObservable<PokerodCastEvent.Post>()

    public final POKEROD_REEL = CancelableObservable<PokerodReelEvent>()

    public final BOBBER_SPAWN_POKEMON_PRE = CancelableObservable<BobberSpawnPokemonEvent.Pre>()

    public final BOBBER_SPAWN_POKEMON_MODIFY = EventObservable<BobberSpawnPokemonEvent.Modify>()

    public final BOBBER_SPAWN_POKEMON_POST = EventObservable<BobberSpawnPokemonEvent.Post>()

    public final POKE_SNACK_SPAWN_POKEMON_PRE = CancelableObservable<PokeSnackSpawnPokemonEvent.Pre>()

    public final POKE_SNACK_SPAWN_POKEMON_POST = EventObservable<PokeSnackSpawnPokemonEvent.Post>()

    public final BAIT_EFFECT_REGISTRATION = EventObservable<BaitEffectFunctionRegistryEvent>()

    public final SPAWN_BUCKET_CHOSEN = EventObservable<SpawnBucketChosenEvent>()

    // Baits and Lures
    public final BAIT_SPAWN_POKEMON_MODIFY = EventObservable<BaitSpawnPokemonEvent.Modify>()


    public final COLLECT_EGG = CancelableObservable<CollectEggEvent>()

    public final HATCH_EGG_PRE = CancelableObservable<HatchEggEvent.Pre>()

    public final HATCH_EGG_POST = EventObservable<HatchEggEvent.Post>()

    // -------------------------------------------------------------------------------------
    //
    // Riding
    //
    // -------------------------------------------------------------------------------------
    public final SELECT_DRIVER = EventObservable<SelectDriverEvent>()
}
