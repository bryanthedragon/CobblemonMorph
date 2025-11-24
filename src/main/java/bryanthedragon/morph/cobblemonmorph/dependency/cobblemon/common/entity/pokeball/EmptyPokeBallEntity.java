/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Unit
 *  kotlin.collections.CollectionsKt
 *  kotlin.collections.SetsKt
 *  kotlin.jvm.functions.Function0
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.Ref$IntRef
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.core.particles.ParticleTypes
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.network.protocol.Packet
 *  net.minecraft.network.protocol.game.ClientGamePacketListener
 *  net.minecraft.network.protocol.game.ClientboundAddEntityPacket
 *  net.minecraft.network.syncher.EntityDataAccessor
 *  net.minecraft.network.syncher.EntityDataSerializer
 *  net.minecraft.network.syncher.EntityDataSerializers
 *  net.minecraft.network.syncher.SynchedEntityData
 *  net.minecraft.resources.ResourceKey
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.sounds.SoundEvents
 *  net.minecraft.util.Mth
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.Entity$RemovalReason
 *  net.minecraft.world.entity.EntityDimensions
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.Pose
 *  net.minecraft.world.entity.projectile.ThrowableItemProjectile
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.phys.BlockHitResult
 *  net.minecraft.world.phys.EntityHitResult
 *  net.minecraft.world.phys.HitResult
 *  net.minecraft.world.phys.HitResult$Type
 *  net.minecraft.world.phys.Vec3
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokeball;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonEntities;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonNetwork;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonSounds;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.actor.BattleActor;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.entity.EntitySideDelegate;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.entity.PokemonSideDelegate;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.Cancelable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.CobblemonEvents;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokeball.PokeBallCaptureCalculatedEvent;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokeball.ThrownPokeballHitEvent;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokemon.PokemonCapturedEvent;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.serializers.StringSetDataSerializer;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.serializers.Vec3DataSerializer;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.PokeBalls;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.catching.CaptureContext;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.catching.CaptureEffect;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.catching.calculators.CaptureCalculator;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.status.Statuses;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.CancelableObservable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.EventObservable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.SimpleObservable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.scheduling.Schedulable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.scheduling.ScheduledTask;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.scheduling.SchedulingTracker;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.PokemonStoreManager;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.party.PlayerPartyStore;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.TextKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ActiveBattlePokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.BattleCaptureAction;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.BattleRegistry;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.BattleTypes;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ForcePassActionResponse;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.entity.EmptyPokeBallClientDelegate;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.PoseType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.Poseable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokeball.EmptyPokeBallEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokeball.EmptyPokeBallServerDelegate;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokeball.WaterDragModifier;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonServerDelegate;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.animation.PlayPoseableAnimationPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleCaptureStartPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.spawn.SpawnPokeballPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokeball.PokeBall;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.properties.UncatchableProperty;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.status.PersistentStatusContainer;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.DistributionUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.EntityExtensionsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.WorldExtensionsKt;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u00e4\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u0000 t2\u00020\u00012\u00020\u00022\u00020\u00032\u00020\u0004:\u0002utB\u0011\b\u0016\u0012\u0006\u0010k\u001a\u00020j\u00a2\u0006\u0004\bl\u0010mB+\b\u0016\u0012\u0006\u0010_\u001a\u00020^\u0012\u0006\u0010k\u001a\u00020j\u0012\u0010\b\u0002\u0010o\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00000n\u00a2\u0006\u0004\bl\u0010pB3\b\u0016\u0012\u0006\u0010_\u001a\u00020^\u0012\u0006\u0010k\u001a\u00020j\u0012\u0006\u0010r\u001a\u00020q\u0012\u0010\b\u0002\u0010o\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00000n\u00a2\u0006\u0004\bl\u0010sJ\u0017\u0010\b\u001a\u00020\u00072\u0006\u0010\u0006\u001a\u00020\u0005H\u0002\u00a2\u0006\u0004\b\b\u0010\tJ\r\u0010\n\u001a\u00020\u0007\u00a2\u0006\u0004\b\n\u0010\u000bJ\u000f\u0010\f\u001a\u00020\u0007H\u0002\u00a2\u0006\u0004\b\f\u0010\u000bJ\u000f\u0010\u000e\u001a\u00020\rH\u0016\u00a2\u0006\u0004\b\u000e\u0010\u000fJ\u0015\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00110\u0010H\u0016\u00a2\u0006\u0004\b\u0012\u0010\u0013J\u000f\u0010\u0014\u001a\u00020\u0007H\u0002\u00a2\u0006\u0004\b\u0014\u0010\u000bJ\u000f\u0010\u0016\u001a\u00020\u0015H\u0016\u00a2\u0006\u0004\b\u0016\u0010\u0017J\u000f\u0010\u0019\u001a\u00020\u0018H\u0014\u00a2\u0006\u0004\b\u0019\u0010\u001aJ\u0017\u0010\u001e\u001a\u00020\u001d2\u0006\u0010\u001c\u001a\u00020\u001bH\u0016\u00a2\u0006\u0004\b\u001e\u0010\u001fJ\u000f\u0010 \u001a\u00020\u0007H\u0014\u00a2\u0006\u0004\b \u0010\u000bJ\u0017\u0010#\u001a\u00020\u00072\u0006\u0010\"\u001a\u00020!H\u0014\u00a2\u0006\u0004\b#\u0010$J\u0017\u0010&\u001a\u00020\u00072\u0006\u0010\"\u001a\u00020%H\u0014\u00a2\u0006\u0004\b&\u0010'J\u0017\u0010)\u001a\u00020\u00072\u0006\u0010\"\u001a\u00020(H\u0014\u00a2\u0006\u0004\b)\u0010*J\u001b\u0010-\u001a\u00020\u00072\n\u0010,\u001a\u0006\u0012\u0002\b\u00030+H\u0016\u00a2\u0006\u0004\b-\u0010.J'\u00105\u001a\u00020\u00072\u0006\u00100\u001a\u00020/2\u0006\u00102\u001a\u0002012\u0006\u00104\u001a\u000203H\u0002\u00a2\u0006\u0004\b5\u00106J\u000f\u00107\u001a\u00020\rH\u0016\u00a2\u0006\u0004\b7\u0010\u000fJ\u000f\u00108\u001a\u00020\u0007H\u0016\u00a2\u0006\u0004\b8\u0010\u000bJ\u000f\u0010:\u001a\u000209H\u0016\u00a2\u0006\u0004\b:\u0010;R0\u0010C\u001a\b\u0012\u0004\u0012\u00020=0<2\f\u0010>\u001a\b\u0012\u0004\u0012\u00020=0<8F@FX\u0086\u000e\u00a2\u0006\f\u001a\u0004\b?\u0010@\"\u0004\bA\u0010BR\u001d\u0010E\u001a\b\u0012\u0004\u0012\u00020\r0D8\u0006\u00a2\u0006\f\n\u0004\bE\u0010F\u001a\u0004\bG\u0010HR$\u0010N\u001a\u00020I2\u0006\u0010>\u001a\u00020I8F@FX\u0086\u000e\u00a2\u0006\f\u001a\u0004\bJ\u0010K\"\u0004\bL\u0010MR$\u0010O\u001a\u0004\u0018\u00010\u00058\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\bO\u0010P\u001a\u0004\bQ\u0010R\"\u0004\bS\u0010\tR!\u0010U\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030+0T8\u0006\u00a2\u0006\f\n\u0004\bU\u0010V\u001a\u0004\bW\u0010XR \u0010Z\u001a\b\u0012\u0004\u0012\u00020\u00000Y8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\bZ\u0010[\u001a\u0004\b\\\u0010]R\"\u0010_\u001a\u00020^8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b_\u0010`\u001a\u0004\ba\u0010b\"\u0004\bc\u0010dR\u001a\u0010f\u001a\u00020e8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\bf\u0010g\u001a\u0004\bh\u0010i\u00a8\u0006v"}, d2={"Lcom/cobblemon/mod/common/entity/pokeball/EmptyPokeBallEntity;", "Lnet/minecraft/world/entity/projectile/ThrowableItemProjectile;", "Lcom/cobblemon/mod/common/entity/Poseable;", "Lcom/cobblemon/mod/common/entity/pokeball/WaterDragModifier;", "Lcom/cobblemon/mod/common/api/scheduling/Schedulable;", "Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;", "pokemonEntity", "", "attemptCatch", "(Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;)V", "beginCapture", "()V", "breakFree", "", "canUsePortals", "()Z", "Lnet/minecraft/network/protocol/Packet;", "Lnet/minecraft/network/protocol/game/ClientGamePacketListener;", "createSpawnPacket", "()Lnet/minecraft/network/protocol/Packet;", "drop", "Lcom/cobblemon/mod/common/entity/PoseType;", "getCurrentPoseType", "()Lcom/cobblemon/mod/common/entity/PoseType;", "Lnet/minecraft/world/item/Item;", "getDefaultItem", "()Lnet/minecraft/world/item/Item;", "Lnet/minecraft/world/entity/Pose;", "pPose", "Lnet/minecraft/world/entity/EntityDimensions;", "getDimensions", "(Lnet/minecraft/world/entity/Pose;)Lnet/minecraft/world/entity/EntityDimensions;", "initDataTracker", "Lnet/minecraft/world/phys/BlockHitResult;", "hitResult", "onBlockHit", "(Lnet/minecraft/world/phys/BlockHitResult;)V", "Lnet/minecraft/world/phys/HitResult;", "onCollision", "(Lnet/minecraft/world/phys/HitResult;)V", "Lnet/minecraft/world/phys/EntityHitResult;", "onEntityHit", "(Lnet/minecraft/world/phys/EntityHitResult;)V", "Lnet/minecraft/network/syncher/EntityDataAccessor;", "data", "onTrackedDataSet", "(Lnet/minecraft/network/syncher/EntityDataAccessor;)V", "Lcom/cobblemon/mod/common/api/scheduling/ScheduledTask;", "task", "", "rollsRemaining", "Lcom/cobblemon/mod/common/api/pokeball/catching/CaptureContext;", "captureResult", "shakeBall", "(Lcom/cobblemon/mod/common/api/scheduling/ScheduledTask;ILcom/cobblemon/mod/common/api/pokeball/catching/CaptureContext;)V", "shouldSave", "tick", "", "waterDrag", "()F", "", "", "value", "getAspects", "()Ljava/util/Set;", "setAspects", "(Ljava/util/Set;)V", "aspects", "Ljava/util/concurrent/CompletableFuture;", "captureFuture", "Ljava/util/concurrent/CompletableFuture;", "getCaptureFuture", "()Ljava/util/concurrent/CompletableFuture;", "Lcom/cobblemon/mod/common/entity/pokeball/EmptyPokeBallEntity$CaptureState;", "getCaptureState", "()Lcom/cobblemon/mod/common/entity/pokeball/EmptyPokeBallEntity$CaptureState;", "setCaptureState", "(Lcom/cobblemon/mod/common/entity/pokeball/EmptyPokeBallEntity$CaptureState;)V", "captureState", "capturingPokemon", "Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;", "getCapturingPokemon", "()Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;", "setCapturingPokemon", "Lcom/cobblemon/mod/common/api/reactive/SimpleObservable;", "dataTrackerEmitter", "Lcom/cobblemon/mod/common/api/reactive/SimpleObservable;", "getDataTrackerEmitter", "()Lcom/cobblemon/mod/common/api/reactive/SimpleObservable;", "Lcom/cobblemon/mod/common/api/entity/EntitySideDelegate;", "delegate", "Lcom/cobblemon/mod/common/api/entity/EntitySideDelegate;", "getDelegate", "()Lcom/cobblemon/mod/common/api/entity/EntitySideDelegate;", "Lcom/cobblemon/mod/common/pokeball/PokeBall;", "pokeBall", "Lcom/cobblemon/mod/common/pokeball/PokeBall;", "getPokeBall", "()Lcom/cobblemon/mod/common/pokeball/PokeBall;", "setPokeBall", "(Lcom/cobblemon/mod/common/pokeball/PokeBall;)V", "Lcom/cobblemon/mod/common/api/scheduling/SchedulingTracker;", "schedulingTracker", "Lcom/cobblemon/mod/common/api/scheduling/SchedulingTracker;", "getSchedulingTracker", "()Lcom/cobblemon/mod/common/api/scheduling/SchedulingTracker;", "Lnet/minecraft/world/level/Level;", "world", "<init>", "(Lnet/minecraft/world/level/Level;)V", "Lnet/minecraft/world/entity/EntityType;", "entityType", "(Lcom/cobblemon/mod/common/pokeball/PokeBall;Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/EntityType;)V", "Lnet/minecraft/world/entity/LivingEntity;", "ownerEntity", "(Lcom/cobblemon/mod/common/pokeball/PokeBall;Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/entity/EntityType;)V", "Companion", "CaptureState", "common"})
@SourceDebugExtension(value={"SMAP\nEmptyPokeBallEntity.kt\nKotlin\n*S Kotlin\n*F\n+ 1 EmptyPokeBallEntity.kt\ncom/cobblemon/mod/common/entity/pokeball/EmptyPokeBallEntity\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 4 EventObservables.kt\ncom/cobblemon/mod/common/api/reactive/CancelableObservable\n+ 5 EventObservables.kt\ncom/cobblemon/mod/common/api/reactive/EventObservable\n+ 6 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n+ 7 EventObservables.kt\ncom/cobblemon/mod/common/api/reactive/EventObservable$post$1\n*L\n1#1,470:1\n1#2:471\n1774#3,4:472\n40#4:476\n41#4,6:480\n47#4:488\n17#5,2:477\n19#5:487\n14#5,5:489\n19#5:497\n13579#6:479\n13580#6:486\n13579#6:494\n13580#6:496\n14#7:495\n*S KotlinDebug\n*F\n+ 1 EmptyPokeBallEntity.kt\ncom/cobblemon/mod/common/entity/pokeball/EmptyPokeBallEntity\n*L\n217#1:472,4\n279#1:476\n279#1:480,6\n279#1:488\n279#1:477,2\n279#1:487\n441#1:489,5\n441#1:497\n279#1:479\n279#1:486\n441#1:494\n441#1:496\n441#1:495\n*E\n"})
public final class EmptyPokeBallEntity
extends ThrowableItemProjectile
implements Poseable,
WaterDragModifier,
Schedulable {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private final SimpleObservable<EntityDataAccessor<?>> dataTrackerEmitter;
    @NotNull
    private final SchedulingTracker schedulingTracker;
    @Nullable
    private PokemonEntity capturingPokemon;
    @NotNull
    private final CompletableFuture<Boolean> captureFuture;
    @NotNull
    private final EntitySideDelegate<EmptyPokeBallEntity> delegate;
    @NotNull
    private PokeBall pokeBall;
    private static final EntityDataAccessor<Byte> CAPTURE_STATE = SynchedEntityData.m_135353_(EmptyPokeBallEntity.class, (EntityDataSerializer)EntityDataSerializers.f_135027_);
    private static final EntityDataAccessor<Vec3> HIT_TARGET_POSITION = SynchedEntityData.m_135353_(EmptyPokeBallEntity.class, (EntityDataSerializer)Vec3DataSerializer.INSTANCE);
    private static final EntityDataAccessor<Vec3> HIT_VELOCITY = SynchedEntityData.m_135353_(EmptyPokeBallEntity.class, (EntityDataSerializer)Vec3DataSerializer.INSTANCE);
    private static final EntityDataAccessor<Boolean> SHAKE = SynchedEntityData.m_135353_(EmptyPokeBallEntity.class, (EntityDataSerializer)EntityDataSerializers.f_135035_);
    private static final EntityDataAccessor<Set<String>> ASPECTS = SynchedEntityData.m_135353_(EmptyPokeBallEntity.class, (EntityDataSerializer)StringSetDataSerializer.INSTANCE);
    public static final float SECONDS_BETWEEN_SHAKES = 1.25f;
    public static final float SECONDS_BEFORE_SHAKE = 1.0f;
    @NotNull
    private static final EntityDimensions DIMENSIONS = new EntityDimensions(0.4f, 0.4f, true);

    @NotNull
    public final SimpleObservable<EntityDataAccessor<?>> getDataTrackerEmitter() {
        return this.dataTrackerEmitter;
    }

    @Override
    @NotNull
    public SchedulingTracker getSchedulingTracker() {
        return this.schedulingTracker;
    }

    @Nullable
    public final PokemonEntity getCapturingPokemon() {
        return this.capturingPokemon;
    }

    public final void setCapturingPokemon(@Nullable PokemonEntity pokemonEntity) {
        this.capturingPokemon = pokemonEntity;
    }

    @NotNull
    public final CompletableFuture<Boolean> getCaptureFuture() {
        return this.captureFuture;
    }

    @NotNull
    public final CaptureState getCaptureState() {
        return CaptureState.values()[((Number)this.f_19804_.m_135370_(CAPTURE_STATE)).byteValue()];
    }

    public final void setCaptureState(@NotNull CaptureState value2) {
        Intrinsics.checkNotNullParameter((Object)((Object)value2), (String)"value");
        this.f_19804_.m_135381_(CAPTURE_STATE, (Object)((byte)value2.ordinal()));
    }

    @NotNull
    public final Set<String> getAspects() {
        Object object = this.f_19804_.m_135370_(ASPECTS);
        Intrinsics.checkNotNullExpressionValue((Object)object, (String)"dataTracker.get(ASPECTS)");
        return (Set)object;
    }

    public final void setAspects(@NotNull Set<String> value2) {
        Intrinsics.checkNotNullParameter(value2, (String)"value");
        this.f_19804_.m_135381_(ASPECTS, value2);
    }

    @NotNull
    public EntitySideDelegate<EmptyPokeBallEntity> getDelegate() {
        return this.delegate;
    }

    protected void m_8097_() {
        super.m_8097_();
        this.f_19804_.m_135372_(CAPTURE_STATE, (Object)((byte)CaptureState.NOT.ordinal()));
        this.f_19804_.m_135372_(ASPECTS, (Object)SetsKt.emptySet());
        this.f_19804_.m_135372_(HIT_TARGET_POSITION, (Object)Vec3.f_82478_);
        this.f_19804_.m_135372_(HIT_VELOCITY, (Object)Vec3.f_82478_);
        this.f_19804_.m_135372_(SHAKE, (Object)false);
    }

    public void m_7350_(@NotNull EntityDataAccessor<?> data) {
        Intrinsics.checkNotNullParameter(data, (String)"data");
        super.m_7350_(data);
        if (this.getDelegate() != null) {
            this.getDelegate().onTrackedDataSet(data);
        }
        if (Intrinsics.areEqual(data, CAPTURE_STATE)) {
            Byte newState = (Byte)this.f_19804_.m_135370_(CAPTURE_STATE);
            switch (WhenMappings.$EnumSwitchMapping$0[CaptureState.values()[newState].ordinal()]) {
                case 1: {
                    this.m_20242_(false);
                    break;
                }
                case 2: {
                    break;
                }
                case 3: {
                    this.m_20242_(false);
                    break;
                }
                case 4: {
                    this.m_20242_(true);
                }
            }
        }
        EntityDataAccessor[] entityDataAccessorArray = new EntityDataAccessor[]{data};
        this.dataTrackerEmitter.emit(entityDataAccessorArray);
    }

    public EmptyPokeBallEntity(@NotNull Level world) {
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        this(PokeBalls.INSTANCE.getPOKE_BALL(), world, null, 4, null);
    }

    public EmptyPokeBallEntity(@NotNull PokeBall pokeBall, @NotNull Level world, @NotNull EntityType<? extends EmptyPokeBallEntity> entityType) {
        Intrinsics.checkNotNullParameter((Object)pokeBall, (String)"pokeBall");
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        Intrinsics.checkNotNullParameter(entityType, (String)"entityType");
        super(entityType, world);
        this.dataTrackerEmitter = new SimpleObservable();
        this.schedulingTracker = new SchedulingTracker();
        this.captureFuture = new CompletableFuture();
        this.delegate = this.m_9236_().f_46443_ ? (EntitySideDelegate)new EmptyPokeBallClientDelegate() : (EntitySideDelegate)new EmptyPokeBallServerDelegate();
        this.getDelegate().initialize((EmptyPokeBallEntity)((Entity)this));
        this.pokeBall = PokeBalls.INSTANCE.getPOKE_BALL();
        this.pokeBall = pokeBall;
    }

    public /* synthetic */ EmptyPokeBallEntity(PokeBall pokeBall, Level level, EntityType entityType, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 4) != 0) {
            entityType = CobblemonEntities.EMPTY_POKEBALL;
        }
        this(pokeBall, level, entityType);
    }

    public EmptyPokeBallEntity(@NotNull PokeBall pokeBall, @NotNull Level world, @NotNull LivingEntity ownerEntity, @NotNull EntityType<? extends EmptyPokeBallEntity> entityType) {
        Intrinsics.checkNotNullParameter((Object)pokeBall, (String)"pokeBall");
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        Intrinsics.checkNotNullParameter((Object)ownerEntity, (String)"ownerEntity");
        Intrinsics.checkNotNullParameter(entityType, (String)"entityType");
        super(entityType, ownerEntity, world);
        this.dataTrackerEmitter = new SimpleObservable();
        this.schedulingTracker = new SchedulingTracker();
        this.captureFuture = new CompletableFuture();
        this.delegate = this.m_9236_().f_46443_ ? (EntitySideDelegate)new EmptyPokeBallClientDelegate() : (EntitySideDelegate)new EmptyPokeBallServerDelegate();
        this.getDelegate().initialize((EmptyPokeBallEntity)((Entity)this));
        this.pokeBall = PokeBalls.INSTANCE.getPOKE_BALL();
        this.pokeBall = pokeBall;
    }

    public /* synthetic */ EmptyPokeBallEntity(PokeBall pokeBall, Level level, LivingEntity livingEntity, EntityType entityType, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 8) != 0) {
            entityType = CobblemonEntities.EMPTY_POKEBALL;
        }
        this(pokeBall, level, livingEntity, entityType);
    }

    @NotNull
    public final PokeBall getPokeBall() {
        return this.pokeBall;
    }

    public final void setPokeBall(@NotNull PokeBall pokeBall) {
        Intrinsics.checkNotNullParameter((Object)pokeBall, (String)"<set-?>");
        this.pokeBall = pokeBall;
    }

    protected void m_8060_(@NotNull BlockHitResult hitResult) {
        Intrinsics.checkNotNullParameter((Object)hitResult, (String)"hitResult");
        if (this.getCaptureState() == CaptureState.NOT) {
            Level level = this.m_9236_();
            Intrinsics.checkNotNullExpressionValue((Object)level, (String)"world");
            if (DistributionUtilsKt.isServerSide(level)) {
                ServerPlayer player;
                super.m_8060_(hitResult);
                Level level2 = this.m_9236_();
                Intrinsics.checkNotNullExpressionValue((Object)level2, (String)"world");
                ParticleOptions particleOptions = (ParticleOptions)ParticleTypes.f_123796_;
                Vec3 vec3 = hitResult.m_82450_();
                Intrinsics.checkNotNullExpressionValue((Object)vec3, (String)"hitResult.pos");
                Vec3 vec32 = hitResult.m_82450_().m_82546_(this.m_20182_()).m_82541_().m_82490_(-0.1);
                Intrinsics.checkNotNullExpressionValue((Object)vec32, (String)"hitResult.pos.subtract(p\u2026ormalize().multiply(-0.1)");
                WorldExtensionsKt.sendParticlesServer(level2, particleOptions, vec3, 2, vec32, 0.0);
                Level level3 = this.m_9236_();
                Intrinsics.checkNotNullExpressionValue((Object)level3, (String)"world");
                Vec3 vec33 = this.m_20182_();
                Intrinsics.checkNotNullExpressionValue((Object)vec33, (String)"pos");
                SoundEvent soundEvent = SoundEvents.f_12635_;
                Intrinsics.checkNotNullExpressionValue((Object)soundEvent, (String)"BLOCK_WOOD_PLACE");
                WorldExtensionsKt.playSoundServer$default(level3, vec33, soundEvent, null, 0.0f, 2.5f, 12, null);
                this.m_146870_();
                Entity entity2 = this.m_19749_();
                ServerPlayer serverPlayer = player = entity2 instanceof ServerPlayer ? (ServerPlayer)entity2 : null;
                boolean bl = serverPlayer != null ? !serverPlayer.m_7500_() : false;
                if (bl) {
                    this.m_19998_((ItemLike)this.m_7881_());
                }
            }
        } else {
            this.m_20242_(false);
            this.m_20256_(Vec3.f_82478_);
        }
    }

    /*
     * WARNING - void declaration
     */
    protected void m_5790_(@NotNull EntityHitResult hitResult) {
        block23: {
            PokemonEntity pokemonEntity;
            block27: {
                Entity owner;
                block24: {
                    BattleCaptureAction battleCaptureAction;
                    ActiveBattlePokemon hitBattlePokemon;
                    BattleActor throwerActor;
                    PokemonBattle battle2;
                    block26: {
                        block25: {
                            boolean bl;
                            BattleActor hitActor;
                            Object object;
                            BattleActor battleActor;
                            block21: {
                                Intrinsics.checkNotNullParameter((Object)hitResult, (String)"hitResult");
                                if (this.getCaptureState() != CaptureState.NOT || !(hitResult.m_82443_() instanceof PokemonEntity)) break block23;
                                Level level = this.m_9236_();
                                Intrinsics.checkNotNullExpressionValue((Object)level, (String)"world");
                                if (!DistributionUtilsKt.isServerSide(level)) break block23;
                                Entity entity2 = hitResult.m_82443_();
                                Intrinsics.checkNotNull((Object)entity2, (String)"null cannot be cast to non-null type bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity");
                                pokemonEntity = (PokemonEntity)entity2;
                                PokemonSideDelegate pokemonSideDelegate = pokemonEntity.getDelegate();
                                Intrinsics.checkNotNull((Object)pokemonSideDelegate, (String)"null cannot be cast to non-null type bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonServerDelegate");
                                battle2 = ((PokemonServerDelegate)pokemonSideDelegate).getBattle();
                                owner = this.m_19749_();
                                if (!pokemonEntity.getPokemon().isWild()) {
                                    Entity entity3 = owner;
                                    if (entity3 != null) {
                                        Object[] objectArray = new Object[]{pokemonEntity.getExposedSpecies().getTranslatedName()};
                                        MutableComponent mutableComponent = LocalizationUtilsKt.lang("capture.not_wild", objectArray);
                                        Intrinsics.checkNotNullExpressionValue((Object)mutableComponent, (String)"lang(\"capture.not_wild\",\u2026edSpecies.translatedName)");
                                        entity3.m_213846_((Component)TextKt.red(mutableComponent));
                                    }
                                    this.drop();
                                    return;
                                }
                                if (!UncatchableProperty.INSTANCE.isCatchable(pokemonEntity)) {
                                    Entity entity4 = owner;
                                    if (entity4 != null) {
                                        MutableComponent mutableComponent = LocalizationUtilsKt.lang("capture.cannot_be_caught", new Object[0]);
                                        Intrinsics.checkNotNullExpressionValue((Object)mutableComponent, (String)"lang(\"capture.cannot_be_caught\")");
                                        entity4.m_213846_((Component)TextKt.red(mutableComponent));
                                    }
                                    this.drop();
                                    return;
                                }
                                if (battle2 == null || owner == null || !(owner instanceof LivingEntity)) break block24;
                                UUID uUID = ((LivingEntity)owner).m_20148_();
                                Intrinsics.checkNotNullExpressionValue((Object)uUID, (String)"owner.uuid");
                                throwerActor = battle2.getActor(uUID);
                                Iterable<BattleActor> iterable = battle2.getActors();
                                for (BattleActor object3 : iterable) {
                                    Iterator it3 = object3;
                                    boolean bl2 = false;
                                    if (!((BattleActor)((Object)it3)).isForPokemon(pokemonEntity)) continue;
                                    battleActor = object3;
                                    break block21;
                                }
                                battleActor = null;
                            }
                            if ((object = (hitActor = (BattleActor)battleActor)) != null && (object = ((BattleActor)object).getActivePokemon()) != null) {
                                Object v11;
                                block22: {
                                    Iterable iterable = (Iterable)object;
                                    for (Object bl2 : iterable) {
                                        ActiveBattlePokemon it2 = (ActiveBattlePokemon)bl2;
                                        boolean bl22 = false;
                                        Object object2 = it2.getBattlePokemon();
                                        if (!Intrinsics.areEqual((Object)(object2 != null && (object2 = ((BattlePokemon)object2).getEffectedPokemon()) != null ? ((Pokemon)object2).getEntity() : null), (Object)pokemonEntity)) continue;
                                        v11 = bl2;
                                        break block22;
                                    }
                                    v11 = null;
                                }
                                v12 = v11;
                            } else {
                                v12 = hitBattlePokemon = null;
                            }
                            if (throwerActor == null) {
                                LivingEntity livingEntity = (LivingEntity)owner;
                                Object[] objectArray = new Object[]{pokemonEntity.getExposedSpecies().getTranslatedName()};
                                MutableComponent mutableComponent = LocalizationUtilsKt.lang("capture.in_battle", objectArray);
                                Intrinsics.checkNotNullExpressionValue((Object)mutableComponent, (String)"lang(\"capture.in_battle\"\u2026edSpecies.translatedName)");
                                livingEntity.m_213846_((Component)TextKt.red(mutableComponent));
                                this.drop();
                                return;
                            }
                            if (hitActor == null || hitBattlePokemon == null) {
                                this.drop();
                                return;
                            }
                            if (!Intrinsics.areEqual((Object)battle2.getFormat().getBattleType(), (Object)BattleTypes.INSTANCE.getSINGLES())) break block25;
                            Iterable $this$count$iv = hitActor.getPokemonList();
                            boolean $i$f$count = false;
                            if ($this$count$iv instanceof Collection && ((Collection)$this$count$iv).isEmpty()) {
                                bl = false;
                            } else {
                                void var10_18;
                                boolean bl3 = false;
                                for (Object element$iv : $this$count$iv) {
                                    BattlePokemon it = (BattlePokemon)element$iv;
                                    boolean bl4 = false;
                                    if (!(it.getHealth() > 0) || ++var10_18 >= 0) continue;
                                    CollectionsKt.throwCountOverflow();
                                }
                                bl = var10_18;
                            }
                            if (bl <= true) break block26;
                        }
                        LivingEntity livingEntity = (LivingEntity)owner;
                        MutableComponent mutableComponent = LocalizationUtilsKt.lang("capture.not_single", new Object[0]);
                        Intrinsics.checkNotNullExpressionValue((Object)mutableComponent, (String)"lang(\"capture.not_single\")");
                        livingEntity.m_213846_((Component)TextKt.red(mutableComponent));
                        this.drop();
                        return;
                    }
                    boolean canFitForcedAction = throwerActor.canFitForcedAction();
                    if (!canFitForcedAction) {
                        LivingEntity livingEntity = (LivingEntity)owner;
                        MutableComponent mutableComponent = LocalizationUtilsKt.lang("capture.not_your_turn", new Object[0]);
                        Intrinsics.checkNotNullExpressionValue((Object)mutableComponent, (String)"lang(\"capture.not_your_turn\")");
                        livingEntity.m_213846_((Component)TextKt.red(mutableComponent));
                        this.drop();
                        return;
                    }
                    BattleCaptureAction battleCaptureAction2 = battleCaptureAction = new BattleCaptureAction(battle2, hitBattlePokemon, this);
                    List<BattleCaptureAction> list = battle2.getCaptureActions();
                    boolean bl = false;
                    battleCaptureAction2.attach();
                    list.add(battleCaptureAction);
                    Object[] objectArray = new Object[3];
                    objectArray[0] = throwerActor.getName();
                    Intrinsics.checkNotNullExpressionValue((Object)this.pokeBall.item().m_41466_(), (String)"pokeBall.item().name");
                    objectArray[2] = pokemonEntity.getExposedSpecies().getTranslatedName();
                    battleCaptureAction = LocalizationUtilsKt.lang("capture.attempted_capture", objectArray);
                    Intrinsics.checkNotNullExpressionValue((Object)battleCaptureAction, (String)"lang(\n                  \u2026                        )");
                    battle2.broadcastChatMessage((Component)TextKt.yellow((MutableComponent)battleCaptureAction));
                    battle2.sendUpdate(new BattleCaptureStartPacket(this.pokeBall.getName(), this.getAspects(), hitBattlePokemon.getPNX()));
                    throwerActor.forceChoose(new ForcePassActionResponse());
                    break block27;
                }
                if (pokemonEntity.isBusy()) {
                    Entity entity5 = owner;
                    if (entity5 != null) {
                        Object[] objectArray = new Object[]{pokemonEntity.getExposedSpecies().getTranslatedName()};
                        MutableComponent mutableComponent = LocalizationUtilsKt.lang("capture.busy", objectArray);
                        Intrinsics.checkNotNullExpressionValue((Object)mutableComponent, (String)"lang(\"capture.busy\", pok\u2026edSpecies.translatedName)");
                        entity5.m_213846_((Component)TextKt.red(mutableComponent));
                    }
                    this.drop();
                    return;
                }
                if (owner instanceof ServerPlayer && BattleRegistry.INSTANCE.getBattleByParticipatingPlayer((ServerPlayer)owner) != null) {
                    ServerPlayer serverPlayer = (ServerPlayer)owner;
                    MutableComponent mutableComponent = LocalizationUtilsKt.lang("you_in_battle", new Object[0]);
                    Intrinsics.checkNotNullExpressionValue((Object)mutableComponent, (String)"lang(\"you_in_battle\")");
                    serverPlayer.m_213846_((Component)TextKt.red(mutableComponent));
                    this.drop();
                    return;
                }
            }
            this.capturingPokemon = pokemonEntity;
            this.f_19804_.m_135381_(HIT_VELOCITY, (Object)this.m_20184_().m_82541_());
            this.f_19804_.m_135381_(HIT_TARGET_POSITION, (Object)hitResult.m_82450_());
            this.attemptCatch(pokemonEntity);
            return;
        }
        super.m_5790_(hitResult);
    }

    private final void drop() {
        ServerPlayer player;
        Object object;
        Entity owner = this.m_19749_();
        this.m_146870_();
        Entity entity2 = owner;
        if (entity2 != null) {
            Entity entity3;
            Entity it = entity3 = entity2;
            boolean bl = false;
            object = it instanceof ServerPlayer ? entity3 : null;
        } else {
            object = null;
        }
        Entity entity4 = object;
        ServerPlayer serverPlayer = player = entity4 instanceof ServerPlayer ? (ServerPlayer)entity4 : null;
        if (!(serverPlayer != null ? serverPlayer.m_7500_() : false)) {
            this.m_19998_((ItemLike)this.m_7881_());
        }
    }

    public boolean m_142391_() {
        return false;
    }

    /*
     * WARNING - void declaration
     */
    public void m_8119_() {
        Vec3 hitTargetPosition;
        block10: {
            block11: {
                super.m_8119_();
                this.getDelegate().tick((EmptyPokeBallEntity)((Entity)this));
                Level level = this.m_9236_();
                Intrinsics.checkNotNullExpressionValue((Object)level, (String)"world");
                if (!DistributionUtilsKt.isServerSide(level)) break block10;
                PokemonEntity pokemonEntity = this.capturingPokemon;
                if (pokemonEntity != null) {
                    void this_$iv$iv;
                    PokemonEntity it = pokemonEntity;
                    boolean bl = false;
                    if (!it.m_20145_()) {
                        this.f_19804_.m_135381_(HIT_TARGET_POSITION, (Object)it.m_20182_());
                    }
                    CancelableObservable<ThrownPokeballHitEvent> cancelableObservable = CobblemonEvents.THROWN_POKEBALL_HIT;
                    ThrownPokeballHitEvent thrownPokeballHitEvent = new ThrownPokeballHitEvent(this, it);
                    CancelableObservable<ThrownPokeballHitEvent> this_$iv = cancelableObservable;
                    boolean $i$f$postThen = false;
                    EventObservable eventObservable = this_$iv;
                    Cancelable[] cancelableArray = new Cancelable[]{thrownPokeballHitEvent};
                    Cancelable[] events$iv$iv = cancelableArray;
                    boolean $i$f$post = false;
                    this_$iv$iv.emit(Arrays.copyOf(events$iv$iv, events$iv$iv.length));
                    Cancelable[] $this$forEach$iv$iv$iv = events$iv$iv;
                    boolean $i$f$forEach = false;
                    int n = $this$forEach$iv$iv$iv.length;
                    for (int i = 0; i < n; ++i) {
                        ThrownPokeballHitEvent it2;
                        Cancelable element$iv$iv$iv;
                        Cancelable it$iv = element$iv$iv$iv = $this$forEach$iv$iv$iv[i];
                        boolean bl2 = false;
                        if (it$iv.isCanceled()) {
                            it2 = (ThrownPokeballHitEvent)it$iv;
                            boolean bl3 = false;
                            this.drop();
                            return;
                        }
                        it2 = (ThrownPokeballHitEvent)it$iv;
                        boolean bl4 = false;
                    }
                }
                if (this.f_19797_ > 600 && this.capturingPokemon == null) {
                    this.m_142687_(Entity.RemovalReason.DISCARDED);
                }
                if (this.m_19749_() == null) break block11;
                Entity entity2 = this.m_19749_();
                Intrinsics.checkNotNull((Object)entity2);
                if (!entity2.m_6084_()) break block11;
                if (this.getCaptureState() == CaptureState.NOT) break block10;
                PokemonEntity pokemonEntity2 = this.capturingPokemon;
                if (pokemonEntity2 != null ? pokemonEntity2.m_6084_() : false) break block10;
            }
            this.breakFree();
            this.m_146870_();
            return;
        }
        if (!((hitTargetPosition = (Vec3)this.f_19804_.m_135370_(HIT_TARGET_POSITION)).m_82553_() == 0.0)) {
            Vec3 diff = hitTargetPosition.m_82546_(this.m_20182_());
            this.m_146922_((float)(Mth.m_14136_((double)diff.f_82479_, (double)diff.f_82481_) * (double)180 / Math.PI));
        }
        this.getSchedulingTracker().update(0.05f);
    }

    private final void shakeBall(ScheduledTask task, int rollsRemaining, CaptureContext captureResult) {
        block9: {
            block8: {
                PokemonEntity pokemonEntity = this.capturingPokemon;
                boolean bl = pokemonEntity != null ? pokemonEntity.m_6084_() : false;
                if (!bl || !this.m_6084_() || this.m_19749_() == null) break block8;
                Entity entity2 = this.m_19749_();
                if (entity2 != null ? entity2.m_6084_() : false) break block9;
            }
            PokemonEntity pokemonEntity = this.capturingPokemon;
            boolean bl = pokemonEntity != null ? pokemonEntity.m_6084_() : false;
            if (bl) {
                this.breakFree();
            }
            this.m_146870_();
            task.expire();
            return;
        }
        if (rollsRemaining <= 0) {
            if (captureResult.isSuccessfulCapture()) {
                this.setCaptureState(captureResult.isCriticalCapture() ? CaptureState.CAPTURED_CRITICAL : CaptureState.CAPTURED);
                Level level = this.m_9236_();
                Intrinsics.checkNotNullExpressionValue((Object)level, (String)"world");
                Vec3 vec3 = this.m_20182_();
                Intrinsics.checkNotNullExpressionValue((Object)vec3, (String)"pos");
                WorldExtensionsKt.playSoundServer$default(level, vec3, CobblemonSounds.POKE_BALL_CAPTURE_SUCCEEDED, null, 0.8f, 1.0f, 4, null);
                PokemonEntity pokemonEntity = this.capturingPokemon;
                if (pokemonEntity == null) {
                    return;
                }
                PokemonEntity pokemon = pokemonEntity;
                Entity entity3 = this.m_19749_();
                ServerPlayer serverPlayer = entity3 instanceof ServerPlayer ? (ServerPlayer)entity3 : null;
                if (serverPlayer == null) {
                    return;
                }
                ServerPlayer player = serverPlayer;
                this.after(1.0f, (Function0<Unit>)((Function0)new Function0<Unit>(pokemon, this, player){
                    final /* synthetic */ PokemonEntity $pokemon;
                    final /* synthetic */ EmptyPokeBallEntity this$0;
                    final /* synthetic */ ServerPlayer $player;
                    {
                        this.$pokemon = $pokemon;
                        this.this$0 = $receiver;
                        this.$player = $player;
                        super(0);
                    }

                    /*
                     * WARNING - void declaration
                     */
                    public final void invoke() {
                        if (this.$pokemon.getPokemon().isWild() && this.$pokemon.m_6084_() && !this.this$0.getCaptureFuture().isDone()) {
                            void events$iv;
                            void $this$iv;
                            EventObservable<PokemonCapturedEvent> $this$forEach$iv;
                            this.$pokemon.m_146870_();
                            this.this$0.m_146870_();
                            this.this$0.getCaptureFuture().complete(true);
                            PokemonStoreManager pokemonStoreManager = Cobblemon.INSTANCE.getStorage();
                            UUID uUID = this.$player.m_20148_();
                            Intrinsics.checkNotNullExpressionValue((Object)uUID, (String)"player.uuid");
                            PlayerPartyStore party = pokemonStoreManager.getParty(uUID);
                            this.$pokemon.getPokemon().setCaughtBall(this.this$0.getPokeBall());
                            Iterable iterable = this.this$0.getPokeBall().getEffects();
                            PokemonCapturedEvent[] pokemonCapturedEventArray = this.$player;
                            PokemonEntity pokemonEntity = this.$pokemon;
                            boolean $i$f$forEach = false;
                            Iterator<T> iterator = $this$forEach$iv.iterator();
                            while (iterator.hasNext()) {
                                T element$iv = iterator.next();
                                CaptureEffect effect = (CaptureEffect)element$iv;
                                boolean bl = false;
                                effect.apply((LivingEntity)pokemonCapturedEventArray, pokemonEntity.getPokemon());
                            }
                            party.add(this.$pokemon.getPokemon());
                            $this$forEach$iv = CobblemonEvents.POKEMON_CAPTURED;
                            pokemonCapturedEventArray = new PokemonCapturedEvent[]{new PokemonCapturedEvent(this.$pokemon.getPokemon(), this.$player, this.this$0)};
                            boolean $i$f$post = false;
                            $this$iv.emit(Arrays.copyOf(events$iv, ((void)events$iv).length));
                            void $this$forEach$iv$iv = events$iv;
                            boolean $i$f$forEach2 = false;
                            int n = ((void)$this$forEach$iv$iv).length;
                            for (int i = 0; i < n; ++i) {
                                void element$iv$iv;
                                void var11_13 = element$iv$iv = $this$forEach$iv$iv[i];
                                boolean bl = false;
                                void it = var11_13;
                            }
                        }
                    }
                }));
                return;
            }
            this.breakFree();
            return;
        }
        Level level = this.m_9236_();
        Intrinsics.checkNotNullExpressionValue((Object)level, (String)"world");
        Vec3 vec3 = this.m_20182_();
        Intrinsics.checkNotNullExpressionValue((Object)vec3, (String)"pos");
        WorldExtensionsKt.playSoundServer$default(level, vec3, CobblemonSounds.POKE_BALL_SHAKE, null, 0.8f, 0.0f, 20, null);
        SynchedEntityData synchedEntityData = this.f_19804_;
        Intrinsics.checkNotNullExpressionValue((Object)synchedEntityData, (String)"dataTracker");
        EntityDataAccessor<Boolean> entityDataAccessor = SHAKE;
        Intrinsics.checkNotNullExpressionValue(entityDataAccessor, (String)"SHAKE");
        EntityExtensionsKt.update(synchedEntityData, entityDataAccessor, shakeBall.2.INSTANCE);
    }

    private final void breakFree() {
        PokemonEntity pokemonEntity = this.capturingPokemon;
        if (pokemonEntity == null) {
            return;
        }
        PokemonEntity pokemon = pokemonEntity;
        pokemon.m_146884_(this.m_20182_());
        pokemon.setBeamMode(2);
        pokemon.m_6842_(false);
        if (pokemon.getBattleId() == null) {
            PersistentStatusContainer persistentStatusContainer = pokemon.getPokemon().getStatus();
            if (persistentStatusContainer != null) {
                PersistentStatusContainer persistentStatusContainer2;
                PersistentStatusContainer it = persistentStatusContainer2 = persistentStatusContainer;
                boolean bl = false;
                persistentStatusContainer = Intrinsics.areEqual((Object)it.getStatus(), (Object)Statuses.INSTANCE.getSLEEP()) ? persistentStatusContainer2 : null;
                if (persistentStatusContainer != null) {
                    it = persistentStatusContainer;
                    boolean bl2 = false;
                    pokemon.getPokemon().setStatus(null);
                }
            }
        }
        this.setCaptureState(CaptureState.BROKEN_FREE);
        Level level = this.m_9236_();
        Intrinsics.checkNotNullExpressionValue((Object)level, (String)"world");
        Vec3 vec3 = this.m_20182_();
        Intrinsics.checkNotNullExpressionValue((Object)vec3, (String)"pos");
        WorldExtensionsKt.playSoundServer$default(level, vec3, CobblemonSounds.POKE_BALL_OPEN, null, 0.8f, 0.0f, 20, null);
        this.after(1.0f, (Function0<Unit>)((Function0)new Function0<Unit>(pokemon, this){
            final /* synthetic */ PokemonEntity $pokemon;
            final /* synthetic */ EmptyPokeBallEntity this$0;
            {
                this.$pokemon = $pokemon;
                this.this$0 = $receiver;
                super(0);
            }

            public final void invoke() {
                this.$pokemon.getBusyLocks().remove(this.this$0);
                this.this$0.getCaptureFuture().complete(false);
                Level level = this.this$0.m_9236_();
                Intrinsics.checkNotNullExpressionValue((Object)level, (String)"world");
                ParticleOptions particleOptions = (ParticleOptions)ParticleTypes.f_123796_;
                Vec3 vec3 = this.this$0.m_20182_();
                Intrinsics.checkNotNullExpressionValue((Object)vec3, (String)"pos");
                WorldExtensionsKt.sendParticlesServer(level, particleOptions, vec3, 20, new Vec3(0.0, 0.2, 0.0), 0.05);
                this.this$0.m_146870_();
            }
        }));
    }

    @NotNull
    protected Item m_7881_() {
        return this.pokeBall.item();
    }

    @NotNull
    public EntityDimensions m_6972_(@NotNull Pose pPose) {
        Intrinsics.checkNotNullParameter((Object)pPose, (String)"pPose");
        return DIMENSIONS;
    }

    private final void attemptCatch(PokemonEntity pokemonEntity) {
        pokemonEntity.getBusyLocks().add(this);
        Vec3 displace = this.m_20184_();
        this.setCaptureState(CaptureState.HIT);
        int mul = this.f_19796_.m_188499_() ? 1 : -1;
        Level level = this.m_9236_();
        Intrinsics.checkNotNullExpressionValue((Object)level, (String)"world");
        Vec3 vec3 = this.m_20182_();
        Intrinsics.checkNotNullExpressionValue((Object)vec3, (String)"pos");
        WorldExtensionsKt.playSoundServer$default(level, vec3, CobblemonSounds.POKE_BALL_HIT, null, 0.4f, 0.0f, 20, null);
        PlayPoseableAnimationPacket pkt = new PlayPoseableAnimationPacket(pokemonEntity.m_19879_(), SetsKt.setOf((Object)"recoil"), SetsKt.emptySet());
        double d = pokemonEntity.m_20185_();
        double d2 = pokemonEntity.m_20186_();
        double d3 = pokemonEntity.m_20189_();
        ResourceKey resourceKey = pokemonEntity.m_9236_().m_46472_();
        NetworkPacket networkPacket = pkt;
        Intrinsics.checkNotNullExpressionValue((Object)resourceKey, (String)"registryKey");
        NetworkPacket.DefaultImpls.sendToPlayersAround$default(networkPacket, d, d2, d3, 50.0, resourceKey, null, 32, null);
        this.m_20256_(displace.m_82542_(-1.0, 0.0, -1.0).m_82541_().m_82524_((float)mul * (float)Math.PI / (float)3).m_82542_(0.1, 0.0, 0.1).m_82520_(0.0, 0.3333333333333333, 0.0));
        pokemonEntity.setPhasingTargetId(this.m_19879_());
        this.after(0.7f, (Function0<Unit>)((Function0)new Function0<Unit>(this, pokemonEntity){
            final /* synthetic */ EmptyPokeBallEntity this$0;
            final /* synthetic */ PokemonEntity $pokemonEntity;
            {
                this.this$0 = $receiver;
                this.$pokemonEntity = $pokemonEntity;
                super(0);
            }

            public final void invoke() {
                this.this$0.m_20256_(Vec3.f_82478_);
                this.this$0.m_20242_(true);
                Level level = this.this$0.m_9236_();
                Intrinsics.checkNotNullExpressionValue((Object)level, (String)"world");
                Vec3 vec3 = this.this$0.m_20182_();
                Intrinsics.checkNotNullExpressionValue((Object)vec3, (String)"pos");
                WorldExtensionsKt.playSoundServer$default(level, vec3, CobblemonSounds.POKE_BALL_CAPTURE_STARTED, null, 0.6f, 0.0f, 20, null);
                this.$pokemonEntity.setBeamMode(3);
            }
        }));
        this.after(2.2f, (Function0<Unit>)((Function0)new Function0<Unit>(pokemonEntity, this){
            final /* synthetic */ PokemonEntity $pokemonEntity;
            final /* synthetic */ EmptyPokeBallEntity this$0;
            {
                this.$pokemonEntity = $pokemonEntity;
                this.this$0 = $receiver;
                super(0);
            }

            public final void invoke() {
                this.$pokemonEntity.setPhasingTargetId(-1);
                this.$pokemonEntity.setBeamMode(0);
                this.$pokemonEntity.m_6842_(true);
                this.this$0.setCaptureState(CaptureState.FALL);
                this.this$0.after(1.5f, (Function0<Unit>)((Function0)new Function0<Unit>(this.this$0){
                    final /* synthetic */ EmptyPokeBallEntity this$0;
                    {
                        this.this$0 = $receiver;
                        super(0);
                    }

                    public final void invoke() {
                        if (this.this$0.getCaptureState() == CaptureState.FALL) {
                            this.this$0.m_20256_(Vec3.f_82478_);
                            this.this$0.m_20242_(true);
                            this.this$0.m_6853_(true);
                            this.this$0.beginCapture();
                        }
                    }
                }));
            }
        }));
    }

    protected void m_6532_(@NotNull HitResult hitResult) {
        Intrinsics.checkNotNullParameter((Object)hitResult, (String)"hitResult");
        super.m_6532_(hitResult);
        if (this.getCaptureState() == CaptureState.FALL && hitResult.m_6662_() == HitResult.Type.BLOCK) {
            this.setCaptureState(CaptureState.SHAKE);
            Level level = this.m_9236_();
            Intrinsics.checkNotNullExpressionValue((Object)level, (String)"world");
            if (DistributionUtilsKt.isServerSide(level)) {
                this.beginCapture();
            }
        }
    }

    /*
     * WARNING - void declaration
     */
    public final void beginCapture() {
        void $this$iv;
        PokemonEntity pokemonEntity = this.capturingPokemon;
        if (pokemonEntity != null) {
            Entity entity2 = (Entity)pokemonEntity;
            Vec3 vec3 = this.m_20182_();
            Intrinsics.checkNotNullExpressionValue((Object)vec3, (String)"pos");
            EntityExtensionsKt.setPositionSafely(entity2, vec3);
        }
        Entity entity3 = this.m_19749_();
        Intrinsics.checkNotNull((Object)entity3, (String)"null cannot be cast to non-null type net.minecraft.entity.LivingEntity");
        LivingEntity thrower = (LivingEntity)entity3;
        CaptureCalculator captureCalculator = Cobblemon.INSTANCE.getConfig().getCaptureCalculator();
        PokemonEntity pokemonEntity2 = this.capturingPokemon;
        Intrinsics.checkNotNull((Object)pokemonEntity2);
        CaptureContext it = captureCalculator.processCapture(thrower, this, pokemonEntity2);
        boolean bl = false;
        PokemonEntity pokemonEntity3 = this.capturingPokemon;
        Intrinsics.checkNotNull((Object)pokemonEntity3);
        PokeBallCaptureCalculatedEvent event = new PokeBallCaptureCalculatedEvent(thrower, pokemonEntity3, this, it);
        EventObservable<PokeBallCaptureCalculatedEvent> eventObservable = CobblemonEvents.POKE_BALL_CAPTURE_CALCULATED;
        PokeBallCaptureCalculatedEvent[] pokeBallCaptureCalculatedEventArray = new PokeBallCaptureCalculatedEvent[]{event};
        PokeBallCaptureCalculatedEvent[] events$iv = pokeBallCaptureCalculatedEventArray;
        boolean $i$f$post = false;
        $this$iv.emit(Arrays.copyOf(events$iv, events$iv.length));
        PokeBallCaptureCalculatedEvent[] $this$forEach$iv$iv = events$iv;
        boolean $i$f$forEach = false;
        int n = $this$forEach$iv$iv.length;
        for (int i = 0; i < n; ++i) {
            PokeBallCaptureCalculatedEvent element$iv$iv;
            PokeBallCaptureCalculatedEvent pokeBallCaptureCalculatedEvent = element$iv$iv = $this$forEach$iv$iv[i];
            boolean bl2 = false;
            PokeBallCaptureCalculatedEvent it2 = pokeBallCaptureCalculatedEvent;
        }
        CaptureContext captureResult = event.getCaptureResult();
        Ref.IntRef rollsRemaining = new Ref.IntRef();
        rollsRemaining.element = captureResult.getNumberOfShakes();
        if (rollsRemaining.element == 4) {
            int n2 = rollsRemaining.element;
            rollsRemaining.element = n2 + -1;
        }
        this.taskBuilder().iterations(captureResult.getNumberOfShakes() + 1).delay(1.0f).interval(1.25f).execute((Function1<? super ScheduledTask, Unit>)((Function1)new Function1<ScheduledTask, Unit>(this, rollsRemaining, captureResult){
            final /* synthetic */ EmptyPokeBallEntity this$0;
            final /* synthetic */ Ref.IntRef $rollsRemaining;
            final /* synthetic */ CaptureContext $captureResult;
            {
                this.this$0 = $receiver;
                this.$rollsRemaining = $rollsRemaining;
                this.$captureResult = $captureResult;
                super(1);
            }

            public final void invoke(@NotNull ScheduledTask it) {
                Intrinsics.checkNotNullParameter((Object)it, (String)"it");
                EmptyPokeBallEntity.access$shakeBall(this.this$0, it, this.$rollsRemaining.element, this.$captureResult);
                int n = this.$rollsRemaining.element;
                this.$rollsRemaining.element = n + -1;
            }
        })).build();
    }

    @Override
    @NotNull
    public PoseType getCurrentPoseType() {
        return PoseType.NONE;
    }

    public boolean m_6072_() {
        return false;
    }

    @NotNull
    public Packet<ClientGamePacketListener> m_5654_() {
        Set<String> set2 = this.getAspects();
        Packet packet = super.m_5654_();
        Intrinsics.checkNotNull((Object)packet, (String)"null cannot be cast to non-null type net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket");
        return CobblemonNetwork.INSTANCE.asVanillaClientBound((NetworkPacket)new SpawnPokeballPacket(this.pokeBall, set2, (ClientboundAddEntityPacket)packet));
    }

    @Override
    public float waterDrag() {
        return this.pokeBall.getWaterDragValue();
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

    public static final /* synthetic */ void access$shakeBall(EmptyPokeBallEntity $this, ScheduledTask task, int rollsRemaining, CaptureContext captureResult) {
        $this.shakeBall(task, rollsRemaining, captureResult);
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\n\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0002\u0010\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\tj\u0002\b\n\u00a8\u0006\u000b"}, d2={"Lcom/cobblemon/mod/common/entity/pokeball/EmptyPokeBallEntity$CaptureState;", "", "<init>", "(Ljava/lang/String;I)V", "NOT", "HIT", "FALL", "SHAKE", "CAPTURED", "CAPTURED_CRITICAL", "BROKEN_FREE", "common"})
    public static final class CaptureState
    extends Enum<CaptureState> {
        public static final /* enum */ CaptureState NOT = new CaptureState();
        public static final /* enum */ CaptureState HIT = new CaptureState();
        public static final /* enum */ CaptureState FALL = new CaptureState();
        public static final /* enum */ CaptureState SHAKE = new CaptureState();
        public static final /* enum */ CaptureState CAPTURED = new CaptureState();
        public static final /* enum */ CaptureState CAPTURED_CRITICAL = new CaptureState();
        public static final /* enum */ CaptureState BROKEN_FREE = new CaptureState();
        private static final /* synthetic */ CaptureState[] $VALUES;

        public static CaptureState[] values() {
            return (CaptureState[])$VALUES.clone();
        }

        public static CaptureState valueOf(String value2) {
            return Enum.valueOf(CaptureState.class, value2);
        }

        static {
            $VALUES = captureStateArray = new CaptureState[]{CaptureState.NOT, CaptureState.HIT, CaptureState.FALL, CaptureState.SHAKE, CaptureState.CAPTURED, CaptureState.CAPTURED_CRITICAL, CaptureState.BROKEN_FREE};
        }
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000@\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\u0005\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u001e\u0010\u001fRS\u0010\u0006\u001a>\u0012\u0018\u0012\u0016\u0012\u0004\u0012\u00020\u0004 \u0005*\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u00030\u0003 \u0005*\u001e\u0012\u0018\u0012\u0016\u0012\u0004\u0012\u00020\u0004 \u0005*\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u00030\u0003\u0018\u00010\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0006\u0010\u0007\u001a\u0004\b\b\u0010\tR;\u0010\u000b\u001a&\u0012\f\u0012\n \u0005*\u0004\u0018\u00010\n0\n \u0005*\u0012\u0012\f\u0012\n \u0005*\u0004\u0018\u00010\n0\n\u0018\u00010\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u000b\u0010\u0007\u001a\u0004\b\f\u0010\tR\u0017\u0010\u000e\u001a\u00020\r8\u0006\u00a2\u0006\f\n\u0004\b\u000e\u0010\u000f\u001a\u0004\b\u0010\u0010\u0011R;\u0010\u0013\u001a&\u0012\f\u0012\n \u0005*\u0004\u0018\u00010\u00120\u0012 \u0005*\u0012\u0012\f\u0012\n \u0005*\u0004\u0018\u00010\u00120\u0012\u0018\u00010\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0013\u0010\u0007\u001a\u0004\b\u0014\u0010\tR;\u0010\u0015\u001a&\u0012\f\u0012\n \u0005*\u0004\u0018\u00010\u00120\u0012 \u0005*\u0012\u0012\f\u0012\n \u0005*\u0004\u0018\u00010\u00120\u0012\u0018\u00010\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0015\u0010\u0007\u001a\u0004\b\u0016\u0010\tR\u0014\u0010\u0018\u001a\u00020\u00178\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\u0018\u0010\u0019R\u0014\u0010\u001a\u001a\u00020\u00178\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\u001a\u0010\u0019R;\u0010\u001c\u001a&\u0012\f\u0012\n \u0005*\u0004\u0018\u00010\u001b0\u001b \u0005*\u0012\u0012\f\u0012\n \u0005*\u0004\u0018\u00010\u001b0\u001b\u0018\u00010\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u001c\u0010\u0007\u001a\u0004\b\u001d\u0010\t\u00a8\u0006 "}, d2={"Lcom/cobblemon/mod/common/entity/pokeball/EmptyPokeBallEntity$Companion;", "", "Lnet/minecraft/network/syncher/EntityDataAccessor;", "", "", "kotlin.jvm.PlatformType", "ASPECTS", "Lnet/minecraft/network/syncher/EntityDataAccessor;", "getASPECTS", "()Lnet/minecraft/network/syncher/EntityDataAccessor;", "", "CAPTURE_STATE", "getCAPTURE_STATE", "Lnet/minecraft/world/entity/EntityDimensions;", "DIMENSIONS", "Lnet/minecraft/world/entity/EntityDimensions;", "getDIMENSIONS", "()Lnet/minecraft/world/entity/EntityDimensions;", "Lnet/minecraft/world/phys/Vec3;", "HIT_TARGET_POSITION", "getHIT_TARGET_POSITION", "HIT_VELOCITY", "getHIT_VELOCITY", "", "SECONDS_BEFORE_SHAKE", "F", "SECONDS_BETWEEN_SHAKES", "", "SHAKE", "getSHAKE", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        public final EntityDataAccessor<Byte> getCAPTURE_STATE() {
            return CAPTURE_STATE;
        }

        public final EntityDataAccessor<Vec3> getHIT_TARGET_POSITION() {
            return HIT_TARGET_POSITION;
        }

        public final EntityDataAccessor<Vec3> getHIT_VELOCITY() {
            return HIT_VELOCITY;
        }

        public final EntityDataAccessor<Boolean> getSHAKE() {
            return SHAKE;
        }

        public final EntityDataAccessor<Set<String>> getASPECTS() {
            return ASPECTS;
        }

        @NotNull
        public final EntityDimensions getDIMENSIONS() {
            return DIMENSIONS;
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }

    @Metadata(mv={1, 8, 0}, k=3, xi=48)
    public final class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] nArray = new int[CaptureState.values().length];
            try {
                nArray[CaptureState.NOT.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                nArray[CaptureState.HIT.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                nArray[CaptureState.FALL.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                nArray[CaptureState.SHAKE.ordinal()] = 4;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                nArray[CaptureState.CAPTURED.ordinal()] = 5;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                nArray[CaptureState.CAPTURED_CRITICAL.ordinal()] = 6;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                nArray[CaptureState.BROKEN_FREE.ordinal()] = 7;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            $EnumSwitchMapping$0 = nArray;
        }
    }
}

