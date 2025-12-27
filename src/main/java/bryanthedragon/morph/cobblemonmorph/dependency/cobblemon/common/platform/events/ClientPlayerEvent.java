package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.platform.events

import net.minecraft.client.player.LocalPlayer

public interface ClientPlayerEvent {
   public val player: LocalPlayer

   public data Login(player: LocalPlayer) : ClientPlayerEvent {
      public open val player: LocalPlayer

      init {
         this.player = player;
      }

      public operator fun component1(): LocalPlayer {
         return this.player;
      }

      public fun copy(player: LocalPlayer = this.player): bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.platform.events.ClientPlayerEvent.Login {
         return new ClientPlayerEvent.Login(player);
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
         } else if (other !is ClientPlayerEvent.Login) {
            return false;
         } else {
            return this.player == (other as ClientPlayerEvent.Login).player;
         }
      }
   }

   public data Logout(player: LocalPlayer) : ClientPlayerEvent {
      public open val player: LocalPlayer

      init {
         this.player = player;
      }

      public operator fun component1(): LocalPlayer {
         return this.player;
      }

      public fun copy(player: LocalPlayer = this.player): bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.platform.events.ClientPlayerEvent.Logout {
         return new ClientPlayerEvent.Logout(player);
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
         } else if (other !is ClientPlayerEvent.Logout) {
            return false;
         } else {
            return this.player == (other as ClientPlayerEvent.Logout).player;
         }
      }
   }
}
