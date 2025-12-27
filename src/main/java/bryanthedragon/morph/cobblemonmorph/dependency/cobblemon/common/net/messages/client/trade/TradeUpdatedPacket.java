package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.trade

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.PokemonDTO
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import java.util.UUID
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.level.Level

public class TradeUpdatedPacket(playerId: UUID, pokemon: Pokemon?) : NetworkPacket<TradeUpdatedPacket> {
   public open val id: ResourceLocation
   public final val playerId: UUID
   public final val pokemon: Pokemon?

   init {
      this.playerId = playerId;
      this.pokemon = pokemon;
      this.id = ID;
   }

   public override fun encode(buffer: FriendlyByteBuf) {
      buffer.m_130077_(this.playerId);
      buffer.m_236821_(this.pokemon, TradeUpdatedPacket::encode$lambda$0);
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
   fun `encode$lambda$0`(`$buffer`: FriendlyByteBuf, var1: FriendlyByteBuf, pokemon: Pokemon) {
      new PokemonDTO(pokemon, true).encode(`$buffer`);
   }

   @SourceDebugExtension(["SMAP\nTradeUpdatedPacket.kt\nKotlin\n*S Kotlin\n*F\n+ 1 TradeUpdatedPacket.kt\ncom/cobblemon/mod/common/net/messages/client/trade/TradeUpdatedPacket$Companion\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,37:1\n1#2:38\n*E\n"])
   public companion object {
      public final val ID: ResourceLocation

      public fun decode(buffer: FriendlyByteBuf): TradeUpdatedPacket {
         val var10002: UUID = buffer.m_130259_();
         return new TradeUpdatedPacket(var10002, buffer.m_236868_(TradeUpdatedPacket.Companion::decode$lambda$1) as Pokemon);
      }

      @JvmStatic
      fun `decode$lambda$1`(`$buffer`: FriendlyByteBuf, it: FriendlyByteBuf): Pokemon {
         val var2: PokemonDTO = new PokemonDTO();
         var2.decode(`$buffer`);
         return var2.create();
      }
   }
}
