@file:SourceDebugExtension(["SMAP\nPlayerExtensions.kt\nKotlin\n*S Kotlin\n*F\n+ 1 PlayerExtensions.kt\ncom/cobblemon/mod/common/util/PlayerExtensionsKt\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,370:1\n1#2:371\n2333#3,14:372\n766#3:386\n857#3,2:387\n*S KotlinDebug\n*F\n+ 1 PlayerExtensions.kt\ncom/cobblemon/mod/common/util/PlayerExtensionsKt\n*L\n124#1:372,14\n148#1:386\n148#1:387,2\n*E\n"])

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonSounds
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.actor.BattleActor
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.ActiveDialogue
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.Dialogue
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.DialogueManager
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.Observable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.Transform
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.PokemonStoreManager
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.party.PlayerPartyStore
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.pc.PCStore
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.player.PlayerDataExtension
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.BattleRegistry
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.platform.events.PlatformEvents
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.platform.events.ServerPlayerEvent
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import java.util.ArrayList;
import java.util.UUID
import kotlin.jvm.functions.Function1
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.core.NonNullList
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.MinecraftServer
import net.minecraft.server.level.ServerPlayer
import net.minecraft.server.players.PlayerList
import net.minecraft.sounds.SoundEvent
import net.minecraft.sounds.SoundEvents
import net.minecraft.sounds.SoundSource
import net.minecraft.util.Mth
import net.minecraft.world.RaycastContext.FluidHandling
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.item.ItemEntity
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.ClipContext
import net.minecraft.world.level.Level
import net.minecraft.world.level.ClipContext.Block
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.AABB
import net.minecraft.world.phys.BlockHitResult
import net.minecraft.world.phys.Vec3
import org.jetbrains.annotations.NotNull

public final val activeDialogue: ActiveDialogue?
   public final get() {
      return DialogueManager.INSTANCE.getActiveDialogues().get(`$this$activeDialogue`.m_20148_());
   }


public final val isInDialogue: Boolean
   public final get() {
      return DialogueManager.INSTANCE.getActiveDialogues().containsKey(`$this$isInDialogue`.m_20148_());
   }


public fun ServerPlayer.party(): PlayerPartyStore {
   return Cobblemon.INSTANCE.getStorage().getParty(`$this$party`);
}

public fun ServerPlayer.pc(): PCStore {
   val var10000: PokemonStoreManager = Cobblemon.INSTANCE.getStorage();
   val var10001: UUID = `$this$pc`.m_20148_();
   return var10000.getPC(var10001);
}

public fun ServerPlayer.closeDialogue() {
   DialogueManager.INSTANCE.stopDialogue(`$this$closeDialogue`);
}

public fun ServerPlayer.openDialogue(dialogue: Dialogue) {
   DialogueManager.INSTANCE.startDialogue(`$this$openDialogue`, dialogue);
}

public fun ServerPlayer.extraData(key: String): PlayerDataExtension? {
   return Cobblemon.INSTANCE.getPlayerData().get(`$this$extraData` as Player).getExtraData().get(key);
}

public fun ServerPlayer.hasKeyItem(key: ResourceLocation): Boolean {
   return Cobblemon.INSTANCE.getPlayerData().get(`$this$hasKeyItem` as Player).getKeyItems().contains(key);
}

public fun UUID.getPlayer(): ServerPlayer? {
   val var10000: MinecraftServer = DistributionUtilsKt.server();
   if (var10000 != null) {
      val var1: PlayerList = var10000.m_6846_();
      if (var1 != null) {
         return var1.m_11259_(`$this$getPlayer`);
      }
   }

   return null;
}

public fun ServerPlayer.onLogout(handler: () -> Unit) {
   Observable.DefaultImpls.subscribe$default(
      PlatformEvents.SERVER_PLAYER_LOGOUT
         .pipe(
            Observable.Companion.filter((new Function1<ServerPlayerEvent.Logout, java.lang.Boolean>(`$this$onLogout`) {
               {
                  super(1);
                  this.$this_onLogout = `$receiver`;
               }

               @NotNull
               public final java.lang.Boolean invoke(@NotNull ServerPlayerEvent.Logout it) {
                  return it.getPlayer().m_20148_() == this.$this_onLogout.m_20148_();
               }
            }) as (ServerPlayerEvent.Logout?) -> java.lang.Boolean) as Transform<ServerPlayerEvent.Logout, ServerPlayerEvent.Logout>,
            Observable.Companion.takeFirst$default(Observable.Companion, 0, 1, null)
         ),
      null,
      (new Function1<ServerPlayerEvent.Logout, Unit>(handler) {
         {
            super(1);
            this.$handler = `$handler`;
         }

         public final void invoke(@NotNull ServerPlayerEvent.Logout it) {
            this.$handler.invoke();
         }
      }) as Function1,
      1,
      null
   );
}

public fun ServerPlayer.didSleep(): Boolean {
   if (`$this$didSleep`.m_36318_() == 100 && (int)`$this$didSleep`.m_9236_().m_46468_() % 24000 == 0 && !isInBattle(`$this$didSleep`)) {
      party(`$this$didSleep`).didSleep();
      return true;
   } else {
      return false;
   }
}

public fun ServerPlayer.isInBattle(): Boolean {
   return BattleRegistry.INSTANCE.getBattleByParticipatingPlayer(`$this$isInBattle`) != null;
}

public fun ServerPlayer.getBattleState(): Pair<PokemonBattle, BattleActor>? {
   val battle: PokemonBattle = BattleRegistry.INSTANCE.getBattleByParticipatingPlayer(`$this$getBattleState`);
   if (battle != null) {
      val actor: BattleActor = battle.getActor(`$this$getBattleState`);
      if (actor != null) {
         return TuplesKt.to(battle, actor);
      }
   }

   return null;
}

public fun Entity.isLookingAt(other: Entity, maxDistance: Float = 10.0F, stepDistance: Float = 0.01F): Boolean {
   var step: Float = stepDistance;
   val startPos: Vec3 = `$this$isLookingAt`.m_146892_();
   val direction: Vec3 = `$this$isLookingAt`.m_20154_();

   while (step <= maxDistance) {
      val location: Vec3 = startPos.m_82549_(direction.m_82490_((double)step));
      step += stepDistance;
      if (other.m_20191_().m_82390_(location)) {
         return true;
      }
   }

   return false;
}

@JvmSynthetic
fun `isLookingAt$default`(var0: Entity, var1: Entity, var2: Float, var3: Float, var4: Int, var5: Any): Boolean {
   if ((var4 and 2) != 0) {
      var2 = 10.0F;
   }

   if ((var4 and 4) != 0) {
      var3 = 0.01F;
   }

   return isLookingAt(var0, var1, var2, var3);
}

public fun <T : Entity> Player.traceFirstEntityCollision(maxDistance: Float = ..., stepDistance: Float = ..., entityClass: Class<Any>, ignoreEntity: Any? = ...): Any? {
   val var5: EntityTraceResult = traceEntityCollision(`$this$traceFirstEntityCollision`, maxDistance, stepDistance, entityClass, ignoreEntity);
   val var19: Entity;
   if (var5 != null) {
      val `iterator$iv`: java.util.Iterator = var5.getEntities().iterator();
      val var10000: Any;
      if (!`iterator$iv`.hasNext()) {
         var10000 = null;
      } else {
         var `minElem$iv`: Any = `iterator$iv`.next();
         if (!`iterator$iv`.hasNext()) {
            var10000 = `minElem$iv`;
         } else {
            var var16: Float = (`minElem$iv` as Entity).m_20270_(`$this$traceFirstEntityCollision` as Entity);

            do {
               val var17: Any = `iterator$iv`.next();
               val var18: Float = (var17 as Entity).m_20270_(`$this$traceFirstEntityCollision` as Entity);
               if (java.lang.Float.compare(var16, var18) > 0) {
                  `minElem$iv` = var17;
                  var16 = var18;
               }
            } while (iterator$iv.hasNext());

            var10000 = `minElem$iv`;
         }
      }

      var19 = var10000 as Entity;
   } else {
      var19 = null;
   }

   return (T)var19;
}

@JvmSynthetic
fun `traceFirstEntityCollision$default`(var0: Player, var1: Float, var2: Float, var3: Class, var4: Entity, var5: Int, var6: Any): Entity {
   if ((var5 and 1) != 0) {
      var1 = 10.0F;
   }

   if ((var5 and 2) != 0) {
      var2 = 0.05F;
   }

   if ((var5 and 8) != 0) {
      var4 = null;
   }

   return traceFirstEntityCollision(var0, var1, var2, var3, var4);
}

public fun <T : Entity> Player.traceEntityCollision(maxDistance: Float = ..., stepDistance: Float = ..., entityClass: Class<Any>, ignoreEntity: Any? = ...): EntityTraceResult<
      Any
   >? {
   var step: Float = stepDistance;
   val startPos: Vec3 = `$this$traceEntityCollision`.m_146892_();
   val direction: Vec3 = `$this$traceEntityCollision`.m_20154_();
   val maxDistanceVector: Vec3 = new Vec3(1.0, 1.0, 1.0).m_82490_((double)maxDistance);
   val entities: java.util.List = `$this$traceEntityCollision`.m_9236_()
      .m_6249_(null, new AABB(startPos.m_82546_(maxDistanceVector), startPos.m_82549_(maxDistanceVector)), PlayerExtensionsKt::traceEntityCollision$lambda$2);

   while (step <= maxDistance) {
      val location: Vec3 = startPos.m_82549_(direction.m_82490_((double)step));
      step += stepDistance;
      var `$this$filter$iv`: java.lang.Iterable = entities;
      var `destination$iv$iv`: java.util.Collection = new ArrayList();

      for (Object element$iv$iv : $this$filter$iv) {
         if (!(ignoreEntity == `element$iv$iv` as Entity) && (`element$iv$iv` as Entity).m_20191_().m_82390_(location)) {
            `destination$iv$iv`.add(`element$iv$iv`);
         }
      }

      `$this$filter$iv` = `destination$iv$iv` as java.util.List;
      `destination$iv$iv` = new ArrayList();

      for (Object element$iv$ivx : $this$filter$iv) {
         if (entityClass.isInstance(`element$iv$ivx` as Entity)) {
            `destination$iv$iv`.add(`element$iv$ivx`);
         }
      }

      val collided: java.util.List = `destination$iv$iv` as java.util.List;
      if (!(`destination$iv$iv` as java.util.List).isEmpty()) {
         return new EntityTraceResult(location, CollectionsKt.filterIsInstance(collided, entityClass));
      }
   }

   return null;
}

@JvmSynthetic
fun `traceEntityCollision$default`(var0: Player, var1: Float, var2: Float, var3: Class, var4: Entity, var5: Int, var6: Any): EntityTraceResult {
   if ((var5 and 1) != 0) {
      var1 = 10.0F;
   }

   if ((var5 and 2) != 0) {
      var2 = 0.05F;
   }

   if ((var5 and 8) != 0) {
      var4 = null;
   }

   return traceEntityCollision(var0, var1, var2, var3, var4);
}

public fun Player.traceBlockCollision(
   maxDistance: Float = 10.0F,
   stepDistance: Float = 0.05F,
   blockFilter: (BlockState) -> Boolean = <unrepresentable>.INSTANCE as Function1
): TraceResult? {
   var step: Float = stepDistance;
   val startPos: Vec3 = `$this$traceBlockCollision`.m_146892_();
   val direction: Vec3 = `$this$traceBlockCollision`.m_20154_();
   var lastBlockPos: BlockPos = Vec3ExtensionsKt.toBlockPos(startPos);

   while (step <= maxDistance) {
      val location: Vec3 = startPos.m_82549_(direction.m_82490_((double)step));
      step += stepDistance;
      val blockPos: BlockPos = Vec3ExtensionsKt.toBlockPos(location);
      if (!(blockPos == lastBlockPos)) {
         lastBlockPos = blockPos;
         val block: BlockState = `$this$traceBlockCollision`.m_9236_().m_8055_(blockPos);
         if (blockFilter.invoke(block) as java.lang.Boolean) {
            return new TraceResult(location, blockPos, findDirectionForIntercept(startPos, location, blockPos));
         }
      }
   }

   return null;
}

@JvmSynthetic
fun `traceBlockCollision$default`(var0: Player, var1: Float, var2: Float, var3: Function1, var4: Int, var5: Any): TraceResult {
   if ((var4 and 1) != 0) {
      var1 = 10.0F;
   }

   if ((var4 and 2) != 0) {
      var2 = 0.05F;
   }

   if ((var4 and 4) != 0) {
      var3 = <unrepresentable>.INSTANCE;
   }

   return traceBlockCollision(var0, var1, var2, var3);
}

public fun findDirectionForIntercept(p0: Vec3, p1: Vec3, blockPos: BlockPos): Direction {
   val xFunc: Function1 = (new Function1<java.lang.Double, java.lang.Double>(p0, p1) {
      {
         super(1);
         this.$p0 = `$p0`;
         this.$p1 = `$p1`;
      }

      @NotNull
      public final java.lang.Double invoke(double it) {
         return this.$p0.f_82479_ + (this.$p1.f_82479_ - this.$p0.f_82479_) * it;
      }
   }) as Function1;
   val yFunc: Function1 = (new Function1<java.lang.Double, java.lang.Double>(p0, p1) {
      {
         super(1);
         this.$p0 = `$p0`;
         this.$p1 = `$p1`;
      }

      @NotNull
      public final java.lang.Double invoke(double it) {
         return this.$p0.f_82480_ + (this.$p1.f_82480_ - this.$p0.f_82480_) * it;
      }
   }) as Function1;
   val zFunc: Function1 = (new Function1<java.lang.Double, java.lang.Double>(p0, p1) {
      {
         super(1);
         this.$p0 = `$p0`;
         this.$p1 = `$p1`;
      }

      @NotNull
      public final java.lang.Double invoke(double it) {
         return this.$p0.f_82481_ + (this.$p1.f_82481_ - this.$p0.f_82481_) * it;
      }
   }) as Function1;
   val tForX: Function1 = (new Function1<java.lang.Double, java.lang.Double>(p0, p1) {
      {
         super(1);
         this.$p0 = `$p0`;
         this.$p1 = `$p1`;
      }

      @NotNull
      public final java.lang.Double invoke(double it) {
         return if (this.$p0.f_82479_ != this.$p1.f_82479_) (it - this.$p0.f_82479_) / (this.$p1.f_82479_ - this.$p0.f_82479_) else this.$p0.f_82479_;
      }
   }) as Function1;
   val tForY: Function1 = (new Function1<java.lang.Double, java.lang.Double>(p0, p1) {
      {
         super(1);
         this.$p0 = `$p0`;
         this.$p1 = `$p1`;
      }

      @NotNull
      public final java.lang.Double invoke(double it) {
         return if (this.$p0.f_82480_ != this.$p1.f_82480_) (it - this.$p0.f_82480_) / (this.$p1.f_82480_ - this.$p0.f_82480_) else this.$p0.f_82480_;
      }
   }) as Function1;
   val tForZ: Function1 = (new Function1<java.lang.Double, java.lang.Double>(p0, p1) {
      {
         super(1);
         this.$p0 = `$p0`;
         this.$p1 = `$p1`;
      }

      @NotNull
      public final java.lang.Double invoke(double it) {
         return if (this.$p0.f_82481_ != this.$p1.f_82481_) (it - this.$p0.f_82481_) / (this.$p1.f_82481_ - this.$p0.f_82481_) else this.$p0.f_82481_;
      }
   }) as Function1;
   val xRange: ClosedFloatingPointRange = RangesKt.rangeTo((double)blockPos.m_123341_(), (double)blockPos.m_123341_() + 1.0);
   val yRange: ClosedFloatingPointRange = RangesKt.rangeTo((double)blockPos.m_123342_(), (double)blockPos.m_123342_() + 1.0);
   val zRange: ClosedFloatingPointRange = RangesKt.rangeTo((double)blockPos.m_123343_(), (double)blockPos.m_123343_() + 1.0);
   val tAtNorth: Double = (tForZ.invoke((double)blockPos.m_123343_()) as java.lang.Number).doubleValue();
   val tAtSouth: Double = (tForZ.invoke((double)blockPos.m_123343_() + 1.0) as java.lang.Number).doubleValue();
   val tAtEast: Double = (tForX.invoke((double)blockPos.m_123341_() + 1.0) as java.lang.Number).doubleValue();
   val tAtWest: Double = (tForX.invoke((double)blockPos.m_123341_()) as java.lang.Number).doubleValue();
   val tAtUp: Double = (tForY.invoke((double)blockPos.m_123342_() + 1.0) as java.lang.Number).doubleValue();
   val tAtDown: Double = (tForY.invoke((double)blockPos.m_123342_()) as java.lang.Number).doubleValue();
   val northCollision: Boolean = yRange.contains(yFunc.invoke(tAtNorth) as java.lang.Comparable)
      && xRange.contains(xFunc.invoke(tAtNorth) as java.lang.Comparable);
   val southCollision: Boolean = yRange.contains(yFunc.invoke(tAtSouth) as java.lang.Comparable)
      && xRange.contains(xFunc.invoke(tAtSouth) as java.lang.Comparable);
   val eastCollision: Boolean = yRange.contains(yFunc.invoke(tAtEast) as java.lang.Comparable)
      && zRange.contains(zFunc.invoke(tAtEast) as java.lang.Comparable);
   val westCollision: Boolean = yRange.contains(yFunc.invoke(tAtWest) as java.lang.Comparable)
      && zRange.contains(zFunc.invoke(tAtWest) as java.lang.Comparable);
   val upCollision: Boolean = zRange.contains(zFunc.invoke(tAtUp) as java.lang.Comparable) && xRange.contains(xFunc.invoke(tAtUp) as java.lang.Comparable);
   val downCollision: Boolean = zRange.contains(zFunc.invoke(tAtDown) as java.lang.Comparable)
      && xRange.contains(xFunc.invoke(tAtDown) as java.lang.Comparable);
   var minDirection: Direction = Direction.UP;
   var minTime: Double = java.lang.Double.MAX_VALUE;
   if (northCollision && tAtNorth < java.lang.Double.MAX_VALUE) {
      minDirection = Direction.NORTH;
      minTime = tAtNorth;
   }

   if (southCollision && tAtSouth < minTime) {
      minDirection = Direction.SOUTH;
      minTime = tAtSouth;
   }

   if (eastCollision && tAtEast < minTime) {
      minDirection = Direction.EAST;
      minTime = tAtEast;
   }

   if (westCollision && tAtWest < minTime) {
      minDirection = Direction.WEST;
      minTime = tAtWest;
   }

   if (upCollision && tAtUp < minTime) {
      minDirection = Direction.UP;
      minTime = tAtUp;
   }

   return if (downCollision && tAtDown < minTime) Direction.DOWN else minDirection;
}

public fun ServerPlayer.raycast(maxDistance: Float, fluidHandling: FluidHandling?): BlockHitResult {
   val f: Float = `$this$raycast`.m_146909_();
   val g: Float = `$this$raycast`.m_146908_();
   val vec3d: Vec3 = `$this$raycast`.m_146892_();
   val h: Float = Mth.m_14089_(-g * (float) (Math.PI / 180.0) - (float) Math.PI);
   val i: Float = Mth.m_14031_(-g * (float) (Math.PI / 180.0) - (float) Math.PI);
   val j: Float = -Mth.m_14089_(-f * (float) (Math.PI / 180.0));
   val var10000: BlockHitResult = `$this$raycast`.m_9236_()
      .m_45547_(
         new ClipContext(
            vec3d,
            vec3d.m_82520_(
               (double)(i * j) * (double)maxDistance,
               (double)Mth.m_14031_(-f * (float) (Math.PI / 180.0)) * (double)maxDistance,
               (double)(h * j) * (double)maxDistance
            ),
            Block.OUTLINE,
            fluidHandling,
            `$this$raycast` as Entity
         )
      );
   return var10000;
}

public fun ServerPlayer.raycastSafeSendout(pokemon: Pokemon, maxDistance: Double, dropHeight: Double, fluidHandling: FluidHandling?): Vec3? {
   val f: Float = `$this$raycastSafeSendout`.m_146909_();
   val g: Float = `$this$raycastSafeSendout`.m_146908_();
   val vec3d: Vec3 = `$this$raycastSafeSendout`.m_146892_();
   val h: Float = Mth.m_14089_(-g * (float) (Math.PI / 180.0) - (float) Math.PI);
   val i: Float = Mth.m_14031_(-g * (float) (Math.PI / 180.0) - (float) Math.PI);
   val j: Float = -Mth.m_14089_(-f * (float) (Math.PI / 180.0));
   val k: Float = Mth.m_14031_(-f * (float) (Math.PI / 180.0));
   val l: Float = i * j;
   val n: Float = h * j;
   val result: BlockHitResult = `$this$raycastSafeSendout`.m_9236_()
      .m_45547_(
         new ClipContext(
            vec3d,
            vec3d.m_82520_((double)l * maxDistance, (double)k * maxDistance, (double)(h * j) * maxDistance),
            Block.OUTLINE,
            fluidHandling,
            `$this$raycastSafeSendout` as Entity
         )
      );
   if (!`$this$raycastSafeSendout`.m_9236_().m_8055_(result.m_82425_()).m_60795_()) {
      if (result.m_82434_() != Direction.UP) {
         val posOffset: Vec3 = result.m_82450_()
            .m_231075_(
               result.m_82434_(),
               if (result.m_82434_() === Direction.DOWN)
                  0.125 + (double)(pokemon.getForm().getHitbox().f_20378_ * pokemon.getForm().getBaseScale()) * 0.5
                  else
                  0.125 + (double)(pokemon.getForm().getHitbox().f_20377_ * pokemon.getForm().getBaseScale()) * 0.5
            );
         var var43: Level = `$this$raycastSafeSendout`.m_9236_();
         val var35: TraceResult = WorldExtensionsKt.traceDownwards$default(posOffset, var43, (float)dropHeight, 0.0F, 4, null);
         if (var35 != null) {
            var43 = `$this$raycastSafeSendout`.m_9236_();
            if (pokemon.isPositionSafe(var43, var35.getBlockPos())) {
               val var46: Double = var35.getLocation().f_82479_;
               val var47: BlockPos = var35.getBlockPos().m_7494_();
               return new Vec3(var46, BlockPosExtensionsKt.toVec3d(var47).f_82480_, var35.getLocation().f_82481_);
            }
         }

         return null;
      } else {
         if (!`$this$raycastSafeSendout`.m_9236_().m_8055_(result.m_82425_().m_7494_()).m_280296_()) {
            val var42: Level = `$this$raycastSafeSendout`.m_9236_();
            val var10002: BlockPos = result.m_82425_();
            if (pokemon.isPositionSafe(var42, var10002)) {
               val var45: Double = result.m_82450_().f_82479_;
               val var10003: BlockPos = result.m_82425_().m_7494_();
               return new Vec3(var45, BlockPosExtensionsKt.toVec3d(var10003).f_82480_, result.m_82450_().f_82481_);
            }
         }

         return null;
      }
   } else {
      val minDrop: Double = Math.min(2.5, maxDistance);
      val traceDown: Double = 0.05;
      var step: Double = minDrop;
      var stepDrop: Double = minDrop;
      var smallestHeight: Double = dropHeight;

      var fallLoc: TraceResult;
      for (fallLoc = null; step <= maxDistance; step += stepDistance) {
         val var10000: Vec3 = vec3d.m_82520_((double)l * step, (double)k * step, (double)n * step);
         if (minDrop != maxDistance) {
            stepDrop = (step - minDrop) / (maxDistance - minDrop) * dropHeight;
         }

         var var10001: Level = `$this$raycastSafeSendout`.m_9236_();
         val var33: TraceResult = WorldExtensionsKt.traceDownwards$default(var10000, var10001, (float)stepDrop, 0.0F, 4, null);
         if (var33 != null) {
            var10001 = `$this$raycastSafeSendout`.m_9236_();
            if (pokemon.isPositionSafe(var10001, var33.getBlockPos())) {
               val var37: Double = var10000.f_82480_ - var33.getLocation().f_82480_;
               if (var37 < smallestHeight) {
                  smallestHeight = var37;
                  fallLoc = var33;
               }
            }
         }
      }

      if (fallLoc != null) {
         var var38: BlockPos = fallLoc.getBlockPos();
         if (var38 != null) {
            var38 = var38.m_7494_();
            if (var38 != null) {
               return var38.m_252807_();
            }
         }
      }

      return null;
   }
}

public fun Inventory.usableItems(): List<ItemStack> {
   val var10000: NonNullList = `$this$usableItems`.f_35976_;
   val var1: java.util.Collection = var10000 as java.util.Collection;
   val var10001: NonNullList = `$this$usableItems`.f_35974_;
   return CollectionsKt.plus(var1, var10001 as java.lang.Iterable);
}

public fun Player.giveOrDropItemStack(stack: ItemStack, playSound: Boolean = true) {
   if (`$this$giveOrDropItemStack`.m_150109_().m_36054_(stack) && stack.m_41619_()) {
      stack.m_41764_(1);
      val var6: ItemEntity = `$this$giveOrDropItemStack`.m_36176_(stack, false);
      if (var6 != null) {
         var6.m_32065_();
      }

      if (playSound) {
         `$this$giveOrDropItemStack`.m_9236_()
            .m_6263_(
               null,
               `$this$giveOrDropItemStack`.m_20185_(),
               `$this$giveOrDropItemStack`.m_20186_(),
               `$this$giveOrDropItemStack`.m_20189_(),
               SoundEvents.f_12019_,
               SoundSource.PLAYERS,
               0.2F,
               ((`$this$giveOrDropItemStack`.m_217043_().m_188501_() - `$this$giveOrDropItemStack`.m_217043_().m_188501_()) * 0.7F + 1.0F) * 2.0F
            );
      }

      `$this$giveOrDropItemStack`.f_36096_.m_38946_();
   } else {
      val var10000: ItemEntity = `$this$giveOrDropItemStack`.m_36176_(stack, false);
      if (var10000 != null) {
         var10000.m_32061_();
         var10000.m_266426_(`$this$giveOrDropItemStack`.m_20148_());
      }
   }
}

@JvmSynthetic
fun `giveOrDropItemStack$default`(var0: Player, var1: ItemStack, var2: Boolean, var3: Int, var4: Any) {
   if ((var3 and 2) != 0) {
      var2 = true;
   }

   giveOrDropItemStack(var0, var1, var2);
}

public fun ServerPlayer.getBattleTheme(): SoundEvent {
   var var3: SoundEvent;
   label11: {
      val var10000: ResourceLocation = Cobblemon.INSTANCE.getPlayerData().get(`$this$getBattleTheme` as Player).getBattleTheme();
      if (var10000 != null) {
         var3 = BuiltInRegistries.f_256894_.m_7745_(var10000) as SoundEvent;
         if (var3 != null) {
            break label11;
         }
      }

      var3 = CobblemonSounds.PVP_BATTLE;
   }

   return var3;
}

fun `traceEntityCollision$lambda$2`(`$tmp0`: Function1, p0: Any): Boolean {
   return `$tmp0`.invoke(p0) as java.lang.Boolean;
}
