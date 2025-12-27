package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.ui

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.PokemonDTO
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import java.util.ArrayList;
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.level.Level

@SourceDebugExtension(["SMAP\nSummaryUIPacket.kt\nKotlin\n*S Kotlin\n*F\n+ 1 SummaryUIPacket.kt\ncom/cobblemon/mod/common/net/messages/client/ui/SummaryUIPacket\n+ 2 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n*L\n1#1,32:1\n11335#2:33\n11670#2,3:34\n*S KotlinDebug\n*F\n+ 1 SummaryUIPacket.kt\ncom/cobblemon/mod/common/net/messages/client/ui/SummaryUIPacket\n*L\n21#1:33\n21#1:34,3\n*E\n"])
public class SummaryUIPacket internal constructor(pokemon: List<PokemonDTO>, editable: Boolean) : NetworkPacket<SummaryUIPacket> {
   public final val editable: Boolean
   public open val id: ResourceLocation
   public final val pokemon: List<PokemonDTO>

   init {
      this.pokemon = pokemon;
      this.editable = editable;
      this.id = ID;
   }

   public constructor(vararg pokemon: Pokemon, editable: Boolean = true)  {
      val `destination$iv$iv`: java.util.Collection = new ArrayList(pokemon.length);

      for (Object item$iv$iv : pokemon) {
         `destination$iv$iv`.add(new PokemonDTO((Pokemon)`item$iv$iv`, true));
      }

      this(`destination$iv$iv` as MutableList<PokemonDTO>, editable);
   }

   public override fun encode(buffer: FriendlyByteBuf) {
      buffer.writeBoolean(this.editable);
      buffer.m_236828_(this.pokemon, SummaryUIPacket::encode$lambda$1);
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
   fun `encode$lambda$1`(pb: FriendlyByteBuf, value: PokemonDTO) {
      value.encode(pb);
   }

   @SourceDebugExtension(["SMAP\nSummaryUIPacket.kt\nKotlin\n*S Kotlin\n*F\n+ 1 SummaryUIPacket.kt\ncom/cobblemon/mod/common/net/messages/client/ui/SummaryUIPacket$Companion\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,32:1\n1#2:33\n*E\n"])
   public companion object {
      public final val ID: ResourceLocation

      public fun decode(buffer: FriendlyByteBuf): SummaryUIPacket {
         val var10002: java.util.List = buffer.m_236845_(SummaryUIPacket.Companion::decode$lambda$1);
         return new SummaryUIPacket(var10002, buffer.readBoolean());
      }

      @JvmStatic
      fun `decode$lambda$1`(it: FriendlyByteBuf): PokemonDTO {
         val var1: PokemonDTO = new PokemonDTO();
         var1.decode(it);
         return var1;
      }
   }
}
