package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonSounds
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.drop.DropTable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.entity.PokemonSideDelegate
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.entity.pokemon.MocKEffect
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonProperties
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.stats.Stats
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.status.Statuses
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ActiveBattlePokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.BattleRegistry
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.entity.PokemonPastureBlockEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.PoseType
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.activestate.ActivePokemonState
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.activestate.PokemonState
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.activestate.SentOutState
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.ai.MoveBehaviour
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.status.PersistentStatusContainer
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.EntityExtensionsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.WorldExtensionsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.world.gamerules.CobblemonGameRules
import java.util.UUID
import java.util.concurrent.CompletableFuture
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.network.chat.Component
import net.minecraft.network.syncher.EntityDataAccessor
import net.minecraft.network.syncher.SynchedEntityData
import net.minecraft.server.level.ServerLevel
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.damagesource.DamageSource
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.Entity.RemovalReason
import net.minecraft.world.entity.ai.attributes.AttributeInstance
import net.minecraft.world.entity.ai.attributes.Attributes
import net.minecraft.world.level.Level
import net.minecraft.world.level.pathfinder.BlockPathTypes
import net.minecraft.world.phys.Vec3

@SourceDebugExtension(["SMAP\nPokemonServerDelegate.kt\nKotlin\n*S Kotlin\n*F\n+ 1 PokemonServerDelegate.kt\ncom/cobblemon/mod/common/entity/pokemon/PokemonServerDelegate\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,248:1\n1#2:249\n*E\n"])
public class PokemonServerDelegate : PokemonSideDelegate {
   public final var acknowledgedHPStat: Int = -1
   public final lateinit var entity: PokemonEntity

   private final val mock: PokemonProperties?
      private final get() {
         val var10000: MocKEffect = this.getEntity().getEffects().getMockEffect();
         return if (var10000 != null) var10000.getMock() else null;
      }


   public override fun changePokemon(pokemon: Pokemon) {
      this.updatePathfindingPenalties(pokemon);
      this.getEntity().m_8099_();
      this.updateMaxHealth();
   }

   public fun updatePathfindingPenalties(pokemon: Pokemon) {
      val moving: MoveBehaviour = pokemon.getForm().getBehaviour().getMoving();
      this.getEntity().m_21441_(BlockPathTypes.LAVA, if (moving.getSwim().getCanSwimInLava()) 12.0F else -1.0F);
      this.getEntity().m_21441_(BlockPathTypes.WATER, if (moving.getSwim().getCanSwimInWater()) 12.0F else -1.0F);
      this.getEntity().m_21441_(BlockPathTypes.WATER_BORDER, if (moving.getSwim().getCanSwimInWater()) 6.0F else -1.0F);
      if (moving.getSwim().getCanBreatheUnderwater()) {
         this.getEntity().m_21441_(BlockPathTypes.WATER, if (moving.getWalk().getAvoidsLand()) 0.0F else 4.0F);
      }

      if (moving.getSwim().getCanBreatheUnderlava()) {
         this.getEntity().m_21441_(BlockPathTypes.LAVA, if (moving.getSwim().getCanSwimInLava()) 4.0F else -1.0F);
      }

      if (moving.getWalk().getAvoidsLand()) {
         this.getEntity().m_21441_(BlockPathTypes.WALKABLE, 12.0F);
      }

      if (moving.getWalk().getCanWalk() && moving.getFly().getCanFly()) {
         this.getEntity().m_21441_(BlockPathTypes.WALKABLE, 0.0F);
      }

      this.getEntity().getNavigation().setCanPathThroughFire(this.getEntity().m_5825_());
   }

   public fun updateMaxHealth() {
      val currentHealthRatio: Double = (double)this.getEntity().m_21223_() / this.getEntity().m_21233_();
      val var10001: Int = this.getEntity().getForm().getBaseStats().get(Stats.HP);
      if (var10001 != null) {
         this.acknowledgedHPStat = var10001;
         val maxHealth: Double = 10.0
            + (double)(RangesKt.coerceIn(this.acknowledgedHPStat, (new IntRange(50, 150)) as ClosedRange) - 50) / (150 - 50) * (100.0 - 10.0);
         val var10000: AttributeInstance = this.getEntity().m_21051_(Attributes.f_22276_);
         if (var10000 != null) {
            var10000.m_22100_(maxHealth);
         }

         this.getEntity().m_21153_((float)currentHealthRatio * (float)maxHealth);
      }
   }

   public open fun initialize(entity: PokemonEntity) {
      this.setEntity(entity);
      entity.f_146794_ = 0.1F;
      entity.getDespawner().beginTracking(entity as Entity);
      this.updateTrackedValues();
   }

   public fun getBattle(): PokemonBattle? {
      val var10000: UUID = this.getEntity().getBattleId();
      return if (var10000 != null) BattleRegistry.INSTANCE.getBattle(var10000) else null;
   }

   public fun updateTrackedValues() {
      var var4: java.lang.String;
      label43: {
         val var10000: PokemonProperties = this.getMock();
         if (var10000 != null) {
            var4 = var10000.getSpecies();
            if (var4 != null) {
               break label43;
            }
         }

         var4 = this.getEntity().getPokemon().getSpecies().getResourceIdentifier().toString();
      }

      label38: {
         val var5: PokemonProperties = this.getMock();
         if (var5 != null) {
            var6 = var5.getNickname();
            if (var6 != null) {
               break label38;
            }
         }

         var6 = this.getEntity().getPokemon().getNickname();
         if (var6 == null) {
            var6 = Component.m_237119_();
         }
      }

      label33: {
         val var7: PokemonProperties = this.getMock();
         if (var7 != null) {
            var8 = var7.getAspects();
            if (var8 != null) {
               break label33;
            }
         }

         var8 = this.getEntity().getPokemon().getAspects();
      }

      this.getEntity().m_20088_().m_135381_(PokemonEntity.Companion.getSPECIES(), var4);
      if (!(this.getEntity().m_20088_().m_135370_(PokemonEntity.Companion.getNICKNAME()) == var6)) {
         this.getEntity().m_20088_().m_135381_(PokemonEntity.Companion.getNICKNAME(), var6);
      }

      this.getEntity().m_20088_().m_135381_(PokemonEntity.Companion.getASPECTS(), var8);
      this.getEntity().m_20088_().m_135381_(PokemonEntity.Companion.getLABEL_LEVEL(), this.getEntity().getPokemon().getLevel());
      this.getEntity()
         .m_20088_()
         .m_135381_(
            PokemonEntity.Companion.getMOVING(),
            this.getEntity().m_20184_().m_82542_(1.0, if (this.getEntity().m_20096_()) 0.0 else 1.0, 1.0).m_82553_() > 0.005F
         );
      this.getEntity().m_20088_().m_135381_(PokemonEntity.Companion.getFRIENDSHIP(), this.getEntity().getPokemon().getFriendship());
      this.updatePoseType();
   }

   public override fun onTrackedDataSet(data: EntityDataAccessor<*>) {
      PokemonSideDelegate.DefaultImpls.onTrackedDataSet(this, data);
      if (this.entity != null && data == PokemonEntity.Companion.getBEHAVIOUR_FLAGS()) {
         this.updatePoseType();
      }
   }

   public open fun tick(entity: PokemonEntity) {
      val state: PokemonState = entity.getPokemon().getState();
      if ((state !is ActivePokemonState || !((state as ActivePokemonState).getEntity() == entity)) && !entity.m_21224_() && entity.m_21223_() > 0.0F) {
         entity.getPokemon().setState(new SentOutState(entity));
      }

      if (entity.m_21805_() != null && entity.getPokemon().getStoreCoordinates().get() == null) {
         entity.m_146870_();
      }

      val tethering: PokemonPastureBlockEntity.Tethering = entity.getTethering();
      if (tethering != null && !(entity.getPokemon().getTetheringId() == tethering.getTetheringId())) {
         entity.m_146870_();
      }

      var var10000: SynchedEntityData = entity.m_20088_();
      val var10001: EntityDataAccessor = PokemonEntity.Companion.getBATTLE_ID();
      EntityExtensionsKt.update(var10000, var10001, <unrepresentable>.INSTANCE);
      val battle: PokemonBattle = this.getBattle();
      if (entity.getTicksLived() % 20 == 0 && battle != null) {
         label69: {
            for (Object var8 : battle.getActivePokemon()) {
               val var12: BattlePokemon = (var8 as ActiveBattlePokemon).getBattlePokemon();
               if ((if (var12 != null) var12.getUuid() else null) == entity.getPokemon().getUuid()) {
                  var10000 = (SynchedEntityData)var8;
                  break label69;
               }
            }

            var10000 = null;
         }

         val activeBattlePokemon: ActiveBattlePokemon = var10000 as ActiveBattlePokemon;
         if (var10000 as ActiveBattlePokemon != null) {
            val var15: Level = entity.m_9236_();
            activeBattlePokemon.setPosition(TuplesKt.to(var15 as ServerLevel, entity.m_20182_()));
         }
      }

      label60: {
         val var14: Int = entity.getForm().getBaseStats().get(Stats.HP);
         val var11: Int = this.acknowledgedHPStat;
         if (var14 != null) {
            if (var14 == var11) {
               break label60;
            }
         }

         this.updateMaxHealth();
      }

      if (!(entity.m_21805_() == entity.getPokemon().getOwnerUUID())) {
         entity.m_21816_(entity.getPokemon().getOwnerUUID());
      }

      if (entity.m_21805_() == null && tethering != null) {
         entity.m_21816_(tethering.getPlayerId());
      }

      if (entity.m_21805_() != null && entity.m_269323_() == null && entity.getTethering() == null) {
         entity.m_142687_(RemovalReason.DISCARDED);
      }

      this.updateTrackedValues();
   }

   public fun updatePoseType() {
      val var10000: PersistentStatusContainer = this.getEntity().getPokemon().getStatus();
      val isSleeping: Boolean = (if (var10000 != null) var10000.getStatus() else null) == Statuses.INSTANCE.getSLEEP()
         && this.getEntity().getBehaviour().getResting().getCanSleep();
      val isMoving: java.lang.Boolean = this.getEntity().m_20088_().m_135370_(PokemonEntity.Companion.getMOVING()) as java.lang.Boolean;
      val isPassenger: Boolean = this.getEntity().m_20159_();
      val isUnderwater: Boolean = this.getEntity().getIsSubmerged();
      val isFlying: Boolean = this.getEntity().getBehaviourFlag(PokemonBehaviourFlag.FLYING);
      val var7: PoseType;
      if (isPassenger) {
         var7 = PoseType.STAND;
      } else if (isSleeping) {
         var7 = PoseType.SLEEP;
      } else {
         var7 = if (isMoving && isUnderwater)
            PoseType.SWIM
            else
            (
               if (isUnderwater)
                  PoseType.FLOAT
                  else
                  (if (isMoving && isFlying) PoseType.FLY else (if (isFlying) PoseType.HOVER else (if (isMoving) PoseType.WALK else PoseType.STAND)))
            );
      }

      this.getEntity().m_20088_().m_135381_(PokemonEntity.Companion.getPOSE_TYPE(), var7);
   }

   public override fun drop(source: DamageSource?) {
      val var3: Entity = if (source != null) source.m_7640_() else null;
      val player: ServerPlayer = var3 as? ServerPlayer;
      if (this.getEntity().getPokemon().isWild()) {
         this.getEntity().setKiller(player);
      }
   }

   public override fun updatePostDeath() {
      if (this.getEntity().f_20919_ == 0) {
         this.getEntity().getEffects().wipe();
         this.getEntity().f_20919_ = 1;
      } else {
         val var10000: CompletableFuture = this.getEntity().getEffects().getProgress();
         if (var10000 == null || var10000.isDone()) {
            this.getEntity().m_20088_().m_135381_(PokemonEntity.Companion.getDYING_EFFECTS_STARTED(), true);
            this.getEntity().f_20919_++;
            if (this.getEntity().f_20919_ == 30) {
               val var2: LivingEntity = this.getEntity().m_269323_();
               if (var2 != null) {
                  val var3: Level = this.getEntity().m_9236_();
                  val var10001: Vec3 = var2.m_20182_();
                  WorldExtensionsKt.playSoundServer$default(var3, var10001, CobblemonSounds.POKE_BALL_RECALL, null, 0.6F, 0.0F, 20, null);
                  this.getEntity().m_20088_().m_135381_(PokemonEntity.Companion.getPHASING_TARGET_ID(), var2.m_19879_());
                  this.getEntity().m_20088_().m_135381_(PokemonEntity.Companion.getBEAM_MODE(), (byte)3);
               }
            }

            if (this.getEntity().f_20919_ == 60) {
               if (this.getEntity().m_269323_() == null) {
                  this.getEntity().m_9236_().m_7605_(this.getEntity() as Entity, (byte)60);
                  if (this.getEntity().m_9236_().m_46469_().m_46207_(CobblemonGameRules.DO_POKEMON_LOOT)) {
                     var var4: DropTable = this.getEntity().getDrops();
                     if (var4 == null) {
                        var4 = this.getEntity().getPokemon().getForm().getDrops();
                     }

                     val var5: LivingEntity = this.getEntity() as LivingEntity;
                     val var10002: Level = this.getEntity().m_9236_();
                     val var6: ServerLevel = var10002 as ServerLevel;
                     val var10003: Vec3 = this.getEntity().m_20182_();
                     DropTable.drop$default(var4, var5, var6, var10003, this.getEntity().getKiller(), null, 16, null);
                  }
               }

               this.getEntity().m_142687_(RemovalReason.KILLED);
            }
         }
      }
   }

   override fun handleStatus(status: Byte) {
      PokemonSideDelegate.DefaultImpls.handleStatus(this, status);
   }
}
