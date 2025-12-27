package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.starter

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.player.PlayerData
import java.util.UUID
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.level.Level

public class SetClientPlayerDataPacket(promptStarter: Boolean,
      starterLocked: Boolean,
      starterSelected: Boolean,
      starterUUID: UUID?,
      resetStarterPrompt: Boolean?
   ) :
   NetworkPacket<SetClientPlayerDataPacket> {
   public open val id: ResourceLocation
   public final val promptStarter: Boolean
   public final val resetStarterPrompt: Boolean?
   public final val starterLocked: Boolean
   public final val starterSelected: Boolean
   public final val starterUUID: UUID?

   init {
      this.promptStarter = promptStarter;
      this.starterLocked = starterLocked;
      this.starterSelected = starterSelected;
      this.starterUUID = starterUUID;
      this.resetStarterPrompt = resetStarterPrompt;
      this.id = ID;
   }

   public constructor(playerData: PlayerData, resetStarterPrompt: Boolean? = null) : this(
         !playerData.getStarterPrompted() || !Cobblemon.INSTANCE.getStarterConfig().getPromptStarterOnceOnly(),
         playerData.getStarterLocked(),
         playerData.getStarterSelected(),
         playerData.getStarterUUID(),
         resetStarterPrompt
      )
   public override fun encode(buffer: FriendlyByteBuf) {
      buffer.writeBoolean(this.promptStarter);
      buffer.writeBoolean(this.starterLocked);
      buffer.writeBoolean(this.starterSelected);
      buffer.m_236821_(this.starterUUID, SetClientPlayerDataPacket::encode$lambda$0);
      buffer.m_236821_(this.resetStarterPrompt, SetClientPlayerDataPacket::encode$lambda$1);
   }

   override fun sendToPlayer(player: ServerPlayer) {
      NetworkPacket.DefaultImpls.sendToPlayer(this, player);
   }

   override fun sendToPlayers(players: MutableIterable<ServerPlayer>) {
      NetworkPacket.DefaultImpls.sendToPlayers(this, players);
   }

   override fun sendToAllPlayers() {
      NetworkPacket.DefaultImpls.sendToAllPlayers(this);
   }

   override fun sendToServer() {
      NetworkPacket.DefaultImpls.sendToServer(this);
   }

   override fun sendToPlayersAround(
      x: Double, y: Double, z: Double, distance: Double, worldKey: ResourceKey<Level>, exclusionCondition: (ServerPlayer?) -> java.lang.Boolean
   ) {
      NetworkPacket.DefaultImpls.sendToPlayersAround(this, x, y, z, distance, worldKey, exclusionCondition);
   }

   override fun toBuffer(): FriendlyByteBuf {
      return NetworkPacket.DefaultImpls.toBuffer(this);
   }

   @JvmStatic
   fun `encode$lambda$0`(pb: FriendlyByteBuf, value: UUID) {
      pb.m_130077_(value);
   }

   @JvmStatic
   fun `encode$lambda$1`(pb: FriendlyByteBuf, value: java.lang.Boolean) {
      pb.writeBoolean(value);
   }

   public companion object {
      public final val ID: ResourceLocation

      public fun decode(buffer: FriendlyByteBuf): SetClientPlayerDataPacket {
         return new SetClientPlayerDataPacket(
            buffer.readBoolean(),
            buffer.readBoolean(),
            buffer.readBoolean(),
            buffer.m_236868_(SetClientPlayerDataPacket.Companion::decode$lambda$0) as UUID,
            buffer.m_236868_(SetClientPlayerDataPacket.Companion::decode$lambda$1) as java.lang.Boolean
         );
      }

      @JvmStatic
      fun `decode$lambda$0`(it: FriendlyByteBuf): UUID {
         return it.m_130259_();
      }

      @JvmStatic
      fun `decode$lambda$1`(it: FriendlyByteBuf): java.lang.Boolean {
         return it.readBoolean();
      }
   }
}
