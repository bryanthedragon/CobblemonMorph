package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.platform.events

import net.minecraft.server.MinecraftServer

public interface ServerEvent {
   public val server: MinecraftServer

   public data Started(server: MinecraftServer) : ServerEvent {
      public open val server: MinecraftServer

      init {
         this.server = server;
      }

      public operator fun component1(): MinecraftServer {
         return this.server;
      }

      public fun copy(server: MinecraftServer = this.server): bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.platform.events.ServerEvent.Started {
         return new ServerEvent.Started(server);
      }

      public override fun toString(): String {
         return "Started(server=${this.server})";
      }

      public override fun hashCode(): Int {
         return this.server.hashCode();
      }

      public override operator fun equals(other: Any?): Boolean {
         if (this === other) {
            return true;
         } else if (other !is ServerEvent.Started) {
            return false;
         } else {
            return this.server == (other as ServerEvent.Started).server;
         }
      }
   }

   public data Starting(server: MinecraftServer) : ServerEvent {
      public open val server: MinecraftServer

      init {
         this.server = server;
      }

      public operator fun component1(): MinecraftServer {
         return this.server;
      }

      public fun copy(server: MinecraftServer = this.server): bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.platform.events.ServerEvent.Starting {
         return new ServerEvent.Starting(server);
      }

      public override fun toString(): String {
         return "Starting(server=${this.server})";
      }

      public override fun hashCode(): Int {
         return this.server.hashCode();
      }

      public override operator fun equals(other: Any?): Boolean {
         if (this === other) {
            return true;
         } else if (other !is ServerEvent.Starting) {
            return false;
         } else {
            return this.server == (other as ServerEvent.Starting).server;
         }
      }
   }

   public data Stopped(server: MinecraftServer) : ServerEvent {
      public open val server: MinecraftServer

      init {
         this.server = server;
      }

      public operator fun component1(): MinecraftServer {
         return this.server;
      }

      public fun copy(server: MinecraftServer = this.server): bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.platform.events.ServerEvent.Stopped {
         return new ServerEvent.Stopped(server);
      }

      public override fun toString(): String {
         return "Stopped(server=${this.server})";
      }

      public override fun hashCode(): Int {
         return this.server.hashCode();
      }

      public override operator fun equals(other: Any?): Boolean {
         if (this === other) {
            return true;
         } else if (other !is ServerEvent.Stopped) {
            return false;
         } else {
            return this.server == (other as ServerEvent.Stopped).server;
         }
      }
   }

   public data Stopping(server: MinecraftServer) : ServerEvent {
      public open val server: MinecraftServer

      init {
         this.server = server;
      }

      public operator fun component1(): MinecraftServer {
         return this.server;
      }

      public fun copy(server: MinecraftServer = this.server): bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.platform.events.ServerEvent.Stopping {
         return new ServerEvent.Stopping(server);
      }

      public override fun toString(): String {
         return "Stopping(server=${this.server})";
      }

      public override fun hashCode(): Int {
         return this.server.hashCode();
      }

      public override operator fun equals(other: Any?): Boolean {
         if (this === other) {
            return true;
         } else if (other !is ServerEvent.Stopping) {
            return false;
         } else {
            return this.server == (other as ServerEvent.Stopping).server;
         }
      }
   }
}
