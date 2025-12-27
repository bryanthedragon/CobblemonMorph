package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.berry

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.berry.Berry
import net.minecraft.core.BlockPos
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.state.BlockState

public data BerryMutationResultEvent(berry: Berry, world: Level, state: BlockState, pos: BlockPos, mutations: Set<Berry>, pickedMutation: Berry?) :
   BerryEvent {
   public open val berry: Berry
   public final val mutations: Set<Berry>
   public final var pickedMutation: Berry?
   public final val pos: BlockPos
   public final val state: BlockState
   public final val world: Level

   init {
      this.berry = berry;
      this.world = world;
      this.state = state;
      this.pos = pos;
      this.mutations = mutations;
      this.pickedMutation = pickedMutation;
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

   public operator fun component5(): Set<Berry> {
      return this.mutations;
   }

   public operator fun component6(): Berry? {
      return this.pickedMutation;
   }

   public fun copy(
      berry: Berry = this.berry,
      world: Level = this.world,
      state: BlockState = this.state,
      pos: BlockPos = this.pos,
      mutations: Set<Berry> = this.mutations,
      pickedMutation: Berry? = this.pickedMutation
   ): BerryMutationResultEvent {
      return new BerryMutationResultEvent(berry, world, state, pos, mutations, pickedMutation);
   }

   public override fun toString(): String {
      return "BerryMutationResultEvent(berry=${this.berry}, world=${this.world}, state=${this.state}, pos=${this.pos}, mutations=${this.mutations}, pickedMutation=${this.pickedMutation})";
   }

   public override fun hashCode(): Int {
      return ((((this.berry.hashCode() * 31 + this.world.hashCode()) * 31 + this.state.hashCode()) * 31 + this.pos.hashCode()) * 31 + this.mutations.hashCode())
            * 31
         + (if (this.pickedMutation == null) 0 else this.pickedMutation.hashCode());
   }

   public override operator fun equals(other: Any?): Boolean {
      if (this === other) {
         return true;
      } else if (other !is BerryMutationResultEvent) {
         return false;
      } else {
         val var2: BerryMutationResultEvent = other as BerryMutationResultEvent;
         if (!(this.berry == (other as BerryMutationResultEvent).berry)) {
            return false;
         } else if (!(this.world == var2.world)) {
            return false;
         } else if (!(this.state == var2.state)) {
            return false;
         } else if (!(this.pos == var2.pos)) {
            return false;
         } else if (!(this.mutations == var2.mutations)) {
            return false;
         } else {
            return this.pickedMutation == var2.pickedMutation;
         }
      }
   }
}
