package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.battle

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ClientNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.CobblemonClient
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle.ActiveClientBattlePokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle.ClientBattle
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle.animations.MoveTileOffscreenAnimation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.battle.BattleOverlay
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleCaptureEndPacket
import java.util.concurrent.ConcurrentLinkedQueue
import kotlin.jvm.functions.Function0
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.client.Minecraft

@SourceDebugExtension(["SMAP\nBattleCaptureEndHandler.kt\nKotlin\n*S Kotlin\n*F\n+ 1 BattleCaptureEndHandler.kt\ncom/cobblemon/mod/common/client/net/battle/BattleCaptureEndHandler\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,27:1\n1#2:28\n*E\n"])
public object BattleCaptureEndHandler : ClientNetworkPacketHandler<BattleCaptureEndPacket> {
   public open fun handle(packet: BattleCaptureEndPacket, client: Minecraft) {
      val var10000: ClientBattle = CobblemonClient.INSTANCE.getBattle();
      if (var10000 != null) {
         val overlay: BattleOverlay = CobblemonClient.INSTANCE.getBattleOverlay();
         val activeBattlePokemon: ActiveClientBattlePokemon = var10000.getPokemonFromPNX(packet.getTargetPNX()).component2() as ActiveClientBattlePokemon;
         if (packet.getSucceeded()) {
            val var10: ConcurrentLinkedQueue = activeBattlePokemon.getAnimations();
            val var6: MoveTileOffscreenAnimation = new MoveTileOffscreenAnimation(0.0F, 1, null);
            overlay.after(var6.getDuration(), (new Function0<Unit>(activeBattlePokemon) {
               {
                  super(0);
                  this.$activeBattlePokemon = `$activeBattlePokemon`;
               }

               public final void invoke() {
                  this.$activeBattlePokemon.setBallCapturing(null);
               }
            }) as () -> Unit);
            var10.add(var6);
         }

         activeBattlePokemon.setBallCapturing(null);
      }
   }

   fun handleOnNettyThread(packet: BattleCaptureEndPacket) {
      ClientNetworkPacketHandler.DefaultImpls.handleOnNettyThread(this, packet);
   }
}
