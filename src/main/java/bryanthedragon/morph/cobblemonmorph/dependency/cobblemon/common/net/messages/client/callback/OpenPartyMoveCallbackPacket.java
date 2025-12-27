package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.callback

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.callback.MoveSelectDTO
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.callback.PartySelectPokemonDTO
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.IntSize
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.NetExtensionsKt
import io.netty.buffer.ByteBuf
import java.util.ArrayList;
import java.util.UUID
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.level.Level

public class OpenPartyMoveCallbackPacket(uuid: UUID, partyTitle: MutableComponent, pokemonList: List<Pair<PartySelectPokemonDTO, List<MoveSelectDTO>>>) :
   NetworkPacket<OpenPartyMoveCallbackPacket> {
   public open val id: ResourceLocation
   public final val partyTitle: MutableComponent
   public final val pokemonList: List<Pair<PartySelectPokemonDTO, List<MoveSelectDTO>>>
   public final val uuid: UUID

   init {
      this.uuid = uuid;
      this.partyTitle = partyTitle;
      this.pokemonList = pokemonList;
      this.id = ID;
   }

   public override fun encode(buffer: FriendlyByteBuf) {
      buffer.m_130077_(this.uuid);
      buffer.m_130083_(this.partyTitle as Component);
      NetExtensionsKt.writeSizedInt(buffer as ByteBuf, IntSize.U_BYTE, this.pokemonList.size());

      for (Pair var3 : this.pokemonList) {
         val pkDTO: PartySelectPokemonDTO = var3.component1() as PartySelectPokemonDTO;
         val mvDTOs: java.util.List = var3.component2() as java.util.List;
         pkDTO.writeToBuffer(buffer);
         NetExtensionsKt.writeSizedInt(buffer as ByteBuf, IntSize.U_BYTE, mvDTOs.size());

         for (MoveSelectDTO mvDTO : mvDTOs) {
            mvDTO.writeToBuffer(buffer);
         }
      }
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

      public fun decode(buffer: FriendlyByteBuf): OpenPartyMoveCallbackPacket {
         val uuid: UUID = buffer.m_130259_();
         val partyTitle: MutableComponent = buffer.m_130238_().m_6881_();
         val pokemonList: java.util.List = new ArrayList();
         val var5: Int = NetExtensionsKt.readSizedInt(buffer as ByteBuf, IntSize.U_BYTE);

         for (int var6 = 0; var6 < var5; var6++) {
            val pkDTO: PartySelectPokemonDTO = new PartySelectPokemonDTO(buffer);
            val mvDTOs: java.util.List = new ArrayList();
            val var11: Int = NetExtensionsKt.readSizedInt(buffer as ByteBuf, IntSize.U_BYTE);

            for (int var12 = 0; var12 < var11; var12++) {
               mvDTOs.add(new MoveSelectDTO(buffer));
            }

            pokemonList.add(TuplesKt.to(pkDTO, mvDTOs));
         }

         return new OpenPartyMoveCallbackPacket(uuid, partyTitle, pokemonList);
      }
   }
}
