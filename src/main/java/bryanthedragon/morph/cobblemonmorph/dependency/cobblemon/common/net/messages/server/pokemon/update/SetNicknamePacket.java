package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.pokemon.update

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket;
import java.util.UUID
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.level.Level

public class SetNicknamePacket(pokemonUUID: UUID, isParty: Boolean, nickname: String?) : NetworkPacket<SetNicknamePacket> {
   public open val id: ResourceLocation
   public final val isParty: Boolean
   public final val nickname: String?
   public final val pokemonUUID: UUID

   init {
      this.pokemonUUID = pokemonUUID;
      this.isParty = isParty;
      this.nickname = nickname;
      this.id = ID;
   }

   public override fun encode(buffer: FriendlyByteBuf) {
      buffer.m_130077_(this.pokemonUUID);
      buffer.writeBoolean(this.isParty);
      buffer.m_236821_(this.nickname, SetNicknamePacket::encode$lambda$0);
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
   fun `encode$lambda$0`(`$buffer`: FriendlyByteBuf, `this$0`: SetNicknamePacket, var2: FriendlyByteBuf, v: java.lang.String) {
      `$buffer`.m_130070_(`this$0`.nickname);
   }

   public companion object {
      public final val ID: ResourceLocation

      public fun decode(buffer: FriendlyByteBuf): SetNicknamePacket {
         val var10002: UUID = buffer.m_130259_();
         return new SetNicknamePacket(var10002, buffer.readBoolean(), buffer.m_236868_(SetNicknamePacket.Companion::decode$lambda$0) as java.lang.String);
      }

      @JvmStatic
      fun `decode$lambda$0`(`$buffer`: FriendlyByteBuf, it: FriendlyByteBuf): java.lang.String {
         return `$buffer`.m_130277_();
      }
   }
}
