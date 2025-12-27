package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.battle

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ClientNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.CobblemonClient
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle.ActiveClientBattlePokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle.ClientBattle
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle.ClientBattlePokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleInitializePacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleTransformPokemonPacket
import net.minecraft.client.Minecraft

public object BattleTransformPokemonHandler : ClientNetworkPacketHandler<BattleTransformPokemonPacket> {
   public open fun handle(packet: BattleTransformPokemonPacket, client: Minecraft) {
      val var10000: ClientBattle = CobblemonClient.INSTANCE.getBattle();
      if (var10000 != null) {
         val activeBattlePokemon: ActiveClientBattlePokemon = var10000.getPokemonFromPNX(packet.getPnx()).component2() as ActiveClientBattlePokemon;
         val update: BattleInitializePacket.ActiveBattlePokemonDTO = packet.getUpdatedPokemon();
         val var9: ClientBattlePokemon = activeBattlePokemon.getBattlePokemon();
         if (var9 != null) {
            var9.setDisplayName(update.getDisplayName());
            var9.setProperties(update.getProperties());
            var9.setAspects(update.getAspects());
            var9.setHpValue(update.getHpValue());
            var9.setMaxHp(update.getMaxHp());
            var9.setHpFlat(update.isFlatHp());
            var9.setStatus(update.getStatus());
            var9.setStatChanges(update.getStatChanges());
         }
      }
   }

   fun handleOnNettyThread(packet: BattleTransformPokemonPacket) {
      ClientNetworkPacketHandler.DefaultImpls.handleOnNettyThread(this, packet);
   }
}
