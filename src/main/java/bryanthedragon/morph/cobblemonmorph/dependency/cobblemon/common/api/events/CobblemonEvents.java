package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.battles.BattleFaintedEvent
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.battles.BattleFledEvent
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.battles.BattleStartedPostEvent
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.battles.BattleStartedPreEvent
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.battles.BattleVictoryEvent
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.berry.BerryHarvestEvent
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.berry.BerryMutationOfferEvent
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.berry.BerryMutationResultEvent
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.berry.BerryYieldCalculationEvent
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.drops.LootDroppedEvent
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.entity.PokemonEntityLoadEvent
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.entity.PokemonEntitySaveEvent
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.entity.PokemonEntitySaveToWorldEvent
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.entity.SpawnEvent
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.farming.ApricornHarvestEvent
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.item.LeftoversCreatedEvent
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokeball.PokeBallCaptureCalculatedEvent
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokeball.PokemonCatchRateEvent
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokeball.ThrownPokeballHitEvent
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokemon.ExperienceGainedPostEvent
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokemon.ExperienceGainedPreEvent
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokemon.FossilRevivedEvent
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokemon.FriendshipUpdatedEvent
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokemon.LevelUpEvent
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokemon.PokemonCapturedEvent
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokemon.PokemonFaintedEvent
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokemon.PokemonNicknamedEvent
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokemon.PokemonRecalledEvent
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokemon.PokemonSentPostEvent
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokemon.PokemonSentPreEvent
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokemon.ShoulderMountEvent
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokemon.TradeCompletedEvent
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokemon.evolution.EvolutionAcceptedEvent
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokemon.evolution.EvolutionCompleteEvent
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokemon.evolution.EvolutionDisplayEvent
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokemon.evolution.EvolutionTestedEvent
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokemon.interaction.HeldItemUpdatedEvent
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokemon.interaction.PokemonInteractionGUICreationEvent
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokemon.interaction.ExperienceCandyUseEvent.Post
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokemon.interaction.ExperienceCandyUseEvent.Pre
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.starter.StarterChosenEvent
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.world.BigRootPropagatedEvent
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.CancelableObservable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.EventObservable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.Observable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.SimpleObservable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.Transform
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.TransformObservable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity
import net.minecraft.server.level.ServerPlayer

public object CobblemonEvents {
   public final val APRICORN_HARVESTED: EventObservable<ApricornHarvestEvent> = new EventObservable()
   public final val BATTLE_FAINTED: EventObservable<BattleFaintedEvent> = new EventObservable()
   public final val BATTLE_FLED: EventObservable<BattleFledEvent> = new EventObservable()
   public final val BATTLE_STARTED_POST: EventObservable<BattleStartedPostEvent> = new EventObservable()
   public final val BATTLE_STARTED_PRE: CancelableObservable<BattleStartedPreEvent> = new CancelableObservable()
   public final val BATTLE_VICTORY: EventObservable<BattleVictoryEvent> = new EventObservable()
   public final val BERRY_HARVEST: EventObservable<BerryHarvestEvent> = new EventObservable()
   public final val BERRY_MUTATION_OFFER: EventObservable<BerryMutationOfferEvent> = new EventObservable()
   public final val BERRY_MUTATION_RESULT: EventObservable<BerryMutationResultEvent> = new EventObservable()
   public final val BERRY_YIELD: EventObservable<BerryYieldCalculationEvent> = new EventObservable()
   public final val BIG_ROOT_PROPAGATED: CancelableObservable<BigRootPropagatedEvent> = new CancelableObservable()
   public final val DATA_SYNCHRONIZED: SimpleObservable<ServerPlayer> = new SimpleObservable()
   public final val ENTITY_SPAWN: CancelableObservable<SpawnEvent<*>> = new CancelableObservable()
   public final val EVOLUTION_ACCEPTED: CancelableObservable<EvolutionAcceptedEvent> = new CancelableObservable()
   public final val EVOLUTION_COMPLETE: EventObservable<EvolutionCompleteEvent> = new EventObservable()
   public final val EVOLUTION_DISPLAY: EventObservable<EvolutionDisplayEvent> = new EventObservable()
   public final val EVOLUTION_TESTED: EventObservable<EvolutionTestedEvent> = new EventObservable()
   public final val EXPERIENCE_CANDY_USE_POST: EventObservable<Post> = new EventObservable()
   public final val EXPERIENCE_CANDY_USE_PRE: CancelableObservable<Pre> = new CancelableObservable()
   public final val EXPERIENCE_GAINED_EVENT_POST: EventObservable<ExperienceGainedPostEvent> = new EventObservable()
   public final val EXPERIENCE_GAINED_EVENT_PRE: CancelableObservable<ExperienceGainedPreEvent> = new CancelableObservable()
   public final val FOSSIL_REVIVED: EventObservable<FossilRevivedEvent> = new EventObservable()
   public final val FRIENDSHIP_UPDATED: EventObservable<FriendshipUpdatedEvent> = new EventObservable()
   public final val HELD_ITEM_POST: EventObservable<bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokemon.HeldItemEvent.Post> = new EventObservable()
   public final val HELD_ITEM_PRE: CancelableObservable<bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokemon.HeldItemEvent.Pre> = new CancelableObservable()
   public final val HELD_ITEM_UPDATED: CancelableObservable<HeldItemUpdatedEvent> = new CancelableObservable()
   public final val LEFTOVERS_CREATED: CancelableObservable<LeftoversCreatedEvent> = new CancelableObservable()
   public final val LEVEL_UP_EVENT: EventObservable<LevelUpEvent> = new EventObservable()
   public final val LOOT_DROPPED: CancelableObservable<LootDroppedEvent> = new CancelableObservable()
   public final val POKEMON_CAPTURED: EventObservable<PokemonCapturedEvent> = new EventObservable()
   public final val POKEMON_CATCH_RATE: EventObservable<PokemonCatchRateEvent> = new EventObservable()
   public final val POKEMON_ENTITY_LOAD: CancelableObservable<PokemonEntityLoadEvent> = new CancelableObservable()
   public final val POKEMON_ENTITY_SAVE: EventObservable<PokemonEntitySaveEvent> = new EventObservable()
   public final val POKEMON_ENTITY_SAVE_TO_WORLD: CancelableObservable<PokemonEntitySaveToWorldEvent> = new CancelableObservable()
   public final val POKEMON_ENTITY_SPAWN: TransformObservable<SpawnEvent<*>, SpawnEvent<PokemonEntity>> =
      ENTITY_SPAWN.pipe(
         Observable.Companion.filter(<unrepresentable>.INSTANCE) as Transform<SpawnEvent<?>, SpawnEvent<?>>,
         Observable.Companion.map(<unrepresentable>.INSTANCE)
      )
      public final val POKEMON_FAINTED: EventObservable<PokemonFaintedEvent> = new EventObservable()
   public final val POKEMON_INTERACTION_GUI_CREATION: EventObservable<PokemonInteractionGUICreationEvent> = new EventObservable()
   public final val POKEMON_NICKNAMED: CancelableObservable<PokemonNicknamedEvent> = new CancelableObservable()
   public final val POKEMON_RECALLED: EventObservable<PokemonRecalledEvent> = new EventObservable()
   public final val POKEMON_RELEASED_EVENT_POST: EventObservable<bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.storage.ReleasePokemonEvent.Post> = new EventObservable()
   public final val POKEMON_RELEASED_EVENT_PRE: CancelableObservable<bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.storage.ReleasePokemonEvent.Pre> =
      new CancelableObservable()
      public final val POKEMON_SENT_POST: EventObservable<PokemonSentPostEvent> = new EventObservable()
   public final val POKEMON_SENT_PRE: CancelableObservable<PokemonSentPreEvent> = new CancelableObservable()
   public final val POKE_BALL_CAPTURE_CALCULATED: EventObservable<PokeBallCaptureCalculatedEvent> = new EventObservable()
   public final val SHOULDER_MOUNT: CancelableObservable<ShoulderMountEvent> = new CancelableObservable()
   public final val STARTER_CHOSEN: CancelableObservable<StarterChosenEvent> = new CancelableObservable()
   public final val THROWN_POKEBALL_HIT: CancelableObservable<ThrownPokeballHitEvent> = new CancelableObservable()
   public final val TRADE_COMPLETED: EventObservable<TradeCompletedEvent> = new EventObservable()
}
