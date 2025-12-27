package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.berry

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.berry.Berry
import net.minecraft.core.BlockPos
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.state.BlockState

public data BerryMutationOfferEvent(berry: Berry, world: Level, state: BlockState, pos: BlockPos, mutations: MutableSet<Berry>) : BerryEvent {
   public open val berry: Berry
   public final val mutations: MutableSet<Berry>
   public final val pos: BlockPos
   public final val state: BlockState
   public final val world: Level

   init {
      this.berry = berry;
      this.world = world;
      this.state = state;
      this.pos = pos;
      this.mutations = mutations;
   }

   public operator fun component1(): Berry {
      return this.berry;
   }

   public operator fun component2(): Level {
      return this.world;
   }

   public operator fun component3(): BlockState {
      return this.state;
   }

   public operator fun component4(): BlockPos {
      return this.pos;
   }

   public operator fun component5(): MutableSet<Berry> {
      return this.mutations;
   }

   public fun copy(
      berry: Berry = this.berry,
      world: Level = this.world,
      state: BlockState = this.state,
      pos: BlockPos = this.pos,
      mutations: MutableSet<Berry> = this.mutations
   ): BerryMutationOfferEvent {
      return new BerryMutationOfferEvent(berry, world, state, pos, mutations);
   }

   public override fun toString(): String {
      return "BerryMutationOfferEvent(berry=${this.berry}, world=${this.world}, state=${this.state}, pos=${this.pos}, mutations=${this.mutations})";
   }

   public override fun hashCode(): Int {
      return (((this.berry.hashCode() * 31 + this.world.hashCode()) * 31 + this.state.hashCode()) * 31 + this.pos.hashCode()) * 31 + this.mutations.hashCode();
   }

   public override operator fun equals(other: Any?): Boolean {
      if (this === other) {
         return true;
      } else if (other !is BerryMutationOfferEvent) {
         return false;
      } else {
         val var2: BerryMutationOfferEvent = other as BerryMutationOfferEvent;
         if (!(this.berry == (other as BerryMutationOfferEvent).berry)) {
            return false;
         } else if (!(this.world == var2.world)) {
            return false;
         } else if (!(this.state == var2.state)) {
            return false;
         } else if (!(this.pos == var2.pos)) {
            return false;
         } else {
            return this.mutations == var2.mutations;
         }
      }
   }
}
