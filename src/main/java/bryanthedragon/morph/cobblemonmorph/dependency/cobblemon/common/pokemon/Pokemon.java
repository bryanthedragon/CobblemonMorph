/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonPrimitive
 *  com.mojang.authlib.GameProfile
 *  com.mojang.datafixers.util.Pair
 *  com.mojang.serialization.Dynamic
 *  com.mojang.serialization.DynamicOps
 *  com.mojang.serialization.JsonOps
 *  kotlin.Lazy
 *  kotlin.LazyKt
 *  kotlin.Metadata
 *  kotlin.Unit
 *  kotlin.collections.CollectionsKt
 *  kotlin.collections.SetsKt
 *  kotlin.comparisons.ComparisonsKt
 *  kotlin.jvm.functions.Function0
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  kotlin.math.MathKt
 *  kotlin.random.Random
 *  kotlin.ranges.ClosedRange
 *  kotlin.ranges.IntRange
 *  kotlin.ranges.RangesKt
 *  kotlin.text.StringsKt
 *  net.minecraft.ResourceLocationException
 *  net.minecraft.core.BlockPos
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.ListTag
 *  net.minecraft.nbt.NbtOps
 *  net.minecraft.nbt.StringTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.Component$Serializer
 *  net.minecraft.network.chat.ComponentContents
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.MinecraftServer
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.tags.FluidTags
 *  net.minecraft.util.Mth
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelAccessor
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.CactusBlock
 *  net.minecraft.world.level.block.CampfireBlock
 *  net.minecraft.world.level.block.FireBlock
 *  net.minecraft.world.level.block.MagmaBlock
 *  net.minecraft.world.level.block.SweetBerryBushBlock
 *  net.minecraft.world.level.block.WitherRoseBlock
 *  net.minecraft.world.phys.Vec3
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.struct.VariableStruct;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.value.DoubleValue;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonNetwork;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonSounds;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.abilities.Abilities;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.abilities.Ability;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.abilities.PotentialAbility;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.data.ShowdownIdentifiable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.Cancelable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.CobblemonEvents;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokemon.ExperienceGainedPostEvent;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokemon.ExperienceGainedPreEvent;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokemon.FriendshipUpdatedEvent;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokemon.HeldItemEvent;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokemon.LevelUpEvent;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokemon.PokemonFaintedEvent;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokemon.PokemonRecalledEvent;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokemon.PokemonSentPostEvent;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokemon.PokemonSentPreEvent;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.BenchedMove;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.BenchedMoves;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.Move;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.MoveSet;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.MoveTemplate;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.Moves;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.PokeBalls;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.Natures;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonProperties;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonPropertyExtractor;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonSpecies;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.aspect.AspectProvider;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.Evolution;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.EvolutionDisplay;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.EvolutionProxy;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.PreEvolution;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.progress.EvolutionProgress;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.experience.ExperienceGroup;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.experience.ExperienceSource;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.feature.SpeciesFeature;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.feature.SpeciesFeatureProvider;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.feature.SpeciesFeatures;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.feature.SynchronizedSpeciesFeature;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.feature.SynchronizedSpeciesFeatureProvider;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.friendship.FriendshipMutationCalculator;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.moves.LearnsetQuery;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.stats.Stat;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.stats.Stats;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.properties.CustomPokemonProperty;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.CancelableObservable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.EventObservable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.Observable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.SettableObservable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.SimpleObservable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.scheduling.SchedulingFunctionsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.InvalidSpeciesException;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.PokemonStore;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.StoreCoordinates;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.party.PlayerPartyStore;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.pc.PCStore;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.types.ElementalType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.types.ElementalTypes;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.types.tera.TeraType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.types.tera.TeraTypes;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.effects.IllusionEffect;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.PokemonUpdatePacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pokemon.update.AbilityUpdatePacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pokemon.update.AspectsUpdatePacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pokemon.update.BenchedMovesUpdatePacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pokemon.update.CaughtBallUpdatePacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pokemon.update.DmaxLevelUpdatePacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pokemon.update.EVsUpdatePacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pokemon.update.ExperienceUpdatePacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pokemon.update.FormUpdatePacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pokemon.update.FriendshipUpdatePacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pokemon.update.GenderUpdatePacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pokemon.update.GmaxFactorUpdatePacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pokemon.update.HealthUpdatePacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pokemon.update.HeldItemUpdatePacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pokemon.update.IVsUpdatePacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pokemon.update.MoveSetUpdatePacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pokemon.update.NatureUpdatePacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pokemon.update.NicknameUpdatePacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pokemon.update.OriginalTrainerUpdatePacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pokemon.update.PokemonStateUpdatePacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pokemon.update.ShinyUpdatePacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pokemon.update.SpeciesFeatureUpdatePacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pokemon.update.SpeciesUpdatePacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pokemon.update.StatusUpdatePacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pokemon.update.TeraTypeUpdatePacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pokemon.update.TetheringUpdatePacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pokemon.update.TradeableUpdatePacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokeball.PokeBall;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.AddExperienceResult;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.EVs;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.FormData;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Gender;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.IVs;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Nature;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.OriginalTrainerType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.PokemonStats;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.RenderablePokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Species;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.activestate.ActivePokemonState;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.activestate.InactivePokemonState;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.activestate.PokemonState;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.activestate.SentOutState;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.activestate.ShoulderedState;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.CobblemonEvolutionProxy;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.progress.DamageTakenEvolutionProgress;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.progress.RecoilEvolutionProgress;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.feature.SeasonFeatureHandler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.misc.GimmighoulStashHandler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.properties.UncatchableProperty;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.status.PersistentStatus;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.status.PersistentStatusContainer;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.DistributionUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.EntityExtensionsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.ResourceLocationExtensionsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.Vec3ExtensionsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.WorldExtensionsKt;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.mojang.authlib.GameProfile;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.JsonOps;
import java.lang.invoke.LambdaMetafactory;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Predicate;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.math.MathKt;
import kotlin.random.Random;
import kotlin.ranges.ClosedRange;
import kotlin.ranges.IntRange;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import net.minecraft.ResourceLocationException;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentContents;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CactusBlock;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.FireBlock;
import net.minecraft.world.level.block.MagmaBlock;
import net.minecraft.world.level.block.SweetBerryBushBlock;
import net.minecraft.world.level.block.WitherRoseBlock;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u00c6\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\n\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u001c\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0010\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0017\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\"\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\"\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b$\n\u0002\u0018\u0002\n\u0002\b%\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u001f\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u001a\b\u0016\u0018\u0000 \u00cd\u00032\u00020\u0001:\u0002\u00cd\u0003B\b\u00a2\u0006\u0005\b\u00cc\u0003\u0010\u001aJ\u001d\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0004\u00a2\u0006\u0004\b\u0007\u0010\bJ%\u0010\u000b\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\t2\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0004\u00a2\u0006\u0004\b\u000b\u0010\fJ\u0015\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u000e\u001a\u00020\r\u00a2\u0006\u0004\b\u0010\u0010\u0011J\r\u0010\u0013\u001a\u00020\u0012\u00a2\u0006\u0004\b\u0013\u0010\u0014J\u0017\u0010\u0017\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0015H\u0014\u00a2\u0006\u0004\b\u0017\u0010\u0018J\u000f\u0010\u0019\u001a\u00020\u000fH\u0014\u00a2\u0006\u0004\b\u0019\u0010\u001aJ\u0015\u0010\u001d\u001a\u00020\u001c2\u0006\u0010\n\u001a\u00020\u001b\u00a2\u0006\u0004\b\u001d\u0010\u001eJ\r\u0010\u001f\u001a\u00020\u001c\u00a2\u0006\u0004\b\u001f\u0010 J\r\u0010!\u001a\u00020\u001c\u00a2\u0006\u0004\b!\u0010 J\r\u0010\"\u001a\u00020\u000f\u00a2\u0006\u0004\b\"\u0010\u001aJ!\u0010%\u001a\u00020\u00002\b\b\u0002\u0010#\u001a\u00020\u001c2\b\b\u0002\u0010$\u001a\u00020\u001c\u00a2\u0006\u0004\b%\u0010&J!\u0010+\u001a\u00020*2\u0012\u0010)\u001a\n\u0012\u0006\b\u0001\u0012\u00020(0'\"\u00020(\u00a2\u0006\u0004\b+\u0010,J\u001b\u0010+\u001a\u00020*2\f\u0010)\u001a\b\u0012\u0004\u0012\u00020(0-\u00a2\u0006\u0004\b+\u0010.J\u001f\u00101\u001a\u00020\u001c2\u0006\u0010/\u001a\u00020\u00042\b\b\u0002\u00100\u001a\u00020\u001c\u00a2\u0006\u0004\b1\u00102J\r\u00103\u001a\u00020\u000f\u00a2\u0006\u0004\b3\u0010\u001aJ\u001d\u00107\u001a\u00020\u001c2\u0006\u00105\u001a\u0002042\u0006\u00106\u001a\u000204\u00a2\u0006\u0004\b7\u00108J\u000f\u00109\u001a\u00020\u000fH\u0002\u00a2\u0006\u0004\b9\u0010\u001aJ\u0017\u0010<\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030;0:\u00a2\u0006\u0004\b<\u0010=J\u0013\u0010>\u001a\b\u0012\u0004\u0012\u00020\u00000;\u00a2\u0006\u0004\b>\u0010?J\r\u0010A\u001a\u00020@\u00a2\u0006\u0004\bA\u0010BJ\u0015\u0010D\u001a\u00020\u00042\u0006\u0010C\u001a\u00020\u0004\u00a2\u0006\u0004\bD\u0010EJ\r\u0010F\u001a\u00020\u0004\u00a2\u0006\u0004\bF\u0010GJ!\u0010L\u001a\u0004\u0018\u00018\u0000\"\b\b\u0000\u0010I*\u00020H2\u0006\u0010K\u001a\u00020J\u00a2\u0006\u0004\bL\u0010MJ\u000f\u0010N\u001a\u0004\u0018\u00010\t\u00a2\u0006\u0004\bN\u0010OJ\u000f\u0010Q\u001a\u0004\u0018\u00010P\u00a2\u0006\u0004\bQ\u0010RJ\u0017\u0010U\u001a\u00020\u00042\u0006\u0010T\u001a\u00020SH\u0016\u00a2\u0006\u0004\bU\u0010VJ!\u0010X\u001a\u00020\u001c2\u0012\u0010W\u001a\n\u0012\u0006\b\u0001\u0012\u00020J0'\"\u00020J\u00a2\u0006\u0004\bX\u0010YJ\r\u0010Z\u001a\u00020\u000f\u00a2\u0006\u0004\bZ\u0010\u001aJ\r\u0010\\\u001a\u00020[\u00a2\u0006\u0004\b\\\u0010]J\u000f\u0010_\u001a\u00020[H\u0000\u00a2\u0006\u0004\b^\u0010]J\u001f\u0010`\u001a\u00020\u001c2\u0006\u0010/\u001a\u00020\u00042\b\b\u0002\u00100\u001a\u00020\u001c\u00a2\u0006\u0004\b`\u00102J\r\u0010a\u001a\u00020\u0000\u00a2\u0006\u0004\ba\u0010bJ\u0017\u0010d\u001a\u00020\u000f2\b\b\u0002\u0010c\u001a\u00020\u001c\u00a2\u0006\u0004\bd\u0010eJ\r\u0010f\u001a\u00020\u001c\u00a2\u0006\u0004\bf\u0010 J\r\u0010g\u001a\u00020\u001c\u00a2\u0006\u0004\bg\u0010 J\r\u0010h\u001a\u00020\u001c\u00a2\u0006\u0004\bh\u0010 J\r\u0010i\u001a\u00020\u001c\u00a2\u0006\u0004\bi\u0010 J\r\u0010j\u001a\u00020\u001c\u00a2\u0006\u0004\bj\u0010 J\r\u0010k\u001a\u00020\u001c\u00a2\u0006\u0004\bk\u0010 J\u001d\u0010p\u001a\u00020\u001c2\u0006\u0010m\u001a\u00020l2\u0006\u0010o\u001a\u00020n\u00a2\u0006\u0004\bp\u0010qJ\u001d\u0010p\u001a\u00020\u001c2\u0006\u0010m\u001a\u00020l2\u0006\u0010s\u001a\u00020r\u00a2\u0006\u0004\bp\u0010tJ\u0015\u0010v\u001a\u00020\u001c2\u0006\u0010u\u001a\u00020\u0004\u00a2\u0006\u0004\bv\u0010wJ\r\u0010x\u001a\u00020\u001c\u00a2\u0006\u0004\bx\u0010 J\r\u0010y\u001a\u00020\u001c\u00a2\u0006\u0004\by\u0010 J\r\u0010z\u001a\u00020\u001c\u00a2\u0006\u0004\bz\u0010 J\u0015\u0010{\u001a\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b{\u0010|J\u0016\u0010\u007f\u001a\u00020\u00002\u0006\u0010~\u001a\u00020}\u00a2\u0006\u0005\b\u007f\u0010\u0080\u0001J\u001a\u0010\u0083\u0001\u001a\u00020\u00002\b\u0010\u0082\u0001\u001a\u00030\u0081\u0001\u00a2\u0006\u0006\b\u0083\u0001\u0010\u0084\u0001J\u0019\u0010\u0086\u0001\u001a\u00020\u000f2\u0007\u0010\u0085\u0001\u001a\u00020H\u00a2\u0006\u0006\b\u0086\u0001\u0010\u0087\u0001J\u001e\u0010\u008a\u0001\u001a\u00020\u000f2\f\u0010\u0089\u0001\u001a\u0007\u0012\u0002\b\u00030\u0088\u0001\u00a2\u0006\u0006\b\u008a\u0001\u0010\u008b\u0001J\u000f\u0010\u008c\u0001\u001a\u00020\u000f\u00a2\u0006\u0005\b\u008c\u0001\u0010\u001aJ\u000f\u0010\u008d\u0001\u001a\u00020\u000f\u00a2\u0006\u0005\b\u008d\u0001\u0010\u001aJN\u0010\u0092\u0001\u001a\t\u0012\u0004\u0012\u00028\u00000\u008e\u0001\"\u0004\b\u0000\u0010I2\u000e\u0010\u008f\u0001\u001a\t\u0012\u0004\u0012\u00028\u00000\u008e\u00012\u001f\b\u0002\u0010\u0091\u0001\u001a\u0018\u0012\u0004\u0012\u00028\u0000\u0012\u000b\u0012\t\u0012\u0002\b\u0003\u0018\u00010\u0088\u0001\u0018\u00010\u0090\u0001\u00a2\u0006\u0006\b\u0092\u0001\u0010\u0093\u0001J\u000f\u0010\u0094\u0001\u001a\u00020[\u00a2\u0006\u0005\b\u0094\u0001\u0010]J\u000f\u0010\u0095\u0001\u001a\u00020\u000f\u00a2\u0006\u0005\b\u0095\u0001\u0010\u001aJ\u0012\u0010\u0096\u0001\u001a\u00020\u0015H\u0016\u00a2\u0006\u0006\b\u0096\u0001\u0010\u0097\u0001J\u001c\u0010\u009a\u0001\u001a\u00020\u000f2\b\u0010\u0099\u0001\u001a\u00030\u0098\u0001H\u0002\u00a2\u0006\u0006\b\u009a\u0001\u0010\u009b\u0001J\u0018\u0010\u009c\u0001\u001a\u00020}2\u0006\u0010~\u001a\u00020}\u00a2\u0006\u0006\b\u009c\u0001\u0010\u009d\u0001J\u001b\u0010\u009e\u0001\u001a\u00030\u0081\u00012\b\u0010\u0082\u0001\u001a\u00030\u0081\u0001\u00a2\u0006\u0006\b\u009e\u0001\u0010\u009f\u0001JJ\u0010\u00a6\u0001\u001a\u0005\u0018\u00010\u00a4\u00012\u0007\u0010C\u001a\u00030\u00a0\u00012\u0007\u0010\u00a1\u0001\u001a\u00020r2\n\u0010\u00a3\u0001\u001a\u0005\u0018\u00010\u00a2\u00012\u0017\b\u0002\u0010\u00a5\u0001\u001a\u0010\u0012\u0005\u0012\u00030\u00a4\u0001\u0012\u0004\u0012\u00020\u000f0\u0090\u0001\u00a2\u0006\u0006\b\u00a6\u0001\u0010\u00a7\u0001Jq\u0010\u00ac\u0001\u001a\n\u0012\u0005\u0012\u00030\u00a4\u00010\u00ab\u00012\u0006\u0010\n\u001a\u00020\t2\u0007\u0010C\u001a\u00030\u00a0\u00012\u0007\u0010\u00a8\u0001\u001a\u00020r2\u000b\b\u0002\u0010\u00a9\u0001\u001a\u0004\u0018\u00010P2\t\b\u0002\u0010\u00aa\u0001\u001a\u00020\u001c2\f\b\u0002\u0010\u00a3\u0001\u001a\u0005\u0018\u00010\u00a2\u00012\u0017\b\u0002\u0010\u00a5\u0001\u001a\u0010\u0012\u0005\u0012\u00030\u00a4\u0001\u0012\u0004\u0012\u00020\u000f0\u0090\u0001\u00a2\u0006\u0006\b\u00ac\u0001\u0010\u00ad\u0001Jr\u0010\u00af\u0001\u001a\n\u0012\u0005\u0012\u00030\u00a4\u00010\u00ab\u00012\u0007\u0010\u0003\u001a\u00030\u00ae\u00012\u0007\u0010C\u001a\u00030\u00a0\u00012\u0007\u0010\u00a1\u0001\u001a\u00020r2\u000b\b\u0002\u0010\u00a9\u0001\u001a\u0004\u0018\u00010P2\t\b\u0002\u0010\u00aa\u0001\u001a\u00020\u001c2\f\b\u0002\u0010\u00a3\u0001\u001a\u0005\u0018\u00010\u00a2\u00012\u0017\b\u0002\u0010\u00a5\u0001\u001a\u0010\u0012\u0005\u0012\u00030\u00a4\u0001\u0012\u0004\u0012\u00020\u000f0\u0090\u0001\u00a2\u0006\u0006\b\u00af\u0001\u0010\u00b0\u0001J \u0010\u00b1\u0001\u001a\u00020\u000f2\u0006\u0010T\u001a\u00020S2\u0006\u0010u\u001a\u00020\u0004\u00a2\u0006\u0006\b\u00b1\u0001\u0010\u00b2\u0001J\u0018\u0010\u00b3\u0001\u001a\u00020\u000f2\u0006\u0010\u0005\u001a\u00020\u0004\u00a2\u0006\u0006\b\u00b3\u0001\u0010\u00b4\u0001J!\u0010\u00b5\u0001\u001a\u00020\u001c2\u0006\u0010u\u001a\u00020\u00042\b\b\u0002\u00100\u001a\u00020\u001c\u00a2\u0006\u0005\b\u00b5\u0001\u00102J \u0010\u00b6\u0001\u001a\u00020\u000f2\u0006\u0010T\u001a\u00020S2\u0006\u0010u\u001a\u00020\u0004\u00a2\u0006\u0006\b\u00b6\u0001\u0010\u00b2\u0001J\u0019\u0010\u00b8\u0001\u001a\u00020\u000f2\u0007\u0010\u00b7\u0001\u001a\u00020P\u00a2\u0006\u0006\b\u00b8\u0001\u0010\u00b9\u0001J\u0019\u0010\u00b8\u0001\u001a\u00020\u000f2\u0007\u0010\u00ba\u0001\u001a\u00020J\u00a2\u0006\u0006\b\u00b8\u0001\u0010\u00bb\u0001J\u0012\u0010\u00bc\u0001\u001a\u00020JH\u0016\u00a2\u0006\u0006\b\u00bc\u0001\u0010\u00bd\u0001J$\u0010\u00c0\u0001\u001a\u00020[2\u0007\u0010\u00be\u0001\u001a\u00020[2\t\b\u0002\u0010\u00bf\u0001\u001a\u00020\u001c\u00a2\u0006\u0006\b\u00c0\u0001\u0010\u00c1\u0001J\u000f\u0010\u00c2\u0001\u001a\u00020\u000f\u00a2\u0006\u0005\b\u00c2\u0001\u0010\u001aJ\u0019\u0010\u00c3\u0001\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0015H\u0016\u00a2\u0006\u0005\b\u00c3\u0001\u0010\u0018J\u000f\u0010\u00c4\u0001\u001a\u00020\u000f\u00a2\u0006\u0005\b\u00c4\u0001\u0010\u001aJ\u000f\u0010\u00c5\u0001\u001a\u00020\u000f\u00a2\u0006\u0005\b\u00c5\u0001\u0010\u001aJ\u001c\u0010\u00c8\u0001\u001a\u00020\u000f2\b\u0010\u00c7\u0001\u001a\u00030\u00c6\u0001H\u0002\u00a2\u0006\u0006\b\u00c8\u0001\u0010\u00c9\u0001J\u001a\u0010\u00cc\u0001\u001a\u00020\u000f2\b\u0010\u00cb\u0001\u001a\u00030\u00ca\u0001\u00a2\u0006\u0006\b\u00cc\u0001\u0010\u00cd\u0001R\u001e\u0010\u00ce\u0001\u001a\t\u0012\u0004\u0012\u00020\u00150\u008e\u00018\u0002X\u0082\u0004\u00a2\u0006\b\n\u0006\b\u00ce\u0001\u0010\u00cf\u0001R%\u0010\u00d1\u0001\u001a\u0010\u0012\u000b\u0012\t\u0012\u0004\u0012\u00020J0\u00d0\u00010\u008e\u00018\u0002X\u0082\u0004\u00a2\u0006\b\n\u0006\b\u00d1\u0001\u0010\u00cf\u0001R\u001f\u0010\u00d3\u0001\u001a\n\u0012\u0005\u0012\u00030\u00d2\u00010\u008e\u00018\u0002X\u0082\u0004\u00a2\u0006\b\n\u0006\b\u00d3\u0001\u0010\u00cf\u0001R\u001f\u0010\u00d5\u0001\u001a\n\u0012\u0005\u0012\u00030\u00d4\u00010\u008e\u00018\u0002X\u0082\u0004\u00a2\u0006\b\n\u0006\b\u00d5\u0001\u0010\u00cf\u0001R\u001e\u0010\u00d6\u0001\u001a\t\u0012\u0004\u0012\u00020\u00040\u008e\u00018\u0002X\u0082\u0004\u00a2\u0006\b\n\u0006\b\u00d6\u0001\u0010\u00cf\u0001R\u001e\u0010\u00d7\u0001\u001a\t\u0012\u0004\u0012\u00020\u00040\u008e\u00018\u0002X\u0082\u0004\u00a2\u0006\b\n\u0006\b\u00d7\u0001\u0010\u00cf\u0001R\u001f\u0010\u00d9\u0001\u001a\n\u0012\u0005\u0012\u00030\u00d8\u00010\u008e\u00018\u0002X\u0082\u0004\u00a2\u0006\b\n\u0006\b\u00d9\u0001\u0010\u00cf\u0001R\u001e\u0010\u00da\u0001\u001a\t\u0012\u0004\u0012\u00020\u00040\u008e\u00018\u0002X\u0082\u0004\u00a2\u0006\b\n\u0006\b\u00da\u0001\u0010\u00cf\u0001R\u001e\u0010\u00db\u0001\u001a\t\u0012\u0004\u0012\u00020H0\u008e\u00018\u0002X\u0082\u0004\u00a2\u0006\b\n\u0006\b\u00db\u0001\u0010\u00cf\u0001R\u001f\u0010\u00dc\u0001\u001a\n\u0012\u0005\u0012\u00030\u0098\u00010\u008e\u00018\u0002X\u0082\u0004\u00a2\u0006\b\n\u0006\b\u00dc\u0001\u0010\u00cf\u0001R\u001e\u0010\u00dd\u0001\u001a\t\u0012\u0004\u0012\u00020\u00040\u008e\u00018\u0002X\u0082\u0004\u00a2\u0006\b\n\u0006\b\u00dd\u0001\u0010\u00cf\u0001R\u001f\u0010\u00df\u0001\u001a\n\u0012\u0005\u0012\u00030\u00de\u00010\u008e\u00018\u0002X\u0082\u0004\u00a2\u0006\b\n\u0006\b\u00df\u0001\u0010\u00cf\u0001R\u001e\u0010\u00e0\u0001\u001a\t\u0012\u0004\u0012\u00020\u001c0\u008e\u00018\u0002X\u0082\u0004\u00a2\u0006\b\n\u0006\b\u00e0\u0001\u0010\u00cf\u0001R\u001e\u0010\u00e1\u0001\u001a\t\u0012\u0004\u0012\u00020[0\u008e\u00018\u0002X\u0082\u0004\u00a2\u0006\b\n\u0006\b\u00e1\u0001\u0010\u00cf\u0001R\u001f\u0010\u00e2\u0001\u001a\n\u0012\u0005\u0012\u00030\u00d8\u00010\u008e\u00018\u0002X\u0082\u0004\u00a2\u0006\b\n\u0006\b\u00e2\u0001\u0010\u00cf\u0001R!\u0010\u00e4\u0001\u001a\f\u0012\u0007\u0012\u0005\u0018\u00010\u00e3\u00010\u008e\u00018\u0002X\u0082\u0004\u00a2\u0006\b\n\u0006\b\u00e4\u0001\u0010\u00cf\u0001R\u001f\u0010\u00e6\u0001\u001a\n\u0012\u0005\u0012\u00030\u00e5\u00010\u008e\u00018\u0002X\u0082\u0004\u00a2\u0006\b\n\u0006\b\u00e6\u0001\u0010\u00cf\u0001R\u001f\u0010\u00e7\u0001\u001a\n\u0012\u0005\u0012\u00030\u00e3\u00010\u008e\u00018\u0002X\u0082\u0004\u00a2\u0006\b\n\u0006\b\u00e7\u0001\u0010\u00cf\u0001R \u0010\u00e8\u0001\u001a\u000b\u0012\u0006\u0012\u0004\u0018\u00010@0\u008e\u00018\u0002X\u0082\u0004\u00a2\u0006\b\n\u0006\b\u00e8\u0001\u0010\u00cf\u0001R \u0010\u00e9\u0001\u001a\u000b\u0012\u0006\u0012\u0004\u0018\u00010J0\u008e\u00018\u0002X\u0082\u0004\u00a2\u0006\b\n\u0006\b\u00e9\u0001\u0010\u00cf\u0001R\u001e\u0010\u00ea\u0001\u001a\t\u0012\u0004\u0012\u00020\u001c0\u008e\u00018\u0002X\u0082\u0004\u00a2\u0006\b\n\u0006\b\u00ea\u0001\u0010\u00cf\u0001R\u001f\u0010\u00ec\u0001\u001a\n\u0012\u0005\u0012\u00030\u00eb\u00010\u008e\u00018\u0002X\u0082\u0004\u00a2\u0006\b\n\u0006\b\u00ec\u0001\u0010\u00cf\u0001R\u001f\u0010\u00ee\u0001\u001a\n\u0012\u0005\u0012\u00030\u00ed\u00010\u008e\u00018\u0002X\u0082\u0004\u00a2\u0006\b\n\u0006\b\u00ee\u0001\u0010\u00cf\u0001R \u0010\u00ef\u0001\u001a\u000b\u0012\u0006\u0012\u0004\u0018\u00010\r0\u008e\u00018\u0002X\u0082\u0004\u00a2\u0006\b\n\u0006\b\u00ef\u0001\u0010\u00cf\u0001R\u001f\u0010\u00f1\u0001\u001a\n\u0012\u0005\u0012\u00030\u00f0\u00010\u008e\u00018\u0002X\u0082\u0004\u00a2\u0006\b\n\u0006\b\u00f1\u0001\u0010\u00cf\u0001R \u0010\u00f2\u0001\u001a\u000b\u0012\u0006\u0012\u0004\u0018\u00010P0\u008e\u00018\u0002X\u0082\u0004\u00a2\u0006\b\n\u0006\b\u00f2\u0001\u0010\u00cf\u0001R\u001e\u0010\u00f3\u0001\u001a\t\u0012\u0004\u0012\u00020\u001c0\u008e\u00018\u0002X\u0082\u0004\u00a2\u0006\b\n\u0006\b\u00f3\u0001\u0010\u00cf\u0001R/\u0010\u0016\u001a\u00020\u00152\u0006\u0010u\u001a\u00020\u00158\u0006@@X\u0086\u000e\u00a2\u0006\u0017\n\u0005\b\u0016\u0010\u00f4\u0001\u001a\u0006\b\u00f5\u0001\u0010\u0097\u0001\"\u0006\b\u00f6\u0001\u0010\u00f7\u0001R\u001b\u0010\u00fa\u0001\u001a\t\u0012\u0004\u0012\u0002040\u00d0\u00018F\u00a2\u0006\b\u001a\u0006\b\u00f8\u0001\u0010\u00f9\u0001R#\u0010\u00fb\u0001\u001a\t\u0012\u0004\u0012\u00020\u00000\u008e\u00018\u0006\u00a2\u0006\u0010\n\u0006\b\u00fb\u0001\u0010\u00cf\u0001\u001a\u0006\b\u00fc\u0001\u0010\u00fd\u0001R?\u0010\u00fe\u0001\u001a\t\u0012\u0004\u0012\u00020J0\u00d0\u00012\r\u0010u\u001a\t\u0012\u0004\u0012\u00020J0\u00d0\u00018\u0006@FX\u0086\u000e\u00a2\u0006\u0018\n\u0006\b\u00fe\u0001\u0010\u00ff\u0001\u001a\u0006\b\u0080\u0002\u0010\u00f9\u0001\"\u0006\b\u0081\u0002\u0010\u0082\u0002R\u0013\u0010\u0084\u0002\u001a\u00020\u00048F\u00a2\u0006\u0007\u001a\u0005\b\u0083\u0002\u0010GR\u001d\u0010\u0085\u0002\u001a\u00030\u00d2\u00018\u0006\u00a2\u0006\u0010\n\u0006\b\u0085\u0002\u0010\u0086\u0002\u001a\u0006\b\u0087\u0002\u0010\u0088\u0002R3\u0010\u0089\u0002\u001a\u00030\u00d4\u00012\u0007\u0010u\u001a\u00030\u00d4\u00018\u0006@FX\u0086\u000e\u00a2\u0006\u0018\n\u0006\b\u0089\u0002\u0010\u008a\u0002\u001a\u0006\b\u008b\u0002\u0010\u008c\u0002\"\u0006\b\u008d\u0002\u0010\u008e\u0002R0\u0010\u008f\u0002\u001a\u00020\u00042\u0006\u0010u\u001a\u00020\u00048\u0006@FX\u0086\u000e\u00a2\u0006\u0017\n\u0006\b\u008f\u0002\u0010\u0090\u0002\u001a\u0005\b\u0091\u0002\u0010G\"\u0006\b\u0092\u0002\u0010\u00b4\u0001R#\u0010\u0094\u0002\u001a\t\u0012\u0005\u0012\u00030\u0093\u00020-8\u0006\u00a2\u0006\u0010\n\u0006\b\u0094\u0002\u0010\u0095\u0002\u001a\u0006\b\u0096\u0002\u0010\u0097\u0002R\u0013\u0010\u0099\u0002\u001a\u00020\u00048F\u00a2\u0006\u0007\u001a\u0005\b\u0098\u0002\u0010GR0\u0010\u009a\u0002\u001a\u00020\u00042\u0006\u0010u\u001a\u00020\u00048\u0006@FX\u0086\u000e\u00a2\u0006\u0017\n\u0006\b\u009a\u0002\u0010\u0090\u0002\u001a\u0005\b\u009b\u0002\u0010G\"\u0006\b\u009c\u0002\u0010\u00b4\u0001R\u0015\u0010\u009f\u0002\u001a\u00030\u00e3\u00018F\u00a2\u0006\b\u001a\u0006\b\u009d\u0002\u0010\u009e\u0002R\u0017\u0010\u00a2\u0002\u001a\u0005\u0018\u00010\u00a4\u00018F\u00a2\u0006\b\u001a\u0006\b\u00a0\u0002\u0010\u00a1\u0002R/\u0010\u00aa\u0002\u001a\u0011\u0012\u0005\u0012\u00030\u00a4\u0002\u0012\u0005\u0012\u00030\u00a5\u00020\u00a3\u00028FX\u0086\u0084\u0002\u00a2\u0006\u0010\n\u0006\b\u00a6\u0002\u0010\u00a7\u0002\u001a\u0006\b\u00a8\u0002\u0010\u00a9\u0002R\u001a\u0010\u00ac\u0002\u001a\t\u0012\u0005\u0012\u00030\u00a5\u00020:8F\u00a2\u0006\u0007\u001a\u0005\b\u00ab\u0002\u0010=R\u001d\u0010\u00ae\u0002\u001a\u00030\u00ad\u00028\u0006\u00a2\u0006\u0010\n\u0006\b\u00ae\u0002\u0010\u00af\u0002\u001a\u0006\b\u00b0\u0002\u0010\u00b1\u0002R0\u0010\u00b2\u0002\u001a\u00020\u00042\u0006\u0010u\u001a\u00020\u00048\u0006@@X\u0086\u000e\u00a2\u0006\u0017\n\u0006\b\u00b2\u0002\u0010\u0090\u0002\u001a\u0005\b\u00b3\u0002\u0010G\"\u0006\b\u00b4\u0002\u0010\u00b4\u0001R\u0015\u0010\u00b8\u0002\u001a\u00030\u00b5\u00028F\u00a2\u0006\b\u001a\u0006\b\u00b6\u0002\u0010\u00b7\u0002R0\u0010\u00b9\u0002\u001a\u00020\u00042\u0006\u0010u\u001a\u00020\u00048\u0006@FX\u0086\u000e\u00a2\u0006\u0017\n\u0006\b\u00b9\u0002\u0010\u0090\u0002\u001a\u0005\b\u00ba\u0002\u0010G\"\u0006\b\u00bb\u0002\u0010\u00b4\u0001R/\u0010\u00bc\u0002\u001a\b\u0012\u0004\u0012\u00020H0-8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0018\n\u0006\b\u00bc\u0002\u0010\u0095\u0002\u001a\u0006\b\u00bd\u0002\u0010\u0097\u0002\"\u0006\b\u00be\u0002\u0010\u00bf\u0002R3\u0010\u00c0\u0002\u001a\u00030\u0098\u00012\u0007\u0010u\u001a\u00030\u0098\u00018\u0006@FX\u0086\u000e\u00a2\u0006\u0018\n\u0006\b\u00c0\u0002\u0010\u00c1\u0002\u001a\u0006\b\u00c2\u0002\u0010\u00c3\u0002\"\u0006\b\u00c4\u0002\u0010\u009b\u0001R0\u0010\u00c5\u0002\u001a\u00020\u00042\u0006\u0010u\u001a\u00020\u00048\u0006@BX\u0086\u000e\u00a2\u0006\u0017\n\u0006\b\u00c5\u0002\u0010\u0090\u0002\u001a\u0005\b\u00c6\u0002\u0010G\"\u0006\b\u00b5\u0001\u0010\u00b4\u0001R3\u0010\u00c7\u0002\u001a\u00030\u00de\u00012\u0007\u0010u\u001a\u00030\u00de\u00018\u0006@FX\u0086\u000e\u00a2\u0006\u0018\n\u0006\b\u00c7\u0002\u0010\u00c8\u0002\u001a\u0006\b\u00c9\u0002\u0010\u00ca\u0002\"\u0006\b\u00cb\u0002\u0010\u00cc\u0002R/\u0010\u00cd\u0002\u001a\u00020\u001c2\u0006\u0010u\u001a\u00020\u001c8\u0006@FX\u0086\u000e\u00a2\u0006\u0016\n\u0006\b\u00cd\u0002\u0010\u00ce\u0002\u001a\u0005\b\u00cf\u0002\u0010 \"\u0005\b\u00d0\u0002\u0010eR0\u0010\u00d1\u0002\u001a\u00020\u00042\u0006\u0010u\u001a\u00020\u00048\u0006@FX\u0086\u000e\u00a2\u0006\u0017\n\u0006\b\u00d1\u0002\u0010\u0090\u0002\u001a\u0005\b\u00d2\u0002\u0010G\"\u0006\b\u00d3\u0002\u0010\u00b4\u0001R\u0017\u0010\\\u001a\u00020[8\u0002@\u0002X\u0082\u000e\u00a2\u0006\u0007\n\u0005\b\\\u0010\u00d4\u0002R\u0013\u0010\u00d6\u0002\u001a\u00020\u00048F\u00a2\u0006\u0007\u001a\u0005\b\u00d5\u0002\u0010GR'\u0010\u00d7\u0002\u001a\u00020\u001c8\u0000@\u0000X\u0080\u000e\u00a2\u0006\u0016\n\u0006\b\u00d7\u0002\u0010\u00ce\u0002\u001a\u0005\b\u00d8\u0002\u0010 \"\u0005\b\u00d9\u0002\u0010eR\u001d\u0010\u00db\u0002\u001a\u00030\u00da\u00028\u0006\u00a2\u0006\u0010\n\u0006\b\u00db\u0002\u0010\u00dc\u0002\u001a\u0006\b\u00dd\u0002\u0010\u00de\u0002R(\u0010\u00df\u0002\u001a\u00020[8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0017\n\u0006\b\u00df\u0002\u0010\u00d4\u0002\u001a\u0005\b\u00e0\u0002\u0010]\"\u0006\b\u00e1\u0002\u0010\u00e2\u0002R.\u0010C\u001a\u00020\u00042\u0006\u0010u\u001a\u00020\u00048\u0006@FX\u0086\u000e\u00a2\u0006\u0016\n\u0005\bC\u0010\u0090\u0002\u001a\u0005\b\u00e3\u0002\u0010G\"\u0006\b\u00e4\u0002\u0010\u00b4\u0001R\u001a\u0010\u00e6\u0002\u001a\t\u0012\u0005\u0012\u00030\u00a5\u00020:8F\u00a2\u0006\u0007\u001a\u0005\b\u00e5\u0002\u0010=R7\u0010\u00e7\u0002\u001a\u0005\u0018\u00010\u00e3\u00012\t\u0010u\u001a\u0005\u0018\u00010\u00e3\u00018\u0006@FX\u0086\u000e\u00a2\u0006\u0018\n\u0006\b\u00e7\u0002\u0010\u00e8\u0002\u001a\u0006\b\u00e9\u0002\u0010\u009e\u0002\"\u0006\b\u00ea\u0002\u0010\u00eb\u0002R\u001d\u0010\u00ec\u0002\u001a\u00030\u00e5\u00018\u0006\u00a2\u0006\u0010\n\u0006\b\u00ec\u0002\u0010\u00ed\u0002\u001a\u0006\b\u00ee\u0002\u0010\u00ef\u0002R3\u0010\u00f0\u0002\u001a\u00030\u00e3\u00012\u0007\u0010u\u001a\u00030\u00e3\u00018\u0006@FX\u0086\u000e\u00a2\u0006\u0018\n\u0006\b\u00f0\u0002\u0010\u00e8\u0002\u001a\u0006\b\u00f1\u0002\u0010\u009e\u0002\"\u0006\b\u00f2\u0002\u0010\u00eb\u0002R4\u0010\u00f3\u0002\u001a\u0004\u0018\u00010@2\b\u0010u\u001a\u0004\u0018\u00010@8\u0006@FX\u0086\u000e\u00a2\u0006\u0017\n\u0006\b\u00f3\u0002\u0010\u00f4\u0002\u001a\u0005\b\u00f5\u0002\u0010B\"\u0006\b\u00f6\u0002\u0010\u00f7\u0002R!\u0010\u00f8\u0002\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030;0-8\u0002X\u0082\u0004\u00a2\u0006\b\n\u0006\b\u00f8\u0002\u0010\u0095\u0002R.\u0010\u00fa\u0002\u001a\u0004\u0018\u00010J2\t\u0010\u00f9\u0002\u001a\u0004\u0018\u00010J8\u0006@BX\u0086\u000e\u00a2\u0006\u0010\n\u0006\b\u00fa\u0002\u0010\u00fb\u0002\u001a\u0006\b\u00fc\u0002\u0010\u00bd\u0001R5\u0010\u00fd\u0002\u001a\u0004\u0018\u00010J2\b\u0010u\u001a\u0004\u0018\u00010J8\u0006@FX\u0086\u000e\u00a2\u0006\u0018\n\u0006\b\u00fd\u0002\u0010\u00fb\u0002\u001a\u0006\b\u00fe\u0002\u0010\u00bd\u0001\"\u0006\b\u00ff\u0002\u0010\u00bb\u0001R,\u0010\u0081\u0003\u001a\u00030\u0080\u00032\b\u0010\u00f9\u0002\u001a\u00030\u0080\u00038\u0006@BX\u0086\u000e\u00a2\u0006\u0010\n\u0006\b\u0081\u0003\u0010\u0082\u0003\u001a\u0006\b\u0083\u0003\u0010\u0084\u0003R,\u0010\u0085\u0003\u001a\u00030\u0081\u00012\b\u0010\u00f9\u0002\u001a\u00030\u0081\u00018\u0006@BX\u0086\u000e\u00a2\u0006\u0010\n\u0006\b\u0085\u0003\u0010\u0086\u0003\u001a\u0006\b\u0087\u0003\u0010\u0088\u0003R\u0017\u0010\u008c\u0003\u001a\u0005\u0018\u00010\u0089\u00038F\u00a2\u0006\b\u001a\u0006\b\u008a\u0003\u0010\u008b\u0003R\u0015\u0010\u0090\u0003\u001a\u00030\u008d\u00038F\u00a2\u0006\b\u001a\u0006\b\u008e\u0003\u0010\u008f\u0003R*\u0010\u0091\u0003\u001a\u00030\u00c6\u00018\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0018\n\u0006\b\u0091\u0003\u0010\u0092\u0003\u001a\u0006\b\u0093\u0003\u0010\u0094\u0003\"\u0006\b\u0095\u0003\u0010\u00c9\u0001R\u0017\u0010\u0097\u0003\u001a\u0005\u0018\u00010\u008d\u00038F\u00a2\u0006\b\u001a\u0006\b\u0096\u0003\u0010\u008f\u0003R/\u0010\u0098\u0003\u001a\u00020\u001c2\u0006\u0010u\u001a\u00020\u001c8\u0006@FX\u0086\u000e\u00a2\u0006\u0016\n\u0006\b\u0098\u0003\u0010\u00ce\u0002\u001a\u0005\b\u0099\u0003\u0010 \"\u0005\b\u009a\u0003\u0010eR\u0013\u0010\u009c\u0003\u001a\u00020\u00048F\u00a2\u0006\u0007\u001a\u0005\b\u009b\u0003\u0010GR\u0013\u0010\u009e\u0003\u001a\u00020\u00048F\u00a2\u0006\u0007\u001a\u0005\b\u009d\u0003\u0010GR3\u0010\u009f\u0003\u001a\u00030\u00eb\u00012\u0007\u0010u\u001a\u00030\u00eb\u00018\u0006@FX\u0086\u000e\u00a2\u0006\u0018\n\u0006\b\u009f\u0003\u0010\u00a0\u0003\u001a\u0006\b\u00a1\u0003\u0010\u00a2\u0003\"\u0006\b\u00a3\u0003\u0010\u00a4\u0003R\u0013\u0010\u00a6\u0003\u001a\u00020\u00048F\u00a2\u0006\u0007\u001a\u0005\b\u00a5\u0003\u0010GR3\u0010\u00a7\u0003\u001a\u00030\u00ed\u00012\u0007\u0010u\u001a\u00030\u00ed\u00018\u0006@FX\u0086\u000e\u00a2\u0006\u0018\n\u0006\b\u00a7\u0003\u0010\u00a8\u0003\u001a\u0006\b\u00a9\u0003\u0010\u00aa\u0003\"\u0006\b\u00ab\u0003\u0010\u00ac\u0003R5\u0010\u000e\u001a\u0005\u0018\u00010\u00ad\u00032\t\u0010u\u001a\u0005\u0018\u00010\u00ad\u00038\u0006@FX\u0086\u000e\u00a2\u0006\u0017\n\u0005\b\u000e\u0010\u00ae\u0003\u001a\u0006\b\u00af\u0003\u0010\u00b0\u0003\"\u0006\b\u00b1\u0003\u0010\u00b2\u0003R*\u0010\u00b5\u0003\u001a\u0010\u0012\u000b\u0012\t\u0012\u0002\b\u0003\u0018\u00010\u00b4\u00030\u00b3\u00038\u0006\u00a2\u0006\u0010\n\u0006\b\u00b5\u0003\u0010\u00b6\u0003\u001a\u0006\b\u00b7\u0003\u0010\u00b8\u0003R3\u0010\u00b9\u0003\u001a\u00030\u00f0\u00012\u0007\u0010u\u001a\u00030\u00f0\u00018\u0006@FX\u0086\u000e\u00a2\u0006\u0018\n\u0006\b\u00b9\u0003\u0010\u00ba\u0003\u001a\u0006\b\u00bb\u0003\u0010\u00bc\u0003\"\u0006\b\u00bd\u0003\u0010\u00be\u0003R4\u0010\u00bf\u0003\u001a\u0004\u0018\u00010P2\b\u0010u\u001a\u0004\u0018\u00010P8\u0006@FX\u0086\u000e\u00a2\u0006\u0017\n\u0006\b\u00bf\u0003\u0010\u00c0\u0003\u001a\u0005\b\u00c1\u0003\u0010R\"\u0006\b\u00c2\u0003\u0010\u00b9\u0001R/\u0010\u00c3\u0003\u001a\u00020\u001c2\u0006\u0010u\u001a\u00020\u001c8\u0006@FX\u0086\u000e\u00a2\u0006\u0016\n\u0006\b\u00c3\u0003\u0010\u00ce\u0002\u001a\u0005\b\u00c4\u0003\u0010 \"\u0005\b\u00c5\u0003\u0010eR\u001a\u0010\u00c7\u0003\u001a\t\u0012\u0005\u0012\u00030\u008d\u00030:8F\u00a2\u0006\u0007\u001a\u0005\b\u00c6\u0003\u0010=R1\u0010\u00c9\u0003\u001a\u000b \u00c8\u0003*\u0004\u0018\u00010P0P8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0017\n\u0006\b\u00c9\u0003\u0010\u00c0\u0003\u001a\u0005\b\u00ca\u0003\u0010R\"\u0006\b\u00cb\u0003\u0010\u00b9\u0001\u00a8\u0006\u00ce\u0003"}, d2={"Lcom/cobblemon/mod/common/pokemon/Pokemon;", "Lcom/cobblemon/mod/common/api/data/ShowdownIdentifiable;", "Lcom/cobblemon/mod/common/api/pokemon/experience/ExperienceSource;", "source", "", "xp", "Lcom/cobblemon/mod/common/pokemon/AddExperienceResult;", "addExperience", "(Lcom/cobblemon/mod/common/api/pokemon/experience/ExperienceSource;I)Lcom/cobblemon/mod/common/pokemon/AddExperienceResult;", "Lnet/minecraft/server/level/ServerPlayer;", "player", "addExperienceWithPlayer", "(Lnet/minecraft/server/level/ServerPlayer;Lcom/cobblemon/mod/common/api/pokemon/experience/ExperienceSource;I)Lcom/cobblemon/mod/common/pokemon/AddExperienceResult;", "Lcom/cobblemon/mod/common/pokemon/status/PersistentStatus;", "status", "", "applyStatus", "(Lcom/cobblemon/mod/common/pokemon/status/PersistentStatus;)V", "Lcom/cobblemon/mod/common/pokemon/RenderablePokemon;", "asRenderablePokemon", "()Lcom/cobblemon/mod/common/pokemon/RenderablePokemon;", "Lcom/cobblemon/mod/common/api/abilities/Ability;", "ability", "attachAbilityCoordinate", "(Lcom/cobblemon/mod/common/api/abilities/Ability;)Lcom/cobblemon/mod/common/api/abilities/Ability;", "attemptAbilityUpdate", "()V", "Lnet/minecraft/world/entity/player/Player;", "", "belongsTo", "(Lnet/minecraft/world/entity/player/Player;)Z", "canBeHealed", "()Z", "canLevelUpFurther", "checkGender", "useJSON", "newUUID", "clone", "(ZZ)Lcom/cobblemon/mod/common/pokemon/Pokemon;", "", "Lcom/cobblemon/mod/common/api/pokemon/PokemonPropertyExtractor;", "extractors", "Lcom/cobblemon/mod/common/api/pokemon/PokemonProperties;", "createPokemonProperties", "([Lcom/cobblemon/mod/common/api/pokemon/PokemonPropertyExtractor;)Lcom/cobblemon/mod/common/api/pokemon/PokemonProperties;", "", "(Ljava/util/List;)Lcom/cobblemon/mod/common/api/pokemon/PokemonProperties;", "amount", "coerceSafe", "decrementFriendship", "(IZ)Z", "didSleep", "Lcom/cobblemon/mod/common/api/moves/MoveTemplate;", "oldMove", "newMove", "exchangeMove", "(Lcom/cobblemon/mod/common/api/moves/MoveTemplate;Lcom/cobblemon/mod/common/api/moves/MoveTemplate;)Z", "findAndLearnFormChangeMoves", "", "Lcom/cobblemon/mod/common/api/reactive/Observable;", "getAllObservables", "()Ljava/lang/Iterable;", "getChangeObservable", "()Lcom/cobblemon/mod/common/api/reactive/Observable;", "Lnet/minecraft/network/chat/MutableComponent;", "getDisplayName", "()Lnet/minecraft/network/chat/MutableComponent;", "level", "getExperienceToLevel", "(I)I", "getExperienceToNextLevel", "()I", "Lcom/cobblemon/mod/common/api/pokemon/feature/SpeciesFeature;", "T", "", "name", "getFeature", "(Ljava/lang/String;)Lcom/cobblemon/mod/common/api/pokemon/feature/SpeciesFeature;", "getOwnerPlayer", "()Lnet/minecraft/server/level/ServerPlayer;", "Ljava/util/UUID;", "getOwnerUUID", "()Ljava/util/UUID;", "Lcom/cobblemon/mod/common/api/pokemon/stats/Stat;", "stat", "getStat", "(Lcom/cobblemon/mod/common/api/pokemon/stats/Stat;)I", "labels", "hasLabels", "([Ljava/lang/String;)Z", "heal", "Lnet/minecraft/world/item/ItemStack;", "heldItem", "()Lnet/minecraft/world/item/ItemStack;", "heldItemNoCopy$common", "heldItemNoCopy", "incrementFriendship", "initialize", "()Lcom/cobblemon/mod/common/pokemon/Pokemon;", "preferLatest", "initializeMoveset", "(Z)V", "isFainted", "isFireImmune", "isFullHealth", "isLegendary", "isMythical", "isPlayerOwned", "Lnet/minecraft/world/level/Level;", "world", "Lnet/minecraft/core/BlockPos;", "pos1", "isPositionSafe", "(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;)Z", "Lnet/minecraft/world/phys/Vec3;", "pos", "(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/phys/Vec3;)Z", "value", "isPossibleFriendship", "(I)Z", "isUltraBeast", "isUncatchable", "isWild", "levelUp", "(Lcom/cobblemon/mod/common/api/pokemon/experience/ExperienceSource;)Lcom/cobblemon/mod/common/pokemon/AddExperienceResult;", "Lcom/google/gson/JsonObject;", "json", "loadFromJSON", "(Lcom/google/gson/JsonObject;)Lcom/cobblemon/mod/common/pokemon/Pokemon;", "Lnet/minecraft/nbt/CompoundTag;", "nbt", "loadFromNBT", "(Lnet/minecraft/nbt/CompoundTag;)Lcom/cobblemon/mod/common/pokemon/Pokemon;", "feature", "markFeatureDirty", "(Lcom/cobblemon/mod/common/api/pokemon/feature/SpeciesFeature;)V", "Lcom/cobblemon/mod/common/net/messages/client/PokemonUpdatePacket;", "packet", "notify", "(Lcom/cobblemon/mod/common/net/messages/client/PokemonUpdatePacket;)V", "recall", "refreshOriginalTrainer", "Lcom/cobblemon/mod/common/api/reactive/SimpleObservable;", "observable", "Lkotlin/Function1;", "notifyPacket", "registerObservable", "(Lcom/cobblemon/mod/common/api/reactive/SimpleObservable;Lkotlin/jvm/functions/Function1;)Lcom/cobblemon/mod/common/api/reactive/SimpleObservable;", "removeHeldItem", "removeOriginalTrainer", "rollAbility", "()Lcom/cobblemon/mod/common/api/abilities/Ability;", "Lcom/cobblemon/mod/common/pokemon/FormData;", "old", "sanitizeFormChangeMoves", "(Lcom/cobblemon/mod/common/pokemon/FormData;)V", "saveToJSON", "(Lcom/google/gson/JsonObject;)Lcom/google/gson/JsonObject;", "saveToNBT", "(Lnet/minecraft/nbt/CompoundTag;)Lnet/minecraft/nbt/CompoundTag;", "Lnet/minecraft/server/level/ServerLevel;", "position", "Lcom/cobblemon/mod/common/entity/pokemon/effects/IllusionEffect;", "illusion", "Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;", "mutation", "sendOut", "(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/phys/Vec3;Lcom/cobblemon/mod/common/entity/pokemon/effects/IllusionEffect;Lkotlin/jvm/functions/Function1;)Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;", "targetPosition", "battleId", "doCry", "Ljava/util/concurrent/CompletableFuture;", "sendOutFromShoulder", "(Lnet/minecraft/server/level/ServerPlayer;Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/phys/Vec3;Ljava/util/UUID;ZLcom/cobblemon/mod/common/entity/pokemon/effects/IllusionEffect;Lkotlin/jvm/functions/Function1;)Ljava/util/concurrent/CompletableFuture;", "Lnet/minecraft/world/entity/LivingEntity;", "sendOutWithAnimation", "(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/phys/Vec3;Ljava/util/UUID;ZLcom/cobblemon/mod/common/entity/pokemon/effects/IllusionEffect;Lkotlin/jvm/functions/Function1;)Ljava/util/concurrent/CompletableFuture;", "setEV", "(Lcom/cobblemon/mod/common/api/pokemon/stats/Stat;I)V", "setExperienceAndUpdateLevel", "(I)V", "setFriendship", "setIV", "playerUUID", "setOriginalTrainer", "(Ljava/util/UUID;)V", "fakeTrainerName", "(Ljava/lang/String;)V", "showdownId", "()Ljava/lang/String;", "stack", "decrement", "swapHeldItem", "(Lnet/minecraft/world/item/ItemStack;Z)Lnet/minecraft/world/item/ItemStack;", "tryRecallWithAnimation", "updateAbility", "updateAspects", "updateForm", "", "quotient", "updateHP", "(F)V", "Lcom/bedrockk/molang/runtime/struct/VariableStruct;", "struct", "writeVariables", "(Lcom/bedrockk/molang/runtime/struct/VariableStruct;)V", "_ability", "Lcom/cobblemon/mod/common/api/reactive/SimpleObservable;", "", "_aspects", "Lcom/cobblemon/mod/common/api/moves/BenchedMoves;", "_benchedMoves", "Lcom/cobblemon/mod/common/pokeball/PokeBall;", "_caughtBall", "_currentHealth", "_dmaxLevel", "Lcom/cobblemon/mod/common/pokemon/PokemonStats;", "_evs", "_experience", "_features", "_form", "_friendship", "Lcom/cobblemon/mod/common/pokemon/Gender;", "_gender", "_gmaxFactor", "_heldItem", "_ivs", "Lcom/cobblemon/mod/common/pokemon/Nature;", "_mintedNature", "Lcom/cobblemon/mod/common/api/moves/MoveSet;", "_moveSet", "_nature", "_nickname", "_originalTrainerName", "_shiny", "Lcom/cobblemon/mod/common/pokemon/Species;", "_species", "Lcom/cobblemon/mod/common/pokemon/activestate/PokemonState;", "_state", "_status", "Lcom/cobblemon/mod/common/api/types/tera/TeraType;", "_teraType", "_tetheringId", "_tradeable", "Lcom/cobblemon/mod/common/api/abilities/Ability;", "getAbility", "setAbility$common", "(Lcom/cobblemon/mod/common/api/abilities/Ability;)V", "getAllAccessibleMoves", "()Ljava/util/Set;", "allAccessibleMoves", "anyChangeObservable", "getAnyChangeObservable", "()Lcom/cobblemon/mod/common/api/reactive/SimpleObservable;", "aspects", "Ljava/util/Set;", "getAspects", "setAspects", "(Ljava/util/Set;)V", "getAttack", "attack", "benchedMoves", "Lcom/cobblemon/mod/common/api/moves/BenchedMoves;", "getBenchedMoves", "()Lcom/cobblemon/mod/common/api/moves/BenchedMoves;", "caughtBall", "Lcom/cobblemon/mod/common/pokeball/PokeBall;", "getCaughtBall", "()Lcom/cobblemon/mod/common/pokeball/PokeBall;", "setCaughtBall", "(Lcom/cobblemon/mod/common/pokeball/PokeBall;)V", "currentHealth", "I", "getCurrentHealth", "setCurrentHealth", "Lcom/cobblemon/mod/common/api/properties/CustomPokemonProperty;", "customProperties", "Ljava/util/List;", "getCustomProperties", "()Ljava/util/List;", "getDefence", "defence", "dmaxLevel", "getDmaxLevel", "setDmaxLevel", "getEffectiveNature", "()Lcom/cobblemon/mod/common/pokemon/Nature;", "effectiveNature", "getEntity", "()Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;", "entity", "Lcom/cobblemon/mod/common/api/pokemon/evolution/EvolutionProxy;", "Lcom/cobblemon/mod/common/api/pokemon/evolution/EvolutionDisplay;", "Lcom/cobblemon/mod/common/api/pokemon/evolution/Evolution;", "evolutionProxy$delegate", "Lkotlin/Lazy;", "getEvolutionProxy", "()Lcom/cobblemon/mod/common/api/pokemon/evolution/EvolutionProxy;", "evolutionProxy", "getEvolutions", "evolutions", "Lcom/cobblemon/mod/common/pokemon/EVs;", "evs", "Lcom/cobblemon/mod/common/pokemon/EVs;", "getEvs", "()Lcom/cobblemon/mod/common/pokemon/EVs;", "experience", "getExperience", "setExperience$common", "Lcom/cobblemon/mod/common/api/pokemon/experience/ExperienceGroup;", "getExperienceGroup", "()Lcom/cobblemon/mod/common/api/pokemon/experience/ExperienceGroup;", "experienceGroup", "faintedTimer", "getFaintedTimer", "setFaintedTimer", "features", "getFeatures", "setFeatures", "(Ljava/util/List;)V", "form", "Lcom/cobblemon/mod/common/pokemon/FormData;", "getForm", "()Lcom/cobblemon/mod/common/pokemon/FormData;", "setForm", "friendship", "getFriendship", "gender", "Lcom/cobblemon/mod/common/pokemon/Gender;", "getGender", "()Lcom/cobblemon/mod/common/pokemon/Gender;", "setGender", "(Lcom/cobblemon/mod/common/pokemon/Gender;)V", "gmaxFactor", "Z", "getGmaxFactor", "setGmaxFactor", "healTimer", "getHealTimer", "setHealTimer", "Lnet/minecraft/world/item/ItemStack;", "getHp", "hp", "isClient", "isClient$common", "setClient$common", "Lcom/cobblemon/mod/common/pokemon/IVs;", "ivs", "Lcom/cobblemon/mod/common/pokemon/IVs;", "getIvs", "()Lcom/cobblemon/mod/common/pokemon/IVs;", "lastFlowerFed", "getLastFlowerFed", "setLastFlowerFed", "(Lnet/minecraft/world/item/ItemStack;)V", "getLevel", "setLevel", "getLockedEvolutions", "lockedEvolutions", "mintedNature", "Lcom/cobblemon/mod/common/pokemon/Nature;", "getMintedNature", "setMintedNature", "(Lcom/cobblemon/mod/common/pokemon/Nature;)V", "moveSet", "Lcom/cobblemon/mod/common/api/moves/MoveSet;", "getMoveSet", "()Lcom/cobblemon/mod/common/api/moves/MoveSet;", "nature", "getNature", "setNature", "nickname", "Lnet/minecraft/network/chat/MutableComponent;", "getNickname", "setNickname", "(Lnet/minecraft/network/chat/MutableComponent;)V", "observables", "<set-?>", "originalTrainer", "Ljava/lang/String;", "getOriginalTrainer", "originalTrainerName", "getOriginalTrainerName", "setOriginalTrainerName", "Lcom/cobblemon/mod/common/pokemon/OriginalTrainerType;", "originalTrainerType", "Lcom/cobblemon/mod/common/pokemon/OriginalTrainerType;", "getOriginalTrainerType", "()Lcom/cobblemon/mod/common/pokemon/OriginalTrainerType;", "persistentData", "Lnet/minecraft/nbt/CompoundTag;", "getPersistentData", "()Lnet/minecraft/nbt/CompoundTag;", "Lcom/cobblemon/mod/common/api/pokemon/evolution/PreEvolution;", "getPreEvolution", "()Lcom/cobblemon/mod/common/api/pokemon/evolution/PreEvolution;", "preEvolution", "Lcom/cobblemon/mod/common/api/types/ElementalType;", "getPrimaryType", "()Lcom/cobblemon/mod/common/api/types/ElementalType;", "primaryType", "scaleModifier", "F", "getScaleModifier", "()F", "setScaleModifier", "getSecondaryType", "secondaryType", "shiny", "getShiny", "setShiny", "getSpecialAttack", "specialAttack", "getSpecialDefence", "specialDefence", "species", "Lcom/cobblemon/mod/common/pokemon/Species;", "getSpecies", "()Lcom/cobblemon/mod/common/pokemon/Species;", "setSpecies", "(Lcom/cobblemon/mod/common/pokemon/Species;)V", "getSpeed", "speed", "state", "Lcom/cobblemon/mod/common/pokemon/activestate/PokemonState;", "getState", "()Lcom/cobblemon/mod/common/pokemon/activestate/PokemonState;", "setState", "(Lcom/cobblemon/mod/common/pokemon/activestate/PokemonState;)V", "Lcom/cobblemon/mod/common/pokemon/status/PersistentStatusContainer;", "Lcom/cobblemon/mod/common/pokemon/status/PersistentStatusContainer;", "getStatus", "()Lcom/cobblemon/mod/common/pokemon/status/PersistentStatusContainer;", "setStatus", "(Lcom/cobblemon/mod/common/pokemon/status/PersistentStatusContainer;)V", "Lcom/cobblemon/mod/common/api/reactive/SettableObservable;", "Lcom/cobblemon/mod/common/api/storage/StoreCoordinates;", "storeCoordinates", "Lcom/cobblemon/mod/common/api/reactive/SettableObservable;", "getStoreCoordinates", "()Lcom/cobblemon/mod/common/api/reactive/SettableObservable;", "teraType", "Lcom/cobblemon/mod/common/api/types/tera/TeraType;", "getTeraType", "()Lcom/cobblemon/mod/common/api/types/tera/TeraType;", "setTeraType", "(Lcom/cobblemon/mod/common/api/types/tera/TeraType;)V", "tetheringId", "Ljava/util/UUID;", "getTetheringId", "setTetheringId", "tradeable", "getTradeable", "setTradeable", "getTypes", "types", "kotlin.jvm.PlatformType", "uuid", "getUuid", "setUuid", "<init>", "Companion", "common"})
@SourceDebugExtension(value={"SMAP\nPokemon.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Pokemon.kt\ncom/cobblemon/mod/common/pokemon/Pokemon\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 4 EventObservables.kt\ncom/cobblemon/mod/common/api/reactive/EventObservable\n+ 5 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n+ 6 EventObservables.kt\ncom/cobblemon/mod/common/api/reactive/CancelableObservable\n+ 7 EventObservables.kt\ncom/cobblemon/mod/common/api/reactive/CancelableObservable$postThen$1\n+ 8 EventObservables.kt\ncom/cobblemon/mod/common/api/reactive/EventObservable$post$1\n+ 9 ArraysJVM.kt\nkotlin/collections/ArraysKt__ArraysJVMKt\n*L\n1#1,1645:1\n1603#2,9:1646\n1855#2:1655\n1856#2:1657\n1612#2:1658\n766#2:1665\n857#2,2:1666\n1855#2,2:1668\n1603#2,9:1675\n1855#2:1684\n1856#2:1686\n1612#2:1687\n1855#2,2:1688\n1612#2:1690\n1045#2:1692\n766#2:1693\n857#2,2:1694\n1747#2,3:1719\n1747#2,3:1723\n1549#2:1745\n1620#2,3:1746\n1855#2,2:1749\n1855#2,2:1751\n1549#2:1753\n1620#2,3:1754\n1855#2,2:1757\n1855#2,2:1759\n1549#2:1761\n1620#2,3:1762\n1855#2,2:1765\n1549#2:1767\n1620#2,3:1768\n1360#2:1771\n1446#2,5:1772\n288#2,2:1777\n1855#2,2:1779\n766#2:1803\n857#2:1804\n2624#2,3:1805\n858#2:1808\n1855#2,2:1809\n1855#2:1816\n2624#2,3:1817\n1856#2:1820\n1#3:1656\n1#3:1659\n1#3:1685\n1#3:1691\n17#4,2:1660\n19#4:1664\n17#4,2:1670\n19#4:1674\n17#4,2:1698\n19#4:1708\n14#4,5:1710\n19#4:1718\n17#4,2:1729\n17#4,2:1736\n19#4:1740\n19#4:1743\n17#4,2:1786\n19#4:1796\n17#4,2:1798\n19#4:1802\n17#4,2:1811\n19#4:1815\n13579#5,2:1662\n13579#5,2:1672\n13579#5:1700\n13580#5:1707\n13579#5:1715\n13580#5:1717\n12541#5:1722\n12542#5:1726\n13579#5:1731\n13579#5,2:1738\n13580#5:1742\n13579#5,2:1781\n13579#5:1788\n13580#5:1795\n13579#5,2:1800\n13579#5,2:1813\n39#6,2:1696\n41#6,2:1701\n44#6,3:1704\n47#6:1709\n39#6,2:1727\n41#6,2:1732\n44#6:1735\n46#6:1741\n47#6:1744\n40#6:1785\n41#6,6:1789\n47#6:1797\n39#7:1703\n39#7:1734\n14#8:1716\n37#9,2:1783\n*S KotlinDebug\n*F\n+ 1 Pokemon.kt\ncom/cobblemon/mod/common/pokemon/Pokemon\n*L\n124#1:1646,9\n124#1:1655\n124#1:1657\n124#1:1658\n223#1:1665\n223#1:1666,2\n224#1:1668,2\n306#1:1675,9\n306#1:1684\n306#1:1686\n306#1:1687\n306#1:1688,2\n306#1:1690\n429#1:1692\n431#1:1693\n431#1:1694,2\n618#1:1719,3\n710#1:1723,3\n798#1:1745\n798#1:1746,3\n802#1:1749,2\n866#1:1751,2\n920#1:1753\n920#1:1754,3\n921#1:1757,2\n924#1:1759,2\n990#1:1761\n990#1:1762,3\n994#1:1765,2\n1194#1:1767\n1194#1:1768,3\n1202#1:1771\n1202#1:1772,5\n1343#1:1777,2\n1409#1:1779,2\n1465#1:1803\n1465#1:1804\n1465#1:1805,3\n1465#1:1808\n1466#1:1809,2\n1563#1:1816\n1564#1:1817,3\n1563#1:1820\n124#1:1656\n306#1:1685\n218#1:1660,2\n218#1:1664\n264#1:1670,2\n264#1:1674\n472#1:1698,2\n472#1:1708\n576#1:1710,5\n576#1:1718\n751#1:1729,2\n758#1:1736,2\n758#1:1740\n751#1:1743\n1445#1:1786,2\n1445#1:1796\n1457#1:1798,2\n1457#1:1802\n1472#1:1811,2\n1472#1:1815\n218#1:1662,2\n264#1:1672,2\n472#1:1700\n472#1:1707\n576#1:1715\n576#1:1717\n710#1:1722\n710#1:1726\n751#1:1731\n758#1:1738,2\n751#1:1742\n1425#1:1781,2\n1445#1:1788\n1445#1:1795\n1457#1:1800,2\n1472#1:1813,2\n472#1:1696,2\n472#1:1701,2\n472#1:1704,3\n472#1:1709\n751#1:1727,2\n751#1:1732,2\n751#1:1735\n751#1:1741\n751#1:1744\n1445#1:1785\n1445#1:1789,6\n1445#1:1797\n472#1:1703\n751#1:1734\n576#1:1716\n1435#1:1783,2\n*E\n"})
public class Pokemon
implements ShowdownIdentifiable {
    @NotNull
    public static final Companion Companion = new Companion(null);
    private UUID uuid = UUID.randomUUID();
    @NotNull
    private Species species = PokemonSpecies.INSTANCE.random();
    @NotNull
    private FormData form = this.species.getStandardForm();
    @NotNull
    private final IVs ivs = IVs.Companion.createRandomIVs$default(IVs.Companion, 0, 1, null);
    @NotNull
    private final EVs evs = EVs.Companion.createEmpty();
    @Nullable
    private MutableComponent nickname;
    private int level = 1;
    private int currentHealth = this.getHp();
    @NotNull
    private Gender gender = Gender.GENDERLESS;
    @Nullable
    private PersistentStatusContainer status;
    private int experience;
    private int friendship = this.form.getBaseFriendship();
    @NotNull
    private PokemonState state = new InactivePokemonState();
    @NotNull
    private TeraType teraType = TeraTypes.forElementalType(this.getPrimaryType());
    private int dmaxLevel;
    private boolean gmaxFactor;
    private boolean shiny;
    private boolean tradeable = true;
    @NotNull
    private Nature nature = Natures.INSTANCE.getRandomNature();
    @Nullable
    private Nature mintedNature;
    @NotNull
    private final MoveSet moveSet = new MoveSet();
    private int faintedTimer = -1;
    private int healTimer = -1;
    @Nullable
    private UUID tetheringId;
    @NotNull
    private OriginalTrainerType originalTrainerType = OriginalTrainerType.NONE;
    @Nullable
    private String originalTrainer;
    @Nullable
    private String originalTrainerName;
    @NotNull
    private final BenchedMoves benchedMoves = new BenchedMoves();
    @NotNull
    private Ability ability = Abilities.INSTANCE.getDUMMY().create(false);
    private float scaleModifier = 1.0f;
    @NotNull
    private PokeBall caughtBall = PokeBalls.INSTANCE.getPOKE_BALL();
    @NotNull
    private List<SpeciesFeature> features = new ArrayList();
    @NotNull
    private Set<String> aspects = SetsKt.emptySet();
    private boolean isClient;
    @NotNull
    private final SettableObservable<StoreCoordinates<?>> storeCoordinates = new SettableObservable<Object>(null);
    @NotNull
    private final Lazy evolutionProxy$delegate = LazyKt.lazy((Function0)((Function0)new Function0<CobblemonEvolutionProxy>(this){
        final /* synthetic */ Pokemon this$0;
        {
            this.this$0 = $receiver;
            super(0);
        }

        @NotNull
        public final CobblemonEvolutionProxy invoke() {
            return new CobblemonEvolutionProxy(this.this$0, this.this$0.isClient$common());
        }
    }));
    @NotNull
    private final List<CustomPokemonProperty> customProperties = new ArrayList();
    @NotNull
    private CompoundTag persistentData = new CompoundTag();
    @NotNull
    private ItemStack heldItem;
    @NotNull
    private ItemStack lastFlowerFed;
    @NotNull
    private final List<Observable<?>> observables;
    @NotNull
    private final SimpleObservable<Pokemon> anyChangeObservable;
    @NotNull
    private final SimpleObservable<FormData> _form;
    @NotNull
    private final SimpleObservable<Species> _species;
    @NotNull
    private final SimpleObservable<MutableComponent> _nickname;
    @NotNull
    private final SimpleObservable<Integer> _experience;
    @NotNull
    private final SimpleObservable<Integer> _friendship;
    @NotNull
    private final SimpleObservable<Integer> _currentHealth;
    @NotNull
    private final SimpleObservable<Boolean> _shiny;
    @NotNull
    private final SimpleObservable<Boolean> _tradeable;
    @NotNull
    private final SimpleObservable<Nature> _nature;
    @NotNull
    private final SimpleObservable<Nature> _mintedNature;
    @NotNull
    private final SimpleObservable<MoveSet> _moveSet;
    @NotNull
    private final SimpleObservable<PokemonState> _state;
    @NotNull
    private final SimpleObservable<PersistentStatus> _status;
    @NotNull
    private final SimpleObservable<PokeBall> _caughtBall;
    @NotNull
    private final SimpleObservable<BenchedMoves> _benchedMoves;
    @NotNull
    private final SimpleObservable<PokemonStats> _ivs;
    @NotNull
    private final SimpleObservable<PokemonStats> _evs;
    @NotNull
    private final SimpleObservable<Set<String>> _aspects;
    @NotNull
    private final SimpleObservable<Gender> _gender;
    @NotNull
    private final SimpleObservable<Ability> _ability;
    @NotNull
    private final SimpleObservable<ItemStack> _heldItem;
    @NotNull
    private final SimpleObservable<UUID> _tetheringId;
    @NotNull
    private final SimpleObservable<TeraType> _teraType;
    @NotNull
    private final SimpleObservable<Integer> _dmaxLevel;
    @NotNull
    private final SimpleObservable<Boolean> _gmaxFactor;
    @NotNull
    private final SimpleObservable<String> _originalTrainerName;
    @NotNull
    private final SimpleObservable<SpeciesFeature> _features;
    @NotNull
    private static FriendshipMutationCalculator LEVEL_UP_FRIENDSHIP_CALCULATOR = FriendshipMutationCalculator.Companion.getSWORD_AND_SHIELD_LEVEL_UP();
    @NotNull
    private static final ResourceLocation SHEDINJA = MiscUtilsKt.cobblemonResource("shedinja");

    public Pokemon() {
        ItemStack itemStack = ItemStack.f_41583_;
        Intrinsics.checkNotNullExpressionValue((Object)itemStack, (String)"EMPTY");
        this.heldItem = itemStack;
        Observable.DefaultImpls.subscribe$default(this.storeCoordinates, null, new Function1<StoreCoordinates<?>, Unit>(){

            public final void invoke(@Nullable StoreCoordinates<?> it) {
                if (it != null && !(it.getStore() instanceof PCStore) && this.getTetheringId() != null) {
                    SchedulingFunctionsKt.afterOnServer$default(1, 0.0f, (Function0)new Function0<Unit>(){

                        public final void invoke() {
                            this.setTetheringId(null);
                        }
                    }, 2, null);
                }
            }
        }, 1, null);
        ItemStack itemStack2 = ItemStack.f_41583_;
        Intrinsics.checkNotNullExpressionValue((Object)itemStack2, (String)"EMPTY");
        this.lastFlowerFed = itemStack2;
        this.observables = new ArrayList();
        this.anyChangeObservable = new SimpleObservable();
        this._form = this.registerObservable(new SimpleObservable(), (Function1)new Function1<FormData, PokemonUpdatePacket<?>>(this){
            final /* synthetic */ Pokemon this$0;
            {
                this.this$0 = $receiver;
                super(1);
            }

            @Nullable
            public final PokemonUpdatePacket<?> invoke(@NotNull FormData it) {
                Intrinsics.checkNotNullParameter((Object)it, (String)"it");
                return new FormUpdatePacket((Function0<? extends Pokemon>)((Function0)new Function0<Pokemon>(this.this$0){
                    final /* synthetic */ Pokemon this$0;
                    {
                        this.this$0 = $receiver;
                        super(0);
                    }

                    @NotNull
                    public final Pokemon invoke() {
                        return this.this$0;
                    }
                }), it);
            }
        });
        this._species = this.registerObservable(new SimpleObservable(), (Function1)new Function1<Species, PokemonUpdatePacket<?>>(this){
            final /* synthetic */ Pokemon this$0;
            {
                this.this$0 = $receiver;
                super(1);
            }

            @Nullable
            public final PokemonUpdatePacket<?> invoke(@NotNull Species it) {
                Intrinsics.checkNotNullParameter((Object)it, (String)"it");
                return new SpeciesUpdatePacket((Function0<? extends Pokemon>)((Function0)new Function0<Pokemon>(this.this$0){
                    final /* synthetic */ Pokemon this$0;
                    {
                        this.this$0 = $receiver;
                        super(0);
                    }

                    @NotNull
                    public final Pokemon invoke() {
                        return this.this$0;
                    }
                }), it);
            }
        });
        this._nickname = this.registerObservable(new SimpleObservable(), (Function1)new Function1<MutableComponent, PokemonUpdatePacket<?>>(this){
            final /* synthetic */ Pokemon this$0;
            {
                this.this$0 = $receiver;
                super(1);
            }

            @Nullable
            public final PokemonUpdatePacket<?> invoke(@Nullable MutableComponent it) {
                return new NicknameUpdatePacket((Function0<? extends Pokemon>)((Function0)new Function0<Pokemon>(this.this$0){
                    final /* synthetic */ Pokemon this$0;
                    {
                        this.this$0 = $receiver;
                        super(0);
                    }

                    @NotNull
                    public final Pokemon invoke() {
                        return this.this$0;
                    }
                }), it);
            }
        });
        this._experience = this.registerObservable(new SimpleObservable(), (Function1)new Function1<Integer, PokemonUpdatePacket<?>>(this){
            final /* synthetic */ Pokemon this$0;
            {
                this.this$0 = $receiver;
                super(1);
            }

            @Nullable
            public final PokemonUpdatePacket<?> invoke(int it) {
                return new ExperienceUpdatePacket((Function0<? extends Pokemon>)((Function0)new Function0<Pokemon>(this.this$0){
                    final /* synthetic */ Pokemon this$0;
                    {
                        this.this$0 = $receiver;
                        super(0);
                    }

                    @NotNull
                    public final Pokemon invoke() {
                        return this.this$0;
                    }
                }), it);
            }
        });
        this._friendship = this.registerObservable(new SimpleObservable(), (Function1)new Function1<Integer, PokemonUpdatePacket<?>>(this){
            final /* synthetic */ Pokemon this$0;
            {
                this.this$0 = $receiver;
                super(1);
            }

            @Nullable
            public final PokemonUpdatePacket<?> invoke(int it) {
                return new FriendshipUpdatePacket((Function0<? extends Pokemon>)((Function0)new Function0<Pokemon>(this.this$0){
                    final /* synthetic */ Pokemon this$0;
                    {
                        this.this$0 = $receiver;
                        super(0);
                    }

                    @NotNull
                    public final Pokemon invoke() {
                        return this.this$0;
                    }
                }), it);
            }
        });
        this._currentHealth = this.registerObservable(new SimpleObservable(), (Function1)new Function1<Integer, PokemonUpdatePacket<?>>(this){
            final /* synthetic */ Pokemon this$0;
            {
                this.this$0 = $receiver;
                super(1);
            }

            @Nullable
            public final PokemonUpdatePacket<?> invoke(int it) {
                return new HealthUpdatePacket((Function0<? extends Pokemon>)((Function0)new Function0<Pokemon>(this.this$0){
                    final /* synthetic */ Pokemon this$0;
                    {
                        this.this$0 = $receiver;
                        super(0);
                    }

                    @NotNull
                    public final Pokemon invoke() {
                        return this.this$0;
                    }
                }), it);
            }
        });
        this._shiny = this.registerObservable(new SimpleObservable(), (Function1)new Function1<Boolean, PokemonUpdatePacket<?>>(this){
            final /* synthetic */ Pokemon this$0;
            {
                this.this$0 = $receiver;
                super(1);
            }

            @Nullable
            public final PokemonUpdatePacket<?> invoke(boolean it) {
                return new ShinyUpdatePacket((Function0<? extends Pokemon>)((Function0)new Function0<Pokemon>(this.this$0){
                    final /* synthetic */ Pokemon this$0;
                    {
                        this.this$0 = $receiver;
                        super(0);
                    }

                    @NotNull
                    public final Pokemon invoke() {
                        return this.this$0;
                    }
                }), it);
            }
        });
        this._tradeable = this.registerObservable(new SimpleObservable(), (Function1)new Function1<Boolean, PokemonUpdatePacket<?>>(this){
            final /* synthetic */ Pokemon this$0;
            {
                this.this$0 = $receiver;
                super(1);
            }

            @Nullable
            public final PokemonUpdatePacket<?> invoke(boolean it) {
                return new TradeableUpdatePacket((Function0<? extends Pokemon>)((Function0)new Function0<Pokemon>(this.this$0){
                    final /* synthetic */ Pokemon this$0;
                    {
                        this.this$0 = $receiver;
                        super(0);
                    }

                    @NotNull
                    public final Pokemon invoke() {
                        return this.this$0;
                    }
                }), it);
            }
        });
        this._nature = this.registerObservable(new SimpleObservable(), (Function1)new Function1<Nature, PokemonUpdatePacket<?>>(this){
            final /* synthetic */ Pokemon this$0;
            {
                this.this$0 = $receiver;
                super(1);
            }

            @Nullable
            public final PokemonUpdatePacket<?> invoke(@NotNull Nature it) {
                Intrinsics.checkNotNullParameter((Object)it, (String)"it");
                return new NatureUpdatePacket((Function0<? extends Pokemon>)((Function0)new Function0<Pokemon>(this.this$0){
                    final /* synthetic */ Pokemon this$0;
                    {
                        this.this$0 = $receiver;
                        super(0);
                    }

                    @NotNull
                    public final Pokemon invoke() {
                        return this.this$0;
                    }
                }), it, false);
            }
        });
        this._mintedNature = this.registerObservable(new SimpleObservable(), (Function1)new Function1<Nature, PokemonUpdatePacket<?>>(this){
            final /* synthetic */ Pokemon this$0;
            {
                this.this$0 = $receiver;
                super(1);
            }

            @Nullable
            public final PokemonUpdatePacket<?> invoke(@Nullable Nature it) {
                return new NatureUpdatePacket((Function0<? extends Pokemon>)((Function0)new Function0<Pokemon>(this.this$0){
                    final /* synthetic */ Pokemon this$0;
                    {
                        this.this$0 = $receiver;
                        super(0);
                    }

                    @NotNull
                    public final Pokemon invoke() {
                        return this.this$0;
                    }
                }), it, true);
            }
        });
        this._moveSet = this.registerObservable(this.moveSet.getObservable(), (Function1)new Function1<MoveSet, PokemonUpdatePacket<?>>(this){
            final /* synthetic */ Pokemon this$0;
            {
                this.this$0 = $receiver;
                super(1);
            }

            @Nullable
            public final PokemonUpdatePacket<?> invoke(@NotNull MoveSet it) {
                Intrinsics.checkNotNullParameter((Object)it, (String)"it");
                return new MoveSetUpdatePacket((Function0<? extends Pokemon>)((Function0)new Function0<Pokemon>(this.this$0){
                    final /* synthetic */ Pokemon this$0;
                    {
                        this.this$0 = $receiver;
                        super(0);
                    }

                    @NotNull
                    public final Pokemon invoke() {
                        return this.this$0;
                    }
                }), this.this$0.getMoveSet());
            }
        });
        this._state = this.registerObservable(new SimpleObservable(), (Function1)new Function1<PokemonState, PokemonUpdatePacket<?>>(this){
            final /* synthetic */ Pokemon this$0;
            {
                this.this$0 = $receiver;
                super(1);
            }

            @Nullable
            public final PokemonUpdatePacket<?> invoke(@NotNull PokemonState it) {
                Intrinsics.checkNotNullParameter((Object)it, (String)"it");
                return new PokemonStateUpdatePacket((Function0<? extends Pokemon>)((Function0)new Function0<Pokemon>(this.this$0){
                    final /* synthetic */ Pokemon this$0;
                    {
                        this.this$0 = $receiver;
                        super(0);
                    }

                    @NotNull
                    public final Pokemon invoke() {
                        return this.this$0;
                    }
                }), it);
            }
        });
        this._status = this.registerObservable(new SimpleObservable(), (Function1)new Function1<PersistentStatus, PokemonUpdatePacket<?>>(this){
            final /* synthetic */ Pokemon this$0;
            {
                this.this$0 = $receiver;
                super(1);
            }

            @Nullable
            public final PokemonUpdatePacket<?> invoke(@Nullable PersistentStatus it) {
                return new StatusUpdatePacket((Function0<? extends Pokemon>)((Function0)new Function0<Pokemon>(this.this$0){
                    final /* synthetic */ Pokemon this$0;
                    {
                        this.this$0 = $receiver;
                        super(0);
                    }

                    @NotNull
                    public final Pokemon invoke() {
                        return this.this$0;
                    }
                }), it);
            }
        });
        this._caughtBall = this.registerObservable(new SimpleObservable(), (Function1)new Function1<PokeBall, PokemonUpdatePacket<?>>(this){
            final /* synthetic */ Pokemon this$0;
            {
                this.this$0 = $receiver;
                super(1);
            }

            @Nullable
            public final PokemonUpdatePacket<?> invoke(@NotNull PokeBall it) {
                Intrinsics.checkNotNullParameter((Object)it, (String)"it");
                return new CaughtBallUpdatePacket((Function0<? extends Pokemon>)((Function0)new Function0<Pokemon>(this.this$0){
                    final /* synthetic */ Pokemon this$0;
                    {
                        this.this$0 = $receiver;
                        super(0);
                    }

                    @NotNull
                    public final Pokemon invoke() {
                        return this.this$0;
                    }
                }), it);
            }
        });
        this._benchedMoves = this.registerObservable(this.benchedMoves.getObservable(), (Function1)new Function1<BenchedMoves, PokemonUpdatePacket<?>>(this){
            final /* synthetic */ Pokemon this$0;
            {
                this.this$0 = $receiver;
                super(1);
            }

            @Nullable
            public final PokemonUpdatePacket<?> invoke(@NotNull BenchedMoves it) {
                Intrinsics.checkNotNullParameter((Object)it, (String)"it");
                return new BenchedMovesUpdatePacket((Function0<? extends Pokemon>)((Function0)new Function0<Pokemon>(this.this$0){
                    final /* synthetic */ Pokemon this$0;
                    {
                        this.this$0 = $receiver;
                        super(0);
                    }

                    @NotNull
                    public final Pokemon invoke() {
                        return this.this$0;
                    }
                }), it);
            }
        });
        this._ivs = this.registerObservable(this.ivs.getObservable(), (Function1)new Function1<PokemonStats, PokemonUpdatePacket<?>>(this){
            final /* synthetic */ Pokemon this$0;
            {
                this.this$0 = $receiver;
                super(1);
            }

            @Nullable
            public final PokemonUpdatePacket<?> invoke(@NotNull PokemonStats it) {
                Intrinsics.checkNotNullParameter((Object)it, (String)"it");
                return new IVsUpdatePacket((Function0<? extends Pokemon>)((Function0)new Function0<Pokemon>(this.this$0){
                    final /* synthetic */ Pokemon this$0;
                    {
                        this.this$0 = $receiver;
                        super(0);
                    }

                    @NotNull
                    public final Pokemon invoke() {
                        return this.this$0;
                    }
                }), (IVs)it);
            }
        });
        this._evs = this.registerObservable(this.evs.getObservable(), (Function1)new Function1<PokemonStats, PokemonUpdatePacket<?>>(this){
            final /* synthetic */ Pokemon this$0;
            {
                this.this$0 = $receiver;
                super(1);
            }

            @Nullable
            public final PokemonUpdatePacket<?> invoke(@NotNull PokemonStats it) {
                Intrinsics.checkNotNullParameter((Object)it, (String)"it");
                return new EVsUpdatePacket((Function0<? extends Pokemon>)((Function0)new Function0<Pokemon>(this.this$0){
                    final /* synthetic */ Pokemon this$0;
                    {
                        this.this$0 = $receiver;
                        super(0);
                    }

                    @NotNull
                    public final Pokemon invoke() {
                        return this.this$0;
                    }
                }), (EVs)it);
            }
        });
        this._aspects = this.registerObservable(new SimpleObservable(), (Function1)new Function1<Set<? extends String>, PokemonUpdatePacket<?>>(this){
            final /* synthetic */ Pokemon this$0;
            {
                this.this$0 = $receiver;
                super(1);
            }

            @Nullable
            public final PokemonUpdatePacket<?> invoke(@NotNull Set<String> it) {
                Intrinsics.checkNotNullParameter(it, (String)"it");
                return new AspectsUpdatePacket((Function0<? extends Pokemon>)((Function0)new Function0<Pokemon>(this.this$0){
                    final /* synthetic */ Pokemon this$0;
                    {
                        this.this$0 = $receiver;
                        super(0);
                    }

                    @NotNull
                    public final Pokemon invoke() {
                        return this.this$0;
                    }
                }), it);
            }
        });
        this._gender = this.registerObservable(new SimpleObservable(), (Function1)new Function1<Gender, PokemonUpdatePacket<?>>(this){
            final /* synthetic */ Pokemon this$0;
            {
                this.this$0 = $receiver;
                super(1);
            }

            @Nullable
            public final PokemonUpdatePacket<?> invoke(@NotNull Gender it) {
                Intrinsics.checkNotNullParameter((Object)((Object)it), (String)"it");
                return new GenderUpdatePacket((Function0<? extends Pokemon>)((Function0)new Function0<Pokemon>(this.this$0){
                    final /* synthetic */ Pokemon this$0;
                    {
                        this.this$0 = $receiver;
                        super(0);
                    }

                    @NotNull
                    public final Pokemon invoke() {
                        return this.this$0;
                    }
                }), it);
            }
        });
        this._ability = this.registerObservable(new SimpleObservable(), (Function1)new Function1<Ability, PokemonUpdatePacket<?>>(this){
            final /* synthetic */ Pokemon this$0;
            {
                this.this$0 = $receiver;
                super(1);
            }

            @Nullable
            public final PokemonUpdatePacket<?> invoke(@NotNull Ability it) {
                Intrinsics.checkNotNullParameter((Object)it, (String)"it");
                return new AbilityUpdatePacket((Function0<? extends Pokemon>)((Function0)new Function0<Pokemon>(this.this$0){
                    final /* synthetic */ Pokemon this$0;
                    {
                        this.this$0 = $receiver;
                        super(0);
                    }

                    @NotNull
                    public final Pokemon invoke() {
                        return this.this$0;
                    }
                }), it.getTemplate());
            }
        });
        this._heldItem = this.registerObservable(new SimpleObservable(), (Function1)new Function1<ItemStack, PokemonUpdatePacket<?>>(this){
            final /* synthetic */ Pokemon this$0;
            {
                this.this$0 = $receiver;
                super(1);
            }

            @Nullable
            public final PokemonUpdatePacket<?> invoke(@NotNull ItemStack it) {
                Intrinsics.checkNotNullParameter((Object)it, (String)"it");
                return new HeldItemUpdatePacket((Function0<? extends Pokemon>)((Function0)new Function0<Pokemon>(this.this$0){
                    final /* synthetic */ Pokemon this$0;
                    {
                        this.this$0 = $receiver;
                        super(0);
                    }

                    @NotNull
                    public final Pokemon invoke() {
                        return this.this$0;
                    }
                }), it);
            }
        });
        this._tetheringId = this.registerObservable(new SimpleObservable(), (Function1)new Function1<UUID, PokemonUpdatePacket<?>>(this){
            final /* synthetic */ Pokemon this$0;
            {
                this.this$0 = $receiver;
                super(1);
            }

            @Nullable
            public final PokemonUpdatePacket<?> invoke(@Nullable UUID it) {
                return new TetheringUpdatePacket((Function0<? extends Pokemon>)((Function0)new Function0<Pokemon>(this.this$0){
                    final /* synthetic */ Pokemon this$0;
                    {
                        this.this$0 = $receiver;
                        super(0);
                    }

                    @NotNull
                    public final Pokemon invoke() {
                        return this.this$0;
                    }
                }), it);
            }
        });
        this._teraType = this.registerObservable(new SimpleObservable(), (Function1)new Function1<TeraType, PokemonUpdatePacket<?>>(this){
            final /* synthetic */ Pokemon this$0;
            {
                this.this$0 = $receiver;
                super(1);
            }

            @Nullable
            public final PokemonUpdatePacket<?> invoke(@NotNull TeraType it) {
                Intrinsics.checkNotNullParameter((Object)it, (String)"it");
                return new TeraTypeUpdatePacket((Function0<? extends Pokemon>)((Function0)new Function0<Pokemon>(this.this$0){
                    final /* synthetic */ Pokemon this$0;
                    {
                        this.this$0 = $receiver;
                        super(0);
                    }

                    @NotNull
                    public final Pokemon invoke() {
                        return this.this$0;
                    }
                }), it);
            }
        });
        this._dmaxLevel = this.registerObservable(new SimpleObservable(), (Function1)new Function1<Integer, PokemonUpdatePacket<?>>(this){
            final /* synthetic */ Pokemon this$0;
            {
                this.this$0 = $receiver;
                super(1);
            }

            @Nullable
            public final PokemonUpdatePacket<?> invoke(int it) {
                return new DmaxLevelUpdatePacket((Function0<? extends Pokemon>)((Function0)new Function0<Pokemon>(this.this$0){
                    final /* synthetic */ Pokemon this$0;
                    {
                        this.this$0 = $receiver;
                        super(0);
                    }

                    @NotNull
                    public final Pokemon invoke() {
                        return this.this$0;
                    }
                }), it);
            }
        });
        this._gmaxFactor = this.registerObservable(new SimpleObservable(), (Function1)new Function1<Boolean, PokemonUpdatePacket<?>>(this){
            final /* synthetic */ Pokemon this$0;
            {
                this.this$0 = $receiver;
                super(1);
            }

            @Nullable
            public final PokemonUpdatePacket<?> invoke(boolean it) {
                return new GmaxFactorUpdatePacket((Function0<? extends Pokemon>)((Function0)new Function0<Pokemon>(this.this$0){
                    final /* synthetic */ Pokemon this$0;
                    {
                        this.this$0 = $receiver;
                        super(0);
                    }

                    @NotNull
                    public final Pokemon invoke() {
                        return this.this$0;
                    }
                }), it);
            }
        });
        this._originalTrainerName = this.registerObservable(new SimpleObservable(), (Function1)new Function1<String, PokemonUpdatePacket<?>>(this){
            final /* synthetic */ Pokemon this$0;
            {
                this.this$0 = $receiver;
                super(1);
            }

            @Nullable
            public final PokemonUpdatePacket<?> invoke(@Nullable String it) {
                return new OriginalTrainerUpdatePacket((Function0<? extends Pokemon>)((Function0)new Function0<Pokemon>(this.this$0){
                    final /* synthetic */ Pokemon this$0;
                    {
                        this.this$0 = $receiver;
                        super(0);
                    }

                    @NotNull
                    public final Pokemon invoke() {
                        return this.this$0;
                    }
                }), it);
            }
        });
        this._features = this.registerObservable(new SimpleObservable(), (Function1)new Function1<SpeciesFeature, PokemonUpdatePacket<?>>(this){
            final /* synthetic */ Pokemon this$0;
            {
                this.this$0 = $receiver;
                super(1);
            }

            @Nullable
            public final PokemonUpdatePacket<?> invoke(@NotNull SpeciesFeature it) {
                Intrinsics.checkNotNullParameter((Object)it, (String)"it");
                SpeciesFeatureProvider<? extends SpeciesFeature> featureProvider = SpeciesFeatures.INSTANCE.getFeature(it.getName());
                return it instanceof SynchronizedSpeciesFeature && featureProvider instanceof SynchronizedSpeciesFeatureProvider && ((SynchronizedSpeciesFeatureProvider)featureProvider).getVisible() ? (PokemonUpdatePacket)new SpeciesFeatureUpdatePacket((Function0<? extends Pokemon>)((Function0)new Function0<Pokemon>(this.this$0){
                    final /* synthetic */ Pokemon this$0;
                    {
                        this.this$0 = $receiver;
                        super(0);
                    }

                    @NotNull
                    public final Pokemon invoke() {
                        return this.this$0;
                    }
                }), this.this$0.getSpecies().getResourceIdentifier(), (SynchronizedSpeciesFeature)it) : null;
            }
        });
    }

    public final UUID getUuid() {
        return this.uuid;
    }

    public final void setUuid(UUID uUID) {
        this.uuid = uUID;
    }

    @NotNull
    public final Species getSpecies() {
        return this.species;
    }

    /*
     * WARNING - void declaration
     */
    public final void setSpecies(@NotNull Species value2) {
        Intrinsics.checkNotNullParameter((Object)value2, (String)"value");
        if (PokemonSpecies.INSTANCE.getByIdentifier(value2.getResourceIdentifier()) == null) {
            throw new IllegalArgumentException("Cannot set a species that isn't registered");
        }
        float quotient = Mth.m_14036_((float)((float)this.currentHealth / (float)this.getHp()), (float)0.0f, (float)1.0f);
        this.species = value2;
        if (!this.isClient) {
            void $this$mapNotNullTo$iv$iv;
            Iterable $this$mapNotNull$iv = SpeciesFeatures.INSTANCE.getFeaturesFor(this.species);
            boolean $i$f$mapNotNull = false;
            Iterable iterable = $this$mapNotNull$iv;
            Collection destination$iv$iv = new ArrayList();
            boolean $i$f$mapNotNullTo = false;
            void $this$forEach$iv$iv$iv = $this$mapNotNullTo$iv$iv;
            boolean $i$f$forEach = false;
            Iterator iterator = $this$forEach$iv$iv$iv.iterator();
            while (iterator.hasNext()) {
                Object it$iv$iv;
                Object element$iv$iv$iv;
                Object element$iv$iv = element$iv$iv$iv = iterator.next();
                boolean bl = false;
                SpeciesFeatureProvider it = (SpeciesFeatureProvider)element$iv$iv;
                boolean bl2 = false;
                if (it.invoke(this) == null) continue;
                boolean bl3 = false;
                destination$iv$iv.add(it$iv$iv);
            }
            List newFeatures = (List)destination$iv$iv;
            this.features.clear();
            this.features.addAll(newFeatures);
        }
        this.getEvolutionProxy().current().clear();
        this.updateAspects();
        this.updateForm();
        this.checkGender();
        this.updateHP(quotient);
        this.attemptAbilityUpdate();
        Species[] speciesArray = new Species[]{value2};
        this._species.emit((Species[])speciesArray);
    }

    @NotNull
    public final FormData getForm() {
        return this.form;
    }

    public final void setForm(@NotNull FormData value2) {
        Intrinsics.checkNotNullParameter((Object)value2, (String)"value");
        FormData old = this.form;
        float quotient = Mth.m_14036_((float)((float)this.currentHealth / (float)this.getHp()), (float)0.0f, (float)1.0f);
        this.form = value2;
        this.sanitizeFormChangeMoves(old);
        this.getEvolutionProxy().current().clear();
        this.findAndLearnFormChangeMoves();
        this.checkGender();
        this.updateHP(quotient);
        this.attemptAbilityUpdate();
        FormData[] formDataArray = new FormData[]{value2};
        this._form.emit((FormData[])formDataArray);
    }

    @NotNull
    public final IVs getIvs() {
        return this.ivs;
    }

    @NotNull
    public final EVs getEvs() {
        return this.evs;
    }

    public final void setIV(@NotNull Stat stat, int value2) {
        Intrinsics.checkNotNullParameter((Object)stat, (String)"stat");
        float quotient = Mth.m_14036_((float)((float)this.currentHealth / (float)this.getHp()), (float)0.0f, (float)1.0f);
        this.ivs.set(stat, value2);
        if (stat == Stats.HP) {
            this.updateHP(quotient);
        }
        PokemonStats[] pokemonStatsArray = new PokemonStats[]{this.ivs};
        this._ivs.emit((PokemonStats[])pokemonStatsArray);
    }

    public final void setEV(@NotNull Stat stat, int value2) {
        Intrinsics.checkNotNullParameter((Object)stat, (String)"stat");
        float quotient = Mth.m_14036_((float)((float)this.currentHealth / (float)this.getHp()), (float)0.0f, (float)1.0f);
        this.evs.set(stat, value2);
        if (stat == Stats.HP) {
            this.updateHP(quotient);
        }
        PokemonStats[] pokemonStatsArray = new PokemonStats[]{this.evs};
        this._evs.emit((PokemonStats[])pokemonStatsArray);
    }

    @Nullable
    public final MutableComponent getNickname() {
        return this.nickname;
    }

    public final void setNickname(@Nullable MutableComponent value2) {
        this.nickname = value2;
        MutableComponent[] mutableComponentArray = new MutableComponent[]{value2};
        this._nickname.emit((MutableComponent[])mutableComponentArray);
    }

    @NotNull
    public final MutableComponent getDisplayName() {
        Object object;
        block3: {
            block2: {
                MutableComponent mutableComponent;
                object = this.nickname;
                if (object == null || (object = object.m_6881_()) == null) break block2;
                MutableComponent it = mutableComponent = object;
                boolean bl = false;
                object = !Intrinsics.areEqual((Object)it.m_214077_(), (Object)ComponentContents.f_237124_) ? mutableComponent : null;
                if (object != null) break block3;
            }
            MutableComponent mutableComponent = this.species.getTranslatedName().m_6881_();
            object = mutableComponent;
            Intrinsics.checkNotNullExpressionValue((Object)mutableComponent, (String)"species.translatedName.copy()");
        }
        return object;
    }

    public final int getLevel() {
        return this.level;
    }

    public final void setLevel(int value2) {
        int boundedValue = Mth.m_14045_((int)value2, (int)1, (int)Cobblemon.INSTANCE.getConfig().getMaxPokemonLevel());
        float hpRatio = RangesKt.coerceIn((float)((float)this.currentHealth / (float)this.getHp()), (float)0.0f, (float)1.0f);
        this.level = boundedValue;
        if (this.getExperienceGroup().getLevel(this.experience) != boundedValue || value2 == Cobblemon.INSTANCE.getConfig().getMaxPokemonLevel()) {
            this.setExperience$common(this.getExperienceGroup().getExperience(boundedValue));
        }
        this.setCurrentHealth(RangesKt.coerceIn((int)Mth.m_14167_((float)(hpRatio * (float)this.getHp())), (ClosedRange)((ClosedRange)new IntRange(0, this.getHp()))));
    }

    public final int getCurrentHealth() {
        return this.currentHealth;
    }

    /*
     * WARNING - void declaration
     */
    public final void setCurrentHealth(int value2) {
        if (value2 == this.currentHealth) {
            return;
        }
        if (value2 <= 0) {
            PokemonEntity pokemonEntity = this.getEntity();
            if (pokemonEntity != null) {
                pokemonEntity.m_21153_(0.0f);
            }
            this.setStatus(null);
        }
        this.currentHealth = Math.max(Math.min(this.getHp(), value2), 0);
        Integer[] integerArray = new Integer[]{this.currentHealth};
        this._currentHealth.emit((Integer[])integerArray);
        if (this.isFainted()) {
            void $this$filterTo$iv$iv;
            void this_$iv;
            Pokemon.decrementFriendship$default(this, 1, false, 2, null);
            int faintTime = Cobblemon.INSTANCE.getConfig().getDefaultFaintTimer();
            EventObservable<PokemonFaintedEvent> eventObservable = CobblemonEvents.POKEMON_FAINTED;
            PokemonFaintedEvent[] pokemonFaintedEventArray = new PokemonFaintedEvent[]{new PokemonFaintedEvent(this, faintTime)};
            PokemonFaintedEvent[] events$iv = pokemonFaintedEventArray;
            boolean $i$f$post22 = false;
            this_$iv.emit(Arrays.copyOf(events$iv, events$iv.length));
            PokemonFaintedEvent[] $this$forEach$iv$iv = events$iv;
            boolean $i$f$forEach = false;
            int n = $this$forEach$iv$iv.length;
            for (int i = 0; i < n; ++i) {
                PokemonFaintedEvent element$iv$iv;
                PokemonFaintedEvent it = element$iv$iv = $this$forEach$iv$iv[i];
                boolean bl = false;
                this.setFaintedTimer(it.getFaintedTimer());
            }
            Iterable $this$filter$iv = this.getEvolutionProxy().current().progress();
            boolean $i$f$filter = false;
            Iterable $i$f$post22 = $this$filter$iv;
            Collection destination$iv$iv = new ArrayList();
            boolean $i$f$filterTo = false;
            for (Object element$iv$iv : $this$filterTo$iv$iv) {
                EvolutionProgress it = (EvolutionProgress)element$iv$iv;
                boolean bl = false;
                if (!(it instanceof RecoilEvolutionProgress || it instanceof DamageTakenEvolutionProgress)) continue;
                destination$iv$iv.add(element$iv$iv);
            }
            Iterable $this$forEach$iv = (List)destination$iv$iv;
            boolean $i$f$forEach2 = false;
            for (Object element$iv : $this$forEach$iv) {
                EvolutionProgress it = (EvolutionProgress)element$iv;
                boolean bl = false;
                it.reset();
            }
        }
        this.setHealTimer(Cobblemon.INSTANCE.getConfig().getHealTimer());
    }

    @NotNull
    public final Gender getGender() {
        return this.gender;
    }

    public final void setGender(@NotNull Gender value2) {
        Intrinsics.checkNotNullParameter((Object)((Object)value2), (String)"value");
        this.gender = value2;
        if (!this.isClient) {
            this.checkGender();
        }
        if (this.gender == value2) {
            this.updateAspects();
            Gender[] genderArray = new Gender[]{value2};
            this._gender.emit((Gender[])genderArray);
        }
    }

    @Nullable
    public final PersistentStatusContainer getStatus() {
        return this.status;
    }

    public final void setStatus(@Nullable PersistentStatusContainer value2) {
        this.status = value2;
        PersistentStatus[] persistentStatusArray = new PersistentStatus[1];
        PersistentStatusContainer persistentStatusContainer = value2;
        persistentStatusArray[0] = persistentStatusContainer != null ? persistentStatusContainer.getStatus() : null;
        this._status.emit((PersistentStatus[])persistentStatusArray);
    }

    public final int getExperience() {
        return this.experience;
    }

    public final void setExperience$common(int value2) {
        this.experience = value2;
        if (this.level == Cobblemon.INSTANCE.getConfig().getMaxPokemonLevel()) {
            this.experience = this.getExperienceGroup().getExperience(this.level);
        }
        Integer[] integerArray = new Integer[]{this.experience};
        this._experience.emit((Integer[])integerArray);
    }

    public final int getFriendship() {
        return this.friendship;
    }

    /*
     * WARNING - void declaration
     */
    private final void setFriendship(int value2) {
        void events$iv;
        void this_$iv;
        if (!this.isClient && !this.isPossibleFriendship(value2)) {
            return;
        }
        EventObservable<FriendshipUpdatedEvent> eventObservable = CobblemonEvents.FRIENDSHIP_UPDATED;
        FriendshipUpdatedEvent[] friendshipUpdatedEventArray = new FriendshipUpdatedEvent[]{new FriendshipUpdatedEvent(this, value2)};
        boolean $i$f$post = false;
        this_$iv.emit(Arrays.copyOf(events$iv, ((void)events$iv).length));
        void $this$forEach$iv$iv = events$iv;
        boolean $i$f$forEach = false;
        int n = ((void)$this$forEach$iv$iv).length;
        for (int i = 0; i < n; ++i) {
            void element$iv$iv;
            void it = element$iv$iv = $this$forEach$iv$iv[i];
            boolean bl = false;
            this.friendship = it.getNewFriendship();
            Integer[] integerArray = new Integer[]{it.getNewFriendship()};
            this._friendship.emit((Integer[])integerArray);
        }
    }

    @NotNull
    public final PokemonState getState() {
        return this.state;
    }

    public final void setState(@NotNull PokemonState value2) {
        Intrinsics.checkNotNullParameter((Object)value2, (String)"value");
        this.state = value2;
        PokemonState[] pokemonStateArray = new PokemonState[]{value2};
        this._state.emit((PokemonState[])pokemonStateArray);
    }

    @Nullable
    public final PokemonEntity getEntity() {
        PokemonState it = this.state;
        boolean bl = false;
        return it instanceof ActivePokemonState ? ((ActivePokemonState)it).getEntity() : null;
    }

    @NotNull
    public final ElementalType getPrimaryType() {
        return this.form.getPrimaryType();
    }

    @Nullable
    public final ElementalType getSecondaryType() {
        return this.form.getSecondaryType();
    }

    @NotNull
    public final Iterable<ElementalType> getTypes() {
        return this.form.getTypes();
    }

    @NotNull
    public final TeraType getTeraType() {
        return this.teraType;
    }

    public final void setTeraType(@NotNull TeraType value2) {
        Intrinsics.checkNotNullParameter((Object)value2, (String)"value");
        this.teraType = value2;
        TeraType[] teraTypeArray = new TeraType[]{value2};
        this._teraType.emit((TeraType[])teraTypeArray);
    }

    public final int getDmaxLevel() {
        return this.dmaxLevel;
    }

    public final void setDmaxLevel(int value2) {
        this.dmaxLevel = RangesKt.coerceIn((int)value2, (int)0, (int)Cobblemon.INSTANCE.getConfig().getMaxDynamaxLevel());
        Integer[] integerArray = new Integer[]{value2};
        this._dmaxLevel.emit((Integer[])integerArray);
    }

    public final boolean getGmaxFactor() {
        return this.gmaxFactor;
    }

    public final void setGmaxFactor(boolean value2) {
        block7: {
            block6: {
                Object v0;
                block5: {
                    Object it$iv$iv;
                    boolean bl;
                    Object it;
                    boolean bl2;
                    Object element$iv$iv;
                    Object element$iv$iv$iv;
                    Iterable $this$mapNotNullTo$iv$iv;
                    Iterable $this$mapNotNull$iv = this.species.getStandardForm().getEvolutions();
                    boolean $i$f$mapNotNull = false;
                    Iterable iterable = $this$mapNotNull$iv;
                    Collection destination$iv$iv = new ArrayList();
                    boolean $i$f$mapNotNullTo = false;
                    Iterable $this$forEach$iv$iv$iv = $this$mapNotNullTo$iv$iv;
                    boolean $i$f$forEach = false;
                    Iterator iterator = $this$forEach$iv$iv$iv.iterator();
                    while (iterator.hasNext()) {
                        element$iv$iv = element$iv$iv$iv = iterator.next();
                        bl2 = false;
                        it = (Evolution)element$iv$iv;
                        boolean bl3 = false;
                        if (it.getResult().getSpecies() == null) continue;
                        bl = false;
                        destination$iv$iv.add(it$iv$iv);
                    }
                    $this$mapNotNull$iv = (List)destination$iv$iv;
                    $i$f$mapNotNull = false;
                    $this$mapNotNullTo$iv$iv = $this$mapNotNull$iv;
                    destination$iv$iv = new ArrayList();
                    $i$f$mapNotNullTo = false;
                    $this$forEach$iv$iv$iv = $this$mapNotNullTo$iv$iv;
                    $i$f$forEach = false;
                    iterator = $this$forEach$iv$iv$iv.iterator();
                    while (iterator.hasNext()) {
                        element$iv$iv = element$iv$iv$iv = iterator.next();
                        bl2 = false;
                        it = (String)element$iv$iv;
                        boolean bl4 = false;
                        if (PokemonSpecies.INSTANCE.getByName((String)it) == null) continue;
                        bl = false;
                        destination$iv$iv.add(it$iv$iv);
                    }
                    List evolutions = (List)destination$iv$iv;
                    if (this.species.canGmax()) break block6;
                    Iterable iterable2 = evolutions;
                    for (Object e : iterable2) {
                        Species it2 = (Species)e;
                        boolean bl5 = false;
                        if (!it2.canGmax()) continue;
                        v0 = e;
                        break block5;
                    }
                    v0 = null;
                }
                if (v0 == null) break block7;
            }
            this.gmaxFactor = value2;
            Boolean[] booleanArray = new Boolean[]{value2};
            this._gmaxFactor.emit((Boolean[])booleanArray);
        }
    }

    public final boolean getShiny() {
        return this.shiny;
    }

    public final void setShiny(boolean value2) {
        this.shiny = value2;
        this.updateAspects();
        Boolean[] booleanArray = new Boolean[]{value2};
        this._shiny.emit((Boolean[])booleanArray);
    }

    public final boolean getTradeable() {
        return this.tradeable;
    }

    public final void setTradeable(boolean value2) {
        this.tradeable = value2;
        Boolean[] booleanArray = new Boolean[]{value2};
        this._tradeable.emit((Boolean[])booleanArray);
    }

    @NotNull
    public final Nature getNature() {
        return this.nature;
    }

    public final void setNature(@NotNull Nature value2) {
        Intrinsics.checkNotNullParameter((Object)value2, (String)"value");
        this.nature = value2;
        Nature[] natureArray = new Nature[]{value2};
        this._nature.emit((Nature[])natureArray);
    }

    @Nullable
    public final Nature getMintedNature() {
        return this.mintedNature;
    }

    public final void setMintedNature(@Nullable Nature value2) {
        this.mintedNature = value2;
        Nature[] natureArray = new Nature[]{value2};
        this._mintedNature.emit((Nature[])natureArray);
    }

    @NotNull
    public final Nature getEffectiveNature() {
        Nature nature = this.mintedNature;
        if (nature == null) {
            nature = this.nature;
        }
        return nature;
    }

    @NotNull
    public final MoveSet getMoveSet() {
        return this.moveSet;
    }

    @NotNull
    public final ExperienceGroup getExperienceGroup() {
        return this.form.getExperienceGroup();
    }

    public final int getFaintedTimer() {
        return this.faintedTimer;
    }

    public final void setFaintedTimer(int value2) {
        this.faintedTimer = value2;
        Pokemon[] pokemonArray = new Pokemon[]{this};
        this.anyChangeObservable.emit((Pokemon[])pokemonArray);
    }

    public final int getHealTimer() {
        return this.healTimer;
    }

    public final void setHealTimer(int value2) {
        this.healTimer = value2;
        Pokemon[] pokemonArray = new Pokemon[]{this};
        this.anyChangeObservable.emit((Pokemon[])pokemonArray);
    }

    @Nullable
    public final UUID getTetheringId() {
        return this.tetheringId;
    }

    public final void setTetheringId(@Nullable UUID value2) {
        this.tetheringId = value2;
        UUID[] uUIDArray = new UUID[]{value2};
        this._tetheringId.emit((UUID[])uUIDArray);
    }

    @NotNull
    public final OriginalTrainerType getOriginalTrainerType() {
        return this.originalTrainerType;
    }

    @Nullable
    public final String getOriginalTrainer() {
        return this.originalTrainer;
    }

    @Nullable
    public final String getOriginalTrainerName() {
        return this.originalTrainerName;
    }

    public final void setOriginalTrainerName(@Nullable String value2) {
        this.originalTrainerName = value2;
        String[] stringArray = new String[]{value2};
        this._originalTrainerName.emit((String[])stringArray);
    }

    @NotNull
    public final BenchedMoves getBenchedMoves() {
        return this.benchedMoves;
    }

    @NotNull
    public final Ability getAbility() {
        return this.ability;
    }

    public final void setAbility$common(@NotNull Ability value2) {
        Intrinsics.checkNotNullParameter((Object)value2, (String)"value");
        if (!Intrinsics.areEqual((Object)this.ability, (Object)value2)) {
            Ability[] abilityArray = new Ability[]{value2};
            this._ability.emit((Ability[])abilityArray);
        }
        this.ability = value2;
    }

    public final int getHp() {
        return this.getStat(Stats.HP);
    }

    public final int getAttack() {
        return this.getStat(Stats.ATTACK);
    }

    public final int getDefence() {
        return this.getStat(Stats.DEFENCE);
    }

    public final int getSpecialAttack() {
        return this.getStat(Stats.SPECIAL_ATTACK);
    }

    public final int getSpecialDefence() {
        return this.getStat(Stats.SPECIAL_DEFENCE);
    }

    public final int getSpeed() {
        return this.getStat(Stats.SPEED);
    }

    public final float getScaleModifier() {
        return this.scaleModifier;
    }

    public final void setScaleModifier(float f) {
        this.scaleModifier = f;
    }

    @NotNull
    public final PokeBall getCaughtBall() {
        return this.caughtBall;
    }

    public final void setCaughtBall(@NotNull PokeBall value2) {
        Intrinsics.checkNotNullParameter((Object)value2, (String)"value");
        this.caughtBall = value2;
        PokeBall[] pokeBallArray = new PokeBall[]{this.caughtBall};
        this._caughtBall.emit((PokeBall[])pokeBallArray);
    }

    @NotNull
    public final List<SpeciesFeature> getFeatures() {
        return this.features;
    }

    public final void setFeatures(@NotNull List<SpeciesFeature> list) {
        Intrinsics.checkNotNullParameter(list, (String)"<set-?>");
        this.features = list;
    }

    @NotNull
    public final RenderablePokemon asRenderablePokemon() {
        return new RenderablePokemon(this.species, this.aspects);
    }

    @NotNull
    public final Set<String> getAspects() {
        return this.aspects;
    }

    public final void setAspects(@NotNull Set<String> value2) {
        Intrinsics.checkNotNullParameter(value2, (String)"value");
        if (!Intrinsics.areEqual(this.aspects, value2)) {
            this.aspects = value2;
            if (!this.isClient) {
                this.updateForm();
            }
            Set[] setArray = new Set[]{value2};
            this._aspects.emit(setArray);
        }
    }

    public final boolean isClient$common() {
        return this.isClient;
    }

    public final void setClient$common(boolean bl) {
        this.isClient = bl;
    }

    @NotNull
    public final SettableObservable<StoreCoordinates<?>> getStoreCoordinates() {
        return this.storeCoordinates;
    }

    @NotNull
    public final Iterable<Evolution> getEvolutions() {
        Iterable $this$sortedBy$iv = this.form.getEvolutions();
        boolean $i$f$sortedBy = false;
        return CollectionsKt.sortedWith((Iterable)$this$sortedBy$iv, (Comparator)new Comparator(){

            public final int compare(T a, T b) {
                Evolution evolution = (Evolution)a;
                boolean bl = false;
                Comparable comparable = Boolean.valueOf(evolution.getOptional());
                evolution = (Evolution)b;
                Comparable comparable2 = comparable;
                bl = false;
                return ComparisonsKt.compareValues((Comparable)comparable2, (Comparable)Boolean.valueOf(evolution.getOptional()));
            }
        });
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public final Iterable<Evolution> getLockedEvolutions() {
        void $this$filterTo$iv$iv;
        Iterable<Evolution> $this$filter$iv = this.getEvolutions();
        boolean $i$f$filter = false;
        Iterable<Evolution> iterable = $this$filter$iv;
        Collection destination$iv$iv = new ArrayList();
        boolean $i$f$filterTo = false;
        for (Object element$iv$iv : $this$filterTo$iv$iv) {
            Evolution it = (Evolution)element$iv$iv;
            boolean bl = false;
            if (!(!CollectionsKt.contains((Iterable)this.getEvolutionProxy().current(), (Object)it))) continue;
            destination$iv$iv.add(element$iv$iv);
        }
        return (List)destination$iv$iv;
    }

    @Nullable
    public final PreEvolution getPreEvolution() {
        return this.form.getPreEvolution();
    }

    @NotNull
    public final EvolutionProxy<EvolutionDisplay, Evolution> getEvolutionProxy() {
        Lazy lazy = this.evolutionProxy$delegate;
        return (EvolutionProxy)lazy.getValue();
    }

    @NotNull
    public final List<CustomPokemonProperty> getCustomProperties() {
        return this.customProperties;
    }

    @NotNull
    public final CompoundTag getPersistentData() {
        return this.persistentData;
    }

    public int getStat(@NotNull Stat stat) {
        Intrinsics.checkNotNullParameter((Object)stat, (String)"stat");
        return Cobblemon.INSTANCE.getStatProvider().getStatForPokemon(this, stat);
    }

    @Override
    @NotNull
    public String showdownId() {
        if (Intrinsics.areEqual((Object)this.form, (Object)this.species.getStandardForm())) {
            return this.species.showdownId();
        }
        return this.form.showdownId();
    }

    /*
     * WARNING - void declaration
     */
    @Nullable
    public final PokemonEntity sendOut(@NotNull ServerLevel level, @NotNull Vec3 position, @Nullable IllusionEffect illusion, @NotNull Function1<? super PokemonEntity, Unit> mutation) {
        void this_$iv$iv;
        void $this$iv;
        Intrinsics.checkNotNullParameter((Object)level, (String)"level");
        Intrinsics.checkNotNullParameter((Object)position, (String)"position");
        Intrinsics.checkNotNullParameter(mutation, (String)"mutation");
        CancelableObservable<PokemonSentPreEvent> cancelableObservable = CobblemonEvents.POKEMON_SENT_PRE;
        Cancelable event$iv = new PokemonSentPreEvent(this, level, position);
        boolean $i$f$postThen = false;
        EventObservable eventObservable = (EventObservable)$this$iv;
        Cancelable[] cancelableArray = new Cancelable[]{event$iv};
        Cancelable[] events$iv$iv = cancelableArray;
        boolean $i$f$post = false;
        this_$iv$iv.emit(Arrays.copyOf(events$iv$iv, events$iv$iv.length));
        Cancelable[] $this$forEach$iv$iv$iv = events$iv$iv;
        boolean $i$f$forEach = false;
        int n = $this$forEach$iv$iv$iv.length;
        for (int i = 0; i < n; ++i) {
            Cancelable it;
            Cancelable element$iv$iv$iv;
            Cancelable it$iv = element$iv$iv$iv = $this$forEach$iv$iv$iv[i];
            boolean bl = false;
            if (!it$iv.isCanceled()) {
                it = (PokemonSentPreEvent)it$iv;
                boolean bl2 = false;
                SeasonFeatureHandler.INSTANCE.updateSeason(this, (LevelAccessor)level, Vec3ExtensionsKt.toBlockPos(position));
                PokemonEntity entity2 = new PokemonEntity((Level)level, this, null, 4, null);
                IllusionEffect illusionEffect = illusion;
                if (illusionEffect != null) {
                    illusionEffect.start(entity2);
                }
                boolean sentOut = EntityExtensionsKt.setPositionSafely((Entity)entity2, position);
                if (!sentOut) {
                    entity2.m_20343_(position.f_82479_, position.f_82480_, position.f_82481_);
                }
                mutation.invoke((Object)entity2);
                level.m_7967_((Entity)entity2);
                this.setState(new SentOutState(entity2));
                return entity2;
            }
            Cancelable cancelable = it$iv;
            boolean bl3 = false;
            it = cancelable;
        }
        return null;
    }

    public static /* synthetic */ PokemonEntity sendOut$default(Pokemon pokemon, ServerLevel serverLevel, Vec3 vec3, IllusionEffect illusionEffect, Function1 function1, int n, Object object) {
        if (object != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: sendOut");
        }
        if ((n & 8) != 0) {
            function1 = sendOut.1.INSTANCE;
        }
        return pokemon.sendOut(serverLevel, vec3, illusionEffect, (Function1<? super PokemonEntity, Unit>)function1);
    }

    @NotNull
    public final CompletableFuture<PokemonEntity> sendOutWithAnimation(@NotNull LivingEntity source, @NotNull ServerLevel level, @NotNull Vec3 position, @Nullable UUID battleId, boolean doCry, @Nullable IllusionEffect illusion, @NotNull Function1<? super PokemonEntity, Unit> mutation) {
        Intrinsics.checkNotNullParameter((Object)source, (String)"source");
        Intrinsics.checkNotNullParameter((Object)level, (String)"level");
        Intrinsics.checkNotNullParameter((Object)position, (String)"position");
        Intrinsics.checkNotNullParameter(mutation, (String)"mutation");
        if (this.state instanceof ShoulderedState) {
            return this.sendOutFromShoulder((ServerPlayer)source, level, position, battleId, doCry, illusion, mutation);
        }
        CompletableFuture<PokemonEntity> future2 = new CompletableFuture<PokemonEntity>();
        this.sendOut(level, position, illusion, (Function1<? super PokemonEntity, Unit>)((Function1)new Function1<PokemonEntity, Unit>(this, source, battleId, mutation, level, future2, doCry){
            final /* synthetic */ Pokemon this$0;
            final /* synthetic */ LivingEntity $source;
            final /* synthetic */ UUID $battleId;
            final /* synthetic */ Function1<PokemonEntity, Unit> $mutation;
            final /* synthetic */ ServerLevel $level;
            final /* synthetic */ CompletableFuture<PokemonEntity> $future;
            final /* synthetic */ boolean $doCry;
            {
                this.this$0 = $receiver;
                this.$source = $source;
                this.$battleId = $battleId;
                this.$mutation = $mutation;
                this.$level = $level;
                this.$future = $future;
                this.$doCry = $doCry;
                super(1);
            }

            public final void invoke(@NotNull PokemonEntity it) {
                Intrinsics.checkNotNullParameter((Object)it, (String)"it");
                ServerPlayer serverPlayer = this.this$0.getOwnerPlayer();
                if (serverPlayer != null) {
                    ServerPlayer serverPlayer2 = serverPlayer;
                    ServerLevel serverLevel = this.$level;
                    ServerPlayer it2 = serverPlayer2;
                    boolean bl = false;
                    it2.m_21011_(InteractionHand.MAIN_HAND, true);
                    Level level = (Level)serverLevel;
                    Vec3 vec3 = it2.m_20182_();
                    Intrinsics.checkNotNullExpressionValue((Object)vec3, (String)"it.pos");
                    WorldExtensionsKt.playSoundServer$default(level, vec3, CobblemonSounds.POKE_BALL_THROW, null, 0.6f, 0.0f, 20, null);
                }
                it.setPhasingTargetId(this.$source.m_19879_());
                it.setBeamMode(1);
                it.setBattleId(this.$battleId);
                it.after(1.5f, (Function0<Unit>)((Function0)new Function0<Unit>(it, this.$future, this.this$0, this.$doCry){
                    final /* synthetic */ PokemonEntity $it;
                    final /* synthetic */ CompletableFuture<PokemonEntity> $future;
                    final /* synthetic */ Pokemon this$0;
                    final /* synthetic */ boolean $doCry;
                    {
                        this.$it = $it;
                        this.$future = $future;
                        this.this$0 = $receiver;
                        this.$doCry = $doCry;
                        super(0);
                    }

                    /*
                     * WARNING - void declaration
                     */
                    public final void invoke() {
                        void events$iv;
                        void $this$iv;
                        this.$it.setPhasingTargetId(-1);
                        this.$it.setBeamMode(0);
                        this.$future.complete(this.$it);
                        EventObservable<PokemonSentPostEvent> eventObservable = CobblemonEvents.POKEMON_SENT_POST;
                        PokemonSentPostEvent[] pokemonSentPostEventArray = new PokemonSentPostEvent[]{new PokemonSentPostEvent(this.this$0, this.$it)};
                        boolean $i$f$post = false;
                        $this$iv.emit(Arrays.copyOf(events$iv, ((void)events$iv).length));
                        void $this$forEach$iv$iv = events$iv;
                        boolean $i$f$forEach = false;
                        int n = ((void)$this$forEach$iv$iv).length;
                        for (int i = 0; i < n; ++i) {
                            void element$iv$iv;
                            void var9_9 = element$iv$iv = $this$forEach$iv$iv[i];
                            boolean bl = false;
                            void it = var9_9;
                        }
                        if (this.$doCry) {
                            this.$it.cry();
                        }
                    }
                }));
                this.$mutation.invoke((Object)it);
            }
        }));
        return future2;
    }

    public static /* synthetic */ CompletableFuture sendOutWithAnimation$default(Pokemon pokemon, LivingEntity livingEntity, ServerLevel serverLevel, Vec3 vec3, UUID uUID, boolean bl, IllusionEffect illusionEffect, Function1 function1, int n, Object object) {
        if (object != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: sendOutWithAnimation");
        }
        if ((n & 8) != 0) {
            uUID = null;
        }
        if ((n & 0x10) != 0) {
            bl = true;
        }
        if ((n & 0x20) != 0) {
            illusionEffect = null;
        }
        if ((n & 0x40) != 0) {
            function1 = sendOutWithAnimation.1.INSTANCE;
        }
        return pokemon.sendOutWithAnimation(livingEntity, serverLevel, vec3, uUID, bl, illusionEffect, (Function1<? super PokemonEntity, Unit>)function1);
    }

    @NotNull
    public final CompletableFuture<PokemonEntity> sendOutFromShoulder(@NotNull ServerPlayer player, @NotNull ServerLevel level, @NotNull Vec3 targetPosition, @Nullable UUID battleId, boolean doCry, @Nullable IllusionEffect illusion, @NotNull Function1<? super PokemonEntity, Unit> mutation) {
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        Intrinsics.checkNotNullParameter((Object)level, (String)"level");
        Intrinsics.checkNotNullParameter((Object)targetPosition, (String)"targetPosition");
        Intrinsics.checkNotNullParameter(mutation, (String)"mutation");
        CompletableFuture<PokemonEntity> future2 = new CompletableFuture<PokemonEntity>();
        PokemonState pokemonState = this.state;
        Intrinsics.checkNotNull((Object)pokemonState, (String)"null cannot be cast to non-null type bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.activestate.ShoulderedState");
        boolean isLeftShoulder = ((ShoulderedState)pokemonState).isLeftShoulder();
        double arbitraryXOffset = (double)player.m_20205_() * 0.3 + (double)this.form.getHitbox().f_20377_ * 0.3;
        double shoulderHorizontalOffset = isLeftShoulder ? arbitraryXOffset : -arbitraryXOffset;
        float rotation = player.m_146908_();
        double approxShoulderMonHight = (double)player.m_20206_() - (double)this.form.getHitbox().f_20378_ * 0.4;
        Vec3 rotatedOffset = new Vec3(shoulderHorizontalOffset, approxShoulderMonHight, 0.0).m_82524_(-rotation * ((float)Math.PI / 180));
        Vec3 currentPosition = player.m_20182_().m_82549_(rotatedOffset);
        this.recall();
        Intrinsics.checkNotNullExpressionValue((Object)currentPosition, (String)"currentPosition");
        this.sendOut(level, currentPosition, illusion, (Function1<? super PokemonEntity, Unit>)((Function1)new Function1<PokemonEntity, Unit>(level, currentPosition, targetPosition, battleId, mutation, future2, this, doCry){
            final /* synthetic */ ServerLevel $level;
            final /* synthetic */ Vec3 $currentPosition;
            final /* synthetic */ Vec3 $targetPosition;
            final /* synthetic */ UUID $battleId;
            final /* synthetic */ Function1<PokemonEntity, Unit> $mutation;
            final /* synthetic */ CompletableFuture<PokemonEntity> $future;
            final /* synthetic */ Pokemon this$0;
            final /* synthetic */ boolean $doCry;
            {
                this.$level = $level;
                this.$currentPosition = $currentPosition;
                this.$targetPosition = $targetPosition;
                this.$battleId = $battleId;
                this.$mutation = $mutation;
                this.$future = $future;
                this.this$0 = $receiver;
                this.$doCry = $doCry;
                super(1);
            }

            public final void invoke(@NotNull PokemonEntity it) {
                Intrinsics.checkNotNullParameter((Object)it, (String)"it");
                Level level = (Level)this.$level;
                Vec3 vec3 = this.$currentPosition;
                Intrinsics.checkNotNullExpressionValue((Object)vec3, (String)"currentPosition");
                WorldExtensionsKt.playSoundServer$default(level, vec3, CobblemonSounds.PC_DROP, null, 0.6f, 0.0f, 20, null);
                it.m_21566_().m_6849_(this.$targetPosition.f_82479_, this.$targetPosition.f_82480_, this.$targetPosition.f_82481_, 1.2);
                it.setBattleId(this.$battleId);
                SchedulingFunctionsKt.afterOnServer$default(0, 1.5f, (Function0)new Function0<Unit>(this.$future, it, this.this$0, this.$doCry){
                    final /* synthetic */ CompletableFuture<PokemonEntity> $future;
                    final /* synthetic */ PokemonEntity $it;
                    final /* synthetic */ Pokemon this$0;
                    final /* synthetic */ boolean $doCry;
                    {
                        this.$future = $future;
                        this.$it = $it;
                        this.this$0 = $receiver;
                        this.$doCry = $doCry;
                        super(0);
                    }

                    /*
                     * WARNING - void declaration
                     */
                    public final void invoke() {
                        void events$iv;
                        void $this$iv;
                        this.$future.complete(this.$it);
                        EventObservable<PokemonSentPostEvent> eventObservable = CobblemonEvents.POKEMON_SENT_POST;
                        PokemonSentPostEvent[] pokemonSentPostEventArray = new PokemonSentPostEvent[]{new PokemonSentPostEvent(this.this$0, this.$it)};
                        boolean $i$f$post = false;
                        $this$iv.emit(Arrays.copyOf(events$iv, ((void)events$iv).length));
                        void $this$forEach$iv$iv = events$iv;
                        boolean $i$f$forEach = false;
                        int n = ((void)$this$forEach$iv$iv).length;
                        for (int i = 0; i < n; ++i) {
                            void element$iv$iv;
                            void var9_9 = element$iv$iv = $this$forEach$iv$iv[i];
                            boolean bl = false;
                            void it = var9_9;
                        }
                        if (this.$doCry) {
                            this.$it.cry();
                        }
                    }
                }, 1, null);
                this.$mutation.invoke((Object)it);
            }
        }));
        return future2;
    }

    public static /* synthetic */ CompletableFuture sendOutFromShoulder$default(Pokemon pokemon, ServerPlayer serverPlayer, ServerLevel serverLevel, Vec3 vec3, UUID uUID, boolean bl, IllusionEffect illusionEffect, Function1 function1, int n, Object object) {
        if (object != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: sendOutFromShoulder");
        }
        if ((n & 8) != 0) {
            uUID = null;
        }
        if ((n & 0x10) != 0) {
            bl = true;
        }
        if ((n & 0x20) != 0) {
            illusionEffect = null;
        }
        if ((n & 0x40) != 0) {
            function1 = sendOutFromShoulder.1.INSTANCE;
        }
        return pokemon.sendOutFromShoulder(serverPlayer, serverLevel, vec3, uUID, bl, illusionEffect, (Function1<? super PokemonEntity, Unit>)function1);
    }

    /*
     * WARNING - void declaration
     */
    public final void recall() {
        block1: {
            void events$iv;
            void $this$iv;
            EventObservable<PokemonRecalledEvent> eventObservable = CobblemonEvents.POKEMON_RECALLED;
            Object object = new PokemonRecalledEvent[]{new PokemonRecalledEvent(this, this.getEntity())};
            boolean $i$f$post = false;
            $this$iv.emit(Arrays.copyOf(events$iv, ((void)events$iv).length));
            void $this$forEach$iv$iv = events$iv;
            boolean $i$f$forEach = false;
            int n = ((void)$this$forEach$iv$iv).length;
            for (int i = 0; i < n; ++i) {
                void element$iv$iv;
                void var9_9 = element$iv$iv = $this$forEach$iv$iv[i];
                boolean bl = false;
                void it = var9_9;
            }
            object = this.state;
            ActivePokemonState state = object instanceof ActivePokemonState ? (ActivePokemonState)object : null;
            this.setState(new InactivePokemonState());
            ActivePokemonState activePokemonState = state;
            if (activePokemonState == null) break block1;
            activePokemonState.recall();
        }
    }

    public final void tryRecallWithAnimation() {
        if (this.getEntity() != null) {
            PokemonEntity pokemonEntity = this.getEntity();
            if (pokemonEntity != null) {
                pokemonEntity.recallWithAnimation();
            }
            return;
        }
        this.recall();
    }

    public final void heal() {
        block0: {
            PokemonEntity entity2;
            this.setCurrentHealth(this.getHp());
            this.moveSet.heal();
            this.setStatus(null);
            this.setFaintedTimer(-1);
            this.setHealTimer(-1);
            PokemonEntity pokemonEntity = entity2 = this.getEntity();
            if (pokemonEntity == null) break block0;
            pokemonEntity.m_5634_(entity2.m_21233_() - entity2.m_21223_());
        }
    }

    public final boolean isFullHealth() {
        return this.currentHealth == this.getHp();
    }

    public final void didSleep() {
        PokemonEntity entity2;
        this.setCurrentHealth(Math.min(this.currentHealth + this.getHp() / 2, this.getHp()));
        this.setStatus(null);
        this.setFaintedTimer(-1);
        this.setHealTimer(-1);
        PokemonEntity pokemonEntity = entity2 = this.getEntity();
        if (pokemonEntity != null) {
            pokemonEntity.m_5634_(entity2.m_21233_() - entity2.m_21223_());
        }
        this.moveSet.partialHeal();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final boolean canBeHealed() {
        boolean bl;
        if (this.getHp() != this.currentHealth) return true;
        if (this.status != null) return true;
        Iterable $this$any$iv = this.moveSet;
        boolean $i$f$any = false;
        if ($this$any$iv instanceof Collection && ((Collection)$this$any$iv).isEmpty()) {
            return false;
        }
        Iterator iterator = $this$any$iv.iterator();
        do {
            if (!iterator.hasNext()) return false;
            Object element$iv = iterator.next();
            Move move = (Move)element$iv;
            boolean bl2 = false;
            if (move.getCurrentPp() != move.getMaxPp()) {
                return true;
            }
            bl = false;
        } while (!bl);
        return true;
    }

    public final boolean isFainted() {
        return this.currentHealth <= 0;
    }

    private final void updateHP(float quotient) {
        this.setCurrentHealth(MathKt.roundToInt((float)((float)this.getHp() * quotient)));
    }

    public final void applyStatus(@NotNull PersistentStatus status) {
        Intrinsics.checkNotNullParameter((Object)status, (String)"status");
        this.setStatus(new PersistentStatusContainer(status, RangesKt.random((IntRange)status.statusPeriod(), (Random)((Random)Random.Default))));
        if (this.status != null) {
            PersistentStatus[] persistentStatusArray = new PersistentStatus[1];
            PersistentStatusContainer persistentStatusContainer = this.status;
            Intrinsics.checkNotNull((Object)persistentStatusContainer);
            persistentStatusArray[0] = persistentStatusContainer.getStatus();
            this._status.emit((PersistentStatus[])persistentStatusArray);
        }
    }

    public final boolean isFireImmune() {
        return CollectionsKt.contains(this.getTypes(), (Object)ElementalTypes.INSTANCE.getFIRE()) || !this.form.getBehaviour().getMoving().getSwim().getHurtByLava();
    }

    public final boolean isPositionSafe(@NotNull Level world, @NotNull Vec3 pos) {
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        return this.isPositionSafe(world, Vec3ExtensionsKt.toBlockPos(pos));
    }

    public final boolean isPositionSafe(@NotNull Level world, @NotNull BlockPos pos1) {
        BlockPos blockPos2;
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        Intrinsics.checkNotNullParameter((Object)pos1, (String)"pos1");
        if (world.m_8055_(pos1).m_280296_()) {
            BlockPos blockPos3 = pos1.m_7494_();
            blockPos2 = blockPos3;
            Intrinsics.checkNotNullExpressionValue((Object)blockPos3, (String)"{\n            pos1.up()\n        }");
        } else {
            BlockPos blockPos4 = pos1.m_7495_();
            blockPos2 = blockPos4;
            Intrinsics.checkNotNullExpressionValue((Object)blockPos4, (String)"{\n            pos1.down()\n        }");
        }
        BlockPos pos2 = blockPos2;
        BlockPos[] blockPosArray = new BlockPos[]{pos1, pos2};
        BlockPos[] positions = blockPosArray;
        boolean isSafe = true;
        for (BlockPos pos : positions) {
            if (!isSafe) continue;
            Block block = world.m_8055_(pos).m_60734_();
            if (block instanceof SweetBerryBushBlock || block instanceof CactusBlock || block instanceof WitherRoseBlock) {
                isSafe = false;
            }
            if (this.isFireImmune() || !(block instanceof FireBlock) && !(block instanceof MagmaBlock) && !(block instanceof CampfireBlock) && !world.m_8055_(pos).m_60819_().m_205070_(FluidTags.f_13132_)) continue;
            isSafe = false;
        }
        return isSafe;
    }

    public final boolean isLegendary() {
        String[] stringArray = new String[]{"legendary"};
        return this.hasLabels(stringArray);
    }

    public final boolean isMythical() {
        String[] stringArray = new String[]{"mythical"};
        return this.hasLabels(stringArray);
    }

    public final boolean isUltraBeast() {
        String[] stringArray = new String[]{"ultra_beast"};
        return this.hasLabels(stringArray);
    }

    public final boolean hasLabels(String ... labels) {
        boolean bl;
        block5: {
            Intrinsics.checkNotNullParameter((Object)labels, (String)"labels");
            String[] $this$all$iv = labels;
            boolean $i$f$all = false;
            int n = $this$all$iv.length;
            for (int i = 0; i < n; ++i) {
                boolean bl2;
                block4: {
                    String element$iv;
                    String label = element$iv = $this$all$iv[i];
                    boolean bl3 = false;
                    Iterable $this$any$iv = this.form.getLabels();
                    boolean $i$f$any = false;
                    if ($this$any$iv instanceof Collection && ((Collection)$this$any$iv).isEmpty()) {
                        bl2 = false;
                    } else {
                        for (Object element$iv2 : $this$any$iv) {
                            String it = (String)element$iv2;
                            boolean bl4 = false;
                            if (!StringsKt.equals((String)it, (String)label, (boolean)true)) continue;
                            bl2 = true;
                            break block4;
                        }
                        bl2 = false;
                    }
                }
                if (bl2) continue;
                bl = false;
                break block5;
            }
            bl = true;
        }
        return bl;
    }

    public final boolean isUncatchable() {
        return UncatchableProperty.INSTANCE.uncatchable().matches(this);
    }

    @NotNull
    public final ItemStack heldItem() {
        ItemStack itemStack = this.heldItem.m_41777_();
        Intrinsics.checkNotNullExpressionValue((Object)itemStack, (String)"this.heldItem.copy()");
        return itemStack;
    }

    @NotNull
    public final ItemStack heldItemNoCopy$common() {
        return this.heldItem;
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public final ItemStack swapHeldItem(@NotNull ItemStack stack, boolean decrement) {
        void this_$iv$iv;
        void $this$iv;
        Intrinsics.checkNotNullParameter((Object)stack, (String)"stack");
        ItemStack existing = this.heldItem();
        CancelableObservable<HeldItemEvent.Pre> cancelableObservable = CobblemonEvents.HELD_ITEM_PRE;
        Cancelable event$iv = new HeldItemEvent.Pre(this, stack, existing, decrement);
        boolean $i$f$postThen = false;
        EventObservable eventObservable = (EventObservable)$this$iv;
        Cancelable[] cancelableArray = new Cancelable[]{event$iv};
        Cancelable[] events$iv$iv = cancelableArray;
        boolean $i$f$post = false;
        this_$iv$iv.emit(Arrays.copyOf(events$iv$iv, events$iv$iv.length));
        Cancelable[] $this$forEach$iv$iv$iv = events$iv$iv;
        boolean $i$f$forEach = false;
        int n = $this$forEach$iv$iv$iv.length;
        for (int i = 0; i < n; ++i) {
            Cancelable element$iv$iv$iv;
            Cancelable it$iv = element$iv$iv$iv = $this$forEach$iv$iv$iv[i];
            boolean bl = false;
            if (!it$iv.isCanceled()) {
                void this_$iv;
                Object object;
                HeldItemEvent.Pre event = (HeldItemEvent.Pre)it$iv;
                boolean bl2 = false;
                Object[] $this$swapHeldItem_u24lambda_u2418_u24lambda_u2416 = object = event.getReceiving().m_41777_();
                boolean bl3 = false;
                $this$swapHeldItem_u24lambda_u2418_u24lambda_u2416.m_41764_(1);
                ItemStack[] giving = object;
                if (event.getDecrement()) {
                    event.getReceiving().m_41774_(1);
                }
                Intrinsics.checkNotNullExpressionValue((Object)giving, (String)"giving");
                this.heldItem = giving;
                object = new ItemStack[]{giving};
                this._heldItem.emit((ItemStack[])object);
                object = CobblemonEvents.HELD_ITEM_POST;
                $this$swapHeldItem_u24lambda_u2418_u24lambda_u2416 = new HeldItemEvent.Post[1];
                ItemStack itemStack = this.heldItem();
                ItemStack itemStack2 = event.getReturning().m_41777_();
                Intrinsics.checkNotNullExpressionValue((Object)itemStack2, (String)"event.returning.copy()");
                $this$swapHeldItem_u24lambda_u2418_u24lambda_u2416[0] = new HeldItemEvent.Post(this, itemStack, itemStack2, event.getDecrement());
                Object[] events$iv = $this$swapHeldItem_u24lambda_u2418_u24lambda_u2416;
                boolean $i$f$post2 = false;
                this_$iv.emit(Arrays.copyOf(events$iv, events$iv.length));
                Object[] $this$forEach$iv$iv = events$iv;
                boolean $i$f$forEach2 = false;
                int n2 = $this$forEach$iv$iv.length;
                for (int j = 0; j < n2; ++j) {
                    ItemStack element$iv$iv;
                    ItemStack it = element$iv$iv = $this$forEach$iv$iv[j];
                    boolean bl4 = false;
                    GimmighoulStashHandler.INSTANCE.giveHeldItem((HeldItemEvent.Post)it);
                }
                return event.getReturning();
            }
            Cancelable cancelable = it$iv;
            boolean bl5 = false;
            Cancelable it = cancelable;
        }
        return stack;
    }

    public static /* synthetic */ ItemStack swapHeldItem$default(Pokemon pokemon, ItemStack itemStack, boolean bl, int n, Object object) {
        if (object != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: swapHeldItem");
        }
        if ((n & 2) != 0) {
            bl = true;
        }
        return pokemon.swapHeldItem(itemStack, bl);
    }

    @NotNull
    public final ItemStack removeHeldItem() {
        ItemStack itemStack = ItemStack.f_41583_;
        Intrinsics.checkNotNullExpressionValue((Object)itemStack, (String)"EMPTY");
        return Pokemon.swapHeldItem$default(this, itemStack, false, 2, null);
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public final CompoundTag saveToNBT(@NotNull CompoundTag nbt) {
        void it;
        String it2;
        Collection collection;
        Iterable $this$mapTo$iv$iv;
        PersistentStatusContainer persistentStatusContainer;
        Intrinsics.checkNotNullParameter((Object)nbt, (String)"nbt");
        nbt.m_128359_("CobblemonVersion", "1.5.2");
        nbt.m_128362_("UUID", this.uuid);
        nbt.m_128359_("Species", this.species.getResourceIdentifier().toString());
        MutableComponent mutableComponent = this.nickname;
        if (mutableComponent != null) {
            MutableComponent it3 = mutableComponent;
            boolean bl = false;
            nbt.m_128359_("Nickname", Component.Serializer.m_130703_((Component)((Component)it3)));
        }
        nbt.m_128359_("FormId", this.form.formOnlyShowdownId());
        nbt.m_128405_("Experience", this.experience);
        nbt.m_128376_("Level", (short)this.level);
        nbt.m_128376_("Friendship", (short)this.friendship);
        nbt.m_128359_("Gender", this.gender.name());
        nbt.m_128376_("Health", (short)this.currentHealth);
        nbt.m_128365_("IVs", (Tag)this.ivs.saveToNBT(new CompoundTag()));
        nbt.m_128365_("EVs", (Tag)this.evs.saveToNBT(new CompoundTag()));
        nbt.m_128365_("MoveSet", (Tag)this.moveSet.getNBT());
        nbt.m_128350_("ScaleModifier", this.scaleModifier);
        nbt.m_128379_("Shiny", this.shiny);
        CompoundTag abilityNBT = this.ability.saveToNBT(new CompoundTag());
        nbt.m_128365_("Ability", (Tag)abilityNBT);
        CompoundTag compoundTag = this.state.writeToNBT(new CompoundTag());
        if (compoundTag != null) {
            CompoundTag it4 = compoundTag;
            boolean bl = false;
            nbt.m_128365_("State", (Tag)it4);
        }
        if ((persistentStatusContainer = this.status) != null && (persistentStatusContainer = persistentStatusContainer.saveToNBT(new CompoundTag())) != null) {
            PersistentStatusContainer it5 = persistentStatusContainer;
            boolean bl = false;
            nbt.m_128365_("Status", (Tag)it5);
        }
        nbt.m_128359_("CaughtBall", this.caughtBall.getName().toString());
        nbt.m_128405_("FaintedTimer", this.faintedTimer);
        nbt.m_128405_("HealingTimer", this.healTimer);
        nbt.m_128365_("BenchedMoves", (Tag)this.benchedMoves.saveToNBT(new ListTag()));
        nbt.m_128365_("Evolutions", this.getEvolutionProxy().saveToNBT());
        Iterable $this$map$iv = this.customProperties;
        boolean $i$f$map = false;
        Iterable it5 = $this$map$iv;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv, (int)10));
        boolean $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            CustomPokemonProperty customPokemonProperty = (CustomPokemonProperty)item$iv$iv;
            collection = destination$iv$iv;
            boolean bl = false;
            collection.add(it2.asString());
        }
        $this$map$iv = (List)destination$iv$iv;
        $i$f$map = false;
        $this$mapTo$iv$iv = $this$map$iv;
        destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv, (int)10));
        $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            it2 = (String)item$iv$iv;
            collection = destination$iv$iv;
            boolean bl = false;
            collection.add(StringTag.m_129297_((String)it2));
        }
        List propertyList = (List)destination$iv$iv;
        Iterable $i$f$map2 = $this$map$iv = new ListTag();
        String string = "PokemonData";
        collection = nbt;
        boolean bl = false;
        it.addAll((Collection)propertyList);
        Unit unit = Unit.INSTANCE;
        collection.m_128365_(string, (Tag)$this$map$iv);
        nbt.m_128359_("Nature", this.nature.getName().toString());
        Nature nature = this.mintedNature;
        if (nature != null) {
            Nature it6 = nature;
            boolean bl2 = false;
            nbt.m_128359_("MintedNature", it6.getName().toString());
        }
        Iterable $this$forEach$iv = this.features;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            SpeciesFeature it7 = (SpeciesFeature)element$iv;
            boolean bl3 = false;
            it7.saveToNBT(nbt);
        }
        if (!this.heldItem.m_41619_()) {
            nbt.m_128365_("HeldItem", (Tag)this.heldItem.m_41739_(new CompoundTag()));
        }
        nbt.m_128365_("PersistentData", (Tag)this.persistentData);
        if (this.tetheringId != null) {
            nbt.m_128362_("TetheringId", this.tetheringId);
        }
        nbt.m_128359_("TeraType", this.teraType.getId().toString());
        nbt.m_128405_("DmaxLevel", this.dmaxLevel);
        nbt.m_128379_("GmaxFactor", this.gmaxFactor);
        nbt.m_128379_("Tradeable", this.tradeable);
        if (this.originalTrainer != null) {
            nbt.m_128359_("PokemonOriginalTrainer", this.originalTrainer);
        }
        nbt.m_128359_("PokemonOriginalTrainerType", this.originalTrainerType.name());
        return nbt;
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public final Pokemon loadFromNBT(@NotNull CompoundTag nbt) {
        CharSequence charSequence;
        Object object;
        Pokemon pokemon;
        CharSequence charSequence2;
        Object it;
        boolean bl;
        Pokemon pokemon2;
        block34: {
            block33: {
                void it2;
                block30: {
                    Class<? extends PokemonState> clazz;
                    block32: {
                        block31: {
                            FormData formData;
                            Object v6;
                            Object object2;
                            block29: {
                                MutableComponent mutableComponent;
                                Intrinsics.checkNotNullParameter((Object)nbt, (String)"nbt");
                                Object it3 = object2 = nbt.m_128461_("CobblemonVersion");
                                boolean bl2 = false;
                                Intrinsics.checkNotNullExpressionValue((Object)it3, (String)"it");
                                Object object3 = !StringsKt.isBlank((CharSequence)((CharSequence)it3)) ? object2 : null;
                                if (object3 == null) {
                                    object3 = "1.1.1";
                                }
                                String version = object3;
                                this.uuid = nbt.m_128342_("UUID");
                                try {
                                    String string = nbt.m_128461_("Species");
                                    Intrinsics.checkNotNullExpressionValue((Object)string, (String)"nbt.getString(DataKeys.POKEMON_SPECIES_IDENTIFIER)");
                                    String rawID = StringsKt.replace$default((String)string, (String)"pokemonCobblemon", (String)"cobblemon", (boolean)false, (int)4, null);
                                    Species species = PokemonSpecies.INSTANCE.getByIdentifier(new ResourceLocation(rawID));
                                    if (species == null) {
                                        throw new InvalidSpeciesException(new ResourceLocation(rawID));
                                    }
                                    this.setSpecies(species);
                                }
                                catch (ResourceLocationException e) {
                                    throw new IllegalStateException("Failed to read a species identifier from NBT");
                                }
                                it3 = object2 = nbt.m_128461_("Nickname");
                                pokemon2 = this;
                                boolean bl3 = false;
                                Intrinsics.checkNotNullExpressionValue((Object)it3, (String)"it");
                                bl = !StringsKt.isBlank((CharSequence)((CharSequence)it3));
                                Pokemon pokemon3 = pokemon2;
                                Object object4 = bl ? object2 : null;
                                if (object4 != null) {
                                    it3 = object4;
                                    pokemon2 = pokemon3;
                                    boolean bl4 = false;
                                    mutableComponent = Component.Serializer.m_130701_((String)it3);
                                    pokemon3 = pokemon2;
                                } else {
                                    mutableComponent = null;
                                }
                                pokemon3.setNickname(mutableComponent);
                                object2 = this.species.getForms();
                                pokemon2 = this;
                                it3 = object2;
                                Iterator bl4 = it3.iterator();
                                while (bl4.hasNext()) {
                                    Object t = bl4.next();
                                    it = (FormData)t;
                                    boolean bl5 = false;
                                    if (!Intrinsics.areEqual((Object)((FormData)it).formOnlyShowdownId(), (Object)nbt.m_128461_("FormId"))) continue;
                                    v6 = t;
                                    break block29;
                                }
                                v6 = null;
                            }
                            if ((formData = (FormData)v6) == null) {
                                formData = this.species.getStandardForm();
                            }
                            pokemon2.setForm(formData);
                            this.setLevel(nbt.m_128448_("Level"));
                            object2 = nbt.m_128451_("Experience");
                            int it4 = ((Number)object2).intValue();
                            pokemon2 = this;
                            boolean bl6 = false;
                            bl = this.getExperienceGroup().getLevel(it4) == this.level;
                            Object object5 = bl ? object2 : null;
                            pokemon2.setExperience$common(object5 != null ? ((Integer)object5).intValue() : this.getExperienceGroup().getExperience(this.level));
                            this.setFriendship(RangesKt.coerceIn((int)nbt.m_128448_("Friendship"), (int)0, (int)(this.isClient ? Integer.MAX_VALUE : Cobblemon.INSTANCE.getConfig().getMaxPokemonFriendship())));
                            Object it22 = object2 = nbt.m_128461_("Gender");
                            pokemon2 = this;
                            boolean bl7 = false;
                            Intrinsics.checkNotNullExpressionValue((Object)it22, (String)"it");
                            bl = !StringsKt.isBlank((CharSequence)((CharSequence)it22));
                            Object object6 = bl ? object2 : null;
                            if (object6 == null) {
                                object6 = "MALE";
                            }
                            pokemon2.setGender(Gender.valueOf((String)object6));
                            this.setCurrentHealth(nbt.m_128448_("Health"));
                            CompoundTag compoundTag = nbt.m_128469_("IVs");
                            Intrinsics.checkNotNullExpressionValue((Object)compoundTag, (String)"nbt.getCompound(DataKeys.POKEMON_IVS)");
                            this.ivs.loadFromNBT(compoundTag);
                            CompoundTag compoundTag2 = nbt.m_128469_("EVs");
                            Intrinsics.checkNotNullExpressionValue((Object)compoundTag2, (String)"nbt.getCompound(DataKeys.POKEMON_EVS)");
                            this.evs.loadFromNBT(compoundTag2);
                            this.moveSet.loadFromNBT(nbt);
                            this.scaleModifier = nbt.m_128457_("ScaleModifier");
                            if (nbt.m_128425_("Ability", 10)) {
                                CompoundTag compoundTag3 = nbt.m_128469_("Ability");
                                Intrinsics.checkNotNullExpressionValue((Object)compoundTag3, (String)"nbt.getCompound(DataKeys.POKEMON_ABILITY)");
                                this.ability.loadFromNBT(compoundTag3);
                            }
                            this.setShiny(nbt.m_128471_("Shiny"));
                            if (!nbt.m_128441_("State")) break block30;
                            CompoundTag stateNBT = nbt.m_128469_("State");
                            String type = stateNBT.m_128461_("StateType");
                            Class<? extends PokemonState> clazz2 = PokemonState.Companion.getStates().get(type);
                            clazz = clazz2;
                            if (clazz == null || (clazz = clazz.getDeclaredConstructor(new Class[0])) == null || (clazz = (PokemonState)((Constructor)((Object)clazz)).newInstance(new Object[0])) == null) break block31;
                            Intrinsics.checkNotNullExpressionValue((Object)stateNBT, (String)"stateNBT");
                            if ((clazz = ((PokemonState)((Object)clazz)).readFromNBT(stateNBT)) != null) break block32;
                        }
                        clazz = new InactivePokemonState();
                    }
                    this.setState((PokemonState)((Object)clazz));
                }
                if (nbt.m_128441_("Status")) {
                    CompoundTag statusNBT = nbt.m_128469_("Status");
                    Intrinsics.checkNotNullExpressionValue((Object)statusNBT, (String)"statusNBT");
                    this.setStatus(PersistentStatusContainer.Companion.loadFromNBT(statusNBT));
                }
                this.setFaintedTimer(nbt.m_128451_("FaintedTimer"));
                this.setHealTimer(nbt.m_128451_("HealingTimer"));
                String ballName = nbt.m_128461_("CaughtBall");
                PokeBall pokeBall = PokeBalls.INSTANCE.getPokeBall(new ResourceLocation(ballName));
                if (pokeBall == null) {
                    pokeBall = PokeBalls.INSTANCE.getPOKE_BALL();
                }
                this.setCaughtBall(pokeBall);
                ListTag listTag = nbt.m_128437_("BenchedMoves", 10);
                Intrinsics.checkNotNullExpressionValue((Object)listTag, (String)"nbt.getList(DataKeys.BEN\u2026S, COMPOUND_TYPE.toInt())");
                this.benchedMoves.loadFromNBT(listTag);
                ListTag propertiesList = nbt.m_128437_("PokemonData", 8);
                Intrinsics.checkNotNullExpressionValue((Object)propertiesList, (String)"propertiesList");
                PokemonProperties properties2 = PokemonProperties.Companion.parse$default(PokemonProperties.Companion, CollectionsKt.joinToString$default((Iterable)((Iterable)propertiesList), (CharSequence)" ", null, null, (int)0, null, (Function1)loadFromNBT.properties.1.INSTANCE, (int)30, null), " ", null, 4, null);
                this.customProperties.clear();
                this.customProperties.addAll((Collection<CustomPokemonProperty>)properties2.getCustomProperties());
                Iterable $this$forEach$iv = SpeciesFeatures.INSTANCE.getFeaturesFor(this.species);
                boolean $i$f$forEach = false;
                for (Object element$iv : $this$forEach$iv) {
                    Object feature;
                    SpeciesFeatureProvider it5 = (SpeciesFeatureProvider)element$iv;
                    boolean bl8 = false;
                    if (it5.invoke(nbt) == null) continue;
                    this.features.removeIf(arg_0 -> Pokemon.loadFromNBT$lambda$34$lambda$33((Function1)new Function1<SpeciesFeature, Boolean>(feature){
                        final /* synthetic */ SpeciesFeature $feature;
                        {
                            this.$feature = $feature;
                            super(1);
                        }

                        @NotNull
                        public final Boolean invoke(@NotNull SpeciesFeature it) {
                            Intrinsics.checkNotNullParameter((Object)it, (String)"it");
                            return Intrinsics.areEqual((Object)it.getName(), (Object)this.$feature.getName());
                        }
                    }, arg_0));
                    this.features.add((SpeciesFeature)feature);
                }
                charSequence2 = nbt.m_128461_("Nature");
                it = charSequence2;
                pokemon2 = this;
                boolean $i$a$-takeIf-Pokemon$loadFromNBT$82 = false;
                Intrinsics.checkNotNullExpressionValue((Object)it, (String)"it");
                bl = !StringsKt.isBlank((CharSequence)((CharSequence)it));
                pokemon = pokemon2;
                object = bl ? charSequence2 : null;
                if (object == null) break block33;
                String $i$a$-takeIf-Pokemon$loadFromNBT$82 = object;
                pokemon2 = pokemon;
                boolean bl9 = false;
                Nature nature = Natures.INSTANCE.getNature(new ResourceLocation((String)it2));
                Intrinsics.checkNotNull((Object)nature);
                pokemon = pokemon2;
                object = nature;
                if (nature != null) break block34;
            }
            object = Natures.INSTANCE.getRandomNature();
        }
        pokemon.setNature((Nature)object);
        if (nbt.m_128441_("MintedNature")) {
            Nature nature;
            charSequence2 = nbt.m_128461_("MintedNature");
            it = charSequence2;
            pokemon2 = this;
            boolean bl10 = false;
            Intrinsics.checkNotNullExpressionValue((Object)it, (String)"it");
            bl = !StringsKt.isBlank((CharSequence)((CharSequence)it));
            Pokemon pokemon4 = pokemon2;
            CharSequence charSequence3 = bl ? charSequence2 : null;
            if (charSequence3 != null) {
                it = charSequence3;
                pokemon2 = pokemon4;
                boolean bl11 = false;
                nature = Natures.INSTANCE.getNature(new ResourceLocation((String)it));
                pokemon4 = pokemon2;
            } else {
                nature = null;
            }
            pokemon4.setMintedNature(nature);
        }
        this.updateAspects();
        this.updateForm();
        Tag tag = nbt.m_128423_("Evolutions");
        if (tag != null) {
            Tag tag2 = tag;
            boolean bl12 = false;
            this.getEvolutionProxy().loadFromNBT(tag2);
        }
        if (nbt.m_128441_("HeldItem")) {
            ItemStack itemStack = ItemStack.m_41712_((CompoundTag)nbt.m_128469_("HeldItem"));
            Intrinsics.checkNotNullExpressionValue((Object)itemStack, (String)"fromNbt(nbt.getCompound(DataKeys.HELD_ITEM))");
            this.heldItem = itemStack;
        }
        CompoundTag compoundTag = nbt.m_128469_("PersistentData");
        Intrinsics.checkNotNullExpressionValue((Object)compoundTag, (String)"nbt.getCompound(DataKeys.POKEMON_PERSISTENT_DATA)");
        this.persistentData = compoundTag;
        this.setTetheringId(nbt.m_128403_("TetheringId") ? nbt.m_128342_("TetheringId") : null);
        String string = nbt.m_128461_("TeraType");
        Intrinsics.checkNotNullExpressionValue((Object)string, (String)"nbt.getString(DataKeys.POKEMON_TERA_TYPE)");
        TeraType teraType = TeraTypes.get(ResourceLocationExtensionsKt.asIdentifierDefaultingNamespace$default(string, null, 1, null));
        if (teraType != null) {
            it = teraType;
            boolean bl13 = false;
            this.setTeraType((TeraType)it);
        }
        this.setDmaxLevel(nbt.m_128451_("DmaxLevel"));
        this.setGmaxFactor(nbt.m_128471_("GmaxFactor"));
        this.setTradeable(nbt.m_128441_("Tradeable") ? nbt.m_128471_("Tradeable") : true);
        Pokemon pokemon5 = this;
        charSequence2 = nbt.m_128461_("PokemonOriginalTrainerType");
        if (charSequence2.length() == 0) {
            pokemon2 = pokemon5;
            boolean bl14 = false;
            charSequence = "NONE";
            pokemon5 = pokemon2;
        } else {
            charSequence = charSequence2;
        }
        Intrinsics.checkNotNullExpressionValue((Object)charSequence, (String)"nbt.getString(DataKeys.P\u2026alTrainerType.NONE.name }");
        pokemon5.originalTrainerType = OriginalTrainerType.valueOf((String)charSequence);
        this.originalTrainer = nbt.m_128441_("PokemonOriginalTrainer") ? nbt.m_128461_("PokemonOriginalTrainer") : null;
        this.refreshOriginalTrainer();
        return this;
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public final JsonObject saveToJSON(@NotNull JsonObject json) {
        String it;
        Collection collection;
        Iterable $this$mapTo$iv$iv;
        PersistentStatusContainer persistentStatusContainer;
        MutableComponent it2;
        Intrinsics.checkNotNullParameter((Object)json, (String)"json");
        json.addProperty("CobblemonVersion", "1.5.2");
        json.addProperty("UUID", this.uuid.toString());
        json.addProperty("Species", this.species.getResourceIdentifier().toString());
        MutableComponent mutableComponent = this.nickname;
        if (mutableComponent != null) {
            it2 = mutableComponent;
            boolean bl = false;
            json.add("Nickname", Component.Serializer.m_130716_((Component)((Component)it2)));
        }
        json.addProperty("FormId", this.form.formOnlyShowdownId());
        json.addProperty("Experience", (Number)this.experience);
        json.addProperty("Level", (Number)this.level);
        json.addProperty("Friendship", (Number)this.friendship);
        json.addProperty("Health", (Number)this.currentHealth);
        json.addProperty("Gender", this.gender.name());
        json.add("IVs", (JsonElement)this.ivs.saveToJSON(new JsonObject()));
        json.add("EVs", (JsonElement)this.evs.saveToJSON(new JsonObject()));
        json.add("MoveSet", (JsonElement)this.moveSet.saveToJSON(new JsonObject()));
        json.addProperty("ScaleModifier", (Number)Float.valueOf(this.scaleModifier));
        json.add("Ability", (JsonElement)this.ability.saveToJSON(new JsonObject()));
        json.addProperty("Shiny", Boolean.valueOf(this.shiny));
        JsonObject jsonObject = this.state.writeToJSON(new JsonObject());
        if (jsonObject != null) {
            it2 = jsonObject;
            boolean bl = false;
            json.add("State", (JsonElement)it2);
        }
        if ((persistentStatusContainer = this.status) != null && (persistentStatusContainer = persistentStatusContainer.saveToJSON(new JsonObject())) != null) {
            PersistentStatusContainer it3 = persistentStatusContainer;
            boolean bl = false;
            json.add("Status", (JsonElement)it3);
        }
        json.addProperty("CaughtBall", this.caughtBall.getName().toString());
        json.add("BenchedMoves", (JsonElement)this.benchedMoves.saveToJSON(new JsonArray()));
        json.addProperty("FaintedTimer", (Number)this.faintedTimer);
        json.addProperty("HealingTimer", (Number)this.healTimer);
        json.add("Evolutions", this.getEvolutionProxy().saveToJson());
        Iterable $this$map$iv = this.customProperties;
        boolean $i$f$map = false;
        Iterable it3 = $this$map$iv;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv, (int)10));
        boolean $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            CustomPokemonProperty customPokemonProperty = (CustomPokemonProperty)item$iv$iv;
            collection = destination$iv$iv;
            boolean bl = false;
            collection.add(it.asString());
        }
        $this$map$iv = (List)destination$iv$iv;
        $i$f$map = false;
        $this$mapTo$iv$iv = $this$map$iv;
        destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv, (int)10));
        $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            it = (String)item$iv$iv;
            collection = destination$iv$iv;
            boolean bl = false;
            collection.add(new JsonPrimitive(it));
        }
        List propertyList = (List)destination$iv$iv;
        Iterable $i$f$map2 = $this$map$iv = new JsonArray();
        String string = "PokemonData";
        collection = json;
        boolean bl = false;
        Iterable $this$forEach$iv = propertyList;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            void it4;
            JsonElement p0 = (JsonElement)element$iv;
            boolean bl2 = false;
            it4.add(p0);
        }
        Unit unit = Unit.INSTANCE;
        collection.add(string, (JsonElement)$this$map$iv);
        json.addProperty("Nature", this.nature.getName().toString());
        Nature nature = this.mintedNature;
        if (nature != null) {
            Nature it5 = nature;
            boolean bl3 = false;
            json.addProperty("MintedNature", it5.getName().toString());
        }
        Iterable $this$forEach$iv2 = this.features;
        boolean $i$f$forEach2 = false;
        for (Object element$iv : $this$forEach$iv2) {
            SpeciesFeature it6 = (SpeciesFeature)element$iv;
            boolean bl4 = false;
            it6.saveToJSON(json);
        }
        if (!this.heldItem.m_41619_()) {
            ItemStack.f_41582_.encodeStart((DynamicOps)JsonOps.INSTANCE, (Object)this.heldItem).result().ifPresent(arg_0 -> Pokemon.saveToJSON$lambda$50((Function1)new Function1<JsonElement, Unit>(json){
                final /* synthetic */ JsonObject $json;
                {
                    this.$json = $json;
                    super(1);
                }

                public final void invoke(@NotNull JsonElement it) {
                    Intrinsics.checkNotNullParameter((Object)it, (String)"it");
                    this.$json.add("HeldItem", it);
                }
            }, arg_0));
        }
        json.add("PersistentData", (JsonElement)Dynamic.convert((DynamicOps)((DynamicOps)NbtOps.f_128958_), (DynamicOps)((DynamicOps)JsonOps.INSTANCE), (Object)this.persistentData));
        UUID tetheringId = this.tetheringId;
        if (tetheringId != null) {
            json.addProperty("TetheringId", tetheringId.toString());
        }
        json.addProperty("TeraType", this.teraType.getId().toString());
        json.addProperty("DmaxLevel", (Number)this.dmaxLevel);
        json.addProperty("GmaxFactor", Boolean.valueOf(this.gmaxFactor));
        json.addProperty("Tradeable", Boolean.valueOf(this.tradeable));
        json.addProperty("PokemonOriginalTrainerType", this.originalTrainerType.name());
        if (this.originalTrainer != null) {
            json.addProperty("PokemonOriginalTrainer", this.originalTrainer);
        }
        return json;
    }

    /*
     * Unable to fully structure code
     * Could not resolve type clashes
     */
    @NotNull
    public final Pokemon loadFromJSON(@NotNull JsonObject json) {
        block57: {
            block56: {
                block53: {
                    block55: {
                        block54: {
                            block50: {
                                block52: {
                                    block51: {
                                        Intrinsics.checkNotNullParameter((Object)json, (String)"json");
                                        v0 = json.get("CobblemonVersion");
                                        v1 = v0 != null ? v0.getAsString() : null;
                                        if (v1 == null) {
                                            v1 = "1.1.1";
                                        }
                                        version = v1;
                                        this.uuid = UUID.fromString(json.get("UUID").getAsString());
                                        try {
                                            v2 = json.get("Species").getAsString();
                                            Intrinsics.checkNotNullExpressionValue((Object)v2, (String)"json.get(DataKeys.POKEMO\u2026CIES_IDENTIFIER).asString");
                                            rawID = StringsKt.replace$default((String)v2, (String)"pokemonCobblemon", (String)"cobblemon", (boolean)false, (int)4, null);
                                            v3 = PokemonSpecies.INSTANCE.getByIdentifier(new ResourceLocation(rawID));
                                            if (v3 == null) {
                                                throw new InvalidSpeciesException(new ResourceLocation(rawID));
                                            }
                                            this.setSpecies(v3);
                                        }
                                        catch (ResourceLocationException e) {
                                            throw new IllegalStateException("Failed to deserialize a species identifier");
                                        }
                                        v4 = this;
                                        if (!Intrinsics.areEqual((Object)version, (Object)"1.4.0")) break block51;
                                        var16_5 = v4;
                                        try {
                                            v4 = var16_5;
                                            v5 /* !! */  = json.get("Nickname");
                                            if (v5 /* !! */  == null || (v5 /* !! */  = v5 /* !! */ .getAsString()) == null) ** GOTO lbl-1000
                                            var7_10 /* !! */  = var6_6 /* !! */  = v5 /* !! */ ;
                                            var17_12 = v4;
                                            $i$a$-takeIf-Pokemon$loadFromJSON$1 = false;
                                            var18_19 = !StringsKt.isBlank((CharSequence)((CharSequence)it /* !! */ ));
                                            v4 = var17_12;
                                            v5 /* !! */  = var18_19 != false ? var6_6 /* !! */  : null;
                                            if (v5 /* !! */  != null) {
                                                it /* !! */  = v5 /* !! */ ;
                                                var17_12 = v4;
                                                $i$a$-let-Pokemon$loadFromJSON$2 = false;
                                                v6 = Component.Serializer.m_130701_((String)it /* !! */ );
                                                v4 = var17_12;
                                            } else lbl-1000:
                                            // 2 sources

                                            {
                                                v6 = null;
                                            }
                                            e = v6;
                                        }
                                        catch (UnsupportedOperationException var4_20) {
                                            v4 = var16_5;
                                            v7 = json.get("Nickname");
                                            if (v7 != null) {
                                                it /* !! */  = v7;
                                                var16_5 = v4;
                                                $i$a$-let-Pokemon$loadFromJSON$3 = false;
                                                v8 = Component.Serializer.m_130691_((JsonElement)it /* !! */ );
                                                v4 = var16_5;
                                            } else {
                                                v8 = null;
                                            }
                                            e = v8;
                                        }
                                        v9 = e;
                                        break block52;
                                    }
                                    v10 = json.get("Nickname");
                                    if (v10 != null) {
                                        var5_22 = v10;
                                        var16_5 = v4;
                                        $i$a$-let-Pokemon$loadFromJSON$4 = false;
                                        v9 = Component.Serializer.m_130691_((JsonElement)it);
                                        v4 = var16_5;
                                    } else {
                                        v9 = null;
                                    }
                                }
                                v4.setNickname(v9);
                                e = this.species.getForms();
                                var16_5 = this;
                                it = e;
                                for (JsonElement it : it) {
                                    it = (FormData)it /* !! */ ;
                                    $i$a$-find-Pokemon$loadFromJSON$5 = false;
                                    if (!Intrinsics.areEqual((Object)it.formOnlyShowdownId(), (Object)json.get("FormId").getAsString())) continue;
                                    v11 /* !! */  = it /* !! */ ;
                                    break block50;
                                }
                                v11 /* !! */  = null;
                            }
                            if ((v12 = (FormData)v11 /* !! */ ) == null) {
                                v12 = this.species.getStandardForm();
                            }
                            var16_5.setForm(v12);
                            this.setLevel(json.get("Level").getAsInt());
                            e = json.get("Experience").getAsInt();
                            it = ((Number)e).intValue();
                            var16_5 = this;
                            $i$a$-takeIf-Pokemon$loadFromJSON$6 = false;
                            var17_13 = this.getExperienceGroup().getLevel(it) == this.level;
                            v13 = var17_13 != false ? e : null;
                            var16_5.setExperience$common(v13 != null ? v13.intValue() : this.getExperienceGroup().getExperience(this.level));
                            this.setFriendship(RangesKt.coerceIn((int)json.get("Friendship").getAsInt(), (int)0, (int)(this.isClient != false ? 0x7FFFFFFF : Cobblemon.INSTANCE.getConfig().getMaxPokemonFriendship())));
                            this.setCurrentHealth(json.get("Health").getAsInt());
                            v14 = json.get("Gender");
                            v15 = v14 != null ? v14.getAsString() : null;
                            if (v15 == null) {
                                v15 = "male";
                            }
                            this.setGender(Gender.valueOf(v15));
                            v16 = json.getAsJsonObject("IVs");
                            Intrinsics.checkNotNullExpressionValue((Object)v16, (String)"json.getAsJsonObject(DataKeys.POKEMON_IVS)");
                            this.ivs.loadFromJSON(v16);
                            v17 = json.getAsJsonObject("EVs");
                            Intrinsics.checkNotNullExpressionValue((Object)v17, (String)"json.getAsJsonObject(DataKeys.POKEMON_EVS)");
                            this.evs.loadFromJSON(v17);
                            v18 = json.get("MoveSet").getAsJsonObject();
                            Intrinsics.checkNotNullExpressionValue((Object)v18, (String)"json.get(DataKeys.POKEMON_MOVESET).asJsonObject");
                            this.moveSet.loadFromJSON(v18);
                            this.scaleModifier = json.get("ScaleModifier").getAsFloat();
                            if (json.has("Ability") && json.get("Ability").isJsonObject()) {
                                v19 = json.getAsJsonObject("Ability");
                                Intrinsics.checkNotNullExpressionValue((Object)v19, (String)"json.getAsJsonObject(DataKeys.POKEMON_ABILITY)");
                                this.ability.loadFromJSON(v19);
                            }
                            this.setShiny(json.get("Shiny").getAsBoolean());
                            if (!json.has("State")) break block53;
                            stateJson = json.get("State").getAsJsonObject();
                            v20 = stateJson.get("StateType");
                            v21 = type = v20 != null ? v20.getAsString() : null;
                            if (v21 != null) {
                                it /* !! */  = v21;
                                $i$a$-let-Pokemon$loadFromJSON$clazz$1 = false;
                                v22 = PokemonState.Companion.getStates().get(it /* !! */ );
                            } else {
                                v22 = null;
                            }
                            if ((v23 = (clazz = v22)) == null || (v23 = v23.getDeclaredConstructor(new Class[0])) == null || (v23 = (PokemonState)v23.newInstance(new Object[0])) == null) break block54;
                            Intrinsics.checkNotNullExpressionValue((Object)stateJson, (String)"stateJson");
                            if ((v23 = v23.readFromJSON(stateJson)) != null) break block55;
                        }
                        v23 = new InactivePokemonState();
                    }
                    this.setState((PokemonState)v23);
                }
                if (json.has("Status")) {
                    statusJson = json.get("Status").getAsJsonObject();
                    Intrinsics.checkNotNullExpressionValue((Object)statusJson, (String)"statusJson");
                    this.setStatus(PersistentStatusContainer.Companion.loadFromJSON(statusJson));
                }
                if ((v24 = PokeBalls.INSTANCE.getPokeBall(new ResourceLocation(ballName = json.get("CaughtBall").getAsString()))) == null) {
                    v24 = PokeBalls.INSTANCE.getPOKE_BALL();
                }
                this.setCaughtBall(v24);
                v25 = json.get("BenchedMoves");
                v26 /* !! */  = v25 != null ? v25.getAsJsonArray() : null;
                if (v26 /* !! */  == null) {
                    v26 /* !! */  = new JsonArray();
                }
                this.benchedMoves.loadFromJSON(v26 /* !! */ );
                this.setFaintedTimer(json.get("FaintedTimer").getAsInt());
                this.setHealTimer(json.get("HealingTimer").getAsInt());
                v27 = json.getAsJsonArray("PokemonData");
                if (v27 != null) {
                    $this$map$iv = (Iterable)v27;
                    $i$f$map = false;
                    $i$a$-find-Pokemon$loadFromJSON$5 = $this$map$iv;
                    destination$iv$iv = new ArrayList<E>(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv, (int)10));
                    $i$f$mapTo = false;
                    for (T item$iv$iv : $this$mapTo$iv$iv) {
                        var14_38 = (JsonElement)item$iv$iv;
                        var16_5 = destination$iv$iv;
                        $i$a$-map-Pokemon$loadFromJSON$propertyList$1 = false;
                        var16_5.add(it.getAsString());
                    }
                    v28 = (List)destination$iv$iv;
                } else {
                    v28 = CollectionsKt.emptyList();
                }
                propertyList = v28;
                properties = PokemonProperties.Companion.parse$default(PokemonProperties.Companion, CollectionsKt.joinToString$default((Iterable)propertyList, (CharSequence)" ", null, null, (int)0, null, null, (int)62, null), " ", null, 4, null);
                this.customProperties.clear();
                this.customProperties.addAll((Collection<CustomPokemonProperty>)properties.getCustomProperties());
                $this$forEach$iv = SpeciesFeatures.INSTANCE.getFeaturesFor(this.species);
                $i$f$forEach = false;
                for (T element$iv : $this$forEach$iv) {
                    it = (SpeciesFeatureProvider)element$iv;
                    $i$a$-forEach-Pokemon$loadFromJSON$7 = false;
                    if (it.invoke(json) == null) continue;
                    this.features.removeIf((Predicate<Object>)LambdaMetafactory.metafactory(null, null, null, (Ljava/lang/Object;)Z, loadFromJSON$lambda$60$lambda$59(kotlin.jvm.functions.Function1 java.lang.Object ), (Ljava/lang/Object;)Z)((Function1)((Function1)new Function1<SpeciesFeature, Boolean>(feature){
                        final /* synthetic */ SpeciesFeature $feature;
                        {
                            this.$feature = $feature;
                            super(1);
                        }

                        @NotNull
                        public final Boolean invoke(@NotNull SpeciesFeature it) {
                            Intrinsics.checkNotNullParameter((Object)it, (String)"it");
                            return Intrinsics.areEqual((Object)it.getName(), (Object)this.$feature.getName());
                        }
                    })));
                    this.features.add((SpeciesFeature)feature);
                }
                v29 = this;
                v30 = json.get("Nature").getAsString();
                if (v30 == null) break block56;
                element$iv = v30;
                var16_5 = v29;
                $i$a$-let-Pokemon$loadFromJSON$8 = false;
                v31 = Natures.INSTANCE.getNature(new ResourceLocation((String)it));
                Intrinsics.checkNotNull((Object)v31);
                v29 = var16_5;
                v30 = v31;
                if (v31 != null) break block57;
            }
            v30 = Natures.INSTANCE.getRandomNature();
        }
        v29.setNature((Nature)v30);
        if (json.has("MintedNature")) {
            v32 = this;
            v33 = json.get("MintedNature").getAsString();
            if (v33 != null) {
                $i$f$map = v33;
                var16_5 = v32;
                $i$a$-let-Pokemon$loadFromJSON$9 = false;
                v34 = Natures.INSTANCE.getNature(new ResourceLocation((String)it /* !! */ ));
                v32 = var16_5;
            } else {
                v34 = null;
            }
            v32.setMintedNature(v34);
        }
        this.updateAspects();
        this.updateForm();
        v35 = json.get("Evolutions");
        if (v35 != null) {
            it /* !! */  = v35;
            $i$a$-let-Pokemon$loadFromJSON$10 = false;
            this.getEvolutionProxy().loadFromJson(it /* !! */ );
        }
        if (json.has("HeldItem")) {
            ItemStack.f_41582_.decode((DynamicOps)JsonOps.INSTANCE, (Object)json.get("HeldItem")).result().ifPresent((Consumer<Object>)LambdaMetafactory.metafactory(null, null, null, (Ljava/lang/Object;)V, loadFromJSON$lambda$64(kotlin.jvm.functions.Function1 java.lang.Object ), (Ljava/lang/Object;)V)((Function1)((Function1)new Function1<Pair<ItemStack, JsonElement>, Unit>(this){
                final /* synthetic */ Pokemon this$0;
                {
                    this.this$0 = $receiver;
                    super(1);
                }

                public final void invoke(@NotNull Pair<ItemStack, JsonElement> it) {
                    Intrinsics.checkNotNullParameter(it, (String)"it");
                    Object object = it.getFirst();
                    Intrinsics.checkNotNullExpressionValue((Object)object, (String)"it.first");
                    Pokemon.access$setHeldItem$p(this.this$0, (ItemStack)object);
                }
            })));
        }
        if (json.has("PersistentData")) {
            v36 = Dynamic.convert((DynamicOps)((DynamicOps)JsonOps.INSTANCE), (DynamicOps)((DynamicOps)NbtOps.f_128958_), (Object)json.get("PersistentData"));
            Intrinsics.checkNotNull((Object)v36, (String)"null cannot be cast to non-null type net.minecraft.nbt.NbtCompound");
            this.persistentData = (CompoundTag)v36;
        }
        if (json.has("TetheringId")) {
            this.setTetheringId(UUID.fromString(json.get("TetheringId").getAsString()));
        }
        if (json.has("TeraType")) {
            v37 = json.get("TeraType").getAsString();
            Intrinsics.checkNotNullExpressionValue((Object)v37, (String)"json.get(DataKeys.POKEMON_TERA_TYPE).asString");
            v38 = TeraTypes.get(ResourceLocationExtensionsKt.asIdentifierDefaultingNamespace$default(v37, null, 1, null));
            if (v38 != null) {
                it /* !! */  = v38;
                $i$a$-let-Pokemon$loadFromJSON$12 = false;
                this.setTeraType((TeraType)it /* !! */ );
            }
        } else {
            this.setTeraType(TeraTypes.forElementalType((ElementalType)CollectionsKt.random((Collection)CollectionsKt.toList(this.form.getTypes()), (Random)((Random)Random.Default))));
        }
        if (json.has("DmaxLevel")) {
            this.setDmaxLevel(json.get("DmaxLevel").getAsInt());
        }
        if (json.has("GmaxFactor")) {
            this.setGmaxFactor(json.get("GmaxFactor").getAsBoolean());
        }
        if (json.has("Tradeable")) {
            this.setTradeable(json.get("Tradeable").getAsBoolean());
        }
        if (json.has("PokemonOriginalTrainerType")) {
            v39 = json.get("PokemonOriginalTrainerType").getAsString();
            Intrinsics.checkNotNullExpressionValue((Object)v39, (String)"json.get(DataKeys.POKEMO\u2026AL_TRAINER_TYPE).asString");
            this.originalTrainerType = OriginalTrainerType.valueOf(v39);
        }
        if (json.has("PokemonOriginalTrainer")) {
            this.originalTrainer = json.get("PokemonOriginalTrainer").getAsString();
        }
        this.refreshOriginalTrainer();
        return this;
    }

    @NotNull
    public final Pokemon clone(boolean useJSON, boolean newUUID) {
        Pokemon pokemon;
        if (useJSON) {
            JsonObject jsonObject = var4_3 = this.saveToJSON(new JsonObject());
            var7_5 = new Pokemon();
            boolean bl = false;
            it.remove("Evolutions");
            v0 = var7_5.loadFromJSON(var4_3);
        } else {
            it = var4_3 = this.saveToNBT(new CompoundTag());
            var7_5 = new Pokemon();
            boolean bl = false;
            it.m_128473_("Evolutions");
            v0 = pokemon = var7_5.loadFromNBT((CompoundTag)var4_3);
        }
        if (newUUID) {
            pokemon.uuid = UUID.randomUUID();
        }
        return pokemon;
    }

    public static /* synthetic */ Pokemon clone$default(Pokemon pokemon, boolean bl, boolean bl2, int n, Object object) {
        if (object != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: clone");
        }
        if ((n & 1) != 0) {
            bl = true;
        }
        if ((n & 2) != 0) {
            bl2 = true;
        }
        return pokemon.clone(bl, bl2);
    }

    @Nullable
    public final ServerPlayer getOwnerPlayer() {
        StoreCoordinates<?> it = this.storeCoordinates.get();
        boolean bl = false;
        if (this.isPlayerOwned()) {
            ServerPlayer serverPlayer;
            MinecraftServer minecraftServer = DistributionUtilsKt.server();
            if (minecraftServer != null && (minecraftServer = minecraftServer.m_6846_()) != null) {
                StoreCoordinates<?> storeCoordinates = it;
                Intrinsics.checkNotNull(storeCoordinates);
                serverPlayer = minecraftServer.m_11259_(storeCoordinates.getStore().getUuid());
            } else {
                serverPlayer = null;
            }
            return serverPlayer;
        }
        return null;
    }

    @Nullable
    public final UUID getOwnerUUID() {
        StoreCoordinates<?> it = this.storeCoordinates.get();
        boolean bl = false;
        if (this.isPlayerOwned()) {
            StoreCoordinates<?> storeCoordinates = it;
            Intrinsics.checkNotNull(storeCoordinates);
            if (storeCoordinates.getStore() instanceof PlayerPartyStore) {
                PokemonStore<?> pokemonStore = it.getStore();
                Intrinsics.checkNotNull(pokemonStore, (String)"null cannot be cast to non-null type bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.party.PlayerPartyStore");
                return ((PlayerPartyStore)pokemonStore).getPlayerUUID();
            }
            return it.getStore().getUuid();
        }
        return null;
    }

    public final boolean belongsTo(@NotNull Player player) {
        boolean bl;
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        StoreCoordinates<?> storeCoordinates = this.storeCoordinates.get();
        if (storeCoordinates != null) {
            StoreCoordinates<?> it = storeCoordinates;
            boolean bl2 = false;
            bl = Intrinsics.areEqual((Object)it.getStore().getUuid(), (Object)player.m_20148_());
        } else {
            bl = false;
        }
        return bl;
    }

    public final boolean isPlayerOwned() {
        boolean bl;
        StoreCoordinates<?> storeCoordinates = this.storeCoordinates.get();
        if (storeCoordinates != null) {
            StoreCoordinates<?> it = storeCoordinates;
            boolean bl2 = false;
            bl = it.getStore() instanceof PlayerPartyStore || it.getStore() instanceof PCStore;
        } else {
            bl = false;
        }
        return bl;
    }

    public final boolean isWild() {
        return this.storeCoordinates.get() == null;
    }

    public final boolean setFriendship(int value2, boolean coerceSafe) {
        int sanitizedAmount;
        int n = sanitizedAmount = coerceSafe ? RangesKt.coerceAtMost((int)Math.abs(value2), (int)Cobblemon.INSTANCE.getConfig().getMaxPokemonFriendship()) : Math.abs(value2);
        if (!this.isClient && !this.isPossibleFriendship(sanitizedAmount)) {
            return false;
        }
        this.setFriendship(sanitizedAmount);
        return true;
    }

    public static /* synthetic */ boolean setFriendship$default(Pokemon pokemon, int n, boolean bl, int n2, Object object) {
        if (object != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: setFriendship");
        }
        if ((n2 & 2) != 0) {
            bl = true;
        }
        return pokemon.setFriendship(n, bl);
    }

    public final boolean incrementFriendship(int amount, boolean coerceSafe) {
        int sanitizedAmount = coerceSafe ? RangesKt.coerceAtMost((int)Math.abs(amount), (int)(Cobblemon.INSTANCE.getConfig().getMaxPokemonFriendship() - this.friendship)) : Math.abs(amount);
        int newValue = this.friendship + sanitizedAmount;
        if (this.isPossibleFriendship(newValue)) {
            this.setFriendship(newValue);
        }
        return this.friendship == newValue;
    }

    public static /* synthetic */ boolean incrementFriendship$default(Pokemon pokemon, int n, boolean bl, int n2, Object object) {
        if (object != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: incrementFriendship");
        }
        if ((n2 & 2) != 0) {
            bl = true;
        }
        return pokemon.incrementFriendship(n, bl);
    }

    public final boolean decrementFriendship(int amount, boolean coerceSafe) {
        int sanitizedAmount = coerceSafe ? RangesKt.coerceAtMost((int)Math.abs(amount), (int)this.friendship) : Math.abs(amount);
        int newValue = this.friendship - sanitizedAmount;
        if (this.isPossibleFriendship(newValue)) {
            this.setFriendship(newValue);
        }
        return this.friendship == newValue;
    }

    public static /* synthetic */ boolean decrementFriendship$default(Pokemon pokemon, int n, boolean bl, int n2, Object object) {
        if (object != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: decrementFriendship");
        }
        if ((n2 & 2) != 0) {
            bl = true;
        }
        return pokemon.decrementFriendship(n, bl);
    }

    public final boolean isPossibleFriendship(int value2) {
        return value2 >= 0 && value2 <= Cobblemon.INSTANCE.getConfig().getMaxPokemonFriendship();
    }

    public final void setOriginalTrainer(@NotNull UUID playerUUID) {
        Intrinsics.checkNotNullParameter((Object)playerUUID, (String)"playerUUID");
        this.originalTrainerType = OriginalTrainerType.PLAYER;
        this.originalTrainer = playerUUID.toString();
    }

    public final void setOriginalTrainer(@NotNull String fakeTrainerName) {
        Intrinsics.checkNotNullParameter((Object)fakeTrainerName, (String)"fakeTrainerName");
        this.originalTrainerType = OriginalTrainerType.NPC;
        this.originalTrainer = fakeTrainerName;
    }

    public final void refreshOriginalTrainer() {
        switch (WhenMappings.$EnumSwitchMapping$0[this.originalTrainerType.ordinal()]) {
            case 1: {
                UUID uUID = UUID.fromString(this.originalTrainer);
                if (uUID != null) {
                    String string;
                    UUID uuid2 = uUID;
                    boolean bl = false;
                    Object object = DistributionUtilsKt.server();
                    if (object != null && (object = object.m_129927_()) != null && (object = object.m_11002_(uuid2)) != null && (object = (GameProfile)((Optional)object).orElse(null)) != null && (object = (string = object.getName())) != null) {
                        Intrinsics.checkNotNullExpressionValue((Object)object, (String)"name");
                        String it = string;
                        boolean bl2 = false;
                        this.setOriginalTrainerName(it);
                    }
                }
                break;
            }
            case 2: {
                this.setOriginalTrainerName(this.originalTrainer);
                break;
            }
            case 3: {
                this.setOriginalTrainerName(null);
            }
        }
    }

    public final void removeOriginalTrainer() {
        this.originalTrainer = null;
        this.originalTrainerType = OriginalTrainerType.NONE;
        this.setOriginalTrainerName(null);
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public final Set<MoveTemplate> getAllAccessibleMoves() {
        void $this$mapTo$iv$iv;
        void $this$map$iv;
        Iterable iterable = this.benchedMoves;
        Set<MoveTemplate> set2 = this.form.getMoves().getLevelUpMovesUpTo(this.level);
        boolean $i$f$map = false;
        void var3_4 = $this$map$iv;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv, (int)10));
        boolean $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            void it;
            BenchedMove benchedMove = (BenchedMove)item$iv$iv;
            Collection collection = destination$iv$iv;
            boolean bl = false;
            collection.add(it.getMoveTemplate());
        }
        return SetsKt.plus((Set)SetsKt.plus(set2, (Iterable)((List)destination$iv$iv)), (Iterable)this.form.getMoves().getEvolutionMoves());
    }

    /*
     * WARNING - void declaration
     */
    public final void updateAspects() {
        if (!this.isClient) {
            void $this$flatMapTo$iv$iv;
            void $this$flatMap$iv;
            Iterable iterable = AspectProvider.Companion.getProviders();
            Pokemon pokemon = this;
            boolean $i$f$flatMap = false;
            void var3_4 = $this$flatMap$iv;
            Collection destination$iv$iv = new ArrayList();
            boolean $i$f$flatMapTo = false;
            for (Object element$iv$iv : $this$flatMapTo$iv$iv) {
                AspectProvider it = (AspectProvider)element$iv$iv;
                boolean bl = false;
                Iterable list$iv$iv = it.provide(this);
                CollectionsKt.addAll((Collection)destination$iv$iv, (Iterable)list$iv$iv);
            }
            pokemon.setAspects(CollectionsKt.toSet((Iterable)((List)destination$iv$iv)));
        }
    }

    public final void updateForm() {
        FormData newForm = this.species.getForm(this.aspects);
        if (!Intrinsics.areEqual((Object)this.form, (Object)newForm)) {
            this.setForm(newForm);
        }
    }

    @NotNull
    public final Pokemon initialize() {
        this.setSpecies(this.species);
        this.checkGender();
        Pokemon.initializeMoveset$default(this, false, 1, null);
        return this;
    }

    @NotNull
    public final ItemStack getLastFlowerFed() {
        return this.lastFlowerFed;
    }

    public final void setLastFlowerFed(@NotNull ItemStack itemStack) {
        Intrinsics.checkNotNullParameter((Object)itemStack, (String)"<set-?>");
        this.lastFlowerFed = itemStack;
    }

    public final void checkGender() {
        boolean reassess = false;
        float f = this.form.getMaleRatio();
        if (!(0.0f <= f ? f <= 1.0f : false) && this.gender != Gender.GENDERLESS) {
            reassess = true;
        } else if (this.form.getMaleRatio() == 0.0f && this.gender != Gender.FEMALE) {
            reassess = true;
        } else if (this.form.getMaleRatio() == 1.0f && this.gender != Gender.MALE) {
            reassess = true;
        } else {
            f = this.form.getMaleRatio();
            boolean bl = 0.0f <= f ? f <= 1.0f : false;
            if (bl && this.gender == Gender.GENDERLESS) {
                reassess = true;
            }
        }
        if (reassess) {
            f = this.form.getMaleRatio();
            this.setGender(!(0.0f <= f ? f <= 1.0f : false) ? Gender.GENDERLESS : (this.form.getMaleRatio() == 1.0f || Random.Default.nextFloat() <= this.form.getMaleRatio() ? Gender.MALE : Gender.FEMALE));
        }
    }

    @NotNull
    public Ability rollAbility() {
        if (this.isClient) {
            return this.ability;
        }
        Ability ability = (Ability)this.form.getAbilities().select(this.species, this.aspects).component1();
        return this.updateAbility(ability.getTemplate().create(false));
    }

    @NotNull
    public Ability updateAbility(@NotNull Ability ability) {
        Intrinsics.checkNotNullParameter((Object)ability, (String)"ability");
        if (this.isClient) {
            return this.ability;
        }
        this.setAbility$common(ability.getForced() ? ability : this.attachAbilityCoordinate(ability));
        return this.ability;
    }

    /*
     * WARNING - void declaration
     */
    protected void attemptAbilityUpdate() {
        Object indexed;
        Object potential;
        List potentials;
        if (this.isClient || this.ability.getForced()) {
            return;
        }
        if (Intrinsics.areEqual((Object)this.ability.getTemplate(), (Object)Abilities.INSTANCE.getDUMMY())) {
            this.rollAbility();
            return;
        }
        List list = potentials = this.form.getAbilities().getMapping().get((Object)this.ability.getPriority());
        Object object = potential = list != null ? (PotentialAbility)CollectionsKt.getOrNull(list, (int)this.ability.getIndex()) : null;
        if (potential == null && potentials != null) {
            for (int i = RangesKt.coerceAtLeast((int)this.ability.getIndex(), (int)0); -1 < i; --i) {
                indexed = (PotentialAbility)CollectionsKt.getOrNull(potentials, (int)i);
                if (indexed == null) continue;
                potential = indexed;
                break;
            }
        }
        if (potential != null) {
            void $this$attemptAbilityUpdate_u24lambda_u2476;
            Ability ability = potential.getTemplate().create(false);
            indexed = ability;
            Pokemon pokemon = this;
            boolean bl = false;
            $this$attemptAbilityUpdate_u24lambda_u2476.setIndex$common(this.ability.getIndex());
            $this$attemptAbilityUpdate_u24lambda_u2476.setPriority$common(this.ability.getPriority());
            pokemon.setAbility$common(ability);
            return;
        }
        this.rollAbility();
    }

    @NotNull
    protected Ability attachAbilityCoordinate(@NotNull Ability ability) {
        PotentialAbility found;
        Object object;
        block8: {
            Object object2;
            block7: {
                List list;
                Object v0;
                block6: {
                    Intrinsics.checkNotNullParameter((Object)ability, (String)"ability");
                    if (this.isClient || ability.getForced() || Intrinsics.areEqual((Object)ability.getTemplate(), (Object)Abilities.INSTANCE.getDUMMY())) {
                        return ability;
                    }
                    Iterable $this$firstOrNull$iv = this.form.getAbilities();
                    boolean $i$f$firstOrNull = false;
                    object = $this$firstOrNull$iv.iterator();
                    while (object.hasNext()) {
                        Object element$iv = object.next();
                        PotentialAbility potential = (PotentialAbility)element$iv;
                        boolean bl = false;
                        if (!Intrinsics.areEqual((Object)potential.getTemplate(), (Object)ability.getTemplate())) continue;
                        v0 = element$iv;
                        break block6;
                    }
                    v0 = null;
                }
                PotentialAbility potentialAbility = v0;
                if (potentialAbility == null) {
                    list = ability;
                    Ability $this$attachAbilityCoordinate_u24lambda_u2478 = list;
                    boolean bl = false;
                    $this$attachAbilityCoordinate_u24lambda_u2478.setForced$common(true);
                    return list;
                }
                found = potentialAbility;
                list = this.form.getAbilities().getMapping().get((Object)found.getPriority());
                if (list == null) break block7;
                object2 = list.indexOf(found);
                int it = ((Number)object2).intValue();
                boolean bl = false;
                Object object3 = object = it != -1 ? object2 : null;
                if (object != null) break block8;
            }
            object2 = ability;
            Ability $this$attachAbilityCoordinate_u24lambda_u2480 = object2;
            boolean bl = false;
            $this$attachAbilityCoordinate_u24lambda_u2480.setForced$common(true);
            return object2;
        }
        int index = (Integer)object;
        ability.setPriority$common(found.getPriority());
        ability.setIndex$common(index);
        return ability;
    }

    public final void initializeMoveset(boolean preferLatest) {
        List possibleMoves = CollectionsKt.toMutableList((Collection)this.form.getMoves().getLevelUpMovesUpTo(this.level));
        this.moveSet.doWithoutEmitting((Function0<Unit>)((Function0)new Function0<Unit>(this, (List<MoveTemplate>)possibleMoves, preferLatest){
            final /* synthetic */ Pokemon this$0;
            final /* synthetic */ List<MoveTemplate> $possibleMoves;
            final /* synthetic */ boolean $preferLatest;
            {
                this.this$0 = $receiver;
                this.$possibleMoves = $possibleMoves;
                this.$preferLatest = $preferLatest;
                super(0);
            }

            public final void invoke() {
                this.this$0.getMoveSet().clear();
                if (this.$possibleMoves.isEmpty()) {
                    this.this$0.getMoveSet().add(Moves.INSTANCE.getExceptional().create());
                    return;
                }
                Function0 selector2 = (Function0)new Function0<MoveTemplate>(this.$preferLatest, this.$possibleMoves){
                    final /* synthetic */ boolean $preferLatest;
                    final /* synthetic */ List<MoveTemplate> $possibleMoves;
                    {
                        this.$preferLatest = $preferLatest;
                        this.$possibleMoves = $possibleMoves;
                        super(0);
                    }

                    /*
                     * WARNING - void declaration
                     */
                    @Nullable
                    public final MoveTemplate invoke() {
                        MoveTemplate moveTemplate;
                        if (this.$preferLatest) {
                            moveTemplate = (MoveTemplate)CollectionsKt.removeLastOrNull(this.$possibleMoves);
                        } else {
                            void var1_1;
                            MoveTemplate random = (MoveTemplate)CollectionsKt.randomOrNull((Collection)this.$possibleMoves, (Random)((Random)Random.Default));
                            if (random != null) {
                                this.$possibleMoves.remove(random);
                            }
                            moveTemplate = var1_1;
                        }
                        return moveTemplate;
                    }
                };
                for (int i = 0; i < 4 && (MoveTemplate)selector2.invoke() != null; ++i) {
                    MoveTemplate move;
                    this.this$0.getMoveSet().setMove(i, move.create());
                }
            }
        }));
        this.moveSet.update();
    }

    public static /* synthetic */ void initializeMoveset$default(Pokemon pokemon, boolean bl, int n, Object object) {
        if (object != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: initializeMoveset");
        }
        if ((n & 1) != 0) {
            bl = true;
        }
        pokemon.initializeMoveset(bl);
    }

    public final int getExperienceToNextLevel() {
        return this.getExperienceToLevel(this.level + 1);
    }

    public final int getExperienceToLevel(int level) {
        return level <= this.level ? 0 : this.getExperienceGroup().getExperience(level) - this.experience;
    }

    public final void setExperienceAndUpdateLevel(int xp) {
        this.setExperience$common(xp);
        int newLevel = this.getExperienceGroup().getLevel(xp);
        if (newLevel != this.level && newLevel <= Cobblemon.INSTANCE.getConfig().getMaxPokemonLevel()) {
            this.setLevel(newLevel);
        }
    }

    @NotNull
    public final AddExperienceResult addExperienceWithPlayer(@NotNull ServerPlayer player, @NotNull ExperienceSource source, int xp) {
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        Intrinsics.checkNotNullParameter((Object)source, (String)"source");
        AddExperienceResult result = this.addExperience(source, xp);
        if (result.getExperienceAdded() <= 0) {
            return result;
        }
        Object[] objectArray = new Object[]{this.getDisplayName(), xp};
        player.m_5661_((Component)LocalizationUtilsKt.lang("experience.gained", objectArray), true);
        if (result.getOldLevel() != result.getNewLevel()) {
            objectArray = new Object[]{this.getDisplayName(), result.getNewLevel()};
            player.m_213846_((Component)LocalizationUtilsKt.lang("experience.level_up", objectArray));
            int repeats = result.getNewLevel() - result.getOldLevel();
            if (repeats >= 1) {
                int n = 0;
                while (n < repeats) {
                    int it = n++;
                    boolean bl = false;
                    Pokemon.incrementFriendship$default(this, LEVEL_UP_FRIENDSHIP_CALCULATOR.calculate(this), false, 2, null);
                }
            }
            Iterable $this$forEach$iv = result.getNewMoves();
            boolean $i$f$forEach = false;
            for (Object element$iv : $this$forEach$iv) {
                MoveTemplate it = (MoveTemplate)element$iv;
                boolean bl = false;
                Object[] objectArray2 = new Object[]{this.getDisplayName(), it.getDisplayName()};
                player.m_213846_((Component)LocalizationUtilsKt.lang("experience.learned_move", objectArray2));
            }
        }
        return result;
    }

    @Nullable
    public final <T extends SpeciesFeature> T getFeature(@NotNull String name) {
        Object v0;
        block1: {
            Intrinsics.checkNotNullParameter((Object)name, (String)"name");
            Iterable iterable = this.features;
            for (Object t : iterable) {
                SpeciesFeature it = (SpeciesFeature)t;
                boolean bl = false;
                if (!Intrinsics.areEqual((Object)it.getName(), (Object)name)) continue;
                v0 = t;
                break block1;
            }
            v0 = null;
        }
        Object var2_7 = v0;
        return (T)(var2_7 instanceof SpeciesFeature ? (SpeciesFeature)var2_7 : null);
    }

    @NotNull
    public final PokemonProperties createPokemonProperties(PokemonPropertyExtractor ... extractors) {
        Intrinsics.checkNotNullParameter((Object)extractors, (String)"extractors");
        PokemonProperties properties2 = new PokemonProperties();
        PokemonPropertyExtractor[] $this$forEach$iv = extractors;
        boolean $i$f$forEach = false;
        int n = $this$forEach$iv.length;
        for (int i = 0; i < n; ++i) {
            PokemonPropertyExtractor element$iv;
            PokemonPropertyExtractor it = element$iv = $this$forEach$iv[i];
            boolean bl = false;
            it.invoke(this, properties2);
        }
        return properties2;
    }

    @NotNull
    public final PokemonProperties createPokemonProperties(@NotNull List<PokemonPropertyExtractor> extractors) {
        Intrinsics.checkNotNullParameter(extractors, (String)"extractors");
        Collection $this$toTypedArray$iv = extractors;
        boolean $i$f$toTypedArray = false;
        Collection thisCollection$iv = $this$toTypedArray$iv;
        PokemonPropertyExtractor[] pokemonPropertyExtractorArray = thisCollection$iv.toArray(new PokemonPropertyExtractor[0]);
        return this.createPokemonProperties(Arrays.copyOf(pokemonPropertyExtractorArray, pokemonPropertyExtractorArray.length));
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public final AddExperienceResult addExperience(@NotNull ExperienceSource source, int xp) {
        void events$iv;
        void this_$iv;
        Iterator $this$filterTo$iv$iv;
        int n;
        void this_$iv$iv;
        Intrinsics.checkNotNullParameter((Object)source, (String)"source");
        if (xp < 0 || !this.canLevelUpFurther()) {
            return new AddExperienceResult(this.level, this.level, SetsKt.emptySet(), 0);
        }
        int oldLevel = this.level;
        Set<MoveTemplate> previousLevelUpMoves = this.form.getMoves().getLevelUpMovesUpTo(oldLevel);
        int appliedXP = 0;
        appliedXP = xp;
        LevelUpEvent[] levelUpEventArray = CobblemonEvents.EXPERIENCE_GAINED_EVENT_PRE;
        Object object = new ExperienceGainedPreEvent(this, source, appliedXP);
        LevelUpEvent[] this_$iv2 = levelUpEventArray;
        boolean $i$f$postThen = false;
        EventObservable eventObservable = (EventObservable)this_$iv2;
        Cancelable[] cancelableArray = new Cancelable[]{object};
        Cancelable[] events$iv$iv = cancelableArray;
        int $i$f$post = 0;
        this_$iv$iv.emit(Arrays.copyOf(events$iv$iv, events$iv$iv.length));
        Cancelable[] $this$forEach$iv$iv$iv2 = events$iv$iv;
        boolean $i$f$forEach = false;
        int n2 = $this$forEach$iv$iv$iv2.length;
        for (n = 0; n < n2; ++n) {
            ExperienceGainedPreEvent it;
            Cancelable element$iv$iv$iv;
            Cancelable it$iv = element$iv$iv$iv = $this$forEach$iv$iv$iv2[n];
            boolean bl = false;
            if (it$iv.isCanceled()) {
                it = (ExperienceGainedPreEvent)it$iv;
                boolean bl2 = false;
                return new AddExperienceResult(this.level, this.level, SetsKt.emptySet(), 0);
            }
            it = (ExperienceGainedPreEvent)it$iv;
            boolean bl3 = false;
            appliedXP = it.getExperience();
        }
        this.setExperience$common(this.experience + appliedXP);
        int newLevel = 0;
        newLevel = RangesKt.coerceAtMost((int)this.getExperienceGroup().getLevel(this.experience), (int)Cobblemon.INSTANCE.getConfig().getMaxPokemonLevel());
        if (newLevel != oldLevel) {
            void events$iv2;
            void this_$iv3;
            object = CobblemonEvents.LEVEL_UP_EVENT;
            this_$iv2 = new LevelUpEvent[]{new LevelUpEvent(this, oldLevel, newLevel)};
            boolean $i$f$post2 = false;
            this_$iv3.emit(Arrays.copyOf(events$iv2, ((void)events$iv2).length));
            void $this$forEach$iv$iv = events$iv2;
            boolean $i$f$forEach2 = false;
            int $this$forEach$iv$iv$iv2 = ((void)$this$forEach$iv$iv).length;
            for ($i$f$post = 0; $i$f$post < $this$forEach$iv$iv$iv2; ++$i$f$post) {
                void element$iv$iv;
                void it = element$iv$iv = $this$forEach$iv$iv[$i$f$post];
                boolean bl = false;
                newLevel = it.getNewLevel();
            }
            this.setLevel(newLevel);
        }
        Set<MoveTemplate> newLevelUpMoves = this.form.getMoves().getLevelUpMovesUpTo(newLevel);
        Iterable $this$filter$iv = SetsKt.minus(newLevelUpMoves, (Iterable)previousLevelUpMoves);
        boolean $i$f$filter = false;
        Iterable $i$f$forEach2 = $this$filter$iv;
        Collection destination$iv$iv = new ArrayList();
        boolean $i$f$filterTo = false;
        Iterator element$iv$iv = $this$filterTo$iv$iv.iterator();
        while (element$iv$iv.hasNext()) {
            boolean bl;
            Object element$iv$iv2;
            block11: {
                element$iv$iv2 = element$iv$iv.next();
                MoveTemplate it = (MoveTemplate)element$iv$iv2;
                boolean bl4 = false;
                Iterable $this$none$iv = this.moveSet;
                boolean $i$f$none = false;
                if ($this$none$iv instanceof Collection && ((Collection)$this$none$iv).isEmpty()) {
                    bl = true;
                } else {
                    for (Object element$iv : $this$none$iv) {
                        Move move = (Move)element$iv;
                        boolean bl5 = false;
                        if (!Intrinsics.areEqual((Object)move.getTemplate(), (Object)it)) continue;
                        bl = false;
                        break block11;
                    }
                    bl = true;
                }
            }
            if (!bl) continue;
            destination$iv$iv.add(element$iv$iv2);
        }
        Set differences = CollectionsKt.toMutableSet((Iterable)((List)destination$iv$iv));
        Object $this$forEach$iv = differences;
        boolean $i$f$forEach222 = false;
        $this$filterTo$iv$iv = $this$forEach$iv.iterator();
        while ($this$filterTo$iv$iv.hasNext()) {
            Object element$iv = $this$filterTo$iv$iv.next();
            MoveTemplate it = (MoveTemplate)element$iv;
            boolean bl = false;
            if (!this.moveSet.hasSpace()) continue;
            this.moveSet.add(it.create());
        }
        $this$forEach$iv = CobblemonEvents.EXPERIENCE_GAINED_EVENT_POST;
        ExperienceGainedPostEvent[] $i$f$forEach222 = new ExperienceGainedPostEvent[]{new ExperienceGainedPostEvent(this, source, appliedXP, oldLevel, newLevel, differences)};
        boolean $i$f$post3 = false;
        this_$iv.emit(Arrays.copyOf(events$iv, ((void)events$iv).length));
        void $this$forEach$iv$iv = events$iv;
        boolean $i$f$forEach3 = false;
        int n3 = 0;
        n = ((void)$this$forEach$iv$iv).length;
        if (n3 < n) {
            void element$iv$iv3;
            void it = element$iv$iv3 = $this$forEach$iv$iv[n3];
            boolean bl = false;
            return new AddExperienceResult(oldLevel, newLevel, it.getLearnedMoves(), appliedXP);
        }
        return new AddExperienceResult(oldLevel, newLevel, differences, appliedXP);
    }

    public final boolean canLevelUpFurther() {
        return this.level < Cobblemon.INSTANCE.getConfig().getMaxPokemonLevel();
    }

    @NotNull
    public final AddExperienceResult levelUp(@NotNull ExperienceSource source) {
        Intrinsics.checkNotNullParameter((Object)source, (String)"source");
        return this.addExperience(source, this.getExperienceToNextLevel());
    }

    public final boolean exchangeMove(@NotNull MoveTemplate oldMove, @NotNull MoveTemplate newMove) {
        Move currentMove;
        Object v2;
        BenchedMove benchedNewMove;
        block6: {
            BenchedMove benchedMove;
            Object v0;
            Object object2;
            block5: {
                Intrinsics.checkNotNullParameter((Object)oldMove, (String)"oldMove");
                Intrinsics.checkNotNullParameter((Object)newMove, (String)"newMove");
                Iterable iterable = this.benchedMoves;
                for (Object object2 : iterable) {
                    BenchedMove it = (BenchedMove)object2;
                    boolean bl = false;
                    if (!Intrinsics.areEqual((Object)it.getMoveTemplate(), (Object)newMove)) continue;
                    v0 = object2;
                    break block5;
                }
                v0 = null;
            }
            if ((benchedMove = (BenchedMove)v0) == null) {
                benchedMove = benchedNewMove = new BenchedMove(newMove, 0);
            }
            if (this.moveSet.hasSpace()) {
                this.benchedMoves.remove(newMove);
                Move move = newMove.create();
                move.setRaisedPpStages(benchedNewMove.getPpRaisedStages());
                move.setCurrentPp(move.getMaxPp());
                this.moveSet.add(move);
                return true;
            }
            Iterable iterable = this.moveSet;
            object2 = iterable.iterator();
            while (object2.hasNext()) {
                Object e = object2.next();
                Move it = (Move)e;
                boolean bl = false;
                if (!Intrinsics.areEqual((Object)it.getTemplate(), (Object)oldMove)) continue;
                v2 = e;
                break block6;
            }
            v2 = null;
        }
        Move move = v2;
        if (move == null) {
            return false;
        }
        Move it = currentMove = move;
        boolean bl = false;
        float currentPPRatio = (float)it.getCurrentPp() / (float)it.getMaxPp();
        this.benchedMoves.doThenEmit((Function0<Unit>)((Function0)new Function0<Unit>(this, newMove, currentMove){
            final /* synthetic */ Pokemon this$0;
            final /* synthetic */ MoveTemplate $newMove;
            final /* synthetic */ Move $currentMove;
            {
                this.this$0 = $receiver;
                this.$newMove = $newMove;
                this.$currentMove = $currentMove;
                super(0);
            }

            public final void invoke() {
                this.this$0.getBenchedMoves().remove(this.$newMove);
                this.this$0.getBenchedMoves().add(new BenchedMove(this.$currentMove.getTemplate(), this.$currentMove.getRaisedPpStages()));
            }
        }));
        Move move2 = newMove.create();
        move2.setRaisedPpStages(benchedNewMove.getPpRaisedStages());
        move2.setCurrentPp((int)(currentPPRatio * (float)move2.getMaxPp()));
        this.moveSet.setMove(CollectionsKt.indexOf((Iterable)this.moveSet, (Object)currentMove), move2);
        return true;
    }

    public final void notify(@NotNull PokemonUpdatePacket<?> packet) {
        block0: {
            Intrinsics.checkNotNullParameter(packet, (String)"packet");
            StoreCoordinates<?> storeCoordinates = this.storeCoordinates.get();
            if (storeCoordinates == null) break block0;
            StoreCoordinates<?> $this$notify_u24lambda_u2495 = storeCoordinates;
            boolean bl = false;
            CobblemonNetwork.INSTANCE.sendPacketToPlayers($this$notify_u24lambda_u2495.getStore().getObservingPlayers(), (NetworkPacket)packet);
        }
    }

    @NotNull
    public final <T> SimpleObservable<T> registerObservable(@NotNull SimpleObservable<T> observable2, @Nullable Function1<? super T, ? extends PokemonUpdatePacket<?>> notifyPacket) {
        Intrinsics.checkNotNullParameter(observable2, (String)"observable");
        this.observables.add(observable2);
        Observable.DefaultImpls.subscribe$default(observable2, null, new Function1<T, Unit>(notifyPacket, this){
            final /* synthetic */ Function1<T, PokemonUpdatePacket<?>> $notifyPacket;
            final /* synthetic */ Pokemon this$0;
            {
                this.$notifyPacket = $notifyPacket;
                this.this$0 = $receiver;
                super(1);
            }

            public final void invoke(T it) {
                PokemonUpdatePacket packet;
                if (this.$notifyPacket != null && this.this$0.getStoreCoordinates().get() != null && (packet = (PokemonUpdatePacket)this.$notifyPacket.invoke(it)) != null) {
                    this.this$0.notify(packet);
                }
                Pokemon[] pokemonArray = new Pokemon[]{this.this$0};
                this.this$0.getAnyChangeObservable().emit((Pokemon[])pokemonArray);
            }
        }, 1, null);
        return observable2;
    }

    public static /* synthetic */ SimpleObservable registerObservable$default(Pokemon pokemon, SimpleObservable simpleObservable, Function1 function1, int n, Object object) {
        if (object != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: registerObservable");
        }
        if ((n & 2) != 0) {
            function1 = null;
        }
        return pokemon.registerObservable(simpleObservable, function1);
    }

    @NotNull
    public final SimpleObservable<Pokemon> getAnyChangeObservable() {
        return this.anyChangeObservable;
    }

    public final void markFeatureDirty(@NotNull SpeciesFeature feature) {
        Intrinsics.checkNotNullParameter((Object)feature, (String)"feature");
        SpeciesFeature[] speciesFeatureArray = new SpeciesFeature[]{feature};
        this._features.emit((SpeciesFeature[])speciesFeatureArray);
    }

    @NotNull
    public final Iterable<Observable<?>> getAllObservables() {
        return this.observables;
    }

    @NotNull
    public final Observable<Pokemon> getChangeObservable() {
        return this.anyChangeObservable;
    }

    public final void writeVariables(@NotNull VariableStruct struct2) {
        Intrinsics.checkNotNullParameter((Object)struct2, (String)"struct");
        struct2.setDirectly("level", new DoubleValue(this.level));
        struct2.setDirectly("max_hp", new DoubleValue(this.getHp()));
        struct2.setDirectly("current_hp", new DoubleValue(this.currentHealth));
        struct2.setDirectly("friendship", new DoubleValue(this.friendship));
        struct2.setDirectly("shiny", new DoubleValue(this.shiny));
        for (Stat stat : Stats.Companion.getPERMANENT()) {
            struct2.setDirectly("ev_" + stat.getShowdownId(), new DoubleValue(this.evs.getOrDefault(stat)));
            struct2.setDirectly("iv_" + stat.getShowdownId(), new DoubleValue(this.ivs.getOrDefault(stat)));
            struct2.setDirectly("stat_" + stat.getShowdownId(), new DoubleValue(this.getStat(stat)));
        }
    }

    private final void findAndLearnFormChangeMoves() {
        Iterable $this$forEach$iv = this.form.getMoves().getFormChangeMoves();
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            boolean bl;
            MoveTemplate move;
            block4: {
                move = (MoveTemplate)element$iv;
                boolean bl2 = false;
                Iterable $this$none$iv = this.benchedMoves;
                boolean $i$f$none = false;
                if ($this$none$iv instanceof Collection && ((Collection)$this$none$iv).isEmpty()) {
                    bl = true;
                } else {
                    for (Object element$iv2 : $this$none$iv) {
                        BenchedMove it = (BenchedMove)element$iv2;
                        boolean bl3 = false;
                        if (!Intrinsics.areEqual((Object)it.getMoveTemplate(), (Object)move)) continue;
                        bl = false;
                        break block4;
                    }
                    bl = true;
                }
            }
            if (!bl) continue;
            this.benchedMoves.add(new BenchedMove(move, 0));
        }
    }

    private final void sanitizeFormChangeMoves(FormData old) {
        BenchedMove benchedMove;
        for (int i = 0; i < 4; ++i) {
            Move move = this.moveSet.get(i);
            if (move == null || !LearnsetQuery.Companion.getFORM_CHANGE().canLearn(move.getTemplate(), old.getMoves()) || LearnsetQuery.Companion.getANY().canLearn(move.getTemplate(), this.form.getMoves())) continue;
            this.moveSet.setMove(i, null);
        }
        Iterator<BenchedMove> benchedIterator = this.benchedMoves.iterator();
        while (benchedIterator.hasNext()) {
            benchedMove = benchedIterator.next();
            if (!LearnsetQuery.Companion.getFORM_CHANGE().canLearn(benchedMove.getMoveTemplate(), old.getMoves()) || LearnsetQuery.Companion.getANY().canLearn(benchedMove.getMoveTemplate(), this.form.getMoves())) continue;
            benchedIterator.remove();
        }
        if (CollectionsKt.filterNotNull((Iterable)this.moveSet).isEmpty()) {
            benchedMove = (BenchedMove)CollectionsKt.firstOrNull((Iterable)this.benchedMoves);
            if (benchedMove != null) {
                this.moveSet.setMove(0, new Move(benchedMove.getMoveTemplate(), benchedMove.getPpRaisedStages(), 0, 4, null));
                return;
            }
            Pokemon.initializeMoveset$default(this, false, 1, null);
        }
    }

    private static final boolean loadFromNBT$lambda$34$lambda$33(Function1 $tmp0, Object p0) {
        Intrinsics.checkNotNullParameter((Object)$tmp0, (String)"$tmp0");
        return (Boolean)$tmp0.invoke(p0);
    }

    private static final void saveToJSON$lambda$50(Function1 $tmp0, Object p0) {
        Intrinsics.checkNotNullParameter((Object)$tmp0, (String)"$tmp0");
        $tmp0.invoke(p0);
    }

    private static final boolean loadFromJSON$lambda$60$lambda$59(Function1 $tmp0, Object p0) {
        Intrinsics.checkNotNullParameter((Object)$tmp0, (String)"$tmp0");
        return (Boolean)$tmp0.invoke(p0);
    }

    private static final void loadFromJSON$lambda$64(Function1 $tmp0, Object p0) {
        Intrinsics.checkNotNullParameter((Object)$tmp0, (String)"$tmp0");
        $tmp0.invoke(p0);
    }

    public static final /* synthetic */ void access$setHeldItem$p(Pokemon $this, ItemStack itemStack) {
        $this.heldItem = itemStack;
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0017\u0010\u0018J\u0015\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006J\u0015\u0010\t\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\u0007\u00a2\u0006\u0004\b\t\u0010\nR\"\u0010\f\u001a\u00020\u000b8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\f\u0010\r\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\u001a\u0010\u0013\u001a\u00020\u00128\u0000X\u0080\u0004\u00a2\u0006\f\n\u0004\b\u0013\u0010\u0014\u001a\u0004\b\u0015\u0010\u0016\u00a8\u0006\u0019"}, d2={"Lcom/cobblemon/mod/common/pokemon/Pokemon$Companion;", "", "Lcom/google/gson/JsonObject;", "json", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "loadFromJSON", "(Lcom/google/gson/JsonObject;)Lcom/cobblemon/mod/common/pokemon/Pokemon;", "Lnet/minecraft/nbt/CompoundTag;", "compound", "loadFromNBT", "(Lnet/minecraft/nbt/CompoundTag;)Lcom/cobblemon/mod/common/pokemon/Pokemon;", "Lcom/cobblemon/mod/common/api/pokemon/friendship/FriendshipMutationCalculator;", "LEVEL_UP_FRIENDSHIP_CALCULATOR", "Lcom/cobblemon/mod/common/api/pokemon/friendship/FriendshipMutationCalculator;", "getLEVEL_UP_FRIENDSHIP_CALCULATOR", "()Lcom/cobblemon/mod/common/api/pokemon/friendship/FriendshipMutationCalculator;", "setLEVEL_UP_FRIENDSHIP_CALCULATOR", "(Lcom/cobblemon/mod/common/api/pokemon/friendship/FriendshipMutationCalculator;)V", "Lnet/minecraft/resources/ResourceLocation;", "SHEDINJA", "Lnet/minecraft/resources/ResourceLocation;", "getSHEDINJA$common", "()Lnet/minecraft/resources/ResourceLocation;", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final FriendshipMutationCalculator getLEVEL_UP_FRIENDSHIP_CALCULATOR() {
            return LEVEL_UP_FRIENDSHIP_CALCULATOR;
        }

        public final void setLEVEL_UP_FRIENDSHIP_CALCULATOR(@NotNull FriendshipMutationCalculator friendshipMutationCalculator) {
            Intrinsics.checkNotNullParameter((Object)friendshipMutationCalculator, (String)"<set-?>");
            LEVEL_UP_FRIENDSHIP_CALCULATOR = friendshipMutationCalculator;
        }

        @NotNull
        public final ResourceLocation getSHEDINJA$common() {
            return SHEDINJA;
        }

        @NotNull
        public final Pokemon loadFromNBT(@NotNull CompoundTag compound) {
            Intrinsics.checkNotNullParameter((Object)compound, (String)"compound");
            return new Pokemon().loadFromNBT(compound);
        }

        @NotNull
        public final Pokemon loadFromJSON(@NotNull JsonObject json) {
            Intrinsics.checkNotNullParameter((Object)json, (String)"json");
            return new Pokemon().loadFromJSON(json);
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }

    @Metadata(mv={1, 8, 0}, k=3, xi=48)
    public final class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] nArray = new int[OriginalTrainerType.values().length];
            try {
                nArray[OriginalTrainerType.PLAYER.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                nArray[OriginalTrainerType.NPC.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                nArray[OriginalTrainerType.NONE.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            $EnumSwitchMapping$0 = nArray;
        }
    }
}

