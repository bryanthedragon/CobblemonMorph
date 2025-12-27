package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.battle

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ClientNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.CobblemonClient
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle.ActiveClientBattlePokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle.ClientBattle
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle.ClientBattleActor
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle.ClientBattlePokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleInitializePacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleReplacePokemonPacket
import net.minecraft.client.Minecraft

public object BattleReplacePokemonHandler : ClientNetworkPacketHandler<BattleReplacePokemonPacket> {
   public open fun handle(packet: BattleReplacePokemonPacket, client: Minecraft) {
      val var10000: ClientBattle = CobblemonClient.INSTANCE.getBattle();
      if (var10000 != null) {
         val var4: Pair = var10000.getPokemonFromPNX(packet.getPnx());
         val actor: ClientBattleActor = var4.component1() as ClientBattleActor;
         val activeBattlePokemon: ActiveClientBattlePokemon = var4.component2() as ActiveClientBattlePokemon;
         val `$this$handle_u24lambda_u241`: BattleInitializePacket.ActiveBattlePokemonDTO = packet.getRealPokemon();
         val var9: ClientBattlePokemon = new ClientBattlePokemon(
            `$this$handle_u24lambda_u241`.getUuid(),
            `$this$handle_u24lambda_u241`.getDisplayName(),
            `$this$handle_u24lambda_u241`.getProperties(),
            `$this$handle_u24lambda_u241`.getAspects(),
            `$this$handle_u24lambda_u241`.getHpValue(),
            `$this$handle_u24lambda_u241`.getMaxHp(),
            packet.isAlly(),
            `$this$handle_u24lambda_u241`.getStatus(),
            `$this$handle_u24lambda_u241`.getStatChanges()
         );
         var9.setActor(actor);
         activeBattlePokemon.setBattlePokemon(var9);
      }
   }

   fun handleOnNettyThread(packet: BattleReplacePokemonPacket) {
      ClientNetworkPacketHandler.DefaultImpls.handleOnNettyThread(this, packet);
   }
}
