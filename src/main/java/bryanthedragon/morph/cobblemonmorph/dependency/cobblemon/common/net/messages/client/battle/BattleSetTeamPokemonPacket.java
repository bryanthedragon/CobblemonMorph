package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle;

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

@SourceDebugExtension(["SMAP\nBattleSetTeamPokemonPacket.kt\nKotlin\n*S Kotlin\n*F\n+ 1 BattleSetTeamPokemonPacket.kt\ncom/cobblemon/mod/common/net/messages/client/battle/BattleSetTeamPokemonPacket\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,40:1\n1549#2:41\n1620#2,3:42\n*S KotlinDebug\n*F\n+ 1 BattleSetTeamPokemonPacket.kt\ncom/cobblemon/mod/common/net/messages/client/battle/BattleSetTeamPokemonPacket\n*L\n31#1:41\n31#1:42,3\n*E\n"])
public class BattleSetTeamPokemonPacket(team: List<PokemonDTO>) : NetworkPacket<BattleSetTeamPokemonPacket> {
   public open val id: ResourceLocation
   public final val team: List<PokemonDTO>

   init {
      this.team = team;
      this.id = ID;
   }

   public constructor(team: Collection<Pokemon>)  {
      val `$this$map$iv`: java.lang.Iterable = team;
      val `destination$iv$iv`: java.util.Collection = new ArrayList(CollectionsKt.collectionSizeOrDefault(team, 10));

      for (Object item$iv$iv : $this$map$iv) {
         `destination$iv$iv`.add(new PokemonDTO(`item$iv$iv` as Pokemon, true));
      }

      this(`destination$iv$iv` as MutableList<PokemonDTO>);
   }

   public override fun encode(buffer: FriendlyByteBuf) {
      buffer.m_236828_(this.team, BattleSetTeamPokemonPacket::encode$lambda$1);
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

   @SourceDebugExtension(["SMAP\nBattleSetTeamPokemonPacket.kt\nKotlin\n*S Kotlin\n*F\n+ 1 BattleSetTeamPokemonPacket.kt\ncom/cobblemon/mod/common/net/messages/client/battle/BattleSetTeamPokemonPacket$Companion\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,40:1\n1#2:41\n*E\n"])
   public companion object {
      public final val ID: ResourceLocation

      public fun decode(buffer: FriendlyByteBuf): BattleSetTeamPokemonPacket {
         val var10002: java.util.List = buffer.m_236845_(BattleSetTeamPokemonPacket.Companion::decode$lambda$1);
         return new BattleSetTeamPokemonPacket(var10002);
      }

      @JvmStatic
      fun `decode$lambda$1`(it: FriendlyByteBuf): PokemonDTO {
         val var1: PokemonDTO = new PokemonDTO();
         var1.decode(it);
         return var1;
      }
   }
}
