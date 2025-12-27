package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.battle

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ClientNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.sound.battle.BattleMusicController
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.sound.battle.BattleMusicInstance
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleMusicPacket
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.client.Minecraft
import net.minecraft.client.resources.sounds.SoundInstance
import net.minecraft.client.sounds.SoundManager
import net.minecraft.sounds.SoundEvent
import org.apache.logging.log4j.Logger

@SourceDebugExtension(["SMAP\nBattleMusicHandler.kt\nKotlin\n*S Kotlin\n*F\n+ 1 BattleMusicHandler.kt\ncom/cobblemon/mod/common/client/net/battle/BattleMusicHandler\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,42:1\n1#2:43\n*E\n"])
public object BattleMusicHandler : ClientNetworkPacketHandler<BattleMusicPacket> {
   public open fun handle(packet: BattleMusicPacket, client: Minecraft) {
      val soundManager: SoundManager = client.m_91106_();
      val var10000: SoundEvent = packet.getMusic();
      val var8: BattleMusicInstance = if (var10000 != null) new BattleMusicInstance(var10000, packet.getVolume(), packet.getPitch()) else null;
      val currMusic: BattleMusicInstance = BattleMusicController.INSTANCE.getMusic();
      if (var8 == null) {
         BattleMusicController.INSTANCE.endMusic();
      } else if (!soundManager.m_120403_(currMusic as SoundInstance)) {
         BattleMusicController.INSTANCE.initializeMusic(var8);
      } else if (!(var8.m_7904_() == currMusic.m_7904_())) {
         BattleMusicController.INSTANCE.switchMusic(var8);
      } else {
         val var9: Logger = Cobblemon.INSTANCE.getLOGGER();
         val var10001: SoundEvent = packet.getMusic();
         var9.error("Ignored BattleMusicPacket from server: ${if (var10001 != null) var10001.m_11660_() else null}");
      }
   }

   fun handleOnNettyThread(packet: BattleMusicPacket) {
      ClientNetworkPacketHandler.DefaultImpls.handleOnNettyThread(this, packet);
   }
}
