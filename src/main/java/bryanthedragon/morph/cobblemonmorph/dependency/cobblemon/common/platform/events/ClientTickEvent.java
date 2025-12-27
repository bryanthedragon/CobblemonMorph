package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.platform.events

import net.minecraft.client.Minecraft

public interface ClientTickEvent {
   public val client: Minecraft

   public data Post(client: Minecraft) : ClientTickEvent {
      public open val client: Minecraft

      init {
         this.client = client;
      }

      public operator fun component1(): Minecraft {
         return this.client;
      }

      public fun copy(client: Minecraft = this.client): bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.platform.events.ClientTickEvent.Post {
         return new ClientTickEvent.Post(client);
      }

      public override fun toString(): String {
         return "Post(client=${this.client})";
      }

      public override fun hashCode(): Int {
         return this.client.hashCode();
      }

      public override operator fun equals(other: Any?): Boolean {
         if (this === other) {
            return true;
         } else if (other !is ClientTickEvent.Post) {
            return false;
         } else {
            return this.client == (other as ClientTickEvent.Post).client;
         }
      }
   }

   public data Pre(client: Minecraft) : ClientTickEvent {
      public open val client: Minecraft

      init {
         this.client = client;
      }

      public operator fun component1(): Minecraft {
         return this.client;
      }

      public fun copy(client: Minecraft = this.client): bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.platform.events.ClientTickEvent.Pre {
         return new ClientTickEvent.Pre(client);
      }

      public override fun toString(): String {
         return "Pre(client=${this.client})";
      }

      public override fun hashCode(): Int {
         return this.client.hashCode();
      }

      public override operator fun equals(other: Any?): Boolean {
         if (this === other) {
            return true;
         } else if (other !is ClientTickEvent.Pre) {
            return false;
         } else {
            return this.client == (other as ClientTickEvent.Pre).client;
         }
      }
   }
}
