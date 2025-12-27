package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.battle

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ClientNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.CobblemonClient
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle.ActiveClientBattlePokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle.ClientBattle
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle.ClientBattleActor
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle.ClientBattlePokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle.animations.MoveTileOffscreenAnimation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle.animations.SwapAndMoveTileOnscreenAnimation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle.animations.TileAnimation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.PokemonFloatingState
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleInitializePacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleSwitchPokemonPacket
import java.util.UUID
import java.util.concurrent.ConcurrentLinkedQueue
import net.minecraft.client.Minecraft

public object BattleSwitchPokemonHandler : ClientNetworkPacketHandler<BattleSwitchPokemonPacket> {
   public open fun handle(packet: BattleSwitchPokemonPacket, client: Minecraft) {
      val var10000: ClientBattle = CobblemonClient.INSTANCE.getBattle();
      if (var10000 != null) {
         val var4: Pair = var10000.getPokemonFromPNX(packet.getPnx());
         val actor: ClientBattleActor = var4.component1() as ClientBattleActor;
         val activeBattlePokemon: ActiveClientBattlePokemon = var4.component2() as ActiveClientBattlePokemon;
         if ((CollectionsKt.lastOrNull(activeBattlePokemon.getAnimations()) as TileAnimation) !is MoveTileOffscreenAnimation) {
            activeBattlePokemon.getAnimations().add(new MoveTileOffscreenAnimation(0.0F, 1, null));
         }

         val var18: ConcurrentLinkedQueue = activeBattlePokemon.getAnimations();
         val `$this$handle_u24lambda_u241`: BattleInitializePacket.ActiveBattlePokemonDTO = packet.getNewPokemon();
         val var10: ClientBattlePokemon = new ClientBattlePokemon(
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
         var10.setActor(actor);
         var10.setState(new PokemonFloatingState());
         var18.add(new SwapAndMoveTileOnscreenAnimation(var10, 0.0F, 2, null));
         val var10002: UUID = client.m_91094_().m_92548_().getId();
         if (actor == var10000.getParticipatingActor(var10002)) {
            CobblemonClient.INSTANCE.getStorage().switchToPokemon(packet.getNewPokemon().getUuid());
         }
      }
   }

   fun handleOnNettyThread(packet: BattleSwitchPokemonPacket) {
      ClientNetworkPacketHandler.DefaultImpls.handleOnNettyThread(this, packet);
   }
}
