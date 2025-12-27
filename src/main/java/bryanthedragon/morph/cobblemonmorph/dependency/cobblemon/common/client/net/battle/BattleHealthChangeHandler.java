package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.battle

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ClientNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.CobblemonClient
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle.ActiveClientBattlePokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle.ClientBattle
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle.ClientBattlePokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle.animations.HealthChangeAnimation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleHealthChangePacket
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.client.Minecraft

@SourceDebugExtension(["SMAP\nBattleHealthChangeHandler.kt\nKotlin\n*S Kotlin\n*F\n+ 1 BattleHealthChangeHandler.kt\ncom/cobblemon/mod/common/client/net/battle/BattleHealthChangeHandler\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,26:1\n1#2:27\n*E\n"])
public object BattleHealthChangeHandler : ClientNetworkPacketHandler<BattleHealthChangePacket> {
   public open fun handle(packet: BattleHealthChangePacket, client: Minecraft) {
      val var10000: ClientBattle = CobblemonClient.INSTANCE.getBattle();
      if (var10000 != null) {
         val activePokemon: ActiveClientBattlePokemon = var10000.getPokemonFromPNX(packet.getPnx()).component2() as ActiveClientBattlePokemon;
         val var7: java.lang.Float = packet.getNewMaxHealth();
         if (var7 != null) {
            val it: Float = var7.floatValue();
            val var8: ClientBattlePokemon = activePokemon.getBattlePokemon();
            if (var8 != null) {
               var8.setMaxHp(it);
            }
         }

         activePokemon.getAnimations().add(new HealthChangeAnimation(packet.getNewHealth(), 0.0F, 2, null));
      }
   }

   fun handleOnNettyThread(packet: BattleHealthChangePacket) {
      ClientNetworkPacketHandler.DefaultImpls.handleOnNettyThread(this, packet);
   }
}
