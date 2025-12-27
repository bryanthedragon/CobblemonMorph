package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.platform.events

import net.minecraft.server.MinecraftServer

public interface ServerTickEvent {
   public val server: MinecraftServer

   public data Post(server: MinecraftServer) : ServerTickEvent {
      public open val server: MinecraftServer

      init {
         this.server = server;
      }

      public operator fun component1(): MinecraftServer {
         return this.server;
      }

      public fun copy(server: MinecraftServer = this.server): bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.platform.events.ServerTickEvent.Post {
         return new ServerTickEvent.Post(server);
      }

      public override fun toString(): String {
         return "Post(server=${this.server})";
      }

      public override fun hashCode(): Int {
         return this.server.hashCode();
      }

      public override operator fun equals(other: Any?): Boolean {
         if (this === other) {
            return true;
         } else if (other !is ServerTickEvent.Post) {
            return false;
         } else {
            return this.server == (other as ServerTickEvent.Post).server;
         }
      }
   }

   public data Pre(server: MinecraftServer) : ServerTickEvent {
      public open val server: MinecraftServer

      init {
         this.server = server;
      }

      public operator fun component1(): MinecraftServer {
         return this.server;
      }

      public fun copy(server: MinecraftServer = this.server): bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.platform.events.ServerTickEvent.Pre {
         return new ServerTickEvent.Pre(server);
      }

      public override fun toString(): String {
         return "Pre(server=${this.server})";
      }

      public override fun hashCode(): Int {
         return this.server.hashCode();
      }

      public override operator fun equals(other: Any?): Boolean {
         if (this === other) {
            return true;
         } else if (other !is ServerTickEvent.Pre) {
            return false;
         } else {
            return this.server == (other as ServerTickEvent.Pre).server;
         }
      }
   }
}
