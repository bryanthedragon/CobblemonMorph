/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.JvmField
 *  net.minecraft.server.level.ServerPlayer
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.CobblemonEvents;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.battles.BattleFaintedEvent;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.battles.BattleFledEvent;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.battles.BattleStartedPostEvent;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.battles.BattleStartedPreEvent;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.battles.BattleVictoryEvent;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.berry.BerryHarvestEvent;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.berry.BerryMutationOfferEvent;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.berry.BerryMutationResultEvent;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.berry.BerryYieldCalculationEvent;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.drops.LootDroppedEvent;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.entity.PokemonEntityLoadEvent;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.entity.PokemonEntitySaveEvent;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.entity.PokemonEntitySaveToWorldEvent;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.entity.SpawnEvent;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.farming.ApricornHarvestEvent;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.item.LeftoversCreatedEvent;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokeball.PokeBallCaptureCalculatedEvent;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokeball.PokemonCatchRateEvent;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokeball.ThrownPokeballHitEvent;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokemon.ExperienceGainedPostEvent;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokemon.ExperienceGainedPreEvent;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokemon.FossilRevivedEvent;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokemon.FriendshipUpdatedEvent;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokemon.HeldItemEvent;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokemon.LevelUpEvent;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokemon.PokemonCapturedEvent;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokemon.PokemonFaintedEvent;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokemon.PokemonNicknamedEvent;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokemon.PokemonRecalledEvent;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokemon.PokemonSentPostEvent;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokemon.PokemonSentPreEvent;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokemon.ShoulderMountEvent;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokemon.TradeCompletedEvent;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokemon.evolution.EvolutionAcceptedEvent;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokemon.evolution.EvolutionCompleteEvent;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokemon.evolution.EvolutionDisplayEvent;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokemon.evolution.EvolutionTestedEvent;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokemon.interaction.ExperienceCandyUseEvent;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokemon.interaction.HeldItemUpdatedEvent;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokemon.interaction.PokemonInteractionGUICreationEvent;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.starter.StarterChosenEvent;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.storage.ReleasePokemonEvent;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.world.BigRootPropagatedEvent;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.CancelableObservable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.EventObservable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.Observable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.SimpleObservable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.TransformObservable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity;
import kotlin.Metadata;
import kotlin.jvm.JvmField;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u00c2\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u00c6\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\bj\u0010kR\u001a\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u00028\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\u0004\u0010\u0005R\u001a\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00060\u00028\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\u0007\u0010\u0005R\u001a\u0010\t\u001a\b\u0012\u0004\u0012\u00020\b0\u00028\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\t\u0010\u0005R\u001a\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\n0\u00028\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\u000b\u0010\u0005R\u001a\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\r0\f8\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\u000e\u0010\u000fR\u001a\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00100\u00028\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\u0011\u0010\u0005R\u001a\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00120\u00028\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\u0013\u0010\u0005R\u001a\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00140\u00028\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\u0015\u0010\u0005R\u001a\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00160\u00028\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\u0017\u0010\u0005R\u001a\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00180\u00028\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\u0019\u0010\u0005R\u001a\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u001a0\f8\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\u001b\u0010\u000fR\u001a\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u001d0\u001c8\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\u001e\u0010\u001fR\u001e\u0010!\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030 0\f8\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b!\u0010\u000fR\u001a\u0010#\u001a\b\u0012\u0004\u0012\u00020\"0\f8\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b#\u0010\u000fR\u001a\u0010%\u001a\b\u0012\u0004\u0012\u00020$0\u00028\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b%\u0010\u0005R\u001a\u0010'\u001a\b\u0012\u0004\u0012\u00020&0\u00028\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b'\u0010\u0005R\u001a\u0010)\u001a\b\u0012\u0004\u0012\u00020(0\u00028\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b)\u0010\u0005R\u001a\u0010+\u001a\b\u0012\u0004\u0012\u00020*0\u00028\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b+\u0010\u0005R\u001a\u0010-\u001a\b\u0012\u0004\u0012\u00020,0\f8\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b-\u0010\u000fR\u001a\u0010/\u001a\b\u0012\u0004\u0012\u00020.0\u00028\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b/\u0010\u0005R\u001a\u00101\u001a\b\u0012\u0004\u0012\u0002000\f8\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b1\u0010\u000fR\u001a\u00103\u001a\b\u0012\u0004\u0012\u0002020\u00028\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b3\u0010\u0005R\u001a\u00105\u001a\b\u0012\u0004\u0012\u0002040\u00028\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b5\u0010\u0005R\u001a\u00107\u001a\b\u0012\u0004\u0012\u0002060\u00028\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b7\u0010\u0005R\u001a\u00109\u001a\b\u0012\u0004\u0012\u0002080\f8\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b9\u0010\u000fR\u001a\u0010;\u001a\b\u0012\u0004\u0012\u00020:0\f8\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b;\u0010\u000fR\u001a\u0010=\u001a\b\u0012\u0004\u0012\u00020<0\f8\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b=\u0010\u000fR\u001a\u0010?\u001a\b\u0012\u0004\u0012\u00020>0\u00028\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b?\u0010\u0005R\u001a\u0010A\u001a\b\u0012\u0004\u0012\u00020@0\f8\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\bA\u0010\u000fR\u001a\u0010C\u001a\b\u0012\u0004\u0012\u00020B0\u00028\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\bC\u0010\u0005R\u001a\u0010E\u001a\b\u0012\u0004\u0012\u00020D0\u00028\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\bE\u0010\u0005R\u001a\u0010G\u001a\b\u0012\u0004\u0012\u00020F0\f8\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\bG\u0010\u000fR\u001a\u0010I\u001a\b\u0012\u0004\u0012\u00020H0\u00028\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\bI\u0010\u0005R\u001a\u0010K\u001a\b\u0012\u0004\u0012\u00020J0\f8\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\bK\u0010\u000fR*\u0010N\u001a\u0018\u0012\b\u0012\u0006\u0012\u0002\b\u00030 \u0012\n\u0012\b\u0012\u0004\u0012\u00020M0 0L8\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\bN\u0010OR\u001a\u0010Q\u001a\b\u0012\u0004\u0012\u00020P0\u00028\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\bQ\u0010\u0005R\u001a\u0010S\u001a\b\u0012\u0004\u0012\u00020R0\u00028\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\bS\u0010\u0005R\u001a\u0010U\u001a\b\u0012\u0004\u0012\u00020T0\f8\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\bU\u0010\u000fR\u001a\u0010W\u001a\b\u0012\u0004\u0012\u00020V0\u00028\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\bW\u0010\u0005R\u001a\u0010Y\u001a\b\u0012\u0004\u0012\u00020X0\u00028\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\bY\u0010\u0005R\u001a\u0010[\u001a\b\u0012\u0004\u0012\u00020Z0\f8\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b[\u0010\u000fR\u001a\u0010]\u001a\b\u0012\u0004\u0012\u00020\\0\u00028\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b]\u0010\u0005R\u001a\u0010_\u001a\b\u0012\u0004\u0012\u00020^0\f8\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b_\u0010\u000fR\u001a\u0010a\u001a\b\u0012\u0004\u0012\u00020`0\u00028\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\ba\u0010\u0005R\u001a\u0010c\u001a\b\u0012\u0004\u0012\u00020b0\f8\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\bc\u0010\u000fR\u001a\u0010e\u001a\b\u0012\u0004\u0012\u00020d0\f8\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\be\u0010\u000fR\u001a\u0010g\u001a\b\u0012\u0004\u0012\u00020f0\f8\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\bg\u0010\u000fR\u001a\u0010i\u001a\b\u0012\u0004\u0012\u00020h0\u00028\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\bi\u0010\u0005\u00a8\u0006l"}, d2={"Lcom/cobblemon/mod/common/api/events/CobblemonEvents;", "", "Lcom/cobblemon/mod/common/api/reactive/EventObservable;", "Lcom/cobblemon/mod/common/api/events/farming/ApricornHarvestEvent;", "APRICORN_HARVESTED", "Lcom/cobblemon/mod/common/api/reactive/EventObservable;", "Lcom/cobblemon/mod/common/api/events/battles/BattleFaintedEvent;", "BATTLE_FAINTED", "Lcom/cobblemon/mod/common/api/events/battles/BattleFledEvent;", "BATTLE_FLED", "Lcom/cobblemon/mod/common/api/events/battles/BattleStartedPostEvent;", "BATTLE_STARTED_POST", "Lcom/cobblemon/mod/common/api/reactive/CancelableObservable;", "Lcom/cobblemon/mod/common/api/events/battles/BattleStartedPreEvent;", "BATTLE_STARTED_PRE", "Lcom/cobblemon/mod/common/api/reactive/CancelableObservable;", "Lcom/cobblemon/mod/common/api/events/battles/BattleVictoryEvent;", "BATTLE_VICTORY", "Lcom/cobblemon/mod/common/api/events/berry/BerryHarvestEvent;", "BERRY_HARVEST", "Lcom/cobblemon/mod/common/api/events/berry/BerryMutationOfferEvent;", "BERRY_MUTATION_OFFER", "Lcom/cobblemon/mod/common/api/events/berry/BerryMutationResultEvent;", "BERRY_MUTATION_RESULT", "Lcom/cobblemon/mod/common/api/events/berry/BerryYieldCalculationEvent;", "BERRY_YIELD", "Lcom/cobblemon/mod/common/api/events/world/BigRootPropagatedEvent;", "BIG_ROOT_PROPAGATED", "Lcom/cobblemon/mod/common/api/reactive/SimpleObservable;", "Lnet/minecraft/server/level/ServerPlayer;", "DATA_SYNCHRONIZED", "Lcom/cobblemon/mod/common/api/reactive/SimpleObservable;", "Lcom/cobblemon/mod/common/api/events/entity/SpawnEvent;", "ENTITY_SPAWN", "Lcom/cobblemon/mod/common/api/events/pokemon/evolution/EvolutionAcceptedEvent;", "EVOLUTION_ACCEPTED", "Lcom/cobblemon/mod/common/api/events/pokemon/evolution/EvolutionCompleteEvent;", "EVOLUTION_COMPLETE", "Lcom/cobblemon/mod/common/api/events/pokemon/evolution/EvolutionDisplayEvent;", "EVOLUTION_DISPLAY", "Lcom/cobblemon/mod/common/api/events/pokemon/evolution/EvolutionTestedEvent;", "EVOLUTION_TESTED", "Lcom/cobblemon/mod/common/api/events/pokemon/interaction/ExperienceCandyUseEvent$Post;", "EXPERIENCE_CANDY_USE_POST", "Lcom/cobblemon/mod/common/api/events/pokemon/interaction/ExperienceCandyUseEvent$Pre;", "EXPERIENCE_CANDY_USE_PRE", "Lcom/cobblemon/mod/common/api/events/pokemon/ExperienceGainedPostEvent;", "EXPERIENCE_GAINED_EVENT_POST", "Lcom/cobblemon/mod/common/api/events/pokemon/ExperienceGainedPreEvent;", "EXPERIENCE_GAINED_EVENT_PRE", "Lcom/cobblemon/mod/common/api/events/pokemon/FossilRevivedEvent;", "FOSSIL_REVIVED", "Lcom/cobblemon/mod/common/api/events/pokemon/FriendshipUpdatedEvent;", "FRIENDSHIP_UPDATED", "Lcom/cobblemon/mod/common/api/events/pokemon/HeldItemEvent$Post;", "HELD_ITEM_POST", "Lcom/cobblemon/mod/common/api/events/pokemon/HeldItemEvent$Pre;", "HELD_ITEM_PRE", "Lcom/cobblemon/mod/common/api/events/pokemon/interaction/HeldItemUpdatedEvent;", "HELD_ITEM_UPDATED", "Lcom/cobblemon/mod/common/api/events/item/LeftoversCreatedEvent;", "LEFTOVERS_CREATED", "Lcom/cobblemon/mod/common/api/events/pokemon/LevelUpEvent;", "LEVEL_UP_EVENT", "Lcom/cobblemon/mod/common/api/events/drops/LootDroppedEvent;", "LOOT_DROPPED", "Lcom/cobblemon/mod/common/api/events/pokemon/PokemonCapturedEvent;", "POKEMON_CAPTURED", "Lcom/cobblemon/mod/common/api/events/pokeball/PokemonCatchRateEvent;", "POKEMON_CATCH_RATE", "Lcom/cobblemon/mod/common/api/events/entity/PokemonEntityLoadEvent;", "POKEMON_ENTITY_LOAD", "Lcom/cobblemon/mod/common/api/events/entity/PokemonEntitySaveEvent;", "POKEMON_ENTITY_SAVE", "Lcom/cobblemon/mod/common/api/events/entity/PokemonEntitySaveToWorldEvent;", "POKEMON_ENTITY_SAVE_TO_WORLD", "Lcom/cobblemon/mod/common/api/reactive/TransformObservable;", "Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;", "POKEMON_ENTITY_SPAWN", "Lcom/cobblemon/mod/common/api/reactive/TransformObservable;", "Lcom/cobblemon/mod/common/api/events/pokemon/PokemonFaintedEvent;", "POKEMON_FAINTED", "Lcom/cobblemon/mod/common/api/events/pokemon/interaction/PokemonInteractionGUICreationEvent;", "POKEMON_INTERACTION_GUI_CREATION", "Lcom/cobblemon/mod/common/api/events/pokemon/PokemonNicknamedEvent;", "POKEMON_NICKNAMED", "Lcom/cobblemon/mod/common/api/events/pokemon/PokemonRecalledEvent;", "POKEMON_RECALLED", "Lcom/cobblemon/mod/common/api/events/storage/ReleasePokemonEvent$Post;", "POKEMON_RELEASED_EVENT_POST", "Lcom/cobblemon/mod/common/api/events/storage/ReleasePokemonEvent$Pre;", "POKEMON_RELEASED_EVENT_PRE", "Lcom/cobblemon/mod/common/api/events/pokemon/PokemonSentPostEvent;", "POKEMON_SENT_POST", "Lcom/cobblemon/mod/common/api/events/pokemon/PokemonSentPreEvent;", "POKEMON_SENT_PRE", "Lcom/cobblemon/mod/common/api/events/pokeball/PokeBallCaptureCalculatedEvent;", "POKE_BALL_CAPTURE_CALCULATED", "Lcom/cobblemon/mod/common/api/events/pokemon/ShoulderMountEvent;", "SHOULDER_MOUNT", "Lcom/cobblemon/mod/common/api/events/starter/StarterChosenEvent;", "STARTER_CHOSEN", "Lcom/cobblemon/mod/common/api/events/pokeball/ThrownPokeballHitEvent;", "THROWN_POKEBALL_HIT", "Lcom/cobblemon/mod/common/api/events/pokemon/TradeCompletedEvent;", "TRADE_COMPLETED", "<init>", "()V", "common"})
public final class CobblemonEvents {
    @NotNull
    public static final CobblemonEvents INSTANCE = new CobblemonEvents();
    @JvmField
    @NotNull
    public static final SimpleObservable<ServerPlayer> DATA_SYNCHRONIZED = new SimpleObservable();
    @JvmField
    @NotNull
    public static final CancelableObservable<ShoulderMountEvent> SHOULDER_MOUNT = new CancelableObservable();
    @JvmField
    @NotNull
    public static final EventObservable<FriendshipUpdatedEvent> FRIENDSHIP_UPDATED = new EventObservable();
    @JvmField
    @NotNull
    public static final EventObservable<PokemonFaintedEvent> POKEMON_FAINTED = new EventObservable();
    @JvmField
    @NotNull
    public static final CancelableObservable<EvolutionAcceptedEvent> EVOLUTION_ACCEPTED = new CancelableObservable();
    @JvmField
    @NotNull
    public static final EventObservable<EvolutionDisplayEvent> EVOLUTION_DISPLAY = new EventObservable();
    @JvmField
    @NotNull
    public static final EventObservable<EvolutionTestedEvent> EVOLUTION_TESTED = new EventObservable();
    @JvmField
    @NotNull
    public static final EventObservable<EvolutionCompleteEvent> EVOLUTION_COMPLETE = new EventObservable();
    @JvmField
    @NotNull
    public static final CancelableObservable<PokemonNicknamedEvent> POKEMON_NICKNAMED = new CancelableObservable();
    @JvmField
    @NotNull
    public static final CancelableObservable<HeldItemUpdatedEvent> HELD_ITEM_UPDATED = new CancelableObservable();
    @JvmField
    @NotNull
    public static final CancelableObservable<ThrownPokeballHitEvent> THROWN_POKEBALL_HIT = new CancelableObservable();
    @JvmField
    @NotNull
    public static final EventObservable<PokemonCatchRateEvent> POKEMON_CATCH_RATE = new EventObservable();
    @JvmField
    @NotNull
    public static final EventObservable<PokeBallCaptureCalculatedEvent> POKE_BALL_CAPTURE_CALCULATED = new EventObservable();
    @JvmField
    @NotNull
    public static final EventObservable<PokemonCapturedEvent> POKEMON_CAPTURED = new EventObservable();
    @JvmField
    @NotNull
    public static final EventObservable<FossilRevivedEvent> FOSSIL_REVIVED = new EventObservable();
    @JvmField
    @NotNull
    public static final CancelableObservable<BattleStartedPreEvent> BATTLE_STARTED_PRE = new CancelableObservable();
    @JvmField
    @NotNull
    public static final EventObservable<BattleStartedPostEvent> BATTLE_STARTED_POST = new EventObservable();
    @JvmField
    @NotNull
    public static final EventObservable<BattleFledEvent> BATTLE_FLED = new EventObservable();
    @JvmField
    @NotNull
    public static final EventObservable<BattleVictoryEvent> BATTLE_VICTORY = new EventObservable();
    @JvmField
    @NotNull
    public static final EventObservable<BattleFaintedEvent> BATTLE_FAINTED = new EventObservable();
    @JvmField
    @NotNull
    public static final CancelableObservable<PokemonSentPreEvent> POKEMON_SENT_PRE = new CancelableObservable();
    @JvmField
    @NotNull
    public static final EventObservable<PokemonSentPostEvent> POKEMON_SENT_POST = new EventObservable();
    @JvmField
    @NotNull
    public static final EventObservable<PokemonRecalledEvent> POKEMON_RECALLED = new EventObservable();
    @JvmField
    @NotNull
    public static final EventObservable<TradeCompletedEvent> TRADE_COMPLETED = new EventObservable();
    @JvmField
    @NotNull
    public static final EventObservable<LevelUpEvent> LEVEL_UP_EVENT = new EventObservable();
    @JvmField
    @NotNull
    public static final EventObservable<PokemonInteractionGUICreationEvent> POKEMON_INTERACTION_GUI_CREATION = new EventObservable();
    @JvmField
    @NotNull
    public static final EventObservable<PokemonEntitySaveEvent> POKEMON_ENTITY_SAVE = new EventObservable();
    @JvmField
    @NotNull
    public static final CancelableObservable<PokemonEntityLoadEvent> POKEMON_ENTITY_LOAD = new CancelableObservable();
    @JvmField
    @NotNull
    public static final CancelableObservable<PokemonEntitySaveToWorldEvent> POKEMON_ENTITY_SAVE_TO_WORLD = new CancelableObservable();
    @JvmField
    @NotNull
    public static final CancelableObservable<SpawnEvent<?>> ENTITY_SPAWN = new CancelableObservable();
    @JvmField
    @NotNull
    public static final TransformObservable<SpawnEvent<?>, SpawnEvent<PokemonEntity>> POKEMON_ENTITY_SPAWN = ENTITY_SPAWN.pipe(Observable.Companion.filter(POKEMON_ENTITY_SPAWN.1.INSTANCE), Observable.Companion.map(POKEMON_ENTITY_SPAWN.2.INSTANCE));
    @JvmField
    @NotNull
    public static final CancelableObservable<ExperienceGainedPreEvent> EXPERIENCE_GAINED_EVENT_PRE = new CancelableObservable();
    @JvmField
    @NotNull
    public static final EventObservable<ExperienceGainedPostEvent> EXPERIENCE_GAINED_EVENT_POST = new EventObservable();
    @JvmField
    @NotNull
    public static final CancelableObservable<ExperienceCandyUseEvent.Pre> EXPERIENCE_CANDY_USE_PRE = new CancelableObservable();
    @JvmField
    @NotNull
    public static final EventObservable<ExperienceCandyUseEvent.Post> EXPERIENCE_CANDY_USE_POST = new EventObservable();
    @JvmField
    @NotNull
    public static final CancelableObservable<ReleasePokemonEvent.Pre> POKEMON_RELEASED_EVENT_PRE = new CancelableObservable();
    @JvmField
    @NotNull
    public static final EventObservable<ReleasePokemonEvent.Post> POKEMON_RELEASED_EVENT_POST = new EventObservable();
    @JvmField
    @NotNull
    public static final CancelableObservable<LootDroppedEvent> LOOT_DROPPED = new CancelableObservable();
    @JvmField
    @NotNull
    public static final CancelableObservable<StarterChosenEvent> STARTER_CHOSEN = new CancelableObservable();
    @JvmField
    @NotNull
    public static final EventObservable<ApricornHarvestEvent> APRICORN_HARVESTED = new EventObservable();
    @JvmField
    @NotNull
    public static final EventObservable<BerryHarvestEvent> BERRY_HARVEST = new EventObservable();
    @JvmField
    @NotNull
    public static final EventObservable<BerryMutationOfferEvent> BERRY_MUTATION_OFFER = new EventObservable();
    @JvmField
    @NotNull
    public static final EventObservable<BerryMutationResultEvent> BERRY_MUTATION_RESULT = new EventObservable();
    @JvmField
    @NotNull
    public static final EventObservable<BerryYieldCalculationEvent> BERRY_YIELD = new EventObservable();
    @JvmField
    @NotNull
    public static final CancelableObservable<LeftoversCreatedEvent> LEFTOVERS_CREATED = new CancelableObservable();
    @JvmField
    @NotNull
    public static final CancelableObservable<BigRootPropagatedEvent> BIG_ROOT_PROPAGATED = new CancelableObservable();
    @JvmField
    @NotNull
    public static final CancelableObservable<HeldItemEvent.Pre> HELD_ITEM_PRE = new CancelableObservable();
    @JvmField
    @NotNull
    public static final EventObservable<HeldItemEvent.Post> HELD_ITEM_POST = new EventObservable();

    private CobblemonEvents() {
    }
}

