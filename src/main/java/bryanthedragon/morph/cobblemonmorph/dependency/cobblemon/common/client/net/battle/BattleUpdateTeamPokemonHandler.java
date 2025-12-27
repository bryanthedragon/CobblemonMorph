package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.battle

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ClientNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.CobblemonClient
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle.ClientBattle
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle.ClientBattleActor
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleUpdateTeamPokemonPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import java.util.UUID
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.client.Minecraft
import net.minecraft.client.player.LocalPlayer

@SourceDebugExtension(["SMAP\nBattleUpdateTeamPokemonHandler.kt\nKotlin\n*S Kotlin\n*F\n+ 1 BattleUpdateTeamPokemonHandler.kt\ncom/cobblemon/mod/common/client/net/battle/BattleUpdateTeamPokemonHandler\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,28:1\n1#2:29\n*E\n"])
public object BattleUpdateTeamPokemonHandler : ClientNetworkPacketHandler<BattleUpdateTeamPokemonPacket> {
   public open fun handle(packet: BattleUpdateTeamPokemonPacket, client: Minecraft) {
      var var10000: ClientBattle = CobblemonClient.INSTANCE.getBattle();
      if (var10000 != null) {
         val var7: java.util.Iterator = var10000.getSide1().getActors().iterator();

         while (true) {
            if (var7.hasNext()) {
               val var8: Any = var7.next();
               val var17: UUID = (var8 as ClientBattleActor).getUuid();
               val var10001: LocalPlayer = Minecraft.m_91087_().f_91074_;
               if (!(var17 == (if (var10001 != null) var10001.m_20148_() else null))) {
                  continue;
               }

               var10000 = (ClientBattle)var8;
               break;
            }

            var10000 = null;
            break;
         }

         val actor: ClientBattleActor = var10000 as ClientBattleActor;
         if (var10000 as ClientBattleActor != null) {
            val var13: java.util.Iterator = actor.getPokemon().iterator();

            while (true) {
               if (!var13.hasNext()) {
                  var10000 = null;
                  break;
               }

               val var14: Any = var13.next();
               if ((var14 as Pokemon).getUuid() == packet.getPokemon().getUuid()) {
                  var10000 = (ClientBattle)var14;
                  break;
               }
            }

            val previous: Pokemon = var10000 as Pokemon;
            if (var10000 as Pokemon != null) {
               actor.getPokemon().add(actor.getPokemon().indexOf(previous), packet.getPokemon().create());
               actor.getPokemon().remove(previous);
            }
         }
      }
   }

   fun handleOnNettyThread(packet: BattleUpdateTeamPokemonPacket) {
      ClientNetworkPacketHandler.DefaultImpls.handleOnNettyThread(this, packet);
   }
}
