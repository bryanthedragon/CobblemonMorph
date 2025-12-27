package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleInitializePacket.ActiveBattlePokemonDTO
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.level.Level

public class BattleSwitchPokemonPacket(pnx: String, newPokemon: ActiveBattlePokemonDTO, isAlly: Boolean) : NetworkPacket<BattleSwitchPokemonPacket> {
   public open val id: ResourceLocation
   public final val isAlly: Boolean
   public final val newPokemon: ActiveBattlePokemonDTO
   public final val pnx: String

   init {
      this.pnx = pnx;
      this.newPokemon = newPokemon;
      this.isAlly = isAlly;
      this.id = ID;
   }

   public constructor(pnx: String, newPokemon: BattlePokemon, isAlly: Boolean, illusion: BattlePokemon?) : this(
         pnx, BattleInitializePacket.ActiveBattlePokemonDTO.Companion.fromPokemon(newPokemon, isAlly, illusion), isAlly
      )
   public override fun encode(buffer: FriendlyByteBuf) {
      buffer.m_130070_(this.pnx);
      this.newPokemon.saveToBuffer(buffer);
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

      public fun decode(buffer: FriendlyByteBuf): BattleSwitchPokemonPacket {
         val var10002: java.lang.String = buffer.m_130277_();
         return new BattleSwitchPokemonPacket(var10002, BattleInitializePacket.ActiveBattlePokemonDTO.Companion.loadFromBuffer(buffer), buffer.readBoolean());
      }
   }
}
