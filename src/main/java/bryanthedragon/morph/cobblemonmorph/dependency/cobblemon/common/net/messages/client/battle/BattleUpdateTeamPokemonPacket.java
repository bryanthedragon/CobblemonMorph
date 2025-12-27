package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.PokemonDTO
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.level.Level

public class BattleUpdateTeamPokemonPacket(pokemon: PokemonDTO) : NetworkPacket<BattleUpdateTeamPokemonPacket> {
   public open val id: ResourceLocation
   public final val pokemon: PokemonDTO

   init {
      this.pokemon = pokemon;
      this.id = ID;
   }

   public constructor(pokemon: Pokemon) : this(new PokemonDTO(pokemon, true))
   public override fun encode(buffer: FriendlyByteBuf) {
      this.pokemon.encode(buffer);
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

   @SourceDebugExtension(["SMAP\nBattleUpdateTeamPokemonPacket.kt\nKotlin\n*S Kotlin\n*F\n+ 1 BattleUpdateTeamPokemonPacket.kt\ncom/cobblemon/mod/common/net/messages/client/battle/BattleUpdateTeamPokemonPacket$Companion\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,37:1\n1#2:38\n*E\n"])
   public companion object {
      public final val ID: ResourceLocation

      public fun decode(buffer: FriendlyByteBuf): BattleUpdateTeamPokemonPacket {
         val var2: PokemonDTO = new PokemonDTO();
         var2.decode(buffer);
         return new BattleUpdateTeamPokemonPacket(var2);
      }
   }
}
