package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.sound

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ClientNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.sound.UnvalidatedPlaySoundS2CPacket
import net.minecraft.client.Minecraft
import net.minecraft.sounds.SoundEvent
import net.minecraft.world.entity.player.Player

internal object UnvalidatedPlaySoundS2CPacketHandler : ClientNetworkPacketHandler<UnvalidatedPlaySoundS2CPacket> {
   public open fun handle(packet: UnvalidatedPlaySoundS2CPacket, client: Minecraft) {
      client.m_201446_(UnvalidatedPlaySoundS2CPacketHandler::handle$lambda$0);
   }

   fun handleOnNettyThread(packet: UnvalidatedPlaySoundS2CPacket) {
      ClientNetworkPacketHandler.DefaultImpls.handleOnNettyThread(this, packet);
   }

   @JvmStatic
   fun `handle$lambda$0`(`$client`: Minecraft, `$packet`: UnvalidatedPlaySoundS2CPacket) {
      if (`$client`.m_91106_().m_120384_(`$packet`.getSound()) != null) {
         if (`$client`.f_91073_ != null) {
            `$client`.f_91073_
               .m_6263_(
                  `$client`.f_91074_ as Player,
                  `$packet`.getX(),
                  `$packet`.getY(),
                  `$packet`.getZ(),
                  SoundEvent.m_262824_(`$packet`.getSound()),
                  `$packet`.getCategory(),
                  `$packet`.getVolume(),
                  `$packet`.getPitch()
               );
         }
      }
   }
}
