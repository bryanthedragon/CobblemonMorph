package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.callback

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.callback.PartySelectPokemonDTO
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket;
import java.util.UUID
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.level.Level

public class OpenPartyCallbackPacket(uuid: UUID, title: MutableComponent, pokemon: List<PartySelectPokemonDTO>) : NetworkPacket<OpenPartyCallbackPacket> {
   public open val id: ResourceLocation
   public final val pokemon: List<PartySelectPokemonDTO>
   public final val title: MutableComponent
   public final val uuid: UUID

   init {
      this.uuid = uuid;
      this.title = title;
      this.pokemon = pokemon;
      this.id = ID;
   }

   public override fun encode(buffer: FriendlyByteBuf) {
      buffer.m_130077_(this.uuid);
      buffer.m_130083_(this.title as Component);
      buffer.m_236828_(this.pokemon, OpenPartyCallbackPacket::encode$lambda$0);
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
   fun `encode$lambda$0`(`$buffer`: FriendlyByteBuf, var1: FriendlyByteBuf, v: PartySelectPokemonDTO) {
      v.writeToBuffer(`$buffer`);
   }

   public companion object {
      public final val ID: ResourceLocation

      public fun decode(buffer: FriendlyByteBuf): OpenPartyCallbackPacket {
         val var10002: UUID = buffer.m_130259_();
         val var10003: MutableComponent = buffer.m_130238_().m_6881_();
         val var10004: java.util.List = buffer.m_236845_(OpenPartyCallbackPacket.Companion::decode$lambda$0);
         return new OpenPartyCallbackPacket(var10002, var10003, var10004);
      }

      @JvmStatic
      fun `decode$lambda$0`(`$buffer`: FriendlyByteBuf, var1: FriendlyByteBuf): PartySelectPokemonDTO {
         return new PartySelectPokemonDTO(`$buffer`);
      }
   }
}
