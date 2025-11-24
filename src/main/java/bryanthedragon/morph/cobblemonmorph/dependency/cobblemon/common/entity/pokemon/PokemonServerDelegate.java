/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Pair
 *  kotlin.TuplesKt
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  kotlin.ranges.ClosedRange
 *  kotlin.ranges.IntRange
 *  kotlin.ranges.RangesKt
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.syncher.EntityDataAccessor
 *  net.minecraft.network.syncher.SynchedEntityData
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.Entity$RemovalReason
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.ai.attributes.AttributeInstance
 *  net.minecraft.world.entity.ai.attributes.Attributes
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.pathfinder.BlockPathTypes
 *  net.minecraft.world.phys.Vec3
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonSounds;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.drop.DropTable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.entity.PokemonSideDelegate;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.entity.pokemon.MocKEffect;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonProperties;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.stats.Stats;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.status.Statuses;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ActiveBattlePokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.BattleRegistry;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.entity.PokemonPastureBlockEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.PoseType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonBehaviourFlag;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonServerDelegate;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.activestate.ActivePokemonState;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.activestate.PokemonState;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.activestate.SentOutState;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.ai.MoveBehaviour;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.status.PersistentStatusContainer;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.EntityExtensionsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.WorldExtensionsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.world.gamerules.CobblemonGameRules;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.ranges.ClosedRange;
import kotlin.ranges.IntRange;
import kotlin.ranges.RangesKt;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\b\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u0007\u00a2\u0006\u0004\b,\u0010\u0018J\u0017\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0005\u0010\u0006J\u0019\u0010\t\u001a\u00020\u00042\b\u0010\b\u001a\u0004\u0018\u00010\u0007H\u0016\u00a2\u0006\u0004\b\t\u0010\nJ\u000f\u0010\f\u001a\u0004\u0018\u00010\u000b\u00a2\u0006\u0004\b\f\u0010\rJ\u0017\u0010\u0010\u001a\u00020\u00042\u0006\u0010\u000f\u001a\u00020\u000eH\u0016\u00a2\u0006\u0004\b\u0010\u0010\u0011J\u001b\u0010\u0014\u001a\u00020\u00042\n\u0010\u0013\u001a\u0006\u0012\u0002\b\u00030\u0012H\u0016\u00a2\u0006\u0004\b\u0014\u0010\u0015J\u0017\u0010\u0016\u001a\u00020\u00042\u0006\u0010\u000f\u001a\u00020\u000eH\u0016\u00a2\u0006\u0004\b\u0016\u0010\u0011J\r\u0010\u0017\u001a\u00020\u0004\u00a2\u0006\u0004\b\u0017\u0010\u0018J\u0015\u0010\u0019\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0019\u0010\u0006J\r\u0010\u001a\u001a\u00020\u0004\u00a2\u0006\u0004\b\u001a\u0010\u0018J\u000f\u0010\u001b\u001a\u00020\u0004H\u0016\u00a2\u0006\u0004\b\u001b\u0010\u0018J\r\u0010\u001c\u001a\u00020\u0004\u00a2\u0006\u0004\b\u001c\u0010\u0018R\"\u0010\u001e\u001a\u00020\u001d8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u001e\u0010\u001f\u001a\u0004\b \u0010!\"\u0004\b\"\u0010#R\"\u0010\u000f\u001a\u00020\u000e8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\b\u000f\u0010$\u001a\u0004\b%\u0010&\"\u0004\b'\u0010\u0011R\u0016\u0010+\u001a\u0004\u0018\u00010(8BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b)\u0010*\u00a8\u0006-"}, d2={"Lcom/cobblemon/mod/common/entity/pokemon/PokemonServerDelegate;", "Lcom/cobblemon/mod/common/api/entity/PokemonSideDelegate;", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "pokemon", "", "changePokemon", "(Lcom/cobblemon/mod/common/pokemon/Pokemon;)V", "Lnet/minecraft/world/damagesource/DamageSource;", "source", "drop", "(Lnet/minecraft/world/damagesource/DamageSource;)V", "Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;", "getBattle", "()Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;", "Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;", "entity", "initialize", "(Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;)V", "Lnet/minecraft/network/syncher/EntityDataAccessor;", "data", "onTrackedDataSet", "(Lnet/minecraft/network/syncher/EntityDataAccessor;)V", "tick", "updateMaxHealth", "()V", "updatePathfindingPenalties", "updatePoseType", "updatePostDeath", "updateTrackedValues", "", "acknowledgedHPStat", "I", "getAcknowledgedHPStat", "()I", "setAcknowledgedHPStat", "(I)V", "Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;", "getEntity", "()Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;", "setEntity", "Lcom/cobblemon/mod/common/api/pokemon/PokemonProperties;", "getMock", "()Lcom/cobblemon/mod/common/api/pokemon/PokemonProperties;", "mock", "<init>", "common"})
@SourceDebugExtension(value={"SMAP\nPokemonServerDelegate.kt\nKotlin\n*S Kotlin\n*F\n+ 1 PokemonServerDelegate.kt\ncom/cobblemon/mod/common/entity/pokemon/PokemonServerDelegate\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,248:1\n1#2:249\n*E\n"})
public final class PokemonServerDelegate
implements PokemonSideDelegate {
    public PokemonEntity entity;
    private int acknowledgedHPStat = -1;

    @NotNull
    public final PokemonEntity getEntity() {
        PokemonEntity pokemonEntity = this.entity;
        if (pokemonEntity != null) {
            return pokemonEntity;
        }
        Intrinsics.throwUninitializedPropertyAccessException((String)"entity");
        return null;
    }

    public final void setEntity(@NotNull PokemonEntity pokemonEntity) {
        Intrinsics.checkNotNullParameter((Object)pokemonEntity, (String)"<set-?>");
        this.entity = pokemonEntity;
    }

    public final int getAcknowledgedHPStat() {
        return this.acknowledgedHPStat;
    }

    public final void setAcknowledgedHPStat(int n) {
        this.acknowledgedHPStat = n;
    }

    private final PokemonProperties getMock() {
        MocKEffect mocKEffect = this.getEntity().getEffects().getMockEffect();
        return mocKEffect != null ? mocKEffect.getMock() : null;
    }

    @Override
    public void changePokemon(@NotNull Pokemon pokemon) {
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        this.updatePathfindingPenalties(pokemon);
        this.getEntity().m_8099_();
        this.updateMaxHealth();
    }

    public final void updatePathfindingPenalties(@NotNull Pokemon pokemon) {
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        MoveBehaviour moving2 = pokemon.getForm().getBehaviour().getMoving();
        this.getEntity().m_21441_(BlockPathTypes.LAVA, moving2.getSwim().getCanSwimInLava() ? 12.0f : -1.0f);
        this.getEntity().m_21441_(BlockPathTypes.WATER, moving2.getSwim().getCanSwimInWater() ? 12.0f : -1.0f);
        this.getEntity().m_21441_(BlockPathTypes.WATER_BORDER, moving2.getSwim().getCanSwimInWater() ? 6.0f : -1.0f);
        if (moving2.getSwim().getCanBreatheUnderwater()) {
            this.getEntity().m_21441_(BlockPathTypes.WATER, moving2.getWalk().getAvoidsLand() ? 0.0f : 4.0f);
        }
        if (moving2.getSwim().getCanBreatheUnderlava()) {
            this.getEntity().m_21441_(BlockPathTypes.LAVA, moving2.getSwim().getCanSwimInLava() ? 4.0f : -1.0f);
        }
        if (moving2.getWalk().getAvoidsLand()) {
            this.getEntity().m_21441_(BlockPathTypes.WALKABLE, 12.0f);
        }
        if (moving2.getWalk().getCanWalk() && moving2.getFly().getCanFly()) {
            this.getEntity().m_21441_(BlockPathTypes.WALKABLE, 0.0f);
        }
        this.getEntity().getNavigation().setCanPathThroughFire(this.getEntity().m_5825_());
    }

    public final void updateMaxHealth() {
        double currentHealthRatio = (double)this.getEntity().m_21223_() / (double)this.getEntity().m_21233_();
        Integer n = this.getEntity().getForm().getBaseStats().get(Stats.HP);
        if (n == null) {
            return;
        }
        this.acknowledgedHPStat = n;
        int minStat = 50;
        int maxStat = 150;
        int baseStat = RangesKt.coerceIn((int)this.acknowledgedHPStat, (ClosedRange)((ClosedRange)new IntRange(minStat, maxStat)));
        double r = (double)(baseStat - minStat) / (double)(maxStat - minStat);
        double minPossibleHP = 10.0;
        double maxPossibleHP = 100.0;
        double maxHealth = minPossibleHP + r * (maxPossibleHP - minPossibleHP);
        AttributeInstance attributeInstance = this.getEntity().m_21051_(Attributes.f_22276_);
        if (attributeInstance != null) {
            attributeInstance.m_22100_(maxHealth);
        }
        this.getEntity().m_21153_((float)currentHealthRatio * (float)maxHealth);
    }

    @Override
    public void initialize(@NotNull PokemonEntity entity2) {
        Intrinsics.checkNotNullParameter((Object)entity2, (String)"entity");
        this.setEntity(entity2);
        PokemonEntity $this$initialize_u24lambda_u240 = entity2;
        boolean bl = false;
        $this$initialize_u24lambda_u240.f_146794_ = 0.1f;
        entity2.getDespawner().beginTracking((PokemonEntity)((Entity)$this$initialize_u24lambda_u240));
        this.updateTrackedValues();
    }

    @Nullable
    public final PokemonBattle getBattle() {
        PokemonBattle pokemonBattle;
        UUID uUID = this.getEntity().getBattleId();
        if (uUID != null) {
            UUID uUID2 = uUID;
            BattleRegistry battleRegistry = BattleRegistry.INSTANCE;
            UUID p0 = uUID2;
            boolean bl = false;
            pokemonBattle = battleRegistry.getBattle(p0);
        } else {
            pokemonBattle = null;
        }
        return pokemonBattle;
    }

    public final void updateTrackedValues() {
        Set<String> set2;
        PokemonProperties trackedNickname;
        Object object = this.getMock();
        if (object == null || (object = ((PokemonProperties)object).getSpecies()) == null) {
            String string = this.getEntity().getPokemon().getSpecies().getResourceIdentifier().toString();
            object = string;
            Intrinsics.checkNotNullExpressionValue((Object)string, (String)"entity.pokemon.species.r\u2026urceIdentifier.toString()");
        }
        Object trackedSpecies = object;
        PokemonProperties pokemonProperties = this.getMock();
        if ((pokemonProperties == null || (pokemonProperties = pokemonProperties.getNickname()) == null) && (pokemonProperties = this.getEntity().getPokemon().getNickname()) == null) {
            pokemonProperties = trackedNickname = Component.m_237119_();
        }
        if ((set2 = this.getMock()) == null || (set2 = ((PokemonProperties)((Object)set2)).getAspects()) == null) {
            set2 = this.getEntity().getPokemon().getAspects();
        }
        Set<String> trackedAspects = set2;
        this.getEntity().m_20088_().m_135381_(PokemonEntity.Companion.getSPECIES(), trackedSpecies);
        if (!Intrinsics.areEqual((Object)this.getEntity().m_20088_().m_135370_(PokemonEntity.Companion.getNICKNAME()), (Object)trackedNickname)) {
            this.getEntity().m_20088_().m_135381_(PokemonEntity.Companion.getNICKNAME(), (Object)trackedNickname);
        }
        this.getEntity().m_20088_().m_135381_(PokemonEntity.Companion.getASPECTS(), trackedAspects);
        this.getEntity().m_20088_().m_135381_(PokemonEntity.Companion.getLABEL_LEVEL(), (Object)this.getEntity().getPokemon().getLevel());
        this.getEntity().m_20088_().m_135381_(PokemonEntity.Companion.getMOVING(), (Object)(this.getEntity().m_20184_().m_82542_(1.0, this.getEntity().m_20096_() ? 0.0 : 1.0, 1.0).m_82553_() > (double)0.005f ? 1 : 0));
        this.getEntity().m_20088_().m_135381_(PokemonEntity.Companion.getFRIENDSHIP(), (Object)this.getEntity().getPokemon().getFriendship());
        this.updatePoseType();
    }

    @Override
    public void onTrackedDataSet(@NotNull EntityDataAccessor<?> data) {
        Intrinsics.checkNotNullParameter(data, (String)"data");
        PokemonSideDelegate.DefaultImpls.onTrackedDataSet(this, data);
        if (this.entity != null && Intrinsics.areEqual(data, PokemonEntity.Companion.getBEHAVIOUR_FLAGS())) {
            this.updatePoseType();
        }
    }

    @Override
    public void tick(@NotNull PokemonEntity entity2) {
        PokemonPastureBlockEntity.Tethering tethering;
        Intrinsics.checkNotNullParameter((Object)entity2, (String)"entity");
        PokemonState state = entity2.getPokemon().getState();
        if (!(state instanceof ActivePokemonState && Intrinsics.areEqual((Object)((ActivePokemonState)state).getEntity(), (Object)entity2) || entity2.m_21224_() || !(entity2.m_21223_() > 0.0f))) {
            entity2.getPokemon().setState(new SentOutState(entity2));
        }
        if (entity2.m_21805_() != null && entity2.getPokemon().getStoreCoordinates().get() == null) {
            entity2.m_146870_();
        }
        if ((tethering = entity2.getTethering()) != null && !Intrinsics.areEqual((Object)entity2.getPokemon().getTetheringId(), (Object)tethering.getTetheringId())) {
            entity2.m_146870_();
        }
        SynchedEntityData synchedEntityData = entity2.m_20088_();
        Intrinsics.checkNotNullExpressionValue((Object)synchedEntityData, (String)"entity.dataTracker");
        EntityDataAccessor<Optional<UUID>> entityDataAccessor = PokemonEntity.Companion.getBATTLE_ID();
        Intrinsics.checkNotNullExpressionValue(entityDataAccessor, (String)"PokemonEntity.BATTLE_ID");
        EntityExtensionsKt.update(synchedEntityData, entityDataAccessor, tick.1.INSTANCE);
        PokemonBattle battle2 = this.getBattle();
        if (entity2.getTicksLived() % 20 == 0 && battle2 != null) {
            ActiveBattlePokemon activeBattlePokemon;
            block10: {
                Iterable<ActiveBattlePokemon> iterable = battle2.getActivePokemon();
                Iterator<ActiveBattlePokemon> iterator = iterable.iterator();
                while (iterator.hasNext()) {
                    ActiveBattlePokemon activeBattlePokemon2;
                    ActiveBattlePokemon it = activeBattlePokemon2 = iterator.next();
                    boolean bl = false;
                    BattlePokemon battlePokemon = it.getBattlePokemon();
                    if (!Intrinsics.areEqual((Object)(battlePokemon != null ? battlePokemon.getUuid() : null), (Object)entity2.getPokemon().getUuid())) continue;
                    activeBattlePokemon = activeBattlePokemon2;
                    break block10;
                }
                activeBattlePokemon = null;
            }
            ActiveBattlePokemon activeBattlePokemon3 = activeBattlePokemon;
            if (activeBattlePokemon3 != null) {
                Level level = entity2.m_9236_();
                Intrinsics.checkNotNull((Object)level, (String)"null cannot be cast to non-null type net.minecraft.server.world.ServerWorld");
                activeBattlePokemon3.setPosition((Pair<? extends ServerLevel, ? extends Vec3>)TuplesKt.to((Object)((ServerLevel)level), (Object)entity2.m_20182_()));
            }
        }
        Integer n = entity2.getForm().getBaseStats().get(Stats.HP);
        int n2 = this.acknowledgedHPStat;
        if (n == null || n != n2) {
            this.updateMaxHealth();
        }
        if (!Intrinsics.areEqual((Object)entity2.m_21805_(), (Object)entity2.getPokemon().getOwnerUUID())) {
            entity2.m_21816_(entity2.getPokemon().getOwnerUUID());
        }
        if (entity2.m_21805_() == null && tethering != null) {
            entity2.m_21816_(tethering.getPlayerId());
        }
        if (entity2.m_21805_() != null && entity2.m_269323_() == null && entity2.getTethering() == null) {
            entity2.m_142687_(Entity.RemovalReason.DISCARDED);
        }
        this.updateTrackedValues();
    }

    public final void updatePoseType() {
        PoseType poseType;
        PersistentStatusContainer persistentStatusContainer = this.getEntity().getPokemon().getStatus();
        boolean isSleeping = Intrinsics.areEqual((Object)(persistentStatusContainer != null ? persistentStatusContainer.getStatus() : null), (Object)Statuses.INSTANCE.getSLEEP()) && this.getEntity().getBehaviour().getResting().getCanSleep();
        Boolean isMoving = (Boolean)this.getEntity().m_20088_().m_135370_(PokemonEntity.Companion.getMOVING());
        boolean isPassenger = this.getEntity().m_20159_();
        boolean isUnderwater = this.getEntity().getIsSubmerged();
        boolean isFlying = this.getEntity().getBehaviourFlag(PokemonBehaviourFlag.FLYING);
        if (isPassenger) {
            poseType = PoseType.STAND;
        } else if (isSleeping) {
            poseType = PoseType.SLEEP;
        } else {
            Intrinsics.checkNotNullExpressionValue((Object)isMoving, (String)"isMoving");
            poseType = isMoving != false && isUnderwater ? PoseType.SWIM : (isUnderwater ? PoseType.FLOAT : (isMoving != false && isFlying ? PoseType.FLY : (isFlying ? PoseType.HOVER : (isMoving != false ? PoseType.WALK : PoseType.STAND))));
        }
        PoseType poseType2 = poseType;
        this.getEntity().m_20088_().m_135381_(PokemonEntity.Companion.getPOSE_TYPE(), (Object)poseType2);
    }

    @Override
    public void drop(@Nullable DamageSource source) {
        ServerPlayer player;
        DamageSource damageSource = source;
        Entity entity2 = damageSource != null ? damageSource.m_7640_() : null;
        ServerPlayer serverPlayer = player = entity2 instanceof ServerPlayer ? (ServerPlayer)entity2 : null;
        if (this.getEntity().getPokemon().isWild()) {
            this.getEntity().setKiller(player);
        }
    }

    @Override
    public void updatePostDeath() {
        LivingEntity owner;
        if (this.getEntity().f_20919_ == 0) {
            this.getEntity().getEffects().wipe();
            this.getEntity().f_20919_ = 1;
            return;
        }
        CompletableFuture<PokemonEntity> completableFuture = this.getEntity().getEffects().getProgress();
        boolean bl = completableFuture != null ? !completableFuture.isDone() : false;
        if (bl) {
            return;
        }
        this.getEntity().m_20088_().m_135381_(PokemonEntity.Companion.getDYING_EFFECTS_STARTED(), (Object)true);
        PokemonEntity pokemonEntity = this.getEntity();
        ++pokemonEntity.f_20919_;
        int cfr_ignored_0 = pokemonEntity.f_20919_;
        if (this.getEntity().f_20919_ == 30 && (owner = this.getEntity().m_269323_()) != null) {
            Level level = this.getEntity().m_9236_();
            Intrinsics.checkNotNullExpressionValue((Object)level, (String)"entity.world");
            Vec3 vec3 = owner.m_20182_();
            Intrinsics.checkNotNullExpressionValue((Object)vec3, (String)"owner.pos");
            WorldExtensionsKt.playSoundServer$default(level, vec3, CobblemonSounds.POKE_BALL_RECALL, null, 0.6f, 0.0f, 20, null);
            this.getEntity().m_20088_().m_135381_(PokemonEntity.Companion.getPHASING_TARGET_ID(), (Object)owner.m_19879_());
            this.getEntity().m_20088_().m_135381_(PokemonEntity.Companion.getBEAM_MODE(), (Object)3);
        }
        if (this.getEntity().f_20919_ == 60) {
            if (this.getEntity().m_269323_() == null) {
                this.getEntity().m_9236_().m_7605_((Entity)this.getEntity(), (byte)60);
                if (this.getEntity().m_9236_().m_46469_().m_46207_(CobblemonGameRules.DO_POKEMON_LOOT)) {
                    DropTable dropTable = this.getEntity().getDrops();
                    if (dropTable == null) {
                        dropTable = this.getEntity().getPokemon().getForm().getDrops();
                    }
                    LivingEntity livingEntity = (LivingEntity)this.getEntity();
                    Level level = this.getEntity().m_9236_();
                    Intrinsics.checkNotNull((Object)level, (String)"null cannot be cast to non-null type net.minecraft.server.world.ServerWorld");
                    ServerLevel serverLevel = (ServerLevel)level;
                    Vec3 vec3 = this.getEntity().m_20182_();
                    Intrinsics.checkNotNullExpressionValue((Object)vec3, (String)"entity.pos");
                    DropTable.drop$default(dropTable, livingEntity, serverLevel, vec3, this.getEntity().getKiller(), null, 16, null);
                }
            }
            this.getEntity().m_142687_(Entity.RemovalReason.KILLED);
        }
    }

    @Override
    public void handleStatus(byte status) {
        PokemonSideDelegate.DefaultImpls.handleStatus(this, status);
    }
}

