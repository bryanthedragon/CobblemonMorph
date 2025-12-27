package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.berry

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.berry.Berry
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.entity.BerryBlockEntity
import net.minecraft.core.BlockPos
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.state.BlockState

public data BerryHarvestEvent(berry: Berry,
      player: ServerPlayer,
      world: Level,
      pos: BlockPos,
      state: BlockState,
      blockEntity: BerryBlockEntity,
      drops: MutableList<ItemStack>
   ) :
   BerryEvent {
   public open val berry: Berry
   public final val blockEntity: BerryBlockEntity
   public final val drops: MutableList<ItemStack>
   public final val player: ServerPlayer
   public final val pos: BlockPos
   public final val state: BlockState
   public final val world: Level

   init {
      this.berry = berry;
      this.player = player;
      this.world = world;
      this.pos = pos;
      this.state = state;
      this.blockEntity = blockEntity;
      this.drops = drops;
   }

   public operator fun component1(): Berry {
      return this.berry;
   }

   public operator fun component2(): ServerPlayer {
      return this.player;
   }

   public operator fun component3(): Level {
      return this.world;
   }

   public operator fun component4(): BlockPos {
      return this.pos;
   }

   public operator fun component5(): BlockState {
      return this.state;
   }

   public operator fun component6(): BerryBlockEntity {
      return this.blockEntity;
   }

   public operator fun component7(): MutableList<ItemStack> {
      return this.drops;
   }

   public fun copy(
      berry: Berry = this.berry,
      player: ServerPlayer = this.player,
      world: Level = this.world,
      pos: BlockPos = this.pos,
      state: BlockState = this.state,
      blockEntity: BerryBlockEntity = this.blockEntity,
      drops: MutableList<ItemStack> = this.drops
   ): BerryHarvestEvent {
      return new BerryHarvestEvent(berry, player, world, pos, state, blockEntity, drops);
   }

   public override fun toString(): String {
      return "BerryHarvestEvent(berry=${this.berry}, player=${this.player}, world=${this.world}, pos=${this.pos}, state=${this.state}, blockEntity=${this.blockEntity}, drops=${this.drops})";
   }

   public override fun hashCode(): Int {
      return (
               ((((this.berry.hashCode() * 31 + this.player.hashCode()) * 31 + this.world.hashCode()) * 31 + this.pos.hashCode()) * 31 + this.state.hashCode())
                     * 31
                  + this.blockEntity.hashCode()
            )
            * 31
         + this.drops.hashCode();
   }

   public override operator fun equals(other: Any?): Boolean {
      if (this === other) {
         return true;
      } else if (other !is BerryHarvestEvent) {
         return false;
      } else {
         val var2: BerryHarvestEvent = other as BerryHarvestEvent;
         if (!(this.berry == (other as BerryHarvestEvent).berry)) {
            return false;
         } else if (!(this.player == var2.player)) {
            return false;
         } else if (!(this.world == var2.world)) {
            return false;
         } else if (!(this.pos == var2.pos)) {
            return false;
         } else if (!(this.state == var2.state)) {
            return false;
         } else if (!(this.blockEntity == var2.blockEntity)) {
            return false;
         } else {
            return this.drops == var2.drops;
         }
      }
   }
}
