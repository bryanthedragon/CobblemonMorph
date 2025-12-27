package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.platform.events

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.Cancelable
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.InteractionHand
import net.minecraft.world.entity.Entity
import net.minecraft.world.item.ItemStack

public interface ServerPlayerEvent {
   public val player: ServerPlayer

   public data Death(player: ServerPlayer) : Cancelable, ServerPlayerEvent {
      public open val player: ServerPlayer

      init {
         this.player = player;
      }

      public operator fun component1(): ServerPlayer {
         return this.player;
      }

      public fun copy(player: ServerPlayer = this.player): bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.platform.events.ServerPlayerEvent.Death {
         return new ServerPlayerEvent.Death(player);
      }

      public override fun toString(): String {
         return "Death(player=${this.player})";
      }

      public override fun hashCode(): Int {
         return this.player.hashCode();
      }

      public override operator fun equals(other: Any?): Boolean {
         if (this === other) {
            return true;
         } else if (other !is ServerPlayerEvent.Death) {
            return false;
         } else {
            return this.player == (other as ServerPlayerEvent.Death).player;
         }
      }
   }

   public data Login(player: ServerPlayer) : ServerPlayerEvent {
      public open val player: ServerPlayer

      init {
         this.player = player;
      }

      public operator fun component1(): ServerPlayer {
         return this.player;
      }

      public fun copy(player: ServerPlayer = this.player): bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.platform.events.ServerPlayerEvent.Login {
         return new ServerPlayerEvent.Login(player);
      }

      public override fun toString(): String {
         return "Login(player=${this.player})";
      }

      public override fun hashCode(): Int {
         return this.player.hashCode();
      }

      public override operator fun equals(other: Any?): Boolean {
         if (this === other) {
            return true;
         } else if (other !is ServerPlayerEvent.Login) {
            return false;
         } else {
            return this.player == (other as ServerPlayerEvent.Login).player;
         }
      }
   }

   public data Logout(player: ServerPlayer) : ServerPlayerEvent {
      public open val player: ServerPlayer

      init {
         this.player = player;
      }

      public operator fun component1(): ServerPlayer {
         return this.player;
      }

      public fun copy(player: ServerPlayer = this.player): bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.platform.events.ServerPlayerEvent.Logout {
         return new ServerPlayerEvent.Logout(player);
      }

      public override fun toString(): String {
         return "Logout(player=${this.player})";
      }

      public override fun hashCode(): Int {
         return this.player.hashCode();
      }

      public override operator fun equals(other: Any?): Boolean {
         if (this === other) {
            return true;
         } else if (other !is ServerPlayerEvent.Logout) {
            return false;
         } else {
            return this.player == (other as ServerPlayerEvent.Logout).player;
         }
      }
   }

   public data RightClickBlock(player: ServerPlayer, pos: BlockPos, hand: InteractionHand, face: Direction?) : Cancelable, ServerPlayerEvent {
      public final val face: Direction?
      public final val hand: InteractionHand
      public open val player: ServerPlayer
      public final val pos: BlockPos

      init {
         this.player = player;
         this.pos = pos;
         this.hand = hand;
         this.face = face;
      }

      public operator fun component1(): ServerPlayer {
         return this.player;
      }

      public operator fun component2(): BlockPos {
         return this.pos;
      }

      public operator fun component3(): InteractionHand {
         return this.hand;
      }

      public operator fun component4(): Direction? {
         return this.face;
      }

      public fun copy(player: ServerPlayer = this.player, pos: BlockPos = this.pos, hand: InteractionHand = this.hand, face: Direction? = this.face): bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.platform.events.ServerPlayerEvent.RightClickBlock {
         return new ServerPlayerEvent.RightClickBlock(player, pos, hand, face);
      }

      public override fun toString(): String {
         return "RightClickBlock(player=${this.player}, pos=${this.pos}, hand=${this.hand}, face=${this.face})";
      }

      public override fun hashCode(): Int {
         return ((this.player.hashCode() * 31 + this.pos.hashCode()) * 31 + this.hand.hashCode()) * 31 + (if (this.face == null) 0 else this.face.hashCode());
      }

      public override operator fun equals(other: Any?): Boolean {
         if (this === other) {
            return true;
         } else if (other !is ServerPlayerEvent.RightClickBlock) {
            return false;
         } else {
            val var2: ServerPlayerEvent.RightClickBlock = other as ServerPlayerEvent.RightClickBlock;
            if (!(this.player == (other as ServerPlayerEvent.RightClickBlock).player)) {
               return false;
            } else if (!(this.pos == var2.pos)) {
               return false;
            } else if (this.hand != var2.hand) {
               return false;
            } else {
               return this.face === var2.face;
            }
         }
      }
   }

   public data RightClickEntity(player: ServerPlayer, item: ItemStack, hand: InteractionHand, entity: Entity) : Cancelable, ServerPlayerEvent {
      public final val entity: Entity
      public final val hand: InteractionHand
      public final val item: ItemStack
      public open val player: ServerPlayer

      init {
         this.player = player;
         this.item = item;
         this.hand = hand;
         this.entity = entity;
      }

      public operator fun component1(): ServerPlayer {
         return this.player;
      }

      public operator fun component2(): ItemStack {
         return this.item;
      }

      public operator fun component3(): InteractionHand {
         return this.hand;
      }

      public operator fun component4(): Entity {
         return this.entity;
      }

      public fun copy(player: ServerPlayer = this.player, item: ItemStack = this.item, hand: InteractionHand = this.hand, entity: Entity = this.entity): bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.platform.events.ServerPlayerEvent.RightClickEntity {
         return new ServerPlayerEvent.RightClickEntity(player, item, hand, entity);
      }

      public override fun toString(): String {
         return "RightClickEntity(player=${this.player}, item=${this.item}, hand=${this.hand}, entity=${this.entity})";
      }

      public override fun hashCode(): Int {
         return ((this.player.hashCode() * 31 + this.item.hashCode()) * 31 + this.hand.hashCode()) * 31 + this.entity.hashCode();
      }

      public override operator fun equals(other: Any?): Boolean {
         if (this === other) {
            return true;
         } else if (other !is ServerPlayerEvent.RightClickEntity) {
            return false;
         } else {
            val var2: ServerPlayerEvent.RightClickEntity = other as ServerPlayerEvent.RightClickEntity;
            if (!(this.player == (other as ServerPlayerEvent.RightClickEntity).player)) {
               return false;
            } else if (!(this.item == var2.item)) {
               return false;
            } else if (this.hand != var2.hand) {
               return false;
            } else {
               return this.entity == var2.entity;
            }
         }
      }
   }
}
