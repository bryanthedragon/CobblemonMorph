package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.battle

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ClientNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.PokeBalls
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.CobblemonClient
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle.ActiveClientBattlePokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle.ClientBallDisplay
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle.ClientBattle
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleCaptureStartPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokeball.PokeBall
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.client.Minecraft

@SourceDebugExtension(["SMAP\nBattleCaptureStartHandler.kt\nKotlin\n*S Kotlin\n*F\n+ 1 BattleCaptureStartHandler.kt\ncom/cobblemon/mod/common/client/net/battle/BattleCaptureStartHandler\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,26:1\n1#2:27\n*E\n"])
public object BattleCaptureStartHandler : ClientNetworkPacketHandler<BattleCaptureStartPacket> {
   public open fun handle(packet: BattleCaptureStartPacket, client: Minecraft) {
      val var10000: ClientBattle = CobblemonClient.INSTANCE.getBattle();
      if (var10000 != null) {
         val tile: ActiveClientBattlePokemon = var10000.getPokemonFromPNX(packet.getTargetPNX()).getSecond() as ActiveClientBattlePokemon;
         var var11: PokeBall = PokeBalls.INSTANCE.getPokeBall(packet.getPokeBallType());
         if (var11 == null) {
            var11 = PokeBalls.INSTANCE.getPOKE_BALL();
         }

         val var7: ClientBallDisplay = new ClientBallDisplay(var11, packet.getAspects());
         var7.start();
         tile.setBallCapturing(var7);
      }
   }

   fun handleOnNettyThread(packet: BattleCaptureStartPacket) {
      ClientNetworkPacketHandler.DefaultImpls.handleOnNettyThread(this, packet);
   }
}
