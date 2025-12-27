package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonProperties
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleInitializePacket.ActiveBattlePokemonDTO
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.level.Level

public class BattleTransformPokemonPacket(pnx: String, updatedPokemon: ActiveBattlePokemonDTO, isAlly: Boolean) : NetworkPacket<BattleTransformPokemonPacket> {
   public open val id: ResourceLocation
   public final val isAlly: Boolean
   public final val pnx: String
   public final val updatedPokemon: ActiveBattlePokemonDTO

   init {
      this.pnx = pnx;
      this.updatedPokemon = updatedPokemon;
      this.isAlly = isAlly;
      this.id = ID;
   }

   public constructor(pnx: String, updatedPokemon: BattlePokemon, isAlly: Boolean) : this(
         pnx,
         BattleInitializePacket.ActiveBattlePokemonDTO.Companion.fromPokemon$default(
            BattleInitializePacket.ActiveBattlePokemonDTO.Companion, updatedPokemon, isAlly, null, 4, null
         ),
         isAlly
      )
   public constructor(pnx: String, updatedPokemon: BattlePokemon, mock: PokemonProperties, isAlly: Boolean) : this(
         pnx, BattleInitializePacket.ActiveBattlePokemonDTO.Companion.fromMock(updatedPokemon, isAlly, mock), isAlly
      )
   public override fun encode(buffer: FriendlyByteBuf) {
      buffer.m_130070_(this.pnx);
      this.updatedPokemon.saveToBuffer(buffer);
      buffer.writeBoolean(this.isAlly);
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

   public companion object {
      public final val ID: ResourceLocation

      public fun decode(buffer: FriendlyByteBuf): BattleTransformPokemonPacket {
         val var10002: java.lang.String = buffer.m_130277_();
         return new BattleTransformPokemonPacket(var10002, BattleInitializePacket.ActiveBattlePokemonDTO.Companion.loadFromBuffer(buffer), buffer.readBoolean());
      }
   }
}
