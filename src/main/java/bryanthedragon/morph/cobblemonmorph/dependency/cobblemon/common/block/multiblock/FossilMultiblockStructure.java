package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.multiblock

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonSounds
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.CobblemonEvents
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokemon.FossilRevivedEvent
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.fossil.Fossil
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.fossil.Fossils
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.fossil.NaturalMaterials
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.multiblock.MultiblockEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.multiblock.MultiblockStructure
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonProperties
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.EventObservable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.tags.CobblemonItemTags
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.FossilAnalyzerBlock
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.MonitorBlock
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.RestorationTankBlock
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.MonitorBlock.MonitorScreen
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.entity.FossilMultiblockEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.entity.RestorationTankBlockEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.fossil.FossilState
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.sound.CancellableSoundController
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.sound.CancellableSoundInstance
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.PokeBallItem
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokeball.PokeBall
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.DistributionUtilsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.PlayerExtensionsKt
import com.mojang.authlib.GameProfile
import java.util.ArrayList;
import java.util.Arrays
import java.util.Optional
import java.util.UUID
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.nbt.CompoundTag
import net.minecraft.nbt.ListTag
import net.minecraft.nbt.NbtUtils
import net.minecraft.nbt.Tag
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.MinecraftServer
import net.minecraft.server.level.ServerLevel
import net.minecraft.server.level.ServerPlayer
import net.minecraft.server.players.GameProfileCache
import net.minecraft.sounds.SoundSource
import net.minecraft.tags.FluidTags
import net.minecraft.util.RandomSource
import net.minecraft.world.Containers
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.Pose
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.ItemLike
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.HorizontalDirectionalBlock
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityTicker
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.properties.Property
import net.minecraft.world.phys.AABB
import net.minecraft.world.phys.BlockHitResult

@SourceDebugExtension(["SMAP\nFossilMultiblockStructure.kt\nKotlin\n*S Kotlin\n*F\n+ 1 FossilMultiblockStructure.kt\ncom/cobblemon/mod/common/block/multiblock/FossilMultiblockStructure\n+ 2 EventObservables.kt\ncom/cobblemon/mod/common/api/reactive/EventObservable\n+ 3 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n+ 4 EventObservables.kt\ncom/cobblemon/mod/common/api/reactive/EventObservable$post$1\n+ 5 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,673:1\n14#2,5:674\n19#2:682\n14#2,5:683\n19#2:691\n13579#3:679\n13580#3:681\n13579#3:688\n13580#3:690\n14#4:680\n14#4:689\n1855#5,2:692\n1855#5,2:694\n1855#5,2:696\n1855#5,2:698\n*S KotlinDebug\n*F\n+ 1 FossilMultiblockStructure.kt\ncom/cobblemon/mod/common/block/multiblock/FossilMultiblockStructure\n*L\n129#1:674,5\n129#1:682\n227#1:683,5\n227#1:691\n129#1:679\n129#1:681\n227#1:688\n227#1:690\n129#1:680\n227#1:689\n333#1:692,2\n339#1:694,2\n438#1:696,2\n604#1:698,2\n*E\n"])
public class FossilMultiblockStructure(monitorPos: BlockPos, analyzerPos: BlockPos, tankBasePos: BlockPos, animAge: Int = -1, animPartialTicks: Float = 0.0F) :
   MultiblockStructure {
   public final val analyzerPos: BlockPos
   public open val controllerBlockPos: BlockPos
   public final var fillLevel: Int
   public final var fossilInventory: MutableList<ItemStack>
   private final var fossilOwnerUUID: UUID?
   public final val fossilState: FossilState

   public final var hasCreatedPokemon: Boolean
      private set

   private final var lastInteraction: Long
   private final var machineStartTime: Long
   public final val monitorPos: BlockPos

   public final var organicMaterialInside: Int
      private set

   private final var protectionTime: Int

   public final var resultingFossil: Fossil?
      private set

   public final val tankBasePos: BlockPos
   public final var tankConnectorDirection: Direction?

   public final var timeRemaining: Int
      private set

   init {
      this.monitorPos = monitorPos;
      this.analyzerPos = analyzerPos;
      this.tankBasePos = tankBasePos;
      this.controllerBlockPos = this.analyzerPos;
      this.timeRemaining = -1;
      this.protectionTime = -1;
      this.fossilState = new FossilState(animAge, animPartialTicks);
      this.fossilInventory = new ArrayList<>();
   }

   public override fun onUse(
      blockState: BlockState,
      world: Level,
      blockPos: BlockPos,
      player: Player,
      interactionHand: InteractionHand,
      blockHitResult: BlockHitResult
   ): InteractionResult {
      val stack: ItemStack = player.m_21120_(interactionHand);
      if (stack.m_204117_(CobblemonItemTags.POKE_BALLS) || stack.m_41720_() is PokeBallItem) {
         if (player !is ServerPlayer) {
            return InteractionResult.SUCCESS;
         }

         if (this.hasCreatedPokemon) {
            if (this.fossilOwnerUUID != null && !((player as ServerPlayer).m_20148_() == this.fossilOwnerUUID)) {
               var var23: java.lang.String = "UNKNOWN_USER";
               val var33: MinecraftServer = DistributionUtilsKt.server();
               if (var33 != null) {
                  val var34: GameProfileCache = var33.m_129927_();
                  if (var34 != null) {
                     val var35: Optional = var34.m_11002_(this.fossilOwnerUUID);
                     if (var35 != null) {
                        val var36: GameProfile = var35.orElse(null) as GameProfile;
                        if (var36 != null) {
                           val var37: java.lang.String = var36.getName();
                           if (var37 != null) {
                              var23 = var37;
                           }
                        }
                     }
                  }
               }

               player.m_5661_(LocalizationUtilsKt.lang("fossilmachine.protected", var23) as Component, true);
               return InteractionResult.FAIL;
            }

            val var30: Item = stack.m_41720_();
            val var21: PokeBall = (var30 as PokeBallItem).getPokeBall();
            if (!(player as ServerPlayer).m_7500_()) {
               if (stack != null) {
                  stack.m_41774_(1);
               }
            }

            label121: {
               if (this.resultingFossil != null) {
                  val var31: PokemonProperties = this.resultingFossil.getResult();
                  if (var31 != null) {
                     var32 = var31.create();
                     break label121;
                  }
               }

               var32 = null;
            }

            if (var32 != null) {
               var32.setCaughtBall(var21);
               PlayerExtensionsKt.party(player as ServerPlayer).add(var32);
               this.fossilState.setGrowthState("Taken");
               player.m_6330_(CobblemonSounds.FOSSIL_MACHINE_RETRIEVE_POKEMON, SoundSource.BLOCKS, 1.0F, 1.0F);
               val monitorState: EventObservable = CobblemonEvents.FOSSIL_REVIVED;
               val `events$iv`: Array<FossilRevivedEvent> = new FossilRevivedEvent[]{new FossilRevivedEvent(var32, player as ServerPlayer)};
               monitorState.emit(Arrays.copyOf(`events$iv`, `events$iv`.length));

               for (Object element$iv$iv : events$iv) {
                  ;
               }
            }

            val var25: BlockState = world.m_8055_(this.monitorPos);
            if (var25.m_61138_(MonitorBlock.Companion.getSCREEN() as Property) && !var25.equals(MonitorBlock.MonitorScreen.OFF)) {
               world.m_46597_(this.monitorPos, var25.m_61124_(MonitorBlock.Companion.getSCREEN() as Property, MonitorBlock.MonitorScreen.OFF) as BlockState);
            }

            this.hasCreatedPokemon = false;
            this.fossilOwnerUUID = null;
            this.protectionTime = -1;
            this.updateFossilType(world);
            this.syncToClient(world);
            this.markDirty(world);
            return InteractionResult.SUCCESS;
         }
      }

      if (player.m_21120_(interactionHand).m_41619_()) {
         if (!this.isRunning() && !this.hasCreatedPokemon) {
            if (this.fossilInventory.isEmpty()) {
               return InteractionResult.CONSUME;
            }

            if (player is ServerPlayer) {
               player.m_21008_(interactionHand, CollectionsKt.last(this.fossilInventory) as ItemStack);
               this.fossilInventory.remove(this.fossilInventory.size() - 1);
               world.m_247517_(null, this.analyzerPos, CobblemonSounds.FOSSIL_MACHINE_RETRIEVE_FOSSIL, SoundSource.BLOCKS);
               this.updateFossilType(world);
               this.syncToClient(world);
               this.markDirty(world);
            }
         }

         return InteractionResult.CONSUME;
      } else {
         val var10000: Fossils = Fossils.INSTANCE;
         if (var10000.isFossilIngredient(stack)) {
            if (!this.isRunning() && !this.hasCreatedPokemon) {
               if (this.fossilInventory.size() > Cobblemon.INSTANCE.getConfig().getMaxInsertedFossilItems()) {
                  return InteractionResult.FAIL;
               }

               if (player is ServerPlayer) {
                  val var20: ItemStack = stack.m_255036_(1);
                  if (!(player as ServerPlayer).m_7500_()) {
                     stack.m_41774_(1);
                  }

                  this.fossilOwnerUUID = (player as ServerPlayer).m_20148_();
                  val var29: java.util.List = this.fossilInventory;
                  var29.add(var20);
                  this.updateFossilType(world);
                  world.m_247517_(null, this.analyzerPos, CobblemonSounds.FOSSIL_MACHINE_INSERT_FOSSIL, SoundSource.BLOCKS);
                  this.syncToClient(world);
                  this.markDirty(world);
               }
            }

            return InteractionResult.SUCCESS;
         } else if (NaturalMaterials.INSTANCE.isNaturalMaterial(stack)) {
            if (player is ServerPlayer
               && !this.isRunning()
               && !this.hasCreatedPokemon
               && this.organicMaterialInside < 128
               && this.insertOrganicMaterial(new ItemStack(stack.m_41720_() as ItemLike, 1), world)) {
               this.lastInteraction = world.m_46467_();
               if (!(player as ServerPlayer).m_7500_()) {
                  val returnItem: ResourceLocation = NaturalMaterials.INSTANCE.getReturnItem(stack);
                  stack.m_41774_(1);
                  PlayerExtensionsKt.giveOrDropItemStack(player, new ItemStack(BuiltInRegistries.f_257033_.m_7745_(returnItem) as ItemLike), false);
               }
            }

            val var28: InteractionResult = InteractionResult.m_19078_(world.f_46443_);
            return var28;
         } else {
            return if (stack.m_204117_(CobblemonItemTags.FOSSILS)) InteractionResult.SUCCESS else InteractionResult.PASS;
         }
      }
   }

   public fun spawn(world: Level, pos: BlockPos, directionToBehind: Direction, pokemon: Pokemon): Boolean {
      val entity: PokemonEntity = new PokemonEntity(world, pokemon, null, 4, null);
      entity.m_6210_();
      val idealPlace: BlockPos = pos.m_121955_(directionToBehind.m_122436_().m_142393_((int)Math.ceil(entity.m_20191_().m_82362_() / 2.0) + 1));
      var box: AABB = entity.m_6972_(Pose.STANDING).m_20393_(idealPlace.m_252807_().m_82492_(0.0, 0.5, 0.0));

      for (int i = 0; i < 6; i++) {
         box = box.m_82386_((double)directionToBehind.m_122436_().m_123341_(), 0.0, (double)directionToBehind.m_122436_().m_123343_());
         val var10002: BlockPos = idealPlace.m_121955_(directionToBehind.m_122436_());
         val fixedPosition: BlockPos = this.makeSuitableY(world, var10002, entity, box);
         if (fixedPosition != null) {
            entity.m_146884_(fixedPosition.m_252807_().m_82492_(0.0, 0.5, 0.0));
            if (world.m_7967_(entity as Entity)) {
               val `$this$iv`: EventObservable = CobblemonEvents.FOSSIL_REVIVED;
               val `events$iv`: Array<FossilRevivedEvent> = new FossilRevivedEvent[]{new FossilRevivedEvent(pokemon, null)};
               `$this$iv`.emit(Arrays.copyOf(`events$iv`, `events$iv`.length));

               for (Object element$iv$iv : events$iv) {
                  ;
               }

               return true;
            }

            Cobblemon.INSTANCE.getLOGGER().warn("Couldn't spawn resurrected Pokémon for some reason");
            break;
         }
      }

      return false;
   }

   public fun isSafeFloor(world: Level, pos: BlockPos, entity: PokemonEntity): Boolean {
      val state: BlockState = world.m_8055_(pos);
      return !state.m_60795_()
         && (
            state.m_60634_(world as BlockGetter, pos, entity as Entity)
               || state.m_60638_(world as BlockGetter, pos, entity as Entity, Direction.DOWN)
               || (entity.getBehaviour().getMoving().getSwim().getCanWalkOnWater() || entity.getBehaviour().getMoving().getSwim().getCanSwimInWater())
                  && state.m_60819_().m_205070_(FluidTags.f_13131_)
               || (entity.getBehaviour().getMoving().getSwim().getCanWalkOnLava() || entity.getBehaviour().getMoving().getSwim().getCanSwimInLava())
                  && state.m_60819_().m_205070_(FluidTags.f_13132_)
         );
   }

   public fun makeSuitableY(world: Level, pos: BlockPos, entity: PokemonEntity, box: AABB): BlockPos? {
      if (world.m_186437_(entity as Entity, box)) {
         for (int i = 1; i < 16; i++) {
            if (!world.m_186437_(entity as Entity, box.m_82386_(0.5, (double)i, 0.5))) {
               val var10002: BlockPos = pos.m_7918_(0, i - 1, 0);
               if (this.isSafeFloor(world, var10002, entity)) {
                  return pos.m_7918_(0, i, 0);
               }
            }
         }
      } else {
         for (int ix = 1; ix < 16; ix++) {
            if (world.m_186437_(entity as Entity, box.m_82386_(0.5, -((double)ix), 0.5))) {
               val var9: BlockPos = pos.m_7918_(0, -ix, 0);
               if (this.isSafeFloor(world, var9, entity)) {
                  return pos.m_7918_(0, -ix + 1, 0);
               }
            }
         }
      }

      return null;
   }

   @Deprecated(message = "Deprecated in Java")
   public override fun getComparatorOutput(state: BlockState, world: Level?, pos: BlockPos?): Int {
      if (world == null || pos == null) {
         return 0;
      } else if (this.monitorPos == pos) {
         if (this.hasCreatedPokemon) {
            return 15;
         } else {
            return if (!this.isRunning()) 0 else Math.max(15 - this.timeRemaining * 15 / 14400, 1);
         }
      } else {
         return if (!(this.tankBasePos == pos) && !(this.tankBasePos.m_7494_() == pos)) 0 else this.organicMaterialInside * 15 / 128;
      }
   }

   public override fun onTriggerEvent(state: BlockState?, world: ServerLevel?, pos: BlockPos?, random: RandomSource?) {
      if (this.protectionTime <= 0) {
         if (!this.hasCreatedPokemon) {
            return;
         }

         if (this.resultingFossil == null) {
            return;
         }

         val var10000: PokemonProperties = this.resultingFossil.getResult();
         if (var10000 == null) {
            return;
         }

         val var8: Pokemon = var10000.create();
         if (var8 == null) {
            return;
         }

         label29: {
            if (state != null) {
               val var9: Direction = state.m_61143_(HorizontalDirectionalBlock.f_54117_ as Property) as Direction;
               if (var9 != null) {
                  var10 = var9.m_122424_();
                  break label29;
               }
            }

            var10 = null;
         }

         if (pos != null && var10 != null && world != null && this.spawn(world as Level, pos, var10, var8)) {
            this.fossilState.setGrowthState("Taken");
            this.hasCreatedPokemon = false;
            this.fossilOwnerUUID = null;
            this.protectionTime = -1;
            world.m_247517_(null, this.tankBasePos, CobblemonSounds.FOSSIL_MACHINE_RETRIEVE_POKEMON, SoundSource.BLOCKS);
            this.updateFossilType(world as Level);
            this.syncToClient(world as Level);
            this.markDirty(world as Level);
         }
      }
   }

   public override fun onBreak(world: Level, pos: BlockPos, state: BlockState, player: Player?) {
      var monitorEntity: MultiblockEntity;
      var direction: Direction;
      var var19: MultiblockEntity;
      var var20: MultiblockEntity;
      var var21: MultiblockEntity;
      var var29: Pokemon;
      val analyzerEntity: BlockEntity = world.m_7702_(this.monitorPos);
      monitorEntity = analyzerEntity as? MultiblockEntity;
      val tankBaseEntity: BlockEntity = world.m_7702_(this.analyzerPos);
      var19 = tankBaseEntity as? MultiblockEntity;
      val tankTopEntity: BlockEntity = world.m_7702_(this.tankBasePos);
      var20 = tankTopEntity as? MultiblockEntity;
      val tankBaseBlockState: BlockEntity = world.m_7702_(this.tankBasePos.m_7494_());
      var21 = tankBaseBlockState as? MultiblockEntity;
      direction = (world.m_8055_(if (var20 != null) var20.m_58899_() else null).m_61143_(HorizontalDirectionalBlock.f_54117_ as Property) as Direction)
         .m_122424_();
      label113:
      if (this.hasCreatedPokemon) {
         if (this.resultingFossil != null) {
            val var10000: PokemonProperties = this.resultingFossil.getResult();
            if (var10000 != null) {
               var29 = var10000.create();
               break label113;
            }
         }

         var29 = null;
      } else {
         var29 = null;
      }

      if (monitorEntity != null) {
         monitorEntity.setMultiblockStructure(null);
      }

      if (var19 != null) {
         var19.setMultiblockStructure(null);
      }

      if (var20 != null) {
         var20.setMultiblockStructure(null);
      }

      if (var21 != null) {
         var21.setMultiblockStructure(null);
      }

      if (monitorEntity != null) {
         monitorEntity.setMasterBlockPos(null);
      }

      if (var19 != null) {
         var19.setMasterBlockPos(null);
      }

      if (var20 != null) {
         var20.setMasterBlockPos(null);
      }

      if (var21 != null) {
         var21.setMasterBlockPos(null);
      }

      if (this.timeRemaining == -1 || this.timeRemaining >= 20) {
         val `$this$forEach$iv`: java.lang.Iterable;
         for (Object element$iv : $this$forEach$iv) {
            Containers.m_18992_(
               world,
               (double)pos.m_123341_(),
               (double)pos.m_123342_(),
               (double)pos.m_123343_(),
               new ItemStack((`element$iv` as ItemStack).m_41720_() as ItemLike, 1)
            );
         }
      }

      if (var20 is RestorationTankBlockEntity) {
         val var23: java.lang.Iterable;
         for (Object element$iv : var23) {
            Containers.m_18992_(world, (double)pos.m_123341_(), (double)pos.m_123342_(), (double)pos.m_123343_(), var26 as ItemStack);
         }
      }

      if (var29 != null) {
         this.spawn(world, pos, direction, var29);
      }

      this.protectionTime = -1;
      this.updateFossilType(world);
      this.stopMachine(world);
      this.syncToClient(world);
      this.markDirty(world);
   }

   public override fun markRemoved(world: Level) {
      if (world.f_46443_) {
         val var10000: CancellableSoundController = CancellableSoundController.INSTANCE;
         val var10001: BlockPos = this.tankBasePos;
         val var10002: ResourceLocation = CobblemonSounds.FOSSIL_MACHINE_ACTIVE_LOOP.m_11660_();
         var10000.stopSound(var10001, var10002);
      }
   }

   public override fun tick(world: Level) {
      if (this.protectionTime > 0) {
         this.protectionTime += -1;
      }

      if (this.protectionTime == 0) {
         this.protectionTime = -1;
         this.fossilOwnerUUID = null;
         this.updateProgress(world);
         this.syncToClient(world);
         this.markDirty(world);
         world.m_247517_(null, this.tankBasePos, CobblemonSounds.FOSSIL_MACHINE_UNPROTECTED, SoundSource.BLOCKS);
      }

      if (!this.hasCreatedPokemon) {
         if (world.f_46443_ && this.isRunning() && (world.m_46467_() - this.machineStartTime) % 160L == 0L && world.f_46443_) {
            CancellableSoundController.INSTANCE
               .playSound(new CancellableSoundInstance(CobblemonSounds.FOSSIL_MACHINE_ACTIVE_LOOP, this.tankBasePos, true, 1.0F, 1.0F));
         }

         if (this.timeRemaining == -1 && this.organicMaterialInside >= 128 && this.resultingFossil != null) {
            this.startMachine(world);
         } else {
            if (this.timeRemaining >= 0) {
               this.timeRemaining += -1;
            }

            if (this.timeRemaining % 1800 == 0) {
               this.updateProgress(world);
               this.syncToClient(world);
               this.markDirty(world);
            }

            if (this.timeRemaining == 0) {
               world.m_247517_(null, this.tankBasePos, CobblemonSounds.FOSSIL_MACHINE_FINISHED, SoundSource.BLOCKS);
               this.fossilInventory.clear();
               this.hasCreatedPokemon = true;
               if (this.fossilOwnerUUID != null) {
                  this.protectionTime = 6000;
               }

               this.stopMachine(world);
            }
         }
      }
   }

   public override fun syncToClient(world: Level) {
      val analyzerEntity: BlockEntity = world.m_7702_(this.tankBasePos);
      val tankBaseEntity: MultiblockEntity = analyzerEntity as? MultiblockEntity;
      val monitorEntity: BlockEntity = world.m_7702_(this.getControllerBlockPos());
      val var6: MultiblockEntity = monitorEntity as? MultiblockEntity;
      val var5: BlockEntity = world.m_7702_(this.monitorPos);
      val var7: MultiblockEntity = var5 as? MultiblockEntity;
      if (tankBaseEntity != null) {
         world.m_7260_(this.tankBasePos, tankBaseEntity.m_58900_(), tankBaseEntity.m_58900_(), 2);
      }

      if (var6 != null) {
         world.m_7260_(this.analyzerPos, var6.m_58900_(), var6.m_58900_(), 2);
      }

      if (var7 != null) {
         world.m_7260_(this.monitorPos, var7.m_58900_(), var7.m_58900_(), 2);
      }
   }

   public override fun markDirty(world: Level) {
      val var9: java.lang.Iterable;
      for (Object element$iv : var9) {
         val it: BlockEntity = `element$iv` as BlockEntity;
         if (`element$iv` as BlockEntity != null) {
            it.m_6596_();
         }
      }
   }

   public fun startMachine(world: Level) {
      this.timeRemaining = 14400;
      this.machineStartTime = world.m_46467_();
      world.m_247517_(null, this.tankBasePos, CobblemonSounds.FOSSIL_MACHINE_ACTIVATE, SoundSource.BLOCKS);
      if (world.f_46443_) {
         CancellableSoundController.INSTANCE
            .playSound(new CancellableSoundInstance(CobblemonSounds.FOSSIL_MACHINE_ACTIVE_LOOP, this.tankBasePos, true, 1.0F, 1.0F));
      }

      this.updateOnStatus(world);
      this.updateProgress(world);
      this.syncToClient(world);
      this.markDirty(world);
   }

   public fun stopMachine(world: Level) {
      this.fossilState.setGrowthState("Fully Grown");
      this.timeRemaining = -1;
      this.organicMaterialInside = 0;
      this.fossilInventory.clear();
      if (world.f_46443_) {
         val var10000: CancellableSoundController = CancellableSoundController.INSTANCE;
         val var10001: BlockPos = this.tankBasePos;
         val var10002: ResourceLocation = CobblemonSounds.FOSSIL_MACHINE_ACTIVE_LOOP.m_11660_();
         var10000.stopSound(var10001, var10002);
      }

      this.updateOnStatus(world);
      this.updateProgress(world);
      this.syncToClient(world);
      this.markDirty(world);
   }

   public fun updateOnStatus(world: Level) {
      val upperTankPos: BlockPos = this.tankBasePos.m_7494_();
      val analyzerState: BlockState = world.m_8055_(this.analyzerPos);
      val tankState: BlockState = world.m_8055_(this.tankBasePos.m_7494_());
      if (analyzerState.m_61138_(FossilAnalyzerBlock.Companion.getON() as Property)) {
         world.m_46597_(this.analyzerPos, analyzerState.m_61124_(FossilAnalyzerBlock.Companion.getON() as Property, this.timeRemaining >= 0) as BlockState);
      }

      if (tankState.m_61138_(RestorationTankBlock.Companion.getON() as Property)) {
         world.m_46597_(upperTankPos, tankState.m_61124_(RestorationTankBlock.Companion.getON() as Property, this.timeRemaining >= 0) as BlockState);
      }
   }

   public fun updateProgress(world: Level) {
      val monitorState: BlockState = world.m_8055_(this.monitorPos);
      if (monitorState.m_61138_(MonitorBlock.Companion.getSCREEN() as Property)) {
         val screenID: MonitorBlock.MonitorScreen = if (this.protectionTime > 0.0F)
            MonitorBlock.MonitorScreen.GREEN_PROGRESS_9
            else
            (if (this.timeRemaining <= 0) MonitorBlock.MonitorScreen.OFF else this.getProgressScreen((14400 - this.timeRemaining) / 1800));
         world.m_46597_(this.monitorPos, monitorState.m_61124_(MonitorBlock.Companion.getSCREEN() as Property, screenID) as BlockState);
      }
   }

   public fun getProgressScreen(progress: Int): MonitorScreen {
      var var10000: MonitorBlock.MonitorScreen;
      switch (progress) {
         case 0:
            var10000 = MonitorBlock.MonitorScreen.BLUE_PROGRESS_1;
            break;
         case 1:
            var10000 = MonitorBlock.MonitorScreen.BLUE_PROGRESS_2;
            break;
         case 2:
            var10000 = MonitorBlock.MonitorScreen.BLUE_PROGRESS_3;
            break;
         case 3:
            var10000 = MonitorBlock.MonitorScreen.BLUE_PROGRESS_4;
            break;
         case 4:
            var10000 = MonitorBlock.MonitorScreen.BLUE_PROGRESS_5;
            break;
         case 5:
            var10000 = MonitorBlock.MonitorScreen.BLUE_PROGRESS_6;
            break;
         case 6:
            var10000 = MonitorBlock.MonitorScreen.BLUE_PROGRESS_7;
            break;
         case 7:
            var10000 = MonitorBlock.MonitorScreen.BLUE_PROGRESS_8;
            break;
         case 8:
            var10000 = MonitorBlock.MonitorScreen.BLUE_PROGRESS_9;
            break;
         default:
            var10000 = MonitorBlock.MonitorScreen.OFF;
      }

      return var10000;
   }

   public fun updateFossilType(world: Level) {
      if (this.fossilInventory.isEmpty()) {
         if (this.resultingFossil == null) {
            return;
         }

         this.resultingFossil = null;
      } else {
         this.resultingFossil = Fossils.INSTANCE.getFossilByItemStacks(this.fossilInventory);
      }
   }

   public fun isRunning(): Boolean {
      return this.timeRemaining > 0;
   }

   public fun insertOrganicMaterial(stack: ItemStack, world: Level): Boolean {
      var natureValue: Int = NaturalMaterials.INSTANCE.getContent(stack);
      if (this.timeRemaining <= 0 && this.organicMaterialInside < 128 && natureValue != null) {
         natureValue = natureValue * stack.m_41613_();
         if (natureValue <= 0 && this.organicMaterialInside == 0) {
            return false;
         } else {
            val oldFillStage: Int = this.organicMaterialInside * 8 / 128;
            if (this.organicMaterialInside + natureValue > 128) {
               this.organicMaterialInside = 128;
            } else if (this.organicMaterialInside + natureValue < 0) {
               this.organicMaterialInside = 0;
            } else {
               this.organicMaterialInside = this.organicMaterialInside + natureValue;
            }

            if (this.organicMaterialInside >= 128) {
               world.m_5594_(null, this.tankBasePos, CobblemonSounds.FOSSIL_MACHINE_DNA_FULL, SoundSource.BLOCKS, 1.0F, 1.0F);
            } else if (world.m_46467_() - this.lastInteraction < 10L) {
               world.m_5594_(null, this.tankBasePos, CobblemonSounds.FOSSIL_MACHINE_INSERT_DNA_SMALL, SoundSource.BLOCKS, 1.0F, 1.0F);
            } else {
               world.m_5594_(null, this.tankBasePos, CobblemonSounds.FOSSIL_MACHINE_INSERT_DNA, SoundSource.BLOCKS, 1.0F, 1.0F);
            }

            this.markDirty(world);
            if (oldFillStage != this.organicMaterialInside * 8 / 128) {
               this.syncToClient(world);
            }

            return true;
         }
      } else {
         return false;
      }
   }

   public fun insertFossil(stack: ItemStack, world: Level): Boolean {
      if (this.timeRemaining <= 0 && this.fossilInventory.size() != 3) {
         val oldFillStage: Int = this.fossilInventory.size();
         this.fossilInventory.add(stack);
         world.m_247517_(null, this.analyzerPos, CobblemonSounds.FOSSIL_MACHINE_INSERT_FOSSIL, SoundSource.BLOCKS);
         this.updateFossilType(world);
         this.markDirty(world);
         if (oldFillStage != this.fossilInventory.size()) {
            this.syncToClient(world);
         }

         return true;
      } else {
         return false;
      }
   }

   public override fun writeToNbt(): CompoundTag {
      val result: CompoundTag = new CompoundTag();
      result.m_128365_("MonitorPos", NbtUtils.m_129224_(this.monitorPos) as Tag);
      result.m_128365_("AnalyzerPos", NbtUtils.m_129224_(this.analyzerPos) as Tag);
      result.m_128365_("TankBasePos", NbtUtils.m_129224_(this.tankBasePos) as Tag);
      result.m_128405_("TimeLeft", this.timeRemaining);
      result.m_128405_("ProtectedTimeLeft", this.protectionTime);
      if (this.fossilOwnerUUID != null) {
         result.m_128362_("FossilOwner", this.fossilOwnerUUID);
      }

      result.m_128405_("OrganicContent", this.organicMaterialInside);
      val fossilInv: ListTag = new ListTag();

      val `$this$forEach$iv`: java.lang.Iterable;
      for (Object element$iv : $this$forEach$iv) {
         fossilInv.add((`element$iv` as ItemStack).m_41739_(new CompoundTag()));
      }

      result.m_128365_("InsertedFossilStacks", fossilInv as Tag);
      result.m_128359_("ConnectorDirection", if (this.tankConnectorDirection != null) this.tankConnectorDirection.toString() else null);
      if (this.resultingFossil != null) {
         val var10002: Fossil = this.resultingFossil;
         result.m_128359_("InsertedFossil", var10002.m_7912_());
      }

      result.m_128379_("HasCreatedPokemon", this.hasCreatedPokemon);
      return result;
   }

   @JvmStatic
   fun `TICKER$lambda$5`(world: Level, var1: BlockPos, var2: BlockState, blockEntity: FossilMultiblockEntity) {
      if (blockEntity.getMultiblockStructure() != null) {
         val var10000: MultiblockStructure = blockEntity.getMultiblockStructure();
         var10000.tick(world);
      }
   }

   @SourceDebugExtension(["SMAP\nFossilMultiblockStructure.kt\nKotlin\n*S Kotlin\n*F\n+ 1 FossilMultiblockStructure.kt\ncom/cobblemon/mod/common/block/multiblock/FossilMultiblockStructure$Companion\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,673:1\n1855#2,2:674\n*S KotlinDebug\n*F\n+ 1 FossilMultiblockStructure.kt\ncom/cobblemon/mod/common/block/multiblock/FossilMultiblockStructure$Companion\n*L\n645#1:674,2\n*E\n"])
   public companion object {
      public const val MATERIAL_TO_START: Int
      public const val PROTECTION_TIME: Int
      public final val TICKER: BlockEntityTicker<FossilMultiblockEntity>
      public const val TICKS_PER_MINUTE: Int
      public const val TIME_PER_STAGE: Int
      public const val TIME_TO_TAKE: Int

      public fun fromNbt(nbt: CompoundTag, animAge: Int = -1, partialTicks: Float = 0.0F): FossilMultiblockStructure {
         val monitorPos: BlockPos = NbtUtils.m_129239_(nbt.m_128469_("MonitorPos"));
         val compartmentPos: BlockPos = NbtUtils.m_129239_(nbt.m_128469_("AnalyzerPos"));
         val tankBasePos: BlockPos = NbtUtils.m_129239_(nbt.m_128469_("TankBasePos"));
         val result: FossilMultiblockStructure = new FossilMultiblockStructure(monitorPos, compartmentPos, tankBasePos, animAge, partialTicks);
         FossilMultiblockStructure.access$setOrganicMaterialInside$p(result, nbt.m_128451_("OrganicContent"));
         FossilMultiblockStructure.access$setTimeRemaining$p(result, nbt.m_128451_("TimeLeft"));
         FossilMultiblockStructure.access$setProtectionTime$p(result, if (nbt.m_128441_("ProtectedTimeLeft")) nbt.m_128451_("ProtectedTimeLeft") else -1);
         FossilMultiblockStructure.access$setFossilOwnerUUID$p(result, if (nbt.m_128441_("FossilOwner")) nbt.m_128342_("FossilOwner") else null);
         val var10000: Tag = nbt.m_128423_("InsertedFossilStacks");
         val fossilInv: ListTag = var10000 as ListTag;
         val actualFossilList: java.util.List = new ArrayList();

         val id: java.lang.Iterable;
         for (Object element$iv : id) {
            val it: Tag = `element$iv` as Tag;
            val var10001: ItemStack = ItemStack.m_41712_(it as CompoundTag);
            actualFossilList.add(var10001);
         }

         result.setFossilInventory(actualFossilList);
         result.setTankConnectorDirection(Direction.m_122402_(nbt.m_128461_("ConnectorDirection")));
         if (nbt.m_128441_("InsertedFossil")) {
            val var16: ResourceLocation = new ResourceLocation(nbt.m_128461_("InsertedFossil"));
            val var17: Fossil = Fossils.INSTANCE.getByIdentifier(var16);
            if (var17 != null) {
               FossilMultiblockStructure.access$setResultingFossil$p(result, var17);
            } else {
               Cobblemon.INSTANCE.getLOGGER().error("Loaded fossil structure with invalid fossil type: {}", var16);
            }
         }

         if (nbt.m_128441_("CreatedPokemon")) {
            FossilMultiblockStructure.access$setHasCreatedPokemon$p(result, true);
         } else if (nbt.m_128441_("HasCreatedPokemon")) {
            FossilMultiblockStructure.access$setHasCreatedPokemon$p(result, nbt.m_128471_("HasCreatedPokemon"));
         }

         result.setFillLevel(result.getOrganicMaterialInside() * 8 / 128);
         return result;
      }
   }
}
