/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Unit
 *  kotlin.collections.CollectionsKt
 *  kotlin.collections.SetsKt
 *  kotlin.jvm.JvmStatic
 *  kotlin.jvm.functions.Function0
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.Ref$BooleanRef
 *  kotlin.jvm.internal.SourceDebugExtension
 *  kotlin.ranges.IntRange
 *  kotlin.text.StringsKt
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Registry
 *  net.minecraft.core.Vec3i
 *  net.minecraft.core.registries.BuiltInRegistries
 *  net.minecraft.core.registries.Registries
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.NbtUtils
 *  net.minecraft.nbt.StringTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.ComponentContents
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.network.protocol.Packet
 *  net.minecraft.network.protocol.game.ClientGamePacketListener
 *  net.minecraft.network.protocol.game.ClientboundAddEntityPacket
 *  net.minecraft.network.syncher.EntityDataAccessor
 *  net.minecraft.network.syncher.EntityDataSerializer
 *  net.minecraft.network.syncher.EntityDataSerializers
 *  net.minecraft.network.syncher.SynchedEntityData
 *  net.minecraft.resources.ResourceKey
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.sounds.SoundEvents
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.tags.FluidTags
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.damagesource.DamageTypes
 *  net.minecraft.world.effect.MobEffect
 *  net.minecraft.world.entity.AgeableMob
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.Entity$RemovalReason
 *  net.minecraft.world.entity.EntityDimensions
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.ExperienceOrb
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.Mob
 *  net.minecraft.world.entity.Pose
 *  net.minecraft.world.entity.Shearable
 *  net.minecraft.world.entity.ai.attributes.AttributeSupplier$Builder
 *  net.minecraft.world.entity.ai.attributes.Attributes
 *  net.minecraft.world.entity.ai.control.MoveControl
 *  net.minecraft.world.entity.ai.goal.EatBlockGoal
 *  net.minecraft.world.entity.ai.goal.Goal
 *  net.minecraft.world.entity.ai.goal.Goal$Flag
 *  net.minecraft.world.entity.ai.navigation.PathNavigation
 *  net.minecraft.world.entity.animal.Animal
 *  net.minecraft.world.entity.animal.ShoulderRidingEntity
 *  net.minecraft.world.entity.item.ItemEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.DyeItem
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.ItemUtils
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.item.SuspiciousStewItem
 *  net.minecraft.world.level.EntityGetter
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.biome.Biome
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.gameevent.GameEvent
 *  net.minecraft.world.level.material.FluidState
 *  net.minecraft.world.level.pathfinder.BlockPathTypes
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.Vec3
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonEntities;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonItems;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonNetwork;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonSounds;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.actor.BattleActor;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.conditional.RegistryLikeCondition;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.drop.DropTable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.entity.Despawner;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.entity.PokemonSideDelegate;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.entity.pokemon.MocKEffect;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.Cancelable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.CobblemonEvents;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.entity.PokemonEntityLoadEvent;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.entity.PokemonEntitySaveEvent;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.entity.PokemonEntitySaveToWorldEvent;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokemon.ShoulderMountEvent;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.interaction.PokemonEntityInteraction;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.serializers.PoseTypeDataSerializer;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.serializers.StringSetDataSerializer;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonProperties;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.effect.ShoulderEffect;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.Evolution;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.feature.ChoiceSpeciesFeatureProvider;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.feature.FlagSpeciesFeature;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.feature.SpeciesFeatureProvider;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.feature.SpeciesFeatures;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.feature.StringSpeciesFeature;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.status.Statuses;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.CancelableObservable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.EventObservable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.ObservableSubscription;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.SimpleObservable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.scheduling.Schedulable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.scheduling.ScheduledTask;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.scheduling.SchedulingTracker;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.InvalidSpeciesException;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.PokemonStoreManager;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.pc.PCStore;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.types.ElementalTypes;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.BagItems;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.BattleBuilder;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.BattleRegistry;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.entity.PokemonPastureBlockEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.entity.PokemonClientDelegate;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.PoseType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.Poseable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.generic.GenericBedrockEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokeball.EmptyPokeBallEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonBehaviourFlag;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonServerDelegate;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.ai.PokemonMoveControl;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.ai.PokemonNavigation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.ai.goals.PokemonBreatheAirGoal;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.ai.goals.PokemonFloatToSurfaceGoal;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.ai.goals.PokemonFollowOwnerGoal;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.ai.goals.PokemonInBattleMovementGoal;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.ai.goals.PokemonLookAtEntityGoal;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.ai.goals.PokemonMoveIntoFluidGoal;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.ai.goals.PokemonPointAtSpawnGoal;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.ai.goals.PokemonWanderAroundGoal;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.ai.goals.SleepOnTrainerGoal;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.ai.goals.WildRestGoal;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.effects.EffectTracker;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.effects.IllusionEffect;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.battle.BagItemConvertible;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.mixin.accessor.AccessorEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.animation.PlayPoseableAnimationPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.sound.UnvalidatedPlaySoundS2CPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.spawn.SpawnPokemonPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.ui.InteractPokemonUIPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.FormData;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Species;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.activestate.ActivePokemonState;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.activestate.InactivePokemonState;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.activestate.PokemonState;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.activestate.ShoulderedState;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.ai.FormPokemonBehaviour;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.ai.RestBehaviour;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.variants.ItemInteractionEvolution;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.misc.GimmighoulStashHandler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.status.PersistentStatusContainer;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.BitUtilitiesKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.CollectionUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.CompoundTagExtensionsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.PlayerExtensionsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.WorldExtensionsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.world.gamerules.CobblemonGameRules;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.ranges.IntRange;
import kotlin.text.StringsKt;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.Vec3i;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentContents;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.Shearable;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.EatBlockGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.ShoulderRidingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SuspiciousStewItem;
import net.minecraft.world.level.EntityGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u00b0\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0001\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0005\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0010\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0006\n\u0002\b\u0006\n\u0002\u0010!\n\u0002\u0010\u0000\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u001a\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0016\u0018\u0000 \u00c2\u00022\u00020\u00012\u00020\u00022\u00020\u00032\u00020\u0004:\u0002\u00c2\u0002B1\u0012\u0006\u0010\r\u001a\u00020*\u0012\n\b\u0002\u0010\u009d\u0002\u001a\u00030\u0097\u0001\u0012\u0012\b\u0002\u0010\u00bf\u0002\u001a\u000b\u0012\u0006\b\u0001\u0012\u00020\u00000\u00be\u0002\u00a2\u0006\u0006\b\u00c0\u0002\u0010\u00c1\u0002J\u001f\u0010\n\u001a\u00020\t2\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\u0007H\u0002\u00a2\u0006\u0004\b\n\u0010\u000bJ\u001f\u0010\u0011\u001a\u00020\u00102\u0006\u0010\r\u001a\u00020\f2\u0006\u0010\u000f\u001a\u00020\u000eH\u0016\u00a2\u0006\u0004\b\u0011\u0010\u0012J\u0015\u0010\u0013\u001a\u00020\t2\u0006\u0010\u0006\u001a\u00020\u0005\u00a2\u0006\u0004\b\u0013\u0010\u0014J\u0017\u0010\u0015\u001a\u00020\t2\u0006\u0010\u0006\u001a\u00020\u0005H\u0016\u00a2\u0006\u0004\b\u0015\u0010\u0014J\u000f\u0010\u0016\u001a\u00020\tH\u0016\u00a2\u0006\u0004\b\u0016\u0010\u0017J\u0017\u0010\u0018\u001a\u00020\t2\u0006\u0010\u000f\u001a\u00020\u000eH\u0016\u00a2\u0006\u0004\b\u0018\u0010\u0019J\r\u0010\u001a\u001a\u00020\t\u00a2\u0006\u0004\b\u001a\u0010\u0017J\u000f\u0010\u001b\u001a\u00020\tH\u0016\u00a2\u0006\u0004\b\u001b\u0010\u0017J\u000f\u0010\u001c\u001a\u00020\tH\u0016\u00a2\u0006\u0004\b\u001c\u0010\u0017J\u0017\u0010\u001f\u001a\u00020\t2\u0006\u0010\u001e\u001a\u00020\u001dH\u0016\u00a2\u0006\u0004\b\u001f\u0010 J\u000f\u0010!\u001a\u00020\u0010H\u0016\u00a2\u0006\u0004\b!\u0010\"J\r\u0010#\u001a\u00020\t\u00a2\u0006\u0004\b#\u0010\u0017J!\u0010(\u001a\u0004\u0018\u00010'2\u0006\u0010$\u001a\u00020\f2\u0006\u0010&\u001a\u00020%H\u0016\u00a2\u0006\u0004\b(\u0010)J\u0017\u0010,\u001a\u00020+2\u0006\u0010\r\u001a\u00020*H\u0014\u00a2\u0006\u0004\b,\u0010-J\u0015\u00100\u001a\b\u0012\u0004\u0012\u00020/0.H\u0016\u00a2\u0006\u0004\b0\u00101J\r\u00102\u001a\u00020\u0010\u00a2\u0006\u0004\b2\u0010\"J!\u00107\u001a\u00020\t2\b\u00104\u001a\u0004\u0018\u0001032\u0006\u00106\u001a\u000205H\u0016\u00a2\u0006\u0004\b7\u00108J\u0019\u00109\u001a\u00020\u00102\b\u00104\u001a\u0004\u0018\u000103H\u0014\u00a2\u0006\u0004\b9\u0010:J\u000f\u0010;\u001a\u00020\u0010H\u0014\u00a2\u0006\u0004\b;\u0010\"J\u0015\u0010=\u001a\u00020\t2\u0006\u0010\u0006\u001a\u00020<\u00a2\u0006\u0004\b=\u0010>J\u001f\u0010C\u001a\u0002052\u0006\u0010@\u001a\u00020?2\u0006\u0010B\u001a\u00020AH\u0014\u00a2\u0006\u0004\bC\u0010DJ\u0011\u0010E\u001a\u0004\u0018\u00010'H\u0014\u00a2\u0006\u0004\bE\u0010FJ\r\u0010H\u001a\u00020G\u00a2\u0006\u0004\bH\u0010IJ\u0015\u0010L\u001a\u00020\t2\u0006\u0010K\u001a\u00020J\u00a2\u0006\u0004\bL\u0010MJ\u000f\u0010O\u001a\u00020NH\u0016\u00a2\u0006\u0004\bO\u0010PJ\u0011\u0010R\u001a\u0004\u0018\u00010QH\u0016\u00a2\u0006\u0004\bR\u0010SJ\u000f\u0010T\u001a\u00020QH\u0014\u00a2\u0006\u0004\bT\u0010SJ\u0017\u0010U\u001a\u00020A2\u0006\u0010@\u001a\u00020?H\u0016\u00a2\u0006\u0004\bU\u0010VJ\u0017\u0010W\u001a\u0002052\u0006\u0010@\u001a\u00020?H\u0016\u00a2\u0006\u0004\bW\u0010XJ\r\u0010Y\u001a\u00020\t\u00a2\u0006\u0004\bY\u0010\u0017J\u000f\u0010[\u001a\u00020ZH\u0016\u00a2\u0006\u0004\b[\u0010\\J\u000f\u0010]\u001a\u00020QH\u0016\u00a2\u0006\u0004\b]\u0010SJ\u000f\u0010^\u001a\u00020+H\u0016\u00a2\u0006\u0004\b^\u0010_J\u0017\u0010b\u001a\u0002052\u0006\u0010a\u001a\u00020`H\u0016\u00a2\u0006\u0004\bb\u0010cJ)\u0010g\u001a\u00020\t2\u0006\u0010d\u001a\u0002052\u0006\u0010e\u001a\u0002052\b\u0010f\u001a\u0004\u0018\u000103H\u0016\u00a2\u0006\u0004\bg\u0010hJ\u0017\u0010k\u001a\u00020\u00102\u0006\u0010j\u001a\u00020iH\u0016\u00a2\u0006\u0004\bk\u0010lJ\u000f\u0010m\u001a\u00020\tH\u0016\u00a2\u0006\u0004\bm\u0010\u0017J\u0015\u0010n\u001a\u00020\t2\u0006\u0010\u0006\u001a\u00020\u0005\u00a2\u0006\u0004\bn\u0010\u0014J\r\u0010o\u001a\u00020\u0010\u00a2\u0006\u0004\bo\u0010\"J\u000f\u0010p\u001a\u00020\u0010H\u0014\u00a2\u0006\u0004\bp\u0010\"J\u000f\u0010q\u001a\u00020\u0010H\u0016\u00a2\u0006\u0004\bq\u0010\"J\u001f\u0010u\u001a\u00020t2\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010s\u001a\u00020rH\u0016\u00a2\u0006\u0004\bu\u0010vJ\u0017\u0010w\u001a\u00020\t2\u0006\u0010\b\u001a\u00020\u0007H\u0016\u00a2\u0006\u0004\bw\u0010xJ\u000f\u0010y\u001a\u00020\tH\u0016\u00a2\u0006\u0004\by\u0010\u0017J\r\u0010z\u001a\u00020\t\u00a2\u0006\u0004\bz\u0010\u0017J\u000f\u0010{\u001a\u00020\tH\u0016\u00a2\u0006\u0004\b{\u0010\u0017J\r\u0010|\u001a\u00020\t\u00a2\u0006\u0004\b|\u0010\u0017J\u0017\u0010}\u001a\u00020\t2\u0006\u0010f\u001a\u000203H\u0016\u00a2\u0006\u0004\b}\u0010~J\u000f\u0010\u007f\u001a\u00020\tH\u0016\u00a2\u0006\u0004\b\u007f\u0010\u0017J\u0011\u0010\u0080\u0001\u001a\u00020\tH\u0016\u00a2\u0006\u0005\b\u0080\u0001\u0010\u0017J\u000f\u0010\u0081\u0001\u001a\u00020\t\u00a2\u0006\u0005\b\u0081\u0001\u0010\u0017J\u0019\u0010\u0083\u0001\u001a\u000b \u0082\u0001*\u0004\u0018\u00010Z0Z\u00a2\u0006\u0006\b\u0083\u0001\u0010\u0084\u0001J\u0013\u0010\u0086\u0001\u001a\u00030\u0085\u0001H\u0016\u00a2\u0006\u0006\b\u0086\u0001\u0010\u0087\u0001J\u0019\u0010\u0088\u0001\u001a\u00020\t2\u0006\u0010\u0006\u001a\u00020<H\u0016\u00a2\u0006\u0005\b\u0088\u0001\u0010>J\u001f\u0010\u0089\u0001\u001a\u00020\t2\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\u0007\u00a2\u0006\u0005\b\u0089\u0001\u0010\u000bJ\u0011\u0010\u008a\u0001\u001a\u00020\u0010H\u0016\u00a2\u0006\u0005\b\u008a\u0001\u0010\"J\u001c\u0010\u008b\u0001\u001a\u00020\u00102\b\u0010\u0006\u001a\u0004\u0018\u00010<H\u0016\u00a2\u0006\u0006\b\u008b\u0001\u0010\u008c\u0001J \u0010\u008f\u0001\u001a\u00020\u00102\f\u0010\u008e\u0001\u001a\u0007\u0012\u0002\b\u00030\u008d\u0001H\u0016\u00a2\u0006\u0006\b\u008f\u0001\u0010\u0090\u0001J\u0011\u0010\u0091\u0001\u001a\u00020\u0010H\u0016\u00a2\u0006\u0005\b\u0091\u0001\u0010\"J\u001c\u0010\u0094\u0001\u001a\u00020\u00102\b\u0010\u0093\u0001\u001a\u00030\u0092\u0001H\u0016\u00a2\u0006\u0006\b\u0094\u0001\u0010\u0095\u0001J\u0018\u0010\u0098\u0001\u001a\n\u0012\u0005\u0012\u00030\u0097\u00010\u0096\u0001\u00a2\u0006\u0006\b\u0098\u0001\u0010\u0099\u0001J\u001c\u0010\u009c\u0001\u001a\u00020\u00102\b\u0010\u009b\u0001\u001a\u00030\u009a\u0001H\u0016\u00a2\u0006\u0006\b\u009c\u0001\u0010\u009d\u0001J\u001b\u0010\u009f\u0001\u001a\u00020\u00102\u0007\u0010\u009e\u0001\u001a\u00020ZH\u0016\u00a2\u0006\u0006\b\u009f\u0001\u0010\u00a0\u0001J!\u0010\u00a2\u0001\u001a\u00020\u00102\u0006\u0010K\u001a\u00020J2\u0007\u0010\u00a1\u0001\u001a\u00020\t\u00a2\u0006\u0006\b\u00a2\u0001\u0010\u00a3\u0001J\u001d\u0010\u00a5\u0001\u001a\u00020\u00102\t\u0010\u00a4\u0001\u001a\u0004\u0018\u00010QH\u0016\u00a2\u0006\u0006\b\u00a5\u0001\u0010\u00a6\u0001J\u001b\u0010\u00a8\u0001\u001a\u00020\u00102\u0007\u0010\u00a7\u0001\u001a\u00020\tH\u0016\u00a2\u0006\u0006\b\u00a8\u0001\u0010\u00a9\u0001J\u001a\u0010\u00ac\u0001\u001a\u00020\u00102\b\u0010\u00ab\u0001\u001a\u00030\u00aa\u0001\u00a2\u0006\u0006\b\u00ac\u0001\u0010\u00ad\u0001J\u001c\u0010\u00b0\u0001\u001a\u00020\u00102\b\u0010\u00af\u0001\u001a\u00030\u00ae\u0001H\u0016\u00a2\u0006\u0006\b\u00b0\u0001\u0010\u00b1\u0001J\u0011\u0010\u00b2\u0001\u001a\u00020\tH\u0016\u00a2\u0006\u0005\b\u00b2\u0001\u0010\u0017J\u0011\u0010\u00b3\u0001\u001a\u00020\tH\u0016\u00a2\u0006\u0005\b\u00b3\u0001\u0010\u0017J\u0011\u0010\u00b4\u0001\u001a\u00020\u0010H\u0016\u00a2\u0006\u0005\b\u00b4\u0001\u0010\"J\u001c\u0010\u00b7\u0001\u001a\u00020\u00102\b\u0010\u00b6\u0001\u001a\u00030\u00b5\u0001H\u0016\u00a2\u0006\u0006\b\u00b7\u0001\u0010\u00b8\u0001J\u0017\u0010\u00b9\u0001\u001a\u00020\t2\u0006\u0010\u0006\u001a\u00020<\u00a2\u0006\u0005\b\u00b9\u0001\u0010>J\u001c\u0010\u00bc\u0001\u001a\u00020\u00102\b\u0010\u00bb\u0001\u001a\u00030\u00ba\u0001H\u0002\u00a2\u0006\u0006\b\u00bc\u0001\u0010\u00bd\u0001J\u0011\u0010\u00be\u0001\u001a\u00020\u0010H\u0002\u00a2\u0006\u0005\b\u00be\u0001\u0010\"J\u0011\u0010\u00bf\u0001\u001a\u00020\u0010H\u0014\u00a2\u0006\u0005\b\u00bf\u0001\u0010\"J\u001d\u0010\u00c0\u0001\u001a\u00030\u0092\u00012\b\u0010\u0093\u0001\u001a\u00030\u0092\u0001H\u0016\u00a2\u0006\u0006\b\u00c0\u0001\u0010\u00c1\u0001R\u001c\u0010\u00c6\u0001\u001a\n\u0012\u0005\u0012\u00030\u00c3\u00010\u00c2\u00018F\u00a2\u0006\b\u001a\u0006\b\u00c4\u0001\u0010\u00c5\u0001R0\u0010\u00cd\u0001\u001a\u0005\u0018\u00010\u00c7\u00012\n\u0010\u00c8\u0001\u001a\u0005\u0018\u00010\u00c7\u00018F@FX\u0086\u000e\u00a2\u0006\u0010\u001a\u0006\b\u00c9\u0001\u0010\u00ca\u0001\"\u0006\b\u00cb\u0001\u0010\u00cc\u0001R)\u0010\u00d0\u0001\u001a\u00020Z2\u0007\u0010\u00c8\u0001\u001a\u00020Z8F@FX\u0086\u000e\u00a2\u0006\u000f\u001a\u0005\b\u00ce\u0001\u0010\\\"\u0006\b\u00cf\u0001\u0010\u00a0\u0001R\u0015\u0010\u00d4\u0001\u001a\u00030\u00d1\u00018F\u00a2\u0006\b\u001a\u0006\b\u00d2\u0001\u0010\u00d3\u0001R*\u0010\u00d6\u0001\u001a\u00030\u00d5\u00018\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0018\n\u0006\b\u00d6\u0001\u0010\u00d7\u0001\u001a\u0006\b\u00d8\u0001\u0010\u00d9\u0001\"\u0006\b\u00da\u0001\u0010\u00db\u0001R$\u0010\u00de\u0001\u001a\n\u0012\u0005\u0012\u00030\u00dd\u00010\u00dc\u00018\u0006\u00a2\u0006\u0010\n\u0006\b\u00de\u0001\u0010\u00df\u0001\u001a\u0006\b\u00e0\u0001\u0010\u00e1\u0001R(\u0010\u00e2\u0001\u001a\u00020\t8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0017\n\u0006\b\u00e2\u0001\u0010\u00e3\u0001\u001a\u0005\b\u00e4\u0001\u0010\u0017\"\u0006\b\u00e5\u0001\u0010\u00a9\u0001R \u0010\u00e7\u0001\u001a\u00030\u00e6\u00018\u0016X\u0096\u0004\u00a2\u0006\u0010\n\u0006\b\u00e7\u0001\u0010\u00e8\u0001\u001a\u0006\b\u00e9\u0001\u0010\u00ea\u0001R0\u0010\u00ec\u0001\u001a\t\u0012\u0004\u0012\u00020\u00000\u00eb\u00018\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0018\n\u0006\b\u00ec\u0001\u0010\u00ed\u0001\u001a\u0006\b\u00ee\u0001\u0010\u00ef\u0001\"\u0006\b\u00f0\u0001\u0010\u00f1\u0001R,\u0010\u00f3\u0001\u001a\u0005\u0018\u00010\u00f2\u00018\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0018\n\u0006\b\u00f3\u0001\u0010\u00f4\u0001\u001a\u0006\b\u00f5\u0001\u0010\u00f6\u0001\"\u0006\b\u00f7\u0001\u0010\u00f8\u0001R*\u0010\u00fa\u0001\u001a\u00030\u00f9\u00018\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0018\n\u0006\b\u00fa\u0001\u0010\u00fb\u0001\u001a\u0006\b\u00fc\u0001\u0010\u00fd\u0001\"\u0006\b\u00fe\u0001\u0010\u00ff\u0001R,\u0010\u0081\u0002\u001a\u0005\u0018\u00010\u0080\u00028\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0018\n\u0006\b\u0081\u0002\u0010\u0082\u0002\u001a\u0006\b\u0083\u0002\u0010\u0084\u0002\"\u0006\b\u0085\u0002\u0010\u0086\u0002R\u0015\u0010\u008a\u0002\u001a\u00030\u0087\u00028F\u00a2\u0006\b\u001a\u0006\b\u0088\u0002\u0010\u0089\u0002R\u0015\u0010\u008e\u0002\u001a\u00030\u008b\u00028F\u00a2\u0006\b\u001a\u0006\b\u008c\u0002\u0010\u008d\u0002R\u0015\u0010\u0090\u0002\u001a\u00030\u0087\u00028F\u00a2\u0006\b\u001a\u0006\b\u008f\u0002\u0010\u0089\u0002R\u0013\u0010\u0092\u0002\u001a\u00020Z8F\u00a2\u0006\u0007\u001a\u0005\b\u0091\u0002\u0010\\R\u0013\u0010\u0093\u0002\u001a\u00020\t8F\u00a2\u0006\u0007\u001a\u0005\b\u0093\u0002\u0010\u0017R\u0013\u0010\u0094\u0002\u001a\u00020\t8F\u00a2\u0006\u0007\u001a\u0005\b\u0094\u0002\u0010\u0017R+\u0010\u0095\u0002\u001a\u0004\u0018\u00010<8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0018\n\u0006\b\u0095\u0002\u0010\u0096\u0002\u001a\u0006\b\u0097\u0002\u0010\u0098\u0002\"\u0006\b\u0099\u0002\u0010\u008c\u0001R)\u0010\u009c\u0002\u001a\u00020Z2\u0007\u0010\u00c8\u0001\u001a\u00020Z8F@FX\u0086\u000e\u00a2\u0006\u000f\u001a\u0005\b\u009a\u0002\u0010\\\"\u0006\b\u009b\u0002\u0010\u00a0\u0001R4\u0010\u009d\u0002\u001a\u00030\u0097\u00012\b\u0010\u00c8\u0001\u001a\u00030\u0097\u00018\u0006@FX\u0086\u000e\u00a2\u0006\u0018\n\u0006\b\u009d\u0002\u0010\u009e\u0002\u001a\u0006\b\u009f\u0002\u0010\u00a0\u0002\"\u0006\b\u00a1\u0002\u0010\u00a2\u0002R(\u0010\u00a3\u0002\u001a\u00020\t8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0017\n\u0006\b\u00a3\u0002\u0010\u00e3\u0001\u001a\u0005\b\u00a4\u0002\u0010\u0017\"\u0006\b\u00a5\u0002\u0010\u00a9\u0001R&\u0010\u00a7\u0002\u001a\f\u0012\u0007\u0012\u0005\u0018\u00010\u009a\u00010\u00a6\u00028\u0006\u00a2\u0006\u0010\n\u0006\b\u00a7\u0002\u0010\u00a8\u0002\u001a\u0006\b\u00a9\u0002\u0010\u00aa\u0002R \u0010\u00ac\u0002\u001a\u00030\u00ab\u00028\u0016X\u0096\u0004\u00a2\u0006\u0010\n\u0006\b\u00ac\u0002\u0010\u00ad\u0002\u001a\u0006\b\u00ae\u0002\u0010\u00af\u0002R(\u0010\u00b1\u0002\u001a\u000e\u0012\t\u0012\u0007\u0012\u0002\b\u00030\u00b0\u00020\u00dc\u00018\u0006\u00a2\u0006\u0010\n\u0006\b\u00b1\u0002\u0010\u00df\u0001\u001a\u0006\b\u00b2\u0002\u0010\u00e1\u0001R,\u0010\u00b4\u0002\u001a\u0005\u0018\u00010\u00b3\u00028\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0018\n\u0006\b\u00b4\u0002\u0010\u00b5\u0002\u001a\u0006\b\u00b6\u0002\u0010\u00b7\u0002\"\u0006\b\u00b8\u0002\u0010\u00b9\u0002R(\u0010\u00ba\u0002\u001a\u00020Z8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0017\n\u0006\b\u00ba\u0002\u0010\u00bb\u0002\u001a\u0005\b\u00bc\u0002\u0010\\\"\u0006\b\u00bd\u0002\u0010\u00a0\u0001\u00a8\u0006\u00c3\u0002"}, d2={"Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;", "Lnet/minecraft/world/entity/animal/ShoulderRidingEntity;", "Lcom/cobblemon/mod/common/entity/Poseable;", "Lnet/minecraft/world/entity/Shearable;", "Lcom/cobblemon/mod/common/api/scheduling/Schedulable;", "Lnet/minecraft/world/entity/player/Player;", "player", "Lnet/minecraft/world/item/ItemStack;", "stack", "", "attemptItemInteraction", "(Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/item/ItemStack;)Z", "Lnet/minecraft/server/level/ServerLevel;", "world", "Lnet/minecraft/world/entity/animal/Animal;", "other", "", "breed", "(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/entity/animal/Animal;)V", "canBattle", "(Lnet/minecraft/world/entity/player/Player;)Z", "canBeLeashedBy", "canBreatheInWater", "()Z", "canBreedWith", "(Lnet/minecraft/world/entity/animal/Animal;)Z", "canSleep", "canTakeDamage", "canUsePortals", "Lnet/minecraft/world/level/material/FluidState;", "state", "canWalkOnFluid", "(Lnet/minecraft/world/level/material/FluidState;)Z", "checkDespawn", "()V", "couldStopFlying", "level", "Lnet/minecraft/world/entity/AgeableMob;", "partner", "", "createChild", "(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/entity/AgeableMob;)Ljava/lang/Void;", "Lnet/minecraft/world/level/Level;", "Lcom/cobblemon/mod/common/entity/pokemon/ai/PokemonNavigation;", "createNavigation", "(Lnet/minecraft/world/level/Level;)Lcom/cobblemon/mod/common/entity/pokemon/ai/PokemonNavigation;", "Lnet/minecraft/network/protocol/Packet;", "Lnet/minecraft/network/protocol/game/ClientGamePacketListener;", "createSpawnPacket", "()Lnet/minecraft/network/protocol/Packet;", "cry", "Lnet/minecraft/world/damagesource/DamageSource;", "source", "", "amount", "damage", "(Lnet/minecraft/world/damagesource/DamageSource;F)Z", "drop", "(Lnet/minecraft/world/damagesource/DamageSource;)V", "dropXp", "Lnet/minecraft/server/level/ServerPlayer;", "forceBattle", "(Lnet/minecraft/server/level/ServerPlayer;)Z", "Lnet/minecraft/world/entity/Pose;", "pose", "Lnet/minecraft/world/entity/EntityDimensions;", "dimensions", "getActiveEyeHeight", "(Lnet/minecraft/world/entity/Pose;Lnet/minecraft/world/entity/EntityDimensions;)F", "getAmbientSound", "()Ljava/lang/Void;", "Lnet/minecraft/sounds/SoundEvent;", "getBattleTheme", "()Lnet/minecraft/sounds/SoundEvent;", "Lcom/cobblemon/mod/common/entity/pokemon/PokemonBehaviourFlag;", "flag", "getBehaviourFlag", "(Lcom/cobblemon/mod/common/entity/pokemon/PokemonBehaviourFlag;)Z", "Lcom/cobblemon/mod/common/entity/PoseType;", "getCurrentPoseType", "()Lcom/cobblemon/mod/common/entity/PoseType;", "Lnet/minecraft/network/chat/Component;", "getCustomName", "()Lnet/minecraft/network/chat/Component;", "getDefaultName", "getDimensions", "(Lnet/minecraft/world/entity/Pose;)Lnet/minecraft/world/entity/EntityDimensions;", "getEyeHeight", "(Lnet/minecraft/world/entity/Pose;)F", "getIsSubmerged", "", "getMinAmbientSoundDelay", "()I", "getName", "getNavigation", "()Lcom/cobblemon/mod/common/entity/pokemon/ai/PokemonNavigation;", "Lnet/minecraft/world/level/pathfinder/BlockPathTypes;", "nodeType", "getPathfindingPenalty", "(Lnet/minecraft/world/level/pathfinder/BlockPathTypes;)F", "fallDistance", "damageMultiplier", "damageSource", "handleFallDamage", "(FFLnet/minecraft/world/damagesource/DamageSource;)Z", "", "status", "handleStatus", "(B)V", "hasCustomName", "hasRoomToMount", "hideNameRendering", "initDataTracker", "initGoals", "Lnet/minecraft/world/InteractionHand;", "hand", "Lnet/minecraft/world/InteractionResult;", "interactMob", "(Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/InteractionHand;)Lnet/minecraft/world/InteractionResult;", "isBreedingItem", "(Lnet/minecraft/world/item/ItemStack;)Z", "isCustomNameVisible", "isFalling", "isFireImmune", "isFlying", "isInvulnerableTo", "(Lnet/minecraft/world/damagesource/DamageSource;)Z", "isReadyToSitOnPlayer", "isShearable", "isUncatchable", "kotlin.jvm.PlatformType", "labelLevel", "()Ljava/lang/Integer;", "Lnet/minecraft/world/level/EntityGetter;", "method_48926", "()Lnet/minecraft/world/level/EntityGetter;", "mountOnto", "offerHeldItem", "onEatingGrass", "onStoppedTrackingBy", "(Lnet/minecraft/server/level/ServerPlayer;)V", "Lnet/minecraft/network/syncher/EntityDataAccessor;", "data", "onTrackedDataSet", "(Lnet/minecraft/network/syncher/EntityDataAccessor;)V", "playAmbientSound", "Lnet/minecraft/nbt/CompoundTag;", "nbt", "readNbt", "(Lnet/minecraft/nbt/CompoundTag;)V", "Ljava/util/concurrent/CompletableFuture;", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "recallWithAnimation", "()Ljava/util/concurrent/CompletableFuture;", "Lnet/minecraft/entity/Entity$RemovalReason;", "reason", "remove", "(Lnet/minecraft/world/entity/Entity$RemovalReason;)V", "air", "setAir", "(I)V", "on", "setBehaviourFlag", "(Lcom/cobblemon/mod/common/entity/pokemon/PokemonBehaviourFlag;Z)V", "name", "setCustomName", "(Lnet/minecraft/network/chat/Component;)V", "visible", "setCustomNameVisible", "(Z)V", "Lnet/minecraft/world/entity/ai/control/MoveControl;", "moveControl", "setMoveControl", "(Lnet/minecraft/world/entity/ai/control/MoveControl;)V", "Lnet/minecraft/sounds/SoundSource;", "shearedSoundCategory", "sheared", "(Lnet/minecraft/sounds/SoundSource;)V", "shouldRenderName", "shouldSave", "tick", "Lnet/minecraft/world/phys/Vec3;", "movementInput", "travel", "(Lnet/minecraft/world/phys/Vec3;)V", "tryMountingShoulder", "Lnet/minecraft/core/BlockPos;", "fromBp", "updateBlocksTraveled", "(Lnet/minecraft/core/BlockPos;)V", "updateEyeHeight", "updatePostDeath", "writeNbt", "(Lnet/minecraft/nbt/CompoundTag;)Lnet/minecraft/nbt/CompoundTag;", "", "", "getAspects", "()Ljava/util/Set;", "aspects", "Ljava/util/UUID;", "value", "getBattleId", "()Ljava/util/UUID;", "setBattleId", "(Ljava/util/UUID;)V", "battleId", "getBeamMode", "setBeamMode", "beamMode", "Lcom/cobblemon/mod/common/pokemon/ai/FormPokemonBehaviour;", "getBehaviour", "()Lcom/cobblemon/mod/common/pokemon/ai/FormPokemonBehaviour;", "behaviour", "", "blocksTraveled", "D", "getBlocksTraveled", "()D", "setBlocksTraveled", "(D)V", "", "", "busyLocks", "Ljava/util/List;", "getBusyLocks", "()Ljava/util/List;", "countsTowardsSpawnCap", "Z", "getCountsTowardsSpawnCap", "setCountsTowardsSpawnCap", "Lcom/cobblemon/mod/common/api/entity/PokemonSideDelegate;", "delegate", "Lcom/cobblemon/mod/common/api/entity/PokemonSideDelegate;", "getDelegate", "()Lcom/cobblemon/mod/common/api/entity/PokemonSideDelegate;", "Lcom/cobblemon/mod/common/api/entity/Despawner;", "despawner", "Lcom/cobblemon/mod/common/api/entity/Despawner;", "getDespawner", "()Lcom/cobblemon/mod/common/api/entity/Despawner;", "setDespawner", "(Lcom/cobblemon/mod/common/api/entity/Despawner;)V", "Lcom/cobblemon/mod/common/api/drop/DropTable;", "drops", "Lcom/cobblemon/mod/common/api/drop/DropTable;", "getDrops", "()Lcom/cobblemon/mod/common/api/drop/DropTable;", "setDrops", "(Lcom/cobblemon/mod/common/api/drop/DropTable;)V", "Lcom/cobblemon/mod/common/entity/pokemon/effects/EffectTracker;", "effects", "Lcom/cobblemon/mod/common/entity/pokemon/effects/EffectTracker;", "getEffects", "()Lcom/cobblemon/mod/common/entity/pokemon/effects/EffectTracker;", "setEffects", "(Lcom/cobblemon/mod/common/entity/pokemon/effects/EffectTracker;)V", "Lcom/cobblemon/mod/common/entity/generic/GenericBedrockEntity;", "evolutionEntity", "Lcom/cobblemon/mod/common/entity/generic/GenericBedrockEntity;", "getEvolutionEntity", "()Lcom/cobblemon/mod/common/entity/generic/GenericBedrockEntity;", "setEvolutionEntity", "(Lcom/cobblemon/mod/common/entity/generic/GenericBedrockEntity;)V", "Lcom/cobblemon/mod/common/pokemon/FormData;", "getExposedForm", "()Lcom/cobblemon/mod/common/pokemon/FormData;", "exposedForm", "Lcom/cobblemon/mod/common/pokemon/Species;", "getExposedSpecies", "()Lcom/cobblemon/mod/common/pokemon/Species;", "exposedSpecies", "getForm", "form", "getFriendship", "friendship", "isBattling", "isBusy", "killer", "Lnet/minecraft/server/level/ServerPlayer;", "getKiller", "()Lnet/minecraft/server/level/ServerPlayer;", "setKiller", "getPhasingTargetId", "setPhasingTargetId", "phasingTargetId", "pokemon", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "getPokemon", "()Lcom/cobblemon/mod/common/pokemon/Pokemon;", "setPokemon", "(Lcom/cobblemon/mod/common/pokemon/Pokemon;)V", "queuedToDespawn", "getQueuedToDespawn", "setQueuedToDespawn", "Lcom/cobblemon/mod/common/api/reactive/SimpleObservable;", "removalObservable", "Lcom/cobblemon/mod/common/api/reactive/SimpleObservable;", "getRemovalObservable", "()Lcom/cobblemon/mod/common/api/reactive/SimpleObservable;", "Lcom/cobblemon/mod/common/api/scheduling/SchedulingTracker;", "schedulingTracker", "Lcom/cobblemon/mod/common/api/scheduling/SchedulingTracker;", "getSchedulingTracker", "()Lcom/cobblemon/mod/common/api/scheduling/SchedulingTracker;", "Lcom/cobblemon/mod/common/api/reactive/ObservableSubscription;", "subscriptions", "getSubscriptions", "Lcom/cobblemon/mod/common/block/entity/PokemonPastureBlockEntity$Tethering;", "tethering", "Lcom/cobblemon/mod/common/block/entity/PokemonPastureBlockEntity$Tethering;", "getTethering", "()Lcom/cobblemon/mod/common/block/entity/PokemonPastureBlockEntity$Tethering;", "setTethering", "(Lcom/cobblemon/mod/common/block/entity/PokemonPastureBlockEntity$Tethering;)V", "ticksLived", "I", "getTicksLived", "setTicksLived", "Lnet/minecraft/world/entity/EntityType;", "type", "<init>", "(Lnet/minecraft/world/level/Level;Lcom/cobblemon/mod/common/pokemon/Pokemon;Lnet/minecraft/world/entity/EntityType;)V", "Companion", "common"})
@SourceDebugExtension(value={"SMAP\nPokemonEntity.kt\nKotlin\n*S Kotlin\n*F\n+ 1 PokemonEntity.kt\ncom/cobblemon/mod/common/entity/pokemon/PokemonEntity\n+ 2 EventObservables.kt\ncom/cobblemon/mod/common/api/reactive/EventObservable\n+ 3 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n+ 4 EventObservables.kt\ncom/cobblemon/mod/common/api/reactive/EventObservable$post$1\n+ 5 EventObservables.kt\ncom/cobblemon/mod/common/api/reactive/CancelableObservable\n+ 6 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 7 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 8 EventObservables.kt\ncom/cobblemon/mod/common/api/reactive/CancelableObservable$postThen$1\n*L\n1#1,1260:1\n14#2,5:1261\n19#2:1269\n17#2,2:1271\n19#2:1281\n17#2,2:1301\n19#2:1311\n17#2,2:1347\n19#2:1357\n13579#3:1266\n13580#3:1268\n13579#3:1273\n13580#3:1280\n13579#3:1303\n13580#3:1310\n18987#3,2:1330\n13579#3:1349\n13580#3:1356\n14#4:1267\n40#5:1270\n41#5,6:1274\n47#5:1282\n39#5,2:1299\n41#5,2:1304\n44#5,3:1307\n47#5:1312\n39#5,2:1345\n41#5,2:1350\n44#5,3:1353\n47#5:1358\n1747#6,3:1283\n1747#6,3:1286\n1747#6,3:1290\n1747#6,3:1293\n1747#6,3:1296\n800#6,11:1313\n1360#6:1324\n1446#6,5:1325\n800#6,11:1332\n1855#6,2:1343\n1549#6:1359\n1620#6,3:1360\n1855#6,2:1363\n1855#6,2:1365\n1#7:1289\n39#8:1306\n39#8:1352\n*S KotlinDebug\n*F\n+ 1 PokemonEntity.kt\ncom/cobblemon/mod/common/entity/pokemon/PokemonEntity\n*L\n470#1:1261,5\n470#1:1269\n540#1:1271,2\n540#1:1281\n798#1:1301,2\n798#1:1311\n949#1:1347,2\n949#1:1357\n470#1:1266\n470#1:1268\n540#1:1273\n540#1:1280\n798#1:1303\n798#1:1310\n879#1:1330,2\n949#1:1349\n949#1:1356\n470#1:1267\n540#1:1270\n540#1:1274,6\n540#1:1282\n798#1:1299,2\n798#1:1304,2\n798#1:1307,3\n798#1:1312\n949#1:1345,2\n949#1:1350,2\n949#1:1353,3\n949#1:1358\n607#1:1283,3\n608#1:1286,3\n636#1:1290,3\n639#1:1293,3\n713#1:1296,3\n855#1:1313,11\n878#1:1324\n878#1:1325,5\n893#1:1332,11\n894#1:1343,2\n985#1:1359\n985#1:1360,3\n996#1:1363,2\n1020#1:1365,2\n798#1:1306\n949#1:1352\n*E\n"})
public class PokemonEntity
extends ShoulderRidingEntity
implements Poseable,
Shearable,
Schedulable {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private final SimpleObservable<Entity.RemovalReason> removalObservable;
    @NotNull
    private final List<ObservableSubscription<?>> subscriptions;
    @NotNull
    private final SchedulingTracker schedulingTracker;
    @NotNull
    private Pokemon pokemon;
    @NotNull
    private Despawner<PokemonEntity> despawner;
    @Nullable
    private ServerPlayer killer;
    @Nullable
    private GenericBedrockEntity evolutionEntity;
    private int ticksLived;
    @NotNull
    private final List<Object> busyLocks;
    @Nullable
    private DropTable drops;
    @Nullable
    private PokemonPastureBlockEntity.Tethering tethering;
    private boolean queuedToDespawn;
    private double blocksTraveled;
    private boolean countsTowardsSpawnCap;
    @NotNull
    private final PokemonSideDelegate delegate;
    @NotNull
    private EffectTracker effects;
    private static final EntityDataAccessor<String> SPECIES = SynchedEntityData.m_135353_(PokemonEntity.class, (EntityDataSerializer)EntityDataSerializers.f_135030_);
    private static final EntityDataAccessor<Component> NICKNAME = SynchedEntityData.m_135353_(PokemonEntity.class, (EntityDataSerializer)EntityDataSerializers.f_135031_);
    private static final EntityDataAccessor<Boolean> NICKNAME_VISIBLE = SynchedEntityData.m_135353_(PokemonEntity.class, (EntityDataSerializer)EntityDataSerializers.f_135035_);
    private static final EntityDataAccessor<Boolean> SHOULD_RENDER_NAME = SynchedEntityData.m_135353_(PokemonEntity.class, (EntityDataSerializer)EntityDataSerializers.f_135035_);
    private static final EntityDataAccessor<Boolean> MOVING = SynchedEntityData.m_135353_(PokemonEntity.class, (EntityDataSerializer)EntityDataSerializers.f_135035_);
    private static final EntityDataAccessor<Byte> BEHAVIOUR_FLAGS = SynchedEntityData.m_135353_(PokemonEntity.class, (EntityDataSerializer)EntityDataSerializers.f_135027_);
    private static final EntityDataAccessor<Integer> PHASING_TARGET_ID = SynchedEntityData.m_135353_(PokemonEntity.class, (EntityDataSerializer)EntityDataSerializers.f_135028_);
    private static final EntityDataAccessor<Byte> BEAM_MODE = SynchedEntityData.m_135353_(PokemonEntity.class, (EntityDataSerializer)EntityDataSerializers.f_135027_);
    private static final EntityDataAccessor<Optional<UUID>> BATTLE_ID = SynchedEntityData.m_135353_(PokemonEntity.class, (EntityDataSerializer)EntityDataSerializers.f_135041_);
    private static final EntityDataAccessor<Set<String>> ASPECTS = SynchedEntityData.m_135353_(PokemonEntity.class, (EntityDataSerializer)StringSetDataSerializer.INSTANCE);
    private static final EntityDataAccessor<Boolean> DYING_EFFECTS_STARTED = SynchedEntityData.m_135353_(PokemonEntity.class, (EntityDataSerializer)EntityDataSerializers.f_135035_);
    private static final EntityDataAccessor<PoseType> POSE_TYPE = SynchedEntityData.m_135353_(PokemonEntity.class, (EntityDataSerializer)PoseTypeDataSerializer.INSTANCE);
    private static final EntityDataAccessor<Integer> LABEL_LEVEL = SynchedEntityData.m_135353_(PokemonEntity.class, (EntityDataSerializer)EntityDataSerializers.f_135028_);
    private static final EntityDataAccessor<Boolean> HIDE_LABEL = SynchedEntityData.m_135353_(PokemonEntity.class, (EntityDataSerializer)EntityDataSerializers.f_135035_);
    private static final EntityDataAccessor<Boolean> UNBATTLEABLE = SynchedEntityData.m_135353_(PokemonEntity.class, (EntityDataSerializer)EntityDataSerializers.f_135035_);
    private static final EntityDataAccessor<Boolean> COUNTS_TOWARDS_SPAWN_CAP = SynchedEntityData.m_135353_(PokemonEntity.class, (EntityDataSerializer)EntityDataSerializers.f_135035_);
    private static final EntityDataAccessor<Float> SPAWN_DIRECTION = SynchedEntityData.m_135353_(PokemonEntity.class, (EntityDataSerializer)EntityDataSerializers.f_135029_);
    private static final EntityDataAccessor<Integer> FRIENDSHIP = SynchedEntityData.m_135353_(PokemonEntity.class, (EntityDataSerializer)EntityDataSerializers.f_135028_);
    @NotNull
    public static final String BATTLE_LOCK = "battle";

    public PokemonEntity(@NotNull Level world, @NotNull Pokemon pokemon, @NotNull EntityType<? extends PokemonEntity> type) {
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        Intrinsics.checkNotNullParameter(type, (String)"type");
        super(type, world);
        this.removalObservable = new SimpleObservable();
        this.subscriptions = new ArrayList();
        this.schedulingTracker = new SchedulingTracker();
        this.pokemon = pokemon;
        this.despawner = Cobblemon.INSTANCE.getBestSpawner().getDefaultPokemonDespawner();
        this.busyLocks = new ArrayList();
        this.countsTowardsSpawnCap = true;
        this.delegate = world.f_46443_ ? (PokemonSideDelegate)new PokemonClientDelegate() : (PokemonSideDelegate)new PokemonServerDelegate();
        this.effects = new EffectTracker(this);
        this.getDelegate().initialize((Entity)this);
        this.getDelegate().changePokemon(pokemon);
        this.m_6210_();
    }

    public /* synthetic */ PokemonEntity(Level level, Pokemon pokemon, EntityType entityType, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 2) != 0) {
            pokemon = new Pokemon();
        }
        if ((n & 4) != 0) {
            entityType = CobblemonEntities.POKEMON;
        }
        this(level, pokemon, entityType);
    }

    @NotNull
    public final SimpleObservable<Entity.RemovalReason> getRemovalObservable() {
        return this.removalObservable;
    }

    @NotNull
    public final List<ObservableSubscription<?>> getSubscriptions() {
        return this.subscriptions;
    }

    @Override
    @NotNull
    public SchedulingTracker getSchedulingTracker() {
        return this.schedulingTracker;
    }

    @NotNull
    public final FormData getForm() {
        return this.pokemon.getForm();
    }

    @NotNull
    public final FormPokemonBehaviour getBehaviour() {
        return this.getForm().getBehaviour();
    }

    @NotNull
    public final Pokemon getPokemon() {
        return this.pokemon;
    }

    public final void setPokemon(@NotNull Pokemon value2) {
        Intrinsics.checkNotNullParameter((Object)value2, (String)"value");
        this.pokemon = value2;
        this.getDelegate().changePokemon(value2);
        this.m_274367_(this.getBehaviour().getMoving().getStepHeight());
        this.updateEyeHeight();
    }

    @NotNull
    public final Despawner<PokemonEntity> getDespawner() {
        return this.despawner;
    }

    public final void setDespawner(@NotNull Despawner<PokemonEntity> despawner) {
        Intrinsics.checkNotNullParameter(despawner, (String)"<set-?>");
        this.despawner = despawner;
    }

    @Nullable
    public final ServerPlayer getKiller() {
        return this.killer;
    }

    public final void setKiller(@Nullable ServerPlayer serverPlayer) {
        this.killer = serverPlayer;
    }

    @Nullable
    public final GenericBedrockEntity getEvolutionEntity() {
        return this.evolutionEntity;
    }

    public final void setEvolutionEntity(@Nullable GenericBedrockEntity genericBedrockEntity) {
        this.evolutionEntity = genericBedrockEntity;
    }

    public final int getTicksLived() {
        return this.ticksLived;
    }

    public final void setTicksLived(int n) {
        this.ticksLived = n;
    }

    @NotNull
    public final List<Object> getBusyLocks() {
        return this.busyLocks;
    }

    public final boolean isBusy() {
        return !((Collection)this.busyLocks).isEmpty();
    }

    @NotNull
    public final Set<String> getAspects() {
        Object object = this.f_19804_.m_135370_(ASPECTS);
        Intrinsics.checkNotNullExpressionValue((Object)object, (String)"dataTracker.get(ASPECTS)");
        return (Set)object;
    }

    @Nullable
    public final UUID getBattleId() {
        return ((Optional)this.f_19804_.m_135370_(BATTLE_ID)).orElse(null);
    }

    public final void setBattleId(@Nullable UUID value2) {
        this.f_19804_.m_135381_(BATTLE_ID, Optional.ofNullable(value2));
    }

    public final boolean isBattling() {
        return ((Optional)this.f_19804_.m_135370_(BATTLE_ID)).isPresent();
    }

    public final int getFriendship() {
        Object object = this.f_19804_.m_135370_(FRIENDSHIP);
        Intrinsics.checkNotNullExpressionValue((Object)object, (String)"dataTracker.get(FRIENDSHIP)");
        return ((Number)object).intValue();
    }

    @Nullable
    public final DropTable getDrops() {
        return this.drops;
    }

    public final void setDrops(@Nullable DropTable dropTable) {
        this.drops = dropTable;
    }

    @Nullable
    public final PokemonPastureBlockEntity.Tethering getTethering() {
        return this.tethering;
    }

    public final void setTethering(@Nullable PokemonPastureBlockEntity.Tethering tethering) {
        this.tethering = tethering;
    }

    public final boolean getQueuedToDespawn() {
        return this.queuedToDespawn;
    }

    public final void setQueuedToDespawn(boolean bl) {
        this.queuedToDespawn = bl;
    }

    public final double getBlocksTraveled() {
        return this.blocksTraveled;
    }

    public final void setBlocksTraveled(double d) {
        this.blocksTraveled = d;
    }

    public final boolean getCountsTowardsSpawnCap() {
        return this.countsTowardsSpawnCap;
    }

    public final void setCountsTowardsSpawnCap(boolean bl) {
        this.countsTowardsSpawnCap = bl;
    }

    public final int getBeamMode() {
        return ((Number)this.f_19804_.m_135370_(BEAM_MODE)).byteValue();
    }

    public final void setBeamMode(int value2) {
        this.f_19804_.m_135381_(BEAM_MODE, (Object)((byte)value2));
    }

    public final int getPhasingTargetId() {
        Object object = this.f_19804_.m_135370_(PHASING_TARGET_ID);
        Intrinsics.checkNotNullExpressionValue((Object)object, (String)"dataTracker.get(PHASING_TARGET_ID)");
        return ((Number)object).intValue();
    }

    public final void setPhasingTargetId(int value2) {
        this.f_19804_.m_135381_(PHASING_TARGET_ID, (Object)value2);
    }

    @NotNull
    public PokemonSideDelegate getDelegate() {
        return this.delegate;
    }

    @NotNull
    public final EffectTracker getEffects() {
        return this.effects;
    }

    public final void setEffects(@NotNull EffectTracker effectTracker) {
        Intrinsics.checkNotNullParameter((Object)effectTracker, (String)"<set-?>");
        this.effects = effectTracker;
    }

    @NotNull
    public final Species getExposedSpecies() {
        Object object = this.effects.getMockEffect();
        if (object == null || (object = object.getExposedSpecies()) == null) {
            object = this.pokemon.getSpecies();
        }
        return object;
    }

    @NotNull
    public final FormData getExposedForm() {
        Object object = this.effects.getMockEffect();
        if (object == null || (object = object.getExposedForm()) == null) {
            object = this.pokemon.getForm();
        }
        return object;
    }

    protected void m_8097_() {
        super.m_8097_();
        this.f_19804_.m_135372_(SPECIES, (Object)"");
        this.f_19804_.m_135372_(NICKNAME, (Object)Component.m_237119_());
        this.f_19804_.m_135372_(NICKNAME_VISIBLE, (Object)true);
        this.f_19804_.m_135372_(SHOULD_RENDER_NAME, (Object)true);
        this.f_19804_.m_135372_(MOVING, (Object)false);
        this.f_19804_.m_135372_(BEHAVIOUR_FLAGS, (Object)0);
        this.f_19804_.m_135372_(BEAM_MODE, (Object)0);
        this.f_19804_.m_135372_(PHASING_TARGET_ID, (Object)-1);
        this.f_19804_.m_135372_(BATTLE_ID, Optional.empty());
        this.f_19804_.m_135372_(ASPECTS, (Object)SetsKt.emptySet());
        this.f_19804_.m_135372_(DYING_EFFECTS_STARTED, (Object)false);
        this.f_19804_.m_135372_(POSE_TYPE, (Object)PoseType.STAND);
        this.f_19804_.m_135372_(LABEL_LEVEL, (Object)1);
        this.f_19804_.m_135372_(HIDE_LABEL, (Object)false);
        this.f_19804_.m_135372_(UNBATTLEABLE, (Object)false);
        this.f_19804_.m_135372_(COUNTS_TOWARDS_SPAWN_CAP, (Object)true);
        this.f_19804_.m_135372_(SPAWN_DIRECTION, (Object)Float.valueOf(this.m_9236_().f_46441_.m_188501_() * 360.0f));
        this.f_19804_.m_135372_(FRIENDSHIP, (Object)0);
    }

    public void m_7350_(@NotNull EntityDataAccessor<?> data) {
        EntityDataAccessor<?> entityDataAccessor;
        Intrinsics.checkNotNullParameter(data, (String)"data");
        super.m_7350_(data);
        if (this.getDelegate() != null) {
            this.getDelegate().onTrackedDataSet(data);
        }
        if (Intrinsics.areEqual(entityDataAccessor = data, SPECIES)) {
            this.m_6210_();
        } else if (Intrinsics.areEqual(entityDataAccessor, POSE_TYPE)) {
            Object object = this.f_19804_.m_135370_(data);
            Intrinsics.checkNotNull((Object)object, (String)"null cannot be cast to non-null type bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.PoseType");
            PoseType value2 = (PoseType)((Object)object);
            if (value2 == PoseType.FLY || value2 == PoseType.HOVER) {
                this.m_20242_(true);
            } else {
                this.m_20242_(false);
            }
        } else if (Intrinsics.areEqual(entityDataAccessor, BATTLE_ID)) {
            if (this.getBattleId() != null) {
                this.busyLocks.remove(BATTLE_LOCK);
                this.busyLocks.add(BATTLE_LOCK);
            } else {
                this.busyLocks.remove(BATTLE_LOCK);
            }
        }
    }

    public boolean m_203441_(@NotNull FluidState state) {
        Intrinsics.checkNotNullParameter((Object)state, (String)"state");
        return state.m_205070_(FluidTags.f_13131_) && !this.m_204029_(FluidTags.f_13131_) ? this.getBehaviour().getMoving().getSwim().getCanWalkOnWater() : (state.m_205070_(FluidTags.f_13132_) && !this.m_204029_(FluidTags.f_13132_) ? this.getBehaviour().getMoving().getSwim().getCanWalkOnLava() : super.m_203441_(state));
    }

    public void m_7822_(byte status) {
        this.getDelegate().handleStatus(status);
        super.m_7822_(status);
    }

    public void m_8119_() {
        super.m_8119_();
        this.m_21310_(0);
        if (this.queuedToDespawn) {
            this.m_142687_(Entity.RemovalReason.DISCARDED);
            return;
        }
        if (this.evolutionEntity != null) {
            GenericBedrockEntity genericBedrockEntity = this.evolutionEntity;
            Intrinsics.checkNotNull((Object)genericBedrockEntity);
            PokemonEntity pokemonEntity = this.pokemon.getEntity();
            Intrinsics.checkNotNull((Object)pokemonEntity);
            double d = pokemonEntity.m_20185_();
            PokemonEntity pokemonEntity2 = this.pokemon.getEntity();
            Intrinsics.checkNotNull((Object)pokemonEntity2);
            double d2 = pokemonEntity2.m_20186_();
            PokemonEntity pokemonEntity3 = this.pokemon.getEntity();
            Intrinsics.checkNotNull((Object)pokemonEntity3);
            genericBedrockEntity.m_6034_(d, d2, pokemonEntity3.m_20189_());
            PokemonEntity pokemonEntity4 = this.pokemon.getEntity();
            Intrinsics.checkNotNull((Object)pokemonEntity4);
            pokemonEntity4.f_21344_.m_26573_();
        }
        this.getDelegate().tick((Entity)this);
        int n = this.ticksLived;
        this.ticksLived = n + 1;
        if (this.ticksLived % 20 == 0) {
            this.updateEyeHeight();
        }
        if (this.ticksLived <= 20) {
            this.m_147271_();
            Float spawnDirection = (Float)this.f_19804_.m_135370_(SPAWN_DIRECTION);
            Intrinsics.checkNotNullExpressionValue((Object)spawnDirection, (String)"spawnDirection");
            this.m_5618_(spawnDirection.floatValue());
            this.f_20884_ = spawnDirection.floatValue();
        }
        if (this.tethering != null) {
            PokemonPastureBlockEntity.Tethering tethering = this.tethering;
            Intrinsics.checkNotNull((Object)tethering);
            if (!tethering.getBox().m_82393_(this.m_20185_(), this.m_20186_(), this.m_20189_())) {
                this.tethering = null;
                this.pokemon.recall();
            }
        }
        if (this.tethering != null && this.m_9236_().m_46003_(this.m_21805_()) != null) {
            UUID uUID = this.m_21805_();
            if (uUID != null) {
                Pokemon actualPokemon;
                UUID it = uUID;
                boolean bl = false;
                PCStore pCStore = Cobblemon.INSTANCE.getStorage().getPC(it);
                UUID uUID2 = this.pokemon.getUuid();
                Intrinsics.checkNotNullExpressionValue((Object)uUID2, (String)"this.pokemon.uuid");
                Pokemon pokemon = actualPokemon = pCStore.get(uUID2);
                if (pokemon != null) {
                    Pokemon it2 = pokemon;
                    boolean bl2 = false;
                    if (it2 != this.pokemon) {
                        this.setPokemon(it2);
                    }
                }
            }
        }
        this.getSchedulingTracker().update(0.05f);
    }

    public final void setMoveControl(@NotNull MoveControl moveControl) {
        Intrinsics.checkNotNullParameter((Object)moveControl, (String)"moveControl");
        this.f_21342_ = moveControl;
    }

    public boolean m_6040_() {
        return this.getBehaviour().getMoving().getSwim().getCanBreatheUnderwater();
    }

    public boolean m_5825_() {
        return this.pokemon.isFireImmune();
    }

    public boolean m_142535_(float fallDistance, float damageMultiplier, @Nullable DamageSource damageSource) {
        return CollectionsKt.contains(this.pokemon.getTypes(), (Object)ElementalTypes.INSTANCE.getFLYING()) || Intrinsics.areEqual((Object)this.pokemon.getAbility().getName(), (Object)"levitate") || this.pokemon.getSpecies().getBehaviour().getMoving().getFly().getCanFly() ? false : super.m_142535_(fallDistance, damageMultiplier, damageSource);
    }

    public boolean m_6673_(@NotNull DamageSource damageSource) {
        Intrinsics.checkNotNullParameter((Object)damageSource, (String)"damageSource");
        if (!((Collection)this.busyLocks).isEmpty()) {
            return true;
        }
        if (this.m_21805_() != null && (damageSource.m_7639_() instanceof Player || damageSource.m_276093_(DamageTypes.f_268612_))) {
            return true;
        }
        if (!Cobblemon.INSTANCE.getConfig().getPlayerDamagePokemon() && damageSource.m_7639_() instanceof Player) {
            return true;
        }
        return super.m_6673_(damageSource);
    }

    public final boolean isUncatchable() {
        return this.pokemon.isUncatchable();
    }

    @NotNull
    public final CompletableFuture<Pokemon> recallWithAnimation() {
        LivingEntity owner = this.m_269323_();
        CompletableFuture<Pokemon> future2 = new CompletableFuture<Pokemon>();
        Integer n = (Integer)this.f_19804_.m_135370_(PHASING_TARGET_ID);
        int n2 = -1;
        if (n != null && n == n2 && owner != null) {
            Level level = owner.m_9236_();
            Intrinsics.checkNotNullExpressionValue((Object)level, (String)"owner.getWorld()");
            Vec3 vec3 = this.m_20182_();
            Intrinsics.checkNotNullExpressionValue((Object)vec3, (String)"pos");
            WorldExtensionsKt.playSoundServer$default(level, vec3, CobblemonSounds.POKE_BALL_RECALL, null, 0.6f, 0.0f, 20, null);
            this.f_19804_.m_135381_(PHASING_TARGET_ID, (Object)owner.m_19879_());
            this.f_19804_.m_135381_(BEAM_MODE, (Object)3);
            PokemonState state = this.pokemon.getState();
            this.after(1.5f, (Function0<Unit>)((Function0)new Function0<Unit>(state, this, future2){
                final /* synthetic */ PokemonState $state;
                final /* synthetic */ PokemonEntity this$0;
                final /* synthetic */ CompletableFuture<Pokemon> $future;
                {
                    this.$state = $state;
                    this.this$0 = $receiver;
                    this.$future = $future;
                    super(0);
                }

                public final void invoke() {
                    if (Intrinsics.areEqual((Object)this.$state, (Object)this.this$0.getPokemon().getState())) {
                        this.this$0.getPokemon().recall();
                    }
                    this.$future.complete(this.this$0.getPokemon());
                }
            }));
        } else {
            this.pokemon.recall();
            future2.complete(this.pokemon);
        }
        return future2;
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public CompoundTag m_20240_(@NotNull CompoundTag nbt) {
        void $this$iv;
        Intrinsics.checkNotNullParameter((Object)nbt, (String)"nbt");
        PokemonPastureBlockEntity.Tethering tethering = this.tethering;
        if (tethering != null) {
            CompoundTag tetheringNbt = new CompoundTag();
            tetheringNbt.m_128362_("TetheringId", tethering.getTetheringId());
            tetheringNbt.m_128362_("UUID", tethering.getPokemonId());
            tetheringNbt.m_128362_("PokemonOwnerId", tethering.getPlayerId());
            tetheringNbt.m_128362_("PCId", tethering.getPcId());
            tetheringNbt.m_128365_("TetherMinRoamPos", (Tag)NbtUtils.m_129224_((BlockPos)tethering.getMinRoamPos()));
            tetheringNbt.m_128365_("TetherMaxRoamPos", (Tag)NbtUtils.m_129224_((BlockPos)tethering.getMaxRoamPos()));
            nbt.m_128365_("Tethering", (Tag)tetheringNbt);
        } else {
            nbt.m_128365_("Pokemon", (Tag)this.pokemon.saveToNBT(new CompoundTag()));
        }
        UUID battleIdToSave = this.getBattleId();
        if (battleIdToSave != null) {
            nbt.m_128362_("BattleId", battleIdToSave);
        }
        nbt.m_128359_("PoseType", ((PoseType)((Object)this.f_19804_.m_135370_(POSE_TYPE))).name());
        Object object = this.f_19804_.m_135370_(BEHAVIOUR_FLAGS);
        Intrinsics.checkNotNullExpressionValue((Object)object, (String)"dataTracker.get(BEHAVIOUR_FLAGS)");
        nbt.m_128344_("BehaviourFlags", ((Number)object).byteValue());
        Object object2 = this.f_19804_.m_135370_(HIDE_LABEL);
        Intrinsics.checkNotNullExpressionValue((Object)object2, (String)"dataTracker.get(HIDE_LABEL)");
        if (((Boolean)object2).booleanValue()) {
            nbt.m_128379_("HideLabel", true);
        }
        Object object3 = this.f_19804_.m_135370_(UNBATTLEABLE);
        Intrinsics.checkNotNullExpressionValue((Object)object3, (String)"dataTracker.get(UNBATTLEABLE)");
        if (((Boolean)object3).booleanValue()) {
            nbt.m_128379_("Unbattleable", true);
        }
        if (!this.countsTowardsSpawnCap) {
            nbt.m_128379_("CountsTowardsSpawnCap", false);
        }
        nbt.m_128365_("EntityEffects", (Tag)this.effects.saveToNbt());
        EventObservable<PokemonEntitySaveEvent> eventObservable = CobblemonEvents.POKEMON_ENTITY_SAVE;
        PokemonEntitySaveEvent[] pokemonEntitySaveEventArray = new PokemonEntitySaveEvent[]{new PokemonEntitySaveEvent(this, nbt)};
        PokemonEntitySaveEvent[] events$iv = pokemonEntitySaveEventArray;
        boolean $i$f$post = false;
        $this$iv.emit(Arrays.copyOf(events$iv, events$iv.length));
        PokemonEntitySaveEvent[] $this$forEach$iv$iv = events$iv;
        boolean $i$f$forEach = false;
        int n = $this$forEach$iv$iv.length;
        for (int i = 0; i < n; ++i) {
            PokemonEntitySaveEvent element$iv$iv;
            PokemonEntitySaveEvent pokemonEntitySaveEvent = element$iv$iv = $this$forEach$iv$iv[i];
            boolean bl = false;
            PokemonEntitySaveEvent it = pokemonEntitySaveEvent;
        }
        CompoundTag compoundTag = super.m_20240_(nbt);
        Intrinsics.checkNotNullExpressionValue((Object)compoundTag, (String)"super.writeNbt(nbt)");
        return compoundTag;
    }

    /*
     * WARNING - void declaration
     */
    public void m_20258_(@NotNull CompoundTag nbt) {
        void this_$iv$iv;
        Object object;
        PokemonBattle battle2;
        UUID savedBattleId;
        Cancelable[] maxRoamPos;
        Object minRoamPos;
        Intrinsics.checkNotNullParameter((Object)nbt, (String)"nbt");
        super.m_20258_(nbt);
        if (nbt.m_128441_("Tethering")) {
            tetheringNBT = nbt.m_128469_("Tethering");
            UUID tetheringId = tetheringNBT.m_128342_("TetheringId");
            UUID pcId = tetheringNBT.m_128342_("PCId");
            UUID pokemonId = tetheringNBT.m_128342_("UUID");
            UUID playerId = tetheringNBT.m_128342_("PokemonOwnerId");
            minRoamPos = NbtUtils.m_129239_((CompoundTag)tetheringNBT.m_128469_("TetherMinRoamPos"));
            maxRoamPos = NbtUtils.m_129239_((CompoundTag)tetheringNBT.m_128469_("TetherMaxRoamPos"));
            PokemonStoreManager pokemonStoreManager = Cobblemon.INSTANCE.getStorage();
            Intrinsics.checkNotNullExpressionValue((Object)pcId, (String)"pcId");
            PCStore pCStore = pokemonStoreManager.getPC(pcId);
            Intrinsics.checkNotNullExpressionValue((Object)pokemonId, (String)"pokemonId");
            Pokemon loadedPokemon = pCStore.get(pokemonId);
            if (loadedPokemon != null && Intrinsics.areEqual((Object)loadedPokemon.getTetheringId(), (Object)tetheringId)) {
                this.setPokemon(loadedPokemon);
                Intrinsics.checkNotNullExpressionValue((Object)minRoamPos, (String)"minRoamPos");
                Intrinsics.checkNotNullExpressionValue((Object)maxRoamPos, (String)"maxRoamPos");
                Intrinsics.checkNotNullExpressionValue((Object)playerId, (String)"playerId");
                Intrinsics.checkNotNullExpressionValue((Object)tetheringId, (String)"tetheringId");
                this.tethering = new PokemonPastureBlockEntity.Tethering((BlockPos)minRoamPos, (BlockPos)maxRoamPos, playerId, "", tetheringId, pokemonId, pcId, this.m_19879_());
            } else {
                this.setPokemon(new Pokemon());
                this.m_21153_(0.0f);
            }
        } else {
            PokemonEntity pokemonEntity;
            PokemonEntity pokemonEntity2 = this;
            try {
                pokemonEntity = pokemonEntity2;
                Pokemon pokemon = new Pokemon();
                CompoundTag compoundTag = nbt.m_128469_("Pokemon");
                Intrinsics.checkNotNullExpressionValue((Object)compoundTag, (String)"nbt.getCompound(DataKeys.POKEMON)");
                tetheringNBT = pokemon.loadFromNBT(compoundTag);
            }
            catch (InvalidSpeciesException tetheringId) {
                pokemonEntity = pokemonEntity2;
                this.m_21153_(0.0f);
                tetheringNBT = new Pokemon();
            }
            pokemonEntity.setPokemon((Pokemon)tetheringNBT);
        }
        UUID uUID = savedBattleId = nbt.m_128403_("BattleId") ? nbt.m_128342_("BattleId") : null;
        if (savedBattleId != null && (battle2 = BattleRegistry.INSTANCE.getBattle(savedBattleId)) != null) {
            this.setBattleId(savedBattleId);
        }
        if (nbt.m_128441_("EntityEffects")) {
            CompoundTag compoundTag = nbt.m_128469_("EntityEffects");
            Intrinsics.checkNotNullExpressionValue((Object)compoundTag, (String)"nbt.getCompound(DataKeys.ENTITY_EFFECTS)");
            this.effects.loadFromNBT(compoundTag);
        }
        if ((object = this.effects.getMockEffect()) == null || (object = object.getMock()) == null || (object = ((PokemonProperties)object).getSpecies()) == null) {
            String string = this.pokemon.getSpecies().getResourceIdentifier().toString();
            object = string;
            Intrinsics.checkNotNullExpressionValue((Object)string, (String)"pokemon.species.resourceIdentifier.toString()");
        }
        this.f_19804_.m_135381_(SPECIES, object);
        MutableComponent mutableComponent = this.pokemon.getNickname();
        if (mutableComponent == null) {
            mutableComponent = Component.m_237119_();
        }
        this.f_19804_.m_135381_(NICKNAME, (Object)mutableComponent);
        this.f_19804_.m_135381_(LABEL_LEVEL, (Object)this.pokemon.getLevel());
        String string = nbt.m_128461_("PoseType");
        Intrinsics.checkNotNullExpressionValue((Object)string, (String)"nbt.getString(DataKeys.POKEMON_POSE_TYPE)");
        this.f_19804_.m_135381_(POSE_TYPE, (Object)PoseType.valueOf(string));
        this.f_19804_.m_135381_(BEHAVIOUR_FLAGS, (Object)nbt.m_128445_("BehaviourFlags"));
        if (nbt.m_128441_("HideLabel")) {
            this.f_19804_.m_135381_(HIDE_LABEL, (Object)nbt.m_128471_("HideLabel"));
        }
        if (nbt.m_128441_("Unbattleable")) {
            this.f_19804_.m_135381_(UNBATTLEABLE, (Object)nbt.m_128471_("Unbattleable"));
        }
        if (nbt.m_128441_("CountsTowardsSpawnCap")) {
            this.countsTowardsSpawnCap = nbt.m_128471_("CountsTowardsSpawnCap");
        }
        CancelableObservable<PokemonEntityLoadEvent> cancelableObservable = CobblemonEvents.POKEMON_ENTITY_LOAD;
        PokemonEntityLoadEvent pokemonEntityLoadEvent = new PokemonEntityLoadEvent(this, nbt);
        CancelableObservable<PokemonEntityLoadEvent> this_$iv = cancelableObservable;
        boolean $i$f$postThen = false;
        minRoamPos = this_$iv;
        maxRoamPos = new Cancelable[]{pokemonEntityLoadEvent};
        Cancelable[] events$iv$iv = maxRoamPos;
        boolean $i$f$post = false;
        this_$iv$iv.emit(Arrays.copyOf(events$iv$iv, events$iv$iv.length));
        Cancelable[] $this$forEach$iv$iv$iv = events$iv$iv;
        boolean $i$f$forEach = false;
        int n = $this$forEach$iv$iv$iv.length;
        for (int i = 0; i < n; ++i) {
            PokemonEntityLoadEvent it;
            Cancelable element$iv$iv$iv;
            Cancelable it$iv = element$iv$iv$iv = $this$forEach$iv$iv$iv[i];
            boolean bl = false;
            if (it$iv.isCanceled()) {
                it = (PokemonEntityLoadEvent)it$iv;
                boolean bl2 = false;
                this.m_146870_();
                continue;
            }
            it = (PokemonEntityLoadEvent)it$iv;
            boolean bl3 = false;
        }
    }

    @NotNull
    public Packet<ClientGamePacketListener> m_5654_() {
        Packet packet = super.m_5654_();
        Intrinsics.checkNotNull((Object)packet, (String)"null cannot be cast to non-null type net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket");
        return CobblemonNetwork.INSTANCE.asVanillaClientBound((NetworkPacket)new SpawnPokemonPacket(this, (ClientboundAddEntityPacket)packet));
    }

    public float m_21439_(@NotNull BlockPathTypes nodeType) {
        Intrinsics.checkNotNullParameter((Object)nodeType, (String)"nodeType");
        return nodeType == BlockPathTypes.OPEN ? 2.0f : super.m_21439_(nodeType);
    }

    @NotNull
    public PokemonNavigation getNavigation() {
        PathNavigation pathNavigation = this.f_21344_;
        Intrinsics.checkNotNull((Object)pathNavigation, (String)"null cannot be cast to non-null type bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.ai.PokemonNavigation");
        return (PokemonNavigation)pathNavigation;
    }

    @NotNull
    protected PokemonNavigation createNavigation(@NotNull Level world) {
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        return new PokemonNavigation(world, this);
    }

    public void m_8099_() {
        if (this.pokemon == null) {
            return;
        }
        this.f_21342_ = new PokemonMoveControl(this);
        this.f_21345_.m_262460_(PokemonEntity::initGoals$lambda$4);
        this.f_21345_.m_25352_(0, (Goal)new PokemonInBattleMovementGoal(this, 10));
        this.f_21345_.m_25352_(0, new Goal(this){
            final /* synthetic */ PokemonEntity this$0;
            {
                this.this$0 = $receiver;
            }

            /*
             * Enabled force condition propagation
             * Lifted jumps to return sites
             */
            public boolean m_8036_() {
                Integer n = (Integer)PokemonEntity.access$getDataTracker$p$s-566291850(this.this$0).m_135370_(PokemonEntity.Companion.getPHASING_TARGET_ID());
                int n2 = -1;
                if (n == null) return true;
                if (n != n2) return true;
                PersistentStatusContainer persistentStatusContainer = this.this$0.getPokemon().getStatus();
                if (Intrinsics.areEqual((Object)(persistentStatusContainer != null ? persistentStatusContainer.getStatus() : null), (Object)Statuses.INSTANCE.getSLEEP())) return true;
                Object object = PokemonEntity.access$getDataTracker$p$s-566291850(this.this$0).m_135370_(PokemonEntity.Companion.getDYING_EFFECTS_STARTED());
                Intrinsics.checkNotNullExpressionValue((Object)object, (String)"dataTracker.get(DYING_EFFECTS_STARTED)");
                if ((Boolean)object != false) return true;
                if (this.this$0.getEvolutionEntity() == null) return false;
                return true;
            }

            public boolean m_8045_() {
                PersistentStatusContainer persistentStatusContainer = this.this$0.getPokemon().getStatus();
                if (Intrinsics.areEqual((Object)(persistentStatusContainer != null ? persistentStatusContainer.getStatus() : null), (Object)Statuses.INSTANCE.getSLEEP()) && !this.this$0.canSleep() && !this.this$0.isBusy()) {
                    return false;
                }
                PersistentStatusContainer persistentStatusContainer2 = this.this$0.getPokemon().getStatus();
                return Intrinsics.areEqual((Object)(persistentStatusContainer2 != null ? persistentStatusContainer2.getStatus() : null), (Object)Statuses.INSTANCE.getSLEEP()) || this.this$0.isBusy();
            }

            public EnumSet<Goal.Flag> m_7684_() {
                return EnumSet.allOf(Goal.Flag.class);
            }
        });
        this.f_21345_.m_25352_(1, (Goal)new PokemonBreatheAirGoal(this));
        this.f_21345_.m_25352_(2, (Goal)new PokemonFloatToSurfaceGoal(this));
        this.f_21345_.m_25352_(3, (Goal)new PokemonFollowOwnerGoal(this, 1.0, 8.0f, 2.0f, false));
        this.f_21345_.m_25352_(4, (Goal)new PokemonMoveIntoFluidGoal(this));
        this.f_21345_.m_25352_(5, (Goal)new SleepOnTrainerGoal(this));
        this.f_21345_.m_25352_(5, (Goal)new WildRestGoal(this));
        if (this.pokemon.getFeature("sheared") != null) {
            this.f_21345_.m_25352_(5, (Goal)new EatBlockGoal((Mob)this));
        }
        this.f_21345_.m_25352_(6, (Goal)new PokemonWanderAroundGoal(this));
        this.f_21345_.m_25352_(7, (Goal)new PokemonLookAtEntityGoal(this, ServerPlayer.class, 5.0f));
        this.f_21345_.m_25352_(8, (Goal)new PokemonPointAtSpawnGoal(this));
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final boolean canSleep() {
        Registry registry;
        Object element$iv;
        RegistryLikeCondition it;
        Iterator iterator;
        boolean $i$f$any;
        Iterable $this$any$iv;
        RestBehaviour rest = this.getBehaviour().getResting();
        int worldTime = (int)(this.m_9236_().m_46468_() % (long)24000);
        int light = this.m_9236_().m_46803_(this.m_20183_());
        Block block = this.m_9236_().m_8055_(this.m_20183_()).m_60734_();
        Biome biome2 = (Biome)this.m_9236_().m_204166_(this.m_20183_()).m_203334_();
        if (!rest.getCanSleep()) return false;
        if (this.getBehaviourFlag(PokemonBehaviourFlag.EXCITED)) return false;
        if (!this.getBehaviour().getResting().getTimes().contains(worldTime)) return false;
        IntRange intRange = rest.getLight();
        int n = intRange.getFirst();
        if (light > intRange.getLast()) return false;
        if (n > light) return false;
        boolean bl = true;
        if (!bl) return false;
        if (!rest.getBlocks().isEmpty()) {
            Registry registry2;
            $this$any$iv = rest.getBlocks();
            $i$f$any = false;
            if ($this$any$iv instanceof Collection && ((Collection)$this$any$iv).isEmpty()) {
                return false;
            }
            iterator = $this$any$iv.iterator();
            do {
                if (!iterator.hasNext()) return false;
                element$iv = iterator.next();
                it = (RegistryLikeCondition)element$iv;
                boolean bl2 = false;
                Intrinsics.checkNotNullExpressionValue((Object)block, (String)"block");
                registry2 = this.m_9236_().m_9598_().m_175515_(Registries.f_256747_);
                Intrinsics.checkNotNullExpressionValue((Object)registry2, (String)"this.world.registryManager.get(RegistryKeys.BLOCK)");
            } while (!it.fits(block, registry2));
            boolean bl3 = true;
            if (!bl3) return false;
        }
        if (rest.getBiomes().isEmpty()) return true;
        $this$any$iv = rest.getBiomes();
        $i$f$any = false;
        if ($this$any$iv instanceof Collection && ((Collection)$this$any$iv).isEmpty()) {
            return false;
        }
        iterator = $this$any$iv.iterator();
        do {
            if (!iterator.hasNext()) return false;
            element$iv = iterator.next();
            it = (RegistryLikeCondition)element$iv;
            boolean bl4 = false;
            Intrinsics.checkNotNullExpressionValue((Object)biome2, (String)"biome");
            registry = this.m_9236_().m_9598_().m_175515_(Registries.f_256952_);
            Intrinsics.checkNotNullExpressionValue((Object)registry, (String)"this.world.registryManager.get(RegistryKeys.BIOME)");
        } while (!it.fits(biome2, registry));
        return true;
    }

    @Nullable
    public Void createChild(@NotNull ServerLevel level, @NotNull AgeableMob partner) {
        Intrinsics.checkNotNullParameter((Object)level, (String)"level");
        Intrinsics.checkNotNullParameter((Object)partner, (String)"partner");
        return null;
    }

    public boolean m_29897_() {
        return this.pokemon.getForm().getShoulderMountable();
    }

    @NotNull
    public InteractionResult m_6071_(@NotNull Player player, @NotNull InteractionHand hand) {
        Object v0;
        ItemStack itemStack;
        block60: {
            Intrinsics.checkNotNullParameter((Object)player, (String)"player");
            Intrinsics.checkNotNullParameter((Object)hand, (String)"hand");
            itemStack = player.m_21120_(hand);
            Iterable iterable = SpeciesFeatures.INSTANCE.getFeaturesFor(this.pokemon.getSpecies());
            for (Object object : iterable) {
                SpeciesFeatureProvider it = (SpeciesFeatureProvider)object;
                boolean bl = false;
                if (!(it instanceof ChoiceSpeciesFeatureProvider && ((ChoiceSpeciesFeatureProvider)it).getKeys().contains("color"))) continue;
                v0 = object;
                break block60;
            }
            v0 = null;
        }
        SpeciesFeatureProvider colorFeatureType = v0;
        StringSpeciesFeature colorFeature = (StringSpeciesFeature)this.pokemon.getFeature("color");
        if (Intrinsics.areEqual((Object)this.m_21805_(), (Object)player.m_20148_()) || this.m_21805_() == null) {
            if (itemStack.m_150930_(Items.f_42574_) && this.m_6220_()) {
                this.m_5851_(SoundSource.PLAYERS);
                this.m_146852_(GameEvent.f_157781_, (Entity)player);
                itemStack.m_41622_(1, (LivingEntity)player, arg_0 -> PokemonEntity.interactMob$lambda$8(hand, arg_0));
                return InteractionResult.SUCCESS;
            }
            if (itemStack.m_150930_(Items.f_42446_)) {
                if (this.pokemon.getFeature("milkable") != null) {
                    player.m_5496_(SoundEvents.f_144168_, 1.0f, 1.0f);
                    ItemStack milkBucket = ItemUtils.m_41813_((ItemStack)itemStack, (Player)player, (ItemStack)Items.f_42455_.m_7968_());
                    player.m_21008_(hand, milkBucket);
                    InteractionResult interactionResult = InteractionResult.m_19078_((boolean)this.m_9236_().f_46443_);
                    Intrinsics.checkNotNullExpressionValue((Object)interactionResult, (String)"success(world.isClient)");
                    return interactionResult;
                }
            } else if (itemStack.m_150930_(Items.f_42399_)) {
                boolean bl;
                boolean $i$f$any;
                block61: {
                    $this$any$iv = this.pokemon.getAspects();
                    $i$f$any = false;
                    if ($this$any$iv instanceof Collection && ((Collection)$this$any$iv).isEmpty()) {
                        bl = false;
                    } else {
                        for (Object element$iv : $this$any$iv) {
                            String it = (String)element$iv;
                            boolean bl2 = false;
                            if (!StringsKt.contains$default((CharSequence)it, (CharSequence)"mooshtank", (boolean)false, (int)2, null)) continue;
                            bl = true;
                            break block61;
                        }
                        bl = false;
                    }
                }
                if (bl) {
                    player.m_5496_(SoundEvents.f_12073_, 1.0f, 1.0f);
                    if (!Intrinsics.areEqual((Object)this.pokemon.getLastFlowerFed(), (Object)ItemStack.f_41583_)) {
                        boolean bl3;
                        block62: {
                            $this$any$iv = this.pokemon.getAspects();
                            $i$f$any = false;
                            if ($this$any$iv instanceof Collection && ((Collection)$this$any$iv).isEmpty()) {
                                bl3 = false;
                            } else {
                                for (Object element$iv : $this$any$iv) {
                                    String it = (String)element$iv;
                                    boolean bl4 = false;
                                    if (!StringsKt.contains$default((CharSequence)it, (CharSequence)"mooshtank-brown", (boolean)false, (int)2, null)) continue;
                                    bl3 = true;
                                    break block62;
                                }
                                bl3 = false;
                            }
                        }
                        if (bl3) {
                            MobEffect effect = null;
                            int duration = 0;
                            if (this.pokemon.getLastFlowerFed().m_150930_(Items.f_41942_)) {
                                effect = MobEffect.m_19453_((int)12);
                                duration = 80;
                            } else if (this.pokemon.getLastFlowerFed().m_150930_(Items.f_41943_)) {
                                effect = MobEffect.m_19453_((int)15);
                                duration = 160;
                            } else if (this.pokemon.getLastFlowerFed().m_150930_(Items.f_41941_) || this.pokemon.getLastFlowerFed().m_150930_(Items.f_41939_)) {
                                effect = MobEffect.m_19453_((int)23);
                                duration = 7;
                            } else if (this.pokemon.getLastFlowerFed().m_150930_(Items.f_41949_)) {
                                effect = MobEffect.m_19453_((int)8);
                                duration = 120;
                            } else if (this.pokemon.getLastFlowerFed().m_150930_(Items.f_41950_)) {
                                effect = MobEffect.m_19453_((int)19);
                                duration = 240;
                            } else if (this.pokemon.getLastFlowerFed().m_150930_(Items.f_41948_)) {
                                effect = MobEffect.m_19453_((int)10);
                                duration = 160;
                            } else if (this.pokemon.getLastFlowerFed().m_150930_(Items.f_41940_) || this.pokemon.getLastFlowerFed().m_150930_(Items.f_271471_)) {
                                effect = MobEffect.m_19453_((int)16);
                                duration = 100;
                            } else if (this.pokemon.getLastFlowerFed().m_150930_(Items.f_41947_) || this.pokemon.getLastFlowerFed().m_150930_(Items.f_41944_) || this.pokemon.getLastFlowerFed().m_150930_(Items.f_41946_) || this.pokemon.getLastFlowerFed().m_150930_(Items.f_41945_)) {
                                effect = MobEffect.m_19453_((int)18);
                                duration = 180;
                            } else if (this.pokemon.getLastFlowerFed().m_150930_(Items.f_41951_)) {
                                effect = MobEffect.m_19453_((int)20);
                                duration = 160;
                            } else if (this.pokemon.getLastFlowerFed().m_150930_(CobblemonItems.PEP_UP_FLOWER)) {
                                effect = MobEffect.m_19453_((int)25);
                                duration = 160;
                            }
                            ItemStack susStewStack = Items.f_42718_.m_7968_();
                            SuspiciousStewItem.m_43258_((ItemStack)susStewStack, (MobEffect)effect, (int)duration);
                            ItemStack susStewEffect = ItemUtils.m_41813_((ItemStack)itemStack, (Player)player, (ItemStack)susStewStack);
                            player.m_21008_(hand, susStewEffect);
                            ItemStack itemStack2 = ItemStack.f_41583_;
                            Intrinsics.checkNotNullExpressionValue((Object)itemStack2, (String)"EMPTY");
                            this.pokemon.setLastFlowerFed(itemStack2);
                            InteractionResult interactionResult = InteractionResult.m_19078_((boolean)this.m_9236_().f_46443_);
                            Intrinsics.checkNotNullExpressionValue((Object)interactionResult, (String)"success(world.isClient)");
                            return interactionResult;
                        }
                    }
                    ItemStack mushroomStew = ItemUtils.m_41813_((ItemStack)itemStack, (Player)player, (ItemStack)Items.f_42400_.m_7968_());
                    player.m_21008_(hand, mushroomStew);
                    InteractionResult interactionResult = InteractionResult.m_19078_((boolean)this.m_9236_().f_46443_);
                    Intrinsics.checkNotNullExpressionValue((Object)interactionResult, (String)"success(world.isClient)");
                    return interactionResult;
                }
            } else if (itemStack.m_150930_(Items.f_41942_) || itemStack.m_150930_(Items.f_41943_) || itemStack.m_150930_(Items.f_41941_) || itemStack.m_150930_(Items.f_41939_) || itemStack.m_150930_(Items.f_41949_) || itemStack.m_150930_(Items.f_41950_) || itemStack.m_150930_(Items.f_41948_) || itemStack.m_150930_(Items.f_41940_) || itemStack.m_150930_(Items.f_271471_) || itemStack.m_150930_(Items.f_41947_) || itemStack.m_150930_(Items.f_41944_) || itemStack.m_150930_(Items.f_41946_) || itemStack.m_150930_(Items.f_41945_) || itemStack.m_150930_(Items.f_41951_) || itemStack.m_150930_(CobblemonItems.PEP_UP_FLOWER)) {
                boolean bl;
                block63: {
                    $this$any$iv = this.pokemon.getAspects();
                    boolean $i$f$any = false;
                    if ($this$any$iv instanceof Collection && ((Collection)$this$any$iv).isEmpty()) {
                        bl = false;
                    } else {
                        for (Object element$iv : $this$any$iv) {
                            String it = (String)element$iv;
                            boolean bl5 = false;
                            if (!StringsKt.contains$default((CharSequence)it, (CharSequence)"mooshtank", (boolean)false, (int)2, null)) continue;
                            bl = true;
                            break block63;
                        }
                        bl = false;
                    }
                }
                if (bl) {
                    player.m_5496_(SoundEvents.f_12072_, 1.0f, 1.0f);
                    Intrinsics.checkNotNullExpressionValue((Object)itemStack, (String)"itemStack");
                    this.pokemon.setLastFlowerFed(itemStack);
                    InteractionResult interactionResult = InteractionResult.m_19078_((boolean)this.m_9236_().f_46443_);
                    Intrinsics.checkNotNullExpressionValue((Object)interactionResult, (String)"success(world.isClient)");
                    return interactionResult;
                }
            } else if (!player.m_6144_() && Intrinsics.areEqual((Object)player.m_20148_(), (Object)this.m_21805_()) && (itemStack.m_150930_((Item)CobblemonItems.RELIC_COIN) || itemStack.m_150930_((Item)CobblemonItems.RELIC_COIN_POUCH) || itemStack.m_150930_((Item)CobblemonItems.RELIC_COIN_SACK) || itemStack.m_150930_(Items.f_42419_) || itemStack.m_150930_(Items.f_42418_) || itemStack.m_150930_(Items.f_42791_))) {
                if (GimmighoulStashHandler.INSTANCE.interactMob(player, hand, this.pokemon)) {
                    return InteractionResult.SUCCESS;
                }
            } else if (itemStack.m_41720_() instanceof DyeItem && colorFeatureType != null) {
                Object object = colorFeature;
                if (object == null || (object = ((StringSpeciesFeature)object).getValue()) == null) {
                    object = "";
                }
                Object currentColor = object;
                Item item = itemStack.m_41720_();
                Intrinsics.checkNotNull((Object)item, (String)"null cannot be cast to non-null type net.minecraft.item.DyeItem");
                DyeItem item2 = (DyeItem)item;
                if (!StringsKt.equals((String)item2.m_41089_().name(), (String)currentColor, (boolean)true)) {
                    if (player instanceof ServerPlayer) {
                        if (colorFeature != null) {
                            String string = item2.m_41089_().name().toLowerCase(Locale.ROOT);
                            Intrinsics.checkNotNullExpressionValue((Object)string, (String)"this as java.lang.String).toLowerCase(Locale.ROOT)");
                            colorFeature.setValue(string);
                            this.pokemon.markFeatureDirty(colorFeature);
                        } else {
                            String string = item2.m_41089_().name().toLowerCase(Locale.ROOT);
                            Intrinsics.checkNotNullExpressionValue((Object)string, (String)"this as java.lang.String).toLowerCase(Locale.ROOT)");
                            StringSpeciesFeature newColorFeature = new StringSpeciesFeature("color", string);
                            this.pokemon.getFeatures().add(newColorFeature);
                            Pokemon[] pokemonArray = new Pokemon[]{this.pokemon};
                            this.pokemon.getAnyChangeObservable().emit((Pokemon[])pokemonArray);
                        }
                        this.pokemon.updateAspects();
                        if (!((ServerPlayer)player).m_7500_()) {
                            itemStack.m_41774_(1);
                        }
                    }
                    InteractionResult interactionResult = InteractionResult.m_19078_((boolean)this.m_9236_().f_46443_);
                    Intrinsics.checkNotNullExpressionValue((Object)interactionResult, (String)"success(world.isClient)");
                    return interactionResult;
                }
            } else if (itemStack.m_41720_().equals((Object)Items.f_42447_) && colorFeatureType != null) {
                if (player instanceof ServerPlayer && colorFeature != null) {
                    if (!((ServerPlayer)player).m_7500_()) {
                        itemStack.m_41774_(1);
                        ItemStack itemStack3 = Items.f_42446_.m_7968_();
                        Intrinsics.checkNotNullExpressionValue((Object)itemStack3, (String)"BUCKET.defaultStack");
                        PlayerExtensionsKt.giveOrDropItemStack$default(player, itemStack3, false, 2, null);
                    }
                    colorFeature.setValue("");
                    this.pokemon.markFeatureDirty(colorFeature);
                    this.pokemon.updateAspects();
                }
                InteractionResult interactionResult = InteractionResult.m_19078_((boolean)this.m_9236_().f_46443_);
                Intrinsics.checkNotNullExpressionValue((Object)interactionResult, (String)"success(world.isClient)");
                return interactionResult;
            }
        }
        if (hand == InteractionHand.MAIN_HAND && player instanceof ServerPlayer && Intrinsics.areEqual((Object)this.pokemon.getOwnerPlayer(), (Object)player)) {
            if (((ServerPlayer)player).m_6144_()) {
                UUID uUID = this.m_20148_();
                Intrinsics.checkNotNullExpressionValue((Object)uUID, (String)"this.getUuid()");
                new InteractPokemonUIPacket(uUID, this.m_29897_() && CollectionsKt.contains((Iterable)PlayerExtensionsKt.party((ServerPlayer)player), (Object)this.pokemon)).sendToPlayer((ServerPlayer)player);
            } else {
                ItemStack itemStack4 = player.m_21120_(hand);
                Intrinsics.checkNotNullExpressionValue((Object)itemStack4, (String)"player.getStackInHand(hand)");
                if (this.attemptItemInteraction(player, itemStack4)) {
                    return InteractionResult.SUCCESS;
                }
            }
        }
        InteractionResult interactionResult = super.m_6071_(player, hand);
        Intrinsics.checkNotNullExpressionValue((Object)interactionResult, (String)"super.interactMob(player, hand)");
        return interactionResult;
    }

    @NotNull
    public EntityDimensions m_6972_(@NotNull Pose pose) {
        Intrinsics.checkNotNullParameter((Object)pose, (String)"pose");
        MocKEffect mocKEffect = this.effects.getMockEffect();
        float scale = mocKEffect != null ? mocKEffect.getScale() : this.getForm().getBaseScale() * this.pokemon.getScaleModifier();
        EntityDimensions entityDimensions = this.getExposedForm().getHitbox().m_20388_(scale);
        Intrinsics.checkNotNullExpressionValue((Object)entityDimensions, (String)"this.exposedForm.hitbox.scaled(scale)");
        return entityDimensions;
    }

    public boolean m_142066_() {
        return super.m_142066_() && !this.isBusy();
    }

    public boolean m_6469_(@Nullable DamageSource source, float amount) {
        boolean bl;
        if (super.m_6469_(source, amount)) {
            MocKEffect mocKEffect = this.effects.getMockEffect();
            if (mocKEffect != null) {
                MocKEffect mocKEffect2;
                MocKEffect it = mocKEffect2 = mocKEffect;
                boolean bl2 = false;
                mocKEffect = it instanceof IllusionEffect && this.getBattleId() == null ? mocKEffect2 : null;
                if (mocKEffect != null) {
                    mocKEffect.end(this);
                }
            }
            if (this.m_21223_() == 0.0f) {
                this.pokemon.setCurrentHealth(0);
            }
            bl = true;
        } else {
            bl = false;
        }
        return bl;
    }

    /*
     * WARNING - void declaration
     */
    public boolean m_142391_() {
        if (this.m_21805_() == null && (Cobblemon.INSTANCE.getConfig().getSavePokemonToWorld() || this.m_21532_())) {
            void this_$iv$iv;
            void $this$iv;
            CancelableObservable<PokemonEntitySaveToWorldEvent> cancelableObservable = CobblemonEvents.POKEMON_ENTITY_SAVE_TO_WORLD;
            Cancelable event$iv = new PokemonEntitySaveToWorldEvent(this);
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
                    it = (PokemonEntitySaveToWorldEvent)it$iv;
                    boolean bl2 = false;
                    return true;
                }
                Cancelable cancelable = it$iv;
                boolean bl3 = false;
                it = cancelable;
            }
        }
        return this.tethering != null;
    }

    public void m_6043_() {
        if (this.pokemon.getOwnerUUID() == null && !this.m_21532_() && this.despawner.shouldDespawn((PokemonEntity)((Entity)this))) {
            this.m_146870_();
        }
    }

    public float m_20236_(@NotNull Pose pose) {
        Intrinsics.checkNotNullParameter((Object)pose, (String)"pose");
        return this.getExposedForm().eyeHeight(this);
    }

    protected float m_6431_(@NotNull Pose pose, @NotNull EntityDimensions dimensions) {
        Intrinsics.checkNotNullParameter((Object)pose, (String)"pose");
        Intrinsics.checkNotNullParameter((Object)dimensions, (String)"dimensions");
        if (this.pokemon == null) {
            return super.m_6431_(pose, dimensions);
        }
        return this.getExposedForm().eyeHeight(this);
    }

    public final void setBehaviourFlag(@NotNull PokemonBehaviourFlag flag, boolean on) {
        Intrinsics.checkNotNullParameter((Object)((Object)flag), (String)"flag");
        Object object = this.f_19804_.m_135370_(BEHAVIOUR_FLAGS);
        Intrinsics.checkNotNullExpressionValue((Object)object, (String)"dataTracker.get(BEHAVIOUR_FLAGS)");
        this.f_19804_.m_135381_(BEHAVIOUR_FLAGS, (Object)BitUtilitiesKt.setBitForByte(((Number)object).byteValue(), flag.getBit(), on));
    }

    public final boolean getBehaviourFlag(@NotNull PokemonBehaviourFlag flag) {
        Intrinsics.checkNotNullParameter((Object)((Object)flag), (String)"flag");
        Object object = this.f_19804_.m_135370_(BEHAVIOUR_FLAGS);
        Intrinsics.checkNotNullExpressionValue((Object)object, (String)"dataTracker.get(BEHAVIOUR_FLAGS)");
        return BitUtilitiesKt.getBitForByte(((Number)object).byteValue(), flag.getBit());
    }

    public final boolean canBattle(@NotNull Player player) {
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        Object object = this.f_19804_.m_135370_(UNBATTLEABLE);
        Intrinsics.checkNotNullExpressionValue((Object)object, (String)"dataTracker.get(UNBATTLEABLE)");
        if (((Boolean)object).booleanValue()) {
            return false;
        }
        if (this.isBusy()) {
            return false;
        }
        if (this.getBattleId() != null) {
            return false;
        }
        if (this.m_21805_() != null) {
            return false;
        }
        return !(this.m_21223_() <= 0.0f) && !this.m_21224_();
    }

    public final Integer labelLevel() {
        return (Integer)this.f_19804_.m_135370_(LABEL_LEVEL);
    }

    /*
     * WARNING - void declaration
     */
    public void m_8032_() {
        block4: {
            block3: {
                void $this$filterIsInstanceTo$iv$iv;
                if (!this.m_20067_()) break block3;
                Iterable $this$filterIsInstance$iv = this.busyLocks;
                boolean $i$f$filterIsInstance = false;
                Iterable iterable = $this$filterIsInstance$iv;
                Collection destination$iv$iv = new ArrayList();
                boolean $i$f$filterIsInstanceTo = false;
                for (Object element$iv$iv : $this$filterIsInstanceTo$iv$iv) {
                    if (!(element$iv$iv instanceof EmptyPokeBallEntity)) continue;
                    destination$iv$iv.add(element$iv$iv);
                }
                if (!((List)destination$iv$iv).isEmpty()) break block4;
            }
            ResourceLocation sound2 = new ResourceLocation(this.pokemon.getSpecies().getResourceIdentifier().m_135827_(), "pokemon." + this.pokemon.showdownId() + ".ambient");
            SoundSource soundSource = this.m_5720_();
            Intrinsics.checkNotNullExpressionValue((Object)soundSource, (String)"this.soundCategory");
            NetworkPacket networkPacket = new UnvalidatedPlaySoundS2CPacket(sound2, soundSource, this.m_20185_(), this.m_20186_(), this.m_20189_(), this.m_6121_(), this.m_6100_());
            double d = this.m_20185_();
            double d2 = this.m_20186_();
            double d3 = this.m_20189_();
            ResourceKey resourceKey = this.m_9236_().m_46472_();
            Intrinsics.checkNotNullExpressionValue((Object)resourceKey, (String)"this.world.registryKey");
            NetworkPacket.DefaultImpls.sendToPlayersAround$default(networkPacket, d, d2, d3, 16.0, resourceKey, null, 32, null);
        }
    }

    @Nullable
    protected Void getAmbientSound() {
        return null;
    }

    public int m_8100_() {
        return Cobblemon.INSTANCE.getConfig().getAmbientPokemonCryTicks();
    }

    /*
     * WARNING - void declaration
     */
    private final boolean attemptItemInteraction(Player player, ItemStack stack) {
        Item item;
        block21: {
            boolean bl;
            BattlePokemon battlePokemon;
            BagItemConvertible bagItemConvertible;
            block20: {
                Collection<Object> collection;
                block19: {
                    Iterator $this$flatMapTo$iv$iv;
                    Object object;
                    block23: {
                        block22: {
                            if (stack.m_41619_()) {
                                return false;
                            }
                            if (!(player instanceof ServerPlayer) || !this.isBattling()) break block21;
                            object = this.getBattleId();
                            if (object == null) break block22;
                            UUID uUID = object;
                            BattleRegistry battleRegistry = BattleRegistry.INSTANCE;
                            UUID p0 = uUID;
                            boolean bl2 = false;
                            PokemonBattle pokemonBattle = battleRegistry.getBattle(p0);
                            object = pokemonBattle;
                            if (pokemonBattle != null) break block23;
                        }
                        return false;
                    }
                    Object battle2 = object;
                    BagItemConvertible bagItemConvertible2 = BagItems.INSTANCE.getConvertibleForStack(stack);
                    if (bagItemConvertible2 == null) {
                        return false;
                    }
                    bagItemConvertible = bagItemConvertible2;
                    Iterable<BattleActor> $this$flatMap$iv = ((PokemonBattle)battle2).getActors();
                    boolean $i$f$flatMap22 = false;
                    Iterable<BattleActor> bl2 = $this$flatMap$iv;
                    Collection<Object> destination$iv$iv2 = new ArrayList();
                    boolean $i$f$flatMapTo = false;
                    Iterator iterator = $this$flatMapTo$iv$iv.iterator();
                    while (iterator.hasNext()) {
                        Object element$iv$iv = iterator.next();
                        BattleActor it = (BattleActor)element$iv$iv;
                        boolean bl3 = false;
                        Iterable list$iv$iv = it.getPokemonList();
                        CollectionsKt.addAll((Collection)destination$iv$iv2, (Iterable)list$iv$iv);
                    }
                    Iterable $i$f$flatMap22 = (List)destination$iv$iv2;
                    for (Collection<Object> destination$iv$iv2 : $i$f$flatMap22) {
                        BattlePokemon it = (BattlePokemon)((Object)destination$iv$iv2);
                        boolean bl4 = false;
                        if (!Intrinsics.areEqual((Object)it.getEffectedPokemon().getUuid(), (Object)this.pokemon.getUuid())) continue;
                        collection = destination$iv$iv2;
                        break block19;
                    }
                    collection = null;
                }
                BattlePokemon battlePokemon2 = (BattlePokemon)((Object)collection);
                if (battlePokemon2 == null) {
                    return false;
                }
                battlePokemon = battlePokemon2;
                BattleActor[] $this$none$iv = battlePokemon.getActor().getSide().getActors();
                boolean $i$f$none = false;
                int $this$flatMapTo$iv$iv = $this$none$iv.length;
                for (int $i$f$flatMap22 = 0; $i$f$flatMap22 < $this$flatMapTo$iv$iv; ++$i$f$flatMap22) {
                    BattleActor element$iv;
                    BattleActor it = element$iv = $this$none$iv[$i$f$flatMap22];
                    boolean bl5 = false;
                    if (!it.isForPlayer((ServerPlayer)player)) continue;
                    bl = false;
                    break block20;
                }
                bl = true;
            }
            if (bl) {
                return true;
            }
            return bagItemConvertible.handleInteraction((ServerPlayer)player, battlePokemon, stack);
        }
        if (!(player instanceof ServerPlayer) || this.isBusy()) {
            return false;
        }
        if (Intrinsics.areEqual((Object)this.pokemon.getOwnerPlayer(), (Object)player)) {
            void $this$forEach$iv;
            Iterator $this$filterIsInstanceTo$iv$iv;
            Iterable $this$filterIsInstance$iv;
            Level level = ((ServerPlayer)player).m_9236_();
            Intrinsics.checkNotNullExpressionValue((Object)level, (String)"player.world");
            ItemInteractionEvolution.ItemInteractionContext context = new ItemInteractionEvolution.ItemInteractionContext(stack, level);
            Iterable<Evolution> bagItemConvertible = this.pokemon.getLockedEvolutions();
            boolean $i$f$filterIsInstance = false;
            void $this$none$iv = $this$filterIsInstance$iv;
            Collection destination$iv$iv = new ArrayList();
            boolean $i$f$filterIsInstanceTo = false;
            Iterator $this$flatMapTo$iv$iv = $this$filterIsInstanceTo$iv$iv.iterator();
            while ($this$flatMapTo$iv$iv.hasNext()) {
                Object element$iv$iv = $this$flatMapTo$iv$iv.next();
                if (!(element$iv$iv instanceof ItemInteractionEvolution)) continue;
                destination$iv$iv.add(element$iv$iv);
            }
            $this$filterIsInstance$iv = (List)destination$iv$iv;
            boolean $i$f$forEach = false;
            for (Object element$iv : $this$forEach$iv) {
                ItemInteractionEvolution evolution = (ItemInteractionEvolution)element$iv;
                boolean bl = false;
                if (!evolution.attemptEvolution(this.pokemon, (Object)context)) continue;
                if (!((ServerPlayer)player).m_7500_()) {
                    stack.m_41774_(1);
                }
                Level level2 = this.m_9236_();
                Intrinsics.checkNotNullExpressionValue((Object)level2, (String)"this.world");
                Vec3 vec3 = this.m_20182_();
                Intrinsics.checkNotNullExpressionValue((Object)vec3, (String)"this.pos");
                WorldExtensionsKt.playSoundServer$default(level2, vec3, CobblemonSounds.ITEM_USE, null, 1.0f, 1.0f, 4, null);
                return true;
            }
        }
        PokemonEntityInteraction pokemonEntityInteraction = (item = stack.m_41720_()) instanceof PokemonEntityInteraction ? (PokemonEntityInteraction)item : null;
        if (pokemonEntityInteraction != null) {
            PokemonEntityInteraction it = pokemonEntityInteraction;
            boolean bl = false;
            if (it.onInteraction((ServerPlayer)player, this, stack)) {
                SoundEvent soundEvent = it.getSound();
                if (soundEvent != null) {
                    SoundEvent it2 = soundEvent;
                    boolean bl6 = false;
                    Level level = this.m_9236_();
                    Intrinsics.checkNotNullExpressionValue((Object)level, (String)"this.world");
                    Vec3 vec3 = this.m_20182_();
                    Intrinsics.checkNotNullExpressionValue((Object)vec3, (String)"this.pos");
                    WorldExtensionsKt.playSoundServer$default(level, vec3, it2, null, 1.0f, 1.0f, 4, null);
                }
                return true;
            }
        }
        return false;
    }

    public final boolean offerHeldItem(@NotNull Player player, @NotNull ItemStack stack) {
        MutableComponent mutableComponent;
        Object[] objectArray;
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        Intrinsics.checkNotNullParameter((Object)stack, (String)"stack");
        if (!(player instanceof ServerPlayer) || this.isBusy() || !Intrinsics.areEqual((Object)this.pokemon.getOwnerPlayer(), (Object)player)) {
            return false;
        }
        Object[] $this$offerHeldItem_u24lambda_u2420 = objectArray = stack.m_41777_();
        boolean bl = false;
        $this$offerHeldItem_u24lambda_u2420.m_41764_(1);
        Object[] giving = objectArray;
        ItemStack possibleReturn = this.pokemon.heldItemNoCopy$common();
        if (stack.m_41619_() && possibleReturn.m_41619_()) {
            return false;
        }
        if (ItemStack.m_41728_((ItemStack)giving, (ItemStack)possibleReturn)) {
            $this$offerHeldItem_u24lambda_u2420 = new Object[2];
            $this$offerHeldItem_u24lambda_u2420[0] = this.pokemon.getDisplayName();
            Intrinsics.checkNotNullExpressionValue((Object)stack.m_41786_(), (String)"stack.name");
            player.m_213846_((Component)LocalizationUtilsKt.lang("held_item.already_holding", $this$offerHeldItem_u24lambda_u2420));
            return true;
        }
        ItemStack returned = this.pokemon.swapHeldItem(stack, !((ServerPlayer)player).m_7500_());
        if (giving.m_41619_()) {
            var7_8 = new Object[2];
            Intrinsics.checkNotNullExpressionValue((Object)returned.m_41786_(), (String)"returned.name");
            var7_8[1] = this.pokemon.getDisplayName();
            mutableComponent = LocalizationUtilsKt.lang("held_item.take", var7_8);
        } else if (returned.m_41619_()) {
            var7_8 = new Object[2];
            var7_8[0] = this.pokemon.getDisplayName();
            Intrinsics.checkNotNullExpressionValue((Object)giving.m_41786_(), (String)"giving.name");
            mutableComponent = LocalizationUtilsKt.lang("held_item.give", var7_8);
        } else {
            var7_8 = new Object[3];
            Intrinsics.checkNotNullExpressionValue((Object)returned.m_41786_(), (String)"returned.name");
            var7_8[1] = this.pokemon.getDisplayName();
            Intrinsics.checkNotNullExpressionValue((Object)giving.m_41786_(), (String)"giving.name");
            mutableComponent = LocalizationUtilsKt.lang("held_item.replace", var7_8);
        }
        MutableComponent text = mutableComponent;
        PlayerExtensionsKt.giveOrDropItemStack$default(player, returned, false, 2, null);
        player.m_213846_((Component)text);
        Level level = this.m_9236_();
        Intrinsics.checkNotNullExpressionValue((Object)level, (String)"this.world");
        Vec3 vec3 = this.m_20182_();
        Intrinsics.checkNotNullExpressionValue((Object)vec3, (String)"this.pos");
        SoundEvent soundEvent = SoundEvents.f_12019_;
        Intrinsics.checkNotNullExpressionValue((Object)soundEvent, (String)"ENTITY_ITEM_PICKUP");
        WorldExtensionsKt.playSoundServer$default(level, vec3, soundEvent, null, 0.6f, 1.4f, 4, null);
        return true;
    }

    /*
     * WARNING - void declaration
     */
    public final boolean tryMountingShoulder(@NotNull ServerPlayer player) {
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        if (this.pokemon.belongsTo((Player)player) && this.hasRoomToMount((Player)player)) {
            void this_$iv$iv;
            void $this$iv;
            CancelableObservable<ShoulderMountEvent> cancelableObservable = CobblemonEvents.SHOULDER_MOUNT;
            Cancelable event$iv = new ShoulderMountEvent(player, this.pokemon, player.m_36331_().m_128456_());
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
                    it = (ShoulderMountEvent)it$iv;
                    boolean bl2 = false;
                    Vec3 dirToPlayer = player.m_146892_().m_82546_(this.m_20182_()).m_82542_(1.0, 0.0, 1.0).m_82541_();
                    this.m_20256_(dirToPlayer.m_82490_(0.8).m_82520_(0.0, 0.5, 0.0));
                    Object lock = new Object();
                    this.busyLocks.add(lock);
                    this.after(0.5f, (Function0<Unit>)((Function0)new Function0<Unit>(this, lock, player){
                        final /* synthetic */ PokemonEntity this$0;
                        final /* synthetic */ Object $lock;
                        final /* synthetic */ ServerPlayer $player;
                        {
                            this.this$0 = $receiver;
                            this.$lock = $lock;
                            this.$player = $player;
                            super(0);
                        }

                        /*
                         * WARNING - void declaration
                         */
                        public final void invoke() {
                            boolean isLeft;
                            this.this$0.getBusyLocks().remove(this.$lock);
                            if (!this.this$0.isBusy() && this.this$0.m_6084_() && ((isLeft = this.$player.m_36331_().m_128456_()) || this.$player.m_36332_().m_128456_())) {
                                void $this$forEach$iv;
                                Pokemon pokemon = this.this$0.getPokemon();
                                UUID uUID = this.$player.m_20148_();
                                Intrinsics.checkNotNullExpressionValue((Object)uUID, (String)"player.uuid");
                                UUID uUID2 = this.this$0.getPokemon().getUuid();
                                Intrinsics.checkNotNullExpressionValue((Object)uUID2, (String)"pokemon.uuid");
                                pokemon.setState(new ShoulderedState(uUID, isLeft, uUID2));
                                this.this$0.m_29895_(this.$player);
                                Iterable iterable = this.this$0.getPokemon().getForm().getShoulderEffects();
                                PokemonEntity pokemonEntity = this.this$0;
                                ServerPlayer serverPlayer = this.$player;
                                boolean $i$f$forEach = false;
                                for (T element$iv : $this$forEach$iv) {
                                    ShoulderEffect it = (ShoulderEffect)element$iv;
                                    boolean bl = false;
                                    it.applyEffect(pokemonEntity.getPokemon(), serverPlayer, isLeft);
                                }
                                Level level = this.this$0.m_9236_();
                                Intrinsics.checkNotNullExpressionValue((Object)level, (String)"this.world");
                                Vec3 vec3 = this.this$0.m_20182_();
                                Intrinsics.checkNotNullExpressionValue((Object)vec3, (String)"this.pos");
                                SoundEvent soundEvent = SoundEvents.f_12019_;
                                Intrinsics.checkNotNullExpressionValue((Object)soundEvent, (String)"ENTITY_ITEM_PICKUP");
                                WorldExtensionsKt.playSoundServer$default(level, vec3, soundEvent, null, 0.7f, 1.4f, 4, null);
                                this.this$0.m_146870_();
                            }
                        }
                    }));
                    return true;
                }
                Cancelable cancelable = it$iv;
                boolean bl3 = false;
                it = cancelable;
            }
        }
        return false;
    }

    /*
     * WARNING - void declaration
     */
    public boolean m_29895_(@NotNull ServerPlayer player) {
        Collection<StringTag> collection;
        void $this$mapTo$iv$iv;
        void $this$map$iv;
        CompoundTag compoundTag;
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        if (!super.m_29895_(player)) {
            return false;
        }
        CompoundTag compoundTag2 = player.m_36332_();
        Intrinsics.checkNotNullExpressionValue((Object)compoundTag2, (String)"player.shoulderEntityRight");
        if (CompoundTagExtensionsKt.isPokemonEntity(compoundTag2) && Intrinsics.areEqual((Object)player.m_36332_().m_128469_("Pokemon").m_128342_("UUID"), (Object)this.pokemon.getUuid())) {
            compoundTag = player.m_36332_();
        } else {
            CompoundTag compoundTag3 = player.m_36331_();
            Intrinsics.checkNotNullExpressionValue((Object)compoundTag3, (String)"player.shoulderEntityLeft");
            if (CompoundTagExtensionsKt.isPokemonEntity(compoundTag3) && Intrinsics.areEqual((Object)player.m_36331_().m_128469_("Pokemon").m_128342_("UUID"), (Object)this.pokemon.getUuid())) {
                compoundTag = player.m_36331_();
            } else {
                return true;
            }
        }
        CompoundTag nbt = compoundTag;
        nbt.m_128362_("shoulder_uuid", this.pokemon.getUuid());
        nbt.m_128359_("shoulder_species", this.pokemon.getSpecies().getResourceIdentifier().toString());
        nbt.m_128359_("shoulder_form", this.pokemon.getForm().getName());
        Iterable iterable = this.pokemon.getAspects();
        String string = "shoulder_aspects";
        CompoundTag compoundTag4 = nbt;
        boolean $i$f$map = false;
        void var5_7 = $this$map$iv;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv, (int)10));
        boolean $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            void p0;
            String string2 = (String)item$iv$iv;
            collection = destination$iv$iv;
            boolean bl = false;
            collection.add(StringTag.m_129297_((String)p0));
        }
        collection = (List)destination$iv$iv;
        compoundTag4.m_128365_(string, (Tag)CollectionUtilsKt.toNbtList((Collection<? extends Tag>)collection));
        nbt.m_128350_("shoulder_scale", this.pokemon.getScaleModifier());
        return true;
    }

    public void m_142687_(@NotNull Entity.RemovalReason reason) {
        Intrinsics.checkNotNullParameter((Object)reason, (String)"reason");
        PokemonState pokemonState = this.pokemon.getState();
        ActivePokemonState activePokemonState = pokemonState instanceof ActivePokemonState ? (ActivePokemonState)pokemonState : null;
        PokemonEntity stateEntity = activePokemonState != null ? activePokemonState.getEntity() : null;
        super.m_142687_(reason);
        if (Intrinsics.areEqual((Object)stateEntity, (Object)this)) {
            this.pokemon.setState(new InactivePokemonState());
        }
        Iterable $this$forEach$iv = this.subscriptions;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            ObservableSubscription p0 = (ObservableSubscription)element$iv;
            boolean bl = false;
            p0.unsubscribe();
        }
        Entity.RemovalReason[] removalReasonArray = new Entity.RemovalReason[]{reason};
        this.removalObservable.emit((Entity.RemovalReason[])removalReasonArray);
        if (reason.m_146965_() && this.pokemon.getTetheringId() != null) {
            this.pokemon.setTetheringId(null);
        }
        if (this.evolutionEntity != null) {
            GenericBedrockEntity genericBedrockEntity = this.evolutionEntity;
            Intrinsics.checkNotNull((Object)genericBedrockEntity);
            genericBedrockEntity.m_6074_();
            if (this.pokemon.getEntity() != null) {
                this.pokemon.getEntity().evolutionEntity = null;
            }
        }
    }

    public final boolean hasRoomToMount(@NotNull Player player) {
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        return (player.m_36331_().m_128456_() || player.m_36332_().m_128456_()) && !player.m_20159_() && player.m_20096_() && !player.m_20069_() && !player.f_146808_;
    }

    public final void cry() {
        if (this.m_20067_()) {
            return;
        }
        PlayPoseableAnimationPacket pkt = new PlayPoseableAnimationPacket(this.m_19879_(), SetsKt.setOf((Object)"cry"), SetsKt.emptySet());
        List list = this.m_9236_().m_6443_(ServerPlayer.class, AABB.m_165882_((Vec3)this.m_20182_(), (double)64.0, (double)64.0, (double)64.0), arg_0 -> PokemonEntity.cry$lambda$24(cry.1.INSTANCE, arg_0));
        Intrinsics.checkNotNullExpressionValue((Object)list, (String)"world.getEntitiesByClass\u20260, 64.0, 64.0), { true })");
        Iterable $this$forEach$iv = list;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            ServerPlayer it = (ServerPlayer)element$iv;
            boolean bl = false;
            Intrinsics.checkNotNullExpressionValue((Object)it, (String)"it");
            CobblemonNetwork.INSTANCE.sendPacket(it, pkt);
        }
    }

    protected void m_6668_(@Nullable DamageSource source) {
        if (this.pokemon.isWild()) {
            super.m_6668_(source);
            this.getDelegate().drop(source);
        }
    }

    protected void m_21226_() {
        if (this.m_9236_() instanceof ServerLevel && !this.m_217046_() && (this.m_6124_() || this.f_20889_ > 0 && this.m_6149_() && this.m_9236_().m_46469_().m_46207_(CobblemonGameRules.DO_POKEMON_LOOT))) {
            Level level = this.m_9236_();
            Intrinsics.checkNotNull((Object)level, (String)"null cannot be cast to non-null type net.minecraft.server.world.ServerWorld");
            ExperienceOrb.m_147082_((ServerLevel)((ServerLevel)level), (Vec3)this.m_20182_(), (int)this.m_213860_());
        }
    }

    protected void m_6153_() {
        this.getDelegate().updatePostDeath();
    }

    public void m_8035_() {
        super.m_8035_();
        FlagSpeciesFeature feature = (FlagSpeciesFeature)this.pokemon.getFeature("sheared");
        if (feature != null) {
            feature.setEnabled(false);
            this.pokemon.markFeatureDirty(feature);
            this.pokemon.updateAspects();
        }
    }

    public void m_7023_(@NotNull Vec3 movementInput) {
        Intrinsics.checkNotNullParameter((Object)movementInput, (String)"movementInput");
        BlockPos prevBlockPos = this.m_20183_();
        super.m_7023_(movementInput);
        Intrinsics.checkNotNullExpressionValue((Object)prevBlockPos, (String)"prevBlockPos");
        this.updateBlocksTraveled(prevBlockPos);
    }

    private final void updateBlocksTraveled(BlockPos fromBp) {
        if (this.m_20159_() || this.isFalling()) {
            return;
        }
        double blocksTaken = this.m_20183_().m_123331_((Vec3i)fromBp);
        if (blocksTaken > 0.0) {
            this.blocksTraveled += blocksTaken;
        }
    }

    private final void updateEyeHeight() {
        Intrinsics.checkNotNull((Object)this, (String)"null cannot be cast to non-null type bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.mixin.accessor.AccessorEntity");
        AccessorEntity accessorEntity = (AccessorEntity)((Object)this);
        EntityDimensions entityDimensions = this.m_6095_().m_20680_();
        Intrinsics.checkNotNullExpressionValue((Object)entityDimensions, (String)"this.type.dimensions");
        accessorEntity.standingEyeHeight(this.m_6431_(Pose.STANDING, entityDimensions));
    }

    public final boolean isFlying() {
        return this.getBehaviourFlag(PokemonBehaviourFlag.FLYING);
    }

    public final boolean couldStopFlying() {
        return this.isFlying() && !this.getBehaviour().getMoving().getWalk().getAvoidsLand() && this.getBehaviour().getMoving().getWalk().getCanWalk();
    }

    public final boolean isFalling() {
        return this.f_19789_ > 0.0f && this.m_9236_().m_8055_(this.m_20183_().m_7495_()).m_60795_() && !this.isFlying();
    }

    public final boolean getIsSubmerged() {
        return this.m_20077_() || this.m_5842_();
    }

    @Override
    @NotNull
    public PoseType getCurrentPoseType() {
        Object object = this.f_19804_.m_135370_(POSE_TYPE);
        Intrinsics.checkNotNullExpressionValue((Object)object, (String)"this.dataTracker.get(POSE_TYPE)");
        return (PoseType)((Object)object);
    }

    @NotNull
    protected Component m_5677_() {
        return (Component)this.pokemon.getSpecies().getTranslatedName();
    }

    @NotNull
    public Component m_7755_() {
        Object object;
        block5: {
            block4: {
                Component component;
                if (!((Boolean)this.f_19804_.m_135370_(NICKNAME_VISIBLE)).booleanValue()) {
                    return this.m_5677_();
                }
                object = (Component)this.f_19804_.m_135370_(NICKNAME);
                if (object == null) break block4;
                Component it = component = object;
                boolean bl = false;
                object = !Intrinsics.areEqual((Object)it.m_214077_(), (Object)ComponentContents.f_237124_) ? component : null;
                if (object != null) break block5;
            }
            object = (Component)this.pokemon.getDisplayName();
        }
        return object;
    }

    @Nullable
    public Component m_7770_() {
        return (Component)this.pokemon.getNickname();
    }

    public void m_6593_(@Nullable Component name) {
        Component component = name;
        this.pokemon.setNickname(Component.m_237113_((String)(component != null ? component.getString() : null)));
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean m_8077_() {
        if (this.pokemon.getNickname() == null) return false;
        MutableComponent mutableComponent = this.pokemon.getNickname();
        if (Intrinsics.areEqual((Object)(mutableComponent != null ? mutableComponent.m_214077_() : null), (Object)ComponentContents.f_237124_)) return false;
        return true;
    }

    public void m_20340_(boolean visible) {
        this.f_19804_.m_135381_(NICKNAME_VISIBLE, (Object)visible);
    }

    public final boolean forceBattle(@NotNull ServerPlayer player) {
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        if (!this.canBattle((Player)player)) {
            return false;
        }
        Ref.BooleanRef isSuccessful = new Ref.BooleanRef();
        BattleBuilder.pve$default(BattleBuilder.INSTANCE, player, this, null, null, false, false, 0.0f, null, 252, null).ifSuccessful((Function1<? super PokemonBattle, Unit>)((Function1)new Function1<PokemonBattle, Unit>(isSuccessful){
            final /* synthetic */ Ref.BooleanRef $isSuccessful;
            {
                this.$isSuccessful = $isSuccessful;
                super(1);
            }

            public final void invoke(@NotNull PokemonBattle it) {
                Intrinsics.checkNotNullParameter((Object)it, (String)"it");
                this.$isSuccessful.element = true;
            }
        }));
        return isSuccessful.element;
    }

    public boolean m_20151_() {
        Object object = this.f_19804_.m_135370_(NICKNAME_VISIBLE);
        Intrinsics.checkNotNullExpressionValue((Object)object, (String)"dataTracker.get(NICKNAME_VISIBLE)");
        return (Boolean)object;
    }

    public boolean m_6052_() {
        Object object = this.f_19804_.m_135370_(SHOULD_RENDER_NAME);
        Intrinsics.checkNotNullExpressionValue((Object)object, (String)"dataTracker.get(SHOULD_RENDER_NAME)");
        return (Boolean)object;
    }

    public final void hideNameRendering() {
        this.f_19804_.m_135381_(SHOULD_RENDER_NAME, (Object)false);
    }

    public boolean m_6898_(@NotNull ItemStack stack) {
        Intrinsics.checkNotNullParameter((Object)stack, (String)"stack");
        return false;
    }

    public boolean m_7848_(@NotNull Animal other) {
        Intrinsics.checkNotNullParameter((Object)other, (String)"other");
        return false;
    }

    public void m_27563_(@NotNull ServerLevel world, @NotNull Animal other) {
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        Intrinsics.checkNotNullParameter((Object)other, (String)"other");
    }

    @NotNull
    public EntityGetter m_9236_() {
        Level level = this.m_9236_();
        Intrinsics.checkNotNullExpressionValue((Object)level, (String)"this.world");
        return (EntityGetter)level;
    }

    public void m_5851_(@NotNull SoundSource shearedSoundCategory) {
        block50: {
            Intrinsics.checkNotNullParameter((Object)shearedSoundCategory, (String)"shearedSoundCategory");
            this.m_9236_().m_6269_(null, (Entity)this, SoundEvents.f_12344_, shearedSoundCategory, 1.0f, 1.0f);
            FlagSpeciesFeature flagSpeciesFeature = (FlagSpeciesFeature)this.pokemon.getFeature("sheared");
            if (flagSpeciesFeature == null) {
                return;
            }
            FlagSpeciesFeature feature = flagSpeciesFeature;
            feature.setEnabled(true);
            this.pokemon.markFeatureDirty(feature);
            this.pokemon.updateAspects();
            int i = this.f_19796_.m_188503_(3) + 1;
            int j = 0;
            if (j > i) break block50;
            while (true) {
                ItemEntity itemEntity;
                Item woolItem;
                Object color;
                Object object;
                if ((object = (StringSpeciesFeature)this.pokemon.getFeature("color")) == null || (object = ((StringSpeciesFeature)object).getValue()) == null) {
                    object = "white";
                }
                switch (color = object) {
                    case "black": {
                        Item item = Items.f_41938_;
                        break;
                    }
                    case "blue": {
                        Item item = Items.f_41934_;
                        break;
                    }
                    case "brown": {
                        Item item = Items.f_41935_;
                        break;
                    }
                    case "cyan": {
                        Item item = Items.f_41932_;
                        break;
                    }
                    case "gray": {
                        Item item = Items.f_41877_;
                        break;
                    }
                    case "green": {
                        Item item = Items.f_41936_;
                        break;
                    }
                    case "light-blue": {
                        Item item = Items.f_41873_;
                        break;
                    }
                    case "light-gray": {
                        Item item = Items.f_41878_;
                        break;
                    }
                    case "lime": {
                        Item item = Items.f_41875_;
                        break;
                    }
                    case "magenta": {
                        Item item = Items.f_41872_;
                        break;
                    }
                    case "orange": {
                        Item item = Items.f_41871_;
                        break;
                    }
                    case "purple": {
                        Item item = Items.f_41933_;
                        break;
                    }
                    case "red": {
                        Item item = Items.f_41937_;
                        break;
                    }
                    case "yellow": {
                        Item item = Items.f_41874_;
                        break;
                    }
                    default: {
                        Item item = woolItem = Items.f_41870_;
                    }
                }
                if (this.m_20000_((ItemLike)woolItem, 1) == null) {
                    return;
                }
                itemEntity.m_20256_(itemEntity.m_20184_().m_82520_((double)((this.f_19796_.m_188501_() - this.f_19796_.m_188501_()) * 0.1f), (double)(this.f_19796_.m_188501_() * 0.05f), (double)((this.f_19796_.m_188501_() - this.f_19796_.m_188501_()) * 0.1f)));
                if (j == i) break;
                ++j;
            }
        }
    }

    public boolean m_6220_() {
        FlagSpeciesFeature flagSpeciesFeature = (FlagSpeciesFeature)this.pokemon.getFeature("sheared");
        if (flagSpeciesFeature == null) {
            return false;
        }
        FlagSpeciesFeature feature = flagSpeciesFeature;
        return !this.isBusy() && !this.pokemon.isFainted() && !feature.getEnabled();
    }

    public boolean m_6072_() {
        return false;
    }

    public void m_20301_(int air) {
        if (this.isBattling()) {
            this.f_19804_.m_135381_(ShoulderRidingEntity.f_19832_, (Object)300);
            return;
        }
        super.m_20301_(air);
    }

    public void m_6452_(@Nullable ServerPlayer player) {
        if (player == null) {
            return;
        }
        if (Intrinsics.areEqual((Object)this.m_21805_(), (Object)player.m_20148_()) && this.tethering == null) {
            this.queuedToDespawn = true;
            return;
        }
    }

    public boolean m_6573_(@NotNull Player player) {
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        return this.m_21805_() == null || Intrinsics.areEqual((Object)this.m_21805_(), (Object)player.m_20148_());
    }

    @NotNull
    public final SoundEvent getBattleTheme() {
        SoundEvent soundEvent = (SoundEvent)BuiltInRegistries.f_256894_.m_7745_(this.getForm().getBattleTheme());
        if (soundEvent == null) {
            soundEvent = CobblemonSounds.PVW_BATTLE;
        }
        Intrinsics.checkNotNullExpressionValue((Object)soundEvent, (String)"Registries.SOUND_EVENT.g\u2026obblemonSounds.PVW_BATTLE");
        return soundEvent;
    }

    @Override
    @NotNull
    public ScheduledTask momentarily(@NotNull Function0<Unit> action2) {
        return Schedulable.DefaultImpls.momentarily(this, action2);
    }

    @Override
    @NotNull
    public ScheduledTask after(float seconds, @NotNull Function0<Unit> action2) {
        return Schedulable.DefaultImpls.after(this, seconds, action2);
    }

    @Override
    @NotNull
    public ScheduledTask lerp(float seconds, @NotNull Function1<? super Float, Unit> action2) {
        return Schedulable.DefaultImpls.lerp(this, seconds, action2);
    }

    @Override
    @NotNull
    public ScheduledTask.Builder taskBuilder() {
        return Schedulable.DefaultImpls.taskBuilder(this);
    }

    private static final boolean initGoals$lambda$4(Goal it) {
        return true;
    }

    private static final void interactMob$lambda$8(InteractionHand $hand, Player it) {
        Intrinsics.checkNotNullParameter((Object)$hand, (String)"$hand");
        it.m_21190_($hand);
    }

    private static final boolean cry$lambda$24(Function1 $tmp0, Object p0) {
        Intrinsics.checkNotNullParameter((Object)$tmp0, (String)"$tmp0");
        return (Boolean)$tmp0.invoke(p0);
    }

    public static final EntityDataAccessor<String> getSPECIES() {
        return Companion.getSPECIES();
    }

    public static final EntityDataAccessor<Component> getNICKNAME() {
        return Companion.getNICKNAME();
    }

    public static final EntityDataAccessor<Boolean> getNICKNAME_VISIBLE() {
        return Companion.getNICKNAME_VISIBLE();
    }

    public static final EntityDataAccessor<Boolean> getSHOULD_RENDER_NAME() {
        return Companion.getSHOULD_RENDER_NAME();
    }

    public static final EntityDataAccessor<Boolean> getMOVING() {
        return Companion.getMOVING();
    }

    public static final EntityDataAccessor<Byte> getBEHAVIOUR_FLAGS() {
        return Companion.getBEHAVIOUR_FLAGS();
    }

    public static final EntityDataAccessor<Integer> getPHASING_TARGET_ID() {
        return Companion.getPHASING_TARGET_ID();
    }

    public static final EntityDataAccessor<Byte> getBEAM_MODE() {
        return Companion.getBEAM_MODE();
    }

    public static final EntityDataAccessor<Optional<UUID>> getBATTLE_ID() {
        return Companion.getBATTLE_ID();
    }

    public static final EntityDataAccessor<Set<String>> getASPECTS() {
        return Companion.getASPECTS();
    }

    public static final EntityDataAccessor<Boolean> getDYING_EFFECTS_STARTED() {
        return Companion.getDYING_EFFECTS_STARTED();
    }

    public static final EntityDataAccessor<PoseType> getPOSE_TYPE() {
        return Companion.getPOSE_TYPE();
    }

    public static final EntityDataAccessor<Integer> getLABEL_LEVEL() {
        return Companion.getLABEL_LEVEL();
    }

    public static final EntityDataAccessor<Boolean> getHIDE_LABEL() {
        return Companion.getHIDE_LABEL();
    }

    public static final EntityDataAccessor<Boolean> getUNBATTLEABLE() {
        return Companion.getUNBATTLEABLE();
    }

    public static final EntityDataAccessor<Boolean> getCOUNTS_TOWARDS_SPAWN_CAP() {
        return Companion.getCOUNTS_TOWARDS_SPAWN_CAP();
    }

    public static final EntityDataAccessor<Float> getSPAWN_DIRECTION() {
        return Companion.getSPAWN_DIRECTION();
    }

    public static final EntityDataAccessor<Integer> getFRIENDSHIP() {
        return Companion.getFRIENDSHIP();
    }

    public static final /* synthetic */ SynchedEntityData access$getDataTracker$p$s-566291850(PokemonEntity $this) {
        return $this.f_19804_;
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\\\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0005\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0010\b\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0007\n\u0002\b\u000b\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\bL\u0010\u000eJ\r\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0003\u0010\u0004R\\\u0010\t\u001a>\u0012\u0018\u0012\u0016\u0012\u0004\u0012\u00020\u0007 \b*\n\u0012\u0004\u0012\u00020\u0007\u0018\u00010\u00060\u0006 \b*\u001e\u0012\u0018\u0012\u0016\u0012\u0004\u0012\u00020\u0007 \b*\n\u0012\u0004\u0012\u00020\u0007\u0018\u00010\u00060\u0006\u0018\u00010\u00050\u00058\u0006X\u0087\u0004\u00a2\u0006\u0012\n\u0004\b\t\u0010\n\u0012\u0004\b\r\u0010\u000e\u001a\u0004\b\u000b\u0010\fR|\u0010\u0011\u001a^\u0012(\u0012&\u0012\f\u0012\n \b*\u0004\u0018\u00010\u00100\u0010 \b*\u0012\u0012\f\u0012\n \b*\u0004\u0018\u00010\u00100\u0010\u0018\u00010\u000f0\u000f \b*.\u0012(\u0012&\u0012\f\u0012\n \b*\u0004\u0018\u00010\u00100\u0010 \b*\u0012\u0012\f\u0012\n \b*\u0004\u0018\u00010\u00100\u0010\u0018\u00010\u000f0\u000f\u0018\u00010\u00050\u00058\u0006X\u0087\u0004\u00a2\u0006\u0012\n\u0004\b\u0011\u0010\n\u0012\u0004\b\u0013\u0010\u000e\u001a\u0004\b\u0012\u0010\fR\u0014\u0010\u0014\u001a\u00020\u00078\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\u0014\u0010\u0015RD\u0010\u0017\u001a&\u0012\f\u0012\n \b*\u0004\u0018\u00010\u00160\u0016 \b*\u0012\u0012\f\u0012\n \b*\u0004\u0018\u00010\u00160\u0016\u0018\u00010\u00050\u00058\u0006X\u0087\u0004\u00a2\u0006\u0012\n\u0004\b\u0017\u0010\n\u0012\u0004\b\u0019\u0010\u000e\u001a\u0004\b\u0018\u0010\fRD\u0010\u001a\u001a&\u0012\f\u0012\n \b*\u0004\u0018\u00010\u00160\u0016 \b*\u0012\u0012\f\u0012\n \b*\u0004\u0018\u00010\u00160\u0016\u0018\u00010\u00050\u00058\u0006X\u0087\u0004\u00a2\u0006\u0012\n\u0004\b\u001a\u0010\n\u0012\u0004\b\u001c\u0010\u000e\u001a\u0004\b\u001b\u0010\fRD\u0010\u001e\u001a&\u0012\f\u0012\n \b*\u0004\u0018\u00010\u001d0\u001d \b*\u0012\u0012\f\u0012\n \b*\u0004\u0018\u00010\u001d0\u001d\u0018\u00010\u00050\u00058\u0006X\u0087\u0004\u00a2\u0006\u0012\n\u0004\b\u001e\u0010\n\u0012\u0004\b \u0010\u000e\u001a\u0004\b\u001f\u0010\fRD\u0010!\u001a&\u0012\f\u0012\n \b*\u0004\u0018\u00010\u001d0\u001d \b*\u0012\u0012\f\u0012\n \b*\u0004\u0018\u00010\u001d0\u001d\u0018\u00010\u00050\u00058\u0006X\u0087\u0004\u00a2\u0006\u0012\n\u0004\b!\u0010\n\u0012\u0004\b#\u0010\u000e\u001a\u0004\b\"\u0010\fRD\u0010%\u001a&\u0012\f\u0012\n \b*\u0004\u0018\u00010$0$ \b*\u0012\u0012\f\u0012\n \b*\u0004\u0018\u00010$0$\u0018\u00010\u00050\u00058\u0006X\u0087\u0004\u00a2\u0006\u0012\n\u0004\b%\u0010\n\u0012\u0004\b'\u0010\u000e\u001a\u0004\b&\u0010\fRD\u0010(\u001a&\u0012\f\u0012\n \b*\u0004\u0018\u00010\u001d0\u001d \b*\u0012\u0012\f\u0012\n \b*\u0004\u0018\u00010\u001d0\u001d\u0018\u00010\u00050\u00058\u0006X\u0087\u0004\u00a2\u0006\u0012\n\u0004\b(\u0010\n\u0012\u0004\b*\u0010\u000e\u001a\u0004\b)\u0010\fRD\u0010+\u001a&\u0012\f\u0012\n \b*\u0004\u0018\u00010$0$ \b*\u0012\u0012\f\u0012\n \b*\u0004\u0018\u00010$0$\u0018\u00010\u00050\u00058\u0006X\u0087\u0004\u00a2\u0006\u0012\n\u0004\b+\u0010\n\u0012\u0004\b-\u0010\u000e\u001a\u0004\b,\u0010\fRD\u0010.\u001a&\u0012\f\u0012\n \b*\u0004\u0018\u00010\u001d0\u001d \b*\u0012\u0012\f\u0012\n \b*\u0004\u0018\u00010\u001d0\u001d\u0018\u00010\u00050\u00058\u0006X\u0087\u0004\u00a2\u0006\u0012\n\u0004\b.\u0010\n\u0012\u0004\b0\u0010\u000e\u001a\u0004\b/\u0010\fRD\u00102\u001a&\u0012\f\u0012\n \b*\u0004\u0018\u00010101 \b*\u0012\u0012\f\u0012\n \b*\u0004\u0018\u00010101\u0018\u00010\u00050\u00058\u0006X\u0087\u0004\u00a2\u0006\u0012\n\u0004\b2\u0010\n\u0012\u0004\b4\u0010\u000e\u001a\u0004\b3\u0010\fRD\u00105\u001a&\u0012\f\u0012\n \b*\u0004\u0018\u00010\u001d0\u001d \b*\u0012\u0012\f\u0012\n \b*\u0004\u0018\u00010\u001d0\u001d\u0018\u00010\u00050\u00058\u0006X\u0087\u0004\u00a2\u0006\u0012\n\u0004\b5\u0010\n\u0012\u0004\b7\u0010\u000e\u001a\u0004\b6\u0010\fRD\u00108\u001a&\u0012\f\u0012\n \b*\u0004\u0018\u00010$0$ \b*\u0012\u0012\f\u0012\n \b*\u0004\u0018\u00010$0$\u0018\u00010\u00050\u00058\u0006X\u0087\u0004\u00a2\u0006\u0012\n\u0004\b8\u0010\n\u0012\u0004\b:\u0010\u000e\u001a\u0004\b9\u0010\fRD\u0010<\u001a&\u0012\f\u0012\n \b*\u0004\u0018\u00010;0; \b*\u0012\u0012\f\u0012\n \b*\u0004\u0018\u00010;0;\u0018\u00010\u00050\u00058\u0006X\u0087\u0004\u00a2\u0006\u0012\n\u0004\b<\u0010\n\u0012\u0004\b>\u0010\u000e\u001a\u0004\b=\u0010\fRD\u0010?\u001a&\u0012\f\u0012\n \b*\u0004\u0018\u00010\u001d0\u001d \b*\u0012\u0012\f\u0012\n \b*\u0004\u0018\u00010\u001d0\u001d\u0018\u00010\u00050\u00058\u0006X\u0087\u0004\u00a2\u0006\u0012\n\u0004\b?\u0010\n\u0012\u0004\bA\u0010\u000e\u001a\u0004\b@\u0010\fRD\u0010C\u001a&\u0012\f\u0012\n \b*\u0004\u0018\u00010B0B \b*\u0012\u0012\f\u0012\n \b*\u0004\u0018\u00010B0B\u0018\u00010\u00050\u00058\u0006X\u0087\u0004\u00a2\u0006\u0012\n\u0004\bC\u0010\n\u0012\u0004\bE\u0010\u000e\u001a\u0004\bD\u0010\fRD\u0010F\u001a&\u0012\f\u0012\n \b*\u0004\u0018\u00010\u00070\u0007 \b*\u0012\u0012\f\u0012\n \b*\u0004\u0018\u00010\u00070\u0007\u0018\u00010\u00050\u00058\u0006X\u0087\u0004\u00a2\u0006\u0012\n\u0004\bF\u0010\n\u0012\u0004\bH\u0010\u000e\u001a\u0004\bG\u0010\fRD\u0010I\u001a&\u0012\f\u0012\n \b*\u0004\u0018\u00010\u001d0\u001d \b*\u0012\u0012\f\u0012\n \b*\u0004\u0018\u00010\u001d0\u001d\u0018\u00010\u00050\u00058\u0006X\u0087\u0004\u00a2\u0006\u0012\n\u0004\bI\u0010\n\u0012\u0004\bK\u0010\u000e\u001a\u0004\bJ\u0010\f\u00a8\u0006M"}, d2={"Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity$Companion;", "", "Lnet/minecraft/entity/attribute/DefaultAttributeContainer$Builder;", "createAttributes", "()Lnet/minecraft/world/entity/ai/attributes/AttributeSupplier$Builder;", "Lnet/minecraft/network/syncher/EntityDataAccessor;", "", "", "kotlin.jvm.PlatformType", "ASPECTS", "Lnet/minecraft/network/syncher/EntityDataAccessor;", "getASPECTS", "()Lnet/minecraft/network/syncher/EntityDataAccessor;", "getASPECTS$annotations", "()V", "Ljava/util/Optional;", "Ljava/util/UUID;", "BATTLE_ID", "getBATTLE_ID", "getBATTLE_ID$annotations", "BATTLE_LOCK", "Ljava/lang/String;", "", "BEAM_MODE", "getBEAM_MODE", "getBEAM_MODE$annotations", "BEHAVIOUR_FLAGS", "getBEHAVIOUR_FLAGS", "getBEHAVIOUR_FLAGS$annotations", "", "COUNTS_TOWARDS_SPAWN_CAP", "getCOUNTS_TOWARDS_SPAWN_CAP", "getCOUNTS_TOWARDS_SPAWN_CAP$annotations", "DYING_EFFECTS_STARTED", "getDYING_EFFECTS_STARTED", "getDYING_EFFECTS_STARTED$annotations", "", "FRIENDSHIP", "getFRIENDSHIP", "getFRIENDSHIP$annotations", "HIDE_LABEL", "getHIDE_LABEL", "getHIDE_LABEL$annotations", "LABEL_LEVEL", "getLABEL_LEVEL", "getLABEL_LEVEL$annotations", "MOVING", "getMOVING", "getMOVING$annotations", "Lnet/minecraft/network/chat/Component;", "NICKNAME", "getNICKNAME", "getNICKNAME$annotations", "NICKNAME_VISIBLE", "getNICKNAME_VISIBLE", "getNICKNAME_VISIBLE$annotations", "PHASING_TARGET_ID", "getPHASING_TARGET_ID", "getPHASING_TARGET_ID$annotations", "Lcom/cobblemon/mod/common/entity/PoseType;", "POSE_TYPE", "getPOSE_TYPE", "getPOSE_TYPE$annotations", "SHOULD_RENDER_NAME", "getSHOULD_RENDER_NAME", "getSHOULD_RENDER_NAME$annotations", "", "SPAWN_DIRECTION", "getSPAWN_DIRECTION", "getSPAWN_DIRECTION$annotations", "SPECIES", "getSPECIES", "getSPECIES$annotations", "UNBATTLEABLE", "getUNBATTLEABLE", "getUNBATTLEABLE$annotations", "<init>", "common"})
    public static final class Companion {
        private Companion() {
        }

        public final EntityDataAccessor<String> getSPECIES() {
            return SPECIES;
        }

        @JvmStatic
        public static /* synthetic */ void getSPECIES$annotations() {
        }

        public final EntityDataAccessor<Component> getNICKNAME() {
            return NICKNAME;
        }

        @JvmStatic
        public static /* synthetic */ void getNICKNAME$annotations() {
        }

        public final EntityDataAccessor<Boolean> getNICKNAME_VISIBLE() {
            return NICKNAME_VISIBLE;
        }

        @JvmStatic
        public static /* synthetic */ void getNICKNAME_VISIBLE$annotations() {
        }

        public final EntityDataAccessor<Boolean> getSHOULD_RENDER_NAME() {
            return SHOULD_RENDER_NAME;
        }

        @JvmStatic
        public static /* synthetic */ void getSHOULD_RENDER_NAME$annotations() {
        }

        public final EntityDataAccessor<Boolean> getMOVING() {
            return MOVING;
        }

        @JvmStatic
        public static /* synthetic */ void getMOVING$annotations() {
        }

        public final EntityDataAccessor<Byte> getBEHAVIOUR_FLAGS() {
            return BEHAVIOUR_FLAGS;
        }

        @JvmStatic
        public static /* synthetic */ void getBEHAVIOUR_FLAGS$annotations() {
        }

        public final EntityDataAccessor<Integer> getPHASING_TARGET_ID() {
            return PHASING_TARGET_ID;
        }

        @JvmStatic
        public static /* synthetic */ void getPHASING_TARGET_ID$annotations() {
        }

        public final EntityDataAccessor<Byte> getBEAM_MODE() {
            return BEAM_MODE;
        }

        @JvmStatic
        public static /* synthetic */ void getBEAM_MODE$annotations() {
        }

        public final EntityDataAccessor<Optional<UUID>> getBATTLE_ID() {
            return BATTLE_ID;
        }

        @JvmStatic
        public static /* synthetic */ void getBATTLE_ID$annotations() {
        }

        public final EntityDataAccessor<Set<String>> getASPECTS() {
            return ASPECTS;
        }

        @JvmStatic
        public static /* synthetic */ void getASPECTS$annotations() {
        }

        public final EntityDataAccessor<Boolean> getDYING_EFFECTS_STARTED() {
            return DYING_EFFECTS_STARTED;
        }

        @JvmStatic
        public static /* synthetic */ void getDYING_EFFECTS_STARTED$annotations() {
        }

        public final EntityDataAccessor<PoseType> getPOSE_TYPE() {
            return POSE_TYPE;
        }

        @JvmStatic
        public static /* synthetic */ void getPOSE_TYPE$annotations() {
        }

        public final EntityDataAccessor<Integer> getLABEL_LEVEL() {
            return LABEL_LEVEL;
        }

        @JvmStatic
        public static /* synthetic */ void getLABEL_LEVEL$annotations() {
        }

        public final EntityDataAccessor<Boolean> getHIDE_LABEL() {
            return HIDE_LABEL;
        }

        @JvmStatic
        public static /* synthetic */ void getHIDE_LABEL$annotations() {
        }

        public final EntityDataAccessor<Boolean> getUNBATTLEABLE() {
            return UNBATTLEABLE;
        }

        @JvmStatic
        public static /* synthetic */ void getUNBATTLEABLE$annotations() {
        }

        public final EntityDataAccessor<Boolean> getCOUNTS_TOWARDS_SPAWN_CAP() {
            return COUNTS_TOWARDS_SPAWN_CAP;
        }

        @JvmStatic
        public static /* synthetic */ void getCOUNTS_TOWARDS_SPAWN_CAP$annotations() {
        }

        public final EntityDataAccessor<Float> getSPAWN_DIRECTION() {
            return SPAWN_DIRECTION;
        }

        @JvmStatic
        public static /* synthetic */ void getSPAWN_DIRECTION$annotations() {
        }

        public final EntityDataAccessor<Integer> getFRIENDSHIP() {
            return FRIENDSHIP;
        }

        @JvmStatic
        public static /* synthetic */ void getFRIENDSHIP$annotations() {
        }

        @NotNull
        public final AttributeSupplier.Builder createAttributes() {
            AttributeSupplier.Builder builder = LivingEntity.m_21183_().m_22266_(Attributes.f_22277_).m_22266_(Attributes.f_22282_);
            Intrinsics.checkNotNullExpressionValue((Object)builder, (String)"createLivingAttributes()\u2026GENERIC_ATTACK_KNOCKBACK)");
            return builder;
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

