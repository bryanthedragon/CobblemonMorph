package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.battle

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ClientNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.CobblemonClient
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle.ClientBattle
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle.ClientBattleActor
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.PokemonDTO
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleSetTeamPokemonPacket
import java.util.ArrayList;
import java.util.UUID
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.client.Minecraft
import net.minecraft.client.player.LocalPlayer

@SourceDebugExtension(["SMAP\nBattleSetTeamPokemonHandler.kt\nKotlin\n*S Kotlin\n*F\n+ 1 BattleSetTeamPokemonHandler.kt\ncom/cobblemon/mod/common/client/net/battle/BattleSetTeamPokemonHandler\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,22:1\n1#2:23\n1549#3:24\n1620#3,3:25\n*S KotlinDebug\n*F\n+ 1 BattleSetTeamPokemonHandler.kt\ncom/cobblemon/mod/common/client/net/battle/BattleSetTeamPokemonHandler\n*L\n20#1:24\n20#1:25,3\n*E\n"])
public object BattleSetTeamPokemonHandler : ClientNetworkPacketHandler<BattleSetTeamPokemonPacket> {
   public open fun handle(packet: BattleSetTeamPokemonPacket, client: Minecraft) {
      var var10000: ClientBattle = CobblemonClient.INSTANCE.getBattle();
      val `$this$mapTo$iv$iv`: java.util.Iterator = var10000.getSide1().getActors().iterator();

      while (true) {
         if (`$this$mapTo$iv$iv`.hasNext()) {
            val `destination$iv$iv`: Any = `$this$mapTo$iv$iv`.next();
            val var20: UUID = (`destination$iv$iv` as ClientBattleActor).getUuid();
            val var10001: LocalPlayer = Minecraft.m_91087_().f_91074_;
            if (!(var20 == (if (var10001 != null) var10001.m_20148_() else null))) {
               continue;
            }

            var10000 = (ClientBattle)`destination$iv$iv`;
            break;
         }

         var10000 = null;
         break;
      }

      val var3: ClientBattleActor = var10000 as ClientBattleActor;
      if (var10000 as ClientBattleActor != null) {
         val `$this$map$iv`: java.lang.Iterable = packet.getTeam();
         val var16: java.util.Collection = new ArrayList(CollectionsKt.collectionSizeOrDefault(`$this$map$iv`, 10));

         for (Object item$iv$iv : $this$map$iv) {
            var16.add((`item$iv$iv` as PokemonDTO).create());
         }

         var3.setPokemon(CollectionsKt.toMutableList(var16 as java.util.List));
      }
   }

   fun handleOnNettyThread(packet: BattleSetTeamPokemonPacket) {
      ClientNetworkPacketHandler.DefaultImpls.handleOnNettyThread(this, packet);
   }
}
